package com.kurukshetra.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class otp{

    // 6 fields
    private TextField[] otpFields = new TextField[6];

    public void show(Stage otpStage) {

        VBox mainContainer = new VBox();
        mainContainer.setStyle("-fx-background-color: #F0F1FA;");
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPrefSize(1080, 720);

        VBox card = createCard();
        mainContainer.getChildren().add(card);

        Scene scene = new Scene(mainContainer);
        otpStage.setTitle("OTP Verification");
        otpStage.setScene(scene);
        otpStage.show();
    }

    private VBox createCard() {
        VBox card = new VBox();
        card.setStyle("-fx-background-color: white; -fx-border-radius: 15; -fx-background-radius: 15;");
        card.setPrefSize(450, 550);
        card.setMaxWidth(450);
        card.setPadding(new Insets(40, 45, 35, 45));
        card.setSpacing(20);
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle(card.getStyle() + " -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        
        VBox lockIconBox = createLockIcon();
        lockIconBox.setPrefHeight(60);

        Label title = new Label("Verify Identity");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#333333"));

        Label message = new Label("We've sent a 6-digit secure code to your\nregistered device. Please enter it below to\nproceed.");
        message.setFont(Font.font("System", 14));
        message.setTextFill(Color.web("#666666")); 
        message.setTextAlignment(TextAlignment.CENTER);
        message.setWrapText(true);
        message.setLineSpacing(4);

        HBox otpInputBox = createOTPInputFields();

        HBox timerBox = createTimer();

        Button verifyBtn = createVerifyBtn();

        Label encryptedLabel = new Label("🔒 End-to-End Encrypted Login");
        encryptedLabel.setFont(Font.font("System", 11));
        encryptedLabel.setTextFill(Color.web("#999999"));

        VBox.setMargin(message, new Insets(0, 0, 5, 0));
        VBox.setMargin(otpInputBox, new Insets(5, 0, 5, 0));
        VBox.setMargin(verifyBtn, new Insets(5, 0, 0, 0));
        
        card.getChildren().addAll(lockIconBox, title, message, otpInputBox, timerBox, verifyBtn, encryptedLabel);
        return card;

    }

   private Button createVerifyBtn() {
        Button verifyButton = new Button("Verify & Login   →");
        verifyButton.setPrefWidth(350);
        verifyButton.setPrefHeight(50);
        verifyButton.setFont(Font.font("System", FontWeight.BOLD, 16));
        verifyButton.setTextFill(Color.WHITE);
        verifyButton.setStyle(
                "-fx-background-color: #006B8F; " + // Primary blue color
                "-fx-background-radius: 8; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 12 20; " +
                "-fx-font-weight: bold;"
        );

        // hovereffect
        verifyButton.setOnMouseEntered(e->
                verifyButton.setStyle(
                        "-fx-background-color: #005070; " + 
                        "-fx-background-radius: 8; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 12 20; " +
                        "-fx-font-weight: bold;"
                )
        );

        //Reset color on mouse exit
        verifyButton.setOnMouseExited(e ->
                verifyButton.setStyle(
                        "-fx-background-color: #006B8F; " +
                        "-fx-background-radius: 8; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 12 20; " +
                        "-fx-font-weight: bold;"
                )
        );

        return verifyButton;
    }

    private HBox createTimer() {

        HBox timerBox = new HBox();
        timerBox.setAlignment(Pos.CENTER_LEFT);   
        timerBox.setPrefHeight(30);

        Label timerLabel = new Label("⏱ 02:00");
        timerLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        timerLabel.setTextFill(Color.web("#555555"));

        Label resendLabel = new Label("Resend code");
        resendLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        resendLabel.setTextFill(Color.web("#006B8F"));
        resendLabel.setStyle("-fx-cursor: hand;");
        resendLabel.setOnMouseEntered(e -> resendLabel.setUnderline(true));
        resendLabel.setOnMouseExited(e -> resendLabel.setUnderline(false));

        // Spacer to push resend to right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        timerBox.getChildren().addAll(timerLabel, spacer, resendLabel);

        // ===== DYNAMIC COUNTDOWN LOGIC =====
        int[] secondsLeft = {120}; 

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (secondsLeft[0] > 0) {
                secondsLeft[0]--;
                int minutes = secondsLeft[0] / 60;
                int seconds = secondsLeft[0] % 60;
                timerLabel.setText(String.format("⏱ %02d:%02d", minutes, seconds));
            } else {
                timerLabel.setText("⏱ 00:00 - Expired");
                timerLabel.setTextFill(Color.web("#E53935"));
            }
        }));
        timeline.setCycleCount(120);
        timeline.play();

        // ===== RESEND resets timer =====
        resendLabel.setOnMouseClicked(e -> {
            secondsLeft[0] = 178;
            timerLabel.setTextFill(Color.web("#555555"));
            timerLabel.setText("⏱ 02:58");
        });

        return timerBox;
    }

    private HBox createOTPInputFields() {
        HBox otpBox = new HBox();
        otpBox.setAlignment(Pos.CENTER);
        otpBox.setSpacing(8); 
        otpBox.setPrefHeight(70);

        //focused color 
        String focusedStyle =
                "-fx-font-size: 20; " +
                "-fx-text-alignment: center; " +
                "-fx-border-color: #05809f; " +   
                "-fx-border-radius: 10; " +
                "-fx-border-width: 2.5; " +
                "-fx-background-radius: 10; " +
                "-fx-background-color: #e7f5f6e3; " + 
                "-fx-padding: 0; " +
                "-fx-font-weight: bold;" +
                "-fx-alignment: center;";

        String unfocusedStyle =
                "-fx-font-size: 20; " +
                "-fx-text-alignment: center; " +
                "-fx-border-color: #D1D5DB; " +   
                "-fx-border-radius: 10; " +
                "-fx-border-width: 2; " +
                "-fx-background-radius: 10; " +
                "-fx-background-color: white; " +
                "-fx-padding: 0; " +
                "-fx-font-weight: bold;" +
                "-fx-alignment: center;";

        for(int i=0; i<6; i++){
            TextField otpField = new TextField();
            otpField.setPrefWidth(55);
            otpField.setPrefHeight(55);
            otpField.setAlignment(Pos.CENTER);
            otpField.setMaxWidth(55);

            otpField.setStyle(unfocusedStyle);

            final int index = i; // needed for use inside lambda

            //highlight only the currently focused field
            otpField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (isNowFocused) {
                    otpField.setStyle(focusedStyle);
                } else {
                    otpField.setStyle(unfocusedStyle);
                }
            });

            // VALIDATE + AUTO-ADVANCE to next field
            otpField.textProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal.matches("[0-9]*")) {
                    otpField.setText(oldVal); // reject non-digits
                    return;
                }
                if (newVal.length() > 1) {
                    // keep only the last typed character
                    otpField.setText(newVal.substring(newVal.length() - 1));
                    return;
                }
                // Move to next field automatically after typing a digit
                if (newVal.length() == 1 && index < 5) {
                    otpFields[index + 1].requestFocus();
                }
            });

            // BACKSPACE goes to previous field when current is empty
            otpField.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.BACK_SPACE && otpField.getText().isEmpty() && index > 0) {
                    otpFields[index - 1].requestFocus();
                    otpFields[index - 1].clear();
                }
            });

            otpFields[i] = otpField; // store reference
            otpBox.getChildren().add(otpField);
        }

        return otpBox;
    }

    private VBox createLockIcon() {
        VBox iconContainer = new VBox();
        iconContainer.setAlignment(Pos.CENTER);
        iconContainer.setPrefHeight(60);

        Circle circle = new Circle(30);
        circle.setFill(Color.web("#DFF0F8"));

        Label lockLabel = new Label("🔒");
        lockLabel.setFont(Font.font("System", 28));

        StackPane iconStack = new StackPane(circle, lockLabel);  
        iconContainer.getChildren().add(iconStack);
        
        return iconContainer;
        
    }
    
}
