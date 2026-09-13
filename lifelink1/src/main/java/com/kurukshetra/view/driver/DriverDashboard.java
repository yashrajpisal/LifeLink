package com.kurukshetra.view.driver;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.view.Welcome;
import com.google.cloud.Timestamp;
import java.util.Locale;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.ListenerRegistration;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import com.kurukshetra.view.util.ShimmerLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import netscape.javascript.JSObject;

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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

public class DriverDashboard extends Application {

    // =========================================================
    // CURRENT LOGGED IN DRIVER SESSION EMAIL
    // =========================================================
    public static String loggedInDriverEmail = "driver1@lifelink.com";

    // =========================================================
    // FAINT & LIGHT COLOR PALETTE (POLICE-MATCHED THEME)
    // =========================================================
    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, sans-serif; ";

    private static final String PAGE_BG = "#ccdde7ff";         // Faint sky-blue page canvas
    private static final String MENU_BG = "#D6EDFA";         // Richer sky-blue menu bar
    private static final String SURFACE = "#FFFFFF";         // Pure white cards
    private static final String BORDER = "#A8D8F0";          // Medium sky border
    private static final String LIGHT_GRAY = "#E8F5FC";

    // Navigation & Primary Accents (vibrant sky blue)
    private static final String ACTIVE_NAV_BG = "#D6F2FB";   // Vibrant light sky blue
    private static final String ACTIVE_NAV_TEXT = "#0694C8"; // Deep sky blue
    private static final String BLUE = "#29B6E8";            // Vibrant sky blue
    private static final String BLUE_DARK = "#0694C8";       // Deep hover blue
    private static final String LIGHT_BLUE = "#E0F7FD";      // Faint sky blue

    private static final String GREEN = "#059669";           // Success emerald
    private static final String LIGHT_GREEN = "#ECFDF5";     // Faint success green

    private static final String ORANGE = "#D97706";          // Faint amber
    private static final String LIGHT_ORANGE = "#FFFBEB";

    private static final String RED = "#DC2626";             // Soft alert red
    private static final String LIGHT_RED = "#FFF1F2";

    private static final String TEXT = "#0A2540";            // Dark readable text
    private static final String SECONDARY = "#4A6A85";       // Muted navy-blue
    private static final String MUTED = "#7EA8C1";

    public static Stage driverStage;
    private Scene driverScene;
    public static BorderPane root;

    // LifeLink Voice Control Fields
    private Button voiceCommandButton;
    private boolean voiceControlEnabled = false;
    private Label voiceStatusLabel;
    private final Map<String, Button> hospitalSelectButtons = new HashMap<>();
    private final Map<String, Button> hospitalNotifyButtons = new HashMap<>();
    private final Map<String, HospitalEntry> displayedHospitalEntries = new HashMap<>();
    private DriverVoiceCommandService voiceCommandService;
    private String lastTtsEmergencyTripId = "";
    private String lastAnnouncedHospitalName = "";
    private String currentRecommendedHospitalName = "";

    // Real-time Containers & Listeners
    private StackPane emergencyCardSlot;
    private HBox hospitalCardsContainer;
    private HBox hospitalArea;
    private VBox notificationOverlay;
    private Label hospitalCountLabel;
    private Label hospitalSubtitleLabel;
    private VBox missionProgressContainer;
    private ListenerRegistration emergencyListener;
    private ListenerRegistration missionProgressListener;
    private ListenerRegistration hospitalNotifyListener;
    private ListenerRegistration hospitalListener;
    private WebEngine activeMapEngine;
    private boolean isMapReady = false;

    // Active Mission Tracking & Destination Coordinates
    private String activeDocId = "";
    private String activeTripId = "";
    private String activePatientId = "";
    private String activeSource = "Swargate";
    private String activeDestinationHospital = "Core2Web Pune";
    private String activeAmbulanceId = "AMB-101";
    private String activeNurseEmail = "nurse@lifelink.com";
    private Double activePickupLat = null;
    private Double activePickupLng = null;
    private Double targetPickupLat = null;
    private Double targetPickupLng = null;
    private boolean hasAutoMarkedPickup = false;
    private Double activeDestLat = null;
    private Double activeDestLng = null;
    private String lastNotifiedTripId = "";
    private String currentMissionStatus = "STANDBY";
    private boolean isAssigned = false;
    private boolean isPoliceNotified = false;
    private Button activeNotifyPoliceBtn = null;
    private boolean isHospitalNotified = false;
    private boolean isHospitalConfirmed = false;
    private boolean hasAlertedHospitalConfirmation = false;
    private String notifiedHospitalName = "";
    private ListenerRegistration driToHosPopupListener;
    private String loadedHospitalTripId = "";
    private boolean isDirectionChosen = false;
    private BorderPane mainEmergencyPane;

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

    // Track dismissed notification trip IDs so cut pop-ups never show again across tab switches
    private static final Set<String> dismissedEmergencyTripIds = Collections.synchronizedSet(new HashSet<>());
    private static final Set<String> dismissedHospitalTripIds = Collections.synchronizedSet(new HashSet<>());

    // Image Caches
    private Image logoImage;
    private Image ambulanceImage;
    private Image hospitalImage;

    // JavaScript Bridge
    public class JavaControlRoomBridge {
        public void updateDistanceETA(String distanceStr, String etaStr) {
            Platform.runLater(() -> {
                System.out.println("[DriverNavigation] Distance: " + distanceStr + " | ETA: " + etaStr);
            });
        }
    }

    private final JavaControlRoomBridge mapBridge = new JavaControlRoomBridge();

    private static class HospitalEntry {
        String id;
        String name;
        String email;
        String address;
        String status;
        int availableICUBeds;
        int notBusyDoctorsCount;
        int availableOTCount;
        String distanceDisplay;
        String etaDisplay;
        double calculatedDistanceKm;
        int etaMins;
        Double hospLat;
        Double hospLng;

        HospitalEntry(String id, String name, String email, String address, String status, int availableICUBeds, int notBusyDoctorsCount, int availableOTCount, String distanceDisplay, String etaDisplay, double calculatedDistanceKm, int etaMins, Double hospLat, Double hospLng) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.address = address;
            this.status = status;
            this.availableICUBeds = availableICUBeds;
            this.notBusyDoctorsCount = notBusyDoctorsCount;
            this.availableOTCount = availableOTCount;
            this.distanceDisplay = distanceDisplay;
            this.etaDisplay = etaDisplay;
            this.calculatedDistanceKm = calculatedDistanceKm;
            this.etaMins = etaMins;
            this.hospLat = hospLat;
            this.hospLng = hospLng;
        }
    }

    // =========================================================
    // IMAGE LOADING HELPER
    // =========================================================
    private Image loadSafeImage(String resourcePath, String relativePath) {
        try {
            var res = getClass().getResource(resourcePath);
            if (res != null) {
                return new Image(res.toExternalForm(), true);
            }
        } catch (Exception ignored) {}

        try {
            File f = new File(relativePath);
            if (f.exists()) {
                return new Image(f.toURI().toString(), true);
            }
            File f2 = new File("LifeLink/lifelink1/" + relativePath);
            if (f2.exists()) {
                return new Image(f2.toURI().toString(), true);
            }
        } catch (Exception ignored) {}

        return null;
    }

    private void preloadImages() {
        logoImage = loadSafeImage("/assets/Images/lifelinklogonew.png", "src/main/resources/assets/Images/lifelinklogonew.png");
        ambulanceImage = loadSafeImage("/assets/Images/ambulance.jpeg", "src/main/resources/assets/Images/ambulance.jpeg");
        hospitalImage = loadSafeImage("/assets/Images/hospital.jpeg", "src/main/resources/assets/Images/hospital.jpeg");
    }

    // =========================================================
    // MAIN DASHBOARD PANE
    // =========================================================
    public BorderPane getEmergencyPane() {
        preloadImages();

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        VBox content = new VBox(22);
        content.setPadding(new Insets(26, 30, 30, 30));

        // 1. TOP HEADER (Police-Style Clean Header)
        HBox topBar = createDashboardHeader();

        // 2. MAIN CONTENT ROW (Emergency Card Slot + Mission Progress Milestones)
        HBox topSection = new HBox(20);

        emergencyCardSlot = new StackPane();
        HBox.setHgrow(emergencyCardSlot, Priority.ALWAYS);
        emergencyCardSlot.getChildren().add(createStandbyEmergencyCard());

        VBox missionCard = createMissionProgressCard();
        topSection.getChildren().addAll(emergencyCardSlot, missionCard);

        // 3. AI RECOMMENDED HOSPITALS AREA (Hidden when no emergency is assigned)
        hospitalArea = createHospitalArea();
        hospitalArea.setVisible(false);
        hospitalArea.setManaged(false);

        content.getChildren().addAll(topBar, topSection, hospitalArea);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
        scrollPane.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-background: " + PAGE_BG + "; " +
            "-fx-border-color: transparent; " +
            "-fx-focus-color: transparent; " +
            "-fx-faint-focus-color: transparent;"
        );

        StackPane centerStack = new StackPane();
        centerStack.setStyle("-fx-background-color: " + PAGE_BG + ";");
        centerStack.getChildren().add(scrollPane);

        notificationOverlay = new VBox(12);
        notificationOverlay.setAlignment(Pos.TOP_RIGHT);
        notificationOverlay.setPadding(new Insets(18, 24, 0, 0));
        notificationOverlay.setPickOnBounds(false);
        centerStack.getChildren().add(notificationOverlay);

        pane.setCenter(centerStack);

        listenToAssignedEmergencyRequests();
        startLiveTracking();

        FadeTransition fade = new FadeTransition(Duration.millis(350), pane);
        fade.setFromValue(0.3);
        fade.setToValue(1);
        fade.play();

        return pane;
    }

    // =========================================================
    // TOP HEADER (POLICE STYLE)
    // =========================================================
    private HBox createDashboardHeader() {
        HBox headerRow = new HBox(15);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        Label title = new Label("Ambulance Driver Dashboard");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 28px; -fx-font-weight: 800; -fx-text-fill: " + TEXT + ";");

        // Label sub = new Label("Real-time emergency dispatch, green corridor navigation & hospital ER coordination.");
        // sub.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-text-fill: " + MUTED + ";");
        titleBox.getChildren().addAll(title);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // GPS Corridor Status Pill
        HBox gpsPill = new HBox(6);
        gpsPill.setAlignment(Pos.CENTER);
        gpsPill.setPadding(new Insets(6, 14, 6, 14));
        gpsPill.setStyle(
            "-fx-background-color: " + LIGHT_GREEN + "; " +
            "-fx-border-color: #A7F3D0; " +
            "-fx-border-radius: 16px; " +
            "-fx-background-radius: 16px;"
        );
        Label dot = new Label("●");
        dot.setStyle("-fx-font-size: 9px; -fx-text-fill: " + GREEN + ";");
        Label gpsTxt = new Label("LIVE GPS CORRIDOR");
        gpsTxt.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + GREEN + ";");
        gpsPill.getChildren().addAll(dot, gpsTxt);

        // =========================================================
        // LIFE LINK VOICE CONTROL TOGGLE
        // =========================================================
        voiceCommandButton = new Button();
        voiceCommandButton.setPrefWidth(82);
        voiceCommandButton.setPrefHeight(30);
        voiceCommandButton.setFocusTraversable(false);
        updateVoiceControlToggle();

        voiceCommandButton.setOnAction(e -> {
            voiceControlEnabled = !voiceControlEnabled;
            updateVoiceControlToggle();

            if (voiceControlEnabled) {
                updateVoiceStatus("Say \"Hey LifeLink\"", GREEN);
                if (voiceCommandService == null) {
                    initializeVoiceCommandService();
                }
                voiceCommandService.startWakeWordListening(
                        new HashSet<>(displayedHospitalEntries.keySet())
                );
            } else {
                updateVoiceStatus("Voice control disabled", SECONDARY);
                if (voiceCommandService != null) {
                    voiceCommandService.stopWakeWordListening();
                }
            }
        });

        voiceStatusLabel = new Label("Voice ready");
        voiceStatusLabel.setStyle(
                FONT_FAMILY +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + MUTED + ";"
        );

        VBox voiceBox = new VBox(3);
        voiceBox.setAlignment(Pos.CENTER);
        voiceBox.getChildren().addAll(voiceCommandButton, voiceStatusLabel);

        headerRow.getChildren().addAll(titleBox, spacer, voiceBox, gpsPill);
        return headerRow;
    }

    // =========================================================
    // FIRESTORE DISPATCH LISTENER
    // =========================================================
    private void listenToAssignedEmergencyRequests() {
        try {
            if (emergencyListener != null) emergencyListener.remove();

            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return;

            emergencyListener = db.collection("adminEmergencyRequests")
                    .whereEqualTo("driverID", loggedInDriverEmail.trim())
                    .addSnapshotListener((snapshots, error) -> {
                        if (error != null) return;

                        Platform.runLater(() -> {
                            if (snapshots == null || snapshots.isEmpty()) {
                                isAssigned = false;
                                activeDocId = "";
                                activeTripId = "";
                                activePatientId = "";
                                activePickupLat = null;
                                activePickupLng = null;
                                targetPickupLat = null;
                                targetPickupLng = null;
                                hasAutoMarkedPickup = false;
                                activeDestLat = null;
                                activeDestLng = null;
                                activeAmbulanceId = "AMB-101";
                                activeNurseEmail = "";
                                lastNotifiedTripId = "";
                                lastTtsEmergencyTripId = "";
                                lastAnnouncedHospitalName = "";
                                currentRecommendedHospitalName = "";
                                currentMissionStatus = "STANDBY";
                                isPoliceNotified = false;
                                activeNotifyPoliceBtn = null;
                                isHospitalNotified = false;
                                isHospitalConfirmed = false;
                                hasAlertedHospitalConfirmation = false;
                                detachMissionProgressListener();
                                detachHospitalNotificationListener();
                                updateMissionProgressUI();
                                updateEmergencyCardView(createStandbyEmergencyCard());
                                if (hospitalArea != null) {
                                    hospitalArea.setVisible(false);
                                    hospitalArea.setManaged(false);
                                }
                                return;
                            }

                            DocumentSnapshot activeDoc = null;
                            for (DocumentSnapshot doc : snapshots.getDocuments()) {
                                String status = doc.getString("status");
                                if (status != null && !status.equalsIgnoreCase("COMPLETE") && !status.equalsIgnoreCase("COMPLETED")) {
                                    activeDoc = doc;
                                    break;
                                }
                            }

                            if (activeDoc != null) {
                                activeDocId = activeDoc.getId();
                                String patID = activeDoc.getString("patID") != null ? activeDoc.getString("patID") : "PAT-2001";
                                String source = extractPickupLocation(activeDoc);
                                String destination = activeDoc.getString("destination") != null ? activeDoc.getString("destination") : (activeDoc.getString("destinationHospital") != null ? activeDoc.getString("destinationHospital") : "Core2Web Pune");
                                String severity = activeDoc.getString("severity") != null ? activeDoc.getString("severity") : "HIGH";
                                String notes = activeDoc.getString("notes") != null ? activeDoc.getString("notes") : "Emergency Transport Protocol Active";
                                String status = activeDoc.getString("status") != null ? activeDoc.getString("status") : "EN_ROUTE_TO_PICKUP";
                                String tripID = activeDoc.getString("tripID") != null ? activeDoc.getString("tripID") : activeDoc.getId();

                                String ambId = activeDoc.getString("ambulanceId") != null ? activeDoc.getString("ambulanceId") : (activeDoc.getString("ambulanceID") != null ? activeDoc.getString("ambulanceID") : "AMB-101");
                                String nurseId = activeDoc.getString("nurseID") != null ? activeDoc.getString("nurseID") : (activeDoc.getString("nurseEmail") != null ? activeDoc.getString("nurseEmail") : (activeDoc.getString("nurseemail") != null ? activeDoc.getString("nurseemail") : "nurse@lifelink.com"));
                                activeAmbulanceId = ambId;
                                activeNurseEmail = nurseId;

                                // Extract destination coordinates directly from adminEmergencyRequests (destLat, destinationLat, latitude, lat)
                                Double dLat = extractDouble(activeDoc, "destLat", "destinationLat", "dest_lat", "latitude", "lat");
                                Double dLng = extractDouble(activeDoc, "destLng", "destinationLng", "dest_lng", "longitude", "lng", "long");
                                if (dLat == null || dLng == null || dLat == 0.0 || dLng == 0.0) {
                                    double[] fallbackCoords = resolveKnownCoordinates(destination);
                                    dLat = fallbackCoords[0];
                                    dLng = fallbackCoords[1];
                                }
                                activeDestLat = dLat;
                                activeDestLng = dLng;

                                // Extract pickup coordinates from adminEmergencyRequests (pickupLat, sourceLat)
                                // Note: DO NOT fall back to latitude/longitude because those belong to destination in adminEmergencyRequests
                                Double pLat = extractDouble(activeDoc, "pickupLat", "sourceLat", "pickup_lat", "source_lat");
                                Double pLng = extractDouble(activeDoc, "pickupLng", "sourceLng", "pickup_lng", "source_lng");
                                if (pLat == null || pLng == null || pLat == 0.0 || pLng == 0.0) {
                                    double[] pCoords = resolveKnownCoordinates(source);
                                    pLat = pCoords[0];
                                    pLng = pCoords[1];
                                }
                                activePickupLat = pLat;
                                activePickupLng = pLng;
                                targetPickupLat = pLat;
                                targetPickupLng = pLng;

                                System.out.println("[DriverDashboard] Destination Coordinates from adminEmergencyRequests: Lat=" + activeDestLat + ", Lng=" + activeDestLng + " (Hospital: " + destination + ")");
                                System.out.println("[DriverDashboard] Pickup Coordinates: Lat=" + activePickupLat + ", Lng=" + activePickupLng + " (Location: " + source + ")");

                                boolean isNewDispatch = !tripID.equals(lastNotifiedTripId);
                                if (isNewDispatch) {
                                    lastNotifiedTripId = tripID;
                                    hasAutoMarkedPickup = false;
                                    hasAlertedHospitalConfirmation = false;
                                    showNewEmergencyNotification(tripID, patID, source, destination, severity, activeDestLat, activeDestLng);
                                    plotRouteOnMap(source, destination, activeDestLat, activeDestLng);

                                    if (!tripID.equals(lastTtsEmergencyTripId)) {
                                        lastTtsEmergencyTripId = tripID;
                                        SarvamTTSService.speak("New emergency assigned. Please check the emergency details.");
                                    }
                                }

                                isAssigned = true;
                                activeTripId = tripID;
                                activePatientId = patID;
                                activeSource = source;
                                activeDestinationHospital = destination;
                                currentMissionStatus = status;

                                if (hospitalArea != null) {
                                    hospitalArea.setVisible(true);
                                    hospitalArea.setManaged(true);
                                }

                                syncMissionProgressInit(tripID, patID, source, destination, status);
                                attachMissionProgressListener(tripID);
                                attachHospitalNotificationListener(tripID);

                                Node activeCard = createDynamicActiveEmergencyCard(tripID, patID, source, destination, severity, notes, status);
                                updateEmergencyCardView(activeCard);

                                // Only fetch hospital cards once per assigned trip, and NEVER re-fetch as mission progress advances or after directions are taken
                                if (!isDirectionChosen && (loadedHospitalTripId == null || !loadedHospitalTripId.equals(tripID))) {
                                    listenToHospitalCollection();
                                }
                            } else {
                                isAssigned = false;
                                activeDocId = "";
                                activeTripId = "";
                                activePatientId = "";
                                activePickupLat = null;
                                activePickupLng = null;
                                targetPickupLat = null;
                                targetPickupLng = null;
                                hasAutoMarkedPickup = false;
                                activeDestLat = null;
                                activeDestLng = null;
                                activeAmbulanceId = "AMB-101";
                                activeNurseEmail = "";
                                lastNotifiedTripId = "";
                                lastTtsEmergencyTripId = "";
                                lastAnnouncedHospitalName = "";
                                currentRecommendedHospitalName = "";
                                loadedHospitalTripId = "";
                                isDirectionChosen = false;
                                if (hospitalListener != null) {
                                    try { hospitalListener.remove(); } catch (Exception ignored) {}
                                    hospitalListener = null;
                                }
                                currentMissionStatus = "STANDBY";
                                isPoliceNotified = false;
                                activeNotifyPoliceBtn = null;
                                isHospitalNotified = false;
                                isHospitalConfirmed = false;
                                hasAlertedHospitalConfirmation = false;
                                detachMissionProgressListener();
                                detachHospitalNotificationListener();
                                updateMissionProgressUI();
                                updateEmergencyCardView(createStandbyEmergencyCard());
                                if (hospitalArea != null) {
                                    hospitalArea.setVisible(false);
                                    hospitalArea.setManaged(false);
                                }
                            }
                        });
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================
    // NURSE-STYLE TOAST NOTIFICATION SYSTEM (CLEAN TEXT, NO BUTTONS)
    // =========================================================
    private void showNurseStyleToast(String title, String message, String type, String tripId, boolean isEmergency) {
        // Record all top-right corner notifications into permanent driver notification store
        DriverNotification.addNotification(title, message, type, tripId, isEmergency);

        if (tripId != null) {
            if (isEmergency && dismissedEmergencyTripIds.contains(tripId)) return;
            if (!isEmergency && dismissedHospitalTripIds.contains(tripId)) return;
        }

        Platform.runLater(() -> {
            if (notificationOverlay == null) return;
            if (tripId != null) {
                if (isEmergency && dismissedEmergencyTripIds.contains(tripId)) return;
                if (!isEmergency && dismissedHospitalTripIds.contains(tripId)) return;
            }

            try {
                java.awt.Toolkit.getDefaultToolkit().beep();
            } catch (Exception ignored) {}

            String bg;
            String borderColor;
            String textColor;
            String icon;

            String lowerType = type != null ? type.toLowerCase() : "info";
            switch (lowerType) {
                case "success":
                    bg = "#F0FDF4";
                    borderColor = "#22C55E";
                    textColor = "#15803D";
                    icon = "✓";
                    break;
                case "error":
                case "danger":
                case "emergency":
                    bg = "#FEF2F2";
                    borderColor = "#EF4444";
                    textColor = "#B91C1C";
                    icon = "🚨";
                    break;
                case "warning":
                    bg = "#FFFBEB";
                    borderColor = "#F59E0B";
                    textColor = "#B45309";
                    icon = "⚠";
                    break;
                default:
                    bg = "#EFF6FF";
                    borderColor = "#3B82F6";
                    textColor = "#1D4ED8";
                    icon = "ℹ";
                    break;
            }

            HBox card = new HBox(12);
            card.setAlignment(Pos.CENTER_LEFT);
            card.setPadding(new Insets(12, 16, 12, 16));
            card.setMaxWidth(380);
            card.setStyle("-fx-background-color: " + bg + "; -fx-border-color: " + borderColor
                    + "; -fx-border-width: 1.5; -fx-background-radius: 12; -fx-border-radius: 12; -fx-effect: dropshadow(gaussian, rgba(43,33,37,0.18), 16, 0, 0, 4);");

            StackPane iconCircle = new StackPane();
            Circle circle = new Circle(14);
            circle.setFill(Color.web(borderColor));
            Text iconText = new Text(icon);
            iconText.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: white;");
            iconCircle.getChildren().addAll(circle, iconText);

            VBox textBox = new VBox(2);
            HBox.setHgrow(textBox, Priority.ALWAYS);

            Text titleText = new Text(title);
            titleText.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + textColor + ";");

            Label msgLbl = new Label(message);
            msgLbl.setWrapText(true);
            msgLbl.setMaxWidth(280);
            msgLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: #1E293B;");

            textBox.getChildren().addAll(titleText, msgLbl);

            Button closeBtn = new Button("×");
            closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748B; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 0 4 0 4; -fx-cursor: hand;");

            card.getChildren().addAll(iconCircle, textBox, closeBtn);

            card.setOpacity(0);
            card.setTranslateX(60);

            notificationOverlay.getChildren().add(0, card);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(250), card);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(250), card);
            slideIn.setFromX(60);
            slideIn.setToX(0);

            ParallelTransition animIn = new ParallelTransition(fadeIn, slideIn);

            PauseTransition hold = new PauseTransition(Duration.millis(4500));

            FadeTransition fadeOut = new FadeTransition(Duration.millis(280), card);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            TranslateTransition slideOut = new TranslateTransition(Duration.millis(280), card);
            slideOut.setToX(60);

            ParallelTransition animOut = new ParallelTransition(fadeOut, slideOut);

            SequentialTransition sequence = new SequentialTransition(animIn, hold, animOut);
            sequence.setOnFinished(e -> notificationOverlay.getChildren().remove(card));

            closeBtn.setOnAction(e -> {
                if (tripId != null) {
                    if (isEmergency) dismissedEmergencyTripIds.add(tripId);
                    else dismissedHospitalTripIds.add(tripId);
                }
                sequence.stop();
                notificationOverlay.getChildren().remove(card);
            });

            sequence.play();
        });
    }

    private void showNewEmergencyNotification(String tripID, String patID, String source, String destination, String severity, Double destLat, Double destLng) {
        String msg = "Trip #" + tripID + " [" + severity.toUpperCase() + "] • Patient: " + patID + "\nPickup: " + source + " ➜ " + destination;
        showNurseStyleToast("New Emergency Assigned", msg, "emergency", tripID, true);
    }

    // =========================================================
    // MISSION PROGRESS SYNC & LISTENERS
    // =========================================================
    private void syncMissionProgressInit(String tripId, String patId, String source, String destination, String currentStatus) {
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return;

                Map<String, Object> data = new HashMap<>();
                data.put("tripID", tripId);
                data.put("patID", patId);
                data.put("driverID", loggedInDriverEmail.trim());
                if (activeNurseEmail != null && !activeNurseEmail.trim().isEmpty()) {
                    data.put("nurseID", activeNurseEmail.trim());
                    data.put("nurseEmail", activeNurseEmail.trim());
                }
                data.put("source", source);
                data.put("destination", destination);
                data.put("currentStatus", currentStatus);
                data.put("lastUpdated", Timestamp.now());

                db.collection("missionProgress").document(tripId).set(data, SetOptions.merge());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateMissionProgressStatus(String newStatus) {
        currentMissionStatus = newStatus;
        if (Platform.isFxApplicationThread()) {
            updateMissionProgressUI();
        } else {
            Platform.runLater(this::updateMissionProgressUI);
        }

        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return;

                String timeStr = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));

                Map<String, Object> progressUpdate = new HashMap<>();
                progressUpdate.put("currentStatus", newStatus);
                progressUpdate.put("lastUpdated", Timestamp.now());
                progressUpdate.put("statusTime", timeStr);
                if (!activeTripId.isEmpty()) {
                    db.collection("missionProgress").document(activeTripId).set(progressUpdate, SetOptions.merge());
                }

                if (!activeDocId.isEmpty()) {
                    db.collection("adminEmergencyRequests").document(activeDocId).update("status", newStatus);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void attachMissionProgressListener(String tripId) {
        detachMissionProgressListener();
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return;

            missionProgressListener = db.collection("missionProgress").document(tripId)
                    .addSnapshotListener((snapshot, error) -> {
                        if (error != null || snapshot == null || !snapshot.exists()) return;

                        if (snapshot.contains("currentStatus") && snapshot.getString("currentStatus") != null) {
                            currentMissionStatus = snapshot.getString("currentStatus");
                        }
                        if (snapshot.contains("policeNotified") && snapshot.getBoolean("policeNotified") != null) {
                            isPoliceNotified = Boolean.TRUE.equals(snapshot.getBoolean("policeNotified"));
                        }

                        Platform.runLater(() -> {
                            updateNotifyPoliceBtnUI();
                            updateMissionProgressUI();
                        });
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void detachMissionProgressListener() {
        if (missionProgressListener != null) {
            try { missionProgressListener.remove(); } catch (Exception ignored) {}
            missionProgressListener = null;
        }
    }

    private void attachHospitalNotificationListener(String tripId) {
        detachHospitalNotificationListener();
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return;

            // 1. Primary listener on DriToHosPopup collection (TripID)
            driToHosPopupListener = db.collection("DriToHosPopup")
                    .whereEqualTo("TripID", tripId)
                    .addSnapshotListener((snapshots, error) -> {
                        if (error != null) return;
                        if (snapshots != null && !snapshots.isEmpty()) {
                            processHospitalPopupSnapshots(snapshots, tripId);
                        }
                    });

            // 2. Secondary listener on DriToHosPopup collection (tripID)
            hospitalNotifyListener = db.collection("DriToHosPopup")
                    .whereEqualTo("tripID", tripId)
                    .addSnapshotListener((snapshots, error) -> {
                        if (error != null) return;
                        if (snapshots != null && !snapshots.isEmpty()) {
                            processHospitalPopupSnapshots(snapshots, tripId);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processHospitalPopupSnapshots(QuerySnapshot snapshots, String tripId) {
        boolean anyConfirmed = false;
        String confirmedHospName = "";
        for (DocumentSnapshot doc : snapshots.getDocuments()) {
            isHospitalNotified = true;
            String hName = doc.getString("hospitalName");
            if (hName != null && !hName.isEmpty()) {
                notifiedHospitalName = hName;
            }

            String status = doc.getString("status");
            if (status != null && (status.equalsIgnoreCase("confirm") || status.equalsIgnoreCase("confirmed"))) {
                anyConfirmed = true;
                if (hName != null && !hName.isEmpty()) {
                    confirmedHospName = hName;
                }
            }
        }

        if (anyConfirmed) {
            if (!isHospitalConfirmed || !hasAlertedHospitalConfirmation) {
                isHospitalConfirmed = true;
                hasAlertedHospitalConfirmation = true;
                String targetHosp = confirmedHospName.isEmpty() ? notifiedHospitalName : confirmedHospName;
                showHospitalConfirmationNotification(targetHosp, tripId);
                plotRouteWithDirectCoordinates(activePickupLat, activePickupLng, null, null, (targetHosp.isEmpty() ? activeDestinationHospital : targetHosp));
            }
            isHospitalConfirmed = true;
        } else {
            isHospitalConfirmed = false;
        }
        Platform.runLater(this::updateMissionProgressUI);
    }

    private void detachHospitalNotificationListener() {
        if (driToHosPopupListener != null) {
            try { driToHosPopupListener.remove(); } catch (Exception ignored) {}
            driToHosPopupListener = null;
        }
        if (hospitalNotifyListener != null) {
            try { hospitalNotifyListener.remove(); } catch (Exception ignored) {}
            hospitalNotifyListener = null;
        }
    }

    private void showHospitalConfirmationNotification(String hospitalName, String tripID) {
        String targetHosp = hospitalName.isEmpty() ? activeDestinationHospital : hospitalName;
        String msg = "Trip #" + tripID + " • " + targetHosp + "\nTrauma bed & OT readiness secured for admission.";
        showNurseStyleToast("Hospital Confirmed Admission", msg, "success", tripID, false);
    }

    private void listenToHospitalCollection() {
        if (!isAssigned || isDirectionChosen) {
            return;
        }

        try {
            if (hospitalListener != null) {
                try { hospitalListener.remove(); } catch (Exception ignored) {}
                hospitalListener = null;
            }

            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return;

            showHospitalShimmerLoading();

            hospitalListener = db.collection("hospital").addSnapshotListener((snapshots, error) -> {
                if (error != null) {
                    System.err.println("[HospitalCollection] Snapshot error: " + error.getMessage());
                    return;
                }
                if (isDirectionChosen || !isAssigned) return;

                new Thread(() -> {
                    try {
                        if (!isAssigned) {
                            Platform.runLater(() -> {
                                if (hospitalArea != null) {
                                    hospitalArea.setVisible(false);
                                    hospitalArea.setManaged(false);
                                }
                            });
                            return;
                        }

                        if (snapshots == null || snapshots.isEmpty()) {
                            Platform.runLater(() -> {
                                if (hospitalCountLabel != null) hospitalCountLabel.setText("0 Hospitals");
                                if (hospitalCardsContainer != null) hospitalCardsContainer.getChildren().clear();
                            });
                            return;
                        }

                        // Determine destination origin coordinates for proximity sorting
                        double originLat;
                        double originLng;
                        if (activeDestLat != null && activeDestLng != null && activeDestLat != 0.0 && activeDestLng != 0.0) {
                            originLat = activeDestLat;
                            originLng = activeDestLng;
                        } else {
                            double[] originCoords = resolveKnownCoordinates(activeDestinationHospital.isEmpty() ? activeSource : activeDestinationHospital);
                            originLat = originCoords[0];
                            originLng = originCoords[1];
                        }

                        List<QueryDocumentSnapshot> docList = snapshots.getDocuments();
                        List<HospitalEntry> hospitalList = Collections.synchronizedList(new ArrayList<>());
                        ExecutorService pool = Executors.newFixedThreadPool(Math.min(docList.size(), 14));
                        List<CompletableFuture<Void>> futures = new ArrayList<>();

                        for (QueryDocumentSnapshot doc : docList) {
                            String id = doc.getId();
                            String rawName = doc.getString("hospitalName") != null ? doc.getString("hospitalName") : doc.getString("name");
                            Double docLat = doc.getDouble("latitude");
                            Double docLng = doc.getDouble("longitude");

                            // Filter out dummy test accounts that have no name and no coordinates
                            if (rawName == null && docLat == null && !isKnownHospitalId(id)) {
                                continue;
                            }

                            String name = rawName;
                            String address = doc.getString("address");
                            String status = doc.getString("status") != null ? doc.getString("status") : "Emergency Ready";
                            String email = doc.getString("email") != null ? doc.getString("email") : id;

                            if (name == null || name.trim().isEmpty() || name.contains("@")) {
                                name = resolveHospitalNameFromId(id);
                            }
                            if (address == null || address.trim().isEmpty() || address.contains("@")) {
                                address = resolveHospitalAddress(name, id);
                            }

                            Double hospLat = docLat;
                            Double hospLng = docLng;
                            if (hospLat == null || hospLng == null || hospLat == 0.0 || hospLng == 0.0) {
                                double[] coords = resolveKnownCoordinates(name + " " + address + " " + id);
                                hospLat = coords[0];
                                hospLng = coords[1];
                            }

                            double distanceKm = calculateHaversineDistance(originLat, originLng, hospLat, hospLng);
                            int etaMins = Math.max(2, (int) Math.ceil((distanceKm / 45.0) * 60));
                            String distStr = String.format("%.1f km", distanceKm);
                            String etaStr = etaMins + " min";

                            // Initial capacity values from doc fields or intelligent defaults
                            int initialICU = 5;
                            Object bedsObj = doc.get("beds");
                            if (bedsObj != null) {
                                String bStr = bedsObj.toString().replaceAll("[^0-9]", "");
                                if (!bStr.isEmpty()) initialICU = Integer.parseInt(bStr);
                            } else {
                                Long bLong = doc.getLong("availableICUBeds");
                                if (bLong != null) initialICU = bLong.intValue();
                            }

                            int initialDocs = 4;
                            Object dObj = doc.get("doctors");
                            if (dObj != null) {
                                String dStr = dObj.toString().replaceAll("[^0-9]", "");
                                if (!dStr.isEmpty()) initialDocs = Integer.parseInt(dStr);
                            }

                            int initialOT = 2;
                            Object otObj = doc.get("operationTheatres");
                            if (otObj != null) {
                                String otStr = otObj.toString().replaceAll("[^0-9]", "");
                                if (!otStr.isEmpty()) initialOT = Integer.parseInt(otStr);
                            }

                            // Add HospitalEntry immediately so entries are never omitted due to slow subcollections
                            HospitalEntry entry = new HospitalEntry(id, name, email, address, status,
                                    initialICU, initialDocs, initialOT,
                                    distStr, etaStr, distanceKm, etaMins, hospLat, hospLng);
                            hospitalList.add(entry);

                            // Asynchronously enrich live capacities from subcollections
                            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                                try {
                                    // 1. Available ICU Beds
                                    try {
                                        DocumentSnapshot resDoc = db.collection("hospital").document(id)
                                                .collection("resources").document("current").get().get(1500, TimeUnit.MILLISECONDS);
                                        if (resDoc != null && resDoc.exists()) {
                                            Long icuLong = resDoc.getLong("availableICUBeds");
                                            if (icuLong != null) {
                                                entry.availableICUBeds = icuLong.intValue();
                                            } else {
                                                Long genLong = resDoc.getLong("availableEmergencyBeds");
                                                if (genLong != null) entry.availableICUBeds = genLong.intValue();
                                            }
                                        }
                                    } catch (Exception ignored) {}

                                    // 2. Non-busy Doctors
                                    try {
                                        QuerySnapshot docSnap = db.collection("hospital").document(id)
                                                .collection("doctors").get().get(1500, TimeUnit.MILLISECONDS);
                                        if (docSnap != null && !docSnap.isEmpty()) {
                                            int freeDocs = 0;
                                            for (DocumentSnapshot dDoc : docSnap.getDocuments()) {
                                                String dStat = dDoc.getString("status");
                                                boolean isBusy = dStat != null && (
                                                    dStat.equalsIgnoreCase("busy") ||
                                                    dStat.equalsIgnoreCase("in surgery") ||
                                                    dStat.equalsIgnoreCase("surgery") ||
                                                    dStat.equalsIgnoreCase("assigned") ||
                                                    dStat.equalsIgnoreCase("on leave") ||
                                                    dStat.equalsIgnoreCase("offline")
                                                );
                                                if (!isBusy) freeDocs++;
                                            }
                                            entry.notBusyDoctorsCount = freeDocs;
                                        }
                                    } catch (Exception ignored) {}

                                    // 3. Available OT
                                    try {
                                        QuerySnapshot otSnap = db.collection("hospital").document(id)
                                                .collection("operationTheatres").get().get(1500, TimeUnit.MILLISECONDS);
                                        if (otSnap != null && !otSnap.isEmpty()) {
                                            int freeOT = 0;
                                            for (DocumentSnapshot otDoc : otSnap.getDocuments()) {
                                                String otStat = otDoc.getString("status");
                                                boolean isOccupied = otStat != null && (
                                                    otStat.equalsIgnoreCase("reserved") ||
                                                    otStat.equalsIgnoreCase("busy") ||
                                                    otStat.equalsIgnoreCase("occupied") ||
                                                    otStat.equalsIgnoreCase("in use")
                                                );
                                                if (!isOccupied) freeOT++;
                                            }
                                            entry.availableOTCount = freeOT;
                                        }
                                    } catch (Exception ignored) {}

                                } catch (Exception itemEx) {
                                    itemEx.printStackTrace();
                                }
                            }, pool);

                            futures.add(future);
                        }

                        // Wait for parallel completion (up to 3 seconds)
                        try {
                            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get(3000, TimeUnit.MILLISECONDS);
                        } catch (Exception waitEx) {
                            System.out.println("[HospitalCollection] Live capacity enrichment finished with timeout. Total hospitals loaded: " + hospitalList.size());
                        } finally {
                            pool.shutdown();
                        }

                        // Ensure at least 5 hospitals are present under all conditions
                        if (hospitalList.size() < 5) {
                            ensureMinimumFallbackHospitals(hospitalList, originLat, originLng);
                        }

                        // Multi-criteria sorting:
                        // 1. Primary: Minimum distance from destination coordinates (closest first)
                        // 2. Proximity bracket (within 1.0 km): Prioritize available ICU beds (ICU bed-wise)
                        // 3. Other parameters-wise: Available OT rooms and on-duty doctors capacity
                        // 4. Exact distance tie-breaker
                        List<HospitalEntry> sortedList = new ArrayList<>(hospitalList);
                        sortedList.sort((h1, h2) -> {
                            double distDiff = h1.calculatedDistanceKm - h2.calculatedDistanceKm;
                            if (Math.abs(distDiff) > 0.3) {
                                return Double.compare(h1.calculatedDistanceKm, h2.calculatedDistanceKm);
                            }

                            // Within 1.0 km proximity bracket: prioritize available ICU beds
                            boolean icu1 = h1.availableICUBeds > 0;
                            boolean icu2 = h2.availableICUBeds > 0;
                            if (icu1 != icu2) {
                                return icu1 ? -1 : 1;
                            }

                            if (Math.abs(h1.availableICUBeds - h2.availableICUBeds) >= 2) {
                                return Integer.compare(h2.availableICUBeds, h1.availableICUBeds);
                            }

                            // Other parameters: OT rooms + Doctors
                            int otherCap1 = (h1.availableOTCount * 2) + h1.notBusyDoctorsCount;
                            int otherCap2 = (h2.availableOTCount * 2) + h2.notBusyDoctorsCount;
                            if (otherCap1 != otherCap2) {
                                return Integer.compare(otherCap2, otherCap1);
                            }

                            return Double.compare(h1.calculatedDistanceKm, h2.calculatedDistanceKm);
                        });

                        // Show min 5 hospitals (up to 5 cards)
                        int topCount = Math.min(sortedList.size(), 5);
                        List<HospitalEntry> topHospitals = sortedList.subList(0, topCount);

                        Platform.runLater(() -> {
                            try {
                                if (!isAssigned) {
                                    if (hospitalArea != null) {
                                        hospitalArea.setVisible(false);
                                        hospitalArea.setManaged(false);
                                    }
                                    return;
                                }

                                if (hospitalCardsContainer != null) {
                                    hospitalCardsContainer.getChildren().clear();
                                }
                                hospitalSelectButtons.clear();
                                hospitalNotifyButtons.clear();
                                displayedHospitalEntries.clear();

                                if (hospitalCountLabel != null) {
                                    hospitalCountLabel.setText("Top " + topCount + " Recommended");
                                }
                                if (hospitalSubtitleLabel != null) {
                                    if (activeDestLat != null && activeDestLng != null) {
                                        hospitalSubtitleLabel.setText(String.format("Top %d sorted by GPS destination (%.4f, %.4f) • Distance • ICU Beds • OT Capacity", topCount, activeDestLat, activeDestLng));
                                    } else {
                                        hospitalSubtitleLabel.setText("Top " + topCount + " sorted by proximity to " + activeDestinationHospital + " • ICU Beds • OT Capacity");
                                    }
                                }

                                if (topHospitals.isEmpty()) {
                                    Label emptyLbl = new Label("No hospitals currently responding with available trauma units.");
                                    emptyLbl.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + MUTED + "; -fx-padding: 20;");
                                    hospitalCardsContainer.getChildren().add(emptyLbl);
                                } else {
                                    for (int i = 0; i < topHospitals.size(); i++) {
                                        HospitalEntry h = topHospitals.get(i);
                                        VBox card = createHospitalCard(h, i + 1);
                                        hospitalCardsContainer.getChildren().add(card);
                                    }
                                }

                                // Mark trip as loaded only after cards are successfully rendered
                                loadedHospitalTripId = activeTripId;

                                if (voiceCommandService != null) {
                                    voiceCommandService.updateHospitalKeys(displayedHospitalEntries.keySet());
                                }

                                if (!topHospitals.isEmpty()) {
                                    HospitalEntry recommended = topHospitals.get(0);
                                    String recommendedName = (recommended.name != null && !recommended.name.trim().isEmpty())
                                            ? recommended.name.trim()
                                            : resolveHospitalNameFromId(recommended.id);

                                    currentRecommendedHospitalName = recommendedName;
                                }

                            } catch (Exception renderEx) {
                                renderEx.printStackTrace();
                            }
                        });

                    } catch (Exception threadEx) {
                        threadEx.printStackTrace();
                        Platform.runLater(() -> {
                            if (hospitalCardsContainer != null && hospitalCardsContainer.getChildren().isEmpty()) {
                                if (hospitalCountLabel != null) hospitalCountLabel.setText("Offline Mode");
                            }
                        });
                    }
                }).start();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Double extractDouble(DocumentSnapshot doc, String... keys) {
        if (doc == null) return null;
        for (String key : keys) {
            if (doc.contains(key)) {
                Object val = doc.get(key);
                if (val != null) {
                    if (val instanceof Number) {
                        return ((Number) val).doubleValue();
                    }
                    try {
                        String s = val.toString().trim();
                        if (!s.isEmpty()) {
                            return Double.parseDouble(s);
                        }
                    } catch (Exception ignored) {}
                }
            }
        }
        return null;
    }

    private String extractPickupLocation(DocumentSnapshot doc) {
        if (doc == null) return "Swargate";

        String[] fields = {"pickupLocation", "patientPickupLocation", "pickup", "location", "address", "source"};
        for (String f : fields) {
            if (doc.contains(f) && doc.getString(f) != null) {
                String val = doc.getString(f).trim();
                if (!val.isEmpty() && !val.equalsIgnoreCase("Emergency Location")) {
                    return val;
                }
            }
        }

        return "Swargate";
    }

    private boolean isKnownHospitalId(String id) {
        if (id == null) return false;
        String low = id.toLowerCase();
        return low.contains("hospital") || low.contains("bharati") || low.contains("sknmcgh") 
            || low.contains("kem") || low.contains("hihoho") || low.contains("ojas") 
            || low.contains("sahyadri") || low.contains("pulse") || low.contains("silverbirch")
            || low.contains("vighnaharta") || low.contains("galaxy") || low.contains("inamdar")
            || low.contains("morya") || low.contains("noble") || low.contains("nobel");
    }

    private String resolveHospitalNameFromId(String id) {
        if (id == null) return "Emergency Hospital";
        String lowerId = id.toLowerCase();
        if (lowerId.contains("bharati") || lowerId.contains("bharatividyapeeth")) return "Bharati Vidyapeeth Hospital";
        if (lowerId.contains("sknmcgh") || lowerId.contains("navale")) return "Smt. Kashibai Navale Hospital";
        if (lowerId.contains("kem") || lowerId.contains("hihoho")) return "KEM Hospital & Research Centre";
        if (lowerId.contains("nobel") || lowerId.contains("ojas")) return "NOBEL Hospital";
        if (lowerId.contains("sahyadri")) return "Sahyadri Super Speciality Hospital";
        if (lowerId.contains("pulse")) return "Pulse Multi-Speciality Hospital";
        if (lowerId.contains("vighnaharta")) return "Vighnaharta Hospital";
        if (lowerId.contains("silverbirch") || lowerId.contains("silver birch")) return "Silver Birch Multi-Speciality Hospital";
        if (lowerId.contains("galaxy")) return "Galaxy Care Hospital";
        if (lowerId.contains("inamdar")) return "Inamdar Multispeciality Hospital";
        if (lowerId.contains("morya")) return "Morya Multi-Speciality Hospital";
        if (lowerId.contains("noble")) return "Noble Hospital";
        return id;
    }

    private String resolveHospitalAddress(String name, String id) {
        String lower = (name + " " + id).toLowerCase();
        if (lower.contains("bharati")) return "Pune-Satara Rd, Dhankawadi, Katraj";
        if (lower.contains("navale") || lower.contains("sknmcgh")) return "Narhe, Ambegaon BK, Pune";
        if (lower.contains("kem") || lower.contains("hihoho")) return "Rasta Peth, Sardar Moodliar Rd";
        if (lower.contains("nobel") || lower.contains("ojas") || lower.contains("noble")) return "153, Magarpatta Rd, Hadapsar";
        if (lower.contains("sahyadri")) return "Deccan Gymkhana, Pune";
        if (lower.contains("pulse")) return "Vadgaon Budruk, Sinhagad Rd";
        if (lower.contains("vighnaharta")) return "Katraj Kondhwa Rd, Pune";
        if (lower.contains("silverbirch")) return "Dhayari, Sinhagad Rd";
        if (lower.contains("galaxy")) return "Karve Rd, Kothrud, Pune";
        if (lower.contains("inamdar")) return "Fatima Nagar, Wanowrie";
        if (lower.contains("morya")) return "Chinchwad, Pune";
        return (name != null && !name.isEmpty()) ? (name + ", Pune") : "Pune, Maharashtra";
    }

    private void ensureMinimumFallbackHospitals(List<HospitalEntry> list, double originLat, double originLng) {
        String[][] fallbacks = {
            {"kem@hospital.com", "KEM Hospital & Research Centre", "Rasta Peth, Sardar Moodliar Rd, Pune", "18.5204", "73.8656", "6", "5", "3"},
            {"sahyadri@hospital.com", "Sahyadri Super Speciality Hospital", "Deccan Gymkhana, Pune", "18.5146", "73.8378", "8", "7", "4"},
            {"inamdar@hospital.com", "Inamdar Multispeciality Hospital", "Fatima Nagar, Wanowrie, Pune", "18.5012", "73.8996", "5", "4", "2"},
            {"bharati@hospital.com", "Bharati Vidyapeeth Hospital", "Pune-Satara Rd, Dhankawadi, Katraj", "18.4575", "73.8677", "10", "6", "4"},
            {"navale@hospital.com", "Smt. Kashibai Navale Hospital", "Narhe, Ambegaon BK, Pune", "18.4528", "73.8290", "7", "5", "3"},
            {"galaxy@hospital.com", "Galaxy Care Hospital", "Karve Rd, Kothrud, Pune", "18.5074", "73.8077", "4", "4", "2"}
        };

        Set<String> existingNames = new HashSet<>();
        for (HospitalEntry h : list) {
            if (h.name != null) existingNames.add(h.name.toLowerCase().trim());
        }

        for (String[] fb : fallbacks) {
            if (list.size() >= 5) break;
            String fbName = fb[1];
            if (existingNames.contains(fbName.toLowerCase().trim())) continue;

            double lat = Double.parseDouble(fb[3]);
            double lng = Double.parseDouble(fb[4]);
            double distKm = calculateHaversineDistance(originLat, originLng, lat, lng);
            int eta = Math.max(2, (int) Math.ceil((distKm / 45.0) * 60));
            String distStr = String.format("%.1f km", distKm);
            String etaStr = eta + " min";
            int icu = Integer.parseInt(fb[5]);
            int docs = Integer.parseInt(fb[6]);
            int ot = Integer.parseInt(fb[7]);

            list.add(new HospitalEntry(fb[0], fbName, fb[0], fb[2], "Emergency Ready",
                    icu, docs, ot, distStr, etaStr, distKm, eta, lat, lng));
            existingNames.add(fbName.toLowerCase().trim());
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
            conn.setRequestProperty("User-Agent", "LifeLink-Ambulance-System");
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

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round((R * c) * 10.0) / 10.0;
    }

    private void updateEmergencyCardView(Node newCard) {
        emergencyCardSlot.getChildren().clear();
        emergencyCardSlot.getChildren().add(newCard);
    }

    // =========================================================
    // STANDBY CARD WITH AMBULANCE IMAGE
    // =========================================================
    private VBox createStandbyEmergencyCard() {
        VBox card = new VBox(14);
        card.setPadding(new Insets(24));
        card.setAlignment(Pos.CENTER);
        card.setStyle(
            "-fx-background-color: " + SURFACE + "; " +
            "-fx-border-color: " + BORDER + "; " +
            "-fx-border-width: 1px; " +
            "-fx-background-radius: 14px; " +
            "-fx-border-radius: 14px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(30,60,90,0.04), 10, 0, 0, 3);"
        );

        HBox mainRow = new HBox(20);
        mainRow.setAlignment(Pos.CENTER);

        // Ambulance Image Holder
        StackPane imageHolder = new StackPane();
        imageHolder.setPrefSize(150, 105);
        imageHolder.setMinSize(150, 105);
        imageHolder.setMaxSize(150, 105);
        imageHolder.setStyle("-fx-background-color: " + LIGHT_BLUE + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");

        if (ambulanceImage != null && !ambulanceImage.isError()) {
            ImageView ambView = new ImageView(ambulanceImage);
            ambView.setFitWidth(150);
            ambView.setFitHeight(105);
            ambView.setPreserveRatio(false);
            Rectangle clip = new Rectangle(150, 105);
            clip.setArcWidth(22);
            clip.setArcHeight(22);
            ambView.setClip(clip);
            imageHolder.getChildren().add(ambView);
        } else {
            Label fallbackIcon = new Label("🚑");
            fallbackIcon.setStyle("-fx-font-size: 38px;");
            imageHolder.getChildren().add(fallbackIcon);
        }

        VBox infoBox = new VBox(6);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        HBox badgeRow = new HBox(8);
        badgeRow.setAlignment(Pos.CENTER_LEFT);

        Label standbyBadge = new Label("● AMBULANCE READY & STANDBY");
        standbyBadge.setStyle(
            FONT_FAMILY + "-fx-background-color: " + LIGHT_GREEN + "; " +
            "-fx-text-fill: " + GREEN + "; " +
            "-fx-font-size: 10px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 4 10; " +
            "-fx-background-radius: 8px; " +
            "-fx-border-color: #A7F3D0; -fx-border-radius: 8px;"
        );
        badgeRow.getChildren().add(standbyBadge);

        Label title = new Label("Unit Alpha-1 Online");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 20px; -fx-font-weight: 800; -fx-text-fill: " + TEXT + ";");

        Label subtitle = new Label("Driver: " + loggedInDriverEmail + "  |  Base Zone: Pune Central / Katraj");
        subtitle.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + MUTED + ";");

        Label tip = new Label("Standing by for high-priority dispatch calls from emergency control room.");
        tip.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + BLUE + ";");

        infoBox.getChildren().addAll(badgeRow, title, subtitle, tip);
        mainRow.getChildren().addAll(imageHolder, infoBox);

        card.getChildren().add(mainRow);
        return card;
    }

    // =========================================================
    // ACTIVE DISPATCH CARD WITH ROUTE IMAGES & LEAFLET MAP
    // =========================================================
    private VBox createDynamicActiveEmergencyCard(String tripID, String patID, String source, String destination, String severity, String notes, String status) {
        VBox card = new VBox(14);
        card.setPadding(new Insets(18));
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle(
            "-fx-background-color: " + SURFACE + "; " +
            "-fx-border-color: " + BORDER + "; " +
            "-fx-border-width: 1px; " +
            "-fx-background-radius: 14px; " +
            "-fx-border-radius: 14px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(30,60,90,0.05), 12, 0, 0, 3);"
        );

        // Header Row
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("🚨");
        icon.setStyle("-fx-font-size: 20px;");

        VBox heading = new VBox(2);
        Label title = new Label("Active Emergency Trip: " + tripID);
        title.setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: 800; -fx-text-fill: " + TEXT + ";");

        Label subtitle = new Label("Patient: " + patID + "  |  Status: " + status.replace("_", " "));
        subtitle.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " + MUTED + ";");
        heading.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label priority = new Label("● " + severity.toUpperCase() + " PRIORITY");
        priority.setStyle(
            FONT_FAMILY + "-fx-background-color: " + LIGHT_RED + "; " +
            "-fx-text-fill: " + RED + "; " +
            "-fx-font-size: 10.5px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 5 10; " +
            "-fx-background-radius: 8px; " +
            "-fx-border-color: #FECDD3; -fx-border-radius: 8px;"
        );

        header.getChildren().addAll(icon, heading, spacer, priority);

        // Journey Route Strip with Ambulance & Hospital Thumbnails
        HBox routeStrip = new HBox(14);
        routeStrip.setAlignment(Pos.CENTER_LEFT);
        routeStrip.setPadding(new Insets(10, 14, 10, 14));
        routeStrip.setStyle(
            "-fx-background-color: " + LIGHT_BLUE + "; " +
            "-fx-border-color: #D6EEFB; " +
            "-fx-border-radius: 10px; " +
            "-fx-background-radius: 10px;"
        );

        // Pickup point with Ambulance image
        HBox pickupPoint = new HBox(8);
        pickupPoint.setAlignment(Pos.CENTER_LEFT);

        StackPane ambIconPane = new StackPane();
        ambIconPane.setPrefSize(32, 32);
        ambIconPane.setMinSize(32, 32);
        ambIconPane.setStyle("-fx-background-color: white; -fx-background-radius: 6px; -fx-border-color: " + BORDER + "; -fx-border-radius: 6px;");

        if (ambulanceImage != null && !ambulanceImage.isError()) {
            ImageView ambThumb = new ImageView(ambulanceImage);
            ambThumb.setFitWidth(32);
            ambThumb.setFitHeight(32);
            Rectangle clip = new Rectangle(32, 32);
            clip.setArcWidth(12);
            clip.setArcHeight(12);
            ambThumb.setClip(clip);
            ambIconPane.getChildren().add(ambThumb);
        } else {
            Label l = new Label("🚑");
            ambIconPane.getChildren().add(l);
        }

        VBox pickupText = new VBox(1);
        Label pickupTag = new Label("PICKUP LOCATION");
        pickupTag.setStyle(FONT_FAMILY + "-fx-font-size: 8.5px; -fx-font-weight: bold; -fx-text-fill: " + MUTED + ";");
        Label pickupVal = new Label(source);
        pickupVal.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT + ";");
        pickupText.getChildren().addAll(pickupTag, pickupVal);
        pickupPoint.getChildren().addAll(ambIconPane, pickupText);

        Region routeSpacer = new Region();
        HBox.setHgrow(routeSpacer, Priority.ALWAYS);

        Label routeArrow = new Label("➔  ➔  GREEN CORRIDOR ACTIVE  ➔  ➔");
        routeArrow.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + BLUE + ";");

        Region routeSpacer2 = new Region();
        HBox.setHgrow(routeSpacer2, Priority.ALWAYS);

        // Destination with Hospital image
        HBox destPoint = new HBox(8);
        destPoint.setAlignment(Pos.CENTER_LEFT);

        StackPane hospIconPane = new StackPane();
        hospIconPane.setPrefSize(32, 32);
        hospIconPane.setMinSize(32, 32);
        hospIconPane.setStyle("-fx-background-color: white; -fx-background-radius: 6px; -fx-border-color: " + BORDER + "; -fx-border-radius: 6px;");

        if (hospitalImage != null && !hospitalImage.isError()) {
            ImageView hospThumb = new ImageView(hospitalImage);
            hospThumb.setFitWidth(32);
            hospThumb.setFitHeight(32);
            Rectangle clip = new Rectangle(32, 32);
            clip.setArcWidth(12);
            clip.setArcHeight(12);
            hospThumb.setClip(clip);
            hospIconPane.getChildren().add(hospThumb);
        } else {
            Label l = new Label("🏥");
            hospIconPane.getChildren().add(l);
        }

        VBox destText = new VBox(1);
        Label destTag = new Label("EMERGENCY DESTINATION");
        destTag.setStyle(FONT_FAMILY + "-fx-font-size: 8.5px; -fx-font-weight: bold; -fx-text-fill: " + MUTED + ";");
        Label destVal = new Label(destination);
        destVal.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + BLUE + ";");
        destText.getChildren().addAll(destTag, destVal);
        destPoint.getChildren().addAll(hospIconPane, destText);

        routeStrip.getChildren().addAll(pickupPoint, routeSpacer, routeArrow, routeSpacer2, destPoint);

        // Map View with dynamic rounded clipping
        StackPane mapPane = new StackPane();
        mapPane.setPrefHeight(380);
        mapPane.setMinHeight(380);
        mapPane.setMaxHeight(380);
        mapPane.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(mapPane, Priority.ALWAYS);
        mapPane.setStyle("-fx-background-color: " + PAGE_BG + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");

        WebView mapWebView = new WebView();
        mapWebView.setStyle("-fx-background-color: " + PAGE_BG + ";");
        mapWebView.setPrefHeight(380);
        mapWebView.setMaxHeight(380);

        Rectangle dynamicClip = new Rectangle();
        dynamicClip.setArcWidth(14);
        dynamicClip.setArcHeight(14);
        dynamicClip.widthProperty().bind(mapWebView.widthProperty());
        dynamicClip.heightProperty().bind(mapWebView.heightProperty());
        mapWebView.setClip(dynamicClip);

        activeMapEngine = mapWebView.getEngine();
        activeMapEngine.setJavaScriptEnabled(true);

        String mapUrl = getClass().getResource("/driver_map.html") != null
                ? getClass().getResource("/driver_map.html").toExternalForm()
                : "";

        if (!mapUrl.isEmpty()) {
            activeMapEngine.load(mapUrl);
            activeMapEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    JSObject window = (JSObject) activeMapEngine.executeScript("window");
                    window.setMember("javaControlRoom", mapBridge);
                    isMapReady = true;
                    plotRouteOnMap(source, destination, activeDestLat, activeDestLng);
                }
            });
        }

        // Notify Police Corner Button
        Button notifyPoliceBtn = new Button(isPoliceNotified ? "✓ Police Alerted" : "👮 Notify Police");
        notifyPoliceBtn.setStyle(isPoliceNotified
                ? FONT_FAMILY + "-fx-background-color: " + GREEN + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 7 14; -fx-background-radius: 6;"
                : FONT_FAMILY + "-fx-background-color: " + RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 7 14; -fx-background-radius: 6; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.18), 6, 0, 0, 2);");
        StackPane.setAlignment(notifyPoliceBtn, Pos.TOP_RIGHT);
        StackPane.setMargin(notifyPoliceBtn, new Insets(12));

        notifyPoliceBtn.setOnAction(e -> triggerPoliceAlert());
        activeNotifyPoliceBtn = notifyPoliceBtn;

        mapPane.getChildren().addAll(mapWebView, notifyPoliceBtn);

        // Navigation Action Button in clean blue
        Button navigation = new Button("⌖   NAVIGATE GREEN CORRIDOR FROM " + source.toUpperCase() + " TO " + destination.toUpperCase());
        navigation.setMaxWidth(Double.MAX_VALUE);
        navigation.setPrefHeight(44);
        navigation.setStyle(FONT_FAMILY + "-fx-background-color: " + BLUE + "; -fx-text-fill: white; -fx-font-size: 12.5px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;");
        navigation.setOnAction(e -> plotRouteWithDirectCoordinates(activePickupLat, activePickupLng, activeDestLat, activeDestLng, destination));
        navigation.setOnMouseEntered(e -> navigation.setStyle(FONT_FAMILY + "-fx-background-color: " + BLUE_DARK + "; -fx-text-fill: white; -fx-font-size: 12.5px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;"));
        navigation.setOnMouseExited(e -> navigation.setStyle(FONT_FAMILY + "-fx-background-color: " + BLUE + "; -fx-text-fill: white; -fx-font-size: 12.5px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;"));

        card.getChildren().addAll(header, routeStrip, mapPane, navigation);
        return card;
    }

    private void updateNotifyPoliceBtnUI() {
        if (activeNotifyPoliceBtn != null) {
            if (isPoliceNotified) {
                activeNotifyPoliceBtn.setText("✓ Police Alerted");
                activeNotifyPoliceBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + GREEN + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 7 14; -fx-background-radius: 6;");
            } else {
                activeNotifyPoliceBtn.setText("👮 Notify Police");
                activeNotifyPoliceBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + RED + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 7 14; -fx-background-radius: 6; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.18), 6, 0, 0, 2);");
            }
        }
    }

    private void triggerPoliceAlert() {
        if (isPoliceNotified) return;
        isPoliceNotified = true;
        updateNotifyPoliceBtnUI();
        updateMissionProgressUI();
        showNurseStyleToast("Police Corridor Alerted", "Priority traffic preemption clearance broadcast to city traffic police.", "info", activeTripId, false);
        SarvamTTSService.speak("Police notified.");

        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db != null) {
                    String tripId = activeTripId;
                    String patId = activePatientId;
                    String pickupLoc = activeSource;
                    String destHosp = activeDestinationHospital;
                    Double sLat = activePickupLat;
                    Double sLng = activePickupLng;
                    Double dLat = activeDestLat;
                    Double dLng = activeDestLng;

                    // Re-query adminEmergencyRequests for the most up-to-date pickupLocation & destination
                    if (!activeDocId.isEmpty()) {
                        try {
                            DocumentSnapshot latestDoc = db.collection("adminEmergencyRequests").document(activeDocId).get().get();
                            if (latestDoc != null && latestDoc.exists()) {
                                String docPickup = extractPickupLocation(latestDoc);
                                if (docPickup != null && !docPickup.isEmpty() && !docPickup.equalsIgnoreCase("Emergency Location")) {
                                    pickupLoc = docPickup;
                                    activeSource = docPickup;
                                }
                                String docDest = latestDoc.getString("destination");
                                if (docDest == null || docDest.trim().isEmpty()) {
                                    docDest = latestDoc.getString("destinationHospital");
                                }
                                if (docDest != null && !docDest.trim().isEmpty()) {
                                    destHosp = docDest.trim();
                                    activeDestinationHospital = destHosp;
                                }
                                Double docPLat = extractDouble(latestDoc, "pickupLat", "sourceLat", "pickup_lat", "source_lat");
                                Double docPLng = extractDouble(latestDoc, "pickupLng", "sourceLng", "pickup_lng", "source_lng");
                                if (docPLat != null && docPLng != null && docPLat != 0.0 && docPLng != 0.0) {
                                    sLat = docPLat;
                                    sLng = docPLng;
                                    activePickupLat = sLat;
                                    activePickupLng = sLng;
                                }
                                Double docDLat = extractDouble(latestDoc, "destLat", "destinationLat", "latitude", "lat");
                                Double docDLng = extractDouble(latestDoc, "destLng", "destinationLng", "longitude", "lng");
                                if (docDLat != null && docDLng != null && docDLat != 0.0 && docDLng != 0.0) {
                                    dLat = docDLat;
                                    dLng = docDLng;
                                    activeDestLat = dLat;
                                    activeDestLng = dLng;
                                }
                            }
                        } catch (Exception ex) {
                            System.err.println("[triggerPoliceAlert] Re-fetch error: " + ex.getMessage());
                        }
                    }

                    if (pickupLoc == null || pickupLoc.trim().isEmpty() || pickupLoc.equalsIgnoreCase("Emergency Location")) {
                        pickupLoc = (activeSource != null && !activeSource.isEmpty()) ? activeSource : "Swargate";
                    }

                    Map<String, Object> alert = new HashMap<>();
                    alert.put("tripID", tripId);
                    alert.put("patID", patId);
                    alert.put("driverID", loggedInDriverEmail);
                    alert.put("pickupLocation", pickupLoc);
                    alert.put("source", pickupLoc);
                    alert.put("destinationHospital", destHosp);
                    alert.put("destination", destHosp);
                    alert.put("status", "Green Corridor Requested");
                    alert.put("timestamp", Timestamp.now());
                    if (sLat != null && sLng != null) {
                        alert.put("srcLat", sLat);
                        alert.put("srcLng", sLng);
                        alert.put("pickupLat", sLat);
                        alert.put("pickupLng", sLng);
                    }
                    if (dLat != null && dLng != null) {
                        alert.put("destLat", dLat);
                        alert.put("destLng", dLng);
                        alert.put("latitude", dLat);
                        alert.put("longitude", dLng);
                    }

                    db.collection("policeEmergencyAlerts").add(alert);

                    Map<String, Object> prog = new HashMap<>();
                    prog.put("policeNotified", true);
                    prog.put("policeNotifiedTime", LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
                    prog.put("pickupLocation", pickupLoc);
                    prog.put("source", pickupLoc);
                    prog.put("destination", destHosp);
                    if (!activeTripId.isEmpty()) {
                        db.collection("missionProgress").document(activeTripId).set(prog, SetOptions.merge());
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void plotRouteOnMap(String sourceAddr, String destHospital) {
        plotRouteWithDirectCoordinates(activePickupLat, activePickupLng, activeDestLat, activeDestLng, destHospital);
    }

    private void plotRouteOnMap(String sourceAddr, String destHospital, Double destLat, Double destLng) {
        plotRouteWithDirectCoordinates(activePickupLat, activePickupLng, destLat, destLng, destHospital);
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
                double[] c = fetchCoordinatesForAddress(activeSource);
                startLat = c[0];
                startLng = c[1];
            }

            double endLat;
            double endLng;
            if (destLat != null && destLng != null && destLat != 0.0 && destLng != 0.0) {
                endLat = destLat;
                endLng = destLng;
            } else {
                double[] c = fetchCoordinatesForAddress(destName != null ? destName : activeDestinationHospital);
                endLat = c[0];
                endLng = c[1];
            }

            Platform.runLater(() -> {
                if (isMapReady && activeMapEngine != null) {
                    String js = "setTimeout(function() { " +
                                "  if (typeof setRouteWithCoordinates === 'function') { " +
                                "    setRouteWithCoordinates(" + startLat + ", " + startLng + ", " + endLat + ", " + endLng + ", '" + (destName != null ? destName.replace("'", "\\'") : "Emergency Destination") + "'); " +
                                "  } else if (typeof setRouteFromCurrentLocation === 'function') { " +
                                "    setRouteFromCurrentLocation(" + startLat + ", " + startLng + ", '" + (destName != null ? destName.replace("'", "\\'") : "Emergency Destination") + "'); " +
                                "  } " +
                                "}, 200);";
                    activeMapEngine.executeScript(js);
                }
            });
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
        trackingThread.setName("DriverLiveTrackingThread");
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
                System.out.println("[DriverDashboard] ThingSpeak HTTP status: " + response.statusCode());
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
                checkGeofenceArrivalAtPickup(latitude, longitude);
                return;
            }

            lastLatitude = latitude;
            lastLongitude = longitude;
            liveAmbulanceLat = latitude;
            liveAmbulanceLng = longitude;
            activePickupLat = latitude;
            activePickupLng = longitude;

            checkGeofenceArrivalAtPickup(latitude, longitude);

            System.out.println("========================================");
            System.out.println("[DriverDashboard] LIVE AMBULANCE GPS TELEMETRY");
            System.out.println("Latitude  : " + latitude);
            System.out.println("Longitude : " + longitude);
            System.out.println("========================================");

            Platform.runLater(() -> {
                if (isMapReady && activeMapEngine != null) {
                    String safeDestName = (activeDestinationHospital != null ? activeDestinationHospital.replace("'", "\\'") : "Emergency Destination");
                    if (activeDestLat != null && activeDestLng != null && activeDestLat != 0.0 && activeDestLng != 0.0) {
                        String js = "if (typeof updateAmbulanceLive === 'function') { " +
                                    "  updateAmbulanceLive(" + latitude + ", " + longitude + ", " + activeDestLat + ", " + activeDestLng + ", '" + safeDestName + "'); " +
                                    "} else if (typeof updateAmbulance === 'function') { " +
                                    "  updateAmbulance(" + latitude + ", " + longitude + "); " +
                                    "}";
                        activeMapEngine.executeScript(js);
                    } else {
                        String js = "if (typeof updateAmbulance === 'function') { updateAmbulance(" + latitude + ", " + longitude + "); }";
                        activeMapEngine.executeScript(js);
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("[DriverDashboard] ThingSpeak fetch error: " + e.getMessage());
        }
    }

    private double calculateDistanceInMeters(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371000.0; // Earth radius in meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2.0) * Math.sin(dLon / 2.0);
        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));
        return R * c;
    }

    private void checkGeofenceArrivalAtPickup(double currentLat, double currentLng) {
        if (!isAssigned || activeTripId.isEmpty()) {
            return;
        }

        // If pickup has already been reached or surpassed, do not re-trigger
        boolean isPickupAlreadyDone = currentMissionStatus.equalsIgnoreCase("ARRIVED_AT_PICKUP")
                || currentMissionStatus.equalsIgnoreCase("PATIENT_ONBOARD")
                || currentMissionStatus.equalsIgnoreCase("EN_ROUTE_TO_HOSPITAL")
                || currentMissionStatus.equalsIgnoreCase("ARRIVED_AT_HOSPITAL")
                || currentMissionStatus.equalsIgnoreCase("COMPLETED");

        if (isPickupAlreadyDone) {
            return;
        }

        Double pLat = targetPickupLat;
        Double pLng = targetPickupLng;
        if (pLat == null || pLng == null || pLat == 0.0 || pLng == 0.0) {
            if (activeSource != null && !activeSource.trim().isEmpty()) {
                double[] coords = resolveKnownCoordinates(activeSource);
                pLat = coords[0];
                pLng = coords[1];
                targetPickupLat = pLat;
                targetPickupLng = pLng;
            }
        }

        if (pLat == null || pLng == null || pLat == 0.0 || pLng == 0.0) {
            return;
        }

        double distanceMeters = calculateDistanceInMeters(currentLat, currentLng, pLat, pLng);

        // Reset auto-marked guard if ambulance moves away (> 300m)
        if (distanceMeters > 300.0) {
            hasAutoMarkedPickup = false;
        }

        System.out.println(String.format(Locale.US,
                "[DriverDashboard] Geofence Pickup Check: Ambulance(%.5f, %.5f) vs Pickup(%.5f, %.5f) | Distance: %.1fm",
                currentLat, currentLng, pLat, pLng, distanceMeters));

        // When coordinates match (within 150m or delta < 0.0015 deg ~ 110m)
        if (!hasAutoMarkedPickup && (distanceMeters <= 150.0 || (Math.abs(currentLat - pLat) < 0.0015 && Math.abs(currentLng - pLng) < 0.0015))) {
            hasAutoMarkedPickup = true;
            System.out.println(String.format(Locale.US,
                    "[DriverDashboard] >>> GPS MATCH! Ambulance arrived at pickup location (%.1fm). Auto-checking pickup checkbox. <<<", distanceMeters));

            Platform.runLater(() -> {
                updateMissionProgressStatus("ARRIVED_AT_PICKUP");
                showNurseStyleToast("Arrived at Pickup Location", "Ambulance arrived at patient pickup point (" + (activeSource != null && !activeSource.isEmpty() ? activeSource : "Pickup Point") + ").", "success", activeTripId, false);
            });
        }
    }

    // =========================================================
    // MISSION PROGRESS CARD & TIMELINE
    // =========================================================
    private VBox createMissionProgressCard() {
        VBox card = new VBox(14);
        card.setPrefWidth(310);
        card.setMinWidth(305);
        card.setMaxWidth(315);
        card.setPadding(new Insets(18, 18, 18, 18));
        card.setStyle(
            "-fx-background-color: " + SURFACE + "; " +
            "-fx-border-color: " + BORDER + "; " +
            "-fx-border-width: 1px; " +
            "-fx-background-radius: 14px; " +
            "-fx-border-radius: 14px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(30,60,90,0.04), 10, 0, 0, 3);"
        );

        HBox heading = new HBox(8);
        heading.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(2);
        Label title = new Label("Mission Progression");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 14px; -fx-font-weight: 800; -fx-text-fill: " + TEXT + ";");

        Label subtitle = new Label("Live timeline & dispatch telemetry");
        subtitle.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-text-fill: " + MUTED + ";");
        titleBox.getChildren().addAll(title);                                                                                 

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label statusBadge = new Label(isAssigned ? "● IN DISPATCH" : "○ STANDBY");
        statusBadge.setStyle(
            FONT_FAMILY + "-fx-font-size: 8.5px; -fx-font-weight: 800; " +
            "-fx-text-fill: " + (isAssigned ? GREEN : MUTED) + "; " +
            "-fx-background-color: " + (isAssigned ? LIGHT_GREEN : "#F1F5F9") + "; " +
            "-fx-padding: 3 7; -fx-background-radius: 6px;"
        );

        heading.getChildren().addAll(titleBox, spacer, statusBadge);

        missionProgressContainer = new VBox(0);
        VBox.setVgrow(missionProgressContainer, Priority.ALWAYS);
        updateMissionProgressUI();

        card.getChildren().addAll(heading, missionProgressContainer);
        return card;
    }

    private void updateMissionProgressUI() {
        if (missionProgressContainer == null) return;
        missionProgressContainer.getChildren().clear();

        if (!isAssigned) {
            missionProgressContainer.getChildren().addAll(
                createTimelineMilestone("🚨", "Trip Assignment", "Awaiting emergency dispatch call", "○ STANDBY", "#F1F5F9", "#64748B", false, false, false, null),
                createTimelineMilestone("🏥", "Hospital Confirmation", "Pending hospital selection", "○ STANDBY", "#F1F5F9", "#64748B", false, false, false, null),
                createTimelineMilestone("📍", "Arrived at Pickup", "Ambulance standing by at station", "○ STANDBY", "#F1F5F9", "#64748B", false, false, false, null),
                createTimelineMilestone("🚑", "Patient Onboard & Moving", "Pending pickup arrival", "○ STANDBY", "#F1F5F9", "#64748B", false, false, false, null),
                createTimelineMilestone("👮", "Police Notification", "Green corridor clearance pending", "○ STANDBY", "#F1F5F9", "#64748B", false, false, false, null),
                createTimelineMilestone("🏁", "ER Handover & Completed", "Pending patient admission", "○ STANDBY", "#F1F5F9", "#64748B", false, false, true, null)
            );
            return;
        }

        boolean isPickupDone = currentMissionStatus.equalsIgnoreCase("ARRIVED_AT_PICKUP")
                || currentMissionStatus.equalsIgnoreCase("PATIENT_ONBOARD")
                || currentMissionStatus.equalsIgnoreCase("EN_ROUTE_TO_HOSPITAL")
                || currentMissionStatus.equalsIgnoreCase("ARRIVED_AT_HOSPITAL")
                || currentMissionStatus.equalsIgnoreCase("COMPLETED");

        boolean isEnRouteHospDone = currentMissionStatus.equalsIgnoreCase("EN_ROUTE_TO_HOSPITAL")
                || currentMissionStatus.equalsIgnoreCase("ARRIVED_AT_HOSPITAL")
                || currentMissionStatus.equalsIgnoreCase("COMPLETED");

        boolean isCompleted = currentMissionStatus.equalsIgnoreCase("COMPLETED");

        // Dynamic progress completion percentage
        int completedCount = 0;
        if (isAssigned) completedCount++;
        if (isHospitalConfirmed) completedCount++;
        if (isPickupDone) completedCount++;
        if (isEnRouteHospDone) completedCount++;
        if (isPoliceNotified) completedCount++;
        if (isCompleted) completedCount++;
        int progressPct = (int) Math.round((completedCount / 6.0) * 100);

        HBox progressHeader = new HBox(6);
        progressHeader.setAlignment(Pos.CENTER_LEFT);
        progressHeader.setPadding(new Insets(0, 0, 6, 0));

        Label progLabel = new Label("MISSION COMPLETION");
        progLabel.setStyle(FONT_FAMILY + "-fx-font-size: 8.5px; -fx-font-weight: 800; -fx-text-fill: " + MUTED + ";");
        Region prSpacer = new Region();
        HBox.setHgrow(prSpacer, Priority.ALWAYS);
        Label progPctLabel = new Label(completedCount + "/6 STEPS  (" + progressPct + "%)");
        progPctLabel.setStyle(FONT_FAMILY + "-fx-font-size: 9px; -fx-font-weight: 800; -fx-text-fill: " + (progressPct == 100 ? GREEN : BLUE) + ";");
        progressHeader.getChildren().addAll(progLabel, prSpacer, progPctLabel);

        ProgressBar missionProgressBar = new ProgressBar(completedCount / 6.0);
        missionProgressBar.setPrefHeight(4);
        missionProgressBar.setMinHeight(4);
        missionProgressBar.setMaxHeight(4);
        missionProgressBar.setMaxWidth(Double.MAX_VALUE);
        missionProgressBar.setStyle("-fx-accent: " + (progressPct == 100 ? GREEN : BLUE) + "; -fx-control-inner-background: #F1F5F9;");
        VBox.setMargin(missionProgressBar, new Insets(0, 0, 10, 0));

        missionProgressContainer.getChildren().addAll(progressHeader, missionProgressBar);

        // STEP 1: TRIP ASSIGNED
        missionProgressContainer.getChildren().add(
            createTimelineMilestone("🚨", "Trip Assigned (" + activeTripId + ")", "Patient: " + activePatientId + " • Amb: " + activeAmbulanceId, "✓ ACTIVE", "#DCFCE7", "#15803D", true, true, false, null)
        );

        // STEP 2: HOSPITAL CONFIRMATION
        if (isHospitalConfirmed) {
            missionProgressContainer.getChildren().add(
                createTimelineMilestone("🏥", "Hospital Confirmed", "Trauma bed confirmed by " + (notifiedHospitalName.isEmpty() ? activeDestinationHospital : notifiedHospitalName), "✓ CONFIRMED", "#DCFCE7", "#15803D", true, false, false, null)
            );
        } else if (isHospitalNotified) {
            missionProgressContainer.getChildren().add(
                createTimelineMilestone("⏳", "Hospital Awaiting Confirm", "Request sent to " + (notifiedHospitalName.isEmpty() ? activeDestinationHospital : notifiedHospitalName) + " (Not confirm)", "● NOT CONFIRM", "#FEF3C7", "#D97706", false, true, false, null)
            );
        } else {
            missionProgressContainer.getChildren().add(
                createTimelineMilestone("🏥", "Hospital Confirmation", "Click 'Select Hospital' below to request trauma bed", "○ SELECT HOSP", "#F1F5F9", "#64748B", false, false, false, null)
            );
        }

        // STEP 3: ARRIVED AT PICKUP LOCATION
        CheckBox pickupCheck = new CheckBox(isPickupDone ? "✓ Arrived at Pickup Point" : "Mark as Arrived at Pickup");
        pickupCheck.setSelected(isPickupDone);
        pickupCheck.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + (isPickupDone ? "#059669" : "#0284C7") + "; -fx-cursor: hand;");
        pickupCheck.setOnAction(e -> {
            if (pickupCheck.isSelected()) {
                updateMissionProgressStatus("ARRIVED_AT_PICKUP");
            } else {
                hasAutoMarkedPickup = true;
                updateMissionProgressStatus("EN_ROUTE_TO_PICKUP");
            }
        });

        missionProgressContainer.getChildren().add(
            createTimelineMilestone("📍", "Arrived at Pickup", isPickupDone ? "✓ Reached " + activeSource : "Navigating to " + activeSource, isPickupDone ? "✓ ARRIVED" : "● EN ROUTE", isPickupDone ? "#DCFCE7" : "#EFF6FF", isPickupDone ? "#15803D" : "#0284C7", isPickupDone, !isPickupDone, false, pickupCheck)
        );

        // STEP 4: PATIENT ONBOARD & EN ROUTE
        CheckBox onboardCheck = new CheckBox(isEnRouteHospDone ? "✓ Patient Loaded & Moving" : "Mark Patient Onboard & En Route");
        onboardCheck.setSelected(isEnRouteHospDone);
        onboardCheck.setDisable(!isPickupDone);
        onboardCheck.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + (isEnRouteHospDone ? "#059669" : (isPickupDone ? "#0284C7" : "#94A3B8")) + "; -fx-cursor: hand;");
        onboardCheck.setOnAction(e -> {
            if (onboardCheck.isSelected()) {
                updateMissionProgressStatus("EN_ROUTE_TO_HOSPITAL");
                plotRouteWithDirectCoordinates(activePickupLat, activePickupLng, activeDestLat, activeDestLng, activeDestinationHospital);
            } else {
                updateMissionProgressStatus("ARRIVED_AT_PICKUP");
            }
        });

        missionProgressContainer.getChildren().add(
            createTimelineMilestone("🚑", "Patient Onboard & Moving", isEnRouteHospDone ? "✓ En route to " + activeDestinationHospital : (isPickupDone ? "Ready to load patient and move" : "Awaiting pickup arrival"), isEnRouteHospDone ? "✓ ONBOARD" : (isPickupDone ? "● READY" : "○ WAITING"), isEnRouteHospDone ? "#DCFCE7" : (isPickupDone ? "#FEF3C7" : "#F1F5F9"), isEnRouteHospDone ? "#15803D" : (isPickupDone ? "#D97706" : "#64748B"), isEnRouteHospDone, isPickupDone && !isEnRouteHospDone, false, onboardCheck)
        );

        // STEP 5: POLICE CORRIDOR ALERT (Positioned below Patient Onboard & Moving)
        if (isPoliceNotified) {
            missionProgressContainer.getChildren().add(
                createTimelineMilestone("👮", "Police Corridor Alert", "Green corridor cleared by traffic police", "✓ NOTIFIED", "#DCFCE7", "#15803D", true, false, false, null)
            );
        } else {
            Button alertPoliceBtn = new Button("👮 Alert Police Now");
            alertPoliceBtn.setStyle(FONT_FAMILY + "-fx-background-color: #EF4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 9.5px; -fx-padding: 4 9; -fx-background-radius: 5; -fx-cursor: hand;");
            alertPoliceBtn.setOnAction(e -> triggerPoliceAlert());

            missionProgressContainer.getChildren().add(
                createTimelineMilestone("👮", "Police Corridor Alert", "Traffic preemption alert pending", "● PENDING", "#FEE2E2", "#DC2626", false, true, false, alertPoliceBtn)
            );
        }

        // STEP 6: ER HANDOVER & PATIENT DELIVERED
        if (isCompleted) {
            missionProgressContainer.getChildren().add(
                createTimelineMilestone("🏁", "ER Handover & Completed", "✓ Patient delivered to emergency room team", "✓ COMPLETED", "#DCFCE7", "#15803D", true, false, true, null)
            );
        } else if (isEnRouteHospDone) {
            Button completeHandoverBtn = new Button("✓ Complete ER Handover");
            completeHandoverBtn.setMaxWidth(Double.MAX_VALUE);
            completeHandoverBtn.setStyle(FONT_FAMILY + "-fx-background-color: #10B981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 6 12; -fx-background-radius: 6; -fx-cursor: hand;");
            completeHandoverBtn.setOnAction(e -> {
                completeHandoverBtn.setDisable(true);
                updateMissionProgressStatus("COMPLETED");
                showNurseStyleToast("ER Handover Completed", "Patient successfully admitted to hospital trauma team. Mission complete.", "success", activeTripId, false);
            });

            missionProgressContainer.getChildren().add(
                createTimelineMilestone("🏁", "ER Handover", "Arrived at destination hospital? Complete mission", "● READY", "#FEF3C7", "#D97706", false, true, true, completeHandoverBtn)
            );
        } else {
            missionProgressContainer.getChildren().add(
                createTimelineMilestone("🏁", "Patient Delivered", "Pending hospital arrival & ER handover", "○ PENDING", "#F1F5F9", "#64748B", false, false, true, null)
            );
        }
    }

    private VBox createTimelineMilestone(String icon, String title, String subtitle, 
                                        String badgeText, String badgeBg, String badgeFg, 
                                        boolean isDone, boolean isActive, boolean isLast, Node control) {
        VBox wrapper = new VBox(0);

        HBox row = new HBox(10);
        row.setAlignment(Pos.TOP_LEFT);

        // LEFT COLUMN: Circle Node Icon + Track Line
        VBox leftColumn = new VBox(0);
        leftColumn.setAlignment(Pos.TOP_CENTER);
        leftColumn.setPrefWidth(28);
        leftColumn.setMinWidth(28);

        StackPane circle = new StackPane();
        circle.setPrefSize(28, 28);
        circle.setMinSize(28, 28);
        circle.setMaxSize(28, 28);

        String circleBg = isDone ? "#ECFDF5" : (isActive ? "#EFF6FF" : "#F8FAFC");
        String circleBorder = isDone ? "#10B981" : (isActive ? "#0284C7" : "#CBD5E1");
        String dropEffect = isDone 
                ? "-fx-effect: dropshadow(gaussian, rgba(16,185,129,0.30), 6, 0, 0, 1);" 
                : (isActive ? "-fx-effect: dropshadow(gaussian, rgba(2,132,199,0.35), 6, 0, 0, 1);" : "");

        circle.setStyle("-fx-background-color: " + circleBg + "; -fx-border-color: " + circleBorder + "; -fx-border-width: 1.8px; -fx-background-radius: 50%; -fx-border-radius: 50%; " + dropEffect);

        Label iconLabel = new Label(isDone ? "✓" : icon);
        iconLabel.setStyle(FONT_FAMILY + "-fx-font-size: " + (isDone ? "11px" : "11.5px") + "; -fx-font-weight: bold; -fx-text-fill: " + (isDone ? "#059669" : (isActive ? "#0284C7" : "#64748B")) + ";");
        circle.getChildren().add(iconLabel);

        leftColumn.getChildren().add(circle);

        if (!isLast) {
            Region line = new Region();
            line.setPrefWidth(2);
            line.setMinWidth(2);
            line.setMaxWidth(2);
            line.setPrefHeight(control != null ? 48 : 34);
            line.setStyle("-fx-background-color: " + (isDone ? "#10B981" : "#E2E8F0") + ";");
            VBox.setVgrow(line, Priority.ALWAYS);
            leftColumn.getChildren().add(line);
        }

        // RIGHT COLUMN: Content Card
        VBox rightCard = new VBox(4);
        HBox.setHgrow(rightCard, Priority.ALWAYS);
        rightCard.setPadding(new Insets(1, 4, isLast ? 4 : 14, 0));

        HBox titleRow = new HBox(4);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Label titleLbl = new Label(title);
        titleLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: " + (isDone || isActive ? "800" : "600") + "; -fx-text-fill: " + (isDone ? "#0F172A" : (isActive ? "#0369A1" : "#475569")) + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label badgeLbl = new Label(badgeText);
        badgeLbl.setStyle(FONT_FAMILY + "-fx-font-size: 8px; -fx-font-weight: bold; -fx-text-fill: " + badgeFg + "; -fx-background-color: " + badgeBg + "; -fx-border-color: " + badgeFg + "33; -fx-border-width: 0.8px; -fx-border-radius: 5px; -fx-padding: 2 6; -fx-background-radius: 5px;");

        titleRow.getChildren().addAll(titleLbl, spacer, badgeLbl);

        Label subLbl = new Label(subtitle);
        subLbl.setWrapText(true);
        subLbl.setStyle(FONT_FAMILY + "-fx-font-size: 9.5px; -fx-text-fill: " + (isDone ? "#059669" : (isActive ? "#0284C7" : "#64748B")) + ";");

        rightCard.getChildren().addAll(titleRow, subLbl);

        if (control != null) {
            VBox.setMargin(control, new Insets(4, 0, 0, 0));
            rightCard.getChildren().add(control);
        }

        row.getChildren().addAll(leftColumn, rightCard);
        VBox.setVgrow(row, Priority.ALWAYS);
        VBox.setVgrow(wrapper, Priority.ALWAYS);
        wrapper.getChildren().add(row);
        return wrapper;
    }

    // =========================================================
    // AI RECOMMENDED HOSPITALS (PREVIOUS CLEAN CARD STYLING)
    // =========================================================
    private HBox createHospitalArea() {
        HBox area = new HBox(18);
        area.setFillHeight(true);

        VBox recommendedSection = createRecommendedHospitalsSection();
        HBox.setHgrow(recommendedSection, Priority.ALWAYS);
        area.getChildren().addAll(recommendedSection);

        return area;
    }

    private VBox createRecommendedHospitalsSection() {
        VBox section = new VBox(12);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        VBox heading = new VBox(2);
        Label title = new Label("LifeLink Recommended Hospitals");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 15.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT + ";");

        // hospitalSubtitleLabel = new Label("Top 5 hospitals sorted by proximity to emergency destination & live capacities");
        // hospitalSubtitleLabel.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-text-fill: " + MUTED + ";");
        heading.getChildren().addAll(title);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        hospitalCountLabel = new Label("Loading Hospitals...");
        hospitalCountLabel.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-font-weight: bold; -fx-text-fill: " + BLUE + "; -fx-background-color: " + LIGHT_BLUE + "; -fx-padding: 5 10; -fx-background-radius: 8;");
        header.getChildren().addAll(heading, spacer, hospitalCountLabel);

        hospitalCardsContainer = new HBox(14);
        hospitalCardsContainer.setPadding(new Insets(2, 3, 8, 2));

        ScrollPane horizontalScroll = new ScrollPane(hospitalCardsContainer);
        horizontalScroll.setFitToHeight(true);
        horizontalScroll.setFitToWidth(false);
        horizontalScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        horizontalScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        horizontalScroll.setPannable(true);
        horizontalScroll.setPrefHeight(275);
        horizontalScroll.setMinHeight(275);
        horizontalScroll.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-background: transparent; " +
            "-fx-border-color: transparent; " +
            "-fx-focus-color: transparent; " +
            "-fx-faint-focus-color: transparent;"
        );

        // Smooth mouse wheel horizontal scrolling without visible scrollbar
        horizontalScroll.setOnScroll(event -> {
            if (event.getDeltaY() != 0) {
                double delta = event.getDeltaY();
                double width = hospitalCardsContainer.getBoundsInLocal().getWidth();
                if (width > 0) {
                    double step = (delta / width) * 2.5;
                    horizontalScroll.setHvalue(Math.min(1.0, Math.max(0.0, horizontalScroll.getHvalue() - step)));
                }
                event.consume();
            }
        });

        section.getChildren().addAll(header, horizontalScroll);
        return section;
    }

    private void showHospitalShimmerLoading() {
        Platform.runLater(() -> {
            if (hospitalCardsContainer == null) return;
            hospitalCardsContainer.getChildren().clear();
            if (hospitalCountLabel != null) {
                hospitalCountLabel.setText("Scanning ICU & OT capacities...");
            }
            for (int i = 0; i < 5; i++) {
                hospitalCardsContainer.getChildren().add(createHospitalSkeletonCard());
            }
        });
    }

    private Node createHospitalSkeletonCard() {
        VBox skeleton = new VBox(11);
        skeleton.setPrefWidth(260);
        skeleton.setMinWidth(260);
        skeleton.setMaxWidth(260);
        skeleton.setPadding(new Insets(15));
        skeleton.setStyle(
            "-fx-background-color: " + SURFACE + "; " +
            "-fx-border-color: " + BORDER + "; " +
            "-fx-border-width: 1px; " +
            "-fx-background-radius: 12px; " +
            "-fx-border-radius: 12px;"
        );

        // Header: rank badge & status badge
        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);
        Region rankBar = ShimmerLoader.createBar(70, 18, 6);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Region badgeBar = ShimmerLoader.createBar(85, 18, 6);
        topRow.getChildren().addAll(rankBar, spacer, badgeBar);

        // Name row
        HBox nameRow = new HBox(10);
        nameRow.setAlignment(Pos.CENTER_LEFT);
        Region iconBar = ShimmerLoader.createBar(34, 34, 8);
        VBox nameBox = new VBox(4);
        Region nameBar = ShimmerLoader.createBar(120, 13, 5);
        Region subBar = ShimmerLoader.createBar(80, 10, 4);
        nameBox.getChildren().addAll(nameBar, subBar);
        nameRow.getChildren().addAll(iconBar, nameBox);

        Region div = new Region();
        div.setPrefHeight(1);
        div.setStyle("-fx-background-color: #EEF1F6;");

        // Metrics grid: 4 boxes
        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.add(ShimmerLoader.createBar(110, 36, 6), 0, 0);
        grid.add(ShimmerLoader.createBar(110, 36, 6), 1, 0);
        grid.add(ShimmerLoader.createBar(110, 36, 6), 0, 1);
        grid.add(ShimmerLoader.createBar(110, 36, 6), 1, 1);

        // Action buttons
        HBox actions = new HBox(8);
        actions.getChildren().addAll(
            ShimmerLoader.createBar(110, 32, 7),
            ShimmerLoader.createBar(110, 32, 7)
        );

        skeleton.getChildren().addAll(topRow, nameRow, div, grid, actions);
        return new ShimmerLoader.ShimmerPane(skeleton, 260, 245);
    }

    // =========================================================
    // INITIALIZE VOICE COMMAND SERVICE
    // =========================================================
    private void initializeVoiceCommandService() {
        voiceCommandService =
                new DriverVoiceCommandService(
                        new DriverVoiceCommandService.VoiceCommandListener() {

                            @Override
                            public void onVoiceStarted() {
                                Platform.runLater(() -> {
                                    if (voiceCommandButton != null) {
                                        voiceControlEnabled = true;
                                        updateVoiceControlToggle();
                                    }
                                    updateVoiceStatus("Listening...", GREEN);
                                });
                            }

                            @Override
                            public void onVoiceStatus(
                                    String message,
                                    DriverVoiceCommandService.VoiceStatus status
                            ) {
                                String color = BLUE;
                                if (status == DriverVoiceCommandService.VoiceStatus.SUCCESS) {
                                    color = GREEN;
                                } else if (status == DriverVoiceCommandService.VoiceStatus.WARNING) {
                                    color = ORANGE;
                                } else if (status == DriverVoiceCommandService.VoiceStatus.ERROR) {
                                    color = RED;
                                }
                                updateVoiceStatus(message, color);
                            }

                            // =========================================================
                            // POLICE VOICE COMMAND
                            // =========================================================
                            @Override
                            public void onPoliceCommand() {
                                Platform.runLater(() -> {
                                    System.out.println("[Voice] Police command received");
                                    System.out.println("[Voice] isPoliceNotified = " + isPoliceNotified);

                                    // Police was actually notified earlier
                                    if (isPoliceNotified) {
                                        updateVoiceStatus("Police already alerted", ORANGE);
                                        return;
                                    }

                                    // Notify police
                                    triggerPoliceAlert();
                                    updateVoiceStatus("Police notified • Green Corridor requested", GREEN);
                                });
                            }

                            // =========================================================
                            // HOSPITAL SELECT VOICE COMMAND
                            // =========================================================
                            @Override
                            public void onHospitalSelectCommand(String hospitalKey) {
                                Platform.runLater(() -> {
                                    String targetKey = (hospitalKey != null) ? hospitalKey.trim() : "";
                                    System.out.println("[Voice] Hospital select command: " + targetKey);
                                    System.out.println("[Voice] isHospitalNotified = " + isHospitalNotified + ", notifiedHospitalName = " + notifiedHospitalName);

                                    // Hospital must be notified first
                                    if (!isHospitalNotified) {
                                        updateVoiceStatus("Notify hospital first", ORANGE);
                                        return;
                                    }

                                    Button targetButton = null;
                                    String selectedHospitalName = null;

                                    String normNotified = (notifiedHospitalName != null)
                                            ? DriverVoiceCommandService.normalizeHospitalName(notifiedHospitalName)
                                            : "";
                                    String normTarget = DriverVoiceCommandService.normalizeHospitalName(targetKey);

                                    boolean targetMatchesNotified = !normNotified.isEmpty() && (
                                            targetKey.isEmpty() ||
                                            targetKey.equalsIgnoreCase("hospital") ||
                                            normTarget.isEmpty() ||
                                            normNotified.equals(normTarget) ||
                                            normNotified.contains(normTarget) ||
                                            normTarget.contains(normNotified) ||
                                            (notifiedHospitalName != null && targetKey.toLowerCase().contains(notifiedHospitalName.toLowerCase()))
                                    );

                                    if (targetMatchesNotified) {
                                        targetButton = hospitalSelectButtons.get(normNotified);
                                        if (targetButton == null && notifiedHospitalName != null) {
                                            targetButton = hospitalSelectButtons.get(notifiedHospitalName.toLowerCase().trim());
                                        }
                                        if (targetButton == null) {
                                            targetButton = findBestMatchingHospitalButton(normNotified, hospitalSelectButtons);
                                        }
                                        if (targetButton != null) {
                                            selectedHospitalName = notifiedHospitalName;
                                            targetKey = normNotified;
                                        }
                                    }

                                    if (targetButton == null && !targetKey.isEmpty()) {
                                        targetButton = hospitalSelectButtons.get(targetKey);
                                    }
                                    if (targetButton == null && !normTarget.isEmpty()) {
                                        targetButton = hospitalSelectButtons.get(normTarget);
                                        if (targetButton != null) targetKey = normTarget;
                                    }
                                    if (targetButton == null && !targetKey.isEmpty()) {
                                        targetButton = findBestMatchingHospitalButton(targetKey, hospitalSelectButtons);
                                    }
                                    if (targetButton == null && notifiedHospitalName != null && !notifiedHospitalName.isEmpty()) {
                                        targetButton = hospitalSelectButtons.get(normNotified);
                                        if (targetButton == null) {
                                            targetButton = findBestMatchingHospitalButton(normNotified, hospitalSelectButtons);
                                        }
                                        if (targetButton != null) {
                                            selectedHospitalName = notifiedHospitalName;
                                            targetKey = normNotified;
                                        }
                                    }

                                    if (targetButton == null) {
                                        updateVoiceStatus("Hospital button unavailable", RED);
                                        return;
                                    }

                                    System.out.println("[Voice] Firing hospital select button for: " + targetKey);
                                    targetButton.fire();
                                    System.out.println("[Voice] Hospital select button fired successfully");

                                    if (selectedHospitalName == null) {
                                        HospitalEntry hospital = displayedHospitalEntries.get(targetKey);
                                        selectedHospitalName = (hospital != null) ? hospital.name : targetKey;
                                    }

                                    updateVoiceStatus("Selected " + selectedHospitalName, GREEN);
                                });
                            }

                            // =========================================================
                            // HOSPITAL NOTIFY VOICE COMMAND
                            // =========================================================
                            @Override
                            public void onHospitalNotifyCommand(String hospitalKey) {
                                Platform.runLater(() -> {
                                    String targetKey = (hospitalKey != null) ? hospitalKey.trim() : "";
                                    System.out.println("[Voice] Hospital notify command: " + targetKey);
                                    System.out.println("[Voice] Available hospital notify buttons: " + hospitalNotifyButtons.keySet());

                                    Button targetButton = hospitalNotifyButtons.get(targetKey);
                                    String normalizedKey = DriverVoiceCommandService.normalizeHospitalName(targetKey);
                                    if (targetButton == null && !normalizedKey.isEmpty()) {
                                        targetButton = hospitalNotifyButtons.get(normalizedKey);
                                        if (targetButton != null) {
                                            targetKey = normalizedKey;
                                        }
                                    }

                                    if (targetButton == null) {
                                        targetButton = findBestMatchingHospitalButton(targetKey, hospitalNotifyButtons);
                                    }

                                    if (targetButton == null) {
                                        System.out.println("[Voice] ERROR: Notify button not found for " + targetKey);
                                        updateVoiceStatus("Hospital button unavailable", RED);
                                        return;
                                    }

                                    System.out.println("[Voice] Notify button found");

                                    if (targetButton.isDisabled()) {
                                        System.out.println("[Voice] Target hospital button is already disabled/notified");
                                        updateVoiceStatus("Hospital already notified", ORANGE);
                                        return;
                                    }

                                    System.out.println("[Voice] Firing hospital notify button");
                                    targetButton.fire();
                                    System.out.println("[Voice] Hospital notify button fired");

                                    HospitalEntry hospital = displayedHospitalEntries.get(targetKey);
                                    String dispName = (hospital != null) ? hospital.name : targetKey;

                                    updateVoiceStatus("Notifying " + dispName, GREEN);
                                });
                            }

                            // =========================================================
                            // ASK RECOMMENDED HOSPITAL VOICE COMMAND
                            // =========================================================
                            private long lastAskHospitalTimestamp = 0;

                            @Override
                            public void onAskRecommendedHospitalCommand() {
                                Platform.runLater(() -> {
                                    long now = System.currentTimeMillis();
                                    if (now - lastAskHospitalTimestamp < 4000) {
                                        System.out.println("[Voice] Debouncing onAskRecommendedHospitalCommand (too frequent)");
                                        return;
                                    }
                                    lastAskHospitalTimestamp = now;

                                    System.out.println("[Voice] Handling onAskRecommendedHospitalCommand");

                                    // 1. Check if an active emergency exists
                                    if (!isAssigned) {
                                        updateVoiceStatus("No active emergency", ORANGE);
                                        SarvamTTSService.speak("There is no active emergency assigned at the moment.");
                                        return;
                                    }

                                    // 2. Retrieve actual recommended hospital from existing logic
                                    String targetHospital = "";

                                    if (currentRecommendedHospitalName != null && !currentRecommendedHospitalName.trim().isEmpty()) {
                                        targetHospital = currentRecommendedHospitalName.trim();
                                    } else if (!displayedHospitalEntries.isEmpty()) {
                                        HospitalEntry firstEntry = displayedHospitalEntries.values().iterator().next();
                                        if (firstEntry != null && firstEntry.name != null && !firstEntry.name.trim().isEmpty()) {
                                            targetHospital = firstEntry.name.trim();
                                        }
                                    }

                                    if (targetHospital.isEmpty() && activeDestinationHospital != null && !activeDestinationHospital.trim().isEmpty()) {
                                        targetHospital = activeDestinationHospital.trim();
                                    }

                                    if (!targetHospital.isEmpty()) {
                                        System.out.println("[Voice] Announcing recommended hospital: " + targetHospital);
                                        updateVoiceStatus("Recommended: " + targetHospital, GREEN);
                                        SarvamTTSService.speak(targetHospital + " is recommended for this emergency.");
                                    } else {
                                        System.out.println("[Voice] No hospital recommendation available yet.");
                                        updateVoiceStatus("Loading hospitals...", ORANGE);
                                        SarvamTTSService.speak("Hospitals are currently being loaded. Please ask again in a moment.");
                                    }
                                });
                            }

                            @Override
                            public void onVoiceFinished() {
                                Platform.runLater(() -> {
                                    updateVoiceControlToggle();
                                    if (voiceControlEnabled) {
                                        new Thread(() -> {
                                            try {
                                                Thread.sleep(4000);
                                            } catch (InterruptedException ignored) {}
                                            Platform.runLater(() -> {
                                                if (voiceControlEnabled) {
                                                    updateVoiceStatus("Say \"Hey LifeLink\"", GREEN);
                                                }
                                            });
                                        }).start();
                                    }
                                });
                            }
                        }
                );
    }

    private Button findBestMatchingHospitalButton(String searchKey, Map<String, Button> buttonMap) {
        if (searchKey == null || searchKey.trim().isEmpty() || buttonMap == null || buttonMap.isEmpty()) {
            return null;
        }

        String norm = DriverVoiceCommandService.normalizeHospitalName(searchKey);
        if (norm.isEmpty()) {
            norm = searchKey.toLowerCase().trim();
        }

        if (buttonMap.containsKey(norm)) {
            return buttonMap.get(norm);
        }
        if (buttonMap.containsKey(searchKey.toLowerCase().trim())) {
            return buttonMap.get(searchKey.toLowerCase().trim());
        }

        Button bestButton = null;
        int bestScore = 0;

        for (Map.Entry<String, Button> entry : buttonMap.entrySet()) {
            String key = entry.getKey();
            if (key == null || key.isEmpty()) continue;

            int score = 0;
            if (key.equalsIgnoreCase(norm)) {
                score = 1000;
            } else if (key.contains(norm) || norm.contains(key)) {
                score = 500;
            } else {
                String[] keyWords = key.split(" ");
                String[] searchWords = norm.split(" ");

                for (String sw : searchWords) {
                    if (sw.length() < 3 || DriverVoiceCommandService.isStopWord(sw)) continue;
                    for (String kw : keyWords) {
                        if (kw.length() < 3 || DriverVoiceCommandService.isStopWord(kw)) continue;
                        if (kw.equalsIgnoreCase(sw)) {
                            score += (sw.length() * 20);
                        } else if (kw.contains(sw) || sw.contains(kw)) {
                            score += (sw.length() * 10);
                        }
                    }
                }
            }

            if (score > bestScore) {
                bestScore = score;
                bestButton = entry.getValue();
            }
        }

        return (bestScore > 0) ? bestButton : null;
    }

    // =========================================================
    // START VOICE COMMAND
    // =========================================================
    private void startVoiceCommand() {
        if (!voiceControlEnabled) {
            return;
        }

        if (voiceCommandService == null) {
            initializeVoiceCommandService();
        }

        voiceCommandService.startVoiceCommand(
                new HashSet<>(displayedHospitalEntries.keySet())
        );
    }

    // =========================================================
    // UPDATE VOICE CONTROL TOGGLE UI
    // =========================================================
    private void updateVoiceControlToggle() {
        if (voiceCommandButton == null) {
            return;
        }

        if (voiceControlEnabled) {
            voiceCommandButton.setText("      ON  ●");
            voiceCommandButton.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + GREEN + ";" +
                    "-fx-text-fill: white;" +
                    "-fx-font-size: 10px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 15;" +
                    "-fx-border-radius: 15;" +
                    "-fx-padding: 0 7 0 7;" +
                    "-fx-cursor: hand;"
            );
        } else {
            voiceCommandButton.setText("●  OFF");
            voiceCommandButton.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + PAGE_BG + ";" +
                    "-fx-text-fill: " + SECONDARY + ";" +
                    "-fx-font-size: 10px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 15;" +
                    "-fx-border-radius: 15;" +
                    "-fx-border-color: " + BORDER + ";" +
                    "-fx-padding: 0 7 0 7;" +
                    "-fx-cursor: hand;"
            );
        }
    }

    // =========================================================
    // UPDATE VOICE STATUS
    // =========================================================
    private void updateVoiceStatus(String message, String color) {
        Platform.runLater(() -> {
            if (voiceStatusLabel != null) {
                voiceStatusLabel.setText(message);
                voiceStatusLabel.setStyle(
                        FONT_FAMILY +
                        "-fx-font-size: 10px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + color + ";"
                );
            }
        });
    }

    private VBox createHospitalCard(HospitalEntry h, int rank) {
        VBox card = new VBox(11);
        card.setPrefWidth(260);
        card.setMinWidth(260);
        card.setMaxWidth(260);
        card.setPadding(new Insets(15));
        card.setStyle(
            "-fx-background-color: " + SURFACE + "; " +
            "-fx-border-color: " + BORDER + "; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 12; " +
            "-fx-background-radius: 12; " +
            "-fx-effect: dropshadow(gaussian, rgba(30,60,90,0.06), 8, 0, 0, 2); " +
            "-fx-cursor: hand;"
        );

        // Header: Rank Badge + Status Badge
        HBox topBadgeRow = new HBox(8);
        topBadgeRow.setAlignment(Pos.CENTER_LEFT);

        Label rankBadge = new Label(rank == 1 ? "★ #1 BEST MATCH" : ("#" + rank + " NEAREST"));
        rankBadge.setStyle(
            FONT_FAMILY + "-fx-font-size: 8.5px; -fx-font-weight: 800; " +
            "-fx-text-fill: " + (rank == 1 ? BLUE : TEXT) + "; " +
            "-fx-background-color: " + (rank == 1 ? LIGHT_BLUE : "#F1F5F9") + "; " +
            "-fx-padding: 3 7; -fx-background-radius: 6px;"
        );

        Region badgeSpacer = new Region();
        HBox.setHgrow(badgeSpacer, Priority.ALWAYS);

        // Status Badge based on live capacity
        String statusText;
        String statusBg;
        String statusFg;
        if (h.availableICUBeds > 0 && h.availableOTCount > 0) {
            statusText = "● ICU & OT READY";
            statusBg = LIGHT_GREEN;
            statusFg = GREEN;
        } else if (h.availableICUBeds == 0) {
            statusText = "● ICU FULL";
            statusBg = LIGHT_RED;
            statusFg = RED;
        } else if (h.availableOTCount == 0) {
            statusText = "● OT BUSY";
            statusBg = LIGHT_ORANGE;
            statusFg = ORANGE;
        } else {
            statusText = "● " + h.status;
            statusBg = LIGHT_BLUE;
            statusFg = BLUE;
        }

        Label statusBadge = new Label(statusText);
        statusBadge.setStyle(FONT_FAMILY + "-fx-background-color: " + statusBg + "; -fx-text-fill: " + statusFg + "; -fx-font-size: 8px; -fx-font-weight: bold; -fx-padding: 3 7; -fx-background-radius: 6px;");

        topBadgeRow.getChildren().addAll(rankBadge, badgeSpacer, statusBadge);

        // Hospital Name & Address Row
        HBox nameRow = new HBox(10);
        nameRow.setAlignment(Pos.CENTER_LEFT);

        StackPane hospIcon = new StackPane();
        hospIcon.setPrefSize(34, 34);
        hospIcon.setMinSize(34, 34);
        hospIcon.setStyle("-fx-background-color: " + LIGHT_BLUE + "; -fx-background-radius: 8;");
        Label icon = new Label("🏥");
        icon.setStyle("-fx-font-size: 15px;");
        hospIcon.getChildren().add(icon);

        VBox nameBox = new VBox(2);
        HBox.setHgrow(nameBox, Priority.ALWAYS);

        Label hospitalName = new Label(h.name);
        hospitalName.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT + ";");
        hospitalName.setWrapText(false);

        Label addressLabel = new Label(h.address);
        addressLabel.setStyle(FONT_FAMILY + "-fx-font-size: 9.5px; -fx-text-fill: " + MUTED + ";");
        nameBox.getChildren().addAll(hospitalName, addressLabel);

        nameRow.getChildren().addAll(hospIcon, nameBox);

        // Divider
        Region divider = new Region();
        divider.setPrefHeight(1);
        divider.setStyle("-fx-background-color: #EEF1F6;");

        // Four Metric Boxes (DISTANCE / ETA, ICU BEDS, OT ROOMS, DOCTORS)
        GridPane metricsGrid = new GridPane();
        metricsGrid.setHgap(8);
        metricsGrid.setVgap(8);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(50);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(50);
        metricsGrid.getColumnConstraints().addAll(c1, c2);

        String distVal = h.distanceDisplay + " (" + h.etaDisplay + ")";
        VBox distanceBox = createMetricBox("DISTANCE (ETA)", distVal, BLUE);

        String icuVal = h.availableICUBeds > 0 ? (h.availableICUBeds + " Available") : "0 Beds (Full)";
        String icuColor = h.availableICUBeds > 0 ? GREEN : RED;
        VBox bedsBox = createMetricBox("ICU BEDS", icuVal, icuColor);

        String otVal = h.availableOTCount > 0 ? (h.availableOTCount + " Ready") : "0 Ready";
        String otColor = h.availableOTCount > 0 ? GREEN : ORANGE;
        VBox otBox = createMetricBox("OT ROOMS", otVal, otColor);

        String docVal = h.notBusyDoctorsCount + " On Duty";
        VBox doctorsBox = createMetricBox("DOCTORS", docVal, TEXT);

        metricsGrid.add(distanceBox, 0, 0);
        metricsGrid.add(bedsBox, 1, 0);
        metricsGrid.add(otBox, 0, 1);
        metricsGrid.add(doctorsBox, 1, 1);

        // Action Buttons
        HBox actions = new HBox(8);

        Button directionButton = new Button("Select");
        directionButton.setMaxWidth(Double.MAX_VALUE);
        directionButton.setPrefHeight(32);
        HBox.setHgrow(directionButton, Priority.ALWAYS);
        directionButton.setStyle(FONT_FAMILY + "-fx-background-color: " + BLUE + "; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 7; -fx-cursor: hand;");

        Button selectButton = new Button("Notify");

        String hospitalVoiceKey = DriverVoiceCommandService.normalizeHospitalName(h.name);

        hospitalSelectButtons.put(hospitalVoiceKey, directionButton);
        hospitalNotifyButtons.put(hospitalVoiceKey, selectButton);
        displayedHospitalEntries.put(hospitalVoiceKey, h);

        // Also register full lowercased hospital name and common root aliases for instantaneous matching
        String lowerName = (h.name != null) ? h.name.toLowerCase().trim() : "";
        if (!lowerName.isEmpty()) {
            hospitalSelectButtons.put(lowerName, directionButton);
            hospitalNotifyButtons.put(lowerName, selectButton);
            displayedHospitalEntries.put(lowerName, h);

            if (lowerName.contains("kem")) {
                hospitalSelectButtons.put("kem", directionButton);
                hospitalNotifyButtons.put("kem", selectButton);
                displayedHospitalEntries.put("kem", h);
            }
            if (lowerName.contains("pulse")) {
                hospitalSelectButtons.put("pulse", directionButton);
                hospitalNotifyButtons.put("pulse", selectButton);
                displayedHospitalEntries.put("pulse", h);
            }
            if (lowerName.contains("navale") || lowerName.contains("kashibai") || lowerName.contains("sknmcgh")) {
                hospitalSelectButtons.put("navale", directionButton);
                hospitalNotifyButtons.put("navale", selectButton);
                displayedHospitalEntries.put("navale", h);
            }
            if (lowerName.contains("sahyadri")) {
                hospitalSelectButtons.put("sahyadri", directionButton);
                hospitalNotifyButtons.put("sahyadri", selectButton);
                displayedHospitalEntries.put("sahyadri", h);
            }
            if (lowerName.contains("poona") || lowerName.contains("puna")) {
                hospitalSelectButtons.put("poona", directionButton);
                hospitalNotifyButtons.put("poona", selectButton);
                displayedHospitalEntries.put("poona", h);
            }
            if (lowerName.contains("bharati") || lowerName.contains("vidyapeeth")) {
                hospitalSelectButtons.put("bharati", directionButton);
                hospitalNotifyButtons.put("bharati", selectButton);
                displayedHospitalEntries.put("bharati", h);
            }
            if (lowerName.contains("inamdar")) {
                hospitalSelectButtons.put("inamdar", directionButton);
                hospitalNotifyButtons.put("inamdar", selectButton);
                displayedHospitalEntries.put("inamdar", h);
            }
            if (lowerName.contains("galaxy")) {
                hospitalSelectButtons.put("galaxy", directionButton);
                hospitalNotifyButtons.put("galaxy", selectButton);
                displayedHospitalEntries.put("galaxy", h);
            }
            if (lowerName.contains("noble") || lowerName.contains("nobel") || lowerName.contains("ojas")) {
                hospitalSelectButtons.put("noble", directionButton);
                hospitalNotifyButtons.put("noble", selectButton);
                displayedHospitalEntries.put("noble", h);
            }
            if (lowerName.contains("ruby")) {
                hospitalSelectButtons.put("ruby", directionButton);
                hospitalNotifyButtons.put("ruby", selectButton);
                displayedHospitalEntries.put("ruby", h);
            }
            if (lowerName.contains("sancheti")) {
                hospitalSelectButtons.put("sancheti", directionButton);
                hospitalNotifyButtons.put("sancheti", selectButton);
                displayedHospitalEntries.put("sancheti", h);
            }
            if (lowerName.contains("silverbirch") || lowerName.contains("silver birch")) {
                hospitalSelectButtons.put("silverbirch", directionButton);
                hospitalNotifyButtons.put("silverbirch", selectButton);
                displayedHospitalEntries.put("silverbirch", h);
            }
            if (lowerName.contains("morya")) {
                hospitalSelectButtons.put("morya", directionButton);
                hospitalNotifyButtons.put("morya", selectButton);
                displayedHospitalEntries.put("morya", h);
            }
        }

        selectButton.setMaxWidth(Double.MAX_VALUE);
        selectButton.setPrefHeight(32);
        HBox.setHgrow(selectButton, Priority.ALWAYS);
        selectButton.setStyle(FONT_FAMILY + "-fx-background-color: " + LIGHT_BLUE + "; -fx-text-fill: " + BLUE + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 7; -fx-cursor: hand;");

        directionButton.setOnAction(e -> {
            directionButton.setText("✓ Navigating");
            directionButton.setStyle(FONT_FAMILY + "-fx-background-color: " + GREEN + "; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 7;");

            // Lock hospital re-fetching so cards below are never fetched or refreshed again
            isDirectionChosen = true;
            if (hospitalListener != null) {
                try { hospitalListener.remove(); } catch (Exception ignored) {}
                hospitalListener = null;
            }

            // The emergency pickup location / source for navigating to the hospital becomes Swargate
            String newSource = "Swargate";
            Double newPickupLat = 18.5011;
            Double newPickupLng = 73.8627;

            if (activeDestinationHospital != null && !activeDestinationHospital.trim().isEmpty() 
                    && !activeDestinationHospital.equalsIgnoreCase(h.name) 
                    && !activeDestinationHospital.equalsIgnoreCase("Katraj")
                    && !isKnownHospitalId(activeDestinationHospital)) {
                newSource = activeDestinationHospital;
                if (activeDestLat != null && activeDestLat != 0.0) {
                    newPickupLat = activeDestLat;
                    newPickupLng = activeDestLng;
                } else {
                    double[] coords = resolveKnownCoordinates(newSource);
                    newPickupLat = coords[0];
                    newPickupLng = coords[1];
                }
            } else if (activeSource != null && activeSource.toLowerCase().contains("swargate")) {
                newSource = "Swargate";
                newPickupLat = 18.5011;
                newPickupLng = 73.8627;
            }

            activeSource = newSource;
            activePickupLat = newPickupLat;
            activePickupLng = newPickupLng;
            targetPickupLat = newPickupLat;
            targetPickupLng = newPickupLng;

            activeDestinationHospital = h.name;
            activeDestLat = h.hospLat;
            activeDestLng = h.hospLng;
            currentMissionStatus = "EN_ROUTE_TO_HOSPITAL";

            // Update UI components immediately with new source (Swargate) and new destination (e.g. KEM Hospital)
            updateMissionProgressUI();

            Node updatedCard = createDynamicActiveEmergencyCard(activeTripId, activePatientId, activeSource, activeDestinationHospital, "HIGH", "Emergency Transport Protocol Active", currentMissionStatus);
            updateEmergencyCardView(updatedCard);

            // Direct coordinates routing on map: Swargate (lat, lng) to selected hospital (lat, lng)
            plotRouteWithDirectCoordinates(activePickupLat, activePickupLng, h.hospLat, h.hospLng, h.name);

            // Audio confirmation on successful hospital selection
            SarvamTTSService.speak("Hospital selected.");

            new Thread(() -> {
                try {
                    Firestore db = FirebaseConfig.getFirestore();
                    if (db != null) {
                        String notifId = "NOTIF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                        Map<String, Object> hospitalNotification = new HashMap<>();
                        hospitalNotification.put("notificationId", notifId);
                        hospitalNotification.put("timestamp", Timestamp.now());

                        String hospEmail = (h.email != null && !h.email.trim().isEmpty()) 
                                ? h.email.trim() 
                                : (h.id != null && h.id.contains("@") ? h.id : h.name.toLowerCase().replaceAll("\\s+", "") + "@hospital.com");
                        hospitalNotification.put("hospitalemail", hospEmail);
                        hospitalNotification.put("hospitalEmail", hospEmail);
                        hospitalNotification.put("hospitalName", h.name);
                        hospitalNotification.put("hospitalAddress", h.address);
                        hospitalNotification.put("hospitalId", h.id);

                        hospitalNotification.put("driverID", loggedInDriverEmail.trim());
                        hospitalNotification.put("driveremail", loggedInDriverEmail.trim());
                        hospitalNotification.put("tripID", activeTripId.isEmpty() ? "TRIP-AUTO" : activeTripId);
                        hospitalNotification.put("TripID", activeTripId.isEmpty() ? "TRIP-AUTO" : activeTripId);
                        hospitalNotification.put("patientID", activePatientId.isEmpty() ? "PAT-AUTO" : activePatientId);
                        hospitalNotification.put("patID", activePatientId.isEmpty() ? "PAT-AUTO" : activePatientId);
                        hospitalNotification.put("pickupLocation", activeSource);
                        hospitalNotification.put("source", activeSource);
                        hospitalNotification.put("destination", h.name);
                        hospitalNotification.put("ambulanceId", (activeAmbulanceId != null && !activeAmbulanceId.isEmpty()) ? activeAmbulanceId : "AMB-101");
                        hospitalNotification.put("ambulanceID", (activeAmbulanceId != null && !activeAmbulanceId.isEmpty()) ? activeAmbulanceId : "AMB-101");
                        hospitalNotification.put("nurseemail", (activeNurseEmail != null && !activeNurseEmail.isEmpty()) ? activeNurseEmail : "nurse@lifelink.com");
                        hospitalNotification.put("status", "EN_ROUTE_TO_HOSPITAL");
                        hospitalNotification.put("message", "Ambulance en route with patient to " + h.name + ". Emergency corridor active.");
                        if (h.hospLat != null && h.hospLng != null) {
                            hospitalNotification.put("destLat", h.hospLat);
                            hospitalNotification.put("destLng", h.hospLng);
                        }
                        if (activePickupLat != null && activePickupLng != null) {
                            hospitalNotification.put("pickupLat", activePickupLat);
                            hospitalNotification.put("pickupLng", activePickupLng);
                        }

                        db.collection("driverToHospitalNotify").document(notifId).set(hospitalNotification).get();

                        // 2. Update patient location as source and selected hospital as destination in "adminEmergencyRequests" collection
                        if (!activeDocId.isEmpty()) {
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("source", activeSource);
                            updates.put("pickupLocation", activeSource);
                            updates.put("destination", h.name);
                            updates.put("status", "EN_ROUTE_TO_HOSPITAL");
                            updates.put("lastUpdated", Timestamp.now());
                            if (h.hospLat != null && h.hospLng != null) {
                                updates.put("destLat", h.hospLat);
                                updates.put("destLng", h.hospLng);
                                updates.put("latitude", h.hospLat);
                                updates.put("longitude", h.hospLng);
                            }
                            if (activePickupLat != null && activePickupLng != null) {
                                updates.put("pickupLat", activePickupLat);
                                updates.put("pickupLng", activePickupLng);
                                updates.put("sourceLat", activePickupLat);
                                updates.put("sourceLng", activePickupLng);
                            }
                            db.collection("adminEmergencyRequests")
                                    .document(activeDocId)
                                    .update(updates);

                            if (!activeTripId.isEmpty()) {
                                Map<String, Object> progUpdates = new HashMap<>();
                                progUpdates.put("source", activeSource);
                                progUpdates.put("destination", h.name);
                                progUpdates.put("currentStatus", "EN_ROUTE_TO_HOSPITAL");
                                progUpdates.put("lastUpdated", Timestamp.now());
                                if (h.hospLat != null && h.hospLng != null) {
                                    progUpdates.put("destLat", h.hospLat);
                                    progUpdates.put("destLng", h.hospLng);
                                }
                                if (activePickupLat != null && activePickupLng != null) {
                                    progUpdates.put("pickupLat", activePickupLat);
                                    progUpdates.put("pickupLng", activePickupLng);
                                }
                                db.collection("missionProgress").document(activeTripId).set(progUpdates, SetOptions.merge());
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        });

        selectButton.setOnAction(e -> {
            selectButton.setText("Notifying...");
            selectButton.setDisable(true);
            notifiedHospitalName = h.name;
            isHospitalNotified = true;
            updateMissionProgressUI();

            if (!activeTripId.isEmpty()) {
                attachHospitalNotificationListener(activeTripId);
            }

            new Thread(() -> {
                try {
                    Firestore db = FirebaseConfig.getFirestore();
                    if (db != null) {
                        String notifId = "DTH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                        Map<String, Object> hospitalNotification = new HashMap<>();
                        hospitalNotification.put("notificationId", notifId);
                        hospitalNotification.put("timestamp", Timestamp.now());
                        hospitalNotification.put("ambulanceId", (activeAmbulanceId != null && !activeAmbulanceId.isEmpty()) ? activeAmbulanceId : "AMB-101");
                        hospitalNotification.put("ambulanceID", (activeAmbulanceId != null && !activeAmbulanceId.isEmpty()) ? activeAmbulanceId : "AMB-101");
                        hospitalNotification.put("TripID", activeTripId.isEmpty() ? "TRIP-AUTO" : activeTripId);
                        hospitalNotification.put("tripID", activeTripId.isEmpty() ? "TRIP-AUTO" : activeTripId);
                        hospitalNotification.put("nurseemail", (activeNurseEmail != null && !activeNurseEmail.isEmpty()) ? activeNurseEmail : "nurse@lifelink.com");
                        hospitalNotification.put("driveremail", loggedInDriverEmail.trim());
                        hospitalNotification.put("driverID", loggedInDriverEmail.trim());

                        String hospEmail = (h.email != null && !h.email.trim().isEmpty()) 
                                ? h.email.trim() 
                                : (h.id != null && h.id.contains("@") ? h.id : h.name.toLowerCase().replaceAll("\\s+", "") + "@hospital.com");
                        hospitalNotification.put("hospitalemail", hospEmail);
                        hospitalNotification.put("hospitalName", h.name);
                        hospitalNotification.put("hospitalAddress", h.address);
                        hospitalNotification.put("hospitalId", h.id);
                        hospitalNotification.put("message", "Are all emergency resources available and updated for patient admission?");
                        hospitalNotification.put("status", "not confirm");
                        // NOTE: patID is strictly omitted per specification ("don't add patID")

                        db.collection("DriToHosPopup").document(notifId).set(hospitalNotification).get();

                        // Audio confirmation on successful hospital notification
                        SarvamTTSService.speak("Hospital notified.");

                        Platform.runLater(() -> {
                            selectButton.setText("✓ Notified");
                            selectButton.setStyle(FONT_FAMILY + "-fx-background-color: " + LIGHT_GREEN + "; -fx-text-fill: " + GREEN + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 7;");
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Platform.runLater(() -> {
                        selectButton.setText("Failed");
                        selectButton.setDisable(false);
                    });
                }
            }).start();
        });

        actions.getChildren().addAll(directionButton, selectButton);
        card.getChildren().addAll(topBadgeRow, nameRow, divider, metricsGrid, actions);
        return card;
    }

    // =========================================================
    // METRIC BOX (2x2 HELPER)
    // =========================================================
    private VBox createMetricBox(String label, String value, String valueColor) {
        VBox box = new VBox(3);
        box.setPadding(new Insets(8, 9, 8, 9));
        box.setMaxWidth(Double.MAX_VALUE);
        box.setStyle(
                "-fx-background-color: #F8FAFC;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #EEF2F6;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 8;");

        Label labelText = new Label(label);
        labelText.setStyle(FONT_FAMILY + "-fx-font-size: 8px; -fx-font-weight: bold; -fx-text-fill: " + MUTED + ";");

        Label valueText = new Label(value);
        valueText.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + valueColor + ";");

        box.getChildren().addAll(labelText, valueText);
        return box;
    }

    private Image loadSafeIcon() {
        try (var stream = getClass().getResourceAsStream("/assets/Images/lifelinklogonew.png")) {
            if (stream != null) {
                return new Image(stream);
            }
            var res = getClass().getResource("/assets/Images/lifelinklogonew.png");
            if (res != null) {
                return new Image(res.toExternalForm(), false);
            }
        } catch (Exception ignored) {}

        try {
            File f = new File("src/main/resources/assets/Images/lifelinklogonew.png");
            if (!f.exists()) f = new File("LifeLink/lifelink1/src/main/resources/assets/Images/lifelinklogonew.png");
            if (f.exists()) {
                return new Image(f.toURI().toString(), false);
            }
        } catch (Exception ignored) {}

        return null;
    }

    // =========================================================
    // START ENTRYPOINT
    // =========================================================
    @Override
    public void start(Stage stage) throws Exception {
        driverStage = stage;
        preloadImages();
        initializeVoiceCommandService();

        root = new BorderPane();
        root.setStyle("-fx-background-color: " + PAGE_BG + ";");
        mainEmergencyPane = getEmergencyPane();
        root.setCenter(mainEmergencyPane);

        StackPane sidebar = createSidebar(root);
        root.setLeft(sidebar);

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        driverScene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());

        driverStage.setX(visualBounds.getMinX());
        driverStage.setY(visualBounds.getMinY());
        driverStage.setWidth(visualBounds.getWidth());
        driverStage.setHeight(visualBounds.getHeight());

        driverStage.setScene(driverScene);
        driverStage.setTitle("LifeLink - Ambulance Driver (" + loggedInDriverEmail + ")");
        try {
            Image icon = loadSafeIcon();
            if (icon != null && !icon.isError()) {
                driverStage.getIcons().setAll(icon);
            } else if (logoImage != null && !logoImage.isError()) {
                driverStage.getIcons().setAll(logoImage);
            }
        } catch (Exception ignored) {}
        driverStage.setMaximized(true);

        startLiveTracking();

        driverStage.setOnCloseRequest(e -> {
            isTrackingRunning = false;
            if (voiceCommandService != null) {
                try { voiceCommandService.stopWakeWordListening(); } catch (Exception ignored) {}
            }
            if (emergencyListener != null) emergencyListener.remove();
            if (missionProgressListener != null) missionProgressListener.remove();
            if (hospitalNotifyListener != null) hospitalNotifyListener.remove();
            if (hospitalListener != null) hospitalListener.remove();
            if (driToHosPopupListener != null) driToHosPopupListener.remove();
            Platform.exit();
            System.exit(0);
        });

        driverStage.show();
    }

    // =========================================================
    // DRIVER NAVIGATION MENU BAR (260px, MATCHED TO SIDEBAR.JAVA WITH DRIVER BLUE)
    // =========================================================
    private StackPane createSidebar(BorderPane root) {
        VBox leftMenu = new VBox(12);
        leftMenu.setPadding(new Insets(18, 14, 18, 14));
        leftMenu.setPrefWidth(260);
        leftMenu.setMinWidth(260);
        leftMenu.setMaxWidth(260);
        leftMenu.setStyle(
                FONT_FAMILY +
                "-fx-background-color: transparent;"
        );

        // 1. Top Brand Header (With Logo Image or Icon)
        HBox brandHeader = new HBox(12);
        brandHeader.setAlignment(Pos.CENTER_LEFT);
        brandHeader.setPadding(new Insets(4, 6, 8, 6));

        StackPane brandIconPane = new StackPane();
        brandIconPane.setPrefSize(40, 40);
        brandIconPane.setMinSize(40, 40);
        brandIconPane.setMaxSize(40, 40);

        if (logoImage != null && !logoImage.isError()) {
            ImageView logoView = new ImageView(logoImage);
            logoView.setFitHeight(40);
            logoView.setFitWidth(40);
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);
            Rectangle clip = new Rectangle(40, 40);
            clip.setArcWidth(10);
            clip.setArcHeight(10);
            logoView.setClip(clip);
            brandIconPane.setStyle(
                    "-fx-background-radius: 12px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 8, 0, 0, 3);"
            );
            brandIconPane.getChildren().add(logoView);
        } else {
            brandIconPane.setStyle(
                    "-fx-background-color: linear-gradient(to bottom right, " + BLUE + ", " + BLUE_DARK + "); " +
                    "-fx-background-radius: 12px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(41,182,232,0.50), 10, 0, 0, 4);"
            );
            Text brandIcon = new Text("🚑");
            brandIcon.setStyle("-fx-font-size: 20px; -fx-fill: white;");
            brandIconPane.getChildren().add(brandIcon);
        }

        VBox brandTextBox = new VBox(5);
        Text lifeLinkText = new Text("LifeLink");
        lifeLinkText.setStyle(FONT_FAMILY + "-fx-font-size: 20px; -fx-font-weight: 800; -fx-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 4, 0, 0, 1);");

        // Text consoleText = new Text("Driver Console • Alpha-1");
        // consoleText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-fill: #7EB8DA; -fx-font-weight: 500;");
        brandTextBox.getChildren().addAll(lifeLinkText);
        brandHeader.getChildren().addAll(brandIconPane, brandTextBox);

        // 2. Animated Live Telemetry Hub Badge
        HBox liveHubBadge = new HBox(7);
        liveHubBadge.setAlignment(Pos.CENTER_LEFT);
        liveHubBadge.setPadding(new Insets(5, 10, 5, 10));
        liveHubBadge.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.35); " +
                "-fx-border-color: rgba(41, 182, 232, 0.45); " +
                "-fx-border-radius: 16px; " +
                "-fx-background-radius: 16px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(41,182,232,0.20), 6, 0, 0, 1);"
        );

        Circle pulseDot = new Circle(4, Color.web(GREEN));
        Timeline pulseAnim = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(pulseDot.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(800), new KeyValue(pulseDot.opacityProperty(), 0.35)),
                new KeyFrame(Duration.millis(1600), new KeyValue(pulseDot.opacityProperty(), 1.0))
        );
        pulseAnim.setCycleCount(Animation.INDEFINITE);
        pulseAnim.play();

        // Text liveHubText = new Text("DISPATCH UNIT ONLINE");
        // liveHubText.setStyle(FONT_FAMILY + "-fx-font-size: 9.5px; -fx-font-weight: 800; -fx-fill: #7EB8DA; -fx-letter-spacing: 0.6px;");
        // liveHubBadge.getChildren().addAll(pulseDot, liveHubText);

        VBox brandBox = new VBox(8, brandHeader);
        brandBox.setPadding(new Insets(0, 0, 4, 0));

        // Navigation Section Heading (Matching POLICE NAVIGATION style)
        Label navSectionHeading = new Label("DRIVER NAVIGATION");
        navSectionHeading.setStyle(
                FONT_FAMILY +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: 800; " +
                "-fx-text-fill: #7EB8DA; " +
                "-fx-padding: 10px 8px 4px 8px; " +
                "-fx-letter-spacing: 0.8px; " +
                "-fx-opacity: 0.95;"
        );

        // Navigation Buttons (Matching Sidebar.java style with Driver Blue)
        Button dashboardButton = createPoliceNavButton("🚨  Dashboard", true);
        Button notificationButton = createPoliceNavButton("🔔  Notifications", false);
        Button tripHistoryButton = createPoliceNavButton("⏱  Trip History", false);
        Button complaintButton = createPoliceNavButton("📝  Complaints", false);
        Button settingsButton = createPoliceNavButton("⚙  Settings", false);

        Button[] allNavButtons = {dashboardButton, notificationButton, tripHistoryButton, complaintButton, settingsButton};

        dashboardButton.setOnAction(event -> {
            setPoliceNavActive(dashboardButton, allNavButtons);
            if (mainEmergencyPane == null) {
                mainEmergencyPane = getEmergencyPane();
            }
            root.setCenter(mainEmergencyPane);
        });

        notificationButton.setOnAction(event -> {
            setPoliceNavActive(notificationButton, allNavButtons);
            DriverNotification driverNotification = new DriverNotification(() -> {
                setPoliceNavActive(dashboardButton, allNavButtons);
                if (mainEmergencyPane == null) {
                    mainEmergencyPane = getEmergencyPane();
                }
                root.setCenter(mainEmergencyPane);
            });
            root.setCenter(driverNotification.getNotificationPage());
        });

        // Live badge update on notification button when alerts arrive or read status changes
        DriverNotification.setOnNotificationChanged(() -> {
            Platform.runLater(() -> {
                int unread = DriverNotification.getUnreadCount();
                if (unread > 0) {
                    notificationButton.setText("🔔  Notifications (" + unread + ")");
                } else {
                    notificationButton.setText("🔔  Notifications");
                }
            });
        });

        tripHistoryButton.setOnAction(event -> {
            setPoliceNavActive(tripHistoryButton, allNavButtons);
            DriverTripHistory driverTripHistory = new DriverTripHistory();
            root.setCenter(driverTripHistory.getDriverTripsPage());
        });

        complaintButton.setOnAction(event -> {
            setPoliceNavActive(complaintButton, allNavButtons);
            DriverComplaint driverComplaint = new DriverComplaint(loggedInDriverEmail, activeTripId);
            root.setCenter(driverComplaint.getComplaintSection());
        });

        settingsButton.setOnAction(event -> {
            setPoliceNavActive(settingsButton, allNavButtons);
            DriverSetting driverSetting = new DriverSetting(loggedInDriverEmail);
            root.setCenter(driverSetting.getAppSettingsPage(() -> {
                setPoliceNavActive(dashboardButton, allNavButtons);
                if (mainEmergencyPane == null) {
                    mainEmergencyPane = getEmergencyPane();
                }
                root.setCenter(mainEmergencyPane);
            }));
        });

        // Spacer pushes profile and logout to bottom
        Region menuSpacer = new Region();
        VBox.setVgrow(menuSpacer, Priority.ALWAYS);

        // Profile Mini Card (Matching Sidebar.java)
        HBox profileBox = new HBox(12);
        profileBox.setAlignment(Pos.CENTER_LEFT);
        profileBox.setPadding(new Insets(10, 12, 14, 12));
        profileBox.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.40); " +
                "-fx-border-color: rgba(255, 255, 255, 0.18); " +
                "-fx-border-radius: 12px; " +
                "-fx-background-radius: 12px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.30), 8, 0, 0, 2);"
        );

        String driverEmail = (loggedInDriverEmail != null && !loggedInDriverEmail.isEmpty()) ? loggedInDriverEmail : "driver@lifelink.com";
        String initialsText = extractInitials(driverEmail);

        StackPane avatarPane = new StackPane();
        avatarPane.setPrefSize(36, 36);
        avatarPane.setMinSize(36, 36);
        avatarPane.setMaxSize(36, 36);
        avatarPane.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, " + BLUE + ", " + BLUE_DARK + "); " +
                "-fx-background-radius: 18px;"
        );
        Text initials = new Text(initialsText);
        initials.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: white;");
        avatarPane.getChildren().add(initials);

        VBox profileDetails = new VBox(2);
        Text profileName = new Text(driverEmail);
        profileName.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #FFFFFF;");
        Text profileRole = new Text("Verified Ambulance Pilot");
        profileRole.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-fill: rgba(160,220,255,0.85);");
        profileDetails.getChildren().addAll(profileName, profileRole);
        profileBox.getChildren().addAll(avatarPane, profileDetails);

        // Logout Button with smooth hover transition (Matching Sidebar.java)
        Button logoutButton = new Button("↩  Logout");
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setPrefHeight(46);
        logoutButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #0D2040; " +
                "-fx-text-fill: #89C4E8; " +
                "-fx-font-size: 13.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-color: rgba(137,196,232,0.25); " +
                "-fx-border-radius: 12px; " +
                "-fx-border-width: 1px; " +
                "-fx-cursor: hand;"
        );
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #2A6DB5; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 13.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-color: rgba(255,255,255,0.25); " +
                "-fx-border-radius: 12px; " +
                "-fx-border-width: 1px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(42,109,181,0.50), 12, 0, 0, 3);"
        ));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #0D2040; " +
                "-fx-text-fill: #89C4E8; " +
                "-fx-font-size: 13.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-color: rgba(137,196,232,0.25); " +
                "-fx-border-radius: 12px; " +
                "-fx-border-width: 1px; " +
                "-fx-cursor: hand;"
        ));

        logoutButton.setOnAction(event -> {
            isTrackingRunning = false;
            if (voiceCommandService != null) {
                try { voiceCommandService.stopWakeWordListening(); } catch (Exception ignored) {}
            }
            if (emergencyListener != null) emergencyListener.remove();
            if (missionProgressListener != null) missionProgressListener.remove();
            if (hospitalNotifyListener != null) hospitalNotifyListener.remove();
            if (hospitalListener != null) hospitalListener.remove();
            if (driToHosPopupListener != null) driToHosPopupListener.remove();
            try {
                Welcome welcome = new Welcome();
                welcome.start(driverStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        leftMenu.getChildren().addAll(
                brandBox,
                navSectionHeading,
                dashboardButton,
                notificationButton,
                tripHistoryButton,
                complaintButton,
                settingsButton,
                menuSpacer,
                profileBox,
                logoutButton
        );

        // ---- Background image layer wrapped in StackPane ----
        StackPane sidebarStack = new StackPane();
        sidebarStack.setPrefWidth(260);
        sidebarStack.setMinWidth(260);
        sidebarStack.setMaxWidth(260);
        sidebarStack.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(13,32,64,0.35), 18, 0, 4, 0);");

        // Background image
        Image sidebarBg = loadSafeImage("/assets/Images/driverDashboardbackground.png",
                "src/main/resources/assets/Images/driverDashboardbackground.png");
        if (sidebarBg != null && !sidebarBg.isError()) {
            ImageView bgImageView = new ImageView(sidebarBg);
            bgImageView.setPreserveRatio(false);
            bgImageView.setSmooth(true);
            bgImageView.fitWidthProperty().bind(sidebarStack.widthProperty());
            bgImageView.fitHeightProperty().bind(sidebarStack.heightProperty());
            sidebarStack.getChildren().add(bgImageView);
        } else {
            // Fallback solid dark gradient when image not found
            Pane fallbackBg = new Pane();
            fallbackBg.setStyle("-fx-background-color: linear-gradient(to bottom, #0A2540, #0C3B5E, #093A4A);");
            sidebarStack.getChildren().add(fallbackBg);
        }

        // Dark gradient overlay for readability
        Pane gradientOverlay = new Pane();
        gradientOverlay.setStyle(
            "-fx-background-color: linear-gradient(" +
                "to bottom, " +
                "rgba(82, 114, 171, 0.82) 0%, " +
                "rgba(60, 92, 139, 0.72) 40%, " +
                "rgba(10, 34, 75, 0.88) 100%" +
            ");"
        );

        // Right edge separator glow
        Pane edgeGlow = new Pane();
        edgeGlow.setStyle(
            "-fx-background-color: linear-gradient(" +
                "to right, " +
                "transparent, " +
                "rgba(41, 182, 232, 0.18)" +
            "); " +
            "-fx-max-width: 2px; " +
            "-fx-pref-width: 2px;"
        );
        StackPane.setAlignment(edgeGlow, Pos.CENTER_RIGHT);

        sidebarStack.getChildren().addAll(gradientOverlay, leftMenu, edgeGlow);

        return sidebarStack;
    }

    private static Label createCategoryLabel(String text) {
        Label catLabel = new Label(text);
        catLabel.setStyle(
                FONT_FAMILY +
                "-fx-font-size: 9.5px; " +
                "-fx-font-weight: 800; " +
                "-fx-text-fill: " + MUTED + "; " +
                "-fx-padding: 10px 6px 4px 6px; " +
                "-fx-letter-spacing: 0.6px;"
        );
        return catLabel;
    }

    private static Label createCategoryLabelDark(String text) {
        Label catLabel = new Label(text);
        catLabel.setStyle(
                FONT_FAMILY +
                "-fx-font-size: 9.5px; " +
                "-fx-font-weight: 800; " +
                "-fx-text-fill: #7EB8DA; " +
                "-fx-padding: 10px 6px 4px 6px; " +
                "-fx-letter-spacing: 0.6px;"
        );
        return catLabel;
    }

    private static String getActiveNavStyle() {
        return FONT_FAMILY +
                "-fx-background-color: #FFFFFF; " +
                "-fx-text-fill: #0D2B5E; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 12px; " +
                "-fx-alignment: center-left; " +
                "-fx-padding: 0 16px; " +
                "-fx-border-color: transparent; " +
                "-fx-border-radius: 12px; " +
                "-fx-border-width: 0; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(13,32,64,0.25), 10, 0, 0, 3); " +
                "-fx-cursor: hand;";
    }

    private static String getInactiveNavStyle() {
        return FONT_FAMILY +
                "-fx-background-color: transparent; " +
                "-fx-text-fill: #B8D4F0; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: normal; " +
                "-fx-background-radius: 12px; " +
                "-fx-alignment: center-left; " +
                "-fx-padding: 0 16px; " +
                "-fx-border-color: transparent; " +
                "-fx-border-radius: 12px; " +
                "-fx-border-width: 1px; " +
                "-fx-cursor: hand;";
    }

    private static String getHoverNavStyle() {
        return FONT_FAMILY +
                "-fx-background-color: rgba(255, 255, 255, 0.10); " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 12px; " +
                "-fx-alignment: center-left; " +
                "-fx-padding: 0 16px; " +
                "-fx-border-color: rgba(184, 212, 240, 0.25); " +
                "-fx-border-radius: 12px; " +
                "-fx-border-width: 1px; " +
                "-fx-cursor: hand;";
    }

    private Button createPoliceNavButton(String text, boolean active) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(46);

        if (active) {
            btn.setStyle(getActiveNavStyle());
        } else {
            btn.setStyle(getInactiveNavStyle());
            btn.setOnMouseEntered(e -> {
                btn.setStyle(getHoverNavStyle());
                btn.setTranslateX(4);
            });
            btn.setOnMouseExited(e -> {
                btn.setStyle(getInactiveNavStyle());
                btn.setTranslateX(0);
            });
        }

        return btn;
    }

    private void setPoliceNavActive(Button activeBtn, Button[] allButtons) {
        for (Button b : allButtons) {
            if (b == activeBtn) {
                b.setStyle(getActiveNavStyle());
                b.setTranslateX(0);
                b.setOnMouseEntered(null);
                b.setOnMouseExited(null);
            } else {
                b.setStyle(getInactiveNavStyle());
                b.setTranslateX(0);
                b.setOnMouseEntered(e -> {
                    b.setStyle(getHoverNavStyle());
                    b.setTranslateX(4);
                });
                b.setOnMouseExited(e -> {
                    b.setStyle(getInactiveNavStyle());
                    b.setTranslateX(0);
                });
            }
        }
    }

    private static String extractInitials(String email) {
        if (email == null || email.isEmpty()) return "DR";
        int atIdx = email.indexOf('@');
        String namePart = atIdx > 0 ? email.substring(0, atIdx) : email;
        if (namePart.length() >= 2) {
            return namePart.substring(0, 2).toUpperCase();
        }
        return namePart.toUpperCase();
    }

    private BorderPane createNotificationPage() {
        DriverNotification driverNotification = new DriverNotification(() -> {
            if (mainEmergencyPane == null) {
                mainEmergencyPane = getEmergencyPane();
            }
            root.setCenter(mainEmergencyPane);
        });
        return driverNotification.getNotificationPage();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        isTrackingRunning = false;
        if (voiceCommandService != null) {
            try { voiceCommandService.stopWakeWordListening(); } catch (Exception ignored) {}
        }
        if (emergencyListener != null) emergencyListener.remove();
        if (missionProgressListener != null) missionProgressListener.remove();
        if (hospitalNotifyListener != null) hospitalNotifyListener.remove();
        if (hospitalListener != null) hospitalListener.remove();
        if (driToHosPopupListener != null) driToHosPopupListener.remove();
        super.stop();
        Platform.exit();
        System.exit(0);
    }
}