// package com.kurukshetra.view.family;

// import javafx.application.Application;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Cursor;
// import javafx.scene.Scene;
// import javafx.scene.control.*;
// import javafx.scene.input.MouseEvent;
// import javafx.scene.layout.*;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Circle;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.scene.web.WebEngine;
// import javafx.scene.web.WebView;
// import javafx.stage.Stage;

// import java.net.URL;

// public class EmergencyServices extends Application {

//     private final String BACKGROUND = "#F5F5FC";
//     private final String SIDEBAR = "#FBFBFE";
//     private final String RED = "#E83D45";
//     private final String LIGHT_RED = "#FFE0E0";
//     private final String DARK_TEXT = "#252A3B";
//     private final String MUTED = "#74798B";
//     private final String BLUE = "#C5E7FA";
//     private final String TEAL = "#005A72";
//     private final String GREEN = "#31AA6B";

//     private WebEngine mapEngine;

//     @Override
//     public void start(Stage stage) {

//         BorderPane root = new BorderPane();

//         root.setStyle(
//                 "-fx-background-color: " + BACKGROUND + ";");

//         // --------------------------------------------------
//         // SIDEBAR
//         // --------------------------------------------------

//         VBox sidebar = createSidebar();

//         root.setLeft(sidebar);

//         // --------------------------------------------------
//         // MAIN AREA
//         // --------------------------------------------------

//         VBox mainContent = new VBox(14);

//         mainContent.setPadding(
//                 new Insets(15, 18, 15, 16));

//         mainContent.setFillWidth(true);

//         // --------------------------------------------------
//         // HEADER
//         // --------------------------------------------------

//         BorderPane header = createHeader();

//         // --------------------------------------------------
//         // TOP SECTION
//         // --------------------------------------------------

//         VBox sosCard = createSOSCard();

//         VBox locationCard = createLocationCard();

//         HBox topSection = new HBox(
//                 14,
//                 sosCard,
//                 locationCard);

//         HBox.setHgrow(locationCard, Priority.ALWAYS);

//         // --------------------------------------------------
//         // BOTTOM SECTION
//         // --------------------------------------------------

//         VBox contactsCard = createContactsCard();

//         VBox mapCard = createMapCard();

//         HBox bottomSection = new HBox(
//                 14,
//                 contactsCard,
//                 mapCard);

//         HBox.setHgrow(mapCard, Priority.ALWAYS);

//         // --------------------------------------------------
//         // ADD EVERYTHING
//         // --------------------------------------------------

//         mainContent.getChildren().addAll(
//                 header,
//                 topSection,
//                 bottomSection);

//         ScrollPane scrollPane = new ScrollPane(mainContent);

//         scrollPane.setFitToWidth(true);
//         scrollPane.setHbarPolicy(
//                 ScrollPane.ScrollBarPolicy.NEVER);

//         scrollPane.setStyle(
//                 "-fx-background-color: transparent;" +
//                         "-fx-border-color: transparent;");

//         root.setCenter(scrollPane);

//         // --------------------------------------------------
//         // SCENE
//         // --------------------------------------------------

//         Scene scene = new Scene(
//                 root,
//                 1200,
//                 700);

//         stage.setTitle(
//                 "LifeLink - Emergency Services");

//         stage.setMinWidth(950);
//         stage.setMinHeight(600);

//         stage.setScene(scene);

//         stage.show();
//     }

//     // ======================================================
//     // SIDEBAR
//     // ======================================================

//     private VBox createSidebar() {

//         VBox sidebar = new VBox(7);

//         sidebar.setPrefWidth(190);
//         sidebar.setMinWidth(190);

//         sidebar.setPadding(
//                 new Insets(16, 10, 12, 10));

//         sidebar.setStyle(
//                 "-fx-background-color: " + SIDEBAR + ";" +
//                         "-fx-border-color: transparent #E7E8F0 transparent transparent;");

//         // --------------------------------------------------
//         // LOGO
//         // --------------------------------------------------

//         Label logo = new Label("LifeLink");

//         logo.setFont(
//                 Font.font(
//                         "Arial",
//                         FontWeight.BOLD,
//                         17));

//         logo.setTextFill(
//                 Color.web("#17203A"));

//         sidebar.getChildren().add(logo);

//         // --------------------------------------------------
//         // PROFILE
//         // --------------------------------------------------

//         HBox profile = new HBox(8);

//         profile.setAlignment(
//                 Pos.CENTER_LEFT);

//         Circle avatar = new Circle(
//                 16,
//                 Color.web("#D7E1F0"));

//         VBox profileText = new VBox(1);

//         Label name = new Label(
//                 "Sarah Miller");

//         name.setStyle(
//                 "-fx-font-size: 8px;" +
//                         "-fx-font-weight: bold;" +
//                         "-fx-text-fill: #34394A;");

//         Label role = new Label(
//                 "Family Care Lead");

//         role.setStyle(
//                 "-fx-font-size: 7px;" +
//                         "-fx-text-fill: #777C8C;");

//         profileText.getChildren().addAll(
//                 name,
//                 role);

//         profile.getChildren().addAll(
//                 avatar,
//                 profileText);

//         sidebar.getChildren().add(profile);

//         // Spacer

//         Region spacer1 = new Region();

//         spacer1.setPrefHeight(8);

//         sidebar.getChildren().add(
//                 spacer1);

//         // --------------------------------------------------
//         // NAVIGATION
//         // --------------------------------------------------

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "⌂",
//                         "Dashboard",
//                         false));

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "⊞",
//                         "Find Hospitals",
//                         false));

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "✚",
//                         "Emergency Services",
//                         true));

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "✚",
//                         "First-Aid Assistant",
//                         false));

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "▣",
//                         "Appointments",
//                         false));

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "▤",
//                         "Medical History",
//                         false));

//         // Spacer

//         Region spacer2 = new Region();

//         VBox.setVgrow(
//                 spacer2,
//                 Priority.ALWAYS);

//         sidebar.getChildren().add(
//                 spacer2);

//         // --------------------------------------------------
//         // EMERGENCY HELP
//         // --------------------------------------------------

//         Label emergencyHelp = new Label(
//                 "Emergency Help");

//         emergencyHelp.setMaxWidth(
//                 Double.MAX_VALUE);

//         emergencyHelp.setAlignment(
//                 Pos.CENTER);

//         emergencyHelp.setPadding(
//                 new Insets(7));

//         emergencyHelp.setCursor(
//                 Cursor.HAND);

//         emergencyHelp.setStyle(
//                 "-fx-background-color: " + RED + ";" +
//                         "-fx-background-radius: 7;" +
//                         "-fx-text-fill: white;" +
//                         "-fx-font-size: 9px;" +
//                         "-fx-font-weight: bold;");

//         sidebar.getChildren().add(
//                 emergencyHelp);

//         // --------------------------------------------------
//         // SETTINGS
//         // --------------------------------------------------

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "⚙",
//                         "Settings",
//                         false));

//         sidebar.getChildren().add(
//                 navigationItem(
//                         "↪",
//                         "Logout",
//                         false));

//         return sidebar;
//     }

//     // ======================================================
//     // NAVIGATION ITEM
//     // ======================================================

//     private HBox navigationItem(
//             String icon,
//             String text,
//             boolean selected) {

//         HBox item = new HBox(8);

//         item.setAlignment(
//                 Pos.CENTER_LEFT);

//         item.setPadding(
//                 new Insets(7, 8, 7, 8));

//         item.setMaxWidth(
//                 Double.MAX_VALUE);

//         Label iconLabel = new Label(
//                 icon);

//         Label textLabel = new Label(
//                 text);

//         if (selected) {

//             item.setStyle(
//                     "-fx-background-color: #FDEBED;" +
//                             "-fx-background-radius: 7;");

//             iconLabel.setTextFill(
//                     Color.web(RED));

//             textLabel.setTextFill(
//                     Color.web(RED));

//             textLabel.setFont(
//                     Font.font(
//                             "Arial",
//                             FontWeight.BOLD,
//                             9));

//         } else {

//             textLabel.setTextFill(
//                     Color.web("#4F5464"));

//             iconLabel.setTextFill(
//                     Color.web("#4F5464"));

//             textLabel.setFont(
//                     Font.font(
//                             "Arial",
//                             9));
//         }

//         iconLabel.setFont(
//                 Font.font(
//                         "Arial",
//                         10));

//         item.getChildren().addAll(
//                 iconLabel,
//                 textLabel);

//         item.setCursor(
//                 Cursor.HAND);

//         return item;
//     }

//     // ======================================================
//     // HEADER
//     // ======================================================

//     private BorderPane createHeader() {

//         BorderPane header = new BorderPane();

//         Label title = new Label(
//                 "Emergency Services");

//         title.setFont(
//                 Font.font(
//                         "Arial",
//                         FontWeight.BOLD,
//                         20));

//         title.setTextFill(
//                 Color.web(RED));

//         HBox rightIcons = new HBox(
//                 14);

//         rightIcons.setAlignment(
//                 Pos.CENTER_RIGHT);

//         Label notification = new Label(
//                 "♧");

//         Label help = new Label(
//                 "?");

//         notification.setStyle(
//                 "-fx-font-size: 12px;" +
//                         "-fx-text-fill: #37405A;");

//         help.setStyle(
//                 "-fx-font-size: 12px;" +
//                         "-fx-text-fill: #37405A;");

//         rightIcons.getChildren().addAll(
//                 notification,
//                 help);

//         header.setLeft(
//                 title);

//         header.setRight(
//                 rightIcons);

//         return header;
//     }

//     // ======================================================
//     // SOS CARD
//     // ======================================================

//     private VBox createSOSCard() {

//         VBox card = createCard();

//         card.setPrefWidth(
//                 360);

//         Label title = new Label(
//                 "Need Immediate Help?");

//         title.setFont(
//                 Font.font(
//                         "Arial",
//                         FontWeight.BOLD,
//                         12));

//         title.setTextFill(
//                 Color.web("#333849"));

//         Label description = new Label(
//                 "Slide to call emergency services (911) or notify\n" +
//                         "your emergency contacts.");

//         description.setAlignment(
//                 Pos.CENTER);

//         description.setStyle(
//                 "-fx-font-size: 8px;" +
//                         "-fx-text-fill: " + MUTED + ";");

//         SosSlider slider = new SosSlider();

//         card.getChildren().addAll(
//                 title,
//                 description,
//                 slider);

//         return card;
//     }

//     // ======================================================
//     // SOS SLIDER
//     // ======================================================

//     private class SosSlider extends StackPane {

//         private final StackPane handle = new StackPane();

//         private double startX;
//         private double initialX;

//         public SosSlider() {

//             setPrefHeight(45);
//             setMinHeight(45);

//             // Track

//             Region track = new Region();

//             track.setMaxWidth(
//                     Double.MAX_VALUE);

//             track.setPrefHeight(
//                     45);

//             track.setStyle(
//                     "-fx-background-color: #FFD6D6;" +
//                             "-fx-background-radius: 25;");

//             // Text

//             Label text = new Label(
//                     "SLIDE TO SOS  »");

//             text.setStyle(
//                     "-fx-text-fill: #C36B72;" +
//                             "-fx-font-size: 9px;" +
//                             "-fx-font-weight: bold;");

//             // Handle

//             Circle circle = new Circle(
//                     18,
//                     Color.web(RED));

//             Label phone = new Label(
//                     "☎");

//             phone.setTextFill(
//                     Color.WHITE);

//             phone.setFont(
//                     Font.font(
//                             "Arial",
//                             FontWeight.BOLD,
//                             14));

//             handle.getChildren().addAll(
//                     circle,
//                     phone);

//             handle.setMinSize(
//                     38,
//                     38);

//             handle.setMaxSize(
//                     38,
//                     38);

//             handle.setCursor(
//                     Cursor.HAND);

//             StackPane.setAlignment(
//                     handle,
//                     Pos.CENTER_LEFT);

//             StackPane.setMargin(
//                     handle,
//                     new Insets(0, 0, 0, 4));

//             getChildren().addAll(
//                     track,
//                     text,
//                     handle);

//             // --------------------------------------------------
//             // DRAG START
//             // --------------------------------------------------

//             handle.addEventHandler(
//                     MouseEvent.MOUSE_PRESSED,
//                     event -> {

//                         startX = event.getSceneX();

//                         initialX = handle.getTranslateX();

//                         event.consume();
//                     });

//             // --------------------------------------------------
//             // DRAGGING
//             // --------------------------------------------------

//             handle.addEventHandler(
//                     MouseEvent.MOUSE_DRAGGED,
//                     event -> {

//                         double movement = event.getSceneX()
//                                 - startX;

//                         double max = getWidth() - 50;

//                         double newX = initialX + movement;

//                         if (newX < 0) {
//                             newX = 0;
//                         }

//                         if (newX > max) {
//                             newX = max;
//                         }

//                         handle.setTranslateX(
//                                 newX);

//                         event.consume();
//                     });

//             // --------------------------------------------------
//             // RELEASE
//             // --------------------------------------------------

//             handle.addEventHandler(
//                     MouseEvent.MOUSE_RELEASED,
//                     event -> {

//                         double max = getWidth() - 50;

//                         double current = handle.getTranslateX();

//                         double progress = current / max;

//                         if (progress >= 0.80) {

//                             Alert alert = new Alert(
//                                     Alert.AlertType.CONFIRMATION);

//                             alert.setTitle(
//                                     "Confirm SOS");

//                             alert.setHeaderText(
//                                     "Initiate Emergency SOS?");

//                             alert.setContentText(
//                                     "You are about to initiate an emergency SOS action.\n\n" +
//                                             "This JavaFX prototype will simulate the emergency alert.");

//                             alert.showAndWait();

//                         }

//                         handle.setTranslateX(
//                                 0);

//                         event.consume();
//                     });
//         }
//     }

//     // ======================================================
//     // LOCATION CARD
//     // ======================================================

//     private VBox createLocationCard() {

//         VBox card = createCard();

//         card.setMaxWidth(
//                 Double.MAX_VALUE);

//         HBox.setHgrow(
//                 card,
//                 Priority.ALWAYS);

//         BorderPane header = new BorderPane();

//         VBox labels = new VBox(2);

//         Label title = new Label(
//                 "⌖  Current Location & Nearest ER");

//         title.setStyle(
//                 "-fx-font-size: 9px;" +
//                         "-fx-font-weight: bold;" +
//                         "-fx-text-fill: #34394A;");

//         Label address = new Label(
//                 "124 Maple Street, Seattle, WA");

//         address.setStyle(
//                 "-fx-font-size: 7px;" +
//                         "-fx-text-fill: " + MUTED + ";");

//         labels.getChildren().addAll(
//                 title,
//                 address);

//         Label locate = new Label(
//                 "◎");

//         locate.setStyle(
//                 "-fx-background-color: #F0F3F8;" +
//                         "-fx-background-radius: 15;" +
//                         "-fx-padding: 5;" +
//                         "-fx-font-size: 10px;");

//         header.setLeft(
//                 labels);

//         header.setRight(
//                 locate);

//         card.getChildren().add(
//                 header);

//         return card;
//     }

//     // ======================================================
//     // CONTACTS CARD
//     // ======================================================

//     private VBox createContactsCard() {

//         VBox card = createCard();

//         card.setPrefWidth(
//                 360);

//         BorderPane header = new BorderPane();

//         Label title = new Label(
//                 "♧  Notify Emergency Contacts");

//         title.setStyle(
//                 "-fx-font-size: 10px;" +
//                         "-fx-font-weight: bold;" +
//                         "-fx-text-fill: #34394A;");

//         Label edit = new Label(
//                 "Edit");

//         edit.setStyle(
//                 "-fx-font-size: 8px;" +
//                         "-fx-text-fill: #6E7383;");

//         header.setLeft(
//                 title);

//         header.setRight(
//                 edit);

//         card.getChildren().add(
//                 header);

//         // Contact 1

//         card.getChildren().add(
//                 createContact(
//                         "JM",
//                         "John Miller",
//                         "Husband",
//                         "(555) 123-4567"));

//         // Contact 2

//         card.getChildren().add(
//                 createContact(
//                         "EM",
//                         "Emily Miller",
//                         "Daughter",
//                         "(555) 987-6543"));

//         // Quick alert button

//         Button quickAlert = new Button(
//                 "▷  Send Quick Alert Now");

//         quickAlert.setMaxWidth(
//                 Double.MAX_VALUE);

//         quickAlert.setPrefHeight(
//                 30);

//         quickAlert.setCursor(
//                 Cursor.HAND);

//         quickAlert.setStyle(
//                 "-fx-background-color: " + BLUE + ";" +
//                         "-fx-background-radius: 7;" +
//                         "-fx-text-fill: #285A76;" +
//                         "-fx-font-size: 9px;" +
//                         "-fx-font-weight: bold;");

//         quickAlert.setOnAction(
//                 event -> {

//                     Alert alert = new Alert(
//                             Alert.AlertType.CONFIRMATION);

//                     alert.setTitle(
//                             "Quick Alert");

//                     alert.setHeaderText(
//                             "Send Quick Alert?");

//                     alert.setContentText(
//                             "This will simulate sending an emergency alert " +
//                                     "to your enabled emergency contacts.");

//                     alert.showAndWait();
//                 });

//         card.getChildren().add(
//                 quickAlert);

//         return card;
//     }

//     // ======================================================
//     // CONTACT ROW
//     // ======================================================

//     private HBox createContact(
//             String initials,
//             String name,
//             String relation,
//             String phone) {

//         HBox row = new HBox(8);

//         row.setAlignment(
//                 Pos.CENTER_LEFT);

//         row.setPadding(
//                 new Insets(6));

//         row.setStyle(
//                 "-fx-background-color: #F8F9FD;" +
//                         "-fx-background-radius: 8;");

//         // Avatar

//         StackPane avatar = new StackPane();

//         Circle circle = new Circle(
//                 12,
//                 Color.web("#DDE6F1"));

//         Label initialsLabel = new Label(
//                 initials);

//         initialsLabel.setStyle(
//                 "-fx-font-size: 7px;" +
//                         "-fx-font-weight: bold;" +
//                         "-fx-text-fill: #566175;");

//         avatar.getChildren().addAll(
//                 circle,
//                 initialsLabel);

//         // Information

//         VBox information = new VBox(1);

//         Label nameLabel = new Label(
//                 name);

//         nameLabel.setStyle(
//                 "-fx-font-size: 9px;" +
//                         "-fx-font-weight: bold;" +
//                         "-fx-text-fill: #34394A;");

//         Label details = new Label(
//                 relation + " • " + phone);

//         details.setStyle(
//                 "-fx-font-size: 7px;" +
//                         "-fx-text-fill: " + MUTED + ";");

//         information.getChildren().addAll(
//                 nameLabel,
//                 details);

//         // Spacer

//         Region spacer = new Region();

//         HBox.setHgrow(
//                 spacer,
//                 Priority.ALWAYS);

//         // Toggle

//         ToggleButton toggle = new ToggleButton();

//         toggle.setSelected(
//                 true);

//         toggle.setMinSize(
//                 35,
//                 19);

//         toggle.setMaxSize(
//                 35,
//                 19);

//         updateToggleStyle(
//                 toggle);

//         toggle.selectedProperty()
//                 .addListener(
//                         (obs, oldValue, newValue) -> updateToggleStyle(toggle));

//         row.getChildren().addAll(
//                 avatar,
//                 information,
//                 spacer,
//                 toggle);

//         return row;
//     }

//     // ======================================================
//     // TOGGLE STYLE
//     // ======================================================

//     private void updateToggleStyle(
//             ToggleButton toggle) {

//         if (toggle.isSelected()) {

//             toggle.setStyle(
//                     "-fx-background-color: " + GREEN + ";" +
//                             "-fx-background-radius: 15;" +
//                             "-fx-border-color: transparent;");

//         } else {

//             toggle.setStyle(
//                     "-fx-background-color: #C7CBD3;" +
//                             "-fx-background-radius: 15;" +
//                             "-fx-border-color: transparent;");
//         }
//     }

//     // ======================================================
//     // MAP CARD
//     // ======================================================

//     private VBox createMapCard() {

//         VBox card = createCard();

//         card.setMinWidth(
//                 420);

//         HBox.setHgrow(
//                 card,
//                 Priority.ALWAYS);

//         WebView webView = createMap();

//         VBox.setVgrow(
//                 webView,
//                 Priority.ALWAYS);

//         Button directions = new Button(
//                 "◉  Get Directions to Nearest ER");

//         directions.setMaxWidth(
//                 Double.MAX_VALUE);

//         directions.setPrefHeight(
//                 30);

//         directions.setCursor(
//                 Cursor.HAND);

//         directions.setStyle(
//                 "-fx-background-color: " + TEAL + ";" +
//                         "-fx-background-radius: 7;" +
//                         "-fx-text-fill: white;" +
//                         "-fx-font-size: 9px;" +
//                         "-fx-font-weight: bold;");

//         directions.setOnAction(
//                 event -> {

//                     if (mapEngine != null) {

//                         try {

//                             mapEngine.executeScript(
//                                     "showRoute()");

//                         } catch (Exception ignored) {
//                         }
//                     }
//                 });

//         card.getChildren().addAll(
//                 // webView,
//                 directions);

//         return card;
//     }

//     // ======================================================
//     // INTERACTIVE MAP
//     // ======================================================

//     private WebView createMap() {

//         WebView webView = new WebView();

//         webView.setPrefHeight(
//                 300);

//         mapEngine = webView.getEngine();

//         URL url = getClass()
//                 .getResource(
//                         "resources/Emergencymap.html");

//         if (url != null) {

//             mapEngine.load(
//                     url.toExternalForm());

//         } else {

//             Label error = new Label(
//                     "Map resource not found");

//             error.setStyle(
//                     "-fx-text-fill: #B33B44;");
//         }

//         return webView;
//     }

//     // ======================================================
//     // CARD STYLE
//     // ======================================================

//     private VBox createCard() {

//         VBox card = new VBox(9);

//         card.setPadding(
//                 new Insets(13));

//         card.setStyle(
//                 "-fx-background-color: white;" +
//                         "-fx-background-radius: 9;" +
//                         "-fx-border-color: #E5E6EF;" +
//                         "-fx-border-radius: 9;" +
//                         "-fx-effect: dropshadow(" +
//                         "gaussian," +
//                         "rgba(30,40,70,0.07)," +
//                         "10," +
//                         "0," +
//                         "0," +
//                         "2);");

//         return card;
//     }

//     // ======================================================
//     // MAIN
//     // ======================================================

//     public static void main(
//             String[] args) {

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EmergencyServices extends Application {

    // ======================================================
    // COLORS
    // ======================================================

    private final String BACKGROUND = "#F5F5FC";
    private final String SIDEBAR = "#FBFBFE";
    private final String RED = "#E83D45";
    private final String LIGHT_RED = "#FFE0E0";
    private final String DARK_TEXT = "#252A3B";
    private final String MUTED = "#74798B";
    private final String BLUE = "#C5E7FA";
    private final String TEAL = "#005A72";
    private final String GREEN = "#31AA6B";

    // ======================================================
    // START
    // ======================================================

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();

        root.setStyle(
                "-fx-background-color: " + BACKGROUND + ";"
        );

        // ==================================================
        // SIDEBAR
        // ==================================================

        VBox sidebar = createSidebar();

        root.setLeft(sidebar);

        // ==================================================
        // MAIN AREA
        // ==================================================

        VBox mainContent = new VBox(14);

        mainContent.setPadding(
                new Insets(15, 18, 15, 16)
        );

        mainContent.setFillWidth(true);

        // ==================================================
        // HEADER
        // ==================================================

        BorderPane header = createHeader();

        // ==================================================
        // TOP SECTION
        // ==================================================

        VBox sosCard = createSOSCard();

        VBox locationCard = createLocationCard();

        HBox topSection = new HBox(
                14,
                sosCard,
                locationCard
        );

        HBox.setHgrow(
                locationCard,
                Priority.ALWAYS
        );

        // ==================================================
        // BOTTOM SECTION
        // ==================================================

        VBox contactsCard = createContactsCard();

        VBox mapCard = createMapCard();

        HBox bottomSection = new HBox(
                14,
                contactsCard,
                mapCard
        );

        HBox.setHgrow(
                mapCard,
                Priority.ALWAYS
        );

        // ==================================================
        // ADD EVERYTHING
        // ==================================================

        mainContent.getChildren().addAll(
                header,
                topSection,
                bottomSection
        );

        ScrollPane scrollPane = new ScrollPane(
                mainContent
        );

        scrollPane.setFitToWidth(true);

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );

        root.setCenter(scrollPane);

        // ==================================================
        // SCENE
        // ==================================================

        Scene scene = new Scene(
                root,
                1200,
                700
        );

        stage.setTitle(
                "LifeLink - Emergency Services"
        );

        stage.setMinWidth(950);
        stage.setMinHeight(600);

        stage.setScene(scene);

        stage.show();
    }

    // ======================================================
    // SIDEBAR
    // ======================================================

    private VBox createSidebar() {

        VBox sidebar = new VBox(7);

        sidebar.setPrefWidth(190);
        sidebar.setMinWidth(190);

        sidebar.setPadding(
                new Insets(16, 10, 12, 10)
        );

        sidebar.setStyle(
                "-fx-background-color: " + SIDEBAR + ";" +
                "-fx-border-color: transparent #E7E8F0 transparent transparent;"
        );

        // ==================================================
        // LOGO
        // ==================================================

        Label logo = new Label("LifeLink");

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

        // ==================================================
        // PROFILE
        // ==================================================

        HBox profile = new HBox(8);

        profile.setAlignment(
                Pos.CENTER_LEFT
        );

        Circle avatar = new Circle(
                16,
                Color.web("#D7E1F0")
        );

        VBox profileText = new VBox(1);

        Label name = new Label(
                "Sarah Miller"
        );

        name.setStyle(
                "-fx-font-size: 8px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #34394A;"
        );

        Label role = new Label(
                "Family Care Lead"
        );

        role.setStyle(
                "-fx-font-size: 7px;" +
                "-fx-text-fill: #777C8C;"
        );

        profileText.getChildren().addAll(
                name,
                role
        );

        profile.getChildren().addAll(
                avatar,
                profileText
        );

        sidebar.getChildren().add(profile);

        // ==================================================
        // SPACER
        // ==================================================

        Region spacer1 = new Region();

        spacer1.setPrefHeight(8);

        sidebar.getChildren().add(spacer1);

        // ==================================================
        // NAVIGATION
        // ==================================================

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
                        true
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
                        "▤",
                        "Medical History",
                        false
                )
        );

        // ==================================================
        // SPACER
        // ==================================================

        Region spacer2 = new Region();

        VBox.setVgrow(
                spacer2,
                Priority.ALWAYS
        );

        sidebar.getChildren().add(spacer2);

        // ==================================================
        // EMERGENCY HELP
        // ==================================================

        Label emergencyHelp = new Label(
                "Emergency Help"
        );

        emergencyHelp.setMaxWidth(
                Double.MAX_VALUE
        );

        emergencyHelp.setAlignment(
                Pos.CENTER
        );

        emergencyHelp.setPadding(
                new Insets(7)
        );

        emergencyHelp.setCursor(
                Cursor.HAND
        );

        emergencyHelp.setStyle(
                "-fx-background-color: " + RED + ";" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 9px;" +
                "-fx-font-weight: bold;"
        );

        emergencyHelp.setOnMouseClicked(event -> {

            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION
            );

            alert.setTitle("Emergency Help");
            alert.setHeaderText("Emergency Assistance");
            alert.setContentText(
                    "Emergency assistance request initiated."
            );

            alert.showAndWait();
        });

        sidebar.getChildren().add(
                emergencyHelp
        );

        // ==================================================
        // SETTINGS
        // ==================================================

        sidebar.getChildren().add(
                navigationItem(
                        "⚙",
                        "Settings",
                        false
                )
        );

        sidebar.getChildren().add(
                navigationItem(
                        "↪",
                        "Logout",
                        false
                )
        );

        return sidebar;
    }

    // ======================================================
    // NAVIGATION ITEM
    // ======================================================

    private HBox navigationItem(
            String icon,
            String text,
            boolean selected
    ) {

        HBox item = new HBox(8);

        item.setAlignment(
                Pos.CENTER_LEFT
        );

        item.setPadding(
                new Insets(7, 8, 7, 8)
        );

        item.setMaxWidth(
                Double.MAX_VALUE
        );

        Label iconLabel = new Label(
                icon
        );

        Label textLabel = new Label(
                text
        );

        if (selected) {

            item.setStyle(
                    "-fx-background-color: #FDEBED;" +
                    "-fx-background-radius: 7;"
            );

            iconLabel.setTextFill(
                    Color.web(RED)
            );

            textLabel.setTextFill(
                    Color.web(RED)
            );

            textLabel.setFont(
                    Font.font(
                            "Arial",
                            FontWeight.BOLD,
                            9
                    )
            );

        } else {

            textLabel.setTextFill(
                    Color.web("#4F5464")
            );

            iconLabel.setTextFill(
                    Color.web("#4F5464")
            );

            textLabel.setFont(
                    Font.font(
                            "Arial",
                            9
                    )
            );
        }

        iconLabel.setFont(
                Font.font(
                        "Arial",
                        10
                )
        );

        item.getChildren().addAll(
                iconLabel,
                textLabel
        );

        item.setCursor(
                Cursor.HAND
        );

        return item;
    }

    // ======================================================
    // HEADER
    // ======================================================

    private BorderPane createHeader() {

        BorderPane header = new BorderPane();

        Label title = new Label(
                "Emergency Services"
        );

        title.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        20
                )
        );

        title.setTextFill(
                Color.web(RED)
        );

        HBox rightIcons = new HBox(14);

        rightIcons.setAlignment(
                Pos.CENTER_RIGHT
        );

        Label notification = new Label("♧");
        Label help = new Label("?");

        notification.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #37405A;"
        );

        help.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #37405A;"
        );

        rightIcons.getChildren().addAll(
                notification,
                help
        );

        header.setLeft(title);
        header.setRight(rightIcons);

        return header;
    }

    // ======================================================
    // SOS CARD
    // ======================================================

    private VBox createSOSCard() {

        VBox card = createCard();

        card.setPrefWidth(360);

        Label title = new Label(
                "Need Immediate Help?"
        );

        title.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        12
                )
        );

        title.setTextFill(
                Color.web("#333849")
        );

        Label description = new Label(
                "Slide to call emergency services (911) or notify\n" +
                "your emergency contacts."
        );

        description.setAlignment(
                Pos.CENTER
        );

        description.setStyle(
                "-fx-font-size: 8px;" +
                "-fx-text-fill: " + MUTED + ";"
        );

        SosSlider slider = new SosSlider();

        card.getChildren().addAll(
                title,
                description,
                slider
        );

        return card;
    }

    // ======================================================
    // SOS SLIDER
    // ======================================================

    private class SosSlider extends StackPane {

        private final StackPane handle =
                new StackPane();

        private double startX;
        private double initialX;

        public SosSlider() {

            setPrefHeight(45);
            setMinHeight(45);

            // Track
            Region track = new Region();

            track.setMaxWidth(
                    Double.MAX_VALUE
            );

            track.setPrefHeight(45);

            track.setStyle(
                    "-fx-background-color: #FFD6D6;" +
                    "-fx-background-radius: 25;"
            );

            // Text
            Label text = new Label(
                    "SLIDE TO SOS  »"
            );

            text.setStyle(
                    "-fx-text-fill: #C36B72;" +
                    "-fx-font-size: 9px;" +
                    "-fx-font-weight: bold;"
            );

            // Handle
            Circle circle = new Circle(
                    18,
                    Color.web(RED)
            );

            Label phone = new Label(
                    "☎"
            );

            phone.setTextFill(
                    Color.WHITE
            );

            phone.setFont(
                    Font.font(
                            "Arial",
                            FontWeight.BOLD,
                            14
                    )
            );

            handle.getChildren().addAll(
                    circle,
                    phone
            );

            handle.setMinSize(
                    38,
                    38
            );

            handle.setMaxSize(
                    38,
                    38
            );

            handle.setCursor(
                    Cursor.HAND
            );

            StackPane.setAlignment(
                    handle,
                    Pos.CENTER_LEFT
            );

            StackPane.setMargin(
                    handle,
                    new Insets(0, 0, 0, 4)
            );

            getChildren().addAll(
                    track,
                    text,
                    handle
            );

            // ==================================================
            // DRAG START
            // ==================================================

            handle.addEventHandler(
                    MouseEvent.MOUSE_PRESSED,
                    event -> {

                        startX =
                                event.getSceneX();

                        initialX =
                                handle.getTranslateX();

                        event.consume();
                    }
            );

            // ==================================================
            // DRAGGING
            // ==================================================

            handle.addEventHandler(
                    MouseEvent.MOUSE_DRAGGED,
                    event -> {

                        double movement =
                                event.getSceneX()
                                - startX;

                        double max =
                                Math.max(
                                        0,
                                        getWidth() - 50
                                );

                        double newX =
                                initialX + movement;

                        if (newX < 0) {
                            newX = 0;
                        }

                        if (newX > max) {
                            newX = max;
                        }

                        handle.setTranslateX(
                                newX
                        );

                        event.consume();
                    }
            );

            // ==================================================
            // RELEASE
            // ==================================================

            handle.addEventHandler(
                    MouseEvent.MOUSE_RELEASED,
                    event -> {

                        double max =
                                Math.max(
                                        1,
                                        getWidth() - 50
                                );

                        double current =
                                handle.getTranslateX();

                        double progress =
                                current / max;

                        if (progress >= 0.80) {

                            Alert alert =
                                    new Alert(
                                            Alert.AlertType.CONFIRMATION
                                    );

                            alert.setTitle(
                                    "Confirm SOS"
                            );

                            alert.setHeaderText(
                                    "Initiate Emergency SOS?"
                            );

                            alert.setContentText(
                                    "You are about to initiate an emergency SOS action.\n\n" +
                                    "This JavaFX prototype will simulate the emergency alert."
                            );

                            alert.showAndWait();
                        }

                        handle.setTranslateX(0);

                        event.consume();
                    }
            );
        }
    }

    // ======================================================
    // LOCATION CARD
    // ======================================================

    private VBox createLocationCard() {

        VBox card = createCard();

        card.setMaxWidth(
                Double.MAX_VALUE
        );

        HBox.setHgrow(
                card,
                Priority.ALWAYS
        );

        BorderPane header =
                new BorderPane();

        VBox labels =
                new VBox(2);

        Label title =
                new Label(
                        "⌖  Current Location & Nearest ER"
                );

        title.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #34394A;"
        );

        Label address =
                new Label(
                        "124 Maple Street, Seattle, WA"
                );

        address.setStyle(
                "-fx-font-size: 7px;" +
                "-fx-text-fill: " + MUTED + ";"
        );

        labels.getChildren().addAll(
                title,
                address
        );

        Label locate =
                new Label("◎");

        locate.setStyle(
                "-fx-background-color: #F0F3F8;" +
                "-fx-background-radius: 15;" +
                "-fx-padding: 5;" +
                "-fx-font-size: 10px;"
        );

        header.setLeft(labels);
        header.setRight(locate);

        card.getChildren().add(
                header
        );

        return card;
    }

    // ======================================================
    // CONTACTS CARD
    // ======================================================

    private VBox createContactsCard() {

        VBox card = createCard();

        card.setPrefWidth(360);

        BorderPane header =
                new BorderPane();

        Label title =
                new Label(
                        "♧  Notify Emergency Contacts"
                );

        title.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #34394A;"
        );

        Label edit =
                new Label("Edit");

        edit.setStyle(
                "-fx-font-size: 8px;" +
                "-fx-text-fill: #6E7383;"
        );

        header.setLeft(title);
        header.setRight(edit);

        card.getChildren().add(header);

        // Contact 1
        card.getChildren().add(
                createContact(
                        "JM",
                        "John Miller",
                        "Husband",
                        "(555) 123-4567"
                )
        );

        // Contact 2
        card.getChildren().add(
                createContact(
                        "EM",
                        "Emily Miller",
                        "Daughter",
                        "(555) 987-6543"
                )
        );

        // Quick alert button
        Button quickAlert =
                new Button(
                        "▷  Send Quick Alert Now"
                );

        quickAlert.setMaxWidth(
                Double.MAX_VALUE
        );

        quickAlert.setPrefHeight(30);

        quickAlert.setCursor(
                Cursor.HAND
        );

        quickAlert.setStyle(
                "-fx-background-color: " + BLUE + ";" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: #285A76;" +
                "-fx-font-size: 9px;" +
                "-fx-font-weight: bold;"
        );

        quickAlert.setOnAction(
                event -> {

                    Alert alert =
                            new Alert(
                                    Alert.AlertType.CONFIRMATION
                            );

                    alert.setTitle(
                            "Quick Alert"
                    );

                    alert.setHeaderText(
                            "Send Quick Alert?"
                    );

                    alert.setContentText(
                            "This will simulate sending an emergency alert " +
                            "to your enabled emergency contacts."
                    );

                    alert.showAndWait();
                }
        );

        card.getChildren().add(
                quickAlert
        );

        return card;
    }

    // ======================================================
    // CONTACT ROW
    // ======================================================

    private HBox createContact(
            String initials,
            String name,
            String relation,
            String phone
    ) {

        HBox row = new HBox(8);

        row.setAlignment(
                Pos.CENTER_LEFT
        );

        row.setPadding(
                new Insets(6)
        );

        row.setStyle(
                "-fx-background-color: #F8F9FD;" +
                "-fx-background-radius: 8;"
        );

        // Avatar
        StackPane avatar =
                new StackPane();

        Circle circle =
                new Circle(
                        12,
                        Color.web("#DDE6F1")
                );

        Label initialsLabel =
                new Label(initials);

        initialsLabel.setStyle(
                "-fx-font-size: 7px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #566175;"
        );

        avatar.getChildren().addAll(
                circle,
                initialsLabel
        );

        // Information
        VBox information =
                new VBox(1);

        Label nameLabel =
                new Label(name);

        nameLabel.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #34394A;"
        );

        Label details =
                new Label(
                        relation + " • " + phone
                );

        details.setStyle(
                "-fx-font-size: 7px;" +
                "-fx-text-fill: " + MUTED + ";"
        );

        information.getChildren().addAll(
                nameLabel,
                details
        );

        // Spacer
        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        // Toggle
        ToggleButton toggle =
                new ToggleButton();

        toggle.setSelected(true);

        toggle.setMinSize(
                35,
                19
        );

        toggle.setMaxSize(
                35,
                19
        );

        updateToggleStyle(toggle);

        toggle.selectedProperty()
                .addListener(
                        (obs, oldValue, newValue) ->
                                updateToggleStyle(toggle)
                );

        row.getChildren().addAll(
                avatar,
                information,
                spacer,
                toggle
        );

        return row;
    }

    // ======================================================
    // TOGGLE STYLE
    // ======================================================

    private void updateToggleStyle(
            ToggleButton toggle
    ) {

        if (toggle.isSelected()) {

            toggle.setStyle(
                    "-fx-background-color: " + GREEN + ";" +
                    "-fx-background-radius: 15;" +
                    "-fx-border-color: transparent;"
            );

        } else {

            toggle.setStyle(
                    "-fx-background-color: #C7CBD3;" +
                    "-fx-background-radius: 15;" +
                    "-fx-border-color: transparent;"
            );
        }
    }

    // ======================================================
    // MAP CARD
    // ======================================================

    private VBox createMapCard() {

        VBox card = createCard();

        card.setMinWidth(420);

        HBox.setHgrow(
                card,
                Priority.ALWAYS
        );

        // ==================================================
        // MAP PLACEHOLDER
        // ==================================================

        VBox mapPlaceholder =
                new VBox(8);

        mapPlaceholder.setAlignment(
                Pos.CENTER
        );

        mapPlaceholder.setPrefHeight(250);

        mapPlaceholder.setMaxWidth(
                Double.MAX_VALUE
        );

        mapPlaceholder.setStyle(
                "-fx-background-color: #F3F6FA;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #DDE3EA;" +
                "-fx-border-radius: 8;"
        );

        Label mapIcon =
                new Label("⌖");

        mapIcon.setStyle(
                "-fx-font-size: 32px;" +
                "-fx-text-fill: " + TEAL + ";"
        );

        Label mapTitle =
                new Label(
                        "Emergency Location Map"
                );

        mapTitle.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #34394A;"
        );

        Label mapText =
                new Label(
                        "Interactive map will be connected here"
                );

        mapText.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-text-fill: " + MUTED + ";"
        );

        mapPlaceholder.getChildren().addAll(
                mapIcon,
                mapTitle,
                mapText
        );

        VBox.setVgrow(
                mapPlaceholder,
                Priority.ALWAYS
        );

        // ==================================================
        // DIRECTIONS BUTTON
        // ==================================================

        Button directions =
                new Button(
                        "◉  Get Directions to Nearest ER"
                );

        directions.setMaxWidth(
                Double.MAX_VALUE
        );

        directions.setPrefHeight(30);

        directions.setCursor(
                Cursor.HAND
        );

        directions.setStyle(
                "-fx-background-color: " + TEAL + ";" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 9px;" +
                "-fx-font-weight: bold;"
        );

        directions.setOnAction(
                event -> {

                    Alert alert =
                            new Alert(
                                    Alert.AlertType.INFORMATION
                            );

                    alert.setTitle(
                            "Emergency Directions"
                    );

                    alert.setHeaderText(
                            "Nearest Emergency Room"
                    );

                    alert.setContentText(
                            "Directions functionality will be connected " +
                            "to the map service."
                    );

                    alert.showAndWait();
                }
        );

        card.getChildren().addAll(
                mapPlaceholder,
                directions
        );

        return card;
    }

    // ======================================================
    // CARD STYLE
    // ======================================================

    private VBox createCard() {

        VBox card =
                new VBox(9);

        card.setPadding(
                new Insets(13)
        );

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 9;" +
                "-fx-border-color: #E5E6EF;" +
                "-fx-border-radius: 9;" +
                "-fx-effect: dropshadow(" +
                "gaussian," +
                "rgba(30,40,70,0.07)," +
                "10," +
                "0," +
                "0," +
                "2);"
        );

        return card;
    }

    // ======================================================
    // MAIN
    // ======================================================

    public static void main(
            String[] args
    ) {
        launch(args);
    }
}