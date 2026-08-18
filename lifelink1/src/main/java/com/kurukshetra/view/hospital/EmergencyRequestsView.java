package com.kurukshetra.view.hospital;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class EmergencyRequestsView extends VBox {

    // ─── Colour palette ──────────────────────────────────────────────────────
    private static final String C_PAGE_BG       = "#FFFFFF";
    private static final String C_WHITE         = "#FFFFFF";
    private static final String C_CARD_BG       = "#F8FAFC";  // Lighter blue/lavender tint for summary cards
    private static final String C_BORDER        = "#E2ECF6";
    private static final String C_HEADER_FG     = "#1A2456";
    private static final String C_SUB_FG        = "#6c7fa1";
    private static final String C_SEARCH_BG     = "#EEF3FA";
    private static final String C_LABEL_UP      = "#8A9BB8";
    private static final String C_TH_BG         = "#F0F5FC";
    private static final String C_ROW_HOVER     = "#F7FAFF";
    private static final String C_DIVIDER       = "#E8EFF8";
    private static final String C_BTN_GREEN     = "#16A34A";
    private static final String C_BTN_GREEN_HV  = "#15803D";
    private static final String C_FILTER_ACTV   = "#1565C0";
    private static final String C_NOTICE_BG     = "#EFF6FF";
    private static final String C_NOTICE_BORDER = "#BFDBFE";
    private static final String C_NOTICE_ICON   = "#2563EB";
    private static final String C_NOTICE_TITLE  = "#1E40AF";
    private static final String C_NOTICE_TEXT   = "#3B82F6";

    // Severity badge colours
    private static final String C_HIGH_BG      = "#FEE2E2";
    private static final String C_HIGH_FG      = "#DC2626";
    private static final String C_HIGH_BORDER  = "#FECACA";
    private static final String C_MED_BG       = "#FEF3C7";
    private static final String C_MED_FG       = "#B45309";
    private static final String C_MED_BORDER   = "#FDE68A";
    private static final String C_LOW_BG       = "#E8EFF8";
    private static final String C_LOW_FG       = "#475569";
    private static final String C_LOW_BORDER   = "#D0DCEA";

    // Card icon colours
    private static final String C_CARD_RED     = "#DC2626";
    private static final String C_CARD_RED_BG  = "#FEF2F2";
    private static final String C_CARD_BLUE    = "#2563EB";
    private static final String C_CARD_BLUE_BG = "#EFF6FF";
    private static final String C_CARD_ORG     = "#EA580C";
    private static final String C_CARD_ORG_BG  = "#FFF7ED";
    private static final String C_CARD_GRY     = "#475569";
    private static final String C_CARD_GRY_BG  = "#F1F5F9";

    // Filter badge colours
    private static final String C_BADGE_RED    = "#DC2626";
    private static final String C_BADGE_ORANGE = "#C2710C";

    // ETA colours
    private static final String C_ETA_RED      = "#DC2626";
    private static final String C_ETA_GREEN    = "#16A34A";
    private static final String C_ETA_DARK     = "#1A2456";

    // Column widths – shared by header and data rows for alignment
    private static final int W_ID   = 100;
    private static final int W_NAME = 140;
    private static final int W_TYPE = 170;
    private static final int W_SEV  = 110;
    private static final int W_ETA  = 110;
    private static final int W_DEPT = 150;
    private static final int W_AMB  = 120;
    private static final int W_ACT  = 90;

    public EmergencyRequestsView() {
        setStyle("-fx-background-color: " + C_PAGE_BG + ";");

        ScrollPane scroll = new ScrollPane(buildMainContent());
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(false);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("-fx-background: " + C_PAGE_BG + "; -fx-background-color: " + C_PAGE_BG + ";");

        VBox.setVgrow(scroll, Priority.ALWAYS);
        getChildren().add(scroll);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Main content
    // ─────────────────────────────────────────────────────────────────────────
    private VBox buildMainContent() {
        VBox content = new VBox(18);
        content.setPadding(new Insets(0, 30, 30, 30));
        content.setStyle("-fx-background-color: " + C_PAGE_BG + ";");
        content.getChildren().addAll(
            buildTopBar(),
            buildPageHeader(),
            buildFilterRow(),
            buildSummaryCards(),
            buildEmergencyTable(),
            buildSystemNotice()
        );
        return content;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Top bar (search + icons + profile)
    // ─────────────────────────────────────────────────────────────────────────
    private HBox buildTopBar() {
        HBox bar = new HBox(16);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(18, 0, 0, 0));

        HBox searchBox = new HBox(8);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(10, 16, 10, 16));
        searchBox.setStyle(
            "-fx-background-color: " + C_SEARCH_BG + ";" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: " + C_BORDER + ";" +
            "-fx-border-radius: 8;"
        );
        searchBox.setPrefWidth(320);

        Label searchIcon = new Label("\u2315");
        searchIcon.setFont(Font.font("System", FontWeight.BOLD, 10));
        searchIcon.setTextFill(Color.web(C_SUB_FG));

        Label searchHint = new Label("Search medical records, patients\u2026");
        searchHint.setFont(Font.font("System", FontWeight.BOLD, 13));
        searchHint.setTextFill(Color.web(C_LABEL_UP));
        searchBox.getChildren().addAll(searchIcon, searchHint);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        StackPane bell = iconCircleBtnWithDot("\uD83D\uDD14", 16);
        StackPane gear = iconCircleBtn("\u2699", 16);

        Region vDiv = new Region();
        vDiv.setPrefSize(1, 24);
        vDiv.setMinSize(1, 24);
        vDiv.setMaxSize(1, 24);
        vDiv.setStyle("-fx-background-color: " + C_BORDER + ";");
        HBox.setMargin(vDiv, new Insets(0, 8, 0, 8));

        HBox profile = buildProfileChip();
        bar.getChildren().addAll(searchBox, spacer, bell, gear, vDiv, profile);
        return bar;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Page header (title + Manual Entry button)
    // ─────────────────────────────────────────────────────────────────────────
    private HBox buildPageHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);
        Label title = new Label("Active Emergency Requests");
        title.setFont(Font.font("System", FontWeight.BOLD, 26));
        title.setTextFill(Color.web(C_HEADER_FG));

        Label subtitle = new Label("Real-time status monitoring for all incoming emergency cases.");
        subtitle.setFont(Font.font("System", FontWeight.BOLD, 13));
        subtitle.setTextFill(Color.web(C_SUB_FG));
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button manualEntry = new Button("\uff0b  Manual Entry");
        manualEntry.setFont(Font.font("System", FontWeight.BOLD, 13));
        manualEntry.setTextFill(Color.WHITE);
        String btnNormal =
            "-fx-background-color: " + C_FILTER_ACTV + ";" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 10 22 10 22;" +
            "-fx-cursor: hand;";
        String btnHover =
            "-fx-background-color: #0745a3;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 10 22 10 22;" +
            "-fx-cursor: hand;";
        manualEntry.setStyle(btnNormal);
        manualEntry.setOnMouseEntered(e -> manualEntry.setStyle(btnHover));
        manualEntry.setOnMouseExited(e  -> manualEntry.setStyle(btnNormal));

        header.getChildren().addAll(titleBox, spacer, manualEntry);
        return header;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Filter row
    // ─────────────────────────────────────────────────────────────────────────
    private HBox buildFilterRow() {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        Label filterLabel = new Label("FILTER SEVERITY:");
        filterLabel.setFont(Font.font("System", FontWeight.BOLD, 11));
        filterLabel.setTextFill(Color.web(C_LABEL_UP));

        Button all      = filterBtn("All Requests", true,  null, null);
        Button critical = filterBtn("Critical",     false, "4",  C_BADGE_RED);
        Button moderate = filterBtn("Moderate",     false, "8",  C_BADGE_ORANGE);
        Button minor    = filterBtn("Minor",        false, null, null);

        row.getChildren().addAll(filterLabel, all, critical, moderate, minor);
        return row;
    }

    /**
     * Filter button. When a badge count is supplied the number is rendered
     * as a styled bubble alongside the label text (using a graphic HBox).
     * badgeColor controls the circular badge background color.
     */
    private Button filterBtn(String text, boolean active, String badge, String badgeColor) {
        Button btn = new Button();
        btn.setCursor(javafx.scene.Cursor.HAND);

        if (badge != null) {
            HBox graphic = new HBox(6);
            graphic.setAlignment(Pos.CENTER_LEFT);

            Label textLbl = new Label(text);
            textLbl.setFont(Font.font("System", FontWeight.BOLD, 13));

            Label badgeLbl = new Label(badge);
            badgeLbl.setFont(Font.font("System", FontWeight.BOLD, 11));
            StackPane bubble = new StackPane(badgeLbl);
            bubble.setPrefSize(22, 22);
            bubble.setMinSize(22, 22);

            if (active) {
                textLbl.setTextFill(Color.WHITE);
                badgeLbl.setTextFill(Color.web(C_FILTER_ACTV));
                bubble.setStyle("-fx-background-color: white; -fx-background-radius: 50;");
            } else {
                textLbl.setTextFill(Color.web(C_HEADER_FG));
                badgeLbl.setTextFill(Color.WHITE);
                String bgColor = (badgeColor != null) ? badgeColor : C_HEADER_FG;
                bubble.setStyle(
                    "-fx-background-color: " + bgColor + ";" +
                    "-fx-background-radius: 50;"
                );
            }
            graphic.getChildren().addAll(textLbl, bubble);
            btn.setGraphic(graphic);
        } else {
            btn.setText(text);
            btn.setFont(Font.font("System", FontWeight.BOLD, 13));
            btn.setTextFill(active ? Color.WHITE : Color.web(C_HEADER_FG));
        }

        btn.setStyle(active
            ? "-fx-background-color: " + C_FILTER_ACTV + ";" +
              "-fx-background-radius: 20;" +
              "-fx-padding: 6 18 6 18;"
            : "-fx-background-color: " + C_WHITE + ";" +
              "-fx-background-radius: 20;" +
              "-fx-border-color: " + C_BORDER + ";" +
              "-fx-border-radius: 20;" +
              "-fx-padding: 6 18 6 18;"
        );
        return btn;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Summary cards
    // ─────────────────────────────────────────────────────────────────────────
    private HBox buildSummaryCards() {
        HBox row = new HBox(16);
        VBox c1 = summaryCard("CRITICAL CASES",     "04",   "\uD83D\uDCC8", C_CARD_RED,  C_CARD_RED_BG);
        VBox c2 = summaryCard("ACTIVE AMBULANCES",  "12",   "\uD83D\uDE91", C_CARD_BLUE, C_CARD_BLUE_BG);
        VBox c3 = summaryCard("AVG. RESPONSE TIME", "4.2m", "\u23F1",        C_CARD_ORG,  C_CARD_ORG_BG);
        VBox c4 = summaryCard("AVAILABLE ER BEDS",  "06",   "\uD83D\uDECF",  C_CARD_GRY,  C_CARD_GRY_BG);

        for (VBox card : List.of(c1, c2, c3, c4)) {
            HBox.setHgrow(card, Priority.ALWAYS);
        }
        row.getChildren().addAll(c1, c2, c3, c4);
        return row;
    }

    private VBox summaryCard(String labelText, String value,
                             String emoji, String iconFg, String iconBg) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(18));
        card.setStyle(
            "-fx-background-color: " + C_CARD_BG + ";" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: " + C_BORDER + ";" +
            "-fx-border-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.04), 6, 0, 0, 2);"
        );

        HBox top = new HBox(12);
        top.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(44, 44);
        iconBox.setMinSize(44, 44);
        iconBox.setStyle(
            "-fx-background-color: " + iconBg + ";" +
            "-fx-background-radius: 10;"
        );
        Label icon = new Label(emoji);
        icon.setFont(Font.font("System", 20));
        icon.setTextFill(Color.web(iconFg));
        iconBox.getChildren().add(icon);

        VBox textCol = new VBox(4);
        Label lbl = new Label(labelText);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 10));
        lbl.setTextFill(Color.web(C_LABEL_UP));

        Label val = new Label(value);
        val.setFont(Font.font("System", FontWeight.BOLD, 28));
        val.setTextFill(Color.web(C_HEADER_FG));

        textCol.getChildren().addAll(lbl, val);
        top.getChildren().addAll(iconBox, textCol);
        card.getChildren().add(top);
        return card;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Emergency table (header + data rows + footer)
    // ─────────────────────────────────────────────────────────────────────────
    private VBox buildEmergencyTable() {
        VBox wrapper = new VBox();
        wrapper.setStyle(
            "-fx-background-color: " + C_WHITE + ";" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: " + C_BORDER + ";" +
            "-fx-border-radius: 12;"
        );

        wrapper.getChildren().add(buildTableHeader());

        for (EmergencyRow r : sampleRows()) {
            Separator sep = new Separator(javafx.geometry.Orientation.HORIZONTAL);
            sep.setStyle("-fx-background-color: " + C_DIVIDER + ";");
            wrapper.getChildren().addAll(sep, buildDataRow(r));
        }

        Separator footerSep = new Separator(javafx.geometry.Orientation.HORIZONTAL);
        footerSep.setStyle("-fx-background-color: " + C_DIVIDER + ";");
        wrapper.getChildren().addAll(footerSep, buildTableFooter());

        return wrapper;
    }

    private HBox buildTableHeader() {
        HBox hdr = new HBox();
        hdr.setStyle(
            "-fx-background-color: " + C_TH_BG + ";" +
            "-fx-background-radius: 12 12 0 0;"
        );
        hdr.setPadding(new Insets(14, 18, 14, 18));
        hdr.setAlignment(Pos.CENTER_LEFT);
        hdr.getChildren().addAll(
            thCell("EMERGENCY\nID",      W_ID),
            thCell("PATIENT\nNAME",      W_NAME),
            thCell("EMERGENCY TYPE",     W_TYPE),
            thCell("SEVERITY",           W_SEV),
            thCell("ETA",                W_ETA),
            thCell("RECOMMENDED\nDEPT.", W_DEPT),
            thCell("AMBULANCE\nSTATUS",  W_AMB),
            thCell("ACTION",              W_ACT)
        );
        return hdr;
    }

    private Label thCell(String text, int width) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 10));
        lbl.setTextFill(Color.web(C_LABEL_UP));
        lbl.setMinWidth(width);
        lbl.setPrefWidth(width);
        lbl.setWrapText(true);
        return lbl;
    }

    private Label thCellGrow(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 10));
        lbl.setTextFill(Color.web(C_LABEL_UP));
        HBox.setHgrow(lbl, Priority.ALWAYS);
        return lbl;
    }

    private HBox buildDataRow(EmergencyRow r) {
        HBox row = new HBox();
        row.setPadding(new Insets(16, 18, 16, 18));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: " + C_WHITE + ";");
        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: " + C_ROW_HOVER + ";"));
        row.setOnMouseExited(e  -> row.setStyle("-fx-background-color: " + C_WHITE + ";"));

        // Emergency ID
        Label id = new Label(r.id);
        id.setFont(Font.font("System", FontWeight.BOLD, 12));
        id.setTextFill(Color.web(C_SUB_FG));
        id.setMinWidth(W_ID); id.setPrefWidth(W_ID);

        // Patient name
        Label name = new Label(r.patientName);
        name.setFont(Font.font("System", FontWeight.BOLD, 13));
        name.setTextFill(Color.web(C_HEADER_FG));
        name.setWrapText(true);
        name.setMinWidth(W_NAME); name.setPrefWidth(W_NAME);

        // Emergency type: icon chip + label
        HBox typeCell = new HBox(6);
        typeCell.setAlignment(Pos.CENTER_LEFT);
        typeCell.setMinWidth(W_TYPE); typeCell.setPrefWidth(W_TYPE);
        StackPane typeIconBox = new StackPane();
        typeIconBox.setPrefSize(30, 30); typeIconBox.setMinSize(30, 30);
        typeIconBox.setStyle("-fx-background-color: " + r.typeIconBg + "; -fx-background-radius: 6;");
        Label typeIcon = new Label(r.typeIcon);
        typeIcon.setFont(Font.font("System", FontWeight.BOLD, 13));
        typeIconBox.getChildren().add(typeIcon);
        Label typeLbl = new Label(r.emergencyType);
        typeLbl.setFont(Font.font("System", FontWeight.BOLD, 12));
        typeLbl.setTextFill(Color.web(C_HEADER_FG));
        typeLbl.setWrapText(true);
        typeCell.getChildren().addAll(typeIconBox, typeLbl);

        // Severity badge
        HBox badgeWrap = new HBox(severityBadge(r.severity));
        badgeWrap.setAlignment(Pos.CENTER_LEFT);
        badgeWrap.setMinWidth(W_SEV); badgeWrap.setPrefWidth(W_SEV);

        // ETA
        VBox etaCell = buildEtaCell(r.eta, r.etaSub);
        etaCell.setMinWidth(W_ETA); etaCell.setPrefWidth(W_ETA);

        // Recommended dept.
        HBox deptCell = new HBox(6);
        deptCell.setAlignment(Pos.CENTER_LEFT);
        deptCell.setMinWidth(W_DEPT); deptCell.setPrefWidth(W_DEPT);
        Circle dot = new Circle(4);
        // Use gray dot for Urgent Care, blue for other departments
        if (r.dept != null && r.dept.contains("Urgent Care")) {
            dot.setFill(Color.web(C_CARD_GRY));
        } else {
            dot.setFill(Color.web(C_FILTER_ACTV));
        }
        Label deptLbl = new Label(r.dept);
        deptLbl.setFont(Font.font("System", FontWeight.BOLD, 12));
        deptLbl.setTextFill(Color.web(C_HEADER_FG));
        deptLbl.setWrapText(true);
        deptCell.getChildren().addAll(dot, deptLbl);

        // Ambulance status
        HBox ambCell = buildAmbulanceCell(r.ambulanceStatus, r.ambulanceIcon);
        ambCell.setMinWidth(W_AMB); ambCell.setPrefWidth(W_AMB);

        // Action button
        Button actionBtn = buildAcceptBtn();
        HBox actCell = new HBox(actionBtn);
        actCell.setAlignment(Pos.CENTER_LEFT);
        actCell.setMinWidth(W_ACT); actCell.setPrefWidth(W_ACT);

        row.getChildren().addAll(
            id, name, typeCell, badgeWrap, etaCell,
            deptCell, ambCell, actCell
        );
        return row;
    }

    private HBox buildAmbulanceCell(String status, String icon) {
        HBox cell = new HBox(6);
        cell.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(28, 28); iconBox.setMinSize(28, 28);
        iconBox.setStyle("-fx-background-color: #EFF6FF; -fx-background-radius: 6;");
        Label icn = new Label(icon);
        icn.setFont(Font.font("System", FontWeight.BOLD, 14));
        iconBox.getChildren().add(icn);

        Label lbl = new Label(status);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 12));
        lbl.setTextFill(Color.web(C_HEADER_FG));
        lbl.setWrapText(true);
        cell.getChildren().addAll(iconBox, lbl);
        return cell;
    }

    private VBox buildEtaCell(String eta, String sub) {
        VBox cell = new VBox(2);
        cell.setAlignment(Pos.CENTER_LEFT);

        Label etaLbl = new Label(eta);
        etaLbl.setFont(Font.font("System", FontWeight.BOLD, 13));

        // Color logic per reference:
        // "2 min" → RED, "Arrived" → GREEN, others ("5 min", "12 min") → DARK
        if (eta.equals("Arrived")) {
            etaLbl.setTextFill(Color.web(C_ETA_GREEN));
        } else if (eta.equals("2 min")) {
            etaLbl.setTextFill(Color.web(C_ETA_RED));
        } else {
            etaLbl.setTextFill(Color.web(C_ETA_DARK));
        }
        cell.getChildren().add(etaLbl);

        if (sub != null) {
            Label subLbl = new Label(sub);
            subLbl.setFont(Font.font("System", FontPosture.ITALIC, 10));
            subLbl.setTextFill(Color.web(C_SUB_FG));
            cell.getChildren().add(subLbl);
        }
        return cell;
    }

    private StackPane severityBadge(String level) {
        Label lbl = new Label(level);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 10));

        String bg, fg, border;
        switch (level.toUpperCase()) {
            case "HIGH"   -> { bg = C_HIGH_BG; fg = C_HIGH_FG; border = C_HIGH_BORDER; }
            case "MEDIUM" -> { bg = C_MED_BG;  fg = C_MED_FG;  border = C_MED_BORDER;  }
            default       -> { bg = C_LOW_BG;  fg = C_LOW_FG;  border = C_LOW_BORDER;  }
        }
        lbl.setTextFill(Color.web(fg));

        StackPane sp = new StackPane(lbl);
        sp.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-background-radius: 4;" +
            "-fx-border-color: " + border + ";" +
            "-fx-border-radius: 4;" +
            "-fx-padding: 3 10 3 10;"
        );
        return sp;
    }

    private Button buildAcceptBtn() {
        Button btn = new Button("Accept");
        btn.setFont(Font.font("System", FontWeight.BOLD, 12));
        btn.setTextFill(Color.WHITE);
        String normal =
            "-fx-background-color: " + C_BTN_GREEN + ";" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 7 18 7 18;" +
            "-fx-cursor: hand;";
        String hovered =
            "-fx-background-color: " + C_BTN_GREEN_HV + ";" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 7 18 7 18;" +
            "-fx-cursor: hand;";
        btn.setStyle(normal);
        btn.setOnMouseEntered(e -> btn.setStyle(hovered));
        btn.setOnMouseExited(e  -> btn.setStyle(normal));
        return btn;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Table footer ("Showing N of M active requests" + pagination)
    // ─────────────────────────────────────────────────────────────────────────
    private HBox buildTableFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setPadding(new Insets(12, 18, 12, 18));

        Label info = new Label("Showing 4 of 24 active requests");
        info.setFont(Font.font("System", FontWeight.BOLD, 12));
        info.setTextFill(Color.web(C_SUB_FG));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button prev = paginationBtn("\u2039");
        Button next = paginationBtn("\u203A");

        footer.getChildren().addAll(info, spacer, prev, next);
        return footer;
    }

    private Button paginationBtn(String arrow) {
        Button btn = new Button(arrow);
        btn.setFont(Font.font("System", FontWeight.BOLD, 16));
        btn.setTextFill(Color.web(C_HEADER_FG));
        btn.setCursor(javafx.scene.Cursor.HAND);
        String normal =
            "-fx-background-color: " + C_WHITE + ";" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: " + C_BORDER + ";" +
            "-fx-border-radius: 6;" +
            "-fx-padding: 4 10 4 10;";
        String hovered =
            "-fx-background-color: " + C_TH_BG + ";" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: " + C_BORDER + ";" +
            "-fx-border-radius: 6;" +
            "-fx-padding: 4 10 4 10;";
        btn.setStyle(normal);
        btn.setOnMouseEntered(e -> btn.setStyle(hovered));
        btn.setOnMouseExited(e  -> btn.setStyle(normal));
        return btn;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  System notice banner
    // ─────────────────────────────────────────────────────────────────────────
    private HBox buildSystemNotice() {
        HBox panel = new HBox(14);
        panel.setAlignment(Pos.CENTER_LEFT);
        panel.setPadding(new Insets(16, 20, 16, 20));
        panel.setStyle(
            "-fx-background-color: " + C_NOTICE_BG + ";" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: " + C_NOTICE_BORDER + ";" +
            "-fx-border-radius: 12;"
        );

        StackPane iconCircle = new StackPane();
        iconCircle.setPrefSize(36, 36);
        iconCircle.setMinSize(36, 36);
        iconCircle.setStyle(
            "-fx-background-color: " + C_NOTICE_ICON + ";" +
            "-fx-background-radius: 50;"
        );
        Label infoIcon = new Label("\u2139");
        infoIcon.setFont(Font.font("System", FontWeight.BOLD, 16));
        infoIcon.setTextFill(Color.WHITE);
        iconCircle.getChildren().add(infoIcon);

        VBox textCol = new VBox(3);
        HBox.setHgrow(textCol, Priority.ALWAYS);
        Label noticeTitle = new Label("System Notice");
        noticeTitle.setFont(Font.font("System", FontWeight.BOLD, 13));
        noticeTitle.setTextFill(Color.web(C_NOTICE_TITLE));
        Label noticeBody = new Label(
            "All available ambulances in the Northern District are currently deployed. " +
            "Redirecting new Minor calls to St. Jude\u2019s."
        );
        noticeBody.setFont(Font.font("System", FontWeight.BOLD, 12));
        noticeBody.setTextFill(Color.web(C_NOTICE_TEXT));
        noticeBody.setWrapText(true);
        textCol.getChildren().addAll(noticeTitle, noticeBody);

        Label routeLink = new Label("View Routing Map");
        routeLink.setFont(Font.font("System", FontWeight.BOLD, 12));
        routeLink.setTextFill(Color.web(C_NOTICE_TITLE));
        routeLink.setCursor(javafx.scene.Cursor.HAND);
        routeLink.setStyle("-fx-underline: true;");

        panel.getChildren().addAll(iconCircle, textCol, routeLink);
        return panel;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Profile chip
    // ─────────────────────────────────────────────────────────────────────────
    private HBox buildProfileChip() {
        HBox chip = new HBox(10);
        chip.setAlignment(Pos.CENTER_LEFT);

        VBox nameBox = new VBox(1);
        nameBox.setAlignment(Pos.CENTER_RIGHT);
        Label name = new Label("Dr. Sarah Chen");
        name.setFont(Font.font("System", FontWeight.BOLD, 13));
        name.setTextFill(Color.web(C_HEADER_FG));
        Label role = new Label("Triage Supervisor");
        role.setFont(Font.font("System", FontWeight.BOLD, 11));
        role.setTextFill(Color.web(C_SUB_FG));
        nameBox.getChildren().addAll(name, role);

        StackPane avatar = new StackPane();
        Circle circle = new Circle(20);
        circle.setFill(Color.web("#C7D9F0"));
        Label initials = new Label("SC");
        initials.setFont(Font.font("System", FontWeight.BOLD, 12));
        initials.setTextFill(Color.web(C_FILTER_ACTV));
        avatar.getChildren().addAll(circle, initials);

        chip.getChildren().addAll(nameBox, avatar);
        return chip;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Shared helpers
    // ─────────────────────────────────────────────────────────────────────────
    private StackPane iconCircleBtn(String emoji, double fontSize) {
        Label lbl = new Label(emoji);
        lbl.setFont(Font.font("System", fontSize));
        lbl.setTextFill(Color.web(C_HEADER_FG));

        StackPane pane = new StackPane(lbl);
        pane.setPrefSize(36, 36);
        pane.setStyle(
            "-fx-background-color: " + C_WHITE + ";" +
            "-fx-background-radius: 50;" +
            "-fx-border-color: " + C_BORDER + ";" +
            "-fx-border-radius: 50;"
        );
        pane.setCursor(javafx.scene.Cursor.HAND);
        return pane;
    }

    /** Bell icon with a small red notification dot overlaid top-right. */
    private StackPane iconCircleBtnWithDot(String emoji, double fontSize) {
        StackPane base = iconCircleBtn(emoji, fontSize);

        Circle dot = new Circle(5);
        dot.setFill(Color.web("#EF4444"));
        dot.setStroke(Color.WHITE);
        dot.setStrokeWidth(1.5);
        StackPane.setAlignment(dot, Pos.TOP_RIGHT);
        StackPane.setMargin(dot, new Insets(3, 3, 0, 0));
        base.getChildren().add(dot);
        return base;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Sample data
    // ─────────────────────────────────────────────────────────────────────────
    private List<EmergencyRow> sampleRows() {
        return List.of(
            new EmergencyRow("#ER-8842", "James\nHarrison",  "Cardiac Arrest",        "\u2764",    "#FEF2F2", "HIGH",   "2 min",   "Arriving soon", "Cardiology\n(ER-A)",  "\uD83D\uDE91", "En route"),
            new EmergencyRow("#ER-8843", "Maria\nRodriguez", "Respiratory\nDistress",  "\uD83D\uDCA8","#FEF2F2", "MEDIUM","5 min",  null,            "Pulmonology\n(ER-B)", "\uD83D\uDE91", "En route"),
            new EmergencyRow("#ER-8844", "Robert\nFletcher", "Multi-Trauma",           "\uD83D\uDEA8","#FEF2F2", "HIGH", "Arrived", null,            "Trauma Unit\n(ER-A)", "\uD83D\uDCCD", "At Bay 4"),
            new EmergencyRow("#ER-8845", "Sarah\nJenkins",   "Minor\nLaceration",      "\uD83E\uDE79","#EFF6FF", "LOW",  "12 min",  null,            "Urgent Care\n(ER-C)", "\uD83D\uDE91", "En route")
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  Data model
    // ─────────────────────────────────────────────────────────────────────────
    private static class EmergencyRow {
        final String id, patientName, emergencyType, typeIcon, typeIconBg, severity;
        final String eta, etaSub, dept, ambulanceIcon, ambulanceStatus;

        EmergencyRow(String id, String patientName, String emergencyType, String typeIcon,
                     String typeIconBg, String severity, String eta, String etaSub,
                     String dept, String ambulanceIcon, String ambulanceStatus) {
            this.id              = id;
            this.patientName     = patientName;
            this.emergencyType   = emergencyType;
            this.typeIcon        = typeIcon;
            this.typeIconBg      = typeIconBg;
            this.severity        = severity;
            this.eta             = eta;
            this.etaSub          = etaSub;
            this.dept            = dept;
            this.ambulanceIcon   = ambulanceIcon;
            this.ambulanceStatus = ambulanceStatus;
        }
    }
}
