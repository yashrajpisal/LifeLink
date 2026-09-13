package com.kurukshetra.view.voice;

import com.kurukshetra.dao.voice.NurseVoiceReportDao;
import com.kurukshetra.model.voice.NurseVoiceReportModel;
import com.google.cloud.Timestamp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.UUID;

public class NurseVoiceReportView extends Application {

    private static final String PRIMARY_PINK = "#E67593";
    private static final String VERY_LIGHT_PINK = "#FDEDF2";
    private static final String PAGE_BG = "#FCF9FA";
    private static final String SURFACE = "#FFFFFF";
    private static final String TEXT_PRIMARY = "#2B2226";
    private static final String TEXT_SECONDARY = "#665960";
    private static final String BORDER_COLOR = "#EEDDE3";
    private static final String DANGER_RED = "#D71920";

    // Google Cloud Speech-to-Text Service Bridge
    private final GoogleCloudSpeechService voiceService = new GoogleCloudSpeechService("mr-IN");
    private final NurseVoiceReportDao reportDao = new NurseVoiceReportDao();

    private TextArea transcriptionTextArea;
    private Button recordButton;
    private TextField patientIdField;
    private ComboBox<String> languageSelector;
    private Label statusIndicator;
    private Circle recordingDot;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("LifeLink - Voice Clinical Report");

        VBox rootContainer = new VBox(20);
        rootContainer.setPadding(new Insets(30));
        rootContainer.setAlignment(Pos.TOP_CENTER);
        rootContainer.setStyle("-fx-background-color: " + PAGE_BG + "; -fx-font-family: 'Segoe UI';");

        VBox reportCard = createVoiceReportCard("nurse1@lifelink.com", () -> {
            System.out.println("Back button clicked");
            primaryStage.close();
        });
        reportCard.setMaxWidth(820);

        rootContainer.getChildren().add(reportCard);

        Scene scene = new Scene(rootContainer, 920, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public VBox createVoiceReportCard(String nurseEmail, Runnable onBack) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 1; -fx-border-radius: 14; -fx-background-radius: 14; -fx-effect: dropshadow(gaussian, rgba(230,117,147,0.06), 8, 0, 0, 2);");

        // Top Header
        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(2);
        Text title = new Text("Voice Clinical Report (Speech-to-Text)");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text subtitle = new Text("Dictate in Marathi, English, or Hindi to generate real-time patient notes.");
        subtitle.setStyle("-fx-font-size: 11px; -fx-fill: " + TEXT_SECONDARY + ";");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backBtn = new Button("← Back");
        backBtn.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-text-fill: " + PRIMARY_PINK + "; -fx-font-weight: bold; -fx-font-size: 11px; -fx-background-radius: 8; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            if (voiceService.isRecording()) {
                voiceService.stopListening();
            }
            if (onBack != null) onBack.run();
        });

        topRow.getChildren().addAll(titleBox, spacer, backBtn);

        // Configuration Row: Patient ID & Language Switcher
        HBox configRow = new HBox(14);
        configRow.setAlignment(Pos.CENTER_LEFT);

        Label patientLbl = new Label("Patient ID:");
        patientLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        patientIdField = new TextField();
        patientIdField.setPromptText("e.g. PAT-2041");
        patientIdField.setPrefWidth(180);
        patientIdField.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 6 8;");

        Label langLbl = new Label("Language:");
        langLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        languageSelector = new ComboBox<>();
        languageSelector.getItems().addAll("मराठी (Marathi - mr-IN)", "English (India - en-IN)", "हिंदी (Hindi - hi-IN)", "English (US - en-US)");
        languageSelector.setValue("मराठी (Marathi - mr-IN)");
        languageSelector.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 6; -fx-background-radius: 6;");

        languageSelector.setOnAction(e -> {
            String selected = languageSelector.getValue();
            if (selected.contains("mr-IN")) voiceService.setLanguageCode("mr-IN");
            else if (selected.contains("hi-IN")) voiceService.setLanguageCode("hi-IN");
            else if (selected.contains("en-IN")) voiceService.setLanguageCode("en-IN");
            else voiceService.setLanguageCode("en-US");
        });

        configRow.getChildren().addAll(patientLbl, patientIdField, langLbl, languageSelector);

        // Speech-to-Text Live Transcript Box
        transcriptionTextArea = new TextArea();
        transcriptionTextArea.setPromptText("बोललेले शब्द येथे थेट दिसतील / Spoken words will appear here in real time...");
        transcriptionTextArea.setWrapText(true);
        transcriptionTextArea.setPrefRowCount(7);
        transcriptionTextArea.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8; -fx-background-radius: 8; -fx-font-size: 13px; -fx-text-fill: " + TEXT_PRIMARY + ";");

        // Action Toolbar
        HBox actionsRow = new HBox(12);
        actionsRow.setAlignment(Pos.CENTER_LEFT);

        recordButton = new Button("🎤 Start Recording");
        recordButton.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");
        recordButton.setOnAction(e -> toggleRecording());

        Button clearBtn = new Button("Clear");
        clearBtn.setStyle("-fx-background-color: transparent; -fx-border-color: " + BORDER_COLOR + "; -fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 12px; -fx-padding: 7 14; -fx-background-radius: 8; -fx-cursor: hand;");
        clearBtn.setOnAction(e -> transcriptionTextArea.clear());

        Button saveBtn = new Button("💾 Save Clinical Report");
        saveBtn.setStyle("-fx-background-color: #258052; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");
        saveBtn.setOnAction(e -> saveReportToFirebase(nurseEmail));

        recordingDot = new Circle(4, Color.TRANSPARENT);
        statusIndicator = new Label("Idle");
        statusIndicator.setStyle("-fx-font-size: 11px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        HBox statusBox = new HBox(6, recordingDot, statusIndicator);
        statusBox.setAlignment(Pos.CENTER_LEFT);

        Region actionSpacer = new Region();
        HBox.setHgrow(actionSpacer, Priority.ALWAYS);

        actionsRow.getChildren().addAll(recordButton, clearBtn, saveBtn, actionSpacer, statusBox);

        card.getChildren().addAll(topRow, configRow, transcriptionTextArea, actionsRow);
        return card;
    }

    private void toggleRecording() {
        if (!voiceService.isRecording()) {
            recordButton.setText("⏹ Stop Dictation");
            recordButton.setStyle("-fx-background-color: " + DANGER_RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");
            recordingDot.setFill(Color.web(DANGER_RED));
            statusIndicator.setText("Transcribing clinical audio...");
            languageSelector.setDisable(true);

            voiceService.startListening(
                liveText -> Platform.runLater(() -> transcriptionTextArea.setText(liveText)),
                completedText -> Platform.runLater(() -> {
                    transcriptionTextArea.setText(completedText);
                    recordButton.setText("🎤 Start Recording");
                    recordButton.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");
                    recordingDot.setFill(Color.TRANSPARENT);
                    statusIndicator.setText("Dictation Complete");
                    languageSelector.setDisable(false);
                })
            );
        } else {
            voiceService.stopListening();
            recordButton.setText("🎤 Start Recording");
            recordButton.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 8 16; -fx-background-radius: 8; -fx-cursor: hand;");
            recordingDot.setFill(Color.TRANSPARENT);
            statusIndicator.setText("Processing final text...");
            languageSelector.setDisable(false);
        }
    }

    private void saveReportToFirebase(String nurseEmail) {
        String content = transcriptionTextArea.getText().trim();
        String patientId = patientIdField.getText().trim();

        if (content.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty Transcription", "Please dictate or type text before saving.");
            return;
        }

        String reportId = "VR-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        NurseVoiceReportModel model = new NurseVoiceReportModel(
            reportId,
            (nurseEmail != null ? nurseEmail : "nurse1@lifelink.com"),
            (patientId.isEmpty() ? "General Clinical Note" : patientId),
            content,
            Timestamp.now()
        );

        new Thread(() -> {
            reportDao.saveVoiceReport(model);
            Platform.runLater(() -> {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Voice Clinical Report #" + reportId + " saved successfully!");
                transcriptionTextArea.clear();
                patientIdField.clear();
            });
        }).start();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}