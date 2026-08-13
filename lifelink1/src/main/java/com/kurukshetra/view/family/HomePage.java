package com.kurukshetra.view.family;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage extends Application{

    public Stage HomePageStage;
    public Scene HomePageScene;
    public BorderPane HomePageBoarderPane;
    @Override
    public void start(Stage arg0) throws Exception {
        HomePageStage = arg0;

        Text t1 = new Text("Right side");
        Button goToAiAssistance = new Button("Go to Ai Assistance Page");
    
        goToAiAssistance.setOnAction(event -> {
            System.out.println("Go to next Page");
            AICareAssistant obj = new AICareAssistant();
            Scene sc = new Scene(obj.setBorderPane());
            sc.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            HomePageStage.setScene(sc);
        });

        Text t2 = new Text("Left side");
        // Button goToAiAssistance = new Button("Go to Ai Assistance Page");
    
        // goToAiAssistance.setOnAction(event -> {
        //     System.out.println("Go to next Page");

        // });

        VBox vb1 = new VBox(20,t1,goToAiAssistance);
        vb1.setStyle("-fx-border-color : black");

        VBox vb2 = new VBox(20,t2);
        vb2.setStyle("-fx-border-color : black");

        HomePageBoarderPane = new BorderPane();
        HomePageBoarderPane.setLeft(vb2);
        HomePageBoarderPane.setRight(vb1);
        HomePageScene = new Scene(HomePageBoarderPane,HomePageStage.getWidth(),HomePageStage.getHeight());
        HomePageStage.setScene(HomePageScene);
        HomePageStage.setMaximized(true);
        HomePageStage.show();
    }
    
}
