package com.kurukshetra.view.loginSignup;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AalLoginStartPoint extends Application{

    public static Stage startPageStage;
    private Scene startPageScene;
    public BorderPane startBorderPane;

    @Override
    public void start(Stage newStage) throws Exception {
        startPageStage = newStage;
        
        HospitalLoginPage hospitalLoginPage = new HospitalLoginPage();
        startBorderPane = hospitalLoginPage.getHospitalLoginPage();


        startPageScene = new Scene(startBorderPane);
        startPageStage.setScene(startPageScene);
        startPageStage.setMaximized(true);
        startPageStage.show();
    }
    

    // public void getLoginPages(String loginRole){
    //     if(loginRole.equals("System Admin")){

    //     }
    // }

}
