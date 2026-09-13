package com.kurukshetra.view.driver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import org.json.JSONObject;

public class DriverVoiceCommandService {

    // =========================================================
    // SARVAM CONFIGURATION
    // =========================================================

    private static final String SARVAM_STT_URL = "https://api.sarvam.ai/speech-to-text";

    private static final String SARVAM_MODEL = "saaras:v3";

    private static final int VOICE_RECORD_SECONDS = 5;
    private static final int WAKE_WORD_RECORD_SECONDS = 3;

    private final HttpClient httpClient =
            HttpClient.newBuilder().build();

    private final VoiceCommandListener listener;

    private volatile boolean isVoiceRecording = false;
    private volatile boolean wakeWordListening = false;
    private volatile boolean stopRequested = false;
    private Thread wakeWordThread;
    private static volatile long lastProcessedCommandTime = 0;

    private final Set<String> activeHospitalKeys =
            java.util.concurrent.ConcurrentHashMap.newKeySet();

    // =========================================================
    // VOICE STATUS
    // =========================================================

    public enum VoiceStatus {
        INFO,
        SUCCESS,
        WARNING,
        ERROR
    }


    // =========================================================
    // VOICE COMMAND LISTENER
    // =========================================================

    public interface VoiceCommandListener {

        void onVoiceStarted();

        void onVoiceStatus(
                String message,
                VoiceStatus status
        );

        void onPoliceCommand();

        void onHospitalSelectCommand(
                String hospitalKey
        );

        void onHospitalNotifyCommand(
                String hospitalKey
        );

        void onAskRecommendedHospitalCommand();

        void onVoiceFinished();
    }


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DriverVoiceCommandService(
            VoiceCommandListener listener
    ) {

        this.listener = listener;
        this.activeHospitalKeys.addAll(Arrays.asList(
                "kem",
                "pulse",
                "navale",
                "sahyadri",
                "poona",
                "bharati",
                "inamdar",
                "sancheti",
                "ruby",
                "galaxy",
                "noble",
                "silverbirch",
                "morya"
        ));
    }

    public void updateHospitalKeys(
            Collection<String> keys
    ) {
        if (keys != null && !keys.isEmpty()) {
            this.activeHospitalKeys.addAll(keys);
        }
    }


    // =========================================================
    // CHECK RECORDING STATE
    // =========================================================

    public boolean isVoiceRecording() {

        return isVoiceRecording;
    }


    // =========================================================
    // START VOICE COMMAND
    // =========================================================

    public void startVoiceCommand(
            Collection<String> hospitalKeys
    ) {


        if (!wakeWordListening ||
                stopRequested ||
                SarvamTTSService.isSpeaking()) {

                return;
        }

        if (isVoiceRecording) {
            return;
        }

        String apiKey =
                System.getenv("SARVAM_API_KEY");

        if (apiKey == null ||
                apiKey.trim().isEmpty()) {

            listener.onVoiceStatus(
                    "Sarvam API key missing",
                    VoiceStatus.ERROR
            );

            System.err.println(
                    "[Voice] SARVAM_API_KEY environment variable is missing."
            );

            return;
        }

        isVoiceRecording = true;
        stopRequested = false;

        listener.onVoiceStarted();

        if (hospitalKeys != null && !hospitalKeys.isEmpty()) {
            activeHospitalKeys.addAll(hospitalKeys);
        }

        Set<String> hospitalKeySnapshot =
                new HashSet<>(activeHospitalKeys);

        new Thread(() -> {

            File audioFile = null;

            try {
                if (SarvamTTSService.isSpeaking()) {
                    System.out.println("[Voice] TTS is speaking, cancelling voice command recording.");
                    return;
                }

                audioFile =
                        recordVoiceAudio();

                if (SarvamTTSService.isSpeaking()) {
                    System.out.println("[Voice] TTS spoke during recording, discarding recorded audio.");
                    return;
                }

                if (audioFile == null ||
                        !audioFile.exists()) {

                    throw new IOException(
                            "Voice recording failed."
                    );
                }

                listener.onVoiceStatus(
                        "Processing voice...",
                        VoiceStatus.WARNING
                );

                String transcript =
                        sendAudioToSarvam(
                                audioFile,
                                apiKey
                        );

                if (SarvamTTSService.isSpeaking()) {
                    System.out.println("[Voice] TTS speaking during STT response, discarding: " + transcript);
                    return;
                }

                System.out.println(
                        "[Voice] Transcript: "
                                + transcript
                );

                if (transcript == null ||
                        transcript.trim().isEmpty()) {

                    listener.onVoiceStatus(
                            "Could not understand",
                            VoiceStatus.ERROR
                    );

                    return;
                }

                processVoiceCommand(
                        transcript,
                        hospitalKeySnapshot
                );

            } catch (Exception ex) {

                ex.printStackTrace();

                listener.onVoiceStatus(
                        "Voice error: "
                                + ex.getMessage(),
                        VoiceStatus.ERROR
                );

            } finally {

                isVoiceRecording = false;

                if (audioFile != null) {

                    try {

                        Files.deleteIfExists(
                                audioFile.toPath()
                        );

                    } catch (Exception ignored) {
                    }
                }

                listener.onVoiceFinished();

                // if (wakeWordListening &&
                //         !stopRequested) {

                //         startWakeWordListening(
                //                 hospitalKeySnapshot
                //         );
                // }
            }

        }, "LifeLink-VoiceCommandThread").start();
    }










        // =========================================================
        // START WAKE WORD LISTENING
        // =========================================================

        public void startWakeWordListening(
                Collection<String> hospitalKeys
        ) {

        if (wakeWordListening) {
                return;
        }

        String apiKey =
                System.getenv("SARVAM_API_KEY");

        if (apiKey == null ||
                apiKey.trim().isEmpty()) {

                listener.onVoiceStatus(
                        "Sarvam API key missing",
                        VoiceStatus.ERROR
                );

                System.err.println(
                        "[Voice] SARVAM_API_KEY environment variable is missing."
                );

                return;
        }

        wakeWordListening = true;

        stopRequested = false;

        if (hospitalKeys != null && !hospitalKeys.isEmpty()) {
            activeHospitalKeys.addAll(hospitalKeys);
        }

        wakeWordThread =
                new Thread(() -> {

                        while (
                                wakeWordListening &&
                                !stopRequested
                        ) {

                        if (isVoiceRecording || SarvamTTSService.isSpeaking()) {

                                try {
                                Thread.sleep(300);
                                } catch (InterruptedException ignored) {
                                }

                                continue;
                        }

                        File audioFile = null;

                        try {

                                audioFile =
                                        recordWakeWordAudio();

                                if (audioFile == null ||
                                        !audioFile.exists()) {

                                continue;
                                }

                                if (!wakeWordListening ||
                                        stopRequested ||
                                        SarvamTTSService.isSpeaking()) {

                                continue;
                                }

                                String transcript =
                                        sendAudioToSarvam(
                                                audioFile,
                                                apiKey
                                        );

                                if (SarvamTTSService.isSpeaking()) {
                                        System.out.println(
                                                "[Wake] Discarded transcript during TTS playback: "
                                                        + transcript
                                        );
                                        continue;
                                }

                                System.out.println(
                                        "[Wake] Transcript: "
                                                + transcript
                                );

                                boolean hasCommand = isDirectCommand(transcript);
                                boolean hasWakeWord = containsWakeWord(transcript);

                                if (hasCommand) {

                                        System.out.println(
                                                "[Wake] Direct command detected: "
                                                        + transcript
                                        );

                                        listener.onVoiceStarted();
                                        listener.onVoiceStatus(
                                                "Processing command...",
                                                VoiceStatus.WARNING
                                        );

                                        processVoiceCommand(
                                                transcript,
                                                new HashSet<>(activeHospitalKeys)
                                        );

                                        listener.onVoiceFinished();

                                } else if (hasWakeWord) {

                                        System.out.println(
                                                "[Wake] Listening for command..."
                                        );

                                        listener.onVoiceStatus(
                                                "Listening for command...",
                                                VoiceStatus.SUCCESS
                                        );

                                        startVoiceCommand(
                                                new HashSet<>(activeHospitalKeys)
                                        );

                                }

                        } catch (Exception ex) {

                                if (wakeWordListening &&
                                        !stopRequested) {

                                System.err.println(
                                        "[Wake] Error: "
                                                + ex.getMessage()
                                );
                                }

                        } finally {

                                if (audioFile != null) {

                                try {

                                        Files.deleteIfExists(
                                                audioFile.toPath()
                                        );

                                } catch (Exception ignored) {
                                }
                                }
                        }
                        }

                }, "LifeLink-WakeWordThread");

        wakeWordThread.setDaemon(true);

        wakeWordThread.start();

        listener.onVoiceStatus(
                "Waiting for Hey LifeLink...",
                VoiceStatus.INFO
        );
        }







        // =========================================================
        // STOP WAKE WORD LISTENING
        // =========================================================

        public void stopWakeWordListening() {

                wakeWordListening = false;

                stopRequested = true;

                if (wakeWordThread != null) {

                        wakeWordThread.interrupt();

                        wakeWordThread = null;
                }

                System.out.println(
                        "[Wake] Wake word listening stopped."
                );
        }








        // =========================================================
        // RECORD WAKE WORD AUDIO
        // =========================================================

        private File recordWakeWordAudio()
                throws Exception {

        AudioFormat format =
                new AudioFormat(
                        16000.0f,
                        16,
                        1,
                        true,
                        false
                );

        DataLine.Info info =
                new DataLine.Info(
                        TargetDataLine.class,
                        format
                );

        if (!AudioSystem.isLineSupported(info)) {

                throw new IOException(
                        "Microphone audio format is not supported."
                );
        }

        TargetDataLine microphone =
                (TargetDataLine)
                        AudioSystem.getLine(info);

        File audioFile =
                File.createTempFile(
                        "lifelink_wake_",
                        ".wav"
                );

        try {

                microphone.open(format);

                microphone.start();

                int bufferSize =
                        (int)
                                (
                                        format.getSampleRate()
                                                * format.getFrameSize()
                                );

                byte[] buffer =
                        new byte[bufferSize];

                long endTime =
                        System.currentTimeMillis()
                                + (
                                        WAKE_WORD_RECORD_SECONDS
                                                * 1000L
                                );

                ByteArrayOutputStream audioData =
                        new ByteArrayOutputStream();

                while (
                        System.currentTimeMillis()
                                < endTime
                                &&
                        wakeWordListening
                                &&
                        !stopRequested
                ) {

                int bytesRead =
                        microphone.read(
                                buffer,
                                0,
                                buffer.length
                        );

                if (bytesRead > 0) {

                        audioData.write(
                                buffer,
                                0,
                                bytesRead
                        );
                }
                }

                byte[] pcmData =
                        audioData.toByteArray();

                if (pcmData.length > 0) {

                writeWavFile(
                        audioFile,
                        pcmData,
                        format
                );
                }

        } finally {

                try {
                microphone.stop();
                } catch (Exception ignored) {
                }

                try {
                microphone.close();
                } catch (Exception ignored) {
                }
        }

        return audioFile;
        }









        // =========================================================
        // CHECK WAKE WORD
        // =========================================================

        private boolean containsWakeWord(
                        String transcript
                ) {

                if (transcript == null ||
                        transcript.trim().isEmpty()) {

                        return false;
                }

                String normalized =
                        transcript
                                .toLowerCase()
                                .replaceAll(
                                        "[^a-z0-9 ]",
                                        " "
                                )
                                .replaceAll(
                                        "\\s+",
                                        " "
                                )
                                .trim();

                System.out.println(
                        "[Wake] Checking: "
                                + normalized
                );

                return normalized.contains("hey lifelink") ||
                        normalized.contains("hey livelink") ||
                        normalized.contains("hey life link") ||
                        normalized.contains("hey live link");
        }

        private boolean isDirectCommand(String transcript) {
                if (transcript == null || transcript.trim().isEmpty()) {
                        return false;
                }
                String cmd = transcript.trim().toLowerCase();
                return isPoliceVoiceCommand(cmd) ||
                       isAskRecommendedHospitalCommand(cmd) ||
                       isHospitalSelectCommand(cmd, activeHospitalKeys) ||
                       isHospitalNotifyCommand(cmd, activeHospitalKeys);
        }






    // =========================================================
    // RECORD VOICE AUDIO
    // =========================================================

    private File recordVoiceAudio()
            throws Exception {

        AudioFormat format =
                new AudioFormat(
                        16000.0f,
                        16,
                        1,
                        true,
                        false
                );

        DataLine.Info info =
                new DataLine.Info(
                        TargetDataLine.class,
                        format
                );

        if (!AudioSystem.isLineSupported(info)) {

            throw new IOException(
                    "Microphone audio format is not supported."
            );
        }

        TargetDataLine microphone =
                (TargetDataLine)
                        AudioSystem.getLine(info);

        File audioFile =
                File.createTempFile(
                        "lifelink_voice_",
                        ".wav"
                );

        try {

            microphone.open(format);
            microphone.start();

            int bufferSize =
                    (int)
                            (format.getSampleRate()
                                    * format.getFrameSize());

            byte[] buffer =
                    new byte[bufferSize];

            long endTime =
                    System.currentTimeMillis()
                            + (VOICE_RECORD_SECONDS * 1000L);

            ByteArrayOutputStream audioData =
                    new ByteArrayOutputStream();

            while (
                System.currentTimeMillis()
                        < endTime
                        &&
                !stopRequested
            ) {

                int bytesRead =
                        microphone.read(
                                buffer,
                                0,
                                buffer.length
                        );

                if (bytesRead > 0) {

                    audioData.write(
                            buffer,
                            0,
                            bytesRead
                    );
                }
            }

            byte[] pcmData =
                    audioData.toByteArray();

            writeWavFile(
                    audioFile,
                    pcmData,
                    format
            );

        } finally {

            try {
                microphone.stop();
            } catch (Exception ignored) {
            }

            try {
                microphone.close();
            } catch (Exception ignored) {
            }
        }

        return audioFile;
    }


    // =========================================================
    // WRITE WAV FILE
    // =========================================================

    private void writeWavFile(
            File file,
            byte[] audioData,
            AudioFormat format
    ) throws IOException {

        int sampleRate =
                (int) format.getSampleRate();

        int channels =
                format.getChannels();

        int bitsPerSample =
                format.getSampleSizeInBits();

        int byteRate =
                sampleRate
                        * channels
                        * bitsPerSample
                        / 8;

        int blockAlign =
                channels
                        * bitsPerSample
                        / 8;

        int dataLength =
                audioData.length;

        int fileSize =
                36 + dataLength;

        try (OutputStream out =
                     Files.newOutputStream(
                             file.toPath(),
                             StandardOpenOption.CREATE,
                             StandardOpenOption.TRUNCATE_EXISTING
                     )) {

            out.write('R');
            out.write('I');
            out.write('F');
            out.write('F');

            writeLittleEndianInt(
                    out,
                    fileSize
            );

            out.write('W');
            out.write('A');
            out.write('V');
            out.write('E');

            out.write('f');
            out.write('m');
            out.write('t');
            out.write(' ');

            writeLittleEndianInt(
                    out,
                    16
            );

            writeLittleEndianShort(
                    out,
                    (short) 1
            );

            writeLittleEndianShort(
                    out,
                    (short) channels
            );

            writeLittleEndianInt(
                    out,
                    sampleRate
            );

            writeLittleEndianInt(
                    out,
                    byteRate
            );

            writeLittleEndianShort(
                    out,
                    (short) blockAlign
            );

            writeLittleEndianShort(
                    out,
                    (short) bitsPerSample
            );

            out.write('d');
            out.write('a');
            out.write('t');
            out.write('a');

            writeLittleEndianInt(
                    out,
                    dataLength
            );

            out.write(audioData);
        }
    }


    // =========================================================
    // LITTLE ENDIAN INT
    // =========================================================

    private void writeLittleEndianInt(
            OutputStream out,
            int value
    ) throws IOException {

        out.write(value & 0xff);
        out.write((value >> 8) & 0xff);
        out.write((value >> 16) & 0xff);
        out.write((value >> 24) & 0xff);
    }


    // =========================================================
    // LITTLE ENDIAN SHORT
    // =========================================================

    private void writeLittleEndianShort(
            OutputStream out,
            short value
    ) throws IOException {

        out.write(value & 0xff);
        out.write((value >> 8) & 0xff);
    }


    // =========================================================
    // SEND AUDIO TO SARVAM
    // =========================================================

    private String sendAudioToSarvam(
            File audioFile,
            String apiKey
    ) throws Exception {

        String boundary =
                "----LifeLinkVoiceBoundary"
                        + System.currentTimeMillis();

        ByteArrayOutputStream body =
                new ByteArrayOutputStream();

        writeMultipartText(
                body,
                boundary,
                "model",
                SARVAM_MODEL
        );

        writeMultipartText(
                body,
                boundary,
                "mode",
                "codemix"
        );

        writeMultipartText(
                body,
                boundary,
                "language_code",
                "unknown"
        );

        writeMultipartFile(
                body,
                boundary,
                "file",
                audioFile,
                "audio/wav"
        );

        body.write(
                ("--" + boundary + "--\r\n")
                        .getBytes(StandardCharsets.UTF_8)
        );

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(SARVAM_STT_URL))
                        .header(
                                "api-subscription-key",
                                apiKey
                        )
                        .header(
                                "Content-Type",
                                "multipart/form-data; boundary="
                                        + boundary
                        )
                        .POST(
                                HttpRequest.BodyPublishers.ofByteArray(
                                        body.toByteArray()
                                )
                        )
                        .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        System.out.println(
                "[Voice] Sarvam HTTP status: "
                        + response.statusCode()
        );

        if (response.statusCode() != 200) {

            throw new IOException(
                    "Sarvam API error: HTTP "
                            + response.statusCode()
                            + " - "
                            + response.body()
            );
        }

        JSONObject json =
                new JSONObject(
                        response.body()
                );

        return json.optString(
                "transcript",
                ""
        );
    }


    // =========================================================
    // MULTIPART TEXT
    // =========================================================

    private void writeMultipartText(
            OutputStream out,
            String boundary,
            String name,
            String value
    ) throws IOException {

        String part =
                "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\""
                        + name
                        + "\"\r\n\r\n"
                        + value
                        + "\r\n";

        out.write(
                part.getBytes(
                        StandardCharsets.UTF_8
                )
        );
    }


    // =========================================================
    // MULTIPART FILE
    // =========================================================

    private void writeMultipartFile(
            OutputStream out,
            String boundary,
            String name,
            File file,
            String contentType
    ) throws IOException {

        String header =
                "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\""
                        + name
                        + "\"; filename=\""
                        + file.getName()
                        + "\"\r\n"
                        + "Content-Type: "
                        + contentType
                        + "\r\n\r\n";

        out.write(
                header.getBytes(
                        StandardCharsets.UTF_8
                )
        );

        Files.copy(
                file.toPath(),
                out
        );

        out.write(
                "\r\n".getBytes(
                        StandardCharsets.UTF_8
                )
        );
    }


    // =========================================================
    // PROCESS VOICE COMMAND
    // =========================================================

    private void processVoiceCommand(
            String transcript,
            Collection<String> hospitalKeys
    ) {

        long now = System.currentTimeMillis();
        if (now - lastProcessedCommandTime < 3500) {
            System.out.println(
                    "[Voice] Debouncing rapid voice command (within cooldown): "
                            + transcript
            );
            return;
        }

        String command =
                transcript
                        .trim()
                        .toLowerCase();

        System.out.println(
                "[Voice] Processing command: "
                        + transcript
        );


        // -----------------------------------------------------
        // POLICE COMMAND
        // -----------------------------------------------------

        if (isPoliceVoiceCommand(command)) {

            lastProcessedCommandTime = System.currentTimeMillis();
            listener.onPoliceCommand();

            return;
        }


        // -----------------------------------------------------
        // ASK RECOMMENDED HOSPITAL COMMAND
        // -----------------------------------------------------

        if (isAskRecommendedHospitalCommand(command)) {

            lastProcessedCommandTime = System.currentTimeMillis();
            System.out.println(
                    "[Voice] Recognized intent: ASK_RECOMMENDED_HOSPITAL from \""
                            + transcript + "\""
            );

            listener.onAskRecommendedHospitalCommand();

            return;
        }


        // -----------------------------------------------------
        // HOSPITAL COMMAND
        // -----------------------------------------------------

        boolean selectCommand =
                isHospitalSelectCommand(
                        command,
                        hospitalKeys
                );

        boolean notifyCommand =
                isHospitalNotifyCommand(
                        command,
                        hospitalKeys
                );

        if (selectCommand ||
                notifyCommand) {

            String hospitalKey =
                    findHospitalFromVoiceCommand(
                            command,
                            hospitalKeys
                    );

            if (selectCommand && !notifyCommand) {
                // If it's a select command and no specific hospital is identified (e.g. driver just said "select" / "select hospital")
                // pass "" so DriverDashboard can select the already-notified hospital!
                lastProcessedCommandTime = System.currentTimeMillis();
                listener.onHospitalSelectCommand(
                        hospitalKey != null ? hospitalKey : ""
                );
                return;
            }

            if (hospitalKey == null) {

                listener.onVoiceStatus(
                        "Hospital not found",
                        VoiceStatus.ERROR
                );

                System.out.println(
                        "[Voice] Could not identify hospital from: "
                                + transcript
                );

                return;
            }

            lastProcessedCommandTime = System.currentTimeMillis();
            if (selectCommand) {
                listener.onHospitalSelectCommand(hospitalKey);
            } else {
                listener.onHospitalNotifyCommand(hospitalKey);
            }
            return;
        }


        // -----------------------------------------------------
        // UNKNOWN COMMAND
        // -----------------------------------------------------

        listener.onVoiceStatus(
                "Command not recognized",
                VoiceStatus.ERROR
        );

        System.out.println(
                "[Voice] Unsupported command: "
                        + transcript
        );
    }


    // =========================================================
    // POLICE COMMAND CHECK
    // =========================================================

    private boolean isPoliceVoiceCommand(
            String command
    ) {

        if (command == null || command.trim().isEmpty()) {
            return false;
        }

        String normalized =
                command.toLowerCase()
                        .replaceAll("[^a-z0-9 ]", " ")
                        .replaceAll("\\s+", " ")
                        .trim();

        // Guard against assistant's own confirmation announcement
        if (normalized.equals("police notified") || normalized.contains("police notified")) {
            return false;
        }

        boolean hasPolice =
                normalized.contains("police") ||
                normalized.contains("traffic police") ||
                normalized.contains("polise");

        boolean hasCorridor =
                normalized.contains("green corridor") ||
                normalized.contains("corridor");

        return hasPolice || hasCorridor;
    }


    // =========================================================
    // ASK RECOMMENDED HOSPITAL COMMAND CHECK
    // =========================================================

    public static boolean isAskRecommendedHospitalCommand(String command) {
        if (command == null || command.trim().isEmpty()) {
            return false;
        }

        String normalized = command.toLowerCase()
                .replaceAll("[^a-z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();

        // 1. Guard against explicit action commands: "select", "notify", "inform", "alert", "choose", "chuno"
        // These belong to SELECT_HOSPITAL or NOTIFY_HOSPITAL
        if (normalized.startsWith("select ") || normalized.startsWith("choose ")
                || normalized.startsWith("notify ") || normalized.startsWith("inform ")
                || normalized.startsWith("alert ") || normalized.contains(" ko select")
                || normalized.contains(" select karo") || normalized.contains(" choose karo")
                || normalized.contains(" notify karo") || normalized.contains(" bhejo")) {
            return false;
        }

        // Guard against assistant's own spoken answers & announcements (to eliminate acoustic feedback loops)
        if (normalized.contains("for this emergency")
                || normalized.contains("active emergency assigned")
                || normalized.contains("currently being loaded")
                || normalized.contains("please ask again")
                || normalized.contains("at the moment")
                || normalized.contains("police notified")
                || normalized.contains("hospital selected")
                || normalized.contains("hospital notified")) {
            return false;
        }

        // 2. Direct intent phrases in English
        if (normalized.contains("recommended hospital")
                || normalized.contains("recommend hospital")
                || normalized.contains("recommend a hospital")
                || normalized.contains("suggest hospital")
                || normalized.contains("suggest a hospital")
                || normalized.contains("suggested hospital")
                || normalized.contains("which hospital")
                || normalized.contains("what hospital")
                || normalized.contains("best hospital")
                || normalized.contains("where should i go")
                || normalized.contains("where should i take")
                || normalized.contains("where to take")
                || normalized.contains("where do i take")
                || normalized.contains("which one is recommended")
                || normalized.contains("which one is best")
                || normalized.contains("nearest hospital")
                || normalized.contains("closest hospital")) {
            return true;
        }

        // 3. Direct intent phrases in Hindi / Hinglish
        if (normalized.contains("kaunsa hospital")
                || normalized.contains("kounsa hospital")
                || normalized.contains("kon sa hospital")
                || normalized.contains("kaun se hospital")
                || normalized.contains("kounse hospital")
                || normalized.contains("hospital kaunsa")
                || normalized.contains("hospital kounsa")
                || normalized.contains("hospital kon sa")
                || normalized.contains("leke jaana hai")
                || normalized.contains("leke jana hai")
                || normalized.contains("leke jaun")
                || normalized.contains("leke jau")
                || normalized.contains("kahan leke")
                || normalized.contains("kahan jana hai")
                || normalized.contains("kaha jana hai")
                || normalized.contains("kahan le jana")
                || normalized.contains("kaha le jana")
                || normalized.contains("kisme leke")
                || normalized.contains("kis hospital")
                || normalized.contains("best hospital kaunsa")
                || normalized.contains("best hospital kounsa")
                || normalized.contains("hospital recommended hai")
                || normalized.contains("recommended kaunsa")) {
            return true;
        }

        // 4. Inquisitive meaning detection: question word + hospital
        boolean hasQuestionWord = normalized.contains("which") || normalized.contains("what")
                || normalized.contains("where") || normalized.contains("tell me")
                || normalized.contains("kaun") || normalized.contains("kon")
                || normalized.contains("kaha") || normalized.contains("kahan")
                || normalized.contains("kidhar") || normalized.contains("kis");

        boolean hasHospitalWord = normalized.contains("hospital") || normalized.contains("hosp")
                || normalized.contains("patient");

        boolean hasRecommendationWord = normalized.contains("recommend") || normalized.contains("best")
                || normalized.contains("better") || normalized.contains("take")
                || normalized.contains("go to") || normalized.contains("jaana")
                || normalized.contains("jana") || normalized.contains("jaun")
                || normalized.contains("batao") || normalized.contains("bataye");

        if (hasQuestionWord && (hasHospitalWord || hasRecommendationWord)) {
            return true;
        }

        return false;
    }


    // =========================================================
    // HOSPITAL SELECT COMMAND CHECK
    // =========================================================

    private boolean isHospitalSelectCommand(
            String command,
            Collection<String> hospitalKeys
    ) {

        if (command == null) {
            return false;
        }

        String normalized =
                command.toLowerCase();

        // Guard against assistant's own announcements
        if (normalized.equals("hospital selected") || normalized.contains("hospital selected")
                || normalized.contains("is recommended") || normalized.contains("for this emergency")) {
            return false;
        }

        boolean hasSelectWord =
                normalized.contains("select") ||
                normalized.contains("choose") ||
                normalized.contains("chuno") ||
                normalized.contains("select karo") ||
                normalized.contains("choose karo") ||
                normalized.contains("ko select") ||
                normalized.contains("navigate") ||
                normalized.contains("navigation");

        boolean hasHospital =
                normalized.contains("hospital");

        return hasSelectWord &&
                (
                        hasHospital ||
                        findHospitalFromVoiceCommand(
                                normalized,
                                hospitalKeys
                        ) != null ||
                        normalized.trim().split("\\s+").length <= 3
                );
    }


    // =========================================================
    // HOSPITAL NOTIFY COMMAND CHECK
    // =========================================================

    private boolean isHospitalNotifyCommand(
            String command,
            Collection<String> hospitalKeys
    ) {

        if (command == null) {
            return false;
        }

        String normalized =
                command.toLowerCase();

        // Guard against assistant's own announcements
        if (normalized.equals("hospital notified") || normalized.contains("hospital notified")
                || normalized.contains("is recommended") || normalized.contains("for this emergency")) {
            return false;
        }

        boolean hasNotifyWord =
                normalized.contains("notify") ||
                normalized.contains("inform") ||
                normalized.contains("alert") ||
                normalized.contains("bhejo") ||
                normalized.contains("intimate");

        boolean hasHospital =
                normalized.contains("hospital");

        return hasNotifyWord &&
                (
                        hasHospital ||
                        findHospitalFromVoiceCommand(
                                normalized,
                                hospitalKeys
                        ) != null
                );
    }


    // =========================================================
    // STOPWORDS SET FOR ACCURATE HOSPITAL MATCHING
    // =========================================================

    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
            "pune", "punecity", "hospital", "hospitals", "hosp",
            "multispeciality", "multispecialty", "multi", "speciality", "specialty",
            "super", "centre", "center", "research", "care", "clinic",
            "trust", "memorial", "institute", "road", "rd", "rasta", "peth",
            "the", "to", "and", "for", "in", "at", "near", "ko", "karo", "karna", "please",
            "select", "notify", "choose", "chuno", "navigate", "alert", "do", "this", "that"
    ));

    public static boolean isStopWord(String word) {
        return word != null && STOPWORDS.contains(word.toLowerCase().trim());
    }


    // =========================================================
    // FIND HOSPITAL FROM VOICE COMMAND
    // =========================================================

    private String findHospitalFromVoiceCommand(
            String command,
            Collection<String> hospitalKeys
    ) {

        if (command == null ||
                command.trim().isEmpty()) {

            return null;
        }

        Collection<String> keysToSearch =
                (hospitalKeys != null && !hospitalKeys.isEmpty())
                        ? hospitalKeys
                        : activeHospitalKeys;

        if (keysToSearch == null ||
                keysToSearch.isEmpty()) {

            return null;
        }

        String normalizedCommand =
                normalizeHospitalName(command);

        if (normalizedCommand == null || normalizedCommand.trim().isEmpty()) {
            return null;
        }

        normalizedCommand = normalizedCommand.trim();

        String bestMatch = null;
        int bestScore = 0;

        for (String hospitalKey :
                keysToSearch) {

            if (hospitalKey == null ||
                    hospitalKey.trim().isEmpty()) {

                continue;
            }

            String normKey = normalizeHospitalName(hospitalKey);
            if (normKey.isEmpty()) {
                normKey = hospitalKey.toLowerCase().trim();
            }

            int score = 0;

            if (normalizedCommand.equals(normKey) || normalizedCommand.equals(hospitalKey)) {

                score = 1000;

            } else if (normKey.contains(normalizedCommand) || normalizedCommand.contains(normKey)) {

                score = 500;

            } else {

                String[] hospitalWords =
                        normKey.split(" ");

                for (String word :
                        hospitalWords) {

                    if (word.length() < 3 || isStopWord(word)) {
                        continue;
                    }

                    if (normalizedCommand.contains(
                            word
                    )) {

                        score += (word.length() * 20);
                    }
                }

                String[] cmdWords =
                        normalizedCommand.split(" ");

                for (String cmdWord :
                        cmdWords) {

                    if (cmdWord.length() < 3 || isStopWord(cmdWord)) {
                        continue;
                    }

                    if (normKey.contains(
                            cmdWord
                    )) {

                        score += (cmdWord.length() * 20);
                    }
                }
            }

            if (score > bestScore) {

                bestScore = score;

                bestMatch = hospitalKey;
            }
        }

        return (bestScore > 0) ? bestMatch : null;
    }


    // =========================================================
    // NORMALIZE HOSPITAL NAME
    // =========================================================

    public static String normalizeHospitalName(
            String value
    ) {

        if (value == null) {
            return "";
        }

        String normalized =
                value
                        .toLowerCase()
                        .replaceAll(
                                "[^a-z0-9 ]",
                                " "
                        )
                        // Phonetic & common STT replacements for KEM Hospital (prioritized)
                        .replaceAll(
                                "\\bk\\s*e\\s*m\\b",
                                "kem"
                        )
                        .replaceAll(
                                "\\bk\\s+e\\s+m\\b",
                                "kem"
                        )
                        .replaceAll(
                                "\\bkay\\s+ee\\s+em\\b",
                                "kem"
                        )
                        .replaceAll(
                                "\\bkay\\s+e\\s+m\\b",
                                "kem"
                        )
                        .replaceAll(
                                "\\bkay\\s+yam\\b",
                                "kem"
                        )
                        .replaceAll(
                                "\\bchem\\b",
                                "kem"
                        )
                        .replaceAll(
                                "\\bcam\\b",
                                "kem"
                        )
                        .replaceAll(
                                "\\bkam\\b",
                                "kem"
                        )
                        .replaceAll(
                                "\\bkhem\\b",
                                "kem"
                        )
                        .replaceAll(
                                "\\bkm\\b",
                                "kem"
                        )
                        .replaceAll(
                                "\\bk\\s+m\\b",
                                "kem"
                        )
                        .replaceAll(
                                "\\bcame\\b",
                                "kem"
                        )
                        .replaceAll(
                                "\\bcan\\b",
                                "kem"
                        )
                        // Navale Hospital
                        .replaceAll(
                                "\\bnawale\\b",
                                "navale"
                        )
                        .replaceAll(
                                "\\bnavle\\b",
                                "navale"
                        )
                        .replaceAll(
                                "\\bnawle\\b",
                                "navale"
                        )
                        .replaceAll(
                                "\\bnaval\\b",
                                "navale"
                        )
                        .replaceAll(
                                "\\bkashibai\\b",
                                "navale"
                        )
                        .replaceAll(
                                "\\bsknmcgh\\b",
                                "navale"
                        )
                        // Sahyadri Hospital
                        .replaceAll(
                                "\\bsahayadri\\b",
                                "sahyadri"
                        )
                        .replaceAll(
                                "\\bsayhadri\\b",
                                "sahyadri"
                        )
                        .replaceAll(
                                "\\bsayadri\\b",
                                "sahyadri"
                        )
                        .replaceAll(
                                "\\bsahyadhri\\b",
                                "sahyadri"
                        )
                        // Pulse Hospital
                        .replaceAll(
                                "\\bpals\\b",
                                "pulse"
                        )
                        .replaceAll(
                                "\\bpuls\\b",
                                "pulse"
                        )
                        // Bharati Vidyapeeth
                        .replaceAll(
                                "\\bbharti\\b",
                                "bharati"
                        )
                        .replaceAll(
                                "\\bbharathi\\b",
                                "bharati"
                        )
                        .replaceAll(
                                "\\bvidyapeeth\\b",
                                "bharati"
                        )
                        .replaceAll(
                                "\\bvidyapith\\b",
                                "bharati"
                        )
                        // Poona Hospital
                        .replaceAll(
                                "\\bpuna\\b",
                                "poona"
                        )
                        // Sancheti
                        .replaceAll(
                                "\\bsan\\s+cheti\\b",
                                "sancheti"
                        )
                        // Inamdar
                        .replaceAll(
                                "\\binam\\s+dar\\b",
                                "inamdar"
                        )
                        // Ruby Hall
                        .replaceAll(
                                "\\brubi\\b",
                                "ruby"
                        )
                        // Noble / Nobel
                        .replaceAll(
                                "\\bnobel\\b",
                                "noble"
                        )
                        .replaceAll(
                                "\\bojas\\b",
                                "noble"
                        )
                        // Galaxy
                        .replaceAll(
                                "\\bgalaxi\\b",
                                "galaxy"
                        )
                        // Silver Birch
                        .replaceAll(
                                "\\bsilver\\s+birch\\b",
                                "silverbirch"
                        )
                        // Morya
                        .replaceAll(
                                "\\bmoulya\\b",
                                "morya"
                        )
                        // Generic words & action commands to strip
                        .replaceAll(
                                "\\bhospital\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bhospitals\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bhosp\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bhey\\b",
                                " "
                        )
                        .replaceAll(
                                "\\blifelink\\b",
                                " "
                        )
                        .replaceAll(
                                "\\blivelink\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bthe\\b",
                                " "
                        )
                        .replaceAll(
                                "\\band\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bko\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bplease\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bselect\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bnotify\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bchoose\\b",
                                " "
                        )
                        .replaceAll(
                                "\\balert\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bkaro\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bkarna\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bchuno\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bdo\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bto\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bnavigate\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bnavigation\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bthis\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bthat\\b",
                                " "
                        )
                        // Geographic and institutional stopwords that cause spurious cross-matches
                        .replaceAll(
                                "\\bpune\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bpunecity\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bmultispeciality\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bmultispecialty\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bmulti\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bspeciality\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bspecialty\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bsuper\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bcentre\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bcenter\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bresearch\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bcare\\b",
                                " "
                        )
                        .replaceAll(
                                "\\bclinic\\b",
                                " "
                        )
                        .replaceAll(
                                "\\broad\\b",
                                " "
                        )
                        .replaceAll(
                                "\\brd\\b",
                                " "
                        )
                        .replaceAll(
                                "\\s+",
                                " "
                        )
                        .trim();

        return normalized;
    }

    public static void main(String[] args) {
        String[] tests = {
            "hey lifelink which hospital is recommended",
            "Which hospital is recommended?",
            "Which hospital should I go to?",
            "Which hospital is best?",
            "Where should I take the patient?",
            "Tell me the recommended hospital.",
            "Hospital kaunsa recommended hai?",
            "Kaunsa hospital leke jaana hai?",
            "Patient ko kaunse hospital leke jaun?",
            "Best hospital kaunsa hai?",
            "Which one is recommended?",
            "What hospital is best?",
            "Where to take him?",
            "Select KEM Hospital",
            "Notify KEM Hospital",
            "Alert police now",
            "Select Bharati Hospital",
            "Notify Sahyadri Hospital",
            "KEM Hospital is recommended for this emergency.",
            "Bharati Hospital is recommended for this emergency.",
            "There is no active emergency assigned at the moment.",
            "Hospitals are currently being loaded. Please ask again in a moment."
        };

        for (String test : tests) {
            boolean isAskRec = isAskRecommendedHospitalCommand(test);
            System.out.printf("%-60s -> isAskRecommendedHospitalCommand: %b%n", "\"" + test + "\"", isAskRec);
        }
    }
}