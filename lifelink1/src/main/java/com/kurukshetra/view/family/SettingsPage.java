package com.kurukshetra.view.family;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class SettingsPage {

    public BorderPane setBorderPane(Stage stage) {

        BorderPane bp = new BorderPane();
        bp.getStyleClass().add("root-pane");

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.SETTINGS);
        bp.setLeft(sidebar);

        VBox mainContent = buildMainContent();
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("main-scroll");
        bp.setCenter(scrollPane);

        return bp;
    }

    // ---------------- MAIN CONTENT ----------------

    private VBox buildMainContent() {

        HBox header = buildHeader();
        TextFlow subtitle = buildSubtitle();

        VBox leftColumn = new VBox(20, buildAccountProfileCard(), buildSecurityCard());
        leftColumn.setPrefWidth(480);
        HBox.setHgrow(leftColumn, Priority.ALWAYS);

        VBox rightColumn = new VBox(20, buildNotificationsCard(), buildAppearanceCard());
        rightColumn.setPrefWidth(300);

        HBox bodyRow = new HBox(20, leftColumn, rightColumn);

        HBox actionsRow = buildActionsRow();

        VBox mainContent = new VBox(16, header, subtitle, bodyRow, actionsRow);
        mainContent.getStyleClass().add("main-content");
        mainContent.setPadding(new Insets(24));

        return mainContent;
    }

    private HBox buildHeader() {
        Label title = new Label("System Settings");
        title.getStyleClass().add("page-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label bell = new Label("🔔");
        Label help = new Label("❓");
        bell.getStyleClass().add("icon-label");
        help.getStyleClass().add("icon-label");

        Label avatar = new Label("👩");
        avatar.getStyleClass().add("avatar-label");

        HBox header = new HBox(16, title, spacer, bell, help, avatar);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private TextFlow buildSubtitle() {

        Text pre = new Text("Manage your ");
        pre.getStyleClass().add("muted-text");

        Text account = new Text("account");
        account.getStyleClass().add("subtitle-highlight-blue");

        Text mid1 = new Text(" preferences, ");
        mid1.getStyleClass().add("muted-text");

        Text security = new Text("security");
        security.getStyleClass().add("subtitle-highlight-orange");

        Text mid2 = new Text(", and ");
        mid2.getStyleClass().add("muted-text");

        Text application = new Text("application");
        application.getStyleClass().add("subtitle-highlight-teal");

        Text post = new Text(" experience.");
        post.getStyleClass().add("muted-text");

        return new TextFlow(pre, account, mid1, security, mid2, application, post);
    }

    // ---------------- ACCOUNT & PROFILE CARD ----------------

    private VBox buildAccountProfileCard() {

        Label icon = new Label("👤");
        icon.getStyleClass().add("card-icon");
        Label title = new Label("Account & Profile");
        title.getStyleClass().add("settings-card-title");
        HBox header = new HBox(8, icon, title);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox fullNameBox = buildField("Full Name", "Sarah Miller");

        VBox emailBox = buildField("Email Address", "sarah.m@example.com");
        VBox phoneBox = buildField("Phone Number", "+1 (555) 123-4567");
        HBox.setHgrow(emailBox, Priority.ALWAYS);
        HBox.setHgrow(phoneBox, Priority.ALWAYS);
        HBox contactRow = new HBox(14, emailBox, phoneBox);

        Button updateBtn = new Button("Update Profile");
        updateBtn.getStyleClass().add("update-profile-btn");
        updateBtn.setOnAction(e -> System.out.println("Update Profile clicked"));

        VBox card = new VBox(16, header, fullNameBox, contactRow, updateBtn);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(20));
        return card;
    }

    private VBox buildField(String label, String value) {

        Label lbl = new Label(label);
        lbl.getStyleClass().add("field-label");

        TextField field = new TextField(value);
        field.getStyleClass().add("settings-field");
        field.setMaxWidth(Double.MAX_VALUE);

        VBox box = new VBox(6, lbl, field);
        return box;
    }

    // ---------------- SECURITY CARD ----------------

    private VBox buildSecurityCard() {

        Label icon = new Label("🔒");
        icon.getStyleClass().add("card-icon");
        Label title = new Label("Security");
        title.getStyleClass().add("settings-card-title");
        HBox header = new HBox(8, icon, title);
        header.setAlignment(Pos.CENTER_LEFT);

        HBox passwordRow = buildSecurityRow("Password", "Last changed 3 months ago", null);
        Label changePassword = new Label("Change Password");
        changePassword.getStyleClass().add("change-password-link");
        passwordRow.getChildren().add(changePassword);

        ToggleButton twoFA = createToggle(true, "#22c55e");
        HBox twoFARow = buildSecurityRow("Two-Factor Authentication",
                "Add an extra layer of security to your account", twoFA);

        VBox card = new VBox(18, header, passwordRow, twoFARow);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(20));
        return card;
    }

    private HBox buildSecurityRow(String title, String desc, ToggleButton toggle) {

        Label titleLbl = new Label(title);
        titleLbl.getStyleClass().add("settings-row-title");

        Label descLbl = new Label(desc);
        descLbl.getStyleClass().add("settings-row-desc");
        descLbl.setWrapText(true);

        VBox textBox = new VBox(2, titleLbl, descLbl);
        textBox.setMaxWidth(280);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox row = new HBox(10, textBox, spacer);
        row.setAlignment(Pos.CENTER_LEFT);

        if (toggle != null) {
            row.getChildren().add(toggle);
        }

        return row;
    }

    // ---------------- NOTIFICATIONS CARD ----------------

    private VBox buildNotificationsCard() {

        Label title = new Label("🔔  Notifications");
        title.getStyleClass().add("settings-card-title");

        HBox emergencyRow = buildToggleRow("Emergency Alerts", true, "#e5484d");
        HBox hospitalRow = buildToggleRow("Hospital Updates", true, "#22c55e");
        HBox appointmentRow = buildToggleRow("Appointment Reminders", true, "#22c55e");
        HBox aiRow = buildToggleRow("AI Health Insights", false, "#22c55e");

        VBox card = new VBox(16, title, emergencyRow, hospitalRow, appointmentRow, aiRow);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(20));
        return card;
    }

    private HBox buildToggleRow(String label, boolean on, String onColor) {

        Label lbl = new Label(label);
        lbl.getStyleClass().add("settings-row-title");

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
        toggle.setMinSize(36, 20);
        toggle.setMaxSize(36, 20);
        updateToggleStyle(toggle, onColor);
        toggle.selectedProperty().addListener((obs, oldV, newV) -> updateToggleStyle(toggle, onColor));
        return toggle;
    }

    private void updateToggleStyle(ToggleButton toggle, String onColor) {
        if (toggle.isSelected()) {
            toggle.setStyle("-fx-background-color: " + onColor + "; -fx-background-radius: 15; -fx-border-color: transparent;");
        } else {
            toggle.setStyle("-fx-background-color: #c7cbd3; -fx-background-radius: 15; -fx-border-color: transparent;");
        }
    }

    // ---------------- APPEARANCE & REGION CARD ----------------

    private VBox buildAppearanceCard() {

        Label title = new Label("🎨  Appearance & Region");
        title.getStyleClass().add("settings-card-title");

        Label themeLbl = new Label("Theme");
        themeLbl.getStyleClass().add("field-label");

        Button lightBtn = new Button("☀  Light");
        Button darkBtn = new Button("🌙  Dark");
        lightBtn.getStyleClass().addAll("theme-btn", "theme-btn-active");
        darkBtn.getStyleClass().add("theme-btn");
        HBox.setHgrow(lightBtn, Priority.ALWAYS);
        HBox.setHgrow(darkBtn, Priority.ALWAYS);
        lightBtn.setMaxWidth(Double.MAX_VALUE);
        darkBtn.setMaxWidth(Double.MAX_VALUE);

        lightBtn.setOnAction(e -> {
            lightBtn.getStyleClass().add("theme-btn-active");
            darkBtn.getStyleClass().remove("theme-btn-active");
        });
        darkBtn.setOnAction(e -> {
            darkBtn.getStyleClass().add("theme-btn-active");
            lightBtn.getStyleClass().remove("theme-btn-active");
        });

        HBox themeRow = new HBox(8, lightBtn, darkBtn);

        Label langLbl = new Label("Language");
        langLbl.getStyleClass().add("field-label");
        ComboBox<String> langBox = new ComboBox<>();
        langBox.getItems().addAll("English (US)", "Spanish", "French");
        langBox.setValue("English (US)");
        langBox.getStyleClass().add("settings-combo");
        langBox.setMaxWidth(Double.MAX_VALUE);

        Label tzLbl = new Label("Timezone");
        tzLbl.getStyleClass().add("field-label");
        ComboBox<String> tzBox = new ComboBox<>();
        tzBox.getItems().addAll(
                "(GMT-05:00) Eastern Time",
                "(GMT-06:00) Central Time",
                "(GMT-08:00) Pacific Time");
        tzBox.setValue("(GMT-05:00) Eastern Time");
        tzBox.getStyleClass().add("settings-combo");
        tzBox.setMaxWidth(Double.MAX_VALUE);

        VBox card = new VBox(12, title, themeLbl, themeRow, langLbl, langBox, tzLbl, tzBox);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(20));
        return card;
    }

    // ---------------- SAVE / DISCARD ----------------

    private HBox buildActionsRow() {

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button discard = new Button("Discard");
        discard.getStyleClass().add("discard-btn");
        discard.setOnAction(e -> System.out.println("Discard clicked"));

        Button save = new Button("Save Changes");
        save.getStyleClass().add("primary-dark-btn");
        save.setOnAction(e -> System.out.println("Save Changes clicked"));

        HBox row = new HBox(10, spacer, discard, save);
        row.setAlignment(Pos.CENTER_RIGHT);
        return row;
    }
}
