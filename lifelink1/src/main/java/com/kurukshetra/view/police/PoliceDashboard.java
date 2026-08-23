package com.kurukshetra.view.police;

import com.kurukshetra.dao.admin.AdminSideEmgReqDao;
import com.kurukshetra.model.admin.AdminSideEmgReqModel;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import netscape.javascript.JSObject;

public class PoliceDashboard extends Application {

    public static Stage dashboardStage;
    private Scene dashboardScene;
    private StackPane rootPane;
    private BorderPane mainBorderPane;
    private StackPane mapContainer;
    private VBox centerMapBox;
    private Set<String> knownPendingTrips = new HashSet<>();

    // ── WebView + Bridge ────────────────────────────────────────────────
    private WebView mapWebView;
    private WebEngine webEngine;
    private boolean webViewReady = false;
    private AdminSideEmgReqModel currentRequest;
    private Timeline ambulanceTimeline;

    /**
     * Java-to-JavaScript bridge class.
     * Bound to {@code window.javaControlRoom} inside the WebView so that
     * JavaScript can call back into Java (e.g. when the user clicks
     * "NOTIFY TRAFFIC POLICE" on the map).
     *
     * <p><strong>Must be a public class</strong> — the JS engine cannot
     * invoke methods on private/anonymous classes.
     */
    public class JavaControlRoomBridge {

        /**
         * Called from JS: window.javaControlRoom.onCorridorCleared(src, dest)
         *
         * When the police officer clicks "NOTIFY TRAFFIC POLICE" on the map,
         * JavaScript calls this method.  We update the tracked request's
         * status to CLEARANCE_ACTIVE so the rest of the system knows the
         * corridor is being cleared.
         */
        public void onCorridorCleared(String source, String destination) {
            Platform.runLater(() -> {
                System.out.println("[JavaControlRoomBridge] Corridor CLEARED");
                System.out.println("   FROM : " + source);
                System.out.println("   TO   : " + destination);

                // Update the model status so downstream listeners can react
                if (currentRequest != null) {
                    currentRequest.setStatus("CLEARANCE_ACTIVE");
                    System.out.println("[JavaControlRoomBridge] Request status → CLEARANCE_ACTIVE");
                    
                    // SAVE UPDATE BACK TO FIRESTORE
                    new AdminSideEmgReqDao().createEmergencyRequest(currentRequest);
                }
            });
        }

        /** Called from JS: window.javaControlRoom.log(message) */
        public void log(String message) {
            System.out.println("[JS → Java] " + message);
        }

        /** Called from JS to toggle immersive fullscreen mode */
        public void toggleFullscreen() {
            Platform.runLater(() -> {
                if (dashboardStage != null) {
                    if (dashboardStage.isFullScreen()) {
                        dashboardStage.setFullScreen(false);
                        rootPane.getChildren().remove(mapContainer);
                        centerMapBox.getChildren().add(1, mapContainer);
                    } else {
                        dashboardStage.setFullScreen(true);
                        centerMapBox.getChildren().remove(mapContainer);
                        rootPane.getChildren().add(mapContainer);
                    }
                }
            });
        }
    }

    // Keep a strong reference so the GC doesn't collect the bridge
    private final JavaControlRoomBridge jsBridge = new JavaControlRoomBridge();

    // ====================================================================
    // processEmergencyRequest  — the public API for other Java code
    // ====================================================================
    /**
     * Receives an emergency request (typically from an Admin Green-Corridor
     * dispatch) and tells the WebView map to plot the route.
     *
     * @param request the {@link AdminSideEmgReqModel} containing source and
     *                destination
     */
    public void processEmergencyRequest(AdminSideEmgReqModel request) {
        if (request == null) return;

        // Keep a reference so the bridge can update this request's status
        this.currentRequest = request;

        String source      = sanitiseForJs(request.getSource());
        String destination  = sanitiseForJs(request.getDestination());

        Platform.runLater(() -> {
            if (webViewReady && webEngine != null) {
                String js = "setRouteByNames('" + source + "', '" + destination + "')";
                System.out.println("[PoliceDashboard] Executing JS → " + js);
                webEngine.executeScript(js);
            } else {
                System.err.println("[PoliceDashboard] WebView not ready yet — request queued in console.");
            }
        });
    }

    /** Escape single-quotes so we don't break the JS string literal. */
    private String sanitiseForJs(String input) {
        if (input == null) return "";
        return input.replace("'", "\\'").replace("\n", " ").replace("\r", "");
    }

    // ====================================================================
    // startLiveAmbulanceTracking  — animates the 🚑 along the route
    // ====================================================================
    /**
     * Accepts an array of [lat, lng] coordinate pairs (the route polyline)
     * and uses a JavaFX {@link Timeline} to push one point per second into
     * the JavaScript {@code updateAmbulancePosition(lat, lng)} function.
     *
     * <p>This creates the illusion of an ambulance driving along the
     * Green Corridor route on the map.
     *
     * @param coordinates a 2-D array where each element is {lat, lng}
     */
    public void startLiveAmbulanceTracking(double[][] coordinates) {
        if (coordinates == null || coordinates.length == 0) {
            System.err.println("[PoliceDashboard] No coordinates for ambulance tracking.");
            return;
        }

        // Stop any previous tracking animation
        if (ambulanceTimeline != null) {
            ambulanceTimeline.stop();
        }

        // Build a Timeline with one KeyFrame per coordinate point, 1 second apart
        ambulanceTimeline = new Timeline();
        ambulanceTimeline.setCycleCount(Timeline.INDEFINITE); // Loop continuously for 24/7 tracking

        for (int i = 0; i < coordinates.length; i++) {
            final double lat = coordinates[i][0];
            final double lng = coordinates[i][1];
            final int index = i;

            KeyFrame kf = new KeyFrame(
                Duration.seconds(i + 1),  // fire at second 1, 2, 3, ...
                event -> {
                    if (webViewReady && webEngine != null) {
                        String js = "updateAmbulancePosition(" + lat + ", " + lng + ")";
                        webEngine.executeScript(js);

                        // Log every 10th point so the console isn't flooded
                        if (index % 10 == 0) {
                            System.out.println("[AmbulanceTrack] Point " + index
                                + "/" + coordinates.length
                                + " → (" + lat + ", " + lng + ")");
                        }
                    }
                }
            );
            ambulanceTimeline.getKeyFrames().add(kf);
        }

        ambulanceTimeline.setOnFinished(e ->
            System.out.println("[AmbulanceTrack] Ambulance reached destination.")
        );

        System.out.println("[PoliceDashboard] Starting ambulance tracking with "
            + coordinates.length + " waypoints.");
        ambulanceTimeline.play();
    }

    // ====================================================================
    // start()  — builds the full Police Dashboard UI
    // ====================================================================
    @Override
    public void start(Stage stage) throws Exception {

        dashboardStage = stage;

        BorderPane borderPane = new BorderPane();
        mainBorderPane = borderPane;
        borderPane.setStyle("-fx-background-color: #f8f8ff;");

        // LEFT MENU
        VBox leftMenu = new VBox(15);
        leftMenu.setPadding(new Insets(25, 15, 20, 15));
        leftMenu.setPrefWidth(230);
        leftMenu.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d8dce5; -fx-border-width: 0px 1px 0px 0px;");

        Text profileName = new Text("Control Room");
        profileName.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text profileText = new Text("Police Dashboard");
        profileText.setStyle("-fx-font-size: 13px; -fx-fill: #6b7280;");

        VBox profileBox = new VBox(5);
        profileBox.getChildren().addAll(profileName, profileText);

        // DASHBOARD BUTTON
        Button dashboardButton = new Button("Dashboard");
        dashboardButton.setPrefWidth(195);
        dashboardButton.setPrefHeight(45);
     //   dashboardButton.setAlignment(Pos.CENTER_LEFT);
        dashboardButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

        // HISTORY BUTTON
        Button historyButton = new Button("History");
        historyButton.setPrefWidth(195);
        historyButton.setPrefHeight(45);
    //    historyButton.setAlignment(Pos.CENTER_LEFT);
        historyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");


        // PROFILE BUTTON
        Button profileButton = new Button("Profile");
        profileButton.setPrefWidth(195);
        profileButton.setPrefHeight(45);
        //profileButton.setAlignment(Pos.CENTER_LEFT);
        profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

         
        // SETTINGS BUTTON
        Button settingsButton = new Button("Settings");
        settingsButton.setPrefWidth(195);
        settingsButton.setPrefHeight(45);
        //settingsButton.setAlignment(Pos.CENTER_LEFT);
        settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px;");

        // SPACER
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // LOGOUT BUTTON
        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(195);
        logoutButton.setPrefHeight(45);
        // logoutButton.setAlignment(Pos.BOTTOM_CENTER);
        logoutButton.setStyle("-fx-background-color: #fff1f2; -fx-text-fill: #dc2626; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

        // HOVER EFFECTS (Safe for Active Tabs)
        java.util.function.Consumer<Button> setHover = (btn) -> {
            btn.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (btn.getStyle().contains("#e9edff")) return; // Skip active tab
                if (newVal) btn.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px; -fx-cursor: hand;");
                else btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
            });
        };
        setHover.accept(dashboardButton);
        setHover.accept(historyButton);
        setHover.accept(profileButton);
        setHover.accept(settingsButton);
        
        logoutButton.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) logoutButton.setStyle("-fx-background-color: #fecdd3; -fx-text-fill: #dc2626; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand;");
            else logoutButton.setStyle("-fx-background-color: #fff1f2; -fx-text-fill: #dc2626; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");
        });

        historyButton.setOnAction(event -> {

                dashboardButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

                historyButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

                PoliceHistory history = new PoliceHistory();
                ScrollPane sp = new ScrollPane(history.getHistoryVBox());
                sp.setFitToWidth(true);
                sp.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
                borderPane.setCenter(sp);
        });

        profileButton.setOnAction(event ->{

                dashboardButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                historyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

                profileButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

                PoliceProfile profile = new PoliceProfile();
                ScrollPane sp = new ScrollPane(profile.getProfileVBox());
                sp.setFitToWidth(true);
                sp.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
                borderPane.setCenter(sp);
        });

        settingsButton.setOnAction(event -> {

                dashboardButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                historyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

                settingsButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

                PoliceSettings setting = new PoliceSettings();
                ScrollPane sp = new ScrollPane(setting.getSettingsVBox());
                sp.setFitToWidth(true);
                sp.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
                borderPane.setCenter(sp);
        });

        leftMenu.getChildren().addAll(
                profileBox,
                dashboardButton,
                historyButton,
                profileButton,
                settingsButton,
                 spacer,
                logoutButton
        );

        borderPane.setLeft(leftMenu);

        // MAIN DASHBOARD
        VBox dashboard = new VBox(20);
        dashboard.setPadding(new Insets(25));

        // TOP HEADER
        Text heading = new Text("Control Room");
        heading.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text subHeading = new Text("Monitor ambulances, traffic clearance and emergency arrivals");
        subHeading.setStyle("-fx-font-size: 14px; -fx-fill: #6b7280;");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(heading, subHeading);

        // MAIN CONTENT
        HBox mainContent = new HBox(20);
        mainContent.setPrefHeight(650);

        // CENTER PART
        VBox centerPart = new VBox(15);
        centerPart.setPrefWidth(780);

        Text incomingText = new Text("Incoming Ambulances");
        incomingText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text liveText = new Text("● LIVE");
        liveText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #16a34a;");

        HBox incomingHeader = new HBox(15);
        incomingHeader.setAlignment(Pos.CENTER_LEFT);
        incomingHeader.getChildren().addAll(incomingText, liveText);

        // ─── MAP AREA — replaced static placeholder with live WebView ───
        mapWebView = new WebView();
        mapWebView.setPrefHeight(390);
        mapWebView.setPrefWidth(780);

        webEngine = mapWebView.getEngine();

        // Load the Leaflet map HTML from the classpath resources folder
        String mapUrl = getClass().getResource("/live_map.html").toExternalForm();
        webEngine.load(mapUrl);

        // Once the page finishes loading, bind the Java↔JS bridge
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                System.out.println("[PoliceDashboard] WebView loaded successfully.");
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaControlRoom", jsBridge);
                webViewReady = true;
                System.out.println("[PoliceDashboard] Java-JS bridge bound to window.javaControlRoom");
            }
            if (newState == Worker.State.FAILED) {
                System.err.println("[PoliceDashboard] WebView failed to load live_map.html");
            }
        });

        // Wrap the WebView in a styled container to match the original UI
        StackPane mapPane = new StackPane(mapWebView);
        mapContainer = mapPane;
        mapPane.setPrefHeight(390);
        mapPane.setPrefWidth(780);
        mapPane.setStyle("-fx-background-color: #0f172a; -fx-background-radius: 15px; -fx-border-color: #334155; -fx-border-radius: 15px;");
        // Clip the WebView corners so they follow the rounded container
        mapWebView.setStyle("-fx-background-color: #0f172a;");

        // ─── DYNAMIC UI CONTAINERS ───
        VBox ambulanceInfo = new VBox(15);
        Text loadingAmb = new Text("Loading live data...");
        loadingAmb.setStyle("-fx-font-size: 14px; -fx-fill: #6b7280; -fx-font-style: italic;");
        ambulanceInfo.getChildren().add(loadingAmb);
        
        VBox clearanceCard = new VBox(15);
        clearanceCard.setPadding(new Insets(20));
        clearanceCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e5e7eb; -fx-border-radius: 15px;");
        
        Text clearanceTitleInit = new Text("Clearance Alerts");
        clearanceTitleInit.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: #111827;");
        Text loadingClearance = new Text("Loading live data...");
        loadingClearance.setStyle("-fx-font-size: 14px; -fx-fill: #6b7280; -fx-font-style: italic;");
        clearanceCard.getChildren().addAll(clearanceTitleInit, loadingClearance);

        VBox statusCard = new VBox(15);
        statusCard.setPadding(new Insets(20));
        statusCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-color: #e5e7eb; -fx-border-radius: 15px;");

        Text statusTitleInit = new Text("Status & Arrivals");
        statusTitleInit.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: #111827;");
        Text loadingStatus = new Text("Loading live data...");
        loadingStatus.setStyle("-fx-font-size: 14px; -fx-fill: #6b7280; -fx-font-style: italic;");
        statusCard.getChildren().addAll(statusTitleInit, loadingStatus);

        centerMapBox = centerPart;
        centerPart.getChildren().addAll(
                incomingHeader,
                mapPane,
                ambulanceInfo
        );

        // RIGHT PART
        VBox rightPart = new VBox(20);
        rightPart.setPrefWidth(390);

        rightPart.getChildren().addAll(
                clearanceCard,
                statusCard
        );

        // ─── REAL-TIME FIRESTORE LISTENER ───
        AdminSideEmgReqDao dao = new AdminSideEmgReqDao();
        dao.listenToEmergencyRequests(requests -> {
            Platform.runLater(() -> {
                updateDashboardUI(requests, ambulanceInfo, clearanceCard, statusCard);
            });
        });

        // ADD CENTER + RIGHT
        HBox.setHgrow(centerPart, Priority.ALWAYS);

        mainContent.getChildren().addAll(
                centerPart,
                rightPart
        );

        dashboard.getChildren().addAll(
                headingBox,
                mainContent
        );

        // CENTER SCROLL PANE
        ScrollPane scrollPane = new ScrollPane(dashboard);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        borderPane.setCenter(scrollPane);

        dashboardButton.setOnAction(event -> {

                historyButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                profileButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");
                settingsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-size: 15px; -fx-background-radius: 10px;");

                dashboardButton.setStyle("-fx-background-color: #e9edff; -fx-text-fill: #3949ab; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 10px;");

                borderPane.setCenter(scrollPane);
        });

        logoutButton.setOnAction(event ->{
                PoliceSignIn signInPage = new PoliceSignIn();
                dashboardStage.setScene(signInPage.getSignInScene()); 
        });

        // SCENE
        rootPane = new StackPane(borderPane);
        dashboardScene = new Scene(rootPane, 1200, 750);

        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Police Control Room");
        
        // Force maximize using visual bounds to guarantee it works on all Windows setups
        javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        dashboardStage.setX(screenBounds.getMinX());
        dashboardStage.setY(screenBounds.getMinY());
        dashboardStage.setWidth(screenBounds.getWidth());
        dashboardStage.setHeight(screenBounds.getHeight());
        dashboardStage.setMaximized(true);
        
        dashboardStage.show();
    }

    // ====================================================================
    // NOTIFICATION SYSTEM
    // ====================================================================
    public void showNotification(String title, String message) {
        if (rootPane == null) return;
        
        Platform.runLater(() -> {
            VBox notifBox = new VBox(5);
            notifBox.setPadding(new Insets(15, 20, 15, 20));
            notifBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px; -fx-border-color: #16a34a; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 5);");
            notifBox.setMaxWidth(350);
            notifBox.setMaxHeight(80);
            
            Text tText = new Text(title);
            tText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: #111827;");
            
            Text mText = new Text(message);
            mText.setStyle("-fx-font-size: 13px; -fx-fill: #4b5563;");
            
            notifBox.getChildren().addAll(tText, mText);
            
            StackPane.setAlignment(notifBox, Pos.TOP_RIGHT);
            StackPane.setMargin(notifBox, new Insets(20, 20, 0, 0));
            
            // Animation
            notifBox.setTranslateX(400); // Start off-screen
            rootPane.getChildren().add(notifBox);
            
            javafx.animation.TranslateTransition slideIn = new javafx.animation.TranslateTransition(Duration.millis(300), notifBox);
            slideIn.setToX(0);
            
            javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(Duration.seconds(7));
            
            javafx.animation.TranslateTransition slideOut = new javafx.animation.TranslateTransition(Duration.millis(300), notifBox);
            slideOut.setToX(400);
            slideOut.setOnFinished(e -> rootPane.getChildren().remove(notifBox));
            
            javafx.animation.SequentialTransition seq = new javafx.animation.SequentialTransition(slideIn, pause, slideOut);
            seq.play();
        });
    }

    // ====================================================================
    // DYNAMIC UI UPDATER
    // ====================================================================
    private void updateDashboardUI(List<AdminSideEmgReqModel> requests, VBox ambulanceInfo, VBox clearanceCard, VBox statusCard) {
        ambulanceInfo.getChildren().clear();
        clearanceCard.getChildren().clear();
        statusCard.getChildren().clear();

        // Rebuild Headers
        Text clearanceTitle = new Text("Clearance Alerts");
        clearanceTitle.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: #111827;");
        
        long pendingCount = requests.stream().filter(r -> "PENDING_CLEARANCE".equals(r.getStatus())).count();
        Text pendingText = new Text(pendingCount + " Pending");
        pendingText.setStyle("-fx-font-size: 12px; -fx-fill: #dc2626; -fx-font-weight: bold;");

        HBox clearanceHeader = new HBox();
        clearanceHeader.setAlignment(Pos.CENTER_LEFT);
        Region clearanceSpacer = new Region();
        HBox.setHgrow(clearanceSpacer, Priority.ALWAYS);
        clearanceHeader.getChildren().addAll(clearanceTitle, clearanceSpacer, pendingText);
        clearanceCard.getChildren().add(clearanceHeader);

        Text statusTitle = new Text("Status & Arrivals");
        statusTitle.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-fill: #111827;");
        statusCard.getChildren().add(statusTitle);

        if (requests.isEmpty()) {
            Text noData = new Text("No active emergencies found.");
            noData.setStyle("-fx-font-size: 14px; -fx-fill: #6b7280; -fx-font-style: italic;");
            ambulanceInfo.getChildren().add(noData);
            
            Text noAlerts = new Text("No pending alerts.");
            noAlerts.setStyle("-fx-font-size: 14px; -fx-fill: #6b7280; -fx-font-style: italic;");
            clearanceCard.getChildren().add(noAlerts);
            
            Text noStatus = new Text("No incoming arrivals.");
            noStatus.setStyle("-fx-font-size: 14px; -fx-fill: #6b7280; -fx-font-style: italic;");
            statusCard.getChildren().add(noStatus);
            return;
        }

        HBox ambRow = new HBox(15);
        int ambCount = 0;

        for (AdminSideEmgReqModel req : requests) {
            if ("PENDING_CLEARANCE".equals(req.getStatus())) {
                if (!knownPendingTrips.contains(req.getTripID())) {
                    knownPendingTrips.add(req.getTripID());
                    String msg = "Severity: " + req.getSeverity() + " | Trip: " + req.getTripID() + "\n" + req.getSource() + " ➔ " + req.getDestination();
                    showNotification("New Emergency Alert!", msg);
                }
                
                if (currentRequest == null || !currentRequest.getTripID().equals(req.getTripID())) {
                    currentRequest = req;
                    javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(Duration.seconds(1.5));
                    delay.setOnFinished(e -> processEmergencyRequest(req));
                    delay.play();
                }
            }

            // Populate Clearance Alerts
            if ("PENDING_CLEARANCE".equals(req.getStatus()) || "CLEARANCE_ACTIVE".equals(req.getStatus())) {
                clearanceCard.getChildren().add(createClearanceAlert(req));
            }

            // Populate Ambulance Info (bottom left)
            if (!"COMPLETED".equals(req.getStatus())) {
                VBox ambBox = createAmbulanceCard(req);
                HBox.setHgrow(ambBox, Priority.ALWAYS);
                ambRow.getChildren().add(ambBox);
                ambCount++;
                if (ambCount % 2 == 0) {
                    ambulanceInfo.getChildren().add(ambRow);
                    ambRow = new HBox(15);
                }
            }

            // Populate Status & Arrivals
            statusCard.getChildren().add(createStatusRow(req));
        }

        if (ambCount % 2 != 0) {
            ambulanceInfo.getChildren().add(ambRow); // Add remaining
        }
    }

    private VBox createAmbulanceCard(AdminSideEmgReqModel req) {
        VBox box = new VBox(5);
        box.setPadding(new Insets(15));
        box.setPrefWidth(240);
        box.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12px; -fx-border-color: #e5e7eb; -fx-border-radius: 12px;");

        Text name = new Text("Trip: " + req.getTripID());
        name.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text status = new Text(req.getStatus());
        status.setStyle("-fx-font-size: 13px; -fx-fill: #16a34a;");

        Text eta = new Text("Dest: " + req.getDestination());
        eta.setStyle("-fx-font-size: 12px; -fx-fill: #6b7280;");

        box.getChildren().addAll(name, status, eta);
        return box;
    }

    private VBox createClearanceAlert(AdminSideEmgReqModel req) {
        VBox box = new VBox(8);
        box.setPadding(new Insets(15));
        
        boolean isActive = "CLEARANCE_ACTIVE".equals(req.getStatus());
        box.setStyle(isActive 
            ? "-fx-background-color: #f0fdf4; -fx-background-radius: 12px; -fx-border-color: #bbf7d0; -fx-border-radius: 12px;" 
            : "-fx-background-color: #fff7ed; -fx-background-radius: 12px; -fx-border-color: #fed7aa; -fx-border-radius: 12px;");

        Text title = new Text("Dest: " + req.getDestination());
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text msg = new Text(isActive ? "Corridor Cleared" : "Awaiting Clearance");
        msg.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + (isActive ? "#16a34a" : "#ea580c") + ";");

        Text sub = new Text("Severity: " + req.getSeverity() + " | Trip: " + req.getTripID());
        sub.setStyle("-fx-font-size: 12px; -fx-fill: #6b7280;");

        box.getChildren().addAll(title, msg, sub);
        return box;
    }

    private VBox createStatusRow(AdminSideEmgReqModel req) {
        VBox box = new VBox(5);
        box.setPadding(new Insets(10, 15, 10, 15));
        box.setStyle("-fx-border-color: #f3f4f6; -fx-border-width: 0 0 1px 0;");

        Text dest = new Text(req.getDestination());
        dest.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: #111827;");

        Text status = new Text(req.getStatus().replace("_", " "));
        String color = "COMPLETED".equals(req.getStatus()) ? "#16a34a" : ("EN_ROUTE".equals(req.getStatus()) ? "#2563eb" : "#d97706");
        status.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: " + color + ";");

        box.getChildren().addAll(dest, status);
        return box;
    }
}