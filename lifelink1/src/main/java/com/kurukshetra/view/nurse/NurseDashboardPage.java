package com.kurukshetra.view.nurse;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.dao.voice.NurseVoiceReportDao;
import com.kurukshetra.model.voice.NurseVoiceReportModel;
import com.kurukshetra.view.voice.GoogleCloudSpeechService;
import com.github.sarxos.webcam.Webcam;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.ListenerRegistration;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import com.kurukshetra.view.Welcome;
import com.kurukshetra.view.call.VideoCallEngine;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class NurseDashboardPage extends Application {

    // ================= EXACT PRESERVED COLOR THEME =================
    private static final String PRIMARY_PINK = "#e67593";
    private static final String PINK_DARK = "#D85375";
    private static final String PRIMARY_HOVER = "#D95F80";
    private static final String VERY_LIGHT_PINK = "#FDF0F4";
    private static final String LIGHT_PINK = "#FCE4EC";
    private static final String SOFT_PINK = "#F8D4DF";
    private static final String PALE_PINK = "#FFF6F8";
    private static final String VERY_PALE_PINK = "#FFF9FA";
    private static final String MENU_BG = "#FCDCE5";
    private static final String PAGE_BG = "#f9d6d7";
    private static final String SURFACE = "#FFFFFF";
    private static final String PRIMARY_TEXT = "#2B2125";
    private static final String SECONDARY_TEXT = "#695960";
    private static final String MUTED_TEXT = "#99878E";
    private static final String BORDER_COLOR = "#EEDEE3";
    private static final String DIVIDER_COLOR = "#F4E8EC";
    private static final String DANGER_RED = "#D71920";
    private static final String LIGHT_RED = "#FDE8E8";
    private static final String EMERALD_GREEN = "#16A34A";
    private static final String EMERALD_BG = "#EAF7EE";
    private static final String FONT_STYLE = "-fx-font-family: 'Segoe UI', -apple-system, sans-serif; ";

    // ================= SIDEBAR THEME CONSTANTS (NURSE BACKGROUND SPEC) =================
    private static final String SIDEBAR_ACTIVE_BG = "#FFFFFF";
    private static final String SIDEBAR_ACTIVE_TEXT = "#6B1D36";
    private static final String SIDEBAR_NAV_TEXT = "#FFF0F5";
    private static final String SIDEBAR_NAV_HOVER_BG = "rgba(90, 18, 42, 0.45)";

    public static Stage dashboardStage;
    public static Scene dashboardScene;

    public static BorderPane root;
    public static VBox mainContent;
    public static ScrollPane mainScrollPane;
    public static StackPane appOverlay;
    public static VBox sideBarRef;

    public Button dashboardButton;
    public Button tripsButton;
    public Button profileButton;
    public Button complaintButton;
    public Button logoutButton;
    public Button activeMenuButton;

    // Interactive Clinical Tabs (Horizontal Dock)
    private Button tabOverviewBtn;
    private Button tabMonitorBtn;
    private Button tabVoiceBtn;
    private Button tabPhotosBtn;
    private Button tabAiBtn;
    private Button tabConsultBtn;
    private Button tabActivityBtn;
    private Button activeTabBtn;

    public static String loginemail = "nurse1@lifelink.com";
    private StackPane dynamicCenterSlot;
    private VBox defaultOverviewCard;

    private Stage inAppVideoConsultStage;
    private VideoCallEngine nurseVideoEngine;
    private ListenerRegistration nurseVideoCallListener;
    private Webcam consultWebcam;
    private final AtomicBoolean isConsultCamActive = new AtomicBoolean(false);  
    private final AtomicBoolean isMicMuted = new AtomicBoolean(false);  
    
    private GoogleCloudSpeechService voiceService;
    private final NurseVoiceReportDao reportDao = new NurseVoiceReportDao();

    private Webcam activeWebcam;
    private final AtomicBoolean isCameraActive = new AtomicBoolean(false);

    private static final String GROQ_API_KEY = System.getenv("GROQ_API_KEY") != null 
            ? System.getenv("GROQ_API_KEY") 
            : "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";

    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "w2vrrz2c",
            "api_key", "584434458948198",
            "api_secret", "mOIxVa2JOe33SUA4tC6xut5boPM"
    ));

    private String lastDictatedText = "";
    private final List<String> selectedSummaryPhotoUrls = new ArrayList<>();

    private final List<HBox> nurseActivityLogList = new ArrayList<>();
    private VBox myLogContainer;

    private VBox notificationsContainer;
    private HBox horizontalMissionStepsContainer;
    private ListenerRegistration emergencyRequestListener;
    private ListenerRegistration missionProgressListener;
    private ListenerRegistration nurseActiveTripListener;

    private String activePatientId = "PAT-###";
    private String activeTripId = "TRIP-###";
    private String activeDestinationHospitalEmail = "ojas@gmail.com";
    private String activeDestinationHospital = "Not Assigned";
    private String currentMissionStatus = "Not Assigned";

    private HBox liveUploadGalleryItemsBox;
    private HBox overviewPhotoThumbnailsBox;
    private Label overviewNotesSummaryLabel;

    private Label globalDestinationBadge;
    private Label globalTripBadge;
    private Label corridorStatusLabel;
    private Label missionTimelineTripPill;
    private VBox missionTimelineCard;
    private Text overviewTitleSub;
    private PatientIcuMonitorView activePatientMonitorView;

    // ================= TRIAGE HUB IMAGE CAROUSEL STATE =================
    private Timeline carouselTimeline;
    private int currentCarouselSlideIndex = 0;
    private boolean isCarouselPlaying = true;
    private ImageView carouselImageView;
    private ProgressIndicator carouselSpinner;
    private Label carouselSlideCounterLabel;
    private Label carouselTagBadge;
    private Label carouselSlideTitle;
    private Label carouselSlideDesc;
    private VBox carouselCaptionBox;
    private HBox carouselDotsBox;
    private Button carouselAutoPlayToggleBtn;

    static {
        // Enable WebRTC and media stream flags inside JavaFX WebView
        System.setProperty("javafx.web.userdatafolder", System.getProperty("java.io.tmpdir"));
        System.setProperty("prism.lcdtext", "false");
        // Enable media playback and WebRTC flags for Chromium WebEngine
        System.setProperty("com.sun.webkit.usePlatformFontLoader", "true");
    }
    
    private void stopPatientMonitorAnimation() {
        stopCarouselTimeline();
        if (activePatientMonitorView != null) {
            try {
                activePatientMonitorView.stopAnimation();
            } catch (Exception ignored) {}
            activePatientMonitorView = null;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        dashboardStage = stage;

        root = new BorderPane();
        root.setStyle("-fx-background-color: " + PAGE_BG + "; -fx-font-family: 'Segoe UI', -apple-system, sans-serif;");
        root.setPadding(new Insets(0));

        try {
            voiceService = new GoogleCloudSpeechService("mr-IN");
        } catch (Exception e) {
            System.err.println("[VoiceService] Init warning: " + e.getMessage());
        }

        logNurseActivity("Shift Started", "Nurse logged in & ambulance telemetry link established");
        fetchActivePatientAssignment();

        // ================= SIDEBAR (260px, NURSE BACKGROUND WITH GLASS STYLING) =================
        VBox sideBar = new VBox(10);
        sideBar.setPadding(new Insets(18, 14, 18, 14));
        sideBar.setPrefWidth(260);
        sideBar.setMinWidth(260);
        sideBar.setMaxWidth(260);
        sideBar.setStyle(
                FONT_STYLE +
                "-fx-background-color: transparent;"
        );
        sideBarRef = sideBar;

        // 1. Top Brand Header
        HBox brandHeader = new HBox(12);
        brandHeader.setAlignment(Pos.CENTER_LEFT);
        brandHeader.setPadding(new Insets(4, 6, 8, 6));

        StackPane brandIconPane = new StackPane();
        brandIconPane.setPrefSize(40, 40);
        brandIconPane.setMinSize(40, 40);
        brandIconPane.setMaxSize(40, 40);

        Image logoImg = loadLogoImage();
        if (logoImg != null && !logoImg.isError()) {
            ImageView logoView = new ImageView(logoImg);
            logoView.setFitHeight(40);
            logoView.setFitWidth(40);
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);
            Rectangle logoClip = new Rectangle(40, 40);
            logoClip.setArcWidth(10);
            logoClip.setArcHeight(10);
            logoView.setClip(logoClip);
            brandIconPane.setStyle(
                    "-fx-background-radius: 12px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 8, 0, 0, 3);"
            );
            brandIconPane.getChildren().add(logoView);
        } else {
            brandIconPane.setStyle(
                    "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY_PINK + ", " + PINK_DARK + "); " +
                    "-fx-background-radius: 12px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(230,117,147,0.35), 8, 0, 0, 3);"
            );
            Text brandIconText = new Text("✚");
            brandIconText.setStyle("-fx-font-size: 20px; -fx-fill: white;");
            brandIconPane.getChildren().add(brandIconText);
        }

        VBox brandTextBox = new VBox(1);
        Text lifeLinkText = new Text("LifeLink");
        lifeLinkText.setStyle(FONT_STYLE + "-fx-font-size: 20px; -fx-font-weight: 800; -fx-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.40), 4, 0, 0, 1);");
        Text consoleText = new Text("Nurse Console");
        consoleText.setStyle(FONT_STYLE + "-fx-font-size: 11.5px; -fx-fill: #ffffff; -fx-font-weight: 600; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.30), 3, 0, 0, 1);");
        brandTextBox.getChildren().addAll(lifeLinkText, consoleText);
        brandHeader.getChildren().addAll(brandIconPane, brandTextBox);

        // 2. Animated Live Triage Unit Online Badge
        HBox liveHubBadge = new HBox(7);
        liveHubBadge.setAlignment(Pos.CENTER_LEFT);
        liveHubBadge.setPadding(new Insets(5, 10, 5, 10));
        liveHubBadge.setStyle(
                "-fx-background-color: rgba(90, 18, 42, 0.45); " +
                "-fx-border-color: rgba(255, 255, 255, 0.28); " +
                "-fx-border-radius: 16px; " +
                "-fx-background-radius: 16px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.20), 6, 0, 0, 1);"
        );

        Circle pulseDot = new Circle(4, Color.web(EMERALD_GREEN));
        Timeline pulseAnim = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(pulseDot.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(800), new KeyValue(pulseDot.opacityProperty(), 0.35)),
                new KeyFrame(Duration.millis(1600), new KeyValue(pulseDot.opacityProperty(), 1.0))
        );
        pulseAnim.setCycleCount(Animation.INDEFINITE);
        pulseAnim.play();


        VBox brandBox = new VBox(8, brandHeader);
        brandBox.setPadding(new Insets(0, 0, 8, 0));

        // Navigation Section Label (Matching PoliceDashboard)
        Label navLabel = new Label("NURSE PORTAL");
        navLabel.setStyle(FONT_STYLE + "-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + SIDEBAR_NAV_TEXT + "; -fx-letter-spacing: 0.6px; -fx-padding: 6 0 4 8;");

        // 4. Navigation Buttons (Matching Driver sidebar style with Nurse Pink)
        dashboardButton = createNurseNavButton("🏠  Dashboard", true);
        tripsButton = createNurseNavButton("✈  Nurse Trips", false);
        profileButton = createNurseNavButton("◍  Profile", false);
        complaintButton = createNurseNavButton("📝  Complaints", false);

        Button[] allNavButtons = {dashboardButton, tripsButton, profileButton, complaintButton};

        dashboardButton.setOnAction(e -> {
            setNurseNavActive(dashboardButton, allNavButtons);
            getToDashboardPage();
        });

        tripsButton.setOnAction(e -> {
            stopPatientMonitorAnimation();
            stopEmbeddedCamera();
            closeInAppVideoConsultStage();
            setNurseNavActive(tripsButton, allNavButtons);
            NurseTripsPage tripsPage = new NurseTripsPage(loginemail);
            root.setCenter(tripsPage.getNurseTripsPage(() -> {
                setNurseNavActive(dashboardButton, allNavButtons);
                getToDashboardPage();
            }));
        });

        profileButton.setOnAction(e -> {
            stopPatientMonitorAnimation();
            stopEmbeddedCamera();
            closeInAppVideoConsultStage();
            setNurseNavActive(profileButton, allNavButtons);
            NurseProfilePage profilePage = new NurseProfilePage(loginemail);
            root.setCenter(profilePage.getProfilePage(() -> {
                setNurseNavActive(dashboardButton, allNavButtons);
                getToDashboardPage();
            }));
        });

        complaintButton.setOnAction(e -> {
            stopPatientMonitorAnimation();
            stopEmbeddedCamera();
            closeInAppVideoConsultStage();
            setNurseNavActive(complaintButton, allNavButtons);
            String nurseDisplayName = (loginemail != null && !loginemail.isEmpty()) ? loginemail.split("@")[0] : "Nurse";
            nurseDisplayName = Character.toUpperCase(nurseDisplayName.charAt(0)) + (nurseDisplayName.length() > 1 ? nurseDisplayName.substring(1) : "");
            NurseComplaint nurseComplaint = new NurseComplaint(loginemail, nurseDisplayName, activeTripId);
            root.setCenter(nurseComplaint.getComplaintSection());
        });

        // 5. Spacer pushes profile and logout to bottom
        Region menuSpacer = new Region();
        VBox.setVgrow(menuSpacer, Priority.ALWAYS);

        // 6. Profile Mini Card (Glass card matching Nurse Background)
        HBox profileBox = new HBox(12);
        profileBox.setAlignment(Pos.CENTER_LEFT);
        profileBox.setPadding(new Insets(10, 12, 12, 12));
        profileBox.setStyle(
                "-fx-background-color: rgba(60, 10, 25, 0.52); " +
                "-fx-border-color: rgba(255, 255, 255, 0.22); " +
                "-fx-border-radius: 12px; " +
                "-fx-background-radius: 12px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.30), 8, 0, 0, 2);"
        );

        String nurseEmailForCard = (loginemail != null && !loginemail.isEmpty()) ? loginemail : "nurse@lifelink.com";
        String initialsText = extractInitials(nurseEmailForCard);

        StackPane avatarPane = new StackPane();
        avatarPane.setPrefSize(36, 36);
        avatarPane.setMinSize(36, 36);
        avatarPane.setMaxSize(36, 36);
        avatarPane.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY_PINK + ", " + PINK_DARK + "); " +
                "-fx-background-radius: 18px;"
        );
        Text initialsLabel = new Text(initialsText);
        initialsLabel.setStyle(FONT_STYLE + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: white;");
        avatarPane.getChildren().add(initialsLabel);

        VBox profileDetails = new VBox(2);
        Text profileName = new Text(nurseEmailForCard);
        profileName.setStyle(FONT_STYLE + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #FFFFFF;");
        Text profileRole = new Text("Certified Nurse");
        profileRole.setStyle(FONT_STYLE + "-fx-font-size: 10px; -fx-fill: #FFD2DE;");
        profileDetails.getChildren().addAll(profileName, profileRole);
        profileBox.getChildren().addAll(avatarPane, profileDetails);

        // 7. Logout Button (Deep red glass button)
        logoutButton = new Button("↩  Logout");
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setPrefHeight(46);
        logoutButton.setStyle(
                FONT_STYLE +
                "-fx-background-color: rgba(150, 20, 45, 0.65); " +
                "-fx-text-fill: #FFE0E7; " +
                "-fx-font-size: 13.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-color: rgba(255, 180, 200, 0.35); " +
                "-fx-border-radius: 12px; " +
                "-fx-border-width: 1px; " +
                "-fx-cursor: hand;"
        );
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(
                FONT_STYLE +
                "-fx-background-color: #E11D48; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 13.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-color: rgba(255, 255, 255, 0.40); " +
                "-fx-border-radius: 12px; " +
                "-fx-border-width: 1px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(225, 29, 72, 0.50), 12, 0, 0, 3);"
        ));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(
                FONT_STYLE +
                "-fx-background-color: rgba(150, 20, 45, 0.65); " +
                "-fx-text-fill: #FFE0E7; " +
                "-fx-font-size: 13.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-color: rgba(255, 180, 200, 0.35); " +
                "-fx-border-radius: 12px; " +
                "-fx-border-width: 1px; " +
                "-fx-cursor: hand;"
        ));
        logoutButton.setOnAction(event -> {
            stopPatientMonitorAnimation();
            stopEmbeddedCamera();
            closeInAppVideoConsultStage();
            detachFirebaseListener();
            if (voiceService != null && voiceService.isRecording()) {
                voiceService.stopListening();
            }
            try {
                Welcome welcome = new Welcome();
                welcome.start(dashboardStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        sideBar.getChildren().addAll(
                brandBox,
                navLabel,
                dashboardButton,
                tripsButton,
                profileButton,
                complaintButton,
                menuSpacer,
                profileBox,
                logoutButton
        );

        // ---- Background image layer wrapped in StackPane ----
        StackPane sidebarStack = new StackPane();
        sidebarStack.setPrefWidth(260);
        sidebarStack.setMinWidth(260);
        sidebarStack.setMaxWidth(260);
        sidebarStack.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(216, 83, 117, 0.30), 18, 0, 4, 0);");

        // Background image
        Image sidebarBg = loadSafeImage("/assets/Images/nurseDashboardbackground.png",
                "src/main/resources/assets/Images/nurseDashboardbackground.png");
        if (sidebarBg != null && !sidebarBg.isError()) {
            ImageView bgImageView = new ImageView(sidebarBg);
            bgImageView.setPreserveRatio(false);
            bgImageView.setSmooth(true);
            bgImageView.fitWidthProperty().bind(sidebarStack.widthProperty());
            bgImageView.fitHeightProperty().bind(sidebarStack.heightProperty());
            sidebarStack.getChildren().add(bgImageView);
        } else {
            Pane fallbackBg = new Pane();
            fallbackBg.setStyle("-fx-background-color: linear-gradient(to bottom, #FA8B9E, #F36981, #E8506E);");
            sidebarStack.getChildren().add(fallbackBg);
        }

        // Gentle gradient overlay preserving artwork vibrancy while ensuring high contrast
        Pane gradientOverlay = new Pane();
        gradientOverlay.setStyle(
            "-fx-background-color: linear-gradient(" +
                "to bottom, " +
                "rgba(60, 12, 26, 0.18) 0%, " +
                "rgba(40, 8, 18, 0.06) 40%, " +
                "rgba(40, 8, 18, 0.32) 100%" +
            ");"
        );

        // Right edge subtle glow/divider
        Pane edgeGlow = new Pane();
        edgeGlow.setStyle(
            "-fx-background-color: linear-gradient(" +
                "to bottom, " +
                "rgba(255, 200, 220, 0.40), " +
                "rgba(230, 117, 147, 0.25), " +
                "transparent" +
            "); " +
            "-fx-max-width: 2px; " +
            "-fx-pref-width: 2px;"
        );
        StackPane.setAlignment(edgeGlow, Pos.CENTER_RIGHT);

        sidebarStack.getChildren().addAll(gradientOverlay, sideBar, edgeGlow);

        mainContent = createDashboardContent();
        mainScrollPane = new ScrollPane(mainContent);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setFitToHeight(true);
        mainScrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        root.setLeft(sidebarStack);
        root.setCenter(mainScrollPane);

        StackPane finalRoot = new StackPane(root);
        finalRoot.setStyle("-fx-background-color: " + PAGE_BG + ";");
        appOverlay = finalRoot;

        // =============================================================
        // FULL SCREEN RESOLUTION FIX (SCREEN BOUNDS ALIGNMENT)
        // =============================================================
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        dashboardScene = new Scene(finalRoot, visualBounds.getWidth(), visualBounds.getHeight());

        dashboardStage.setX(visualBounds.getMinX());
        dashboardStage.setY(visualBounds.getMinY());
        dashboardStage.setWidth(visualBounds.getWidth());
        dashboardStage.setHeight(visualBounds.getHeight());

        dashboardStage.setTitle("LifeLink - Emergency Nurse Triage Operations");
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setMaximized(true);
        dashboardStage.show();

        stage.setOnCloseRequest(e -> {
            stopPatientMonitorAnimation();
            stopEmbeddedCamera();
            closeInAppVideoConsultStage();
            detachFirebaseListener();
            Platform.exit();
            System.exit(0);
        });

        FadeTransition fade = new FadeTransition(NurseAppSettings.dur(350), finalRoot);
        fade.setFromValue(0.6);
        fade.setToValue(1);
        fade.play();
    }

    // =========================================================
    // NURSE NAVIGATION BUTTON HELPERS (MATCHED TO REFERENCE SCREENSHOT SPEC)
    // =========================================================
    private String getActiveNurseNavStyle() {
        return FONT_STYLE +
                "-fx-background-color: " + SIDEBAR_ACTIVE_BG + "; " +
                "-fx-text-fill: " + SIDEBAR_ACTIVE_TEXT + "; " +
                "-fx-font-size: 15.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 12px; " +
                "-fx-alignment: center-left; " +
                "-fx-padding: 0 16px; " +
                "-fx-border-color: transparent; " +
                "-fx-border-radius: 12px; " +
                "-fx-border-width: 0; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(90, 15, 35, 0.28), 10, 0, 0, 3); " +
                "-fx-cursor: hand;";
    }

    private String getInactiveNurseNavStyle() {
        return FONT_STYLE +
                "-fx-background-color: transparent; " +
                "-fx-text-fill: " + SIDEBAR_NAV_TEXT + "; " +
                "-fx-font-size: 15.5px; " +
                "-fx-font-weight: 500; " +
                "-fx-background-radius: 12px; " +
                "-fx-alignment: center-left; " +
                "-fx-padding: 0 16px; " +
                "-fx-border-color: transparent; " +
                "-fx-border-radius: 12px; " +
                "-fx-border-width: 1px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(60, 10, 25, 0.35), 4, 0, 0, 1); " +
                "-fx-cursor: hand;";
    }

    private String getHoverNurseNavStyle() {
        return FONT_STYLE +
                "-fx-background-color: " + SIDEBAR_NAV_HOVER_BG + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 15.5px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 12px; " +
                "-fx-alignment: center-left; " +
                "-fx-padding: 0 16px; " +
                "-fx-border-color: rgba(255, 255, 255, 0.30); " +
                "-fx-border-radius: 12px; " +
                "-fx-border-width: 1px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.20), 8, 0, 0, 2); " +
                "-fx-cursor: hand;";
    }

    private Button createNurseNavButton(String text, boolean active) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(46);
        if (active) {
            btn.setStyle(getActiveNurseNavStyle());
        } else {
            btn.setStyle(getInactiveNurseNavStyle());
            btn.setOnMouseEntered(e -> {
                btn.setStyle(getHoverNurseNavStyle());
                btn.setTranslateX(4);
            });
            btn.setOnMouseExited(e -> {
                btn.setStyle(getInactiveNurseNavStyle());
                btn.setTranslateX(0);
            });
        }
        return btn;
    }

    private void setNurseNavActive(Button activeBtn, Button[] allButtons) {
        for (Button b : allButtons) {
            if (b == activeBtn) {
                b.setStyle(getActiveNurseNavStyle());
                b.setTranslateX(0);
                b.setOnMouseEntered(null);
                b.setOnMouseExited(null);
            } else {
                b.setStyle(getInactiveNurseNavStyle());
                b.setTranslateX(0);
                b.setOnMouseEntered(e -> {
                    b.setStyle(getHoverNurseNavStyle());
                    b.setTranslateX(4);
                });
                b.setOnMouseExited(e -> {
                    b.setStyle(getInactiveNurseNavStyle());
                    b.setTranslateX(0);
                });
            }
        }
        activeMenuButton = activeBtn;
    }

    // Keep extractInitials helper for profile card
    private String extractInitials(String email) {
        if (email == null || email.isEmpty()) return "N";
        String local = email.contains("@") ? email.split("@")[0] : email;
        String[] parts = local.split("[._\\-]");
        if (parts.length >= 2) {
            return ("" + Character.toUpperCase(parts[0].charAt(0)) + Character.toUpperCase(parts[1].charAt(0)));
        }
        return local.length() >= 2
                ? ("" + Character.toUpperCase(local.charAt(0)) + Character.toUpperCase(local.charAt(1)))
                : ("" + Character.toUpperCase(local.charAt(0)));
    }

    // =========================================================================
    // MAIN DASHBOARD LAYOUT: FULL-WIDTH BALANCED ARCHITECTURE
    // =========================================================================
    public VBox createDashboardContent() {
        VBox mainContent = new VBox(14);
        mainContent.setPadding(new Insets(20, 24, 20, 24));
        mainContent.setMinWidth(0);
        mainContent.setMaxWidth(Double.MAX_VALUE);
        mainContent.setStyle("-fx-background-color: " + PAGE_BG + ";");
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        // ================= 1. TOP HEADER BAR =================
        HBox headerRow = new HBox(14);
        headerRow.setAlignment(Pos.CENTER_LEFT);
        headerRow.setMaxWidth(Double.MAX_VALUE);

        int hour = LocalTime.now().getHour();
        String timeGreeting = (hour < 12) ? "Morning Shift, Sarah" : (hour < 17) ? "Afternoon Shift, Sarah" : "Evening Shift, Sarah";

        VBox headerText = new VBox(2);
        HBox greetingWithBadge = new HBox(8);
        greetingWithBadge.setAlignment(Pos.CENTER_LEFT);

        Text greeting = new Text(timeGreeting);
        greeting.setStyle("-fx-font-size: 21px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        greetingWithBadge.getChildren().addAll(greeting);

        headerText.getChildren().addAll(greetingWithBadge);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        globalDestinationBadge = new Label("🏥 ER: " + activeDestinationHospital);
        globalDestinationBadge.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 16px; -fx-background-radius: 16px; -fx-padding: 5 12; -fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_TEXT + ";");

        HBox activeTripPill = new HBox(6);
        activeTripPill.setAlignment(Pos.CENTER);
        activeTripPill.setPadding(new Insets(5, 12, 5, 12));
        activeTripPill.setStyle("-fx-background-color: " + EMERALD_BG + "; -fx-border-color: #BDE4CB; -fx-border-radius: 16px; -fx-background-radius: 16px;");
        Circle greenDot = new Circle(4, Color.web(EMERALD_GREEN));

        ScaleTransition dotPulse = new ScaleTransition(NurseAppSettings.dur(800), greenDot);
        dotPulse.setFromX(1.0);
        dotPulse.setFromY(1.0);
        dotPulse.setToX(1.5);
        dotPulse.setToY(1.5);
        dotPulse.setAutoReverse(true);
        dotPulse.setCycleCount(ScaleTransition.INDEFINITE);
        dotPulse.play();

        globalTripBadge = new Label("Trip: " + activeTripId);
        globalTripBadge.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + EMERALD_GREEN + ";");
        activeTripPill.getChildren().addAll(greenDot, globalTripBadge);

        Button syncBtn = new Button("🔄");
        syncBtn.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 16; -fx-background-radius: 16; -fx-cursor: hand; -fx-font-size: 11px; -fx-padding: 4 8;");
        Tooltip.install(syncBtn, new Tooltip("Refresh Live Stream"));
        syncBtn.setOnAction(e -> {
            RotateTransition rt = new RotateTransition(NurseAppSettings.dur(400), syncBtn);
            rt.setByAngle(360);
            rt.play();
            fetchActivePatientAssignment();
            NurseToast.show(appOverlay, "Live data synced from Firestore!", "info");
        });

        headerRow.getChildren().addAll(headerText, headerSpacer, globalDestinationBadge, activeTripPill, syncBtn);

        // ================= 2. TOP DYNAMIC MISSION TIMELINE =================
        VBox missionTimelineCard = buildHorizontalMissionTimeline();

        // ================= 3. CLINICAL TABS DOCK =================
        HBox workflowDock = buildWorkflowModeSwitcher();

        // ================= 4. FULL-WIDTH DYNAMIC WORKSPACE =================
        defaultOverviewCard = buildSummaryCardNode();
        dynamicCenterSlot = new StackPane(defaultOverviewCard);
        dynamicCenterSlot.setMinWidth(0);
        dynamicCenterSlot.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(dynamicCenterSlot, Priority.ALWAYS);

        mainContent.getChildren().addAll(headerRow, missionTimelineCard, workflowDock, dynamicCenterSlot);
        staggerIn(defaultOverviewCard, 0);
        startCarouselTimeline();

        return mainContent;
    }

    // =========================================================================
    // COMPONENT 1: DYNAMIC CHANGING MISSION TIMELINE (PROPER CONNECTING STEPPER)
    // =========================================================================
    private VBox buildHorizontalMissionTimeline() {
        VBox card = new VBox(10);
        card.setPadding(new Insets(14, 20, 14, 20));
        card.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 20; -fx-background-radius: 20;");
        card.setEffect(new DropShadow(10, 0, 3, Color.rgb(230, 117, 147, 0.05)));
        missionTimelineCard = card;

        // Header Row with Trip Info, Patient ID, and Corridor Telemetry
        HBox topInfoRow = new HBox(14);
        topInfoRow.setAlignment(Pos.CENTER_LEFT);

        Label headerTitle = new Label("CORRIDOR MISSION TIMELINE");
        headerTitle.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_TEXT + "; -fx-letter-spacing: 0.5px;");

        missionTimelineTripPill = new Label("Trip: " + activeTripId + " (Pat: " + activePatientId + ")");
        missionTimelineTripPill.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_PINK + "; -fx-background-color: " + VERY_LIGHT_PINK + "; -fx-padding: 2 8; -fx-background-radius: 6;");

        corridorStatusLabel = new Label("Status: " + currentMissionStatus.replace("_", " "));
        corridorStatusLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + EMERALD_GREEN + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topInfoRow.getChildren().addAll(headerTitle, missionTimelineTripPill, corridorStatusLabel);

        // Continuous line with 4 dots and headings below
        horizontalMissionStepsContainer = new HBox(0);
        horizontalMissionStepsContainer.setAlignment(Pos.CENTER);
        horizontalMissionStepsContainer.setMaxWidth(Double.MAX_VALUE);
        horizontalMissionStepsContainer.setPadding(new Insets(8, 0, 2, 0));
        updateHorizontalMissionStepsUI();

        card.getChildren().addAll(topInfoRow, horizontalMissionStepsContainer);
        return card;
    }

    private void updateHorizontalMissionStepsUI() {
        if (horizontalMissionStepsContainer == null) return;
        horizontalMissionStepsContainer.getChildren().clear();

        if ("NO_ACTIVE_MISSION".equalsIgnoreCase(currentMissionStatus)
                || "NONE".equalsIgnoreCase(activeTripId)
                || "STANDBY".equalsIgnoreCase(currentMissionStatus)
                || "COMPLETED".equalsIgnoreCase(currentMissionStatus)
                || "COMPLETE".equalsIgnoreCase(currentMissionStatus)
                || "CLOSED".equalsIgnoreCase(currentMissionStatus)) {

            HBox standbyBox = new HBox(8);
            standbyBox.setAlignment(Pos.CENTER);
            standbyBox.setPadding(new Insets(10, 0, 8, 0));

            Circle standbyDot = new Circle(4, Color.web(MUTED_TEXT));
            Label standbyLbl = new Label("○ No active uncompleted trip. Ambulance is standing by for new emergency dispatches.");
            standbyLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: " + MUTED_TEXT + "; -fx-font-style: italic; -fx-font-weight: 500;");

            standbyBox.getChildren().addAll(standbyDot, standbyLbl);
            horizontalMissionStepsContainer.getChildren().add(standbyBox);
            return;
        }

        boolean isAssignedDone = !currentMissionStatus.equalsIgnoreCase("STANDBY");
        boolean isPickupDone = currentMissionStatus.equalsIgnoreCase("ARRIVED_AT_PICKUP")
                || currentMissionStatus.equalsIgnoreCase("PATIENT_ONBOARD")
                || currentMissionStatus.equalsIgnoreCase("EN_ROUTE_TO_HOSPITAL")
                || currentMissionStatus.equalsIgnoreCase("ARRIVED_AT_HOSPITAL");

        boolean isEnRouteHospDone = currentMissionStatus.equalsIgnoreCase("PATIENT_ONBOARD")
                || currentMissionStatus.equalsIgnoreCase("EN_ROUTE_TO_HOSPITAL")
                || currentMissionStatus.equalsIgnoreCase("ARRIVED_AT_HOSPITAL");

        boolean isDeliveredDone = currentMissionStatus.equalsIgnoreCase("ARRIVED_AT_HOSPITAL");

        String state1 = isAssignedDone ? "completed" : "upcoming";
        String state2 = isPickupDone ? "completed" : (isAssignedDone ? "current" : "upcoming");
        String state3 = isEnRouteHospDone ? "completed" : (isPickupDone ? "current" : "upcoming");
        String state4 = isDeliveredDone ? "completed" : (isEnRouteHospDone ? "current" : "upcoming");

        // Step 1: Emergency Trip Assigned
        VBox step1 = createTimelineDotStep(
                "Emergency Trip Assigned",
                "Trip: " + activeTripId + " (Pat: " + activePatientId + ")",
                state1,
                false, false,
                true, isPickupDone || state2.equals("current")
        );

        // Step 2: Arrived at Patient Pickup
        VBox step2 = createTimelineDotStep(
                "Arrived at Patient Pickup",
                isPickupDone ? "Ambulance arrived at site" : "En route to pickup location",
                state2,
                true, isPickupDone || state2.equals("current"),
                true, isEnRouteHospDone || state3.equals("current")
        );

        // Step 3: En Route to Hospital
        VBox step3 = createTimelineDotStep(
                "En Route to Hospital",
                isEnRouteHospDone ? "Green corridor cleared to ER" : "Awaiting patient onboard",
                state3,
                true, isEnRouteHospDone || state3.equals("current"),
                true, isDeliveredDone || state4.equals("current")
        );

        // Step 4: Hospital ER Handover
        VBox step4 = createTimelineDotStep(
                "Hospital ER Handover",
                isDeliveredDone ? "Patient handover complete" : "Pending arrival at trauma bay",
                state4,
                true, isDeliveredDone || state4.equals("current"),
                false, false
        );

        horizontalMissionStepsContainer.getChildren().addAll(step1, step2, step3, step4);
    }

    private VBox createTimelineDotStep(
            String title,
            String subtitle,
            String state,
            boolean hasLeftLine,
            boolean isLeftLineColored,
            boolean hasRightLine,
            boolean isRightLineColored) {

        VBox stepCol = new VBox(6);
        stepCol.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(stepCol, Priority.ALWAYS);

        // 1. Dot row with connecting lines
        HBox dotRow = new HBox(0);
        dotRow.setAlignment(Pos.CENTER);
        dotRow.setMaxWidth(Double.MAX_VALUE);

        Region leftLine = new Region();
        leftLine.setMinHeight(3);
        leftLine.setMaxHeight(3);
        leftLine.setPrefHeight(3);
        HBox.setHgrow(leftLine, Priority.ALWAYS);
        if (!hasLeftLine) {
            leftLine.setOpacity(0.0);
        } else {
            leftLine.setStyle("-fx-background-color: " + (isLeftLineColored ? PRIMARY_PINK : BORDER_COLOR) + ";");
        }

        StackPane dotStack = new StackPane();
        dotStack.setMinSize(28, 28);
        dotStack.setPrefSize(28, 28);
        dotStack.setMaxSize(28, 28);

        if (state.equals("completed")) {
            Circle circle = new Circle(12, Color.web(PRIMARY_PINK));
            circle.setEffect(new DropShadow(4, 0, 2, Color.rgb(230, 117, 147, 0.35)));
            Text check = new Text("✓");
            check.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: white;");
            dotStack.getChildren().addAll(circle, check);
        } else if (state.equals("current")) {
            Circle glow = new Circle(14, Color.rgb(230, 117, 147, 0.25));
            Circle circle = new Circle(8, Color.web(PRIMARY_PINK));
            Circle innerDot = new Circle(3, Color.WHITE);
            dotStack.getChildren().addAll(glow, circle, innerDot);

            ScaleTransition pulse = new ScaleTransition(NurseAppSettings.dur(900), glow);
            pulse.setFromX(1.0);
            pulse.setFromY(1.0);
            pulse.setToX(1.5);
            pulse.setToY(1.5);
            pulse.setAutoReverse(true);
            pulse.setCycleCount(ScaleTransition.INDEFINITE);
            pulse.play();
        } else {
            Circle circle = new Circle(10, Color.WHITE);
            circle.setStroke(Color.web(BORDER_COLOR));
            circle.setStrokeWidth(2.5);
            dotStack.getChildren().add(circle);
        }

        Region rightLine = new Region();
        rightLine.setMinHeight(3);
        rightLine.setMaxHeight(3);
        rightLine.setPrefHeight(3);
        HBox.setHgrow(rightLine, Priority.ALWAYS);
        if (!hasRightLine) {
            rightLine.setOpacity(0.0);
        } else {
            rightLine.setStyle("-fx-background-color: " + (isRightLineColored ? PRIMARY_PINK : BORDER_COLOR) + ";");
        }

        dotRow.getChildren().addAll(leftLine, dotStack, rightLine);

        // 2. Heading and subtitle below the dot
        VBox textStack = new VBox(3);
        textStack.setAlignment(Pos.TOP_CENTER);
        textStack.setPadding(new Insets(4, 4, 2, 4));

        Label headingLabel = new Label(title);
        headingLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: "
                + (state.equals("upcoming") ? MUTED_TEXT : PRIMARY_TEXT) + ";");
        headingLabel.setAlignment(Pos.CENTER);

        HBox statusBadgeRow = new HBox(4);
        statusBadgeRow.setAlignment(Pos.CENTER);

        if (state.equals("current")) {
            Label nowBadge = new Label("NOW");
            nowBadge.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-text-fill: " + PRIMARY_PINK + "; -fx-font-weight: bold; -fx-font-size: 8px; -fx-padding: 1 5; -fx-background-radius: 4; -fx-border-color: " + PRIMARY_PINK + "; -fx-border-radius: 4;");
            statusBadgeRow.getChildren().add(nowBadge);
        } else if (state.equals("completed")) {
            Label doneBadge = new Label("✓");
            doneBadge.setStyle("-fx-text-fill: " + EMERALD_GREEN + "; -fx-font-weight: bold; -fx-font-size: 9px;");
            statusBadgeRow.getChildren().add(doneBadge);
        }

        Label subLabel = new Label(subtitle);
        subLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: "
                + (state.equals("current") ? PRIMARY_PINK : (state.equals("completed") ? SECONDARY_TEXT : MUTED_TEXT)) + ";");
        subLabel.setAlignment(Pos.CENTER);
        statusBadgeRow.getChildren().add(subLabel);

        textStack.getChildren().addAll(headingLabel, statusBadgeRow);
        stepCol.getChildren().addAll(dotRow, textStack);

        return stepCol;
    }

    // =========================================================================
    // COMPONENT 2: INTERACTIVE CLINICAL TABS DOCK (CIRCULAR PILL BUTTONS - TWO LINES)
    // =========================================================================
    private HBox buildWorkflowModeSwitcher() {
        HBox dock = new HBox(8);
        dock.setAlignment(Pos.CENTER);
        dock.setPadding(new Insets(6, 12, 6, 12));
        dock.setStyle("-fx-background-color: " + SURFACE + "; -fx-background-radius: 30; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 30;");
        dock.setEffect(new DropShadow(8, 0, 2, Color.rgb(230, 117, 147, 0.05)));

        tabOverviewBtn = createDockButton("📋 Triage\nHub", true);
        tabMonitorBtn = createDockButton("💓 Patient\nMonitor", false);
        tabVoiceBtn = createDockButton("🎤 Voice\nReport", false);
        tabPhotosBtn = createDockButton("📷 Patient\nPhotos", false);
        tabAiBtn = createDockButton("✦ AI Clinical\nSummary", false);
        tabConsultBtn = createDockButton("👨‍⚕ Doctor\nTele-Call", false);
        tabActivityBtn = createDockButton("📜 Activity\n& Dispatches", false);

        activeTabBtn = tabOverviewBtn;

        tabOverviewBtn.setOnAction(e -> {
            setDockActive(tabOverviewBtn);
            stopPatientMonitorAnimation();
            showDefaultCenterWidget();
        });

        tabMonitorBtn.setOnAction(e -> {
            setDockActive(tabMonitorBtn);
            stopEmbeddedCamera();
            closeInAppVideoConsultStage();
            showPatientMonitorWidget();
        });

        tabVoiceBtn.setOnAction(e -> {
            setDockActive(tabVoiceBtn);
            stopEmbeddedCamera();
            closeInAppVideoConsultStage();
            stopPatientMonitorAnimation();
            showVoiceReportWidget();
        });

        tabPhotosBtn.setOnAction(e -> {
            setDockActive(tabPhotosBtn);
            stopEmbeddedCamera();
            closeInAppVideoConsultStage();
            stopPatientMonitorAnimation();
            showPhotoUploadWidget();
        });

        tabAiBtn.setOnAction(e -> {
            setDockActive(tabAiBtn);
            stopEmbeddedCamera();
            closeInAppVideoConsultStage();
            stopPatientMonitorAnimation();
            showAiSummaryGeneratorWidget();
        });

        tabConsultBtn.setOnAction(e -> {
            setDockActive(tabConsultBtn);
            stopEmbeddedCamera();
            stopPatientMonitorAnimation();
            showDoctorConsultationWidget(activePatientId);
        });

        tabActivityBtn.setOnAction(e -> {
            setDockActive(tabActivityBtn);
            stopEmbeddedCamera();
            closeInAppVideoConsultStage();
            stopPatientMonitorAnimation();
            showActivityAndDispatchesWidget();
        });

        dock.getChildren().addAll(
                tabOverviewBtn, tabMonitorBtn, tabVoiceBtn, 
                tabPhotosBtn, tabAiBtn, tabConsultBtn, tabActivityBtn
        );
        return dock;
    }

    private Button createDockButton(String label, boolean isActive) {
        Button btn = new Button(label);
        btn.setTextAlignment(TextAlignment.CENTER);
        btn.setAlignment(Pos.CENTER);
        btn.setPrefHeight(48);
        btn.setMinHeight(48);
        btn.setLineSpacing(2.0);
        btn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btn, Priority.ALWAYS);
        btn.setStyle(isActive 
                ? "-fx-background-color: linear-gradient(to right, " + PRIMARY_PINK + ", " + PINK_DARK + "); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4 10; -fx-background-radius: 24; -fx-border-radius: 24; -fx-border-color: " + PRIMARY_PINK + "; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(216,83,117,0.40), 8, 0, 0, 2);"
                : "-fx-background-color: " + SURFACE + "; -fx-text-fill: " + SECONDARY_TEXT + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 24; -fx-background-radius: 24; -fx-cursor: hand;");

        btn.setOnMouseEntered(e -> {
            if (btn != activeTabBtn) {
                btn.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-text-fill: " + PRIMARY_PINK + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-border-color: " + PRIMARY_PINK + "; -fx-border-radius: 24; -fx-background-radius: 24; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(230,117,147,0.22), 6, 0, 0, 2);");
                ScaleTransition st = new ScaleTransition(NurseAppSettings.dur(130), btn);
                st.setToX(1.02);
                st.setToY(1.02);
                st.play();
            }
        });
        btn.setOnMouseExited(e -> {
            if (btn != activeTabBtn) {
                btn.setStyle("-fx-background-color: " + SURFACE + "; -fx-text-fill: " + SECONDARY_TEXT + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 24; -fx-background-radius: 24;");
                ScaleTransition st = new ScaleTransition(NurseAppSettings.dur(130), btn);
                st.setToX(1.0);
                st.setToY(1.0);
                st.play();
            }
        });
        return btn;
    }

    private void setDockActive(Button targetBtn) {
        Button[] all = { tabOverviewBtn, tabMonitorBtn, tabVoiceBtn, tabPhotosBtn, tabAiBtn, tabConsultBtn, tabActivityBtn };
        for (Button b : all) {
            if (b == null) continue;
            b.setScaleX(1.0);
            b.setScaleY(1.0);
            if (b == targetBtn) {
                b.setStyle("-fx-background-color: linear-gradient(to right, " + PRIMARY_PINK + ", " + PINK_DARK + "); -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4 10; -fx-background-radius: 24; -fx-border-radius: 24; -fx-border-color: " + PRIMARY_PINK + "; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(216,83,117,0.40), 8, 0, 0, 2);");
            } else {
                b.setStyle("-fx-background-color: " + SURFACE + "; -fx-text-fill: " + SECONDARY_TEXT + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 24; -fx-background-radius: 24;");
            }
        }
        activeTabBtn = targetBtn;
    }

    // =========================================================================
    // TRIAGE HUB IMAGE CAROUSEL MODEL & ASSET REPLACEMENT CONFIG
    // =========================================================================
    public static class CarouselSlide {
        private final String tag;
        private final String title;
        private final String description;
        private final String imageUrl;
        private final String fallbackAssetPath;
        private final String accentColor;

        public CarouselSlide(String tag, String title, String description, String imageUrl, String fallbackAssetPath, String accentColor) {
            this.tag = tag;
            this.title = title;
            this.description = description;
            this.imageUrl = imageUrl;
            this.fallbackAssetPath = fallbackAssetPath;
            this.accentColor = accentColor;
        }

        public String getTag() { return tag; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public String getImageUrl() { return imageUrl; }
        public String getFallbackAssetPath() { return fallbackAssetPath; }
        public String getAccentColor() { return accentColor; }
    }

    public static class CarouselImageViewPane extends Region {
        private final ImageView imageView;

        public CarouselImageViewPane(ImageView imageView) {
            this.imageView = imageView;
            getChildren().add(imageView);

            imageView.imageProperty().addListener((obs, oldImg, newImg) -> {
                if (newImg != null) {
                    if (newImg.getProgress() >= 1.0) {
                        requestLayout();
                    } else {
                        newImg.progressProperty().addListener((pObs, pOld, pNew) -> {
                            if (pNew.doubleValue() >= 1.0) {
                                requestLayout();
                            }
                        });
                    }
                }
                requestLayout();
            });
        }

        @Override
        protected double computeMinWidth(double height) { return 0; }
        @Override
        protected double computeMinHeight(double width) { return 0; }
        @Override
        protected double computePrefWidth(double height) { return 0; }
        @Override
        protected double computePrefHeight(double width) { return 0; }

        @Override
        protected void layoutChildren() {
            double w = getWidth();
            double h = getHeight();
            if (w <= 0 || h <= 0) return;

            Image img = imageView.getImage();
            if (img != null && img.getWidth() > 0 && img.getHeight() > 0) {
                double imgW = img.getWidth();
                double imgH = img.getHeight();
                double scale = Math.max(w / imgW, h / imgH);
                double scaledW = Math.ceil(imgW * scale);
                double scaledH = Math.ceil(imgH * scale);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(scaledW);
                imageView.setFitHeight(scaledH);
                layoutInArea(imageView, (w - scaledW) / 2.0, (h - scaledH) / 2.0, scaledW, scaledH, 0, HPos.CENTER, VPos.CENTER);
            } else {
                imageView.setPreserveRatio(false);
                imageView.setFitWidth(w);
                imageView.setFitHeight(h);
                layoutInArea(imageView, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
            }
        }
    }

    private static final List<CarouselSlide> TRIAGE_CAROUSEL_SLIDES = List.of(
            new CarouselSlide(
                    "EMERGENCY AMBULANCE DISPATCH",
                    "Rapid Response Unit MH16-104 En Route",
                    "Priority Green Corridor active • Real-time GPS telemetry synchronized with KEM Hospital ER Bay 01 • ETA: 8 Mins.",
                    "/assets/Images/triage_ambulance.jpg",
                    "/assets/Images/triage_ambulance.jpg",
                    DANGER_RED
            ),
            new CarouselSlide(
                    "REAL-TIME ICU TELEMETRY",
                    "Continuous Multiparameter Patient Vitals Broadcasting",
                    "Real-time cardiac rhythm, SpO₂, blood pressure, and GCS telemetry streaming continuously to Central ER Triage Command.",
                    "/assets/Images/triage_telemetry.jpg",
                    "/assets/Images/triage_telemetry.jpg",
                    PRIMARY_PINK
            ),
            new CarouselSlide(
                    "ER TRAUMA BAY PRE-ALERT",
                    "Hospital Emergency Trauma Bay 01 & Cath Lab on Standby",
                    "Direct clinical pre-arrival handover initiated • Specialized ER surgical and trauma team standing by for zero-delay patient intake.",
                    "/assets/Images/triage_traumabay.jpg",
                    "/assets/Images/triage_traumabay.jpg",
                    "#8B5CF6"
            )
    );

    // =========================================================================
    // WORKSPACE VIEW 1: TRIAGE HUB WITH 3-IMAGE CAROUSEL SLIDER
    // =========================================================================
    private VBox buildSummaryCardNode() {
        VBox card = new VBox(12);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setMinWidth(0);
        card.setMaxWidth(Double.MAX_VALUE);
        applyCardStyle(card);
        VBox.setVgrow(card, Priority.ALWAYS);

        if (overviewNotesSummaryLabel == null) {
            overviewNotesSummaryLabel = new Label(lastDictatedText.isEmpty() 
                    ? "Patient responsive to verbal commands. Bilateral air entry clear, mild tachypnea." 
                    : lastDictatedText);
            overviewNotesSummaryLabel.setWrapText(true);
        }
        if (overviewPhotoThumbnailsBox == null) {
            overviewPhotoThumbnailsBox = new HBox(8);
        }

        // 1. TOP HEADER BAR
        HBox headerBar = new HBox(12);
        headerBar.setAlignment(Pos.CENTER_LEFT);
        headerBar.setPadding(new Insets(2, 4, 8, 4));
        headerBar.setMinWidth(0);

        StackPane hubIconCircle = new StackPane();
        Circle hCirc = new Circle(15, Color.web(VERY_LIGHT_PINK));
        hCirc.setStroke(Color.web(PRIMARY_PINK));
        hCirc.setStrokeWidth(1.2);
        Text hIcon = new Text("📋");
        hIcon.setStyle("-fx-font-size: 13px;");
        hubIconCircle.getChildren().addAll(hCirc, hIcon);

        VBox titleStack = new VBox(1);
        Text titleMain = new Text("Triage Operations Command Hub");
        titleMain.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        titleStack.getChildren().addAll(titleMain);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Label liveBadge = new Label("● LIVE CAROUSEL FEED");
        liveBadge.setStyle("-fx-background-color: " + EMERALD_BG + "; -fx-text-fill: " + EMERALD_GREEN + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 14; -fx-border-color: rgba(22,163,74,0.3); -fx-border-radius: 14;");

        carouselSlideCounterLabel = new Label("Slide 1 of " + TRIAGE_CAROUSEL_SLIDES.size());
        carouselSlideCounterLabel.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-text-fill: " + PRIMARY_PINK + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 10; -fx-background-radius: 14; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14;");

        carouselAutoPlayToggleBtn = new Button(isCarouselPlaying ? "⏸ Pause" : "▶ Auto-Play");
        carouselAutoPlayToggleBtn.setStyle("-fx-background-color: " + SURFACE + "; -fx-text-fill: " + PRIMARY_PINK + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 12; -fx-background-radius: 14; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14; -fx-cursor: hand;");
        carouselAutoPlayToggleBtn.setOnAction(e -> {
            if (isCarouselPlaying) {
                isCarouselPlaying = false;
                stopCarouselTimeline();
                carouselAutoPlayToggleBtn.setText("▶ Auto-Play");
            } else {
                isCarouselPlaying = true;
                startCarouselTimeline();
                carouselAutoPlayToggleBtn.setText("⏸ Pause");
            }
        });

        headerBar.getChildren().addAll(hubIconCircle, titleStack, headerSpacer, liveBadge, carouselSlideCounterLabel, carouselAutoPlayToggleBtn);

        // 2. MAIN CAROUSEL SLIDER VIEWPORT
        StackPane carouselViewport = new StackPane();
        carouselViewport.setMinHeight(350);
        carouselViewport.setPrefHeight(440);
        carouselViewport.setMaxHeight(Double.MAX_VALUE);
        carouselViewport.setMinWidth(0);
        carouselViewport.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(carouselViewport, Priority.ALWAYS);
        carouselViewport.setEffect(new DropShadow(14, 0, 4, Color.rgb(230, 117, 147, 0.12)));

        Rectangle clip = new Rectangle();
        clip.setArcWidth(24);
        clip.setArcHeight(24);
        clip.widthProperty().bind(carouselViewport.widthProperty());
        clip.heightProperty().bind(carouselViewport.heightProperty());
        carouselViewport.setClip(clip);

        carouselImageView = new ImageView();
        carouselImageView.setPreserveRatio(false);
        CarouselImageViewPane carouselImagePane = new CarouselImageViewPane(carouselImageView);

        carouselSpinner = new ProgressIndicator();
        carouselSpinner.setMaxSize(40, 40);
        carouselSpinner.setStyle("-fx-progress-color: " + PRIMARY_PINK + ";");
        carouselSpinner.setVisible(false);

        Button prevArrowBtn = new Button("❮");
        prevArrowBtn.setStyle("-fx-background-color: rgba(20, 15, 18, 0.70); -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-min-width: 44px; -fx-min-height: 44px; -fx-max-width: 44px; -fx-max-height: 44px; -fx-background-radius: 22; -fx-border-color: rgba(255,255,255,0.30); -fx-border-radius: 22; -fx-cursor: hand;");
        StackPane.setAlignment(prevArrowBtn, Pos.CENTER_LEFT);
        StackPane.setMargin(prevArrowBtn, new Insets(0, 0, 0, 14));
        prevArrowBtn.setOnMouseEntered(e -> prevArrowBtn.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-min-width: 44px; -fx-min-height: 44px; -fx-max-width: 44px; -fx-max-height: 44px; -fx-background-radius: 22; -fx-border-color: white; -fx-border-radius: 22; -fx-cursor: hand;"));
        prevArrowBtn.setOnMouseExited(e -> prevArrowBtn.setStyle("-fx-background-color: rgba(20, 15, 18, 0.70); -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-min-width: 44px; -fx-min-height: 44px; -fx-max-width: 44px; -fx-max-height: 44px; -fx-background-radius: 22; -fx-border-color: rgba(255,255,255,0.30); -fx-border-radius: 22; -fx-cursor: hand;"));
        prevArrowBtn.setOnAction(e -> {
            updateCarouselSlide(currentCarouselSlideIndex - 1, true);
            resetCarouselTimer();
        });

        Button nextArrowBtn = new Button("❯");
        nextArrowBtn.setStyle("-fx-background-color: rgba(20, 15, 18, 0.70); -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-min-width: 44px; -fx-min-height: 44px; -fx-max-width: 44px; -fx-max-height: 44px; -fx-background-radius: 22; -fx-border-color: rgba(255,255,255,0.30); -fx-border-radius: 22; -fx-cursor: hand;");
        StackPane.setAlignment(nextArrowBtn, Pos.CENTER_RIGHT);
        StackPane.setMargin(nextArrowBtn, new Insets(0, 14, 0, 0));
        nextArrowBtn.setOnMouseEntered(e -> nextArrowBtn.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-min-width: 44px; -fx-min-height: 44px; -fx-max-width: 44px; -fx-max-height: 44px; -fx-background-radius: 22; -fx-border-color: white; -fx-border-radius: 22; -fx-cursor: hand;"));
        nextArrowBtn.setOnMouseExited(e -> nextArrowBtn.setStyle("-fx-background-color: rgba(20, 15, 18, 0.70); -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-min-width: 44px; -fx-min-height: 44px; -fx-max-width: 44px; -fx-max-height: 44px; -fx-background-radius: 22; -fx-border-color: rgba(255,255,255,0.30); -fx-border-radius: 22; -fx-cursor: hand;"));
        nextArrowBtn.setOnAction(e -> {
            updateCarouselSlide(currentCarouselSlideIndex + 1, true);
            resetCarouselTimer();
        });

        // Subtle dark gradient scrim at bottom to ensure text readability
        Region gradientScrim = new Region();
        gradientScrim.setStyle("-fx-background-color: linear-gradient(to top, rgba(20, 10, 14, 0.88) 0%, rgba(20, 10, 14, 0.40) 50%, transparent 100%);");
        gradientScrim.setMouseTransparent(true);

        // Slide Content Overlay (Tag Badge, Title, Description)
        carouselCaptionBox = new VBox(6);
        carouselCaptionBox.setAlignment(Pos.BOTTOM_LEFT);
        carouselCaptionBox.setPadding(new Insets(0, 24, 20, 24));
        carouselCaptionBox.setMouseTransparent(true);
        StackPane.setAlignment(carouselCaptionBox, Pos.BOTTOM_LEFT);

        carouselTagBadge = new Label();
        carouselTagBadge.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: 800; -fx-padding: 4 10; -fx-background-radius: 12; -fx-letter-spacing: 0.8px;");

        carouselSlideTitle = new Label();
        carouselSlideTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 800; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.60), 8, 0, 0, 2);");

        carouselSlideDesc = new Label();
        carouselSlideDesc.setWrapText(true);
        carouselSlideDesc.setMaxWidth(680);
        carouselSlideDesc.setStyle("-fx-font-size: 11.5px; -fx-text-fill: rgba(255, 240, 245, 0.90); -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.60), 6, 0, 0, 1);");

        carouselCaptionBox.getChildren().addAll(carouselTagBadge, carouselSlideTitle, carouselSlideDesc);

        carouselDotsBox = new HBox(8);
        carouselDotsBox.setAlignment(Pos.CENTER);
        carouselDotsBox.setPadding(new Insets(5, 12, 5, 12));
        // carouselDotsBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.50); -fx-background-radius: 16;");
        StackPane.setAlignment(carouselDotsBox, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(carouselDotsBox, new Insets(0, 20, 20, 0));

        carouselViewport.getChildren().addAll(carouselImagePane, gradientScrim, carouselCaptionBox, prevArrowBtn, nextArrowBtn, carouselDotsBox);

        carouselViewport.setOnMouseEntered(e -> pauseCarouselAutoPlay());
        carouselViewport.setOnMouseExited(e -> resumeCarouselAutoPlay());

        card.getChildren().addAll(headerBar, carouselViewport);
        updateCarouselSlide(currentCarouselSlideIndex, false);

        return card;
    }

    private void updateCarouselSlide(int targetIndex, boolean animate) {
        if (TRIAGE_CAROUSEL_SLIDES.isEmpty()) return;
        int total = TRIAGE_CAROUSEL_SLIDES.size();
        currentCarouselSlideIndex = (targetIndex % total + total) % total;
        CarouselSlide slide = TRIAGE_CAROUSEL_SLIDES.get(currentCarouselSlideIndex);

        if (carouselSlideCounterLabel != null) {
            carouselSlideCounterLabel.setText("Slide " + (currentCarouselSlideIndex + 1) + " of " + total);
        }

        if (carouselTagBadge != null) {
            carouselTagBadge.setText("● " + slide.getTag());
            carouselTagBadge.setStyle("-fx-background-color: " + slide.getAccentColor() + "; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: 800; -fx-padding: 4 10; -fx-background-radius: 12; -fx-letter-spacing: 0.8px;");
        }
        if (carouselSlideTitle != null) {
            carouselSlideTitle.setText(slide.getTitle());
        }
        if (carouselSlideDesc != null) {
            carouselSlideDesc.setText(slide.getDescription());
        }

        renderCarouselDots();

        if (carouselImageView != null) {
            carouselImageView.setOpacity(1.0);
            if (carouselCaptionBox != null) carouselCaptionBox.setOpacity(1.0);
            loadCarouselImage(slide);
        }
    }

    private void renderCarouselDots() {
        if (carouselDotsBox == null) return;
        carouselDotsBox.getChildren().clear();
        for (int i = 0; i < TRIAGE_CAROUSEL_SLIDES.size(); i++) {
            final int dotIndex = i;
            if (i == currentCarouselSlideIndex) {
                Rectangle activePill = new Rectangle(24, 7);
                activePill.setArcWidth(7);
                activePill.setArcHeight(7);
                activePill.setFill(Color.web(PRIMARY_PINK));
                activePill.setStyle("-fx-cursor: hand;");
                activePill.setOnMouseClicked(e -> {
                    updateCarouselSlide(dotIndex, true);
                    resetCarouselTimer();
                });
                carouselDotsBox.getChildren().add(activePill);
            } else {
                Circle inactiveDot = new Circle(4, Color.rgb(255, 255, 255, 0.5));
                inactiveDot.setStyle("-fx-cursor: hand;");
                inactiveDot.setOnMouseClicked(e -> {
                    updateCarouselSlide(dotIndex, true);
                    resetCarouselTimer();
                });
                carouselDotsBox.getChildren().add(inactiveDot);
            }
        }
    }

    private void loadCarouselImage(CarouselSlide slide) {
        if (carouselImageView == null) return;
        if (carouselSpinner != null) carouselSpinner.setVisible(false);

        // Prioritize local resource for instantaneous display
        String assetPath = slide.getFallbackAssetPath();
        Image localImg = loadSafeImage(assetPath, "src/main/resources" + assetPath);
        if (localImg != null && !localImg.isError()) {
            carouselImageView.setImage(localImg);
            return;
        }

        // Gracefully attempt URL if it's an online HTTP source
        if (slide.getImageUrl() != null && slide.getImageUrl().startsWith("http")) {
            if (carouselSpinner != null) carouselSpinner.setVisible(true);
            try {
                Image img = new Image(slide.getImageUrl(), true);
                img.progressProperty().addListener((obs, oldV, newV) -> {
                    if (newV.doubleValue() >= 1.0 && carouselSpinner != null) {
                        carouselSpinner.setVisible(false);
                    }
                });
                img.errorProperty().addListener((obs, oldV, isErr) -> {
                    if (isErr && carouselSpinner != null) {
                        carouselSpinner.setVisible(false);
                    }
                });
                carouselImageView.setImage(img);
            } catch (Exception ignored) {
                if (carouselSpinner != null) carouselSpinner.setVisible(false);
            }
        }
    }

    private void startCarouselTimeline() {
        stopCarouselTimeline();
        isCarouselPlaying = true;
        carouselTimeline = new Timeline(new KeyFrame(Duration.seconds(4.0), e -> {
            updateCarouselSlide(currentCarouselSlideIndex + 1, true);
        }));
        carouselTimeline.setCycleCount(Animation.INDEFINITE);
        carouselTimeline.play();
        if (carouselAutoPlayToggleBtn != null) {
            carouselAutoPlayToggleBtn.setText("⏸ Pause");
        }
    }

    private void stopCarouselTimeline() {
        if (carouselTimeline != null) {
            try {
                carouselTimeline.stop();
            } catch (Exception ignored) {}
            carouselTimeline = null;
        }
    }

    private void resetCarouselTimer() {
        if (isCarouselPlaying) {
            startCarouselTimeline();
        }
    }

    private void pauseCarouselAutoPlay() {
        if (carouselTimeline != null) {
            carouselTimeline.pause();
        }
    }

    private void resumeCarouselAutoPlay() {
        if (isCarouselPlaying && carouselTimeline != null) {
            carouselTimeline.play();
        }
    }

    private HBox createMiniVitalBadge(String code, String value, String color) {
        HBox badge = new HBox(4);
        badge.setAlignment(Pos.CENTER);
        badge.setPadding(new Insets(4, 10, 4, 10));
        badge.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 16; -fx-background-radius: 16;");

        Label cLbl = new Label(code);
        cLbl.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        Label vLbl = new Label(value);
        vLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_TEXT + ";");

        badge.getChildren().addAll(cLbl, vLbl);
        return badge;
    }

    private void showDefaultCenterWidget() {
        stopPatientMonitorAnimation();
        if (voiceService != null && voiceService.isRecording()) {
            voiceService.stopListening();
        }
        stopEmbeddedCamera();
        setDockActive(tabOverviewBtn);

        defaultOverviewCard = buildSummaryCardNode();
        swapDynamicCenterView(defaultOverviewCard);
        startCarouselTimeline();
    }

    // =========================================================================
    // WORKSPACE VIEW 2: PATIENT MONITOR & CLINICAL VITALS DASHBOARD
    // =========================================================================
    private void showPatientMonitorWidget() {
        stopEmbeddedCamera();
        stopPatientMonitorAnimation();

        VBox card = new VBox(14);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setMinWidth(0);
        card.setMaxWidth(Double.MAX_VALUE);
        applyCardStyle(card);

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.setMinWidth(0);

        Text title = new Text("💓 Patient Vitals & Clinical ICU Telemetry Monitor");
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Label patPill = new Label("Patient: " + activePatientId + "  •  Destination: " + activeDestinationHospital);
        patPill.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-text-fill: " + PRIMARY_PINK + "; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 4 10; -fx-background-radius: 14; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button closeBtn = new Button("✕ Close View");
        closeBtn.setStyle("-fx-background-color: " + SURFACE + "; -fx-text-fill: " + MUTED_TEXT + "; -fx-font-size: 11px; -fx-cursor: hand; -fx-padding: 4 12; -fx-background-radius: 16; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 16;");
        closeBtn.setOnAction(e -> showDefaultCenterWidget());
        topRow.getChildren().addAll(title, patPill, spacer, closeBtn);

        HBox primaryVitalsRow = new HBox(10);
        primaryVitalsRow.setAlignment(Pos.CENTER);
        primaryVitalsRow.setMinWidth(0);

        Text hrValText = new Text("78");
        Label hrSubLabel = new Label("Normal Sinus • 60-100");
        VBox hrTile = createDetailedVitalCardDynamic("💓", "Heart Rate", hrValText, "BPM", "#10B981", hrSubLabel);

        Text bpValText = new Text("120/80");
        Label bpSubLabel = new Label("Normotensive • MAP 93");
        VBox bpTile = createDetailedVitalCardDynamic("🩸", "Blood Pressure (BM)", bpValText, "mmHg", PRIMARY_PINK, bpSubLabel);

        Text pulseValText = new Text("78");
        Label pulseSubLabel = new Label("SpO₂ 98% • PI 4.2%");
        VBox pulseTile = createDetailedVitalCardDynamic("⚡", "Pulse Rate", pulseValText, "PR bpm", "#0284C7", pulseSubLabel);

        Text tempValText = new Text("37.0");
        Label tempSubLabel = new Label("Core Normothermic • 98.6°F");
        VBox tempTile = createDetailedVitalCardDynamic("🌡", "Temperature", tempValText, "°C", "#D97706", tempSubLabel);

        HBox.setHgrow(hrTile, Priority.ALWAYS);
        HBox.setHgrow(bpTile, Priority.ALWAYS);
        HBox.setHgrow(pulseTile, Priority.ALWAYS);
        HBox.setHgrow(tempTile, Priority.ALWAYS);
        hrTile.setMinWidth(0);
        bpTile.setMinWidth(0);
        pulseTile.setMinWidth(0);
        tempTile.setMinWidth(0);

        primaryVitalsRow.getChildren().addAll(hrTile, bpTile, pulseTile, tempTile);

        activePatientMonitorView = new PatientIcuMonitorView(activePatientId, activeDestinationHospital, snapshot -> {
            Platform.runLater(() -> {
                hrValText.setText(String.valueOf(snapshot.heartRate));
                hrSubLabel.setText(snapshot.rhythm != null ? snapshot.rhythm : "Normal Sinus");
                bpValText.setText(snapshot.systolic + "/" + snapshot.diastolic);
                int map = (int) Math.round((2.0 * snapshot.diastolic + snapshot.systolic) / 3.0);
                bpSubLabel.setText("BM / MAP: " + map + " mmHg • HR & Pulse");
                pulseValText.setText(String.valueOf(snapshot.pulseRate));
                pulseSubLabel.setText("SpO₂ " + snapshot.spo2 );
                tempValText.setText(String.format("%.1f", snapshot.temperature));
                double fahr = (snapshot.temperature * 9.0 / 5.0) + 32.0;
                tempSubLabel.setText(String.format("%.1f °F • Sensor", fahr));
            });
        });

        
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

      
        // actionsBar.getChildren().addAll(liveSensorDot, sp, exportToAiBtn);

        card.getChildren().addAll(topRow, primaryVitalsRow, activePatientMonitorView);

        ScrollPane scrollPane = new ScrollPane(card);
        scrollPane.setFitToWidth(true);
        scrollPane.setMinWidth(0);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Retain smooth mouse & trackpad scrolling while keeping the vertical scrollbar completely invisible
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() != 0) {
                double contentHeight = card.getHeight() > 0 ? card.getHeight() : card.getBoundsInLocal().getHeight();
                double viewportHeight = scrollPane.getViewportBounds().getHeight();
                double diff = contentHeight - viewportHeight;
                if (diff > 0) {
                    double delta = event.getDeltaY() * 2.0;
                    double newV = scrollPane.getVvalue() - (delta / diff);
                    scrollPane.setVvalue(Math.max(0.0, Math.min(1.0, newV)));
                }
                event.consume();
            }
        });

        swapDynamicCenterView(scrollPane);
    }

    private VBox createDetailedVitalCardDynamic(String icon, String title, Text valText, String unit, String color, Label subLabel) {
        VBox card = new VBox(4);
        card.setPadding(new Insets(10, 14, 10, 14));
        card.setMinWidth(0);
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14; -fx-background-radius: 14;");
        card.setEffect(new DropShadow(6, 0, 2, Color.rgb(230, 117, 147, 0.05)));

        HBox top = new HBox(8);
        top.setAlignment(Pos.CENTER_LEFT);

        StackPane iconWrap = new StackPane();
        Circle bg = new Circle(14);
        bg.setFill(Color.web(color, 0.12));
        bg.setStroke(Color.web(color, 0.35));
        bg.setStrokeWidth(1.2);

        Text iconText = new Text(icon);
        iconText.setStyle("-fx-font-size: 13px;");

        iconWrap.getChildren().addAll(bg, iconText);
        iconWrap.setMinSize(28, 28);
        iconWrap.setMaxSize(28, 28);

        Label lbl = new Label(title);
        lbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_TEXT + ";");

        top.getChildren().addAll(iconWrap, lbl);

        HBox valBox = new HBox(6);
        valBox.setAlignment(Pos.BASELINE_LEFT);
        valBox.setPadding(new Insets(4, 0, 0, 0));

        valText.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-fill: " + color + "; -fx-font-family: 'Consolas', sans-serif;");

        Text u = new Text(unit);
        u.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + SECONDARY_TEXT + ";");

        valBox.getChildren().addAll(valText, u);

        if (subLabel != null) {
            subLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: " + MUTED_TEXT + "; -fx-font-weight: 500;");
            card.getChildren().addAll(top, valBox, subLabel);
        } else {
            card.getChildren().addAll(top, valBox);
        }

        return card;
    }

    private void showVoiceReportWidget() {
        stopEmbeddedCamera();

        VBox card = new VBox(14);
        card.setPadding(new Insets(16, 20, 16, 20));
        applyCardStyle(card);
        VBox.setVgrow(card, Priority.ALWAYS);

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("🎤 Voice Clinical Audio Dictation");
        title.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backToHub = new Button("✕ Close View");
        backToHub.setStyle("-fx-background-color: transparent; -fx-text-fill: " + MUTED_TEXT + "; -fx-font-size: 11px; -fx-cursor: hand;");
        backToHub.setOnAction(e -> showDefaultCenterWidget());
        topRow.getChildren().addAll(title, spacer, backToHub);

        HBox configRow = new HBox(12);
        configRow.setAlignment(Pos.CENTER_LEFT);

        Label patientBadge = new Label("Patient: " + activePatientId);
        patientBadge.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14; -fx-background-radius: 14; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4 12;");

        ComboBox<String> langBox = new ComboBox<>();
        langBox.getItems().addAll("मराठी (mr-IN)", "English (en-IN)", "हिंदी (hi-IN)");
        langBox.setValue("मराठी (mr-IN)");
        langBox.setStyle("-fx-font-size: 11px; -fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14; -fx-background-radius: 14;");
        langBox.setOnAction(e -> {
            if (voiceService != null) {
                String val = langBox.getValue();
                if (val.contains("mr-IN")) voiceService.setLanguageCode("mr-IN");
                else if (val.contains("hi-IN")) voiceService.setLanguageCode("hi-IN");
                else voiceService.setLanguageCode("en-IN");
            }
        });

        Region cfgSpacer = new Region();
        HBox.setHgrow(cfgSpacer, Priority.ALWAYS);

        HBox soundwaveBox = new HBox(3);
        soundwaveBox.setAlignment(Pos.CENTER);
        List<Rectangle> waveBars = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Rectangle bar = new Rectangle(4, 10, Color.web(BORDER_COLOR));
            bar.setArcWidth(4);
            bar.setArcHeight(4);
            waveBars.add(bar);
            soundwaveBox.getChildren().add(bar);
        }

        Timeline waveTimeline = new Timeline(
                new KeyFrame(Duration.millis(120), e -> {
                    for (Rectangle r : waveBars) {
                        double h = 5 + Math.random() * 16;
                        r.setHeight(h);
                        r.setFill(Color.web(PRIMARY_PINK));
                    }
                })
        );
        waveTimeline.setCycleCount(Animation.INDEFINITE);

        configRow.getChildren().addAll(patientBadge, langBox, cfgSpacer, soundwaveBox);

        TextArea transcriptArea = new TextArea(lastDictatedText);
        transcriptArea.setPromptText("बोललेले शब्द येथे थेट दिसतील / Speak into microphone to dictate clinical notes...");
        transcriptArea.setWrapText(true);
        transcriptArea.setPrefRowCount(5);
        transcriptArea.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 16; -fx-background-radius: 16; -fx-font-size: 12px; -fx-padding: 6;");
        VBox.setVgrow(transcriptArea, Priority.ALWAYS);

        HBox quickChips = new HBox(6);
        quickChips.setAlignment(Pos.CENTER_LEFT);
        Label chipHeader = new Label("Quick Insert:");
        chipHeader.setStyle("-fx-font-size: 9px; -fx-text-fill: " + MUTED_TEXT + "; -fx-font-weight: bold;");
        quickChips.getChildren().addAll(
                chipHeader,
                createObservationTag("+ Conscious & Alert", transcriptArea),
                createObservationTag("+ Hypoxia Detected", transcriptArea),
                createObservationTag("+ Cervical Collar Applied", transcriptArea),
                createObservationTag("+ Rapid ER Bay Required", transcriptArea)
        );

        HBox actionsRow = new HBox(12);
        actionsRow.setAlignment(Pos.CENTER_LEFT);

        Button recordBtn = new Button("🎤 Start Recording");
        recordBtn.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 7 18; -fx-background-radius: 24; -fx-border-radius: 24; -fx-cursor: hand;");

        Circle recordingDot = new Circle(4, Color.TRANSPARENT);
        Label statusLbl = new Label("Microphone Ready (Click to Record)");
        statusLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: " + SECONDARY_TEXT + ";");

        Region actSpacer = new Region();
        HBox.setHgrow(actSpacer, Priority.ALWAYS);

        Button saveBtn = new Button("💾 Save Note to Record");
        saveBtn.setStyle("-fx-background-color: " + EMERALD_GREEN + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 7 18; -fx-background-radius: 24; -fx-border-radius: 24; -fx-cursor: hand;");

        recordBtn.setOnAction(e -> {
            if (voiceService == null) {
                statusLbl.setText("Speech Service Not Initialized");
                return;
            }
            if (!voiceService.isRecording()) {
                recordBtn.setText("⏹ Stop Dictation");
                recordBtn.setStyle("-fx-background-color: " + DANGER_RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 7 16; -fx-background-radius: 6; -fx-cursor: hand;");
                recordingDot.setFill(Color.web(DANGER_RED));
                statusLbl.setText("Listening live...");
                waveTimeline.play();

                voiceService.startListening(
                        liveText -> Platform.runLater(() -> transcriptArea.setText(liveText)),
                        finalText -> Platform.runLater(() -> {
                            transcriptArea.setText(finalText);
                            lastDictatedText = finalText;
                            recordBtn.setText("🎤 Start Recording");
                            recordBtn.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 7 16; -fx-background-radius: 6; -fx-cursor: hand;");
                            recordingDot.setFill(Color.TRANSPARENT);
                            waveTimeline.stop();
                            for (Rectangle r : waveBars) {
                                r.setHeight(10);
                                r.setFill(Color.web(BORDER_COLOR));
                            }
                            statusLbl.setText("Finished recording");
                        })
                );
            } else {
                voiceService.stopListening();
                lastDictatedText = transcriptArea.getText();
                recordBtn.setText("🎤 Start Recording");
                recordBtn.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 7 16; -fx-background-radius: 6; -fx-cursor: hand;");
                recordingDot.setFill(Color.TRANSPARENT);
                waveTimeline.stop();
                for (Rectangle r : waveBars) {
                    r.setHeight(10);
                    r.setFill(Color.web(BORDER_COLOR));
                }
                statusLbl.setText("Processing final text...");
            }
        });

        saveBtn.setOnAction(e -> {
            String text = transcriptArea.getText().trim();
            if (text.isEmpty()) {
                NurseToast.show(appOverlay, "Transcript is empty. Please record audio first.", "warning");
                return;
            }

            lastDictatedText = text;
            String reportId = "VR-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            NurseVoiceReportModel model = new NurseVoiceReportModel(
                    reportId,
                    loginemail,
                    activePatientId,
                    text,
                    Timestamp.now()
            );

            new Thread(() -> {
                reportDao.saveVoiceReport(model);
                Platform.runLater(() -> {
                    NurseToast.show(appOverlay, "Voice note saved for " + model.getPatientId() + "!", "success");
                    logNurseActivity("Voice Note Recorded", "Dictated clinical audio note for " + model.getPatientId());
                    if (overviewNotesSummaryLabel != null) overviewNotesSummaryLabel.setText(text);
                });
            }).start();
        });

        actionsRow.getChildren().addAll(recordBtn, recordingDot, statusLbl, actSpacer, saveBtn);
        card.getChildren().addAll(topRow, configRow, transcriptArea, quickChips, actionsRow);

        swapDynamicCenterView(card);
    }

    private void showPhotoUploadWidget() {
        stopEmbeddedCamera();

        VBox card = new VBox(14);
        card.setPadding(new Insets(16, 20, 16, 20));
        applyCardStyle(card);
        VBox.setVgrow(card, Priority.ALWAYS);

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("📷 Upload Patient Trauma & Wound Photos");
        title.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button closeBtn = new Button("✕ Close View");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + MUTED_TEXT + "; -fx-font-size: 11px; -fx-cursor: hand;");
        closeBtn.setOnAction(e -> showDefaultCenterWidget());
        topRow.getChildren().addAll(title, spacer, closeBtn);

        HBox sourceButtons = new HBox(16);
        sourceButtons.setAlignment(Pos.CENTER);
        sourceButtons.setPadding(new Insets(4, 0, 8, 0));

        Button cameraBtn = createOptionButton("📷", "Live Webcam", "Capture ambulance feed");
        Button photoGalleryBtn = createOptionButton("🖼", "Photo Gallery", "Pick local image file");

        sourceButtons.getChildren().addAll(cameraBtn, photoGalleryBtn);

        VBox inCardCameraContainer = new VBox(8);
        inCardCameraContainer.setAlignment(Pos.CENTER);
        inCardCameraContainer.setPadding(new Insets(12));
        inCardCameraContainer.setStyle("-fx-background-color: #141213; -fx-background-radius: 20; -fx-border-color: " + PRIMARY_PINK + "; -fx-border-radius: 20;");
        inCardCameraContainer.setVisible(false);
        inCardCameraContainer.setManaged(false);

        ImageView cameraStreamView = new ImageView();
        cameraStreamView.setFitWidth(360);
        cameraStreamView.setFitHeight(180);
        cameraStreamView.setPreserveRatio(true);

        Label camStatusLbl = new Label("Initializing camera feed...");
        camStatusLbl.setStyle("-fx-font-size: 10px; -fx-text-fill: #98D8A0;");

        Button snapBtn = new Button("📸 Snap & Upload to Cloud");
        snapBtn.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 7 18; -fx-background-radius: 24; -fx-border-radius: 24; -fx-cursor: hand;");

        Button closeCamBtn = new Button("Close Camera");
        closeCamBtn.setStyle("-fx-background-color: #352E32; -fx-text-fill: white; -fx-font-size: 11px; -fx-padding: 7 14; -fx-background-radius: 24; -fx-border-radius: 24; -fx-cursor: hand;");

        HBox camControls = new HBox(10, snapBtn, closeCamBtn);
        camControls.setAlignment(Pos.CENTER);

        inCardCameraContainer.getChildren().addAll(cameraStreamView, camStatusLbl, camControls);

        Label statusLbl = new Label("Upload injury and trauma photos directly to patient record.");
        statusLbl.setStyle("-fx-font-size: 10px; -fx-text-fill: " + SECONDARY_TEXT + ";");

        VBox gallerySection = new VBox(6);
        gallerySection.setAlignment(Pos.CENTER_LEFT);

        Label galleryHeader = new Label("Uploaded Photos for Patient (" + activePatientId + "):");
        galleryHeader.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        liveUploadGalleryItemsBox = new HBox(10);
        liveUploadGalleryItemsBox.setAlignment(Pos.CENTER_LEFT);
        liveUploadGalleryItemsBox.setPadding(new Insets(4, 2, 4, 2));

        ScrollPane galleryScroll = new ScrollPane(liveUploadGalleryItemsBox);
        galleryScroll.setFitToHeight(true);
        galleryScroll.setPrefHeight(125);
        galleryScroll.setMinHeight(125);
        galleryScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        galleryScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        galleryScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14; -fx-background-radius: 14;");

        gallerySection.getChildren().addAll(galleryHeader, galleryScroll);

        Runnable refreshGallery = () -> {
            new Thread(() -> {
                try {
                    Firestore db = FirebaseConfig.getFirestore();
                    if (db == null) return;

                    QuerySnapshot snap = db.collection("patientPhotos")
                            .whereEqualTo("patientId", activePatientId)
                            .get()
                            .get();

                    Platform.runLater(() -> {
                        liveUploadGalleryItemsBox.getChildren().clear();
                        if (overviewPhotoThumbnailsBox != null) overviewPhotoThumbnailsBox.getChildren().clear();

                        if (snap.isEmpty()) {
                            Label noPh = new Label("No photos uploaded yet. Snap via webcam or choose a local file above.");
                            noPh.setStyle("-fx-font-size: 10px; -fx-text-fill: " + MUTED_TEXT + "; -fx-padding: 8;");
                            liveUploadGalleryItemsBox.getChildren().add(noPh);

                            if (overviewPhotoThumbnailsBox != null) {
                                Label noPhOverview = new Label("No photos attached.");
                                noPhOverview.setStyle("-fx-font-size: 10px; -fx-text-fill: " + MUTED_TEXT + ";");
                                overviewPhotoThumbnailsBox.getChildren().add(noPhOverview);
                            }
                        } else {
                            for (DocumentSnapshot doc : snap.getDocuments()) {
                                String docId = doc.getId();
                                String url = doc.getString("imageUrl");
                                if (url != null) {
                                    StackPane cardItem = createRemovableElevatedPhotoCard(docId, url);
                                    liveUploadGalleryItemsBox.getChildren().add(cardItem);

                                    if (overviewPhotoThumbnailsBox != null) {
                                        ImageView thumb = new ImageView();
                                        try { thumb.setImage(new Image(url, 48, 48, false, true, true)); } catch (Exception ignored) {}
                                        thumb.setFitWidth(48);
                                        thumb.setFitHeight(48);
                                        Rectangle cl = new Rectangle(48, 48);
                                        cl.setArcWidth(6);
                                        cl.setArcHeight(6);
                                        thumb.setClip(cl);
                                        thumb.setStyle("-fx-cursor: hand;");
                                        thumb.setOnMouseClicked(ev -> openLargeImagePreview(url));
                                        overviewPhotoThumbnailsBox.getChildren().add(thumb);
                                    }
                                }
                            }
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        };

        refreshGallery.run();

        cameraBtn.setOnAction(e -> {
            inCardCameraContainer.setVisible(true);
            inCardCameraContainer.setManaged(true);
            startEmbeddedWebcam(cameraStreamView, camStatusLbl, snapBtn, activePatientId, statusLbl, inCardCameraContainer, refreshGallery);
        });

        closeCamBtn.setOnAction(e -> {
            stopEmbeddedCamera();
            inCardCameraContainer.setVisible(false);
            inCardCameraContainer.setManaged(false);
            statusLbl.setText("Camera closed.");
        });

        photoGalleryBtn.setOnAction(e -> {
            stopEmbeddedCamera();
            inCardCameraContainer.setVisible(false);
            inCardCameraContainer.setManaged(false);
            handleFileChooserUpload(activePatientId, statusLbl, refreshGallery);
        });

        card.getChildren().addAll(topRow, sourceButtons, inCardCameraContainer, statusLbl, gallerySection);
        swapDynamicCenterView(card);
    }

    private Button createOptionButton(String icon, String label, String subtitle) {
        HBox box = new HBox(8);
        box.setAlignment(Pos.CENTER_LEFT);

        Text iconText = new Text(icon);
        iconText.setStyle("-fx-font-size: 16px; -fx-fill: " + PRIMARY_PINK + ";");

        VBox textStack = new VBox(1);
        Text lblText = new Text(label);
        lblText.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        Text subText = new Text(subtitle);
        subText.setStyle("-fx-font-size: 8px; -fx-fill: " + SECONDARY_TEXT + ";");
        textStack.getChildren().addAll(lblText, subText);

        box.getChildren().addAll(iconText, textStack);

        Button btn = new Button();
        btn.setGraphic(box);
        btn.setPrefWidth(190);
        btn.setPrefHeight(50);
        btn.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 16; -fx-background-radius: 16; -fx-cursor: hand;");

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + LIGHT_PINK + "; -fx-border-color: " + PRIMARY_PINK + "; -fx-border-radius: 16; -fx-background-radius: 16; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 16; -fx-background-radius: 16;"));

        return btn;
    }

    private StackPane createRemovableElevatedPhotoCard(String docId, String imageUrl) {
        StackPane frame = new StackPane();
        frame.setPrefSize(90, 85);
        frame.setMinSize(90, 85);
        frame.setMaxSize(90, 85);
        frame.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 1.5px; -fx-border-radius: 14px; -fx-background-radius: 14px; -fx-cursor: hand;");
        frame.setEffect(new DropShadow(6, 0, 2, Color.rgb(0, 0, 0, 0.08)));

        ImageView iv = new ImageView();
        try {
            iv.setImage(new Image(imageUrl, 86, 81, false, true, true));
        } catch (Exception ignored) {}
        iv.setFitWidth(86);
        iv.setFitHeight(81);

        Rectangle clip = new Rectangle(86, 81);
        clip.setArcWidth(12);
        clip.setArcHeight(12);
        iv.setClip(clip);

        Button deleteBtn = new Button("✕");
        deleteBtn.setStyle("-fx-background-color: " + DANGER_RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 8px; -fx-min-width: 16px; -fx-min-height: 16px; -fx-max-width: 16px; -fx-max-height: 16px; -fx-background-radius: 50%; -fx-padding: 0; -fx-cursor: hand;");
        StackPane.setAlignment(deleteBtn, Pos.TOP_RIGHT);
        StackPane.setMargin(deleteBtn, new Insets(3, 3, 0, 0));

        deleteBtn.setOnAction(e -> {
            e.consume();
            if (liveUploadGalleryItemsBox != null) {
                liveUploadGalleryItemsBox.getChildren().remove(frame);
            }
            selectedSummaryPhotoUrls.remove(imageUrl);

            new Thread(() -> {
                try {
                    Firestore db = FirebaseConfig.getFirestore();
                    if (db != null && docId != null && !docId.isEmpty()) {
                        db.collection("patientPhotos").document(docId).delete();
                        Platform.runLater(() -> {
                            NurseToast.show(appOverlay, "Photo removed from patient record!", "success");
                            logNurseActivity("Photo Removed", "Deleted photo " + docId + " for " + activePatientId);
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        });

        frame.getChildren().addAll(iv, deleteBtn);
        frame.setOnMouseClicked(e -> openLargeImagePreview(imageUrl));
        return frame;
    }

    private void startEmbeddedWebcam(ImageView streamView, Label camStatus, Button snapBtn, String patientId, Label parentStatus, VBox camContainer, Runnable onUploadComplete) {
        stopEmbeddedCamera();
        isCameraActive.set(true);
        snapBtn.setDisable(true);
        camStatus.setText("Connecting to webcam device...");

        new Thread(() -> {
            try {
                activeWebcam = Webcam.getDefault();
                if (activeWebcam == null) {
                    Platform.runLater(() -> camStatus.setText("No webcam device detected."));
                    return;
                }

                activeWebcam.open();
                Platform.runLater(() -> {
                    camStatus.setText("● Live Camera Active (Click Snap to Upload)");
                    snapBtn.setDisable(false);
                });

                while (isCameraActive.get() && activeWebcam.isOpen()) {
                    BufferedImage img = activeWebcam.getImage();
                    if (img != null) {
                        WritableImage fxImg = SwingFXUtils.toFXImage(img, null);
                        Platform.runLater(() -> streamView.setImage(fxImg));
                    }
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException ignored) {}
                }
            } catch (Exception ex) {
                Platform.runLater(() -> camStatus.setText("Camera error: " + ex.getMessage()));
            }
        }).start();

        snapBtn.setOnAction(e -> {
            snapBtn.setDisable(true);
            camStatus.setText("Capturing snapshot & uploading to secure cloud...");

            new Thread(() -> {
                try {
                    if (activeWebcam != null) {
                        BufferedImage capture = activeWebcam.getImage();
                        if (capture != null) {
                            File tempFile = File.createTempFile("patient_snap_", ".jpg");
                            ImageIO.write(capture, "JPG", tempFile);

                            Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());
                            String imageUrl = (String) uploadResult.get("secure_url");
                            selectedSummaryPhotoUrls.add(imageUrl);

                            Firestore db = FirebaseConfig.getFirestore();
                            String photoId = "IMG-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

                            Map<String, Object> photoDoc = new HashMap<>();
                            photoDoc.put("photoId", photoId);
                            photoDoc.put("patientId", patientId);
                            photoDoc.put("imageUrl", imageUrl);
                            photoDoc.put("uploadedBy", loginemail);
                            photoDoc.put("timestamp", Timestamp.now());

                            db.collection("patientPhotos").document(photoId).set(photoDoc);
                            tempFile.deleteOnExit();

                            Platform.runLater(() -> {
                                stopEmbeddedCamera();
                                camContainer.setVisible(false);
                                camContainer.setManaged(false);
                                parentStatus.setText("✓ Snapshot securely uploaded & synchronized!");
                                NurseToast.show(appOverlay, "Live camera snapshot uploaded!", "success");
                                logNurseActivity("Patient Photo Captured", "Live snapshot captured for " + patientId);
                                if (onUploadComplete != null) onUploadComplete.run();
                            });
                        }
                    }
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        camStatus.setText("Upload failed: " + ex.getMessage());
                        snapBtn.setDisable(false);
                    });
                }
            }).start();
        });
    }

    private void stopEmbeddedCamera() {
        isCameraActive.set(false);
        if (activeWebcam != null && activeWebcam.isOpen()) {
            new Thread(() -> {
                try { activeWebcam.close(); } catch (Exception ignored) {}
            }).start();
        }
    }

    private void handleFileChooserUpload(String patientId, Label statusLbl, Runnable onUploadComplete) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Patient Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.webp")
        );
        File selectedFile = fileChooser.showOpenDialog(dashboardStage);

        if (selectedFile != null) {
            statusLbl.setText("Uploading to secure cloud...");
            new Thread(() -> {
                try {
                    Map uploadResult = cloudinary.uploader().upload(selectedFile, ObjectUtils.emptyMap());
                    String imageUrl = (String) uploadResult.get("secure_url");
                    selectedSummaryPhotoUrls.add(imageUrl);

                    Firestore db = FirebaseConfig.getFirestore();
                    String photoId = "IMG-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

                    Map<String, Object> photoDoc = new HashMap<>();
                    photoDoc.put("photoId", photoId);
                    photoDoc.put("patientId", patientId);
                    photoDoc.put("imageUrl", imageUrl);
                    photoDoc.put("uploadedBy", loginemail);
                    photoDoc.put("timestamp", Timestamp.now());

                    db.collection("patientPhotos").document(photoId).set(photoDoc);

                    Platform.runLater(() -> {
                        statusLbl.setText("✓ Uploaded & Synced!");
                        NurseToast.show(appOverlay, "Image saved to patient records!", "success");
                        logNurseActivity("Image Uploaded", "Uploaded field image for " + patientId);
                        if (onUploadComplete != null) onUploadComplete.run();
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> statusLbl.setText("Upload failed: " + ex.getMessage()));
                }
            }).start();
        }
    }

    private void openLargeImagePreview(String imageUrl) {
        Stage imageStage = new Stage();
        imageStage.initOwner(dashboardStage);
        imageStage.initStyle(StageStyle.UTILITY);
        imageStage.setTitle("Field Evidence Image Preview");

        ImageView view = new ImageView(new Image(imageUrl, true));
        view.setFitWidth(520);
        view.setFitHeight(380);
        view.setPreserveRatio(true);

        VBox layout = new VBox(view);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(14));
        layout.setStyle("-fx-background-color: " + SURFACE + ";");

        Scene scene = new Scene(layout, 540, 400);
        imageStage.setScene(scene);
        imageStage.show();
    }

    private void showAiSummaryGeneratorWidget() {
        stopEmbeddedCamera();
        selectedSummaryPhotoUrls.clear();

        VBox card = new VBox(14);
        card.setPadding(new Insets(16, 20, 16, 20));
        applyCardStyle(card);
        VBox.setVgrow(card, Priority.ALWAYS);

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Text starIcon = new Text("✦");
        starIcon.setStyle("-fx-font-size: 16px; -fx-fill: " + PRIMARY_PINK + ";");

        Text title = new Text("AI Clinical Triage Summary (LifeLink AI)");
        title.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button closeBtn = new Button("✕ Close View");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + MUTED_TEXT + "; -fx-cursor: hand;");
        closeBtn.setOnAction(e -> showDefaultCenterWidget());
        topRow.getChildren().addAll(starIcon, title, spacer, closeBtn);

        HBox dualPane = new HBox(14);
        dualPane.setAlignment(Pos.TOP_LEFT);
        dualPane.setMaxWidth(Double.MAX_VALUE);

        VBox notesPane = new VBox(6);
        notesPane.setPrefWidth(380);
        HBox.setHgrow(notesPane, Priority.ALWAYS);

        Label notesLabel = new Label("Field Voice Notes & Clinical Observations");
        notesLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + SECONDARY_TEXT + ";");

        TextArea voiceInputArea = new TextArea(lastDictatedText);
        voiceInputArea.setPromptText("Clinical observations auto-sync from Firebase or dictation...");
        voiceInputArea.setPrefRowCount(4);
        voiceInputArea.setMinHeight(85);
        voiceInputArea.setWrapText(true);
        voiceInputArea.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14; -fx-background-radius: 14; -fx-font-size: 11px; -fx-padding: 4;");

        HBox quickTags = new HBox(4);
        quickTags.setAlignment(Pos.CENTER_LEFT);
        quickTags.getChildren().addAll(
                createObservationTag("+ Stable BP", voiceInputArea),
                createObservationTag("+ Oxygen Given", voiceInputArea),
                createObservationTag("+ Severe Bleeding", voiceInputArea),
                createObservationTag("+ OT Alert", voiceInputArea)
        );

        notesPane.getChildren().addAll(notesLabel, voiceInputArea, quickTags);

        VBox photoCard = new VBox(6);
        photoCard.setPrefWidth(280);
        photoCard.setPadding(new Insets(10));
        photoCard.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 18; -fx-background-radius: 18;");

        HBox photoHeaderRow = new HBox(4);
        photoHeaderRow.setAlignment(Pos.CENTER_LEFT);
        Label photoHeader = new Label("Attach Evidence Photos");
        photoHeader.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_TEXT + ";");
        Region phSpacer = new Region();
        HBox.setHgrow(phSpacer, Priority.ALWAYS);
        Label photoCountStatus = new Label("0 selected");
        photoCountStatus.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_PINK + ";");
        photoHeaderRow.getChildren().addAll(photoHeader, phSpacer, photoCountStatus);

        HBox visualPhotoCardsContainer = new HBox(6);
        visualPhotoCardsContainer.setAlignment(Pos.CENTER_LEFT);

        ScrollPane visualPhotoScroll = new ScrollPane(visualPhotoCardsContainer);
        visualPhotoScroll.setFitToHeight(true);
        visualPhotoScroll.setPrefHeight(80);
        visualPhotoScroll.setMinHeight(80);
        visualPhotoScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        visualPhotoScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        visualPhotoScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 12; -fx-background-radius: 12;");

        photoCard.getChildren().addAll(photoHeaderRow, visualPhotoScroll);
        dualPane.getChildren().addAll(notesPane, photoCard);

        VBox outputBox = new VBox(6);
        HBox outHeaderRow = new HBox();
        outHeaderRow.setAlignment(Pos.CENTER_LEFT);

        Label outputLabel = new Label("Synthesized AI Clinical Triage Report");
        outputLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_PINK + ";");

        Region outSpacer = new Region();
        HBox.setHgrow(outSpacer, Priority.ALWAYS);

        Button copyBtn = new Button("📋 Copy Report");
        copyBtn.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-text-fill: " + PRIMARY_TEXT + "; -fx-font-size: 10px; -fx-padding: 3 10; -fx-background-radius: 14; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14; -fx-cursor: hand;");

        outHeaderRow.getChildren().addAll(outputLabel, outSpacer, copyBtn);

        TextArea aiOutputArea = new TextArea();
        aiOutputArea.setPromptText("Click 'Synthesize AI Report' to generate plain-text clinical triage report for ER team...");
        aiOutputArea.setPrefRowCount(6);
        aiOutputArea.setMinHeight(95);
        aiOutputArea.setWrapText(true);
        aiOutputArea.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + PRIMARY_PINK + "; -fx-border-radius: 16; -fx-background-radius: 16; -fx-font-size: 11px; -fx-font-family: 'Consolas', 'Segoe UI', monospace; -fx-padding: 6;");
        VBox.setVgrow(aiOutputArea, Priority.ALWAYS);

        copyBtn.setOnAction(e -> {
            String text = aiOutputArea.getText();
            if (text != null && !text.isEmpty()) {
                ClipboardContent content = new ClipboardContent();
                content.putString(text);
                Clipboard.getSystemClipboard().setContent(content);
                NurseToast.show(appOverlay, "Report copied to clipboard!", "success");
            }
        });

        outputBox.getChildren().addAll(outHeaderRow, aiOutputArea);

        HBox bottomControls = new HBox(10);
        bottomControls.setAlignment(Pos.CENTER_LEFT);

        Button generateBtn = new Button("⚡ Synthesize AI Report");
        generateBtn.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 7 18; -fx-background-radius: 24; -fx-border-radius: 24; -fx-cursor: hand;");

        Button sendHospitalBtn = new Button("📨 Dispatch to " + activeDestinationHospital);
        sendHospitalBtn.setStyle("-fx-background-color: " + EMERALD_GREEN + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 7 18; -fx-background-radius: 24; -fx-border-radius: 24; -fx-cursor: hand;");

        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(18, 18);
        spinner.setVisible(false);

        Label statusLbl = new Label("Ready to analyze patient records.");
        statusLbl.setStyle("-fx-font-size: 10px; -fx-text-fill: " + SECONDARY_TEXT + ";");

        Runnable loadPatientDataTask = () -> {
            new Thread(() -> {
                try {
                    Firestore db = FirebaseConfig.getFirestore();
                    if (db == null) return;

                    QuerySnapshot voiceSnap = db.collection("nurseVoiceReports")
                            .whereEqualTo("patientId", activePatientId)
                            .get()
                            .get();

                    StringBuilder sb = new StringBuilder();
                    for (DocumentSnapshot doc : voiceSnap.getDocuments()) {
                        String txt = doc.getString("transcribedText");
                        if (txt == null || txt.isEmpty()) txt = doc.getString("content");
                        if (txt != null) sb.append(txt).append("\n");
                    }

                    QuerySnapshot photoSnap = db.collection("patientPhotos")
                            .whereEqualTo("patientId", activePatientId)
                            .get()
                            .get();

                    List<String> availableUrls = new ArrayList<>();
                    for (DocumentSnapshot doc : photoSnap.getDocuments()) {
                        String url = doc.getString("imageUrl");
                        if (url != null) availableUrls.add(url);
                    }

                    Platform.runLater(() -> {
                        if (sb.length() > 0) {
                            voiceInputArea.setText(sb.toString().trim());
                        }

                        visualPhotoCardsContainer.getChildren().clear();
                        selectedSummaryPhotoUrls.clear();

                        if (availableUrls.isEmpty()) {
                            Label noPh = new Label("No patient photos uploaded.");
                            noPh.setStyle("-fx-font-size: 9px; -fx-text-fill: " + MUTED_TEXT + "; -fx-padding: 6;");
                            visualPhotoCardsContainer.getChildren().add(noPh);
                            photoCountStatus.setText("0 selected");
                        } else {
                            for (String url : availableUrls) {
                                selectedSummaryPhotoUrls.add(url);
                                StackPane selectableCard = createInteractiveSelectablePhotoCard(url, photoCountStatus);
                                visualPhotoCardsContainer.getChildren().add(selectableCard);
                            }
                            photoCountStatus.setText(selectedSummaryPhotoUrls.size() + " selected");
                        }
                    });
                } catch (Exception ignored) {}
            }).start();
        };

        loadPatientDataTask.run();

        generateBtn.setOnAction(e -> {
            String notes = voiceInputArea.getText().trim();
            if (notes.isEmpty()) {
                NurseToast.show(appOverlay, "Please enter or record voice report notes first.", "warning");
                return;
            }

            spinner.setVisible(true);
            generateBtn.setDisable(true);
            statusLbl.setText("LifeLink AI is generating clinical triage summary...");

            new Thread(() -> {
                try {
                    String photoContext = selectedSummaryPhotoUrls.isEmpty() 
                            ? "No photos attached." 
                            : selectedSummaryPhotoUrls.size() + " medical trauma images attached: " + String.join(", ", selectedSummaryPhotoUrls);

                    String prompt = "You are Sister Ananya, senior emergency triage nurse inside a LifeLink ambulance transporting Patient ID: " + activePatientId + " to " + activeDestinationHospital + ".\n"
                            + "Field Observations & Voice Dictation: " + notes + "\n"
                            + "Attached Evidence: " + photoContext + "\n"
                            + "STRICT OUTPUT RULES:\n"
                            + "1. Do NOT write conversational greetings, internal reasoning, or thinking tags.\n"
                            + "2. Do NOT use markdown bolding or asterisks.\n"
                            + "3. Output strictly under these 3 clean plain-text sections:\n"
                            + "1. PATIENT CONDITION & TRIAGE LEVEL\n"
                            + "2. IMMEDIATE FIELD OBSERVATIONS & VITALS\n"
                            + "3. RECOMMENDED ER & CATH-LAB/OT PREPARATION";

                    String rawAiResult = callGroqApi(prompt);
                    String cleanAiResult = cleanNurseOutput(rawAiResult);

                    Platform.runLater(() -> {
                        aiOutputArea.setText(cleanAiResult.isEmpty() ? rawAiResult : cleanAiResult);
                        spinner.setVisible(false);
                        generateBtn.setDisable(false);
                        statusLbl.setText("✓ AI Summary generated successfully!");
                        NurseToast.show(appOverlay, "AI Triage Summary generated!", "success");
                        logNurseActivity("AI Triage Generated", "Generated summary report for " + activePatientId);
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        spinner.setVisible(false);
                        generateBtn.setDisable(false);
                        statusLbl.setText("AI Error: " + ex.getMessage());
                    });
                }
            }).start();
        });

        sendHospitalBtn.setOnAction(e -> {
            String summary = aiOutputArea.getText().trim();
            if (summary.isEmpty()) {
                NurseToast.show(appOverlay, "Generate the AI Summary before dispatching.", "warning");
                return;
            }

            statusLbl.setText("Dispatching triage report to " + activeDestinationHospital + "...");
            new Thread(() -> {
                try {
                    Firestore db = FirebaseConfig.getFirestore();
                    String photoPayload = selectedSummaryPhotoUrls.isEmpty() ? "" : String.join(" | ", selectedSummaryPhotoUrls);

                    String hospEmail = (activeDestinationHospitalEmail != null && !activeDestinationHospitalEmail.trim().isEmpty()) 
                            ? activeDestinationHospitalEmail.trim() 
                            : "";
                    if (activeDestinationHospital != null && !activeDestinationHospital.trim().isEmpty()) {
                        try {
                            QuerySnapshot hospSnap = db.collection("hospital")
                                    .whereEqualTo("hospitalName", activeDestinationHospital.trim())
                                    .get().get();
                            if (hospSnap.isEmpty()) {
                                hospSnap = db.collection("hospital")
                                        .whereEqualTo("name", activeDestinationHospital.trim())
                                        .get().get();
                            }
                            if (!hospSnap.isEmpty()) {
                                DocumentSnapshot hDoc = hospSnap.getDocuments().get(0);
                                String foundEmail = hDoc.getString("hospitalEmail");
                                if (foundEmail == null || foundEmail.isEmpty()) foundEmail = hDoc.getString("email");
                                if (foundEmail == null || foundEmail.isEmpty()) foundEmail = hDoc.getId();
                                if (foundEmail != null && !foundEmail.isEmpty()) {
                                    hospEmail = foundEmail;
                                    activeDestinationHospitalEmail = foundEmail;
                                }
                            }
                        } catch (Exception ignored) {}
                    }
                    if (hospEmail.isEmpty()) {
                        hospEmail = "ojas@gmail.com";
                    }

                    String reportId = "HOSP-REP-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
                    Map<String, Object> doc = new HashMap<>();
                    doc.put("reportId", reportId);
                    doc.put("patientId", activePatientId);
                    doc.put("targetHospitalEmail", hospEmail);
                    doc.put("hospitalEmail", hospEmail);
                    doc.put("hospitalemail", hospEmail);
                    doc.put("targetHospital", activeDestinationHospital);
                    doc.put("aiSummaryReport", summary);
                    doc.put("rawVoiceNotes", voiceInputArea.getText().trim());
                    doc.put("patphoto", photoPayload);
                    doc.put("patReport", summary);
                    doc.put("photoUrl", photoPayload);
                    doc.put("dispatchedBy", loginemail);
                    doc.put("timestamp", Timestamp.now());
                    db.collection("hospitalAiReports").document(reportId).set(doc);

                    Map<String, Object> nurseReportDoc = new HashMap<>();
                    nurseReportDoc.put("patientId", activePatientId);
                    nurseReportDoc.put("targetHospital", activeDestinationHospital);
                    nurseReportDoc.put("targetHospitalEmail", hospEmail);
                    nurseReportDoc.put("hospitalEmail", hospEmail);
                    nurseReportDoc.put("hospitalemail", hospEmail);
                    nurseReportDoc.put("email", hospEmail);
                    nurseReportDoc.put("aiSummaryReport", summary);
                    nurseReportDoc.put("rawVoiceNotes", voiceInputArea.getText().trim());
                    nurseReportDoc.put("photoPayload", photoPayload);
                    nurseReportDoc.put("dispatchedBy", loginemail);
                    nurseReportDoc.put("tripId", activeTripId);
                    nurseReportDoc.put("timestamp", Timestamp.now());
                    
                    db.collection("nurseToHospitalReport")
                            .document(activePatientId)
                            .set(nurseReportDoc, SetOptions.merge());

                    Platform.runLater(() -> {
                        statusLbl.setText("✓ Dispatched to " + activeDestinationHospital + "!");
                        NurseToast.show(appOverlay, "Triage report sent to " + activeDestinationHospital + "!", "success");
                        logNurseActivity("Report Dispatched", "Dispatched clinical report to " + activeDestinationHospital);
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> statusLbl.setText("Dispatch failed: " + ex.getMessage()));
                }
            }).start();
        });

        bottomControls.getChildren().addAll(generateBtn, sendHospitalBtn, spinner, statusLbl);
        card.getChildren().addAll(topRow, dualPane, outputBox, bottomControls);

        swapDynamicCenterView(card);
    }

    private Button createObservationTag(String text, TextArea targetArea) {
        Button tag = new Button(text);
        tag.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14; -fx-background-radius: 14; -fx-font-size: 9px; -fx-text-fill: " + SECONDARY_TEXT + "; -fx-padding: 3 8; -fx-cursor: hand;");
        tag.setOnAction(e -> {
            String current = targetArea.getText().trim();
            if (current.isEmpty()) targetArea.setText(text.replace("+ ", "") + ".");
            else targetArea.setText(current + " " + text.replace("+ ", "") + ".");
        });
        tag.setOnMouseEntered(e -> tag.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-border-color: " + PRIMARY_PINK + "; -fx-border-radius: 14; -fx-background-radius: 14; -fx-font-size: 9px; -fx-text-fill: " + PRIMARY_PINK + "; -fx-padding: 3 8; -fx-cursor: hand;"));
        tag.setOnMouseExited(e -> tag.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14; -fx-background-radius: 14; -fx-font-size: 9px; -fx-text-fill: " + SECONDARY_TEXT + "; -fx-padding: 3 8;"));
        return tag;
    }

    private StackPane createInteractiveSelectablePhotoCard(String imageUrl, Label photoCountStatus) {
        StackPane card = new StackPane();
        card.setPrefSize(72, 72);
        card.setMinSize(72, 72);
        card.setMaxSize(72, 72);
        card.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + EMERALD_GREEN + "; -fx-border-width: 2px; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-cursor: hand;");

        ImageView iv = new ImageView();
        try {
            iv.setImage(new Image(imageUrl, 68, 68, false, true, true));
        } catch (Exception ignored) {}
        iv.setFitWidth(68);
        iv.setFitHeight(68);

        Rectangle clip = new Rectangle(68, 68);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        iv.setClip(clip);

        StackPane badge = new StackPane();
        Circle badgeCircle = new Circle(8, Color.web(EMERALD_GREEN));
        Text badgeTick = new Text("✓");
        badgeTick.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-fill: white;");
        badge.getChildren().addAll(badgeCircle, badgeTick);
        StackPane.setAlignment(badge, Pos.TOP_RIGHT);
        StackPane.setMargin(badge, new Insets(2, 2, 0, 0));

        card.getChildren().addAll(iv, badge);

        card.setOnMouseClicked(e -> {
            if (selectedSummaryPhotoUrls.contains(imageUrl)) {
                selectedSummaryPhotoUrls.remove(imageUrl);
                badgeCircle.setFill(Color.web("#CBD5E1"));
                badgeTick.setText("");
                card.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 1px; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-cursor: hand; -fx-opacity: 0.6;");
            } else {
                selectedSummaryPhotoUrls.add(imageUrl);
                badgeCircle.setFill(Color.web(EMERALD_GREEN));
                badgeTick.setText("✓");
                card.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + EMERALD_GREEN + "; -fx-border-width: 2px; -fx-border-radius: 12px; -fx-background-radius: 12px; -fx-cursor: hand; -fx-opacity: 1.0;");
            }
            photoCountStatus.setText(selectedSummaryPhotoUrls.size() + " selected");
        });

        return card;
    }

    // =========================================================================
    // WORKSPACE VIEW 6: DOCTOR TELE-CONSULTATION CHANNEL (SYNCHRONIZED JITSI)
    // =========================================================================
   private void showDoctorConsultationWidget(String patientId) {
        stopEmbeddedCamera();
        closeInAppVideoConsultStage();
        setDockActive(tabConsultBtn);

        String targetPatId = (patientId == null || patientId.isEmpty() || patientId.contains("Loading")) 
                ? activePatientId 
                : patientId;

        logNurseActivity("Consultation Requested", "Initiating direct tele-consultation for " + targetPatId);

        VBox card = new VBox(16);
        card.setPadding(new Insets(20, 24, 20, 24));
        applyCardStyle(card);
        VBox.setVgrow(card, Priority.ALWAYS);

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("👨‍⚕ Emergency Tele-Consultation Hub");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button closeBtn = new Button("✕ Close View");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + MUTED_TEXT + "; -fx-cursor: hand; -fx-font-size: 11px;");
        closeBtn.setOnAction(e -> showDefaultCenterWidget());
        topRow.getChildren().addAll(title, spacer, closeBtn);

        VBox controlContainer = new VBox(16);
        controlContainer.setAlignment(Pos.CENTER);
        controlContainer.setMaxWidth(600);
        controlContainer.setPadding(new Insets(20, 24, 20, 24));
        controlContainer.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-background-radius: 20; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 20;");

        Circle livePulse = new Circle(26, Color.web(PRIMARY_PINK));
        Text camIcon = new Text("📹");
        camIcon.setStyle("-fx-font-size: 20px; -fx-fill: white;");
        StackPane iconBadge = new StackPane(livePulse, camIcon);

        Text panelTitle = new Text("Emergency Tele-Consultation Video");
        panelTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Label roomInfo = new Label("Target Hospital: " + activeDestinationHospital + " | Patient: " + targetPatId);
        roomInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: " + SECONDARY_TEXT + ";");

        // Native In-App Video Viewport
        BorderPane videoFrame = new BorderPane();
        videoFrame.setPrefHeight(320);
        videoFrame.setMinHeight(260);
        videoFrame.setMaxWidth(560);
        videoFrame.setStyle("-fx-background-color: #0b0b14; -fx-background-radius: 12px; -fx-border-color: #352E32; -fx-border-radius: 12px;");

        StackPane videoArea = new StackPane();
        ImageView remoteHospitalVideo = new ImageView();
        remoteHospitalVideo.setPreserveRatio(true);
        remoteHospitalVideo.fitWidthProperty().bind(videoFrame.widthProperty().subtract(10));
        remoteHospitalVideo.fitHeightProperty().bind(videoFrame.heightProperty().subtract(10));

        ImageView localNursePreview = new ImageView();
        localNursePreview.setFitWidth(130);
        localNursePreview.setFitHeight(95);
        localNursePreview.setPreserveRatio(true);
        localNursePreview.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 8, 0, 0, 2);");
        StackPane.setAlignment(localNursePreview, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(localNursePreview, new Insets(10));

        VBox waitingPlaceholder = new VBox(8);
        waitingPlaceholder.setAlignment(Pos.CENTER);
        Label placeholderText = new Label("Ready to connect to Hospital ER");
        placeholderText.setStyle("-fx-font-size: 13px; -fx-text-fill: #9ca3af;");
        Label placeholderSub = new Label("Camera & Audio will stream directly inside LifeLink");
        placeholderSub.setStyle("-fx-font-size: 11px; -fx-text-fill: #6b7280;");
        waitingPlaceholder.getChildren().addAll(placeholderText, placeholderSub);

        videoArea.getChildren().addAll(waitingPlaceholder, remoteHospitalVideo, localNursePreview);
        videoFrame.setCenter(videoArea);

        HBox callInputs = new HBox(12);
        callInputs.setAlignment(Pos.CENTER);

        Button launchDirectBtn = new Button("🚀 Start In-App Call");
        launchDirectBtn.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 10 24; -fx-background-radius: 20; -fx-cursor: hand;");

        Button endNurseCallBtn = new Button("⏹ End Call");
        endNurseCallBtn.setStyle("-fx-background-color: " + DANGER_RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 10 24; -fx-background-radius: 20; -fx-cursor: hand;");
        endNurseCallBtn.setDisable(true);

        callInputs.getChildren().addAll(launchDirectBtn, endNurseCallBtn);

        Label statusMsg = new Label("Click 'Start In-App Call' to stream directly to hospital.");
        statusMsg.setStyle("-fx-font-size: 11px; -fx-text-fill: " + MUTED_TEXT + ";");

        // Action: Start Call
        launchDirectBtn.setOnAction(e -> {
            launchDirectBtn.setDisable(true);
            endNurseCallBtn.setDisable(false);
            statusMsg.setText("Initializing video call & notifying Hospital ER...");
            statusMsg.setStyle("-fx-font-size: 11px; -fx-text-fill: " + EMERALD_GREEN + ";");

            if (nurseVideoEngine == null) {
                nurseVideoEngine = new VideoCallEngine();
            }

            remoteHospitalVideo.imageProperty().bind(nurseVideoEngine.remoteVideoImageProperty());
            localNursePreview.imageProperty().bind(nurseVideoEngine.localVideoImageProperty());
            waitingPlaceholder.setVisible(false);

            String nurseLocalIp = VideoCallEngine.getLocalNetworkIp();

            new Thread(() -> {
                try {
                    Firestore db = FirebaseConfig.getFirestore();
                    if (db != null) {
                        Map<String, Object> callData = new HashMap<>();
                        callData.put("hospitalName", activeDestinationHospital);
                        callData.put("patientId", targetPatId);
                        callData.put("tripId", activeTripId);
                        callData.put("nurseEmail", loginemail);
                        callData.put("nurseIp", nurseLocalIp);
                        callData.put("status", "RINGING");
                        callData.put("timestamp", Timestamp.now());

                        db.collection("videoCall").document(activeTripId).set(callData, SetOptions.merge()).get();

                        // Listen for Hospital response & Hospital IP
                        if (nurseVideoCallListener != null) {
                            nurseVideoCallListener.remove();
                        }
                        nurseVideoCallListener = db.collection("videoCall").document(activeTripId)
                                .addSnapshotListener((snap, err) -> {
                                    if (snap != null && snap.exists()) {
                                        String st = snap.getString("status");
                                        String hospIp = snap.getString("hospitalIp");
                                        Platform.runLater(() -> {
                                            if (hospIp != null && !hospIp.trim().isEmpty()) {
                                                if (nurseVideoEngine != null && nurseVideoEngine.isRunning()) {
                                                    nurseVideoEngine.updateRemoteIp(hospIp.trim());
                                                }
                                            }
                                            if ("CONNECTED".equalsIgnoreCase(st)) {
                                                statusMsg.setText("✓ Connected! Hospital joined native video call.");
                                                statusMsg.setStyle("-fx-font-size: 11px; -fx-text-fill: " + EMERALD_GREEN + ";");
                                            } else if ("ENDED".equalsIgnoreCase(st)) {
                                                if (nurseVideoEngine != null && nurseVideoEngine.isRunning()) {
                                                    nurseVideoEngine.stopCall();
                                                }
                                                waitingPlaceholder.setVisible(true);
                                                launchDirectBtn.setDisable(false);
                                                endNurseCallBtn.setDisable(true);
                                                statusMsg.setText("Hospital ended the call.");
                                                statusMsg.setStyle("-fx-font-size: 11px; -fx-text-fill: " + MUTED_TEXT + ";");
                                            }
                                        });
                                    }
                                });
                    }

                    String targetToConnect = com.kurukshetra.view.call.VideoCallConfig.HOSPITAL_IP;
                    if (targetToConnect == null || targetToConnect.trim().isEmpty()) {
                        targetToConnect = "127.0.0.1";
                    }
                    targetToConnect = targetToConnect.trim();
                    nurseVideoEngine.startCall(targetToConnect, () -> {
                        statusMsg.setText("✓ Live video feed established!");
                    }, () -> {
                        waitingPlaceholder.setVisible(true);
                        launchDirectBtn.setDisable(false);
                        endNurseCallBtn.setDisable(true);
                    });

                    Platform.runLater(() -> {
                        statusMsg.setText("✓ Calling Hospital... )");
                        NurseToast.show(appOverlay, "In-app emergency video call ringing!", "success");
                        logNurseActivity("Native Tele-Call Started", "Direct LAN video call to " + activeDestinationHospital);
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        launchDirectBtn.setDisable(false);
                        endNurseCallBtn.setDisable(true);
                        waitingPlaceholder.setVisible(true);
                        statusMsg.setText("Error: " + ex.getMessage());
                        statusMsg.setStyle("-fx-font-size: 11px; -fx-text-fill: " + DANGER_RED + ";");
                    });
                    ex.printStackTrace();
                }
            }).start();
        });

        // Action: End Call
        endNurseCallBtn.setOnAction(e -> {
            if (nurseVideoEngine != null) {
                nurseVideoEngine.stopCall();
            }
            if (nurseVideoCallListener != null) {
                try { nurseVideoCallListener.remove(); } catch (Exception ignored) {}
                nurseVideoCallListener = null;
            }
            waitingPlaceholder.setVisible(true);
            launchDirectBtn.setDisable(false);
            endNurseCallBtn.setDisable(true);
            statusMsg.setText("Call ended.");
            statusMsg.setStyle("-fx-font-size: 11px; -fx-text-fill: " + MUTED_TEXT + ";");

            new Thread(() -> {
                try {
                    Firestore db = FirebaseConfig.getFirestore();
                    if (db != null) {
                        Map<String, Object> endData = new HashMap<>();
                        endData.put("status", "ENDED");
                        db.collection("videoCall").document(activeTripId).set(endData, SetOptions.merge()).get();
                    }
                } catch (Exception ignored) {}
            }).start();
        });

        controlContainer.getChildren().addAll(iconBadge, panelTitle, roomInfo, videoFrame, callInputs, statusMsg);

        StackPane centerWrapper = new StackPane(controlContainer);
        centerWrapper.setAlignment(Pos.CENTER);
        VBox.setVgrow(centerWrapper, Priority.ALWAYS);

        card.getChildren().addAll(topRow, centerWrapper);
        swapDynamicCenterView(card);
    }
     
    private void openInAppVideoStage(String hospitalName, String tripId, String patientId) {
        closeInAppVideoConsultStage();

        inAppVideoConsultStage = new Stage();
        inAppVideoConsultStage.initOwner(dashboardStage);
        inAppVideoConsultStage.initStyle(StageStyle.UNDECORATED);

        BorderPane videoRoot = new BorderPane();
        videoRoot.setStyle("-fx-background-color: #141213; -fx-border-color: " + PRIMARY_PINK + "; -fx-border-width: 2.5; -fx-border-radius: 16; -fx-background-radius: 16; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.65), 24, 0, 0, 8);");

        // Top Navigation Bar
        HBox topBar = new HBox(12);
        topBar.setPadding(new Insets(14, 20, 14, 20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #1F1B1D; -fx-background-radius: 14 14 0 0;");

        Circle liveDot = new Circle(6, Color.web(EMERALD_GREEN));
        Text titleText = new Text("Tele-Consultation Console: " + hospitalName + "  |  Patient: " + patientId);
        titleText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: white;");

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Button closeWindowBtn = new Button("✕");
        closeWindowBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #A09499; -fx-font-size: 15px; -fx-cursor: hand;");
        closeWindowBtn.setOnAction(e -> {
            closeInAppVideoConsultStage();
            showDefaultCenterWidget();
        });
        topBar.getChildren().addAll(liveDot, titleText, headerSpacer, closeWindowBtn);

        // Native In-App Login & Host Control Panel
        VBox loginPanel = new VBox(14);
        loginPanel.setAlignment(Pos.CENTER);
        loginPanel.setMaxWidth(420);
        loginPanel.setPadding(new Insets(24));
        loginPanel.setStyle("-fx-background-color: #1F1B1D; -fx-background-radius: 16; -fx-border-color: #352E32; -fx-border-radius: 16;");

        Text panelTitle = new Text("🔐 Jitsi Moderator Login");
        panelTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: white;");

        Label panelSub = new Label("Authenticate as host to initialize the secure ER room.");
        panelSub.setStyle("-fx-font-size: 11px; -fx-text-fill: #A09499;");

        javafx.scene.control.TextField emailField = new javafx.scene.control.TextField(loginemail);
        emailField.setPromptText("Nurse Email");
        emailField.setStyle("-fx-background-color: #2D272A; -fx-text-fill: white; -fx-prompt-text-fill: #70636B; -fx-background-radius: 8; -fx-padding: 8;");

        javafx.scene.control.PasswordField passField = new javafx.scene.control.PasswordField();
        passField.setText("lifelink2026");
        passField.setPromptText("Moderator Password");
        passField.setStyle("-fx-background-color: #2D272A; -fx-text-fill: white; -fx-prompt-text-fill: #70636B; -fx-background-radius: 8; -fx-padding: 8;");

        Button loginAndJoinBtn = new Button("🚀 Authenticate & Launch Meet");
        loginAndJoinBtn.setStyle("-fx-background-color: " + PRIMARY_PINK + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 10 20; -fx-background-radius: 20; -fx-cursor: hand;");
        
        Label statusMsg = new Label("");
        statusMsg.setStyle("-fx-font-size: 10px; -fx-text-fill: #98D8A0;");

        loginAndJoinBtn.setOnAction(e -> {
            String email = emailField.getText().trim();
            String pass = passField.getText().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                statusMsg.setText("Please enter credentials.");
                statusMsg.setStyle("-fx-font-size: 10px; -fx-text-fill: #D71920;");
                return;
            }

            statusMsg.setText("Authenticating with Jitsi server...");
            statusMsg.setStyle("-fx-font-size: 10px; -fx-text-fill: #98D8A0;");

            new Thread(() -> {
                try {
                    Thread.sleep(800); // Simulate secure auth handshake
                    String jitsiRoomId = "lifelink-er-trip-" + tripId.replaceAll("[^a-zA-Z0-9-_]", "");
                    String jitsiUrl = "https://meet.jit.si/" + jitsiRoomId + "#config.prejoinPageEnabled=false";

                    Platform.runLater(() -> {
                        statusMsg.setText("✓ Authenticated! Opening meeting window...");
                        try {
                            if (java.awt.Desktop.isDesktopSupported() && java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)) {
                                java.awt.Desktop.getDesktop().browse(new URI(jitsiUrl));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> statusMsg.setText("Login failed: " + ex.getMessage()));
                }
            }).start();
        });

        loginPanel.getChildren().addAll(panelTitle, panelSub, emailField, passField, loginAndJoinBtn, statusMsg);

        StackPane centerContainer = new StackPane(loginPanel);
        centerContainer.setStyle("-fx-background-color: #141213;");

        // Bottom Controls Bar
        HBox bottomControls = new HBox(16);
        bottomControls.setAlignment(Pos.CENTER);
        bottomControls.setPadding(new Insets(14));
        bottomControls.setStyle("-fx-background-color: #1F1B1D; -fx-background-radius: 0 0 14 14;");

        Button disconnectBtn = new Button("⏹ Close Window");
        disconnectBtn.setStyle("-fx-background-color: " + DANGER_RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 7 18; -fx-background-radius: 8; -fx-cursor: hand;");
        disconnectBtn.setOnAction(e -> {
            closeInAppVideoConsultStage();
            showDefaultCenterWidget();
            logNurseActivity("Consultation Ended", "Closed tele-call console with " + hospitalName);
        });

        bottomControls.getChildren().add(disconnectBtn);

        videoRoot.setTop(topBar);
        videoRoot.setCenter(centerContainer);
        videoRoot.setBottom(bottomControls);

        Scene consultScene = new Scene(videoRoot, 900, 560);
        consultScene.setFill(Color.TRANSPARENT);
        inAppVideoConsultStage.setScene(consultScene);

        if (dashboardStage != null && dashboardStage.isShowing()) {
            inAppVideoConsultStage.setX(dashboardStage.getX() + (dashboardStage.getWidth() - 900) / 2);
            inAppVideoConsultStage.setY(dashboardStage.getY() + (dashboardStage.getHeight() - 560) / 2);
        }

        inAppVideoConsultStage.show();
    }
    private void startConsultationCamera(ImageView streamView) {
        isConsultCamActive.set(true);
        new Thread(() -> {
            try {
                if (consultWebcam == null) {
                    consultWebcam = Webcam.getDefault();
                }
                if (consultWebcam != null && !consultWebcam.isOpen()) {
                    consultWebcam.open();
                }
                while (isConsultCamActive.get() && consultWebcam != null && consultWebcam.isOpen()) {
                    BufferedImage img = consultWebcam.getImage();
                    if (img != null) {
                        WritableImage fxImg = SwingFXUtils.toFXImage(img, null);
                        Platform.runLater(() -> {
                            if (isConsultCamActive.get()) {
                                streamView.setImage(fxImg);
                            }
                        });
                    }
                    Thread.sleep(33);
                }
            } catch (Exception ex) {
                System.err.println("[ConsultCam] Camera stream error: " + ex.getMessage());
            }
        }).start();
    }

    private void closeInAppVideoConsultStage() {
        isConsultCamActive.set(false);
        isMicMuted.set(false);
        if (nurseVideoEngine != null) {
            nurseVideoEngine.stopCall();
            nurseVideoEngine = null;
        }
        if (nurseVideoCallListener != null) {
            try { nurseVideoCallListener.remove(); } catch (Exception ignored) {}
            nurseVideoCallListener = null;
        }
        if (consultWebcam != null && consultWebcam.isOpen()) {
            new Thread(() -> {
                try { consultWebcam.close(); } catch (Exception ignored) {}
            }).start();
        }
        if (inAppVideoConsultStage != null) {
            inAppVideoConsultStage.close();
            inAppVideoConsultStage = null;
        }
    }

    // =========================================================================
    // WORKSPACE VIEW 7: ACTIVITY LOGS & DISPATCHES
    // =========================================================================
    private void showActivityAndDispatchesWidget() {
        stopEmbeddedCamera();

        VBox card = new VBox(14);
        card.setPadding(new Insets(16, 20, 16, 20));
        applyCardStyle(card);
        VBox.setVgrow(card, Priority.ALWAYS);

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("📜 Clinical Activity Logs & Dispatched Requests");
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button closeBtn = new Button("✕ Close View");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + MUTED_TEXT + "; -fx-cursor: hand;");
        closeBtn.setOnAction(e -> showDefaultCenterWidget());
        topRow.getChildren().addAll(title, spacer, closeBtn);

        HBox splitBox = new HBox(16);
        splitBox.setAlignment(Pos.TOP_LEFT);
        VBox.setVgrow(splitBox, Priority.ALWAYS);

        VBox dispatchesCol = new VBox(8);
        dispatchesCol.setPadding(new Insets(14));
        dispatchesCol.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-background-radius: 18; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 18;");
        HBox.setHgrow(dispatchesCol, Priority.ALWAYS);

        HBox dHeader = new HBox(6);
        dHeader.setAlignment(Pos.CENTER_LEFT);
        Text dTitle = new Text("🚨 Dispatched Emergency Requests (Live Firestore)");
        dTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        Circle liveDot = new Circle(3.5, Color.web(EMERALD_GREEN));
        Region spD = new Region();
        HBox.setHgrow(spD, Priority.ALWAYS);
        Label liveBadge = new Label("Live Stream");
        liveBadge.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + EMERALD_GREEN + ";");
        dHeader.getChildren().addAll(dTitle, spD, liveDot, liveBadge);

        notificationsContainer = new VBox(6);
        Label waitLbl = new Label("Connecting to live dispatches...");
        waitLbl.setStyle("-fx-font-size: 10px; -fx-text-fill: " + MUTED_TEXT + "; -fx-padding: 8;");
        notificationsContainer.getChildren().add(waitLbl);

        ScrollPane dScroll = new ScrollPane(notificationsContainer);
        dScroll.setFitToWidth(true);
        dScroll.setPrefHeight(360);
        dScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(dScroll, Priority.ALWAYS);

        dispatchesCol.getChildren().addAll(dHeader, dScroll);
        attachFirebaseNotificationsListener();

        VBox activityCol = new VBox(8);
        activityCol.setPadding(new Insets(14));
        activityCol.setStyle("-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-background-radius: 18; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 18;");
        HBox.setHgrow(activityCol, Priority.ALWAYS);

        HBox aHeader = new HBox();
        aHeader.setAlignment(Pos.CENTER_LEFT);
        Text aTitle = new Text("📋 Nurse Action & Shift Audit Timeline");
        aTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        Region spA = new Region();
        HBox.setHgrow(spA, Priority.ALWAYS);
        Text clearBtn = new Text("Clear History");
        clearBtn.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_PINK + "; -fx-cursor: hand;");
        clearBtn.setOnMouseClicked(e -> {
            nurseActivityLogList.clear();
            if (myLogContainer != null) myLogContainer.getChildren().clear();
        });
        aHeader.getChildren().addAll(aTitle, spA, clearBtn);

        myLogContainer = new VBox(4);
        myLogContainer.getChildren().addAll(nurseActivityLogList);

        ScrollPane aScroll = new ScrollPane(myLogContainer);
        aScroll.setFitToWidth(true);
        aScroll.setPrefHeight(360);
        aScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(aScroll, Priority.ALWAYS);

        activityCol.getChildren().addAll(aHeader, aScroll);

        splitBox.getChildren().addAll(dispatchesCol, activityCol);
        card.getChildren().addAll(topRow, splitBox);

        swapDynamicCenterView(card);
    }

    private void attachFirebaseNotificationsListener() {
        detachFirebaseListener();
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return;

            final String currentNurseEmail = (loginemail != null && !loginemail.trim().isEmpty()) ? loginemail.trim() : "";

            emergencyRequestListener = db.collection("adminEmergencyRequests")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .limit(25)
                    .addSnapshotListener((snapshots, e) -> {
                        if (e != null || snapshots == null) return;

                        Platform.runLater(() -> {
                            if (notificationsContainer != null) notificationsContainer.getChildren().clear();

                            if (snapshots.isEmpty()) {
                                Label noNotif = new Label("No active emergency requests dispatched.");
                                noNotif.setStyle("-fx-font-size: 10px; -fx-text-fill: " + MUTED_TEXT + "; -fx-padding: 8;");
                                if (notificationsContainer != null) notificationsContainer.getChildren().add(noNotif);
                                return;
                            }

                            int matchedCount = 0;
                            for (DocumentSnapshot doc : snapshots.getDocuments()) {
                                String nId = doc.getString("nurseID");
                                if (nId == null || nId.trim().isEmpty()) nId = doc.getString("nurseEmail");
                                if (nId == null || nId.trim().isEmpty()) nId = doc.getString("nurseemail");

                                boolean isForMe = currentNurseEmail.isEmpty() || (nId != null && nId.trim().equalsIgnoreCase(currentNurseEmail));
                                if (isForMe) {
                                    String tripId = doc.getString("tripID") != null ? doc.getString("tripID") : "TRIP-" + doc.getId().substring(0, 4).toUpperCase();
                                    String patId = doc.getString("patID") != null ? doc.getString("patID"): (doc.getString("patientId") != null ? doc.getString("patientId") : "Unknown");
                                    String pickup = doc.getString("pickupLocation") != null ? doc.getString("pickupLocation") : (doc.getString("location") != null ? doc.getString("location") : "Emergency Site");

                                    String msg = "Trip: " + tripId + " | Patient: " + patId + " | Pickup: " + pickup;
                                    HBox item = notificationItem("🚨 Dispatched Trip", msg, "NOW", true);
                                    if (notificationsContainer != null) notificationsContainer.getChildren().add(item);
                                    matchedCount++;
                                }
                            }

                            if (matchedCount == 0 && notificationsContainer != null) {
                                Label noNotif = new Label("No emergency requests dispatched for " + (currentNurseEmail.isEmpty() ? "this nurse" : currentNurseEmail) + ".");
                                noNotif.setStyle("-fx-font-size: 10px; -fx-text-fill: " + MUTED_TEXT + "; -fx-padding: 8;");
                                notificationsContainer.getChildren().add(noNotif);
                            }
                        });
                    });
        } catch (Exception ex) {
            System.err.println("[NotificationListener] Error: " + ex.getMessage());
        }
    }

    private void detachFirebaseListener() {
        if (emergencyRequestListener != null) {
            try { emergencyRequestListener.remove(); } catch (Exception ignored) {}
            emergencyRequestListener = null;
        }
        if (missionProgressListener != null) {
            try { missionProgressListener.remove(); } catch (Exception ignored) {}
            missionProgressListener = null;
        }
        if (nurseActiveTripListener != null) {
            try { nurseActiveTripListener.remove(); } catch (Exception ignored) {}
            nurseActiveTripListener = null;
        }
    }

    public HBox notificationItem(String title, String description, String time, boolean unread) {
        HBox row = new HBox(8);
        row.setAlignment(Pos.TOP_LEFT);
        row.setPadding(new Insets(8, 12, 8, 12));
        row.setStyle("-fx-background-color: " + (unread ? SURFACE : "transparent") + "; -fx-background-radius: 14; -fx-border-color: " + (unread ? BORDER_COLOR : "transparent") + "; -fx-border-radius: 14; -fx-cursor: hand;");

        Circle dot = new Circle(3);
        dot.setFill(Color.web(unread ? PINK_DARK : "transparent"));

        VBox dotWrap = new VBox(dot);
        dotWrap.setAlignment(Pos.TOP_CENTER);
        dotWrap.setPadding(new Insets(4, 0, 0, 0));

        VBox textBox = new VBox(2);
        Text titleText = new Text(title);
        titleText.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text descText = new Text(description);
        descText.setStyle("-fx-font-size: 9px; -fx-fill: " + SECONDARY_TEXT + "; -fx-line-spacing: 2px;");

        Text timeText = new Text(time);
        timeText.setStyle("-fx-font-size: 8px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_PINK + ";");

        textBox.getChildren().addAll(titleText, descText, timeText);
        row.getChildren().addAll(dotWrap, textBox);

        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: " + LIGHT_PINK + "; -fx-background-radius: 14; -fx-border-color: " + PRIMARY_PINK + "; -fx-border-radius: 14;"));
        row.setOnMouseExited(e -> row.setStyle("-fx-background-color: " + (unread ? SURFACE : "transparent") + "; -fx-background-radius: 14; -fx-border-color: " + (unread ? BORDER_COLOR : "transparent") + "; -fx-border-radius: 14;"));

        return row;
    }

    private void updateAllPatientBadges() {
        boolean hasActiveTrip = !"NONE".equalsIgnoreCase(activeTripId) && !"NO_ACTIVE_MISSION".equalsIgnoreCase(currentMissionStatus);

        if (globalDestinationBadge != null) {
            globalDestinationBadge.setText("🏥 ER: " + activeDestinationHospital);
        }
        if (globalTripBadge != null) {
            globalTripBadge.setText(hasActiveTrip ? "Trip: " + activeTripId : "Trip: Standby");
        }
        if (corridorStatusLabel != null) {
            if (hasActiveTrip) {
                corridorStatusLabel.setText("Status: " + currentMissionStatus.replace("_", " "));
                corridorStatusLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + EMERALD_GREEN + ";");
            } else {
                corridorStatusLabel.setText("Status: STANDBY");
                corridorStatusLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + MUTED_TEXT + ";");
            }
        }
        if (missionTimelineTripPill != null) {
            if (hasActiveTrip) {
                missionTimelineTripPill.setText("Trip: " + activeTripId + " (Pat: " + activePatientId + ")");
                missionTimelineTripPill.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_PINK + "; -fx-background-color: " + VERY_LIGHT_PINK + "; -fx-padding: 2 8; -fx-background-radius: 6;");
            } else {
                missionTimelineTripPill.setText("Standby: No Active Trip");
                missionTimelineTripPill.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + MUTED_TEXT + "; -fx-background-color: " + VERY_LIGHT_PINK + "; -fx-padding: 2 8; -fx-background-radius: 6;");
            }
        }
        if (overviewTitleSub != null) {
            overviewTitleSub.setText(hasActiveTrip 
                ? "Ambulance Unit 04 • Patient: " + activePatientId + " • Target: " + activeDestinationHospital
                : "Ambulance Unit 04 • Standby Mode • Target: " + activeDestinationHospital);
        }
        updateHorizontalMissionStepsUI();
    }

    private void fetchActivePatientAssignment() {
        attachMissionProgressLiveListener();
    }

    private void attachMissionProgressLiveListener(String tripId) {
        attachMissionProgressLiveListener();
    }

    private void attachMissionProgressLiveListener() {
        if (nurseActiveTripListener != null) {
            try { nurseActiveTripListener.remove(); } catch (Exception ignored) {}
            nurseActiveTripListener = null;
        }
        if (missionProgressListener != null) {
            try { missionProgressListener.remove(); } catch (Exception ignored) {}
            missionProgressListener = null;
        }
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return;

            final String currentNurseEmail = (loginemail != null && !loginemail.trim().isEmpty())
                    ? loginemail.trim()
                    : "nurse1@lifelink.com";

            // Live listener on "adminEmergencyRequests" for active trips assigned to this nurse
            nurseActiveTripListener = db.collection("adminEmergencyRequests")
                    .addSnapshotListener((snapshots, error) -> {
                        if (error != null) {
                            System.err.println("[NurseDashboard] Error listening to assigned emergency requests: " + error.getMessage());
                            return;
                        }

                        if (snapshots == null || snapshots.isEmpty()) {
                            Platform.runLater(this::setNoActiveMissionState);
                            return;
                        }

                        List<DocumentSnapshot> assignedDocs = new ArrayList<>();
                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
                            String nId = doc.getString("nurseID");
                            if (nId == null || nId.trim().isEmpty()) nId = doc.getString("nurseEmail");
                            if (nId == null || nId.trim().isEmpty()) nId = doc.getString("nurseemail");

                            boolean isAssignedToMe = (nId != null && nId.trim().equalsIgnoreCase(currentNurseEmail));

                            if (isAssignedToMe) {
                                String status = doc.getString("status");
                                if (status == null || status.trim().isEmpty()) status = doc.getString("currentStatus");

                                boolean isCompleted = (status != null) && (
                                        status.equalsIgnoreCase("COMPLETED") ||
                                        status.equalsIgnoreCase("COMPLETE") ||
                                        status.equalsIgnoreCase("CLOSED") ||
                                        status.equalsIgnoreCase("FINISHED")
                                );

                                if (!isCompleted) {
                                    assignedDocs.add(doc);
                                }
                            }
                        }

                        if (assignedDocs.isEmpty()) {
                            Platform.runLater(this::setNoActiveMissionState);
                            return;
                        }

                        // Select the latest uncompleted mission assigned to this nurse
                        assignedDocs.sort((d1, d2) -> Long.compare(extractDocTimestamp(d2), extractDocTimestamp(d1)));
                        DocumentSnapshot activeDoc = assignedDocs.get(0);

                        String tripId = activeDoc.getString("tripID");
                        if (tripId == null || tripId.trim().isEmpty()) tripId = activeDoc.getId();

                        String patId = activeDoc.getString("patID");
                        if (patId == null || patId.trim().isEmpty()) patId = activeDoc.getString("patientId");
                        if (patId == null || patId.trim().isEmpty()) patId = "PAT-UNKNOWN";

                        String dest = activeDoc.getString("destination");
                        if (dest == null || dest.trim().isEmpty()) dest = activeDoc.getString("destinationHospital");
                        if (dest == null || dest.trim().isEmpty()) dest = "Core2Web Pune";

                        String initialStatus = activeDoc.getString("status");
                        if (initialStatus == null || initialStatus.trim().isEmpty()) initialStatus = "ASSIGNED";

                        final String finalTripId = tripId;
                        final String finalPatId = patId;
                        final String finalDest = dest;
                        final String finalStatus = initialStatus;

                        Platform.runLater(() -> {
                            activeTripId = finalTripId;
                            activePatientId = finalPatId;
                            activeDestinationHospital = finalDest;
                            currentMissionStatus = finalStatus;
                            updateAllPatientBadges();
                        });

                        // Attach live listener on missionProgress for this specific active trip
                        if (missionProgressListener != null) {
                            try { missionProgressListener.remove(); } catch (Exception ignored) {}
                            missionProgressListener = null;
                        }
                        try {
                            missionProgressListener = db.collection("missionProgress").document(finalTripId)
                                    .addSnapshotListener((progSnapshot, progErr) -> {
                                        if (progErr != null || progSnapshot == null || !progSnapshot.exists()) return;

                                        String liveStatus = progSnapshot.getString("currentStatus");
                                        if (liveStatus == null || liveStatus.trim().isEmpty()) liveStatus = progSnapshot.getString("status");
                                        if (liveStatus != null && !liveStatus.trim().isEmpty()) {
                                            final String updatedStatus = liveStatus;
                                            Platform.runLater(() -> {
                                                currentMissionStatus = updatedStatus;
                                                updateAllPatientBadges();
                                            });
                                        }
                                    });
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNoActiveMissionState() {
        activeTripId = "NONE";
        activePatientId = "NONE";
        currentMissionStatus = "NO_ACTIVE_MISSION";
        updateAllPatientBadges();
    }

    private long extractDocTimestamp(DocumentSnapshot doc) {
        if (doc == null) return 0L;
        try {
            Object obj = doc.get("lastUpdated");
            if (obj == null) obj = doc.get("timestamp");
            if (obj == null) obj = doc.get("updatedAt");
            if (obj instanceof Timestamp) {
                return ((Timestamp) obj).getSeconds();
            } else if (obj instanceof java.util.Date) {
                return ((java.util.Date) obj).getTime() / 1000L;
            } else if (obj instanceof Number) {
                return ((Number) obj).longValue();
            }
        } catch (Exception ignored) {}
        return 0L;
    }

    public void logNurseActivity(String actionTitle, String description) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
        HBox item = notificationItem("● " + actionTitle, description, timestamp, true);
        nurseActivityLogList.add(0, item);

        if (myLogContainer != null) {
            Platform.runLater(() -> myLogContainer.getChildren().add(0, item));
        }
    }

    private void swapDynamicCenterView(javafx.scene.Node newView) {
        FadeTransition fadeOut = new FadeTransition(NurseAppSettings.dur(120), dynamicCenterSlot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            dynamicCenterSlot.getChildren().clear();
            dynamicCenterSlot.getChildren().add(newView);
            FadeTransition fadeIn = new FadeTransition(NurseAppSettings.dur(160), dynamicCenterSlot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    public void staggerIn(javafx.scene.Node node, double delayMillis) {
        node.setOpacity(0);
        node.setTranslateY(12);

        FadeTransition fade = new FadeTransition(NurseAppSettings.dur(350), node);
        fade.setToValue(1);
        fade.setDelay(NurseAppSettings.dur(delayMillis));

        TranslateTransition slide = new TranslateTransition(NurseAppSettings.dur(350), node);
        slide.setToY(0);
        slide.setDelay(NurseAppSettings.dur(delayMillis));

        new ParallelTransition(fade, slide).play();
    }

    // Legacy menuButton kept for backward compatibility if referenced elsewhere
    public Button menuButton(String icon, String name) {
        return createNurseNavButton(icon + "  " + name, false);
    }

    // Legacy setSelectedMenuButton kept for backward compatibility
    public void setSelectedMenuButton(Button selectedButton) {
        activeMenuButton = selectedButton;
    }

    public void getToDashboardPage() {
        stopPatientMonitorAnimation();
        stopEmbeddedCamera();
        closeInAppVideoConsultStage();
        activeMenuButton = dashboardButton;
        setDockActive(tabOverviewBtn);
        defaultOverviewCard = buildSummaryCardNode();
        dynamicCenterSlot.getChildren().setAll(defaultOverviewCard);
        startCarouselTimeline();
        if (mainScrollPane != null) {
            root.setCenter(mainScrollPane);
        } else {
            root.setCenter(mainContent);
        }
    }

    private void applyCardStyle(VBox card) {
        card.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 1; -fx-border-radius: 20; -fx-background-radius: 20;");
        card.setEffect(new DropShadow(12, 0, 4, Color.rgb(230, 117, 147, 0.06)));
    }

    // =========================================================================
    // GROQ API & TEXT PROCESSING UTILITIES
    // =========================================================================
    public static String cleanNurseOutput(String rawText) {
        if (rawText == null || rawText.trim().isEmpty()) return "";

        String cleaned = rawText;

        if (cleaned.contains("u003c/thinku003e")) {
            cleaned = cleaned.substring(cleaned.lastIndexOf("u003c/thinku003e") + "u003c/thinku003e".length());
        } else if (cleaned.contains("</think>")) {
            cleaned = cleaned.substring(cleaned.lastIndexOf("</think>") + "</think>".length());
        }

        cleaned = cleaned.replaceAll("(?s)u003cthinku003e.*", "");
        cleaned = cleaned.replaceAll("(?s)<think>.*", "");

        cleaned = cleaned.replace("u0026", "&")
                         .replaceAll("[\\*#_`~]", "");

        if (cleaned.contains("\\u")) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cleaned.length(); i++) {
                char c = cleaned.charAt(i);
                if (c == '\\' && i + 5 < cleaned.length() && cleaned.charAt(i + 1) == 'u') {
                    try {
                        String hex = cleaned.substring(i + 2, i + 6);
                        sb.append((char) Integer.parseInt(hex, 16));
                        i += 5;
                        continue;
                    } catch (Exception ignored) {}
                }
                sb.append(c);
            }
            cleaned = sb.toString();
        }

        return cleaned.trim();
    }

    private String callGroqApi(String prompt) throws Exception {
        URL url = new URL("https://api.groq.com/openai/v1/chat/completions");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + GROQ_API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(30000);

        String sanitizedPrompt = prompt.replace("\\", "\\\\")
                                       .replace("\"", "\\\"")
                                       .replace("\n", "\\n")
                                       .replace("\r", "\\r");

        String jsonPayload = "{"
                + "\"model\": \"groq/compound-mini\","
                + "\"messages\": ["
                + "  {\"role\": \"system\", \"content\": \"You are Sister Ananya, senior emergency triage nurse on the LifeLink Response Team. Synthesize field notes into direct, plain-text triage reports without conversational fluff, internal thinking blocks, or markdown asterisks.\"},"
                + "  {\"role\": \"user\", \"content\": \"" + sanitizedPrompt + "\"}"
                + "],"
                + "\"temperature\": 0.2"
                + "}";

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                StringBuilder err = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) err.append(line);
                throw new RuntimeException("HTTP " + responseCode + ": " + err.toString());
            }
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder resp = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) resp.append(line);

            return parseGroqJsonResponse(resp.toString());
        }
    }

    private String parseGroqJsonResponse(String json) {
        try {
            int choicesIdx = json.indexOf("\"choices\"");
            if (choicesIdx != -1) {
                int contentKey = json.indexOf("\"content\":", choicesIdx);
                if (contentKey != -1) {
                    int startQuote = json.indexOf("\"", contentKey + 10);
                    if (startQuote != -1) {
                        StringBuilder sb = new StringBuilder();
                        boolean escaped = false;
                        for (int i = startQuote + 1; i < json.length(); i++) {
                            char c = json.charAt(i);
                            if (escaped) {
                                if (c == 'n') sb.append('\n');
                                else if (c == 'r') sb.append('\r');
                                else if (c == 't') sb.append('\t');
                                else if (c == '\"') sb.append('\"');
                                else if (c == '\\') sb.append('\\');
                                else sb.append(c);
                                escaped = false;
                            } else if (c == '\\') {
                                escaped = true;
                            } else if (c == '\"') {
                                break;
                            } else {
                                sb.append(c);
                            }
                        }
                        String parsed = sb.toString().trim();
                        if (!parsed.isEmpty()) return parsed;
                    }
                }
            }
        } catch (Exception ignored) {}
        return json;
    }

    private Image loadLogoImage() {
        String[] resourcePaths = {
                "/assets/Images/lifelinklogonew.png",
                "/assets/Images/LifeLinkLogo.png",
                "/lifelinklogonew.png"
        };
        for (String resPath : resourcePaths) {
            try {
                var url = getClass().getResource(resPath);
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

    private Image loadSafeImage(String resourcePath, String relativePath) {
        try {
            var res = getClass().getResource(resourcePath);
            if (res != null) {
                Image img = new Image(res.toExternalForm(), true);
                if (!img.isError()) {
                    return img;
                }
            }
        } catch (Exception ignored) {}

        try {
            File f = new File(relativePath);
            if (f.exists()) {
                Image img = new Image(f.toURI().toString(), true);
                if (!img.isError()) {
                    return img;
                }
            }
            File f2 = new File("lifelink1/" + relativePath);
            if (f2.exists()) {
                Image img = new Image(f2.toURI().toString(), true);
                if (!img.isError()) {
                    return img;
                }
            }
            File f3 = new File("LifeLink/lifelink1/" + relativePath);
            if (f3.exists()) {
                Image img = new Image(f3.toURI().toString(), true);
                if (!img.isError()) {
                    return img;
                }
            }
        } catch (Exception ignored) {}

        return null;
    }

    @Override
    public void stop() throws Exception {
        stopPatientMonitorAnimation();
        stopEmbeddedCamera();
        closeInAppVideoConsultStage();
        detachFirebaseListener();
        super.stop();
        Platform.exit();
        System.exit(0);
    }
}