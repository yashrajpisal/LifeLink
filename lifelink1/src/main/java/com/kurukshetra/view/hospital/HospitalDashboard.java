package com.kurukshetra.view.hospital;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.kurukshetra.view.Welcome;


public class HospitalDashboard extends Application {

    public static Stage dashboardStage;
    private Scene dashboardScene;

    @Override
    public void start(Stage stage) throws Exception {

        dashboardStage = stage;

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #faf8ff;");

        // =============================================================
        // LEFT MENU
        // =============================================================

        VBox leftMenu = new VBox(8);
        leftMenu.setPadding(new Insets(25, 15, 20, 15));
        leftMenu.setPrefWidth(240);
        leftMenu.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-width: 0px 1px 0px 0px;");

        Text lifeLinkText = new Text("LifeLink");
        lifeLinkText.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        Text hospitalText = new Text("Hospital Management");
        hospitalText.setStyle("-fx-font-size: 13px; -fx-fill: #434655;");

        VBox profileBox = new VBox(5);
        profileBox.setPadding(new Insets(0, 10, 25, 10));
        profileBox.getChildren().addAll(lifeLinkText, hospitalText);

        Button dashboardButton = new Button("Dashboard");
        dashboardButton.setPrefWidth(220);
        dashboardButton.setPrefHeight(45);
        dashboardButton.setStyle("-fx-background-color: #dbe1ff; -fx-text-fill: #004ac6; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-border-color: #004ac6; -fx-border-width: 0px 2px 0px 0px;");

        Button emergencyButton = new Button("Emergency Requests");
        emergencyButton.setPrefWidth(220);
        emergencyButton.setPrefHeight(45);
        emergencyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 14px; -fx-background-radius: 10px;");

        Button resourceButton = new Button("Resource Management");
        resourceButton.setPrefWidth(220);
        resourceButton.setPrefHeight(45);
        resourceButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 14px; -fx-background-radius: 10px;");

        Button doctorButton = new Button("Doctor Management");
        doctorButton.setPrefWidth(220);
        doctorButton.setPrefHeight(45);
        doctorButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 14px; -fx-background-radius: 10px;");

        Button otButton = new Button("Operation Theatre");
        otButton.setPrefWidth(220);
        otButton.setPrefHeight(45);
        otButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 14px; -fx-background-radius: 10px;");

        Button patientButton = new Button("Patient Records");
        patientButton.setPrefWidth(220);
        patientButton.setPrefHeight(45);
        patientButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 14px; -fx-background-radius: 10px;");

        Button ambulanceButton = new Button("Ambulance Tracking");
        ambulanceButton.setPrefWidth(220);
        ambulanceButton.setPrefHeight(45);
        ambulanceButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 14px; -fx-background-radius: 10px;");

        Button notificationButton = new Button("Notifications");
        notificationButton.setPrefWidth(220);
        notificationButton.setPrefHeight(45);
        notificationButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 14px; -fx-background-radius: 10px;");

        Button analyticsButton = new Button("Analytics");
        analyticsButton.setPrefWidth(220);
        analyticsButton.setPrefHeight(45);
        analyticsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 14px; -fx-background-radius: 10px;");

        Button settingsButton = new Button("Settings");
        settingsButton.setPrefWidth(220);
        settingsButton.setPrefHeight(45);
        settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 14px; -fx-background-radius: 10px;");

        Region menuSpacer = new Region();
        VBox.setVgrow(menuSpacer, Priority.ALWAYS);

        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(220);
        logoutButton.setPrefHeight(45);
        logoutButton.setStyle("-fx-background-color: #fff1f2; -fx-text-fill: #dc2626; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 10px;");

        leftMenu.getChildren().addAll(profileBox, dashboardButton, emergencyButton, resourceButton, doctorButton, otButton, patientButton, ambulanceButton, notificationButton, analyticsButton, settingsButton, menuSpacer, logoutButton);

        borderPane.setLeft(leftMenu);

        // =============================================================
        // MAIN CONTENT
        // =============================================================

        VBox mainContent = new VBox(24);
        mainContent.setPadding(new Insets(25));
        mainContent.setStyle("-fx-background-color: #faf8ff;");

        Text pageTitle = new Text("Dashboard Overview");
        pageTitle.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text pageSubtitle = new Text("Real-time status of LifeLink Hospital resources and emergencies.");
        pageSubtitle.setStyle("-fx-font-size: 14px; -fx-fill: #434655;");

        VBox titleBox = new VBox(5);
        titleBox.getChildren().addAll(pageTitle, pageSubtitle);

        TextField searchField = new TextField();
        searchField.setPromptText("Search patient, doctor, or resource...");
        searchField.setPrefWidth(400);
        searchField.setPrefHeight(38);
        searchField.setStyle("-fx-background-color: #f3f3fe; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-padding: 0px 18px; -fx-font-size: 13px;");

        HBox pageButtons = new HBox(10);
        pageButtons.setAlignment(Pos.CENTER_RIGHT);
        pageButtons.getChildren().addAll(searchField);

        Region pageSpacer = new Region();
        HBox.setHgrow(pageSpacer, Priority.ALWAYS);

        HBox pageHeader = new HBox(10);
        pageHeader.getChildren().addAll(titleBox, pageSpacer, pageButtons);
        pageHeader.setAlignment(Pos.CENTER_LEFT);

        // =============================================================
        // SUMMARY CARDS
        // =============================================================

        HBox summaryCards = new HBox(16);

        VBox icuCard = new VBox(8);
        icuCard.setPadding(new Insets(16));
        icuCard.setPrefHeight(125);
        icuCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        HBox.setHgrow(icuCard, Priority.ALWAYS);

        Text icuIcon = new Text("▣");
        icuIcon.setStyle("-fx-font-size: 18px; -fx-fill: #004ac6;");

        Text icuCapacity = new Text("92% CAP");
        icuCapacity.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #16a34a;");

        HBox icuTop = new HBox(10);
        Region icuSpacer = new Region();
        HBox.setHgrow(icuSpacer, Priority.ALWAYS);
        icuTop.getChildren().addAll(icuIcon, icuSpacer, icuCapacity);

        Text icuValue = new Text("24");
        icuValue.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text icuLabel = new Text("Available ICU Beds");
        icuLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        icuCard.getChildren().addAll(icuTop, icuValue, icuLabel);

        VBox emergencyBedsCard = new VBox(8);
        emergencyBedsCard.setPadding(new Insets(16));
        emergencyBedsCard.setPrefHeight(125);
        emergencyBedsCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        HBox.setHgrow(emergencyBedsCard, Priority.ALWAYS);

        Text emergencyBedsIcon = new Text("!");
        emergencyBedsIcon.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

        Text emergencyLow = new Text("LOW");
        emergencyLow.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

        HBox emergencyBedsTop = new HBox(10);
        Region emergencyBedsSpacer = new Region();
        HBox.setHgrow(emergencyBedsSpacer, Priority.ALWAYS);
        emergencyBedsTop.getChildren().addAll(emergencyBedsIcon, emergencyBedsSpacer, emergencyLow);

        Text emergencyBedsValue = new Text("12");
        emergencyBedsValue.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text emergencyBedsLabel = new Text("Emergency Beds");
        emergencyBedsLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        emergencyBedsCard.getChildren().addAll(emergencyBedsTop, emergencyBedsValue, emergencyBedsLabel);

        VBox doctorsCard = new VBox(8);
        doctorsCard.setPadding(new Insets(16));
        doctorsCard.setPrefHeight(125);
        doctorsCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        HBox.setHgrow(doctorsCard, Priority.ALWAYS);

        Text doctorsIcon = new Text("✚");
        doctorsIcon.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        Text doctorsValue = new Text("48");
        doctorsValue.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text doctorsLabel = new Text("Doctors Active");
        doctorsLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        doctorsCard.getChildren().addAll(doctorsIcon, doctorsValue, doctorsLabel);

        VBox otCard = new VBox(8);
        otCard.setPadding(new Insets(16));
        otCard.setPrefHeight(125);
        otCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        HBox.setHgrow(otCard, Priority.ALWAYS);

        Text otIcon = new Text("OT");
        otIcon.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        Text otValue = new Text("03");
        otValue.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text otLabel = new Text("OTs Available");
        otLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        otCard.getChildren().addAll(otIcon, otValue, otLabel);

        VBox ambulanceCard = new VBox(8);
        ambulanceCard.setPadding(new Insets(16));
        ambulanceCard.setPrefHeight(125);
        ambulanceCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        HBox.setHgrow(ambulanceCard, Priority.ALWAYS);

        Text ambulanceIcon = new Text("A");
        ambulanceIcon.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        Text ambulanceValue = new Text("15");
        ambulanceValue.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text ambulanceLabel = new Text("Active Ambulances");
        ambulanceLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        ambulanceCard.getChildren().addAll(ambulanceIcon, ambulanceValue, ambulanceLabel);

        VBox incomingCard = new VBox(8);
        incomingCard.setPadding(new Insets(16));
        incomingCard.setPrefHeight(125);
        incomingCard.setStyle("-fx-background-color: #fff1f2; -fx-border-color: #fecdd3; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        HBox.setHgrow(incomingCard, Priority.ALWAYS);

        Text incomingIcon = new Text("!");
        incomingIcon.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

        Text incomingValue = new Text("08");
        incomingValue.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

        Text incomingLabel = new Text("Incoming Alerts");
        incomingLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

        incomingCard.getChildren().addAll(incomingIcon, incomingValue, incomingLabel);

        summaryCards.getChildren().addAll(icuCard, emergencyBedsCard, doctorsCard, otCard, ambulanceCard, incomingCard);

        // =============================================================
        // RESOURCE UTILIZATION
        // =============================================================

        HBox upperContent = new HBox(24);

        VBox utilizationCard = new VBox(18);
        utilizationCard.setPadding(new Insets(20));
        utilizationCard.setPrefHeight(400);
        utilizationCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        HBox.setHgrow(utilizationCard, Priority.ALWAYS);

        Text utilizationTitle = new Text("Resource Utilization Trends");
        utilizationTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #191b23;");

        ComboBox<String> periodCombo = new ComboBox<>();
        periodCombo.getItems().addAll("Last 24 Hours", "Last 7 Days");
        periodCombo.setValue("Last 24 Hours");
        periodCombo.setPrefHeight(32);

        HBox utilizationHeader = new HBox(10);
        Region utilizationSpacer = new Region();
        HBox.setHgrow(utilizationSpacer, Priority.ALWAYS);
        utilizationHeader.getChildren().addAll(utilizationTitle, utilizationSpacer, periodCombo);

        HBox chart = new HBox(15);
        chart.setAlignment(Pos.BOTTOM_CENTER);
        chart.setPrefHeight(260);

        Region chartBar1 = new Region();
        chartBar1.setPrefWidth(35);
        chartBar1.setPrefHeight(100);
        chartBar1.setStyle("-fx-background-color: #b4c5ff; -fx-background-radius: 6px 6px 0px 0px;");

        Region chartBar2 = new Region();
        chartBar2.setPrefWidth(35);
        chartBar2.setPrefHeight(145);
        chartBar2.setStyle("-fx-background-color: #8da8ed; -fx-background-radius: 6px 6px 0px 0px;");

        Region chartBar3 = new Region();
        chartBar3.setPrefWidth(35);
        chartBar3.setPrefHeight(120);
        chartBar3.setStyle("-fx-background-color: #7899e2; -fx-background-radius: 6px 6px 0px 0px;");

        Region chartBar4 = new Region();
        chartBar4.setPrefWidth(35);
        chartBar4.setPrefHeight(190);
        chartBar4.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 6px 6px 0px 0px;");

        Region chartBar5 = new Region();
        chartBar5.setPrefWidth(35);
        chartBar5.setPrefHeight(155);
        chartBar5.setStyle("-fx-background-color: #6689d8; -fx-background-radius: 6px 6px 0px 0px;");

        Region chartBar6 = new Region();
        chartBar6.setPrefWidth(35);
        chartBar6.setPrefHeight(215);
        chartBar6.setStyle("-fx-background-color: #004ac6; -fx-background-radius: 6px 6px 0px 0px;");

        Region chartBar7 = new Region();
        chartBar7.setPrefWidth(35);
        chartBar7.setPrefHeight(180);
        chartBar7.setStyle("-fx-background-color: #5278ce; -fx-background-radius: 6px 6px 0px 0px;");

        chart.getChildren().addAll(chartBar1, chartBar2, chartBar3, chartBar4, chartBar5, chartBar6, chartBar7);

        HBox chartLabels = new HBox(15);
        chartLabels.setAlignment(Pos.CENTER);

        Text time1 = new Text("00:00");
        Text time2 = new Text("04:00");
        Text time3 = new Text("08:00");
        Text time4 = new Text("12:00");
        Text time5 = new Text("16:00");
        Text time6 = new Text("20:00");
        Text time7 = new Text("Current");

        time1.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");
        time2.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");
        time3.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");
        time4.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");
        time5.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");
        time6.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");
        time7.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        chartLabels.getChildren().addAll(time1, time2, time3, time4, time5, time6, time7);

        utilizationCard.getChildren().addAll(utilizationHeader, chart, chartLabels);

        // =============================================================
        // LIVE EMERGENCY FEED
        // =============================================================

        VBox emergencyFeedCard = new VBox(12);
        emergencyFeedCard.setPadding(new Insets(20));
        emergencyFeedCard.setPrefWidth(420);
        emergencyFeedCard.setPrefHeight(400);
        emergencyFeedCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Text feedTitle = new Text("●  Live Emergency Feed");
        feedTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #191b23;");

        VBox feed1 = new VBox(5);
        feed1.setPadding(new Insets(10));
        feed1.setStyle("-fx-background-color: #fff1f2; -fx-border-color: #ba1a1a; -fx-border-width: 0px 0px 0px 4px; -fx-background-radius: 0px 8px 8px 0px;");

        Text feed1Title = new Text("Cardiac Arrest - Zone 4");
        feed1Title.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text feed1Time = new Text("2m ago");
        feed1Time.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        Text feed1Text = new Text("Ambulance #A12 arriving in 4 mins.");
        feed1Text.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        feed1.getChildren().addAll(feed1Title, feed1Time, feed1Text);

        VBox feed2 = new VBox(5);
        feed2.setPadding(new Insets(10));
        feed2.setStyle("-fx-background-color: #fff1f2; -fx-border-color: #ba1a1a; -fx-border-width: 0px 0px 0px 4px; -fx-background-radius: 0px 8px 8px 0px;");

        Text feed2Title = new Text("Trauma Case - Highway 102");
        feed2Title.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text feed2Time = new Text("5m ago");
        feed2Time.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        Text feed2Text = new Text("Multiple casualties. OT-02 reserved.");
        feed2Text.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        feed2.getChildren().addAll(feed2Title, feed2Time, feed2Text);

        VBox feed3 = new VBox(5);
        feed3.setPadding(new Insets(10));
        feed3.setStyle("-fx-background-color: #fff7ed; -fx-border-color: #f97316; -fx-border-width: 0px 0px 0px 4px; -fx-background-radius: 0px 8px 8px 0px;");

        Text feed3Title = new Text("Critical Lab Report");
        feed3Title.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text feed3Time = new Text("12m ago");
        feed3Time.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        Text feed3Text = new Text("Patient ID #8829 - Dr. Smith notified.");
        feed3Text.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        feed3.getChildren().addAll(feed3Title, feed3Time, feed3Text);

        VBox feed4 = new VBox(5);
        feed4.setPadding(new Insets(10));
        feed4.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 0px 4px; -fx-background-radius: 0px 8px 8px 0px;");

        Text feed4Title = new Text("ICU-04 Patient Transfer");
        feed4Title.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text feed4Time = new Text("22m ago");
        feed4Time.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        Text feed4Text = new Text("Completed. Bed available for sanitization.");
        feed4Text.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        feed4.getChildren().addAll(feed4Title, feed4Time, feed4Text);

        Button viewEmergencyButton = new Button("View All Emergency Requests");
        viewEmergencyButton.setMaxWidth(Double.MAX_VALUE);
        viewEmergencyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 12px; -fx-font-weight: bold;");

        emergencyFeedCard.getChildren().addAll(feedTitle, feed1, feed2, feed3, feed4, viewEmergencyButton);

        upperContent.getChildren().addAll(utilizationCard, emergencyFeedCard);

        // =============================================================
        // LOWER CONTENT
        // =============================================================

        HBox lowerContent = new HBox(24);

        VBox ambulanceTrackingCard = new VBox(18);
        ambulanceTrackingCard.setPadding(new Insets(20));
        ambulanceTrackingCard.setPrefWidth(500);
        ambulanceTrackingCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        HBox.setHgrow(ambulanceTrackingCard, Priority.ALWAYS);

        Text incomingTitle = new Text("Incoming Ambulances");
        incomingTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #191b23;");

        VBox ambulance1 = new VBox(6);
        ambulance1.setPadding(new Insets(8));

        Text ambulance1Name = new Text("AMB-104 (Cardiac)");
        ambulance1Name.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text ambulance1Status = new Text("On Route");
        ambulance1Status.setStyle("-fx-font-size: 11px; -fx-fill: #004ac6;");

        Text ambulance1Route = new Text("Central Station → North Wing");
        ambulance1Route.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        Text ambulance1Eta = new Text("ETA: 09:42");
        ambulance1Eta.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        Region ambulance1Background = new Region();
        ambulance1Background.setPrefHeight(7);
        ambulance1Background.setStyle("-fx-background-color: #e1e2ed; -fx-background-radius: 10px;");

        Region ambulance1Progress = new Region();
        ambulance1Progress.setPrefHeight(7);
        ambulance1Progress.setPrefWidth(300);
        ambulance1Progress.setStyle("-fx-background-color: #004ac6; -fx-background-radius: 10px;");

        StackPane ambulance1ProgressPane = new StackPane();
        ambulance1ProgressPane.setAlignment(Pos.CENTER_LEFT);
        ambulance1ProgressPane.getChildren().addAll(ambulance1Background, ambulance1Progress);

        HBox ambulance1Header = new HBox(10);
        Region ambulance1HeaderSpacer = new Region();
        HBox.setHgrow(ambulance1HeaderSpacer, Priority.ALWAYS);
        ambulance1Header.getChildren().addAll(ambulance1Name, ambulance1HeaderSpacer, ambulance1Status);

        HBox ambulance1Bottom = new HBox(10);
        Region ambulance1BottomSpacer = new Region();
        HBox.setHgrow(ambulance1BottomSpacer, Priority.ALWAYS);
        ambulance1Bottom.getChildren().addAll(ambulance1Route, ambulance1BottomSpacer, ambulance1Eta);

        ambulance1.getChildren().addAll(ambulance1Header, ambulance1ProgressPane, ambulance1Bottom);

        VBox ambulance2 = new VBox(6);
        ambulance2.setPadding(new Insets(8));

        Text ambulance2Name = new Text("AMB-022 (Trauma)");
        ambulance2Name.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text ambulance2Status = new Text("Slow Traffic");
        ambulance2Status.setStyle("-fx-font-size: 11px; -fx-fill: #f97316;");

        Text ambulance2Route = new Text("East Bypass → Emergency ER");
        ambulance2Route.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        Text ambulance2Eta = new Text("ETA: 09:47");
        ambulance2Eta.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #f97316;");

        Region ambulance2Background = new Region();
        ambulance2Background.setPrefHeight(7);
        ambulance2Background.setStyle("-fx-background-color: #e1e2ed; -fx-background-radius: 10px;");

        Region ambulance2Progress = new Region();
        ambulance2Progress.setPrefHeight(7);
        ambulance2Progress.setPrefWidth(160);
        ambulance2Progress.setStyle("-fx-background-color: #f97316; -fx-background-radius: 10px;");

        StackPane ambulance2ProgressPane = new StackPane();
        ambulance2ProgressPane.setAlignment(Pos.CENTER_LEFT);
        ambulance2ProgressPane.getChildren().addAll(ambulance2Background, ambulance2Progress);

        HBox ambulance2Header = new HBox(10);
        Region ambulance2HeaderSpacer = new Region();
        HBox.setHgrow(ambulance2HeaderSpacer, Priority.ALWAYS);
        ambulance2Header.getChildren().addAll(ambulance2Name, ambulance2HeaderSpacer, ambulance2Status);

        HBox ambulance2Bottom = new HBox(10);
        Region ambulance2BottomSpacer = new Region();
        HBox.setHgrow(ambulance2BottomSpacer, Priority.ALWAYS);
        ambulance2Bottom.getChildren().addAll(ambulance2Route, ambulance2BottomSpacer, ambulance2Eta);

        ambulance2.getChildren().addAll(ambulance2Header, ambulance2ProgressPane, ambulance2Bottom);

        VBox ambulance3 = new VBox(6);
        ambulance3.setPadding(new Insets(8));

        Text ambulance3Name = new Text("AMB-098 (General)");
        ambulance3Name.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text ambulance3Status = new Text("On Route");
        ambulance3Status.setStyle("-fx-font-size: 11px; -fx-fill: #004ac6;");

        Text ambulance3Route = new Text("Airport Terminal → LifeLink");
        ambulance3Route.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        Text ambulance3Eta = new Text("ETA: 09:50");
        ambulance3Eta.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        Region ambulance3Background = new Region();
        ambulance3Background.setPrefHeight(7);
        ambulance3Background.setStyle("-fx-background-color: #e1e2ed; -fx-background-radius: 10px;");

        Region ambulance3Progress = new Region();
        ambulance3Progress.setPrefHeight(7);
        ambulance3Progress.setPrefWidth(100);
        ambulance3Progress.setStyle("-fx-background-color: #004ac6; -fx-background-radius: 10px;");

        StackPane ambulance3ProgressPane = new StackPane();
        ambulance3ProgressPane.setAlignment(Pos.CENTER_LEFT);
        ambulance3ProgressPane.getChildren().addAll(ambulance3Background, ambulance3Progress);

        HBox ambulance3Header = new HBox(10);
        Region ambulance3HeaderSpacer = new Region();
        HBox.setHgrow(ambulance3HeaderSpacer, Priority.ALWAYS);
        ambulance3Header.getChildren().addAll(ambulance3Name, ambulance3HeaderSpacer, ambulance3Status);

        HBox ambulance3Bottom = new HBox(10);
        Region ambulance3BottomSpacer = new Region();
        HBox.setHgrow(ambulance3BottomSpacer, Priority.ALWAYS);
        ambulance3Bottom.getChildren().addAll(ambulance3Route, ambulance3BottomSpacer, ambulance3Eta);

        ambulance3.getChildren().addAll(ambulance3Header, ambulance3ProgressPane, ambulance3Bottom);

        ambulanceTrackingCard.getChildren().addAll(incomingTitle, ambulance1, ambulance2, ambulance3);

        // =============================================================
        // RESOURCE STOCK
        // =============================================================

        VBox resourceStockCard = new VBox(18);
        resourceStockCard.setPadding(new Insets(20));
        resourceStockCard.setPrefWidth(400);
        resourceStockCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        HBox.setHgrow(resourceStockCard, Priority.ALWAYS);

        Text stockTitle = new Text("Resource Stock Status");
        stockTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #191b23;");

        HBox stockCircles = new HBox(25);
        stockCircles.setAlignment(Pos.CENTER);

        StackPane oxygenCircle = new StackPane();
        oxygenCircle.setPrefWidth(110);
        oxygenCircle.setPrefHeight(110);
        oxygenCircle.setStyle("-fx-background-color: #dbe1ff; -fx-background-radius: 60px; -fx-border-color: #004ac6; -fx-border-width: 8px; -fx-border-radius: 60px;");

        VBox oxygenText = new VBox(0);
        oxygenText.setAlignment(Pos.CENTER);

        Text oxygenValue = new Text("75%");
        oxygenValue.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text oxygenLabel = new Text("O2 Supply");
        oxygenLabel.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        oxygenText.getChildren().addAll(oxygenValue, oxygenLabel);
        oxygenCircle.getChildren().add(oxygenText);

        StackPane bloodCircle = new StackPane();
        bloodCircle.setPrefWidth(110);
        bloodCircle.setPrefHeight(110);
        bloodCircle.setStyle("-fx-background-color: #fff1f2; -fx-background-radius: 60px; -fx-border-color: #ba1a1a; -fx-border-width: 8px; -fx-border-radius: 60px;");

        VBox bloodText = new VBox(0);
        bloodText.setAlignment(Pos.CENTER);

        Text bloodValue = new Text("30%");
        bloodValue.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

        Text bloodLabel = new Text("Blood Bank");
        bloodLabel.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        bloodText.getChildren().addAll(bloodValue, bloodLabel);
        bloodCircle.getChildren().add(bloodText);

        stockCircles.getChildren().addAll(oxygenCircle, bloodCircle);

        Text ppeLabel = new Text("Personal Protective Equipment (PPE)");
        ppeLabel.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        Text ppeValue = new Text("88%");
        ppeValue.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");

        HBox ppeHeader = new HBox(10);
        Region ppeHeaderSpacer = new Region();
        HBox.setHgrow(ppeHeaderSpacer, Priority.ALWAYS);
        ppeHeader.getChildren().addAll(ppeLabel, ppeHeaderSpacer, ppeValue);

        Region ppeBackground = new Region();
        ppeBackground.setPrefHeight(8);
        ppeBackground.setStyle("-fx-background-color: #e1e2ed; -fx-background-radius: 10px;");

        Region ppeProgress = new Region();
        ppeProgress.setPrefHeight(8);
        ppeProgress.setPrefWidth(300);
        ppeProgress.setStyle("-fx-background-color: #16a34a; -fx-background-radius: 10px;");

        StackPane ppePane = new StackPane();
        ppePane.setAlignment(Pos.CENTER_LEFT);
        ppePane.getChildren().addAll(ppeBackground, ppeProgress);

        Text surgicalLabel = new Text("Surgical Kits Availability");
        surgicalLabel.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        Text surgicalValue = new Text("62%");
        surgicalValue.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");

        HBox surgicalHeader = new HBox(10);
        Region surgicalHeaderSpacer = new Region();
        HBox.setHgrow(surgicalHeaderSpacer, Priority.ALWAYS);
        surgicalHeader.getChildren().addAll(surgicalLabel, surgicalHeaderSpacer, surgicalValue);

        Region surgicalBackground = new Region();
        surgicalBackground.setPrefHeight(8);
        surgicalBackground.setStyle("-fx-background-color: #e1e2ed; -fx-background-radius: 10px;");

        Region surgicalProgress = new Region();
        surgicalProgress.setPrefHeight(8);
        surgicalProgress.setPrefWidth(210);
        surgicalProgress.setStyle("-fx-background-color: #004ac6; -fx-background-radius: 10px;");

        StackPane surgicalPane = new StackPane();
        surgicalPane.setAlignment(Pos.CENTER_LEFT);
        surgicalPane.getChildren().addAll(surgicalBackground, surgicalProgress);

        resourceStockCard.getChildren().addAll(stockTitle, stockCircles, ppeHeader, ppePane, surgicalHeader, surgicalPane);

        // =============================================================
        // PATIENT VOLUME + QUICK ACTIONS
        // =============================================================

        VBox rightLowerContent = new VBox(18);
        rightLowerContent.setPrefWidth(300);

        VBox patientVolumeCard = new VBox(12);
        patientVolumeCard.setPadding(new Insets(18));
        patientVolumeCard.setPrefHeight(180);
        patientVolumeCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Text patientVolumeTitle = new Text("Today's Patient Vol.");
        patientVolumeTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #191b23;");

        HBox patientBars = new HBox(8);
        patientBars.setAlignment(Pos.BOTTOM_CENTER);
        patientBars.setPrefHeight(80);

        Region patientBar1 = new Region();
        patientBar1.setPrefWidth(22);
        patientBar1.setPrefHeight(45);
        patientBar1.setStyle("-fx-background-color: #b4c5ff; -fx-background-radius: 4px 4px 0px 0px;");

        Region patientBar2 = new Region();
        patientBar2.setPrefWidth(22);
        patientBar2.setPrefHeight(30);
        patientBar2.setStyle("-fx-background-color: #b4c5ff; -fx-background-radius: 4px 4px 0px 0px;");

        Region patientBar3 = new Region();
        patientBar3.setPrefWidth(22);
        patientBar3.setPrefHeight(58);
        patientBar3.setStyle("-fx-background-color: #b4c5ff; -fx-background-radius: 4px 4px 0px 0px;");

        Region patientBar4 = new Region();
        patientBar4.setPrefWidth(22);
        patientBar4.setPrefHeight(70);
        patientBar4.setStyle("-fx-background-color: #8da8ed; -fx-background-radius: 4px 4px 0px 0px;");

        Region patientBar5 = new Region();
        patientBar5.setPrefWidth(22);
        patientBar5.setPrefHeight(80);
        patientBar5.setStyle("-fx-background-color: #004ac6; -fx-background-radius: 4px 4px 0px 0px;");

        Region patientBar6 = new Region();
        patientBar6.setPrefWidth(22);
        patientBar6.setPrefHeight(68);
        patientBar6.setStyle("-fx-background-color: #b4c5ff; -fx-background-radius: 4px 4px 0px 0px;");

        Region patientBar7 = new Region();
        patientBar7.setPrefWidth(22);
        patientBar7.setPrefHeight(55);
        patientBar7.setStyle("-fx-background-color: #b4c5ff; -fx-background-radius: 4px 4px 0px 0px;");

        patientBars.getChildren().addAll(patientBar1, patientBar2, patientBar3, patientBar4, patientBar5, patientBar6, patientBar7);

        HBox patientDays = new HBox(12);
        patientDays.setAlignment(Pos.CENTER);

        Text mon = new Text("MON");
        Text tue = new Text("TUE");
        Text wed = new Text("WED");
        Text thu = new Text("THU");
        Text fri = new Text("FRI");
        Text sat = new Text("SAT");
        Text sun = new Text("SUN");

        mon.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-fill: #737686;");
        tue.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-fill: #737686;");
        wed.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-fill: #737686;");
        thu.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-fill: #737686;");
        fri.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-fill: #737686;");
        sat.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-fill: #737686;");
        sun.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-fill: #737686;");

        patientDays.getChildren().addAll(mon, tue, wed, thu, fri, sat, sun);

        patientVolumeCard.getChildren().addAll(patientVolumeTitle, patientBars, patientDays);

        HBox quickActions1 = new HBox(8);

        Button assignDoctorButton = new Button("Assign Doctor");
        assignDoctorButton.setPrefWidth(140);
        assignDoctorButton.setPrefHeight(55);
        assignDoctorButton.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Button reserveIcuButton = new Button("Reserve ICU");
        reserveIcuButton.setPrefWidth(140);
        reserveIcuButton.setPrefHeight(55);
        reserveIcuButton.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        quickActions1.getChildren().addAll(assignDoctorButton, reserveIcuButton);

        HBox quickActions2 = new HBox(8);

        Button labRequestButton = new Button("Lab Request");
        labRequestButton.setPrefWidth(140);
        labRequestButton.setPrefHeight(55);
        labRequestButton.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Button printLogsButton = new Button("Print Logs");
        printLogsButton.setPrefWidth(140);
        printLogsButton.setPrefHeight(55);
        printLogsButton.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        quickActions2.getChildren().addAll(labRequestButton, printLogsButton);

        rightLowerContent.getChildren().addAll(patientVolumeCard, quickActions1, quickActions2);

        lowerContent.getChildren().addAll(ambulanceTrackingCard, resourceStockCard, rightLowerContent);

        // =============================================================
        // ADD MAIN CONTENT
        // =============================================================

        mainContent.getChildren().addAll(pageHeader, summaryCards, upperContent, lowerContent);

        // =============================================================
        // SCROLL PANE
        // =============================================================

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        VBox finalContent = new VBox(scrollPane);
        finalContent.setStyle("-fx-background-color: #faf8ff;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        borderPane.setCenter(finalContent);

        // =============================================================
        // NAVIGATION
        // =============================================================

        dashboardButton.setOnAction(event -> {

     
                dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                resourceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                doctorButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                otButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                patientButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                notificationButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

        // Active button
                dashboardButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

        // Show dashboard
                borderPane.setCenter(finalContent);
        });


        emergencyButton.setOnAction(event -> {

        // Reset all buttons
                dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                resourceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                doctorButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                otButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                patientButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                notificationButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

        // Active button
                emergencyButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

        // Show Emergency Requests page
                HospitalEmergencyRequests emergencyRequests = new HospitalEmergencyRequests();
                borderPane.setCenter(emergencyRequests.getEmergencyRequests());
        });


        resourceButton.setOnAction(event -> {

        // Reset all buttons
                dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                resourceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                doctorButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                otButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                patientButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                notificationButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

                // Active button
                resourceButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

                HospitalResourceManagement resourceManagement = new HospitalResourceManagement();
                borderPane.setCenter(resourceManagement.getResourceManagement());
        });


        doctorButton.setOnAction(event -> {

                // Reset all buttons
                dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                resourceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                doctorButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                otButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                patientButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                notificationButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

                // Active button
                doctorButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

                // Add your Doctor Management page here
                HospitalDoctorManagement doctorManagement = new HospitalDoctorManagement();
                borderPane.setCenter(doctorManagement.getDoctorManagement());
        });


        otButton.setOnAction(event -> {

                // Reset all buttons
                dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                resourceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                doctorButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                otButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                patientButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                notificationButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

                // Active button
                otButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

                 
                HospitalOperationTheatre operationTheatre = new HospitalOperationTheatre();
                borderPane.setCenter(operationTheatre.getOperationTheatre());
        });


        patientButton.setOnAction(event -> {

                // Reset all buttons
                dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                resourceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                doctorButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                otButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                patientButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                notificationButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

                // Active button
                patientButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

                 
                HospitalPatientRecords patientRecords = new HospitalPatientRecords();
                borderPane.setCenter(patientRecords.getPatientRecords());
        });


        ambulanceButton.setOnAction(event -> {

                // Reset all buttons
                dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                resourceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                doctorButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                otButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                patientButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                notificationButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

                // Active button
                ambulanceButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

                 
                HospitalAmbulanceTracking ambulanceTracking = new HospitalAmbulanceTracking();
                borderPane.setCenter(ambulanceTracking.getAmbulanceTracking());
        });


        notificationButton.setOnAction(event -> {

                // Reset all buttons
                dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                resourceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                doctorButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                otButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                patientButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                notificationButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

                // Active button
                notificationButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

                HospitalNotification notification = new HospitalNotification();
                borderPane.setCenter(notification.getNotification());
        });


        analyticsButton.setOnAction(event -> {

                // Reset all buttons
                dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                resourceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                doctorButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                otButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                patientButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                notificationButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

                // Active button
                analyticsButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

                HospitalAnalytics analytics = new HospitalAnalytics();
                borderPane.setCenter(analytics.getAnalytics());
        });


        settingsButton.setOnAction(event -> {

                // Reset all buttons
                dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                resourceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                doctorButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                otButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                patientButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                notificationButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

                // Active button
                settingsButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

                HospitalSettings settings = new HospitalSettings();
                borderPane.setCenter(settings.getSettings());
        });


        logoutButton.setOnAction(event -> {
                 // Add your SignIn navigation logic here.
        });

        // =============================================================
        // SCENE
        // =============================================================
        
        dashboardScene = new Scene(borderPane);

        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("LifeLink Hospital Dashboard");
        dashboardStage.setMaximized(true);
        dashboardStage.show();
    }
}

