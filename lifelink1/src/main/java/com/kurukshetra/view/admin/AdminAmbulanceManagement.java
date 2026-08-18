// package com.kurukshetra.view.admin;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Circle;
// import javafx.scene.shape.Line;
// import javafx.scene.text.Text;

// public class AdminAmbulanceManagement {

//     public VBox getAmbulanceManagement() {

//         VBox mainContent = new VBox(20);
//         mainContent.setPadding(new Insets(25));
//         mainContent.setStyle("-fx-background-color: #faf8ff;");

//         Text heading = new Text("Ambulance Fleet Management");
//         heading.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #191b23;");

//         Text subHeading = new Text("Real-time oversight of critical emergency transport assets.");
//         subHeading.setStyle("-fx-font-size: 14px; -fx-fill: #737686;");

//         VBox headingBox = new VBox(5);
//         headingBox.getChildren().addAll(
//                 heading,
//                 subHeading
//         );

//         Button assignDriverButton = new Button("＋   Assign Driver");
//         assignDriverButton.setPrefWidth(140);
//         assignDriverButton.setPrefHeight(42);
//         assignDriverButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #004ac6; -fx-border-color: #004ac6; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-font-size: 12px; -fx-font-weight: bold;");

//         Button registerAmbulanceButton = new Button("＋   Register Ambulance");
//         registerAmbulanceButton.setPrefWidth(160);
//         registerAmbulanceButton.setPrefHeight(42);
//         registerAmbulanceButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-background-radius: 10px; -fx-font-size: 12px; -fx-font-weight: bold;");

//         HBox headerButtons = new HBox(10);
//         headerButtons.setAlignment(Pos.CENTER_RIGHT);
//         headerButtons.getChildren().addAll(
//                 assignDriverButton,
//                 registerAmbulanceButton
//         );

//         Region headerSpacer = new Region();
//         HBox.setHgrow(headerSpacer, Priority.ALWAYS);

//         HBox header = new HBox(
//                 headingBox,
//                 headerSpacer,
//                 headerButtons
//         );

//         header.setAlignment(Pos.CENTER_LEFT);

//         // METRICS

//         HBox metricsRow = new HBox(15);
//         metricsRow.setAlignment(Pos.CENTER);

//         VBox totalFleetBox = new VBox(7);
//         totalFleetBox.setPadding(new Insets(18));
//         totalFleetBox.setPrefHeight(115);
//         totalFleetBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e1e2ed; -fx-border-radius: 15px;");

//         Text totalFleetTitle = new Text("TOTAL FLEET");
//         totalFleetTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

//         Text totalFleetValue = new Text("42");
//         totalFleetValue.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #191b23;");

//         Text totalFleetInfo = new Text("↗  +2");
//         totalFleetInfo.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #16a34a;");

//         totalFleetBox.getChildren().addAll(
//                 totalFleetTitle,
//                 totalFleetValue,
//                 totalFleetInfo
//         );

//         VBox activeCallsBox = new VBox(7);
//         activeCallsBox.setPadding(new Insets(18));
//         activeCallsBox.setPrefHeight(115);
//         activeCallsBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e1e2ed; -fx-border-radius: 15px;");

//         Text activeCallsTitle = new Text("ACTIVE CALLS");
//         activeCallsTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

//         Text activeCallsValue = new Text("18");
//         activeCallsValue.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #004ac6;");

//         Text activeCallsInfo = new Text("On Mission");
//         activeCallsInfo.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

//         activeCallsBox.getChildren().addAll(
//                 activeCallsTitle,
//                 activeCallsValue,
//                 activeCallsInfo
//         );

//         VBox unavailableBox = new VBox(7);
//         unavailableBox.setPadding(new Insets(18));
//         unavailableBox.setPrefHeight(115);
//         unavailableBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e1e2ed; -fx-border-radius: 15px; -fx-border-width: 0px 0px 0px 4px;");

//         Text unavailableTitle = new Text("UNAVAILABLE");
//         unavailableTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

//         Text unavailableValue = new Text("5");
//         unavailableValue.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

//         Text unavailableInfo = new Text("Maintenance");
//         unavailableInfo.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

//         unavailableBox.getChildren().addAll(
//                 unavailableTitle,
//                 unavailableValue,
//                 unavailableInfo
//         );

//         VBox responseTimeBox = new VBox(7);
//         responseTimeBox.setPadding(new Insets(18));
//         responseTimeBox.setPrefHeight(115);
//         responseTimeBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e1e2ed; -fx-border-radius: 15px;");

//         Text responseTimeTitle = new Text("AVG RESPONSE TIME");
//         responseTimeTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

//         Text responseTimeValue = new Text("8.4 m");
//         responseTimeValue.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: #191b23;");

//         Text responseTimeInfo = new Text("✓  Target Met");
//         responseTimeInfo.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #16a34a;");

//         responseTimeBox.getChildren().addAll(
//                 responseTimeTitle,
//                 responseTimeValue,
//                 responseTimeInfo
//         );

//         HBox.setHgrow(totalFleetBox, Priority.ALWAYS);
//         HBox.setHgrow(activeCallsBox, Priority.ALWAYS);
//         HBox.setHgrow(unavailableBox, Priority.ALWAYS);
//         HBox.setHgrow(responseTimeBox, Priority.ALWAYS);

//         metricsRow.getChildren().addAll(
//                 totalFleetBox,
//                 activeCallsBox,
//                 unavailableBox,
//                 responseTimeBox
//         );

//         // MAIN AREA

//         HBox mainArea = new HBox(20);

//         VBox tableCard = new VBox();
//         tableCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #c3c6d7; -fx-border-radius: 15px;");

//         VBox tableTitleBox = new VBox(4);
//         tableTitleBox.setPadding(new Insets(16));

//         Text tableTitle = new Text("Vehicle Status Registry");
//         tableTitle.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: #191b23;");

//         Text tableInfo = new Text("Monitor ambulance vehicles and driver status");
//         tableInfo.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

//         tableTitleBox.getChildren().addAll(
//                 tableTitle,
//                 tableInfo
//         );

//         TextField searchField = new TextField();
//         searchField.setPromptText("Search vehicle or driver...");
//         searchField.setPrefWidth(230);
//         searchField.setPrefHeight(38);
//         searchField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-font-size: 12px;");

//         Region tableHeaderSpacer = new Region();
//         HBox.setHgrow(tableHeaderSpacer, Priority.ALWAYS);

//         HBox tableTop = new HBox(
//                 tableTitleBox,
//                 tableHeaderSpacer,
//                 searchField
//         );

//         tableTop.setPadding(new Insets(5, 15, 5, 5));
//         tableTop.setAlignment(Pos.CENTER_LEFT);

//         // TABLE HEADER

//         HBox tableHeader = new HBox(10);
//         tableHeader.setPadding(new Insets(12, 15, 12, 15));
//         tableHeader.setAlignment(Pos.CENTER_LEFT);
//         tableHeader.setStyle("-fx-background-color: #f3f3fe;");

//         Text ambulanceHeader = new Text("AMBULANCE NO.");
//         ambulanceHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #737686;");
//         ambulanceHeader.setWrappingWidth(115);

//         Text driverHeader = new Text("DRIVER");
//         driverHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #737686;");
//         driverHeader.setWrappingWidth(115);

//         Text statusHeader = new Text("STATUS");
//         statusHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #737686;");
//         statusHeader.setWrappingWidth(100);

//         Text etaHeader = new Text("ETA");
//         etaHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #737686;");
//         etaHeader.setWrappingWidth(70);

//         Text actionHeader = new Text("ACTION");
//         actionHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #737686;");
//         actionHeader.setWrappingWidth(70);

//         tableHeader.getChildren().addAll(
//                 ambulanceHeader,
//                 driverHeader,
//                 statusHeader,
//                 etaHeader,
//                 actionHeader
//         );

//         // AMBULANCE 1

//         HBox ambulanceRow1 = new HBox(10);
//         ambulanceRow1.setPadding(new Insets(14, 15, 14, 15));
//         ambulanceRow1.setAlignment(Pos.CENTER_LEFT);
//         ambulanceRow1.setStyle("-fx-border-color: transparent transparent #e1e2ed transparent; -fx-border-width: 0px 0px 1px 0px;");

//         Circle ambulanceCircle1 = new Circle(20);
//         ambulanceCircle1.setFill(Color.web("#dbe1ff"));

//         Text ambulanceIcon1 = new Text("🚑");
//         ambulanceIcon1.setStyle("-fx-font-size: 16px;");

//         StackPane ambulanceIconPane1 = new StackPane();
//         ambulanceIconPane1.setPrefSize(40, 40);
//         ambulanceIconPane1.getChildren().addAll(
//                 ambulanceCircle1,
//                 ambulanceIcon1
//         );

//         Text ambulanceNumber1 = new Text("AMB-1024");
//         ambulanceNumber1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #191b23;");

//         HBox ambulanceNumberBox1 = new HBox(8);
//         ambulanceNumberBox1.setPrefWidth(115);
//         ambulanceNumberBox1.setAlignment(Pos.CENTER_LEFT);
//         ambulanceNumberBox1.getChildren().addAll(
//                 ambulanceIconPane1,
//                 ambulanceNumber1
//         );

//         Text driver1 = new Text("Marcus Thorne");
//         driver1.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

//         HBox driverBox1 = new HBox(driver1);
//         driverBox1.setPrefWidth(115);
//         driverBox1.setAlignment(Pos.CENTER_LEFT);

//         Text status1 = new Text("ON ROUTE");
//         status1.setStyle("-fx-background-color: #dcfce7; -fx-text-fill: #15803d; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-padding: 5px 9px;");

//         HBox statusBox1 = new HBox(status1);
//         statusBox1.setPrefWidth(100);
//         statusBox1.setAlignment(Pos.CENTER_LEFT);

//         Text eta1 = new Text("4 mins");
//         eta1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #191b23;");

//         HBox etaBox1 = new HBox(eta1);
//         etaBox1.setPrefWidth(70);
//         etaBox1.setAlignment(Pos.CENTER_LEFT);

//         Button actionButton1 = new Button("⋮");
//         actionButton1.setPrefSize(35, 35);
//         actionButton1.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 18px;");

//         HBox actionBox1 = new HBox(actionButton1);
//         actionBox1.setPrefWidth(70);
//         actionBox1.setAlignment(Pos.CENTER_LEFT);

//         ambulanceRow1.getChildren().addAll(
//                 ambulanceNumberBox1,
//                 driverBox1,
//                 statusBox1,
//                 etaBox1,
//                 actionBox1
//         );

//         // AMBULANCE 2

//         HBox ambulanceRow2 = new HBox(10);
//         ambulanceRow2.setPadding(new Insets(14, 15, 14, 15));
//         ambulanceRow2.setAlignment(Pos.CENTER_LEFT);
//         ambulanceRow2.setStyle("-fx-border-color: transparent transparent #e1e2ed transparent; -fx-border-width: 0px 0px 1px 0px;");

//         Circle ambulanceCircle2 = new Circle(20);
//         ambulanceCircle2.setFill(Color.web("#e1e2ed"));

//         Text ambulanceIcon2 = new Text("🚑");
//         ambulanceIcon2.setStyle("-fx-font-size: 16px;");

//         StackPane ambulanceIconPane2 = new StackPane();
//         ambulanceIconPane2.setPrefSize(40, 40);
//         ambulanceIconPane2.getChildren().addAll(
//                 ambulanceCircle2,
//                 ambulanceIcon2
//         );

//         Text ambulanceNumber2 = new Text("AMB-9981");
//         ambulanceNumber2.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #191b23;");

//         HBox ambulanceNumberBox2 = new HBox(8);
//         ambulanceNumberBox2.setPrefWidth(115);
//         ambulanceNumberBox2.setAlignment(Pos.CENTER_LEFT);
//         ambulanceNumberBox2.getChildren().addAll(
//                 ambulanceIconPane2,
//                 ambulanceNumber2
//         );

//         Text driver2 = new Text("Sarah Jenkins");
//         driver2.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

//         HBox driverBox2 = new HBox(driver2);
//         driverBox2.setPrefWidth(115);
//         driverBox2.setAlignment(Pos.CENTER_LEFT);

//         Text status2 = new Text("STATIONARY");
//         status2.setStyle("-fx-background-color: #dbeafe; -fx-text-fill: #1d4ed8; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-padding: 5px 9px;");

//         HBox statusBox2 = new HBox(status2);
//         statusBox2.setPrefWidth(100);
//         statusBox2.setAlignment(Pos.CENTER_LEFT);

//         Text eta2 = new Text("—");
//         eta2.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

//         HBox etaBox2 = new HBox(eta2);
//         etaBox2.setPrefWidth(70);
//         etaBox2.setAlignment(Pos.CENTER_LEFT);

//         Button actionButton2 = new Button("⋮");
//         actionButton2.setPrefSize(35, 35);
//         actionButton2.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 18px;");

//         HBox actionBox2 = new HBox(actionButton2);
//         actionBox2.setPrefWidth(70);
//         actionBox2.setAlignment(Pos.CENTER_LEFT);

//         ambulanceRow2.getChildren().addAll(
//                 ambulanceNumberBox2,
//                 driverBox2,
//                 statusBox2,
//                 etaBox2,
//                 actionBox2
//         );

//         // AMBULANCE 3

//         HBox ambulanceRow3 = new HBox(10);
//         ambulanceRow3.setPadding(new Insets(14, 15, 14, 15));
//         ambulanceRow3.setAlignment(Pos.CENTER_LEFT);
//         ambulanceRow3.setStyle("-fx-border-color: transparent transparent #e1e2ed transparent; -fx-border-width: 0px 0px 1px 0px; -fx-border-insets: 0 0 0 0;");

//         Circle ambulanceCircle3 = new Circle(20);
//         ambulanceCircle3.setFill(Color.web("#ffdad6"));

//         Text ambulanceIcon3 = new Text("⚠");
//         ambulanceIcon3.setStyle("-fx-font-size: 16px; -fx-fill: #ba1a1a;");

//         StackPane ambulanceIconPane3 = new StackPane();
//         ambulanceIconPane3.setPrefSize(40, 40);
//         ambulanceIconPane3.getChildren().addAll(
//                 ambulanceCircle3,
//                 ambulanceIcon3
//         );

//         Text ambulanceNumber3 = new Text("AMB-4402");
//         ambulanceNumber3.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #191b23;");

//         HBox ambulanceNumberBox3 = new HBox(8);
//         ambulanceNumberBox3.setPrefWidth(115);
//         ambulanceNumberBox3.setAlignment(Pos.CENTER_LEFT);
//         ambulanceNumberBox3.getChildren().addAll(
//                 ambulanceIconPane3,
//                 ambulanceNumber3
//         );

//         Text driver3 = new Text("David Wu");
//         driver3.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

//         HBox driverBox3 = new HBox(driver3);
//         driverBox3.setPrefWidth(115);
//         driverBox3.setAlignment(Pos.CENTER_LEFT);

//         Text status3 = new Text("EMERGENCY");
//         status3.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-padding: 5px 9px;");

//         HBox statusBox3 = new HBox(status3);
//         statusBox3.setPrefWidth(100);
//         statusBox3.setAlignment(Pos.CENTER_LEFT);

//         Text eta3 = new Text("1 min");
//         eta3.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

//         HBox etaBox3 = new HBox(eta3);
//         etaBox3.setPrefWidth(70);
//         etaBox3.setAlignment(Pos.CENTER_LEFT);

//         Button actionButton3 = new Button("⋮");
//         actionButton3.setPrefSize(35, 35);
//         actionButton3.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 18px;");

//         HBox actionBox3 = new HBox(actionButton3);
//         actionBox3.setPrefWidth(70);
//         actionBox3.setAlignment(Pos.CENTER_LEFT);

//         ambulanceRow3.getChildren().addAll(
//                 ambulanceNumberBox3,
//                 driverBox3,
//                 statusBox3,
//                 etaBox3,
//                 actionBox3
//         );

//         // AMBULANCE 4

//         HBox ambulanceRow4 = new HBox(10);
//         ambulanceRow4.setPadding(new Insets(14, 15, 14, 15));
//         ambulanceRow4.setAlignment(Pos.CENTER_LEFT);

//         Circle ambulanceCircle4 = new Circle(20);
//         ambulanceCircle4.setFill(Color.web("#e1e2ed"));

//         Text ambulanceIcon4 = new Text("🚑");
//         ambulanceIcon4.setStyle("-fx-font-size: 16px;");

//         StackPane ambulanceIconPane4 = new StackPane();
//         ambulanceIconPane4.setPrefSize(40, 40);
//         ambulanceIconPane4.getChildren().addAll(
//                 ambulanceCircle4,
//                 ambulanceIcon4
//         );

//         Text ambulanceNumber4 = new Text("AMB-7723");
//         ambulanceNumber4.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #191b23;");

//         HBox ambulanceNumberBox4 = new HBox(8);
//         ambulanceNumberBox4.setPrefWidth(115);
//         ambulanceNumberBox4.setAlignment(Pos.CENTER_LEFT);
//         ambulanceNumberBox4.getChildren().addAll(
//                 ambulanceIconPane4,
//                 ambulanceNumber4
//         );

//         Text driver4 = new Text("Elena Rodriguez");
//         driver4.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

//         HBox driverBox4 = new HBox(driver4);
//         driverBox4.setPrefWidth(115);
//         driverBox4.setAlignment(Pos.CENTER_LEFT);

//         Text status4 = new Text("MAINTENANCE");
//         status4.setStyle("-fx-background-color: #fef3c7; -fx-text-fill: #a16207; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 15px; -fx-padding: 5px 9px;");

//         HBox statusBox4 = new HBox(status4);
//         statusBox4.setPrefWidth(100);
//         statusBox4.setAlignment(Pos.CENTER_LEFT);

//         Text eta4 = new Text("—");
//         eta4.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

//         HBox etaBox4 = new HBox(eta4);
//         etaBox4.setPrefWidth(70);
//         etaBox4.setAlignment(Pos.CENTER_LEFT);

//         Button actionButton4 = new Button("⋮");
//         actionButton4.setPrefSize(35, 35);
//         actionButton4.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 18px;");

//         HBox actionBox4 = new HBox(actionButton4);
//         actionBox4.setPrefWidth(70);
//         actionBox4.setAlignment(Pos.CENTER_LEFT);

//         ambulanceRow4.getChildren().addAll(
//                 ambulanceNumberBox4,
//                 driverBox4,
//                 statusBox4,
//                 etaBox4,
//                 actionBox4
//         );

//         tableCard.getChildren().addAll(
//                 tableTop,
//                 tableHeader,
//                 ambulanceRow1,
//                 ambulanceRow2,
//                 ambulanceRow3,
//                 ambulanceRow4
//         );

//         // LIVE TRACKING

//         VBox trackingCard = new VBox();
//         trackingCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #c3c6d7; -fx-border-radius: 15px;");

//         HBox trackingHeader = new HBox(10);
//         trackingHeader.setPadding(new Insets(16));
//         trackingHeader.setAlignment(Pos.CENTER_LEFT);

//         Text mapIcon = new Text("⌖");
//         mapIcon.setStyle("-fx-font-size: 23px; -fx-fill: #004ac6;");

//         Text trackingTitle = new Text("Live Tracking");
//         trackingTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: #191b23;");

//         Region trackingSpacer = new Region();
//         HBox.setHgrow(trackingSpacer, Priority.ALWAYS);

//         Circle liveCircle = new Circle(5);
//         liveCircle.setFill(Color.web("#22c55e"));

//         Text liveText = new Text("Live");
//         liveText.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

//         HBox liveBox = new HBox(5);
//         liveBox.setAlignment(Pos.CENTER);
//         liveBox.getChildren().addAll(
//                 liveCircle,
//                 liveText
//         );

//         trackingHeader.getChildren().addAll(
//                 mapIcon,
//                 trackingTitle,
//                 trackingSpacer,
//                 liveBox
//         );

//         // MAP AREA

//         StackPane mapPane = new StackPane();
//         mapPane.setPrefHeight(430);
//         mapPane.setStyle("-fx-background-color: #dce8df;");

//         Line road1 = new Line(0, 80, 500, 280);
//         road1.setStroke(Color.web("#ffffff"));
//         road1.setStrokeWidth(12);

//         Line road2 = new Line(80, 0, 300, 430);
//         road2.setStroke(Color.web("#ffffff"));
//         road2.setStrokeWidth(10);

//         Line road3 = new Line(500, 100, 100, 350);
//         road3.setStroke(Color.web("#ffffff"));
//         road3.setStrokeWidth(9);

//         Line routeLine = new Line(80, 350, 420, 100);
//         routeLine.setStroke(Color.web("#2563eb"));
//         routeLine.setStrokeWidth(5);

//         Circle markerCircle1 = new Circle(20);
//         markerCircle1.setFill(Color.web("#004ac6"));

//         Text markerText1 = new Text("🚑");
//         markerText1.setStyle("-fx-font-size: 16px;");

//         StackPane marker1 = new StackPane();
//         marker1.setLayoutX(120);
//         marker1.setLayoutY(130);
//         marker1.getChildren().addAll(
//                 markerCircle1,
//                 markerText1
//         );

//         Circle markerCircle2 = new Circle(20);
//         markerCircle2.setFill(Color.web("#ba1a1a"));

//         Text markerText2 = new Text("!");
//         markerText2.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-fill: white;");

//         StackPane marker2 = new StackPane();
//         marker2.setLayoutX(350);
//         marker2.setLayoutY(270);
//         marker2.getChildren().addAll(
//                 markerCircle2,
//                 markerText2
//         );

//         Text ambulanceLabel1 = new Text("AMB-1024");
//         ambulanceLabel1.setStyle("-fx-background-color: white; -fx-padding: 5px 8px; -fx-background-radius: 5px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #191b23;");

//         StackPane labelPane1 = new StackPane(ambulanceLabel1);
//         labelPane1.setLayoutX(105);
//         labelPane1.setLayoutY(90);

//         Text ambulanceLabel2 = new Text("EMERGENCY");
//         ambulanceLabel2.setStyle("-fx-background-color: white; -fx-padding: 5px 8px; -fx-background-radius: 5px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

//         StackPane labelPane2 = new StackPane(ambulanceLabel2);
//         labelPane2.setLayoutX(320);
//         labelPane2.setLayoutY(225);

//         mapPane.getChildren().addAll(
//                 road1,
//                 road2,
//                 road3,
//                 routeLine,
//                 marker1,
//                 marker2,
//                 labelPane1,
//                 labelPane2
//         );

//         HBox mapFooter = new HBox();
//         mapFooter.setPadding(new Insets(12, 15, 12, 15));
//         mapFooter.setAlignment(Pos.CENTER_LEFT);
//         mapFooter.setStyle("-fx-background-color: #f3f3fe;");

//         Text districtText = new Text("Emergency Control Center");
//         districtText.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

//         Region footerSpacer = new Region();
//         HBox.setHgrow(footerSpacer, Priority.ALWAYS);

//         Button fullViewButton = new Button("Full View");
//         fullViewButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 11px; -fx-font-weight: bold;");

//         mapFooter.getChildren().addAll(
//                 districtText,
//                 footerSpacer,
//                 fullViewButton
//         );

//         trackingCard.getChildren().addAll(
//                 trackingHeader,
//                 mapPane,
//                 mapFooter
//         );

//         HBox.setHgrow(tableCard, Priority.ALWAYS);
//         mainArea.getChildren().addAll(
//                 tableCard,
//                 trackingCard
//         );

//         // FINAL CONTENT

//         mainContent.getChildren().addAll(
//                 header,
//                 metricsRow,
//                 mainArea
//         );

//         ScrollPane scrollPane = new ScrollPane(mainContent);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//         scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

//         VBox finalContent = new VBox(scrollPane);
//         finalContent.setStyle("-fx-background-color: #faf8ff;");

//         VBox.setVgrow(scrollPane, Priority.ALWAYS);

//         return finalContent;
//     }
// }


package com.kurukshetra.view.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class AdminAmbulanceManagement {

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

    private static final String SUCCESS_TEXT = "#15803D";
    private static final String SUCCESS_BG = "#DCFCE7";

    private static final String WARNING_TEXT = "#A16207";
    private static final String WARNING_BG = "#FEF3C7";

    private static final String DANGER_TEXT = "#E66A7A";
    private static final String DANGER_BG = "#FDE7EB";
    private static final String DANGER_BORDER = "#FCCED5";

    private static final String CARD_SHADOW = "-fx-effect: dropshadow(gaussian, rgba(156, 125, 240, 0.08), 16, 0.1, 0, 4);";
    private static final String FONT_STACK = "-fx-font-family: 'Segoe UI', 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;";

    private static final String BASE_CARD_STYLE = FONT_STACK +
            "-fx-background-color: " + BG_SURFACE + ";" +
            "-fx-background-radius: 16px;" +
            "-fx-border-color: " + BORDER_COLOR + ";" +
            "-fx-border-radius: 16px;" +
            "-fx-border-width: 1px;" +
            CARD_SHADOW;

    private static final String PRIMARY_BUTTON_STYLE = FONT_STACK +
            "-fx-background-color: " + PURPLE_BUTTON + ";" +
            "-fx-text-fill: #FFFFFF;" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10px;" +
            "-fx-effect: dropshadow(gaussian, rgba(192, 132, 252, 0.35), 10, 0.2, 0, 3);" +
            "-fx-cursor: hand;";

    private static final String SECONDARY_BUTTON_STYLE = FONT_STACK +
            "-fx-background-color: " + BG_SURFACE + ";" +
            "-fx-text-fill: " + PURPLE_DARK + ";" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-color: " + BORDER_COLOR + ";" +
            "-fx-border-radius: 10px;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;";

    public VBox getAmbulanceManagement() {

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(25));
        mainContent.setStyle("-fx-background-color: " + BG_PAGE + ";");

        // HEADER
        Text heading = new Text("Ambulance Fleet Management");
        heading.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text subHeading = new Text("Real-time oversight of critical emergency transport assets.");
        subHeading.setStyle(FONT_STACK + "-fx-font-size: 14px; -fx-fill: " + TEXT_SECONDARY + ";");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(
                heading,
                subHeading
        );

        Button assignDriverButton = new Button("＋   Assign Driver");
        assignDriverButton.setPrefWidth(140);
        assignDriverButton.setPrefHeight(42);
        assignDriverButton.setStyle(SECONDARY_BUTTON_STYLE);

        Button registerAmbulanceButton = new Button("＋   Register Ambulance");
        registerAmbulanceButton.setPrefWidth(165);
        registerAmbulanceButton.setPrefHeight(42);
        registerAmbulanceButton.setStyle(PRIMARY_BUTTON_STYLE);

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

        // METRICS ROW
        HBox metricsRow = new HBox(15);
        metricsRow.setAlignment(Pos.CENTER);

        VBox totalFleetBox = new VBox(7);
        totalFleetBox.setPadding(new Insets(18));
        totalFleetBox.setPrefHeight(115);
        totalFleetBox.setStyle(BASE_CARD_STYLE);

        Text totalFleetTitle = new Text("TOTAL FLEET");
        totalFleetTitle.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + TEXT_SECONDARY + ";");

        Text totalFleetValue = new Text("42");
        totalFleetValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text totalFleetInfo = new Text("↗  +2");
        totalFleetInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + SUCCESS_TEXT + ";");

        totalFleetBox.getChildren().addAll(
                totalFleetTitle,
                totalFleetValue,
                totalFleetInfo
        );

        VBox activeCallsBox = new VBox(7);
        activeCallsBox.setPadding(new Insets(18));
        activeCallsBox.setPrefHeight(115);
        activeCallsBox.setStyle(BASE_CARD_STYLE);

        Text activeCallsTitle = new Text("ACTIVE CALLS");
        activeCallsTitle.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + TEXT_SECONDARY + ";");

        Text activeCallsValue = new Text("18");
        activeCallsValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");

        Text activeCallsInfo = new Text("On Mission");
        activeCallsInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_MUTED + ";");

        activeCallsBox.getChildren().addAll(
                activeCallsTitle,
                activeCallsValue,
                activeCallsInfo
        );

        VBox unavailableBox = new VBox(7);
        unavailableBox.setPadding(new Insets(18));
        unavailableBox.setPrefHeight(115);
        unavailableBox.setStyle(FONT_STACK +
                "-fx-background-color: " + DANGER_BG + ";" +
                "-fx-background-radius: 16px;" +
                "-fx-border-color: " + DANGER_BORDER + ";" +
                "-fx-border-radius: 16px;" +
                "-fx-border-width: 1px;" +
                "-fx-effect: dropshadow(gaussian, rgba(230, 106, 122, 0.10), 16, 0.1, 0, 4);");

        Text unavailableTitle = new Text("UNAVAILABLE");
        unavailableTitle.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + DANGER_TEXT + ";");

        Text unavailableValue = new Text("5");
        unavailableValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + DANGER_TEXT + ";");

        Text unavailableInfo = new Text("Maintenance");
        unavailableInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + DANGER_TEXT + ";");

        unavailableBox.getChildren().addAll(
                unavailableTitle,
                unavailableValue,
                unavailableInfo
        );

        VBox responseTimeBox = new VBox(7);
        responseTimeBox.setPadding(new Insets(18));
        responseTimeBox.setPrefHeight(115);
        responseTimeBox.setStyle(BASE_CARD_STYLE);

        Text responseTimeTitle = new Text("AVG RESPONSE TIME");
        responseTimeTitle.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + TEXT_SECONDARY + ";");

        Text responseTimeValue = new Text("8.4 m");
        responseTimeValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text responseTimeInfo = new Text("✓  Target Met");
        responseTimeInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + SUCCESS_TEXT + ";");

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
        tableCard.setStyle(BASE_CARD_STYLE);

        VBox tableTitleBox = new VBox(4);
        tableTitleBox.setPadding(new Insets(18));

        Text tableTitle = new Text("Vehicle Status Registry");
        tableTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text tableInfo = new Text("Monitor ambulance vehicles and driver status");
        tableInfo.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + TEXT_MUTED + ";");

        tableTitleBox.getChildren().addAll(
                tableTitle,
                tableInfo
        );

        TextField searchField = new TextField();
        searchField.setPromptText("Search vehicle or driver...");
        searchField.setPrefWidth(230);
        searchField.setPrefHeight(38);
        searchField.setStyle(FONT_STACK +
                "-fx-background-color: #FAF8FF;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 20px;" +
                "-fx-background-radius: 20px;" +
                "-fx-padding: 0px 14px;" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";");

        Region tableHeaderSpacer = new Region();
        HBox.setHgrow(tableHeaderSpacer, Priority.ALWAYS);

        HBox tableTop = new HBox(
                tableTitleBox,
                tableHeaderSpacer,
                searchField
        );
        tableTop.setPadding(new Insets(5, 18, 5, 5));
        tableTop.setAlignment(Pos.CENTER_LEFT);

        // TABLE HEADER
        HBox tableHeader = new HBox(10);
        tableHeader.setPadding(new Insets(12, 18, 12, 18));
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setStyle("-fx-background-color: " + PURPLE_LIGHT + ";");

        Text ambulanceHeader = new Text("AMBULANCE NO.");
        ambulanceHeader.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        ambulanceHeader.setWrappingWidth(125);

        Text driverHeader = new Text("DRIVER");
        driverHeader.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        driverHeader.setWrappingWidth(120);

        Text statusHeader = new Text("STATUS");
        statusHeader.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        statusHeader.setWrappingWidth(110);

        Text etaHeader = new Text("ETA");
        etaHeader.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        etaHeader.setWrappingWidth(70);

        Text actionHeader = new Text("ACTION");
        actionHeader.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        actionHeader.setWrappingWidth(60);

        tableHeader.getChildren().addAll(
                ambulanceHeader,
                driverHeader,
                statusHeader,
                etaHeader,
                actionHeader
        );

        String rowBorderStyle = "-fx-border-color: " + BORDER_DIVIDER + "; -fx-border-width: 0px 0px 1px 0px;";

        // AMBULANCE 1
        HBox ambulanceRow1 = new HBox(10);
        ambulanceRow1.setPadding(new Insets(14, 18, 14, 18));
        ambulanceRow1.setAlignment(Pos.CENTER_LEFT);
        ambulanceRow1.setStyle(rowBorderStyle);

        Circle ambulanceCircle1 = new Circle(18);
        ambulanceCircle1.setFill(Color.web(PURPLE_LIGHT));

        Text ambulanceIcon1 = new Text("🚑");
        ambulanceIcon1.setStyle("-fx-font-size: 15px;");

        StackPane ambulanceIconPane1 = new StackPane();
        ambulanceIconPane1.setPrefSize(36, 36);
        ambulanceIconPane1.getChildren().addAll(
                ambulanceCircle1,
                ambulanceIcon1
        );

        Text ambulanceNumber1 = new Text("AMB-1024");
        ambulanceNumber1.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        HBox ambulanceNumberBox1 = new HBox(8);
        ambulanceNumberBox1.setPrefWidth(125);
        ambulanceNumberBox1.setAlignment(Pos.CENTER_LEFT);
        ambulanceNumberBox1.getChildren().addAll(
                ambulanceIconPane1,
                ambulanceNumber1
        );

        Text driver1 = new Text("Marcus Thorne");
        driver1.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + TEXT_SECONDARY + ";");

        HBox driverBox1 = new HBox(driver1);
        driverBox1.setPrefWidth(120);
        driverBox1.setAlignment(Pos.CENTER_LEFT);

        Label status1 = new Label("ON ROUTE");
        status1.setStyle(FONT_STACK + "-fx-background-color: " + SUCCESS_BG + "; -fx-text-fill: " + SUCCESS_TEXT + "; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-padding: 4px 8px;");

        HBox statusBox1 = new HBox(status1);
        statusBox1.setPrefWidth(110);
        statusBox1.setAlignment(Pos.CENTER_LEFT);

        Text eta1 = new Text("4 mins");
        eta1.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        HBox etaBox1 = new HBox(eta1);
        etaBox1.setPrefWidth(70);
        etaBox1.setAlignment(Pos.CENTER_LEFT);

        Button actionButton1 = new Button("⋮");
        actionButton1.setPrefSize(32, 32);
        actionButton1.setStyle(FONT_STACK + "-fx-background-color: transparent; -fx-text-fill: " + PURPLE_DARK + "; -fx-font-size: 18px; -fx-cursor: hand;");

        HBox actionBox1 = new HBox(actionButton1);
        actionBox1.setPrefWidth(60);
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
        ambulanceRow2.setPadding(new Insets(14, 18, 14, 18));
        ambulanceRow2.setAlignment(Pos.CENTER_LEFT);
        ambulanceRow2.setStyle(rowBorderStyle);

        Circle ambulanceCircle2 = new Circle(18);
        ambulanceCircle2.setFill(Color.web("#F3F4F6"));

        Text ambulanceIcon2 = new Text("🚑");
        ambulanceIcon2.setStyle("-fx-font-size: 15px;");

        StackPane ambulanceIconPane2 = new StackPane();
        ambulanceIconPane2.setPrefSize(36, 36);
        ambulanceIconPane2.getChildren().addAll(
                ambulanceCircle2,
                ambulanceIcon2
        );

        Text ambulanceNumber2 = new Text("AMB-9981");
        ambulanceNumber2.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        HBox ambulanceNumberBox2 = new HBox(8);
        ambulanceNumberBox2.setPrefWidth(125);
        ambulanceNumberBox2.setAlignment(Pos.CENTER_LEFT);
        ambulanceNumberBox2.getChildren().addAll(
                ambulanceIconPane2,
                ambulanceNumber2
        );

        Text driver2 = new Text("Sarah Jenkins");
        driver2.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + TEXT_SECONDARY + ";");

        HBox driverBox2 = new HBox(driver2);
        driverBox2.setPrefWidth(120);
        driverBox2.setAlignment(Pos.CENTER_LEFT);

        Label status2 = new Label("STATIONARY");
        status2.setStyle(FONT_STACK + "-fx-background-color: " + PURPLE_LIGHT + "; -fx-text-fill: " + PURPLE_DARK + "; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-padding: 4px 8px;");

        HBox statusBox2 = new HBox(status2);
        statusBox2.setPrefWidth(110);
        statusBox2.setAlignment(Pos.CENTER_LEFT);

        Text eta2 = new Text("—");
        eta2.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + TEXT_MUTED + ";");

        HBox etaBox2 = new HBox(eta2);
        etaBox2.setPrefWidth(70);
        etaBox2.setAlignment(Pos.CENTER_LEFT);

        Button actionButton2 = new Button("⋮");
        actionButton2.setPrefSize(32, 32);
        actionButton2.setStyle(FONT_STACK + "-fx-background-color: transparent; -fx-text-fill: " + PURPLE_DARK + "; -fx-font-size: 18px; -fx-cursor: hand;");

        HBox actionBox2 = new HBox(actionButton2);
        actionBox2.setPrefWidth(60);
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
        ambulanceRow3.setPadding(new Insets(14, 18, 14, 18));
        ambulanceRow3.setAlignment(Pos.CENTER_LEFT);
        ambulanceRow3.setStyle(rowBorderStyle);

        Circle ambulanceCircle3 = new Circle(18);
        ambulanceCircle3.setFill(Color.web(DANGER_BG));

        Text ambulanceIcon3 = new Text("⚠");
        ambulanceIcon3.setStyle(FONT_STACK + "-fx-font-size: 15px; -fx-fill: " + DANGER_TEXT + ";");

        StackPane ambulanceIconPane3 = new StackPane();
        ambulanceIconPane3.setPrefSize(36, 36);
        ambulanceIconPane3.getChildren().addAll(
                ambulanceCircle3,
                ambulanceIcon3
        );

        Text ambulanceNumber3 = new Text("AMB-4402");
        ambulanceNumber3.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        HBox ambulanceNumberBox3 = new HBox(8);
        ambulanceNumberBox3.setPrefWidth(125);
        ambulanceNumberBox3.setAlignment(Pos.CENTER_LEFT);
        ambulanceNumberBox3.getChildren().addAll(
                ambulanceIconPane3,
                ambulanceNumber3
        );

        Text driver3 = new Text("David Wu");
        driver3.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + TEXT_SECONDARY + ";");

        HBox driverBox3 = new HBox(driver3);
        driverBox3.setPrefWidth(120);
        driverBox3.setAlignment(Pos.CENTER_LEFT);

        Label status3 = new Label("EMERGENCY");
        status3.setStyle(FONT_STACK + "-fx-background-color: " + DANGER_BG + "; -fx-text-fill: " + DANGER_TEXT + "; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-padding: 4px 8px;");

        HBox statusBox3 = new HBox(status3);
        statusBox3.setPrefWidth(110);
        statusBox3.setAlignment(Pos.CENTER_LEFT);

        Text eta3 = new Text("1 min");
        eta3.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + DANGER_TEXT + ";");

        HBox etaBox3 = new HBox(eta3);
        etaBox3.setPrefWidth(70);
        etaBox3.setAlignment(Pos.CENTER_LEFT);

        Button actionButton3 = new Button("⋮");
        actionButton3.setPrefSize(32, 32);
        actionButton3.setStyle(FONT_STACK + "-fx-background-color: transparent; -fx-text-fill: " + PURPLE_DARK + "; -fx-font-size: 18px; -fx-cursor: hand;");

        HBox actionBox3 = new HBox(actionButton3);
        actionBox3.setPrefWidth(60);
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
        ambulanceRow4.setPadding(new Insets(14, 18, 14, 18));
        ambulanceRow4.setAlignment(Pos.CENTER_LEFT);

        Circle ambulanceCircle4 = new Circle(18);
        ambulanceCircle4.setFill(Color.web("#F3F4F6"));

        Text ambulanceIcon4 = new Text("🚑");
        ambulanceIcon4.setStyle("-fx-font-size: 15px;");

        StackPane ambulanceIconPane4 = new StackPane();
        ambulanceIconPane4.setPrefSize(36, 36);
        ambulanceIconPane4.getChildren().addAll(
                ambulanceCircle4,
                ambulanceIcon4
        );

        Text ambulanceNumber4 = new Text("AMB-7723");
        ambulanceNumber4.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        HBox ambulanceNumberBox4 = new HBox(8);
        ambulanceNumberBox4.setPrefWidth(125);
        ambulanceNumberBox4.setAlignment(Pos.CENTER_LEFT);
        ambulanceNumberBox4.getChildren().addAll(
                ambulanceIconPane4,
                ambulanceNumber4
        );

        Text driver4 = new Text("Elena Rodriguez");
        driver4.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + TEXT_SECONDARY + ";");

        HBox driverBox4 = new HBox(driver4);
        driverBox4.setPrefWidth(120);
        driverBox4.setAlignment(Pos.CENTER_LEFT);

        Label status4 = new Label("MAINTENANCE");
        status4.setStyle(FONT_STACK + "-fx-background-color: " + WARNING_BG + "; -fx-text-fill: " + WARNING_TEXT + "; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-padding: 4px 8px;");

        HBox statusBox4 = new HBox(status4);
        statusBox4.setPrefWidth(110);
        statusBox4.setAlignment(Pos.CENTER_LEFT);

        Text eta4 = new Text("—");
        eta4.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + TEXT_MUTED + ";");

        HBox etaBox4 = new HBox(eta4);
        etaBox4.setPrefWidth(70);
        etaBox4.setAlignment(Pos.CENTER_LEFT);

        Button actionButton4 = new Button("⋮");
        actionButton4.setPrefSize(32, 32);
        actionButton4.setStyle(FONT_STACK + "-fx-background-color: transparent; -fx-text-fill: " + PURPLE_DARK + "; -fx-font-size: 18px; -fx-cursor: hand;");

        HBox actionBox4 = new HBox(actionButton4);
        actionBox4.setPrefWidth(60);
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

        // LIVE TRACKING CARD
        VBox trackingCard = new VBox();
        trackingCard.setStyle(BASE_CARD_STYLE);

        HBox trackingHeader = new HBox(10);
        trackingHeader.setPadding(new Insets(16, 18, 16, 18));
        trackingHeader.setAlignment(Pos.CENTER_LEFT);

        Text mapIcon = new Text("⌖");
        mapIcon.setStyle(FONT_STACK + "-fx-font-size: 22px; -fx-fill: " + PURPLE_DARK + ";");

        Text trackingTitle = new Text("Live Tracking");
        trackingTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Region trackingSpacer = new Region();
        HBox.setHgrow(trackingSpacer, Priority.ALWAYS);

        Circle liveCircle = new Circle(4.5);
        liveCircle.setFill(Color.web("#22c55e"));

        Text liveText = new Text("Live");
        liveText.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + SUCCESS_TEXT + ";");

        HBox liveBox = new HBox(5);
        liveBox.setAlignment(Pos.CENTER);
        liveBox.setStyle("-fx-background-color: " + SUCCESS_BG + "; -fx-padding: 3px 8px; -fx-background-radius: 10px;");
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

        // MAP CANVAS SIMULATION
        StackPane mapPane = new StackPane();
        mapPane.setPrefHeight(430);
        mapPane.setStyle("-fx-background-color: #F1ECF9;");

        Line road1 = new Line(0, 80, 500, 280);
        road1.setStroke(Color.web("#FFFFFF"));
        road1.setStrokeWidth(12);

        Line road2 = new Line(80, 0, 300, 430);
        road2.setStroke(Color.web("#FFFFFF"));
        road2.setStrokeWidth(10);

        Line road3 = new Line(500, 100, 100, 350);
        road3.setStroke(Color.web("#FFFFFF"));
        road3.setStrokeWidth(9);

        Line routeLine = new Line(80, 350, 420, 100);
        routeLine.setStroke(Color.web(PURPLE_PRIMARY));
        routeLine.setStrokeWidth(4);

        Circle markerCircle1 = new Circle(18);
        markerCircle1.setFill(Color.web(PURPLE_DARK));

        Text markerText1 = new Text("🚑");
        markerText1.setStyle("-fx-font-size: 14px;");

        StackPane marker1 = new StackPane();
        marker1.setLayoutX(120);
        marker1.setLayoutY(130);
        marker1.getChildren().addAll(
                markerCircle1,
                markerText1
        );

        Circle markerCircle2 = new Circle(18);
        markerCircle2.setFill(Color.web(DANGER_TEXT));

        Text markerText2 = new Text("!");
        markerText2.setStyle(FONT_STACK + "-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: white;");

        StackPane marker2 = new StackPane();
        marker2.setLayoutX(350);
        marker2.setLayoutY(270);
        marker2.getChildren().addAll(
                markerCircle2,
                markerText2
        );

        Text ambulanceLabel1 = new Text("AMB-1024");
        ambulanceLabel1.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        StackPane labelPane1 = new StackPane(ambulanceLabel1);
        labelPane1.setStyle("-fx-background-color: white; -fx-padding: 4px 8px; -fx-background-radius: 6px; -fx-effect: dropshadow(gaussian, rgba(156, 125, 240, 0.15), 6, 0.1, 0, 2);");
        labelPane1.setLayoutX(105);
        labelPane1.setLayoutY(90);

        Text ambulanceLabel2 = new Text("EMERGENCY");
        ambulanceLabel2.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + DANGER_TEXT + ";");

        StackPane labelPane2 = new StackPane(ambulanceLabel2);
        labelPane2.setStyle("-fx-background-color: white; -fx-padding: 4px 8px; -fx-background-radius: 6px; -fx-effect: dropshadow(gaussian, rgba(230, 106, 122, 0.15), 6, 0.1, 0, 2);");
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
        mapFooter.setPadding(new Insets(12, 18, 12, 18));
        mapFooter.setAlignment(Pos.CENTER_LEFT);
        mapFooter.setStyle("-fx-background-color: " + PURPLE_LIGHT + ";");

        Text districtText = new Text("Emergency Control Center");
        districtText.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + PURPLE_DARK + "; -fx-font-weight: 500;");

        Region footerSpacer = new Region();
        HBox.setHgrow(footerSpacer, Priority.ALWAYS);

        Button fullViewButton = new Button("Full View");
        fullViewButton.setStyle(FONT_STACK + "-fx-background-color: transparent; -fx-text-fill: " + PURPLE_DARK + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-cursor: hand;");

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

        // ROOT ASSEMBLY
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
        finalContent.setStyle("-fx-background-color: " + BG_PAGE + ";");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return finalContent;
    }
}