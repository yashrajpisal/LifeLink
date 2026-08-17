// package com.kurukshetra.view.nurse;

// import javafx.animation.FadeTransition;
// import javafx.animation.Interpolator;
// import javafx.animation.ParallelTransition;
// import javafx.animation.PauseTransition;
// import javafx.animation.ScaleTransition;
// import javafx.animation.SequentialTransition;
// import javafx.animation.TranslateTransition;
// import javafx.application.Application;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.ScrollPane;
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
// import javafx.stage.Stage;

// public class NurseDashboardPage extends Application {

//     public static Stage dashboardStage;
//     public static Scene dashboardScene;

//     public static BorderPane root;
//     public static VBox mainContent;
//     public static StackPane appOverlay;
//     public static VBox sideBarRef;

//     public Button dashboardButton;
//     public Button patientsButton;
//     public Button tripsButton;
//     public Button profileButton;
//     public Button settingsButton;
//     public Button helpButton;

//     public Button activeMenuButton;

//     @Override
//     public void start(Stage stage) throws Exception {

//         dashboardStage = stage;

//         root = new BorderPane();

//         root.setStyle("-fx-background-color : #F7F9FC; -fx-font-family : 'Segoe UI';");

//         // ================= SIDEBAR =================

//         VBox sideBar = new VBox(8);

//         sideBar.setPrefWidth(225);
//         sideBar.setPadding(new Insets(24, 16, 18, 16));

//         sideBar.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #E4E9F0; -fx-border-width : 0 1 0 0; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.06), 12, 0, 2, 0);");

//         sideBarRef = sideBar;

//         Text logo = new Text("✚ LifeLink");

//         logo.setStyle("-fx-font-size : 26px; -fx-font-weight : bold; -fx-fill : #08A1E5;");

//         VBox logoBox = new VBox(logo);
//         logoBox.setPadding(new Insets(0, 0, 14, 4));

//         // ================= PROFILE =================

//         HBox profile = new HBox(10);

//         profile.setAlignment(Pos.CENTER_LEFT);
//         profile.setPadding(new Insets(12, 10, 12, 10));
//         profile.setStyle("-fx-background-color : #EAF7FD; -fx-background-radius : 14;");

//         Circle profileCircle = new Circle(20);

//         profileCircle.setFill(Color.web("#08A1E5"));

//         Text profileLetter = new Text("S");

//         profileLetter.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : white;");

//         StackPane profileImage = new StackPane(profileCircle, profileLetter);

//         VBox profileInfo = new VBox(2);

//         Text doctorName = new Text("Dr. Sarah Smith");

//         doctorName.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Text doctorRole = new Text("Head of Emergency");
//         doctorRole.setStyle("-fx-font-size : 9px; -fx-fill : #728096;");

//         Text doctorHospital = new Text("Hospital Admin");

//         doctorHospital.setStyle("-fx-font-size : 9px; -fx-fill : #728096;");

//         profileInfo.getChildren().addAll(
//                 doctorName,
//                 doctorRole,
//                 doctorHospital);

//         profile.getChildren().addAll(
//                 profileImage,
//                 profileInfo);

//         VBox profileWrap = new VBox(profile);
//         profileWrap.setPadding(new Insets(0, 0, 12, 0));

//         Text navLabel = new Text("MENU");
//         navLabel.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : #A6B2C2;");

//         VBox navLabelBox = new VBox(navLabel);
//         navLabelBox.setPadding(new Insets(4, 0, 4, 8));

//         // ================= SIDEBAR BUTTONS =================

//         dashboardButton = menuButton("▦", "Dashboard");

//         patientsButton = menuButton("♙", "Patients Details");

//         tripsButton = menuButton("✈", "Nurse Trips");

//         profileButton = menuButton("◍", "Profile");

//         settingsButton = menuButton("⚙", "Settings");

//         helpButton = menuButton("?", "Help");

//         setSelectedMenuButton(dashboardButton);

//         dashboardButton.setOnAction(e -> {

//             System.out.println("Dashboard button clicked");

//             getToDashboardPage();
//         });

//         patientsButton.setOnAction(e -> {

//             System.out.println("Patients Details button clicked");

//             setSelectedMenuButton(patientsButton);

//             NursePatientDetailsPage patientPage = new NursePatientDetailsPage();

//             root.setCenter(patientPage.getPatientDetailsPage(() -> getToDashboardPage()));
//         });

//         tripsButton.setOnAction(e -> {

//             System.out.println("Nurse Trips button clicked");

//             setSelectedMenuButton(tripsButton);

//             NurseTripsPage tripsPage = new NurseTripsPage();

//             root.setCenter(tripsPage.getNurseTripsPage(() -> getToDashboardPage()));
//         });

//         profileButton.setOnAction(e -> {

//             System.out.println("Profile button clicked");

//             setSelectedMenuButton(profileButton);

//             NurseProfilePage profilePage = new NurseProfilePage();

//             root.setCenter(profilePage.getProfilePage(() -> getToDashboardPage()));
//         });

//         settingsButton.setOnAction(e -> {

//             System.out.println("Settings button clicked");

//             setSelectedMenuButton(settingsButton);

//             NurseSettingPage settingsPage = new NurseSettingPage();

//             root.setCenter(settingsPage.getAppSettingsPage(() -> getToDashboardPage()));
//         });

//         helpButton.setOnAction(e -> {

//             System.out.println("Help button clicked");

//             setSelectedMenuButton(helpButton);

//             NurseToast.show(appOverlay, "Help center coming soon", "info");
//         });

//         Region sideSpace = new Region();

//         VBox.setVgrow(
//                 sideSpace,
//                 Priority.ALWAYS);

//         Text supportLabel = new Text("SUPPORT");
//         supportLabel.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : #A6B2C2;");

//         VBox supportLabelBox = new VBox(supportLabel);
//         supportLabelBox.setPadding(new Insets(4, 0, 4, 8));

//         sideBar.getChildren().addAll(
//                 logoBox,
//                 profileWrap,
//                 navLabelBox,
//                 dashboardButton,
//                 patientsButton,
//                 tripsButton,
//                 profileButton,
//                 sideSpace,
//                 supportLabelBox,
//                 settingsButton,
//                 helpButton);

//         mainContent = createDashboardContent();

//         root.setLeft(sideBar);

//         root.setCenter(mainContent);

//         Circle emergencyCircle = new Circle(24);

//         emergencyCircle.setFill(Color.web("#D71920"));
//         emergencyCircle.setStroke(Color.WHITE);
//         emergencyCircle.setStrokeWidth(3);

//         Text emergencyIcon = new Text("✦");
//         emergencyIcon.setStyle("-fx-font-size : 19px; -fx-font-weight : bold; -fx-fill : white;");

//         StackPane emergencyButton = new StackPane(emergencyCircle, emergencyIcon);

//         emergencyButton.setMinSize(48, 48);

//         emergencyButton.setPrefSize(48, 48);

//         emergencyButton.setMaxSize(48, 48);

//         emergencyButton.setStyle("-fx-effect : dropshadow(gaussian, rgba(215,25,32,0.45), 14, 0, 0, 4); -fx-cursor : hand;");

//         emergencyButton.setOnMouseClicked(e -> {

//             System.out.println("Emergency button clicked");
//         });

//         ScaleTransition emergencyAnimation = new ScaleTransition(NurseAppSettings.dur(800), emergencyButton);

//         emergencyAnimation.setFromX(1);
//         emergencyAnimation.setFromY(1);
//         emergencyAnimation.setToX(1.10);
//         emergencyAnimation.setToY(1.10);
//         emergencyAnimation.setAutoReverse(true);
//         emergencyAnimation.setCycleCount(ScaleTransition.INDEFINITE);

//         emergencyAnimation.play();

//         StackPane finalRoot = new StackPane(root, emergencyButton);

//         StackPane.setAlignment(emergencyButton, Pos.BOTTOM_RIGHT);

//         StackPane.setMargin(emergencyButton, new Insets(0, 28, 28, 0));

//         appOverlay = finalRoot;

//         dashboardScene = new Scene(finalRoot, dashboardStage.getWidth(), dashboardStage.getHeight());
//         dashboardStage.setTitle("LifeLink - Dashboard");
//         dashboardStage.setScene(dashboardScene);
//         dashboardStage.setMaximized(true);
//         dashboardStage.show();

//         // ================= RESPONSIVE SIDEBAR =================
//         // Collapses the sidebar to icon-only under ~980px so the app stays usable on
//         // smaller windows / tablet-sized viewports without needing a second layout file.

//         dashboardScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {

//             boolean compact = newWidth.doubleValue() < 980;

//             sideBarRef.setPrefWidth(compact ? 76 : 225);
//             sideBarRef.setPadding(compact ? new Insets(24, 10, 18, 10) : new Insets(24, 16, 18, 16));

//             Button[] navButtons = { dashboardButton, patientsButton, tripsButton, profileButton, settingsButton, helpButton };

//             for (Button button : navButtons) {

//                 HBox box = (HBox) button.getGraphic();
//                 Text nameText = (Text) box.getChildren().get(1);
//                 nameText.setVisible(!compact);
//                 nameText.setManaged(!compact);
//                 button.setPrefWidth(compact ? 46 : 193);
//             }
//         });

//         FadeTransition fade = new FadeTransition(NurseAppSettings.dur(600), finalRoot);

//         fade.setFromValue(0.3);
//         fade.setToValue(1);

//         fade.play();
//     }

//     public VBox createDashboardContent() {

//         VBox mainContent = new VBox(24);

//         mainContent.setPadding(new Insets(32, 38, 30, 38));

//         VBox header = new VBox(5);

//         Text greeting = new Text("Morning Shift, Sarah.");
//         greeting.setStyle("-fx-font-size : 28px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Text status = new Text("Ward 3 ER is currently at 85% capacity. Priority tasks loaded.");
//         status.setStyle("-fx-font-size : 13px; -fx-fill : #728096;");

//         header.getChildren().addAll(greeting, status);

//         VBox actions = new VBox(16);

//         actions.setAlignment(Pos.CENTER_LEFT);

//         Button voiceButton = actionButton("●", "Voice Report");

//         Button consultButton = actionButton("●", "Consult Doctor");

//         Button uploadButton = actionButton("▣", "Upload Photo");

//         Button aiButton = actionButton("▣", "AI Summary");

//         voiceButton.setOnAction(e -> System.out.println("Voice Report button clicked"));

//         consultButton.setOnAction(e -> System.out.println("Consult Doctor button clicked"));

//         uploadButton.setOnAction(e -> System.out.println("Upload Photo button clicked"));

//         aiButton.setOnAction(e -> System.out.println("AI Summary button clicked"));

//         actions.getChildren().addAll(voiceButton, consultButton, uploadButton, aiButton);

//         HBox lowerArea = new HBox(20);

//         VBox summaryCard = new VBox(15);

//         summaryCard.setPrefWidth(580);
//         summaryCard.setPrefHeight(270);

//         summaryCard.setPadding(new Insets(24));
//         summaryCard.setStyle("-fx-background-color : linear-gradient(to bottom right, #FFFFFF, #F5FBFE); -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.10), 14, 0, 0, 4);");

//         HBox summaryTitle = new HBox(10);
//         summaryTitle.setAlignment(Pos.CENTER_LEFT);

//         Text star = new Text("✦");
//         star.setStyle("-fx-font-size : 20px; -fx-fill : #08A1E5;");

//         Text summaryHeading = new Text("AI Generated Summary");
//         summaryHeading.setStyle("-fx-font-size : 18px; -fx-font-weight : bold; -fx-fill : #007FAE;");
//         summaryTitle.getChildren().addAll(star, summaryHeading);

//         Text summaryText = new Text("Shift summary: 12 patients assessed, 3 emergencies handled,\n" + "resources currently at 85% capacity. The ward is\n" + "experiencing high volume but triage efficiency remains within\n" + "target parameters.");
//         summaryText.setStyle("-fx-font-size : 13px; -fx-fill : #556579;");

//         Region summarySpace = new Region();

//         VBox.setVgrow(summarySpace, Priority.ALWAYS);

//         Text updated = new Text("Last updated: 2 mins ago");
//         updated.setStyle("-fx-font-size : 10px; -fx-fill : #8993A2;");

//         summaryCard.getChildren().addAll(summaryTitle, summaryText, summarySpace, updated);

//         // ---- Skeleton shimmer: briefly show a loading placeholder before the
//         // "AI generated" text resolves, reinforcing that it was actually computed. ----

//         summaryTitle.setOpacity(0);
//         summaryText.setOpacity(0);
//         updated.setOpacity(0);

//         VBox skeleton = new VBox(10);

//         Region line1 = new Region();
//         line1.setPrefSize(220, 18);
//         line1.setStyle("-fx-background-color : #EAF0F6; -fx-background-radius : 6;");

//         Region line2 = new Region();
//         line2.setPrefSize(480, 12);
//         line2.setStyle("-fx-background-color : #EAF0F6; -fx-background-radius : 6;");

//         Region line3 = new Region();
//         line3.setPrefSize(420, 12);
//         line3.setStyle("-fx-background-color : #EAF0F6; -fx-background-radius : 6;");

//         skeleton.getChildren().addAll(line1, line2, line3);
//         skeleton.setPadding(new Insets(4, 0, 0, 0));

//         summaryCard.getChildren().add(1, skeleton);

//         FadeTransition shimmer = new FadeTransition(NurseAppSettings.dur(650), skeleton);
//         shimmer.setFromValue(1);
//         shimmer.setToValue(0.4);
//         shimmer.setAutoReverse(true);
//         shimmer.setCycleCount(2);

//         PauseTransition skeletonHold = new PauseTransition(NurseAppSettings.dur(750));

//         skeletonHold.setOnFinished(e -> {

//             summaryCard.getChildren().remove(skeleton);

//             FadeTransition revealTitle = new FadeTransition(NurseAppSettings.dur(300), summaryTitle);
//             revealTitle.setToValue(1);

//             FadeTransition revealText = new FadeTransition(NurseAppSettings.dur(300), summaryText);
//             revealText.setToValue(1);

//             FadeTransition revealUpdated = new FadeTransition(NurseAppSettings.dur(300), updated);
//             revealUpdated.setToValue(1);

//             new ParallelTransition(revealTitle, revealText, revealUpdated).play();
//         });

//         new SequentialTransition(shimmer, skeletonHold).play();

//         VBox rightCards = new VBox(18);

//         VBox activityCard = new VBox(11);

//         activityCard.setPrefWidth(290);
//         activityCard.setPrefHeight(120);

//         activityCard.setPadding(new Insets(18));
//         activityCard.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.08), 10, 0, 0, 3);");

//         Text activityTitle = new Text("↻  Recent Activity Log");
//         activityTitle.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : #08A1E5;");

//         Text activityOne = new Text("●  Patient #402 Photo Uploaded");
//         activityOne.setStyle("-fx-font-size : 10px; -fx-fill : #617085;");

//         Text activityTwo = new Text("●  AI Report Generated for Ward 2");
//         activityTwo.setStyle("-fx-font-size : 10px; -fx-fill : #617085;");

//         activityCard.getChildren().addAll(activityTitle, activityOne, activityTwo);

//         addCardHoverEffect(activityCard, "#FFFFFF", "#D8E1EB", 1, "#EAF7FD", "#08A1E5", 1.5);

//         HBox topHBox = new HBox(10);

//         VBox notificationCard = buildUpdatesWidget();

//         rightCards.getChildren().addAll(activityCard);
//         topHBox.getChildren().addAll(actions, summaryCard, notificationCard);
//         lowerArea.getChildren().addAll(rightCards);
//         mainContent.getChildren().addAll(header, topHBox, lowerArea);

//         staggerIn(summaryCard, 0);
//         staggerIn(activityCard, 90);

//         return mainContent;
//     }

//     // Fades and slides a card up into place, delayed by `delayMillis` so a group
//     // of cards enters in a soft cascade instead of popping in all at once.
//     public void staggerIn(javafx.scene.Node node, double delayMillis) {

//         node.setOpacity(0);
//         node.setTranslateY(14);

//         FadeTransition fade = new FadeTransition(NurseAppSettings.dur(400), node);
//         fade.setToValue(1);
//         fade.setDelay(NurseAppSettings.dur(delayMillis));

//         TranslateTransition slide = new TranslateTransition(NurseAppSettings.dur(400), node);
//         slide.setToY(0);
//         slide.setDelay(NurseAppSettings.dur(delayMillis));

//         new ParallelTransition(fade, slide).play();
//     }

//     public void addCardHoverEffect(VBox card, String bg, String border, double borderWidth, String hoverBg, String hoverBorder, double hoverBorderWidth) {

//         ScaleTransition scale = new ScaleTransition(NurseAppSettings.dur(150), card);

//         card.setOnMouseEntered(e -> {

//             card.setStyle("-fx-background-color : " + hoverBg + "; -fx-border-color : " + hoverBorder + "; -fx-border-width : " + hoverBorderWidth + "; -fx-border-radius : 16; -fx-background-radius : 16;");
//             scale.setToX(1.02);
//             scale.setToY(1.02);
//             scale.playFromStart();
//         });

//         card.setOnMouseExited(e -> {

//             card.setStyle("-fx-background-color : " + bg + "; -fx-border-color : " + border + "; -fx-border-width : " + borderWidth + "; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.08), 10, 0, 0, 3);");
//             scale.setToX(1);
//             scale.setToY(1);
//             scale.playFromStart();
//         });
//     }

//     public Button menuButton(
//             String icon,
//             String name) {

//         Text iconText = new Text(icon);
//         iconText.setStyle("-fx-font-size : 15px; -fx-fill : #536277;");

//         Text nameText = new Text(name);
//         nameText.setStyle("-fx-font-size : 12px; -fx-fill : #536277;");

//         HBox box = new HBox(12, iconText, nameText);
//         box.setAlignment(Pos.CENTER_LEFT);

//         Button button = new Button();
//         button.setGraphic(box);
//         button.setPrefWidth(193);
//         button.setPrefHeight(42);
//         button.setAlignment(Pos.CENTER_LEFT);

//         button.setStyle("-fx-background-color : transparent; -fx-padding : 5 15 5 15; -fx-cursor : hand;");

//         button.setOnMouseEntered(e -> {

//             if (button != activeMenuButton) {

//                 button.setStyle("-fx-background-color : #EAF7FD; -fx-background-radius : 20; -fx-padding : 5 15 5 15; -fx-cursor : hand;");
//             }
//         });

//         button.setOnMouseExited(e -> {

//             if (button != activeMenuButton) {

//                 button.setStyle("-fx-background-color : transparent; -fx-padding : 5 15 5 15; -fx-cursor : hand;");
//             }
//         });

//         button.focusedProperty().addListener((obs, wasFocused, isFocused) -> {

//             if (isFocused) {

//                 button.setStyle(button.getStyle() + " -fx-border-color : #08A1E5; -fx-border-width : 2; -fx-border-radius : 20;");

//             } else if (button != activeMenuButton) {

//                 button.setStyle("-fx-background-color : transparent; -fx-padding : 5 15 5 15; -fx-cursor : hand;");
//             }
//         });

//         return button;
//     }

//     public void setSelectedMenuButton(
//             Button selectedButton) {

//         Button[] buttons = {
//                 dashboardButton,
//                 patientsButton,
//                 tripsButton,
//                 profileButton,
//                 settingsButton,
//                 helpButton
//         };

//         for (Button button : buttons) {

//             HBox box = (HBox) button.getGraphic();
//             Text iconText = (Text) box.getChildren().get(0);
//             Text nameText = (Text) box.getChildren().get(1);

//             if (button == selectedButton) {

//                 button.setStyle("-fx-background-color : #08A1E5; -fx-background-radius : 20; -fx-padding : 5 15 5 15; -fx-cursor : hand; -fx-effect : dropshadow(gaussian, rgba(8,161,229,0.35), 8, 0, 0, 2);");
//                 iconText.setStyle("-fx-font-size : 15px; -fx-fill : white;");
//                 nameText.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : white;");

//             } else {

//                 button.setStyle("-fx-background-color : transparent; -fx-padding : 5 15 5 15; -fx-cursor : hand;");
//                 iconText.setStyle("-fx-font-size : 15px; -fx-fill : #536277;");
//                 nameText.setStyle("-fx-font-size : 12px; -fx-fill : #536277;");
//             }
//         }

//         activeMenuButton = selectedButton;

//         ScaleTransition pulse = new ScaleTransition(NurseAppSettings.dur(140), selectedButton);
//         pulse.setFromX(0.96);
//         pulse.setFromY(0.9);
//         pulse.setToX(1);
//         pulse.setToY(1);
//         pulse.play();
//     }

//     public Button actionButton(String icon, String name) {

//         Text iconText = new Text(icon);

//         iconText.setStyle("-fx-font-size : 20px; -fx-fill : #08A1E5;");

//         Text nameText = new Text(name);

//         nameText.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         VBox box = new VBox(10, iconText, nameText);
//         box.setAlignment(Pos.CENTER);

//         Button button = new Button();
//         button.setGraphic(box);
//         button.setPrefWidth(155);
//         button.setPrefHeight(95);

//         button.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.08), 8, 0, 0, 3); -fx-cursor : hand;");

//         ScaleTransition scale = new ScaleTransition(NurseAppSettings.dur(150), button);

//         button.setOnMouseEntered(e -> {

//             button.setStyle("-fx-background-color : #EAF7FD; -fx-border-color : #08A1E5; -fx-border-width : 1.5; -fx-border-radius : 14; -fx-background-radius : 14; -fx-cursor : hand;");
//             scale.setToX(1.04);
//             scale.setToY(1.04);
//             scale.playFromStart();
//         });

//         button.setOnMouseExited(e -> {

//             button.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14; -fx-cursor : hand;");
//             scale.setToX(1);
//             scale.setToY(1);
//             scale.playFromStart();
//         });

//         button.setOnMousePressed(e -> {
//             scale.setToX(0.97);
//             scale.setToY(0.97);
//             scale.playFromStart();
//         });

//         button.setOnMouseReleased(e -> {
//             scale.setToX(1.04);
//             scale.setToY(1.04);
//             scale.playFromStart();
//         });

//         button.focusedProperty().addListener((obs, wasFocused, isFocused) -> {

//             if (isFocused) {

//                 button.setStyle("-fx-background-color : #EAF7FD; -fx-border-color : #08A1E5; -fx-border-width : 2; -fx-border-radius : 14; -fx-background-radius : 14; -fx-cursor : hand;");

//             } else {

//                 button.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14; -fx-cursor : hand;");
//             }
//         });

//         return button;
//     }

//     // =========================================================
//     // NOTIFICATIONS / MISSION PROGRESS WIDGET
//     // One fixed-size card with two swappable views (Notifications and
//     // Mission Progress) that slide past each other horizontally when the
//     // pill tabs above them are clicked. Only ever one card, one view visible. 
//     // =========================================================

//     public VBox buildUpdatesWidget() {

//         double viewportWidth = 294;
//         double viewportHeight = 240;

//         VBox card = new VBox(14);
//         card.setPrefWidth(330);
//         card.setPadding(new Insets(18));
//         card.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.08), 10, 0, 0, 3);");

//         // Text headerIcon = new Text("⊞");
//         // headerIcon.setStyle("-fx-font-size : 20px; -fx-fill : #08A1E5;");

//         Button notifTab = tabPill("🔔", "Notifications");
//         Button missionTab = tabPill("📈", "Mission Progress");

//         HBox tabRow = new HBox(8, notifTab, missionTab);
//         tabRow.setAlignment(Pos.CENTER_LEFT);

//         Region divider = new Region();
//         divider.setPrefHeight(1);
//         divider.setStyle("-fx-background-color : #EEF1F6;");

//         VBox notificationsView = buildNotificationsView(viewportWidth);
//         notificationsView.setPrefWidth(viewportWidth);
//         notificationsView.setMaxWidth(viewportWidth);

//         VBox missionView = buildMissionProgressView(viewportWidth);
//         missionView.setPrefWidth(viewportWidth);
//         missionView.setMaxWidth(viewportWidth);
//         missionView.setTranslateX(viewportWidth);

//         StackPane viewport = new StackPane(notificationsView, missionView);
//         viewport.setPrefSize(viewportWidth, viewportHeight);
//         viewport.setMaxSize(viewportWidth, viewportHeight);
//         viewport.setAlignment(Pos.TOP_LEFT);

//         Rectangle clip = new Rectangle(viewportWidth, viewportHeight);
//         viewport.setClip(clip);

//         boolean[] onMissionView = { false };

//         setTabActive(notifTab, true);
//         setTabActive(missionTab, false);

//         notifTab.setOnAction(e -> {

//             if (!onMissionView[0]) {
//                 return;
//             }

//             onMissionView[0] = false;

//             setTabActive(notifTab, true);
//             setTabActive(missionTab, false);

//             slideViews(missionView, notificationsView, viewportWidth, false);
//         });

//         missionTab.setOnAction(e -> {

//             if (onMissionView[0]) {
//                 return;
//             }

//             onMissionView[0] = true;

//             setTabActive(notifTab, false);
//             setTabActive(missionTab, true);

//             slideViews(notificationsView, missionView, viewportWidth, true);
//         });

//         card.getChildren().addAll( tabRow, divider, viewport);

//         return card;
//     }

//     // Slides the current view out one side while the new view slides in from
//     // the other, 300ms ease-in-out, matching the sidebar's other transitions.
//     public void slideViews(javafx.scene.Node outgoing, javafx.scene.Node incoming, double width, boolean movingToMap) {

//         double outgoingTarget = movingToMap ? -width : width;

//         TranslateTransition outAnim = new TranslateTransition(NurseAppSettings.dur(300), outgoing);
//         outAnim.setToX(outgoingTarget);
//         outAnim.setInterpolator(Interpolator.EASE_BOTH);

//         incoming.setTranslateX(movingToMap ? width : -width);

//         TranslateTransition inAnim = new TranslateTransition(NurseAppSettings.dur(300), incoming);
//         inAnim.setToX(0);
//         inAnim.setInterpolator(Interpolator.EASE_BOTH);

//         new ParallelTransition(outAnim, inAnim).play();
//     }

//     public Button tabPill(String icon, String label) {

//         Text iconText = new Text(icon);
//         iconText.setStyle("-fx-font-size : 11px;");

//         Text labelText = new Text(label);
//         labelText.setStyle("-fx-font-size : 10px; -fx-font-weight : bold;");

//         HBox box = new HBox(6, iconText, labelText);
//         box.setAlignment(Pos.CENTER);

//         Button button = new Button();
//         button.setGraphic(box);
//         button.setPrefHeight(30);
//         button.setPadding(new Insets(0, 12, 0, 12));
//         button.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-cursor : hand;");

//         button.setOnMouseEntered(e -> {

//             if (!button.getStyle().contains("dropshadow")) {
//                 button.setStyle("-fx-background-color : #F0F8FE; -fx-border-color : #08A1E5; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-cursor : hand;");
//             }
//         });

//         button.setOnMouseExited(e -> {

//             if (!button.getStyle().contains("dropshadow")) {
//                 button.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-cursor : hand;");
//             }
//         });

//         return button;
//     }

//     public void setTabActive(Button button, boolean active) {

//         HBox box = (HBox) button.getGraphic();
//         Text labelText = (Text) box.getChildren().get(1);

//         if (active) {

//             button.setStyle("-fx-background-color : #08A1E5; -fx-border-color : #08A1E5; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-cursor : hand; -fx-effect : dropshadow(gaussian, rgba(8,161,229,0.30), 6, 0, 0, 2);");
//             labelText.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : white;");

//         } else {

//             button.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-cursor : hand;");
//             labelText.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : #536277;");
//         }
//     }

//     // =========================================================
//     // NOTIFICATIONS VIEW
//     // =========================================================

//     public VBox buildNotificationsView(double width) {

//         VBox view = new VBox(10);
//         view.setPrefWidth(width);

//         HBox headerRow = new HBox();
//         headerRow.setAlignment(Pos.CENTER_LEFT);

//         Text title = new Text("Notifications");
//         title.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Region space = new Region();
//         HBox.setHgrow(space, Priority.ALWAYS);

//         Text markRead = new Text("Mark all as read");
//         markRead.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : #08A1E5; -fx-cursor : hand;");
//         markRead.setOnMouseClicked(e -> System.out.println("Mark all as read clicked"));

//         headerRow.getChildren().addAll(title, space, markRead);

//         VBox list = new VBox(2);

//         list.getChildren().addAll(
//                 notificationItem("Emergency Protocol Activated", "Emergency protocol has been activated.", "2 min ago", true),
//                 notificationItem("Patient Pickup Request", "New patient pickup request has been received.", "5 min ago", true),
//                 notificationItem("Patient Details Updated", "Patient information has been updated successfully.", "12 min ago", false),
//                 notificationItem("Trip Started", "Your assigned patient trip has started.", "20 min ago", false));

//         ScrollPane scroll = new ScrollPane(list);
//         scroll.setFitToWidth(true);
//         scroll.setPrefHeight(190);
//         scroll.setStyle("-fx-background-color : transparent; -fx-background : transparent; -fx-border-color : transparent;");

//         view.getChildren().addAll(headerRow, scroll);

//         return view;
//     }

//     public HBox notificationItem(String title, String description, String time, boolean unread) {

//         HBox row = new HBox(8);
//         row.setAlignment(Pos.TOP_LEFT);
//         row.setPadding(new Insets(8, 6, 8, 6));
//         row.setStyle("-fx-background-color : " + (unread ? "#F5FBFE" : "transparent") + "; -fx-background-radius : 8;");

//         Circle dot = new Circle(3);
//         dot.setFill(Color.web(unread ? "#08A1E5" : "transparent"));

//         VBox dotWrap = new VBox(dot);
//         dotWrap.setAlignment(Pos.TOP_CENTER);
//         dotWrap.setPadding(new Insets(4, 0, 0, 0));

//         VBox textBox = new VBox(3);

//         Text titleText = new Text(title);
//         titleText.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Text descText = new Text(description);
//         descText.setStyle("-fx-font-size : 9px; -fx-fill : #728096;");
//         descText.setWrappingWidth(225);

//         Text timeText = new Text(time);
//         timeText.setStyle("-fx-font-size : 8px; -fx-font-weight : bold; -fx-fill : #08A1E5;");

//         textBox.getChildren().addAll(titleText, descText, timeText);

//         row.getChildren().addAll(dotWrap, textBox);

//         row.setOnMouseEntered(e -> row.setStyle("-fx-background-color : #EAF7FD; -fx-background-radius : 8;"));

//         row.setOnMouseExited(e -> row.setStyle("-fx-background-color : " + (unread ? "#F5FBFE" : "transparent") + "; -fx-background-radius : 8;"));

//         return row;
//     }

//     // =========================================================
//     // MISSION PROGRESS VIEW
//     // Vertical step timeline: filled blue circles with checkmarks for
//     // completed steps, a pulsing blue circle for the current step, and
//     // light outlined circles for upcoming steps, connected by a line.
//     // =========================================================

//     public VBox buildMissionProgressView(double width) {

//         VBox view = new VBox(10);
//         view.setPrefWidth(width);

//         Text title = new Text("Mission Progress");
//         title.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         VBox stepsList = new VBox(0);

//         stepsList.getChildren().addAll(
//                 missionStep("Patient Request Received", "Central Dispatch", "10:42 AM", "completed", null, false),
//                 missionStep("Patient Details Received", "Patient information received successfully", "10:43 AM", "completed", null, false),
//                 missionStep("En Route to Patient", "ETA: 4 mins", "NOW", "current", "Traffic is heavy on the current route.", false),
//                 missionStep("Patient Pickup", "Waiting for patient pickup", "", "upcoming", null, false),
//                 missionStep("Returning to Hospital", "Patient transportation in progress", "", "upcoming", null, false),
//                 missionStep("Mission Completed", "Patient safely delivered", "", "upcoming", null, true));

//         ScrollPane scroll = new ScrollPane(stepsList);
//         scroll.setFitToWidth(true);
//         scroll.setPrefHeight(200);
//         scroll.setStyle("-fx-background-color : transparent; -fx-background : transparent; -fx-border-color : transparent;");

//         view.getChildren().addAll(title, scroll);

//         return view;
//     }

//     public HBox missionStep(String title, String subtitle, String time, String state, String infoMessage, boolean isLast) {

//         HBox row = new HBox(12);
//         row.setAlignment(Pos.TOP_LEFT);

//         // ---- left column: checkpoint circle + connecting line down to the next step ----

//         VBox leftCol = new VBox(0);
//         leftCol.setAlignment(Pos.TOP_CENTER);
//         leftCol.setPrefWidth(20);

//         StackPane checkpoint = new StackPane();
//         checkpoint.setMinSize(18, 18);
//         checkpoint.setPrefSize(18, 18);
//         checkpoint.setMaxSize(18, 18);

//         if (state.equals("completed")) {

//             Circle circle = new Circle(9);
//             circle.setFill(Color.web("#08A1E5"));

//             Text check = new Text("✓");
//             check.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : white;");

//             checkpoint.getChildren().addAll(circle, check);

//         } else if (state.equals("current")) {

//             Circle glow = new Circle(9);
//             glow.setFill(Color.web("#08A1E5", 0.25));

//             Circle circle = new Circle(6);
//             circle.setFill(Color.web("#08A1E5"));

//             checkpoint.getChildren().addAll(glow, circle);

//             ScaleTransition pulse = new ScaleTransition(NurseAppSettings.dur(900), glow);
//             pulse.setFromX(1);
//             pulse.setFromY(1);
//             pulse.setToX(1.6);
//             pulse.setToY(1.6);
//             pulse.setAutoReverse(true);
//             pulse.setCycleCount(ScaleTransition.INDEFINITE);
//             pulse.play();

//         } else {

//             Circle circle = new Circle(8);
//             circle.setFill(Color.WHITE);
//             circle.setStroke(Color.web("#C7D2DE"));
//             circle.setStrokeWidth(2);

//             checkpoint.getChildren().add(circle);
//         }

//         leftCol.getChildren().add(checkpoint);

//         if (!isLast) {

//             Region line = new Region();
//             line.setPrefWidth(2);
//             line.setPrefHeight(infoMessage != null ? 86 : 42);
//             line.setStyle("-fx-background-color : " + (state.equals("upcoming") ? "#E4E9F0" : "#B9DEF2") + ";");

//             VBox lineWrap = new VBox(line);
//             lineWrap.setAlignment(Pos.TOP_CENTER);
//             lineWrap.setPadding(new Insets(5, 7, 7, 7));

//             leftCol.getChildren().add(lineWrap);
//         }

//         // ---- right column: title / subtitle / time / info box ----

//         VBox rightCol = new VBox(3);
//         rightCol.setPadding(new Insets(0, 0, 14, 0));

//         HBox titleRow = new HBox();
//         titleRow.setAlignment(Pos.CENTER_LEFT);

//         String titleColor = state.equals("upcoming") ? "#A6B2C2" : "#172B4D";

//         Text titleText = new Text(title);
//         titleText.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + titleColor + ";");

//         Region space = new Region();
//         HBox.setHgrow(space, Priority.ALWAYS);

//         titleRow.getChildren().addAll(titleText, space);

//         if (!time.isEmpty()) {

//             Text timeText = new Text(time);
//             timeText.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + (state.equals("current") ? "#08A1E5" : "#A6B2C2") + ";");
//             titleRow.getChildren().add(timeText);
//         }

//         Text subtitleText = new Text(subtitle);
//         subtitleText.setStyle("-fx-font-size : 9px; -fx-fill : " + (state.equals("upcoming") ? "#A6B2C2" : "#728096") + ";");
//         subtitleText.setWrappingWidth(190);

//         rightCol.getChildren().addAll(titleRow, subtitleText);

//         if (infoMessage != null) {

//             Text infoText = new Text(infoMessage);
//             infoText.setStyle("-fx-font-size : 8px; -fx-fill : #536277;");
//             infoText.setWrappingWidth(175);

//             StackPane infoBox = new StackPane(infoText);
//             infoBox.setPadding(new Insets(8));
//             infoBox.setAlignment(Pos.TOP_LEFT);
//             infoBox.setStyle("-fx-background-color : #EAF7FD; -fx-background-radius : 8;");

//             VBox infoWrap = new VBox(infoBox);
//             infoWrap.setPadding(new Insets(6, 0, 0, 0));

//             rightCol.getChildren().add(infoWrap);
//         }

//         row.getChildren().addAll(leftCol, rightCol);

//         return row;
//     }

//     public void getToDashboardPage() {

//         setSelectedMenuButton(dashboardButton);
//         root.setCenter(mainContent);
//     }
// }


package com.kurukshetra.view.nurse;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
import javafx.stage.Stage;

public class NurseDashboardPage extends Application {

    // ================= COLOR CONSTANTS (SOFT FAINT PINK THEME) =================
    private static final String PRIMARY_PINK = "#E67593";
    private static final String PINK_DARK = "#FF1493";
    private static final String PRIMARY_HOVER = "#D95F80";
    private static final String VERY_LIGHT_PINK = "#FDEDF2";
    private static final String LIGHT_PINK = "#F9E0E8";
    private static final String SOFT_PINK = "#F5D1DC";
    private static final String PALE_PINK = "#FFF6F8";
    private static final String VERY_PALE_PINK = "#FFF9FA";
    private static final String PAGE_BG = "#FCF9FA";
    private static final String SURFACE = "#FFFFFF";
    private static final String PRIMARY_TEXT = "#2B2226";
    private static final String SECONDARY_TEXT = "#665960";
    private static final String MUTED_TEXT = "#94878D";
    private static final String BORDER_COLOR = "#EEDDE3";
    private static final String DIVIDER_COLOR = "#F3E8EC";
    private static final String HOVER_BG = "#FFF2F5";

    public static Stage dashboardStage;
    public static Scene dashboardScene;

    public static BorderPane root;
    public static VBox mainContent;
    public static StackPane appOverlay;
    public static VBox sideBarRef;

    public Button dashboardButton;
    public Button patientsButton;
    public Button tripsButton;
    public Button profileButton;
    public Button settingsButton;
    public Button helpButton;

    public Button activeMenuButton;

    @Override
    public void start(Stage stage) throws Exception {

        dashboardStage = stage;

        root = new BorderPane();
        root.setStyle("-fx-background-color : " + PAGE_BG + "; -fx-font-family : 'Segoe UI';");

        // ================= SIDEBAR =================

        VBox sideBar = new VBox(8);

        sideBar.setPrefWidth(225);
        sideBar.setPadding(new Insets(24, 16, 18, 16));
        sideBar.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 0 1 0 0; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.06), 12, 0, 2, 0);");

        sideBarRef = sideBar;

        Text logo = new Text("✚ LifeLink");
        logo.setStyle("-fx-font-size : 26px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");

        VBox logoBox = new VBox(logo);
        logoBox.setPadding(new Insets(0, 0, 14, 4));

        // ================= PROFILE =================

        HBox profile = new HBox(10);

        profile.setAlignment(Pos.CENTER_LEFT);
        profile.setPadding(new Insets(12, 10, 12, 10));
        profile.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-background-radius : 14;");

        Circle profileCircle = new Circle(20);
        profileCircle.setFill(Color.web(PRIMARY_PINK));

        Text profileLetter = new Text("S");
        profileLetter.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : white;");

        StackPane profileImage = new StackPane(profileCircle, profileLetter);

        VBox profileInfo = new VBox(2);

        Text doctorName = new Text("Dr. Sarah Smith");
        doctorName.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text doctorRole = new Text("Head of Emergency");
        doctorRole.setStyle("-fx-font-size : 9px; -fx-fill : " + SECONDARY_TEXT + ";");

        Text doctorHospital = new Text("Hospital Admin");
        doctorHospital.setStyle("-fx-font-size : 9px; -fx-fill : " + SECONDARY_TEXT + ";");

        profileInfo.getChildren().addAll(
                doctorName,
                doctorRole,
                doctorHospital);

        profile.getChildren().addAll(
                profileImage,
                profileInfo);

        VBox profileWrap = new VBox(profile);
        profileWrap.setPadding(new Insets(0, 0, 12, 0));

        Text navLabel = new Text("MENU");
        navLabel.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + MUTED_TEXT + ";");

        VBox navLabelBox = new VBox(navLabel);
        navLabelBox.setPadding(new Insets(4, 0, 4, 8));

        // ================= SIDEBAR BUTTONS =================

        dashboardButton = menuButton("▦", "Dashboard");
        patientsButton = menuButton("♙", "Patients Details");
        tripsButton = menuButton("✈", "Nurse Trips");
        profileButton = menuButton("◍", "Profile");
        settingsButton = menuButton("⚙", "Settings");
        helpButton = menuButton("?", "Help");

        setSelectedMenuButton(dashboardButton);

        dashboardButton.setOnAction(e -> {
            System.out.println("Dashboard button clicked");
            getToDashboardPage();
        });

        patientsButton.setOnAction(e -> {
            System.out.println("Patients Details button clicked");
            setSelectedMenuButton(patientsButton);
            NursePatientDetailsPage patientPage = new NursePatientDetailsPage();
            root.setCenter(patientPage.getPatientDetailsPage(() -> getToDashboardPage()));
        });

        tripsButton.setOnAction(e -> {
            System.out.println("Nurse Trips button clicked");
            setSelectedMenuButton(tripsButton);
            NurseTripsPage tripsPage = new NurseTripsPage();
            root.setCenter(tripsPage.getNurseTripsPage(() -> getToDashboardPage()));
        });

        profileButton.setOnAction(e -> {
            System.out.println("Profile button clicked");
            setSelectedMenuButton(profileButton);
            NurseProfilePage profilePage = new NurseProfilePage();
            root.setCenter(profilePage.getProfilePage(() -> getToDashboardPage()));
        });

        settingsButton.setOnAction(e -> {
            System.out.println("Settings button clicked");
            setSelectedMenuButton(settingsButton);
            NurseSettingPage settingsPage = new NurseSettingPage();
            root.setCenter(settingsPage.getAppSettingsPage(() -> getToDashboardPage()));
        });

        helpButton.setOnAction(e -> {
            System.out.println("Help button clicked");
            setSelectedMenuButton(helpButton);
            NurseToast.show(appOverlay, "Help center coming soon", "info");
        });

        Region sideSpace = new Region();
        VBox.setVgrow(sideSpace, Priority.ALWAYS);

        Text supportLabel = new Text("SUPPORT");
        supportLabel.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + MUTED_TEXT + ";");

        VBox supportLabelBox = new VBox(supportLabel);
        supportLabelBox.setPadding(new Insets(4, 0, 4, 8));

        sideBar.getChildren().addAll(
                logoBox,
                profileWrap,
                navLabelBox,
                dashboardButton,
                patientsButton,
                tripsButton,
                profileButton,
                sideSpace,
                supportLabelBox,
                settingsButton,
                helpButton);

        mainContent = createDashboardContent();

        root.setLeft(sideBar);
        root.setCenter(mainContent);

        // Destructive / Emergency Floating Action Button (preserves distinct red identity)
        Circle emergencyCircle = new Circle(24);
        emergencyCircle.setFill(Color.web("#D71920"));
        emergencyCircle.setStroke(Color.WHITE);
        emergencyCircle.setStrokeWidth(3);

        Text emergencyIcon = new Text("✦");
        emergencyIcon.setStyle("-fx-font-size : 19px; -fx-font-weight : bold; -fx-fill : white;");

        StackPane emergencyButton = new StackPane(emergencyCircle, emergencyIcon);
        emergencyButton.setMinSize(48, 48);
        emergencyButton.setPrefSize(48, 48);
        emergencyButton.setMaxSize(48, 48);
        emergencyButton.setStyle("-fx-effect : dropshadow(gaussian, rgba(215,25,32,0.45), 14, 0, 0, 4); -fx-cursor : hand;");

        emergencyButton.setOnMouseClicked(e -> {
            System.out.println("Emergency button clicked");
        });

        ScaleTransition emergencyAnimation = new ScaleTransition(NurseAppSettings.dur(800), emergencyButton);
        emergencyAnimation.setFromX(1);
        emergencyAnimation.setFromY(1);
        emergencyAnimation.setToX(1.10);
        emergencyAnimation.setToY(1.10);
        emergencyAnimation.setAutoReverse(true);
        emergencyAnimation.setCycleCount(ScaleTransition.INDEFINITE);
        emergencyAnimation.play();

        StackPane finalRoot = new StackPane(root, emergencyButton);
        StackPane.setAlignment(emergencyButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(emergencyButton, new Insets(0, 28, 28, 0));

        appOverlay = finalRoot;

        dashboardScene = new Scene(finalRoot, dashboardStage.getWidth(), dashboardStage.getHeight());
        dashboardStage.setTitle("LifeLink - Dashboard");
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setMaximized(true);
        dashboardStage.show();

        // Responsive Sidebar
        dashboardScene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            boolean compact = newWidth.doubleValue() < 980;

            sideBarRef.setPrefWidth(compact ? 76 : 225);
            sideBarRef.setPadding(compact ? new Insets(24, 10, 18, 10) : new Insets(24, 16, 18, 16));

            Button[] navButtons = { dashboardButton, patientsButton, tripsButton, profileButton, settingsButton, helpButton };

            for (Button button : navButtons) {
                HBox box = (HBox) button.getGraphic();
                Text nameText = (Text) box.getChildren().get(1);
                nameText.setVisible(!compact);
                nameText.setManaged(!compact);
                button.setPrefWidth(compact ? 46 : 193);
            }
        });

        FadeTransition fade = new FadeTransition(NurseAppSettings.dur(600), finalRoot);
        fade.setFromValue(0.3);
        fade.setToValue(1);
        fade.play();
    }

    public VBox createDashboardContent() {

        VBox mainContent = new VBox(24);
        mainContent.setPadding(new Insets(32, 38, 30, 38));

        VBox header = new VBox(5);

        Text greeting = new Text("Morning Shift, Sarah.");
        greeting.setStyle("-fx-font-size : 28px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text status = new Text("Ward 3 ER is currently at 85% capacity. Priority tasks loaded.");
        status.setStyle("-fx-font-size : 13px; -fx-fill : " + SECONDARY_TEXT + ";");

        header.getChildren().addAll(greeting, status);

        VBox actions = new VBox(16);
        actions.setAlignment(Pos.CENTER_LEFT);

        Button voiceButton = actionButton("●", "Voice Report");
        Button consultButton = actionButton("●", "Consult Doctor");
        Button uploadButton = actionButton("▣", "Upload Photo");
        Button aiButton = actionButton("▣", "AI Summary");

        voiceButton.setOnAction(e -> System.out.println("Voice Report button clicked"));
        consultButton.setOnAction(e -> System.out.println("Consult Doctor button clicked"));
        uploadButton.setOnAction(e -> System.out.println("Upload Photo button clicked"));
        aiButton.setOnAction(e -> System.out.println("AI Summary button clicked"));

        actions.getChildren().addAll(voiceButton, consultButton, uploadButton, aiButton);

        HBox lowerArea = new HBox(20);

        VBox summaryCard = new VBox(15);
        summaryCard.setPrefWidth(580);
        summaryCard.setPrefHeight(270);
        summaryCard.setPadding(new Insets(24));
        summaryCard.setStyle("-fx-background-color : linear-gradient(to bottom right, #FFFFFF, " + VERY_PALE_PINK + "); -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 14, 0, 0, 4);");

        HBox summaryTitle = new HBox(10);
        summaryTitle.setAlignment(Pos.CENTER_LEFT);

        Text star = new Text("✦");
        star.setStyle("-fx-font-size : 20px; -fx-fill : " + PRIMARY_PINK + ";");

        Text summaryHeading = new Text("AI Generated Summary");
        summaryHeading.setStyle("-fx-font-size : 18px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");
        summaryTitle.getChildren().addAll(star, summaryHeading);

        Text summaryText = new Text("Shift summary: 12 patients assessed, 3 emergencies handled,\n" + "resources currently at 85% capacity. The ward is\n" + "experiencing high volume but triage efficiency remains within\n" + "target parameters.");
        summaryText.setStyle("-fx-font-size : 13px; -fx-fill : " + SECONDARY_TEXT + ";");

        Region summarySpace = new Region();
        VBox.setVgrow(summarySpace, Priority.ALWAYS);

        Text updated = new Text("Last updated: 2 mins ago");
        updated.setStyle("-fx-font-size : 10px; -fx-fill : " + MUTED_TEXT + ";");

        summaryCard.getChildren().addAll(summaryTitle, summaryText, summarySpace, updated);

        // Skeleton Shimmer
        summaryTitle.setOpacity(0);
        summaryText.setOpacity(0);
        updated.setOpacity(0);

        VBox skeleton = new VBox(10);
        Region line1 = new Region();
        line1.setPrefSize(220, 18);
        line1.setStyle("-fx-background-color : " + LIGHT_PINK + "; -fx-background-radius : 6;");

        Region line2 = new Region();
        line2.setPrefSize(480, 12);
        line2.setStyle("-fx-background-color : " + LIGHT_PINK + "; -fx-background-radius : 6;");

        Region line3 = new Region();
        line3.setPrefSize(420, 12);
        line3.setStyle("-fx-background-color : " + LIGHT_PINK + "; -fx-background-radius : 6;");

        skeleton.getChildren().addAll(line1, line2, line3);
        skeleton.setPadding(new Insets(4, 0, 0, 0));

        summaryCard.getChildren().add(1, skeleton);

        FadeTransition shimmer = new FadeTransition(NurseAppSettings.dur(650), skeleton);
        shimmer.setFromValue(1);
        shimmer.setToValue(0.4);
        shimmer.setAutoReverse(true);
        shimmer.setCycleCount(2);

        PauseTransition skeletonHold = new PauseTransition(NurseAppSettings.dur(750));
        skeletonHold.setOnFinished(e -> {
            summaryCard.getChildren().remove(skeleton);

            FadeTransition revealTitle = new FadeTransition(NurseAppSettings.dur(300), summaryTitle);
            revealTitle.setToValue(1);

            FadeTransition revealText = new FadeTransition(NurseAppSettings.dur(300), summaryText);
            revealText.setToValue(1);

            FadeTransition revealUpdated = new FadeTransition(NurseAppSettings.dur(300), updated);
            revealUpdated.setToValue(1);

            new ParallelTransition(revealTitle, revealText, revealUpdated).play();
        });

        new SequentialTransition(shimmer, skeletonHold).play();

        VBox rightCards = new VBox(18);

        VBox activityCard = new VBox(11);
        activityCard.setPrefWidth(290);
        activityCard.setPrefHeight(120);
        activityCard.setPadding(new Insets(18));
        activityCard.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 10, 0, 0, 3);");

        Text activityTitle = new Text("↻  Recent Activity Log");
        activityTitle.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");

        Text activityOne = new Text("●  Patient #402 Photo Uploaded");
        activityOne.setStyle("-fx-font-size : 10px; -fx-fill : " + SECONDARY_TEXT + ";");

        Text activityTwo = new Text("●  AI Report Generated for Ward 2");
        activityTwo.setStyle("-fx-font-size : 10px; -fx-fill : " + SECONDARY_TEXT + ";");

        activityCard.getChildren().addAll(activityTitle, activityOne, activityTwo);

        addCardHoverEffect(activityCard, SURFACE, BORDER_COLOR, 1, VERY_LIGHT_PINK, PRIMARY_PINK, 1.5);

        HBox topHBox = new HBox(10);
        VBox notificationCard = buildUpdatesWidget();

        rightCards.getChildren().addAll(activityCard);
        topHBox.getChildren().addAll(actions, summaryCard, notificationCard);
        lowerArea.getChildren().addAll(rightCards);
        mainContent.getChildren().addAll(header, topHBox, lowerArea);

        staggerIn(summaryCard, 0);
        staggerIn(activityCard, 90);

        return mainContent;
    }

    public void staggerIn(javafx.scene.Node node, double delayMillis) {
        node.setOpacity(0);
        node.setTranslateY(14);

        FadeTransition fade = new FadeTransition(NurseAppSettings.dur(400), node);
        fade.setToValue(1);
        fade.setDelay(NurseAppSettings.dur(delayMillis));

        TranslateTransition slide = new TranslateTransition(NurseAppSettings.dur(400), node);
        slide.setToY(0);
        slide.setDelay(NurseAppSettings.dur(delayMillis));

        new ParallelTransition(fade, slide).play();
    }

    public void addCardHoverEffect(VBox card, String bg, String border, double borderWidth, String hoverBg, String hoverBorder, double hoverBorderWidth) {
        ScaleTransition scale = new ScaleTransition(NurseAppSettings.dur(150), card);

        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-background-color : " + hoverBg + "; -fx-border-color : " + hoverBorder + "; -fx-border-width : " + hoverBorderWidth + "; -fx-border-radius : 16; -fx-background-radius : 16;");
            scale.setToX(1.02);
            scale.setToY(1.02);
            scale.playFromStart();
        });

        card.setOnMouseExited(e -> {
            card.setStyle("-fx-background-color : " + bg + "; -fx-border-color : " + border + "; -fx-border-width : " + borderWidth + "; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 10, 0, 0, 3);");
            scale.setToX(1);
            scale.setToY(1);
            scale.playFromStart();
        });
    }

    public Button menuButton(String icon, String name) {
        Text iconText = new Text(icon);
        iconText.setStyle("-fx-font-size : 15px; -fx-fill : " + SECONDARY_TEXT + ";");

        Text nameText = new Text(name);
        nameText.setStyle("-fx-font-size : 12px; -fx-fill : " + SECONDARY_TEXT + ";");

        HBox box = new HBox(12, iconText, nameText);
        box.setAlignment(Pos.CENTER_LEFT);

        Button button = new Button();
        button.setGraphic(box);
        button.setPrefWidth(193);
        button.setPrefHeight(42);
        button.setAlignment(Pos.CENTER_LEFT);

        button.setStyle("-fx-background-color : transparent; -fx-padding : 5 15 5 15; -fx-cursor : hand;");

        button.setOnMouseEntered(e -> {
            if (button != activeMenuButton) {
                button.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-background-radius : 20; -fx-padding : 5 15 5 15; -fx-cursor : hand;");
            }
        });

        button.setOnMouseExited(e -> {
            if (button != activeMenuButton) {
                button.setStyle("-fx-background-color : transparent; -fx-padding : 5 15 5 15; -fx-cursor : hand;");
            }
        });

        button.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused) {
                button.setStyle(button.getStyle() + " -fx-border-color : " + PRIMARY_PINK + "; -fx-border-width : 2; -fx-border-radius : 20;");
            } else if (button != activeMenuButton) {
                button.setStyle("-fx-background-color : transparent; -fx-padding : 5 15 5 15; -fx-cursor : hand;");
            }
        });

        return button;
    }

    public void setSelectedMenuButton(Button selectedButton) {
        Button[] buttons = {
                dashboardButton,
                patientsButton,
                tripsButton,
                profileButton,
                settingsButton,
                helpButton
        };

        for (Button button : buttons) {
            HBox box = (HBox) button.getGraphic();
            Text iconText = (Text) box.getChildren().get(0);
            Text nameText = (Text) box.getChildren().get(1);

            if (button == selectedButton) {
                button.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-background-radius : 20; -fx-padding : 5 15 5 15; -fx-cursor : hand; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.35), 8, 0, 0, 2);");
                iconText.setStyle("-fx-font-size : 15px; -fx-fill : white;");
                nameText.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : white;");
            } else {
                button.setStyle("-fx-background-color : transparent; -fx-padding : 5 15 5 15; -fx-cursor : hand;");
                iconText.setStyle("-fx-font-size : 15px; -fx-fill : " + SECONDARY_TEXT + ";");
                nameText.setStyle("-fx-font-size : 12px; -fx-fill : " + SECONDARY_TEXT + ";");
            }
        }

        activeMenuButton = selectedButton;

        ScaleTransition pulse = new ScaleTransition(NurseAppSettings.dur(140), selectedButton);
        pulse.setFromX(0.96);
        pulse.setFromY(0.9);
        pulse.setToX(1);
        pulse.setToY(1);
        pulse.play();
    }

    public Button actionButton(String icon, String name) {
        Text iconText = new Text(icon);
        iconText.setStyle("-fx-font-size : 20px; -fx-fill : " + PRIMARY_PINK + ";");

        Text nameText = new Text(name);
        nameText.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        VBox box = new VBox(10, iconText, nameText);
        box.setAlignment(Pos.CENTER);

        Button button = new Button();
        button.setGraphic(box);
        button.setPrefWidth(155);
        button.setPrefHeight(95);
        button.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 8, 0, 0, 3); -fx-cursor : hand;");

        ScaleTransition scale = new ScaleTransition(NurseAppSettings.dur(150), button);

        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-border-color : " + PRIMARY_PINK + "; -fx-border-width : 1.5; -fx-border-radius : 14; -fx-background-radius : 14; -fx-cursor : hand;");
            scale.setToX(1.04);
            scale.setToY(1.04);
            scale.playFromStart();
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14; -fx-cursor : hand;");
            scale.setToX(1);
            scale.setToY(1);
            scale.playFromStart();
        });

        button.setOnMousePressed(e -> {
            scale.setToX(0.97);
            scale.setToY(0.97);
            scale.playFromStart();
        });

        button.setOnMouseReleased(e -> {
            scale.setToX(1.04);
            scale.setToY(1.04);
            scale.playFromStart();
        });

        button.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused) {
                button.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-border-color : " + PRIMARY_PINK + "; -fx-border-width : 2; -fx-border-radius : 14; -fx-background-radius : 14; -fx-cursor : hand;");
            } else {
                button.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14; -fx-cursor : hand;");
            }
        });

        return button;
    }

    public VBox buildUpdatesWidget() {
        double viewportWidth = 294;
        double viewportHeight = 240;

        VBox card = new VBox(14);
        card.setPrefWidth(330);
        card.setPadding(new Insets(18));
        card.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 10, 0, 0, 3);");

        Button notifTab = tabPill("🔔", "Notifications");
        Button missionTab = tabPill("📈", "Mission Progress");

        HBox tabRow = new HBox(8, notifTab, missionTab);
        tabRow.setAlignment(Pos.CENTER_LEFT);

        Region divider = new Region();
        divider.setPrefHeight(1);
        divider.setStyle("-fx-background-color : " + DIVIDER_COLOR + ";");

        VBox notificationsView = buildNotificationsView(viewportWidth);
        notificationsView.setPrefWidth(viewportWidth);
        notificationsView.setMaxWidth(viewportWidth);

        VBox missionView = buildMissionProgressView(viewportWidth);
        missionView.setPrefWidth(viewportWidth);
        missionView.setMaxWidth(viewportWidth);
        missionView.setTranslateX(viewportWidth);

        StackPane viewport = new StackPane(notificationsView, missionView);
        viewport.setPrefSize(viewportWidth, viewportHeight);
        viewport.setMaxSize(viewportWidth, viewportHeight);
        viewport.setAlignment(Pos.TOP_LEFT);

        Rectangle clip = new Rectangle(viewportWidth, viewportHeight);
        viewport.setClip(clip);

        boolean[] onMissionView = { false };

        setTabActive(notifTab, true);
        setTabActive(missionTab, false);

        notifTab.setOnAction(e -> {
            if (!onMissionView[0]) return;
            onMissionView[0] = false;
            setTabActive(notifTab, true);
            setTabActive(missionTab, false);
            slideViews(missionView, notificationsView, viewportWidth, false);
        });

        missionTab.setOnAction(e -> {
            if (onMissionView[0]) return;
            onMissionView[0] = true;
            setTabActive(notifTab, false);
            setTabActive(missionTab, true);
            slideViews(notificationsView, missionView, viewportWidth, true);
        });

        card.getChildren().addAll(tabRow, divider, viewport);

        return card;
    }

    public void slideViews(javafx.scene.Node outgoing, javafx.scene.Node incoming, double width, boolean movingToMap) {
        double outgoingTarget = movingToMap ? -width : width;

        TranslateTransition outAnim = new TranslateTransition(NurseAppSettings.dur(300), outgoing);
        outAnim.setToX(outgoingTarget);
        outAnim.setInterpolator(Interpolator.EASE_BOTH);

        incoming.setTranslateX(movingToMap ? width : -width);

        TranslateTransition inAnim = new TranslateTransition(NurseAppSettings.dur(300), incoming);
        inAnim.setToX(0);
        inAnim.setInterpolator(Interpolator.EASE_BOTH);

        new ParallelTransition(outAnim, inAnim).play();
    }

    public Button tabPill(String icon, String label) {
        Text iconText = new Text(icon);
        iconText.setStyle("-fx-font-size : 11px;");

        Text labelText = new Text(label);
        labelText.setStyle("-fx-font-size : 10px; -fx-font-weight : bold;");

        HBox box = new HBox(6, iconText, labelText);
        box.setAlignment(Pos.CENTER);

        Button button = new Button();
        button.setGraphic(box);
        button.setPrefHeight(30);
        button.setPadding(new Insets(0, 12, 0, 12));
        button.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-cursor : hand;");

        button.setOnMouseEntered(e -> {
            if (!button.getStyle().contains("dropshadow")) {
                button.setStyle("-fx-background-color : " + HOVER_BG + "; -fx-border-color : " + PRIMARY_PINK + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-cursor : hand;");
            }
        });

        button.setOnMouseExited(e -> {
            if (!button.getStyle().contains("dropshadow")) {
                button.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-cursor : hand;");
            }
        });

        return button;
    }

    public void setTabActive(Button button, boolean active) {
        HBox box = (HBox) button.getGraphic();
        Text labelText = (Text) box.getChildren().get(1);

        if (active) {
            button.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-border-color : " + PRIMARY_PINK + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-cursor : hand; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.30), 6, 0, 0, 2);");
            labelText.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : white;");
        } else {
            button.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-cursor : hand;");
            labelText.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : " + SECONDARY_TEXT + ";");
        }
    }

    public VBox buildNotificationsView(double width) {
        VBox view = new VBox(10);
        view.setPrefWidth(width);

        HBox headerRow = new HBox();
        headerRow.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("Notifications");
        title.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        Text markRead = new Text("Mark all as read");
        markRead.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + "; -fx-cursor : hand;");
        markRead.setOnMouseClicked(e -> System.out.println("Mark all as read clicked"));

        headerRow.getChildren().addAll(title, space, markRead);

        VBox list = new VBox(2);
        list.getChildren().addAll(
                notificationItem("Emergency Protocol Activated", "Emergency protocol has been activated.", "2 min ago", true),
                notificationItem("Patient Pickup Request", "New patient pickup request has been received.", "5 min ago", true),
                notificationItem("Patient Details Updated", "Patient information has been updated successfully.", "12 min ago", false),
                notificationItem("Trip Started", "Your assigned patient trip has started.", "20 min ago", false));

        ScrollPane scroll = new ScrollPane(list);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(190);
        scroll.setStyle("-fx-background-color : transparent; -fx-background : transparent; -fx-border-color : transparent;");

        view.getChildren().addAll(headerRow, scroll);

        return view;
    }

    public HBox notificationItem(String title, String description, String time, boolean unread) {
        HBox row = new HBox(8);
        row.setAlignment(Pos.TOP_LEFT);
        row.setPadding(new Insets(8, 6, 8, 6));
        row.setStyle("-fx-background-color : " + (unread ? VERY_PALE_PINK : "transparent") + "; -fx-background-radius : 8;");

        Circle dot = new Circle(3);
        dot.setFill(Color.web(unread ? PINK_DARK : "transparent"));

        VBox dotWrap = new VBox(dot);
        dotWrap.setAlignment(Pos.TOP_CENTER);
        dotWrap.setPadding(new Insets(4, 0, 0, 0));

        VBox textBox = new VBox(3);

        Text titleText = new Text(title);
        titleText.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text descText = new Text(description);
        descText.setStyle("-fx-font-size : 9px; -fx-fill : " + SECONDARY_TEXT + ";");
        descText.setWrappingWidth(225);

        Text timeText = new Text(time);
        timeText.setStyle("-fx-font-size : 8px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");

        textBox.getChildren().addAll(titleText, descText, timeText);
        row.getChildren().addAll(dotWrap, textBox);

        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-background-radius : 8;"));
        row.setOnMouseExited(e -> row.setStyle("-fx-background-color : " + (unread ? VERY_PALE_PINK : "transparent") + "; -fx-background-radius : 8;"));

        return row;
    }

    public VBox buildMissionProgressView(double width) {
        VBox view = new VBox(10);
        view.setPrefWidth(width);

        Text title = new Text("Mission Progress");
        title.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        VBox stepsList = new VBox(0);
        stepsList.getChildren().addAll(
                missionStep("Patient Request Received", "Central Dispatch", "10:42 AM", "completed", null, false),
                missionStep("Patient Details Received", "Patient information received successfully", "10:43 AM", "completed", null, false),
                missionStep("En Route to Patient", "ETA: 4 mins", "NOW", "current", "Traffic is heavy on the current route.", false),
                missionStep("Patient Pickup", "Waiting for patient pickup", "", "upcoming", null, false),
                missionStep("Returning to Hospital", "Patient transportation in progress", "", "upcoming", null, false),
                missionStep("Mission Completed", "Patient safely delivered", "", "upcoming", null, true));

        ScrollPane scroll = new ScrollPane(stepsList);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(200);
        scroll.setStyle("-fx-background-color : transparent; -fx-background : transparent; -fx-border-color : transparent;");

        view.getChildren().addAll(title, scroll);

        return view;
    }

    public HBox missionStep(String title, String subtitle, String time, String state, String infoMessage, boolean isLast) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.TOP_LEFT);

        VBox leftCol = new VBox(0);
        leftCol.setAlignment(Pos.TOP_CENTER);
        leftCol.setPrefWidth(20);

        StackPane checkpoint = new StackPane();
        checkpoint.setMinSize(18, 18);
        checkpoint.setPrefSize(18, 18);
        checkpoint.setMaxSize(18, 18);

        if (state.equals("completed")) {
            Circle circle = new Circle(9);
            circle.setFill(Color.web(PRIMARY_PINK));

            Text check = new Text("✓");
            check.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : white;");

            checkpoint.getChildren().addAll(circle, check);
        } else if (state.equals("current")) {
            Circle glow = new Circle(9);
            glow.setFill(Color.web(PRIMARY_PINK, 0.25));

            Circle circle = new Circle(6);
            circle.setFill(Color.web(PRIMARY_PINK));

            checkpoint.getChildren().addAll(glow, circle);

            ScaleTransition pulse = new ScaleTransition(NurseAppSettings.dur(900), glow);
            pulse.setFromX(1);
            pulse.setFromY(1);
            pulse.setToX(1.6);
            pulse.setToY(1.6);
            pulse.setAutoReverse(true);
            pulse.setCycleCount(ScaleTransition.INDEFINITE);
            pulse.play();
        } else {
            Circle circle = new Circle(8);
            circle.setFill(Color.WHITE);
            circle.setStroke(Color.web(BORDER_COLOR));
            circle.setStrokeWidth(2);

            checkpoint.getChildren().add(circle);
        }

        leftCol.getChildren().add(checkpoint);

        if (!isLast) {
            Region line = new Region();
            line.setPrefWidth(2);
            line.setPrefHeight(infoMessage != null ? 86 : 42);
            line.setStyle("-fx-background-color : " + (state.equals("upcoming") ? BORDER_COLOR : LIGHT_PINK) + ";");

            VBox lineWrap = new VBox(line);
            lineWrap.setAlignment(Pos.TOP_CENTER);
            lineWrap.setPadding(new Insets(5, 7, 7, 7));

            leftCol.getChildren().add(lineWrap);
        }

        VBox rightCol = new VBox(3);
        rightCol.setPadding(new Insets(0, 0, 14, 0));

        HBox titleRow = new HBox();
        titleRow.setAlignment(Pos.CENTER_LEFT);

        String titleColor = state.equals("upcoming") ? MUTED_TEXT : PRIMARY_TEXT;

        Text titleText = new Text(title);
        titleText.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + titleColor + ";");

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        titleRow.getChildren().addAll(titleText, space);

        if (!time.isEmpty()) {
            Text timeText = new Text(time);
            timeText.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + (state.equals("current") ? PRIMARY_PINK : MUTED_TEXT) + ";");
            titleRow.getChildren().add(timeText);
        }

        Text subtitleText = new Text(subtitle);
        subtitleText.setStyle("-fx-font-size : 9px; -fx-fill : " + (state.equals("upcoming") ? MUTED_TEXT : SECONDARY_TEXT) + ";");
        subtitleText.setWrappingWidth(190);

        rightCol.getChildren().addAll(titleRow, subtitleText);

        if (infoMessage != null) {
            Text infoText = new Text(infoMessage);
            infoText.setStyle("-fx-font-size : 8px; -fx-fill : " + SECONDARY_TEXT + ";");
            infoText.setWrappingWidth(175);

            StackPane infoBox = new StackPane(infoText);
            infoBox.setPadding(new Insets(8));
            infoBox.setAlignment(Pos.TOP_LEFT);
            infoBox.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-background-radius : 8;");

            VBox infoWrap = new VBox(infoBox);
            infoWrap.setPadding(new Insets(6, 0, 0, 0));

            rightCol.getChildren().add(infoWrap);
        }

        row.getChildren().addAll(leftCol, rightCol);

        return row;
    }

    public void getToDashboardPage() {
        setSelectedMenuButton(dashboardButton);
        root.setCenter(mainContent);
    }
}