package com.kurukshetra.view.hospital;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.ListenerRegistration;
import com.google.cloud.firestore.Firestore;
import com.google.api.core.ApiFuture;
import java.util.concurrent.Executors;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker;

import com.kurukshetra.controller.hospitalController.HospitalResourceController;
import com.kurukshetra.model.hospitalModel.HospitalResourceModel;
import com.kurukshetra.controller.hospitalController.OperationTheatreController;
import com.kurukshetra.model.hospitalModel.OperationTheatreModel;
import com.kurukshetra.view.Welcome;
import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.controller.hospitalController.DoctorController;
import com.kurukshetra.controller.hospitalController.DriverHospitalNotificationController;
import com.kurukshetra.model.hospitalModel.DoctorModel;
import com.kurukshetra.model.hospitalModel.DriverHospitalNotificationModel;
import com.kurukshetra.view.util.ShimmerLoader;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.SetOptions;

import javafx.application.Platform;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.awt.Desktop;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import javafx.concurrent.Worker;
import org.json.JSONArray;
import org.json.JSONObject;

public class HospitalDashboard extends Application {

    // =========================================================
    // COLOR VARIABLES (GREEN THEME)
    // =========================================================
    // Sidebar Background: #051E1B / #072420 (Gradient)
    // Active Nav: bg #FFFFFF, text #064E3B, border rgba(94, 234, 212, 0.65)
    // Inactive Nav: bg transparent, text #CCFBF1
    // Hover Nav: bg rgba(45, 212, 191, 0.22), text #FFFFFF
    // Logout Button: bg rgba(150, 25, 35, 0.60), text #FFE4E6
    // Content Background: #F8FAFC
    // Primary Text: #0F172A
    // Secondary Text: #64748B
    // Borders: #E2E8F0
    // PAGE_BG : #a5bdaaff
    // =========================================================

    public static String hospitalEmail = "ojas@gmail.com";
    public static String hospitalName = "KEM Hospital Pune";
    private static Text hospitalTextRef;

    public static void fetchHospitalNameFromFirebase(String email) {
        if (email == null || email.trim().isEmpty())
            return;
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null)
                return;
            String cleanEmail = email.trim();

            // 1. Try direct document lookup by email
            DocumentSnapshot doc = db.collection("hospital").document(cleanEmail).get().get();
            if (doc != null && doc.exists()) {
                String name = doc.getString("hospitalName");
                if (name == null || name.trim().isEmpty())
                    name = doc.getString("name");
                if (name != null && !name.trim().isEmpty()) {
                    hospitalName = name.trim();
                    System.out.println("[HospitalDashboard] Fetched Hospital Name from Doc ID (" + cleanEmail + "): "
                            + hospitalName);
                    updateHospitalUI();
                    return;
                }
            }

            // 2. Query where email == cleanEmail
            QuerySnapshot qSnap = db.collection("hospital").whereEqualTo("email", cleanEmail).get().get();
            if (qSnap != null && !qSnap.isEmpty()) {
                DocumentSnapshot d = qSnap.getDocuments().get(0);
                String name = d.getString("hospitalName");
                if (name == null || name.trim().isEmpty())
                    name = d.getString("name");
                if (name != null && !name.trim().isEmpty()) {
                    hospitalName = name.trim();
                    System.out.println("[HospitalDashboard] Fetched Hospital Name where email=" + cleanEmail + ": "
                            + hospitalName);
                    updateHospitalUI();
                    return;
                }
            }

            // 3. Query where hospitalEmail == cleanEmail
            QuerySnapshot qSnap2 = db.collection("hospital").whereEqualTo("hospitalEmail", cleanEmail).get().get();
            if (qSnap2 != null && !qSnap2.isEmpty()) {
                DocumentSnapshot d = qSnap2.getDocuments().get(0);
                String name = d.getString("hospitalName");
                if (name == null || name.trim().isEmpty())
                    name = d.getString("name");
                if (name != null && !name.trim().isEmpty()) {
                    hospitalName = name.trim();
                    System.out.println("[HospitalDashboard] Fetched Hospital Name where hospitalEmail=" + cleanEmail
                            + ": " + hospitalName);
                    updateHospitalUI();
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("[HospitalDashboard] Error fetching hospital name from Firebase: " + e.getMessage());
        }
    }

    private static void updateHospitalUI() {
        Platform.runLater(() -> {
            if (hospitalTextRef != null) {
                hospitalTextRef.setText(hospitalName);
            }
            if (dashboardStage != null) {
                dashboardStage.setTitle("LifeLink Hospital Dashboard (" + hospitalName + ")");
            }
        });
    }

    public HospitalDashboard(String hospitalEmail) {
        if (hospitalEmail != null && !hospitalEmail.trim().isEmpty()) {
            HospitalDashboard.hospitalEmail = hospitalEmail.trim();
            new Thread(() -> fetchHospitalNameFromFirebase(hospitalEmail.trim())).start();
        }
    }

    public HospitalDashboard() {
    }

    public static Stage dashboardStage;
    private Scene dashboardScene;

    private boolean mapReady = false;

    private String pendingPickupLocation = null;
    private String pendingHospitalAddress = null;

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

    private WebEngine ambulanceMapEngine;
    private boolean isMapLoaded = false;
    private Double activeInboundDestLat = null;
    private Double activeInboundDestLng = null;
    private String activeInboundHospName = null;

    private double pendingAmbulanceLat;
    private double pendingAmbulanceLon;
    private String pendingHospitalName;
    private boolean pendingAmbulanceRequest = false;

    private ListenerRegistration driToHosPopupListener;
    private final Set<String> handledDriToHosPopupDocIds = new HashSet<>();
    private static StackPane rootStack;
    private static VBox popupOverlayContainer;
    private static String pendingDriToHosDocId = null;
    private static String pendingDriToHosAmbId = null;
    private static String pendingDriToHosPatId = null;

    private String selectedPatientId = "PAT-2004";
    private String selectedTripId = "MH16-104";
    private String selectedAmbulanceId = "AMB-104";
    private String selectedDriverEmail = "";
    private String selectedMessage = "";
    private String selectedStatus = "INCOMING";
    private VBox selectedAmbulanceCardNode = null;

    private static final String CARD_STYLE_DEFAULT = "-fx-background-color: #F8FAFC; " +
            "-fx-border-color: #E2E8F0; " +
            "-fx-border-width: 1.5px; " +
            "-fx-border-radius: 12px; " +
            "-fx-background-radius: 12px; " +
            "-fx-cursor: hand;";

    private static final String CARD_STYLE_HOVER = "-fx-background-color: #F1F5F9; " +
            "-fx-border-color: #CBD5E1; " +
            "-fx-border-width: 1.5px; " +
            "-fx-border-radius: 12px; " +
            "-fx-background-radius: 12px; " +
            "-fx-cursor: hand;";

    private static final String CARD_STYLE_SELECTED = "-fx-background-color: #EFF6FF; " +
            "-fx-border-color: #006591; " +
            "-fx-border-width: 2px; " +
            "-fx-border-radius: 12px; " +
            "-fx-background-radius: 12px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,101,145,0.18), 10, 0, 0, 2); " +
            "-fx-cursor: hand;";

    // =========================================================================
    // HOSPITAL GREEN THEME SIDEBAR CONSTANTS & HELPERS (MATCHING IMAGE SPEC)
    // =========================================================================
    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";

    // Green Theme Sidebar Constants matching hospitalDashboardbackground.png
    private static final String SIDEBAR_NAV_ACTIVE = FONT_FAMILY +
            "-fx-background-color: #FFFFFF; " +
            "-fx-text-fill: #064E3B; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: 800; " +
            "-fx-background-radius: 11px; " +
            "-fx-alignment: center-left; " +
            "-fx-padding: 10px 16px; " +
            "-fx-border-color: rgba(94, 234, 212, 0.65); " +
            "-fx-border-radius: 11px; " +
            "-fx-border-width: 1px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(16, 185, 129, 0.45), 12, 0, 0, 3); " +
            "-fx-cursor: hand;";

    private static final String SIDEBAR_NAV_INACTIVE = FONT_FAMILY +
            "-fx-background-color: transparent; " +
            "-fx-text-fill: #CCFBF1; " +
            "-fx-font-size: 15px; " +
            "-fx-font-weight: 500; " +
            "-fx-background-radius: 11px; " +
            "-fx-alignment: center-left; " +
            "-fx-padding: 10px 16px; " +
            "-fx-border-color: transparent; " +
            "-fx-border-radius: 11px; " +
            "-fx-border-width: 1px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.35), 4, 0, 0, 1); " +
            "-fx-cursor: hand;";

    private static final String SIDEBAR_NAV_HOVER = FONT_FAMILY +
            "-fx-background-color: rgba(45, 212, 191, 0.22); " +
            "-fx-text-fill: #FFFFFF; " +
            "-fx-font-size: 15px; " +
            "-fx-font-weight: 600; " +
            "-fx-background-radius: 11px; " +
            "-fx-alignment: center-left; " +
            "-fx-padding: 10px 16px; " +
            "-fx-border-color: rgba(94, 234, 212, 0.50); " +
            "-fx-border-radius: 11px; " +
            "-fx-border-width: 1px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(16, 185, 129, 0.30), 8, 0, 0, 2); " +
            "-fx-cursor: hand;";

    private static Button currentActiveNavButton;

    private static void applyNavHover(Button btn) {
        btn.setOnMouseEntered(e -> {
            if (btn != currentActiveNavButton && !btn.getStyle().contains("#064E3B")) {
                btn.setStyle(SIDEBAR_NAV_HOVER);
                btn.setTranslateX(4);
            }
        });
        btn.setOnMouseExited(e -> {
            if (btn != currentActiveNavButton && !btn.getStyle().contains("#064E3B")) {
                btn.setStyle(SIDEBAR_NAV_INACTIVE);
                btn.setTranslateX(0);
            }
        });
    }

    private static void setActiveNav(Button activeBtn, Button... otherButtons) {
        currentActiveNavButton = activeBtn;
        activeBtn.setStyle(SIDEBAR_NAV_ACTIVE);
        activeBtn.setTranslateX(0);
        for (Button btn : otherButtons) {
            btn.setStyle(SIDEBAR_NAV_INACTIVE);
            btn.setTranslateX(0);
        }
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
        } catch (Exception ignored) {
        }

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
        } catch (Exception ignored) {
        }

        return null;
    }

    private static VBox createTrendingMetricCard(
            String iconEmoji, String iconBgColor, String iconTextColor,
            String title, Text valueNode, String badgeText, String badgeBg, String badgeTextColor) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(16));
        card.setPrefHeight(132);
        card.setStyle(
                "-fx-background-color: #FFFFFF; " +
                        "-fx-border-color: #E2E8F0; " +
                        "-fx-border-radius: 14px; " +
                        "-fx-background-radius: 14px; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 14, 0, 0, 3);");
        HBox.setHgrow(card, Priority.ALWAYS);

        card.setOnMouseEntered(e -> {
            card.setTranslateY(-4);
            card.setStyle(
                    "-fx-background-color: #FFFFFF; " +
                            "-fx-border-color: #CBD5E1; " +
                            "-fx-border-radius: 14px; " +
                            "-fx-background-radius: 14px; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.09), 18, 0, 0, 6);");
        });
        card.setOnMouseExited(e -> {
            card.setTranslateY(0);
            card.setStyle(
                    "-fx-background-color: #FFFFFF; " +
                            "-fx-border-color: #E2E8F0; " +
                            "-fx-border-radius: 14px; " +
                            "-fx-background-radius: 14px; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 14, 0, 0, 3);");
        });

        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconPane = new StackPane();
        iconPane.setPrefSize(34, 34);
        iconPane.setMinSize(34, 34);
        iconPane.setStyle(
                "-fx-background-color: " + iconBgColor + "; " +
                        "-fx-background-radius: 8px;");
        Text iconText = new Text(iconEmoji);
        iconText.setStyle("-fx-font-size: 15px; -fx-fill: " + iconTextColor + ";");
        iconPane.getChildren().add(iconText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label badge = new Label(badgeText);
        badge.setStyle(
                "-fx-background-color: " + badgeBg + "; " +
                        "-fx-text-fill: " + badgeTextColor + "; " +
                        "-fx-font-size: 9.5px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 3px 8px; " +
                        "-fx-background-radius: 6px; " +
                        FONT_FAMILY);
        topRow.getChildren().addAll(iconPane, spacer, badge);

        valueNode.setStyle(FONT_FAMILY + "-fx-font-size: 28px; -fx-font-weight: 800; -fx-fill: #0F172A;");

        Text labelText = new Text(title);
        labelText.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: 600; -fx-fill: #64748B;");

        card.getChildren().addAll(topRow, valueNode, labelText);
        return card;
    }

    @Override
    public void start(Stage stage) throws Exception {

        dashboardStage = stage;

        HospitalResourceController resourceController = new HospitalResourceController();

        String safeHospitalEmail = (hospitalEmail != null && !hospitalEmail.trim().isEmpty())
                ? hospitalEmail.trim()
                : "hospital1@lifelink.com";
        HospitalDashboard.hospitalEmail = safeHospitalEmail;

        // Fetch hospital name from Firebase using login email
        fetchHospitalNameFromFirebase(safeHospitalEmail);

        HospitalResourceModel resource = resourceController.getHospitalResource(safeHospitalEmail);

        int totalICUBeds = 0;
        int availableICUBeds = 0;
        int totalEmergencyBeds = 0;
        int availableEmergencyBeds = 0;

        if (resource != null) {
            totalICUBeds = resource.getTotalICUBeds();
            availableICUBeds = resource.getAvailableICUBeds();
            totalEmergencyBeds = resource.getTotalEmergencyBeds();
            availableEmergencyBeds = resource.getAvailableEmergencyBeds();
        }

        System.out.println("Hospital Email: " + safeHospitalEmail);
        System.out.println("Available ICU Beds: " + availableICUBeds);
        System.out.println("Available Emergency Beds: " + availableEmergencyBeds);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #a5bdaaff;");

        // =============================================================
        // LEFT MENU - HOSPITAL OPERATIONS SIDEBAR (WITH GREEN THEME BACKGROUND)
        // =============================================================

        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(20, 14, 20, 14));
        leftMenu.setPrefWidth(260);
        leftMenu.setMinWidth(260);
        leftMenu.setMaxWidth(260);
        leftMenu.setStyle("-fx-background-color: transparent;");

        // Hospital Brand Header
        HBox brandHeader = new HBox(12);
        brandHeader.setAlignment(Pos.CENTER_LEFT);
        brandHeader.setPadding(new Insets(4, 8, 14, 8));

        StackPane brandIconPane = new StackPane();
        brandIconPane.setPrefSize(38, 38);
        brandIconPane.setMinSize(38, 38);
        brandIconPane.setMaxSize(38, 38);
        brandIconPane.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #10B981, #0D9488); " +
                        "-fx-background-radius: 10px; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(16, 185, 129, 0.45), 10, 0, 0, 2);");
        Text brandIcon = new Text("+");
        brandIcon.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white;");
        brandIconPane.getChildren().add(brandIcon);

        VBox brandTextBox = new VBox(1);
        Text lifeLinkText = new Text("LifeLink");
        lifeLinkText.setStyle(FONT_FAMILY
                + "-fx-font-size: 18px; -fx-font-weight: 800; -fx-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 6, 0, 0, 1);");

        Text hospitalText = new Text(hospitalName);
        hospitalText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: 600; -fx-fill: #99F6E4;");
        hospitalTextRef = hospitalText;
        brandTextBox.getChildren().addAll(lifeLinkText, hospitalText);
        brandHeader.getChildren().addAll(brandIconPane, brandTextBox);

        VBox brandBox = new VBox(10, brandHeader);
        brandBox.setPadding(new Insets(0, 0, 10, 0));

        // Category 1: CLINICAL OPERATIONS
        Button dashboardButton = new Button("Dashboard");
        dashboardButton.setMaxWidth(Double.MAX_VALUE);
        dashboardButton.setPrefHeight(40);
        dashboardButton.setStyle(SIDEBAR_NAV_ACTIVE);
        currentActiveNavButton = dashboardButton;

        Button patientMonitoringButton = new Button("Patient Live Monitoring");
        patientMonitoringButton.setMaxWidth(Double.MAX_VALUE);
        patientMonitoringButton.setPrefHeight(40);
        patientMonitoringButton.setStyle(SIDEBAR_NAV_INACTIVE);

        Button patientButton = new Button("Patient Details");
        patientButton.setMaxWidth(Double.MAX_VALUE);
        patientButton.setPrefHeight(40);
        patientButton.setStyle(SIDEBAR_NAV_INACTIVE);

        Button resourceButton = new Button("Resource Management");
        resourceButton.setMaxWidth(Double.MAX_VALUE);
        resourceButton.setPrefHeight(40);
        resourceButton.setStyle(SIDEBAR_NAV_INACTIVE);

        Button doctorButton = new Button("Doctor Management");
        doctorButton.setMaxWidth(Double.MAX_VALUE);
        doctorButton.setPrefHeight(40);
        doctorButton.setStyle(SIDEBAR_NAV_INACTIVE);

        Button complaintButton = new Button("Complaints");
        complaintButton.setMaxWidth(Double.MAX_VALUE);
        complaintButton.setPrefHeight(40);
        complaintButton.setStyle(SIDEBAR_NAV_INACTIVE);

        Button settingsButton = new Button("Profile Settings");
        settingsButton.setMaxWidth(Double.MAX_VALUE);
        settingsButton.setPrefHeight(40);
        settingsButton.setStyle(SIDEBAR_NAV_INACTIVE);

        Button[] navButtons = { dashboardButton, patientMonitoringButton, resourceButton, doctorButton, patientButton,
                complaintButton, settingsButton };
        for (Button btn : navButtons) {
            applyNavHover(btn);
        }

        Region menuSpacer = new Region();
        VBox.setVgrow(menuSpacer, Priority.ALWAYS);

        // User Info Box
        HBox userInfoBox = new HBox(12);
        userInfoBox.setAlignment(Pos.CENTER_LEFT);
        userInfoBox.setPadding(new Insets(12));
        userInfoBox.setStyle(
                "-fx-background-color: rgba(10, 60, 25, 0.52); " +
                        "-fx-border-color: rgba(255, 255, 255, 0.22); " +
                        "-fx-border-radius: 12px; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.3), 8, 0, 0, 2);");

        StackPane avatarPane = new StackPane();
        avatarPane.setPrefSize(40, 40);
        avatarPane.setMinSize(40, 40);
        avatarPane.setStyle("-fx-background-color: #328831; -fx-background-radius: 50%;");
        Text avatarInitials = new Text("O"); // Hospital or initials
        avatarInitials.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: #FFFFFF;");
        avatarPane.getChildren().add(avatarInitials);

        VBox userDetailsBox = new VBox(2);
        Text userEmailText = new Text(safeHospitalEmail);
        userEmailText.setStyle(
                "-fx-font-family: 'Segoe UI'; -fx-font-size: 12.5px; -fx-font-weight: bold; -fx-fill: #FFFFFF;");
        Text userRoleText = new Text("Hospital Administrator");
        userRoleText.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 11px; -fx-fill: #94A3B8;");
        userDetailsBox.getChildren().addAll(userEmailText, userRoleText);

        userInfoBox.getChildren().addAll(avatarPane, userDetailsBox);

        Button logoutButton = new Button("Log Out Session");
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setPrefHeight(40);
        logoutButton.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: rgba(23, 145, 60, 0.6); " +
                        "-fx-text-fill: #FFE4E6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 11px; " +
                        "-fx-border-color: rgba(206, 254, 205, 0.35); " +
                        "-fx-border-radius: 11px; " +
                        "-fx-border-width: 1px; " +
                        "-fx-cursor: hand;");
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: rgba(23, 145, 60, 0.6);; " +
                        "-fx-text-fill: #FFFFFF; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 11px; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.45); " +
                        "-fx-border-radius: 11px; " +
                        "-fx-border-width: 1px; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(29, 225, 62, 0.5), 12, 0, 0, 3);"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: rgba(25, 150, 100, 0.6); " +
                        "-fx-text-fill: #FFE4E6; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 11px; " +
                        "-fx-border-color: rgba(205, 254, 232, 0.35); " +
                        "-fx-border-radius: 11px; " +
                        "-fx-border-width: 1px; " +
                        "-fx-cursor: hand;"));

        Text hospitalNavText = new Text("HOSPITAL");
        hospitalNavText.setStyle(FONT_FAMILY
                + "-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: #5EEAD4;-fx-letter-spacing: 0.6px; -fx-padding: 6 0 4 8;");
        // navLabel.setStyle(FONT_FAMILY + "-fx-font-size: 20px; -fx-font-weight: bold;
        // -fx-text-fill: " + SIDEBAR_TEXT_MUTED + "; -fx-letter-spacing: 0.6px;
        // -fx-padding: 6 0 4 8;");

        VBox.setMargin(hospitalNavText, new Insets(10, 0, 5, 10));

        leftMenu.getChildren().addAll(
                brandBox,
                hospitalNavText,
                dashboardButton, patientMonitoringButton, patientButton,
                resourceButton, doctorButton,
                complaintButton, settingsButton,
                menuSpacer,
                userInfoBox,
                logoutButton);

        // ---- Background image layer wrapped in StackPane ----
        StackPane sidebarStack = new StackPane();
        sidebarStack.setPrefWidth(260);
        sidebarStack.setMinWidth(260);
        sidebarStack.setMaxWidth(260);
        sidebarStack.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(4, 28, 24, 0.35), 18, 0, 4, 0);");

        // Background image
        Image sidebarBg = loadSafeImage("/assets/Images/hospitalDashboardbackground.png",
                "src/main/resources/assets/Images/hospitalDashboardbackground.png");
        if (sidebarBg != null && !sidebarBg.isError()) {
            ImageView bgImageView = new ImageView(sidebarBg);
            bgImageView.setPreserveRatio(false);
            bgImageView.setSmooth(true);
            bgImageView.fitWidthProperty().bind(sidebarStack.widthProperty());
            bgImageView.fitHeightProperty().bind(sidebarStack.heightProperty());
            sidebarStack.getChildren().add(bgImageView);
        } else {
            Pane fallbackBg = new Pane();
            fallbackBg.setStyle("-fx-background-color: linear-gradient(to bottom, #0A2E2A, #0D3E38, #07221F);");
            sidebarStack.getChildren().add(fallbackBg);
        }

        // Dark gradient overlay for text readability preserving artwork vibrancy
        Pane gradientOverlay = new Pane();
        gradientOverlay.setStyle(
                "-fx-background-color: linear-gradient(" +
                        "to bottom, " +
                        "rgba(5, 30, 27, 0.40) 0%, " +
                        "rgba(7, 36, 32, 0.20) 35%, " +
                        "rgba(4, 22, 20, 0.55) 100%" +
                        ");");

        // Right edge separator glow
        Pane edgeGlow = new Pane();
        edgeGlow.setStyle(
                "-fx-background-color: linear-gradient(" +
                        "to bottom, " +
                        "rgba(94, 234, 212, 0.35), " +
                        "rgba(45, 212, 191, 0.15), " +
                        "transparent" +
                        "); " +
                        "-fx-max-width: 2px; " +
                        "-fx-pref-width: 2px;");
        StackPane.setAlignment(edgeGlow, Pos.CENTER_RIGHT);

        sidebarStack.getChildren().addAll(gradientOverlay, leftMenu, edgeGlow);

        borderPane.setLeft(sidebarStack);

        // =============================================================
        // MAIN CONTENT - OPERATIONS DASHBOARD
        // =============================================================

        VBox mainContent = new VBox(14);
        mainContent.setPadding(new Insets(16, 22, 16, 22));
        mainContent.setStyle("-fx-background-color: #a5bdaaff;");
        mainContent.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        Text pageTitle = new Text("Emergency Operations Center");
        pageTitle.setStyle(FONT_FAMILY + "-fx-font-size: 24px; -fx-font-weight: 800; -fx-fill: #0F172A;");

        VBox titleBox = new VBox(3);
        titleBox.getChildren().addAll(pageTitle);

        Region pageSpacer = new Region();
        HBox.setHgrow(pageSpacer, Priority.ALWAYS);

        HBox pageHeader = new HBox(12);
        pageHeader.getChildren().addAll(titleBox, pageSpacer);
        pageHeader.setAlignment(Pos.CENTER_LEFT);
        pageHeader.setMaxWidth(Double.MAX_VALUE);

        // =============================================================
        // SUMMARY CARDS - TRENDING MEDTECH KPI METRICS
        // =============================================================

        HBox summaryCards = new HBox(14);
        summaryCards.setMaxWidth(Double.MAX_VALUE);

        Text icuValue = new Text(String.valueOf(availableICUBeds));
        VBox icuCard = createTrendingMetricCard("🏥", "#EFF6FF", "#0284C7", "Available ICU Beds", icuValue, "92% CAP",
                "#D1FAE5", "#065F46");

        Text emergencyBedsValue = new Text(String.valueOf(availableEmergencyBeds));
        VBox emergencyBedsCard = createTrendingMetricCard("🚨", "#FFF1F2", "#EF4444", "Emergency Beds",
                emergencyBedsValue, "CRITICAL", "#FEE2E2", "#991B1B");

        // Realtime Resource listener code
        resourceController.listenToHospitalResource(
                safeHospitalEmail,
                updatedResource -> {
                    if (updatedResource != null) {
                        Platform.runLater(() -> {
                            icuValue.setText(String.valueOf(updatedResource.getAvailableICUBeds()));
                            emergencyBedsValue.setText(String.valueOf(updatedResource.getAvailableEmergencyBeds()));
                        });
                    }
                });

        Text doctorsValue = new Text("0");
        VBox doctorsCard = createTrendingMetricCard("👨‍⚕️", "#ECFDF5", "#10B981", "Active Physicians", doctorsValue,
                "ON DUTY", "#D1FAE5", "#065F46");

        DoctorController doctorController = new DoctorController();
        List<DoctorModel> doctors = doctorController.getDoctors(safeHospitalEmail);

        int activeDoctorCount = 0;
        if (doctors != null) {
            for (DoctorModel doctor : doctors) {
                if (doctor != null && "AVAILABLE".equalsIgnoreCase(doctor.getStatus())) {
                    activeDoctorCount++;
                }
            }
        }
        doctorsValue.setText(String.valueOf(activeDoctorCount));

        // LIVE DOCTOR COUNT LISTENER
        doctorController.listenToDoctors(
                safeHospitalEmail,
                updatedDoctors -> {
                    Platform.runLater(() -> {
                        int availableDoctorCount = 0;
                        if (updatedDoctors != null) {
                            for (DoctorModel doctor : updatedDoctors) {
                                if (doctor != null && "AVAILABLE".equalsIgnoreCase(doctor.getStatus())) {
                                    availableDoctorCount++;
                                }
                            }
                        }
                        doctorsValue.setText(String.valueOf(availableDoctorCount));
                    });
                });

        Text otValue = new Text("0");
        VBox otCard = createTrendingMetricCard("🔬", "#EEF2FF", "#6366F1", "Operating Theatres", otValue, "STERILIZED",
                "#E0E7FF", "#3730A3");

        OperationTheatreController operationTheatreController = new OperationTheatreController();
        String[] otIds = { "OT-01", "OT-02", "OT-03" };

        int availableOTCount = 0;
        for (String otId : otIds) {
            OperationTheatreModel ot = operationTheatreController.getOperationTheatre(safeHospitalEmail, otId);
            if (ot == null || !"RESERVED".equalsIgnoreCase(ot.getStatus())) {
                availableOTCount++;
            }
        }
        otValue.setText(String.valueOf(availableOTCount));

        for (String otId : otIds) {
            operationTheatreController.listenToOperationTheatre(
                    safeHospitalEmail,
                    otId,
                    updatedOT -> {
                        Platform.runLater(() -> {
                            int availableCount = 0;
                            for (String id : otIds) {
                                OperationTheatreModel currentOT = operationTheatreController
                                        .getOperationTheatre(safeHospitalEmail, id);
                                if (currentOT == null || !"RESERVED".equalsIgnoreCase(currentOT.getStatus())) {
                                    availableCount++;
                                }
                            }
                            otValue.setText(String.valueOf(availableCount));
                        });
                    });
        }

        Text incomingValue = new Text("08");
        VBox incomingCard = createTrendingMetricCard("⚡", "#FFF1F2", "#F43F5E", "Emergency Alerts", incomingValue,
                "PRIORITY 1", "#FFE4E6", "#9F1239");

        summaryCards.getChildren().addAll(icuCard, emergencyBedsCard, doctorsCard, otCard, incomingCard);

        // =============================================================
        // INCOMING AMBULANCE ALERT + INCOMING AMBULANCES
        // =============================================================

        HBox upperContent = new HBox(24);
        upperContent.setMaxWidth(Double.MAX_VALUE);
        upperContent.setPrefHeight(0);
        upperContent.setMinHeight(0);
        VBox.setVgrow(upperContent, Priority.ALWAYS);

        VBox incomingAlertCard = new VBox(15);
        incomingAlertCard.setPadding(new Insets(20));
        incomingAlertCard.setPrefHeight(0);
        incomingAlertCard.setMinHeight(0);
        incomingAlertCard.setMinWidth(600);
        incomingAlertCard.setStyle(
                "-fx-background-color: #FFFFFF; " +
                        "-fx-border-color: #E2E8F0; " +
                        "-fx-border-radius: 16px; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 16, 0, 0, 4);");
        HBox.setHgrow(incomingAlertCard, Priority.ALWAYS);

        Text incomingAlertTitle = new Text("Incoming Ambulance Live Radar & Triage");
        incomingAlertTitle.setStyle(FONT_FAMILY + "-fx-font-size: 16.5px; -fx-font-weight: bold; -fx-fill: #0F172A;");

        Label alertStatus = new Label("●  LIVE RADAR STREAM");
        alertStatus.setStyle(
                "-fx-background-color: #ECFDF5; " +
                        "-fx-text-fill: #065F46; " +
                        "-fx-border-color: #A7F3D0; " +
                        "-fx-border-radius: 12px; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-font-size: 10px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 3px 9px; " +
                        FONT_FAMILY);

        HBox alertHeader = new HBox(10);
        Region alertHeaderSpacer = new Region();
        HBox.setHgrow(alertHeaderSpacer, Priority.ALWAYS);
        alertHeader.getChildren().addAll(incomingAlertTitle, alertHeaderSpacer, alertStatus);

        StackPane ambulanceMapPlaceholder = new StackPane();
        ambulanceMapPlaceholder.setMinHeight(280);
        ambulanceMapPlaceholder.setPrefHeight(320);
        ambulanceMapPlaceholder.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(ambulanceMapPlaceholder, Priority.ALWAYS);
        ambulanceMapPlaceholder.setStyle(
                "-fx-background-color: #F8FAFC;" +
                        "-fx-border-color: #E2E8F0;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-background-radius: 12px;");

        WebView ambulanceMapWebView = new WebView();
        ambulanceMapEngine = ambulanceMapWebView.getEngine();
        ambulanceMapWebView.setMaxWidth(Double.MAX_VALUE);
        ambulanceMapWebView.setMaxHeight(Double.MAX_VALUE);
        ambulanceMapWebView.setOpacity(0.0);

        ShimmerLoader.ShimmerPane mapShimmer = ShimmerLoader.createMapSkeleton(650, 320);
        mapShimmer.setMaxWidth(Double.MAX_VALUE);
        mapShimmer.setMaxHeight(Double.MAX_VALUE);
        ambulanceMapPlaceholder.getChildren().addAll(ambulanceMapWebView, mapShimmer);

        ambulanceMapEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                isMapLoaded = true;
                Platform.runLater(() -> {
                    FadeTransition ft = new FadeTransition(javafx.util.Duration.millis(350), ambulanceMapWebView);
                    ft.setFromValue(0.0);
                    ft.setToValue(1.0);
                    ft.play();
                    mapShimmer.stop();
                    ambulanceMapPlaceholder.getChildren().remove(mapShimmer);
                });
                if (liveAmbulanceLat != null && liveAmbulanceLat != 0.0) {
                    String targetHosp = (activeInboundHospName != null && !activeInboundHospName.isEmpty())
                            ? activeInboundHospName
                            : hospitalName;
                    if (targetHosp == null)
                        targetHosp = "Hospital";
                    String safeDest = targetHosp.replace("'", "\\'");
                    if (activeInboundDestLat != null && activeInboundDestLng != null && activeInboundDestLat != 0.0) {
                        String js = String.format(Locale.US,
                                "if (typeof setRouteWithCoordinates === 'function') { setRouteWithCoordinates(%f, %f, %f, %f, '%s'); } else { setRouteFromCurrentLocation(%f, %f, '%s'); }",
                                liveAmbulanceLat, liveAmbulanceLng, activeInboundDestLat, activeInboundDestLng,
                                safeDest,
                                liveAmbulanceLat, liveAmbulanceLng, safeDest);
                        ambulanceMapEngine.executeScript(js);
                    } else {
                        String js = String.format(Locale.US,
                                "if (typeof setRouteFromCurrentLocation === 'function') { setRouteFromCurrentLocation(%f, %f, '%s'); } else if (typeof updateAmbulance === 'function') { updateAmbulance(%f, %f); }",
                                liveAmbulanceLat, liveAmbulanceLng, safeDest, liveAmbulanceLat, liveAmbulanceLng);
                        ambulanceMapEngine.executeScript(js);
                    }
                }
            }
        });

        var mapUrl = getClass().getResource("/driver_map.html");
        if (mapUrl != null) {
            ambulanceMapEngine.load(mapUrl.toExternalForm());
        }

        VBox alertMessageBox = new VBox(8);
        alertMessageBox.setPadding(new Insets(14));
        alertMessageBox.setStyle(
                "-fx-background-color: #FFF1F2;" +
                        "-fx-border-color: #EF4444;" +
                        "-fx-border-width: 0px 0px 0px 4px;" +
                        "-fx-background-radius: 0px 12px 12px 0px;");

        HBox alertIdRow = new HBox(8);
        alertIdRow.setAlignment(Pos.CENTER_LEFT);

        Text ambulanceAlertId = new Text("AMB-104");
        ambulanceAlertId.setStyle(FONT_FAMILY + "-fx-font-size: 15px; -fx-font-weight: 800; -fx-fill: #0F172A;");

        Label emergencyAlertType = new Label("CARDIAC EMERGENCY • CRITICAL");
        emergencyAlertType.setStyle(
                "-fx-background-color: #FEE2E2; -fx-text-fill: #991B1B; -fx-font-size: 9.5px; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 6px; "
                        + FONT_FAMILY);
        alertIdRow.getChildren().addAll(ambulanceAlertId, emergencyAlertType);

        Text patientAlertName = new Text("Patient: Rahul Sharma (Age 52, Male) • Pre-admission Triage");
        patientAlertName.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: 600; -fx-fill: #334155;");

        Text driverAlertMessage = new Text(
                "Inbound to Emergency Bay 1. Patient has acute chest pain with ST-elevation. Pre-allocate ICU bed and alert cardiology team.");
        driverAlertMessage.setWrappingWidth(600);
        driverAlertMessage.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-fill: #475569;");

        Text alertTime = new Text("Telemetry connected just now • In transit via Shivaji Road corridor");
        alertTime.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-fill: #94A3B8;");

        alertMessageBox.getChildren().addAll(alertIdRow, patientAlertName, driverAlertMessage, alertTime);

        HBox alertButtons = new HBox(12);
        alertButtons.setAlignment(Pos.CENTER);

        Button confirmAlertButton = new Button("Confirm");
        confirmAlertButton.setPrefWidth(200);
        confirmAlertButton.setPrefHeight(44);
        confirmAlertButton.setStyle(
                "-fx-background-color: #10B981; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(16,185,129,0.25), 8, 0, 0, 2); " +
                        "-fx-cursor: hand; " +
                        FONT_FAMILY);
        confirmAlertButton.setOnMouseEntered(e -> confirmAlertButton.setStyle(
                "-fx-background-color: #059669; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(16,185,129,0.35), 10, 0, 0, 3); -fx-cursor: hand; "
                        + FONT_FAMILY));
        confirmAlertButton.setOnMouseExited(e -> confirmAlertButton.setStyle(
                "-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(16,185,129,0.25), 8, 0, 0, 2); -fx-cursor: hand; "
                        + FONT_FAMILY));

        confirmAlertButton.setOnAction(event -> {
            double lat = (liveAmbulanceLat != null && liveAmbulanceLat != 0.0) ? liveAmbulanceLat : pendingAmbulanceLat;
            double lng = (liveAmbulanceLng != null && liveAmbulanceLng != 0.0) ? liveAmbulanceLng : pendingAmbulanceLon;
            String targetHosp = (pendingHospitalName != null && !pendingHospitalName.isEmpty()) ? pendingHospitalName
                    : hospitalName;
            if (targetHosp == null)
                targetHosp = "Hospital";

            if (ambulanceMapEngine != null && isMapLoaded) {
                String safeDest = targetHosp.replace("'", "\\'");
                if (activeInboundDestLat != null && activeInboundDestLng != null && activeInboundDestLat != 0.0) {
                    String js = String.format(Locale.US,
                            "if (typeof setRouteWithCoordinates === 'function') { setRouteWithCoordinates(%f, %f, %f, %f, '%s'); } else { setRouteFromCurrentLocation(%f, %f, '%s'); }",
                            lat, lng, activeInboundDestLat, activeInboundDestLng, safeDest,
                            lat, lng, safeDest);
                    ambulanceMapEngine.executeScript(js);
                } else {
                    String js = String.format(Locale.US,
                            "if (typeof setRouteFromCurrentLocation === 'function') { setRouteFromCurrentLocation(%f, %f, '%s'); }",
                            lat, lng, safeDest);
                    ambulanceMapEngine.executeScript(js);
                }
            }
            pendingAmbulanceRequest = false;
        });

        Button bookResourceButton = new Button("Reserve OT");
        bookResourceButton.setPrefWidth(200);
        bookResourceButton.setPrefHeight(44);
        bookResourceButton.setStyle(
                "-fx-background-color: #006591; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,101,145,0.25), 8, 0, 0, 2); " +
                        "-fx-cursor: hand; " +
                        FONT_FAMILY);
        bookResourceButton.setOnMouseEntered(e -> bookResourceButton.setStyle(
                "-fx-background-color: #004D70; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,101,145,0.35), 10, 0, 0, 3); -fx-cursor: hand; "
                        + FONT_FAMILY));
        bookResourceButton.setOnMouseExited(e -> bookResourceButton.setStyle(
                "-fx-background-color: #006591; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,101,145,0.25), 8, 0, 0, 2); -fx-cursor: hand; "
                        + FONT_FAMILY));

        Button viewDetailsButton = new Button("Patient Details");
        viewDetailsButton.setPrefWidth(200);
        viewDetailsButton.setPrefHeight(44);
        viewDetailsButton.setStyle(
                "-fx-background-color: #FFFFFF; " +
                        "-fx-text-fill: #006591; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: #CBD5E1; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand; " +
                        FONT_FAMILY);
        viewDetailsButton.setOnMouseEntered(e -> viewDetailsButton.setStyle(
                "-fx-background-color: #F8FAFC; -fx-text-fill: #004D70; -fx-font-size: 13px; -fx-font-weight: bold; -fx-border-color: #94A3B8; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-cursor: hand; "
                        + FONT_FAMILY));
        viewDetailsButton.setOnMouseExited(e -> viewDetailsButton.setStyle(
                "-fx-background-color: #FFFFFF; -fx-text-fill: #006591; -fx-font-size: 13px; -fx-font-weight: bold; -fx-border-color: #CBD5E1; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-cursor: hand; "
                        + FONT_FAMILY));

        alertButtons.getChildren().addAll(bookResourceButton, viewDetailsButton);

        incomingAlertCard.getChildren().addAll(alertHeader, ambulanceMapPlaceholder, alertMessageBox, alertButtons);

        // =============================================================
        // DYNAMIC INCOMING AMBULANCES LIST WITH DRIVER EMAIL LOOKUP
        // =============================================================
        VBox incomingAmbulanceList = new VBox(10);
        VBox.setVgrow(incomingAmbulanceList, Priority.ALWAYS);

        ShimmerLoader.ShimmerPane inboundShimmer = ShimmerLoader.createHospitalInboundListSkeleton(380, 3);
        incomingAmbulanceList.getChildren().add(inboundShimmer);

        VBox incomingAmbulancesCard = new VBox(14);
        incomingAmbulancesCard.setPadding(new Insets(20));
        incomingAmbulancesCard.setPrefHeight(0);
        incomingAmbulancesCard.setMinHeight(0);
        incomingAmbulancesCard.setPrefWidth(420);
        incomingAmbulancesCard.setStyle(
                "-fx-background-color: #FFFFFF; " +
                        "-fx-border-color: #E2E8F0; " +
                        "-fx-border-radius: 16px; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 16, 0, 0, 4);");
        HBox.setHgrow(incomingAmbulancesCard, Priority.NEVER);

        Text incomingAmbulancesTitle = new Text("Inbound Fleet Tracker");
        incomingAmbulancesTitle
                .setStyle(FONT_FAMILY + "-fx-font-size: 16.5px; -fx-font-weight: bold; -fx-fill: #0F172A;");

        Label incomingAmbulancesCount = new Label("0 EN ROUTE");
        incomingAmbulancesCount.setStyle(
                "-fx-background-color: #EFF6FF; " +
                        "-fx-text-fill: #0284C7; " +
                        "-fx-font-size: 10px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 3px 9px; " +
                        "-fx-background-radius: 12px; " +
                        FONT_FAMILY);

        HBox ambulancesHeader = new HBox(10);
        Region ambulancesHeaderSpacer = new Region();
        HBox.setHgrow(ambulancesHeaderSpacer, Priority.ALWAYS);
        ambulancesHeader.getChildren().addAll(incomingAmbulancesTitle, ambulancesHeaderSpacer, incomingAmbulancesCount);

        ScrollPane incomingScroll = new ScrollPane(incomingAmbulanceList);
        incomingScroll.setFitToWidth(true);
        incomingScroll.setPrefHeight(420);
        incomingScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        incomingScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        incomingScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Button viewAllAmbulancesButton = new Button("View Full Emergency Fleet Schedule →");
        viewAllAmbulancesButton.setMaxWidth(Double.MAX_VALUE);
        viewAllAmbulancesButton.setPrefHeight(38);
        viewAllAmbulancesButton.setStyle(
                "-fx-background-color: #F8FAFC; " +
                        "-fx-text-fill: #006591; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: #E2E8F0; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-cursor: hand; " +
                        FONT_FAMILY);

        incomingAmbulancesCard.getChildren().addAll(ambulancesHeader, incomingScroll, viewAllAmbulancesButton);

        upperContent.getChildren().addAll(incomingAlertCard, incomingAmbulancesCard);

        mainContent.getChildren().addAll(pageHeader, summaryCards, upperContent);

        // Real-time listener for driverToHospitalNotify matching logged in hospital
        Firestore firestoreDb = FirebaseConfig.getFirestore();
        if (firestoreDb != null) {
            firestoreDb.collection("driverToHospitalNotify")
                    .addSnapshotListener((snapshots, error) -> {
                        if (error != null) {
                            System.err.println("[HospitalDashboard] Error listening to driverToHospitalNotify: "
                                    + error.getMessage());
                            return;
                        }
                        if (snapshots == null)
                            return;

                        List<DocumentSnapshot> documents = new ArrayList<>();
                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
                            String docHospEmail = doc.getString("hospitalEmail");
                            if (docHospEmail == null)
                                docHospEmail = doc.getString("hospitalemail");
                            if (docHospEmail == null)
                                docHospEmail = doc.getString("hospitalId");

                            String hName = doc.getString("hospitalName");
                            if (hName == null)
                                hName = doc.getString("hospitalAddress");

                            // Filter email-oriented for logged in hospital (with dynamic hospital name
                            // fallback)
                            boolean emailMatch = (docHospEmail != null && safeHospitalEmail != null
                                    && docHospEmail.trim().equalsIgnoreCase(safeHospitalEmail.trim()));
                            boolean nameMatch = (hName != null && hospitalName != null
                                    && hName.trim().equalsIgnoreCase(hospitalName.trim()));

                            if (emailMatch || nameMatch) {
                                documents.add(doc);
                            }
                        }

                        // Sort newest-first by Firestore timestamp
                        documents.sort((a, b) -> {
                            Timestamp ta = a.getTimestamp("timestamp");
                            Timestamp tb = b.getTimestamp("timestamp");

                            if (ta == null && tb == null)
                                return 0;
                            if (ta == null)
                                return 1;
                            if (tb == null)
                                return -1;

                            return tb.compareTo(ta);
                        });

                        Platform.runLater(() -> {
                            if (inboundShimmer != null) {
                                inboundShimmer.stop();
                            }
                            incomingAmbulanceList.getChildren().clear();
                            int matchCount = documents.size();

                            incomingAmbulancesCount.setText(matchCount + " EN ROUTE");
                            incomingValue.setText(String.format("%02d", matchCount));

                            if (documents.isEmpty()) {
                                ambulanceAlertId.setText("No Incoming Ambulance");
                                emergencyAlertType.setText("WAITING FOR REQUEST");
                                patientAlertName.setText("Patient: --");
                                driverAlertMessage.setText("No ambulance is currently coming to this hospital.");
                                alertTime.setText("Waiting for incoming request");
                                alertStatus.setText("●  NO ALERT");
                                alertStatus.setStyle(
                                        "-fx-background-color: #F1F5F9; -fx-text-fill: #64748B; -fx-border-color: #CBD5E1; "
                                                +
                                                "-fx-border-radius: 12px; -fx-background-radius: 12px; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 3px 9px; "
                                                + FONT_FAMILY);
                                pendingAmbulanceRequest = false;

                                Label emptyLbl = new Label("No incoming ambulances found for " + hospitalName);
                                emptyLbl.setStyle(FONT_FAMILY
                                        + "-fx-font-size: 12.5px; -fx-text-fill: #94A3B8; -fx-font-style: italic; -fx-padding: 16px;");
                                incomingAmbulanceList.getChildren().add(emptyLbl);
                                return;
                            }

                            for (int i = 0; i < documents.size(); i++) {
                                DocumentSnapshot doc = documents.get(i);

                                String tripId = doc.getString("tripID");
                                if (tripId == null)
                                    tripId = doc.getString("TripID");
                                if (tripId == null)
                                    tripId = doc.getString("tripId");
                                if (tripId == null)
                                    tripId = "AMB-TRIP";

                                String patId = doc.getString("patientID");
                                if (patId == null)
                                    patId = doc.getString("patID");
                                if (patId == null)
                                    patId = doc.getString("patientId");
                                if (patId == null)
                                    patId = "PAT-UNKNOWN";

                                String ambId = doc.getString("ambulanceId");
                                if (ambId == null)
                                    ambId = doc.getString("ambulanceID");
                                if (ambId == null)
                                    ambId = "AMB-101";

                                String driverEmail = doc.getString("driverID");
                                if (driverEmail == null)
                                    driverEmail = doc.getString("driveremail");
                                if (driverEmail == null)
                                    driverEmail = doc.getString("driverEmail");
                                if (driverEmail == null)
                                    driverEmail = doc.getString("email");
                                if (driverEmail == null)
                                    driverEmail = "--";

                                String msg = doc.getString("message");
                                if (msg == null || msg.trim().isEmpty()) {
                                    msg = "Emergency Case En Route • Priority Corridor";
                                }

                                String status = doc.getString("status");
                                if (status == null || status.trim().isEmpty()) {
                                    status = "EN_ROUTE_TO_HOSPITAL";
                                }

                                String pickupLocation = doc.getString("pickupLocation");
                                if (pickupLocation == null)
                                    pickupLocation = doc.getString("source");
                                if (pickupLocation == null)
                                    pickupLocation = "--";

                                String hName = doc.getString("hospitalName");
                                if (hName == null)
                                    hName = doc.getString("hospitalAddress");
                                if (hName == null)
                                    hName = hospitalName;

                                Double dLat = doc.getDouble("destLat");
                                Double dLng = doc.getDouble("destLng");
                                Double pLat = doc.getDouble("pickupLat");
                                if (pLat == null)
                                    pLat = doc.getDouble("srcLat");
                                Double pLng = doc.getDouble("pickupLng");
                                if (pLng == null)
                                    pLng = doc.getDouble("srcLng");
                                Timestamp ts = doc.getTimestamp("timestamp");

                                // Build Card UI
                                VBox ambCard = new VBox(7);
                                ambCard.setPadding(new Insets(12));
                                ambCard.setMaxWidth(Double.MAX_VALUE);
                                ambCard.setStyle(CARD_STYLE_DEFAULT);

                                // Header row
                                HBox ambHead = new HBox(8);
                                ambHead.setAlignment(Pos.CENTER_LEFT);

                                Text cardAmbIcon = new Text("🚑");
                                cardAmbIcon.setStyle("-fx-font-size: 15px;");

                                VBox ambTitleBox = new VBox(1);
                                Text ambIdText = new Text(ambId);
                                ambIdText.setStyle(
                                        FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: 800; -fx-fill: #0F172A;");
                                Text cardIncomingLabel = new Text("INCOMING AMBULANCE");
                                cardIncomingLabel.setStyle(
                                        FONT_FAMILY + "-fx-font-size: 8px; -fx-font-weight: bold; -fx-fill: #64748B;");
                                ambTitleBox.getChildren().addAll(ambIdText, cardIncomingLabel);

                                Region ambSp = new Region();
                                HBox.setHgrow(ambSp, Priority.ALWAYS);

                                Label tIdTag = new Label(tripId);
                                tIdTag.setStyle(FONT_FAMILY
                                        + "-fx-background-color: #EFF6FF; -fx-text-fill: #0284C7; -fx-font-size: 9.5px; -fx-font-weight: bold; -fx-padding: 2px 7px; -fx-background-radius: 6px;");

                                Label statusBadge = new Label(status.replace("_", " "));
                                statusBadge.setStyle(FONT_FAMILY
                                        + "-fx-background-color: #DCFCE7; -fx-text-fill: #16A34A; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 12px;");

                                ambHead.getChildren().addAll(cardAmbIcon, ambTitleBox, ambSp, tIdTag, statusBadge);

                                // Divider
                                Region divider = new Region();
                                divider.setPrefHeight(1);
                                divider.setStyle("-fx-background-color: #E2E8F0;");

                                // Patient & Driver Row
                                HBox patientDriverRow = new HBox(12);
                                VBox pBox = new VBox(2);
                                HBox.setHgrow(pBox, Priority.ALWAYS);
                                Text pCaption = new Text("PATIENT");
                                pCaption.setStyle(
                                        FONT_FAMILY + "-fx-font-size: 8px; -fx-font-weight: bold; -fx-fill: #94A3B8;");
                                Text pText = new Text(patId);
                                pText.setStyle(
                                        FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: #334155;");
                                pBox.getChildren().addAll(pCaption, pText);

                                VBox dBox = new VBox(2);
                                HBox.setHgrow(dBox, Priority.ALWAYS);
                                Text dCaption = new Text("DRIVER");
                                dCaption.setStyle(
                                        FONT_FAMILY + "-fx-font-size: 8px; -fx-font-weight: bold; -fx-fill: #94A3B8;");
                                Text dText = new Text(driverEmail);
                                dText.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-fill: #334155;");
                                dBox.getChildren().addAll(dCaption, dText);
                                patientDriverRow.getChildren().addAll(pBox, dBox);

                                // Route Row
                                HBox routeBox = new HBox(6);
                                routeBox.setAlignment(Pos.CENTER_LEFT);
                                Text rIcon = new Text("📍");
                                rIcon.setStyle("-fx-font-size: 11px;");
                                VBox rDetails = new VBox(1);
                                Text rCaption = new Text("PICKUP LOCATION");
                                rCaption.setStyle(
                                        FONT_FAMILY + "-fx-font-size: 8px; -fx-font-weight: bold; -fx-fill: #94A3B8;");
                                Text rText = new Text(pickupLocation);
                                rText.setWrappingWidth(300);
                                rText.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-fill: #475569;");
                                rDetails.getChildren().addAll(rCaption, rText);
                                routeBox.getChildren().addAll(rIcon, rDetails);

                                // Message Text
                                Text msgText = new Text(msg);
                                msgText.setWrappingWidth(320);
                                msgText.setStyle(
                                        FONT_FAMILY + "-fx-font-size: 11px; -fx-fill: #DC2626; -fx-font-weight: 600;");

                                ambCard.getChildren().addAll(ambHead, divider, patientDriverRow, routeBox, msgText);
                                incomingAmbulanceList.getChildren().add(ambCard);

                                final VBox thisCard = ambCard;
                                final String fAmbId = ambId;
                                final String fPatId = patId;
                                final String fTripId = tripId;
                                final String fDriverId = driverEmail;
                                final String fStatus = status;
                                final String fPickup = pickupLocation;
                                final String fMsg = msg;
                                final Double fPLat = pLat;
                                final Double fPLng = pLng;
                                final Double fDLat = dLat;
                                final Double fDLng = dLng;
                                final String fHosp = hName;
                                final Timestamp fTs = ts;

                                // Interactive hover and click
                                ambCard.setOnMouseEntered(e -> {
                                    if (thisCard != selectedAmbulanceCardNode) {
                                        thisCard.setStyle(CARD_STYLE_HOVER);
                                    }
                                });
                                ambCard.setOnMouseExited(e -> {
                                    if (thisCard != selectedAmbulanceCardNode) {
                                        thisCard.setStyle(CARD_STYLE_DEFAULT);
                                    }
                                });
                                ambCard.setOnMouseClicked(e -> {
                                    selectAmbulanceRequest(
                                            thisCard,
                                            fAmbId, fPatId, fTripId, fDriverId,
                                            fStatus, fPickup, fMsg,
                                            fPLat, fPLng, fDLat, fDLng,
                                            fHosp, fTs,
                                            ambulanceAlertId, emergencyAlertType, patientAlertName,
                                            driverAlertMessage, alertTime, alertStatus);
                                });

                                // Select first request on initial load or if matching
                                if (i == 0) {
                                    selectAmbulanceRequest(
                                            thisCard,
                                            fAmbId, fPatId, fTripId, fDriverId,
                                            fStatus, fPickup, fMsg,
                                            fPLat, fPLng, fDLat, fDLng,
                                            fHosp, fTs,
                                            ambulanceAlertId, emergencyAlertType, patientAlertName,
                                            driverAlertMessage, alertTime, alertStatus);
                                }
                            }
                        });
                    });
        }

        // =============================================================
        // SCROLL PANE
        // =============================================================

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        VBox finalContent = new VBox(scrollPane);
        finalContent.setStyle("-fx-background-color: #a5bdaaff;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        borderPane.setCenter(finalContent);

        // =============================================================
        // NAVIGATION ACTIONS (WITH CLEAN ACTIVE/INACTIVE SWITCHING)
        // =============================================================

        dashboardButton.setOnAction(event -> {
            setActiveNav(dashboardButton, patientMonitoringButton, resourceButton, doctorButton, patientButton,
                    settingsButton, complaintButton);
            borderPane.setCenter(finalContent);
        });

        patientMonitoringButton.setOnAction(event -> {
            setActiveNav(patientMonitoringButton, dashboardButton, resourceButton, doctorButton, patientButton,
                    settingsButton, complaintButton);
            HospitalPatientMonitoring monitoring = new HospitalPatientMonitoring(selectedPatientId, selectedTripId,
                    hospitalName);
            borderPane.setCenter(monitoring.getPatientMonitoring());
        });

        resourceButton.setOnAction(event -> {
            setActiveNav(resourceButton, dashboardButton, patientMonitoringButton, doctorButton, patientButton,
                    settingsButton, complaintButton);
            HospitalResourceManagement resourceManagement = new HospitalResourceManagement(safeHospitalEmail);
            borderPane.setCenter(resourceManagement.getResourceManagement());
        });

        doctorButton.setOnAction(event -> {
            setActiveNav(doctorButton, dashboardButton, patientMonitoringButton, resourceButton, patientButton,
                    settingsButton, complaintButton);
            HospitalDoctorManagement doctorManagement = new HospitalDoctorManagement(safeHospitalEmail);
            borderPane.setCenter(doctorManagement.getDoctorManagement());
        });

        patientButton.setOnAction(event -> {
            setActiveNav(patientButton, dashboardButton, patientMonitoringButton, resourceButton, doctorButton,
                    settingsButton, complaintButton);
            HospitalPatientEmergencyRecords patientRecords = new HospitalPatientEmergencyRecords(safeHospitalEmail);
            borderPane.setCenter(patientRecords.getPatientEmergencyRecords());
        });

        complaintButton.setOnAction(event -> {
            setActiveNav(complaintButton, dashboardButton, patientMonitoringButton, resourceButton, doctorButton,
                    patientButton, settingsButton);
            HospitalComplaints complaints = new HospitalComplaints();
            borderPane.setCenter(complaints.getComplaintSection());
        });

        settingsButton.setOnAction(event -> {
            setActiveNav(settingsButton, dashboardButton, patientMonitoringButton, resourceButton, doctorButton,
                    patientButton, complaintButton);
            HospitalSettings settings = new HospitalSettings(safeHospitalEmail);
            borderPane.setCenter(settings.getSettings());
        });

        bookResourceButton.setOnAction(event -> {
            setActiveNav(resourceButton, dashboardButton, patientMonitoringButton, doctorButton, patientButton,
                    settingsButton, complaintButton);
            HospitalResourceManagement resourceManagement = new HospitalResourceManagement(safeHospitalEmail);
            borderPane.setCenter(resourceManagement.getResourceManagement());
        });

        viewDetailsButton.setOnAction(event -> {
            setActiveNav(patientMonitoringButton, dashboardButton, resourceButton, doctorButton, patientButton,
                    settingsButton, complaintButton);
            HospitalPatientMonitoring monitoring = new HospitalPatientMonitoring(selectedPatientId, selectedTripId,
                    hospitalName);
            borderPane.setCenter(monitoring.getPatientMonitoring());
        });

        logoutButton.setOnAction(event -> {
            isTrackingRunning = false;
            if (driToHosPopupListener != null)
                driToHosPopupListener.remove();
            try {
                Welcome welcome = new Welcome();
                welcome.start(dashboardStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // =============================================================
        // FULL SCREEN RESOLUTION FIX (SCREEN BOUNDS ALIGNMENT)
        // =============================================================
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

        rootStack = new StackPane(borderPane);
        rootStack.setStyle("-fx-background-color: #a5bdaaff;");

        popupOverlayContainer = new VBox(12);
        popupOverlayContainer.setAlignment(Pos.TOP_RIGHT);
        popupOverlayContainer.setPickOnBounds(false);
        popupOverlayContainer.setMaxWidth(420);
        popupOverlayContainer.setPadding(new Insets(20, 24, 0, 0));
        StackPane.setAlignment(popupOverlayContainer, Pos.TOP_RIGHT);
        rootStack.getChildren().add(popupOverlayContainer);

        dashboardScene = new Scene(rootStack, visualBounds.getWidth(), visualBounds.getHeight());
        try {
            dashboardScene.getStylesheets()
                    .add(getClass().getResource("/css/modern-hospital-theme.css").toExternalForm());
        } catch (Exception ignored) {
        }

        dashboardStage.setX(visualBounds.getMinX());
        dashboardStage.setY(visualBounds.getMinY());
        dashboardStage.setWidth(visualBounds.getWidth());
        dashboardStage.setHeight(visualBounds.getHeight());

        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("LifeLink Hospital Dashboard (" + hospitalName + ")");
        dashboardStage.setMaximized(true);
        dashboardStage.show();

        startLiveTracking();

        dashboardStage.setOnCloseRequest(e -> {
            isTrackingRunning = false;
            if (driToHosPopupListener != null)
                driToHosPopupListener.remove();
            dashboardStage.close();
        });

        listenForDriToHosPopup();
        checkAndShowPendingDriToHosPopup();
    }

    // =========================================================
    // INCOMING REQUEST SELECTION & TELEMETRY SYNCHRONIZATION
    // =========================================================
    private void selectAmbulanceRequest(
            VBox cardNode,
            String ambId, String patId, String tripId, String driverId,
            String status, String pickupLoc, String msg,
            Double pLat, Double pLng, Double dLat, Double dLng,
            String hosp, Timestamp ts,
            Text ambulanceAlertId, Label emergencyAlertType, Text patientAlertName,
            Text driverAlertMessage, Text alertTime, Label alertStatus) {
        this.selectedPatientId = (patId != null && !patId.trim().isEmpty()) ? patId.trim() : "PAT-UNKNOWN";
        this.selectedTripId = (tripId != null && !tripId.trim().isEmpty()) ? tripId.trim() : "TRIP-AUTO";
        this.selectedAmbulanceId = (ambId != null && !ambId.trim().isEmpty()) ? ambId.trim() : "AMB-101";
        this.selectedDriverEmail = (driverId != null) ? driverId.trim() : "";
        this.selectedMessage = (msg != null) ? msg.trim() : "";
        this.selectedStatus = (status != null) ? status.trim() : "INCOMING";

        if (selectedAmbulanceCardNode != null) {
            selectedAmbulanceCardNode.setStyle(CARD_STYLE_DEFAULT);
        }
        selectedAmbulanceCardNode = cardNode;
        if (cardNode != null) {
            cardNode.setStyle(CARD_STYLE_SELECTED);
        }

        ambulanceAlertId.setText(selectedAmbulanceId);
        emergencyAlertType.setText(selectedStatus.replace("_", " "));
        patientAlertName.setText("Patient: " + selectedPatientId + " • Pre-admission Triage");
        driverAlertMessage.setText((msg != null && !msg.trim().isEmpty()) ? msg : "Ambulance is en route to hospital.");
        if (ts != null) {
            alertTime.setText(
                    "Received: " + ts.toDate().toString() + " • Destination: " + (hosp != null ? hosp : hospitalName));
        } else {
            alertTime.setText(
                    "Live Dispatch • In transit via active GPS corridor to " + (hosp != null ? hosp : hospitalName));
        }
        alertStatus.setText("●  SELECTED ALERT");
        alertStatus.setStyle(
                "-fx-background-color: #EFF6FF; " +
                        "-fx-text-fill: #1D4ED8; " +
                        "-fx-border-color: #BFDBFE; " +
                        "-fx-border-radius: 12px; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-font-size: 10px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 3px 9px; " +
                        FONT_FAMILY);

        if (pLat != null && pLng != null) {
            pendingAmbulanceLat = pLat;
            pendingAmbulanceLon = pLng;
        } else if (pickupLoc != null && !pickupLoc.trim().isEmpty()) {
            pendingAmbulanceLat = getLatitude(pickupLoc);
            pendingAmbulanceLon = getLongitude(pickupLoc);
        }
        if (dLat != null && dLng != null) {
            activeInboundDestLat = dLat;
            activeInboundDestLng = dLng;
        }
        pendingHospitalName = (hosp != null && !hosp.isEmpty()) ? hosp : hospitalName;
        activeInboundHospName = pendingHospitalName;
        pendingAmbulanceRequest = true;

        if (ambulanceMapEngine != null && isMapLoaded) {
            double effLat = (liveAmbulanceLat != null && liveAmbulanceLat != 0.0) ? liveAmbulanceLat
                    : pendingAmbulanceLat;
            double effLng = (liveAmbulanceLng != null && liveAmbulanceLng != 0.0) ? liveAmbulanceLng
                    : pendingAmbulanceLon;
            String safeDest = pendingHospitalName.replace("'", "\\'");

            if (activeInboundDestLat != null && activeInboundDestLng != null && activeInboundDestLat != 0.0) {
                String js = String.format(Locale.US,
                        "if (typeof setRouteWithCoordinates === 'function') { setRouteWithCoordinates(%f, %f, %f, %f, '%s'); } else { setRouteFromCurrentLocation(%f, %f, '%s'); }",
                        effLat, effLng, activeInboundDestLat, activeInboundDestLng, safeDest,
                        effLat, effLng, safeDest);
                ambulanceMapEngine.executeScript(js);
            } else {
                String js = String.format(Locale.US,
                        "if (typeof setRouteFromCurrentLocation === 'function') { setRouteFromCurrentLocation(%f, %f, '%s'); } else if (typeof updateAmbulance === 'function') { updateAmbulance(%f, %f); }",
                        effLat, effLng, safeDest, effLat, effLng);
                ambulanceMapEngine.executeScript(js);
            }
        }
        System.out.println("[HospitalDashboard] Selected incoming request: Ambulance=" + selectedAmbulanceId
                + ", Patient=" + selectedPatientId + ", Trip=" + selectedTripId);
    }

    // =========================================================
    // DRITOHOSPOPUP REAL-TIME LISTENER & CONFIRMATION MODAL
    // =========================================================
    private void listenForDriToHosPopup() {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) {
                System.err.println("[DriToHosPopup] Firestore is not initialized.");
                return;
            }

            if (driToHosPopupListener != null) {
                driToHosPopupListener.remove();
            }

            System.out.println("[DriToHosPopup] Starting listener for hospital: " + hospitalEmail);

            driToHosPopupListener = db.collection("DriToHosPopup")
                    .addSnapshotListener((snapshots, error) -> {
                        if (error != null) {
                            System.err.println("[DriToHosPopup] Error: " + error.getMessage());
                            return;
                        }
                        if (snapshots == null)
                            return;

                        String currentHospEmail = (hospitalEmail != null) ? hospitalEmail.trim() : "";
                        String currentHospName = (hospitalName != null) ? hospitalName.trim() : "";

                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
                            String docHospEmail = doc.getString("hospitalemail");
                            if (docHospEmail == null) docHospEmail = doc.getString("hospitalEmail");
                            if (docHospEmail == null) docHospEmail = doc.getString("email");

                            String docHospName = doc.getString("hospitalName");

                            boolean isEmailTarget = (docHospEmail != null && !currentHospEmail.isEmpty()
                                    && docHospEmail.trim().equalsIgnoreCase(currentHospEmail));
                            boolean isNameTarget = (docHospName != null && !currentHospName.isEmpty()
                                    && docHospName.trim().equalsIgnoreCase(currentHospName));

                            if (!isEmailTarget && !isNameTarget) {
                                continue;
                            }

                            String status = doc.getString("status");
                            if (status == null || !status.trim().equalsIgnoreCase("not confirm")) {
                                continue;
                            }

                            String docId = doc.getId();
                            if (handledDriToHosPopupDocIds.contains(docId)) {
                                continue;
                            }
                            handledDriToHosPopupDocIds.add(docId);

                            String ambId = doc.getString("ambulanceId");
                            if (ambId == null || ambId.trim().isEmpty()) ambId = doc.getString("ambulanceID");
                            if (ambId == null || ambId.trim().isEmpty()) ambId = doc.getString("ambulance_id");
                            if (ambId == null || ambId.trim().isEmpty()) ambId = doc.getString("driverID");
                            if (ambId == null || ambId.trim().isEmpty()) ambId = "AMB-101";

                            String patId = doc.getString("patID");
                            if (patId == null || patId.trim().isEmpty()) patId = doc.getString("patientID");
                            if (patId == null || patId.trim().isEmpty()) patId = doc.getString("patientId");
                            if (patId == null || patId.trim().isEmpty()) patId = doc.getString("patId");
                            if (patId == null || patId.trim().isEmpty()) patId = doc.getString("TripID");
                            if (patId == null || patId.trim().isEmpty()) patId = doc.getString("tripID");
                            if (patId == null || patId.trim().isEmpty()) patId = "PAT-101";

                            final String finalDocId = docId;
                            final String finalAmbId = ambId;
                            final String finalPatId = patId;

                            Platform.runLater(() -> {
                                if (dashboardStage != null && dashboardStage.isShowing() && popupOverlayContainer != null) {
                                    showDriToHosPopupDialog(finalDocId, finalAmbId, finalPatId);
                                } else {
                                    pendingDriToHosDocId = finalDocId;
                                    pendingDriToHosAmbId = finalAmbId;
                                    pendingDriToHosPatId = finalPatId;
                                }
                            });
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAndShowPendingDriToHosPopup() {
        if (pendingDriToHosDocId != null && popupOverlayContainer != null) {
            final String dId = pendingDriToHosDocId;
            final String aId = pendingDriToHosAmbId;
            final String pId = pendingDriToHosPatId;
            pendingDriToHosDocId = null;
            pendingDriToHosAmbId = null;
            pendingDriToHosPatId = null;
            Platform.runLater(() -> showDriToHosPopupDialog(dId, aId, pId));
        }
    }

    private void showDriToHosPopupDialog(String docId, String ambulanceId, String patId) {
        if (popupOverlayContainer == null) {
            pendingDriToHosDocId = docId;
            pendingDriToHosAmbId = ambulanceId;
            pendingDriToHosPatId = patId;
            return;
        }

        popupOverlayContainer.getChildren().clear();

        VBox card = new VBox(12);
        card.setMaxWidth(390);
        card.setMinWidth(350);
        card.setPadding(new Insets(16, 18, 16, 18));
        card.setStyle(
                "-fx-background-color: #FFFFFF; " +
                "-fx-background-radius: 16px; " +
                "-fx-border-color: #0D9488; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 16px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(13, 148, 136, 0.35), 24, 0, 0, 8);"
        );

        // Header Row with Live Badge and Close Button
        HBox headerRow = new HBox(8);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        HBox badgePill = new HBox(6);
        badgePill.setAlignment(Pos.CENTER);
        badgePill.setPadding(new Insets(4, 10, 4, 10));
        badgePill.setStyle("-fx-background-color: #FEF2F2; -fx-border-color: #FECDD3; -fx-border-radius: 12; -fx-background-radius: 12;");

        Circle pulseDot = new Circle(4, Color.web("#EF4444"));
        FadeTransition ftPulse = new FadeTransition(Duration.millis(700), pulseDot);
        ftPulse.setFromValue(1.0);
        ftPulse.setToValue(0.25);
        ftPulse.setAutoReverse(true);
        ftPulse.setCycleCount(FadeTransition.INDEFINITE);
        ftPulse.play();

        Label badgeText = new Label("INBOUND AMBULANCE");
        badgeText.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: 800; -fx-text-fill: #BE123C; -fx-letter-spacing: 0.5px;");
        badgePill.getChildren().addAll(pulseDot, badgeText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button dismissBtn = new Button("✕");
        dismissBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #94A3B8; -fx-font-size: 12px; -fx-cursor: hand; -fx-padding: 0 4;");
        dismissBtn.setOnMouseEntered(e -> dismissBtn.setStyle("-fx-background-color: #F1F5F9; -fx-text-fill: #334155; -fx-font-size: 12px; -fx-cursor: hand; -fx-padding: 0 4; -fx-background-radius: 4;"));
        dismissBtn.setOnMouseExited(e -> dismissBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #94A3B8; -fx-font-size: 12px; -fx-cursor: hand; -fx-padding: 0 4;"));
        dismissBtn.setOnAction(e -> dismissNotificationCard(card));

        headerRow.getChildren().addAll(badgePill, spacer, dismissBtn);

        // Details Row: Ambulance ID and PatID only
        HBox detailsRow = new HBox(10);
        detailsRow.setAlignment(Pos.CENTER);
        detailsRow.setMaxWidth(Double.MAX_VALUE);

        VBox ambBox = new VBox(2);
        ambBox.setAlignment(Pos.CENTER);
        ambBox.setPadding(new Insets(8, 12, 8, 12));
        ambBox.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        HBox.setHgrow(ambBox, Priority.ALWAYS);

        Label ambLabel = new Label("AMBULANCE ID");
        ambLabel.setStyle(FONT_FAMILY + "-fx-font-size: 9px; -fx-font-weight: 700; -fx-text-fill: #64748B;");
        Text ambValue = new Text(ambulanceId);
        ambValue.setStyle(FONT_FAMILY + "-fx-font-size: 14px; -fx-font-weight: 800; -fx-fill: #006591;");
        ambBox.getChildren().addAll(ambLabel, ambValue);

        VBox patBox = new VBox(2);
        patBox.setAlignment(Pos.CENTER);
        patBox.setPadding(new Insets(8, 12, 8, 12));
        patBox.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #E2E8F0; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        HBox.setHgrow(patBox, Priority.ALWAYS);

        Label patLabel = new Label("PATIENT ID (PatID)");
        patLabel.setStyle(FONT_FAMILY + "-fx-font-size: 9px; -fx-font-weight: 700; -fx-text-fill: #64748B;");
        Text patValue = new Text(patId);
        patValue.setStyle(FONT_FAMILY + "-fx-font-size: 14px; -fx-font-weight: 800; -fx-fill: #BE123C;");
        patBox.getChildren().addAll(patLabel, patValue);

        detailsRow.getChildren().addAll(ambBox, patBox);

        // Compulsory message Box: "Your all resourses are updated or not ?"
        VBox messageBox = new VBox(4);
        messageBox.setAlignment(Pos.CENTER);
        messageBox.setPadding(new Insets(10, 12, 10, 12));
        messageBox.setStyle("-fx-background-color: #FEF3C7; -fx-border-color: #FCD34D; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Text compulsoryMessage = new Text("Your all resourses are updated or not ?");
        compulsoryMessage.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: 800; -fx-fill: #92400E;");
        messageBox.getChildren().add(compulsoryMessage);

        // Action Buttons: Confirm and Deny
        HBox actionsRow = new HBox(12);
        actionsRow.setAlignment(Pos.CENTER);

        Button denyBtn = new Button("✕ Deny");
        denyBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(denyBtn, Priority.ALWAYS);
        denyBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #EF4444; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 12px; " +
                "-fx-padding: 8 16; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(239, 68, 68, 0.35), 6, 0, 0, 2);"
        );

        Button confirmBtn = new Button("✓ Confirm");
        confirmBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(confirmBtn, Priority.ALWAYS);
        confirmBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #10B981; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 12px; " +
                "-fx-padding: 8 16; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(16, 185, 129, 0.35), 6, 0, 0, 2);"
        );

        denyBtn.setOnAction(e -> {
            updateDriToHosStatus(docId, "deny");
            dismissNotificationCard(card);
        });

        confirmBtn.setOnAction(e -> {
            updateDriToHosStatus(docId, "confirm");
            dismissNotificationCard(card);
        });

        actionsRow.getChildren().addAll(denyBtn, confirmBtn);

        card.getChildren().addAll(headerRow, detailsRow, messageBox, actionsRow);

        card.setOpacity(0.0);
        card.setTranslateX(40.0);
        popupOverlayContainer.getChildren().add(card);

        TranslateTransition tt = new TranslateTransition(Duration.millis(250), card);
        tt.setToX(0.0);
        FadeTransition ft = new FadeTransition(Duration.millis(250), card);
        ft.setToValue(1.0);
        tt.play();
        ft.play();
    }

    private void dismissNotificationCard(VBox card) {
        if (card == null || popupOverlayContainer == null) return;
        TranslateTransition tt = new TranslateTransition(Duration.millis(200), card);
        tt.setToX(60.0);
        FadeTransition ft = new FadeTransition(Duration.millis(200), card);
        ft.setToValue(0.0);
        tt.setOnFinished(e -> popupOverlayContainer.getChildren().remove(card));
        tt.play();
        ft.play();
    }

    private void updateDriToHosStatus(String docId, String status) {
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db != null && docId != null && !docId.trim().isEmpty()) {
                    Map<String, Object> update = new HashMap<>();
                    update.put("status", status);
                    update.put("respondedAt", Timestamp.now());
                    update.put("respondedHospitalEmail", (hospitalEmail != null) ? hospitalEmail.trim() : "");
                    db.collection("DriToHosPopup").document(docId).set(update, SetOptions.merge()).get();
                    System.out.println("[DriToHosPopup] Updated doc " + docId + " status to: " + status);
                }
            } catch (Exception ex) {
                System.err.println("[DriToHosPopup] Failed to update status: " + ex.getMessage());
                ex.printStackTrace();
            }
        }).start();
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
        trackingThread.setName("HospitalLiveTrackingThread");
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
            System.out.println("[HospitalDashboard] LIVE AMBULANCE GPS TELEMETRY");
            System.out.println("Latitude  : " + latitude);
            System.out.println("Longitude : " + longitude);
            System.out.println("========================================");

            Platform.runLater(() -> {
                if (isMapLoaded && ambulanceMapEngine != null) {
                    String targetHosp = (activeInboundHospName != null && !activeInboundHospName.isEmpty())
                            ? activeInboundHospName
                            : hospitalName;
                    if (targetHosp == null)
                        targetHosp = "Hospital";
                    String safeHospName = targetHosp.replace("'", "\\'");

                    if (activeInboundDestLat != null && activeInboundDestLng != null && activeInboundDestLat != 0.0) {
                        String js = String.format(Locale.US,
                                "if (typeof updateAmbulanceLive === 'function') { " +
                                        "  updateAmbulanceLive(%f, %f, %f, %f, '%s'); " +
                                        "} else if (typeof updateAmbulance === 'function') { " +
                                        "  updateAmbulance(%f, %f); " +
                                        "}",
                                latitude, longitude, activeInboundDestLat, activeInboundDestLng, safeHospName, latitude,
                                longitude);
                        ambulanceMapEngine.executeScript(js);
                    } else if (pendingAmbulanceRequest || activeInboundHospName != null) {
                        String js = String.format(Locale.US,
                                "if (typeof updateAmbulanceLive === 'function') { " +
                                        "  updateAmbulanceLive(%f, %f, null, null, '%s'); " +
                                        "} else if (typeof updateAmbulance === 'function') { " +
                                        "  updateAmbulance(%f, %f); " +
                                        "}",
                                latitude, longitude, safeHospName, latitude, longitude);
                        ambulanceMapEngine.executeScript(js);
                    } else {
                        String js = String.format(Locale.US,
                                "if (typeof updateAmbulance === 'function') { updateAmbulance(%f, %f); }",
                                latitude, longitude);
                        ambulanceMapEngine.executeScript(js);
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("[HospitalDashboard] ThingSpeak fetch error: " + e.getMessage());
        }
    }

    private double getLatitude(String pickupLocation) {
        if (pickupLocation == null) {
            return 18.5018;
        }
        if (pickupLocation.toLowerCase().contains("katraj")) {
            return 18.4575;
        }
        if (pickupLocation.toLowerCase().contains("swargate")) {
            return 18.5018;
        }
        if (pickupLocation.toLowerCase().contains("narhe")) {
            return 18.4852;
        }
        return 18.5018;
    }

    private double getLongitude(String pickupLocation) {
        if (pickupLocation == null) {
            return 73.8636;
        }
        if (pickupLocation.toLowerCase().contains("katraj")) {
            return 73.8677;
        }
        if (pickupLocation.toLowerCase().contains("swargate")) {
            return 73.8636;
        }
        if (pickupLocation.toLowerCase().contains("narhe")) {
            return 73.8166;
        }
        return 73.8636;
    }

    @Override
    public void stop() throws Exception {
        isTrackingRunning = false;
        if (driToHosPopupListener != null)
            driToHosPopupListener.remove();
        super.stop();
    }
}
