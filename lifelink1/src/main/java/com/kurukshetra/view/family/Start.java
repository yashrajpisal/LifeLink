package com.kurukshetra.view.family;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Start extends Application {

    public static Stage mainStage;

    @Override
    public void start(Stage stage) {
        mainStage = stage;

        // Start on the Dashboard / Home Page
        FamilyHomePage homePage = new FamilyHomePage();
        BorderPane bp = homePage.setBorderPane(stage);

        Scene sc = new Scene(bp, 1280, 800);
        sc.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());

        stage.setTitle("LifeLink");
        stage.setScene(sc);
        stage.setMaximized(true);
        stage.show();
    }
}
