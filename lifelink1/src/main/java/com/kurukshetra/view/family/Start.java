package com.kurukshetra.view.family;


// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;
// import javafx.stage.Stage;

// public class Start extends Application{

//     public Stage HomePageStage;
//     public Scene HomePageScene;
//     public BorderPane HomePageBoarderPane;
//     @Override
//     public void start(Stage arg0) throws Exception {
//         HomePageStage = arg0;

//         Text t1 = new Text("Right side");
//         Button goToAiAssistance = new Button("Go to Ai Assistance Page");
//         goToAiAssistance.setOnAction(event -> {
//             System.out.println("Go to last Page");
//             FamilyHomePage home = new FamilyHomePage();
//             Scene sc = new Scene(home.setBorderPane(HomePageStage));
//             sc.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
//             HomePageStage.setScene(sc);
//             HomePageStage.setMaximized(true);
//         });

//         Button goToAiAssistance1 = new Button("Go to lAst Page");
//         goToAiAssistance1.setOnAction(event -> {
//             System.out.println("Go to next Page");
//             FamilyFindCare obj = new FamilyFindCare();
//             // Create Scene without using stage width/height (those are 0 before showing)
//             Scene sc = new Scene(obj.setBorderPane(HomePageStage));
//             sc.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
//             HomePageStage.setScene(sc);
//             HomePageStage.setMaximized(true);
//         });

//         VBox vb2 = new VBox(20,t1,goToAiAssistance , goToAiAssistance1);
//         vb2.setStyle("-fx-border-color : black");

       

//         HomePageBoarderPane = new BorderPane();
//         HomePageBoarderPane.setLeft(vb2);
//         // HomePageBoarderPane.setCenter(home.getHomePageContent());


//         // Create scene without explicit width/height so layout determines preferred size
//         HomePageScene = new Scene(HomePageBoarderPane);
//         HomePageScene.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());
//         HomePageStage.setScene(HomePageScene);
//         // Maximize so the UI fills the full screen
//         HomePageStage.setMaximized(true);
//         HomePageStage.show();
//     }
    
// }



// import com.kurukshetra.view.family.FamilyHomePage;

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

        Scene sc = new Scene(bp, mainStage.getHeight(),mainStage.getWidth());
        sc.getStylesheets().add(getClass().getResource("/css/style1.css").toExternalForm());

        stage.setTitle("LifeLink");
        stage.setScene(sc);
        stage.setMaximized(true);
        stage.show();
    }
}