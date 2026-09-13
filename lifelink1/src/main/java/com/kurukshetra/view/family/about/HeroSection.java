package com.kurukshetra.view.family.about;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Premium dark-theme LifeLink About hero.
 * Features cinematic ambient lighting, Sora typography, and a staggered reveal.
 */
public class HeroSection {

    private static final String BG = "#07162E";
    private static final String TEXT_PRIMARY = "#F5F8FC";
    private static final String SECONDARY = "#A9BCD0";
    private static final String ORANGE = "#E17B32";
    private static final String CYAN = "#29C6D8";
    private static final String FONT =
            "-fx-font-family: 'Sora', 'Segoe UI', -apple-system, BlinkMacSystemFont, sans-serif;";

    public VBox build() {
        StackPane hero = new StackPane();
        hero.setMinHeight(460);
        hero.setPrefHeight(490);
        hero.setMaxWidth(Double.MAX_VALUE);
        hero.setStyle(FONT + "-fx-background-color:" + BG + ";");

        // Ambient atmospheric lighting orbs (subtle, non-distracting)
        Circle topCyanAura = new Circle(220, Color.web("#0D3156", 0.35));
        Circle bottomOrangeAura = new Circle(190, Color.web("#382017", 0.28));
        Circle centerGlow = new Circle(140, Color.web(CYAN, 0.05));
        centerGlow.setStroke(Color.web(CYAN, 0.12));
        centerGlow.setStrokeWidth(1.0);

        StackPane.setAlignment(topCyanAura, Pos.TOP_LEFT);
        StackPane.setMargin(topCyanAura, new Insets(-120, 0, 0, -80));
        StackPane.setAlignment(bottomOrangeAura, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(bottomOrangeAura, new Insets(0, -60, -100, 0));
        StackPane.setAlignment(centerGlow, Pos.CENTER);

        VBox content = new VBox(18);
        content.setAlignment(Pos.CENTER);
        content.setMaxWidth(960);
        content.setPadding(new Insets(50, 40, 48, 40));

        // Eyebrow badge pill with glowing amber beacon
        HBox eyebrowBadge = new HBox(8);
        eyebrowBadge.setAlignment(Pos.CENTER);
        eyebrowBadge.setMaxWidth(380);
        eyebrowBadge.setPadding(new Insets(6, 16, 6, 12));
        eyebrowBadge.setStyle(
                FONT +
                "-fx-background-color: rgba(225, 123, 50, 0.12);" +
                "-fx-border-color: rgba(225, 123, 50, 0.38);" +
                "-fx-border-radius: 18px;" +
                "-fx-background-radius: 18px;"
        );
        

        Circle pulseDot = new Circle(4, Color.web(ORANGE));
        Label eyebrowText = new Label(" ABOUT LIFELINK");
        eyebrowText.setStyle(FONT +
                "-fx-text-fill: " + ORANGE + ";" +
                "-fx-font-size: 11.5px;" +
                "-fx-font-weight: 800;" +
                "-fx-letter-spacing: 1.5px;");
        eyebrowBadge.getChildren().addAll(pulseDot, eyebrowText);

        // Main Section Heading
        Label title = new Label("Technology that connects every second with the right care.");
        title.setWrapText(true);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setMaxWidth(880);
        title.setStyle(FONT +
                "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                "-fx-font-size: 38px;" +
                "-fx-font-weight: 800;" +
                "-fx-line-spacing: 4px;");

        // Clean Body Description
        Label description = new Label(
                "LifeLink is a Smart Emergency Hospital Recommendation & Assistance System " +
                "engineered to eliminate critical delays during medical emergencies by seamlessly synchronizing " +
                "patients, ambulances, police traffic support, hospitals, and specialized trauma care."
        );
        description.setWrapText(true);
        description.setMaxWidth(780);
        description.setTextAlignment(TextAlignment.CENTER);
        description.setStyle(FONT +
                "-fx-text-fill: " + SECONDARY + ";" +
                "-fx-font-size: 15.5px;" +
                "-fx-line-spacing: 5px;");

        content.getChildren().addAll(eyebrowBadge, title, description);
        hero.getChildren().addAll(topCyanAura, bottomOrangeAura, centerGlow, content);

        // =====================================================
        // STAGGERED ENTRANCE ANIMATION: Eyebrow -> Title -> Desc
        // =====================================================
        eyebrowBadge.setOpacity(0);
        eyebrowBadge.setTranslateY(16);
        title.setOpacity(0);
        title.setTranslateY(24);
        description.setOpacity(0);
        description.setTranslateY(20);

        // Eyebrow reveal (immediate)
        animateIn(eyebrowBadge, 0, 500);

        // Title reveal (after 180ms)
        animateIn(title, 180, 650);

        // Description reveal (after 380ms)
        animateIn(description, 380, 700);

        // Gentle floating motion for atmospheric auras
        TranslateTransition topFloat = new TranslateTransition(Duration.seconds(8), topCyanAura);
        topFloat.setFromY(-10);
        topFloat.setToY(15);
        topFloat.setFromX(-6);
        topFloat.setToX(8);
        topFloat.setAutoReverse(true);
        topFloat.setCycleCount(TranslateTransition.INDEFINITE);
        topFloat.play();

        TranslateTransition bottomFloat = new TranslateTransition(Duration.seconds(10), bottomOrangeAura);
        bottomFloat.setFromX(10);
        bottomFloat.setToX(-12);
        bottomFloat.setFromY(6);
        bottomFloat.setToY(-10);
        bottomFloat.setAutoReverse(true);
        bottomFloat.setCycleCount(TranslateTransition.INDEFINITE);
        bottomFloat.play();

        return new VBox(hero);
    }

    private void animateIn(javafx.scene.Node node, int delayMillis, int durationMillis) {
        PauseTransition delay = new PauseTransition(Duration.millis(delayMillis));
        delay.setOnFinished(e -> {
            FadeTransition fade = new FadeTransition(Duration.millis(durationMillis), node);
            fade.setFromValue(0);
            fade.setToValue(1);

            TranslateTransition move = new TranslateTransition(Duration.millis(durationMillis), node);
            move.setFromY(node.getTranslateY());
            move.setToY(0);

            fade.play();
            move.play();
        });
        delay.play();
    }
}
