package com.kurukshetra.view.hospital;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line; 

public class AmbulanceTracking {

    public VBox getAmbulanceTracking() {

        // ========================= MAIN PAGE =========================

        VBox mainVBox = new VBox();
        mainVBox.setStyle("-fx-background-color: #faf8ff;");
        mainVBox.setPrefWidth(1100);
        mainVBox.setPrefHeight(750);

        // ========================= TOP BAR =========================

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(14, 24, 14, 24));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);
        topBar.setStyle("-fx-background-color: #faf8ff; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search ambulances or drivers...");
        searchField.setPrefWidth(320);
        searchField.setPrefHeight(36);
        searchField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 8px 12px; -fx-font-size: 13px;");

        HBox.setHgrow(searchField, Priority.ALWAYS);

        HBox topRight = new HBox();
        topRight.setSpacing(14);
        topRight.setAlignment(Pos.CENTER_RIGHT);

        Button notificationButton = new Button("🔔");
        notificationButton.setPrefSize(38, 38);
        notificationButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-background-radius: 50%;");

        Button settingsButton = new Button("⚙");
        settingsButton.setPrefSize(38, 38);
        settingsButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-background-radius: 50%;");

        Label adminView = new Label("Admin View");
        adminView.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        topRight.getChildren().addAll(
                notificationButton,
                settingsButton,
                adminView
        );

        HBox.setHgrow(topRight, Priority.NEVER);

        topBar.getChildren().addAll(
                searchField,
                topRight
        );

        // ========================= MAP AREA =========================

        HBox contentArea = new HBox();
        contentArea.setSpacing(0);
        contentArea.setPrefHeight(650);
        contentArea.setStyle("-fx-background-color: #e1e2ed;");

        // ========================= MAP =========================

        Pane mapPane = new Pane();
        mapPane.setPrefWidth(760);
        mapPane.setPrefHeight(650);
        mapPane.setStyle("-fx-background-color: #dfe5ea; -fx-border-color: #c3c6d7;");

        HBox.setHgrow(mapPane, Priority.ALWAYS);

        // Roads

        Line road1 = new Line(20, 100, 730, 500);
        road1.setStroke(Color.web("#ffffff"));
        road1.setStrokeWidth(18);

        Line road2 = new Line(100, 600, 680, 50);
        road2.setStroke(Color.web("#ffffff"));
        road2.setStrokeWidth(16);

        Line road3 = new Line(20, 330, 730, 330);
        road3.setStroke(Color.web("#ffffff"));
        road3.setStrokeWidth(14);

        Line road4 = new Line(370, 20, 370, 630);
        road4.setStroke(Color.web("#ffffff"));
        road4.setStrokeWidth(14);

        // Emergency route

        Line emergencyRoute = new Line(180, 450, 590, 280);
        emergencyRoute.setStroke(Color.web("#2563eb"));
        emergencyRoute.setStrokeWidth(7);

        // Hospital marker

        Circle hospitalCircle = new Circle(180, 450, 16);
        hospitalCircle.setFill(Color.web("#004ac6"));
        hospitalCircle.setStroke(Color.WHITE);
        hospitalCircle.setStrokeWidth(4);

        Label hospitalLabel = new Label("LifeLink Central");
        hospitalLabel.setLayoutX(140);
        hospitalLabel.setLayoutY(475);
        hospitalLabel.setStyle("-fx-background-color: white; -fx-background-radius: 6px; -fx-padding: 6px 10px; -fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #004ac6; -fx-border-color: #c3c6d7; -fx-border-radius: 6px;");

        // Accident marker

        Circle accidentCircle = new Circle(590, 150, 16);
        accidentCircle.setFill(Color.web("#ba1a1a"));
        accidentCircle.setStroke(Color.WHITE);
        accidentCircle.setStrokeWidth(4);

        Label accidentLabel = new Label("⚠ Accident: Location A");
        accidentLabel.setLayoutX(525);
        accidentLabel.setLayoutY(175);
        accidentLabel.setStyle("-fx-background-color: white; -fx-background-radius: 6px; -fx-padding: 6px 10px; -fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #ba1a1a; -fx-border-color: #c3c6d7; -fx-border-radius: 6px;");

        // Ambulance marker

        Circle ambulanceCircle = new Circle(500, 320, 18);
        ambulanceCircle.setFill(Color.web("#2563eb"));
        ambulanceCircle.setStroke(Color.WHITE);
        ambulanceCircle.setStrokeWidth(4);

        Label ambulanceIcon = new Label("🚑");
        ambulanceIcon.setLayoutX(488);
        ambulanceIcon.setLayoutY(306);
        ambulanceIcon.setStyle("-fx-font-size: 20px;");

        Label ambulanceInfo = new Label("Unit #824 (Mike Ross)\nETA: 4 MINS");
        ambulanceInfo.setLayoutX(530);
        ambulanceInfo.setLayoutY(300);
        ambulanceInfo.setStyle("-fx-background-color: white; -fx-background-radius: 8px; -fx-padding: 8px 12px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #191b23; -fx-border-color: #c3c6d7; -fx-border-radius: 8px;");

        // Map controls

        VBox mapControls = new VBox();
        mapControls.setSpacing(6);
        mapControls.setLayoutX(20);
        mapControls.setLayoutY(20);

        Button zoomIn = new Button("+");
        zoomIn.setPrefSize(42, 42);
        zoomIn.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-font-size: 20px; -fx-font-weight: bold;");

        Button zoomOut = new Button("-");
        zoomOut.setPrefSize(42, 42);
        zoomOut.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-font-size: 20px; -fx-font-weight: bold;");

        Button layersButton = new Button("☷");
        layersButton.setPrefSize(42, 42);
        layersButton.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-font-size: 18px;");

        Button locationButton = new Button("⌖");
        locationButton.setPrefSize(42, 42);
        locationButton.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-font-size: 20px;");

        mapControls.getChildren().addAll(
                zoomIn,
                zoomOut,
                layersButton,
                locationButton
        );

        mapPane.getChildren().addAll(
                road1,
                road2,
                road3,
                road4,
                emergencyRoute,
                hospitalCircle,
                hospitalLabel,
                accidentCircle,
                accidentLabel,
                ambulanceCircle,
                ambulanceIcon,
                ambulanceInfo,
                mapControls
        );

        // ========================= RIGHT INFORMATION PANEL =========================

        VBox informationPanel = new VBox();
        informationPanel.setPrefWidth(360);
        informationPanel.setPadding(new Insets(18));
        informationPanel.setSpacing(18);
        informationPanel.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 0px 1px;");

        // Active Mission Header

        HBox missionHeader = new HBox();
        missionHeader.setAlignment(Pos.CENTER_LEFT);
        missionHeader.setSpacing(10);

        Label missionTitle = new Label("Active Mission");
        missionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        HBox.setHgrow(missionTitle, Priority.ALWAYS);

        Label urgentLabel = new Label("URGENT");
        urgentLabel.setStyle("-fx-background-color: #ffdad6; -fx-text-fill: #ba1a1a; -fx-padding: 5px 9px; -fx-background-radius: 5px; -fx-font-size: 10px; -fx-font-weight: bold;");

        missionHeader.getChildren().addAll(
                missionTitle,
                urgentLabel
        );

        // Driver Information

        HBox driverBox = new HBox();
        driverBox.setSpacing(12);
        driverBox.setAlignment(Pos.CENTER_LEFT);

        Label driverIcon = new Label("👨‍⚕");
        driverIcon.setPrefSize(50, 50);
        driverIcon.setAlignment(Pos.CENTER);
        driverIcon.setStyle("-fx-background-color: #d0e1fb; -fx-background-radius: 10px; -fx-font-size: 25px;");

        VBox driverDetails = new VBox();
        driverDetails.setSpacing(3);

        Label driverName = new Label("Mike Ross");
        driverName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label driverRole = new Label("Lead Paramedic • Badge #4421");
        driverRole.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

        driverDetails.getChildren().addAll(
                driverName,
                driverRole
        );

        HBox.setHgrow(driverDetails, Priority.ALWAYS);

        Button callButton = new Button("☎");
        callButton.setPrefSize(38, 38);
        callButton.setStyle("-fx-background-color: #e7e7f3; -fx-background-radius: 50%; -fx-font-size: 17px;");

        driverBox.getChildren().addAll(
                driverIcon,
                driverDetails,
                callButton
        );

        // Route Statistics

        HBox routeStats = new HBox();
        routeStats.setSpacing(10);

        VBox arrivalBox = new VBox();
        arrivalBox.setPadding(new Insets(10));
        arrivalBox.setSpacing(4);
        arrivalBox.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Label arrivalTitle = new Label("ESTIMATED ARRIVAL");
        arrivalTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        Label arrivalValue = new Label("4 Minutes");
        arrivalValue.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: #004ac6;");

        arrivalBox.getChildren().addAll(
                arrivalTitle,
                arrivalValue
        );

        VBox distanceBox = new VBox();
        distanceBox.setPadding(new Insets(10));
        distanceBox.setSpacing(4);
        distanceBox.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Label distanceTitle = new Label("DISTANCE");
        distanceTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        Label distanceValue = new Label("2.5 KM");
        distanceValue.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: #004ac6;");

        distanceBox.getChildren().addAll(
                distanceTitle,
                distanceValue
        );

        HBox.setHgrow(arrivalBox, Priority.ALWAYS);
        HBox.setHgrow(distanceBox, Priority.ALWAYS);

        routeStats.getChildren().addAll(
                arrivalBox,
                distanceBox
        );

        // Patient Status

        VBox patientSection = new VBox();
        patientSection.setSpacing(7);

        HBox patientStatusHeader = new HBox();
        patientStatusHeader.setAlignment(Pos.CENTER_LEFT);

        Label patientStatusTitle = new Label("PATIENT STATUS");
        patientStatusTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        HBox.setHgrow(patientStatusTitle, Priority.ALWAYS);

        Label criticalLabel = new Label("🚨 Critical");
        criticalLabel.setStyle("-fx-text-fill: #ba1a1a; -fx-font-size: 13px; -fx-font-weight: bold;");

        patientStatusHeader.getChildren().addAll(
                patientStatusTitle,
                criticalLabel
        );

        VBox traumaBox = new VBox();
        traumaBox.setSpacing(5);
        traumaBox.setPadding(new Insets(12));
        traumaBox.setStyle("-fx-background-color: #fff5f4; -fx-border-color: #ffdad6; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Label traumaTitle = new Label("Trauma - Severe Bleeding");
        traumaTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #ba1a1a;");

        Label traumaDetails = new Label("Male, ~45yrs. Vital signs stabilized, awaiting rapid transport to OT 4.");
        traumaDetails.setWrapText(true);
        traumaDetails.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

        traumaBox.getChildren().addAll(
                traumaTitle,
                traumaDetails
        );

        patientSection.getChildren().addAll(
                patientStatusHeader,
                traumaBox
        );

        // ========================= TRACKER =========================

        VBox tracker = new VBox();
        tracker.setSpacing(15);

        Label trackerTitle = new Label("MISSION STATUS");
        trackerTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        VBox dispatched = new VBox();
        dispatched.setSpacing(3);

        Label dispatchedTitle = new Label("●  Ambulance Dispatched");
        dispatchedTitle.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label dispatchedTime = new Label("     14:02 PM");
        dispatchedTime.setStyle("-fx-font-size: 10px; -fx-text-fill: #434655;");

        dispatched.getChildren().addAll(
                dispatchedTitle,
                dispatchedTime
        );

        VBox extraction = new VBox();
        extraction.setSpacing(3);

        Label extractionTitle = new Label("●  On-site Extraction");
        extractionTitle.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label extractionTime = new Label("     14:15 PM");
        extractionTime.setStyle("-fx-font-size: 10px; -fx-text-fill: #434655;");

        extraction.getChildren().addAll(
                extractionTitle,
                extractionTime
        );

        VBox transit = new VBox();
        transit.setSpacing(3);

        Label transitTitle = new Label("●  In Transit to Hospital");
        transitTitle.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #004ac6;");

        Label transitStatus = new Label("     LIVE STATUS");
        transitStatus.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #004ac6;");

        transit.getChildren().addAll(
                transitTitle,
                transitStatus
        );

        tracker.getChildren().addAll(
                trackerTitle,
                dispatched,
                extraction,
                transit
        );

        // ========================= PREPARE OT BUTTON =========================

        Button prepareOTButton = new Button("▣   Prepare OT 4");
        prepareOTButton.setMaxWidth(Double.MAX_VALUE);
        prepareOTButton.setPrefHeight(42);
        prepareOTButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 8px;");

        // ========================= ADD INFORMATION PANEL =========================

        informationPanel.getChildren().addAll(
                missionHeader,
                driverBox,
                routeStats,
                patientSection,
                tracker,
                prepareOTButton
        );

        // ========================= CONTENT AREA =========================

        contentArea.getChildren().addAll(
                mapPane,
                informationPanel
        );

        // ========================= BOTTOM ACTION BAR =========================

        HBox bottomBar = new HBox();
        bottomBar.setSpacing(15);
        bottomBar.setPadding(new Insets(10, 20, 10, 20));
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 25px; -fx-background-radius: 25px;");

        Button contactDriver = new Button("💬  Contact Driver");
        contactDriver.setStyle("-fx-background-color: transparent; -fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Button refreshLocation = new Button("⟳  Refresh Location");
        refreshLocation.setStyle("-fx-background-color: transparent; -fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Button dispatchUnit = new Button("🚑  Dispatch New Unit");
        dispatchUnit.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-padding: 10px 18px; -fx-background-radius: 20px; -fx-font-size: 13px; -fx-font-weight: bold;");

        bottomBar.getChildren().addAll(
                contactDriver,
                refreshLocation,
                dispatchUnit
        );

        // ========================= FINAL PAGE =========================

        mainVBox.getChildren().addAll(
                topBar,
                contentArea,
                bottomBar
        );

        VBox.setVgrow(contentArea, Priority.ALWAYS);

        ScrollPane scrollPane = new ScrollPane(mainVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background:#faf8ff;-fx-background-color:#faf8ff;");

        VBox AmbulacePage = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return AmbulacePage;
    }
}
