package com.kurukshetra.view.hospital;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.ListenerRegistration;
import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.view.util.ShimmerLoader;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import com.kurukshetra.view.nurse.PatientIcuMonitorView;

public class HospitalPatientMonitoring {

    private VBox patientMonitoring;

    private ListenerRegistration videoCallListener;
    private ListenerRegistration reportListener;

    private String currentPatientId = "PAT-2004";
    private String currentTripId = "MH16-104";
    private String currentHospitalName = "KEM Hospital Pune";

    public HospitalPatientMonitoring() {
        this("PAT-2004", "MH16-104", "KEM Hospital Pune");
    }

    public HospitalPatientMonitoring(String pId, String tId, String hName) {
        if (pId != null && !pId.trim().isEmpty()) {
            this.currentPatientId = pId.trim();
        }
        if (tId != null && !tId.trim().isEmpty()) {
            this.currentTripId = tId.trim();
        }
        if (hName != null && !hName.trim().isEmpty()) {
            this.currentHospitalName = hName.trim();
        }

        patientMonitoring = new VBox(20);
        patientMonitoring.setPadding(new Insets(25));
        patientMonitoring.setFillWidth(true);
        patientMonitoring.setStyle("-fx-background-color: #a5bdaaff;");

        VBox content = new VBox(20);
        content.setPadding(new Insets(5));
        content.setFillWidth(true);

        // Let content size itself dynamically to fit all children without cutting off bottom elements.
        content.setMinHeight(Region.USE_COMPUTED_SIZE);
        content.setPrefHeight(Region.USE_COMPUTED_SIZE);

        // ============================================================
        // TITLE
        // ============================================================

        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER_LEFT);

        VBox titleTexts = new VBox(3);

        Label title = new Label("PATIENT MONITORING");
        title.setStyle(
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #202124;"
        );

        titleTexts.getChildren().addAll(title);

        Region titleSpacer = new Region();
        HBox.setHgrow(titleSpacer, Priority.ALWAYS);

        
        titleBox.getChildren().addAll(
                titleTexts,
                titleSpacer
        );

        // ============================================================
        // PATIENT INFORMATION BAR
        // ============================================================

        HBox patientInfoBar = new HBox(20);
        patientInfoBar.setPadding(new Insets(15));
        patientInfoBar.setAlignment(Pos.CENTER_LEFT);
        patientInfoBar.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #e4e4e4;" +
                "-fx-border-radius: 12;"
        );

        VBox patientIdBox = new VBox(3);

        Label patientIdTitle = new Label("PATIENT ID");
        patientIdTitle.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #888888;"
        );

        Label patientId = new Label(currentPatientId);
        patientId.setStyle(
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #252525;"
        );

        patientIdBox.getChildren().addAll(
                patientIdTitle,
                patientId
        );

        VBox hospitalBox = new VBox(3);

        Label hospitalTitle = new Label("TARGET HOSPITAL");
        hospitalTitle.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #888888;"
        );

        Label hospitalName = new Label(currentHospitalName);
        hospitalName.setStyle(
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #252525;"
        );

        hospitalBox.getChildren().addAll(
                hospitalTitle,
                hospitalName
        );

        VBox tripBox = new VBox(3);

        Label tripTitle = new Label("TRIP ID");
        tripTitle.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #888888;"
        );

        Label tripId = new Label(currentTripId);
        tripId.setStyle(
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #252525;"
        );

        tripBox.getChildren().addAll(
                tripTitle,
                tripId
        );

        patientInfoBar.getChildren().addAll(
                patientIdBox,
                hospitalBox,
                tripBox
        );

        // ============================================================
        // TOP SECTION
        // VIDEO CALL + MEDICAL REPORT
        // ============================================================

        HBox topSection = new HBox(18);
        topSection.setPrefHeight(600);
        topSection.setMinHeight(550);
        topSection.setFillHeight(true);

        // ============================================================
        // VIDEO CALL CARD
        // ============================================================

        VBox videoCard = new VBox(15);
        videoCard.setPadding(new Insets(20));
        videoCard.setPrefWidth(800);
        videoCard.setMinWidth(650);
        videoCard.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: #e5e5e5;" +
                "-fx-border-radius: 15;"
        );

        HBox videoHeader = new HBox();
        videoHeader.setAlignment(Pos.CENTER_LEFT);

        VBox videoHeaderText = new VBox(3);

        Label videoTitle = new Label("VIDEO CALL");
        videoTitle.setStyle(
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #202124;"
        );

        Label videoSubtitle = new Label(
                "Live communication with ambulance nurse"
        );
        videoSubtitle.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #888888;"
        );

        videoHeaderText.getChildren().addAll(
                videoTitle,
                videoSubtitle
        );

        Region videoHeaderSpacer = new Region();
        HBox.setHgrow(videoHeaderSpacer, Priority.ALWAYS);

        Label callStatus = new Label("WAITING");
        callStatus.setStyle(
                "-fx-background-color: #f3f4f6;" +
                "-fx-text-fill: #6b7280;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 7px 12px;" +
                "-fx-background-radius: 20;"
        );

        videoHeader.getChildren().addAll(
                videoHeaderText,
                videoHeaderSpacer,
                callStatus
        );

        // ============================================================
        // VIDEO AREA
        // ============================================================

        BorderPane videoArea = new BorderPane();
        videoArea.setPrefHeight(470);
        videoArea.setMinHeight(400);
        videoArea.setStyle(
                "-fx-background-color: #111827;" +
                "-fx-background-radius: 12;"
        );

        VBox videoCenter = new VBox(12);
        videoCenter.setAlignment(Pos.CENTER);

        Circle cameraCircle = new Circle(
                38,
                Color.web("#374151")
        );

        Label cameraIcon = new Label("☎");
        cameraIcon.setStyle(
                "-fx-font-size: 30px;" +
                "-fx-text-fill: white;"
        );

        StackPanePlaceholder stackPlaceholder =
                new StackPanePlaceholder();

        Label videoText = new Label(
                "Waiting for nurse video call..."
        );
        videoText.setStyle(
                "-fx-font-size: 15px;" +
                "-fx-text-fill: #d1d5db;"
        );

        Label videoSubText = new Label(
                "When the nurse starts a call, it will appear here."
        );
        videoSubText.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-text-fill: #9ca3af;"
        );

        stackPlaceholder.getChildren().add(cameraCircle);
        stackPlaceholder.getChildren().add(cameraIcon);

        videoCenter.getChildren().addAll(
                stackPlaceholder
                // videoText,
                // videoSubText
        );

        videoArea.setCenter(videoCenter);

        // ============================================================
        // VIDEO BUTTONS
        // ============================================================

        HBox videoButtons = new HBox(12);
        videoButtons.setAlignment(Pos.CENTER_RIGHT);

        Button joinCallButton = new Button("JOIN CALL");
        joinCallButton.setPrefHeight(42);
        joinCallButton.setPrefWidth(140);
        joinCallButton.setStyle(
                "-fx-background-color: #22c55e;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-cursor: hand;"
        );

        Button endCallButton = new Button("END CALL");
        endCallButton.setPrefHeight(42);
        endCallButton.setPrefWidth(140);
        endCallButton.setStyle(
                "-fx-background-color: #ef4444;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-cursor: hand;"
        );

        videoButtons.getChildren().addAll(
                joinCallButton,
                endCallButton
        );

        videoCard.getChildren().addAll(
                videoHeader,
                videoArea,
                videoButtons
        );

        VBox.setVgrow(videoArea, Priority.ALWAYS);
        HBox.setHgrow(videoCard, Priority.ALWAYS);

        // ============================================================
        // MEDICAL REPORT CARD
        // ============================================================

        VBox reportCard = new VBox(15);
        reportCard.setPadding(new Insets(20));
        reportCard.setPrefWidth(500);
        reportCard.setMinWidth(400);
        reportCard.setMaxWidth(550);
        reportCard.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: #e5e5e5;" +
                "-fx-border-radius: 15;"
        );

        HBox reportHeader = new HBox();
        reportHeader.setAlignment(Pos.CENTER_LEFT);

        VBox reportHeaderText = new VBox(3);

        Label reportTitle = new Label("MEDICAL REPORT");
        reportTitle.setStyle(
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #202124;"
        );

        Label reportSubtitle = new Label(
                "OCR / AI report received from nurse"
        );
        reportSubtitle.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #888888;"
        );

        reportHeaderText.getChildren().addAll(
                reportTitle,
                reportSubtitle
        );

        Region reportHeaderSpacer = new Region();
        HBox.setHgrow(reportHeaderSpacer, Priority.ALWAYS);

        Label reportStatus = new Label("WAITING");
        reportStatus.setStyle(
                "-fx-background-color: #f3f4f6;" +
                "-fx-text-fill: #6b7280;" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 6px 10px;" +
                "-fx-background-radius: 20;"
        );

        reportHeader.getChildren().addAll(
                reportHeaderText,
                reportHeaderSpacer,
                reportStatus
        );

        // ============================================================
        // OCR REPORT CONTENT
        // ============================================================

        VBox reportContentBox = new VBox(10);
        reportContentBox.setPadding(new Insets(15));
        reportContentBox.setStyle(
                "-fx-background-color: #f8f9fc;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #eeeeee;" +
                "-fx-border-radius: 10;"
        );

        Label reportContent = new Label(
                "No medical report received yet."
        );
        reportContent.setWrapText(true);
        reportContent.setStyle(
                "-fx-font-size: 13px;" +
                "-fx-text-fill: #555555;"
        );

        ShimmerLoader.ShimmerPane reportShimmer = ShimmerLoader.createCardSkeleton(320, 80);
        reportContent.setVisible(false);
        reportContent.setManaged(false);
        reportContentBox.getChildren().addAll(reportShimmer, reportContent);

        VBox nurseNotesBox = new VBox(8);
        nurseNotesBox.setPadding(new Insets(15));
        nurseNotesBox.setStyle(
                "-fx-background-color: #fffaf0;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #f1e2bd;" +
                "-fx-border-radius: 10;"
        );

        Label nurseNotesTitle = new Label("NURSE NOTES");
        nurseNotesTitle.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #9a6700;"
        );

        Label nurseNotes = new Label(
                "No nurse notes available."
        );
        nurseNotes.setWrapText(true);
        nurseNotes.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #6b5a35;"
        );

        nurseNotesBox.getChildren().addAll(
                nurseNotesTitle,
                nurseNotes
        );

        VBox reportSpacer = new VBox();
        VBox.setVgrow(reportSpacer, Priority.ALWAYS);

        reportCard.getChildren().addAll(
                reportHeader,
                reportContentBox,
                nurseNotesBox,
                reportSpacer
        );

        topSection.getChildren().addAll(
                videoCard,
                reportCard
        );







        
        // ============================================================
        // PATIENT LIVE DATA
        // ============================================================

        VBox liveDataCard = new VBox(15);
        liveDataCard.setPadding(new Insets(20));
        liveDataCard.setPrefHeight(680);
        liveDataCard.setMinHeight(680);
        liveDataCard.setMaxHeight(Double.MAX_VALUE);
        liveDataCard.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: #e5e5e5;" +
                "-fx-border-radius: 15;"
        );

        // LIVE HEADER
        HBox liveHeader = new HBox();
        liveHeader.setAlignment(Pos.CENTER_LEFT);

        VBox liveHeaderText = new VBox(3);

        Label liveTitle = new Label("PATIENT LIVE DATA");
        liveTitle.setStyle(
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #202124;"
        );

        Label liveSubtitle = new Label("Real-time patient vitals");
        liveSubtitle.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #888888;"
        );

        liveHeaderText.getChildren().addAll(
                liveTitle,
                liveSubtitle
        );

        Region liveHeaderSpacer = new Region();
        HBox.setHgrow(liveHeaderSpacer, Priority.ALWAYS);

        Label liveStatus = new Label("LIVE");
        liveStatus.setStyle(
                "-fx-background-color: #dcfce7;" +
                "-fx-text-fill: #16a34a;" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 6px 12px;" +
                "-fx-background-radius: 20;"
        );

        liveHeader.getChildren().addAll(
                liveHeaderText,
                liveHeaderSpacer,
                liveStatus
        );

        // VITAL VALUE LABELS
        Label hrValue = new Label("78");
        Label bpValue = new Label("120/80");
        Label pulseValue = new Label("78");
        Label spo2Value = new Label("98%");
        Label tempValue = new Label("37.0°C");
        Label rhythmValue = new Label("NSR");

        // VITAL BOX 1
        VBox heartRateBox = new VBox(5);
        heartRateBox.setAlignment(Pos.CENTER_LEFT);
        heartRateBox.setPadding(new Insets(10));
        heartRateBox.setPrefWidth(150);
        heartRateBox.setMinWidth(130);
        heartRateBox.setPrefHeight(90);
        heartRateBox.setMinHeight(90);
        heartRateBox.setMaxHeight(90);
        heartRateBox.setStyle(
                "-fx-background-color: #f0fff5;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #c7f9d9;" +
                "-fx-border-radius: 10;"
        );

        Label hrTitle = new Label("HEART RATE");
        hrTitle.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #16a34a;"
        );

        hrValue.setStyle(
                "-fx-font-size: 23px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #16a34a;"
        );

        Label hrUnit = new Label("BPM");
        hrUnit.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-text-fill: #777777;"
        );

        heartRateBox.getChildren().addAll(
                hrTitle,
                hrValue,
                hrUnit
        );

        // VITAL BOX 2
        VBox bpBox = new VBox(5);
        bpBox.setAlignment(Pos.CENTER_LEFT);
        bpBox.setPadding(new Insets(10));
        bpBox.setPrefWidth(150);
        bpBox.setMinWidth(130);
        bpBox.setPrefHeight(90);
        bpBox.setMinHeight(90);
        bpBox.setMaxHeight(90);
        bpBox.setStyle(
                "-fx-background-color: #fff4f7;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #f4c9d5;" +
                "-fx-border-radius: 10;"
        );

        Label bpTitle = new Label("BLOOD PRESSURE");
        bpTitle.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #D85375;"
        );

        bpValue.setStyle(
                "-fx-font-size: 23px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #D85375;"
        );

        Label bpUnit = new Label("mmHg");
        bpUnit.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-text-fill: #777777;"
        );

        bpBox.getChildren().addAll(
                bpTitle,
                bpValue,
                bpUnit
        );

        // VITAL BOX 3
        VBox pulseBox = new VBox(5);
        pulseBox.setAlignment(Pos.CENTER_LEFT);
        pulseBox.setPadding(new Insets(10));
        pulseBox.setPrefWidth(150);
        pulseBox.setMinWidth(130);
        pulseBox.setPrefHeight(90);
        pulseBox.setMinHeight(90);
        pulseBox.setMaxHeight(90);
        pulseBox.setStyle(
                "-fx-background-color: #effcff;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #bceff7;" +
                "-fx-border-radius: 10;"
        );

        Label pulseTitle = new Label("PULSE RATE");
        pulseTitle.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #0891b2;"
        );

        pulseValue.setStyle(
                "-fx-font-size: 23px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #0891b2;"
        );

        Label pulseUnit = new Label("BPM");
        pulseUnit.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-text-fill: #777777;"
        );

        pulseBox.getChildren().addAll(
                pulseTitle,
                pulseValue,
                pulseUnit
        );

        // VITAL BOX 4
        VBox spo2Box = new VBox(5);
        spo2Box.setAlignment(Pos.CENTER_LEFT);
        spo2Box.setPadding(new Insets(10));
        spo2Box.setPrefWidth(150);
        spo2Box.setMinWidth(130);
        spo2Box.setPrefHeight(90);
        spo2Box.setMinHeight(90);
        spo2Box.setMaxHeight(90);
        spo2Box.setStyle(
                "-fx-background-color: #effcff;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #bceff7;" +
                "-fx-border-radius: 10;"
        );

        Label spo2Title = new Label("SpO₂");
        spo2Title.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #0891b2;"
        );

        spo2Value.setStyle(
                "-fx-font-size: 23px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #0891b2;"
        );

        Label spo2Unit = new Label("OXYGEN");
        spo2Unit.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-text-fill: #777777;"
        );

        spo2Box.getChildren().addAll(
                spo2Title,
                spo2Value,
                spo2Unit
        );

        // VITAL BOX 5
        VBox tempBox = new VBox(5);
        tempBox.setAlignment(Pos.CENTER_LEFT);
        tempBox.setPadding(new Insets(10));
        tempBox.setPrefWidth(150);
        tempBox.setMinWidth(130);
        tempBox.setPrefHeight(90);
        tempBox.setMinHeight(90);
        tempBox.setMaxHeight(90);
        tempBox.setStyle(
                "-fx-background-color: #fffbea;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #f3e3a5;" +
                "-fx-border-radius: 10;"
        );

        Label tempTitle = new Label("TEMPERATURE");
        tempTitle.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #c28a00;"
        );

        tempValue.setStyle(
                "-fx-font-size: 23px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #c28a00;"
        );

        Label tempUnit = new Label("CORE TEMP");
        tempUnit.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-text-fill: #777777;"
        );

        tempBox.getChildren().addAll(
                tempTitle,
                tempValue,
                tempUnit
        );

        // VITAL BOX 6
        VBox rhythmBox = new VBox(5);
        rhythmBox.setAlignment(Pos.CENTER_LEFT);
        rhythmBox.setPadding(new Insets(10));
        rhythmBox.setPrefWidth(150);
        rhythmBox.setMinWidth(130);
        rhythmBox.setPrefHeight(90);
        rhythmBox.setMinHeight(90);
        rhythmBox.setMaxHeight(90);
        rhythmBox.setStyle(
                "-fx-background-color: #f7f5ff;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #ddd6fe;" +
                "-fx-border-radius: 10;"
        );

        Label rhythmTitle = new Label("RHYTHM");
        rhythmTitle.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #7c3aed;"
        );

        rhythmValue.setStyle(
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #7c3aed;"
        );

        Label rhythmUnit = new Label("ECG STATUS");
        rhythmUnit.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-text-fill: #777777;"
        );

        rhythmBox.getChildren().addAll(
                rhythmTitle,
                rhythmValue,
                rhythmUnit
        );

        // VITAL BOX CONTAINER
        HBox vitalBoxes = new HBox(10);
        vitalBoxes.setAlignment(Pos.CENTER_LEFT);
        vitalBoxes.setFillHeight(true);
        vitalBoxes.setPrefHeight(90);
        vitalBoxes.setMinHeight(90);
        vitalBoxes.setMaxHeight(90);

        HBox.setHgrow(heartRateBox, Priority.ALWAYS);
        HBox.setHgrow(bpBox, Priority.ALWAYS);
        HBox.setHgrow(pulseBox, Priority.ALWAYS);
        HBox.setHgrow(spo2Box, Priority.ALWAYS);
        HBox.setHgrow(tempBox, Priority.ALWAYS);
        HBox.setHgrow(rhythmBox, Priority.ALWAYS);

        vitalBoxes.getChildren().addAll(
                heartRateBox,
                bpBox,
                pulseBox,
                spo2Box,
                tempBox,
                rhythmBox
        );

        // ICU MONITOR
        PatientIcuMonitorView patientMonitor =
                new PatientIcuMonitorView(
                        currentPatientId,
                        currentHospitalName,
                        snapshot -> {

                        Platform.runLater(() -> {

                                hrValue.setText(
                                        String.valueOf(snapshot.heartRate)
                                );

                                bpValue.setText(
                                        snapshot.systolic +
                                        "/" +
                                        snapshot.diastolic
                                );

                                pulseValue.setText(
                                        String.valueOf(snapshot.pulseRate)
                                );

                                spo2Value.setText(
                                        snapshot.spo2 + "%"
                                );

                                tempValue.setText(
                                        String.format(
                                                "%.1f°C",
                                                snapshot.temperature
                                        )
                                );

                                rhythmValue.setText(
                                        snapshot.rhythm
                                );

                                System.out.println(
                                        "HR: " + snapshot.heartRate +
                                        " | BP: " + snapshot.systolic +
                                        "/" + snapshot.diastolic +
                                        " | Pulse: " + snapshot.pulseRate +
                                        " | SpO2: " + snapshot.spo2 +
                                        " | Temp: " + snapshot.temperature +
                                        " | Rhythm: " + snapshot.rhythm
                                );
                        });
                        }
                );

        patientMonitor.setPrefHeight(430);
        patientMonitor.setMinHeight(430);
        patientMonitor.setMaxHeight(430);

        // ADD HEADER + BOXES + LIVE MONITOR
        liveDataCard.getChildren().addAll(
                liveHeader,
                vitalBoxes,
                patientMonitor
        );

        // ============================================================
        // ADD EVERYTHING TO CONTENT
        // ============================================================

        content.getChildren().addAll(
                titleBox,
                patientInfoBar,
                topSection,
                liveDataCard
        );

        // ============================================================
        // SCROLL PANE
        // ============================================================

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setContent(content);

        scrollPane.setFitToWidth(true);

        // IMPORTANT:
        // Do not force content to viewport height.
        scrollPane.setFitToHeight(false);

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setPannable(true);

        scrollPane.setPrefViewportHeight(700);

        scrollPane.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background: transparent;"
        );

        VBox.setVgrow(
                scrollPane,
                Priority.ALWAYS
        );

        patientMonitoring.getChildren().add(scrollPane);

        // ============================================================
        // VIDEO CALL FIRESTORE LISTENER
        // ============================================================

        Firestore db = FirebaseConfig.getFirestore();

        videoCallListener = db.collection("videoCall")
                .document(currentTripId)
                .addSnapshotListener((snapshot, error) -> {

                    if (error != null) {
                        System.out.println(
                                "Video call listener error: "
                                        + error.getMessage()
                        );
                        return;
                    }

                    if (snapshot == null || !snapshot.exists()) {
                        return;
                    }

                    String status = snapshot.getString("status");

                    if (status == null) {
                        return;
                    }

                    Platform.runLater(() -> {

                        if (status.equalsIgnoreCase("RINGING")) {

                            callStatus.setText("INCOMING CALL");

                            callStatus.setStyle(
                                    "-fx-background-color: #fee2e2;" +
                                    "-fx-text-fill: #dc2626;" +
                                    "-fx-font-size: 11px;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-padding: 7px 12px;" +
                                    "-fx-background-radius: 20;"
                            );

                            videoText.setText(
                                    "Nurse is calling..."
                            );

                            videoSubText.setText(
                                    "Click JOIN CALL to connect."
                            );

                        } else if (status.equalsIgnoreCase("CONNECTED")) {

                            callStatus.setText("CONNECTED");

                            callStatus.setStyle(
                                    "-fx-background-color: #dcfce7;" +
                                    "-fx-text-fill: #16a34a;" +
                                    "-fx-font-size: 11px;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-padding: 7px 12px;" +
                                    "-fx-background-radius: 20;"
                            );

                            videoText.setText(
                                    "Video call connected"
                            );

                            videoSubText.setText(
                                    "You are connected with the nurse."
                            );

                        } else if (status.equalsIgnoreCase("ENDED")) {

                            callStatus.setText("CALL ENDED");

                            callStatus.setStyle(
                                    "-fx-background-color: #f3f4f6;" +
                                    "-fx-text-fill: #6b7280;" +
                                    "-fx-font-size: 11px;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-padding: 7px 12px;" +
                                    "-fx-background-radius: 20;"
                            );

                            videoText.setText(
                                    "Video call ended"
                            );

                            videoSubText.setText(
                                    "Waiting for next call."
                            );
                        }
                    });
                });

        // ============================================================
        // NURSE REPORT FIRESTORE LISTENER
        // ============================================================

        reportListener = db.collection("nurseToHospitalReport")
                .document(currentPatientId)
                .addSnapshotListener((snapshot, error) -> {

                    if (error != null) {
                        System.out.println(
                                "Medical report listener error: "
                                        + error.getMessage()
                        );
                        return;
                    }

                    if (snapshot == null || !snapshot.exists()) {
                        return;
                    }

                    String aiSummary =
                            snapshot.getString("aiSummaryReport");

                    String rawNotes =
                            snapshot.getString("rawVoiceNotes");

                    String status =
                            snapshot.getString("reportStatus");

                    Platform.runLater(() -> {
                        if (reportShimmer != null) {
                            reportShimmer.stop();
                            reportContentBox.getChildren().remove(reportShimmer);
                        }
                        reportContent.setVisible(true);
                        reportContent.setManaged(true);

                        if (aiSummary != null &&
                                !aiSummary.trim().isEmpty()) {

                            reportContent.setText(aiSummary);
                        }

                        if (rawNotes != null &&
                                !rawNotes.trim().isEmpty()) {

                            nurseNotes.setText(rawNotes);
                        }

                        reportStatus.setText("REPORT RECEIVED");

                        reportStatus.setStyle(
                                "-fx-background-color: #dcfce7;" +
                                "-fx-text-fill: #16a34a;" +
                                "-fx-font-size: 10px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-padding: 6px 10px;" +
                                "-fx-background-radius: 20;"
                        );
                    });
                });

        // ============================================================
        // JOIN CALL
        // ============================================================

        joinCallButton.setOnAction(event -> {

            try {

                String jitsiRoomId =
                        "lifelink-er-trip-" + currentTripId;

                String jitsiUrl =
                        "https://meet.jit.si/"
                                + jitsiRoomId
                                + "#config.prejoinPageEnabled=false";

                java.awt.Desktop.getDesktop()
                        .browse(new java.net.URI(jitsiUrl));

                db.collection("videoCall")
                        .document(currentTripId)
                        .update(
                                "status",
                                "CONNECTED"
                        );

            } catch (Exception e) {

                e.printStackTrace();
            }
        });

        // ============================================================
        // END CALL
        // ============================================================

        endCallButton.setOnAction(event -> {

            db.collection("videoCall")
                    .document(currentTripId)
                    .update(
                            "status",
                            "ENDED"
                    );

            callStatus.setText("CALL ENDED");

            callStatus.setStyle(
                    "-fx-background-color: #f3f4f6;" +
                    "-fx-text-fill: #6b7280;" +
                    "-fx-font-size: 11px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-padding: 7px 12px;" +
                    "-fx-background-radius: 20;"
            );

            videoText.setText("Video call ended");

            videoSubText.setText(
                    "Waiting for next call."
            );
        });
    }

    public VBox getPatientMonitoring() {
        return patientMonitoring;
    }

    // ================================================================
    // SIMPLE STACKPANE PLACEHOLDER
    // ================================================================

    private static class StackPanePlaceholder
            extends javafx.scene.layout.StackPane {

        public StackPanePlaceholder() {
            setPrefSize(80, 80);
            setMinSize(80, 80);
            setMaxSize(80, 80);
            setAlignment(Pos.CENTER);
        }
    }
}