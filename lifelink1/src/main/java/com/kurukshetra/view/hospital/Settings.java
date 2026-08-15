
package com.kurukshetra.view.hospital;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Settings {

    public VBox getSettings() {

        // ========================= MAIN PAGE =========================

        VBox mainVBox = new VBox();
        mainVBox.setStyle("-fx-background-color: #faf8ff;");
        mainVBox.setPrefWidth(1100);
        mainVBox.setPrefHeight(750);

        // ========================= TOP BAR =========================

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(14, 24, 14, 24));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);
        topBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search settings...");
        searchField.setPrefWidth(320);
        searchField.setPrefHeight(36);
        searchField.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: transparent; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-padding: 8px 15px; -fx-font-size: 13px;");

        HBox.setHgrow(searchField, Priority.ALWAYS);

        HBox topRight = new HBox();
        topRight.setSpacing(12);
        topRight.setAlignment(Pos.CENTER_RIGHT);

        Button notificationButton = new Button("🔔");
        notificationButton.setPrefSize(38, 38);
        notificationButton.setStyle("-fx-background-color: transparent; -fx-font-size: 18px; -fx-background-radius: 50%;");

        Button settingsButton = new Button("⚙");
        settingsButton.setPrefSize(38, 38);
        settingsButton.setStyle("-fx-background-color: #e7e7f3; -fx-font-size: 18px; -fx-background-radius: 50%;");

        Label adminView = new Label("Admin View");
        adminView.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        topRight.getChildren().addAll(
                notificationButton,
                settingsButton,
                adminView
        );

        topBar.getChildren().addAll(
                searchField,
                topRight
        );

        // ========================= PAGE CONTENT =========================

        VBox pageContent = new VBox();
        pageContent.setPadding(new Insets(28));
        pageContent.setSpacing(22);
        pageContent.setStyle("-fx-background-color: #faf8ff;");

        // ========================= PAGE HEADER =========================

        VBox pageHeader = new VBox();
        pageHeader.setSpacing(5);

        Label title = new Label("Settings");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label subtitle = new Label("Manage hospital preferences, notifications, security and system configuration.");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

        pageHeader.getChildren().addAll(
                title,
                subtitle
        );

        // ========================= HOSPITAL INFORMATION =========================

        VBox hospitalSection = new VBox();
        hospitalSection.setSpacing(15);
        hospitalSection.setPadding(new Insets(20));
        hospitalSection.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label hospitalTitle = new Label("Hospital Information");
        hospitalTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label hospitalDescription = new Label("Update basic information about your hospital.");
        hospitalDescription.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

        VBox hospitalNameBox = new VBox();
        hospitalNameBox.setSpacing(6);

        Label hospitalNameLabel = new Label("Hospital Name");
        hospitalNameLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        TextField hospitalName = new TextField("LifeLink Central Hospital");
        hospitalName.setPrefHeight(38);
        hospitalName.setStyle("-fx-background-color: #faf8ff; -fx-border-color: #c3c6d7; -fx-border-radius: 7px; -fx-background-radius: 7px; -fx-padding: 8px 12px; -fx-font-size: 13px;");

        hospitalNameBox.getChildren().addAll(
                hospitalNameLabel,
                hospitalName
        );

        VBox hospitalEmailBox = new VBox();
        hospitalEmailBox.setSpacing(6);

        Label hospitalEmailLabel = new Label("Hospital Email");
        hospitalEmailLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        TextField hospitalEmail = new TextField("admin@lifelinkhospital.com");
        hospitalEmail.setPrefHeight(38);
        hospitalEmail.setStyle("-fx-background-color: #faf8ff; -fx-border-color: #c3c6d7; -fx-border-radius: 7px; -fx-background-radius: 7px; -fx-padding: 8px 12px; -fx-font-size: 13px;");

        hospitalEmailBox.getChildren().addAll(
                hospitalEmailLabel,
                hospitalEmail
        );

        HBox hospitalRow = new HBox();
        hospitalRow.setSpacing(18);

        HBox.setHgrow(hospitalNameBox, Priority.ALWAYS);
        HBox.setHgrow(hospitalEmailBox, Priority.ALWAYS);

        hospitalRow.getChildren().addAll(
                hospitalNameBox,
                hospitalEmailBox
        );

        VBox hospitalAddressBox = new VBox();
        hospitalAddressBox.setSpacing(6);

        Label hospitalAddressLabel = new Label("Hospital Address");
        hospitalAddressLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        TextField hospitalAddress = new TextField("Pune, Maharashtra, India");
        hospitalAddress.setPrefHeight(38);
        hospitalAddress.setStyle("-fx-background-color: #faf8ff; -fx-border-color: #c3c6d7; -fx-border-radius: 7px; -fx-background-radius: 7px; -fx-padding: 8px 12px; -fx-font-size: 13px;");

        hospitalAddressBox.getChildren().addAll(
                hospitalAddressLabel,
                hospitalAddress
        );

        Button saveHospitalButton = new Button("Save Changes");
        saveHospitalButton.setPrefHeight(38);
        saveHospitalButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 7px; -fx-padding: 8px 18px;");

        hospitalSection.getChildren().addAll(
                hospitalTitle,
                hospitalDescription,
                hospitalRow,
                hospitalAddressBox,
                saveHospitalButton
        );

        // ========================= NOTIFICATION SETTINGS =========================

        VBox notificationSection = new VBox();
        notificationSection.setSpacing(14);
        notificationSection.setPadding(new Insets(20));
        notificationSection.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label notificationTitle = new Label("Notification Preferences");
        notificationTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label notificationDescription = new Label("Choose which hospital events should generate notifications.");
        notificationDescription.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

        CheckBox emergencyNotifications = new CheckBox("Emergency Requests");
        emergencyNotifications.setSelected(true);
        emergencyNotifications.setStyle("-fx-font-size: 13px; -fx-text-fill: #191b23;");

        CheckBox ambulanceNotifications = new CheckBox("Ambulance Arrival Notifications");
        ambulanceNotifications.setSelected(true);
        ambulanceNotifications.setStyle("-fx-font-size: 13px; -fx-text-fill: #191b23;");

        CheckBox resourceNotifications = new CheckBox("Low Resource Alerts");
        resourceNotifications.setSelected(true);
        resourceNotifications.setStyle("-fx-font-size: 13px; -fx-text-fill: #191b23;");

        CheckBox doctorNotifications = new CheckBox("Doctor Assignment Notifications");
        doctorNotifications.setSelected(true);
        doctorNotifications.setStyle("-fx-font-size: 13px; -fx-text-fill: #191b23;");

        CheckBox otNotifications = new CheckBox("Operation Theatre Updates");
        otNotifications.setSelected(true);
        otNotifications.setStyle("-fx-font-size: 13px; -fx-text-fill: #191b23;");

        notificationSection.getChildren().addAll(
                notificationTitle,
                notificationDescription,
                emergencyNotifications,
                ambulanceNotifications,
                resourceNotifications,
                doctorNotifications,
                otNotifications
        );

        // ========================= EMERGENCY SETTINGS =========================

        VBox emergencySection = new VBox();
        emergencySection.setSpacing(14);
        emergencySection.setPadding(new Insets(20));
        emergencySection.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label emergencyTitle = new Label("Emergency Settings");
        emergencyTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label emergencyDescription = new Label("Configure how critical emergency requests are handled.");
        emergencyDescription.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

        CheckBox automaticEmergency = new CheckBox("Enable automatic emergency alerts");
        automaticEmergency.setSelected(true);
        automaticEmergency.setStyle("-fx-font-size: 13px; -fx-text-fill: #191b23;");

        CheckBox ambulanceAutoAssign = new CheckBox("Automatically notify ambulance team");
        ambulanceAutoAssign.setSelected(true);
        ambulanceAutoAssign.setStyle("-fx-font-size: 13px; -fx-text-fill: #191b23;");

        CheckBox doctorAlert = new CheckBox("Alert available emergency doctors");
        doctorAlert.setSelected(true);
        doctorAlert.setStyle("-fx-font-size: 13px; -fx-text-fill: #191b23;");

        CheckBox icuAlert = new CheckBox("Notify when ICU capacity reaches critical level");
        icuAlert.setSelected(true);
        icuAlert.setStyle("-fx-font-size: 13px; -fx-text-fill: #191b23;");

        emergencySection.getChildren().addAll(
                emergencyTitle,
                emergencyDescription,
                automaticEmergency,
                ambulanceAutoAssign,
                doctorAlert,
                icuAlert
        );

        // ========================= SYSTEM SETTINGS =========================

        VBox systemSection = new VBox();
        systemSection.setSpacing(15);
        systemSection.setPadding(new Insets(20));
        systemSection.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label systemTitle = new Label("System Preferences");
        systemTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label systemDescription = new Label("Configure general application preferences.");
        systemDescription.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

        VBox languageBox = new VBox();
        languageBox.setSpacing(6);

        Label languageLabel = new Label("Language");
        languageLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        ComboBox<String> languageCombo = new ComboBox<>();
        languageCombo.getItems().addAll(
                "English",
                "Hindi",
                "Marathi"
        );
        languageCombo.setValue("English");
        languageCombo.setPrefWidth(220);
        languageCombo.setPrefHeight(38);

        languageBox.getChildren().addAll(
                languageLabel,
                languageCombo
        );

        VBox refreshBox = new VBox();
        refreshBox.setSpacing(6);

        Label refreshLabel = new Label("Dashboard Refresh Interval");
        refreshLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #434655;");

        ComboBox<String> refreshCombo = new ComboBox<>();
        refreshCombo.getItems().addAll(
                "10 Seconds",
                "30 Seconds",
                "1 Minute",
                "5 Minutes"
        );
        refreshCombo.setValue("30 Seconds");
        refreshCombo.setPrefWidth(220);
        refreshCombo.setPrefHeight(38);

        refreshBox.getChildren().addAll(
                refreshLabel,
                refreshCombo
        );

        HBox systemRow = new HBox();
        systemRow.setSpacing(25);
        systemRow.getChildren().addAll(
                languageBox,
                refreshBox
        );

        systemSection.getChildren().addAll(
                systemTitle,
                systemDescription,
                systemRow
        );

        // ========================= SECURITY SETTINGS =========================

        VBox securitySection = new VBox();
        securitySection.setSpacing(14);
        securitySection.setPadding(new Insets(20));
        securitySection.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label securityTitle = new Label("Security");
        securityTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label securityDescription = new Label("Manage administrator account and system security preferences.");
        securityDescription.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

        CheckBox twoFactor = new CheckBox("Enable two-factor authentication");
        twoFactor.setSelected(true);
        twoFactor.setStyle("-fx-font-size: 13px; -fx-text-fill: #191b23;");

        CheckBox sessionTimeout = new CheckBox("Automatically log out inactive sessions");
        sessionTimeout.setSelected(true);
        sessionTimeout.setStyle("-fx-font-size: 13px; -fx-text-fill: #191b23;");

        Button changePassword = new Button("Change Password");
        changePassword.setPrefHeight(38);
        changePassword.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 7px; -fx-background-radius: 7px; -fx-text-fill: #191b23; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 8px 18px;");

        securitySection.getChildren().addAll(
                securityTitle,
                securityDescription,
                twoFactor,
                sessionTimeout,
                changePassword
        );

        // ========================= SAVE ALL BUTTON =========================

        HBox actionBar = new HBox();
        actionBar.setSpacing(12);
        actionBar.setAlignment(Pos.CENTER_RIGHT);

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefHeight(40);
        cancelButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #c3c6d7; -fx-border-radius: 7px; -fx-background-radius: 7px; -fx-text-fill: #434655; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 8px 20px;");

        Button saveButton = new Button("Save All Changes");
        saveButton.setPrefHeight(40);
        saveButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-background-radius: 7px; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 8px 20px;");

        actionBar.getChildren().addAll(
                cancelButton,
                saveButton
        );

        // ========================= ADD ALL SECTIONS =========================

        pageContent.getChildren().addAll(
                pageHeader,
                hospitalSection,
                notificationSection,
                emergencySection,
                systemSection,
                securitySection,
                new Separator(),
                actionBar
        );

        // ========================= SCROLL PANE =========================

        ScrollPane scrollPane = new ScrollPane(pageContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background:#faf8ff;-fx-background-color:#faf8ff;");

        VBox settingsPage = new VBox(
                topBar,
                scrollPane
        );

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return settingsPage;
    }
}

