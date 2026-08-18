// package com.kurukshetra.view.family;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Cursor;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.ToggleButton;
// import javafx.scene.input.MouseEvent;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Circle;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.stage.Stage;

// // This class builds the "Emergency Services" screen.
// // It does NOT extend Application - it only returns a BorderPane that
// // gets dropped onto the app's single shared Stage/Scene, exactly like
// // every other page (FamilyHomePage, FamilyFindCare, ...).
// public class EmergencyServices {

//     // ======================================================
//     // COLORS (kept as the original inline palette for this page's content)
//     // ======================================================

//     private final String RED = "#E83D45";
//     private final String DARK_TEXT = "#252A3B";
//     private final String MUTED = "#74798B";
//     private final String BLUE = "#C5E7FA";
//     private final String TEAL = "#005A72";
//     private final String GREEN = "#31AA6B";

//     // ======================================================
//     // ENTRY POINT
//     // ======================================================

//     public BorderPane setBorderPane(Stage stage) {

//         BorderPane root = new BorderPane();
//         root.getStyleClass().add("root-pane");

//         // ==================================================
//         // SIDEBAR (shared across every page)
//         // ==================================================

//         VBox sidebar = Sidebar.build(stage, Sidebar.Page.EMERGENCY);
//         root.setLeft(sidebar);

//         // ==================================================
//         // MAIN AREA
//         // ==================================================

//         VBox mainContent = new VBox(14);
//         mainContent.setPadding(new Insets(20, 24, 20, 24));
//         mainContent.setFillWidth(true);

//         BorderPane header = createHeader();

//         VBox sosCard = createSOSCard();
//         VBox locationCard = createLocationCard();

//         HBox topSection = new HBox(14, sosCard, locationCard);
//         HBox.setHgrow(locationCard, Priority.ALWAYS);

//         VBox contactsCard = createContactsCard();
//         VBox mapCard = createMapCard();

//         HBox bottomSection = new HBox(14, contactsCard, mapCard);
//         HBox.setHgrow(mapCard, Priority.ALWAYS);

//         mainContent.getChildren().addAll(header, topSection, bottomSection);

//         ScrollPane scrollPane = new ScrollPane(mainContent);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//         scrollPane.getStyleClass().add("main-scroll");

//         root.setCenter(scrollPane);
//         MedicalReports.playPageAnimation(scrollPane);

//         return root;
//     }

//     // ======================================================
//     // HEADER
//     // ======================================================

//     private BorderPane createHeader() {

//         BorderPane header = new BorderPane();

//         Label title = new Label("Emergency Services");
//         title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
//         title.setTextFill(Color.web(RED));

//         HBox rightIcons = new HBox(14);
//         rightIcons.setAlignment(Pos.CENTER_RIGHT);

//         Label notification = new Label("🔔");
//         Label help = new Label("❓");
//         notification.setStyle("-fx-font-size: 14px; -fx-text-fill: #37405A;");
//         help.setStyle("-fx-font-size: 14px; -fx-text-fill: #37405A;");

//         rightIcons.getChildren().addAll(notification, help);

//         header.setLeft(title);
//         header.setRight(rightIcons);

//         return header;
//     }

//     // ======================================================
//     // SOS CARD
//     // ======================================================

//     private VBox createSOSCard() {

//         VBox card = createCard();
//         card.setPrefWidth(380);

//         Label title = new Label("Need Immediate Help?");
//         title.setFont(Font.font("Arial", FontWeight.BOLD, 13));
//         title.setTextFill(Color.web("#333849"));

//         Label description = new Label(
//                 "Slide to call emergency services (911) or notify\nyour emergency contacts.");
//         description.setAlignment(Pos.CENTER);
//         description.setStyle("-fx-font-size: 10px; -fx-text-fill: " + MUTED + ";");

//         SosSlider slider = new SosSlider();

//         card.getChildren().addAll(title, description, slider);

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

//             setPrefHeight(48);
//             setMinHeight(48);

//             Region track = new Region();
//             track.setMaxWidth(Double.MAX_VALUE);
//             track.setPrefHeight(48);
//             track.setStyle("-fx-background-color: #FFD6D6; -fx-background-radius: 25;");

//             Label text = new Label("SLIDE TO SOS  »");
//             text.setStyle("-fx-text-fill: #C36B72; -fx-font-size: 10px; -fx-font-weight: bold;");

//             Circle circle = new Circle(18, Color.web(RED));
//             Label phone = new Label("☎");
//             phone.setTextFill(Color.WHITE);
//             phone.setFont(Font.font("Arial", FontWeight.BOLD, 14));

//             handle.getChildren().addAll(circle, phone);
//             handle.setMinSize(38, 38);
//             handle.setMaxSize(38, 38);
//             handle.setCursor(Cursor.HAND);

//             StackPane.setAlignment(handle, Pos.CENTER_LEFT);
//             StackPane.setMargin(handle, new Insets(0, 0, 0, 4));

//             getChildren().addAll(track, text, handle);

//             handle.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
//                 startX = event.getSceneX();
//                 initialX = handle.getTranslateX();
//                 event.consume();
//             });

//             handle.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
//                 double movement = event.getSceneX() - startX;
//                 double max = Math.max(0, getWidth() - 50);
//                 double newX = initialX + movement;
//                 if (newX < 0) newX = 0;
//                 if (newX > max) newX = max;
//                 handle.setTranslateX(newX);
//                 event.consume();
//             });

//             handle.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
//                 double max = Math.max(1, getWidth() - 50);
//                 double progress = handle.getTranslateX() / max;

//                 if (progress >= 0.80) {
//                     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                     alert.setTitle("Confirm SOS");
//                     alert.setHeaderText("Initiate Emergency SOS?");
//                     alert.setContentText(
//                             "You are about to initiate an emergency SOS action.\n\n" +
//                             "This JavaFX prototype will simulate the emergency alert.");
//                     alert.showAndWait();
//                 }

//                 handle.setTranslateX(0);
//                 event.consume();
//             });
//         }
//     }

//     // ======================================================
//     // LOCATION CARD
//     // ======================================================

//     private VBox createLocationCard() {

//         VBox card = createCard();
//         card.setMaxWidth(Double.MAX_VALUE);
//         HBox.setHgrow(card, Priority.ALWAYS);

//         BorderPane header = new BorderPane();

//         VBox labels = new VBox(2);
//         Label title = new Label("⌖  Current Location & Nearest ER");
//         title.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #34394A;");

//         Label address = new Label("124 Maple Street, Seattle, WA");
//         address.setStyle("-fx-font-size: 10px; -fx-text-fill: " + MUTED + ";");

//         labels.getChildren().addAll(title, address);

//         Label locate = new Label("◎");
//         locate.setStyle("-fx-background-color: #F0F3F8; -fx-background-radius: 15; -fx-padding: 5; -fx-font-size: 11px;");

//         header.setLeft(labels);
//         header.setRight(locate);

//         card.getChildren().add(header);

//         return card;
//     }

//     // ======================================================
//     // CONTACTS CARD
//     // ======================================================

//     private VBox createContactsCard() {

//         VBox card = createCard();
//         card.setPrefWidth(380);

//         BorderPane header = new BorderPane();

//         Label title = new Label("👪  Notify Emergency Contacts");
//         title.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #34394A;");

//         Label edit = new Label("Edit");
//         edit.setStyle("-fx-font-size: 10px; -fx-text-fill: #6E7383;");

//         header.setLeft(title);
//         header.setRight(edit);

//         card.getChildren().add(header);

//         card.getChildren().add(createContact("JM", "John Miller", "Husband", "(555) 123-4567"));
//         card.getChildren().add(createContact("EM", "Emily Miller", "Daughter", "(555) 987-6543"));

//         javafx.scene.control.Button quickAlert = new javafx.scene.control.Button("▷  Send Quick Alert Now");
//         quickAlert.setMaxWidth(Double.MAX_VALUE);
//         quickAlert.setPrefHeight(32);
//         quickAlert.setCursor(Cursor.HAND);
//         quickAlert.setStyle(
//                 "-fx-background-color: " + BLUE + ";" +
//                 "-fx-background-radius: 7;" +
//                 "-fx-text-fill: #285A76;" +
//                 "-fx-font-size: 10px;" +
//                 "-fx-font-weight: bold;"
//         );

//         quickAlert.setOnAction(event -> {
//             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//             alert.setTitle("Quick Alert");
//             alert.setHeaderText("Send Quick Alert?");
//             alert.setContentText(
//                     "This will simulate sending an emergency alert " +
//                     "to your enabled emergency contacts.");
//             alert.showAndWait();
//         });

//         card.getChildren().add(quickAlert);

//         return card;
//     }

//     // ======================================================
//     // CONTACT ROW
//     // ======================================================

//     private HBox createContact(String initials, String name, String relation, String phone) {

//         HBox row = new HBox(8);
//         row.setAlignment(Pos.CENTER_LEFT);
//         row.setPadding(new Insets(7));
//         row.setStyle("-fx-background-color: #F8F9FD; -fx-background-radius: 8;");

//         StackPane avatar = new StackPane();
//         Circle circle = new Circle(13, Color.web("#DDE6F1"));
//         Label initialsLabel = new Label(initials);
//         initialsLabel.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #566175;");
//         avatar.getChildren().addAll(circle, initialsLabel);

//         VBox information = new VBox(1);
//         Label nameLabel = new Label(name);
//         nameLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #34394A;");

//         Label details = new Label(relation + " • " + phone);
//         details.setStyle("-fx-font-size: 9px; -fx-text-fill: " + MUTED + ";");

//         information.getChildren().addAll(nameLabel, details);

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         ToggleButton toggle = new ToggleButton();
//         toggle.setSelected(true);
//         toggle.setMinSize(36, 20);
//         toggle.setMaxSize(36, 20);
//         updateToggleStyle(toggle);
//         toggle.selectedProperty().addListener((obs, oldValue, newValue) -> updateToggleStyle(toggle));

//         row.getChildren().addAll(avatar, information, spacer, toggle);

//         return row;
//     }

//     // ======================================================
//     // TOGGLE STYLE
//     // ======================================================

//     private void updateToggleStyle(ToggleButton toggle) {
//         if (toggle.isSelected()) {
//             toggle.setStyle("-fx-background-color: " + GREEN + "; -fx-background-radius: 15; -fx-border-color: transparent;");
//         } else {
//             toggle.setStyle("-fx-background-color: #C7CBD3; -fx-background-radius: 15; -fx-border-color: transparent;");
//         }
//     }

//     // ======================================================
//     // MAP CARD
//     // ======================================================

//     private VBox createMapCard() {

//         VBox card = createCard();
//         card.setMinWidth(430);
//         HBox.setHgrow(card, Priority.ALWAYS);

//         VBox mapPlaceholder = new VBox(8);
//         mapPlaceholder.setAlignment(Pos.CENTER);
//         mapPlaceholder.setPrefHeight(260);
//         mapPlaceholder.setMaxWidth(Double.MAX_VALUE);
//         mapPlaceholder.setStyle(
//                 "-fx-background-color: #F3F6FA;" +
//                 "-fx-background-radius: 8;" +
//                 "-fx-border-color: #DDE3EA;" +
//                 "-fx-border-radius: 8;"
//         );

//         Label mapIcon = new Label("⌖");
//         mapIcon.setStyle("-fx-font-size: 34px; -fx-text-fill: " + TEAL + ";");

//         Label mapTitle = new Label("Emergency Location Map");
//         mapTitle.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #34394A;");

//         Label mapText = new Label("Interactive map will be connected here");
//         mapText.setStyle("-fx-font-size: 10px; -fx-text-fill: " + MUTED + ";");

//         mapPlaceholder.getChildren().addAll(mapIcon, mapTitle, mapText);
//         VBox.setVgrow(mapPlaceholder, Priority.ALWAYS);

//         javafx.scene.control.Button directions = new javafx.scene.control.Button("◉  Get Directions to Nearest ER");
//         directions.setMaxWidth(Double.MAX_VALUE);
//         directions.setPrefHeight(32);
//         directions.setCursor(Cursor.HAND);
//         directions.setStyle(
//                 "-fx-background-color: " + TEAL + ";" +
//                 "-fx-background-radius: 7;" +
//                 "-fx-text-fill: white;" +
//                 "-fx-font-size: 10px;" +
//                 "-fx-font-weight: bold;"
//         );

//         directions.setOnAction(event -> {
//             Alert alert = new Alert(Alert.AlertType.INFORMATION);
//             alert.setTitle("Emergency Directions");
//             alert.setHeaderText("Nearest Emergency Room");
//             alert.setContentText("Directions functionality will be connected to the map service.");
//             alert.showAndWait();
//         });

//         card.getChildren().addAll(mapPlaceholder, directions);

//         return card;
//     }

//     // ======================================================
//     // CARD STYLE
//     // ======================================================

//     private VBox createCard() {

//         VBox card = new VBox(9);
//         card.setPadding(new Insets(14));
//         card.setStyle(
//                 "-fx-background-color: white;" +
//                 "-fx-background-radius: 12;" +
//                 "-fx-border-color: #E5E6EF;" +
//                 "-fx-border-radius: 12;" +
//                 "-fx-effect: dropshadow(gaussian, rgba(30,40,70,0.07), 10, 0, 0, 2);"
//         );

//         return card;
//     }
// }




package com.kurukshetra.view.family;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EmergencyServices {

    private static final String PRIMARY = "#CA6721";
    private static final String PRIMARY_DARK = "#782F16";
    private static final String PAGE_BG = "#FCF9F7";
    private static final String SURFACE = "#FFFFFF";
    private static final String VERY_LIGHT_TERRACOTTA = "#FDF0E8";
    private static final String LIGHT_TERRACOTTA = "#F9E4D6";
    private static final String PALE_PEACH = "#FEF7F2";
    private static final String TEXT_PRIMARY = "#2B211D";
    private static final String TEXT_MUTED = "#938780";
    private static final String BORDER_COLOR = "#EDE0D8";
    private static final String DANGER_BG = "#FCE8E7";
    private static final String DANGER_TEXT = "#C94F4F";
    private static final String GREEN = "#28785B";

    public BorderPane setBorderPane(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + PAGE_BG + ";");

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.EMERGENCY);
        root.setLeft(sidebar);

        VBox mainContent = new VBox(14);
        mainContent.setPadding(new Insets(20, 24, 20, 24));
        mainContent.setFillWidth(true);
        mainContent.setStyle("-fx-background-color: " + PAGE_BG + ";");

        BorderPane header = createHeader();
        VBox sosCard = createSOSCard();
        VBox locationCard = createLocationCard();

        HBox topSection = new HBox(14, sosCard, locationCard);
        HBox.setHgrow(locationCard, Priority.ALWAYS);

        VBox contactsCard = createContactsCard();
        VBox mapCard = createMapCard();

        HBox bottomSection = new HBox(14, contactsCard, mapCard);
        HBox.setHgrow(mapCard, Priority.ALWAYS);

        mainContent.getChildren().addAll(header, topSection, bottomSection);

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle(
                "-fx-background: " + PAGE_BG + ";" +
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );

        root.setCenter(scrollPane);
        MedicalReports.playPageAnimation(scrollPane);
        return root;
    }

    private BorderPane createHeader() {
        BorderPane header = new BorderPane();

        Label title = new Label("Emergency Services");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));
        title.setTextFill(Color.web(DANGER_TEXT));

        HBox rightIcons = new HBox(14);
        rightIcons.setAlignment(Pos.CENTER_RIGHT);

        Label notification = new Label("🔔");
        Label help = new Label("❓");
        styleUtilityIconButton(notification);
        styleUtilityIconButton(help);

        rightIcons.getChildren().addAll(notification, help);
        header.setLeft(title);
        header.setRight(rightIcons);
        return header;
    }

    private VBox createSOSCard() {
        VBox card = createCard();
        card.setPrefWidth(380);

        Label title = new Label("Need Immediate Help?");
        title.setFont(Font.font("System", FontWeight.BOLD, 14));
        title.setTextFill(Color.web(TEXT_PRIMARY));

        Label description = new Label("Slide to call emergency services (911) or notify\nyour emergency contacts.");
        description.setAlignment(Pos.CENTER);
        description.setStyle("-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + "; -fx-line-spacing: 2px;");

        SosSlider slider = new SosSlider();
        card.getChildren().addAll(title, description, slider);
        return card;
    }

    private class SosSlider extends StackPane {
        private final StackPane handle = new StackPane();
        private double startX;
        private double initialX;

        public SosSlider() {
            setPrefHeight(48);
            setMinHeight(48);

            Region track = new Region();
            track.setMaxWidth(Double.MAX_VALUE);
            track.setPrefHeight(48);
            track.setStyle("-fx-background-color: " + DANGER_BG + "; -fx-background-radius: 25; -fx-border-color: #F8D3D1; -fx-border-radius: 25;");

            Label text = new Label("SLIDE TO SOS  »");
            text.setStyle("-fx-text-fill: " + DANGER_TEXT + "; -fx-font-size: 11px; -fx-font-weight: bold;");

            Circle circle = new Circle(18, Color.web(DANGER_TEXT));
            Label phone = new Label("☎");
            phone.setTextFill(Color.WHITE);
            phone.setFont(Font.font("System", FontWeight.BOLD, 14));

            handle.getChildren().addAll(circle, phone);
            handle.setMinSize(38, 38);
            handle.setMaxSize(38, 38);
            handle.setCursor(Cursor.HAND);

            StackPane.setAlignment(handle, Pos.CENTER_LEFT);
            StackPane.setMargin(handle, new Insets(0, 0, 0, 4));

            getChildren().addAll(track, text, handle);

            handle.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
                startX = event.getSceneX();
                initialX = handle.getTranslateX();
                event.consume();
            });

            handle.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
                double movement = event.getSceneX() - startX;
                double max = Math.max(0, getWidth() - 50);
                double newX = initialX + movement;
                if (newX < 0) newX = 0;
                if (newX > max) newX = max;
                handle.setTranslateX(newX);
                event.consume();
            });

            handle.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
                double max = Math.max(1, getWidth() - 50);
                double progress = handle.getTranslateX() / max;

                if (progress >= 0.80) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm SOS");
                    alert.setHeaderText("Initiate Emergency SOS?");
                    alert.setContentText("You are about to initiate an emergency SOS action.\n\nThis JavaFX prototype will simulate the emergency alert.");
                    alert.showAndWait();
                }
                handle.setTranslateX(0);
                event.consume();
            });
        }
    }

    private VBox createLocationCard() {
        VBox card = createCard();
        card.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(card, Priority.ALWAYS);

        BorderPane header = new BorderPane();
        VBox labels = new VBox(2);
        Label title = new Label("⌖  Current Location & Nearest ER");
        title.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label address = new Label("124 Maple Street, Seattle, WA");
        address.setStyle("-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");
        labels.getChildren().addAll(title, address);

        Label locate = new Label("◎");
        locate.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY + ";" +
                "-fx-background-radius: 15;" +
                "-fx-padding: 5 8;" +
                "-fx-font-size: 13px;"
        );

        header.setLeft(labels);
        header.setRight(locate);
        card.getChildren().add(header);
        return card;
    }

    private VBox createContactsCard() {
        VBox card = createCard();
        card.setPrefWidth(380);

        BorderPane header = new BorderPane();
        Label title = new Label("👪  Notify Emergency Contacts");
        title.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label edit = new Label("Edit");
        edit.setStyle("-fx-font-size: 11px; -fx-text-fill: " + PRIMARY + "; -fx-cursor: hand; -fx-font-weight: bold;");

        header.setLeft(title);
        header.setRight(edit);
        card.getChildren().add(header);

        card.getChildren().add(createContact("JM", "John Miller", "Husband", "(555) 123-4567"));
        card.getChildren().add(createContact("EM", "Emily Miller", "Daughter", "(555) 987-6543"));

        Button quickAlert = new Button("▷  Send Quick Alert Now");
        quickAlert.setMaxWidth(Double.MAX_VALUE);
        quickAlert.setPrefHeight(34);
        quickAlert.setCursor(Cursor.HAND);
        quickAlert.setStyle(
                "-fx-background-color: " + PRIMARY + ";" +
                "-fx-background-radius: 8;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        );
        quickAlert.setOnMouseEntered(e -> quickAlert.setStyle(
                "-fx-background-color: " + PRIMARY_DARK + ";" +
                "-fx-background-radius: 8;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        ));
        quickAlert.setOnMouseExited(e -> quickAlert.setStyle(
                "-fx-background-color: " + PRIMARY + ";" +
                "-fx-background-radius: 8;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        ));

        quickAlert.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Quick Alert");
            alert.setHeaderText("Send Quick Alert?");
            alert.setContentText("This will simulate sending an emergency alert to your enabled emergency contacts.");
            alert.showAndWait();
        });

        card.getChildren().add(quickAlert);
        return card;
    }

    private HBox createContact(String initials, String name, String relation, String phone) {
        HBox row = new HBox(8);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(8));
        row.setStyle("-fx-background-color: " + PALE_PEACH + "; -fx-background-radius: 8;");

        StackPane avatar = new StackPane();
        Circle circle = new Circle(13, Color.web(LIGHT_TERRACOTTA));
        Label initialsLabel = new Label(initials);
        initialsLabel.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_DARK + ";");
        avatar.getChildren().addAll(circle, initialsLabel);

        VBox information = new VBox(1);
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label details = new Label(relation + " • " + phone);
        details.setStyle("-fx-font-size: 9px; -fx-text-fill: " + TEXT_MUTED + ";");
        information.getChildren().addAll(nameLabel, details);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ToggleButton toggle = new ToggleButton();
        toggle.setSelected(true);
        toggle.setMinSize(36, 20);
        toggle.setMaxSize(36, 20);
        updateToggleStyle(toggle);
        toggle.selectedProperty().addListener((obs, oldValue, newValue) -> updateToggleStyle(toggle));

        row.getChildren().addAll(avatar, information, spacer, toggle);
        return row;
    }

    private void updateToggleStyle(ToggleButton toggle) {
        if (toggle.isSelected()) {
            toggle.setStyle("-fx-background-color: " + GREEN + "; -fx-background-radius: 15; -fx-border-color: transparent;");
        } else {
            toggle.setStyle("-fx-background-color: #C7CBD3; -fx-background-radius: 15; -fx-border-color: transparent;");
        }
    }

    private VBox createMapCard() {
        VBox card = createCard();
        card.setMinWidth(430);
        HBox.setHgrow(card, Priority.ALWAYS);

        VBox mapPlaceholder = new VBox(8);
        mapPlaceholder.setAlignment(Pos.CENTER);
        mapPlaceholder.setPrefHeight(260);
        mapPlaceholder.setMaxWidth(Double.MAX_VALUE);
        mapPlaceholder.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 8;"
        );

        Label mapIcon = new Label("⌖");
        mapIcon.setStyle("-fx-font-size: 34px; -fx-text-fill: " + PRIMARY + ";");

        Label mapTitle = new Label("Emergency Location Map");
        mapTitle.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label mapText = new Label("Interactive map will be connected here");
        mapText.setStyle("-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");

        mapPlaceholder.getChildren().addAll(mapIcon, mapTitle, mapText);
        VBox.setVgrow(mapPlaceholder, Priority.ALWAYS);

        Button directions = new Button("◉  Get Directions to Nearest ER");
        directions.setMaxWidth(Double.MAX_VALUE);
        directions.setPrefHeight(34);
        directions.setCursor(Cursor.HAND);
        directions.setStyle(
                "-fx-background-color: " + PRIMARY_DARK + ";" +
                "-fx-background-radius: 8;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        );
        directions.setOnMouseEntered(e -> directions.setStyle(
                "-fx-background-color: " + PRIMARY + ";" +
                "-fx-background-radius: 8;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        ));
        directions.setOnMouseExited(e -> directions.setStyle(
                "-fx-background-color: " + PRIMARY_DARK + ";" +
                "-fx-background-radius: 8;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        ));

        directions.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Emergency Directions");
            alert.setHeaderText("Nearest Emergency Room");
            alert.setContentText("Directions functionality will be connected to the map service.");
            alert.showAndWait();
        });

        card.getChildren().addAll(mapPlaceholder, directions);
        return card;
    }

    private VBox createCard() {
        VBox card = new VBox(10);
        card.setPadding(new Insets(16));
        card.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 12;"
        );
        card.setEffect(new DropShadow(12, 0, 4, Color.rgb(120, 47, 22, 0.05)));
        return card;
    }

    private void styleUtilityIconButton(Label label) {
        label.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 18px;" +
                "-fx-background-radius: 18px;" +
                "-fx-min-width: 36px;" +
                "-fx-min-height: 36px;" +
                "-fx-alignment: center;" +
                "-fx-font-size: 14px;" +
                "-fx-cursor: hand;"
        );
    }
}