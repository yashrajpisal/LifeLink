package com.kurukshetra.view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Welcome extends Application{

    public Stage welcomStage;
    @Override
    public void start(Stage argWelcom) throws Exception {
        welcomStage = argWelcom;

        new otp().show(welcomStage);
    }
    
}
