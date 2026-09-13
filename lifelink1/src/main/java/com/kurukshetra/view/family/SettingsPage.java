package com.kurukshetra.view.family;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Modern, Rich, Animated, and Clean System & Account Settings.
 * Features:
 * - Profile and Emergency Contact Management.
 * - Two-Factor Authentication and Security Directives.
 * - Push & SMS Emergency Notification Preferences.
 * - Regional Localization and System Appearance.
 */
public class SettingsPage {

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

    private Label feedbackBanner;
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;

    public BorderPane setBorderPane(Stage stage) {
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.SETTINGS);
        bp.setLeft(sidebar);

        VBox mainContent = buildMainContent();
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: " + PAGE_BG + "; -fx-background-color: transparent; -fx-border-color: transparent;");
        bp.setCenter(scrollPane);

        MedicalReports.playPageAnimation(scrollPane);
        return bp;
    }

    private VBox buildMainContent() {
        HBox header = buildHeader();

        feedbackBanner = new Label();
        feedbackBanner.setVisible(false);
        feedbackBanner.setManaged(false);

        VBox leftColumn = new VBox(18, buildAccountProfileCard(), buildSecurityCard());
        leftColumn.setPrefWidth(490);
        HBox.setHgrow(leftColumn, Priority.ALWAYS);

        VBox rightColumn = new VBox(18, buildNotificationsCard(), buildAppearanceCard());
        rightColumn.setPrefWidth(340);
        rightColumn.setMinWidth(300);

        HBox bodyRow = new HBox(18, leftColumn, rightColumn);
        HBox actionsRow = buildActionsRow();

        VBox mainContent = new VBox(16, header, feedbackBanner, bodyRow, actionsRow);
        mainContent.setStyle("-fx-background-color: " + PAGE_BG + ";");
        mainContent.setPadding(new Insets(20, 24, 24, 24));
        return mainContent;
    }

    private HBox buildHeader() {
        VBox titleBox = new VBox(2);
        Label title = new Label("Family Account & System Preferences");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 22px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label subtitle = new Label("Configure personal profile credentials, emergency alert dispatch rules, and system behavior.");
        subtitle.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_SECONDARY + ";");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox securityBadge = new HBox(6);
        securityBadge.setAlignment(Pos.CENTER);
        securityBadge.setPadding(new Insets(6, 12, 6, 12));
        securityBadge.setStyle(
                "-fx-background-color: " + LIGHT_GREEN + ";" +
                "-fx-border-color: #86EFAC;" +
                "-fx-border-radius: 16px;" +
                "-fx-background-radius: 16px;"
        );
        Label secIcon = new Label("🛡");
        Label secTxt = new Label("256-Bit Encrypted Portal");
        secTxt.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + GREEN + ";");
        securityBadge.getChildren().addAll(secIcon, secTxt);

        HBox header = new HBox(16, titleBox, spacer, securityBadge);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private VBox buildAccountProfileCard() {
        Label icon = new Label("👤");
        icon.setStyle("-fx-font-size: 15px;");
        Label title = new Label("Family Lead Account & Profile");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 15px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        HBox header = new HBox(8, icon, title);
        header.setAlignment(Pos.CENTER_LEFT);

        String activeEmail = FamilyHomePage.USER_EMAIL != null ? FamilyHomePage.USER_EMAIL : "sarah.m@example.com";
        VBox fullNameBox = buildField("FULL LEGAL NAME", nameField = new TextField("Sarah Miller"));
        VBox emailBox = buildField("REGISTERED EMAIL ADDRESS", emailField = new TextField(activeEmail));
        VBox phoneBox = buildField("PRIMARY PHONE (FAST2SMS ALERTS)", phoneField = new TextField("+91 98765 43210"));

        HBox.setHgrow(emailBox, Priority.ALWAYS);
        HBox.setHgrow(phoneBox, Priority.ALWAYS);
        HBox contactRow = new HBox(14, emailBox, phoneBox);

        Button updateBtn = new Button("Update Lead Profile");
        updateBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 11.5px;" +
                "-fx-background-radius: 8px;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-padding: 8px 16px;" +
                "-fx-cursor: hand;"
        );
        updateBtn.setOnAction(e -> showFeedback("✓ Account details updated successfully", true));

        VBox card = new VBox(14, header, fullNameBox, contactRow, updateBtn);
        applyCardStyle(card);
        return card;
    }

    private VBox buildField(String label, TextField field) {
        Label lbl = new Label(label);
        lbl.setStyle(FONT_FAMILY + "-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        field.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PALE_PEACH + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-padding: 9px;" +
                "-fx-font-size: 12.5px;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );
        field.setMaxWidth(Double.MAX_VALUE);

        return new VBox(4, lbl, field);
    }

    private VBox buildSecurityCard() {
        Label icon = new Label("🔒");
        icon.setStyle("-fx-font-size: 15px;");
        Label title = new Label("Security & Access Governance");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 15px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        HBox header = new HBox(8, icon, title);
        header.setAlignment(Pos.CENTER_LEFT);

        HBox passwordRow = buildSecurityRow("Account Password", "Last changed 45 days ago • High entropy cipher", null);
        Label changePassword = new Label("Change Password");
        changePassword.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-text-fill: " + PRIMARY + "; -fx-cursor: hand; -fx-font-weight: bold;");
        changePassword.setOnMouseClicked(e -> showFeedback("Password reset link sent to your registered email", true));
        passwordRow.getChildren().add(changePassword);

        ToggleButton twoFA = createToggle(true, GREEN);
        HBox twoFARow = buildSecurityRow("Two-Factor Authentication", "Verify SMS OTP on emergency records dispatch", twoFA);

        VBox card = new VBox(14, header, passwordRow, twoFARow);
        applyCardStyle(card);
        return card;
    }

    private HBox buildSecurityRow(String title, String desc, ToggleButton toggle) {
        Label titleLbl = new Label(title);
        titleLbl.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label descLbl = new Label(desc);
        descLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");
        descLbl.setWrapText(true);

        VBox textBox = new VBox(2, titleLbl, descLbl);
        textBox.setMaxWidth(300);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(10, textBox, spacer);
        row.setAlignment(Pos.CENTER_LEFT);
        if (toggle != null) {
            row.getChildren().add(toggle);
        }
        return row;
    }

    private VBox buildNotificationsCard() {
        Label title = new Label("🔔  Emergency Notifications");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 15px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        HBox emergencyRow = buildToggleRow("High Priority Emergency Alerts", true, "#DC2626");
        HBox hospitalRow = buildToggleRow("Hospital Bed & ICU Updates", true, GREEN);
        HBox appointmentRow = buildToggleRow("Prescription Sync Reminders", true, GREEN);
        HBox aiRow = buildToggleRow("Sister Ananya AI Health Insights", true, PRIMARY);

        VBox card = new VBox(14, title, emergencyRow, hospitalRow, appointmentRow, aiRow);
        applyCardStyle(card);
        return card;
    }

    private HBox buildToggleRow(String label, boolean on, String onColor) {
        Label lbl = new Label(label);
        lbl.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: 600; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ToggleButton toggle = createToggle(on, onColor);
        HBox row = new HBox(10, lbl, spacer, toggle);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private ToggleButton createToggle(boolean selected, String onColor) {
        ToggleButton toggle = new ToggleButton();
        toggle.setSelected(selected);
        toggle.setMinSize(40, 22);
        toggle.setMaxSize(40, 22);
        updateToggleStyle(toggle, onColor);
        toggle.selectedProperty().addListener((obs, oldV, newV) -> updateToggleStyle(toggle, onColor));
        return toggle;
    }

    private void updateToggleStyle(ToggleButton toggle, String onColor) {
        if (toggle.isSelected()) {
            toggle.setText("ON");
            toggle.setStyle(FONT_FAMILY + "-fx-background-color: " + onColor + "; -fx-text-fill: white; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 15; -fx-cursor: hand;");
        } else {
            toggle.setText("OFF");
            toggle.setStyle(FONT_FAMILY + "-fx-background-color: #CBD5E1; -fx-text-fill: #475569; -fx-font-size: 9px; -fx-font-weight: bold; -fx-background-radius: 15; -fx-cursor: hand;");
        }
    }

    private VBox buildAppearanceCard() {
        Label title = new Label("🎨  Localization & Interface");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 15px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label langLbl = new Label("Default Portal Language");
        langLbl.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        ComboBox<String> langBox = new ComboBox<>();
        langBox.getItems().addAll("English (India)", "Marathi (मराठी)", "Hindi (हिंदी)");
        langBox.setValue("English (India)");
        langBox.setStyle(FONT_FAMILY + "-fx-background-color: " + PALE_PEACH + "; -fx-background-radius: 8px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-font-size: 12px;");
        langBox.setMaxWidth(Double.MAX_VALUE);

        Label tzLbl = new Label("Regional Hospital Timezone");
        tzLbl.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        ComboBox<String> tzBox = new ComboBox<>();
        tzBox.getItems().addAll("(GMT+05:30) India Standard Time (IST)", "(GMT+00:00) UTC", "(GMT-05:00) Eastern Time");
        tzBox.setValue("(GMT+05:30) India Standard Time (IST)");
        tzBox.setStyle(FONT_FAMILY + "-fx-background-color: " + PALE_PEACH + "; -fx-background-radius: 8px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-font-size: 12px;");
        tzBox.setMaxWidth(Double.MAX_VALUE);

        VBox card = new VBox(10, title, langLbl, langBox, tzLbl, tzBox);
        applyCardStyle(card);
        return card;
    }

    private HBox buildActionsRow() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button discard = new Button("Discard Changes");
        discard.setStyle(
                FONT_FAMILY +
                "-fx-background-color: transparent;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-background-radius: 8px;" +
                "-fx-text-fill: " + TEXT_SECONDARY + ";" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 9px 18px;" +
                "-fx-cursor: hand;"
        );
        discard.setOnAction(e -> showFeedback("Changes reverted to current profile baseline", false));

        Button save = new Button("Save Preferences");
        save.setStyle(
                FONT_FAMILY +
                "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + ");" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 12px;" +
                "-fx-background-radius: 8px;" +
                "-fx-padding: 10px 22px;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.25), 8, 0, 0, 2);"
        );
        save.setOnAction(e -> showFeedback("✓ All account and alert preferences saved successfully", true));

        HBox row = new HBox(12, spacer, discard, save);
        row.setAlignment(Pos.CENTER_RIGHT);
        return row;
    }

    private void showFeedback(String msg, boolean success) {
        feedbackBanner.setText(msg);
        feedbackBanner.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + (success ? LIGHT_GREEN : VERY_LIGHT_TERRACOTTA) + ";" +
                "-fx-text-fill: " + (success ? GREEN : PRIMARY_DARK) + ";" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10px 16px;" +
                "-fx-border-color: " + (success ? "#86EFAC" : LIGHT_TERRACOTTA) + ";" +
                "-fx-border-radius: 8px;" +
                "-fx-background-radius: 8px;"
        );
        feedbackBanner.setVisible(true);
        feedbackBanner.setManaged(true);

        FadeTransition ft = new FadeTransition(Duration.millis(250), feedbackBanner);
        ft.setFromValue(0.2);
        ft.setToValue(1.0);
        ft.play();
    }

    private void applyCardStyle(VBox card) {
        card.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 16px;" +
                "-fx-background-radius: 16px;" +
                "-fx-padding: 20px;"
        );
        card.setEffect(new DropShadow(10, 0, 3, Color.rgb(120, 47, 22, 0.05)));
    }
}