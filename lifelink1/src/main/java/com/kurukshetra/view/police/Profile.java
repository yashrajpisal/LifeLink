package com.kurukshetra.view.police;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Profile {

    private static final String BG_SURFACE = "#faf8ff";
    private static final String PRIMARY_COLOR = "#006591";
    private static final String ON_SURFACE = "#131b2e";
    private static final String ON_SURFACE_VARIANT = "#3e4850";
    private static final String OUTLINE_VARIANT = "#bec8d2";
    private static final String CARD_BG = "#ffffff";

    public VBox getProfileVBox() {

        // MAIN PROFILE AREA
        VBox profilePage = new VBox(25);
        profilePage.setPadding(new Insets(40, 50, 40, 50));
        profilePage.setStyle("-fx-background-color: " + BG_SURFACE + ";");

        // HEADER
        Text heading = new Text("Control Room Profile");
        heading.setStyle(
                "-fx-font-size: 30px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + ON_SURFACE + ";"
        );

        Text subHeading = new Text("Police control room information");
        subHeading.setStyle(
                "-fx-font-size: 15px; " +
                "-fx-fill: " + ON_SURFACE_VARIANT + ";"
        );

        VBox headingBox = new VBox(7);
        headingBox.getChildren().addAll(
                heading,
                subHeading
        );

        // CONTROL ROOM CARD
        VBox controlRoomCard = new VBox(18);
        controlRoomCard.setPadding(new Insets(25));
        controlRoomCard.setStyle(
                "-fx-background-color: " + CARD_BG + "; " +
                "-fx-background-radius: 18px; " +
                "-fx-border-color: " + OUTLINE_VARIANT + "; " +
                "-fx-border-radius: 18px;"
        );

        Text controlRoomTitle = new Text("Control Room Information");
        controlRoomTitle.setStyle(
                "-fx-font-size: 21px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + ON_SURFACE + ";"
        );

        HBox roomNameRow = createInfoRow(
                "Control Room",
                "Pune City Emergency Control Room"
        );

        HBox locationRow = createInfoRow(
                "Location",
                "Pune Police Commissionerate, Shivajinagar, Pune"
        );

        HBox districtRow = createInfoRow(
                "Police District",
                "Pune City"
        );

        HBox statusRow = createInfoRow(
                "Status",
                "Active"
        );

        controlRoomCard.getChildren().addAll(
                controlRoomTitle,
                roomNameRow,
                locationRow,
                districtRow,
                statusRow
        );

        // OFFICER CARD
        VBox officerCard = new VBox(18);
        officerCard.setPadding(new Insets(25));
        officerCard.setStyle(
                "-fx-background-color: " + CARD_BG + "; " +
                "-fx-background-radius: 18px; " +
                "-fx-border-color: " + OUTLINE_VARIANT + "; " +
                "-fx-border-radius: 18px;"
        );

        Text officerTitle = new Text("Control Room In-charge");
        officerTitle.setStyle(
                "-fx-font-size: 21px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + ON_SURFACE + ";"
        );

        HBox officerNameRow = createInfoRow(
                "Officer",
                "Control Room In-charge"
        );

        HBox officerRoleRow = createInfoRow(
                "Role",
                "Emergency Coordination Officer"
        );

        HBox contactRow = createInfoRow(
                "Contact",
                "Police Control Room Emergency Line"
        );

        officerCard.getChildren().addAll(
                officerTitle,
                officerNameRow,
                officerRoleRow,
                contactRow
        );

        // SERVICES CARD
        VBox servicesCard = new VBox(18);
        servicesCard.setPadding(new Insets(25));
        servicesCard.setStyle(
                "-fx-background-color: " + CARD_BG + "; " +
                "-fx-background-radius: 18px; " +
                "-fx-border-color: " + OUTLINE_VARIANT + "; " +
                "-fx-border-radius: 18px;"
        );

        Text servicesTitle = new Text("Services Coordinated");
        servicesTitle.setStyle(
                "-fx-font-size: 21px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + ON_SURFACE + ";"
        );

        HBox service1 = createServiceRow(
                "Ambulance Route Clearance"
        );

        HBox service2 = createServiceRow(
                "Traffic Signal Coordination"
        );

        HBox service3 = createServiceRow(
                "Emergency Route Monitoring"
        );

        HBox service4 = createServiceRow(
                "Ambulance Arrival Coordination"
        );

        servicesCard.getChildren().addAll(
                servicesTitle,
                service1,
                service2,
                service3,
                service4
        );

        // ADD EVERYTHING
        profilePage.getChildren().addAll(
                headingBox,
                controlRoomCard,
                officerCard,
                servicesCard
        );

        // ENTRANCE ANIMATION
        playEntranceAnimation(profilePage);

        return profilePage;
    }

    private HBox createInfoRow(String title, String value) {

        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10));

        Text titleText = new Text(title);
        titleText.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + ON_SURFACE_VARIANT + ";"
        );

        Text valueText = new Text(value);
        valueText.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-fill: " + ON_SURFACE + ";"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row.getChildren().addAll(
                titleText,
                spacer,
                valueText
        );

        return row;
    }

    private HBox createServiceRow(String service) {

        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12));

        row.setStyle(
                "-fx-background-color: #f7f9ff; " +
                "-fx-background-radius: 10px;"
        );

        Text bullet = new Text("●");
        bullet.setStyle(
                "-fx-font-size: 12px; " +
                "-fx-fill: " + PRIMARY_COLOR + ";"
        );

        Text serviceText = new Text(service);
        serviceText.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-fill: " + ON_SURFACE + ";"
        );

        row.getChildren().addAll(
                bullet,
                serviceText
        );

        return row;
    }

    private void playEntranceAnimation(VBox profilePage) {

        int delay = 0;

        for (Node child : profilePage.getChildren()) {

            child.setOpacity(0);
            child.setTranslateY(25);

            FadeTransition fade = new FadeTransition(
                    Duration.millis(500),
                    child
            );

            fade.setToValue(1.0);

            TranslateTransition translate = new TranslateTransition(
                    Duration.millis(500),
                    child
            );

            translate.setToY(0);

            ParallelTransition animation = new ParallelTransition(
                    fade,
                    translate
            );

            animation.setDelay(Duration.millis(delay));
            animation.play();

            delay += 100;
        }
    }
}