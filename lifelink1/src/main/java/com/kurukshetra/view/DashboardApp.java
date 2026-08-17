package com.kurukshetra.view;

import com.kurukshetra.view.NativeCallScreen;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DashboardApp extends Application {

    private Stage stage;
    private Scene dashboardScene;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        VBox dashboardRoot = new VBox(20);
        dashboardRoot.setAlignment(Pos.CENTER);
        dashboardRoot.setPadding(new Insets(30));
        dashboardRoot.setStyle("-fx-background-color: #121222;");

        Label title = new Label("Nurse Station Dashboard");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);

        Label ipLabel = new Label("Enter Friend / Patient Laptop IP:");
        ipLabel.setTextFill(Color.web("#deb7ff"));

        TextField ipField = new TextField("192.168.1.10"); // Replace with friend's IP
        ipField.setMaxWidth(250);
        ipField.setStyle("-fx-background-color: #1e1e2f; -fx-text-fill: white; -fx-padding: 8px; -fx-background-radius: 8px;");

        // The Call Button
        Button startCallBtn = new Button("Start Video & Voice Call");
        startCallBtn.setStyle("-fx-background-color: #ba54f5; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20px; -fx-padding: 12px 28px; -fx-cursor: hand;");

        startCallBtn.setOnAction(e -> {
            String targetIp = ipField.getText().trim();
            // Switch current view to NativeCallScreen directly inside the dashboard
            NativeCallScreen callScreen = new NativeCallScreen(targetIp, () -> {
                stage.setScene(dashboardScene); // Return back to dashboard when call ends
            });
            stage.setScene(new Scene(callScreen, 850, 600));
        });

        dashboardRoot.getChildren().addAll(title, ipLabel, ipField, startCallBtn);
        dashboardScene = new Scene(dashboardRoot, 850, 600);

        primaryStage.setTitle("Nurse Station - Video Call Client");
        primaryStage.setScene(dashboardScene);
        primaryStage.show();
    }

}