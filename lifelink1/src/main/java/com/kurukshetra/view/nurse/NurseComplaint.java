package com.kurukshetra.view.nurse;

import com.kurukshetra.config.FirebaseConfig;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class NurseComplaint {

    private static final String PRIMARY_PINK = "#E67593";
    private static final String PINK_DARK = "#D85375";
    private static final String VERY_LIGHT_PINK = "#FDF0F4";
    private static final String PAGE_BG = "#f9d6d7";
    private static final String SURFACE = "#FFFFFF";
    private static final String PRIMARY_TEXT = "#2B2125";
    private static final String SECONDARY_TEXT = "#695960";
    private static final String MUTED_TEXT = "#99878E";
    private static final String BORDER_COLOR = "#EEDEE3";
    private static final String DANGER_RED = "#D71920";
    private static final String EMERALD_GREEN = "#16A34A";

    private final String nurseEmail;
    private final String nurseName;
    private final String activeTripId;

    public NurseComplaint() {
        this("nurse1@lifelink.com", "Sarah Miller", "TRIP-01");
    }

    public NurseComplaint(String nurseEmail, String nurseName, String activeTripId) {
        this.nurseEmail = (nurseEmail != null && !nurseEmail.trim().isEmpty()) ? nurseEmail.trim() : "nurse1@lifelink.com";
        this.nurseName = (nurseName != null && !nurseName.trim().isEmpty()) ? nurseName.trim() : "Sarah Miller";
        this.activeTripId = (activeTripId != null && !activeTripId.trim().isEmpty()) ? activeTripId.trim() : "TRIP-01";
    }

    public VBox getComplaintSection() {

        VBox mainSection = new VBox(20);
        mainSection.setPadding(new Insets(35, 35, 38, 35));
        mainSection.setAlignment(Pos.TOP_CENTER);
        mainSection.setStyle("-fx-background-color: " + PAGE_BG + "; -fx-font-family: 'Segoe UI', -apple-system, sans-serif;");

        Text complaintTitle = new Text("Clinical & Transit Grievance Portal");
        complaintTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        VBox headingBox = new VBox(5, complaintTitle);
        headingBox.setMaxWidth(900);

        VBox complaintCard = new VBox(20);
        complaintCard.setPadding(new Insets(25));
        complaintCard.setMaxWidth(900);
        complaintCard.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-background-radius: 16px; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 16px; " +
                "-fx-effect: dropshadow(gaussian, rgba(230,117,147,0.08), 16, 0.1, 0, 4);"
        );

        Label formTitle = new Label("Dual-Party Incident Dispatch");
        formTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_TEXT + ";");

        Label formSubtitle = new Label("Reporting Nurse: " + nurseName + " (" + nurseEmail + ")  •  Unit: " + activeTripId);
        formSubtitle.setWrapText(true);
        formSubtitle.setStyle("-fx-font-size: 11px; -fx-text-fill: " + PRIMARY_PINK + "; -fx-font-weight: bold;");

        VBox formHeading = new VBox(5, formTitle, formSubtitle);

        // Target Driver Field
        Label driverTitle = new Label("TARGET DRIVER NAME");
        driverTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + MUTED_TEXT + ";");

        TextField driverField = new TextField();
        driverField.setPromptText("Enter target driver name");
        driverField.setPrefHeight(42);
        driverField.setMaxWidth(Double.MAX_VALUE);
        driverField.setStyle("-fx-font-size: 12px; -fx-background-color: " + VERY_LIGHT_PINK + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        VBox driverBoxSection = new VBox(7, driverTitle, driverField);

        // Target Hospital Field
        Label hospitalTitle = new Label("TARGET HOSPITAL NAME");
        hospitalTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + MUTED_TEXT + ";");

        TextField hospitalField = new TextField();
        hospitalField.setPromptText("Enter target hospital name");
        hospitalField.setPrefHeight(42);
        hospitalField.setMaxWidth(Double.MAX_VALUE);
        hospitalField.setStyle("-fx-font-size: 12px; -fx-background-color: " + VERY_LIGHT_PINK + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        VBox hospitalBoxSection = new VBox(7, hospitalTitle, hospitalField);

        HBox targetsRow = new HBox(15);
        targetsRow.getChildren().addAll(driverBoxSection, hospitalBoxSection);
        targetsRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(driverBoxSection, Priority.ALWAYS);
        HBox.setHgrow(hospitalBoxSection, Priority.ALWAYS);

        // Simultaneous Complain Categories for Driver & Hospital
        Label driverIssueTitle = new Label("DRIVER / TRANSIT GRIEVANCE");
        driverIssueTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + MUTED_TEXT + ";");

        ComboBox<String> driverIssueBox = new ComboBox<>();
        driverIssueBox.getItems().addAll(
                "Unsafe speed or erratic transit handling",
                "Failure to request police green corridor in time",
                "Poor route navigation or missing deviation checks",
                "Interpersonal communication breakdown with crew",
                "None / N/A"
        );
        driverIssueBox.setValue("None / N/A");
        driverIssueBox.setMaxWidth(Double.MAX_VALUE);
        driverIssueBox.setPrefHeight(42);
        driverIssueBox.setStyle("-fx-font-size: 12px; -fx-background-color: " + VERY_LIGHT_PINK + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        VBox driverSection = new VBox(7, driverIssueTitle, driverIssueBox);

        Label hospitalIssueTitle = new Label("HOSPITAL ER / RECEPTION GRIEVANCE");
        hospitalIssueTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + MUTED_TEXT + ";");

        ComboBox<String> hospitalIssueBox = new ComboBox<>();
        hospitalIssueBox.getItems().addAll(
                "ER trauma team unresponsiveness upon arrival",
                "ICU bed reservation denied despite prior sync",
                "Delayed handover documentation clearance",
                "Oxygen or blood bank reserve mismatch",
                "None / N/A"
        );
        hospitalIssueBox.setValue("None / N/A");
        hospitalIssueBox.setMaxWidth(Double.MAX_VALUE);
        hospitalIssueBox.setPrefHeight(42);
        hospitalIssueBox.setStyle("-fx-font-size: 12px; -fx-background-color: " + VERY_LIGHT_PINK + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        VBox hospitalSection = new VBox(7, hospitalIssueTitle, hospitalIssueBox);

        HBox issuesRow = new HBox(15, driverSection, hospitalSection);
        issuesRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(driverSection, Priority.ALWAYS);
        HBox.setHgrow(hospitalSection, Priority.ALWAYS);

        // Description
        Label descriptionTitle = new Label("JOINT INCIDENT NARRATIVE & CLINICAL LOG");
        descriptionTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + MUTED_TEXT + ";");

        TextArea complaintDescription = new TextArea();
        complaintDescription.setPromptText("Detail the simultaneous driver and hospital coordination issues observed during transit...");
        complaintDescription.setWrapText(true);
        complaintDescription.setPrefRowCount(6);
        complaintDescription.setMaxWidth(Double.MAX_VALUE);
        complaintDescription.setStyle("-fx-font-size: 12px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        VBox descriptionSection = new VBox(7, descriptionTitle, complaintDescription);

        Button sendComplaintButton = new Button("DISPATCH JOINT REPORT TO ADMIN");
        sendComplaintButton.setMaxWidth(Double.MAX_VALUE);
        sendComplaintButton.setMinHeight(48);
        sendComplaintButton.setStyle(
                "-fx-background-color: " + PRIMARY_PINK + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 9px; " +
                "-fx-padding: 12px; " +
                "-fx-cursor: hand;"
        );
        sendComplaintButton.setOnMouseEntered(e -> sendComplaintButton.setStyle("-fx-background-color: " + PINK_DARK + "; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 9px; -fx-padding: 12px; -fx-cursor: hand;"));
        sendComplaintButton.setOnMouseExited(e -> sendComplaintButton.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 9px; -fx-padding: 12px; -fx-cursor: hand;"));

        Label complaintMessage = new Label();
        complaintMessage.setWrapText(true);
        complaintMessage.setMaxWidth(Double.MAX_VALUE);
        complaintMessage.setAlignment(Pos.CENTER);

        sendComplaintButton.setOnAction(e -> {
            if (driverField.getText().trim().isEmpty() ||
                    hospitalField.getText().trim().isEmpty() ||
                    complaintDescription.getText().trim().isEmpty()) {

                complaintMessage.setText("Please enter target driver name, hospital name, and incident narrative.");
                complaintMessage.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + DANGER_RED + ";");
                return;
            }

            String driver = driverField.getText().trim();
            String hospital = hospitalField.getText().trim();
            String driverGrievance = driverIssueBox.getValue();
            String hospitalGrievance = hospitalIssueBox.getValue();
            String description = complaintDescription.getText().trim();

            sendComplaintButton.setDisable(true);
            complaintMessage.setText("Submitting joint nurse incident report to Firestore...");
            complaintMessage.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_PINK + ";");

            new Thread(() -> {
                try {
                    Firestore db = FirebaseConfig.getFirestore();
                    if (db == null) {
                        Platform.runLater(() -> {
                            complaintMessage.setText("Error: Firebase database not connected.");
                            complaintMessage.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + DANGER_RED + ";");
                            sendComplaintButton.setDisable(false);
                        });
                        return;
                    }

                    String complaintId = generateNextComplaintId(db);

                    Map<String, Object> complaintData = new HashMap<>();
                    complaintData.put("complaintId", complaintId);
                    complaintData.put("nurseEmail", nurseEmail);
                    complaintData.put("nurseName", nurseName);
                    complaintData.put("tripId", activeTripId);
                    complaintData.put("reportedByRole", "Emergency Triage Nurse");
                    complaintData.put("driverName", driver);
                    complaintData.put("targetHospital", hospital);
                    complaintData.put("driverGrievance", driverGrievance);
                    complaintData.put("hospitalGrievance", hospitalGrievance);
                    complaintData.put("description", description);
                    complaintData.put("status", "PENDING");
                    complaintData.put("priority", "HIGH");
                    complaintData.put("timestamp", Timestamp.now());

                    // Stored in "complaintByNurse" collection
                    db.collection("complaintByNurse")
                            .document(complaintId)
                            .set(complaintData)
                            .get();

                    Platform.runLater(() -> {
                        complaintMessage.setText("✓ Simultaneous report " + complaintId + " dispatched successfully to Admin.");
                        complaintMessage.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + EMERALD_GREEN + ";");

                        complaintDescription.clear();
                        driverField.clear();
                        hospitalField.clear();
                        driverIssueBox.setValue("None / N/A");
                        hospitalIssueBox.setValue("None / N/A");
                        sendComplaintButton.setDisable(false);
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Platform.runLater(() -> {
                        complaintMessage.setText("Failed to submit report: " + ex.getMessage());
                        complaintMessage.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + DANGER_RED + ";");
                        sendComplaintButton.setDisable(false);
                    });
                }
            }).start();
        });

        complaintCard.getChildren().addAll(
                formHeading,
                targetsRow,
                issuesRow,
                descriptionSection,
                sendComplaintButton,
                complaintMessage
        );

        mainSection.getChildren().addAll(headingBox, complaintCard);
        return mainSection;
    }

    private synchronized String generateNextComplaintId(Firestore db) throws Exception {
        int maxSeqNumber = 1000;
        QuerySnapshot snap = db.collection("complaintByNurse").get().get();

        for (DocumentSnapshot doc : snap.getDocuments()) {
            String existingId = doc.getString("complaintId");
            if (existingId == null || existingId.isEmpty()) {
                existingId = doc.getId();
            }

            if (existingId != null && existingId.startsWith("COMP-N-")) {
                try {
                    String numPart = existingId.substring("COMP-N-".length()).trim();
                    int parsed = Integer.parseInt(numPart);
                    if (parsed > maxSeqNumber) {
                        maxSeqNumber = parsed;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        return "COMP-N-" + (maxSeqNumber + 1);
    }
}