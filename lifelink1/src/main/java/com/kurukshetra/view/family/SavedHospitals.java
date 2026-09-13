package com.kurukshetra.view.family;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Modern, Rich, Animated, and Clean Saved Hospitals & Emergency Facility Bookmarks.
 * Features primary trauma hospital cards, quick route navigation in browser/Google Maps,
 * specialty clinic bookmarks, and direct telephone dialing simulations.
 */
public class SavedHospitals {

    private static final String PRIMARY = "#CA6721";
    private static final String PRIMARY_DARK = "#782F16";
    private static final String PAGE_BG = "#FCF9F7";
    private static final String SURFACE = "#FFFFFF";
    private static final String VERY_LIGHT_TERRACOTTA = "#FDF0E8";
    private static final String LIGHT_TERRACOTTA = "#F9E4D6";
    private static final String PALE_PEACH = "#FEF7F2";
    private static final String TEXT_PRIMARY = "#2B211D";
    private static final String TEXT_SECONDARY = "#675B55";
    private static final String TEXT_MUTED = "#938780";
    private static final String BORDER_COLOR = "#EDE0D8";
    private static final String GREEN = "#16A34A";
    private static final String LIGHT_GREEN = "#E8F5EC";

    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";

    public BorderPane setBorderPane(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.SAVED_HOSPITALS);
        root.setLeft(sidebar);

        VBox main = new VBox(18);
        main.setPadding(new Insets(20, 24, 24, 24));
        main.setFillWidth(true);
        main.setStyle("-fx-background-color: " + PAGE_BG + ";");

        HBox heading = createHeading();
        HBox mainHospital = createMainHospital();

        HBox savedHospitalsRow = new HBox(16);
        VBox oakridge = createSmallHospital("Oakridge Specialized Clinic", "Critical Ambulatory Care", "5.8 km", "🏥", "Oakridge Clinic Pune");
        VBox valley = createSmallHospital("Valley Pediatric Trauma Center", "24/7 Neonatal Intensive Care", "9.2 km", "👶", "Valley Pediatric Center Pune");

        HBox.setHgrow(oakridge, Priority.ALWAYS);
        HBox.setHgrow(valley, Priority.ALWAYS);
        savedHospitalsRow.getChildren().addAll(oakridge, valley);

        HBox secondaryRow = new HBox(16);
        VBox heartInstitute = createSmallHospital("Ruby Hall Cardiac Institute", "Emergency Interventional Cardiology", "3.4 km", "💗", "Ruby Hall Clinic Pune");
        VBox deenanath = createSmallHospital("Deenanath Mangeshkar Trauma", "Level 1 Emergency & Burn Unit", "6.1 km", "⚡", "Deenanath Mangeshkar Hospital Pune");

        HBox.setHgrow(heartInstitute, Priority.ALWAYS);
        HBox.setHgrow(deenanath, Priority.ALWAYS);
        secondaryRow.getChildren().addAll(heartInstitute, deenanath);

        main.getChildren().addAll(heading, mainHospital, savedHospitalsRow, secondaryRow);

        ScrollPane scrollPane = new ScrollPane(main);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: " + PAGE_BG + "; -fx-background-color: transparent; -fx-border-color: transparent;");

        root.setCenter(scrollPane);
        MedicalReports.playPageAnimation(scrollPane);
        return root;
    }

    private HBox createHeading() {
        VBox titleBox = new VBox(2);
        Label title = new Label("Saved Emergency Facilities & Hospital Bookmarks");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 22px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label subtitle = new Label("Quick access to your preferred family trauma centers, verified clinics, and emergency routes.");
        subtitle.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_SECONDARY + ";");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox badge = new HBox(6);
        badge.setAlignment(Pos.CENTER);
        badge.setPadding(new Insets(6, 12, 6, 12));
        badge.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 16px;" +
                "-fx-background-radius: 16px;"
        );
        Label icon = new Label("🔖");
        Label text = new Label("5 Bookmarked Centers");
        text.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_DARK + ";");
        badge.getChildren().addAll(icon, text);

        HBox heading = new HBox(16, titleBox, spacer, badge);
        heading.setAlignment(Pos.CENTER_LEFT);
        return heading;
    }

    private HBox createMainHospital() {
        HBox container = new HBox(16);
        container.setPrefHeight(180);

        VBox hospitalCard = new VBox(10);
        hospitalCard.setPadding(new Insets(16));
        hospitalCard.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-background-radius: 16px;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 16px;"
        );
        hospitalCard.setEffect(new DropShadow(10, 0, 3, Color.rgb(120, 47, 22, 0.05)));
        HBox.setHgrow(hospitalCard, Priority.ALWAYS);

        HBox hospitalContent = new HBox(16);
        ImageView hospitalImage = createHospitalImage();
        hospitalContent.getChildren().add(hospitalImage);

        VBox details = new VBox(6);
        HBox.setHgrow(details, Priority.ALWAYS);

        HBox titleRow = new HBox(8);
        titleRow.setAlignment(Pos.CENTER_LEFT);
        Label hospitalName = new Label("Mercy General Hospital & Trauma Center");
        hospitalName.setStyle(FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label primary = new Label("★ Primary Facility");
        primary.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-background-radius: 10px;" +
                "-fx-padding: 3px 8px;" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 10px;"
        );
        titleRow.getChildren().addAll(hospitalName, primary);

        Label address = new Label("📍 1240 Wellness Blvd, Swargate, Pune • 2.4 km away (8 mins transit)");
        address.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " + TEXT_MUTED + ";");

        HBox badges = new HBox(6);
        badges.getChildren().addAll(
                badge("Level 1 Trauma Center", VERY_LIGHT_TERRACOTTA, PRIMARY_DARK),
                badge("24/7 Pediatric ICU", PALE_PEACH, PRIMARY_DARK),
                badge("● ER Admissions Open", LIGHT_GREEN, GREEN)
        );

        Region verticalSpacer = new Region();
        VBox.setVgrow(verticalSpacer, Priority.ALWAYS);

        HBox buttons = new HBox(10);
        Button route = new Button("🧭  Navigate (Google Maps)");
        route.setStyle(
                FONT_FAMILY +
                "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + ");" +
                "-fx-background-radius: 8px;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11.5px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 8 16;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.25), 8, 0, 0, 2);"
        );

        Button call = new Button("☎  Call ER Desk");
        call.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 11.5px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 8 14;" +
                "-fx-cursor: hand;"
        );

        route.setOnAction(event -> openGoogleMapsRoute("Mercy General Hospital Pune"));
        call.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Emergency Hotline");
            alert.setHeaderText("Mercy General Emergency Desk");
            alert.setContentText("Emergency Desk Hotline: +91 20 2450 8800\n\nIn active emergency, ambulance coordination will dispatch automatically.");
            alert.showAndWait();
        });

        buttons.getChildren().addAll(route, call);
        details.getChildren().addAll(titleRow, address, badges, verticalSpacer, buttons);
        hospitalContent.getChildren().add(details);
        hospitalCard.getChildren().add(hospitalContent);

        // Map Overview Card
        VBox mapContainer = createMapContainer();
        mapContainer.setPrefWidth(320);
        mapContainer.setMinWidth(300);

        container.getChildren().addAll(hospitalCard, mapContainer);
        return container;
    }

    private ImageView createHospitalImage() {
        String imageURL = "https://images.unsplash.com/photo-1587351021759-3e566b6af7cc?auto=format&fit=crop&w=400&q=80";
        Image image = new Image(imageURL, 140, 120, false, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(140);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(false);
        imageView.setSmooth(true);

        Rectangle clip = new Rectangle(140, 120);
        clip.setArcWidth(12);
        clip.setArcHeight(12);
        imageView.setClip(clip);

        return imageView;
    }

    private VBox createMapContainer() {
        VBox container = new VBox(8);
        container.setPadding(new Insets(12));
        container.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-background-radius: 16px;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 16px;"
        );
        container.setEffect(new DropShadow(10, 0, 3, Color.rgb(120, 47, 22, 0.05)));

        VBox mapPlaceholder = new VBox(6);
        mapPlaceholder.setAlignment(Pos.CENTER);
        mapPlaceholder.setPrefHeight(105);
        mapPlaceholder.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(mapPlaceholder, Priority.ALWAYS);
        mapPlaceholder.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-background-radius: 10px;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 10px;"
        );

        Label mapIcon = new Label("⌖");
        mapIcon.setStyle("-fx-font-size: 24px; -fx-text-fill: " + PRIMARY + ";");

        Label mapTitle = new Label("Live GPS Route Direct");
        mapTitle.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label mapText = new Label("Real-time live Google Maps route guidance");
        mapText.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-text-fill: " + TEXT_MUTED + ";");

        mapPlaceholder.getChildren().addAll(mapIcon, mapTitle, mapText);

        Button directions = new Button("🧭 Open Route in Maps");
        directions.setMaxWidth(Double.MAX_VALUE);
        directions.setPrefHeight(34);
        directions.setCursor(Cursor.HAND);
        directions.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-border-color: " + PRIMARY + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        );
        directions.setOnAction(event -> openGoogleMapsRoute("Mercy General Hospital Pune"));

        container.getChildren().addAll(mapPlaceholder, directions);
        return container;
    }

    private VBox createSmallHospital(String line1, String line2, String distance, String icon, String searchQuery) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(14));
        card.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-background-radius: 14px;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 14px;" +
                "-fx-cursor: hand;"
        );
        card.setEffect(new DropShadow(8, 0, 2, Color.rgb(120, 47, 22, 0.04)));

        HBox content = new HBox(12);
        content.setAlignment(Pos.CENTER_LEFT);

        StackPane iconBox = new StackPane();
        iconBox.setMinSize(42, 42);
        iconBox.setMaxSize(42, 42);
        iconBox.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
        );

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 18px;");
        iconBox.getChildren().add(iconLabel);

        VBox information = new VBox(2);
        HBox.setHgrow(information, Priority.ALWAYS);

        Label hospitalName = new Label(line1);
        hospitalName.setStyle(FONT_FAMILY + "-fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-size: 13px; -fx-font-weight: bold;");

        Label second = new Label(line2);
        second.setStyle(FONT_FAMILY + "-fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 11px;");

        Label distanceLabel = new Label("📍 " + distance + " relative to Swargate GPS");
        distanceLabel.setStyle(FONT_FAMILY + "-fx-text-fill: " + PRIMARY + "; -fx-font-size: 10.5px; -fx-font-weight: 600;");

        information.getChildren().addAll(hospitalName, second, distanceLabel);

        HBox actions = new HBox(8);
        actions.setAlignment(Pos.CENTER_RIGHT);

        Button routeBtn = new Button("🧭 Route");
        routeBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 4 10;" +
                "-fx-cursor: hand;"
        );
        routeBtn.setOnAction(e -> openGoogleMapsRoute(searchQuery));

        Button callBtn = new Button("📞 Call");
        callBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PALE_PEACH + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                "-fx-font-size: 11px;" +
                "-fx-padding: 4 10;" +
                "-fx-cursor: hand;"
        );
        callBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Contact Facility");
            alert.setHeaderText(line1);
            alert.setContentText("Emergency coordination hotline verified: +91 20 2600 0000");
            alert.showAndWait();
        });

        actions.getChildren().addAll(routeBtn, callBtn);
        content.getChildren().addAll(iconBox, information, actions);

        card.getChildren().add(content);

        card.setOnMouseEntered(e -> {
            card.setTranslateY(-2);
            card.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + PALE_PEACH + ";" +
                    "-fx-background-radius: 14px;" +
                    "-fx-border-color: " + PRIMARY + ";" +
                    "-fx-border-width: 1.2px;" +
                    "-fx-border-radius: 14px;" +
                    "-fx-cursor: hand;"
            );
        });

        card.setOnMouseExited(e -> {
            card.setTranslateY(0);
            card.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + SURFACE + ";" +
                    "-fx-background-radius: 14px;" +
                    "-fx-border-color: " + BORDER_COLOR + ";" +
                    "-fx-border-width: 1px;" +
                    "-fx-border-radius: 14px;" +
                    "-fx-cursor: hand;"
            );
        });

        return card;
    }

    private Label badge(String text, String background, String foreground) {
        Label label = new Label(text);
        label.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + background + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-padding: 3px 8px;" +
                "-fx-text-fill: " + foreground + ";" +
                "-fx-font-size: 9.5px;" +
                "-fx-font-weight: bold;"
        );
        return label;
    }

    private void openGoogleMapsRoute(String query) {
        try {
            String url = "https://www.google.com/maps/search/?api=1&query=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Route Information");
            alert.setHeaderText("Destination: " + query);
            alert.setContentText("Direct browser navigation failed: " + ex.getMessage());
            alert.showAndWait();
        }
    }
}