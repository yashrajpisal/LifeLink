package com.kurukshetra.view.hospital;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DoctorManagementView extends VBox {

    // ─── Palette ──────────────────────────────────────────────────────────────
    private static final String PAGE_BG        = "#F8FAFC";
    private static final String CARD_BG        = "#F0F4F8";
    private static final String TABLE_BG       = "#FFFFFF";
    private static final String HEADER_BG      = "#FFFFFF";
    private static final String BORDER         = "#E2E8F0";
    private static final String TEXT_PRIMARY   = "#1A202C";
    private static final String TEXT_SECONDARY = "#64748B";
    private static final String TEXT_MUTED     = "#94A3B8";
    private static final String BLUE           = "#1565C0";
    private static final String BLUE_LIGHT     = "#EFF6FF";
    private static final String GREEN          = "#16A34A";
    private static final String GREEN_LIGHT    = "#F0FDF4";
    private static final String ORANGE         = "#D97706";
    private static final String ORANGE_LIGHT   = "#FFF7ED";
    private static final String RED            = "#DC2626";
    private static final String RED_LIGHT      = "#FEF2F2";
    private static final String TEAL           = "#0D9488";
    private static final String ICON_BG_BLUE   = "#EEF4FF";
    private static final String ICON_BG_PINK   = "#FFF0F3";
    private static final String ICON_BLUE      = "#4F6FDB";
    private static final String ICON_PINK      = "#D06080";

    // ─── Constructor ──────────────────────────────────────────────────────────
    public DoctorManagementView() {
        super(0);
        setStyle("-fx-background-color: " + PAGE_BG + ";");
        VBox.setVgrow(this, Priority.ALWAYS);
        VBox mainContent = buildMainContent();
        VBox.setVgrow(mainContent, Priority.ALWAYS);
        getChildren().add(mainContent);
    }

    // ─── Main content ─────────────────────────────────────────────────────────
    private VBox buildMainContent() {
        VBox wrapper = new VBox(0);
        wrapper.setStyle("-fx-background-color: " + PAGE_BG + ";");
        VBox.setVgrow(wrapper, Priority.ALWAYS);

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

    // ─── Top bar ──────────────────────────────────────────────────────────────
    private HBox buildTopBar() {
        // Search box
        HBox searchBox = new HBox(6);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(0, 10, 0, 10));
        searchBox.setPrefHeight(36);
        searchBox.setPrefWidth(320);
        searchBox.setMaxWidth(380);
        searchBox.setStyle(
            "-fx-background-color: #F8FAFC;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );

        Label searchIcon = new Label("\uD83D\uDD0D");
        searchIcon.setFont(Font.font("System", FontWeight.BOLD, 12));
        searchIcon.setTextFill(Color.web(TEXT_MUTED));

        TextField searchField = new TextField();
        searchField.setPromptText("Search medical staff, ID or department...");
        searchField.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;" +
            "-fx-font-size: 13;" +
            "-fx-text-fill: " + TEXT_SECONDARY + ";"
        );
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchBox.getChildren().addAll(searchIcon, searchField);

        Button notifBtn = iconBtn("\uD83D\uDD14");   // 🔔
        Button settBtn  = iconBtn("\u2699");          // ⚙

        Region divider = new Region();
        divider.setPrefSize(1, 28);
        divider.setStyle("-fx-background-color: " + BORDER + ";");

        // Profile avatar — initials circle
        StackPane avatar = buildAvatarCircle("SJ", "#4A90D9");

        Label nameL = new Label("Dr. Sarah Jenkins");
        nameL.setFont(Font.font("System", FontWeight.BOLD, 13));
        nameL.setTextFill(Color.web(TEXT_PRIMARY));
        
        Label roleL = new Label("Chief Surgeon");
        roleL.setFont(Font.font("System", 11));
        roleL.setTextFill(Color.web(TEXT_SECONDARY));

        VBox profileTxt = new VBox(0, nameL, roleL);
        profileTxt.setAlignment(Pos.CENTER_LEFT);

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

    // ─── Body ─────────────────────────────────────────────────────────────────
    private VBox buildBody() {
        VBox body = new VBox(20);
        body.setPadding(new Insets(24, 24, 32, 24));
        body.setStyle("-fx-background-color: " + PAGE_BG + ";");
        body.getChildren().addAll(
            buildPageHeader(),
            buildSummaryCards(),
            buildFilterBar(),
            buildDoctorTable()
        );
        return body;
    }

    // ─── Page header ──────────────────────────────────────────────────────────
    private HBox buildPageHeader() {
        VBox titleBlock = new VBox(4);
        HBox.setHgrow(titleBlock, Priority.ALWAYS);

        Label title = new Label("Medical Staff Oversight");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#1A2456"));

        Label subtitle = new Label(
            "Manage specialized practitioners and monitor real-time availability across hospital wings."
        );
        subtitle.setFont(Font.font("System", FontWeight.BOLD, 13));
        subtitle.setTextFill(Color.web(TEXT_SECONDARY));
        subtitle.setWrapText(true);
        titleBlock.getChildren().addAll(title, subtitle);

        // ── Add Doctor button (filled blue) ──────────────────────────────────
        Button addBtn = new Button();
        addBtn.setStyle(
            "-fx-background-color: " + BLUE + ";" +
            "-fx-background-radius: 8;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 10 20 10 20;" +
            "-fx-cursor: hand;"
        );
        Label addIcon = new Label("+");
        addIcon.setFont(Font.font("System", FontWeight.BOLD, 18));
        addIcon.setTextFill(Color.WHITE);
        Label addTxt = new Label("Add\nDoctor");
        addTxt.setFont(Font.font("System", FontWeight.BOLD, 13));
        addTxt.setTextFill(Color.WHITE);
        addTxt.setAlignment(Pos.CENTER_LEFT);
        HBox addContent = new HBox(6, addIcon, addTxt);
        addContent.setAlignment(Pos.CENTER);
        addContent.setPadding(new Insets(4, 6, 4, 6));
        addBtn.setGraphic(addContent);
        addBtn.setOnMouseEntered(e -> addBtn.setStyle(
            "-fx-background-color: #0D47A1;" +
            "-fx-background-radius: 8;" +
            "-fx-text-fill: white; -fx-font-size: 13;" +
            "-fx-font-weight: bold; -fx-padding: 10 20 10 20; -fx-cursor: hand;"
        ));
        addBtn.setOnMouseExited(e -> addBtn.setStyle(
            "-fx-background-color: " + BLUE + ";" +
            "-fx-background-radius: 8;" +
            "-fx-text-fill: white; -fx-font-size: 13;" +
            "-fx-font-weight: bold; -fx-padding: 10 20 10 20; -fx-cursor: hand;"
        ));

        // ── Assign Emergency button (outlined) ────────────────────────────────
        Button emergBtn = new Button();
        emergBtn.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 8;" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-font-size: 13; -fx-font-weight: bold;" +
            "-fx-padding: 10 16 10 16; -fx-cursor: hand;"
        );
        Label emergIcon = new Label("\uD83D\uDEA8"); // 🚨
        emergIcon.setFont(Font.font("System", FontWeight.BOLD, 14));
        Label emergTxt = new Label("Assign\nEmergency");
        emergTxt.setFont(Font.font("System", FontWeight.BOLD, 13));
        emergTxt.setTextFill(Color.web(TEXT_PRIMARY));
        emergTxt.setAlignment(Pos.CENTER_LEFT);
        HBox emergContent = new HBox(6, emergIcon, emergTxt);
        emergContent.setAlignment(Pos.CENTER);
        emergContent.setPadding(new Insets(4, 6, 4, 6));
        emergBtn.setGraphic(emergContent);
        emergBtn.setOnMouseEntered(e -> emergBtn.setStyle(
            "-fx-background-color: #F8FAFC;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #CBD5E1; -fx-border-radius: 8;" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-font-size: 13; -fx-font-weight: bold;" +
            "-fx-padding: 10 16 10 16; -fx-cursor: hand;"
        ));
        emergBtn.setOnMouseExited(e -> emergBtn.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: " + BORDER + "; -fx-border-radius: 8;" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-font-size: 13; -fx-font-weight: bold;" +
            "-fx-padding: 10 16 10 16; -fx-cursor: hand;"
        ));

        HBox btnGroup = new HBox(10, addBtn, emergBtn);
        btnGroup.setAlignment(Pos.CENTER_RIGHT);
        HBox header = new HBox(16, titleBlock, btnGroup);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    // ─── Summary cards ────────────────────────────────────────────────────────
    private HBox buildSummaryCards() {
        HBox row = new HBox(16);
        row.setAlignment(Pos.CENTER_LEFT);

        VBox card1 = summaryCard(makeSurgeonIcon(), "#EEF2FF",
                                  "ACTIVE SURGEONS", "12", "4 in surgery", BLUE);
        VBox card2 = summaryCard(makeClipboardIcon("#475569"), "#F1F5F9",
                                  "DOCTORS ON DUTY", "48", "Across 8 departments", TEXT_SECONDARY);
        VBox card3 = capacityCard();

        HBox.setHgrow(card1, Priority.ALWAYS);
        HBox.setHgrow(card2, Priority.ALWAYS);
        HBox.setHgrow(card3, Priority.ALWAYS);

        card1.setMinWidth(0);
        card2.setMinWidth(0);
        card3.setMinWidth(0);

        card1.setPrefWidth(0);
        card2.setPrefWidth(0);
        card3.setPrefWidth(0);
        
        card1.setMaxWidth(Double.MAX_VALUE);
        card2.setMaxWidth(Double.MAX_VALUE);
        card3.setMaxWidth(Double.MAX_VALUE);

        row.getChildren().addAll(card1, card2, card3);
        return row;
    }

    /**
     * Canvas icon — Doctor/Surgeon figure:
     * a person outline (oval head + rounded body) with a medical cross overlaid.
     */
    private Canvas makeSurgeonIcon() {
        Canvas c = new Canvas(22, 22);
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setStroke(Color.web(ICON_BLUE));
        gc.setLineWidth(2.2);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);
        // Head
        gc.strokeOval(7, 0, 8, 8);
        // Body — rounded rectangle
        gc.strokeRoundRect(3, 10, 16, 11, 4, 4);
        // Medical cross (vertical bar)
        gc.strokeLine(11, 12.5, 11, 18.5);
        // Medical cross (horizontal bar)
        gc.strokeLine(8, 15.5, 14, 15.5);
        return c;
    }

    /**
     * Canvas icon — Clipboard:
     * a rectangle body with a tab at the top and three horizontal content lines.
     */
    private Canvas makeClipboardIcon(String strokeColor) {
        Canvas c = new Canvas(22, 22);
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setStroke(Color.web(strokeColor));
        gc.setLineWidth(2.2);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);
        // Body rectangle
        gc.strokeRoundRect(1.5, 3.5, 19, 17.5, 3, 3);
        // Tab at top
        gc.strokeRoundRect(6.5, 0.5, 9, 5.5, 2, 2);
        // Content lines
        gc.strokeLine(5, 10, 17, 10);
        gc.strokeLine(5, 14, 17, 14);
        gc.strokeLine(5, 18, 13, 18);
        return c;
    }

    /**
     * Canvas icon — Two people (staff group) with a plus sign:
     * represents Staff Capacity. Drawn in pink/salmon.
     */
    private Canvas makePeopleIcon() {
        Canvas c = new Canvas(22, 22);
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setStroke(Color.web(ICON_PINK));
        gc.setLineWidth(2.2);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.setLineJoin(StrokeLineJoin.ROUND);
        // Person 1 (left): head
        gc.strokeOval(1, 1, 8, 7);
        // Person 1: body (rounded rect, partial)
        gc.strokeRoundRect(0, 10, 10, 7, 2, 2);
        // Person 2 (right): head
        gc.strokeOval(11, 3, 8, 7);
        // Person 2: body
        gc.strokeRoundRect(10, 12, 10, 7, 2, 2);
        // Plus sign (top-right of icon)
        gc.strokeLine(18, 0, 18, 6);    // vertical
        gc.strokeLine(15, 3, 21, 3);    // horizontal
        return c;
    }

    private VBox summaryCard(Canvas icon, String iconBg,
                             String label, String value,
                             String subtext, String subtextColor) {
        VBox card = new VBox(0);
        card.setStyle(
            "-fx-background-color: #FFFFFF;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 12;"
        );
        card.setPadding(new Insets(20));

        StackPane iconBox = new StackPane(icon);
        iconBox.setPrefSize(44, 44);
        iconBox.setMinSize(44, 44);
        iconBox.setMaxSize(44, 44);
        iconBox.setStyle(
            "-fx-background-color: " + iconBg + ";" +
            "-fx-background-radius: 10;"
        );

        HBox topRow = new HBox(iconBox);
        topRow.setAlignment(Pos.CENTER_LEFT);

        VBox textBlock = new VBox(2);
        textBlock.setPadding(new Insets(10, 0, 0, 0));

        Label capLbl = new Label(label);
        capLbl.setFont(Font.font("System", FontWeight.BOLD, 10));
        capLbl.setTextFill(Color.web(TEXT_MUTED));

        Label valLbl = new Label(value);
        valLbl.setFont(Font.font("System", FontWeight.BOLD, 28));
        valLbl.setTextFill(Color.web(TEXT_PRIMARY));

        Label subLbl = new Label(subtext);
        subLbl.setFont(Font.font("System", 12));
        subLbl.setTextFill(Color.web(subtextColor));

        textBlock.getChildren().addAll(capLbl, valLbl, subLbl);
        card.getChildren().addAll(topRow, textBlock);
        return card;
    }

    // ─── Staff Capacity card ──────────────────────────────────────────────────
    private VBox capacityCard() {
        VBox card = new VBox(0);
        card.setStyle(
            "-fx-background-color: #FFFFFF;" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 12;"
        );
        card.setPadding(new Insets(20));

        StackPane iconBox = new StackPane(makePeopleIcon());
        iconBox.setPrefSize(44, 44);
        iconBox.setMinSize(44, 44);
        iconBox.setMaxSize(44, 44);
        iconBox.setStyle(
            "-fx-background-color: " + ICON_BG_PINK + ";" +
            "-fx-background-radius: 10;"
        );

        HBox topRow = new HBox(iconBox);
        topRow.setAlignment(Pos.CENTER_LEFT);

        VBox textBlock = new VBox(2);
        textBlock.setPadding(new Insets(10, 0, 0, 0));

        Label capLbl = new Label("STAFF CAPACITY");
        capLbl.setFont(Font.font("System", FontWeight.BOLD, 10));
        capLbl.setTextFill(Color.web(TEXT_MUTED));

        Label valLbl = new Label("85%");
        valLbl.setFont(Font.font("System", FontWeight.BOLD, 28));
        valLbl.setTextFill(Color.web(TEXT_PRIMARY));

        Label badgeLbl = new Label("\u2197 +2%");
        badgeLbl.setFont(Font.font("System", FontWeight.BOLD, 11));
        badgeLbl.setTextFill(Color.web(GREEN));
        badgeLbl.setStyle(
            "-fx-background-color: " + GREEN_LIGHT + ";" +
            "-fx-background-radius: 4;" +
            "-fx-padding: 2 6 2 6;"
        );

        Region valueSpacer = new Region();
        HBox.setHgrow(valueSpacer, Priority.ALWAYS);
        HBox valueRow = new HBox(8, valLbl, valueSpacer, badgeLbl);
        valueRow.setAlignment(Pos.CENTER_LEFT);

        StackPane progressBar = buildProgressBar(0.85, BLUE, 6);
        
        textBlock.getChildren().addAll(capLbl, valueRow, progressBar);
        card.getChildren().addAll(topRow, textBlock);
        return card;
    }

    private StackPane buildProgressBar(double pct, String color, int height) {
        Region track = new Region();
        track.setMinHeight(height);
        track.setMaxHeight(height);
        track.setStyle("-fx-background-color: #E2E8F0; -fx-background-radius: " + height + ";");

        Region fill = new Region();
        fill.setMinHeight(height);
        fill.setMaxHeight(height);
        fill.setStyle("-fx-background-color: " + color + "; -fx-background-radius: " + height + ";");

        StackPane bar = new StackPane(track, fill);
        bar.setAlignment(Pos.CENTER_LEFT);
        
        fill.maxWidthProperty().bind(bar.widthProperty().multiply(pct));

        bar.setPadding(new Insets(8, 0, 0, 0));
        bar.setMinHeight(height + 16);
        return bar;
    }

    // ─── Filter bar ───────────────────────────────────────────────────────────
    private HBox buildFilterBar() {
        HBox bar = new HBox(12);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(14, 16, 14, 16));
        bar.setStyle(
            "-fx-background-color: " + CARD_BG + ";" +
            "-fx-background-radius: 10;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 10;"
        );

        HBox deptDrop  = dropdownBox("Department:\nAll");
        HBox availDrop = dropdownBox("Availability:\nAvailable");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Filter icon button
        Label filterIcon = new Label("\u2261"); // ≡
        filterIcon.setFont(Font.font("System", FontWeight.BOLD, 18));
        filterIcon.setTextFill(Color.web(TEXT_SECONDARY));
        Button filterIconBtn = new Button();
        filterIconBtn.setGraphic(filterIcon);
        String filterBase =
            "-fx-background-color: transparent;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 6;" +
            "-fx-padding: 6 10 6 10; -fx-cursor: hand;";
        filterIconBtn.setStyle(filterBase);
        filterIconBtn.setOnMouseEntered(e -> filterIconBtn.setStyle(
            "-fx-background-color: #F1F5F9;" +
            "-fx-border-color: #CBD5E1;" +
            "-fx-border-radius: 6;" +
            "-fx-padding: 6 10 6 10; -fx-cursor: hand;"
        ));
        filterIconBtn.setOnMouseExited(e -> filterIconBtn.setStyle(filterBase));

        // Download icon button
        Label downloadIcon = new Label("\u2913"); // ⤓
        downloadIcon.setFont(Font.font("System", FontWeight.BOLD, 16));
        downloadIcon.setTextFill(Color.web(TEXT_SECONDARY));
        Button downloadBtn = new Button();
        downloadBtn.setGraphic(downloadIcon);
        String dlBase =
            "-fx-background-color: transparent;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 6;" +
            "-fx-padding: 6 10 6 10; -fx-cursor: hand;";
        downloadBtn.setStyle(dlBase);
        downloadBtn.setOnMouseEntered(e -> downloadBtn.setStyle(
            "-fx-background-color: #F1F5F9;" +
            "-fx-border-color: #CBD5E1;" +
            "-fx-border-radius: 6;" +
            "-fx-padding: 6 10 6 10; -fx-cursor: hand;"
        ));
        downloadBtn.setOnMouseExited(e -> downloadBtn.setStyle(dlBase));

        bar.getChildren().addAll(deptDrop, availDrop, spacer, filterIconBtn, downloadBtn);
        return bar;
    }

    private HBox dropdownBox(String labelText) {
        Label lbl = new Label(labelText);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 12));
        lbl.setTextFill(Color.web(TEXT_PRIMARY));
        lbl.setAlignment(Pos.CENTER_LEFT);

        Label arrow = new Label("\u25BE"); // ▾
        arrow.setFont(Font.font("System", FontWeight.BOLD, 11));
        arrow.setTextFill(Color.web(TEXT_SECONDARY));

        HBox box = new HBox(8, lbl, arrow);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(8, 12, 8, 12));
        box.setMinWidth(140);
        String baseStyle =
            "-fx-background-color: white;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;";
        box.setStyle(baseStyle);
        box.setOnMouseEntered(e -> box.setStyle(
            "-fx-background-color: #F8FAFC;" +
            "-fx-border-color: #CBD5E1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        ));
        box.setOnMouseExited(e -> box.setStyle(baseStyle));
        return box;
    }

    // ─── Doctor table ─────────────────────────────────────────────────────────
    private Node buildDoctorTable() {
        VBox tableCard = new VBox(0);
        tableCard.setStyle(
            "-fx-background-color: " + TABLE_BG + ";" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 12;"
        );

        tableCard.getChildren().add(buildTableHeaderRow());
        tableCard.getChildren().add(tableDivider());

        // [name, id, dept, spec, shift, availability, status, avatarColor]
        Object[][] rows = {
            { "Dr. James Wilson",    "LL-9021", "Cardiology",  "Interventional Cardiology", "AM", "Available",  "On Duty",             "#4A90D9" },
            { "Dr. Elena Rodriguez", "LL-4432", "Orthopedics", "Spinal Surgery",            "AM", "In Surgery", "On Duty",             "#E07A5F" },
            { "Dr. Michael Chen",    "LL-2188", "Pediatrics",  "Child Neurology",           "PM", "On Break",   "Shift Start (14:00)", "#6AAB9C" },
            { "Dr. Sarah Thompson",  "LL-1055", "Oncology",    "Radiotherapy",              "AM", "Available",  "On Duty",             "#9B87B8" },
            { "Dr. Robert Miller",   "LL-3310", "Neurology",   "Neurosurgery",              "PM", "In Surgery", "On Duty",             "#E07A5F" },
        };

        for (int i = 0; i < rows.length; i++) {
            Object[] r = rows[i];
            HBox row = buildTableDataRow(
                (String) r[0], (String) r[1], (String) r[2],
                (String) r[3], (String) r[4], (String) r[5],
                (String) r[6], (String) r[7]
            );
            tableCard.getChildren().add(row);
            if (i < rows.length - 1) tableCard.getChildren().add(tableDivider());
        }

        tableCard.getChildren().addAll(tableDivider(), buildPagination());
        return tableCard;
    }

    // ─── Table header ─────────────────────────────────────────────────────────
    private HBox buildTableHeaderRow() {
        HBox row = new HBox(0);
        row.setPadding(new Insets(0, 16, 0, 16));
        row.setPrefHeight(44);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: " + TABLE_BG + ";");
        row.getChildren().addAll(
            tableHeaderCell("DOCTOR NAME",     0.22),
            tableHeaderCell("DEPARTMENT",      0.13),
            tableHeaderCell("SPECIALIZATION",  0.17),
            tableHeaderCell("SHIFT",           0.08),
            tableHeaderCell("AVAILABILITY",    0.13),
            tableHeaderCell("STATUS",          0.15),
            tableHeaderCell("ACTIONS",         0.12)
        );
        return row;
    }

    private Node tableHeaderCell(String text, double widthPct) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 10));
        lbl.setStyle("-fx-font-weight: bold;");
        lbl.setTextFill(Color.web(TEXT_MUTED));
        
        HBox cell = new HBox(lbl);
        cell.setAlignment(Pos.CENTER_LEFT);
        cell.setPrefWidth(widthPct * 900);
        cell.setMinWidth(0);
        cell.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(cell, Priority.ALWAYS);
        return cell;
    }

    // ─── Table data row ───────────────────────────────────────────────────────
    private HBox buildTableDataRow(String name, String id,
                                   String dept, String spec,
                                   String shift, String availability,
                                   String status, String avatarColor) {
        HBox row = new HBox(0);
        row.setPadding(new Insets(0, 16, 0, 16));
        row.setPrefHeight(70);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: " + TABLE_BG + ";");
        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: #F8FAFC;"));
        row.setOnMouseExited( e -> row.setStyle("-fx-background-color: " + TABLE_BG + ";"));

        // ── Doctor Name + ID ──────────────────────────────────────────────────
        StackPane avatar = buildAvatarCircle(getInitials(name), avatarColor);
        Label nameLbl = new Label(name);
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        nameLbl.setTextFill(Color.web(TEXT_PRIMARY));
        nameLbl.setWrapText(true);
        Label idLbl = new Label("ID: " + id);
        idLbl.setFont(Font.font("System", 11));
        idLbl.setTextFill(Color.web(TEXT_MUTED));
        VBox nameBlock = new VBox(2, nameLbl, idLbl);
        HBox nameCell = new HBox(10, avatar, nameBlock);
        nameCell.setAlignment(Pos.CENTER_LEFT);
        nameCell.setPrefWidth(0.22 * 900);
        nameCell.setMinWidth(0);
        nameCell.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(nameCell, Priority.ALWAYS);

        // ── Department ────────────────────────────────────────────────────────
        Label deptLbl = plainCell(dept, 0.13);

        // ── Specialization ────────────────────────────────────────────────────
        Label specLbl = plainCell(spec, 0.17);
        specLbl.setWrapText(true);

        // ── Shift badge (AM=blue, PM=amber) ───────────────────────────────────
        boolean isAM = "AM".equalsIgnoreCase(shift);
        Label shiftBadge = new Label(shift);
        shiftBadge.setFont(Font.font("System", FontWeight.BOLD, 11));
        shiftBadge.setTextFill(Color.web(isAM ? "#1E40AF" : "#92400E"));
        shiftBadge.setStyle(
            "-fx-background-color: " + (isAM ? "#DBEAFE" : "#FEF3C7") + ";" +
            "-fx-background-radius: 4;" +
            "-fx-padding: 3 8 3 8;"
        );
        HBox shiftCell = new HBox(shiftBadge);
        shiftCell.setAlignment(Pos.CENTER_LEFT);
        shiftCell.setFillHeight(false);
        shiftCell.setPrefWidth(0.08 * 900);
        shiftCell.setMinWidth(0);
        shiftCell.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(shiftCell, Priority.ALWAYS);

        // ── Availability badge ────────────────────────────────────────────────
        HBox availCell = new HBox(availabilityBadge(availability));
        availCell.setAlignment(Pos.CENTER_LEFT);
        availCell.setFillHeight(false);
        availCell.setPrefWidth(0.13 * 900);
        availCell.setMinWidth(0);
        availCell.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(availCell, Priority.ALWAYS);

        // ── Status ────────────────────────────────────────────────────────────
        Label statusLbl = new Label(status);
        statusLbl.setFont(Font.font("System", FontWeight.BOLD, 12));
        // "On Duty" = blue, "Shift Start" = dark gray
        String statusColor;
        if (status.equals("On Duty")) {
            statusColor = BLUE;
        } else {
            statusColor = TEXT_SECONDARY;
        }
        statusLbl.setTextFill(Color.web(statusColor));
        HBox statusCell = new HBox(statusLbl);
        statusCell.setAlignment(Pos.CENTER_LEFT);
        statusCell.setFillHeight(false);
        statusCell.setPrefWidth(0.15 * 900);
        statusCell.setMinWidth(0);
        statusCell.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(statusCell, Priority.ALWAYS);

        // ── Actions ───────────────────────────────────────────────────────────
        HBox actionsCell = new HBox(8, editBtn(), deleteBtn());
        actionsCell.setAlignment(Pos.CENTER_LEFT);
        actionsCell.setPrefWidth(0.12 * 900);
        actionsCell.setMinWidth(0);
        actionsCell.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(actionsCell, Priority.ALWAYS);

        row.getChildren().addAll(nameCell, deptLbl, specLbl, shiftCell,
                                  availCell, statusCell, actionsCell);
        return row;
    }

    // ─── Edit button (✎ pencil) — blue on hover ───────────────────────────────
    private Node editBtn() {
        Label icon = new Label("\u270E");
        icon.setFont(Font.font("System", FontWeight.BOLD, 14));
        icon.setStyle("-fx-font-weight: bold;");
        icon.setTextFill(Color.web(TEXT_SECONDARY));
        Button btn = new Button();
        btn.setGraphic(icon);
        String base =
            "-fx-background-color: transparent;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 6; -fx-background-radius: 6;" +
            "-fx-padding: 4 8 4 8; -fx-cursor: hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> {
            btn.setStyle(
                "-fx-background-color: #EFF6FF;" +
                "-fx-border-color: " + BLUE + ";" +
                "-fx-border-radius: 6; -fx-background-radius: 6;" +
                "-fx-padding: 4 8 4 8; -fx-cursor: hand;"
            );
            icon.setTextFill(Color.web(BLUE));
        });
        btn.setOnMouseExited(e -> {
            btn.setStyle(base);
            icon.setTextFill(Color.web(TEXT_SECONDARY));
        });
        return btn;
    }

    // ─── Delete button (🗑 trash) — red on hover ──────────────────────────────
    private Node deleteBtn() {
        Label icon = new Label("\uD83D\uDDD1");
        icon.setFont(Font.font("System", FontWeight.BOLD, 13));
        icon.setStyle("-fx-font-weight: bold;");
        icon.setTextFill(Color.web(TEXT_MUTED));
        Button btn = new Button();
        btn.setGraphic(icon);
        String base =
            "-fx-background-color: transparent;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 6; -fx-background-radius: 6;" +
            "-fx-padding: 4 8 4 8; -fx-cursor: hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> {
            btn.setStyle(
                "-fx-background-color: #FEF2F2;" +
                "-fx-border-color: " + RED + ";" +
                "-fx-border-radius: 6; -fx-background-radius: 6;" +
                "-fx-padding: 4 8 4 8; -fx-cursor: hand;"
            );
            icon.setTextFill(Color.web(RED));
        });
        btn.setOnMouseExited(e -> {
            btn.setStyle(base);
            icon.setTextFill(Color.web(TEXT_MUTED));
        });
        return btn;
    }

    // ─── Availability badge ───────────────────────────────────────────────────
    private Node availabilityBadge(String status) {
        String bg, fg;
        switch (status) {
            case "Available"  -> { bg = GREEN_LIGHT;  fg = GREEN;  }
            case "In Surgery" -> { bg = RED_LIGHT;    fg = RED;    }
            case "On Break"   -> { bg = ORANGE_LIGHT; fg = ORANGE; }
            default           -> { bg = BLUE_LIGHT;   fg = BLUE;   }
        }
        Circle dot = new Circle(4);
        dot.setFill(Color.web(fg));
        Label textLbl = new Label(status);
        textLbl.setFont(Font.font("System", FontWeight.BOLD, 11));
        textLbl.setTextFill(Color.web(fg));
        HBox badge = new HBox(5, dot, textLbl);
        badge.setAlignment(Pos.CENTER_LEFT);
        badge.setPadding(new Insets(4, 10, 4, 8));
        badge.setStyle("-fx-background-color: " + bg + "; -fx-background-radius: 6;");
        return badge;
    }

    // ─── Pagination footer ────────────────────────────────────────────────────
    private HBox buildPagination() {
        Label showing = new Label("Showing 1-5 of 48 Doctors");
        showing.setFont(Font.font("System", 12));
        showing.setTextFill(Color.web(TEXT_SECONDARY));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox paginationBox = new HBox(4);
        paginationBox.setAlignment(Pos.CENTER);
        paginationBox.getChildren().addAll(
            pageNavBtn("\u2039"),       // ‹ prev
            pageNumBtn("1", true),      // active
            pageNumBtn("2", false),
            pageNumBtn("3", false),
            pageEllipsis(),
            pageNumBtn("10", false),
            pageNavBtn("\u203A")        // › next
        );

        HBox footer = new HBox(10, showing, spacer, paginationBox);
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setPadding(new Insets(14, 16, 14, 16));
        return footer;
    }

    private Button pageNavBtn(String symbol) {
        Button btn = new Button(symbol);
        btn.setFont(Font.font("System", FontWeight.BOLD, 14));
        String base =
            "-fx-background-color: white;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 6; -fx-background-radius: 6;" +
            "-fx-text-fill: " + TEXT_SECONDARY + ";" +
            "-fx-padding: 4 10 4 10; -fx-cursor: hand;" +
            "-fx-min-width: 32; -fx-min-height: 32;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: #F0F4F8;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 6; -fx-background-radius: 6;" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-padding: 4 10 4 10; -fx-cursor: hand;" +
            "-fx-min-width: 32; -fx-min-height: 32;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(base));
        return btn;
    }

    private Button pageNumBtn(String num, boolean active) {
        Button btn = new Button(num);
        btn.setFont(Font.font("System",
            active ? FontWeight.BOLD : FontWeight.BOLD, 12));
        String base = (active
            ? "-fx-background-color: " + BLUE + "; -fx-text-fill: white;" +
              "-fx-border-color: " + BLUE + ";"
            : "-fx-background-color: white; -fx-text-fill: " + TEXT_SECONDARY + ";" +
              "-fx-border-color: " + BORDER + ";") +
            "-fx-border-radius: 6; -fx-background-radius: 6;" +
            "-fx-padding: 4 10 4 10; -fx-cursor: hand;" +
            "-fx-min-width: 32; -fx-min-height: 32;";
        btn.setStyle(base);
        if (!active) {
            btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #F0F4F8; -fx-text-fill: " + TEXT_PRIMARY + ";" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 6; -fx-background-radius: 6;" +
                "-fx-padding: 4 10 4 10; -fx-cursor: hand;" +
                "-fx-min-width: 32; -fx-min-height: 32;"
            ));
            btn.setOnMouseExited(e -> btn.setStyle(base));
        }
        return btn;
    }

    private Label pageEllipsis() {
        Label lbl = new Label("...");
        lbl.setFont(Font.font("System", 12));
        lbl.setTextFill(Color.web(TEXT_MUTED));
        lbl.setPadding(new Insets(0, 4, 0, 4));
        return lbl;
    }

    // ─── Shared helpers ───────────────────────────────────────────────────────

    /** Top-bar icon button with transparent-to-highlighted hover. */
    private Button iconBtn(String icon) {
        Button b = new Button(icon);
        b.setFont(Font.font("System", FontWeight.BOLD, 15));
        String base =
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;" +
            "-fx-padding: 6 10 6 10; -fx-cursor: hand;";
        b.setStyle(base);
        b.setOnMouseEntered(e -> b.setStyle(
            "-fx-background-color: #F1F5F9;" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: transparent;" +
            "-fx-padding: 6 10 6 10; -fx-cursor: hand;"
        ));
        b.setOnMouseExited(e -> b.setStyle(base));
        return b;
    }

    /** Circular avatar — photo-like placeholder with grey bg and white initials. */
    private StackPane buildAvatarCircle(String initials, String colorHex) {
        Circle bg = new Circle(20);
        bg.setFill(Color.web("#C4CDD5"));
        Circle border = new Circle(21);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.web("#E2E8F0"));
        border.setStrokeWidth(1.5);
        Label txt = new Label(initials);
        txt.setFont(Font.font("System", FontWeight.BOLD, 11));
        txt.setTextFill(Color.WHITE);
        StackPane sp = new StackPane(border, bg, txt);
        sp.setPrefSize(42, 42);
        sp.setMinSize(42, 42);
        sp.setMaxSize(42, 42);
        return sp;
    }

    /** Plain text table cell with proportional flex width. */
    private Label plainCell(String text, double widthPct) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        lbl.setStyle("-fx-font-weight: bold;");
        lbl.setTextFill(Color.web(TEXT_SECONDARY));
        lbl.setPrefWidth(widthPct * 900);
        lbl.setMinWidth(0);
        lbl.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(lbl, Priority.ALWAYS);
        return lbl;
    }

    /** 1-px horizontal divider between table rows. */
    private Node tableDivider() {
        Region line = new Region();
        line.setPrefHeight(1);
        line.setMinHeight(1);
        line.setMaxHeight(1);
        line.setMaxWidth(Double.MAX_VALUE);
        line.setStyle("-fx-background-color: " + BORDER + ";");
        return line;
    }

    /** Extracts up to 2 initials: "Dr. James Wilson" → "JW". */
    private String getInitials(String fullName) {
        String[] parts = fullName.replaceAll("Dr\\.", "").trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (!p.isEmpty()) sb.append(p.charAt(0));
            if (sb.length() == 2) break;
        }
        return sb.toString().toUpperCase();
    }
}
