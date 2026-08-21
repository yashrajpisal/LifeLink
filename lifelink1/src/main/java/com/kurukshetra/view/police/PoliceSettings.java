// package com.kurukshetra.view.police;

// import javafx.animation.FadeTransition;
// import javafx.animation.ParallelTransition;
// import javafx.animation.TranslateTransition;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.CheckBox;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;
// import javafx.util.Duration;

// public class PoliceSettings {

//     private static final String BG_SURFACE = "#faf8ff";
//     private static final String PRIMARY_COLOR = "#006591";
//     private static final String ON_SURFACE = "#131b2e";
//     private static final String ON_SURFACE_VARIANT = "#3e4850";
//     private static final String OUTLINE_VARIANT = "#bec8d2";
//     private static final String CARD_BG = "#ffffff";

//     public VBox getSettingsVBox() {

//         // MAIN SETTINGS AREA
//         VBox settingsPage = new VBox(25);
//         settingsPage.setPadding(new Insets(40, 50, 40, 50));
//         settingsPage.setStyle("-fx-background-color: " + BG_SURFACE + ";");

//         // HEADER
//         Text heading = new Text("Settings");
//         heading.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: " + ON_SURFACE + ";");

//         Text subHeading = new Text("Manage police control room preferences");
//         subHeading.setStyle("-fx-font-size: 15px; -fx-fill: " + ON_SURFACE_VARIANT + ";");

//         VBox headingBox = new VBox(7);
//         headingBox.getChildren().addAll(
//                 heading,
//                 subHeading
//         );

//         // GENERAL SETTINGS CARD
//         VBox generalCard = new VBox(18);
//         generalCard.setPadding(new Insets(25));
//         generalCard.setStyle("-fx-background-color: " + CARD_BG + "; -fx-background-radius: 18px; -fx-border-color: " + OUTLINE_VARIANT + "; -fx-border-radius: 18px;");

//         Text generalTitle = new Text("General Settings");
//         generalTitle.setStyle("-fx-font-size: 21px; -fx-font-weight: bold; -fx-fill: " + ON_SURFACE + ";");

//         // EMERGENCY NOTIFICATION
//         HBox notificationRow = new HBox(15);
//         notificationRow.setAlignment(Pos.CENTER_LEFT);
//         notificationRow.setPadding(new Insets(12));
//         notificationRow.setStyle("-fx-background-color: #f7f9ff; -fx-background-radius: 12px;");

//         VBox notificationInfo = new VBox(4);

//         Text notificationTitle = new Text("Emergency Notifications");
//         notificationTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + ON_SURFACE + ";");

//         Text notificationDescription = new Text("Receive alerts when an ambulance requests police assistance");
//         notificationDescription.setStyle("-fx-font-size: 13px; -fx-fill: " + ON_SURFACE_VARIANT + ";");

//         notificationInfo.getChildren().addAll(
//                 notificationTitle,
//                 notificationDescription
//         );

//         Region notificationSpacer = new Region();
//         HBox.setHgrow(notificationSpacer, Priority.ALWAYS);

//         CheckBox notificationCheck = new CheckBox();
//         notificationCheck.setSelected(true);

//         notificationRow.getChildren().addAll(
//                 notificationInfo,
//                 notificationSpacer,
//                 notificationCheck
//         );

//         // ROUTE CLEARANCE
//         HBox routeRow = new HBox(15);
//         routeRow.setAlignment(Pos.CENTER_LEFT);
//         routeRow.setPadding(new Insets(12));
//         routeRow.setStyle("-fx-background-color: #f7f9ff; -fx-background-radius: 12px;");

//         VBox routeInfo = new VBox(4);

//         Text routeTitle = new Text("Route Clearance Alerts");
//         routeTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + ON_SURFACE + ";");

//         Text routeDescription = new Text("Get notified when an ambulance requires route clearance");
//         routeDescription.setStyle("-fx-font-size: 13px; -fx-fill: " + ON_SURFACE_VARIANT + ";");

//         routeInfo.getChildren().addAll(
//                 routeTitle,
//                 routeDescription
//         );

//         Region routeSpacer = new Region();
//         HBox.setHgrow(routeSpacer, Priority.ALWAYS);

//         CheckBox routeCheck = new CheckBox();
//         routeCheck.setSelected(true);

//         routeRow.getChildren().addAll(
//                 routeInfo,
//                 routeSpacer,
//                 routeCheck
//         );

//         // TRAFFIC SIGNAL
//         HBox signalRow = new HBox(15);
//         signalRow.setAlignment(Pos.CENTER_LEFT);
//         signalRow.setPadding(new Insets(12));
//         signalRow.setStyle("-fx-background-color: #f7f9ff; -fx-background-radius: 12px;");

//         VBox signalInfo = new VBox(4);

//         Text signalTitle = new Text("Traffic Signal Coordination");
//         signalTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + ON_SURFACE + ";");

//         Text signalDescription = new Text("Receive alerts for emergency traffic signal coordination");
//         signalDescription.setStyle("-fx-font-size: 13px; -fx-fill: " + ON_SURFACE_VARIANT + ";");

//         signalInfo.getChildren().addAll(
//                 signalTitle,
//                 signalDescription
//         );

//         Region signalSpacer = new Region();
//         HBox.setHgrow(signalSpacer, Priority.ALWAYS);

//         CheckBox signalCheck = new CheckBox();
//         signalCheck.setSelected(true);

//         signalRow.getChildren().addAll(
//                 signalInfo,
//                 signalSpacer,
//                 signalCheck
//         );

//         generalCard.getChildren().addAll(
//                 generalTitle,
//                 notificationRow,
//                 routeRow,
//                 signalRow
//         );

//         // CONTROL ROOM CARD
//         VBox controlCard = new VBox(18);
//         controlCard.setPadding(new Insets(25));
//         controlCard.setStyle("-fx-background-color: " + CARD_BG + "; -fx-background-radius: 18px; -fx-border-color: " + OUTLINE_VARIANT + "; -fx-border-radius: 18px;");

//         Text controlTitle = new Text("Control Room");
//         controlTitle.setStyle("-fx-font-size: 21px; -fx-font-weight: bold; -fx-fill: " + ON_SURFACE + ";");

//         // AUTOMATIC REFRESH
//         HBox refreshRow = new HBox(15);
//         refreshRow.setAlignment(Pos.CENTER_LEFT);
//         refreshRow.setPadding(new Insets(12));
//         refreshRow.setStyle("-fx-background-color: #f7f9ff; -fx-background-radius: 12px;");

//         VBox refreshInfo = new VBox(4);

//         Text refreshTitle = new Text("Automatic Data Refresh");
//         refreshTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + ON_SURFACE + ";");

//         Text refreshDescription = new Text("Automatically update ambulance and traffic information");
//         refreshDescription.setStyle("-fx-font-size: 13px; -fx-fill: " + ON_SURFACE_VARIANT + ";");

//         refreshInfo.getChildren().addAll(
//                 refreshTitle,
//                 refreshDescription
//         );

//         Region refreshSpacer = new Region();
//         HBox.setHgrow(refreshSpacer, Priority.ALWAYS);

//         CheckBox refreshCheck = new CheckBox();
//         refreshCheck.setSelected(true);

//         refreshRow.getChildren().addAll(
//                 refreshInfo,
//                 refreshSpacer,
//                 refreshCheck
//         );

//         // SOUND ALERT
//         HBox soundRow = new HBox(15);
//         soundRow.setAlignment(Pos.CENTER_LEFT);
//         soundRow.setPadding(new Insets(12));
//         soundRow.setStyle("-fx-background-color: #f7f9ff; -fx-background-radius: 12px;");

//         VBox soundInfo = new VBox(4);

//         Text soundTitle = new Text("Emergency Sound Alert");
//         soundTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + ON_SURFACE + ";");

//         Text soundDescription = new Text("Play a sound when a new emergency request arrives");
//         soundDescription.setStyle("-fx-font-size: 13px; -fx-fill: " + ON_SURFACE_VARIANT + ";");

//         soundInfo.getChildren().addAll(
//                 soundTitle,
//                 soundDescription
//         );

//         Region soundSpacer = new Region();
//         HBox.setHgrow(soundSpacer, Priority.ALWAYS);

//         CheckBox soundCheck = new CheckBox();
//         soundCheck.setSelected(true);

//         soundRow.getChildren().addAll(
//                 soundInfo,
//                 soundSpacer,
//                 soundCheck
//         );

//         controlCard.getChildren().addAll(
//                 controlTitle,
//                 refreshRow,
//                 soundRow
//         );

//         // SAVE BUTTON
//         HBox buttonBox = new HBox();
//         buttonBox.setAlignment(Pos.CENTER_RIGHT);

//         Button saveButton = new Button("Save Changes");
//         saveButton.setPrefWidth(150);
//         saveButton.setPrefHeight(45);
//         saveButton.setStyle("-fx-background-color: " + PRIMARY_COLOR + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 12px;");

//         buttonBox.getChildren().add(saveButton);

//         // ADD EVERYTHING
//         settingsPage.getChildren().addAll(
//                 headingBox,
//                 generalCard,
//                 controlCard,
//                 buttonBox
//         );

//         // ENTRANCE ANIMATION
//         int delay = 0;

//         for (javafx.scene.Node child : settingsPage.getChildren()) {

//             child.setOpacity(0);
//             child.setTranslateY(25);

//             FadeTransition fade = new FadeTransition(Duration.millis(500), child);
//             fade.setToValue(1.0);

//             TranslateTransition translate = new TranslateTransition(Duration.millis(500), child);
//             translate.setToY(0);

//             ParallelTransition animation = new ParallelTransition(fade, translate);
//             animation.setDelay(Duration.millis(delay));
//             animation.play();

//             delay += 100;
//         }

//         return settingsPage;
//     }
// }
package com.kurukshetra.view.police;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PoliceSettings {

    // --- FAINT DARK-BROWN / WARM BRONZE COLOR PALETTE ---
    private static final String PAGE_BG = "#F7F3EF";
    private static final String SURFACE = "#FFFFFF";
    private static final String VERY_LIGHT_BEIGE = "#F8F4F0";
    private static final String BROWN_DARK = "#694d3b";
    private static final String WARM_BRONZE = "#8d6338";
    private static final String PRIMARY_TEXT = "#29231F";
    private static final String SEC_TEXT = "#635951";
    private static final String BORDER = "#E5DBD2";

    public ScrollPane getSettingsView() {

        // MAIN SETTINGS AREA
        VBox settingsPage = new VBox(25);
        settingsPage.setPadding(new Insets(40, 50, 40, 50));
        settingsPage.setStyle("-fx-background-color: " + PAGE_BG + ";");

        // HEADER
        Text heading = new Text("Settings");
        heading.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text subHeading = new Text("Manage police control room preferences");
        subHeading.setStyle("-fx-font-size: 15px; -fx-fill: " + SEC_TEXT + ";");

        VBox headingBox = new VBox(7);
        headingBox.getChildren().addAll(
                heading,
                subHeading
        );

        // GENERAL SETTINGS CARD
        VBox generalCard = new VBox(18);
        generalCard.setPadding(new Insets(25));
        generalCard.setStyle("-fx-background-color: " + SURFACE + "; -fx-background-radius: 18px; -fx-border-color: " + BORDER + "; -fx-border-radius: 18px;");

        Text generalTitle = new Text("General Settings");
        generalTitle.setStyle("-fx-font-size: 21px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        // EMERGENCY NOTIFICATION
        HBox notificationRow = new HBox(15);
        notificationRow.setAlignment(Pos.CENTER_LEFT);
        notificationRow.setPadding(new Insets(12));
        notificationRow.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 12px;");

        VBox notificationInfo = new VBox(4);

        Text notificationTitle = new Text("Emergency Notifications");
        notificationTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text notificationDescription = new Text("Receive alerts when an ambulance requests police assistance");
        notificationDescription.setStyle("-fx-font-size: 13px; -fx-fill: " + SEC_TEXT + ";");

        notificationInfo.getChildren().addAll(
                notificationTitle,
                notificationDescription
        );

        Region notificationSpacer = new Region();
        HBox.setHgrow(notificationSpacer, Priority.ALWAYS);

        CheckBox notificationCheck = new CheckBox();
        notificationCheck.setSelected(true);

        notificationRow.getChildren().addAll(
                notificationInfo,
                notificationSpacer,
                notificationCheck
        );

        // ROUTE CLEARANCE
        HBox routeRow = new HBox(15);
        routeRow.setAlignment(Pos.CENTER_LEFT);
        routeRow.setPadding(new Insets(12));
        routeRow.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 12px;");

        VBox routeInfo = new VBox(4);

        Text routeTitle = new Text("Route Clearance Alerts");
        routeTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text routeDescription = new Text("Get notified when an ambulance requires route clearance");
        routeDescription.setStyle("-fx-font-size: 13px; -fx-fill: " + SEC_TEXT + ";");

        routeInfo.getChildren().addAll(
                routeTitle,
                routeDescription
        );

        Region routeSpacer = new Region();
        HBox.setHgrow(routeSpacer, Priority.ALWAYS);

        CheckBox routeCheck = new CheckBox();
        routeCheck.setSelected(true);

        routeRow.getChildren().addAll(
                routeInfo,
                routeSpacer,
                routeCheck
        );

        // TRAFFIC SIGNAL
        HBox signalRow = new HBox(15);
        signalRow.setAlignment(Pos.CENTER_LEFT);
        signalRow.setPadding(new Insets(12));
        signalRow.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 12px;");

        VBox signalInfo = new VBox(4);

        Text signalTitle = new Text("Traffic Signal Coordination");
        signalTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text signalDescription = new Text("Receive alerts for emergency traffic signal coordination");
        signalDescription.setStyle("-fx-font-size: 13px; -fx-fill: " + SEC_TEXT + ";");

        signalInfo.getChildren().addAll(
                signalTitle,
                signalDescription
        );

        Region signalSpacer = new Region();
        HBox.setHgrow(signalSpacer, Priority.ALWAYS);

        CheckBox signalCheck = new CheckBox();
        signalCheck.setSelected(true);

        signalRow.getChildren().addAll(
                signalInfo,
                signalSpacer,
                signalCheck
        );

        generalCard.getChildren().addAll(
                generalTitle,
                notificationRow,
                routeRow,
                signalRow
        );

        // CONTROL ROOM CARD
        VBox controlCard = new VBox(18);
        controlCard.setPadding(new Insets(25));
        controlCard.setStyle("-fx-background-color: " + SURFACE + "; -fx-background-radius: 18px; -fx-border-color: " + BORDER + "; -fx-border-radius: 18px;");

        Text controlTitle = new Text("Control Room");
        controlTitle.setStyle("-fx-font-size: 21px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        // AUTOMATIC REFRESH
        HBox refreshRow = new HBox(15);
        refreshRow.setAlignment(Pos.CENTER_LEFT);
        refreshRow.setPadding(new Insets(12));
        refreshRow.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 12px;");

        VBox refreshInfo = new VBox(4);

        Text refreshTitle = new Text("Automatic Data Refresh");
        refreshTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text refreshDescription = new Text("Automatically update ambulance and traffic information");
        refreshDescription.setStyle("-fx-font-size: 13px; -fx-fill: " + SEC_TEXT + ";");

        refreshInfo.getChildren().addAll(
                refreshTitle,
                refreshDescription
        );

        Region refreshSpacer = new Region();
        HBox.setHgrow(refreshSpacer, Priority.ALWAYS);

        CheckBox refreshCheck = new CheckBox();
        refreshCheck.setSelected(true);

        refreshRow.getChildren().addAll(
                refreshInfo,
                refreshSpacer,
                refreshCheck
        );

        // SOUND ALERT
        HBox soundRow = new HBox(15);
        soundRow.setAlignment(Pos.CENTER_LEFT);
        soundRow.setPadding(new Insets(12));
        soundRow.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 12px;");

        VBox soundInfo = new VBox(4);

        Text soundTitle = new Text("Emergency Sound Alert");
        soundTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

        Text soundDescription = new Text("Play a sound when a new emergency request arrives");
        soundDescription.setStyle("-fx-font-size: 13px; -fx-fill: " + SEC_TEXT + ";");

        soundInfo.getChildren().addAll(
                soundTitle,
                soundDescription
        );

        Region soundSpacer = new Region();
        HBox.setHgrow(soundSpacer, Priority.ALWAYS);

        CheckBox soundCheck = new CheckBox();
        soundCheck.setSelected(true);

        soundRow.getChildren().addAll(
                soundInfo,
                soundSpacer,
                soundCheck
        );

        controlCard.getChildren().addAll(
                controlTitle,
                refreshRow,
                soundRow
        );

        // SAVE BUTTON
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        Button saveButton = new Button("Save Changes");
        saveButton.setPrefWidth(150);
        saveButton.setPrefHeight(45);
        saveButton.setStyle("-fx-background-color: " + WARM_BRONZE + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-cursor: hand;");
        addHoverEffect(saveButton);

        buttonBox.getChildren().add(saveButton);

        // ADD EVERYTHING
        settingsPage.getChildren().addAll(
                headingBox,
                generalCard,
                controlCard,
                buttonBox
        );

        // ENTRANCE ANIMATION
        int delay = 0;

        for (javafx.scene.Node child : settingsPage.getChildren()) {

            child.setOpacity(0);
            child.setTranslateY(25);

            FadeTransition fade = new FadeTransition(Duration.millis(500), child);
            fade.setToValue(1.0);

            TranslateTransition translate = new TranslateTransition(Duration.millis(500), child);
            translate.setToY(0);

            ParallelTransition animation = new ParallelTransition(fade, translate);
            animation.setDelay(Duration.millis(delay));
            animation.play();

            delay += 100;
        }

        ScrollPane scrollPane = new ScrollPane(settingsPage);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");

        return scrollPane;
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + BROWN_DARK + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + WARM_BRONZE + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 12px; -fx-cursor: hand;"));
    }
}