
package com.kurukshetra.view.admin;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminDashboard extends Application {

    public static Stage dashboardStage;
    private Scene dashboardScene;

    @Override
    public void start(Stage stage) throws Exception {

        dashboardStage = stage;

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #f8f8ff;");

        VBox leftMenu = new VBox(12);
        leftMenu.setPadding(new Insets(25, 15, 20, 15));
        leftMenu.setPrefWidth(250);
        leftMenu.setStyle("-fx-background-color: #ffffff;" + "-fx-border-color: #d8dce5;" + "-fx-border-width: 0px 1px 0px 0px;");

        Text lifeLinkText = new Text("LifeLink");
        lifeLinkText.setStyle("-fx-font-size: 25px;" + "-fx-font-weight: bold;" + "-fx-fill: #004ac6;");

        Text adminText = new Text("Admin Dashboard");
        adminText.setStyle("-fx-font-size: 13px;" + "-fx-fill: #6b7280;");

        VBox profileBox = new VBox(5);
        profileBox.getChildren().addAll(
                lifeLinkText,
                adminText
        );
        profileBox.setPadding(new Insets(0, 10, 20, 10));

        Button dashboardButton = new Button("Dashboard");
        dashboardButton.setPrefWidth(220);
        dashboardButton.setPrefHeight(45);
        dashboardButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

        Button hospitalButton = new Button("Hospital Management");
        hospitalButton.setPrefWidth(220);
        hospitalButton.setPrefHeight(45);
        hospitalButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

        Button ambulanceButton = new Button("Ambulance Management");
        ambulanceButton.setPrefWidth(220);
        ambulanceButton.setPrefHeight(45);
        ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

        Button userButton = new Button("User Management");
        userButton.setPrefWidth(220);
        userButton.setPrefHeight(45);
        userButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

        Button policeButton = new Button("Police Management");
        policeButton.setPrefWidth(220);
        policeButton.setPrefHeight(45);
        policeButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

        Button emergencyButton = new Button("Emergency Monitoring");
        emergencyButton.setPrefWidth(220);
        emergencyButton.setPrefHeight(45);
        emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

        Button analyticsButton = new Button("Analytics & Reports");
        analyticsButton.setPrefWidth(220);
        analyticsButton.setPrefHeight(45);
        analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

        Text systemText = new Text("SYSTEM");
        systemText.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #737686;");
        systemText.setTranslateX(10);

        Button activityButton = new Button("Activity Logs");
        activityButton.setPrefWidth(220);
        activityButton.setPrefHeight(45);
        activityButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

        Button settingsButton = new Button("Settings");
        settingsButton.setPrefWidth(220);
        settingsButton.setPrefHeight(45);
        settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(220);
        logoutButton.setPrefHeight(45);
        logoutButton.setStyle("-fx-background-color: #fff1f2;" + "-fx-text-fill: #dc2626;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

        leftMenu.getChildren().addAll(
                profileBox,
                dashboardButton,
                hospitalButton,
                ambulanceButton,
                userButton,
                policeButton,
                emergencyButton,
                analyticsButton,
                systemText,
                activityButton,
                settingsButton,
                spacer,
                logoutButton
        );

        borderPane.setLeft(leftMenu);



        VBox rightContent = new VBox(20);
        rightContent.setPadding(new Insets(25));
        rightContent.setStyle("-fx-background-color: #f8f8ff;");

        Text heading = new Text("Admin Dashboard");
        heading.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text subHeading = new Text("System status overview and emergency management");
        subHeading.setStyle("-fx-font-size: 14px;" + "-fx-fill: #6b7280;");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(
                heading,
                subHeading
        );

        Button exportButton = new Button("Export PDF");
        exportButton.setPrefWidth(120);
        exportButton.setPrefHeight(40);
        exportButton.setStyle("-fx-background-color: #ffffff;" + "-fx-text-fill: #374151;" + "-fx-font-size: 13px;" + "-fx-border-color: #d1d5db;" + "-fx-border-radius: 8px;" + "-fx-background-radius: 8px;");

        Button emergencyNewButton = new Button("New Emergency");
        emergencyNewButton.setPrefWidth(135);
        emergencyNewButton.setPrefHeight(40);
        emergencyNewButton.setStyle("-fx-background-color: #004ac6;" + "-fx-text-fill: white;" + "-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 8px;");

        HBox headerButtons = new HBox(10);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);
        headerButtons.getChildren().addAll(
                exportButton,
                emergencyNewButton
        );

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox header = new HBox(
                headingBox,
                headerSpacer,
                headerButtons
        );
        header.setAlignment(Pos.CENTER_LEFT);

        HBox cardsRow1 = new HBox(15);

        VBox hospitalCard = new VBox(7);
        hospitalCard.setPadding(new Insets(18));
        hospitalCard.setPrefHeight(130);
        hospitalCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text hospitalTitle = new Text("Total Hospitals");
        hospitalTitle.setStyle("-fx-font-size: 13px;" + "-fx-fill: #6b7280;" + "-fx-font-weight: bold;");

        Text hospitalValue = new Text("124");
        hospitalValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text hospitalInfo = new Text("+2 this month");
        hospitalInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #16a34a;");

        hospitalCard.getChildren().addAll(
                hospitalTitle,
                hospitalValue,
                hospitalInfo
        );
        HBox.setHgrow(hospitalCard, Priority.ALWAYS);

        VBox ambulanceCard = new VBox(7);
        ambulanceCard.setPadding(new Insets(18));
        ambulanceCard.setPrefHeight(130);
        ambulanceCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text ambulanceTitle = new Text("Active Ambulances");
        ambulanceTitle.setStyle("-fx-font-size: 13px;" + "-fx-fill: #6b7280;" + "-fx-font-weight: bold;");

        Text ambulanceValue = new Text("86");
        ambulanceValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text ambulanceInfo = new Text("12 in maintenance");
        ambulanceInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        ambulanceCard.getChildren().addAll(
                ambulanceTitle,
                ambulanceValue,
                ambulanceInfo
        );
        HBox.setHgrow(ambulanceCard, Priority.ALWAYS);

        VBox patientCard = new VBox(7);
        patientCard.setPadding(new Insets(18));
        patientCard.setPrefHeight(130);
        patientCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text patientTitle = new Text("Registered Patients");
        patientTitle.setStyle("-fx-font-size: 13px;" + "-fx-fill: #6b7280;" + "-fx-font-weight: bold;");

        Text patientValue = new Text("42.8k");
        patientValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text patientInfo = new Text("Active database");
        patientInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        patientCard.getChildren().addAll(
                patientTitle,
                patientValue,
                patientInfo
        );
        HBox.setHgrow(patientCard, Priority.ALWAYS);

        VBox doctorCard = new VBox(7);
        doctorCard.setPadding(new Insets(18));
        doctorCard.setPrefHeight(130);
        doctorCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text doctorTitle = new Text("Registered Doctors");
        doctorTitle.setStyle("-fx-font-size: 13px;" + "-fx-fill: #6b7280;" + "-fx-font-weight: bold;");

        Text doctorValue = new Text("3,412");
        doctorValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text doctorInfo = new Text("98 currently on shift");
        doctorInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        doctorCard.getChildren().addAll(
                doctorTitle,
                doctorValue,
                doctorInfo
        );
        HBox.setHgrow(doctorCard, Priority.ALWAYS);

        cardsRow1.getChildren().addAll(
                hospitalCard,
                ambulanceCard,
                patientCard,
                doctorCard
        );

        HBox cardsRow2 = new HBox(15);

        VBox emergencyCard = new VBox(7);
        emergencyCard.setPadding(new Insets(18));
        emergencyCard.setPrefHeight(130);
        emergencyCard.setStyle("-fx-background-color: #fff1f2;" + "-fx-background-radius: 12px;" + "-fx-border-color: #fecdd3;" + "-fx-border-radius: 12px;");

        Text emergencyTitle = new Text("Active Emergencies");
        emergencyTitle.setStyle("-fx-font-size: 13px;" + "-fx-fill: #dc2626;" + "-fx-font-weight: bold;");

        Text emergencyValue = new Text("14");
        emergencyValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #dc2626;");

        Text emergencyInfo = new Text("4 Critical Priority");
        emergencyInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #dc2626;");

        emergencyCard.getChildren().addAll(
                emergencyTitle,
                emergencyValue,
                emergencyInfo
        );
        HBox.setHgrow(emergencyCard, Priority.ALWAYS);

        VBox icuCard = new VBox(7);
        icuCard.setPadding(new Insets(18));
        icuCard.setPrefHeight(130);
        icuCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text icuTitle = new Text("Available ICU Beds");
        icuTitle.setStyle("-fx-font-size: 13px;" + "-fx-fill: #6b7280;" + "-fx-font-weight: bold;");

        Text icuValue = new Text("42");
        icuValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text icuInfo = new Text("Capacity monitoring");
        icuInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        icuCard.getChildren().addAll(
                icuTitle,
                icuValue,
                icuInfo
        );
        HBox.setHgrow(icuCard, Priority.ALWAYS);

        VBox requestCard = new VBox(7);
        requestCard.setPadding(new Insets(18));
        requestCard.setPrefHeight(130);
        requestCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text requestTitle = new Text("Emergency Requests Today");
        requestTitle.setStyle("-fx-font-size: 13px;" + "-fx-fill: #6b7280;" + "-fx-font-weight: bold;");

        Text requestValue = new Text("312");
        requestValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text requestInfo = new Text("+12% vs avg");
        requestInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #16a34a;");

        requestCard.getChildren().addAll(
                requestTitle,
                requestValue,
                requestInfo
        );
        HBox.setHgrow(requestCard, Priority.ALWAYS);

        VBox responseCard = new VBox(7);
        responseCard.setPadding(new Insets(18));
        responseCard.setPrefHeight(130);
        responseCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text responseTitle = new Text("Avg Response Time");
        responseTitle.setStyle("-fx-font-size: 13px;" + "-fx-fill: #6b7280;" + "-fx-font-weight: bold;");

        Text responseValue = new Text("8.2m");
        responseValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text responseInfo = new Text("Target: <10 mins");
        responseInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #16a34a;");

        responseCard.getChildren().addAll(
                responseTitle,
                responseValue,
                responseInfo
        );
        HBox.setHgrow(responseCard, Priority.ALWAYS);

        cardsRow2.getChildren().addAll(
                emergencyCard,
                icuCard,
                requestCard,
                responseCard
        );

        HBox mainContent = new HBox(20);

        VBox leftContent = new VBox(20);
        HBox.setHgrow(leftContent, Priority.ALWAYS);

        VBox chartCard = new VBox(15);
        chartCard.setPadding(new Insets(20));
        chartCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 15px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 15px;");

        Text chartTitle = new Text("Emergency Cases per Day");
        chartTitle.setStyle("-fx-font-size: 19px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        HBox chart = new HBox(15);
        chart.setAlignment(Pos.BOTTOM_CENTER);
        chart.setPrefHeight(220);

        Region bar1 = new Region();
        bar1.setPrefWidth(35);
        bar1.setPrefHeight(80);
        bar1.setStyle("-fx-background-color: #8fa8e8;" + "-fx-background-radius: 6px 6px 0px 0px;");

        Region bar2 = new Region();
        bar2.setPrefWidth(35);
        bar2.setPrefHeight(120);
        bar2.setStyle("-fx-background-color: #6689d8;" + "-fx-background-radius: 6px 6px 0px 0px;");

        Region bar3 = new Region();
        bar3.setPrefWidth(35);
        bar3.setPrefHeight(95);
        bar3.setStyle("-fx-background-color: #8fa8e8;" + "-fx-background-radius: 6px 6px 0px 0px;");

        Region bar4 = new Region();
        bar4.setPrefWidth(35);
        bar4.setPrefHeight(145);
        bar4.setStyle("-fx-background-color: #5278ce;" + "-fx-background-radius: 6px 6px 0px 0px;");

        Region bar5 = new Region();
        bar5.setPrefWidth(35);
        bar5.setPrefHeight(110);
        bar5.setStyle("-fx-background-color: #7895dc;" + "-fx-background-radius: 6px 6px 0px 0px;");

        Region bar6 = new Region();
        bar6.setPrefWidth(35);
        bar6.setPrefHeight(170);
        bar6.setStyle("-fx-background-color: #4169c1;" + "-fx-background-radius: 6px 6px 0px 0px;");

        Region bar7 = new Region();
        bar7.setPrefWidth(35);
        bar7.setPrefHeight(130);
        bar7.setStyle("-fx-background-color: #6689d8;" + "-fx-background-radius: 6px 6px 0px 0px;");

        chart.getChildren().addAll(
                bar1,
                bar2,
                bar3,
                bar4,
                bar5,
                bar6,
                bar7
        );

        HBox days = new HBox(15);

        String[] dayNames = {
                "Mon",
                "Tue",
                "Wed",
                "Thu",
                "Fri",
                "Sat",
                "Sun"
        };

        for (String dayName : dayNames) {

            Text day = new Text(dayName);
            day.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

            HBox dayBox = new HBox(day);
            dayBox.setAlignment(Pos.CENTER);
            dayBox.setPrefWidth(35);

            days.getChildren().add(dayBox);
        }

        chartCard.getChildren().addAll(
                chartTitle,
                chart,
                days
        );

        VBox resourceCard = new VBox(15);
        resourceCard.setPadding(new Insets(20));
        resourceCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 15px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 15px;");

        Text resourceTitle = new Text("Hospital Resource Usage");
        resourceTitle.setStyle("-fx-font-size: 19px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text hospital1Name = new Text("St. Mary's General");
        hospital1Name.setStyle("-fx-font-size: 13px;" + "-fx-fill: #374151;");

        Text hospital1Percentage = new Text("88%");
        hospital1Percentage.setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        Region hospital1Spacer = new Region();
        HBox.setHgrow(hospital1Spacer, Priority.ALWAYS);

        HBox hospital1Header = new HBox(
                hospital1Name,
                hospital1Spacer,
                hospital1Percentage
        );

        Region hospital1Background = new Region();
        hospital1Background.setPrefHeight(8);
        hospital1Background.setStyle("-fx-background-color: #e5e7eb;" + "-fx-background-radius: 10px;");

        Region hospital1Progress = new Region();
        hospital1Progress.setPrefHeight(8);
        hospital1Progress.setPrefWidth(400);
        hospital1Progress.setStyle("-fx-background-color: #004ac6;" + "-fx-background-radius: 10px;");

        StackPane hospital1ProgressPane = new StackPane();
        hospital1ProgressPane.setAlignment(Pos.CENTER_LEFT);
        hospital1ProgressPane.getChildren().addAll(
                hospital1Background,
                hospital1Progress
        );

        Text hospital2Name = new Text("City Central Medical");
        hospital2Name.setStyle("-fx-font-size: 13px;" + "-fx-fill: #374151;");

        Text hospital2Percentage = new Text("64%");
        hospital2Percentage.setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        Region hospital2Spacer = new Region();
        HBox.setHgrow(hospital2Spacer, Priority.ALWAYS);

        HBox hospital2Header = new HBox(
                hospital2Name,
                hospital2Spacer,
                hospital2Percentage
        );

        Region hospital2Background = new Region();
        hospital2Background.setPrefHeight(8);
        hospital2Background.setStyle("-fx-background-color: #e5e7eb;" + "-fx-background-radius: 10px;");

        Region hospital2Progress = new Region();
        hospital2Progress.setPrefHeight(8);
        hospital2Progress.setPrefWidth(290);
        hospital2Progress.setStyle("-fx-background-color: #004ac6;" + "-fx-background-radius: 10px;");

        StackPane hospital2ProgressPane = new StackPane();
        hospital2ProgressPane.setAlignment(Pos.CENTER_LEFT);
        hospital2ProgressPane.getChildren().addAll(
                hospital2Background,
                hospital2Progress
        );

        Text hospital3Name = new Text("Regional Children's");
        hospital3Name.setStyle("-fx-font-size: 13px;" + "-fx-fill: #374151;");

        Text hospital3Percentage = new Text("42%");
        hospital3Percentage.setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        Region hospital3Spacer = new Region();
        HBox.setHgrow(hospital3Spacer, Priority.ALWAYS);

        HBox hospital3Header = new HBox(
                hospital3Name,
                hospital3Spacer,
                hospital3Percentage
        );

        Region hospital3Background = new Region();
        hospital3Background.setPrefHeight(8);
        hospital3Background.setStyle("-fx-background-color: #e5e7eb;" + "-fx-background-radius: 10px;");

        Region hospital3Progress = new Region();
        hospital3Progress.setPrefHeight(8);
        hospital3Progress.setPrefWidth(190);
        hospital3Progress.setStyle("-fx-background-color: #004ac6;" + "-fx-background-radius: 10px;");

        StackPane hospital3ProgressPane = new StackPane();
        hospital3ProgressPane.setAlignment(Pos.CENTER_LEFT);
        hospital3ProgressPane.getChildren().addAll(
                hospital3Background,
                hospital3Progress
        );

        resourceCard.getChildren().addAll(
                resourceTitle,
                hospital1Header,
                hospital1ProgressPane,
                hospital2Header,
                hospital2ProgressPane,
                hospital3Header,
                hospital3ProgressPane
        );

        leftContent.getChildren().addAll(
                chartCard,
                resourceCard
        );

        VBox rightPart = new VBox(20);
        rightPart.setPrefWidth(380);

        VBox liveCard = new VBox();
        liveCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 15px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 15px;");

        Text liveTitle = new Text("●  Live Emergency Feed");
        liveTitle.setStyle("-fx-font-size: 18px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        HBox liveHeader = new HBox(liveTitle);
        liveHeader.setPadding(new Insets(18));

        VBox feed1 = new VBox(6);
        feed1.setPadding(new Insets(15));
        feed1.setStyle("-fx-border-color: #e5e7eb;" + "-fx-border-width: 0px 0px 1px 0px;");

        Text feed1Priority = new Text("CRITICAL");
        feed1Priority.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-fill: #dc2626;");

        Text feed1Title = new Text("Cardiac Arrest - Zone 4");
        feed1Title.setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text feed1Text = new Text("Ambulance #A-204 dispatched. ETA 4 mins.");
        feed1Text.setWrappingWidth(320);
        feed1Text.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        feed1.getChildren().addAll(
                feed1Priority,
                feed1Title,
                feed1Text
        );

        VBox feed2 = new VBox(6);
        feed2.setPadding(new Insets(15));
        feed2.setStyle("-fx-border-color: #e5e7eb;" + "-fx-border-width: 0px 0px 1px 0px;");

        Text feed2Priority = new Text("MODERATE");
        feed2Priority.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-fill: #d97706;");

        Text feed2Title = new Text("Traffic Incident - Zone 2");
        feed2Title.setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text feed2Text = new Text("2 vehicles involved. Dispatching support unit.");
        feed2Text.setWrappingWidth(320);
        feed2Text.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        feed2.getChildren().addAll(
                feed2Priority,
                feed2Title,
                feed2Text
        );

        VBox feed3 = new VBox(6);
        feed3.setPadding(new Insets(15));

        Text feed3Priority = new Text("CRITICAL");
        feed3Priority.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-fill: #dc2626;");

        Text feed3Title = new Text("Hospital Bed Shortage");
        feed3Title.setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text feed3Text = new Text("City Central reaching capacity.");
        feed3Text.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        feed3.getChildren().addAll(
                feed3Priority,
                feed3Title,
                feed3Text
        );

        Button viewAllButton = new Button("View All Activities");
        viewAllButton.setMaxWidth(Double.MAX_VALUE);
        viewAllButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #004ac6;" + "-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-padding: 12px;");

        liveCard.getChildren().addAll(
                liveHeader,
                feed1,
                feed2,
                feed3,
                viewAllButton
        );

        VBox healthCard = new VBox(15);
        healthCard.setPadding(new Insets(20));
        healthCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 15px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 15px;");

        Text healthTitle = new Text("System Health");
        healthTitle.setStyle("-fx-font-size: 19px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text database = new Text("●  Database        Online");
        database.setStyle("-fx-font-size: 12px;" + "-fx-fill: #16a34a;");

        Text dispatch = new Text("●  Dispatch API    Active");
        dispatch.setStyle("-fx-font-size: 12px;" + "-fx-fill: #16a34a;");

        Text gps = new Text("●  GPS Tracking    Syncing");
        gps.setStyle("-fx-font-size: 12px;" + "-fx-fill: #2563eb;");

        Text latency = new Text("●  Server Latency  14ms");
        latency.setStyle("-fx-font-size: 12px;" + "-fx-fill: #16a34a;");

        healthCard.getChildren().addAll(
                healthTitle,
                database,
                dispatch,
                gps,
                latency
        );

        VBox notificationCard = new VBox(12);
        notificationCard.setPadding(new Insets(20));
        notificationCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 15px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 15px;");

        Text notificationTitle = new Text("Notifications");
        notificationTitle.setStyle("-fx-font-size: 19px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text notification1 = new Text("System Update: Version 2.4.1 scheduled for 03:00 AM.");
        notification1.setWrappingWidth(330);
        notification1.setStyle("-fx-font-size: 11px;" + "-fx-fill: #374151;");

        Text notificationTime1 = new Text("1 hour ago");
        notificationTime1.setStyle("-fx-font-size: 10px;" + "-fx-fill: #9ca3af;");

        Text notification2 = new Text("New Specialist Doctor registered at St. Mary's.");
        notification2.setWrappingWidth(330);
        notification2.setStyle("-fx-font-size: 11px;" + "-fx-fill: #374151;");

        Text notificationTime2 = new Text("3 hours ago");
        notificationTime2.setStyle("-fx-font-size: 10px;" + "-fx-fill: #9ca3af;");

        notificationCard.getChildren().addAll(
                notificationTitle,
                notification1,
                notificationTime1,
                notification2,
                notificationTime2
        );

        rightPart.getChildren().addAll(
                liveCard,
                healthCard,
                notificationCard
        );

        HBox.setHgrow(leftContent, Priority.ALWAYS);

        mainContent.getChildren().addAll(
                leftContent,
                rightPart
        );

        VBox quickActions = new VBox(12);

        Text quickTitle = new Text("Quick Actions");
        quickTitle.setStyle("-fx-font-size: 19px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        HBox actionButtons = new HBox(12);

        Button addHospitalButton = new Button("+   Add Hospital");
        addHospitalButton.setPrefWidth(170);
        addHospitalButton.setPrefHeight(50);
        addHospitalButton.setStyle("-fx-background-color: #ffffff;" + "-fx-text-fill: #374151;" + "-fx-border-color: #d1d5db;" + "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 13px;");

        Button registerAmbulanceButton = new Button("Register Ambulance");
        registerAmbulanceButton.setPrefWidth(190);
        registerAmbulanceButton.setPrefHeight(50);
        registerAmbulanceButton.setStyle("-fx-background-color: #ffffff;" + "-fx-text-fill: #374151;" + "-fx-border-color: #d1d5db;" + "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 13px;");

        Button broadcastButton = new Button("Broadcast");
        broadcastButton.setPrefWidth(160);
        broadcastButton.setPrefHeight(50);
        broadcastButton.setStyle("-fx-background-color: #ffffff;" + "-fx-text-fill: #374151;" + "-fx-border-color: #d1d5db;" + "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 13px;");

        Button liveFeedButton = new Button("Live Feed");
        liveFeedButton.setPrefWidth(160);
        liveFeedButton.setPrefHeight(50);
        liveFeedButton.setStyle("-fx-background-color: #ffffff;" + "-fx-text-fill: #374151;" + "-fx-border-color: #d1d5db;" + "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 13px;");

        actionButtons.getChildren().addAll(
                addHospitalButton,
                registerAmbulanceButton,
                broadcastButton,
                liveFeedButton
        );

        quickActions.getChildren().addAll(
                quickTitle,
                actionButtons
        );

        VBox dashboard = new VBox(20);
        dashboard.setPadding(new Insets(25));

        dashboard.getChildren().addAll(
                header,
                cardsRow1,
                cardsRow2,
                mainContent,
                quickActions
        );

        ScrollPane scrollPane = new ScrollPane(dashboard);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;" + "-fx-background: transparent;");

        rightContent.getChildren().add(scrollPane);
        borderPane.setCenter(rightContent);




        dashboardButton.setOnAction(event -> {

            hospitalButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            userButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            policeButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            activityButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            dashboardButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

            rightContent.getChildren().clear();
            rightContent.getChildren().add(scrollPane);
        });



        hospitalButton.setOnAction(event -> {

            dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            userButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            policeButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            activityButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;" + "-fx-background-radius: 10px;");
            hospitalButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

            HospitalManagement hospManagement = new HospitalManagement();
            borderPane.setCenter(hospManagement.getHospitalManagement());
        });



        ambulanceButton.setOnAction(event -> {

            dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            hospitalButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            userButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            policeButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            activityButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            ambulanceButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

            AmbulanceManagement ambulManagement = new AmbulanceManagement();
            borderPane.setCenter(ambulManagement.getAmbulanceManagement());

        });

        userButton.setOnAction(event -> {

            dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            hospitalButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            policeButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            activityButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");

            userButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

            UserManagement userManagement = new UserManagement();
            borderPane.setCenter(userManagement.getUserManagement());

        });

        policeButton.setOnAction(event ->{
        
           dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            hospitalButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            activityButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");

            policeButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

            PoliceManagement policeManagement = new PoliceManagement();
            borderPane.setCenter(policeManagement.getPoliceManagement());

        });

        emergencyButton.setOnAction(event -> {

            dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            hospitalButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            userButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            policeButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            activityButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            emergencyButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

            EmergencyMonitoring emergencyMonitoring = new EmergencyMonitoring();
            borderPane.setCenter(emergencyMonitoring.getEmergencyMonitoring());

        });

        analyticsButton.setOnAction(event -> {

            dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            hospitalButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            userButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            policeButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            activityButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            analyticsButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

            AnalyticsAndReports analyticsReports = new AnalyticsAndReports();
            borderPane.setCenter(analyticsReports.getAnalyticsAndReports());

        });

        activityButton.setOnAction(event -> {

            dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            hospitalButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            userButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            policeButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            settingsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            activityButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

             ActivityLogs activityLogs = new ActivityLogs();
             borderPane.setCenter(activityLogs.getActivityLogsPage());
        });

        settingsButton.setOnAction(event -> {

            dashboardButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            hospitalButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            ambulanceButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            userButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            policeButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            emergencyButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            analyticsButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            activityButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #4b5563;" + "-fx-font-size: 15px;");
            settingsButton.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");

            Settings settings = new Settings();
            borderPane.setCenter(settings.getSettingsPage());
             
        });

        logoutButton.setOnAction(event -> {
            /*
             * Your SignIn navigation logic goes here.
             *
             * Example:
             *
             * SignIn signInPage = new SignIn();
             * dashboardStage.setScene(signInPage.getSignInScene());
             */
        });

        dashboardScene = new Scene(borderPane, dashboardStage.getWidth(),dashboardStage.getHeight());

        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("LifeLink Admin Dashboard");
        dashboardStage.setMaximized(true);
        dashboardStage.show();
    }
}