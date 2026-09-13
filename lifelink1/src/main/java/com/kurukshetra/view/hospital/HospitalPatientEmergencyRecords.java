package com.kurukshetra.view.hospital;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.view.util.ShimmerLoader;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.ListenerRegistration;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HospitalPatientEmergencyRecords {

    // =========================================================================
    // DESIGN SYSTEM CONSTANTS (MATCHING HospitalDashboard.java)
    // =========================================================================
    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";
    private static final String PRIMARY_TEAL = "#006591";
    private static final String TEAL_HOVER = "#004F72";
    private static final String PAGE_BG = "#a5bdaaff";
    private static final String PAGE_BG_green = "#a5bdaaff";
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

    private static final String INFO_BG = "#E0F2FE";
    private static final String INFO_TEXT = "#0369A1";
    private static final String INFO_BORDER = "#BAE6FD";

    private static final String INDIGO_BG = "#EEF2FF";
    private static final String INDIGO_TEXT = "#4F46E5";
    private static final String INDIGO_BORDER = "#C7D2FE";

    private static final String CARD_STYLE =
            "-fx-background-color: " + SURFACE + "; " +
            "-fx-border-color: " + BORDER_COLOR + "; " +
            "-fx-border-radius: 14px; " +
            "-fx-background-radius: 14px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 14, 0, 0, 3);";

    // Table references
    private VBox ambulancePatientTable;
    private VBox familyPatientTable;
    private ShimmerLoader.ShimmerPane ambulanceShimmer;
    private ShimmerLoader.ShimmerPane familyShimmer;
    private Text ambulanceShowing;
    private Text familyShowing;
    private StackPane rootStackPane;

    // Stat metric values
    private Text statAmbCount = new Text("0");
    private Text statFamCount = new Text("0");
    private Text statOcrCount = new Text("0");

    private ListenerRegistration notifyListener;
    private ListenerRegistration familyRepoListener;

    private final List<Map<String, Object>> mergedAmbulancePatients = new ArrayList<>();
    private final List<DocumentSnapshot> familyPatientRecordsList = new ArrayList<>();

    // Static hospital name filter updated as requested
    private static final String STATIC_HOSPITAL_NAME = "KEM Hospital Pune";

    private String hospitalEmail = "hospital1@lifelink.com";
    private String dynamicHospitalName = "KEM Hospital Pune";
    private Label facText;

    public HospitalPatientEmergencyRecords(String hospitalEmail) {
        if (hospitalEmail != null && !hospitalEmail.trim().isEmpty()) {
            this.hospitalEmail = hospitalEmail.trim();
        } else if (HospitalDashboard.hospitalEmail != null && !HospitalDashboard.hospitalEmail.trim().isEmpty()) {
            this.hospitalEmail = HospitalDashboard.hospitalEmail.trim();
        }
        fetchHospitalDetails();
    }

    public HospitalPatientEmergencyRecords() {
        this(HospitalDashboard.hospitalEmail);
    }

    private void fetchHospitalDetails() {
        if (hospitalEmail == null || hospitalEmail.trim().isEmpty()) return;
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return;
            String cleanEmail = hospitalEmail.trim();

            DocumentSnapshot doc = db.collection("hospital").document(cleanEmail).get().get();
            if (doc != null && doc.exists()) {
                String name = doc.getString("hospitalName");
                if (name == null || name.trim().isEmpty()) name = doc.getString("name");
                if (name != null && !name.trim().isEmpty()) {
                    this.dynamicHospitalName = name.trim();
                    return;
                }
            }

            QuerySnapshot qSnap = db.collection("hospital").whereEqualTo("email", cleanEmail).get().get();
            if (qSnap != null && !qSnap.isEmpty()) {
                DocumentSnapshot d = qSnap.getDocuments().get(0);
                String name = d.getString("hospitalName");
                if (name == null || name.trim().isEmpty()) name = d.getString("name");
                if (name != null && !name.trim().isEmpty()) {
                    this.dynamicHospitalName = name.trim();
                    return;
                }
            }

            QuerySnapshot qSnap2 = db.collection("hospital").whereEqualTo("hospitalEmail", cleanEmail).get().get();
            if (qSnap2 != null && !qSnap2.isEmpty()) {
                DocumentSnapshot d = qSnap2.getDocuments().get(0);
                String name = d.getString("hospitalName");
                if (name == null || name.trim().isEmpty()) name = d.getString("name");
                if (name != null && !name.trim().isEmpty()) {
                    this.dynamicHospitalName = name.trim();
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("[HospitalPatientEmergencyRecords] Error fetching hospital name: " + e.getMessage());
        }

        if (HospitalDashboard.hospitalName != null && !HospitalDashboard.hospitalName.isEmpty()) {
            this.dynamicHospitalName = HospitalDashboard.hospitalName;
        }
    }

    public StackPane getPatientEmergencyRecords() {

        rootStackPane = new StackPane();
        rootStackPane.setStyle("-fx-background-color: " + PAGE_BG_green + "; " + FONT_FAMILY);

        VBox mainBox = new VBox();
        mainBox.setStyle("-fx-background-color: " + PAGE_BG_green + ";");

        // =====================================================================
        // 1. TOP BAR (CLINICAL WORKSPACE HEADER)
        // =====================================================================
        HBox topBar = new HBox(16);
        topBar.setPadding(new Insets(14, 28, 14, 28));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: " + PAGE_BG_green + "; -fx-border-color: transparent; -fx-border-width: 0px;");

        VBox headerTitleBox = new VBox(4);
        HBox.setHgrow(headerTitleBox, Priority.ALWAYS);
        Text pageTitle = new Text("Emergency Inflow & Clinical Registry");
        pageTitle.setStyle(FONT_FAMILY + "-fx-font-size: 26px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");
        headerTitleBox.getChildren().add(pageTitle);

        // Modern search input aligned to the right
        HBox searchContainer = new HBox(10);
        searchContainer.setAlignment(Pos.CENTER_LEFT);
        searchContainer.setPadding(new Insets(0, 16, 0, 16));
        searchContainer.setPrefHeight(42);
        searchContainer.setPrefWidth(440);
        searchContainer.setMaxWidth(440);
        searchContainer.setStyle(
                "-fx-background-color: rgba(248,250,252,0.95); " +
                "-fx-background-radius: 18px; " +
                "-fx-border-color: transparent; " +
                "-fx-border-width: 0; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.08), 10, 0, 0, 2);"
        );

        Label searchIcon = new Label("🔍");
        searchIcon.setStyle("-fx-font-size: 14px; -fx-text-fill: " + TEXT_MUTED + ";");

        TextField searchField = new TextField();
        searchField.setPromptText("Search incoming ambulance cases, trip ID, or family submissions...");
        searchField.setStyle(
                FONT_FAMILY +
                "-fx-background-color: transparent; " +
                "-fx-background-insets: 0; " +
                "-fx-border-color: transparent; " +
                "-fx-border-width: 0; " +
                "-fx-font-size: 13.5px; " +
                "-fx-text-fill: " + TEXT_PRIMARY + "; " +
                "-fx-prompt-text-fill: " + TEXT_MUTED + "; " +
                "-fx-padding: 0;"
        );
        HBox.setHgrow(searchField, Priority.ALWAYS);

        searchField.focusedProperty().addListener((obs, oldV, isFocused) -> {
            if (isFocused) {
                searchContainer.setStyle(
                        "-fx-background-color: #FFFFFF; " +
                        "-fx-background-radius: 18px; " +
                        "-fx-border-color: transparent; " +
                        "-fx-border-width: 0; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,101,145,0.12), 12, 0, 0, 3);"
                );
            } else {
                searchContainer.setStyle(
                        "-fx-background-color: rgba(248,250,252,0.95); " +
                        "-fx-background-radius: 18px; " +
                        "-fx-border-color: transparent; " +
                        "-fx-border-width: 0; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.08), 10, 0, 0, 2);"
                );
            }
        });

        searchContainer.getChildren().addAll(searchIcon, searchField);
        topBar.getChildren().addAll(headerTitleBox, searchContainer);

        // =====================================================================
        // 2. MAIN SCROLLABLE WORKSPACE
        // =====================================================================
        VBox content = new VBox(28);
        content.setPadding(new Insets(26, 32, 36, 32));
        content.setStyle("-fx-background-color: " + PAGE_BG_green + ";");

        // =====================================================================
        // SECTION 1: AMBULANCE INCOMING PATIENT RECORDS
        // =====================================================================
        VBox ambulanceSection = new VBox(14);

        HBox ambHeaderRow = new HBox(12);
        ambHeaderRow.setAlignment(Pos.CENTER_LEFT);

        StackPane ambIconHolder = new StackPane();
        ambIconHolder.setPrefSize(34, 34);
        ambIconHolder.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 9px; -fx-border-color: " + INFO_BORDER + "; -fx-border-radius: 9px;");
        Label ambBadgeIcon = new Label("🚑");
        ambBadgeIcon.setStyle("-fx-font-size: 16px;");
        ambIconHolder.getChildren().add(ambBadgeIcon);

        VBox ambTitleBox = new VBox(2);
        Text ambSectionTitle = new Text("Ambulance Emergency Inflow");
        ambSectionTitle.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        ambTitleBox.getChildren().addAll(ambSectionTitle);

        Region ambSpacer = new Region();
        HBox.setHgrow(ambSpacer, Priority.ALWAYS);

        ambulanceShowing = new Text("");
        ambulanceShowing.setStyle(FONT_FAMILY + "-fx-fill: " + TEXT_MUTED + "; -fx-font-size: 13px;");
        ambHeaderRow.getChildren().addAll(ambIconHolder, ambTitleBox, ambSpacer, ambulanceShowing);

        // Ambulance Table Container Card
        ambulancePatientTable = new VBox();
        ambulancePatientTable.setStyle(CARD_STYLE);

        HBox ambTableHeader = new HBox(12);
        ambTableHeader.setPadding(new Insets(14, 20, 14, 20));
        ambTableHeader.setAlignment(Pos.CENTER_LEFT);
        ambTableHeader.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0px 0px 1px 0px; -fx-border-radius: 14px 14px 0px 0px; -fx-background-radius: 14px 14px 0px 0px;");

        Label ambIdH = createTableHeaderLabel("PATIENT ID", 170);
        Label ambTripH = createTableHeaderLabel("AMBULANCE / TRIP", 170);
        Label ambOcrH = createTableHeaderLabel("CLINICAL OCR REPORT", 190);
        Label ambTimeH = createTableHeaderLabel("DISPATCH TIME", 180);
        Label ambActH = createTableHeaderLabel("ACTIONS", 200);
        ambActH.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(ambActH, Priority.ALWAYS);

        ambTableHeader.getChildren().addAll(ambIdH, ambTripH, ambOcrH, ambTimeH, ambActH);
        ambulancePatientTable.getChildren().add(ambTableHeader);

        ambulanceShimmer = ShimmerLoader.createTableSkeleton(4, 5, 960, 210);
        ambulancePatientTable.getChildren().add(ambulanceShimmer);

        ambulanceSection.getChildren().addAll(ambHeaderRow, ambulancePatientTable);

        // =====================================================================
        // SECTION 2: FAMILY USER PATIENT RECORDS
        // =====================================================================
        VBox familySection = new VBox(14);

        HBox famHeaderRow = new HBox(12);
        famHeaderRow.setAlignment(Pos.CENTER_LEFT);

        StackPane famIconHolder = new StackPane();
        famIconHolder.setPrefSize(34, 34);
        famIconHolder.setStyle("-fx-background-color: " + INDIGO_BG + "; -fx-background-radius: 9px; -fx-border-color: " + INDIGO_BORDER + "; -fx-border-radius: 9px;");
        Label famBadgeIcon = new Label("👨‍👩‍👧‍👦");
        famBadgeIcon.setStyle("-fx-font-size: 16px;");
        famIconHolder.getChildren().add(famBadgeIcon);

        VBox famTitleBox = new VBox(2);
        Text famSectionTitle = new Text("Family Repository Submissions");
        famSectionTitle.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        famTitleBox.getChildren().addAll(famSectionTitle);

        Region famSpacer = new Region();
        HBox.setHgrow(famSpacer, Priority.ALWAYS);

        familyShowing = new Text("");
        familyShowing.setStyle(FONT_FAMILY + "-fx-fill: " + TEXT_MUTED + "; -fx-font-size: 13px;");
        famHeaderRow.getChildren().addAll(famIconHolder, famTitleBox, famSpacer, familyShowing);

        // Family Table Container Card
        familyPatientTable = new VBox();
        familyPatientTable.setStyle(CARD_STYLE);

        HBox famTableHeader = new HBox(12);
        famTableHeader.setPadding(new Insets(14, 20, 14, 20));
        famTableHeader.setAlignment(Pos.CENTER_LEFT);
        famTableHeader.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0px 0px 1px 0px; -fx-border-radius: 14px 14px 0px 0px; -fx-background-radius: 14px 14px 0px 0px;");

        Label famIdH = createTableHeaderLabel("PATIENT / REF ID", 170);
        Label famNameH = createTableHeaderLabel("PATIENT NAME", 190);
        Label famOcrH = createTableHeaderLabel("OCR SUMMARY", 190);
        Label famTimeH = createTableHeaderLabel("SUBMISSION TIME", 180);
        Label famActH = createTableHeaderLabel("ACTIONS", 230);
        famActH.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(famActH, Priority.ALWAYS);

        famTableHeader.getChildren().addAll(famIdH, famNameH, famOcrH, famTimeH, famActH);
        familyPatientTable.getChildren().add(famTableHeader);

        familyShimmer = ShimmerLoader.createTableSkeleton(4, 5, 960, 210);
        familyPatientTable.getChildren().add(familyShimmer);

        familySection.getChildren().addAll(famHeaderRow, familyPatientTable);

        // Assemble Content
        content.getChildren().addAll(ambulanceSection, familySection);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: " + PAGE_BG + "; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        mainBox.getChildren().addAll(topBar, scrollPane);
        rootStackPane.getChildren().add(mainBox);

        // Real-time Search Listener
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filterAndRenderAmbulanceTable(newVal);
            filterAndRenderFamilyTable(newVal);
        });

        // Initialize Firestore Real-time streams
        startAmbulancePatientStream();
        startFamilyToHospitalListener();

        return rootStackPane;
    }

    private Label createTableHeaderLabel(String text, double width) {
        Label lbl = new Label(text);
        lbl.setPrefWidth(width);
        lbl.setStyle(FONT_FAMILY +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: " + TEXT_MUTED + "; " +
                "-fx-letter-spacing: 0.6px; " +
                "-fx-background-color: #F8FAFC; " +
                "-fx-border-color: transparent; " +
                "-fx-border-radius: 14px; " +
                "-fx-background-radius: 14px; " +
                "-fx-padding: 2 0;");
        return lbl;
    }

    // =========================================================================
    // TRENDING METRIC CARD HELPER (MATCHING HospitalDashboard.java)
    // =========================================================================
    private VBox createMetricCard(String iconEmoji, String iconTextColor, String iconBgColor,
                                  String title, Text valueNode, String badgeText,
                                  String badgeBg, String badgeTextColor) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setPrefHeight(125);
        card.setStyle(CARD_STYLE);
        HBox.setHgrow(card, Priority.ALWAYS);

        card.setOnMouseEntered(e -> {
            card.setTranslateY(-3);
            card.setStyle(
                "-fx-background-color: #FFFFFF; " +
                "-fx-border-color: #CBD5E1; " +
                "-fx-border-radius: 14px; " +
                "-fx-background-radius: 14px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.09), 18, 0, 0, 6);"
            );
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
        iconPane.setStyle("-fx-background-color: " + iconBgColor + "; -fx-background-radius: 8px;");
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
            "-fx-background-radius: 6px;"
        );
        topRow.getChildren().addAll(iconPane, spacer, badge);

        valueNode.setStyle(FONT_FAMILY + "-fx-font-size: 26px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");

        Text labelText = new Text(title);
        labelText.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: 600; -fx-fill: " + TEXT_MUTED + "; -fx-letter-spacing: 0.4px;");

        card.getChildren().addAll(topRow, valueNode, labelText);
        return card;
    }

    // =========================================================================
    // STREAM 1: AMBULANCE PATIENTS (driverToHospitalNotify -> KEM Hospital Pune)
    // =========================================================================
    private void startAmbulancePatientStream() {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return;

            if (notifyListener != null) notifyListener.remove();

            notifyListener = db.collection("driverToHospitalNotify")
                    .addSnapshotListener((snapshots, error) -> {
                        if (error != null || snapshots == null) return;

                        new Thread(() -> {
                            List<Map<String, Object>> mergedList = new ArrayList<>();
                            int ocrAvailableCount = 0;

                            for (DocumentSnapshot notifyDoc : snapshots.getDocuments()) {
                                String docHospEmail = notifyDoc.getString("hospitalEmail");
                                if (docHospEmail == null) docHospEmail = notifyDoc.getString("hospitalemail");
                                if (docHospEmail == null) docHospEmail = notifyDoc.getString("hospitalId");

                                String hospitalName = notifyDoc.getString("hospitalName");
                                if (hospitalName == null) hospitalName = notifyDoc.getString("hospitalAddress");

                                // Strictly filter by hospital email (with dynamic hospital name fallback)
                                boolean emailMatch = (docHospEmail != null && hospitalEmail != null && docHospEmail.trim().equalsIgnoreCase(hospitalEmail.trim()));
                                boolean nameMatch = (dynamicHospitalName != null && hospitalName != null && hospitalName.trim().equalsIgnoreCase(dynamicHospitalName.trim()));

                                if (!emailMatch && !nameMatch) {
                                    continue;
                                }

                                String patientId = notifyDoc.getString("patientID");
                                if (patientId == null || patientId.isEmpty()) patientId = notifyDoc.getString("patientId");
                                if (patientId == null || patientId.isEmpty()) continue;

                                String tripId = notifyDoc.getString("tripID");
                                if (tripId == null) tripId = notifyDoc.getString("tripId");
                                if (tripId == null) tripId = "TRIP-AUTO";

                                String aiSummary = "No OCR summary available.";
                                String photoPayload = "";
                                try {
                                    DocumentSnapshot nurseDoc = db.collection("nurseToHospitalReport").document(patientId).get().get();
                                    if (nurseDoc.exists()) {
                                        if (nurseDoc.getString("aiSummaryReport") != null) aiSummary = nurseDoc.getString("aiSummaryReport");
                                        else if (nurseDoc.getString("patReport") != null) aiSummary = nurseDoc.getString("patReport");
                                        if (nurseDoc.getString("photoPayload") != null) photoPayload = nurseDoc.getString("photoPayload");
                                        else if (nurseDoc.getString("patphoto") != null) photoPayload = nurseDoc.getString("patphoto");
                                    } else {
                                        if (notifyDoc.getString("patReport") != null) aiSummary = notifyDoc.getString("patReport");
                                        if (notifyDoc.getString("patphoto") != null) photoPayload = notifyDoc.getString("patphoto");
                                    }
                                } catch (Exception ignored) {}

                                if (aiSummary != null && !aiSummary.contains("No OCR summary")) {
                                    ocrAvailableCount++;
                                }

                                Timestamp ts = notifyDoc.getTimestamp("timestamp");
                                if (ts == null) ts = Timestamp.now();

                                Map<String, Object> record = new HashMap<>();
                                record.put("patientId", patientId);
                                record.put("tripId", tripId);
                                record.put("aiSummary", aiSummary);
                                record.put("photoPayload", photoPayload);
                                record.put("timestamp", ts);

                                mergedList.add(record);
                            }

                            mergedList.sort((a, b) -> ((Timestamp) b.get("timestamp")).compareTo((Timestamp) a.get("timestamp")));

                            final int finalOcr = ocrAvailableCount;
                            Platform.runLater(() -> {
                                mergedAmbulancePatients.clear();
                                mergedAmbulancePatients.addAll(mergedList);
                                statAmbCount.setText(String.valueOf(mergedList.size()));
                                statOcrCount.setText(String.valueOf(finalOcr));
                                filterAndRenderAmbulanceTable("");
                            });
                        }).start();
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterAndRenderAmbulanceTable(String searchFilter) {
        if (ambulancePatientTable == null || ambulancePatientTable.getChildren().isEmpty()) return;

        if (ambulanceShimmer != null) {
            ambulanceShimmer.stop();
            ambulanceShimmer = null;
        }

        var header = ambulancePatientTable.getChildren().get(0);
        ambulancePatientTable.getChildren().clear();
        ambulancePatientTable.getChildren().add(header);

        String query = (searchFilter == null) ? "" : searchFilter.trim().toLowerCase();
        int matchCount = 0;
        boolean whiteBg = true;

        for (Map<String, Object> record : mergedAmbulancePatients) {
            String patientId = (String) record.get("patientId");
            String tripId = (String) record.get("tripId");
            String summary = (String) record.get("aiSummary");
            String photoPayload = (String) record.get("photoPayload");

            Timestamp ts = (Timestamp) record.get("timestamp");
            String timeFormatted = ts != null ? new SimpleDateFormat("dd MMM yyyy, hh:mm a").format(ts.toDate()) : "Recent";

            boolean matches = query.isEmpty()
                    || patientId.toLowerCase().contains(query)
                    || tripId.toLowerCase().contains(query)
                    || summary.toLowerCase().contains(query);

            if (matches) {
                HBox row = createAmbulancePatientRow(patientId, tripId, summary, timeFormatted, photoPayload, whiteBg);
                ambulancePatientTable.getChildren().add(row);
                whiteBg = !whiteBg;
                matchCount++;
            }
        }

        if (matchCount == 0) {
            HBox emptyBox = new HBox();
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(32));
            String filterTarget = (dynamicHospitalName != null && !dynamicHospitalName.isEmpty()) ? dynamicHospitalName : hospitalEmail;
            Label emptyLbl = new Label("No incoming ambulance records matching filter for " + filterTarget);
            emptyLbl.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-text-fill: " + TEXT_MUTED + ";");
            emptyBox.getChildren().add(emptyLbl);
            ambulancePatientTable.getChildren().add(emptyBox);
        }
    }

    private HBox createAmbulancePatientRow(String patientId, String tripId, String summary, String visit, String reportUrl, boolean whiteBg) {
        HBox row = new HBox(12);
        row.setPadding(new Insets(14, 20, 14, 20));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle(whiteBg
                ? "-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;"
                : "-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;");

        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: #F1F5F9; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;"));
        row.setOnMouseExited(e -> row.setStyle(whiteBg
                ? "-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;"
                : "-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;"));

        // 1. Patient ID Chip
        HBox idBox = new HBox(6);
        idBox.setAlignment(Pos.CENTER_LEFT);
        idBox.setPrefWidth(170);
        Label idBadge = new Label(patientId);
        idBadge.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_TEAL + "; -fx-background-color: " + INFO_BG + "; -fx-padding: 4 8; -fx-background-radius: 6px; -fx-border-radius: 6px;");
        idBox.getChildren().add(idBadge);

        // 2. Ambulance Trip ID
        HBox tripBox = new HBox(6);
        tripBox.setAlignment(Pos.CENTER_LEFT);
        tripBox.setPrefWidth(170);
        Label ambIcon = new Label("🚑");
        ambIcon.setStyle("-fx-font-size: 13px;");
        Text tripText = new Text(tripId);
        tripText.setStyle(FONT_FAMILY + "-fx-fill: " + TEXT_PRIMARY + "; -fx-font-size: 12.5px; -fx-font-weight: 600;");
        tripBox.getChildren().addAll(ambIcon, tripText);

        // 3. Clinical OCR Report Button
        HBox ocrBox = new HBox();
        ocrBox.setAlignment(Pos.CENTER_LEFT);
        ocrBox.setPrefWidth(190);
        Button ocrReportButton = new Button("View OCR Report");
        ocrReportButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + INFO_BG + "; " +
                "-fx-text-fill: " + INFO_TEXT + "; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-color: " + INFO_BORDER + "; " +
                "-fx-border-radius: 8px; " +
                "-fx-padding: 6 12; " +
                "-fx-cursor: hand;"
        );
        ocrReportButton.setOnMouseEntered(e -> ocrReportButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #BAE6FD; " +
                "-fx-text-fill: " + INFO_TEXT + "; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-color: #7DD3FC; " +
                "-fx-border-radius: 8px; " +
                "-fx-padding: 6 12; " +
                "-fx-cursor: hand;"
        ));
        ocrReportButton.setOnMouseExited(e -> ocrReportButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + INFO_BG + "; " +
                "-fx-text-fill: " + INFO_TEXT + "; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-color: " + INFO_BORDER + "; " +
                "-fx-border-radius: 8px; " +
                "-fx-padding: 6 12; " +
                "-fx-cursor: hand;"
        ));
        ocrReportButton.setOnAction(e -> showOcrReportModal(patientId, tripId, summary));
        ocrBox.getChildren().add(ocrReportButton);

        // 4. Timestamp
        HBox timeBox = new HBox(6);
        timeBox.setAlignment(Pos.CENTER_LEFT);
        timeBox.setPrefWidth(180);
        Text timeText = new Text(visit);
        timeText.setStyle(FONT_FAMILY + "-fx-fill: " + TEXT_MUTED + "; -fx-font-size: 12px;");
        timeBox.getChildren().add(timeText);

        // 5. Actions: Photos Button
        HBox actions = new HBox(8);
        actions.setPrefWidth(200);
        actions.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(actions, Priority.ALWAYS);

        Button photosBtn = new Button("Attached Photos");
        photosBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_TEAL + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-padding: 6 14; " +
                "-fx-cursor: hand;"
        );
        photosBtn.setOnMouseEntered(e -> {
            photosBtn.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + TEAL_HOVER + "; " +
                    "-fx-text-fill: #FFFFFF; " +
                    "-fx-font-size: 11.5px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8px; " +
                    "-fx-padding: 6 14; " +
                    "-fx-cursor: hand;"
            );
            photosBtn.setTranslateY(-1);
        });
        photosBtn.setOnMouseExited(e -> {
            photosBtn.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + PRIMARY_TEAL + "; " +
                    "-fx-text-fill: #FFFFFF; " +
                    "-fx-font-size: 11.5px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8px; " +
                    "-fx-padding: 6 14; " +
                    "-fx-cursor: hand;"
            );
            photosBtn.setTranslateY(0);
        });
        photosBtn.setOnAction(e -> showPhotoGalleryDialog(patientId, tripId, reportUrl));

        actions.getChildren().add(photosBtn);

        row.getChildren().addAll(idBox, tripBox, ocrBox, timeBox, actions);
        return row;
    }

    // =========================================================================
    // STREAM 2: FAMILY USER PATIENTS (familyTohospitalRepo)
    // =========================================================================
    private void startFamilyToHospitalListener() {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            if (db == null) return;

            if (familyRepoListener != null) familyRepoListener.remove();

            familyRepoListener = db.collection("familyTohospitalRepo")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .addSnapshotListener((snapshots, error) -> {
                        if (error != null || snapshots == null) return;

                        familyPatientRecordsList.clear();
                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
                            String docEmail = doc.getString("hospitalEmail");
                            if (docEmail == null) docEmail = doc.getString("hospitalemail");
                            if (docEmail == null) docEmail = doc.getString("email");

                            String docHospName = doc.getString("hospitalName");

                            // Filter by hospital email (with dynamic hospital name fallback)
                            boolean emailMatches = (docEmail != null && hospitalEmail != null && docEmail.trim().equalsIgnoreCase(hospitalEmail.trim()));
                            boolean nameMatches = (dynamicHospitalName != null && docHospName != null && docHospName.trim().equalsIgnoreCase(dynamicHospitalName.trim()));

                            if (emailMatches || nameMatches) {
                                familyPatientRecordsList.add(doc);
                            }
                        }

                        Platform.runLater(() -> {
                            statFamCount.setText(String.valueOf(familyPatientRecordsList.size()));
                            filterAndRenderFamilyTable("");
                        });
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterAndRenderFamilyTable(String searchFilter) {
        if (familyPatientTable == null || familyPatientTable.getChildren().isEmpty()) return;

        if (familyShimmer != null) {
            familyShimmer.stop();
            familyShimmer = null;
        }

        var header = familyPatientTable.getChildren().get(0);
        familyPatientTable.getChildren().clear();
        familyPatientTable.getChildren().add(header);

        String query = (searchFilter == null) ? "" : searchFilter.trim().toLowerCase();
        int matchCount = 0;
        boolean whiteBg = true;

        for (DocumentSnapshot doc : familyPatientRecordsList) {
            String reportId = doc.getString("reportId");
            if (reportId == null || reportId.isEmpty()) reportId = doc.getId();

            String memberName = doc.getString("patientMemberName");
            if (memberName == null || memberName.isEmpty()) memberName = "Patient";

            String summary = doc.getString("reportSummary");
            if (summary == null || summary.isEmpty()) summary = "No OCR summary available";

            String reportUrl = doc.getString("reportUrl");

            Timestamp ts = doc.getTimestamp("timestamp");
            String timeFormatted = ts != null ? new SimpleDateFormat("dd MMM yyyy, hh:mm a").format(ts.toDate()) : "Recent";

            boolean matches = query.isEmpty()
                    || reportId.toLowerCase().contains(query)
                    || memberName.toLowerCase().contains(query)
                    || summary.toLowerCase().contains(query);

            if (matches) {
                HBox row = createFamilyPatientRow("#" + reportId, memberName, summary, timeFormatted, reportUrl, whiteBg);
                familyPatientTable.getChildren().add(row);
                whiteBg = !whiteBg;
                matchCount++;
            }
        }

        if (matchCount == 0) {
            HBox emptyBox = new HBox();
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(32));
            Label emptyLbl = new Label("No family patient records matching the search query.");
            emptyLbl.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-text-fill: " + TEXT_MUTED + ";");
            emptyBox.getChildren().add(emptyLbl);
            familyPatientTable.getChildren().add(emptyBox);
        }
  }

    private HBox createFamilyPatientRow(String id, String name, String summary, String visit, String reportUrl, boolean whiteBg) {
        HBox row = new HBox(12);
        row.setPadding(new Insets(14, 20, 14, 20));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle(whiteBg
                ? "-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;"
                : "-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;");

        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: #F1F5F9; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;"));
        row.setOnMouseExited(e -> row.setStyle(whiteBg
                ? "-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;"
                : "-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;"));

        // 1. Ref ID
        HBox idBox = new HBox(6);
        idBox.setAlignment(Pos.CENTER_LEFT);
        idBox.setPrefWidth(170);
        Label idBadge = new Label(id);
        idBadge.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_TEAL + "; -fx-background-color: " + INFO_BG + "; -fx-padding: 4 8; -fx-background-radius: 6px; -fx-border-radius: 6px;");
        idBox.getChildren().add(idBadge);

        // 2. Patient Name
        HBox nameBox = new HBox(8);
        nameBox.setAlignment(Pos.CENTER_LEFT);
        nameBox.setPrefWidth(190);
        StackPane initialHolder = new StackPane();
        initialHolder.setPrefSize(26, 26);
        initialHolder.setStyle("-fx-background-color: " + INDIGO_BG + "; -fx-background-radius: 13px;");
        Label initialLbl = new Label(name.isEmpty() ? "P" : name.substring(0, 1).toUpperCase());
        initialLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + INDIGO_TEXT + ";");
        initialHolder.getChildren().add(initialLbl);

        Text nameText = new Text(name);
        nameText.setStyle(FONT_FAMILY + "-fx-fill: " + TEXT_PRIMARY + "; -fx-font-size: 13px; -fx-font-weight: bold;");
        nameBox.getChildren().addAll(initialHolder, nameText);

        // 3. OCR Summary Button
        HBox ocrBox = new HBox();
        ocrBox.setAlignment(Pos.CENTER_LEFT);
        ocrBox.setPrefWidth(190);
        Button ocrBtn = new Button("Clinical Summary");
        ocrBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + INDIGO_BG + "; " +
                "-fx-text-fill: " + INDIGO_TEXT + "; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-color: " + INDIGO_BORDER + "; " +
                "-fx-border-radius: 8px; " +
                "-fx-padding: 6 12; " +
                "-fx-cursor: hand;"
        );
        ocrBtn.setOnAction(e -> showOcrReportModal(id, "Family Submission - " + name, summary));
        ocrBox.getChildren().add(ocrBtn);

        // 4. Timestamp
        HBox timeBox = new HBox(6);
        timeBox.setAlignment(Pos.CENTER_LEFT);
        timeBox.setPrefWidth(180);
        Text timeText = new Text(visit);
        timeText.setStyle(FONT_FAMILY + "-fx-fill: " + TEXT_MUTED + "; -fx-font-size: 12px;");
        timeBox.getChildren().add(timeText);

        // 5. Actions (View Full Details & Photos)
        HBox actions = new HBox(8);
        actions.setPrefWidth(230);
        actions.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(actions, Priority.ALWAYS);

        Button viewDetailsBtn = new Button("Profile");
        viewDetailsBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_TEAL + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-padding: 6 12; " +
                "-fx-cursor: hand;"
        );
        viewDetailsBtn.setOnMouseEntered(e -> viewDetailsBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + TEAL_HOVER + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-padding: 6 12; " +
                "-fx-cursor: hand;"
        ));
        viewDetailsBtn.setOnMouseExited(e -> viewDetailsBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_TEAL + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-padding: 6 12; " +
                "-fx-cursor: hand;"
        ));
        viewDetailsBtn.setOnAction(e -> fetchAndShowMemberDetailsModal(name));

        Button photosBtn = new Button("Evidence");
        photosBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #F1F5F9; " +
                "-fx-text-fill: " + PRIMARY_TEAL + "; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 8px; " +
                "-fx-padding: 6 12; " +
                "-fx-cursor: hand;"
        );
        photosBtn.setOnAction(e -> showPhotoGalleryDialog(id, name, reportUrl));

        actions.getChildren().addAll(viewDetailsBtn, photosBtn);

        row.getChildren().addAll(idBox, nameBox, ocrBox, timeBox, actions);
        return row;
    }

    // =========================================================================
    // MODAL 1: MEMBER PROFILE DRAWER (family -> email -> members)
    // =========================================================================
    private void fetchAndShowMemberDetailsModal(String patientName) {
        if (rootStackPane == null) return;

        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(15, 23, 42, 0.65);");

        VBox modalCard = new VBox(18);
        modalCard.setMaxSize(700, 560);
        modalCard.setPrefSize(700, 560);
        modalCard.setPadding(new Insets(26));
        modalCard.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-background-radius: 18px; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 18px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.25), 30, 0, 0, 10);"
        );

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBadge = new StackPane();
        iconBadge.setPrefSize(38, 38);
        iconBadge.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 10px; -fx-border-color: " + INFO_BORDER + "; -fx-border-radius: 10px;");
        Label iconLbl = new Label("👤");
        iconLbl.setStyle("-fx-font-size: 18px;");
        iconBadge.getChildren().add(iconLbl);

        VBox titleBox = new VBox(2);
        Text title = new Text("Patient Comprehensive Health Profile");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text subtitle = new Text("Queried from Family Health Vault for: " + patientName);
        subtitle.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-fill: " + TEXT_MUTED + ";");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button closeTopBtn = new Button("✕");
        closeTopBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 16px; -fx-cursor: hand;");
        closeTopBtn.setOnAction(e -> rootStackPane.getChildren().remove(overlay));

        header.getChildren().addAll(iconBadge, titleBox, spacer, closeTopBtn);

        StackPane contentHolder = new StackPane();
        VBox.setVgrow(contentHolder, Priority.ALWAYS);
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(35, 35);
        contentHolder.getChildren().add(spinner);

        Button dismissBtn = new Button("Close Profile");
        dismissBtn.setPrefWidth(120);
        dismissBtn.setPrefHeight(40);
        dismissBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_TEAL + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 10px; " +
                "-fx-cursor: hand;"
        );
        dismissBtn.setOnAction(e -> rootStackPane.getChildren().remove(overlay));

        HBox footer = new HBox(dismissBtn);
        footer.setAlignment(Pos.CENTER_RIGHT);

        modalCard.getChildren().addAll(header, contentHolder, footer);
        overlay.getChildren().add(modalCard);
        StackPane.setAlignment(modalCard, Pos.CENTER);
        rootStackPane.getChildren().add(overlay);

        overlay.setOnMouseClicked(e -> {
            if (e.getTarget() == overlay) {
                rootStackPane.getChildren().remove(overlay);
            }
        });

        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                DocumentSnapshot matchedMemberDoc = null;
                if (db != null) {
                    QuerySnapshot familyDocs = db.collection("family").get().get();
                    for (DocumentSnapshot familyDoc : familyDocs.getDocuments()) {
                        QuerySnapshot memberDocs = familyDoc.getReference().collection("members").get().get();
                        for (DocumentSnapshot memDoc : memberDocs.getDocuments()) {
                            String fullName = memDoc.getString("fullName");
                            if (fullName == null) fullName = memDoc.getString("name");
                            if (fullName != null && fullName.trim().equalsIgnoreCase(patientName.trim())) {
                                matchedMemberDoc = memDoc;
                                break;
                            }
                        }
                        if (matchedMemberDoc != null) break;
                    }
                }

                final DocumentSnapshot finalDoc = matchedMemberDoc;
                Platform.runLater(() -> {
                    contentHolder.getChildren().clear();
                    if (finalDoc == null || !finalDoc.exists()) {
                        Label notFoundLbl = new Label("No specific member profile found for '" + patientName + "' under family repository.");
                        notFoundLbl.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-text-fill: " + TEXT_MUTED + ";");
                        contentHolder.getChildren().add(notFoundLbl);
                    } else {
                        contentHolder.getChildren().add(buildMemberDetailsGrid(finalDoc));
                    }
                });
            } catch (Exception ex) {
                Platform.runLater(() -> {
                    Label errLbl = new Label("Error retrieving records: " + ex.getMessage());
                    errLbl.setStyle(FONT_FAMILY + "-fx-text-fill: " + DANGER_TEXT + ";");
                    contentHolder.getChildren().add(errLbl);
                });
            }
        }).start();
    }

    private ScrollPane buildMemberDetailsGrid(DocumentSnapshot doc) {
        VBox container = new VBox(16);
        container.setPadding(new Insets(18));
        container.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 14px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 14px;");

        GridPane grid = new GridPane();
        grid.setHgap(22);
        grid.setVgap(16);
        grid.setAlignment(Pos.CENTER_LEFT);

        addGridField(grid, "Full Name", doc.getString("fullName") != null ? doc.getString("fullName") : (doc.getString("name") != null ? doc.getString("name") : "N/A"), 0, 0);
        addGridField(grid, "Age", doc.getString("age") != null ? doc.getString("age") : "N/A", 1, 0);
        addGridField(grid, "Gender", doc.getString("gender") != null ? doc.getString("gender") : "N/A", 0, 1);
        addGridField(grid, "Blood Group", doc.getString("bloodGroup") != null ? doc.getString("bloodGroup") : "N/A", 1, 1);
        addGridField(grid, "Weight", doc.getString("weight") != null ? doc.getString("weight") : "N/A", 0, 2);
        addGridField(grid, "Member Tag", doc.getString("memberTag") != null ? doc.getString("memberTag") : "Primary Member", 1, 2);
        addGridField(grid, "Primary Phone", doc.getString("phone") != null ? doc.getString("phone") : "N/A", 0, 3);
        addGridField(grid, "ICE Emergency Contact", doc.getString("emergencyContact") != null ? doc.getString("emergencyContact") : "N/A", 1, 3);
        addGridField(grid, "Known Allergies", doc.getString("allergy") != null ? doc.getString("allergy") : "None declared", 0, 4);
        addGridField(grid, "Chronic Conditions", doc.getString("chronicCondition") != null ? doc.getString("chronicCondition") : "None recorded", 1, 4);

        container.getChildren().add(grid);
        ScrollPane sp = new ScrollPane(container);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        return sp;
    }

    private void addGridField(GridPane grid, String labelText, String valueText, int col, int row) {
        VBox box = new VBox(4);
        box.setPadding(new Insets(10, 14, 10, 14));
        box.setStyle("-fx-background-color: " + SURFACE + "; -fx-background-radius: 10px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 10px;");
        box.setPrefWidth(290);

        Label lbl = new Label(labelText);
        lbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-weight: bold; -fx-letter-spacing: 0.3px;");

        Label val = new Label(valueText);
        val.setWrapText(true);
        val.setStyle(FONT_FAMILY + "-fx-font-size: 13.5px; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-weight: 600;");

        box.getChildren().addAll(lbl, val);
        grid.add(box, col, row);
    }

    // =========================================================================
    // MODAL 2: CLINICAL OCR SUMMARY REPORT MODAL
    // =========================================================================
    private void showOcrReportModal(String id, String subtitleStr, String summaryText) {
        if (rootStackPane == null) return;
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(15, 23, 42, 0.65);");

        VBox modalCard = new VBox(18);
        modalCard.setMaxSize(680, 520);
        modalCard.setPrefSize(680, 520);
        modalCard.setPadding(new Insets(26));
        modalCard.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-background-radius: 18px; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 18px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.25), 30, 0, 0, 10);"
        );

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBadge = new StackPane();
        iconBadge.setPrefSize(38, 38);
        iconBadge.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 10px; -fx-border-color: " + INFO_BORDER + "; -fx-border-radius: 10px;");
        Label iconLbl = new Label("📋");
        iconLbl.setStyle("-fx-font-size: 18px;");
        iconBadge.getChildren().add(iconLbl);

        VBox titleBox = new VBox(2);
        Text title = new Text("Clinical OCR Summary Report");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text subtitle = new Text("Record ID: " + id + "  •  " + subtitleStr);
        subtitle.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-fill: " + TEXT_MUTED + ";");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button closeTopBtn = new Button("✕");
        closeTopBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 16px; -fx-cursor: hand;");
        closeTopBtn.setOnAction(e -> rootStackPane.getChildren().remove(overlay));

        header.getChildren().addAll(iconBadge, titleBox, spacer, closeTopBtn);

        VBox summaryContainer = new VBox(10);
        summaryContainer.setPadding(new Insets(16));
        summaryContainer.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 12px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 12px;");

        Label reportContent = new Label(summaryText);
        reportContent.setWrapText(true);
        reportContent.setStyle(FONT_FAMILY + "-fx-font-size: 13.5px; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-line-spacing: 5px;");
        summaryContainer.getChildren().add(reportContent);

        ScrollPane sp = new ScrollPane(summaryContainer);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(sp, Priority.ALWAYS);

        Button closeBtn = new Button("Close Report");
        closeBtn.setPrefWidth(120);
        closeBtn.setPrefHeight(40);
        closeBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_TEAL + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 10px; " +
                "-fx-cursor: hand;"
        );
        closeBtn.setOnAction(e -> rootStackPane.getChildren().remove(overlay));

        HBox footer = new HBox(closeBtn);
        footer.setAlignment(Pos.CENTER_RIGHT);

        modalCard.getChildren().addAll(header, sp, footer);
        overlay.getChildren().add(modalCard);
        StackPane.setAlignment(modalCard, Pos.CENTER);
        rootStackPane.getChildren().add(overlay);

        overlay.setOnMouseClicked(e -> {
            if (e.getTarget() == overlay) {
                rootStackPane.getChildren().remove(overlay);
            }
        });
    }

    // =========================================================================
    // MODAL 3: PHOTO & DOCUMENT VIEWER MODAL
    // =========================================================================
    private void showPhotoGalleryDialog(String patientId, String tripId, String reportUrl) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Medical Documents & Evidence - " + patientId);

        VBox layout = new VBox(16);
        layout.setPadding(new Insets(24));
        layout.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);

        StackPane docIconHolder = new StackPane();
        docIconHolder.setPrefSize(34, 34);
        docIconHolder.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 8px;");
        Label dIcon = new Label("📸");
        dIcon.setStyle("-fx-font-size: 16px;");
        docIconHolder.getChildren().add(dIcon);

        VBox titleBox = new VBox(2);
        Text title = new Text("Attached Diagnostic Evidence");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Label meta = new Label("Patient: " + patientId + "  •  Reference: " + tripId);
        meta.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + PRIMARY_TEAL + "; -fx-font-weight: bold;");
        titleBox.getChildren().addAll(title, meta);

        topRow.getChildren().addAll(docIconHolder, titleBox);

        VBox imagesContainer = new VBox(16);
        imagesContainer.setAlignment(Pos.CENTER);
        imagesContainer.setPadding(new Insets(10));

        if (reportUrl == null || reportUrl.trim().isEmpty()) {
            VBox emptyBox = new VBox(10);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(30));
            Label noImageLabel = new Label("No document image or photo URL attached to this clinical record.");
            noImageLabel.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-text-fill: " + TEXT_MUTED + ";");
            emptyBox.getChildren().add(noImageLabel);
            imagesContainer.getChildren().add(emptyBox);
        } else {
            String[] urls = reportUrl.split("\\|");
            for (String rawUrl : urls) {
                String singleUrl = rawUrl.trim();
                if (singleUrl.isEmpty()) continue;

                VBox singleImageBox = new VBox(10);
                singleImageBox.setAlignment(Pos.CENTER);
                singleImageBox.setPadding(new Insets(14));
                singleImageBox.setStyle(CARD_STYLE);

                ProgressIndicator spinner = new ProgressIndicator();
                spinner.setPrefSize(35, 35);

                ImageView imageView = new ImageView();
                imageView.setFitWidth(520);
                imageView.setFitHeight(340);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);

                Rectangle clip = new Rectangle();
                clip.setArcWidth(12);
                clip.setArcHeight(12);
                clip.widthProperty().bind(imageView.fitWidthProperty());
                clip.heightProperty().bind(imageView.fitHeightProperty());
                imageView.setClip(clip);

                Image img = new Image(singleUrl, true);
                img.progressProperty().addListener((obs, oldVal, newVal) -> {
                    if (newVal.doubleValue() >= 1.0) {
                        spinner.setVisible(false);
                    }
                });
                img.errorProperty().addListener((obs, oldVal, isErr) -> {
                    if (isErr) {
                        spinner.setVisible(false);
                        Label errLbl = new Label("Could not render in-app image preview. Click below to view in browser.");
                        errLbl.setStyle(FONT_FAMILY + "-fx-text-fill: " + DANGER_TEXT + "; -fx-font-size: 11.5px;");
                        singleImageBox.getChildren().add(errLbl);
                    }
                });

                imageView.setImage(img);
                StackPane imageStack = new StackPane(spinner, imageView);
                imageStack.setAlignment(Pos.CENTER);

                Button fullResBtn = new Button("Open Full Resolution Document");
                fullResBtn.setStyle(
                        FONT_FAMILY +
                        "-fx-background-color: " + INFO_BG + "; " +
                        "-fx-text-fill: " + PRIMARY_TEAL + "; " +
                        "-fx-font-size: 11.5px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-color: " + INFO_BORDER + "; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-padding: 6 14; " +
                        "-fx-cursor: hand;"
                );
                fullResBtn.setOnAction(ev -> openUrlInBrowser(singleUrl));

                singleImageBox.getChildren().addAll(imageStack, fullResBtn);
                imagesContainer.getChildren().add(singleImageBox);
            }
        }

        ScrollPane scroll = new ScrollPane(imagesContainer);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(420);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 12px;");

        HBox btnRow = new HBox();
        btnRow.setAlignment(Pos.CENTER_RIGHT);

        Button closeBtn = new Button("Close Viewer");
        closeBtn.setPrefWidth(110);
        closeBtn.setPrefHeight(38);
        closeBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_TEAL + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 12.5px; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
        );
        closeBtn.setOnAction(e -> stage.close());
        btnRow.getChildren().add(closeBtn);

        layout.getChildren().addAll(topRow, scroll, btnRow);

        Scene scene = new Scene(layout, 620, 560);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void openUrlInBrowser(String urlStr) {
        if (urlStr == null || urlStr.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("File Unavailable");
            alert.setHeaderText(null);
            alert.setContentText("No document URL attached.");
            alert.showAndWait();
            return;
        }
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(urlStr.trim()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}