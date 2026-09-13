// package com.example.view.voice;


// import javafx.application.Application;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.layout.VBox;
// import javafx.stage.Stage;

// public class MainView extends Application {

//     @Override
//     public void start(Stage primaryStage) {
//         // 1. Create UI Components
//         Label titleLabel = new Label("Welcome to the Application");
//         titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

//         Button actionBtn = new Button("Click Me");
//         Label statusLabel = new Label();

//         // 2. Set Event Handlers
//         actionBtn.setOnAction(e -> statusLabel.setText("Button clicked!"));

//         // 3. Layout Setup
//         VBox root = new VBox(15);
//         root.setAlignment(Pos.CENTER);
//         root.getChildren().addAll(titleLabel, actionBtn, statusLabel);

//         // 4. Create and Show Scene
//         Scene scene = new Scene(root, 600, 400);
//         primaryStage.setTitle("Dashboard View");
//         primaryStage.setScene(scene);
//         primaryStage.show();
//     }

//     public static void main(String[] args) {
//         launch(args);
//     }
// }
