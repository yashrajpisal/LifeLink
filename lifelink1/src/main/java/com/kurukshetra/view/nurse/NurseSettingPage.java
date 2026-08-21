// package com.kurukshetra.view.nurse;

// import javafx.animation.FadeTransition;
// import javafx.animation.ParallelTransition;
// import javafx.animation.TranslateTransition;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.RadioButton;
// import javafx.scene.control.ToggleGroup;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Circle;
// import javafx.scene.shape.Rectangle;
// import javafx.scene.text.Text;

// public class NurseSettingPage {

//     public BorderPane settingsRoot;

//     public BorderPane getAppSettingsPage(Runnable callBackDashboard) {

//         settingsRoot = new BorderPane();
//         settingsRoot.setStyle("-fx-background-color : #F7F9FC; -fx-font-family : 'Segoe UI';");

//         // ================= TOP BAR =================

//         HBox topBar = new HBox(14);
//         topBar.setAlignment(Pos.CENTER_LEFT);
//         topBar.setPadding(new Insets(18, 30, 6, 30));

//         Button backButton = new Button("‹");
//         backButton.setPrefWidth(40);
//         backButton.setPrefHeight(40);
//         backButton.setStyle("-fx-background-color : #EAF7FD; -fx-text-fill : #08A1E5; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;");

//         backButton.setOnAction(e -> {
//             System.out.println("Back to Dashboard");
//             callBackDashboard.run();
//         });

//         backButton.setOnMouseEntered(e ->
//                 backButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;")
//         );

//         backButton.setOnMouseExited(e ->
//                 backButton.setStyle("-fx-background-color : #EAF7FD; -fx-text-fill : #08A1E5; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;")
//         );

//         VBox heading = new VBox(3);

//         Text pageTitle = new Text("Settings");
//         pageTitle.setStyle("-fx-font-size : 24px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Text pageSubtitle = new Text("Manage your basic account and preferences.");
//         pageSubtitle.setStyle("-fx-font-size : 11px; -fx-fill : #728096;");

//         heading.getChildren().addAll(pageTitle, pageSubtitle);

//         topBar.getChildren().addAll(backButton, heading);

//         // ================= MAIN CONTENT =================

//         VBox mainContent = new VBox(16);
//         mainContent.setPadding(new Insets(10, 50, 20, 50));

//         // ================= PROFILE INFORMATION CARD =================

//         VBox profileCard = new VBox(14);
//         profileCard.setPadding(new Insets(18, 20, 18, 20));
//         profileCard.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 16; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.08), 10, 0, 0, 3);");

//         Text profileCardTitle = new Text("Profile Information");
//         profileCardTitle.setStyle("-fx-font-size : 13px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         HBox profileRow = new HBox(16);
//         profileRow.setAlignment(Pos.CENTER_LEFT);

//         Circle avatarCircle = new Circle(28);
//         avatarCircle.setFill(Color.web("#08A1E5"));
//         avatarCircle.setStroke(Color.WHITE);
//         avatarCircle.setStrokeWidth(3);

//         Text avatarLetter = new Text("S");
//         avatarLetter.setStyle("-fx-font-size : 20px; -fx-font-weight : bold; -fx-fill : white;");

//         StackPane avatar = new StackPane(avatarCircle, avatarLetter);

//         VBox profileDetails = new VBox(4);

//         Text profileName = new Text("Dr. Sarah Smith");
//         profileName.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Text profileRole = new Text("Head of Emergency");
//         profileRole.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : #08A1E5;");

//         HBox emailRow = new HBox(8);
//         emailRow.setAlignment(Pos.CENTER_LEFT);
//         Text emailIcon = new Text("✉");
//         emailIcon.setStyle("-fx-font-size : 11px; -fx-fill : #728096;");
//         Text emailText = new Text("sarah.smith@lifelink.med");
//         emailText.setStyle("-fx-font-size : 11px; -fx-fill : #536277;");
//         emailRow.getChildren().addAll(emailIcon, emailText);

//         HBox phoneRow = new HBox(8);
//         phoneRow.setAlignment(Pos.CENTER_LEFT);
//         Text phoneIcon = new Text("☎");
//         phoneIcon.setStyle("-fx-font-size : 11px; -fx-fill : #728096;");
//         Text phoneText = new Text("+1 (555) 019-2834");
//         phoneText.setStyle("-fx-font-size : 11px; -fx-fill : #536277;");
//         phoneRow.getChildren().addAll(phoneIcon, phoneText);

//         profileDetails.getChildren().addAll(profileName, profileRole, emailRow, phoneRow);

//         Region profileSpace = new Region();
//         HBox.setHgrow(profileSpace, Priority.ALWAYS);

//         VBox profileActions = new VBox(10);
//         profileActions.setAlignment(Pos.CENTER);

//         Button editProfileButton = settingsActionButton("Edit Profile");
//         Button changePasswordButton = settingsActionButton("Change Password");

//         editProfileButton.setOnAction(e -> System.out.println("Edit Profile button clicked"));
//         changePasswordButton.setOnAction(e -> System.out.println("Change Password button clicked"));

//         profileActions.getChildren().addAll(editProfileButton, changePasswordButton);

//         profileRow.getChildren().addAll(avatar, profileDetails, profileSpace, profileActions);

//         profileCard.getChildren().addAll(profileCardTitle, profileRow);

//         // ================= APPEARANCE CARD =================

//         VBox appearanceCard = new VBox(12);
//         appearanceCard.setPadding(new Insets(18, 20, 18, 20));
//         appearanceCard.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 16; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.08), 10, 0, 0, 3);");

//         Text appearanceTitle = new Text("Appearance");
//         appearanceTitle.setStyle("-fx-font-size : 13px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Text themeLabel = new Text("THEME");
//         themeLabel.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : #A6B2C2;");

//         ToggleGroup themeGroup = new ToggleGroup();

//         RadioButton lightTheme = new RadioButton("Light");
//         lightTheme.setToggleGroup(themeGroup);
//         lightTheme.setSelected(true);
//         lightTheme.setStyle("-fx-font-size : 12px; -fx-text-fill : #172B4D; -fx-cursor : hand;");

//         RadioButton darkTheme = new RadioButton("Dark");
//         darkTheme.setToggleGroup(themeGroup);
//         darkTheme.setStyle("-fx-font-size : 12px; -fx-text-fill : #172B4D; -fx-cursor : hand;");

//         lightTheme.setOnAction(e -> System.out.println("Light theme selected"));
//         darkTheme.setOnAction(e -> System.out.println("Dark theme selected"));

//         HBox themeRow = new HBox(20, lightTheme, darkTheme);
//         themeRow.setAlignment(Pos.CENTER_LEFT);

//         appearanceCard.getChildren().addAll(appearanceTitle, themeLabel, themeRow);

//         // ================= NOTIFICATIONS CARD =================

//         VBox notificationsCard = new VBox(12);
//         notificationsCard.setPadding(new Insets(18, 20, 18, 20));
//         notificationsCard.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 16; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.08), 10, 0, 0, 3);");

//         Text notificationsTitle = new Text("Notifications");
//         notificationsTitle.setStyle("-fx-font-size : 13px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         HBox notificationsRow = new HBox();
//         notificationsRow.setAlignment(Pos.CENTER_LEFT);

//         Text notificationsLabel = new Text("Notifications On / Off");
//         notificationsLabel.setStyle("-fx-font-size : 12px; -fx-fill : #536277;");

//         Region notificationsSpace = new Region();
//         HBox.setHgrow(notificationsSpace, Priority.ALWAYS);

//         StackPane notificationsToggle = toggleSwitch(true);

//         notificationsRow.getChildren().addAll(notificationsLabel, notificationsSpace, notificationsToggle);

//         notificationsCard.getChildren().addAll(notificationsTitle, notificationsRow);

//         mainContent.getChildren().addAll(profileCard, appearanceCard, notificationsCard);

//         // ================= BOTTOM BAR =================

//         Button cancelButton = new Button("Cancel");
//         cancelButton.setPrefWidth(110);
//         cancelButton.setPrefHeight(38);
//         cancelButton.setStyle("-fx-background-color : #F0F4F9; -fx-text-fill : #536277; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;");

//         cancelButton.setOnAction(e -> {
//             System.out.println("Cancel button clicked");
//             callBackDashboard.run();
//         });

//         cancelButton.setOnMouseEntered(e ->
//                 cancelButton.setStyle("-fx-background-color : #E4E9F0; -fx-text-fill : #536277; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;")
//         );

//         cancelButton.setOnMouseExited(e ->
//                 cancelButton.setStyle("-fx-background-color : #F0F4F9; -fx-text-fill : #536277; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;")
//         );

//         Button saveButton = new Button("Save Changes");
//         saveButton.setPrefWidth(140);
//         saveButton.setPrefHeight(38);
//         saveButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;");

//         saveButton.setOnAction(e -> {
//             System.out.println("Save Changes button clicked");
//             NurseToast.show(NurseDashboardPage.appOverlay, "Settings saved", "success");
//         });

//         saveButton.setOnMouseEntered(e ->
//                 saveButton.setStyle("-fx-background-color : #007FAE; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;")
//         );

//         saveButton.setOnMouseExited(e ->
//                 saveButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;")
//         );

//         HBox bottomBar = new HBox(12, cancelButton, saveButton);
//         bottomBar.setAlignment(Pos.CENTER_RIGHT);
//         bottomBar.setPadding(new Insets(10, 50, 20, 50));
//         bottomBar.setStyle("-fx-border-color : #E4E9F0 transparent transparent transparent; -fx-border-width : 1 0 0 0;");

//         settingsRoot.setTop(topBar);
//         settingsRoot.setCenter(mainContent);
//         settingsRoot.setBottom(bottomBar);

//         // ================= PAGE ANIMATION =================

//         FadeTransition fade = new FadeTransition(NurseAppSettings.dur(450), settingsRoot);
//         fade.setFromValue(0.3);
//         fade.setToValue(1);
//         fade.play();

//         return settingsRoot;
//     }

//     // =========================================================
//     // SETTINGS ACTION BUTTON (Edit Profile / Change Password style)
//     // =========================================================

//     public Button settingsActionButton(String label) {

//         Button button = new Button(label);
//         button.setPrefWidth(150);
//         button.setPrefHeight(34);
//         button.setStyle("-fx-background-color : #EAF7FD; -fx-text-fill : #007FAE; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");

//         button.setOnMouseEntered(e ->
//                 button.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;")
//         );

//         button.setOnMouseExited(e ->
//                 button.setStyle("-fx-background-color : #EAF7FD; -fx-text-fill : #007FAE; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;")
//         );

//         button.focusedProperty().addListener((obs, wasFocused, isFocused) -> {

//             if (isFocused) {
//                 button.setStyle("-fx-background-color : #EAF7FD; -fx-text-fill : #007FAE; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand; -fx-border-color : #08A1E5; -fx-border-width : 2; -fx-border-radius : 8;");
//             } else {
//                 button.setStyle("-fx-background-color : #EAF7FD; -fx-text-fill : #007FAE; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");
//             }
//         });

//         return button;
//     }

//     // =========================================================
//     // TOGGLE SWITCH
//     // Simple custom on/off switch built from a rounded Rectangle track and a
//     // Circle knob, since JavaFX has no built-in toggle-switch control.
//     // =========================================================

//     public StackPane toggleSwitch(boolean initialOn) {

//         boolean[] isOn = { initialOn };

//         Rectangle track = new Rectangle(40, 22);
//         track.setArcWidth(22);
//         track.setArcHeight(22);
//         track.setFill(Color.web(isOn[0] ? "#08A1E5" : "#D8E1EB"));

//         Circle knob = new Circle(9);
//         knob.setFill(Color.WHITE);
//         knob.setStroke(Color.web("#DCE5EC"));
//         knob.setTranslateX(isOn[0] ? 9 : -9);

//         StackPane toggle = new StackPane(track, knob);
//         toggle.setPrefSize(40, 22);
//         toggle.setMaxSize(40, 22);
//         toggle.setStyle("-fx-cursor : hand;");

//         toggle.setOnMouseClicked(e -> {

//             isOn[0] = !isOn[0];

//             System.out.println("Notifications toggled " + (isOn[0] ? "ON" : "OFF"));

//             TranslateTransition slideKnob = new TranslateTransition(NurseAppSettings.dur(160), knob);
//             slideKnob.setToX(isOn[0] ? 9 : -9);

//             track.setFill(Color.web(isOn[0] ? "#08A1E5" : "#D8E1EB"));

//             new ParallelTransition(slideKnob).play();
//         });

//         return toggle;
//     }
// }



package com.kurukshetra.view.nurse;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class NurseSettingPage {

    // ================= COLOR PALETTE =================
    private static final String PRIMARY_PINK = "#E67593";
    private static final String PRIMARY_HOVER = "#D95F80";
    private static final String PINK_DARK = "#FF1493";
    private static final String VERY_LIGHT_PINK = "#FDEDF2";
    private static final String LIGHT_PINK = "#F9E0E8";
    private static final String VERY_PALE_PINK = "#FFF9FA";
    private static final String PAGE_BG = "#FCF9FA";
    private static final String SURFACE = "#FFFFFF";
    private static final String PRIMARY_TEXT = "#2B2226";
    private static final String SECONDARY_TEXT = "#665960";
    private static final String MUTED_TEXT = "#94878D";
    private static final String BORDER_COLOR = "#EEDDE3";
    private static final String DIVIDER_COLOR = "#F3E8EC";

    public BorderPane settingsRoot;

    public BorderPane getAppSettingsPage(Runnable callBackDashboard) {

        settingsRoot = new BorderPane();
        settingsRoot.setStyle("-fx-background-color : " + PAGE_BG + "; -fx-font-family : 'Segoe UI';");

        // ================= TOP BAR =================

        HBox topBar = new HBox(14);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(18, 30, 6, 30));

        Button backButton = new Button("‹");
        backButton.setPrefWidth(40);
        backButton.setPrefHeight(40);
        backButton.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-text-fill : " + PRIMARY_PINK + "; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;");

        backButton.setOnAction(e -> {
            System.out.println("Back to Dashboard");
            callBackDashboard.run();
        });

        backButton.setOnMouseEntered(e ->
                backButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;")
        );

        backButton.setOnMouseExited(e ->
                backButton.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-text-fill : " + PRIMARY_PINK + "; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;")
        );

        VBox heading = new VBox(3);
        Text pageTitle = new Text("Settings");
        pageTitle.setStyle("-fx-font-size : 24px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text pageSubtitle = new Text("Manage your basic account and preferences.");
        pageSubtitle.setStyle("-fx-font-size : 11px; -fx-fill : " + SECONDARY_TEXT + ";");

        heading.getChildren().addAll(pageTitle, pageSubtitle);
        topBar.getChildren().addAll(backButton, heading);

        // ================= MAIN CONTENT =================

        VBox mainContent = new VBox(16);
        mainContent.setPadding(new Insets(10, 50, 20, 50));

        // ================= PROFILE INFORMATION CARD =================

        VBox profileCard = new VBox(14);
        profileCard.setPadding(new Insets(18, 20, 18, 20));
        profileCard.setStyle("-fx-background-color : " + SURFACE + "; -fx-background-radius : 16; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 10, 0, 0, 3);");

        Text profileCardTitle = new Text("Profile Information");
        profileCardTitle.setStyle("-fx-font-size : 13px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        HBox profileRow = new HBox(16);
        profileRow.setAlignment(Pos.CENTER_LEFT);

        Circle avatarCircle = new Circle(28);
        avatarCircle.setFill(Color.web(PRIMARY_PINK));
        avatarCircle.setStroke(Color.WHITE);
        avatarCircle.setStrokeWidth(3);

        Text avatarLetter = new Text("S");
        avatarLetter.setStyle("-fx-font-size : 20px; -fx-font-weight : bold; -fx-fill : white;");

        StackPane avatar = new StackPane(avatarCircle, avatarLetter);

        VBox profileDetails = new VBox(4);

        Text profileName = new Text("Dr. Sarah Smith");
        profileName.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text profileRole = new Text("Head of Emergency");
        profileRole.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");

        HBox emailRow = new HBox(8);
        emailRow.setAlignment(Pos.CENTER_LEFT);
        Text emailIcon = new Text("✉");
        emailIcon.setStyle("-fx-font-size : 11px; -fx-fill : " + MUTED_TEXT + ";");
        Text emailText = new Text("sarah.smith@lifelink.med");
        emailText.setStyle("-fx-font-size : 11px; -fx-fill : " + SECONDARY_TEXT + ";");
        emailRow.getChildren().addAll(emailIcon, emailText);

        HBox phoneRow = new HBox(8);
        phoneRow.setAlignment(Pos.CENTER_LEFT);
        Text phoneIcon = new Text("☎");
        phoneIcon.setStyle("-fx-font-size : 11px; -fx-fill : " + MUTED_TEXT + ";");
        Text phoneText = new Text("+1 (555) 019-2834");
        phoneText.setStyle("-fx-font-size : 11px; -fx-fill : " + SECONDARY_TEXT + ";");
        phoneRow.getChildren().addAll(phoneIcon, phoneText);

        profileDetails.getChildren().addAll(profileName, profileRole, emailRow, phoneRow);

        Region profileSpace = new Region();
        HBox.setHgrow(profileSpace, Priority.ALWAYS);

        VBox profileActions = new VBox(10);
        profileActions.setAlignment(Pos.CENTER);

        Button editProfileButton = settingsActionButton("Edit Profile");
        Button changePasswordButton = settingsActionButton("Change Password");

        editProfileButton.setOnAction(e -> System.out.println("Edit Profile button clicked"));
        changePasswordButton.setOnAction(e -> System.out.println("Change Password button clicked"));

        profileActions.getChildren().addAll(editProfileButton, changePasswordButton);
        profileRow.getChildren().addAll(avatar, profileDetails, profileSpace, profileActions);
        profileCard.getChildren().addAll(profileCardTitle, profileRow);

        // ================= APPEARANCE CARD =================

        VBox appearanceCard = new VBox(12);
        appearanceCard.setPadding(new Insets(18, 20, 18, 20));
        appearanceCard.setStyle("-fx-background-color : " + SURFACE + "; -fx-background-radius : 16; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 10, 0, 0, 3);");

        Text appearanceTitle = new Text("Appearance");
        appearanceTitle.setStyle("-fx-font-size : 13px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text themeLabel = new Text("THEME");
        themeLabel.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + MUTED_TEXT + ";");

        ToggleGroup themeGroup = new ToggleGroup();

        RadioButton lightTheme = new RadioButton("Light");
        lightTheme.setToggleGroup(themeGroup);
        lightTheme.setSelected(true);
        lightTheme.setStyle("-fx-font-size : 12px; -fx-text-fill : " + PRIMARY_TEXT + "; -fx-cursor : hand;");

        RadioButton darkTheme = new RadioButton("Dark");
        darkTheme.setToggleGroup(themeGroup);
        darkTheme.setStyle("-fx-font-size : 12px; -fx-text-fill : " + PRIMARY_TEXT + "; -fx-cursor : hand;");

        lightTheme.setOnAction(e -> System.out.println("Light theme selected"));
        darkTheme.setOnAction(e -> System.out.println("Dark theme selected"));

        HBox themeRow = new HBox(20, lightTheme, darkTheme);
        themeRow.setAlignment(Pos.CENTER_LEFT);

        appearanceCard.getChildren().addAll(appearanceTitle, themeLabel, themeRow);

        // ================= NOTIFICATIONS CARD =================

        VBox notificationsCard = new VBox(12);
        notificationsCard.setPadding(new Insets(18, 20, 18, 20));
        notificationsCard.setStyle("-fx-background-color : " + SURFACE + "; -fx-background-radius : 16; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 10, 0, 0, 3);");

        Text notificationsTitle = new Text("Notifications");
        notificationsTitle.setStyle("-fx-font-size : 13px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        HBox notificationsRow = new HBox();
        notificationsRow.setAlignment(Pos.CENTER_LEFT);

        Text notificationsLabel = new Text("Notifications On / Off");
        notificationsLabel.setStyle("-fx-font-size : 12px; -fx-fill : " + SECONDARY_TEXT + ";");

        Region notificationsSpace = new Region();
        HBox.setHgrow(notificationsSpace, Priority.ALWAYS);

        StackPane notificationsToggle = toggleSwitch(true);

        notificationsRow.getChildren().addAll(notificationsLabel, notificationsSpace, notificationsToggle);
        notificationsCard.getChildren().addAll(notificationsTitle, notificationsRow);

        mainContent.getChildren().addAll(profileCard, appearanceCard, notificationsCard);

        // ================= BOTTOM BAR =================

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(110);
        cancelButton.setPrefHeight(38);
        cancelButton.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-text-fill : " + SECONDARY_TEXT + "; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-border-radius : 9; -fx-cursor : hand;");

        cancelButton.setOnAction(e -> {
            System.out.println("Cancel button clicked");
            callBackDashboard.run();
        });

        cancelButton.setOnMouseEntered(e ->
                cancelButton.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-border-color : " + LIGHT_PINK + "; -fx-text-fill : " + PRIMARY_TEXT + "; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-border-radius : 9; -fx-cursor : hand;")
        );

        cancelButton.setOnMouseExited(e ->
                cancelButton.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-text-fill : " + SECONDARY_TEXT + "; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-border-radius : 9; -fx-cursor : hand;")
        );

        Button saveButton = new Button("Save Changes");
        saveButton.setPrefWidth(140);
        saveButton.setPrefHeight(38);
        saveButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;");

        saveButton.setOnAction(e -> {
            System.out.println("Save Changes button clicked");
            // NurseToast.show(NurseDashboardPage.appOverlay, "Settings saved", "success");
        });

        saveButton.setOnMouseEntered(e ->
                saveButton.setStyle("-fx-background-color : " + PRIMARY_HOVER + "; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;")
        );

        saveButton.setOnMouseExited(e ->
                saveButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;")
        );

        HBox bottomBar = new HBox(12, cancelButton, saveButton);
        bottomBar.setAlignment(Pos.CENTER_RIGHT);
        bottomBar.setPadding(new Insets(10, 50, 20, 50));
        bottomBar.setStyle("-fx-border-color : " + DIVIDER_COLOR + " transparent transparent transparent; -fx-border-width : 1 0 0 0;");

        settingsRoot.setTop(topBar);
        settingsRoot.setCenter(mainContent);
        settingsRoot.setBottom(bottomBar);

        FadeTransition fade = new FadeTransition(NurseAppSettings.dur(450), settingsRoot);
        fade.setFromValue(0.3);
        fade.setToValue(1);
        fade.play();

        return settingsRoot;
    }

    public Button settingsActionButton(String label) {

        Button button = new Button(label);
        button.setPrefWidth(150);
        button.setPrefHeight(34);
        button.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-text-fill : " + PRIMARY_PINK + "; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");

        button.setOnMouseEntered(e ->
                button.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;")
        );

        button.setOnMouseExited(e ->
                button.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-text-fill : " + PRIMARY_PINK + "; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;")
        );

        button.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused) {
                button.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-text-fill : " + PRIMARY_PINK + "; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand; -fx-border-color : " + PRIMARY_PINK + "; -fx-border-width : 2; -fx-border-radius : 8;");
            } else {
                button.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-text-fill : " + PRIMARY_PINK + "; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");
            }
        });

        return button;
    }

    public StackPane toggleSwitch(boolean initialOn) {

        boolean[] isOn = { initialOn };

        Rectangle track = new Rectangle(40, 22);
        track.setArcWidth(22);
        track.setArcHeight(22);
        track.setFill(Color.web(isOn[0] ? PRIMARY_PINK : BORDER_COLOR));

        Circle knob = new Circle(9);
        knob.setFill(Color.WHITE);
        knob.setStroke(Color.web(DIVIDER_COLOR));
        knob.setTranslateX(isOn[0] ? 9 : -9);

        StackPane toggle = new StackPane(track, knob);
        toggle.setPrefSize(40, 22);
        toggle.setMaxSize(40, 22);
        toggle.setStyle("-fx-cursor : hand;");

        toggle.setOnMouseClicked(e -> {
            isOn[0] = !isOn[0];
            System.out.println("Notifications toggled " + (isOn[0] ? "ON" : "OFF"));

            TranslateTransition slideKnob = new TranslateTransition(NurseAppSettings.dur(160), knob);
            slideKnob.setToX(isOn[0] ? 9 : -9);

            track.setFill(Color.web(isOn[0] ? PRIMARY_PINK : BORDER_COLOR));
            new ParallelTransition(slideKnob).play();
        });

        return toggle;
    }
}