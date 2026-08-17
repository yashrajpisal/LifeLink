// package com.kurukshetra.view.hospital;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.chart.BarChart;
// import javafx.scene.chart.CategoryAxis;
// import javafx.scene.chart.LineChart;
// import javafx.scene.chart.NumberAxis;
// import javafx.scene.chart.PieChart;
// import javafx.scene.chart.XYChart;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.GridPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.VBox;

// public class HospitalAnalytics {

//     public VBox getAnalytics() {

//         // ========================= MAIN PAGE =========================

//         VBox mainVBox = new VBox();
//         mainVBox.setStyle("-fx-background-color: #faf8ff;");
//         mainVBox.setPrefWidth(1100);
//         mainVBox.setPrefHeight(750);

//         // ========================= TOP BAR =========================

//         HBox topBar = new HBox();
//         topBar.setPadding(new Insets(14, 24, 14, 24));
//         topBar.setAlignment(Pos.CENTER_LEFT);
//         topBar.setSpacing(20);
//         topBar.setStyle("-fx-background-color: #faf8ff; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

//         TextField searchField = new TextField();
//         searchField.setPromptText("Search analytics data...");
//         searchField.setPrefWidth(320);
//         searchField.setPrefHeight(36);
//         searchField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-padding: 8px 15px; -fx-font-size: 13px;");

//         HBox.setHgrow(searchField, Priority.ALWAYS);

//         HBox topRight = new HBox();
//         topRight.setSpacing(12);
//         topRight.setAlignment(Pos.CENTER_RIGHT);

//         Button notificationButton = new Button("🔔");
//         notificationButton.setPrefSize(38, 38);
//         notificationButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-background-radius: 50%;");

//         Button settingsButton = new Button("⚙");
//         settingsButton.setPrefSize(38, 38);
//         settingsButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-background-radius: 50%;");

//         Label adminView = new Label("Admin View");
//         adminView.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         topRight.getChildren().addAll(
//                 notificationButton,
//                 settingsButton,
//                 adminView
//         );

//         topBar.getChildren().addAll(
//                 searchField,
//                 topRight
//         );

//         // ========================= MAIN CONTENT =========================

//         VBox contentVBox = new VBox();
//         contentVBox.setPadding(new Insets(24));
//         contentVBox.setSpacing(18);
//         contentVBox.setStyle("-fx-background-color: #faf8ff;");

//         // ========================= ANALYTICS HEADER =========================

//         HBox analyticsHeader = new HBox();
//         analyticsHeader.setAlignment(Pos.BOTTOM_LEFT);
//         analyticsHeader.setSpacing(20);

//         VBox titleBox = new VBox();
//         titleBox.setSpacing(4);

//         Label pageTitle = new Label("Analytics Overview");
//         pageTitle.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         Label pageDescription = new Label(
//                 "Real-time performance metrics and hospital capacity data."
//         );
//         pageDescription.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

//         titleBox.getChildren().addAll(
//                 pageTitle,
//                 pageDescription
//         );

//         HBox.setHgrow(titleBox, Priority.ALWAYS);

//         HBox headerButtons = new HBox();
//         headerButtons.setSpacing(8);

//         Button dateButton = new Button("▣   Last 30 Days");
//         dateButton.setPrefHeight(38);
//         dateButton.setStyle("-fx-background-color: #e1e2ed; -fx-text-fill: #191b23; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8px 14px;");

//         Button exportButton = new Button("↓   Export Report");
//         exportButton.setPrefHeight(38);
//         exportButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-background-radius: 8px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8px 14px;");

//         headerButtons.getChildren().addAll(
//                 dateButton,
//                 exportButton
//         );

//         analyticsHeader.getChildren().addAll(
//                 titleBox,
//                 headerButtons
//         );

//         // ========================= STATISTICS CARDS =========================

//         HBox statisticsCards = new HBox();
//         statisticsCards.setSpacing(14);

//         // Today's Emergencies

//         VBox emergencyCard = new VBox();
//         emergencyCard.setPadding(new Insets(16));
//         emergencyCard.setSpacing(8);
//         emergencyCard.setPrefHeight(100);
//         emergencyCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         HBox emergencyContent = new HBox();
//         emergencyContent.setSpacing(12);
//         emergencyContent.setAlignment(Pos.CENTER_LEFT);

//         Label emergencyIcon = new Label("🚨");
//         emergencyIcon.setPrefSize(48, 48);
//         emergencyIcon.setAlignment(Pos.CENTER);
//         emergencyIcon.setStyle("-fx-background-color: #ffdad6; -fx-background-radius: 10px; -fx-font-size: 22px;");

//         VBox emergencyDetails = new VBox();
//         emergencyDetails.setSpacing(4);

//         Label emergencyTitle = new Label("TODAY'S EMERGENCIES");
//         emergencyTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

//         Label emergencyValue = new Label("42");
//         emergencyValue.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         Label emergencyChange = new Label("+12% ↑");
//         emergencyChange.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #ba1a1a;");

//         HBox emergencyValueBox = new HBox();
//         emergencyValueBox.setSpacing(8);
//         emergencyValueBox.setAlignment(Pos.CENTER_LEFT);

//         emergencyValueBox.getChildren().addAll(
//                 emergencyValue,
//                 emergencyChange
//         );

//         emergencyDetails.getChildren().addAll(
//                 emergencyTitle,
//                 emergencyValueBox
//         );

//         emergencyContent.getChildren().addAll(
//                 emergencyIcon,
//                 emergencyDetails
//         );

//         emergencyCard.getChildren().add(emergencyContent);

//         // Monthly Emergencies

//         VBox monthlyCard = new VBox();
//         monthlyCard.setPadding(new Insets(16));
//         monthlyCard.setSpacing(8);
//         monthlyCard.setPrefHeight(100);
//         monthlyCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         HBox monthlyContent = new HBox();
//         monthlyContent.setSpacing(12);
//         monthlyContent.setAlignment(Pos.CENTER_LEFT);

//         Label monthlyIcon = new Label("📈");
//         monthlyIcon.setPrefSize(48, 48);
//         monthlyIcon.setAlignment(Pos.CENTER);
//         monthlyIcon.setStyle("-fx-background-color: #dbe1ff; -fx-background-radius: 10px; -fx-font-size: 22px;");

//         VBox monthlyDetails = new VBox();
//         monthlyDetails.setSpacing(4);

//         Label monthlyTitle = new Label("MONTHLY EMERGENCIES");
//         monthlyTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

//         Label monthlyValue = new Label("1.2k");
//         monthlyValue.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         Label monthlyChange = new Label("-3% ↓");
//         monthlyChange.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #10b981;");

//         HBox monthlyValueBox = new HBox();
//         monthlyValueBox.setSpacing(8);
//         monthlyValueBox.setAlignment(Pos.CENTER_LEFT);

//         monthlyValueBox.getChildren().addAll(
//                 monthlyValue,
//                 monthlyChange
//         );

//         monthlyDetails.getChildren().addAll(
//                 monthlyTitle,
//                 monthlyValueBox
//         );

//         monthlyContent.getChildren().addAll(
//                 monthlyIcon,
//                 monthlyDetails
//         );

//         monthlyCard.getChildren().add(monthlyContent);

//         // Average ETA

//         VBox etaCard = new VBox();
//         etaCard.setPadding(new Insets(16));
//         etaCard.setSpacing(8);
//         etaCard.setPrefHeight(100);
//         etaCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         HBox etaContent = new HBox();
//         etaContent.setSpacing(12);
//         etaContent.setAlignment(Pos.CENTER_LEFT);

//         Label etaIcon = new Label("⏱");
//         etaIcon.setPrefSize(48, 48);
//         etaIcon.setAlignment(Pos.CENTER);
//         etaIcon.setStyle("-fx-background-color: #d0e1fb; -fx-background-radius: 10px; -fx-font-size: 22px;");

//         VBox etaDetails = new VBox();
//         etaDetails.setSpacing(4);

//         Label etaTitle = new Label("AVERAGE ETA");
//         etaTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

//         Label etaValue = new Label("4.2m");
//         etaValue.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         Label etaChange = new Label("-0.5m ↓");
//         etaChange.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #10b981;");

//         HBox etaValueBox = new HBox();
//         etaValueBox.setSpacing(8);
//         etaValueBox.setAlignment(Pos.CENTER_LEFT);

//         etaValueBox.getChildren().addAll(
//                 etaValue,
//                 etaChange
//         );

//         etaDetails.getChildren().addAll(
//                 etaTitle,
//                 etaValueBox
//         );

//         etaContent.getChildren().addAll(
//                 etaIcon,
//                 etaDetails
//         );

//         etaCard.getChildren().add(etaContent);

//         // Recovery Rate

//         VBox recoveryCard = new VBox();
//         recoveryCard.setPadding(new Insets(16));
//         recoveryCard.setSpacing(8);
//         recoveryCard.setPrefHeight(100);
//         recoveryCard.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         HBox recoveryContent = new HBox();
//         recoveryContent.setSpacing(12);
//         recoveryContent.setAlignment(Pos.CENTER_LEFT);

//         Label recoveryIcon = new Label("❤");
//         recoveryIcon.setPrefSize(48, 48);
//         recoveryIcon.setAlignment(Pos.CENTER);
//         recoveryIcon.setStyle("-fx-background-color: #ffdbcd; -fx-background-radius: 10px; -fx-font-size: 22px;");

//         VBox recoveryDetails = new VBox();
//         recoveryDetails.setSpacing(4);

//         Label recoveryTitle = new Label("RECOVERY RATE");
//         recoveryTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

//         Label recoveryValue = new Label("94%");
//         recoveryValue.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         Label recoveryChange = new Label("+1.2% ↑");
//         recoveryChange.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #10b981;");

//         HBox recoveryValueBox = new HBox();
//         recoveryValueBox.setSpacing(8);
//         recoveryValueBox.setAlignment(Pos.CENTER_LEFT);

//         recoveryValueBox.getChildren().addAll(
//                 recoveryValue,
//                 recoveryChange
//         );

//         recoveryDetails.getChildren().addAll(
//                 recoveryTitle,
//                 recoveryValueBox
//         );

//         recoveryContent.getChildren().addAll(
//                 recoveryIcon,
//                 recoveryDetails
//         );

//         recoveryCard.getChildren().add(recoveryContent);

//         HBox.setHgrow(emergencyCard, Priority.ALWAYS);
//         HBox.setHgrow(monthlyCard, Priority.ALWAYS);
//         HBox.setHgrow(etaCard, Priority.ALWAYS);
//         HBox.setHgrow(recoveryCard, Priority.ALWAYS);

//         statisticsCards.getChildren().addAll(
//                 emergencyCard,
//                 monthlyCard,
//                 etaCard,
//                 recoveryCard
//         );

//         // ========================= CHART ROW 1 =========================

//         HBox chartRow1 = new HBox();
//         chartRow1.setSpacing(14);

//         // ========================= EMERGENCY CASE TRENDS =========================

//         VBox emergencyChartBox = new VBox();
//         emergencyChartBox.setPadding(new Insets(16));
//         emergencyChartBox.setSpacing(12);
//         emergencyChartBox.setPrefHeight(300);
//         emergencyChartBox.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         HBox emergencyChartHeader = new HBox();
//         emergencyChartHeader.setAlignment(Pos.CENTER_LEFT);

//         Label emergencyChartTitle = new Label("Emergency Case Trends");
//         emergencyChartTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         HBox.setHgrow(emergencyChartTitle, Priority.ALWAYS);

//         HBox chartLegend = new HBox();
//         chartLegend.setSpacing(12);

//         Label criticalLegend = new Label("● Critical");
//         criticalLegend.setStyle("-fx-font-size: 11px; -fx-text-fill: #004ac6;");

//         Label standardLegend = new Label("● Standard");
//         standardLegend.setStyle("-fx-font-size: 11px; -fx-text-fill: #505f76;");

//         chartLegend.getChildren().addAll(
//                 criticalLegend,
//                 standardLegend
//         );

//         emergencyChartHeader.getChildren().addAll(
//                 emergencyChartTitle,
//                 chartLegend
//         );

//         CategoryAxis emergencyXAxis = new CategoryAxis();
//         emergencyXAxis.setLabel("Time");

//         NumberAxis emergencyYAxis = new NumberAxis();
//         emergencyYAxis.setLabel("Cases");

//         LineChart<String, Number> emergencyChart =
//                 new LineChart<>(emergencyXAxis, emergencyYAxis);

//         emergencyChart.setLegendVisible(false);
//         emergencyChart.setAnimated(false);
//         emergencyChart.setCreateSymbols(true);
//         emergencyChart.setPrefHeight(220);

//         XYChart.Series<String, Number> criticalSeries =
//                 new XYChart.Series<>();

//         criticalSeries.setName("Critical");

//         criticalSeries.getData().add(new XYChart.Data<>("00:00", 12));
//         criticalSeries.getData().add(new XYChart.Data<>("04:00", 19));
//         criticalSeries.getData().add(new XYChart.Data<>("08:00", 3));
//         criticalSeries.getData().add(new XYChart.Data<>("12:00", 5));
//         criticalSeries.getData().add(new XYChart.Data<>("16:00", 2));
//         criticalSeries.getData().add(new XYChart.Data<>("20:00", 3));
//         criticalSeries.getData().add(new XYChart.Data<>("23:59", 9));

//         XYChart.Series<String, Number> standardSeries =
//                 new XYChart.Series<>();

//         standardSeries.setName("Standard");

//         standardSeries.getData().add(new XYChart.Data<>("00:00", 22));
//         standardSeries.getData().add(new XYChart.Data<>("04:00", 14));
//         standardSeries.getData().add(new XYChart.Data<>("08:00", 25));
//         standardSeries.getData().add(new XYChart.Data<>("12:00", 28));
//         standardSeries.getData().add(new XYChart.Data<>("16:00", 18));
//         standardSeries.getData().add(new XYChart.Data<>("20:00", 22));
//         standardSeries.getData().add(new XYChart.Data<>("23:59", 25));

//         emergencyChart.getData().addAll(
//                 criticalSeries,
//                 standardSeries
//         );

//         emergencyChart.setStyle(
//                 "-fx-background-color: transparent; " +
//                 "-fx-border-color: transparent;"
//         );

//         emergencyChartBox.getChildren().addAll(
//                 emergencyChartHeader,
//                 emergencyChart
//         );

//         // ========================= BED OCCUPANCY =========================

//         VBox bedChartBox = new VBox();
//         bedChartBox.setPadding(new Insets(16));
//         bedChartBox.setSpacing(10);
//         bedChartBox.setPrefWidth(360);
//         bedChartBox.setPrefHeight(300);
//         bedChartBox.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         Label bedTitle = new Label("Bed Occupancy");
//         bedTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         PieChart bedChart = new PieChart();

//         PieChart.Data occupiedData =
//                 new PieChart.Data("Occupied", 65);

//         PieChart.Data availableData =
//                 new PieChart.Data("Available", 20);

//         PieChart.Data maintenanceData =
//                 new PieChart.Data("Maintenance", 5);

//         PieChart.Data reservedData =
//                 new PieChart.Data("Reserved", 10);

//         bedChart.getData().addAll(
//                 occupiedData,
//                 availableData,
//                 maintenanceData,
//                 reservedData
//         );

//         bedChart.setLegendVisible(true);
//         bedChart.setLabelsVisible(false);
//         bedChart.setStartAngle(90);
//         bedChart.setPrefHeight(230);

//         bedChartBox.getChildren().addAll(
//                 bedTitle,
//                 bedChart
//         );

//         HBox.setHgrow(emergencyChartBox, Priority.ALWAYS);

//         chartRow1.getChildren().addAll(
//                 emergencyChartBox,
//                 bedChartBox
//         );

//         // ========================= CHART ROW 2 =========================

//         HBox chartRow2 = new HBox();
//         chartRow2.setSpacing(14);

//         // ========================= ICU USAGE =========================

//         VBox icuChartBox = new VBox();
//         icuChartBox.setPadding(new Insets(16));
//         icuChartBox.setSpacing(10);
//         icuChartBox.setPrefHeight(300);
//         icuChartBox.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         Label icuTitle = new Label("ICU Usage by Department");
//         icuTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         CategoryAxis icuXAxis = new CategoryAxis();
//         NumberAxis icuYAxis = new NumberAxis();

//         BarChart<String, Number> icuChart =
//                 new BarChart<>(icuXAxis, icuYAxis);

//         icuChart.setLegendVisible(false);
//         icuChart.setAnimated(false);
//         icuChart.setPrefHeight(230);

//         XYChart.Series<String, Number> activePatients =
//                 new XYChart.Series<>();

//         activePatients.setName("Active Patients");

//         activePatients.getData().add(
//                 new XYChart.Data<>("Cardio", 14)
//         );

//         activePatients.getData().add(
//                 new XYChart.Data<>("Neuro", 11)
//         );

//         activePatients.getData().add(
//                 new XYChart.Data<>("Trauma", 22)
//         );

//         activePatients.getData().add(
//                 new XYChart.Data<>("Pediatric", 8)
//         );

//         icuChart.getData().add(activePatients);

//         icuChartBox.getChildren().addAll(
//                 icuTitle,
//                 icuChart
//         );

//         // ========================= DOCTOR AVAILABILITY HEATMAP =========================

//         VBox doctorHeatmapBox = new VBox();
//         doctorHeatmapBox.setPadding(new Insets(16));
//         doctorHeatmapBox.setSpacing(10);
//         doctorHeatmapBox.setPrefHeight(300);
//         doctorHeatmapBox.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         Label doctorHeatmapTitle =
//                 new Label("Doctor Availability Heatmap");

//         doctorHeatmapTitle.setStyle(
//                 "-fx-font-size: 18px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-text-fill: #191b23;"
//         );

//         GridPane heatmap = new GridPane();
//         heatmap.setHgap(5);
//         heatmap.setVgap(5);
//         heatmap.setPadding(new Insets(5));

//         String[] days = {
//                 "Mon", "Tue", "Wed",
//                 "Thu", "Fri", "Sat", "Sun"
//         };

//         // Day labels

//         for (int i = 0; i < days.length; i++) {

//             Label dayLabel = new Label(days[i]);

//             dayLabel.setStyle(
//                     "-fx-font-size: 10px; " +
//                     "-fx-font-weight: bold; " +
//                     "-fx-text-fill: #434655;"
//             );

//             dayLabel.setAlignment(Pos.CENTER);

//             heatmap.add(dayLabel, i, 0);
//         }

//         int[] heatValues = {
//                 30, 75, 90, 45, 65, 25, 80,
//                 55, 85, 40, 70, 95, 30, 60,
//                 90, 45, 65, 80, 35, 70, 50,
//                 25, 60, 85, 45, 75, 95, 40
//         };

//         for (int row = 1; row <= 4; row++) {

//             for (int col = 0; col < 7; col++) {

//                 int index = (row - 1) * 7 + col;

//                 Label cell = new Label();

//                 cell.setPrefSize(28, 28);

//                 int value = heatValues[index];

//                 String backgroundColor;

//                 if (value >= 80) {
//                     backgroundColor = "#ef4444";
//                 } else if (value >= 60) {
//                     backgroundColor = "#f59e0b";
//                 } else if (value >= 30) {
//                     backgroundColor = "#10b981";
//                 } else {
//                     backgroundColor = "#e1e2ed";
//                 }

//                 cell.setStyle(
//                         "-fx-background-color: " + backgroundColor + ";" +
//                         "-fx-background-radius: 4px;"
//                 );

//                 cell.setTooltip(
//                         new javafx.scene.control.Tooltip(
//                                 "Doctor Load: " + value + "%"
//                         )
//                 );

//                 heatmap.add(cell, col, row);
//             }
//         }

//         HBox heatmapLegend = new HBox();
//         heatmapLegend.setSpacing(12);
//         heatmapLegend.setAlignment(Pos.CENTER);

//         Label low = new Label("Low");
//         low.setStyle("-fx-font-size: 10px; -fx-text-fill: #434655;");

//         Label medium = new Label("Medium");
//         medium.setStyle("-fx-font-size: 10px; -fx-text-fill: #434655;");

//         Label high = new Label("High");
//         high.setStyle("-fx-font-size: 10px; -fx-text-fill: #434655;");

//         heatmapLegend.getChildren().addAll(
//                 low,
//                 medium,
//                 high
//         );

//         doctorHeatmapBox.getChildren().addAll(
//                 doctorHeatmapTitle,
//                 heatmap,
//                 heatmapLegend
//         );

//         // ========================= RESPONSE TIME =========================

//         VBox responseChartBox = new VBox();
//         responseChartBox.setPadding(new Insets(16));
//         responseChartBox.setSpacing(10);
//         responseChartBox.setPrefHeight(300);
//         responseChartBox.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         Label responseTitle =
//                 new Label("Avg Response Time (min)");

//         responseTitle.setStyle(
//                 "-fx-font-size: 18px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-text-fill: #191b23;"
//         );

//         CategoryAxis responseXAxis = new CategoryAxis();
//         NumberAxis responseYAxis = new NumberAxis();

//         responseXAxis.setLabel("Day");
//         responseYAxis.setLabel("Minutes");

//         LineChart<String, Number> responseChart =
//                 new LineChart<>(responseXAxis, responseYAxis);

//         responseChart.setLegendVisible(false);
//         responseChart.setAnimated(false);
//         responseChart.setPrefHeight(230);

//         XYChart.Series<String, Number> responseSeries =
//                 new XYChart.Series<>();

//         responseSeries.setName("Response Time");

//         responseSeries.getData().add(
//                 new XYChart.Data<>("Mon", 4.5)
//         );

//         responseSeries.getData().add(
//                 new XYChart.Data<>("Tue", 4.2)
//         );

//         responseSeries.getData().add(
//                 new XYChart.Data<>("Wed", 5.1)
//         );

//         responseSeries.getData().add(
//                 new XYChart.Data<>("Thu", 4.0)
//         );

//         responseSeries.getData().add(
//                 new XYChart.Data<>("Fri", 3.8)
//         );

//         responseSeries.getData().add(
//                 new XYChart.Data<>("Sat", 4.4)
//         );

//         responseSeries.getData().add(
//                 new XYChart.Data<>("Sun", 4.2)
//         );

//         responseChart.getData().add(responseSeries);

//         responseChartBox.getChildren().addAll(
//                 responseTitle,
//                 responseChart
//         );

//         HBox.setHgrow(icuChartBox, Priority.ALWAYS);
//         HBox.setHgrow(doctorHeatmapBox, Priority.ALWAYS);
//         HBox.setHgrow(responseChartBox, Priority.ALWAYS);

//         chartRow2.getChildren().addAll(
//                 icuChartBox,
//                 doctorHeatmapBox,
//                 responseChartBox
//         );

//         // ========================= ADD CONTENT =========================

//         contentVBox.getChildren().addAll(
//                 analyticsHeader,
//                 statisticsCards,
//                 chartRow1,
//                 chartRow2
//         );

//         // ========================= SCROLL PANE =========================

//         ScrollPane scrollPane = new ScrollPane(contentVBox);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setFitToHeight(false);
//         scrollPane.setStyle(
//                 "-fx-background-color: #faf8ff; " +
//                 "-fx-background: #faf8ff;"
//         );

//         VBox analyticsPage = new VBox(scrollPane);

//         VBox.setVgrow(scrollPane, Priority.ALWAYS);

//         // ========================= FINAL PAGE =========================

//         mainVBox.getChildren().addAll(
//                 topBar,
//                 analyticsPage
//         );

//         VBox.setVgrow(analyticsPage, Priority.ALWAYS);

//         return mainVBox;
//     }
// }

package com.kurukshetra.view.hospital;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HospitalAnalytics {

    private static final String PRIMARY_TEAL    = "#087F8C";
    private static final String TEAL_DARK       = "#056D79";
    private static final String TEAL_VERY_LIGHT = "#EAF8F9";
    private static final String TEAL_LIGHT      = "#DDF3F5";
    private static final String TEAL_SOFT       = "#CDECEF";
    private static final String TEAL_PALE       = "#F2FBFB";

    private static final String PAGE_BG         = "#F7FBFC";
    private static final String SURFACE         = "#FFFFFF";
    private static final String BORDER_COLOR    = "#DCECEF";

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

    public VBox getAnalytics() {

        VBox mainVBox = new VBox();
        mainVBox.setStyle("-fx-background-color: " + PAGE_BG + ";");
        mainVBox.setPrefWidth(1100);
        mainVBox.setPrefHeight(750);

        // ========================= TOP BAR =========================
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(14, 24, 14, 24));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);
        topBar.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0px 0px 1px 0px;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search analytics data...");
        searchField.setPrefWidth(320);
        searchField.setPrefHeight(36);
        searchField.setStyle(
            "-fx-background-color: " + SURFACE + ";" +
            "-fx-border-color: " + BORDER_COLOR + ";" +
            "-fx-border-radius: 20px;" +
            "-fx-background-radius: 20px;" +
            "-fx-padding: 8px 15px;" +
            "-fx-font-size: 13px;" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-prompt-text-fill: " + TEXT_MUTED + ";"
        );
        HBox.setHgrow(searchField, Priority.ALWAYS);

        HBox topRight = new HBox();
        topRight.setSpacing(12);
        topRight.setAlignment(Pos.CENTER_RIGHT);

        Button notificationButton = new Button("🔔");
        notificationButton.setPrefSize(38, 38);
        notificationButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-background-radius: 50%; -fx-cursor: hand;");

        Button settingsButton = new Button("⚙");
        settingsButton.setPrefSize(38, 38);
        settingsButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-background-radius: 50%; -fx-cursor: hand;");

        Label adminView = new Label("Admin View");
        adminView.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        topRight.getChildren().addAll(notificationButton, settingsButton, adminView);
        topBar.getChildren().addAll(searchField, topRight);

        // ========================= MAIN CONTENT =========================
        VBox contentVBox = new VBox();
        contentVBox.setPadding(new Insets(24));
        contentVBox.setSpacing(18);
        contentVBox.setStyle("-fx-background-color: " + PAGE_BG + ";");

        // Header
        HBox analyticsHeader = new HBox();
        analyticsHeader.setAlignment(Pos.BOTTOM_LEFT);
        analyticsHeader.setSpacing(20);

        VBox titleBox = new VBox();
        titleBox.setSpacing(4);

        Label pageTitle = new Label("Analytics Overview");
        pageTitle.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label pageDescription = new Label("Real-time performance metrics and hospital capacity data.");
        pageDescription.setStyle("-fx-font-size: 13px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        titleBox.getChildren().addAll(pageTitle, pageDescription);
        HBox.setHgrow(titleBox, Priority.ALWAYS);

        HBox headerButtons = new HBox();
        headerButtons.setSpacing(8);

        Button dateButton = new Button("▣   Last 30 Days");
        dateButton.setPrefHeight(38);
        dateButton.setStyle("-fx-background-color: " + SURFACE + "; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8px 14px; -fx-cursor: hand;");

        Button exportButton = new Button("↓   Export Report");
        exportButton.setPrefHeight(38);
        exportButton.setStyle("-fx-background-color: " + PRIMARY_TEAL + "; -fx-text-fill: white; -fx-background-radius: 8px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8px 14px; -fx-cursor: hand;");
        exportButton.setOnMouseEntered(e -> exportButton.setStyle("-fx-background-color: " + TEAL_DARK + "; -fx-text-fill: white; -fx-background-radius: 8px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8px 14px; -fx-cursor: hand;"));
        exportButton.setOnMouseExited(e -> exportButton.setStyle("-fx-background-color: " + PRIMARY_TEAL + "; -fx-text-fill: white; -fx-background-radius: 8px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 8px 14px; -fx-cursor: hand;"));

        headerButtons.getChildren().addAll(dateButton, exportButton);
        analyticsHeader.getChildren().addAll(titleBox, headerButtons);

        // ========================= STATISTICS CARDS =========================
        HBox statisticsCards = new HBox();
        statisticsCards.setSpacing(14);

        VBox emergencyCard = createStatCard("🚨", STATUS_DANGER_BG, "TODAY'S EMERGENCIES", "42", "+12% ↑", STATUS_DANGER_TEXT);
        VBox monthlyCard   = createStatCard("📈", TEAL_VERY_LIGHT, "MONTHLY EMERGENCIES", "1.2k", "-3% ↓", STATUS_SUCCESS_TEXT);
        VBox etaCard       = createStatCard("⏱", TEAL_LIGHT, "AVERAGE ETA", "4.2m", "-0.5m ↓", STATUS_SUCCESS_TEXT);
        VBox recoveryCard  = createStatCard("❤", STATUS_SUCCESS_BG, "RECOVERY RATE", "94%", "+1.2% ↑", STATUS_SUCCESS_TEXT);

        HBox.setHgrow(emergencyCard, Priority.ALWAYS);
        HBox.setHgrow(monthlyCard, Priority.ALWAYS);
        HBox.setHgrow(etaCard, Priority.ALWAYS);
        HBox.setHgrow(recoveryCard, Priority.ALWAYS);

        statisticsCards.getChildren().addAll(emergencyCard, monthlyCard, etaCard, recoveryCard);

        // ========================= CHART ROW 1 =========================
        HBox chartRow1 = new HBox();
        chartRow1.setSpacing(14);

        // Emergency Trends
        VBox emergencyChartBox = new VBox();
        emergencyChartBox.setPadding(new Insets(16));
        emergencyChartBox.setSpacing(12);
        emergencyChartBox.setPrefHeight(300);
        emergencyChartBox.setStyle(CARD_STYLE);

        HBox emergencyChartHeader = new HBox();
        emergencyChartHeader.setAlignment(Pos.CENTER_LEFT);

        Label emergencyChartTitle = new Label("Emergency Case Trends");
        emergencyChartTitle.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");
        HBox.setHgrow(emergencyChartTitle, Priority.ALWAYS);

        HBox chartLegend = new HBox();
        chartLegend.setSpacing(12);

        Label criticalLegend = new Label("● Critical");
        criticalLegend.setStyle("-fx-font-size: 11px; -fx-text-fill: " + PRIMARY_TEAL + ";");

        Label standardLegend = new Label("● Standard");
        standardLegend.setStyle("-fx-font-size: 11px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        chartLegend.getChildren().addAll(criticalLegend, standardLegend);
        emergencyChartHeader.getChildren().addAll(emergencyChartTitle, chartLegend);

        CategoryAxis emergencyXAxis = new CategoryAxis();
        emergencyXAxis.setLabel("Time");

        NumberAxis emergencyYAxis = new NumberAxis();
        emergencyYAxis.setLabel("Cases");

        LineChart<String, Number> emergencyChart = new LineChart<>(emergencyXAxis, emergencyYAxis);
        emergencyChart.setLegendVisible(false);
        emergencyChart.setAnimated(false);
        emergencyChart.setCreateSymbols(true);
        emergencyChart.setPrefHeight(220);

        XYChart.Series<String, Number> criticalSeries = new XYChart.Series<>();
        criticalSeries.setName("Critical");
        criticalSeries.getData().add(new XYChart.Data<>("00:00", 12));
        criticalSeries.getData().add(new XYChart.Data<>("04:00", 19));
        criticalSeries.getData().add(new XYChart.Data<>("08:00", 3));
        criticalSeries.getData().add(new XYChart.Data<>("12:00", 5));
        criticalSeries.getData().add(new XYChart.Data<>("16:00", 2));
        criticalSeries.getData().add(new XYChart.Data<>("20:00", 3));
        criticalSeries.getData().add(new XYChart.Data<>("23:59", 9));

        XYChart.Series<String, Number> standardSeries = new XYChart.Series<>();
        standardSeries.setName("Standard");
        standardSeries.getData().add(new XYChart.Data<>("00:00", 22));
        standardSeries.getData().add(new XYChart.Data<>("04:00", 14));
        standardSeries.getData().add(new XYChart.Data<>("08:00", 25));
        standardSeries.getData().add(new XYChart.Data<>("12:00", 28));
        standardSeries.getData().add(new XYChart.Data<>("16:00", 18));
        standardSeries.getData().add(new XYChart.Data<>("20:00", 22));
        standardSeries.getData().add(new XYChart.Data<>("23:59", 25));

        emergencyChart.getData().addAll(criticalSeries, standardSeries);
        emergencyChart.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        emergencyChartBox.getChildren().addAll(emergencyChartHeader, emergencyChart);

        // Bed Occupancy
        VBox bedChartBox = new VBox();
        bedChartBox.setPadding(new Insets(16));
        bedChartBox.setSpacing(10);
        bedChartBox.setPrefWidth(360);
        bedChartBox.setPrefHeight(300);
        bedChartBox.setStyle(CARD_STYLE);

        Label bedTitle = new Label("Bed Occupancy");
        bedTitle.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        PieChart bedChart = new PieChart();
        PieChart.Data occupiedData = new PieChart.Data("Occupied", 65);
        PieChart.Data availableData = new PieChart.Data("Available", 20);
        PieChart.Data maintenanceData = new PieChart.Data("Maintenance", 5);
        PieChart.Data reservedData = new PieChart.Data("Reserved", 10);

        bedChart.getData().addAll(occupiedData, availableData, maintenanceData, reservedData);
        bedChart.setLegendVisible(true);
        bedChart.setLabelsVisible(false);
        bedChart.setStartAngle(90);
        bedChart.setPrefHeight(230);

        bedChartBox.getChildren().addAll(bedTitle, bedChart);
        HBox.setHgrow(emergencyChartBox, Priority.ALWAYS);
        chartRow1.getChildren().addAll(emergencyChartBox, bedChartBox);

        // ========================= CHART ROW 2 =========================
        HBox chartRow2 = new HBox();
        chartRow2.setSpacing(14);

        // ICU Usage
        VBox icuChartBox = new VBox();
        icuChartBox.setPadding(new Insets(16));
        icuChartBox.setSpacing(10);
        icuChartBox.setPrefHeight(300);
        icuChartBox.setStyle(CARD_STYLE);

        Label icuTitle = new Label("ICU Usage by Department");
        icuTitle.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        CategoryAxis icuXAxis = new CategoryAxis();
        NumberAxis icuYAxis = new NumberAxis();
        BarChart<String, Number> icuChart = new BarChart<>(icuXAxis, icuYAxis);
        icuChart.setLegendVisible(false);
        icuChart.setAnimated(false);
        icuChart.setPrefHeight(230);

        XYChart.Series<String, Number> activePatients = new XYChart.Series<>();
        activePatients.setName("Active Patients");
        activePatients.getData().add(new XYChart.Data<>("Cardio", 14));
        activePatients.getData().add(new XYChart.Data<>("Neuro", 11));
        activePatients.getData().add(new XYChart.Data<>("Trauma", 22));
        activePatients.getData().add(new XYChart.Data<>("Pediatric", 8));

        icuChart.getData().add(activePatients);
        icuChartBox.getChildren().addAll(icuTitle, icuChart);

        // Doctor Heatmap
        VBox doctorHeatmapBox = new VBox();
        doctorHeatmapBox.setPadding(new Insets(16));
        doctorHeatmapBox.setSpacing(10);
        doctorHeatmapBox.setPrefHeight(300);
        doctorHeatmapBox.setStyle(CARD_STYLE);

        Label doctorHeatmapTitle = new Label("Doctor Availability Heatmap");
        doctorHeatmapTitle.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        GridPane heatmap = new GridPane();
        heatmap.setHgap(5);
        heatmap.setVgap(5);
        heatmap.setPadding(new Insets(5));

        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");
            dayLabel.setAlignment(Pos.CENTER);
            heatmap.add(dayLabel, i, 0);
        }

        int[] heatValues = {
            30, 75, 90, 45, 65, 25, 80,
            55, 85, 40, 70, 95, 30, 60,
            90, 45, 65, 80, 35, 70, 50,
            25, 60, 85, 45, 75, 95, 40
        };

        for (int row = 1; row <= 4; row++) {
            for (int col = 0; col < 7; col++) {
                int index = (row - 1) * 7 + col;
                Label cell = new Label();
                cell.setPrefSize(28, 28);
                int value = heatValues[index];

                String backgroundColor;
                if (value >= 80) {
                    backgroundColor = STATUS_DANGER_TEXT;
                } else if (value >= 60) {
                    backgroundColor = STATUS_WARN_TEXT;
                } else if (value >= 30) {
                    backgroundColor = STATUS_SUCCESS_TEXT;
                } else {
                    backgroundColor = TEAL_SOFT;
                }

                cell.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: 4px;");
                cell.setTooltip(new javafx.scene.control.Tooltip("Doctor Load: " + value + "%"));
                heatmap.add(cell, col, row);
            }
        }

        HBox heatmapLegend = new HBox();
        heatmapLegend.setSpacing(12);
        heatmapLegend.setAlignment(Pos.CENTER);

        Label low = new Label("Low");
        low.setStyle("-fx-font-size: 10px; -fx-text-fill: " + TEXT_MUTED + ";");
        Label medium = new Label("Medium");
        medium.setStyle("-fx-font-size: 10px; -fx-text-fill: " + TEXT_MUTED + ";");
        Label high = new Label("High");
        high.setStyle("-fx-font-size: 10px; -fx-text-fill: " + TEXT_MUTED + ";");

        heatmapLegend.getChildren().addAll(low, medium, high);
        doctorHeatmapBox.getChildren().addAll(doctorHeatmapTitle, heatmap, heatmapLegend);

        // Response Time
        VBox responseChartBox = new VBox();
        responseChartBox.setPadding(new Insets(16));
        responseChartBox.setSpacing(10);
        responseChartBox.setPrefHeight(300);
        responseChartBox.setStyle(CARD_STYLE);

        Label responseTitle = new Label("Avg Response Time (min)");
        responseTitle.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        CategoryAxis responseXAxis = new CategoryAxis();
        NumberAxis responseYAxis = new NumberAxis();
        responseXAxis.setLabel("Day");
        responseYAxis.setLabel("Minutes");

        LineChart<String, Number> responseChart = new LineChart<>(responseXAxis, responseYAxis);
        responseChart.setLegendVisible(false);
        responseChart.setAnimated(false);
        responseChart.setPrefHeight(230);

        XYChart.Series<String, Number> responseSeries = new XYChart.Series<>();
        responseSeries.setName("Response Time");
        responseSeries.getData().add(new XYChart.Data<>("Mon", 4.5));
        responseSeries.getData().add(new XYChart.Data<>("Tue", 4.2));
        responseSeries.getData().add(new XYChart.Data<>("Wed", 5.1));
        responseSeries.getData().add(new XYChart.Data<>("Thu", 4.0));
        responseSeries.getData().add(new XYChart.Data<>("Fri", 3.8));
        responseSeries.getData().add(new XYChart.Data<>("Sat", 4.4));
        responseSeries.getData().add(new XYChart.Data<>("Sun", 4.2));

        responseChart.getData().add(responseSeries);
        responseChartBox.getChildren().addAll(responseTitle, responseChart);

        HBox.setHgrow(icuChartBox, Priority.ALWAYS);
        HBox.setHgrow(doctorHeatmapBox, Priority.ALWAYS);
        HBox.setHgrow(responseChartBox, Priority.ALWAYS);

        chartRow2.getChildren().addAll(icuChartBox, doctorHeatmapBox, responseChartBox);
        contentVBox.getChildren().addAll(analyticsHeader, statisticsCards, chartRow1, chartRow2);

        ScrollPane scrollPane = new ScrollPane(contentVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: " + PAGE_BG + "; -fx-background: " + PAGE_BG + ";");

        VBox analyticsPage = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        mainVBox.getChildren().addAll(topBar, analyticsPage);
        VBox.setVgrow(analyticsPage, Priority.ALWAYS);

        return mainVBox;
    }

    private VBox createStatCard(String icon, String iconBg, String title, String val, String change, String changeColor) {
        VBox card = new VBox();
        card.setPadding(new Insets(16));
        card.setSpacing(8);
        card.setPrefHeight(100);
        card.setStyle(CARD_STYLE);

        HBox content = new HBox();
        content.setSpacing(12);
        content.setAlignment(Pos.CENTER_LEFT);

        Label icn = new Label(icon);
        icn.setPrefSize(44, 44);
        icn.setAlignment(Pos.CENTER);
        icn.setStyle("-fx-background-color: " + iconBg + "; -fx-background-radius: 10px; -fx-font-size: 20px;");

        VBox details = new VBox();
        details.setSpacing(3);

        Label t = new Label(title);
        t.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");

        Label v = new Label(val);
        v.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label chg = new Label(change);
        chg.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + changeColor + ";");

        HBox valBox = new HBox(8, v, chg);
        valBox.setAlignment(Pos.CENTER_LEFT);

        details.getChildren().addAll(t, valBox);
        content.getChildren().addAll(icn, details);
        card.getChildren().add(content);
        return card;
    }
}