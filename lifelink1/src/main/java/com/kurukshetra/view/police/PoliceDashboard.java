package com.kurukshetra.view.police;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.controller.police.PoliceProfileController;
import com.kurukshetra.view.Welcome;
import com.kurukshetra.view.util.ShimmerLoader;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.ListenerRegistration;
import com.google.cloud.firestore.SetOptions;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
// import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.ScrollEvent;
import netscape.javascript.JSObject;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

public class PoliceDashboard extends Application {

    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, BlinkMacSystemFont, sans-serif; ";

    public static Stage dashboardStage;
    public static String loggedInPoliceEmail = null;

    public PoliceDashboard() {
    }

    public PoliceDashboard(String email) {
        if (email != null && !email.trim().isEmpty()) {
            loggedInPoliceEmail = email.trim();
            PoliceProfileController.loggedInEmail = email.trim();
        }
    }

    private Scene dashboardScene;
    private StackPane rootStack;
    private StackPane popupLayer;

    // =========================================================
    // MAIN CONTENT COLORS
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
    private static final String ACCENT_TERRACOTTA_TEXT = "#993C1D";   // text on ACCENT_TERRACOTTA_BGs
    private static final String SUCCESS_GREEN          = "#639922";   // "Completed"/"Cleared" badge text
    private static final String SUCCESS_GREEN_BG       = "#EAF3DE";   // "Completed"/"Cleared" badge background

    // =========================================================
    // SIDEBAR COLORS
    // =========================================================
    private static final String SIDEBAR_BG             = "#8a5941";   // dark espresso-brown sidebar background
    private static final String SIDEBAR_TEXT           = "#E8DCD1";   // default nav item text (light warm gray, not pure white)
    private static final String SIDEBAR_TEXT_MUTED     = "#edd7c1";   // section labels like "POLICE NAVIGATION", inactive icons
    private static final String SIDEBAR_ACTIVE_BG      = "#FBF5EF";   // active nav item background = ACCENT_TERRACOTTA
    private static final String SIDEBAR_ACTIVE_TEXT    = "#4A241C";   // text/icon on the active nav item
    private static final String SIDEBAR_HOVER_BG       = "#3D2A1F";   // subtle hover state on inactive nav items, one step lighter than SIDEBAR_BG
    private static final String SIDEBAR_BORDER         = "#3D2A1F";   // divider lines inside sidebar, if any
    private static final String SIDEBAR_SIGNOUT_BG     = "#4A241C";   // "Sign Out Shift" button background — dark red-brown
    private static final String SIDEBAR_SIGNOUT_TEXT   = "#F3B8A8";   // "Sign Out Shift" text color — light coral

    // Reusable Card Style
    private static final String CARD_STYLE = "-fx-background-color: " + SURFACE
            + "; -fx-background-radius: 18px; -fx-border-color: " + BORDER
            + "; -fx-border-radius: 18px; -fx-effect: dropshadow(gaussian, rgba(51, 38, 30, 0.07), 16, 0.10, 0, 4);";

    // Real-time dynamic UI references
    private VBox alertsContainer;
    private Text pendingCountText;
    private WebView mapWebView;
    private WebEngine mapWebEngine;
    private ListenerRegistration alertsListener;
    private ListenerRegistration emergencyRequestsListener;
    private ListenerRegistration missionProgressListener;
    private boolean isMapLoaded = false;
    private ShimmerLoader.ShimmerPane alertsShimmer;
    private ShimmerLoader.ShimmerPane mapShimmer;

    // Active Mission & Map Tracking state (synchronized with DriverDashboard)
    private String activeTripId = "";
    private String activeDestinationHospital = "NOBEL Hospital";
    private Double activeDestLat = null;
    private Double activeDestLng = null;
    private String activeSource = "Core2Web Pune";
    private Double activePickupLat = null;
    private Double activePickupLng = null;

    // JavaScript Bridge
    public class JavaControlRoomBridge {
        public void updateDistanceETA(String distanceStr, String etaStr) {
            Platform.runLater(() -> {
                System.out.println("[PoliceNavigation] Distance: " + distanceStr + " | ETA: " + etaStr);
            });
        }
    }
    private final JavaControlRoomBridge mapBridge = new JavaControlRoomBridge();

    // Track known alert IDs to detect new notifications for the pop-up
    private final Set<String> knownAlertDocIds = new HashSet<>();
    private boolean isInitialSnapshotLoaded = false;

    // =========================================================
    // THINGSPEAK LIVE GPS TELEMETRY
    // =========================================================
    private static final String CHANNEL_ID = "3484394";
    private static final String THINGSPEAK_URL = "https://api.thingspeak.com/channels/3484394/feeds.json?api_key=NGNFZNO5KV90OLMJ&results=2";
    private static final int UPDATE_INTERVAL = 5000;

    private volatile boolean isTrackingRunning = true;
    private Thread trackingThread;
    private Double liveAmbulanceLat = null;
    private Double liveAmbulanceLng = null;
    private double lastLatitude = 0;
    private double lastLongitude = 0;
    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private PoliceAlertModel activeSelectedAlert = null;

    public static class PoliceAlertModel {
        public String docId;
        public String tripId;
        public String patId;
        public String source;
        public String destination;
        public String status;
        public String timestampStr;
        public Timestamp rawTimestamp = null;
        public long epochMillis = System.currentTimeMillis();
        public double srcLat = 18.4852;
        public double srcLng = 73.8166;
        public Double destLat = null;
        public Double destLng = null;

        public PoliceAlertModel(String docId, String tripId, String patId, String source, String destination,
                                String status, String timestampStr) {
            this.docId = docId;
            this.tripId = tripId;
            this.patId = patId;
            this.source = source;
            this.destination = destination;
            this.status = status;
            this.timestampStr = timestampStr;
        }
    }

    private final List<PoliceAlertModel> activeAlertsList = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        dashboardStage = stage;

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        // =============================================================
        // 1. LEFT-SIDE MENU (UPGRADED DIMENSIONS: 260px)
        // =============================================================
        StackPane sidebarStack = new StackPane();
        sidebarStack.setPrefWidth(260);
        sidebarStack.setMinWidth(260);
        sidebarStack.setMaxWidth(260);
        sidebarStack.setEffect(new DropShadow(14, 0, 0, Color.rgb(0, 0, 0, 0.20)));

        Image bgImage = loadPoliceDashboardBackground();
        if (bgImage != null && !bgImage.isError()) {
            BackgroundSize backgroundSize = new BackgroundSize(
                    1.0, 1.0, true, true, false, true);
            BackgroundImage backgroundImage = new BackgroundImage(
                    bgImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    backgroundSize);
            sidebarStack.setBackground(new Background(backgroundImage));
            sidebarStack.setStyle("-fx-border-color: " + SIDEBAR_BORDER + "; -fx-border-width: 0px 1px 0px 0px;");
        } else {
            sidebarStack.setStyle("-fx-background-color: " + SIDEBAR_BG + "; -fx-border-color: " + SIDEBAR_BORDER
                    + "; -fx-border-width: 0px 1px 0px 0px;");
        }

        VBox leftMenu = new VBox(12);
        leftMenu.setPadding(new Insets(28, 18, 24, 18));
        leftMenu.setPrefWidth(260);
        leftMenu.setMinWidth(260);
        leftMenu.setMaxWidth(260);
        leftMenu.setMaxHeight(Double.MAX_VALUE);
        leftMenu.setStyle("-fx-background-color: transparent;");

        // Brand / Division Header Box
        HBox brandBox = new HBox(12);
        brandBox.setAlignment(Pos.CENTER_LEFT);
        brandBox.setPadding(new Insets(0, 4, 14, 4));

        StackPane logoHolder = new StackPane();
        logoHolder.setPrefSize(40, 40);
        logoHolder.setMinSize(40, 40);
        logoHolder.setMaxSize(40, 40);

        Image logoImg = loadLogoImage();
        if (logoImg != null && !logoImg.isError()) {
            ImageView logoView = new ImageView(logoImg);
            logoView.setFitWidth(40);
            logoView.setFitHeight(40);
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);

            Rectangle clip = new Rectangle(40, 40);
            clip.setArcWidth(12);
            clip.setArcHeight(12);
            logoView.setClip(clip);

            logoHolder.setStyle(
                    "-fx-background-radius: 12px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.35), 6, 0, 0, 2);"
            );
            logoHolder.getChildren().add(logoView);
        } else {
            logoHolder.setStyle("-fx-background-color: " + SIDEBAR_HOVER_BG + "; -fx-background-radius: 12px; -fx-border-color: " + SIDEBAR_BORDER + "; -fx-border-radius: 12px;");
            Label shieldIcon = new Label("🛡️");
            shieldIcon.setStyle("-fx-font-size: 20px;");
            logoHolder.getChildren().add(shieldIcon);
        }

        VBox titleTexts = new VBox(2);
        Text brandTitle = new Text("LifeLink");
        brandTitle.setStyle(FONT_FAMILY + "-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: " + SIDEBAR_TEXT + ";");
        Text brandSubtitle = new Text("Police Traffic Division");
        brandSubtitle.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-fill: " + SIDEBAR_TEXT_MUTED + ";");
        titleTexts.getChildren().addAll(brandTitle, brandSubtitle);

        brandBox.getChildren().addAll(logoHolder, titleTexts);

        // Navigation Section Label
        Label navLabel = new Label("POLICE NAVIGATION");
        navLabel.setStyle(FONT_FAMILY + "-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + SIDEBAR_TEXT_MUTED + "; -fx-letter-spacing: 0.6px; -fx-padding: 6 0 4 8;");

        // Menu Buttons
        Button dashboardButton = createMenuButton("▦", "Dashboard", true);
        Button historyButton = createMenuButton("🕒", "Clearance History", false);
        Button profileButton = createMenuButton("👮", "Officer Profile", false);

        // Spacer pushes profile and logout to bottom
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Profile Mini Card (Matching DriverDashboard & Police Theme)
        HBox profileBox = new HBox(12);
        profileBox.setAlignment(Pos.CENTER_LEFT);
        profileBox.setPadding(new Insets(10, 12, 14, 12));
        profileBox.setMaxWidth(Double.MAX_VALUE);

        String profileCardNormalStyle =
                "-fx-background-color: rgba(0, 0, 0, 0.40); " +
                "-fx-border-color: rgba(255, 255, 255, 0.18); " +
                "-fx-border-radius: 12px; " +
                "-fx-background-radius: 12px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.30), 8, 0, 0, 2); " +
                "-fx-cursor: hand;";

        String profileCardHoverStyle =
                "-fx-background-color: rgba(43, 29, 21, 0.70); " +
                "-fx-border-color: " + ACCENT_TERRACOTTA + "; " +
                "-fx-border-radius: 12px; " +
                "-fx-background-radius: 12px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(216, 90, 48, 0.35), 10, 0, 0, 2); " +
                "-fx-cursor: hand;";

        profileBox.setStyle(profileCardNormalStyle);
        profileBox.setOnMouseEntered(e -> profileBox.setStyle(profileCardHoverStyle));
        profileBox.setOnMouseExited(e -> profileBox.setStyle(profileCardNormalStyle));

        String officerEmail = (loggedInPoliceEmail != null && !loggedInPoliceEmail.isEmpty())
                ? loggedInPoliceEmail
                : PoliceProfileController.getEffectiveEmail();
        if (officerEmail == null || officerEmail.trim().isEmpty()) {
            officerEmail = "police1@lifelink.com";
        }
        String initialsText = extractInitials(officerEmail);

        StackPane avatarPane = new StackPane();
        avatarPane.setPrefSize(36, 36);
        avatarPane.setMinSize(36, 36);
        avatarPane.setMaxSize(36, 36);
        avatarPane.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, " + ACCENT_TERRACOTTA + ", " + BROWN_DARK + "); " +
                "-fx-background-radius: 18px;"
        );
        Text initials = new Text(initialsText);
        initials.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: white;");
        avatarPane.getChildren().add(initials);

        VBox profileDetails = new VBox(2);
        profileDetails.setAlignment(Pos.CENTER_LEFT);
        Text officerName = new Text(officerEmail);
        officerName.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #FFFFFF;");
        Text profileRole = new Text("Verified Traffic Officer");
        profileRole.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-fill: " + SIDEBAR_SIGNOUT_TEXT + ";");
        profileDetails.getChildren().addAll(officerName, profileRole);
        profileBox.getChildren().addAll(avatarPane, profileDetails);

        profileBox.setOnMouseClicked(event -> {
            resetMenuSelection(profileButton, dashboardButton, historyButton);
            PoliceProfile profile = new PoliceProfile();
            borderPane.setCenter(profile.getProfileView());
        });

        // Bottom Logout Button
        Button logoutButton = new Button("↩  Logout");
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setPrefHeight(46);
        logoutButton.setStyle(
                FONT_FAMILY + "-fx-background-color: " + SIDEBAR_SIGNOUT_BG + ";" +
                "-fx-text-fill: " + SIDEBAR_SIGNOUT_TEXT + ";" +
                "-fx-font-size: 13.5px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: " + SIDEBAR_BORDER + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-cursor: hand;"
        );
        logoutButton.setOnMouseEntered(e -> {
            logoutButton.setStyle(
                    FONT_FAMILY + "-fx-background-color: " + ACCENT_TERRACOTTA + ";" +
                    "-fx-text-fill: #FFFFFF;" +
                    "-fx-font-size: 13.5px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 12px;" +
                    "-fx-border-color: " + ACCENT_TERRACOTTA + ";" +
                    "-fx-border-radius: 12px;" +
                    "-fx-cursor: hand;"
            );
            logoutButton.setTranslateY(-2);
        });
        logoutButton.setOnMouseExited(e -> {
            logoutButton.setStyle(
                    FONT_FAMILY + "-fx-background-color: " + SIDEBAR_SIGNOUT_BG + ";" +
                    "-fx-text-fill: " + SIDEBAR_SIGNOUT_TEXT + ";" +
                    "-fx-font-size: 13.5px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 12px;" +
                    "-fx-border-color: " + SIDEBAR_BORDER + ";" +
                    "-fx-border-radius: 12px;" +
                    "-fx-cursor: hand;"
            );
            logoutButton.setTranslateY(0);
        });

        leftMenu.getChildren().addAll(
                brandBox,
                navLabel,
                dashboardButton,
                historyButton,
                profileButton,
                spacer,
                profileBox,
                logoutButton
        );
        sidebarStack.getChildren().add(leftMenu);
        borderPane.setLeft(sidebarStack);

        // =============================================================
        // 2. MAIN DASHBOARD CONTENT LAYOUT
        // =============================================================
        VBox dashboard = new VBox(22);
        dashboard.setPadding(new Insets(28, 36, 32, 36));
        dashboard.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(dashboard, Priority.ALWAYS);

        // Header Box with live status badge
        HBox topHeaderRow = new HBox(16);
        topHeaderRow.setAlignment(Pos.CENTER_LEFT);

        VBox headingBox = new VBox(4);

        Text heading = new Text("Control Room Operations");
        heading.setStyle(FONT_FAMILY + "-fx-font-size: 28px; -fx-font-weight: 800; -fx-fill: " + PRIMARY_TEXT + ";");

        headingBox.getChildren().addAll( heading);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        topHeaderRow.getChildren().addAll(headingBox, headerSpacer);

        // Main 2-Column Stage (Center Map & Right Alerts)
        HBox mainContent = new HBox(24);
        mainContent.setAlignment(Pos.TOP_LEFT);
        mainContent.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(mainContent, Priority.ALWAYS);
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        // ================= CENTER PART: EXPANDED MAP BOX =================
        VBox centerPart = new VBox(14);
        centerPart.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(centerPart, Priority.ALWAYS);
        VBox.setVgrow(centerPart, Priority.ALWAYS);

        HBox incomingHeader = new HBox(12);
        incomingHeader.setAlignment(Pos.CENTER_LEFT);

        Text incomingText = new Text("Incoming Ambulances");
        incomingText.setStyle(FONT_FAMILY + "-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        HBox liveTag = new HBox(6);
        liveTag.setAlignment(Pos.CENTER);
        liveTag.setPadding(new Insets(3, 10, 3, 10));
        liveTag.setStyle("-fx-background-color: " + ACCENT_TERRACOTTA_BG + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");
        Text liveText = new Text("● LIVE GPS CORRIDOR");
        liveText.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + ACCENT_TERRACOTTA_TEXT + ";");
        liveTag.getChildren().add(liveText);

        incomingHeader.getChildren().addAll(incomingText, liveTag);

        // MAP CARD CONTAINER WITH FULL-WIDTH STRETCH & ENHANCED PADDING
        VBox mapCardContainer = new VBox(14);
        mapCardContainer.setPadding(new Insets(20));
        mapCardContainer.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(mapCardContainer, Priority.ALWAYS);
        VBox.setVgrow(mapCardContainer, Priority.ALWAYS);
        mapCardContainer.setStyle(CARD_STYLE);

        // Header inside Map Card
        HBox mapCardHeader = new HBox(10);
        mapCardHeader.setAlignment(Pos.CENTER_LEFT);

        StackPane mapIconHolder = new StackPane();
        mapIconHolder.setPrefSize(32, 32);
        mapIconHolder.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 8px; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px;");
        Label mIcon = new Label("🚨");
        mIcon.setStyle("-fx-font-size: 15px;");
        mapIconHolder.getChildren().add(mIcon);

        Text mapMissionTitle = new Text("Active Emergency Corridor Tracking");
        mapMissionTitle.setStyle(FONT_FAMILY + "-fx-font-size: 15.5px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Region mapHeaderSpacer = new Region();
        HBox.setHgrow(mapHeaderSpacer, Priority.ALWAYS);

        Label priorityBadge = new Label("• HIGH PRIORITY TRANSIT");
        priorityBadge.setStyle(
                FONT_FAMILY + "-fx-background-color: " + ACCENT_TERRACOTTA_BG + ";" +
                "-fx-text-fill: " + ACCENT_TERRACOTTA_TEXT + ";" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 11px;" +
                "-fx-padding: 5px 12px;" +
                "-fx-background-radius: 8px;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 8px;"
        );

        mapCardHeader.getChildren().addAll(mapIconHolder, mapMissionTitle, mapHeaderSpacer, priorityBadge);

        // Enlarged WebView with dynamic responsive clipping
        mapWebView = new WebView();
        mapWebView.setMaxWidth(Double.MAX_VALUE);
        mapWebView.setPrefHeight(470);
        mapWebView.setMinHeight(450);
        VBox.setVgrow(mapWebView, Priority.ALWAYS);

        Rectangle dynamicClip = new Rectangle();
        dynamicClip.setArcWidth(16);
        dynamicClip.setArcHeight(16);
        dynamicClip.widthProperty().bind(mapWebView.widthProperty());
        dynamicClip.heightProperty().bind(mapWebView.heightProperty());
        mapWebView.setClip(dynamicClip);

        mapWebEngine = mapWebView.getEngine();
        mapWebEngine.setJavaScriptEnabled(true);
        var mapUrl = getClass().getResource("/driver_map.html");
        if (mapUrl != null) {
            mapWebEngine.load(mapUrl.toExternalForm());
        } else {
            String resolved = resolveDriverMapUrl();
            if (resolved != null) {
                mapWebEngine.load(resolved);
            } else {
                mapWebEngine.loadContent(getCleanLeafletTemplate(), "text/html");
            }
        }

        mapWebEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                try {
                    JSObject window = (JSObject) mapWebEngine.executeScript("window");
                    window.setMember("javaControlRoom", mapBridge);
                } catch (Exception ignored) {}
                isMapLoaded = true;
                if (activeSelectedAlert != null) {
                    plotRouteWithDirectCoordinates(activeSelectedAlert.srcLat, activeSelectedAlert.srcLng, activeSelectedAlert.destLat, activeSelectedAlert.destLng, activeSelectedAlert.destination);
                } else if (!activeAlertsList.isEmpty()) {
                    PoliceAlertModel first = activeAlertsList.get(0);
                    plotRouteWithDirectCoordinates(first.srcLat, first.srcLng, first.destLat, first.destLng, first.destination);
                } else {
                    plotRouteWithDirectCoordinates(activePickupLat, activePickupLng, activeDestLat, activeDestLng, activeDestinationHospital);
                }
            }
        });

        Button navigateCorridorBtn = new Button("🧭 NAVIGATE GREEN CORRIDOR & CLEAR SIGNALS");
        navigateCorridorBtn.setMaxWidth(Double.MAX_VALUE);
        navigateCorridorBtn.setPrefHeight(44);
        navigateCorridorBtn.setStyle(
                "-fx-background-color: #0284c7;" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 13px;" +
                "-fx-background-radius: 10px;" +
                "-fx-cursor: hand;");
        navigateCorridorBtn.setOnAction(e -> {
            PoliceAlertModel alert = (activeSelectedAlert != null) ? activeSelectedAlert : (!activeAlertsList.isEmpty() ? activeAlertsList.get(0) : null);
            if (alert != null) {
                plotRouteWithDirectCoordinates(alert.srcLat, alert.srcLng, alert.destLat, alert.destLng, alert.destination);
            } else {
                plotRouteWithDirectCoordinates(activePickupLat, activePickupLng, activeDestLat, activeDestLng, activeDestinationHospital);
            }
        });

        mapCardContainer.getChildren().addAll(mapCardHeader, mapWebView, navigateCorridorBtn);
        centerPart.getChildren().addAll(incomingHeader, mapCardContainer);

        // ================= RIGHT PART: CLEARANCE ALERTS (WIDTH: 410px) =================
        VBox rightPart = new VBox(20);
        rightPart.setPrefWidth(410);
        rightPart.setMinWidth(390);
        rightPart.setMaxWidth(430);

        VBox clearanceCard = new VBox(16);
        clearanceCard.setPadding(new Insets(22));
        clearanceCard.setStyle(CARD_STYLE);

        HBox clearanceHeader = new HBox(8);
        clearanceHeader.setAlignment(Pos.CENTER_LEFT);

        Text clearanceTitle = new Text("Clearance Alerts");
        clearanceTitle.setStyle(FONT_FAMILY + "-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Region clearanceSpacer = new Region();
        HBox.setHgrow(clearanceSpacer, Priority.ALWAYS);

        HBox countBadge = new HBox(6);
        countBadge.setAlignment(Pos.CENTER);
        countBadge.setPadding(new Insets(4, 10, 4, 10));
        countBadge.setStyle("-fx-background-color: " + ACCENT_TERRACOTTA_BG + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");

        pendingCountText = new Text("0 Pending");
        pendingCountText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-fill: " + ACCENT_TERRACOTTA_TEXT + "; -fx-font-weight: bold;");
        countBadge.getChildren().add(pendingCountText);

        clearanceHeader.getChildren().addAll(clearanceTitle, clearanceSpacer, countBadge);

        alertsContainer = new VBox(12);
        alertsShimmer = ShimmerLoader.createPoliceAlertListSkeleton(360, 3);
        alertsContainer.getChildren().add(alertsShimmer);

        ScrollPane alertsScroll = new ScrollPane(alertsContainer);
        alertsScroll.setFitToWidth(true);
        alertsScroll.setPrefHeight(510);
        alertsScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        applyHiddenScrollbars(alertsScroll);

        clearanceCard.getChildren().addAll(clearanceHeader, alertsScroll);
        rightPart.getChildren().add(clearanceCard);

        mainContent.getChildren().addAll(centerPart, rightPart);
        dashboard.getChildren().addAll(topHeaderRow, mainContent);

        // ScrollPane stretching edge-to-edge
        ScrollPane scrollPane = new ScrollPane(dashboard);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");
        applyHiddenScrollbars(scrollPane);

        borderPane.setCenter(scrollPane);

        // Navigation Actions
        dashboardButton.setOnAction(event -> {
            resetMenuSelection(dashboardButton, historyButton, profileButton);
            borderPane.setCenter(scrollPane);
        });

        historyButton.setOnAction(event -> {
            resetMenuSelection(historyButton, dashboardButton, profileButton);
            PoliceHistory history = new PoliceHistory();
            borderPane.setCenter(history.getHistoryView());
        });

        profileButton.setOnAction(event -> {
            resetMenuSelection(profileButton, dashboardButton, historyButton);
            PoliceProfile profile = new PoliceProfile();
            borderPane.setCenter(profile.getProfileView());
        });

        logoutButton.setOnAction(event -> {
            try {
                isTrackingRunning = false;
                if (alertsListener != null) alertsListener.remove();
                if (emergencyRequestsListener != null) emergencyRequestsListener.remove();
                if (missionProgressListener != null) missionProgressListener.remove();
                Welcome welcome = new Welcome();
                welcome.start(stage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // POP-UP NOTIFICATION OVERLAY LAYER (POSITIONED AT TOP RIGHT CORNER)
        popupLayer = new StackPane();
        popupLayer.setPickOnBounds(false);
        popupLayer.setAlignment(Pos.TOP_RIGHT);
        popupLayer.setPadding(new Insets(20, 24, 0, 0));

        rootStack = new StackPane(borderPane, popupLayer);

        // Initialize Firestore Real-time listeners
        startPoliceAlertsListener();
        startAdminEmergencyRequestsListener();
        startMissionProgressListener();

        // Staggered Page Entrance Animation
        dashboard.setOpacity(0);
        dashboard.setTranslateY(16);
        FadeTransition ft = new FadeTransition(Duration.millis(350), dashboard);
        ft.setToValue(1.0);
        TranslateTransition tt = new TranslateTransition(Duration.millis(350), dashboard);
        tt.setToY(0);
        new ParallelTransition(ft, tt).play();

        // Full Screen Resolution Fix
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        dashboardScene = new Scene(rootStack, visualBounds.getWidth(), visualBounds.getHeight());

        dashboardStage.setX(visualBounds.getMinX());
        dashboardStage.setY(visualBounds.getMinY());
        dashboardStage.setWidth(visualBounds.getWidth());
        dashboardStage.setHeight(visualBounds.getHeight());

        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("LifeLink - Police Control Room Command Desk");
        dashboardStage.setMaximized(true);
        dashboardStage.show();

        startLiveTracking();

        dashboardStage.setOnCloseRequest(e -> {
            isTrackingRunning = false;
            if (alertsListener != null) alertsListener.remove();
            if (emergencyRequestsListener != null) emergencyRequestsListener.remove();
            if (missionProgressListener != null) missionProgressListener.remove();
            Platform.exit();
            System.exit(0);
        });
    }

    private Button createMenuButton(String iconStr, String textStr, boolean isActive) {
        HBox box = new HBox(12);
        box.setAlignment(Pos.CENTER_LEFT);

        Text icon = new Text(iconStr);
        icon.setStyle(FONT_FAMILY + "-fx-font-size: 15px; -fx-fill: " + (isActive ? SIDEBAR_ACTIVE_TEXT : SIDEBAR_TEXT_MUTED) + ";");

        Text label = new Text(textStr);
        label.setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: " + (isActive ? "bold" : "normal") + "; -fx-fill: " + (isActive ? SIDEBAR_ACTIVE_TEXT : SIDEBAR_TEXT) + ";");

        box.getChildren().addAll(icon, label);

        Button btn = new Button();
        btn.setGraphic(box);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(46);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(0, 16, 0, 16));

        if (isActive) {
            btn.setStyle("-fx-background-color: " + SIDEBAR_ACTIVE_BG + "; -fx-background-radius: 12px; -fx-cursor: hand; -fx-border-color: " + SIDEBAR_ACTIVE_BG + "; -fx-border-radius: 12px;");
        } else {
            btn.setStyle("-fx-background-color: transparent; -fx-background-radius: 12px; -fx-cursor: hand;");
        }

        btn.setOnMouseEntered(e -> {
            if (!btn.getStyle().contains(SIDEBAR_ACTIVE_BG)) {
                btn.setStyle("-fx-background-color: " + SIDEBAR_HOVER_BG + "; -fx-background-radius: 12px; -fx-cursor: hand;");
            }
            btn.setTranslateX(3);
        });

        btn.setOnMouseExited(e -> {
            if (!btn.getStyle().contains(SIDEBAR_ACTIVE_BG)) {
                btn.setStyle("-fx-background-color: transparent; -fx-background-radius: 12px; -fx-cursor: hand;");
            }
            btn.setTranslateX(0);
        });

        return btn;
    }

    private void resetMenuSelection(Button activeBtn, Button... inactiveBtns) {
        HBox aBox = (HBox) activeBtn.getGraphic();
        ((Text) aBox.getChildren().get(0)).setStyle(FONT_FAMILY + "-fx-font-size: 15px; -fx-fill: " + SIDEBAR_ACTIVE_TEXT + ";");
        ((Text) aBox.getChildren().get(1)).setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: " + SIDEBAR_ACTIVE_TEXT + ";");
        activeBtn.setStyle("-fx-background-color: " + SIDEBAR_ACTIVE_BG + "; -fx-background-radius: 12px; -fx-cursor: hand; -fx-border-color: " + SIDEBAR_ACTIVE_BG + "; -fx-border-radius: 12px;");

        for (Button btn : inactiveBtns) {
            HBox box = (HBox) btn.getGraphic();
            ((Text) box.getChildren().get(0)).setStyle(FONT_FAMILY + "-fx-font-size: 15px; -fx-fill: " + SIDEBAR_TEXT_MUTED + ";");
            ((Text) box.getChildren().get(1)).setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: normal; -fx-fill: " + SIDEBAR_TEXT + ";");
            btn.setStyle("-fx-background-color: transparent; -fx-background-radius: 12px; -fx-cursor: hand;");
        }
    }

    private Image loadLogoImage() {
        String[] resourcePaths = {
                "/assets/Images/lifelinklogonew.png",
                "/assets/Images/LifeLinkLogo.png",
                "/lifelinklogonew.png"
        };
        for (String resPath : resourcePaths) {
            try {
                var url = PoliceDashboard.class.getResource(resPath);
                if (url != null) {
                    Image img = new Image(url.toExternalForm(), false);
                    if (!img.isError()) {
                        return img;
                    }
                }
            } catch (Exception ignored) {}
        }

        String[] filePaths = {
                "src/main/resources/assets/Images/lifelinklogonew.png",
                "lifelink1/src/main/resources/assets/Images/lifelinklogonew.png",
                "src/main/resources/assets/Images/LifeLinkLogo.png",
                "lifelink1/src/main/resources/assets/Images/LifeLinkLogo.png"
        };
        for (String filePath : filePaths) {
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    Image img = new Image(file.toURI().toString(), false);
                    if (!img.isError()) {
                        return img;
                    }
                }
            } catch (Exception ignored) {}
        }

        return null;
    }

    private Image loadPoliceDashboardBackground() {
        String[] resourcePaths = {
                "/assets/Images/policeDashboardbackground.png",
                "/policeDashboardbackground.png"
        };
        for (String resPath : resourcePaths) {
            try {
                var url = PoliceDashboard.class.getResource(resPath);
                if (url != null) {
                    Image img = new Image(url.toExternalForm(), false);
                    if (!img.isError()) {
                        return img;
                    }
                }
            } catch (Exception ignored) {}
        }

        String[] filePaths = {
                "src/main/resources/assets/Images/policeDashboardbackground.png",
                "lifelink1/src/main/resources/assets/Images/policeDashboardbackground.png",
                "assets/Images/policeDashboardbackground.png"
        };
        for (String filePath : filePaths) {
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    Image img = new Image(file.toURI().toString(), false);
                    if (!img.isError()) {
                        return img;
                    }
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

    private static String extractInitials(String email) {
        if (email == null || email.trim().isEmpty()) return "PO";
        int atIdx = email.indexOf('@');
        String namePart = atIdx > 0 ? email.substring(0, atIdx) : email;
        if (namePart.length() >= 2) {
            return namePart.substring(0, 2).toUpperCase();
        }
        return namePart.toUpperCase();
    }

    public static void applyHiddenScrollbars(ScrollPane sp) {
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setPannable(true);
        sp.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() != 0 && sp.getContent() != null) {
                double deltaY = event.getDeltaY();
                double contentHeight = sp.getContent().getBoundsInLocal().getHeight();
                double viewportHeight = sp.getViewportBounds().getHeight();
                if (viewportHeight <= 0) {
                    viewportHeight = sp.getHeight();
                }
                double scrollableDistance = contentHeight - viewportHeight;
                if (viewportHeight > 0 && scrollableDistance > 1.0) {
                    double currentV = sp.getVvalue();
                    boolean canScrollUp = deltaY > 0 && currentV > 0.001;
                    boolean canScrollDown = deltaY < 0 && currentV < 0.999;
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

    // =========================================================
    // 1. DRIVER MAP HTML RESOLVER & FALLBACK TEMPLATE
    // =========================================================
    private String resolveDriverMapUrl() {
        try {
            var url = getClass().getResource("/driver_map.html");
            if (url != null) return url.toExternalForm();
        } catch (Exception ignored) {}
        try {
            var url = PoliceDashboard.class.getResource("/driver_map.html");
            if (url != null) return url.toExternalForm();
        } catch (Exception ignored) {}
        try {
            var url = Thread.currentThread().getContextClassLoader().getResource("driver_map.html");
            if (url != null) return url.toExternalForm();
        } catch (Exception ignored) {}
        String[] candidatePaths = {
                "src/main/resources/driver_map.html",
                "lifelink1/src/main/resources/driver_map.html",
                "target/classes/driver_map.html",
                "lifelink1/target/classes/driver_map.html"
        };
        for (String path : candidatePaths) {
            File f = new File(path);
            if (f.exists()) {
                return f.toURI().toASCIIString();
            }
        }
        return null;
    }

    private String getCleanLeafletTemplate() {
        try (java.io.InputStream is = getClass().getResourceAsStream("/driver_map.html")) {
            if (is != null) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (Exception ignored) {}
        try (java.io.InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("driver_map.html")) {
            if (is != null) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (Exception ignored) {}
        String[] candidatePaths = {
                "src/main/resources/driver_map.html",
                "lifelink1/src/main/resources/driver_map.html",
                "target/classes/driver_map.html",
                "lifelink1/target/classes/driver_map.html"
        };
        for (String path : candidatePaths) {
            File f = new File(path);
            if (f.exists()) {
                try {
                    return java.nio.file.Files.readString(f.toPath(), StandardCharsets.UTF_8);
                } catch (Exception ignored) {}
            }
        }
        return getFallbackDriverMapHtml();
    }

    private String getFallbackDriverMapHtml() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Driver Clean Map</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.css\" />\n" +
                "    <style>\n" +
                "        * { box-sizing: border-box; margin: 0; padding: 0; }\n" +
                "        html, body {\n" +
                "            width: 100%; height: 100%; margin: 0; padding: 0;\n" +
                "            overflow: hidden; background-color: #f8fafc;\n" +
                "            font-family: 'Segoe UI', -apple-system, sans-serif;\n" +
                "        }\n" +
                "        #map { width: 100%; height: 100%; display: block; background: #f8fafc; }\n" +
                "        .leaflet-container { width: 100% !important; height: 100% !important; background: #f8fafc !important; }\n" +
                "        .leaflet-tile { visibility: visible !important; }\n" +
                "        .leaflet-tile-container img { width: 256.5px !important; height: 256.5px !important; }\n" +
                "        .badge-pickup {\n" +
                "            background: #ef4444 !important;\n" +
                "            color: #ffffff !important;\n" +
                "            border: 2px solid #ffffff !important;\n" +
                "            border-radius: 6px !important;\n" +
                "            font-weight: 800 !important;\n" +
                "            font-size: 11px !important;\n" +
                "            padding: 4px 8px !important;\n" +
                "            box-shadow: 0 2px 8px rgba(239, 68, 68, 0.35) !important;\n" +
                "        }\n" +
                "        .badge-destination {\n" +
                "            background: #0284c7 !important;\n" +
                "            color: #ffffff !important;\n" +
                "            border: 2px solid #ffffff !important;\n" +
                "            border-radius: 6px !important;\n" +
                "            font-weight: 800 !important;\n" +
                "            font-size: 11px !important;\n" +
                "            padding: 4px 8px !important;\n" +
                "            box-shadow: 0 2px 8px rgba(2, 132, 199, 0.35) !important;\n" +
                "        }\n" +
                "        .marker-amb {\n" +
                "            width: 38px; height: 38px; background: #ffffff;\n" +
                "            border-radius: 50%; display: flex; align-items: center; justify-content: center;\n" +
                "            font-size: 20px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);\n" +
                "        }\n" +
                "        .marker-dest-pin {\n" +
                "            width: 34px; height: 34px; background: #0284c7;\n" +
                "            border: 2.5px solid #ffffff; border-radius: 50%;\n" +
                "            display: flex; align-items: center; justify-content: center;\n" +
                "            font-size: 16px; color: white; box-shadow: 0 2px 10px rgba(2, 132, 199, 0.4);\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"map\"></div>\n" +
                "<script src=\"https://unpkg.com/leaflet@1.9.4/dist/leaflet.js\"></script>\n" +
                "<script>\n" +
                "    L.Browser.any3d = false;\n" +
                "    let map, destMarker, ambulanceMarker, corridorCasing, corridorCore;\n" +
                "    let routeCoords = [];\n" +
                "    let currentDestLoc = null;\n" +
                "    const ambIcon = L.divIcon({ className: '', html: '<div class=\"marker-amb\">&#128657;</div>', iconSize: [38, 38], iconAnchor: [19, 19] });\n" +
                "    const destIcon = L.divIcon({ className: '', html: '<div class=\"marker-dest-pin\">&#127973;</div>', iconSize: [34, 34], iconAnchor: [17, 17] });\n" +
                "\n" +
                "    function initMap() {\n" +
                "        if (map) return;\n" +
                "        map = L.map('map', { \n" +
                "            zoomControl: false, \n" +
                "            attributionControl: false,\n" +
                "            zoomAnimation: false, \n" +
                "            fadeAnimation: false \n" +
                "        }).setView([18.4800, 73.8600], 13);\n" +
                "\n" +
                "        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {\n" +
                "            maxZoom: 19,\n" +
                "            tileSize: 256\n" +
                "        }).addTo(map);\n" +
                "\n" +
                "        const resizeObserver = new ResizeObserver(() => {\n" +
                "            if (map) map.invalidateSize(true);\n" +
                "        });\n" +
                "        resizeObserver.observe(document.getElementById('map'));\n" +
                "    }\n" +
                "\n" +
                "    async function geocodeLocation(nameStr) {\n" +
                "        const query = (nameStr || '').trim().toLowerCase();\n" +
                "        if (query.includes('swargate')) return { lat: 18.5018, lng: 73.8636, name: 'Swargate' };\n" +
                "        if (query.includes('katraj')) return { lat: 18.4575, lng: 73.8677, name: 'Katraj' };\n" +
                "        if (query.includes('core2web') || query.includes('narhe')) return { lat: 18.4852, lng: 73.8166, name: 'Core2web' };\n" +
                "        if (query.includes('ruby') || query.includes('sassoon')) return { lat: 18.5308, lng: 73.8778, name: 'Ruby Hall Clinic' };\n" +
                "        if (query.includes('hadapsar') || query.includes('nobel')) return { lat: 18.5089, lng: 73.9259, name: 'Nobel Hospital' };\n" +
                "        if (query.includes('kem')) return { lat: 18.5204, lng: 73.8720, name: 'KEM Hospital' };\n" +
                "        const url = 'https://nominatim.openstreetmap.org/search?format=json&countrycodes=in&limit=1&q=' + encodeURIComponent(nameStr + ', Pune');\n" +
                "        try {\n" +
                "            const res = await fetch(url);\n" +
                "            const data = await res.json();\n" +
                "            if (data && data.length > 0) {\n" +
                "                return { lat: parseFloat(data[0].lat), lng: parseFloat(data[0].lon), name: data[0].display_name.split(',')[0] };\n" +
                "            }\n" +
                "        } catch (e) {}\n" +
                "        return { lat: 18.5018, lng: 73.8636, name: nameStr };\n" +
                "    }\n" +
                "\n" +
                "    let hasInitialBoundsSet = false;\n" +
                "    async function fetchAndDrawRoute(startLat, startLng, destLat, destLng, shouldFitBounds = false) {\n" +
                "        if (!map) return;\n" +
                "        const url = 'https://router.project-osrm.org/route/v1/driving/' + startLng + ',' + startLat + ';' + destLng + ',' + destLat + '?overview=full&geometries=geojson';\n" +
                "        try {\n" +
                "            const res = await fetch(url);\n" +
                "            const data = await res.json();\n" +
                "            if (data.routes && data.routes[0]) {\n" +
                "                const route = data.routes[0];\n" +
                "                routeCoords = route.geometry.coordinates.map(p => [p[1], p[0]]);\n" +
                "                if (!corridorCasing) {\n" +
                "                    corridorCasing = L.polyline(routeCoords, { color: '#005a9e', weight: 8, opacity: 0.85 }).addTo(map);\n" +
                "                } else {\n" +
                "                    corridorCasing.setLatLngs(routeCoords);\n" +
                "                }\n" +
                "                if (!corridorCore) {\n" +
                "                    corridorCore = L.polyline(routeCoords, { color: '#08a1e5', weight: 4.5, opacity: 1.0 }).addTo(map);\n" +
                "                } else {\n" +
                "                    corridorCore.setLatLngs(routeCoords);\n" +
                "                }\n" +
                "                if (shouldFitBounds || !hasInitialBoundsSet) {\n" +
                "                    hasInitialBoundsSet = true;\n" +
                "                    map.fitBounds(L.latLngBounds(routeCoords), {\n" +
                "                        paddingTopLeft: [30, 60],\n" +
                "                        paddingBottomRight: [30, 60]\n" +
                "                    });\n" +
                "                }\n" +
                "                if (window.javaControlRoom && typeof window.javaControlRoom.updateDistanceETA === 'function') {\n" +
                "                    const distKm = (route.distance / 1000).toFixed(1) + ' km';\n" +
                "                    const etaMin = Math.round(route.duration / 60) + ' mins';\n" +
                "                    window.javaControlRoom.updateDistanceETA(distKm, etaMin);\n" +
                "                }\n" +
                "            }\n" +
                "        } catch (err) {\n" +
                "            console.log('OSRM routing error:', err);\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    function updateAmbulance(latitude, longitude) {\n" +
                "        if (!map) initMap();\n" +
                "        latitude = parseFloat(latitude);\n" +
                "        longitude = parseFloat(longitude);\n" +
                "        if (isNaN(latitude) || isNaN(longitude) || latitude === 0 || longitude === 0) return;\n" +
                "        if (!ambulanceMarker) {\n" +
                "            ambulanceMarker = L.marker([latitude, longitude], { icon: ambIcon, zIndexOffset: 3000 }).addTo(map);\n" +
                "            ambulanceMarker.bindTooltip('&#128657; Ambulance (Live Location)', {\n" +
                "                permanent: true, direction: 'top', className: 'badge-pickup', offset: [0, -14]\n" +
                "            }).openTooltip();\n" +
                "            if (!currentDestLoc) map.setView([latitude, longitude], 15);\n" +
                "        } else {\n" +
                "            ambulanceMarker.setLatLng([latitude, longitude]);\n" +
                "        }\n" +
                "        if (currentDestLoc && currentDestLoc.lat && currentDestLoc.lng) {\n" +
                "            fetchAndDrawRoute(latitude, longitude, currentDestLoc.lat, currentDestLoc.lng, false);\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    async function setDestination(destLat, destLng, destName) {\n" +
                "        if (!map) initMap();\n" +
                "        destLat = parseFloat(destLat);\n" +
                "        destLng = parseFloat(destLng);\n" +
                "        if (isNaN(destLat) || isNaN(destLng) || destLat === 0 || destLng === 0) return;\n" +
                "        currentDestLoc = { lat: destLat, lng: destLng, name: destName || 'Emergency Destination' };\n" +
                "        if (destMarker) map.removeLayer(destMarker);\n" +
                "        destMarker = L.marker([destLat, destLng], { icon: destIcon }).addTo(map);\n" +
                "        destMarker.bindTooltip('&#127973; ' + (destName || 'Emergency Destination'), {\n" +
                "            permanent: true, direction: 'top', className: 'badge-destination', offset: [0, -16]\n" +
                "        }).openTooltip();\n" +
                "        if (ambulanceMarker) {\n" +
                "            const ambPos = ambulanceMarker.getLatLng();\n" +
                "            await fetchAndDrawRoute(ambPos.lat, ambPos.lng, destLat, destLng, true);\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    async function updateAmbulanceLive(currLat, currLng, destLat, destLng, destName) {\n" +
                "        if (!map) initMap();\n" +
                "        currLat = parseFloat(currLat);\n" +
                "        currLng = parseFloat(currLng);\n" +
                "        if (!isNaN(currLat) && !isNaN(currLng) && currLat !== 0 && currLng !== 0) {\n" +
                "            if (!ambulanceMarker) {\n" +
                "                ambulanceMarker = L.marker([currLat, currLng], { icon: ambIcon, zIndexOffset: 3000 }).addTo(map);\n" +
                "                ambulanceMarker.bindTooltip('&#128657; Ambulance (Live Location)', {\n" +
                "                    permanent: true, direction: 'top', className: 'badge-pickup', offset: [0, -14]\n" +
                "                }).openTooltip();\n" +
                "            } else {\n" +
                "                ambulanceMarker.setLatLng([currLat, currLng]);\n" +
                "            }\n" +
                "        }\n" +
                "        if (destLat !== null && destLat !== undefined && destLng !== null && destLng !== undefined && !isNaN(destLat) && !isNaN(destLng) && parseFloat(destLat) !== 0) {\n" +
                "            const parsedDLat = parseFloat(destLat);\n" +
                "            const parsedDLng = parseFloat(destLng);\n" +
                "            if (!currentDestLoc || currentDestLoc.lat !== parsedDLat || currentDestLoc.lng !== parsedDLng) {\n" +
                "                currentDestLoc = { lat: parsedDLat, lng: parsedDLng, name: destName || 'Emergency Destination' };\n" +
                "                if (destMarker) map.removeLayer(destMarker);\n" +
                "                destMarker = L.marker([currentDestLoc.lat, currentDestLoc.lng], { icon: destIcon }).addTo(map);\n" +
                "                destMarker.bindTooltip('&#127973; ' + (currentDestLoc.name || 'Emergency Destination'), {\n" +
                "                    permanent: true, direction: 'top', className: 'badge-destination', offset: [0, -16]\n" +
                "                }).openTooltip();\n" +
                "            }\n" +
                "        }\n" +
                "        if (ambulanceMarker && currentDestLoc && currentDestLoc.lat && currentDestLoc.lng) {\n" +
                "            const ambPos = ambulanceMarker.getLatLng();\n" +
                "            await fetchAndDrawRoute(ambPos.lat, ambPos.lng, currentDestLoc.lat, currentDestLoc.lng, false);\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    async function setRouteFromCurrentLocation(currLat, currLng, destName) {\n" +
                "        await setRouteWithCoordinates(currLat, currLng, null, null, destName);\n" +
                "    }\n" +
                "\n" +
                "    async function setRouteWithCoordinates(currLat, currLng, destLat, destLng, destName) {\n" +
                "        if (!map) initMap();\n" +
                "        map.invalidateSize(true);\n" +
                "        let destLoc;\n" +
                "        if (destLat !== null && destLat !== undefined && destLng !== null && destLng !== undefined && !isNaN(destLat) && !isNaN(destLng) && parseFloat(destLat) !== 0) {\n" +
                "            destLoc = { lat: parseFloat(destLat), lng: parseFloat(destLng), name: destName || 'Emergency Destination' };\n" +
                "        } else {\n" +
                "            destLoc = await geocodeLocation(destName);\n" +
                "        }\n" +
                "        currentDestLoc = destLoc;\n" +
                "        if (destMarker) map.removeLayer(destMarker);\n" +
                "        destMarker = L.marker([destLoc.lat, destLoc.lng], { icon: destIcon }).addTo(map)\n" +
                "            .bindTooltip('&#127973; ' + (destLoc.name || 'Emergency Destination'), { permanent: true, direction: 'top', className: 'badge-destination', offset: [0, -16] });\n" +
                "        destMarker.openTooltip();\n" +
                "        if (!ambulanceMarker) {\n" +
                "            ambulanceMarker = L.marker([currLat, currLng], { icon: ambIcon, zIndexOffset: 3000 }).addTo(map)\n" +
                "                .bindTooltip('&#128657; Ambulance (Live Location)', { permanent: true, direction: 'top', className: 'badge-pickup', offset: [0, -14] });\n" +
                "            ambulanceMarker.openTooltip();\n" +
                "        } else if (currLat && currLng && !isNaN(currLat) && !isNaN(currLng) && currLat !== 0) {\n" +
                "            ambulanceMarker.setLatLng([currLat, currLng]);\n" +
                "        }\n" +
                "        const ambPos = ambulanceMarker.getLatLng();\n" +
                "        await fetchAndDrawRoute(ambPos.lat, ambPos.lng, destLoc.lat, destLoc.lng, true);\n" +
                "        setTimeout(() => { if (map) map.invalidateSize(true); }, 150);\n" +
                "    }\n" +
                "\n" +
                "    window.onload = function () {\n" +
                "        initMap();\n" +
                "    };\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
    }

    // =========================================================
    // THINGSPEAK LIVE TRACKING LOGIC
    // =========================================================
    private synchronized void startLiveTracking() {
        if (trackingThread != null && trackingThread.isAlive()) {
            return;
        }
        isTrackingRunning = true;
        trackingThread = new Thread(() -> {
            while (isTrackingRunning) {
                fetchThingSpeakLocation();
                try {
                    Thread.sleep(UPDATE_INTERVAL);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        trackingThread.setDaemon(true);
        trackingThread.setName("PoliceLiveTrackingThread");
        trackingThread.start();
    }

    private void fetchThingSpeakLocation() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(THINGSPEAK_URL))
                    .header("User-Agent", "LifeLink-JavaFX")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return;
            }

            JSONObject json = new JSONObject(response.body());
            JSONArray feeds = json.optJSONArray("feeds");
            if (feeds == null || feeds.length() == 0) {
                return;
            }

            JSONObject latestFeed = feeds.getJSONObject(feeds.length() - 1);
            String latitudeText = latestFeed.optString("field1", "");
            String longitudeText = latestFeed.optString("field2", "");

            if (latitudeText.isEmpty() || longitudeText.isEmpty()) {
                return;
            }

            double latitude = Double.parseDouble(latitudeText);
            double longitude = Double.parseDouble(longitudeText);

            if (latitude == 0 || longitude == 0 || Double.isNaN(latitude) || Double.isNaN(longitude)) {
                return;
            }

            if (latitude == lastLatitude && longitude == lastLongitude) {
                return;
            }

            lastLatitude = latitude;
            lastLongitude = longitude;
            liveAmbulanceLat = latitude;
            liveAmbulanceLng = longitude;

            System.out.println("========================================");
            System.out.println("[PoliceDashboard] LIVE AMBULANCE GPS TELEMETRY");
            System.out.println("Latitude  : " + latitude);
            System.out.println("Longitude : " + longitude);
            System.out.println("========================================");

            Platform.runLater(() -> {
                if (isMapLoaded && mapWebEngine != null) {
                    Double destLat = (activeSelectedAlert != null && activeSelectedAlert.destLat != null && activeSelectedAlert.destLat != 0.0) 
                            ? activeSelectedAlert.destLat : activeDestLat;
                    Double destLng = (activeSelectedAlert != null && activeSelectedAlert.destLng != null && activeSelectedAlert.destLng != 0.0) 
                            ? activeSelectedAlert.destLng : activeDestLng;
                    String destName = (activeSelectedAlert != null && activeSelectedAlert.destination != null) 
                            ? activeSelectedAlert.destination : activeDestinationHospital;
                    String safeDestName = (destName != null ? destName.replace("'", "\\'") : "Emergency Destination");

                    if (destLat != null && destLng != null && destLat != 0.0 && destLng != 0.0) {
                        String js = String.format(Locale.US,
                                "if (typeof updateAmbulanceLive === 'function') { " +
                                "  updateAmbulanceLive(%f, %f, %f, %f, '%s'); " +
                                "} else if (typeof updateAmbulance === 'function') { " +
                                "  updateAmbulance(%f, %f); " +
                                "}",
                                latitude, longitude, destLat, destLng, safeDestName, latitude, longitude);
                        mapWebEngine.executeScript(js);
                    } else {
                        String js = String.format(Locale.US,
                                "if (typeof updateAmbulance === 'function') { updateAmbulance(%f, %f); }",
                                latitude, longitude);
                        mapWebEngine.executeScript(js);
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("[PoliceDashboard] ThingSpeak fetch error: " + e.getMessage());
        }
    }

    private double[] resolveKnownCoordinates(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new double[]{18.5204, 73.8567}; // Central Pune fallback
        }
        String lower = query.toLowerCase();
        if (lower.contains("swargate")) return new double[]{18.5011, 73.8627};
        if (lower.contains("katraj") || lower.contains("bharati") || lower.contains("bharatividyapeeth")) return new double[]{18.4575, 73.8677};
        if (lower.contains("navale") || lower.contains("sknmcgh") || lower.contains("kashibai")) return new double[]{18.4528, 73.8290};
        if (lower.contains("core2web") || lower.contains("narhe")) return new double[]{18.4414, 73.8286};
        if (lower.contains("sahyadri") || lower.contains("deccan")) return new double[]{18.5146, 73.8378};
        if (lower.contains("kem") || lower.contains("rasta peth") || lower.contains("hihoho")) return new double[]{18.5204, 73.8656};
        if (lower.contains("inamdar") || lower.contains("fatima") || lower.contains("wanowrie")) return new double[]{18.5012, 73.8996};
        if (lower.contains("pulse")) return new double[]{18.4632, 73.8268};
        if (lower.contains("silver birch") || lower.contains("silverbirch") || lower.contains("dhayari") || lower.contains("wakad")) return new double[]{18.4485, 73.8124};
        if (lower.contains("vighnaharta")) return new double[]{18.4575, 73.8569};
        if (lower.contains("morya") || lower.contains("chinchwad")) return new double[]{18.6276, 73.7852};
        if (lower.contains("galaxy") || lower.contains("karve")) return new double[]{18.5074, 73.8077};
        if (lower.contains("nobel") || lower.contains("noble") || lower.contains("hadapsar") || lower.contains("ojas")) return new double[]{18.5049, 73.9271};
        if (lower.contains("sassoon") || lower.contains("ruby") || lower.contains("station")) return new double[]{18.5308, 73.8778};
        if (lower.contains("kothrud")) return new double[]{18.5074, 73.8077};
        if (lower.contains("baner") || lower.contains("balewadi")) return new double[]{18.5590, 73.7868};
        if (lower.contains("vimannagar") || lower.contains("viman nagar") || lower.contains("airport")) return new double[]{18.5679, 73.9143};
        if (lower.contains("aundh")) return new double[]{18.5580, 73.8075};

        return new double[]{18.5204, 73.8567};
    }

    private double[] fetchCoordinatesForAddress(String addressStr) {
        if (addressStr == null || addressStr.trim().isEmpty()) {
            return new double[]{18.4852, 73.8166};
        }

        // Fast resolution for known Pune locations without network latency
        double[] known = resolveKnownCoordinates(addressStr);
        if (known[0] != 18.5204 || known[1] != 73.8567) {
            return known;
        }

        try {
            String cleanQuery = addressStr.trim();
            String query = URLEncoder.encode(cleanQuery, StandardCharsets.UTF_8);
            String urlStr = "https://nominatim.openstreetmap.org/search?format=json&countrycodes=in&limit=1&q=" + query;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "LifeLink-Police-System");
            conn.setConnectTimeout(1500);
            conn.setReadTimeout(1500);

            if (conn.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                reader.close();

                String json = response.toString();
                if (json.contains("\"lat\"") && json.contains("\"lon\"")) {
                    int latIdx = json.indexOf("\"lat\":\"") + 7;
                    double lat = Double.parseDouble(json.substring(latIdx, json.indexOf("\"", latIdx)));

                    int lonIdx = json.indexOf("\"lon\":\"") + 7;
                    double lon = Double.parseDouble(json.substring(lonIdx, json.indexOf("\"", lonIdx)));

                    return new double[]{lat, lon};
                }
            }
        } catch (Exception ignored) {}

        return known;
    }

    private void plotRouteWithDirectCoordinates(Double srcLat, Double srcLng, Double destLat, Double destLng, String destName) {
        new Thread(() -> {
            double startLat;
            double startLng;
            if (liveAmbulanceLat != null && liveAmbulanceLng != null && liveAmbulanceLat != 0.0 && liveAmbulanceLng != 0.0) {
                startLat = liveAmbulanceLat;
                startLng = liveAmbulanceLng;
            } else if (srcLat != null && srcLng != null && srcLat != 0.0 && srcLng != 0.0) {
                startLat = srcLat;
                startLng = srcLng;
            } else {
                double[] c = fetchCoordinatesForAddress(activeSource != null ? activeSource : (activeSelectedAlert != null ? activeSelectedAlert.source : "Swargate"));
                startLat = c[0];
                startLng = c[1];
            }

            double endLat;
            double endLng;
            if (destLat != null && destLng != null && destLat != 0.0 && destLng != 0.0) {
                endLat = destLat;
                endLng = destLng;
            } else {
                double[] c = fetchCoordinatesForAddress(destName != null ? destName : (activeDestinationHospital != null ? activeDestinationHospital : "NOBEL Hospital"));
                endLat = c[0];
                endLng = c[1];
            }

            activeDestLat = endLat;
            activeDestLng = endLng;
            if (destName != null && !destName.isEmpty()) {
                activeDestinationHospital = destName;
            }

            Platform.runLater(() -> {
                if (isMapLoaded && mapWebEngine != null) {
                    String safeDest = (destName != null ? destName.replace("'", "\\'") : (activeDestinationHospital != null ? activeDestinationHospital.replace("'", "\\'") : "Emergency Destination"));
                    String js = "setTimeout(function() { " +
                                "  if (typeof setRouteWithCoordinates === 'function') { " +
                                "    setRouteWithCoordinates(" + startLat + ", " + startLng + ", " + endLat + ", " + endLng + ", '" + safeDest + "'); " +
                                "  } else if (typeof setRouteFromCurrentLocation === 'function') { " +
                                "    setRouteFromCurrentLocation(" + startLat + ", " + startLng + ", '" + safeDest + "'); " +
                                "  } " +
                                "}, 200);";
                    mapWebEngine.executeScript(js);
                }
            });
        }).start();
    }

    private void updateMapRoute(PoliceAlertModel alert) {
        if (alert == null) return;
        activeSelectedAlert = alert;
        activeTripId = alert.tripId;
        activeDestinationHospital = alert.destination;
        activeDestLat = alert.destLat;
        activeDestLng = alert.destLng;
        activeSource = alert.source;
        activePickupLat = alert.srcLat;
        activePickupLng = alert.srcLng;
        plotRouteWithDirectCoordinates(alert.srcLat, alert.srcLng, alert.destLat, alert.destLng, alert.destination);
    }

    private void updateMapRoute(double currLat, double currLng, String destination) {
        plotRouteWithDirectCoordinates(currLat, currLng, activeDestLat, activeDestLng, destination);
    }

    private void updateMapRoute(double currLat, double currLng, Double destLat, Double destLng, String destination) {
        plotRouteWithDirectCoordinates(currLat, currLng, destLat, destLng, destination);
    }

    private void plotRouteOnMap(String sourceAddr, String destHospital) {
        plotRouteWithDirectCoordinates(activePickupLat, activePickupLng, activeDestLat, activeDestLng, destHospital);
    }

    private void plotRouteOnMap(String sourceAddr, String destHospital, Double destLat, Double destLng) {
        plotRouteWithDirectCoordinates(activePickupLat, activePickupLng, destLat, destLng, destHospital);
    }

    // =========================================================
    // 2. FIRESTORE REAL-TIME LISTENER (policeEmergencyAlerts)
    // =========================================================
    private void startPoliceAlertsListener() {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) {
                renderEmptyAlerts("Firebase configuration not initialized.");
                return;
            }

            alertsListener = db.collection("policeEmergencyAlerts")
                    .addSnapshotListener((snapshots, error) -> {
                        if (error != null) {
                            System.err.println("Police Alerts listener error: "
                                    + error.getMessage());
                            return;
                        }

                        if (snapshots != null) {
                            activeAlertsList.clear();
                            PoliceAlertModel newAlert = null;

                            for (var doc : snapshots.getDocuments()) {
                                String docId = doc.getId();

                                String tripId = doc.contains("tripID")
                                        ? doc.getString("tripID")
                                        : doc.getString("TripID");
                                if (tripId == null)
                                    tripId = "UNIT-" + docId.substring(0,
                                            Math.min(docId.length(), 6))
                                            .toUpperCase();

                                String patId = doc.contains("patID")
                                        ? doc.getString("patID")
                                        : doc.getString("PATID");
                                if (patId == null)
                                    patId = "PAT-UNKNOWN";

                                String source = doc.contains("pickupLocation")
                                        ? doc.getString("pickupLocation")
                                        : doc.getString("source");
                                if (source == null)
                                    source = "Core2Web Pune";

                                String dest = null;
                                if (doc.contains("destinationHospital"))
                                    dest = doc.getString("destinationHospital");
                                if (dest == null && doc.contains("destination"))
                                    dest = doc.getString("destination");
                                if (dest == null && doc.contains("Destination"))
                                    dest = doc.getString("Destination");
                                if (dest == null && doc.contains("hospitalName"))
                                    dest = doc.getString("hospitalName");
                                if (dest == null)
                                    dest = "NOBEL Hospital";

                                String status = doc.contains("status")
                                        ? doc.getString("status")
                                        : "PENDING";

                                String timeStr = "Just Now";
                                Timestamp rawTs = null;
                                long epoch = System.currentTimeMillis();
                                if (doc.contains("timestamp")) {
                                    Object tObj = doc.get("timestamp");
                                    if (tObj instanceof Timestamp) {
                                        rawTs = (Timestamp) tObj;
                                        epoch = rawTs.toDate().getTime();
                                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                                        timeStr = sdf.format(rawTs.toDate());
                                    } else if (tObj instanceof java.util.Date) {
                                        java.util.Date d = (java.util.Date) tObj;
                                        rawTs = Timestamp.of(d);
                                        epoch = d.getTime();
                                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                                        timeStr = sdf.format(d);
                                    } else if (tObj instanceof Long) {
                                        epoch = (Long) tObj;
                                        rawTs = Timestamp.ofTimeSecondsAndNanos(epoch / 1000, 0);
                                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                                        timeStr = sdf.format(new java.util.Date(epoch));
                                    }
                                }

                                PoliceAlertModel alert = new PoliceAlertModel(docId,
                                        tripId, patId, source, dest, status,
                                        timeStr);
                                alert.rawTimestamp = rawTs;
                                alert.epochMillis = epoch;

                                Double sLat = getSafeDouble(doc, "srcLat");
                                if (sLat == null) sLat = getSafeDouble(doc, "pickupLat");
                                if (sLat != null) alert.srcLat = sLat;
                                Double sLng = getSafeDouble(doc, "srcLng");
                                if (sLng == null) sLng = getSafeDouble(doc, "pickupLng");
                                if (sLng != null) alert.srcLng = sLng;

                                Double dLat = getSafeDouble(doc, "destLat");
                                if (dLat == null) dLat = getSafeDouble(doc, "latitude");
                                if (dLat == null && dest != null) {
                                    double[] c = resolveKnownCoordinates(dest);
                                    if (c[0] != 18.5204 || c[1] != 73.8567) dLat = c[0];
                                }
                                if (dLat != null) alert.destLat = dLat;

                                Double dLng = getSafeDouble(doc, "destLng");
                                if (dLng == null) dLng = getSafeDouble(doc, "longitude");
                                if (dLng == null && dest != null) {
                                    double[] c = resolveKnownCoordinates(dest);
                                    if (c[0] != 18.5204 || c[1] != 73.8567) dLng = c[1];
                                }
                                if (dLng != null) alert.destLng = dLng;

                                // If missionProgress or adminEmergencyRequests already synced a more recent destination for this trip
                                if (activeTripId != null && !activeTripId.isEmpty() && activeTripId.equalsIgnoreCase(tripId)) {
                                    if (activeDestinationHospital != null && !activeDestinationHospital.isEmpty()) {
                                        alert.destination = activeDestinationHospital;
                                    }
                                    if (activeDestLat != null && activeDestLat != 0.0) alert.destLat = activeDestLat;
                                    if (activeDestLng != null && activeDestLng != 0.0) alert.destLng = activeDestLng;
                                }

                                activeAlertsList.add(alert);

                                if (isInitialSnapshotLoaded) {
                                    if (!knownAlertDocIds.contains(docId) && !"CLEARED".equalsIgnoreCase(status) && !"DENIED".equalsIgnoreCase(status)) {
                                        newAlert = alert;
                                    }
                                }
                                knownAlertDocIds.add(docId);
                            }

                            // Sort timewise descending so newly created requests always appear at 1st position
                            activeAlertsList.sort((a, b) -> {
                                if (a.rawTimestamp != null && b.rawTimestamp != null) {
                                    return b.rawTimestamp.compareTo(a.rawTimestamp);
                                } else if (a.rawTimestamp != null) {
                                    return -1;
                                } else if (b.rawTimestamp != null) {
                                    return 1;
                                }
                                return Long.compare(b.epochMillis, a.epochMillis);
                            });

                            isInitialSnapshotLoaded = true;
                            final PoliceAlertModel alertToNotify = newAlert;

                            Platform.runLater(() -> {
                                if (alertToNotify != null) {
                                    activeSelectedAlert = alertToNotify;
                                }
                                renderDynamicAlerts();
                                if (alertToNotify != null) {
                                    updateMapRoute(alertToNotify);
                                    showEmergencyAlertPopup(alertToNotify);
                                }
                            });
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
            renderEmptyAlerts("Error connecting to police alert streams.");
        }
    }

    private void startAdminEmergencyRequestsListener() {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return;

            emergencyRequestsListener = db.collection("adminEmergencyRequests")
                    .addSnapshotListener((snapshots, error) -> {
                        if (error != null || snapshots == null) return;

                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
                            String status = doc.getString("status");
                            if (status != null && (status.equalsIgnoreCase("COMPLETE") || status.equalsIgnoreCase("COMPLETED"))) {
                                continue;
                            }

                            String tripId = doc.getString("tripID") != null ? doc.getString("tripID") 
                                    : (doc.getString("TripID") != null ? doc.getString("TripID") : doc.getId());
                            String destination = doc.getString("destination") != null ? doc.getString("destination") 
                                    : (doc.getString("destinationHospital") != null ? doc.getString("destinationHospital") : null);
                            String source = doc.getString("source") != null ? doc.getString("source") 
                                    : (doc.getString("pickupLocation") != null ? doc.getString("pickupLocation") : null);

                            Double dLat = getSafeDouble(doc, "destLat");
                            if (dLat == null) dLat = getSafeDouble(doc, "destinationLat");
                            if (dLat == null) dLat = getSafeDouble(doc, "latitude");
                            if (dLat == null && destination != null) {
                                double[] c = resolveKnownCoordinates(destination);
                                if (c[0] != 18.5204 || c[1] != 73.8567) dLat = c[0];
                            }

                            Double dLng = getSafeDouble(doc, "destLng");
                            if (dLng == null) dLng = getSafeDouble(doc, "destinationLng");
                            if (dLng == null) dLng = getSafeDouble(doc, "longitude");
                            if (dLng == null && destination != null) {
                                double[] c = resolveKnownCoordinates(destination);
                                if (c[0] != 18.5204 || c[1] != 73.8567) dLng = c[1];
                            }

                            Double pLat = getSafeDouble(doc, "pickupLat");
                            if (pLat == null) pLat = getSafeDouble(doc, "sourceLat");
                            Double pLng = getSafeDouble(doc, "pickupLng");
                            if (pLng == null) pLng = getSafeDouble(doc, "sourceLng");

                            if (destination != null && !destination.isEmpty()) {
                                syncEmergencyDestination(tripId, source, destination, dLat, dLng, pLat, pLng, status);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startMissionProgressListener() {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return;

            missionProgressListener = db.collection("missionProgress")
                    .addSnapshotListener((snapshots, error) -> {
                        if (error != null || snapshots == null) return;

                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
                            String tripId = doc.getId();
                            String destination = doc.getString("destination");
                            String source = doc.getString("source");
                            Double destLat = getSafeDouble(doc, "destLat");
                            Double destLng = getSafeDouble(doc, "destLng");
                            Double pickupLat = getSafeDouble(doc, "pickupLat");
                            Double pickupLng = getSafeDouble(doc, "pickupLng");
                            String status = doc.getString("currentStatus");

                            if (destination != null && !destination.isEmpty()) {
                                syncEmergencyDestination(tripId, source, destination, destLat, destLng, pickupLat, pickupLng, status);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void syncEmergencyDestination(String tripId, String source, String destination, 
                                                       Double destLat, Double destLng, 
                                                       Double pickupLat, Double pickupLng, String status) {
        if (destination == null || destination.trim().isEmpty()) return;

        if (destLat == null || destLng == null || destLat == 0.0 || destLng == 0.0) {
            double[] known = resolveKnownCoordinates(destination);
            destLat = known[0];
            destLng = known[1];
        }

        boolean destinationChanged = (activeDestinationHospital == null || !activeDestinationHospital.equalsIgnoreCase(destination))
                || (activeDestLat != null && destLat != null && !activeDestLat.equals(destLat));

        activeTripId = (tripId != null && !tripId.isEmpty()) ? tripId : activeTripId;
        activeDestinationHospital = destination;
        activeDestLat = destLat;
        activeDestLng = destLng;
        if (source != null && !source.isEmpty()) activeSource = source;
        if (pickupLat != null && pickupLat != 0.0) activePickupLat = pickupLat;
        if (pickupLng != null && pickupLng != 0.0) activePickupLng = pickupLng;

        boolean matchedAlert = false;
        PoliceAlertModel targetAlert = null;
        for (PoliceAlertModel alert : activeAlertsList) {
            if (alert.tripId != null && (alert.tripId.equalsIgnoreCase(tripId) || tripId == null || activeAlertsList.size() == 1)) {
                alert.destination = destination;
                if (destLat != null) alert.destLat = destLat;
                if (destLng != null) alert.destLng = destLng;
                if (pickupLat != null && pickupLat != 0.0) alert.srcLat = pickupLat;
                if (pickupLng != null && pickupLng != 0.0) alert.srcLng = pickupLng;
                if (source != null && !source.isEmpty()) alert.source = source;
                matchedAlert = true;
                targetAlert = alert;
                break;
            }
        }

        if (activeSelectedAlert != null) {
            if (activeSelectedAlert.tripId == null || activeSelectedAlert.tripId.equalsIgnoreCase(tripId) || !matchedAlert) {
                activeSelectedAlert.destination = destination;
                if (destLat != null) activeSelectedAlert.destLat = destLat;
                if (destLng != null) activeSelectedAlert.destLng = destLng;
                if (pickupLat != null && pickupLat != 0.0) activeSelectedAlert.srcLat = pickupLat;
                if (pickupLng != null && pickupLng != 0.0) activeSelectedAlert.srcLng = pickupLng;
                if (source != null && !source.isEmpty()) activeSelectedAlert.source = source;
            }
        } else if (targetAlert != null) {
            activeSelectedAlert = targetAlert;
        }

        // Keep policeEmergencyAlerts document updated in Firestore if matching alert exists
        if (targetAlert != null && targetAlert.docId != null && !targetAlert.docId.isEmpty() && destinationChanged) {
            final String docId = targetAlert.docId;
            final String finalDest = destination;
            final Double fDestLat = destLat;
            final Double fDestLng = destLng;
            final Double fSrcLat = pickupLat;
            final Double fSrcLng = pickupLng;
            final String fSource = source;
            new Thread(() -> {
                try {
                    Firestore db = FirebaseConfig.getFirestore();
                    if (db != null) {
                        Map<String, Object> updateAlert = new HashMap<>();
                        updateAlert.put("destination", finalDest);
                        updateAlert.put("destinationHospital", finalDest);
                        if (fDestLat != null) updateAlert.put("destLat", fDestLat);
                        if (fDestLng != null) updateAlert.put("destLng", fDestLng);
                        if (fSrcLat != null) updateAlert.put("srcLat", fSrcLat);
                        if (fSrcLng != null) updateAlert.put("srcLng", fSrcLng);
                        if (fSource != null) updateAlert.put("source", fSource);
                        db.collection("policeEmergencyAlerts").document(docId).set(updateAlert, SetOptions.merge());
                    }
                } catch (Exception ignored) {}
            }).start();
        }

        final Double finalDestLat = destLat;
        final Double finalDestLng = destLng;
        final Double finalPickupLat = (pickupLat != null && pickupLat != 0.0) ? pickupLat : activePickupLat;
        final Double finalPickupLng = (pickupLng != null && pickupLng != 0.0) ? pickupLng : activePickupLng;
        final String finalDestName = destination;

        if (destinationChanged) {
            System.out.println("[PoliceDashboard] Destination updated from Driver dispatch: " + destination + " (Lat: " + destLat + ", Lng: " + destLng + ")");
            Platform.runLater(() -> {
                renderDynamicAlerts();
                plotRouteWithDirectCoordinates(finalPickupLat, finalPickupLng, finalDestLat, finalDestLng, finalDestName);
            });
        }
    }

    private void renderDynamicAlerts() {
        if (alertsShimmer != null) {
            alertsShimmer.stop();
            alertsShimmer = null;
        }
        alertsContainer.getChildren().clear();

        long pendingCount = activeAlertsList.stream().filter(
                a -> !"CLEARED".equalsIgnoreCase(a.status) && !"DENIED".equalsIgnoreCase(a.status))
                .count();
        pendingCountText.setText(pendingCount + " Pending");
        pendingCountText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-fill: "
                + (pendingCount > 0 ? ACCENT_TERRACOTTA_TEXT : SUCCESS_GREEN) + "; -fx-font-weight: bold;");

        if (activeAlertsList.isEmpty()) {
            renderEmptyAlerts("No active emergency clearance requests.");
            return;
        }

        // Sort timewise descending (newest request at 1st position)
        activeAlertsList.sort((a, b) -> {
            if (a.rawTimestamp != null && b.rawTimestamp != null) {
                return b.rawTimestamp.compareTo(a.rawTimestamp);
            } else if (a.rawTimestamp != null) {
                return -1;
            } else if (b.rawTimestamp != null) {
                return 1;
            }
            return Long.compare(b.epochMillis, a.epochMillis);
        });

        for (PoliceAlertModel alert : activeAlertsList) {
            VBox alertCard = buildAlertItemCard(alert);
            alertsContainer.getChildren().add(alertCard);
        }

        if (activeSelectedAlert != null) {
            PoliceAlertModel matched = null;
            for (PoliceAlertModel a : activeAlertsList) {
                if ((a.docId != null && a.docId.equals(activeSelectedAlert.docId))
                        || (a.tripId != null && a.tripId.equals(activeSelectedAlert.tripId))) {
                    matched = a;
                    break;
                }
            }
            if (matched != null) {
                activeSelectedAlert = matched;
            } else {
                activeSelectedAlert = activeAlertsList.stream()
                        .filter(a -> !"CLEARED".equalsIgnoreCase(a.status)).findFirst()
                        .orElse(activeAlertsList.get(0));
            }
        } else {
            PoliceAlertModel firstPending = activeAlertsList.stream()
                    .filter(a -> !"CLEARED".equalsIgnoreCase(a.status)).findFirst()
                    .orElse(activeAlertsList.get(0));
            activeSelectedAlert = firstPending;
        }

        if (activeSelectedAlert != null) {
            updateMapRoute(activeSelectedAlert);
        }
    }

    // =========================================================
    // 3. ELEVATED ALERT ITEM CARDS WITH TACTILE INTERACTIONS
    // =========================================================
    private VBox buildAlertItemCard(PoliceAlertModel alert) {
        boolean isCleared = "CLEARED".equalsIgnoreCase(alert.status);
        boolean isDenied = "DENIED".equalsIgnoreCase(alert.status);

        VBox card = new VBox(10);
        card.setPadding(new Insets(16));
        card.setStyle(
                "-fx-background-color: " + (isCleared ? SUCCESS_GREEN_BG : (isDenied ? ACCENT_TERRACOTTA_BG : VERY_LIGHT_BEIGE)) + ";" +
                "-fx-background-radius: 14px;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 14px;" +
                "-fx-cursor: hand;"
        );
        card.setEffect(new DropShadow(8, 0, 2, Color.rgb(51, 38, 30, 0.04)));

        card.setOnMouseClicked(e -> {
            activeSelectedAlert = alert;
            updateMapRoute(alert);
        });

        card.setOnMouseEntered(e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(120), card);
            tt.setToX(3);
            tt.play();
            card.setEffect(new DropShadow(12, 0, 3, Color.rgb(51, 38, 30, 0.08)));
        });

        card.setOnMouseExited(e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(120), card);
            tt.setToX(0);
            tt.play();
            card.setEffect(new DropShadow(8, 0, 2, Color.rgb(51, 38, 30, 0.04)));
        });

        // Top Row: Unit Badge + Patient ID + Timestamp
        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);

        HBox unitBadge = new HBox(4);
        unitBadge.setAlignment(Pos.CENTER);
        unitBadge.setPadding(new Insets(3, 8, 3, 8));
        unitBadge.setStyle("-fx-background-color: " + SURFACE + "; -fx-background-radius: 6px; -fx-border-color: " + BORDER + "; -fx-border-radius: 6px;");
        Text unitText = new Text("🚑 " + alert.tripId);
        unitText.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        unitBadge.getChildren().add(unitText);

        Text patLbl = new Text("(" + alert.patId + ")");
        patLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-fill: " + SEC_TEXT + ";");

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Text timeLbl = new Text(alert.timestampStr);
        timeLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: 600; -fx-fill: " + SEC_TEXT + ";");

        topRow.getChildren().addAll(unitBadge, patLbl, sp, timeLbl);

        // Transit Route Details
        VBox routeBox = new VBox(4);
        routeBox.setPadding(new Insets(4, 8, 4, 8));
        routeBox.setStyle("-fx-background-color: " + SURFACE + "; -fx-background-radius: 8px; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px;");

        Text fromText = new Text("📍 From: " + alert.source);
        fromText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-fill: " + SEC_TEXT + ";");

        Text toText = new Text("🏥 To: " + alert.destination);
        toText.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        routeBox.getChildren().addAll(fromText, toText);

        // Buttons & Status Badges
        HBox buttonsRow = new HBox(10);
        buttonsRow.setAlignment(Pos.CENTER_LEFT);

        if (isCleared) {
            Label statusBadge = new Label("✓ Green Corridor Cleared • Signal Active");
            statusBadge.setStyle(FONT_FAMILY + "-fx-background-color: " + SUCCESS_GREEN_BG + "; -fx-text-fill: " + SUCCESS_GREEN
                    + "; -fx-font-weight: bold; -fx-font-size: 11.5px; -fx-padding: 6 12; -fx-background-radius: 8px; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px;");
            buttonsRow.getChildren().add(statusBadge);
        } else if (isDenied) {
            Label statusBadge = new Label("✕ Clearance Request Denied");
            statusBadge.setStyle(FONT_FAMILY + "-fx-background-color: " + ACCENT_TERRACOTTA_BG + "; -fx-text-fill: " + ACCENT_TERRACOTTA_TEXT
                    + "; -fx-font-weight: bold; -fx-font-size: 11.5px; -fx-padding: 6 12; -fx-background-radius: 8px; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px;");
            buttonsRow.getChildren().add(statusBadge);
        } else {
            Button confirmBtn = new Button("✓ Clear Corridor");
            confirmBtn.setPrefHeight(36);
            confirmBtn.setStyle(
                    FONT_FAMILY + "-fx-background-color: " + BROWN_DARK + ";" +
                    "-fx-text-fill: #FFFFFF;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 12px;" +
                    "-fx-background-radius: 8px;" +
                    "-fx-cursor: hand;" +
                    "-fx-padding: 0 16;"
            );
            confirmBtn.setEffect(new DropShadow(8, 0, 2, Color.rgb(122, 74, 50, 0.25)));

            confirmBtn.setOnMouseEntered(e -> {
                confirmBtn.setStyle(
                        FONT_FAMILY + "-fx-background-color: " + ACCENT_TERRACOTTA + ";" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0 16;"
                );
                confirmBtn.setTranslateY(-2);
            });
            confirmBtn.setOnMouseExited(e -> {
                confirmBtn.setStyle(
                        FONT_FAMILY + "-fx-background-color: " + BROWN_DARK + ";" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0 16;"
                );
                confirmBtn.setTranslateY(0);
            });

            Button denyBtn = new Button("✕ Deny");
            denyBtn.setPrefHeight(36);
            denyBtn.setStyle(
                    FONT_FAMILY + "-fx-background-color: " + ACCENT_TERRACOTTA_BG + ";" +
                    "-fx-text-fill: " + ACCENT_TERRACOTTA_TEXT + ";" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 12px;" +
                    "-fx-background-radius: 8px;" +
                    "-fx-border-color: " + BORDER + ";" +
                    "-fx-border-radius: 8px;" +
                    "-fx-cursor: hand;" +
                    "-fx-padding: 0 14;"
            );

            confirmBtn.setOnAction(e -> {
                updateAlertStatusInFirestore(alert.docId, "CLEARED");
                updateMapRoute(alert);
            });
            denyBtn.setOnAction(e -> updateAlertStatusInFirestore(alert.docId, "DENIED"));

            buttonsRow.getChildren().addAll(confirmBtn, denyBtn);
        }

        card.getChildren().addAll(topRow, routeBox, buttonsRow);

        card.setOnMouseEntered(e -> {
            card.setTranslateY(-2);
            card.setEffect(new DropShadow(14, 0, 4, Color.rgb(51, 38, 30, 0.10)));
        });
        card.setOnMouseExited(e -> {
            card.setTranslateY(0);
            card.setEffect(new DropShadow(8, 0, 2, Color.rgb(51, 38, 30, 0.04)));
        });

        return card;
    }

    private void updateAlertStatusInFirestore(String docId, String newStatus) {
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db != null) {
                    Map<String, Object> update = new HashMap<>();
                    update.put("status", newStatus);
                    update.put("policeActionTime", Timestamp.now());

                    db.collection("policeEmergencyAlerts").document(docId).set(update,
                            SetOptions.merge());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void renderEmptyAlerts(String msg) {
        if (alertsShimmer != null) {
            alertsShimmer.stop();
            alertsShimmer = null;
        }
        alertsContainer.getChildren().clear();
        Label empty = new Label(msg);
        empty.setStyle(FONT_FAMILY + "-fx-text-fill: " + SEC_TEXT + "; -fx-font-size: 12.5px; -fx-padding: 16;");
        alertsContainer.getChildren().add(empty);
    }

    private Label createLoadingLabel() {
        Label l = new Label("Connecting to live Police Emergency stream...");
        l.setStyle(FONT_FAMILY + "-fx-text-fill: " + SEC_TEXT + "; -fx-font-size: 12.5px; -fx-padding: 12;");
        return l;
    }

    // =========================================================
    // 4. ELEVATED EMERGENCY ALERT POP-UP (SAME LOGIC, PREMIUM UI)
    // =========================================================
    private void showEmergencyAlertPopup(PoliceAlertModel alert) {
        try {
            Toolkit.getDefaultToolkit().beep();
        } catch (Exception ignored) {}

        VBox popupCard = new VBox(9);
        popupCard.setPrefWidth(330);
        popupCard.setMaxWidth(330);
        popupCard.setMinHeight(Region.USE_PREF_SIZE);
        popupCard.setPrefHeight(Region.USE_PREF_SIZE);
        popupCard.setMaxHeight(Region.USE_PREF_SIZE);
        popupCard.setPadding(new Insets(12, 14, 12, 14));
        popupCard.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-background-radius: 14px; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 14px; " +
                "-fx-effect: dropshadow(gaussian, rgba(51, 38, 30, 0.18), 16, 0.08, 0, 4);"
        );

        // Header Row with Siren, Title, and Close Button
        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);

        StackPane sirenBadge = new StackPane();
        sirenBadge.setPrefSize(26, 26);
        sirenBadge.setMinSize(26, 26);
        sirenBadge.setMaxSize(26, 26);
        sirenBadge.setStyle("-fx-background-color: " + ACCENT_TERRACOTTA_BG + "; -fx-background-radius: 6px; -fx-border-color: " + BORDER + "; -fx-border-radius: 6px;");
        Label sirenIcon = new Label("🚨");
        sirenIcon.setStyle("-fx-font-size: 13px;");
        sirenBadge.getChildren().add(sirenIcon);

        Text titleText = new Text("INCOMING CORRIDOR ALERT");
        titleText.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: 800; -fx-fill: " + ACCENT_TERRACOTTA_TEXT + "; -fx-letter-spacing: 0.4px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button closeBtn = new Button("✕");
        closeBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: " + SEC_TEXT + "; -fx-font-size: 12px; -fx-cursor: hand; -fx-padding: 2 6;");
        closeBtn.setOnMouseEntered(e -> closeBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-text-fill: " + PRIMARY_TEXT + "; -fx-font-size: 12px; -fx-cursor: hand; -fx-background-radius: 4px; -fx-padding: 2 6;"));
        closeBtn.setOnMouseExited(e -> closeBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: " + SEC_TEXT + "; -fx-font-size: 12px; -fx-cursor: hand; -fx-padding: 2 6;"));

        topRow.getChildren().addAll(sirenBadge, titleText, spacer, closeBtn);

        // Details Container
        VBox infoBox = new VBox(5);
        infoBox.setPadding(new Insets(8, 10, 8, 10));
        infoBox.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-border-color: " + BORDER + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        HBox unitRow = new HBox(6);
        unitRow.setAlignment(Pos.CENTER_LEFT);
        Text unitLbl = new Text("🚑 " + alert.tripId);
        unitLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        Text patLbl = new Text("(" + alert.patId + ")");
        patLbl.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-fill: " + SEC_TEXT + ";");
        Region uSpacer = new Region();
        HBox.setHgrow(uSpacer, Priority.ALWAYS);
        Text timeLbl = new Text("Just Now");
        timeLbl.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-font-weight: bold; -fx-fill: " + ACCENT_TERRACOTTA_TEXT + ";");
        unitRow.getChildren().addAll(unitLbl, patLbl, uSpacer, timeLbl);

        Text fromLbl = new Text("📍 From: " + alert.source);
        fromLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-fill: " + SEC_TEXT + ";");

        Text toLbl = new Text("🏥 To: " + alert.destination);
        toLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        infoBox.getChildren().addAll(unitRow, fromLbl, toLbl);

        // Action Buttons
        HBox actionsRow = new HBox(8);
        actionsRow.setAlignment(Pos.CENTER_RIGHT);

        Button viewMapBtn = new Button("📍 Map");
        viewMapBtn.setPrefHeight(28);
        viewMapBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + SURFACE + "; -fx-text-fill: " + SEC_TEXT + "; -fx-border-color: " + BORDER + "; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-font-size: 11px; -fx-font-weight: 600; -fx-padding: 0 10; -fx-cursor: hand;");

        Button clearBtn = new Button("✓ Clear Corridor");
        clearBtn.setPrefHeight(28);
        clearBtn.setStyle(
                FONT_FAMILY + "-fx-background-color: " + BROWN_DARK + ";" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 11px;" +
                "-fx-background-radius: 6px;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 0 12;"
        );
        clearBtn.setEffect(new DropShadow(6, 0, 2, Color.rgb(122, 74, 50, 0.20)));

        clearBtn.setOnMouseEntered(e -> {
            clearBtn.setStyle(
                    FONT_FAMILY + "-fx-background-color: " + ACCENT_TERRACOTTA + ";" +
                    "-fx-text-fill: #FFFFFF;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 11px;" +
                    "-fx-background-radius: 6px;" +
                    "-fx-cursor: hand;" +
                    "-fx-padding: 0 12;"
            );
            clearBtn.setTranslateY(-1);
        });
        clearBtn.setOnMouseExited(e -> {
            clearBtn.setStyle(
                    FONT_FAMILY + "-fx-background-color: " + BROWN_DARK + ";" +
                    "-fx-text-fill: #FFFFFF;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-size: 11px;" +
                    "-fx-background-radius: 6px;" +
                    "-fx-cursor: hand;" +
                    "-fx-padding: 0 12;"
            );
            clearBtn.setTranslateY(0);
        });

        actionsRow.getChildren().addAll(viewMapBtn, clearBtn);
        popupCard.getChildren().addAll(topRow, infoBox, actionsRow);

        StackPane.setAlignment(popupCard, Pos.TOP_RIGHT);
        StackPane.setMargin(popupCard, new Insets(0, 0, 0, 0));

        popupCard.setOpacity(0);
        popupCard.setTranslateX(40);

        popupLayer.getChildren().setAll(popupCard);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(280), popupCard);
        fadeIn.setToValue(1.0);

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(280), popupCard);
        slideIn.setToX(0);

        ParallelTransition enter = new ParallelTransition(fadeIn, slideIn);
        enter.play();

        Runnable closeAction = () -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(240), popupCard);
            fadeOut.setToValue(0.0);

            TranslateTransition slideOut = new TranslateTransition(Duration.millis(240), popupCard);
            slideOut.setToX(40);

            ParallelTransition exit = new ParallelTransition(fadeOut, slideOut);
            exit.setOnFinished(e -> popupLayer.getChildren().remove(popupCard));
            exit.play();
        };

        closeBtn.setOnAction(e -> closeAction.run());

        viewMapBtn.setOnAction(e -> {
            updateMapRoute(alert);
            closeAction.run();
        });

        clearBtn.setOnAction(e -> {
            updateAlertStatusInFirestore(alert.docId, "CLEARED");
            updateMapRoute(alert);
            closeAction.run();
        });

        PauseTransition autoClose = new PauseTransition(Duration.seconds(20));
        autoClose.setOnFinished(e -> {
            if (popupLayer.getChildren().contains(popupCard)) {
                closeAction.run();
            }
        });
        autoClose.play();
    }

    private Double getSafeDouble(DocumentSnapshot doc, String field) {
        if (doc == null || !doc.contains(field)) return null;
        try {
            Object val = doc.get(field);
            if (val == null) return null;
            if (val instanceof Number) {
                return ((Number) val).doubleValue();
            }
            String s = val.toString().trim();
            if (!s.isEmpty()) {
                return Double.parseDouble(s);
            }
        } catch (Exception ignored) {}
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        isTrackingRunning = false;
        if (alertsListener != null) alertsListener.remove();
        if (emergencyRequestsListener != null) emergencyRequestsListener.remove();
        if (missionProgressListener != null) missionProgressListener.remove();
        super.stop();
        Platform.exit();
        System.exit(0);
    }
}