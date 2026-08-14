// package com.kurukshetra.view.family;

// import javax.print.attribute.TextSyntax;

// import javafx.geometry.Pos;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;

// public class Hospitals {
    
//     public VBox getHospitalVBox(){

//         Text title = new Text("Hospital");
//         title.setStyle("-fx-font-size: 18px; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-background-color: #e1f0f9; -fx-padding:10px 10px");

//         VBox vmain = new VBox(title);
//         vmain.setAlignment(Pos.CENTER);
//         return vmain;
//     }
// }
package com.kurukshetra.view.family;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Hospitals {

    // Colors
    private static final String BODY_BACKGROUND = "#F7F9FE";
    private static final String WHITE = "#FFFFFF";
    private static final String BLUE = "#0B4FCC";
    private static final String DARK_BLUE = "#0645C2";
    private static final String LIGHT_BLUE = "#EEF4FF";
    private static final String BORDER = "#D8E0EB";
    private static final String DIVIDER = "#E4E7EC";
    private static final String TEXT = "#101828";
    private static final String SECONDARY_TEXT = "#667085";
    private static final String GREEN = "#087A35";
    private static final String GREEN_BACKGROUND = "#E9F7EF";
    private static final String RED = "#C81E1E";
    private static final String RED_BACKGROUND = "#FFE0DD";

    // Return hospital page
    public VBox getHospitalVBox() {
        VBox main = new VBox(24);

        main.setPadding(
            new Insets(38, 40, 45, 40)
        );

        main.setStyle(
            "-fx-background-color: " + BODY_BACKGROUND + ";"
        );

        HBox header = createHeader();

        VBox filterBox = createFilterBox();

        HBox hospitals = new HBox(18);
        hospitals.setAlignment(Pos.TOP_LEFT);

        VBox card1 = createHospitalCard(
            "Sairatna Hospital",
            "4.3",
            "850 reviews",
            "1.2 km",
            "8 mins",
            "12",
            "08",
            "Available",
            GREEN_BACKGROUND,
            GREEN,
            false
        );

        VBox card2 = createHospitalCard(
            "City General Hospital",
            "4.5",
            "980 reviews",
            "0.4 km",
            "3 mins",
            "03",
            "15",
            "Closest Emergency",
            RED_BACKGROUND,
            RED,
            true
        );

        VBox card3 = createHospitalCard(
            "Unity Health Institute",
            "4.2",
            "2.5k reviews",
            "3.5 km",
            "14 mins",
            "24",
            "32",
            "Specialist Wing",
            GREEN_BACKGROUND,
            GREEN,
            false
        );

        VBox mapBox = createMapBox();

        hospitals.getChildren().addAll(
            card1,
            card2,
            card3,
            mapBox
        );

        main.getChildren().addAll(
            header,
            filterBox,
            hospitals
        );

        return main;
    }

    // Create hospital header
    private HBox createHeader() {
        Label title = new Label("Find Nearby Hospitals");

        title.setStyle(
            "-fx-font-size: 32px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT + ";"
        );

        Label subtitle = new Label(
            "Real-time availability of critical care across your current radius."
        );

        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: " + SECONDARY_TEXT + ";"
        );

        VBox titleBox = new VBox(7);
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button recenter = new Button("⌁  Recenter Map");

        recenter.setStyle(
            "-fx-background-color: " + BLUE + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 9px;" +
            "-fx-padding: 11px 18px;" +
            "-fx-cursor: hand;"
        );

        recenter.setOnMouseEntered(event ->
            recenter.setStyle(
                "-fx-background-color: " + DARK_BLUE + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 9px;" +
                "-fx-padding: 11px 18px;" +
                "-fx-cursor: hand;"
            )
        );

        recenter.setOnMouseExited(event ->
            recenter.setStyle(
                "-fx-background-color: " + BLUE + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 9px;" +
                "-fx-padding: 11px 18px;" +
                "-fx-cursor: hand;"
            )
        );

        HBox header = new HBox(
            titleBox,
            spacer,
            recenter
        );

        header.setAlignment(Pos.CENTER_LEFT);

        return header;
    }

    // Create filters
    private VBox createFilterBox() {
        Label filterText = new Label("Filter by:");

        filterText.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #344054;"
        );

        Label distance = createFilter("Distance (Any)");
        Label icu = createFilter("▣  ICU Availability");
        Label emergency = createFilter("✱  Emergency Beds");
        Label doctors = createFilter("♙  Doctors on Duty");
        Label specialists = createFilter("♧  Specialists");
        Label blood = createFilter("♢  Blood Availability");

        Label clear = new Label("Clear All");

        clear.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: " + DARK_BLUE + ";" +
            "-fx-padding: 10px 12px;" +
            "-fx-cursor: hand;"
        );

        Region separator = new Region();
        separator.setPrefWidth(1);
        separator.setPrefHeight(30);
        separator.setStyle("-fx-background-color: #CBD5E1;");

        HBox row1 = new HBox(
            10,
            filterText,
            distance,
            icu,
            emergency,
            doctors
        );

        row1.setAlignment(Pos.CENTER_LEFT);

        HBox row2 = new HBox(
            10,
            specialists,
            blood,
            separator,
            clear
        );

        row2.setAlignment(Pos.CENTER_LEFT);

        VBox box = new VBox(10, row1, row2);

        box.setPadding(new Insets(12));

        box.setStyle(
            "-fx-background-color: " + WHITE + ";" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 14px;" +
            "-fx-background-radius: 14px;" +
            "-fx-border-width: 1px;"
        );

        return box;
    }

    // Create filter chip
    private Label createFilter(String text) {
        Label filter = new Label(text);

        filter.setStyle(
            "-fx-background-color: " + LIGHT_BLUE + ";" +
            "-fx-text-fill: #344054;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 10px 13px;" +
            "-fx-background-radius: 8px;" +
            "-fx-cursor: hand;"
        );

        filter.setOnMouseEntered(event ->
            filter.setStyle(
                "-fx-background-color: #DCE8FF;" +
                "-fx-text-fill: " + DARK_BLUE + ";" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10px 13px;" +
                "-fx-background-radius: 8px;" +
                "-fx-cursor: hand;"
            )
        );

        filter.setOnMouseExited(event ->
            filter.setStyle(
                "-fx-background-color: " + LIGHT_BLUE + ";" +
                "-fx-text-fill: #344054;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10px 13px;" +
                "-fx-background-radius: 8px;" +
                "-fx-cursor: hand;"
            )
        );

        return filter;
    }

    // Create hospital card
    private VBox createHospitalCard(
        String hospitalName,
        String rating,
        String reviews,
        String distance,
        String travelTime,
        String icuBeds,
        String availableDoctors,
        String badgeText,
        String badgeBackground,
        String badgeColor,
        boolean emergencyCard
    ) {
        VBox card = new VBox(14);

        card.setPadding(new Insets(20));
        card.setPrefWidth(255);
        card.setMinWidth(255);
        card.setMaxWidth(280);
        card.setMinHeight(390);

        String borderColor = emergencyCard ? "#F1B9B6" : BORDER;

        card.setStyle(
            "-fx-background-color: " + WHITE + ";" +
            "-fx-border-color: " + borderColor + ";" +
            "-fx-border-radius: 14px;" +
            "-fx-background-radius: 14px;" +
            "-fx-border-width: 1px;"
        );

        Label hospital = new Label(hospitalName);

        hospital.setWrapText(true);

        hospital.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT + ";"
        );

        Label status = new Label(badgeText);

        if (emergencyCard) {
            status.setStyle(
                "-fx-background-color: " + RED + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 6px 9px;" +
                "-fx-background-radius: 12px;"
            );
        } else {
            status.setStyle(
                "-fx-background-color: " + badgeBackground + ";" +
                "-fx-text-fill: " + badgeColor + ";" +
                "-fx-border-color: " + badgeColor + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;" +
                "-fx-padding: 5px 9px;" +
                "-fx-font-size: 10px;"
            );
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox nameRow = new HBox(
            hospital,
            spacer,
            status
        );

        nameRow.setAlignment(Pos.TOP_LEFT);

        Label star = new Label("☆");

        star.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-text-fill: #8B5E00;"
        );

        Label ratingValue = new Label(rating);

        ratingValue.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT + ";"
        );

        Label reviewsValue = new Label("(" + reviews + ")");

        reviewsValue.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-text-fill: #667085;"
        );

        HBox ratingRow = new HBox(
            6,
            star,
            ratingValue,
            reviewsValue
        );

        ratingRow.setAlignment(Pos.CENTER_LEFT);

        VBox distanceBox = createInfoBox(
            "▱",
            "Distance",
            distance
        );

        VBox travelBox = createInfoBox(
            "◷",
            "Travel Time",
            travelTime
        );

        HBox distanceTravel = new HBox(
            30,
            distanceBox,
            travelBox
        );

        VBox icuBox = createAvailabilityBox(
            "Available ICU\nBeds",
            icuBeds,
            BLUE
        );

        VBox doctorBox = createAvailabilityBox(
            "Available\nDoctors",
            availableDoctors,
            GREEN
        );

        HBox availability = new HBox(
            10,
            icuBox,
            doctorBox
        );

        Button details = new Button("Details");
        details.setMaxWidth(Double.MAX_VALUE);

        details.setStyle(
            "-fx-background-color: #DCE6F7;" +
            "-fx-text-fill: #344054;" +
            "-fx-font-size: 13px;" +
            "-fx-padding: 10px;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;"
        );

        Button navigation = new Button("Navigate");
        navigation.setMaxWidth(Double.MAX_VALUE);

        navigation.setStyle(
            "-fx-background-color: white;" +
            "-fx-text-fill: " + DARK_BLUE + ";" +
            "-fx-font-size: 13px;" +
            "-fx-padding: 10px;" +
            "-fx-border-color: " + DARK_BLUE + ";" +
            "-fx-border-radius: 10px;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;"
        );

        HBox detailNavigation = new HBox(
            10,
            details,
            navigation
        );

        HBox.setHgrow(details, Priority.ALWAYS);
        HBox.setHgrow(navigation, Priority.ALWAYS);

        Button selectHospital = new Button("Select Hospital");
        selectHospital.setMaxWidth(Double.MAX_VALUE);

        String selectColor = emergencyCard ? RED : BLUE;

        selectHospital.setStyle(
            "-fx-background-color: " + selectColor + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 11px;" +
            "-fx-background-radius: 9px;" +
            "-fx-cursor: hand;"
        );

        selectHospital.setOnAction(event ->
            System.out.println("Selected Hospital: " + hospitalName)
        );

        card.getChildren().addAll(
            nameRow,
            ratingRow,
            createDivider(),
            distanceTravel,
            createDivider(),
            availability,
            detailNavigation,
            selectHospital
        );

        return card;
    }

    // Create information box
    private VBox createInfoBox(
        String icon,
        String title,
        String value
    ) {
        VBox box = new VBox(3);

        HBox titleRow = new HBox(5);

        Label iconLabel = new Label(icon);
        iconLabel.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-text-fill: " + BLUE + ";"
        );

        Label titleLabel = new Label(title);
        titleLabel.setStyle(
            "-fx-font-size: 10px;" +
            "-fx-text-fill: " + SECONDARY_TEXT + ";"
        );

        titleRow.getChildren().addAll(
            iconLabel,
            titleLabel
        );

        Label valueLabel = new Label(value);
        valueLabel.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT + ";"
        );

        box.getChildren().addAll(
            titleRow,
            valueLabel
        );

        return box;
    }

    // Create availability box
    private VBox createAvailabilityBox(
        String title,
        String value,
        String valueColor
    ) {
        VBox box = new VBox(3);

        box.setPrefWidth(105);
        box.setMinWidth(100);
        box.setPadding(new Insets(10));

        box.setStyle(
            "-fx-background-color: " + LIGHT_BLUE + ";" +
            "-fx-background-radius: 11px;"
        );

        Label titleLabel = new Label(title);
        titleLabel.setStyle(
            "-fx-font-size: 10px;" +
            "-fx-text-fill: #344054;"
        );

        Label valueLabel = new Label(value);
        valueLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + valueColor + ";"
        );

        box.getChildren().addAll(
            titleLabel,
            valueLabel
        );

        return box;
    }

    // Create divider
    private Region createDivider() {
        Region divider = new Region();

        divider.setPrefHeight(1);
        divider.setMaxWidth(Double.MAX_VALUE);
        divider.setStyle(
            "-fx-background-color: " + DIVIDER + ";"
        );

        return divider;
    }

    // Create map
    private VBox createMapBox() {
        VBox mapBox = new VBox(12);

        mapBox.setPadding(new Insets(18));
        mapBox.setPrefWidth(280);
        mapBox.setMinWidth(280);
        mapBox.setMaxWidth(300);
        mapBox.setMinHeight(390);

        mapBox.setStyle(
            "-fx-background-color: " + WHITE + ";" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 14px;" +
            "-fx-background-radius: 14px;" +
            "-fx-border-width: 1px;"
        );

        Label mapText = new Label("Live Network Map");

        mapText.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT + ";"
        );

        Label mapSubText = new Label("Showing 4 locations");

        mapSubText.setStyle(
            "-fx-font-size: 11px;" +
            "-fx-text-fill: #667085;"
        );

        VBox mapTitle = new VBox(
            3,
            mapText,
            mapSubText
        );

        VBox mapArea = new VBox();

        mapArea.setAlignment(Pos.CENTER);
        mapArea.setPrefHeight(275);
        mapArea.setMinHeight(275);

        mapArea.setStyle(
            "-fx-background-color: #E7EEF2;" +
            "-fx-background-radius: 11px;"
        );

        Label mapIcon = new Label("⌖");

        mapIcon.setStyle(
            "-fx-font-size: 42px;" +
            "-fx-text-fill: " + BLUE + ";"
        );

        Label mapName = new Label("Live Hospital Map");

        mapName.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #475467;"
        );

        Label mapLocations = new Label("4 nearby locations");

        mapLocations.setStyle(
            "-fx-font-size: 11px;" +
            "-fx-text-fill: #667085;"
        );

        mapArea.getChildren().addAll(
            mapIcon,
            mapName,
            mapLocations
        );

        Label quickDirections = new Label("Quick Directions");

        quickDirections.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #344054;"
        );

        HBox directionBox = new HBox(10);

        directionBox.setAlignment(Pos.CENTER_LEFT);
        directionBox.setPadding(new Insets(10));

        directionBox.setStyle(
            "-fx-background-color: " + LIGHT_BLUE + ";" +
            "-fx-background-radius: 10px;"
        );

        Label directionIcon = new Label("⌖");

        directionIcon.setStyle(
            "-fx-background-color: " + BLUE + ";" +
            "-fx-text-fill: white;" +
            "-fx-padding: 7px;" +
            "-fx-background-radius: 7px;" +
            "-fx-font-size: 15px;"
        );

        VBox directionText = new VBox(2);

        Label directionTitle = new Label(
            "Closest: City General"
        );

        directionTitle.setStyle(
            "-fx-font-size: 11px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT + ";"
        );

        Label directionSub = new Label(
            "Fastest response route active"
        );

        directionSub.setStyle(
            "-fx-font-size: 10px;" +
            "-fx-text-fill: #667085;"
        );

        directionText.getChildren().addAll(
            directionTitle,
            directionSub
        );

        directionBox.getChildren().addAll(
            directionIcon,
            directionText
        );

        mapBox.getChildren().addAll(
            mapTitle,
            createDivider(),
            mapArea,
            quickDirections,
            directionBox
        );

        return mapBox;
    }
}