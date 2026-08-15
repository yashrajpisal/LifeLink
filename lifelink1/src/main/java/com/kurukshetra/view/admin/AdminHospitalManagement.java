
package com.kurukshetra.view.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class AdminHospitalManagement {

    public VBox getHospitalManagement() {

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(25));
        mainContent.setStyle("-fx-background-color: #f8f8ff;");

        Text heading = new Text("Hospital Management");
        heading.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text subHeading = new Text("Monitor and manage healthcare facilities across the city-wide emergency dispatch network.");
        subHeading.setStyle("-fx-font-size: 14px;" + "-fx-fill: #6b7280;");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(
                heading,
                subHeading
        );

        Button verifyHospitalButton = new Button("⚙   Verify Hospital");
        verifyHospitalButton.setPrefWidth(135);
        verifyHospitalButton.setPrefHeight(42);
        verifyHospitalButton.setStyle("-fx-background-color: #ffffff;" + "-fx-text-fill: #374151;" + "-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-border-color: #c3c6d7;" + "-fx-border-radius: 9px;" + "-fx-background-radius: 9px;");

        Button addHospitalButton = new Button("+   Add Hospital");
        addHospitalButton.setPrefWidth(135);
        addHospitalButton.setPrefHeight(42);
        addHospitalButton.setStyle("-fx-background-color: #004ac6;" + "-fx-text-fill: white;" + "-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 9px;");

        HBox headerButtons = new HBox(10);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);
        headerButtons.getChildren().addAll(
                verifyHospitalButton,
                addHospitalButton
        );

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox header = new HBox(
                headingBox,
                headerSpacer,
                headerButtons
        );

        header.setAlignment(Pos.CENTER_LEFT);

        HBox summaryRow = new HBox(15);
        summaryRow.setAlignment(Pos.CENTER);

        VBox totalHospitalBox = new VBox(5);
        totalHospitalBox.setPadding(new Insets(15));
        totalHospitalBox.setPrefHeight(105);
        totalHospitalBox.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text totalTitle = new Text("TOTAL HOSPITALS");
        totalTitle.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #6b7280;");

        Text totalValue = new Text("142");
        totalValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #1455c0;");

        Text totalInfo = new Text("↗ +4 since last month");
        totalInfo.setStyle("-fx-font-size: 10px;" + "-fx-fill: #16a34a;");

        totalHospitalBox.getChildren().addAll(
                totalTitle,
                totalValue,
                totalInfo
        );

        VBox activeHospitalBox = new VBox(5);
        activeHospitalBox.setPadding(new Insets(15));
        activeHospitalBox.setPrefHeight(105);
        activeHospitalBox.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text activeTitle = new Text("ACTIVE HOSPITALS");
        activeTitle.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #6b7280;");

        Text activeValue = new Text("138");
        activeValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #1455c0;");

        Text activeInfo = new Text("● 97% operational rate");
        activeInfo.setStyle("-fx-font-size: 10px;" + "-fx-fill: #16a34a;");

        activeHospitalBox.getChildren().addAll(
                activeTitle,
                activeValue,
                activeInfo
        );

        VBox occupancyBox = new VBox(5);
        occupancyBox.setPadding(new Insets(15));
        occupancyBox.setPrefHeight(105);
        occupancyBox.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text occupancyTitle = new Text("ICU OCCUPANCY");
        occupancyTitle.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #6b7280;");

        Text occupancyValue = new Text("84%");
        occupancyValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #dc2626;");

        Region occupancyBackground = new Region();
        occupancyBackground.setPrefHeight(7);
        occupancyBackground.setPrefWidth(130);
        occupancyBackground.setStyle("-fx-background-color: #e5e7eb;" + "-fx-background-radius: 10px;");

        Region occupancyProgress = new Region();
        occupancyProgress.setPrefHeight(7);
        occupancyProgress.setPrefWidth(110);
        occupancyProgress.setStyle("-fx-background-color: #dc2626;" + "-fx-background-radius: 10px;");

        StackPane occupancyProgressPane = new StackPane();
        occupancyProgressPane.setAlignment(Pos.CENTER_LEFT);
        occupancyProgressPane.getChildren().addAll(
                occupancyBackground,
                occupancyProgress
        );

        occupancyBox.getChildren().addAll(
                occupancyTitle,
                occupancyValue,
                occupancyProgressPane
        );

        HBox.setHgrow(totalHospitalBox, Priority.ALWAYS);
        HBox.setHgrow(activeHospitalBox, Priority.ALWAYS);
        HBox.setHgrow(occupancyBox, Priority.ALWAYS);

        summaryRow.getChildren().addAll(
                totalHospitalBox,
                activeHospitalBox,
                occupancyBox
        );

        HBox filterBox = new HBox(12);
        filterBox.setPadding(new Insets(15));
        filterBox.setAlignment(Pos.CENTER_LEFT);
        filterBox.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search hospital by name, ID or phone...");
        searchField.setPrefHeight(40);
        searchField.setPrefWidth(420);
        searchField.setStyle("-fx-background-color: #ffffff;" + "-fx-border-color: #c3c6d7;" + "-fx-border-radius: 9px;" + "-fx-background-radius: 9px;" + "-fx-font-size: 13px;");

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll(
                "All Status",
                "Active",
                "Critical",
                "Maintenance"
        );
        statusCombo.setValue("All Status");
        statusCombo.setPrefHeight(40);
        statusCombo.setPrefWidth(160);

        ComboBox<String> facilityCombo = new ComboBox<>();
        facilityCombo.getItems().addAll(
                "All Facilities",
                "ICU",
                "OT",
                "Emergency",
                "Trauma Center"
        );
        facilityCombo.setValue("All Facilities");
        facilityCombo.setPrefHeight(40);
        facilityCombo.setPrefWidth(175);

        filterBox.getChildren().addAll(
                searchField,
                statusCombo,
                facilityCombo
        );

        VBox inventoryCard = new VBox();
        inventoryCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 15px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 15px;");

        Text inventoryTitle = new Text("Facility Inventory");
        inventoryTitle.setStyle("-fx-font-size: 19px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text inventoryInfo = new Text("Hospital availability and emergency resource status");
        inventoryInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #737686;");

        VBox inventoryTitleBox = new VBox(4);
        inventoryTitleBox.getChildren().addAll(
                inventoryTitle,
                inventoryInfo
        );

        Button filterButton = new Button("☷");
        filterButton.setPrefSize(38, 35);
        filterButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #374151;" + "-fx-font-size: 16px;");

        Button moreButton = new Button("⋮");
        moreButton.setPrefSize(38, 35);
        moreButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #374151;" + "-fx-font-size: 18px;");

        HBox inventoryActions = new HBox(3);
        inventoryActions.setAlignment(Pos.CENTER_RIGHT);
        inventoryActions.getChildren().addAll(
                filterButton,
                moreButton
        );

        Region inventoryHeaderSpacer = new Region();
        HBox.setHgrow(inventoryHeaderSpacer, Priority.ALWAYS);

        HBox inventoryHeader = new HBox(
                inventoryTitleBox,
                inventoryHeaderSpacer,
                inventoryActions
        );

        inventoryHeader.setPadding(new Insets(16));
        inventoryHeader.setAlignment(Pos.CENTER_LEFT);

        HBox tableHeader = new HBox(10);
        tableHeader.setPadding(new Insets(12, 15, 12, 15));
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setStyle("-fx-background-color: #e9e9f5;");

        Label hospitalNameHeader = new Label("HOSPITAL NAME");
        hospitalNameHeader.setPrefWidth(175);
        hospitalNameHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-text-fill: #6b7280;");

        Label cityHeader = new Label("CITY");
        cityHeader.setPrefWidth(110);
        cityHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-text-fill: #6b7280;");

        Label icuHeader = new Label("ICU\nBEDS");
        icuHeader.setPrefWidth(75);
        icuHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-text-fill: #6b7280;");

        Label availabilityHeader = new Label("AVAILABILITY");
        availabilityHeader.setPrefWidth(125);
        availabilityHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-text-fill: #6b7280;");

        Label statusHeader = new Label("STATUS");
        statusHeader.setPrefWidth(110);
        statusHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-text-fill: #6b7280;");

        Label manageHeader = new Label("ACTION");
        manageHeader.setPrefWidth(80);
        manageHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-text-fill: #6b7280;");

        tableHeader.getChildren().addAll(
                hospitalNameHeader,
                cityHeader,
                icuHeader,
                availabilityHeader,
                statusHeader,
                manageHeader
        );

        HBox hospitalRow1 = new HBox(10);
        hospitalRow1.setPadding(new Insets(14, 15, 14, 15));
        hospitalRow1.setAlignment(Pos.CENTER_LEFT);
        hospitalRow1.setStyle("-fx-border-color: transparent transparent #e5e7eb transparent;" + "-fx-border-width: 0px 0px 1px 0px;");

        Circle hospitalIconCircle1 = new Circle(13);
        hospitalIconCircle1.setFill(Color.web("#e9efff"));

        Text hospitalIcon1 = new Text("▦");
        hospitalIcon1.setStyle("-fx-font-size: 13px;" + "-fx-fill: #1455c0;");

        StackPane hospitalIconPane1 = new StackPane();
        hospitalIconPane1.setPrefSize(28, 28);
        hospitalIconPane1.getChildren().addAll(
                hospitalIconCircle1,
                hospitalIcon1
        );

        Text hospitalName1 = new Text("St. Mary Medical\nCenter");
        hospitalName1.setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        HBox hospitalNameBox1 = new HBox(8);
        hospitalNameBox1.setPrefWidth(175);
        hospitalNameBox1.setAlignment(Pos.CENTER_LEFT);
        hospitalNameBox1.getChildren().addAll(
                hospitalIconPane1,
                hospitalName1
        );

        Text city1 = new Text("North\nRiverside");
        city1.setStyle("-fx-font-size: 12px;" + "-fx-fill: #6b7280;");
        city1.setWrappingWidth(100);

        HBox cityBox1 = new HBox(city1);
        cityBox1.setPrefWidth(110);
        cityBox1.setAlignment(Pos.CENTER_LEFT);

        Text icu1 = new Text("42 / 50");
        icu1.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        HBox icuBox1 = new HBox(icu1);
        icuBox1.setPrefWidth(75);
        icuBox1.setAlignment(Pos.CENTER_LEFT);

        Region availabilityBackground1 = new Region();
        availabilityBackground1.setPrefWidth(45);
        availabilityBackground1.setPrefHeight(5);
        availabilityBackground1.setStyle("-fx-background-color: #e5e7eb;" + "-fx-background-radius: 5px;");

        Region availabilityProgress1 = new Region();
        availabilityProgress1.setPrefWidth(38);
        availabilityProgress1.setPrefHeight(5);
        availabilityProgress1.setStyle("-fx-background-color: #1455c0;" + "-fx-background-radius: 5px;");

        StackPane availabilityPane1 = new StackPane();
        availabilityPane1.setPrefWidth(45);
        availabilityPane1.setAlignment(Pos.CENTER_LEFT);
        availabilityPane1.getChildren().addAll(
                availabilityBackground1,
                availabilityProgress1
        );

        Text availabilityText1 = new Text("Low");
        availabilityText1.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        HBox availabilityBox1 = new HBox(7);
        availabilityBox1.setPrefWidth(125);
        availabilityBox1.setAlignment(Pos.CENTER_LEFT);
        availabilityBox1.getChildren().addAll(
                availabilityPane1,
                availabilityText1
        );

        Label status1 = new Label("ACTIVE");
        status1.setStyle("-fx-background-color: #dcfce7;" + "-fx-text-fill: #16a34a;" + "-fx-font-size: 9px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 12px;" + "-fx-padding: 5px 10px;");

        HBox statusBox1 = new HBox(status1);
        statusBox1.setPrefWidth(110);
        statusBox1.setAlignment(Pos.CENTER_LEFT);

        Button manageButton1 = new Button("Manage");
        manageButton1.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #1455c0;" + "-fx-font-size: 11px;" + "-fx-font-weight: bold;");

        HBox manageBox1 = new HBox(manageButton1);
        manageBox1.setPrefWidth(80);
        manageBox1.setAlignment(Pos.CENTER_LEFT);

        hospitalRow1.getChildren().addAll(
                hospitalNameBox1,
                cityBox1,
                icuBox1,
                availabilityBox1,
                statusBox1,
                manageBox1
        );

        HBox hospitalRow2 = new HBox(10);
        hospitalRow2.setPadding(new Insets(14, 15, 14, 15));
        hospitalRow2.setAlignment(Pos.CENTER_LEFT);
        hospitalRow2.setStyle("-fx-border-color: transparent transparent #e5e7eb transparent;" + "-fx-border-width: 0px 0px 1px 0px;");

        Circle hospitalIconCircle2 = new Circle(13);
        hospitalIconCircle2.setFill(Color.web("#e9efff"));

        Text hospitalIcon2 = new Text("▦");
        hospitalIcon2.setStyle("-fx-font-size: 13px;" + "-fx-fill: #1455c0;");

        StackPane hospitalIconPane2 = new StackPane();
        hospitalIconPane2.setPrefSize(28, 28);
        hospitalIconPane2.getChildren().addAll(
                hospitalIconCircle2,
                hospitalIcon2
        );

        Text hospitalName2 = new Text("Central General\nHospital");
        hospitalName2.setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        HBox hospitalNameBox2 = new HBox(8);
        hospitalNameBox2.setPrefWidth(175);
        hospitalNameBox2.setAlignment(Pos.CENTER_LEFT);
        hospitalNameBox2.getChildren().addAll(
                hospitalIconPane2,
                hospitalName2
        );

        Text city2 = new Text("Downtown");
        city2.setStyle("-fx-font-size: 12px;" + "-fx-fill: #6b7280;");

        HBox cityBox2 = new HBox(city2);
        cityBox2.setPrefWidth(110);
        cityBox2.setAlignment(Pos.CENTER_LEFT);

        Text icu2 = new Text("12 / 85");
        icu2.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        HBox icuBox2 = new HBox(icu2);
        icuBox2.setPrefWidth(75);
        icuBox2.setAlignment(Pos.CENTER_LEFT);

        Region availabilityBackground2 = new Region();
        availabilityBackground2.setPrefWidth(45);
        availabilityBackground2.setPrefHeight(5);
        availabilityBackground2.setStyle("-fx-background-color: #e5e7eb;" + "-fx-background-radius: 5px;");

        Region availabilityProgress2 = new Region();
        availabilityProgress2.setPrefWidth(7);
        availabilityProgress2.setPrefHeight(5);
        availabilityProgress2.setStyle("-fx-background-color: #22c55e;" + "-fx-background-radius: 5px;");

        StackPane availabilityPane2 = new StackPane();
        availabilityPane2.setPrefWidth(45);
        availabilityPane2.setAlignment(Pos.CENTER_LEFT);
        availabilityPane2.getChildren().addAll(
                availabilityBackground2,
                availabilityProgress2
        );

        Text availabilityText2 = new Text("High");
        availabilityText2.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        HBox availabilityBox2 = new HBox(7);
        availabilityBox2.setPrefWidth(125);
        availabilityBox2.setAlignment(Pos.CENTER_LEFT);
        availabilityBox2.getChildren().addAll(
                availabilityPane2,
                availabilityText2
        );

        Label status2 = new Label("ACTIVE");
        status2.setStyle("-fx-background-color: #dcfce7;" + "-fx-text-fill: #16a34a;" + "-fx-font-size: 9px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 12px;" + "-fx-padding: 5px 10px;");

        HBox statusBox2 = new HBox(status2);
        statusBox2.setPrefWidth(110);
        statusBox2.setAlignment(Pos.CENTER_LEFT);

        Button manageButton2 = new Button("Manage");
        manageButton2.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #1455c0;" + "-fx-font-size: 11px;" + "-fx-font-weight: bold;");

        HBox manageBox2 = new HBox(manageButton2);
        manageBox2.setPrefWidth(80);
        manageBox2.setAlignment(Pos.CENTER_LEFT);

        hospitalRow2.getChildren().addAll(
                hospitalNameBox2,
                cityBox2,
                icuBox2,
                availabilityBox2,
                statusBox2,
                manageBox2
        );

        HBox hospitalRow3 = new HBox(10);
        hospitalRow3.setPadding(new Insets(14, 15, 14, 15));
        hospitalRow3.setAlignment(Pos.CENTER_LEFT);
        hospitalRow3.setStyle("-fx-border-color: transparent transparent #e5e7eb transparent;" + "-fx-border-width: 0px 0px 1px 0px;");

        Circle hospitalIconCircle3 = new Circle(13);
        hospitalIconCircle3.setFill(Color.web("#e9efff"));

        Text hospitalIcon3 = new Text("▦");
        hospitalIcon3.setStyle("-fx-font-size: 13px;" + "-fx-fill: #1455c0;");

        StackPane hospitalIconPane3 = new StackPane();
        hospitalIconPane3.setPrefSize(28, 28);
        hospitalIconPane3.getChildren().addAll(
                hospitalIconCircle3,
                hospitalIcon3
        );

        Text hospitalName3 = new Text("Valley Hope Clinic");
        hospitalName3.setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        HBox hospitalNameBox3 = new HBox(8);
        hospitalNameBox3.setPrefWidth(175);
        hospitalNameBox3.setAlignment(Pos.CENTER_LEFT);
        hospitalNameBox3.getChildren().addAll(
                hospitalIconPane3,
                hospitalName3
        );

        Text city3 = new Text("West Hills");
        city3.setStyle("-fx-font-size: 12px;" + "-fx-fill: #6b7280;");

        HBox cityBox3 = new HBox(city3);
        cityBox3.setPrefWidth(110);
        cityBox3.setAlignment(Pos.CENTER_LEFT);

        Text icu3 = new Text("0 / 12");
        icu3.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        HBox icuBox3 = new HBox(icu3);
        icuBox3.setPrefWidth(75);
        icuBox3.setAlignment(Pos.CENTER_LEFT);

        Region availabilityBackground3 = new Region();
        availabilityBackground3.setPrefWidth(45);
        availabilityBackground3.setPrefHeight(5);
        availabilityBackground3.setStyle("-fx-background-color: #e5e7eb;" + "-fx-background-radius: 5px;");

        Region availabilityProgress3 = new Region();
        availabilityProgress3.setPrefWidth(45);
        availabilityProgress3.setPrefHeight(5);
        availabilityProgress3.setStyle("-fx-background-color: #dc2626;" + "-fx-background-radius: 5px;");

        StackPane availabilityPane3 = new StackPane();
        availabilityPane3.setPrefWidth(45);
        availabilityPane3.setAlignment(Pos.CENTER_LEFT);
        availabilityPane3.getChildren().addAll(
                availabilityBackground3,
                availabilityProgress3
        );

        Text availabilityText3 = new Text("Full");
        availabilityText3.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        HBox availabilityBox3 = new HBox(7);
        availabilityBox3.setPrefWidth(125);
        availabilityBox3.setAlignment(Pos.CENTER_LEFT);
        availabilityBox3.getChildren().addAll(
                availabilityPane3,
                availabilityText3
        );

        Label status3 = new Label("CRITICAL");
        status3.setStyle("-fx-background-color: #fee2e2;" + "-fx-text-fill: #dc2626;" + "-fx-font-size: 9px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 12px;" + "-fx-padding: 5px 10px;");

        HBox statusBox3 = new HBox(status3);
        statusBox3.setPrefWidth(110);
        statusBox3.setAlignment(Pos.CENTER_LEFT);

        Button manageButton3 = new Button("Manage");
        manageButton3.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #1455c0;" + "-fx-font-size: 11px;" + "-fx-font-weight: bold;");

        HBox manageBox3 = new HBox(manageButton3);
        manageBox3.setPrefWidth(80);
        manageBox3.setAlignment(Pos.CENTER_LEFT);

        hospitalRow3.getChildren().addAll(
                hospitalNameBox3,
                cityBox3,
                icuBox3,
                availabilityBox3,
                statusBox3,
                manageBox3
        );

        HBox hospitalRow4 = new HBox(10);
        hospitalRow4.setPadding(new Insets(14, 15, 14, 15));
        hospitalRow4.setAlignment(Pos.CENTER_LEFT);
        hospitalRow4.setStyle("-fx-border-color: transparent transparent #e5e7eb transparent;" + "-fx-border-width: 0px 0px 1px 0px;");

        Circle hospitalIconCircle4 = new Circle(13);
        hospitalIconCircle4.setFill(Color.web("#e9efff"));

        Text hospitalIcon4 = new Text("▦");
        hospitalIcon4.setStyle("-fx-font-size: 13px;" + "-fx-fill: #1455c0;");

        StackPane hospitalIconPane4 = new StackPane();
        hospitalIconPane4.setPrefSize(28, 28);
        hospitalIconPane4.getChildren().addAll(
                hospitalIconCircle4,
                hospitalIcon4
        );

        Text hospitalName4 = new Text("Eastside Surgical");
        hospitalName4.setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        HBox hospitalNameBox4 = new HBox(8);
        hospitalNameBox4.setPrefWidth(175);
        hospitalNameBox4.setAlignment(Pos.CENTER_LEFT);
        hospitalNameBox4.getChildren().addAll(
                hospitalIconPane4,
                hospitalName4
        );

        Text city4 = new Text("East Bay");
        city4.setStyle("-fx-font-size: 12px;" + "-fx-fill: #6b7280;");

        HBox cityBox4 = new HBox(city4);
        cityBox4.setPrefWidth(110);
        cityBox4.setAlignment(Pos.CENTER_LEFT);

        Text icu4 = new Text("8 / 24");
        icu4.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        HBox icuBox4 = new HBox(icu4);
        icuBox4.setPrefWidth(75);
        icuBox4.setAlignment(Pos.CENTER_LEFT);

        Region availabilityBackground4 = new Region();
        availabilityBackground4.setPrefWidth(45);
        availabilityBackground4.setPrefHeight(5);
        availabilityBackground4.setStyle("-fx-background-color: #e5e7eb;" + "-fx-background-radius: 5px;");

        Region availabilityProgress4 = new Region();
        availabilityProgress4.setPrefWidth(15);
        availabilityProgress4.setPrefHeight(5);
        availabilityProgress4.setStyle("-fx-background-color: #22c55e;" + "-fx-background-radius: 5px;");

        StackPane availabilityPane4 = new StackPane();
        availabilityPane4.setPrefWidth(45);
        availabilityPane4.setAlignment(Pos.CENTER_LEFT);
        availabilityPane4.getChildren().addAll(
                availabilityBackground4,
                availabilityProgress4
        );

        Text availabilityText4 = new Text("High");
        availabilityText4.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        HBox availabilityBox4 = new HBox(7);
        availabilityBox4.setPrefWidth(125);
        availabilityBox4.setAlignment(Pos.CENTER_LEFT);
        availabilityBox4.getChildren().addAll(
                availabilityPane4,
                availabilityText4
        );

        Label status4 = new Label("MAINTENANCE");
        status4.setStyle("-fx-background-color: #e5e7eb;" + "-fx-text-fill: #6b7280;" + "-fx-font-size: 8px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 12px;" + "-fx-padding: 5px 8px;");

        HBox statusBox4 = new HBox(status4);
        statusBox4.setPrefWidth(110);
        statusBox4.setAlignment(Pos.CENTER_LEFT);

        Button manageButton4 = new Button("Manage");
        manageButton4.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #1455c0;" + "-fx-font-size: 11px;" + "-fx-font-weight: bold;");

        HBox manageBox4 = new HBox(manageButton4);
        manageBox4.setPrefWidth(80);
        manageBox4.setAlignment(Pos.CENTER_LEFT);

        hospitalRow4.getChildren().addAll(
                hospitalNameBox4,
                cityBox4,
                icuBox4,
                availabilityBox4,
                statusBox4,
                manageBox4
        );

        HBox hospitalRow5 = new HBox(10);
        hospitalRow5.setPadding(new Insets(14, 15, 14, 15));
        hospitalRow5.setAlignment(Pos.CENTER_LEFT);

        Circle hospitalIconCircle5 = new Circle(13);
        hospitalIconCircle5.setFill(Color.web("#e9efff"));

        Text hospitalIcon5 = new Text("▦");
        hospitalIcon5.setStyle("-fx-font-size: 13px;" + "-fx-fill: #1455c0;");

        StackPane hospitalIconPane5 = new StackPane();
        hospitalIconPane5.setPrefSize(28, 28);
        hospitalIconPane5.getChildren().addAll(
                hospitalIconCircle5,
                hospitalIcon5
        );

        Text hospitalName5 = new Text("Lakeside Children's");
        hospitalName5.setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        HBox hospitalNameBox5 = new HBox(8);
        hospitalNameBox5.setPrefWidth(175);
        hospitalNameBox5.setAlignment(Pos.CENTER_LEFT);
        hospitalNameBox5.getChildren().addAll(
                hospitalIconPane5,
                hospitalName5
        );

        Text city5 = new Text("South Lake");
        city5.setStyle("-fx-font-size: 12px;" + "-fx-fill: #6b7280;");

        HBox cityBox5 = new HBox(city5);
        cityBox5.setPrefWidth(110);
        cityBox5.setAlignment(Pos.CENTER_LEFT);

        Text icu5 = new Text("18 / 20");
        icu5.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");

        HBox icuBox5 = new HBox(icu5);
        icuBox5.setPrefWidth(75);
        icuBox5.setAlignment(Pos.CENTER_LEFT);

        Region availabilityBackground5 = new Region();
        availabilityBackground5.setPrefWidth(45);
        availabilityBackground5.setPrefHeight(5);
        availabilityBackground5.setStyle("-fx-background-color: #e5e7eb;" + "-fx-background-radius: 5px;");

        Region availabilityProgress5 = new Region();
        availabilityProgress5.setPrefWidth(38);
        availabilityProgress5.setPrefHeight(5);
        availabilityProgress5.setStyle("-fx-background-color: #1455c0;" + "-fx-background-radius: 5px;");

        StackPane availabilityPane5 = new StackPane();
        availabilityPane5.setPrefWidth(45);
        availabilityPane5.setAlignment(Pos.CENTER_LEFT);
        availabilityPane5.getChildren().addAll(
                availabilityBackground5,
                availabilityProgress5
        );

        Text availabilityText5 = new Text("Low");
        availabilityText5.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        HBox availabilityBox5 = new HBox(7);
        availabilityBox5.setPrefWidth(125);
        availabilityBox5.setAlignment(Pos.CENTER_LEFT);
        availabilityBox5.getChildren().addAll(
                availabilityPane5,
                availabilityText5
        );

        Label status5 = new Label("ACTIVE");
        status5.setStyle("-fx-background-color: #dcfce7;" + "-fx-text-fill: #16a34a;" + "-fx-font-size: 9px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 12px;" + "-fx-padding: 5px 10px;");

        HBox statusBox5 = new HBox(status5);
        statusBox5.setPrefWidth(110);
        statusBox5.setAlignment(Pos.CENTER_LEFT);

        Button manageButton5 = new Button("Manage");
        manageButton5.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #1455c0;" + "-fx-font-size: 11px;" + "-fx-font-weight: bold;");

        HBox manageBox5 = new HBox(manageButton5);
        manageBox5.setPrefWidth(80);
        manageBox5.setAlignment(Pos.CENTER_LEFT);

        hospitalRow5.getChildren().addAll(
                hospitalNameBox5,
                cityBox5,
                icuBox5,
                availabilityBox5,
                statusBox5,
                manageBox5
        );

        inventoryCard.getChildren().addAll(
                inventoryHeader,
                tableHeader,
                hospitalRow1,
                hospitalRow2,
                hospitalRow3,
                hospitalRow4,
                hospitalRow5
        );

        Text showingText = new Text("Showing 1-5 of 142 hospitals");
        showingText.setStyle("-fx-font-size: 12px;" + "-fx-fill: #737686;");

        Region paginationSpacer = new Region();
        HBox.setHgrow(paginationSpacer, Priority.ALWAYS);

        Button previousButton = new Button("‹");
        previousButton.setPrefSize(38, 35);
        previousButton.setStyle("-fx-background-color: #ffffff;" + "-fx-border-color: #c3c6d7;" + "-fx-background-radius: 8px;" + "-fx-border-radius: 8px;" + "-fx-font-size: 17px;");

        Button pageOne = new Button("1");
        pageOne.setPrefSize(38, 35);
        pageOne.setStyle("-fx-background-color: #004ac6;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-background-radius: 8px;");

        Button pageTwo = new Button("2");
        pageTwo.setPrefSize(38, 35);
        pageTwo.setStyle("-fx-background-color: #ffffff;" + "-fx-border-color: #c3c6d7;" + "-fx-background-radius: 8px;" + "-fx-border-radius: 8px;");

        Button nextButton = new Button("›");
        nextButton.setPrefSize(38, 35);
        nextButton.setStyle("-fx-background-color: #ffffff;" + "-fx-border-color: #c3c6d7;" + "-fx-background-radius: 8px;" + "-fx-border-radius: 8px;" + "-fx-font-size: 17px;");

        HBox pagination = new HBox(8);
        pagination.setAlignment(Pos.CENTER_LEFT);
        pagination.getChildren().addAll(
                showingText,
                paginationSpacer,
                previousButton,
                pageOne,
                pageTwo,
                nextButton
        );

        mainContent.getChildren().addAll(
                header,
                summaryRow,
                filterBox,
                inventoryCard,
                pagination
        );

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;" + "-fx-background: transparent;");

        VBox finalContent = new VBox(scrollPane);
        finalContent.setStyle("-fx-background-color: #f8f8ff;");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return finalContent;
    }
}