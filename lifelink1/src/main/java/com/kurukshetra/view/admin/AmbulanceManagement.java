package com.kurukshetra.view.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class AmbulanceManagement {

    public VBox getAmbulanceManagement() {

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(25));
        mainContent.setStyle("-fx-background-color: #faf8ff;");

        Text heading = new Text("Ambulance Fleet Management");
        heading.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text subHeading = new Text("Real-time oversight of critical emergency transport assets.");
        subHeading.setStyle("-fx-font-size: 14px; -fx-fill: #737686;");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(
                heading,
                subHeading
        );

        Button assignDriverButton = new Button("＋   Assign Driver");
        assignDriverButton.setPrefWidth(140);
        assignDriverButton.setPrefHeight(42);
        assignDriverButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #004ac6; -fx-border-color: #004ac6; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-font-size: 12px; -fx-font-weight: bold;");

        Button registerAmbulanceButton = new Button("＋   Register Ambulance");
        registerAmbulanceButton.setPrefWidth(160);
        registerAmbulanceButton.setPrefHeight(42);
        registerAmbulanceButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-background-radius: 10px; -fx-font-size: 12px; -fx-font-weight: bold;");

        HBox headerButtons = new HBox(10);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);
        headerButtons.getChildren().addAll(
                assignDriverButton,
                registerAmbulanceButton
        );

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox header = new HBox(
                headingBox,
                headerSpacer,
                headerButtons
        );

        header.setAlignment(Pos.CENTER_LEFT);

        // METRICS

        HBox metricsRow = new HBox(15);
        metricsRow.setAlignment(Pos.CENTER);

        VBox totalFleetBox = new VBox(7);
        totalFleetBox.setPadding(new Insets(18));
        totalFleetBox.setPrefHeight(115);
        totalFleetBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e1e2ed; -fx-border-radius: 15px;");

        Text totalFleetTitle = new Text("TOTAL FLEET");
        totalFleetTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        Text totalFleetValue = new Text("42");
        totalFleetValue.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text totalFleetInfo = new Text("↗  +2");
        totalFleetInfo.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #16a34a;");

        totalFleetBox.getChildren().addAll(
                totalFleetTitle,
                totalFleetValue,
                totalFleetInfo
        );

        VBox activeCallsBox = new VBox(7);
        activeCallsBox.setPadding(new Insets(18));
        activeCallsBox.setPrefHeight(115);
        activeCallsBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e1e2ed; -fx-border-radius: 15px;");

        Text activeCallsTitle = new Text("ACTIVE CALLS");
        activeCallsTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        Text activeCallsValue = new Text("18");
        activeCallsValue.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        Text activeCallsInfo = new Text("On Mission");
        activeCallsInfo.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        activeCallsBox.getChildren().addAll(
                activeCallsTitle,
                activeCallsValue,
                activeCallsInfo
        );

        VBox unavailableBox = new VBox(7);
        unavailableBox.setPadding(new Insets(18));
        unavailableBox.setPrefHeight(115);
        unavailableBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e1e2ed; -fx-border-radius: 15px; -fx-border-width: 0px 0px 0px 4px;");

        Text unavailableTitle = new Text("UNAVAILABLE");
        unavailableTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        Text unavailableValue = new Text("5");
        unavailableValue.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

        Text unavailableInfo = new Text("Maintenance");
        unavailableInfo.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        unavailableBox.getChildren().addAll(
                unavailableTitle,
                unavailableValue,
                unavailableInfo
        );

        VBox responseTimeBox = new VBox(7);
        responseTimeBox.setPadding(new Insets(18));
        responseTimeBox.setPrefHeight(115);
        responseTimeBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e1e2ed; -fx-border-radius: 15px;");

        Text responseTimeTitle = new Text("AVG RESPONSE TIME");
        responseTimeTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        Text responseTimeValue = new Text("8.4 m");
        responseTimeValue.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text responseTimeInfo = new Text("✓  Target Met");
        responseTimeInfo.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #16a34a;");

        responseTimeBox.getChildren().addAll(
                responseTimeTitle,
                responseTimeValue,
                responseTimeInfo
        );

        HBox.setHgrow(totalFleetBox, Priority.ALWAYS);
        HBox.setHgrow(activeCallsBox, Priority.ALWAYS);
        HBox.setHgrow(unavailableBox, Priority.ALWAYS);
        HBox.setHgrow(responseTimeBox, Priority.ALWAYS);

        metricsRow.getChildren().addAll(
                totalFleetBox,
                activeCallsBox,
                unavailableBox,
                responseTimeBox
        );

        // MAIN AREA

        HBox mainArea = new HBox(20);

        VBox tableCard = new VBox();
        tableCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #c3c6d7; -fx-border-radius: 15px;");

        VBox tableTitleBox = new VBox(4);
        tableTitleBox.setPadding(new Insets(16));

        Text tableTitle = new Text("Vehicle Status Registry");
        tableTitle.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text tableInfo = new Text("Monitor ambulance vehicles and driver status");
        tableInfo.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        tableTitleBox.getChildren().addAll(
                tableTitle,
                tableInfo
        );

        TextField searchField = new TextField();
        searchField.setPromptText("Search vehicle or driver...");
        searchField.setPrefWidth(230);
        searchField.setPrefHeight(38);
        searchField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-font-size: 12px;");

        Region tableHeaderSpacer = new Region();
        HBox.setHgrow(tableHeaderSpacer, Priority.ALWAYS);

        HBox tableTop = new HBox(
                tableTitleBox,
                tableHeaderSpacer,
                searchField
        );

        tableTop.setPadding(new Insets(5, 15, 5, 5));
        tableTop.setAlignment(Pos.CENTER_LEFT);

        // TABLE HEADER

        HBox tableHeader = new HBox(10);
        tableHeader.setPadding(new Insets(12, 15, 12, 15));
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setStyle("-fx-background-color: #f3f3fe;");

        Text ambulanceHeader = new Text("AMBULANCE NO.");
        ambulanceHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #737686;");
        ambulanceHeader.setWrappingWidth(115);

        Text driverHeader = new Text("DRIVER");
        driverHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #737686;");
        driverHeader.setWrappingWidth(115);

        Text statusHeader = new Text("STATUS");
        statusHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #737686;");
        statusHeader.setWrappingWidth(100);

        Text etaHeader = new Text("ETA");
        etaHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #737686;");
        etaHeader.setWrappingWidth(70);

        Text actionHeader = new Text("ACTION");
        actionHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #737686;");
        actionHeader.setWrappingWidth(70);

        tableHeader.getChildren().addAll(
                ambulanceHeader,
                driverHeader,
                statusHeader,
                etaHeader,
                actionHeader
        );

        // AMBULANCE 1

        HBox ambulanceRow1 = new HBox(10);
        ambulanceRow1.setPadding(new Insets(14, 15, 14, 15));
        ambulanceRow1.setAlignment(Pos.CENTER_LEFT);
        ambulanceRow1.setStyle("-fx-border-color: transparent transparent #e1e2ed transparent; -fx-border-width: 0px 0px 1px 0px;");

        Circle ambulanceCircle1 = new Circle(20);
        ambulanceCircle1.setFill(Color.web("#dbe1ff"));

        Text ambulanceIcon1 = new Text("🚑");
        ambulanceIcon1.setStyle("-fx-font-size: 16px;");

        StackPane ambulanceIconPane1 = new StackPane();
        ambulanceIconPane1.setPrefSize(40, 40);
        ambulanceIconPane1.getChildren().addAll(
                ambulanceCircle1,
                ambulanceIcon1
        );

        Text ambulanceNumber1 = new Text("AMB-1024");
        ambulanceNumber1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #191b23;");

        HBox ambulanceNumberBox1 = new HBox(8);
        ambulanceNumberBox1.setPrefWidth(115);
        ambulanceNumberBox1.setAlignment(Pos.CENTER_LEFT);
        ambulanceNumberBox1.getChildren().addAll(
                ambulanceIconPane1,
                ambulanceNumber1
        );

        Text driver1 = new Text("Marcus Thorne");
        driver1.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

        HBox driverBox1 = new HBox(driver1);
        driverBox1.setPrefWidth(115);
        driverBox1.setAlignment(Pos.CENTER_LEFT);

        Text status1 = new Text("ON ROUTE");
        status1.setStyle("-fx-background-color: #dcfce7; -fx-text-fill: #15803d; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-padding: 5px 9px;");

        HBox statusBox1 = new HBox(status1);
        statusBox1.setPrefWidth(100);
        statusBox1.setAlignment(Pos.CENTER_LEFT);

        Text eta1 = new Text("4 mins");
        eta1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #191b23;");

        HBox etaBox1 = new HBox(eta1);
        etaBox1.setPrefWidth(70);
        etaBox1.setAlignment(Pos.CENTER_LEFT);

        Button actionButton1 = new Button("⋮");
        actionButton1.setPrefSize(35, 35);
        actionButton1.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 18px;");

        HBox actionBox1 = new HBox(actionButton1);
        actionBox1.setPrefWidth(70);
        actionBox1.setAlignment(Pos.CENTER_LEFT);

        ambulanceRow1.getChildren().addAll(
                ambulanceNumberBox1,
                driverBox1,
                statusBox1,
                etaBox1,
                actionBox1
        );

        // AMBULANCE 2

        HBox ambulanceRow2 = new HBox(10);
        ambulanceRow2.setPadding(new Insets(14, 15, 14, 15));
        ambulanceRow2.setAlignment(Pos.CENTER_LEFT);
        ambulanceRow2.setStyle("-fx-border-color: transparent transparent #e1e2ed transparent; -fx-border-width: 0px 0px 1px 0px;");

        Circle ambulanceCircle2 = new Circle(20);
        ambulanceCircle2.setFill(Color.web("#e1e2ed"));

        Text ambulanceIcon2 = new Text("🚑");
        ambulanceIcon2.setStyle("-fx-font-size: 16px;");

        StackPane ambulanceIconPane2 = new StackPane();
        ambulanceIconPane2.setPrefSize(40, 40);
        ambulanceIconPane2.getChildren().addAll(
                ambulanceCircle2,
                ambulanceIcon2
        );

        Text ambulanceNumber2 = new Text("AMB-9981");
        ambulanceNumber2.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #191b23;");

        HBox ambulanceNumberBox2 = new HBox(8);
        ambulanceNumberBox2.setPrefWidth(115);
        ambulanceNumberBox2.setAlignment(Pos.CENTER_LEFT);
        ambulanceNumberBox2.getChildren().addAll(
                ambulanceIconPane2,
                ambulanceNumber2
        );

        Text driver2 = new Text("Sarah Jenkins");
        driver2.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

        HBox driverBox2 = new HBox(driver2);
        driverBox2.setPrefWidth(115);
        driverBox2.setAlignment(Pos.CENTER_LEFT);

        Text status2 = new Text("STATIONARY");
        status2.setStyle("-fx-background-color: #dbeafe; -fx-text-fill: #1d4ed8; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-padding: 5px 9px;");

        HBox statusBox2 = new HBox(status2);
        statusBox2.setPrefWidth(100);
        statusBox2.setAlignment(Pos.CENTER_LEFT);

        Text eta2 = new Text("—");
        eta2.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

        HBox etaBox2 = new HBox(eta2);
        etaBox2.setPrefWidth(70);
        etaBox2.setAlignment(Pos.CENTER_LEFT);

        Button actionButton2 = new Button("⋮");
        actionButton2.setPrefSize(35, 35);
        actionButton2.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 18px;");

        HBox actionBox2 = new HBox(actionButton2);
        actionBox2.setPrefWidth(70);
        actionBox2.setAlignment(Pos.CENTER_LEFT);

        ambulanceRow2.getChildren().addAll(
                ambulanceNumberBox2,
                driverBox2,
                statusBox2,
                etaBox2,
                actionBox2
        );

        // AMBULANCE 3

        HBox ambulanceRow3 = new HBox(10);
        ambulanceRow3.setPadding(new Insets(14, 15, 14, 15));
        ambulanceRow3.setAlignment(Pos.CENTER_LEFT);
        ambulanceRow3.setStyle("-fx-border-color: transparent transparent #e1e2ed transparent; -fx-border-width: 0px 0px 1px 0px; -fx-border-insets: 0 0 0 0;");

        Circle ambulanceCircle3 = new Circle(20);
        ambulanceCircle3.setFill(Color.web("#ffdad6"));

        Text ambulanceIcon3 = new Text("⚠");
        ambulanceIcon3.setStyle("-fx-font-size: 16px; -fx-fill: #ba1a1a;");

        StackPane ambulanceIconPane3 = new StackPane();
        ambulanceIconPane3.setPrefSize(40, 40);
        ambulanceIconPane3.getChildren().addAll(
                ambulanceCircle3,
                ambulanceIcon3
        );

        Text ambulanceNumber3 = new Text("AMB-4402");
        ambulanceNumber3.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #191b23;");

        HBox ambulanceNumberBox3 = new HBox(8);
        ambulanceNumberBox3.setPrefWidth(115);
        ambulanceNumberBox3.setAlignment(Pos.CENTER_LEFT);
        ambulanceNumberBox3.getChildren().addAll(
                ambulanceIconPane3,
                ambulanceNumber3
        );

        Text driver3 = new Text("David Wu");
        driver3.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

        HBox driverBox3 = new HBox(driver3);
        driverBox3.setPrefWidth(115);
        driverBox3.setAlignment(Pos.CENTER_LEFT);

        Text status3 = new Text("EMERGENCY");
        status3.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-padding: 5px 9px;");

        HBox statusBox3 = new HBox(status3);
        statusBox3.setPrefWidth(100);
        statusBox3.setAlignment(Pos.CENTER_LEFT);

        Text eta3 = new Text("1 min");
        eta3.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

        HBox etaBox3 = new HBox(eta3);
        etaBox3.setPrefWidth(70);
        etaBox3.setAlignment(Pos.CENTER_LEFT);

        Button actionButton3 = new Button("⋮");
        actionButton3.setPrefSize(35, 35);
        actionButton3.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 18px;");

        HBox actionBox3 = new HBox(actionButton3);
        actionBox3.setPrefWidth(70);
        actionBox3.setAlignment(Pos.CENTER_LEFT);

        ambulanceRow3.getChildren().addAll(
                ambulanceNumberBox3,
                driverBox3,
                statusBox3,
                etaBox3,
                actionBox3
        );

        // AMBULANCE 4

        HBox ambulanceRow4 = new HBox(10);
        ambulanceRow4.setPadding(new Insets(14, 15, 14, 15));
        ambulanceRow4.setAlignment(Pos.CENTER_LEFT);

        Circle ambulanceCircle4 = new Circle(20);
        ambulanceCircle4.setFill(Color.web("#e1e2ed"));

        Text ambulanceIcon4 = new Text("🚑");
        ambulanceIcon4.setStyle("-fx-font-size: 16px;");

        StackPane ambulanceIconPane4 = new StackPane();
        ambulanceIconPane4.setPrefSize(40, 40);
        ambulanceIconPane4.getChildren().addAll(
                ambulanceCircle4,
                ambulanceIcon4
        );

        Text ambulanceNumber4 = new Text("AMB-7723");
        ambulanceNumber4.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #191b23;");

        HBox ambulanceNumberBox4 = new HBox(8);
        ambulanceNumberBox4.setPrefWidth(115);
        ambulanceNumberBox4.setAlignment(Pos.CENTER_LEFT);
        ambulanceNumberBox4.getChildren().addAll(
                ambulanceIconPane4,
                ambulanceNumber4
        );

        Text driver4 = new Text("Elena Rodriguez");
        driver4.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

        HBox driverBox4 = new HBox(driver4);
        driverBox4.setPrefWidth(115);
        driverBox4.setAlignment(Pos.CENTER_LEFT);

        Text status4 = new Text("MAINTENANCE");
        status4.setStyle("-fx-background-color: #fef3c7; -fx-text-fill: #a16207; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-padding: 5px 9px;");

        HBox statusBox4 = new HBox(status4);
        statusBox4.setPrefWidth(100);
        statusBox4.setAlignment(Pos.CENTER_LEFT);

        Text eta4 = new Text("—");
        eta4.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

        HBox etaBox4 = new HBox(eta4);
        etaBox4.setPrefWidth(70);
        etaBox4.setAlignment(Pos.CENTER_LEFT);

        Button actionButton4 = new Button("⋮");
        actionButton4.setPrefSize(35, 35);
        actionButton4.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 18px;");

        HBox actionBox4 = new HBox(actionButton4);
        actionBox4.setPrefWidth(70);
        actionBox4.setAlignment(Pos.CENTER_LEFT);

        ambulanceRow4.getChildren().addAll(
                ambulanceNumberBox4,
                driverBox4,
                statusBox4,
                etaBox4,
                actionBox4
        );

        tableCard.getChildren().addAll(
                tableTop,
                tableHeader,
                ambulanceRow1,
                ambulanceRow2,
                ambulanceRow3,
                ambulanceRow4
        );

        // LIVE TRACKING

        VBox trackingCard = new VBox();
        trackingCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #c3c6d7; -fx-border-radius: 15px;");

        HBox trackingHeader = new HBox(10);
        trackingHeader.setPadding(new Insets(16));
        trackingHeader.setAlignment(Pos.CENTER_LEFT);

        Text mapIcon = new Text("⌖");
        mapIcon.setStyle("-fx-font-size: 23px; -fx-fill: #004ac6;");

        Text trackingTitle = new Text("Live Tracking");
        trackingTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Region trackingSpacer = new Region();
        HBox.setHgrow(trackingSpacer, Priority.ALWAYS);

        Circle liveCircle = new Circle(5);
        liveCircle.setFill(Color.web("#22c55e"));

        Text liveText = new Text("Live");
        liveText.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        HBox liveBox = new HBox(5);
        liveBox.setAlignment(Pos.CENTER);
        liveBox.getChildren().addAll(
                liveCircle,
                liveText
        );

        trackingHeader.getChildren().addAll(
                mapIcon,
                trackingTitle,
                trackingSpacer,
                liveBox
        );

        // MAP AREA

        StackPane mapPane = new StackPane();
        mapPane.setPrefHeight(430);
        mapPane.setStyle("-fx-background-color: #dce8df;");

        Line road1 = new Line(0, 80, 500, 280);
        road1.setStroke(Color.web("#ffffff"));
        road1.setStrokeWidth(12);

        Line road2 = new Line(80, 0, 300, 430);
        road2.setStroke(Color.web("#ffffff"));
        road2.setStrokeWidth(10);

        Line road3 = new Line(500, 100, 100, 350);
        road3.setStroke(Color.web("#ffffff"));
        road3.setStrokeWidth(9);

        Line routeLine = new Line(80, 350, 420, 100);
        routeLine.setStroke(Color.web("#2563eb"));
        routeLine.setStrokeWidth(5);

        Circle markerCircle1 = new Circle(20);
        markerCircle1.setFill(Color.web("#004ac6"));

        Text markerText1 = new Text("🚑");
        markerText1.setStyle("-fx-font-size: 16px;");

        StackPane marker1 = new StackPane();
        marker1.setLayoutX(120);
        marker1.setLayoutY(130);
        marker1.getChildren().addAll(
                markerCircle1,
                markerText1
        );

        Circle markerCircle2 = new Circle(20);
        markerCircle2.setFill(Color.web("#ba1a1a"));

        Text markerText2 = new Text("!");
        markerText2.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-fill: white;");

        StackPane marker2 = new StackPane();
        marker2.setLayoutX(350);
        marker2.setLayoutY(270);
        marker2.getChildren().addAll(
                markerCircle2,
                markerText2
        );

        Text ambulanceLabel1 = new Text("AMB-1024");
        ambulanceLabel1.setStyle("-fx-background-color: white; -fx-padding: 5px 8px; -fx-background-radius: 5px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #191b23;");

        StackPane labelPane1 = new StackPane(ambulanceLabel1);
        labelPane1.setLayoutX(105);
        labelPane1.setLayoutY(90);

        Text ambulanceLabel2 = new Text("EMERGENCY");
        ambulanceLabel2.setStyle("-fx-background-color: white; -fx-padding: 5px 8px; -fx-background-radius: 5px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

        StackPane labelPane2 = new StackPane(ambulanceLabel2);
        labelPane2.setLayoutX(320);
        labelPane2.setLayoutY(225);

        mapPane.getChildren().addAll(
                road1,
                road2,
                road3,
                routeLine,
                marker1,
                marker2,
                labelPane1,
                labelPane2
        );

        HBox mapFooter = new HBox();
        mapFooter.setPadding(new Insets(12, 15, 12, 15));
        mapFooter.setAlignment(Pos.CENTER_LEFT);
        mapFooter.setStyle("-fx-background-color: #f3f3fe;");

        Text districtText = new Text("Emergency Control Center");
        districtText.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        Region footerSpacer = new Region();
        HBox.setHgrow(footerSpacer, Priority.ALWAYS);

        Button fullViewButton = new Button("Full View");
        fullViewButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 11px; -fx-font-weight: bold;");

        mapFooter.getChildren().addAll(
                districtText,
                footerSpacer,
                fullViewButton
        );

        trackingCard.getChildren().addAll(
                trackingHeader,
                mapPane,
                mapFooter
        );

        HBox.setHgrow(tableCard, Priority.ALWAYS);
        mainArea.getChildren().addAll(
                tableCard,
                trackingCard
        );

        // FINAL CONTENT

        mainContent.getChildren().addAll(
                header,
                metricsRow,
                mainArea
        );

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        VBox finalContent = new VBox(scrollPane);
        finalContent.setStyle("-fx-background-color: #faf8ff;");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return finalContent;
    }
}