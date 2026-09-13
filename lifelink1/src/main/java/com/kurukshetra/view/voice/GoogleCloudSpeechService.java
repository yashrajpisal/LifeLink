package com.kurukshetra.view.voice;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import javax.sound.sampled.*;
import java.io.FileInputStream;
import java.util.function.Consumer;

public class GoogleCloudSpeechService {

    // Exact path to your service account json
    private static final String CREDENTIALS_PATH = 
        "C:\\Users\\Asus\\Desktop\\JavaFx_Practical\\SeparateLoginTesting\\nurse\\src\\main\\resources\\lifelinkjson.json";

    private TargetDataLine microphone;
    private boolean isRecording = false;
    private SpeechClient speechClient;
    private ClientStream<StreamingRecognizeRequest> clientStream;
    private String languageCode = "mr-IN";

    public GoogleCloudSpeechService(String languageCode) {
        if (languageCode != null && !languageCode.trim().isEmpty()) {
            this.languageCode = languageCode;
        }
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    private SpeechClient createSpeechClientExplicitly() throws Exception {
        FileInputStream serviceAccountStream = new FileInputStream(CREDENTIALS_PATH);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream);

        SpeechSettings settings = SpeechSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        return SpeechClient.create(settings);
    }

    public void startListening(Consumer<String> onLiveChunk, Consumer<String> onComplete) {
        if (isRecording) return;
        isRecording = true;

        new Thread(() -> {
            try {
                // 1. Create client with explicit credentials provider
                speechClient = createSpeechClientExplicitly();

                ResponseObserver<StreamingRecognizeResponse> responseObserver =
                        new ResponseObserver<StreamingRecognizeResponse>() {
                            StringBuilder fullTranscription = new StringBuilder();

                            @Override
                            public void onStart(StreamController controller) {}

                            @Override
                            public void onResponse(StreamingRecognizeResponse response) {
                                for (StreamingRecognitionResult result : response.getResultsList()) {
                                    if (result.getAlternativesCount() > 0) {
                                        SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                                        String text = alternative.getTranscript();

                                        if (result.getIsFinal()) {
                                            fullTranscription.append(text).append(" ");
                                            onLiveChunk.accept(fullTranscription.toString().trim());
                                        } else {
                                            onLiveChunk.accept(fullTranscription + text);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                System.err.println("[GoogleSpeech] Stream error: " + t.getMessage());
                                onLiveChunk.accept("Error: " + t.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                onComplete.accept(fullTranscription.toString().trim());
                            }
                        };

                clientStream = speechClient.streamingRecognizeCallable().splitCall(responseObserver);

                RecognitionConfig recognitionConfig =
                        RecognitionConfig.newBuilder()
                                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                                .setLanguageCode(languageCode)
                                .setSampleRateHertz(16000)
                                .setEnableAutomaticPunctuation(true)
                                .build();

                StreamingRecognitionConfig streamingRecognitionConfig =
                        StreamingRecognitionConfig.newBuilder()
                                .setConfig(recognitionConfig)
                                .setInterimResults(true)
                                .build();

                StreamingRecognizeRequest request =
                        StreamingRecognizeRequest.newBuilder()
                                .setStreamingConfig(streamingRecognitionConfig)
                                .build();

                clientStream.send(request);

                // Hardware microphone setup & 16kHz resampling
                AudioFormat targetFormat = new AudioFormat(16000.0f, 16, 1, true, false);
                AudioFormat hardwareFormat = new AudioFormat(44100.0f, 16, 1, true, false);
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, hardwareFormat);

                if (!AudioSystem.isLineSupported(info)) {
                    hardwareFormat = new AudioFormat(44100.0f, 16, 2, true, false);
                    info = new DataLine.Info(TargetDataLine.class, hardwareFormat);
                }

                microphone = (TargetDataLine) AudioSystem.getLine(info);
                microphone.open(hardwareFormat);
                microphone.start();

                AudioInputStream rawStream = new AudioInputStream(microphone);
                AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, rawStream);

                byte[] buffer = new byte[4096];
                int bytesRead;

                while (isRecording) {
                    bytesRead = convertedStream.read(buffer, 0, buffer.length);
                    if (bytesRead > 0) {
                        StreamingRecognizeRequest audioRequest =
                                StreamingRecognizeRequest.newBuilder()
                                        .setAudioContent(ByteString.copyFrom(buffer, 0, bytesRead))
                                        .build();
                        clientStream.send(audioRequest);
                    }
                }

                clientStream.closeSend();
                convertedStream.close();

            } catch (Exception e) {
                System.err.println("[GoogleSpeech] Audio capture error: " + e.getMessage());
                e.printStackTrace();
            } finally {
                cleanupMicrophone();
            }
        }).start();
    }

    public void stopListening() {
        isRecording = false;
        cleanupMicrophone();
        if (clientStream != null) {
            try {
                clientStream.closeSend();
            } catch (Exception ignored) {}
        }
        if (speechClient != null && !speechClient.isShutdown()) {
            speechClient.close();
        }
    }

    private void cleanupMicrophone() {
        if (microphone != null) {
            try {
                microphone.stop();
                microphone.close();
            } catch (Exception ignored) {}
        }
    }

    public boolean isRecording() {
        return isRecording;
    }
}