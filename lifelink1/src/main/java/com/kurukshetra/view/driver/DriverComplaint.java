package com.kurukshetra.view.driver;

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

public class DriverComplaint {

    private static final String BLUE = "#29B6E8";
    private static final String BLUE_DARK = "#0694C8";
    private static final String LIGHT_BLUE = "#E0F7FD";
    private static final String SURFACE = "#FFFFFF";
    private static final String TEXT = "#0A2540";
    private static final String SECONDARY = "#4A6A85";
    private static final String BORDER = "#B8E4F5";
    private static final String RED = "#D71920";
    private static final String GREEN = "#20B86A";

    private final String driverEmail;
    private final String activeTripId;

    public DriverComplaint() {
        this("driver1@lifelink.com", "TRIP-01");
    }

    public DriverComplaint(String driverEmail, String activeTripId) {
        this.driverEmail = (driverEmail != null && !driverEmail.trim().isEmpty()) ? driverEmail.trim() : "driver1@lifelink.com";
        this.activeTripId = (activeTripId != null && !activeTripId.trim().isEmpty()) ? activeTripId.trim() : "TRIP-01";
    }

    public VBox getComplaintSection() {
        VBox mainSection = new VBox(20);
        mainSection.setPadding(new Insets(25, 35, 30, 35));
        mainSection.setAlignment(Pos.TOP_CENTER);
        mainSection.setStyle("-fx-background-color: #ccdde7ff; -fx-font-family: 'Segoe UI', -apple-system, sans-serif;");

        Text complaintTitle = new Text("Driver Grievance & Transit Issue Portal");
        complaintTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT + ";");

        Text complaintSubtitle = new Text("Report operational delays, nurse coordination breakdowns, or hospital admission friction during transit.");
        complaintSubtitle.setStyle("-fx-font-size: 12.5px; -fx-fill: " + SECONDARY + ";");

        VBox headingBox = new VBox(5, complaintTitle, complaintSubtitle);
        headingBox.setMaxWidth(900);

        VBox complaintCard = new VBox(20);
        complaintCard.setPadding(new Insets(25));
        complaintCard.setMaxWidth(900);
        complaintCard.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-background-radius: 16px; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-radius: 16px; " +
                "-fx-effect: dropshadow(gaussian, rgba(8,161,229,0.08), 16, 0.1, 0, 4);"
        );

        Label formTitle = new Label("Driver Incident Dispatch");
        formTitle.setStyle("-fx-font-size: 15.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT + ";");

        Label formSubtitle = new Label("Logged-in Driver: " + driverEmail + "  •  Active Unit: " + activeTripId);
        formSubtitle.setWrapText(true);
        formSubtitle.setStyle("-fx-font-size: 11.5px; -fx-text-fill: " + BLUE + "; -fx-font-weight: bold;");

        VBox formHeading = new VBox(5, formTitle, formSubtitle);

        // Target Nurse Field
        Label nurseTitle = new Label("TARGET NURSE NAME");
        nurseTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + SECONDARY + ";");

        TextField nurseField = new TextField();
        nurseField.setPromptText("Enter accompanying nurse name");
        nurseField.setPrefHeight(42);
        nurseField.setMaxWidth(Double.MAX_VALUE);
        nurseField.setStyle("-fx-font-size: 12.5px; -fx-background-color: " + LIGHT_BLUE + "; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        VBox nurseBoxSection = new VBox(7, nurseTitle, nurseField);

        // Target Hospital Field
        Label hospitalTitle = new Label("TARGET HOSPITAL NAME");
        hospitalTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + SECONDARY + ";");

        TextField hospitalField = new TextField();
        hospitalField.setPromptText("Enter destination hospital name");
        hospitalField.setPrefHeight(42);
        hospitalField.setMaxWidth(Double.MAX_VALUE);
        hospitalField.setStyle("-fx-font-size: 12px; -fx-background-color: " + LIGHT_BLUE + "; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        VBox hospitalBoxSection = new VBox(7, hospitalTitle, hospitalField);

        HBox targetsRow = new HBox(15, nurseBoxSection, hospitalBoxSection);
        targetsRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(nurseBoxSection, Priority.ALWAYS);
        HBox.setHgrow(hospitalBoxSection, Priority.ALWAYS);

        // Complaint Category
        Label complaintTypeTitle = new Label("ISSUE CATEGORY");
        complaintTypeTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + SECONDARY + ";");

        ComboBox<String> complaintType = new ComboBox<>();
        complaintType.getItems().addAll(
                "Nurse unresponsiveness or lack of coordination during transit",
                "Hospital emergency gate delay / security hold-up",
                "Incorrect routing information provided by dispatch",
                "Unsafe patient loading / unloading conditions",
                "Other"
        );
        complaintType.setPromptText("Select issue category");
        complaintType.setMaxWidth(Double.MAX_VALUE);
        complaintType.setPrefHeight(42);
        complaintType.setStyle("-fx-font-size: 12px; -fx-background-color: " + LIGHT_BLUE + "; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        VBox complaintTypeSection = new VBox(7, complaintTypeTitle, complaintType);

        // Description
        Label descriptionTitle = new Label("INCIDENT NARRATIVE & DETAILS");
        descriptionTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + SECONDARY + ";");

        TextArea complaintDescription = new TextArea();
        complaintDescription.setPromptText("Describe the transit issue clearly so that Admin can review it...");
        complaintDescription.setWrapText(true);
        complaintDescription.setPrefRowCount(6);
        complaintDescription.setMaxWidth(Double.MAX_VALUE);
        complaintDescription.setStyle("-fx-font-size: 12.5px; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        VBox descriptionSection = new VBox(7, descriptionTitle, complaintDescription);

        Button sendComplaintButton = new Button("SUBMIT COMPLAINT TO ADMIN");
        sendComplaintButton.setMaxWidth(Double.MAX_VALUE);
        sendComplaintButton.setMinHeight(48);
        sendComplaintButton.setStyle(
                "-fx-background-color: " + BLUE + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 13.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 9px; " +
                "-fx-padding: 12px; " +
                "-fx-cursor: hand;"
        );
        sendComplaintButton.setOnMouseEntered(e -> sendComplaintButton.setStyle("-fx-background-color: " + BLUE_DARK + "; -fx-text-fill: white; -fx-font-size: 13.5px; -fx-font-weight: bold; -fx-background-radius: 9px; -fx-padding: 12px; -fx-cursor: hand;"));
        sendComplaintButton.setOnMouseExited(e -> sendComplaintButton.setStyle("-fx-background-color: " + BLUE + "; -fx-text-fill: white; -fx-font-size: 13.5px; -fx-font-weight: bold; -fx-background-radius: 9px; -fx-padding: 12px; -fx-cursor: hand;"));

        Label complaintMessage = new Label();
        complaintMessage.setWrapText(true);
        complaintMessage.setMaxWidth(Double.MAX_VALUE);
        complaintMessage.setAlignment(Pos.CENTER);

        sendComplaintButton.setOnAction(e -> {
            if (nurseField.getText().trim().isEmpty() ||
                    hospitalField.getText().trim().isEmpty() ||
                    complaintType.getValue() == null ||
                    complaintDescription.getText().trim().isEmpty()) {

                complaintMessage.setText("Please fill all complaint details.");
                complaintMessage.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + RED + ";");
                return;
            }

            String targetNurse = nurseField.getText().trim();
            String targetHospital = hospitalField.getText().trim();
            String type = complaintType.getValue();
            String description = complaintDescription.getText().trim();

            sendComplaintButton.setDisable(true);
            complaintMessage.setText("Submitting report to Admin via Firestore...");
            complaintMessage.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + BLUE + ";");

            new Thread(() -> {
                try {
                    Firestore db = FirebaseConfig.getFirestore();
                    if (db == null) {
                        Platform.runLater(() -> {
                            complaintMessage.setText("Error: Firebase database not connected.");
                            complaintMessage.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + RED + ";");
                            sendComplaintButton.setDisable(false);
                        });
                        return;
                    }

                    String complaintId = generateNextComplaintId(db);

                    Map<String, Object> complaintData = new HashMap<>();
                    complaintData.put("complaintId", complaintId);
                    complaintData.put("driverEmail", driverEmail);
                    complaintData.put("tripId", activeTripId);
                    complaintData.put("reportedByRole", "Ambulance Driver");
                    complaintData.put("targetNurse", targetNurse);
                    complaintData.put("targetHospital", targetHospital);
                    complaintData.put("complaintType", type);
                    complaintData.put("description", description);
                    complaintData.put("status", "PENDING");
                    complaintData.put("priority", "HIGH");
                    complaintData.put("timestamp", Timestamp.now());

                    // Storing in "complainByDriver" collection
                    db.collection("complainByDriver")
                            .document(complaintId)
                            .set(complaintData)
                            .get();

                    Platform.runLater(() -> {
                        complaintMessage.setText("✓ Complaint " + complaintId + " dispatched successfully to Admin.");
                        complaintMessage.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + GREEN + ";");

                        complaintDescription.clear();
                        nurseField.clear();
                        hospitalField.clear();
                        complaintType.setValue(null);
                        sendComplaintButton.setDisable(false);
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Platform.runLater(() -> {
                        complaintMessage.setText("Failed to submit report: " + ex.getMessage());
                        complaintMessage.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + RED + ";");
                        sendComplaintButton.setDisable(false);
                    });
                }
            }).start();
        });

        complaintCard.getChildren().addAll(
                formHeading,
                targetsRow,
                complaintTypeSection,
                descriptionSection,
                sendComplaintButton,
                complaintMessage
        );

        mainSection.getChildren().addAll(headingBox, complaintCard);
        return mainSection;
    }

    private synchronized String generateNextComplaintId(Firestore db) throws Exception {
        int maxSeqNumber = 1000;
        QuerySnapshot snap = db.collection("complainByDriver").get().get();

        for (DocumentSnapshot doc : snap.getDocuments()) {
            String existingId = doc.getString("complaintId");
            if (existingId == null || existingId.isEmpty()) {
                existingId = doc.getId();
            }

            if (existingId != null && existingId.startsWith("COMP-D-")) {
                try {
                    String numPart = existingId.substring("COMP-D-".length()).trim();
                    int parsed = Integer.parseInt(numPart);
                    if (parsed > maxSeqNumber) {
                        maxSeqNumber = parsed;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        return "COMP-D-" + (maxSeqNumber + 1);
    }
}