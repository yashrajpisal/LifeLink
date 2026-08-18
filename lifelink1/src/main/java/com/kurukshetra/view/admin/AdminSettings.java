
// package com.kurukshetra.view.admin;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.CheckBox;
// import javafx.scene.control.ComboBox;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;

// public class AdminSettings {

//     public static VBox getSettingsPage() {

//         // =============================================================
//         // MAIN CONTENT
//         // =============================================================

//         VBox mainContent = new VBox(24);
//         mainContent.setPadding(new Insets(30));
//         mainContent.setStyle("-fx-background-color: #f8f8ff;");

//         // =============================================================
//         // TOP HEADER
//         // =============================================================

//         HBox topHeader = new HBox();
//         topHeader.setAlignment(Pos.CENTER_LEFT);
//         topHeader.setSpacing(15);
//         topHeader.setPadding(new Insets(0, 0, 10, 0));

//         VBox headerText = new VBox(4);

//         Label pageTitle = new Label("System Settings");
//         pageTitle.setStyle("-fx-font-size: 32px; -fx-font-weight: 600; -fx-text-fill: #191b23;");

//         Label pageSubtitle = new Label("Configure global parameters and security protocols for the LifeLink ecosystem.");
//         pageSubtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #434655;");

//         headerText.getChildren().addAll(pageTitle, pageSubtitle);
//         topHeader.getChildren().add(headerText);

//         // =============================================================
//         // SETTINGS AREA
//         // =============================================================

//         HBox settingsArea = new HBox(24);
//         settingsArea.setAlignment(Pos.TOP_LEFT);

//         // =============================================================
//         // LEFT SETTINGS NAVIGATION
//         // =============================================================

//         // VBox settingsNavigation = new VBox(8);
//         // settingsNavigation.setPrefWidth(210);
//         // settingsNavigation.setMinWidth(210);

//         // Button generalButton = new Button("⚙   General");
//         // generalButton.setMaxWidth(Double.MAX_VALUE);
//         // generalButton.setAlignment(Pos.CENTER_LEFT);
//         // generalButton.setPadding(new Insets(12));
//         // generalButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 12px;");

//         // Button securityButton = new Button("🔒   Security");
//         // securityButton.setMaxWidth(Double.MAX_VALUE);
//         // securityButton.setAlignment(Pos.CENTER_LEFT);
//         // securityButton.setPadding(new Insets(12));
//         // securityButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 13px; -fx-background-radius: 12px;");

//         // Button notificationButton = new Button("🔔   Notifications");
//         // notificationButton.setMaxWidth(Double.MAX_VALUE);
//         // notificationButton.setAlignment(Pos.CENTER_LEFT);
//         // notificationButton.setPadding(new Insets(12));
//         // notificationButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 13px; -fx-background-radius: 12px;");

//         // Button appearanceButton = new Button("🎨   Appearance");
//         // appearanceButton.setMaxWidth(Double.MAX_VALUE);
//         // appearanceButton.setAlignment(Pos.CENTER_LEFT);
//         // appearanceButton.setPadding(new Insets(12));
//         // appearanceButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 13px; -fx-background-radius: 12px;");

//         // Button databaseButton = new Button("▣   Database");
//         // databaseButton.setMaxWidth(Double.MAX_VALUE);
//         // databaseButton.setAlignment(Pos.CENTER_LEFT);
//         // databaseButton.setPadding(new Insets(12));
//         // databaseButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #434655; -fx-font-size: 13px; -fx-background-radius: 12px;");

//         // settingsNavigation.getChildren().addAll(
//         //         generalButton,
//         //         securityButton,
//         //         notificationButton,
//         //         appearanceButton,
//         //         databaseButton
//         // );

//         // =============================================================
//         // RIGHT FORM AREA
//         // =============================================================

//         VBox formArea = new VBox(24);
//         formArea.setMaxWidth(Double.MAX_VALUE);

//         HBox.setHgrow(formArea, Priority.ALWAYS);

//         // =============================================================
//         // GENERAL CONFIGURATION
//         // =============================================================

//         VBox generalCard = new VBox();
//         generalCard.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-width: 1px; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         VBox generalHeader = new VBox(5);
//         generalHeader.setPadding(new Insets(20));
//         generalHeader.setStyle("-fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

//         Label generalTitle = new Label("General Configuration");
//         generalTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #191b23;");

//         Label generalSubtitle = new Label("Identity and organizational branding.");
//         generalSubtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

//         generalHeader.getChildren().addAll(generalTitle, generalSubtitle);

//         VBox generalBody = new VBox(20);
//         generalBody.setPadding(new Insets(20));

//         HBox nameOrganization = new HBox(18);

//         VBox systemNameBox = new VBox(7);
//         HBox.setHgrow(systemNameBox, Priority.ALWAYS);

//         Label systemNameLabel = new Label("SYSTEM NAME");
//         systemNameLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #434655;");

//         TextField systemNameField = new TextField("LifeLink Global Dashboard");
//         systemNameField.setPrefHeight(40);
//         systemNameField.setStyle("-fx-background-color: #faf8ff; -fx-border-color: #737686; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 0px 12px; -fx-font-size: 14px;");

//         systemNameBox.getChildren().addAll(systemNameLabel, systemNameField);

//         VBox organizationBox = new VBox(7);
//         HBox.setHgrow(organizationBox, Priority.ALWAYS);

//         Label organizationLabel = new Label("ORGANIZATION UNIT");
//         organizationLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #434655;");

//         TextField organizationField = new TextField("Central Health Authority");
//         organizationField.setPrefHeight(40);
//         organizationField.setStyle("-fx-background-color: #faf8ff; -fx-border-color: #737686; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-padding: 0px 12px; -fx-font-size: 14px;");

//         organizationBox.getChildren().addAll(organizationLabel, organizationField);

//         nameOrganization.getChildren().addAll(systemNameBox, organizationBox);

//         VBox timezoneBox = new VBox(7);

//         Label timezoneLabel = new Label("SYSTEM TIMEZONE");
//         timezoneLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #434655;");

//         ComboBox<String> timezoneCombo = new ComboBox<>();
//         timezoneCombo.getItems().addAll(
//                 "UTC (GMT+00:00)",
//                 "Eastern Standard Time (EST)",
//                 "Pacific Standard Time (PST)"
//         );
//         timezoneCombo.setValue("Eastern Standard Time (EST)");
//         timezoneCombo.setMaxWidth(Double.MAX_VALUE);
//         timezoneCombo.setPrefHeight(40);

//         timezoneBox.getChildren().addAll(timezoneLabel, timezoneCombo);

//         generalBody.getChildren().addAll(nameOrganization, timezoneBox);
//         generalCard.getChildren().addAll(generalHeader, generalBody);

//         // =============================================================
//         // SECURITY
//         // =============================================================

//         VBox securityCard = new VBox();
//         securityCard.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-width: 1px; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         VBox securityHeader = new VBox(5);
//         securityHeader.setPadding(new Insets(20));
//         securityHeader.setStyle("-fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

//         Label securityTitle = new Label("Security & Authentication");
//         securityTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #191b23;");

//         Label securitySubtitle = new Label("Protect access with multi-factor protocols.");
//         securitySubtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

//         securityHeader.getChildren().addAll(securityTitle, securitySubtitle);

//         VBox securityBody = new VBox(20);
//         securityBody.setPadding(new Insets(20));

//         HBox twoFactorBox = new HBox();
//         twoFactorBox.setAlignment(Pos.CENTER_LEFT);
//         twoFactorBox.setPadding(new Insets(15));
//         twoFactorBox.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         VBox twoFactorText = new VBox(4);

//         Label twoFactorTitle = new Label("Two-Factor Authentication (2FA)");
//         twoFactorTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

//         Label twoFactorSubtitle = new Label("Add an extra layer of security to the administrator login.");
//         twoFactorSubtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");

//         twoFactorText.getChildren().addAll(twoFactorTitle, twoFactorSubtitle);

//         CheckBox twoFactorCheck = new CheckBox();
//         twoFactorCheck.setSelected(true);

//         Region securitySpacer = new Region();
//         HBox.setHgrow(securitySpacer, Priority.ALWAYS);

//         twoFactorBox.getChildren().addAll(twoFactorText, securitySpacer, twoFactorCheck);

//         Label passwordRequirements = new Label("PASSWORD REQUIREMENTS");
//         passwordRequirements.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #434655;");

//         HBox passwordItems = new HBox(25);

//         Label requirement1 = new Label("✓  Minimum 12 characters");
//         requirement1.setStyle("-fx-font-size: 13px; -fx-text-fill: #16a34a;");

//         Label requirement2 = new Label("✓  Special characters");
//         requirement2.setStyle("-fx-font-size: 13px; -fx-text-fill: #16a34a;");

//         Label requirement3 = new Label("○  Frequent reset (90 days)");
//         requirement3.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");

//         passwordItems.getChildren().addAll(requirement1, requirement2, requirement3);

//         Button updatePasswordButton = new Button("Update Root Password");
//         updatePasswordButton.setPadding(new Insets(10, 20, 10, 20));
//         updatePasswordButton.setStyle("-fx-background-color: white; -fx-border-color: #2563eb; -fx-text-fill: #2563eb; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-font-weight: bold;");

//         securityBody.getChildren().addAll(
//                 twoFactorBox,
//                 passwordRequirements,
//                 passwordItems,
//                 updatePasswordButton
//         );

//         securityCard.getChildren().addAll(securityHeader, securityBody);

//         // =============================================================
//         // NOTIFICATIONS
//         // =============================================================

//         VBox notificationCard = new VBox();
//         notificationCard.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-width: 1px; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         VBox notificationHeader = new VBox(5);
//         notificationHeader.setPadding(new Insets(20));
//         notificationHeader.setStyle("-fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

//         Label notificationTitle = new Label("Communication Channels");
//         notificationTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #191b23;");

//         Label notificationSubtitle = new Label("Configure how the system sends critical alerts.");
//         notificationSubtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

//         notificationHeader.getChildren().addAll(notificationTitle, notificationSubtitle);

//         VBox notificationBody = new VBox(18);
//         notificationBody.setPadding(new Insets(20));

//         HBox emailRow = new HBox();
//         emailRow.setAlignment(Pos.CENTER_LEFT);

//         VBox emailText = new VBox(4);

//         Label emailTitle = new Label("Critical Email Alerts");
//         emailTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

//         Label emailSubtitle = new Label("Immediate dispatch on system failures.");
//         emailSubtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");

//         emailText.getChildren().addAll(emailTitle, emailSubtitle);

//         CheckBox emailCheck = new CheckBox();
//         emailCheck.setSelected(true);

//         Region emailSpacer = new Region();
//         HBox.setHgrow(emailSpacer, Priority.ALWAYS);

//         emailRow.getChildren().addAll(emailText, emailSpacer, emailCheck);

//         HBox smsRow = new HBox();
//         smsRow.setAlignment(Pos.CENTER_LEFT);

//         VBox smsText = new VBox(4);

//         Label smsTitle = new Label("SMS Dispatch Updates");
//         smsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

//         Label smsSubtitle = new Label("Notify response units via cellular networks.");
//         smsSubtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");

//         smsText.getChildren().addAll(smsTitle, smsSubtitle);

//         CheckBox smsCheck = new CheckBox();
//         smsCheck.setSelected(true);

//         Region smsSpacer = new Region();
//         HBox.setHgrow(smsSpacer, Priority.ALWAYS);

//         smsRow.getChildren().addAll(smsText, smsSpacer, smsCheck);

//         HBox dailyRow = new HBox();
//         dailyRow.setAlignment(Pos.CENTER_LEFT);

//         VBox dailyText = new VBox(4);

//         Label dailyTitle = new Label("Daily Summary Reports");
//         dailyTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

//         Label dailySubtitle = new Label("Receive a midnight audit trail.");
//         dailySubtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");

//         dailyText.getChildren().addAll(dailyTitle, dailySubtitle);

//         CheckBox dailyCheck = new CheckBox();

//         Region dailySpacer = new Region();
//         HBox.setHgrow(dailySpacer, Priority.ALWAYS);

//         dailyRow.getChildren().addAll(dailyText, dailySpacer, dailyCheck);

//         notificationBody.getChildren().addAll(emailRow, smsRow, dailyRow);
//         notificationCard.getChildren().addAll(notificationHeader, notificationBody);

//         // =============================================================
//         // APPEARANCE
//         // =============================================================

//         VBox appearanceCard = new VBox();
//         appearanceCard.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-width: 1px; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         VBox appearanceHeader = new VBox(5);
//         appearanceHeader.setPadding(new Insets(20));
//         appearanceHeader.setStyle("-fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

//         Label appearanceTitle = new Label("System Appearance");
//         appearanceTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #191b23;");

//         Label appearanceSubtitle = new Label("Tailor the visual experience for long shifts.");
//         appearanceSubtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

//         appearanceHeader.getChildren().addAll(appearanceTitle, appearanceSubtitle);

//         HBox appearanceBody = new HBox(15);
//         appearanceBody.setPadding(new Insets(20));

//         VBox lightModeBox = new VBox(10);
//         lightModeBox.setAlignment(Pos.CENTER);
//         lightModeBox.setPadding(new Insets(15));
//         lightModeBox.setStyle("-fx-background-color: white; -fx-border-color: #2563eb; -fx-border-width: 2px; -fx-border-radius: 12px; -fx-background-radius: 12px;");
//         HBox.setHgrow(lightModeBox, Priority.ALWAYS);

//         StackPane lightPreview = new StackPane();
//         lightPreview.setPrefHeight(70);
//         lightPreview.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #c3c6d7; -fx-border-radius: 8px; -fx-background-radius: 8px;");

//         Label lightIcon = new Label("☀");
//         lightIcon.setStyle("-fx-font-size: 25px; -fx-text-fill: #737686;");

//         lightPreview.getChildren().add(lightIcon);

//         Label lightText = new Label("Light Mode");
//         lightText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

//         lightModeBox.getChildren().addAll(lightPreview, lightText);

//         VBox darkModeBox = new VBox(10);
//         darkModeBox.setAlignment(Pos.CENTER);
//         darkModeBox.setPadding(new Insets(15));
//         darkModeBox.setStyle("-fx-background-color: #1e293b; -fx-border-color: transparent; -fx-border-radius: 12px; -fx-background-radius: 12px;");
//         HBox.setHgrow(darkModeBox, Priority.ALWAYS);

//         StackPane darkPreview = new StackPane();
//         darkPreview.setPrefHeight(70);
//         darkPreview.setStyle("-fx-background-color: #0f172a; -fx-border-radius: 8px; -fx-background-radius: 8px;");

//         Label darkIcon = new Label("☾");
//         darkIcon.setStyle("-fx-font-size: 25px; -fx-text-fill: #94a3b8;");

//         darkPreview.getChildren().add(darkIcon);

//         Label darkText = new Label("Dark Mode");
//         darkText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: white;");

//         darkModeBox.getChildren().addAll(darkPreview, darkText);

//         VBox syncModeBox = new VBox(10);
//         syncModeBox.setAlignment(Pos.CENTER);
//         syncModeBox.setPadding(new Insets(15));
//         syncModeBox.setStyle("-fx-background-color: #f1f5f9; -fx-border-color: transparent; -fx-border-radius: 12px; -fx-background-radius: 12px;");
//         HBox.setHgrow(syncModeBox, Priority.ALWAYS);

//         StackPane syncPreview = new StackPane();
//         syncPreview.setPrefHeight(70);
//         syncPreview.setStyle("-fx-background-color: #e2e8f0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

//         Label syncIcon = new Label("◐");
//         syncIcon.setStyle("-fx-font-size: 25px; -fx-text-fill: #737686;");

//         syncPreview.getChildren().add(syncIcon);

//         Label syncText = new Label("Sync with OS");
//         syncText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

//         syncModeBox.getChildren().addAll(syncPreview, syncText);

//         appearanceBody.getChildren().addAll(
//                 lightModeBox,
//                 darkModeBox,
//                 syncModeBox
//         );

//         appearanceCard.getChildren().addAll(appearanceHeader, appearanceBody);

//         // =============================================================
//         // DATABASE
//         // =============================================================

//         VBox databaseCard = new VBox();
//         databaseCard.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-width: 1px; -fx-border-radius: 12px; -fx-background-radius: 12px;");

//         VBox databaseHeader = new VBox(5);
//         databaseHeader.setPadding(new Insets(20));
//         databaseHeader.setStyle("-fx-border-color: #c3c6d7; -fx-border-width: 0px 0px 1px 0px;");

//         Label databaseTitle = new Label("Database & Disaster Recovery");
//         databaseTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 600; -fx-text-fill: #191b23;");

//         Label databaseSubtitle = new Label("Maintain data integrity and system availability.");
//         databaseSubtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

//         databaseHeader.getChildren().addAll(databaseTitle, databaseSubtitle);

//         VBox databaseBody = new VBox(18);
//         databaseBody.setPadding(new Insets(20));

//         HBox databaseStats = new HBox(15);

//         VBox backupBox = new VBox(5);
//         backupBox.setPadding(new Insets(15));
//         backupBox.setStyle("-fx-background-color: #e7e7f3; -fx-background-radius: 12px;");
//         HBox.setHgrow(backupBox, Priority.ALWAYS);

//         Label backupLabel = new Label("LAST BACKUP");
//         backupLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #434655;");

//         Label backupTime = new Label("2 hours ago");
//         backupTime.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2563eb;");

//         Label backupLocation = new Label("Cloud Storage: US-East-1");
//         backupLocation.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

//         backupBox.getChildren().addAll(
//                 backupLabel,
//                 backupTime,
//                 backupLocation
//         );

//         VBox healthBox = new VBox(5);
//         healthBox.setPadding(new Insets(15));
//         healthBox.setStyle("-fx-background-color: #e7e7f3; -fx-background-radius: 12px;");
//         HBox.setHgrow(healthBox, Priority.ALWAYS);

//         Label healthLabel = new Label("DATABASE HEALTH");
//         healthLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #434655;");

//         Label healthValue = new Label("Optimal (99.9%)");
//         healthValue.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #16a34a;");

//         Label healthDetails = new Label("IOPS: 12.4k / 15k");
//         healthDetails.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

//         healthBox.getChildren().addAll(
//                 healthLabel,
//                 healthValue,
//                 healthDetails
//         );

//         databaseStats.getChildren().addAll(backupBox, healthBox);

//         HBox databaseButtons = new HBox(12);

//         Button backupButton = new Button("▣   Run Manual Backup");
//         backupButton.setPadding(new Insets(11, 20, 11, 20));
//         backupButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10px;");

//         Button restoreButton = new Button("↻   Restore from Point");
//         restoreButton.setPadding(new Insets(11, 20, 11, 20));
//         restoreButton.setStyle("-fx-background-color: #e1e2ed; -fx-text-fill: #191b23; -fx-font-weight: bold; -fx-background-radius: 10px;");

//         databaseButtons.getChildren().addAll(
//                 backupButton,
//                 restoreButton
//         );

//         databaseBody.getChildren().addAll(
//                 databaseStats,
//                 databaseButtons
//         );

//         databaseCard.getChildren().addAll(
//                 databaseHeader,
//                 databaseBody
//         );

//         // =============================================================
//         // FORM AREA ADD
//         // =============================================================

//         formArea.getChildren().addAll(
//                 generalCard,
//                 securityCard,
//                 notificationCard,
//                 appearanceCard,
//                 databaseCard
//         );

//         settingsArea.getChildren().addAll(
//                 // settingsNavigation,
//                 formArea
//         );

//         // =============================================================
//         // SAVE / DISCARD BUTTONS
//         // =============================================================

//         HBox footerButtons = new HBox(12);
//         footerButtons.setAlignment(Pos.CENTER_RIGHT);
//         footerButtons.setPadding(new Insets(10, 0, 10, 0));

//         Button discardButton = new Button("Discard Changes");
//         discardButton.setPadding(new Insets(12, 22, 12, 22));
//         discardButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563eb; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 10px;");

//         Button saveButton = new Button("Save System Settings");
//         saveButton.setPadding(new Insets(12, 25, 12, 25));
//         saveButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 10px;");

//         footerButtons.getChildren().addAll(
//                 discardButton,
//                 saveButton
//         );

//         // =============================================================
//         // ADD EVERYTHING TO MAIN CONTENT
//         // =============================================================

//         mainContent.getChildren().addAll(
//                 topHeader,
//                 settingsArea,
//                 footerButtons
//         );

//         // =============================================================
//         // SCROLL PANE
//         // =============================================================

//         ScrollPane scrollPane = new ScrollPane(mainContent);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//         scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

//         // =============================================================
//         // FINAL CONTENT
//         // =============================================================

//         VBox finalContent = new VBox(scrollPane);
//         finalContent.setStyle("-fx-background-color: #f8f8ff;");

//         VBox.setVgrow(scrollPane, Priority.ALWAYS);

//         return finalContent;
//     }
// }



package com.kurukshetra.view.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AdminSettings {

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

    private static final String SUCCESS_TEXT = "#15803D";
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
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10px;" +
            "-fx-effect: dropshadow(gaussian, rgba(192, 132, 252, 0.35), 10, 0.2, 0, 3);" +
            "-fx-cursor: hand;";

    private static final String CONTROL_INPUT_STYLE = FONT_STACK +
            "-fx-background-color: #FAF8FF;" +
            "-fx-border-color: " + BORDER_COLOR + ";" +
            "-fx-border-radius: 10px;" +
            "-fx-background-radius: 10px;" +
            "-fx-padding: 0px 12px;" +
            "-fx-font-size: 13px;" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";";

    public static VBox getSettingsPage() {

        VBox mainContent = new VBox(24);
        mainContent.setPadding(new Insets(30));
        mainContent.setStyle("-fx-background-color: " + BG_PAGE + ";");

        // TOP HEADER
        HBox topHeader = new HBox();
        topHeader.setAlignment(Pos.CENTER_LEFT);
        topHeader.setSpacing(15);
        topHeader.setPadding(new Insets(0, 0, 10, 0));

        VBox headerText = new VBox(4);

        Label pageTitle = new Label("System Settings");
        pageTitle.setStyle(FONT_STACK + "-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label pageSubtitle = new Label("Configure global parameters and security protocols for the LifeLink ecosystem.");
        pageSubtitle.setStyle(FONT_STACK + "-fx-font-size: 15px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        headerText.getChildren().addAll(pageTitle, pageSubtitle);
        topHeader.getChildren().add(headerText);

        VBox formArea = new VBox(24);
        formArea.setMaxWidth(Double.MAX_VALUE);

        // GENERAL CONFIGURATION
        VBox generalCard = new VBox();
        generalCard.setStyle(BASE_CARD_STYLE);

        VBox generalHeader = new VBox(4);
        generalHeader.setPadding(new Insets(20));
        generalHeader.setStyle("-fx-border-color: " + BORDER_DIVIDER + "; -fx-border-width: 0px 0px 1px 0px;");

        Label generalTitle = new Label("General Configuration");
        generalTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label generalSubtitle = new Label("Identity and organizational branding.");
        generalSubtitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        generalHeader.getChildren().addAll(generalTitle, generalSubtitle);

        VBox generalBody = new VBox(18);
        generalBody.setPadding(new Insets(20));

        HBox nameOrganization = new HBox(18);

        VBox systemNameBox = new VBox(7);
        HBox.setHgrow(systemNameBox, Priority.ALWAYS);

        Label systemNameLabel = new Label("SYSTEM NAME");
        systemNameLabel.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");

        TextField systemNameField = new TextField("LifeLink Global Dashboard");
        systemNameField.setPrefHeight(40);
        systemNameField.setStyle(CONTROL_INPUT_STYLE);

        systemNameBox.getChildren().addAll(systemNameLabel, systemNameField);

        VBox organizationBox = new VBox(7);
        HBox.setHgrow(organizationBox, Priority.ALWAYS);

        Label organizationLabel = new Label("ORGANIZATION UNIT");
        organizationLabel.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");

        TextField organizationField = new TextField("Central Health Authority");
        organizationField.setPrefHeight(40);
        organizationField.setStyle(CONTROL_INPUT_STYLE);

        organizationBox.getChildren().addAll(organizationLabel, organizationField);
        nameOrganization.getChildren().addAll(systemNameBox, organizationBox);

        VBox timezoneBox = new VBox(7);
        Label timezoneLabel = new Label("SYSTEM TIMEZONE");
        timezoneLabel.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");

        ComboBox<String> timezoneCombo = new ComboBox<>();
        timezoneCombo.getItems().addAll(
                "UTC (GMT+00:00)",
                "Eastern Standard Time (EST)",
                "Pacific Standard Time (PST)"
        );
        timezoneCombo.setValue("Eastern Standard Time (EST)");
        timezoneCombo.setMaxWidth(Double.MAX_VALUE);
        timezoneCombo.setPrefHeight(40);
        timezoneCombo.setStyle(CONTROL_INPUT_STYLE);

        timezoneBox.getChildren().addAll(timezoneLabel, timezoneCombo);
        generalBody.getChildren().addAll(nameOrganization, timezoneBox);
        generalCard.getChildren().addAll(generalHeader, generalBody);

        // SECURITY
        VBox securityCard = new VBox();
        securityCard.setStyle(BASE_CARD_STYLE);

        VBox securityHeader = new VBox(4);
        securityHeader.setPadding(new Insets(20));
        securityHeader.setStyle("-fx-border-color: " + BORDER_DIVIDER + "; -fx-border-width: 0px 0px 1px 0px;");

        Label securityTitle = new Label("Security & Authentication");
        securityTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label securitySubtitle = new Label("Protect access with multi-factor protocols.");
        securitySubtitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        securityHeader.getChildren().addAll(securityTitle, securitySubtitle);

        VBox securityBody = new VBox(18);
        securityBody.setPadding(new Insets(20));

        HBox twoFactorBox = new HBox();
        twoFactorBox.setAlignment(Pos.CENTER_LEFT);
        twoFactorBox.setPadding(new Insets(15));
        twoFactorBox.setStyle(FONT_STACK + "-fx-background-color: " + PURPLE_LIGHT + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        VBox twoFactorText = new VBox(4);
        Label twoFactorTitle = new Label("Two-Factor Authentication (2FA)");
        twoFactorTitle.setStyle(FONT_STACK + "-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label twoFactorSubtitle = new Label("Add an extra layer of security to the administrator login.");
        twoFactorSubtitle.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        twoFactorText.getChildren().addAll(twoFactorTitle, twoFactorSubtitle);

        CheckBox twoFactorCheck = new CheckBox();
        twoFactorCheck.setSelected(true);

        Region securitySpacer = new Region();
        HBox.setHgrow(securitySpacer, Priority.ALWAYS);

        twoFactorBox.getChildren().addAll(twoFactorText, securitySpacer, twoFactorCheck);

        Label passwordRequirements = new Label("PASSWORD REQUIREMENTS");
        passwordRequirements.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");

        HBox passwordItems = new HBox(25);
        Label requirement1 = new Label("✓  Minimum 12 characters");
        requirement1.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-text-fill: " + SUCCESS_TEXT + ";");

        Label requirement2 = new Label("✓  Special characters");
        requirement2.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-text-fill: " + SUCCESS_TEXT + ";");

        Label requirement3 = new Label("○  Frequent reset (90 days)");
        requirement3.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED + ";");

        passwordItems.getChildren().addAll(requirement1, requirement2, requirement3);

        Button updatePasswordButton = new Button("Update Root Password");
        updatePasswordButton.setPadding(new Insets(10, 20, 10, 20));
        updatePasswordButton.setStyle(FONT_STACK + "-fx-background-color: " + BG_SURFACE + "; -fx-border-color: " + PURPLE_DARK + "; -fx-text-fill: " + PURPLE_DARK + "; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-font-weight: bold; -fx-cursor: hand;");

        securityBody.getChildren().addAll(twoFactorBox, passwordRequirements, passwordItems, updatePasswordButton);
        securityCard.getChildren().addAll(securityHeader, securityBody);

        // NOTIFICATIONS
        VBox notificationCard = new VBox();
        notificationCard.setStyle(BASE_CARD_STYLE);

        VBox notificationHeader = new VBox(4);
        notificationHeader.setPadding(new Insets(20));
        notificationHeader.setStyle("-fx-border-color: " + BORDER_DIVIDER + "; -fx-border-width: 0px 0px 1px 0px;");

        Label notificationTitle = new Label("Communication Channels");
        notificationTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label notificationSubtitle = new Label("Configure how the system sends critical alerts.");
        notificationSubtitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        notificationHeader.getChildren().addAll(notificationTitle, notificationSubtitle);

        VBox notificationBody = new VBox(16);
        notificationBody.setPadding(new Insets(20));

        HBox emailRow = createToggleRow("Critical Email Alerts", "Immediate dispatch on system failures.", true);
        HBox smsRow = createToggleRow("SMS Dispatch Updates", "Notify response units via cellular networks.", true);
        HBox dailyRow = createToggleRow("Daily Summary Reports", "Receive a midnight audit trail.", false);

        notificationBody.getChildren().addAll(emailRow, smsRow, dailyRow);
        notificationCard.getChildren().addAll(notificationHeader, notificationBody);

        // APPEARANCE
        VBox appearanceCard = new VBox();
        appearanceCard.setStyle(BASE_CARD_STYLE);

        VBox appearanceHeader = new VBox(4);
        appearanceHeader.setPadding(new Insets(20));
        appearanceHeader.setStyle("-fx-border-color: " + BORDER_DIVIDER + "; -fx-border-width: 0px 0px 1px 0px;");

        Label appearanceTitle = new Label("System Appearance");
        appearanceTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label appearanceSubtitle = new Label("Tailor the visual experience for long shifts.");
        appearanceSubtitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        appearanceHeader.getChildren().addAll(appearanceTitle, appearanceSubtitle);

        HBox appearanceBody = new HBox(15);
        appearanceBody.setPadding(new Insets(20));

        VBox lightModeBox = new VBox(10);
        lightModeBox.setAlignment(Pos.CENTER);
        lightModeBox.setPadding(new Insets(15));
        lightModeBox.setStyle("-fx-background-color: white; -fx-border-color: " + PURPLE_DARK + "; -fx-border-width: 2px; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-cursor: hand;");
        HBox.setHgrow(lightModeBox, Priority.ALWAYS);

        StackPane lightPreview = new StackPane();
        lightPreview.setPrefHeight(70);
        lightPreview.setStyle("-fx-background-color: " + PURPLE_LIGHT + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        Label lightIcon = new Label("☀");
        lightIcon.setStyle("-fx-font-size: 25px; -fx-text-fill: " + PURPLE_DARK + ";");
        lightPreview.getChildren().add(lightIcon);

        Label lightText = new Label("Light Mode");
        lightText.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + PURPLE_DARK + ";");
        lightModeBox.getChildren().addAll(lightPreview, lightText);

        VBox darkModeBox = new VBox(10);
        darkModeBox.setAlignment(Pos.CENTER);
        darkModeBox.setPadding(new Insets(15));
        darkModeBox.setStyle("-fx-background-color: #1e293b; -fx-border-color: transparent; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-cursor: hand;");
        HBox.setHgrow(darkModeBox, Priority.ALWAYS);

        StackPane darkPreview = new StackPane();
        darkPreview.setPrefHeight(70);
        darkPreview.setStyle("-fx-background-color: #0f172a; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        Label darkIcon = new Label("☾");
        darkIcon.setStyle("-fx-font-size: 25px; -fx-text-fill: #94a3b8;");
        darkPreview.getChildren().add(darkIcon);

        Label darkText = new Label("Dark Mode");
        darkText.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: white;");
        darkModeBox.getChildren().addAll(darkPreview, darkText);

        VBox syncModeBox = new VBox(10);
        syncModeBox.setAlignment(Pos.CENTER);
        syncModeBox.setPadding(new Insets(15));
        syncModeBox.setStyle("-fx-background-color: #f1f5f9; -fx-border-color: transparent; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-cursor: hand;");
        HBox.setHgrow(syncModeBox, Priority.ALWAYS);

        StackPane syncPreview = new StackPane();
        syncPreview.setPrefHeight(70);
        syncPreview.setStyle("-fx-background-color: #e2e8f0; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        Label syncIcon = new Label("◐");
        syncIcon.setStyle("-fx-font-size: 25px; -fx-text-fill: #737686;");
        syncPreview.getChildren().add(syncIcon);

        Label syncText = new Label("Sync with OS");
        syncText.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");
        syncModeBox.getChildren().addAll(syncPreview, syncText);

        appearanceBody.getChildren().addAll(lightModeBox, darkModeBox, syncModeBox);
        appearanceCard.getChildren().addAll(appearanceHeader, appearanceBody);

        // DATABASE
        VBox databaseCard = new VBox();
        databaseCard.setStyle(BASE_CARD_STYLE);

        VBox databaseHeader = new VBox(4);
        databaseHeader.setPadding(new Insets(20));
        databaseHeader.setStyle("-fx-border-color: " + BORDER_DIVIDER + "; -fx-border-width: 0px 0px 1px 0px;");

        Label databaseTitle = new Label("Database & Disaster Recovery");
        databaseTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label databaseSubtitle = new Label("Maintain data integrity and system availability.");
        databaseSubtitle.setStyle(FONT_STACK + "-fx-font-size: 13px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        databaseHeader.getChildren().addAll(databaseTitle, databaseSubtitle);

        VBox databaseBody = new VBox(18);
        databaseBody.setPadding(new Insets(20));

        HBox databaseStats = new HBox(15);

        VBox backupBox = new VBox(5);
        backupBox.setPadding(new Insets(15));
        backupBox.setStyle(FONT_STACK + "-fx-background-color: #FAF8FF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        HBox.setHgrow(backupBox, Priority.ALWAYS);

        Label backupLabel = new Label("LAST BACKUP");
        backupLabel.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");

        Label backupTime = new Label("2 hours ago");
        backupTime.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + PURPLE_DARK + ";");

        Label backupLocation = new Label("Cloud Storage: US-East-1");
        backupLocation.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        backupBox.getChildren().addAll(backupLabel, backupTime, backupLocation);

        VBox healthBox = new VBox(5);
        healthBox.setPadding(new Insets(15));
        healthBox.setStyle(FONT_STACK + "-fx-background-color: #FAF8FF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        HBox.setHgrow(healthBox, Priority.ALWAYS);

        Label healthLabel = new Label("DATABASE HEALTH");
        healthLabel.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");

        Label healthValue = new Label("Optimal (99.9%)");
        healthValue.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + SUCCESS_TEXT + ";");

        Label healthDetails = new Label("IOPS: 12.4k / 15k");
        healthDetails.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        healthBox.getChildren().addAll(healthLabel, healthValue, healthDetails);
        databaseStats.getChildren().addAll(backupBox, healthBox);

        HBox databaseButtons = new HBox(12);
        Button backupButton = new Button("▣   Run Manual Backup");
        backupButton.setPadding(new Insets(11, 20, 11, 20));
        backupButton.setStyle(PRIMARY_BUTTON_STYLE);

        Button restoreButton = new Button("↻   Restore from Point");
        restoreButton.setPadding(new Insets(11, 20, 11, 20));
        restoreButton.setStyle(FONT_STACK + "-fx-background-color: " + BG_SURFACE + "; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-font-weight: bold; -fx-cursor: hand;");

        databaseButtons.getChildren().addAll(backupButton, restoreButton);
        databaseBody.getChildren().addAll(databaseStats, databaseButtons);
        databaseCard.getChildren().addAll(databaseHeader, databaseBody);

        formArea.getChildren().addAll(
                generalCard,
                securityCard,
                notificationCard,
                appearanceCard,
                databaseCard
        );

        // FOOTER ACTIONS
        HBox footerButtons = new HBox(12);
        footerButtons.setAlignment(Pos.CENTER_RIGHT);
        footerButtons.setPadding(new Insets(10, 0, 10, 0));

        Button discardButton = new Button("Discard Changes");
        discardButton.setPadding(new Insets(12, 22, 12, 22));
        discardButton.setStyle(FONT_STACK + "-fx-background-color: transparent; -fx-text-fill: " + PURPLE_DARK + "; -fx-font-size: 13px; -fx-font-weight: bold; -fx-cursor: hand;");

        Button saveButton = new Button("Save System Settings");
        saveButton.setPadding(new Insets(12, 25, 12, 25));
        saveButton.setStyle(PRIMARY_BUTTON_STYLE);

        footerButtons.getChildren().addAll(discardButton, saveButton);

        mainContent.getChildren().addAll(
                topHeader,
                formArea,
                footerButtons
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

    private static HBox createToggleRow(String title, String subtitle, boolean checked) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        VBox text = new VBox(3);
        Label titleLabel = new Label(title);
        titleLabel.setStyle(FONT_STACK + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label subLabel = new Label(subtitle);
        subLabel.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        text.getChildren().addAll(titleLabel, subLabel);

        CheckBox check = new CheckBox();
        check.setSelected(checked);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row.getChildren().addAll(text, spacer, check);
        return row;
    }
}