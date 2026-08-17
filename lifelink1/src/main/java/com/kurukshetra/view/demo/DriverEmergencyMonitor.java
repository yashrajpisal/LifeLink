package com.kurukshetra.view.demo;

import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.Firestore;
import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.driverModel.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;

public class DriverEmergencyMonitor extends Application {

    private VBox emergencyContainer;
    private Label statusLabel;
    private Firestore db;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("LifeLink - Realtime Driver Emergency Dispatch");

        // 1. Root Layout Setup
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f6f9;");

        // 2. Header
        Label headerLabel = new Label("Active Emergency Dispatches");
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1a202c;");

        statusLabel = new Label("Connecting to Firestore...");
        statusLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #718096;");

        // 3. Scrollable List for Incoming Emergency Cards
        emergencyContainer = new VBox(12);
        emergencyContainer.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(emergencyContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().addAll(headerLabel, statusLabel, scrollPane);

        // 4. Scene Configuration
        Scene scene = new Scene(root, 650, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        // 5. Initialize Firebase & Start Background Realtime Listener
        startFirestoreListener();
    }

    private void startFirestoreListener() {
        new Thread(() -> {
            try {
                db = FirebaseConfig.getFirestore();
                if (db == null) {
                    Platform.runLater(() -> statusLabel.setText("Database connection failed."));
                    return;
                }

                // Get driver email from Singleton UserModel or use default fallback for standalone testing
                // String driverEmail = UserModel.getInstance().getEmail();
                String driverEmail = "driver1@lifelink.com";
                if (driverEmail == null || driverEmail.isEmpty()) {
                    driverEmail = "driver1@lifelink.com"; 
                }

                final String finalDriverEmail = driverEmail;
                Platform.runLater(() -> statusLabel.setText("Listening for emergencies assigned to: " + finalDriverEmail));

                // Listen to Firestore collection in real time
                db.collection("driversideEmergency")
                  .whereEqualTo("assignedDriverEmail", finalDriverEmail)
                  .whereEqualTo("status", "PENDING")
                  .addSnapshotListener((snapshots, error) -> {
                      if (error != null) {
                          System.err.println("Firestore Listen Error: " + error.getMessage());
                          return;
                      }

                      if (snapshots != null && !snapshots.isEmpty()) {
                          for (DocumentChange change : snapshots.getDocumentChanges()) {
                              if (change.getType() == DocumentChange.Type.ADDED) {
                                  var doc = change.getDocument();

                                  String emergencyId = doc.getId();
                                  String patient = doc.getString("patientName");
                                  String type = doc.getString("emergencyType");
                                  String location = doc.getString("pickupLocation");
                                  String priority = doc.getString("priority");
                                  String mapUrl = doc.getString("googleMapUrl");

                                  // Update UI on the JavaFX Application Thread
                                  Platform.runLater(() -> {
                                      renderEmergencyCard(emergencyId, patient, type, location, priority, mapUrl);
                                      showEmergencyPopup(patient, type, location, mapUrl);
                                  });
                              }
                          }
                      }
                  });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void renderEmergencyCard(String id, String patient, String type, String location, String priority, String mapUrl) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");

        HBox topRow = new HBox(10);
        Label typeLabel = new Label(type != null ? type : "Emergency Alert");
        typeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #e53e3e;");

        Label priorityBadge = new Label(priority != null ? priority : "HIGH");
        priorityBadge.setStyle("-fx-background-color: #fed7d7; -fx-text-fill: #9b2c2c; -fx-padding: 2 8; -fx-background-radius: 4; -fx-font-weight: bold; -fx-font-size: 11px;");

        topRow.getChildren().addAll(typeLabel, priorityBadge);

        Label patientLabel = new Label("Patient: " + (patient != null ? patient : "Unknown"));
        patientLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #2d3748;");

        Label locationLabel = new Label("Location: " + (location != null ? location : "Not specified"));
        locationLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #4a5568;");

        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);

        Button navButton = new Button("Open Google Maps Navigation");
        navButton.setStyle("-fx-background-color: #3182ce; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 6 12; -fx-background-radius: 4; -fx-cursor: hand;");
        navButton.setOnAction(e -> openMap(mapUrl));

        actions.getChildren().add(navButton);
        card.getChildren().addAll(topRow, patientLabel, locationLabel, actions);

        emergencyContainer.getChildren().add(0, card);
    }

    private void showEmergencyPopup(String patient, String type, String location, String mapUrl) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CRITICAL DISPATCH ALERT");
        alert.setHeaderText("New Alert: " + type);
        alert.setContentText("Patient: " + patient + "\nPickup: " + location + "\n\nDo you want to open turn-by-turn navigation now?");

        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                openMap(mapUrl);
            }
        });
    }

    private void openMap(String mapUrl) {
        if (mapUrl != null && !mapUrl.isEmpty()) {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(mapUrl));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        launch(args);
    }
}