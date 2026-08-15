package com.kurukshetra.view.police;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SignIn {

    private Scene signInScene;

    public Scene getSignInScene() {

        VBox mainBox = new VBox(20);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(40));
        mainBox.setStyle("-fx-background-color: #faf8ff;");

        Text title = new Text("LifeLink");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: #006591;");

        Text subTitle = new Text("Police Control Room");
        subTitle.setStyle("-fx-font-size: 15px; -fx-fill: #3e4850;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(300);
        usernameField.setPrefHeight(45);
        usernameField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #bec8d2; -fx-padding: 10px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(300);
        passwordField.setPrefHeight(45);
        passwordField.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #bec8d2; -fx-padding: 10px;");

        Button signInButton = new Button("Sign In");
        signInButton.setPrefWidth(300);
        signInButton.setPrefHeight(45);
        signInButton.setStyle("-fx-background-color: #006591; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

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