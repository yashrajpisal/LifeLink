package com.kurukshetra.view.nurse;

import com.kurukshetra.config.FirebaseConfig;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.kurukshetra.controller.AdminSideEmgReqController;
import com.kurukshetra.model.AdminSideEmgReqModel;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NurseTripsPage {

    // ================= COLOR PALETTE =================
    private static final String PRIMARY_PINK = "#E67593";
    private static final String PRIMARY_HOVER = "#D95F80";
    private static final String VERY_LIGHT_PINK = "#FDEDF2";
    private static final String LIGHT_PINK = "#F9E0E8";
    private static final String SOFT_PINK = "#F5D1DC";
    private static final String VERY_PALE_PINK = "#FFF9FA";
    private static final String MENU_BG = "#FCDCE5";
    private static final String PAGE_BG = "#f9d6d7";
    private static final String SURFACE = "#FFFFFF";
    private static final String PRIMARY_TEXT = "#2B2226";
    private static final String SECONDARY_TEXT = "#665960";
    private static final String MUTED_TEXT = "#94878D";
    private static final String BORDER_COLOR = "#EEDDE3";
    private static final String DIVIDER_COLOR = "#F3E8EC";

    private final AdminSideEmgReqController emgController = new AdminSideEmgReqController();
    private final Map<String, AdminSideEmgReqModel> tripsCache = new LinkedHashMap<>();
    private final String nurseEmail;

    public NurseTripsPage() {
        this(null);
    }

    public NurseTripsPage(String nurseEmail) {
        this.nurseEmail = (nurseEmail != null && !nurseEmail.trim().isEmpty()) ? nurseEmail.trim() : null;
    }

    public BorderPane tripsRoot;
    public StackPane pageStack;
    public VBox tableBody;
    public VBox emptyState;

    public List<HBox> rowNodes = new ArrayList<>();
    public List<String[]> rowData = new ArrayList<>();

    private Text totalValueText;
    private Text todayValueText;
    private Text completedValueText;
    private Text paginationSummaryText;
    private StackPane shimmerOverlay;
    private Timeline shimmerTimeline;

    private TextField searchField;
    private DatePicker dateFilter;
    private ComboBox<String> statusFilter;

    public static final double COL_SR = 55;
    public static final double COL_TRIP = 110;
    public static final double COL_LOCATION = 270;
    public static final double COL_PATIENT = 105;
    public static final double COL_DATE = 110;
    public static final double COL_TIME = 95;
    public static final double COL_STATUS = 120;
    public static final double COL_ACTION = 115;

    public BorderPane getNurseTripsPage(Runnable callBackDashboard) {
        tripsRoot = new BorderPane();
        tripsRoot.setStyle("-fx-background-color : " + PAGE_BG + "; -fx-font-family : 'Segoe UI';");
        tripsRoot.setPadding(new Insets(10, 0, 10, 0));

        // Top Bar
        HBox topBar = new HBox(16);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(20, 35, 10, 35));


        VBox heading = new VBox(3);
        Text pageTitle = new Text("Nurse Emergency Trips");
        pageTitle.setStyle("-fx-font-size : 26px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");
        heading.getChildren().addAll(pageTitle);

        Region topSpace = new Region();
        HBox.setHgrow(topSpace, Priority.ALWAYS);
        topBar.getChildren().addAll(heading, topSpace);

        // Main Content
        VBox mainContent = new VBox(22);
        mainContent.setPadding(new Insets(12, 35, 32, 35));

        // Summary Row (Enlarged Cards)
        HBox summaryRow = new HBox(16);
        totalValueText = new Text("0");
        todayValueText = new Text("0");
        completedValueText = new Text("0");

        VBox totalCard = summaryCard("TOTAL DISPATCHES", totalValueText, PRIMARY_PINK);
        VBox todayCard = summaryCard("TODAY'S MISSIONS", todayValueText, PRIMARY_PINK);
        VBox completedCard = summaryCard("COMPLETED TRANSITS", completedValueText, "#258052");
        summaryRow.getChildren().addAll(totalCard, todayCard, completedCard);

        // Filter Row (Enlarged Inputs)
        HBox filterRow = new HBox(16);
        filterRow.setAlignment(Pos.CENTER_LEFT);
        filterRow.setPadding(new Insets(16, 20, 16, 20));
        filterRow.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.06), 10, 0, 0, 2);");

        searchField = new TextField();
        searchField.setPromptText("🔍  Search by Trip ID, Patient ID, or Location...");
        searchField.setPrefHeight(42);
        searchField.setPrefWidth(350);
        searchField.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 10; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 10; -fx-font-size : 12.5px; -fx-padding: 0 14; -fx-text-fill : " + PRIMARY_TEXT + ";");

        dateFilter = new DatePicker();
        dateFilter.setPromptText("Select Date");
        dateFilter.setPrefHeight(42);
        dateFilter.setPrefWidth(170);
        dateFilter.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 10; -fx-background-radius : 10; -fx-font-size : 12.5px;");

        statusFilter = new ComboBox<>();
        statusFilter.getItems().addAll("All Status", "Scheduled", "In Progress", "Completed", "Cancelled");
        statusFilter.setValue("All Status");
        statusFilter.setPrefHeight(42);
        statusFilter.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 10; -fx-background-radius : 10; -fx-font-size : 12.5px;");

        Region filterSpace = new Region();
        HBox.setHgrow(filterSpace, Priority.ALWAYS);
        filterRow.getChildren().addAll(searchField, filterSpace, dateFilter, statusFilter);

        // Table
        Text sectionTitle = new Text("Trip History (Live Feed)");
        sectionTitle.setStyle("-fx-font-size : 16px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        VBox tableContainer = new VBox();
        tableContainer.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 12, 0, 0, 3);");

        HBox headerRow = tableHeaderRow();
        tableBody = new VBox();
        tableContainer.getChildren().addAll(headerRow, tableBody);

        StackPane tableStack = new StackPane(tableContainer);
        shimmerOverlay = buildShimmerOverlay();
        tableStack.getChildren().add(shimmerOverlay);

        pageStack = new StackPane();
        emptyState = buildEmptyState();
        emptyState.setVisible(false);
        emptyState.setManaged(false);

        HBox paginationRow = buildPaginationRow();
        VBox tripsSection = new VBox(16, sectionTitle, tableStack, emptyState, paginationRow);
        VBox contentColumn = new VBox(20, summaryRow, filterRow, tripsSection);

        pageStack.getChildren().add(contentColumn);

        Runnable applyFilters = () -> renderFilteredTrips(searchField.getText(), statusFilter.getValue(), dateFilter.getValue());
        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters.run());
        statusFilter.setOnAction(e -> applyFilters.run());
        dateFilter.setOnAction(e -> applyFilters.run());

        mainContent.getChildren().add(pageStack);
        startShimmer();

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");
        applyHiddenScrollbars(scrollPane);

        tripsRoot.setTop(topBar);
        tripsRoot.setCenter(scrollPane);

        startFirestoreLiveListener();
        return tripsRoot;
    }

    private void startFirestoreLiveListener() {
        emgController.subscribeToLiveRequests(list -> {
            Platform.runLater(() -> {
                stopShimmer();
                tripsCache.clear();
                for (AdminSideEmgReqModel item : list) {
                    if (item.getTripID() != null && !item.getTripID().trim().isEmpty()) {
                        String assignedNurse = item.getNurseID();
                        boolean matchesNurse = (nurseEmail == null || nurseEmail.isEmpty())
                                || (assignedNurse != null && assignedNurse.trim().equalsIgnoreCase(nurseEmail.trim()));
                        if (matchesNurse) {
                            tripsCache.put(item.getTripID().trim(), item);
                        }
                    }
                }
                updateSummaryMetrics();
                renderFilteredTrips(
                        searchField != null ? searchField.getText() : "",
                        statusFilter != null ? statusFilter.getValue() : "All Status",
                        dateFilter != null ? dateFilter.getValue() : null
                );
            });
        });
    }

    private void renderFilteredTrips(String queryText, String statusVal, LocalDate selectedDate) {
        tableBody.getChildren().clear();
        rowNodes.clear();
        rowData.clear();

        String q = (queryText == null) ? "" : queryText.trim().toLowerCase();
        int serialCounter = 1;
        int visibleCount = 0;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        for (AdminSideEmgReqModel trip : tripsCache.values()) {
            String tripId = trip.getTripID() != null ? trip.getTripID() : "N/A";
            String patientId = trip.getPatID() != null ? trip.getPatID() : "N/A";
            String source = trip.getSource() != null ? trip.getSource() : "Hospital";
            String destination = trip.getDestination() != null ? trip.getDestination() : "Destination";
            String location = source + "  →  " + destination;
            String normalizedStatus = normalizeStatus(trip.getStatus());

            String formattedDate = "Today";
            String formattedTime = "10:30 AM";
            LocalDate tripLocalDate = null;

            if (trip.getTimestamp() != null) {
                Timestamp ts = trip.getTimestamp();
                tripLocalDate = ts.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                formattedDate = tripLocalDate.format(dateFormatter);
                formattedTime = DateTimeFormatter.ofPattern("hh:mm a").format(ts.toDate().toInstant().atZone(ZoneId.systemDefault()));
            }

            boolean matchesSearch = q.isEmpty()
                    || tripId.toLowerCase().contains(q)
                    || patientId.toLowerCase().contains(q)
                    || location.toLowerCase().contains(q);

            boolean matchesStatus = (statusVal == null) || "All Status".equalsIgnoreCase(statusVal) || normalizedStatus.equalsIgnoreCase(statusVal);
            boolean matchesDate = (selectedDate == null) || (tripLocalDate != null && tripLocalDate.isEqual(selectedDate));

            if (matchesSearch && matchesStatus && matchesDate) {
                String srNo = String.format("%02d", serialCounter++);
                addTripRow(srNo, tripId, location, patientId, formattedDate, formattedTime, normalizedStatus, trip);
                visibleCount++;
            }
        }

        boolean hasVisibleRows = visibleCount > 0;
        emptyState.setVisible(!hasVisibleRows);
        emptyState.setManaged(!hasVisibleRows);

        if (paginationSummaryText != null) {
            paginationSummaryText.setText("Showing " + visibleCount + " of " + tripsCache.size() + " trips");
        }
    }

    private void updateSummaryMetrics() {
        int total = tripsCache.size();
        int todayCount = 0;
        int completedCount = 0;
        LocalDate today = LocalDate.now();

        for (AdminSideEmgReqModel trip : tripsCache.values()) {
            String st = trip.getStatus() != null ? trip.getStatus().toUpperCase() : "";
            if (st.contains("COMPLET")) completedCount++;

            if (trip.getTimestamp() != null) {
                LocalDate tripDate = trip.getTimestamp().toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (tripDate.isEqual(today)) todayCount++;
            } else {
                todayCount++;
            }
        }

        totalValueText.setText(String.valueOf(total));
        todayValueText.setText(String.valueOf(todayCount));
        completedValueText.setText(String.valueOf(completedCount));
    }

    private String normalizeStatus(String status) {
        if (status == null || status.trim().isEmpty()) return "Scheduled";
        String s = status.trim().toUpperCase();
        if (s.contains("PROGRESS") || s.contains("ASSIGNED") || s.contains("ACCEPTED")) return "In Progress";
        if (s.contains("COMPLET")) return "Completed";
        if (s.contains("CANCEL")) return "Cancelled";
        return "Scheduled";
    }

    public VBox summaryCard(String label, Text valueNode, String accentColor) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(18, 24, 18, 24));
        card.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.07), 10, 0, 0, 2); -fx-cursor: hand;");

        Text labelText = new Text(label);
        labelText.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + MUTED_TEXT + "; -fx-letter-spacing: 0.5px;");
        valueNode.setStyle("-fx-font-size : 28px; -fx-font-weight : bold; -fx-fill : " + accentColor + ";");

        card.getChildren().addAll(labelText, valueNode);

        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : #FDA4AF; -fx-border-width : 1.2; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(225,29,72,0.12), 14, 0, 0, 4); -fx-cursor: hand;");
            card.setTranslateY(-3);
        });
        card.setOnMouseExited(e -> {
            card.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.07), 10, 0, 0, 2); -fx-cursor: hand;");
            card.setTranslateY(0);
        });

        HBox.setHgrow(card, Priority.ALWAYS);
        return card;
    }

    public HBox tableHeaderRow() {
        HBox header = new HBox();
        header.setPadding(new Insets(14, 20, 14, 20));
        header.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-background-radius : 16 16 0 0; -fx-border-color : " + DIVIDER_COLOR + " transparent transparent transparent;");

        header.getChildren().addAll(
                wrapCell(headerCell("SR. NO.", COL_SR), COL_SR),
                wrapCell(headerCell("TRIP ID", COL_TRIP), COL_TRIP),
                locationHeaderCell(),
                wrapCell(headerCell("PATIENT ID", COL_PATIENT), COL_PATIENT),
                wrapCell(headerCell("DATE", COL_DATE), COL_DATE),
                wrapCell(headerCell("TIME", COL_TIME), COL_TIME),
                wrapCell(headerCell("STATUS", COL_STATUS), COL_STATUS),
                wrapCell(headerCell("ACTION", COL_ACTION), COL_ACTION));
        return header;
    }

    public HBox locationHeaderCell() {
        HBox box = new HBox(headerCell("LOCATION (FROM → TO)", COL_LOCATION));
        box.setPrefWidth(COL_LOCATION);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    public Text headerCell(String label, double width) {
        Text text = new Text(label);
        text.setStyle("-fx-font-size : 10.5px; -fx-font-weight : bold; -fx-fill : #8F3F5B; -fx-letter-spacing: 0.5px;");
        return text;
    }

    public void addTripRow(String srNo, String tripId, String location, String patientId, String date, String time, String status, AdminSideEmgReqModel rawModel) {
        HBox row = new HBox();
        row.setPadding(new Insets(16, 20, 16, 20));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + DIVIDER_COLOR + " transparent transparent transparent; -fx-border-width : 1 0 0 0; -fx-cursor : hand;");

        Text srText = rowCell(srNo, SECONDARY_TEXT, false);
        Text tripText = rowCell(tripId, PRIMARY_PINK, true);

        HBox locationBox = new HBox(6);
        locationBox.setPrefWidth(COL_LOCATION);
        locationBox.setAlignment(Pos.CENTER_LEFT);
        Text locationText = new Text(location);
        locationText.setStyle("-fx-font-size : 12.5px; -fx-fill : " + PRIMARY_TEXT + ";");
        locationBox.getChildren().add(locationText);

        Text patientText = rowCell(patientId, PRIMARY_TEXT, false);
        Text dateText = rowCell(date, SECONDARY_TEXT, false);
        Text timeText = rowCell(time, MUTED_TEXT, false);

        Text statusBadge = new Text(status);
        statusBadge.setStyle(badgeStyle(status));
        StackPane statusCell = new StackPane(statusBadge);
        statusCell.setPrefWidth(COL_STATUS);
        statusCell.setAlignment(Pos.CENTER_LEFT);

        Text actionText = new Text("View Details →");
        actionText.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");
        StackPane actionCell = new StackPane(actionText);
        actionCell.setPrefWidth(COL_ACTION);
        actionCell.setAlignment(Pos.CENTER_LEFT);

        StackPane srCell = wrapCell(srText, COL_SR);
        StackPane tripCell = wrapCell(tripText, COL_TRIP);
        StackPane patientCell = wrapCell(patientText, COL_PATIENT);
        StackPane dateCell = wrapCell(dateText, COL_DATE);
        StackPane timeCell = wrapCell(timeText, COL_TIME);

        row.getChildren().addAll(srCell, tripCell, locationBox, patientCell, dateCell, timeCell, statusCell, actionCell);

        row.setOnMouseEntered(e ->
                row.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-border-color : " + DIVIDER_COLOR + " transparent transparent transparent; -fx-border-width : 1 0 0 0; -fx-cursor : hand;")
        );
        row.setOnMouseExited(e ->
                row.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + DIVIDER_COLOR + " transparent transparent transparent; -fx-border-width : 1 0 0 0; -fx-cursor : hand;")
        );

        row.setOnMouseClicked(e -> showTripDetailsModal(tripId, location, patientId, date, time, status, rawModel));

        tableBody.getChildren().add(row);
        rowNodes.add(row);
        rowData.add(new String[] { tripId, patientId, status });
    }

    public Text rowCell(String value, String color, boolean bold) {
        Text text = new Text(value);
        text.setStyle("-fx-font-size : 12.5px; " + (bold ? "-fx-font-weight : bold; " : "") + "-fx-fill : " + color + ";");
        return text;
    }

    public StackPane wrapCell(Text text, double width) {
        StackPane cell = new StackPane(text);
        cell.setPrefWidth(width);
        cell.setAlignment(Pos.CENTER_LEFT);
        return cell;
    }

    public String badgeStyle(String status) {
        String bg = "#F5F1F3";
        String fg = "#665960";

        if ("Completed".equalsIgnoreCase(status)) {
            bg = "#E7F5ED";
            fg = "#258052";
        } else if ("In Progress".equalsIgnoreCase(status)) {
            bg = VERY_LIGHT_PINK;
            fg = PRIMARY_PINK;
        } else if ("Scheduled".equalsIgnoreCase(status)) {
            bg = "#FFF3D8";
            fg = "#B77900";
        } else if ("Cancelled".equalsIgnoreCase(status)) {
            bg = "#FCE8E7";
            fg = "#C94F4F";
        }

        return "-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : " + fg + "; -fx-background-color : " + bg + "; -fx-padding : 4 10 4 10; -fx-background-radius : 8;";
    }

    public HBox buildPaginationRow() {
        HBox pagination = new HBox(14);
        pagination.setAlignment(Pos.CENTER_LEFT);
        pagination.setPadding(new Insets(4, 6, 0, 6));

        paginationSummaryText = new Text("Showing 0 of 0 trips");
        paginationSummaryText.setStyle("-fx-font-size : 10px; -fx-fill : " + MUTED_TEXT + ";");

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        Text prev = pageLink("Previous", false);
        Text pageOne = pageLink("1", true);
        Text pageTwo = pageLink("2", false);
        Text pageThree = pageLink("3", false);
        Text next = pageLink("Next", false);

        HBox pageLinks = new HBox(10, prev, pageOne, pageTwo, pageThree, next);
        pageLinks.setAlignment(Pos.CENTER_LEFT);

        pagination.getChildren().addAll(paginationSummaryText, space, pageLinks);
        return pagination;
    }

    public Text pageLink(String label, boolean active) {
        Text text = new Text(label);
        text.setStyle(active
                ? "-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + "; -fx-cursor : hand;"
                : "-fx-font-size : 11px; -fx-fill : " + MUTED_TEXT + "; -fx-cursor : hand;");
        return text;
    }

    public VBox buildEmptyState() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(50));
        box.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14;");

        Text icon = new Text("⇄");
        icon.setStyle("-fx-font-size : 30px; -fx-fill : " + SOFT_PINK + ";");
        Text title = new Text("No trips found");
        title.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");
        Text subtitle = new Text("No patient emergency requests found in 'adminEmergencyRequests'.");
        subtitle.setStyle("-fx-font-size : 11px; -fx-fill : " + SECONDARY_TEXT + ";");

        box.getChildren().addAll(icon, title, subtitle);
        return box;
    }

    private StackPane buildShimmerOverlay() {
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(255,255,255,0.92); -fx-background-radius: 16;");
        overlay.setMouseTransparent(true);

        VBox skeleton = new VBox(10);
        skeleton.setPadding(new Insets(18, 20, 18, 20));
        skeleton.setFillWidth(true);

        for (int rowIndex = 0; rowIndex < 5; rowIndex++) {
            HBox row = new HBox(14);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPrefHeight(42);
            row.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-background-radius: 8;");

            for (int columnIndex = 0; columnIndex < 4; columnIndex++) {
                Region block = new Region();
                block.setPrefHeight(12);
                block.setMaxWidth(Double.MAX_VALUE);
                block.setStyle("-fx-background-color: " + SOFT_PINK + "; -fx-background-radius: 6;");
                HBox.setHgrow(block, Priority.ALWAYS);
                row.getChildren().add(block);
            }
            skeleton.getChildren().add(row);
        }

        Region highlight = new Region();
        highlight.setPrefWidth(90);
        highlight.setMaxHeight(Double.MAX_VALUE);
        highlight.setStyle("-fx-background-color: linear-gradient(to right, transparent, rgba(255,255,255,0.75), transparent);");
        StackPane.setAlignment(highlight, Pos.CENTER_LEFT);
        overlay.getChildren().addAll(skeleton, highlight);
        overlay.setUserData(highlight);
        return overlay;
    }

    private void startShimmer() {
        stopShimmer();
        if (shimmerOverlay == null) return;

        shimmerOverlay.setVisible(true);
        shimmerTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(((Region) shimmerOverlay.getUserData()).translateXProperty(), -120)),
                new KeyFrame(Duration.seconds(1.4), new KeyValue(((Region) shimmerOverlay.getUserData()).translateXProperty(), 900))
        );
        shimmerTimeline.setCycleCount(Animation.INDEFINITE);
        shimmerTimeline.play();
    }

    private void stopShimmer() {
        if (shimmerTimeline != null) {
            shimmerTimeline.stop();
            shimmerTimeline = null;
        }
        if (shimmerOverlay != null) shimmerOverlay.setVisible(false);
    }

    // =========================================================
    // MODAL WITH ASYNCHRONOUS FIRESTORE PHOTO & VOICE RETRIEVAL
    // =========================================================
    public void showTripDetailsModal(String tripId, String location, String patientId, String date, String time, String status, AdminSideEmgReqModel rawModel) {
        StackPane dimmer = new StackPane();
        dimmer.setStyle("-fx-background-color : rgba(43,34,38,0.45);");

        VBox modal = new VBox(12);
        modal.setMaxWidth(520);
        modal.setMaxHeight(580);
        modal.setPadding(new Insets(22));
        modal.setStyle("-fx-background-color : white; -fx-background-radius : 16; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(43,34,38,0.25), 20, 0, 0, 8);");
        modal.setOpacity(0);
        modal.setScaleX(0.92);
        modal.setScaleY(0.92);

        HBox topTitleRow = new HBox(10);
        topTitleRow.setAlignment(Pos.CENTER_LEFT);

        Text modalTitle = new Text("Trip Details — " + tripId);
        modalTitle.setStyle("-fx-font-size : 16px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text badge = new Text(status);
        badge.setStyle(badgeStyle(status));

        Region titleSpacer = new Region();
        HBox.setHgrow(titleSpacer, Priority.ALWAYS);

        Button closeTopBtn = new Button("✕");
        closeTopBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + MUTED_TEXT + "; -fx-cursor: hand; -fx-font-size: 12px;");
        closeTopBtn.setOnAction(e -> hideModal(dimmer));

        topTitleRow.getChildren().addAll(modalTitle, badge, titleSpacer, closeTopBtn);

        VBox modalContent = new VBox(14);
        modalContent.setPadding(new Insets(4));

        VBox infoSection = new VBox(6,
                modalRow("Trip ID", tripId),
                modalRow("Patient ID", patientId),
                modalRow("Nurse Assigned", rawModel != null && rawModel.getNurseID() != null ? rawModel.getNurseID() : "None"),
                modalRow("Driver Assigned", rawModel != null && rawModel.getDriverID() != null ? rawModel.getDriverID() : "None"),
                modalRow("Date & Time", date + " at " + time)
        );

        Text locationLabel = new Text("ROUTE COORDINATES");
        locationLabel.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + MUTED_TEXT + ";");
        Text locationText = new Text(location);
        locationText.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");
        VBox locationBox = new VBox(4, locationLabel, locationText);

        // Uploaded Photos Section
        VBox photosSection = new VBox(8);
        Text photosHeader = new Text("📷 Uploaded Patient Photos");
        photosHeader.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");

        HBox photoGallery = new HBox(10);
        photoGallery.setAlignment(Pos.CENTER_LEFT);
        ProgressIndicator photoSpinner = new ProgressIndicator();
        photoSpinner.setPrefSize(20, 20);
        photoGallery.getChildren().add(photoSpinner);
        photosSection.getChildren().addAll(photosHeader, photoGallery);

        // Voice Clinical Reports Section
        VBox voiceSection = new VBox(8);
        Text voiceHeader = new Text("🎤 Voice Clinical Transcripts");
        voiceHeader.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");

        VBox voiceList = new VBox(6);
        ProgressIndicator voiceSpinner = new ProgressIndicator();
        voiceSpinner.setPrefSize(20, 20);
        voiceList.getChildren().add(voiceSpinner);
        voiceSection.getChildren().addAll(voiceHeader, voiceList);

        modalContent.getChildren().addAll(locationBox, infoSection, photosSection, voiceSection);

        ScrollPane modalScroll = new ScrollPane(modalContent);
        modalScroll.setFitToWidth(true);
        modalScroll.setPrefHeight(380);
        modalScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        applyHiddenScrollbars(modalScroll);

        HBox actionsRow = new HBox(10);
        actionsRow.setAlignment(Pos.CENTER_RIGHT);

        Button closeButton = new Button("Close");
        closeButton.setPrefWidth(90);
        closeButton.setPrefHeight(36);
        closeButton.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-text-fill : " + SECONDARY_TEXT + "; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-border-radius : 8; -fx-cursor : hand;");
        closeButton.setOnAction(e -> hideModal(dimmer));
        actionsRow.getChildren().add(closeButton);

        if (rawModel != null && rawModel.getDestination() != null) {
            Button navButton = new Button("🧭 Navigate Map");
            navButton.setPrefHeight(36);
            navButton.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-text-fill : " + PRIMARY_PINK + "; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");
            navButton.setOnAction(e -> {
                try {
                    String encoded = URLEncoder.encode(rawModel.getDestination(), StandardCharsets.UTF_8);
                    String url = "https://www.google.com/maps/dir/?api=1&destination=" + encoded;
                    if (Desktop.isDesktopSupported()) Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            actionsRow.getChildren().add(0, navButton);
        }

        modal.getChildren().addAll(topTitleRow, modalScroll, actionsRow);
        dimmer.getChildren().add(modal);
        dimmer.setOnMouseClicked(e -> {
            if (e.getTarget() == dimmer) hideModal(dimmer);
        });

        pageStack.getChildren().add(dimmer);

        // Fetch Live Media Attachments from Firestore
        fetchPatientMedia(patientId, photoGallery, voiceList);

        FadeTransition dimIn = new FadeTransition(NurseAppSettings.dur(180), dimmer);
        dimIn.setFromValue(0);
        dimIn.setToValue(1);

        FadeTransition modalFadeIn = new FadeTransition(NurseAppSettings.dur(200), modal);
        modalFadeIn.setToValue(1);

        ScaleTransition modalScaleIn = new ScaleTransition(NurseAppSettings.dur(200), modal);
        modalScaleIn.setToX(1);
        modalScaleIn.setToY(1);

        new ParallelTransition(dimIn, modalFadeIn, modalScaleIn).play();
    }

    private void fetchPatientMedia(String patientId, HBox photoGallery, VBox voiceList) {
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();

                // 1. Query Patient Photos by patientId (e.g. PAT-2001)
                QuerySnapshot photoSnap = db.collection("patientPhotos")
                        .whereEqualTo("patientId", patientId)
                        .get()
                        .get();

                List<String> imageUrls = new ArrayList<>();
                for (DocumentSnapshot doc : photoSnap.getDocuments()) {
                    String url = doc.getString("imageUrl");
                    if (url != null) imageUrls.add(url);
                }

                // 2. Query Voice Reports by patientId (e.g. PAT-2001)
                QuerySnapshot voiceSnap = db.collection("nurseVoiceReports")
                        .whereEqualTo("patientId", patientId)
                        .get()
                        .get();

                List<String> transcripts = new ArrayList<>();
                for (DocumentSnapshot doc : voiceSnap.getDocuments()) {
                    String content = doc.getString("transcribedText");
                    if (content != null) transcripts.add(content);
                }

                Platform.runLater(() -> {
                    // Update Photos UI
                    photoGallery.getChildren().clear();
                    if (imageUrls.isEmpty()) {
                        Text noPhotos = new Text("No photos uploaded for patient " + patientId);
                        noPhotos.setStyle("-fx-font-size: 10px; -fx-fill: " + MUTED_TEXT + ";");
                        photoGallery.getChildren().add(noPhotos);
                    } else {
                        for (String url : imageUrls) {
                            ImageView img = new ImageView(new Image(url, 90, 70, true, true, true));
                            img.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 2); -fx-cursor: hand;");

                            Rectangle clip = new Rectangle(90, 70);
                            clip.setArcWidth(10);
                            clip.setArcHeight(10);
                            img.setClip(clip);

                            img.setOnMouseClicked(e -> {
                                try {
                                    if (Desktop.isDesktopSupported()) Desktop.getDesktop().browse(new URI(url));
                                } catch (Exception ignored) {}
                            });
                            photoGallery.getChildren().add(img);
                        }
                    }

                    // Update Voice Notes UI
                    voiceList.getChildren().clear();
                    if (transcripts.isEmpty()) {
                        Text noVoice = new Text("No voice notes recorded for patient " + patientId);
                        noVoice.setStyle("-fx-font-size: 10px; -fx-fill: " + MUTED_TEXT + ";");
                        voiceList.getChildren().add(noVoice);
                    } else {
                        for (String t : transcripts) {
                            VBox noteBox = new VBox(3);
                            noteBox.setPadding(new Insets(8, 10, 8, 10));
                            noteBox.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-background-radius: 8; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8;");

                            Text noteText = new Text("“ " + t + " ”");
                            noteText.setStyle("-fx-font-size: 11px; -fx-fill: " + PRIMARY_TEXT + "; -fx-font-style: italic;");
                            noteText.setWrappingWidth(440);

                            noteBox.getChildren().add(noteText);
                            voiceList.getChildren().add(noteBox);
                        }
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    photoGallery.getChildren().clear();
                    voiceList.getChildren().clear();
                    photoGallery.getChildren().add(new Text("Error loading attachments."));
                });
            }
        }).start();
    }

    public HBox modalRow(String label, String value) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        Text labelText = new Text(label);
        labelText.setStyle("-fx-font-size : 10px; -fx-fill : " + MUTED_TEXT + ";");

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        Text valueText = new Text(value);
        valueText.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        row.getChildren().addAll(labelText, space, valueText);
        return row;
    }

    public void hideModal(StackPane dimmer) {
        FadeTransition dimOut = new FadeTransition(NurseAppSettings.dur(160), dimmer);
        dimOut.setToValue(0);
        dimOut.setOnFinished(e -> pageStack.getChildren().remove(dimmer));
        dimOut.play();
    }

    public static void applyHiddenScrollbars(ScrollPane sp) {
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setPannable(true);
        sp.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() != 0 && sp.getContent() != null) {
                double deltaY = event.getDeltaY();
                double contentHeight = sp.getContent().getBoundsInLocal().getHeight();
                if (contentHeight <= 0 && sp.getContent() instanceof Region) {
                    contentHeight = ((Region) sp.getContent()).getHeight();
                }
                double viewportHeight = sp.getViewportBounds().getHeight();
                if (viewportHeight <= 0) {
                    viewportHeight = sp.getHeight();
                }
                double scrollableDistance = contentHeight - viewportHeight;
                if (viewportHeight > 0 && scrollableDistance > 1.0) {
                    double currentV = sp.getVvalue();
                    boolean canScrollUp = deltaY > 0 && currentV > 0.0001;
                    boolean canScrollDown = deltaY < 0 && currentV < 0.9999;
                    if (canScrollUp || canScrollDown) {
                        double scrollStep = deltaY * 2.5;
                        double newV = currentV - (scrollStep / scrollableDistance);
                        sp.setVvalue(Math.max(0.0, Math.min(1.0, newV)));
                        event.consume();
                    }
                }
            }
        });
    }
}