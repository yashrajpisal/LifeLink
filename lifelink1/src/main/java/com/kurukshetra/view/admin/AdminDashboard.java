package com.kurukshetra.view.admin;

import com.kurukshetra.controller.admin.AdminSideEmgReqController;
import com.kurukshetra.model.admin.AdminSideEmgReqModel;
import com.google.cloud.Timestamp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminDashboard extends Application {

    public static Stage dashboardStage;
    private Scene dashboardScene;
    private BorderPane borderPane;
    private VBox rightContent;

    // Design Tokens - LifeLink Pastel Purple Theme
    private static final String BG_PAGE = "#FAF7FB";
    private static final String BG_SURFACE = "#FFFFFF";
    private static final String BORDER_COLOR = "#E9E2EF";
    private static final String BORDER_DIVIDER = "#F0E7F5";

    private static final String TEXT_PRIMARY = "#0F172A";
    private static final String TEXT_SECONDARY = "#5F5A70";
    private static final String TEXT_MUTED = "#8B8798";

    private static final String PURPLE_PRIMARY = "#9C7DF0";
    private static final String PURPLE_DARK = "#8B68E5";
    private static final String PURPLE_BUTTON = "#C084FC";
    private static final String PURPLE_LIGHT = "#F3E8FF";
    private static final String PURPLE_VARIANT = "#E9D5FF";

    private static final String SUCCESS_TEXT = "#22C55E";
    private static final String SUCCESS_BG = "#DCFCE7";
    private static final String WARNING_TEXT = "#F59E0B";
    private static final String WARNING_BG = "#FEF3C7";
    private static final String DANGER_TEXT = "#E66A7A";
    private static final String DANGER_BG = "#FDE7EB";
    private static final String DANGER_BORDER = "#FCCED5";

    private static final String CARD_SHADOW = "-fx-effect: dropshadow(gaussian, rgba(156, 125, 240, 0.08), 16, 0.1, 0, 4);";
    private static final String FONT_STACK = "-fx-font-family: 'Segoe UI', 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;";

    // Style Helpers
    private static final String NAV_ACTIVE_STYLE = FONT_STACK +
            "-fx-background-color: " + PURPLE_LIGHT + ";" +
            "-fx-text-fill: " + PURPLE_DARK + ";" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 12px;" +
            "-fx-cursor: hand;";

    private static final String NAV_INACTIVE_STYLE = FONT_STACK +
            "-fx-background-color: transparent;" +
            "-fx-text-fill: " + TEXT_SECONDARY + ";" +
            "-fx-font-size: 15px;" +
            "-fx-background-radius: 12px;" +
            "-fx-cursor: hand;";

    private static final String BASE_CARD_STYLE = FONT_STACK +
            "-fx-background-color: " + BG_SURFACE + ";" +
            "-fx-background-radius: 16px;" +
            "-fx-border-color: " + BORDER_COLOR + ";" +
            "-fx-border-radius: 16px;" +
            "-fx-border-width: 1px;" +
            CARD_SHADOW;

    private static final String SECONDARY_BUTTON_STYLE = FONT_STACK +
            "-fx-background-color: " + BG_SURFACE + ";" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: 500;" +
            "-fx-border-color: " + BORDER_COLOR + ";" +
            "-fx-border-radius: 10px;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;";

    private static final String PRIMARY_BUTTON_STYLE = FONT_STACK +
            "-fx-background-color: " + PURPLE_BUTTON + ";" +
            "-fx-text-fill: #FFFFFF;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10px;" +
            "-fx-effect: dropshadow(gaussian, rgba(192, 132, 252, 0.35), 10, 0.2, 0, 3);" +
            "-fx-cursor: hand;";

    // Backend Controller & Cache
    private final AdminSideEmgReqController emgController = new AdminSideEmgReqController();
    private final List<AdminSideEmgReqModel> liveEmergencyList = new ArrayList<>();

    // Form Controls
    private TextField tripIdField;
    private TextField patIdField;
    private TextField sourceField;
    private TextField destinationField;
    private TextField nurseIdField;
    private TextField driverIdField;
    private ComboBox<String> statusBox;
    private TextField timestampField;
    private Button submitDispatchBtn;
    private Label dispatchStatusLabel;

    // Feed Controls
    private VBox emergencyFeedCardsContainer;
    private TextField feedSearchField;
    private Text totalFeedBadge;

    @Override
    public void start(Stage stage) throws Exception {
        dashboardStage = stage;

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: " + BG_PAGE + ";");

        // --- SIDEBAR NAVIGATION ---
        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(25, 16, 20, 16));
        leftMenu.setPrefWidth(250);
        leftMenu.setStyle("-fx-background-color: " + BG_SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-width: 0px 1px 0px 0px;");

        Text lifeLinkText = new Text("LifeLink");
        lifeLinkText.setStyle(FONT_STACK + "-fx-font-size: 25px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");

        Text adminText = new Text("Admin Dashboard");
        adminText.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + TEXT_MUTED + ";");

        VBox profileBox = new VBox(4);
        profileBox.getChildren().addAll(lifeLinkText, adminText);
        profileBox.setPadding(new Insets(0, 10, 18, 10));

        Button dashboardButton = new Button("Dashboard");
        dashboardButton.setPrefWidth(220);
        dashboardButton.setPrefHeight(45);
        dashboardButton.setStyle(NAV_ACTIVE_STYLE);

        Button hospitalButton = new Button("Hospital Management");
        hospitalButton.setPrefWidth(220);
        hospitalButton.setPrefHeight(45);
        hospitalButton.setStyle(NAV_INACTIVE_STYLE);

        Button ambulanceButton = new Button("Ambulance Management");
        ambulanceButton.setPrefWidth(220);
        ambulanceButton.setPrefHeight(45);
        ambulanceButton.setStyle(NAV_INACTIVE_STYLE);

        Button userButton = new Button("User Management");
        userButton.setPrefWidth(220);
        userButton.setPrefHeight(45);
        userButton.setStyle(NAV_INACTIVE_STYLE);

        Button policeButton = new Button("Police Management");
        policeButton.setPrefWidth(220);
        policeButton.setPrefHeight(45);
        policeButton.setStyle(NAV_INACTIVE_STYLE);

        Button emergencyButton = new Button("Emergency Monitoring");
        emergencyButton.setPrefWidth(220);
        emergencyButton.setPrefHeight(45);
        emergencyButton.setStyle(NAV_INACTIVE_STYLE);

        Button analyticsButton = new Button("Analytics & Reports");
        analyticsButton.setPrefWidth(220);
        analyticsButton.setPrefHeight(45);
        analyticsButton.setStyle(NAV_INACTIVE_STYLE);

        Button activityButton = new Button("Activity Logs");
        activityButton.setPrefWidth(220);
        activityButton.setPrefHeight(45);
        activityButton.setStyle(NAV_INACTIVE_STYLE);

        Button settingsButton = new Button("Settings");
        settingsButton.setPrefWidth(220);
        settingsButton.setPrefHeight(45);
        settingsButton.setStyle(NAV_INACTIVE_STYLE);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(220);
        logoutButton.setPrefHeight(45);
        logoutButton.setStyle(FONT_STACK +
                "-fx-background-color: " + DANGER_BG + ";" +
                "-fx-text-fill: " + DANGER_TEXT + ";" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 12px;" +
                "-fx-cursor: hand;");

        leftMenu.getChildren().addAll(
                profileBox,
                dashboardButton,
                hospitalButton,
                ambulanceButton,
                userButton,
                policeButton,
                emergencyButton,
                analyticsButton,
                activityButton,
                settingsButton,
                spacer,
                logoutButton
        );

        borderPane.setLeft(leftMenu);

        // --- RIGHT MAIN CONTAINER ---
        rightContent = new VBox(20);
        rightContent.setPadding(new Insets(25));
        rightContent.setStyle("-fx-background-color: " + BG_PAGE + ";");

        // Header Section
        Text heading = new Text("Admin Dashboard");
        heading.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text subHeading = new Text("System status overview and emergency management");
        subHeading.setStyle(FONT_STACK + "-fx-font-size: 14px; -fx-fill: " + TEXT_SECONDARY + ";");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(heading, subHeading);

        Button exportButton = new Button("Export PDF");
        exportButton.setPrefWidth(120);
        exportButton.setPrefHeight(40);
        exportButton.setStyle(SECONDARY_BUTTON_STYLE);

        Button emergencyNewButton = new Button("+ New Emergency");
        emergencyNewButton.setPrefWidth(150);
        emergencyNewButton.setPrefHeight(40);
        emergencyNewButton.setStyle(PRIMARY_BUTTON_STYLE);

        emergencyNewButton.setOnAction(e -> {
            borderPane.setCenter(buildEmergencyDispatchCard());
        });

        HBox headerButtons = new HBox(10);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);
        headerButtons.getChildren().addAll(exportButton, emergencyNewButton);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox header = new HBox(headingBox, headerSpacer, headerButtons);
        header.setAlignment(Pos.CENTER_LEFT);

        // Cards Row 1
        HBox cardsRow1 = new HBox(16);

        VBox hospitalCard = new VBox(7);
        hospitalCard.setPadding(new Insets(18));
        hospitalCard.setPrefHeight(130);
        hospitalCard.setStyle(BASE_CARD_STYLE);
        Text hospitalTitle = new Text("Total Hospitals");
        hospitalTitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + TEXT_SECONDARY + "; -fx-font-weight: bold;");
        Text hospitalValue = new Text("124");
        hospitalValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text hospitalInfo = new Text("+2 this month");
        hospitalInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + SUCCESS_TEXT + "; -fx-font-weight: bold;");
        hospitalCard.getChildren().addAll(hospitalTitle, hospitalValue, hospitalInfo);
        HBox.setHgrow(hospitalCard, Priority.ALWAYS);

        VBox ambulanceCard = new VBox(7);
        ambulanceCard.setPadding(new Insets(18));
        ambulanceCard.setPrefHeight(130);
        ambulanceCard.setStyle(BASE_CARD_STYLE);
        Text ambulanceTitle = new Text("Active Ambulances");
        ambulanceTitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + TEXT_SECONDARY + "; -fx-font-weight: bold;");
        Text ambulanceValue = new Text("86");
        ambulanceValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text ambulanceInfo = new Text("12 in maintenance");
        ambulanceInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_MUTED + ";");
        ambulanceCard.getChildren().addAll(ambulanceTitle, ambulanceValue, ambulanceInfo);
        HBox.setHgrow(ambulanceCard, Priority.ALWAYS);

        VBox patientCard = new VBox(7);
        patientCard.setPadding(new Insets(18));
        patientCard.setPrefHeight(130);
        patientCard.setStyle(BASE_CARD_STYLE);
        Text patientTitle = new Text("Registered Patients");
        patientTitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + TEXT_SECONDARY + "; -fx-font-weight: bold;");
        Text patientValue = new Text("42.8k");
        patientValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text patientInfo = new Text("Active database");
        patientInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_MUTED + ";");
        patientCard.getChildren().addAll(patientTitle, patientValue, patientInfo);
        HBox.setHgrow(patientCard, Priority.ALWAYS);

        VBox doctorCard = new VBox(7);
        doctorCard.setPadding(new Insets(18));
        doctorCard.setPrefHeight(130);
        doctorCard.setStyle(BASE_CARD_STYLE);
        Text doctorTitle = new Text("Registered Doctors");
        doctorTitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + TEXT_SECONDARY + "; -fx-font-weight: bold;");
        Text doctorValue = new Text("3,412");
        doctorValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text doctorInfo = new Text("98 currently on shift");
        doctorInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_MUTED + ";");
        doctorCard.getChildren().addAll(doctorTitle, doctorValue, doctorInfo);
        HBox.setHgrow(doctorCard, Priority.ALWAYS);

        cardsRow1.getChildren().addAll(hospitalCard, ambulanceCard, patientCard, doctorCard);

        // Cards Row 2
        HBox cardsRow2 = new HBox(16);

        VBox emergencyCard = new VBox(7);
        emergencyCard.setPadding(new Insets(18));
        emergencyCard.setPrefHeight(130);
        emergencyCard.setStyle(FONT_STACK +
                "-fx-background-color: " + DANGER_BG + ";" +
                "-fx-background-radius: 16px;" +
                "-fx-border-color: " + DANGER_BORDER + ";" +
                "-fx-border-radius: 16px;" +
                "-fx-border-width: 1px;" +
                "-fx-effect: dropshadow(gaussian, rgba(230, 106, 122, 0.10), 16, 0.1, 0, 4);");
        Text emergencyTitle = new Text("Active Emergencies");
        emergencyTitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + DANGER_TEXT + "; -fx-font-weight: bold;");
        Text emergencyValue = new Text("14");
        emergencyValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + DANGER_TEXT + ";");
        Text emergencyInfo = new Text("4 Critical Priority");
        emergencyInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + DANGER_TEXT + "; -fx-font-weight: bold;");
        emergencyCard.getChildren().addAll(emergencyTitle, emergencyValue, emergencyInfo);
        HBox.setHgrow(emergencyCard, Priority.ALWAYS);

        VBox icuCard = new VBox(7);
        icuCard.setPadding(new Insets(18));
        icuCard.setPrefHeight(130);
        icuCard.setStyle(BASE_CARD_STYLE);
        Text icuTitle = new Text("Available ICU Beds");
        icuTitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + TEXT_SECONDARY + "; -fx-font-weight: bold;");
        Text icuValue = new Text("42");
        icuValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text icuInfo = new Text("Capacity monitoring");
        icuInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_MUTED + ";");
        icuCard.getChildren().addAll(icuTitle, icuValue, icuInfo);
        HBox.setHgrow(icuCard, Priority.ALWAYS);

        VBox requestCard = new VBox(7);
        requestCard.setPadding(new Insets(18));
        requestCard.setPrefHeight(130);
        requestCard.setStyle(BASE_CARD_STYLE);
        Text requestTitle = new Text("Emergency Requests Today");
        requestTitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + TEXT_SECONDARY + "; -fx-font-weight: bold;");
        Text requestValue = new Text("312");
        requestValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text requestInfo = new Text("+12% vs avg");
        requestInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + SUCCESS_TEXT + "; -fx-font-weight: bold;");
        requestCard.getChildren().addAll(requestTitle, requestValue, requestInfo);
        HBox.setHgrow(requestCard, Priority.ALWAYS);

        VBox responseCard = new VBox(7);
        responseCard.setPadding(new Insets(18));
        responseCard.setPrefHeight(130);
        responseCard.setStyle(BASE_CARD_STYLE);
        Text responseTitle = new Text("Avg Response Time");
        responseTitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + TEXT_SECONDARY + "; -fx-font-weight: bold;");
        Text responseValue = new Text("8.2m");
        responseValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text responseInfo = new Text("Target: <10 mins");
        responseInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + SUCCESS_TEXT + "; -fx-font-weight: bold;");
        responseCard.getChildren().addAll(responseTitle, responseValue, responseInfo);
        HBox.setHgrow(responseCard, Priority.ALWAYS);

        cardsRow2.getChildren().addAll(emergencyCard, icuCard, requestCard, responseCard);

        // Main Content Area
        HBox mainContent = new HBox(20);
        VBox leftContent = new VBox(20);
        HBox.setHgrow(leftContent, Priority.ALWAYS);

        // Chart Card
        VBox chartCard = new VBox(16);
        chartCard.setPadding(new Insets(22));
        chartCard.setStyle(BASE_CARD_STYLE);
        Text chartTitle = new Text("Emergency Cases per Day");
        chartTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        HBox chart = new HBox(16);
        chart.setAlignment(Pos.BOTTOM_CENTER);
        chart.setPrefHeight(220);

        Region bar1 = new Region(); bar1.setPrefWidth(35); bar1.setPrefHeight(80); bar1.setStyle("-fx-background-color: " + PURPLE_VARIANT + "; -fx-background-radius: 8px 8px 0px 0px;");
        Region bar2 = new Region(); bar2.setPrefWidth(35); bar2.setPrefHeight(120); bar2.setStyle("-fx-background-color: " + PURPLE_PRIMARY + "; -fx-background-radius: 8px 8px 0px 0px;");
        Region bar3 = new Region(); bar3.setPrefWidth(35); bar3.setPrefHeight(95); bar3.setStyle("-fx-background-color: " + PURPLE_VARIANT + "; -fx-background-radius: 8px 8px 0px 0px;");
        Region bar4 = new Region(); bar4.setPrefWidth(35); bar4.setPrefHeight(145); bar4.setStyle("-fx-background-color: " + PURPLE_DARK + "; -fx-background-radius: 8px 8px 0px 0px;");
        Region bar5 = new Region(); bar5.setPrefWidth(35); bar5.setPrefHeight(110); bar5.setStyle("-fx-background-color: " + PURPLE_PRIMARY + "; -fx-background-radius: 8px 8px 0px 0px;");
        Region bar6 = new Region(); bar6.setPrefWidth(35); bar6.setPrefHeight(170); bar6.setStyle("-fx-background-color: #6D48D7; -fx-background-radius: 8px 8px 0px 0px;");
        Region bar7 = new Region(); bar7.setPrefWidth(35); bar7.setPrefHeight(130); bar7.setStyle("-fx-background-color: " + PURPLE_DARK + "; -fx-background-radius: 8px 8px 0px 0px;");

        chart.getChildren().addAll(bar1, bar2, bar3, bar4, bar5, bar6, bar7);

        HBox days = new HBox(16);
        String[] dayNames = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (String dayName : dayNames) {
            Text day = new Text(dayName);
            day.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_MUTED + "; -fx-font-weight: 500;");
            HBox dayBox = new HBox(day);
            dayBox.setAlignment(Pos.CENTER);
            dayBox.setPrefWidth(35);
            days.getChildren().add(dayBox);
        }
        chartCard.getChildren().addAll(chartTitle, chart, days);

        // Hospital Resource Usage Card
        VBox resourceCard = new VBox(15);
        resourceCard.setPadding(new Insets(22));
        resourceCard.setStyle(BASE_CARD_STYLE);
        Text resourceTitle = new Text("Hospital Resource Usage");
        resourceTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        // Hospital 1
        Text hospital1Name = new Text("St. Mary's General");
        hospital1Name.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + TEXT_PRIMARY + "; -fx-font-weight: 500;");
        Text hospital1Percentage = new Text("88%");
        hospital1Percentage.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        Region hospital1Spacer = new Region();
        HBox.setHgrow(hospital1Spacer, Priority.ALWAYS);
        HBox hospital1Header = new HBox(hospital1Name, hospital1Spacer, hospital1Percentage);
        Region hospital1Background = new Region();
        hospital1Background.setPrefHeight(8);
        hospital1Background.setStyle("-fx-background-color: " + PURPLE_LIGHT + "; -fx-background-radius: 10px;");
        Region hospital1Progress = new Region();
        hospital1Progress.setPrefHeight(8);
        hospital1Progress.setPrefWidth(400);
        hospital1Progress.setStyle("-fx-background-color: " + PURPLE_BUTTON + "; -fx-background-radius: 10px;");
        StackPane hospital1ProgressPane = new StackPane(hospital1Background, hospital1Progress);
        hospital1ProgressPane.setAlignment(Pos.CENTER_LEFT);

        // Hospital 2
        Text hospital2Name = new Text("City Central Medical");
        hospital2Name.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + TEXT_PRIMARY + "; -fx-font-weight: 500;");
        Text hospital2Percentage = new Text("64%");
        hospital2Percentage.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        Region hospital2Spacer = new Region();
        HBox.setHgrow(hospital2Spacer, Priority.ALWAYS);
        HBox hospital2Header = new HBox(hospital2Name, hospital2Spacer, hospital2Percentage);
        Region hospital2Background = new Region();
        hospital2Background.setPrefHeight(8);
        hospital2Background.setStyle("-fx-background-color: " + PURPLE_LIGHT + "; -fx-background-radius: 10px;");
        Region hospital2Progress = new Region();
        hospital2Progress.setPrefHeight(8);
        hospital2Progress.setPrefWidth(290);
        hospital2Progress.setStyle("-fx-background-color: " + PURPLE_PRIMARY + "; -fx-background-radius: 10px;");
        StackPane hospital2ProgressPane = new StackPane(hospital2Background, hospital2Progress);
        hospital2ProgressPane.setAlignment(Pos.CENTER_LEFT);

        // Hospital 3
        Text hospital3Name = new Text("Regional Children's");
        hospital3Name.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + TEXT_PRIMARY + "; -fx-font-weight: 500;");
        Text hospital3Percentage = new Text("42%");
        hospital3Percentage.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        Region hospital3Spacer = new Region();
        HBox.setHgrow(hospital3Spacer, Priority.ALWAYS);
        HBox hospital3Header = new HBox(hospital3Name, hospital3Spacer, hospital3Percentage);
        Region hospital3Background = new Region();
        hospital3Background.setPrefHeight(8);
        hospital3Background.setStyle("-fx-background-color: " + PURPLE_LIGHT + "; -fx-background-radius: 10px;");
        Region hospital3Progress = new Region();
        hospital3Progress.setPrefHeight(8);
        hospital3Progress.setPrefWidth(190);
        hospital3Progress.setStyle("-fx-background-color: " + PURPLE_PRIMARY + "; -fx-background-radius: 10px;");
        StackPane hospital3ProgressPane = new StackPane(hospital3Background, hospital3Progress);
        hospital3ProgressPane.setAlignment(Pos.CENTER_LEFT);

        resourceCard.getChildren().addAll(resourceTitle, hospital1Header, hospital1ProgressPane, hospital2Header, hospital2ProgressPane, hospital3Header, hospital3ProgressPane);
        leftContent.getChildren().addAll(chartCard, resourceCard);

        // Right Part Live Feed Panel
        VBox rightPart = new VBox(20);
        rightPart.setPrefWidth(380);

        VBox liveCard = new VBox();
        liveCard.setStyle(BASE_CARD_STYLE);
        Text liveTitle = new Text("●  Live Emergency Feed");
        liveTitle.setStyle(FONT_STACK + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: " + DANGER_TEXT + ";");
        HBox liveHeader = new HBox(liveTitle);
        liveHeader.setPadding(new Insets(18));

        VBox feed1 = new VBox(5);
        feed1.setPadding(new Insets(14, 18, 14, 18));
        feed1.setStyle("-fx-border-color: " + BORDER_DIVIDER + "; -fx-border-width: 0px 0px 1px 0px;");
        Text feed1Priority = new Text("CRITICAL");
        feed1Priority.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + DANGER_TEXT + ";");
        Text feed1Title = new Text("Cardiac Arrest - Zone 4");
        feed1Title.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text feed1Text = new Text("Ambulance #A-204 dispatched. ETA 4 mins.");
        feed1Text.setWrappingWidth(320);
        feed1Text.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_SECONDARY + ";");
        feed1.getChildren().addAll(feed1Priority, feed1Title, feed1Text);

        VBox feed2 = new VBox(5);
        feed2.setPadding(new Insets(14, 18, 14, 18));
        feed2.setStyle("-fx-border-color: " + BORDER_DIVIDER + "; -fx-border-width: 0px 0px 1px 0px;");
        Text feed2Priority = new Text("MODERATE");
        feed2Priority.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + WARNING_TEXT + ";");
        Text feed2Title = new Text("Traffic Incident - Zone 2");
        feed2Title.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text feed2Text = new Text("2 vehicles involved. Dispatching support unit.");
        feed2Text.setWrappingWidth(320);
        feed2Text.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_SECONDARY + ";");
        feed2.getChildren().addAll(feed2Priority, feed2Title, feed2Text);

        VBox feed3 = new VBox(5);
        feed3.setPadding(new Insets(14, 18, 14, 18));
        Text feed3Priority = new Text("CRITICAL");
        feed3Priority.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + DANGER_TEXT + ";");
        Text feed3Title = new Text("Hospital Bed Shortage");
        feed3Title.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text feed3Text = new Text("City Central reaching capacity.");
        feed3Text.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_SECONDARY + ";");
        feed3.getChildren().addAll(feed3Priority, feed3Title, feed3Text);

        Button viewAllButton = new Button("View All Activities");
        viewAllButton.setMaxWidth(Double.MAX_VALUE);
        viewAllButton.setStyle(FONT_STACK + "-fx-background-color: transparent; -fx-text-fill: " + PURPLE_DARK + "; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 12px; -fx-cursor: hand;");

        liveCard.getChildren().addAll(liveHeader, feed1, feed2, feed3, viewAllButton);

        VBox healthCard = new VBox(12);
        healthCard.setPadding(new Insets(20));
        healthCard.setStyle(BASE_CARD_STYLE);
        Text healthTitle = new Text("System Health");
        healthTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text database = new Text("●  Database       Online");
        database.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + SUCCESS_TEXT + "; -fx-font-weight: 500;");
        Text dispatch = new Text("●  Dispatch API   Active");
        dispatch.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + SUCCESS_TEXT + "; -fx-font-weight: 500;");
        Text gps = new Text("●  GPS Tracking   Syncing");
        gps.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + PURPLE_DARK + "; -fx-font-weight: 500;");
        Text latency = new Text("●  Server Latency 14ms");
        latency.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + SUCCESS_TEXT + "; -fx-font-weight: 500;");
        healthCard.getChildren().addAll(healthTitle, database, dispatch, gps, latency);

        VBox notificationCard = new VBox(10);
        notificationCard.setPadding(new Insets(20));
        notificationCard.setStyle(BASE_CARD_STYLE);
        Text notificationTitle = new Text("Notifications");
        notificationTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text notification1 = new Text("System Update: Version 2.4.1 scheduled for 03:00 AM.");
        notification1.setWrappingWidth(330);
        notification1.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_PRIMARY + ";");
        Text notificationTime1 = new Text("1 hour ago");
        notificationTime1.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-fill: " + TEXT_MUTED + ";");
        Text notification2 = new Text("New Specialist Doctor registered at St. Mary's.");
        notification2.setWrappingWidth(330);
        notification2.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_PRIMARY + ";");
        Text notificationTime2 = new Text("3 hours ago");
        notificationTime2.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-fill: " + TEXT_MUTED + ";");
        notificationCard.getChildren().addAll(notificationTitle, notification1, notificationTime1, notification2, notificationTime2);

        rightPart.getChildren().addAll(liveCard, healthCard, notificationCard);
        mainContent.getChildren().addAll(leftContent, rightPart);

        // Quick Actions Section
        VBox quickActions = new VBox(12);
        Text quickTitle = new Text("Quick Actions");
        quickTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        HBox actionButtons = new HBox(12);
        Button addHospitalButton = new Button("+   Add Hospital");
        addHospitalButton.setPrefWidth(170); addHospitalButton.setPrefHeight(48); addHospitalButton.setStyle(SECONDARY_BUTTON_STYLE);
        Button registerAmbulanceButton = new Button("Register Ambulance");
        registerAmbulanceButton.setPrefWidth(190); registerAmbulanceButton.setPrefHeight(48); registerAmbulanceButton.setStyle(SECONDARY_BUTTON_STYLE);
        Button broadcastButton = new Button("Broadcast");
        broadcastButton.setPrefWidth(160); broadcastButton.setPrefHeight(48); broadcastButton.setStyle(SECONDARY_BUTTON_STYLE);
        Button liveFeedButton = new Button("Live Feed");
        liveFeedButton.setPrefWidth(160); liveFeedButton.setPrefHeight(48); liveFeedButton.setStyle(SECONDARY_BUTTON_STYLE);

        actionButtons.getChildren().addAll(addHospitalButton, registerAmbulanceButton, broadcastButton, liveFeedButton);
        quickActions.getChildren().addAll(quickTitle, actionButtons);

        // Assembly
        VBox dashboard = new VBox(20);
        dashboard.setPadding(new Insets(25));
        dashboard.getChildren().addAll(header, cardsRow1, cardsRow2, mainContent, quickActions);

        ScrollPane scrollPane = new ScrollPane(dashboard);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        rightContent.getChildren().add(scrollPane);
        borderPane.setCenter(rightContent);

        // --- NAVIGATION HANDLERS ---
        dashboardButton.setOnAction(event -> {
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            emergencyButton.setStyle(NAV_INACTIVE_STYLE);
            analyticsButton.setStyle(NAV_INACTIVE_STYLE);
            activityButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            dashboardButton.setStyle(NAV_ACTIVE_STYLE);

            borderPane.setCenter(rightContent);
        });

        hospitalButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            emergencyButton.setStyle(NAV_INACTIVE_STYLE);
            analyticsButton.setStyle(NAV_INACTIVE_STYLE);
            activityButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_ACTIVE_STYLE);

            AdminHospitalManagement hospManagement = new AdminHospitalManagement();
            borderPane.setCenter(hospManagement.getHospitalManagement());
        });

        ambulanceButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            emergencyButton.setStyle(NAV_INACTIVE_STYLE);
            analyticsButton.setStyle(NAV_INACTIVE_STYLE);
            activityButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_ACTIVE_STYLE);

            AdminAmbulanceManagement ambulManagement = new AdminAmbulanceManagement();
            borderPane.setCenter(ambulManagement.getAmbulanceManagement());
        });

        userButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            emergencyButton.setStyle(NAV_INACTIVE_STYLE);
            analyticsButton.setStyle(NAV_INACTIVE_STYLE);
            activityButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_ACTIVE_STYLE);

            AdminUserManagement userManagement = new AdminUserManagement();
            borderPane.setCenter(userManagement.getUserManagement());
        });

        policeButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            emergencyButton.setStyle(NAV_INACTIVE_STYLE);
            analyticsButton.setStyle(NAV_INACTIVE_STYLE);
            activityButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_ACTIVE_STYLE);

            AdminPoliceManagement policeManagement = new AdminPoliceManagement();
            borderPane.setCenter(policeManagement.getPoliceManagement());
        });

        emergencyButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            analyticsButton.setStyle(NAV_INACTIVE_STYLE);
            activityButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            emergencyButton.setStyle(NAV_ACTIVE_STYLE);

            AdminEmergencyMonitoring emergencyMonitoring = new AdminEmergencyMonitoring();
            borderPane.setCenter(emergencyMonitoring.getEmergencyMonitoring());
        });

        analyticsButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            emergencyButton.setStyle(NAV_INACTIVE_STYLE);
            activityButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            analyticsButton.setStyle(NAV_ACTIVE_STYLE);

            AdminAnalyticsAndReports analyticsReports = new AdminAnalyticsAndReports();
            borderPane.setCenter(analyticsReports.getAnalyticsAndReports());
        });

        activityButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            emergencyButton.setStyle(NAV_INACTIVE_STYLE);
            analyticsButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            activityButton.setStyle(NAV_ACTIVE_STYLE);

            AdminActivityLogs activityLogs = new AdminActivityLogs();
            borderPane.setCenter(activityLogs.getActivityLogsPage());
        });

        settingsButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            emergencyButton.setStyle(NAV_INACTIVE_STYLE);
            analyticsButton.setStyle(NAV_INACTIVE_STYLE);
            activityButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_ACTIVE_STYLE);

            AdminSettings settings = new AdminSettings();
            borderPane.setCenter(settings.getSettingsPage());
        });

        logoutButton.setOnAction(event -> {
            // Optional logout logic
        });

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        dashboardScene = new Scene(borderPane, visualBounds.getWidth(), visualBounds.getHeight());

        dashboardStage.setX(visualBounds.getMinX());
        dashboardStage.setY(visualBounds.getMinY());
        dashboardStage.setWidth(visualBounds.getWidth());
        dashboardStage.setHeight(visualBounds.getHeight());

        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("LifeLink Admin Dashboard");
        dashboardStage.setMaximized(true);
        dashboardStage.show();
    }

    // =========================================================================
    // EMBEDDED EMERGENCY DISPATCH & ASSIGNMENT CARD METHOD
    // =========================================================================
    public VBox buildEmergencyDispatchCard() {
        VBox root = new VBox(16);
        root.setPadding(new Insets(24));
        root.setStyle("-fx-background-color: " + BG_PAGE + ";" + FONT_STACK);

        // Header Row
        HBox topHeader = new HBox();
        topHeader.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(2);
        Text title = new Text("Emergency Request Dispatch Center");
        title.setStyle(FONT_STACK + "-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text subtitle = new Text("Assign coordinates, change live trip status, and open routes in 'adminEmergencyRequests'.");
        subtitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-fill: " + TEXT_SECONDARY + ";");
        titleBox.getChildren().addAll(title, subtitle);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Button backButton = new Button("← Back to Dashboard");
        backButton.setStyle(SECONDARY_BUTTON_STYLE);
        backButton.setOnAction(e -> borderPane.setCenter(rightContent));

        topHeader.getChildren().addAll(titleBox, headerSpacer, backButton);

        // Split Horizontal Layout
        HBox mainSplit = new HBox(20);
        mainSplit.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(mainSplit, Priority.ALWAYS);

        // Left Dispatch Form Box
        VBox leftFormPane = new VBox(14);
        leftFormPane.setPrefWidth(480);
        leftFormPane.setPadding(new Insets(20));
        leftFormPane.setStyle(BASE_CARD_STYLE);

        Text formTitle = new Text("Dispatch New Emergency");
        formTitle.setStyle(FONT_STACK + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER_LEFT);

        Label tripIdLbl = createFieldLabel("Trip ID:");
        tripIdField = createTextField("e.g., TRIP-9081");
        tripIdField.setText("TRIP-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());

        Label patIdLbl = createFieldLabel("Patient ID:");
        patIdField = createTextField("e.g., PAT-104");

        Label sourceLbl = createFieldLabel("Source / Pickup:");
        sourceField = createTextField("e.g., Sector 4, Station Road");

        Label destinationLbl = createFieldLabel("Destination:");
        destinationField = createTextField("e.g., City Care Hospital, Ward 2");

        Label nurseIdLbl = createFieldLabel("Nurse ID / Email:");
        nurseIdField = createTextField("nurse1@lifelink.com");
        nurseIdField.setText("nurse1@lifelink.com");

        Label driverIdLbl = createFieldLabel("Driver ID / Email:");
        driverIdField = createTextField("driver1@lifelink.com");
        driverIdField.setText("driver1@lifelink.com");

        Label statusLbl = createFieldLabel("Status:");
        statusBox = new ComboBox<>();
        statusBox.getItems().addAll("PENDING", "ASSIGNED", "IN_PROGRESS", "COMPLETED", "CANCELLED");
        statusBox.setValue("PENDING");
        statusBox.setPrefWidth(260);
        statusBox.setStyle("-fx-background-color: " + BG_SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 6; -fx-background-radius: 6;");

        Label timeLbl = createFieldLabel("Timestamp:");
        timestampField = createTextField("Auto-generated Firestore Timestamp");
        timestampField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        timestampField.setEditable(false);
        timestampField.setStyle(timestampField.getStyle() + " -fx-opacity: 0.85;");

        grid.add(tripIdLbl, 0, 0);
        grid.add(tripIdField, 1, 0);

        grid.add(patIdLbl, 0, 1);
        grid.add(patIdField, 1, 1);

        grid.add(sourceLbl, 0, 2);
        grid.add(sourceField, 1, 2);

        grid.add(destinationLbl, 0, 3);
        grid.add(destinationField, 1, 3);

        grid.add(nurseIdLbl, 0, 4);
        grid.add(nurseIdField, 1, 4);

        grid.add(driverIdLbl, 0, 5);
        grid.add(driverIdField, 1, 5);

        grid.add(statusLbl, 0, 6);
        grid.add(statusBox, 1, 6);

        grid.add(timeLbl, 0, 7);
        grid.add(timestampField, 1, 7);

        submitDispatchBtn = new Button("Dispatch Request");
        submitDispatchBtn.setStyle(PRIMARY_BUTTON_STYLE);
        submitDispatchBtn.setOnAction(e -> handleDispatchSubmission());

        Button resetBtn = new Button("Reset");
        resetBtn.setStyle(SECONDARY_BUTTON_STYLE);
        resetBtn.setOnAction(e -> resetDispatchForm());

        HBox btnBox = new HBox(10, submitDispatchBtn, resetBtn);
        btnBox.setAlignment(Pos.CENTER_LEFT);
        btnBox.setPadding(new Insets(6, 0, 0, 0));

        dispatchStatusLabel = new Label();
        dispatchStatusLabel.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        leftFormPane.getChildren().addAll(formTitle, grid, btnBox, dispatchStatusLabel);

        // Right Live Feed Box
        VBox rightDataPane = new VBox(12);
        rightDataPane.setPadding(new Insets(20));
        rightDataPane.setStyle(BASE_CARD_STYLE);

        HBox feedTopRow = new HBox(10);
        feedTopRow.setAlignment(Pos.CENTER_LEFT);

        Text feedTitle = new Text("Live Dispatches Feed & Status Control");
        feedTitle.setStyle(FONT_STACK + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        totalFeedBadge = new Text("0 Logged");
        totalFeedBadge.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");

        Region feedSpacer = new Region();
        HBox.setHgrow(feedSpacer, Priority.ALWAYS);

        Button refreshBtn = new Button("↻ Fetch All");
        refreshBtn.setStyle(SECONDARY_BUTTON_STYLE);
        refreshBtn.setOnAction(e -> manualFetchAllDispatches());

        feedTopRow.getChildren().addAll(feedTitle, totalFeedBadge, feedSpacer, refreshBtn);

        feedSearchField = new TextField();
        feedSearchField.setPromptText("🔍 Filter by Trip ID, Patient ID, Source, Nurse, or Driver...");
        feedSearchField.setStyle("-fx-background-color: " + BG_PAGE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 6 10; -fx-font-size: 12px;");
        feedSearchField.textProperty().addListener((obs, oldVal, newVal) -> filterAndRenderFeed(newVal));

        emergencyFeedCardsContainer = new VBox(10);
        emergencyFeedCardsContainer.setAlignment(Pos.TOP_CENTER);

        ScrollPane feedScroll = new ScrollPane(emergencyFeedCardsContainer);
        feedScroll.setFitToWidth(true);
        feedScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(feedScroll, Priority.ALWAYS);

        rightDataPane.getChildren().addAll(feedTopRow, feedSearchField, feedScroll);

        HBox.setHgrow(leftFormPane, Priority.NEVER);
        HBox.setHgrow(rightDataPane, Priority.ALWAYS);

        mainSplit.getChildren().addAll(leftFormPane, rightDataPane);
        root.getChildren().addAll(topHeader, mainSplit);

        // Start Firestore Live Stream
        startRealtimeDispatchListener();

        return root;
    }

    private void handleDispatchSubmission() {
        String tripId = tripIdField.getText().trim();
        String patId = patIdField.getText().trim();
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();
        String nurseId = nurseIdField.getText().trim();
        String driverId = driverIdField.getText().trim();
        String status = statusBox.getValue();

        if (tripId.isEmpty() || patId.isEmpty() || source.isEmpty() || destination.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Trip ID, Patient ID, Source, and Destination are required.");
            return;
        }

        submitDispatchBtn.setDisable(true);
        dispatchStatusLabel.setText("Writing dispatch request to Firebase Firestore...");

        new Thread(() -> {
            boolean success = emgController.dispatchEmergencyRequest(
                tripId, patId, source, destination, nurseId, driverId, status
            );

            Platform.runLater(() -> {
                submitDispatchBtn.setDisable(false);
                if (success) {
                    dispatchStatusLabel.setText("Trip #" + tripId + " dispatched successfully!");
                    resetDispatchForm();
                } else {
                    dispatchStatusLabel.setText("Failed to dispatch request. Check logs.");
                }
            });
        }).start();
    }

    private void startRealtimeDispatchListener() {
        emgController.subscribeToLiveRequests(list -> {
            Platform.runLater(() -> {
                liveEmergencyList.clear();
                liveEmergencyList.addAll(list);
                totalFeedBadge.setText(list.size() + " Requests Logged");
                filterAndRenderFeed(feedSearchField != null ? feedSearchField.getText() : "");
            });
        });
    }

    private void manualFetchAllDispatches() {
        new Thread(() -> {
            List<AdminSideEmgReqModel> list = emgController.getAllRequests();
            Platform.runLater(() -> {
                liveEmergencyList.clear();
                liveEmergencyList.addAll(list);
                totalFeedBadge.setText(list.size() + " Requests Logged");
                filterAndRenderFeed(feedSearchField != null ? feedSearchField.getText() : "");
            });
        }).start();
    }

    private void filterAndRenderFeed(String query) {
        emergencyFeedCardsContainer.getChildren().clear();
        String q = (query == null) ? "" : query.trim().toLowerCase();

        List<AdminSideEmgReqModel> filtered = new ArrayList<>();
        for (AdminSideEmgReqModel item : liveEmergencyList) {
            boolean match = q.isEmpty()
                    || (item.getTripID() != null && item.getTripID().toLowerCase().contains(q))
                    || (item.getPatID() != null && item.getPatID().toLowerCase().contains(q))
                    || (item.getSource() != null && item.getSource().toLowerCase().contains(q))
                    || (item.getDestination() != null && item.getDestination().toLowerCase().contains(q))
                    || (item.getNurseID() != null && item.getNurseID().toLowerCase().contains(q))
                    || (item.getDriverID() != null && item.getDriverID().toLowerCase().contains(q));

            if (match) filtered.add(item);
        }

        if (filtered.isEmpty()) {
            VBox emptyBox = new VBox(6);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(30));

            Text emptyText = new Text(liveEmergencyList.isEmpty() ? "No emergency requests found in Firestore." : "No records match \"" + query + "\"");
            emptyText.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + TEXT_MUTED + ";");
            emptyBox.getChildren().add(emptyText);
            emergencyFeedCardsContainer.getChildren().add(emptyBox);
            return;
        }

        for (AdminSideEmgReqModel req : filtered) {
            emergencyFeedCardsContainer.getChildren().add(createDispatchCardRow(req));
        }
    }

    // =========================================================================
    // DISPATCH REQUEST CARD WITH STATUS CHANGER & MAP BUTTON
    // =========================================================================
    private VBox createDispatchCardRow(AdminSideEmgReqModel req) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(14));
        card.setStyle("-fx-background-color: " + BG_PAGE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 1; -fx-border-radius: 10; -fx-background-radius: 10;");

        // Top Row: Trip ID + Patient ID + Map Button + Live Status ComboBox
        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);

        // 1. Google Map Action on Left
        Button mapBtn = new Button("📍 Map");
        mapBtn.setStyle(FONT_STACK + "-fx-background-color: " + PURPLE_LIGHT + "; -fx-text-fill: " + PURPLE_DARK + "; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4 8; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-radius: 5px;-fx-border-width: 1px; -fx-border-color:"+ PURPLE_DARK);
        mapBtn.setOnAction(e -> openRouteMap(req.getSource(), req.getDestination()));

        Text tripText = new Text(req.getTripID() != null ? req.getTripID() : "UNASSIGNED");
        tripText.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");

        Text patientText = new Text("• Pat: " + (req.getPatID() != null ? req.getPatID() : "N/A"));
        patientText.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // 2. Status Changer Option
        Label statusLabel = new Label("Status:");
        statusLabel.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        ComboBox<String> cardStatusBox = new ComboBox<>();
        cardStatusBox.getItems().addAll("PENDING", "ASSIGNED", "IN_PROGRESS", "COMPLETED", "CANCELLED");
        cardStatusBox.setValue(req.getStatus() != null ? req.getStatus().toUpperCase() : "PENDING");
        cardStatusBox.setStyle(FONT_STACK + "-fx-background-color: " + BG_SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 6; -fx-background-radius: 6; -fx-font-size: 11px;");

        cardStatusBox.setOnAction(e -> {
            String newStatus = cardStatusBox.getValue();
            if (newStatus != null && !newStatus.equalsIgnoreCase(req.getStatus())) {
                req.setStatus(newStatus);
                updateRequestStatus(req);
            }
        });

        topRow.getChildren().addAll(tripText, patientText, spacer, statusLabel, cardStatusBox);

        // Route Row
        HBox routeRow = new HBox(6);
        routeRow.setAlignment(Pos.CENTER_LEFT);
        Circle dot = new Circle(3, Color.web(PURPLE_DARK));
        Text routeText = new Text((req.getSource() != null ? req.getSource() : "Unknown") + "  ➔  " + (req.getDestination() != null ? req.getDestination() : "Unknown"));
        routeText.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_SECONDARY + ";");
        routeRow.getChildren().addAll(dot, routeText);

        // Bottom Row: Staff & Timestamp
        HBox bottomRow = new HBox(12);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        Text staffInfo = new Text("👩‍⚕ " + (req.getNurseID() != null ? req.getNurseID() : "None") + "  |  🚑 " + (req.getDriverID() != null ? req.getDriverID() : "None") +" | ");
        staffInfo.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + TEXT_MUTED + ";");

        Region bottomSpacer = new Region();
        HBox.setHgrow(bottomSpacer, Priority.ALWAYS);

        String formattedTimestamp = formatFirestoreTimestamp(req.getTimestamp());
        Text timeText = new Text(formattedTimestamp);
        timeText.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-fill: " + TEXT_MUTED + ";");

        bottomRow.getChildren().addAll(staffInfo,mapBtn, bottomSpacer, timeText);
        card.getChildren().addAll(topRow, routeRow, bottomRow);

        return card;
    }

    private void updateRequestStatus(AdminSideEmgReqModel req) {
        new Thread(() -> {
            emgController.createEmergencyRequest(req);
            Platform.runLater(() -> System.out.println("Trip #" + req.getTripID() + " status updated to " + req.getStatus()));
        }).start();
    }

    private void openRouteMap(String source, String destination) {
        try {
            String dest = (destination != null && !destination.trim().isEmpty()) ? destination : "Hospital";
            String src = (source != null && !source.trim().isEmpty()) ? source : "";
            
            String url = "https://www.google.com/maps/dir/?api=1&origin=" 
                    + URLEncoder.encode(src, StandardCharsets.UTF_8) 
                    + "&destination=" 
                    + URLEncoder.encode(dest, StandardCharsets.UTF_8);

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String formatFirestoreTimestamp(Timestamp ts) {
        if (ts == null) return "Just now";
        try {
            return ts.toDate().toInstant()
                     .atZone(ZoneId.systemDefault())
                     .format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
        } catch (Exception e) {
            return "N/A";
        }
    }

    private void resetDispatchForm() {
        tripIdField.setText("TRIP-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        patIdField.clear();
        sourceField.clear();
        destinationField.clear();
        timestampField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        statusBox.setValue("PENDING");
    }

    private Label createFieldLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(FONT_STACK + "-fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-size: 11px;");
        return lbl;
    }

    private TextField createTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(260);
        tf.setStyle("-fx-background-color: " + BG_SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 6 8; -fx-font-size: 11px; -fx-text-fill: " + TEXT_PRIMARY + ";");
        return tf;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}