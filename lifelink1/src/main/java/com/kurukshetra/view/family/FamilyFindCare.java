package com.kurukshetra.view.family;





import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
// import javafx.scene.web.WebEngine;
// import javafx.scene.web.WebView;
import javafx.stage.Stage;

// This class builds the "Find Care" screen.
// It does NOT extend Application. It just returns a BorderPane
// that MainApp / HomePage put on the Stage.
public class FamilyFindCare {

    // ---------- DUMMY / STATIC DATA (replace with backend later) ----------
    // name, subtitle, waitTag, waitTagStyle, distance, eta, rating, specialty1, specialty2
    private String[][] hospitals = {
            {"Mercy General Hospital", "Level 1 Trauma Center • Teaching Hospital",
                    "Low Wait Time", "tag-green", "1.2 miles away", "5 min ETA", "4.8",
                    "Emergency ER", "Pediatrics"},
            {"St. Jude Medical Center", "Specialized Care • Cardiovascular",
                    "Moderate Wait", "tag-orange", "3.4 miles away", "12 min ETA", "4.6",
                    "Cardiology", "Neurology"}
    };
    // ------------------------------------------------------------------

    public BorderPane setBorderPane(Stage stage) {

        BorderPane bp = new BorderPane();
        bp.getStyleClass().add("root-pane");

        VBox sidebar = buildSidebar(stage);
        bp.setLeft(sidebar);

        VBox mainContent = buildMainContent();
        bp.setCenter(mainContent);

        return bp;
    }

    // ---------------- SIDEBAR ----------------
    // Same style as HomePage sidebar, "Find Hospitals" is active here.
    private VBox buildSidebar(Stage stage) {

        Label logo = new Label("💙 LifeLink");
        logo.getStyleClass().add("logo-label");

        Button dashboardBtn = new Button("🏠  Dashboard");
        Button findHospitalsBtn = new Button("➕  Find Hospitals");
        Button emergencyBtn = new Button("✳  Emergency Services");
        Button firstAidBtn = new Button("🩹  First-Aid Assistant");
        Button appointmentsBtn = new Button("📅  Appointments");
        Button medicalHistoryBtn = new Button("🕘  Medical History");
        Button savedHospitalsBtn = new Button("🔖  Saved Hospitals");

        dashboardBtn.getStyleClass().add("nav-button");
        findHospitalsBtn.getStyleClass().addAll("nav-button", "nav-button-active");
        emergencyBtn.getStyleClass().add("nav-button");
        firstAidBtn.getStyleClass().add("nav-button");
        appointmentsBtn.getStyleClass().add("nav-button");
        medicalHistoryBtn.getStyleClass().add("nav-button");
        savedHospitalsBtn.getStyleClass().add("nav-button");

        dashboardBtn.setMaxWidth(Double.MAX_VALUE);
        findHospitalsBtn.setMaxWidth(Double.MAX_VALUE);
        emergencyBtn.setMaxWidth(Double.MAX_VALUE);
        firstAidBtn.setMaxWidth(Double.MAX_VALUE);
        appointmentsBtn.setMaxWidth(Double.MAX_VALUE);
        medicalHistoryBtn.setMaxWidth(Double.MAX_VALUE);
        savedHospitalsBtn.setMaxWidth(Double.MAX_VALUE);

        // Navigate back to Dashboard screen
        dashboardBtn.setOnAction(event -> {
            FamilyHomePage homePage = new FamilyHomePage();
            BorderPane homePane = homePage.setBorderPane(stage);
            stage.getScene().setRoot(homePane);
        });

        VBox navBox = new VBox(6,
                dashboardBtn, findHospitalsBtn, emergencyBtn,
                firstAidBtn, appointmentsBtn, medicalHistoryBtn, savedHospitalsBtn);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label avatar = new Label("👩");
        avatar.getStyleClass().add("avatar-label");
        Label name = new Label("Sarah Miller");
        Label role = new Label("Family Care Lead");
        name.getStyleClass().add("member-name");
        role.getStyleClass().add("member-relation");
        VBox profileText = new VBox(name, role);
        HBox profileBox = new HBox(10, avatar, profileText);
        profileBox.setAlignment(Pos.CENTER_LEFT);

        Button sosBtn = new Button("🆘  Emergency Help");
        sosBtn.getStyleClass().add("emergency-btn");
        sosBtn.setMaxWidth(Double.MAX_VALUE);
        sosBtn.setOnAction(event -> System.out.println("SOS Emergency Help pressed!"));

        VBox sidebar = new VBox(20, logo, navBox, spacer, profileBox, sosBtn);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(230);
        sidebar.setPadding(new Insets(20, 14, 20, 14));

        return sidebar;
    }

    // ---------------- MAIN CONTENT ----------------
    private VBox buildMainContent() {

        HBox header = buildHeader();
        VBox searchBox = buildSearchBox();
        HBox filterRow = buildFilterRow();

        Label resultsLabel = new Label("Showing " + hospitals.length + " facilities nearby");
        resultsLabel.getStyleClass().add("results-label");

        VBox hospitalList = new VBox(14);
        for (String[] h : hospitals) {
            hospitalList.getChildren().add(buildHospitalCard(h));
        }
        ScrollPane listScroll = new ScrollPane(hospitalList);
        listScroll.setFitToWidth(true);
        listScroll.getStyleClass().add("main-scroll");

        VBox leftColumn = new VBox(14, resultsLabel, listScroll);
        leftColumn.setPrefWidth(420);
        VBox.setVgrow(listScroll, Priority.ALWAYS);

        // WebView mapView = buildMapView();
        // VBox mapColumn = new VBox(mapView);
        // mapColumn.getStyleClass().add("map-container");
        // HBox.setHgrow(mapColumn, Priority.ALWAYS);
        // VBox.setVgrow(mapView, Priority.ALWAYS);
        // mapView.prefWidthProperty().bind(mapColumn.widthProperty());
        // mapView.prefHeightProperty().bind(mapColumn.heightProperty());

        HBox bodyRow = new HBox(20, leftColumn /*mapColumn*/);
        VBox.setVgrow(bodyRow, Priority.ALWAYS);

        VBox mainContent = new VBox(18, header, searchBox, filterRow, bodyRow);
        mainContent.getStyleClass().add("main-content");
        mainContent.setPadding(new Insets(24));

        return mainContent;
    }

    private HBox buildHeader() {
        Label title = new Label("Find Care");
        title.getStyleClass().add("page-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label bell = new Label("🔔");
        Label help = new Label("❓");
        bell.getStyleClass().add("icon-label");
        help.getStyleClass().add("icon-label");

        HBox header = new HBox(20, title, spacer, bell, help);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private VBox buildSearchBox() {
        TextField searchField = new TextField();
        searchField.setPromptText("🔍  Search hospitals, specialties, or services...");
        searchField.getStyleClass().add("search-field");
        searchField.setMaxWidth(Double.MAX_VALUE);

        Label locationLabel = new Label("📍 Using your current location: Seattle, WA");
        locationLabel.getStyleClass().add("location-label");

        VBox box = new VBox(10, searchField, locationLabel);
        box.getStyleClass().add("card");
        box.setPadding(new Insets(16));
        return box;
    }

    private HBox buildFilterRow() {
        Button all = new Button("All");
        Button emergency = new Button("✳ Emergency");
        Button openNow = new Button("🕘 Open Now");
        Button icu = new Button("💗 ICU");
        Button trauma = new Button("🩸 Trauma");

        all.getStyleClass().addAll("filter-btn", "filter-btn-active");
        emergency.getStyleClass().add("filter-btn");
        openNow.getStyleClass().add("filter-btn");
        icu.getStyleClass().add("filter-btn");
        trauma.getStyleClass().add("filter-btn");

        HBox filterRow = new HBox(10, all, emergency, openNow, icu, trauma);
        return filterRow;
    }

    private VBox buildHospitalCard(String[] h) {
        String name = h[0];
        String subtitle = h[1];
        String waitTag = h[2];
        String waitTagStyle = h[3];
        String distance = h[4];
        String eta = h[5];
        String rating = h[6];
        String specialty1 = h[7];
        String specialty2 = h[8];

        Label nameLbl = new Label(name);
        nameLbl.getStyleClass().add("card-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label waitLbl = new Label(waitTag);
        waitLbl.getStyleClass().addAll("status-pill", waitTagStyle);

        HBox topRow = new HBox(nameLbl, spacer, waitLbl);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label subtitleLbl = new Label(subtitle);
        subtitleLbl.getStyleClass().add("card-desc");

        Label distEtaLbl = new Label("📍 " + distance + "     ⏱ " + eta);
        distEtaLbl.getStyleClass().add("card-desc");

        Label ratingLbl = new Label("⭐ " + rating);
        ratingLbl.getStyleClass().add("rating-label");

        HBox infoRow = new HBox(20, distEtaLbl, spacerRegion(), ratingLbl);
        infoRow.setAlignment(Pos.CENTER_LEFT);

        Label tag1 = new Label(specialty1);
        Label tag2 = new Label(specialty2);
        tag1.getStyleClass().add("specialty-tag");
        tag2.getStyleClass().add("specialty-tag");
        HBox tagRow = new HBox(8, tag1, tag2);

        VBox card = new VBox(8, topRow, subtitleLbl, infoRow, tagRow);
        card.getStyleClass().addAll("card", "hospital-card");
        card.setPadding(new Insets(16));
        return card;
    }

    private Region spacerRegion() {
        Region r = new Region();
        HBox.setHgrow(r, Priority.ALWAYS);
        return r;
    }

    // ---------------- MAP (WebView + Leaflet / OpenStreetMap) ----------------
    // No API key needed. Uses free OpenStreetMap tiles loaded through Leaflet.js.


    // private WebView buildMapView() {
    //     WebView webView = new WebView();
    //     WebEngine engine = webView.getEngine();
    //     engine.loadContent(buildMapHtml());
    //     return webView;
    // }

    // private String buildMapHtml() {
    //     // Simple Leaflet map centered on Seattle with a few dummy hospital markers.
    //     StringBuilder html = new StringBuilder();
    //     html.append("<html><head>");
    //     html.append("<link rel='stylesheet' href='https://unpkg.com/leaflet@1.9.4/dist/leaflet.css' />");
    //     html.append("<script src='https://unpkg.com/leaflet@1.9.4/dist/leaflet.js'></script>");
    //     html.append("<style>html,body,#map{height:100%;margin:0;padding:0;}</style>");
    //     html.append("</head><body>");
    //     html.append("<div id='map'></div>");
    //     html.append("<script>");
    //     html.append("var map = L.map('map').setView([47.6062, -122.3321], 13);");
    //     html.append("L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);");
    //     html.append("L.marker([47.6205, -122.3493]).addTo(map).bindPopup('Mercy General Hospital<br>1.2 miles - 5 min drive').openPopup();");
    //     html.append("L.marker([47.5980, -122.3200]).addTo(map).bindPopup('St. Jude Medical Center<br>3.4 miles - 12 min drive');");
    //     html.append("</script>");
    //     html.append("</body></html>");
    //     return html.toString();
    // }
}