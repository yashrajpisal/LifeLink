package com.kurukshetra.view.family;

import com.kurukshetra.view.Welcome;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.scene.Node;
public class Dashboard extends Application {

    public static Stage dashboardStage;
    private Scene dashboardScene;

    public static BorderPane root;

    // Colors
    private static final String BODY_BACKGROUND = "#F7F9FE";
    private static final String WHITE = "#FFFFFF";
    private static final String BLUE = "#0B4FCC";
    private static final String DARK_BLUE = "#0645C2";
    private static final String LIGHT_BLUE = "#EEF4FF";
    private static final String SIDEBAR_BLUE = "#F3F7FC";
    private static final String BORDER = "#D8E0EB";
    private static final String DIVIDER = "#E4E7EC";
    private static final String TEXT = "#101828";
    private static final String SECONDARY_TEXT = "#667085";

    @Override
    public void start(Stage stage) {
        dashboardStage = stage;

        root = new BorderPane();
        root.setStyle("-fx-background-color: " + BODY_BACKGROUND + ";");

        VBox sidebar = createSidebar(root);

        root.setLeft(sidebar);
        root.setCenter(createScrollPane(createDashboardBody()));

        dashboardScene = new Scene(root, 1553, 820);

        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("LifeLink - Emergency Care System");
        dashboardStage.setMaximized(true);
        dashboardStage.show();
    }

    // Create left sidebar
    private VBox createSidebar(BorderPane root) {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(255);
        sidebar.setMinWidth(255);
        sidebar.setMaxWidth(255);
        sidebar.setPadding(new Insets(26, 18, 20, 18));
        sidebar.setStyle(
            "-fx-background-color: " + WHITE + ";" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-width: 0 1px 0 0;"
        );

        // Brand
        HBox brand = new HBox(12);
        brand.setAlignment(Pos.CENTER_LEFT);
        brand.setPadding(new Insets(0, 8, 25, 8));

        VBox logoBox = new VBox();
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPrefSize(42, 42);
        logoBox.setMinSize(42, 42);
        logoBox.setMaxSize(42, 42);
        logoBox.setStyle(
            "-fx-background-color: " + BLUE + ";" +
            "-fx-background-radius: 12px;"
        );

        Label logo = new Label("✚");
        logo.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 22px;" +
            "-fx-font-weight: bold;"
        );

        logoBox.getChildren().add(logo);

        VBox brandText = new VBox(1);

        Label lifeLink = new Label("LifeLink");
        lifeLink.setStyle(
            "-fx-font-size: 21px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT + ";"
        );

        Label brandSub = new Label("Emergency Care");
        brandSub.setStyle(
            "-fx-font-size: 10px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + BLUE + ";"
        );

        brandText.getChildren().addAll(lifeLink, brandSub);
        brand.getChildren().addAll(logoBox, brandText);

        // Menu
        Label menuTitle = new Label("MAIN MENU");
        menuTitle.setPadding(new Insets(22, 10, 10, 10));
        menuTitle.setStyle(
            "-fx-font-size: 10px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #98A2B3;" +
            "-fx-letter-spacing: 1px;"
        );

        VBox navigation = new VBox(6);

        Button dashboardButton = createNavigationButton("⌂", "Dashboard", true);
        Button hospitalsButton = createNavigationButton("✚", "Hospitals", false);
        Button medicalButton = createNavigationButton("▣", "Medical Details", false);
        Button profileButton = createNavigationButton("◉", "Profile", false);
        Button settingButton = createNavigationButton("⚙", "Settings", false);

        // Dashboard
        dashboardButton.setOnAction(event -> {
            System.out.println("Dashboard");

            setActiveNavigation(navigation, dashboardButton);

            root.setCenter(
                (createDashboardBody())
            );
        });

        // Hospitals
        hospitalsButton.setOnAction(event -> {
            System.out.println("Hospitals");

            setActiveNavigation(navigation, hospitalsButton);

            Hospitals hospital = new Hospitals();

            root.setCenter(
                (hospital.getHospitalVBox())
            );
        });

        // Medical Details
        medicalButton.setOnAction(event -> {
            System.out.println("Medical Details");

            setActiveNavigation(navigation, medicalButton);

            MedicalDetails medicalDetails = new MedicalDetails();

            root.setCenter(medicalDetails.getMedicalVBox());
        });

        // Profile
        profileButton.setOnAction(event -> {
            System.out.println("Profile");

            setActiveNavigation(navigation, profileButton);

            Profile profile = new Profile();

            root.setCenter(
                (profile.getProfileVBox())
            );
        });

        // Settings
        settingButton.setOnAction(event -> {
            System.out.println("Settings");

            setActiveNavigation(navigation, settingButton);

            Setting setting = new Setting();

            root.setCenter(
                (setting.getSettingVBox())
            );
        });

        navigation.getChildren().addAll(
            dashboardButton,
            hospitalsButton,
            medicalButton,
            profileButton,
            settingButton
        );

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Account
        Label accountTitle = new Label("ACCOUNT");
        accountTitle.setPadding(new Insets(0, 10, 10, 10));
        accountTitle.setStyle(
            "-fx-font-size: 10px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #98A2B3;"
        );

        HBox profileBox = new HBox(11);
        profileBox.setAlignment(Pos.CENTER_LEFT);
        profileBox.setPadding(new Insets(10));
        profileBox.setStyle(
            "-fx-background-color: " + SIDEBAR_BLUE + ";" +
            "-fx-background-radius: 12px;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-radius: 12px;"
        );

        VBox avatar = new VBox();
        avatar.setAlignment(Pos.CENTER);
        avatar.setPrefSize(38, 38);
        avatar.setMinSize(38, 38);
        avatar.setMaxSize(38, 38);
        avatar.setStyle(
            "-fx-background-color: #DCE8FF;" +
            "-fx-background-radius: 50%;"
        );

        Label avatarText = new Label("J");
        avatarText.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + BLUE + ";"
        );

        avatar.getChildren().add(avatarText);

        VBox userInfo = new VBox(2);
        HBox.setHgrow(userInfo, Priority.ALWAYS);

        Label userName = new Label("Family User");
        userName.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT + ";"
        );

        Label userRole = new Label("PATIENT / FAMILY");
        userRole.setStyle(
            "-fx-font-size: 9px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + SECONDARY_TEXT + ";"
        );

        userInfo.getChildren().addAll(userName, userRole);

        Label more = new Label("⋮");
        more.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-text-fill: " + SECONDARY_TEXT + ";"
        );

        profileBox.getChildren().addAll(
            avatar,
            userInfo,
            more
        );

        // Logout
        Button logoutButton = createLogoutButton();

        logoutButton.setOnAction(event -> {
            System.out.println("Logout");

            try {
                Welcome welcome = new Welcome();
                welcome.start(dashboardStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        sidebar.getChildren().addAll(
            brand,
            createSidebarDivider(),
            menuTitle,
            navigation,
            spacer,
            accountTitle,
            profileBox,
            logoutButton
        );

        return sidebar;
    }

    // Create navigation button
    private Button createNavigationButton(String icon, String text, boolean active) {
        Button button = new Button();

        HBox content = new HBox(13);
        content.setAlignment(Pos.CENTER_LEFT);

        Label iconLabel = new Label(icon);
        iconLabel.setMinWidth(22);
        iconLabel.setAlignment(Pos.CENTER);

        Label textLabel = new Label(text);

        content.getChildren().addAll(iconLabel, textLabel);

        button.setGraphic(content);
        button.setText("");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMinHeight(46);
        button.setPrefHeight(46);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPadding(new Insets(0, 14, 0, 14));

        if (active) {
            applyActiveStyle(button, iconLabel, textLabel);
        } else {
            applyInactiveStyle(button, iconLabel, textLabel);
        }

        button.setOnMouseEntered(event -> {
            if (!button.getStyleClass().contains("active-nav")) {
                applyHoverStyle(button, iconLabel, textLabel);
            }
        });

        button.setOnMouseExited(event -> {
            if (!button.getStyleClass().contains("active-nav")) {
                applyInactiveStyle(button, iconLabel, textLabel);
            }
        });

        return button;
    }

    // Apply active navigation style
    private void applyActiveStyle(Button button, Label icon, Label text) {
        if (!button.getStyleClass().contains("active-nav")) {
            button.getStyleClass().add("active-nav");
        }

        button.setStyle(
            "-fx-background-color: " + LIGHT_BLUE + ";" +
            "-fx-background-radius: 11px;" +
            "-fx-cursor: hand;"
        );

        icon.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + BLUE + ";"
        );

        text.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + BLUE + ";"
        );
    }

    // Apply inactive navigation style
    private void applyInactiveStyle(Button button, Label icon, Label text) {
        button.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-background-radius: 11px;" +
            "-fx-cursor: hand;"
        );

        icon.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-text-fill: #667085;"
        );

        text.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-font-weight: 600;" +
            "-fx-text-fill: #475467;"
        );
    }

    // Apply hover navigation style
    private void applyHoverStyle(Button button, Label icon, Label text) {
        button.setStyle(
            "-fx-background-color: #F2F5FA;" +
            "-fx-background-radius: 11px;" +
            "-fx-cursor: hand;"
        );

        icon.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-text-fill: " + BLUE + ";"
        );

        text.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT + ";"
        );
    }

    // Set selected navigation
    private void setActiveNavigation(VBox navigation, Button selectedButton) {
        for (Node node : navigation.getChildren()) {
            if (node instanceof Button button) {
                HBox content = (HBox) button.getGraphic();

                Label icon = (Label) content.getChildren().get(0);
                Label text = (Label) content.getChildren().get(1);

                button.getStyleClass().remove("active-nav");

                if (button == selectedButton) {
                    applyActiveStyle(button, icon, text);
                } else {
                    applyInactiveStyle(button, icon, text);
                }
            }
        }
    }

    // Create logout button
    private Button createLogoutButton() {
        Button logout = new Button();

        HBox content = new HBox(13);
        content.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("↪");
        icon.setMinWidth(22);
        icon.setAlignment(Pos.CENTER);

        Label text = new Label("Logout");

        icon.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-text-fill: #C81E1E;"
        );

        text.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #C81E1E;"
        );

        content.getChildren().addAll(icon, text);

        logout.setGraphic(content);
        logout.setText("");
        logout.setMaxWidth(Double.MAX_VALUE);
        logout.setMinHeight(44);
        logout.setPrefHeight(44);
        logout.setAlignment(Pos.CENTER_LEFT);
        logout.setPadding(new Insets(0, 14, 0, 14));

        logout.setStyle(
            "-fx-background-color: #FFF4F3;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;"
        );

        logout.setOnMouseEntered(event ->
            logout.setStyle(
                "-fx-background-color: #FFE5E2;" +
                "-fx-background-radius: 10px;" +
                "-fx-cursor: hand;"
            )
        );

        logout.setOnMouseExited(event ->
            logout.setStyle(
                "-fx-background-color: #FFF4F3;" +
                "-fx-background-radius: 10px;" +
                "-fx-cursor: hand;"
            )
        );

        return logout;
    }

    // Create sidebar divider
    private Region createSidebarDivider() {
        Region divider = new Region();

        divider.setPrefHeight(1);
        divider.setMaxWidth(Double.MAX_VALUE);
        divider.setStyle("-fx-background-color: " + DIVIDER + ";");

        return divider;
    }

    // Create dashboard body
    private VBox createDashboardBody() {
        VBox body = new VBox(24);

        body.setPadding(new Insets(38, 40, 45, 40));
        body.setStyle(
            "-fx-background-color: " + BODY_BACKGROUND + ";"
        );

        Label title = new Label("Dashboard");
        title.setStyle(
            "-fx-font-size: 32px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: " + TEXT + ";"
        );

        Label subtitle = new Label(
            "Welcome to your LifeLink emergency care dashboard."
        );

        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: " + SECONDARY_TEXT + ";"
        );

        body.getChildren().addAll(title, subtitle);

        return body;
    }

    // Create scroll pane
    private ScrollPane createScrollPane(VBox content) {
        ScrollPane scrollPane = new ScrollPane(content);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(true);

        scrollPane.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-background: " + BODY_BACKGROUND + ";"
        );

        return scrollPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}