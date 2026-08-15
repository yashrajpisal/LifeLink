package com.kurukshetra.view.hospital;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class HospitalEmergencyRequests {

    public VBox getEmergencyRequests() {

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(25));
        mainContent.setStyle("-fx-background-color: #faf8ff;");

        // =========================
        // PAGE HEADER
        // =========================

        Text heading = new Text("Active Emergency Requests");
        heading.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #191b23;");

        Text subHeading = new Text("Real-time status monitoring for all incoming emergency cases.");
        subHeading.setStyle("-fx-font-size: 14px;" + "-fx-fill: #737686;");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(
                heading,
                subHeading
        );

        Button manualEntryButton = new Button("+   Manual Entry");
        manualEntryButton.setPrefWidth(135);
        manualEntryButton.setPrefHeight(42);
        manualEntryButton.setStyle("-fx-background-color: #004ac6;" + "-fx-text-fill: white;" + "-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 9px;");

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox header = new HBox(
                headingBox,
                headerSpacer,
                manualEntryButton
        );

        header.setAlignment(Pos.CENTER_LEFT);

        // =========================
        // FILTER SECTION
        // =========================

        Text filterTitle = new Text("FILTER SEVERITY:");
        filterTitle.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-fill: #737686;");

        Button allButton = new Button("All Requests");
        allButton.setPrefHeight(32);
        allButton.setStyle("-fx-background-color: #004ac6;" + "-fx-text-fill: white;" + "-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 18px;" + "-fx-padding: 5px 14px;");

        Button criticalButton = new Button("Critical   4");
        criticalButton.setPrefHeight(32);
        criticalButton.setStyle("-fx-background-color: #ffffff;" + "-fx-text-fill: #ba1a1a;" + "-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-border-color: #c3c6d7;" + "-fx-border-radius: 18px;" + "-fx-background-radius: 18px;" + "-fx-padding: 5px 14px;");

        Button moderateButton = new Button("Moderate   8");
        moderateButton.setPrefHeight(32);
        moderateButton.setStyle("-fx-background-color: #ffffff;" + "-fx-text-fill: #943700;" + "-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-border-color: #c3c6d7;" + "-fx-border-radius: 18px;" + "-fx-background-radius: 18px;" + "-fx-padding: 5px 14px;");

        Button minorButton = new Button("Minor");
        minorButton.setPrefHeight(32);
        minorButton.setStyle("-fx-background-color: #ffffff;" + "-fx-text-fill: #505f76;" + "-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-border-color: #c3c6d7;" + "-fx-border-radius: 18px;" + "-fx-background-radius: 18px;" + "-fx-padding: 5px 14px;");

        HBox filterRow = new HBox(
                10,
                filterTitle,
                allButton,
                criticalButton,
                moderateButton,
                minorButton
        );

        filterRow.setAlignment(Pos.CENTER_LEFT);

        // =========================
        // SUMMARY CARDS
        // =========================

        HBox summaryRow = new HBox(15);
        summaryRow.setAlignment(Pos.CENTER);

        // Critical Cases
        VBox criticalBox = new VBox(5);
        criticalBox.setPadding(new Insets(15));
        criticalBox.setPrefHeight(105);
        criticalBox.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text criticalTitle = new Text("CRITICAL CASES");
        criticalTitle.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #737686;");

        Text criticalValue = new Text("04");
        criticalValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #ba1a1a;");

        Text criticalInfo = new Text("Requires immediate attention");
        criticalInfo.setStyle("-fx-font-size: 10px;" + "-fx-fill: #ba1a1a;");

        criticalBox.getChildren().addAll(
                criticalTitle,
                criticalValue,
                criticalInfo
        );

        // Active Ambulances
        VBox ambulanceBox = new VBox(5);
        ambulanceBox.setPadding(new Insets(15));
        ambulanceBox.setPrefHeight(105);
        ambulanceBox.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text ambulanceTitle = new Text("ACTIVE AMBULANCES");
        ambulanceTitle.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #737686;");

        Text ambulanceValue = new Text("12");
        ambulanceValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #004ac6;");

        Text ambulanceInfo = new Text("Currently deployed");
        ambulanceInfo.setStyle("-fx-font-size: 10px;" + "-fx-fill: #004ac6;");

        ambulanceBox.getChildren().addAll(
                ambulanceTitle,
                ambulanceValue,
                ambulanceInfo
        );

        // Response Time
        VBox responseBox = new VBox(5);
        responseBox.setPadding(new Insets(15));
        responseBox.setPrefHeight(105);
        responseBox.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text responseTitle = new Text("AVG. RESPONSE TIME");
        responseTitle.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #737686;");

        Text responseValue = new Text("4.2m");
        responseValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #943700;");

        Text responseInfo = new Text("Average emergency response");
        responseInfo.setStyle("-fx-font-size: 10px;" + "-fx-fill: #943700;");

        responseBox.getChildren().addAll(
                responseTitle,
                responseValue,
                responseInfo
        );

        // Available ER Beds
        VBox bedsBox = new VBox(5);
        bedsBox.setPadding(new Insets(15));
        bedsBox.setPrefHeight(105);
        bedsBox.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text bedsTitle = new Text("AVAILABLE ER BEDS");
        bedsTitle.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #737686;");

        Text bedsValue = new Text("06");
        bedsValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #505f76;");

        Text bedsInfo = new Text("Emergency beds available");
        bedsInfo.setStyle("-fx-font-size: 10px;" + "-fx-fill: #505f76;");

        bedsBox.getChildren().addAll(
                bedsTitle,
                bedsValue,
                bedsInfo
        );

        HBox.setHgrow(criticalBox, Priority.ALWAYS);
        HBox.setHgrow(ambulanceBox, Priority.ALWAYS);
        HBox.setHgrow(responseBox, Priority.ALWAYS);
        HBox.setHgrow(bedsBox, Priority.ALWAYS);

        summaryRow.getChildren().addAll(
                criticalBox,
                ambulanceBox,
                responseBox,
                bedsBox
        );

        // =========================
        // SEARCH / FILTER
        // =========================

        HBox searchFilterBox = new HBox(12);
        searchFilterBox.setPadding(new Insets(15));
        searchFilterBox.setAlignment(Pos.CENTER_LEFT);
        searchFilterBox.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search emergency ID, patient or emergency type...");
        searchField.setPrefHeight(40);
        searchField.setPrefWidth(430);
        searchField.setStyle("-fx-background-color: #ffffff;" + "-fx-border-color: #c3c6d7;" + "-fx-border-radius: 9px;" + "-fx-background-radius: 9px;" + "-fx-font-size: 13px;");

        ComboBox<String> severityCombo = new ComboBox<>();
        severityCombo.getItems().addAll(
                "All Severity",
                "High",
                "Medium",
                "Low"
        );
        severityCombo.setValue("All Severity");
        severityCombo.setPrefHeight(40);
        severityCombo.setPrefWidth(160);

        ComboBox<String> ambulanceStatusCombo = new ComboBox<>();
        ambulanceStatusCombo.getItems().addAll(
                "All Ambulance Status",
                "En route",
                "Arrived",
                "At Bay"
        );
        ambulanceStatusCombo.setValue("All Ambulance Status");
        ambulanceStatusCombo.setPrefHeight(40);
        ambulanceStatusCombo.setPrefWidth(190);

        searchFilterBox.getChildren().addAll(
                searchField,
                severityCombo,
                ambulanceStatusCombo
        );

        // =========================
        // EMERGENCY REQUEST CARD
        // =========================

        VBox requestCard = new VBox();
        requestCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 15px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 15px;");

        Text requestTitle = new Text("Emergency Request Queue");
        requestTitle.setStyle("-fx-font-size: 19px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text requestInfo = new Text("Incoming ambulance and patient emergency requests");

        requestInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #737686;");

        VBox requestTitleBox = new VBox(4);
        requestTitleBox.getChildren().addAll(
                requestTitle,
                requestInfo
        );

        Button filterButton = new Button("☷");
        filterButton.setPrefSize(38, 35);
        filterButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #374151;" + "-fx-font-size: 16px;");

        Button moreButton = new Button("⋮");
        moreButton.setPrefSize(38, 35);
        moreButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #374151;" + "-fx-font-size: 18px;");

        HBox requestActions = new HBox(3);
        requestActions.setAlignment(Pos.CENTER_RIGHT);
        requestActions.getChildren().addAll(
                filterButton,
                moreButton
        );

        Region requestHeaderSpacer = new Region();
        HBox.setHgrow(requestHeaderSpacer, Priority.ALWAYS);

        HBox requestHeader = new HBox(
                requestTitleBox,
                requestHeaderSpacer,
                requestActions
        );

        requestHeader.setPadding(new Insets(16));
        requestHeader.setAlignment(Pos.CENTER_LEFT);

        // =========================
        // TABLE HEADER
        // =========================

        HBox tableHeader = new HBox(10);
        tableHeader.setPadding(new Insets(12, 15, 12, 15));
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setStyle("-fx-background-color: #e9e9f5;");

        Text emergencyIdHeader = new Text("EMERGENCY ID");
        emergencyIdHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-fill: #6b7280;");
        HBox emergencyIdHeaderBox = new HBox(emergencyIdHeader);
        emergencyIdHeaderBox.setPrefWidth(100);

        Text patientHeader = new Text("PATIENT");
        patientHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-fill: #6b7280;");
        HBox patientHeaderBox = new HBox(patientHeader);
        patientHeaderBox.setPrefWidth(145);

        Text typeHeader = new Text("EMERGENCY TYPE");
        typeHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-fill: #6b7280;");
        HBox typeHeaderBox = new HBox(typeHeader);
        typeHeaderBox.setPrefWidth(145);

        Text severityHeader = new Text("SEVERITY");
        severityHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-fill: #6b7280;");
        HBox severityHeaderBox = new HBox(severityHeader);
        severityHeaderBox.setPrefWidth(90);

        Text etaHeader = new Text("ETA");
        etaHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-fill: #6b7280;");
        HBox etaHeaderBox = new HBox(etaHeader);
        etaHeaderBox.setPrefWidth(75);
        etaHeaderBox.setAlignment(Pos.CENTER);

        Text departmentHeader = new Text("RECOMMENDED DEPT.");
        departmentHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-fill: #6b7280;");
        HBox departmentHeaderBox = new HBox(departmentHeader);
        departmentHeaderBox.setPrefWidth(155);

        Text ambulanceStatusHeader = new Text("AMBULANCE");
        ambulanceStatusHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-fill: #6b7280;");
        HBox ambulanceStatusHeaderBox = new HBox(ambulanceStatusHeader);
        ambulanceStatusHeaderBox.setPrefWidth(115);

        Text actionHeader = new Text("ACTION");
        actionHeader.setStyle("-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-fill: #6b7280;");
        HBox actionHeaderBox = new HBox(actionHeader);
        actionHeaderBox.setPrefWidth(180);
        actionHeaderBox.setAlignment(Pos.CENTER_RIGHT);

        tableHeader.getChildren().addAll(
                emergencyIdHeaderBox,
                patientHeaderBox,
                typeHeaderBox,
                severityHeaderBox,
                etaHeaderBox,
                departmentHeaderBox,
                ambulanceStatusHeaderBox,
                actionHeaderBox
        );

        // =========================
        // ROW 1
        // =========================

        HBox row1 = new HBox(10);
        row1.setPadding(new Insets(14, 15, 14, 15));
        row1.setAlignment(Pos.CENTER_LEFT);
        row1.setStyle("-fx-border-color: transparent transparent #e5e7eb transparent;" + "-fx-border-width: 0px 0px 1px 0px;");

        Text id1 = new Text("#ER-8842");
        id1.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");
        HBox idBox1 = new HBox(id1);
        idBox1.setPrefWidth(100);

        Text patient1 = new Text("James Harrison");
        patient1.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");
        HBox patientBox1 = new HBox(patient1);
        patientBox1.setPrefWidth(145);

        Text type1 = new Text("♥  Cardiac Arrest");
        type1.setStyle("-fx-font-size: 12px;" + "-fx-fill: #ba1a1a;");
        HBox typeBox1 = new HBox(type1);
        typeBox1.setPrefWidth(145);

        Text severity1 = new Text("HIGH");
        severity1.setStyle("-fx-background-color: #ffdad6;" + "-fx-text-fill: #ba1a1a;" + "-fx-font-size: 9px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 12px;" + "-fx-padding: 5px 10px;");
        HBox severityBox1 = new HBox(severity1);
        severityBox1.setPrefWidth(90);
        severityBox1.setAlignment(Pos.CENTER_LEFT);

        Text eta1 = new Text("2 min");
        eta1.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #ba1a1a;");
        HBox etaBox1 = new HBox(eta1);
        etaBox1.setPrefWidth(75);
        etaBox1.setAlignment(Pos.CENTER);

        Text dept1 = new Text("Cardiology (ER-A)");
        dept1.setStyle("-fx-font-size: 11px;" + "-fx-fill: #374151;");
        HBox deptBox1 = new HBox(dept1);
        deptBox1.setPrefWidth(155);

        Text ambulance1 = new Text("●  En route");
        ambulance1.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #004ac6;");
        HBox ambulanceBox1 = new HBox(ambulance1);
        ambulanceBox1.setPrefWidth(115);

        Button accept1 = new Button("Accept");
        accept1.setPrefWidth(65);
        accept1.setStyle("-fx-background-color: #10b981;" + "-fx-text-fill: white;" + "-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 7px;");

        Button reject1 = new Button("Reject");
        reject1.setPrefWidth(65);
        reject1.setStyle("-fx-background-color: #64748b;" + "-fx-text-fill: white;" + "-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 7px;");

        Button view1 = new Button("◉");
        view1.setPrefWidth(35);
        view1.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #004ac6;" + "-fx-font-size: 14px;");

        HBox actions1 = new HBox(5, accept1, reject1, view1);
        actions1.setPrefWidth(180);
        actions1.setAlignment(Pos.CENTER_RIGHT);

        row1.getChildren().addAll(
                idBox1,
                patientBox1,
                typeBox1,
                severityBox1,
                etaBox1,
                deptBox1,
                ambulanceBox1,
                actions1
        );

        // =========================
        // ROW 2
        // =========================

        HBox row2 = new HBox(10);
        row2.setPadding(new Insets(14, 15, 14, 15));
        row2.setAlignment(Pos.CENTER_LEFT);
        row2.setStyle("-fx-border-color: transparent transparent #e5e7eb transparent;" + "-fx-border-width: 0px 0px 1px 0px;");

        Text id2 = new Text("#ER-8843");
        id2.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");
        HBox idBox2 = new HBox(id2);
        idBox2.setPrefWidth(100);

        Text patient2 = new Text("Maria Rodriguez");
        patient2.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");
        HBox patientBox2 = new HBox(patient2);
        patientBox2.setPrefWidth(145);

        Text type2 = new Text("◉  Respiratory Distress");
        type2.setStyle("-fx-font-size: 12px;" + "-fx-fill: #943700;");
        HBox typeBox2 = new HBox(type2);
        typeBox2.setPrefWidth(145);

        Text severity2 = new Text("MEDIUM");
        severity2.setStyle("-fx-background-color: #ffdbcd;" + "-fx-text-fill: #943700;" + "-fx-font-size: 9px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 12px;" + "-fx-padding: 5px 8px;");
        HBox severityBox2 = new HBox(severity2);
        severityBox2.setPrefWidth(90);
        severityBox2.setAlignment(Pos.CENTER_LEFT);

        Text eta2 = new Text("5 min");
        eta2.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");
        HBox etaBox2 = new HBox(eta2);
        etaBox2.setPrefWidth(75);
        etaBox2.setAlignment(Pos.CENTER);

        Text dept2 = new Text("Pulmonology (ER-B)");
        dept2.setStyle("-fx-font-size: 11px;" + "-fx-fill: #374151;");
        HBox deptBox2 = new HBox(dept2);
        deptBox2.setPrefWidth(155);

        Text ambulance2 = new Text("●  En route");
        ambulance2.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #004ac6;");
        HBox ambulanceBox2 = new HBox(ambulance2);
        ambulanceBox2.setPrefWidth(115);

        Button accept2 = new Button("Accept");
        accept2.setPrefWidth(65);
        accept2.setStyle("-fx-background-color: #10b981;" + "-fx-text-fill: white;" + "-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 7px;");

        Button reject2 = new Button("Reject");
        reject2.setPrefWidth(65);
        reject2.setStyle("-fx-background-color: #64748b;" + "-fx-text-fill: white;" + "-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 7px;");

        Button view2 = new Button("◉");
        view2.setPrefWidth(35);
        view2.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #004ac6;" + "-fx-font-size: 14px;");

        HBox actions2 = new HBox(5, accept2, reject2, view2);
        actions2.setPrefWidth(180);
        actions2.setAlignment(Pos.CENTER_RIGHT);

        row2.getChildren().addAll(
                idBox2,
                patientBox2,
                typeBox2,
                severityBox2,
                etaBox2,
                deptBox2,
                ambulanceBox2,
                actions2
        );

        // =========================
        // ROW 3
        // =========================

        HBox row3 = new HBox(10);
        row3.setPadding(new Insets(14, 15, 14, 15));
        row3.setAlignment(Pos.CENTER_LEFT);
        row3.setStyle("-fx-border-color: transparent transparent #e5e7eb transparent;" + "-fx-border-width: 0px 0px 1px 0px;");

        Text id3 = new Text("#ER-8844");
        id3.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");
        HBox idBox3 = new HBox(id3);
        idBox3.setPrefWidth(100);

        Text patient3 = new Text("Robert Fletcher");
        patient3.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");
        HBox patientBox3 = new HBox(patient3);
        patientBox3.setPrefWidth(145);

        Text type3 = new Text("⚠  Multi-Trauma");
        type3.setStyle("-fx-font-size: 12px;" + "-fx-fill: #ba1a1a;");
        HBox typeBox3 = new HBox(type3);
        typeBox3.setPrefWidth(145);

        Text severity3 = new Text("HIGH");
        severity3.setStyle("-fx-background-color: #ffdad6;" + "-fx-text-fill: #ba1a1a;" + "-fx-font-size: 9px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 12px;" + "-fx-padding: 5px 10px;");
        HBox severityBox3 = new HBox(severity3);
        severityBox3.setPrefWidth(90);
        severityBox3.setAlignment(Pos.CENTER_LEFT);

        Text eta3 = new Text("Arrived");
        eta3.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #16a34a;");
        HBox etaBox3 = new HBox(eta3);
        etaBox3.setPrefWidth(75);
        etaBox3.setAlignment(Pos.CENTER);

        Text dept3 = new Text("Trauma Unit (ER-A)");
        dept3.setStyle("-fx-font-size: 11px;" + "-fx-fill: #374151;");
        HBox deptBox3 = new HBox(dept3);
        deptBox3.setPrefWidth(155);

        Text ambulance3 = new Text("●  At Bay 4");
        ambulance3.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #737686;");
        HBox ambulanceBox3 = new HBox(ambulance3);
        ambulanceBox3.setPrefWidth(115);

        Button accept3 = new Button("Accept");
        accept3.setPrefWidth(65);
        accept3.setStyle("-fx-background-color: #10b981;" + "-fx-text-fill: white;" + "-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 7px;");

        Button reject3 = new Button("Reject");
        reject3.setPrefWidth(65);
        reject3.setStyle("-fx-background-color: #64748b;" + "-fx-text-fill: white;" + "-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 7px;");

        Button view3 = new Button("◉");
        view3.setPrefWidth(35);
        view3.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #004ac6;" + "-fx-font-size: 14px;");

        HBox actions3 = new HBox(5, accept3, reject3, view3);
        actions3.setPrefWidth(180);
        actions3.setAlignment(Pos.CENTER_RIGHT);

        row3.getChildren().addAll(
                idBox3,
                patientBox3,
                typeBox3,
                severityBox3,
                etaBox3,
                deptBox3,
                ambulanceBox3,
                actions3
        );

        // =========================
        // ROW 4
        // =========================

        HBox row4 = new HBox(10);
        row4.setPadding(new Insets(14, 15, 14, 15));
        row4.setAlignment(Pos.CENTER_LEFT);
        row4.setStyle("-fx-border-color: transparent transparent #e5e7eb transparent;" + "-fx-border-width: 0px 0px 1px 0px;");

        Text id4 = new Text("#ER-8845");
        id4.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");
        HBox idBox4 = new HBox(id4);
        idBox4.setPrefWidth(100);

        Text patient4 = new Text("Sarah Jenkins");
        patient4.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");
        HBox patientBox4 = new HBox(patient4);
        patientBox4.setPrefWidth(145);

        Text type4 = new Text("●  Minor Laceration");
        type4.setStyle("-fx-font-size: 12px;" + "-fx-fill: #505f76;");
        HBox typeBox4 = new HBox(type4);
        typeBox4.setPrefWidth(145);

        Text severity4 = new Text("LOW");
        severity4.setStyle("-fx-background-color: #d3e4fe;" + "-fx-text-fill: #505f76;" + "-fx-font-size: 9px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 12px;" + "-fx-padding: 5px 10px;");
        HBox severityBox4 = new HBox(severity4);
        severityBox4.setPrefWidth(90);
        severityBox4.setAlignment(Pos.CENTER_LEFT);

        Text eta4 = new Text("12 min");
        eta4.setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;" + "-fx-fill: #374151;");
        HBox etaBox4 = new HBox(eta4);
        etaBox4.setPrefWidth(75);
        etaBox4.setAlignment(Pos.CENTER);

        Text dept4 = new Text("Urgent Care (ER-C)");
        dept4.setStyle("-fx-font-size: 11px;" + "-fx-fill: #374151;");
        HBox deptBox4 = new HBox(dept4);
        deptBox4.setPrefWidth(155);

        Text ambulance4 = new Text("●  En route");
        ambulance4.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #004ac6;");
        HBox ambulanceBox4 = new HBox(ambulance4);
        ambulanceBox4.setPrefWidth(115);

        Button accept4 = new Button("Accept");
        accept4.setPrefWidth(65);
        accept4.setStyle("-fx-background-color: #10b981;" + "-fx-text-fill: white;" + "-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 7px;");

        Button reject4 = new Button("Reject");
        reject4.setPrefWidth(65);
        reject4.setStyle("-fx-background-color: #64748b;" + "-fx-text-fill: white;" + "-fx-font-size: 10px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 7px;");

        Button view4 = new Button("◉");
        view4.setPrefWidth(35);
        view4.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #004ac6;" + "-fx-font-size: 14px;");

        HBox actions4 = new HBox(5, accept4, reject4, view4);
        actions4.setPrefWidth(180);
        actions4.setAlignment(Pos.CENTER_RIGHT);

        row4.getChildren().addAll(
                idBox4,
                patientBox4,
                typeBox4,
                severityBox4,
                etaBox4,
                deptBox4,
                ambulanceBox4,
                actions4
        );

        // =========================
        // REQUEST CARD
        // =========================

        requestCard.getChildren().addAll(
                requestHeader,
                tableHeader,
                row1,
                row2,
                row3,
                row4
        );

        // =========================
        // PAGINATION
        // =========================

        Text showingText = new Text("Showing 4 of 24 active requests");
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

        // =========================
        // SYSTEM NOTICE
        // =========================

        VBox noticeBox = new VBox(4);
        noticeBox.setPadding(new Insets(14));
        noticeBox.setStyle("-fx-background-color: #eff6ff;" + "-fx-border-color: #bfdbfe;" + "-fx-border-radius: 12px;" + "-fx-background-radius: 12px;");

        Text noticeTitle = new Text("●  System Notice");
        noticeTitle.setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-fill: #004ac6;");

        Text noticeText = new Text("All available ambulances in the Northern District are currently deployed. Redirecting new Minor calls to St. Jude’s.");
        noticeText.setStyle("-fx-font-size: 11px;" + "-fx-fill: #54647a;");
        noticeText.setWrappingWidth(900);

        noticeBox.getChildren().addAll(
                noticeTitle,
                noticeText
        );

        Button routingButton = new Button("View Routing Map");
        routingButton.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #004ac6;" + "-fx-font-size: 11px;" + "-fx-font-weight: bold;");

        Region noticeSpacer = new Region();
        HBox.setHgrow(noticeSpacer, Priority.ALWAYS);

        HBox notice = new HBox(
                noticeBox,
                noticeSpacer,
                routingButton
        );

        notice.setAlignment(Pos.CENTER_LEFT);
        notice.setPadding(new Insets(5));

        // =========================
        // MAIN CONTENT
        // =========================

        mainContent.getChildren().addAll(
                header,
                filterRow,
                summaryRow,
                searchFilterBox,
                requestCard,
                pagination,
                notice
        );

        // =========================
        // SCROLL PANE
        // =========================

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent;" + "-fx-background: transparent;");

        VBox finalContent = new VBox(scrollPane);
        finalContent.setStyle("-fx-background-color: #faf8ff;");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return finalContent;
    }
}