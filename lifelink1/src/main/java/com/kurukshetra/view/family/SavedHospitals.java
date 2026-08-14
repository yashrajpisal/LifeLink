// package com.kurukshetra.view.family;

// import javafx.application.Application;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Cursor;
// import javafx.scene.Scene;
// import javafx.scene.control.*;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.*;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Circle;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.scene.web.WebEngine;
// import javafx.scene.web.WebView;
// import javafx.stage.Stage;

// import java.net.URL;

// public class SavedHospitals extends Application {

//     // =========================================================
//     // COLORS
//     // =========================================================

//     private static final String BACKGROUND = "#F5F5FD";
//     private static final String SIDEBAR = "#FBFBFE";

//     private static final String DARK_TEXT = "#252A3A";
//     private static final String MUTED = "#777C8D";

//     private static final String PURPLE = "#6254B8";
//     private static final String LIGHT_PURPLE = "#ECEAF9";

//     private static final String BLUE = "#BFE4F8";

//     private static final String RED = "#E62F39";

//     private static final String GREEN = "#35A96B";

//     private static final String BORDER = "#E4E5EF";

//     private static final String TEAL = "#00566F";

//     private WebEngine mapEngine;

//     // =========================================================
//     // START
//     // =========================================================

//     @Override
//     public void start(Stage stage) {

//         BorderPane root = new BorderPane();

//         root.setStyle(
//                 "-fx-background-color: " + BACKGROUND + ";"
//         );

//         // -----------------------------------------------------
//         // SIDEBAR
//         // -----------------------------------------------------

//         VBox sidebar = createSidebar();

//         root.setLeft(sidebar);

//         // -----------------------------------------------------
//         // MAIN CONTENT
//         // -----------------------------------------------------

//         VBox main = new VBox(18);

//         main.setPadding(
//                 new Insets(
//                         25,
//                         25,
//                         25,
//                         28
//                 )
//         );

//         main.setFillWidth(true);

//         // -----------------------------------------------------
//         // HEADER
//         // -----------------------------------------------------

//         VBox heading = createHeading();

//         // -----------------------------------------------------
//         // MAIN HOSPITAL AREA
//         // -----------------------------------------------------

//         HBox mainHospital = createMainHospital();

//         // -----------------------------------------------------
//         // SMALL HOSPITAL CARDS
//         // -----------------------------------------------------

//         HBox savedHospitals = new HBox(
//                 16
//         );

//         VBox oakridge =
//                 createSmallHospital(
//                         "Oakridge Specialized",
//                         "Clinic",
//                         "5.8 mi",
//                         "▣"
//                 );

//         VBox valley =
//                 createSmallHospital(
//                         "Valley Pediatric Center",
//                         "",
//                         "9.2 mi",
//                         "♙"
//                 );

//         HBox.setHgrow(
//                 oakridge,
//                 Priority.ALWAYS
//         );

//         HBox.setHgrow(
//                 valley,
//                 Priority.ALWAYS
//         );

//         savedHospitals.getChildren().addAll(
//                 oakridge,
//                 valley
//         );

//         // -----------------------------------------------------
//         // ADD
//         // -----------------------------------------------------

//         main.getChildren().addAll(
//                 heading,
//                 mainHospital,
//                 savedHospitals
//         );

//         // -----------------------------------------------------
//         // SCROLL
//         // -----------------------------------------------------

//         ScrollPane scrollPane =
//                 new ScrollPane(main);

//         scrollPane.setFitToWidth(true);

//         scrollPane.setHbarPolicy(
//                 ScrollPane.ScrollBarPolicy.NEVER
//         );

//         scrollPane.setVbarPolicy(
//                 ScrollPane.ScrollBarPolicy.AS_NEEDED
//         );

//         scrollPane.setStyle(
//                 "-fx-background-color: transparent;" +
//                 "-fx-border-color: transparent;"
//         );

//         root.setCenter(
//                 scrollPane
//         );

//         // -----------------------------------------------------
//         // SCENE
//         // -----------------------------------------------------

//         Scene scene =
//                 new Scene(
//                         root,
//                         1160,
//                         700
//                 );

//         stage.setTitle(
//                 "LifeLink - Saved Hospitals"
//         );

//         stage.setMinWidth(
//                 950
//         );

//         stage.setMinHeight(
//                 600
//         );

//         stage.setScene(
//                 scene
//         );

//         stage.show();
//     }

//     // =========================================================
//     // SIDEBAR
//     // =========================================================

//     private VBox createSidebar() {

//         VBox sidebar =
//                 new VBox(7);

//         sidebar.setPrefWidth(
//                 190
//         );

//         sidebar.setMinWidth(
//                 190
//         );

//         sidebar.setPadding(
//                 new Insets(
//                         16,
//                         10,
//                         10,
//                         10
//                 )
//         );

//         sidebar.setStyle(
//                 "-fx-background-color: " + SIDEBAR + ";" +
//                 "-fx-border-color: transparent #E6E7F0 transparent transparent;"
//         );

//         // -----------------------------------------------------
//         // LOGO
//         // -----------------------------------------------------

//         Label logo =
//                 new Label(
//                         "LifeLink"
//                 );

//         logo.setFont(
//                 Font.font(
//                         "Arial",
//                         FontWeight.BOLD,
//                         17
//                 )
//         );

//         logo.setTextFill(
//                 Color.web("#17203A")
//         );

//         sidebar.getChildren().add(
//                 logo
//         );

//         // -----------------------------------------------------
//         // PROFILE
//         // -----------------------------------------------------

//         HBox profile =
//                 new HBox(8);

//         profile.setAlignment(
//                 Pos.CENTER_LEFT
//         );

//         Circle avatar =
//                 new Circle(
//                         15,
//                         Color.web("#D9E4F1")
//                 );

//         Label avatarLetter =
//                 new Label(
//                         "S"
//                 );

//         avatarLetter.setTextFill(
//                 Color.web("#4D5A70")
//         );

//         avatarLetter.setFont(
//                 Font.font(
//                         "Arial",
//                         FontWeight.BOLD,
//                         9
//                 )
//         );

//         StackPane avatarBox =
//                 new StackPane(
//                         avatar,
//                         avatarLetter
//                 );

//         VBox profileText =
//                 new VBox(1);

//         Label name =
//                 new Label(
//                         "Sarah Miller"
//                 );

//         name.setStyle(
//                 "-fx-font-size: 8px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-text-fill: #34394A;"
//         );

//         Label role =
//                 new Label(
//                         "Family Care Lead"
//                 );

//         role.setStyle(
//                 "-fx-font-size: 7px;" +
//                 "-fx-text-fill: #7B8090;"
//         );

//         profileText.getChildren().addAll(
//                 name,
//                 role
//         );

//         profile.getChildren().addAll(
//                 avatarBox,
//                 profileText
//         );

//         sidebar.getChildren().add(
//                 profile
//         );

//         Region profileSpacer =
//                 new Region();

//         profileSpacer.setPrefHeight(
//                 12
//         );

//         sidebar.getChildren().add(
//                 profileSpacer
//         );

//         // -----------------------------------------------------
//         // NAVIGATION
//         // -----------------------------------------------------

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "⌂",
//                         "Dashboard",
//                         false
//                 )
//         );

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "⊞",
//                         "Find Hospitals",
//                         false
//                 )
//         );

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "✚",
//                         "Emergency Services",
//                         false
//                 )
//         );

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "✚",
//                         "First-Aid Assistant",
//                         false
//                 )
//         );

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "▣",
//                         "Appointments",
//                         false
//                 )
//         );

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "◴",
//                         "Medical History",
//                         false
//                 )
//         );

//         // -----------------------------------------------------
//         // SAVED HOSPITALS SELECTED
//         // -----------------------------------------------------

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "▣",
//                         "Saved Hospitals",
//                         true
//                 )
//         );

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "⚙",
//                         "Settings",
//                         false
//                 )
//         );

//         // -----------------------------------------------------
//         // SPACER
//         // -----------------------------------------------------

//         Region spacer =
//                 new Region();

//         VBox.setVgrow(
//                 spacer,
//                 Priority.ALWAYS
//         );

//         sidebar.getChildren().add(
//                 spacer
//         );

//         // -----------------------------------------------------
//         // EMERGENCY HELP
//         // -----------------------------------------------------

//         Button emergency =
//                 new Button(
//                         "✚  Emergency Help"
//                 );

//         emergency.setMaxWidth(
//                 Double.MAX_VALUE
//         );

//         emergency.setPrefHeight(
//                 30
//         );

//         emergency.setCursor(
//                 Cursor.HAND
//         );

//         emergency.setStyle(
//                 "-fx-background-color: " + RED + ";" +
//                 "-fx-background-radius: 7;" +
//                 "-fx-text-fill: white;" +
//                 "-fx-font-size: 9px;" +
//                 "-fx-font-weight: bold;"
//         );

//         emergency.setOnAction(
//                 event -> {

//                     Alert alert =
//                             new Alert(
//                                     Alert.AlertType.INFORMATION
//                             );

//                     alert.setTitle(
//                             "Emergency Help"
//                     );

//                     alert.setHeaderText(
//                             "Emergency Help"
//                     );

//                     alert.setContentText(
//                             "Emergency Help action selected."
//                     );

//                     alert.showAndWait();
//                 }
//         );

//         sidebar.getChildren().add(
//                 emergency
//         );

//         // -----------------------------------------------------
//         // LOGOUT
//         // -----------------------------------------------------

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "↪",
//                         "Logout",
//                         false
//                 )
//         );

//         return sidebar;
//     }

//     // =========================================================
//     // NAVIGATION ITEM
//     // =========================================================

//     private HBox navigationItem(
//             String icon,
//             String text,
//             boolean selected
//     ) {

//         HBox item =
//                 new HBox(8);

//         item.setAlignment(
//                 Pos.CENTER_LEFT
//         );

//         item.setPadding(
//                 new Insets(
//                         7,
//                         7,
//                         7,
//                         7
//                 )
//         );

//         item.setMaxWidth(
//                 Double.MAX_VALUE
//         );

//         item.setCursor(
//                 Cursor.HAND
//         );

//         Label iconLabel =
//                 new Label(
//                         icon
//                 );

//         iconLabel.setFont(
//                 Font.font(
//                         "Arial",
//                         10
//                 )
//         );

//         Label textLabel =
//                 new Label(
//                         text
//                 );

//         textLabel.setFont(
//                 Font.font(
//                         "Arial",
//                         selected ? FontWeight.BOLD : FontWeight.NORMAL,
//                         8
//                 )
//         );

//         if (selected) {

//             item.setStyle(
//                     "-fx-background-color: " +
//                     LIGHT_PURPLE +
//                     ";" +
//                     "-fx-background-radius: 6;"
//             );

//             iconLabel.setTextFill(
//                     Color.web(TEAL)
//             );

//             textLabel.setTextFill(
//                     Color.web(TEAL)
//             );

//         } else {

//             iconLabel.setTextFill(
//                     Color.web("#555A6A")
//             );

//             textLabel.setTextFill(
//                     Color.web("#555A6A")
//             );
//         }

//         item.getChildren().addAll(
//                 iconLabel,
//                 textLabel
//         );

//         // -----------------------------------------------------
//         // HOVER
//         // -----------------------------------------------------

//         item.setOnMouseEntered(
//                 event -> {

//                     if (!selected) {

//                         item.setStyle(
//                                 "-fx-background-color: #F0F0F7;" +
//                                 "-fx-background-radius: 6;"
//                         );
//                     }
//                 }
//         );

//         item.setOnMouseExited(
//                 event -> {

//                     if (!selected) {

//                         item.setStyle(
//                                 "-fx-background-color: transparent;"
//                         );
//                     }
//                 }
//         );

//         return item;
//     }

//     // =========================================================
//     // PAGE HEADING
//     // =========================================================

//     private VBox createHeading() {

//         VBox heading =
//                 new VBox(3);

//         Label title =
//                 new Label(
//                         "Saved Hospitals"
//                 );

//         title.setFont(
//                 Font.font(
//                         "Arial",
//                         FontWeight.BOLD,
//                         18
//                 )
//         );

//         title.setTextFill(
//                 Color.web(DARK_TEXT)
//         );

//         Label subtitle =
//                 new Label(
//                         "Quick access to your preferred healthcare facilities and emergency contacts."
//                 );

//         subtitle.setStyle(
//                 "-fx-font-size: 8px;" +
//                 "-fx-text-fill: " + MUTED + ";"
//         );

//         heading.getChildren().addAll(
//                 title,
//                 subtitle
//         );

//         return heading;
//     }

//     // =========================================================
//     // MAIN HOSPITAL CARD
//     // =========================================================

//     private HBox createMainHospital() {

//         HBox container =
//                 new HBox(12);

//         container.setPrefHeight(
//                 130
//         );

//         // -----------------------------------------------------
//         // HOSPITAL INFORMATION
//         // -----------------------------------------------------

//         VBox hospitalCard =
//                 new VBox(7);

//         hospitalCard.setPadding(
//                 new Insets(10)
//         );

//         hospitalCard.setPrefWidth(
//                 550
//         );

//         hospitalCard.setStyle(
//                 "-fx-background-color: white;" +
//                 "-fx-background-radius: 9;" +
//                 "-fx-border-color: " + BORDER + ";" +
//                 "-fx-border-radius: 9;" +
//                 "-fx-effect: dropshadow(" +
//                 "gaussian," +
//                 "rgba(30,40,70,0.06)," +
//                 "9,0,0,2);"
//         );

//         HBox.setHgrow(
//                 hospitalCard,
//                 Priority.ALWAYS
//         );

//         HBox hospitalContent =
//                 new HBox(12);

//         // -----------------------------------------------------
//         // HOSPITAL IMAGE
//         // -----------------------------------------------------

//         ImageView hospitalImage =
//                 createHospitalImage();

//         hospitalContent.getChildren().add(
//                 hospitalImage
//         );

//         // -----------------------------------------------------
//         // DETAILS
//         // -----------------------------------------------------

//         VBox details =
//                 new VBox(5);

//         HBox titleRow =
//                 new HBox(7);

//         Label hospitalName =
//                 new Label(
//                         "Mercy General Hospital"
//                 );

//         hospitalName.setFont(
//                 Font.font(
//                         "Arial",
//                         FontWeight.BOLD,
//                         11
//                 )
//         );

//         hospitalName.setTextFill(
//                 Color.web(DARK_TEXT)
//         );

//         Label primary =
//                 new Label(
//                         "★ Primary"
//                 );

//         primary.setStyle(
//                 "-fx-background-color: #E9F3F8;" +
//                 "-fx-background-radius: 10;" +
//                 "-fx-padding: 3 7;" +
//                 "-fx-text-fill: #24617A;" +
//                 "-fx-font-size: 7px;" +
//                 "-fx-font-weight: bold;"
//         );

//         titleRow.getChildren().addAll(
//                 hospitalName,
//                 primary
//         );

//         Label address =
//                 new Label(
//                         "⌖ 1240 Wellness Blvd, Springfield"
//                 );

//         address.setStyle(
//                 "-fx-font-size: 7px;" +
//                 "-fx-text-fill: " + MUTED + ";"
//         );

//         HBox badges =
//                 new HBox(5);

//         badges.getChildren().addAll(
//                 badge(
//                         "Level 1 Trauma",
//                         "#E6ECFA",
//                         "#4D5F8D"
//                 ),
//                 badge(
//                         "Pediatric Care",
//                         "#E8F1FC",
//                         "#50709A"
//                 ),
//                 badge(
//                         "● ER Open",
//                         "#E1F5E9",
//                         "#3A8661"
//                 )
//         );

//         Region verticalSpacer =
//                 new Region();

//         VBox.setVgrow(
//                 verticalSpacer,
//                 Priority.ALWAYS
//         );

//         // -----------------------------------------------------
//         // BUTTONS
//         // -----------------------------------------------------

//         HBox buttons =
//                 new HBox(8);

//         Button route =
//                 smallButton(
//                         "◆  Route",
//                         TEAL
//                 );

//         Button call =
//                 smallButton(
//                         "☎  Call",
//                         "#B9E2F7"
//                 );

//         call.setTextFill(
//                 Color.web("#255D79")
//         );

//         route.setOnAction(
//                 event -> showRoute()
//         );

//         call.setOnAction(
//                 event -> {

//                     Alert alert =
//                             new Alert(
//                                     Alert.AlertType.INFORMATION
//                             );

//                     alert.setTitle(
//                             "Call Hospital"
//                     );

//                     alert.setHeaderText(
//                             "Mercy General Hospital"
//                     );

//                     alert.setContentText(
//                             "Calling (555) 245-8800...\n\n" +
//                             "This is a UI prototype, so no real call will be placed."
//                     );

//                     alert.showAndWait();
//                 }
//         );

//         buttons.getChildren().addAll(
//                 route,
//                 call
//         );

//         details.getChildren().addAll(
//                 titleRow,
//                 address,
//                 badges,
//                 verticalSpacer,
//                 buttons
//         );

//         hospitalContent.getChildren().add(
//                 details
//         );

//         hospitalCard.getChildren().add(
//                 hospitalContent
//         );

//         // -----------------------------------------------------
//         // MAP
//         // -----------------------------------------------------

//         VBox mapContainer =
//                 createMapContainer();

//         mapContainer.setPrefWidth(
//                 330
//         );

//         HBox.setHgrow(
//                 mapContainer,
//                 Priority.ALWAYS
//         );

//         container.getChildren().addAll(
//                 hospitalCard,
//                 mapContainer
//         );

//         return container;
//     }

//     // =========================================================
//     // HOSPITAL IMAGE
//     // =========================================================

//     private ImageView createHospitalImage() {

//         String imageURL =
//                 "https://images.unsplash.com/photo-1587351021759-3e566b6af7cc" +
//                 "?auto=format&fit=crop&w=400&q=80";

//         Image image =
//                 new Image(
//                         imageURL,
//                         115,
//                         90,
//                         false,
//                         true,
//                         true
//                 );

//         ImageView imageView =
//                 new ImageView(
//                         image
//                 );

//         imageView.setFitWidth(
//                 115
//         );

//         imageView.setFitHeight(
//                 90
//         );

//         imageView.setPreserveRatio(
//                 false
//         );

//         imageView.setSmooth(
//                 true
//         );

//         return imageView;
//     }

//     // =========================================================
//     // MAP
//     // =========================================================

//     private VBox createMapContainer() {

//         VBox container =
//                 new VBox();

//         container.setStyle(
//                 "-fx-background-color: white;" +
//                 "-fx-background-radius: 9;" +
//                 "-fx-border-color: " + BORDER + ";" +
//                 "-fx-border-radius: 9;" +
//                 "-fx-effect: dropshadow(" +
//                 "gaussian," +
//                 "rgba(30,40,70,0.06)," +
//                 "9,0,0,2);"
//         );

//         WebView webView =
//                 new WebView();

//         webView.setPrefHeight(
//                 130
//         );

//         webView.setContextMenuEnabled(
//                 false
//         );

//         mapEngine =
//                 webView.getEngine();

//         URL mapURL =
//                 getClass().getResource(
//                         "/map.html"
//                 );

//         if (mapURL != null) {

//             mapEngine.load(
//                     mapURL.toExternalForm()
//             );

//         }

//         VBox.setVgrow(
//                 webView,
//                 Priority.ALWAYS
//         );

//         container.getChildren().add(
//                 webView
//         );

//         return container;
//     }

//     // =========================================================
//     // SMALL HOSPITAL
//     // =========================================================

//     private VBox createSmallHospital(
//             String line1,
//             String line2,
//             String distance,
//             String icon
//     ) {

//         VBox card =
//                 new VBox();

//         card.setPadding(
//                 new Insets(10)
//         );

//         card.setPrefHeight(
//                 78
//         );

//         card.setStyle(
//                 "-fx-background-color: white;" +
//                 "-fx-background-radius: 9;" +
//                 "-fx-border-color: " + BORDER + ";" +
//                 "-fx-border-radius: 9;" +
//                 "-fx-effect: dropshadow(" +
//                 "gaussian," +
//                 "rgba(30,40,70,0.05)," +
//                 "8,0,0,2);"
//         );

//         HBox content =
//                 new HBox(10);

//         content.setAlignment(
//                 Pos.CENTER_LEFT
//         );

//         // -----------------------------------------------------
//         // ICON BOX
//         // -----------------------------------------------------

//         StackPane iconBox =
//                 new StackPane();

//         iconBox.setMinSize(
//                 42,
//                 42
//         );

//         iconBox.setMaxSize(
//                 42,
//                 42
//         );

//         iconBox.setStyle(
//                 "-fx-background-color: #EFF1FB;" +
//                 "-fx-background-radius: 6;"
//         );

//         Label iconLabel =
//                 new Label(
//                         icon
//                 );

//         iconLabel.setStyle(
//                 "-fx-text-fill: #7392B3;" +
//                 "-fx-font-size: 16px;"
//         );

//         iconBox.getChildren().add(
//                 iconLabel
//         );

//         // -----------------------------------------------------
//         // INFORMATION
//         // -----------------------------------------------------

//         VBox information =
//                 new VBox(2);

//         Label hospitalName =
//                 new Label(
//                         line1
//                 );

//         hospitalName.setStyle(
//                 "-fx-text-fill: " + DARK_TEXT + ";" +
//                 "-fx-font-size: 10px;" +
//                 "-fx-font-weight: bold;"
//         );

//         Label second =
//                 new Label(
//                         line2
//                 );

//         second.setStyle(
//                 "-fx-text-fill: " + DARK_TEXT + ";" +
//                 "-fx-font-size: 10px;" +
//                 "-fx-font-weight: bold;"
//         );

//         Label distanceLabel =
//                 new Label(
//                         "⌖ " + distance
//                 );

//         distanceLabel.setStyle(
//                 "-fx-text-fill: " + MUTED + ";" +
//                 "-fx-font-size: 7px;"
//         );

//         information.getChildren().add(
//                 hospitalName
//         );

//         if (!line2.isEmpty()) {

//             information.getChildren().add(
//                     second
//             );
//         }

//         information.getChildren().add(
//                 distanceLabel
//         );

//         // -----------------------------------------------------
//         // BOOKMARK
//         // -----------------------------------------------------

//         Region spacer =
//                 new Region();

//         HBox.setHgrow(
//                 spacer,
//                 Priority.ALWAYS
//         );

//         Label bookmark =
//                 new Label(
//                         "▮"
//                 );

//         bookmark.setStyle(
//                 "-fx-text-fill: #00566F;" +
//                 "-fx-font-size: 12px;"
//         );

//         content.getChildren().addAll(
//                 iconBox,
//                 information,
//                 spacer,
//                 bookmark
//         );

//         // -----------------------------------------------------
//         // BUTTONS
//         // -----------------------------------------------------

//         HBox buttons =
//                 new HBox(5);

//         buttons.setPadding(
//                 new Insets(
//                         2,
//                         0,
//                         0,
//                         52
//                 )
//         );

//         Button route =
//                 new Button(
//                         "◆ Route"
//                 );

//         route.setStyle(
//                 "-fx-background-color: #C9E9F8;" +
//                 "-fx-background-radius: 8;" +
//                 "-fx-text-fill: #28647F;" +
//                 "-fx-font-size: 7px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-padding: 3 8;"
//         );

//         Button phone =
//                 new Button(
//                         "☎"
//                 );

//         phone.setStyle(
//                 "-fx-background-color: #F0F1F7;" +
//                 "-fx-background-radius: 8;" +
//                 "-fx-text-fill: #46536A;" +
//                 "-fx-font-size: 7px;" +
//                 "-fx-padding: 3 8;"
//         );

//         route.setOnAction(
//                 event -> showRoute()
//         );

//         phone.setOnAction(
//                 event -> {

//                     Alert alert =
//                             new Alert(
//                                     Alert.AlertType.INFORMATION
//                             );

//                     alert.setTitle(
//                             "Contact Hospital"
//                     );

//                     alert.setHeaderText(
//                             line1
//                     );

//                     alert.setContentText(
//                             "Hospital contact action selected."
//                     );

//                     alert.showAndWait();
//                 }
//         );

//         buttons.getChildren().addAll(
//                 route,
//                 phone
//         );

//         card.getChildren().addAll(
//                 content,
//                 buttons
//         );

//         return card;
//     }

//     // =========================================================
//     // BADGE
//     // =========================================================

//     private Label badge(
//             String text,
//             String background,
//             String foreground
//     ) {

//         Label label =
//                 new Label(
//                         text
//                 );

//         label.setStyle(
//                 "-fx-background-color: " +
//                 background +
//                 ";" +
//                 "-fx-background-radius: 10;" +
//                 "-fx-padding: 3 6;" +
//                 "-fx-text-fill: " +
//                 foreground +
//                 ";" +
//                 "-fx-font-size: 6px;" +
//                 "-fx-font-weight: bold;"
//         );

//         return label;
//     }

//     // =========================================================
//     // BUTTON
//     // =========================================================

//     private Button smallButton(
//             String text,
//             String background
//     ) {

//         Button button =
//                 new Button(
//                         text
//                 );

//         button.setPrefWidth(
//                 105
//         );

//         button.setPrefHeight(
//                 25
//         );

//         button.setCursor(
//                 Cursor.HAND
//         );

//         button.setStyle(
//                 "-fx-background-color: " +
//                 background +
//                 ";" +
//                 "-fx-background-radius: 6;" +
//                 "-fx-text-fill: white;" +
//                 "-fx-font-size: 8px;" +
//                 "-fx-font-weight: bold;"
//         );

//         return button;
//     }

//     // =========================================================
//     // ROUTE
//     // =========================================================

//     private void showRoute() {

//         if (mapEngine != null) {

//             try {

//                 mapEngine.executeScript(
//                         "showRoute()"
//                 );

//             } catch (Exception ignored) {
//             }
//         }
//     }

//     // =========================================================
//     // MAIN
//     // =========================================================

//     public static void main(
//             String[] args
//     ) {
//         launch(args);
//     }
// }


package com.kurukshetra.view.family;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SavedHospitals extends Application {

    // =========================================================
    // COLORS
    // =========================================================

    private static final String BACKGROUND = "#F5F5FD";
    private static final String SIDEBAR = "#FBFBFE";

    private static final String DARK_TEXT = "#252A3A";
    private static final String MUTED = "#777C8D";

    private static final String PURPLE = "#6254B8";
    private static final String LIGHT_PURPLE = "#ECEAF9";

    private static final String BLUE = "#BFE4F8";

    private static final String RED = "#E62F39";

    private static final String GREEN = "#35A96B";

    private static final String BORDER = "#E4E5EF";

    private static final String TEAL = "#00566F";

    // =========================================================
    // START
    // =========================================================

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();

        root.setStyle(
                "-fx-background-color: " + BACKGROUND + ";"
        );

        // -----------------------------------------------------
        // SIDEBAR
        // -----------------------------------------------------

        VBox sidebar = createSidebar();

        root.setLeft(sidebar);

        // -----------------------------------------------------
        // MAIN CONTENT
        // -----------------------------------------------------

        VBox main = new VBox(18);

        main.setPadding(
                new Insets(
                        25,
                        25,
                        25,
                        28
                )
        );

        main.setFillWidth(true);

        // -----------------------------------------------------
        // HEADER
        // -----------------------------------------------------

        VBox heading = createHeading();

        // -----------------------------------------------------
        // MAIN HOSPITAL AREA
        // -----------------------------------------------------

        HBox mainHospital = createMainHospital();

        // -----------------------------------------------------
        // SMALL HOSPITAL CARDS
        // -----------------------------------------------------

        HBox savedHospitals = new HBox(16);

        VBox oakridge =
                createSmallHospital(
                        "Oakridge Specialized",
                        "Clinic",
                        "5.8 mi",
                        "▣"
                );

        VBox valley =
                createSmallHospital(
                        "Valley Pediatric Center",
                        "",
                        "9.2 mi",
                        "♙"
                );

        HBox.setHgrow(
                oakridge,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                valley,
                Priority.ALWAYS
        );

        savedHospitals.getChildren().addAll(
                oakridge,
                valley
        );

        // -----------------------------------------------------
        // ADD
        // -----------------------------------------------------

        main.getChildren().addAll(
                heading,
                mainHospital,
                savedHospitals
        );

        // -----------------------------------------------------
        // SCROLL
        // -----------------------------------------------------

        ScrollPane scrollPane =
                new ScrollPane(main);

        scrollPane.setFitToWidth(true);

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scrollPane.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );

        root.setCenter(scrollPane);

        // -----------------------------------------------------
        // SCENE
        // -----------------------------------------------------

        Scene scene =
                new Scene(
                        root,
                        1160,
                        700
                );

        stage.setTitle(
                "LifeLink - Saved Hospitals"
        );

        stage.setMinWidth(950);
        stage.setMinHeight(600);

        stage.setScene(scene);

        stage.show();
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private VBox createSidebar() {

        VBox sidebar =
                new VBox(7);

        sidebar.setPrefWidth(190);
        sidebar.setMinWidth(190);

        sidebar.setPadding(
                new Insets(
                        16,
                        10,
                        10,
                        10
                )
        );

        sidebar.setStyle(
                "-fx-background-color: " + SIDEBAR + ";" +
                "-fx-border-color: transparent #E6E7F0 transparent transparent;"
        );

        // -----------------------------------------------------
        // LOGO
        // -----------------------------------------------------

        Label logo =
                new Label("LifeLink");

        logo.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        17
                )
        );

        logo.setTextFill(
                Color.web("#17203A")
        );

        sidebar.getChildren().add(logo);

        // -----------------------------------------------------
        // PROFILE
        // -----------------------------------------------------

        HBox profile =
                new HBox(8);

        profile.setAlignment(
                Pos.CENTER_LEFT
        );

        Circle avatar =
                new Circle(
                        15,
                        Color.web("#D9E4F1")
                );

        Label avatarLetter =
                new Label("S");

        avatarLetter.setTextFill(
                Color.web("#4D5A70")
        );

        avatarLetter.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        9
                )
        );

        StackPane avatarBox =
                new StackPane(
                        avatar,
                        avatarLetter
                );

        VBox profileText =
                new VBox(1);

        Label name =
                new Label(
                        "Sarah Miller"
                );

        name.setStyle(
                "-fx-font-size: 8px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #34394A;"
        );

        Label role =
                new Label(
                        "Family Care Lead"
                );

        role.setStyle(
                "-fx-font-size: 7px;" +
                "-fx-text-fill: #7B8090;"
        );

        profileText.getChildren().addAll(
                name,
                role
        );

        profile.getChildren().addAll(
                avatarBox,
                profileText
        );

        sidebar.getChildren().add(profile);

        Region profileSpacer =
                new Region();

        profileSpacer.setPrefHeight(12);

        sidebar.getChildren().add(
                profileSpacer
        );

        // -----------------------------------------------------
        // NAVIGATION
        // -----------------------------------------------------

        sidebar.getChildren().add(
                navigationItem(
                        "⌂",
                        "Dashboard",
                        false
                )
        );

        sidebar.getChildren().add(
                navigationItem(
                        "⊞",
                        "Find Hospitals",
                        false
                )
        );

        sidebar.getChildren().add(
                navigationItem(
                        "✚",
                        "Emergency Services",
                        false
                )
        );

        sidebar.getChildren().add(
                navigationItem(
                        "✚",
                        "First-Aid Assistant",
                        false
                )
        );

        sidebar.getChildren().add(
                navigationItem(
                        "▣",
                        "Appointments",
                        false
                )
        );

        sidebar.getChildren().add(
                navigationItem(
                        "◴",
                        "Medical History",
                        false
                )
        );

        // -----------------------------------------------------
        // SAVED HOSPITALS SELECTED
        // -----------------------------------------------------

        sidebar.getChildren().add(
                navigationItem(
                        "▣",
                        "Saved Hospitals",
                        true
                )
        );

        sidebar.getChildren().add(
                navigationItem(
                        "⚙",
                        "Settings",
                        false
                )
        );

        // -----------------------------------------------------
        // SPACER
        // -----------------------------------------------------

        Region spacer =
                new Region();

        VBox.setVgrow(
                spacer,
                Priority.ALWAYS
        );

        sidebar.getChildren().add(
                spacer
        );

        // -----------------------------------------------------
        // EMERGENCY HELP
        // -----------------------------------------------------

        Button emergency =
                new Button(
                        "✚  Emergency Help"
                );

        emergency.setMaxWidth(
                Double.MAX_VALUE
        );

        emergency.setPrefHeight(30);

        emergency.setCursor(
                Cursor.HAND
        );

        emergency.setStyle(
                "-fx-background-color: " + RED + ";" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 9px;" +
                "-fx-font-weight: bold;"
        );

        emergency.setOnAction(
                event -> {

                    Alert alert =
                            new Alert(
                                    Alert.AlertType.INFORMATION
                            );

                    alert.setTitle(
                            "Emergency Help"
                    );

                    alert.setHeaderText(
                            "Emergency Help"
                    );

                    alert.setContentText(
                            "Emergency Help action selected."
                    );

                    alert.showAndWait();
                }
        );

        sidebar.getChildren().add(
                emergency
        );

        // -----------------------------------------------------
        // LOGOUT
        // -----------------------------------------------------

        sidebar.getChildren().add(
                navigationItem(
                        "↪",
                        "Logout",
                        false
                )
        );

        return sidebar;
    }

    // =========================================================
    // NAVIGATION ITEM
    // =========================================================

    private HBox navigationItem(
            String icon,
            String text,
            boolean selected
    ) {

        HBox item =
                new HBox(8);

        item.setAlignment(
                Pos.CENTER_LEFT
        );

        item.setPadding(
                new Insets(
                        7,
                        7,
                        7,
                        7
                )
        );

        item.setMaxWidth(
                Double.MAX_VALUE
        );

        item.setCursor(
                Cursor.HAND
        );

        Label iconLabel =
                new Label(
                        icon
                );

        iconLabel.setFont(
                Font.font(
                        "Arial",
                        10
                )
        );

        Label textLabel =
                new Label(
                        text
                );

        textLabel.setFont(
                Font.font(
                        "Arial",
                        selected
                                ? FontWeight.BOLD
                                : FontWeight.NORMAL,
                        8
                )
        );

        if (selected) {

            item.setStyle(
                    "-fx-background-color: " +
                    LIGHT_PURPLE +
                    ";" +
                    "-fx-background-radius: 6;"
            );

            iconLabel.setTextFill(
                    Color.web(TEAL)
            );

            textLabel.setTextFill(
                    Color.web(TEAL)
            );

        } else {

            iconLabel.setTextFill(
                    Color.web("#555A6A")
            );

            textLabel.setTextFill(
                    Color.web("#555A6A")
            );
        }

        item.getChildren().addAll(
                iconLabel,
                textLabel
        );

        // -----------------------------------------------------
        // HOVER
        // -----------------------------------------------------

        item.setOnMouseEntered(
                event -> {

                    if (!selected) {

                        item.setStyle(
                                "-fx-background-color: #F0F0F7;" +
                                "-fx-background-radius: 6;"
                        );
                    }
                }
        );

        item.setOnMouseExited(
                event -> {

                    if (!selected) {

                        item.setStyle(
                                "-fx-background-color: transparent;"
                        );
                    }
                }
        );

        return item;
    }

    // =========================================================
    // PAGE HEADING
    // =========================================================

    private VBox createHeading() {

        VBox heading =
                new VBox(3);

        Label title =
                new Label(
                        "Saved Hospitals"
                );

        title.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        18
                )
        );

        title.setTextFill(
                Color.web(DARK_TEXT)
        );

        Label subtitle =
                new Label(
                        "Quick access to your preferred healthcare facilities and emergency contacts."
                );

        subtitle.setStyle(
                "-fx-font-size: 8px;" +
                "-fx-text-fill: " + MUTED + ";"
        );

        heading.getChildren().addAll(
                title,
                subtitle
        );

        return heading;
    }

    // =========================================================
    // MAIN HOSPITAL CARD
    // =========================================================

    private HBox createMainHospital() {

        HBox container =
                new HBox(12);

        container.setPrefHeight(130);

        // -----------------------------------------------------
        // HOSPITAL INFORMATION
        // -----------------------------------------------------

        VBox hospitalCard =
                new VBox(7);

        hospitalCard.setPadding(
                new Insets(10)
        );

        hospitalCard.setPrefWidth(550);

        hospitalCard.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 9;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 9;" +
                "-fx-effect: dropshadow(" +
                "gaussian," +
                "rgba(30,40,70,0.06)," +
                "9,0,0,2);"
        );

        HBox.setHgrow(
                hospitalCard,
                Priority.ALWAYS
        );

        HBox hospitalContent =
                new HBox(12);

        // -----------------------------------------------------
        // HOSPITAL IMAGE
        // -----------------------------------------------------

        ImageView hospitalImage =
                createHospitalImage();

        hospitalContent.getChildren().add(
                hospitalImage
        );

        // -----------------------------------------------------
        // DETAILS
        // -----------------------------------------------------

        VBox details =
                new VBox(5);

        HBox titleRow =
                new HBox(7);

        Label hospitalName =
                new Label(
                        "Mercy General Hospital"
                );

        hospitalName.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        11
                )
        );

        hospitalName.setTextFill(
                Color.web(DARK_TEXT)
        );

        Label primary =
                new Label(
                        "★ Primary"
                );

        primary.setStyle(
                "-fx-background-color: #E9F3F8;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 3 7;" +
                "-fx-text-fill: #24617A;" +
                "-fx-font-size: 7px;" +
                "-fx-font-weight: bold;"
        );

        titleRow.getChildren().addAll(
                hospitalName,
                primary
        );

        Label address =
                new Label(
                        "⌖ 1240 Wellness Blvd, Springfield"
                );

        address.setStyle(
                "-fx-font-size: 7px;" +
                "-fx-text-fill: " + MUTED + ";"
        );

        HBox badges =
                new HBox(5);

        badges.getChildren().addAll(
                badge(
                        "Level 1 Trauma",
                        "#E6ECFA",
                        "#4D5F8D"
                ),
                badge(
                        "Pediatric Care",
                        "#E8F1FC",
                        "#50709A"
                ),
                badge(
                        "● ER Open",
                        "#E1F5E9",
                        "#3A8661"
                )
        );

        Region verticalSpacer =
                new Region();

        VBox.setVgrow(
                verticalSpacer,
                Priority.ALWAYS
        );

        // -----------------------------------------------------
        // BUTTONS
        // -----------------------------------------------------

        HBox buttons =
                new HBox(8);

        Button route =
                smallButton(
                        "◆  Route",
                        TEAL
                );

        Button call =
                smallButton(
                        "☎  Call",
                        "#B9E2F7"
                );

        call.setTextFill(
                Color.web("#255D79")
        );

        route.setOnAction(
                event -> showRoute()
        );

        call.setOnAction(
                event -> {

                    Alert alert =
                            new Alert(
                                    Alert.AlertType.INFORMATION
                            );

                    alert.setTitle(
                            "Call Hospital"
                    );

                    alert.setHeaderText(
                            "Mercy General Hospital"
                    );

                    alert.setContentText(
                            "Calling (555) 245-8800...\n\n" +
                            "This is a UI prototype, so no real call will be placed."
                    );

                    alert.showAndWait();
                }
        );

        buttons.getChildren().addAll(
                route,
                call
        );

        details.getChildren().addAll(
                titleRow,
                address,
                badges,
                verticalSpacer,
                buttons
        );

        hospitalContent.getChildren().add(
                details
        );

        hospitalCard.getChildren().add(
                hospitalContent
        );

        // -----------------------------------------------------
        // MAP CONTAINER
        // -----------------------------------------------------

        VBox mapContainer =
                createMapContainer();

        mapContainer.setPrefWidth(330);

        HBox.setHgrow(
                mapContainer,
                Priority.ALWAYS
        );

        container.getChildren().addAll(
                hospitalCard,
                mapContainer
        );

        return container;
    }

    // =========================================================
    // HOSPITAL IMAGE
    // =========================================================

    private ImageView createHospitalImage() {

        String imageURL =
                "https://images.unsplash.com/photo-1587351021759-3e566b6af7cc" +
                "?auto=format&fit=crop&w=400&q=80";

        Image image =
                new Image(
                        imageURL,
                        115,
                        90,
                        false,
                        true,
                        true
                );

        ImageView imageView =
                new ImageView(image);

        imageView.setFitWidth(115);

        imageView.setFitHeight(90);

        imageView.setPreserveRatio(false);

        imageView.setSmooth(true);

        return imageView;
    }

    // =========================================================
    // MAP CONTAINER
    // =========================================================

    private VBox createMapContainer() {

        VBox container =
                new VBox(8);

        container.setPadding(
                new Insets(8)
        );

        container.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 9;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 9;" +
                "-fx-effect: dropshadow(" +
                "gaussian," +
                "rgba(30,40,70,0.06)," +
                "9,0,0,2);"
        );

        // -----------------------------------------------------
        // MAP PLACEHOLDER
        // -----------------------------------------------------

        VBox mapPlaceholder =
                new VBox(7);

        mapPlaceholder.setAlignment(
                Pos.CENTER
        );

        mapPlaceholder.setPrefHeight(130);

        mapPlaceholder.setMaxWidth(
                Double.MAX_VALUE
        );

        VBox.setVgrow(
                mapPlaceholder,
                Priority.ALWAYS
        );

        mapPlaceholder.setStyle(
                "-fx-background-color: #F1F5F8;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #DDE3EA;" +
                "-fx-border-radius: 8;"
        );

        Label mapIcon =
                new Label("⌖");

        mapIcon.setStyle(
                "-fx-font-size: 28px;" +
                "-fx-text-fill: " + TEAL + ";"
        );

        Label mapTitle =
                new Label(
                        "Hospital Location"
                );

        mapTitle.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + DARK_TEXT + ";"
        );

        Label mapText =
                new Label(
                        "Interactive map temporarily disabled"
                );

        mapText.setStyle(
                "-fx-font-size: 7px;" +
                "-fx-text-fill: " + MUTED + ";"
        );

        mapPlaceholder.getChildren().addAll(
                mapIcon,
                mapTitle,
                mapText
        );

        // -----------------------------------------------------
        // GET DIRECTIONS BUTTON
        // -----------------------------------------------------

        Button directions =
                new Button(
                        "◆  Get Directions"
                );

        directions.setMaxWidth(
                Double.MAX_VALUE
        );

        directions.setPrefHeight(27);

        directions.setCursor(
                Cursor.HAND
        );

        directions.setStyle(
                "-fx-background-color: " + TEAL + ";" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 8px;" +
                "-fx-font-weight: bold;"
        );

        directions.setOnAction(
                event -> showRoute()
        );

        container.getChildren().addAll(
                mapPlaceholder,
                directions
        );

        return container;
    }

    // =========================================================
    // SMALL HOSPITAL
    // =========================================================

    private VBox createSmallHospital(
            String line1,
            String line2,
            String distance,
            String icon
    ) {

        VBox card =
                new VBox();

        card.setPadding(
                new Insets(10)
        );

        card.setPrefHeight(78);

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 9;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-radius: 9;" +
                "-fx-effect: dropshadow(" +
                "gaussian," +
                "rgba(30,40,70,0.05)," +
                "8,0,0,2);"
        );

        HBox content =
                new HBox(10);

        content.setAlignment(
                Pos.CENTER_LEFT
        );

        // -----------------------------------------------------
        // ICON BOX
        // -----------------------------------------------------

        StackPane iconBox =
                new StackPane();

        iconBox.setMinSize(
                42,
                42
        );

        iconBox.setMaxSize(
                42,
                42
        );

        iconBox.setStyle(
                "-fx-background-color: #EFF1FB;" +
                "-fx-background-radius: 6;"
        );

        Label iconLabel =
                new Label(icon);

        iconLabel.setStyle(
                "-fx-text-fill: #7392B3;" +
                "-fx-font-size: 16px;"
        );

        iconBox.getChildren().add(
                iconLabel
        );

        // -----------------------------------------------------
        // INFORMATION
        // -----------------------------------------------------

        VBox information =
                new VBox(2);

        Label hospitalName =
                new Label(line1);

        hospitalName.setStyle(
                "-fx-text-fill: " + DARK_TEXT + ";" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;"
        );

        Label second =
                new Label(line2);

        second.setStyle(
                "-fx-text-fill: " + DARK_TEXT + ";" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;"
        );

        Label distanceLabel =
                new Label(
                        "⌖ " + distance
                );

        distanceLabel.setStyle(
                "-fx-text-fill: " + MUTED + ";" +
                "-fx-font-size: 7px;"
        );

        information.getChildren().add(
                hospitalName
        );

        if (!line2.isEmpty()) {

            information.getChildren().add(
                    second
            );
        }

        information.getChildren().add(
                distanceLabel
        );

        // -----------------------------------------------------
        // BOOKMARK
        // -----------------------------------------------------

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label bookmark =
                new Label("▮");

        bookmark.setStyle(
                "-fx-text-fill: #00566F;" +
                "-fx-font-size: 12px;"
        );

        content.getChildren().addAll(
                iconBox,
                information,
                spacer,
                bookmark
        );

        // -----------------------------------------------------
        // BUTTONS
        // -----------------------------------------------------

        HBox buttons =
                new HBox(5);

        buttons.setPadding(
                new Insets(
                        2,
                        0,
                        0,
                        52
                )
        );

        Button route =
                new Button(
                        "◆ Route"
                );

        route.setStyle(
                "-fx-background-color: #C9E9F8;" +
                "-fx-background-radius: 8;" +
                "-fx-text-fill: #28647F;" +
                "-fx-font-size: 7px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 3 8;"
        );

        Button phone =
                new Button("☎");

        phone.setStyle(
                "-fx-background-color: #F0F1F7;" +
                "-fx-background-radius: 8;" +
                "-fx-text-fill: #46536A;" +
                "-fx-font-size: 7px;" +
                "-fx-padding: 3 8;"
        );

        route.setOnAction(
                event -> showRoute()
        );

        phone.setOnAction(
                event -> {

                    Alert alert =
                            new Alert(
                                    Alert.AlertType.INFORMATION
                            );

                    alert.setTitle(
                            "Contact Hospital"
                    );

                    alert.setHeaderText(
                            line1
                    );

                    alert.setContentText(
                            "Hospital contact action selected."
                    );

                    alert.showAndWait();
                }
        );

        buttons.getChildren().addAll(
                route,
                phone
        );

        card.getChildren().addAll(
                content,
                buttons
        );

        return card;
    }

    // =========================================================
    // BADGE
    // =========================================================

    private Label badge(
            String text,
            String background,
            String foreground
    ) {

        Label label =
                new Label(text);

        label.setStyle(
                "-fx-background-color: " +
                background +
                ";" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 3 6;" +
                "-fx-text-fill: " +
                foreground +
                ";" +
                "-fx-font-size: 6px;" +
                "-fx-font-weight: bold;"
        );

        return label;
    }

    // =========================================================
    // BUTTON
    // =========================================================

    private Button smallButton(
            String text,
            String background
    ) {

        Button button =
                new Button(text);

        button.setPrefWidth(105);

        button.setPrefHeight(25);

        button.setCursor(
                Cursor.HAND
        );

        button.setStyle(
                "-fx-background-color: " +
                background +
                ";" +
                "-fx-background-radius: 6;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 8px;" +
                "-fx-font-weight: bold;"
        );

        return button;
    }

    // =========================================================
    // ROUTE
    // =========================================================

    private void showRoute() {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle(
                "Route"
        );

        alert.setHeaderText(
                "Directions to Mercy General Hospital"
        );

        alert.setContentText(
                "Route functionality is currently simulated.\n\n" +
                "The interactive WebView map has been disabled " +
                "to avoid the JavaFX WebView runtime conflict."
        );

        alert.showAndWait();
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        launch(args);
    }
}