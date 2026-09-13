package com.kurukshetra.view.nurse;

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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class NurseProfilePage {

    // Cloudinary Setup matching LifeLink standard credentials
    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "w2vrrz2c",
            "api_key", "584434458948198",
            "api_secret", "mOIxVa2JOe33SUA4tC6xut5boPM"
    ));

    // ================= EXACT NURSE DASHBOARD COLOR PALETTE =================
    private static final String PRIMARY_PINK = "#E67593";
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
    private static final String EMERALD_GREEN = "#16A34A";
    private static final String EMERALD_BG = "#EAF7EE";
    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, sans-serif; ";

    public BorderPane profileRoot;

    // Form Fields
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;
    private TextField assignedUnitField;
    private TextField specializationField;
    private TextField experienceField;
    private ComboBox<String> shiftScheduleCombo;
    private ComboBox<String> bloodGroupCombo;
    private TextField emergencyContactField;

    // Document Fields
    private TextField licenseNumberField;
    private TextField idProofNumberField;
    private TextField certificationNumberField;
    private TextField medicalFitnessNumberField;

    // Document Cloudinary URLs
    private String profilePicUrl = "";
    private String licenseDocUrl = "";
    private String idProofDocUrl = "";
    private String certificationDocUrl = "";
    private String medicalFitnessDocUrl = "";

    // UI Document Status Labels
    private Label licenseStatusLabel;
    private Label idProofStatusLabel;
    private Label certificationStatusLabel;
    private Label medicalFitnessStatusLabel;

    // Avatar preview
    private Circle avatarCircle;
    private Text avatarLetter;
    private Label avatarStatus;

    // Status Banner
    private Label saveStatusBanner;

    private String nurseEmail;

    public NurseProfilePage() {
        this(NurseDashboardPage.loginemail != null && !NurseDashboardPage.loginemail.isEmpty()
                ? NurseDashboardPage.loginemail
                : "nurse1@lifelink.com");
    }

    public NurseProfilePage(String nurseEmail) {
        this.nurseEmail = (nurseEmail != null && !nurseEmail.trim().isEmpty())
                ? nurseEmail.trim()
                : "nurse1@lifelink.com";
    }

    public BorderPane getProfilePage() {
        return getProfilePage(null);
    }

    public BorderPane getNurseProfilePage(Runnable callbackDashboard) {
        return getProfilePage(callbackDashboard);
    }

    public BorderPane getProfilePage(Runnable callbackDashboard) {
        profileRoot = new BorderPane();
        profileRoot.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);
        profileRoot.setPadding(new Insets(10, 0, 10, 0));

        VBox content = new VBox(22);
        content.setPadding(new Insets(20, 35, 30, 35));

        // 1. Top Bar
        HBox topBar = createTopBar(callbackDashboard);

        // 2. Status Banner (Hidden by default)
        saveStatusBanner = new Label();
        saveStatusBanner.setVisible(false);
        saveStatusBanner.setManaged(false);
        saveStatusBanner.setPadding(new Insets(10, 16, 10, 16));
        saveStatusBanner.setMaxWidth(Double.MAX_VALUE);
        saveStatusBanner.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: 600; -fx-background-radius: 8px;");

        // 3. Nurse Profile Information Card (with Cloudinary Profile Pic)
        VBox profileCard = createProfileInformationCard();

        // 4. Nurse Verification Documents Card (with Cloudinary Document Uploads)
        VBox documentsCard = createDocumentsVerificationCard();

        // 5. Bottom Action Bar
        HBox bottomBar = createBottomBar(callbackDashboard);

        content.getChildren().addAll(topBar, saveStatusBanner, profileCard, documentsCard, bottomBar);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-background: " + PAGE_BG + "; " +
                "-fx-border-color: transparent;"
        );
        applyHiddenScrollbars(scrollPane);

        profileRoot.setCenter(scrollPane);

        // Load Nurse data from Firebase collection "nurse"
        loadNurseDataFromFirebase();

        FadeTransition fade = new FadeTransition(Duration.millis(350), profileRoot);
        fade.setFromValue(0.3);
        fade.setToValue(1);
        fade.play();

        return profileRoot;
    }

    // =========================================================
    // 1. TOP BAR
    // =========================================================
    private HBox createTopBar(Runnable callbackDashboard) {
        HBox topBar = new HBox(14);
        topBar.setAlignment(Pos.CENTER_LEFT);

        VBox heading = new VBox(3);
        Text pageTitle = new Text("Nurse Profile & Clinical Verification");
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        heading.getChildren().addAll(pageTitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        
        topBar.getChildren().addAll(heading, spacer);
        return topBar;
    }

    // =========================================================
    // 2. NURSE PROFILE CARD (WITH CLOUDINARY AVATAR)
    // =========================================================
    private VBox createProfileInformationCard() {
        VBox card = new VBox(18);
        card.setPadding(new Insets(24, 26, 24, 26));
        card.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-background-radius: 14px; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 14px; " +
                "-fx-effect: dropshadow(gaussian, rgba(230,117,147,0.08), 12, 0, 0, 4);"
        );

        Text cardTitle = new Text("Clinical Staff & Deployment Details");
        cardTitle.setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        HBox profileHeader = new HBox(22);
        profileHeader.setAlignment(Pos.CENTER_LEFT);

        // Avatar with Camera Upload Button
        StackPane avatarStack = new StackPane();
        avatarCircle = new Circle(44);
        avatarCircle.setFill(Color.web(PRIMARY_PINK));
        avatarCircle.setStroke(Color.web(SOFT_PINK));
        avatarCircle.setStrokeWidth(3);

        avatarLetter = new Text("N");
        avatarLetter.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-fill: white;");

        avatarStack.getChildren().addAll(avatarCircle, avatarLetter);

        VBox avatarActions = new VBox(6);
        avatarActions.setAlignment(Pos.CENTER_LEFT);

        Button uploadPhotoBtn = new Button("📷 Upload Profile Picture");
        uploadPhotoBtn.setPrefHeight(34);
        uploadPhotoBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_PINK + "; " +
                "-fx-text-fill: " + PRIMARY_PINK + "; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 14 0 14;"
        );
        uploadPhotoBtn.setOnAction(e -> chooseAndUploadProfilePicture());
        uploadPhotoBtn.setOnMouseEntered(e -> uploadPhotoBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_PINK + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 14 0 14;"
        ));
        uploadPhotoBtn.setOnMouseExited(e -> uploadPhotoBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_PINK + "; " +
                "-fx-text-fill: " + PRIMARY_PINK + "; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 14 0 14;"
        ));

        avatarStatus = new Label("Supports JPG, PNG");
        avatarStatus.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + MUTED_TEXT + ";");

        avatarActions.getChildren().addAll(uploadPhotoBtn, avatarStatus);
        profileHeader.getChildren().addAll(avatarStack, avatarActions);

        // Profile Form Grid
        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(16);

        nameField = createStyledTextField("e.g. Sister Snehal Kulkarni");
        emailField = createStyledTextField("nurse1@lifelink.com");
        emailField.setEditable(false);
        emailField.setStyle(emailField.getStyle() + "-fx-background-color: " + VERY_PALE_PINK + "; -fx-opacity: 0.90;");

        phoneField = createStyledTextField("+91 98765 43210");
        assignedUnitField = createStyledTextField("AMB-101 / Emergency Ward 3");
        specializationField = createStyledTextField("Emergency Triage / Senior Trauma Nurse");
        experienceField = createStyledTextField("5 Years");

        shiftScheduleCombo = new ComboBox<>();
        shiftScheduleCombo.getItems().addAll("Morning (08:00 - 16:00)", "Evening (16:00 - 00:00)", "Night (00:00 - 08:00)", "Rotating / On-Call");
        shiftScheduleCombo.setValue("Morning (08:00 - 16:00)");
        shiftScheduleCombo.setPrefHeight(40);
        shiftScheduleCombo.setMaxWidth(Double.MAX_VALUE);
        shiftScheduleCombo.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1.5px; " +
                "-fx-border-radius: 8px; " +
                "-fx-background-radius: 8px; " +
                "-fx-font-size: 13px;"
        );

        bloodGroupCombo = new ComboBox<>();
        bloodGroupCombo.getItems().addAll("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-");
        bloodGroupCombo.setValue("B+");
        bloodGroupCombo.setPrefHeight(40);
        bloodGroupCombo.setMaxWidth(Double.MAX_VALUE);
        bloodGroupCombo.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1.5px; " +
                "-fx-border-radius: 8px; " +
                "-fx-background-radius: 8px; " +
                "-fx-font-size: 13px;"
        );

        emergencyContactField = createStyledTextField("+91 91234 56789 (Kin)");

        // Row 0
        formGrid.add(createFieldBox("Full Nurse Name", nameField), 0, 0);
        formGrid.add(createFieldBox("Nurse Email Address (ID)", emailField), 1, 0);

        // Row 1
        formGrid.add(createFieldBox("Contact Phone Number", phoneField), 0, 1);
        formGrid.add(createFieldBox("Assigned Clinical / Ambulance Unit", assignedUnitField), 1, 1);

        // Row 2
        formGrid.add(createFieldBox("Clinical Specialization / Role", specializationField), 0, 2);
        formGrid.add(createFieldBox("Clinical Experience", experienceField), 1, 2);

        // Row 3
        formGrid.add(createFieldBox("Duty Shift Schedule", shiftScheduleCombo), 0, 3);
        formGrid.add(createFieldBox("Blood Group", bloodGroupCombo), 1, 3);

        // Row 4
        formGrid.add(createFieldBox("Emergency Contact Number", emergencyContactField), 0, 4);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        formGrid.getColumnConstraints().addAll(col1, col2);

        card.getChildren().addAll(cardTitle, profileHeader, formGrid);
        return card;
    }

    // =========================================================
    // 3. NURSE DOCUMENTS CARD (WITH CLOUDINARY UPLOAD SLOTS)
    // =========================================================
    private VBox createDocumentsVerificationCard() {
        VBox card = new VBox(18);
        card.setPadding(new Insets(24, 26, 24, 26));
        card.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-background-radius: 14px; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 14px; " +
                "-fx-effect: dropshadow(gaussian, rgba(230,117,147,0.08), 12, 0, 0, 4);"
        );

        Text cardTitle = new Text("Nurse Verification & Licensing Documents");
        cardTitle.setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");


        VBox docSlots = new VBox(14);

        // Slot 1: Nursing Council Registration / Licence (RNRM)
        licenseNumberField = createStyledTextField("MNC-RNRM-88392");
        licenseStatusLabel = createStatusBadge(false, "No Licence File Uploaded");
        HBox licenseSlot = createDocumentSlot(
                "🪪 Nursing Council Registration (State / National RNRM)",
                "",
                licenseNumberField,
                licenseStatusLabel,
                () -> chooseAndUploadDocument("nursing_licence", licenseStatusLabel, url -> licenseDocUrl = url),
                () -> openDocumentInBrowser(licenseDocUrl)
        );

        // Slot 2: Government ID Proof (Aadhaar / Passport / Voter ID)
        idProofNumberField = createStyledTextField("1234-5678-9012");
        idProofStatusLabel = createStatusBadge(false, "No ID Proof Uploaded");
        HBox idProofSlot = createDocumentSlot(
                "📄 Government ID Proof (Aadhaar / Passport / Voter ID)",
                "",
                idProofNumberField,
                idProofStatusLabel,
                () -> chooseAndUploadDocument("id_proof", idProofStatusLabel, url -> idProofDocUrl = url),
                () -> openDocumentInBrowser(idProofDocUrl)
        );

        // Slot 3: Advanced Life Support Certifications (BLS / ACLS / PALS)
        certificationNumberField = createStyledTextField("AHA-ACLS-2024-9182");
        certificationStatusLabel = createStatusBadge(false, "No Certificate Uploaded");
        HBox certSlot = createDocumentSlot(
                "🩺 Advanced Life Support Certification (BLS / ACLS / PALS)",
                "",
                certificationNumberField,
                certificationStatusLabel,
                () -> chooseAndUploadDocument("life_support_cert", certificationStatusLabel, url -> certificationDocUrl = url),
                () -> openDocumentInBrowser(certificationDocUrl)
        );

        // Slot 4: Hospital Medical Fitness & Immunization Clearance
        medicalFitnessNumberField = createStyledTextField("MED-FIT-CL-5501");
        medicalFitnessStatusLabel = createStatusBadge(false, "No Clearance File Uploaded");
        HBox medicalSlot = createDocumentSlot(
                "🛡 Hospital Medical Fitness & Immunization Clearance",
                "",
                medicalFitnessNumberField,
                medicalFitnessStatusLabel,
                () -> chooseAndUploadDocument("medical_fitness", medicalFitnessStatusLabel, url -> medicalFitnessDocUrl = url),
                () -> openDocumentInBrowser(medicalFitnessDocUrl)
        );

        docSlots.getChildren().addAll(licenseSlot, idProofSlot, certSlot, medicalSlot);
        card.getChildren().addAll(cardTitle, docSlots);
        return card;
    }

    private HBox createDocumentSlot(String title, String desc, TextField numberField, Label statusLabel, Runnable onUpload, Runnable onView) {
        HBox slot = new HBox(16);
        slot.setAlignment(Pos.CENTER_LEFT);
        slot.setPadding(new Insets(14, 16, 14, 16));
        slot.setStyle(
                "-fx-background-color: " + VERY_PALE_PINK + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1px; " +
                "-fx-background-radius: 10px; " +
                "-fx-border-radius: 10px;"
        );

        VBox infoBox = new VBox(4);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        Label titleLbl = new Label(title);
        titleLbl.setStyle(FONT_FAMILY + "-fx-font-size: 13.5px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_TEXT + ";");

        Label descLbl = new Label(desc);
        descLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " + SECONDARY_TEXT + ";");

        HBox inputAndStatusRow = new HBox(10);
        inputAndStatusRow.setAlignment(Pos.CENTER_LEFT);
        numberField.setPrefWidth(220);
        numberField.setPrefHeight(34);

        Label refLabel = new Label("Doc Ref #:");
        refLabel.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + SECONDARY_TEXT + ";");

        inputAndStatusRow.getChildren().addAll(refLabel, numberField, statusLabel);

        infoBox.getChildren().addAll(titleLbl, descLbl, inputAndStatusRow);

        VBox actionBox = new VBox(6);
        actionBox.setAlignment(Pos.CENTER_RIGHT);

        Button uploadBtn = new Button("📁 Upload File");
        uploadBtn.setPrefHeight(32);
        uploadBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_PINK + "; " +
                "-fx-text-fill: " + PRIMARY_PINK + "; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 6px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 12 0 12;"
        );
        uploadBtn.setOnAction(e -> onUpload.run());
        uploadBtn.setOnMouseEntered(e -> uploadBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_PINK + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 6px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 12 0 12;"
        ));
        uploadBtn.setOnMouseExited(e -> uploadBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_PINK + "; " +
                "-fx-text-fill: " + PRIMARY_PINK + "; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 6px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 0 12 0 12;"
        ));

        Button viewBtn = new Button("🔗 View Document");
        viewBtn.setPrefHeight(28);
        viewBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: transparent; " +
                "-fx-text-fill: " + PINK_DARK + "; " +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: 600; " +
                "-fx-cursor: hand;"
        );
        viewBtn.setOnAction(e -> onView.run());
        viewBtn.setOnMouseEntered(e -> viewBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: transparent; " +
                "-fx-text-fill: " + PRIMARY_PINK + "; " +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: 600; " +
                "-fx-underline: true; " +
                "-fx-cursor: hand;"
        ));
        viewBtn.setOnMouseExited(e -> viewBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: transparent; " +
                "-fx-text-fill: " + PINK_DARK + "; " +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: 600; " +
                "-fx-underline: false; " +
                "-fx-cursor: hand;"
        ));

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
                    "-fx-background-color: " + EMERALD_BG + "; " +
                    "-fx-text-fill: " + EMERALD_GREEN + "; " +
                    "-fx-font-size: 10.5px; " +
                    "-fx-font-weight: 700; " +
                    "-fx-padding: 3 8; " +
                    "-fx-background-radius: 4px;"
            );
        } else {
            badge.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + VERY_LIGHT_PINK + "; " +
                    "-fx-text-fill: " + PINK_DARK + "; " +
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
                "-fx-background-color: " + VERY_LIGHT_PINK + "; " +
                "-fx-text-fill: " + SECONDARY_TEXT + "; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
        );
        discardBtn.setOnAction(e -> loadNurseDataFromFirebase());
        discardBtn.setOnMouseEntered(e -> discardBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + LIGHT_PINK + "; " +
                "-fx-text-fill: " + PRIMARY_TEXT + "; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
        ));
        discardBtn.setOnMouseExited(e -> discardBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_PINK + "; " +
                "-fx-text-fill: " + SECONDARY_TEXT + "; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
        ));

        Button saveBtn = new Button("💾 Save Nurse Profile & Documents");
        saveBtn.setPrefHeight(42);
        saveBtn.setPrefWidth(260);
        saveBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_PINK + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
        );
        saveBtn.setOnAction(e -> saveNurseDataToFirebase());
        saveBtn.setOnMouseEntered(e -> saveBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PINK_DARK + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
        ));
        saveBtn.setOnMouseExited(e -> saveBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_PINK + "; " +
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
        fileChooser.setTitle("Choose Nurse Profile Picture");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.webp")
        );

        File file = fileChooser.showOpenDialog(profileRoot != null && profileRoot.getScene() != null ? profileRoot.getScene().getWindow() : null);
        if (file == null) return;

        avatarStatus.setText("Uploading to Cloudinary...");
        avatarStatus.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + PRIMARY_PINK + "; -fx-font-weight: bold;");

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
                        avatarStatus.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + EMERALD_GREEN + "; -fx-font-weight: bold;");
                        showBanner("Profile picture uploaded to Cloudinary successfully! Remember to click Save.", true);
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    avatarStatus.setText("Upload Failed: " + ex.getMessage());
                    avatarStatus.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + DANGER_RED + ";");
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
        fileChooser.setTitle("Upload Nurse Document (" + docType + ")");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Document / Image Files", "*.pdf", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(profileRoot != null && profileRoot.getScene() != null ? profileRoot.getScene().getWindow() : null);
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
    private void loadNurseDataFromFirebase() {
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) return;

                // First try primary collection "nurse", then fallback to "nurseStaff"
                DocumentSnapshot doc = db.collection("nurse").document(nurseEmail).get().get();
                if (!doc.exists()) {
                    doc = db.collection("nurseStaff").document(nurseEmail).get().get();
                }

                DocumentSnapshot finalDoc = doc;
                Platform.runLater(() -> {
                    if (finalDoc != null && finalDoc.exists()) {
                        String name = finalDoc.getString("name");
                        String email = finalDoc.getString("email");
                        String phone = finalDoc.getString("phone");
                        String unit = finalDoc.getString("assignedUnit");
                        if (unit == null) unit = finalDoc.getString("department");
                        String spec = finalDoc.getString("specialization");
                        if (spec == null) spec = finalDoc.getString("role");
                        String exp = finalDoc.getString("experience");
                        String shift = finalDoc.getString("shift");
                        String blood = finalDoc.getString("bloodGroup");
                        String emgContact = finalDoc.getString("emergencyContact");

                        String licNum = finalDoc.getString("licenseNumber");
                        String idNum = finalDoc.getString("idProofNumber");
                        String certNum = finalDoc.getString("certificationNumber");
                        if (certNum == null) certNum = finalDoc.getString("lifeSupportCertNumber");
                        String medNum = finalDoc.getString("medicalFitnessNumber");

                        profilePicUrl = finalDoc.getString("profilePicUrl") != null ? finalDoc.getString("profilePicUrl") : "";
                        licenseDocUrl = finalDoc.getString("licenseDocUrl") != null ? finalDoc.getString("licenseDocUrl") : "";
                        idProofDocUrl = finalDoc.getString("idProofDocUrl") != null ? finalDoc.getString("idProofDocUrl") : "";
                        certificationDocUrl = finalDoc.getString("certificationDocUrl") != null ? finalDoc.getString("certificationDocUrl") : "";
                        if (certificationDocUrl.isEmpty() && finalDoc.getString("lifeSupportDocUrl") != null) {
                            certificationDocUrl = finalDoc.getString("lifeSupportDocUrl");
                        }
                        medicalFitnessDocUrl = finalDoc.getString("medicalFitnessDocUrl") != null ? finalDoc.getString("medicalFitnessDocUrl") : "";

                        if (name != null) nameField.setText(name);
                        if (email != null) emailField.setText(email);
                        if (phone != null) phoneField.setText(phone);
                        if (unit != null) assignedUnitField.setText(unit);
                        if (spec != null) specializationField.setText(spec);
                        if (exp != null) experienceField.setText(exp);
                        if (shift != null && shiftScheduleCombo.getItems().contains(shift)) {
                            shiftScheduleCombo.setValue(shift);
                        }
                        if (blood != null && bloodGroupCombo.getItems().contains(blood)) {
                            bloodGroupCombo.setValue(blood);
                        }
                        if (emgContact != null) emergencyContactField.setText(emgContact);

                        if (licNum != null) licenseNumberField.setText(licNum);
                        if (idNum != null) idProofNumberField.setText(idNum);
                        if (certNum != null) certificationNumberField.setText(certNum);
                        if (medNum != null) medicalFitnessNumberField.setText(medNum);

                        // Update Document Status Badges
                        if (!licenseDocUrl.isEmpty()) {
                            licenseStatusLabel.setText("✓ Verified on Cloudinary");
                            updateBadgeStyle(licenseStatusLabel, true);
                        }
                        if (!idProofDocUrl.isEmpty()) {
                            idProofStatusLabel.setText("✓ Verified on Cloudinary");
                            updateBadgeStyle(idProofStatusLabel, true);
                        }
                        if (!certificationDocUrl.isEmpty()) {
                            certificationStatusLabel.setText("✓ Verified on Cloudinary");
                            updateBadgeStyle(certificationStatusLabel, true);
                        }
                        if (!medicalFitnessDocUrl.isEmpty()) {
                            medicalFitnessStatusLabel.setText("✓ Verified on Cloudinary");
                            updateBadgeStyle(medicalFitnessStatusLabel, true);
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

                    } else {
                        // Populate default fallback values
                        emailField.setText(nurseEmail);
                        if (nameField.getText().isEmpty()) nameField.setText("Clinical Nurse");
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> showBanner("Error loading from Firestore: " + ex.getMessage(), false));
            }
        }).start();
    }

    private void saveNurseDataToFirebase() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", nameField.getText().trim());
        data.put("email", nurseEmail);
        data.put("phone", phoneField.getText().trim());
        data.put("assignedUnit", assignedUnitField.getText().trim());
        data.put("department", assignedUnitField.getText().trim());
        data.put("specialization", specializationField.getText().trim());
        data.put("role", specializationField.getText().trim());
        data.put("experience", experienceField.getText().trim());
        data.put("shift", shiftScheduleCombo.getValue());
        data.put("bloodGroup", bloodGroupCombo.getValue());
        data.put("emergencyContact", emergencyContactField.getText().trim());

        data.put("licenseNumber", licenseNumberField.getText().trim());
        data.put("idProofNumber", idProofNumberField.getText().trim());
        data.put("certificationNumber", certificationNumberField.getText().trim());
        data.put("medicalFitnessNumber", medicalFitnessNumberField.getText().trim());

        data.put("profilePicUrl", profilePicUrl);
        data.put("licenseDocUrl", licenseDocUrl);
        data.put("idProofDocUrl", idProofDocUrl);
        data.put("certificationDocUrl", certificationDocUrl);
        data.put("medicalFitnessDocUrl", medicalFitnessDocUrl);
        data.put("updatedAt", com.google.cloud.Timestamp.now());

        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db != null) {
                    // Update primary collection "nurse"
                    db.collection("nurse").document(nurseEmail).set(data, SetOptions.merge()).get();

                    // Also keep "nurseStaff" collection synchronized
                    try {
                        db.collection("nurseStaff").document(nurseEmail).set(data, SetOptions.merge()).get();
                    } catch (Exception ignored) {}

                    Platform.runLater(() -> {
                        showBanner("✓ Nurse profile and documents successfully saved !", true);
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
            saveStatusBanner.setStyle(FONT_FAMILY + "-fx-background-color: " + EMERALD_BG + "; -fx-text-fill: " + EMERALD_GREEN + "; -fx-font-size: 13px; -fx-font-weight: 600; -fx-background-radius: 8px; -fx-border-color: " + EMERALD_GREEN + "; -fx-border-width: 1px; -fx-border-radius: 8px; -fx-padding: 10 16;");
        } else {
            saveStatusBanner.setStyle(FONT_FAMILY + "-fx-background-color: " + VERY_LIGHT_PINK + "; -fx-text-fill: " + DANGER_RED + "; -fx-font-size: 13px; -fx-font-weight: 600; -fx-background-radius: 8px; -fx-border-color: " + PRIMARY_PINK + "; -fx-border-width: 1px; -fx-border-radius: 8px; -fx-padding: 10 16;");
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
        label.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-font-weight: 600; -fx-text-fill: " + SECONDARY_TEXT + ";");
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
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-width: 1.5px; " +
                "-fx-border-radius: 8px; " +
                "-fx-background-radius: 8px; " +
                "-fx-font-size: 13px; " +
                "-fx-text-fill: " + PRIMARY_TEXT + "; " +
                "-fx-padding: 0 12 0 12;"
        );
        tf.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                tf.setStyle(
                        FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + "; " +
                        "-fx-border-color: " + PRIMARY_PINK + "; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 13px; " +
                        "-fx-text-fill: " + PRIMARY_TEXT + "; " +
                        "-fx-padding: 0 12 0 12;"
                );
            } else {
                tf.setStyle(
                        FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + "; " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 13px; " +
                        "-fx-text-fill: " + PRIMARY_TEXT + "; " +
                        "-fx-padding: 0 12 0 12;"
                );
            }
        });
        return tf;
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