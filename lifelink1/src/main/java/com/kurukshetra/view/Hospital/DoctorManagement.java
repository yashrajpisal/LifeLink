package com.kurukshetra.view.Hospital;

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

public class DoctorManagement {

    public VBox getDoctorManagement() {

        // Main content container
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(25));
        mainContent.setStyle("-fx-background-color: #faf8ff;");

        // Page heading
        Text heading = new Text("Medical Staff Oversight");
        heading.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text subHeading = new Text("Manage specialized practitioners and monitor real-time availability across hospital wings.");
        subHeading.setStyle("-fx-font-size: 14px; -fx-fill: #434655;");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(
                heading,
                subHeading
        );

        // Add doctor button
        Button addDoctorButton = new Button("+   Add Doctor");
        addDoctorButton.setPrefWidth(125);
        addDoctorButton.setPrefHeight(42);
        addDoctorButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 9px;");

        // Assign emergency button
        Button assignEmergencyButton = new Button("⚕   Assign Emergency");
        assignEmergencyButton.setPrefWidth(155);
        assignEmergencyButton.setPrefHeight(42);
        assignEmergencyButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #191b23; -fx-font-size: 12px; -fx-font-weight: bold; -fx-border-color: #c3c6d7; -fx-border-radius: 9px; -fx-background-radius: 9px;");

        HBox headerButtons = new HBox(10);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);
        headerButtons.getChildren().addAll(
                addDoctorButton,
                assignEmergencyButton
        );

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox header = new HBox(
                headingBox,
                headerSpacer,
                headerButtons
        );
        header.setAlignment(Pos.CENTER_LEFT);

        // Statistics cards
        HBox statsRow = new HBox(15);
        statsRow.setAlignment(Pos.CENTER);

        VBox activeSurgeonBox = new VBox(5);
        activeSurgeonBox.setPadding(new Insets(15));
        activeSurgeonBox.setPrefHeight(105);
        activeSurgeonBox.setStyle("-fx-background-color: #f3f3fe; -fx-background-radius: 12px; -fx-border-color: #c3c6d7; -fx-border-radius: 12px;");

        Circle surgeonCircle = new Circle(25);
        surgeonCircle.setFill(Color.web("#dbe1ff"));

        Text surgeonIcon = new Text("⚕");
        surgeonIcon.setStyle("-fx-font-size: 20px; -fx-fill: #004ac6;");

        StackPane surgeonIconPane = new StackPane();
        surgeonIconPane.setPrefSize(50, 50);
        surgeonIconPane.getChildren().addAll(
                surgeonCircle,
                surgeonIcon
        );

        Text surgeonTitle = new Text("ACTIVE SURGEONS");
        surgeonTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        Text surgeonValue = new Text("12");
        surgeonValue.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text surgeonInfo = new Text("4 in surgery");
        surgeonInfo.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        VBox surgeonTextBox = new VBox(2);
        surgeonTextBox.getChildren().addAll(
                surgeonTitle,
                surgeonValue,
                surgeonInfo
        );

        HBox surgeonContent = new HBox(15);
        surgeonContent.setAlignment(Pos.CENTER_LEFT);
        surgeonContent.getChildren().addAll(
                surgeonIconPane,
                surgeonTextBox
        );

        activeSurgeonBox.getChildren().add(surgeonContent);

        VBox doctorsDutyBox = new VBox(5);
        doctorsDutyBox.setPadding(new Insets(15));
        doctorsDutyBox.setPrefHeight(105);
        doctorsDutyBox.setStyle("-fx-background-color: #f3f3fe; -fx-background-radius: 12px; -fx-border-color: #c3c6d7; -fx-border-radius: 12px;");

        Circle dutyCircle = new Circle(25);
        dutyCircle.setFill(Color.web("#d0e1fb"));

        Text dutyIcon = new Text("✚");
        dutyIcon.setStyle("-fx-font-size: 20px; -fx-fill: #505f76;");

        StackPane dutyIconPane = new StackPane();
        dutyIconPane.setPrefSize(50, 50);
        dutyIconPane.getChildren().addAll(
                dutyCircle,
                dutyIcon
        );

        Text dutyTitle = new Text("DOCTORS ON DUTY");
        dutyTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        Text dutyValue = new Text("48");
        dutyValue.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text dutyInfo = new Text("Across 8 departments");
        dutyInfo.setStyle("-fx-font-size: 10px; -fx-fill: #434655;");

        VBox dutyTextBox = new VBox(2);
        dutyTextBox.getChildren().addAll(
                dutyTitle,
                dutyValue,
                dutyInfo
        );

        HBox dutyContent = new HBox(15);
        dutyContent.setAlignment(Pos.CENTER_LEFT);
        dutyContent.getChildren().addAll(
                dutyIconPane,
                dutyTextBox
        );

        doctorsDutyBox.getChildren().add(dutyContent);

        VBox capacityBox = new VBox(5);
        capacityBox.setPadding(new Insets(15));
        capacityBox.setPrefHeight(105);
        capacityBox.setStyle("-fx-background-color: #f3f3fe; -fx-background-radius: 12px; -fx-border-color: #c3c6d7; -fx-border-radius: 12px;");

        Circle capacityCircle = new Circle(25);
        capacityCircle.setFill(Color.web("#ffdbcd"));

        Text capacityIcon = new Text("♟");
        capacityIcon.setStyle("-fx-font-size: 20px; -fx-fill: #943700;");

        StackPane capacityIconPane = new StackPane();
        capacityIconPane.setPrefSize(50, 50);
        capacityIconPane.getChildren().addAll(
                capacityCircle,
                capacityIcon
        );

        Text capacityTitle = new Text("STAFF CAPACITY");
        capacityTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #737686;");

        Text capacityValue = new Text("85%");
        capacityValue.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text capacityChange = new Text("↗ +2%");
        capacityChange.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #ba1a1a;");

        HBox capacityValueBox = new HBox(15);
        capacityValueBox.setAlignment(Pos.CENTER_LEFT);
        capacityValueBox.getChildren().addAll(
                capacityValue,
                capacityChange
        );

        Region capacityBackground = new Region();
        capacityBackground.setPrefHeight(6);
        capacityBackground.setPrefWidth(150);
        capacityBackground.setStyle("-fx-background-color: #e7e7f3; -fx-background-radius: 10px;");

        Region capacityProgress = new Region();
        capacityProgress.setPrefHeight(6);
        capacityProgress.setPrefWidth(128);
        capacityProgress.setStyle("-fx-background-color: #004ac6; -fx-background-radius: 10px;");

        StackPane capacityProgressPane = new StackPane();
        capacityProgressPane.setAlignment(Pos.CENTER_LEFT);
        capacityProgressPane.getChildren().addAll(
                capacityBackground,
                capacityProgress
        );

        VBox capacityTextBox = new VBox(2);
        capacityTextBox.getChildren().addAll(
                capacityTitle,
                capacityValueBox,
                capacityProgressPane
        );

        HBox capacityContent = new HBox(15);
        capacityContent.setAlignment(Pos.CENTER_LEFT);
        capacityContent.getChildren().addAll(
                capacityIconPane,
                capacityTextBox
        );

        capacityBox.getChildren().add(capacityContent);

        HBox.setHgrow(activeSurgeonBox, Priority.ALWAYS);
        HBox.setHgrow(doctorsDutyBox, Priority.ALWAYS);
        HBox.setHgrow(capacityBox, Priority.ALWAYS);

        statsRow.getChildren().addAll(
                activeSurgeonBox,
                doctorsDutyBox,
                capacityBox
        );

        // Table filters
        HBox filterBox = new HBox(12);
        filterBox.setPadding(new Insets(15));
        filterBox.setAlignment(Pos.CENTER_LEFT);
        filterBox.setStyle("-fx-background-color: #f3f3fe; -fx-background-radius: 12px; -fx-border-color: #c3c6d7; -fx-border-radius: 12px;");

        ComboBox<String> departmentCombo = new ComboBox<>();
        departmentCombo.getItems().addAll(
                "Department: All",
                "Cardiology",
                "Orthopedics",
                "Pediatrics",
                "Oncology",
                "Neurology"
        );
        departmentCombo.setValue("Department: All");
        departmentCombo.setPrefHeight(40);
        departmentCombo.setPrefWidth(175);

        ComboBox<String> availabilityCombo = new ComboBox<>();
        availabilityCombo.getItems().addAll(
                "Availability: Available",
                "Available",
                "In Surgery",
                "On Break"
        );
        availabilityCombo.setValue("Availability: Available");
        availabilityCombo.setPrefHeight(40);
        availabilityCombo.setPrefWidth(185);

        Region filterSpacer = new Region();
        HBox.setHgrow(filterSpacer, Priority.ALWAYS);

        Button filterButton = new Button("☷");
        filterButton.setPrefSize(40, 38);
        filterButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 17px;");

        Button downloadButton = new Button("↓");
        downloadButton.setPrefSize(40, 38);
        downloadButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 17px;");

        filterBox.getChildren().addAll(
                departmentCombo,
                availabilityCombo,
                filterSpacer,
                filterButton,
                downloadButton
        );

        // Doctor table card
        VBox doctorCard = new VBox();
        doctorCard.setStyle("-fx-background-color: #f3f3fe; -fx-background-radius: 15px; -fx-border-color: #c3c6d7; -fx-border-radius: 15px;");

        // Table header
        HBox tableHeader = new HBox(10);
        tableHeader.setPadding(new Insets(12, 15, 12, 15));
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setStyle("-fx-background-color: #f1f5f9;");

        Label doctorNameHeader = new Label("DOCTOR NAME");
        doctorNameHeader.setPrefWidth(190);
        doctorNameHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #737686;");

        Label departmentHeader = new Label("DEPARTMENT");
        departmentHeader.setPrefWidth(115);
        departmentHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #737686;");

        Label specializationHeader = new Label("SPECIALIZATION");
        specializationHeader.setPrefWidth(160);
        specializationHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #737686;");

        Label shiftHeader = new Label("SHIFT");
        shiftHeader.setPrefWidth(70);
        shiftHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #737686;");

        Label availabilityHeader = new Label("AVAILABILITY");
        availabilityHeader.setPrefWidth(125);
        availabilityHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #737686;");

        Label statusHeader = new Label("STATUS");
        statusHeader.setPrefWidth(125);
        statusHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #737686;");

        Label actionHeader = new Label("ACTIONS");
        actionHeader.setPrefWidth(85);
        actionHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #737686;");

        // Add table headers
        tableHeader.getChildren().addAll(
                doctorNameHeader,
                departmentHeader,
                specializationHeader,
                shiftHeader,
                availabilityHeader,
                statusHeader,
                actionHeader
        );

        // Doctor row 1
        HBox doctorRow1 = new HBox(10);
        doctorRow1.setPadding(new Insets(14, 15, 14, 15));
        doctorRow1.setAlignment(Pos.CENTER_LEFT);
        doctorRow1.setStyle("-fx-background-color: #ffffff; -fx-border-color: transparent transparent #c3c6d7 transparent; -fx-border-width: 0px 0px 1px 0px;");

        Circle doctorCircle1 = new Circle(20);
        doctorCircle1.setFill(Color.web("#dbe1ff"));

        Text doctorInitial1 = new Text("JW");
        doctorInitial1.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        StackPane doctorImagePane1 = new StackPane();
        doctorImagePane1.setPrefSize(40, 40);
        doctorImagePane1.getChildren().addAll(
                doctorCircle1,
                doctorInitial1
        );

        Text doctorName1 = new Text("Dr. James Wilson");
        doctorName1.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text doctorId1 = new Text("ID: LL-9021");
        doctorId1.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        VBox doctorInfo1 = new VBox(2);
        doctorInfo1.getChildren().addAll(
                doctorName1,
                doctorId1
        );

        HBox doctorNameBox1 = new HBox(10);
        doctorNameBox1.setPrefWidth(190);
        doctorNameBox1.setAlignment(Pos.CENTER_LEFT);
        doctorNameBox1.getChildren().addAll(
                doctorImagePane1,
                doctorInfo1
        );

        Text department1 = new Text("Cardiology");
        department1.setStyle("-fx-font-size: 12px; -fx-fill: #191b23;");

        HBox departmentBox1 = new HBox(department1);
        departmentBox1.setPrefWidth(115);
        departmentBox1.setAlignment(Pos.CENTER_LEFT);

        Text specialization1 = new Text("Interventional Cardiology");
        specialization1.setStyle("-fx-font-size: 12px; -fx-fill: #434655;");

        HBox specializationBox1 = new HBox(specialization1);
        specializationBox1.setPrefWidth(160);
        specializationBox1.setAlignment(Pos.CENTER_LEFT);

        Label shift1 = new Label("AM");
        shift1.setStyle("-fx-background-color: #e7e7f3; -fx-text-fill: #191b23; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-padding: 5px 9px;");

        HBox shiftBox1 = new HBox(shift1);
        shiftBox1.setPrefWidth(70);
        shiftBox1.setAlignment(Pos.CENTER_LEFT);

        Label availability1 = new Label("●  Available");
        availability1.setStyle("-fx-background-color: #dcfce7; -fx-text-fill: #15803d; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 7px; -fx-padding: 5px 8px;");

        HBox availabilityBox1 = new HBox(availability1);
        availabilityBox1.setPrefWidth(125);
        availabilityBox1.setAlignment(Pos.CENTER_LEFT);

        Text status1 = new Text("On Duty");
        status1.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        HBox statusBox1 = new HBox(status1);
        statusBox1.setPrefWidth(125);
        statusBox1.setAlignment(Pos.CENTER_LEFT);

        Button edit1 = new Button("✎");
        edit1.setPrefSize(32, 32);
        edit1.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 16px;");

        Button delete1 = new Button("⌫");
        delete1.setPrefSize(32, 32);
        delete1.setStyle("-fx-background-color: transparent; -fx-text-fill: #ba1a1a; -fx-font-size: 15px;");

        HBox actions1 = new HBox(3);
        actions1.setPrefWidth(85);
        actions1.setAlignment(Pos.CENTER_RIGHT);
        actions1.getChildren().addAll(
                edit1,
                delete1
        );

        doctorRow1.getChildren().addAll(
                doctorNameBox1,
                departmentBox1,
                specializationBox1,
                shiftBox1,
                availabilityBox1,
                statusBox1,
                actions1
        );

        // Doctor row 2
        HBox doctorRow2 = new HBox(10);
        doctorRow2.setPadding(new Insets(14, 15, 14, 15));
        doctorRow2.setAlignment(Pos.CENTER_LEFT);
        doctorRow2.setStyle("-fx-background-color: #ffffff; -fx-border-color: transparent transparent #c3c6d7 transparent; -fx-border-width: 0px 0px 1px 0px;");

        Circle doctorCircle2 = new Circle(20);
        doctorCircle2.setFill(Color.web("#dbe1ff"));

        Text doctorInitial2 = new Text("ER");
        doctorInitial2.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        StackPane doctorImagePane2 = new StackPane();
        doctorImagePane2.setPrefSize(40, 40);
        doctorImagePane2.getChildren().addAll(
                doctorCircle2,
                doctorInitial2
        );

        Text doctorName2 = new Text("Dr. Elena Rodriguez");
        doctorName2.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text doctorId2 = new Text("ID: LL-4432");
        doctorId2.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        VBox doctorInfo2 = new VBox(2);
        doctorInfo2.getChildren().addAll(
                doctorName2,
                doctorId2
        );

        HBox doctorNameBox2 = new HBox(10);
        doctorNameBox2.setPrefWidth(190);
        doctorNameBox2.setAlignment(Pos.CENTER_LEFT);
        doctorNameBox2.getChildren().addAll(
                doctorImagePane2,
                doctorInfo2
        );

        Text department2 = new Text("Orthopedics");
        department2.setStyle("-fx-font-size: 12px; -fx-fill: #191b23;");

        HBox departmentBox2 = new HBox(department2);
        departmentBox2.setPrefWidth(115);
        departmentBox2.setAlignment(Pos.CENTER_LEFT);

        Text specialization2 = new Text("Spinal Surgery");
        specialization2.setStyle("-fx-font-size: 12px; -fx-fill: #434655;");

        HBox specializationBox2 = new HBox(specialization2);
        specializationBox2.setPrefWidth(160);
        specializationBox2.setAlignment(Pos.CENTER_LEFT);

        Label shift2 = new Label("AM");
        shift2.setStyle("-fx-background-color: #e7e7f3; -fx-text-fill: #191b23; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-padding: 5px 9px;");

        HBox shiftBox2 = new HBox(shift2);
        shiftBox2.setPrefWidth(70);
        shiftBox2.setAlignment(Pos.CENTER_LEFT);

        Label availability2 = new Label("●  In Surgery");
        availability2.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #b91c1c; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 7px; -fx-padding: 5px 8px;");

        HBox availabilityBox2 = new HBox(availability2);
        availabilityBox2.setPrefWidth(125);
        availabilityBox2.setAlignment(Pos.CENTER_LEFT);

        Text status2 = new Text("On Duty");
        status2.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        HBox statusBox2 = new HBox(status2);
        statusBox2.setPrefWidth(125);
        statusBox2.setAlignment(Pos.CENTER_LEFT);

        Button edit2 = new Button("✎");
        edit2.setPrefSize(32, 32);
        edit2.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 16px;");

        Button delete2 = new Button("⌫");
        delete2.setPrefSize(32, 32);
        delete2.setStyle("-fx-background-color: transparent; -fx-text-fill: #ba1a1a; -fx-font-size: 15px;");

        HBox actions2 = new HBox(3);
        actions2.setPrefWidth(85);
        actions2.setAlignment(Pos.CENTER_RIGHT);
        actions2.getChildren().addAll(
                edit2,
                delete2
        );

        doctorRow2.getChildren().addAll(
                doctorNameBox2,
                departmentBox2,
                specializationBox2,
                shiftBox2,
                availabilityBox2,
                statusBox2,
                actions2
        );

        // Doctor row 3
        HBox doctorRow3 = new HBox(10);
        doctorRow3.setPadding(new Insets(14, 15, 14, 15));
        doctorRow3.setAlignment(Pos.CENTER_LEFT);
        doctorRow3.setStyle("-fx-background-color: #ffffff; -fx-border-color: transparent transparent #c3c6d7 transparent; -fx-border-width: 0px 0px 1px 0px;");

        Circle doctorCircle3 = new Circle(20);
        doctorCircle3.setFill(Color.web("#dbe1ff"));

        Text doctorInitial3 = new Text("MC");
        doctorInitial3.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        StackPane doctorImagePane3 = new StackPane();
        doctorImagePane3.setPrefSize(40, 40);
        doctorImagePane3.getChildren().addAll(
                doctorCircle3,
                doctorInitial3
        );

        Text doctorName3 = new Text("Dr. Michael Chen");
        doctorName3.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text doctorId3 = new Text("ID: LL-2188");
        doctorId3.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        VBox doctorInfo3 = new VBox(2);
        doctorInfo3.getChildren().addAll(
                doctorName3,
                doctorId3
        );

        HBox doctorNameBox3 = new HBox(10);
        doctorNameBox3.setPrefWidth(190);
        doctorNameBox3.setAlignment(Pos.CENTER_LEFT);
        doctorNameBox3.getChildren().addAll(
                doctorImagePane3,
                doctorInfo3
        );

        Text department3 = new Text("Pediatrics");
        department3.setStyle("-fx-font-size: 12px; -fx-fill: #191b23;");

        HBox departmentBox3 = new HBox(department3);
        departmentBox3.setPrefWidth(115);
        departmentBox3.setAlignment(Pos.CENTER_LEFT);

        Text specialization3 = new Text("Child Neurology");
        specialization3.setStyle("-fx-font-size: 12px; -fx-fill: #434655;");

        HBox specializationBox3 = new HBox(specialization3);
        specializationBox3.setPrefWidth(160);
        specializationBox3.setAlignment(Pos.CENTER_LEFT);

        Label shift3 = new Label("PM");
        shift3.setStyle("-fx-background-color: #e7e7f3; -fx-text-fill: #191b23; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-padding: 5px 9px;");

        HBox shiftBox3 = new HBox(shift3);
        shiftBox3.setPrefWidth(70);
        shiftBox3.setAlignment(Pos.CENTER_LEFT);

        Label availability3 = new Label("●  On Break");
        availability3.setStyle("-fx-background-color: #fef3c7; -fx-text-fill: #b45309; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 7px; -fx-padding: 5px 8px;");

        HBox availabilityBox3 = new HBox(availability3);
        availabilityBox3.setPrefWidth(125);
        availabilityBox3.setAlignment(Pos.CENTER_LEFT);

        Text status3 = new Text("Shift Start (14:00)");
        status3.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: #737686;");

        HBox statusBox3 = new HBox(status3);
        statusBox3.setPrefWidth(125);
        statusBox3.setAlignment(Pos.CENTER_LEFT);

        Button edit3 = new Button("✎");
        edit3.setPrefSize(32, 32);
        edit3.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 16px;");

        Button delete3 = new Button("⌫");
        delete3.setPrefSize(32, 32);
        delete3.setStyle("-fx-background-color: transparent; -fx-text-fill: #ba1a1a; -fx-font-size: 15px;");

        HBox actions3 = new HBox(3);
        actions3.setPrefWidth(85);
        actions3.setAlignment(Pos.CENTER_RIGHT);
        actions3.getChildren().addAll(
                edit3,
                delete3
        );

        doctorRow3.getChildren().addAll(
                doctorNameBox3,
                departmentBox3,
                specializationBox3,
                shiftBox3,
                availabilityBox3,
                statusBox3,
                actions3
        );

        // Doctor row 4
        HBox doctorRow4 = new HBox(10);
        doctorRow4.setPadding(new Insets(14, 15, 14, 15));
        doctorRow4.setAlignment(Pos.CENTER_LEFT);
        doctorRow4.setStyle("-fx-background-color: #ffffff; -fx-border-color: transparent transparent #c3c6d7 transparent; -fx-border-width: 0px 0px 1px 0px;");

        Circle doctorCircle4 = new Circle(20);
        doctorCircle4.setFill(Color.web("#dbe1ff"));

        Text doctorInitial4 = new Text("ST");
        doctorInitial4.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        StackPane doctorImagePane4 = new StackPane();
        doctorImagePane4.setPrefSize(40, 40);
        doctorImagePane4.getChildren().addAll(
                doctorCircle4,
                doctorInitial4
        );

        Text doctorName4 = new Text("Dr. Sarah Thompson");
        doctorName4.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text doctorId4 = new Text("ID: LL-1055");
        doctorId4.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        VBox doctorInfo4 = new VBox(2);
        doctorInfo4.getChildren().addAll(
                doctorName4,
                doctorId4
        );

        HBox doctorNameBox4 = new HBox(10);
        doctorNameBox4.setPrefWidth(190);
        doctorNameBox4.setAlignment(Pos.CENTER_LEFT);
        doctorNameBox4.getChildren().addAll(
                doctorImagePane4,
                doctorInfo4
        );

        Text department4 = new Text("Oncology");
        department4.setStyle("-fx-font-size: 12px; -fx-fill: #191b23;");

        HBox departmentBox4 = new HBox(department4);
        departmentBox4.setPrefWidth(115);
        departmentBox4.setAlignment(Pos.CENTER_LEFT);

        Text specialization4 = new Text("Radiotherapy");
        specialization4.setStyle("-fx-font-size: 12px; -fx-fill: #434655;");

        HBox specializationBox4 = new HBox(specialization4);
        specializationBox4.setPrefWidth(160);
        specializationBox4.setAlignment(Pos.CENTER_LEFT);

        Label shift4 = new Label("AM");
        shift4.setStyle("-fx-background-color: #e7e7f3; -fx-text-fill: #191b23; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-padding: 5px 9px;");

        HBox shiftBox4 = new HBox(shift4);
        shiftBox4.setPrefWidth(70);
        shiftBox4.setAlignment(Pos.CENTER_LEFT);

        Label availability4 = new Label("●  Available");
        availability4.setStyle("-fx-background-color: #dcfce7; -fx-text-fill: #15803d; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 7px; -fx-padding: 5px 8px;");

        HBox availabilityBox4 = new HBox(availability4);
        availabilityBox4.setPrefWidth(125);
        availabilityBox4.setAlignment(Pos.CENTER_LEFT);

        Text status4 = new Text("On Duty");
        status4.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        HBox statusBox4 = new HBox(status4);
        statusBox4.setPrefWidth(125);
        statusBox4.setAlignment(Pos.CENTER_LEFT);

        Button edit4 = new Button("✎");
        edit4.setPrefSize(32, 32);
        edit4.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 16px;");

        Button delete4 = new Button("⌫");
        delete4.setPrefSize(32, 32);
        delete4.setStyle("-fx-background-color: transparent; -fx-text-fill: #ba1a1a; -fx-font-size: 15px;");

        HBox actions4 = new HBox(3);
        actions4.setPrefWidth(85);
        actions4.setAlignment(Pos.CENTER_RIGHT);
        actions4.getChildren().addAll(
                edit4,
                delete4
        );

        doctorRow4.getChildren().addAll(
                doctorNameBox4,
                departmentBox4,
                specializationBox4,
                shiftBox4,
                availabilityBox4,
                statusBox4,
                actions4
        );

        // Doctor row 5
        HBox doctorRow5 = new HBox(10);
        doctorRow5.setPadding(new Insets(14, 15, 14, 15));
        doctorRow5.setAlignment(Pos.CENTER_LEFT);
        doctorRow5.setStyle("-fx-background-color: #ffffff;");

        Circle doctorCircle5 = new Circle(20);
        doctorCircle5.setFill(Color.web("#dbe1ff"));

        Text doctorInitial5 = new Text("RM");
        doctorInitial5.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        StackPane doctorImagePane5 = new StackPane();
        doctorImagePane5.setPrefSize(40, 40);
        doctorImagePane5.getChildren().addAll(
                doctorCircle5,
                doctorInitial5
        );

        Text doctorName5 = new Text("Dr. Robert Miller");
        doctorName5.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #191b23;");

        Text doctorId5 = new Text("ID: LL-3310");
        doctorId5.setStyle("-fx-font-size: 10px; -fx-fill: #737686;");

        VBox doctorInfo5 = new VBox(2);
        doctorInfo5.getChildren().addAll(
                doctorName5,
                doctorId5
        );

        HBox doctorNameBox5 = new HBox(10);
        doctorNameBox5.setPrefWidth(190);
        doctorNameBox5.setAlignment(Pos.CENTER_LEFT);
        doctorNameBox5.getChildren().addAll(
                doctorImagePane5,
                doctorInfo5
        );

        Text department5 = new Text("Neurology");
        department5.setStyle("-fx-font-size: 12px; -fx-fill: #191b23;");

        HBox departmentBox5 = new HBox(department5);
        departmentBox5.setPrefWidth(115);
        departmentBox5.setAlignment(Pos.CENTER_LEFT);

        Text specialization5 = new Text("Neurosurgery");
        specialization5.setStyle("-fx-font-size: 12px; -fx-fill: #434655;");

        HBox specializationBox5 = new HBox(specialization5);
        specializationBox5.setPrefWidth(160);
        specializationBox5.setAlignment(Pos.CENTER_LEFT);

        Label shift5 = new Label("PM");
        shift5.setStyle("-fx-background-color: #e7e7f3; -fx-text-fill: #191b23; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-padding: 5px 9px;");

        HBox shiftBox5 = new HBox(shift5);
        shiftBox5.setPrefWidth(70);
        shiftBox5.setAlignment(Pos.CENTER_LEFT);

        Label availability5 = new Label("●  In Surgery");
        availability5.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #b91c1c; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 7px; -fx-padding: 5px 8px;");

        HBox availabilityBox5 = new HBox(availability5);
        availabilityBox5.setPrefWidth(125);
        availabilityBox5.setAlignment(Pos.CENTER_LEFT);

        Text status5 = new Text("On Duty");
        status5.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        HBox statusBox5 = new HBox(status5);
        statusBox5.setPrefWidth(125);
        statusBox5.setAlignment(Pos.CENTER_LEFT);

        Button edit5 = new Button("✎");
        edit5.setPrefSize(32, 32);
        edit5.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 16px;");

        Button delete5 = new Button("⌫");
        delete5.setPrefSize(32, 32);
        delete5.setStyle("-fx-background-color: transparent; -fx-text-fill: #ba1a1a; -fx-font-size: 15px;");

        HBox actions5 = new HBox(3);
        actions5.setPrefWidth(85);
        actions5.setAlignment(Pos.CENTER_RIGHT);
        actions5.getChildren().addAll(
                edit5,
                delete5
        );

        doctorRow5.getChildren().addAll(
                doctorNameBox5,
                departmentBox5,
                specializationBox5,
                shiftBox5,
                availabilityBox5,
                statusBox5,
                actions5
        );

        // Add all doctor rows to table
        doctorCard.getChildren().addAll(
                tableHeader,
                doctorRow1,
                doctorRow2,
                doctorRow3,
                doctorRow4,
                doctorRow5
        );

        // Pagination
        Text showingText = new Text("Showing 1-5 of 48 Doctors");
        showingText.setStyle("-fx-font-size: 12px; -fx-fill: #737686;");

        Region paginationSpacer = new Region();
        HBox.setHgrow(paginationSpacer, Priority.ALWAYS);

        Button previousButton = new Button("‹");
        previousButton.setPrefSize(38, 35);
        previousButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-font-size: 17px;");

        Button pageOne = new Button("1");
        pageOne.setPrefSize(38, 35);
        pageOne.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px;");

        Button pageTwo = new Button("2");
        pageTwo.setPrefSize(38, 35);
        pageTwo.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-background-radius: 8px; -fx-border-radius: 8px;");

        Button pageThree = new Button("3");
        pageThree.setPrefSize(38, 35);
        pageThree.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-background-radius: 8px; -fx-border-radius: 8px;");

        Text dots = new Text("...");
        dots.setStyle("-fx-font-size: 13px; -fx-fill: #737686;");

        Button pageTen = new Button("10");
        pageTen.setPrefSize(38, 35);
        pageTen.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-background-radius: 8px; -fx-border-radius: 8px;");

        Button nextButton = new Button("›");
        nextButton.setPrefSize(38, 35);
        nextButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-font-size: 17px;");

        HBox paginationButtons = new HBox(8);
        paginationButtons.setAlignment(Pos.CENTER_RIGHT);
        paginationButtons.getChildren().addAll(
                previousButton,
                pageOne,
                pageTwo,
                pageThree,
                dots,
                pageTen,
                nextButton
        );

        HBox pagination = new HBox(10);
        pagination.setPadding(new Insets(12, 0, 0, 0));
        pagination.setAlignment(Pos.CENTER_LEFT);
        pagination.getChildren().addAll(
                showingText,
                paginationSpacer,
                paginationButtons
        );

        // Add complete content to main container
        mainContent.getChildren().addAll(
                header,
                statsRow,
                filterBox,
                doctorCard,
                pagination
        );

        // Scroll page vertically
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
