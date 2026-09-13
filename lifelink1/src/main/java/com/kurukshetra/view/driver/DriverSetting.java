package com.kurukshetra.view.driver;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.kurukshetra.config.FirebaseConfig;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class DriverSetting {

    // Cloudinary Setup matching LifeLink standard credentials
    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "w2vrrz2c",
            "api_key", "584434458948198",
            "api_secret", "mOIxVa2JOe33SUA4tC6xut5boPM"
    ));

    // Color Palette
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
    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, sans-serif; ";

    public BorderPane settingsRoot;

    // Form Fields
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;
    private TextField ambulanceIdField;
    private TextField experienceField;
    private TextField baseZoneField;
    private ComboBox<String> bloodGroupCombo;
    private TextField emergencyContactField;

    // Document Fields
    private TextField licenseNumberField;
    private TextField idProofNumberField;
    private TextField medicalCertNumberField;
    private TextField policeVerificationNumberField;

    // Document Cloudinary URLs
    private String profilePicUrl = "";
    private String licenseDocUrl = "";
    private String idProofDocUrl = "";
    private String medicalCertDocUrl = "";
    private String policeVerificationDocUrl = "";

    // UI Document Status Labels
    private Label licenseStatusLabel;
    private Label idProofStatusLabel;
    private Label medicalCertStatusLabel;
    private Label policeVerificationStatusLabel;

    // Avatar preview
    private Circle avatarCircle;
    private Text avatarLetter;
    private Label avatarStatus;

    // Status Banner
    private Label saveStatusBanner;

    private String driverEmail;

    public DriverSetting() {
        this(DriverDashboard.loggedInDriverEmail != null && !DriverDashboard.loggedInDriverEmail.isEmpty()
                ? DriverDashboard.loggedInDriverEmail
                : "driver1@lifelink.com");
    }

    public DriverSetting(String driverEmail) {
        this.driverEmail = (driverEmail != null && !driverEmail.trim().isEmpty())
                ? driverEmail.trim()
                : "driver1@lifelink.com";
    }

    public BorderPane getAppSettingsPage() {
        return getAppSettingsPage(null);
    }

    public BorderPane getAppSettingsPage(Runnable callbackDashboard) {
        settingsRoot = new BorderPane();
        settingsRoot.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        VBox content = new VBox(22);
        content.setPadding(new Insets(24, 35, 35, 35));

        // 1. Top Bar
        HBox topBar = createTopBar(callbackDashboard);

        // 2. Status Banner (Hidden by default)
        saveStatusBanner = new Label();
        saveStatusBanner.setVisible(false);
        saveStatusBanner.setManaged(false);
        saveStatusBanner.setPadding(new Insets(10, 16, 10, 16));
        saveStatusBanner.setMaxWidth(Double.MAX_VALUE);
        saveStatusBanner.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: 600; -fx-background-radius: 8px;");

        // 3. Driver Profile Information Card (with Cloudinary Profile Pic)
        VBox profileCard = createProfileInformationCard();

        // 4. Driver Verification Documents Card (with Cloudinary Document Uploads)
        VBox documentsCard = createDocumentsVerificationCard();

        // 5. Bottom Action Bar
        HBox bottomBar = createBottomBar(callbackDashboard);

        content.getChildren().addAll(topBar, saveStatusBanner, profileCard, documentsCard, bottomBar);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-background: " + PAGE_BG + "; " +
                "-fx-border-color: transparent;"
        );

        settingsRoot.setCenter(scrollPane);

        // Load Driver data from Firebase collection "driver"
        loadDriverDataFromFirebase();

        FadeTransition fade = new FadeTransition(Duration.millis(350), settingsRoot);
        fade.setFromValue(0.3);
        fade.setToValue(1);
        fade.play();

        return settingsRoot;
    }

    // =========================================================
    // 1. TOP BAR
    // =========================================================
    private HBox createTopBar(Runnable callbackDashboard) {
        HBox topBar = new HBox(14);
        topBar.setAlignment(Pos.CENTER_LEFT);

        if (callbackDashboard != null) {
            Button backButton = new Button("‹");
            backButton.setPrefWidth(40);
            backButton.setPrefHeight(40);
            backButton.setStyle(FONT_FAMILY + "-fx-background-color: " + BLUE_LIGHT + "; -fx-text-fill: " + BLUE + "; -fx-font-size: 20px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-cursor: hand;");
            backButton.setOnAction(e -> callbackDashboard.run());
            backButton.setOnMouseEntered(e -> backButton.setStyle(FONT_FAMILY + "-fx-background-color: " + BLUE + "; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-cursor: hand;"));
            backButton.setOnMouseExited(e -> backButton.setStyle(FONT_FAMILY + "-fx-background-color: " + BLUE_LIGHT + "; -fx-text-fill: " + BLUE + "; -fx-font-size: 20px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-cursor: hand;"));
            topBar.getChildren().add(backButton);
        }

        VBox heading = new VBox(3);
        Text pageTitle = new Text("Driver Profile & Document Verification");
        pageTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: " + TEXT + ";");

        Text pageSubtitle = new Text("Official ambulance driver credentials, licensing documents, and cloud synchronization.");
        pageSubtitle.setStyle("-fx-font-size: 12.5px; -fx-fill: " + SECONDARY + ";");
        heading.getChildren().addAll(pageTitle, pageSubtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox livePill = new HBox(6);
        livePill.setAlignment(Pos.CENTER);
        livePill.setPadding(new Insets(6, 14, 6, 14));
        livePill.setStyle("-fx-background-color: " + GREEN_LIGHT + "; -fx-background-radius: 20px; -fx-border-color: " + GREEN + "; -fx-border-radius: 20px; -fx-border-width: 1px;");
        Label dot = new Label("●");
        dot.setStyle("-fx-font-size: 10px; -fx-text-fill: " + GREEN + ";");
        Label liveTxt = new Label("FIRESTORE: collection(\"driver\")");
        liveTxt.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: bold; -fx-text-fill: #15803D;");
        livePill.getChildren().addAll(dot, liveTxt);

        topBar.getChildren().addAll(heading, spacer, livePill);
        return topBar;
    }

    // =========================================================
    // 2. DRIVER PROFILE CARD (WITH CLOUDINARY AVATAR)
    // =========================================================
    private VBox createProfileInformationCard() {
        VBox card = new VBox(18);
        card.setPadding(new Insets(24, 26, 24, 26));
        card.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-background-radius: 14px; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 14px; " +
                "-fx-effect: dropshadow(gaussian, rgba(30,60,90,0.05), 10, 0, 0, 3);"
        );

        Text cardTitle = new Text("Personal & Vehicle Deployment Details");
        cardTitle.setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: " + TEXT + ";");

        HBox profileHeader = new HBox(22);
        profileHeader.setAlignment(Pos.CENTER_LEFT);

        // Avatar with Camera Upload Button
        StackPane avatarStack = new StackPane();
        avatarCircle = new Circle(44);
        avatarCircle.setFill(Color.web(BLUE));
        avatarCircle.setStroke(Color.web(BORDER));
        avatarCircle.setStrokeWidth(2);

        avatarLetter = new Text("D");
        avatarLetter.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: white;");

        avatarStack.getChildren().addAll(avatarCircle, avatarLetter);

        VBox avatarActions = new VBox(6);
        avatarActions.setAlignment(Pos.CENTER_LEFT);

        Button uploadPhotoBtn = new Button("📷 Upload Profile Picture");
        uploadPhotoBtn.setPrefHeight(34);
        uploadPhotoBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + BLUE_LIGHT + "; " +
                "-fx-text-fill: " + BLUE + "; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 14 0 14;"
        );
        uploadPhotoBtn.setOnAction(e -> chooseAndUploadProfilePicture());
        uploadPhotoBtn.setOnMouseEntered(e -> uploadPhotoBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + BLUE + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 14 0 14;"
        ));
        uploadPhotoBtn.setOnMouseExited(e -> uploadPhotoBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + BLUE_LIGHT + "; " +
                "-fx-text-fill: " + BLUE + "; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 14 0 14;"
        ));

        avatarStatus = new Label("Supports JPG, PNG • Automatically stored in Cloudinary");
        avatarStatus.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + MUTED + ";");

        avatarActions.getChildren().addAll(uploadPhotoBtn, avatarStatus);
        profileHeader.getChildren().addAll(avatarStack, avatarActions);

        // Profile Form Grid
        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(16);

        nameField = createStyledTextField("e.g. Ramesh Patil");
        emailField = createStyledTextField("driver1@lifelink.com");
        emailField.setEditable(false);
        emailField.setStyle(emailField.getStyle() + "-fx-background-color: #F8FAFC; -fx-opacity: 0.85;");

        phoneField = createStyledTextField("+91 98765 43210");
        ambulanceIdField = createStyledTextField("AMB-101");
        experienceField = createStyledTextField("5 Years");
        baseZoneField = createStyledTextField("Swargate / Pune Central");

        bloodGroupCombo = new ComboBox<>();
        bloodGroupCombo.getItems().addAll("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-");
        bloodGroupCombo.setValue("O+");
        bloodGroupCombo.setPrefHeight(40);
        bloodGroupCombo.setMaxWidth(Double.MAX_VALUE);
        bloodGroupCombo.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-width: 1.5px; " +
                "-fx-border-radius: 8px; " +
                "-fx-background-radius: 8px; " +
                "-fx-font-size: 13px;"
        );

        emergencyContactField = createStyledTextField("+91 91234 56789 (Kin)");

        // Row 0
        formGrid.add(createFieldBox("Full Driver Name", nameField), 0, 0);
        formGrid.add(createFieldBox("Driver Email Address (ID)", emailField), 1, 0);

        // Row 1
        formGrid.add(createFieldBox("Contact Phone Number", phoneField), 0, 1);
        formGrid.add(createFieldBox("Assigned Ambulance Unit", ambulanceIdField), 1, 1);

        // Row 2
        formGrid.add(createFieldBox("Blood Group", bloodGroupCombo), 0, 2);
        formGrid.add(createFieldBox("Driving Experience", experienceField), 1, 2);

        // Row 3
        formGrid.add(createFieldBox("Base Operational Zone", baseZoneField), 0, 3);
        formGrid.add(createFieldBox("Emergency Contact Number", emergencyContactField), 1, 3);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        formGrid.getColumnConstraints().addAll(col1, col2);

        card.getChildren().addAll(cardTitle, profileHeader, formGrid);
        return card;
    }

    // =========================================================
    // 3. DRIVER DOCUMENTS CARD (WITH CLOUDINARY UPLOAD SLOTS)
    // =========================================================
    private VBox createDocumentsVerificationCard() {
        VBox card = new VBox(18);
        card.setPadding(new Insets(24, 26, 24, 26));
        card.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-background-radius: 14px; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 14px; " +
                "-fx-effect: dropshadow(gaussian, rgba(30,60,90,0.05), 10, 0, 0, 3);"
        );

        Text cardTitle = new Text("Driver Verification & Licensing Documents");
        cardTitle.setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: " + TEXT + ";");

        Text cardSubtitle = new Text("Upload official identification, commercial driving license, and fitness certifications. All files are securely uploaded to Cloudinary.");
        cardSubtitle.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-fill: " + SECONDARY + ";");

        VBox docSlots = new VBox(14);

        // Slot 1: Commercial Driving Licence
        licenseNumberField = createStyledTextField("DL-1420110012345");
        licenseStatusLabel = createStatusBadge(false, "No Licence File Uploaded");
        HBox licenseSlot = createDocumentSlot(
                "🪪 Commercial Driving Licence (Ambulance Endorsed)",
                "Mandatory HMV / Commercial driving license valid for emergency service transport.",
                licenseNumberField,
                licenseStatusLabel,
                () -> chooseAndUploadDocument("licence", licenseStatusLabel, url -> licenseDocUrl = url),
                () -> openDocumentInBrowser(licenseDocUrl)
        );

        // Slot 2: Government ID Proof (Aadhaar / National ID)
        idProofNumberField = createStyledTextField("1234-5678-9012");
        idProofStatusLabel = createStatusBadge(false, "No ID Proof Uploaded");
        HBox idProofSlot = createDocumentSlot(
                "📄 Government ID Proof (Aadhaar / Passport / Voter ID)",
                "Primary government identification for driver verification and background audit.",
                idProofNumberField,
                idProofStatusLabel,
                () -> chooseAndUploadDocument("id_proof", idProofStatusLabel, url -> idProofDocUrl = url),
                () -> openDocumentInBrowser(idProofDocUrl)
        );

        // Slot 3: Medical Fitness & First-Aid Certificate
        medicalCertNumberField = createStyledTextField("MED-FIT-9920");
        medicalCertStatusLabel = createStatusBadge(false, "No Medical Certificate Uploaded");
        HBox medicalCertSlot = createDocumentSlot(
                "🩺 Medical Fitness & First-Aid Certification",
                "Certified annual visual, cardiovascular & emergency responder fitness clearance.",
                medicalCertNumberField,
                medicalCertStatusLabel,
                () -> chooseAndUploadDocument("medical_cert", medicalCertStatusLabel, url -> medicalCertDocUrl = url),
                () -> openDocumentInBrowser(medicalCertDocUrl)
        );

        // Slot 4: Police Verification Clearance
        policeVerificationNumberField = createStyledTextField("POL-VER-44019");
        policeVerificationStatusLabel = createStatusBadge(false, "No Police Verification Uploaded");
        HBox policeSlot = createDocumentSlot(
                "🛡 Police Background Verification Certificate",
                "Official law enforcement character and criminal record clearance certificate.",
                policeVerificationNumberField,
                policeVerificationStatusLabel,
                () -> chooseAndUploadDocument("police_verification", policeVerificationStatusLabel, url -> policeVerificationDocUrl = url),
                () -> openDocumentInBrowser(policeVerificationDocUrl)
        );

        docSlots.getChildren().addAll(licenseSlot, idProofSlot, medicalCertSlot, policeSlot);
        card.getChildren().addAll(cardTitle, cardSubtitle, docSlots);
        return card;
    }

    private HBox createDocumentSlot(String title, String desc, TextField numberField, Label statusLabel, Runnable onUpload, Runnable onView) {
        HBox slot = new HBox(16);
        slot.setAlignment(Pos.CENTER_LEFT);
        slot.setPadding(new Insets(14, 16, 14, 16));
        slot.setStyle(
                "-fx-background-color: #F8FAFC; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-width: 1px; " +
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px;"
        );

        VBox infoBox = new VBox(4);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        Label titleLbl = new Label(title);
        titleLbl.setStyle(FONT_FAMILY + "-fx-font-size: 13.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT + ";");

        Label descLbl = new Label(desc);
        descLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " + SECONDARY + ";");

        HBox inputAndStatusRow = new HBox(10);
        inputAndStatusRow.setAlignment(Pos.CENTER_LEFT);
        numberField.setPrefWidth(220);
        numberField.setPrefHeight(34);

        inputAndStatusRow.getChildren().addAll(new Label("Doc Ref #:"), numberField, statusLabel);

        infoBox.getChildren().addAll(titleLbl, descLbl, inputAndStatusRow);

        VBox actionBox = new VBox(6);
        actionBox.setAlignment(Pos.CENTER_RIGHT);

        Button uploadBtn = new Button("📁 Choose & Upload File");
        uploadBtn.setPrefHeight(32);
        uploadBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + BLUE_LIGHT + "; " +
                "-fx-text-fill: " + BLUE + "; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 6px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 12 0 12;"
        );
        uploadBtn.setOnAction(e -> onUpload.run());

        Button viewBtn = new Button("🔗 View Document");
        viewBtn.setPrefHeight(28);
        viewBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: transparent; " +
                "-fx-text-fill: " + BLUE_DARK + "; " +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: 600; " +
                "-fx-cursor: hand;"
        );
        viewBtn.setOnAction(e -> onView.run());

        actionBox.getChildren().addAll(uploadBtn, viewBtn);

        slot.getChildren().addAll(infoBox, actionBox);
        return slot;
    }

    private Label createStatusBadge(boolean uploaded, String text) {
        Label badge = new Label(text);
        updateBadgeStyle(badge, uploaded);
        return badge;
    }

    private void updateBadgeStyle(Label badge, boolean uploaded) {
        if (uploaded) {
            badge.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + GREEN_LIGHT + "; " +
                    "-fx-text-fill: #15803D; " +
                    "-fx-font-size: 10.5px; " +
                    "-fx-font-weight: 700; " +
                    "-fx-padding: 3 8; " +
                    "-fx-background-radius: 4px;"
            );
        } else {
            badge.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: #FEF3C7; " +
                    "-fx-text-fill: #B45309; " +
                    "-fx-font-size: 10.5px; " +
                    "-fx-font-weight: 600; " +
                    "-fx-padding: 3 8; " +
                    "-fx-background-radius: 4px;"
            );
        }
    }

    // =========================================================
    // 4. BOTTOM ACTION BAR
    // =========================================================
    private HBox createBottomBar(Runnable callbackDashboard) {
        HBox bar = new HBox(14);
        bar.setAlignment(Pos.CENTER_RIGHT);
        bar.setPadding(new Insets(10, 0, 10, 0));

        Button discardBtn = new Button("Discard Changes");
        discardBtn.setPrefHeight(42);
        discardBtn.setPrefWidth(140);
        discardBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #F1F5F9; " +
                "-fx-text-fill: " + SECONDARY + "; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
        );
        discardBtn.setOnAction(e -> loadDriverDataFromFirebase());

        Button saveBtn = new Button("💾 Save Driver Profile & Documents");
        saveBtn.setPrefHeight(42);
        saveBtn.setPrefWidth(260);
        saveBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + BLUE + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
        );
        saveBtn.setOnAction(e -> saveDriverDataToFirebase());
        saveBtn.setOnMouseEntered(e -> saveBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + BLUE_DARK + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
        ));
        saveBtn.setOnMouseExited(e -> saveBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + BLUE + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
        ));

        bar.getChildren().addAll(discardBtn, saveBtn);
        return bar;
    }

    // =========================================================
    // CLOUDINARY UPLOAD LOGIC
    // =========================================================
    private void chooseAndUploadProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Driver Profile Picture");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.webp")
        );

        File file = fileChooser.showOpenDialog(settingsRoot != null && settingsRoot.getScene() != null ? settingsRoot.getScene().getWindow() : null);
        if (file == null) return;

        avatarStatus.setText("Uploading to Cloudinary...");
        avatarStatus.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + BLUE + "; -fx-font-weight: bold;");

        // Set local preview immediately
        try {
            Image localImg = new Image(file.toURI().toString(), true);
            localImg.progressProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal.doubleValue() >= 1.0) {
                    avatarCircle.setFill(new ImagePattern(localImg));
                    avatarLetter.setVisible(false);
                }
            });
        } catch (Exception ignored) {}

        new Thread(() -> {
            try {
                Map res = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                String url = (String) res.get("secure_url");
                if (url != null && !url.isEmpty()) {
                    profilePicUrl = url;
                    Platform.runLater(() -> {
                        avatarStatus.setText("✓ Profile Picture Uploaded to Cloudinary");
                        avatarStatus.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + GREEN + "; -fx-font-weight: bold;");
                        showBanner("Profile picture uploaded to Cloudinary successfully! Remember to click Save.", true);
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    avatarStatus.setText("Upload Failed: " + ex.getMessage());
                    avatarStatus.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + RED + ";");
                    showBanner("Cloudinary upload failed: " + ex.getMessage(), false);
                });
            }
        }).start();
    }

    private interface UrlConsumer {
        void accept(String url);
    }

    private void chooseAndUploadDocument(String docType, Label statusLabel, UrlConsumer onUrlReady) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Driver Document (" + docType + ")");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Document / Image Files", "*.pdf", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(settingsRoot != null && settingsRoot.getScene() != null ? settingsRoot.getScene().getWindow() : null);
        if (file == null) return;

        statusLabel.setText("Uploading to Cloudinary...");
        updateBadgeStyle(statusLabel, false);

        new Thread(() -> {
            try {
                Map res = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                String url = (String) res.get("secure_url");
                if (url != null && !url.isEmpty()) {
                    onUrlReady.accept(url);
                    Platform.runLater(() -> {
                        statusLabel.setText("✓ Uploaded: " + file.getName());
                        updateBadgeStyle(statusLabel, true);
                        showBanner("Document (" + file.getName() + ") uploaded to Cloudinary. Click Save to persist.", true);
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    statusLabel.setText("Upload Failed");
                    updateBadgeStyle(statusLabel, false);
                    showBanner("Document upload failed: " + ex.getMessage(), false);
                });
            }
        }).start();
    }

    private void openDocumentInBrowser(String url) {
        if (url == null || url.trim().isEmpty()) {
            showBanner("No document file has been uploaded for this slot yet.", false);
            return;
        }

        new Thread(() -> {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(url));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    // =========================================================
    // FIREBASE FIRESTORE DATA SYNC
    // =========================================================
    private void loadDriverDataFromFirebase() {
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return;

                DocumentSnapshot doc = db.collection("driver").document(driverEmail).get().get();

                Platform.runLater(() -> {
                    if (doc != null && doc.exists()) {
                        String name = doc.getString("name");
                        String email = doc.getString("email");
                        String phone = doc.getString("phone");
                        String ambId = doc.getString("ambulanceId");
                        String exp = doc.getString("experience");
                        String zone = doc.getString("baseZone");
                        String blood = doc.getString("bloodGroup");
                        String emgContact = doc.getString("emergencyContact");

                        String licNum = doc.getString("licenseNumber");
                        String idNum = doc.getString("idProofNumber");
                        String medNum = doc.getString("medicalCertNumber");
                        String polNum = doc.getString("policeVerificationNumber");

                        profilePicUrl = doc.getString("profilePicUrl") != null ? doc.getString("profilePicUrl") : "";
                        licenseDocUrl = doc.getString("licenseDocUrl") != null ? doc.getString("licenseDocUrl") : "";
                        idProofDocUrl = doc.getString("idProofDocUrl") != null ? doc.getString("idProofDocUrl") : "";
                        medicalCertDocUrl = doc.getString("medicalCertDocUrl") != null ? doc.getString("medicalCertDocUrl") : "";
                        policeVerificationDocUrl = doc.getString("policeVerificationDocUrl") != null ? doc.getString("policeVerificationDocUrl") : "";

                        if (name != null) nameField.setText(name);
                        if (email != null) emailField.setText(email);
                        if (phone != null) phoneField.setText(phone);
                        if (ambId != null) ambulanceIdField.setText(ambId);
                        if (exp != null) experienceField.setText(exp);
                        if (zone != null) baseZoneField.setText(zone);
                        if (blood != null && bloodGroupCombo.getItems().contains(blood)) bloodGroupCombo.setValue(blood);
                        if (emgContact != null) emergencyContactField.setText(emgContact);

                        if (licNum != null) licenseNumberField.setText(licNum);
                        if (idNum != null) idProofNumberField.setText(idNum);
                        if (medNum != null) medicalCertNumberField.setText(medNum);
                        if (polNum != null) policeVerificationNumberField.setText(polNum);

                        // Update Document Status Badges
                        if (!licenseDocUrl.isEmpty()) {
                            licenseStatusLabel.setText("✓ Verified on Cloudinary");
                            updateBadgeStyle(licenseStatusLabel, true);
                        }
                        if (!idProofDocUrl.isEmpty()) {
                            idProofStatusLabel.setText("✓ Verified on Cloudinary");
                            updateBadgeStyle(idProofStatusLabel, true);
                        }
                        if (!medicalCertDocUrl.isEmpty()) {
                            medicalCertStatusLabel.setText("✓ Verified on Cloudinary");
                            updateBadgeStyle(medicalCertStatusLabel, true);
                        }
                        if (!policeVerificationDocUrl.isEmpty()) {
                            policeVerificationStatusLabel.setText("✓ Verified on Cloudinary");
                            updateBadgeStyle(policeVerificationStatusLabel, true);
                        }

                        // Update Avatar
                        if (!profilePicUrl.isEmpty()) {
                            try {
                                Image avatarImg = new Image(profilePicUrl, true);
                                avatarImg.progressProperty().addListener((obs, oldVal, newVal) -> {
                                    if (newVal.doubleValue() >= 1.0) {
                                        avatarCircle.setFill(new ImagePattern(avatarImg));
                                        avatarLetter.setVisible(false);
                                    }
                                });
                            } catch (Exception ignored) {}
                        } else if (name != null && !name.isEmpty()) {
                            avatarLetter.setText(name.substring(0, 1).toUpperCase());
                        }

                        showBanner("Driver profile data synchronized from Firebase collection(\"driver\").", true);
                    } else {
                        // Populate default fallback values
                        emailField.setText(driverEmail);
                        if (nameField.getText().isEmpty()) nameField.setText("Ambulance Pilot");
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> showBanner("Error loading from Firestore: " + ex.getMessage(), false));
            }
        }).start();
    }

    private void saveDriverDataToFirebase() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", nameField.getText().trim());
        data.put("email", driverEmail);
        data.put("phone", phoneField.getText().trim());
        data.put("ambulanceId", ambulanceIdField.getText().trim());
        data.put("experience", experienceField.getText().trim());
        data.put("baseZone", baseZoneField.getText().trim());
        data.put("bloodGroup", bloodGroupCombo.getValue());
        data.put("emergencyContact", emergencyContactField.getText().trim());

        data.put("licenseNumber", licenseNumberField.getText().trim());
        data.put("idProofNumber", idProofNumberField.getText().trim());
        data.put("medicalCertNumber", medicalCertNumberField.getText().trim());
        data.put("policeVerificationNumber", policeVerificationNumberField.getText().trim());

        data.put("profilePicUrl", profilePicUrl);
        data.put("licenseDocUrl", licenseDocUrl);
        data.put("idProofDocUrl", idProofDocUrl);
        data.put("medicalCertDocUrl", medicalCertDocUrl);
        data.put("policeVerificationDocUrl", policeVerificationDocUrl);
        data.put("updatedAt", com.google.cloud.Timestamp.now());

        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db != null) {
                    db.collection("driver").document(driverEmail).set(data, SetOptions.merge()).get();
                    Platform.runLater(() -> {
                        showBanner("✓ Driver profile and documents successfully saved to Firebase collection(\"driver\")!", true);
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    showBanner("Failed to save to Firebase: " + ex.getMessage(), false);
                });
            }
        }).start();
    }

    private void showBanner(String message, boolean isSuccess) {
        if (saveStatusBanner == null) return;
        saveStatusBanner.setText(message);
        if (isSuccess) {
            saveStatusBanner.setStyle(FONT_FAMILY + "-fx-background-color: " + GREEN_LIGHT + "; -fx-text-fill: #15803D; -fx-font-size: 13px; -fx-font-weight: 600; -fx-background-radius: 8px; -fx-border-color: " + GREEN + "; -fx-border-width: 1px; -fx-border-radius: 8px; -fx-padding: 10 16;");
        } else {
            saveStatusBanner.setStyle(FONT_FAMILY + "-fx-background-color: " + RED_LIGHT + "; -fx-text-fill: " + RED + "; -fx-font-size: 13px; -fx-font-weight: 600; -fx-background-radius: 8px; -fx-border-color: " + RED + "; -fx-border-width: 1px; -fx-border-radius: 8px; -fx-padding: 10 16;");
        }
        saveStatusBanner.setVisible(true);
        saveStatusBanner.setManaged(true);
    }

    // =========================================================
    // UI HELPERS
    // =========================================================
    private VBox createFieldBox(String labelText, javafx.scene.Node field) {
        VBox box = new VBox(6);
        Label label = new Label(labelText);
        label.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-font-weight: 600; -fx-text-fill: " + SECONDARY + ";");
        box.getChildren().addAll(label, field);
        return box;
    }

    private TextField createStyledTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefHeight(40);
        tf.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-width: 1.5px; " +
                "-fx-border-radius: 8px; " +
                "-fx-background-radius: 8px; " +
                "-fx-font-size: 13px; " +
                "-fx-text-fill: " + TEXT + "; " +
                "-fx-padding: 0 12 0 12;"
        );
        tf.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                tf.setStyle(
                        FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + "; " +
                        "-fx-border-color: " + BLUE + "; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 13px; " +
                        "-fx-text-fill: " + TEXT + "; " +
                        "-fx-padding: 0 12 0 12;"
                );
            } else {
                tf.setStyle(
                        FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + "; " +
                        "-fx-border-color: " + BORDER + "; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 13px; " +
                        "-fx-text-fill: " + TEXT + "; " +
                        "-fx-padding: 0 12 0 12;"
                );
            }
        });
        return tf;
    }
}