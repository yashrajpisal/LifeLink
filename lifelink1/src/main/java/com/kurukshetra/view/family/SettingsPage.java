// package com.kurukshetra.view.family;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.ComboBox;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TextField;
// import javafx.scene.control.ToggleButton;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;
// import javafx.scene.text.TextFlow;
// import javafx.stage.Stage;


// public class SettingsPage {

//     public BorderPane setBorderPane(Stage stage) {

//         BorderPane bp = new BorderPane();
//         bp.getStyleClass().add("root-pane");

//         VBox sidebar = Sidebar.build(stage, Sidebar.Page.SETTINGS);
//         bp.setLeft(sidebar);

//         VBox mainContent = buildMainContent();
//         ScrollPane scrollPane = new ScrollPane(mainContent);
//         scrollPane.setFitToWidth(true);
//         scrollPane.getStyleClass().add("main-scroll");
//         bp.setCenter(scrollPane);

//         MedicalReports.playPageAnimation(scrollPane);

//         return bp;
//     }

//     // ---------------- MAIN CONTENT ----------------

//     private VBox buildMainContent() {

//         HBox header = buildHeader();
//         TextFlow subtitle = buildSubtitle();

//         VBox leftColumn = new VBox(20, buildAccountProfileCard(), buildSecurityCard());
//         leftColumn.setPrefWidth(480);
//         HBox.setHgrow(leftColumn, Priority.ALWAYS);

//         VBox rightColumn = new VBox(20, buildNotificationsCard(), buildAppearanceCard());
//         rightColumn.setPrefWidth(300);

//         HBox bodyRow = new HBox(20, leftColumn, rightColumn);

//         HBox actionsRow = buildActionsRow();

//         VBox mainContent = new VBox(16, header, subtitle, bodyRow, actionsRow);
//         mainContent.getStyleClass().add("main-content");
//         mainContent.setPadding(new Insets(24));

//         return mainContent;
//     }

//     private HBox buildHeader() {
//         Label title = new Label("System Settings");
//         title.getStyleClass().add("page-title");

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         Label bell = new Label("🔔");
//         Label help = new Label("❓");
//         bell.getStyleClass().add("icon-label");
//         help.getStyleClass().add("icon-label");

//         Label avatar = new Label("👩");
//         avatar.getStyleClass().add("avatar-label");

//         HBox header = new HBox(16, title, spacer, bell, help, avatar);
//         header.setAlignment(Pos.CENTER_LEFT);
//         return header;
//     }

//     private TextFlow buildSubtitle() {

//         Text pre = new Text("Manage your ");
//         pre.getStyleClass().add("muted-text");

//         Text account = new Text("account");
//         account.getStyleClass().add("subtitle-highlight-blue");

//         Text mid1 = new Text(" preferences, ");
//         mid1.getStyleClass().add("muted-text");

//         Text security = new Text("security");
//         security.getStyleClass().add("subtitle-highlight-orange");

//         Text mid2 = new Text(", and ");
//         mid2.getStyleClass().add("muted-text");

//         Text application = new Text("application");
//         application.getStyleClass().add("subtitle-highlight-teal");

//         Text post = new Text(" experience.");
//         post.getStyleClass().add("muted-text");

//         return new TextFlow(pre, account, mid1, security, mid2, application, post);
//     }

//     // ---------------- ACCOUNT & PROFILE CARD ----------------

//     private VBox buildAccountProfileCard() {

//         Label icon = new Label("👤");
//         icon.getStyleClass().add("card-icon");
//         Label title = new Label("Account & Profile");
//         title.getStyleClass().add("settings-card-title");
//         HBox header = new HBox(8, icon, title);
//         header.setAlignment(Pos.CENTER_LEFT);

//         VBox fullNameBox = buildField("Full Name", "Sarah Miller");

//         VBox emailBox = buildField("Email Address", "sarah.m@example.com");
//         VBox phoneBox = buildField("Phone Number", "+1 (555) 123-4567");
//         HBox.setHgrow(emailBox, Priority.ALWAYS);
//         HBox.setHgrow(phoneBox, Priority.ALWAYS);
//         HBox contactRow = new HBox(14, emailBox, phoneBox);

//         Button updateBtn = new Button("Update Profile");
//         updateBtn.getStyleClass().add("update-profile-btn");
//         updateBtn.setOnAction(e -> System.out.println("Update Profile clicked"));

//         VBox card = new VBox(16, header, fullNameBox, contactRow, updateBtn);
//         card.getStyleClass().add("card");
//         card.setPadding(new Insets(20));
//         return card;
//     }

//     private VBox buildField(String label, String value) {

//         Label lbl = new Label(label);
//         lbl.getStyleClass().add("field-label");

//         TextField field = new TextField(value);
//         field.getStyleClass().add("settings-field");
//         field.setMaxWidth(Double.MAX_VALUE);

//         VBox box = new VBox(6, lbl, field);
//         return box;
//     }

//     // ---------------- SECURITY CARD ----------------

//     private VBox buildSecurityCard() {

//         Label icon = new Label("🔒");
//         icon.getStyleClass().add("card-icon");
//         Label title = new Label("Security");
//         title.getStyleClass().add("settings-card-title");
//         HBox header = new HBox(8, icon, title);
//         header.setAlignment(Pos.CENTER_LEFT);

//         HBox passwordRow = buildSecurityRow("Password", "Last changed 3 months ago", null);
//         Label changePassword = new Label("Change Password");
//         changePassword.getStyleClass().add("change-password-link");
//         passwordRow.getChildren().add(changePassword);

//         ToggleButton twoFA = createToggle(true, "#22c55e");
//         HBox twoFARow = buildSecurityRow("Two-Factor Authentication",
//                 "Add an extra layer of security to your account", twoFA);

//         VBox card = new VBox(18, header, passwordRow, twoFARow);
//         card.getStyleClass().add("card");
//         card.setPadding(new Insets(20));
//         return card;
//     }

//     private HBox buildSecurityRow(String title, String desc, ToggleButton toggle) {

//         Label titleLbl = new Label(title);
//         titleLbl.getStyleClass().add("settings-row-title");

//         Label descLbl = new Label(desc);
//         descLbl.getStyleClass().add("settings-row-desc");
//         descLbl.setWrapText(true);

//         VBox textBox = new VBox(2, titleLbl, descLbl);
//         textBox.setMaxWidth(280);

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         HBox row = new HBox(10, textBox, spacer);
//         row.setAlignment(Pos.CENTER_LEFT);

//         if (toggle != null) {
//             row.getChildren().add(toggle);
//         }

//         return row;
//     }

//     // ---------------- NOTIFICATIONS CARD ----------------

//     private VBox buildNotificationsCard() {

//         Label title = new Label("🔔  Notifications");
//         title.getStyleClass().add("settings-card-title");

//         HBox emergencyRow = buildToggleRow("Emergency Alerts", true, "#e5484d");
//         HBox hospitalRow = buildToggleRow("Hospital Updates", true, "#22c55e");
//         HBox appointmentRow = buildToggleRow("Appointment Reminders", true, "#22c55e");
//         HBox aiRow = buildToggleRow("AI Health Insights", false, "#22c55e");

//         VBox card = new VBox(16, title, emergencyRow, hospitalRow, appointmentRow, aiRow);
//         card.getStyleClass().add("card");
//         card.setPadding(new Insets(20));
//         return card;
//     }

//     private HBox buildToggleRow(String label, boolean on, String onColor) {

//         Label lbl = new Label(label);
//         lbl.getStyleClass().add("settings-row-title");

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         ToggleButton toggle = createToggle(on, onColor);

//         HBox row = new HBox(10, lbl, spacer, toggle);
//         row.setAlignment(Pos.CENTER_LEFT);
//         return row;
//     }

//     private ToggleButton createToggle(boolean selected, String onColor) {

//         ToggleButton toggle = new ToggleButton();
//         toggle.setSelected(selected);
//         toggle.setMinSize(36, 20);
//         toggle.setMaxSize(36, 20);
//         updateToggleStyle(toggle, onColor);
//         toggle.selectedProperty().addListener((obs, oldV, newV) -> updateToggleStyle(toggle, onColor));
//         return toggle;
//     }

//     private void updateToggleStyle(ToggleButton toggle, String onColor) {
//         if (toggle.isSelected()) {
//             toggle.setStyle("-fx-background-color: " + onColor + "; -fx-background-radius: 15; -fx-border-color: transparent;");
//         } else {
//             toggle.setStyle("-fx-background-color: #c7cbd3; -fx-background-radius: 15; -fx-border-color: transparent;");
//         }
//     }

//     // ---------------- APPEARANCE & REGION CARD ----------------

//     private VBox buildAppearanceCard() {

//         Label title = new Label("🎨  Appearance & Region");
//         title.getStyleClass().add("settings-card-title");

//         Label themeLbl = new Label("Theme");
//         themeLbl.getStyleClass().add("field-label");

//         Button lightBtn = new Button("☀  Light");
//         Button darkBtn = new Button("🌙  Dark");
//         lightBtn.getStyleClass().addAll("theme-btn", "theme-btn-active");
//         darkBtn.getStyleClass().add("theme-btn");
//         HBox.setHgrow(lightBtn, Priority.ALWAYS);
//         HBox.setHgrow(darkBtn, Priority.ALWAYS);
//         lightBtn.setMaxWidth(Double.MAX_VALUE);
//         darkBtn.setMaxWidth(Double.MAX_VALUE);

//         lightBtn.setOnAction(e -> {
//             lightBtn.getStyleClass().add("theme-btn-active");
//             darkBtn.getStyleClass().remove("theme-btn-active");
//         });
//         darkBtn.setOnAction(e -> {
//             darkBtn.getStyleClass().add("theme-btn-active");
//             lightBtn.getStyleClass().remove("theme-btn-active");
//         });

//         HBox themeRow = new HBox(8, lightBtn, darkBtn);

//         Label langLbl = new Label("Language");
//         langLbl.getStyleClass().add("field-label");
//         ComboBox<String> langBox = new ComboBox<>();
//         langBox.getItems().addAll("English (US)", "Spanish", "French");
//         langBox.setValue("English (US)");
//         langBox.getStyleClass().add("settings-combo");
//         langBox.setMaxWidth(Double.MAX_VALUE);

//         Label tzLbl = new Label("Timezone");
//         tzLbl.getStyleClass().add("field-label");
//         ComboBox<String> tzBox = new ComboBox<>();
//         tzBox.getItems().addAll(
//                 "(GMT-05:00) Eastern Time",
//                 "(GMT-06:00) Central Time",
//                 "(GMT-08:00) Pacific Time");
//         tzBox.setValue("(GMT-05:00) Eastern Time");
//         tzBox.getStyleClass().add("settings-combo");
//         tzBox.setMaxWidth(Double.MAX_VALUE);

//         VBox card = new VBox(12, title, themeLbl, themeRow, langLbl, langBox, tzLbl, tzBox);
//         card.getStyleClass().add("card");
//         card.setPadding(new Insets(20));
//         return card;
//     }

//     // ---------------- SAVE / DISCARD ----------------

//     private HBox buildActionsRow() {

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         Button discard = new Button("Discard");
//         discard.getStyleClass().add("discard-btn");
//         discard.setOnAction(e -> System.out.println("Discard clicked"));

//         Button save = new Button("Save Changes");
//         save.getStyleClass().add("primary-dark-btn");
//         save.setOnAction(e -> System.out.println("Save Changes clicked"));

//         HBox row = new HBox(10, spacer, discard, save);
//         row.setAlignment(Pos.CENTER_RIGHT);
//         return row;
//     }
// }



package com.kurukshetra.view.family;

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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class SettingsPage {

    private static final String PRIMARY = "#CA6721";
    private static final String PRIMARY_DARK = "#782F16";
    private static final String PAGE_BG = "#FCF9F7";
    private static final String SURFACE = "#FFFFFF";
    private static final String VERY_LIGHT_TERRACOTTA = "#FDF0E8";
    private static final String LIGHT_TERRACOTTA = "#F9E4D6";
    private static final String TEXT_PRIMARY = "#2B211D";
    private static final String TEXT_SECONDARY = "#675B55";
    private static final String TEXT_MUTED = "#938780";
    private static final String BORDER_COLOR = "#EDE0D8";
    private static final String HOVER_BG = "#FFF5EF";

    public BorderPane setBorderPane(Stage stage) {
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: " + PAGE_BG + ";");

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.SETTINGS);
        bp.setLeft(sidebar);

        VBox mainContent = buildMainContent();
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: " + PAGE_BG + "; -fx-background-color: transparent; -fx-border-color: transparent;");
        bp.setCenter(scrollPane);

        MedicalReports.playPageAnimation(scrollPane);
        return bp;
    }

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
        mainContent.setStyle("-fx-background-color: " + PAGE_BG + ";");
        mainContent.setPadding(new Insets(24));
        return mainContent;
    }

    private HBox buildHeader() {
        Label title = new Label("System Settings");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        

        HBox header = new HBox(16, title, spacer);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private TextFlow buildSubtitle() {
        Text pre = new Text("Manage your ");
        pre.setFill(Color.web(TEXT_MUTED));
        pre.setFont(Font.font("System", 12));

        Text account = new Text("account");
        account.setFill(Color.web(PRIMARY));
        account.setFont(Font.font("System", FontWeight.BOLD, 12));

        Text mid1 = new Text(" preferences, ");
        mid1.setFill(Color.web(TEXT_MUTED));
        mid1.setFont(Font.font("System", 12));

        Text security = new Text("security");
        security.setFill(Color.web(PRIMARY_DARK));
        security.setFont(Font.font("System", FontWeight.BOLD, 12));

        Text mid2 = new Text(", and ");
        mid2.setFill(Color.web(TEXT_MUTED));
        mid2.setFont(Font.font("System", 12));

        Text application = new Text("application");
        application.setFill(Color.web(PRIMARY));
        application.setFont(Font.font("System", FontWeight.BOLD, 12));

        Text post = new Text(" experience.");
        post.setFill(Color.web(TEXT_MUTED));
        post.setFont(Font.font("System", 12));

        return new TextFlow(pre, account, mid1, security, mid2, application, post);
    }

    private VBox buildAccountProfileCard() {
        Label icon = new Label("👤");
        Label title = new Label("Account & Profile");
        title.setFont(Font.font("System", FontWeight.BOLD, 14));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        HBox header = new HBox(8, icon, title);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox fullNameBox = buildField("Full Name", "Sarah Miller");
        VBox emailBox = buildField("Email Address", "sarah.m@example.com");
        VBox phoneBox = buildField("Phone Number", "+1 (555) 123-4567");

        HBox.setHgrow(emailBox, Priority.ALWAYS);
        HBox.setHgrow(phoneBox, Priority.ALWAYS);
        HBox contactRow = new HBox(14, emailBox, phoneBox);

        Button updateBtn = new Button("Update Profile");
        updateBtn.setStyle("-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK + "; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 8px; -fx-padding: 8px 16px; -fx-cursor: hand;");
        updateBtn.setOnMouseEntered(e -> updateBtn.setStyle("-fx-background-color: " + LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK + "; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 8px; -fx-padding: 8px 16px; -fx-cursor: hand;"));
        updateBtn.setOnMouseExited(e -> updateBtn.setStyle("-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK + "; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 8px; -fx-padding: 8px 16px; -fx-cursor: hand;"));
        updateBtn.setOnAction(e -> System.out.println("Update Profile clicked"));

        VBox card = new VBox(16, header, fullNameBox, contactRow, updateBtn);
        applyCardStyle(card);
        return card;
    }

    private VBox buildField(String label, String value) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        TextField field = new TextField(value);
        field.setStyle("-fx-background-color: #FEF7F2; -fx-background-radius: 8px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-padding: 8px; -fx-font-size: 12px; -fx-text-fill: " + TEXT_PRIMARY + ";");
        field.setMaxWidth(Double.MAX_VALUE);

        return new VBox(6, lbl, field);
    }

    private VBox buildSecurityCard() {
        Label icon = new Label("🔒");
        Label title = new Label("Security");
        title.setFont(Font.font("System", FontWeight.BOLD, 14));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        HBox header = new HBox(8, icon, title);
        header.setAlignment(Pos.CENTER_LEFT);

        HBox passwordRow = buildSecurityRow("Password", "Last changed 3 months ago", null);
        Label changePassword = new Label("Change Password");
        changePassword.setStyle("-fx-font-size: 11px; -fx-text-fill: " + PRIMARY + "; -fx-cursor: hand; -fx-font-weight: bold;");
        passwordRow.getChildren().add(changePassword);

        ToggleButton twoFA = createToggle(true, "#23804F");
        HBox twoFARow = buildSecurityRow("Two-Factor Authentication", "Add an extra layer of security to your account", twoFA);

        VBox card = new VBox(18, header, passwordRow, twoFARow);
        applyCardStyle(card);
        return card;
    }

    private HBox buildSecurityRow(String title, String desc, ToggleButton toggle) {
        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label descLbl = new Label(desc);
        descLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");
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

    private VBox buildNotificationsCard() {
        Label title = new Label("🔔  Notifications");
        title.setFont(Font.font("System", FontWeight.BOLD, 14));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        HBox emergencyRow = buildToggleRow("Emergency Alerts", true, "#C94F4F");
        HBox hospitalRow = buildToggleRow("Hospital Updates", true, "#23804F");
        HBox appointmentRow = buildToggleRow("Appointment Reminders", true, "#23804F");
        HBox aiRow = buildToggleRow("AI Health Insights", false, "#23804F");

        VBox card = new VBox(16, title, emergencyRow, hospitalRow, appointmentRow, aiRow);
        applyCardStyle(card);
        return card;
    }

    private HBox buildToggleRow(String label, boolean on, String onColor) {
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

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
            toggle.setStyle("-fx-background-color: #C7CBD3; -fx-background-radius: 15; -fx-border-color: transparent;");
        }
    }

    private VBox buildAppearanceCard() {
        Label title = new Label("🎨  Appearance & Region");
        title.setFont(Font.font("System", FontWeight.BOLD, 14));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label themeLbl = new Label("Theme");
        themeLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        Button lightBtn = new Button("☀  Light");
        Button darkBtn = new Button("🌙  Dark");
        styleThemeBtn(lightBtn, true);
        styleThemeBtn(darkBtn, false);

        HBox.setHgrow(lightBtn, Priority.ALWAYS);
        HBox.setHgrow(darkBtn, Priority.ALWAYS);
        lightBtn.setMaxWidth(Double.MAX_VALUE);
        darkBtn.setMaxWidth(Double.MAX_VALUE);

        lightBtn.setOnAction(e -> {
            styleThemeBtn(lightBtn, true);
            styleThemeBtn(darkBtn, false);
        });
        darkBtn.setOnAction(e -> {
            styleThemeBtn(darkBtn, true);
            styleThemeBtn(lightBtn, false);
        });

        HBox themeRow = new HBox(8, lightBtn, darkBtn);

        Label langLbl = new Label("Language");
        langLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        ComboBox<String> langBox = new ComboBox<>();
        langBox.getItems().addAll("English (US)", "Spanish", "French");
        langBox.setValue("English (US)");
        langBox.setStyle("-fx-background-color: #FEF7F2; -fx-background-radius: 8px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-font-size: 12px;");
        langBox.setMaxWidth(Double.MAX_VALUE);

        Label tzLbl = new Label("Timezone");
        tzLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        ComboBox<String> tzBox = new ComboBox<>();
        tzBox.getItems().addAll("(GMT-05:00) Eastern Time", "(GMT-06:00) Central Time", "(GMT-08:00) Pacific Time");
        tzBox.setValue("(GMT-05:00) Eastern Time");
        tzBox.setStyle("-fx-background-color: #FEF7F2; -fx-background-radius: 8px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-font-size: 12px;");
        tzBox.setMaxWidth(Double.MAX_VALUE);

        VBox card = new VBox(12, title, themeLbl, themeRow, langLbl, langBox, tzLbl, tzBox);
        applyCardStyle(card);
        return card;
    }

    private void styleThemeBtn(Button btn, boolean active) {
        if (active) {
            btn.setStyle("-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-text-fill: " + PRIMARY_DARK + "; -fx-font-weight: bold; -fx-border-color: " + PRIMARY + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-padding: 8px 14px; -fx-cursor: hand;");
        } else {
            btn.setStyle("-fx-background-color: #FEF7F2; -fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 11px; -fx-background-radius: 8px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-padding: 8px 14px; -fx-cursor: hand;");
        }
    }

    private HBox buildActionsRow() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button discard = new Button("Discard");
        discard.setStyle("-fx-background-color: transparent; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 10px 18px; -fx-cursor: hand;");
        discard.setOnAction(e -> System.out.println("Discard clicked"));

        Button save = new Button("Save Changes");
        save.setStyle("-fx-background-color: " + PRIMARY + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-padding: 10px 18px; -fx-cursor: hand;");
        save.setOnMouseEntered(e -> save.setStyle("-fx-background-color: " + PRIMARY_DARK + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-padding: 10px 18px; -fx-cursor: hand;"));
        save.setOnMouseExited(e -> save.setStyle("-fx-background-color: " + PRIMARY + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-padding: 10px 18px; -fx-cursor: hand;"));
        save.setOnAction(e -> System.out.println("Save Changes clicked"));

        HBox row = new HBox(10, spacer, discard, save);
        row.setAlignment(Pos.CENTER_RIGHT);
        return row;
    }

    private void applyCardStyle(VBox card) {
        card.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 14px;" +
                "-fx-background-radius: 14px;" +
                "-fx-padding: 20px;"
        );
        card.setEffect(new DropShadow(10, 0, 3, Color.rgb(120, 47, 22, 0.05)));
    }

    private void styleUtilityIconButton(Label label) {
        label.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 18px;" +
                "-fx-background-radius: 18px;" +
                "-fx-min-width: 36px;" +
                "-fx-min-height: 36px;" +
                "-fx-alignment: center;" +
                "-fx-font-size: 14px;" +
                "-fx-cursor: hand;"
        );
    }
}