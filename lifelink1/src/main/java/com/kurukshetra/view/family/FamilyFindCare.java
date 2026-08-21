// package com.kurukshetra.view.family;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.VBox;
// import javafx.stage.Stage;

// public class FamilyFindCare {

//     private String[][] hospitals = {
//             {"Mercy General Hospital", "Level 1 Trauma Center • Teaching Hospital",
//                     "Low Wait Time", "tag-green", "1.2 miles away", "5 min ETA", "4.8",
//                     "Emergency ER", "Pediatrics"},
//             {"St. Jude Medical Center", "Specialized Care • Cardiovascular",
//                     "Moderate Wait", "tag-orange", "3.4 miles away", "12 min ETA", "4.6",
//                     "Cardiology", "Neurology"}
//     };
//     public BorderPane setBorderPane(Stage stage) {

//         BorderPane bp = new BorderPane();
//         bp.getStyleClass().add("root-pane");

//         VBox sidebar = Sidebar.build(stage, Sidebar.Page.FIND_HOSPITALS);
//         bp.setLeft(sidebar);

//         VBox mainContent = buildMainContent();
//         bp.setCenter(mainContent);

//         return bp;
//     }

//     // ---------------- MAIN CONTENT ----------------
//     private VBox buildMainContent() {

//         HBox header = buildHeader();
//         VBox searchBox = buildSearchBox();
//         HBox filterRow = buildFilterRow();

//         Label resultsLabel = new Label("Showing " + hospitals.length + " facilities nearby");
//         resultsLabel.getStyleClass().add("results-label");

//         VBox hospitalList = new VBox(14);
//         for (String[] h : hospitals) {
//             hospitalList.getChildren().add(buildHospitalCard(h));
//         }
//         ScrollPane listScroll = new ScrollPane(hospitalList);
//         listScroll.setFitToWidth(true);
//         listScroll.getStyleClass().add("main-scroll");

//         VBox leftColumn = new VBox(14, resultsLabel, listScroll);
//         leftColumn.setPrefWidth(420);
//         VBox.setVgrow(listScroll, Priority.ALWAYS);

//         MedicalReports.playPageAnimation(leftColumn);

//         HBox bodyRow = new HBox(20, leftColumn);
//         VBox.setVgrow(bodyRow, Priority.ALWAYS);

//         VBox mainContent = new VBox(18, header, searchBox, filterRow, bodyRow);
//         mainContent.getStyleClass().add("main-content");
//         mainContent.setPadding(new Insets(24));

//         return mainContent;
//     }

//     private HBox buildHeader() {
//         Label title = new Label("Find Care");
//         title.getStyleClass().add("page-title");

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         Label bell = new Label("🔔");
//         Label help = new Label("❓");
//         bell.getStyleClass().add("icon-label");
//         help.getStyleClass().add("icon-label");

//         HBox header = new HBox(20, title, spacer, bell, help);
//         header.setAlignment(Pos.CENTER_LEFT);
//         return header;
//     }

//     private VBox buildSearchBox() {
//         TextField searchField = new TextField();
//         searchField.setPromptText("🔍  Search hospitals, specialties, or services...");
//         searchField.getStyleClass().add("search-field");
//         searchField.setMaxWidth(Double.MAX_VALUE);

//         Label locationLabel = new Label("📍 Using your current location: Seattle, WA");
//         locationLabel.getStyleClass().add("location-label");

//         VBox box = new VBox(10, searchField, locationLabel);
//         box.getStyleClass().add("card");
//         box.setPadding(new Insets(16));
//         return box;
//     }

//     private HBox buildFilterRow() {
//         Button all = new Button("All");
//         Button emergency = new Button("✳ Emergency");
//         Button openNow = new Button("🕘 Open Now");
//         Button icu = new Button("💗 ICU");
//         Button trauma = new Button("🩸 Trauma");

//         all.getStyleClass().addAll("filter-btn", "filter-btn-active");
//         emergency.getStyleClass().add("filter-btn");
//         openNow.getStyleClass().add("filter-btn");
//         icu.getStyleClass().add("filter-btn");
//         trauma.getStyleClass().add("filter-btn");

//         HBox filterRow = new HBox(10, all, emergency, openNow, icu, trauma);
//         return filterRow;
//     }

//     private VBox buildHospitalCard(String[] h) {
//         String name = h[0];
//         String subtitle = h[1];
//         String waitTag = h[2];
//         String waitTagStyle = h[3];
//         String distance = h[4];
//         String eta = h[5];
//         String rating = h[6];
//         String specialty1 = h[7];
//         String specialty2 = h[8];

//         Label nameLbl = new Label(name);
//         nameLbl.getStyleClass().add("card-title");

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         Label waitLbl = new Label(waitTag);
//         waitLbl.getStyleClass().addAll("status-pill", waitTagStyle);

//         HBox topRow = new HBox(nameLbl, spacer, waitLbl);
//         topRow.setAlignment(Pos.CENTER_LEFT);

//         Label subtitleLbl = new Label(subtitle);
//         subtitleLbl.getStyleClass().add("card-desc");

//         Label distEtaLbl = new Label("📍 " + distance + "     ⏱ " + eta);
//         distEtaLbl.getStyleClass().add("card-desc");

//         Label ratingLbl = new Label("⭐ " + rating);
//         ratingLbl.getStyleClass().add("rating-label");

//         HBox infoRow = new HBox(20, distEtaLbl, spacerRegion(), ratingLbl);
//         infoRow.setAlignment(Pos.CENTER_LEFT);

//         Label tag1 = new Label(specialty1);
//         Label tag2 = new Label(specialty2);
//         tag1.getStyleClass().add("specialty-tag");
//         tag2.getStyleClass().add("specialty-tag");
//         HBox tagRow = new HBox(8, tag1, tag2);

//         VBox card = new VBox(8, topRow, subtitleLbl, infoRow, tagRow);
//         card.getStyleClass().addAll("card", "hospital-card");
//         card.setPadding(new Insets(16));
//         return card;
//     }

//     private Region spacerRegion() {
//         Region r = new Region();
//         HBox.setHgrow(r, Priority.ALWAYS);
//         return r;
//     }
// }


package com.kurukshetra.view.family;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

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
    private static final String HOVER_BG = "#FFF5EF";

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
        bp.setStyle("-fx-background-color: " + PAGE_BG + ";");

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.FIND_HOSPITALS);
        bp.setLeft(sidebar);

        VBox mainContent = buildMainContent();
        bp.setCenter(mainContent);
        return bp;
    }

    private VBox buildMainContent() {
        HBox header = buildHeader();
        VBox searchBox = buildSearchBox();
        HBox filterRow = buildFilterRow();

        Label resultsLabel = new Label("Showing " + hospitals.length + " facilities nearby");
        resultsLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
        resultsLabel.setStyle("-fx-text-fill: " + TEXT_MUTED + ";");

        VBox hospitalList = new VBox(14);
        for (String[] h : hospitals) {
            hospitalList.getChildren().add(buildHospitalCard(h));
        }

        ScrollPane listScroll = new ScrollPane(hospitalList);
        listScroll.setFitToWidth(true);
        listScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        listScroll.setStyle(
                "-fx-background: " + PAGE_BG + ";" +
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );

        VBox leftColumn = new VBox(14, resultsLabel, listScroll);
        leftColumn.setPrefWidth(460);
        VBox.setVgrow(listScroll, Priority.ALWAYS);

        MedicalReports.playPageAnimation(leftColumn);

        HBox bodyRow = new HBox(20, leftColumn);
        VBox.setVgrow(bodyRow, Priority.ALWAYS);

        VBox mainContent = new VBox(18, header, searchBox, filterRow, bodyRow);
        mainContent.setStyle("-fx-background-color: " + PAGE_BG + ";");
        mainContent.setPadding(new Insets(24));
        return mainContent;
    }

    private HBox buildHeader() {
        Label title = new Label("Find Care");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(20, title);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private VBox buildSearchBox() {
        TextField searchField = new TextField();
        searchField.setPromptText("🔍  Search hospitals, specialties, or services...");
        searchField.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-background-radius: 8px;" +
                "-fx-padding: 10px 14px;" +
                "-fx-font-size: 13px;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );
        searchField.focusedProperty().addListener((obs, oldV, newV) -> {
            if (newV) {
                searchField.setStyle(
                        "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + PRIMARY + ";" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 10px 14px;" +
                        "-fx-font-size: 13px;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";"
                );
            } else {
                searchField.setStyle(
                        "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 10px 14px;" +
                        "-fx-font-size: 13px;" +
                        "-fx-text-fill: " + TEXT_PRIMARY + ";"
                );
            }
        });
        searchField.setMaxWidth(Double.MAX_VALUE);

        Label locationLabel = new Label("📍 Using your current location: Seattle, WA");
        locationLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
        locationLabel.setStyle("-fx-text-fill: " + PRIMARY + ";");

        VBox box = new VBox(10, searchField, locationLabel);
        box.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;" +
                "-fx-padding: 16px;"
        );
        box.setEffect(new DropShadow(10, 0, 3, Color.rgb(120, 47, 22, 0.05)));
        return box;
    }

    private HBox buildFilterRow() {
        Button all = new Button("All");
        Button emergency = new Button("✳ Emergency");
        Button openNow = new Button("🕘 Open Now");
        Button icu = new Button("💗 ICU");
        Button trauma = new Button("🩸 Trauma");

        styleFilterButton(all, true);
        styleFilterButton(emergency, false);
        styleFilterButton(openNow, false);
        styleFilterButton(icu, false);
        styleFilterButton(trauma, false);

        return new HBox(10, all, emergency, openNow, icu, trauma);
    }

    private void styleFilterButton(Button btn, boolean active) {
        if (active) {
            btn.setStyle(
                    "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                    "-fx-text-fill: " + PRIMARY_DARK + ";" +
                    "-fx-border-color: " + PRIMARY + ";" +
                    "-fx-border-radius: 20px;" +
                    "-fx-background-radius: 20px;" +
                    "-fx-font-size: 12px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-padding: 8px 16px;" +
                    "-fx-cursor: hand;"
            );
        } else {
            btn.setStyle(
                    "-fx-background-color: " + SURFACE + ";" +
                    "-fx-text-fill: " + TEXT_SECONDARY + ";" +
                    "-fx-border-color: " + BORDER_COLOR + ";" +
                    "-fx-border-radius: 20px;" +
                    "-fx-background-radius: 20px;" +
                    "-fx-font-size: 12px;" +
                    "-fx-padding: 8px 16px;" +
                    "-fx-cursor: hand;"
            );
            btn.setOnMouseEntered(e -> btn.setStyle(
                    "-fx-background-color: " + HOVER_BG + ";" +
                    "-fx-text-fill: " + PRIMARY_DARK + ";" +
                    "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                    "-fx-border-radius: 20px;" +
                    "-fx-background-radius: 20px;" +
                    "-fx-font-size: 12px;" +
                    "-fx-padding: 8px 16px;" +
                    "-fx-cursor: hand;"
            ));
            btn.setOnMouseExited(e -> btn.setStyle(
                    "-fx-background-color: " + SURFACE + ";" +
                    "-fx-text-fill: " + TEXT_SECONDARY + ";" +
                    "-fx-border-color: " + BORDER_COLOR + ";" +
                    "-fx-border-radius: 20px;" +
                    "-fx-background-radius: 20px;" +
                    "-fx-font-size: 12px;" +
                    "-fx-padding: 8px 16px;" +
                    "-fx-cursor: hand;"
            ));
        }
    }

    private VBox buildHospitalCard(String[] h) {
        String name = h[0];
        String subtitle = h[1];
        String waitTag = h[2];
        String distance = h[4];
        String eta = h[5];
        String rating = h[6];
        String specialty1 = h[7];
        String specialty2 = h[8];

        Label nameLbl = new Label(name);
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 15));
        nameLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label waitLbl = new Label(waitTag);
        waitLbl.setFont(Font.font("System", FontWeight.BOLD, 11));
        if (waitTag.contains("Low")) {
            waitLbl.setStyle("-fx-background-color: #E8F5EC; -fx-text-fill: #23804F; -fx-padding: 4px 10px; -fx-background-radius: 12px;");
        } else {
            waitLbl.setStyle("-fx-background-color: #FFF3D8; -fx-text-fill: #B77900; -fx-padding: 4px 10px; -fx-background-radius: 12px;");
        }

        HBox topRow = new HBox(nameLbl, spacer, waitLbl);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label subtitleLbl = new Label(subtitle);
        subtitleLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED + ";");

        Label distEtaLbl = new Label("📍 " + distance + "     ⏱ " + eta);
        distEtaLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        Label ratingLbl = new Label("⭐ " + rating);
        ratingLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #B77900;");

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        HBox infoRow = new HBox(12, distEtaLbl, spacer2, ratingLbl);
        infoRow.setAlignment(Pos.CENTER_LEFT);

        Label tag1 = createSpecialtyTag(specialty1);
        Label tag2 = createSpecialtyTag(specialty2);
        HBox tagRow = new HBox(8, tag1, tag2);

        VBox card = new VBox(8, topRow, subtitleLbl, infoRow, tagRow);
        card.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;" +
                "-fx-padding: 16px;" +
                "-fx-cursor: hand;"
        );
        card.setEffect(new DropShadow(10, 0, 3, Color.rgb(120, 47, 22, 0.05)));

        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: " + PALE_PEACH + ";" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;" +
                "-fx-padding: 16px;" +
                "-fx-cursor: hand;"
        ));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;" +
                "-fx-padding: 16px;" +
                "-fx-cursor: hand;"
        ));

        return card;
    }

    private Label createSpecialtyTag(String text) {
        Label lbl = new Label(text);
        lbl.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 11px;" +
                "-fx-padding: 4px 10px;" +
                "-fx-background-radius: 8px;"
        );
        return lbl;
    }

    private void styleUtilityIconButton(Label label) {
        label.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 18px;" +
                "-fx-background-radius: 18px;" +
                "-fx-min-width: 36px;" +
                "-fx-min-height: 36px;" +
                "-fx-alignment: center;" +
                "-fx-font-size: 14px;" +
                "-fx-cursor: hand;"
        );
    }
}