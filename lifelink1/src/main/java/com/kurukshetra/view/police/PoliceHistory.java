package com.kurukshetra.view.police;

import com.kurukshetra.config.FirebaseConfig;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import com.kurukshetra.view.util.ShimmerLoader;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class PoliceHistory {

    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, BlinkMacSystemFont, sans-serif; ";

    // =========================================================
    // MAIN CONTENT COLORS (BROWN 3 — MOCHA & TERRACOTTA)
    // =========================================================
    private static final String PAGE_BG                = "#F8F0EA";   // page background
    private static final String SURFACE                = "#FFFFFF";   // cards
    private static final String VERY_LIGHT_BEIGE       = "#FBF5EF";   // input field background
    private static final String BROWN_DARK             = "#7A4A32";   // primary buttons, headers
    private static final String PRIMARY_TEXT           = "#33261E";   // main text
    private static final String SEC_TEXT               = "#725D4E";   // secondary/label text
    private static final String BORDER                 = "#EBDCCF";   // card and input borders
    private static final String ACCENT_TERRACOTTA      = "#D85A30";   // hover states, links, live/priority badges
    private static final String ACCENT_TERRACOTTA_BG   = "#FAECE7";   // light bg for pending/priority badges
    private static final String ACCENT_TERRACOTTA_TEXT = "#993C1D";   // text on ACCENT_TERRACOTTA_BG
    private static final String SUCCESS_GREEN          = "#639922";   // "Completed"/"Cleared" badge text
    private static final String SUCCESS_GREEN_BG       = "#EAF3DE";   // "Completed"/"Cleared" badge background

    // =========================================================
    // SIDEBAR COLORS
    // =========================================================
    private static final String SIDEBAR_BG             = "#2B1D15";   // dark espresso-brown sidebar background
    private static final String SIDEBAR_TEXT           = "#E8DCD1";   // default nav item text (light warm gray, not pure white)
    private static final String SIDEBAR_TEXT_MUTED     = "#A6907E";   // section labels like "POLICE NAVIGATION", inactive icons
    private static final String SIDEBAR_ACTIVE_BG      = "#D85A30";   // active nav item background = ACCENT_TERRACOTTA
    private static final String SIDEBAR_ACTIVE_TEXT    = "#FFFFFF";   // text/icon on the active nav item
    private static final String SIDEBAR_HOVER_BG       = "#3D2A1F";   // subtle hover state on inactive nav items, one step lighter than SIDEBAR_BG
    private static final String SIDEBAR_BORDER         = "#3D2A1F";   // divider lines inside sidebar, if any
    private static final String SIDEBAR_SIGNOUT_BG     = "#4A241C";   // "Sign Out Shift" button background — dark red-brown
    private static final String SIDEBAR_SIGNOUT_TEXT   = "#F3B8A8";   // "Sign Out Shift" text color — light coral

    // Reusable Card Style with soft gaussian ambient shadow
    private static final String CARD_STYLE = "-fx-background-color: " + SURFACE
            + "; -fx-background-radius: 18px; -fx-border-color: " + BORDER
            + "; -fx-border-radius: 18px; -fx-effect: dropshadow(gaussian, rgba(51, 38, 30, 0.06), 16, 0.10, 0, 4);";

    public static class HistoryRecord {
        public String docId;
        public String tripId;
        public String patId;
        public String driverName;
        public String source;
        public String destination;
        public String status;
        public Date timestamp;
        public String formattedDate;
        public String formattedTime;
        public String actionTimeStr;

        public HistoryRecord(String docId, String tripId, String patId, String driverName, String source, String destination, String status, Date timestamp, String actionTimeStr) {
            this.docId = docId;
            this.tripId = tripId;
            this.patId = patId;
            this.driverName = driverName;
            this.source = source;
            this.destination = destination;
            this.status = status;
            this.timestamp = timestamp != null ? timestamp : new Date();

            SimpleDateFormat dateFmt = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
            SimpleDateFormat timeFmt = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

            this.formattedDate = dateFmt.format(this.timestamp);
            this.formattedTime = timeFmt.format(this.timestamp);
            this.actionTimeStr = actionTimeStr != null ? actionTimeStr : "Cleared: " + timeFmt.format(new Date(this.timestamp.getTime() + 180000));
        }
    }

    // High-performance daemon thread pool matching available CPU cores
    private static final ExecutorService WORKER_POOL = Executors.newFixedThreadPool(
            Math.max(2, Runtime.getRuntime().availableProcessors()),
            r -> {
                Thread t = new Thread(r, "PoliceHistory-Worker");
                t.setDaemon(true);
                return t;
            }
    );

    private final List<HistoryRecord> allRecordsList = new ArrayList<>();
    private VBox historyCardsContainer;
    private DatePicker datePicker;
    private TextField searchField;
    private ShimmerLoader.ShimmerPane historyShimmer;

    // Dynamic KPI Metric Nodes
    private Text kpiTotalCountText;
    private Text kpiClearedCountText;
    private Text kpiHospitalsCountText;
    private Text showingCountBadgeText;

    // KPI shimmer references for reveal-on-load
    private HBox kpiBannerSlot;
    private ShimmerLoader.ShimmerPane kpiShimmer1;
    private ShimmerLoader.ShimmerPane kpiShimmer2;
    private ShimmerLoader.ShimmerPane kpiShimmer3;
    private VBox kpiCard1Real;
    private VBox kpiCard2Real;
    private VBox kpiCard3Real;

    public ScrollPane getHistoryView() {
        VBox historyPage = new VBox(22);
        historyPage.setPadding(new Insets(32, 44, 40, 44));
        historyPage.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        // =========================================================
        // 1. MODERN HEADER WITH LIVE ARCHIVE STATUS BADGE
        // =========================================================
        HBox topHeaderRow = new HBox(16);
        topHeaderRow.setAlignment(Pos.CENTER_LEFT);

        VBox headingBox = new VBox(5);

        Text heading = new Text("Ambulance Clearance History");
        heading.setStyle(FONT_FAMILY + "-fx-font-size: 28px; -fx-font-weight: 800; -fx-fill: " + PRIMARY_TEXT + ";");

        headingBox.getChildren().addAll( heading);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        // Header Live Sync Counter Pill
        HBox syncChip = new HBox(8);
        syncChip.setAlignment(Pos.CENTER);
        syncChip.setPadding(new Insets(8, 16, 8, 16));
        syncChip.setStyle("-fx-background-color: " + SURFACE + "; -fx-background-radius: 20px; -fx-border-color: " + BORDER + "; -fx-border-radius: 20px; -fx-effect: dropshadow(gaussian, rgba(51,38,30,0.04), 10, 0, 0, 2);");

        Label syncIcon = new Label("⚡");
        syncIcon.setStyle("-fx-font-size: 13px; -fx-text-fill: " + ACCENT_TERRACOTTA + ";");
        showingCountBadgeText = new Text("Loading Logs...");
        showingCountBadgeText.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        syncChip.getChildren().addAll(syncIcon, showingCountBadgeText);

        topHeaderRow.getChildren().addAll(headingBox, headerSpacer, syncChip);

        // =========================================================
        // 2. HERO KPI STAT SUMMARY BANNER (3-COLUMN CARDS)
        // =========================================================
        HBox kpiBanner = buildKpiBannerWithShimmer();

        // =========================================================
        // 3. ELEVATED SEGMENTED FILTER & SEARCH BAR
        // =========================================================
        HBox filterBox = new HBox(14);
        filterBox.setAlignment(Pos.CENTER_LEFT);
        filterBox.setPadding(new Insets(16, 20, 16, 20));
        filterBox.setStyle(CARD_STYLE);

        HBox dateLabelBox = new HBox(6);
        dateLabelBox.setAlignment(Pos.CENTER_LEFT);
        Label dateIcon = new Label("📅");
        dateIcon.setStyle("-fx-font-size: 14px;");
        Text dateText = new Text("Date Filter");
        dateText.setStyle(FONT_FAMILY + "-fx-font-size: 13.5px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        dateLabelBox.getChildren().addAll(dateIcon, dateText);

        datePicker = new DatePicker();
        datePicker.setPrefWidth(180);
        datePicker.setPrefHeight(38);
        datePicker.setPromptText("Pick a date...");
        datePicker.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 10px; -fx-border-color: " + BORDER + "; -fx-border-radius: 10px; -fx-font-size: 12.5px;");
        datePicker.valueProperty().addListener((obs, oldVal, newVal) -> filterAndRenderRecords());

        Button todayButton = new Button("Today");
        todayButton.setPrefWidth(85);
        todayButton.setPrefHeight(38);
        todayButton.setStyle(FONT_FAMILY + "-fx-background-color: " + BROWN_DARK + "; -fx-text-fill: white; -fx-font-size: 12.5px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand;");
        addSpringHoverEffect(todayButton, BROWN_DARK, ACCENT_TERRACOTTA, "#FFFFFF", "#FFFFFF");
        todayButton.setOnAction(event -> datePicker.setValue(LocalDate.now()));

        Button clearFilterBtn = new Button("All Records");
        clearFilterBtn.setPrefWidth(98);
        clearFilterBtn.setPrefHeight(38);
        clearFilterBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-border-color: " + BORDER + "; -fx-border-radius: 10px; -fx-text-fill: " + SEC_TEXT + "; -fx-font-size: 12.5px; -fx-font-weight: bold; -fx-cursor: hand;");
        addSpringHoverEffect(clearFilterBtn, "transparent", VERY_LIGHT_BEIGE, SEC_TEXT, PRIMARY_TEXT);
        clearFilterBtn.setOnAction(event -> datePicker.setValue(null));

        Button refreshBtn = new Button("↻  Refresh");
        refreshBtn.setPrefWidth(100);
        refreshBtn.setPrefHeight(38);
        refreshBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-border-color: " + BORDER + "; -fx-border-radius: 10px; -fx-text-fill: " + BROWN_DARK + "; -fx-font-size: 12.5px; -fx-font-weight: bold; -fx-cursor: hand;");
        addSpringHoverEffect(refreshBtn, VERY_LIGHT_BEIGE, ACCENT_TERRACOTTA_BG, BROWN_DARK, ACCENT_TERRACOTTA_TEXT);
        refreshBtn.setOnAction(event -> refreshHistory());

        Region searchSpacer = new Region();
        HBox.setHgrow(searchSpacer, Priority.ALWAYS);

        // Modern Search Input with subtle magnifying glass icon
        HBox searchContainer = new HBox(8);
        searchContainer.setAlignment(Pos.CENTER_LEFT);
        searchContainer.setPadding(new Insets(0, 12, 0, 12));
        searchContainer.setPrefWidth(300);
        searchContainer.setPrefHeight(38);
        searchContainer.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");

        Label searchIcon = new Label("🔍");
        searchIcon.setStyle("-fx-font-size: 13px; -fx-text-fill: " + SEC_TEXT + ";");

        searchField = new TextField();
        searchField.setPromptText("Search Unit, Hospital or Driver...");
        searchField.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: " + PRIMARY_TEXT + "; -fx-font-size: 13px; -fx-padding: 0;");
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterAndRenderRecords());

        searchField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                searchContainer.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 12px; -fx-border-color: " + ACCENT_TERRACOTTA + "; -fx-border-radius: 12px; -fx-border-width: 1.5px;");
            } else {
                searchContainer.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px; -fx-border-width: 1px;");
            }
        });

        searchContainer.getChildren().addAll(searchIcon, searchField);

        filterBox.getChildren().addAll(dateLabelBox, datePicker, todayButton, clearFilterBtn, refreshBtn, searchSpacer, searchContainer);

        historyCardsContainer = new VBox(20);
        showShimmerSkeleton();

        historyPage.getChildren().addAll(topHeaderRow, kpiBanner, filterBox, historyCardsContainer);

        ScrollPane scrollPane = new ScrollPane(historyPage);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");
        PoliceDashboard.applyHiddenScrollbars(scrollPane);

        loadHistoryFromFirestore();

        return scrollPane;
    }

    // =========================================================
    // 2. KPI STAT BANNER COMPONENT (WITH SHIMMER SKELETONS)
    // =========================================================
    private HBox buildKpiBannerWithShimmer() {
        kpiTotalCountText = new Text("0");
        kpiClearedCountText = new Text("0");
        kpiHospitalsCountText = new Text("0");

        kpiCard1Real = createKpiStatCard("🚑", "TOTAL CORRIDORS", kpiTotalCountText, "Telemetry Logged", ACCENT_TERRACOTTA);
        kpiCard2Real = createKpiStatCard("✓", "CLEARANCES COMPLETED", kpiClearedCountText, "100% Interlock Success", SUCCESS_GREEN);
        kpiCard3Real = createKpiStatCard("🏥", "HOSPITALS CONNECTED", kpiHospitalsCountText, "Emergency Care Centers", BROWN_DARK);
        kpiCard1Real.setOpacity(0);
        kpiCard2Real.setOpacity(0);
        kpiCard3Real.setOpacity(0);

        // Shimmer skeletons overlaid on each KPI slot
        kpiShimmer1 = ShimmerLoader.createCardSkeleton(260, 110);
        kpiShimmer2 = ShimmerLoader.createCardSkeleton(260, 110);
        kpiShimmer3 = ShimmerLoader.createCardSkeleton(260, 110);
        kpiShimmer1.setMaxSize(Double.MAX_VALUE, 110);
        kpiShimmer2.setMaxSize(Double.MAX_VALUE, 110);
        kpiShimmer3.setMaxSize(Double.MAX_VALUE, 110);

        StackPane slot1 = new StackPane(kpiCard1Real, kpiShimmer1);
        StackPane slot2 = new StackPane(kpiCard2Real, kpiShimmer2);
        StackPane slot3 = new StackPane(kpiCard3Real, kpiShimmer3);
        slot1.setAlignment(Pos.TOP_LEFT);
        slot2.setAlignment(Pos.TOP_LEFT);
        slot3.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(slot1, Priority.ALWAYS);
        HBox.setHgrow(slot2, Priority.ALWAYS);
        HBox.setHgrow(slot3, Priority.ALWAYS);

        kpiBannerSlot = new HBox(16, slot1, slot2, slot3);
        kpiBannerSlot.setAlignment(Pos.CENTER);
        kpiBannerSlot.setMaxWidth(Double.MAX_VALUE);
        return kpiBannerSlot;
    }

    /** Fade out the KPI shimmer skeletons and reveal the real stat cards. */
    private void revealKpiBanner() {
        revealKpiSlot(kpiShimmer1, kpiCard1Real);
        revealKpiSlot(kpiShimmer2, kpiCard2Real);
        revealKpiSlot(kpiShimmer3, kpiCard3Real);
    }

    private void revealKpiSlot(ShimmerLoader.ShimmerPane shimmer, VBox realCard) {
        if (shimmer == null) return;
        shimmer.stop();
        // Find parent StackPane
        if (shimmer.getParent() instanceof javafx.scene.layout.StackPane sp) {
            FadeTransition fo = new FadeTransition(Duration.millis(280), shimmer);
            fo.setFromValue(1.0);
            fo.setToValue(0.0);
            fo.setOnFinished(e -> sp.getChildren().remove(shimmer));
            fo.play();
        }
        FadeTransition fi = new FadeTransition(Duration.millis(320), realCard);
        fi.setFromValue(0.0);
        fi.setToValue(1.0);
        fi.play();
    }

    private VBox createKpiStatCard(String iconStr, String titleStr, Text valueNode, String subtextStr, String accentColor) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(18, 22, 18, 22));
        card.setStyle(CARD_STYLE);

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconCircle = new StackPane();
        iconCircle.setPrefSize(38, 38);
        iconCircle.setMinSize(38, 38);
        iconCircle.setMaxSize(38, 38);
        iconCircle.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 10px; -fx-border-color: " + BORDER + "; -fx-border-radius: 10px;");
        Label iconLbl = new Label(iconStr);
        iconLbl.setStyle("-fx-font-size: 16px;");
        iconCircle.getChildren().add(iconLbl);

        Text titleText = new Text(titleStr);
        titleText.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: 800; -fx-fill: " + SEC_TEXT + "; -fx-letter-spacing: 0.5px;");

        topRow.getChildren().addAll(iconCircle, titleText);

        valueNode.setStyle(FONT_FAMILY + "-fx-font-size: 26px; -fx-font-weight: 800; -fx-fill: " + PRIMARY_TEXT + ";");

        HBox subBox = new HBox(6);
        subBox.setAlignment(Pos.CENTER_LEFT);
        Label dot = new Label("●");
        dot.setStyle("-fx-font-size: 9px; -fx-text-fill: " + accentColor + ";");
        Text subText = new Text(subtextStr);
        subText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: 600; -fx-fill: " + SEC_TEXT + ";");
        subBox.getChildren().addAll(dot, subText);

        card.getChildren().addAll(topRow, valueNode, subBox);

        // Tactile hover elevation
        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-background-color: " + SURFACE + "; -fx-background-radius: 18px; -fx-border-color: " + ACCENT_TERRACOTTA + "; -fx-border-radius: 18px; -fx-effect: dropshadow(gaussian, rgba(216, 90, 48, 0.12), 18, 0.12, 0, 5);");
            card.setTranslateY(-2);
        });
        card.setOnMouseExited(e -> {
            card.setStyle(CARD_STYLE);
            card.setTranslateY(0);
        });

        return card;
    }

    private void updateKpiMetrics(List<HistoryRecord> records) {
        int total = records.size();
        long cleared = records.stream()
                .filter(r -> "CLEARED".equalsIgnoreCase(r.status) || "COMPLETED".equalsIgnoreCase(r.status))
                .count();
        long hospitalCount = records.stream()
                .map(r -> r.destination)
                .filter(d -> d != null && !d.trim().isEmpty())
                .distinct()
                .count();

        kpiTotalCountText.setText(String.valueOf(total));
        kpiClearedCountText.setText(String.valueOf(cleared));
        kpiHospitalsCountText.setText(String.valueOf(hospitalCount));
        showingCountBadgeText.setText(total + " Corridors in Registry");

        // Subtle pop animation on metric updates
        ScaleTransition st1 = new ScaleTransition(Duration.millis(180), kpiTotalCountText);
        st1.setFromX(1.15); st1.setFromY(1.15); st1.setToX(1.0); st1.setToY(1.0);
        st1.play();

        ScaleTransition st2 = new ScaleTransition(Duration.millis(180), kpiClearedCountText);
        st2.setFromX(1.15); st2.setFromY(1.15); st2.setToX(1.0); st2.setToY(1.0);
        st2.play();

        ScaleTransition st3 = new ScaleTransition(Duration.millis(180), kpiHospitalsCountText);
        st3.setFromX(1.15); st3.setFromY(1.15); st3.setToX(1.0); st3.setToY(1.0);
        st3.play();
    }

    private void showShimmerSkeleton() {
        if (historyShimmer != null) {
            historyShimmer.stop();
        }
        historyCardsContainer.getChildren().clear();
        historyShimmer = ShimmerLoader.createPoliceHistorySkeleton(860, 2);
        historyCardsContainer.getChildren().add(historyShimmer);
    }

    public void refreshHistory() {
        showShimmerSkeleton();
        loadHistoryFromFirestore();
    }

    // =========================================================
    // 3. FIRESTORE DATA RETRIEVAL (MULTI-THREADED & ASYNC)
    // =========================================================
    private static HistoryRecord mapDocToHistoryRecord(QueryDocumentSnapshot doc) {
        String docId = doc.getId();

        String tripId = doc.contains("tripID") ? doc.getString("tripID") : doc.getString("TripID");
        if (tripId == null) tripId = "UNIT-" + docId.substring(0, Math.min(docId.length(), 6)).toUpperCase();

        String patId = doc.contains("patID") ? doc.getString("patID") : doc.getString("PATID");
        if (patId == null) patId = "PAT-UNKNOWN";

        String driver = doc.contains("driverID") ? doc.getString("driverID") : doc.getString("driverName");
        if (driver == null) driver = "On-Duty Paramedic";

        String source = doc.contains("pickupLocation") ? doc.getString("pickupLocation") : doc.getString("source");
        if (source == null) source = "Swargate Central";

        String dest = null;
        if (doc.contains("destinationHospital")) dest = doc.getString("destinationHospital");
        if (dest == null && doc.contains("destination")) dest = doc.getString("destination");
        if (dest == null && doc.contains("Destination")) dest = doc.getString("Destination");
        if (dest == null && doc.contains("hospitalName")) dest = doc.getString("hospitalName");
        if (dest == null && doc.contains("hospital")) dest = doc.getString("hospital");
        if (dest == null || dest.trim().isEmpty()) dest = "KEM Hospital";

        String status = doc.contains("status") ? doc.getString("status") : "COMPLETED";

        Date recordTime = new Date();
        if (doc.contains("timestamp") && doc.get("timestamp") instanceof Timestamp) {
            Timestamp ts = doc.getTimestamp("timestamp");
            if (ts != null) recordTime = ts.toDate();
        }

        String actionTimeStr = null;
        if (doc.contains("policeActionTime") && doc.get("policeActionTime") instanceof Timestamp) {
            Timestamp pts = doc.getTimestamp("policeActionTime");
            if (pts != null) {
                SimpleDateFormat timeFmt = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                actionTimeStr = "Cleared: " + timeFmt.format(pts.toDate());
            }
        }

        return new HistoryRecord(docId, tripId, patId, driver, source, dest, status, recordTime, actionTimeStr);
    }

    private void loadHistoryFromFirestore() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return Collections.<HistoryRecord>emptyList();

                QuerySnapshot snapshot = db.collection("policeEmergencyAlerts").get().get();

                return snapshot.getDocuments()
                        .parallelStream()
                        .map(PoliceHistory::mapDocToHistoryRecord)
                        .sorted((a, b) -> b.timestamp.compareTo(a.timestamp))
                        .collect(Collectors.toList());

            } catch (Exception e) {
                System.err.println("Error fetching police history: " + e.getMessage());
                return Collections.<HistoryRecord>emptyList();
            }
        }, WORKER_POOL).thenAccept(records -> {
            Platform.runLater(() -> {
                allRecordsList.clear();
                allRecordsList.addAll(records);
                updateKpiMetrics(allRecordsList);
                revealKpiBanner();
                filterAndRenderRecords();
            });
        });
    }

    // =========================================================
    // 4. DYNAMIC GROUPING & FILTERING WITH STAGGERED ENTRANCE
    // =========================================================
    private void filterAndRenderRecords() {
        if (historyShimmer != null) {
            historyShimmer.stop();
            historyShimmer = null;
        }
        historyCardsContainer.getChildren().clear();

        String query = searchField.getText() != null ? searchField.getText().trim().toLowerCase() : "";
        LocalDate selectedDate = datePicker.getValue();

        SimpleDateFormat compareFmt = new SimpleDateFormat("yyyy-MM-dd");

        List<HistoryRecord> filtered = new ArrayList<>();
        for (HistoryRecord r : allRecordsList) {
            boolean matchesSearch = query.isEmpty()
                    || r.tripId.toLowerCase().contains(query)
                    || r.destination.toLowerCase().contains(query)
                    || r.source.toLowerCase().contains(query)
                    || r.driverName.toLowerCase().contains(query)
                    || r.patId.toLowerCase().contains(query);

            boolean matchesDate = true;
            if (selectedDate != null) {
                String rDateStr = compareFmt.format(r.timestamp);
                String sDateStr = selectedDate.toString();
                matchesDate = rDateStr.equals(sDateStr);
            }

            if (matchesSearch && matchesDate) {
                filtered.add(r);
            }
        }

        showingCountBadgeText.setText("Showing " + filtered.size() + " of " + allRecordsList.size() + " Transits");

        if (filtered.isEmpty()) {
            VBox emptyBox = buildEmptyState();
            historyCardsContainer.getChildren().add(emptyBox);
            return;
        }

        Map<String, List<HistoryRecord>> grouped = new LinkedHashMap<>();
        for (HistoryRecord r : filtered) {
            grouped.computeIfAbsent(r.formattedDate, k -> new ArrayList<>()).add(r);
        }

        for (Map.Entry<String, List<HistoryRecord>> entry : grouped.entrySet()) {
            VBox dateBox = buildDateGroupCard(entry.getKey(), entry.getValue());
            historyCardsContainer.getChildren().add(dateBox);
        }

        // Staggered Cascade Entrance Animation for smooth SaaS feel
        int delay = 0;
        for (Node card : historyCardsContainer.getChildren()) {
            card.setOpacity(0);
            card.setTranslateY(18);

            FadeTransition ft = new FadeTransition(Duration.millis(320), card);
            ft.setToValue(1.0);

            TranslateTransition tt = new TranslateTransition(Duration.millis(320), card);
            tt.setToY(0);

            ParallelTransition pt = new ParallelTransition(ft, tt);
            pt.setDelay(Duration.millis(delay));
            pt.play();

            delay += 70;
        }
    }

    private VBox buildDateGroupCard(String dateTitle, List<HistoryRecord> records) {
        VBox dateBox = new VBox(14);
        dateBox.setPadding(new Insets(22, 24, 22, 24));
        dateBox.setStyle(CARD_STYLE);

        HBox dateHeader = new HBox(12);
        dateHeader.setAlignment(Pos.CENTER_LEFT);

        StackPane calIconHolder = new StackPane();
        calIconHolder.setPrefSize(34, 34);
        calIconHolder.setMinSize(34, 34);
        calIconHolder.setMaxSize(34, 34);
        calIconHolder.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 8px; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px;");
        Label calIcon = new Label("📅");
        calIcon.setStyle("-fx-font-size: 14px;");
        calIconHolder.getChildren().add(calIcon);

        Text dateText = new Text(dateTitle);
        dateText.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: 800; -fx-fill: " + PRIMARY_TEXT + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox countPill = new HBox(6);
        countPill.setAlignment(Pos.CENTER);
        countPill.setPadding(new Insets(4, 12, 4, 12));
        countPill.setStyle("-fx-background-color: " + ACCENT_TERRACOTTA_BG + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");
        Text countText = new Text(records.size() + (records.size() == 1 ? " Transit Cleared" : " Transits Cleared"));
        countText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: bold; -fx-fill: " + ACCENT_TERRACOTTA_TEXT + ";");
        countPill.getChildren().add(countText);

        dateHeader.getChildren().addAll(calIconHolder, dateText, spacer, countPill);
        dateBox.getChildren().add(dateHeader);

        for (HistoryRecord r : records) {
            dateBox.getChildren().add(buildHistoryRow(r));
        }

        return dateBox;
    }

    // =========================================================
    // 5. ELEVATED HISTORY ROW WITH TACTILE HOVER ANIMATION
    // =========================================================
    private HBox buildHistoryRow(HistoryRecord r) {
        HBox row = new HBox(18);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(14, 18, 14, 18));
        row.setStyle("-fx-background-color: " + SURFACE + "; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px; -fx-border-width: 1px; -fx-cursor: hand;");

        // Col 1: Rounded Unit Emblem Badge + Unit ID + Patient
        HBox unitCol = new HBox(12);
        unitCol.setAlignment(Pos.CENTER_LEFT);

        StackPane unitBadgeHolder = new StackPane();
        unitBadgeHolder.setPrefSize(42, 42);
        unitBadgeHolder.setMinSize(42, 42);
        unitBadgeHolder.setMaxSize(42, 42);
        unitBadgeHolder.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");
        Label ambIcon = new Label("🚑");
        ambIcon.setStyle("-fx-font-size: 18px;");
        unitBadgeHolder.getChildren().add(ambIcon);

        VBox unitDetails = new VBox(3);
        HBox idLine = new HBox(6);
        idLine.setAlignment(Pos.CENTER_LEFT);

        Text unitTitle = new Text(r.tripId);
        unitTitle.setStyle(FONT_FAMILY + "-fx-font-size: 14px; -fx-font-weight: 800; -fx-fill: " + PRIMARY_TEXT + ";");

        HBox patBadge = new HBox(4);
        patBadge.setPadding(new Insets(2, 6, 2, 6));
        patBadge.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 6px; -fx-border-color: " + BORDER + "; -fx-border-radius: 6px;");
        Text patText = new Text(r.patId);
        patText.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + SEC_TEXT + ";");
        patBadge.getChildren().add(patText);

        idLine.getChildren().addAll(unitTitle, patBadge);

        Text driverText = new Text("👤 " + r.driverName);
        driverText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-fill: " + SEC_TEXT + ";");

        unitDetails.getChildren().addAll(idLine, driverText);
        unitCol.getChildren().addAll(unitBadgeHolder, unitDetails);

        // Col 2: Route Visualizer (Origin -> Destination)
        VBox routeCol = new VBox(3);
        routeCol.setAlignment(Pos.CENTER_LEFT);

        HBox routeLine = new HBox(6);
        routeLine.setAlignment(Pos.CENTER_LEFT);
        Text fromText = new Text("📍 " + r.source);
        fromText.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-font-weight: 600; -fx-fill: " + PRIMARY_TEXT + ";");
        Text arrowText = new Text(" ➔ ");
        arrowText.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: 800; -fx-fill: " + ACCENT_TERRACOTTA + ";");
        Text toText = new Text("🏥 " + r.destination);
        toText.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        routeLine.getChildren().addAll(fromText, arrowText, toText);

        Text corridorTag = new Text("⚡ Automated Green Corridor Preemption Active");
        corridorTag.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-fill: " + SEC_TEXT + ";");

        routeCol.getChildren().addAll(routeLine, corridorTag);

        // Col 3: Timeline & Clearance Performance
        VBox timeCol = new VBox(3);
        timeCol.setAlignment(Pos.CENTER_LEFT);

        Text arrivalText = new Text("🕒 Dispatched: " + r.formattedTime);
        arrivalText.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-font-weight: 700; -fx-fill: " + PRIMARY_TEXT + ";");

        Text clearanceText = new Text(r.actionTimeStr);
        clearanceText.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: 600; -fx-fill: " + SEC_TEXT + ";");

        timeCol.getChildren().addAll(arrivalText, clearanceText);

        // Col 4: Modern Glowing Status Badge
        boolean isCleared = "CLEARED".equalsIgnoreCase(r.status) || "COMPLETED".equalsIgnoreCase(r.status);
        Label statusBadge = new Label(isCleared ? "●  CLEARED & LOGGED" : "●  PENDING INTERLOCK");
        statusBadge.setStyle(
                FONT_FAMILY + "-fx-background-color: " + (isCleared ? SUCCESS_GREEN_BG : ACCENT_TERRACOTTA_BG) + ";" +
                "-fx-text-fill: " + (isCleared ? SUCCESS_GREEN : ACCENT_TERRACOTTA_TEXT) + ";" +
                "-fx-font-size: 11px; -fx-font-weight: 800; -fx-padding: 6px 14px; -fx-background-radius: 20px; " +
                "-fx-border-color: " + (isCleared ? SUCCESS_GREEN : ACCENT_TERRACOTTA) + "; -fx-border-radius: 20px; -fx-border-width: 1px;"
        );

        HBox.setHgrow(unitCol, Priority.ALWAYS);
        HBox.setHgrow(routeCol, Priority.ALWAYS);
        HBox.setHgrow(timeCol, Priority.NEVER);

        row.getChildren().addAll(unitCol, routeCol, timeCol, statusBadge);

        // Smooth Tactile Hover Micro-Animation
        row.setOnMouseEntered(e -> {
            row.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 14px; -fx-border-color: " + ACCENT_TERRACOTTA + "; -fx-border-radius: 14px; -fx-border-width: 1.5px; -fx-cursor: hand;");
            row.setEffect(new DropShadow(12, 0, 3, Color.rgb(51, 38, 30, 0.08)));

            TranslateTransition tt = new TranslateTransition(Duration.millis(140), row);
            tt.setToX(4);
            tt.play();
        });

        row.setOnMouseExited(e -> {
            row.setStyle("-fx-background-color: " + SURFACE + "; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px; -fx-border-width: 1px; -fx-cursor: hand;");
            row.setEffect(null);

            TranslateTransition tt = new TranslateTransition(Duration.millis(140), row);
            tt.setToX(0);
            tt.play();
        });

        return row;
    }

    private VBox buildEmptyState() {
        VBox emptyBox = new VBox(12);
        emptyBox.setAlignment(Pos.CENTER);
        emptyBox.setPadding(new Insets(50, 40, 50, 40));
        emptyBox.setStyle(CARD_STYLE);

        StackPane radarIcon = new StackPane();
        radarIcon.setPrefSize(56, 56);
        radarIcon.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 28px; -fx-border-color: " + BORDER + "; -fx-border-radius: 28px;");
        Label rIcon = new Label("📡");
        rIcon.setStyle("-fx-font-size: 24px;");
        radarIcon.getChildren().add(rIcon);

        Text noDataTitle = new Text("No Ambulance Records Found");
        noDataTitle.setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: 800; -fx-fill: " + PRIMARY_TEXT + ";");

        Text noDataSub = new Text("No transit records match your current search query or date filter.");
        noDataSub.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-fill: " + SEC_TEXT + ";");

        Button resetBtn = new Button("Reset All Filters");
        resetBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + BROWN_DARK + "; -fx-text-fill: white; -fx-font-size: 12.5px; -fx-font-weight: bold; -fx-padding: 8 18; -fx-background-radius: 10px; -fx-cursor: hand;");
        addSpringHoverEffect(resetBtn, BROWN_DARK, ACCENT_TERRACOTTA, "#FFFFFF", "#FFFFFF");
        resetBtn.setOnAction(e -> {
            searchField.clear();
            datePicker.setValue(null);
        });

        emptyBox.getChildren().addAll(radarIcon, noDataTitle, noDataSub, resetBtn);
        return emptyBox;
    }

    // =========================================================
    // 6. BUTTON INTERACTIVE SPRING MICRO-ANIMATIONS
    // =========================================================
    private void addSpringHoverEffect(Button btn, String bgNormal, String bgHover, String textNormal, String textHover) {
        btn.setOnMouseEntered(e -> {
            btn.setStyle(FONT_FAMILY + "-fx-background-color: " + bgHover + "; -fx-text-fill: " + textHover + "; -fx-font-size: 12.5px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand;");
            ScaleTransition st = new ScaleTransition(Duration.millis(120), btn);
            st.setToX(1.03);
            st.setToY(1.03);
            st.play();
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle(FONT_FAMILY + "-fx-background-color: " + bgNormal + "; -fx-text-fill: " + textNormal + "; -fx-font-size: 12.5px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand;");
            ScaleTransition st = new ScaleTransition(Duration.millis(120), btn);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        btn.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(80), btn);
            st.setToX(0.96);
            st.setToY(0.96);
            st.play();
        });

        btn.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(80), btn);
            st.setToX(1.03);
            st.setToY(1.03);
            st.play();
        });
    }
}