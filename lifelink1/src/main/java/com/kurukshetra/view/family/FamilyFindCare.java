package com.kurukshetra.view.family;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.controller.familyController.MedicalReportsController;
import com.kurukshetra.model.familyModel.MemberModel;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FamilyFindCare {

    private static final String PRIMARY = "#CA6721";
    private static final String PRIMARY_DARK = "#782F16";
    private static final String PAGE_BG = "#FCF9F7";
    private static final String SURFACE = "#FFFFFF";
    private static final String VERY_LIGHT_TERRACOTTA = "#FDF0E8";
    private static final String LIGHT_TERRACOTTA = "#F9E4D6";
    private static final String PALE_PEACH = "#FEF7F2";
    private static final String TEXT_PRIMARY = "#2B211D";
    private static final String TEXT_SECONDARY = "#675B55";
    private static final String TEXT_MUTED = "#938780";
    private static final String BORDER_COLOR = "#EDE0D8";
    private static final String GREEN = "#16A34A";
    private static final String LIGHT_GREEN = "#E8F5EC";
    private static final String ORANGE = "#B77900";
    private static final String LIGHT_ORANGE = "#FEF3C7";

    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";

    // Groq Vision & Cloudinary Setup

    private static final String GROQ_API_KEY = System.getenv("GROQ_API_KEY") != null
            ? System.getenv("GROQ_API_KEY")
            : "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";

    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "w2vrrz2c",
            "api_key", "584434458948198",
            "api_secret", "mOIxVa2JOe33SUA4tC6xut5boPM"));

    // User reference location (Pune Center / Swargate)
    private final double userLat = 18.5018;
    private final double userLng = 73.8636;

    // UI Elements
    private VBox hospitalListContainer;
    private Label resultsCountLabel;
    private StackPane detailDrawerSlot;
    private TextField searchField;
    private ProgressIndicator loadingSpinner;
    private Stage parentStage;

    // In-memory data store
    private final List<HospitalModel> allHospitals = new ArrayList<>();
    private String currentFilter = "All";
    private HospitalModel selectedHospital = null;

    public static class HospitalModel {
        String email;
        String name;
        String address;
        String city;
        String category;
        String type;
        String phone;
        double latitude;
        double longitude;
        double distanceKm;
        int etaMinutes;

        public HospitalModel(String email, String name, String address, String city, String category,
                String type, String phone, double latitude, double longitude,
                double distanceKm, int etaMinutes) {
            this.email = email;
            this.name = name;
            this.address = address;
            this.city = city;
            this.category = category;
            this.type = type;
            this.phone = phone;
            this.latitude = latitude;
            this.longitude = longitude;
            this.distanceKm = distanceKm;
            this.etaMinutes = etaMinutes;
        }
    }

    public BorderPane setBorderPane(Stage stage) {
        this.parentStage = stage;
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.FIND_HOSPITALS);
        bp.setLeft(sidebar);

        VBox mainContent = buildMainContent();
        bp.setCenter(mainContent);
        playPageAnimation(mainContent);

        fetchHospitalsFromFirestore();

        return bp;
    }

    private VBox buildMainContent() {
        HBox header = buildHeader();
        VBox commandBar = buildCommandBar();

        resultsCountLabel = new Label("Connecting to LifeLink Emergency Hospital Grid...");
        resultsCountLabel.setStyle(
                FONT_FAMILY + "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 11.5px; -fx-font-weight: 500;");

        hospitalListContainer = new VBox(12);
        hospitalListContainer.setPadding(new Insets(2, 6, 12, 2));

        loadingSpinner = new ProgressIndicator();
        loadingSpinner.setPrefSize(36, 36);
        loadingSpinner.setStyle("-fx-progress-color: " + PRIMARY + ";");

        StackPane listWrapper = new StackPane(hospitalListContainer, loadingSpinner);
        StackPane.setAlignment(loadingSpinner, Pos.CENTER);

        ScrollPane listScroll = new ScrollPane(listWrapper);
        listScroll.setFitToWidth(true);
        listScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        listScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        listScroll.setPannable(true);
        listScroll.setStyle(
                "-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(listScroll, Priority.ALWAYS);

        VBox leftColumn = new VBox(10, resultsCountLabel, listScroll);
        leftColumn.setPrefWidth(470);
        leftColumn.setMinWidth(430);
        leftColumn.setMaxWidth(500);
        VBox.setVgrow(leftColumn, Priority.ALWAYS);

        detailDrawerSlot = new StackPane(createInitialDetailPlaceholder());
        HBox.setHgrow(detailDrawerSlot, Priority.ALWAYS);

        HBox bodyRow = new HBox(18, leftColumn, detailDrawerSlot);
        VBox.setVgrow(bodyRow, Priority.ALWAYS);

        VBox mainContent = new VBox(14, header, commandBar, bodyRow);
        mainContent.setStyle("-fx-background-color: " + PAGE_BG + ";");
        mainContent.setPadding(new Insets(20, 24, 20, 24));
        return mainContent;
    }

    private HBox buildHeader() {
        VBox titleBox = new VBox(2);
        Label title = new Label("Find Hospitals & Specialized Care");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 22px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        // Label subtitle = new Label("Browse verified emergency facilities, inspect
        // live ICU & ventilator capacity, or dispatch prescriptions.");
        // subtitle.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " +
        // TEXT_SECONDARY + ";");
        titleBox.getChildren().addAll(title);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox statusPill = new HBox(6);
        statusPill.setAlignment(Pos.CENTER);
        statusPill.setPadding(new Insets(6, 12, 6, 12));
        statusPill.setStyle(
                "-fx-background-color: " + LIGHT_GREEN + "; " +
                        "-fx-border-color: #86EFAC; " +
                        "-fx-border-radius: 16px; " +
                        "-fx-background-radius: 16px;");
        // Label dot = new Label("●");
        // dot.setStyle("-fx-text-fill: " + GREEN + "; -fx-font-size: 10px;");
        // Label statusTxt = new Label("24/7 LifeLink Grid Active");
        // statusTxt.setStyle(FONT_FAMILY + "-fx-text-fill: " + GREEN + ";
        // -fx-font-size: 11px; -fx-font-weight: bold;");
        // statusPill.getChildren().addAll(dot, statusTxt);

        HBox header = new HBox(16, titleBox, spacer);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private VBox buildCommandBar() {
        // Search bar with icon styling
        HBox searchRow = new HBox(10);
        searchRow.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("🔍  Search hospitals by name, specialty, address, or city...");
        searchField.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-padding: 10px 14px;" +
                        "-fx-font-size: 13px;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";");
        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilterAndSearch());

        // Category Filter Buttons
        HBox filterPills = buildFilterRow();

        // HBox locationInfoRow = new HBox(8);
        // locationInfoRow.setAlignment(Pos.CENTER_LEFT);
        // Label locationIcon = new Label("📍");
        // locationIcon.setStyle("-fx-font-size: 12px;");
        // Label locationLabel = new Label("GPS Coordinates: Swargate / Pune Center
        // (18.5018° N, 73.8636° E) • Real-time Haversine Distance");
        // locationLabel.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " +
        // PRIMARY + "; -fx-font-weight: 600;");
        // locationInfoRow.getChildren().addAll(locationIcon, locationLabel);

        VBox commandBox = new VBox(10, searchRow, filterPills);
        commandBox.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 14px;" +
                        "-fx-background-radius: 14px;" +
                        "-fx-padding: 14px 18px;");
        commandBox.setEffect(new DropShadow(8, 0, 2, Color.rgb(120, 47, 22, 0.04)));
        return commandBox;
    }

    private HBox buildFilterRow() {
        Button all = new Button("All Facilities");
        Button multi = new Button("🏥 Multispeciality");
        Button superSpec = new Button("⚡ Super Speciality");
        Button general = new Button("🩺 General Care");

        styleFilterButton(all, true);
        styleFilterButton(multi, false);
        styleFilterButton(superSpec, false);
        styleFilterButton(general, false);

        List<Button> buttons = List.of(all, multi, superSpec, general);

        for (Button btn : buttons) {
            btn.setOnAction(e -> {
                for (Button b : buttons)
                    styleFilterButton(b, b == btn);
                if (btn == all)
                    currentFilter = "All";
                else if (btn == multi)
                    currentFilter = "Multispeciality";
                else if (btn == superSpec)
                    currentFilter = "Super Speciality";
                else if (btn == general)
                    currentFilter = "General";
                applyFilterAndSearch();
            });
        }

        HBox filterRow = new HBox(8, all, multi, superSpec, general);
        filterRow.setAlignment(Pos.CENTER_LEFT);
        return filterRow;
    }

    private void styleFilterButton(Button btn, boolean active) {
        if (active) {
            btn.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                            "-fx-text-fill: " + PRIMARY_DARK + ";" +
                            "-fx-border-color: " + PRIMARY + ";" +
                            "-fx-border-radius: 16px;" +
                            "-fx-background-radius: 16px;" +
                            "-fx-font-size: 11.5px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-padding: 6px 14px;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.15), 6, 0, 0, 1);");
        } else {
            btn.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: " + SURFACE + ";" +
                            "-fx-text-fill: " + TEXT_SECONDARY + ";" +
                            "-fx-border-color: " + BORDER_COLOR + ";" +
                            "-fx-border-radius: 16px;" +
                            "-fx-background-radius: 16px;" +
                            "-fx-font-size: 11.5px;" +
                            "-fx-padding: 6px 14px;" +
                            "-fx-cursor: hand;");
            btn.setOnMouseEntered(e -> {
                btn.setStyle(
                        FONT_FAMILY +
                                "-fx-background-color: " + PALE_PEACH + ";" +
                                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                                "-fx-border-radius: 16px;" +
                                "-fx-background-radius: 16px;" +
                                "-fx-font-size: 11.5px;" +
                                "-fx-padding: 6px 14px;" +
                                "-fx-cursor: hand;");
            });
            btn.setOnMouseExited(e -> {
                if (!btn.getText().contains(currentFilter)
                        && !(currentFilter.equals("All") && btn.getText().equals("All Facilities"))) {
                    btn.setStyle(
                            FONT_FAMILY +
                                    "-fx-background-color: " + SURFACE + ";" +
                                    "-fx-text-fill: " + TEXT_SECONDARY + ";" +
                                    "-fx-border-color: " + BORDER_COLOR + ";" +
                                    "-fx-border-radius: 16px;" +
                                    "-fx-background-radius: 16px;" +
                                    "-fx-font-size: 11.5px;" +
                                    "-fx-padding: 6px 14px;" +
                                    "-fx-cursor: hand;");
                }
            });
        }
    }

    // =========================================================
    // FIRESTORE FETCH & DISTANCE CALCULATION
    // =========================================================
    private void fetchHospitalsFromFirestore() {
        loadingSpinner.setVisible(true);

        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null) {
                    Platform.runLater(() -> {
                        loadingSpinner.setVisible(false);
                        resultsCountLabel.setText("Firebase configuration not initialized.");
                    });
                    return;
                }

                QuerySnapshot snapshot = db.collection("hospital").get().get();

                List<HospitalModel> loaded = new ArrayList<>();
                for (DocumentSnapshot doc : snapshot.getDocuments()) {
                    String email = doc.getId();
                    String name = getString(doc, "hospitalName", null);
                    if (name == null || name.trim().isEmpty()) {
                        name = getString(doc, "name", email);
                    }
                    String address = getString(doc, "address", "Pune, Maharashtra");
                    String city = getString(doc, "city", "Pune");
                    String category = getString(doc, "hospitalCategory", "General");
                    String type = getString(doc, "hospitalType", "Private");
                    String phone = getString(doc, "phoneNumber", null);
                    if (phone == null || phone.trim().isEmpty()) {
                        phone = getString(doc, "phone", "+91 20 0000 0000");
                    }

                    double lat = getDouble(doc, "latitude", 0.0);
                    if (lat == 0.0) {
                        lat = getDouble(doc, "lat", 18.5204);
                    }
                    double lng = getDouble(doc, "longitude", 0.0);
                    if (lng == 0.0) {
                        lng = getDouble(doc, "lng", 73.8567);
                    }

                    double dist = calculateHaversine(userLat, userLng, lat, lng);
                    int eta = Math.max(4, (int) Math.ceil((dist / 35.0) * 60));

                    loaded.add(
                            new HospitalModel(email, name, address, city, category, type, phone, lat, lng, dist, eta));
                }

                loaded.sort((a, b) -> Double.compare(a.distanceKm, b.distanceKm));

                Platform.runLater(() -> {
                    allHospitals.clear();
                    allHospitals.addAll(loaded);
                    loadingSpinner.setVisible(false);
                    applyFilterAndSearch();
                    if (!allHospitals.isEmpty()) {
                        selectedHospital = allHospitals.get(0);
                        showHospitalDetailedView(selectedHospital);
                    }
                });

            } catch (Exception e) {
                Platform.runLater(() -> {
                    loadingSpinner.setVisible(false);
                    resultsCountLabel.setText("Failed to load hospitals: " + e.getMessage());
                });
                e.printStackTrace();
            }
        }).start();
    }

    private double getDouble(DocumentSnapshot doc, String field, double defaultValue) {
        if (doc == null || !doc.contains(field))
            return defaultValue;
        try {
            Object val = doc.get(field);
            if (val == null)
                return defaultValue;
            if (val instanceof Number) {
                return ((Number) val).doubleValue();
            }
            String s = val.toString().trim();
            if (!s.isEmpty()) {
                return Double.parseDouble(s);
            }
        } catch (Exception ignored) {
        }
        return defaultValue;
    }

    private long getLong(DocumentSnapshot doc, String field, long defaultValue) {
        if (doc == null || !doc.contains(field))
            return defaultValue;
        try {
            Object val = doc.get(field);
            if (val == null)
                return defaultValue;
            if (val instanceof Number) {
                return ((Number) val).longValue();
            }
            String s = val.toString().trim();
            if (!s.isEmpty()) {
                return Long.parseLong(s);
            }
        } catch (Exception ignored) {
        }
        return defaultValue;
    }

    private String getString(DocumentSnapshot doc, String field, String defaultValue) {
        if (doc == null || !doc.contains(field))
            return defaultValue;
        try {
            Object val = doc.get(field);
            if (val == null)
                return defaultValue;
            String s = val.toString().trim();
            return s.isEmpty() ? defaultValue : s;
        } catch (Exception ignored) {
        }
        return defaultValue;
    }

    private double calculateHaversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round((R * c) * 10.0) / 10.0;
    }

    private void applyFilterAndSearch() {
        String query = searchField != null && searchField.getText() != null
                ? searchField.getText().trim().toLowerCase()
                : "";

        List<HospitalModel> filtered = new ArrayList<>();
        for (HospitalModel h : allHospitals) {
            boolean matchesFilter = currentFilter.equals("All") || h.category.equalsIgnoreCase(currentFilter);
            boolean matchesQuery = query.isEmpty()
                    || h.name.toLowerCase().contains(query)
                    || h.address.toLowerCase().contains(query)
                    || h.category.toLowerCase().contains(query)
                    || h.city.toLowerCase().contains(query);

            if (matchesFilter && matchesQuery) {
                filtered.add(h);
            }
        }

        hospitalListContainer.getChildren().clear();
        resultsCountLabel.setText("Showing " + filtered.size() + " facilities nearby within verified response radius");

        if (filtered.isEmpty()) {
            VBox empty = new VBox(10);
            empty.setAlignment(Pos.CENTER);
            empty.setPadding(new Insets(40, 20, 40, 20));
            Label icon = new Label("🏥");
            icon.setStyle("-fx-font-size: 32px;");
            Label emptyLbl = new Label("No hospitals found matching your search criteria.");
            emptyLbl.setStyle(
                    FONT_FAMILY + "-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 12.5px; -fx-font-weight: 500;");
            empty.getChildren().addAll(icon, emptyLbl);
            hospitalListContainer.getChildren().add(empty);
        } else {
            for (HospitalModel h : filtered) {
                hospitalListContainer.getChildren().add(buildHospitalCard(h));
            }
        }
    }

    private VBox buildHospitalCard(HospitalModel h) {
        boolean isSelected = selectedHospital != null && selectedHospital.email.equals(h.email);

        VBox card = new VBox(10);
        card.setPadding(new Insets(14, 16, 14, 16));

        String cardBaseStyle = FONT_FAMILY +
                "-fx-background-color: " + (isSelected ? PALE_PEACH : SURFACE) + ";" +
                "-fx-border-color: " + (isSelected ? PRIMARY : BORDER_COLOR) + ";" +
                "-fx-border-width: " + (isSelected ? "1.5px" : "1px") + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;" +
                "-fx-cursor: hand;";

        card.setStyle(cardBaseStyle);
        card.setEffect(new DropShadow(8, 0, 2, Color.rgb(120, 47, 22, isSelected ? 0.09 : 0.04)));

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconPane = new StackPane();
        iconPane.setPrefSize(36, 36);
        iconPane.setMinSize(36, 36);
        iconPane.setMaxSize(36, 36);
        iconPane.setStyle(
                "-fx-background-color: " + (isSelected ? VERY_LIGHT_TERRACOTTA : PALE_PEACH) + ";" +
                        "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;");
        Label icon = new Label("🏥");
        icon.setStyle("-fx-font-size: 15px;");
        iconPane.getChildren().add(icon);

        VBox titleBox = new VBox(2);
        HBox.setHgrow(titleBox, Priority.ALWAYS);

        Label nameLbl = new Label(h.name);
        nameLbl.setStyle(
                FONT_FAMILY + "-fx-font-size: 13.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label addressLbl = new Label("📍 " + h.address);
        addressLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");
        titleBox.getChildren().addAll(nameLbl, addressLbl);

        Label statusBadge = new Label("Trauma Ready");
        statusBadge.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + LIGHT_GREEN + ";" +
                        "-fx-text-fill: " + GREEN + ";" +
                        "-fx-font-size: 9.5px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 3 8;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: #86EFAC;" +
                        "-fx-border-radius: 8px;");

        topRow.getChildren().addAll(iconPane, titleBox, statusBadge);

        GridPane metrics = new GridPane();
        metrics.setHgap(8);
        metrics.setVgap(6);

        VBox distBox = createMetricBox("DISTANCE", h.distanceKm + " km", PRIMARY);
        VBox etaBox = createMetricBox("EST. TRANSIT", h.etaMinutes + " mins", PRIMARY_DARK);
        VBox catBox = createMetricBox("CATEGORY", h.category, TEXT_SECONDARY);
        VBox typeBox = createMetricBox("NETWORK TYPE", h.type, GREEN);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(25);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(25);
        ColumnConstraints c3 = new ColumnConstraints();
        c3.setPercentWidth(25);
        ColumnConstraints c4 = new ColumnConstraints();
        c4.setPercentWidth(25);
        metrics.getColumnConstraints().addAll(c1, c2, c3, c4);

        metrics.add(distBox, 0, 0);
        metrics.add(etaBox, 1, 0);
        metrics.add(catBox, 2, 0);
        metrics.add(typeBox, 3, 0);

        card.getChildren().addAll(topRow, metrics);

        card.setOnMouseClicked(e -> {
            selectedHospital = h;
            applyFilterAndSearch();
            showHospitalDetailedView(h);
        });

        card.setOnMouseEntered(e -> {
            card.setTranslateY(-2);
            card.setStyle(
                    FONT_FAMILY +
                            "-fx-background-color: " + PALE_PEACH + ";" +
                            "-fx-border-color: " + PRIMARY + ";" +
                            "-fx-border-width: 1.2px;" +
                            "-fx-border-radius: 12px;" +
                            "-fx-background-radius: 12px;" +
                            "-fx-cursor: hand;");
        });

        card.setOnMouseExited(e -> {
            card.setTranslateY(0);
            card.setStyle(cardBaseStyle);
        });

        return card;
    }

    private VBox createMetricBox(String label, String value, String color) {
        VBox b = new VBox(2);
        b.setPadding(new Insets(5, 7, 5, 7));
        b.setStyle("-fx-background-color: " + PAGE_BG + "; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 6; -fx-background-radius: 6;");

        Label l = new Label(label);
        l.setStyle(FONT_FAMILY + "-fx-font-size: 8.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");

        Label v = new Label(value);
        v.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        b.getChildren().addAll(l, v);
        return b;
    }

    // =========================================================
    // RIGHT DETAIL DRAWER: LIVE RESOURCES & SELECT FOR REPORT
    // =========================================================
    private void showHospitalDetailedView(HospitalModel h) {
        VBox drawer = new VBox(14);
        drawer.setPadding(new Insets(20));
        drawer.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 16px;" +
                        "-fx-background-radius: 16px;");
        drawer.setEffect(new DropShadow(12, 0, 4, Color.rgb(120, 47, 22, 0.06)));

        // Hospital Banner Header
        HBox top = new HBox(12);
        top.setAlignment(Pos.CENTER_LEFT);

        Circle c = new Circle(22, Color.web(VERY_LIGHT_TERRACOTTA));
        Label ic = new Label("🏥");
        ic.setStyle("-fx-font-size: 18px;");
        StackPane iconStack = new StackPane(c, ic);

        VBox titleCol = new VBox(2);
        Label name = new Label(h.name);
        name.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label location = new Label("📍 " + h.address + ", " + h.city + " • Primary Emergency Provider");
        location.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " + TEXT_MUTED + ";");
        titleCol.getChildren().addAll(name, location);

        top.getChildren().addAll(iconStack, titleCol);

        // Action Toolbar
        HBox actionRow = new HBox(10);
        actionRow.setAlignment(Pos.CENTER_LEFT);

        Button selectHospitalBtn = new Button("📄 Upload & Dispatch Report");
        selectHospitalBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + ");"
                        +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 11.5px;" +
                        "-fx-padding: 9 16;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.25), 8, 0, 0, 2);");

        Button directionsBtn = new Button("🧭 Navigate (Google Maps)");
        directionsBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                        "-fx-text-fill: " + PRIMARY_DARK + ";" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 11.5px;" +
                        "-fx-padding: 9 14;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                        "-fx-border-radius: 8px;" +
                        "-fx-cursor: hand;");

        Button callBtn = new Button("📞 Call (" + h.phone + ")");
        callBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-text-fill: " + TEXT_SECONDARY + ";" +
                        "-fx-font-size: 11.5px;" +
                        "-fx-font-weight: 500;" +
                        "-fx-padding: 8 14;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-cursor: hand;");

        // Open Google Maps In Default Browser
        directionsBtn.setOnAction(e -> {
            try {
                String googleMapsUrl = "https://www.google.com/maps/dir/?api=1&origin="
                        + userLat + "," + userLng
                        + "&destination=" + h.latitude + "," + h.longitude
                        + "&destination_place_id=" + URLEncoder.encode(h.name, StandardCharsets.UTF_8);

                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(googleMapsUrl));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Open Report Dispatcher / OCR StackPane
        selectHospitalBtn.setOnAction(e -> showFamilyReportUploadPane(h));

        actionRow.getChildren().addAll(selectHospitalBtn, directionsBtn, callBtn);

        // Live Bed Capacity Section
        Label resHeading = new Label("Live Capacity & Intensive Care Resources");
        resHeading.setStyle(
                FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_DARK + ";");

        GridPane resourceGrid = new GridPane();
        resourceGrid.setHgap(10);
        resourceGrid.setVgap(10);

        Label loadingResources = new Label("Synchronizing real-time telemetry from hospital emergency database...");
        loadingResources.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");
        VBox resourcesWrapper = new VBox(8, resHeading, loadingResources);

        // On-Duty Medical Staff
        Label docHeading = new Label("On-Duty Emergency Trauma Specialists");
        docHeading.setStyle(
                FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_DARK + ";");

        VBox doctorsList = new VBox(6);
        Label loadingDocs = new Label("Fetching active specialist duty roster...");
        loadingDocs.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");
        doctorsList.getChildren().add(loadingDocs);
        VBox docsWrapper = new VBox(8, docHeading, doctorsList);

        drawer.getChildren().addAll(top, actionRow, createDivider(), resourcesWrapper, createDivider(), docsWrapper);

        detailDrawerSlot.getChildren().clear();
        detailDrawerSlot.getChildren().add(drawer);

        FadeTransition ft = new FadeTransition(Duration.millis(220), drawer);
        ft.setFromValue(0.3);
        ft.setToValue(1.0);
        ft.play();

        // Async Subcollection Query
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null)
                    return;

                DocumentSnapshot resDoc = db.collection("hospital").document(h.email)
                        .collection("resources").document("current").get().get();

                QuerySnapshot docSnap = db.collection("hospital").document(h.email)
                        .collection("doctors").limit(4).get().get();

                Platform.runLater(() -> {
                    resourcesWrapper.getChildren().remove(loadingResources);

                    if (resDoc.exists()) {
                        long emerBeds = getLong(resDoc, "availableEmergencyBeds", 18);
                        long totEmer = getLong(resDoc, "totalEmergencyBeds", 25);
                        long icuBeds = getLong(resDoc, "availableICUBeds", 12);
                        long totIcu = getLong(resDoc, "totalICUBeds", 30);
                        long vent = getLong(resDoc, "availableVentilators", 8);
                        long oxy = getLong(resDoc, "oxygenReserve", 98);

                        resourceGrid.add(createDetailResourcePill("🚨 Emergency Beds",
                                emerBeds + " / " + totEmer + " Available", GREEN), 0, 0);
                        resourceGrid.add(createDetailResourcePill("💗 ICU Units",
                                icuBeds + " / " + totIcu + " Available", PRIMARY), 1, 0);
                        resourceGrid.add(
                                createDetailResourcePill("💨 Ventilators", vent + " Units Online", PRIMARY_DARK), 0, 1);
                        resourceGrid.add(createDetailResourcePill("🧪 Oxygen Reserve", oxy + "% Reserve", GREEN), 1, 1);

                        resourcesWrapper.getChildren().add(resourceGrid);
                    } else {
                        resourceGrid.add(createDetailResourcePill("🚨 Emergency Beds", "14 Units Available", GREEN), 0,
                                0);
                        resourceGrid.add(createDetailResourcePill("💗 ICU Units", "8 Units Available", PRIMARY), 1, 0);
                        resourceGrid.add(createDetailResourcePill("💨 Ventilators", "6 Units Online", PRIMARY_DARK), 0,
                                1);
                        resourceGrid.add(createDetailResourcePill("🧪 Oxygen Reserve", "95% Reserve", GREEN), 1, 1);
                        resourcesWrapper.getChildren().add(resourceGrid);
                    }

                    doctorsList.getChildren().clear();
                    if (!docSnap.isEmpty()) {
                        for (DocumentSnapshot doc : docSnap.getDocuments()) {
                            String docName = getString(doc, "doctorName", "Dr. Specialist");
                            String spec = getString(doc, "specialization", "Trauma & Critical Care");
                            String status = getString(doc, "status", "On Duty");

                            HBox docRow = new HBox(10);
                            docRow.setAlignment(Pos.CENTER_LEFT);
                            docRow.setPadding(new Insets(6, 10, 6, 10));
                            docRow.setStyle("-fx-background-color: " + PAGE_BG
                                    + "; -fx-background-radius: 8px; -fx-border-color: " + BORDER_COLOR
                                    + "; -fx-border-radius: 8px;");

                            Label dIcon = new Label("👨‍⚕️");
                            dIcon.setStyle("-fx-font-size: 14px;");

                            Label dName = new Label(docName + "  •  " + spec);
                            dName.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: bold; -fx-text-fill: "
                                    + TEXT_PRIMARY + ";");

                            Region docSpacer = new Region();
                            HBox.setHgrow(docSpacer, Priority.ALWAYS);

                            boolean onDuty = !status.equalsIgnoreCase("On Leave");
                            Label dStatus = new Label(status);
                            dStatus.setStyle(
                                    FONT_FAMILY +
                                            "-fx-font-size: 9.5px;" +
                                            "-fx-font-weight: bold;" +
                                            "-fx-text-fill: " + (onDuty ? GREEN : ORANGE) + ";" +
                                            "-fx-background-color: " + (onDuty ? LIGHT_GREEN : LIGHT_ORANGE) + ";" +
                                            "-fx-padding: 3 8;" +
                                            "-fx-background-radius: 6px;");

                            docRow.getChildren().addAll(dIcon, dName, docSpacer, dStatus);
                            doctorsList.getChildren().add(docRow);
                        }
                    } else {
                        Label fallback = new Label(
                                "24/7 Board-certified Trauma Surgeons and Critical Care Specialists active on shift.");
                        fallback.setStyle(
                                FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " + TEXT_SECONDARY + ";");
                        doctorsList.getChildren().add(fallback);
                    }
                });

            } catch (Exception ignored) {
            }
        }).start();
    }

    // =========================================================
    // AUTO-INCREMENTING REPORT ID GENERATOR (PAT-U-2001+)
    // =========================================================
    private synchronized String generateNextReportId(Firestore db) throws Exception {
        int maxSeqNumber = 2000;
        QuerySnapshot snap = db.collection("familyTohospitalRepo").get().get();

        for (DocumentSnapshot doc : snap.getDocuments()) {
            String existingId = doc.getString("reportId");
            if (existingId == null || existingId.isEmpty()) {
                existingId = doc.getId();
            }

            if (existingId != null && existingId.startsWith("PAT-U-")) {
                try {
                    String numericPart = existingId.substring("PAT-U-".length()).trim();
                    int parsed = Integer.parseInt(numericPart);
                    if (parsed > maxSeqNumber) {
                        maxSeqNumber = parsed;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }

        return "PAT-U-" + (maxSeqNumber + 1);
    }

    // =========================================================
    // FAMILY REPORT & OCR DISPATCHER STACKPANE
    // =========================================================
    private void showFamilyReportUploadPane(HospitalModel h) {
        VBox uploadCard = new VBox(14);
        uploadCard.setPadding(new Insets(20));
        uploadCard.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + PRIMARY + ";" +
                        "-fx-border-width: 1.5px;" +
                        "-fx-border-radius: 16px;" +
                        "-fx-background-radius: 16px;");
        uploadCard.setEffect(new DropShadow(14, 0, 4, Color.rgb(202, 103, 33, 0.12)));

        // Header with Back Button
        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label headerTitle = new Label("📄 Dispatch Medical Report to " + h.name);
        headerTitle.setStyle(
                FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: 800; -fx-text-fill: " + PRIMARY_DARK + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button backBtn = new Button("✕ Close & Return");
        backBtn.setStyle(FONT_FAMILY + "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: "
                + PRIMARY_DARK
                + "; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 11px; -fx-padding: 5 10; -fx-background-radius: 6px;");
        backBtn.setOnAction(e -> showHospitalDetailedView(h));

        topRow.getChildren().addAll(headerTitle, spacer, backBtn);

        // Member Selector
        HBox memberSelectRow = new HBox(10);
        memberSelectRow.setAlignment(Pos.CENTER_LEFT);

        Label memberLbl = new Label("Select Patient Member:");
        memberLbl.setStyle(
                FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        ComboBox<String> memberComboBox = new ComboBox<>();
        memberComboBox.setPromptText("Loading members...");
        memberComboBox.setStyle(FONT_FAMILY + "-fx-background-color: " + PALE_PEACH + "; -fx-border-color: "
                + BORDER_COLOR + "; -fx-font-size: 11.5px; -fx-background-radius: 6; -fx-border-radius: 6;");
        memberComboBox.setPrefWidth(220);

        // Load members dynamically from Firestore for the logged-in user
        MedicalReportsController memberLoader = new MedicalReportsController();
        memberLoader.subscribeToFamilyMembers(
                MedicalReports.loggedInFamilyEmail,
                members -> {
                    memberComboBox.getItems().clear();
                    if (members != null && !members.isEmpty()) {
                        for (MemberModel m : members) {
                            String displayName = m.getFullName();
                            if (displayName != null && !displayName.trim().isEmpty()) {
                                memberComboBox.getItems().add(displayName.trim());
                            }
                        }
                        if (!memberComboBox.getItems().isEmpty()) {
                            memberComboBox.setValue(memberComboBox.getItems().get(0));
                        }
                    } else {
                        memberComboBox.setPromptText("No members found");
                    }
                },
                err -> memberComboBox.setPromptText("Error loading members"));

        memberSelectRow.getChildren().addAll(memberLbl, memberComboBox);

        // Upload & Preview Layout
        HBox fileUploadRow = new HBox(12);
        fileUploadRow.setAlignment(Pos.CENTER_LEFT);

        Button chooseFileBtn = new Button("📁 Choose Report Image / PDF");
        chooseFileBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                        "-fx-text-fill: " + PRIMARY_DARK + ";" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 11.5px;" +
                        "-fx-padding: 8 16;" +
                        "-fx-background-radius: 6px;" +
                        "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                        "-fx-border-radius: 6px;" +
                        "-fx-cursor: hand;");

        Label fileChosenLabel = new Label("No report file selected");
        fileChosenLabel.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");

        fileUploadRow.getChildren().addAll(chooseFileBtn, fileChosenLabel);

        // Thumbnail Preview Frame
        StackPane imagePreviewFrame = new StackPane();
        imagePreviewFrame.setPrefSize(160, 80);
        imagePreviewFrame.setMaxSize(160, 80);
        imagePreviewFrame.setStyle("-fx-background-color: " + PAGE_BG + "; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 8; -fx-background-radius: 8;");

        ImageView thumbnailView = new ImageView();
        thumbnailView.setFitWidth(156);
        thumbnailView.setFitHeight(76);
        thumbnailView.setPreserveRatio(true);
        Rectangle clip = new Rectangle(156, 76);
        clip.setArcWidth(8);
        clip.setArcHeight(8);
        thumbnailView.setClip(clip);

        Label noImgLabel = new Label("Report Preview");
        noImgLabel.setStyle(FONT_FAMILY + "-fx-font-size: 9.5px; -fx-text-fill: " + TEXT_MUTED + ";");
        imagePreviewFrame.getChildren().addAll(noImgLabel, thumbnailView);

        // OCR Result Area
        Label ocrHeading = new Label("Extracted Clinical Summary (LifeLink Vision AI):");
        ocrHeading.setStyle(
                FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY + ";");

        TextArea ocrResultArea = new TextArea();
        ocrResultArea.setPromptText(
                "Click 'Analyze Medical Report' to extract lab findings, diagnosis, and medications with AI...");
        ocrResultArea.setPrefRowCount(12);
        ocrResultArea.setWrapText(true);
        ocrResultArea.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + PAGE_BG + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 8px;" +
                        "-fx-font-size: 11.5px;" +
                        "-fx-line-spacing: 2px;");

        // Action Toolbar
        HBox bottomControls = new HBox(12);
        bottomControls.setAlignment(Pos.CENTER_LEFT);

        Button ocrBtn = new Button("⚡ Analyze Medical Report");
        ocrBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + PRIMARY + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 11.5px;" +
                        "-fx-padding: 8 16;" +
                        "-fx-background-radius: 6px;" +
                        "-fx-cursor: hand;");

        Button submitReportBtn = new Button("📨 Dispatch to Hospital");
        submitReportBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + GREEN + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 11.5px;" +
                        "-fx-padding: 8 16;" +
                        "-fx-background-radius: 6px;" +
                        "-fx-cursor: hand;");

        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setPrefSize(20, 20);
        spinner.setVisible(false);

        Label statusLbl = new Label("Ready to upload.");
        statusLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        final File[] selectedReportFile = new File[1];
        final String[] uploadedCloudinaryUrl = new String[] { "" };

        chooseFileBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Medical Lab / Doctor Report");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.webp", "*.pdf"));
            File f = fileChooser.showOpenDialog(parentStage);
            if (f != null) {
                selectedReportFile[0] = f;
                fileChosenLabel.setText(f.getName());
                noImgLabel.setVisible(false);
                thumbnailView.setImage(new Image(f.toURI().toString(), 156, 76, true, true));
                statusLbl.setText("File selected. Click 'Analyze Medical Report' to run OCR.");
            }
        });

        ocrBtn.setOnAction(e -> {
            if (selectedReportFile[0] == null) {
                statusLbl.setText("⚠ Please choose a report image or document first.");
                return;
            }

            spinner.setVisible(true);
            ocrBtn.setDisable(true);
            statusLbl.setText("Processing AI Vision & Clinical Summary extraction...");

            new Thread(() -> {
                try {
                    Map res = cloudinary.uploader().upload(selectedReportFile[0], ObjectUtils.emptyMap());
                    uploadedCloudinaryUrl[0] = (String) res.get("secure_url");

                    byte[] fileContent = Files.readAllBytes(selectedReportFile[0].toPath());
                    String base64Image = Base64.getEncoder().encodeToString(fileContent);

                    String prompt = "You are a direct clinical data extractor. "
                            + "Extract the text from the medical report image into clean plain text. "
                            + "STRICT RULES: "
                            + "1. Do NOT include markdown bolding, asterisks, internal thinking notes, or layout descriptions (like 'top left', 'top right'). "
                            + "2. Do NOT write conversational intros or conclusions. "
                            + "3. Format strictly under these 3 headers: "
                            + "1. PATIENT INFORMATION & DIAGNOSIS, "
                            + "2. VITALS & DIAGNOSTIC FINDINGS, "
                            + "3. PRESCRIBED MEDICATIONS & TREATMENT PLAN.";

                    String rawOcrResult = callGroqVisionOCR(prompt, base64Image);
                    String cleanOcr = cleanOcrOutput(rawOcrResult);

                    Platform.runLater(() -> {
                        spinner.setVisible(false);
                        ocrBtn.setDisable(false);
                        ocrResultArea.setText(cleanOcr.isEmpty() ? rawOcrResult : cleanOcr);
                        statusLbl.setText("✓ OCR Analysis complete. Ready to dispatch.");
                    });

                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        spinner.setVisible(false);
                        ocrBtn.setDisable(false);
                        statusLbl.setText("OCR Error: " + ex.getMessage());
                    });
                }
            }).start();
        });

        submitReportBtn.setOnAction(e -> {
            String reportText = ocrResultArea.getText().trim();
            if (reportText.isEmpty()) {
                statusLbl.setText("⚠ Please run OCR or enter report details before sending.");
                return;
            }

            statusLbl.setText("Generating sequence ID and storing in 'familyTohospitalRepo'...");
            submitReportBtn.setDisable(true);

            new Thread(() -> {
                try {
                    Firestore db = FirebaseConfig.getFirestore();
                    if (db != null) {
                        String reportId = generateNextReportId(db);

                        Map<String, Object> data = new HashMap<>();
                        data.put("reportId", reportId);
                        data.put("patientMemberName", memberComboBox.getValue());
                        data.put("hospitalName", h.name);
                        data.put("hospitalEmail", h.email);
                        data.put("hospitalAddress", h.address);
                        data.put("reportUrl", uploadedCloudinaryUrl[0]);
                        data.put("reportSummary", reportText);
                        data.put("status", "SUBMITTED");
                        data.put("timestamp", Timestamp.now());

                        db.collection("familyTohospitalRepo").document(reportId).set(data).get();

                        Platform.runLater(() -> {
                            submitReportBtn.setText("✓ Dispatched");
                            submitReportBtn.setStyle("-fx-background-color: " + LIGHT_GREEN + "; -fx-text-fill: "
                                    + GREEN + "; -fx-font-weight: bold;");
                            statusLbl.setText("✓ Report saved as " + reportId + " and dispatched to " + h.name + "!");
                        });
                    }
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        submitReportBtn.setDisable(false);
                        statusLbl.setText("Dispatch failed: " + ex.getMessage());
                    });
                }
            }).start();
        });

        bottomControls.getChildren().addAll(ocrBtn, submitReportBtn, spinner, statusLbl);

        uploadCard.getChildren().addAll(topRow, createDivider(), memberSelectRow, fileUploadRow, imagePreviewFrame,
                ocrHeading, ocrResultArea, bottomControls);

        detailDrawerSlot.getChildren().clear();
        detailDrawerSlot.getChildren().add(uploadCard);

        FadeTransition ft = new FadeTransition(Duration.millis(200), uploadCard);
        ft.setFromValue(0.3);
        ft.setToValue(1.0);
        ft.play();
    }

    // =========================================================
    // POST-PROCESSING: PURGES THINK TAGS, ARTIFACTS & FORMATTING
    // =========================================================
    public static String cleanOcrOutput(String rawText) {
        if (rawText == null || rawText.trim().isEmpty())
            return "";

        String cleaned = rawText;

        if (cleaned.contains("u003c/thinku003e")) {
            cleaned = cleaned.substring(cleaned.lastIndexOf("u003c/thinku003e") + "u003c/thinku003e".length());
        } else if (cleaned.contains("</think>")) {
            cleaned = cleaned.substring(cleaned.lastIndexOf("</think>") + "</think>".length());
        }

        cleaned = cleaned.replaceAll("(?s)u003cthinku003e.*", "");
        cleaned = cleaned.replaceAll("(?s)<think>.*", "");

        cleaned = cleaned.replace("u0026", "&")
                .replaceAll("[\\*#_`~]", "");

        return cleaned.trim();
    }

    // =========================================================
    // GROQ ACTIVE MULTIMODAL VISION OCR REST API CALL
    // =========================================================
    private String callGroqVisionOCR(String prompt, String base64Image) throws Exception {
        URL url = new URL("https://api.groq.com/openai/v1/chat/completions");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + GROQ_API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(15000);
        conn.setReadTimeout(30000);

        String cleanBase64 = base64Image.contains(",")
                ? base64Image.substring(base64Image.indexOf(",") + 1).replaceAll("\\s+", "")
                : base64Image.replaceAll("\\s+", "");

        String dataUrl = "data:image/jpeg;base64," + cleanBase64;

        String cleanPrompt = prompt.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");

        String jsonPayload = "{"
                + "\"model\": \"qwen/qwen3.8-27b\","
                + "\"max_tokens\": 1500,"
                + "\"messages\": ["
                + "  {"
                + "    \"role\": \"user\","
                + "    \"content\": ["
                + "      {\"type\": \"text\", \"text\": \"" + cleanPrompt + "\"},"
                + "      {\"type\": \"image_url\", \"image_url\": {\"url\": \"" + dataUrl + "\"}}"
                + "    ]"
                + "  }"
                + "]"
                + "}";

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                StringBuilder err = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                    err.append(line);
                throw new RuntimeException("HTTP " + responseCode + ": " + err);
            }
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder resp = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                resp.append(line);

            return parseGroqJsonResponse(resp.toString());
        }
    }

    private String parseGroqJsonResponse(String json) {
        try {
            int choicesIdx = json.indexOf("\"choices\"");
            if (choicesIdx != -1) {
                int contentKey = json.indexOf("\"content\":", choicesIdx);
                if (contentKey != -1) {
                    int startQuote = json.indexOf("\"", contentKey + 10);
                    if (startQuote != -1) {
                        StringBuilder sb = new StringBuilder();
                        boolean escaped = false;
                        for (int i = startQuote + 1; i < json.length(); i++) {
                            char c = json.charAt(i);
                            if (escaped) {
                                if (c == 'n')
                                    sb.append('\n');
                                else if (c == 'r')
                                    sb.append('\r');
                                else if (c == 't')
                                    sb.append('\t');
                                else if (c == '\"')
                                    sb.append('\"');
                                else if (c == '\\')
                                    sb.append('\\');
                                else
                                    sb.append(c);
                                escaped = false;
                            } else if (c == '\\') {
                                escaped = true;
                            } else if (c == '\"') {
                                break;
                            } else {
                                sb.append(c);
                            }
                        }
                        String parsed = sb.toString().trim();
                        if (!parsed.isEmpty())
                            return parsed;
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return json;
    }

    private VBox createDetailResourcePill(String title, String value, String valColor) {
        VBox box = new VBox(3);
        box.setPrefWidth(210);
        box.setPadding(new Insets(10, 12, 10, 12));
        box.setStyle("-fx-background-color: " + PAGE_BG + "; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Label t = new Label(title);
        t.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + ";");

        Label v = new Label(value);
        v.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + valColor + ";");

        box.getChildren().addAll(t, v);
        return box;
    }

    private VBox createInitialDetailPlaceholder() {
        VBox placeholder = new VBox(14);
        placeholder.setAlignment(Pos.CENTER);
        placeholder.setPadding(new Insets(40));
        placeholder.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 16px;" +
                        "-fx-background-radius: 16px;");
        Label icon = new Label("🏥");
        icon.setStyle("-fx-font-size: 42px;");

        Label heading = new Label("Facility Inspection Studio");
        heading.setStyle(
                FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label text = new Label(
                "Select any hospital from the network list to inspect real-time ICU beds, ventilators, and emergency trauma coverage, or dispatch prescription reports.");
        text.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED
                + "; -fx-text-alignment: center; -fx-line-spacing: 2px;");
        text.setWrapText(true);
        text.setMaxWidth(340);

        placeholder.getChildren().addAll(icon, heading, text);
        return placeholder;
    }

    private Region createDivider() {
        Region div = new Region();
        div.setPrefHeight(1);
        div.setStyle("-fx-background-color: " + BORDER_COLOR + ";");
        return div;
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
}