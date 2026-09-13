package com.kurukshetra.view.family;

import com.kurukshetra.view.Welcome;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Sidebar {

    private static final String PRIMARY = "#CA6721";
    private static final String PRIMARY_DARK = "#782F16";
    private static final String SURFACE = "#FFFFFF";
    private static final String VERY_LIGHT_TERRACOTTA = "#FDF0E8";
    private static final String LIGHT_TERRACOTTA = "#F9E4D6";
    private static final String PALE_PEACH = "#FEF7F2";
    private static final String TEXT_PRIMARY = "#2B211D";
    private static final String TEXT_SECONDARY = "#675B55";
    private static final String TEXT_MUTED = "#938780";
    private static final String BORDER_COLOR = "#EDE0D8";
    private static final String HOVER_BG = "#FFF5EF";
    private static final String GREEN = "#16A34A";
    private static final String SOS_RED = "#BA3B3E";
    private static final String SOS_BG = "#FCEAE8";
    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";

    // High-Legibility Typography for Background Image (Warm Creams & Whites)
    private static final String TEXT_LIGHT_TITLE = "#FFFFFF";
    private static final String TEXT_LIGHT_SUB = "#FBE2D3";
    private static final String TEXT_NAV_INACTIVE = "#FFF5EE";

    public enum Page {
        DASHBOARD,
        FIND_HOSPITALS,
        FIRST_AID,
        MEDICAL_HISTORY,
        SAVED_HOSPITALS,
        REVIEWS,
        SETTINGS,
        EMERGENCY,
        ABOUT_US
    }

    public static VBox build(Stage stage, Page activePage) {
        VBox leftMenu = new VBox(6);
        leftMenu.setPadding(new Insets(18, 14, 18, 14));
        leftMenu.setPrefWidth(260);
        leftMenu.setMinWidth(260);
        leftMenu.setMaxWidth(260);
        leftMenu.setEffect(new DropShadow(14, 0, 0, Color.rgb(0, 0, 0, 0.12)));

        Image bgImage = loadFamilyDashboardBackground();
        if (bgImage != null && !bgImage.isError()) {
            BackgroundSize backgroundSize = new BackgroundSize(
                    1.0, 1.0, true, true, false, true);
            BackgroundImage backgroundImage = new BackgroundImage(
                    bgImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    backgroundSize);
            leftMenu.setBackground(new Background(backgroundImage));
            leftMenu.setStyle(
                    FONT_FAMILY +
                    "-fx-border-color: " + BORDER_COLOR + "; " +
                    "-fx-border-width: 0px 1px 0px 0px;"
            );
        } else {
            leftMenu.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; " +
                    "-fx-border-color: " + BORDER_COLOR + "; " +
                    "-fx-border-width: 0px 1px 0px 0px;"
            );
        }

        // 1. Brand Header with Logo / Icon
        HBox brandHeader = new HBox(12);
        brandHeader.setAlignment(Pos.CENTER_LEFT);
        brandHeader.setPadding(new Insets(4, 6, 8, 6));

        StackPane brandIconPane = new StackPane();
        brandIconPane.setPrefSize(42, 42);
        brandIconPane.setMinSize(42, 42);
        brandIconPane.setMaxSize(42, 42);

        Image logoImg = loadLogoImage();
        if (logoImg != null && !logoImg.isError()) {
            ImageView logoView = new ImageView(logoImg);
            logoView.setFitWidth(42);
            logoView.setFitHeight(42);
            logoView.setPreserveRatio(true);
            logoView.setSmooth(true);

            Rectangle clip = new Rectangle(42, 42);
            clip.setArcWidth(12);
            clip.setArcHeight(12);
            logoView.setClip(clip);

            brandIconPane.setStyle(
                    "-fx-background-radius: 12px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.12), 6, 0, 0, 2);"
            );
            brandIconPane.getChildren().add(logoView);
        } else {
            brandIconPane.setStyle(
                    "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + "); " +
                    "-fx-background-radius: 12px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.30), 8, 0, 0, 3);"
            );
            Text brandIcon = new Text("🧡");
            brandIcon.setStyle("-fx-font-size: 20px; -fx-fill: white;");
            brandIconPane.getChildren().add(brandIcon);
        }

        VBox brandTextBox = new VBox(1);
        Text lifeLinkText = new Text("LifeLink");
        lifeLinkText.setStyle(FONT_FAMILY + "-fx-font-size: 21px; -fx-font-weight: 800; -fx-fill: " + TEXT_LIGHT_TITLE + ";");
        lifeLinkText.setEffect(new DropShadow(8, 0, 1, Color.rgb(0, 0, 0, 0.40)));

        Text familyText = new Text("Family Care Portal");
        familyText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-fill: " + TEXT_LIGHT_SUB + "; -fx-font-weight: 600;");
        brandTextBox.getChildren().addAll(lifeLinkText, familyText);
        brandHeader.getChildren().addAll(brandIconPane, brandTextBox);

        // 2. Animated Live Telemetry Hub Badge
        HBox liveHubBadge = new HBox(7);
        liveHubBadge.setAlignment(Pos.CENTER_LEFT);
        liveHubBadge.setPadding(new Insets(5, 10, 5, 10));
        liveHubBadge.setStyle(
                "-fx-background-color: #FFFFFF; " +
                "-fx-border-color: " + LIGHT_TERRACOTTA + "; " +
                "-fx-border-radius: 16px; " +
                "-fx-background-radius: 16px;"
        );

        Circle pulseDot = new Circle(4, Color.web(GREEN));
        // Pulse animation on green dot
        Timeline pulseAnim = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(pulseDot.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(800), new KeyValue(pulseDot.opacityProperty(), 0.35)),
                new KeyFrame(Duration.millis(1600), new KeyValue(pulseDot.opacityProperty(), 1.0))
        );
        pulseAnim.setCycleCount(Animation.INDEFINITE);
        pulseAnim.play();

       
        // liveHubBadge.getChildren().addAll(pulseDot, liveHubText);

        VBox brandBox = new VBox(8, brandHeader);
        brandBox.setPadding(new Insets(0, 0, 8, 0));

        // Navigation Section Label (Matching Police Navigation style)
        Label navLabel = new Label("Family Hub");
        navLabel.setStyle(FONT_FAMILY + "-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_LIGHT_TITLE + "; -fx-letter-spacing: 0.6px; -fx-padding: 6 0 4 8; -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.40), 6, 0, 0, 1);");

        Button dashboardButton = createNavButton("🏠  Dashboard", activePage == Page.DASHBOARD);
        Button findHospitalsButton = createNavButton("🏥  Hospitals", activePage == Page.FIND_HOSPITALS);
        Button firstAidButton = createNavButton("🩹  First-Aid", activePage == Page.FIRST_AID);
        Button medicalHistoryButton = createNavButton("📋  Medical", activePage == Page.MEDICAL_HISTORY);
        Button reviewsButton = createNavButton("⭐  Reviews", activePage == Page.REVIEWS);
        Button aboutUsButton = createNavButton("ℹ️  About Us", activePage == Page.ABOUT_US);

        dashboardButton.setOnAction(e -> navigateTo(stage, new FamilyHomePage().setBorderPane(stage)));
        findHospitalsButton.setOnAction(e -> navigateTo(stage, new FamilyFindCare().setBorderPane(stage)));
        firstAidButton.setOnAction(e -> navigateTo(stage, new FirstAidAssistant().setBorderPane(stage)));
        medicalHistoryButton.setOnAction(e -> navigateTo(stage, new MedicalReports().setBorderPane(stage)));
        reviewsButton.setOnAction(e -> navigateTo(stage, new FamilyReviewPage().setBorderPane(stage)));
        aboutUsButton.setOnAction(e -> {
            stage.setMaximized(true);
            navigateTo(stage, new AboutUsPage().setBorderPane(stage));
        });

        // Spacer pushes profile and logout to bottom
        Region menuSpacer = new Region();
        VBox.setVgrow(menuSpacer, Priority.ALWAYS);

        // Profile Mini Card
        HBox profileBox = new HBox(10);
        profileBox.setAlignment(Pos.CENTER_LEFT);
        profileBox.setPadding(new Insets(8, 10, 8, 10));

        String profileCardNormalStyle =
                "-fx-background-color: rgba(35, 18, 10, 0.60); " +
                "-fx-border-color: rgba(255, 255, 255, 0.22); " +
                "-fx-border-radius: 12px; " +
                "-fx-background-radius: 12px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.30), 8, 0, 0, 2); " +
                "-fx-cursor: hand;";

        String profileCardHoverStyle =
                "-fx-background-color: rgba(43, 21, 12, 0.78); " +
                "-fx-border-color: " + PRIMARY + "; " +
                "-fx-border-radius: 12px; " +
                "-fx-background-radius: 12px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(202, 103, 33, 0.35), 10, 0, 0, 2); " +
                "-fx-cursor: hand;";

        profileBox.setStyle(profileCardNormalStyle);
        profileBox.setOnMouseEntered(e -> profileBox.setStyle(profileCardHoverStyle));
        profileBox.setOnMouseExited(e -> profileBox.setStyle(profileCardNormalStyle));

        String userEmail = FamilyHomePage.USER_EMAIL != null ? FamilyHomePage.USER_EMAIL : "family@lifelink.com";
        String initialsText = extractInitials(userEmail);

        StackPane avatarPane = new StackPane();
        avatarPane.setPrefSize(36, 36);
        avatarPane.setMinSize(36, 36);
        avatarPane.setMaxSize(36, 36);
        avatarPane.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + "); " +
                "-fx-background-radius: 18px;"
        );
        Text initials = new Text(initialsText);
        initials.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: white;");
        avatarPane.getChildren().add(initials);

        VBox profileDetails = new VBox(2);
        Text profileName = new Text(userEmail);
        profileName.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: #FFFFFF;");
        Text profileRole = new Text("Verified Family Care Lead");
        profileRole.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-fill: " + TEXT_LIGHT_SUB + "; -fx-font-weight: 500;");
        profileDetails.getChildren().addAll(profileName, profileRole);
        profileBox.getChildren().addAll(avatarPane, profileDetails);

        // Logout Button with smooth hover transition (One word: Logout)
        Button logoutButton = new Button("↩  Logout");
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setPrefHeight(42);
        logoutButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: rgba(62, 22, 14, 0.80); " +
                "-fx-text-fill: #FFD2C8; " +
                "-fx-font-size: 13.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-color: rgba(255, 255, 255, 0.18); " +
                "-fx-border-radius: 12px; " +
                "-fx-cursor: hand;"
        );
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #BA3B3E; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 13.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-color: #BA3B3E; " +
                "-fx-border-radius: 12px; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(186, 59, 62, 0.40), 10, 0, 0, 2);"
        ));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: rgba(62, 22, 14, 0.80); " +
                "-fx-text-fill: #FFD2C8; " +
                "-fx-font-size: 13.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 12px; " +
                "-fx-border-color: rgba(255, 255, 255, 0.18); " +
                "-fx-border-radius: 12px; " +
                "-fx-cursor: hand;"
        ));
        logoutButton.setOnAction(e -> {
            try {
                Welcome welcome = new Welcome();
                welcome.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        leftMenu.getChildren().addAll(
                brandBox,
                navLabel,
                dashboardButton,
                findHospitalsButton,
                firstAidButton,
                medicalHistoryButton,
                reviewsButton,
                aboutUsButton,
                menuSpacer,
                profileBox,
                logoutButton
        );

        return leftMenu;
    }

    private static Label createCategoryLabel(String text) {
        Label catLabel = new Label(text);
        catLabel.setStyle(
                FONT_FAMILY +
                "-fx-font-size: 9.5px; " +
                "-fx-font-weight: 800; " +
                "-fx-text-fill: " + TEXT_MUTED + "; " +
                "-fx-padding: 10px 6px 4px 6px; " +
                "-fx-letter-spacing: 0.6px;"
        );
        return catLabel;
    }

    private static Button createNavButton(String text, boolean active) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(42);

        String activeStyle =
                FONT_FAMILY +
                "-fx-background-color: #FFFFFF; " +
                "-fx-text-fill: " + PRIMARY_DARK + "; " +
                "-fx-font-size: 13.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 12px; " +
                "-fx-alignment: center-left; " +
                "-fx-padding: 0 14px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.25), 10, 0, 0, 3); " +
                "-fx-cursor: hand;";

        String inactiveStyle =
                FONT_FAMILY +
                "-fx-background-color: transparent; " +
                "-fx-text-fill: " + TEXT_NAV_INACTIVE + "; " +
                "-fx-font-size: 13.5px; " +
                "-fx-font-weight: 600; " +
                "-fx-background-radius: 12px; " +
                "-fx-alignment: center-left; " +
                "-fx-padding: 0 14px; " +
                "-fx-cursor: hand;";

        if (active) {
            btn.setStyle(activeStyle);
        } else {
            btn.setStyle(inactiveStyle);
            btn.setOnMouseEntered(e -> {
                btn.setStyle(
                        FONT_FAMILY +
                        "-fx-background-color: rgba(255, 255, 255, 0.22); " +
                        "-fx-text-fill: #FFFFFF; " +
                        "-fx-font-size: 13.5px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-alignment: center-left; " +
                        "-fx-padding: 0 14px; " +
                        "-fx-border-color: rgba(255, 255, 255, 0.35); " +
                        "-fx-border-radius: 12px; " +
                        "-fx-cursor: hand;"
                );
                btn.setTranslateX(4);
            });
            btn.setOnMouseExited(e -> {
                btn.setStyle(inactiveStyle);
                btn.setTranslateX(0);
            });
        }
        return btn;
    }

    private static String extractInitials(String email) {
        if (email == null || email.isEmpty()) return "FP";
        int atIdx = email.indexOf('@');
        String namePart = atIdx > 0 ? email.substring(0, atIdx) : email;
        if (namePart.length() >= 2) {
            return namePart.substring(0, 2).toUpperCase();
        }
        return namePart.toUpperCase();
    }

    private static void navigateTo(Stage stage, BorderPane newRoot) {
        stage.getScene().setRoot(newRoot);
    }

    private static Image loadLogoImage() {
        String[] resourcePaths = {
                "/assets/Images/lifelinklogonew.png",
                "/assets/Images/LifeLinkLogo.png",
                "/lifelinklogonew.png"
        };
        for (String resPath : resourcePaths) {
            try {
                var url = Sidebar.class.getResource(resPath);
                if (url != null) {
                    Image img = new Image(url.toExternalForm(), false);
                    if (!img.isError()) {
                        return img;
                    }
                }
            } catch (Exception ignored) {}
        }

        String[] filePaths = {
                "src/main/resources/assets/Images/lifelinklogonew.png",
                "lifelink1/src/main/resources/assets/Images/lifelinklogonew.png",
                "src/main/resources/assets/Images/LifeLinkLogo.png",
                "lifelink1/src/main/resources/assets/Images/LifeLinkLogo.png"
        };
        for (String filePath : filePaths) {
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    Image img = new Image(file.toURI().toString(), false);
                    if (!img.isError()) {
                        return img;
                    }
                }
            } catch (Exception ignored) {}
        }

        return null;
    }

    private static Image loadFamilyDashboardBackground() {
        String[] resourcePaths = {
                "/assets/Images/familyDashboardbackground.png",
                "/familyDashboardbackground.png"
        };
        for (String resPath : resourcePaths) {
            try {
                var url = Sidebar.class.getResource(resPath);
                if (url != null) {
                    Image img = new Image(url.toExternalForm(), false);
                    if (!img.isError()) {
                        return img;
                    }
                }
            } catch (Exception ignored) {}
        }

        String[] filePaths = {
                "src/main/resources/assets/Images/familyDashboardbackground.png",
                "lifelink1/src/main/resources/assets/Images/familyDashboardbackground.png",
                "assets/Images/familyDashboardbackground.png"
        };
        for (String filePath : filePaths) {
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    Image img = new Image(file.toURI().toString(), false);
                    if (!img.isError()) {
                        return img;
                    }
                }
            } catch (Exception ignored) {}
        }

        return null;
    }
}