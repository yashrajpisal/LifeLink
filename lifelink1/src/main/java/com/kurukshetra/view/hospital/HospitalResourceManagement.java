package com.kurukshetra.view.hospital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.kurukshetra.controller.hospitalController.DoctorController;
import com.kurukshetra.controller.hospitalController.HospitalResourceController;
import com.kurukshetra.controller.hospitalController.OperationTheatreController;
import com.kurukshetra.model.hospitalModel.DoctorModel;
import com.kurukshetra.model.hospitalModel.HospitalResourceModel;
import com.kurukshetra.model.hospitalModel.OperationTheatreModel;
import com.kurukshetra.view.util.ShimmerLoader;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HospitalResourceManagement {

    // =========================================================================
    // DESIGN SYSTEM CONSTANTS (MATCHING HospitalDashboard.java)
    // =========================================================================
    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";
    private static final String PRIMARY_TEAL = "#006591";
    private static final String TEAL_HOVER = "#004F72";
    private static final String PAGE_BG = "#F8FAFC";
    private static final String PAGE_BG_green = "#a5bdaaff ";
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

    private static final String CARD_STYLE =
            "-fx-background-color: " + SURFACE + "; " +
            "-fx-border-color: " + BORDER_COLOR + "; " +
            "-fx-border-radius: 18px; " +
            "-fx-background-radius: 18px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.06), 20, 0, 0, 4);";

    private final String hospitalId;
    private final HospitalResourceController hospitalResourceController;

    public HospitalResourceManagement(String hospitalId) {
        this.hospitalId = hospitalId;
        this.hospitalResourceController = new HospitalResourceController();
    }

    public VBox getResourceManagement() {

        HospitalResourceModel resource = hospitalResourceController.getHospitalResource(hospitalId);

        VBox mainContent = new VBox(24);
        mainContent.setPadding(new Insets(26, 32, 36, 32));
        mainContent.setStyle("-fx-background-color: " + PAGE_BG_green + "; " + FONT_FAMILY);

        // =====================================================================
        // 1. TOP HEADER (TITLE & ACTION BUTTONS)
        // =====================================================================
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox headingBox = new VBox(4);
        Text heading = new Text("Critical Assets & Operation Theatres");
        heading.setStyle(FONT_FAMILY + "-fx-font-size: 26px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");

        headingBox.getChildren().addAll(heading);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Button updateButton = new Button("Update Inventory");
        updateButton.setPrefHeight(42);
        updateButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_TEAL + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 12.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 10px; " +
                "-fx-padding: 0 18; " +
                "-fx-cursor: hand;"
        );
        updateButton.setEffect(new DropShadow(10, 0, 2, Color.rgb(0, 101, 145, 0.25)));

        updateButton.setOnMouseEntered(e -> {
            updateButton.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + TEAL_HOVER + "; " +
                    "-fx-text-fill: #FFFFFF; " +
                    "-fx-font-size: 12.5px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-padding: 0 18; " +
                    "-fx-cursor: hand;"
            );
            updateButton.setTranslateY(-2);
        });
        updateButton.setOnMouseExited(e -> {
            updateButton.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + PRIMARY_TEAL + "; " +
                    "-fx-text-fill: #FFFFFF; " +
                    "-fx-font-size: 12.5px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-padding: 0 18; " +
                    "-fx-cursor: hand;"
            );
            updateButton.setTranslateY(0);
        });

        HBox headerButtons = new HBox(12, updateButton);
        headerButtons.setAlignment(Pos.CENTER_RIGHT);

        header.getChildren().addAll(headingBox, headerSpacer, headerButtons);

        // =====================================================================
        // 2. CRITICAL INVENTORY & BED CAPACITY SECTION
        // =====================================================================
        VBox inventorySection = new VBox(14);

        HBox inventoryTitleRow = new HBox(12);
        inventoryTitleRow.setAlignment(Pos.CENTER_LEFT);

        StackPane invIconHolder = new StackPane();
        invIconHolder.setPrefSize(34, 34);
        invIconHolder.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 8px;");
        Label invIcon = new Label("🏥");
        invIcon.setStyle("-fx-font-size: 16px;");
        invIconHolder.getChildren().add(invIcon);

        VBox invTitleBox = new VBox(2);
        Text inventoryStatusTitle = new Text("Critical Inventory & Bed Telemetry");
        inventoryStatusTitle.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        invTitleBox.getChildren().addAll(inventoryStatusTitle);

        Region invSpacer = new Region();
        HBox.setHgrow(invSpacer, Priority.ALWAYS);


        inventoryTitleRow.getChildren().addAll(invIconHolder, invTitleBox, invSpacer);

        // --- ROW 1 CARDS: ICU BEDS, EMERGENCY BEDS, GENERAL BEDS ---
        int icuAvail = (resource != null) ? resource.getAvailableICUBeds() : 0;
        int icuTot = (resource != null) ? resource.getTotalICUBeds() : 0;
        Text icuValue = new Text(String.valueOf(icuAvail));
        Text icuTotal = new Text(" / " + icuTot + " available");
        VBox icuCard = createResourceCard("🛏️", PRIMARY_TEAL, INFO_BG, "ICU Critical Beds", icuValue, icuTotal,
                icuAvail < 5 ? "LOW STOCK" : "OPERATIONAL", icuAvail < 5 ? DANGER_BG : SUCCESS_BG, icuAvail < 5 ? DANGER_TEXT : SUCCESS_TEXT,
                (double) icuAvail / Math.max(1, icuTot), PRIMARY_TEAL);

        int emAvail = (resource != null) ? resource.getAvailableEmergencyBeds() : 0;
        int emTot = (resource != null) ? resource.getTotalEmergencyBeds() : 0;
        Text emergencyValue = new Text(String.valueOf(emAvail));
        Text emergencyTotal = new Text(" / " + emTot + " available");
        VBox emergencyCard = createResourceCard("🚨", DANGER_TEXT, DANGER_BG, "Emergency Trauma Beds", emergencyValue, emergencyTotal,
                emAvail < 8 ? "HIGH TRAFFIC" : "AVAILABLE", emAvail < 8 ? WARNING_BG : SUCCESS_BG, emAvail < 8 ? WARNING_TEXT : SUCCESS_TEXT,
                (double) emAvail / Math.max(1, emTot), "#EA580C");

        int genAvail = (resource != null) ? resource.getAvailableGeneralBeds() : 0;
        int genTot = (resource != null) ? resource.getTotalGeneralBeds() : 0;
        Text generalValue = new Text(String.valueOf(genAvail));
        Text generalTotal = new Text(" / " + genTot + " available");
        VBox generalCard = createResourceCard("🏨", SUCCESS_TEXT, SUCCESS_BG, "General Ward Beds", generalValue, generalTotal,
                "OPTIMAL", SUCCESS_BG, SUCCESS_TEXT,
                (double) genAvail / Math.max(1, genTot), SUCCESS_TEXT);

        icuCard.setMaxWidth(Double.MAX_VALUE);
        emergencyCard.setMaxWidth(Double.MAX_VALUE);
        generalCard.setMaxWidth(Double.MAX_VALUE);

        // --- ROW 2 CARDS: VENTILATORS, OXYGEN, BLOOD UNITS ---
        int ventAvail = (resource != null) ? resource.getAvailableVentilators() : 0;
        int ventTot = (resource != null) ? resource.getTotalVentilators() : 0;
        Text ventilatorValue = new Text(String.valueOf(ventAvail));
        Text ventilatorTotal = new Text(" / " + ventTot + " available");
        VBox ventilatorCard = createResourceCard("≈", "#0284C7", "#F0F9FF", "Mechanical Ventilators", ventilatorValue, ventilatorTotal,
                "OPERATIONAL", SUCCESS_BG, SUCCESS_TEXT,
                (double) ventAvail / Math.max(1, ventTot), "#0284C7");

        int oxAvail = (resource != null) ? resource.getOxygenReserve() : 0;
        Text oxygenValue = new Text(String.valueOf(oxAvail));
        Text oxygenTotal = new Text(" % capacity");
        VBox oxygenCard = createResourceCard("O₂", PRIMARY_TEAL, INFO_BG, "Oxygen Reserves", oxygenValue, oxygenTotal,
                oxAvail > 70 ? "SAFE RANGE" : "LOW PRESSURE", oxAvail > 70 ? SUCCESS_BG : WARNING_BG, oxAvail > 70 ? SUCCESS_TEXT : WARNING_TEXT,
                (double) oxAvail / 100.0, PRIMARY_TEAL);

        // Blood Units Card
        VBox bloodCard = new VBox(10);
        bloodCard.setPadding(new Insets(18, 20, 18, 20));
        bloodCard.setPrefHeight(170);
        bloodCard.setStyle(CARD_STYLE);
        HBox.setHgrow(bloodCard, Priority.ALWAYS);

        bloodCard.setOnMouseEntered(e -> {
            bloodCard.setTranslateY(-4);
            bloodCard.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-border-radius: 18px; -fx-background-radius: 18px; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.12), 24, 0, 0, 8);");
        });
        bloodCard.setOnMouseExited(e -> {
            bloodCard.setTranslateY(0);
            bloodCard.setStyle(CARD_STYLE);
        });

        HBox bloodTop = new HBox(8);
        bloodTop.setAlignment(Pos.CENTER_LEFT);
        StackPane bIconHolder = new StackPane();
        bIconHolder.setPrefSize(34, 34);
        bIconHolder.setStyle("-fx-background-color: " + DANGER_BG + "; -fx-background-radius: 8px;");
        Text bIcon = new Text("♥");
        bIcon.setStyle("-fx-font-size: 16px; -fx-fill: " + DANGER_TEXT + ";");
        bIconHolder.getChildren().add(bIcon);

        Text bloodTitle = new Text("Blood Bank Reserves");
        bloodTitle.setStyle(FONT_FAMILY + "-fx-font-size: 13.5px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Region bSpacer = new Region();
        HBox.setHgrow(bSpacer, Priority.ALWAYS);

        Label bloodStatus = new Label("O- CRITICAL");
        bloodStatus.setStyle(FONT_FAMILY + "-fx-background-color: " + DANGER_BG + "; -fx-text-fill: " + DANGER_TEXT + "; -fx-font-size: 9.5px; -fx-font-weight: bold; -fx-background-radius: 6px; -fx-padding: 3px 8px;");
        bloodTop.getChildren().addAll(bIconHolder, bloodTitle, bSpacer, bloodStatus);

        HBox bloodGroups = new HBox(12);
        bloodGroups.setAlignment(Pos.CENTER_LEFT);
        
        VBox oNeg = createBloodGroup("O-", "4u", true);
        VBox aPos = createBloodGroup("A+", "22u", false);
        VBox bNeg = createBloodGroup("B-", "12u", false);
        VBox abPos = createBloodGroup("AB+", "18u", false);
        
        HBox.setHgrow(oNeg, Priority.ALWAYS);
        HBox.setHgrow(aPos, Priority.ALWAYS);
        HBox.setHgrow(bNeg, Priority.ALWAYS);
        HBox.setHgrow(abPos, Priority.ALWAYS);
        
        bloodGroups.getChildren().addAll(oNeg, aPos, bNeg, abPos);

        Text bloodWarning = new Text("⚠️ Warning: O- Negative critical reserve threshold detected.");
        bloodWarning.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-fill: " + DANGER_TEXT + "; -fx-font-weight: 600;");

        Region bloodSpacer = new Region();
        VBox.setVgrow(bloodSpacer, Priority.ALWAYS);

        bloodCard.getChildren().addAll(bloodTop, bloodSpacer, bloodGroups, bloodWarning);

        ventilatorCard.setMaxWidth(Double.MAX_VALUE);
        oxygenCard.setMaxWidth(Double.MAX_VALUE);
        bloodCard.setMaxWidth(Double.MAX_VALUE);

        javafx.scene.layout.GridPane cardGrid = new javafx.scene.layout.GridPane();
        cardGrid.setHgap(16);
        cardGrid.setVgap(16);
        javafx.scene.layout.ColumnConstraints cc = new javafx.scene.layout.ColumnConstraints();
        cc.setPercentWidth(33.333);
        cardGrid.getColumnConstraints().addAll(cc, cc, cc);
        
        cardGrid.add(icuCard, 0, 0);
        cardGrid.add(emergencyCard, 1, 0);
        cardGrid.add(generalCard, 2, 0);
        cardGrid.add(ventilatorCard, 0, 1);
        cardGrid.add(oxygenCard, 1, 1);
        cardGrid.add(bloodCard, 2, 1);

        inventorySection.getChildren().addAll(inventoryTitleRow, cardGrid);

        // Wire update availability button
        updateButton.setOnAction(e -> showUpdateAvailabilityPopup(
                icuValue, icuTotal, emergencyValue, emergencyTotal,
                generalValue, generalTotal, ventilatorValue, ventilatorTotal,
                oxygenValue, bloodWarning
        ));

        // =====================================================================
        // 4. OPERATION THEATRE (OT) ALLOCATION SECTION
        // =====================================================================
        OperationTheatreController operationTheatreController = new OperationTheatreController();
        DoctorController doctorController = new DoctorController();

        String[] patientNames = {
                "Rahul Patil - PAT-2026-1045",
                "Aarav Sharma - PAT-2026-1082",
                "Sneha Deshmukh - PAT-2026-1091",
                "Vikram Joshi - PAT-2026-1104",
                "Neha Kulkarni - PAT-2026-1120"
        };

        String[] procedureNames = {
                "Cardiac Bypass Surgery",
                "Appendectomy",
                "Orthopedic Surgery",
                "Gallbladder Surgery",
                "Hernia Repair",
                "Emergency Surgery"
        };

        String[] operationTimes = {
                "09:00 AM",
                "10:30 AM",
                "12:00 PM",
                "02:30 PM",
                "04:00 PM",
                "06:30 PM"
        };

        List<DoctorModel> firebaseDoctors = new ArrayList<>();
        Map<String, Set<String>> assignedDoctors = new HashMap<>();
        assignedDoctors.put("OT-01", new LinkedHashSet<>());
        assignedDoctors.put("OT-02", new LinkedHashSet<>());
        assignedDoctors.put("OT-03", new LinkedHashSet<>());

        Map<String, Label> otStatusLabels = new HashMap<>();
        Map<String, Label> otSummaryLabels = new HashMap<>();
        Map<String, ComboBox<String>> otPatients = new HashMap<>();
        Map<String, ComboBox<String>> otProcedures = new HashMap<>();
        Map<String, ComboBox<String>> otTimes = new HashMap<>();
        Map<String, VBox> otDoctorPanes = new HashMap<>();
        Map<String, List<ToggleButton>> otDoctorButtons = new HashMap<>();
        Map<String, Button> otReserveButtons = new HashMap<>();
        Map<String, Button> otCompleteButtons = new HashMap<>();

        HBox otHeading = new HBox(12);
        otHeading.setAlignment(Pos.CENTER_LEFT);

        StackPane otIconHolder = new StackPane();
        otIconHolder.setPrefSize(34, 34);
        otIconHolder.setStyle("-fx-background-color: " + INDIGO_BG + "; -fx-background-radius: 8px;");
        Label otIcon = new Label("🔬");
        otIcon.setStyle("-fx-font-size: 16px;");
        otIconHolder.getChildren().add(otIcon);

        VBox otTitleBox = new VBox(2);
        Text operationTheatreTitle = new Text("Surgical Theatres & Procedure Allocation");
        operationTheatreTitle.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        otTitleBox.getChildren().addAll(operationTheatreTitle);

        otHeading.getChildren().addAll(otIconHolder, otTitleBox);

        // Helper to create OT Cards
        Function<String, VBox> createOTCard = otId -> {
            VBox card = new VBox(14);
            card.setPadding(new Insets(20));
            card.setPrefWidth(350);
            card.setStyle(CARD_STYLE);
            HBox.setHgrow(card, Priority.ALWAYS);

            card.setOnMouseEntered(ev -> {
                card.setTranslateY(-3);
                card.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-border-radius: 14px; -fx-background-radius: 14px; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.09), 18, 0, 0, 6);");
            });
            card.setOnMouseExited(ev -> {
                card.setTranslateY(0);
                card.setStyle(CARD_STYLE);
            });

            Label otLabel = new Label(otId);
            otLabel.setStyle(FONT_FAMILY + "-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

            Label status = new Label("●  AVAILABLE");
            status.setStyle(FONT_FAMILY + "-fx-background-color: " + SUCCESS_BG + "; -fx-text-fill: " + SUCCESS_TEXT + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-background-radius: 20px; -fx-border-color: " + SUCCESS_BORDER + "; -fx-border-radius: 20px;");
            otStatusLabels.put(otId, status);

            Region topSp = new Region();
            HBox.setHgrow(topSp, Priority.ALWAYS);
            HBox top = new HBox(otLabel, topSp, status);
            top.setAlignment(Pos.CENTER_LEFT);

            // Patient Field
            Label patientTitle = new Label("TARGET PATIENT");
            patientTitle.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");
            ComboBox<String> patient = new ComboBox<>();
            patient.getItems().addAll(patientNames);
            patient.setPromptText("Select patient for surgery");
            patient.setMaxWidth(Double.MAX_VALUE);
            patient.setPrefHeight(38);
            patient.setStyle(FONT_FAMILY + "-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12px;");
            otPatients.put(otId, patient);

            // Procedure Field
            Label procedureTitle = new Label("SURGICAL PROCEDURE");
            procedureTitle.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");
            ComboBox<String> procedure = new ComboBox<>();
            procedure.getItems().addAll(procedureNames);
            procedure.setPromptText("Select procedure type");
            procedure.setMaxWidth(Double.MAX_VALUE);
            procedure.setPrefHeight(38);
            procedure.setStyle(FONT_FAMILY + "-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12px;");
            otProcedures.put(otId, procedure);

            // Doctors Field
            Label doctorTitle = new Label("SURGICAL SPECIALISTS TEAM");
            doctorTitle.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");
            Label doctorHint = new Label("Select up to 3 medical specialists:");
            doctorHint.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-text-fill: " + TEXT_MUTED + ";");

            VBox doctorPane = new VBox(6);
            doctorPane.setPadding(new Insets(4, 0, 4, 0));
            ShimmerLoader.ShimmerPane docShimmer = ShimmerLoader.createMemberPillsSkeleton(280, 36);
            doctorPane.getChildren().add(docShimmer);
            otDoctorPanes.put(otId, doctorPane);
            otDoctorButtons.put(otId, new ArrayList<>());

            VBox doctorBox = new VBox(4, doctorTitle, doctorHint, doctorPane);

            // Operation Time Field
            Label timeTitle = new Label("SCHEDULED OPERATION TIME");
            timeTitle.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");
            ComboBox<String> time = new ComboBox<>();
            time.getItems().addAll(operationTimes);
            time.setPromptText("Select time slot");
            time.setMaxWidth(Double.MAX_VALUE);
            time.setPrefHeight(38);
            time.setStyle(FONT_FAMILY + "-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12px;");
            otTimes.put(otId, time);

            // Summary Label
            Label summaryTitle = new Label("PROCEDURE BRIEF");
            summaryTitle.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");
            Label summary = new Label("No surgery currently assigned.");
            summary.setWrapText(true);
            summary.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " + TEXT_SECONDARY + ";");
            otSummaryLabels.put(otId, summary);

            VBox summaryBox = new VBox(4, summaryTitle, summary);
            summaryBox.setPadding(new Insets(10, 12, 10, 12));
            summaryBox.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

            // Action Buttons
            Button reserve = new Button("ASSIGN & RESERVE OT");
            reserve.setMaxWidth(Double.MAX_VALUE);
            reserve.setPrefHeight(40);
            reserve.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + PRIMARY_TEAL + "; " +
                    "-fx-text-fill: #FFFFFF; " +
                    "-fx-font-size: 11px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8px; " +
                    "-fx-cursor: hand;"
            );
            otReserveButtons.put(otId, reserve);

            Button complete = new Button("COMPLETE PROCEDURE");
            complete.setMaxWidth(Double.MAX_VALUE);
            complete.setPrefHeight(40);
            complete.setDisable(true);
            complete.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: #F1F5F9; " +
                    "-fx-text-fill: " + TEXT_MUTED + "; " +
                    "-fx-font-size: 11px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8px;"
            );
            otCompleteButtons.put(otId, complete);

            HBox buttons = new HBox(8, reserve, complete);
            HBox.setHgrow(reserve, Priority.ALWAYS);
            HBox.setHgrow(complete, Priority.ALWAYS);

            card.getChildren().addAll(top, new VBox(4, patientTitle, patient), new VBox(4, procedureTitle, procedure), doctorBox, new VBox(4, timeTitle, time), summaryBox, buttons);
            return card;
        };

        VBox ot1Card = createOTCard.apply("OT-01");
        VBox ot2Card = createOTCard.apply("OT-02");
        VBox ot3Card = createOTCard.apply("OT-03");

        HBox otCards = new HBox(16, ot1Card, ot2Card, ot3Card);
        HBox.setHgrow(ot1Card, Priority.ALWAYS);
        HBox.setHgrow(ot2Card, Priority.ALWAYS);
        HBox.setHgrow(ot3Card, Priority.ALWAYS);

        // Doctor Name Resolvers
        Function<String, String> getDoctorDisplayName = doctorId -> {
            for (DoctorModel doctor : firebaseDoctors) {
                if (doctor.getDoctorId().equals(doctorId)) {
                    return doctor.getDoctorName() + " (" + doctor.getSpecialization() + ")";
                }
            }
            return doctorId;
        };

        Function<Set<String>, String> getDoctorDisplayString = doctorIds -> {
            List<String> names = new ArrayList<>();
            for (String docId : doctorIds) {
                names.add(getDoctorDisplayName.apply(docId));
            }
            return String.join(", ", names);
        };

        Runnable refreshDoctorAvailability = () -> {
            Set<String> busyDoctors = new HashSet<>();
            for (Set<String> doctors : assignedDoctors.values()) {
                busyDoctors.addAll(doctors);
            }

            for (String otId : assignedDoctors.keySet()) {
                Set<String> currentDoctors = assignedDoctors.get(otId);
                List<ToggleButton> buttons = otDoctorButtons.get(otId);

                for (ToggleButton button : buttons) {
                    DoctorModel doctor = (DoctorModel) button.getUserData();
                    String doctorId = doctor.getDoctorId();
                    boolean selected = currentDoctors.contains(doctorId);
                    boolean busyElsewhere = busyDoctors.contains(doctorId) && !selected;
                    boolean firebaseBusy = "BUSY".equalsIgnoreCase(doctor.getStatus()) || "ON LEAVE".equalsIgnoreCase(doctor.getStatus());

                    button.setDisable(busyElsewhere || firebaseBusy);
                }
            }
        };

        // Real-time Doctor Roster Listener
        doctorController.listenToDoctors(hospitalId, doctors -> {
            Platform.runLater(() -> {
                firebaseDoctors.clear();
                if (doctors != null) {
                    firebaseDoctors.addAll(doctors);
                }

                for (String otId : assignedDoctors.keySet()) {
                    VBox pane = otDoctorPanes.get(otId);
                    pane.getChildren().clear();
                    otDoctorButtons.get(otId).clear();

                    for (DoctorModel doctor : firebaseDoctors) {
                        ToggleButton button = new ToggleButton(doctor.getDoctorName() + " - " + doctor.getSpecialization());
                        button.setWrapText(true);
                        button.setMaxWidth(Double.MAX_VALUE);
                        button.setUserData(doctor);
                        button.setStyle(
                                FONT_FAMILY +
                                "-fx-background-color: #F8FAFC; " +
                                "-fx-text-fill: " + TEXT_PRIMARY + "; " +
                                "-fx-font-size: 11px; " +
                                "-fx-font-weight: 600; " +
                                "-fx-background-radius: 6px; " +
                                "-fx-border-color: " + BORDER_COLOR + "; " +
                                "-fx-border-radius: 6px; " +
                                "-fx-padding: 6px 10px; " +
                                "-fx-cursor: hand;"
                        );

                        button.setOnAction(e -> {
                            Set<String> selected = assignedDoctors.get(otId);
                            String doctorId = doctor.getDoctorId();

                            if (button.isSelected()) {
                                if (selected.size() >= 3) {
                                    button.setSelected(false);
                                    return;
                                }
                                selected.add(doctorId);
                                button.setStyle(
                                        FONT_FAMILY +
                                        "-fx-background-color: " + PRIMARY_TEAL + "; " +
                                        "-fx-text-fill: #FFFFFF; " +
                                        "-fx-font-size: 11px; " +
                                        "-fx-font-weight: bold; " +
                                        "-fx-background-radius: 6px; " +
                                        "-fx-border-color: " + TEAL_HOVER + "; " +
                                        "-fx-border-radius: 6px; " +
                                        "-fx-padding: 6px 10px; " +
                                        "-fx-cursor: hand;"
                                );
                            } else {
                                selected.remove(doctorId);
                                button.setStyle(
                                        FONT_FAMILY +
                                        "-fx-background-color: #F8FAFC; " +
                                        "-fx-text-fill: " + TEXT_PRIMARY + "; " +
                                        "-fx-font-size: 11px; " +
                                        "-fx-font-weight: 600; " +
                                        "-fx-background-radius: 6px; " +
                                        "-fx-border-color: " + BORDER_COLOR + "; " +
                                        "-fx-border-radius: 6px; " +
                                        "-fx-padding: 6px 10px; " +
                                        "-fx-cursor: hand;"
                                );
                            }
                            refreshDoctorAvailability.run();
                        });

                        otDoctorButtons.get(otId).add(button);
                        pane.getChildren().add(button);
                    }
                }
                refreshDoctorAvailability.run();
            });
        });

        // Wire Reserve & Complete handlers
        for (String otId : assignedDoctors.keySet()) {
            Button reserve = otReserveButtons.get(otId);
            Button complete = otCompleteButtons.get(otId);
            ComboBox<String> patient = otPatients.get(otId);
            ComboBox<String> procedure = otProcedures.get(otId);
            ComboBox<String> time = otTimes.get(otId);
            Label status = otStatusLabels.get(otId);
            Label summary = otSummaryLabels.get(otId);

            reserve.setOnAction(e -> {
                Set<String> selected = assignedDoctors.get(otId);
                if (patient.getValue() == null || procedure.getValue() == null || time.getValue() == null || selected.isEmpty()) {
                    return;
                }

                String doctorNames = getDoctorDisplayString.apply(selected);
                final String patientVal = patient.getValue();
                final String procedureVal = procedure.getValue();
                final String timeVal = time.getValue();
                final Set<String> selectedDocs = new HashSet<>(selected);

                new Thread(() -> {
                    operationTheatreController.reserveOperationTheatre(hospitalId, otId, patientVal, procedureVal, doctorNames, timeVal);
                    for (String doctorId : selectedDocs) {
                        doctorController.updateDoctorStatus(hospitalId, doctorId, "BUSY");
                    }
                }).start();

                for (String doctorId : selectedDocs) {
                    for (DoctorModel doc : firebaseDoctors) {
                        if (doc.getDoctorId().equals(doctorId)) {
                            doc.setStatus("BUSY");
                        }
                    }
                }

                status.setText("●  RESERVED");
                status.setStyle(FONT_FAMILY + "-fx-background-color: " + WARNING_BG + "; -fx-text-fill: " + WARNING_TEXT + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-background-radius: 20px; -fx-border-color: " + WARNING_BORDER + "; -fx-border-radius: 20px;");

                summary.setText("Patient: " + patientVal + "\nProcedure: " + procedureVal + "\nSpecialists: " + doctorNames + "\nScheduled: " + timeVal);
                summary.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: 600; -fx-text-fill: " + TEXT_PRIMARY + ";");

                patient.setDisable(true);
                procedure.setDisable(true);
                time.setDisable(true);
                reserve.setDisable(true);
                reserve.setStyle(FONT_FAMILY + "-fx-background-color: #F1F5F9; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 8px;");

                complete.setDisable(false);
                complete.setStyle(FONT_FAMILY + "-fx-background-color: " + SUCCESS_TEXT + "; -fx-text-fill: #FFFFFF; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");

                for (ToggleButton button : otDoctorButtons.get(otId)) {
                    button.setDisable(true);
                }
                refreshDoctorAvailability.run();
            });

            complete.setOnAction(e -> {
                Set<String> doctorsToRelease = new HashSet<>(assignedDoctors.get(otId));

                // Also check if any doctor toggle button in this OT card was selected
                if (otDoctorButtons.containsKey(otId)) {
                    for (ToggleButton btn : otDoctorButtons.get(otId)) {
                        if (btn.isSelected() && btn.getUserData() instanceof DoctorModel) {
                            doctorsToRelease.add(((DoctorModel) btn.getUserData()).getDoctorId());
                        }
                    }
                }

                // Check stored OT in Firestore in case doctors are stored there
                try {
                    OperationTheatreModel existingOT = operationTheatreController.getOperationTheatre(hospitalId, otId);
                    if (existingOT != null && existingOT.getDoctors() != null && !existingOT.getDoctors().isEmpty()) {
                        for (DoctorModel doc : firebaseDoctors) {
                            if (existingOT.getDoctors().contains(doc.getDoctorName()) || existingOT.getDoctors().contains(doc.getDoctorId())) {
                                doctorsToRelease.add(doc.getDoctorId());
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                final Set<String> releaseList = new HashSet<>(doctorsToRelease);

                // Update doctors to AVAILABLE in Firestore
                new Thread(() -> {
                    for (String doctorId : releaseList) {
                        doctorController.updateDoctorStatus(hospitalId, doctorId, "AVAILABLE");
                    }
                    operationTheatreController.completeOperationTheatre(hospitalId, otId);
                }).start();

                // Immediately update in-memory doctor roster so UI is responsive
                for (String doctorId : releaseList) {
                    for (DoctorModel doc : firebaseDoctors) {
                        if (doc.getDoctorId().equals(doctorId)) {
                            doc.setStatus("AVAILABLE");
                        }
                    }
                }

                assignedDoctors.get(otId).clear();

                status.setText("●  AVAILABLE");
                status.setStyle(FONT_FAMILY + "-fx-background-color: " + SUCCESS_BG + "; -fx-text-fill: " + SUCCESS_TEXT + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-background-radius: 20px; -fx-border-color: " + SUCCESS_BORDER + "; -fx-border-radius: 20px;");

                summary.setText("No surgery currently assigned.");
                summary.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " + TEXT_MUTED + ";");

                patient.setValue(null);
                procedure.setValue(null);
                time.setValue(null);

                patient.setDisable(false);
                procedure.setDisable(false);
                time.setDisable(false);

                reserve.setDisable(false);
                reserve.setStyle(FONT_FAMILY + "-fx-background-color: " + PRIMARY_TEAL + "; -fx-text-fill: #FFFFFF; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");

                complete.setDisable(true);
                complete.setStyle(FONT_FAMILY + "-fx-background-color: #F1F5F9; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 8px;");

                for (ToggleButton button : otDoctorButtons.get(otId)) {
                    button.setSelected(false);
                    button.setDisable(false);
                    button.setStyle(FONT_FAMILY + "-fx-background-color: #F8FAFC; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-size: 11px; -fx-font-weight: 600; -fx-background-radius: 6px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 6px; -fx-padding: 6px 10px; -fx-cursor: hand;");
                }
                refreshDoctorAvailability.run();
            });
        }

        // Load existing reserved state from Firestore for each OT
        new Thread(() -> {
            for (String otId : assignedDoctors.keySet()) {
                try {
                    OperationTheatreModel ot = operationTheatreController.getOperationTheatre(hospitalId, otId);
                    if (ot != null && "RESERVED".equalsIgnoreCase(ot.getStatus())) {
                        Platform.runLater(() -> {
                            Label status = otStatusLabels.get(otId);
                            Label summary = otSummaryLabels.get(otId);
                            Button reserve = otReserveButtons.get(otId);
                            Button complete = otCompleteButtons.get(otId);
                            ComboBox<String> patient = otPatients.get(otId);
                            ComboBox<String> procedure = otProcedures.get(otId);
                            ComboBox<String> time = otTimes.get(otId);

                            if (status != null) {
                                status.setText("●  RESERVED");
                                status.setStyle(FONT_FAMILY + "-fx-background-color: " + WARNING_BG + "; -fx-text-fill: " + WARNING_TEXT + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-background-radius: 20px; -fx-border-color: " + WARNING_BORDER + "; -fx-border-radius: 20px;");
                            }
                            if (summary != null) {
                                summary.setText("Patient: " + (ot.getPatient() != null ? ot.getPatient() : "Assigned") +
                                        "\nProcedure: " + (ot.getProcedure() != null ? ot.getProcedure() : "Scheduled") +
                                        "\nSpecialists: " + (ot.getDoctors() != null ? ot.getDoctors() : "Assigned") +
                                        "\nScheduled: " + (ot.getOperationTime() != null ? ot.getOperationTime() : "Active"));
                                summary.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: 600; -fx-text-fill: " + TEXT_PRIMARY + ";");
                            }
                            if (patient != null) patient.setDisable(true);
                            if (procedure != null) procedure.setDisable(true);
                            if (time != null) time.setDisable(true);

                            if (reserve != null) {
                                reserve.setDisable(true);
                                reserve.setStyle(FONT_FAMILY + "-fx-background-color: #F1F5F9; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 8px;");
                            }
                            if (complete != null) {
                                complete.setDisable(false);
                                complete.setStyle(FONT_FAMILY + "-fx-background-color: " + SUCCESS_TEXT + "; -fx-text-fill: #FFFFFF; -fx-font-size: 11px; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");
                            }
                            if (ot.getDoctors() != null) {
                                for (DoctorModel doc : firebaseDoctors) {
                                    if (ot.getDoctors().contains(doc.getDoctorName()) || ot.getDoctors().contains(doc.getDoctorId())) {
                                        assignedDoctors.get(otId).add(doc.getDoctorId());
                                    }
                                }
                            }
                            if (otDoctorButtons.containsKey(otId)) {
                                for (ToggleButton button : otDoctorButtons.get(otId)) {
                                    button.setDisable(true);
                                }
                            }
                            refreshDoctorAvailability.run();
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        VBox operationTheatreSection = new VBox(14, otHeading, otCards);

        mainContent.getChildren().addAll(
                header,
                inventorySection,
                operationTheatreSection
        );

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");

        VBox finalContent = new VBox(scrollPane);
        finalContent.setStyle("-fx-background-color: " + PAGE_BG + ";");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return finalContent;
    }

    // =========================================================================
    // RESOURCE CARD BUILDER (MATCHING HospitalDashboard.java)
    // =========================================================================
    private VBox createResourceCard(String iconStr, String iconColor, String iconBg, String title,
                                   Text valText, Text totText, String statusStr, String statusBg, String statusColor,
                                   double progressPct, String progressColor) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(18, 20, 18, 20));
        card.setPrefHeight(170);
        card.setStyle(CARD_STYLE);

        card.setOnMouseEntered(e -> {
            card.setTranslateY(-4);
            card.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CBD5E1; -fx-border-radius: 18px; -fx-background-radius: 18px; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.12), 24, 0, 0, 8);");
        });
        card.setOnMouseExited(e -> {
            card.setTranslateY(0);
            card.setStyle(CARD_STYLE);
        });

        HBox top = new HBox(8);
        top.setAlignment(Pos.CENTER_LEFT);

        StackPane iconPane = new StackPane();
        iconPane.setPrefSize(34, 34);
        iconPane.setStyle("-fx-background-color: " + iconBg + "; -fx-background-radius: 8px;");
        Text icon = new Text(iconStr);
        icon.setStyle(FONT_FAMILY + "-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + iconColor + ";");
        iconPane.getChildren().add(icon);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label statusBadge = new Label(statusStr);
        statusBadge.setStyle(FONT_FAMILY + "-fx-background-color: " + statusBg + "; -fx-text-fill: " + statusColor + "; -fx-font-size: 9.5px; -fx-font-weight: bold; -fx-padding: 3px 8px; -fx-background-radius: 6px;");

        top.getChildren().addAll(iconPane, spacer, statusBadge);

        Text titleText = new Text(title);
        titleText.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: 600; -fx-fill: " + TEXT_MUTED + ";");

        valText.setStyle(FONT_FAMILY + "-fx-font-size: 26px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");
        totText.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-fill: " + TEXT_MUTED + ";");

        HBox valBox = new HBox(valText, totText);
        valBox.setAlignment(Pos.BASELINE_LEFT);
        
        VBox textContent = new VBox(4, titleText, valBox);
        HBox.setHgrow(textContent, Priority.ALWAYS);

        // Circular Progress
        double safePct = Math.max(0.0, Math.min(1.0, progressPct));
        double radius = 34;
        double strokeWidth = 8;
        double gap = (safePct > 0.02 && safePct < 0.98) ? 14 : 0;
        
        javafx.scene.shape.Arc pBg = new javafx.scene.shape.Arc();
        pBg.setCenterX(radius);
        pBg.setCenterY(radius);
        pBg.setRadiusX(radius);
        pBg.setRadiusY(radius);
        pBg.setStartAngle(90 - (360 * safePct) - (gap > 0 ? gap / 2 : 0));
        pBg.setLength(-(360 * (1 - safePct)) + gap);
        pBg.setType(javafx.scene.shape.ArcType.OPEN);
        pBg.setFill(Color.TRANSPARENT);
        pBg.setStroke(Color.web("#E2E8F0"));
        pBg.setStrokeWidth(strokeWidth);
        pBg.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);
        
        javafx.scene.shape.Arc pFill = new javafx.scene.shape.Arc();
        pFill.setCenterX(radius);
        pFill.setCenterY(radius);
        pFill.setRadiusX(radius);
        pFill.setRadiusY(radius);
        pFill.setStartAngle(90);
        pFill.setLength(-(360 * safePct) + (gap > 0 ? gap / 2 : 0));
        pFill.setType(javafx.scene.shape.ArcType.OPEN);
        pFill.setFill(Color.TRANSPARENT);
        pFill.setStroke(Color.web(progressColor));
        pFill.setStrokeWidth(strokeWidth);
        pFill.setStrokeLineCap(javafx.scene.shape.StrokeLineCap.ROUND);

        Text percentText = new Text(String.format("%.0f%%", safePct * 100));
        percentText.setStyle(FONT_FAMILY + "-fx-font-size: 13.5px; -fx-font-weight: bold; -fx-fill: " + progressColor + ";");

        javafx.beans.value.ChangeListener<String> textListener = (obs, oldV, newV) -> {
            try {
                double val = Double.parseDouble(valText.getText().trim());
                double tot = 100.0;
                String totStr = totText.getText().replaceAll("[^0-9.]", "");
                if (!totStr.isEmpty()) {
                    tot = Double.parseDouble(totStr);
                }
                double newPct = Math.max(0.0, Math.min(1.0, val / Math.max(1.0, tot)));
                double newGap = (newPct > 0.02 && newPct < 0.98) ? 14 : 0;

                pBg.setStartAngle(90 - (360 * newPct) - (newGap > 0 ? newGap / 2 : 0));
                pBg.setLength(-(360 * (1 - newPct)) + newGap);

                pFill.setLength(-(360 * newPct) + (newGap > 0 ? newGap / 2 : 0));
                
                percentText.setText(String.format("%.0f%%", newPct * 100));
            } catch (Exception ignored) {}
        };
        
        valText.textProperty().addListener(textListener);
        totText.textProperty().addListener(textListener);
        
        javafx.scene.shape.Circle boundsCircle = new javafx.scene.shape.Circle(radius);
        boundsCircle.setCenterX(radius);
        boundsCircle.setCenterY(radius);
        boundsCircle.setFill(Color.TRANSPARENT);
        boundsCircle.setStroke(Color.TRANSPARENT);
        boundsCircle.setStrokeWidth(strokeWidth);

        javafx.scene.Group arcGroup = new javafx.scene.Group(boundsCircle, pBg, pFill);
        
        StackPane pPane = new StackPane(arcGroup, percentText);
        pPane.setPrefSize(radius * 2 + strokeWidth, radius * 2 + strokeWidth);
        pPane.setMinSize(radius * 2 + strokeWidth, radius * 2 + strokeWidth);
        
        HBox bottomRow = new HBox(textContent, pPane);
        bottomRow.setAlignment(Pos.CENTER);
        
        Region spacerMiddle = new Region();
        VBox.setVgrow(spacerMiddle, Priority.ALWAYS);

        card.getChildren().addAll(top, spacerMiddle, bottomRow);
        return card;
    }

    // =========================================================================
    // MODAL: UPDATE AVAILABILITY POPUP
    // =========================================================================
    private void showUpdateAvailabilityPopup(
            Text icuValue, Text icuTotal,
            Text emergencyValue, Text emergencyTotal,
            Text generalValue, Text generalTotal,
            Text ventilatorValue, Text ventilatorTotal,
            Text oxygenValue, Text bloodWarning
    ) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Update Resource Telemetry - LifeLink");
        popupStage.setResizable(false);

        VBox popupRoot = new VBox(18);
        popupRoot.setPadding(new Insets(26));
        popupRoot.setPrefWidth(640);
        popupRoot.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);
        StackPane iconH = new StackPane();
        iconH.setPrefSize(36, 36);
        iconH.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 10px;");
        Label iLbl = new Label("🔄");
        iLbl.setStyle("-fx-font-size: 18px;");
        iconH.getChildren().add(iLbl);

        VBox titleBox = new VBox(2);
        Text popupTitle = new Text("Update Hospital Resource Telemetry");
        popupTitle.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text popupSubtitle = new Text("Real-time synchronization with Central Emergency Interlock network.");
        popupSubtitle.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-fill: " + TEXT_MUTED + ";");
        titleBox.getChildren().addAll(popupTitle, popupSubtitle);
        topRow.getChildren().addAll(iconH, titleBox);

        VBox formCard = new VBox(14);
        formCard.setPadding(new Insets(20));
        formCard.setStyle(CARD_STYLE);

        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(14);

        TextField icuField = new TextField(icuValue.getText());
        TextField icuTotalField = new TextField(extractNumber(icuTotal.getText()));
        TextField emergencyField = new TextField(emergencyValue.getText());
        TextField emergencyTotalField = new TextField(extractNumber(emergencyTotal.getText()));
        TextField generalField = new TextField(generalValue.getText());
        TextField generalTotalField = new TextField(extractNumber(generalTotal.getText()));
        TextField ventilatorField = new TextField(ventilatorValue.getText());
        TextField ventilatorTotalField = new TextField(extractNumber(ventilatorTotal.getText()));
        TextField oxygenField = new TextField(oxygenValue.getText());

        stylePopupField(icuField);
        stylePopupField(icuTotalField);
        stylePopupField(emergencyField);
        stylePopupField(emergencyTotalField);
        stylePopupField(generalField);
        stylePopupField(generalTotalField);
        stylePopupField(ventilatorField);
        stylePopupField(ventilatorTotalField);
        stylePopupField(oxygenField);

        addPopupRow(grid, 0, "ICU Available Beds", icuField, "Total ICU Capacity", icuTotalField);
        addPopupRow(grid, 1, "Emergency Beds Available", emergencyField, "Total Emergency Capacity", emergencyTotalField);
        addPopupRow(grid, 2, "General Beds Available", generalField, "Total General Capacity", generalTotalField);
        addPopupRow(grid, 3, "Ventilators Available", ventilatorField, "Total Ventilator Stock", ventilatorTotalField);

        Label oxygenLabel = new Label("Oxygen Reserves (% capacity)");
        oxygenLabel.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");
        grid.add(oxygenLabel, 0, 4);
        grid.add(oxygenField, 1, 4);

        Label bloodInfo = new Label("Notice: O- Negative critical level is monitored automatically by the Blood Bank telemetry.");
        bloodInfo.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + DANGER_TEXT + "; -fx-background-color: " + DANGER_BG + "; -fx-padding: 8px 12px; -fx-background-radius: 6px;");

        formCard.getChildren().addAll(grid, bloodInfo);

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefHeight(40);
        cancelButton.setStyle(FONT_FAMILY + "-fx-background-color: " + SURFACE + "; -fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 12.5px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 0 16; -fx-cursor: hand;");
        cancelButton.setOnAction(e -> popupStage.close());

        Button saveButton = new Button("Commit Telemetry Update");
        saveButton.setPrefHeight(40);
        saveButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_TEAL + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 12.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-padding: 0 20; " +
                "-fx-cursor: hand;"
        );

        saveButton.setOnAction(e -> {
            try {
                int icuAvailable = Integer.parseInt(icuField.getText().trim());
                int icuTotalValue = Integer.parseInt(icuTotalField.getText().trim());
                int emergencyAvailable = Integer.parseInt(emergencyField.getText().trim());
                int emergencyTotalValue = Integer.parseInt(emergencyTotalField.getText().trim());
                int generalAvailable = Integer.parseInt(generalField.getText().trim());
                int generalTotalValue = Integer.parseInt(generalTotalField.getText().trim());
                int ventilatorAvailable = Integer.parseInt(ventilatorField.getText().trim());
                int ventilatorTotalValue = Integer.parseInt(ventilatorTotalField.getText().trim());
                int oxygenAvailable = Integer.parseInt(oxygenField.getText().trim());

                if (icuAvailable < 0 || icuTotalValue < 0 || emergencyAvailable < 0 || emergencyTotalValue < 0 ||
                        generalAvailable < 0 || generalTotalValue < 0 || ventilatorAvailable < 0 || ventilatorTotalValue < 0 || oxygenAvailable < 0) {
                    return;
                }

                if (icuAvailable > icuTotalValue || emergencyAvailable > emergencyTotalValue || generalAvailable > generalTotalValue ||
                        ventilatorAvailable > ventilatorTotalValue || oxygenAvailable > 100) {
                    return;
                }

                icuValue.setText(String.valueOf(icuAvailable));
                icuTotal.setText(" / " + icuTotalValue + " available");
                emergencyValue.setText(String.valueOf(emergencyAvailable));
                emergencyTotal.setText(" / " + emergencyTotalValue + " available");
                generalValue.setText(String.valueOf(generalAvailable));
                generalTotal.setText(" / " + generalTotalValue + " available");
                ventilatorValue.setText(String.valueOf(ventilatorAvailable));
                ventilatorTotal.setText(" / " + ventilatorTotalValue + " available");
                oxygenValue.setText(String.valueOf(oxygenAvailable));

                hospitalResourceController.saveHospitalResource(
                        hospitalId,
                        icuTotalValue, icuAvailable,
                        emergencyTotalValue, emergencyAvailable,
                        generalTotalValue, generalAvailable,
                        ventilatorTotalValue, ventilatorAvailable,
                        oxygenAvailable
                );

                popupStage.close();
            } catch (NumberFormatException ignored) {}
        });

        Region bSp = new Region();
        HBox.setHgrow(bSp, Priority.ALWAYS);
        HBox btnBox = new HBox(12, cancelButton, bSp, saveButton);
        btnBox.setAlignment(Pos.CENTER_RIGHT);

        popupRoot.getChildren().addAll(topRow, formCard, btnBox);

        Scene popupScene = new Scene(popupRoot);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    private void stylePopupField(TextField field) {
        field.setPrefWidth(130);
        field.setPrefHeight(36);
        field.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12px;");
    }

    private void addPopupRow(GridPane grid, int row, String leftText, TextField leftField, String rightText, TextField rightField) {
        Label leftLabel = new Label(leftText);
        leftLabel.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        Label rightLabel = new Label(rightText);
        rightLabel.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        grid.add(leftLabel, 0, row);
        grid.add(leftField, 1, row);
        grid.add(rightLabel, 2, row);
        grid.add(rightField, 3, row);
    }

    private String extractNumber(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private VBox createBloodGroup(String group, String units, boolean critical) {
        VBox box = new VBox(4);
        box.setAlignment(Pos.CENTER);
        box.setMaxWidth(Double.MAX_VALUE);
        box.setPadding(new Insets(8, 8, 8, 8));

        if (critical) {
            box.setStyle("-fx-background-color: " + DANGER_BG + "; -fx-border-color: " + DANGER_BORDER + "; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, " + DANGER_BORDER + ", 6, 0, 0, 2);");
        } else {
            box.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.02), 4, 0, 0, 1);");
        }

        Label groupLabel = new Label(group);
        groupLabel.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: 800; -fx-text-fill: " + (critical ? DANGER_TEXT : PRIMARY_TEAL) + ";");

        Label unitsLabel = new Label(units);
        unitsLabel.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: 600; -fx-text-fill: " + (critical ? DANGER_TEXT : TEXT_PRIMARY) + ";");

        box.getChildren().addAll(groupLabel, unitsLabel);
        return box;
    }

    private HBox createLogRow(String colorHex, String message, String timeStr) {
        HBox row = new HBox(12);
        row.setPadding(new Insets(10, 14, 10, 14));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 8px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px;");

        Circle dot = new Circle(4, Color.web(colorHex));
        Text msg = new Text(message);
        msg.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: 600; -fx-fill: " + TEXT_PRIMARY + ";");

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Text time = new Text(timeStr);
        time.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-fill: " + TEXT_MUTED + ";");

        row.getChildren().addAll(dot, msg, sp, time);
        return row;
    }
}
