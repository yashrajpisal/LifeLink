package com.kurukshetra.view.driver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.SourceDataLine;

import org.json.JSONArray;
import org.json.JSONObject;

public class SarvamTTSService {

    // =========================================================
    // SARVAM TTS CONFIGURATION
    // =========================================================
    private static final String SARVAM_TTS_URL = "https://api.sarvam.ai/text-to-speech";
    private static final String DEFAULT_MODEL = "bulbul:v3";
    private static final String DEFAULT_LANGUAGE_CODE = "en-IN";
    private static final String DEFAULT_SPEAKER = "shubh";
    private static final double DEFAULT_PACE = 1.0;

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final java.util.concurrent.atomic.AtomicBoolean speaking = new java.util.concurrent.atomic.AtomicBoolean(false);

    public static boolean isSpeaking() {
        return speaking.get();
    }

    // Single-threaded daemon executor to queue speech sequentially without blocking JavaFX UI thread
    private static final ExecutorService ttsExecutor = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "SarvamTTSService-Worker");
        t.setDaemon(true);
        return t;
    });

    /**
     * Speaks the given text asynchronously using Sarvam Text-to-Speech API.
     * Audio is played automatically through system speakers.
     * Safe to call from any thread (including JavaFX UI thread).
     *
     * @param text The text to speak.
     */
    public static void speak(String text) {
        if (text == null || text.trim().isEmpty()) {
            return;
        }

        System.out.println("[SarvamTTS] speak() requested: \"" + text + "\"");

        ttsExecutor.submit(() -> {
            speaking.set(true);
            try {
                String apiKey = System.getenv("SARVAM_API_KEY");
                if (apiKey == null || apiKey.trim().isEmpty()) {
                    System.err.println("[SarvamTTS] SARVAM_API_KEY environment variable is not set or empty.");
                    return;
                }

                System.out.println("[SarvamTTS] Sending TTS request to Sarvam API...");

                // Prepare JSON Request payload for Sarvam bulbul:v3
                JSONObject payload = new JSONObject();
                payload.put("text", text.trim());
                payload.put("language_code", DEFAULT_LANGUAGE_CODE);
                payload.put("speaker", DEFAULT_SPEAKER);
                payload.put("model", DEFAULT_MODEL);
                payload.put("pace", DEFAULT_PACE);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SARVAM_TTS_URL))
                        .header("api-subscription-key", apiKey.trim())
                        .header("Content-Type", "application/json")
                        .timeout(Duration.ofSeconds(15))
                        .POST(HttpRequest.BodyPublishers.ofString(payload.toString(), StandardCharsets.UTF_8))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println("[SarvamTTS] Sarvam TTS HTTP status: " + response.statusCode());

                if (response.statusCode() != 200) {
                    System.err.println("[SarvamTTS] API error: HTTP " + response.statusCode() + " - " + response.body());
                    return;
                }

                JSONObject resJson = new JSONObject(response.body());
                if (!resJson.has("audios")) {
                    System.err.println("[SarvamTTS] 'audios' field missing in response: " + response.body());
                    return;
                }

                JSONArray audios = resJson.getJSONArray("audios");
                if (audios.isEmpty()) {
                    System.err.println("[SarvamTTS] 'audios' array is empty.");
                    return;
                }

                String base64Audio = audios.getString(0);
                byte[] audioBytes = Base64.getDecoder().decode(base64Audio);

                System.out.println("[SarvamTTS] Playing audio (" + audioBytes.length + " bytes)...");
                playAudio(audioBytes);
                System.out.println("[SarvamTTS] Playback finished successfully.");

            } catch (Exception e) {
                System.err.println("[SarvamTTS] Failed to synthesize or play speech: " + e.getMessage());
                e.printStackTrace();
            } finally {
                speaking.set(false);
            }
        });
    }

    /**
     * Plays decoded audio bytes using SourceDataLine streaming, with fallback to temporary WAV file playback.
     * This avoids premature closure and guarantees the audio buffer drains fully to the speakers.
     */
    private static void playAudio(byte[] audioBytes) {
        boolean playedSuccessfully = false;

        // Method 1: Play using SourceDataLine (standard streaming to default speaker line)
        try (ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
             AudioInputStream ais = AudioSystem.getAudioInputStream(bais)) {

            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            if (AudioSystem.isLineSupported(info)) {
                try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                    line.open(format);
                    line.start();

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = ais.read(buffer, 0, buffer.length)) != -1) {
                        line.write(buffer, 0, bytesRead);
                    }

                    line.drain();
                    line.stop();
                    playedSuccessfully = true;
                }
            } else {
                // If direct format isn't supported, try converting to standard PCM
                AudioFormat targetFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        format.getSampleRate(),
                        16,
                        format.getChannels(),
                        format.getChannels() * 2,
                        format.getSampleRate(),
                        false
                );
                try (AudioInputStream convertedAis = AudioSystem.getAudioInputStream(targetFormat, ais);
                     SourceDataLine line = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, targetFormat))) {
                    line.open(targetFormat);
                    line.start();

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = convertedAis.read(buffer, 0, buffer.length)) != -1) {
                        line.write(buffer, 0, bytesRead);
                    }

                    line.drain();
                    line.stop();
                    playedSuccessfully = true;
                }
            }
        } catch (Exception ex) {
            System.err.println("[SarvamTTS] SourceDataLine playback warning: " + ex.getMessage());
        }

        // Method 2: Fallback to Clip with robust completion waiting if SourceDataLine did not succeed
        if (!playedSuccessfully) {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
                 AudioInputStream ais = AudioSystem.getAudioInputStream(bais)) {

                Clip clip = AudioSystem.getClip();
                clip.open(ais);

                long durationMs = (long) (clip.getMicrosecondLength() / 1000.0);
                clip.start();
                Thread.sleep(durationMs + 200); // Wait for full clip duration
                clip.stop();
                clip.close();
                playedSuccessfully = true;
            } catch (Exception ex) {
                System.err.println("[SarvamTTS] Clip fallback playback error: " + ex.getMessage());
            }
        }

        // Method 3: Windows fallback via System SoundPlayer if Java Sound was silent/unavailable
        if (!playedSuccessfully) {
            try {
                File tempWav = File.createTempFile("sarvam_tts_", ".wav");
                tempWav.deleteOnExit();
                try (FileOutputStream fos = new FileOutputStream(tempWav)) {
                    fos.write(audioBytes);
                }
                ProcessBuilder pb = new ProcessBuilder("powershell", "-c",
                        "(New-Object System.Media.SoundPlayer '" + tempWav.getAbsolutePath().replace("'", "''") + "').PlaySync()");
                Process proc = pb.start();
                proc.waitFor();
            } catch (Exception ex) {
                System.err.println("[SarvamTTS] Windows SoundPlayer fallback error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Testing SarvamTTSService...");
        speak("Testing LifeLink audio announcement. If you can hear this, audio playback is working perfectly.");
        try {
            Thread.sleep(8000);
        } catch (InterruptedException ignored) {}
    }
}
