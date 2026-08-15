
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class AdminUserManagement {

    public VBox getUserManagement() {

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(25));
        mainContent.setStyle("-fx-background-color: #f8f8ff;");

        Text heading = new Text("User Management");
        heading.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text subHeading = new Text("Manage administrative, medical, and emergency responders access.");
        subHeading.setStyle("-fx-font-size: 14px; -fx-fill: #6b7280;");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(
                heading,
                subHeading
        );

        Button addUserButton = new Button("+   Add User");
        addUserButton.setPrefWidth(130);
        addUserButton.setPrefHeight(42);
        addUserButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 9px;");

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox header = new HBox(
                headingBox,
                headerSpacer,
                addUserButton
        );
        header.setAlignment(Pos.CENTER_LEFT);

        HBox filterBox = new HBox(12);
        filterBox.setPadding(new Insets(15));
        filterBox.setAlignment(Pos.CENTER_LEFT);
        filterBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12px; -fx-border-color: #e5e7eb; -fx-border-radius: 12px;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search by name, ID or phone...");
        searchField.setPrefHeight(40);
        searchField.setPrefWidth(420);
        searchField.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 9px; -fx-background-radius: 9px; -fx-font-size: 13px;");

        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll(
                "All Roles",
                "Doctor",
                "Ambulance Driver",
                "Police Officer",
                "Administrator"
        );
        roleCombo.setValue("All Roles");
        roleCombo.setPrefHeight(40);
        roleCombo.setPrefWidth(170);
        roleCombo.setStyle("-fx-border-color: #c3c6d7; -fx-background-radius: 9px; -fx-border-radius: 9px; -fx-font-size: 12px;");

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll(
                "All Status",
                "Active",
                "Suspended",
                "Pending"
        );
        statusCombo.setValue("All Status");
        statusCombo.setPrefHeight(40);
        statusCombo.setPrefWidth(160);
        statusCombo.setStyle("-fx-border-color: #c3c6d7; -fx-background-radius: 9px; -fx-border-radius: 9px; -fx-font-size: 12px;");

        filterBox.getChildren().addAll(
                searchField,
                roleCombo,
                statusCombo
        );

        VBox userCard = new VBox();
        userCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e5e7eb; -fx-border-radius: 15px;");

        Text tableTitle = new Text("Registered Users");
        tableTitle.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text tableInfo = new Text("Manage system users and their access status");
        tableInfo.setStyle("-fx-font-size: 11px; -fx-fill: #737686;");

        VBox tableTitleBox = new VBox(4);
        tableTitleBox.getChildren().addAll(
                tableTitle,
                tableInfo
        );

        Region titleSpacer = new Region();
        HBox.setHgrow(titleSpacer, Priority.ALWAYS);

        Button filterButton = new Button("☷");
        filterButton.setPrefSize(38, 35);
        filterButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #374151; -fx-font-size: 16px;");

        Button moreButton = new Button("⋮");
        moreButton.setPrefSize(38, 35);
        moreButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #374151; -fx-font-size: 18px;");

        HBox tableActions = new HBox(3);
        tableActions.setAlignment(Pos.CENTER_RIGHT);
        tableActions.getChildren().addAll(
                filterButton,
                moreButton
        );

        HBox tableHeader = new HBox(
                tableTitleBox,
                titleSpacer,
                tableActions
        );
        tableHeader.setPadding(new Insets(16));
        tableHeader.setAlignment(Pos.CENTER_LEFT);

        HBox columnHeader = new HBox(10);
        columnHeader.setPadding(new Insets(12, 15, 12, 15));
        columnHeader.setAlignment(Pos.CENTER_LEFT);
        columnHeader.setStyle("-fx-background-color: #e9e9f5;");

        Label idHeader = new Label("USER ID");
        idHeader.setPrefWidth(100);
        idHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");

        Label nameHeader = new Label("NAME");
        nameHeader.setPrefWidth(210);
        nameHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");

        Label roleHeader = new Label("ROLE");
        roleHeader.setPrefWidth(135);
        roleHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");

        Label phoneHeader = new Label("PHONE");
        phoneHeader.setPrefWidth(150);
        phoneHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");

        Label statusHeader = new Label("STATUS");
        statusHeader.setPrefWidth(110);
        statusHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");

        Label actionHeader = new Label("ACTIONS");
        actionHeader.setPrefWidth(130);
        actionHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #6b7280;");

        columnHeader.getChildren().addAll(
                idHeader,
                nameHeader,
                roleHeader,
                phoneHeader,
                statusHeader,
                actionHeader
        );

        HBox userRow1 = new HBox(10);
        userRow1.setPadding(new Insets(14, 15, 14, 15));
        userRow1.setAlignment(Pos.CENTER_LEFT);
        userRow1.setStyle("-fx-border-color: transparent transparent #e5e7eb transparent; -fx-border-width: 0px 0px 1px 0px;");

        Text userId1 = new Text("LL-4902");
        userId1.setStyle("-fx-font-size: 11px; -fx-fill: #6b7280;");
        HBox idBox1 = new HBox(userId1);
        idBox1.setPrefWidth(100);
        idBox1.setAlignment(Pos.CENTER_LEFT);

        Circle userCircle1 = new Circle(18);
        userCircle1.setFill(Color.web("#dbe1ff"));

        Text userInitial1 = new Text("S");
        userInitial1.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #004ac6;");

        javafx.scene.layout.StackPane userIcon1 = new javafx.scene.layout.StackPane();
        userIcon1.setPrefSize(36, 36);
        userIcon1.getChildren().addAll(
                userCircle1,
                userInitial1
        );

        Text userName1 = new Text("Dr. Sarah Jenkins");
        userName1.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #374151;");

        HBox nameBox1 = new HBox(10);
        nameBox1.setPrefWidth(210);
        nameBox1.setAlignment(Pos.CENTER_LEFT);
        nameBox1.getChildren().addAll(
                userIcon1,
                userName1
        );

        Label role1 = new Label("DOCTOR");
        role1.setStyle("-fx-background-color: #dbe1ff; -fx-text-fill: #003ea8; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-padding: 5px 10px;");

        HBox roleBox1 = new HBox(role1);
        roleBox1.setPrefWidth(135);
        roleBox1.setAlignment(Pos.CENTER_LEFT);

        Text phone1 = new Text("+1 (555) 012-9934");
        phone1.setStyle("-fx-font-size: 12px; -fx-fill: #6b7280;");

        HBox phoneBox1 = new HBox(phone1);
        phoneBox1.setPrefWidth(150);
        phoneBox1.setAlignment(Pos.CENTER_LEFT);

        Label status1 = new Label("●  ACTIVE");
        status1.setStyle("-fx-text-fill: #16a34a; -fx-font-size: 10px; -fx-font-weight: bold;");

        HBox statusBox1 = new HBox(status1);
        statusBox1.setPrefWidth(110);
        statusBox1.setAlignment(Pos.CENTER_LEFT);

        Button profileButton1 = new Button("Profile");
        profileButton1.setStyle("-fx-background-color: transparent; -fx-text-fill: #1455c0; -fx-font-size: 11px; -fx-font-weight: bold;");

        Button editButton1 = new Button("✎");
        editButton1.setPrefSize(30, 30);
        editButton1.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #374151; -fx-background-radius: 7px;");

        Button deleteButton1 = new Button("×");
        deleteButton1.setPrefSize(30, 30);
        deleteButton1.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-font-size: 16px; -fx-background-radius: 7px;");

        HBox actionBox1 = new HBox(5);
        actionBox1.setPrefWidth(130);
        actionBox1.setAlignment(Pos.CENTER_LEFT);
        actionBox1.getChildren().addAll(
                profileButton1,
                editButton1,
                deleteButton1
        );

        userRow1.getChildren().addAll(
                idBox1,
                nameBox1,
                roleBox1,
                phoneBox1,
                statusBox1,
                actionBox1
        );

        HBox userRow2 = new HBox(10);
        userRow2.setPadding(new Insets(14, 15, 14, 15));
        userRow2.setAlignment(Pos.CENTER_LEFT);
        userRow2.setStyle("-fx-border-color: transparent transparent #e5e7eb transparent; -fx-border-width: 0px 0px 1px 0px;");

        Text userId2 = new Text("LL-1185");
        userId2.setStyle("-fx-font-size: 11px; -fx-fill: #6b7280;");
        HBox idBox2 = new HBox(userId2);
        idBox2.setPrefWidth(100);
        idBox2.setAlignment(Pos.CENTER_LEFT);

        Circle userCircle2 = new Circle(18);
        userCircle2.setFill(Color.web("#d0e1fb"));

        Text userInitial2 = new Text("M");
        userInitial2.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #1455c0;");

        javafx.scene.layout.StackPane userIcon2 = new javafx.scene.layout.StackPane();
        userIcon2.setPrefSize(36, 36);
        userIcon2.getChildren().addAll(
                userCircle2,
                userInitial2
        );

        Text userName2 = new Text("Mark Thompson");
        userName2.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #374151;");

        HBox nameBox2 = new HBox(10);
        nameBox2.setPrefWidth(210);
        nameBox2.setAlignment(Pos.CENTER_LEFT);
        nameBox2.getChildren().addAll(
                userIcon2,
                userName2
        );

        Label role2 = new Label("AMBULANCE");
        role2.setStyle("-fx-background-color: #d0e1fb; -fx-text-fill: #38485d; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-padding: 5px 10px;");

        HBox roleBox2 = new HBox(role2);
        roleBox2.setPrefWidth(135);
        roleBox2.setAlignment(Pos.CENTER_LEFT);

        Text phone2 = new Text("+1 (555) 012-3321");
        phone2.setStyle("-fx-font-size: 12px; -fx-fill: #6b7280;");

        HBox phoneBox2 = new HBox(phone2);
        phoneBox2.setPrefWidth(150);
        phoneBox2.setAlignment(Pos.CENTER_LEFT);

        Label status2 = new Label("●  SUSPENDED");
        status2.setStyle("-fx-text-fill: #dc2626; -fx-font-size: 10px; -fx-font-weight: bold;");

        HBox statusBox2 = new HBox(status2);
        statusBox2.setPrefWidth(110);
        statusBox2.setAlignment(Pos.CENTER_LEFT);

        Button profileButton2 = new Button("Profile");
        profileButton2.setStyle("-fx-background-color: transparent; -fx-text-fill: #1455c0; -fx-font-size: 11px; -fx-font-weight: bold;");

        Button editButton2 = new Button("✎");
        editButton2.setPrefSize(30, 30);
        editButton2.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #374151; -fx-background-radius: 7px;");

        Button deleteButton2 = new Button("×");
        deleteButton2.setPrefSize(30, 30);
        deleteButton2.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-font-size: 16px; -fx-background-radius: 7px;");

        HBox actionBox2 = new HBox(5);
        actionBox2.setPrefWidth(130);
        actionBox2.setAlignment(Pos.CENTER_LEFT);
        actionBox2.getChildren().addAll(
                profileButton2,
                editButton2,
                deleteButton2
        );

        userRow2.getChildren().addAll(
                idBox2,
                nameBox2,
                roleBox2,
                phoneBox2,
                statusBox2,
                actionBox2
        );

        HBox userRow3 = new HBox(10);
        userRow3.setPadding(new Insets(14, 15, 14, 15));
        userRow3.setAlignment(Pos.CENTER_LEFT);
        userRow3.setStyle("-fx-border-color: transparent transparent #e5e7eb transparent; -fx-border-width: 0px 0px 1px 0px;");

        Text userId3 = new Text("LL-0032");
        userId3.setStyle("-fx-font-size: 11px; -fx-fill: #6b7280;");
        HBox idBox3 = new HBox(userId3);
        idBox3.setPrefWidth(100);
        idBox3.setAlignment(Pos.CENTER_LEFT);

        Circle userCircle3 = new Circle(18);
        userCircle3.setFill(Color.web("#dfe3e7"));

        Text userInitial3 = new Text("J");
        userInitial3.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #515659;");

        javafx.scene.layout.StackPane userIcon3 = new javafx.scene.layout.StackPane();
        userIcon3.setPrefSize(36, 36);
        userIcon3.getChildren().addAll(
                userCircle3,
                userInitial3
        );

        Text userName3 = new Text("Officer Jane Doe");
        userName3.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: #374151;");

        HBox nameBox3 = new HBox(10);
        nameBox3.setPrefWidth(210);
        nameBox3.setAlignment(Pos.CENTER_LEFT);
        nameBox3.getChildren().addAll(
                userIcon3,
                userName3
        );

        Label role3 = new Label("POLICE");
        role3.setStyle("-fx-background-color: #dfe3e7; -fx-text-fill: #43474b; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-padding: 5px 10px;");

        HBox roleBox3 = new HBox(role3);
        roleBox3.setPrefWidth(135);
        roleBox3.setAlignment(Pos.CENTER_LEFT);

        Text phone3 = new Text("+1 (555) 012-7755");
        phone3.setStyle("-fx-font-size: 12px; -fx-fill: #6b7280;");

        HBox phoneBox3 = new HBox(phone3);
        phoneBox3.setPrefWidth(150);
        phoneBox3.setAlignment(Pos.CENTER_LEFT);

        Label status3 = new Label("●  ACTIVE");
        status3.setStyle("-fx-text-fill: #16a34a; -fx-font-size: 10px; -fx-font-weight: bold;");

        HBox statusBox3 = new HBox(status3);
        statusBox3.setPrefWidth(110);
        statusBox3.setAlignment(Pos.CENTER_LEFT);

        Button profileButton3 = new Button("Profile");
        profileButton3.setStyle("-fx-background-color: transparent; -fx-text-fill: #1455c0; -fx-font-size: 11px; -fx-font-weight: bold;");

        Button editButton3 = new Button("✎");
        editButton3.setPrefSize(30, 30);
        editButton3.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #374151; -fx-background-radius: 7px;");

        Button deleteButton3 = new Button("×");
        deleteButton3.setPrefSize(30, 30);
        deleteButton3.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-font-size: 16px; -fx-background-radius: 7px;");

        HBox actionBox3 = new HBox(5);
        actionBox3.setPrefWidth(130);
        actionBox3.setAlignment(Pos.CENTER_LEFT);
        actionBox3.getChildren().addAll(
                profileButton3,
                editButton3,
                deleteButton3
        );

        userRow3.getChildren().addAll(
                idBox3,
                nameBox3,
                roleBox3,
                phoneBox3,
                statusBox3,
                actionBox3
        );

        userCard.getChildren().addAll(
                tableHeader,
                columnHeader,
                userRow1,
                userRow2,
                userRow3
        );

        Text showingText = new Text("Showing 1-10 of 124 users");
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

        Button nextButton = new Button("›");
        nextButton.setPrefSize(38, 35);
        nextButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-font-size: 17px;");

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
                filterBox,
                userCard,
                pagination
        );

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        VBox finalContent = new VBox(scrollPane);
        finalContent.setStyle("-fx-background-color: #f8f8ff;");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return finalContent;
    }
}