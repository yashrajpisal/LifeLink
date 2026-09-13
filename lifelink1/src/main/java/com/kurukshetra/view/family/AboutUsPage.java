package com.kurukshetra.view.family;

import com.kurukshetra.view.family.about.EmergencyFlowSection;
import com.kurukshetra.view.family.about.HeroSection;
import com.kurukshetra.view.family.about.MentorOrbitSection;
import com.kurukshetra.view.family.about.PeoplePyramidSection;
import com.kurukshetra.view.family.about.TeamLeadsShowcaseSection;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Immersive, full-screen LifeLink About Us experience.
 * Features a modern healthcare-tech aesthetic, glassmorphic navigation,
 * custom Sora typography, and smooth storytelling sections.
 */
public class AboutUsPage {

    public static final String BG = "#07162E";
    public static final String BG_SECONDARY = "#0B203B";
    public static final String SURFACE = "#102A48";
    public static final String TEXT_PRIMARY = "#F5F8FC";
    public static final String TEXT_SECONDARY = "#A9BCD0";
    public static final String CYAN = "#29C6D8";
    public static final String ORANGE = "#E17B32";

    public static final String FONT =
            "-fx-font-family: 'Sora', 'Segoe UI', -apple-system, BlinkMacSystemFont, sans-serif;";

    static {
        loadProjectFonts();
    }

    private static void loadProjectFonts() {
        try {
            var regular = AboutUsPage.class.getResourceAsStream("/fonts/Sora-Regular.ttf");
            if (regular != null) Font.loadFont(regular, 14);
            var semiBold = AboutUsPage.class.getResourceAsStream("/fonts/Sora-SemiBold.ttf");
            if (semiBold != null) Font.loadFont(semiBold, 14);
            var bold = AboutUsPage.class.getResourceAsStream("/fonts/Sora-Bold.ttf");
            if (bold != null) Font.loadFont(bold, 14);
        } catch (Exception ignored) {
        }
    }

    public BorderPane setBorderPane(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle(FONT + "-fx-background-color:" + BG + ";");

        // =====================================================
        // GLASSMORPHIC TOP NAVIGATION BAR
        // =====================================================
        HBox topBar = new HBox(16);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(14, 28, 14, 28));
        topBar.setStyle(
                FONT +
                "-fx-background-color: rgba(7, 22, 46, 0.94);" +
                "-fx-border-color: rgba(41, 198, 216, 0.16);" +
                "-fx-border-width: 0 0 1 0;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.35), 14, 0, 0, 4);"
        );

        // Back button to Family Dashboard
        HBox backBtn = new HBox(8);
        backBtn.setAlignment(Pos.CENTER);
        backBtn.setPadding(new Insets(8, 16, 8, 14));
        backBtn.setStyle(
                FONT +
                "-fx-background-color: rgba(16, 42, 72, 0.65);" +
                "-fx-border-color: rgba(41, 198, 216, 0.25);" +
                "-fx-border-radius: 20px;" +
                "-fx-background-radius: 20px;" +
                "-fx-cursor: hand;"
        );

        Label backArrow = new Label("←");
        backArrow.setStyle(FONT + "-fx-text-fill:" + CYAN + ";-fx-font-size:14px;-fx-font-weight:bold;");

        Label backText = new Label("Family Dashboard");
        backText.setStyle(FONT + "-fx-text-fill:" + TEXT_PRIMARY + ";-fx-font-size:12.5px;-fx-font-weight:700;");

        backBtn.getChildren().addAll(backArrow, backText);

        backBtn.setOnMouseEntered(e -> {
            backBtn.setStyle(
                    FONT +
                    "-fx-background-color: rgba(41, 198, 216, 0.18);" +
                    "-fx-border-color: " + CYAN + ";" +
                    "-fx-border-radius: 20px;" +
                    "-fx-background-radius: 20px;" +
                    "-fx-cursor: hand;" +
                    "-fx-effect: dropshadow(three-pass-box, rgba(41, 198, 216, 0.25), 10, 0, 0, 2);"
            );
        });

        backBtn.setOnMouseExited(e -> {
            backBtn.setStyle(
                    FONT +
                    "-fx-background-color: rgba(16, 42, 72, 0.65);" +
                    "-fx-border-color: rgba(41, 198, 216, 0.25);" +
                    "-fx-border-radius: 20px;" +
                    "-fx-background-radius: 20px;" +
                    "-fx-cursor: hand;"
            );
        });

        backBtn.setOnMouseClicked(e -> {
            stage.getScene().setRoot(new FamilyHomePage().setBorderPane(stage));
        });


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Right Ecosystem Pill with subtle status dot
        HBox ecosystemPill = new HBox(8);
        ecosystemPill.setAlignment(Pos.CENTER);
        ecosystemPill.setPadding(new Insets(6, 14, 6, 12));
        ecosystemPill.setStyle(
                FONT +
                "-fx-background-color: rgba(11, 32, 59, 0.70);" +
                "-fx-border-color: rgba(41, 198, 216, 0.30);" +
                "-fx-border-radius: 16px;" +
                "-fx-background-radius: 16px;"
        );

        Circle liveDot = new Circle(4, Color.web(CYAN));
        Label ecosystemLabel = new Label("HEALTHCARE ECOSYSTEM");
        ecosystemLabel.setStyle(FONT +
                "-fx-text-fill:" + CYAN + ";" +
                "-fx-font-size:10px;" +
                "-fx-font-weight:800;" +
                "-fx-letter-spacing:1.2px;"
        );
        ecosystemPill.getChildren().addAll(liveDot, ecosystemLabel);

        topBar.getChildren().addAll(backBtn,spacer, ecosystemPill);
        root.setTop(topBar);

        // =====================================================
        // CONTENT SECTIONS CONTAINER
        // =====================================================
        VBox content = new VBox(0);
        content.setFillWidth(true);
        content.setPadding(Insets.EMPTY);
        content.setStyle(FONT + "-fx-background-color:" + BG + ";");

        content.getChildren().addAll(
                new HeroSection().build(),
                new EmergencyFlowSection().build(),
                new PeoplePyramidSection().build(),
                new MentorOrbitSection().build(),
                new TeamLeadsShowcaseSection().build()
        );

        // Custom ScrollPane styling
        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(false);
        scroll.setPannable(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setPadding(Insets.EMPTY);
        scroll.setStyle(
                FONT +
                "-fx-background-color: " + BG + ";" +
                "-fx-background: " + BG + ";" +
                "-fx-border-color: transparent;" +
                "-fx-padding: 0;"
        );

        root.setCenter(scroll);
        return root;
    }
}
