package com.kurukshetra.view.demo;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.kurukshetra.config.FirebaseConfig;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AdminEmergencyDispatch extends Application {

    private TextField driverEmailField;
    private TextField patientNameField;
    private ComboBox<String> emergencyTypeBox;
    private TextField locationField;
    private ComboBox<String> priorityBox;
    private Button dispatchBtn;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("LifeLink - Admin Emergency Dispatch Console");

        // 1. Root Container
        VBox root = new VBox(18);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #f8fafc;");
        root.setAlignment(Pos.TOP_CENTER);

        // 2. Header
        Label headerLabel = new Label("Dispatch Emergency to Ambulance Driver");
        headerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        Label subHeader = new Label("Fill in patient and location details to alert active drivers.");
        subHeader.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b;");

        // 3. Form Grid
        GridPane formGrid = new GridPane();
        formGrid.setHgap(12);
        formGrid.setVgap(14);
        formGrid.setAlignment(Pos.CENTER);

        // Driver Email Field
        Label driverLabel = new Label("Driver Email:");
        driverLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #334155;");
        driverEmailField = new TextField();
        driverEmailField.setPromptText("e.g., driver1@lifelink.com");
        driverEmailField.setText("driver1@lifelink.com"); // Pre-filled default for quick testing
        driverEmailField.setPrefWidth(300);

        // Patient Name Field
        Label patientLabel = new Label("Patient Name:");
        patientLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #334155;");
        patientNameField = new TextField();
        patientNameField.setPromptText("e.g., Ramesh Sharma");

        // Emergency Type Dropdown
        Label typeLabel = new Label("Emergency Type:");
        typeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #334155;");
        emergencyTypeBox = new ComboBox<>();
        emergencyTypeBox.getItems().addAll(
            "Cardiac Arrest", 
            "Road Accident / Trauma", 
            "Respiratory Distress", 
            "Stroke / Neurological", 
            "Severe Burn", 
            "Maternity Emergency"
        );
        emergencyTypeBox.setValue("Road Accident / Trauma");
        emergencyTypeBox.setPrefWidth(300);

        // Pickup Location Field
        Label locLabel = new Label("Pickup Location:");
        locLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #334155;");
        locationField = new TextField();
        locationField.setPromptText("e.g., Karjat Station Road, Sector 4");

        // Priority Dropdown
        Label priorityLabel = new Label("Priority Level:");
        priorityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #334155;");
        priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("CRITICAL", "HIGH", "MEDIUM", "LOW");
        priorityBox.setValue("CRITICAL");
        priorityBox.setPrefWidth(300);

        // Add to grid
        formGrid.add(driverLabel, 0, 0);
        formGrid.add(driverEmailField, 1, 0);

        formGrid.add(patientLabel, 0, 1);
        formGrid.add(patientNameField, 1, 1);

        formGrid.add(typeLabel, 0, 2);
        formGrid.add(emergencyTypeBox, 1, 2);

        formGrid.add(locLabel, 0, 3);
        formGrid.add(locationField, 1, 3);

        formGrid.add(priorityLabel, 0, 4);
        formGrid.add(priorityBox, 1, 4);

        // 4. Action Buttons & Feedback
        dispatchBtn = new Button("Dispatch Emergency Alert");
        dispatchBtn.setStyle("-fx-background-color: #dc2626; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 24; -fx-background-radius: 6; -fx-cursor: hand;");
        dispatchBtn.setOnAction(e -> handleDispatch());

        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #475569;");

        HBox btnContainer = new HBox(dispatchBtn);
        btnContainer.setAlignment(Pos.CENTER);
        btnContainer.setPadding(new Insets(10, 0, 0, 0));

        root.getChildren().addAll(headerLabel, subHeader, formGrid, btnContainer, statusLabel);

        // 5. Scene Setup
        Scene scene = new Scene(root, 520, 520);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleDispatch() {
        String driverEmail = driverEmailField.getText().trim();
        String patientName = patientNameField.getText().trim();
        String emergencyType = emergencyTypeBox.getValue();
        String location = locationField.getText().trim();
        String priority = priorityBox.getValue();

        // Validation
        if (driverEmail.isEmpty() || patientName.isEmpty() || location.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please complete all fields before dispatching.");
            return;
        }

        dispatchBtn.setDisable(true);
        statusLabel.setText("Connecting to Firestore and dispatching...");

        // Run network database call on background thread to prevent UI freezing
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) {
                    Platform.runLater(() -> {
                        statusLabel.setText("Database connection error!");
                        dispatchBtn.setDisable(false);
                    });
                    return;
                }

                // 1. Build Google Maps Turn-by-Turn URL
                String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);
                String googleMapUrl = "https://www.google.com/maps/dir/?api=1&destination=" + encodedLocation;

                // 2. Generate Auto ID Document Reference
                DocumentReference docRef = db.collection("driversideEmergency").document();
                String autoEmergencyId = docRef.getId();

                // 3. Assemble Payload
                Map<String, Object> data = new HashMap<>();
                data.put("emergencyId", autoEmergencyId);
                data.put("assignedDriverEmail", driverEmail);
                data.put("patientName", patientName);
                data.put("emergencyType", emergencyType);
                data.put("pickupLocation", location);
                data.put("googleMapUrl", googleMapUrl);
                data.put("priority", priority);
                data.put("status", "PENDING");
                data.put("timestamp", Timestamp.now());

                // 4. Save to Firestore
                docRef.set(data).get();

                // 5. Update UI on Success
                Platform.runLater(() -> {
                    statusLabel.setText("Emergency " + autoEmergencyId + " dispatched successfully!");
                    dispatchBtn.setDisable(false);

                    // Clear fields for next alert
                    patientNameField.clear();
                    locationField.clear();

                    showAlert(Alert.AlertType.INFORMATION, "Dispatch Successful", 
                              "Emergency alert sent to: " + driverEmail + "\nID: " + autoEmergencyId);
                });

            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    statusLabel.setText("Failed to dispatch: " + ex.getMessage());
                    dispatchBtn.setDisable(false);
                    showAlert(Alert.AlertType.ERROR, "Dispatch Error", ex.getMessage());
                });
            }
        }).start();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        launch(args);
    }
}