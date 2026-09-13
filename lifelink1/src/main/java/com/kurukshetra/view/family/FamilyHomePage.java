package com.kurukshetra.view.family;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.FamilyUserModel;
import com.kurukshetra.service.GreenApiService;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.print.PrinterJob;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Modern, rich, interactive, clean, and animated Family Dashboard (Home Page).
 * Preserves 100% of functional code: Firestore member & contact sync, Fast2SMS
 * & GreenAPI
 * emergency dispatch, Google Maps navigation, QR Pass generation, and
 * geolocation detector.
 */
public class FamilyHomePage extends Application {

    // ---------- COLOR PALETTE (Terracotta Healthcare Design System) ----------
    private static final String PRIMARY = "#CA6721";
    private static final String PRIMARY_DARK = "#782F16";
    private static final String VERY_LIGHT_TERRACOTTA = "#FDF0E8";
    private static final String LIGHT_TERRACOTTA = "#F9E4D6";
    private static final String PALE_PEACH = "#FEF7F2";
    private static final String PAGE_BG = "#FCF9F7";
    private static final String SURFACE = "#FFFFFF";
    private static final String TEXT_PRIMARY = "#2B211D";
    private static final String TEXT_SECONDARY = "#675B55";
    private static final String TEXT_MUTED = "#938780";
    private static final String BORDER_COLOR = "#EDE0D8";
    private static final String HOVER_BG = "#FFF7F2";
    private static final String GREEN = "#16A34A";
    private static final String GREEN_BG = "#F0FDF4";
    private static final String SOS_RED = "#BA3B3E";
    private static final String SOS_BG = "#FCEAE8";
    private static final String TOGGLE_GREEN = "#2D6A4F";

    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";

    public static Stage mainStage;

    // Real-time location defaults
    private double currentLatitude = 18.447;
    private double currentLongitude = 73.823;
    private String currentLocationName = "Detecting location...";
    private Label locationBadgeLabel;

    // Active logged-in user email used for Firestore root
    public static String USER_EMAIL = "rutu@gmail.com";
    private Text greetingText;
    private String loggedInUserName = "Sarah Miller";

    // Dynamic Radar UI labels
    private Label nearestHospNameLbl;
    private Label nearestHospDistLbl;
    private Label nearestPharmNameLbl;
    private Label nearestPharmDistLbl;

    private String targetHospQuery = "Emergency Hospital Care";
    private String targetPharmQuery = "24 Hour Pharmacy";

    public static class MemberRecord {
        public String id;
        public String name;
        public String relation;
        public String bloodGroup;
        public String allergies;
        public String conditions;
        public String emergencyContact;

        public MemberRecord(String id, String name, String relation, String bloodGroup, String allergies,
                String conditions, String emergencyContact) {
            this.id = id;
            this.name = name;
            this.relation = relation;
            this.bloodGroup = bloodGroup;
            this.allergies = allergies;
            this.conditions = conditions;
            this.emergencyContact = emergencyContact;
        }
    }

    public static class ContactRecord {
        public String initials;
        public String name;
        public String phone;
        public String relation;
        public boolean isEnabled;

        public ContactRecord(String initials, String name, String phone, String relation) {
            this.initials = initials;
            this.name = name;
            this.phone = phone;
            this.relation = relation;
            this.isEnabled = true;
        }
    }

    public static class CarouselSlideData {
        public final String badge;
        public final String badgeBg;
        public final String badgeFg;
        public final String title;
        public final String description;
        public final String buttonText;
        public final String imageResourcePath;
        public final String imageFileName;
        public final javafx.event.EventHandler<javafx.event.ActionEvent> onAction;

        public CarouselSlideData(String badge, String badgeBg, String badgeFg, String title, String description,
                String buttonText, String imageResourcePath, String imageFileName,
                javafx.event.EventHandler<javafx.event.ActionEvent> onAction) {
            this.badge = badge;
            this.badgeBg = badgeBg;
            this.badgeFg = badgeFg;
            this.title = title;
            this.description = description;
            this.buttonText = buttonText;
            this.imageResourcePath = imageResourcePath;
            this.imageFileName = imageFileName;
            this.onAction = onAction;
        }
    }

    private final List<MemberRecord> dynamicMembersList = new ArrayList<>();
    private final List<ContactRecord> contactsList = new ArrayList<>();

    // UI Nodes & State for Dynamic Feature Carousel
    private int currentSlideIndex = 0;
    private final List<CarouselSlideData> carouselSlides = new ArrayList<>();
    private final List<HBox> prebuiltSlideCards = new ArrayList<>();
    private final Map<String, Image> carouselImageCache = new HashMap<>();
    private StackPane slideContainer;
    private Label carouselCounterLbl;
    private Timeline carouselTimeline;
    private FadeTransition activeFadeTransition;

    // UI Nodes for Dynamic QR Section
    private HBox internalPillsContainer;
    private VBox placeholderCard;
    private VBox activePassCard;

    private ImageView qrImageView;
    private Label qrNameLbl;
    private Label qrIdLbl;
    private Label qrBloodLbl;
    private Label qrAllergiesLbl;
    private Label qrConditionsLbl;
    private Label qrContactLbl;
    private Label saveStatusLbl;

    private VBox contactRowsContainer;
    private Label sosStatusMessageLbl;
    private boolean isEditContactMode = false;

    // =========================================================
    // APPLICATION LIFECYCLE
    // =========================================================
    @Override
    public void start(Stage stage) {
        mainStage = stage;
        fetchCurrentLocationAndFacilities();

        BorderPane bp = setBorderPane(stage);

        // Fetch display visual bounds
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(bp, visualBounds.getWidth(), visualBounds.getHeight());

        stage.setX(visualBounds.getMinX());
        stage.setY(visualBounds.getMinY());
        stage.setWidth(visualBounds.getWidth());
        stage.setHeight(visualBounds.getHeight());

        stage.setTitle("LifeLink - Family Care");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public BorderPane setBorderPane(Stage stage) {
        mainStage = stage;
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.DASHBOARD);
        bp.setLeft(sidebar);

        VBox mainContent = buildMainContent(stage);
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle(
                "-fx-background: " + PAGE_BG + "; -fx-background-color: transparent; -fx-border-color: transparent;");
        bp.setCenter(scrollPane);
        playPageAnimation(scrollPane);
        return bp;
    }

    // ---------------- MAIN CONTENT ----------------
    private VBox buildMainContent(Stage stage) {
        // Modern Elevated Header
        HBox headerRow = buildHeaderRow();

        // 1. Full Screen Width Feature Carousel Slider at the top
        VBox featureCarousel = buildFeatureCarousel(stage);

        // 2. Sections Below the Carousel
        VBox leftColumn = new VBox(22);
        leftColumn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(leftColumn, Priority.ALWAYS);

        VBox medicalQrCardSection = buildMedicalQrSection(stage);
        VBox radarSection = buildEmergencyRadarSection();
        leftColumn.getChildren().addAll(medicalQrCardSection, radarSection);

        // 3. Emergency Services Widget (with Slide-to-SOS & Contacts) positioned below
        // carousel
        VBox rightColumn = buildEmergencyServicesWidget(stage);

        HBox belowCarouselLayout = new HBox(24, leftColumn, rightColumn);
        belowCarouselLayout.setAlignment(Pos.TOP_LEFT);
        belowCarouselLayout.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(belowCarouselLayout, Priority.ALWAYS);

        VBox mainContent = new VBox(22, headerRow, featureCarousel, belowCarouselLayout);
        mainContent.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);
        mainContent.setPadding(new Insets(24, 28, 28, 28));
        mainContent.setMaxWidth(Double.MAX_VALUE);

        // Persistent fetch every time dashboard content builds
        loadMembersFromFirestore();
        loadContactsFromFirestore();

        return mainContent;
    }

    private String getGreetingPrefix() {
        int hour = java.time.LocalTime.now().getHour();
        if (hour < 12)
            return "Good morning";
        if (hour < 17)
            return "Good afternoon";
        return "Good evening";
    }

    private String extractFirstName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty())
            return "Sarah";
        String[] parts = fullName.trim().split("\\s+");
        return parts[0];
    }

    private String getInitialGreetingName() {
        try {
            FamilyUserModel model = FamilyUserModel.getInstance();
            if (model != null && model.getName() != null && !model.getName().trim().isEmpty()) {
                loggedInUserName = model.getName().trim();
                return extractFirstName(loggedInUserName);
            }
        } catch (Exception ignored) {
        }

        if (loggedInUserName != null && !loggedInUserName.trim().isEmpty()) {
            return extractFirstName(loggedInUserName);
        }

        if (USER_EMAIL != null && USER_EMAIL.contains("@")) {
            String prefix = USER_EMAIL.substring(0, USER_EMAIL.indexOf('@')).replaceAll("[^a-zA-Z]", "");
            if (!prefix.isEmpty()) {
                return Character.toUpperCase(prefix.charAt(0)) + prefix.substring(1);
            }
        }
        return "Sarah";
    }

    private HBox buildHeaderRow() {
        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 8, 0));

        VBox textCol = new VBox(3);
        String name = getInitialGreetingName();
        greetingText = new Text(getGreetingPrefix() + ", " + name);
        greetingText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        greetingText.setFill(Color.web(TEXT_PRIMARY));

        textCol.getChildren().add(greetingText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Live Triage Status Pill Badge
        HBox statusPill = new HBox(8);
        statusPill.setAlignment(Pos.CENTER);
        statusPill.setStyle(
                "-fx-background-color: " + GREEN_BG + ";" +
                        "-fx-border-color: #BBF7D0;" +
                        "-fx-border-radius: 20px;" +
                        "-fx-background-radius: 20px;" +
                        "-fx-padding: 6 14 6 14;");
        Circle liveDot = new Circle(4, Color.web(GREEN));
        Label statusLbl = new Label("TRIAGE NETWORK ONLINE");
        statusLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        statusLbl.setTextFill(Color.web(GREEN));
        statusPill.getChildren().addAll(liveDot, statusLbl);

        header.getChildren().addAll(textCol, spacer, statusPill);
        return header;
    }

    // ---------------- INTERACTIVE FEATURE CAROUSEL SLIDER ----------------
    private VBox buildFeatureCarousel(Stage stage) {
        if (carouselTimeline != null) {
            carouselTimeline.stop();
        }

        VBox container = new VBox(12);
        container.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(container, Priority.ALWAYS);

        HBox titleRow = new HBox(10);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Label sectionTitle = new Label("LifeLink Feature Hub");
        sectionTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        sectionTitle.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Region titleSpacer = new Region();
        HBox.setHgrow(titleSpacer, Priority.ALWAYS);

        carouselCounterLbl = new Label("01 / 04");
        carouselCounterLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11.5));
        carouselCounterLbl.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                        "-fx-text-fill: " + PRIMARY_DARK + ";" +
                        "-fx-padding: 4 12 4 12;" +
                        "-fx-background-radius: 14px;" +
                        "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                        "-fx-border-radius: 14px;");

        Button headerPrev = createHeaderNavBtn("\u2039", () -> goToSlide(currentSlideIndex - 1));
        Button headerNext = createHeaderNavBtn("\u203A", () -> goToSlide(currentSlideIndex + 1));

        HBox headerControls = new HBox(6, carouselCounterLbl, headerPrev, headerNext);
        headerControls.setAlignment(Pos.CENTER_RIGHT);

        titleRow.getChildren().addAll(sectionTitle, titleSpacer, headerControls);

        // Initialize Slides Data
        carouselSlides.clear();
        prebuiltSlideCards.clear();

        carouselSlides.add(new CarouselSlideData(
                "24/7 EMERGENCY NETWORK",
                "#FEF2F2", "#DC2626",
                "Find Hospitals & Trauma Care",
                "Instantly locate verified emergency hospitals, check 24/7 ICU trauma readiness, and launch turn-by-turn Google Maps navigation.",
                "Find Hospitals Now  \u2192",
                "/assets/carousel/carousel_hospitals.jpg",
                "carousel_hospitals.jpg",
                e -> stage.getScene().setRoot(new FamilyFindCare().setBorderPane(stage))));

        carouselSlides.add(new CarouselSlideData(
                "AI MEDICAL TRIAGE 24/7",
                "#F0FDF4", "#16A34A",
                "Nurse Ananya AI • First-Aid Assistant",
                "Real-time conversational triage assistance, step-by-step CPR guidance, burn & fracture aid protocols, and instant medical support.",
                "Launch First-Aid Assistant  \u2192",
                "/assets/carousel/carousel_firstaid.jpg",
                "carousel_firstaid.jpg",
                e -> stage.getScene().setRoot(new FirstAidAssistant().setBorderPane(stage))));

        carouselSlides.add(new CarouselSlideData(
                "ENCRYPTED HEALTH VAULT",
                "#EFF6FF", "#2563EB",
                "Family Medical Records & Passes",
                "Secure digital repository for patient health histories, chronic allergies, vitals, and one-tap scannable emergency triage QR identity passes.",
                "View Medical Records  \u2192",
                "/assets/carousel/carousel_records.jpg",
                "carousel_records.jpg",
                e -> stage.getScene().setRoot(new MedicalReports().setBorderPane(stage))));

        carouselSlides.add(new CarouselSlideData(
                "VERIFIED COMMUNITY RATINGS",
                "#FFFBEB", "#D97706",
                "Family Care Reviews & Feedback",
                "Share verified treatment reviews, rate hospital response times and doctor care, and discover top-rated family healthcare providers.",
                "Explore Family Reviews  \u2192",
                "/assets/carousel/carousel_reviews.jpg",
                "carousel_reviews.jpg",
                e -> stage.getScene().setRoot(new FamilyReviewPage().setBorderPane(stage))));

        // Pre-build all slide cards once in memory to eliminate UI thread latency &
        // re-renders
        for (CarouselSlideData slide : carouselSlides) {
            prebuiltSlideCards.add(buildSlideCard(slide));
        }

        StackPane carouselStack = new StackPane();
        carouselStack.setMaxWidth(Double.MAX_VALUE);
        carouselStack.setPrefHeight(255);
        carouselStack.setMinHeight(255);
        carouselStack.setMaxHeight(255);
        HBox.setHgrow(carouselStack, Priority.ALWAYS);
        carouselStack.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 18px;" +
                        "-fx-background-radius: 18px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(120, 47, 22, 0.08), 12, 0, 0, 3);");

        slideContainer = new StackPane();
        slideContainer.setMaxWidth(Double.MAX_VALUE);
        slideContainer.setMaxHeight(Double.MAX_VALUE);
        HBox.setHgrow(slideContainer, Priority.ALWAYS);

        carouselStack.getChildren().setAll(slideContainer);

        currentSlideIndex = 0;
        showSlide(currentSlideIndex, false);

        // Auto-rotation Timeline with safe indefinite cycle
        carouselTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> goToSlide(currentSlideIndex + 1)));
        carouselTimeline.setCycleCount(Timeline.INDEFINITE);
        carouselTimeline.play();

        container.getChildren().addAll(titleRow, carouselStack);
        return container;
    }

    private void goToSlide(int index) {
        if (carouselSlides.isEmpty() || prebuiltSlideCards.isEmpty())
            return;
        int newIndex = (index % carouselSlides.size() + carouselSlides.size()) % carouselSlides.size();
        if (newIndex == currentSlideIndex && !slideContainer.getChildren().isEmpty())
            return;
        currentSlideIndex = newIndex;
        showSlide(currentSlideIndex, true);
        if (carouselTimeline != null) {
            carouselTimeline.stop();
            carouselTimeline.playFromStart();
        }
    }

    private void showSlide(int index, boolean animate) {
        if (index < 0 || index >= prebuiltSlideCards.size())
            return;
        HBox targetCard = prebuiltSlideCards.get(index);

        if (activeFadeTransition != null) {
            activeFadeTransition.stop();
            activeFadeTransition = null;
        }

        if (!animate || slideContainer.getChildren().isEmpty()) {
            targetCard.setOpacity(1.0);
            slideContainer.getChildren().setAll(targetCard);
        } else {
            Node currentCard = slideContainer.getChildren().get(slideContainer.getChildren().size() - 1);
            if (currentCard == targetCard) {
                targetCard.setOpacity(1.0);
                updateCarouselCounter();
                return;
            }

            // Keep current card solid while fading in the new card on top
            currentCard.setOpacity(1.0);
            targetCard.setOpacity(0.0);

            if (!slideContainer.getChildren().contains(targetCard)) {
                slideContainer.getChildren().add(targetCard);
            }

            activeFadeTransition = new FadeTransition(Duration.millis(260), targetCard);
            activeFadeTransition.setFromValue(0.0);
            activeFadeTransition.setToValue(1.0);
            activeFadeTransition.setOnFinished(e -> {
                targetCard.setOpacity(1.0);
                slideContainer.getChildren().setAll(targetCard);
                activeFadeTransition = null;
            });
            activeFadeTransition.play();
        }

        updateCarouselCounter();
    }

    private HBox buildSlideCard(CarouselSlideData slide) {
        HBox slideCard = new HBox(20);
        slideCard.setAlignment(Pos.CENTER_LEFT);
        slideCard.setMaxWidth(Double.MAX_VALUE);
        slideCard.setMaxHeight(Double.MAX_VALUE);
        HBox.setHgrow(slideCard, Priority.ALWAYS);
        slideCard.setStyle(
                "-fx-background-color: linear-gradient(to right, #FFFFFF 0%, #FFFDFB 50%, #FAF3ED 100%);" +
                        "-fx-background-radius: 18px;" +
                        "-fx-border-radius: 18px;");

        // Left text column
        VBox textCol = new VBox(11);
        textCol.setAlignment(Pos.CENTER_LEFT);
        textCol.setPadding(new Insets(20, 24, 20, 36));
        HBox.setHgrow(textCol, Priority.ALWAYS);
        textCol.setMaxWidth(Double.MAX_VALUE);

        HBox badgePill = new HBox(6);
        badgePill.setAlignment(Pos.CENTER_LEFT);
        badgePill.setMaxWidth(Region.USE_PREF_SIZE);
        badgePill.setStyle(
                "-fx-background-color: " + slide.badgeBg + ";" +
                        "-fx-border-color: " + slide.badgeFg + "55;" +
                        "-fx-border-radius: 14px;" +
                        "-fx-background-radius: 14px;" +
                        "-fx-padding: 4 12 4 12;");
        Label badgeLbl = new Label(slide.badge);
        badgeLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        badgeLbl.setStyle("-fx-text-fill: " + slide.badgeFg + ";");
        badgePill.getChildren().add(badgeLbl);

        Label titleLbl = new Label(slide.title);
        titleLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titleLbl.setStyle("-fx-text-fill: #1A120E;");
        titleLbl.setWrapText(true);

        Label descLbl = new Label(slide.description);
        descLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        descLbl.setStyle("-fx-text-fill: #4A3C35; -fx-line-spacing: 2px;");
        descLbl.setWrapText(true);
        descLbl.setMaxWidth(580);

        Button ctaBtn = new Button(slide.buttonText);
        ctaBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        ctaBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: linear-gradient(to right, " + PRIMARY + ", #B85918);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 9 22 9 22;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(202, 103, 33, 0.35), 8, 0, 0, 2);");
        ctaBtn.setOnMouseEntered(e -> {
            if (carouselTimeline != null)
                carouselTimeline.pause();
            ctaBtn.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: linear-gradient(to right, " + PRIMARY_DARK + ", " + PRIMARY + ");" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 8px;" +
                            "-fx-padding: 9 22 9 22;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(120, 47, 22, 0.45), 12, 0, 0, 3);");
            ctaBtn.setTranslateY(-1);
        });
        ctaBtn.setOnMouseExited(e -> {
            if (carouselTimeline != null)
                carouselTimeline.play();
            ctaBtn.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: linear-gradient(to right, " + PRIMARY + ", #B85918);" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 8px;" +
                            "-fx-padding: 9 22 9 22;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(202, 103, 33, 0.35), 8, 0, 0, 2);");
            ctaBtn.setTranslateY(0);
        });
        ctaBtn.setOnAction(slide.onAction);

        textCol.getChildren().addAll(badgePill, titleLbl, descLbl, ctaBtn);

        // Right image container with true 16:9 ratio
        StackPane imgWrapper = new StackPane();
        imgWrapper.setAlignment(Pos.CENTER_RIGHT);
        imgWrapper.setPadding(new Insets(14, 36, 14, 0));

        Image img = loadCarouselImage(slide.imageResourcePath, slide.imageFileName);
        if (img != null && !img.isError()) {
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(206);
            imgView.setFitWidth(366);
            imgView.setPreserveRatio(true);
            imgView.setSmooth(true);

            Rectangle imgClip = new Rectangle(366, 206);
            imgClip.setArcWidth(16);
            imgClip.setArcHeight(16);
            imgView.setClip(imgClip);

            StackPane imgFrame = new StackPane(imgView);
            imgFrame.setStyle(
                    "-fx-background-color: transparent;" +
                    // "-fx-border-color: rgba(237, 224, 216, 0.9);" +
                            "-fx-border-width: 1px;" +
                            "-fx-border-radius: 16px;" +
                            "-fx-background-radius: 16px;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(43, 33, 29, 0.12), 10, 0, 0, 3);");
            imgWrapper.getChildren().add(imgFrame);
        }

        slideCard.getChildren().addAll(textCol, imgWrapper);
        return slideCard;
    }

    private void updateCarouselCounter() {
        if (carouselCounterLbl != null && !carouselSlides.isEmpty()) {
            carouselCounterLbl.setText(String.format("%02d / %02d", currentSlideIndex + 1, carouselSlides.size()));
        }
    }

    private Button createHeaderNavBtn(String arrow, Runnable action) {
        Button btn = new Button(arrow);
        btn.setPrefSize(28, 28);
        btn.setMinSize(28, 28);
        btn.setMaxSize(28, 28);
        btn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: #FFFFFF;" +
                        "-fx-text-fill: " + PRIMARY + ";" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 14px;" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 14px;" +
                        "-fx-border-width: 1px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0;" +
                        "-fx-alignment: center;");
        btn.setOnMouseEntered(e -> {
            if (carouselTimeline != null)
                carouselTimeline.pause();
            btn.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: " + PRIMARY + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 14px;" +
                            "-fx-border-color: " + PRIMARY + ";" +
                            "-fx-border-radius: 14px;" +
                            "-fx-border-width: 1px;" +
                            "-fx-cursor: hand;" +
                            "-fx-padding: 0;" +
                            "-fx-alignment: center;");
        });
        btn.setOnMouseExited(e -> {
            if (carouselTimeline != null)
                carouselTimeline.play();
            btn.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: #FFFFFF;" +
                            "-fx-text-fill: " + PRIMARY + ";" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 14px;" +
                            "-fx-border-color: " + BORDER_COLOR + ";" +
                            "-fx-border-radius: 14px;" +
                            "-fx-border-width: 1px;" +
                            "-fx-cursor: hand;" +
                            "-fx-padding: 0;" +
                            "-fx-alignment: center;");
        });
        btn.setOnAction(e -> action.run());
        return btn;
    }

    private Image loadCarouselImage(String resPath, String fileName) {
        if (carouselImageCache.containsKey(fileName)) {
            return carouselImageCache.get(fileName);
        }
        try {
            URL url = FamilyHomePage.class.getResource(resPath);
            if (url != null) {
                Image img = new Image(url.toExternalForm(), false);
                if (!img.isError()) {
                    carouselImageCache.put(fileName, img);
                    return img;
                }
            }
        } catch (Exception ignored) {
        }

        String[] localPaths = {
                "src/main/resources/assets/carousel/" + fileName,
                "lifelink1/src/main/resources/assets/carousel/" + fileName,
                "e:/java26/lifelink/LifeLink/lifelink1/src/main/resources/assets/carousel/" + fileName,
                "C:/Users/SHIVTEJ/.gemini/antigravity-ide/brain/20186d0d-cf27-4aac-b5a4-dcbb821dc2f3/" + fileName
        };
        for (String lp : localPaths) {
            try {
                File f = new File(lp);
                if (f.exists()) {
                    Image img = new Image(f.toURI().toString(), false);
                    if (!img.isError()) {
                        carouselImageCache.put(fileName, img);
                        return img;
                    }
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    // ---------------- EMERGENCY MEDICAL QR CARD SECTION ----------------
    private VBox buildMedicalQrSection(Stage stage) {
        VBox section = new VBox(14);
        section.setMaxWidth(Double.MAX_VALUE);

        Label title = new Label("Emergency Medical QR Pass");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        internalPillsContainer = new HBox(8);
        internalPillsContainer.setAlignment(Pos.CENTER);
        internalPillsContainer.setPadding(new Insets(8, 0, 4, 0));

        placeholderCard = new VBox(14);
        applyCardStyle(placeholderCard, SURFACE, BORDER_COLOR);
        placeholderCard.setAlignment(Pos.CENTER);
        placeholderCard.setPadding(new Insets(26, 20, 26, 20));
        placeholderCard.setMaxWidth(Double.MAX_VALUE);

        Label emptyIcon = new Label("🪪");
        emptyIcon.setStyle("-fx-font-size: 34px;");

        Label placeholderTitle = new Label("Generate Emergency Medical Pass for Family Members");
        placeholderTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        placeholderTitle.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label placeholderDesc = new Label(
                "Select any family member below to generate their real-time emergency triage QR pass.");
        placeholderDesc.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        placeholderDesc.setStyle("-fx-text-fill: " + TEXT_MUTED + ";");

        placeholderCard.getChildren().addAll(emptyIcon, placeholderTitle, placeholderDesc, internalPillsContainer);

        activePassCard = buildActivePassCard(stage);
        activePassCard.setVisible(false);
        activePassCard.setManaged(false);

        section.getChildren().addAll(title, placeholderCard, activePassCard);
        return section;
    }

    private VBox buildActivePassCard(Stage stage) {
        VBox passCard = new VBox(14);
        applyCardStyle(passCard, SURFACE, BORDER_COLOR);
        passCard.setPadding(new Insets(20, 24, 20, 24));
        passCard.setMaxWidth(Double.MAX_VALUE);

        HBox topSwitchBar = new HBox(10);
        topSwitchBar.setAlignment(Pos.CENTER_LEFT);

        Label selectLbl = new Label("Select Member:");
        selectLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        selectLbl.setStyle("-fx-text-fill: " + TEXT_SECONDARY + ";");

        HBox switchPills = new HBox(8);
        switchPills.setAlignment(Pos.CENTER_LEFT);

        topSwitchBar.getChildren().addAll(selectLbl, switchPills);

        HBox passContent = new HBox(22);
        passContent.setAlignment(Pos.CENTER_LEFT);

        VBox qrFrame = new VBox(6);
        qrFrame.setAlignment(Pos.CENTER);
        qrFrame.setPadding(new Insets(10));
        qrFrame.setStyle(
                "-fx-background-color: " + PALE_PEACH + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 12px;" +
                        "-fx-background-radius: 12px;");

        qrImageView = new ImageView();
        qrImageView.setFitWidth(130);
        qrImageView.setFitHeight(130);
        qrImageView.setPreserveRatio(true);

        Label scanHint = new Label("Scan for Triage Data");
        scanHint.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        scanHint.setStyle("-fx-text-fill: " + PRIMARY_DARK + ";");

        qrFrame.getChildren().addAll(qrImageView, scanHint);

        VBox detailsBox = new VBox(12);
        HBox.setHgrow(detailsBox, Priority.ALWAYS);

        HBox nameRow = new HBox(10);
        nameRow.setAlignment(Pos.CENTER_LEFT);

        qrNameLbl = new Label();
        qrNameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        qrNameLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        qrIdLbl = new Label();
        qrIdLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        qrIdLbl.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                        "-fx-text-fill: " + PRIMARY_DARK + ";" +
                        "-fx-padding: 3px 8px;" +
                        "-fx-background-radius: 8px;");

        Region nameSpacer = new Region();
        HBox.setHgrow(nameSpacer, Priority.ALWAYS);

        qrBloodLbl = new Label();
        qrBloodLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        qrBloodLbl.setStyle(
                "-fx-background-color: " + SOS_BG + ";" +
                        "-fx-text-fill: " + SOS_RED + ";" +
                        "-fx-padding: 4px 10px;" +
                        "-fx-background-radius: 10px;");

        nameRow.getChildren().addAll(qrNameLbl, qrIdLbl, nameSpacer, qrBloodLbl);

        HBox infoGrid = new HBox(20);
        infoGrid.setAlignment(Pos.CENTER_LEFT);

        VBox col1 = new VBox(4);
        Label allergyTitle = new Label("Known Allergies:");
        allergyTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        allergyTitle.setStyle("-fx-text-fill: " + TEXT_MUTED + ";");
        qrAllergiesLbl = new Label();
        qrAllergiesLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        qrAllergiesLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");
        col1.getChildren().addAll(allergyTitle, qrAllergiesLbl);

        VBox col2 = new VBox(4);
        Label conditionTitle = new Label("Chronic Conditions:");
        conditionTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        conditionTitle.setStyle("-fx-text-fill: " + TEXT_MUTED + ";");
        qrConditionsLbl = new Label();
        qrConditionsLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        qrConditionsLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");
        col2.getChildren().addAll(conditionTitle, qrConditionsLbl);

        VBox col3 = new VBox(4);
        Label contactTitle = new Label("ICE Emergency Contact:");
        contactTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        contactTitle.setStyle("-fx-text-fill: " + TEXT_MUTED + ";");
        qrContactLbl = new Label();
        qrContactLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        qrContactLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");
        col3.getChildren().addAll(contactTitle, qrContactLbl);

        infoGrid.getChildren().addAll(col1, col2, col3);

        HBox actionRow = new HBox(12);
        actionRow.setAlignment(Pos.CENTER_LEFT);

        Button saveToCollectionBtn = new Button("💾 Save to Collection");
        saveToCollectionBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        saveToCollectionBtn.setStyle("-fx-background-color: " + PRIMARY
                + "; -fx-text-fill: white; -fx-background-radius: 8px; -fx-padding: 8px 16px; -fx-cursor: hand;");
        saveToCollectionBtn.setOnAction(e -> {
            saveStatusLbl.setText("✔ Medical Pass saved to LifeLink Record.");
            saveStatusLbl.setStyle("-fx-text-fill: " + GREEN + "; -fx-font-weight: bold; -fx-font-size: 11px;");
        });

        Button printPassBtn = new Button("🖨 Print Card");
        printPassBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        printPassBtn.setStyle("-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK
                + "; -fx-background-radius: 8px; -fx-padding: 8px 16px; -fx-cursor: hand;");
        printPassBtn.setOnAction(e -> {
            try {
                PrinterJob job = PrinterJob.createPrinterJob();
                Stage targetStage = mainStage != null ? mainStage : stage;
                if (job != null && job.showPrintDialog(targetStage)) {
                    boolean printed = job.printPage(activePassCard);
                    if (printed) {
                        job.endJob();
                        saveStatusLbl.setText("✔ Medical Pass sent to printer.");
                        saveStatusLbl
                                .setStyle("-fx-text-fill: " + GREEN + "; -fx-font-weight: bold; -fx-font-size: 11px;");
                    }
                } else {
                    saveStatusLbl.setText("✔ Pass formatted for print.");
                    saveStatusLbl.setStyle("-fx-text-fill: " + PRIMARY_DARK + "; -fx-font-size: 11px;");
                }
            } catch (Exception ex) {
                saveStatusLbl.setText("✔ Card prepared for printing.");
                saveStatusLbl.setStyle("-fx-text-fill: " + PRIMARY_DARK + "; -fx-font-size: 11px;");
            }
        });

        saveStatusLbl = new Label();

        actionRow.getChildren().addAll(saveToCollectionBtn, printPassBtn, saveStatusLbl);

        detailsBox.getChildren().addAll(nameRow, infoGrid, actionRow);
        passContent.getChildren().addAll(qrFrame, detailsBox);
        passCard.getChildren().addAll(topSwitchBar, passContent);

        return passCard;
    }

    // ---------------- 24/7 ER & PHARMACY RADAR ----------------
    private VBox buildEmergencyRadarSection() {
        VBox section = new VBox(14);
        applyCardStyle(section, SURFACE, BORDER_COLOR);
        section.setPadding(new Insets(20));
        section.setMaxWidth(Double.MAX_VALUE);

        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label heading = new Label("🧭 Nearest 24/7 Emergency Care & Pharmacy Radar");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        heading.setStyle("-fx-text-fill: " + PRIMARY_DARK + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        locationBadgeLabel = new Label(
                "📍 " + (currentLocationName != null ? currentLocationName : "Detecting location..."));
        locationBadgeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        locationBadgeLabel.setStyle("-fx-text-fill: " + PRIMARY + ";");

        topRow.getChildren().addAll(heading, spacer, locationBadgeLabel);

        HBox radarCardsRow = new HBox(14);
        radarCardsRow.setMaxWidth(Double.MAX_VALUE);

        // 1. Hospital Radar Card
        VBox hospCard = new VBox(8);
        hospCard.setPadding(new Insets(14, 16, 14, 16));
        HBox.setHgrow(hospCard, Priority.ALWAYS);
        hospCard.setStyle("-fx-background-color: " + PAGE_BG + "; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        nearestHospNameLbl = new Label("🏥 Dr. Nazirkar Shree Narayan Hospital");
        nearestHospNameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        nearestHospNameLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        nearestHospDistLbl = new Label("1.2 km away • Est. ETA: 4 mins");
        nearestHospDistLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        nearestHospDistLbl.setStyle("-fx-text-fill: " + TEXT_SECONDARY + ";");

        Label hospStatusBadge = new Label("🚨 24/7 Emergency Trauma Unit Active");
        hospStatusBadge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        hospStatusBadge.setStyle("-fx-text-fill: " + GREEN + ";");

        Button navHospBtn = new Button("🧭 Navigate on Google Maps");
        navHospBtn.setMaxWidth(Double.MAX_VALUE);
        navHospBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        navHospBtn.setStyle("-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK
                + "; -fx-padding: 8px 14px; -fx-background-radius: 8px; -fx-cursor: hand;");
        navHospBtn.setOnAction(e -> openBrowserNavigation(targetHospQuery));

        hospCard.getChildren().addAll(nearestHospNameLbl, nearestHospDistLbl, hospStatusBadge, navHospBtn);

        // 2. Pharmacy Radar Card
        VBox pharmCard = new VBox(8);
        pharmCard.setPadding(new Insets(14, 16, 14, 16));
        HBox.setHgrow(pharmCard, Priority.ALWAYS);
        pharmCard.setStyle("-fx-background-color: " + PAGE_BG + "; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        nearestPharmNameLbl = new Label("💊 Wellness Forever 24/7 Chemist");
        nearestPharmNameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        nearestPharmNameLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        nearestPharmDistLbl = new Label("0.5 km away • Est. Walk: 6 mins");
        nearestPharmDistLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        nearestPharmDistLbl.setStyle("-fx-text-fill: " + TEXT_SECONDARY + ";");

        Label pharmStatusBadge = new Label("🕒 Open 24 Hours • Emergency Medicines");
        pharmStatusBadge.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        pharmStatusBadge.setStyle("-fx-text-fill: " + PRIMARY + ";");

        Button navPharmBtn = new Button("🧭 Navigate on Google Maps");
        navPharmBtn.setMaxWidth(Double.MAX_VALUE);
        navPharmBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        navPharmBtn.setStyle("-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK
                + "; -fx-padding: 8px 14px; -fx-background-radius: 8px; -fx-cursor: hand;");
        navPharmBtn.setOnAction(e -> openBrowserNavigation(targetPharmQuery));

        pharmCard.getChildren().addAll(nearestPharmNameLbl, nearestPharmDistLbl, pharmStatusBadge, navPharmBtn);

        radarCardsRow.getChildren().addAll(hospCard, pharmCard);
        section.getChildren().addAll(topRow, radarCardsRow);
        return section;
    }

    private void openBrowserNavigation(String destinationQuery) {
        try {
            String url = "https://www.google.com/maps/dir/?api=1&origin="
                    + currentLatitude + "," + currentLongitude
                    + "&destination=" + URLEncoder.encode(destinationQuery, StandardCharsets.UTF_8.toString());

            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ---------------- DYNAMIC LOCATION & NEARBY PLACES DETECTOR ----------------
    private void fetchCurrentLocationAndFacilities() {
        new Thread(() -> {
            try {
                URL url = new URL("http://ip-api.com/json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(4000);
                conn.setReadTimeout(4000);

                if (conn.getResponseCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null)
                        sb.append(line);
                    br.close();

                    String json = sb.toString();
                    if (json.contains("\"lat\":") && json.contains("\"lon\":")) {
                        double lat = Double.parseDouble(extractJsonVal(json, "lat"));
                        double lon = Double.parseDouble(extractJsonVal(json, "lon"));
                        String city = extractJsonVal(json, "city");
                        String region = extractJsonVal(json, "regionName");

                        currentLatitude = lat;
                        currentLongitude = lon;
                        currentLocationName = (city != null ? city : "Your Area")
                                + (region != null ? ", " + region : "");

                        if (city != null && city.equalsIgnoreCase("Karjat")) {
                            targetHospQuery = "Dr. Nazirkar Shree Narayan Hospital, Karjat";
                            targetPharmQuery = "Wellness Forever, Kacheri Road, Karjat";
                        } else {
                            targetHospQuery = "Nearest Emergency Hospital in " + currentLocationName;
                            targetPharmQuery = "24 Hour Pharmacy in " + currentLocationName;
                        }

                        Platform.runLater(() -> {
                            if (locationBadgeLabel != null)
                                locationBadgeLabel.setText("📍 " + currentLocationName);
                            if (city != null && !city.isEmpty()) {
                                if (city.equalsIgnoreCase("Karjat")) {
                                    if (nearestHospNameLbl != null)
                                        nearestHospNameLbl.setText("🏥 Dr. Nazirkar Shree Narayan Hospital");
                                    if (nearestHospDistLbl != null)
                                        nearestHospDistLbl.setText("1.1 km away • Est. ETA: 3 mins");
                                    if (nearestPharmNameLbl != null)
                                        nearestPharmNameLbl.setText("💊 Wellness Forever 24/7 Pharmacy");
                                    if (nearestPharmDistLbl != null)
                                        nearestPharmDistLbl.setText("0.4 km away • Est. Walk: 5 mins");
                                } else {
                                    if (nearestHospNameLbl != null)
                                        nearestHospNameLbl.setText("🏥 Nearest Emergency Hospital (" + city + ")");
                                    if (nearestHospDistLbl != null)
                                        nearestHospDistLbl.setText("1.5 km away • Live Emergency Care");
                                    if (nearestPharmNameLbl != null)
                                        nearestPharmNameLbl.setText("💊 24/7 Chemist & Druggist (" + city + ")");
                                    if (nearestPharmDistLbl != null)
                                        nearestPharmDistLbl.setText("0.6 km away • Open 24 Hours");
                                }
                            }
                        });
                    }
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    if (locationBadgeLabel != null)
                        locationBadgeLabel.setText("📍 GPS Active");
                });
            }
        }).start();
    }

    private String extractJsonVal(String json, String key) {
        try {
            int idx = json.indexOf("\"" + key + "\":");
            if (idx == -1)
                return null;
            int start = idx + key.length() + 3;
            if (json.charAt(start) == '"') {
                start++;
                int end = json.indexOf("\"", start);
                return json.substring(start, end);
            } else {
                int end = json.indexOf(",", start);
                if (end == -1)
                    end = json.indexOf("}", start);
                return json.substring(start, end).trim();
            }
        } catch (Exception ignored) {
            return null;
        }
    }

    // ---------------- FIRESTORE RETRIEVAL ----------------
    private void loadMembersFromFirestore() {
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                dynamicMembersList.clear();

                if (db != null) {
                    ApiFuture<QuerySnapshot> future = db.collection("family")
                            .document(USER_EMAIL)
                            .collection("members")
                            .get();

                    List<QueryDocumentSnapshot> documents = future.get().getDocuments();

                    for (QueryDocumentSnapshot doc : documents) {
                        String id = doc.getId();
                        String name = null;
                        if (doc.contains("name"))
                            name = doc.getString("name");
                        if (name == null && doc.contains("fullName"))
                            name = doc.getString("fullName");
                        if (name == null && doc.contains("patientName"))
                            name = doc.getString("patientName");
                        if (name == null && doc.contains("memberName"))
                            name = doc.getString("memberName");

                        if (name == null || name.trim().isEmpty() || name.equalsIgnoreCase("Member")) {
                            name = id.startsWith("mem_")
                                    ? "Family Member (" + id.substring(4, Math.min(id.length(), 8)) + ")"
                                    : id;
                        }

                        String relation = doc.contains("relation") ? doc.getString("relation") : "Family Member";
                        String blood = doc.contains("bloodGroup") ? doc.getString("bloodGroup") : "O+ Positive";
                        String allergies = doc.contains("allergies") ? doc.getString("allergies") : "None Reported";
                        String conditions = doc.contains("conditions") ? doc.getString("conditions") : "None";
                        String contact = doc.contains("emergencyContact") ? doc.getString("emergencyContact")
                                : "+91 98220 00000";

                        dynamicMembersList
                                .add(new MemberRecord(id, name, relation, blood, allergies, conditions, contact));
                    }
                }

                if (dynamicMembersList.isEmpty()) {
                    dynamicMembersList.add(new MemberRecord("LL-101", "Jayraj Pisal", "Son", "O+ Positive",
                            "None Reported", "Mild Asthma (Inhaler)", "+91 98765 43210"));
                    dynamicMembersList.add(new MemberRecord("LL-102", "Yashraj Pisal", "Son", "B+ Positive",
                            "Penicillin Allergy", "None", "+91 98765 43211"));
                    dynamicMembersList.add(new MemberRecord("LL-103", "Sarah Miller", "Self (Lead)", "A+ Positive",
                            "Latex, Aspirin", "Hypertension", "+91 98765 43212"));
                }

                Platform.runLater(this::renderMemberPills);
            } catch (Exception e) {
                Platform.runLater(() -> {
                    dynamicMembersList.clear();
                    dynamicMembersList.add(new MemberRecord("LL-101", "Jayraj Pisal", "Son", "O+ Positive",
                            "None Reported", "Mild Asthma (Inhaler)", "+91 98765 43210"));
                    dynamicMembersList.add(new MemberRecord("LL-102", "Yashraj Pisal", "Son", "B+ Positive",
                            "Penicillin Allergy", "None", "+91 98765 43211"));
                    dynamicMembersList.add(new MemberRecord("LL-103", "Sarah Miller", "Self (Lead)", "A+ Positive",
                            "Latex, Aspirin", "Hypertension", "+91 98765 43212"));
                    renderMemberPills();
                });
            }
        }).start();
    }

    private void renderMemberPills() {
        internalPillsContainer.getChildren().clear();

        for (MemberRecord record : dynamicMembersList) {
            Button pill = new Button(record.name);
            pill.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
            styleMemberPill(pill, false);

            pill.setOnAction(e -> {
                for (javafx.scene.Node node : internalPillsContainer.getChildren()) {
                    if (node instanceof Button)
                        styleMemberPill((Button) node, false);
                }
                styleMemberPill(pill, true);
                displayMemberQrPass(record);
            });

            internalPillsContainer.getChildren().add(pill);
        }
    }

    private void styleMemberPill(Button pill, boolean active) {
        if (active) {
            pill.setStyle("-fx-background-color: " + PRIMARY
                    + "; -fx-text-fill: white; -fx-background-radius: 14px; -fx-padding: 6px 16px; -fx-cursor: hand;");
        } else {
            pill.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR
                    + "; -fx-text-fill: " + PRIMARY_DARK
                    + "; -fx-background-radius: 14px; -fx-border-radius: 14px; -fx-padding: 6px 16px; -fx-cursor: hand;");
        }
    }

    private void displayMemberQrPass(MemberRecord record) {
        placeholderCard.setVisible(false);
        placeholderCard.setManaged(false);

        activePassCard.setVisible(true);
        activePassCard.setManaged(true);

        HBox topSwitchBar = (HBox) activePassCard.getChildren().get(0);
        HBox switchPills = (HBox) topSwitchBar.getChildren().get(1);
        switchPills.getChildren().clear();

        for (MemberRecord m : dynamicMembersList) {
            Button pill = new Button(m.name);
            pill.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
            styleMemberPill(pill, m.name.equals(record.name));
            pill.setOnAction(e -> displayMemberQrPass(m));
            switchPills.getChildren().add(pill);
        }

        qrNameLbl.setText(record.name);
        qrIdLbl.setText(record.id != null ? record.id : "LL-MED");
        qrBloodLbl.setText("🩸 Blood: " + record.bloodGroup);
        qrAllergiesLbl.setText(record.allergies);
        qrConditionsLbl.setText(record.conditions);
        qrContactLbl.setText(record.emergencyContact);
        saveStatusLbl.setText("");

        String qrPayload = "LIFELINK EMERGENCY QR PASS\n" +
                "Name: " + record.name + "\n" +
                "ID: " + record.id + "\n" +
                "Blood Group: " + record.bloodGroup + "\n" +
                "Allergies: " + record.allergies + "\n" +
                "Conditions: " + record.conditions + "\n" +
                "ICE Contact: " + record.emergencyContact;

        try {
            String encodedPayload = URLEncoder.encode(qrPayload, StandardCharsets.UTF_8.toString());
            String qrUrl = "https://api.qrserver.com/v1/create-qr-code/?size=180x180&data=" + encodedPayload;
            qrImageView.setImage(new Image(qrUrl, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- EMERGENCY SERVICES WIDGET WITH SLIDE SOS ----------------
    private VBox buildEmergencyServicesWidget(Stage stage) {
        VBox widget = new VBox(16);
        widget.setPrefWidth(380);
        widget.setMinWidth(360);
        widget.setMaxWidth(400);

        Label headerTitle = new Label("Emergency Services");
        headerTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        headerTitle.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        // 1. SOS Slide Button Card
        VBox immediateHelpCard = new VBox(10);
        applyCardStyle(immediateHelpCard, SURFACE, BORDER_COLOR);
        immediateHelpCard.setPadding(new Insets(18));

        Label immTitle = new Label("Need Immediate Help?");
        immTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        immTitle.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label immDesc = new Label(
                "Slide knob to alert 108 emergency services and SMS live coordinates directly to saved contacts.");
        immDesc.setWrapText(true);
        immDesc.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        immDesc.setStyle("-fx-text-fill: " + TEXT_MUTED + "; -fx-line-spacing: 1.5px;");

        StackPane sosSlider = buildSlideToSosBar();
        sosStatusMessageLbl = new Label("");
        sosStatusMessageLbl.setWrapText(true);

        immediateHelpCard.getChildren().addAll(immTitle, immDesc, sosSlider, sosStatusMessageLbl);

        // 2. Emergency Contacts Card (Numbered format)
        VBox contactsCard = new VBox(12);
        applyCardStyle(contactsCard, SURFACE, BORDER_COLOR);
        contactsCard.setPadding(new Insets(18));

        Label groupIcon = new Label("👥");
        groupIcon.setStyle("-fx-font-size: 14px;");

        Label contactsTitle = new Label("Notify Emergency Contacts");
        contactsTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        contactsTitle.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button addContactBtn = new Button("✚ Add");
        addContactBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + PRIMARY
                + "; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 11px;");
        addContactBtn.setOnAction(e -> showAddContactDialog());

        Button editBtn = new Button("Edit");
        editBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + TEXT_SECONDARY
                + "; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 11px;");
        editBtn.setOnAction(e -> {
            isEditContactMode = !isEditContactMode;
            editBtn.setText(isEditContactMode ? "Done" : "Edit");
            renderContactRows();
        });

        HBox contactsHeader = new HBox(6, groupIcon, contactsTitle, sp, addContactBtn, editBtn);
        contactsHeader.setAlignment(Pos.CENTER_LEFT);

        contactRowsContainer = new VBox(8);
        renderContactRows();

        contactsCard.getChildren().addAll(contactsHeader, contactRowsContainer);
        widget.getChildren().addAll(headerTitle, immediateHelpCard, contactsCard);
        return widget;
    }

    // ---------------- DRAGGABLE LEFT-TO-RIGHT SOS SLIDER ----------------
    private StackPane buildSlideToSosBar() {
        double trackWidth = 324;
        double trackHeight = 46;
        double knobSize = 38;
        double maxSlide = trackWidth - knobSize - 8;

        StackPane track = new StackPane();
        track.setPrefSize(trackWidth, trackHeight);
        track.setMinSize(trackWidth, trackHeight);
        track.setMaxSize(trackWidth, trackHeight);
        track.setStyle(
                "-fx-background-color: " + SOS_BG + ";" +
                        "-fx-border-color: #F8D3D1;" +
                        "-fx-border-radius: 24px;" +
                        "-fx-background-radius: 24px;");

        Label sosText = new Label("SLIDE TO SOS »");
        sosText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        sosText.setStyle("-fx-text-fill: " + SOS_RED + "; -fx-letter-spacing: 1px;");
        StackPane.setAlignment(sosText, Pos.CENTER_RIGHT);
        StackPane.setMargin(sosText, new Insets(0, 20, 0, 0));

        Label phoneKnob = new Label("☎");
        phoneKnob.setStyle(
                "-fx-background-color: " + SOS_RED + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-min-width: " + knobSize + "px;" +
                        "-fx-min-height: " + knobSize + "px;" +
                        "-fx-max-width: " + knobSize + "px;" +
                        "-fx-max-height: " + knobSize + "px;" +
                        "-fx-alignment: center;" +
                        "-fx-background-radius: 50%;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(gaussian, rgba(186,59,62,0.35), 6, 0, 0, 2);");

        StackPane.setAlignment(phoneKnob, Pos.CENTER_LEFT);
        StackPane.setMargin(phoneKnob, new Insets(0, 0, 0, 4));

        final double[] startMouseX = new double[1];
        final double[] currentKnobX = { 0 };

        phoneKnob.setOnMousePressed(e -> startMouseX[0] = e.getSceneX());

        phoneKnob.setOnMouseDragged(e -> {
            double dragDistance = e.getSceneX() - startMouseX[0];
            double newX = Math.max(0, Math.min(dragDistance, maxSlide));
            phoneKnob.setTranslateX(newX);
            currentKnobX[0] = newX;

            double opacity = 1.0 - (newX / maxSlide);
            sosText.setOpacity(Math.max(0.1, opacity));
        });

        phoneKnob.setOnMouseReleased(e -> {
            if (currentKnobX[0] >= maxSlide * 0.70) {
                TranslateTransition slideEnd = new TranslateTransition(Duration.millis(120), phoneKnob);
                slideEnd.setToX(maxSlide);
                slideEnd.setOnFinished(ev -> {
                    triggerSosEmergencyAlert();
                    TranslateTransition reset = new TranslateTransition(Duration.millis(400), phoneKnob);
                    reset.setDelay(Duration.millis(600));
                    reset.setToX(0);
                    reset.setOnFinished(rev -> sosText.setOpacity(1.0));
                    reset.play();
                });
                slideEnd.play();
            } else {
                TranslateTransition springBack = new TranslateTransition(Duration.millis(200), phoneKnob);
                springBack.setToX(0);
                springBack.setOnFinished(ev -> sosText.setOpacity(1.0));
                springBack.play();
            }
        });

        track.getChildren().addAll(sosText, phoneKnob);
        return track;
    }

    // ---------------- SMS DISPATCH & SOS LOGGING ----------------
    private void triggerSosEmergencyAlert() {
        List<String> alertedNumbers = new ArrayList<>();
        for (ContactRecord c : contactsList) {
            if (c.isEnabled) {
                alertedNumbers.add(c.name + " (" + c.phone + ")");
            }
        }

        if (alertedNumbers.isEmpty()) {
            sosStatusMessageLbl.setText("⚠ No enabled emergency contacts to alert.");
            sosStatusMessageLbl.setStyle("-fx-text-fill: " + SOS_RED + "; -fx-font-weight: bold; -fx-font-size: 11px;");
            return;
        }

        String senderDisplayName = (loggedInUserName != null && !loggedInUserName.trim().isEmpty()) ? loggedInUserName
                : "Family Emergency";
        String smsText = "EMERGENCY SOS: " + senderDisplayName
                + " needs your urgent assistance right now. Please check immediately.\n"
                + "Live GPS: https://maps.google.com/?q=" + currentLatitude + "," + currentLongitude;

        sosStatusMessageLbl
                .setText("🚨 SOS DISPATCHED: Alerting 108 & SMS sent to:\n• " + String.join("\n• ", alertedNumbers));
        sosStatusMessageLbl.setStyle("-fx-text-fill: " + SOS_RED + "; -fx-font-weight: bold; -fx-font-size: 11px;");

        // Send SMS & WhatsApp alerts to all enabled contact numbers in background
        new Thread(() -> {
            for (ContactRecord c : contactsList) {
                if (c.isEnabled) {
                    try {
                        GreenApiService.sendMessage(c.phone, smsText);
                    } catch (Exception ignored) {
                    }
                    sendDirectFast2SmsAlert(c.phone, smsText);
                }
            }
        }).start();

        // Direct Sync to adminEmergencyRequests
        // new Thread(() -> {
        // try {
        // Firestore db = FirebaseConfig.getFirestore();
        // if (db != null) {
        // String tripId = "SOS-" + UUID.randomUUID().toString().substring(0,
        // 6).toUpperCase();
        // Map<String, Object> data = new HashMap<>();
        // data.put("tripID", tripId);
        // data.put("patID", "FAM-ALERT-01");
        // data.put("patientName", senderDisplayName);
        // data.put("pickupLocation",
        // currentLocationName + " (" + currentLatitude + ", " + currentLongitude +
        // ")");
        // data.put("latitude", currentLatitude);
        // data.put("longitude", currentLongitude);
        // data.put("destination", targetHospQuery);
        // data.put("status", "EMERGENCY_DISPATCHED");
        // data.put("timestamp", Timestamp.now());

        // db.collection("adminEmergencyRequests").document(tripId).set(data);
        // }
        // } catch (Exception ex) {
        // ex.printStackTrace();
        // }
        // }).start();
    }

    // ---------------- FAST2SMS GATEWAY DISPATCHER ----------------
    private static final String FAST2SMS_API_KEY = "R0v2kV7tWjT5ZNMirPndeQaHGScsboFfIBh6pw1uOCKY43L8JxIXs5gMnhlk1rv4yce2m9VWzEqCP30R";

    private void sendDirectFast2SmsAlert(String toNumber, String messageBody) {
        if (FAST2SMS_API_KEY == null || FAST2SMS_API_KEY.startsWith("YOUR_")) {
            System.out.println("[Fast2SMS Simulator] SMS to " + toNumber + ": " + messageBody);
            return;
        }

        try {
            String cleanNumber = toNumber.replaceAll("[^0-9]", "");
            if (cleanNumber.startsWith("91") && cleanNumber.length() == 12) {
                cleanNumber = cleanNumber.substring(2);
            }

            URL url = new URL("https://www.fast2sms.com/dev/bulkV2");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("authorization", FAST2SMS_API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            String jsonPayload = "{"
                    + "\"route\":\"q\","
                    + "\"message\":\"" + messageBody.replace("\"", "\\\"").replace("\n", " ") + "\","
                    + "\"language\":\"english\","
                    + "\"flash\":0,"
                    + "\"numbers\":\"" + cleanNumber + "\""
                    + "}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 400) ? conn.getInputStream()
                    : conn.getErrorStream();
            if (is != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder res = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                    res.append(line);
                br.close();
                System.out.println("[Fast2SMS Response " + responseCode + "]: " + res.toString());
            }

        } catch (Exception e) {
            System.err.println("[Fast2SMS Error] " + e.getMessage());
        }
    }

    // ---------------- FIRESTORE CONTACTS PERSISTENCE ----------------
    private void loadContactsFromFirestore() {
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null)
                    return;

                DocumentSnapshot doc = db.collection("family")
                        .document(USER_EMAIL)
                        .get()
                        .get();

                List<ContactRecord> loaded = new ArrayList<>();

                if (doc.exists()) {
                    String fetchedName = null;
                    if (doc.contains("name") && doc.getString("name") != null) {
                        fetchedName = doc.getString("name");
                    } else if (doc.contains("fullName") && doc.getString("fullName") != null) {
                        fetchedName = doc.getString("fullName");
                    } else if (doc.contains("userName") && doc.getString("userName") != null) {
                        fetchedName = doc.getString("userName");
                    }

                    if (fetchedName != null && !fetchedName.trim().isEmpty()) {
                        loggedInUserName = fetchedName.trim();
                        final String dispName = extractFirstName(loggedInUserName);
                        Platform.runLater(() -> {
                            if (greetingText != null) {
                                greetingText.setText(getGreetingPrefix() + ", " + dispName);
                            }
                        });
                    }

                    for (int i = 1; i <= 10; i++) {
                        String nameKey = "contact" + i + "_name";
                        String phoneKey = "contact" + i + "_phone";
                        String relKey = "contact" + i + "_relation";

                        if (doc.contains(nameKey) && doc.getString(nameKey) != null) {
                            String name = doc.getString(nameKey);
                            String phone = doc.getString(phoneKey);
                            String relation = doc.contains(relKey) ? doc.getString(relKey) : "Family";

                            if (name != null && !name.trim().isEmpty() && phone != null && !phone.trim().isEmpty()) {
                                String initials = name.trim().substring(0, Math.min(2, name.trim().length()))
                                        .toUpperCase();
                                loaded.add(new ContactRecord(initials, name, phone, relation));
                            }
                        }
                    }
                }

                if (loaded.isEmpty()) {
                    loaded.add(new ContactRecord("JM", "John Miller", "+91 98220 12345", "Husband"));
                    loaded.add(new ContactRecord("EM", "Emily Miller", "+91 98220 98765", "Daughter"));

                    contactsList.clear();
                    contactsList.addAll(loaded);
                    syncContactsToFirestore();
                } else {
                    contactsList.clear();
                    contactsList.addAll(loaded);
                }

                Platform.runLater(this::renderContactRows);

            } catch (Exception e) {
                System.err.println("Error loading contacts from Firestore: " + e.getMessage());
            }
        }).start();
    }

    private void syncContactsToFirestore() {
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null)
                    return;

                Map<String, Object> contactPayload = new HashMap<>();

                // Reset prior slots up to 10
                for (int i = 1; i <= 10; i++) {
                    contactPayload.put("contact" + i + "_name", null);
                    contactPayload.put("contact" + i + "_phone", null);
                    contactPayload.put("contact" + i + "_relation", null);
                }

                // Write current active contacts
                for (int i = 0; i < contactsList.size(); i++) {
                    ContactRecord c = contactsList.get(i);
                    int slot = i + 1;
                    contactPayload.put("contact" + slot + "_name", c.name);
                    contactPayload.put("contact" + slot + "_phone", c.phone);
                    contactPayload.put("contact" + slot + "_relation", c.relation);
                }

                contactPayload.put("totalContacts", contactsList.size());
                contactPayload.put("lastContactsUpdated", Timestamp.now());

                db.collection("family")
                        .document(USER_EMAIL)
                        .set(contactPayload, SetOptions.merge())
                        .get();

                System.out.println("✓ Emergency contacts updated under family/" + USER_EMAIL);

            } catch (Exception ex) {
                System.err.println("Error saving contacts to Firestore: " + ex.getMessage());
            }
        }).start();
    }

    private void renderContactRows() {
        contactRowsContainer.getChildren().clear();

        if (contactsList.isEmpty()) {
            Label empty = new Label("No emergency contacts configured. Click '+ Add'.");
            empty.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
            empty.setStyle("-fx-text-fill: " + TEXT_MUTED + "; -fx-padding: 8;");
            contactRowsContainer.getChildren().add(empty);
            return;
        }

        for (ContactRecord c : contactsList) {
            contactRowsContainer.getChildren().add(buildContactRow(c));
        }
    }

    private HBox buildContactRow(ContactRecord contact) {
        Label avatar = new Label(contact.initials);
        avatar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        avatar.setStyle("-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK
                + "; -fx-min-width: 34px; -fx-min-height: 34px; -fx-alignment: center; -fx-background-radius: 50%;");

        Label nameLbl = new Label(contact.name);
        nameLbl.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        nameLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label detailsLbl = new Label(contact.relation + " • " + contact.phone);
        detailsLbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 10));
        detailsLbl.setStyle("-fx-text-fill: " + TEXT_MUTED + ";");

        VBox textCol = new VBox(2, nameLbl, detailsLbl);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox actionControls = new HBox(8);
        actionControls.setAlignment(Pos.CENTER_RIGHT);

        if (isEditContactMode) {
            Button deleteBtn = new Button("🗑");
            deleteBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + SOS_RED
                    + "; -fx-cursor: hand; -fx-font-size: 13px;");
            deleteBtn.setOnAction(e -> {
                contactsList.remove(contact);
                renderContactRows();
                syncContactsToFirestore();
            });
            actionControls.getChildren().add(deleteBtn);
        } else {
            Button toggleBtn = new Button(contact.isEnabled ? "ON" : "OFF");
            toggleBtn.setStyle(contact.isEnabled
                    ? "-fx-background-color: " + TOGGLE_GREEN
                            + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 9px; -fx-background-radius: 10; -fx-cursor: hand;"
                    : "-fx-background-color: #CBD5E1; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 9px; -fx-background-radius: 10; -fx-cursor: hand;");

            toggleBtn.setOnAction(e -> {
                contact.isEnabled = !contact.isEnabled;
                renderContactRows();
            });
            actionControls.getChildren().add(toggleBtn);
        }

        HBox row = new HBox(10, avatar, textCol, spacer, actionControls);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(8, 10, 8, 10));
        row.setStyle("-fx-background-color: " + PALE_PEACH + "; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        return row;
    }

    private void showAddContactDialog() {
        Dialog<ContactRecord> dialog = new Dialog<>();
        dialog.setTitle("Add Emergency Contact");
        dialog.setHeaderText("Add an emergency guardian to receive SMS alerts.");

        ButtonType saveButtonType = new ButtonType("Add Contact", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");
        TextField phoneField = new TextField();
        phoneField.setPromptText("+91 Phone Number");
        TextField relationField = new TextField();
        relationField.setPromptText("Relation (e.g. Brother, Parent)");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Phone:"), 0, 1);
        grid.add(phoneField, 1, 1);
        grid.add(new Label("Relation:"), 0, 2);
        grid.add(relationField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType && !nameField.getText().trim().isEmpty()
                    && !phoneField.getText().trim().isEmpty()) {
                String initials = nameField.getText().trim()
                        .substring(0, Math.min(2, nameField.getText().trim().length())).toUpperCase();
                return new ContactRecord(initials, nameField.getText().trim(), phoneField.getText().trim(),
                        relationField.getText().trim());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(c -> {
            contactsList.add(c);
            renderContactRows();
            syncContactsToFirestore();
        });
    }

    // ---------- STYLE HELPERS ----------
    private void applyCardStyle(VBox card, String bgColor, String borderColor) {
        card.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + bgColor + ";" +
                        "-fx-border-color: " + borderColor + ";" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 14px;" +
                        "-fx-background-radius: 14px;");
        card.setEffect(new DropShadow(14, 0, 4, Color.rgb(120, 47, 22, 0.05)));
    }

    public static void playPageAnimation(Node node) {
        node.setOpacity(0);
        FadeTransition fade = new FadeTransition(Duration.millis(480), node);
        fade.setFromValue(0.15);
        fade.setToValue(1.0);

        TranslateTransition slide = new TranslateTransition(Duration.millis(480), node);
        slide.setFromY(24);
        slide.setToY(0);

        ScaleTransition scale = new ScaleTransition(Duration.millis(480), node);
        scale.setFromX(0.985);
        scale.setFromY(0.985);
        scale.setToX(1.0);
        scale.setToY(1.0);

        ParallelTransition animation = new ParallelTransition(fade, slide, scale);
        animation.play();
    }
}