package com.kurukshetra.view.police;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PoliceSignIn {

    private Scene signInScene;

    public Scene getSignInScene() {

        VBox mainBox = new VBox(20);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(40));
        mainBox.setStyle("-fx-background-color: #f8f8ff;"); // Match dashboard background

        Text title = new Text("LifeLink");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-fill: #111827;"); // Match dashboard primary text

        Text subTitle = new Text("Police Control Room");
        subTitle.setStyle("-fx-font-size: 16px; -fx-fill: #6b7280;"); // Match dashboard subtitle text

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(300);
        usernameField.setPrefHeight(45);
        usernameField.setMaxWidth(300);
        usernameField.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #d1d5db; -fx-padding: 10px; -fx-text-fill: #111827;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(300);
        passwordField.setPrefHeight(45);
        passwordField.setMaxWidth(300);
        passwordField.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #d1d5db; -fx-padding: 10px; -fx-text-fill: #111827;");

        Button signInButton = new Button("Sign In");
        signInButton.setPrefWidth(300);
        signInButton.setPrefHeight(45);
        signInButton.setStyle("-fx-background-color: #3949ab; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand;");

        mainBox.getChildren().addAll(
                title,
                subTitle,
                usernameField,
                passwordField,
                signInButton
        );

        signInScene = new Scene(mainBox, 1544, 826);

        return signInScene;
    }
}