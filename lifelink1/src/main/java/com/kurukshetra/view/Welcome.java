package com.kurukshetra.view;

import com.kurukshetra.view.admin.AdminLoginPage;
import com.kurukshetra.view.driver.DriverLoginPage;
import com.kurukshetra.view.family.FamilyLoginPage;
import com.kurukshetra.view.hospital.HospitalLoginPage;
import com.kurukshetra.view.nurse.NurseLoginPage;
import com.kurukshetra.view.police.PoliceLoginPage;

// import java.sql.Driver;

// import com.kurukshetra.view.driver.DriverDashboard;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Welcome extends Application {


    // Color Palette matching theme specifications
    private static final String BG_SURFACE = "#faf8ff";
    private static final String PRIMARY_COLOR = "#006591";
    private static final String ON_SURFACE = "#131b2e";
    private static final String ON_SURFACE_VARIANT = "#3e4850";
    private static final String OUTLINE_VARIANT = "#bec8d2";
    private static final String CONTAINER_LOW = "#f2f3ff";
    private static final String CARD_BG = "#ffffff";
    
    // Icon Container Styles
    private static final String ICON_BG_DEFAULT = "#eaedff";
    private static final String ICON_BG_ERROR = "#ffdad6";
    private static final String ICON_COLOR_ERROR = "#ba1a1a";
    private static final String ICON_BG_POLICE = "#d0e1fb";
    private static final String ICON_COLOR_POLICE = "#54647a";

    // SVG Vector Paths (Standard Material Symbols)
    private static final String SVG_ADMIN = "M12 1L3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4zm-2 16l-4-4 1.41-1.41L10 14.17l6.59-6.59L18 9l-8 8z";
    private static final String SVG_PATIENT = "M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z";
    private static final String SVG_AMBULANCE = "M18.92 6.01C18.72 5.42 18.16 5 17.5 5h-11c-.66 0-1.21.42-1.42 1.01L3 12v8c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h12v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-8l-2.08-5.99zM6.5 16c-.83 0-1.5-.67-1.5-1.5S5.67 13 6.5 13s1.5.67 1.5 1.5S7.33 16 6.5 16zm11 0c-.83 0-1.5-.67-1.5-1.5s.67-1.5 1.5-1.5 1.5.67 1.5 1.5-.67 1.5-1.5 1.5zM5 11l1.5-4.5h11L19 11H5z";
    private static final String SVG_HOSPITAL = "M19 10.5h-5.5V5h-3v5.5H5v3h5.5V19h3v-5.5H19z";
    private static final String SVG_POLICE = "M12 1L3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4zm0 6c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3z";

    public static Stage WelcomeStage;
    private Scene sc;
    public static BorderPane root;
    @Override
    public void start(Stage primaryStage) {
        WelcomeStage = primaryStage;

        root = new BorderPane();
        root.setStyle("-fx-background-color: " + BG_SURFACE + ";");

        // Layout Components
        VBox leftSidebar = createSidebar();
        VBox mainContent = createMainContent();

        root.setLeft(leftSidebar);
        root.setCenter(mainContent);

        sc = new Scene(root, WelcomeStage.getWidth(), WelcomeStage.getHeight());
        WelcomeStage.setTitle("LifeLink - Select Role");
        WelcomeStage.setScene(sc);
        WelcomeStage.setMaximized(true);
        WelcomeStage.show();

        // Entrance Staggered Slide-up Animation
        playEntranceAnimations(mainContent);

    }


    private VBox createSidebar() {
        VBox sidebar = new VBox(20);
        sidebar.setPrefWidth(380);
        sidebar.setPadding(new Insets(48));
        sidebar.setStyle("-fx-background-color: " + CONTAINER_LOW + ";");

        Label brandLabel = new Label("LifeLink");
        brandLabel.setFont(Font.font("Plus Jakarta Sans", FontWeight.BOLD, 42));
        brandLabel.setTextFill(Color.web(PRIMARY_COLOR));

        Label descLabel = new Label("Emergency response and medical coordination system. Select your portal to continue.");
        descLabel.setFont(Font.font("Inter", 16));
        descLabel.setTextFill(Color.web(ON_SURFACE_VARIANT));
        descLabel.setWrapText(true);

        sidebar.getChildren().addAll(brandLabel, descLabel);
        return sidebar;
    }

    private VBox createMainContent() {
        VBox container = new VBox(32);
        container.setPadding(new Insets(48, 64, 48, 64));
        container.setAlignment(Pos.CENTER_LEFT);

        // Header
        VBox header = new VBox(8);
        Label title = new Label("Welcome back");
        title.setFont(Font.font("Plus Jakarta Sans", FontWeight.BOLD, 32));
        title.setTextFill(Color.web(ON_SURFACE));

        Label subtitle = new Label("Please select your access role to securely log into the system.");
        subtitle.setFont(Font.font("Inter", 16));
        subtitle.setTextFill(Color.web(ON_SURFACE_VARIANT));
        header.getChildren().addAll(title, subtitle);

        // Cards Grid
        GridPane grid = new GridPane();
        grid.setHgap(24);
        grid.setVgap(24);
        grid.setMaxWidth(900);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33.33);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33.33);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33.33);
        grid.getColumnConstraints().addAll(col1, col2, col3);

        // Instantiating Role Cards
        // ADmin card
        StackPane adminCard = createRoleCard(SVG_ADMIN, "System Admin", "Manage users, oversee system integrity, and configure platform settings.", PRIMARY_COLOR, ICON_BG_DEFAULT);
        adminCard.setOnMouseClicked(e ->{
            AdminLoginPage adm = new AdminLoginPage();
            Scene sc = new Scene(adm.getAdminLoginPage());
            WelcomeStage.setScene(sc);
        });

        // Patient Card
        StackPane patientCard = createRoleCard(SVG_PATIENT, "Patient & Family", "Access medical records, track vitals, and communicate with healthcare providers.", PRIMARY_COLOR, ICON_BG_DEFAULT);
        patientCard.setOnMouseClicked(event->{
            FamilyLoginPage flg = new FamilyLoginPage();
            Scene sc = new Scene(flg.getFamilyLoginPage());
            WelcomeStage.setScene(sc);
        });
        // ambulance card
        StackPane ambulanceDriverCard = createRoleCard(SVG_AMBULANCE, "Ambulance Driver", "Receive dispatch alerts, navigate routes, and transmit patient vitals en route.", ICON_COLOR_ERROR, ICON_BG_ERROR);
        ambulanceDriverCard.setOnMouseClicked(e ->{
            DriverLoginPage dri = new DriverLoginPage();
            Scene sc = new Scene(dri.getDriverLoginPage());
            WelcomeStage.setScene(sc);
        });

        StackPane ambulanceNurceCard = createRoleCard(SVG_AMBULANCE, "Ambulance Nurce", "Receive dispatch alerts, navigate routes, and transmit patient vitals en route.", ICON_COLOR_ERROR, ICON_BG_ERROR);
        ambulanceNurceCard.setOnMouseClicked(event ->{
            NurseLoginPage nls = new NurseLoginPage();
            Scene sc = new Scene(nls.getNurseLoginPage());
            WelcomeStage.setScene(sc);
        });
        StackPane hospitalCard = createRoleCard(SVG_HOSPITAL, "Hospital Staff", "View incoming emergencies, manage ER capacity, and review patient data.", PRIMARY_COLOR, ICON_BG_DEFAULT);
        hospitalCard.setOnMouseClicked(event ->{
            HospitalLoginPage hpl = new HospitalLoginPage();
            Scene sc = new Scene(hpl.getHospitalLoginPage());
            WelcomeStage.setScene(sc);
        });
        StackPane policeCard = createRoleCard(SVG_POLICE, "Police Control Room", "Coordinate multi-agency emergency responses, monitor active incidents, and ensure scene security.", ICON_COLOR_POLICE, ICON_BG_POLICE);
        policeCard.setOnMouseClicked(event ->{
            PoliceLoginPage obj = new PoliceLoginPage();
            Scene sc = new Scene(obj.getPoliceLoginPage());
            WelcomeStage.setScene(sc);
        });
        // Grid Positioning
        grid.add(ambulanceDriverCard, 0, 0);
        grid.add(ambulanceNurceCard, 1, 0);
        grid.add(hospitalCard, 2, 0);
        grid.add(policeCard, 0, 1);
        grid.add(patientCard, 1, 1);
        grid.add(adminCard, 2, 1);
                

        // Footer
        Label footer = new Label("Secure Connection • End-to-End Encrypted • HIPAA Compliant");
        footer.setFont(Font.font("JetBrains Mono", 12));
        footer.setTextFill(Color.web(OUTLINE_VARIANT));
        footer.setMaxWidth(Double.MAX_VALUE);
        footer.setAlignment(Pos.CENTER);

        container.getChildren().addAll(header, grid, footer);
        return container;
    }

    private StackPane createRoleCard(String svgPathData, String titleText, String descText, String iconColorHex, String iconBgHex) {
        StackPane card = new StackPane();
        card.setPadding(new Insets(24));
        card.setStyle(String.format(
            "-fx-background-color: %s; -fx-background-radius: 16; -fx-border-color: %s; -fx-border-radius: 16; -fx-border-width: 1;",
            CARD_BG, OUTLINE_VARIANT
        ));

        // Background Accent Shape
        Circle bgDecoration = new Circle(40);
        bgDecoration.setFill(Color.web(PRIMARY_COLOR, 0.05));
        StackPane.setAlignment(bgDecoration, Pos.TOP_RIGHT);
        bgDecoration.setTranslateX(20);
        bgDecoration.setTranslateY(-20);

        // Icon Rendering via JavaFX SVGPath
        SVGPath iconNode = new SVGPath();
        iconNode.setContent(svgPathData);
        iconNode.setFill(Color.web(iconColorHex));

        StackPane iconBox = new StackPane(iconNode);
        iconBox.setPrefSize(64, 64);
        iconBox.setMaxSize(64, 64);
        iconBox.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 12;", iconBgHex));

        // Text
        Label cardTitle = new Label(titleText);
        cardTitle.setFont(Font.font("Plus Jakarta Sans", FontWeight.BOLD, 18));
        cardTitle.setTextFill(Color.web(ON_SURFACE));

        Label cardDesc = new Label(descText);
        cardDesc.setFont(Font.font("Inter", 14));
        cardDesc.setTextFill(Color.web(ON_SURFACE_VARIANT));
        cardDesc.setWrapText(true);

        VBox contentBox = new VBox(12, iconBox, cardTitle, cardDesc);
        contentBox.setAlignment(Pos.TOP_LEFT);

        card.getChildren().addAll(bgDecoration, contentBox);

        // Setup Interactive Animations
        setupHoverAnimation(card, iconBox);

        return card;
    }

    private void setupHoverAnimation(Node card, Node iconBox) {
        card.setOnMouseEntered(e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(200), card);
            tt.setToY(-8);

            ScaleTransition st = new ScaleTransition(Duration.millis(200), card);
            st.setToX(1.02);
            st.setToY(1.02);

            ScaleTransition iconSt = new ScaleTransition(Duration.millis(200), iconBox);
            iconSt.setToX(1.1);
            iconSt.setToY(1.1);

            new ParallelTransition(tt, st, iconSt).play();
            card.setStyle(String.format(
                "-fx-background-color: %s; -fx-background-radius: 16; -fx-border-color: %s; -fx-border-radius: 16; -fx-border-width: 1.5; -fx-effect: dropshadow(three-pass-box, rgba(0, 101, 145, 0.15), 15, 0, 0, 10);",
                CARD_BG, PRIMARY_COLOR
            ));
        });

        card.setOnMouseExited(e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(200), card);
            tt.setToY(0);

            ScaleTransition st = new ScaleTransition(Duration.millis(200), card);
            st.setToX(1.0);
            st.setToY(1.0);

            ScaleTransition iconSt = new ScaleTransition(Duration.millis(200), iconBox);
            iconSt.setToX(1.0);
            iconSt.setToY(1.0);

            new ParallelTransition(tt, st, iconSt).play();
            card.setStyle(String.format(
                "-fx-background-color: %s; -fx-background-radius: 16; -fx-border-color: %s; -fx-border-radius: 16; -fx-border-width: 1;",
                CARD_BG, OUTLINE_VARIANT
            ));
        });
    }

    private void playEntranceAnimations(VBox mainContainer) {
        GridPane grid = (GridPane) mainContainer.getChildren().get(1);
        int delay = 0;

        for (Node child : grid.getChildren()) {
            child.setOpacity(0);
            child.setTranslateY(30);

            FadeTransition ft = new FadeTransition(Duration.millis(500), child);
            ft.setToValue(1.0);

            TranslateTransition tt = new TranslateTransition(Duration.millis(500), child);
            tt.setToY(0);

            ParallelTransition pt = new ParallelTransition(ft, tt);
            pt.setDelay(Duration.millis(delay));
            pt.play();

            delay += 100;
        }
    }

}