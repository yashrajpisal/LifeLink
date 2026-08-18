package com.kurukshetra.view.hospital;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ResourceManagementView extends VBox {

    // ─────────────────────────────────────────────────────────────────────────
    //  Colour palette
    // ─────────────────────────────────────────────────────────────────────────
    private static final String PAGE_BG        = "#F0F4F8";
    private static final String CARD_BG        = "#FFFFFF";
    private static final String HEADER_BG      = "#FFFFFF";
    private static final String BORDER         = "#E2E8F0";
    private static final String TEXT_PRIMARY   = "#1A202C";
    private static final String TEXT_SECONDARY = "#64748B";
    private static final String TEXT_MUTED     = "#94A3B8";
    private static final String BLUE           = "#1565C0";
    private static final String ORANGE         = "#D97706";
    private static final String GREEN          = "#16A34A";
    private static final String RED            = "#DC2626";
    private static final String TEAL           = "#059669";
    private static final String INDIGO         = "#1D4ED8";
    private static final String PURPLE         = "#7C3AED";

    // tag combos [bg, fg, text]
    private static final String[] TAG_LOW  = { "#FEF2F2", RED, "LOW STOCK" };
    private static final String[] TAG_WARN = { "#FFF7ED", ORANGE, "WARNING" };
    private static final String[] TAG_OPT  = { "#F0FDF4", GREEN, "OPTIMAL" };
    private static final String[] TAG_OPER = { "#F0FDF4", GREEN, "OPERATIONAL" };

    // donut colours
    private static final String DONUT_ICU     = "#1565C0";
    private static final String DONUT_EMERG   = "#38BDF8";
    private static final String DONUT_GENERAL = "#94A3B8";

    // log dot colours
    private static final String DOT_BLUE   = "#3B82F6";
    private static final String DOT_ORANGE = "#F59E0B";
    private static final String DOT_GREY   = "#6B7280";

    // ─────────────────────────────────────────────────────────────────────────
    //  Constructor — builds the full view as a VBox node
    // ─────────────────────────────────────────────────────────────────────────
    public ResourceManagementView() {
        super(0);
        getChildren().add(buildMainContent());
        setStyle("-fx-background-color: " + PAGE_BG + ";");
        VBox.setVgrow(this, Priority.ALWAYS);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Main content  (top-bar + scrollable body)
    // ─────────────────────────────────────────────────────────────────────────
    private VBox buildMainContent() {
        VBox wrapper = new VBox(0);
        wrapper.setStyle("-fx-background-color: " + PAGE_BG + ";");

        HBox topBar = buildTopBar();
        topBar.setStyle(
            "-fx-background-color: " + HEADER_BG + ";" +
            "-fx-border-color: transparent transparent " + BORDER + " transparent;" +
            "-fx-border-width: 0 0 1 0;"
        );

        ScrollPane scroll = new ScrollPane(buildBody());
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background: " + PAGE_BG + "; -fx-background-color: " + PAGE_BG + ";");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        wrapper.getChildren().addAll(topBar, scroll);
        return wrapper;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Top bar
    // ─────────────────────────────────────────────────────────────────────────
    private HBox buildTopBar() {
        HBox searchBox = new HBox(6);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(0, 10, 0, 10));
        searchBox.setPrefHeight(36);
        searchBox.setPrefWidth(280);
        searchBox.setMaxWidth(320);
        searchBox.setStyle(
            "-fx-background-color: #F8FAFC;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );

        Label searchIcon = new Label("\uD83D\uDD0D");
        searchIcon.setFont(Font.font("System", FontWeight.BOLD, 12));

        TextField searchField = new TextField();
        searchField.setPromptText("Search resources...");
        searchField.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;" +
            "-fx-font-size: 13;" +
            "-fx-text-fill: " + TEXT_SECONDARY + ";"
        );
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchBox.getChildren().addAll(searchIcon, searchField);

        Button notifBtn = iconBtn("\uD83D\uDD14");
        Button settBtn  = iconBtn("\u2699");

        Region divider = new Region();
        divider.setPrefSize(1, 28);
        divider.setStyle("-fx-background-color: " + BORDER + ";");

        Label avatar = new Label("\uD83D\uDC64");
        avatar.setFont(Font.font("System", FontWeight.BOLD, 18));
        avatar.setStyle(
            "-fx-background-color: #CBD5E1;" +
            "-fx-background-radius: 18;" +
            "-fx-padding: 4 7 4 7;"
        );

        Label nameL = new Label("Admin Portal");
        nameL.setFont(Font.font("System", FontWeight.BOLD, 13));
        nameL.setTextFill(Color.web(TEXT_PRIMARY));

        Label roleL = new Label("Systems Overseer");
        roleL.setFont(Font.font("System", FontWeight.BOLD, 11));
        roleL.setTextFill(Color.web(TEXT_SECONDARY));

        VBox profileTxt = new VBox(1, nameL, roleL);

        HBox profile = new HBox(8, avatar, profileTxt);
        profile.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bar = new HBox(12, searchBox, spacer, notifBtn, settBtn, divider, profile);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(10, 20, 10, 20));
        bar.setPrefHeight(58);
        return bar;
    }

    private Button iconBtn(String icon) {
        Button b = new Button(icon);
        b.setFont(Font.font("System", FontWeight.BOLD, 15));
        b.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;" +
            "-fx-padding: 4 8 4 8;" +
            "-fx-cursor: hand;"
        );
        b.setOnMouseEntered(e -> b.setStyle(
            "-fx-background-color: #F1F5F9;" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: transparent;" +
            "-fx-padding: 4 8 4 8;" +
            "-fx-cursor: hand;"
        ));
        b.setOnMouseExited(e -> b.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;" +
            "-fx-padding: 4 8 4 8;" +
            "-fx-cursor: hand;"
        ));
        return b;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Body
    // ─────────────────────────────────────────────────────────────────────────
    private VBox buildBody() {
        VBox body = new VBox(22);
        body.setPadding(new Insets(24, 24, 32, 24));
        body.setStyle("-fx-background-color: " + PAGE_BG + ";");
        body.getChildren().addAll(
            buildPageHeader(),
            buildInventorySection(),
            buildBottomSection()
        );
        return body;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Page header
    // ─────────────────────────────────────────────────────────────────────────
    private HBox buildPageHeader() {
        Label title = new Label("Resource Management");
        title.setFont(Font.font("System", FontWeight.BOLD, 26));
        title.setTextFill(Color.web("#1A2456"));

        Label sub = new Label("Real-time status and allocation tracking for hospital critical assets.");
        sub.setFont(Font.font("System", FontWeight.BOLD, 13));
        sub.setTextFill(Color.web(TEXT_SECONDARY));

        VBox titleBox = new VBox(4, title, sub);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button histBtn = outlineBtn("\uD83D\uDD51  View History");
        Button updBtn  = outlineBtn("\uD83D\uDD04  Update Availability");
        Button addBtn  = primaryBtn("\uFF0B  Add Resource");

        HBox buttons = new HBox(10, histBtn, updBtn, addBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        HBox header = new HBox(16, titleBox, spacer, buttons);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private Button outlineBtn(String text) {
        Button b = new Button(text);
        b.setFont(Font.font("System", FontWeight.BOLD, 13));
        b.setTextFill(Color.web(TEXT_PRIMARY));
        String normalStyle =
            "-fx-background-color: " + CARD_BG + ";" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 8 16 8 16;" +
            "-fx-cursor: hand;";
        String hoverStyle =
            "-fx-background-color: #F1F5F9;" +
            "-fx-border-color: #94A3B8;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 8 16 8 16;" +
            "-fx-cursor: hand;";
        b.setStyle(normalStyle);
        b.setOnMouseEntered(e -> b.setStyle(hoverStyle));
        b.setOnMouseExited(e  -> b.setStyle(normalStyle));
        return b;
    }

    private Button primaryBtn(String text) {
        Button b = new Button(text);
        b.setFont(Font.font("System", FontWeight.BOLD, 13));
        b.setTextFill(Color.WHITE);
        String normalStyle =
            "-fx-background-color: " + BLUE + ";" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 8 18 8 18;" +
            "-fx-cursor: hand;";
        String hoverStyle =
            "-fx-background-color: #0D47A1;" +   // deeper blue on hover
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 8 18 8 18;" +
            "-fx-cursor: hand;";
        b.setStyle(normalStyle);
        b.setOnMouseEntered(e -> b.setStyle(hoverStyle));
        b.setOnMouseExited(e  -> b.setStyle(normalStyle));
        return b;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Critical Inventory Status  (3 col x 2 row grid)
    // ─────────────────────────────────────────────────────────────────────────
    private VBox buildInventorySection() {
        Label sectionTitle = new Label("CRITICAL INVENTORY STATUS");
        sectionTitle.setFont(Font.font("System", FontWeight.BOLD, 11));
        sectionTitle.setTextFill(Color.web(TEXT_MUTED));

        Label syncLbl = new Label("Last sync:  2 mins ago");
        syncLbl.setFont(Font.font("System", FontWeight.BOLD, 12));
        syncLbl.setTextFill(Color.web(TEXT_MUTED));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        HBox sectionHdr = new HBox(sectionTitle, sp, syncLbl);
        sectionHdr.setAlignment(Pos.CENTER_LEFT);

        GridPane grid = new GridPane();
        grid.setHgap(14);
        grid.setVgap(14);

        for (int i = 0; i < 3; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(33.33);
            cc.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(cc);
        }

        // Row 0
        grid.add(bedCard("\uD83D\uDECF", "#EFF6FF", BLUE, "ICU Beds",
                         "42", "50", "available", TAG_LOW,  BLUE,    42, 50), 0, 0);
        grid.add(bedCard("\u2733", "#FFF7ED", ORANGE, "Emergency Beds",
                         "18", "30", "available", TAG_WARN, ORANGE, 18, 30), 1, 0);
        grid.add(bedCard("\uD83D\uDECC", "#F1F5F9", "#475569", "General Beds",
                         "312", "400", "available", TAG_OPT, "#334155", 312, 400), 2, 0);

        // Row 1
        grid.add(bedCard("\uD83D\uDCA8", "#EFF6FF", BLUE, "Ventilators",
                         "12", "15", "available", TAG_OPER, BLUE, 12, 15), 0, 1);
        grid.add(oxygenCard(), 1, 1);
        grid.add(bloodCard(),  2, 1);

        return new VBox(10, sectionHdr, grid);
    }

    /** Generic bed / resource card. */
    private VBox bedCard(String iconEmoji, String iconBg, String iconFg,
                          String name, String count, String total, String unit,
                          String[] tag, String barColor,
                          int current, int max) {
        VBox card = card();

        Label icon = emojiIcon(iconEmoji, iconBg, iconFg);
        Region sp  = hSpacer();
        Label  t   = statusTag(tag[2], tag[0], tag[1]);

        HBox topRow = new HBox(8, icon, sp, t);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label nameLbl = new Label(name);
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        nameLbl.setTextFill(Color.web(TEXT_SECONDARY));
        VBox.setMargin(nameLbl, new Insets(10, 0, 2, 0));

        Label cntLbl = new Label(count);
        cntLbl.setFont(Font.font("System", FontWeight.BOLD, 28));
        cntLbl.setTextFill(Color.web(TEXT_PRIMARY));

        Label totLbl = new Label(" / " + total + " " + unit);
        totLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        totLbl.setTextFill(Color.web(TEXT_SECONDARY));

        HBox cntRow = new HBox(2, cntLbl, totLbl);
        cntRow.setAlignment(Pos.BASELINE_LEFT);

        StackPane bar = progressBar((double) current / max, barColor);
        VBox.setMargin(bar, new Insets(10, 0, 0, 0));

        card.getChildren().addAll(topRow, nameLbl, cntRow, bar);
        return card;
    }

    /** Oxygen Reserves card. */
    private VBox oxygenCard() {
        VBox card = card();

        Label icon = emojiIcon("\uD83D\uDCCB", "#F1F5F9", "#475569");
        Label tagLbl = statusTag("SAFE RANGE", "#ECFDF5", TEAL);

        HBox topRow = new HBox(8, icon, hSpacer(), tagLbl);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label nameLbl = new Label("Oxygen Reserves");
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        nameLbl.setTextFill(Color.web(TEXT_SECONDARY));
        VBox.setMargin(nameLbl, new Insets(10, 0, 2, 0));

        Label cntLbl = new Label("98");
        cntLbl.setFont(Font.font("System", FontWeight.BOLD, 28));
        cntLbl.setTextFill(Color.web(TEXT_PRIMARY));

        Label unitLbl = new Label("% capacity");
        unitLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        unitLbl.setTextFill(Color.web(TEXT_SECONDARY));

        HBox cntRow = new HBox(2, cntLbl, unitLbl);
        cntRow.setAlignment(Pos.BASELINE_LEFT);

        StackPane bar = progressBar(0.98, TEAL);
        VBox.setMargin(bar, new Insets(10, 0, 0, 0));

        card.getChildren().addAll(topRow, nameLbl, cntRow, bar);
        return card;
    }

    /** Blood Units card. */
    private VBox bloodCard() {
        VBox card = card();

        Label icon = emojiIcon("\uD83D\uDCA7", "#FEF2F2", RED); // Use standard water droplet instead of blood drop for font compatibility
        HBox topRow = new HBox(8, icon, hSpacer());
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label nameLbl = new Label("Blood Units");
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 14));
        nameLbl.setTextFill(Color.web(TEXT_PRIMARY));
        VBox.setMargin(nameLbl, new Insets(10, 0, 8, 0));

        HBox chips = new HBox(6,
            bloodChip("O-",  "4u",  "#FEF2F2", RED),
            bloodChip("A+",  "22u", "#EFF6FF", BLUE),
            bloodChip("B-",  "12u", "#F0FDF4", GREEN),
            bloodChip("AB+", "18u", "#F5F3FF", PURPLE)
        );

        Label warn = new Label("\u26A0  O- Negative critical levels detected");
        warn.setFont(Font.font("System", FontWeight.BOLD, 11));
        warn.setTextFill(Color.web(RED));
        VBox.setMargin(warn, new Insets(8, 0, 0, 0));

        card.getChildren().addAll(topRow, nameLbl, chips, warn);
        return card;
    }

    private VBox bloodChip(String type, String amount, String bg, String fg) {
        Label typeLbl = new Label(type);
        typeLbl.setFont(Font.font("System", FontWeight.BOLD, 11));
        typeLbl.setTextFill(Color.web(fg));

        Label amtLbl = new Label(amount);
        amtLbl.setFont(Font.font("System", FontWeight.BOLD, 14));
        amtLbl.setTextFill(Color.web(TEXT_PRIMARY));

        VBox chip = new VBox(2, typeLbl, amtLbl);
        chip.setAlignment(Pos.CENTER);
        chip.setPadding(new Insets(6, 10, 6, 10));
        chip.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 8;"
        );
        return chip;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Bottom section  (Donut card + right column)
    // ─────────────────────────────────────────────────────────────────────────
    private HBox buildBottomSection() {
        VBox bedOcc = buildBedOccupancyCard();
        HBox.setHgrow(bedOcc, Priority.ALWAYS);

        VBox rightCol = buildRightColumn();
        rightCol.setMinWidth(360);
        rightCol.setPrefWidth(420);
        rightCol.setMaxWidth(480);

        HBox bottom = new HBox(14, bedOcc, rightCol);
        bottom.setAlignment(Pos.TOP_LEFT);
        return bottom;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Bed Occupancy Distribution card
    // ─────────────────────────────────────────────────────────────────────────
    private VBox buildBedOccupancyCard() {
        VBox card = card();
        card.setSpacing(16);

        Label title = new Label("Bed Occupancy Distribution");
        title.setFont(Font.font("System", FontWeight.BOLD, 16));
        title.setTextFill(Color.web(TEXT_PRIMARY));

        StackPane donut = buildDonutChart();

        VBox legend = new VBox(10,
            legendRow(DONUT_ICU,     "ICU Occupancy",  "84%"),
            legendRow(DONUT_EMERG,   "Emergency Ward", "60%"),
            legendRow(DONUT_GENERAL, "General Ward",   "78%")
        );
        legend.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(legend, Priority.ALWAYS);

        HBox donutRow = new HBox(24, donut, legend);
        donutRow.setAlignment(Pos.CENTER_LEFT);

        Label warnLbl = new Label(
            "Capacity reaching threshold in ICU.\nRecommended: Redirect non-critical cases."
        );
        warnLbl.setFont(Font.font("System", FontWeight.BOLD, 12));
        warnLbl.setTextFill(Color.web("#92400E"));
        warnLbl.setWrapText(true);

        VBox warnBox = new VBox(warnLbl);
        warnBox.setPadding(new Insets(10, 12, 10, 12));
        warnBox.setStyle(
            "-fx-background-color: #FFF7ED;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #FDE68A;" +
            "-fx-border-radius: 8;"
        );

        card.getChildren().addAll(title, donutRow, warnBox);
        return card;
    }

    private StackPane buildDonutChart() {
        int size = 180;
        Canvas canvas = new Canvas(size, size);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double cx = size / 2.0, cy = size / 2.0;
        double outerR = 80, strokeW = 28;
        double r = outerR - strokeW / 2.0;

        // Background ring
        gc.setLineWidth(strokeW);
        gc.setStroke(Color.web("#E2E8F0"));
        gc.strokeOval(cx - r, cy - r, r * 2, r * 2);

        // Coloured segments: proportional to 84 / 60 / 78
        double total    = 84 + 60 + 78;
        double gap      = 3.0;
        double icuDeg   = (84 / total) * (360 - 3 * gap);
        double emergDeg = (60 / total) * (360 - 3 * gap);
        double genDeg   = (360 - 3 * gap) - icuDeg - emergDeg;

        drawSegment(gc, cx, cy, r, strokeW, -90, icuDeg, DONUT_ICU);
        drawSegment(gc, cx, cy, r, strokeW, -90 + icuDeg + gap, emergDeg, DONUT_EMERG);
        drawSegment(gc, cx, cy, r, strokeW, -90 + icuDeg + gap + emergDeg + gap, genDeg, DONUT_GENERAL);

        Label pct = new Label("82%");
        pct.setFont(Font.font("System", FontWeight.BOLD, 24));
        pct.setTextFill(Color.web(TEXT_PRIMARY));

        Label tot = new Label("Total");
        tot.setFont(Font.font("System", FontWeight.BOLD, 12));
        tot.setTextFill(Color.web(TEXT_SECONDARY));

        VBox centre = new VBox(2, pct, tot);
        centre.setAlignment(Pos.CENTER);

        StackPane stack = new StackPane(canvas, centre);
        stack.setPrefSize(size, size);
        stack.setMinSize(size, size);
        stack.setMaxSize(size, size);
        return stack;
    }

    private void drawSegment(GraphicsContext gc, double cx, double cy,
                              double radius, double strokeW,
                              double startDeg, double extentDeg, String color) {
        gc.setStroke(Color.web(color));
        gc.setLineWidth(strokeW);
        gc.setLineCap(javafx.scene.shape.StrokeLineCap.BUTT);
        gc.strokeArc(
            cx - radius, cy - radius, radius * 2, radius * 2,
            -startDeg, -extentDeg,
            ArcType.OPEN
        );
    }

    private HBox legendRow(String dotColor, String label, String pct) {
        Circle dot = new Circle(6, Color.web(dotColor));

        Label nameLbl = new Label(label);
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        nameLbl.setTextFill(Color.web(TEXT_SECONDARY));
        HBox.setHgrow(nameLbl, Priority.ALWAYS);

        Label pctLbl = new Label(pct);
        pctLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        pctLbl.setTextFill(Color.web(TEXT_PRIMARY));
        pctLbl.setStyle(
            "-fx-background-color: #F1F5F9;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 2 10 2 10;"
        );

        HBox row = new HBox(8, dot, nameLbl, pctLbl);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(6, 10, 6, 10));
        row.setMaxWidth(Double.MAX_VALUE);
        row.setStyle(
            "-fx-background-color: " + CARD_BG + ";" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );
        return row;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Right column  (ICU trend + Allocation log)
    // ─────────────────────────────────────────────────────────────────────────
    private VBox buildRightColumn() {
        return new VBox(14, buildTrendCard(), buildAllocationLog());
    }

    /** ICU Usage Trend (7D) card. */
    private VBox buildTrendCard() {
        VBox card = card();
        card.setSpacing(12);

        Label title = new Label("ICU Usage Trend (7D)");
        title.setFont(Font.font("System", FontWeight.BOLD, 14));
        title.setTextFill(Color.web(TEXT_PRIMARY));

        Label weekly = new Label("Weekly");
        weekly.setFont(Font.font("System", FontWeight.BOLD, 12));
        weekly.setTextFill(Color.web(TEXT_SECONDARY));
        weekly.setStyle(
            "-fx-background-color: #F1F5F9;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 3 8 3 8;"
        );

        HBox hdr = new HBox(title, hSpacer(), weekly);
        hdr.setAlignment(Pos.CENTER_LEFT);

        Canvas chart = buildTrendChart();

        String[] days = { "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN" };
        HBox dayRow = new HBox();
        dayRow.setAlignment(Pos.CENTER);
        for (String d : days) {
            Label lbl = new Label(d);
            lbl.setFont(Font.font("System", FontWeight.BOLD, 9));
            lbl.setTextFill(Color.web(TEXT_MUTED));
            lbl.setAlignment(Pos.CENTER);
            lbl.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(lbl, Priority.ALWAYS);
            dayRow.getChildren().add(lbl);
        }

        VBox stats = new VBox(8,
            statRow("Average Occupancy", "76.4%",            TEXT_PRIMARY),
            statRow("Peak Demand Day",   "Friday",           BLUE),
            statRow("Efficiency Delta",  "+12% vs last week", GREEN)
        );
        VBox.setMargin(stats, new Insets(4, 0, 0, 0));

        card.getChildren().addAll(hdr, chart, dayRow, stats);
        return card;
    }

    private Canvas buildTrendChart() {
        Canvas canvas = new Canvas(340, 90);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // MON TUE WED THU FRI SAT SUN
        double[] vals = { 72, 68, 75, 79, 88, 70, 74 };
        int    n    = vals.length;
        double padL = 2, padR = 2, padT = 6, padB = 4;
        double w    = canvas.getWidth()  - padL - padR;
        double h    = canvas.getHeight() - padT - padB;
        double slotW = w / n;
        double barW  = slotW - 6;

        for (int i = 0; i < n; i++) {
            double barH = (vals[i] / 100.0) * h;
            double x    = padL + i * slotW + 3;
            double y    = padT + h - barH;
            gc.setFill(i == 4 ? Color.web(BLUE) : Color.web("#BAD4F5"));
            gc.fillRoundRect(x, y, barW, barH, 4, 4);
        }
        return canvas;
    }

    private HBox statRow(String label, String value, String valColor) {
        Label lbl = new Label(label);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 12));
        lbl.setTextFill(Color.web(TEXT_SECONDARY));
        HBox.setHgrow(lbl, Priority.ALWAYS);

        Label val = new Label(value);
        val.setFont(Font.font("System", FontWeight.BOLD, 13));
        val.setTextFill(Color.web(valColor));

        HBox row = new HBox(8, lbl, val);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    /** Recent Allocation Log card. */
    private VBox buildAllocationLog() {
        VBox card = card();
        card.setSpacing(12);

        Label title = new Label("Recent Allocation Log");
        title.setFont(Font.font("System", FontWeight.BOLD, 14));
        title.setTextFill(Color.web(TEXT_PRIMARY));

        VBox logs = new VBox(0,
            logEntry(DOT_BLUE,   "ICU Bed #12 allocated to Patient P-908",
                     "12:45 PM \u2022 Unit A-4"),
            hRule(),
            logEntry(DOT_ORANGE, "Oxygen cylinder refill requested",
                     "11:30 AM \u2022 Storage West"),
            hRule(),
            logEntry(DOT_GREY,   "General Bed #242 vacated",
                     "10:15 AM \u2022 Ward C")
        );

        card.getChildren().addAll(title, logs);
        return card;
    }

    private HBox logEntry(String dotColor, String message, String meta) {
        Circle dot = new Circle(5, Color.web(dotColor));
        dot.setTranslateY(4);

        Label msgLbl = new Label(message);
        msgLbl.setFont(Font.font("System", FontWeight.BOLD, 12));
        msgLbl.setTextFill(Color.web(TEXT_PRIMARY));
        msgLbl.setWrapText(true);

        Label metaLbl = new Label(meta);
        metaLbl.setFont(Font.font("System", FontWeight.BOLD, 11));
        metaLbl.setTextFill(Color.web(TEXT_MUTED));

        VBox text = new VBox(2, msgLbl, metaLbl);
        HBox.setHgrow(text, Priority.ALWAYS);

        HBox row = new HBox(10, dot, text);
        row.setPadding(new Insets(8, 0, 8, 0));
        row.setAlignment(Pos.TOP_LEFT);
        return row;
    }

    private Region hRule() {
        Region r = new Region();
        r.setPrefHeight(1);
        r.setMaxWidth(Double.MAX_VALUE);
        r.setStyle("-fx-background-color: " + BORDER + ";");
        return r;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Shared helpers
    // ─────────────────────────────────────────────────────────────────────────

    /** White rounded card container. */
    private VBox card() {
        VBox c = new VBox(0);
        c.setPadding(new Insets(16));
        c.setStyle(
            "-fx-background-color: " + CARD_BG + ";" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 12;" +
            "-fx-background-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.04), 8, 0, 0, 2);"
        );
        return c;
    }

    /** Emoji label with a tinted pill background. */
    private Label emojiIcon(String emoji, String bgColor, String fgColor) {
        Label lbl = new Label(emoji);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 16));
        lbl.setTextFill(Color.web(fgColor));
        lbl.setStyle(
            "-fx-background-color: " + bgColor + ";" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 6 8 6 8;"
        );
        return lbl;
    }

    /** Coloured pill status tag. */
    private Label statusTag(String text, String bg, String fg) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 10));
        lbl.setTextFill(Color.web(fg));
        lbl.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-background-radius: 20;" +
            "-fx-padding: 2 8 2 8;"
        );
        return lbl;
    }

    /**
     * Responsive progress bar.
     * The fill Region's width is bound to the StackPane's actual width
     * via a widthProperty listener — works correctly without absolute sizing.
     */
    private StackPane progressBar(double fraction, String color) {
        Region track = new Region();
        track.setPrefHeight(5);
        track.setMaxWidth(Double.MAX_VALUE);
        track.setStyle("-fx-background-color: #E2E8F0; -fx-background-radius: 3;");

        Region fill = new Region();
        fill.setPrefHeight(5);
        fill.setMaxHeight(5);
        fill.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 3;");

        StackPane stack = new StackPane(track, fill);
        StackPane.setAlignment(fill, Pos.CENTER_LEFT);
        stack.setMaxWidth(Double.MAX_VALUE);

        // KEY FIX: setMaxWidth must be set alongside setPrefWidth.
        // StackPane stretches children to fill itself by default;
        // constraining maxWidth stops the fill from expanding beyond its fraction.
        stack.widthProperty().addListener((obs, oldW, newW) -> {
            double w = newW.doubleValue() * fraction;
            fill.setPrefWidth(w);
            fill.setMaxWidth(w);
        });
        return stack;
    }

    /** Greedy horizontal spacer. */
    private Region hSpacer() {
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        return r;
    }
}
