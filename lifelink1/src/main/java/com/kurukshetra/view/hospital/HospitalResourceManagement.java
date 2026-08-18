// package com.kurukshetra.view.hospital;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.ComboBox;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Circle;
// import javafx.scene.text.Text;

// public class HospitalResourceManagement {

//     public VBox getResourceManagement() {

//         VBox mainContent = new VBox(20);
//         mainContent.setPadding(new Insets(25));
//         mainContent.setStyle("-fx-background-color: #faf8ff;");

//         /* =========================================================
//          * PAGE HEADER
//          * ========================================================= */

//         Text heading = new Text("Resource Management");
//         heading.setStyle(
//                 "-fx-font-size: 28px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #004ac6;"
//         );

//         Text subHeading = new Text(
//                 "Real-time status and allocation tracking for hospital critical assets."
//         );
//         subHeading.setStyle(
//                 "-fx-font-size: 14px;" +
//                 "-fx-fill: #737686;"
//         );

//         VBox headingBox = new VBox(5);
//         headingBox.getChildren().addAll(
//                 heading,
//                 subHeading
//         );

//         Button historyButton = new Button("↺   View History");
//         historyButton.setPrefWidth(125);
//         historyButton.setPrefHeight(40);
//         historyButton.setStyle(
//                 "-fx-background-color: #ffffff;" +
//                 "-fx-text-fill: #505f76;" +
//                 "-fx-font-size: 11px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-border-color: #c3c6d7;" +
//                 "-fx-border-radius: 8px;" +
//                 "-fx-background-radius: 8px;"
//         );

//         Button updateButton = new Button("↻   Update Availability");
//         updateButton.setPrefWidth(155);
//         updateButton.setPrefHeight(40);
//         updateButton.setStyle(
//                 "-fx-background-color: #ffffff;" +
//                 "-fx-text-fill: #505f76;" +
//                 "-fx-font-size: 11px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-border-color: #c3c6d7;" +
//                 "-fx-border-radius: 8px;" +
//                 "-fx-background-radius: 8px;"
//         );

//         Button addResourceButton = new Button("+   Add Resource");
//         addResourceButton.setPrefWidth(135);
//         addResourceButton.setPrefHeight(40);
//         addResourceButton.setStyle(
//                 "-fx-background-color: #004ac6;" +
//                 "-fx-text-fill: white;" +
//                 "-fx-font-size: 11px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 8px;"
//         );

//         HBox headerButtons = new HBox(10);
//         headerButtons.setAlignment(Pos.CENTER_RIGHT);
//         headerButtons.getChildren().addAll(
//                 historyButton,
//                 updateButton,
//                 addResourceButton
//         );

//         Region headerSpacer = new Region();
//         HBox.setHgrow(headerSpacer, Priority.ALWAYS);

//         HBox header = new HBox(
//                 headingBox,
//                 headerSpacer,
//                 headerButtons
//         );

//         header.setAlignment(Pos.CENTER_LEFT);

//         /* =========================================================
//          * CRITICAL INVENTORY TITLE
//          * ========================================================= */

//         Text inventoryStatusTitle = new Text("CRITICAL INVENTORY STATUS");
//         inventoryStatusTitle.setStyle(
//                 "-fx-font-size: 11px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #737686;"
//         );

//         Text lastSync = new Text("Last sync: 2 mins ago");
//         lastSync.setStyle(
//                 "-fx-font-size: 11px;" +
//                 "-fx-fill: #737686;"
//         );

//         Region inventoryTitleSpacer = new Region();
//         HBox.setHgrow(inventoryTitleSpacer, Priority.ALWAYS);

//         HBox inventoryTitleRow = new HBox(
//                 inventoryStatusTitle,
//                 inventoryTitleSpacer,
//                 lastSync
//         );

//         inventoryTitleRow.setAlignment(Pos.CENTER_LEFT);

//         /* =========================================================
//          * ICU BED CARD
//          * ========================================================= */

//         VBox icuCard = new VBox(8);
//         icuCard.setPadding(new Insets(15));
//         icuCard.setPrefHeight(160);
//         icuCard.setStyle(
//                 "-fx-background-color: #f8fafc;" +
//                 "-fx-border-color: #e2e8f0;" +
//                 "-fx-border-radius: 12px;" +
//                 "-fx-background-radius: 12px;"
//         );

//         Circle icuCircle = new Circle(20);
//         icuCircle.setFill(Color.web("#e9efff"));

//         Text icuIcon = new Text("▣");
//         icuIcon.setStyle(
//                 "-fx-font-size: 17px;" +
//                 "-fx-fill: #004ac6;"
//         );

//         StackPane icuIconPane = new StackPane(
//                 icuCircle,
//                 icuIcon
//         );
//         icuIconPane.setPrefSize(40, 40);

//         Label icuStatus = new Label("LOW STOCK");
//         icuStatus.setStyle(
//                 "-fx-background-color: #fee2e2;" +
//                 "-fx-text-fill: #ba1a1a;" +
//                 "-fx-font-size: 9px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 5px;" +
//                 "-fx-padding: 5px 7px;"
//         );

//         Region icuTopSpacer = new Region();
//         HBox.setHgrow(icuTopSpacer, Priority.ALWAYS);

//         HBox icuTop = new HBox(
//                 icuIconPane,
//                 icuTopSpacer,
//                 icuStatus
//         );
//         icuTop.setAlignment(Pos.CENTER_LEFT);

//         Text icuTitle = new Text("ICU Beds");
//         icuTitle.setStyle(
//                 "-fx-font-size: 13px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Text icuValue = new Text("42");
//         icuValue.setStyle(
//                 "-fx-font-size: 28px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Text icuTotal = new Text(" / 50 available");
//         icuTotal.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-fill: #737686;"
//         );

//         HBox icuValueBox = new HBox(
//                 icuValue,
//                 icuTotal
//         );
//         icuValueBox.setAlignment(Pos.BASELINE_LEFT);

//         Region icuProgressBackground = new Region();
//         icuProgressBackground.setPrefHeight(5);
//         icuProgressBackground.setStyle(
//                 "-fx-background-color: #e1e2ed;" +
//                 "-fx-background-radius: 5px;"
//         );

//         Region icuProgress = new Region();
//         icuProgress.setPrefHeight(5);
//         icuProgress.setPrefWidth(84);
//         icuProgress.setStyle(
//                 "-fx-background-color: #004ac6;" +
//                 "-fx-background-radius: 5px;"
//         );

//         StackPane icuProgressPane = new StackPane(
//                 icuProgressBackground,
//                 icuProgress
//         );
//         icuProgressPane.setAlignment(Pos.CENTER_LEFT);

//         icuCard.getChildren().addAll(
//                 icuTop,
//                 icuTitle,
//                 icuValueBox,
//                 icuProgressPane
//         );

//         /* =========================================================
//          * EMERGENCY BED CARD
//          * ========================================================= */

//         VBox emergencyCard = new VBox(8);
//         emergencyCard.setPadding(new Insets(15));
//         emergencyCard.setPrefHeight(160);
//         emergencyCard.setStyle(
//                 "-fx-background-color: #f8fafc;" +
//                 "-fx-border-color: #e2e8f0;" +
//                 "-fx-border-radius: 12px;" +
//                 "-fx-background-radius: 12px;"
//         );

//         Circle emergencyCircle = new Circle(20);
//         emergencyCircle.setFill(Color.web("#fff0e8"));

//         Text emergencyIcon = new Text("!");
//         emergencyIcon.setStyle(
//                 "-fx-font-size: 18px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #bc4800;"
//         );

//         StackPane emergencyIconPane = new StackPane(
//                 emergencyCircle,
//                 emergencyIcon
//         );
//         emergencyIconPane.setPrefSize(40, 40);

//         Label emergencyStatus = new Label("WARNING");
//         emergencyStatus.setStyle(
//                 "-fx-background-color: #ffedd5;" +
//                 "-fx-text-fill: #c2410c;" +
//                 "-fx-font-size: 9px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 5px;" +
//                 "-fx-padding: 5px 7px;"
//         );

//         Region emergencyTopSpacer = new Region();
//         HBox.setHgrow(emergencyTopSpacer, Priority.ALWAYS);

//         HBox emergencyTop = new HBox(
//                 emergencyIconPane,
//                 emergencyTopSpacer,
//                 emergencyStatus
//         );
//         emergencyTop.setAlignment(Pos.CENTER_LEFT);

//         Text emergencyTitle = new Text("Emergency Beds");
//         emergencyTitle.setStyle(
//                 "-fx-font-size: 13px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Text emergencyValue = new Text("18");
//         emergencyValue.setStyle(
//                 "-fx-font-size: 28px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Text emergencyTotal = new Text(" / 30 available");
//         emergencyTotal.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-fill: #737686;"
//         );

//         HBox emergencyValueBox = new HBox(
//                 emergencyValue,
//                 emergencyTotal
//         );
//         emergencyValueBox.setAlignment(Pos.BASELINE_LEFT);

//         Region emergencyProgressBackground = new Region();
//         emergencyProgressBackground.setPrefHeight(5);
//         emergencyProgressBackground.setStyle(
//                 "-fx-background-color: #e1e2ed;" +
//                 "-fx-background-radius: 5px;"
//         );

//         Region emergencyProgress = new Region();
//         emergencyProgress.setPrefHeight(5);
//         emergencyProgress.setPrefWidth(60);
//         emergencyProgress.setStyle(
//                 "-fx-background-color: #bc4800;" +
//                 "-fx-background-radius: 5px;"
//         );

//         StackPane emergencyProgressPane = new StackPane(
//                 emergencyProgressBackground,
//                 emergencyProgress
//         );
//         emergencyProgressPane.setAlignment(Pos.CENTER_LEFT);

//         emergencyCard.getChildren().addAll(
//                 emergencyTop,
//                 emergencyTitle,
//                 emergencyValueBox,
//                 emergencyProgressPane
//         );

//         /* =========================================================
//          * GENERAL BED CARD
//          * ========================================================= */

//         VBox generalCard = new VBox(8);
//         generalCard.setPadding(new Insets(15));
//         generalCard.setPrefHeight(160);
//         generalCard.setStyle(
//                 "-fx-background-color: #f8fafc;" +
//                 "-fx-border-color: #e2e8f0;" +
//                 "-fx-border-radius: 12px;" +
//                 "-fx-background-radius: 12px;"
//         );

//         Circle generalCircle = new Circle(20);
//         generalCircle.setFill(Color.web("#e7edf5"));

//         Text generalIcon = new Text("▣");
//         generalIcon.setStyle(
//                 "-fx-font-size: 17px;" +
//                 "-fx-fill: #505f76;"
//         );

//         StackPane generalIconPane = new StackPane(
//                 generalCircle,
//                 generalIcon
//         );
//         generalIconPane.setPrefSize(40, 40);

//         Label generalStatus = new Label("OPTIMAL");
//         generalStatus.setStyle(
//                 "-fx-background-color: #dcfce7;" +
//                 "-fx-text-fill: #15803d;" +
//                 "-fx-font-size: 9px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 5px;" +
//                 "-fx-padding: 5px 7px;"
//         );

//         Region generalTopSpacer = new Region();
//         HBox.setHgrow(generalTopSpacer, Priority.ALWAYS);

//         HBox generalTop = new HBox(
//                 generalIconPane,
//                 generalTopSpacer,
//                 generalStatus
//         );
//         generalTop.setAlignment(Pos.CENTER_LEFT);

//         Text generalTitle = new Text("General Beds");
//         generalTitle.setStyle(
//                 "-fx-font-size: 13px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Text generalValue = new Text("312");
//         generalValue.setStyle(
//                 "-fx-font-size: 28px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Text generalTotal = new Text(" / 400 available");
//         generalTotal.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-fill: #737686;"
//         );

//         HBox generalValueBox = new HBox(
//                 generalValue,
//                 generalTotal
//         );
//         generalValueBox.setAlignment(Pos.BASELINE_LEFT);

//         Region generalProgressBackground = new Region();
//         generalProgressBackground.setPrefHeight(5);
//         generalProgressBackground.setStyle(
//                 "-fx-background-color: #e1e2ed;" +
//                 "-fx-background-radius: 5px;"
//         );

//         Region generalProgress = new Region();
//         generalProgress.setPrefHeight(5);
//         generalProgress.setPrefWidth(78);
//         generalProgress.setStyle(
//                 "-fx-background-color: #505f76;" +
//                 "-fx-background-radius: 5px;"
//         );

//         StackPane generalProgressPane = new StackPane(
//                 generalProgressBackground,
//                 generalProgress
//         );
//         generalProgressPane.setAlignment(Pos.CENTER_LEFT);

//         generalCard.getChildren().addAll(
//                 generalTop,
//                 generalTitle,
//                 generalValueBox,
//                 generalProgressPane
//         );

//         /* =========================================================
//          * VENTILATOR CARD
//          * ========================================================= */

//         VBox ventilatorCard = new VBox(8);
//         ventilatorCard.setPadding(new Insets(15));
//         ventilatorCard.setPrefHeight(160);
//         ventilatorCard.setStyle(
//                 "-fx-background-color: #f8fafc;" +
//                 "-fx-border-color: #e2e8f0;" +
//                 "-fx-border-radius: 12px;" +
//                 "-fx-background-radius: 12px;"
//         );

//         Circle ventilatorCircle = new Circle(20);
//         ventilatorCircle.setFill(Color.web("#e9efff"));

//         Text ventilatorIcon = new Text("≈");
//         ventilatorIcon.setStyle(
//                 "-fx-font-size: 20px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #004ac6;"
//         );

//         StackPane ventilatorIconPane = new StackPane(
//                 ventilatorCircle,
//                 ventilatorIcon
//         );
//         ventilatorIconPane.setPrefSize(40, 40);

//         Label ventilatorStatus = new Label("OPERATIONAL");
//         ventilatorStatus.setStyle(
//                 "-fx-background-color: #dcfce7;" +
//                 "-fx-text-fill: #15803d;" +
//                 "-fx-font-size: 9px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 5px;" +
//                 "-fx-padding: 5px 7px;"
//         );

//         Region ventilatorTopSpacer = new Region();
//         HBox.setHgrow(ventilatorTopSpacer, Priority.ALWAYS);

//         HBox ventilatorTop = new HBox(
//                 ventilatorIconPane,
//                 ventilatorTopSpacer,
//                 ventilatorStatus
//         );
//         ventilatorTop.setAlignment(Pos.CENTER_LEFT);

//         Text ventilatorTitle = new Text("Ventilators");
//         ventilatorTitle.setStyle(
//                 "-fx-font-size: 13px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Text ventilatorValue = new Text("12");
//         ventilatorValue.setStyle(
//                 "-fx-font-size: 28px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Text ventilatorTotal = new Text(" / 15 available");
//         ventilatorTotal.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-fill: #737686;"
//         );

//         HBox ventilatorValueBox = new HBox(
//                 ventilatorValue,
//                 ventilatorTotal
//         );
//         ventilatorValueBox.setAlignment(Pos.BASELINE_LEFT);

//         Region ventilatorProgressBackground = new Region();
//         ventilatorProgressBackground.setPrefHeight(5);
//         ventilatorProgressBackground.setStyle(
//                 "-fx-background-color: #e1e2ed;" +
//                 "-fx-background-radius: 5px;"
//         );

//         Region ventilatorProgress = new Region();
//         ventilatorProgress.setPrefHeight(5);
//         ventilatorProgress.setPrefWidth(80);
//         ventilatorProgress.setStyle(
//                 "-fx-background-color: #004ac6;" +
//                 "-fx-background-radius: 5px;"
//         );

//         StackPane ventilatorProgressPane = new StackPane(
//                 ventilatorProgressBackground,
//                 ventilatorProgress
//         );
//         ventilatorProgressPane.setAlignment(Pos.CENTER_LEFT);

//         ventilatorCard.getChildren().addAll(
//                 ventilatorTop,
//                 ventilatorTitle,
//                 ventilatorValueBox,
//                 ventilatorProgressPane
//         );

//         /* =========================================================
//          * OXYGEN CARD
//          * ========================================================= */

//         VBox oxygenCard = new VBox(8);
//         oxygenCard.setPadding(new Insets(15));
//         oxygenCard.setPrefHeight(160);
//         oxygenCard.setStyle(
//                 "-fx-background-color: #f8fafc;" +
//                 "-fx-border-color: #e2e8f0;" +
//                 "-fx-border-radius: 12px;" +
//                 "-fx-background-radius: 12px;"
//         );

//         Circle oxygenCircle = new Circle(20);
//         oxygenCircle.setFill(Color.web("#e7edf5"));

//         Text oxygenIcon = new Text("O₂");
//         oxygenIcon.setStyle(
//                 "-fx-font-size: 14px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #505f76;"
//         );

//         StackPane oxygenIconPane = new StackPane(
//                 oxygenCircle,
//                 oxygenIcon
//         );
//         oxygenIconPane.setPrefSize(40, 40);

//         Label oxygenStatus = new Label("SAFE RANGE");
//         oxygenStatus.setStyle(
//                 "-fx-background-color: #dcfce7;" +
//                 "-fx-text-fill: #15803d;" +
//                 "-fx-font-size: 9px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 5px;" +
//                 "-fx-padding: 5px 7px;"
//         );

//         Region oxygenTopSpacer = new Region();
//         HBox.setHgrow(oxygenTopSpacer, Priority.ALWAYS);

//         HBox oxygenTop = new HBox(
//                 oxygenIconPane,
//                 oxygenTopSpacer,
//                 oxygenStatus
//         );
//         oxygenTop.setAlignment(Pos.CENTER_LEFT);

//         Text oxygenTitle = new Text("Oxygen Reserves");
//         oxygenTitle.setStyle(
//                 "-fx-font-size: 13px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Text oxygenValue = new Text("98");
//         oxygenValue.setStyle(
//                 "-fx-font-size: 28px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Text oxygenTotal = new Text(" % capacity");
//         oxygenTotal.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-fill: #737686;"
//         );

//         HBox oxygenValueBox = new HBox(
//                 oxygenValue,
//                 oxygenTotal
//         );
//         oxygenValueBox.setAlignment(Pos.BASELINE_LEFT);

//         Region oxygenProgressBackground = new Region();
//         oxygenProgressBackground.setPrefHeight(5);
//         oxygenProgressBackground.setStyle(
//                 "-fx-background-color: #e1e2ed;" +
//                 "-fx-background-radius: 5px;"
//         );

//         Region oxygenProgress = new Region();
//         oxygenProgress.setPrefHeight(5);
//         oxygenProgress.setPrefWidth(98);
//         oxygenProgress.setStyle(
//                 "-fx-background-color: #505f76;" +
//                 "-fx-background-radius: 5px;"
//         );

//         StackPane oxygenProgressPane = new StackPane(
//                 oxygenProgressBackground,
//                 oxygenProgress
//         );
//         oxygenProgressPane.setAlignment(Pos.CENTER_LEFT);

//         oxygenCard.getChildren().addAll(
//                 oxygenTop,
//                 oxygenTitle,
//                 oxygenValueBox,
//                 oxygenProgressPane
//         );

//         /* =========================================================
//          * BLOOD UNITS CARD
//          * ========================================================= */

//         VBox bloodCard = new VBox(8);
//         bloodCard.setPadding(new Insets(15));
//         bloodCard.setPrefHeight(160);
//         bloodCard.setStyle(
//                 "-fx-background-color: #f8fafc;" +
//                 "-fx-border-color: #e2e8f0;" +
//                 "-fx-border-radius: 12px;" +
//                 "-fx-background-radius: 12px;"
//         );

//         Circle bloodCircle = new Circle(20);
//         bloodCircle.setFill(Color.web("#fee2e2"));

//         Text bloodIcon = new Text("♥");
//         bloodIcon.setStyle(
//                 "-fx-font-size: 17px;" +
//                 "-fx-fill: #ba1a1a;"
//         );

//         StackPane bloodIconPane = new StackPane(
//                 bloodCircle,
//                 bloodIcon
//         );
//         bloodIconPane.setPrefSize(40, 40);

//         Text bloodTitle = new Text("Blood Units");
//         bloodTitle.setStyle(
//                 "-fx-font-size: 13px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Region bloodSpacer = new Region();
//         HBox.setHgrow(bloodSpacer, Priority.ALWAYS);

//         HBox bloodTop = new HBox(
//                 bloodIconPane,
//                 bloodSpacer,
//                 bloodTitle
//         );
//         bloodTop.setAlignment(Pos.CENTER_LEFT);

//         HBox bloodGroups = new HBox(5);
//         bloodGroups.setAlignment(Pos.CENTER);

//         bloodGroups.getChildren().addAll(
//                 createBloodGroup("O-", "4u", true),
//                 createBloodGroup("A+", "22u", false),
//                 createBloodGroup("B-", "12u", false),
//                 createBloodGroup("AB+", "18u", false)
//         );

//         Text bloodWarning = new Text(
//                 "O- Negative critical levels detected"
//         );
//         bloodWarning.setStyle(
//                 "-fx-font-size: 10px;" +
//                 "-fx-fill: #737686;"
//         );

//         bloodCard.getChildren().addAll(
//                 bloodTop,
//                 bloodGroups,
//                 bloodWarning
//         );

//         /* =========================================================
//          * RESOURCE CARD GRID
//          * ========================================================= */

//         HBox resourceRow1 = new HBox(15);
//         resourceRow1.getChildren().addAll(
//                 icuCard,
//                 emergencyCard,
//                 generalCard
//         );

//         HBox.setHgrow(icuCard, Priority.ALWAYS);
//         HBox.setHgrow(emergencyCard, Priority.ALWAYS);
//         HBox.setHgrow(generalCard, Priority.ALWAYS);

//         HBox resourceRow2 = new HBox(15);
//         resourceRow2.getChildren().addAll(
//                 ventilatorCard,
//                 oxygenCard,
//                 bloodCard
//         );

//         HBox.setHgrow(ventilatorCard, Priority.ALWAYS);
//         HBox.setHgrow(oxygenCard, Priority.ALWAYS);
//         HBox.setHgrow(bloodCard, Priority.ALWAYS);

//         VBox resourceStatusSection = new VBox(12);
//         resourceStatusSection.getChildren().addAll(
//                 inventoryTitleRow,
//                 resourceRow1,
//                 resourceRow2
//         );

//         /* =========================================================
//          * BED OCCUPANCY DISTRIBUTION
//          * ========================================================= */

//         VBox occupancyCard = new VBox(15);
//         occupancyCard.setPadding(new Insets(20));
//         occupancyCard.setPrefHeight(340);
//         occupancyCard.setStyle(
//                 "-fx-background-color: #f8fafc;" +
//                 "-fx-border-color: #e2e8f0;" +
//                 "-fx-border-radius: 12px;" +
//                 "-fx-background-radius: 12px;"
//         );

//         Text occupancyTitle = new Text("Bed Occupancy Distribution");
//         occupancyTitle.setStyle(
//                 "-fx-font-size: 18px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         StackPane occupancyCircle = createOccupancyCircle();

//         VBox occupancyChartBox = new VBox(
//                 occupancyTitle,
//                 occupancyCircle
//         );
//         occupancyChartBox.setAlignment(Pos.CENTER);
//         occupancyChartBox.setPrefWidth(330);

//         VBox occupancyDetails = new VBox(12);
//         occupancyDetails.getChildren().addAll(
//                 createOccupancyRow("ICU Occupancy", "84%", "#004ac6"),
//                 createOccupancyRow("Emergency Ward", "60%", "#2563eb"),
//                 createOccupancyRow("General Ward", "78%", "#505f76")
//         );

//         Region occupancyDetailSpacer = new Region();
//         VBox.setVgrow(occupancyDetailSpacer, Priority.ALWAYS);

//         Text occupancyMessage = new Text(
//                 "Capacity reaching threshold in ICU. Recommended: Redirect non-critical cases."
//         );
//         occupancyMessage.setWrappingWidth(300);
//         occupancyMessage.setStyle(
//                 "-fx-font-size: 11px;" +
//                 "-fx-fill: #737686;"
//         );

//         occupancyDetails.getChildren().addAll(
//                 occupancyDetailSpacer,
//                 occupancyMessage
//         );

//         HBox occupancyContent = new HBox(30);
//         occupancyContent.setAlignment(Pos.CENTER);
//         occupancyContent.getChildren().addAll(
//                 occupancyChartBox,
//                 occupancyDetails
//         );

//         occupancyCard.getChildren().add(
//                 occupancyContent
//         );

//         /* =========================================================
//          * ICU USAGE TREND
//          * ========================================================= */

//         VBox trendCard = new VBox(15);
//         trendCard.setPadding(new Insets(20));
//         trendCard.setPrefHeight(516);
//         trendCard.setStyle(
//                 "-fx-background-color: #f8fafc;" +
//                 "-fx-border-color: #e2e8f0;" +
//                 "-fx-border-radius: 12px;" +
//                 "-fx-background-radius: 12px;"
//         );

//         Text trendTitle = new Text("ICU Usage Trend (7D)");
//         trendTitle.setStyle(
//                 "-fx-font-size: 18px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         ComboBox<String> trendCombo = new ComboBox<>();
//         trendCombo.getItems().addAll(
//                 "Weekly",
//                 "Monthly"
//         );
//         trendCombo.setValue("Weekly");
//         trendCombo.setPrefWidth(100);
//         trendCombo.setPrefHeight(32);

//         Region trendHeaderSpacer = new Region();
//         HBox.setHgrow(trendHeaderSpacer, Priority.ALWAYS);

//         HBox trendHeader = new HBox(
//                 trendTitle,
//                 trendHeaderSpacer,
//                 trendCombo
//         );
//         trendHeader.setAlignment(Pos.CENTER_LEFT);

//         HBox trendBars = new HBox(10);
//         trendBars.setAlignment(Pos.BOTTOM_CENTER);
//         trendBars.setPrefHeight(260);

//         trendBars.getChildren().addAll(
//                 createTrendBar("MON", 40),
//                 createTrendBar("TUE", 65),
//                 createTrendBar("WED", 55),
//                 createTrendBar("THU", 88),
//                 createTrendBar("FRI", 92),
//                 createTrendBar("SAT", 70),
//                 createTrendBar("SUN", 84)
//         );

//         VBox trendStatistics = new VBox(12);

//         Text averageLabel = new Text("Average Occupancy");
//         averageLabel.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

//         Text averageValue = new Text("76.4%");
//         averageValue.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

//         trendStatistics.getChildren().add(
//                 createStatisticRow(averageLabel, averageValue)
//         );

//         Text peakLabel = new Text("Peak Demand Day");
//         peakLabel.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

//         Text peakValue = new Text("Friday");
//         peakValue.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #004ac6;"
//         );

//         trendStatistics.getChildren().add(
//                 createStatisticRow(peakLabel, peakValue)
//         );

//         Text efficiencyLabel = new Text("Efficiency Delta");
//         efficiencyLabel.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

//         Text efficiencyValue = new Text("+12% vs last week");
//         efficiencyValue.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #16a34a;"
//         );

//         trendStatistics.getChildren().add(
//                 createStatisticRow(efficiencyLabel, efficiencyValue)
//         );

//         trendCard.getChildren().addAll(
//                 trendHeader,
//                 trendBars,
//                 trendStatistics
//         );

//         /* =========================================================
//          * RECENT ALLOCATION LOG
//          * ========================================================= */

//         VBox logCard = new VBox(15);
//         logCard.setPadding(new Insets(20));
//         logCard.setMinHeight(300);
//         logCard.setStyle(
//                 "-fx-background-color: #f8fafc;" +
//                 "-fx-border-color: #e2e8f0;" +
//                 "-fx-border-radius: 12px;" +
//                 "-fx-background-radius: 12px;"
//         );

//         Text logTitle = new Text("Recent Allocation Log");
//         logTitle.setStyle(
//                 "-fx-font-size: 18px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         logCard.getChildren().addAll(
//                 logTitle,
//                 createLogRow(
//                         "#004ac6",
//                         "ICU Bed #12 allocated to Patient P-908",
//                         "12:45 PM • Unit A-4"
//                 ),
//                 createLogRow(
//                         "#bc4800",
//                         "Oxygen cylinder refill requested",
//                         "11:30 AM • Storage West"
//                 ),
//                 createLogRow(
//                         "#505f76",
//                         "General Bed #242 vacated",
//                         "10:15 AM • Ward C"
//                 )
//         );

//         VBox analyticsColumn = new VBox(20);
//         analyticsColumn.getChildren().addAll(
//                 trendCard,
//                 logCard
//         );

//         HBox analyticsSection = new HBox(20);
//         analyticsSection.getChildren().addAll(
//                 resourceStatusSection,
//                 analyticsColumn
//         );

//         HBox.setHgrow(resourceStatusSection, Priority.ALWAYS);
//         HBox.setHgrow(analyticsColumn, Priority.ALWAYS);

//         /* =========================================================
//          * ASSET REGISTRY
//          * ========================================================= */

//         VBox assetRegistry = new VBox();
//         assetRegistry.setStyle(
//                 "-fx-background-color: #ffffff;" +
//                 "-fx-background-radius: 12px;" +
//                 "-fx-border-color: #e2e8f0;" +
//                 "-fx-border-radius: 12px;"
//         );

//         Text assetTitle = new Text("Asset Registry");
//         assetTitle.setStyle(
//                 "-fx-font-size: 18px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Button filterButton = new Button("☷");
//         filterButton.setPrefSize(38, 35);
//         filterButton.setStyle(
//                 "-fx-background-color: transparent;" +
//                 "-fx-text-fill: #505f76;" +
//                 "-fx-font-size: 16px;"
//         );

//         Button downloadButton = new Button("↓");
//         downloadButton.setPrefSize(38, 35);
//         downloadButton.setStyle(
//                 "-fx-background-color: transparent;" +
//                 "-fx-text-fill: #505f76;" +
//                 "-fx-font-size: 16px;"
//         );

//         HBox assetActions = new HBox(5);
//         assetActions.setAlignment(Pos.CENTER_RIGHT);
//         assetActions.getChildren().addAll(
//                 filterButton,
//                 downloadButton
//         );

//         Region assetHeaderSpacer = new Region();
//         HBox.setHgrow(assetHeaderSpacer, Priority.ALWAYS);

//         HBox assetHeader = new HBox(
//                 assetTitle,
//                 assetHeaderSpacer,
//                 assetActions
//         );

//         assetHeader.setPadding(new Insets(15));
//         assetHeader.setAlignment(Pos.CENTER_LEFT);
//         assetHeader.setStyle(
//                 "-fx-background-color: #f3f3fe;" +
//                 "-fx-border-color: transparent transparent #e2e8f0 transparent;" +
//                 "-fx-border-width: 0px 0px 1px 0px;"
//         );

//         HBox assetTableHeader = new HBox(10);
//         assetTableHeader.setPadding(new Insets(12, 15, 12, 15));
//         assetTableHeader.setAlignment(Pos.CENTER_LEFT);
//         assetTableHeader.setStyle(
//                 "-fx-background-color: #f1f5f9;"
//         );

//         assetTableHeader.getChildren().addAll(
//                 createTableHeader("RESOURCE ID", 120),
//                 createTableHeader("CATEGORY", 145),
//                 createTableHeader("LOCATION", 150),
//                 createTableHeader("STATUS", 120),
//                 createTableHeader("LAST SERVICE", 150),
//                 createTableHeader("ACTIONS", 90)
//         );

//         HBox assetRow1 = createAssetRow(
//                 "VT-049",
//                 "Ventilator",
//                 "ICU Bay 4",
//                 "IN USE",
//                 "Oct 12, 2023",
//                 "#dcfce7",
//                 "#15803d"
//         );

//         HBox assetRow2 = createAssetRow(
//                 "IB-201",
//                 "ICU Bed",
//                 "ICU Bay 2",
//                 "AVAILABLE",
//                 "Nov 05, 2023",
//                 "#dbeafe",
//                 "#2563eb"
//         );

//         HBox assetRow3 = createAssetRow(
//                 "OX-912",
//                 "Oxygen Conc.",
//                 "Emergency A",
//                 "MAINTENANCE",
//                 "Oct 28, 2023",
//                 "#fee2e2",
//                 "#ba1a1a"
//         );

//         assetRegistry.getChildren().addAll(
//                 assetHeader,
//                 assetTableHeader,
//                 assetRow1,
//                 assetRow2,
//                 assetRow3
//         );

//         /* =========================================================
//          * FINAL CONTENT
//          * ========================================================= */

//         mainContent.getChildren().addAll(
//                 header,
//                 analyticsSection,
//                 occupancyCard,
//                 assetRegistry
//         );

//         ScrollPane scrollPane = new ScrollPane(mainContent);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setHbarPolicy(
//                 ScrollPane.ScrollBarPolicy.NEVER
//         );
//         scrollPane.setStyle(
//                 "-fx-background-color: transparent;" +
//                 "-fx-background: transparent;"
//         );

//         VBox finalContent = new VBox(scrollPane);
//         finalContent.setStyle(
//                 "-fx-background-color: #faf8ff;"
//         );

//         VBox.setVgrow(
//                 scrollPane,
//                 Priority.ALWAYS
//         );

//         return finalContent;
//     }

//     /* =============================================================
//      * BLOOD GROUP CARD
//      * ============================================================= */

//     private VBox createBloodGroup(
//             String group,
//             String units,
//             boolean critical
//     ) {

//         VBox box = new VBox(2);
//         box.setAlignment(Pos.CENTER);
//         box.setPrefWidth(55);
//         box.setPadding(new Insets(5));

//         if (critical) {

//             box.setStyle(
//                     "-fx-background-color: #fee2e2;" +
//                     "-fx-border-color: #fecaca;" +
//                     "-fx-border-radius: 5px;" +
//                     "-fx-background-radius: 5px;"
//             );

//         } else {

//             box.setStyle(
//                     "-fx-background-color: #ffffff;" +
//                     "-fx-border-color: #c3c6d7;" +
//                     "-fx-border-radius: 5px;" +
//                     "-fx-background-radius: 5px;"
//             );
//         }

//         Label groupLabel = new Label(group);
//         groupLabel.setStyle(
//                 "-fx-font-size: 9px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-text-fill: " +
//                 (critical ? "#ba1a1a;" : "#737686;")
//         );

//         Label unitsLabel = new Label(units);
//         unitsLabel.setStyle(
//                 "-fx-font-size: 11px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-text-fill: " +
//                 (critical ? "#ba1a1a;" : "#191b23;")
//         );

//         box.getChildren().addAll(
//                 groupLabel,
//                 unitsLabel
//         );

//         return box;
//     }

//     /* =============================================================
//      * OCCUPANCY CIRCLE
//      * ============================================================= */

//     private StackPane createOccupancyCircle() {

//         Circle backgroundCircle = new Circle(
//                 80,
//                 Color.TRANSPARENT
//         );

//         backgroundCircle.setStroke(
//                 Color.web("#e1e2ed")
//         );
//         backgroundCircle.setStrokeWidth(20);

//         Circle icuCircle = new Circle(
//                 80,
//                 Color.TRANSPARENT
//         );

//         icuCircle.setStroke(
//                 Color.web("#004ac6")
//         );
//         icuCircle.setStrokeWidth(20);
//         icuCircle.getStrokeDashArray().addAll(
//                 420.0,
//                 100.0
//         );

//         Circle emergencyCircle = new Circle(
//                 80,
//                 Color.TRANSPARENT
//         );

//         emergencyCircle.setStroke(
//                 Color.web("#2563eb")
//         );
//         emergencyCircle.setStrokeWidth(20);
//         emergencyCircle.getStrokeDashArray().addAll(
//                 300.0,
//                 220.0
//         );

//         Circle generalCircle = new Circle(
//                 80,
//                 Color.TRANSPARENT
//         );

//         generalCircle.setStroke(
//                 Color.web("#505f76")
//         );
//         generalCircle.setStrokeWidth(20);
//         generalCircle.getStrokeDashArray().addAll(
//                 180.0,
//                 340.0
//         );

//         Text percentage = new Text("82%");
//         percentage.setStyle(
//                 "-fx-font-size: 28px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #191b23;"
//         );

//         Text total = new Text("TOTAL");
//         total.setStyle(
//                 "-fx-font-size: 10px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-fill: #737686;"
//         );

//         VBox centerText = new VBox(
//                 2,
//                 percentage,
//                 total
//         );
//         centerText.setAlignment(Pos.CENTER);

//         StackPane pane = new StackPane(
//                 backgroundCircle,
//                 icuCircle,
//                 emergencyCircle,
//                 generalCircle,
//                 centerText
//         );

//         pane.setPrefSize(200, 200);

//         return pane;
//     }

//     /* =============================================================
//      * OCCUPANCY ROW
//      * ============================================================= */

//     private HBox createOccupancyRow(
//             String name,
//             String value,
//             String circleColor
//     ) {

//         Circle circle = new Circle(
//                 6,
//                 Color.web(circleColor)
//         );

//         Label nameLabel = new Label(name);
//         nameLabel.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-text-fill: #191b23;"
//         );

//         HBox left = new HBox(
//                 8,
//                 circle,
//                 nameLabel
//         );
//         left.setAlignment(Pos.CENTER_LEFT);

//         Label valueLabel = new Label(value);
//         valueLabel.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-text-fill: #191b23;"
//         );

//         Region spacer = new Region();
//         HBox.setHgrow(
//                 spacer,
//                 Priority.ALWAYS
//         );

//         HBox row = new HBox(
//                 left,
//                 spacer,
//                 valueLabel
//         );

//         row.setPadding(
//                 new Insets(10)
//         );

//         row.setAlignment(
//                 Pos.CENTER_LEFT
//         );

//         row.setStyle(
//                 "-fx-background-color: #ffffff;" +
//                 "-fx-border-color: #c3c6d7;" +
//                 "-fx-border-radius: 6px;" +
//                 "-fx-background-radius: 6px;"
//         );

//         return row;
//     }

//     /* =============================================================
//      * TREND BAR
//      * ============================================================= */

//     private VBox createTrendBar(
//             String day,
//             int percentage
//     ) {

//         Region background = new Region();
//         background.setPrefWidth(25);
//         background.setPrefHeight(180);
//         background.setStyle(
//                 "-fx-background-color: #e1e2ed;" +
//                 "-fx-background-radius: 5px;"
//         );

//         Region bar = new Region();
//         bar.setPrefWidth(25);
//         bar.setPrefHeight(
//                 percentage * 1.6
//         );
//         bar.setStyle(
//                 "-fx-background-color: #b4c5ff;" +
//                 "-fx-background-radius: 5px 5px 0px 0px;"
//         );

//         StackPane barPane = new StackPane(
//                 background,
//                 bar
//         );

//         barPane.setAlignment(
//                 Pos.BOTTOM_CENTER
//         );

//         Label dayLabel = new Label(day);
//         dayLabel.setStyle(
//                 "-fx-font-size: 9px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-text-fill: #737686;"
//         );

//         VBox box = new VBox(
//                 6,
//                 barPane,
//                 dayLabel
//         );

//         box.setAlignment(
//                 Pos.BOTTOM_CENTER
//         );

//         return box;
//     }

//     /* =============================================================
//      * STATISTIC ROW
//      * ============================================================= */

//     private HBox createStatisticRow(
//             Text left,
//             Text right
//     ) {

//         Region spacer = new Region();
//         HBox.setHgrow(
//                 spacer,
//                 Priority.ALWAYS
//         );

//         HBox row = new HBox(
//                 left,
//                 spacer,
//                 right
//         );

//         row.setAlignment(
//                 Pos.CENTER_LEFT
//         );

//         return row;
//     }

//     /* =============================================================
//      * LOG ROW
//      * ============================================================= */

//     private HBox createLogRow(
//             String color,
//             String message,
//             String time
//     ) {

//         Circle dot = new Circle(
//                 4,
//                 Color.web(color)
//         );

//         VBox textBox = new VBox(3);

//         Text messageText = new Text(message);
//         messageText.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-fill: #191b23;"
//         );

//         Text timeText = new Text(time);
//         timeText.setStyle(
//                 "-fx-font-size: 10px;" +
//                 "-fx-fill: #737686;"
//         );

//         textBox.getChildren().addAll(
//                 messageText,
//                 timeText
//         );

//         HBox row = new HBox(
//                 10,
//                 dot,
//                 textBox
//         );

//         row.setAlignment(
//                 Pos.TOP_LEFT
//         );

//         return row;
//     }

//     /* =============================================================
//      * TABLE HEADER
//      * ============================================================= */

//     private Label createTableHeader(
//             String text,
//             double width
//     ) {

//         Label label = new Label(text);
//         label.setPrefWidth(width);
//         label.setStyle(
//                 "-fx-font-size: 10px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-text-fill: #505f76;"
//         );

//         return label;
//     }

//     /* =============================================================
//      * ASSET ROW
//      * ============================================================= */

//     private HBox createAssetRow(
//             String id,
//             String category,
//             String location,
//             String status,
//             String lastService,
//             String statusBackground,
//             String statusColor
//     ) {

//         HBox row = new HBox(10);

//         row.setPadding(
//                 new Insets(13, 15, 13, 15)
//         );

//         row.setAlignment(
//                 Pos.CENTER_LEFT
//         );

//         row.setStyle(
//                 "-fx-border-color: transparent transparent #e2e8f0 transparent;" +
//                 "-fx-border-width: 0px 0px 1px 0px;"
//         );

//         Label idLabel = new Label(id);
//         idLabel.setPrefWidth(120);
//         idLabel.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-text-fill: #191b23;"
//         );

//         Label categoryLabel = new Label(category);
//         categoryLabel.setPrefWidth(145);
//         categoryLabel.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-text-fill: #191b23;"
//         );

//         Label locationLabel = new Label(location);
//         locationLabel.setPrefWidth(150);
//         locationLabel.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-text-fill: #191b23;"
//         );

//         Label statusLabel = new Label(status);
//         statusLabel.setStyle(
//                 "-fx-background-color: " + statusBackground + ";" +
//                 "-fx-text-fill: " + statusColor + ";" +
//                 "-fx-font-size: 9px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 5px;" +
//                 "-fx-padding: 5px 8px;"
//         );

//         HBox statusBox = new HBox(statusLabel);
//         statusBox.setPrefWidth(120);
//         statusBox.setAlignment(Pos.CENTER_LEFT);

//         Label serviceLabel = new Label(lastService);
//         serviceLabel.setPrefWidth(150);
//         serviceLabel.setStyle(
//                 "-fx-font-size: 12px;" +
//                 "-fx-text-fill: #191b23;"
//         );

//         Button detailsButton = new Button("Details");
//         detailsButton.setStyle(
//                 "-fx-background-color: transparent;" +
//                 "-fx-text-fill: #004ac6;" +
//                 "-fx-font-size: 11px;" +
//                 "-fx-font-weight: bold;"
//         );

//         HBox actionBox = new HBox(detailsButton);
//         actionBox.setPrefWidth(90);
//         actionBox.setAlignment(Pos.CENTER_LEFT);

//         row.getChildren().addAll(
//                 idLabel,
//                 categoryLabel,
//                 locationLabel,
//                 statusBox,
//                 serviceLabel,
//                 actionBox
//         );

//         return row;
//     }
// }




package com.kurukshetra.view.hospital;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class HospitalResourceManagement {

    private static final String PRIMARY_TEAL    = "#087F8C";
    private static final String TEAL_DARK       = "#056D79";
    private static final String TEAL_VERY_LIGHT = "#EAF8F9";
    private static final String TEAL_LIGHT      = "#DDF3F5";
    private static final String TEAL_SOFT       = "#CDECEF";
    private static final String TEAL_PALE       = "#F2FBFB";

    private static final String PAGE_BG         = "#F7FBFC";
    private static final String SURFACE         = "#FFFFFF";
    private static final String BORDER_COLOR    = "#DCECEF";
    private static final String TRACK_BG        = "#E6F0F2";

    private static final String TEXT_PRIMARY    = "#17252A";
    private static final String TEXT_SECONDARY  = "#52646A";
    private static final String TEXT_MUTED      = "#829196";

    private static final String STATUS_SUCCESS_BG   = "#E2F6EC";
    private static final String STATUS_SUCCESS_TEXT = "#22A06B";
    private static final String STATUS_WARN_BG      = "#FFF4D6";
    private static final String STATUS_WARN_TEXT    = "#E8A317";
    private static final String STATUS_DANGER_BG    = "#FCE9EC";
    private static final String STATUS_DANGER_TEXT  = "#D96C7A";

    private static final String CARD_STYLE =
        "-fx-background-color: " + SURFACE + ";" +
        "-fx-border-color: " + BORDER_COLOR + ";" +
        "-fx-border-radius: 14px;" +
        "-fx-background-radius: 14px;" +
        "-fx-effect: dropshadow(gaussian, rgba(8, 127, 140, 0.06), 16, 0.12, 0, 4);";

    public VBox getResourceManagement() {

        VBox mainContent = new VBox(22);
        mainContent.setPadding(new Insets(25));
        mainContent.setStyle("-fx-background-color: " + PAGE_BG + ";");

        /* =========================================================
         * PAGE HEADER
         * ========================================================= */

        Text heading = new Text("Resource Management");
        heading.setStyle(
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + PRIMARY_TEAL + ";"
        );

        Text subHeading = new Text(
                "Real-time status and allocation tracking for hospital critical assets."
        );
        subHeading.setStyle(
                "-fx-font-size: 13px;" +
                "-fx-fill: " + TEXT_SECONDARY + ";"
        );

        VBox headingBox = new VBox(4, heading, subHeading);

        Button historyButton = new Button("↺   View History");
        historyButton.setPrefWidth(125);
        historyButton.setPrefHeight(38);
        historyButton.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-background-radius: 8px;" +
                "-fx-cursor: hand;"
        );

        Button updateButton = new Button("↻   Update Availability");
        updateButton.setPrefWidth(155);
        updateButton.setPrefHeight(38);
        updateButton.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-background-radius: 8px;" +
                "-fx-cursor: hand;"
        );

        Button addResourceButton = new Button("+   Add Resource");
        addResourceButton.setPrefWidth(135);
        addResourceButton.setPrefHeight(38);
        addResourceButton.setStyle(
                "-fx-background-color: " + PRIMARY_TEAL + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8px;" +
                "-fx-cursor: hand;"
        );
        addResourceButton.setOnMouseEntered(e -> addResourceButton.setStyle(
                "-fx-background-color: " + TEAL_DARK + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8px;" +
                "-fx-cursor: hand;"
        ));
        addResourceButton.setOnMouseExited(e -> addResourceButton.setStyle(
                "-fx-background-color: " + PRIMARY_TEAL + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8px;" +
                "-fx-cursor: hand;"
        ));

        HBox headerButtons = new HBox(10, historyButton, updateButton, addResourceButton);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox header = new HBox(headingBox, headerSpacer, headerButtons);
        header.setAlignment(Pos.CENTER_LEFT);

        /* =========================================================
         * CRITICAL INVENTORY TITLE
         * ========================================================= */

        Text inventoryStatusTitle = new Text("CRITICAL INVENTORY STATUS");
        inventoryStatusTitle.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT_MUTED + ";"
        );

        Text lastSync = new Text("Last sync: 2 mins ago");
        lastSync.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-fill: " + TEXT_MUTED + ";"
        );

        Region inventoryTitleSpacer = new Region();
        HBox.setHgrow(inventoryTitleSpacer, Priority.ALWAYS);

        HBox inventoryTitleRow = new HBox(inventoryStatusTitle, inventoryTitleSpacer, lastSync);
        inventoryTitleRow.setAlignment(Pos.CENTER_LEFT);

        /* =========================================================
         * RESOURCE STATUS CARDS
         * ========================================================= */

        VBox icuCard = createResourceCard("▣", PRIMARY_TEAL, TEAL_VERY_LIGHT, "LOW STOCK", STATUS_DANGER_BG, STATUS_DANGER_TEXT, "ICU Beds", "42", " / 50 available", 84, PRIMARY_TEAL);
        VBox emergencyCard = createResourceCard("!", STATUS_WARN_TEXT, STATUS_WARN_BG, "WARNING", STATUS_WARN_BG, STATUS_WARN_TEXT, "Emergency Beds", "18", " / 30 available", 60, STATUS_WARN_TEXT);
        VBox generalCard = createResourceCard("▣", TEAL_DARK, TEAL_LIGHT, "OPTIMAL", STATUS_SUCCESS_BG, STATUS_SUCCESS_TEXT, "General Beds", "312", " / 400 available", 78, TEAL_DARK);
        VBox ventilatorCard = createResourceCard("≈", PRIMARY_TEAL, TEAL_VERY_LIGHT, "OPERATIONAL", STATUS_SUCCESS_BG, STATUS_SUCCESS_TEXT, "Ventilators", "12", " / 15 available", 80, PRIMARY_TEAL);
        VBox oxygenCard = createResourceCard("O₂", TEAL_DARK, TEAL_PALE, "SAFE RANGE", STATUS_SUCCESS_BG, STATUS_SUCCESS_TEXT, "Oxygen Reserves", "98", " % capacity", 98, TEAL_DARK);
        VBox bloodCard = createBloodCard();

        HBox resourceRow1 = new HBox(14, icuCard, emergencyCard, generalCard);
        HBox.setHgrow(icuCard, Priority.ALWAYS);
        HBox.setHgrow(emergencyCard, Priority.ALWAYS);
        HBox.setHgrow(generalCard, Priority.ALWAYS);

        HBox resourceRow2 = new HBox(14, ventilatorCard, oxygenCard, bloodCard);
        HBox.setHgrow(ventilatorCard, Priority.ALWAYS);
        HBox.setHgrow(oxygenCard, Priority.ALWAYS);
        HBox.setHgrow(bloodCard, Priority.ALWAYS);

        VBox resourceStatusSection = new VBox(12, inventoryTitleRow, resourceRow1, resourceRow2);

        /* =========================================================
         * BED OCCUPANCY DISTRIBUTION
         * ========================================================= */

        VBox occupancyCard = new VBox(14);
        occupancyCard.setPadding(new Insets(18));
        occupancyCard.setPrefHeight(340);
        occupancyCard.setStyle(CARD_STYLE);

        Text occupancyTitle = new Text("Bed Occupancy Distribution");
        occupancyTitle.setStyle(
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

        StackPane occupancyCircle = createOccupancyCircle();
        VBox occupancyChartBox = new VBox(occupancyTitle, occupancyCircle);
        occupancyChartBox.setAlignment(Pos.CENTER);
        occupancyChartBox.setPrefWidth(330);

        VBox occupancyDetails = new VBox(10,
                createOccupancyRow("ICU Occupancy", "84%", PRIMARY_TEAL),
                createOccupancyRow("Emergency Ward", "60%", "#2F9AA7"),
                createOccupancyRow("General Ward", "78%", "#72B8BF")
        );

        Region occupancyDetailSpacer = new Region();
        VBox.setVgrow(occupancyDetailSpacer, Priority.ALWAYS);

        Text occupancyMessage = new Text(
                "Capacity reaching threshold in ICU. Recommended: Redirect non-critical cases."
        );
        occupancyMessage.setWrappingWidth(300);
        occupancyMessage.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-fill: " + TEXT_SECONDARY + ";"
        );

        occupancyDetails.getChildren().addAll(occupancyDetailSpacer, occupancyMessage);

        HBox occupancyContent = new HBox(30, occupancyChartBox, occupancyDetails);
        occupancyContent.setAlignment(Pos.CENTER);
        occupancyCard.getChildren().add(occupancyContent);

        /* =========================================================
         * ICU USAGE TREND
         * ========================================================= */

        VBox trendCard = new VBox(14);
        trendCard.setPadding(new Insets(18));
        trendCard.setPrefHeight(450);
        trendCard.setStyle(CARD_STYLE);

        Text trendTitle = new Text("ICU Usage Trend (7D)");
        trendTitle.setStyle(
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

        ComboBox<String> trendCombo = new ComboBox<>();
        trendCombo.getItems().addAll("Weekly", "Monthly");
        trendCombo.setValue("Weekly");
        trendCombo.setPrefWidth(100);
        trendCombo.setPrefHeight(32);
        trendCombo.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 6px;" +
                "-fx-background-radius: 6px;" +
                "-fx-font-size: 11px;"
        );

        Region trendHeaderSpacer = new Region();
        HBox.setHgrow(trendHeaderSpacer, Priority.ALWAYS);

        HBox trendHeader = new HBox(trendTitle, trendHeaderSpacer, trendCombo);
        trendHeader.setAlignment(Pos.CENTER_LEFT);

        HBox trendBars = new HBox(10,
                createTrendBar("MON", 40),
                createTrendBar("TUE", 65),
                createTrendBar("WED", 55),
                createTrendBar("THU", 88),
                createTrendBar("FRI", 92),
                createTrendBar("SAT", 70),
                createTrendBar("SUN", 84)
        );
        trendBars.setAlignment(Pos.BOTTOM_CENTER);
        trendBars.setPrefHeight(240);

        VBox trendStatistics = new VBox(10,
                createStatisticRow("Average Occupancy", "76.4%", TEXT_PRIMARY),
                createStatisticRow("Peak Demand Day", "Friday", PRIMARY_TEAL),
                createStatisticRow("Efficiency Delta", "+12% vs last week", STATUS_SUCCESS_TEXT)
        );

        trendCard.getChildren().addAll(trendHeader, trendBars, trendStatistics);

        /* =========================================================
         * RECENT ALLOCATION LOG
         * ========================================================= */

        VBox logCard = new VBox(12);
        logCard.setPadding(new Insets(18));
        logCard.setMinHeight(260);
        logCard.setStyle(CARD_STYLE);

        Text logTitle = new Text("Recent Allocation Log");
        logTitle.setStyle(
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

        logCard.getChildren().addAll(
                logTitle,
                createLogRow(PRIMARY_TEAL, "ICU Bed #12 allocated to Patient P-908", "12:45 PM • Unit A-4"),
                createLogRow(STATUS_WARN_TEXT, "Oxygen cylinder refill requested", "11:30 AM • Storage West"),
                createLogRow(TEXT_SECONDARY, "General Bed #242 vacated", "10:15 AM • Ward C")
        );

        VBox analyticsColumn = new VBox(16, trendCard, logCard);

        HBox analyticsSection = new HBox(16, resourceStatusSection, analyticsColumn);
        HBox.setHgrow(resourceStatusSection, Priority.ALWAYS);
        HBox.setHgrow(analyticsColumn, Priority.ALWAYS);

        /* =========================================================
         * ASSET REGISTRY
         * ========================================================= */

        VBox assetRegistry = new VBox();
        assetRegistry.setStyle(CARD_STYLE);

        Text assetTitle = new Text("Asset Registry");
        assetTitle.setStyle(
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

        Button filterButton = new Button("☷");
        filterButton.setPrefSize(36, 34);
        filterButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: " + TEXT_SECONDARY + ";" +
                "-fx-font-size: 15px;" +
                "-fx-cursor: hand;"
        );

        Button downloadButton = new Button("↓");
        downloadButton.setPrefSize(36, 34);
        downloadButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: " + TEXT_SECONDARY + ";" +
                "-fx-font-size: 15px;" +
                "-fx-cursor: hand;"
        );

        HBox assetActions = new HBox(4, filterButton, downloadButton);
        assetActions.setAlignment(Pos.CENTER_RIGHT);

        Region assetHeaderSpacer = new Region();
        HBox.setHgrow(assetHeaderSpacer, Priority.ALWAYS);

        HBox assetHeader = new HBox(assetTitle, assetHeaderSpacer, assetActions);
        assetHeader.setPadding(new Insets(14, 16, 14, 16));
        assetHeader.setAlignment(Pos.CENTER_LEFT);
        assetHeader.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: transparent transparent " + BORDER_COLOR + " transparent;" +
                "-fx-border-width: 0px 0px 1px 0px;"
        );

        HBox assetTableHeader = new HBox(10,
                createTableHeader("RESOURCE ID", 120),
                createTableHeader("CATEGORY", 145),
                createTableHeader("LOCATION", 150),
                createTableHeader("STATUS", 120),
                createTableHeader("LAST SERVICE", 150),
                createTableHeader("ACTIONS", 90)
        );
        assetTableHeader.setPadding(new Insets(12, 16, 12, 16));
        assetTableHeader.setAlignment(Pos.CENTER_LEFT);
        assetTableHeader.setStyle("-fx-background-color: " + TEAL_VERY_LIGHT + ";");

        assetRegistry.getChildren().addAll(
                assetHeader,
                assetTableHeader,
                createAssetRow("VT-049", "Ventilator", "ICU Bay 4", "IN USE", "Oct 12, 2023", STATUS_SUCCESS_BG, STATUS_SUCCESS_TEXT, true),
                createAssetRow("IB-201", "ICU Bed", "ICU Bay 2", "AVAILABLE", "Nov 05, 2023", TEAL_VERY_LIGHT, PRIMARY_TEAL, false),
                createAssetRow("OX-912", "Oxygen Conc.", "Emergency A", "MAINTENANCE", "Oct 28, 2023", STATUS_DANGER_BG, STATUS_DANGER_TEXT, true)
        );

        /* =========================================================
         * FINAL LAYOUT
         * ========================================================= */

        mainContent.getChildren().addAll(header, analyticsSection, occupancyCard, assetRegistry);

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        VBox finalContent = new VBox(scrollPane);
        finalContent.setStyle("-fx-background-color: " + PAGE_BG + ";");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return finalContent;
    }

    /* =============================================================
     * HELPER FACTORY METHODS
     * ============================================================= */

    private VBox createResourceCard(
            String iconSymbol, String iconColor, String iconBgColor,
            String badgeText, String badgeBgColor, String badgeTextColor,
            String title, String value, String unitText,
            double progressPercent, String progressColor
    ) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setPrefHeight(150);
        card.setStyle(CARD_STYLE);

        Circle circle = new Circle(18, Color.web(iconBgColor));
        Text icon = new Text(iconSymbol);
        icon.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: " + iconColor + ";");
        StackPane iconPane = new StackPane(circle, icon);
        iconPane.setPrefSize(36, 36);

        Label badge = new Label(badgeText);
        badge.setStyle(
                "-fx-background-color: " + badgeBgColor + ";" +
                "-fx-text-fill: " + badgeTextColor + ";" +
                "-fx-font-size: 9px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 5px;" +
                "-fx-padding: 4px 7px;"
        );

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);
        HBox top = new HBox(iconPane, topSpacer, badge);
        top.setAlignment(Pos.CENTER_LEFT);

        Text cardTitle = new Text(title);
        cardTitle.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text mainVal = new Text(value);
        mainVal.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text subVal = new Text(unitText);
        subVal.setStyle("-fx-font-size: 11px; -fx-fill: " + TEXT_MUTED + ";");

        HBox valueBox = new HBox(mainVal, subVal);
        valueBox.setAlignment(Pos.BASELINE_LEFT);

        Region progressBg = new Region();
        progressBg.setPrefHeight(5);
        progressBg.setStyle("-fx-background-color: " + TRACK_BG + "; -fx-background-radius: 5px;");

        Region progressBar = new Region();
        progressBar.setPrefHeight(5);
        progressBar.setPrefWidth(progressPercent);
        progressBar.setStyle("-fx-background-color: " + progressColor + "; -fx-background-radius: 5px;");

        StackPane progressPane = new StackPane(progressBg, progressBar);
        progressPane.setAlignment(Pos.CENTER_LEFT);

        card.getChildren().addAll(top, cardTitle, valueBox, progressPane);
        return card;
    }

    private VBox createBloodCard() {
        VBox card = new VBox(8);
        card.setPadding(new Insets(15));
        card.setPrefHeight(150);
        card.setStyle(CARD_STYLE);

        Circle bloodCircle = new Circle(18, Color.web(STATUS_DANGER_BG));
        Text bloodIcon = new Text("♥");
        bloodIcon.setStyle("-fx-font-size: 16px; -fx-fill: " + STATUS_DANGER_TEXT + ";");
        StackPane bloodIconPane = new StackPane(bloodCircle, bloodIcon);
        bloodIconPane.setPrefSize(36, 36);

        Text bloodTitle = new Text("Blood Units");
        bloodTitle.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Region bloodSpacer = new Region();
        HBox.setHgrow(bloodSpacer, Priority.ALWAYS);

        HBox bloodTop = new HBox(bloodIconPane, bloodSpacer, bloodTitle);
        bloodTop.setAlignment(Pos.CENTER_LEFT);

        HBox bloodGroups = new HBox(5,
                createBloodGroup("O-", "4u", true),
                createBloodGroup("A+", "22u", false),
                createBloodGroup("B-", "12u", false),
                createBloodGroup("AB+", "18u", false)
        );
        bloodGroups.setAlignment(Pos.CENTER);

        Text bloodWarning = new Text("O- Negative critical levels detected");
        bloodWarning.setStyle("-fx-font-size: 10px; -fx-fill: " + STATUS_DANGER_TEXT + ";");

        card.getChildren().addAll(bloodTop, bloodGroups, bloodWarning);
        return card;
    }

    private VBox createBloodGroup(String group, String units, boolean critical) {
        VBox box = new VBox(2);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(55);
        box.setPadding(new Insets(5));
        box.setStyle(
                critical
                        ? "-fx-background-color: " + STATUS_DANGER_BG + "; -fx-border-color: #F8B4BD; -fx-border-radius: 5px; -fx-background-radius: 5px;"
                        : "-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 5px; -fx-background-radius: 5px;"
        );

        Label groupLabel = new Label(group);
        groupLabel.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + (critical ? STATUS_DANGER_TEXT : TEXT_MUTED) + ";");

        Label unitsLabel = new Label(units);
        unitsLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + (critical ? STATUS_DANGER_TEXT : TEXT_PRIMARY) + ";");

        box.getChildren().addAll(groupLabel, unitsLabel);
        return box;
    }

    private StackPane createOccupancyCircle() {
        Circle backgroundCircle = new Circle(75, Color.TRANSPARENT);
        backgroundCircle.setStroke(Color.web(TRACK_BG));
        backgroundCircle.setStrokeWidth(18);

        Circle icuCircle = new Circle(75, Color.TRANSPARENT);
        icuCircle.setStroke(Color.web(PRIMARY_TEAL));
        icuCircle.setStrokeWidth(18);
        icuCircle.getStrokeDashArray().addAll(390.0, 100.0);

        Circle emergencyCircle = new Circle(75, Color.TRANSPARENT);
        emergencyCircle.setStroke(Color.web("#2F9AA7"));
        emergencyCircle.setStrokeWidth(18);
        emergencyCircle.getStrokeDashArray().addAll(280.0, 210.0);

        Circle generalCircle = new Circle(75, Color.TRANSPARENT);
        generalCircle.setStroke(Color.web("#72B8BF"));
        generalCircle.setStrokeWidth(18);
        generalCircle.getStrokeDashArray().addAll(170.0, 320.0);

        Text percentage = new Text("82%");
        percentage.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text total = new Text("TOTAL");
        total.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + TEXT_MUTED + ";");

        VBox centerText = new VBox(2, percentage, total);
        centerText.setAlignment(Pos.CENTER);

        StackPane pane = new StackPane(backgroundCircle, icuCircle, emergencyCircle, generalCircle, centerText);
        pane.setPrefSize(180, 180);
        return pane;
    }

    private HBox createOccupancyRow(String name, String value, String circleColor) {
        Circle circle = new Circle(6, Color.web(circleColor));
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXT_PRIMARY + ";");

        HBox left = new HBox(8, circle, nameLabel);
        left.setAlignment(Pos.CENTER_LEFT);

        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(left, spacer, valueLabel);
        row.setPadding(new Insets(8, 12, 8, 12));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: " + TEAL_PALE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 6px; -fx-background-radius: 6px;"
        );

        return row;
    }

    private VBox createTrendBar(String day, int percentage) {
        Region background = new Region();
        background.setPrefWidth(22);
        background.setPrefHeight(170);
        background.setStyle("-fx-background-color: " + TRACK_BG + "; -fx-background-radius: 4px;");

        Region bar = new Region();
        bar.setPrefWidth(22);
        bar.setPrefHeight(percentage * 1.5);
        bar.setStyle("-fx-background-color: " + TEAL_SOFT + "; -fx-background-radius: 4px 4px 0px 0px;");

        StackPane barPane = new StackPane(background, bar);
        barPane.setAlignment(Pos.BOTTOM_CENTER);

        Label dayLabel = new Label(day);
        dayLabel.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");

        VBox box = new VBox(5, barPane, dayLabel);
        box.setAlignment(Pos.BOTTOM_CENTER);
        return box;
    }

    private HBox createStatisticRow(String labelText, String valueText, String valueColor) {
        Text left = new Text(labelText);
        left.setStyle("-fx-font-size: 11px; -fx-fill: " + TEXT_SECONDARY + ";");

        Text right = new Text(valueText);
        right.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + valueColor + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(left, spacer, right);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private HBox createLogRow(String color, String message, String time) {
        Circle dot = new Circle(4, Color.web(color));

        Text messageText = new Text(message);
        messageText.setStyle("-fx-font-size: 12px; -fx-fill: " + TEXT_PRIMARY + ";");

        Text timeText = new Text(time);
        timeText.setStyle("-fx-font-size: 10px; -fx-fill: " + TEXT_MUTED + ";");

        VBox textBox = new VBox(2, messageText, timeText);
        HBox row = new HBox(10, dot, textBox);
        row.setAlignment(Pos.TOP_LEFT);
        return row;
    }

    private Label createTableHeader(String text, double width) {
        Label label = new Label(text);
        label.setPrefWidth(width);
        label.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + TEAL_DARK + ";");
        return label;
    }

    private HBox createAssetRow(
            String id, String category, String location,
            String status, String lastService,
            String statusBackground, String statusColor,
            boolean alternate
    ) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(12, 16, 12, 16));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle(
                "-fx-background-color: " + (alternate ? SURFACE : "#F8FCFC") + ";" +
                "-fx-border-color: transparent transparent " + BORDER_COLOR + " transparent;" +
                "-fx-border-width: 0px 0px 1px 0px;"
        );

        Label idLabel = new Label(id);
        idLabel.setPrefWidth(120);
        idLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_TEAL + ";");

        Label categoryLabel = new Label(category);
        categoryLabel.setPrefWidth(145);
        categoryLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label locationLabel = new Label(location);
        locationLabel.setPrefWidth(150);
        locationLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label statusLabel = new Label(status);
        statusLabel.setStyle(
                "-fx-background-color: " + statusBackground + ";" +
                "-fx-text-fill: " + statusColor + ";" +
                "-fx-font-size: 9px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 5px;" +
                "-fx-padding: 5px 8px;"
        );

        HBox statusBox = new HBox(statusLabel);
        statusBox.setPrefWidth(120);
        statusBox.setAlignment(Pos.CENTER_LEFT);

        Label serviceLabel = new Label(lastService);
        serviceLabel.setPrefWidth(150);
        serviceLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Button detailsButton = new Button("Details");
        detailsButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: " + PRIMARY_TEAL + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
        );

        HBox actionBox = new HBox(detailsButton);
        actionBox.setPrefWidth(90);
        actionBox.setAlignment(Pos.CENTER_LEFT);

        row.getChildren().addAll(idLabel, categoryLabel, locationLabel, statusBox, serviceLabel, actionBox);
        return row;
    }
}