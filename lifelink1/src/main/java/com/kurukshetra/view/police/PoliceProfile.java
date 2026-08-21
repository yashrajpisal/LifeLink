package com.kurukshetra.view.police;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class PoliceProfile {

    // --- FAINT DARK-BROWN / WARM BRONZE COLOR PALETTE ---
    private static final String PAGE_BG = "#F7F3EF";
    private static final String SURFACE = "#FFFFFF";
    private static final String VERY_LIGHT_BEIGE = "#F8F4F0";
    private static final String BROWN_DARK = "#694d3b";
    private static final String WARM_BRONZE = "#8d6338";
    private static final String PRIMARY_TEXT = "#29231F";
    private static final String SEC_TEXT = "#635951";
    private static final String BORDER = "#E5DBD2";

    // Track editable rows
    private final List<EditableInfoRow> editableRows = new ArrayList<>();

    public ScrollPane getProfileView() {

        // MAIN PROFILE AREA
        VBox profilePage = new VBox(25);
        profilePage.setPadding(new Insets(40, 50, 40, 50));
        profilePage.setStyle("-fx-background-color: " + PAGE_BG + ";");

        // HEADER TEXTS
        Text heading = new Text("Control Room Profile");
        heading.setStyle(
                "-fx-font-size: 30px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + PRIMARY_TEXT + ";"
        );

        Text subHeading = new Text("Police control room information");
        subHeading.setStyle(
                "-fx-font-size: 15px; " +
                "-fx-fill: " + SEC_TEXT + ";"
        );

        VBox headingTextBox = new VBox(7);
        headingTextBox.getChildren().addAll(heading, subHeading);

        // BUTTONS
        Button editBtn = new Button("Edit Profile");
        editBtn.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-text-fill: " + BROWN_DARK + "; " +
                "-fx-border-color: " + BROWN_DARK + "; " +
                "-fx-border-radius: 8px; " +
                "-fx-background-radius: 8px; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 8px 18px; " +
                "-fx-cursor: hand;"
        );

        Button saveBtn = new Button("Save Profile");
        saveBtn.setStyle(
                "-fx-background-color: " + BROWN_DARK + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-border-radius: 8px; " +
                "-fx-background-radius: 8px; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 8px 18px; " +
                "-fx-cursor: hand;"
        );
        saveBtn.setVisible(false);
        saveBtn.setManaged(false);

        // BUTTON ACTIONS
        editBtn.setOnAction(e -> {
            toggleEditMode(true);
            editBtn.setVisible(false);
            editBtn.setManaged(false);
            saveBtn.setVisible(true);
            saveBtn.setManaged(true);
        });

        saveBtn.setOnAction(e -> {
            toggleEditMode(false);
            saveBtn.setVisible(false);
            saveBtn.setManaged(false);
            editBtn.setVisible(true);
            editBtn.setManaged(true);
        });

        HBox actionBtnBox = new HBox(12, editBtn, saveBtn);
        actionBtnBox.setAlignment(Pos.CENTER_RIGHT);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox headerRow = new HBox(headingTextBox, headerSpacer, actionBtnBox);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        // CONTROL ROOM CARD
        VBox controlRoomCard = new VBox(18);
        controlRoomCard.setPadding(new Insets(25));
        controlRoomCard.setStyle(
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-background-radius: 18px; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-radius: 18px;"
        );

        Text controlRoomTitle = new Text("Control Room Information");
        controlRoomTitle.setStyle(
                "-fx-font-size: 21px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + PRIMARY_TEXT + ";"
        );

        HBox roomNameRow = createInfoRow("Control Room", "Pune City Emergency Control Room");
        HBox locationRow = createInfoRow("Location", "Pune Police Commissionerate, Shivajinagar, Pune");
        HBox districtRow = createInfoRow("Police District", "Pune City");
        HBox statusRow = createInfoRow("Status", "Active");

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
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-background-radius: 18px; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-radius: 18px;"
        );

        Text officerTitle = new Text("Control Room In-charge");
        officerTitle.setStyle(
                "-fx-font-size: 21px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + PRIMARY_TEXT + ";"
        );

        HBox officerNameRow = createInfoRow("Officer", "Control Room In-charge");
        HBox officerRoleRow = createInfoRow("Role", "Emergency Coordination Officer");
        HBox contactRow = createInfoRow("Contact", "Police Control Room Emergency Line");

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
                "-fx-background-color: " + SURFACE + "; " +
                "-fx-background-radius: 18px; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-radius: 18px;"
        );

        Text servicesTitle = new Text("Services Coordinated");
        servicesTitle.setStyle(
                "-fx-font-size: 21px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + PRIMARY_TEXT + ";"
        );

        HBox service1 = createServiceRow("Ambulance Route Clearance");
        HBox service2 = createServiceRow("Traffic Signal Coordination");
        HBox service3 = createServiceRow("Emergency Route Monitoring");
        HBox service4 = createServiceRow("Ambulance Arrival Coordination");

        servicesCard.getChildren().addAll(
                servicesTitle,
                service1,
                service2,
                service3,
                service4
        );

        // ADD EVERYTHING
        profilePage.getChildren().addAll(
                headerRow,
                controlRoomCard,
                officerCard,
                servicesCard
        );

        // ENTRANCE ANIMATION
        playEntranceAnimation(profilePage);

        ScrollPane scrollPane = new ScrollPane(profilePage);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");

        return scrollPane;
    }

    private void toggleEditMode(boolean isEditing) {
        for (EditableInfoRow row : editableRows) {
            row.setEditing(isEditing);
        }
    }

    private HBox createInfoRow(String title, String initialValue) {

        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(6, 10, 6, 10));

        Text titleText = new Text(title);
        titleText.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + SEC_TEXT + ";"
        );

        Text valueText = new Text(initialValue);
        valueText.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-fill: " + PRIMARY_TEXT + ";"
        );

        TextField valueField = new TextField(initialValue);
        valueField.setStyle(
                "-fx-background-color: " + VERY_LIGHT_BEIGE + "; " +
                "-fx-border-color: " + BORDER + "; " +
                "-fx-border-radius: 6px; " +
                "-fx-background-radius: 6px; " +
                "-fx-text-fill: " + PRIMARY_TEXT + "; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 6px 10px;"
        );
        valueField.setPrefWidth(300);
        valueField.setVisible(false);
        valueField.setManaged(false);

        StackPane displayStack = new StackPane(valueText, valueField);
        displayStack.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row.getChildren().addAll(titleText, spacer, displayStack);

        editableRows.add(new EditableInfoRow(valueText, valueField));

        return row;
    }

    private HBox createServiceRow(String service) {

        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12));
        row.setStyle(
                "-fx-background-color: " + VERY_LIGHT_BEIGE + "; " +
                "-fx-background-radius: 10px;"
        );

        Text bullet = new Text("●");
        bullet.setStyle(
                "-fx-font-size: 12px; " +
                "-fx-fill: " + WARM_BRONZE + ";"
        );

        Text serviceText = new Text(service);
        serviceText.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-fill: " + PRIMARY_TEXT + ";"
        );

        row.getChildren().addAll(bullet, serviceText);
        return row;
    }

    private void playEntranceAnimation(VBox profilePage) {

        int delay = 0;

        for (Node child : profilePage.getChildren()) {
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
    }

    // Helper holder class for UI state switching
    private static class EditableInfoRow {
        private final Text textNode;
        private final TextField fieldNode;

        public EditableInfoRow(Text textNode, TextField fieldNode) {
            this.textNode = textNode;
            this.fieldNode = fieldNode;
        }

        public void setEditing(boolean editing) {
            if (editing) {
                fieldNode.setText(textNode.getText());
                textNode.setVisible(false);
                textNode.setManaged(false);
                fieldNode.setVisible(true);
                fieldNode.setManaged(true);
            } else {
                textNode.setText(fieldNode.getText());
                fieldNode.setVisible(false);
                fieldNode.setManaged(false);
                textNode.setVisible(true);
                textNode.setManaged(true);
            }
        }
    }
}
// package com.kurukshetra.view.police;

// import javafx.animation.FadeTransition;
// import javafx.animation.ParallelTransition;
// import javafx.animation.TranslateTransition;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Node;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;
// import javafx.util.Duration;

// public class PoliceProfile {

//     // --- FAINT DARK-BROWN / WARM BRONZE COLOR PALETTE ---
//     private static final String PAGE_BG = "#F7F3EF";
//     private static final String SURFACE = "#FFFFFF";
//     private static final String VERY_LIGHT_BEIGE = "#F8F4F0";
//     private static final String BROWN_DARK = "#694d3b";
//     private static final String WARM_BRONZE = "#8d6338";
//     private static final String PRIMARY_TEXT = "#29231F";
//     private static final String SEC_TEXT = "#635951";
//     private static final String BORDER = "#E5DBD2";

//     public ScrollPane getProfileView() {

//         // MAIN PROFILE AREA
//         VBox profilePage = new VBox(25);
//         profilePage.setPadding(new Insets(40, 50, 40, 50));
//         profilePage.setStyle("-fx-background-color: " + PAGE_BG + ";");

//         // HEADER
//         Text heading = new Text("Control Room Profile");
//         heading.setStyle(
//                 "-fx-font-size: 30px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-fill: " + PRIMARY_TEXT + ";"
//         );

//         Text subHeading = new Text("Police control room information");
//         subHeading.setStyle(
//                 "-fx-font-size: 15px; " +
//                 "-fx-fill: " + SEC_TEXT + ";"
//         );

//         VBox headingBox = new VBox(7);
//         headingBox.getChildren().addAll(
//                 heading,
//                 subHeading
//         );

//         // CONTROL ROOM CARD
//         VBox controlRoomCard = new VBox(18);
//         controlRoomCard.setPadding(new Insets(25));
//         controlRoomCard.setStyle(
//                 "-fx-background-color: " + SURFACE + "; " +
//                 "-fx-background-radius: 18px; " +
//                 "-fx-border-color: " + BORDER + "; " +
//                 "-fx-border-radius: 18px;"
//         );

//         Text controlRoomTitle = new Text("Control Room Information");
//         controlRoomTitle.setStyle(
//                 "-fx-font-size: 21px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-fill: " + PRIMARY_TEXT + ";"
//         );

//         HBox roomNameRow = createInfoRow(
//                 "Control Room",
//                 "Pune City Emergency Control Room"
//         );

//         HBox locationRow = createInfoRow(
//                 "Location",
//                 "Pune Police Commissionerate, Shivajinagar, Pune"
//         );

//         HBox districtRow = createInfoRow(
//                 "Police District",
//                 "Pune City"
//         );

//         HBox statusRow = createInfoRow(
//                 "Status",
//                 "Active"
//         );

//         controlRoomCard.getChildren().addAll(
//                 controlRoomTitle,
//                 roomNameRow,
//                 locationRow,
//                 districtRow,
//                 statusRow
//         );

//         // OFFICER CARD
//         VBox officerCard = new VBox(18);
//         officerCard.setPadding(new Insets(25));
//         officerCard.setStyle(
//                 "-fx-background-color: " + SURFACE + "; " +
//                 "-fx-background-radius: 18px; " +
//                 "-fx-border-color: " + BORDER + "; " +
//                 "-fx-border-radius: 18px;"
//         );

//         Text officerTitle = new Text("Control Room In-charge");
//         officerTitle.setStyle(
//                 "-fx-font-size: 21px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-fill: " + PRIMARY_TEXT + ";"
//         );

//         HBox officerNameRow = createInfoRow(
//                 "Officer",
//                 "Control Room In-charge"
//         );

//         HBox officerRoleRow = createInfoRow(
//                 "Role",
//                 "Emergency Coordination Officer"
//         );

//         HBox contactRow = createInfoRow(
//                 "Contact",
//                 "Police Control Room Emergency Line"
//         );

//         officerCard.getChildren().addAll(
//                 officerTitle,
//                 officerNameRow,
//                 officerRoleRow,
//                 contactRow
//         );

//         // SERVICES CARD
//         VBox servicesCard = new VBox(18);
//         servicesCard.setPadding(new Insets(25));
//         servicesCard.setStyle(
//                 "-fx-background-color: " + SURFACE + "; " +
//                 "-fx-background-radius: 18px; " +
//                 "-fx-border-color: " + BORDER + "; " +
//                 "-fx-border-radius: 18px;"
//         );

//         Text servicesTitle = new Text("Services Coordinated");
//         servicesTitle.setStyle(
//                 "-fx-font-size: 21px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-fill: " + PRIMARY_TEXT + ";"
//         );

//         HBox service1 = createServiceRow(
//                 "Ambulance Route Clearance"
//         );

//         HBox service2 = createServiceRow(
//                 "Traffic Signal Coordination"
//         );

//         HBox service3 = createServiceRow(
//                 "Emergency Route Monitoring"
//         );

//         HBox service4 = createServiceRow(
//                 "Ambulance Arrival Coordination"
//         );

//         servicesCard.getChildren().addAll(
//                 servicesTitle,
//                 service1,
//                 service2,
//                 service3,
//                 service4
//         );

//         // ADD EVERYTHING
//         profilePage.getChildren().addAll(
//                 headingBox,
//                 controlRoomCard,
//                 officerCard,
//                 servicesCard
//         );

//         // ENTRANCE ANIMATION
//         playEntranceAnimation(profilePage);

//         ScrollPane scrollPane = new ScrollPane(profilePage);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//         scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");

//         return scrollPane;
//     }

//     private HBox createInfoRow(String title, String value) {

//         HBox row = new HBox(20);
//         row.setAlignment(Pos.CENTER_LEFT);
//         row.setPadding(new Insets(10));

//         Text titleText = new Text(title);
//         titleText.setStyle(
//                 "-fx-font-size: 14px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-fill: " + SEC_TEXT + ";"
//         );

//         Text valueText = new Text(value);
//         valueText.setStyle(
//                 "-fx-font-size: 14px; " +
//                 "-fx-fill: " + PRIMARY_TEXT + ";"
//         );

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         row.getChildren().addAll(
//                 titleText,
//                 spacer,
//                 valueText
//         );

//         return row;
//     }

//     private HBox createServiceRow(String service) {

//         HBox row = new HBox(12);
//         row.setAlignment(Pos.CENTER_LEFT);
//         row.setPadding(new Insets(12));

//         row.setStyle(
//                 "-fx-background-color: " + VERY_LIGHT_BEIGE + "; " +
//                 "-fx-background-radius: 10px;"
//         );

//         Text bullet = new Text("●");
//         bullet.setStyle(
//                 "-fx-font-size: 12px; " +
//                 "-fx-fill: " + WARM_BRONZE + ";"
//         );

//         Text serviceText = new Text(service);
//         serviceText.setStyle(
//                 "-fx-font-size: 14px; " +
//                 "-fx-fill: " + PRIMARY_TEXT + ";"
//         );

//         row.getChildren().addAll(
//                 bullet,
//                 serviceText
//         );

//         return row;
//     }

//     private void playEntranceAnimation(VBox profilePage) {

//         int delay = 0;

//         for (Node child : profilePage.getChildren()) {

//             child.setOpacity(0);
//             child.setTranslateY(25);

//             FadeTransition fade = new FadeTransition(
//                     Duration.millis(500),
//                     child
//             );

//             fade.setToValue(1.0);

//             TranslateTransition translate = new TranslateTransition(
//                     Duration.millis(500),
//                     child
//             );

//             translate.setToY(0);

//             ParallelTransition animation = new ParallelTransition(
//                     fade,
//                     translate
//             );

//             animation.setDelay(Duration.millis(delay));
//             animation.play();

//             delay += 100;
//         }
//     }
// }