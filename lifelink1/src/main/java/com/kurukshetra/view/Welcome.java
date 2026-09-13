package com.kurukshetra.view;

import com.kurukshetra.view.loginSignup.*;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Welcome extends Application {

    private static final String WELCOME_BACKGROUND = "lifelink_welcome_bg.jpg";

    private static final double CARD_WIDTH = 375;
    private static final double CARD_HEIGHT = 265;
    private static final double GRID_GAP_H = 26;
    private static final double GRID_GAP_V = 24;

    private static final Duration HOVER_DURATION = Duration.millis(180);
    private static final Duration CLICK_DURATION = Duration.millis(70);
    private static final Duration ENTRANCE_DURATION = Duration.millis(420);

    private static final String SVG_HOSPITAL = "M19 10.5h-5.5V5h-3v5.5H5v3h5.5V19h3v-5.5H19z";
    private static final String SVG_AMBULANCE = "M18.92 6.01C18.72 5.42 18.16 5 17.5 5h-11c-.66 0-1.21.42-1.42 1.01L3 12v8c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h12v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-8l-2.08-5.99zM6.5 16c-.83 0-1.5-.67-1.5-1.5S5.67 13 6.5 13s1.5.67 1.5 1.5S7.33 16 6.5 16zm11 0c-.83 0-1.5-.67-1.5-1.5S16.67 13 17.5 13s1.5.67 1.5 1.5S18.33 16 17.5 16zM5 11l1.5-4.5h11L19 11H5z";
    private static final String SVG_POLICE = "M12 1L3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4zm0 6c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3z";
    private static final String SVG_PATIENT = "M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z";
    private static final String SVG_ADMIN = "M12 1L3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4zm-2 16l-4-4 1.41-1.41L10 14.17l6.59-6.59L18 9l-8 8z";
    private static final String SVG_NURSE = "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 14h-2v-3H8v-2h3V8h2v3h3v2h-3v3z";

    public static Stage WelcomeStage;
    private Scene scene;
    public static BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        WelcomeStage = primaryStage;
        root = new BorderPane();

        // High-definition background setup
        Image backgroundImage = loadWelcomeBackground();
        if (backgroundImage != null && !backgroundImage.isError()) {
            BackgroundSize backgroundSize = new BackgroundSize(
                    1.0, 1.0, true, true, false, true);
            BackgroundImage background = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    backgroundSize);
            root.setBackground(new Background(background));
        } else {
            root.setStyle("-fx-background-color: #06152b;");
        }

        // Cinematic deep-blue overlay to make the glossy cards and specular rims pop
        StackPane centerOverlay = new StackPane();
        centerOverlay.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 75%, rgba(6, 20, 44, 0.35) 0%, rgba(3, 10, 24, 0.65) 100%);");

        VBox contentContainer = new VBox(22);
        contentContainer.setAlignment(Pos.CENTER);
        contentContainer.setPadding(new Insets(20, 30, 28, 30));

        VBox header = createHeader();
        List<Node> cardList = new ArrayList<>();
        GridPane cardsGrid = createCardsGrid(cardList);

        contentContainer.getChildren().addAll(header, cardsGrid);
        centerOverlay.getChildren().add(contentContainer);
        root.setCenter(centerOverlay);

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());

        WelcomeStage.setX(visualBounds.getMinX());
        WelcomeStage.setY(visualBounds.getMinY());
        WelcomeStage.setWidth(visualBounds.getWidth());
        WelcomeStage.setHeight(visualBounds.getHeight());
        WelcomeStage.setTitle("LifeLink - Healthcare & Emergency Coordination Platform");

        try (var iconStream = getClass().getResourceAsStream("/assets/Images/lifelinklogonew.png")) {
            if (iconStream != null) {
                WelcomeStage.getIcons().setAll(new Image(iconStream));
            } else {
                File f = new File("src/main/resources/assets/Images/lifelinklogonew.png");
                if (!f.exists()) f = new File("LifeLink/lifelink1/src/main/resources/assets/Images/lifelinklogonew.png");
                if (f.exists()) {
                    WelcomeStage.getIcons().setAll(new Image(f.toURI().toString()));
                }
            }
        } catch (Exception ignored) {}

        WelcomeStage.setScene(scene);
        WelcomeStage.setMaximized(true);

        WelcomeStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        WelcomeStage.show();

        // Staggered luxury glass entrance animation
        playEntranceAnimations(cardList);
    }

    /**
     * Top Header with Title and Marathi Devanagari Subtitle
     */
    private VBox createHeader() {
        VBox header = new VBox(6);
        header.setAlignment(Pos.CENTER);

        Label title = new Label("Welcome to LifeLink");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        title.setTextFill(Color.WHITE);
        title.setEffect(new DropShadow(14, 0, 4, Color.rgb(0, 0, 0, 0.65)));

        Label subtitle = new Label("काळ रक्षा | जीवन रक्षा ||");
        // Use Windows Devanagari UI font "Nirmala UI" or "Segoe UI"
        Font devanagariFont = Font.font("Nirmala UI", FontWeight.BOLD, 18);
        if (devanagariFont == null || "System".equals(devanagariFont.getFamily())) {
            devanagariFont = Font.font("Segoe UI", FontWeight.BOLD, 18);
        }
        subtitle.setFont(devanagariFont);
        subtitle.setTextFill(Color.web("#38BDF8"));
        subtitle.setEffect(new DropShadow(10, 0, 0, Color.rgb(56, 189, 248, 0.55)));

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    /**
     * 3 columns x 2 rows card grid
     */
    private GridPane createCardsGrid(List<Node> cardList) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(GRID_GAP_H);
        grid.setVgap(GRID_GAP_V);

        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setMinWidth(CARD_WIDTH);
            column.setPrefWidth(CARD_WIDTH);
            column.setMaxWidth(CARD_WIDTH);
            column.setHgrow(Priority.NEVER);
            grid.getColumnConstraints().add(column);
        }

        double cardGroupWidth = (CARD_WIDTH * 3) + (GRID_GAP_H * 2);
        grid.setMinWidth(cardGroupWidth);
        grid.setPrefWidth(cardGroupWidth);
        grid.setMaxWidth(cardGroupWidth);

        // 1. Ambulance Driver
        StackPane driverCard = createRoleCard(
                "driver.png",
                SVG_AMBULANCE,
                "Paramedic Dispatch",
                "Ambulance Driver",
                "#F87171",
                "#FCA5A5",
                "rgba(239, 68, 68, 0.22)",
                "rgba(248, 113, 113, 0.50)",
                () -> {
                    DriverLoginPage driverLoginPage = new DriverLoginPage();
                    Scene newScene = new Scene(
                            driverLoginPage.getDriverLoginPage(),
                            WelcomeStage.getWidth(),
                            WelcomeStage.getHeight());
                    WelcomeStage.setScene(newScene);
                    WelcomeStage.setMaximized(true);
                });

        // 2. Ambulance Nurse
        StackPane nurseCard = createRoleCard(
                "nurse.png",
                SVG_NURSE,
                "Clinical Nurse",
                "Ambulance Nurse",
                "#38BDF8",
                "#7DD3FC",
                "rgba(14, 165, 233, 0.22)",
                "rgba(56, 189, 248, 0.50)",
                () -> {
                    NurseLoginPage nurseLoginPage = new NurseLoginPage();
                    Scene newScene = new Scene(
                            nurseLoginPage.getNurseLoginPage(null),
                            WelcomeStage.getWidth(),
                            WelcomeStage.getHeight());
                    WelcomeStage.setScene(newScene);
                    WelcomeStage.setMaximized(true);
                });

        // 3. Hospital Staff
        StackPane hospitalCard = createRoleCard(
                "hospital.png",
                SVG_HOSPITAL,
                "Hospital ER",
                "Hospital",
                "#34D399",
                "#6EE7B7",
                "rgba(16, 185, 129, 0.22)",
                "rgba(52, 211, 153, 0.50)",
                () -> {
                    HospitalLoginPage hospitalLoginPage = new HospitalLoginPage();
                    Scene newScene = new Scene(
                            hospitalLoginPage.getHospitalLoginPage(),
                            WelcomeStage.getWidth(),
                            WelcomeStage.getHeight());
                    WelcomeStage.setScene(newScene);
                    WelcomeStage.setMaximized(true);
                });

        // 4. Police Control Room
        StackPane policeCard = createRoleCard(
                "police.png",
                SVG_POLICE,
                "Police Command",
                "Police Control Room",
                "#818CF8",
                "#A5B4FC",
                "rgba(99, 102, 241, 0.25)",
                "rgba(129, 140, 248, 0.50)",
                () -> {
                    PoliceLoginPage policeLoginPage = new PoliceLoginPage();
                    Scene newScene = new Scene(
                            policeLoginPage.getPoliceLoginPage(),
                            WelcomeStage.getWidth(),
                            WelcomeStage.getHeight());
                    WelcomeStage.setScene(newScene);
                    WelcomeStage.setMaximized(true);
                });

        // 5. Patient & Family
        StackPane familyCard = createRoleCard(
                "family.png",
                SVG_PATIENT,
                "Patient Care",
                "Patient & Family",
                "#FBBF24",
                "#FDE68A",
                "rgba(245, 158, 11, 0.25)",
                "rgba(251, 191, 36, 0.50)",
                () -> {
                    FamilyLoginPage familyLoginPage = new FamilyLoginPage();
                    Scene newScene = new Scene(
                            familyLoginPage.getFamilyLoginPage(),
                            WelcomeStage.getWidth(),
                            WelcomeStage.getHeight());
                    WelcomeStage.setScene(newScene);
                    WelcomeStage.setMaximized(true);
                });

        // 6. System Admin
        StackPane adminCard = createRoleCard(
                "admin.png",
                SVG_ADMIN,
                "Administration",
                "System Admin",
                "#94A3B8",
                "#CBD5E1",
                "rgba(100, 116, 139, 0.30)",
                "rgba(148, 163, 184, 0.50)",
                () -> {
                    AdminLoginPage adminLoginPage = new AdminLoginPage();
                    Scene newScene = new Scene(
                            adminLoginPage.getAdminLoginPage(),
                            WelcomeStage.getWidth(),
                            WelcomeStage.getHeight());
                    WelcomeStage.setScene(newScene);
                    WelcomeStage.setMaximized(true);
                });

        // Row 0
        grid.add(driverCard, 0, 0);
        grid.add(nurseCard, 1, 0);
        grid.add(hospitalCard, 2, 0);

        // Row 1
        grid.add(policeCard, 0, 1);
        grid.add(familyCard, 1, 1);
        grid.add(adminCard, 2, 1);

        cardList.add(driverCard);
        cardList.add(nurseCard);
        cardList.add(hospitalCard);
        cardList.add(policeCard);
        cardList.add(familyCard);
        cardList.add(adminCard);

        return grid;
    }

    /**
     * Creates a high-fidelity glassmorphism card with specular rim reflection,
     * inner showcase box for 3D character, glowing badge pill, and accent arrow.
     */
    private StackPane createRoleCard(
            String imageName,
            String fallbackSvg,
            String badgeText,
            String titleText,
            String accentColorHex,
            String badgeTextColorHex,
            String badgeBgRgba,
            String badgeBorderRgba,
            Runnable onClickAction) {

        StackPane card = new StackPane();
        card.setMinWidth(CARD_WIDTH);
        card.setPrefWidth(CARD_WIDTH);
        card.setMaxWidth(CARD_WIDTH);
        card.setMinHeight(CARD_HEIGHT);
        card.setPrefHeight(CARD_HEIGHT);
        card.setMaxHeight(CARD_HEIGHT);
        card.setCursor(Cursor.HAND);

        // Base glossy glass styling
        String defaultStyle = createCardRestingStyle();
        card.setStyle(defaultStyle);

        DropShadow defaultShadow = new DropShadow(26, 0, 8, Color.rgb(14, 165, 233, 0.20));
        card.setEffect(defaultShadow);

        // Top specular glare sheen across the upper curve of the card
        Region topGlare = new Region();
        topGlare.setPrefHeight(80);
        topGlare.setMaxHeight(80);
        topGlare.setStyle("-fx-background-color: linear-gradient(to bottom, rgba(255, 255, 255, 0.24) 0%, rgba(255, 255, 255, 0.05) 50%, transparent 100%); "
                + "-fx-background-radius: 24 24 0 0;");
        topGlare.setMouseTransparent(true);
        StackPane.setAlignment(topGlare, Pos.TOP_CENTER);

        // Inner 3D character showcase container
        StackPane showcaseBox = new StackPane();
        showcaseBox.setPrefWidth(345);
        showcaseBox.setPrefHeight(154);
        showcaseBox.setMinWidth(345);
        showcaseBox.setMinHeight(154);
        showcaseBox.setMaxWidth(345);
        showcaseBox.setMaxHeight(154);
        showcaseBox.setAlignment(Pos.CENTER);
        showcaseBox.setStyle(
                "-fx-background-color: linear-gradient(to bottom, rgba(255, 255, 255, 0.12) 0%, rgba(255, 255, 255, 0.02) 65%, rgba(255, 255, 255, 0.06) 100%); "
                + "-fx-background-radius: 18; "
                + "-fx-border-color: linear-gradient(to bottom, rgba(255, 255, 255, 0.65) 0%, rgba(255, 255, 255, 0.12) 55%, rgba(255, 255, 255, 0.30) 100%); "
                + "-fx-border-radius: 18; "
                + "-fx-border-width: 1.2;");

        // Inner showcase top specular highlight
        Region innerGleam = new Region();
        innerGleam.setPrefHeight(40);
        innerGleam.setMaxHeight(40);
        innerGleam.setStyle("-fx-background-color: linear-gradient(to bottom, rgba(255, 255, 255, 0.22) 0%, transparent 100%); -fx-background-radius: 17 17 0 0;");
        innerGleam.setMouseTransparent(true);
        StackPane.setAlignment(innerGleam, Pos.TOP_CENTER);

        Image roleImage = loadWelcomeImage(imageName);
        ImageView roleImageView = null;

        if (roleImage != null && !roleImage.isError()) {
            roleImageView = new ImageView(roleImage);
            roleImageView.setFitHeight(144);
            roleImageView.setFitWidth(325);
            roleImageView.setPreserveRatio(true);
            roleImageView.setSmooth(true);
            roleImageView.setEffect(new DropShadow(8, 0, 3, Color.rgb(0, 0, 0, 0.35)));

            showcaseBox.getChildren().addAll(innerGleam, roleImageView);
        } else {
            Node placeholder = createReservedImagePlaceholder(fallbackSvg, accentColorHex, badgeBgRgba);
            showcaseBox.getChildren().addAll(innerGleam, placeholder);
        }

        // Bottom text and action area
        VBox bottomBox = new VBox(6);
        bottomBox.setPadding(new Insets(8, 6, 2, 6));
        bottomBox.setAlignment(Pos.CENTER_LEFT);

        // Badge pill row
        HBox badgeRow = new HBox();
        badgeRow.setAlignment(Pos.CENTER_LEFT);

        Label badgeLabel = new Label(badgeText);
        badgeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        badgeLabel.setTextFill(Color.web(badgeTextColorHex));
        badgeLabel.setStyle(String.format(
                "-fx-background-color: %s; -fx-padding: 3 12 3 12; -fx-background-radius: 20; -fx-border-color: %s; -fx-border-radius: 20; -fx-border-width: 1.0;",
                badgeBgRgba, badgeBorderRgba));
        badgeLabel.setEffect(new DropShadow(8, 0, 0, Color.web(accentColorHex, 0.35)));
        badgeRow.getChildren().add(badgeLabel);

        // Title and Arrow row
        HBox titleRow = new HBox(8);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(titleText);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setEffect(new DropShadow(4, 0, 2, Color.rgb(0, 0, 0, 0.5)));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label arrowLabel = new Label("→");
        arrowLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        arrowLabel.setTextFill(Color.web(accentColorHex));
        arrowLabel.setEffect(new DropShadow(8, 0, 0, Color.web(accentColorHex, 0.65)));

        titleRow.getChildren().addAll(titleLabel, spacer, arrowLabel);

        bottomBox.getChildren().addAll(badgeRow, titleRow);

        // Content layout inside card
        VBox cardContent = new VBox(6);
        cardContent.setPadding(new Insets(12, 12, 10, 12));
        cardContent.setAlignment(Pos.TOP_CENTER);
        cardContent.getChildren().addAll(showcaseBox, bottomBox);

        card.getChildren().addAll(topGlare, cardContent);

        // Setup interactive animations
        setupCardInteractions(card, roleImageView, arrowLabel, defaultStyle, accentColorHex, defaultShadow, onClickAction);

        return card;
    }

    private String createCardRestingStyle() {
        return "-fx-background-color: linear-gradient(to bottom, rgba(255, 255, 255, 0.16) 0%, rgba(255, 255, 255, 0.04) 35%, rgba(10, 28, 56, 0.50) 75%, rgba(255, 255, 255, 0.09) 100%); "
                + "-fx-background-radius: 24; "
                + "-fx-border-color: linear-gradient(to bottom, rgba(255, 255, 255, 0.92) 0%, rgba(255, 255, 255, 0.28) 40%, rgba(255, 255, 255, 0.08) 65%, rgba(255, 255, 255, 0.48) 100%); "
                + "-fx-border-radius: 24; "
                + "-fx-border-width: 1.8;";
    }

    private String createCardHoverStyle(String accentHex) {
        return "-fx-background-color: linear-gradient(to bottom, rgba(255, 255, 255, 0.24) 0%, rgba(255, 255, 255, 0.07) 35%, rgba(10, 28, 56, 0.55) 75%, rgba(255, 255, 255, 0.14) 100%); "
                + "-fx-background-radius: 24; "
                + "-fx-border-color: linear-gradient(to bottom, #FFFFFF 0%, rgba(255, 255, 255, 0.70) 25%, " + accentHex + " 65%, #FFFFFF 100%); "
                + "-fx-border-radius: 24; "
                + "-fx-border-width: 2.2;";
    }

    private StackPane createReservedImagePlaceholder(
            String svgPathData,
            String accentColorHex,
            String badgeBgHex) {

        StackPane placeholder = new StackPane();
        placeholder.setPrefHeight(140);
        placeholder.setMinHeight(140);
        placeholder.setMaxHeight(140);
        placeholder.setAlignment(Pos.CENTER);

        VBox inner = new VBox(8);
        inner.setAlignment(Pos.CENTER);

        SVGPath icon = new SVGPath();
        icon.setContent(svgPathData);
        icon.setFill(Color.web(accentColorHex));

        StackPane iconCircle = new StackPane(icon);
        iconCircle.setPrefSize(48, 48);
        iconCircle.setMaxSize(48, 48);
        iconCircle.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 24;", badgeBgHex));

        Label slotLabel = new Label("Live Portal Ready");
        slotLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 11));
        slotLabel.setTextFill(Color.web("#94a3b8"));

        inner.getChildren().addAll(iconCircle, slotLabel);
        placeholder.getChildren().add(inner);
        return placeholder;
    }

    private void setupCardInteractions(
            StackPane card,
            ImageView roleImageView,
            Label arrowLabel,
            String defaultStyle,
            String accentColorHex,
            DropShadow defaultShadow,
            Runnable onClickAction) {

        DropShadow hoverShadow = new DropShadow(36, 0, 10, Color.web(accentColorHex, 0.45));

        card.setOnMouseEntered(event -> {
            TranslateTransition cardMove = new TranslateTransition(HOVER_DURATION, card);
            cardMove.setToY(-8);

            ScaleTransition cardScale = new ScaleTransition(HOVER_DURATION, card);
            cardScale.setToX(1.025);
            cardScale.setToY(1.025);

            TranslateTransition arrowMove = new TranslateTransition(HOVER_DURATION, arrowLabel);
            arrowMove.setToX(6);

            ParallelTransition pt;
            if (roleImageView != null) {
                ScaleTransition imgScale = new ScaleTransition(HOVER_DURATION, roleImageView);
                imgScale.setToX(1.05);
                imgScale.setToY(1.05);
                pt = new ParallelTransition(cardMove, cardScale, arrowMove, imgScale);
            } else {
                pt = new ParallelTransition(cardMove, cardScale, arrowMove);
            }
            pt.play();

            card.setStyle(createCardHoverStyle(accentColorHex));
            card.setEffect(hoverShadow);
        });

        card.setOnMouseExited(event -> {
            TranslateTransition cardMove = new TranslateTransition(HOVER_DURATION, card);
            cardMove.setToY(0);

            ScaleTransition cardScale = new ScaleTransition(HOVER_DURATION, card);
            cardScale.setToX(1.0);
            cardScale.setToY(1.0);

            TranslateTransition arrowMove = new TranslateTransition(HOVER_DURATION, arrowLabel);
            arrowMove.setToX(0);

            ParallelTransition pt;
            if (roleImageView != null) {
                ScaleTransition imgScale = new ScaleTransition(HOVER_DURATION, roleImageView);
                imgScale.setToX(1.0);
                imgScale.setToY(1.0);
                pt = new ParallelTransition(cardMove, cardScale, arrowMove, imgScale);
            } else {
                pt = new ParallelTransition(cardMove, cardScale, arrowMove);
            }
            pt.play();

            card.setStyle(defaultStyle);
            card.setEffect(defaultShadow);
        });

        card.setOnMousePressed(event -> {
            ScaleTransition scale = new ScaleTransition(CLICK_DURATION, card);
            scale.setToX(0.98);
            scale.setToY(0.98);
            scale.play();
        });

        card.setOnMouseReleased(event -> {
            ScaleTransition scale = new ScaleTransition(CLICK_DURATION, card);
            scale.setToX(1.025);
            scale.setToY(1.025);
            scale.play();
        });

        card.setOnMouseClicked(event -> {
            if (onClickAction != null) {
                onClickAction.run();
            }
        });
    }

    private void playEntranceAnimations(List<Node> cards) {
        int delay = 0;
        for (Node card : cards) {
            card.setOpacity(0);
            card.setTranslateY(25);

            FadeTransition fade = new FadeTransition(ENTRANCE_DURATION, card);
            fade.setToValue(1.0);

            TranslateTransition move = new TranslateTransition(ENTRANCE_DURATION, card);
            move.setToY(0);

            ParallelTransition animation = new ParallelTransition(fade, move);
            animation.setDelay(Duration.millis(delay));
            animation.play();

            delay += 55;
        }
    }

    private Image loadWelcomeImage(String filename) {
        if (filename == null || filename.isBlank()) {
            return null;
        }

        try {
            var url = getClass().getResource("/assets/welcome/" + filename);
            if (url != null) {
                return new Image(url.toExternalForm(), false);
            }
        } catch (Exception ignored) {}

        String[] paths = {
                "src/main/resources/assets/welcome/" + filename,
                "target/classes/assets/welcome/" + filename,
                "assets/welcome/" + filename
        };

        for (String path : paths) {
            try {
                File file = new File(path);
                if (file.exists() && file.isFile()) {
                    return new Image(file.toURI().toString(), false);
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

    private Image loadWelcomeBackground() {
        try {
            var url = getClass().getResource("/assets/welcome/" + WELCOME_BACKGROUND);
            if (url != null) {
                return new Image(url.toExternalForm(), false);
            }
        } catch (Exception ignored) {}

        String[] paths = {
                "src/main/resources/assets/welcome/" + WELCOME_BACKGROUND,
                "target/classes/assets/welcome/" + WELCOME_BACKGROUND,
                "assets/welcome/" + WELCOME_BACKGROUND
        };

        for (String path : paths) {
            try {
                File file = new File(path);
                if (file.exists() && file.isFile()) {
                    return new Image(file.toURI().toString(), false);
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
        System.exit(0);
    }
}