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
import javafx.stage.Stage;

public class FamilyFindCare {

    private String[][] hospitals = {
            {"Mercy General Hospital", "Level 1 Trauma Center • Teaching Hospital",
                    "Low Wait Time", "tag-green", "1.2 miles away", "5 min ETA", "4.8",
                    "Emergency ER", "Pediatrics"},
            {"St. Jude Medical Center", "Specialized Care • Cardiovascular",
                    "Moderate Wait", "tag-orange", "3.4 miles away", "12 min ETA", "4.6",
                    "Cardiology", "Neurology"}
    };
    public BorderPane setBorderPane(Stage stage) {

        BorderPane bp = new BorderPane();
        bp.getStyleClass().add("root-pane");

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.FIND_HOSPITALS);
        bp.setLeft(sidebar);

        VBox mainContent = buildMainContent();
        bp.setCenter(mainContent);

        return bp;
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

        MedicalReports.playPageAnimation(leftColumn);

        HBox bodyRow = new HBox(20, leftColumn);
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
}
