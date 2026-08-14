

package com.kurukshetra.view.family;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class SavedHospitals {

    // COLORS (kept as the original inline palette for this page's content)

    private static final String DARK_TEXT = "#252A3A";
    private static final String MUTED = "#777C8D";
    private static final String RED = "#E62F39";
    private static final String BORDER = "#E4E5EF";
    private static final String TEAL = "#00566F";

    // ENTRY POINT

    public BorderPane setBorderPane(Stage stage) {

        BorderPane root = new BorderPane();
        root.getStyleClass().add("root-pane");

        // SIDEBAR (shared across every page)

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.SAVED_HOSPITALS);
        root.setLeft(sidebar);

        // MAIN CONTENT

        VBox main = new VBox(18);
        main.setPadding(new Insets(25, 25, 25, 28));
        main.setFillWidth(true);

        VBox heading = createHeading();
        HBox mainHospital = createMainHospital();

        HBox savedHospitalsRow = new HBox(16);

        VBox oakridge = createSmallHospital("Oakridge Specialized", "Clinic", "5.8 mi", "▣");
        VBox valley = createSmallHospital("Valley Pediatric Center", "", "9.2 mi", "♙");

        HBox.setHgrow(oakridge, Priority.ALWAYS);
        HBox.setHgrow(valley, Priority.ALWAYS);

        savedHospitalsRow.getChildren().addAll(oakridge, valley);

        main.getChildren().addAll(heading, mainHospital, savedHospitalsRow);

        ScrollPane scrollPane = new ScrollPane(main);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.getStyleClass().add("main-scroll");

        root.setCenter(scrollPane);

        return root;
    }

    // PAGE HEADING

    private VBox createHeading() {

        VBox heading = new VBox(3);

        Label title = new Label("Saved Hospitals");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setTextFill(Color.web(DARK_TEXT));

        Label subtitle = new Label(
                "Quick access to your preferred healthcare facilities and emergency contacts.");
        subtitle.setStyle("-fx-font-size: 11px; -fx-text-fill: " + MUTED + ";");

        heading.getChildren().addAll(title, subtitle);

        return heading;
    }

    // MAIN HOSPITAL CARD

    private HBox createMainHospital() {

        HBox container = new HBox(12);
        container.setPrefHeight(160);

          // HOSPITAL INFORMATION
        VBox hospitalCard = new VBox(7);
        hospitalCard.setPadding(new Insets(14));
        hospitalCard.setPrefWidth(550);
        hospitalCard.setStyle("-fx-background-color: white;" +"-fx-background-radius: 12;" +"-fx-border-color: " + BORDER + ";" +"-fx-border-radius: 12;" +"-fx-effect: dropshadow(gaussian, rgba(30,40,70,0.06), 9, 0, 0, 2);");
        HBox.setHgrow(hospitalCard, Priority.ALWAYS);

        HBox hospitalContent = new HBox(14);

        ImageView hospitalImage = createHospitalImage();
        hospitalContent.getChildren().add(hospitalImage);

        VBox details = new VBox(6);

        HBox titleRow = new HBox(8);
        Label hospitalName = new Label("Mercy General Hospital");
        hospitalName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        hospitalName.setTextFill(Color.web(DARK_TEXT));

        Label primary = new Label("★ Primary");
        primary.setStyle("-fx-background-color: #E9F3F8;" +"-fx-background-radius: 10;" +"-fx-padding: 3 7;" +"-fx-text-fill: #24617A;" +"-fx-font-size: 10px;" +"-fx-font-weight: bold;");

        titleRow.getChildren().addAll(hospitalName, primary);

        Label address = new Label("⌖ 1240 Wellness Blvd, Springfield");
        address.setStyle("-fx-font-size: 11px; -fx-text-fill: " + MUTED + ";");

        HBox badges = new HBox(6);
        badges.getChildren().addAll(
                badge("Level 1 Trauma", "#E6ECFA", "#4D5F8D"),
                badge("Pediatric Care", "#E8F1FC", "#50709A"),
                badge("● ER Open", "#E1F5E9", "#3A8661")
        );

        Region verticalSpacer = new Region();
        VBox.setVgrow(verticalSpacer, Priority.ALWAYS);

        HBox buttons = new HBox(8);

        Button route = smallButton("◆  Route", TEAL);
        Button call = smallButton("☎  Call", "#B9E2F7");
        call.setTextFill(Color.web("#255D79"));

        route.setOnAction(event -> showRoute());
        call.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Call Hospital");
            alert.setHeaderText("Mercy General Hospital");
            alert.setContentText("Calling (555) 245-8800...\n\n" +
                    "This is a UI prototype, so no real call will be placed.");
            alert.showAndWait();
        });

        buttons.getChildren().addAll(route, call);

        details.getChildren().addAll(titleRow, address, badges, verticalSpacer, buttons);
        hospitalContent.getChildren().add(details);
        hospitalCard.getChildren().add(hospitalContent);

        // MAP CONTAINER
        VBox mapContainer = createMapContainer();
        mapContainer.setPrefWidth(330);
        HBox.setHgrow(mapContainer, Priority.ALWAYS);

        container.getChildren().addAll(hospitalCard, mapContainer);

        return container;
    }

    // HOSPITAL IMAGE
  
    private ImageView createHospitalImage() {

        String imageURL =
                "https://images.unsplash.com/photo-1587351021759-3e566b6af7cc?auto=format&fit=crop&w=400&q=80";

        Image image = new Image(imageURL, 130, 100, false, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(130);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(false);
        imageView.setSmooth(true);

        return imageView;
    }

    // MAP CONTAINER
    private VBox createMapContainer() {

        VBox container = new VBox(8);
        container.setPadding(new Insets(8));
        container.setStyle("-fx-background-color: white;" +"-fx-background-radius: 12;" +"-fx-border-color: " + BORDER + ";" +"-fx-border-radius: 12;" +"-fx-effect: dropshadow(gaussian, rgba(30,40,70,0.06), 9, 0, 0, 2);");

        VBox mapPlaceholder = new VBox(7);
        mapPlaceholder.setAlignment(Pos.CENTER);
        mapPlaceholder.setPrefHeight(130);
        mapPlaceholder.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(mapPlaceholder, Priority.ALWAYS);
        mapPlaceholder.setStyle("-fx-background-color: #F1F5F8;" +"-fx-background-radius: 8;" +"-fx-border-color: #DDE3EA;" +"-fx-border-radius: 8;");

        Label mapIcon = new Label("⌖");
        mapIcon.setStyle("-fx-font-size: 28px; -fx-text-fill: " + TEAL + ";");

        Label mapTitle = new Label("Hospital Location");
        mapTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + DARK_TEXT + ";");

        Label mapText = new Label("Interactive map temporarily disabled");
        mapText.setStyle("-fx-font-size: 10px; -fx-text-fill: " + MUTED + ";");

        mapPlaceholder.getChildren().addAll(mapIcon, mapTitle, mapText);

        Button directions = new Button("◆  Get Directions");
        directions.setMaxWidth(Double.MAX_VALUE);
        directions.setPrefHeight(30);
        directions.setCursor(Cursor.HAND);
        directions.setStyle("-fx-background-color: " + TEAL + ";" +"-fx-background-radius: 7;" +"-fx-text-fill: white;" +"-fx-font-size: 10px;" +"-fx-font-weight: bold;");
        directions.setOnAction(event -> showRoute());

        container.getChildren().addAll(mapPlaceholder, directions);

        return container;
    }

   // SMALL HOSPITAL CARD
   
    private VBox createSmallHospital(String line1, String line2, String distance, String icon) {

        VBox card = new VBox();
        card.setPadding(new Insets(12));
        card.setPrefHeight(90);
        card.setStyle("-fx-background-color: white;" +"-fx-background-radius: 12;" +"-fx-border-color: " + BORDER + ";" +"-fx-border-radius: 12;" +"-fx-effect: dropshadow(gaussian, rgba(30,40,70,0.05), 8, 0, 0, 2);");

        HBox content = new HBox(10);
        content.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBox = new StackPane();
        iconBox.setMinSize(42, 42);
        iconBox.setMaxSize(42, 42);
        iconBox.setStyle("-fx-background-color: #EFF1FB; -fx-background-radius: 6;");

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-text-fill: #7392B3; -fx-font-size: 16px;");
        iconBox.getChildren().add(iconLabel);

        VBox information = new VBox(2);
        Label hospitalName = new Label(line1);
        hospitalName.setStyle("-fx-text-fill: " + DARK_TEXT + "; -fx-font-size: 12px; -fx-font-weight: bold;");

        Label second = new Label(line2);
        second.setStyle("-fx-text-fill: " + DARK_TEXT + "; -fx-font-size: 12px; -fx-font-weight: bold;");

        Label distanceLabel = new Label("⌖ " + distance);
        distanceLabel.setStyle("-fx-text-fill: " + MUTED + "; -fx-font-size: 10px;");

        information.getChildren().add(hospitalName);
        if (!line2.isEmpty()) {
            information.getChildren().add(second);
        }
        information.getChildren().add(distanceLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label bookmark = new Label("▮");
        bookmark.setStyle("-fx-text-fill: " + TEAL + "; -fx-font-size: 12px;");

        content.getChildren().addAll(iconBox, information, spacer, bookmark);

        HBox buttons = new HBox(6);
        buttons.setPadding(new Insets(4, 0, 0, 52));

        Button route = new Button("◆ Route");
        route.setStyle("-fx-background-color: #C9E9F8;" +"-fx-background-radius: 8;" +"-fx-text-fill: #28647F;" +"-fx-font-size: 10px;" +"-fx-font-weight: bold;" +"-fx-padding: 3 8;");

        Button phone = new Button("☎");
        phone.setStyle("-fx-background-color: #F0F1F7;" +"-fx-background-radius: 8;" +"-fx-text-fill: #46536A;" +"-fx-font-size: 10px;" +"-fx-padding: 3 8;");

        route.setOnAction(event -> showRoute());
        phone.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Contact Hospital");
            alert.setHeaderText(line1);
            alert.setContentText("Hospital contact action selected.");
            alert.showAndWait();
        });

        buttons.getChildren().addAll(route, phone);
        card.getChildren().addAll(content, buttons);
        return card;
    }

    // BADGE

    private Label badge(String text, String background, String foreground) {

        Label label = new Label(text);
        label.setStyle("-fx-background-color: " + background + ";" +"-fx-background-radius: 10;" +"-fx-padding: 3 6;" +"-fx-text-fill: " + foreground + ";" +"-fx-font-size: 9px;" +"-fx-font-weight: bold;");

        return label;
    }

    // BUTTON

    private Button smallButton(String text, String background) {

        Button button = new Button(text);
        button.setPrefWidth(105);
        button.setPrefHeight(28);
        button.setCursor(Cursor.HAND);
        button.setStyle("-fx-background-color: " + background + ";" +"-fx-background-radius: 6;" +"-fx-text-fill: white;" +"-fx-font-size: 10px;" +"-fx-font-weight: bold;");

        return button;
    }

    // ROUTE
   
    private void showRoute() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Route");
        alert.setHeaderText("Directions to Mercy General Hospital");
        alert.setContentText("Route functionality is currently simulated.\n\n" +"The interactive WebView map has been disabled " +"to avoid the JavaFX WebView runtime conflict.");
        alert.showAndWait();
    }
}
