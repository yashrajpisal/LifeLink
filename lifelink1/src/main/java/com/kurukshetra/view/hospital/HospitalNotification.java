
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
import javafx.scene.shape.Circle;

public class HospitalNotification {

    public VBox getNotification() {

        // ========================= MAIN PAGE =========================

        VBox mainVBox = new VBox();
        mainVBox.setStyle("-fx-background-color: #ffffff;");
        mainVBox.setPrefWidth(1100);
        mainVBox.setPrefHeight(750);

        // ========================= TOP BAR =========================

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(14, 24, 14, 24));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);
        topBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search notifications...");
        searchField.setPrefWidth(320);
        searchField.setPrefHeight(36);
        searchField.setStyle("-fx-background-color: #f3f3fe; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-border-color: transparent; -fx-padding: 8px 15px; -fx-font-size: 13px;");

        HBox.setHgrow(searchField, Priority.ALWAYS);

        HBox topRight = new HBox();
        topRight.setSpacing(12);
        topRight.setAlignment(Pos.CENTER_RIGHT);

        Button notificationButton = new Button("🔔");
        notificationButton.setPrefSize(38, 38);
        notificationButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-background-radius: 50%;");

        Button settingsButton = new Button("⚙");
        settingsButton.setPrefSize(38, 38);
        settingsButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-background-radius: 50%;");

        Label adminView = new Label("Admin View");
        adminView.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        topRight.getChildren().add(notificationButton);
        topRight.getChildren().add(settingsButton);
        topRight.getChildren().add(adminView);

        topBar.getChildren().add(searchField);
        topBar.getChildren().add(topRight);

        // ========================= PAGE CONTENT =========================

        VBox pageContent = new VBox();
        pageContent.setPadding(new Insets(24));
        pageContent.setSpacing(18);
        pageContent.setStyle("-fx-background-color: #ffffff;");

        VBox.setVgrow(pageContent, Priority.ALWAYS);

        // ========================= HEADER =========================

        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setSpacing(20);

        VBox titleBox = new VBox();
        titleBox.setSpacing(4);

        Label titleLabel = new Label("Notifications");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label subtitleLabel = new Label("You have 4 unread alerts requiring attention.");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

        titleBox.getChildren().add(titleLabel);
        titleBox.getChildren().add(subtitleLabel);

        HBox.setHgrow(titleBox, Priority.ALWAYS);

        HBox headerButtons = new HBox();
        headerButtons.setSpacing(8);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);

        Button markReadButton = new Button("✓  Mark as Read");
        markReadButton.setPrefHeight(38);
        markReadButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #505f76; -fx-font-size: 13px; -fx-padding: 8px 14px;");

        Button clearAllButton = new Button("⌫  Clear All");
        clearAllButton.setPrefHeight(38);
        clearAllButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #ba1a1a; -fx-font-size: 13px; -fx-padding: 8px 14px;");

        headerButtons.getChildren().add(markReadButton);
        headerButtons.getChildren().add(clearAllButton);

        headerBox.getChildren().add(titleBox);
        headerBox.getChildren().add(headerButtons);

        // ========================= TAB BAR =========================

        HBox tabBar = new HBox();
        tabBar.setSpacing(5);
        tabBar.setAlignment(Pos.CENTER_LEFT);
        tabBar.setStyle("-fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

        Button allButton = new Button("All");
        allButton.setPrefHeight(40);
        allButton.setStyle("-fx-background-color: transparent; -fx-border-color: #004ac6; -fx-border-width: 0px 0px 2px 0px; -fx-text-fill: #004ac6; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 8px 20px;");

        Button unreadButton = new Button("Unread");
        unreadButton.setPrefHeight(40);
        unreadButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #434655; -fx-font-size: 14px; -fx-padding: 8px 20px;");

        Button emergencyButton = new Button("Emergency  2");
        emergencyButton.setPrefHeight(40);
        emergencyButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #434655; -fx-font-size: 14px; -fx-padding: 8px 20px;");

        tabBar.getChildren().add(allButton);
        tabBar.getChildren().add(unreadButton);
        tabBar.getChildren().add(emergencyButton);

        // ========================= NOTIFICATION LIST =========================

        VBox notificationList = new VBox();
        notificationList.setSpacing(12);
        notificationList.setPadding(new Insets(2, 5, 2, 2));

        // ========================= NOTIFICATION 1 =========================

        HBox notification1 = new HBox();
        notification1.setSpacing(14);
        notification1.setPadding(new Insets(16));
        notification1.setAlignment(Pos.TOP_LEFT);
        notification1.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        VBox emergencyIconBox = new VBox();
        emergencyIconBox.setPrefSize(48, 48);
        emergencyIconBox.setMinSize(48, 48);
        emergencyIconBox.setMaxSize(48, 48);
        emergencyIconBox.setAlignment(Pos.CENTER);
        emergencyIconBox.setStyle("-fx-background-color: #ffdad6; -fx-background-radius: 50%;");

        Label emergencyIcon = new Label("🚨");
        emergencyIcon.setStyle("-fx-font-size: 22px;");

        emergencyIconBox.getChildren().add(emergencyIcon);

        VBox emergencyContent = new VBox();
        emergencyContent.setSpacing(5);

        HBox.setHgrow(emergencyContent, Priority.ALWAYS);

        HBox emergencyTop = new HBox();
        emergencyTop.setAlignment(Pos.CENTER_LEFT);

        Label emergencyType = new Label("URGENT • CRITICAL");
        emergencyType.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #ba1a1a;");

        HBox.setHgrow(emergencyType, Priority.ALWAYS);

        Label emergencyTime = new Label("2 mins ago");
        emergencyTime.setStyle("-fx-font-size: 11px; -fx-text-fill: #737686;");

        emergencyTop.getChildren().add(emergencyType);
        emergencyTop.getChildren().add(emergencyTime);

        Label emergencyTitle = new Label("New Cardiac Arrest case reported at Sector 4");
        emergencyTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");
        emergencyTitle.setWrapText(true);

        Label emergencyDescription = new Label(
                "Rapid response team Alpha-1 has been dispatched. Medical bay 402 is being prepped. Immediate surgeon presence required."
        );
        emergencyDescription.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");
        emergencyDescription.setWrapText(true);

        HBox emergencyActions = new HBox();
        emergencyActions.setSpacing(8);
        emergencyActions.setPadding(new Insets(8, 0, 0, 0));

        Button assignTeamButton = new Button("Assign Team");
        assignTeamButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-background-radius: 8px; -fx-font-size: 12px; -fx-padding: 7px 14px;");

        Button viewRecordButton = new Button("View Record");
        viewRecordButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: #434655; -fx-font-size: 12px; -fx-padding: 7px 14px;");

        emergencyActions.getChildren().add(assignTeamButton);
        emergencyActions.getChildren().add(viewRecordButton);

        emergencyContent.getChildren().add(emergencyTop);
        emergencyContent.getChildren().add(emergencyTitle);
        emergencyContent.getChildren().add(emergencyDescription);
        emergencyContent.getChildren().add(emergencyActions);

        notification1.getChildren().add(emergencyIconBox);
        notification1.getChildren().add(emergencyContent);

        // ========================= NOTIFICATION 2 =========================

        HBox notification2 = new HBox();
        notification2.setSpacing(14);
        notification2.setPadding(new Insets(16));
        notification2.setAlignment(Pos.TOP_LEFT);
        notification2.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        VBox icuIconBox = new VBox();
        icuIconBox.setPrefSize(48, 48);
        icuIconBox.setMinSize(48, 48);
        icuIconBox.setMaxSize(48, 48);
        icuIconBox.setAlignment(Pos.CENTER);
        icuIconBox.setStyle("-fx-background-color: #ffdbcd; -fx-background-radius: 50%;");

        Label icuIcon = new Label("⚠");
        icuIcon.setStyle("-fx-font-size: 22px; -fx-text-fill: #943700;");

        icuIconBox.getChildren().add(icuIcon);

        VBox icuContent = new VBox();
        icuContent.setSpacing(5);

        HBox.setHgrow(icuContent, Priority.ALWAYS);

        HBox icuTop = new HBox();
        icuTop.setAlignment(Pos.CENTER_LEFT);

        Label icuType = new Label("FACILITY STATUS");
        icuType.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #943700;");

        HBox.setHgrow(icuType, Priority.ALWAYS);

        Label icuTime = new Label("15 mins ago");
        icuTime.setStyle("-fx-font-size: 11px; -fx-text-fill: #737686;");

        icuTop.getChildren().add(icuType);
        icuTop.getChildren().add(icuTime);

        Label icuTitle = new Label("ICU Capacity reached 100%");
        icuTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label icuDescription = new Label(
                "All 24 beds in the Intensive Care Unit are currently occupied. Incoming critical patients must be redirected to neighboring facilities or ER overflow."
        );
        icuDescription.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");
        icuDescription.setWrapText(true);

        icuContent.getChildren().addAll(
            icuTop,
            icuTitle,
            icuDescription
        );

        notification2.getChildren().addAll(
            icuIconBox,
            icuContent
        );

        // ========================= NOTIFICATION 3 =========================

        HBox notification3 = new HBox();
        notification3.setSpacing(14);
        notification3.setPadding(new Insets(16));
        notification3.setAlignment(Pos.TOP_LEFT);
        notification3.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        VBox ambulanceIconBox = new VBox();
        ambulanceIconBox.setPrefSize(48, 48);
        ambulanceIconBox.setMinSize(48, 48);
        ambulanceIconBox.setMaxSize(48, 48);
        ambulanceIconBox.setAlignment(Pos.CENTER);
        ambulanceIconBox.setStyle("-fx-background-color: #d0e1fb; -fx-background-radius: 50%;");

        Label ambulanceIcon = new Label("🚑");
        ambulanceIcon.setStyle("-fx-font-size: 22px;");

        ambulanceIconBox.getChildren().add(ambulanceIcon);

        VBox ambulanceContent = new VBox();
        ambulanceContent.setSpacing(5);

        HBox.setHgrow(ambulanceContent, Priority.ALWAYS);

        HBox ambulanceTop = new HBox();
        ambulanceTop.setAlignment(Pos.CENTER_LEFT);

        Label ambulanceType = new Label("LOGISTICS");
        ambulanceType.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #004ac6;");

        HBox.setHgrow(ambulanceType, Priority.ALWAYS);

        Label ambulanceTime = new Label("45 mins ago");
        ambulanceTime.setStyle("-fx-font-size: 11px; -fx-text-fill: #737686;");

        ambulanceTop.getChildren().addAll(
            ambulanceType,
            ambulanceTime
        );
        

        Label ambulanceTitle = new Label("Ambulance #A12 has arrived at ER Bay 1");
        ambulanceTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label ambulanceDescription = new Label(
                "Patient: Unknown Male (approx. 45y), Trauma-2 status. Triage team is receiving the patient now."
        );
        ambulanceDescription.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");
        ambulanceDescription.setWrapText(true);

        HBox ambulanceBottom = new HBox();
        ambulanceBottom.setAlignment(Pos.CENTER_RIGHT);

        Circle unreadCircle = new Circle(5);
        unreadCircle.setFill(javafx.scene.paint.Color.web("#004ac6"));

        ambulanceBottom.getChildren().add(unreadCircle);

        ambulanceContent.getChildren().addAll(
            ambulanceTop,
            ambulanceTitle,
            ambulanceDescription
        );

        notification3.getChildren().addAll(
            ambulanceIconBox,
            ambulanceContent,
            ambulanceBottom
        );

        // ========================= NOTIFICATION 4 =========================

        HBox notification4 = new HBox();
        notification4.setSpacing(14);
        notification4.setPadding(new Insets(16));
        notification4.setAlignment(Pos.TOP_LEFT);
        notification4.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        VBox resourceIconBox = new VBox();
        resourceIconBox.setPrefSize(48, 48);
        resourceIconBox.setMinSize(48, 48);
        resourceIconBox.setMaxSize(48, 48);
        resourceIconBox.setAlignment(Pos.CENTER);
        resourceIconBox.setStyle("-fx-background-color: #e1e2ed; -fx-background-radius: 50%;");

        Label resourceIcon = new Label("📦");
        resourceIcon.setStyle("-fx-font-size: 21px;");

        resourceIconBox.getChildren().add(resourceIcon);

        VBox resourceContent = new VBox();
        resourceContent.setSpacing(5);

        HBox.setHgrow(resourceContent, Priority.ALWAYS);

        HBox resourceTop = new HBox();
        resourceTop.setAlignment(Pos.CENTER_LEFT);

        Label resourceType = new Label("INVENTORY");
        resourceType.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        HBox.setHgrow(resourceType, Priority.ALWAYS);

        Label resourceTime = new Label("1 hour ago");
        resourceTime.setStyle("-fx-font-size: 11px; -fx-text-fill: #737686;");

        resourceTop.getChildren().addAll(
            resourceType,
            resourceTime
        );

        Label resourceTitle = new Label("Resource Low: Surgical Gloves (Size 7.5)");
        resourceTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label resourceDescription = new Label(
                "Stock level dropped below threshold (15 boxes remaining). Automated reorder has been triggered."
        );
        resourceDescription.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");
        resourceDescription.setWrapText(true);

        resourceContent.getChildren().addAll(
            resourceTop,
            resourceTitle,
            resourceDescription
        );

        notification4.getChildren().addAll(
            resourceIconBox,
            resourceContent
        );

        // ========================= NOTIFICATION 5 =========================

        HBox notification5 = new HBox();
        notification5.setSpacing(14);
        notification5.setPadding(new Insets(16));
        notification5.setAlignment(Pos.TOP_LEFT);
        notification5.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        VBox doctorIconBox = new VBox();
        doctorIconBox.setPrefSize(48, 48);
        doctorIconBox.setMinSize(48, 48);
        doctorIconBox.setMaxSize(48, 48);
        doctorIconBox.setAlignment(Pos.CENTER);
        doctorIconBox.setStyle("-fx-background-color: #ededf9; -fx-background-radius: 50%;");

        Label doctorIcon = new Label("👨‍⚕");
        doctorIcon.setStyle("-fx-font-size: 21px;");

        doctorIconBox.getChildren().add(doctorIcon);

        VBox doctorContent = new VBox();
        doctorContent.setSpacing(5);

        HBox.setHgrow(doctorContent, Priority.ALWAYS);

        HBox doctorTop = new HBox();
        doctorTop.setAlignment(Pos.CENTER_LEFT);

        Label doctorType = new Label("STAFFING");
        doctorType.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        HBox.setHgrow(doctorType, Priority.ALWAYS);

        Label doctorTime = new Label("2 hours ago");
        doctorTime.setStyle("-fx-font-size: 11px; -fx-text-fill: #737686;");

        doctorTop.getChildren().addAll(
            doctorType,
            doctorTime
        );

        Label doctorTitle = new Label("Doctor Assigned: Dr. Sarah Chen");
        doctorTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label doctorDescription = new Label(
                "Assigned to Post-Op Care Unit (Section B) for the afternoon shift. All patient charts have been synced to her tablet."
        );
        doctorDescription.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");
        doctorDescription.setWrapText(true);

        doctorContent.getChildren().addAll(
            doctorTop,
            doctorTitle,
            doctorDescription
        );

        notification5.getChildren().addAll(
            doctorIconBox,
            doctorContent
        );

        // ========================= ADD NOTIFICATIONS =========================

        notificationList.getChildren().addAll(
            notification1,
            notification2,
            notification3,
            notification4,
            notification5
        );

        // ========================= SCROLL PANE =========================

        ScrollPane notificationScroll = new ScrollPane();
        notificationScroll.setContent(notificationList);
        notificationScroll.setFitToWidth(true);
        notificationScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        notificationScroll.setStyle("-fx-background:#ffffff; -fx-background-color:#ffffff;");

        VBox.setVgrow(notificationScroll, Priority.ALWAYS);

        // ========================= FOOTER =========================

        HBox footer = new HBox();
        footer.setPadding(new Insets(12, 5, 5, 5));
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setStyle("-fx-border-color: #c3c6d7; -fx-border-width: 1px 0px 0px 0px;");

        Label showingLabel = new Label("Displaying 5 of 124 notifications");
        showingLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #737686;");

        HBox.setHgrow(showingLabel, Priority.ALWAYS);

        HBox pagination = new HBox();
        pagination.setSpacing(15);
        pagination.setAlignment(Pos.CENTER_RIGHT);

        Button previousButton = new Button("Previous");
        previousButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #737686; -fx-font-size: 12px;");

        Label page1 = new Label("1");
        page1.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label page2 = new Label("2");
        page2.setStyle("-fx-font-size: 13px; -fx-text-fill: #737686;");

        Label page3 = new Label("3");
        page3.setStyle("-fx-font-size: 13px; -fx-text-fill: #737686;");

        Label dots = new Label("...");
        dots.setStyle("-fx-font-size: 13px; -fx-text-fill: #737686;");

        Label page12 = new Label("12");
        page12.setStyle("-fx-font-size: 13px; -fx-text-fill: #737686;");

        Button nextButton = new Button("Next");
        nextButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #737686; -fx-font-size: 12px;");

        pagination.getChildren().addAll(
            previousButton,
            page1,
            page2,
            page3,
            dots,
            page12,
            nextButton
        );

        footer.getChildren().addAll(
            showingLabel,
            pagination
        );

        // ========================= ADD TO PAGE CONTENT =========================

        pageContent.getChildren().addAll(
            headerBox,
            tabBar,
            notificationScroll,
            footer
        );

        // ========================= FINAL PAGE =========================

        mainVBox.getChildren().addAll(
            topBar,
            pageContent
        );

        VBox.setVgrow(pageContent, Priority.ALWAYS);

        ScrollPane scrollPane = new ScrollPane(mainVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background:#ffffff;-fx-background-color:#ffffff;");

        VBox notificationPage = new VBox(scrollPane);
        // notificationPage.getChildren().add(scrollPane);

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return notificationPage;
    }
}
