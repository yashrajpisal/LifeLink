<<<<<<< HEAD
package com.kurukshetra.view.admin;
=======
package com.kurukshetra.view.admin;
>>>>>>> 4c382865a866e1f873b176080c9714c57fa1ef39

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AnalyticsAndReports {

    public static VBox getAnalyticsAndReports() {

        // =============================================================
        // MAIN CONTENT
        // =============================================================

        VBox mainContent = new VBox(24);
        mainContent.setPadding(new Insets(30));
        mainContent.setStyle("-fx-background-color: #faf8ff;");

        // =============================================================
        // PAGE HEADER
        // =============================================================

        HBox pageHeader = new HBox(20);
        pageHeader.setAlignment(Pos.CENTER_LEFT);
        pageHeader.setPadding(new Insets(0, 0, 10, 0));

        VBox headingBox = new VBox(5);

        Label pageTitle = new Label("Analytics & Reports");
        pageTitle.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label pageDescription = new Label(
                "Comprehensive performance overview for regional emergency response."
        );
        pageDescription.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

        headingBox.getChildren().addAll(
                pageTitle,
                pageDescription
        );

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox headerButtons = new HBox(10);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);

        Button dateButton = new Button("📅  Last 30 Days");
        dateButton.setStyle("-fx-background-color: white; -fx-border-color: #737686; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 10px 16px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Button pdfButton = new Button("▣  PDF");
        pdfButton.setStyle("-fx-background-color: #004ac6; -fx-background-radius: 10px; -fx-padding: 10px 18px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button excelButton = new Button("▦  Excel");
        excelButton.setStyle("-fx-background-color: #004ac6; -fx-background-radius: 10px; -fx-padding: 10px 18px; -fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: white;");

        headerButtons.getChildren().addAll(
                dateButton,
                pdfButton,
                excelButton
        );

        pageHeader.getChildren().addAll(
                headingBox,
                headerSpacer,
                headerButtons
        );

        // =============================================================
        // SUMMARY CARDS
        // =============================================================

        HBox summaryCards = new HBox(20);
        summaryCards.setAlignment(Pos.CENTER);
        summaryCards.setFillHeight(true);

        // -------------------------------------------------------------
        // SUCCESS RATE
        // -------------------------------------------------------------

        VBox successCard = new VBox(12);
        successCard.setPadding(new Insets(22));
        successCard.setPrefHeight(150);
        successCard.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        HBox successTop = new HBox();
        successTop.setAlignment(Pos.CENTER_LEFT);

        Label successIcon = new Label("✓");
        successIcon.setAlignment(Pos.CENTER);
        successIcon.setPrefSize(40, 40);
        successIcon.setStyle("-fx-background-color: #dbe1ff; -fx-background-radius: 10px; -fx-text-fill: #004ac6; -fx-font-size: 22px; -fx-font-weight: bold;");

        Region successSpacer = new Region();
        HBox.setHgrow(successSpacer, Priority.ALWAYS);

        Label successChange = new Label("+2.4%");
        successChange.setStyle("-fx-background-color: #dcfce7; -fx-background-radius: 5px; -fx-padding: 5px 8px; -fx-text-fill: #15803d; -fx-font-size: 10px; -fx-font-weight: bold;");

        successTop.getChildren().addAll(
                successIcon,
                successSpacer,
                successChange
        );

        Label successLabel = new Label("SUCCESS RATE");
        successLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #737686;");

        Label successValue = new Label("98.2%");
        successValue.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label successDescription = new Label(
                "Critical cases resolved successfully."
        );
        successDescription.setStyle("-fx-font-size: 12px; -fx-text-fill: #737686;");

        successCard.getChildren().addAll(
                successTop,
                successLabel,
                successValue,
                successDescription
        );

        HBox.setHgrow(successCard, Priority.ALWAYS);

        // -------------------------------------------------------------
        // AVG ETA
        // -------------------------------------------------------------

        VBox etaCard = new VBox(12);
        etaCard.setPadding(new Insets(22));
        etaCard.setPrefHeight(150);
        etaCard.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        HBox etaTop = new HBox();
        etaTop.setAlignment(Pos.CENTER_LEFT);

        Label etaIcon = new Label("⏱");
        etaIcon.setAlignment(Pos.CENTER);
        etaIcon.setPrefSize(40, 40);
        etaIcon.setStyle("-fx-background-color: #d3e4fe; -fx-background-radius: 10px; -fx-text-fill: #505f76; -fx-font-size: 20px;");

        Region etaSpacer = new Region();
        HBox.setHgrow(etaSpacer, Priority.ALWAYS);

        Label etaChange = new Label("-12s");
        etaChange.setStyle("-fx-background-color: #fee2e2; -fx-background-radius: 5px; -fx-padding: 5px 8px; -fx-text-fill: #ba1a1a; -fx-font-size: 10px; -fx-font-weight: bold;");

        etaTop.getChildren().addAll(
                etaIcon,
                etaSpacer,
                etaChange
        );

        Label etaLabel = new Label("AVG. ETA");
        etaLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #737686;");

        Label etaValue = new Label("7m 42s");
        etaValue.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label etaDescription = new Label(
                "Time from dispatch to arrival."
        );
        etaDescription.setStyle("-fx-font-size: 12px; -fx-text-fill: #737686;");

        etaCard.getChildren().addAll(
                etaTop,
                etaLabel,
                etaValue,
                etaDescription
        );

        HBox.setHgrow(etaCard, Priority.ALWAYS);

        // =============================================================
        // HOSPITAL LOAD
        // =============================================================

        VBox hospitalLoadCard = new VBox(12);
        hospitalLoadCard.setPadding(new Insets(22));
        hospitalLoadCard.setPrefHeight(150);
        hospitalLoadCard.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        HBox hospitalLoadTitle = new HBox();

        VBox hospitalLoadHeading = new VBox(3);

        Label hospitalLoadLabel = new Label("HOSPITAL LOAD");
        hospitalLoadLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #737686;");

        Label hospitalLoadSubTitle = new Label("Current Capacity Status");
        hospitalLoadSubTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        hospitalLoadHeading.getChildren().addAll(
                hospitalLoadLabel,
                hospitalLoadSubTitle
        );

        Region hospitalLoadSpacer = new Region();
        HBox.setHgrow(hospitalLoadSpacer, Priority.ALWAYS);

        Label moreButton = new Label("⋮");
        moreButton.setStyle("-fx-font-size: 22px; -fx-text-fill: #737686;");

        hospitalLoadTitle.getChildren().addAll(
                hospitalLoadHeading,
                hospitalLoadSpacer,
                moreButton
        );

        HBox bars = new HBox(12);
        bars.setAlignment(Pos.BOTTOM_CENTER);
        bars.setPrefHeight(70);

        Region bar1 = new Region();
        bar1.setPrefWidth(30);
        bar1.setPrefHeight(42);
        bar1.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 6px 6px 0px 0px;");

        Region bar2 = new Region();
        bar2.setPrefWidth(30);
        bar2.setPrefHeight(60);
        bar2.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 6px 6px 0px 0px;");

        Region bar3 = new Region();
        bar3.setPrefWidth(30);
        bar3.setPrefHeight(28);
        bar3.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 6px 6px 0px 0px;");

        Region bar4 = new Region();
        bar4.setPrefWidth(30);
        bar4.setPrefHeight(67);
        bar4.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 6px 6px 0px 0px;");

        Region bar5 = new Region();
        bar5.setPrefWidth(30);
        bar5.setPrefHeight(49);
        bar5.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 6px 6px 0px 0px;");

        Region bar6 = new Region();
        bar6.setPrefWidth(30);
        bar6.setPrefHeight(38);
        bar6.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 6px 6px 0px 0px;");

        Region bar7 = new Region();
        bar7.setPrefWidth(30);
        bar7.setPrefHeight(56);
        bar7.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 6px 6px 0px 0px;");

        Region bar8 = new Region();
        bar8.setPrefWidth(30);
        bar8.setPrefHeight(21);
        bar8.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 6px 6px 0px 0px;");

        bars.getChildren().addAll(
                bar1,
                bar2,
                bar3,
                bar4,
                bar5,
                bar6,
                bar7,
                bar8
        );

        HBox hospitalNames = new HBox();
        hospitalNames.setAlignment(Pos.CENTER);

        Label h01 = new Label("H-01");
        h01.setStyle("-fx-font-size: 9px; -fx-text-fill: #737686;");

        Label h02 = new Label("H-02");
        h02.setStyle("-fx-font-size: 9px; -fx-text-fill: #737686;");

        Label h03 = new Label("H-03");
        h03.setStyle("-fx-font-size: 9px; -fx-text-fill: #737686;");

        Label h04 = new Label("H-04");
        h04.setStyle("-fx-font-size: 9px; -fx-text-fill: #737686;");

        Label h05 = new Label("H-05");
        h05.setStyle("-fx-font-size: 9px; -fx-text-fill: #737686;");

        Label h06 = new Label("H-06");
        h06.setStyle("-fx-font-size: 9px; -fx-text-fill: #737686;");

        Label h07 = new Label("H-07");
        h07.setStyle("-fx-font-size: 9px; -fx-text-fill: #737686;");

        Label h08 = new Label("H-08");
        h08.setStyle("-fx-font-size: 9px; -fx-text-fill: #737686;");

        hospitalNames.getChildren().addAll(
                h01,
                h02,
                h03,
                h04,
                h05,
                h06,
                h07,
                h08
        );

        hospitalLoadCard.getChildren().addAll(
                hospitalLoadTitle,
                bars,
                hospitalNames
        );

        HBox.setHgrow(hospitalLoadCard, Priority.ALWAYS);

        summaryCards.getChildren().addAll(
                successCard,
                etaCard,
                hospitalLoadCard
        );

        // =============================================================
        // DAILY EMERGENCY VOLUME
        // =============================================================

        VBox emergencyVolume = new VBox(18);
        emergencyVolume.setPadding(new Insets(25));
        emergencyVolume.setPrefHeight(350);
        emergencyVolume.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        HBox emergencyVolumeHeader = new HBox();

        VBox emergencyVolumeTitleBox = new VBox(4);

        Label emergencyVolumeTitle = new Label("Daily Emergency Volume");
        emergencyVolumeTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label emergencyVolumeDescription = new Label("Call volume trends over the last 30 days.");
        emergencyVolumeDescription.setStyle("-fx-font-size: 12px; -fx-text-fill: #737686;");

        emergencyVolumeTitleBox.getChildren().addAll(
                emergencyVolumeTitle,
                emergencyVolumeDescription
        );

        Region emergencyHeaderSpacer = new Region();
        HBox.setHgrow(emergencyHeaderSpacer, Priority.ALWAYS);

        HBox chartButtons = new HBox(5);
        chartButtons.setPadding(new Insets(4));
        chartButtons.setStyle("-fx-background-color: #f3f3fe; -fx-background-radius: 8px;");

        Button dailyButton = new Button("Daily");
        dailyButton.setStyle("-fx-background-color: white; -fx-background-radius: 6px; -fx-text-fill: #004ac6; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 6px 12px;");

        Button monthlyButton = new Button("Monthly");
        monthlyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #737686; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 6px 12px;");

        chartButtons.getChildren().addAll(
                dailyButton,
                monthlyButton
        );

        emergencyVolumeHeader.getChildren().addAll(
                emergencyVolumeTitleBox,
                emergencyHeaderSpacer,
                chartButtons
        );

        // -------------------------------------------------------------
        // SIMPLE LINE CHART AREA
        // -------------------------------------------------------------

        StackPane chartArea = new StackPane();
        chartArea.setPrefHeight(220);
        chartArea.setStyle("-fx-background-color: white; -fx-border-color: transparent transparent #c3c6d7 #c3c6d7; -fx-border-width: 0px 0px 1px 1px;");

        VBox chartLines = new VBox();
        chartLines.setSpacing(40);
        chartLines.setPadding(new Insets(20));

        Region line1 = new Region();
        line1.setPrefHeight(1);
        line1.setStyle("-fx-background-color: #e1e2ed;");

        Region line2 = new Region();
        line2.setPrefHeight(1);
        line2.setStyle("-fx-background-color: #e1e2ed;");

        Region line3 = new Region();
        line3.setPrefHeight(1);
        line3.setStyle("-fx-background-color: #e1e2ed;");

        Region line4 = new Region();
        line4.setPrefHeight(1);
        line4.setStyle("-fx-background-color: #e1e2ed;");

        chartLines.getChildren().addAll(
                line1,
                line2,
                line3,
                line4
        );

        chartArea.getChildren().add(chartLines);

        emergencyVolume.getChildren().addAll(
                emergencyVolumeHeader,
                chartArea
        );

        // =============================================================
        // RESPONSE TIME GOALS
        // =============================================================

        VBox responseGoals = new VBox(18);
        responseGoals.setPadding(new Insets(25));
        responseGoals.setPrefHeight(350);
        responseGoals.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label responseTitle = new Label("Response Time Goals");
        responseTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        // Under 5 mins

        HBox underFiveText = new HBox();

        Label underFive = new Label("Under 5 mins");
        underFive.setStyle("-fx-font-size: 12px; -fx-text-fill: #191b23;");

        Region underFiveSpacer = new Region();
        HBox.setHgrow(underFiveSpacer, Priority.ALWAYS);

        Label underFivePercent = new Label("42%");
        underFivePercent.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #004ac6;");

        underFiveText.getChildren().addAll(
                underFive,
                underFiveSpacer,
                underFivePercent
        );

        StackPane underFiveBar = new StackPane();
        underFiveBar.setPrefHeight(8);
        underFiveBar.setMaxWidth(Double.MAX_VALUE);
        underFiveBar.setStyle("-fx-background-color: #e7e7f3; -fx-background-radius: 5px;");

        Region underFiveProgress = new Region();
        underFiveProgress.setPrefHeight(8);
        underFiveProgress.setPrefWidth(100);
        underFiveProgress.setStyle("-fx-background-color: #004ac6; -fx-background-radius: 5px;");

        underFiveBar.getChildren().add(underFiveProgress);
        StackPane.setAlignment(underFiveProgress, Pos.CENTER_LEFT);

        VBox underFiveBox = new VBox(8);
        underFiveBox.getChildren().addAll(
                underFiveText,
                underFiveBar
        );

        // 5 - 10 mins

        HBox fiveTenText = new HBox();

        Label fiveTen = new Label("5 - 10 mins");
        fiveTen.setStyle("-fx-font-size: 12px; -fx-text-fill: #191b23;");

        Region fiveTenSpacer = new Region();
        HBox.setHgrow(fiveTenSpacer, Priority.ALWAYS);

        Label fiveTenPercent = new Label("48%");
        fiveTenPercent.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #004ac6;");

        fiveTenText.getChildren().addAll(
                fiveTen,
                fiveTenSpacer,
                fiveTenPercent
        );

        StackPane fiveTenBar = new StackPane();
        fiveTenBar.setPrefHeight(8);
        fiveTenBar.setMaxWidth(Double.MAX_VALUE);
        fiveTenBar.setStyle("-fx-background-color: #e7e7f3; -fx-background-radius: 5px;");

        Region fiveTenProgress = new Region();
        fiveTenProgress.setPrefHeight(8);
        fiveTenProgress.setPrefWidth(115);
        fiveTenProgress.setStyle("-fx-background-color: #004ac6; -fx-background-radius: 5px;");

        fiveTenBar.getChildren().add(fiveTenProgress);
        StackPane.setAlignment(fiveTenProgress, Pos.CENTER_LEFT);

        VBox fiveTenBox = new VBox(8);
        fiveTenBox.getChildren().addAll(
                fiveTenText,
                fiveTenBar
        );

        // Over 10 mins

        HBox overTenText = new HBox();

        Label overTen = new Label("Over 10 mins");
        overTen.setStyle("-fx-font-size: 12px; -fx-text-fill: #191b23;");

        Region overTenSpacer = new Region();
        HBox.setHgrow(overTenSpacer, Priority.ALWAYS);

        Label overTenPercent = new Label("10%");
        overTenPercent.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #ba1a1a;");

        overTenText.getChildren().addAll(
                overTen,
                overTenSpacer,
                overTenPercent
        );

        StackPane overTenBar = new StackPane();
        overTenBar.setPrefHeight(8);
        overTenBar.setMaxWidth(Double.MAX_VALUE);
        overTenBar.setStyle("-fx-background-color: #e7e7f3; -fx-background-radius: 5px;");

        Region overTenProgress = new Region();
        overTenProgress.setPrefHeight(8);
        overTenProgress.setPrefWidth(25);
        overTenProgress.setStyle("-fx-background-color: #ba1a1a; -fx-background-radius: 5px;");

        overTenBar.getChildren().add(overTenProgress);
        StackPane.setAlignment(overTenProgress, Pos.CENTER_LEFT);

        VBox overTenBox = new VBox(8);
        overTenBox.getChildren().addAll(
                overTenText,
                overTenBar
        );

        // Optimization Tip

        VBox optimizationTip = new VBox(8);
        optimizationTip.setPadding(new Insets(15));
        optimizationTip.setStyle("-fx-background-color: #f3f3fe; -fx-background-radius: 10px;");

        Label optimizationTitle = new Label("💡  Optimization Tip");
        optimizationTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #004ac6;");

        Label optimizationDescription = new Label(
                "Congestion in Sector 4 is increasing Average ETA by 18% during peak hours."
        );
        optimizationDescription.setWrapText(true);
        optimizationDescription.setStyle("-fx-font-size: 11px; -fx-text-fill: #434655;");

        optimizationTip.getChildren().addAll(
                optimizationTitle,
                optimizationDescription
        );

        responseGoals.getChildren().addAll(
                responseTitle,
                underFiveBox,
                fiveTenBox,
                overTenBox,
                optimizationTip
        );

        // =============================================================
        // CHART + RESPONSE GOALS
        // =============================================================

        HBox middleSection = new HBox(24);

        emergencyVolume.setMaxWidth(Double.MAX_VALUE);
        responseGoals.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(emergencyVolume, Priority.ALWAYS);
        HBox.setHgrow(responseGoals, Priority.ALWAYS);

        middleSection.getChildren().addAll(
                emergencyVolume,
                responseGoals
        );

        // =============================================================
        // HOSPITAL PERFORMANCE MATRIX
        // =============================================================

        VBox hospitalPerformance = new VBox(0);
        hospitalPerformance.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-width: 1px;");
        hospitalPerformance.setMaxWidth(Double.MAX_VALUE);

        // -------------------------------------------------------------
        // HEADER
        // -------------------------------------------------------------

        HBox hospitalHeader = new HBox();
        hospitalHeader.setPadding(new Insets(18, 24, 18, 24));
        hospitalHeader.setAlignment(Pos.CENTER_LEFT);
        hospitalHeader.setStyle("-fx-background-color: white; -fx-border-color: transparent transparent #e1e2ed transparent; -fx-border-width: 0px 0px 1px 0px;");

        Label hospitalTitle = new Label("Hospital Performance Matrix");
        hospitalTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Region hospitalHeaderSpacer = new Region();
        HBox.setHgrow(hospitalHeaderSpacer, Priority.ALWAYS);

        Label viewAllHospitals = new Label("View All Hospitals  ›");
        viewAllHospitals.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #004ac6;");

        hospitalHeader.getChildren().addAll(
                hospitalTitle,
                hospitalHeaderSpacer,
                viewAllHospitals
        );

        // -------------------------------------------------------------
        // TABLE HEADER
        // -------------------------------------------------------------

        HBox tableHeader = new HBox();
        tableHeader.setPadding(new Insets(12, 16, 12, 16));
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setStyle("-fx-background-color: #f1f5f9;");

        Label hName = new Label("HOSPITAL\nNAME");
        hName.setPrefWidth(115);
        hName.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        Label hTier = new Label("TIER");
        hTier.setPrefWidth(110);
        hTier.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        Label hEmergency = new Label("TOTAL\nEMERGENCIES");
        hEmergency.setPrefWidth(120);
        hEmergency.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        Label hHandover = new Label("AVG.\nHANDOVER");
        hHandover.setPrefWidth(105);
        hHandover.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        Label hPerformance = new Label("PERFORMANCE\nINDEX");
        hPerformance.setPrefWidth(125);
        hPerformance.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        Label hStatus = new Label("STATUS");
        hStatus.setPrefWidth(90);
        hStatus.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        tableHeader.getChildren().addAll(
                hName,
                hTier,
                hEmergency,
                hHandover,
                hPerformance,
                hStatus
        );

        // -------------------------------------------------------------
        // CITY GENERAL HOSPITAL
        // -------------------------------------------------------------

        HBox row1 = new HBox();
        row1.setPadding(new Insets(16, 16, 16, 16));
        row1.setAlignment(Pos.CENTER_LEFT);
        row1.setStyle("-fx-background-color: white; -fx-border-color: transparent transparent #e1e2ed transparent; -fx-border-width: 0px 0px 1px 0px;");

        Label cityGeneral = new Label("City General\nHospital");
        cityGeneral.setPrefWidth(115);
        cityGeneral.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label cityTier = new Label("Tier 1 Trauma");
        cityTier.setPrefWidth(110);
        cityTier.setStyle("-fx-font-size: 11px; -fx-text-fill: #434655;");

        Label cityEmergency = new Label("1,248");
        cityEmergency.setPrefWidth(120);
        cityEmergency.setStyle("-fx-font-size: 11px; -fx-text-fill: #434655;");

        Label cityHandover = new Label("4m 12s");
        cityHandover.setPrefWidth(105);
        cityHandover.setStyle("-fx-font-size: 11px; -fx-text-fill: #434655;");

        HBox cityPerformance = new HBox(8);
        cityPerformance.setPrefWidth(125);
        cityPerformance.setAlignment(Pos.CENTER_LEFT);

        StackPane cityProgress = new StackPane();
        cityProgress.setPrefWidth(65);
        cityProgress.setPrefHeight(5);
        cityProgress.setStyle("-fx-background-color: #e7e7f3; -fx-background-radius: 5px;");

        Region cityProgressBar = new Region();
        cityProgressBar.setPrefWidth(61);
        cityProgressBar.setPrefHeight(5);
        cityProgressBar.setStyle("-fx-background-color: #22c55e; -fx-background-radius: 5px;");

        cityProgress.getChildren().add(cityProgressBar);
        StackPane.setAlignment(cityProgressBar, Pos.CENTER_LEFT);

        Label cityPercentage = new Label("94%");
        cityPercentage.setStyle("-fx-font-size: 10px; -fx-text-fill: #434655;");

        cityPerformance.getChildren().addAll(
                cityProgress,
                cityPercentage
        );

        Label cityStatus = new Label("Excellent");
        cityStatus.setStyle("-fx-background-color: #dcfce7; -fx-text-fill: #15803d; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 8px; -fx-background-radius: 4px;");

        row1.getChildren().addAll(
                cityGeneral,
                cityTier,
                cityEmergency,
                cityHandover,
                cityPerformance,
                cityStatus
        );

        // -------------------------------------------------------------
        // ST. JUDE MEDICAL CENTER
        // -------------------------------------------------------------

        HBox row2 = new HBox();
        row2.setPadding(new Insets(16, 16, 16, 16));
        row2.setAlignment(Pos.CENTER_LEFT);
        row2.setStyle("-fx-background-color: white; -fx-border-color: transparent transparent #e1e2ed transparent; -fx-border-width: 0px 0px 1px 0px;");

        Label stJude = new Label("St. Jude\nMedical\nCenter");
        stJude.setPrefWidth(115);
        stJude.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label stJudeTier = new Label("Specialized\nCardiac");
        stJudeTier.setPrefWidth(110);
        stJudeTier.setStyle("-fx-font-size: 11px; -fx-text-fill: #434655;");

        Label stJudeEmergency = new Label("856");
        stJudeEmergency.setPrefWidth(120);
        stJudeEmergency.setStyle("-fx-font-size: 11px; -fx-text-fill: #434655;");

        Label stJudeHandover = new Label("5m 45s");
        stJudeHandover.setPrefWidth(105);
        stJudeHandover.setStyle("-fx-font-size: 11px; -fx-text-fill: #434655;");

        HBox stJudePerformance = new HBox(8);
        stJudePerformance.setPrefWidth(125);
        stJudePerformance.setAlignment(Pos.CENTER_LEFT);

        StackPane stJudeProgress = new StackPane();
        stJudeProgress.setPrefWidth(65);
        stJudeProgress.setPrefHeight(5);
        stJudeProgress.setStyle("-fx-background-color: #e7e7f3; -fx-background-radius: 5px;");

        Region stJudeProgressBar = new Region();
        stJudeProgressBar.setPrefWidth(51);
        stJudeProgressBar.setPrefHeight(5);
        stJudeProgressBar.setStyle("-fx-background-color: #eab308; -fx-background-radius: 5px;");

        stJudeProgress.getChildren().add(stJudeProgressBar);
        StackPane.setAlignment(stJudeProgressBar, Pos.CENTER_LEFT);

        Label stJudePercentage = new Label("78%");
        stJudePercentage.setStyle("-fx-font-size: 10px; -fx-text-fill: #434655;");

        stJudePerformance.getChildren().addAll(
                stJudeProgress,
                stJudePercentage
        );

        Label stJudeStatus = new Label("Average");
        stJudeStatus.setStyle("-fx-background-color: #fef3c7; -fx-text-fill: #a16207; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 8px; -fx-background-radius: 4px;");

        row2.getChildren().addAll(
                stJude,
                stJudeTier,
                stJudeEmergency,
                stJudeHandover,
                stJudePerformance,
                stJudeStatus
        );

        // -------------------------------------------------------------
        // NORTHSIDE COMMUNITY
        // -------------------------------------------------------------

        HBox row3 = new HBox();
        row3.setPadding(new Insets(16, 16, 16, 16));
        row3.setAlignment(Pos.CENTER_LEFT);
        row3.setStyle("-fx-background-color: white;");

        Label northside = new Label("Northside\nCommunity");
        northside.setPrefWidth(115);
        northside.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label northsideTier = new Label("Tier 2\nGeneral");
        northsideTier.setPrefWidth(110);
        northsideTier.setStyle("-fx-font-size: 11px; -fx-text-fill: #434655;");

        Label northsideEmergency = new Label("2,104");
        northsideEmergency.setPrefWidth(120);
        northsideEmergency.setStyle("-fx-font-size: 11px; -fx-text-fill: #434655;");

        Label northsideHandover = new Label("3m 50s");
        northsideHandover.setPrefWidth(105);
        northsideHandover.setStyle("-fx-font-size: 11px; -fx-text-fill: #434655;");

        HBox northsidePerformance = new HBox(8);
        northsidePerformance.setPrefWidth(125);
        northsidePerformance.setAlignment(Pos.CENTER_LEFT);

        StackPane northsideProgress = new StackPane();
        northsideProgress.setPrefWidth(65);
        northsideProgress.setPrefHeight(5);
        northsideProgress.setStyle("-fx-background-color: #e7e7f3; -fx-background-radius: 5px;");

        Region northsideProgressBar = new Region();
        northsideProgressBar.setPrefWidth(58);
        northsideProgressBar.setPrefHeight(5);
        northsideProgressBar.setStyle("-fx-background-color: #22c55e; -fx-background-radius: 5px;");

        northsideProgress.getChildren().add(northsideProgressBar);
        StackPane.setAlignment(northsideProgressBar, Pos.CENTER_LEFT);

        Label northsidePercentage = new Label("89%");
        northsidePercentage.setStyle("-fx-font-size: 10px; -fx-text-fill: #434655;");

        northsidePerformance.getChildren().addAll(
                northsideProgress,
                northsidePercentage
        );

        Label northsideStatus = new Label("Optimal");
        northsideStatus.setStyle("-fx-background-color: #dcfce7; -fx-text-fill: #15803d; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 8px; -fx-background-radius: 4px;");

        row3.getChildren().addAll(
                northside,
                northsideTier,
                northsideEmergency,
                northsideHandover,
                northsidePerformance,
                northsideStatus
        );

        hospitalPerformance.getChildren().addAll(
                hospitalHeader,
                tableHeader,
                row1,
                row2,
                row3
        );

        // =============================================================
        // ADD ALL CONTENT TO MAIN CONTENT
        // =============================================================

        mainContent.getChildren().addAll(
                pageHeader,
                summaryCards,
                middleSection,
                hospitalPerformance
        );

        // =============================================================
        // SCROLL PANE
        // =============================================================

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





























// package com.core2web.view.Admin;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.VBox;

// public class AnalyticsReport {

//     public VBox getAnalyticsReport() {

//         VBox mainBox = new VBox(25);
//         mainBox.setPadding(new Insets(30));
//         mainBox.setStyle("-fx-background-color: #faf8ff;");

//         // =============================================================
//         // PAGE HEADER
//         // =============================================================

//         HBox header = new HBox(20);
//         header.setAlignment(Pos.CENTER_LEFT);

//         VBox headingBox = new VBox(5);

//         Label heading = new Label("Analytics & Reports");
//         heading.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         Label subHeading = new Label(
//                 "Comprehensive performance overview for regional emergency response."
//         );
//         subHeading.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

//         headingBox.getChildren().addAll(heading, subHeading);

//         Region headerSpacer = new Region();
//         HBox.setHgrow(headerSpacer, Priority.ALWAYS);

//         Button dateButton = new Button("📅  Last 30 Days");
//         dateButton.setStyle("-fx-background-color: white; -fx-border-color: #737686; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 10px 18px; -fx-font-size: 13px;");

//         Button pdfButton = new Button("PDF");
//         pdfButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-background-radius: 10px; -fx-padding: 10px 18px; -fx-font-weight: bold;");

//         Button excelButton = new Button("Excel");
//         excelButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-background-radius: 10px; -fx-padding: 10px 18px; -fx-font-weight: bold;");

//         HBox buttonBox = new HBox(10, dateButton, pdfButton, excelButton);
//         buttonBox.setAlignment(Pos.CENTER_RIGHT);

//         header.getChildren().addAll(headingBox, headerSpacer, buttonBox);

//         // =============================================================
//         // SUMMARY CARDS
//         // =============================================================

//         HBox summaryBox = new HBox(20);

//         // SUCCESS RATE

//         VBox successCard = new VBox(15);
//         successCard.setPadding(new Insets(25));
//         successCard.setPrefWidth(250);
//         successCard.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         Label successIcon = new Label("✓");
//         successIcon.setStyle("-fx-background-color: #dbe1ff; -fx-text-fill: #004ac6; -fx-font-size: 22px; -fx-font-weight: bold; -fx-padding: 8px 14px; -fx-background-radius: 8px;");

//         Label successTitle = new Label("SUCCESS RATE");
//         successTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #737686;");

//         Label successValue = new Label("98.2%");
//         successValue.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         Label successDescription = new Label(
//                 "Critical cases resolved successfully."
//         );
//         successDescription.setWrapText(true);
//         successDescription.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");

//         successCard.getChildren().addAll(
//                 successIcon,
//                 successTitle,
//                 successValue,
//                 successDescription
//         );

//         // AVG ETA

//         VBox etaCard = new VBox(15);
//         etaCard.setPadding(new Insets(25));
//         etaCard.setPrefWidth(250);
//         etaCard.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         Label etaIcon = new Label("⏱");
//         etaIcon.setStyle("-fx-background-color: #d3e4fe; -fx-text-fill: #505f76; -fx-font-size: 22px; -fx-padding: 8px 12px; -fx-background-radius: 8px;");

//         Label etaTitle = new Label("AVG. ETA");
//         etaTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #737686;");

//         Label etaValue = new Label("7m 42s");
//         etaValue.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         Label etaDescription = new Label(
//                 "Time from dispatch to arrival."
//         );
//         etaDescription.setWrapText(true);
//         etaDescription.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");

//         etaCard.getChildren().addAll(
//                 etaIcon,
//                 etaTitle,
//                 etaValue,
//                 etaDescription
//         );

//         // =============================================================
//         // HOSPITAL LOAD
//         // =============================================================

//         VBox hospitalLoad = new VBox(15);
//         hospitalLoad.setPadding(new Insets(25));
//         hospitalLoad.setPrefWidth(500);
//         HBox.setHgrow(hospitalLoad, Priority.ALWAYS);
//         hospitalLoad.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         Label loadTitle = new Label("HOSPITAL LOAD");
//         loadTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #737686;");

//         Label loadSubTitle = new Label("Current Capacity Status");
//         loadSubTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         HBox bars = new HBox(18);
//         bars.setAlignment(Pos.BOTTOM_CENTER);
//         bars.setPrefHeight(130);

//         // DIRECTLY ADDING BARS — NO FOR LOOP

//         Region bar1 = new Region();
//         bar1.setPrefWidth(35);
//         bar1.setPrefHeight(60);
//         bar1.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 7px 7px 0px 0px;");

//         Region bar2 = new Region();
//         bar2.setPrefWidth(35);
//         bar2.setPrefHeight(85);
//         bar2.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 7px 7px 0px 0px;");

//         Region bar3 = new Region();
//         bar3.setPrefWidth(35);
//         bar3.setPrefHeight(40);
//         bar3.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 7px 7px 0px 0px;");

//         Region bar4 = new Region();
//         bar4.setPrefWidth(35);
//         bar4.setPrefHeight(95);
//         bar4.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 7px 7px 0px 0px;");

//         Region bar5 = new Region();
//         bar5.setPrefWidth(35);
//         bar5.setPrefHeight(70);
//         bar5.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 7px 7px 0px 0px;");

//         Region bar6 = new Region();
//         bar6.setPrefWidth(35);
//         bar6.setPrefHeight(55);
//         bar6.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 7px 7px 0px 0px;");

//         Region bar7 = new Region();
//         bar7.setPrefWidth(35);
//         bar7.setPrefHeight(80);
//         bar7.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 7px 7px 0px 0px;");

//         Region bar8 = new Region();
//         bar8.setPrefWidth(35);
//         bar8.setPrefHeight(30);
//         bar8.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 7px 7px 0px 0px;");

//         bars.getChildren().addAll(
//                 bar1,
//                 bar2,
//                 bar3,
//                 bar4,
//                 bar5,
//                 bar6,
//                 bar7,
//                 bar8
//         );

//         HBox hospitalNames = new HBox();
//         hospitalNames.setAlignment(Pos.CENTER);

//         Label h1 = new Label("H-01");
//         h1.setStyle("-fx-font-size: 10px; -fx-text-fill: #737686;");
//         h1.setMaxWidth(Double.MAX_VALUE);
//         h1.setAlignment(Pos.CENTER);
//         HBox.setHgrow(h1, Priority.ALWAYS);

//         Label h2 = new Label("H-02");
//         h2.setStyle("-fx-font-size: 10px; -fx-text-fill: #737686;");
//         h2.setMaxWidth(Double.MAX_VALUE);
//         h2.setAlignment(Pos.CENTER);
//         HBox.setHgrow(h2, Priority.ALWAYS);

//         Label h3 = new Label("H-03");
//         h3.setStyle("-fx-font-size: 10px; -fx-text-fill: #737686;");
//         h3.setMaxWidth(Double.MAX_VALUE);
//         h3.setAlignment(Pos.CENTER);
//         HBox.setHgrow(h3, Priority.ALWAYS);

//         Label h4 = new Label("H-04");
//         h4.setStyle("-fx-font-size: 10px; -fx-text-fill: #737686;");
//         h4.setMaxWidth(Double.MAX_VALUE);
//         h4.setAlignment(Pos.CENTER);
//         HBox.setHgrow(h4, Priority.ALWAYS);

//         Label h5 = new Label("H-05");
//         h5.setStyle("-fx-font-size: 10px; -fx-text-fill: #737686;");
//         h5.setMaxWidth(Double.MAX_VALUE);
//         h5.setAlignment(Pos.CENTER);
//         HBox.setHgrow(h5, Priority.ALWAYS);

//         Label h6 = new Label("H-06");
//         h6.setStyle("-fx-font-size: 10px; -fx-text-fill: #737686;");
//         h6.setMaxWidth(Double.MAX_VALUE);
//         h6.setAlignment(Pos.CENTER);
//         HBox.setHgrow(h6, Priority.ALWAYS);

//         Label h7 = new Label("H-07");
//         h7.setStyle("-fx-font-size: 10px; -fx-text-fill: #737686;");
//         h7.setMaxWidth(Double.MAX_VALUE);
//         h7.setAlignment(Pos.CENTER);
//         HBox.setHgrow(h7, Priority.ALWAYS);

//         Label h8 = new Label("H-08");
//         h8.setStyle("-fx-font-size: 10px; -fx-text-fill: #737686;");
//         h8.setMaxWidth(Double.MAX_VALUE);
//         h8.setAlignment(Pos.CENTER);
//         HBox.setHgrow(h8, Priority.ALWAYS);

//         hospitalNames.getChildren().addAll(
//                 h1,
//                 h2,
//                 h3,
//                 h4,
//                 h5,
//                 h6,
//                 h7,
//                 h8
//         );

//         hospitalLoad.getChildren().addAll(
//                 loadTitle,
//                 loadSubTitle,
//                 bars,
//                 hospitalNames
//         );

//         summaryBox.getChildren().addAll(
//                 successCard,
//                 etaCard,
//                 hospitalLoad
//         );

//         // =============================================================
//         // DAILY EMERGENCY VOLUME
//         // =============================================================

//         HBox chartSection = new HBox(20);

//         VBox emergencyChart = new VBox(20);
//         emergencyChart.setPadding(new Insets(30));
//         emergencyChart.setPrefHeight(400);
//         HBox.setHgrow(emergencyChart, Priority.ALWAYS);
//         emergencyChart.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         Label emergencyTitle = new Label("Daily Emergency Volume");
//         emergencyTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         Label emergencySubTitle = new Label(
//                 "Call volume trends over the last 30 days."
//         );
//         emergencySubTitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

//         VBox lineChart = new VBox();
//         lineChart.setPrefHeight(250);
//         lineChart.setStyle("-fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 1px;");

//         Region line1 = new Region();
//         line1.setPrefWidth(600);
//         line1.setPrefHeight(3);
//         line1.setStyle("-fx-background-color: #2563eb;");

//         lineChart.getChildren().add(line1);

//         emergencyChart.getChildren().addAll(
//                 emergencyTitle,
//                 emergencySubTitle,
//                 lineChart
//         );

//         // =============================================================
//         // RESPONSE TIME GOALS
//         // =============================================================

//         VBox responseGoals = new VBox(20);
//         responseGoals.setPadding(new Insets(30));
//         responseGoals.setPrefWidth(350);
//         responseGoals.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         Label responseTitle = new Label("Response Time Goals");
//         responseTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         Label underFive = new Label("Under 5 mins                         42%");
//         underFive.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

//         Region progress1 = new Region();
//         progress1.setPrefHeight(8);
//         progress1.setPrefWidth(250);
//         progress1.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 5px;");

//         Label fiveTen = new Label("5 - 10 mins                         48%");
//         fiveTen.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

//         Region progress2 = new Region();
//         progress2.setPrefHeight(8);
//         progress2.setPrefWidth(280);
//         progress2.setStyle("-fx-background-color: #2563eb; -fx-background-radius: 5px;");

//         Label overTen = new Label("Over 10 mins                         10%");
//         overTen.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

//         Region progress3 = new Region();
//         progress3.setPrefHeight(8);
//         progress3.setPrefWidth(70);
//         progress3.setStyle("-fx-background-color: #ba1a1a; -fx-background-radius: 5px;");

//         Label tip = new Label(
//                 "💡 Optimization Tip\n\nCongestion in Sector 4 is increasing Average ETA by 18% during peak hours."
//         );
//         tip.setWrapText(true);
//         tip.setPadding(new Insets(15));
//         tip.setStyle("-fx-background-color: #f3f3fe; -fx-text-fill: #434655; -fx-background-radius: 10px; -fx-font-size: 13px;");

//         responseGoals.getChildren().addAll(
//                 responseTitle,
//                 underFive,
//                 progress1,
//                 fiveTen,
//                 progress2,
//                 overTen,
//                 progress3,
//                 tip
//         );

//         chartSection.getChildren().addAll(
//                 emergencyChart,
//                 responseGoals
//         );

//         // =============================================================
//         // HOSPITAL PERFORMANCE MATRIX
//         // =============================================================

//         VBox performanceBox = new VBox();
//         performanceBox.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         Label performanceTitle = new Label("Hospital Performance Matrix");
//         performanceTitle.setPadding(new Insets(25));
//         performanceTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         HBox tableHeader = new HBox();
//         tableHeader.setPadding(new Insets(15));
//         tableHeader.setStyle("-fx-background-color: #f1f5f9;");

//         Label th1 = new Label("Hospital Name");
//         Label th2 = new Label("Tier");
//         Label th3 = new Label("Total Emergencies");
//         Label th4 = new Label("Avg. Handover");
//         Label th5 = new Label("Performance Index");
//         Label th6 = new Label("Status");

//         th1.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
//         th2.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
//         th3.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
//         th4.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
//         th5.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
//         th6.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

//         HBox.setHgrow(th1, Priority.ALWAYS);
//         HBox.setHgrow(th2, Priority.ALWAYS);
//         HBox.setHgrow(th3, Priority.ALWAYS);
//         HBox.setHgrow(th4, Priority.ALWAYS);
//         HBox.setHgrow(th5, Priority.ALWAYS);
//         HBox.setHgrow(th6, Priority.ALWAYS);

//         tableHeader.getChildren().addAll(
//                 th1,
//                 th2,
//                 th3,
//                 th4,
//                 th5,
//                 th6
//         );

//         HBox row1 = new HBox();
//         row1.setPadding(new Insets(18));

//         Label r1c1 = new Label("City General Hospital");
//         Label r1c2 = new Label("Tier 1 Trauma");
//         Label r1c3 = new Label("1,248");
//         Label r1c4 = new Label("4m 12s");
//         Label r1c5 = new Label("94%");
//         Label r1c6 = new Label("Excellent");

//         r1c6.setStyle("-fx-background-color: #dcfce7; -fx-text-fill: #15803d; -fx-padding: 5px 10px; -fx-background-radius: 5px;");

//         HBox.setHgrow(r1c1, Priority.ALWAYS);
//         HBox.setHgrow(r1c2, Priority.ALWAYS);
//         HBox.setHgrow(r1c3, Priority.ALWAYS);
//         HBox.setHgrow(r1c4, Priority.ALWAYS);
//         HBox.setHgrow(r1c5, Priority.ALWAYS);
//         HBox.setHgrow(r1c6, Priority.ALWAYS);

//         row1.getChildren().addAll(
//                 r1c1,
//                 r1c2,
//                 r1c3,
//                 r1c4,
//                 r1c5,
//                 r1c6
//         );

//         HBox row2 = new HBox();
//         row2.setPadding(new Insets(18));

//         Label r2c1 = new Label("St. Jude Medical Center");
//         Label r2c2 = new Label("Specialized Cardiac");
//         Label r2c3 = new Label("856");
//         Label r2c4 = new Label("5m 45s");
//         Label r2c5 = new Label("78%");
//         Label r2c6 = new Label("Average");

//         r2c6.setStyle("-fx-background-color: #fef3c7; -fx-text-fill: #a16207; -fx-padding: 5px 10px; -fx-background-radius: 5px;");

//         HBox.setHgrow(r2c1, Priority.ALWAYS);
//         HBox.setHgrow(r2c2, Priority.ALWAYS);
//         HBox.setHgrow(r2c3, Priority.ALWAYS);
//         HBox.setHgrow(r2c4, Priority.ALWAYS);
//         HBox.setHgrow(r2c5, Priority.ALWAYS);
//         HBox.setHgrow(r2c6, Priority.ALWAYS);

//         row2.getChildren().addAll(
//                 r2c1,
//                 r2c2,
//                 r2c3,
//                 r2c4,
//                 r2c5,
//                 r2c6
//         );

//         HBox row3 = new HBox();
//         row3.setPadding(new Insets(18));

//         Label r3c1 = new Label("Northside Community");
//         Label r3c2 = new Label("Tier 2 General");
//         Label r3c3 = new Label("2,104");
//         Label r3c4 = new Label("3m 50s");
//         Label r3c5 = new Label("89%");
//         Label r3c6 = new Label("Optimal");

//         r3c6.setStyle("-fx-background-color: #dcfce7; -fx-text-fill: #15803d; -fx-padding: 5px 10px; -fx-background-radius: 5px;");

//         HBox.setHgrow(r3c1, Priority.ALWAYS);
//         HBox.setHgrow(r3c2, Priority.ALWAYS);
//         HBox.setHgrow(r3c3, Priority.ALWAYS);
//         HBox.setHgrow(r3c4, Priority.ALWAYS);
//         HBox.setHgrow(r3c5, Priority.ALWAYS);
//         HBox.setHgrow(r3c6, Priority.ALWAYS);

//         row3.getChildren().addAll(
//                 r3c1,
//                 r3c2,
//                 r3c3,
//                 r3c4,
//                 r3c5,
//                 r3c6
//         );

//         performanceBox.getChildren().addAll(
//                 performanceTitle,
//                 tableHeader,
//                 row1,
//                 row2,
//                 row3
//         );

//         // =============================================================
//         // ADD EVERYTHING
//         // =============================================================

//         mainBox.getChildren().addAll(
//                 header,
//                 summaryBox,
//                 chartSection,
//                 performanceBox
//         );

//         ScrollPane scrollPane = new ScrollPane(mainBox);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//         scrollPane.setStyle("-fx-background-color: transparent;" + "-fx-background: transparent;");

//         VBox finalContent = new VBox(scrollPane);
//         finalContent.setStyle("-fx-background-color: #f8f8ff;");

//         VBox.setVgrow(scrollPane, Priority.ALWAYS);

//         return finalContent;

//     }
// }