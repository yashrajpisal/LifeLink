package com.kurukshetra.view.driver;

import com.kurukshetra.view.Welcome;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DriverDashboard extends Application {

        // =========================================================
        // COLORS
        // =========================================================

        private static final String BG = "#F7F9FC";
        private static final String WHITE = "#FFFFFF";

        private static final String BLUE = "#08A1E5";
        private static final String BLUE_DARK = "#007FAE";
        private static final String LIGHT_BLUE = "#EAF7FD";

        private static final String GREEN = "#20B86A";
        private static final String LIGHT_GREEN = "#E7FBF1";

        private static final String ORANGE = "#C98A1B";
        private static final String LIGHT_ORANGE = "#FFF6E5";

        private static final String RED = "#D71920";
        private static final String LIGHT_RED = "#FDEBEC";

        private static final String TEXT = "#172B4D";
        private static final String SECONDARY = "#728096";

        private static final String BORDER = "#DCE5EC";
        private static final String LIGHT_BG = "#F0F4F9";

        public static Stage driverStage;
        private Scene driverScene;

        public static BorderPane root;

        // =========================================================
        // COLORS
        // =========================================================

        private static final String BODY_BACKGROUND = "#F7F9FC";
        private static final String DARK_BLUE = "#007FAE";
        private static final String ACTIVE_BLUE = "#DFF4FC";
        private static final String SECONDARY_TEXT = "#728096";

        private static final String HOVER_BACKGROUND = "#F2F8FB";

        // MAIN PAGE

        public BorderPane getEmergencyPane() {

                BorderPane root = new BorderPane();

                root.setStyle(
                                "-fx-background-color: " + BG + ";" +
                                                "-fx-font-family: 'Segoe UI';");

                VBox content = new VBox(18);

                content.setPadding(
                                new Insets(22, 30, 25, 30));

                // =====================================================
                // TOP SECTION
                // =====================================================

                HBox topSection = new HBox(16);

                VBox emergencyCard = createActiveEmergencyCard();

                HBox.setHgrow(
                                emergencyCard,
                                Priority.ALWAYS);

                VBox missionCard = createMissionProgressCard();

                topSection.getChildren().addAll(
                                emergencyCard,
                                missionCard);

                // =====================================================
                // HOSPITAL SECTION
                // =====================================================

                HBox hospitalArea = createHospitalArea();

                content.getChildren().addAll(
                                topSection,
                                hospitalArea);

                // =====================================================
                // SCROLL PANE
                // =====================================================

                ScrollPane scrollPane = new ScrollPane(content);

                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(false);

                scrollPane.setHbarPolicy(
                                ScrollPane.ScrollBarPolicy.NEVER);

                scrollPane.setVbarPolicy(
                                ScrollPane.ScrollBarPolicy.AS_NEEDED);

                scrollPane.setPannable(true);

                scrollPane.setStyle(
                                "-fx-background-color: transparent;" +
                                                "-fx-background: " + BG + ";" +
                                                "-fx-border-color: transparent;");

                root.setCenter(scrollPane);

                // =====================================================
                // PAGE ANIMATION
                // =====================================================

                FadeTransition fade = new FadeTransition(
                                Duration.millis(350),
                                root);

                fade.setFromValue(0.3);
                fade.setToValue(1);

                fade.play();

                return root;
        }

        // =========================================================
        // ACTIVE EMERGENCY CARD
        // =========================================================

        private VBox createActiveEmergencyCard() {

                VBox card = new VBox(16);

                card.setPadding(
                                new Insets(20));

                card.setStyle(
                                "-fx-background-color: " + WHITE + ";" +
                                                "-fx-border-color: " + BORDER + ";" +
                                                "-fx-border-width: 1;" +
                                                "-fx-background-radius: 14;" +
                                                "-fx-border-radius: 14;" +
                                                "-fx-effect: dropshadow(gaussian, rgba(30,60,90,0.07), 10, 0, 0, 3);");

                // =====================================================
                // HEADER
                // =====================================================

                HBox header = new HBox(10);

                header.setAlignment(
                                Pos.CENTER_LEFT);

                Label icon = new Label("⚠");

                icon.setStyle(
                                "-fx-font-size: 18px;" +
                                                "-fx-text-fill: " + RED + ";");

                VBox heading = new VBox(2);

                Label title = new Label("Active Emergency Request");

                title.setStyle(
                                "-fx-font-size: 17px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + TEXT + ";");

                Label subtitle = new Label(
                                "Patient pickup request assigned to you");

                subtitle.setStyle(
                                "-fx-font-size: 10px;" +
                                                "-fx-text-fill: " + SECONDARY + ";");

                heading.getChildren().addAll(
                                title,
                                subtitle);

                Region spacer = new Region();

                HBox.setHgrow(
                                spacer,
                                Priority.ALWAYS);

                Label priority = new Label("● HIGH PRIORITY");

                priority.setStyle(
                                "-fx-background-color: " + LIGHT_RED + ";" +
                                                "-fx-text-fill: " + RED + ";" +
                                                "-fx-font-size: 9px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-padding: 5 9 5 9;" +
                                                "-fx-background-radius: 9;");

                header.getChildren().addAll(
                                icon,
                                heading,
                                spacer,
                                priority);

                // =====================================================
                // BODY
                // =====================================================

                HBox body = new HBox(14);

                VBox details = new VBox(10);

                HBox.setHgrow(
                                details,
                                Priority.ALWAYS);

                // =====================================================
                // LOCATION BOX
                // =====================================================

                VBox locationBox = new VBox(5);

                locationBox.setPadding(
                                new Insets(12));

                locationBox.setStyle(
                                "-fx-background-color: " + LIGHT_BG + ";" +
                                                "-fx-background-radius: 10;");

                Label locationTitle = new Label("PATIENT PICKUP LOCATION");

                locationTitle.setStyle(
                                "-fx-font-size: 9px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + SECONDARY + ";");

                Label location = new Label("Sector 4, Main Street");

                location.setStyle(
                                "-fx-font-size: 15px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + TEXT + ";");

                HBox locationMeta = new HBox(16);

                Label distance = new Label("📍 2.4 km away");

                distance.setStyle(
                                "-fx-font-size: 10px;" +
                                                "-fx-text-fill: " + SECONDARY + ";");

                Label eta = new Label("⏱ ETA: 6 min");

                eta.setStyle(
                                "-fx-font-size: 10px;" +
                                                "-fx-text-fill: " + SECONDARY + ";");

                locationMeta.getChildren().addAll(
                                distance,
                                eta);

                locationBox.getChildren().addAll(
                                locationTitle,
                                location,
                                locationMeta);

                // =====================================================
                // PATIENT BOX
                // =====================================================

                VBox patientBox = new VBox(5);

                patientBox.setPadding(
                                new Insets(12));

                patientBox.setStyle(
                                "-fx-background-color: " + LIGHT_BG + ";" +
                                                "-fx-background-radius: 10;");

                Label patientTitle = new Label("PATIENT INFORMATION");

                patientTitle.setStyle(
                                "-fx-font-size: 9px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + SECONDARY + ";");

                Label patient = new Label(
                                "Male, ~45 yrs • Cardiac distress reported");

                patient.setStyle(
                                "-fx-font-size: 12px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + TEXT + ";");

                patientBox.getChildren().addAll(
                                patientTitle,
                                patient);

                details.getChildren().addAll(
                                locationBox,
                                patientBox);

                // =====================================================
                // MAP
                // =====================================================

                StackPane map = new StackPane();

                map.setPrefWidth(270);
                map.setMinWidth(240);
                map.setPrefHeight(155);

                map.setStyle(
                                "-fx-background-color: #E8EEF5;" +
                                                "-fx-background-radius: 10;" +
                                                "-fx-border-color: " + BORDER + ";" +
                                                "-fx-border-radius: 10;");

                Label mapText = new Label("🗺  Map Route");

                mapText.setStyle(
                                "-fx-font-size: 12px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + SECONDARY + ";");

                map.getChildren().add(
                                mapText);

                body.getChildren().addAll(
                                details,
                                map);

                // =====================================================
                // NAVIGATION BUTTON
                // =====================================================

                Button navigation = new Button("⌖   START NAVIGATION");

                navigation.setMaxWidth(
                                Double.MAX_VALUE);

                navigation.setPrefHeight(42);

                navigation.setStyle(
                                "-fx-background-color: " + BLUE + ";" +
                                                "-fx-text-fill: white;" +
                                                "-fx-font-size: 11px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 9;" +
                                                "-fx-cursor: hand;");

                navigation.setOnMouseEntered(e -> navigation.setStyle(
                                "-fx-background-color: " + BLUE_DARK + ";" +
                                                "-fx-text-fill: white;" +
                                                "-fx-font-size: 11px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 9;" +
                                                "-fx-cursor: hand;"));

                navigation.setOnMouseExited(e -> navigation.setStyle(
                                "-fx-background-color: " + BLUE + ";" +
                                                "-fx-text-fill: white;" +
                                                "-fx-font-size: 11px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 9;" +
                                                "-fx-cursor: hand;"));

                card.getChildren().addAll(
                                header,
                                body,
                                navigation);

                return card;
        }

        // =========================================================
        // MISSION PROGRESS CARD
        // =========================================================

        private VBox createMissionProgressCard() {

                VBox card = new VBox(14);

                card.setPrefWidth(275);
                card.setMinWidth(260);

                card.setPadding(
                                new Insets(20));

                card.setStyle(
                                "-fx-background-color: " + WHITE + ";" +
                                                "-fx-border-color: " + BORDER + ";" +
                                                "-fx-border-width: 1;" +
                                                "-fx-background-radius: 14;" +
                                                "-fx-border-radius: 14;" +
                                                "-fx-effect: dropshadow(gaussian, rgba(30,60,90,0.07), 10, 0, 0, 3);");

                VBox heading = new VBox(2);

                Label title = new Label("Mission Progress");

                title.setStyle(
                                "-fx-font-size: 15px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + TEXT + ";");

                Label subtitle = new Label("Emergency response status");

                subtitle.setStyle(
                                "-fx-font-size: 10px;" +
                                                "-fx-text-fill: " + SECONDARY + ";");

                heading.getChildren().addAll(
                                title,
                                subtitle);

                VBox timeline = new VBox(13);

                timeline.getChildren().add(
                                createTimelineStep(
                                                "●",
                                                "Emergency Received",
                                                "10:42 AM",
                                                true,
                                                false,
                                                GREEN));

                timeline.getChildren().add(
                                createTimelineStep(
                                                "●",
                                                "En Route to Patient",
                                                "Navigation started",
                                                false,
                                                true,
                                                BLUE));

                timeline.getChildren().add(
                                createTimelineStep(
                                                "○",
                                                "Patient Picked Up",
                                                "Awaiting pickup",
                                                false,
                                                false,
                                                SECONDARY));

                timeline.getChildren().add(
                                createTimelineStep(
                                                "○",
                                                "En Route to Hospital",
                                                "",
                                                false,
                                                false,
                                                SECONDARY));

                timeline.getChildren().add(
                                createTimelineStep(
                                                "○",
                                                "Patient Delivered",
                                                "",
                                                false,
                                                false,
                                                SECONDARY));

                card.getChildren().addAll(
                                heading,
                                timeline);

                return card;
        }

        // =========================================================
        // TIMELINE STEP
        // =========================================================

        private HBox createTimelineStep(
                        String symbol,
                        String mainText,
                        String subText,
                        boolean done,
                        boolean active,
                        String color) {

                HBox step = new HBox(10);

                step.setAlignment(
                                Pos.TOP_LEFT);

                Label icon = new Label(symbol);

                icon.setStyle(
                                "-fx-font-size: 13px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + color + ";");

                VBox textBox = new VBox(2);

                Label main = new Label(mainText);

                main.setStyle(
                                "-fx-font-size: 11px;" +
                                                "-fx-font-weight: " +
                                                ((active || done) ? "bold" : "normal") +
                                                ";" +
                                                "-fx-text-fill: " +
                                                ((active || done) ? TEXT : SECONDARY) +
                                                ";");

                textBox.getChildren().add(
                                main);

                if (!subText.isEmpty()) {

                        Label sub = new Label(subText);

                        sub.setStyle(
                                        "-fx-font-size: 9px;" +
                                                        "-fx-text-fill: " +
                                                        (active ? BLUE : SECONDARY) +
                                                        ";");

                        textBox.getChildren().add(
                                        sub);
                }

                step.getChildren().addAll(
                                icon,
                                textBox);

                return step;
        }

        // =========================================================
        // HOSPITAL AREA
        //
        // LEFT = Recommended Hospitals
        // RIGHT = Other Nearby Hospitals
        // =========================================================

        private HBox createHospitalArea() {

                HBox area = new HBox(18);

                area.setFillHeight(true);

                // =====================================================
                // LEFT SIDE - RECOMMENDED HOSPITALS
                // =====================================================

                VBox recommendedSection = createRecommendedHospitalsSection();

                HBox.setHgrow(
                                recommendedSection,
                                Priority.ALWAYS);

                // =====================================================
                // RIGHT SIDE - OTHER NEARBY HOSPITALS
                // =====================================================

                VBox otherHospitals = createOtherNearbyHospitals();

                otherHospitals.setPrefWidth(300);
                otherHospitals.setMinWidth(280);
                otherHospitals.setMaxWidth(330);

                area.getChildren().addAll(
                                recommendedSection,
                                otherHospitals);

                return area;
        }

        // =========================================================
        // RECOMMENDED HOSPITALS SECTION
        // =========================================================

        private VBox createRecommendedHospitalsSection() {

                VBox section = new VBox(12);

                // =====================================================
                // HEADER
                // =====================================================

                HBox header = new HBox();

                header.setAlignment(
                                Pos.CENTER_LEFT);

                VBox heading = new VBox(2);

                Label title = new Label(
                                "Recommended Hospitals Nearby");

                title.setStyle(
                                "-fx-font-size: 15px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + TEXT + ";");

                Label subtitle = new Label(
                                "Based on availability, distance and emergency facilities");

                subtitle.setStyle(
                                "-fx-font-size: 10px;" +
                                                "-fx-text-fill: " + SECONDARY + ";");

                heading.getChildren().addAll(
                                title,
                                subtitle);

                Region spacer = new Region();

                HBox.setHgrow(
                                spacer,
                                Priority.ALWAYS);

                Label count = new Label("5 Hospitals");

                count.setStyle(
                                "-fx-font-size: 10px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + BLUE + ";" +
                                                "-fx-background-color: " + LIGHT_BLUE + ";" +
                                                "-fx-padding: 5 9 5 9;" +
                                                "-fx-background-radius: 8;");

                header.getChildren().addAll(
                                heading,
                                spacer,
                                count);

                // =====================================================
                // HORIZONTAL SCROLL
                // =====================================================

                HBox cards = new HBox(14);

                cards.setPadding(
                                new Insets(2, 3, 8, 2));

                // =====================================================
                // HOSPITAL 1
                // =====================================================

                VBox hospital1 = createHospitalCard(
                                "City General Hospital",
                                "Emergency Available",
                                "12 Beds",
                                "4 Doctors",
                                "5.2 km",
                                "12 min",
                                GREEN,
                                LIGHT_GREEN);

                // =====================================================
                // HOSPITAL 2
                // =====================================================

                VBox hospital2 = createHospitalCard(
                                "St. Jude's Medical",
                                "Limited Capacity",
                                "3 Beds",
                                "2 Doctors",
                                "7.8 km",
                                "18 min",
                                ORANGE,
                                LIGHT_ORANGE);

                // =====================================================
                // HOSPITAL 3
                // =====================================================

                VBox hospital3 = createHospitalCard(
                                "County Annex Facility",
                                "Emergency Available",
                                "8 Beds",
                                "6 Doctors",
                                "9.1 km",
                                "22 min",
                                GREEN,
                                LIGHT_GREEN);

                // =====================================================
                // HOSPITAL 4
                // =====================================================

                VBox hospital4 = createHospitalCard(
                                "Metro Care Hospital",
                                "Emergency Available",
                                "15 Beds",
                                "7 Doctors",
                                "6.4 km",
                                "14 min",
                                GREEN,
                                LIGHT_GREEN);

                // =====================================================
                // HOSPITAL 5
                // =====================================================

                VBox hospital5 = createHospitalCard(
                                "Apollo Emergency Center",
                                "Limited Capacity",
                                "5 Beds",
                                "3 Doctors",
                                "10.2 km",
                                "25 min",
                                ORANGE,
                                LIGHT_ORANGE);

                cards.getChildren().addAll(
                                hospital1,
                                hospital2,
                                hospital3,
                                hospital4,
                                hospital5);

                // =====================================================
                // HORIZONTAL SCROLL PANE
                // =====================================================

                ScrollPane horizontalScroll = new ScrollPane(cards);

                horizontalScroll.setFitToHeight(true);
                horizontalScroll.setFitToWidth(false);

                horizontalScroll.setHbarPolicy(
                                ScrollPane.ScrollBarPolicy.AS_NEEDED);

                horizontalScroll.setVbarPolicy(
                                ScrollPane.ScrollBarPolicy.NEVER);

                horizontalScroll.setPannable(true);

                horizontalScroll.setPrefHeight(245);
                horizontalScroll.setMinHeight(245);

                horizontalScroll.setStyle(
                                "-fx-background-color: transparent;" +
                                                "-fx-background: transparent;" +
                                                "-fx-border-color: transparent;");

                section.getChildren().addAll(
                                header,
                                horizontalScroll);

                return section;
        }

        // =========================================================
        // OTHER NEARBY HOSPITALS
        // =========================================================

        private VBox createOtherNearbyHospitals() {

                VBox box = new VBox(10);

                box.setPadding(
                                new Insets(16));

                box.setStyle(
                                "-fx-background-color: " + WHITE + ";" +
                                                "-fx-border-color: " + BORDER + ";" +
                                                "-fx-border-width: 1;" +
                                                "-fx-background-radius: 14;" +
                                                "-fx-border-radius: 14;" +
                                                "-fx-effect: dropshadow(gaussian, rgba(30,60,90,0.06), 8, 0, 0, 2);");

                // =====================================================
                // HEADER
                // =====================================================

                HBox header = new HBox();

                header.setAlignment(
                                Pos.CENTER_LEFT);

                VBox heading = new VBox(2);

                Label title = new Label("Other Nearby Hospitals");

                title.setStyle(
                                "-fx-font-size: 14px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + TEXT + ";");

                Label subtitle = new Label(
                                "Nearby facilities");

                subtitle.setStyle(
                                "-fx-font-size: 9px;" +
                                                "-fx-text-fill: " + SECONDARY + ";");

                heading.getChildren().addAll(
                                title,
                                subtitle);

                Region spacer = new Region();

                HBox.setHgrow(
                                spacer,
                                Priority.ALWAYS);

                Label count = new Label("4");

                count.setStyle(
                                "-fx-background-color: " + LIGHT_BLUE + ";" +
                                                "-fx-text-fill: " + BLUE + ";" +
                                                "-fx-font-size: 9px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-padding: 4 8 4 8;" +
                                                "-fx-background-radius: 8;");

                header.getChildren().addAll(
                                heading,
                                spacer,
                                count);

                // =====================================================
                // HOSPITAL LIST
                // =====================================================

                VBox list = new VBox(8);

                list.getChildren().add(
                                createNearbyHospitalRow(
                                                "Green Valley Hospital",
                                                "4.8 km",
                                                "10 min",
                                                "6 Beds",
                                                GREEN));

                list.getChildren().add(
                                createNearbyHospitalRow(
                                                "Sunrise Medical Center",
                                                "6.1 km",
                                                "15 min",
                                                "4 Beds",
                                                GREEN));

                list.getChildren().add(
                                createNearbyHospitalRow(
                                                "Central Health Clinic",
                                                "7.3 km",
                                                "17 min",
                                                "2 Beds",
                                                ORANGE));

                list.getChildren().add(
                                createNearbyHospitalRow(
                                                "Hope Emergency Hospital",
                                                "8.6 km",
                                                "21 min",
                                                "7 Beds",
                                                GREEN));

                // =====================================================
                // LIST SCROLL
                // =====================================================

                ScrollPane listScroll = new ScrollPane(list);

                listScroll.setFitToWidth(true);
                listScroll.setHbarPolicy(
                                ScrollPane.ScrollBarPolicy.NEVER);

                listScroll.setVbarPolicy(
                                ScrollPane.ScrollBarPolicy.NEVER);

                listScroll.setPannable(true);

                listScroll.setPrefHeight(190);

                listScroll.setStyle(
                                "-fx-background-color: transparent;" +
                                                "-fx-background: transparent;" +
                                                "-fx-border-color: transparent;");

                box.getChildren().addAll(
                                header,
                                listScroll);

                return box;
        }

        // =========================================================
        // OTHER HOSPITAL LIST ROW
        // =========================================================

        private HBox createNearbyHospitalRow(
                        String name,
                        String distance,
                        String time,
                        String beds,
                        String statusColor) {

                HBox row = new HBox(9);

                row.setAlignment(
                                Pos.CENTER_LEFT);

                row.setPadding(
                                new Insets(9));

                row.setStyle(
                                "-fx-background-color: #F8FAFC;" +
                                                "-fx-border-color: #EEF2F6;" +
                                                "-fx-border-width: 1;" +
                                                "-fx-background-radius: 9;" +
                                                "-fx-border-radius: 9;" +
                                                "-fx-cursor: hand;");

                // =====================================================
                // HOSPITAL ICON
                // =====================================================

                StackPane iconBox = new StackPane();

                iconBox.setPrefSize(
                                32,
                                32);

                iconBox.setMinSize(
                                32,
                                32);

                iconBox.setStyle(
                                "-fx-background-color: " + LIGHT_BLUE + ";" +
                                                "-fx-background-radius: 8;");

                Label icon = new Label("🏥");

                icon.setStyle(
                                "-fx-font-size: 13px;");

                iconBox.getChildren().add(
                                icon);

                // =====================================================
                // NAME
                // =====================================================

                VBox nameBox = new VBox(2);

                HBox.setHgrow(
                                nameBox,
                                Priority.ALWAYS);

                Label hospitalName = new Label(name);

                hospitalName.setStyle(
                                "-fx-font-size: 10px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + TEXT + ";");

                Label info = new Label(
                                distance + "  •  " + time);

                info.setStyle(
                                "-fx-font-size: 8px;" +
                                                "-fx-text-fill: " + SECONDARY + ";");

                nameBox.getChildren().addAll(
                                hospitalName,
                                info);

                // =====================================================
                // BEDS
                // =====================================================

                Label bedLabel = new Label(beds);

                bedLabel.setStyle(
                                "-fx-font-size: 8px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + statusColor + ";");

                row.getChildren().addAll(
                                iconBox,
                                nameBox,
                                bedLabel);

                // =====================================================
                // HOVER
                // =====================================================

                row.setOnMouseEntered(e -> row.setStyle(
                                "-fx-background-color: " + LIGHT_BLUE + ";" +
                                                "-fx-border-color: " + BLUE + ";" +
                                                "-fx-border-width: 1;" +
                                                "-fx-background-radius: 9;" +
                                                "-fx-border-radius: 9;" +
                                                "-fx-cursor: hand;"));

                row.setOnMouseExited(e -> row.setStyle(
                                "-fx-background-color: #F8FAFC;" +
                                                "-fx-border-color: #EEF2F6;" +
                                                "-fx-border-width: 1;" +
                                                "-fx-background-radius: 9;" +
                                                "-fx-border-radius: 9;" +
                                                "-fx-cursor: hand;"));

                return row;
        }

        // =========================================================
        // HOSPITAL CARD
        // =========================================================

        private VBox createHospitalCard(
                        String name,
                        String status,
                        String beds,
                        String doctors,
                        String distance,
                        String time,
                        String accent,
                        String statusBackground) {

                VBox card = new VBox(11);

                card.setPrefWidth(245);
                card.setMinWidth(245);
                card.setMaxWidth(245);

                card.setPadding(
                                new Insets(16));

                card.setStyle(
                                "-fx-background-color: " + WHITE + ";" +
                                                "-fx-border-color: " + BORDER + ";" +
                                                "-fx-border-width: 1;" +
                                                "-fx-border-radius: 12;" +
                                                "-fx-background-radius: 12;" +
                                                "-fx-effect: dropshadow(gaussian, rgba(30,60,90,0.06), 8, 0, 0, 2);" +
                                                "-fx-cursor: hand;");

                // =====================================================
                // HOSPITAL HEADER
                // =====================================================

                HBox top = new HBox(10);

                top.setAlignment(
                                Pos.CENTER_LEFT);

                StackPane hospitalIcon = new StackPane();

                hospitalIcon.setPrefSize(
                                34,
                                34);

                hospitalIcon.setMinSize(
                                34,
                                34);

                hospitalIcon.setStyle(
                                "-fx-background-color: " + LIGHT_BLUE + ";" +
                                                "-fx-background-radius: 9;");

                Label icon = new Label("🏥");

                icon.setStyle(
                                "-fx-font-size: 14px;");

                hospitalIcon.getChildren().add(
                                icon);

                VBox nameBox = new VBox(2);

                HBox.setHgrow(
                                nameBox,
                                Priority.ALWAYS);

                Label hospitalName = new Label(name);

                hospitalName.setStyle(
                                "-fx-font-size: 12px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + TEXT + ";");

                Label recommendation = new Label("AI Recommended");

                recommendation.setStyle(
                                "-fx-font-size: 9px;" +
                                                "-fx-text-fill: " + SECONDARY + ";");

                nameBox.getChildren().addAll(
                                hospitalName,
                                recommendation);

                Label statusLabel = new Label(status);

                statusLabel.setStyle(
                                "-fx-background-color: " + statusBackground + ";" +
                                                "-fx-text-fill: " + accent + ";" +
                                                "-fx-font-size: 8px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-padding: 4 7 4 7;" +
                                                "-fx-background-radius: 7;");

                top.getChildren().addAll(
                                hospitalIcon,
                                nameBox,
                                statusLabel);

                // =====================================================
                // DIVIDER
                // =====================================================

                Region divider = new Region();

                divider.setPrefHeight(
                                1);

                divider.setStyle(
                                "-fx-background-color: #EEF1F6;");

                // =====================================================
                // FOUR METRIC BOXES - 2 x 2
                // =====================================================

                GridPane metricsGrid = new GridPane();

                metricsGrid.setHgap(8);
                metricsGrid.setVgap(8);

                VBox bedsBox = createMetricBox(
                                "BEDS",
                                beds,
                                beds.startsWith("3")
                                                ? ORANGE
                                                : GREEN);

                VBox doctorsBox = createMetricBox(
                                "DOCTORS",
                                doctors,
                                TEXT);

                VBox distanceBox = createMetricBox(
                                "DISTANCE",
                                distance,
                                BLUE);

                VBox etaBox = createMetricBox(
                                "ETA",
                                time,
                                BLUE);

                ColumnConstraints column1 = new ColumnConstraints();

                column1.setPercentWidth(50);

                ColumnConstraints column2 = new ColumnConstraints();

                column2.setPercentWidth(50);

                metricsGrid.getColumnConstraints().addAll(
                                column1,
                                column2);

                metricsGrid.add(
                                bedsBox,
                                0,
                                0);

                metricsGrid.add(
                                doctorsBox,
                                1,
                                0);

                metricsGrid.add(
                                distanceBox,
                                0,
                                1);

                metricsGrid.add(
                                etaBox,
                                1,
                                1);

                // =====================================================
                // ACTION BUTTONS
                // SAME WIDTH
                // =====================================================

                HBox actions = new HBox(8);

                Button directionButton = new Button("Get Directions");

                Button selectButton = new Button("Select");

                // Both buttons same width
                directionButton.setPrefWidth(100);
                directionButton.setMinWidth(100);
                directionButton.setMaxWidth(100);

                selectButton.setPrefWidth(100);
                selectButton.setMinWidth(100);
                selectButton.setMaxWidth(100);

                HBox.setHgrow(
                                directionButton,
                                Priority.ALWAYS);

                HBox.setHgrow(
                                selectButton,
                                Priority.ALWAYS);

                directionButton.setPrefHeight(
                                34);

                selectButton.setPrefHeight(
                                34);

                directionButton.setStyle(
                                "-fx-background-color: " + BLUE + ";" +
                                                "-fx-text-fill: white;" +
                                                "-fx-font-size: 10px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 8;" +
                                                "-fx-cursor: hand;");

                selectButton.setStyle(
                                "-fx-background-color: " + LIGHT_BLUE + ";" +
                                                "-fx-text-fill: " + BLUE + ";" +
                                                "-fx-font-size: 10px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 8;" +
                                                "-fx-cursor: hand;");

                directionButton.setOnMouseEntered(e -> directionButton.setStyle(
                                "-fx-background-color: " + BLUE_DARK + ";" +
                                                "-fx-text-fill: white;" +
                                                "-fx-font-size: 10px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 8;" +
                                                "-fx-cursor: hand;"));

                directionButton.setOnMouseExited(e -> directionButton.setStyle(
                                "-fx-background-color: " + BLUE + ";" +
                                                "-fx-text-fill: white;" +
                                                "-fx-font-size: 10px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 8;" +
                                                "-fx-cursor: hand;"));

                selectButton.setOnMouseEntered(e -> selectButton.setStyle(
                                "-fx-background-color: " + BLUE + ";" +
                                                "-fx-text-fill: white;" +
                                                "-fx-font-size: 10px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 8;" +
                                                "-fx-cursor: hand;"));

                selectButton.setOnMouseExited(e -> selectButton.setStyle(
                                "-fx-background-color: " + LIGHT_BLUE + ";" +
                                                "-fx-text-fill: " + BLUE + ";" +
                                                "-fx-font-size: 10px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 8;" +
                                                "-fx-cursor: hand;"));

                actions.getChildren().addAll(
                                directionButton,
                                selectButton);

                // =====================================================
                // ADD CONTENT
                // =====================================================

                card.getChildren().addAll(
                                top,
                                divider,
                                metricsGrid,
                                actions);

                // =====================================================
                // CARD HOVER
                // =====================================================

                card.setOnMouseEntered(e -> {

                        card.setStyle(
                                        "-fx-background-color: " + WHITE + ";" +
                                                        "-fx-border-color: " + BLUE + ";" +
                                                        "-fx-border-width: 1;" +
                                                        "-fx-border-radius: 12;" +
                                                        "-fx-background-radius: 12;" +
                                                        "-fx-effect: dropshadow(gaussian, rgba(8,161,229,0.15), 12, 0, 0, 3);"
                                                        +
                                                        "-fx-cursor: hand;");
                });

                card.setOnMouseExited(e -> {

                        card.setStyle(
                                        "-fx-background-color: " + WHITE + ";" +
                                                        "-fx-border-color: " + BORDER + ";" +
                                                        "-fx-border-width: 1;" +
                                                        "-fx-border-radius: 12;" +
                                                        "-fx-background-radius: 12;" +
                                                        "-fx-effect: dropshadow(gaussian, rgba(30,60,90,0.06), 8, 0, 0, 2);"
                                                        +
                                                        "-fx-cursor: hand;");
                });

                // =====================================================
                // CARD ANIMATION
                // =====================================================

                card.setOpacity(0);
                card.setScaleX(0.97);
                card.setScaleY(0.97);

                FadeTransition fade = new FadeTransition(
                                Duration.millis(350),
                                card);

                fade.setToValue(1);

                ScaleTransition scale = new ScaleTransition(
                                Duration.millis(350),
                                card);

                scale.setToX(1);
                scale.setToY(1);

                new ParallelTransition(
                                fade,
                                scale).play();

                return card;
        }

        // =========================================================
        // METRIC BOX
        // =========================================================

        private VBox createMetricBox(
                        String label,
                        String value,
                        String valueColor) {

                VBox box = new VBox(3);

                box.setPadding(
                                new Insets(8, 9, 8, 9));

                box.setMaxWidth(
                                Double.MAX_VALUE);

                box.setStyle(
                                "-fx-background-color: #F8FAFC;" +
                                                "-fx-background-radius: 8;" +
                                                "-fx-border-color: #EEF2F6;" +
                                                "-fx-border-width: 1;" +
                                                "-fx-border-radius: 8;");

                Label labelText = new Label(label);

                labelText.setStyle(
                                "-fx-font-size: 8px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + SECONDARY + ";");

                Label valueText = new Label(value);

                valueText.setStyle(
                                "-fx-font-size: 11px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " + valueColor + ";");

                box.getChildren().addAll(
                                labelText,
                                valueText);

                return box;
        }

        @Override
        public void start(Stage stage) {

                driverStage = stage;

                // -----------------------------------------------------
                // ROOT
                // -----------------------------------------------------

                root = new BorderPane();

                root.setStyle(
                                "-fx-background-color: " +
                                                BODY_BACKGROUND + ";");

                // -----------------------------------------------------
                // LEFT SIDEBAR
                // -----------------------------------------------------

                VBox sidebar = createSidebar(root);

                root.setLeft(sidebar);

                // -----------------------------------------------------
                // DEFAULT PAGE = DASHBOARD
                // -----------------------------------------------------

                DriverDashboard driverDashboard = new DriverDashboard();

                root.setCenter(
                                driverDashboard.getEmergencyPane());

                // -----------------------------------------------------
                // SCENE
                // -----------------------------------------------------

                driverScene = new Scene(
                                root,
                                1553,
                                820);

                driverStage.setScene(
                                driverScene);

                driverStage.setTitle(
                                "LifeLink - Ambulance Driver");

                driverStage.setMaximized(
                                true);

                driverStage.show();
        }

        // =========================================================
        // SIDEBAR
        // =========================================================

        private VBox createSidebar(
                        BorderPane root) {

                VBox sidebar = new VBox();

                sidebar.setPrefWidth(300);
                sidebar.setMinWidth(300);
                sidebar.setMaxWidth(300);

                sidebar.setPadding(
                                new Insets(
                                                28,
                                                18,
                                                20,
                                                18));

                sidebar.setStyle(
                                "-fx-background-color: " +
                                                WHITE + ";" +

                                                "-fx-border-color: " +
                                                BORDER + ";" +

                                                "-fx-border-width: 0 1 0 0;");

                // =====================================================
                // BRAND
                // =====================================================

                VBox brand = new VBox(4);

                brand.setPadding(
                                new Insets(
                                                0,
                                                16,
                                                38,
                                                16));

                Label lifeLink = new Label(
                                "LifeLink");

                lifeLink.setStyle(
                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 34px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " +
                                                BLUE + ";");

                Label ambulanceName = new Label(
                                "Ambulance Alpha-1");

                ambulanceName.setStyle(
                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 14px;" +
                                                "-fx-font-weight: normal;" +
                                                "-fx-text-fill: " +
                                                SECONDARY_TEXT + ";");

                brand.getChildren().addAll(
                                lifeLink,
                                ambulanceName);

                // =====================================================
                // NAVIGATION
                // =====================================================

                VBox navigation = new VBox(7);

                // -----------------------------------------------------
                // DASHBOARD
                // -----------------------------------------------------

                Button dashboardButton = createNavigationButton(
                                "▦",
                                "Dashboard",
                                true);

                // -----------------------------------------------------
                // NOTIFICATIONS
                // -----------------------------------------------------

                Button notificationButton = createNavigationButton(
                                "♧",
                                "Notifications",
                                false);

                // -----------------------------------------------------
                // TRIP HISTORY
                // -----------------------------------------------------

                Button tripHistoryButton = createNavigationButton(
                                "◷",
                                "Trip History",
                                false);

                // -----------------------------------------------------
                // SETTINGS
                // -----------------------------------------------------

                Button settingsButton = createNavigationButton(
                                "⚙",
                                "Settings",
                                false);

                navigation.getChildren().addAll(
                                dashboardButton,
                                notificationButton,
                                tripHistoryButton,
                                settingsButton);

                // =====================================================
                // NAVIGATION ACTIONS
                // =====================================================

                // -----------------------------------------------------
                // DASHBOARD
                // -----------------------------------------------------

                dashboardButton.setOnAction(event -> {

                        setActiveNavigation(
                                        navigation,
                                        dashboardButton);

                        DriverDashboard driverDashboard = new DriverDashboard();

                        BorderPane dashboardPane = driverDashboard.getEmergencyPane();

                        root.setCenter(
                                        dashboardPane);
                });

                // -----------------------------------------------------
                // NOTIFICATIONS
                // -----------------------------------------------------

                notificationButton.setOnAction(event -> {

                        setActiveNavigation(
                                        navigation,
                                        notificationButton);

                        root.setCenter(
                                        createNotificationPage());
                });

                // -----------------------------------------------------
                // TRIP HISTORY
                // -----------------------------------------------------

                tripHistoryButton.setOnAction(event -> {

                        setActiveNavigation(
                                        navigation,
                                        tripHistoryButton);

                        DriverTripHistory driverTripHistory = new DriverTripHistory();

                        root.setCenter(
                                        driverTripHistory.getDriverTripsPage());
                });

                // -----------------------------------------------------
                // SETTINGS
                // -----------------------------------------------------

                settingsButton.setOnAction(event -> {

                        setActiveNavigation(
                                        navigation,
                                        settingsButton);

                        DriverSetting driverSetting = new DriverSetting();

                        root.setCenter(
                                        driverSetting.getAppSettingsPage());
                });

                // =====================================================
                // BOTTOM SPACER
                // =====================================================

                Region spacer = new Region();

                VBox.setVgrow(
                                spacer,
                                Priority.ALWAYS);

                // =====================================================
                // SIDEBAR DIVIDER
                // =====================================================

                Region divider = createSidebarDivider();

                // =====================================================
                // LOGOUT
                // =====================================================

                Button logoutButton = createLogoutButton();

                logoutButton.setOnAction(event -> {

                        try {

                                Welcome welcome = new Welcome();

                                welcome.start(
                                                driverStage);

                        } catch (Exception e) {

                                e.printStackTrace();

                        }
                });

                // =====================================================
                // ADD EVERYTHING
                // =====================================================

                sidebar.getChildren().addAll(
                                brand,
                                navigation,
                                spacer,
                                divider,
                                logoutButton);

                return sidebar;
        }

        // =========================================================
        // NAVIGATION BUTTON
        // =========================================================

        private Button createNavigationButton(
                        String icon,
                        String text,
                        boolean active) {

                Button button = new Button();

                HBox content = new HBox(13);

                content.setAlignment(
                                Pos.CENTER_LEFT);

                Label iconLabel = new Label(icon);

                iconLabel.setMinWidth(24);

                iconLabel.setPrefWidth(24);

                iconLabel.setAlignment(
                                Pos.CENTER);

                Label textLabel = new Label(text);

                content.getChildren().addAll(
                                iconLabel,
                                textLabel);

                button.setGraphic(
                                content);

                button.setText("");

                button.setMaxWidth(
                                Double.MAX_VALUE);

                button.setMinHeight(
                                52);

                button.setPrefHeight(
                                52);

                button.setAlignment(
                                Pos.CENTER_LEFT);

                button.setPadding(
                                new Insets(
                                                0,
                                                14,
                                                0,
                                                14));

                if (active) {

                        applyActiveStyle(
                                        button,
                                        iconLabel,
                                        textLabel);

                } else {

                        applyInactiveStyle(
                                        button,
                                        iconLabel,
                                        textLabel);
                }

                // =====================================================
                // HOVER
                // =====================================================

                button.setOnMouseEntered(event -> {

                        if (!button
                                        .getStyleClass()
                                        .contains("active-nav")) {

                                applyHoverStyle(
                                                button,
                                                iconLabel,
                                                textLabel);
                        }
                });

                button.setOnMouseExited(event -> {

                        if (!button
                                        .getStyleClass()
                                        .contains("active-nav")) {

                                applyInactiveStyle(
                                                button,
                                                iconLabel,
                                                textLabel);
                        }
                });

                return button;
        }

        // =========================================================
        // ACTIVE STYLE
        // =========================================================

        private void applyActiveStyle(
                        Button button,
                        Label icon,
                        Label text) {

                if (!button
                                .getStyleClass()
                                .contains("active-nav")) {

                        button.getStyleClass()
                                        .add("active-nav");
                }

                button.setStyle(
                                "-fx-background-color: " +
                                                ACTIVE_BLUE + ";" +

                                                "-fx-background-radius: 10px;" +

                                                "-fx-border-color: " +
                                                BLUE + ";" +

                                                "-fx-border-width: 0 0 0 4px;" +

                                                "-fx-border-radius: 10px;" +

                                                "-fx-cursor: hand;");

                icon.setStyle(
                                "-fx-font-family: 'Segoe UI Symbol';" +
                                                "-fx-font-size: 19px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " +
                                                BLUE + ";");

                text.setStyle(
                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 14px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " +
                                                TEXT + ";");
        }

        // =========================================================
        // INACTIVE STYLE
        // =========================================================

        private void applyInactiveStyle(
                        Button button,
                        Label icon,
                        Label text) {

                button.setStyle(
                                "-fx-background-color: transparent;" +
                                                "-fx-background-radius: 10px;" +
                                                "-fx-border-color: transparent;" +
                                                "-fx-cursor: hand;");

                icon.setStyle(
                                "-fx-font-family: 'Segoe UI Symbol';" +
                                                "-fx-font-size: 19px;" +
                                                "-fx-text-fill: " +
                                                SECONDARY_TEXT + ";");

                text.setStyle(
                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 14px;" +
                                                "-fx-font-weight: normal;" +
                                                "-fx-text-fill: " +
                                                SECONDARY_TEXT + ";");
        }

        // =========================================================
        // HOVER STYLE
        // =========================================================

        private void applyHoverStyle(
                        Button button,
                        Label icon,
                        Label text) {

                button.setStyle(
                                "-fx-background-color: " +
                                                HOVER_BACKGROUND + ";" +

                                                "-fx-background-radius: 10px;" +

                                                "-fx-border-color: transparent;" +

                                                "-fx-cursor: hand;");

                icon.setStyle(
                                "-fx-font-family: 'Segoe UI Symbol';" +
                                                "-fx-font-size: 19px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " +
                                                BLUE + ";");

                text.setStyle(
                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 14px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " +
                                                TEXT + ";");
        }

        // =========================================================
        // SET ACTIVE NAVIGATION
        // =========================================================

        private void setActiveNavigation(
                        VBox navigation,
                        Button selectedButton) {

                for (Node node : navigation.getChildren()) {

                        if (node instanceof Button button) {

                                if (button.getGraphic() instanceof HBox content) {

                                        Label icon = (Label) content
                                                        .getChildren()
                                                        .get(0);

                                        Label text = (Label) content
                                                        .getChildren()
                                                        .get(1);

                                        button.getStyleClass()
                                                        .remove("active-nav");

                                        if (button == selectedButton) {

                                                applyActiveStyle(
                                                                button,
                                                                icon,
                                                                text);

                                        } else {

                                                applyInactiveStyle(
                                                                button,
                                                                icon,
                                                                text);
                                        }
                                }
                        }
                }
        }

        // =========================================================
        // LOGOUT BUTTON
        // =========================================================

        private Button createLogoutButton() {

                Button logout = new Button();

                HBox content = new HBox(13);

                content.setAlignment(
                                Pos.CENTER_LEFT);

                Label icon = new Label("↪");

                icon.setMinWidth(24);

                icon.setPrefWidth(24);

                icon.setAlignment(
                                Pos.CENTER);

                Label text = new Label("Logout");

                icon.setStyle(
                                "-fx-font-family: 'Segoe UI Symbol';" +
                                                "-fx-font-size: 20px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " +
                                                SECONDARY_TEXT + ";");

                text.setStyle(
                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 14px;" +
                                                "-fx-text-fill: " +
                                                SECONDARY_TEXT + ";");

                content.getChildren().addAll(
                                icon,
                                text);

                logout.setGraphic(
                                content);

                logout.setText("");

                logout.setMaxWidth(
                                Double.MAX_VALUE);

                logout.setMinHeight(
                                48);

                logout.setPrefHeight(
                                48);

                logout.setAlignment(
                                Pos.CENTER_LEFT);

                logout.setPadding(
                                new Insets(
                                                0,
                                                14,
                                                0,
                                                14));

                logout.setStyle(
                                "-fx-background-color: transparent;" +
                                                "-fx-background-radius: 10px;" +
                                                "-fx-cursor: hand;");

                logout.setOnMouseEntered(event -> {

                        logout.setStyle(
                                        "-fx-background-color: #FFF3F3;" +
                                                        "-fx-background-radius: 10px;" +
                                                        "-fx-cursor: hand;");

                        icon.setStyle(
                                        "-fx-font-family: 'Segoe UI Symbol';" +
                                                        "-fx-font-size: 20px;" +
                                                        "-fx-font-weight: bold;" +
                                                        "-fx-text-fill: #D71920;");

                        text.setStyle(
                                        "-fx-font-family: 'Segoe UI';" +
                                                        "-fx-font-size: 14px;" +
                                                        "-fx-font-weight: bold;" +
                                                        "-fx-text-fill: #D71920;");
                });

                logout.setOnMouseExited(event -> {

                        logout.setStyle(
                                        "-fx-background-color: transparent;" +
                                                        "-fx-background-radius: 10px;" +
                                                        "-fx-cursor: hand;");

                        icon.setStyle(
                                        "-fx-font-family: 'Segoe UI Symbol';" +
                                                        "-fx-font-size: 20px;" +
                                                        "-fx-font-weight: bold;" +
                                                        "-fx-text-fill: " +
                                                        SECONDARY_TEXT + ";");

                        text.setStyle(
                                        "-fx-font-family: 'Segoe UI';" +
                                                        "-fx-font-size: 14px;" +
                                                        "-fx-text-fill: " +
                                                        SECONDARY_TEXT + ";");
                });

                return logout;
        }

        
        // SIDEBAR DIVIDER

        private Region createSidebarDivider() {

                Region divider = new Region();

                divider.setPrefHeight(
                                1);

                divider.setMaxWidth(
                                Double.MAX_VALUE);

                divider.setStyle(
                                "-fx-background-color: " +
                                                BORDER + ";");

                return divider;
        }

        // NOTIFICATION PAGE
        private VBox createNotificationPage() {

                VBox page = new VBox(10);

                page.setPadding(
                                new Insets(40));

                page.setStyle(
                                "-fx-background-color: " +
                                                BODY_BACKGROUND + ";");

                Label title = new Label(
                                "Notifications");

                title.setStyle(
                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 34px;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-text-fill: " +
                                                TEXT + ";");

                Label description = new Label(
                                "Emergency notifications and system alerts.");

                description.setStyle(
                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 16px;" +
                                                "-fx-text-fill: " +
                                                SECONDARY_TEXT + ";");

                page.getChildren().addAll(
                                title,
                                description);

                return page;
        }

}