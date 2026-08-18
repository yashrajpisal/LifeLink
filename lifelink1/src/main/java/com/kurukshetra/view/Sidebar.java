package com.kurukshetra.view;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Sidebar extends VBox {

    //  NAV ITEMS ENUM
    public enum NavItem {
        DASHBOARD           ("Dashboard"),
        EMERGENCY_REQUESTS  ("Emergency Requests"),
        RESOURCE_MANAGEMENT ("Resource Management"),
        DOCTOR_MANAGEMENT   ("Doctor Management"),
        OPERATION_THEATRE   ("Operation Theatre"),
        PATIENT_RECORDS     ("Patient Records"),
        AMBULANCE_TRACKING  ("Ambulance Tracking"),
        NOTIFICATIONS       ("Notifications"),
        ANALYTICS           ("Analytics"),
        SETTINGS            ("Settings");

        private final String label;

        NavItem(String label) { this.label = label; }

        public String getLabel() { return label; }
    }

    //  COLOR CONSTANTS
    private static final String BG_SIDEBAR    = "#EEF5FB";   // light blue-grey (was #FFFFFF)
    private static final String BG_ACTIVE     = "#DDEEFF";   // active row tint
    private static final String BG_HOVER      = "#E3EEF8";   // hover row tint
    private static final String ACTIVE_COLOR  = "#1565C0";   // blue — active text/icon/bar
    private static final String INACTIVE_FG   = "#374151";   // dark charcoal — inactive items
    private static final String BORDER_COLOR  = "#C8D8EC";   // right-edge sidebar border
    private static final String SEPARATOR_COL = "#D0DCEA";   // thin divider lines
    private static final String LOGO_BRAND    = "#1A2456";   // "LifeLink" text colour
    private static final String LOGO_SUBTITLE = "#8A9BB8";   // "HOSPITAL MANAGEMENT" colour

    private static final double SIDEBAR_W = 220.0;
    private static final double ITEM_H    = 50.0;   // row height
    private static final double ACCENT_W  = 4.0;    // right-edge active indicator
    private static final double ICON_BOX  = 24.0;   // icon container size
    private static final double FONT_BODY = 13.0;
    private static final double STROKE    = 1.6;    // default stroke for drawn icons

    //  STATE
    private NavItem           activeItem;
    private Consumer<NavItem> onNavigateCallback;
    private Runnable          onLogoutCallback;

    //  CONSTRUCTORS
    public Sidebar(NavItem activeItem) {
        this.activeItem = activeItem;
        build();
    }

    public Sidebar(String activeLabel) {
        this.activeItem = null; // FIX: was NavItem.DASHBOARD — caused Dashboard to always appear highlighted
        for (NavItem it : NavItem.values()) {
            if (it.getLabel().equalsIgnoreCase(activeLabel.trim())) {
                this.activeItem = it;
                break;
            }
        }
        build();
    }

    //  PUBLIC API
    public void setOnNavigate(Consumer<NavItem> callback) {
        this.onNavigateCallback = callback;
    }
    public void setOnLogout(Runnable callback) {
        this.onLogoutCallback = callback;
    }

    /** Programmatically change the active item and refresh highlights. */
    public void setActiveItem(NavItem item) {
        this.activeItem = item;
        getChildren().clear();
        build();
    }

    //  BUILD — assembles header + nav list + logout into this VBox
    private void build() {
        setStyle(
            "-fx-background-color: " + BG_SIDEBAR + "; " +
            "-fx-border-color: transparent " + BORDER_COLOR + " transparent transparent; " +
            "-fx-border-width: 0 1 0 0;"
        );
        setPrefWidth(SIDEBAR_W);
        setMinWidth(SIDEBAR_W);
        setMaxWidth(SIDEBAR_W);
        setSpacing(0);
        setPadding(Insets.EMPTY);

        VBox header        = buildHeader();
        VBox navItems      = buildNavItems();
        VBox.setVgrow(navItems, Priority.ALWAYS); 
        VBox logoutSection = buildLogoutSection();

        getChildren().addAll(header, navItems, logoutSection);
    }

    //  HEADER  (logo asterisk + brand text + horizontal divider)
    private VBox buildHeader() {
        VBox header = new VBox(0);
        header.setPadding(new Insets(18, 16, 0, 16));
        header.setStyle("-fx-background-color: " + BG_SIDEBAR + ";");

        Label asterisk = new Label("\u2733");
        asterisk.setFont(Font.font("System", FontWeight.BOLD, 30));
        asterisk.setTextFill(Color.web(ACTIVE_COLOR));

        VBox brandBox = new VBox(1);
        brandBox.setAlignment(Pos.CENTER_LEFT);

        Label brandName = new Label("LifeLink");
        brandName.setFont(Font.font("System", FontWeight.BOLD, 18));
        brandName.setTextFill(Color.web(LOGO_BRAND));

        Label brandSub = new Label("HOSPITAL MANAGEMENT");
        brandSub.setFont(Font.font("System", FontWeight.NORMAL, 10));
        brandSub.setTextFill(Color.web(LOGO_SUBTITLE));
        brandBox.getChildren().addAll(brandName, brandSub);

        HBox logoRow = new HBox(8, asterisk, brandBox);
        logoRow.setAlignment(Pos.CENTER_LEFT);

        // Separator sep = new Separator();
        // sep.setStyle("-fx-border-color: " + SEPARATOR_COL + ";");
        // VBox.setMargin(sep, new Insets(14, 0, 0, 0));

        header.getChildren().addAll(logoRow);
        return header;
    }

    //  NAV ITEMS — builds one row per NavItem
    private VBox buildNavItems() {
        VBox container = new VBox(2);
        container.setPadding(new Insets(10, 0, 10, 0));
        for (NavItem item : NavItem.values()) {
            container.getChildren().add(buildNavRow(item));
        }
        return container;
    }

    private HBox buildNavRow(NavItem item) {
        boolean active = (item == activeItem);
        Color   initC  = Color.web(active ? ACTIVE_COLOR : INACTIVE_FG);

        List<Shape> iconShapes = new ArrayList<>();
        Node iconNode = buildIconNode(item, initC, iconShapes);

        StackPane iconWrapper = new StackPane(iconNode);
        iconWrapper.setPrefSize(ICON_BOX, ICON_BOX);
        iconWrapper.setMinSize(ICON_BOX, ICON_BOX);
        iconWrapper.setMaxSize(ICON_BOX, ICON_BOX);

        Label textLbl = new Label(item.getLabel());
        textLbl.setWrapText(true);
        textLbl.setMaxWidth(SIDEBAR_W - ICON_BOX - 28 - ACCENT_W);
        textLbl.setTextFill(initC);
        textLbl.setFont(active
            ? Font.font("System", FontWeight.BOLD,   FONT_BODY)
            : Font.font("System", FontWeight.NORMAL, FONT_BODY));
        HBox.setHgrow(textLbl, Priority.ALWAYS);

        // Content area (icon + label)
        HBox content = new HBox(10, iconWrapper, textLbl);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(0, 8, 0, 12));
        HBox.setHgrow(content, Priority.ALWAYS);

        // Right accent bar — filled only for the active item
        Region accent = new Region();
        accent.setPrefWidth(ACCENT_W);
        accent.setMinWidth(ACCENT_W);
        accent.setMaxWidth(ACCENT_W);
        accent.setPrefHeight(ITEM_H);
        accent.setStyle(active
            ? "-fx-background-color: " + ACTIVE_COLOR + ";"
            : "-fx-background-color: transparent;");

        // Full row
        HBox row = new HBox(0, content, accent);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPrefHeight(ITEM_H);
        row.setMaxWidth(Double.MAX_VALUE);
        row.setCursor(Cursor.HAND);
        row.setStyle(active
            ? "-fx-background-color: " + BG_ACTIVE + ";"
            : "-fx-background-color: transparent;");

        // Hover: highlight icon + text blue, tint row background
        row.setOnMouseEntered(e -> {
            if (item != activeItem) {
                row.setStyle("-fx-background-color: " + BG_HOVER + ";");
                Color hc = Color.web(ACTIVE_COLOR);
                textLbl.setTextFill(hc);
                recolourShapes(iconShapes, hc);
                if (iconNode instanceof Label lbl) lbl.setTextFill(hc);
            }
        });

        // Exit hover: restore inactive colours
        row.setOnMouseExited(e -> {
            if (item != activeItem) {
                row.setStyle("-fx-background-color: transparent;");
                Color ic = Color.web(INACTIVE_FG);
                textLbl.setTextFill(ic);
                recolourShapes(iconShapes, ic);
                if (iconNode instanceof Label lbl) lbl.setTextFill(ic);
            }
        });

        // Click: fire navigation callback with the clicked item
        row.setOnMouseClicked(e -> {
            if (onNavigateCallback != null) onNavigateCallback.accept(item);
        });

        return row;
    }

    //  ICON BUILDER  (FIX 5)
    private Node buildIconNode(NavItem item, Color c, List<Shape> shapes) {
        return switch (item) {
            case DASHBOARD           -> iconDashboard(c, shapes);
            case EMERGENCY_REQUESTS  -> iconUnicode("\u2733", c, 22, shapes); // ✳
            case RESOURCE_MANAGEMENT -> iconDocument(c, shapes);
            case DOCTOR_MANAGEMENT   -> iconDoctorCross(c, shapes);
            case OPERATION_THEATRE   -> iconTheatre(c, shapes);
            case PATIENT_RECORDS     -> iconClipboard(c, shapes);
            case AMBULANCE_TRACKING  -> iconLocationPin(c, shapes);
            case NOTIFICATIONS       -> iconBell(c, shapes);
            case ANALYTICS           -> iconBarChart(c, shapes);
            case SETTINGS            -> iconUnicode("\u2699", c, 22, shapes); // ⚙
        };
    }

    // ── DASHBOARD: 2×2 grid of filled rounded rectangles ─────────────────────
    private Pane iconDashboard(Color c, List<Shape> shapes) {
        Pane p = iconPane();
        double s = 8.0, g = 3.0; 
        double[][] pos = { {0, 0}, {s + g, 0}, {0, s + g}, {s + g, s + g} };
        for (double[] xy : pos) {
            Rectangle r = filledRect(xy[0], xy[1], s, s, 2, c);
            shapes.add(r);
            p.getChildren().add(r);
        }
        return p;
    }

    // ── RESOURCE MANAGEMENT: document outline with 3 text lines ──────────────
    private Pane iconDocument(Color c, List<Shape> shapes) {
        Pane p = iconPane();
        Rectangle outer = strokedRect(1, 0.5, 15, 19, 2, c);
        Line l1 = hline(4,  6, 13, c);
        Line l2 = hline(4, 10, 13, c);
        Line l3 = hline(4, 14, 10, c);
        shapes.addAll(List.of(outer, l1, l2, l3));
        p.getChildren().addAll(outer, l1, l2, l3);
        return p;
    }

    // ── DOCTOR MANAGEMENT: medical cross inside a rounded rectangle ────────────
    private Pane iconDoctorCross(Color c, List<Shape> shapes) {
        Pane p = iconPane();
        Rectangle outer = strokedRect(0.5, 0.5, 18, 18, 4, c);
        Rectangle hbar  = filledRect(4,   8.5, 11, 3, 1, c);    
        Rectangle vbar  = filledRect(8.5, 4,    3, 11, 1, c);   
        shapes.addAll(List.of(outer, hbar, vbar));
        p.getChildren().addAll(outer, hbar, vbar);
        return p;
    }

    // ── OPERATION THEATRE: crosshair inside a circle (surgical target) ─────────
    private Pane iconTheatre(Color c, List<Shape> shapes) {
        Pane p = iconPane();
        Circle outer = strokedCircle(10, 10, 8.5, c);
        Line   h     = seg(2, 10, 18, 10, c);   
        Line   v     = seg(10, 2, 10, 18, c);   
        Circle inner = strokedCircle(10, 10, 3, c);     
        shapes.addAll(List.of(outer, h, v, inner));
        p.getChildren().addAll(outer, h, v, inner);
        return p;
    }

    // ── PATIENT RECORDS: clipboard body + top clip tab + 3 ruled lines ─────────
    private Pane iconClipboard(Color c, List<Shape> shapes) {
        Pane p = iconPane();
        Rectangle body = strokedRect(1,   3,   16, 17, 2, c);   
        Rectangle tab  = strokedRect(6,   0.5,  7,  5, 2, c);   
        Line l1 = hline(4,  8, 14, c);
        Line l2 = hline(4, 12, 14, c);
        Line l3 = hline(4, 16, 11, c);
        shapes.addAll(List.of(body, tab, l1, l2, l3));
        p.getChildren().addAll(body, tab, l1, l2, l3);
        return p;
    }

    // ── AMBULANCE TRACKING: location pin (circle head + converging tail) ────────
    private Pane iconLocationPin(Color c, List<Shape> shapes) {
        Pane p = iconPane();
        Circle head  = strokedCircle(10, 7, 5.5, c);    
        Line   left  = seg(4.5,  11, 10, 20, c);            
        Line   right = seg(15.5, 11, 10, 20, c);        
        shapes.addAll(List.of(head, left, right));
        p.getChildren().addAll(head, left, right);
        return p;
    }

    // ── NOTIFICATIONS: bell (rounded body + base bar + clapper + stem) ──────────
    private Pane iconBell(Color c, List<Shape> shapes) {
        Pane p = iconPane();
        Rectangle body    = strokedRect(3,    3, 13, 12, 6, c);     
        Line      base    = seg(1, 14, 18, 14, c);                   
        Rectangle clapper = strokedRect(7.5, 15,  4,  3, 1, c);    
        Line      stem    = seg(10, 0, 10, 3, c);                    
        shapes.addAll(List.of(body, base, clapper, stem));
        p.getChildren().addAll(body, base, clapper, stem);
        return p;
    }

    // ── ANALYTICS: 3 vertical bars at varying heights (bar chart) ──────────────
    private Pane iconBarChart(Color c, List<Shape> shapes) {
        Pane p = iconPane();
        Rectangle b1 = filledRect(0,   8, 5, 12, 1, c);     
        Rectangle b2 = filledRect(7,   2, 5, 18, 1, c);    
        Rectangle b3 = filledRect(14, 11, 5,  9, 1, c);    
        shapes.addAll(List.of(b1, b2, b3));
        p.getChildren().addAll(b1, b2, b3);
        return p;
    }

    private Label iconUnicode(String ch, Color c, int size, List<Shape> shapes) {
        Label lbl = new Label(ch);
        lbl.setFont(Font.font("System", size));
        lbl.setTextFill(c);
        lbl.setAlignment(Pos.CENTER);
        return lbl;
    }

    //  SHAPE HELPERS — avoid repetition in icon drawers
    private Pane iconPane() {
        Pane p = new Pane();
        p.setPrefSize(20, 20);
        p.setMinSize(20, 20);
        p.setMaxSize(20, 20);
        return p;
    }

    private Rectangle filledRect(double x, double y, double w, double h,
                                  double arc, Color fill) {
        Rectangle r = new Rectangle(x, y, w, h);
        r.setArcWidth(arc * 2);
        r.setArcHeight(arc * 2);
        r.setFill(fill);
        r.setStroke(null);
        return r;
    }

    private Rectangle strokedRect(double x, double y, double w, double h, double arc, Color stroke) {
        Rectangle r = new Rectangle(x, y, w, h);
        r.setArcWidth(arc * 2);
        r.setArcHeight(arc * 2);
        r.setFill(Color.TRANSPARENT);
        r.setStroke(stroke);
        r.setStrokeWidth(STROKE);
        return r;
    }

    private Circle strokedCircle(double cx, double cy, double radius, Color stroke) {
        Circle c = new Circle(cx, cy, radius);
        c.setFill(Color.TRANSPARENT);
        c.setStroke(stroke);
        c.setStrokeWidth(STROKE);
        return c;
    }

    private Line seg(double x1, double y1, double x2, double y2, Color c) {
        Line l = new Line(x1, y1, x2, y2);
        l.setStroke(c);
        l.setStrokeWidth(STROKE);
        l.setStrokeLineCap(StrokeLineCap.ROUND);
        return l;
    }

    private Line hline(double x1, double y, double x2, Color c) {
        return seg(x1, y, x2, y, c);
    }

    private void recolourShapes(List<Shape> shapes, Color c) {
        for (Shape s : shapes) {
            if (s.getFill() != null && !Color.TRANSPARENT.equals(s.getFill())) {
                s.setFill(c);
            }
            if (s.getStroke() != null) {
                s.setStroke(c);
            }
        }
    }

    //  LOGOUT SECTION  (separator + drawn door-arrow icon + "Logout" label)
    private VBox buildLogoutSection() {

        // Logout icon: door frame + right-pointing exit arrow
        Pane logoutIcon   = iconPane();
        Color ic          = Color.web(INACTIVE_FG);
        Rectangle door    = strokedRect(0, 0, 12, 20, 1, ic);   
        Line arrowBody    = seg(11, 10, 19, 10, ic);                
        Line arrowHead1   = seg(16,  7, 19, 10, ic);                
        Line arrowHead2   = seg(16, 13, 19, 10, ic);                
        logoutIcon.getChildren().addAll(door, arrowBody, arrowHead1, arrowHead2);
        List<Shape> logoutShapes = List.of(door, arrowBody, arrowHead1, arrowHead2);

        StackPane iconWrapper = new StackPane(logoutIcon);
        iconWrapper.setPrefSize(ICON_BOX, ICON_BOX);
        iconWrapper.setMinSize(ICON_BOX, ICON_BOX);
        iconWrapper.setMaxSize(ICON_BOX, ICON_BOX);

        Label textLbl = new Label("Logout");
        textLbl.setFont(Font.font("System", FontWeight.NORMAL, FONT_BODY));
        textLbl.setTextFill(Color.web(INACTIVE_FG));

        HBox row = new HBox(10, iconWrapper, textLbl);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPrefHeight(ITEM_H);
        row.setPadding(new Insets(0, 8, 0, 12));
        row.setMaxWidth(Double.MAX_VALUE);
        row.setStyle("-fx-background-color: transparent;");
        row.setCursor(Cursor.HAND);

        row.setOnMouseEntered(e -> {
            row.setStyle("-fx-background-color: " + BG_HOVER + ";");
            Color hc = Color.web(ACTIVE_COLOR);
            textLbl.setTextFill(hc);
            recolourShapes(logoutShapes, hc);
        });
        row.setOnMouseExited(e -> {
            row.setStyle("-fx-background-color: transparent;");
            Color restoreC = Color.web(INACTIVE_FG);
            textLbl.setTextFill(restoreC);
            recolourShapes(logoutShapes, restoreC);
        });
        row.setOnMouseClicked(e -> {
            if (onLogoutCallback != null) onLogoutCallback.run();
        });

        Region bottomPad = new Region();
        bottomPad.setPrefHeight(18);

        VBox section = new VBox(0);
        VBox.setMargin(row, new Insets(4, 0, 0, 0));
        section.getChildren().addAll(row, bottomPad);
        return section;
    }
}
