package com.kurukshetra.view.police;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PoliceDashboard extends Application {

    public static Stage dashboardStage;
    private Scene dashboardScene;

    @Override
    public void start(Stage stage) throws Exception {

        dashboardStage = stage;

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #f8f8ff;");

        // LEFT MENU
        VBox leftMenu = new VBox(15);
        leftMenu.setPadding(new Insets(25, 15, 20, 15));
        leftMenu.setPrefWidth(230);
        leftMenu.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d8dce5; -fx-border-width: 0px 1px 0px 0px;");

        Text profileName = new Text("Control Room");
        profileName.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text profileText = new Text("Police Dashboard");
        profileText.setStyle("-fx-font-size: 13px; -fx-fill: #6b7280;");

        VBox profileBox = new VBox(5);
        profileBox.getChildren().addAll(profileName, profileText);

        // DASHBOARD BUTTON
        Button dashboardButton = new Button("Dashboard");
        dashboardButton.setPrefWidth(195);
        dashboardButton.setPrefHeight(45);
     //   dashboardButton.setAlignment(Pos.CENTER_LEFT);
        dashboardButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

        // HISTORY BUTTON
        Button historyButton = new Button("History");
        historyButton.setPrefWidth(195);
        historyButton.setPrefHeight(45);
    //    historyButton.setAlignment(Pos.CENTER_LEFT);
        historyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");


        // PROFILE BUTTON
        Button profileButton = new Button("Profile");
        profileButton.setPrefWidth(195);
        profileButton.setPrefHeight(45);
        //profileButton.setAlignment(Pos.CENTER_LEFT);
        profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

         
        // SETTINGS BUTTON
        Button settingsButton = new Button("Settings");
        settingsButton.setPrefWidth(195);
        settingsButton.setPrefHeight(45);
        //settingsButton.setAlignment(Pos.CENTER_LEFT);
        settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px;");

        // SPACER
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // LOGOUT BUTTON
        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(195);
        logoutButton.setPrefHeight(45);
        // logoutButton.setAlignment(Pos.BOTTOM_CENTER);
        logoutButton.setStyle("-fx-background-color: #fff1f2; -fx-text-fill: #dc2626; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");


        
        historyButton.setOnAction(event -> {

                dashboardButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

                historyButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

                History history = new History();
                borderPane.setCenter(history.getHistoryVBox());
        });

        profileButton.setOnAction(event ->{

                dashboardButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                historyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

                profileButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

                Profile profile = new Profile();
                borderPane.setCenter(profile.getProfileVBox());
        });

        settingsButton.setOnAction(event -> {

                dashboardButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                historyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

                settingsButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

                Settings setting = new Settings();
                borderPane.setCenter(setting.getSettingsVBox());
        });




        leftMenu.getChildren().addAll(
                profileBox,
                dashboardButton,
                historyButton,
                profileButton,
                settingsButton,
                 spacer,
                logoutButton
        );

        borderPane.setLeft(leftMenu);

        // MAIN DASHBOARD
        VBox dashboard = new VBox(20);
        dashboard.setPadding(new Insets(25));

        // TOP HEADER
        Text heading = new Text("Control Room");
        heading.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text subHeading = new Text("Monitor ambulances, traffic clearance and emergency arrivals");
        subHeading.setStyle("-fx-font-size: 14px; -fx-fill: #6b7280;");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(heading, subHeading);

        // MAIN CONTENT
        HBox mainContent = new HBox(20);
        mainContent.setPrefHeight(650);

        // CENTER PART
        VBox centerPart = new VBox(15);
        centerPart.setPrefWidth(780);

        Text incomingText = new Text("Incoming Ambulances");
        incomingText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text liveText = new Text("● LIVE");
        liveText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #16a34a;");

        HBox incomingHeader = new HBox(15);
        incomingHeader.setAlignment(Pos.CENTER_LEFT);
        incomingHeader.getChildren().addAll(incomingText, liveText);

        // MAP AREA
        StackPane mapPane = new StackPane();
        mapPane.setPrefHeight(390);
        mapPane.setPrefWidth(780);
        mapPane.setStyle("-fx-background-color: #e9eef2; -fx-background-radius: 15px; -fx-border-color: #d1d5db; -fx-border-radius: 15px;");

        Text mapText = new Text("AMBULANCE MAP");
        mapText.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: #6b7280;");

        Circle ambulanceLocation = new Circle(9);
        ambulanceLocation.setFill(Color.web("#ef4444"));

        Circle hospitalLocation = new Circle(9);
        hospitalLocation.setFill(Color.web("#2563eb"));

        Text ambulanceText = new Text(" Ambulance");
        ambulanceText.setStyle("-fx-font-size: 13px; -fx-fill: #374151;");

        Text hospitalText = new Text(" Hospital");
        hospitalText.setStyle("-fx-font-size: 13px; -fx-fill: #374151;");

        HBox mapLocations = new HBox(15);
        mapLocations.setAlignment(Pos.CENTER);
        mapLocations.getChildren().addAll(
                ambulanceLocation,
                ambulanceText,
                hospitalLocation,
                hospitalText
        );

        VBox mapContent = new VBox(15);
        mapContent.setAlignment(Pos.CENTER);
        mapContent.getChildren().addAll(mapText, mapLocations);

        mapPane.getChildren().add(mapContent);

        // AMBULANCE INFORMATION
        VBox ambulanceInfo = new VBox(15);

        VBox ambulance1 = new VBox(5);
        ambulance1.setPadding(new Insets(15));
        ambulance1.setPrefWidth(240);
        ambulance1.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12px; -fx-border-color: #e5e7eb; -fx-border-radius: 12px;");

        Text ambulance1Name = new Text("UNIT A-102");
        ambulance1Name.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text ambulance1Status = new Text("Responding");
        ambulance1Status.setStyle("-fx-font-size: 13px; -fx-fill: #16a34a;");

        Text ambulance1ETA = new Text("ETA: 2 mins");
        ambulance1ETA.setStyle("-fx-font-size: 12px; -fx-fill: #6b7280;");

        ambulance1.getChildren().addAll(
                ambulance1Name,
                ambulance1Status,
                ambulance1ETA
        );

        VBox ambulance2 = new VBox(5);
        ambulance2.setPadding(new Insets(15));
        ambulance2.setPrefWidth(240);
        ambulance2.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12px; -fx-border-color: #e5e7eb; -fx-border-radius: 12px;");

        Text ambulance2Name = new Text("UNIT C-088");
        ambulance2Name.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text ambulance2Status = new Text("In Transit");
        ambulance2Status.setStyle("-fx-font-size: 13px; -fx-fill: #2563eb;");

        Text ambulance2ETA = new Text("ETA: 5 mins");
        ambulance2ETA.setStyle("-fx-font-size: 12px; -fx-fill: #6b7280;");

        ambulance2.getChildren().addAll(
                ambulance2Name,
                ambulance2Status,
                ambulance2ETA
        );

        HBox.setHgrow(ambulance1, Priority.ALWAYS);
        HBox.setHgrow(ambulance2, Priority.ALWAYS);

        ambulanceInfo.getChildren().addAll(
                ambulance1,
                ambulance2
        );

        centerPart.getChildren().addAll(
                incomingHeader,
                mapPane,
                ambulanceInfo
        );

        // RIGHT PART
        VBox rightPart = new VBox(20);
        rightPart.setPrefWidth(390);

        // CLEARANCE ALERTS CARD
        VBox clearanceCard = new VBox(15);
        clearanceCard.setPadding(new Insets(20));
        clearanceCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e5e7eb; -fx-border-radius: 15px;");

        Text clearanceTitle = new Text("Clearance Alerts");
        clearanceTitle.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text pendingText = new Text("2 Pending");
        pendingText.setStyle("-fx-font-size: 12px; -fx-fill: #dc2626; -fx-font-weight: bold;");

        HBox clearanceHeader = new HBox();
        clearanceHeader.setAlignment(Pos.CENTER_LEFT);

        Region clearanceSpacer = new Region();
        HBox.setHgrow(clearanceSpacer, Priority.ALWAYS);

        clearanceHeader.getChildren().addAll(
                clearanceTitle,
                clearanceSpacer,
                pendingText
        );

        // ALERT 1
        VBox alert1 = new VBox(8);
        alert1.setPadding(new Insets(15));
        alert1.setStyle("-fx-background-color: #fff7ed; -fx-background-radius: 12px; -fx-border-color: #fed7aa; -fx-border-radius: 12px;");

        Text alert1Unit = new Text("UNIT A-102");
        alert1Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text alert1Message = new Text("Requesting Arrival Clearance");
        alert1Message.setStyle("-fx-font-size: 13px; -fx-fill: #4b5563;");

        Text alert1ETA = new Text("ETA: 2 mins");
        alert1ETA.setStyle("-fx-font-size: 12px; -fx-fill: #6b7280;");

        Button confirm1 = new Button("Confirm");
        confirm1.setPrefWidth(100);
        confirm1.setStyle("-fx-background-color: #16a34a; -fx-text-fill: white; -fx-font-size: 12px; -fx-background-radius: 8px;");

        Button deny1 = new Button("Deny");
        deny1.setPrefWidth(100);
        deny1.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-font-size: 12px; -fx-background-radius: 8px;");

        HBox alert1Buttons = new HBox(10);
        alert1Buttons.getChildren().addAll(confirm1, deny1);

        alert1.getChildren().addAll(
                alert1Unit,
                alert1Message,
                alert1ETA,
                alert1Buttons
        );

        // ALERT 2
        VBox alert2 = new VBox(8);
        alert2.setPadding(new Insets(15));
        alert2.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 12px; -fx-border-color: #e2e8f0; -fx-border-radius: 12px;");

        Text alert2Unit = new Text("UNIT C-088");
        alert2Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text alert2Message = new Text("Signal Override Request");
        alert2Message.setStyle("-fx-font-size: 13px; -fx-fill: #4b5563;");

        Button confirm2 = new Button("Confirm");
        confirm2.setPrefWidth(100);
        confirm2.setStyle("-fx-background-color: #16a34a; -fx-text-fill: white; -fx-font-size: 12px; -fx-background-radius: 8px;");

        Button deny2 = new Button("Deny");
        deny2.setPrefWidth(100);
        deny2.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-font-size: 12px; -fx-background-radius: 8px;");

        HBox alert2Buttons = new HBox(10);
        alert2Buttons.getChildren().addAll(confirm2, deny2);

        alert2.getChildren().addAll(
                alert2Unit,
                alert2Message,
                alert2Buttons
        );

        clearanceCard.getChildren().addAll(
                clearanceHeader,
                alert1,
                alert2
        );

        // STATUS & ARRIVALS CARD
        VBox statusCard = new VBox(15);
        statusCard.setPadding(new Insets(20));
        statusCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e5e7eb; -fx-border-radius: 15px;");

        Text statusTitle = new Text("Status & Arrivals");
        statusTitle.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: #111827;");

        HBox status1 = new HBox();
        status1.setPadding(new Insets(10));

        Text status1Unit = new Text("UNIT A-42");
        status1Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #374151;");

        Text status1Value = new Text("SUCCESS");
        status1Value.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #16a34a;");

        Region statusSpacer1 = new Region();
        HBox.setHgrow(statusSpacer1, Priority.ALWAYS);

        status1.getChildren().addAll(
                status1Unit,
                statusSpacer1,
                status1Value
        );

        HBox status2 = new HBox();
        status2.setPadding(new Insets(10));

        Text status2Unit = new Text("UNIT C-12");
        status2Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #374151;");

        Text status2Value = new Text("LIVE");
        status2Value.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #2563eb;");

        Region statusSpacer2 = new Region();
        HBox.setHgrow(statusSpacer2, Priority.ALWAYS);

        status2.getChildren().addAll(
                status2Unit,
                statusSpacer2,
                status2Value
        );

        HBox status3 = new HBox();
        status3.setPadding(new Insets(10));

        Text status3Unit = new Text("UNIT B-205");
        status3Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #374151;");

        Text status3Value = new Text("ARRIVED");
        status3Value.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #7c3aed;");

        Region statusSpacer3 = new Region();
        HBox.setHgrow(statusSpacer3, Priority.ALWAYS);

        status3.getChildren().addAll(
                status3Unit,
                statusSpacer3,
                status3Value
        );

        statusCard.getChildren().addAll(
                statusTitle,
                status1,
                status2,
                status3
        );

        rightPart.getChildren().addAll(
                clearanceCard,
                statusCard
        );

        // ADD CENTER + RIGHT
        HBox.setHgrow(centerPart, Priority.ALWAYS);

        mainContent.getChildren().addAll(
                centerPart,
                rightPart
        );

        dashboard.getChildren().addAll(
                headingBox,
                mainContent
        );

        // CENTER SCROLL PANE
        ScrollPane scrollPane = new ScrollPane(dashboard);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        borderPane.setCenter(scrollPane);

        dashboardButton.setOnAction(event -> {

                historyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

                dashboardButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

                borderPane.setCenter(scrollPane);
});

         logoutButton.setOnAction(event ->{


                SignIn signInPage = new SignIn();
                dashboardStage.setScene(signInPage.getSignInScene()); 
        });


        // SCENE
        dashboardScene = new Scene(borderPane, dashboardStage.getWidth(), dashboardStage.getHeight());

        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Police Control Room");
        dashboardStage.setMaximized(true);
        dashboardStage.show();
    }

//     public void goToDashboard() {
//         dashboardStage.setScene(dashboardScene);
//     }
}