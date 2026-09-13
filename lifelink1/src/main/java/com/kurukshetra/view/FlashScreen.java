package com.kurukshetra.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.net.URL;

public class FlashScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            URL resource = getClass().getResource("/assets/video/flash screen video with hand blessing.mp4");
            if (resource == null) {
                System.err.println("Flash screen video not found, skipping...");
                fallbackToWelcome(primaryStage);
                return;
            }

            Media media = new Media(resource.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);

            StackPane root = new StackPane(mediaView);
            root.setStyle("-fx-background-color: black;");

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            
            // Set maximized before bindings for better initial size
            primaryStage.setMaximized(true);

            // Bind media view to window size
            mediaView.fitWidthProperty().bind(scene.widthProperty());
            mediaView.fitHeightProperty().bind(scene.heightProperty());
            mediaView.setPreserveRatio(true);

            // Transition to Welcome screen when video ends
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.dispose();
                fallbackToWelcome(primaryStage);
            });

            // Fallback on error
            mediaPlayer.setOnError(() -> {
                System.err.println("Error playing flash screen video: " + mediaPlayer.getError());
                if (mediaPlayer.getError() != null) {
                    mediaPlayer.getError().printStackTrace();
                }
                mediaPlayer.dispose();
                fallbackToWelcome(primaryStage);
            });

            primaryStage.show();
            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
            fallbackToWelcome(primaryStage);
        }
    }

    private void fallbackToWelcome(Stage primaryStage) {
        try {
            new Welcome().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
