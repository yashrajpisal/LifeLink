// package com.kurukshetra.view.family;

// import javafx.geometry.Pos;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;

// public class Setting {

//     public VBox getSettingVBox(){

//         Text title = new Text("Setting");
//         title.setStyle("-fx-font-size: 18px; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-background-color: #e1f0f9; -fx-padding:10px 10px");

//         VBox vmain = new VBox(title);
//         vmain.setAlignment(Pos.CENTER);
//         return vmain;
//     }
// }

package com.kurukshetra.view.family;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Setting {

    public ScrollPane getSettingVBox(){

        Text headerTitle = new Text("System Settings");
        headerTitle.setStyle("-fx-font-size:20px; -fx-font-weight:bold; -fx-fill:#1e293b;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search settings...");
        searchField.setStyle("-fx-background-color:#e2e8f0; -fx-background-radius:20px; -fx-padding:8px 15px; -fx-pref-width:200px;");

        Button notifBtn = new Button("🔔");
        notifBtn.setStyle("-fx-background-color:transparent; -fx-cursor:hand;");

        Button profileBtn = new Button("👤");
        profileBtn.setStyle("-fx-background-color:transparent; -fx-cursor:hand;");

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        HBox topBar = new HBox(10, headerTitle, topSpacer, searchField, notifBtn, profileBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-padding:0 0 20px 0;");

        // Section 1: Account & Security
        Text sec1Title = new Text("🛡 Account & Security");
        sec1Title.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        VBox pResetText = new VBox(2, new Text("Password Reset"), createSubText("Last changed 3 months ago"));
        Button updatePwdBtn = new Button("Update Password");
        updatePwdBtn.setStyle("-fx-background-color:transparent; -fx-border-color:#2563eb; -fx-border-radius:6px; -fx-text-fill:#2563eb; -fx-padding:6px 12px; -fx-cursor:hand;");
        addHoverAnimation(updatePwdBtn);

        Region sp1 = new Region();
        HBox.setHgrow(sp1, Priority.ALWAYS);
        HBox row1 = new HBox(pResetText, sp1, updatePwdBtn);
        row1.setAlignment(Pos.CENTER_LEFT);

        VBox tfaText = new VBox(2, new Text("Two-Factor Authentication"), createSubText("Add an extra layer of security to your account"));
        Region sp2 = new Region();
        HBox.setHgrow(sp2, Priority.ALWAYS);
        StackPane toggle2fa = createAnimatedToggleSwitch(true);
        HBox row2 = new HBox(tfaText, sp2, toggle2fa);
        row2.setAlignment(Pos.CENTER_LEFT);

        VBox card1 = createCard(sec1Title, row1, row2);

        // Section 2: Notification Preferences
        Text sec2Title = new Text("🔔 Notification Preferences");
        sec2Title.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        HBox notif1 = createNotifRow("💬 SMS Notifications", false);
        HBox notif2 = createNotifRow("✉ Email Alerts", true);
        HBox notif3 = createNotifRow("🔔 Push Notifications", true);

        VBox card2 = createCard(sec2Title, notif1, notif2, notif3);

        // Section 3: Appearance
        Text sec3Title = new Text("🎨 Appearance");
        sec3Title.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        VBox lightModeBox = createThemeCard("Light Mode", true);
        VBox darkModeBox = createThemeCard("Dark Mode", false);
        HBox appearanceBox = new HBox(15, lightModeBox, darkModeBox);

        VBox card3 = createCard(sec3Title, appearanceBox);

        // Section 4: Privacy
        Text sec4Title = new Text("🛡 Privacy");
        sec4Title.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        VBox shareText = new VBox(2, new Text("Share History with Hospital"), createSubText("Allow authorized medical facilities to view your emergency history"));
        Region sp3 = new Region();
        HBox.setHgrow(sp3, Priority.ALWAYS);
        StackPane toggleShare = createAnimatedToggleSwitch(true);
        HBox row3 = new HBox(shareText, sp3, toggleShare);
        row3.setAlignment(Pos.CENTER_LEFT);

        Text contactLabel = new Text("Contact Permissions");
        ComboBox<String> contactCombo = new ComboBox<>();
        contactCombo.getItems().addAll("Allow all emergency contacts", "Allow primary only", "Deny all");
        contactCombo.getSelectionModel().selectFirst();
        contactCombo.setMaxWidth(Double.MAX_VALUE);
        contactCombo.setStyle("-fx-background-color:#f1f5f9; -fx-border-color:#cbd5e1; -fx-border-radius:6px;");

        Text contactSub = createSubText("Controls who can access your contact details during an active alert.");

        VBox card4 = createCard(sec4Title, row3, contactLabel, contactCombo, contactSub);

        // Bottom Actions
        Button resetBtn = new Button("Reset to Defaults");
        resetBtn.setStyle("-fx-background-color:transparent; -fx-text-fill:#475569; -fx-font-weight:bold; -fx-cursor:hand;");
        addHoverAnimation(resetBtn);

        Button saveBtn = new Button("Save Changes");
        saveBtn.setStyle("-fx-background-color:#1d4ed8; -fx-text-fill:white; -fx-padding:10px 20px; -fx-background-radius:6px; -fx-font-weight:bold; -fx-cursor:hand;");
        addHoverAnimation(saveBtn);

        Region botSpacer = new Region();
        HBox.setHgrow(botSpacer, Priority.ALWAYS);
        HBox actionBox = new HBox(15, botSpacer, resetBtn, saveBtn);
        actionBox.setAlignment(Pos.CENTER_RIGHT);
        actionBox.setStyle("-fx-padding:15px 0 0 0;");

        VBox mainContent = new VBox(20, topBar, card1, card2, card3, card4, actionBox);
        mainContent.setStyle("-fx-padding:20px; -fx-background-color:#f8fafc;");

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color:transparent; -fx-background:#f8fafc;");
        return scrollPane;
    }

    private Text createSubText(String text){
        Text t = new Text(text);
        t.setStyle("-fx-fill:#64748b; -fx-font-size:12px;");
        return t;
    }

    private StackPane createAnimatedToggleSwitch(boolean initialState){
        Rectangle bg = new Rectangle(44, 24);
        bg.setArcWidth(24);
        bg.setArcHeight(24);
        bg.setFill(initialState ? Color.web("#2563eb") : Color.web("#cbd5e1"));

        Circle trigger = new Circle(9);
        trigger.setFill(Color.WHITE);

        StackPane pane = new StackPane(bg, trigger);
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setPrefSize(44, 24);
        pane.setStyle("-fx-cursor:hand; -fx-padding:3px;");

        trigger.setTranslateX(initialState ? 20 : 0);

        final boolean[] state = new boolean[]{initialState};

        pane.setOnMouseClicked(e -> {
            state[0] = !state[0];
            TranslateTransition anim = new TranslateTransition(Duration.millis(200), trigger);
            anim.setToX(state[0] ? 20 : 0);
            anim.play();
            bg.setFill(state[0] ? Color.web("#2563eb") : Color.web("#cbd5e1"));
        });

        return pane;
    }

    private HBox createNotifRow(String labelText, boolean active){
        Text label = new Text(labelText);
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        StackPane toggle = createAnimatedToggleSwitch(active);
        HBox row = new HBox(label, sp, toggle);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color:#eff6ff; -fx-padding:12px; -fx-background-radius:8px;");
        return row;
    }

    private VBox createThemeCard(String modeName, boolean isSelected){
        VBox innerBox = new VBox();
        innerBox.setPrefSize(200, 80);
        innerBox.setStyle(isSelected ? "-fx-background-color:#ffffff; -fx-border-color:#cbd5e1; -fx-border-radius:6px;" : "-fx-background-color:#1e293b; -fx-border-radius:6px;");

        Text label = new Text(modeName);
        label.setStyle("-fx-font-weight:bold; -fx-fill:#334155;");

        VBox box = new VBox(8, innerBox, label);
        box.setAlignment(Pos.CENTER);
        box.setStyle(isSelected ? "-fx-border-color:#2563eb; -fx-border-width:2px; -fx-border-radius:8px; -fx-padding:10px; -fx-cursor:hand;" : "-fx-border-color:#e2e8f0; -fx-border-width:1px; -fx-border-radius:8px; -fx-padding:10px; -fx-cursor:hand;");
        addHoverAnimation(box);

        return box;
    }

    private void addHoverAnimation(javafx.scene.Node node){
        ScaleTransition stIn = new ScaleTransition(Duration.millis(150), node);
        stIn.setToX(1.03);
        stIn.setToY(1.03);

        ScaleTransition stOut = new ScaleTransition(Duration.millis(150), node);
        stOut.setToX(1.0);
        stOut.setToY(1.0);

        node.setOnMouseEntered(e -> stIn.playFromStart());
        node.setOnMouseExited(e -> stOut.playFromStart());
    }

    private VBox createCard(javafx.scene.Node... nodes){
        VBox card = new VBox(12, nodes);
        card.setStyle("-fx-background-color:#ffffff; -fx-border-color:#e2e8f0; -fx-border-radius:10px; -fx-background-radius:10px; -fx-padding:20px;");
        return card;
    }
}