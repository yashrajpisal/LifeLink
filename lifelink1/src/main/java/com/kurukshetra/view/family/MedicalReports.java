package com.kurukshetra.view.family;

import com.kurukshetra.controller.familyController.MedicalReportsController;
import com.kurukshetra.model.familyModel.MemberModel;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MedicalReports {

    private Stage stage;

    // Session Email (Document ID under 'family' collection)
    public static String loggedInFamilyEmail = "rutu@gmail.com";

    private final MedicalReportsController controller = new MedicalReportsController();

    // Theme Colors (Terracotta Healthcare Palette)
    private static final String PAGE_BG = "#FCF9F7";
    private static final String SURFACE = "#FFFFFF";
    private static final String PRIMARY = "#CA6721";
    private static final String PRIMARY_DARK = "#782F16";
    private static final String PRIMARY_SELECTED = "#FDF0E8";
    private static final String TEXT_PRIMARY = "#2B211D";
    private static final String TEXT_SECONDARY = "#675B55";
    private static final String TEXT_MUTED = "#938780";
    private static final String BORDER_COLOR = "#EDE0D8";
    private static final String DIVIDER = "#F1E7E1";
    private static final String VERY_LIGHT_TERRACOTTA = "#FDF0E8";
    private static final String LIGHT_TERRACOTTA = "#F9E4D6";
    private static final String PALE_PEACH = "#FEF7F2";
    private static final String FIELD_BG = "#FEF7F2";
    private static final String RED = "#D71920";
    private static final String LIGHT_RED = "#FEE2E2";
    private static final String GREEN = "#16A34A";
    private static final String LIGHT_GREEN = "#E8F5EC";

    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";

    private static final double MEMBER_PANEL_WIDTH = 380;
    private static final double MEMBER_CARD_WIDTH = 340;

    // Hierarchy Layers for Multigenerational Family Tree
    public enum HierarchyLayer {
        GRANDPARENT("Layer 1: Grandparents", "👴", "#7C3AED", "#6D28D9", "#EDE9FE", "#DDD6FE"),
        PARENT("Layer 2: Parents", "👨‍👩‍👦", "#CA6721", "#782F16", "#FDF0E8", "#F9E4D6"),
        SIBLING("Layer 3: Siblings", "👫", "#2563EB", "#1D4ED8", "#EFF6FF", "#DBEAFE"),
        CHILD("Layer 4: Children", "👶", "#059669", "#047857", "#ECFDF5", "#D1FAE5"),
        NEIGHBOUR("Layer 5: Neighbours & Handover", "🏡", "#E11D48", "#BE123C", "#FFE4E6", "#FECDD3");

        public final String title;
        public final String icon;
        public final String primaryColor;
        public final String darkColor;
        public final String bgLight;
        public final String borderLight;

        HierarchyLayer(String title, String icon, String primaryColor, String darkColor, String bgLight,
                String borderLight) {
            this.title = title;
            this.icon = icon;
            this.primaryColor = primaryColor;
            this.darkColor = darkColor;
            this.bgLight = bgLight;
            this.borderLight = borderLight;
        }
    }

    public static HierarchyLayer getMemberLayer(MemberModel m) {
        if (m == null)
            return HierarchyLayer.SIBLING;
        String rel = m.getRelation();
        if (rel != null && !rel.trim().isEmpty()) {
            String lower = rel.toLowerCase().trim();
            if (lower.contains("grand") || lower.contains("dada") || lower.contains("dadi") || lower.contains("nana")
                    || lower.contains("nani")) {
                return HierarchyLayer.GRANDPARENT;
            }
            if (lower.contains("father") || lower.contains("mother") || lower.contains("parent")
                    || lower.contains("dad") || lower.contains("mom") || lower.contains("aai")
                    || lower.contains("vadil")) {
                return HierarchyLayer.PARENT;
            }
            if (lower.contains("sister") || lower.contains("brother") || lower.contains("sibling")
                    || lower.contains("self") || lower.contains("spouse") || lower.contains("wife")
                    || lower.contains("husband") || lower.contains("bhau") || lower.contains("bahin")) {
                return HierarchyLayer.SIBLING;
            }
            if (lower.contains("son") || lower.contains("daughter") || lower.contains("child") || lower.contains("kid")
                    || lower.contains("mulga") || lower.contains("mulgi")) {
                return HierarchyLayer.CHILD;
            }
            if (lower.contains("neighbour") || lower.contains("neighbor") || lower.contains("contact")
                    || lower.contains("friend") || lower.contains("shejari") || lower.contains("handover")) {
                return HierarchyLayer.NEIGHBOUR;
            }
        }
        String tag = m.getMemberTag();
        if (tag != null) {
            String tagLower = tag.toLowerCase();
            if (tagLower.contains("grand"))
                return HierarchyLayer.GRANDPARENT;
            if (tagLower.contains("parent") || tagLower.contains("father") || tagLower.contains("mother"))
                return HierarchyLayer.PARENT;
            if (tagLower.contains("sister") || tagLower.contains("brother") || tagLower.contains("sibling"))
                return HierarchyLayer.SIBLING;
            if (tagLower.contains("child") || tagLower.contains("kid") || tagLower.contains("son")
                    || tagLower.contains("daughter"))
                return HierarchyLayer.CHILD;
            if (tagLower.contains("neighbour") || tagLower.contains("neighbor"))
                return HierarchyLayer.NEIGHBOUR;
        }

        try {
            int age = Integer.parseInt(m.getAge().replaceAll("[^0-9]", ""));
            if (age >= 65)
                return HierarchyLayer.GRANDPARENT;
            if (age >= 40)
                return HierarchyLayer.PARENT;
            if (age >= 18)
                return HierarchyLayer.SIBLING;
            return HierarchyLayer.CHILD;
        } catch (Exception ignored) {
        }

        return HierarchyLayer.SIBLING;
    }

    // Root Stack Container for Toast Notifications
    private StackPane rootStack;

    // State Management
    private VBox treeViewContainer;
    private Pane treeCanvas;
    private VBox detailsViewContainer;
    private boolean isDetailsViewActive = false;
    private Button treeViewToggleBtn;
    private Button detailsViewToggleBtn;
    private HBox memberQuickSwitcherBar;
    private final List<MemberModel> memberDataList = new ArrayList<>();
    private int selectedMemberIndex = 0;

    // Form Controls (Right Side Panel)
    private Label detailsTitle;
    private Label detailsSubtitle;
    private Label bloodGroupPill;
    private StackPane bigAvatar;
    private TextField nameField;
    private ComboBox<String> relationCombo;
    private ComboBox<String> bloodGroupCombo;
    private TextField ageField;
    private ComboBox<String> genderCombo;
    private ComboBox<String> allergiesCombo;
    private TextField weightField;
    private TextField phoneField;
    private TextField emergencyContactField;
    private ComboBox<String> chronicConditionsCombo;
    private ComboBox<String> reportTypeCombo;
    private Label uploadedFileBadge;
    private Button removeFileButton;

    public MedicalReports() {
    }

    public BorderPane setBorderPane(Stage stage) {
        this.stage = stage;

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.MEDICAL_HISTORY);
        borderPane.setLeft(sidebar);

        VBox body = createMedicalReportsBody();
        ScrollPane scrollPane = createMainScrollPane(body);

        rootStack = new StackPane();
        rootStack.getChildren().add(scrollPane);

        borderPane.setCenter(rootStack);
        playPageAnimation(scrollPane);

        // Subscribe to real-time updates from Firebase
        subscribeToRemoteData();

        return borderPane;
    }

    private void subscribeToRemoteData() {
        controller.subscribeToFamilyMembers(loggedInFamilyEmail, remoteMembers -> {
            if (remoteMembers != null && !remoteMembers.isEmpty()) {
                memberDataList.clear();
                memberDataList.addAll(remoteMembers);

                if (selectedMemberIndex >= memberDataList.size()) {
                    selectedMemberIndex = memberDataList.size() - 1;
                }

                renderFamilyTree();
                loadMemberIntoForm(selectedMemberIndex);
            } else if (memberDataList.isEmpty()) {
                // Seed initial multigenerational family tree matching the hand-drawn sketch:
                // 1 Grandparent at top
                // 2 Parents (Father & Mother)
                // 1 "Me" in center with 2 Siblings (left Brother, right Sister)
                // 2 Children (Son & Daughter)
                // 1 Neighbour below!
                MemberModel gp = new MemberModel(
                        "mem_gp_" + UUID.randomUUID().toString().substring(0, 6),
                        "Grandparent",
                        "Dr. Prabhakar Deshmukh",
                        "B+",
                        "74",
                        "Male",
                        "None / No Known Allergies",
                        "68 kg",
                        "9822011111",
                        "9822022222",
                        "Hypertension / High BP",
                        "ECG / Echocardiogram (Cardiology)",
                        "ECG_Heart_Scan_2026.pdf",
                        "Grandfather");

                MemberModel father = new MemberModel(
                        "mem_pr1_" + UUID.randomUUID().toString().substring(0, 6),
                        "Parent",
                        "Ramesh Deshmukh",
                        "O+",
                        "48",
                        "Male",
                        "None / No Known Allergies",
                        "75 kg",
                        "9822033333",
                        "9822022222",
                        "Diabetes Mellitus (Type 1 / 2)",
                        "Blood Test / Complete Blood Count (CBC)",
                        "HbA1c_Sugar_Report.pdf",
                        "Father");

                MemberModel mother = new MemberModel(
                        "mem_pr2_" + UUID.randomUUID().toString().substring(0, 6),
                        "Parent",
                        "Sunita Deshmukh",
                        "A+",
                        "45",
                        "Female",
                        "None / No Known Allergies",
                        "62 kg",
                        "9822033334",
                        "9822022222",
                        "Thyroid Disorder",
                        null,
                        "No file attached",
                        "Mother");

                MemberModel me = new MemberModel(
                        "mem_me_" + UUID.randomUUID().toString().substring(0, 6),
                        "Self",
                        "Sarah Deshmukh (Me)",
                        "O+",
                        "26",
                        "Female",
                        "Penicillin",
                        "58 kg",
                        "9822044444",
                        "9822033333",
                        "None / Healthy",
                        "Vaccination & Immunization Record",
                        "Immunization_Pass_2025.pdf",
                        "Self (Me)");

                MemberModel brother = new MemberModel(
                        "mem_sb1_" + UUID.randomUUID().toString().substring(0, 6),
                        "Sibling",
                        "Rohan Deshmukh",
                        "B+",
                        "24",
                        "Male",
                        "None / No Known Allergies",
                        "70 kg",
                        "9822044445",
                        "9822033333",
                        "None / Healthy",
                        null,
                        "No file attached",
                        "Brother");

                MemberModel sister = new MemberModel(
                        "mem_sb2_" + UUID.randomUUID().toString().substring(0, 6),
                        "Sibling",
                        "Priya Deshmukh",
                        "O+",
                        "22",
                        "Female",
                        "None / No Known Allergies",
                        "54 kg",
                        "9822044446",
                        "9822033333",
                        "None / Healthy",
                        null,
                        "No file attached",
                        "Sister");

                MemberModel son = new MemberModel(
                        "mem_ch1_" + UUID.randomUUID().toString().substring(0, 6),
                        "Child",
                        "Aarav Deshmukh",
                        "A+",
                        "6",
                        "Male",
                        "Peanuts / Tree Nuts",
                        "22 kg",
                        "9822033333",
                        "9822044444",
                        "Asthma / Respiratory Disorders",
                        null,
                        "No file attached",
                        "Son");

                MemberModel daughter = new MemberModel(
                        "mem_ch2_" + UUID.randomUUID().toString().substring(0, 6),
                        "Child",
                        "Ananya Deshmukh",
                        "O+",
                        "4",
                        "Female",
                        "None / No Known Allergies",
                        "16 kg",
                        "9822033333",
                        "9822044444",
                        "None / Healthy",
                        null,
                        "No file attached",
                        "Daughter");

                MemberModel neighbour = new MemberModel(
                        "mem_nb_" + UUID.randomUUID().toString().substring(0, 6),
                        "Neighbour",
                        "Mr. Rajesh Sharma",
                        "AB+",
                        "42",
                        "Male",
                        "None / No Known Allergies",
                        "72 kg",
                        "9822055555",
                        "9822055555",
                        "None / Healthy",
                        null,
                        "No file attached",
                        "Neighbour");

                memberDataList.add(gp);
                memberDataList.add(father);
                memberDataList.add(mother);
                memberDataList.add(me);
                memberDataList.add(brother);
                memberDataList.add(sister);
                memberDataList.add(son);
                memberDataList.add(daughter);
                memberDataList.add(neighbour);

                controller.syncAllMembers(loggedInFamilyEmail, memberDataList, () -> {
                }, err -> {
                });
                renderFamilyTree();
                loadMemberIntoForm(3); // Default selection to "Me"
            }
        }, errorMsg -> showStripToast(errorMsg, ToastType.WARNING));
    }

    private VBox createMedicalReportsBody() {
        VBox body = new VBox(16);
        body.setPadding(new Insets(20, 24, 20, 24));
        body.setStyle("-fx-background-color: " + PAGE_BG + ";");
        body.setFillWidth(true);

        // Header with title, view switcher, and status badge
        HBox header = createPageHeader();

        // Stack Container holding both Tree View and Details View
        StackPane contentStack = new StackPane();
        contentStack.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(contentStack, Priority.ALWAYS);

        treeViewContainer = createTreeView();
        detailsViewContainer = createDetailsPanel();

        // Initially show Tree View
        detailsViewContainer.setVisible(false);
        detailsViewContainer.setManaged(false);

        contentStack.getChildren().addAll(treeViewContainer, detailsViewContainer);
        body.getChildren().addAll(header, contentStack);

        return body;
    }

    private HBox createPageHeader() {
        VBox titleBox = new VBox(2);
        Label title = new Label("Family Health Dossier & Hierarchy Network");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 22px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        // Label subtitle = new Label("Interactive tree hierarchy with circular nodes.
        // Click any member node to open clinical records & dossier.");
        // subtitle.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " +
        // TEXT_SECONDARY + ";");
        titleBox.getChildren().addAll(title);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // View Mode Switcher: [🌳 Family Tree View] [📋 Patient Details]
        treeViewToggleBtn = new Button("🌳 Family Tree");
        detailsViewToggleBtn = new Button("📋 Patient Details");
        treeViewToggleBtn.setOnAction(e -> {
            saveCurrentFormData(false);
            showTreeView();
        });
        detailsViewToggleBtn.setOnAction(e -> {
            showDetailsView();
        });

        HBox toggleBox = new HBox(4, treeViewToggleBtn, detailsViewToggleBtn);
        toggleBox.setAlignment(Pos.CENTER);
        toggleBox.setPadding(new Insets(3, 4, 3, 4));
        toggleBox.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                        "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                        "-fx-border-radius: 22px;" +
                        "-fx-background-radius: 22px;");
        updateViewToggleButtons();

        HBox header = new HBox(16, titleBox, spacer, toggleBox);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private void updateViewToggleButtons() {
        if (treeViewToggleBtn == null || detailsViewToggleBtn == null)
            return;
        if (!isDetailsViewActive) {
            treeViewToggleBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + PRIMARY
                    + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-background-radius: 20px; -fx-padding: 6 14; -fx-cursor: hand;");
            detailsViewToggleBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: "
                    + TEXT_SECONDARY
                    + "; -fx-font-weight: 600; -fx-font-size: 11px; -fx-background-radius: 20px; -fx-padding: 6 14; -fx-cursor: hand;");
        } else {
            treeViewToggleBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: "
                    + TEXT_SECONDARY
                    + "; -fx-font-weight: 600; -fx-font-size: 11px; -fx-background-radius: 20px; -fx-padding: 6 14; -fx-cursor: hand;");
            detailsViewToggleBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + PRIMARY
                    + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px; -fx-background-radius: 20px; -fx-padding: 6 14; -fx-cursor: hand;");
        }
    }

    private void showTreeView() {
        isDetailsViewActive = false;
        updateViewToggleButtons();
        renderFamilyTree();

        treeViewContainer.setVisible(true);
        treeViewContainer.setManaged(true);
        detailsViewContainer.setVisible(false);
        detailsViewContainer.setManaged(false);

        treeViewContainer.setOpacity(0);
        treeViewContainer.setTranslateY(15);
        FadeTransition ft = new FadeTransition(Duration.millis(260), treeViewContainer);
        ft.setToValue(1.0);
        TranslateTransition tt = new TranslateTransition(Duration.millis(260), treeViewContainer);
        tt.setToY(0);
        new ParallelTransition(ft, tt).play();
    }

    private void showDetailsView() {
        isDetailsViewActive = true;
        updateViewToggleButtons();

        detailsViewContainer.setVisible(true);
        detailsViewContainer.setManaged(true);
        treeViewContainer.setVisible(false);
        treeViewContainer.setManaged(false);

        detailsViewContainer.setOpacity(0);
        detailsViewContainer.setTranslateY(15);
        FadeTransition ft = new FadeTransition(Duration.millis(260), detailsViewContainer);
        ft.setToValue(1.0);
        TranslateTransition tt = new TranslateTransition(Duration.millis(260), detailsViewContainer);
        tt.setToY(0);
        new ParallelTransition(ft, tt).play();
    }

    // =========================================================
    // FAMILY TREE VIEW: GRAPH-BASED HIERARCHY MATCHING DRAWN SKETCH
    // =========================================================

    private static class NodePlacement {
        final int index;
        final MemberModel member;
        final HierarchyLayer layer;
        final double x;
        final double y;
        final double width;
        final double height;

        NodePlacement(int index, MemberModel member, HierarchyLayer layer, double x, double y, double width,
                double height) {
            this.index = index;
            this.member = member;
            this.layer = layer;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        double getTopCenterX() {
            return x;
        }

        double getTopCenterY() {
            return y - height / 2.0;
        }

        double getBottomCenterX() {
            return x;
        }

        double getBottomCenterY() {
            return y + height / 2.0;
        }

        double getLeftCenterX() {
            return x - width / 2.0;
        }

        double getLeftCenterY() {
            return y;
        }

        double getRightCenterX() {
            return x + width / 2.0;
        }

        double getRightCenterY() {
            return y;
        }
    }

    private boolean isMeNode(MemberModel m) {
        if (m == null)
            return false;
        String rel = m.getRelation();
        if (rel != null) {
            String r = rel.toLowerCase();
            if (r.contains("self") || r.contains("me"))
                return true;
        }
        String name = m.getFullName();
        if (name != null) {
            String n = name.toLowerCase();
            if (n.contains("(me)") || n.equals("me") || n.contains("sarah"))
                return true;
        }
        String tag = m.getMemberTag();
        if (tag != null && (tag.equalsIgnoreCase("self") || tag.equalsIgnoreCase("me")))
            return true;
        return false;
    }

    private VBox createTreeView() {
        VBox treeContainer = new VBox(16);
        treeContainer.setFillWidth(true);

        // Toolbar
        HBox topBar = new HBox(16);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(14, 20, 14, 20));
        topBar.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 16px;" +
                        "-fx-background-radius: 16px;");
        topBar.setEffect(new DropShadow(10, 0, 3, Color.rgb(120, 47, 22, 0.05)));

        VBox titleBox = new VBox(2);
        Label heading = new Label("Multigenerational Family Hierarchy Tree");
        heading.setStyle(
                FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");
        // Label subHeading = new Label("Descent hierarchy with circular nodes &
        // connection branches. Click any node to open clinical records & dossier.");
        // subHeading.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " +
        // TEXT_SECONDARY + ";");
        titleBox.getChildren().addAll(heading);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addBtn = new Button("+ Add Member");
        addBtn.setPrefHeight(38);
        addBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + ");"
                        +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 11.5px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 0 16px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.25), 8, 0, 0, 2);");
        addBtn.setOnAction(e -> handleAddNewMember());

        topBar.getChildren().addAll(titleBox, spacer, addBtn);

        // Center Wrapper for Tree Canvas
        StackPane canvasWrapper = new StackPane();
        canvasWrapper.setAlignment(Pos.TOP_CENTER);
        canvasWrapper.setPadding(new Insets(10, 0, 20, 0));

        // Fixed coordinate graph canvas
        treeCanvas = new Pane();
        treeCanvas.setPrefSize(1000, 920);
        treeCanvas.setMinSize(1000, 920);
        treeCanvas.setMaxSize(1000, 920);
        treeCanvas.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 16px;" +
                        "-fx-background-radius: 16px;");
        treeCanvas.setEffect(new DropShadow(10, 0, 3, Color.rgb(120, 47, 22, 0.05)));

        canvasWrapper.getChildren().add(treeCanvas);
        treeContainer.getChildren().addAll(topBar, canvasWrapper);
        return treeContainer;
    }

    private void renderFamilyTree() {
        if (treeCanvas == null)
            return;
        treeCanvas.getChildren().clear();

        Group linesGroup = new Group();
        Group labelsGroup = new Group();
        Group nodesGroup = new Group();

        // Categorize members by layer
        List<Integer> gpIndices = new ArrayList<>();
        List<Integer> parentIndices = new ArrayList<>();
        List<Integer> siblingIndices = new ArrayList<>();
        List<Integer> childIndices = new ArrayList<>();
        List<Integer> neighbourIndices = new ArrayList<>();

        for (int i = 0; i < memberDataList.size(); i++) {
            MemberModel m = memberDataList.get(i);
            HierarchyLayer layer = getMemberLayer(m);
            switch (layer) {
                case GRANDPARENT:
                    gpIndices.add(i);
                    break;
                case PARENT:
                    parentIndices.add(i);
                    break;
                case SIBLING:
                    siblingIndices.add(i);
                    break;
                case CHILD:
                    childIndices.add(i);
                    break;
                case NEIGHBOUR:
                    neighbourIndices.add(i);
                    break;
            }
        }

        // Identify "Me" node among siblings
        Integer meIdx = null;
        for (int idx : siblingIndices) {
            if (isMeNode(memberDataList.get(idx))) {
                meIdx = idx;
                break;
            }
        }
        if (meIdx == null && !siblingIndices.isEmpty()) {
            if (siblingIndices.size() >= 3) {
                meIdx = siblingIndices.get(1);
            } else {
                meIdx = siblingIndices.get(0);
            }
        }

        List<Integer> otherSiblings = new ArrayList<>();
        for (int idx : siblingIndices) {
            if (meIdx != null && idx == meIdx.intValue())
                continue;
            otherSiblings.add(idx);
        }

        double centerX = 500.0;
        double nodeW = 156.0;
        double nodeH = 126.0;

        double tier1Y = 75.0; // Grandparent
        double tier2Y = 245.0; // Parents
        double tier3Y = 425.0; // Me & Siblings
        double tier4Y = 605.0; // Children
        double tier5Y = 785.0; // Neighbour

        // Side Level Badges (Watermarks naming each generation tier)
        labelsGroup.getChildren().addAll(
                createSideLevelLabel(HierarchyLayer.GRANDPARENT, "GRANDPARENT", 18, tier1Y - 15),
                createSideLevelLabel(HierarchyLayer.PARENT, "PARENTS", 18, tier2Y - 15),
                createSideLevelLabel(HierarchyLayer.SIBLING, "ME & SIBLINGS", 18, tier3Y - 15),
                createSideLevelLabel(HierarchyLayer.CHILD, "CHILDREN", 18, tier4Y - 15),
                createSideLevelLabel(HierarchyLayer.NEIGHBOUR, "NEIGHBOUR", 18, tier5Y - 15));

        // 1. Position Grandparent Nodes (Tier 1)
        List<NodePlacement> gpNodes = new ArrayList<>();
        if (!gpIndices.isEmpty()) {
            if (gpIndices.size() == 1) {
                int idx = gpIndices.get(0);
                gpNodes.add(new NodePlacement(idx, memberDataList.get(idx), HierarchyLayer.GRANDPARENT, centerX, tier1Y,
                        nodeW, nodeH));
            } else {
                double spacing = 180.0;
                double startX = centerX - ((gpIndices.size() - 1) * spacing) / 2.0;
                for (int i = 0; i < gpIndices.size(); i++) {
                    int idx = gpIndices.get(i);
                    gpNodes.add(new NodePlacement(idx, memberDataList.get(idx), HierarchyLayer.GRANDPARENT,
                            startX + i * spacing, tier1Y, nodeW, nodeH));
                }
            }
        }

        // 2. Position Parent Nodes (Tier 2)
        List<NodePlacement> parentNodes = new ArrayList<>();
        if (!parentIndices.isEmpty()) {
            if (parentIndices.size() == 1) {
                int idx = parentIndices.get(0);
                parentNodes.add(new NodePlacement(idx, memberDataList.get(idx), HierarchyLayer.PARENT, centerX - 170,
                        tier2Y, nodeW, nodeH));
            } else if (parentIndices.size() == 2) {
                int idx1 = parentIndices.get(0);
                int idx2 = parentIndices.get(1);
                parentNodes.add(new NodePlacement(idx1, memberDataList.get(idx1), HierarchyLayer.PARENT, centerX - 170,
                        tier2Y, nodeW, nodeH));
                parentNodes.add(new NodePlacement(idx2, memberDataList.get(idx2), HierarchyLayer.PARENT, centerX + 170,
                        tier2Y, nodeW, nodeH));
            } else {
                double spacing = 180.0;
                double startX = centerX - ((parentIndices.size() - 1) * spacing) / 2.0;
                for (int i = 0; i < parentIndices.size(); i++) {
                    int idx = parentIndices.get(i);
                    parentNodes.add(new NodePlacement(idx, memberDataList.get(idx), HierarchyLayer.PARENT,
                            startX + i * spacing, tier2Y, nodeW, nodeH));
                }
            }
        }

        // 3. Position "Me" and Siblings (Tier 3)
        NodePlacement mePlacement = null;
        if (meIdx != null) {
            mePlacement = new NodePlacement(meIdx, memberDataList.get(meIdx), HierarchyLayer.SIBLING, centerX, tier3Y,
                    nodeW, nodeH);
        }

        List<NodePlacement> leftSiblings = new ArrayList<>();
        List<NodePlacement> rightSiblings = new ArrayList<>();
        if (otherSiblings.size() == 1) {
            int idx = otherSiblings.get(0);
            leftSiblings.add(new NodePlacement(idx, memberDataList.get(idx), HierarchyLayer.SIBLING, centerX - 260,
                    tier3Y, nodeW, nodeH));
        } else if (otherSiblings.size() == 2) {
            int idx1 = otherSiblings.get(0);
            int idx2 = otherSiblings.get(1);
            leftSiblings.add(new NodePlacement(idx1, memberDataList.get(idx1), HierarchyLayer.SIBLING, centerX - 260,
                    tier3Y, nodeW, nodeH));
            rightSiblings.add(new NodePlacement(idx2, memberDataList.get(idx2), HierarchyLayer.SIBLING, centerX + 260,
                    tier3Y, nodeW, nodeH));
        } else if (otherSiblings.size() > 2) {
            int half = (otherSiblings.size() + 1) / 2;
            for (int i = 0; i < otherSiblings.size(); i++) {
                int idx = otherSiblings.get(i);
                if (i < half) {
                    double xPos = centerX - 250 - (half - 1 - i) * 165;
                    leftSiblings.add(new NodePlacement(idx, memberDataList.get(idx), HierarchyLayer.SIBLING, xPos,
                            tier3Y, nodeW, nodeH));
                } else {
                    double xPos = centerX + 250 + (i - half) * 165;
                    rightSiblings.add(new NodePlacement(idx, memberDataList.get(idx), HierarchyLayer.SIBLING, xPos,
                            tier3Y, nodeW, nodeH));
                }
            }
        }

        // 4. Position Children Nodes (Tier 4)
        List<NodePlacement> childNodes = new ArrayList<>();
        if (!childIndices.isEmpty()) {
            if (childIndices.size() == 1) {
                int idx = childIndices.get(0);
                childNodes.add(new NodePlacement(idx, memberDataList.get(idx), HierarchyLayer.CHILD, centerX, tier4Y,
                        nodeW, nodeH));
            } else if (childIndices.size() == 2) {
                int idx1 = childIndices.get(0);
                int idx2 = childIndices.get(1);
                childNodes.add(new NodePlacement(idx1, memberDataList.get(idx1), HierarchyLayer.CHILD, centerX - 150,
                        tier4Y, nodeW, nodeH));
                childNodes.add(new NodePlacement(idx2, memberDataList.get(idx2), HierarchyLayer.CHILD, centerX + 150,
                        tier4Y, nodeW, nodeH));
            } else {
                double spacing = 175.0;
                double startX = centerX - ((childIndices.size() - 1) * spacing) / 2.0;
                for (int i = 0; i < childIndices.size(); i++) {
                    int idx = childIndices.get(i);
                    childNodes.add(new NodePlacement(idx, memberDataList.get(idx), HierarchyLayer.CHILD,
                            startX + i * spacing, tier4Y, nodeW, nodeH));
                }
            }
        }

        // 5. Position Neighbour Nodes (Tier 5)
        List<NodePlacement> neighbourNodes = new ArrayList<>();
        if (!neighbourIndices.isEmpty()) {
            if (neighbourIndices.size() == 1) {
                int idx = neighbourIndices.get(0);
                neighbourNodes.add(new NodePlacement(idx, memberDataList.get(idx), HierarchyLayer.NEIGHBOUR, centerX,
                        tier5Y, nodeW, nodeH));
            } else {
                double spacing = 180.0;
                double startX = centerX - ((neighbourIndices.size() - 1) * spacing) / 2.0;
                for (int i = 0; i < neighbourIndices.size(); i++) {
                    int idx = neighbourIndices.get(i);
                    neighbourNodes.add(new NodePlacement(idx, memberDataList.get(idx), HierarchyLayer.NEIGHBOUR,
                            startX + i * spacing, tier5Y, nodeW, nodeH));
                }
            }
        }

        // Collect all node placements
        List<NodePlacement> allPlacements = new ArrayList<>();
        allPlacements.addAll(gpNodes);
        allPlacements.addAll(parentNodes);
        if (mePlacement != null)
            allPlacements.add(mePlacement);
        allPlacements.addAll(leftSiblings);
        allPlacements.addAll(rightSiblings);
        allPlacements.addAll(childNodes);
        allPlacements.addAll(neighbourNodes);

        // ==========================================
        // DRAW CONNECTING LINES (Strictly matching sketch)
        // ==========================================

        // 1. Grandparent -> Parents (Branching diagonal lines from Grandparent bottom
        // to Parents top)
        if (!gpNodes.isEmpty() && !parentNodes.isEmpty()) {
            NodePlacement primaryGp = gpNodes.get(0);
            for (NodePlacement parent : parentNodes) {
                linesGroup.getChildren().add(
                        createTreeConnectionLine(primaryGp.getBottomCenterX(), primaryGp.getBottomCenterY(),
                                parent.getTopCenterX(), parent.getTopCenterY(),
                                HierarchyLayer.GRANDPARENT.primaryColor, 2.4, false));
            }
        }

        // 2. Parents -> "Me" (Converging diagonal lines into "Me")
        if (mePlacement != null && !parentNodes.isEmpty()) {
            for (NodePlacement parent : parentNodes) {
                linesGroup.getChildren().add(
                        createTreeConnectionLine(parent.getBottomCenterX(), parent.getBottomCenterY(),
                                mePlacement.getTopCenterX(), mePlacement.getTopCenterY(),
                                HierarchyLayer.PARENT.primaryColor, 2.4, false));
            }
        }

        // 3. "Me" <--- [sibling] & [me] ---> [sibling] (Horizontal lines with
        // arrowheads!)
        if (mePlacement != null) {
            // Left siblings: line and arrow pointing to the left
            for (NodePlacement leftSib : leftSiblings) {
                double startX = mePlacement.getLeftCenterX();
                double startY = mePlacement.getLeftCenterY();
                double endX = leftSib.getRightCenterX();
                double endY = leftSib.getRightCenterY();
                linesGroup.getChildren().add(
                        createTreeConnectionLine(startX, startY, endX, endY,
                                HierarchyLayer.SIBLING.primaryColor, 2.4, false));
                linesGroup.getChildren().add(
                        createArrowHead(startX, startY, endX, endY, HierarchyLayer.SIBLING.primaryColor));
            }
            // Right siblings: line and arrow pointing to the right
            for (NodePlacement rightSib : rightSiblings) {
                double startX = mePlacement.getRightCenterX();
                double startY = mePlacement.getRightCenterY();
                double endX = rightSib.getLeftCenterX();
                double endY = rightSib.getLeftCenterY();
                linesGroup.getChildren().add(
                        createTreeConnectionLine(startX, startY, endX, endY,
                                HierarchyLayer.SIBLING.primaryColor, 2.4, false));
                linesGroup.getChildren().add(
                        createArrowHead(startX, startY, endX, endY, HierarchyLayer.SIBLING.primaryColor));
            }
        }

        // 4. "Me" -> Children (Branching descent lines down to Son & Daughter)
        if (mePlacement != null && !childNodes.isEmpty()) {
            for (NodePlacement child : childNodes) {
                linesGroup.getChildren().add(
                        createTreeConnectionLine(mePlacement.getBottomCenterX(), mePlacement.getBottomCenterY(),
                                child.getTopCenterX(), child.getTopCenterY(),
                                HierarchyLayer.CHILD.primaryColor, 2.4, false));
            }
        }

        // 5. Children / Me -> Neighbour (Dashed Emergency Handover link)
        if (!neighbourNodes.isEmpty()) {
            NodePlacement primaryNb = neighbourNodes.get(0);
            double sourceX = centerX;
            double sourceY = (childNodes.isEmpty() && mePlacement != null)
                    ? mePlacement.getBottomCenterY()
                    : (tier4Y + nodeH / 2.0);

            linesGroup.getChildren().add(
                    createTreeConnectionLine(sourceX, sourceY,
                            primaryNb.getTopCenterX(), primaryNb.getTopCenterY(),
                            HierarchyLayer.NEIGHBOUR.primaryColor, 2.2, true));

            // Badge pill on dashed line
            Label handoverBadge = new Label("🚨 Emergency Handover Link");
            handoverBadge.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: #FFE4E6;" +
                            "-fx-text-fill: #E11D48;" +
                            "-fx-font-size: 9px;" +
                            "-fx-font-weight: 800;" +
                            "-fx-padding: 2 8;" +
                            "-fx-background-radius: 10px;" +
                            "-fx-border-color: #FECDD3;" +
                            "-fx-border-radius: 10px;");
            double badgeW = 142.0;
            handoverBadge.setLayoutX(centerX - badgeW / 2.0);
            handoverBadge.setLayoutY((sourceY + primaryNb.getTopCenterY()) / 2.0 - 9.0);
            linesGroup.getChildren().add(handoverBadge);
        }

        // ==========================================
        // RENDER CIRCULAR NODE CARDS
        // ==========================================
        for (NodePlacement np : allPlacements) {
            boolean isMe = (mePlacement != null && np.index == mePlacement.index);
            VBox nodeCard = createGraphNodeCard(np.index, np.member, np.layer, isMe);
            nodeCard.setLayoutX(np.x - np.width / 2.0);
            nodeCard.setLayoutY(np.y - np.height / 2.0);
            nodesGroup.getChildren().add(nodeCard);
        }

        // Layering: lines in back, labels watermark in middle, interactive cards on top
        treeCanvas.getChildren().addAll(linesGroup, labelsGroup, nodesGroup);
    }

    private Line createTreeConnectionLine(double startX, double startY, double endX, double endY, String colorHex,
            double width, boolean dashed) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.web(colorHex));
        line.setStrokeWidth(width);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        if (dashed) {
            line.getStrokeDashArray().addAll(6.0, 4.0);
        }
        return line;
    }

    private Polygon createArrowHead(double fromX, double fromY, double toX, double toY, String colorHex) {
        double arrowLength = 9.0;
        double dx = toX - fromX;
        double dy = toY - fromY;
        double angle = Math.atan2(dy, dx);

        double x1 = toX - arrowLength * Math.cos(angle - Math.toRadians(26));
        double y1 = toY - arrowLength * Math.sin(angle - Math.toRadians(26));
        double x2 = toX - arrowLength * Math.cos(angle + Math.toRadians(26));
        double y2 = toY - arrowLength * Math.sin(angle + Math.toRadians(26));

        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(
                toX, toY,
                x1, y1,
                x2, y2);
        arrow.setFill(Color.web(colorHex));
        return arrow;
    }

    private HBox createSideLevelLabel(HierarchyLayer layer, String title, double x, double y) {
        HBox badge = new HBox(6);
        badge.setAlignment(Pos.CENTER_LEFT);
        badge.setPadding(new Insets(4, 8, 4, 8));
        badge.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + layer.bgLight + ";" +
                        "-fx-border-color: " + layer.borderLight + ";" +
                        "-fx-border-radius: 12px;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-width: 1.2px;");

        Label iconLbl = new Label(layer.icon);
        iconLbl.setStyle("-fx-font-size: 11px;");

        Label titleLbl = new Label(title);
        titleLbl.setStyle(
                FONT_FAMILY + "-fx-font-size: 9.5px; -fx-font-weight: 800; -fx-text-fill: " + layer.darkColor + ";");

        badge.getChildren().addAll(iconLbl, titleLbl);
        badge.setLayoutX(x);
        badge.setLayoutY(y);
        return badge;
    }

    private VBox createGraphNodeCard(int index, MemberModel data, HierarchyLayer layer, boolean isMe) {
        VBox card = new VBox(6);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefWidth(156);
        card.setMinWidth(156);
        card.setMaxWidth(156);
        card.setPrefHeight(126);
        card.setMinHeight(126);
        card.setMaxHeight(126);
        card.setPadding(new Insets(10, 8, 8, 8));

        String borderClr = isMe ? "#2563EB" : layer.borderLight;
        double borderWidth = isMe ? 2.2 : 1.4;
        String bgClr = isMe ? "#FFFFFF" : SURFACE;

        card.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + bgClr + ";" +
                        "-fx-border-color: " + borderClr + ";" +
                        "-fx-border-width: " + borderWidth + "px;" +
                        "-fx-border-radius: 16px;" +
                        "-fx-background-radius: 16px;" +
                        "-fx-cursor: hand;");
        card.setEffect(new DropShadow(8, 0, 2, isMe ? Color.rgb(37, 99, 235, 0.18) : Color.rgb(0, 0, 0, 0.05)));

        String displayName = (data.getFullName() == null || data.getFullName().trim().isEmpty())
                ? (data.getMemberTag() != null ? data.getMemberTag() : "Member " + (index + 1))
                : data.getFullName();
        String initials = extractInitials(displayName);

        // Prominent 60px Circular Node with Distinct Layer Colors
        StackPane circularNode = new StackPane();
        circularNode.setPrefSize(60, 60);
        circularNode.setMinSize(60, 60);
        circularNode.setMaxSize(60, 60);

        Circle outerCircle = new Circle(30);
        outerCircle.setFill(Color.web(isMe ? "#EFF6FF" : layer.bgLight));
        outerCircle.setStroke(Color.web(isMe ? "#2563EB" : layer.primaryColor));
        outerCircle.setStrokeWidth(isMe ? 3.0 : 2.5);

        Label initialsLbl = new Label(initials);
        initialsLbl.setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: 900; -fx-text-fill: "
                + (isMe ? "#1D4ED8" : layer.darkColor) + ";");

        // Small heartbeat/health status pulse dot
        Circle statusDot = new Circle(4.5);
        statusDot.setFill(Color.web(GREEN));
        statusDot.setStroke(Color.WHITE);
        statusDot.setStrokeWidth(1.4);
        StackPane.setAlignment(statusDot, Pos.TOP_RIGHT);
        StackPane.setMargin(statusDot, new Insets(2, 2, 0, 0));

        circularNode.getChildren().addAll(outerCircle, initialsLbl, statusDot);
        circularNode.setEffect(new DropShadow(8, 0, 2, Color.web(isMe ? "#2563EB" : layer.primaryColor, 0.25)));

        // Relationship Role Badge or "⭐ ME (SELF)"
        String relationText = isMe ? "⭐ ME (SELF)"
                : ((data.getRelation() != null && !data.getRelation().trim().isEmpty())
                        ? data.getRelation()
                        : (data.getMemberTag() != null ? data.getMemberTag() : "Member"));

        Label relBadge = new Label(relationText);
        relBadge.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + (isMe ? "#EFF6FF" : layer.bgLight) + ";" +
                        "-fx-text-fill: " + (isMe ? "#2563EB" : layer.primaryColor) + ";" +
                        "-fx-font-size: 9px;" +
                        "-fx-font-weight: 800;" +
                        "-fx-padding: 2 6;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: " + (isMe ? "#BFDBFE" : layer.borderLight) + ";" +
                        "-fx-border-radius: 8px;");

        // Name Label
        Label nameLbl = new Label(displayName);
        nameLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY
                + "; -fx-text-alignment: center;");
        nameLbl.setWrapText(false);
        nameLbl.setMaxWidth(140);
        nameLbl.setAlignment(Pos.CENTER);

        // Blood Group Pill
        Label bloodBadge = new Label(data.getBloodGroup() != null ? data.getBloodGroup() : "O+");
        bloodBadge.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: #FEE2E2;" +
                        "-fx-text-fill: " + RED + ";" +
                        "-fx-font-size: 8.5px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 1 5;" +
                        "-fx-background-radius: 5px;");

        // Quick Age
        String ageText = (data.getAge() != null && !data.getAge().trim().isEmpty()) ? data.getAge() + " yrs" : "";
        Label ageLbl = new Label(ageText);
        ageLbl.setStyle(FONT_FAMILY + "-fx-font-size: 9px; -fx-text-fill: " + TEXT_MUTED + ";");

        HBox metaRow = new HBox(5, relBadge, bloodBadge, ageLbl);
        metaRow.setAlignment(Pos.CENTER);

        card.getChildren().addAll(circularNode, metaRow, nameLbl);

        // Hover animation
        card.setOnMouseEntered(e -> {
            card.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: " + (isMe ? "#EFF6FF" : layer.bgLight) + ";" +
                            "-fx-border-color: " + (isMe ? "#1D4ED8" : layer.primaryColor) + ";" +
                            "-fx-border-width: 2.2px;" +
                            "-fx-border-radius: 16px;" +
                            "-fx-background-radius: 16px;" +
                            "-fx-cursor: hand;");
            card.setTranslateY(-3);
            ScaleTransition st = new ScaleTransition(Duration.millis(140), circularNode);
            st.setToX(1.10);
            st.setToY(1.10);
            st.play();
        });

        card.setOnMouseExited(e -> {
            card.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: " + bgClr + ";" +
                            "-fx-border-color: " + borderClr + ";" +
                            "-fx-border-width: " + borderWidth + "px;" +
                            "-fx-border-radius: 16px;" +
                            "-fx-background-radius: 16px;" +
                            "-fx-cursor: hand;");
            card.setTranslateY(0);
            ScaleTransition st = new ScaleTransition(Duration.millis(140), circularNode);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        // Click on node opens patient details screen!
        card.setOnMouseClicked(e -> {
            selectedMemberIndex = index;
            loadMemberIntoForm(selectedMemberIndex);
            showDetailsView();
        });

        return card;
    }

    private void handleAddNewMember() {
        int nextMemberNumber = memberDataList.size() + 1;
        String newMemberTag = "Member " + nextMemberNumber;
        String docId = "mem_" + UUID.randomUUID().toString().substring(0, 8);

        MemberModel newMember = new MemberModel(
                docId,
                newMemberTag,
                "New Member " + nextMemberNumber,
                "O+",
                "25",
                "Male",
                "None / No Known Allergies",
                "60 kg",
                "",
                "",
                "None / Healthy",
                null,
                "No file attached",
                "Sister");

        memberDataList.add(newMember);
        selectedMemberIndex = memberDataList.size() - 1;

        // Persist to Firebase and immediately open form
        controller.saveMember(loggedInFamilyEmail, newMember, () -> {
            renderFamilyTree();
            loadMemberIntoForm(selectedMemberIndex);
            showDetailsView();
            nameField.requestFocus();
            showStripToast("Added " + newMemberTag + ". Fill details in form.", ToastType.SUCCESS);
        }, err -> showStripToast(err, ToastType.WARNING));
    }

    private void handleRemoveMember(int index) {
        if (memberDataList.size() <= 1) {
            showStripToast("Cannot delete the primary member profile", ToastType.WARNING);
            return;
        }

        MemberModel toDelete = memberDataList.get(index);
        String memberId = toDelete.getId();
        String removedTag = toDelete.getMemberTag();

        memberDataList.remove(index);

        for (int i = 0; i < memberDataList.size(); i++) {
            memberDataList.get(i).setMemberTag("Member " + (i + 1));
        }

        if (selectedMemberIndex >= memberDataList.size()) {
            selectedMemberIndex = memberDataList.size() - 1;
        }

        controller.deleteMember(loggedInFamilyEmail, memberId, () -> {
            controller.syncAllMembers(loggedInFamilyEmail, memberDataList, () -> {
            }, err -> {
            });
            renderFamilyTree();
            loadMemberIntoForm(selectedMemberIndex);
            showTreeView();
            showStripToast(removedTag + " removed from Health Vault", ToastType.INFO);
        }, err -> showStripToast(err, ToastType.WARNING));
    }

    // =========================================================
    // RIGHT SIDE: DETAILS AND HEALTH PARAMETERS PANEL
    // =========================================================
    private VBox createDetailsPanel() {
        VBox details = new VBox(16);
        details.setPadding(new Insets(22, 28, 22, 28));
        details.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 16px;" +
                        "-fx-background-radius: 16px;");
        details.setEffect(new DropShadow(10, 0, 3, Color.rgb(120, 47, 22, 0.05)));

        // Top Navigation Row: [← Back to Family Tree] and Quick Member Switcher
        HBox topNavRow = new HBox(14);
        topNavRow.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("← Back to Family Tree");
        backBtn.setPrefHeight(34);
        backBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                        "-fx-text-fill: " + PRIMARY_DARK + ";" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                        "-fx-border-radius: 8px;" +
                        "-fx-padding: 0 14px;" +
                        "-fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            saveCurrentFormData(false);
            showTreeView();
        });

        Label quickLbl = new Label("Quick Switch Patient:");
        quickLbl.setStyle(
                FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");

        memberQuickSwitcherBar = new HBox(8);
        memberQuickSwitcherBar.setAlignment(Pos.CENTER_LEFT);

        ScrollPane quickScroll = new ScrollPane(memberQuickSwitcherBar);
        quickScroll.setFitToHeight(true);
        quickScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        quickScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        quickScroll.setStyle(
                "-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        HBox.setHgrow(quickScroll, Priority.ALWAYS);

        topNavRow.getChildren().addAll(backBtn, quickLbl, quickScroll);

        // Hero Profile Header
        HBox profileBanner = new HBox(14);
        profileBanner.setAlignment(Pos.CENTER_LEFT);

        bigAvatar = new StackPane();
        bigAvatar.setPrefSize(48, 48);
        bigAvatar.setMinSize(48, 48);
        bigAvatar.setMaxSize(48, 48);
        bigAvatar.setStyle("-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK
                + "); -fx-background-radius: 24px;");
        Label bigAvatarTxt = new Label("👤");
        bigAvatarTxt.setStyle("-fx-font-size: 20px;");
        bigAvatar.getChildren().add(bigAvatarTxt);

        VBox titleCol = new VBox(2);
        HBox.setHgrow(titleCol, Priority.ALWAYS);

        HBox titleRow = new HBox(8);
        titleRow.setAlignment(Pos.CENTER_LEFT);
        detailsTitle = new Label("Medical Details: Sarah Miller");
        detailsTitle.setStyle(
                FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        bloodGroupPill = new Label("O+ Blood Type");
        bloodGroupPill.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                        "-fx-text-fill: " + PRIMARY_DARK + ";" +
                        "-fx-font-size: 10.5px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 3 8;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                        "-fx-border-radius: 10px;");
        titleRow.getChildren().addAll(detailsTitle);

        // detailsSubtitle = new Label("Configured emergency medical directives for
        // hospital emergency trauma admission.");
        // detailsSubtitle.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill:
        // " + TEXT_MUTED + ";");
        titleCol.getChildren().addAll(titleRow);

        profileBanner.getChildren().addAll(titleCol);

        // Section 1: Biometrics & Demographics
        Label sec1Label = createSectionHeader("1. BIOMETRICS & BASIC PARAMETERS");
        HBox row1 = new HBox(14);
        VBox nameBox = createFieldGroup("FULL LEGAL NAME", nameField = new TextField(), "e.g. Sarah Miller");

        relationCombo = new ComboBox<>();
        relationCombo.getItems().addAll(
                "Grandfather", "Grandmother",
                "Father", "Mother",
                "Brother", "Sister",
                "Self", "Spouse / Partner",
                "Son", "Daughter", "Child",
                "Neighbour / Emergency Handover",
                "Other Relative");
        relationCombo.setOnAction(e -> {
            if (selectedMemberIndex >= 0 && selectedMemberIndex < memberDataList.size()) {
                String val = relationCombo.getValue();
                if (val != null) {
                    memberDataList.get(selectedMemberIndex).setRelation(val);
                    renderFamilyTree();
                    HierarchyLayer l = getMemberLayer(memberDataList.get(selectedMemberIndex));
                    if (bigAvatar != null) {
                        bigAvatar.setStyle("-fx-background-color: linear-gradient(to bottom right, " + l.primaryColor
                                + ", " + l.darkColor + "); -fx-background-radius: 24px;");
                    }
                    updateQuickSwitcher();
                }
            }
        });
        VBox relationBox = createComboGroup("RELATION / HIERARCHY ROLE", relationCombo);
        relationBox.setPrefWidth(210);
        relationBox.setMinWidth(210);

        bloodGroupCombo = new ComboBox<>();
        bloodGroupCombo.getItems().addAll("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-", "Unknown");
        VBox bloodBox = createComboGroup("BLOOD GROUP", bloodGroupCombo);
        bloodBox.setPrefWidth(130);
        bloodBox.setMinWidth(130);

        HBox.setHgrow(nameBox, Priority.ALWAYS);
        row1.getChildren().addAll(nameBox, relationBox, bloodBox);

        HBox row2 = new HBox(14);
        VBox ageBox = createFieldGroup("AGE (YEARS)", ageField = new TextField(), "e.g. 28");
        genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Male", "Female", "Other");
        VBox genderBox = createComboGroup("GENDER", genderCombo);
        VBox weightBox = createFieldGroup("WEIGHT (KG)", weightField = new TextField(), "e.g. 62 kg");

        HBox.setHgrow(ageBox, Priority.ALWAYS);
        HBox.setHgrow(genderBox, Priority.ALWAYS);
        HBox.setHgrow(weightBox, Priority.ALWAYS);
        row2.getChildren().addAll(ageBox, genderBox, weightBox);

        // Section 2: Clinical Safety & Conditions
        Label sec2Label = createSectionHeader("2. CLINICAL ALERTS & CHRONIC CONDITIONS");
        HBox row3 = new HBox(14);

        allergiesCombo = new ComboBox<>();
        allergiesCombo.getItems().addAll(
                "None / No Known Allergies",
                "Penicillin",
                "Sulfa Drugs / Sulfonamides",
                "NSAIDs / Aspirin",
                "Latex",
                "Peanuts / Tree Nuts",
                "Contrast Dye (Iodine)",
                "Local / General Anesthetics",
                "Other (See Notes)");
        VBox allergyBox = createComboGroup("KNOWN DRUG / SUBSTANCE ALLERGIES", allergiesCombo);

        chronicConditionsCombo = new ComboBox<>();
        chronicConditionsCombo.getItems().addAll(
                "None / Healthy",
                "Diabetes Mellitus (Type 1 / 2)",
                "Hypertension / High BP",
                "Asthma / Respiratory Disorders",
                "Coronary Artery Disease / Cardiac",
                "Epilepsy / Seizures",
                "Chronic Kidney Disease",
                "Thyroid Disorder");
        VBox chronicBox = createComboGroup("CHRONIC CONDITIONS / PRE-EXISTING", chronicConditionsCombo);

        HBox.setHgrow(allergyBox, Priority.ALWAYS);
        HBox.setHgrow(chronicBox, Priority.ALWAYS);
        row3.getChildren().addAll(allergyBox, chronicBox);

        // Section 3: Emergency Contacts
        Label sec3Label = createSectionHeader("3. EMERGENCY HANDOVER & FAMILY CONTACTS");
        HBox row4 = new HBox(14);
        VBox phoneBox = createFieldGroup("PRIMARY PHONE NO.", phoneField = new TextField(), "e.g. 9876543210");
        VBox emgBox = createFieldGroup("EMERGENCY RELATIVE CONTACT", emergencyContactField = new TextField(),
                "e.g. 9123456780");

        HBox.setHgrow(phoneBox, Priority.ALWAYS);
        HBox.setHgrow(emgBox, Priority.ALWAYS);
        row4.getChildren().addAll(phoneBox, emgBox);

        // Section 4: Document Vault
        VBox docVaultSection = createAllReportSection();

        // Action Toolbar
        HBox actionsRow = new HBox(12);
        actionsRow.setAlignment(Pos.CENTER_RIGHT);
        actionsRow.setPadding(new Insets(10, 0, 0, 0));

        Button deleteBtn = new Button("Delete Profile");
        deleteBtn.setPrefHeight(42);
        deleteBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: transparent;" +
                        "-fx-text-fill: " + RED + ";" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: #FCA5A5;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0 16px;");
        deleteBtn.setOnAction(e -> handleRemoveMember(selectedMemberIndex));

        Region actionSpacer = new Region();
        HBox.setHgrow(actionSpacer, Priority.ALWAYS);

        Button backToTreeBtn = new Button("← Back to Family Tree");
        backToTreeBtn.setPrefHeight(42);
        backToTreeBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: transparent;" +
                        "-fx-text-fill: " + TEXT_SECONDARY + ";" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 8px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0 16px;");
        backToTreeBtn.setOnAction(e -> {
            saveCurrentFormData(false);
            showTreeView();
        });

        Button viewOverallReportBtn = new Button("⤓  View Health Summary Dossier");
        viewOverallReportBtn.setPrefHeight(42);
        viewOverallReportBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                        "-fx-text-fill: " + PRIMARY_DARK + ";" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                        "-fx-border-radius: 8px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 0 18px;");
        viewOverallReportBtn.setOnAction(e -> handleDownloadOrViewReport());

        Button saveButton = createPrimaryButton("Save Member Profile");
        saveButton.setPrefWidth(160);
        saveButton.setOnAction(e -> saveCurrentFormData(true));

        actionsRow.getChildren().addAll(deleteBtn, actionSpacer, viewOverallReportBtn, saveButton);

        details.getChildren().addAll(
                topNavRow,
                createDivider(),
                profileBanner,
                createDivider(),
                sec1Label,
                row1,
                row2,
                createDivider(),
                sec2Label,
                row3,
                createDivider(),
                sec3Label,
                row4,
                createDivider(),
                docVaultSection,
                actionsRow);

        return details;
    }

    private void updateQuickSwitcher() {
        if (memberQuickSwitcherBar == null)
            return;
        memberQuickSwitcherBar.getChildren().clear();
        for (int i = 0; i < memberDataList.size(); i++) {
            final int idx = i;
            MemberModel m = memberDataList.get(i);
            HierarchyLayer layer = getMemberLayer(m);
            boolean isSelected = (i == selectedMemberIndex);

            HBox pill = new HBox(6);
            pill.setAlignment(Pos.CENTER_LEFT);
            pill.setPadding(new Insets(4, 10, 4, 6));
            pill.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: " + (isSelected ? layer.bgLight : SURFACE) + ";" +
                            "-fx-border-color: " + (isSelected ? layer.primaryColor : BORDER_COLOR) + ";" +
                            "-fx-border-width: " + (isSelected ? "1.5px" : "1px") + ";" +
                            "-fx-border-radius: 16px;" +
                            "-fx-background-radius: 16px;" +
                            "-fx-cursor: hand;");

            Circle dot = new Circle(10);
            dot.setFill(Color.web(isSelected ? layer.primaryColor : layer.borderLight));
            Label dotInit = new Label(extractInitials(m.getFullName()));
            dotInit.setStyle(FONT_FAMILY + "-fx-font-size: 8.5px; -fx-font-weight: bold; -fx-text-fill: "
                    + (isSelected ? "white" : layer.darkColor) + ";");
            StackPane dotStack = new StackPane(dot, dotInit);

            String name = (m.getFullName() != null && !m.getFullName().isEmpty()) ? m.getFullName() : m.getMemberTag();
            Label nameLbl = new Label(name);
            nameLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: " + (isSelected ? "bold" : "normal")
                    + "; -fx-text-fill: " + TEXT_PRIMARY + ";");

            pill.getChildren().addAll(dotStack, nameLbl);
            pill.setOnMouseClicked(e -> {
                saveCurrentFormData(false);
                selectedMemberIndex = idx;
                loadMemberIntoForm(selectedMemberIndex);
                updateQuickSwitcher();
                animateDetailsPanelTransition();
            });

            memberQuickSwitcherBar.getChildren().add(pill);
        }
    }

    private void animateDetailsPanelTransition() {
        if (detailsViewContainer == null)
            return;
        FadeTransition fade = new FadeTransition(Duration.millis(250), detailsViewContainer);
        fade.setFromValue(0.4);
        fade.setToValue(1.0);

        TranslateTransition tt = new TranslateTransition(Duration.millis(250), detailsViewContainer);
        tt.setFromX(12);
        tt.setToX(0);

        ParallelTransition pt = new ParallelTransition(fade, tt);
        pt.play();
    }

    private Label createSectionHeader(String title) {
        Label l = new Label(title);
        l.setStyle(
                FONT_FAMILY +
                        "-fx-font-size: 10px;" +
                        "-fx-font-weight: 800;" +
                        "-fx-text-fill: " + PRIMARY_DARK + ";" +
                        "-fx-letter-spacing: 0.5px;" +
                        "-fx-padding: 4 0 0 0;");
        return l;
    }

    private void loadMemberIntoForm(int index) {
        if (index < 0 || index >= memberDataList.size())
            return;
        MemberModel m = memberDataList.get(index);

        HierarchyLayer layer = getMemberLayer(m);
        String fullName = m.getFullName() == null || m.getFullName().isEmpty() ? "New Profile" : m.getFullName();
        detailsTitle.setText("Medical Details: " + fullName);
        bloodGroupPill.setText((m.getBloodGroup() != null ? m.getBloodGroup() : "O+") + " Blood Type");

        if (bigAvatar != null) {
            bigAvatar.setStyle("-fx-background-color: linear-gradient(to bottom right, " + layer.primaryColor + ", "
                    + layer.darkColor + "); -fx-background-radius: 24px;");
        }

        nameField.setText(m.getFullName() != null ? m.getFullName() : "");

        if (m.getRelation() != null && !m.getRelation().trim().isEmpty()) {
            relationCombo.setValue(m.getRelation());
        } else {
            switch (layer) {
                case GRANDPARENT:
                    relationCombo.setValue("Grandfather");
                    break;
                case PARENT:
                    relationCombo.setValue("Father");
                    break;
                case SIBLING:
                    relationCombo.setValue("Sister");
                    break;
                case CHILD:
                    relationCombo.setValue("Son");
                    break;
                case NEIGHBOUR:
                    relationCombo.setValue("Neighbour / Emergency Handover");
                    break;
                default:
                    relationCombo.setValue("Sister");
                    break;
            }
        }

        bloodGroupCombo.setValue(m.getBloodGroup() != null ? m.getBloodGroup() : "O+");
        ageField.setText(m.getAge() != null ? m.getAge() : "25");
        genderCombo.setValue(m.getGender() != null ? m.getGender() : "Male");
        allergiesCombo.setValue(m.getAllergy() != null ? m.getAllergy() : "None / No Known Allergies");
        weightField.setText(m.getWeight() != null ? m.getWeight() : "60 kg");
        phoneField.setText(m.getPhone() != null ? m.getPhone() : "");
        emergencyContactField.setText(m.getEmergencyContact() != null ? m.getEmergencyContact() : "");
        chronicConditionsCombo.setValue(m.getChronicCondition() != null ? m.getChronicCondition() : "None / Healthy");

        if (m.getLastReportType() != null && !m.getLastReportType().isEmpty()) {
            reportTypeCombo.setValue(m.getLastReportType());
        } else {
            reportTypeCombo.setValue(null);
            reportTypeCombo.setPromptText("Choose Document / Report Type...");
        }

        updateFileAttachmentDisplay(m.getUploadedFileName(), m.getLastReportType());
        updateQuickSwitcher();
    }

    private void updateFileAttachmentDisplay(String fileName, String reportType) {
        boolean hasFile = fileName != null && !fileName.isEmpty() && !fileName.equalsIgnoreCase("No file attached");

        if (hasFile) {
            String typeStr = (reportType != null && !reportType.isEmpty()) ? " (" + reportType + ")" : "";
            uploadedFileBadge.setText("✓ Attached Lab Report: " + fileName + typeStr);
            uploadedFileBadge.setStyle(
                    FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: bold; -fx-text-fill: " + GREEN + ";");
            removeFileButton.setVisible(true);
            removeFileButton.setManaged(true);
        } else {
            uploadedFileBadge.setText("No document attached yet");
            uploadedFileBadge.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " + TEXT_MUTED + ";");
            removeFileButton.setVisible(false);
            removeFileButton.setManaged(false);
        }
    }

    private void saveCurrentFormData(boolean notifyFirestore) {
        if (selectedMemberIndex < 0 || selectedMemberIndex >= memberDataList.size())
            return;
        MemberModel m = memberDataList.get(selectedMemberIndex);

        m.setFullName(nameField.getText().trim());
        m.setRelation(relationCombo.getValue() != null ? relationCombo.getValue() : "Sister");
        m.setBloodGroup(bloodGroupCombo.getValue() != null ? bloodGroupCombo.getValue() : "O+");
        m.setAge(ageField.getText().trim());
        m.setGender(genderCombo.getValue() != null ? genderCombo.getValue() : "Male");
        m.setAllergy(allergiesCombo.getValue() != null ? allergiesCombo.getValue() : "None / No Known Allergies");
        m.setWeight(weightField.getText().trim());
        m.setPhone(phoneField.getText().trim());
        m.setEmergencyContact(emergencyContactField.getText().trim());
        m.setChronicCondition(
                chronicConditionsCombo.getValue() != null ? chronicConditionsCombo.getValue() : "None / Healthy");

        if (reportTypeCombo.getValue() != null) {
            m.setLastReportType(reportTypeCombo.getValue());
        }

        if (notifyFirestore) {
            controller.saveMember(loggedInFamilyEmail, m, () -> {
                renderFamilyTree();
                updateQuickSwitcher();
                showStripToast("Medical record saved to Health Vault", ToastType.SUCCESS);
            }, err -> showStripToast(err, ToastType.WARNING));
        }
    }

    // =========================================================
    // REPORT ATTACHMENT SECTION
    // =========================================================
    private VBox createAllReportSection() {
        VBox section = new VBox(8);

        Label title = createSectionHeader("4. DIAGNOSTIC REPORTS & LAB DOCUMENT VAULT");

        HBox uploadRow = new HBox(12);
        uploadRow.setAlignment(Pos.CENTER_LEFT);

        reportTypeCombo = new ComboBox<>();
        reportTypeCombo.getItems().addAll(
                "Blood Test / Complete Blood Count (CBC)",
                "Discharge Summary & Surgery Notes",
                "ECG / Echocardiogram (Cardiology)",
                "MRI / CT Scan Diagnostic",
                "X-Ray Radiography",
                "Doctor's Prescription & Rx History",
                "Vaccination & Immunization Record",
                "Pathology & Biopsy Report");
        reportTypeCombo.setPromptText("Choose Document / Report Type...");
        reportTypeCombo.setPrefHeight(42);
        reportTypeCombo.setMaxWidth(Double.MAX_VALUE);
        reportTypeCombo.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + FIELD_BG + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-font-size: 12px;");
        HBox.setHgrow(reportTypeCombo, Priority.ALWAYS);

        Button uploadButton = new Button("📁  Attach File");
        uploadButton.setPrefWidth(130);
        uploadButton.setPrefHeight(42);
        uploadButton.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                        "-fx-border-color: " + PRIMARY + ";" +
                        "-fx-border-style: dashed;" +
                        "-fx-border-width: 1.5px;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-text-fill: " + PRIMARY_DARK + ";" +
                        "-fx-font-size: 11.5px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;");
        uploadButton.setOnAction(e -> chooseReportFile());

        uploadRow.getChildren().addAll(reportTypeCombo, uploadButton);

        HBox fileBadgeContainer = new HBox(10);
        fileBadgeContainer.setAlignment(Pos.CENTER_LEFT);
        fileBadgeContainer.setPadding(new Insets(2, 4, 0, 4));

        uploadedFileBadge = new Label("No document attached yet");
        uploadedFileBadge.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " + TEXT_MUTED + ";");

        removeFileButton = new Button("✕ Remove");
        removeFileButton.setVisible(false);
        removeFileButton.setManaged(false);
        removeFileButton.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + LIGHT_RED + ";" +
                        "-fx-text-fill: " + RED + ";" +
                        "-fx-font-size: 10px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6px;" +
                        "-fx-padding: 3 8;" +
                        "-fx-cursor: hand;");
        removeFileButton.setOnAction(e -> handleRemoveUploadedFile());

        fileBadgeContainer.getChildren().addAll(uploadedFileBadge, removeFileButton);

        section.getChildren().addAll(title, uploadRow, fileBadgeContainer);
        return section;
    }

    private void chooseReportFile() {
        if (reportTypeCombo.getValue() == null || reportTypeCombo.getValue().trim().isEmpty()) {
            showStripToast("Please choose a report category first", ToastType.WARNING);
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Diagnostic Report Document");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Medical Documents", "*.pdf", "*.png",
                "*.jpg", "*.jpeg", "*.doc", "*.docx"));

        File file = chooser.showOpenDialog(stage);
        if (file != null) {
            String fileName = file.getName();
            String selectedType = reportTypeCombo.getValue();
            MemberModel m = memberDataList.get(selectedMemberIndex);

            m.setUploadedFileName(fileName);
            m.setLastReportType(selectedType);

            updateFileAttachmentDisplay(fileName, selectedType);

            controller.saveMember(loggedInFamilyEmail, m, () -> {
                showStripToast("Attached '" + fileName + "' to Cloud Vault", ToastType.SUCCESS);
            }, err -> showStripToast(err, ToastType.WARNING));
        }
    }

    private void handleRemoveUploadedFile() {
        if (selectedMemberIndex < 0 || selectedMemberIndex >= memberDataList.size())
            return;
        MemberModel m = memberDataList.get(selectedMemberIndex);

        m.setUploadedFileName("No file attached");
        m.setLastReportType(null);
        reportTypeCombo.setValue(null);
        reportTypeCombo.setPromptText("Choose Document / Report Type...");

        updateFileAttachmentDisplay("No file attached", null);

        controller.saveMember(loggedInFamilyEmail, m, () -> {
            showStripToast("Attachment removed from Cloud Vault", ToastType.INFO);
        }, err -> showStripToast(err, ToastType.WARNING));
    }

    private void handleDownloadOrViewReport() {
        saveCurrentFormData(false);
        MemberModel m = memberDataList.get(selectedMemberIndex);

        String summary = "=====================================================\n" +
                "        LIFELINK COMPREHENSIVE FAMILY HEALTH DOSSIER \n" +
                "=====================================================\n" +
                "Patient Identifier : " + m.getMemberTag() + " (" + m.getFullName() + ")\n" +
                "Demographics       : " + m.getAge() + " yrs | " + m.getGender() + " | " + m.getWeight() + "\n" +
                "Blood Group        : " + m.getBloodGroup() + "\n" +
                "Primary Contact    : " + m.getPhone() + "\n" +
                "Emergency Handover : " + m.getEmergencyContact() + "\n" +
                "-----------------------------------------------------\n" +
                "CRITICAL MEDICAL FLAGS FOR EMERGENCY ADMISSION:\n" +
                "• Known Allergies  : " + m.getAllergy() + "\n" +
                "• Chronic Diseases : " + m.getChronicCondition() + "\n" +
                "• Latest Report    : " + (m.getLastReportType() != null ? m.getLastReportType() : "None Specified")
                + "\n" +
                "• File Attachment  : " + m.getUploadedFileName() + "\n" +
                "=====================================================\n" +
                "Security Status: LifeLink Verified Encrypted Record.\n" +
                "Ready for direct hospital ambulance handover transfer.";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Medical History Overview");
        alert.setHeaderText("Clinical Summary for " + m.getFullName());

        TextArea textArea = new TextArea(summary);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(280);
        textArea.setStyle(FONT_FAMILY + "-fx-font-family: 'Consolas', 'Courier New', monospace; -fx-font-size: 12px;");

        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    // =========================================================
    // SINGLE STRIP TOAST NOTIFICATION (TOP RIGHT CORNER)
    // =========================================================
    private enum ToastType {
        SUCCESS, INFO, WARNING
    }

    private void showStripToast(String messageText, ToastType type) {
        HBox toastStrip = new HBox(10);
        toastStrip.setAlignment(Pos.CENTER_LEFT);
        toastStrip.setMaxWidth(360);
        toastStrip.setPrefWidth(360);
        toastStrip.setMinHeight(44);
        toastStrip.setPrefHeight(44);
        toastStrip.setMaxHeight(44);
        toastStrip.setPadding(new Insets(0, 14, 0, 14));

        String iconSymbol = switch (type) {
            case SUCCESS -> "✓";
            case WARNING -> "⚠";
            case INFO -> "ℹ";
        };

        String accentColor = switch (type) {
            case SUCCESS -> GREEN;
            case WARNING -> RED;
            case INFO -> PRIMARY;
        };

        Label icon = new Label(iconSymbol);
        icon.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + accentColor + ";");

        Label message = new Label(messageText);
        message.setStyle(
                FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");
        HBox.setHgrow(message, Priority.ALWAYS);

        toastStrip.getChildren().addAll(icon, message);

        toastStrip.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + accentColor + " " + BORDER_COLOR + " " + BORDER_COLOR + " " + accentColor
                        + ";" +
                        "-fx-border-width: 0 1px 1px 4px;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(43,33,29,0.14), 10, 0, 0, 3);");

        StackPane.setAlignment(toastStrip, Pos.TOP_RIGHT);
        StackPane.setMargin(toastStrip, new Insets(16, 22, 0, 0));

        rootStack.getChildren().add(toastStrip);

        toastStrip.setOpacity(0);
        toastStrip.setTranslateY(-15);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), toastStrip);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(200), toastStrip);
        slideIn.setFromY(-15);
        slideIn.setToY(0);

        ParallelTransition appear = new ParallelTransition(fadeIn, slideIn);
        PauseTransition pause = new PauseTransition(Duration.seconds(2.6));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), toastStrip);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        TranslateTransition slideOut = new TranslateTransition(Duration.millis(250), toastStrip);
        slideOut.setFromY(0);
        slideOut.setToY(-15);

        ParallelTransition disappear = new ParallelTransition(fadeOut, slideOut);
        disappear.setOnFinished(e -> rootStack.getChildren().remove(toastStrip));

        appear.setOnFinished(e -> pause.play());
        pause.setOnFinished(e -> disappear.play());

        appear.play();
    }

    // =========================================================
    // UI BUILDER HELPERS
    // =========================================================
    private VBox createFieldGroup(String labelText, TextField field, String placeholder) {
        VBox box = new VBox(4);
        Label label = new Label(labelText);
        label.setStyle(
                FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        field.setPromptText(placeholder);
        field.setPrefHeight(40);
        field.setMaxWidth(Double.MAX_VALUE);
        field.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + FIELD_BG + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-font-size: 12.5px;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                        "-fx-padding: 0 10px;");
        box.getChildren().addAll(label, field);
        return box;
    }

    private VBox createComboGroup(String labelText, ComboBox<String> combo) {
        VBox box = new VBox(4);
        Label label = new Label(labelText);
        label.setStyle(
                FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        combo.setPrefHeight(40);
        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + FIELD_BG + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-font-size: 12px;");
        box.getChildren().addAll(label, combo);
        return box;
    }

    private Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.setPrefHeight(42);
        button.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + ");"
                        +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.25), 8, 0, 0, 2);");
        button.setOnMouseEntered(e -> button.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + PRIMARY_DARK + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + ");"
                        +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.25), 8, 0, 0, 2);"));
        return button;
    }

    private Region createDivider() {
        Region divider = new Region();
        divider.setPrefHeight(1);
        divider.setMaxWidth(Double.MAX_VALUE);
        divider.setStyle("-fx-background-color: " + DIVIDER + ";");
        return divider;
    }

    private ScrollPane createMainScrollPane(VBox content) {
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(true);
        scrollPane.setStyle(
                "-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");
        return scrollPane;
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

    private void animateNode(Node node) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(140), node);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.12);
        scale.setToY(1.12);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }

    private static String extractInitials(String name) {
        if (name == null || name.trim().isEmpty())
            return "M";
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) {
            return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
        }
        return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
    }
}