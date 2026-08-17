// package com.kurukshetra.view.nurse;

// import javafx.animation.FadeTransition;
// import javafx.animation.ScaleTransition;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.ComboBox;
// import javafx.scene.control.TextField;
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

// public class NursePatientDetailsPage {

//     public BorderPane patientRoot;

//     public BorderPane getPatientDetailsPage(Runnable callBackDashboard) {

//         patientRoot = new BorderPane();
//         patientRoot.setStyle("-fx-background-color : #F7F9FC; -fx-font-family : 'Segoe UI';");

//         // ================= TOP BAR =================

//         HBox topBar = new HBox(14);
//         topBar.setAlignment(Pos.CENTER_LEFT);
//         topBar.setPadding(new Insets(18, 30, 12, 30));

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

//         VBox patientHeading = new VBox(3);

//         Text patientName = new Text("Alex Johnson");
//         patientName.setStyle("-fx-font-size : 22px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         HBox patientInfoRow = new HBox(6);
//         patientInfoRow.setAlignment(Pos.CENTER_LEFT);

//         Text patientInfo = new Text("34 yrs  •  Male");
//         patientInfo.setStyle("-fx-font-size : 11px; -fx-fill : #728096;");

//         Text allergyBadge = new Text("⚠ Allergy: Penicillin");
//         allergyBadge.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : #D71920; -fx-padding : 3 8 3 8;");

//         StackPane allergyWrap = new StackPane(allergyBadge);
//         allergyWrap.setStyle("-fx-background-color : #FDEBEC; -fx-background-radius : 10;");

//         patientInfoRow.getChildren().addAll(patientInfo, allergyWrap);

//         patientHeading.getChildren().addAll(patientName, patientInfoRow);

//         Region topSpace = new Region();
//         HBox.setHgrow(topSpace, Priority.ALWAYS);

//         Circle userCircle = new Circle(19);
//         userCircle.setFill(Color.web("#08A1E5"));

//         Text userIcon = new Text("S");
//         userIcon.setStyle("-fx-font-size : 13px; -fx-font-weight : bold; -fx-fill : white;");

//         StackPane userProfile = new StackPane(userCircle, userIcon);

//         topBar.getChildren().addAll(backButton, patientHeading, topSpace, userProfile);

//         // ================= MAIN CONTENT =================

//         VBox mainContent = new VBox(16);
//         mainContent.setPadding(new Insets(5, 30, 16, 30));

//         // ================= TOP CARDS =================

//         HBox topCards = new HBox(16);

//         // ================= PATIENT DETAILS =================

//         VBox patientCard = new VBox(12);

//         patientCard.setPrefWidth(660);
//         patientCard.setPrefHeight(215);
//         patientCard.setPadding(new Insets(18));

//         patientCard.setStyle("-fx-background-color : white; -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.10), 12, 0, 0, 4);");

//         Text patientTitle = new Text("♙  Patient Details");
//         patientTitle.setStyle("-fx-font-size : 15px; -fx-font-weight : bold; -fx-fill : #007FAE;");

//         HBox rowOne = new HBox(14);

//         VBox nameBox = inputBox("FULL NAME", "e.g. Alex Johnson");
//         VBox heightBox = inputBox("HEIGHT (CM)", "e.g. 175");

//         rowOne.getChildren().addAll(nameBox, heightBox);

//         HBox.setHgrow(nameBox, Priority.ALWAYS);
//         HBox.setHgrow(heightBox, Priority.ALWAYS);

//         HBox rowTwo = new HBox(14);

//         VBox ageBox = inputBox("AGE", "e.g. 34");
//         VBox bloodBox = bloodTypeBox();

//         rowTwo.getChildren().addAll(ageBox, bloodBox);

//         HBox.setHgrow(ageBox, Priority.ALWAYS);
//         HBox.setHgrow(bloodBox, Priority.ALWAYS);

//         patientCard.getChildren().addAll(patientTitle, rowOne, rowTwo);

//         // ================= VITALS =================

//         VBox vitalsCard = new VBox(12);

//         vitalsCard.setPrefWidth(335);
//         vitalsCard.setPrefHeight(215);
//         vitalsCard.setPadding(new Insets(18));

//         vitalsCard.setStyle("-fx-background-color : white; -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.10), 12, 0, 0, 4);");

//         Text vitalsTitle = new Text("▣  Latest Vitals");
//         vitalsTitle.setStyle("-fx-font-size : 15px; -fx-font-weight : bold; -fx-fill : #007FAE;");

//         HBox vitalRowOne = new HBox(10);

//         VBox heart = vitalBox("HEART RATE", "78", "bpm", "#FDEBEC", "#D71920");
//         VBox bloodPressure = vitalBox("BLOOD PRES.", "120/80", "", "#EAF7FD", "#08A1E5");

//         vitalRowOne.getChildren().addAll(heart, bloodPressure);

//         HBox vitalRowTwo = new HBox(10);

//         VBox spo2 = vitalBox("SPO2", "98", "%", "#E7FBF1", "#20B86A");
//         VBox temperature = vitalBox("TEMP", "98.6", "°F", "#EAF7FD", "#08A1E5");

//         vitalRowTwo.getChildren().addAll(spo2, temperature);

//         vitalsCard.getChildren().addAll(vitalsTitle, vitalRowOne, vitalRowTwo);

//         topCards.getChildren().addAll(patientCard, vitalsCard);

//         // ================= LOWER CARDS =================

//         HBox lowerCards = new HBox(16);

//         // ================= MEDICAL REPORTS =================

//         VBox reportsCard = new VBox(11);

//         reportsCard.setPrefWidth(660);
//         reportsCard.setPrefHeight(240);
//         reportsCard.setPadding(new Insets(18));

//         reportsCard.setStyle("-fx-background-color : white; -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.10), 12, 0, 0, 4);");

//         HBox reportsHeading = new HBox();
//         reportsHeading.setAlignment(Pos.CENTER_LEFT);

//         Text reportsTitle = new Text("♧  AI Medical Reports");
//         reportsTitle.setStyle("-fx-font-size : 15px; -fx-font-weight : bold; -fx-fill : #007FAE;");

//         Region reportSpace = new Region();
//         HBox.setHgrow(reportSpace, Priority.ALWAYS);

//         Button newReportButton = new Button("+ New");
//         newReportButton.setPrefHeight(26);
//         newReportButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 10px; -fx-font-weight : bold; -fx-background-radius : 13; -fx-cursor : hand;");

//         newReportButton.setOnAction(e ->
//                 System.out.println("New Medical Report button clicked")
//         );

//         newReportButton.setOnMouseEntered(e ->
//                 newReportButton.setStyle("-fx-background-color : #007FAE; -fx-text-fill : white; -fx-font-size : 10px; -fx-font-weight : bold; -fx-background-radius : 13; -fx-cursor : hand;")
//         );

//         newReportButton.setOnMouseExited(e ->
//                 newReportButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 10px; -fx-font-weight : bold; -fx-background-radius : 13; -fx-cursor : hand;")
//         );

//         reportsHeading.getChildren().addAll(reportsTitle, reportSpace, newReportButton);

//         VBox xrayReport = reportBox("Chest X-Ray Analysis", "Generated Today, 09:45 AM", "AI detected potential minor opacities in the lower left lobe.");

//         VBox bloodReport = reportBox("Comprehensive Blood Panel", "Generated Yesterday, 12:20 PM", "AI markers within normal ranges. No critical flags.");

//         reportsCard.getChildren().addAll(reportsHeading, xrayReport, bloodReport);

//         // ================= CLINICAL GALLERY =================

//         VBox galleryCard = new VBox(11);

//         galleryCard.setPrefWidth(335);
//         galleryCard.setPrefHeight(240);
//         galleryCard.setPadding(new Insets(18));

//         galleryCard.setStyle("-fx-background-color : white; -fx-border-color : #D8E1EB; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.10), 12, 0, 0, 4);");

//         HBox galleryHeading = new HBox();
//         galleryHeading.setAlignment(Pos.CENTER_LEFT);

//         Text galleryTitle = new Text("▣  Clinical Gallery");
//         galleryTitle.setStyle("-fx-font-size : 15px; -fx-font-weight : bold; -fx-fill : #007FAE;");

//         Region gallerySpace = new Region();
//         HBox.setHgrow(gallerySpace, Priority.ALWAYS);

//         Text galleryIcon = new Text("▧");
//         galleryIcon.setStyle("-fx-font-size : 15px; -fx-fill : #08A1E5;");

//         galleryHeading.getChildren().addAll(galleryTitle, gallerySpace, galleryIcon);

//         HBox galleryImages = new HBox(10);

//         StackPane imageOne = galleryImage("Wound\nImage", "#D99575");
//         StackPane imageTwo = galleryImage("Clinical\nPhoto", "#B8D6E3");
//         StackPane addImage = galleryImage("+\nADD PHOTO", "#EAF7FD");

//         addImage.setOnMouseClicked(e -> {
//             System.out.println("Add Photo button clicked");
//             NurseToast.show(NurseDashboardPage.appOverlay, "Photo upload coming soon", "info");
//         });

//         galleryImages.getChildren().addAll(imageOne, imageTwo, addImage);

//         galleryCard.getChildren().addAll(galleryHeading, galleryImages);

//         lowerCards.getChildren().addAll(reportsCard, galleryCard);

//         mainContent.getChildren().addAll(topCards, lowerCards);

//         // ================= SAVE BUTTON =================

//         Button saveButton = new Button("▣  Save Patient Details");

//         saveButton.setPrefWidth(200);
//         saveButton.setPrefHeight(44);

//         saveButton.setStyle("-fx-background-color : #007FAE; -fx-text-fill : white; -fx-font-size : 13px; -fx-font-weight : bold; -fx-background-radius : 12; -fx-cursor : hand;");

//         saveButton.setOnAction(e -> {
//             System.out.println("Save Patient Details button clicked");
//             NurseToast.show(NurseDashboardPage.appOverlay, "Patient details saved", "success");
//         });

//         saveButton.setOnMouseEntered(e ->
//                 saveButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 13px; -fx-font-weight : bold; -fx-background-radius : 12; -fx-cursor : hand; -fx-effect : dropshadow(gaussian, rgba(8,161,229,0.40), 10, 0, 0, 3);")
//         );

//         saveButton.setOnMouseExited(e ->
//                 saveButton.setStyle("-fx-background-color : #007FAE; -fx-text-fill : white; -fx-font-size : 13px; -fx-font-weight : bold; -fx-background-radius : 12; -fx-cursor : hand;")
//         );

//         HBox bottomBar = new HBox();

//         bottomBar.setAlignment(Pos.CENTER_RIGHT);
//         bottomBar.setPadding(new Insets(0, 30, 16, 30));
//         bottomBar.getChildren().add(saveButton);

//         patientRoot.setTop(topBar);
//         patientRoot.setCenter(mainContent);
//         patientRoot.setBottom(bottomBar);

//         // ================= PAGE ANIMATION =================

//         FadeTransition fade = new FadeTransition(NurseAppSettings.dur(450), patientRoot);

//         fade.setFromValue(0.3);
//         fade.setToValue(1);

//         fade.play();

//         return patientRoot;
//     }

//     // =========================================================
//     // INPUT BOX
//     // =========================================================

//     public VBox inputBox(String title, String hint) {

//         VBox box = new VBox(6);

//         Text label = new Text(title);
//         label.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : #728096;");

//         TextField field = new TextField();
//         field.setPromptText(hint);
//         field.setPrefHeight(36);

//         field.setStyle("-fx-background-color : #F0F4F9; -fx-background-radius : 8; -fx-border-color : transparent; -fx-border-radius : 8; -fx-font-size : 11px;");

//         field.setOnMouseEntered(e ->
//                 field.setStyle("-fx-background-color : #EAF7FD; -fx-background-radius : 8; -fx-border-color : #08A1E5; -fx-border-width : 1; -fx-border-radius : 8; -fx-font-size : 11px;")
//         );

//         field.setOnMouseExited(e ->
//                 field.setStyle("-fx-background-color : #F0F4F9; -fx-background-radius : 8; -fx-border-color : transparent; -fx-border-radius : 8; -fx-font-size : 11px;")
//         );

//         field.focusedProperty().addListener((obs, wasFocused, isFocused) -> {

//             if (isFocused) {

//                 field.setStyle("-fx-background-color : #EAF7FD; -fx-background-radius : 8; -fx-border-color : #08A1E5; -fx-border-width : 2; -fx-border-radius : 8; -fx-font-size : 11px;");

//             } else {

//                 field.setStyle("-fx-background-color : #F0F4F9; -fx-background-radius : 8; -fx-border-color : transparent; -fx-border-radius : 8; -fx-font-size : 11px;");
//             }
//         });

//         box.getChildren().addAll(label, field);

//         return box;
//     }

//     // =========================================================
//     // BLOOD TYPE
//     // =========================================================

//     public VBox bloodTypeBox() {

//         VBox box = new VBox(6);

//         Text label = new Text("BLOOD TYPE");
//         label.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : #728096;");

//         ComboBox<String> bloodType = new ComboBox<>();

//         bloodType.getItems().addAll("Select Type", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

//         bloodType.setValue("Select Type");
//         bloodType.setPrefHeight(36);
//         bloodType.setMaxWidth(Double.MAX_VALUE);

//         bloodType.setStyle("-fx-background-color : #F0F4F9; -fx-background-radius : 8; -fx-font-size : 11px;");

//         box.getChildren().addAll(label, bloodType);

//         return box;
//     }

//     // =========================================================
//     // VITAL BOX
//     // =========================================================

//     public VBox vitalBox(String title, String value, String unit, String bg, String accent) {

//         VBox box = new VBox(6);

//         box.setPrefWidth(148);
//         box.setPadding(new Insets(10));

//         box.setStyle("-fx-background-color : " + bg + "; -fx-background-radius : 10; -fx-cursor : hand;");

//         Text titleText = new Text(title);
//         titleText.setStyle("-fx-font-size : 8px; -fx-font-weight : bold; -fx-fill : #65718A;");

//         HBox valueBox = new HBox(4);
//         valueBox.setAlignment(Pos.BOTTOM_LEFT);

//         Text valueText = new Text(value);
//         valueText.setStyle("-fx-font-size : 17px; -fx-font-weight : bold; -fx-fill : " + accent + ";");

//         Text unitText = new Text(unit);
//         unitText.setStyle("-fx-font-size : 8px; -fx-fill : #68778D;");

//         valueBox.getChildren().addAll(valueText, unitText);

//         box.getChildren().addAll(titleText, valueBox);

//         ScaleTransition scale = new ScaleTransition(NurseAppSettings.dur(150), box);

//         box.setOnMouseEntered(e -> {
//             scale.setToX(1.04);
//             scale.setToY(1.04);
//             scale.playFromStart();
//         });

//         box.setOnMouseExited(e -> {
//             scale.setToX(1);
//             scale.setToY(1);
//             scale.playFromStart();
//         });

//         return box;
//     }

//     // =========================================================
//     // REPORT BOX
//     // =========================================================

//     public VBox reportBox(String title, String date, String description) {

//         VBox box = new VBox(5);

//         box.setPadding(new Insets(10));
//         box.setStyle("-fx-background-color : #F5F7FC; -fx-background-radius : 10;");

//         HBox heading = new HBox();
//         heading.setAlignment(Pos.CENTER_LEFT);

//         Text icon = new Text("◉");
//         icon.setStyle("-fx-font-size : 13px; -fx-fill : #08A1E5;");

//         VBox titleBox = new VBox(2);

//         Text reportTitle = new Text(title);
//         reportTitle.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Text reportDate = new Text(date);
//         reportDate.setStyle("-fx-font-size : 8px; -fx-fill : #8993A2;");

//         titleBox.getChildren().addAll(reportTitle, reportDate);

//         Region space = new Region();
//         HBox.setHgrow(space, Priority.ALWAYS);

//         Button viewButton = new Button("View Full");
//         viewButton.setPrefHeight(24);

//         viewButton.setStyle("-fx-background-color : transparent; -fx-border-color : #9AAAC0; -fx-border-radius : 12; -fx-text-fill : #536277; -fx-font-size : 9px; -fx-cursor : hand;");

//         viewButton.setOnAction(e ->
//                 System.out.println("View Full clicked: " + title)
//         );

//         viewButton.setOnMouseEntered(e ->
//                 viewButton.setStyle("-fx-background-color : #08A1E5; -fx-border-color : #08A1E5; -fx-border-radius : 12; -fx-text-fill : white; -fx-font-size : 9px; -fx-cursor : hand;")
//         );

//         viewButton.setOnMouseExited(e ->
//                 viewButton.setStyle("-fx-background-color : transparent; -fx-border-color : #9AAAC0; -fx-border-radius : 12; -fx-text-fill : #536277; -fx-font-size : 9px; -fx-cursor : hand;")
//         );

//         heading.getChildren().addAll(icon, titleBox, space, viewButton);

//         Text reportDescription = new Text(description);
//         reportDescription.setStyle("-fx-font-size : 8px; -fx-fill : #68778D;");

//         box.getChildren().addAll(heading, reportDescription);

//         box.setOnMouseEntered(e ->
//                 box.setStyle("-fx-background-color : #EAF7FD; -fx-background-radius : 10;")
//         );

//         box.setOnMouseExited(e ->
//                 box.setStyle("-fx-background-color : #F5F7FC; -fx-background-radius : 10;")
//         );

//         return box;
//     }

//     // =========================================================
//     // GALLERY IMAGE
//     // =========================================================

//     public StackPane galleryImage(String text, String color) {

//         Rectangle rectangle = new Rectangle(108, 100);

//         rectangle.setArcWidth(12);
//         rectangle.setArcHeight(12);
//         rectangle.setFill(Color.web(color));

//         Text imageText = new Text(text);
//         imageText.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : #526278; -fx-text-alignment : center;");

//         StackPane image = new StackPane(rectangle, imageText);
//         image.setStyle("-fx-cursor : hand;");

//         image.setOnMouseEntered(e -> {

//             ScaleTransition scale = new ScaleTransition(NurseAppSettings.dur(150), image);

//             scale.setToX(1.06);
//             scale.setToY(1.06);

//             scale.play();
//         });

//         image.setOnMouseExited(e -> {

//             ScaleTransition scale = new ScaleTransition(NurseAppSettings.dur(150), image);

//             scale.setToX(1);
//             scale.setToY(1);

//             scale.play();
//         });

//         return image;
//     }
// }



package com.kurukshetra.view.nurse;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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

public class NursePatientDetailsPage {

    // ================= COLOR PALETTE =================
    private static final String PRIMARY_PINK = "#E67593";
    private static final String PRIMARY_HOVER = "#D95F80";
    private static final String PINK_DARK = "#FF1493";
    private static final String VERY_LIGHT_PINK = "#FDEDF2";
    private static final String LIGHT_PINK = "#F9E0E8";
    private static final String PALE_PINK = "#FFF6F8";
    private static final String VERY_PALE_PINK = "#FFF9FA";
    private static final String PAGE_BG = "#FCF9FA";
    private static final String SURFACE = "#FFFFFF";
    private static final String SOFT_PINK = "#F5D1DC";
    private static final String PRIMARY_TEXT = "#2B2226";
    private static final String SECONDARY_TEXT = "#665960";
    private static final String MUTED_TEXT = "#94878D";
    private static final String BORDER_COLOR = "#EEDDE3";
    private static final String DIVIDER_COLOR = "#F3E8EC";

    public BorderPane patientRoot;

    public BorderPane getPatientDetailsPage(Runnable callBackDashboard) {

        patientRoot = new BorderPane();
        patientRoot.setStyle("-fx-background-color : " + PAGE_BG + "; -fx-font-family : 'Segoe UI';");

        // ================= TOP BAR =================

        HBox topBar = new HBox(14);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(18, 30, 12, 30));

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

        VBox patientHeading = new VBox(3);

        Text patientName = new Text("Alex Johnson");
        patientName.setStyle("-fx-font-size : 22px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        HBox patientInfoRow = new HBox(6);
        patientInfoRow.setAlignment(Pos.CENTER_LEFT);

        Text patientInfo = new Text("34 yrs  •  Male");
        patientInfo.setStyle("-fx-font-size : 11px; -fx-fill : " + SECONDARY_TEXT + ";");

        Text allergyBadge = new Text("⚠ Allergy: Penicillin");
        allergyBadge.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : #C94F4F; -fx-padding : 3 8 3 8;");

        StackPane allergyWrap = new StackPane(allergyBadge);
        allergyWrap.setStyle("-fx-background-color : #FCE8E7; -fx-background-radius : 10;");

        patientInfoRow.getChildren().addAll(patientInfo, allergyWrap);
        patientHeading.getChildren().addAll(patientName, patientInfoRow);

        Region topSpace = new Region();
        HBox.setHgrow(topSpace, Priority.ALWAYS);

        Circle userCircle = new Circle(19);
        userCircle.setFill(Color.web(PRIMARY_PINK));

        Text userIcon = new Text("S");
        userIcon.setStyle("-fx-font-size : 13px; -fx-font-weight : bold; -fx-fill : white;");

        StackPane userProfile = new StackPane(userCircle, userIcon);

        topBar.getChildren().addAll(backButton, patientHeading, topSpace, userProfile);

        // ================= MAIN CONTENT =================

        VBox mainContent = new VBox(16);
        mainContent.setPadding(new Insets(5, 30, 16, 30));

        // ================= TOP CARDS =================

        HBox topCards = new HBox(16);

        // ================= PATIENT DETAILS =================

        VBox patientCard = new VBox(12);
        patientCard.setPrefWidth(660);
        patientCard.setPrefHeight(215);
        patientCard.setPadding(new Insets(18));
        patientCard.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 12, 0, 0, 4);");

        Text patientTitle = new Text("♙  Patient Details");
        patientTitle.setStyle("-fx-font-size : 15px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");

        HBox rowOne = new HBox(14);
        VBox nameBox = inputBox("FULL NAME", "e.g. Alex Johnson");
        VBox heightBox = inputBox("HEIGHT (CM)", "e.g. 175");

        rowOne.getChildren().addAll(nameBox, heightBox);
        HBox.setHgrow(nameBox, Priority.ALWAYS);
        HBox.setHgrow(heightBox, Priority.ALWAYS);

        HBox rowTwo = new HBox(14);
        VBox ageBox = inputBox("AGE", "e.g. 34");
        VBox bloodBox = bloodTypeBox();

        rowTwo.getChildren().addAll(ageBox, bloodBox);
        HBox.setHgrow(ageBox, Priority.ALWAYS);
        HBox.setHgrow(bloodBox, Priority.ALWAYS);

        patientCard.getChildren().addAll(patientTitle, rowOne, rowTwo);

        // ================= VITALS =================

        VBox vitalsCard = new VBox(12);
        vitalsCard.setPrefWidth(335);
        vitalsCard.setPrefHeight(215);
        vitalsCard.setPadding(new Insets(18));
        vitalsCard.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 12, 0, 0, 4);");

        Text vitalsTitle = new Text("▣  Latest Vitals");
        vitalsTitle.setStyle("-fx-font-size : 15px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");

        HBox vitalRowOne = new HBox(10);
        VBox heart = vitalBox("HEART RATE", "78", "bpm", "#FCE8EC", "#C84D68");
        VBox bloodPressure = vitalBox("BLOOD PRES.", "120/80", "", VERY_LIGHT_PINK, PRIMARY_PINK);

        vitalRowOne.getChildren().addAll(heart, bloodPressure);

        HBox vitalRowTwo = new HBox(10);
        VBox spo2 = vitalBox("SPO2", "98", "%", "#E7F5ED", "#258052");
        VBox temperature = vitalBox("TEMP", "98.6", "°F", VERY_LIGHT_PINK, PRIMARY_PINK);

        vitalRowTwo.getChildren().addAll(spo2, temperature);
        vitalsCard.getChildren().addAll(vitalsTitle, vitalRowOne, vitalRowTwo);

        topCards.getChildren().addAll(patientCard, vitalsCard);

        // ================= LOWER CARDS =================

        HBox lowerCards = new HBox(16);

        // ================= MEDICAL REPORTS =================

        VBox reportsCard = new VBox(11);
        reportsCard.setPrefWidth(660);
        reportsCard.setPrefHeight(240);
        reportsCard.setPadding(new Insets(18));
        reportsCard.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 12, 0, 0, 4);");

        HBox reportsHeading = new HBox();
        reportsHeading.setAlignment(Pos.CENTER_LEFT);

        Text reportsTitle = new Text("♧  AI Medical Reports");
        reportsTitle.setStyle("-fx-font-size : 15px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");

        Region reportSpace = new Region();
        HBox.setHgrow(reportSpace, Priority.ALWAYS);

        Button newReportButton = new Button("+ New");
        newReportButton.setPrefHeight(26);
        newReportButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 10px; -fx-font-weight : bold; -fx-background-radius : 13; -fx-cursor : hand;");

        newReportButton.setOnAction(e -> System.out.println("New Medical Report button clicked"));

        newReportButton.setOnMouseEntered(e ->
                newReportButton.setStyle("-fx-background-color : " + PRIMARY_HOVER + "; -fx-text-fill : white; -fx-font-size : 10px; -fx-font-weight : bold; -fx-background-radius : 13; -fx-cursor : hand;")
        );

        newReportButton.setOnMouseExited(e ->
                newReportButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 10px; -fx-font-weight : bold; -fx-background-radius : 13; -fx-cursor : hand;")
        );

        reportsHeading.getChildren().addAll(reportsTitle, reportSpace, newReportButton);

        VBox xrayReport = reportBox("Chest X-Ray Analysis", "Generated Today, 09:45 AM", "AI detected potential minor opacities in the lower left lobe.");
        VBox bloodReport = reportBox("Comprehensive Blood Panel", "Generated Yesterday, 12:20 PM", "AI markers within normal ranges. No critical flags.");

        reportsCard.getChildren().addAll(reportsHeading, xrayReport, bloodReport);

        // ================= CLINICAL GALLERY =================

        VBox galleryCard = new VBox(11);
        galleryCard.setPrefWidth(335);
        galleryCard.setPrefHeight(240);
        galleryCard.setPadding(new Insets(18));
        galleryCard.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 12, 0, 0, 4);");

        HBox galleryHeading = new HBox();
        galleryHeading.setAlignment(Pos.CENTER_LEFT);

        Text galleryTitle = new Text("▣  Clinical Gallery");
        galleryTitle.setStyle("-fx-font-size : 15px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");

        Region gallerySpace = new Region();
        HBox.setHgrow(gallerySpace, Priority.ALWAYS);

        Text galleryIcon = new Text("▧");
        galleryIcon.setStyle("-fx-font-size : 15px; -fx-fill : " + PRIMARY_PINK + ";");

        galleryHeading.getChildren().addAll(galleryTitle, gallerySpace, galleryIcon);

        HBox galleryImages = new HBox(10);
        StackPane imageOne = galleryImage("Wound\nImage", "#F5D1DC");
        StackPane imageTwo = galleryImage("Clinical\nPhoto", "#EEDDE3");
        StackPane addImage = galleryImage("+\nADD PHOTO", VERY_LIGHT_PINK);

        addImage.setOnMouseClicked(e -> {
            System.out.println("Add Photo button clicked");
            NurseToast.show(NurseDashboardPage.appOverlay, "Photo upload coming soon", "info");
        });

        galleryImages.getChildren().addAll(imageOne, imageTwo, addImage);
        galleryCard.getChildren().addAll(galleryHeading, galleryImages);

        lowerCards.getChildren().addAll(reportsCard, galleryCard);
        mainContent.getChildren().addAll(topCards, lowerCards);

        // ================= SAVE BUTTON =================

        Button saveButton = new Button("▣  Save Patient Details");
        saveButton.setPrefWidth(200);
        saveButton.setPrefHeight(44);
        saveButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 13px; -fx-font-weight : bold; -fx-background-radius : 12; -fx-cursor : hand;");

        saveButton.setOnAction(e -> {
            System.out.println("Save Patient Details button clicked");
            NurseToast.show(NurseDashboardPage.appOverlay, "Patient details saved", "success");
        });

        saveButton.setOnMouseEntered(e ->
                saveButton.setStyle("-fx-background-color : " + PRIMARY_HOVER + "; -fx-text-fill : white; -fx-font-size : 13px; -fx-font-weight : bold; -fx-background-radius : 12; -fx-cursor : hand; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.35), 10, 0, 0, 3);")
        );

        saveButton.setOnMouseExited(e ->
                saveButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 13px; -fx-font-weight : bold; -fx-background-radius : 12; -fx-cursor : hand;")
        );

        HBox bottomBar = new HBox();
        bottomBar.setAlignment(Pos.CENTER_RIGHT);
        bottomBar.setPadding(new Insets(0, 30, 16, 30));
        bottomBar.getChildren().add(saveButton);

        patientRoot.setTop(topBar);
        patientRoot.setCenter(mainContent);
        patientRoot.setBottom(bottomBar);

        FadeTransition fade = new FadeTransition(NurseAppSettings.dur(450), patientRoot);
        fade.setFromValue(0.3);
        fade.setToValue(1);
        fade.play();

        return patientRoot;
    }

    public VBox inputBox(String title, String hint) {

        VBox box = new VBox(6);

        Text label = new Text(title);
        label.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + SECONDARY_TEXT + ";");

        TextField field = new TextField();
        field.setPromptText(hint);
        field.setPrefHeight(36);
        field.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 8; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 8; -fx-font-size : 11px; -fx-text-fill : " + PRIMARY_TEXT + ";");

        field.setOnMouseEntered(e -> {
            if (!field.isFocused()) {
                field.setStyle("-fx-background-color : " + VERY_PALE_PINK + "; -fx-background-radius : 8; -fx-border-color : " + SOFT_PINK + "; -fx-border-width : 1; -fx-border-radius : 8; -fx-font-size : 11px; -fx-text-fill : " + PRIMARY_TEXT + ";");
            }
        });

        field.setOnMouseExited(e -> {
            if (!field.isFocused()) {
                field.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 8; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 8; -fx-font-size : 11px; -fx-text-fill : " + PRIMARY_TEXT + ";");
            }
        });

        field.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused) {
                field.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 8; -fx-border-color : " + PRIMARY_PINK + "; -fx-border-width : 2; -fx-border-radius : 8; -fx-font-size : 11px; -fx-text-fill : " + PRIMARY_TEXT + ";");
            } else {
                field.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 8; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 8; -fx-font-size : 11px; -fx-text-fill : " + PRIMARY_TEXT + ";");
            }
        });

        box.getChildren().addAll(label, field);
        return box;
    }

    public VBox bloodTypeBox() {

        VBox box = new VBox(6);

        Text label = new Text("BLOOD TYPE");
        label.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + SECONDARY_TEXT + ";");

        ComboBox<String> bloodType = new ComboBox<>();
        bloodType.getItems().addAll("Select Type", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        bloodType.setValue("Select Type");
        bloodType.setPrefHeight(36);
        bloodType.setMaxWidth(Double.MAX_VALUE);
        bloodType.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 8; -fx-background-radius : 8; -fx-font-size : 11px;");

        box.getChildren().addAll(label, bloodType);
        return box;
    }

    public VBox vitalBox(String title, String value, String unit, String bg, String accent) {

        VBox box = new VBox(6);
        box.setPrefWidth(148);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color : " + bg + "; -fx-background-radius : 10; -fx-cursor : hand;");

        Text titleText = new Text(title);
        titleText.setStyle("-fx-font-size : 8px; -fx-font-weight : bold; -fx-fill : " + SECONDARY_TEXT + ";");

        HBox valueBox = new HBox(4);
        valueBox.setAlignment(Pos.BOTTOM_LEFT);

        Text valueText = new Text(value);
        valueText.setStyle("-fx-font-size : 17px; -fx-font-weight : bold; -fx-fill : " + accent + ";");

        Text unitText = new Text(unit);
        unitText.setStyle("-fx-font-size : 8px; -fx-fill : " + MUTED_TEXT + ";");

        valueBox.getChildren().addAll(valueText, unitText);
        box.getChildren().addAll(titleText, valueBox);

        ScaleTransition scale = new ScaleTransition(NurseAppSettings.dur(150), box);

        box.setOnMouseEntered(e -> {
            scale.setToX(1.04);
            scale.setToY(1.04);
            scale.playFromStart();
        });

        box.setOnMouseExited(e -> {
            scale.setToX(1);
            scale.setToY(1);
            scale.playFromStart();
        });

        return box;
    }

    public VBox reportBox(String title, String date, String description) {

        VBox box = new VBox(5);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color : " + VERY_PALE_PINK + "; -fx-border-color : " + DIVIDER_COLOR + "; -fx-border-radius : 10; -fx-background-radius : 10;");

        HBox heading = new HBox();
        heading.setAlignment(Pos.CENTER_LEFT);

        Text icon = new Text("◉");
        icon.setStyle("-fx-font-size : 13px; -fx-fill : " + PRIMARY_PINK + ";");

        VBox titleBox = new VBox(2);
        Text reportTitle = new Text(title);
        reportTitle.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text reportDate = new Text(date);
        reportDate.setStyle("-fx-font-size : 8px; -fx-fill : " + MUTED_TEXT + ";");

        titleBox.getChildren().addAll(reportTitle, reportDate);

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        Button viewButton = new Button("View Full");
        viewButton.setPrefHeight(24);
        viewButton.setStyle("-fx-background-color : transparent; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 12; -fx-text-fill : " + SECONDARY_TEXT + "; -fx-font-size : 9px; -fx-cursor : hand;");

        viewButton.setOnAction(e -> System.out.println("View Full clicked: " + title));

        viewButton.setOnMouseEntered(e ->
                viewButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-border-color : " + PRIMARY_PINK + "; -fx-border-radius : 12; -fx-text-fill : white; -fx-font-size : 9px; -fx-cursor : hand;")
        );

        viewButton.setOnMouseExited(e ->
                viewButton.setStyle("-fx-background-color : transparent; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 12; -fx-text-fill : " + SECONDARY_TEXT + "; -fx-font-size : 9px; -fx-cursor : hand;")
        );

        heading.getChildren().addAll(icon, titleBox, space, viewButton);

        Text reportDescription = new Text(description);
        reportDescription.setStyle("-fx-font-size : 8px; -fx-fill : " + SECONDARY_TEXT + ";");

        box.getChildren().addAll(heading, reportDescription);

        box.setOnMouseEntered(e ->
                box.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-border-color : " + SOFT_PINK + "; -fx-border-radius : 10; -fx-background-radius : 10;")
        );

        box.setOnMouseExited(e ->
                box.setStyle("-fx-background-color : " + VERY_PALE_PINK + "; -fx-border-color : " + DIVIDER_COLOR + "; -fx-border-radius : 10; -fx-background-radius : 10;")
        );

        return box;
    }

    public StackPane galleryImage(String text, String color) {

        Rectangle rectangle = new Rectangle(108, 100);
        rectangle.setArcWidth(12);
        rectangle.setArcHeight(12);
        rectangle.setFill(Color.web(color));

        Text imageText = new Text(text);
        imageText.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + "; -fx-text-alignment : center;");

        StackPane image = new StackPane(rectangle, imageText);
        image.setStyle("-fx-cursor : hand;");

        image.setOnMouseEntered(e -> {
            ScaleTransition scale = new ScaleTransition(NurseAppSettings.dur(150), image);
            scale.setToX(1.06);
            scale.setToY(1.06);
            scale.play();
        });

        image.setOnMouseExited(e -> {
            ScaleTransition scale = new ScaleTransition(NurseAppSettings.dur(150), image);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();
        });

        return image;
    }
}