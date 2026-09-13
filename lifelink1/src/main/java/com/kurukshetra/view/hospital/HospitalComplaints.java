package com.kurukshetra.view.hospital;

import com.kurukshetra.controller.admin.AdminAmbulanceAssignmentController;
import com.kurukshetra.controller.hospitalController.HospitalComplaintController;
import com.kurukshetra.model.admin.AdminAmbulanceAssignmentModel;
import com.kurukshetra.model.hospitalModel.HospitalComplaintModel;
import com.kurukshetra.view.util.ShimmerLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Hospital Complaints & Escalation Management View
 * Styled in strict alignment with HospitalDashboard.java MedTech Clinical
 * Design System.
 */
public class HospitalComplaints {

    // =========================================================================
    // DESIGN SYSTEM CONSTANTS (MATCHING HospitalDashboard.java)
    // =========================================================================
    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";
    private static final String PRIMARY_TEAL = "#006591";
    private static final String TEAL_HOVER = "#004F72";
    private static final String PAGE_BG = "#a5bdaaff";
    private static final String SURFACE = "#FFFFFF";
    private static final String BORDER_COLOR = "#E2E8F0";
    private static final String BORDER_HOVER = "#CBD5E1";
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

    private static final String INFO_BG = "#EFF6FF";
    private static final String INFO_TEXT = "#0284C7";
    private static final String INFO_BORDER = "#BAE6FD";

    private static final String CARD_STYLE = "-fx-background-color: " + SURFACE + "; " +
            "-fx-border-color: " + BORDER_COLOR + "; " +
            "-fx-border-radius: 14px; " +
            "-fx-background-radius: 14px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 14, 0, 0, 3);";

    private String hospitalEmail;
    private String hospitalName = "LifeLink Hospital";
    private final HospitalComplaintController complaintController;
    private final AdminAmbulanceAssignmentController assignmentController = new AdminAmbulanceAssignmentController();

    private final List<AdminAmbulanceAssignmentModel> ambulanceAssignments = new ArrayList<>();
    private final List<HospitalComplaintModel> cachedComplaints = new ArrayList<>();

    // Dynamic UI References
    private Text totalComplaintsVal;
    private Text pendingComplaintsVal;
    private Text highPriorityVal;
    private Text resolvedComplaintsVal;

    private ComboBox<String> ambulanceBox;
    private TextField driverField;
    private TextField nurseField;
    private ComboBox<String> complaintType;
    private ComboBox<String> priorityBox;
    private TextArea complaintDescription;
    private Button sendComplaintButton;

    private HBox alertBanner;
    private Text alertBannerIcon;
    private Text alertBannerText;

    // =========================================================
    // COLOR VARIABLES
    // =========================================================
    // Page Background: #F1F5F9
    // Primary Teal: #006591
    // Text Primary: #0F172A
    // Info BG: #EFF6FF, Info Text: #0284C7
    // Borders: #E2E8F0
    // =========================================================

    private VBox complaintsListContainer;
    private ShimmerLoader.ShimmerPane complaintsShimmer;
    private Label complaintCountBadge;
    private TextField searchFilterField;

    public HospitalComplaints() {
        this.hospitalEmail = HospitalDashboard.hospitalEmail;
        this.complaintController = new HospitalComplaintController();
    }

    public HospitalComplaints(String hospitalEmail) {
        this.hospitalEmail = hospitalEmail;
        this.complaintController = new HospitalComplaintController();
    }

    public VBox getComplaintSection() {

        fetchHospitalDetails();

        // =====================================================================
        // MAIN CONTAINER
        // =====================================================================
        VBox mainContent = new VBox(22);
        mainContent.setPadding(new Insets(24, 28, 32, 28));
        mainContent.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        // =====================================================================
        // 1. TOP HEADER (TITLE & ACTION CONTROLS)
        // =====================================================================
        HBox header = createTopHeader();

        // =====================================================================
        // 2. SUMMARY METRIC STAT CARDS ROW
        // =====================================================================
        HBox summaryMetricsRow = createSummaryMetricsRow();

        // =====================================================================
        // 3. MAIN DUAL-COLUMN CONTENT (FORM + AUDIT LOG)
        // =====================================================================
        HBox splitContent = new HBox(22);
        splitContent.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(splitContent, Priority.ALWAYS);

        VBox formCard = createComplaintFormCard();
        VBox logCard = createComplaintsAuditCard();

        splitContent.getChildren().addAll(formCard, logCard);

        mainContent.getChildren().addAll(header, summaryMetricsRow, splitContent);

        // =====================================================================
        // SMOOTH SCROLLPANE WRAPPER (MATCHING HospitalDashboard.java)
        // =====================================================================
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle(
                "-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");

        VBox finalView = new VBox(scrollPane);
        finalView.setStyle("-fx-background-color: " + PAGE_BG + ";");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Load background data
        loadAmbulanceAssignments();
        loadComplaintsHistory();

        return finalView;
    }

    // =========================================================================
    // 1. TOP HEADER BUILDER
    // =========================================================================
    private HBox createTopHeader() {
        HBox header = new HBox(14);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setMaxWidth(Double.MAX_VALUE);

        VBox headingBox = new VBox(3);
        Text pageTitle = new Text("Incident & Coordination Escalations");
        pageTitle.setStyle(FONT_FAMILY + "-fx-font-size: 26px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");

        headingBox.getChildren().addAll(pageTitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Refresh Button
        Button refreshButton = new Button("Refresh Audit Log");
        refreshButton.setPrefHeight(38);
        refreshButton.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + "; " +
                        "-fx-text-fill: " + PRIMARY_TEAL + "; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-padding: 0 14; " +
                        "-fx-cursor: hand;");
        refreshButton.setOnMouseEntered(e -> refreshButton.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: #F1F5F9; " +
                        "-fx-text-fill: " + TEAL_HOVER + "; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: " + BORDER_HOVER + "; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-padding: 0 14; " +
                        "-fx-cursor: hand;"));
        refreshButton.setOnMouseExited(e -> refreshButton.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + "; " +
                        "-fx-text-fill: " + PRIMARY_TEAL + "; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-padding: 0 14; " +
                        "-fx-cursor: hand;"));
        refreshButton.setOnAction(e -> {
            loadAmbulanceAssignments();
            loadComplaintsHistory();
        });

        HBox topControls = new HBox(10, refreshButton);
        topControls.setAlignment(Pos.CENTER_RIGHT);

        header.getChildren().addAll(headingBox, spacer, topControls);
        return header;
    }

    // =========================================================================
    // 2. SUMMARY METRIC CARDS ROW (MATCHING HospitalDashboard
    // createTrendingMetricCard)
    // =========================================================================
    private HBox createSummaryMetricsRow() {
        HBox row = new HBox(14);
        row.setMaxWidth(Double.MAX_VALUE);

        totalComplaintsVal = new Text("0");
        VBox cardTotal = createTrendingMetricCard(
                "📋", "#EFF6FF", "#0284C7",
                "Total Escalations Logged", totalComplaintsVal,
                "LIFELINK DESK", "#DBEAFE", "#1E40AF");

        pendingComplaintsVal = new Text("0");
        VBox cardPending = createTrendingMetricCard(
                "⏳", "#FFFBEB", "#D97706",
                "Awaiting Admin Action", pendingComplaintsVal,
                "PENDING REVIEW", "#FEF3C7", "#92400E");

        highPriorityVal = new Text("0");
        VBox cardHigh = createTrendingMetricCard(
                "🚨", "#FFF1F2", "#DC2626",
                "High Severity Incidents", highPriorityVal,
                "ACTION REQUIRED", "#FEE2E2", "#991B1B");

        resolvedComplaintsVal = new Text("0");
        VBox cardResolved = createTrendingMetricCard(
                "🛡️", "#ECFDF5", "#059669",
                "Resolved Inquiries", resolvedComplaintsVal,
                "CLOSED", "#D1FAE5", "#065F46");

        row.getChildren().addAll(cardTotal, cardPending, cardHigh, cardResolved);
        return row;
    }

    private VBox createTrendingMetricCard(
            String iconEmoji, String iconBgColor, String iconTextColor,
            String title, Text valueNode, String badgeText, String badgeBg, String badgeTextColor) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(16));
        card.setPrefHeight(132);
        card.setStyle(CARD_STYLE);
        HBox.setHgrow(card, Priority.ALWAYS);

        card.setOnMouseEntered(e -> {
            card.setTranslateY(-3);
            card.setStyle(
                    "-fx-background-color: #FFFFFF; " +
                            "-fx-border-color: " + BORDER_HOVER + "; " +
                            "-fx-border-radius: 14px; " +
                            "-fx-background-radius: 14px; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.08), 16, 0, 0, 5);");
        });
        card.setOnMouseExited(e -> {
            card.setTranslateY(0);
            card.setStyle(CARD_STYLE);
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
                FONT_FAMILY +
                        "-fx-background-color: " + badgeBg + "; " +
                        "-fx-text-fill: " + badgeTextColor + "; " +
                        "-fx-font-size: 9.5px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 3px 8px; " +
                        "-fx-background-radius: 6px;");
        topRow.getChildren().addAll(iconPane, spacer, badge);

        valueNode.setStyle(FONT_FAMILY + "-fx-font-size: 28px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");

        Text labelText = new Text(title);
        labelText.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: 600; -fx-fill: " + TEXT_MUTED + ";");

        card.getChildren().addAll(topRow, valueNode, labelText);
        return card;
    }

    // =========================================================================
    // 3. COMPLAINT SUBMISSION FORM CARD
    // =========================================================================
    private VBox createComplaintFormCard() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(22));
        card.setPrefWidth(490);
        card.setMinWidth(460);
        card.setStyle(CARD_STYLE);

        // Header
        HBox formHeader = new HBox(10);
        formHeader.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(36, 36);
        iconBox.setStyle("-fx-background-color: " + DANGER_BG + "; -fx-background-radius: 8px;");
        Text icon = new Text("🚨");
        icon.setStyle("-fx-font-size: 16px;");
        iconBox.getChildren().add(icon);

        VBox titleBox = new VBox(2);
        Text title = new Text("File Escalation Report");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 16.5px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        titleBox.getChildren().addAll(title);

        formHeader.getChildren().addAll(iconBox, titleBox);

        // --- Field 1: Ambulance ID ---
        Label ambulanceLabel = createFieldLabel("AMBULANCE FLEET IDENTIFIER");
        ambulanceBox = new ComboBox<>();
        ambulanceBox.setPromptText("Select ambulance unit...");
        ambulanceBox.setMaxWidth(Double.MAX_VALUE);
        ambulanceBox.setPrefHeight(40);
        styleControl(ambulanceBox);

        VBox ambulanceSection = new VBox(6, ambulanceLabel, ambulanceBox);

        // --- Field 2 & 3: Auto-linked Driver & Nurse Details ---
        Label driverLabel = createFieldLabel("ASSIGNED DRIVER");
        driverField = new TextField();
        driverField.setPromptText("Driver email (auto-linked)");
        driverField.setEditable(false);
        driverField.setPrefHeight(40);
        styleReadOnlyField(driverField);
        VBox driverSection = new VBox(6, driverLabel, driverField);
        HBox.setHgrow(driverSection, Priority.ALWAYS);

        Label nurseLabel = createFieldLabel("ASSIGNED NURSE");
        nurseField = new TextField();
        nurseField.setPromptText("Nurse email (auto-linked)");
        nurseField.setEditable(false);
        nurseField.setPrefHeight(40);
        styleReadOnlyField(nurseField);
        VBox nurseSection = new VBox(6, nurseLabel, nurseField);
        HBox.setHgrow(nurseSection, Priority.ALWAYS);

        HBox staffRow = new HBox(12, driverSection, nurseSection);

        // Connect ambulance change listener
        ambulanceBox.setOnAction(e -> {
            String selectedAmbId = ambulanceBox.getValue();
            driverField.clear();
            nurseField.clear();

            if (selectedAmbId == null || selectedAmbId.trim().isEmpty()) {
                return;
            }

            for (AdminAmbulanceAssignmentModel assignment : ambulanceAssignments) {
                if (selectedAmbId.equals(assignment.getAmbulanceId())) {
                    driverField.setText(assignment.getDriverEmail() != null ? assignment.getDriverEmail() : "");
                    nurseField.setText(assignment.getNurseEmail() != null ? assignment.getNurseEmail() : "");
                    break;
                }
            }
        });

        // --- Field 4 & 5: Complaint Type & Priority ---
        Label typeLabel = createFieldLabel("INCIDENT CATEGORY");
        complaintType = new ComboBox<>();
        complaintType.getItems().addAll(
                "Driver not coordinating properly",
                "Nurse not coordinating properly",
                "Ambulance staff communication issue",
                "Delay in emergency response",
                "Staff protocol & behaviour violation",
                "Route diversion / transit dispute",
                "Other Operational Incident");
        complaintType.setPromptText("Select category...");
        complaintType.setMaxWidth(Double.MAX_VALUE);
        complaintType.setPrefHeight(40);
        styleControl(complaintType);
        VBox typeSection = new VBox(6, typeLabel, complaintType);
        HBox.setHgrow(typeSection, Priority.ALWAYS);

        Label priorityLabel = createFieldLabel("SEVERITY LEVEL");
        priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(
                "HIGH - Urgent Admin Intervention",
                "MEDIUM - Coordination Breakdown",
                "LOW - Operational Log Note");
        priorityBox.setValue("HIGH - Urgent Admin Intervention");
        priorityBox.setMaxWidth(Double.MAX_VALUE);
        priorityBox.setPrefHeight(40);
        styleControl(priorityBox);
        VBox prioritySection = new VBox(6, priorityLabel, priorityBox);
        prioritySection.setPrefWidth(170);

        HBox categoryRow = new HBox(12, typeSection, prioritySection);

        // --- Field 6: Description ---
        Label descLabel = createFieldLabel("INCIDENT PARTICULARS & TIMELINE");
        complaintDescription = new TextArea();
        complaintDescription.setPromptText(
                "Clearly outline what occurred, patient status impacts, staff coordination failures, and immediate corrective steps needed...");
        complaintDescription.setWrapText(true);
        complaintDescription.setPrefRowCount(5);
        complaintDescription.setMaxWidth(Double.MAX_VALUE);
        complaintDescription.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: #FFFFFF; " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 12.5px; " +
                        "-fx-padding: 8px;");
        complaintDescription.focusedProperty().addListener((obs, oldV, newV) -> {
            if (newV) {
                complaintDescription.setStyle(
                        FONT_FAMILY +
                                "-fx-background-color: #FFFFFF; " +
                                "-fx-border-color: " + PRIMARY_TEAL + "; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-font-size: 12.5px; " +
                                "-fx-padding: 8px;");
            } else {
                complaintDescription.setStyle(
                        FONT_FAMILY +
                                "-fx-background-color: #FFFFFF; " +
                                "-fx-border-color: " + BORDER_COLOR + "; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-font-size: 12.5px; " +
                                "-fx-padding: 8px;");
            }
        });

        VBox descSection = new VBox(6, descLabel, complaintDescription);

        // --- Submit Button ---
        sendComplaintButton = new Button("TRANSMIT COMPLAINT TO CITY ADMIN");
        sendComplaintButton.setMaxWidth(Double.MAX_VALUE);
        sendComplaintButton.setPrefHeight(44);
        sendComplaintButton.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + PRIMARY_TEAL + "; " +
                        "-fx-text-fill: #FFFFFF; " +
                        "-fx-font-size: 12.5px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,101,145,0.25), 8, 0, 0, 2); " +
                        "-fx-cursor: hand;");
        sendComplaintButton.setOnMouseEntered(e -> {
            if (!sendComplaintButton.isDisable()) {
                sendComplaintButton.setStyle(
                        FONT_FAMILY +
                                "-fx-background-color: " + TEAL_HOVER + "; " +
                                "-fx-text-fill: #FFFFFF; " +
                                "-fx-font-size: 12.5px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-background-radius: 10px; " +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,101,145,0.35), 10, 0, 0, 3); " +
                                "-fx-cursor: hand;");
            }
        });
        sendComplaintButton.setOnMouseExited(e -> {
            if (!sendComplaintButton.isDisable()) {
                sendComplaintButton.setStyle(
                        FONT_FAMILY +
                                "-fx-background-color: " + PRIMARY_TEAL + "; " +
                                "-fx-text-fill: #FFFFFF; " +
                                "-fx-font-size: 12.5px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-background-radius: 10px; " +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,101,145,0.25), 8, 0, 0, 2); " +
                                "-fx-cursor: hand;");
            }
        });

        // --- Alert / Status Banner ---
        alertBanner = new HBox(8);
        alertBanner.setAlignment(Pos.CENTER_LEFT);
        alertBanner.setPadding(new Insets(10, 14, 10, 14));
        alertBanner.setVisible(false);
        alertBanner.setManaged(false);

        alertBannerIcon = new Text("");
        alertBannerText = new Text("");
        alertBanner.getChildren().addAll(alertBannerIcon, alertBannerText);

        // Submit Action Handler
        sendComplaintButton.setOnAction(e -> handleComplaintSubmit());

        card.getChildren().addAll(
                formHeader,
                ambulanceSection,
                staffRow,
                categoryRow,
                descSection,
                sendComplaintButton,
                alertBanner);

        return card;
    }

    // =========================================================================
    // 4. COMPLAINTS AUDIT TRAIL & HISTORY CARD
    // =========================================================================
    private VBox createComplaintsAuditCard() {
        VBox card = new VBox(14);
        card.setPadding(new Insets(22));
        card.setStyle(CARD_STYLE);
        HBox.setHgrow(card, Priority.ALWAYS);

        // Header
        HBox headerRow = new HBox(10);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(36, 36);
        iconBox.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 8px;");
        Text icon = new Text("📋");
        icon.setStyle("-fx-font-size: 16px;");
        iconBox.getChildren().add(icon);

        VBox titleBox = new VBox(2);
        Text title = new Text("Incident Audit Log & Tracking");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 16.5px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        titleBox.getChildren().addAll(title);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        complaintCountBadge = new Label("0 FILED");
        complaintCountBadge.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + INFO_BG + "; " +
                        "-fx-text-fill: " + INFO_TEXT + "; " +
                        "-fx-font-size: 10px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 4px 10px; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-border-color: " + INFO_BORDER + "; " +
                        "-fx-border-radius: 12px;");

        headerRow.getChildren().addAll(iconBox, titleBox, spacer, complaintCountBadge);

        // Search Filter Row
        HBox searchRow = new HBox(10);
        searchRow.setAlignment(Pos.CENTER_LEFT);
        searchRow.setPadding(new Insets(0, 16, 0, 16));
        searchRow.setPrefHeight(44);
        searchRow.setStyle(
                "-fx-background-color: #F8FAFC; " +
                        "-fx-border-color: #CBD5E1; " +
                        "-fx-border-radius: 20px; " +
                        "-fx-background-radius: 20px; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 4, 0, 0, 2);");

        Label searchIcon = new Label("🔍");
        searchIcon.setStyle("-fx-font-size: 14px; -fx-text-fill: " + TEXT_MUTED + ";");

        searchFilterField = new TextField();
        searchFilterField.setPromptText("Filter by ID, ambulance, driver, or keyword...");
        searchFilterField
                .setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-font-size: 13.5px; -fx-text-fill: "
                        + TEXT_PRIMARY + "; -fx-prompt-text-fill: " + TEXT_MUTED + "; -fx-padding: 0;");
        HBox.setHgrow(searchFilterField, Priority.ALWAYS);

        searchFilterField.focusedProperty().addListener((obs, oldV, isFocused) -> {
            if (isFocused) {
                searchRow.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + PRIMARY_TEAL
                        + "; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-border-width: 1.5px; -fx-effect: dropshadow(three-pass-box, rgba(0,101,145,0.1), 8, 0, 0, 2);");
            } else {
                searchRow.setStyle(
                        "-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 4, 0, 0, 2);");
            }
        });

        searchFilterField.textProperty().addListener((obs, oldV, newV) -> filterComplaintsList(newV));

        searchRow.getChildren().addAll(searchIcon, searchFilterField);

        // Complaints List Container
        complaintsListContainer = new VBox(10);
        complaintsListContainer.setStyle("-fx-background-color: transparent;");
        complaintsShimmer = ShimmerLoader.createListSkeleton(4, 480, 80);
        complaintsListContainer.getChildren().add(complaintsShimmer);

        ScrollPane listScroll = new ScrollPane(complaintsListContainer);
        listScroll.setFitToWidth(true);
        listScroll.setPrefHeight(470);
        listScroll.setStyle(
                "-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(listScroll, Priority.ALWAYS);

        card.getChildren().addAll(headerRow, searchRow, listScroll);
        return card;
    }

    // =========================================================================
    // COMPLAINT SUBMISSION LOGIC
    // =========================================================================
    private void handleComplaintSubmit() {
        String ambulanceId = ambulanceBox.getValue();
        String driver = driverField.getText();
        String nurse = nurseField.getText();
        String type = complaintType.getValue();
        String description = complaintDescription.getText().trim();

        if (ambulanceId == null || driver == null || driver.trim().isEmpty() ||
                nurse == null || nurse.trim().isEmpty() ||
                type == null || description.isEmpty()) {

            showAlertBanner(
                    "⚠️ Please complete all complaint fields before transmitting.",
                    DANGER_BG, DANGER_TEXT, DANGER_BORDER, "⚠️");
            return;
        }

        sendComplaintButton.setDisable(true);
        sendComplaintButton.setText("TRANSMITTING TO CITY ADMIN...");

        showAlertBanner(
                "Submitting incident report to Admin via Cloud Firestore...",
                INFO_BG, INFO_TEXT, INFO_BORDER, "⏳");

        new Thread(() -> {
            try {
                String targetEmail = (hospitalEmail != null && !hospitalEmail.trim().isEmpty())
                        ? hospitalEmail.trim()
                        : HospitalDashboard.hospitalEmail;

                String complaintId = complaintController.submitComplaint(
                        targetEmail,
                        hospitalName,
                        ambulanceId,
                        driver,
                        nurse,
                        type,
                        description);

                Platform.runLater(() -> {
                    showAlertBanner(
                            "✓ Incident report [" + complaintId + "] lodged successfully. City Admin has been alerted.",
                            SUCCESS_BG, SUCCESS_TEXT, SUCCESS_BORDER, "✓");

                    // Clear fields
                    complaintDescription.clear();
                    complaintType.setValue(null);
                    ambulanceBox.setValue(null);
                    driverField.clear();
                    nurseField.clear();

                    sendComplaintButton.setDisable(false);
                    sendComplaintButton.setText("TRANSMIT COMPLAINT TO CITY ADMIN");

                    // Reload log and stats
                    loadComplaintsHistory();
                });

            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    showAlertBanner(
                            "Failed to lodge escalation: " + ex.getMessage(),
                            DANGER_BG, DANGER_TEXT, DANGER_BORDER, "✕");
                    sendComplaintButton.setDisable(false);
                    sendComplaintButton.setText("TRANSMIT COMPLAINT TO CITY ADMIN");
                });
            }
        }).start();
    }

    private void showAlertBanner(String msg, String bg, String text, String border, String icon) {
        alertBanner.setStyle(
                "-fx-background-color: " + bg + "; " +
                        "-fx-border-color: " + border + "; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px;");
        alertBannerIcon.setText(icon);
        alertBannerIcon.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + text + ";");

        alertBannerText.setText(msg);
        alertBannerText.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: 600; -fx-fill: " + text + ";");

        alertBanner.setVisible(true);
        alertBanner.setManaged(true);
    }

    // =========================================================================
    // ASYNC DATA LOADERS
    // =========================================================================
    private void loadAmbulanceAssignments() {
        new Thread(() -> {
            try {
                List<AdminAmbulanceAssignmentModel> assignments = assignmentController.getAllAssignments();

                Platform.runLater(() -> {
                    ambulanceAssignments.clear();
                    ambulanceAssignments.addAll(assignments);

                    if (ambulanceBox != null) {
                        String currentVal = ambulanceBox.getValue();
                        ambulanceBox.getItems().clear();

                        for (AdminAmbulanceAssignmentModel assignment : assignments) {
                            String ambId = assignment.getAmbulanceId();
                            if (ambId != null && !ambId.trim().isEmpty() && !ambulanceBox.getItems().contains(ambId)) {
                                ambulanceBox.getItems().add(ambId);
                            }
                        }

                        if (currentVal != null && ambulanceBox.getItems().contains(currentVal)) {
                            ambulanceBox.setValue(currentVal);
                        }
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void loadComplaintsHistory() {
        new Thread(() -> {
            try {
                List<HospitalComplaintModel> allComplaints = complaintController.getAllHospitalComplaints();

                String safeHosp = (hospitalName != null) ? hospitalName.trim().toLowerCase(Locale.ROOT) : "";
                String safeEmail = (hospitalEmail != null) ? hospitalEmail.trim().toLowerCase(Locale.ROOT) : "";

                List<HospitalComplaintModel> matched = new ArrayList<>();
                for (HospitalComplaintModel c : allComplaints) {
                    if (c == null)
                        continue;
                    String cEmail = (c.getHospitalEmail() != null)
                            ? c.getHospitalEmail().trim().toLowerCase(Locale.ROOT)
                            : "";
                    String cName = (c.getHospitalName() != null) ? c.getHospitalName().trim().toLowerCase(Locale.ROOT)
                            : "";

                    if (cEmail.isEmpty() || cEmail.equals(safeEmail)
                            || (!safeHosp.isEmpty() && cName.contains(safeHosp))) {
                        matched.add(c);
                    }
                }

                // Sort newest first
                matched.sort((a, b) -> {
                    if (a.getTimestamp() == null && b.getTimestamp() == null)
                        return 0;
                    if (a.getTimestamp() == null)
                        return 1;
                    if (b.getTimestamp() == null)
                        return -1;
                    return b.getTimestamp().compareTo(a.getTimestamp());
                });

                Platform.runLater(() -> {
                    cachedComplaints.clear();
                    cachedComplaints.addAll(matched);

                    // Update Metrics
                    int total = matched.size();
                    int pending = 0;
                    int high = 0;
                    int resolved = 0;

                    for (HospitalComplaintModel c : matched) {
                        String status = (c.getStatus() != null) ? c.getStatus().toUpperCase() : "PENDING";
                        String priority = (c.getPriority() != null) ? c.getPriority().toUpperCase() : "HIGH";

                        if ("RESOLVED".equals(status)) {
                            resolved++;
                        } else {
                            pending++;
                        }

                        if ("HIGH".equals(priority)) {
                            high++;
                        }
                    }

                    if (totalComplaintsVal != null)
                        totalComplaintsVal.setText(String.valueOf(total));
                    if (pendingComplaintsVal != null)
                        pendingComplaintsVal.setText(String.valueOf(pending));
                    if (highPriorityVal != null)
                        highPriorityVal.setText(String.valueOf(high));
                    if (resolvedComplaintsVal != null)
                        resolvedComplaintsVal.setText(String.valueOf(resolved));

                    if (complaintCountBadge != null)
                        complaintCountBadge.setText(total + " FILED");

                    renderComplaintsList(matched);
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void filterComplaintsList(String filterText) {
        if (filterText == null || filterText.trim().isEmpty()) {
            renderComplaintsList(cachedComplaints);
            return;
        }

        String lower = filterText.trim().toLowerCase(Locale.ROOT);
        List<HospitalComplaintModel> filtered = cachedComplaints.stream()
                .filter(c -> {
                    String id = (c.getComplaintId() != null) ? c.getComplaintId().toLowerCase() : "";
                    String amb = (c.getAmbulanceId() != null) ? c.getAmbulanceId().toLowerCase() : "";
                    String drv = (c.getDriverEmail() != null) ? c.getDriverEmail().toLowerCase() : "";
                    String nrs = (c.getNurseEmail() != null) ? c.getNurseEmail().toLowerCase() : "";
                    String type = (c.getComplaintType() != null) ? c.getComplaintType().toLowerCase() : "";
                    String desc = (c.getDescription() != null) ? c.getDescription().toLowerCase() : "";
                    String status = (c.getStatus() != null) ? c.getStatus().toLowerCase() : "";

                    return id.contains(lower) || amb.contains(lower) || drv.contains(lower) ||
                            nrs.contains(lower) || type.contains(lower) || desc.contains(lower) ||
                            status.contains(lower);
                })
                .collect(Collectors.toList());

        renderComplaintsList(filtered);
    }

    private void renderComplaintsList(List<HospitalComplaintModel> list) {
        if (complaintsListContainer == null)
            return;
        if (complaintsShimmer != null) {
            complaintsShimmer.stop();
            complaintsShimmer = null;
        }
        complaintsListContainer.getChildren().clear();

        if (list.isEmpty()) {
            VBox emptyBox = new VBox(10);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(45, 20, 45, 20));
            emptyBox.setStyle(
                    "-fx-background-color: #F8FAFC; " +
                            "-fx-border-color: " + BORDER_COLOR + "; " +
                            "-fx-border-radius: 12px; " +
                            "-fx-background-radius: 12px;");

            Text emptyIcon = new Text("🛡️");
            emptyIcon.setStyle("-fx-font-size: 32px;");

            Text emptyTitle = new Text("No Incident Reports Logged");
            emptyTitle.setStyle(
                    FONT_FAMILY + "-fx-font-size: 14px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

            Text emptyDesc = new Text(
                    "All ambulance dispatch, driver, and nursing operations are currently in regular standing.");
            emptyDesc.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-fill: " + TEXT_MUTED + ";");

            emptyBox.getChildren().addAll(emptyIcon, emptyTitle, emptyDesc);
            complaintsListContainer.getChildren().add(emptyBox);
            return;
        }

        for (HospitalComplaintModel complaint : list) {
            VBox itemCard = createComplaintItemCard(complaint);
            complaintsListContainer.getChildren().add(itemCard);
        }
    }

    private VBox createComplaintItemCard(HospitalComplaintModel c) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(12, 14, 12, 14));
        card.setStyle(
                "-fx-background-color: #F8FAFC; " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-background-radius: 10px;");

        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: #FFFFFF; " +
                        "-fx-border-color: " + BORDER_HOVER + "; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.06), 10, 0, 0, 3);"));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: #F8FAFC; " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-background-radius: 10px;"));

        // Top Row: ID, Ambulance, Priority, Status
        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);

        String idStr = (c.getComplaintId() != null) ? c.getComplaintId() : "COMP-UNKNOWN";
        Text idText = new Text(idStr);
        idText.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        String ambStr = (c.getAmbulanceId() != null) ? "Ambulance: " + c.getAmbulanceId() : "Ambulance: Unassigned";
        Label ambBadge = new Label(ambStr);
        ambBadge.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: #EEF2FF; " +
                        "-fx-text-fill: #4F46E5; " +
                        "-fx-font-size: 9.5px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 2px 7px; " +
                        "-fx-background-radius: 6px;");

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        String priorityStr = (c.getPriority() != null) ? c.getPriority().toUpperCase() : "HIGH";
        Label priorityBadge = new Label(priorityStr);
        if ("HIGH".equals(priorityStr)) {
            priorityBadge.setStyle(FONT_FAMILY + "-fx-background-color: " + DANGER_BG + "; -fx-text-fill: "
                    + DANGER_TEXT
                    + "; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2px 6px; -fx-background-radius: 4px;");
        } else {
            priorityBadge.setStyle(FONT_FAMILY + "-fx-background-color: " + WARNING_BG + "; -fx-text-fill: "
                    + WARNING_TEXT
                    + "; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2px 6px; -fx-background-radius: 4px;");
        }

        String statusStr = (c.getStatus() != null) ? c.getStatus().toUpperCase() : "PENDING";
        Label statusBadge = new Label(statusStr);
        if ("RESOLVED".equals(statusStr)) {
            statusBadge.setStyle(FONT_FAMILY + "-fx-background-color: " + SUCCESS_BG + "; -fx-text-fill: "
                    + SUCCESS_TEXT
                    + "; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2px 7px; -fx-background-radius: 6px; -fx-border-color: "
                    + SUCCESS_BORDER + "; -fx-border-radius: 6px;");
        } else {
            statusBadge.setStyle(FONT_FAMILY + "-fx-background-color: " + WARNING_BG + "; -fx-text-fill: "
                    + WARNING_TEXT
                    + "; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2px 7px; -fx-background-radius: 6px; -fx-border-color: "
                    + WARNING_BORDER + "; -fx-border-radius: 6px;");
        }

        topRow.getChildren().addAll(idText, ambBadge, topSpacer, priorityBadge, statusBadge);

        // Middle Row: Incident Type & Staff Involved
        String typeStr = (c.getComplaintType() != null) ? c.getComplaintType() : "Operational Issue";
        Text typeText = new Text(typeStr);
        typeText.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: 700; -fx-fill: " + TEXT_PRIMARY + ";");

        HBox staffBox = new HBox(8);
        staffBox.setAlignment(Pos.CENTER_LEFT);
        String drvText = (c.getDriverEmail() != null && !c.getDriverEmail().isEmpty()) ? "Driver: " + c.getDriverEmail()
                : "Driver: Unknown";
        String nrsText = (c.getNurseEmail() != null && !c.getNurseEmail().isEmpty()) ? "Nurse: " + c.getNurseEmail()
                : "Nurse: Unknown";

        Label drvTag = new Label(drvText);
        drvTag.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-text-fill: " + TEXT_MUTED + ";");

        Text dot = new Text("•");
        dot.setStyle("-fx-font-size: 10px; -fx-fill: #CBD5E1;");

        Label nrsTag = new Label(nrsText);
        nrsTag.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-text-fill: " + TEXT_MUTED + ";");

        staffBox.getChildren().addAll(drvTag, dot, nrsTag);

        // Description Box
        String descStr = (c.getDescription() != null && !c.getDescription().isEmpty()) ? c.getDescription()
                : "No detailed explanation recorded.";
        Text descText = new Text(descStr);
        descText.setWrappingWidth(420);
        descText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-fill: " + TEXT_SECONDARY + ";");

        // Bottom Row: Timestamp
        String dateStr = "Recently Logged";
        if (c.getTimestamp() != null) {
            try {
                Date d = c.getTimestamp().toDate();
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.ENGLISH);
                dateStr = sdf.format(d);
            } catch (Exception ignored) {
            }
        }
        Text timeText = new Text("Logged on " + dateStr + " • Verified by LifeLink Desk");
        timeText.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-fill: " + TEXT_MUTED + ";");

        card.getChildren().addAll(topRow, typeText, staffBox, descText, timeText);
        return card;
    }

    // =========================================================================
    // STYLING HELPERS
    // =========================================================================
    private Label createFieldLabel(String text) {
        Label label = new Label(text);
        label.setStyle(
                FONT_FAMILY +
                        "-fx-font-size: 10.5px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: " + TEXT_MUTED + ";");
        return label;
    }

    private void styleControl(ComboBox<String> combo) {
        combo.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: #FFFFFF; " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 12.5px;");
        combo.focusedProperty().addListener((obs, oldV, newV) -> {
            if (newV) {
                combo.setStyle(
                        FONT_FAMILY +
                                "-fx-background-color: #FFFFFF; " +
                                "-fx-border-color: " + PRIMARY_TEAL + "; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-font-size: 12.5px;");
            } else {
                combo.setStyle(
                        FONT_FAMILY +
                                "-fx-background-color: #FFFFFF; " +
                                "-fx-border-color: " + BORDER_COLOR + "; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-font-size: 12.5px;");
            }
        });
    }

    private void styleReadOnlyField(TextField field) {
        field.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: #F8FAFC; " +
                        "-fx-border-color: " + BORDER_COLOR + "; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 12px; " +
                        "-fx-text-fill: " + TEXT_SECONDARY + "; " +
                        "-fx-padding: 0 10;");
    }

    // =========================================================================
    // FETCH HOSPITAL DETAILS
    // =========================================================================
    private void fetchHospitalDetails() {
        new Thread(() -> {
            try {
                String targetEmail = (hospitalEmail != null && !hospitalEmail.trim().isEmpty())
                        ? hospitalEmail.trim()
                        : HospitalDashboard.hospitalEmail;

                if (targetEmail != null && !targetEmail.trim().isEmpty()) {
                    String fetchedHospitalName = complaintController.getHospitalName(targetEmail);
                    if (fetchedHospitalName != null && !fetchedHospitalName.isEmpty()) {
                        hospitalName = fetchedHospitalName;
                    }
                }
            } catch (Exception ignored) {
            }
        }).start();
    }
}