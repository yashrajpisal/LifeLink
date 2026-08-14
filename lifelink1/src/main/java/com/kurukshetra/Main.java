package com.kurukshetra;

import com.kurukshetra.view.family.EmergencyServices;
// import com.kurukshetra.view.family.HomePage;
// import com.kurukshetra.view.family.Start;
// import com.kurukshetra.view.nurse.NurseDashboardPage;
import com.kurukshetra.view.family.Start;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        System.out.println("Shree Ganeshay Namhaa!!");
        Application.launch(Start.class,args);
    }
}



// package com.kurukshetra;

// import com.kurukshetra.view.family.FamilyHomePage;

// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.layout.BorderPane;
// import javafx.stage.Stage;

// public class Main extends Application {

//     public static Stage mainStage;

//     @Override
//     public void start(Stage stage) {
//         mainStage = stage;

//         // Start on the Dashboard / Home Page
//         FamilyHomePage homePage = new FamilyHomePage();
//         BorderPane bp = homePage.setBorderPane(stage);

//         Scene sc = new Scene(bp, mainStage.getHeight(),mainStage.getWidth());
//         sc.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());

//         stage.setTitle("LifeLink");
//         stage.setScene(sc);
//         stage.setMaximized(true);
//         stage.show();
//     }

//     public static void main(String[] args) {
//         launch(args);
//     }
// }