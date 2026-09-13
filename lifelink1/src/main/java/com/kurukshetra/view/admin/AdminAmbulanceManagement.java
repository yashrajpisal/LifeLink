package com.kurukshetra.view.admin;

import com.kurukshetra.view.util.ShimmerLoader;
import com.kurukshetra.view.util.ShimmerLoader.ShimmerPane;
import com.kurukshetra.controller.AdminSideEmgReqController;
import com.kurukshetra.model.AdminSideEmgReqModel;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class AdminAmbulanceManagement {

        private final AdminSideEmgReqController emgReqController = new AdminSideEmgReqController();
        private WebEngine ambulanceMapEngine;

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
    private static final String PURPLE_VARIANT = "#E9D5FF";

    private static final String SUCCESS_TEXT = "#15803D";
    private static final String SUCCESS_BG = "#DCFCE7";

    private static final String WARNING_TEXT = "#A16207";
    private static final String WARNING_BG = "#FEF3C7";

    private static final String DANGER_TEXT = "#E66A7A";
    private static final String DANGER_BG = "#FDE7EB";
    private static final String DANGER_BORDER = "#FCCED5";

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
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10px;" +
            "-fx-effect: dropshadow(gaussian, rgba(192, 132, 252, 0.35), 10, 0.2, 0, 3);" +
            "-fx-cursor: hand;";

    private static final String SECONDARY_BUTTON_STYLE = FONT_STACK +
            "-fx-background-color: " + BG_SURFACE + ";" +
            "-fx-text-fill: " + PURPLE_DARK + ";" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-color: " + BORDER_COLOR + ";" +
            "-fx-border-radius: 10px;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;";

    public VBox getAmbulanceManagement() {

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(25));
        mainContent.setStyle("-fx-background-color: " + BG_PAGE + ";");

        // HEADER
        Text heading = new Text("Ambulance Fleet Management");
        heading.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text subHeading = new Text("Real-time oversight of critical emergency transport assets.");
        subHeading.setStyle(FONT_STACK + "-fx-font-size: 14px; -fx-fill: " + TEXT_SECONDARY + ";");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(
                heading,
                subHeading
        );

        Button assignDriverButton = new Button("＋   Assign Driver");
        assignDriverButton.setPrefWidth(140);
        assignDriverButton.setPrefHeight(42);
        assignDriverButton.setStyle(SECONDARY_BUTTON_STYLE);

        Button registerAmbulanceButton = new Button("＋   Register Ambulance");
        registerAmbulanceButton.setPrefWidth(165);
        registerAmbulanceButton.setPrefHeight(42);
        registerAmbulanceButton.setStyle(PRIMARY_BUTTON_STYLE);

        HBox headerButtons = new HBox(10);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);
        headerButtons.getChildren().addAll(
                assignDriverButton,
                registerAmbulanceButton
        );

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox header = new HBox(
                headingBox,
                headerSpacer,
                headerButtons
        );
        header.setAlignment(Pos.CENTER_LEFT);


        
        // METRICS ROW
        HBox metricsRow = new HBox(15);
        metricsRow.setAlignment(Pos.CENTER);

        VBox totalFleetBox = new VBox(7);
        totalFleetBox.setPadding(new Insets(18));
        totalFleetBox.setPrefHeight(115);
        totalFleetBox.setStyle(BASE_CARD_STYLE);

        Text totalFleetTitle = new Text("TOTAL FLEET");
        totalFleetTitle.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + TEXT_SECONDARY + ";");

        Text totalFleetValue = new Text("42");
        totalFleetValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text totalFleetInfo = new Text("↗  +2");
        totalFleetInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + SUCCESS_TEXT + ";");

        totalFleetBox.getChildren().addAll(
                totalFleetTitle,
                totalFleetValue,
                totalFleetInfo
        );

        VBox activeCallsBox = new VBox(7);
        activeCallsBox.setPadding(new Insets(18));
        activeCallsBox.setPrefHeight(115);
        activeCallsBox.setStyle(BASE_CARD_STYLE);

        Text activeCallsTitle = new Text("ACTIVE CALLS");
        activeCallsTitle.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + TEXT_SECONDARY + ";");

        Text activeCallsValue = new Text("18");
        activeCallsValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");

        Text activeCallsInfo = new Text("On Mission");
        activeCallsInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + TEXT_MUTED + ";");

        activeCallsBox.getChildren().addAll(
                activeCallsTitle,
                activeCallsValue,
                activeCallsInfo
        );

        VBox unavailableBox = new VBox(7);
        unavailableBox.setPadding(new Insets(18));
        unavailableBox.setPrefHeight(115);
        unavailableBox.setStyle(FONT_STACK +
                "-fx-background-color: " + DANGER_BG + ";" +
                "-fx-background-radius: 16px;" +
                "-fx-border-color: " + DANGER_BORDER + ";" +
                "-fx-border-radius: 16px;" +
                "-fx-border-width: 1px;" +
                "-fx-effect: dropshadow(gaussian, rgba(230, 106, 122, 0.10), 16, 0.1, 0, 4);");

        Text unavailableTitle = new Text("UNAVAILABLE");
        unavailableTitle.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + DANGER_TEXT + ";");

        Text unavailableValue = new Text("5");
        unavailableValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + DANGER_TEXT + ";");

        Text unavailableInfo = new Text("Maintenance");
        unavailableInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + DANGER_TEXT + ";");

        unavailableBox.getChildren().addAll(
                unavailableTitle,
                unavailableValue,
                unavailableInfo
        );

        VBox responseTimeBox = new VBox(7);
        responseTimeBox.setPadding(new Insets(18));
        responseTimeBox.setPrefHeight(115);
        responseTimeBox.setStyle(BASE_CARD_STYLE);

        Text responseTimeTitle = new Text("AVG RESPONSE TIME");
        responseTimeTitle.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + TEXT_SECONDARY + ";");

        Text responseTimeValue = new Text("8.4 m");
        responseTimeValue.setStyle(FONT_STACK + "-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text responseTimeInfo = new Text("✓  Target Met");
        responseTimeInfo.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + SUCCESS_TEXT + ";");

        responseTimeBox.getChildren().addAll(
                responseTimeTitle,
                responseTimeValue,
                responseTimeInfo
        );

        HBox.setHgrow(totalFleetBox, Priority.ALWAYS);
        HBox.setHgrow(activeCallsBox, Priority.ALWAYS);
        HBox.setHgrow(unavailableBox, Priority.ALWAYS);
        HBox.setHgrow(responseTimeBox, Priority.ALWAYS);

        // metricsRow.getChildren().addAll(
        //         totalFleetBox,
        //         activeCallsBox,
        //         unavailableBox,
        //         responseTimeBox
        // );

        // MAIN AREA
        HBox mainArea = new HBox(20);

        VBox tableCard = new VBox();
        tableCard.setStyle(BASE_CARD_STYLE);

        VBox tableTitleBox = new VBox(4);
        tableTitleBox.setPadding(new Insets(18));

        Text tableTitle = new Text("Vehicle Status Registry");
        tableTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Text tableInfo = new Text("Monitor ambulance vehicles and driver status");
        tableInfo.setStyle(FONT_STACK + "-fx-font-size: 12px; -fx-fill: " + TEXT_MUTED + ";");

        tableTitleBox.getChildren().addAll(
                tableTitle,
                tableInfo
        );

        TextField searchField = new TextField();
        searchField.setPromptText("Search vehicle or driver...");
        searchField.setPrefWidth(230);
        searchField.setPrefHeight(38);
        searchField.setStyle(FONT_STACK +
                "-fx-background-color: #FAF8FF;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 20px;" +
                "-fx-background-radius: 20px;" +
                "-fx-padding: 0px 14px;" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";");

        Region tableHeaderSpacer = new Region();
        HBox.setHgrow(tableHeaderSpacer, Priority.ALWAYS);

        HBox tableTop = new HBox(
                tableTitleBox,
                tableHeaderSpacer,
                searchField
        );
        tableTop.setPadding(new Insets(5, 18, 5, 5));
        tableTop.setAlignment(Pos.CENTER_LEFT);

        // TABLE HEADER
        HBox tableHeader = new HBox(10);
        tableHeader.setPadding(new Insets(12, 18, 12, 18));
        tableHeader.setAlignment(Pos.CENTER_LEFT);
        tableHeader.setStyle("-fx-background-color: " + PURPLE_LIGHT + ";");

        Text ambulanceHeader = new Text("AMBULANCE NO.");
        ambulanceHeader.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        ambulanceHeader.setWrappingWidth(125);

        Text driverHeader = new Text("DRIVER");
        driverHeader.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        driverHeader.setWrappingWidth(120);

        Text statusHeader = new Text("STATUS");
        statusHeader.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        statusHeader.setWrappingWidth(110);

        Text etaHeader = new Text("ROUTE");
        etaHeader.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        etaHeader.setWrappingWidth(180);

        Text actionHeader = new Text("ACTION");
        actionHeader.setStyle(FONT_STACK + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + PURPLE_DARK + ";");
        actionHeader.setWrappingWidth(60);

        tableHeader.getChildren().addAll(
                ambulanceHeader,
                driverHeader,
                statusHeader,
                etaHeader,
                actionHeader
        );

        String rowBorderStyle = "-fx-border-color: " + BORDER_DIVIDER + "; -fx-border-width: 0px 0px 1px 0px;";

        VBox tableRowsBox = new VBox();

        List<AdminSideEmgReqModel> currentRequests = new java.util.ArrayList<>();

        TextArea detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setWrapText(true);
        detailsArea.setPrefHeight(180);
        detailsArea.setPromptText("Select an ambulance to view its details...");
        detailsArea.setStyle(
                FONT_STACK +
                "-fx-control-inner-background: " + BG_SURFACE + ";" +
                "-fx-background-color: " + BG_SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                "-fx-padding: 10px;"
        );

        Text selectedDetailsTitle = new Text("Selected Ambulance Details");
        selectedDetailsTitle.setStyle(
                FONT_STACK +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

         emgReqController.subscribeToLiveRequests(requests -> {

                // currentRequests.clear();
                // currentRequests.addAll(requests);

                Platform.runLater(() -> {

                        tableRowsBox.getChildren().clear();

                        for (AdminSideEmgReqModel request : requests) {

                        HBox ambulanceRow = new HBox(10);
                        ambulanceRow.setPadding(new Insets(14, 18, 14, 18));
                        ambulanceRow.setAlignment(Pos.CENTER_LEFT);
                        ambulanceRow.setStyle(rowBorderStyle);

                        Circle ambulanceCircle = new Circle(18);
                        ambulanceCircle.setFill(Color.web(PURPLE_LIGHT));

                        Text ambulanceIcon = new Text("🚑");
                        ambulanceIcon.setStyle("-fx-font-size: 15px;");

                        StackPane ambulanceIconPane = new StackPane();
                        ambulanceIconPane.setPrefSize(36, 36);
                        ambulanceIconPane.getChildren().addAll(
                                ambulanceCircle,
                                ambulanceIcon
                        );

                        Text ambulanceNumber = new Text(
                                request.getTripID() != null ? request.getTripID() : "N/A"
                        );
                        ambulanceNumber.setStyle(
                                FONT_STACK +
                                "-fx-font-size: 12px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-fill: " + TEXT_PRIMARY + ";"
                        );

                        HBox ambulanceNumberBox = new HBox(8);
                        ambulanceNumberBox.setPrefWidth(125);
                        ambulanceNumberBox.setAlignment(Pos.CENTER_LEFT);
                        ambulanceNumberBox.getChildren().addAll(
                                ambulanceIconPane,
                                ambulanceNumber
                        );

                        Text driver = new Text(
                                request.getDriverID() != null ? request.getDriverID() : "Unassigned"
                        );
                        driver.setStyle(
                                FONT_STACK +
                                "-fx-font-size: 12px; " +
                                "-fx-fill: " + TEXT_SECONDARY + ";"
                        );

                        HBox driverBox = new HBox(driver);
                        driverBox.setPrefWidth(120);
                        driverBox.setAlignment(Pos.CENTER_LEFT);

                        String statusValue = request.getStatus() != null
                                ? request.getStatus()
                                : "UNKNOWN";

                        Label status = new Label(statusValue);
                        status.setStyle(
                                FONT_STACK +
                                "-fx-background-color: " + SUCCESS_BG + ";" +
                                "-fx-text-fill: " + SUCCESS_TEXT + ";" +
                                "-fx-font-size: 9px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 12px;" +
                                "-fx-padding: 4px 8px;"
                        );

                        HBox statusBox = new HBox(status);
                        statusBox.setPrefWidth(110);
                        statusBox.setAlignment(Pos.CENTER_LEFT);

                        String source = request.getSource() != null
                                ? request.getSource()
                                : "Unknown";

                        String destination = request.getDestination() != null
                                ? request.getDestination()
                                : "Unknown";

                        Text route = new Text(source + " → " + destination);
                        route.setStyle(
                                FONT_STACK +
                                "-fx-font-size: 11px; " +
                                "-fx-fill: " + TEXT_PRIMARY + ";"
                        );

                        HBox routeBox = new HBox(route);
                        routeBox.setPrefWidth(180);
                        routeBox.setAlignment(Pos.CENTER_LEFT);

                        Button actionButton = new Button("View");
                        actionButton.setPrefSize(32, 32);
                        actionButton.setStyle(
                                FONT_STACK +
                                "-fx-background-color: transparent;" +
                                "-fx-text-fill: " + PURPLE_DARK + ";" +
                                "-fx-font-size: 18px;" +
                                "-fx-cursor: hand;"
                        );

                        actionButton.setOnAction(event -> {

                        String timestamp = request.getTimestamp() != null
                                ? request.getTimestamp().toString()
                                : "N/A";

                        String details =
                                "Trip ID: " + request.getTripID() + "\n" +
                                "Driver ID: " + request.getDriverID() + "\n" +
                                "Nurse ID: " + request.getNurseID() + "\n" +
                                "Patient ID: " + request.getPatID() + "\n" +
                                "Pickup Location: " + request.getSource() + "\n" +
                                "Destination: " + request.getDestination() + "\n" +
                                "Status: " + request.getStatus() + "\n" +
                                "Time: " + timestamp;

                        detailsArea.setText(details);

                        ambulanceMapEngine.executeScript(
                                "setRouteFromSourceToDestination('" +
                                request.getSource() + "','" +
                                request.getDestination() + "')"
                        );
                        });


                        HBox actionBox = new HBox(actionButton);
                        actionBox.setPrefWidth(60);
                        actionBox.setAlignment(Pos.CENTER_LEFT);

                        ambulanceRow.getChildren().addAll(
                                ambulanceNumberBox,
                                driverBox,
                                statusBox,
                                routeBox,
                                actionBox
                        );

                        tableRowsBox.getChildren().add(ambulanceRow);
                        }
                });
                });


                // searchField.textProperty().addListener((observable, oldValue, newValue) -> {

                // String searchText = newValue == null
                //         ? ""
                //         : newValue.trim().toLowerCase();

                // tableRowsBox.getChildren().clear();

                // for (AdminSideEmgReqModel request : currentRequests) {

                //         String tripID = request.getTripID() != null
                //                 ? request.getTripID().toLowerCase()
                //                 : "";

                //         String driverID = request.getDriverID() != null
                //                 ? request.getDriverID().toLowerCase()
                //                 : "";

                //         String status = request.getStatus() != null
                //                 ? request.getStatus().toLowerCase()
                //                 : "";

                //         String source = request.getSource() != null
                //                 ? request.getSource().toLowerCase()
                //                 : "";

                //         String destination = request.getDestination() != null
                //                 ? request.getDestination().toLowerCase()
                //                 : "";

                //         if (tripID.contains(searchText) ||
                //                 driverID.contains(searchText) ||
                //                 status.contains(searchText) ||
                //                 source.contains(searchText) ||
                //                 destination.contains(searchText)) {

                //         // Search will be handled by refreshing the Firestore rows
                //         // when matching requests are found.
                //         }
                // }
                // });
                
        tableCard.getChildren().addAll(
                tableTop,
                tableHeader,
                tableRowsBox,
                selectedDetailsTitle,
                detailsArea
        );  

         

        

         



        // LIVE TRACKING CARD
        VBox trackingCard = new VBox();
        trackingCard.setStyle(BASE_CARD_STYLE);

        HBox trackingHeader = new HBox(10);
        trackingHeader.setPadding(new Insets(16, 18, 16, 18));
        trackingHeader.setAlignment(Pos.CENTER_LEFT);

        Text mapIcon = new Text("⌖");
        mapIcon.setStyle(FONT_STACK + "-fx-font-size: 22px; -fx-fill: " + PURPLE_DARK + ";");

        Text trackingTitle = new Text("Live Tracking");
        trackingTitle.setStyle(FONT_STACK + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Region trackingSpacer = new Region();
        HBox.setHgrow(trackingSpacer, Priority.ALWAYS);

        Circle liveCircle = new Circle(4.5);
        liveCircle.setFill(Color.web("#22c55e"));

        Text liveText = new Text("Live");
        liveText.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + SUCCESS_TEXT + ";");

        HBox liveBox = new HBox(5);
        liveBox.setAlignment(Pos.CENTER);
        liveBox.setStyle("-fx-background-color: " + SUCCESS_BG + "; -fx-padding: 3px 8px; -fx-background-radius: 10px;");
        liveBox.getChildren().addAll(
                liveCircle,
                liveText
        );

        trackingHeader.getChildren().addAll(
                mapIcon,
                trackingTitle,
                trackingSpacer,
                liveBox
        );





        WebView ambulanceMapView = new WebView();
        ambulanceMapView.setPrefHeight(430);
        ambulanceMapView.setMinHeight(430);
        ambulanceMapView.setMaxHeight(430);

        ambulanceMapEngine = ambulanceMapView.getEngine();

        String mapUrl = getClass()
                .getResource("/driver_map.html")
                .toExternalForm();

        ambulanceMapEngine.load(mapUrl);

        StackPane mapPane = new StackPane();
        mapPane.setPrefHeight(430);
        mapPane.getChildren().add(ambulanceMapView);
         

         





        HBox mapFooter = new HBox();
        mapFooter.setPadding(new Insets(12, 18, 12, 18));
        mapFooter.setAlignment(Pos.CENTER_LEFT);
        mapFooter.setStyle("-fx-background-color: " + PURPLE_LIGHT + ";");

        Text districtText = new Text("Emergency Control Center");
        districtText.setStyle(FONT_STACK + "-fx-font-size: 11px; -fx-fill: " + PURPLE_DARK + "; -fx-font-weight: 500;");

        Region footerSpacer = new Region();
        HBox.setHgrow(footerSpacer, Priority.ALWAYS);

        Button fullViewButton = new Button("Full View");
        fullViewButton.setStyle(FONT_STACK + "-fx-background-color: transparent; -fx-text-fill: " + PURPLE_DARK + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-cursor: hand;");

        mapFooter.getChildren().addAll(
                districtText,
                footerSpacer,
                fullViewButton
        );

        trackingCard.getChildren().addAll(
                trackingHeader,
                mapPane,
                mapFooter
        );

        HBox.setHgrow(tableCard, Priority.ALWAYS);
        mainArea.getChildren().addAll(
                tableCard,
                trackingCard
        );

        // ROOT ASSEMBLY
        mainContent.getChildren().addAll(
                header,
                metricsRow,
                mainArea
        );

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        VBox finalContent = new VBox(scrollPane);
        finalContent.setStyle("-fx-background-color: " + BG_PAGE + ";");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return finalContent;
    }
}