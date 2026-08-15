
package com.kurukshetra.view.admin;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class EmergencyMonitoring {

    public VBox getEmergencyMonitoring() {

        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(25));
        mainBox.setStyle("-fx-background-color: #faf8ff;");

        // ---------------------------------------------------------
        // PAGE HEADER
        // ---------------------------------------------------------

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(5);

        Label title = new Label("Emergency Operations Center");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label subtitle = new Label("Real-time monitoring and coordination across regional networks.");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #434655;");

        titleBox.getChildren().addAll(title, subtitle);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Button dispatchButton = new Button("  Dispatch Unit");
        dispatchButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12px 22px; -fx-background-radius: 10px; -fx-cursor: hand;");

        header.getChildren().addAll(titleBox, headerSpacer, dispatchButton);

        // ---------------------------------------------------------
        // CRITICAL EMERGENCY BANNER
        // ---------------------------------------------------------

        HBox criticalBanner = new HBox(12);
        criticalBanner.setAlignment(Pos.CENTER_LEFT);
        criticalBanner.setPadding(new Insets(12, 18, 12, 18));
        criticalBanner.setStyle("-fx-background-color: #ffdad6; -fx-background-radius: 10px; -fx-border-color: #ba1a1a; -fx-border-radius: 10px;");

        Label emergencyIcon = new Label("⚠");
        emergencyIcon.setStyle("-fx-font-size: 22px; -fx-text-fill: #ba1a1a;");

        VBox criticalText = new VBox(3);

        Label criticalTitle = new Label("3 CRITICAL EMERGENCIES");
        criticalTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #93000a;");

        Label criticalSub = new Label("Immediate attention required across regional emergency networks.");
        criticalSub.setStyle("-fx-font-size: 12px; -fx-text-fill: #93000a;");

        criticalText.getChildren().addAll(criticalTitle, criticalSub);
        criticalBanner.getChildren().addAll(emergencyIcon, criticalText);

        // ---------------------------------------------------------
        // LIVE MAP
        // ---------------------------------------------------------

        VBox mapBox = new VBox();

        HBox mapHeader = new HBox();
        mapHeader.setPadding(new Insets(15));
        mapHeader.setAlignment(Pos.CENTER_LEFT);
        mapHeader.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-border-width: 1px 1px 0px 1px; -fx-background-radius: 12px 12px 0px 0px;");

        Label mapTitle = new Label("●  LIVE EMERGENCY MAP");
        mapTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #ba1a1a;");

        mapHeader.getChildren().add(mapTitle);

        VBox mapArea = new VBox();
        mapArea.setPrefHeight(350);
        mapArea.setAlignment(Pos.CENTER);
        mapArea.setStyle("-fx-background-color: #e7e7f3; -fx-border-color: #c3c6d7; -fx-background-radius: 0px 0px 12px 12px;");

        Label mapLabel = new Label("LIVE MAP");
        mapLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #737686;");

        Label mapInfo = new Label("Emergency locations and active ambulances");
        mapInfo.setStyle("-fx-font-size: 13px; -fx-text-fill: #434655;");

        HBox markers = new HBox(30);
        markers.setAlignment(Pos.CENTER);

        VBox criticalMarker = createMapMarker("Critical Incident", "#ba1a1a");
        VBox ambulanceMarker = createMapMarker("Active Ambulance", "#004ac6");

        markers.getChildren().addAll(criticalMarker, ambulanceMarker);

        mapArea.getChildren().addAll(mapLabel, mapInfo, markers);

        mapBox.getChildren().addAll(mapHeader, mapArea);

        // ---------------------------------------------------------
        // ACTIVE EMERGENCIES
        // ---------------------------------------------------------

        VBox emergencyBox = new VBox();

        HBox emergencyHeader = new HBox();
        emergencyHeader.setPadding(new Insets(15));
        emergencyHeader.setAlignment(Pos.CENTER_LEFT);
        emergencyHeader.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-background-radius: 12px 12px 0px 0px;");

        Label activeTitle = new Label("☷  Active Emergencies");
        activeTitle.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Region emergencySpacer = new Region();
        HBox.setHgrow(emergencySpacer, Priority.ALWAYS);

        Button viewAll = new Button("VIEW ALL");
        viewAll.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 11px; -fx-font-weight: bold; -fx-cursor: hand;");

        emergencyHeader.getChildren().addAll(activeTitle, emergencySpacer, viewAll);

        VBox emergencyList = new VBox(1);
        emergencyList.setStyle("-fx-background-color: #c3c6d7;");

        emergencyList.getChildren().addAll(
                createEmergencyRow("#EMG-4921", "Johnathan Reed", "CRITICAL", "UNIT-A42 (Advanced)", "3 MIN", "In Route", true),
                createEmergencyRow("#EMG-4922", "Sarah Jenkins", "MODERATE", "UNIT-B09 (Basic)", "8 MIN", "Dispatched", false),
                createEmergencyRow("#EMG-4925", "Michael Chen", "MINOR", "UNIT-C11 (Basic)", "14 MIN", "Queued", false)
        );

        emergencyBox.getChildren().addAll(emergencyHeader, emergencyList);

        // ---------------------------------------------------------
        // INCIDENT TIMELINE
        // ---------------------------------------------------------

        VBox timelineBox = new VBox();

        HBox timelineHeader = new HBox();
        timelineHeader.setPadding(new Insets(15));
        timelineHeader.setAlignment(Pos.CENTER_LEFT);
        timelineHeader.setStyle("-fx-background-color: #f3f3fe; -fx-border-color: #c3c6d7; -fx-background-radius: 12px 12px 0px 0px;");

        Label timelineTitle = new Label("◷  Incident Timeline");
        timelineTitle.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        timelineHeader.getChildren().add(timelineTitle);

        VBox timelineContent = new VBox(20);
        timelineContent.setPadding(new Insets(20));
        timelineContent.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-background-radius: 0px 0px 12px 12px;");

        timelineContent.getChildren().addAll(
                createTimelineItem("14:02 PM", "Cardiac Arrest Reported", "Caller ID: +1-555-0123. Location: 124th Ave Mall Entrance.", true),
                createTimelineItem("14:04 PM", "Unit A42 Dispatched", "Paramedics Thompson and Garcia assigned.", false),
                createTimelineItem("13:45 PM", "Traffic Accident Cleared", "Case #EMG-4810 marked as completed. Patient stabilized.", false),
                createTimelineItem("13:30 PM", "New Hospital Bed Available", "St. Mary Medical Center: 2 ICU beds cleared.", false),
                createTimelineItem("13:20 PM", "System Update", "Shift change completed for Region 4 Dispatchers.", false)
        );

        Button reportButton = new Button("↓  Generate Log Report");
        reportButton.setMaxWidth(Double.MAX_VALUE);
        reportButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #004ac6; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 12px; -fx-cursor: hand;");

        timelineBox.getChildren().addAll(timelineHeader, timelineContent, reportButton);

        // ---------------------------------------------------------
        // QUICK ACTIONS
        // ---------------------------------------------------------

        VBox actionsBox = new VBox(12);
        actionsBox.setPadding(new Insets(18));
        actionsBox.setStyle("-fx-background-color: white; -fx-border-color: #c3c6d7; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        Label actionTitle = new Label("Quick Actions");
        actionTitle.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Button routeButton = new Button("🚑  Route Closest Unit");
        routeButton.setMaxWidth(Double.MAX_VALUE);
        routeButton.setStyle("-fx-background-color: #004ac6; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 12px; -fx-background-radius: 8px;");

        Button alertButton = new Button("⚠  Send Emergency Alert");
        alertButton.setMaxWidth(Double.MAX_VALUE);
        alertButton.setStyle("-fx-background-color: #ffdad6; -fx-text-fill: #93000a; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 12px; -fx-background-radius: 8px;");

        Button ambulanceButton = new Button("🚑  View Ambulances");
        ambulanceButton.setMaxWidth(Double.MAX_VALUE);
        ambulanceButton.setStyle("-fx-background-color: #dbe1ff; -fx-text-fill: #003ea8; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 12px; -fx-background-radius: 8px;");

        actionsBox.getChildren().addAll(actionTitle, routeButton, alertButton, ambulanceButton);

        // ---------------------------------------------------------
        // RIGHT SIDE
        // ---------------------------------------------------------

        VBox rightSide = new VBox(20, timelineBox, actionsBox);
        rightSide.setPrefWidth(350);

        // ---------------------------------------------------------
        // LEFT SIDE
        // ---------------------------------------------------------

        VBox leftSide = new VBox(20, mapBox, emergencyBox);
        HBox.setHgrow(leftSide, Priority.ALWAYS);

        // ---------------------------------------------------------
        // CONTENT AREA
        // ---------------------------------------------------------

        HBox content = new HBox(20, leftSide, rightSide);
        HBox.setHgrow(leftSide, Priority.ALWAYS);

        // ---------------------------------------------------------
        // CRITICAL ALERT
        // ---------------------------------------------------------

        VBox criticalAlert = new VBox(8);
        criticalAlert.setPadding(new Insets(16));
        criticalAlert.setStyle("-fx-background-color: #ba1a1a; -fx-background-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 12, 0, 0, 4);");

        Label alertTitle = new Label("⚠  Critical: Unassigned Incident");
        alertTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label alertText = new Label("Multi-vehicle collision reported. No units available in Sector 3.");
        alertText.setWrapText(true);
        alertText.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");

        HBox alertButtons = new HBox(10);

        Button routeClosest = new Button("Route Closest Unit");
        routeClosest.setStyle("-fx-background-color: white; -fx-text-fill: #ba1a1a; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 8px 12px; -fx-background-radius: 6px;");

        Button dismiss = new Button("Dismiss");
        dismiss.setStyle("-fx-background-color: transparent; -fx-border-color: white; -fx-border-radius: 6px; -fx-text-fill: white; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 8px 12px;");

        alertButtons.getChildren().addAll(routeClosest, dismiss);

        criticalAlert.getChildren().addAll(alertTitle, alertText, alertButtons);

        // ---------------------------------------------------------
        // ADD EVERYTHING
        // ---------------------------------------------------------

        mainBox.getChildren().addAll(
                header,
                criticalBanner,
                content,
                criticalAlert
        );

        ScrollPane scrollPane = new ScrollPane(mainBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #faf8ff; -fx-border-color: transparent;");

        VBox page = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        page.setStyle("-fx-background-color: #faf8ff;");

        return page;
    }

    // =============================================================
    // MAP MARKER
    // =============================================================

    private VBox createMapMarker(String text, String color) {

        Circle circle = new Circle(7);
        circle.setFill(Color.web(color));

        Label label = new Label(text);
        label.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        HBox row = new HBox(8, circle, label);
        row.setAlignment(Pos.CENTER);

        VBox box = new VBox(row);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-background-radius: 8px;");

        return box;
    }

    // =============================================================
    // EMERGENCY ROW
    // =============================================================

    private HBox createEmergencyRow(
            String id,
            String patient,
            String severity,
            String ambulance,
            String eta,
            String status,
            boolean critical) {

        HBox row = new HBox(15);
        row.setPadding(new Insets(14));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: white;");

        VBox patientBox = new VBox(3);

        Label idLabel = new Label(id);
        idLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #004ac6; -fx-font-weight: bold;");

        Label patientLabel = new Label(patient);
        patientLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        patientBox.getChildren().addAll(idLabel, patientLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label severityLabel = new Label(severity);
        severityLabel.setStyle(critical
                ? "-fx-background-color: #ba1a1a; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 5px 8px; -fx-background-radius: 5px;"
                : "-fx-background-color: #515659; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 5px 8px; -fx-background-radius: 5px;");

        Label ambulanceLabel = new Label(ambulance);
        ambulanceLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

        Label etaLabel = new Label(eta);
        etaLabel.setStyle(critical
                ? "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #ba1a1a;"
                : "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label statusLabel = new Label(status);
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

        row.getChildren().addAll(
                patientBox,
                spacer,
                severityLabel,
                ambulanceLabel,
                etaLabel,
                statusLabel
        );

        return row;
    }

    // =============================================================
    // TIMELINE ITEM
    // =============================================================

    private VBox createTimelineItem(
            String time,
            String title,
            String description,
            boolean critical) {

        VBox item = new VBox(5);
        item.setPadding(new Insets(0, 0, 12, 15));
        item.setStyle("-fx-border-color: #b4c5ff; -fx-border-width: 0px 0px 0px 2px;");

        Label timeLabel = new Label(time);
        timeLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #737686;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #191b23;");

        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #434655;");

        Label priorityLabel = new Label(critical ? "911 CALL   •   PRIORITY 1" : "SYSTEM EVENT");
        priorityLabel.setStyle(critical
                ? "-fx-background-color: #ffdad6; -fx-text-fill: #93000a; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 7px; -fx-background-radius: 5px;"
                : "-fx-background-color: #d3e4fe; -fx-text-fill: #38485d; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 7px; -fx-background-radius: 5px;");

        item.getChildren().addAll(
                timeLabel,
                titleLabel,
                descriptionLabel,
                priorityLabel
        );

        return item;
    }
}