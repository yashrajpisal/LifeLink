package com.kurukshetra.view.admin;

import com.kurukshetra.view.util.ShimmerLoader;
import com.kurukshetra.view.util.ShimmerLoader.ShimmerPane;
import com.kurukshetra.controller.admin.AdminAmbulanceAssignmentController;
import com.kurukshetra.model.admin.AdminAmbulanceAssignmentModel;

import com.kurukshetra.controller.admin.AdminSideEmgReqController;
import com.kurukshetra.model.admin.AdminSideEmgReqModel;
import com.kurukshetra.view.Welcome;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private ComboBox<String> ambulanceIdBox;
    private List<AdminAmbulanceAssignmentModel> ambulanceAssignments = new ArrayList<>();
    private final AdminAmbulanceAssignmentController assignmentController = new AdminAmbulanceAssignmentController();

    public static Stage dashboardStage;
    private Scene dashboardScene;
    private BorderPane borderPane;
    private VBox rightContent;

    // Design Tokens - LifeLink Premium Theme
    private static final String BG_PAGE = "#F3F4F6"; // Slate-50/100
    private static final String BG_SURFACE = "#FFFFFF";
    private static final String BORDER_COLOR = "#E5E7EB"; // Gray-200
    private static final String BORDER_DIVIDER = "#F3F4F6";

    private static final String TEXT_PRIMARY = "#111827"; // Gray-900
    private static final String TEXT_SECONDARY = "#4B5563"; // Gray-600
    private static final String TEXT_MUTED = "#9CA3AF"; // Gray-400

    private static final String PURPLE_PRIMARY = "#6366F1"; // Indigo-500
    private static final String PURPLE_DARK = "#4F46E5"; // Indigo-600
    private static final String PURPLE_BUTTON = "linear-gradient(to right, #6366F1, #8B5CF6)"; // Indigo to Purple gradient
    private static final String PURPLE_LIGHT = "#EEF2FF"; // Indigo-50
    private static final String PURPLE_VARIANT = "#E0E7FF"; // Indigo-100

    private static final String SUCCESS_TEXT = "#059669";
    private static final String SUCCESS_BG = "#D1FAE5";
    private static final String WARNING_TEXT = "#D97706";
    private static final String WARNING_BG = "#FEF3C7";
    private static final String DANGER_TEXT = "#DC2626";
    private static final String DANGER_BG = "#FEE2E2";
    private static final String DANGER_BORDER = "#FECACA";

    private static final String CARD_SHADOW = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 20, 0.1, 0, 10);";
    private static final String FONT_STACK = "-fx-font-family: 'Inter', 'Segoe UI', -apple-system, sans-serif;";

    // Style Helpers
    private static final String NAV_ACTIVE_STYLE = FONT_STACK +
            "-fx-background-color: #FFFFFF;" +
            "-fx-text-fill: " + PURPLE_DARK + ";" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 12px;" +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.05), 10, 0, 0, 4);" +
            "-fx-cursor: hand;";

    private static final String NAV_INACTIVE_STYLE = FONT_STACK +
            "-fx-background-color: transparent;" +
            "-fx-text-fill: #FFFFFF;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: 500;" +
            "-fx-background-radius: 12px;" +
            "-fx-cursor: hand;";

    private static final String NAV_HOVER_STYLE = FONT_STACK +
            "-fx-background-color: linear-gradient(to right, rgba(99, 102, 241, 0.3), rgba(139, 92, 246, 0.2));" +
            "-fx-text-fill: #FFFFFF;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 12px;" +
            "-fx-border-color: rgba(99, 102, 241, 0.4);" +
            "-fx-border-radius: 12px;" +
            "-fx-border-width: 1px;" +
            "-fx-effect: dropshadow(gaussian, rgba(99, 102, 241, 0.25), 10, 0.1, 0, 4);" +
            "-fx-cursor: hand;";

    private static final String BASE_CARD_STYLE = FONT_STACK +
            "-fx-background-color: " + BG_SURFACE + ";" +
            "-fx-background-radius: 20px;" +
            "-fx-border-color: " + BORDER_COLOR + ";" +
            "-fx-border-radius: 20px;" +
            "-fx-border-width: 1px;" +
            CARD_SHADOW;

    private static final String SECONDARY_BUTTON_STYLE = FONT_STACK +
            "-fx-background-color: " + BG_SURFACE + ";" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-color: " + BORDER_COLOR + ";" +
            "-fx-border-radius: 12px;" +
            "-fx-background-radius: 12px;" +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.02), 4, 0, 0, 2);" +
            "-fx-cursor: hand;";

    private static final String PRIMARY_BUTTON_STYLE = FONT_STACK +
            "-fx-background-color: " + PURPLE_BUTTON + ";" +
            "-fx-text-fill: #FFFFFF;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 12px;" +
            "-fx-effect: dropshadow(gaussian, rgba(99, 102, 241, 0.4), 14, 0.2, 0, 4);" +
            "-fx-cursor: hand;";

    // Backend Controller & Cache
    private final AdminSideEmgReqController emgController = new AdminSideEmgReqController();
    private final List<AdminSideEmgReqModel> liveEmergencyList = new ArrayList<>();

    // Form Controls
    private TextField tripIdField;
    private TextField patIdField;
    private TextField pickupLocationField;
    private TextField destinationField;
    private TextField destLatField;
    private TextField destLngField;
    private TextField nurseIdField;
    private TextField driverIdField;
    private TextField timestampField;
    private Button submitDispatchBtn;
    private Label dispatchStatusLabel;

    // Feed Controls
    private VBox emergencyFeedCardsContainer;
    private TextField feedSearchField;
    private Text totalFeedBadge;
    private boolean isLiveListenerStarted = false;


    private void loadAmbulanceAssignments() {

        new Thread(() -> {

            try {

                List<AdminAmbulanceAssignmentModel> assignments =
                        assignmentController.getAllAssignments();

                Platform.runLater(() -> {

                    ambulanceAssignments.clear();

                    ambulanceAssignments.addAll(
                            assignments
                    );

                    ambulanceIdBox.getItems().clear();

                    for (
                            AdminAmbulanceAssignmentModel assignment :
                            assignments) {

                        if (assignment.getAmbulanceId() != null &&
                                !assignment.getAmbulanceId().trim().isEmpty()) {

                            ambulanceIdBox.getItems().add(
                                    assignment.getAmbulanceId()
                            );
                        }
                    }
                });

            } catch (
                    Exception ex) {

                ex.printStackTrace();
            }

        }).start();
    }



    @Override
    public void start(Stage stage) throws Exception {
        dashboardStage = stage;

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: " + BG_PAGE + ";");

        // --- SIDEBAR NAVIGATION ---
        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(25, 16, 20, 16));
        leftMenu.setPrefWidth(250);
        leftMenu.setStyle(
                "-fx-background-image: url('/assets/Images/adminDashboardbackground.png');" +
                "-fx-background-size: cover;" +
                "-fx-background-position: center center;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-width: 0px 1px 0px 0px;");

        HBox brandRow = new HBox(12);
        brandRow.setAlignment(Pos.CENTER_LEFT);

        ImageView logoView = new ImageView(new Image(getClass().getResourceAsStream("/assets/Images/LifeLinkLogo.png")));
        logoView.setFitWidth(30);
        logoView.setFitHeight(30);
        logoView.setPreserveRatio(true);

        Text lifeLinkText = new Text("LifeLink");
        lifeLinkText.setStyle(FONT_STACK + "-fx-font-size: 25px; -fx-font-weight: bold; -fx-fill: #ffffff");

        brandRow.getChildren().addAll(logoView, lifeLinkText);

        Text adminText = new Text("Admin Dashboard");
        adminText.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-fill: " + TEXT_MUTED + ";");

        VBox profileBox = new VBox(8);
        profileBox.getChildren().addAll(brandRow, adminText);
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

        Button staffManagementButton = new Button("Staff Management");
        staffManagementButton.setPrefWidth(220);
        staffManagementButton.setPrefHeight(45);
        staffManagementButton.setStyle(NAV_INACTIVE_STYLE);

        Button userButton = new Button("User Management");
        userButton.setPrefWidth(220);
        userButton.setPrefHeight(45);
        userButton.setStyle(NAV_INACTIVE_STYLE);

        Button policeButton = new Button("Police Management");
        policeButton.setPrefWidth(220);
        policeButton.setPrefHeight(45);
        policeButton.setStyle(NAV_INACTIVE_STYLE);


        Button complaintButton = new Button("Complaint Receiver");
        complaintButton.setPrefWidth(220);
        complaintButton.setPrefHeight(45);
        complaintButton.setStyle(NAV_INACTIVE_STYLE);

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

        Button[] navButtons = {dashboardButton, hospitalButton, ambulanceButton, staffManagementButton, userButton, policeButton, complaintButton, settingsButton};
        for (Button btn : navButtons) {
            btn.setOnMouseEntered(e -> {
                if (!btn.getStyle().equals(NAV_ACTIVE_STYLE)) {
                    btn.setStyle(NAV_HOVER_STYLE);
                }
            });
            btn.setOnMouseExited(e -> {
                if (!btn.getStyle().equals(NAV_ACTIVE_STYLE)) {
                    btn.setStyle(NAV_INACTIVE_STYLE);
                }
            });
        }

        leftMenu.getChildren().addAll(
                profileBox,
                dashboardButton,
                hospitalButton,
                ambulanceButton,
                staffManagementButton,
                userButton,
                policeButton,
                complaintButton,
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
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            dashboardButton.setStyle(NAV_ACTIVE_STYLE);
            complaintButton.setStyle(NAV_INACTIVE_STYLE);
            staffManagementButton.setStyle(NAV_INACTIVE_STYLE);

            borderPane.setCenter(rightContent);
        });

        hospitalButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_ACTIVE_STYLE);
            complaintButton.setStyle(NAV_INACTIVE_STYLE);
            staffManagementButton.setStyle(NAV_INACTIVE_STYLE);

            AdminHospitalManagement hospManagement = new AdminHospitalManagement();
            borderPane.setCenter(hospManagement.getHospitalManagement());
        });

        ambulanceButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_ACTIVE_STYLE);
            complaintButton.setStyle(NAV_INACTIVE_STYLE);
            staffManagementButton.setStyle(NAV_INACTIVE_STYLE);


            AdminAmbulanceManagement ambulManagement = new AdminAmbulanceManagement();
            borderPane.setCenter(ambulManagement.getAmbulanceManagement());
        });

        userButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_ACTIVE_STYLE);
            complaintButton.setStyle(NAV_INACTIVE_STYLE);
            staffManagementButton.setStyle(NAV_INACTIVE_STYLE);

            AdminUserManagement userManagement = new AdminUserManagement();
            borderPane.setCenter(userManagement.getUserManagement());
        });

        policeButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_ACTIVE_STYLE);
            complaintButton.setStyle(NAV_INACTIVE_STYLE);
            staffManagementButton.setStyle(NAV_INACTIVE_STYLE);


            AdminPoliceManagement policeManagement = new AdminPoliceManagement();
            borderPane.setCenter(policeManagement.getPoliceManagement());
        });

        


        staffManagementButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            staffManagementButton.setStyle(NAV_ACTIVE_STYLE);
            complaintButton.setStyle(NAV_INACTIVE_STYLE);

            AdminStaffManagement staffManagement = new AdminStaffManagement();
            borderPane.setCenter(staffManagement.getStaffManagement());
        });

        complaintButton.setOnAction(e -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            complaintButton.setStyle(NAV_ACTIVE_STYLE);
            settingsButton.setStyle(NAV_INACTIVE_STYLE);
            staffManagementButton.setStyle(NAV_INACTIVE_STYLE);


            AdminComplaintReceiver complaintReceiver = new AdminComplaintReceiver();
            borderPane.setCenter(complaintReceiver.getComplaintReceiver());
        });


        settingsButton.setOnAction(event -> {
            dashboardButton.setStyle(NAV_INACTIVE_STYLE);
            hospitalButton.setStyle(NAV_INACTIVE_STYLE);
            ambulanceButton.setStyle(NAV_INACTIVE_STYLE);
            userButton.setStyle(NAV_INACTIVE_STYLE);
            policeButton.setStyle(NAV_INACTIVE_STYLE);
            settingsButton.setStyle(NAV_ACTIVE_STYLE);
            complaintButton.setStyle(NAV_INACTIVE_STYLE);
            staffManagementButton.setStyle(NAV_INACTIVE_STYLE);


            AdminSettings settings = new AdminSettings();
            borderPane.setCenter(settings.getSettingsPage());
        });

        logoutButton.setOnAction(event -> {
             try {
                Welcome welcome = new Welcome();
                welcome.start(dashboardStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

        dashboardStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

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
        tripIdField = createTextField("Auto-generated Trip ID");
        tripIdField.setText(generateNextTripId());
        tripIdField.setEditable(false);
        tripIdField.setStyle(tripIdField.getStyle() + "-fx-opacity: 0.85;");

        Label patIdLbl = createFieldLabel("Patient ID:");
        patIdField = createTextField("Auto-generated Patient ID");
        patIdField.setText(generateNextPatientId());
        patIdField.setEditable(false);
        patIdField.setStyle(patIdField.getStyle() + "-fx-opacity: 0.85;");

        Label pickupLocationLbl = createFieldLabel("Pickup Location:");
        pickupLocationField = createTextField("Patient Pickup Location e.g., Swargate");
        pickupLocationField.setText("Current AMB LOC ");

        Label destinationLbl = createFieldLabel("Destination:");
        destinationField = createTextField("e.g., pickuplocation, Ward 2");

        Label destCoordsLbl = createFieldLabel("Dest Lat / Lng:");
        destLatField = createTextField("Lat e.g., 18.5204");
        destLatField.setPrefWidth(125);
        destLngField = createTextField("Lng e.g., 73.8567");
        destLngField.setPrefWidth(125);
        HBox destCoordsBox = new HBox(10, destLatField, destLngField);
        destCoordsBox.setAlignment(Pos.CENTER_LEFT);

        Label ambulanceIdLbl = createFieldLabel("Ambulance ID:");

        ambulanceIdBox = new ComboBox<>();
        ambulanceIdBox.setPromptText("Select Ambulance");
        ambulanceIdBox.setPrefWidth(260);
        ambulanceIdBox.setStyle(
                "-fx-background-color: " + BG_SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 6;" +
                "-fx-background-radius: 6;"
        );


        ambulanceIdBox.setOnAction(e -> {

        String selectedAmbulance =
                ambulanceIdBox.getValue();

        if (selectedAmbulance == null ||
                selectedAmbulance.trim().isEmpty()) {

            nurseIdField.clear();
            driverIdField.clear();

            return;
        }

        for (
                AdminAmbulanceAssignmentModel assignment :
                ambulanceAssignments) {

            if (selectedAmbulance.equals(
                    assignment.getAmbulanceId())) {

                nurseIdField.setText(
                        assignment.getNurseEmail()
                );

                driverIdField.setText(
                        assignment.getDriverEmail()
                );

                return;
            }
        }

        nurseIdField.clear();
        driverIdField.clear();

        showAlert(
                Alert.AlertType.WARNING,
                "No Staff Assignment",
                "No driver and nurse are assigned to ambulance " +
                selectedAmbulance +
                "."
        );
    });





        Label nurseIdLbl = createFieldLabel("Nurse ID / Email:");
        nurseIdField = createTextField("Automatically assigned");
        nurseIdField.setEditable(false);
        nurseIdField.setStyle(
                nurseIdField.getStyle() +
                "-fx-opacity: 0.85;"
        );

        Label driverIdLbl = createFieldLabel("Driver ID / Email:");
        driverIdField = createTextField("Automatically assigned");
        driverIdField.setEditable(false);
        driverIdField.setStyle(
        driverIdField.getStyle() +
        "-fx-opacity: 0.85;"
    );

        Label timeLbl = createFieldLabel("Timestamp:");
        timestampField = createTextField("Auto-generated Firestore Timestamp");
        timestampField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        timestampField.setEditable(false);
        timestampField.setStyle(timestampField.getStyle() + " -fx-opacity: 0.85;");

        grid.add(tripIdLbl, 0, 0);
        grid.add(tripIdField, 1, 0);

        grid.add(patIdLbl, 0, 1);
        grid.add(patIdField, 1, 1);

        grid.add(pickupLocationLbl, 0, 2);
        grid.add(pickupLocationField, 1, 2);

        grid.add(destinationLbl, 0, 3);
        grid.add(destinationField, 1, 3);

        grid.add(destCoordsLbl, 0, 4);
        grid.add(destCoordsBox, 1, 4);

        grid.add(ambulanceIdLbl, 0, 5);
        grid.add(ambulanceIdBox, 1, 5);

        grid.add(nurseIdLbl, 0, 6);
        grid.add(nurseIdField, 1, 6);

        grid.add(driverIdLbl, 0, 7);
        grid.add(driverIdField, 1, 7);

        grid.add(timeLbl, 0, 8);
        grid.add(timestampField, 1, 8);


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
        feedScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        feedScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
        loadAmbulanceAssignments();

        return root;
    }

    private void handleDispatchSubmission() {
        String tripId = (tripIdField != null && !tripIdField.getText().trim().isEmpty()) 
                ? tripIdField.getText().trim() 
                : generateNextTripId();
        String patId = (patIdField != null && !patIdField.getText().trim().isEmpty()) 
                ? patIdField.getText().trim() 
                : generateNextPatientId();
        String source = (pickupLocationField != null && !pickupLocationField.getText().trim().isEmpty())
                ? pickupLocationField.getText().trim()
                : "Swargate";
        String destination = destinationField != null ? destinationField.getText().trim() : "";
        String ambulanceId = ambulanceIdBox != null ? ambulanceIdBox.getValue() : null;
        String nurseId = nurseIdField != null ? nurseIdField.getText().trim() : "";
        String driverId = driverIdField != null ? driverIdField.getText().trim() : "";
        String status = "PENDING";

        if (destination.isEmpty()) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Destination Required",
                    "Please enter a destination hospital or location."
            );
            return;
        }

        Double destLat = null;
        Double destLng = null;
        if (destLatField != null && !destLatField.getText().trim().isEmpty()) {
            try {
                destLat = Double.parseDouble(destLatField.getText().trim());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Invalid Latitude", "Please enter a valid numeric value for Latitude (e.g., 18.5204).");
                return;
            }
        }

        if (destLngField != null && !destLngField.getText().trim().isEmpty()) {
            try {
                destLng = Double.parseDouble(destLngField.getText().trim());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.WARNING, "Invalid Longitude", "Please enter a valid numeric value for Longitude (e.g., 73.8567).");
                return;
            }
        }

        if (ambulanceId == null || ambulanceId.trim().isEmpty()) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Ambulance Required",
                    "Please select an ambulance ID."
            );
            return;
        }

        if (driverId.isEmpty() || nurseId.isEmpty()) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Staff Not Assigned",
                    "The selected ambulance does not have an assigned driver and nurse."
            );
            return;
        }

        submitDispatchBtn.setDisable(true);
        dispatchStatusLabel.setText("Writing dispatch request to Firebase Firestore...");

        final Double finalLat = destLat;
        final Double finalLng = destLng;

        new Thread(() -> {
            boolean success = emgController.dispatchEmergencyRequest(
                tripId, patId, source, destination, nurseId, driverId, status, ambulanceId, finalLat, finalLng
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
        if (isLiveListenerStarted) return;
        isLiveListenerStarted = true;
        emgController.subscribeToLiveRequests(list -> {
            Platform.runLater(() -> {
                liveEmergencyList.clear();
                liveEmergencyList.addAll(list);
                updateAutogeneratedIds();
                updateActiveFeedBadge();
                filterAndRenderFeed(feedSearchField != null ? feedSearchField.getText() : "");
            });
        });
    }

    private void manualFetchAllDispatches() {
        emergencyFeedCardsContainer.getChildren().clear();
        ShimmerPane shimmer = ShimmerLoader.createListSkeleton(4, 500, 100);
        emergencyFeedCardsContainer.getChildren().add(shimmer);

        new Thread(() -> {
            try { Thread.sleep(300); } catch (InterruptedException e) {}
            List<AdminSideEmgReqModel> list = emgController.getAllRequests();
            Platform.runLater(() -> {
                ShimmerLoader.transition(emergencyFeedCardsContainer, shimmer, null);
                liveEmergencyList.clear();
                liveEmergencyList.addAll(list);
                updateAutogeneratedIds();
                updateActiveFeedBadge();
                filterAndRenderFeed(feedSearchField != null ? feedSearchField.getText() : "");
            });
        }).start();
    }

    private void filterAndRenderFeed(String query) {
        emergencyFeedCardsContainer.getChildren().clear();
        String q = (query == null) ? "" : query.trim().toLowerCase();

        List<AdminSideEmgReqModel> filtered = new ArrayList<>();
        for (AdminSideEmgReqModel item : liveEmergencyList) {
            // Requirement 4: Requests with status COMPLETED vanish from live dispatches feed
            String status = item.getStatus();
            if (status != null && (status.equalsIgnoreCase("COMPLETED") || status.equalsIgnoreCase("COMPLETE"))) {
                continue;
            }

            boolean match = q.isEmpty()
                    || (item.getTripID() != null && item.getTripID().toLowerCase().contains(q))
                    || (item.getPatID() != null && item.getPatID().toLowerCase().contains(q))
                    || (item.getSource() != null && item.getSource().toLowerCase().contains(q))
                    || (item.getDestination() != null && item.getDestination().toLowerCase().contains(q))
                    || (item.getAmbulanceId() != null && item.getAmbulanceId().toLowerCase().contains(q))
                    || (item.getNurseID() != null && item.getNurseID().toLowerCase().contains(q))
                    || (item.getDriverID() != null && item.getDriverID().toLowerCase().contains(q));

            if (match) filtered.add(item);
        }

        if (filtered.isEmpty()) {
            VBox emptyBox = new VBox(6);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(30));

            Text emptyText = new Text(liveEmergencyList.isEmpty() 
                ? "No emergency requests found in Firestore." 
                : (q.isEmpty() ? "No active dispatches right now (all completed)." : "No active records match \"" + query + "\""));
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
        mapBtn.setOnAction(e -> openRouteMap(req.getSource(), req.getDestination(), req.getLatitude(), req.getLongitude()));

        Text tripText = new Text(req.getTripID() != null ? req.getTripID() : "UNASSIGNED");
        tripText.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");

        Text patientText = new Text("• Pat: " + (req.getPatID() != null ? req.getPatID() : "N/A"));
        patientText.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // 2. Status Changer Option with dynamic Green/Red background
        Label statusLabel = new Label("Status:");
        statusLabel.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        ComboBox<String> cardStatusBox = new ComboBox<>();
        cardStatusBox.getItems().addAll("PENDING", "ASSIGNED", "IN_PROGRESS", "COMPLETED", "CANCELLED");
        String currentStatus = req.getStatus() != null ? req.getStatus().toUpperCase().trim() : "PENDING";
        cardStatusBox.setValue(currentStatus);
        styleStatusBox(cardStatusBox, currentStatus);

        cardStatusBox.setCellFactory(lv -> new javafx.scene.control.ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    String itm = item.toUpperCase().trim();
                    if (itm.equals("COMPLETED") || itm.equals("ASSIGNED")) {
                        setStyle(FONT_STACK + "-fx-text-fill: #15803D; -fx-font-weight: bold; -fx-padding: 4 8;");
                    } else if (itm.equals("IN_PROGRESS")) {
                        setStyle(FONT_STACK + "-fx-text-fill: #B45309; -fx-font-weight: bold; -fx-padding: 4 8;");
                    } else {
                        setStyle(FONT_STACK + "-fx-text-fill: #DC2626; -fx-font-weight: bold; -fx-padding: 4 8;");
                    }
                }
            }
        });

        cardStatusBox.setOnAction(e -> {
            String newStatus = cardStatusBox.getValue();
            if (newStatus != null && !newStatus.equalsIgnoreCase(req.getStatus())) {
                req.setStatus(newStatus);
                styleStatusBox(cardStatusBox, newStatus);
                updateRequestStatus(req);
                // Requirement 4: When marked as COMPLETED, it vanishes immediately from live dispatches feed
                if (newStatus.equalsIgnoreCase("COMPLETED") || newStatus.equalsIgnoreCase("COMPLETE")) {
                    Platform.runLater(() -> {
                        updateActiveFeedBadge();
                        filterAndRenderFeed(feedSearchField != null ? feedSearchField.getText() : "");
                    });
                }
            }
        });

        topRow.getChildren().addAll(tripText, patientText, spacer, statusLabel, cardStatusBox);

        // Route Row
        HBox routeRow = new HBox(6);
        routeRow.setAlignment(Pos.CENTER_LEFT);
        Circle dot = new Circle(3, Color.web(PURPLE_DARK));

        String coordsInfo = (req.getLatitude() != null && req.getLongitude() != null && req.getLatitude() != 0.0 && req.getLongitude() != 0.0)
                ? " [📍 " + req.getLatitude() + ", " + req.getLongitude() + "]"
                : "";
        String destDisplay = (req.getDestination() != null ? req.getDestination() : "Hospital") + coordsInfo;
        String routeStr = (req.getSource() != null && !req.getSource().equalsIgnoreCase("Emergency Location") && !req.getSource().trim().isEmpty())
                ? req.getSource() + "  ➔  " + destDisplay
                : "➔ Destination: " + destDisplay;
        Text routeText = new Text(routeStr);
        routeText.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_SECONDARY + ";");
        routeRow.getChildren().addAll(dot, routeText);

        // Bottom Row: Staff & Timestamp
        HBox bottomRow = new HBox(12);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        String ambDisplay = (req.getAmbulanceId() != null && !req.getAmbulanceId().trim().isEmpty() && !req.getAmbulanceId().equalsIgnoreCase("Unassigned"))
                ? "  |  🚑 Amb: " + req.getAmbulanceId() + " (" + (req.getDriverID() != null ? req.getDriverID() : "None") + ")"
                : "  |  🚑 " + (req.getDriverID() != null ? req.getDriverID() : "None");

        Text staffInfo = new Text("👩‍⚕ " + (req.getNurseID() != null ? req.getNurseID() : "None") + ambDisplay + " | ");
        staffInfo.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + TEXT_MUTED + ";");

        Region bottomSpacer = new Region();
        HBox.setHgrow(bottomSpacer, Priority.ALWAYS);

        String formattedTimestamp = formatFirestoreTimestamp(req.getTimestamp());
        Text timeText = new Text(formattedTimestamp);
        timeText.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-fill: " + TEXT_MUTED + ";");

        bottomRow.getChildren().addAll(staffInfo, mapBtn, bottomSpacer, timeText);
        card.getChildren().addAll(topRow, routeRow, bottomRow);

        return card;
    }

    private void styleStatusBox(ComboBox<String> box, String status) {
        String s = (status != null) ? status.toUpperCase().trim() : "PENDING";
        String bgColor;
        String textColor;
        String borderColor;

        if (s.equals("COMPLETED") || s.equals("ASSIGNED")) {
            // Green background
            bgColor = "#DCFCE7";
            textColor = "#15803D";
            borderColor = "#86EFAC";
        } else if (s.equals("IN_PROGRESS")) {
            // Amber background
            bgColor = "#FEF3C7";
            textColor = "#B45309";
            borderColor = "#FCD34D";
        } else {
            // Red background for PENDING, CANCELLED, etc.
            bgColor = "#FEE2E2";
            textColor = "#DC2626";
            borderColor = "#FCA5A5";
        }

        final String finalTextColor = textColor;
        box.setStyle(FONT_STACK +
                "-fx-background-color: " + bgColor + ";" +
                "-fx-border-color: " + borderColor + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-background-radius: 8px;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;");

        box.setButtonCell(new javafx.scene.control.ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle(FONT_STACK + "-fx-text-fill: " + finalTextColor + "; -fx-font-weight: bold; -fx-font-size: 11px;");
                }
            }
        });
    }

    private void updateRequestStatus(AdminSideEmgReqModel req) {
        new Thread(() -> {
            emgController.createEmergencyRequest(req);
            Platform.runLater(() -> System.out.println("Trip #" + req.getTripID() + " status updated to " + req.getStatus()));
        }).start();
    }

    private void openRouteMap(String source, String destination) {
        openRouteMap(source, destination, null, null);
    }

    private void openRouteMap(String source, String destination, Double destLat, Double destLng) {
        try {
            String dest;
            if (destLat != null && destLng != null && destLat != 0.0 && destLng != 0.0) {
                dest = destLat + "," + destLng;
            } else if (destination != null && !destination.trim().isEmpty()) {
                dest = destination;
            } else {
                dest = "Hospital";
            }
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

    // Helper methods for sequential ID autogeneration starting at TRIP-100 and PAT-200
    private String generateNextTripId() {
        int maxId = 99; // Next starts at least 100
        for (AdminSideEmgReqModel req : liveEmergencyList) {
            if (req != null && req.getTripID() != null) {
                String id = req.getTripID().trim().toUpperCase();
                if (id.startsWith("TRIP-")) {
                    String numPart = id.substring(5).trim();
                    try {
                        int val = Integer.parseInt(numPart);
                        if (val > maxId) {
                            maxId = val;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
        return "TRIP-" + (maxId + 1);
    }

    private String generateNextPatientId() {
        int maxId = 199; // Next starts at least 200
        for (AdminSideEmgReqModel req : liveEmergencyList) {
            if (req != null && req.getPatID() != null) {
                String id = req.getPatID().trim().toUpperCase();
                if (id.startsWith("PAT-")) {
                    String numPart = id.substring(4).trim();
                    try {
                        int val = Integer.parseInt(numPart);
                        if (val > maxId) {
                            maxId = val;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
        return "PAT-" + (maxId + 1);
    }

    private void updateAutogeneratedIds() {
        if (tripIdField != null) {
            tripIdField.setText(generateNextTripId());
        }
        if (patIdField != null) {
            patIdField.setText(generateNextPatientId());
        }
    }

    private void updateActiveFeedBadge() {
        if (totalFeedBadge != null) {
            long activeCount = liveEmergencyList.stream()
                    .filter(r -> r.getStatus() == null || (!r.getStatus().equalsIgnoreCase("COMPLETED") && !r.getStatus().equalsIgnoreCase("COMPLETE")))
                    .count();
            totalFeedBadge.setText(activeCount + " Active Requests");
        }
    }

    private void resetDispatchForm() {
        if (tripIdField != null) tripIdField.setText(generateNextTripId());
        if (patIdField != null) patIdField.setText(generateNextPatientId());
        if (pickupLocationField != null) pickupLocationField.setText("Swargate");
        if (destinationField != null) destinationField.clear();
        if (destLatField != null) destLatField.clear();
        if (destLngField != null) destLngField.clear();
        if (ambulanceIdBox != null) ambulanceIdBox.getSelectionModel().clearSelection();
        if (nurseIdField != null) nurseIdField.clear();
        if (driverIdField != null) driverIdField.clear();
        if (timestampField != null) timestampField.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
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

    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
        System.exit(0);
    }
}