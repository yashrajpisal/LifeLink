package com.kurukshetra.view.driver;

import com.kurukshetra.config.FirebaseConfig;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * DriverNotification
 * Stores all real-time dispatch alerts, hospital confirmations, and top-right corner toast notifications.
 * Provides a modern, rich notification center view for the Driver Console menu bar.
 */
public class DriverNotification {

    // Color Palette matching LifeLink Driver Theme
    private static final String PAGE_BG = "#ccdde7ff";
    private static final String SURFACE = "#FFFFFF";
    private static final String TEXT = "#0A2540";
    private static final String SECONDARY = "#4A6A85";
    private static final String MUTED = "#7EA8C1";
    private static final String BORDER = "#B8E4F5";
    private static final String BLUE = "#29B6E8";
    private static final String BLUE_LIGHT = "#E0F7FD";
    private static final String BLUE_DARK = "#0694C8";
    private static final String GREEN = "#10B981";
    private static final String GREEN_LIGHT = "#DCFCE7";
    private static final String RED = "#EF4444";
    private static final String RED_LIGHT = "#FEE2E2";
    private static final String AMBER = "#F59E0B";
    private static final String AMBER_LIGHT = "#FEF3C7";
    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, sans-serif; ";

    // =========================================================
    // NOTIFICATION DATA MODEL
    // =========================================================
    public static class NotificationItem {
        private final String id;
        private final String title;
        private final String message;
        private final String type;          // "emergency", "success", "warning", "info"
        private final String tripId;
        private final boolean isEmergency;
        private final LocalDateTime timestamp;
        private boolean isRead;

        public NotificationItem(String title, String message, String type, String tripId, boolean isEmergency) {
            this(UUID.randomUUID().toString(), title, message, type, tripId, isEmergency, LocalDateTime.now(), false);
        }

        public NotificationItem(String id, String title, String message, String type, String tripId, boolean isEmergency, LocalDateTime timestamp, boolean isRead) {
            this.id = id;
            this.title = title != null ? title : "Notification";
            this.message = message != null ? message : "";
            this.type = type != null ? type.toLowerCase() : "info";
            this.tripId = tripId != null ? tripId : "";
            this.isEmergency = isEmergency;
            this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
            this.isRead = isRead;
        }

        public String getId() { return id; }
        public String getTitle() { return title; }
        public String getMessage() { return message; }
        public String getType() { return type; }
        public String getTripId() { return tripId; }
        public boolean isEmergency() { return isEmergency; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public boolean isRead() { return isRead; }
        public void setRead(boolean read) { this.isRead = read; }

        public String getTimeFormatted() {
            return timestamp.format(DateTimeFormatter.ofPattern("hh:mm a • dd MMM yyyy", Locale.US));
        }

        public String getTimeAgo() {
            LocalDateTime now = LocalDateTime.now();
            long seconds = ChronoUnit.SECONDS.between(timestamp, now);
            if (seconds < 60) return "Just now";
            long minutes = ChronoUnit.MINUTES.between(timestamp, now);
            if (minutes < 60) return minutes + "m ago";
            long hours = ChronoUnit.HOURS.between(timestamp, now);
            if (hours < 24) return hours + "h ago";
            long days = ChronoUnit.DAYS.between(timestamp, now);
            if (days == 1) return "Yesterday";
            if (days < 7) return days + "d ago";
            return timestamp.format(DateTimeFormatter.ofPattern("dd MMM", Locale.US));
        }
    }

    // =========================================================
    // STATIC NOTIFICATION STORAGE & LISTENER
    // =========================================================
    private static final List<NotificationItem> notificationStore = Collections.synchronizedList(new ArrayList<>());
    private static final AtomicReference<Runnable> onNotificationChangedListener = new AtomicReference<>(null);
    private static volatile boolean hasInitializedHistory = false;

    /**
     * Records a notification into the permanent driver notification store.
     * Called whenever a top-right toast or milestone alert occurs.
     */
    public static void addNotification(String title, String message, String type, String tripId, boolean isEmergency) {
        // Guard against duplicate spamming of exact same notification in close succession
        synchronized (notificationStore) {
            for (NotificationItem existing : notificationStore) {
                if (existing.getTitle().equalsIgnoreCase(title)
                        && existing.getMessage().equals(message)
                        && ChronoUnit.SECONDS.between(existing.getTimestamp(), LocalDateTime.now()) < 3) {
                    return;
                }
            }
            NotificationItem item = new NotificationItem(title, message, type, tripId, isEmergency);
            notificationStore.add(0, item);
        }

        notifyChangeListener();
    }

    public static List<NotificationItem> getAllNotifications() {
        synchronized (notificationStore) {
            return new ArrayList<>(notificationStore);
        }
    }

    public static int getUnreadCount() {
        synchronized (notificationStore) {
            int count = 0;
            for (NotificationItem item : notificationStore) {
                if (!item.isRead()) count++;
            }
            return count;
        }
    }

    public static void markAllAsRead() {
        synchronized (notificationStore) {
            for (NotificationItem item : notificationStore) {
                item.setRead(true);
            }
        }
        notifyChangeListener();
    }

    public static void markAsRead(String id, boolean isRead) {
        synchronized (notificationStore) {
            for (NotificationItem item : notificationStore) {
                if (item.getId().equals(id)) {
                    item.setRead(isRead);
                    break;
                }
            }
        }
        notifyChangeListener();
    }

    public static void deleteNotification(String id) {
        synchronized (notificationStore) {
            notificationStore.removeIf(item -> item.getId().equals(id));
        }
        notifyChangeListener();
    }

    public static void clearAll() {
        synchronized (notificationStore) {
            notificationStore.clear();
        }
        notifyChangeListener();
    }

    public static void setOnNotificationChanged(Runnable listener) {
        onNotificationChangedListener.set(listener);
    }

    private static void notifyChangeListener() {
        Runnable r = onNotificationChangedListener.get();
        if (r != null) {
            Platform.runLater(r);
        }
    }

    /**
     * Loads past emergency requests from Firestore so that the notification
     * history is populated even immediately after launching the application.
     */
    public static void preloadHistoricalNotifications() {
        if (hasInitializedHistory) return;
        hasInitializedHistory = true;

        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return;

                QuerySnapshot snapshot = db.collection("adminEmergencyRequests")
                        .orderBy("timestamp", Query.Direction.DESCENDING)
                        .limit(10)
                        .get()
                        .get();

                if (snapshot != null && !snapshot.isEmpty()) {
                    List<NotificationItem> pastItems = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshot.getDocuments()) {
                        String tripId = doc.getString("tripID") != null ? doc.getString("tripID") : doc.getId();
                        String patId = doc.getString("patID") != null ? doc.getString("patID") : "Patient";
                        String source = doc.getString("source") != null ? doc.getString("source") : (doc.getString("pickupLocation") != null ? doc.getString("pickupLocation") : "Emergency Point");
                        String destination = doc.getString("destination") != null ? doc.getString("destination") : (doc.getString("destinationHospital") != null ? doc.getString("destinationHospital") : "Hospital ER");
                        String severity = doc.getString("severity") != null ? doc.getString("severity") : "URGENT";
                        String status = doc.getString("status") != null ? doc.getString("status") : "DISPATCHED";

                        String title = "Emergency Dispatch #" + tripId;
                        String msg = "Severity: [" + severity.toUpperCase() + "] • Patient: " + patId + "\nRoute: " + source + " ➜ " + destination + " • Status: " + status;

                        pastItems.add(new NotificationItem(
                                UUID.randomUUID().toString(),
                                title,
                                msg,
                                "emergency",
                                tripId,
                                true,
                                LocalDateTime.now().minusMinutes(pastItems.size() * 12L + 5),
                                true
                        ));
                    }

                    synchronized (notificationStore) {
                        for (NotificationItem item : pastItems) {
                            boolean exists = false;
                            for (NotificationItem cur : notificationStore) {
                                if (cur.getTripId().equals(item.getTripId()) && cur.getTitle().equals(item.getTitle())) {
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists) {
                                notificationStore.add(item);
                            }
                        }
                    }

                    notifyChangeListener();
                }
            } catch (Exception ex) {
                System.err.println("[DriverNotification] Historical preload note: " + ex.getMessage());
            }
        }).start();
    }

    // =========================================================
    // UI PAGE IMPLEMENTATION
    // =========================================================
    private final Runnable backToDashboardCallback;
    private VBox cardsContainer;
    private Label totalBadge;
    private Label emergencyBadge;
    private Label hospitalBadge;
    private Label unreadBadge;
    private String activeFilter = "ALL";
    private String searchQuery = "";
    private TextField searchInput;

    public DriverNotification() {
        this(null);
    }

    public DriverNotification(Runnable backToDashboardCallback) {
        this.backToDashboardCallback = backToDashboardCallback;
        preloadHistoricalNotifications();
    }

    public BorderPane getNotificationPage() {
        BorderPane page = new BorderPane();
        page.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        VBox content = new VBox(20);
        content.setPadding(new Insets(24, 32, 32, 32));

        // 1. Header Bar
        HBox headerBar = createHeaderBar();

        // 2. Metrics Ribbon
        HBox statsRibbon = createStatsRibbon();

        // 3. Search and Filter Strip
        VBox controlsBox = createControlsStrip();

        // 4. Notifications List Container
        cardsContainer = new VBox(12);
        cardsContainer.setAlignment(Pos.TOP_CENTER);

        ScrollPane scroll = new ScrollPane(cardsContainer);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-background: " + PAGE_BG + "; " +
                "-fx-border-color: transparent;"
        );
        VBox.setVgrow(scroll, Priority.ALWAYS);

        content.getChildren().addAll(headerBar, statsRibbon, controlsBox, scroll);
        page.setCenter(content);

        // Render current notifications
        refreshNotificationList();

        // Register live UI update listener
        setOnNotificationChanged(() -> {
            Platform.runLater(() -> {
                refreshNotificationList();
                updateStats();
            });
        });

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), page);
        fadeIn.setFromValue(0.4);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        return page;
    }

    // ---------------------------------------------------------
    // HEADER BAR
    // ---------------------------------------------------------
    private HBox createHeaderBar() {
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);

        if (backToDashboardCallback != null) {
            Button backBtn = new Button("‹");
            backBtn.setPrefSize(40, 40);
            backBtn.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + BLUE_LIGHT + "; " +
                    "-fx-text-fill: " + BLUE + "; " +
                    "-fx-font-size: 20px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 12px; " +
                    "-fx-cursor: hand;"
            );
            backBtn.setOnAction(e -> backToDashboardCallback.run());
            backBtn.setOnMouseEntered(e -> backBtn.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + BLUE + "; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 20px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 12px; " +
                    "-fx-cursor: hand;"
            ));
            backBtn.setOnMouseExited(e -> backBtn.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + BLUE_LIGHT + "; " +
                    "-fx-text-fill: " + BLUE + "; " +
                    "-fx-font-size: 20px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 12px; " +
                    "-fx-cursor: hand;"
            ));
            header.getChildren().add(backBtn);
        }

        VBox titleBox = new VBox(4);
        Label title = new Label("Notifications & Dispatch Alerts");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 28px; -fx-font-weight: 800; -fx-text-fill: " + TEXT + ";");

        Label sub = new Label("Complete archive of real-time top-right dispatch toasts, hospital admission approvals, and corridor notices.");
        sub.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-text-fill: " + SECONDARY + ";");
        titleBox.getChildren().addAll(title, sub);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button markAllBtn = new Button("✓ Mark All as Read");
        markAllBtn.setPrefHeight(38);
        markAllBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-text-fill: " + BLUE + "; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-width: 1px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-font-size: 12.5px; " +
                "-fx-font-weight: 600; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 16 0 16;"
        );
        markAllBtn.setOnAction(e -> markAllAsRead());
        markAllBtn.setOnMouseEntered(e -> markAllBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + BLUE_LIGHT + "; " +
                "-fx-text-fill: " + BLUE_DARK + "; " +
                "-fx-border-color: " + BLUE + "; " +
                "-fx-border-width: 1px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-font-size: 12.5px; " +
                "-fx-font-weight: 600; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 16 0 16;"
        ));
        markAllBtn.setOnMouseExited(e -> markAllBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-text-fill: " + BLUE + "; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-width: 1px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-font-size: 12.5px; " +
                "-fx-font-weight: 600; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 16 0 16;"
        ));

        Button clearAllBtn = new Button("🗑 Clear History");
        clearAllBtn.setPrefHeight(38);
        clearAllBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + RED_LIGHT + "; " +
                "-fx-text-fill: " + RED + "; " +
                "-fx-border-color: " + RED_LIGHT + "; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-font-size: 12.5px; " +
                "-fx-font-weight: 600; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 16 0 16;"
        );
        clearAllBtn.setOnAction(e -> clearAll());
        clearAllBtn.setOnMouseEntered(e -> clearAllBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + RED + "; " +
                "-fx-text-fill: white; " +
                "-fx-border-color: " + RED + "; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-font-size: 12.5px; " +
                "-fx-font-weight: 600; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 16 0 16;"
        ));
        clearAllBtn.setOnMouseExited(e -> clearAllBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + RED_LIGHT + "; " +
                "-fx-text-fill: " + RED + "; " +
                "-fx-border-color: " + RED_LIGHT + "; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px; " +
                "-fx-font-size: 12.5px; " +
                "-fx-font-weight: 600; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 16 0 16;"
        ));

        header.getChildren().addAll(titleBox, spacer, markAllBtn, clearAllBtn);
        return header;
    }

    // ---------------------------------------------------------
    // STATS METRIC RIBBON
    // ---------------------------------------------------------
    private HBox createStatsRibbon() {
        HBox ribbon = new HBox(16);
        ribbon.setAlignment(Pos.CENTER_LEFT);

        totalBadge = new Label("0");
        emergencyBadge = new Label("0");
        hospitalBadge = new Label("0");
        unreadBadge = new Label("0");

        VBox cardTotal = createStatCard("Total Logged", totalBadge, BLUE, BLUE_LIGHT, "🔔");
        VBox cardEmg = createStatCard("Critical Emergencies", emergencyBadge, RED, RED_LIGHT, "🚨");
        VBox cardHosp = createStatCard("Hospital Clearances", hospitalBadge, GREEN, GREEN_LIGHT, "🏥");
        VBox cardUnread = createStatCard("Unread Messages", unreadBadge, AMBER, AMBER_LIGHT, "✉");

        HBox.setHgrow(cardTotal, Priority.ALWAYS);
        HBox.setHgrow(cardEmg, Priority.ALWAYS);
        HBox.setHgrow(cardHosp, Priority.ALWAYS);
        HBox.setHgrow(cardUnread, Priority.ALWAYS);

        ribbon.getChildren().addAll(cardTotal, cardEmg, cardHosp, cardUnread);
        updateStats();
        return ribbon;
    }

    private VBox createStatCard(String label, Label valueLabel, String accentColor, String bgLight, String iconStr) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(14, 18, 14, 18));
        card.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-width: 1px; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-radius: 12px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 8, 0, 0, 2);"
        );

        HBox top = new HBox(10);
        top.setAlignment(Pos.CENTER_LEFT);

        StackPane iconHolder = new StackPane();
        iconHolder.setPrefSize(28, 28);
        iconHolder.setStyle("-fx-background-color: " + bgLight + "; -fx-background-radius: 8px;");
        Label icon = new Label(iconStr);
        icon.setStyle("-fx-font-size: 14px;");
        iconHolder.getChildren().add(icon);

        Label title = new Label(label);
        title.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: 600; -fx-text-fill: " + SECONDARY + ";");

        top.getChildren().addAll(iconHolder, title);

        valueLabel.setStyle(FONT_FAMILY + "-fx-font-size: 26px; -fx-font-weight: 800; -fx-text-fill: " + accentColor + ";");

        card.getChildren().addAll(top, valueLabel);
        return card;
    }

    private void updateStats() {
        int total = 0;
        int emg = 0;
        int hosp = 0;
        int unread = 0;

        synchronized (notificationStore) {
            total = notificationStore.size();
            for (NotificationItem item : notificationStore) {
                if (!item.isRead()) unread++;
                if (item.isEmergency() || "emergency".equalsIgnoreCase(item.getType())) emg++;
                if ("success".equalsIgnoreCase(item.getType()) || item.getTitle().toLowerCase().contains("hospital")) hosp++;
            }
        }

        if (totalBadge != null) totalBadge.setText(String.valueOf(total));
        if (emergencyBadge != null) emergencyBadge.setText(String.valueOf(emg));
        if (hospitalBadge != null) hospitalBadge.setText(String.valueOf(hosp));
        if (unreadBadge != null) unreadBadge.setText(String.valueOf(unread));
    }

    // ---------------------------------------------------------
    // FILTER & SEARCH STRIP
    // ---------------------------------------------------------
    private VBox createControlsStrip() {
        VBox box = new VBox(12);

        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);

        // Filter Pills
        HBox filterGroup = new HBox(8);
        filterGroup.setAlignment(Pos.CENTER_LEFT);

        Button btnAll = createFilterPill("All", "ALL");
        Button btnEmg = createFilterPill("🚨 Emergencies", "EMERGENCY");
        Button btnHosp = createFilterPill("🏥 Hospital", "HOSPITAL");
        Button btnSys = createFilterPill("ℹ System & Corridor", "SYSTEM");
        Button btnUnread = createFilterPill("✉ Unread Only", "UNREAD");

        filterGroup.getChildren().addAll(btnAll, btnEmg, btnHosp, btnSys, btnUnread);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Search Field
        HBox searchBox = new HBox(8);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(0, 12, 0, 12));
        searchBox.setPrefWidth(320);
        searchBox.setPrefHeight(38);
        searchBox.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-width: 1px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-radius: 8px;"
        );

        Label searchIcon = new Label("🔍");
        searchIcon.setStyle("-fx-font-size: 13px; -fx-text-fill: " + MUTED + ";");

        searchInput = new TextField();
        searchInput.setPromptText("Search by trip ID, message, or keyword...");
        searchInput.setStyle(
                FONT_FAMILY +
                "-fx-background-color: transparent; " +
                "-fx-border-color: transparent; " +
                "-fx-font-size: 12.5px; " +
                "-fx-text-fill: " + TEXT + "; " +
                "-fx-padding: 0;"
        );
        HBox.setHgrow(searchInput, Priority.ALWAYS);
        searchInput.textProperty().addListener((obs, oldVal, newVal) -> {
            searchQuery = newVal != null ? newVal.trim().toLowerCase() : "";
            refreshNotificationList();
        });

        searchBox.getChildren().addAll(searchIcon, searchInput);

        row.getChildren().addAll(filterGroup, spacer, searchBox);
        box.getChildren().add(row);
        return box;
    }

    private Button createFilterPill(String label, String filterKey) {
        Button btn = new Button(label);
        btn.setPrefHeight(34);
        boolean isActive = activeFilter.equalsIgnoreCase(filterKey);
        applyFilterPillStyle(btn, isActive);

        btn.setOnAction(e -> {
            activeFilter = filterKey;
            // Update styles of parent children
            if (btn.getParent() instanceof HBox) {
                for (Node child : ((HBox) btn.getParent()).getChildren()) {
                    if (child instanceof Button) {
                        Button b = (Button) child;
                        applyFilterPillStyle(b, b == btn);
                    }
                }
            }
            refreshNotificationList();
        });

        return btn;
    }

    private void applyFilterPillStyle(Button btn, boolean active) {
        if (active) {
            btn.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + BLUE + "; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 12.5px; " +
                    "-fx-font-weight: 700; " +
                    "-fx-background-radius: 20px; " +
                    "-fx-cursor: hand; " +
                    "-fx-padding: 0 16 0 16;"
            );
        } else {
            btn.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + SURFACE + "; " +
                    "-fx-text-fill: " + SECONDARY + "; " +
                    "-fx-border-color: " + BORDER + "; " +
                    "-fx-border-width: 1px; " +
                    "-fx-font-size: 12.5px; " +
                    "-fx-font-weight: 500; " +
                    "-fx-background-radius: 20px; " +
                    "-fx-border-radius: 20px; " +
                    "-fx-cursor: hand; " +
                    "-fx-padding: 0 16 0 16;"
            );
        }
    }

    // ---------------------------------------------------------
    // NOTIFICATION LIST RENDERING
    // ---------------------------------------------------------
    private void refreshNotificationList() {
        if (cardsContainer == null) return;
        cardsContainer.getChildren().clear();

        List<NotificationItem> items = getAllNotifications();
        List<NotificationItem> filtered = new ArrayList<>();

        for (NotificationItem item : items) {
            // Apply filter
            if ("UNREAD".equalsIgnoreCase(activeFilter) && item.isRead()) continue;
            if ("EMERGENCY".equalsIgnoreCase(activeFilter) && !item.isEmergency() && !"emergency".equalsIgnoreCase(item.getType())) continue;
            if ("HOSPITAL".equalsIgnoreCase(activeFilter) && !"success".equalsIgnoreCase(item.getType()) && !item.getTitle().toLowerCase().contains("hospital")) continue;
            if ("SYSTEM".equalsIgnoreCase(activeFilter) && (item.isEmergency() || "emergency".equalsIgnoreCase(item.getType()) || item.getTitle().toLowerCase().contains("hospital"))) continue;

            // Apply search
            if (!searchQuery.isEmpty()) {
                String full = (item.getTitle() + " " + item.getMessage() + " " + item.getTripId() + " " + item.getType()).toLowerCase();
                if (!full.contains(searchQuery)) continue;
            }

            filtered.add(item);
        }

        if (filtered.isEmpty()) {
            cardsContainer.getChildren().add(createEmptyState());
            return;
        }

        for (NotificationItem item : filtered) {
            HBox card = createNotificationCard(item);
            cardsContainer.getChildren().add(card);
        }
    }

    private HBox createNotificationCard(NotificationItem item) {
        HBox card = new HBox(16);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(16, 20, 16, 20));

        String borderColor = item.isRead() ? BORDER : BLUE;
        String cardBg = item.isRead() ? SURFACE : "#F8FBFF";
        double borderWidth = item.isRead() ? 1.0 : 1.5;

        card.setStyle(
                "-fx-background-color: " + cardBg + "; " +
                "-fx-border-color: " + borderColor + "; " +
                "-fx-border-width: " + borderWidth + "px; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-radius: 12px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 8, 0, 0, 2);"
        );

        // Icon indicator Circle
        String iconColor;
        String iconText;
        String typeLower = item.getType();

        if (item.isEmergency() || "emergency".equalsIgnoreCase(typeLower) || "danger".equalsIgnoreCase(typeLower)) {
            iconColor = RED;
            iconText = "🚨";
        } else if ("success".equalsIgnoreCase(typeLower)) {
            iconColor = GREEN;
            iconText = "✓";
        } else if ("warning".equalsIgnoreCase(typeLower)) {
            iconColor = AMBER;
            iconText = "⚠";
        } else {
            iconColor = BLUE;
            iconText = "ℹ";
        }

        StackPane iconCircle = new StackPane();
        Circle circle = new Circle(18);
        circle.setFill(Color.web(iconColor));
        Label iconLbl = new Label(iconText);
        iconLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
        iconCircle.getChildren().addAll(circle, iconLbl);

        // Content Area
        VBox contentBox = new VBox(5);
        HBox.setHgrow(contentBox, Priority.ALWAYS);

        // Top Metadata Row
        HBox metaRow = new HBox(10);
        metaRow.setAlignment(Pos.CENTER_LEFT);

        Label titleLbl = new Label(item.getTitle());
        titleLbl.setStyle(FONT_FAMILY + "-fx-font-size: 14px; -fx-font-weight: 700; -fx-text-fill: " + TEXT + ";");

        if (!item.isRead()) {
            Label newChip = new Label("NEW");
            newChip.setStyle(FONT_FAMILY + "-fx-background-color: " + BLUE + "; -fx-text-fill: white; -fx-font-size: 9.5px; -fx-font-weight: 800; -fx-padding: 2 6; -fx-background-radius: 4px;");
            metaRow.getChildren().addAll(titleLbl, newChip);
        } else {
            metaRow.getChildren().add(titleLbl);
        }

        if (!item.getTripId().isEmpty()) {
            Label tripChip = new Label("#" + item.getTripId());
            tripChip.setStyle(FONT_FAMILY + "-fx-background-color: #F1F5F9; -fx-text-fill: " + SECONDARY + "; -fx-font-size: 10.5px; -fx-font-weight: 600; -fx-padding: 2 8; -fx-background-radius: 4px;");
            metaRow.getChildren().add(tripChip);
        }

        Region metaSpacer = new Region();
        HBox.setHgrow(metaSpacer, Priority.ALWAYS);

        Label timeAgoLbl = new Label(item.getTimeAgo() + " (" + item.getTimeFormatted() + ")");
        timeAgoLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + MUTED + ";");

        metaRow.getChildren().addAll(metaSpacer, timeAgoLbl);

        // Message body
        Label msgLbl = new Label(item.getMessage());
        msgLbl.setWrapText(true);
        msgLbl.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-text-fill: " + (item.isRead() ? SECONDARY : TEXT) + "; -fx-line-spacing: 2px;");

        contentBox.getChildren().addAll(metaRow, msgLbl);

        // Quick Actions (Right side)
        HBox actions = new HBox(8);
        actions.setAlignment(Pos.CENTER_RIGHT);

        if (backToDashboardCallback != null && !item.getTripId().isEmpty()) {
            Button viewBtn = new Button("View Mission");
            viewBtn.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + BLUE_LIGHT + "; " +
                    "-fx-text-fill: " + BLUE + "; " +
                    "-fx-font-size: 11.5px; " +
                    "-fx-font-weight: 600; " +
                    "-fx-background-radius: 6px; " +
                    "-fx-cursor: hand; " +
                    "-fx-padding: 6 12;"
            );
            viewBtn.setOnAction(e -> {
                item.setRead(true);
                backToDashboardCallback.run();
            });
            actions.getChildren().add(viewBtn);
        }

        Button toggleReadBtn = new Button(item.isRead() ? "Mark Unread" : "Mark Read");
        toggleReadBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: transparent; " +
                "-fx-text-fill: " + SECONDARY + "; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-width: 1px; " +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: 500; " +
                "-fx-background-radius: 6px; " +
                "-fx-border-radius: 6px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 5 10;"
        );
        toggleReadBtn.setOnAction(e -> markAsRead(item.getId(), !item.isRead()));

        Button deleteBtn = new Button("×");
        deleteBtn.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-text-fill: " + MUTED + "; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 6;"
        );
        deleteBtn.setOnAction(e -> deleteNotification(item.getId()));
        deleteBtn.setOnMouseEntered(e -> deleteBtn.setStyle(
                "-fx-background-color: " + RED_LIGHT + "; " +
                "-fx-text-fill: " + RED + "; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 4px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 6;"
        ));
        deleteBtn.setOnMouseExited(e -> deleteBtn.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-text-fill: " + MUTED + "; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 6;"
        ));

        actions.getChildren().addAll(toggleReadBtn, deleteBtn);

        card.getChildren().addAll(iconCircle, contentBox, actions);

        // Hover Effect
        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: " + cardBg + "; " +
                "-fx-border-color: " + BLUE + "; " +
                "-fx-border-width: " + borderWidth + "px; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-radius: 12px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(8,161,229,0.12), 12, 0, 0, 4);"
        ));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: " + cardBg + "; " +
                "-fx-border-color: " + borderColor + "; " +
                "-fx-border-width: " + borderWidth + "px; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-radius: 12px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 8, 0, 0, 2);"
        ));

        return card;
    }

    // ---------------------------------------------------------
    // EMPTY STATE
    // ---------------------------------------------------------
    private VBox createEmptyState() {
        VBox box = new VBox(12);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(60, 20, 60, 20));
        box.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-width: 1px; " +
                "-fx-border-style: dashed; " +
                "-fx-background-radius: 16px; " +
                "-fx-border-radius: 16px;"
        );

        StackPane circleHolder = new StackPane();
        Circle c = new Circle(32);
        c.setFill(Color.web(BLUE_LIGHT));
        Label bell = new Label("🔔");
        bell.setStyle("-fx-font-size: 26px;");
        circleHolder.getChildren().addAll(c, bell);

        Label title = new Label("No Notifications Found");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: 700; -fx-text-fill: " + TEXT + ";");

        Label desc = new Label(
                searchQuery.isEmpty()
                        ? "All incoming dispatches, hospital approvals, and top-right alert toasts will be archived here automatically."
                        : "No notifications matched your current filter criteria: \"" + searchQuery + "\""
        );
        desc.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-text-fill: " + SECONDARY + ";");

        box.getChildren().addAll(circleHolder, title, desc);
        return box;
    }
}
