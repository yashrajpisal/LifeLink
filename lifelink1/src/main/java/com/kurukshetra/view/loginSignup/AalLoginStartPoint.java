package com.kurukshetra.view.loginSignup;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AalLoginStartPoint extends Application {

    public static Stage startPageStage;
    private Scene startPageScene;
    public BorderPane startBorderPane;

    @Override
    public void start(Stage newStage) throws Exception {
        String loginRole = " ";

        startPageStage = newStage;

        startBorderPane = getLoginPages(loginRole);
        startPageScene = new Scene(startBorderPane, startPageStage.getWidth(), startPageStage.getHeight());
        startPageStage.setScene(startPageScene);
        startPageStage.setMaximized(true);
        startPageStage.show();
    }

    public BorderPane getLoginPages(String loginRole) {
        if (loginRole.equals("Hospital")) {
            HospitalLoginPage hospitalLoginPage = new HospitalLoginPage();
            startBorderPane = hospitalLoginPage.getHospitalLoginPage();
        }
         else if (loginRole.equals("System Admin")) {
            AdminLoginPage adminLoginPage = new AdminLoginPage();
            startBorderPane = adminLoginPage.getAdminLoginPage();
        }
         else if (loginRole.equals("Driver")) {
            DriverLoginPage driverLoginPage = new DriverLoginPage();
            startBorderPane = driverLoginPage.getDriverLoginPage();
        }
         else if (loginRole.equals("Nurse")) {
            NurseLoginPage nurseLoginPage = new NurseLoginPage();
            startBorderPane = nurseLoginPage.getNurseLoginPage();
        }
         else if (loginRole.equals("Police")) {
            PoliceLoginPage policeLoginPage = new PoliceLoginPage();
            startBorderPane = policeLoginPage.getPoliceLoginPage();
        }
         else if (loginRole.equals("Family")) {
            FamilyLoginPage familyLoginPage = new FamilyLoginPage();
            startBorderPane = familyLoginPage.getFamilyLoginPage();
        }

        return startBorderPane;
    }

}
