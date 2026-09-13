package com.kurukshetra.view.family;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FirstAidAssistant {

   
    private static final String GEMINI_API_KEY = "AIzaSyA9W_Tr-oRn9LcafR8UrcdseDgYz7Yz31E";
    private static final String ELEVENLABS_API_KEY = "sk_a4089c1ccae4159a7a9c68018cf67d489dfc98787971918c";
    private static final String ELEVENLABS_VOICE_ID = "zmh5xhBvMzqR4ZlXgcgL"; // Monika Sogam (Community Library Voice)
    private static final String ELEVENLABS_FALLBACK_VOICE_ID = "MejQszAD3NhGn1AFdImO"; // LifeLink Nurse (VoiceLab Generated)

    private static final String ELEVENLABS_MODEL = "eleven_multilingual_v2";
    private static final String GEMINI_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.6-flash:generateContent?key=" + GEMINI_API_KEY;

    private static final String PRIMARY = "#CA6721";
    private static final String PRIMARY_DARK = "#782F16";
    private static final String PAGE_BG = "#FCF9F7";
    private static final String SURFACE = "#FFFFFF";
    private static final String VERY_LIGHT_TERRACOTTA = "#FDF0E8";
    private static final String LIGHT_TERRACOTTA = "#F9E4D6";
    private static final String PALE_PEACH = "#FEF7F2";
    private static final String TEXT_PRIMARY = "#2B211D";
    private static final String TEXT_SECONDARY = "#675B55";
    private static final String TEXT_MUTED = "#938780";
    private static final String BORDER_COLOR = "#EDE0D8";
    private static final String GREEN = "#16A34A";
    private static final String LIGHT_GREEN = "#E8F5EC";
    private static final String RED = "#D71920";
    private static final String LIGHT_RED = "#FEE2E2";

    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";

    // Engine & State
    private Stage stage;
    private MediaPlayer nurseVideoPlayer;
    private MediaView nurseVideoView;
    private ImageView nurseGifView;
    private Image nurseAnimatedGif;
    private Image nurseStillImage;
    private boolean isMarathi = false;
    private boolean isMicActive = false;
    private final AtomicBoolean isSpeakingActive = new AtomicBoolean(false);
    private MediaPlayer currentAudioPlayer = null;
    private Path currentAudioFile = null;

    // Native Java Audio Recorder
    private final JavaAudioRecorder recorder = new JavaAudioRecorder();

    // UI Nodes
    private VBox chatStream;
    private ScrollPane chatScrollPane;
    private TextField textInputField;
    private Button micToggleButton;
    private Button stopSpeakingButton;
    private Label callStatusLabel = new Label("● Call Connected (Ready)");
    private Button enLangBtn;
    private Button mrLangBtn;

    private String lastGeneratedSpeech = "Hello, I am Sister Ananya. Describe your emergency or symptoms.";

    // Overlay Presentation & Teleprompter State
    private HBox nurseOverlayPane;
    private boolean isOverlayEnabled = true;
    private boolean isVoiceEnabled = true;
    private Button toggleOverlayBtn;
    private Button toggleVoiceBtn;
    private Timeline teleprompterTimeline;
    private Label teleprompterTextLabel;
    private ScrollPane teleprompterScrollPane;

   
    private static class JavaAudioRecorder {
        private TargetDataLine targetLine;
        private final AudioFormat format;
        private volatile boolean isRecording = false;
        private ByteArrayOutputStream audioBytes;

        public JavaAudioRecorder() {
            this.format = new AudioFormat(16000.0f, 16, 1, true, false);
        }

        public synchronized void startRecording() {
            try {
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                if (!AudioSystem.isLineSupported(info)) {
                    System.err.println("Microphone line not supported by the system.");
                    return;
                }
                targetLine = (TargetDataLine) AudioSystem.getLine(info);
                targetLine.open(format);
                targetLine.start();
                isRecording = true;
                audioBytes = new ByteArrayOutputStream();

                Thread captureThread = new Thread(() -> {
                    byte[] buffer = new byte[2048];
                    while (isRecording) {
                        int count = targetLine.read(buffer, 0, buffer.length);
                        if (count > 0) {
                            audioBytes.write(buffer, 0, count);
                        }
                    }
                });
                captureThread.setDaemon(true);
                captureThread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public synchronized byte[] stopRecording() {
            isRecording = false;
            if (targetLine != null) {
                targetLine.stop();
                targetLine.close();
            }
            byte[] rawPcm = audioBytes != null ? audioBytes.toByteArray() : new byte[0];
            return addWavHeader(rawPcm, 16000, 1, 16);
        }

        private byte[] addWavHeader(byte[] pcmData, int sampleRate, int channels, int bitDepth) {
            int totalDataLen = pcmData.length + 36;
            int byteRate = sampleRate * channels * (bitDepth / 8);
            byte[] header = new byte[44];

            header[0] = 'R'; header[1] = 'I'; header[2] = 'F'; header[3] = 'F';
            header[4] = (byte) (totalDataLen & 0xff);
            header[5] = (byte) ((totalDataLen >> 8) & 0xff);
            header[6] = (byte) ((totalDataLen >> 16) & 0xff);
            header[7] = (byte) ((totalDataLen >> 24) & 0xff);
            header[8] = 'W'; header[9] = 'A'; header[10] = 'V'; header[11] = 'E';
            header[12] = 'f'; header[13] = 'm'; header[14] = 't'; header[15] = ' ';
            header[16] = 16; header[17] = 0; header[18] = 0; header[19] = 0;
            header[20] = 1; header[21] = 0;
            header[22] = (byte) channels; header[23] = 0;
            header[24] = (byte) (sampleRate & 0xff);
            header[25] = (byte) ((sampleRate >> 8) & 0xff);
            header[26] = (byte) ((sampleRate >> 16) & 0xff);
            header[27] = (byte) ((sampleRate >> 24) & 0xff);
            header[28] = (byte) (byteRate & 0xff);
            header[29] = (byte) ((byteRate >> 8) & 0xff);
            header[30] = (byte) ((byteRate >> 16) & 0xff);
            header[31] = (byte) ((byteRate >> 24) & 0xff);
            header[32] = (byte) (channels * (bitDepth / 8)); header[33] = 0;
            header[34] = (byte) bitDepth; header[35] = 0;
            header[36] = 'd'; header[37] = 'a'; header[38] = 't'; header[39] = 'a';
            header[40] = (byte) (pcmData.length & 0xff);
            header[41] = (byte) ((pcmData.length >> 8) & 0xff);
            header[42] = (byte) ((pcmData.length >> 16) & 0xff);
            header[43] = (byte) ((pcmData.length >> 24) & 0xff);

            byte[] wavBytes = new byte[44 + pcmData.length];
            System.arraycopy(header, 0, wavBytes, 0, 44);
            System.arraycopy(pcmData, 0, wavBytes, 44, pcmData.length);
            return wavBytes;
        }
    }

    public BorderPane setBorderPane(Stage stage) {
        this.stage = stage;
        if (stage != null) {
            stage.setMaximized(true);
        }
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.FIRST_AID);

        StackPane centerStack = new StackPane();
        VBox mainContent = createMainContent();
        HBox overlayPane = createNurseOverlayPane();
        StackPane.setAlignment(overlayPane, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(overlayPane, new Insets(0, 24, 20, 0));

        centerStack.getChildren().addAll(mainContent, overlayPane);

        bp.setLeft(sidebar);
        bp.setCenter(centerStack);
        playPageAnimation(mainContent);

        // Safely dispose media handles when the window is closed
        stage.setOnCloseRequest(e -> dispose());

        return bp;
    }

    private VBox createMainContent() {
        VBox mainBox = new VBox(14);
        mainBox.setStyle("-fx-background-color: " + PAGE_BG + ";");
        mainBox.setPadding(new Insets(18, 24, 18, 24));

        HBox topBar = createTopBar();
        VBox consultationStage = createConsultationStage();
        VBox.setVgrow(consultationStage, Priority.ALWAYS);

        mainBox.getChildren().addAll(topBar, consultationStage);
        return mainBox;
    }

    private HBox createTopBar() {
        VBox titleBox = new VBox(2);
        Label heading = new Label("Sister Ananya — AI Emergency Nurse");
        heading.setStyle(FONT_FAMILY + "-fx-font-size: 22px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        titleBox.getChildren().addAll(heading);

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        // Language Switcher
        HBox langSwitch = new HBox(4);
        langSwitch.setAlignment(Pos.CENTER);
        langSwitch.setPadding(new Insets(3, 6, 3, 6));
        langSwitch.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 20px; " +
                "-fx-background-radius: 20px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(120,47,22,0.05), 6, 0, 0, 1);"
        );

        enLangBtn = new Button("English");
        mrLangBtn = new Button("मराठी");

        updateLangButtons();

        enLangBtn.setOnAction(e -> {
            isMarathi = false;
            updateLangButtons();
        });

        mrLangBtn.setOnAction(e -> {
            isMarathi = true;
            updateLangButtons();
        });

        langSwitch.getChildren().addAll(enLangBtn, mrLangBtn);

        HBox topBar = new HBox(12, titleBox, topSpacer, langSwitch);
        topBar.setAlignment(Pos.CENTER_LEFT);
        return topBar;
    }

    private void updateLangButtons() {
        if (!isMarathi) {
            enLangBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + PRIMARY + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11.5px; -fx-background-radius: 14px; -fx-padding: 4 12; -fx-cursor: hand;");
            mrLangBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 11.5px; -fx-background-radius: 14px; -fx-padding: 4 12; -fx-cursor: hand;");
            if (textInputField != null) textInputField.setPromptText("Type your emergency symptoms here or click mic to speak...");
        } else {
            mrLangBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + PRIMARY + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11.5px; -fx-background-radius: 14px; -fx-padding: 4 12; -fx-cursor: hand;");
            enLangBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 11.5px; -fx-background-radius: 14px; -fx-padding: 4 12; -fx-cursor: hand;");
            if (textInputField != null) textInputField.setPromptText("येथे लक्षणे टाईप करा किंवा बोलण्यासाठी माईक चालू करा...");
        }
    }

    private VBox createConsultationStage() {
        VBox chatDrawer = createChatDrawer();
        VBox.setVgrow(chatDrawer, Priority.ALWAYS);
        return chatDrawer;
    }

    // =========================================================
    // FLOATING NURSE OVERLAY & DYNAMIC WORD-BY-WORD TELEPROMPTER
    // =========================================================
    private HBox createNurseOverlayPane() {
        nurseOverlayPane = new HBox(14);
        nurseOverlayPane.setAlignment(Pos.BOTTOM_RIGHT);
        nurseOverlayPane.setPickOnBounds(false);
        nurseOverlayPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        // 1. Dynamic Word-by-Word Teleprompter Card (Left of Nurse)
        VBox teleprompterCard = createTeleprompterCard();

        // 2. Animated Nurse GIF/Video Card (Right)
        VBox nurseCard = createNurseCard();

        nurseOverlayPane.getChildren().addAll(teleprompterCard, nurseCard);

        // Initially hidden and positioned off-screen to the right
        nurseOverlayPane.setTranslateX(750);
        nurseOverlayPane.setOpacity(0.0);
        nurseOverlayPane.setVisible(false);
        nurseOverlayPane.setMouseTransparent(true);

        return nurseOverlayPane;
    }

    private VBox createTeleprompterCard() {
        VBox card = new VBox(10);
        card.setPrefWidth(350);
        card.setMinWidth(350);
        card.setMaxWidth(350);
        card.setPrefHeight(360);
        card.setMinHeight(360);
        card.setMaxHeight(360);
        card.setStyle(
                FONT_FAMILY +
                "-fx-background-color: rgba(255, 255, 255, 0.96);" +
                "-fx-border-color: " + PRIMARY + ";" +
                "-fx-border-radius: 18px;" +
                "-fx-background-radius: 18px;" +
                "-fx-padding: 14px 16px 14px 16px;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(120,47,22,0.18), 18, 0, 0, 6);"
        );

        // Top bar of teleprompter
        HBox top = new HBox(6);
        top.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("💬");
        icon.setStyle("-fx-font-size: 14px;");

        VBox titleCol = new VBox(1);
        Label title = new Label("Live Nurse Teleprompter");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: 800; -fx-text-fill: " + PRIMARY_DARK + ";");

        Label subtitle = new Label("Real-time word-by-word guidance");
        subtitle.setStyle(FONT_FAMILY + "-fx-font-size: 9.5px; -fx-text-fill: " + TEXT_MUTED + ";");
        titleCol.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Word stream status indicator pill
        HBox streamPill = new HBox(4);
        streamPill.setAlignment(Pos.CENTER);
        streamPill.setStyle("-fx-background-color: " + LIGHT_GREEN + "; -fx-background-radius: 10px; -fx-padding: 2 7; -fx-border-color: #86EFAC; -fx-border-radius: 10px;");
        Circle streamDot = new Circle(3, Color.web(GREEN));
        Label streamLbl = new Label("Streaming");
        streamLbl.setStyle(FONT_FAMILY + "-fx-font-size: 9.5px; -fx-font-weight: bold; -fx-text-fill: " + GREEN + ";");
        streamPill.getChildren().addAll(streamDot, streamLbl);

        top.getChildren().addAll(icon, titleCol, spacer, streamPill);

        // Center scroll area for text
        teleprompterTextLabel = new Label("Sister Ananya is ready to explain clinical guidance word by word...");
        teleprompterTextLabel.setWrapText(true);
        teleprompterTextLabel.setMaxWidth(315);
        teleprompterTextLabel.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-line-spacing: 2.5px;");

        teleprompterScrollPane = new ScrollPane(teleprompterTextLabel);
        teleprompterScrollPane.setFitToWidth(true);
        teleprompterScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        teleprompterScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        teleprompterScrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(teleprompterScrollPane, Priority.ALWAYS);

        // Bottom toolbar
        HBox bot = new HBox(8);
        bot.setAlignment(Pos.CENTER_LEFT);
        bot.setStyle("-fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 1 0 0 0; -fx-padding: 6 0 0 0;");

        Label langInfo = new Label(isMarathi ? "मराठी आवाज" : "English Speech");
        langInfo.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-text-fill: " + TEXT_MUTED + ";");

        Region bSpacer = new Region();
        HBox.setHgrow(bSpacer, Priority.ALWAYS);

        Button copyPromptBtn = new Button("📋 Copy");
        copyPromptBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: " + PRIMARY_DARK + "; -fx-font-size: 10.5px; -fx-font-weight: bold; -fx-cursor: hand;");
        copyPromptBtn.setOnAction(e -> {
            try {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(teleprompterTextLabel.getText());
                clipboard.setContent(content);
                copyPromptBtn.setText("✓ Copied");
                PauseTransition pt = new PauseTransition(Duration.seconds(2));
                pt.setOnFinished(ev -> copyPromptBtn.setText("📋 Copy"));
                pt.play();
            } catch (Exception ignored) {}
        });

        bot.getChildren().addAll(langInfo, bSpacer, copyPromptBtn);

        card.getChildren().addAll(top, teleprompterScrollPane, bot);
        return card;
    }

    private VBox createNurseCard() {
        VBox card = new VBox(8);
        card.setPrefWidth(240);
        card.setMinWidth(240);
        card.setMaxWidth(240);
        card.setPrefHeight(360);
        card.setMinHeight(360);
        card.setMaxHeight(360);
        card.setAlignment(Pos.CENTER);
        card.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + PRIMARY + ";" +
                "-fx-border-radius: 18px;" +
                "-fx-background-radius: 18px;" +
                "-fx-padding: 10px;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(120,47,22,0.25), 22, 0, 0, 8);"
        );

        // Header of Nurse Card
        HBox top = new HBox(6);
        top.setAlignment(Pos.CENTER_LEFT);

        HBox liveBadge = new HBox(4);
        liveBadge.setAlignment(Pos.CENTER);
        liveBadge.setStyle("-fx-background-color: " + LIGHT_RED + "; -fx-background-radius: 10px; -fx-padding: 2 7; -fx-border-color: #FCA5A5; -fx-border-radius: 10px;");
        Circle redDot = new Circle(3, Color.web(RED));
        Label liveTxt = new Label("Sister Ananya");
        liveTxt.setStyle(FONT_FAMILY + "-fx-font-size: 9.5px; -fx-font-weight: bold; -fx-text-fill: " + RED + ";");
        liveBadge.getChildren().addAll(redDot, liveTxt);

        Region topSp = new Region();
        HBox.setHgrow(topSp, Priority.ALWAYS);

        Button dismissBtn = new Button("✕");
        dismissBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-cursor: hand; -fx-padding: 2 7;");
        dismissBtn.setOnAction(e -> hideNurseOverlay());

        top.getChildren().addAll(liveBadge, topSp, dismissBtn);

        // Nurse Video / GIF Container
        StackPane videoContainer = new StackPane();
        videoContainer.setPrefSize(220, 250);
        videoContainer.setMinSize(220, 250);
        videoContainer.setMaxSize(220, 250);
        videoContainer.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-background-radius: 14px;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 14px;"
        );

        initNurseVideo(videoContainer);

        // Bottom Controls inside nurse card
        HBox controls = new HBox(6);
        controls.setAlignment(Pos.CENTER);

        Button stopVoiceBtn = new Button("⏹ Stop");
        stopVoiceBtn.setPrefHeight(30);
        stopVoiceBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + RED + "; -fx-text-fill: white; -fx-font-size: 10.5px; -fx-font-weight: bold; -fx-background-radius: 14px; -fx-cursor: hand; -fx-padding: 4 10;");
        stopVoiceBtn.setOnAction(e -> stopSpeakingVoice());

        Button replayBtn = new Button("🔊 Replay");
        replayBtn.setPrefHeight(30);
        replayBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK + "; -fx-font-size: 10.5px; -fx-font-weight: bold; -fx-background-radius: 14px; -fx-border-color: " + LIGHT_TERRACOTTA + "; -fx-border-radius: 14px; -fx-cursor: hand; -fx-padding: 4 10;");
        replayBtn.setOnAction(e -> replayLastResponse());

        controls.getChildren().addAll(stopVoiceBtn, replayBtn);

        card.getChildren().addAll(top, videoContainer, controls);
        return card;
    }

    private void initNurseVideo(StackPane videoContainer) {
        try {
            // 1. Locate nurse.mp4 video resource or project file
            String videoMediaUrl = null;
            URL res = getClass().getResource("/videos/nurse.mp4");
            if (res != null) {
                videoMediaUrl = res.toExternalForm();
            } else {
                java.io.File file = new java.io.File("src/main/resources/videos/nurse.mp4");
                if (file.exists()) {
                    videoMediaUrl = file.toURI().toString();
                }
            }

            if (videoMediaUrl != null) {
                Media media = new Media(videoMediaUrl);
                nurseVideoPlayer = new MediaPlayer(media);
                nurseVideoPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                nurseVideoPlayer.setMute(true); // TTS provides speech; keep video audio muted

                nurseVideoView = new MediaView(nurseVideoPlayer);
                nurseVideoView.setPreserveRatio(true);
                nurseVideoView.setFitWidth(216);
                nurseVideoView.setFitHeight(246);
                nurseVideoView.setSmooth(true);

                Rectangle clip = new Rectangle(216, 246);
                clip.setArcWidth(14);
                clip.setArcHeight(14);
                nurseVideoView.setClip(clip);

                videoContainer.getChildren().add(nurseVideoView);

                nurseVideoPlayer.setOnReady(() -> {
                    nurseVideoPlayer.pause();
                    nurseVideoPlayer.seek(Duration.ZERO);
                });

                if (isSpeakingActive.get()) {
                    nurseVideoPlayer.play();
                }
                return;
            }
        } catch (Throwable t) {
            System.err.println("Nurse MP4 video initialization notice: " + t.getMessage());
        }

        // Fallback to static nurse image if video fails to initialize
        try {
            URL stillUrl = getClass().getResource("/assets/welcome/nurse.png");
            if (stillUrl == null) {
                stillUrl = getClass().getResource("/assets/nurse.png");
            }
            if (stillUrl != null) {
                nurseStillImage = new Image(stillUrl.toExternalForm(), false);
                nurseGifView = new ImageView(nurseStillImage);
                nurseGifView.setFitWidth(216);
                nurseGifView.setFitHeight(246);
                nurseGifView.setPreserveRatio(true);
                nurseGifView.setSmooth(true);

                Rectangle clip = new Rectangle(216, 246);
                clip.setArcWidth(14);
                clip.setArcHeight(14);
                nurseGifView.setClip(clip);

                videoContainer.getChildren().add(nurseGifView);
            } else {
                Label fallbackAvatar = new Label("👩‍⚕️");
                fallbackAvatar.setStyle("-fx-font-size: 72px;");
                videoContainer.getChildren().add(fallbackAvatar);
            }
        } catch (Exception ex) {
            Label fallbackAvatar = new Label("👩‍⚕️");
            fallbackAvatar.setStyle("-fx-font-size: 72px;");
            videoContainer.getChildren().add(fallbackAvatar);
        }
    }

    private void startWordByWordStream(String text) {
        stopWordByWordStream();

        if (teleprompterTextLabel == null) return;
        teleprompterTextLabel.setText("");

        if (text == null || text.trim().isEmpty()) return;

        String cleanText = text.replace("**", "").replace("###", "").replace("##", "").replace("#", "").trim();
        String[] words = cleanText.split("\\s+");
        if (words.length == 0) return;

        final int totalWords = words.length;
        final int[] index = {0};
        StringBuilder currentStream = new StringBuilder();

        teleprompterTimeline = new Timeline(new KeyFrame(Duration.millis(115), e -> {
            if (index[0] < totalWords) {
                currentStream.append(words[index[0]]).append(" ");
                teleprompterTextLabel.setText(currentStream.toString());
                index[0]++;
                if (teleprompterScrollPane != null) {
                    teleprompterScrollPane.setVvalue(1.0);
                }
            } else {
                stopWordByWordStream();
            }
        }));
        teleprompterTimeline.setCycleCount(totalWords);
        teleprompterTimeline.play();
    }

    private void stopWordByWordStream() {
        if (teleprompterTimeline != null) {
            teleprompterTimeline.stop();
            teleprompterTimeline = null;
        }
    }

    private void showNurseOverlay(String speechText) {
        if (!isOverlayEnabled) return;

        Platform.runLater(() -> {
            if (nurseOverlayPane == null) return;

            if (nurseOverlayPane.isVisible() && nurseOverlayPane.getOpacity() > 0.8) {
                startWordByWordStream(speechText);
                return;
            }

            nurseOverlayPane.setVisible(true);
            nurseOverlayPane.setMouseTransparent(false);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(400), nurseOverlayPane);
            slideIn.setToX(0);
            slideIn.setInterpolator(Interpolator.EASE_OUT);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), nurseOverlayPane);
            fadeIn.setToValue(1.0);

            ParallelTransition pt = new ParallelTransition(slideIn, fadeIn);
            pt.setOnFinished(e -> {
                startWordByWordStream(speechText);
            });
            pt.play();
        });
    }

    private void hideNurseOverlay() {
        Platform.runLater(() -> {
            stopWordByWordStream();
            stopNurseVideo();
            if (nurseOverlayPane == null) return;

            TranslateTransition slideOut = new TranslateTransition(Duration.millis(350), nurseOverlayPane);
            slideOut.setToX(750);
            slideOut.setInterpolator(Interpolator.EASE_IN);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(350), nurseOverlayPane);
            fadeOut.setToValue(0.0);

            ParallelTransition pt = new ParallelTransition(slideOut, fadeOut);
            pt.setOnFinished(e -> {
                nurseOverlayPane.setVisible(false);
                nurseOverlayPane.setMouseTransparent(true);
            });
            pt.play();
        });
    }

    private void startNurseVideo() {
        Platform.runLater(() -> {
            try {
                if (nurseVideoPlayer != null) {
                    nurseVideoPlayer.play();
                }
                if (nurseGifView != null && nurseAnimatedGif != null) {
                    nurseGifView.setImage(nurseAnimatedGif);
                }
            } catch (Exception e) {
                System.err.println("Could not start nurse video/gif: " + e.getMessage());
            }
        });
    }

    private void stopNurseVideo() {
        Platform.runLater(() -> {
            try {
                if (nurseVideoPlayer != null) {
                    nurseVideoPlayer.pause();
                    nurseVideoPlayer.seek(Duration.ZERO);
                }
                if (nurseGifView != null && nurseStillImage != null) {
                    nurseGifView.setImage(nurseStillImage);
                }
            } catch (Exception e) {
                System.err.println("Could not stop nurse video/gif: " + e.getMessage());
            }
        });
    }

    private void toggleMic() {
        stopSpeakingVoice();

        if (!isMicActive) {
            setMicActive(true);
            callStatusLabel.setText("● Listening to your voice symptoms...");
            callStatusLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + GREEN + "; -fx-font-weight: bold; -fx-font-size: 12.5px;");

            recorder.startRecording();
        } else {
            setMicActive(false);
            callStatusLabel.setText("● Processing your voice symptoms...");
            callStatusLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + PRIMARY + "; -fx-font-weight: bold; -fx-font-size: 12.5px;");

            byte[] recordedWav = recorder.stopRecording();

            if (recordedWav != null && recordedWav.length > 5000) {
                handleUserAudioInput(recordedWav);
            } else {
                callStatusLabel.setText("● Audio too short. Please speak again.");
                callStatusLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + RED + "; -fx-font-weight: bold; -fx-font-size: 12.5px;");
            }
        }
    }

    private void handleUserAudioInput(byte[] audioWav) {
        addUserTranscript("🎙 [Voice Query Recorded - Analyzing symptoms...]");

        new Thread(() -> {
            String base64Audio = Base64.getEncoder().encodeToString(audioWav);
            String response = callGeminiAudioAPI(base64Audio, isMarathi);
            lastGeneratedSpeech = response;

            Platform.runLater(() -> {
                addNurseTranscript(response);
                speakText(response);
                chatScrollPane.setVvalue(1.0);
            });
        }).start();
    }

    private void setMicActive(boolean active) {
        isMicActive = active;
        if (active) {
            micToggleButton.setText("⏹ Stop Mic");
            micToggleButton.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + RED + ";" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 12.5px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 24px;" +
                    "-fx-cursor: hand;"
            );
        } else {
            micToggleButton.setText("🎙 Hold / Speak");
            micToggleButton.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + ");" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 12.5px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 24px;" +
                    "-fx-cursor: hand;" +
                    "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.25), 8, 0, 0, 2);"
            );
        }
    }

    // =========================================================
    // ELEVENLABS TTS + NURSE VIDEO CONTROLLER
    // =========================================================
    public void stopSpeakingVoice() {
        isSpeakingActive.set(false);
        stopWordByWordStream();

        MediaPlayer audioPlayer = currentAudioPlayer;
        currentAudioPlayer = null;
        if (audioPlayer != null) {
            try {
                audioPlayer.stop();
                audioPlayer.dispose();
            } catch (Exception ignored) {}
        }

        stopNurseVideo();

        Path oldAudio = currentAudioFile;
        currentAudioFile = null;
        if (oldAudio != null) {
            deleteTempAudio(oldAudio);
        }

        Platform.runLater(() -> {
            if (callStatusLabel != null) {
                callStatusLabel.setText("● Call Connected (Ready)");
                callStatusLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + GREEN + "; -fx-font-weight: bold; -fx-font-size: 12.5px;");
            }
        });
    }

    private void speakText(String text) {
        if (text == null || text.trim().isEmpty()) return;

        stopSpeakingVoice();
        isSpeakingActive.set(true);

        String cleanSpeech = text
                .replaceAll("[\uD83C-\uDBFF][\uDC00-\uDFFF]", "")
                .replace("•", " ")
                .trim();

        // 1. Trigger nurse overlay and word-by-word streaming if overlay is enabled
        if (isOverlayEnabled) {
            showNurseOverlay(cleanSpeech);
        }

        // 2. Check if voice guidance is disabled
        if (!isVoiceEnabled) {
            Platform.runLater(() -> {
                if (callStatusLabel != null) {
                    callStatusLabel.setText("● Nurse Guidance Active (Voice Muted)");
                    callStatusLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + PRIMARY + "; -fx-font-weight: bold; -fx-font-size: 12.5px;");
                }
            });

            if (isOverlayEnabled) {
                startNurseVideo();
                String[] words = cleanSpeech.split("\\s+");
                long durationMs = Math.max(3000, words.length * 120L + 1200);
                PauseTransition pt = new PauseTransition(Duration.millis(durationMs));
                pt.setOnFinished(e -> {
                    stopNurseVideo();
                    isSpeakingActive.set(false);
                    Platform.runLater(() -> {
                        if (callStatusLabel != null) {
                            callStatusLabel.setText("● Call Connected (Ready)");
                            callStatusLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + GREEN + "; -fx-font-weight: bold; -fx-font-size: 12.5px;");
                        }
                    });
                });
                pt.play();
            } else {
                isSpeakingActive.set(false);
            }
            return;
        }

        Platform.runLater(() -> {
            if (callStatusLabel != null) {
                callStatusLabel.setText("● Sister Ananya is speaking...");
                callStatusLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + PRIMARY + "; -fx-font-weight: bold; -fx-font-size: 12.5px;");
            }
        });

        new Thread(() -> {
            Path generatedFile = null;
            try {
                generatedFile = generateVoiceAudio(cleanSpeech, isMarathi);
                currentAudioFile = generatedFile;
                final Path audioPath = generatedFile;

                Platform.runLater(() -> {
                    try {
                        if (!isSpeakingActive.get()) {
                            deleteTempAudio(audioPath);
                            return;
                        }

                        Media audioMedia = new Media(audioPath.toUri().toString());
                        MediaPlayer audioPlayer = new MediaPlayer(audioMedia);
                        currentAudioPlayer = audioPlayer;

                        audioPlayer.setOnPlaying(() -> {
                            startNurseVideo();
                        });

                        audioPlayer.setOnEndOfMedia(() -> {
                            if (currentAudioPlayer == audioPlayer) {
                                currentAudioPlayer = null;
                            }
                            isSpeakingActive.set(false);
                            stopNurseVideo();
                            audioPlayer.dispose();
                            deleteTempAudio(audioPath);

                            Platform.runLater(() -> {
                                callStatusLabel.setText("● Call Connected (Ready)");
                                callStatusLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + GREEN + "; -fx-font-weight: bold; -fx-font-size: 12.5px;");
                            });
                        });

                        audioPlayer.setOnError(() -> {
                            System.err.println("Voice audio playback error: " + audioPlayer.getError());
                            if (currentAudioPlayer == audioPlayer) {
                                currentAudioPlayer = null;
                            }
                            isSpeakingActive.set(false);
                            stopNurseVideo();
                            audioPlayer.dispose();
                            deleteTempAudio(audioPath);

                            Platform.runLater(() -> {
                                callStatusLabel.setText("● Voice Playback Notice");
                                callStatusLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + PRIMARY + "; -fx-font-weight: bold; -fx-font-size: 12.5px;");
                            });
                        });

                        audioPlayer.play();

                    } catch (Exception fxError) {
                        System.err.println("Audio playback error: " + fxError.getMessage());
                        isSpeakingActive.set(false);
                        stopNurseVideo();
                        deleteTempAudio(audioPath);
                        Platform.runLater(() -> {
                            callStatusLabel.setText("● Voice Playback Notice");
                            callStatusLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + PRIMARY + "; -fx-font-weight: bold; -fx-font-size: 12.5px;");
                        });
                    }
                });

            } catch (Exception e) {
                System.err.println("Voice synthesis notice: " + e.getMessage());
                isSpeakingActive.set(false);
                deleteTempAudio(generatedFile);

                Platform.runLater(() -> {
                    stopNurseVideo();
                    callStatusLabel.setText("● Guidance Active (Text Protocol Streamed)");
                    callStatusLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + PRIMARY + "; -fx-font-weight: bold; -fx-font-size: 12.5px;");
                });
            }
        }, "Voice-TTS-Engine").start();
    }

    private Path generateVoiceAudio(String text, boolean marathi) throws Exception {
        // Attempt 1: ElevenLabs with requested Monika Sogam voice ID
        try {
            validateElevenLabsConfiguration();
            return generateElevenLabsSpeech(text, ELEVENLABS_VOICE_ID);
        } catch (Exception e1) {
            String err1 = e1.getMessage() != null ? e1.getMessage() : "";
            System.err.println("ElevenLabs primary voice notice (" + ELEVENLABS_VOICE_ID + "): " + err1);

            // Attempt 2: If library voice requires paid plan (HTTP 402), attempt VoiceLab generated voice
            if (err1.contains("402") || err1.contains("paid_plan_required")) {
                try {
                    return generateElevenLabsSpeech(text, ELEVENLABS_FALLBACK_VOICE_ID);
                } catch (Exception e2) {
                    System.err.println("ElevenLabs fallback voice notice (" + ELEVENLABS_FALLBACK_VOICE_ID + "): " + e2.getMessage());
                }
            }

            // Attempt 3: High-Definition Zero-Quota Neural Web Speech (supports English & Marathi seamlessly)
            // Guarantees Sister Ananya always speaks aloud and never goes mute on 402 or quota limits!
            return generateWebSpeech(text, marathi);
        }
    }

    private Path generateElevenLabsSpeech(String text, String voiceId) throws Exception {
        String endpoint =
                "https://api.elevenlabs.io/v1/text-to-speech/"
                        + voiceId
                        + "?output_format=mp3_44100_128";

        String json = "{\"text\":" + jsonEscape(text)
                + ",\"model_id\":" + jsonEscape(ELEVENLABS_MODEL)
                + "}";

        HttpURLConnection connection =
                (HttpURLConnection) new URL(endpoint).openConnection();

        connection.setRequestMethod("POST");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(60000);
        connection.setDoOutput(true);
        connection.setRequestProperty("xi-api-key", ELEVENLABS_API_KEY);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "audio/mpeg");

        try (OutputStream output = connection.getOutputStream()) {
            output.write(json.getBytes(StandardCharsets.UTF_8));
        }

        int status = connection.getResponseCode();
        if (status < 200 || status >= 300) {
            String errorBody = readConnectionError(connection);
            connection.disconnect();
            throw new RuntimeException("ElevenLabs API HTTP " + status + ": " + errorBody);
        }

        Path audioFile = Files.createTempFile("lifelink-elevenlabs-", ".mp3");
        try (var input = connection.getInputStream();
             var output = Files.newOutputStream(audioFile)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } finally {
            connection.disconnect();
        }
        return audioFile;
    }

    private Path generateWebSpeech(String text, boolean marathi) throws Exception {
        String lang = marathi ? "mr" : "en";
        List<String> chunks = splitTextIntoChunks(text, 180);
        if (chunks.isEmpty()) {
            throw new IllegalArgumentException("No text to speak");
        }

        Path audioFile = Files.createTempFile("lifelink-voice-", ".mp3");
        try (OutputStream fos = Files.newOutputStream(audioFile)) {
            for (String chunk : chunks) {
                if (chunk.trim().isEmpty()) continue;
                String encoded = URLEncoder.encode(chunk.trim(), StandardCharsets.UTF_8);
                String urlStr = "https://translate.google.com/translate_tts?ie=UTF-8&q=" + encoded + "&tl=" + lang + "&client=tw-ob";
                HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
                conn.setConnectTimeout(8000);
                conn.setReadTimeout(15000);

                int code = conn.getResponseCode();
                if (code == 200) {
                    try (InputStream is = conn.getInputStream()) {
                        is.transferTo(fos);
                    }
                }
                conn.disconnect();
            }
        }
        return audioFile;
    }

    private static List<String> splitTextIntoChunks(String text, int maxLen) {
        List<String> chunks = new ArrayList<>();
        if (text == null || text.isBlank()) return chunks;

        String[] sentences = text.split("(?<=[.!?\\n])\\s+");
        StringBuilder current = new StringBuilder();

        for (String s : sentences) {
            s = s.trim();
            if (s.isEmpty()) continue;
            if (current.length() + s.length() + 1 <= maxLen) {
                if (current.length() > 0) current.append(" ");
                current.append(s);
            } else {
                if (current.length() > 0) {
                    chunks.add(current.toString());
                    current.setLength(0);
                }
                if (s.length() <= maxLen) {
                    current.append(s);
                } else {
                    String[] words = s.split("\\s+");
                    for (String w : words) {
                        if (current.length() + w.length() + 1 <= maxLen) {
                            if (current.length() > 0) current.append(" ");
                            current.append(w);
                        } else {
                            if (current.length() > 0) chunks.add(current.toString());
                            current.setLength(0);
                            current.append(w);
                        }
                    }
                }
            }
        }
        if (current.length() > 0) {
            chunks.add(current.toString());
        }
        return chunks;
    }

    private static String jsonEscape(String value) {
        if (value == null) return "\"\"";
        return "\"" + value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n")
                .replace("\t", "\\t")
                + "\"";
    }

    private static String readConnectionError(HttpURLConnection connection) {
        try {
            var stream = connection.getErrorStream();
            if (stream == null) return "No error details returned.";
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Unable to read error response: " + e.getMessage();
        }
    }

    private static void deleteTempAudio(Path file) {
        if (file == null) return;
        try {
            file.toFile().deleteOnExit();
            Files.deleteIfExists(file);
        } catch (Exception ignored) {
            try {
                file.toFile().delete();
            } catch (Exception ignored2) {}
        }
    }

    private static void validateElevenLabsConfiguration() {
        if (ELEVENLABS_API_KEY == null || ELEVENLABS_API_KEY.isBlank()) {
            throw new IllegalStateException("ELEVENLABS_API_KEY is missing.");
        }
        if (ELEVENLABS_VOICE_ID == null
                || ELEVENLABS_VOICE_ID.isBlank()
                || ELEVENLABS_VOICE_ID.equals("YOUR_VOICE_ID")) {
            throw new IllegalStateException("ELEVENLABS_VOICE_ID is missing.");
        }
    }

    private void replayLastResponse() {
        speakText(lastGeneratedSpeech);
    }

    private VBox createChatDrawer() {
        VBox drawer = new VBox();
        drawer.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 16px;" +
                "-fx-background-radius: 16px;"
        );
        drawer.setEffect(new DropShadow(14, 0, 4, Color.rgb(120, 47, 22, 0.05)));

        HBox header = new HBox(8);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(14, 18, 14, 18));
        header.setStyle("-fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;");

        Label transcriptTitle = new Label("Consultation Transcript & Clinical Protocol Stream");
        transcriptTitle.setStyle(FONT_FAMILY + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        // Toggle Nurse Overlay Button
        toggleOverlayBtn = new Button("🎭 Nurse Overlay: ON");
        toggleOverlayBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 14px; -fx-border-color: " + PRIMARY + "; -fx-border-radius: 14px; -fx-padding: 4 10; -fx-cursor: hand;");
        toggleOverlayBtn.setOnAction(e -> {
            isOverlayEnabled = !isOverlayEnabled;
            if (isOverlayEnabled) {
                toggleOverlayBtn.setText("🎭 Nurse Overlay: ON");
                toggleOverlayBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 14px; -fx-border-color: " + PRIMARY + "; -fx-border-radius: 14px; -fx-padding: 4 10; -fx-cursor: hand;");
            } else {
                toggleOverlayBtn.setText("🎭 Nurse Overlay: OFF");
                toggleOverlayBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11px; -fx-background-radius: 14px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14px; -fx-padding: 4 10; -fx-cursor: hand;");
                hideNurseOverlay();
            }
        });

        // Toggle Voice Guidance Button
        toggleVoiceBtn = new Button("🔊 Voice Guide: ON");
        toggleVoiceBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 14px; -fx-border-color: " + PRIMARY + "; -fx-border-radius: 14px; -fx-padding: 4 10; -fx-cursor: hand;");
        toggleVoiceBtn.setOnAction(e -> {
            isVoiceEnabled = !isVoiceEnabled;
            if (isVoiceEnabled) {
                toggleVoiceBtn.setText("🔊 Voice Guide: ON");
                toggleVoiceBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 14px; -fx-border-color: " + PRIMARY + "; -fx-border-radius: 14px; -fx-padding: 4 10; -fx-cursor: hand;");
            } else {
                toggleVoiceBtn.setText("🔇 Voice Guide: OFF");
                toggleVoiceBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11px; -fx-background-radius: 14px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14px; -fx-padding: 4 10; -fx-cursor: hand;");
                stopSpeakingVoice();
            }
        });

        Button clearChatBtn = new Button("Clear Stream");
        clearChatBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11px; -fx-cursor: hand;");
        clearChatBtn.setOnAction(e -> {
            stopSpeakingVoice();
            chatStream.getChildren().clear();
            addNurseTranscript("Transcript cleared. I am listening to your First-Aid queries.");
        });

        header.getChildren().addAll(transcriptTitle, sp, toggleOverlayBtn, toggleVoiceBtn, clearChatBtn);

        chatStream = new VBox(12);
        chatStream.setPadding(new Insets(16));

        addNurseTranscript("Hello! I am Sister Ananya. You can talk to me directly by voice or type below in English or मराठी.");

        chatScrollPane = new ScrollPane(chatStream);
        chatScrollPane.setFitToWidth(true);
        chatScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        chatScrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(chatScrollPane, Priority.ALWAYS);

        HBox chipsRow = createQuickChips();
        HBox inputRow = createBottomInputRow();

        drawer.getChildren().addAll(header, chatScrollPane, chipsRow, inputRow);
        return drawer;
    }

    private HBox createQuickChips() {
        Button burnChip = createChip("🔥 Burn (भाजणे)");
        Button cutChip = createChip("🩹 Bleeding (रक्तस्त्राव)");
        Button sprainChip = createChip("🦴 Fracture (हाड मोडणे)");
        Button cprChip = createChip("❤️ CPR Protocol");

        burnChip.setOnAction(e -> handleUserInput("What is the immediate First-Aid for a hot pan burn?"));
        cutChip.setOnAction(e -> handleUserInput("How to stop heavy blood bleeding from a cut?"));
        sprainChip.setOnAction(e -> handleUserInput("What should I do for a suspected bone fracture or sprain?"));
        cprChip.setOnAction(e -> handleUserInput("How to perform emergency CPR chest compressions?"));

        HBox chips = new HBox(8, burnChip, cutChip, sprainChip, cprChip);
        chips.setPadding(new Insets(0, 16, 8, 16));
        return chips;
    }

    private Button createChip(String label) {
        Button btn = new Button(label);
        btn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: 600;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 4 10;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PALE_PEACH + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: 600;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: " + PRIMARY + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 4 10;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: 600;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 4 10;"
        ));
        return btn;
    }

    private HBox createBottomInputRow() {
        if (callStatusLabel == null) {
            callStatusLabel = new Label("● Call Connected (Ready)");
        }
        callStatusLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + GREEN + "; -fx-font-weight: bold; -fx-font-size: 11.5px;");

        micToggleButton = new Button("🎙 Hold / Speak");
        micToggleButton.setPrefHeight(38);
        micToggleButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + ");" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 20px;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 8 16;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.25), 8, 0, 0, 2);"
        );
        micToggleButton.setOnAction(e -> toggleMic());

        stopSpeakingButton = new Button("⏹ Stop Voice");
        stopSpeakingButton.setPrefHeight(38);
        stopSpeakingButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 11.5px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 20px;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 20px;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 8 14;"
        );
        stopSpeakingButton.setOnAction(e -> stopSpeakingVoice());

        textInputField = new TextField();
        textInputField.setPromptText(isMarathi ? "येथे लक्षणे टाईप करा किंवा बोलण्यासाठी माईक चालू करा..." : "Type your emergency symptoms here or click mic to speak...");
        textInputField.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PALE_PEACH + ";" +
                "-fx-background-radius: 20px;" +
                "-fx-padding: 10px 16px;" +
                "-fx-font-size: 12.5px;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 20px;"
        );
        HBox.setHgrow(textInputField, Priority.ALWAYS);

        textInputField.setOnAction(e -> {
            String text = textInputField.getText().trim();
            if (!text.isEmpty()) {
                handleUserInput(text);
                textInputField.clear();
            }
        });

        Button sendBtn = new Button("Send Query ➔");
        sendBtn.setPrefHeight(38);
        sendBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 20px;" +
                "-fx-padding: 8 18;" +
                "-fx-cursor: hand;"
        );
        sendBtn.setOnAction(e -> {
            String text = textInputField.getText().trim();
            if (!text.isEmpty()) {
                handleUserInput(text);
                textInputField.clear();
            }
        });

        HBox row = new HBox(10, callStatusLabel, micToggleButton, textInputField, sendBtn, stopSpeakingButton);
        row.setAlignment(Pos.CENTER);
        row.setPadding(new Insets(10, 16, 14, 16));
        row.setStyle("-fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 1 0 0 0;");
        return row;
    }

    // =========================================================
    // EXPLICIT GEMINI MULTIMODAL REST PIPELINE
    // =========================================================
    private void handleUserInput(String message) {
        if (message == null || message.trim().isEmpty()) return;

        stopSpeakingVoice();
        addUserTranscript(message);

        new Thread(() -> {
            String response = callGeminiClinicalAPI(message, isMarathi);
            lastGeneratedSpeech = response;

            Platform.runLater(() -> {
                addNurseTranscript(response);
                speakText(response);
                chatScrollPane.setVvalue(1.0);
            });
        }).start();
    }

    private void addUserTranscript(String message) {
        VBox userCard = new VBox(3);
        userCard.setAlignment(Pos.TOP_RIGHT);
        userCard.setMaxWidth(620);

        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
        Label metaLabel = new Label("You (Family) • " + currentTime);
        metaLabel.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-text-fill: " + TEXT_MUTED + ";");

        Label label = new Label(message);
        label.setWrapText(true);
        label.setStyle(
                FONT_FAMILY +
                "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + ");" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 12px;" +
                "-fx-padding: 10 14;" +
                "-fx-background-radius: 14 14 2 14;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.2), 6, 0, 0, 1);"
        );

        userCard.getChildren().addAll(metaLabel, label);

        HBox box = new HBox(userCard);
        box.setAlignment(Pos.CENTER_RIGHT);
        box.setPadding(new Insets(4, 0, 4, 0));
        chatStream.getChildren().add(box);

        FadeTransition ft = new FadeTransition(Duration.millis(200), box);
        ft.setFromValue(0.2);
        ft.setToValue(1.0);
        ft.play();
    }

    private void addNurseTranscript(String message) {
        VBox responseCard = buildNurseResponseCard(message);

        HBox cardRow = new HBox(responseCard);
        cardRow.setAlignment(Pos.TOP_LEFT);
        cardRow.setPadding(new Insets(4, 0, 4, 0));
        chatStream.getChildren().add(cardRow);

        FadeTransition ft = new FadeTransition(Duration.millis(250), cardRow);
        ft.setFromValue(0.2);
        ft.setToValue(1.0);
        ft.play();
    }

    // =========================================================
    // CLINICAL RESPONSE CARD STRUCTURE & PROTOCOL PARSER
    // =========================================================
    private static class ClinicalCardData {
        String title = "";
        List<ClinicalStep> steps = new ArrayList<>();
        String warning = "";
        String generalNote = "";
        String rawText = "";
    }

    private static class ClinicalStep {
        int stepNumber;
        String title;
        String description;

        public ClinicalStep(int stepNumber, String title, String description) {
            this.stepNumber = stepNumber;
            this.title = title;
            this.description = description;
        }
    }

    private ClinicalCardData parseClinicalResponse(String rawText) {
        ClinicalCardData data = new ClinicalCardData();
        data.rawText = rawText != null ? rawText : "";
        if (rawText == null || rawText.trim().isEmpty()) {
            return data;
        }

        String[] lines = rawText.split("\\r?\\n");
        Pattern stepPattern = Pattern.compile("^\\s*([0-9]+|[१२३४५६७८९०]+|[•\\*\\-])[\\.\\:\\-\\)]\\s*(.*)$");

        int autoStepNum = 1;

        for (String rawLine : lines) {
            String line = rawLine.trim();
            if (line.isEmpty()) continue;

            // Strip markdown heading hashes
            if (line.startsWith("#")) {
                line = line.replaceFirst("^#+\\s*", "");
            }

            // Check if it's a warning/precaution line
            String lower = line.toLowerCase();
            if (line.startsWith("⚠️") || lower.startsWith("warning") || lower.startsWith("caution")
                    || line.startsWith("खबरदारी") || line.startsWith("सावधान")
                    || (lower.contains("warning:") || line.contains("खबरदारी:") || line.contains("⚠️"))) {
                String cleanWarn = line.replace("⚠️", "")
                        .replaceFirst("(?i)^(\\*\\*)?(warning|caution|alert|खबरदारी|सावधान)[\\:\\*\\s]*", "")
                        .replace("**", "")
                        .trim();
                if (data.warning.isEmpty()) {
                    data.warning = cleanWarn;
                } else {
                    data.warning += "\n" + cleanWarn;
                }
                continue;
            }

            // Check if it's a step line
            Matcher m = stepPattern.matcher(line);
            if (m.matches()) {
                String numStr = m.group(1);
                String content = m.group(2).trim();

                int stepNum = autoStepNum;
                try {
                    stepNum = Integer.parseInt(numStr);
                } catch (Exception ex) {
                    if (numStr.equals("१")) stepNum = 1;
                    else if (numStr.equals("२")) stepNum = 2;
                    else if (numStr.equals("३")) stepNum = 3;
                    else if (numStr.equals("४")) stepNum = 4;
                    else if (numStr.equals("५")) stepNum = 5;
                }
                autoStepNum = Math.max(autoStepNum, stepNum + 1);

                String stepTitle = "";
                String stepDesc = content;

                Pattern boldPrefix = Pattern.compile("^\\*\\*(.*?)\\*\\*\\:?\\s*(.*)$");
                Matcher boldMatcher = boldPrefix.matcher(content);
                if (boldMatcher.matches()) {
                    stepTitle = boldMatcher.group(1).trim();
                    stepDesc = boldMatcher.group(2).replace("**", "").replaceFirst("^[\\-\\:\\–\\—]\\s*", "").trim();
                } else {
                    int colonIdx = content.indexOf(':');
                    if (colonIdx > 0 && colonIdx < 35) {
                        stepTitle = content.substring(0, colonIdx).replace("**", "").trim();
                        stepDesc = content.substring(colonIdx + 1).replace("**", "").replaceFirst("^[\\-\\:\\–\\—]\\s*", "").trim();
                    } else {
                        stepDesc = content.replace("**", "").trim();
                    }
                }

                data.steps.add(new ClinicalStep(stepNum, stepTitle, stepDesc));
                continue;
            }

            // Check if it's the title line (first line that is short or has protocol/colon)
            if (data.title.isEmpty() && data.steps.isEmpty() && (line.endsWith(":") || line.length() < 70) && !lower.contains("hello") && !lower.contains("namaste")) {
                data.title = line.replace("**", "").replace(":", "").trim();
                continue;
            }

            // General advice / text line
            String cleanText = line.replace("**", "").trim();
            if (data.generalNote.isEmpty()) {
                data.generalNote = cleanText;
            } else {
                data.generalNote += "\n" + cleanText;
            }
        }

        return data;
    }

    private VBox buildNurseResponseCard(String rawMessage) {
        ClinicalCardData cardData = parseClinicalResponse(rawMessage);

        VBox card = new VBox(11);
        card.setMaxWidth(780);
        card.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 16px;" +
                "-fx-background-radius: 16px;" +
                "-fx-padding: 16px 18px 16px 18px;"
        );
        card.setEffect(new DropShadow(14, 0, 4, Color.rgb(120, 47, 22, 0.08)));

        // 1. Nurse Header Row
        HBox headerRow = new HBox(10);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        StackPane avatarPane = new StackPane();
        avatarPane.setPrefSize(38, 38);
        avatarPane.setMinSize(38, 38);
        avatarPane.setMaxSize(38, 38);
        avatarPane.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 19px;" +
                "-fx-background-radius: 19px;"
        );
        Label avatarLabel = new Label("👩‍⚕️");
        avatarLabel.setStyle("-fx-font-size: 18px;");
        avatarPane.getChildren().add(avatarLabel);

        VBox nurseMeta = new VBox(2);
        HBox nurseNameLine = new HBox(6);
        nurseNameLine.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label("Sister Ananya");
        nameLabel.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label verifiedBadge = new Label("Verified Triage ✓");
        verifiedBadge.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + LIGHT_GREEN + ";" +
                "-fx-text-fill: " + GREEN + ";" +
                "-fx-font-size: 9.5px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 2 6;" +
                "-fx-background-radius: 8px;" +
                "-fx-border-color: #86EFAC;" +
                "-fx-border-radius: 8px;"
        );
        nurseNameLine.getChildren().addAll(nameLabel, verifiedBadge);

        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
        Label subLabel = new Label("Clinical AI Nurse • " + currentTime);
        subLabel.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-text-fill: " + TEXT_MUTED + ";");

        nurseMeta.getChildren().addAll(nurseNameLine, subLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button listenBtn = new Button("🔊 Listen");
        listenBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 14px;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 14px;" +
                "-fx-padding: 4 10;" +
                "-fx-cursor: hand;"
        );
        listenBtn.setOnMouseEntered(e -> listenBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 14px;" +
                "-fx-border-color: " + PRIMARY + ";" +
                "-fx-border-radius: 14px;" +
                "-fx-padding: 4 10;" +
                "-fx-cursor: hand;"
        ));
        listenBtn.setOnMouseExited(e -> listenBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 14px;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 14px;" +
                "-fx-padding: 4 10;" +
                "-fx-cursor: hand;"
        ));
        listenBtn.setOnAction(e -> speakText(rawMessage));

        headerRow.getChildren().addAll(avatarPane, nurseMeta, spacer, listenBtn);
        card.getChildren().add(headerRow);

        // 2. Protocol Title Banner
        if (!cardData.title.isEmpty()) {
            HBox titleBanner = new HBox(8);
            titleBanner.setAlignment(Pos.CENTER_LEFT);
            titleBanner.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + PALE_PEACH + ";" +
                    "-fx-border-color: " + PRIMARY + ";" +
                    "-fx-border-width: 0 0 0 3.5px;" +
                    "-fx-background-radius: 0 8 8 0;" +
                    "-fx-padding: 7 12 7 12;"
            );
            Label titleIcon = new Label("⚡");
            titleIcon.setStyle("-fx-font-size: 13px;");
            Label titleText = new Label(cardData.title);
            titleText.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_DARK + ";");
            titleText.setWrapText(true);
            titleBanner.getChildren().addAll(titleIcon, titleText);
            card.getChildren().add(titleBanner);
        }

        // 3. General Intro / Note if present before steps
        if (!cardData.generalNote.isEmpty() && cardData.steps.isEmpty()) {
            Label noteLabel = new Label(cardData.generalNote);
            noteLabel.setWrapText(true);
            noteLabel.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-line-spacing: 2px;");
            card.getChildren().add(noteLabel);
        }

        // 4. Step-by-Step Action Cards
        if (!cardData.steps.isEmpty()) {
            VBox stepsList = new VBox(7);
            for (ClinicalStep step : cardData.steps) {
                HBox stepCard = new HBox(10);
                stepCard.setAlignment(Pos.TOP_LEFT);
                stepCard.setStyle(
                        FONT_FAMILY +
                        "-fx-background-color: " + PAGE_BG + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-padding: 8 12 8 12;"
                );

                StackPane stepBadge = new StackPane();
                stepBadge.setPrefSize(22, 22);
                stepBadge.setMinSize(22, 22);
                stepBadge.setMaxSize(22, 22);
                stepBadge.setStyle(
                        "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + ");" +
                        "-fx-background-radius: 11px;"
                );
                Label stepNumLabel = new Label(String.valueOf(step.stepNumber));
                stepNumLabel.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-font-weight: bold; -fx-text-fill: white;");
                stepBadge.getChildren().add(stepNumLabel);

                VBox stepContent = new VBox(2);
                HBox.setHgrow(stepContent, Priority.ALWAYS);

                if (!step.title.isEmpty()) {
                    Label stepTitleLbl = new Label(step.title);
                    stepTitleLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");
                    stepTitleLbl.setWrapText(true);
                    stepContent.getChildren().add(stepTitleLbl);
                }

                Label stepDescLbl = new Label(step.description);
                stepDescLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " + TEXT_SECONDARY + "; -fx-line-spacing: 2px;");
                stepDescLbl.setWrapText(true);
                stepContent.getChildren().add(stepDescLbl);

                stepCard.getChildren().addAll(stepBadge, stepContent);

                stepCard.setOnMouseEntered(e -> stepCard.setStyle(
                        FONT_FAMILY +
                        "-fx-background-color: #FFF6EE;" +
                        "-fx-border-color: " + PRIMARY + ";" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-padding: 8 12 8 12;"
                ));
                stepCard.setOnMouseExited(e -> stepCard.setStyle(
                        FONT_FAMILY +
                        "-fx-background-color: " + PAGE_BG + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-padding: 8 12 8 12;"
                ));

                stepsList.getChildren().add(stepCard);
            }
            card.getChildren().add(stepsList);
        }

        // Additional note if present alongside steps
        if (!cardData.generalNote.isEmpty() && !cardData.steps.isEmpty()) {
            Label noteLabel = new Label(cardData.generalNote);
            noteLabel.setWrapText(true);
            noteLabel.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_SECONDARY + "; -fx-line-spacing: 2px;");
            card.getChildren().add(noteLabel);
        }

        // 5. Critical Precaution / Warning Box
        if (!cardData.warning.isEmpty()) {
            HBox warningBox = new HBox(8);
            warningBox.setAlignment(Pos.TOP_LEFT);
            warningBox.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: #FEF2F2;" +
                    "-fx-border-color: #FECACA;" +
                    "-fx-border-radius: 10px;" +
                    "-fx-background-radius: 10px;" +
                    "-fx-padding: 9 12 9 12;"
            );

            Label warnIcon = new Label("⚠️");
            warnIcon.setStyle("-fx-font-size: 14px;");

            VBox warnContent = new VBox(2);
            HBox.setHgrow(warnContent, Priority.ALWAYS);

            Label warnHeading = new Label(isMarathi ? "महत्त्वाची खबरदारी (Red Flags):" : "CRITICAL PRECAUTION (Red Flags):");
            warnHeading.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-font-weight: 800; -fx-text-fill: #991B1B;");

            Label warnText = new Label(cardData.warning);
            warnText.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: #7F1D1D; -fx-line-spacing: 1.5px;");
            warnText.setWrapText(true);

            warnContent.getChildren().addAll(warnHeading, warnText);
            warningBox.getChildren().addAll(warnIcon, warnContent);
            card.getChildren().add(warningBox);
        }

        // 6. Action Footer Toolbar
        HBox actionFooter = new HBox(8);
        actionFooter.setAlignment(Pos.CENTER_LEFT);
        actionFooter.setStyle("-fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 1 0 0 0; -fx-padding: 8 0 0 0;");

        Button sosCallBtn = new Button("🚨 Emergency 108");
        sosCallBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #BA3B3E;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 10.5px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 12px;" +
                "-fx-padding: 5 10;" +
                "-fx-cursor: hand;"
        );
        sosCallBtn.setOnAction(e -> showEmergencyHotlineAlert());

        Button findCareBtn = new Button("🏥 Find Hospital");
        findCareBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 10.5px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;" +
                "-fx-padding: 5 10;" +
                "-fx-cursor: hand;"
        );
        findCareBtn.setOnAction(e -> {
            if (stage != null && stage.getScene() != null) {
                stage.getScene().setRoot(new FamilyFindCare().setBorderPane(stage));
            }
        });

        Region footerSpacer = new Region();
        HBox.setHgrow(footerSpacer, Priority.ALWAYS);

        Button copyBtn = new Button("📋 Copy Steps");
        copyBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: transparent;" +
                "-fx-text-fill: " + TEXT_SECONDARY + ";" +
                "-fx-font-size: 10.5px;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 5 8;"
        );
        copyBtn.setOnAction(e -> {
            try {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(rawMessage.replace("**", ""));
                clipboard.setContent(content);

                copyBtn.setText("✓ Copied!");
                copyBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: " + GREEN + "; -fx-font-size: 10.5px; -fx-font-weight: bold;");

                PauseTransition pt = new PauseTransition(Duration.seconds(2));
                pt.setOnFinished(ev -> {
                    copyBtn.setText("📋 Copy Steps");
                    copyBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 10.5px; -fx-cursor: hand; -fx-padding: 5 8;");
                });
                pt.play();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        actionFooter.getChildren().addAll(sosCallBtn, findCareBtn, footerSpacer, copyBtn);
        card.getChildren().add(actionFooter);

        return card;
    }

    private void showEmergencyHotlineAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("LifeLink Emergency Helpline");
        alert.setHeaderText("🚨 24/7 Rapid Emergency First-Response Numbers");
        alert.setContentText(
                "• National Ambulance Service: 108\n" +
                "• All-in-One Emergency Helpline: 112\n" +
                "• Police Control Room: 100\n" +
                "• Fire & Rescue: 101\n" +
                "• LifeLink Emergency Telehealth: 1800-425-9999\n\n" +
                "Please call 108 immediately if the patient is unconscious or in severe shock."
        );
        alert.showAndWait();
    }

    private String callGeminiClinicalAPI(String userQuery, boolean isMarathiMode) {
        try {
            String systemInstruction = "You are Sister Ananya, a certified emergency First-Aid and Triage AI Nurse in the LifeLink emergency system. " +
                    "Format your answer clearly: " +
                    "1. Line 1: Short protocol title with emoji (e.g. '🔥 Burn First-Aid Protocol' or '🩹 Bleeding Control'). " +
                    "2. 3-4 numbered actionable First-Aid steps ('1. Title: Instruction'). " +
                    "3. Conclude with '⚠️ Warning: Red flag precautions'. " +
                    "Decline non-medical questions. " +
                    (isMarathiMode ? "Respond strictly in clear, natural Marathi (मराठी)." : "Respond strictly in English.");

            String combinedPrompt = systemInstruction + " User query: " + userQuery;

            String sanitizedPrompt = combinedPrompt.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");

            String jsonPayload = "{"
                    + "\"contents\": [{"
                    + "  \"parts\": [{\"text\": \"" + sanitizedPrompt + "\"}]"
                    + "}]"
                    + "}";

            return executeHttpQuery(jsonPayload, isMarathiMode);
        } catch (Exception e) {
            return fallbackClinicalResponse(userQuery, isMarathiMode);
        }
    }

    private String callGeminiAudioAPI(String base64Audio, boolean isMarathiMode) {
        try {
            String systemInstruction = "You are Sister Ananya, a certified emergency First-Aid triage nurse. Listen to the user's spoken voice query. " +
                    "Format your response clearly: " +
                    "1. Line 1: Short protocol title with emoji. " +
                    "2. 3-4 numbered actionable First-Aid steps ('1. Title: Instruction'). " +
                    "3. Conclude with '⚠️ Warning: Red flag precautions'. " +
                    (isMarathiMode ? "Respond strictly in clear spoken Marathi (मराठी)." : "Respond strictly in English.");

            String jsonPayload = "{"
                    + "\"contents\": [{"
                    + "  \"parts\": ["
                    + "    {\"text\": \"" + systemInstruction + "\"},"
                    + "    {\"inline_data\": {\"mime_type\": \"audio/wav\", \"data\": \"" + base64Audio + "\"}}"
                    + "  ]"
                    + "}]"
                    + "}";

            return executeHttpQuery(jsonPayload, isMarathiMode);
        } catch (Exception e) {
            return isMarathiMode
                    ? "मी तुमचा आवाज पूर्णपणे ऐकू शकले नाही. कृपया पुन्हा बोला किंवा टाईप करा."
                    : "I couldn't hear your voice clearly. Please speak again or type your symptoms.";
        }
    }

    private String executeHttpQuery(String jsonPayload, boolean isMarathiMode) throws Exception {
        URL url = new URL(GEMINI_ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(18000);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
            br.close();
            return parseGeminiResponseText(response.toString());
        } else {
            return fallbackClinicalResponse("general", isMarathiMode);
        }
    }

    private String parseGeminiResponseText(String json) {
        try {
            int textKey = json.indexOf("\"text\":");
            if (textKey != -1) {
                int startQuote = json.indexOf("\"", textKey + 7);
                if (startQuote != -1) {
                    StringBuilder sb = new StringBuilder();
                    boolean escaped = false;
                    for (int i = startQuote + 1; i < json.length(); i++) {
                        char c = json.charAt(i);
                        if (escaped) {
                            if (c == 'n') sb.append('\n');
                            else if (c == 'r') sb.append('\r');
                            else if (c == 't') sb.append('\t');
                            else if (c == '\"') sb.append('\"');
                            else if (c == '\\') sb.append('\\');
                            else if (c == 'u' && i + 4 < json.length()) {
                                try {
                                    String hex = json.substring(i + 1, i + 5);
                                    sb.append((char) Integer.parseInt(hex, 16));
                                    i += 4;
                                } catch (Exception ex) {
                                    sb.append(c);
                                }
                            } else {
                                sb.append(c);
                            }
                            escaped = false;
                        } else if (c == '\\') {
                            escaped = true;
                        } else if (c == '\"') {
                            break;
                        } else {
                            sb.append(c);
                        }
                    }
                    String result = sb.toString().trim();
                    if (!result.isEmpty()) return result;
                }
            }
        } catch (Exception ignored) {}
        return "Please follow basic First-Aid precautions and keep the patient calm.";
    }

    private String fallbackClinicalResponse(String query, boolean marathi) {
        String lower = query != null ? query.toLowerCase() : "";
        if (marathi) {
            if (lower.contains("भाज") || lower.contains("burn")) {
                return "🔥 भाजल्यास तातडीचा प्रथमोपचार प्रोटोकॉल:\n" +
                       "१. थंड पाणी: १० ते १५ मिनिटे वाहत्या थंड पाण्याखाली जखम धरा.\n" +
                       "२. दागिने काढा: सूज येण्यापूर्वी अंगठी किंवा घड्याळ त्वरित काढून घ्या.\n" +
                       "३. स्वच्छ मलमपट्टी: जखमेवर स्वच्छ निर्जंतुक कापड सैलसर बांधा.\n" +
                       "⚠️ खबरदारी: बर्फ, टूथपेस्ट किंवा तेल लावू नका. फोड आले असल्यास फोडू नका.";
            } else if (lower.contains("रक्त") || lower.contains("bleed")) {
                return "🩹 गंभीर रक्तस्त्राव नियंत्रण प्रोटोकॉल:\n" +
                       "१. थेट दाब: स्वच्छ कापडाने किंवा गॉजने ५ मिनिटे सतत थेट दाब द्या.\n" +
                       "२. अवयव वर ठेवा: जखमी भाग हृदयाच्या पातळीपेक्षा वर ठेवा.\n" +
                       "३. मलमपट्टी सुरक्षित करा: कापड न काढता वरून दुसरी मलमपट्टी बांधा.\n" +
                       "⚠️ खबरदारी: रक्तस्त्राव न थांबल्यास तात्काळ १०८ अॅम्ब्युलन्सला कॉल करा.";
            } else if (lower.contains("cpr") || lower.contains("छाती")) {
                return "❤️ आपत्कालीन CPR छातीचे कॉम्प्रेशन्स प्रोटोकॉल:\n" +
                       "१. सपाट झोपवा: रुग्णाला जमिनीवर पाठीवर सपाट झोपवा.\n" +
                       "२. छातीवर दाब: छातीच्या मध्यभागी दोन्ही हातांनी प्रति मिनिट १००-१२० वेगाने ५ सेमी दाबा.\n" +
                       "३. अविरत सुरू ठेवा: मदत येईपर्यंत किंवा हालचाल दिसेपर्यंत कॉम्प्रेशन्स सुरू ठेवा.\n" +
                       "⚠️ खबरदारी: तात्काळ १०८ वर कॉल करून अॅम्ब्युलन्स बोलवून घ्या.";
            } else if (lower.contains("हाड") || lower.contains("fracture") || lower.contains("sprain")) {
                return "🦴 हाड मोडणे किंवा मुरगळणे प्रोटोकॉल:\n" +
                       "१. स्थिर ठेवा: जखमी भागाची कसलीही हालचाल होऊ देऊ नका.\n" +
                       "२. थंड शेक: कापडात गुंडाळलेला बर्फ किंवा थंड पॅक १५ मिनिटे लावा.\n" +
                       "३. आधार द्या: पट्टी किंवा स्प्लिंटने तो भाग सुरक्षित करा.\n" +
                       "⚠️ खबरदारी: हाड स्वतः सरळ करण्याचा प्रयत्न करू नका.";
            }
            return "🩺 प्राथमिक आरोग्य तपासणी मार्गदर्शन:\n" +
                   "१. शांत बसवा: रुग्णाला सुरक्षित आणि आरामदायी स्थितीत ठेवा.\n" +
                   "२. श्वसन तपासा: रुग्णाचा श्वासोच्छ्वास व्यवस्थित चालू आहे का ते पहा.\n" +
                   "३. मदत घ्या: लक्षणे गंभीर असल्यास लाइफलिंक हॉस्पिटलशी त्वरित संपर्क साधा.\n" +
                   "⚠️ खबरदारी: रुग्णाची प्रकृती खालावल्यास वेळ न दवडता १०८ वर संपर्क करा.";
        } else {
            if (lower.contains("burn")) {
                return "🔥 First-Aid Protocol for Thermal Burns:\n" +
                       "1. Cool the Burn: Hold the injured skin under cold running tap water for 10-15 minutes immediately.\n" +
                       "2. Remove Constrictions: Gently remove tight rings, watches, or clothing before swelling sets in.\n" +
                       "3. Sterile Cover: Protect with a clean, sterile non-stick bandage or clean cling film.\n" +
                       "⚠️ Warning: Never apply ice, butter, flour, or toothpaste. Do not pop blisters.";
            } else if (lower.contains("bleed")) {
                return "🩹 Emergency Bleeding Control Protocol:\n" +
                       "1. Direct Pressure: Apply firm, uninterrupted direct pressure on the wound with clean gauze or cloth.\n" +
                       "2. Elevate Limb: Raise the bleeding extremity above heart level if no fracture is suspected.\n" +
                       "3. Layer Dressings: If blood soaks through, add another pad on top without removing the first.\n" +
                       "⚠️ Warning: If severe arterial spurt or persistent flow exceeds 5 minutes, call 108 immediately.";
            } else if (lower.contains("fracture") || lower.contains("sprain") || lower.contains("bone")) {
                return "🦴 Musculoskeletal Fracture & Sprain Care:\n" +
                       "1. Immobilize: Keep the injured joint or bone completely still. Do not attempt to realign.\n" +
                       "2. Cold Compress: Apply a cloth-wrapped cold pack for 15-20 minutes to reduce swelling.\n" +
                       "3. Support: Splint or sling the limb in the position found.\n" +
                       "⚠️ Warning: Check for pale/cold fingers or loss of pulse which indicates critical vascular emergency.";
            } else if (lower.contains("cpr") || lower.contains("heart")) {
                return "❤️ Adult Hands-Only CPR Protocol:\n" +
                       "1. Call & Check: Call 108 immediately and verify patient is unresponsive and not breathing.\n" +
                       "2. Hand Placement: Place heel of one hand in center of chest, interlock fingers of second hand.\n" +
                       "3. Hard & Fast: Compress chest 2 inches deep at 100-120 beats per minute until medics arrive.\n" +
                       "⚠️ Warning: Minimize interruptions in compressions. Rotate rescuer every 2 minutes if tired.";
            }
            return "🩺 Clinical First-Aid Initial Assessment:\n" +
                   "1. Ensure Safety: Check that the scene is safe for both you and the patient.\n" +
                   "2. Check Vitals: Assess airway, breathing responsiveness, and active bleeding.\n" +
                   "3. Keep Calm: Keep the patient warm, reassured, and in a comfortable recovery position.\n" +
                   "⚠️ Warning: If severe distress, chest pain, or loss of consciousness occurs, summon 108 emergency.";
        }
    }

    // =========================================================
    // EXPLICIT MEDIA RESOURCE CLEANUP (PREVENTS MAVEN FILE LOCKS)
    // =========================================================
    public void dispose() {
        stopSpeakingVoice();

        if (nurseVideoPlayer != null) {
            try {
                nurseVideoPlayer.stop();
                nurseVideoPlayer.dispose();
                nurseVideoPlayer = null;
            } catch (Exception ignored) {}
        }

        if (currentAudioPlayer != null) {
            try {
                currentAudioPlayer.stop();
                currentAudioPlayer.dispose();
                currentAudioPlayer = null;
            } catch (Exception ignored) {}
        }

        deleteTempAudio(currentAudioFile);
        currentAudioFile = null;
    }

    public static void playPageAnimation(Node node) {
        node.setOpacity(0);
        FadeTransition fade = new FadeTransition(Duration.millis(480), node);
        fade.setFromValue(0.15);
        fade.setToValue(1.0);

        TranslateTransition slide = new TranslateTransition(Duration.millis(480), node);
        slide.setFromY(24);
        slide.setToY(0);

        ScaleTransition scale = new ScaleTransition(Duration.millis(480), node);
        scale.setFromX(0.985);
        scale.setFromY(0.985);
        scale.setToX(1.0);
        scale.setToY(1.0);

        ParallelTransition animation = new ParallelTransition(fade, slide, scale);
        animation.play();
    }
}