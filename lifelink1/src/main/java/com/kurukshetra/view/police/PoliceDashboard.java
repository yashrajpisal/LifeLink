// package com.kurukshetra.view.police;

// import javafx.application.Application;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Circle;
// import javafx.scene.text.Text;
// import javafx.stage.Stage;

// public class PoliceDashboard extends Application {

//     public static Stage dashboardStage;
//     private Scene dashboardScene;

//     @Override
//     public void start(Stage stage) throws Exception {

//         dashboardStage = stage;

//         BorderPane borderPane = new BorderPane();
//         borderPane.setStyle("-fx-background-color: #f8f8ff;");

//         // LEFT MENU
//         VBox leftMenu = new VBox(15);
//         leftMenu.setPadding(new Insets(25, 15, 20, 15));
//         leftMenu.setPrefWidth(230);
//         leftMenu.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d8dce5; -fx-border-width: 0px 1px 0px 0px;");

//         Text profileName = new Text("Control Room");
//         profileName.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: #111827;");

//         Text profileText = new Text("Police Dashboard");
//         profileText.setStyle("-fx-font-size: 13px; -fx-fill: #6b7280;");

//         VBox profileBox = new VBox(5);
//         profileBox.getChildren().addAll(profileName, profileText);

//         // DASHBOARD BUTTON
//         Button dashboardButton = new Button("Dashboard");
//         dashboardButton.setPrefWidth(195);
//         dashboardButton.setPrefHeight(45);
//         dashboardButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

//         // HISTORY BUTTON
//         Button historyButton = new Button("History");
//         historyButton.setPrefWidth(195);
//         historyButton.setPrefHeight(45);
//         historyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

//         // PROFILE BUTTON
//         Button profileButton = new Button("Profile");
//         profileButton.setPrefWidth(195);
//         profileButton.setPrefHeight(45);
//         profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
         
//         // SETTINGS BUTTON
//         Button settingsButton = new Button("Settings");
//         settingsButton.setPrefWidth(195);
//         settingsButton.setPrefHeight(45);
//         settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px;");

//         // SPACER (This pushes the logout button to the bottom of the screen)
//         Region spacer = new Region();
//         VBox.setVgrow(spacer, Priority.ALWAYS);

//         // LOGOUT BUTTON
//         Button logoutButton = new Button("Logout");
//         logoutButton.setPrefWidth(195);
//         logoutButton.setPrefHeight(45);
//         logoutButton.setStyle("-fx-background-color: #fff1f2; -fx-text-fill: #dc2626; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

//         historyButton.setOnAction(event -> {
//                 dashboardButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
//                 profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
//                 settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

//                 historyButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

//                 PoliceHistory history = new PoliceHistory();
//                 borderPane.setCenter(history.getHistoryView());
//         });

//         profileButton.setOnAction(event ->{
//                 dashboardButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
//                 historyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
//                 settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

//                 profileButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

//                 PoliceProfile profile = new PoliceProfile();
//                 borderPane.setCenter(profile.getProfileView());
//         });

//         settingsButton.setOnAction(event -> {
//                 dashboardButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
//                 historyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
//                 profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

//                 settingsButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

//                 PoliceSettings setting = new PoliceSettings();
//                 borderPane.setCenter(setting.getSettingsView());
//         });

//         leftMenu.getChildren().addAll(
//                 profileBox,
//                 dashboardButton,
//                 historyButton,
//                 profileButton,
//                 settingsButton,
//                 spacer,
//                 logoutButton
//         );

//         borderPane.setLeft(leftMenu);

//         // MAIN DASHBOARD
//         VBox dashboard = new VBox(20);
//         dashboard.setPadding(new Insets(25));

//         // TOP HEADER
//         Text heading = new Text("Control Room");
//         heading.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #111827;");

//         Text subHeading = new Text("Monitor ambulances, traffic clearance and emergency arrivals");
//         subHeading.setStyle("-fx-font-size: 14px; -fx-fill: #6b7280;");

//         VBox headingBox = new VBox(5);
//         headingBox.getChildren().addAll(heading, subHeading);

//         // MAIN CONTENT
//         HBox mainContent = new HBox(20);
//         mainContent.setPrefHeight(650);

//         // CENTER PART
//         VBox centerPart = new VBox(15);
//         centerPart.setPrefWidth(780);

//         Text incomingText = new Text("Incoming Ambulances");
//         incomingText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: #111827;");

//         Text liveText = new Text("● LIVE");
//         liveText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #16a34a;");

//         HBox incomingHeader = new HBox(15);
//         incomingHeader.setAlignment(Pos.CENTER_LEFT);
//         incomingHeader.getChildren().addAll(incomingText, liveText);

//         // MAP AREA
//         StackPane mapPane = new StackPane();
//         mapPane.setPrefHeight(390);
//         mapPane.setPrefWidth(780);
//         mapPane.setStyle("-fx-background-color: #e9eef2; -fx-background-radius: 15px; -fx-border-color: #d1d5db; -fx-border-radius: 15px;");

//         Text mapText = new Text("AMBULANCE MAP");
//         mapText.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: #6b7280;");

//         Circle ambulanceLocation = new Circle(9);
//         ambulanceLocation.setFill(Color.web("#ef4444"));

//         Circle hospitalLocation = new Circle(9);
//         hospitalLocation.setFill(Color.web("#2563eb"));

//         Text ambulanceText = new Text(" Ambulance");
//         ambulanceText.setStyle("-fx-font-size: 13px; -fx-fill: #374151;");

//         Text hospitalText = new Text(" Hospital");
//         hospitalText.setStyle("-fx-font-size: 13px; -fx-fill: #374151;");

//         HBox mapLocations = new HBox(15);
//         mapLocations.setAlignment(Pos.CENTER);
//         mapLocations.getChildren().addAll(
//                 ambulanceLocation,
//                 ambulanceText,
//                 hospitalLocation,
//                 hospitalText
//         );

//         VBox mapContent = new VBox(15);
//         mapContent.setAlignment(Pos.CENTER);
//         mapContent.getChildren().addAll(mapText, mapLocations);

//         mapPane.getChildren().add(mapContent);

//         // AMBULANCE INFORMATION
//         VBox ambulanceInfo = new VBox(15);

//         VBox ambulance1 = new VBox(5);
//         ambulance1.setPadding(new Insets(15));
//         ambulance1.setPrefWidth(240);
//         ambulance1.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12px; -fx-border-color: #e5e7eb; -fx-border-radius: 12px;");

//         Text ambulance1Name = new Text("UNIT A-102");
//         ambulance1Name.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: #111827;");

//         Text ambulance1Status = new Text("Responding");
//         ambulance1Status.setStyle("-fx-font-size: 13px; -fx-fill: #16a34a;");

//         Text ambulance1ETA = new Text("ETA: 2 mins");
//         ambulance1ETA.setStyle("-fx-font-size: 12px; -fx-fill: #6b7280;");

//         ambulance1.getChildren().addAll(
//                 ambulance1Name,
//                 ambulance1Status,
//                 ambulance1ETA
//         );

//         VBox ambulance2 = new VBox(5);
//         ambulance2.setPadding(new Insets(15));
//         ambulance2.setPrefWidth(240);
//         ambulance2.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12px; -fx-border-color: #e5e7eb; -fx-border-radius: 12px;");

//         Text ambulance2Name = new Text("UNIT C-088");
//         ambulance2Name.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: #111827;");

//         Text ambulance2Status = new Text("In Transit");
//         ambulance2Status.setStyle("-fx-font-size: 13px; -fx-fill: #2563eb;");

//         Text ambulance2ETA = new Text("ETA: 5 mins");
//         ambulance2ETA.setStyle("-fx-font-size: 12px; -fx-fill: #6b7280;");

//         ambulance2.getChildren().addAll(
//                 ambulance2Name,
//                 ambulance2Status,
//                 ambulance2ETA
//         );

//         HBox.setHgrow(ambulance1, Priority.ALWAYS);
//         HBox.setHgrow(ambulance2, Priority.ALWAYS);

//         ambulanceInfo.getChildren().addAll(
//                 ambulance1,
//                 ambulance2
//         );

//         centerPart.getChildren().addAll(
//                 incomingHeader,
//                 mapPane,
//                 ambulanceInfo
//         );

//         // RIGHT PART
//         VBox rightPart = new VBox(20);
//         rightPart.setPrefWidth(390);

//         // CLEARANCE ALERTS CARD
//         VBox clearanceCard = new VBox(15);
//         clearanceCard.setPadding(new Insets(20));
//         clearanceCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e5e7eb; -fx-border-radius: 15px;");

//         Text clearanceTitle = new Text("Clearance Alerts");
//         clearanceTitle.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: #111827;");

//         Text pendingText = new Text("2 Pending");
//         pendingText.setStyle("-fx-font-size: 12px; -fx-fill: #dc2626; -fx-font-weight: bold;");

//         HBox clearanceHeader = new HBox();
//         clearanceHeader.setAlignment(Pos.CENTER_LEFT);

//         Region clearanceSpacer = new Region();
//         HBox.setHgrow(clearanceSpacer, Priority.ALWAYS);

//         clearanceHeader.getChildren().addAll(
//                 clearanceTitle,
//                 clearanceSpacer,
//                 pendingText
//         );

//         // ALERT 1
//         VBox alert1 = new VBox(8);
//         alert1.setPadding(new Insets(15));
//         alert1.setStyle("-fx-background-color: #fff7ed; -fx-background-radius: 12px; -fx-border-color: #fed7aa; -fx-border-radius: 12px;");

//         Text alert1Unit = new Text("UNIT A-102");
//         alert1Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #111827;");

//         Text alert1Message = new Text("Requesting Arrival Clearance");
//         alert1Message.setStyle("-fx-font-size: 13px; -fx-fill: #4b5563;");

//         Text alert1ETA = new Text("ETA: 2 mins");
//         alert1ETA.setStyle("-fx-font-size: 12px; -fx-fill: #6b7280;");

//         Button confirm1 = new Button("Confirm");
//         confirm1.setPrefWidth(100);
//         confirm1.setStyle("-fx-background-color: #16a34a; -fx-text-fill: white; -fx-font-size: 12px; -fx-background-radius: 8px;");

//         Button deny1 = new Button("Deny");
//         deny1.setPrefWidth(100);
//         deny1.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-font-size: 12px; -fx-background-radius: 8px;");

//         HBox alert1Buttons = new HBox(10);
//         alert1Buttons.getChildren().addAll(confirm1, deny1);

//         alert1.getChildren().addAll(
//                 alert1Unit,
//                 alert1Message,
//                 alert1ETA,
//                 alert1Buttons
//         );

//         // ALERT 2
//         VBox alert2 = new VBox(8);
//         alert2.setPadding(new Insets(15));
//         alert2.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 12px; -fx-border-color: #e2e8f0; -fx-border-radius: 12px;");

//         Text alert2Unit = new Text("UNIT C-088");
//         alert2Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #111827;");

//         Text alert2Message = new Text("Signal Override Request");
//         alert2Message.setStyle("-fx-font-size: 13px; -fx-fill: #4b5563;");

//         Button confirm2 = new Button("Confirm");
//         confirm2.setPrefWidth(100);
//         confirm2.setStyle("-fx-background-color: #16a34a; -fx-text-fill: white; -fx-font-size: 12px; -fx-background-radius: 8px;");

//         Button deny2 = new Button("Deny");
//         deny2.setPrefWidth(100);
//         deny2.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-font-size: 12px; -fx-background-radius: 8px;");

//         HBox alert2Buttons = new HBox(10);
//         alert2Buttons.getChildren().addAll(confirm2, deny2);

//         alert2.getChildren().addAll(
//                 alert2Unit,
//                 alert2Message,
//                 alert2Buttons
//         );

//         clearanceCard.getChildren().addAll(
//                 clearanceHeader,
//                 alert1,
//                 alert2
//         );

//         // STATUS & ARRIVALS CARD
//         VBox statusCard = new VBox(15);
//         statusCard.setPadding(new Insets(20));
//         statusCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e5e7eb; -fx-border-radius: 15px;");

//         Text statusTitle = new Text("Status & Arrivals");
//         statusTitle.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: #111827;");

//         HBox status1 = new HBox();
//         status1.setPadding(new Insets(10));

//         Text status1Unit = new Text("UNIT A-42");
//         status1Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #374151;");

//         Text status1Value = new Text("SUCCESS");
//         status1Value.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #16a34a;");

//         Region statusSpacer1 = new Region();
//         HBox.setHgrow(statusSpacer1, Priority.ALWAYS);

//         status1.getChildren().addAll(
//                 status1Unit,
//                 statusSpacer1,
//                 status1Value
//         );

//         HBox status2 = new HBox();
//         status2.setPadding(new Insets(10));

//         Text status2Unit = new Text("UNIT C-12");
//         status2Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #374151;");

//         Text status2Value = new Text("LIVE");
//         status2Value.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #2563eb;");

//         Region statusSpacer2 = new Region();
//         HBox.setHgrow(statusSpacer2, Priority.ALWAYS);

//         status2.getChildren().addAll(
//                 status2Unit,
//                 statusSpacer2,
//                 status2Value
//         );

//         HBox status3 = new HBox();
//         status3.setPadding(new Insets(10));

//         Text status3Unit = new Text("UNIT B-205");
//         status3Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #374151;");

//         Text status3Value = new Text("ARRIVED");
//         status3Value.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #7c3aed;");

//         Region statusSpacer3 = new Region();
//         HBox.setHgrow(statusSpacer3, Priority.ALWAYS);

//         status3.getChildren().addAll(
//                 status3Unit,
//                 statusSpacer3,
//                 status3Value
//         );

//         statusCard.getChildren().addAll(
//                 statusTitle,
//                 status1,
//                 status2,
//                 status3
//         );

//         rightPart.getChildren().addAll(
//                 clearanceCard,
//                 statusCard
//         );

//         // ADD CENTER + RIGHT
//         HBox.setHgrow(centerPart, Priority.ALWAYS);

//         mainContent.getChildren().addAll(
//                 centerPart,
//                 rightPart
//         );

//         dashboard.getChildren().addAll(
//                 headingBox,
//                 mainContent
//         );

//         // CENTER SCROLL PANE
//         ScrollPane scrollPane = new ScrollPane(dashboard);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//         scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

//         borderPane.setCenter(scrollPane);

//         dashboardButton.setOnAction(event -> {

//                 historyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
//                 profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
//                 settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

//                 dashboardButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

//                 borderPane.setCenter(scrollPane);
//         });

//          logoutButton.setOnAction(event ->{
//                 System.out.println("Logout button clicked!!");
//         });

//         // SCENE
//         dashboardScene = new Scene(borderPane, dashboardStage.getWidth(), dashboardStage.getHeight());

//         dashboardStage.setScene(dashboardScene);
//         dashboardStage.setTitle("Police Control Room");
//         dashboardStage.setMaximized(true);
//         dashboardStage.show();
//     }
// }

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

    // --- FAINT DARK-BROWN / WARM BRONZE COLOR PALETTE ---
    private static final String PAGE_BG = "#F7F3EF";
    private static final String SURFACE = "#FFFFFF";
    private static final String SOFT_CREAM = "#FCF9F6";
    private static final String VERY_LIGHT_BEIGE = "#F8F4F0";
    private static final String LIGHT_BEIGE = "#F2ECE6";
    private static final String PALE_BROWN = "#E9DED4";
    
    private static final String PRIMARY_DARK_BROWN = "#2A211D";
    private static final String BROWN_DARK = "#694d3b";
    private static final String WARM_BRONZE = "#8d6338";
    
    private static final String PRIMARY_TEXT = "#29231F";
    private static final String SEC_TEXT = "#635951";
    private static final String MUTED_TEXT = "#91857C";
    
    private static final String BORDER = "#E5DBD2";
    private static final String ACTIVE_BG = "#EDE2D8";
    
    // Semantic Status Colors
    private static final String SUCCESS_BG = "#EAF4ED";
    private static final String SUCCESS_TEXT = "#34704A";
    private static final String RESPONDING_BG = "#F6EBDD";
    private static final String RESPONDING_TEXT = "#8A5A24";
    private static final String COMPLETED_TEXT = "#59665C";
    private static final String DANGER_BG = "#FCE8E7";
    private static final String DANGER_TEXT = "#B84C47";

    // Reusable Styles
    private static final String CARD_STYLE = "-fx-background-color: " + SURFACE + "; -fx-background-radius: 15px; -fx-border-color: " + BORDER + "; -fx-border-radius: 15px; -fx-effect: dropshadow(gaussian, rgba(42, 33, 29, 0.08), 16, 0.12, 0, 5);";
    private static final String SMALL_CARD_STYLE = "-fx-background-color: " + SURFACE + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(42, 33, 29, 0.06), 10, 0.1, 0, 3);";
    private static final String BTN_ACTIVE = "-fx-background-color: " + ACTIVE_BG + "; -fx-text-fill: " + BROWN_DARK + "; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand;";
    private static final String BTN_INACTIVE = "-fx-background-color: transparent; -fx-text-fill: " + SEC_TEXT + "; -fx-font-size: 15px; -fx-background-radius: 10px; -fx-cursor: hand;";
    private static final String BTN_PRIMARY = "-fx-background-color: " + WARM_BRONZE + "; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-background-radius: 8px; -fx-cursor: hand;";
    private static final String BTN_DANGER = "-fx-background-color: " + DANGER_BG + "; -fx-text-fill: " + DANGER_TEXT + "; -fx-font-size: 12px; -fx-background-radius: 8px; -fx-cursor: hand;";

    @Override
    public void start(Stage stage) throws Exception {

        dashboardStage = stage;

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: " + PAGE_BG + ";");

        // LEFT MENU
        VBox leftMenu = new VBox(15);
        leftMenu.setPadding(new Insets(25, 15, 20, 15));
        leftMenu.setPrefWidth(230);
        leftMenu.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER + "; -fx-border-width: 0px 1px 0px 0px;");

        Text profileName = new Text("Control Room");
        profileName.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text profileText = new Text("Police Dashboard");
        profileText.setStyle("-fx-font-size: 13px; -fx-fill: " + SEC_TEXT + ";");

        VBox profileBox = new VBox(5);
        profileBox.getChildren().addAll(profileName, profileText);

        // DASHBOARD BUTTON
        Button dashboardButton = new Button("Dashboard");
        dashboardButton.setPrefWidth(195);
        dashboardButton.setPrefHeight(45);
        dashboardButton.setStyle(BTN_ACTIVE);

        // HISTORY BUTTON
        Button historyButton = new Button("History");
        historyButton.setPrefWidth(195);
        historyButton.setPrefHeight(45);
        historyButton.setStyle(BTN_INACTIVE);

        // PROFILE BUTTON
        Button profileButton = new Button("Profile");
        profileButton.setPrefWidth(195);
        profileButton.setPrefHeight(45);
        profileButton.setStyle(BTN_INACTIVE);
         
        // SETTINGS BUTTON
        Button settingsButton = new Button("Settings");
        settingsButton.setPrefWidth(195);
        settingsButton.setPrefHeight(45);
        settingsButton.setStyle(BTN_INACTIVE);

        // SPACER (This pushes the logout button to the bottom of the screen)
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // LOGOUT BUTTON
        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(195);
        logoutButton.setPrefHeight(45);
        logoutButton.setStyle("-fx-background-color: " + DANGER_BG + "; -fx-text-fill: " + DANGER_TEXT + "; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand;");

        historyButton.setOnAction(event -> {
                dashboardButton.setStyle(BTN_INACTIVE);
                profileButton.setStyle(BTN_INACTIVE);
                settingsButton.setStyle(BTN_INACTIVE);
                historyButton.setStyle(BTN_ACTIVE);

                PoliceHistory history = new PoliceHistory();
                borderPane.setCenter(history.getHistoryView());
        });

        profileButton.setOnAction(event ->{
                dashboardButton.setStyle(BTN_INACTIVE);
                historyButton.setStyle(BTN_INACTIVE);
                settingsButton.setStyle(BTN_INACTIVE);
                profileButton.setStyle(BTN_ACTIVE);

                PoliceProfile profile = new PoliceProfile();
                borderPane.setCenter(profile.getProfileView());
        });

        settingsButton.setOnAction(event -> {
                dashboardButton.setStyle(BTN_INACTIVE);
                historyButton.setStyle(BTN_INACTIVE);
                profileButton.setStyle(BTN_INACTIVE);
                settingsButton.setStyle(BTN_ACTIVE);

                PoliceSettings setting = new PoliceSettings();
                borderPane.setCenter(setting.getSettingsView());
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
        heading.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text subHeading = new Text("Monitor ambulances, traffic clearance and emergency arrivals");
        subHeading.setStyle("-fx-font-size: 14px; -fx-fill: " + SEC_TEXT + ";");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(heading, subHeading);

        // MAIN CONTENT
        HBox mainContent = new HBox(20);
        mainContent.setPrefHeight(650);

        // CENTER PART
        VBox centerPart = new VBox(15);
        centerPart.setPrefWidth(780);

        Text incomingText = new Text("Incoming Ambulances");
        incomingText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text liveText = new Text("● LIVE");
        liveText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + SUCCESS_TEXT + ";");

        HBox incomingHeader = new HBox(15);
        incomingHeader.setAlignment(Pos.CENTER_LEFT);
        incomingHeader.getChildren().addAll(incomingText, liveText);

        // MAP AREA
        StackPane mapPane = new StackPane();
        mapPane.setPrefHeight(390);
        mapPane.setPrefWidth(780);
        mapPane.setStyle("-fx-background-color: " + LIGHT_BEIGE + "; -fx-background-radius: 15px; -fx-border-color: " + BORDER + "; -fx-border-radius: 15px;");

        Text mapText = new Text("AMBULANCE MAP");
        mapText.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: " + MUTED_TEXT + ";");

        Circle ambulanceLocation = new Circle(9);
        ambulanceLocation.setFill(Color.web(DANGER_TEXT));

        Circle hospitalLocation = new Circle(9);
        hospitalLocation.setFill(Color.web(BROWN_DARK));

        Text ambulanceText = new Text(" Ambulance");
        ambulanceText.setStyle("-fx-font-size: 13px; -fx-fill: " + PRIMARY_TEXT + ";");

        Text hospitalText = new Text(" Hospital");
        hospitalText.setStyle("-fx-font-size: 13px; -fx-fill: " + PRIMARY_TEXT + ";");

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
        ambulance1.setStyle(SMALL_CARD_STYLE);

        Text ambulance1Name = new Text("UNIT A-102");
        ambulance1Name.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text ambulance1Status = new Text("Responding");
        ambulance1Status.setStyle("-fx-font-size: 13px; -fx-fill: " + RESPONDING_TEXT + ";");

        Text ambulance1ETA = new Text("ETA: 2 mins");
        ambulance1ETA.setStyle("-fx-font-size: 12px; -fx-fill: " + SEC_TEXT + ";");

        ambulance1.getChildren().addAll(
                ambulance1Name,
                ambulance1Status,
                ambulance1ETA
        );

        VBox ambulance2 = new VBox(5);
        ambulance2.setPadding(new Insets(15));
        ambulance2.setPrefWidth(240);
        ambulance2.setStyle(SMALL_CARD_STYLE);

        Text ambulance2Name = new Text("UNIT C-088");
        ambulance2Name.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text ambulance2Status = new Text("In Transit");
        ambulance2Status.setStyle("-fx-font-size: 13px; -fx-fill: " + BROWN_DARK + ";");

        Text ambulance2ETA = new Text("ETA: 5 mins");
        ambulance2ETA.setStyle("-fx-font-size: 12px; -fx-fill: " + SEC_TEXT + ";");

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
        clearanceCard.setStyle(CARD_STYLE);

        Text clearanceTitle = new Text("Clearance Alerts");
        clearanceTitle.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text pendingText = new Text("2 Pending");
        pendingText.setStyle("-fx-font-size: 12px; -fx-fill: " + DANGER_TEXT + "; -fx-font-weight: bold;");

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
        alert1.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 12px; -fx-border-color: " + PALE_BROWN + "; -fx-border-radius: 12px;");

        Text alert1Unit = new Text("UNIT A-102");
        alert1Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text alert1Message = new Text("Requesting Arrival Clearance");
        alert1Message.setStyle("-fx-font-size: 13px; -fx-fill: " + SEC_TEXT + ";");

        Text alert1ETA = new Text("ETA: 2 mins");
        alert1ETA.setStyle("-fx-font-size: 12px; -fx-fill: " + MUTED_TEXT + ";");

        Button confirm1 = new Button("Confirm");
        confirm1.setPrefWidth(100);
        confirm1.setStyle(BTN_PRIMARY);
        addConfirmHoverEffect(confirm1);

        Button deny1 = new Button("Deny");
        deny1.setPrefWidth(100);
        deny1.setStyle(BTN_DANGER);

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
        alert2.setStyle("-fx-background-color: " + SOFT_CREAM + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");

        Text alert2Unit = new Text("UNIT C-088");
        alert2Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text alert2Message = new Text("Signal Override Request");
        alert2Message.setStyle("-fx-font-size: 13px; -fx-fill: " + SEC_TEXT + ";");

        Button confirm2 = new Button("Confirm");
        confirm2.setPrefWidth(100);
        confirm2.setStyle(BTN_PRIMARY);
        addConfirmHoverEffect(confirm2);

        Button deny2 = new Button("Deny");
        deny2.setPrefWidth(100);
        deny2.setStyle(BTN_DANGER);

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
        statusCard.setStyle(CARD_STYLE);

        Text statusTitle = new Text("Status & Arrivals");
        statusTitle.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        HBox status1 = new HBox();
        status1.setPadding(new Insets(10));

        Text status1Unit = new Text("UNIT A-42");
        status1Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text status1Value = new Text("SUCCESS");
        status1Value.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + SUCCESS_TEXT + ";");

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
        status2Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text status2Value = new Text("LIVE");
        status2Value.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + WARM_BRONZE + ";");

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
        status3Unit.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text status3Value = new Text("ARRIVED");
        status3Value.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + COMPLETED_TEXT + ";");

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
                historyButton.setStyle(BTN_INACTIVE);
                profileButton.setStyle(BTN_INACTIVE);
                settingsButton.setStyle(BTN_INACTIVE);
                dashboardButton.setStyle(BTN_ACTIVE);

                borderPane.setCenter(scrollPane);
        });

         logoutButton.setOnAction(event ->{
                System.out.println("Logout button clicked!!");
        });

        // SCENE
        dashboardScene = new Scene(borderPane, dashboardStage.getWidth(), dashboardStage.getHeight());

        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Police Control Room");
        dashboardStage.setMaximized(true);
        dashboardStage.show();
    }
    
    /**
     * Programmatically applies a dark brown hover effect to the Confirm buttons
     * without relying on external CSS files.
     */
    private void addConfirmHoverEffect(Button button) {
        // Change background to BROWN_DARK on hover
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + BROWN_DARK + "; -fx-text-fill: #FFFFFF; -fx-font-size: 12px; -fx-background-radius: 8px; -fx-cursor: hand;"));
        
        // Revert to original WARM_BRONZE style when mouse exits
        button.setOnMouseExited(e -> button.setStyle(BTN_PRIMARY));
    }
}