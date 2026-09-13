package com.kurukshetra.view.family.about;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Combined Team Leads + Team Members showcase.
 *
 * Layout:
 *        SHIVTEJ         [ CENTER TEAM SLIDER ]         NADEEM
 *
 * Features:
 * - Center area continuously slides through exactly six cards (Right -> Left).
 * - Mouse enter pauses smoothly at exact position.
 * - Mouse exit resumes seamlessly without jumping.
 * - Shivtej and Nadeem have interactive hover lifts and subtle glow.
 */
public class TeamLeadsShowcaseSection {

    private static final String BG = "#07162E";
    private static final String PANEL = "#0B203B";
    private static final String PANEL_2 = "#102A48";
    private static final String TEXT_PRIMARY = "#F5F8FC";
    private static final String TEXT_SECONDARY = "#A9BCD0";
    private static final String CYAN = "#29C6D8";
    private static final String ORANGE = "#E17B32";
    private static final String YPORANGE = "#f8924a" ;
    private static final String BORDER = "rgba(41, 198, 216, 0.22)";

    private static final String FONT =
            "-fx-font-family: 'Sora', 'Segoe UI', -apple-system, BlinkMacSystemFont, sans-serif;";

    private static final double SLIDER_WIDTH = 630;
    private static final double SLIDER_HEIGHT = 340;
    private static final double CARD_WIDTH = 250;
    private static final double CARD_HEIGHT = 285;
    private static final double GAP = 20;

    private final String[][] members = {
            {"Team Member 1", "/assets/Images/about/team/member-1.jpeg"},
            {"Team Member 2", "/assets/Images/about/team/member-2.jpeg"},
            {"Team Member 3", "/assets/Images/about/team/member-3.jpeg"},
            {"Team Member 4", "/assets/Images/about/team/member-4.jpeg"},
            {"Team Member 5", "/assets/Images/about/team/member-5.jpeg"}
    };

    private final String teamImagePath = "/assets/Images/about/team/team-photo.png";

    public VBox build() {
        VBox section = new VBox(26);
        section.setAlignment(Pos.CENTER);
        section.setPadding(new Insets(90, 35, 110, 35));
        section.setMaxWidth(Double.MAX_VALUE);
        section.setStyle(FONT + "-fx-background-color:" + BG + ";");

        // Section Eyebrow Badge
        HBox eyebrowBadge = new HBox(8);
        eyebrowBadge.setAlignment(Pos.CENTER);
        eyebrowBadge.setMaxWidth(380);
        eyebrowBadge.setPadding(new Insets(6, 14, 6, 12));
        eyebrowBadge.setStyle(
                FONT +
                "-fx-background-color: rgba(225, 123, 50, 0.12);" +
                "-fx-border-color: rgba(225, 123, 50, 0.35);" +
                "-fx-border-radius: 16px;" +
                "-fx-background-radius: 16px;"
        );
        Circle dot = new Circle(4, Color.web(ORANGE));
        Label eyebrow = new Label("THE TEAM");
        eyebrow.setStyle(FONT + "-fx-text-fill:" + ORANGE + ";-fx-font-size:11.5px;-fx-font-weight:800;-fx-letter-spacing:1.6px;");
        eyebrowBadge.getChildren().addAll(dot, eyebrow);

        Label title = new Label("The people who build LifeLink together");
        title.setStyle(FONT + "-fx-text-fill:" + TEXT_PRIMARY + ";-fx-font-size:34px;-fx-font-weight:800;");

        Label subtitle = new Label("Our team leads and members working together behind LifeLink.");
        subtitle.setStyle(FONT + "-fx-text-fill:" + TEXT_SECONDARY + ";-fx-font-size:15.5px;");

        // Main Showcase Row
        HBox mainRow = new HBox(40);
        mainRow.setAlignment(Pos.CENTER);
        mainRow.setMaxWidth(1200);

        // Left Lead: Shivtej
        VBox leftLead = buildLead(
                "Shivtej",
                "/assets/Images/shivtej.jpeg",
                "खंबीर साथ",
                "Keeps execution focused, collaborative, and aligned with the core project vision."
        );

        // Right Lead: Nadeem
        VBox rightLead = buildLead(
                "Nadeem",
                "/assets/Images/ndeem.png",
                "आधारस्तंभ",
                "Coordinates system implementation, engineering workflows, and team delivery."
        );

        // Center Auto-Slider
        VBox centerSlider = buildTeamSlider();
        HBox.setHgrow(centerSlider, Priority.ALWAYS);

        mainRow.getChildren().addAll(leftLead, centerSlider, rightLead);
        section.getChildren().addAll(eyebrowBadge, title, subtitle, mainRow);

        return section;
    }

    private VBox buildLead(String name, String imagePath, String position, String description) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(225);
        card.setMaxWidth(225);
        card.setPadding(new Insets(18, 16, 18, 16));
        card.setStyle(leadCardStyle(false));

        // Portrait Image Container
        StackPane imagePane = new StackPane();
        imagePane.setPrefSize(145, 145);

        Circle ring = new Circle(68);
        ring.setFill(Color.web(CYAN, 0.08));
        ring.setStroke(Color.web(CYAN, 0.65));
        ring.setStrokeWidth(1.5);
        imagePane.getChildren().add(ring);

        ImageView image = loadImage(imagePath);
        if (image != null) {
            image.setFitWidth(132);
            image.setFitHeight(132);
            image.setPreserveRatio(true);
            image.setSmooth(true);
            imagePane.getChildren().add(image);
        } else {
            // Elegant Monogram Avatar
            Label fallback = new Label(name.substring(0, Math.min(2, name.length())).toUpperCase());
            fallback.setStyle(FONT + "-fx-text-fill:" + CYAN + ";-fx-font-size:20px;-fx-font-weight:bold;");
            imagePane.getChildren().add(fallback);
        }

        Label nameLabel = new Label(name);
        nameLabel.setStyle(FONT + "-fx-text-fill:" + TEXT_PRIMARY + ";-fx-font-size:22px;-fx-font-weight:800;");

        // Premium Position Pill
        StackPane positionBox = new StackPane();
        positionBox.setPadding(new Insets(4, 12, 4, 12));
        positionBox.setStyle(
                FONT +
                "-fx-background-color: rgba(41, 198, 216, 0.10);" +
                "-fx-border-color: rgba(41, 198, 216, 0.32);" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;"
        );
        Label positionLabel = new Label(position.toUpperCase());
        positionLabel.setStyle(FONT + "-fx-text-fill:" + YPORANGE + ";-fx-font-size:14px;-fx-font-weight:900;-fx-letter-spacing:1.2px;");
        positionBox.getChildren().add(positionLabel);

        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(185);
        descriptionLabel.setAlignment(Pos.CENTER);
        descriptionLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        descriptionLabel.setStyle(FONT + "-fx-text-fill:" + TEXT_SECONDARY + ";-fx-font-size:11.5px;-fx-line-spacing:2.5px;");

        card.getChildren().addAll(imagePane, nameLabel, positionBox, descriptionLabel);

        // Hover Effect: Slight scale, subtle glow, slight lift
        card.setOnMouseEntered(event -> {
            card.setTranslateY(-5);
            card.setScaleX(1.03);
            card.setScaleY(1.03);
            card.setStyle(leadCardStyle(true));
        });

        card.setOnMouseExited(event -> {
            card.setTranslateY(0);
            card.setScaleX(1.0);
            card.setScaleY(1.0);
            card.setStyle(leadCardStyle(false));
        });

        return card;
    }

    private String leadCardStyle(boolean hovered) {
        if (hovered) {
            return FONT +
                    "-fx-background-color:" + PANEL_2 + ";" +
                    "-fx-background-radius:24px;" +
                    "-fx-border-color:" + CYAN + ";" +
                    "-fx-border-radius:24px;" +
                    "-fx-border-width:1.4px;" +
                    "-fx-effect:dropshadow(three-pass-box, rgba(41,198,216,0.28), 24, 0, 0, 7);" +
                    "-fx-cursor:hand;";
        } else {
            return FONT +
                    "-fx-background-color:" + PANEL + ";" +
                    "-fx-background-radius:24px;" +
                    "-fx-border-color:" + BORDER + ";" +
                    "-fx-border-radius:24px;" +
                    "-fx-border-width:1.2px;" +
                    "-fx-effect:dropshadow(three-pass-box, rgba(0,0,0,0.28), 18, 0, 0, 5);" +
                    "-fx-cursor:hand;";
        }
    }

    private VBox buildTeamSlider() {
        VBox wrapper = new VBox(10);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPrefWidth(SLIDER_WIDTH);
        wrapper.setMaxWidth(SLIDER_WIDTH);

        // Top Label Badge
        Label teamLabel = new Label("CORE TEAM MEMBERS");
        teamLabel.setStyle(FONT + "-fx-text-fill:" + CYAN + ";-fx-font-size:11px;-fx-font-weight:900;-fx-letter-spacing:1.8px;");

        // Viewport
        StackPane viewport = new StackPane();
        viewport.setPrefSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        viewport.setMinSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        viewport.setMaxSize(SLIDER_WIDTH, SLIDER_HEIGHT);
        viewport.setStyle(
                FONT +
                "-fx-background-color:" + PANEL + ";" +
                "-fx-background-radius:26px;" +
                "-fx-border-color: rgba(41, 198, 216, 0.20);" +
                "-fx-border-width: 1.2px;" +
                "-fx-border-radius:26px;" +
                "-fx-effect:dropshadow(three-pass-box, rgba(0,0,0,0.30), 22, 0, 0, 6);"
        );

        // Clipped Area
        StackPane clipContainer = new StackPane();
        clipContainer.setPrefSize(SLIDER_WIDTH - 20, SLIDER_HEIGHT - 20);
        clipContainer.setMinSize(SLIDER_WIDTH - 20, SLIDER_HEIGHT - 20);
        clipContainer.setMaxSize(SLIDER_WIDTH - 20, SLIDER_HEIGHT - 20);

        Rectangle clip = new Rectangle(SLIDER_WIDTH - 20, SLIDER_HEIGHT - 20);
        clip.setArcWidth(24);
        clip.setArcHeight(24);
        clipContainer.setClip(clip);

        // Track with duplicate sequence for seamless wrap
        HBox track = new HBox(GAP);
        track.setAlignment(Pos.CENTER_LEFT);

        List<StackPane> cards = new ArrayList<>();
        for (int pass = 0; pass < 2; pass++) {
            cards.add(buildImageCard("LifeLink Team", teamImagePath, true));
            for (String[] member : members) {
                cards.add(buildImageCard(member[0], member[1], false));
            }
        }
        track.getChildren().addAll(cards);
        clipContainer.getChildren().add(track);
        viewport.getChildren().add(clipContainer);
        wrapper.getChildren().addAll(teamLabel, viewport);

        // Auto Scroll (Right -> Left)
        javafx.application.Platform.runLater(() -> {
            // Exactly 6 cards + 6 gaps per cycle for a 100% seamless wrap
            double oneCycleWidth = 6 * (CARD_WIDTH + GAP);

            TranslateTransition scroll = new TranslateTransition(Duration.seconds(16), track);
            scroll.setFromX(0);
            scroll.setToX(-oneCycleWidth);
            scroll.setCycleCount(Animation.INDEFINITE);
            scroll.setInterpolator(Interpolator.LINEAR);
            scroll.play();

            // Hover: Pause in place
            viewport.setOnMouseEntered(event -> scroll.pause());

            // Mouse out: Resume from current position
            viewport.setOnMouseExited(event -> scroll.play());
        });

        return wrapper;
    }

    private StackPane buildImageCard(String name, String imagePath, boolean isTeamImage) {
        StackPane card = new StackPane();
        card.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        card.setMinSize(CARD_WIDTH, CARD_HEIGHT);
        card.setMaxSize(CARD_WIDTH, CARD_HEIGHT);
        card.setStyle(
                FONT +
                "-fx-background-color:" + PANEL_2 + ";" +
                "-fx-background-radius:20px;" +
                "-fx-border-color:" + (isTeamImage ? "rgba(225,123,50,0.50);" : "rgba(41,198,216,0.22);") +
                "-fx-border-radius:20px;" +
                "-fx-border-width:1.2px;" +
                "-fx-effect:dropshadow(three-pass-box, rgba(0,0,0,0.22), 14, 0, 0, 4);"
        );

        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(14));

        ImageView image = loadImage(imagePath);
        if (image != null) {
            image.setFitWidth(CARD_WIDTH - 28);
            image.setFitHeight(195);
            image.setPreserveRatio(true);
            image.setSmooth(true);
            content.getChildren().add(image);
        } else {
            // Elegant Monogram / Group avatar
            StackPane placeholderPane = new StackPane();
            placeholderPane.setPrefSize(CARD_WIDTH - 28, 195);
            placeholderPane.setStyle(
                    FONT +
                    "-fx-background-color: rgba(7, 22, 46, 0.70);" +
                    "-fx-background-radius: 14px;" +
                    "-fx-border-color: rgba(41, 198, 216, 0.15);" +
                    "-fx-border-radius: 14px;"
            );

            Circle circle = new Circle(40, Color.web(isTeamImage ? ORANGE : CYAN, 0.12));
            circle.setStroke(Color.web(isTeamImage ? ORANGE : CYAN, 0.5));
            circle.setStrokeWidth(1.2);

            Label fallback = new Label(isTeamImage ? "TEAM PHOTO" : name.toUpperCase());
            fallback.setStyle(FONT + "-fx-text-fill:" + (isTeamImage ? ORANGE : CYAN) + ";-fx-font-size:11px;-fx-font-weight:bold;");

            placeholderPane.getChildren().addAll(circle, fallback);
            content.getChildren().add(placeholderPane);
        }

        HBox labelBox = new HBox(8);
        labelBox.setAlignment(Pos.CENTER);

        Circle roleDot = new Circle(3.5, Color.web(isTeamImage ? ORANGE : CYAN));
        Label nameLabel = new Label(name);
        nameLabel.setStyle(FONT + "-fx-text-fill:" + TEXT_PRIMARY + ";-fx-font-size:12.5px;-fx-font-weight:800;");
        labelBox.getChildren().addAll(roleDot, nameLabel);

        content.getChildren().add(labelBox);
        card.getChildren().add(content);

        return card;
    }

    private ImageView loadImage(String path) {
        try {
            var url = TeamLeadsShowcaseSection.class.getResource(path);
            if (url != null) {
                return new ImageView(new Image(url.toExternalForm(), true));
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}