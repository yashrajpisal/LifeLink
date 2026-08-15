
package com.kurukshetra.view.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AdminActivityLogs {

    public static VBox getActivityLogsPage() {

        // =============================================================
        // MAIN CONTENT
        // =============================================================

        VBox mainContent = new VBox(24);
        mainContent.setPadding(new Insets(30));
        mainContent.setStyle("-fx-background-color: #f8f8ff;");

        // =============================================================
        // TOP HEADER
        // =============================================================

        HBox topHeader = new HBox();
        topHeader.setAlignment(Pos.CENTER_LEFT);
        topHeader.setSpacing(15);
        topHeader.setPadding(new Insets(0, 0, 10, 0));

        VBox headerText = new VBox(4);

        Label pageTitle = new Label("Activity Logs");
        pageTitle.setStyle("-fx-font-size: 32px;-fx-font-weight: 600;-fx-text-fill: #191b23;");

        Label pageSubtitle = new Label("Monitor administrator activities and system events across the LifeLink ecosystem.");
        pageSubtitle.setStyle("-fx-font-size: 16px;-fx-text-fill: #434655;");

        headerText.getChildren().addAll(pageTitle, pageSubtitle);
        topHeader.getChildren().add(headerText);

        // =============================================================
        // FILTER AREA
        // =============================================================

        VBox filterCard = new VBox(18);
        filterCard.setPadding(new Insets(20));
        filterCard.setStyle("-fx-background-color: white;-fx-border-color: #e2e8f0;-fx-border-width: 1px;-fx-border-radius: 12px;-fx-background-radius: 12px;");

        Label filterTitle = new Label("Activity Filters");
        filterTitle.setStyle("-fx-font-size: 20px;-fx-font-weight: 600;-fx-text-fill: #191b23;");

        HBox filterRow = new HBox(15);
        filterRow.setAlignment(Pos.CENTER_LEFT);

        VBox searchBox = new VBox(7);
        HBox.setHgrow(searchBox, Priority.ALWAYS);

        Label searchLabel = new Label("SEARCH ACTIVITY");
        searchLabel.setStyle("-fx-font-size: 12px;-fx-font-weight: bold;-fx-text-fill: #434655;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search activity, user or action...");
        searchField.setPrefHeight(40);
        searchField.setStyle("-fx-background-color: #faf8ff;-fx-border-color: #737686;-fx-border-radius: 10px;-fx-background-radius: 10px;-fx-padding: 0px 12px;-fx-font-size: 14px;");

        searchBox.getChildren().addAll(searchLabel, searchField);

        VBox activityTypeBox = new VBox(7);
        HBox.setHgrow(activityTypeBox, Priority.ALWAYS);

        Label activityTypeLabel = new Label("ACTIVITY TYPE");
        activityTypeLabel.setStyle("-fx-font-size: 12px;-fx-font-weight: bold;-fx-text-fill: #434655;");

        ComboBox<String> activityTypeCombo = new ComboBox<>();
        activityTypeCombo.getItems().addAll("All Activities", "Login", "Logout", "Hospital", "Ambulance", "User Management", "Settings");
        activityTypeCombo.setValue("All Activities");
        activityTypeCombo.setMaxWidth(Double.MAX_VALUE);
        activityTypeCombo.setPrefHeight(40);

        activityTypeBox.getChildren().addAll(activityTypeLabel, activityTypeCombo);

        VBox dateBox = new VBox(7);
        HBox.setHgrow(dateBox, Priority.ALWAYS);

        Label dateLabel = new Label("DATE");
        dateLabel.setStyle("-fx-font-size: 12px;-fx-font-weight: bold;-fx-text-fill: #434655;");

        ComboBox<String> dateCombo = new ComboBox<>();
        dateCombo.getItems().addAll("All Time", "Today", "Yesterday", "Last 7 Days", "Last 30 Days");
        dateCombo.setValue("Today");
        dateCombo.setMaxWidth(Double.MAX_VALUE);
        dateCombo.setPrefHeight(40);

        dateBox.getChildren().addAll(dateLabel, dateCombo);

        Button filterButton = new Button("Apply Filters");
        filterButton.setPadding(new Insets(11, 20, 11, 20));
        filterButton.setStyle("-fx-background-color: #2563eb;-fx-text-fill: white;-fx-font-weight: bold;-fx-background-radius: 10px;");

        filterRow.getChildren().addAll(searchBox, activityTypeBox, dateBox, filterButton);

        filterCard.getChildren().addAll(filterTitle, filterRow);

        // =============================================================
        // ACTIVITY SUMMARY
        // =============================================================

        HBox summaryRow = new HBox(15);

        VBox totalActivities = new VBox(5);
        totalActivities.setPadding(new Insets(18));
        totalActivities.setStyle("-fx-background-color: white;-fx-border-color: #e2e8f0;-fx-border-width: 1px;-fx-border-radius: 12px;-fx-background-radius: 12px;");
        HBox.setHgrow(totalActivities, Priority.ALWAYS);

        Label totalLabel = new Label("TOTAL ACTIVITIES");
        totalLabel.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-text-fill: #434655;");

        Label totalValue = new Label("1,284");
        totalValue.setStyle("-fx-font-size: 24px;-fx-font-weight: bold;-fx-text-fill: #2563eb;");

        Label totalSubtitle = new Label("Recorded today");
        totalSubtitle.setStyle("-fx-font-size: 12px;-fx-text-fill: #737686;");

        totalActivities.getChildren().addAll(totalLabel, totalValue, totalSubtitle);

        VBox successfulActivities = new VBox(5);
        successfulActivities.setPadding(new Insets(18));
        successfulActivities.setStyle("-fx-background-color: white;-fx-border-color: #e2e8f0;-fx-border-width: 1px;-fx-border-radius: 12px;-fx-background-radius: 12px;");
        HBox.setHgrow(successfulActivities, Priority.ALWAYS);

        Label successLabel = new Label("SUCCESSFUL ACTIONS");
        successLabel.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-text-fill: #434655;");

        Label successValue = new Label("1,241");
        successValue.setStyle("-fx-font-size: 24px;-fx-font-weight: bold;-fx-text-fill: #16a34a;");

        Label successSubtitle = new Label("96.6% success rate");
        successSubtitle.setStyle("-fx-font-size: 12px;-fx-text-fill: #737686;");

        successfulActivities.getChildren().addAll(successLabel, successValue, successSubtitle);

        VBox failedActivities = new VBox(5);
        failedActivities.setPadding(new Insets(18));
        failedActivities.setStyle("-fx-background-color: white;-fx-border-color: #e2e8f0;-fx-border-width: 1px;-fx-border-radius: 12px;-fx-background-radius: 12px;");
        HBox.setHgrow(failedActivities, Priority.ALWAYS);

        Label failedLabel = new Label("FAILED ACTIONS");
        failedLabel.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-text-fill: #434655;");

        Label failedValue = new Label("43");
        failedValue.setStyle("-fx-font-size: 24px;-fx-font-weight: bold;-fx-text-fill: #dc2626;");

        Label failedSubtitle = new Label("Requires attention");
        failedSubtitle.setStyle("-fx-font-size: 12px;-fx-text-fill: #737686;");

        failedActivities.getChildren().addAll(failedLabel, failedValue, failedSubtitle);

        VBox activeUsers = new VBox(5);
        activeUsers.setPadding(new Insets(18));
        activeUsers.setStyle("-fx-background-color: white;-fx-border-color: #e2e8f0;-fx-border-width: 1px;-fx-border-radius: 12px;-fx-background-radius: 12px;");
        HBox.setHgrow(activeUsers, Priority.ALWAYS);

        Label activeLabel = new Label("ACTIVE ADMINS");
        activeLabel.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-text-fill: #434655;");

        Label activeValue = new Label("18");
        activeValue.setStyle("-fx-font-size: 24px;-fx-font-weight: bold;-fx-text-fill: #2563eb;");

        Label activeSubtitle = new Label("Currently online");
        activeSubtitle.setStyle("-fx-font-size: 12px;-fx-text-fill: #737686;");

        activeUsers.getChildren().addAll(activeLabel, activeValue, activeSubtitle);

        summaryRow.getChildren().addAll(
                totalActivities,
                successfulActivities,
                failedActivities,
                activeUsers
        );

        // =============================================================
        // ACTIVITY LOG CARD
        // =============================================================

        VBox activityCard = new VBox();

        activityCard.setStyle("-fx-background-color: white;-fx-border-color: #e2e8f0;-fx-border-width: 1px;-fx-border-radius: 12px;-fx-background-radius: 12px;");

        VBox activityHeader = new VBox(5);
        activityHeader.setPadding(new Insets(20));
        activityHeader.setStyle("-fx-border-color: #c3c6d7;-fx-border-width: 0px 0px 1px 0px;");

        Label activityTitle = new Label("Recent Activity");
        activityTitle.setStyle("-fx-font-size: 20px;-fx-font-weight: 600;-fx-text-fill: #191b23;");

        Label activitySubtitle = new Label("Latest actions performed by administrators and system users.");
        activitySubtitle.setStyle("-fx-font-size: 14px;-fx-text-fill: #434655;");

        activityHeader.getChildren().addAll(
                activityTitle,
                activitySubtitle
        );

        // =============================================================
        // TABLE HEADER
        // =============================================================

        HBox tableHeader = new HBox();
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setPadding(new Insets(14, 20, 14, 20));
        tableHeader.setStyle("-fx-background-color: #f3f3fe;");

        Label timeHeader = new Label("TIME");
        timeHeader.setPrefWidth(130);
        timeHeader.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-text-fill: #434655;");

        Label userHeader = new Label("USER");
        userHeader.setPrefWidth(180);
        userHeader.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-text-fill: #434655;");

        Label actionHeader = new Label("ACTION");
        actionHeader.setPrefWidth(240);
        actionHeader.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-text-fill: #434655;");

        Label moduleHeader = new Label("MODULE");
        moduleHeader.setPrefWidth(170);
        moduleHeader.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-text-fill: #434655;");

        Label statusHeader = new Label("STATUS");
        statusHeader.setPrefWidth(120);
        statusHeader.setStyle("-fx-font-size: 11px;-fx-font-weight: bold;-fx-text-fill: #434655;");

        tableHeader.getChildren().addAll(
                timeHeader,
                userHeader,
                actionHeader,
                moduleHeader,
                statusHeader
        );

        // =============================================================
        // ACTIVITY ROW 1
        // =============================================================

        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER_LEFT);
        row1.setPadding(new Insets(16, 20, 16, 20));
        row1.setStyle("-fx-border-color: #e2e8f0;-fx-border-width: 0px 0px 1px 0px;");

        Label time1 = new Label("01:02 PM");
        time1.setPrefWidth(130);
        time1.setStyle("-fx-font-size: 13px;-fx-text-fill: #737686;");

        Label user1 = new Label("Admin");
        user1.setPrefWidth(180);
        user1.setStyle("-fx-font-size: 13px;-fx-font-weight: bold;-fx-text-fill: #191b23;");

        Label action1 = new Label("Updated system settings");
        action1.setPrefWidth(240);
        action1.setStyle("-fx-font-size: 13px;-fx-text-fill: #434655;");

        Label module1 = new Label("System Settings");
        module1.setPrefWidth(170);
        module1.setStyle("-fx-font-size: 13px;-fx-text-fill: #434655;");

        Label status1 = new Label("SUCCESS");
        status1.setPrefWidth(120);
        status1.setStyle("-fx-background-color: #dcfce7;-fx-text-fill: #15803d;-fx-font-size: 11px;-fx-font-weight: bold;-fx-padding: 5px 10px;-fx-background-radius: 8px;");

        row1.getChildren().addAll(
                time1,
                user1,
                action1,
                module1,
                status1
        );

        // =============================================================
        // ACTIVITY ROW 2
        // =============================================================

        HBox row2 = new HBox();
        row2.setAlignment(Pos.CENTER_LEFT);
        row2.setPadding(new Insets(16, 20, 16, 20));
        row2.setStyle("-fx-border-color: #e2e8f0;-fx-border-width: 0px 0px 1px 0px;");

        Label time2 = new Label("12:48 PM");
        time2.setPrefWidth(130);
        time2.setStyle("-fx-font-size: 13px;-fx-text-fill: #737686;");

        Label user2 = new Label("Admin");
        user2.setPrefWidth(180);
        user2.setStyle("-fx-font-size: 13px;-fx-font-weight: bold;-fx-text-fill: #191b23;");

        Label action2 = new Label("Added new hospital");
        action2.setPrefWidth(240);
        action2.setStyle("-fx-font-size: 13px;-fx-text-fill: #434655;");

        Label module2 = new Label("Hospital Management");
        module2.setPrefWidth(170);
        module2.setStyle("-fx-font-size: 13px;-fx-text-fill: #434655;");

        Label status2 = new Label("SUCCESS");
        status2.setPrefWidth(120);
        status2.setStyle("-fx-background-color: #dcfce7;-fx-text-fill: #15803d;-fx-font-size: 11px;-fx-font-weight: bold;-fx-padding: 5px 10px;-fx-background-radius: 8px;");

        row2.getChildren().addAll(
                time2,
                user2,
                action2,
                module2,
                status2
        );

        // =============================================================
        // ACTIVITY ROW 3
        // =============================================================

        HBox row3 = new HBox();
        row3.setAlignment(Pos.CENTER_LEFT);
        row3.setPadding(new Insets(16, 20, 16, 20));
        row3.setStyle("-fx-border-color: #e2e8f0;-fx-border-width: 0px 0px 1px 0px;");

        Label time3 = new Label("12:35 PM");
        time3.setPrefWidth(130);
        time3.setStyle("-fx-font-size: 13px;-fx-text-fill: #737686;");

        Label user3 = new Label("System Admin");
        user3.setPrefWidth(180);
        user3.setStyle("-fx-font-size: 13px;-fx-font-weight: bold;-fx-text-fill: #191b23;");

        Label action3 = new Label("Updated ambulance status");
        action3.setPrefWidth(240);
        action3.setStyle("-fx-font-size: 13px;-fx-text-fill: #434655;");

        Label module3 = new Label("Ambulance Management");
        module3.setPrefWidth(170);
        module3.setStyle("-fx-font-size: 13px;-fx-text-fill: #434655;");

        Label status3 = new Label("SUCCESS");
        status3.setPrefWidth(120);
        status3.setStyle("-fx-background-color: #dcfce7;-fx-text-fill: #15803d;-fx-font-size: 11px;-fx-font-weight: bold;-fx-padding: 5px 10px;-fx-background-radius: 8px;");

        row3.getChildren().addAll(
                time3,
                user3,
                action3,
                module3,
                status3
        );

        // =============================================================
        // ACTIVITY ROW 4
        // =============================================================

        HBox row4 = new HBox();
        row4.setAlignment(Pos.CENTER_LEFT);
        row4.setPadding(new Insets(16, 20, 16, 20));
        row4.setStyle("-fx-border-color: #e2e8f0;-fx-border-width: 0px 0px 1px 0px;");

        Label time4 = new Label("11:56 AM");
        time4.setPrefWidth(130);
        time4.setStyle("-fx-font-size: 13px;-fx-text-fill: #737686;");

        Label user4 = new Label("Admin");
        user4.setPrefWidth(180);
        user4.setStyle("-fx-font-size: 13px;-fx-font-weight: bold;-fx-text-fill: #191b23;");

        Label action4 = new Label("Viewed emergency report");
        action4.setPrefWidth(240);
        action4.setStyle("-fx-font-size: 13px;-fx-text-fill: #434655;");

        Label module4 = new Label("Emergency Monitoring");
        module4.setPrefWidth(170);
        module4.setStyle("-fx-font-size: 13px;-fx-text-fill: #434655;");

        Label status4 = new Label("SUCCESS");
        status4.setPrefWidth(120);
        status4.setStyle("-fx-background-color: #dcfce7;-fx-text-fill: #15803d;-fx-font-size: 11px;-fx-font-weight: bold;-fx-padding: 5px 10px;-fx-background-radius: 8px;");

        row4.getChildren().addAll(
                time4,
                user4,
                action4,
                module4,
                status4
        );

        // =============================================================
        // ACTIVITY ROW 5
        // =============================================================

        HBox row5 = new HBox();
        row5.setAlignment(Pos.CENTER_LEFT);
        row5.setPadding(new Insets(16, 20, 16, 20));
        row5.setStyle("-fx-border-color: #e2e8f0;-fx-border-width: 0px 0px 1px 0px;");

        Label time5 = new Label("11:24 AM");
        time5.setPrefWidth(130);
        time5.setStyle("-fx-font-size: 13px;-fx-text-fill: #737686;");

        Label user5 = new Label("Admin");
        user5.setPrefWidth(180);
        user5.setStyle("-fx-font-size: 13px;-fx-font-weight: bold;-fx-text-fill: #191b23;");

        Label action5 = new Label("Created new user account");
        action5.setPrefWidth(240);
        action5.setStyle("-fx-font-size: 13px;-fx-text-fill: #434655;");

        Label module5 = new Label("User Management");
        module5.setPrefWidth(170);
        module5.setStyle("-fx-font-size: 13px;-fx-text-fill: #434655;");

        Label status5 = new Label("SUCCESS");
        status5.setPrefWidth(120);
        status5.setStyle("-fx-background-color: #dcfce7;-fx-text-fill: #15803d;-fx-font-size: 11px;-fx-font-weight: bold;-fx-padding: 5px 10px;-fx-background-radius: 8px;");

        row5.getChildren().addAll(
                time5,
                user5,
                action5,
                module5,
                status5
        );

        VBox activityRows = new VBox();

        activityRows.getChildren().addAll(
                tableHeader,
                row1,
                row2,
                row3,
                row4,
                row5
        );

        activityCard.getChildren().addAll(
                activityHeader,
                activityRows
        );

        // =============================================================
        // FOOTER
        // =============================================================

        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(10, 0, 10, 0));

        Label footerText = new Label("Showing 5 of 1,284 activities");
        footerText.setStyle("-fx-font-size: 13px;-fx-text-fill: #737686;");

        Region footerSpacer = new Region();
        HBox.setHgrow(footerSpacer, Priority.ALWAYS);

        Button previousButton = new Button("Previous");
        previousButton.setPadding(new Insets(9, 16, 9, 16));
        previousButton.setStyle("-fx-background-color: #e1e2ed;-fx-text-fill: #434655;-fx-font-weight: bold;-fx-background-radius: 8px;");

        Button nextButton = new Button("Next");
        nextButton.setPadding(new Insets(9, 16, 9, 16));
        nextButton.setStyle("-fx-background-color: #2563eb;-fx-text-fill: white;-fx-font-weight: bold;-fx-background-radius: 8px;");

        footer.getChildren().addAll(
                footerText,
                footerSpacer,
                previousButton,
                nextButton
        );

        // =============================================================
        // ADD EVERYTHING TO MAIN CONTENT
        // =============================================================

        mainContent.getChildren().addAll(
                topHeader,
                filterCard,
                summaryRow,
                activityCard,
                footer
        );

        // =============================================================
        // SCROLL PANE
        // =============================================================

        ScrollPane scrollPane = new ScrollPane(mainContent);

        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;-fx-background: transparent;");

        // =============================================================
        // FINAL CONTENT
        // =============================================================

        VBox finalContent = new VBox(scrollPane);

        finalContent.setStyle("-fx-background-color: #f8f8ff;");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return finalContent;
    }
}