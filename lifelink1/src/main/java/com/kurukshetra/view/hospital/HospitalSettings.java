package com.kurukshetra.view.hospital;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.view.util.ShimmerLoader;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HospitalSettings {

    // =========================================================================
    // DESIGN SYSTEM CONSTANTS (MATCHING HospitalDashboard.java)
    // =========================================================================
    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";
    private static final String PRIMARY_TEAL = "#006591";
    private static final String TEAL_HOVER = "#004F72";
    private static final String PAGE_BG = "#a5bdaaff";
    private static final String SURFACE = "#FFFFFF";
    private static final String BORDER_COLOR = "#E2E8F0";
    private static final String TEXT_PRIMARY = "#0F172A";
    private static final String TEXT_SECONDARY = "#475569";
    private static final String TEXT_MUTED = "#64748B";

    private static final String SUCCESS_BG = "#ECFDF5";
    private static final String SUCCESS_TEXT = "#059669";
    private static final String SUCCESS_BORDER = "#A7F3D0";

    private static final String DANGER_BG = "#FEF2F2";
    private static final String DANGER_TEXT = "#DC2626";
    private static final String DANGER_BORDER = "#FECDD3";

    private static final String WARNING_BG = "#FFFBEB";
    private static final String WARNING_TEXT = "#D97706";
    private static final String WARNING_BORDER = "#FDE68A";

    private static final String INFO_BG = "#E0F2FE";
    private static final String INFO_TEXT = "#0369A1";
    private static final String INFO_BORDER = "#BAE6FD";

    private static final String INDIGO_BG = "#EEF2FF";
    private static final String INDIGO_TEXT = "#4F46E5";
    private static final String INDIGO_BORDER = "#C7D2FE";

    private static final String CARD_STYLE = "-fx-background-color: " + SURFACE + "; " +
            "-fx-border-color: " + BORDER_COLOR + "; " +
            "-fx-border-radius: 14px; " +
            "-fx-background-radius: 14px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 14, 0, 0, 3);";

    private String hospitalEmail;
    private String targetDocId = null;
    private VBox pageContent;
    private ShimmerLoader.ShimmerPane settingsShimmer;

    // Map to hold document file references
    private final Map<String, String> documentFilesMap = new HashMap<>();

    // Form Field Controls (Hospital Profile)
    private TextField hospitalNameField;
    private TextField emailField;
    private TextField phoneField;
    private TextField emergencyPhoneField;
    private ComboBox<String> categoryCombo;
    private ComboBox<String> typeCombo;
    private TextField cmoField;
    private TextField emergencyContactField;
    private TextField websiteField;
    private TextField fleetSizeField;

    // Form Field Controls (Location & Coordinates)
    private TextField addressField;
    private TextField cityField;
    private TextField stateField;
    private TextField pincodeField;
    private TextField latField;
    private TextField lngField;

    // Form Field Controls (Accreditation & Licenses)
    private TextField regNoField;
    private ComboBox<String> accreditationCombo;
    private ComboBox<String> traumaLevelCombo;

    // Document Fields & Labels
    private TextField clinicalLicenseNoField;
    private TextField clinicalLicenseExpiryField;
    private Label clinicalLicenseStatusLbl;

    private TextField nabhCertNoField;
    private TextField nabhValidityField;
    private Label nabhStatusLbl;

    private TextField fireNocIdField;
    private TextField fireNocExpiryField;
    private Label fireNocStatusLbl;

    private TextField bioWasteNoField;
    private TextField bioWasteExpiryField;
    private Label bioWasteStatusLbl;

    private TextField pharmacyLicenseNoField;
    private TextField pharmacyExpiryField;
    private Label pharmacyStatusLbl;

    private TextField eopVerField;
    private TextField eopReviewField;
    private Label eopStatusLbl;

    private TextField addlDocNameField;
    private TextField addlDocNotesField;
    private Label addlDocStatusLbl;

    // Preferences CheckBoxes
    private CheckBox autoEmergencyPreemptionCheck;
    private CheckBox directCoordinationCheck;
    private CheckBox specialistPagingCheck;
    private CheckBox icuDiversionCheck;
    private ComboBox<String> syncIntervalCombo;
    private ComboBox<String> defaultViewCombo;

    // Header Status & Feedback Labels
    private Label overallMsg;
    private Label systemStatusText;
    private Text statDocsValue;
    private Text statHotlineValue;
    private Text statLocationValue;
    private Text statEntityValue;

    public HospitalSettings() {
        this.hospitalEmail = HospitalDashboard.hospitalEmail;
    }

    public HospitalSettings(String hospitalEmail) {
        if (hospitalEmail != null && !hospitalEmail.trim().isEmpty()) {
            this.hospitalEmail = hospitalEmail.trim();
        } else {
            this.hospitalEmail = HospitalDashboard.hospitalEmail;
        }
    }

    public VBox getSettings() {

        pageContent = new VBox(24);
        pageContent.setPadding(new Insets(26, 32, 36, 32));
        pageContent.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        // =====================================================================
        // 1. TOP HEADER (TITLE & LIVE SYSTEM BADGE)
        // =====================================================================
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox headingBox = new VBox(4);
        Text heading = new Text("Hospital Facility Profile & Regulatory Settings");
        heading.setStyle(FONT_FAMILY + "-fx-font-size: 26px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");

        String safeEmail = (hospitalEmail != null && !hospitalEmail.isEmpty()) ? hospitalEmail
                : "hospital@lifelink.com";
        headingBox.getChildren().addAll(heading);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox systemStatusChip = new HBox(8);
        systemStatusChip.setAlignment(Pos.CENTER);
        systemStatusChip.setPadding(new Insets(6, 14, 6, 14));
        systemStatusChip.setStyle("-fx-background-color: " + SUCCESS_BG
                + "; -fx-background-radius: 20px; -fx-border-color: " + SUCCESS_BORDER + "; -fx-border-radius: 20px;");
        Circle liveDot = new Circle(4, Color.web(SUCCESS_TEXT));
        systemStatusText = new Label("● Cloud Connected • 'hospital' Collection");
        systemStatusText.setStyle(
                FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: bold; -fx-text-fill: " + SUCCESS_TEXT + ";");
        systemStatusChip.getChildren().addAll(liveDot, systemStatusText);

        header.getChildren().addAll(headingBox, headerSpacer, systemStatusChip);

        // =====================================================================
        // 2. 4-CARD TELEMETRY STRIP
        // =====================================================================
        HBox metricStrip = new HBox(16);

        statEntityValue = new Text("VERIFIED ENTITY");
        VBox facilityCard = createMetricCard("🏥", PRIMARY_TEAL, INFO_BG, "INSTITUTION STATUS", statEntityValue,
                "LEVEL-1 TRAUMA", SUCCESS_BG, SUCCESS_TEXT);

        statHotlineValue = new Text("+91 20 2567 8900");
        VBox hotlineCard = createMetricCard("🚨", DANGER_TEXT, DANGER_BG, "EMERGENCY HOTLINE", statHotlineValue,
                "24x7 ACTIVE", DANGER_BG, DANGER_TEXT);

        statLocationValue = new Text("Pune, MH");
        VBox locationCard = createMetricCard("📍", INDIGO_TEXT, INDIGO_BG, "DISPATCH BASE", statLocationValue,
                "GEO-VERIFIED", INDIGO_BG, INDIGO_TEXT);

        statDocsValue = new Text("0 Uploaded");
        VBox docsCard = createMetricCard("📜", WARNING_TEXT, WARNING_BG, "REGULATORY DOCUMENTS", statDocsValue,
                "COMPLIANCE", WARNING_BG, WARNING_TEXT);

        metricStrip.getChildren().addAll(facilityCard, hotlineCard, locationCard, docsCard);

        // =====================================================================
        // 3. HOSPITAL PROFILE SECTION (MAPPED TO 'hospital' FIRESTORE)
        // =====================================================================
        VBox hospitalSection = new VBox(18);
        hospitalSection.setPadding(new Insets(24));
        hospitalSection.setStyle(CARD_STYLE);

        HBox hHeader = createSectionHeader("🏥", INFO_BG, PRIMARY_TEAL,
                "Hospital Facility & Administrative Information");

        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(20);
        infoGrid.setVgap(16);

        // Row 0
        VBox nameBox = new VBox(6);
        Label nameLbl = createFieldLabel("HOSPITAL REGISTERED NAME *");
        hospitalNameField = createStyledTextField(
                HospitalDashboard.hospitalName != null ? HospitalDashboard.hospitalName : "KEM Hospital Pune");
        nameBox.getChildren().addAll(nameLbl, hospitalNameField);
        infoGrid.add(nameBox, 0, 0);

        VBox emailBox = new VBox(6);
        Label emailLbl = createFieldLabel("ADMINISTRATIVE / LOGIN EMAIL *");
        emailField = createStyledTextField(safeEmail);
        emailField.setEditable(false);
        emailField.setStyle(emailField.getStyle() + "-fx-opacity: 0.85; -fx-background-color: #F1F5F9;");
        emailBox.getChildren().addAll(emailLbl, emailField);
        infoGrid.add(emailBox, 1, 0);

        // Row 1
        VBox phoneBox = new VBox(6);
        Label phoneLbl = createFieldLabel("MAIN RECEPTION TELEPHONE");
        phoneField = createStyledTextField("+91 20 6603 7300");
        phoneBox.getChildren().addAll(phoneLbl, phoneField);
        infoGrid.add(phoneBox, 0, 1);

        VBox emgPhoneBox = new VBox(6);
        Label emgPhoneLbl = createFieldLabel("24x7 EMERGENCY & TRAUMA HOTLINE *");
        emergencyPhoneField = createStyledTextField("+91 20 2612 5600");
        emgPhoneBox.getChildren().addAll(emgPhoneLbl, emergencyPhoneField);
        infoGrid.add(emgPhoneBox, 1, 1);

        // Row 2
        VBox catBox = new VBox(6);
        Label catLbl = createFieldLabel("HOSPITAL SPECIALTY CATEGORY");
        categoryCombo = new ComboBox<>();
        categoryCombo.getItems().addAll(
                "Multi-Specialty Hospital",
                "Super Specialty Hospital",
                "Comprehensive Trauma Care Center",
                "Cardiology Specialty Hospital",
                "General Hospital",
                "Maternity & Pediatric Care");
        categoryCombo.setValue("Multi-Specialty Hospital");
        categoryCombo.setMaxWidth(Double.MAX_VALUE);
        categoryCombo.setPrefHeight(40);
        categoryCombo.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");
        catBox.getChildren().addAll(catLbl, categoryCombo);
        infoGrid.add(catBox, 0, 2);

        VBox typeBox = new VBox(6);
        Label typeLbl = createFieldLabel("HOSPITAL ENTITY TYPE");
        typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll(
                "Private",
                "Government / Civil",
                "Trust / Charitable",
                "Corporate Healthcare");
        typeCombo.setValue("Trust / Charitable");
        typeCombo.setMaxWidth(Double.MAX_VALUE);
        typeCombo.setPrefHeight(40);
        typeCombo.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");
        typeBox.getChildren().addAll(typeLbl, typeCombo);
        infoGrid.add(typeBox, 1, 2);

        // Row 3
        VBox cmoBox = new VBox(6);
        Label cmoLbl = createFieldLabel("CHIEF MEDICAL OFFICER (CMO) / SUPERINTENDENT");
        cmoField = createStyledTextField("Dr. Rajeshwar Patil, MS (Trauma)");
        cmoBox.getChildren().addAll(cmoLbl, cmoField);
        infoGrid.add(cmoBox, 0, 3);

        VBox emgInchargeBox = new VBox(6);
        Label emgInchargeLbl = createFieldLabel("EMERGENCY IN-CHARGE / ADMINISTRATOR");
        emergencyContactField = createStyledTextField("Dr. Priya Deshmukh, MD (Emergency Medicine)");
        emgInchargeBox.getChildren().addAll(emgInchargeLbl, emergencyContactField);
        infoGrid.add(emgInchargeBox, 1, 3);

        // Row 4
        VBox webBox = new VBox(6);
        Label webLbl = createFieldLabel("OFFICIAL INSTITUTIONAL WEBSITE");
        websiteField = createStyledTextField("https://www.kemhospitalpune.org");
        webBox.getChildren().addAll(webLbl, websiteField);
        infoGrid.add(webBox, 0, 4);

        VBox fleetBox = new VBox(6);
        Label fleetLbl = createFieldLabel("REGISTERED AMBULANCE FLEET UNITS");
        fleetSizeField = createStyledTextField("15");
        fleetBox.getChildren().addAll(fleetLbl, fleetSizeField);
        infoGrid.add(fleetBox, 1, 4);

        applyColumnConstraints(infoGrid);
        hospitalSection.getChildren().addAll(hHeader, infoGrid);

        // =====================================================================
        // 4. DISPATCH LOCATION & GEO-COORDINATES SECTION
        // =====================================================================
        VBox locationSection = new VBox(18);
        locationSection.setPadding(new Insets(24));
        locationSection.setStyle(CARD_STYLE);

        HBox locHeader = createSectionHeader("📍", INDIGO_BG, INDIGO_TEXT,
                "Physical Location & Dispatch Geo-Coordinates");

        GridPane locGrid = new GridPane();
        locGrid.setHgap(20);
        locGrid.setVgap(16);

        VBox addrBox = new VBox(6);
        Label addrLbl = createFieldLabel("PHYSICAL DISPATCH ADDRESS *");
        addressField = createStyledTextField("Rasta Peth, Sardar Moodliar Road, Near Pune Railway Station");
        addrBox.getChildren().addAll(addrLbl, addressField);
        locGrid.add(addrBox, 0, 0);

        VBox cityBox = new VBox(6);
        Label cityLbl = createFieldLabel("CITY / MUNICIPAL CORP");
        cityField = createStyledTextField("Pune");
        cityBox.getChildren().addAll(cityLbl, cityField);
        locGrid.add(cityBox, 1, 0);

        VBox stateBox = new VBox(6);
        Label stateLbl = createFieldLabel("STATE / PROVINCE");
        stateField = createStyledTextField("Maharashtra");
        stateBox.getChildren().addAll(stateLbl, stateField);
        locGrid.add(stateBox, 0, 1);

        VBox pinBox = new VBox(6);
        Label pinLbl = createFieldLabel("POSTAL PINCODE");
        pincodeField = createStyledTextField("411011");
        pinBox.getChildren().addAll(pinLbl, pincodeField);
        locGrid.add(pinBox, 1, 1);

        VBox latBox = new VBox(6);
        Label latLbl = createFieldLabel("GPS LATITUDE (DECIMAL DEGREES) *");
        latField = createStyledTextField("18.5204");
        latBox.getChildren().addAll(latLbl, latField);
        locGrid.add(latBox, 0, 2);

        VBox lngBox = new VBox(6);
        Label lngLbl = createFieldLabel("GPS LONGITUDE (DECIMAL DEGREES) *");
        lngField = createStyledTextField("73.8567");
        lngBox.getChildren().addAll(lngLbl, lngField);
        locGrid.add(lngBox, 1, 2);

        applyColumnConstraints(locGrid);
        locationSection.getChildren().addAll(locHeader, locGrid);

        // =====================================================================
        // 5. ACCREDITATIONS & QUALITY STANDARDS
        // =====================================================================
        VBox accredSection = new VBox(18);
        accredSection.setPadding(new Insets(24));
        accredSection.setStyle(CARD_STYLE);

        HBox accHeader = createSectionHeader("🎖️", SUCCESS_BG, SUCCESS_TEXT,
                "Clinical Accreditations & Licensing Standard");

        GridPane accGrid = new GridPane();
        accGrid.setHgap(20);
        accGrid.setVgap(16);

        VBox regBox = new VBox(6);
        Label regLbl = createFieldLabel("CLINICAL ESTABLISHMENT REGISTRATION NUMBER *");
        regNoField = createStyledTextField("MH-PUN-CLIN-2024-8849");
        regBox.getChildren().addAll(regLbl, regNoField);
        accGrid.add(regBox, 0, 0);

        VBox accBox = new VBox(6);
        Label accLbl = createFieldLabel("QUALITY ACCREDITATION STATUS");
        accreditationCombo = new ComboBox<>();
        accreditationCombo.getItems().addAll(
                "NABH Accredited (Full)",
                "NABH Entry Level",
                "JCI International Accredited",
                "NABL Laboratory Certified",
                "ISO 9001:2015",
                "State Health Department Certified",
                "Application In Review");
        accreditationCombo.setValue("NABH Accredited (Full)");
        accreditationCombo.setMaxWidth(Double.MAX_VALUE);
        accreditationCombo.setPrefHeight(40);
        accreditationCombo.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");
        accBox.getChildren().addAll(accLbl, accreditationCombo);
        accGrid.add(accBox, 1, 0);

        VBox traumaBox = new VBox(6);
        Label traumaLbl = createFieldLabel("DESIGNATED TRAUMA CENTER LEVEL");
        traumaLevelCombo = new ComboBox<>();
        traumaLevelCombo.getItems().addAll(
                "Level 1 - Comprehensive Trauma Center",
                "Level 2 - Major Trauma Center",
                "Level 3 - General Emergency Center",
                "Level 4 - Urgent Care Facility");
        traumaLevelCombo.setValue("Level 1 - Comprehensive Trauma Center");
        traumaLevelCombo.setMaxWidth(Double.MAX_VALUE);
        traumaLevelCombo.setPrefHeight(40);
        traumaLevelCombo.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");
        traumaBox.getChildren().addAll(traumaLbl, traumaLevelCombo);
        accGrid.add(traumaBox, 0, 1);

        applyColumnConstraints(accGrid);
        accredSection.getChildren().addAll(accHeader, accGrid);

        // =====================================================================
        // 6. HOSPITAL-RELATED STATUTORY & REGULATORY DOCUMENTS (NEW FEATURE)
        // =====================================================================
        VBox docsSection = new VBox(18);
        docsSection.setPadding(new Insets(24));
        docsSection.setStyle(CARD_STYLE);

        HBox dHeader = createSectionHeader("📁", WARNING_BG, WARNING_TEXT,
                "Hospital Compliance Documents & Certifications");

        VBox docListContainer = new VBox(14);

        // Document 1: Clinical Establishment License
        clinicalLicenseNoField = createStyledTextField("LIC-MH-2024-991");
        clinicalLicenseExpiryField = createStyledTextField("31-Dec-2027");
        clinicalLicenseStatusLbl = createDocStatusLabel("No document attached");
        VBox doc1Card = createDocumentRow(
                "📜",
                "Clinical Establishment Registration & License",
                "LICENSE / REG NO", clinicalLicenseNoField,
                "VALID UNTIL / EXPIRY", clinicalLicenseExpiryField,
                "clinicalLicenseDoc", clinicalLicenseStatusLbl);

        // Document 2: NABH Accreditation Certificate
        nabhCertNoField = createStyledTextField("NABH-HOSP-2023-0418");
        nabhValidityField = createStyledTextField("15-Aug-2026");
        nabhStatusLbl = createDocStatusLabel("No document attached");
        VBox doc2Card = createDocumentRow(
                "🏆",
                "NABH / JCI Accreditation Certificate",
                "CERTIFICATE NUMBER", nabhCertNoField,
                "VALIDITY DATE", nabhValidityField,
                "nabhAccreditationDoc", nabhStatusLbl);

        // Document 3: Fire Safety & Emergency Evacuation Clearance (NOC)
        fireNocIdField = createStyledTextField("PMRDA-FIRE-NOC-2024-77");
        fireNocExpiryField = createStyledTextField("30-Jun-2025");
        fireNocStatusLbl = createDocStatusLabel("No document attached");
        VBox doc3Card = createDocumentRow(
                "🧯",
                "Fire Safety Clearance Certificate (NOC)",
                "FIRE NOC REFERENCE ID", fireNocIdField,
                "ANNUAL RENEWAL DATE", fireNocExpiryField,
                "fireSafetyNocDoc", fireNocStatusLbl);

        // Document 4: Bio-Medical Waste Authorization
        bioWasteNoField = createStyledTextField("MPCB-BMW-AUTH-4412");
        bioWasteExpiryField = createStyledTextField("31-Mar-2028");
        bioWasteStatusLbl = createDocStatusLabel("No document attached");
        VBox doc4Card = createDocumentRow(
                "☣️",
                "Bio-Medical Waste Management Authorization",
                "AUTHORIZATION NO", bioWasteNoField,
                "AUTHORIZATION VALIDITY", bioWasteExpiryField,
                "bioMedicalWasteDoc", bioWasteStatusLbl);

        // Document 5: 24x7 Pharmacy & Blood Bank Drug License
        pharmacyLicenseNoField = createStyledTextField("FDA-MH-20B-21B-5531");
        pharmacyExpiryField = createStyledTextField("10-Oct-2026");
        pharmacyStatusLbl = createDocStatusLabel("No document attached");
        VBox doc5Card = createDocumentRow(
                "💊",
                "In-House Pharmacy & Blood Storage Drug License",
                "DRUG LICENSE NUMBER", pharmacyLicenseNoField,
                "EXPIRY DATE", pharmacyExpiryField,
                "pharmacyBloodBankDoc", pharmacyStatusLbl);

        // Document 6: Hospital Emergency Operations Plan (EOP) / Trauma SOP
        eopVerField = createStyledTextField("EOP-REV-4.2");
        eopReviewField = createStyledTextField("01-Jan-2025");
        eopStatusLbl = createDocStatusLabel("No document attached");
        VBox doc6Card = createDocumentRow(
                "📋",
                "Hospital Emergency Operations Plan (EOP) & Trauma SOP",
                "PROTOCOL VERSION", eopVerField,
                "LAST REVIEW DATE", eopReviewField,
                "emergencyOpsPlanDoc", eopStatusLbl);

        // Document 7: Additional Statutory Document / MOU
        addlDocNameField = createStyledTextField("City Police Green Corridor MOU");
        addlDocNotesField = createStyledTextField("Direct corridor coordination clearance");
        addlDocStatusLbl = createDocStatusLabel("No document attached");
        VBox doc7Card = createDocumentRow(
                "📑",
                "Additional Compliance Document / Inter-Agency MOU",
                "DOCUMENT TITLE", addlDocNameField,
                "NOTES / REMARKS", addlDocNotesField,
                "additionalDocFile", addlDocStatusLbl);

        docListContainer.getChildren().addAll(doc1Card, doc2Card, doc3Card, doc4Card, doc5Card, doc6Card, doc7Card);
        docsSection.getChildren().addAll(dHeader, docListContainer);

        // =====================================================================
        // 7. EMERGENCY PROTOCOLS & INTERLOCK PREFERENCES
        // =====================================================================
        VBox emergencySection = new VBox(18);
        emergencySection.setPadding(new Insets(24));
        emergencySection.setStyle(CARD_STYLE);

        HBox eHeader = createSectionHeader("⚡", DANGER_BG, DANGER_TEXT, "Emergency Protocol & Interlock Configuration");

        VBox eList = new VBox(12);
        autoEmergencyPreemptionCheck = createStyledCheckBox("Enable Automatic Emergency Preemption",
                "Automatically prepare triage room and clear corridors upon inbound high-priority transit.", true);
        directCoordinationCheck = createStyledCheckBox("Direct Ambulance Fleet Coordination",
                "Share telemetry and bed reservation directly with inbound ambulance crew.", true);
        specialistPagingCheck = createStyledCheckBox("Surgeon & Trauma Specialist Paging",
                "Page on-call surgeons and emergency physicians via SMS/In-App immediately on ambulance notification.",
                true);
        icuDiversionCheck = createStyledCheckBox("ICU Critical Threshold Diversion",
                "Automatically signal diversion warning to city dispatch when ICU reaches 95% capacity.", true);

        eList.getChildren().addAll(autoEmergencyPreemptionCheck, directCoordinationCheck, specialistPagingCheck,
                icuDiversionCheck);
        emergencySection.getChildren().addAll(eHeader, eList);

        // =====================================================================
        // 8. TELEMETRY & DISPLAY PREFERENCES
        // =====================================================================
        VBox systemSection = new VBox(18);
        systemSection.setPadding(new Insets(24));
        systemSection.setStyle(CARD_STYLE);

        HBox sHeader = createSectionHeader("⚙️", INFO_BG, PRIMARY_TEAL, "Telemetry & Display Preferences");

        HBox sRow = new HBox(24);

        VBox refreshBox = new VBox(6);
        Label refreshLabel = createFieldLabel("TELEMETRY SYNC INTERVAL");
        syncIntervalCombo = new ComboBox<>();
        syncIntervalCombo.getItems().addAll("5 Seconds (High Precision)", "10 Seconds (Standard)", "30 Seconds",
                "1 Minute");
        syncIntervalCombo.setValue("10 Seconds (Standard)");
        syncIntervalCombo.setPrefWidth(260);
        syncIntervalCombo.setPrefHeight(40);
        syncIntervalCombo.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");
        refreshBox.getChildren().addAll(refreshLabel, syncIntervalCombo);

        VBox viewModeBox = new VBox(6);
        Label viewModeLabel = createFieldLabel("DEFAULT EMERGENCY VIEW");
        defaultViewCombo = new ComboBox<>();
        defaultViewCombo.getItems().addAll("Emergency Radar & Live Corridor", "Trauma Reception Grid",
                "Resource & Bed Availability");
        defaultViewCombo.setValue("Emergency Radar & Live Corridor");
        defaultViewCombo.setPrefWidth(260);
        defaultViewCombo.setPrefHeight(40);
        defaultViewCombo.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");
        viewModeBox.getChildren().addAll(viewModeLabel, defaultViewCombo);

        sRow.getChildren().addAll(refreshBox, viewModeBox);
        systemSection.getChildren().addAll(sHeader, sRow);

        // =====================================================================
        // 9. ACTION BAR & SAVE CONFIRMATION (DIRECT FIRESTORE WRITE)
        // =====================================================================
        HBox actionBar = new HBox(14);
        actionBar.setPadding(new Insets(18, 24, 18, 24));
        actionBar.setAlignment(Pos.CENTER_RIGHT);
        actionBar.setStyle(CARD_STYLE);

        overallMsg = new Label();
        overallMsg.setStyle(
                FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + SUCCESS_TEXT + ";");

        Region actSpacer = new Region();
        HBox.setHgrow(actSpacer, Priority.ALWAYS);

        Button reloadBtn = new Button("Refresh from Cloud");
        reloadBtn.setPrefHeight(44);
        reloadBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + "; " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-text-fill: " + TEXT_SECONDARY + "; " +
                        "-fx-font-size: 13px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-padding: 0 20; " +
                        "-fx-cursor: hand;");
        reloadBtn.setOnAction(e -> loadHospitalDataFromFirestore());

        Button saveAllButton = new Button("Save Hospital Details & Documents");
        saveAllButton.setPrefHeight(44);
        saveAllButton.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + PRIMARY_TEAL + "; " +
                        "-fx-text-fill: #FFFFFF; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 13.5px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 0 26; " +
                        "-fx-cursor: hand;");
        saveAllButton.setEffect(new DropShadow(10, 0, 2, Color.rgb(0, 101, 145, 0.25)));

        saveAllButton.setOnMouseEntered(e -> {
            saveAllButton.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: " + TEAL_HOVER + "; " +
                            "-fx-text-fill: #FFFFFF; " +
                            "-fx-background-radius: 8px; " +
                            "-fx-font-size: 13.5px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 0 26; " +
                            "-fx-cursor: hand;");
            saveAllButton.setTranslateY(-2);
        });
        saveAllButton.setOnMouseExited(e -> {
            saveAllButton.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: " + PRIMARY_TEAL + "; " +
                            "-fx-text-fill: #FFFFFF; " +
                            "-fx-background-radius: 8px; " +
                            "-fx-font-size: 13.5px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 0 26; " +
                            "-fx-cursor: hand;");
            saveAllButton.setTranslateY(0);
        });

        saveAllButton.setOnAction(e -> saveHospitalDataToFirestore(saveAllButton));

        actionBar.getChildren().addAll(overallMsg, actSpacer, reloadBtn, saveAllButton);

        // =====================================================================
        // ASSEMBLE PAGE
        // =====================================================================
        settingsShimmer = ShimmerLoader.createTableSkeleton(2, 4, 1100, 110);
        pageContent.getChildren().addAll(
                header,
                settingsShimmer,
                metricStrip,
                hospitalSection,
                locationSection,
                accredSection,
                docsSection,
                emergencySection,
                systemSection,
                actionBar);

        ScrollPane scrollPane = new ScrollPane(pageContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle(
                "-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");

        VBox settingsPage = new VBox(scrollPane);
        settingsPage.setStyle("-fx-background-color: " + PAGE_BG + ";");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Fetch existing hospital profile & documents from Firestore
        loadHospitalDataFromFirestore();

        return settingsPage;
    }

    // =========================================================================
    // FIRESTORE READ: LOAD HOSPITAL RECORD & DOCUMENTS
    // =========================================================================
    private void loadHospitalDataFromFirestore() {
        Platform.runLater(() -> {
            if (systemStatusText != null) {
                systemStatusText.setText("● Syncing with Cloud 'hospital' Collection...");
            }
            if (pageContent != null && settingsShimmer == null) {
                settingsShimmer = ShimmerLoader.createTableSkeleton(2, 4, 1100, 110);
                if (pageContent.getChildren().size() > 1) {
                    pageContent.getChildren().add(1, settingsShimmer);
                }
            }
        });
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) {
                    Platform.runLater(() -> overallMsg.setText("Firebase is not initialized."));
                    return;
                }

                String cleanEmail = (hospitalEmail != null && !hospitalEmail.trim().isEmpty())
                        ? hospitalEmail.trim()
                        : HospitalDashboard.hospitalEmail;

                DocumentSnapshot targetDoc = null;

                // 1. Direct document lookup by email
                DocumentSnapshot doc = db.collection("hospital").document(cleanEmail).get().get();
                if (doc != null && doc.exists()) {
                    targetDoc = doc;
                    targetDocId = cleanEmail;
                } else {
                    // 2. Query where email == cleanEmail
                    QuerySnapshot q1 = db.collection("hospital").whereEqualTo("email", cleanEmail).get().get();
                    if (q1 != null && !q1.isEmpty()) {
                        targetDoc = q1.getDocuments().get(0);
                        targetDocId = targetDoc.getId();
                    } else {
                        // 3. Query where hospitalEmail == cleanEmail
                        QuerySnapshot q2 = db.collection("hospital").whereEqualTo("hospitalEmail", cleanEmail).get()
                                .get();
                        if (q2 != null && !q2.isEmpty()) {
                            targetDoc = q2.getDocuments().get(0);
                            targetDocId = targetDoc.getId();
                        }
                    }
                }

                if (targetDocId == null) {
                    targetDocId = cleanEmail;
                }

                final DocumentSnapshot finalDoc = targetDoc;
                Platform.runLater(() -> populateUIWithHospitalData(finalDoc));

            } catch (Exception ex) {
                System.err.println("[HospitalSettings] Error loading hospital details: " + ex.getMessage());
                Platform.runLater(
                        () -> overallMsg.setText("Warning: Could not fetch cloud data (" + ex.getMessage() + ")"));
            }
        }).start();
    }

    private void populateUIWithHospitalData(DocumentSnapshot doc) {
        if (settingsShimmer != null) {
            settingsShimmer.stop();
            if (pageContent != null) {
                pageContent.getChildren().remove(settingsShimmer);
            }
            settingsShimmer = null;
        }
        if (systemStatusText != null) {
            systemStatusText.setText("● Cloud Connected • 'hospital' Collection");
        }

        if (doc == null || !doc.exists()) {
            overallMsg.setText("No cloud record found for this hospital yet. Please complete details and save.");
            overallMsg.setStyle(
                    FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + WARNING_TEXT + ";");
            return;
        }

        // Profile fields
        String name = getString(doc, "hospitalName", getString(doc, "name", "KEM Hospital Pune"));
        hospitalNameField.setText(name);

        String email = getString(doc, "email", getString(doc, "hospitalEmail", hospitalEmail));
        emailField.setText(email);

        phoneField.setText(getString(doc, "phoneNumber", getString(doc, "phone", "+91 20 6603 7300")));
        emergencyPhoneField.setText(getString(doc, "emergencyPhone", "+91 20 2612 5600"));

        String cat = getString(doc, "hospitalCategory", "Multi-Specialty Hospital");
        if (categoryCombo.getItems().contains(cat))
            categoryCombo.setValue(cat);

        String type = getString(doc, "hospitalType", "Trust / Charitable");
        if (typeCombo.getItems().contains(type))
            typeCombo.setValue(type);

        cmoField.setText(getString(doc, "chiefMedicalOfficer", "Dr. Rajeshwar Patil, MS (Trauma)"));
        emergencyContactField
                .setText(getString(doc, "emergencyContactPerson", "Dr. Priya Deshmukh, MD (Emergency Medicine)"));
        websiteField.setText(getString(doc, "website", "https://www.kemhospitalpune.org"));
        fleetSizeField.setText(String.valueOf(getInt(doc, "ambulanceFleetSize", 15)));

        // Location fields
        addressField.setText(getString(doc, "address",
                getString(doc, "hospitalAddress", "Rasta Peth, Sardar Moodliar Road, Near Pune Railway Station")));
        cityField.setText(getString(doc, "city", "Pune"));
        stateField.setText(getString(doc, "state", "Maharashtra"));
        pincodeField.setText(getString(doc, "pincode", "411011"));
        latField.setText(String.valueOf(getDouble(doc, "latitude", getDouble(doc, "lat", 18.5204))));
        lngField.setText(String.valueOf(getDouble(doc, "longitude", getDouble(doc, "lng", 73.8567))));

        // Accreditations
        regNoField.setText(getString(doc, "registrationNumber", "MH-PUN-CLIN-2024-8849"));

        String accred = getString(doc, "accreditation", "NABH Accredited (Full)");
        if (accreditationCombo.getItems().contains(accred))
            accreditationCombo.setValue(accred);

        String trauma = getString(doc, "traumaLevel", "Level 1 - Comprehensive Trauma Center");
        if (traumaLevelCombo.getItems().contains(trauma))
            traumaLevelCombo.setValue(trauma);

        // Document Details & Attached Files
        bindDocumentFromDoc(doc, "clinicalLicenseDoc", "clinicalLicenseDocNo", "clinicalLicenseExpiry",
                clinicalLicenseNoField, clinicalLicenseExpiryField, clinicalLicenseStatusLbl);

        bindDocumentFromDoc(doc, "nabhAccreditationDoc", "nabhCertificateNo", "nabhValidityDate",
                nabhCertNoField, nabhValidityField, nabhStatusLbl);

        bindDocumentFromDoc(doc, "fireSafetyNocDoc", "fireSafetyNocId", "fireSafetyRenewalDate",
                fireNocIdField, fireNocExpiryField, fireNocStatusLbl);

        bindDocumentFromDoc(doc, "bioMedicalWasteDoc", "bioMedicalAuthNo", "bioMedicalValidityDate",
                bioWasteNoField, bioWasteExpiryField, bioWasteStatusLbl);

        bindDocumentFromDoc(doc, "pharmacyBloodBankDoc", "pharmacyLicenseNo", "pharmacyLicenseExpiry",
                pharmacyLicenseNoField, pharmacyExpiryField, pharmacyStatusLbl);

        bindDocumentFromDoc(doc, "emergencyOpsPlanDoc", "emergencyOpsDocVer", "emergencyOpsReviewDate",
                eopVerField, eopReviewField, eopStatusLbl);

        bindDocumentFromDoc(doc, "additionalDocFile", "additionalDocName", "additionalDocNotes",
                addlDocNameField, addlDocNotesField, addlDocStatusLbl);

        // Preferences
        autoEmergencyPreemptionCheck.setSelected(getBoolean(doc, "autoEmergencyPreemption", true));
        directCoordinationCheck.setSelected(getBoolean(doc, "directAmbulanceCoordination", true));
        specialistPagingCheck.setSelected(getBoolean(doc, "traumaSpecialistPaging", true));
        icuDiversionCheck.setSelected(getBoolean(doc, "icuCriticalDiversion", true));

        String interval = getString(doc, "telemetrySyncInterval", "10 Seconds (Standard)");
        if (syncIntervalCombo.getItems().contains(interval))
            syncIntervalCombo.setValue(interval);

        String defView = getString(doc, "defaultEmergencyView", "Emergency Radar & Live Corridor");
        if (defaultViewCombo.getItems().contains(defView))
            defaultViewCombo.setValue(defView);

        // Update Top Strip Cards
        updateMetricStripDisplay();

        // overallMsg.setText("✓ Cloud profile and compliance documents loaded.");
        // overallMsg.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight:
        // bold; -fx-text-fill: " + SUCCESS_TEXT + ";");
    }

    private void bindDocumentFromDoc(DocumentSnapshot doc, String docKey, String noKey, String dateKey,
            TextField noField, TextField dateField, Label statusLbl) {
        String num = getString(doc, noKey, "");
        if (!num.isEmpty())
            noField.setText(num);

        String date = getString(doc, dateKey, "");
        if (!date.isEmpty())
            dateField.setText(date);

        String filePath = getString(doc, docKey, "");
        if (!filePath.isEmpty()) {
            documentFilesMap.put(docKey, filePath);
            String displayName = new File(filePath).getName();
            statusLbl.setText("✓ Stored: " + displayName);
            statusLbl.setStyle(FONT_FAMILY + "-fx-background-color: " + SUCCESS_BG + "; -fx-text-fill: " + SUCCESS_TEXT
                    + "; -fx-border-color: " + SUCCESS_BORDER
                    + "; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-font-size: 11px; -fx-padding: 4px 8px; -fx-font-weight: bold;");
        }
    }

    // =========================================================================
    // FIRESTORE WRITE: SAVE ALL HOSPITAL FIELDS & DOCUMENTS
    // =========================================================================
    private void saveHospitalDataToFirestore(Button saveBtn) {
        saveBtn.setDisable(true);
        saveBtn.setText("Saving to Cloud...");
        overallMsg.setText("Uploading hospital details & documents to Firestore...");
        overallMsg.setStyle(
                FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + INFO_TEXT + ";");

        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) {
                    Platform.runLater(() -> {
                        saveBtn.setDisable(false);
                        saveBtn.setText("Save Hospital Details & Documents");
                        overallMsg.setText("Error: Firebase is not initialized.");
                        overallMsg.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: "
                                + DANGER_TEXT + ";");
                    });
                    return;
                }

                String cleanEmail = (hospitalEmail != null && !hospitalEmail.trim().isEmpty())
                        ? hospitalEmail.trim()
                        : HospitalDashboard.hospitalEmail;

                String saveDocId = (targetDocId != null && !targetDocId.trim().isEmpty())
                        ? targetDocId.trim()
                        : cleanEmail;

                Map<String, Object> data = new HashMap<>();

                // Administrative & Profile
                String hName = hospitalNameField.getText().trim();
                data.put("hospitalName", hName);
                data.put("name", hName);
                data.put("email", cleanEmail);
                data.put("hospitalEmail", cleanEmail);
                data.put("phone", phoneField.getText().trim());
                data.put("phoneNumber", phoneField.getText().trim());
                data.put("emergencyPhone", emergencyPhoneField.getText().trim());
                data.put("hospitalCategory", categoryCombo.getValue());
                data.put("hospitalType", typeCombo.getValue());
                data.put("chiefMedicalOfficer", cmoField.getText().trim());
                data.put("emergencyContactPerson", emergencyContactField.getText().trim());
                data.put("website", websiteField.getText().trim());

                try {
                    data.put("ambulanceFleetSize", Integer.parseInt(fleetSizeField.getText().trim()));
                } catch (Exception ignored) {
                    data.put("ambulanceFleetSize", 15);
                }

                // Location & Coordinates
                String address = addressField.getText().trim();
                data.put("address", address);
                data.put("hospitalAddress", address);
                data.put("city", cityField.getText().trim());
                data.put("state", stateField.getText().trim());
                data.put("pincode", pincodeField.getText().trim());

                try {
                    data.put("latitude", Double.parseDouble(latField.getText().trim()));
                    data.put("lat", Double.parseDouble(latField.getText().trim()));
                } catch (Exception ignored) {
                    data.put("latitude", 18.5204);
                    data.put("lat", 18.5204);
                }

                try {
                    data.put("longitude", Double.parseDouble(lngField.getText().trim()));
                    data.put("lng", Double.parseDouble(lngField.getText().trim()));
                } catch (Exception ignored) {
                    data.put("longitude", 73.8567);
                    data.put("lng", 73.8567);
                }

                // Accreditations & Quality
                data.put("registrationNumber", regNoField.getText().trim());
                data.put("accreditation", accreditationCombo.getValue());
                data.put("traumaLevel", traumaLevelCombo.getValue());

                // Hospital Related Statutory & Clinical Documents
                data.put("clinicalLicenseDoc", documentFilesMap.getOrDefault("clinicalLicenseDoc", ""));
                data.put("clinicalLicenseDocNo", clinicalLicenseNoField.getText().trim());
                data.put("clinicalLicenseExpiry", clinicalLicenseExpiryField.getText().trim());

                data.put("nabhAccreditationDoc", documentFilesMap.getOrDefault("nabhAccreditationDoc", ""));
                data.put("nabhCertificateNo", nabhCertNoField.getText().trim());
                data.put("nabhValidityDate", nabhValidityField.getText().trim());

                data.put("fireSafetyNocDoc", documentFilesMap.getOrDefault("fireSafetyNocDoc", ""));
                data.put("fireSafetyNocId", fireNocIdField.getText().trim());
                data.put("fireSafetyRenewalDate", fireNocExpiryField.getText().trim());

                data.put("bioMedicalWasteDoc", documentFilesMap.getOrDefault("bioMedicalWasteDoc", ""));
                data.put("bioMedicalAuthNo", bioWasteNoField.getText().trim());
                data.put("bioMedicalValidityDate", bioWasteExpiryField.getText().trim());

                data.put("pharmacyBloodBankDoc", documentFilesMap.getOrDefault("pharmacyBloodBankDoc", ""));
                data.put("pharmacyLicenseNo", pharmacyLicenseNoField.getText().trim());
                data.put("pharmacyLicenseExpiry", pharmacyExpiryField.getText().trim());

                data.put("emergencyOpsPlanDoc", documentFilesMap.getOrDefault("emergencyOpsPlanDoc", ""));
                data.put("emergencyOpsDocVer", eopVerField.getText().trim());
                data.put("emergencyOpsReviewDate", eopReviewField.getText().trim());

                data.put("additionalDocName", addlDocNameField.getText().trim());
                data.put("additionalDocFile", documentFilesMap.getOrDefault("additionalDocFile", ""));
                data.put("additionalDocNotes", addlDocNotesField.getText().trim());

                // Summary Documents Map
                Map<String, Object> docsSummary = new HashMap<>();
                for (Map.Entry<String, String> entry : documentFilesMap.entrySet()) {
                    if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                        docsSummary.put(entry.getKey(), new File(entry.getValue()).getName());
                    }
                }
                data.put("hospitalDocuments", docsSummary);

                // Preferences
                data.put("autoEmergencyPreemption", autoEmergencyPreemptionCheck.isSelected());
                data.put("directAmbulanceCoordination", directCoordinationCheck.isSelected());
                data.put("traumaSpecialistPaging", specialistPagingCheck.isSelected());
                data.put("icuCriticalDiversion", icuDiversionCheck.isSelected());
                data.put("telemetrySyncInterval", syncIntervalCombo.getValue());
                data.put("defaultEmergencyView", defaultViewCombo.getValue());

                data.put("lastUpdated", Timestamp.now());

                // Commit to Firestore "hospital" collection
                db.collection("hospital").document(saveDocId).set(data, SetOptions.merge()).get();

                // If document ID was different from email, also write to email document for
                // seamless resolution
                if (!saveDocId.equalsIgnoreCase(cleanEmail)) {
                    db.collection("hospital").document(cleanEmail).set(data, SetOptions.merge()).get();
                }

                // Update HospitalDashboard static hospital name
                HospitalDashboard.hospitalName = hName;

                Platform.runLater(() -> {
                    saveBtn.setDisable(false);
                    saveBtn.setText("Save Hospital Details & Documents");
                    overallMsg.setText(
                            "✓ All hospital details, GPS coordinates, and compliance documents saved in 'hospital' collection!");
                    overallMsg.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: "
                            + SUCCESS_TEXT + ";");
                    updateMetricStripDisplay();
                });

            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    saveBtn.setDisable(false);
                    saveBtn.setText("Save Hospital Details & Documents");
                    overallMsg.setText("Failed to save: " + ex.getMessage());
                    overallMsg.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: "
                            + DANGER_TEXT + ";");
                });
            }
        }).start();
    }

    private void updateMetricStripDisplay() {
        if (statEntityValue != null && hospitalNameField != null) {
            statEntityValue.setText(hospitalNameField.getText().trim());
        }
        if (statHotlineValue != null && emergencyPhoneField != null) {
            statHotlineValue.setText(emergencyPhoneField.getText().trim());
        }
        if (statLocationValue != null && cityField != null && stateField != null) {
            statLocationValue.setText(cityField.getText().trim() + ", " + stateField.getText().trim());
        }
        if (statDocsValue != null) {
            int count = 0;
            for (String v : documentFilesMap.values()) {
                if (v != null && !v.trim().isEmpty())
                    count++;
            }
            statDocsValue.setText(count + " Uploaded / 7");
        }
    }

    // =========================================================================
    // HELPER UI BUILDERS FOR DOCUMENTS & CARDS
    // =========================================================================
    private VBox createDocumentRow(
            String docIcon, String docTitle,
            String noLabelStr, TextField noField,
            String dateLabelStr, TextField dateField,
            String docKey, Label statusLbl) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(16));
        card.setStyle(
                "-fx-background-color: #F8FAFC; " +
                        "-fx-border-color: #E2E8F0; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-background-radius: 10px;");

        // Header
        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Text icon = new Text(docIcon);
        icon.setStyle("-fx-font-size: 18px;");

        VBox titleBox = new VBox(2);
        Label titleLbl = new Label(docTitle);
        titleLbl.setStyle(
                FONT_FAMILY + "-fx-font-size: 13.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");
        titleBox.getChildren().addAll(titleLbl);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button browseBtn = new Button("Choose Document");
        browseBtn.setPrefHeight(34);
        browseBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: #FFFFFF; " +
                        "-fx-border-color: " + PRIMARY_TEAL + "; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-text-fill: " + PRIMARY_TEAL + "; " +
                        "-fx-font-size: 11.5px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 0 14; " +
                        "-fx-cursor: hand;");

        Button removeBtn = new Button("✕");
        removeBtn.setPrefHeight(34);
        removeBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + DANGER_BG + "; " +
                        "-fx-border-color: " + DANGER_BORDER + "; " +
                        "-fx-border-radius: 6px; " +
                        "-fx-background-radius: 6px; " +
                        "-fx-text-fill: " + DANGER_TEXT + "; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 0 10; " +
                        "-fx-cursor: hand;");

        browseBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select " + docTitle);
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Clinical & Regulatory Documents (*.pdf, *.png, *.jpg, *.docx)",
                            "*.pdf", "*.png", "*.jpg", "*.jpeg", "*.docx", "*.doc"),
                    new FileChooser.ExtensionFilter("All Files (*.*)", "*.*"));
            File chosenFile = fileChooser.showOpenDialog(null);
            if (chosenFile != null) {
                documentFilesMap.put(docKey, chosenFile.getAbsolutePath());
                statusLbl.setText("✓ Attached: " + chosenFile.getName());
                statusLbl.setStyle(FONT_FAMILY + "-fx-background-color: " + SUCCESS_BG + "; -fx-text-fill: "
                        + SUCCESS_TEXT + "; -fx-border-color: " + SUCCESS_BORDER
                        + "; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-font-size: 11px; -fx-padding: 4px 8px; -fx-font-weight: bold;");
                updateMetricStripDisplay();
            }
        });

        removeBtn.setOnAction(e -> {
            documentFilesMap.remove(docKey);
            statusLbl.setText("No document attached");
            statusLbl.setStyle(FONT_FAMILY
                    + "-fx-background-color: #F1F5F9; -fx-text-fill: #94A3B8; -fx-border-color: #CBD5E1; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-font-size: 11px; -fx-padding: 4px 8px;");
            updateMetricStripDisplay();
        });

        topRow.getChildren().addAll(icon, titleBox, spacer, statusLbl, browseBtn, removeBtn);

        // Fields Row
        GridPane rowGrid = new GridPane();
        rowGrid.setHgap(16);
        rowGrid.setVgap(10);

        VBox noBox = new VBox(4);
        Label noLbl = createFieldLabel(noLabelStr);
        noBox.getChildren().addAll(noLbl, noField);
        rowGrid.add(noBox, 0, 0);

        VBox dateBox = new VBox(4);
        Label dateLbl = createFieldLabel(dateLabelStr);
        dateBox.getChildren().addAll(dateLbl, dateField);
        rowGrid.add(dateBox, 1, 0);

        applyColumnConstraints(rowGrid);

        card.getChildren().addAll(topRow, rowGrid);
        return card;
    }

    private Label createDocStatusLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(FONT_FAMILY
                + "-fx-background-color: #F1F5F9; -fx-text-fill: #94A3B8; -fx-border-color: #CBD5E1; -fx-border-radius: 6px; -fx-background-radius: 6px; -fx-font-size: 11px; -fx-padding: 4px 8px;");
        return lbl;
    }

    private VBox createMetricCard(String iconStr, String iconColor, String iconBg,
            String labelStr, Text valueNode, String badgeStr,
            String badgeBg, String badgeColor) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setPrefHeight(120);
        card.setStyle(CARD_STYLE);
        HBox.setHgrow(card, Priority.ALWAYS);

        card.setOnMouseEntered(e -> {
            card.setTranslateY(-3);
            card.setStyle(
                    "-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-border-radius: 14px; -fx-background-radius: 14px; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.09), 18, 0, 0, 6);");
        });
        card.setOnMouseExited(e -> {
            card.setTranslateY(0);
            card.setStyle(CARD_STYLE);
        });

        HBox top = new HBox(8);
        top.setAlignment(Pos.CENTER_LEFT);

        StackPane iconHolder = new StackPane();
        iconHolder.setPrefSize(34, 34);
        iconHolder.setStyle("-fx-background-color: " + iconBg + "; -fx-background-radius: 8px;");
        Text icon = new Text(iconStr);
        icon.setStyle(FONT_FAMILY + "-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + iconColor + ";");
        iconHolder.getChildren().add(icon);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Label badge = new Label(badgeStr);
        badge.setStyle(FONT_FAMILY + "-fx-background-color: " + badgeBg + "; -fx-text-fill: " + badgeColor
                + "; -fx-font-size: 9.5px; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 6px;");
        top.getChildren().addAll(iconHolder, sp, badge);

        valueNode.setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");

        Text title = new Text(labelStr);
        title.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-font-weight: 600; -fx-fill: " + TEXT_MUTED
                + "; -fx-letter-spacing: 0.4px;");

        card.getChildren().addAll(top, valueNode, title);
        return card;
    }

    private HBox createSectionHeader(String iconStr, String iconBg, String iconColor, String title) {
        HBox box = new HBox(12);
        box.setAlignment(Pos.CENTER_LEFT);

        StackPane iconHolder = new StackPane();
        iconHolder.setPrefSize(36, 36);
        iconHolder.setStyle("-fx-background-color: " + iconBg + "; -fx-background-radius: 9px;");
        Text icon = new Text(iconStr);
        icon.setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-fill: " + iconColor + "; -fx-font-weight: bold;");
        iconHolder.getChildren().add(icon);

        VBox textBox = new VBox(2);
        Label titleLbl = new Label(title);
        titleLbl.setStyle(
                FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");
        textBox.getChildren().addAll(titleLbl);

        box.getChildren().addAll(iconHolder, textBox);
        return box;
    }

    private Label createFieldLabel(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED
                + "; -fx-letter-spacing: 0.5px;");
        return lbl;
    }

    private TextField createStyledTextField(String initialValue) {
        TextField tf = new TextField(initialValue != null ? initialValue : "");
        tf.setPrefHeight(40);
        tf.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px; -fx-padding: 8px 12px;");
        return tf;
    }

    private CheckBox createStyledCheckBox(String label, String helperDesc, boolean isSelected) {
        CheckBox cb = new CheckBox(label);
        cb.setSelected(isSelected);
        cb.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");
        return cb;
    }

    private void applyColumnConstraints(GridPane grid) {
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().setAll(col1, col2);
    }

    private String getString(DocumentSnapshot doc, String field, String fallback) {
        if (doc == null || !doc.contains(field))
            return fallback;
        Object val = doc.get(field);
        return val != null ? String.valueOf(val).trim() : fallback;
    }

    private int getInt(DocumentSnapshot doc, String field, int fallback) {
        if (doc == null || !doc.contains(field))
            return fallback;
        Object val = doc.get(field);
        if (val instanceof Number)
            return ((Number) val).intValue();
        try {
            return Integer.parseInt(String.valueOf(val));
        } catch (Exception e) {
            return fallback;
        }
    }

    private double getDouble(DocumentSnapshot doc, String field, double fallback) {
        if (doc == null || !doc.contains(field))
            return fallback;
        Object val = doc.get(field);
        if (val instanceof Number)
            return ((Number) val).doubleValue();
        try {
            return Double.parseDouble(String.valueOf(val));
        } catch (Exception e) {
            return fallback;
        }
    }

    private boolean getBoolean(DocumentSnapshot doc, String field, boolean fallback) {
        if (doc == null || !doc.contains(field))
            return fallback;
        Boolean b = doc.getBoolean(field);
        return b != null ? b : fallback;
    }
}
