package com.kurukshetra.view.hospital;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class HospitalPatientRecords {

    public VBox getPatientRecords() {

        // Main page container
        VBox mainBox = new VBox();
        mainBox.setStyle("-fx-background-color: #faf8ff;");

        // ==================== TOP BAR ====================

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(12, 24, 12, 24));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);
        topBar.setStyle("-fx-background-color: #faf8ff; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search patients by name, ID or disease...");
        searchField.setPrefWidth(500);
        searchField.setPrefHeight(38);
        searchField.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-padding: 0px 18px; -fx-font-size: 13px;");

        HBox.setHgrow(searchField, Priority.ALWAYS);

        Button notificationButton = new Button("🔔");
        notificationButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18px;");

        Button settingButton = new Button("⚙");
        settingButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18px;");

        Label profile = new Label("Dr. Sarah Jenkins");
        profile.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        topBar.getChildren().addAll(
                searchField,
                notificationButton,
                settingButton,
                profile
        );

        // ==================== PAGE HEADER ====================

        VBox pageHeader = new VBox(5);
        pageHeader.setPadding(new Insets(24, 24, 18, 24));

        Label title = new Label("Patient Records");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label subtitle = new Label("Manage and access comprehensive medical data for 1,248 registered patients.");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

        pageHeader.getChildren().addAll(
                title,
                subtitle
        );

        // ==================== ACTION BAR ====================

        HBox actionBar = new HBox();
        actionBar.setPadding(new Insets(0, 24, 18, 24));
        actionBar.setSpacing(10);
        actionBar.setAlignment(Pos.CENTER_RIGHT);

        Button filterButton = new Button("☷  Filter");
        filterButton.setPrefHeight(38);
        filterButton.setStyle("-fx-background-color: #ededf9; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 13px; -fx-text-fill: #191b23;");

        Button newPatientButton = new Button("+  New Patient");
        newPatientButton.setPrefHeight(38);
        newPatientButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-background-radius: 8px; -fx-font-size: 13px; -fx-font-weight: bold;");

        actionBar.getChildren().addAll(
                filterButton,
                newPatientButton
        );

        // ==================== TABLE HEADER ====================

        HBox tableHeader = new HBox();
        tableHeader.setPadding(new Insets(14, 15, 14, 15));
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

        Label patientIdHeader = new Label("PATIENT ID");
        Label patientNameHeader = new Label("NAME");
        Label bloodHeader = new Label("BLOOD");
        Label allergyHeader = new Label("ALLERGIES");
        Label conditionHeader = new Label("CONDITION");
        Label visitHeader = new Label("LAST VISIT");
        Label actionHeader = new Label("ACTIONS");

        patientIdHeader.setPrefWidth(95);
        patientNameHeader.setPrefWidth(150);
        bloodHeader.setPrefWidth(75);
        allergyHeader.setPrefWidth(150);
        conditionHeader.setPrefWidth(150);
        visitHeader.setPrefWidth(110);
        actionHeader.setPrefWidth(180);

        patientIdHeader.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #434655;");
        patientNameHeader.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #434655;");
        bloodHeader.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #434655;");
        allergyHeader.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #434655;");
        conditionHeader.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #434655;");
        visitHeader.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #434655;");
        actionHeader.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        tableHeader.getChildren().addAll(
                patientIdHeader,
                patientNameHeader,
                bloodHeader,
                allergyHeader,
                conditionHeader,
                visitHeader,
                actionHeader
        );

        // ==================== PATIENT 1 ====================

        Label id1 = new Label("#LL-8492");
        Label name1 = new Label("Sarah Jenkins\n42 yrs • Female");
        Label blood1 = new Label("A+");
        Label allergy1 = new Label("Penicillin, Latex");
        Label condition1 = new Label("Type 2 Diabetes");
        Label visit1 = new Label("24 Oct 2023");

        Button history1 = new Button("↶");
        Button reports1 = new Button("◉");
        Button download1 = new Button("↓");
        Button upload1 = new Button("↑");

        id1.setPrefWidth(95);
        name1.setPrefWidth(150);
        blood1.setPrefWidth(75);
        allergy1.setPrefWidth(150);
        condition1.setPrefWidth(150);
        visit1.setPrefWidth(110);

        id1.setStyle("-fx-text-fill: #004ac6; -fx-font-size: 13px; -fx-font-weight: bold;");
        name1.setStyle("-fx-text-fill: #191b23; -fx-font-size: 13px; -fx-font-weight: bold;");
        blood1.setStyle("-fx-background-color: #ffede6; -fx-text-fill: #943700; -fx-padding: 4px 8px; -fx-background-radius: 5px; -fx-font-weight: bold;");
        allergy1.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");
        condition1.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");
        visit1.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");

        history1.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        reports1.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        download1.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        upload1.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");

        HBox actions1 = new HBox();
        actions1.setSpacing(2);
        actions1.setPrefWidth(180);
        actions1.setAlignment(Pos.CENTER_RIGHT);
        actions1.getChildren().addAll(
                history1,
                reports1,
                download1,
                upload1
        );

        HBox row1 = new HBox();
        row1.setPadding(new Insets(14, 15, 14, 15));
        row1.setAlignment(Pos.CENTER_LEFT);
        row1.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

        row1.getChildren().addAll(
                id1,
                name1,
                blood1,
                allergy1,
                condition1,
                visit1,
                actions1
        );

        // ==================== PATIENT 2 ====================

        Label id2 = new Label("#LL-1023");
        Label name2 = new Label("Robert Chen\n58 yrs • Male");
        Label blood2 = new Label("O-");
        Label allergy2 = new Label("Shellfish");
        Label condition2 = new Label("Hypertension");
        Label visit2 = new Label("12 Nov 2023");

        Button history2 = new Button("↶");
        Button reports2 = new Button("◉");
        Button download2 = new Button("↓");
        Button upload2 = new Button("↑");

        id2.setPrefWidth(95);
        name2.setPrefWidth(150);
        blood2.setPrefWidth(75);
        allergy2.setPrefWidth(150);
        condition2.setPrefWidth(150);
        visit2.setPrefWidth(110);

        id2.setStyle("-fx-text-fill: #004ac6; -fx-font-size: 13px; -fx-font-weight: bold;");
        name2.setStyle("-fx-text-fill: #191b23; -fx-font-size: 13px; -fx-font-weight: bold;");
        blood2.setStyle("-fx-background-color: #ffdad6; -fx-text-fill: #ba1a1a; -fx-padding: 4px 8px; -fx-background-radius: 5px; -fx-font-weight: bold;");
        allergy2.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");
        condition2.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");
        visit2.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");

        history2.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        reports2.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        download2.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        upload2.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");

        HBox actions2 = new HBox();
        actions2.setSpacing(2);
        actions2.setPrefWidth(180);
        actions2.setAlignment(Pos.CENTER_RIGHT);
        actions2.getChildren().addAll(
                history2,
                reports2,
                download2,
                upload2
        );

        HBox row2 = new HBox();
        row2.setPadding(new Insets(14, 15, 14, 15));
        row2.setAlignment(Pos.CENTER_LEFT);
        row2.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

        row2.getChildren().addAll(
                id2,
                name2,
                blood2,
                allergy2,
                condition2,
                visit2,
                actions2
        );

        // ==================== PATIENT 3 ====================

        Label id3 = new Label("#LL-4512");
        Label name3 = new Label("Emily Rodriguez\n29 yrs • Female");
        Label blood3 = new Label("B+");
        Label allergy3 = new Label("None");
        Label condition3 = new Label("Asthma");
        Label visit3 = new Label("05 Nov 2023");

        Button history3 = new Button("↶");
        Button reports3 = new Button("◉");
        Button download3 = new Button("↓");
        Button upload3 = new Button("↑");

        id3.setPrefWidth(95);
        name3.setPrefWidth(150);
        blood3.setPrefWidth(75);
        allergy3.setPrefWidth(150);
        condition3.setPrefWidth(150);
        visit3.setPrefWidth(110);

        id3.setStyle("-fx-text-fill: #004ac6; -fx-font-size: 13px; -fx-font-weight: bold;");
        name3.setStyle("-fx-text-fill: #191b23; -fx-font-size: 13px; -fx-font-weight: bold;");
        blood3.setStyle("-fx-background-color: #d0e1fb; -fx-text-fill: #505f76; -fx-padding: 4px 8px; -fx-background-radius: 5px; -fx-font-weight: bold;");
        allergy3.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");
        condition3.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");
        visit3.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");

        history3.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        reports3.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        download3.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        upload3.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");

        HBox actions3 = new HBox();
        actions3.setSpacing(2);
        actions3.setPrefWidth(180);
        actions3.setAlignment(Pos.CENTER_RIGHT);
        actions3.getChildren().addAll(
                history3,
                reports3,
                download3,
                upload3
        );

        HBox row3 = new HBox();
        row3.setPadding(new Insets(14, 15, 14, 15));
        row3.setAlignment(Pos.CENTER_LEFT);
        row3.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

        row3.getChildren().addAll(
                id3,
                name3,
                blood3,
                allergy3,
                condition3,
                visit3,
                actions3
        );

        // ==================== PATIENT 4 ====================

        Label id4 = new Label("#LL-9932");
        Label name4 = new Label("David Wilson\n45 yrs • Male");
        Label blood4 = new Label("AB+");
        Label allergy4 = new Label("Dust Mites");
        Label condition4 = new Label("Chronic Back Pain");
        Label visit4 = new Label("18 Nov 2023");

        Button history4 = new Button("↶");
        Button reports4 = new Button("◉");
        Button download4 = new Button("↓");
        Button upload4 = new Button("↑");

        id4.setPrefWidth(95);
        name4.setPrefWidth(150);
        blood4.setPrefWidth(75);
        allergy4.setPrefWidth(150);
        condition4.setPrefWidth(150);
        visit4.setPrefWidth(110);

        id4.setStyle("-fx-text-fill: #004ac6; -fx-font-size: 13px; -fx-font-weight: bold;");
        name4.setStyle("-fx-text-fill: #191b23; -fx-font-size: 13px; -fx-font-weight: bold;");
        blood4.setStyle("-fx-background-color: #dbe1ff; -fx-text-fill: #004ac6; -fx-padding: 4px 8px; -fx-background-radius: 5px; -fx-font-weight: bold;");
        allergy4.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");
        condition4.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");
        visit4.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");

        history4.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        reports4.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        download4.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        upload4.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");

        HBox actions4 = new HBox();
        actions4.setSpacing(2);
        actions4.setPrefWidth(180);
        actions4.setAlignment(Pos.CENTER_RIGHT);
        actions4.getChildren().addAll(
                history4,
                reports4,
                download4,
                upload4
        );

        HBox row4 = new HBox();
        row4.setPadding(new Insets(14, 15, 14, 15));
        row4.setAlignment(Pos.CENTER_LEFT);
        row4.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

        row4.getChildren().addAll(
                id4,
                name4,
                blood4,
                allergy4,
                condition4,
                visit4,
                actions4
        );

        // ==================== PATIENT 5 ====================

        Label id5 = new Label("#LL-5221");
        Label name5 = new Label("Aisha Khan\n34 yrs • Female");
        Label blood5 = new Label("O+");
        Label allergy5 = new Label("Pollen");
        Label condition5 = new Label("Gastroenteritis");
        Label visit5 = new Label("02 Nov 2023");

        Button history5 = new Button("↶");
        Button reports5 = new Button("◉");
        Button download5 = new Button("↓");
        Button upload5 = new Button("↑");

        id5.setPrefWidth(95);
        name5.setPrefWidth(150);
        blood5.setPrefWidth(75);
        allergy5.setPrefWidth(150);
        condition5.setPrefWidth(150);
        visit5.setPrefWidth(110);

        id5.setStyle("-fx-text-fill: #004ac6; -fx-font-size: 13px; -fx-font-weight: bold;");
        name5.setStyle("-fx-text-fill: #191b23; -fx-font-size: 13px; -fx-font-weight: bold;");
        blood5.setStyle("-fx-background-color: #ffede6; -fx-text-fill: #943700; -fx-padding: 4px 8px; -fx-background-radius: 5px; -fx-font-weight: bold;");
        allergy5.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");
        condition5.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");
        visit5.setStyle("-fx-text-fill: #434655; -fx-font-size: 13px;");

        history5.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        reports5.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        download5.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");
        upload5.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 17px;");

        HBox actions5 = new HBox();
        actions5.setSpacing(2);
        actions5.setPrefWidth(180);
        actions5.setAlignment(Pos.CENTER_RIGHT);
        actions5.getChildren().addAll(
                history5,
                reports5,
                download5,
                upload5
        );

        HBox row5 = new HBox();
        row5.setPadding(new Insets(14, 15, 14, 15));
        row5.setAlignment(Pos.CENTER_LEFT);
        row5.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

        row5.getChildren().addAll(
                id5,
                name5,
                blood5,
                allergy5,
                condition5,
                visit5,
                actions5
        );

        // ==================== TABLE ====================

        VBox tableBox = new VBox();
        tableBox.setPadding(new Insets(0, 24, 0, 24));
        tableBox.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-width: 1px; -fx-background-radius: 12px; -fx-border-radius: 12px;");

        tableBox.getChildren().addAll(
                tableHeader,
                row1,
                row2,
                row3,
                row4,
                row5
        );

        // ==================== PAGINATION ====================

        HBox pagination = new HBox();
        pagination.setPadding(new Insets(16, 24, 24, 24));
        pagination.setAlignment(Pos.CENTER_RIGHT);
        pagination.setSpacing(8);

        Label showing = new Label("Showing 1 to 10 of 1,248 patients");
        showing.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");

        HBox.setHgrow(showing, Priority.ALWAYS);

        Button previous = new Button("‹");
        Button page1 = new Button("1");
        Button page2 = new Button("2");
        Button page3 = new Button("3");
        Button next = new Button("›");

        previous.setStyle("-fx-background-color: transparent; -fx-font-size: 18px;");
        page1.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-background-radius: 5px; -fx-font-weight: bold;");
        page2.setStyle("-fx-background-color: transparent; -fx-font-size: 13px;");
        page3.setStyle("-fx-background-color: transparent; -fx-font-size: 13px;");
        next.setStyle("-fx-background-color: transparent; -fx-font-size: 18px;");

        pagination.getChildren().addAll(
                showing,
                previous,
                page1,
                page2,
                page3,
                next
        );

        // ==================== LEFT CONTENT ====================

        VBox leftContent = new VBox();
        leftContent.getChildren().addAll(
                pageHeader,
                actionBar,
                tableBox,
                pagination
        );

        // ==================== MEDICAL SUMMARY ====================

        VBox medicalSummary = new VBox();
        medicalSummary.setPrefWidth(360);
        medicalSummary.setPadding(new Insets(24));
        medicalSummary.setSpacing(18);
        medicalSummary.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 0px 1px;");

        Label summaryTitle = new Label("Medical Summary");
        summaryTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        VBox patientInfo = new VBox();
        patientInfo.setAlignment(Pos.CENTER);
        patientInfo.setSpacing(8);

        Label patientIcon = new Label("👤");
        patientIcon.setPrefSize(100, 100);
        patientIcon.setAlignment(Pos.CENTER);
        patientIcon.setStyle("-fx-background-color: white; -fx-background-radius: 18px; -fx-border-color: #c3c6d7; -fx-border-radius: 18px; -fx-font-size: 45px;");

        Label summaryName = new Label("Robert Chen");
        summaryName.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label summaryId = new Label("#LL-1023");
        summaryId.setStyle("-fx-font-size: 13px; -fx-text-fill: #004ac6; -fx-font-weight: bold;");

        patientInfo.getChildren().addAll(
                patientIcon,
                summaryName,
                summaryId
        );

        // ==================== VITALS ====================

        HBox vitalsRow1 = new HBox();
        vitalsRow1.setSpacing(10);

        VBox heartRate = new VBox();
        heartRate.setPadding(new Insets(14));
        heartRate.setPrefWidth(145);
        heartRate.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label heartTitle = new Label("♥  HEART RATE");
        heartTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        Label heartValue = new Label("72 bpm");
        heartValue.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        heartRate.getChildren().addAll(
                heartTitle,
                heartValue
        );

        VBox temperature = new VBox();
        temperature.setPadding(new Insets(14));
        temperature.setPrefWidth(145);
        temperature.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label tempTitle = new Label("🌡  TEMP");
        tempTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        Label tempValue = new Label("98.6 °F");
        tempValue.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        temperature.getChildren().addAll(
                tempTitle,
                tempValue
        );

        vitalsRow1.getChildren().addAll(
                heartRate,
                temperature
        );

        HBox vitalsRow2 = new HBox();
        vitalsRow2.setSpacing(10);

        VBox bp = new VBox();
        bp.setPadding(new Insets(14));
        bp.setPrefWidth(145);
        bp.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label bpTitle = new Label("↕  BP");
        bpTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        Label bpValue = new Label("120/80 mmHg");
        bpValue.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        bp.getChildren().addAll(
                bpTitle,
                bpValue
        );

        VBox spo2 = new VBox();
        spo2.setPadding(new Insets(14));
        spo2.setPrefWidth(145);
        spo2.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label spo2Title = new Label("♨  SPO2");
        spo2Title.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        Label spo2Value = new Label("99 %");
        spo2Value.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        spo2.getChildren().addAll(
                spo2Title,
                spo2Value
        );

        vitalsRow2.getChildren().addAll(
                bp,
                spo2
        );

        // ==================== PHYSICIAN ====================

        VBox physicianBox = new VBox();
        physicianBox.setSpacing(8);

        Label physicianTitle = new Label("PRIMARY PHYSICIAN");
        physicianTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        HBox physician = new HBox();
        physician.setPadding(new Insets(14));
        physician.setSpacing(12);
        physician.setAlignment(Pos.CENTER_LEFT);
        physician.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label doctorIcon = new Label("⚕");
        doctorIcon.setStyle("-fx-font-size: 24px; -fx-text-fill: #004ac6;");

        VBox doctorDetails = new VBox();

        Label doctorName = new Label("Dr. Elena Vance");
        doctorName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label doctorRole = new Label("Chief Cardiologist");
        doctorRole.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

        doctorDetails.getChildren().addAll(
                doctorName,
                doctorRole
        );

        physician.getChildren().addAll(
                doctorIcon,
                doctorDetails
        );

        physicianBox.getChildren().addAll(
                physicianTitle,
                physician
        );

        // ==================== RECENT RECORDS ====================

        VBox recentRecords = new VBox();
        recentRecords.setSpacing(8);

        Label recentTitle = new Label("RECENT RECORDS");
        recentTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        HBox record1 = new HBox();
        record1.setPadding(new Insets(8));
        record1.setAlignment(Pos.CENTER_LEFT);
        record1.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");

        Label recordName1 = new Label("▣  Cardiac MRI Scan");
        Label recordDate1 = new Label("Nov 10");
        HBox.setHgrow(recordName1, Priority.ALWAYS);

        record1.getChildren().addAll(
                recordName1,
                recordDate1
        );

        HBox record2 = new HBox();
        record2.setPadding(new Insets(8));
        record2.setAlignment(Pos.CENTER_LEFT);
        record2.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");

        Label recordName2 = new Label("▣  Lipid Profile Test");
        Label recordDate2 = new Label("Nov 08");
        HBox.setHgrow(recordName2, Priority.ALWAYS);

        record2.getChildren().addAll(
                recordName2,
                recordDate2
        );

        HBox record3 = new HBox();
        record3.setPadding(new Insets(8));
        record3.setAlignment(Pos.CENTER_LEFT);
        record3.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");

        Label recordName3 = new Label("▣  Flu Vaccination");
        Label recordDate3 = new Label("Oct 15");
        HBox.setHgrow(recordName3, Priority.ALWAYS);

        record3.getChildren().addAll(
                recordName3,
                recordDate3
        );

        recentRecords.getChildren().addAll(
                recentTitle,
                record1,
                record2,
                record3
        );

        Button analyticsButton = new Button("⌁  Full Medical Analytics");
        analyticsButton.setMaxWidth(Double.MAX_VALUE);
        analyticsButton.setPrefHeight(45);
        analyticsButton.setStyle("-fx-background-color: transparent; -fx-border-color: #004ac6; -fx-border-width: 2px; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-text-fill: #004ac6; -fx-font-weight: bold;");

        medicalSummary.getChildren().addAll(
                summaryTitle,
                patientInfo,
                vitalsRow1,
                vitalsRow2,
                physicianBox,
                recentRecords,
                analyticsButton
        );

        // ==================== MAIN CONTENT ====================

        HBox content = new HBox();
        HBox.setHgrow(leftContent, Priority.ALWAYS);

        content.getChildren().addAll(
                leftContent,
                medicalSummary
        );

        // ==================== SCROLL ====================

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: #faf8ff; -fx-background: #faf8ff;");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // ==================== FINAL PAGE ====================

        mainBox.getChildren().addAll(
                topBar,
                scrollPane
        );

        return mainBox;
    }
}

