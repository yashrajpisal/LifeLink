package com.kurukshetra.view.police;

import com.kurukshetra.dao.admin.AdminSideEmgReqDao;
import com.kurukshetra.model.admin.AdminSideEmgReqModel;
import com.google.cloud.Timestamp;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class PoliceHistory {

    private static final String BG_SURFACE = "#faf8ff";
    private static final String PRIMARY_COLOR = "#006591";
    private static final String ON_SURFACE = "#131b2e";
    private static final String ON_SURFACE_VARIANT = "#3e4850";
    private static final String OUTLINE_VARIANT = "#bec8d2";
    private static final String CARD_BG = "#ffffff";

    private VBox listContainer;
    private Text loadingText;

    public VBox getHistoryVBox() {

        VBox historyPage = new VBox(24);
        historyPage.setPadding(new Insets(40, 50, 40, 50));
        historyPage.setStyle("-fx-background-color:" + BG_SURFACE + ";");

        Text heading = new Text("Ambulance History");
        heading.setStyle("-fx-font-size:30px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");

        Text subHeading = new Text("View ambulances that used police facilities");
        subHeading.setStyle("-fx-font-size:15px; -fx-fill:" + ON_SURFACE_VARIANT + ";");

        VBox headingBox = new VBox(7, heading, subHeading);

        HBox filterBox = new HBox(15);
        filterBox.setAlignment(Pos.CENTER_LEFT);
        filterBox.setPadding(new Insets(18));
        filterBox.setStyle("-fx-background-color:" + CARD_BG + "; -fx-background-radius:16px; -fx-border-color:" + OUTLINE_VARIANT + "; -fx-border-radius:16px;");

        Text dateText = new Text("Select Date");
        dateText.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(190);
        datePicker.setStyle("-fx-background-radius:10px;");

        Button todayButton = new Button("Today");
        todayButton.setPrefWidth(90);
        todayButton.setPrefHeight(36);
        todayButton.setStyle("-fx-background-color:" + PRIMARY_COLOR + "; -fx-text-fill:white; -fx-font-size:13px; -fx-font-weight:bold; -fx-background-radius:10px;");
        todayButton.setOnAction(event -> datePicker.setValue(java.time.LocalDate.now()));

        filterBox.getChildren().addAll(dateText, datePicker, todayButton);

        listContainer = new VBox(15);
        
        loadingText = new Text("Loading history from database...");
        loadingText.setStyle("-fx-font-size: 15px; -fx-fill: " + ON_SURFACE_VARIANT + "; -fx-font-style: italic;");
        listContainer.getChildren().add(loadingText);

        ScrollPane scrollPane = new ScrollPane(listContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        historyPage.getChildren().addAll(headingBox, filterBox, scrollPane);

        loadHistoryData();

        return historyPage;
    }

    private void loadHistoryData() {
        AdminSideEmgReqDao dao = new AdminSideEmgReqDao();
        
        // Attempt to fetch from Firestore
        Thread fetchThread = new Thread(() -> {
            try {
                List<AdminSideEmgReqModel> requests = dao.fetchAllEmergencyRequests();
                if (requests != null && !requests.isEmpty()) {
                    Platform.runLater(() -> populateList(requests));
                }
            } catch (Exception e) {
                System.err.println("[PoliceHistory] Failed to load history: " + e.getMessage());
            }
        });
        fetchThread.setDaemon(true);
        fetchThread.start();


    }

    private void populateList(List<AdminSideEmgReqModel> requests) {
        listContainer.getChildren().clear();

        boolean found = false;
        for (AdminSideEmgReqModel req : requests) {
            // Only show processed or completed requests in history
            if ("COMPLETED".equals(req.getStatus()) || "CLEARANCE_ACTIVE".equals(req.getStatus()) || "EN_ROUTE".equals(req.getStatus())) {
                listContainer.getChildren().add(createHistoryCard(req));
                found = true;
            }
        }

        if (!found) {
            Text emptyText = new Text("No history available.");
            emptyText.setStyle("-fx-font-size: 15px; -fx-fill: " + ON_SURFACE_VARIANT + "; -fx-font-style: italic;");
            listContainer.getChildren().add(emptyText);
        }
    }

    private HBox createHistoryCard(AdminSideEmgReqModel req) {
        HBox card = new HBox(20);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(16));
        card.setStyle("-fx-background-color:" + CARD_BG + "; -fx-background-radius:14px; -fx-border-color:" + OUTLINE_VARIANT + "; -fx-border-radius:14px; -fx-border-width:1px;");

        VBox unitBox = new VBox(5);
        Text unit = new Text(req.getTripID());
        unit.setStyle("-fx-font-size:15px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");
        Text driver = new Text("Driver: " + req.getDriverID());
        driver.setStyle("-fx-font-size:12px; -fx-fill:" + ON_SURFACE_VARIANT + ";");
        unitBox.getChildren().addAll(unit, driver);

        VBox timeBox = new VBox(5);
        Text dateText = new Text(req.getTimestamp() != null ? req.getTimestamp().toDate().toString() : "N/A");
        dateText.setStyle("-fx-font-size:12px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");
        Text severity = new Text("Severity: " + req.getSeverity());
        severity.setStyle("-fx-font-size:12px; -fx-fill:" + ON_SURFACE_VARIANT + ";");
        timeBox.getChildren().addAll(dateText, severity);

        VBox routeBox = new VBox(5);
        Text route = new Text(req.getSource() + " → " + req.getDestination());
        route.setStyle("-fx-font-size:13px; -fx-fill:" + ON_SURFACE + ";");
        Text facility = new Text("Route Clearance");
        facility.setStyle("-fx-font-size:12px; -fx-fill:" + ON_SURFACE_VARIANT + ";");
        routeBox.getChildren().addAll(route, facility);

        VBox hospitalBox = new VBox(5);
        Text hospital = new Text(req.getDestination());
        hospital.setStyle("-fx-font-size:13px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");
        
        Text status = new Text(req.getStatus());
        String statusColor = "COMPLETED".equals(req.getStatus()) ? "#16803c" : "#dc2626";
        status.setStyle("-fx-font-size:11px; -fx-font-weight:bold; -fx-fill:" + statusColor + ";");
        hospitalBox.getChildren().addAll(hospital, status);

        HBox.setHgrow(unitBox, Priority.ALWAYS);
        HBox.setHgrow(timeBox, Priority.ALWAYS);
        HBox.setHgrow(routeBox, Priority.ALWAYS);
        HBox.setHgrow(hospitalBox, Priority.ALWAYS);

        card.getChildren().addAll(unitBox, timeBox, routeBox, hospitalBox);
        return card;
    }
}