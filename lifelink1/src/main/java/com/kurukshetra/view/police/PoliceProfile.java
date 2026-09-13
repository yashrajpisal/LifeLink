package com.kurukshetra.view.police;

import com.kurukshetra.controller.police.PoliceProfileController;
import com.kurukshetra.model.police.PoliceProfileModel;
import com.kurukshetra.view.util.ShimmerLoader;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class PoliceProfile {

    // =========================================================
    // MAIN CONTENT COLORS
    // =========================================================
    private static final String PAGE_BG                = "#F8F0EA";   // page background
    private static final String SURFACE                = "#FFFFFF";   // cards
    private static final String VERY_LIGHT_BEIGE       = "#FBF5EF";   // input field background
    private static final String BROWN_DARK             = "#7A4A32";   // primary buttons, headers
    private static final String PRIMARY_TEXT           = "#33261E";   // main text
    private static final String SEC_TEXT               = "#725D4E";   // secondary/label text
    private static final String BORDER                 = "#EBDCCF";   // card and input borders
    private static final String ACCENT_TERRACOTTA      = "#D85A30";   // hover states, links, live/priority badges
    private static final String ACCENT_TERRACOTTA_BG   = "#FAECE7";   // light bg for pending/priority badges
    private static final String ACCENT_TERRACOTTA_TEXT = "#993C1D";   // text on ACCENT_TERRACOTTA_BG
    private static final String SUCCESS_GREEN          = "#639922";   // "Completed"/"Cleared" badge text
    private static final String SUCCESS_GREEN_BG       = "#EAF3DE";   // "Completed"/"Cleared" badge background

    // =========================================================
    // SIDEBAR COLORS
    // =========================================================
    private static final String SIDEBAR_BG             = "#2B1D15";   // dark espresso-brown sidebar background
    private static final String SIDEBAR_TEXT           = "#E8DCD1";   // default nav item text (light warm gray, not pure white)
    private static final String SIDEBAR_TEXT_MUTED     = "#A6907E";   // section labels like "POLICE NAVIGATION", inactive icons
    private static final String SIDEBAR_ACTIVE_BG      = "#D85A30";   // active nav item background = ACCENT_TERRACOTTA
    private static final String SIDEBAR_ACTIVE_TEXT    = "#FFFFFF";   // text/icon on the active nav item
    private static final String SIDEBAR_HOVER_BG       = "#3D2A1F";   // subtle hover state on inactive nav items, one step lighter than SIDEBAR_BG
    private static final String SIDEBAR_BORDER         = "#3D2A1F";   // divider lines inside sidebar, if any
    private static final String SIDEBAR_SIGNOUT_BG     = "#4A241C";   // "Sign Out Shift" button background — dark red-brown
    private static final String SIDEBAR_SIGNOUT_TEXT   = "#F3B8A8";   // "Sign Out Shift" text color — light coral

    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, BlinkMacSystemFont, sans-serif; ";

    private static final String CARD_STYLE = "-fx-background-color: " + SURFACE
            + "; -fx-background-radius: 18px; -fx-border-color: " + BORDER
            + "; -fx-border-radius: 18px; -fx-effect: dropshadow(gaussian, rgba(51, 38, 30, 0.06), 16, 0.10, 0, 4);";

    private final PoliceProfileController controller = new PoliceProfileController();
    private final Map<String, EditableInfoRow> fieldMap = new HashMap<>();

    private Text heroStationName;
    private Text heroLocationSubtitle;

    public ScrollPane getProfileView() {
        fieldMap.clear();

        VBox profilePage = new VBox(24);
        profilePage.setPadding(new Insets(32, 44, 40, 44));
        profilePage.setStyle("-fx-background-color: " + PAGE_BG + ";");

        // 1. TOP HEADER WITH LIVE BEACON
        HBox badgePill = new HBox(6);
        badgePill.setAlignment(Pos.CENTER_LEFT);
        badgePill.setPadding(new Insets(4, 10, 4, 10));
        badgePill.setStyle("-fx-background-color: " + SUCCESS_GREEN_BG + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");

        Circle liveDot = new Circle(4, Color.web(SUCCESS_GREEN));
        FadeTransition pulse = new FadeTransition(Duration.millis(900), liveDot);
        pulse.setFromValue(1.0);
        pulse.setToValue(0.35);
        pulse.setCycleCount(FadeTransition.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();

        String effectiveEmail = PoliceProfileController.getEffectiveEmail();
        String displayUser = (effectiveEmail == null || effectiveEmail.isEmpty())
                ? "Control Room Profile"
                : "Control Room Profile (" + effectiveEmail + ")";

        Text heading = new Text(displayUser);
        heading.setStyle(FONT_FAMILY + "-fx-font-size: 28px; -fx-font-weight: 800; -fx-fill: " + PRIMARY_TEXT + ";");

        VBox headingTextBox = new VBox(5, heading);

        // Action Buttons with Spring Micro-animations
        Button editBtn = new Button("✎  Edit Profile");
        String editBase = FONT_FAMILY + "-fx-background-color: " + SURFACE + "; -fx-text-fill: " + BROWN_DARK + "; -fx-border-color: " + BROWN_DARK + "; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-font-size: 13.5px; -fx-font-weight: bold; -fx-padding: 9px 20px; -fx-cursor: hand;";
        String editHover = FONT_FAMILY + "-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-text-fill: " + ACCENT_TERRACOTTA + "; -fx-border-color: " + ACCENT_TERRACOTTA + "; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-font-size: 13.5px; -fx-font-weight: bold; -fx-padding: 9px 20px; -fx-cursor: hand;";
        addSpringButtonAnimation(editBtn, editBase, editHover, new DropShadow(8, 0, 2, Color.rgb(51, 38, 30, 0.08)));

        Button saveBtn = new Button("💾  Save Profile");
        String saveBase = FONT_FAMILY + "-fx-background-color: " + BROWN_DARK + "; -fx-text-fill: #FFFFFF; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-font-size: 13.5px; -fx-font-weight: bold; -fx-padding: 9px 22px; -fx-cursor: hand;";
        String saveHover = FONT_FAMILY + "-fx-background-color: " + ACCENT_TERRACOTTA + "; -fx-text-fill: #FFFFFF; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-font-size: 13.5px; -fx-font-weight: bold; -fx-padding: 9px 22px; -fx-cursor: hand;";
        addSpringButtonAnimation(saveBtn, saveBase, saveHover, new DropShadow(10, 0, 3, Color.rgb(122, 74, 50, 0.28)));
        saveBtn.setVisible(false);
        saveBtn.setManaged(false);

        editBtn.setOnAction(e -> {
            toggleEditMode(true);
            editBtn.setVisible(false);
            editBtn.setManaged(false);
            saveBtn.setVisible(true);
            saveBtn.setManaged(true);
        });

        saveBtn.setOnAction(e -> {
            saveBtn.setText("Saving...");
            saveBtn.setDisable(true);

            String activeEmail = PoliceProfileController.getEffectiveEmail();
            PoliceProfileModel model = new PoliceProfileModel(
                    activeEmail,
                    getFieldValue("Control Room", "Pune City Emergency Control Room"),
                    getFieldValue("Location", "Pune Police Commissionerate, Shivajinagar, Pune"),
                    getFieldValue("Police District", "Pune City"),
                    getFieldValue("Response Zone", "Pune Central"),
                    getFieldValue("Officer", "Control Room In-charge"),
                    getFieldValue("Role", "Emergency Coordination Officer"),
                    getFieldValue("Contact", "Police Control Room Emergency Line"),
                    getFieldValue("Shift", "08:00 AM – 04:00 PM")
            );

            controller.saveProfile(model, success -> {
                saveBtn.setDisable(false);
                if (Boolean.TRUE.equals(success)) {
                    saveBtn.setText("💾  Save Profile");
                    toggleEditMode(false);
                    saveBtn.setVisible(false);
                    saveBtn.setManaged(false);
                    editBtn.setVisible(true);
                    editBtn.setManaged(true);

                    if (heroStationName != null) {
                        heroStationName.setText(model.getControlRoom());
                    }
                    if (heroLocationSubtitle != null) {
                        heroLocationSubtitle.setText("📍 " + model.getLocation()
                                + " • Zone: " + model.getResponseZone());
                    }
                } else {
                    saveBtn.setText("⚠ Error. Retry Save");
                }
            });
        });

        HBox actionBtnBox = new HBox(12, editBtn, saveBtn);
        actionBtnBox.setAlignment(Pos.CENTER_RIGHT);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox headerRow = new HBox(headingTextBox, headerSpacer, actionBtnBox);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        // 2. HERO STATION OVERVIEW CARD
        HBox heroCard = new HBox(20);
        heroCard.setAlignment(Pos.CENTER_LEFT);
        heroCard.setPadding(new Insets(20, 24, 20, 24));
        heroCard.setStyle(CARD_STYLE);

        StackPane emblemBox = new StackPane();
        emblemBox.setPrefSize(56, 56);
        emblemBox.setMinSize(56, 56);
        emblemBox.setMaxSize(56, 56);
        emblemBox.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 14px; -fx-border-color: " + BORDER + "; -fx-border-radius: 14px;");
        Label shieldEmblem = new Label("🛡");
        shieldEmblem.setStyle("-fx-font-size: 26px;");
        emblemBox.getChildren().add(shieldEmblem);

        VBox heroInfo = new VBox(4);
        HBox stationTitleRow = new HBox(10);
        stationTitleRow.setAlignment(Pos.CENTER_LEFT);
        heroStationName = new Text("Pune City Emergency Control Room");
        heroStationName.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");

       stationTitleRow.getChildren().addAll(heroStationName);

        heroLocationSubtitle = new Text("📍 Pune Police Commissionerate, Shivajinagar, Pune • Zone: Pune Central");
        heroLocationSubtitle.setStyle(FONT_FAMILY + "-fx-font-size: 12.5px; -fx-fill: " + SEC_TEXT + ";");
        heroInfo.getChildren().addAll(stationTitleRow, heroLocationSubtitle);

        Region heroSpacer = new Region();
        HBox.setHgrow(heroSpacer, Priority.ALWAYS);

        HBox statusPill = new HBox(6);
        statusPill.setAlignment(Pos.CENTER);
        statusPill.setPadding(new Insets(6, 14, 6, 14));
        statusPill.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 20px; -fx-border-color: " + BORDER + "; -fx-border-radius: 20px;");
        Text statusText = new Text("● 24/7 INTERLOCK ACTIVE");
        statusText.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: bold; -fx-fill: " + BROWN_DARK + ";");
        statusPill.getChildren().add(statusText);

        heroCard.getChildren().addAll(emblemBox, heroInfo, heroSpacer, statusPill);

        // 3. CONTROL ROOM SPECIFICATIONS CARD
        VBox controlRoomCard = createCard();
        HBox controlRoomTitle = createSectionTitle("🏢", "Control Room & Facility Specifications", "Jurisdiction and regional emergency communications node");

        HBox roomNameRow = createInfoRow("🏢", "Control Room", "Pune City Emergency Control Room");
        HBox locationRow = createInfoRow("📍", "Location", "Pune Police Commissionerate, Shivajinagar, Pune");
        HBox districtRow = createInfoRow("🌆", "Police District", "Pune City");
        HBox responseZoneRow = createInfoRow("📡", "Response Zone", "Pune Central");

        controlRoomCard.getChildren().addAll(controlRoomTitle, roomNameRow, locationRow, districtRow, responseZoneRow);

        // 4. OFFICER & ESCALATION HIERARCHY CARD
        VBox officerCard = createCard();
        HBox officerTitle = createSectionTitle("👮", "Duty Officer & Escalation Hierarchy", "Active supervisor credentials and contact routing");

        HBox officerNameRow = createInfoRow("👤", "Officer", "Control Room In-charge");
        HBox officerRoleRow = createInfoRow("💼", "Role", "Emergency Coordination Officer");
        HBox contactRow = createInfoRow("📞", "Contact", "Police Control Room Emergency Line");
        HBox shiftRow = createInfoRow("🕒", "Shift", "08:00 AM – 04:00 PM");

        officerCard.getChildren().addAll(officerTitle, officerNameRow, officerRoleRow, contactRow, shiftRow);

        VBox cardsContainer = new VBox(22, heroCard, controlRoomCard, officerCard);
        cardsContainer.setOpacity(0);

        ShimmerLoader.ShimmerPane profileShimmer = ShimmerLoader.createPoliceProfileSkeleton(850);
        profileShimmer.setMaxWidth(Double.MAX_VALUE);

        StackPane profileSlot = new StackPane(cardsContainer, profileShimmer);
        profileSlot.setAlignment(Pos.TOP_LEFT);

        profilePage.getChildren().addAll(headerRow, profileSlot);

        // Fetch data AFTER fieldMap is populated
        controller.loadProfile(data -> {
            if (data.getControlRoom() != null) setFieldValue("Control Room", data.getControlRoom());
            if (data.getLocation() != null) setFieldValue("Location", data.getLocation());
            if (data.getPoliceDistrict() != null) setFieldValue("Police District", data.getPoliceDistrict());
            if (data.getResponseZone() != null) setFieldValue("Response Zone", data.getResponseZone());
            if (data.getOfficer() != null) setFieldValue("Officer", data.getOfficer());
            if (data.getRole() != null) setFieldValue("Role", data.getRole());
            if (data.getContact() != null) setFieldValue("Contact", data.getContact());
            if (data.getShift() != null) setFieldValue("Shift", data.getShift());

            if (heroStationName != null && data.getControlRoom() != null) {
                heroStationName.setText(data.getControlRoom());
            }
            if (heroLocationSubtitle != null && data.getLocation() != null) {
                heroLocationSubtitle.setText("📍 " + data.getLocation()
                        + (data.getResponseZone() != null ? " • Zone: " + data.getResponseZone() : ""));
            }

            revealLoadedProfile(profileSlot, profileShimmer, cardsContainer);
        }, () -> {
            // First time initialization
            String activeEmail = PoliceProfileController.getEffectiveEmail();
            PoliceProfileModel defaultModel = new PoliceProfileModel(
                    activeEmail,
                    "Pune City Emergency Control Room",
                    "Pune Police Commissionerate, Shivajinagar, Pune",
                    "Pune City",
                    "Pune Central",
                    "Control Room In-charge",
                    "Emergency Coordination Officer",
                    "Police Control Room Emergency Line",
                    "08:00 AM – 04:00 PM"
            );
            controller.saveProfile(defaultModel, s -> {
                revealLoadedProfile(profileSlot, profileShimmer, cardsContainer);
            });
        });

        playEntranceAnimation(profilePage);

        ScrollPane scrollPane = new ScrollPane(profilePage);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");
        PoliceDashboard.applyHiddenScrollbars(scrollPane);

        return scrollPane;
    }

    private void revealLoadedProfile(StackPane slot, ShimmerLoader.ShimmerPane shimmer, VBox cards) {
        if (shimmer != null) {
            shimmer.stop();
            FadeTransition fo = new FadeTransition(Duration.millis(300), shimmer);
            fo.setFromValue(1.0);
            fo.setToValue(0.0);
            fo.setOnFinished(e -> slot.getChildren().remove(shimmer));
            fo.play();
        }
        FadeTransition fi = new FadeTransition(Duration.millis(350), cards);
        fi.setFromValue(0.0);
        fi.setToValue(1.0);
        fi.play();
    }

    private String getFieldValue(String key, String fallback) {
        EditableInfoRow row = fieldMap.get(key);
        return (row != null) ? row.getValue() : fallback;
    }

    private void setFieldValue(String key, String value) {
        EditableInfoRow row = fieldMap.get(key);
        if (row != null && value != null) {
            row.setValue(value);
        }
    }

    private VBox createCard() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(26));
        card.setStyle(CARD_STYLE);
        return card;
    }

    private HBox createSectionTitle(String iconStr, String titleStr, String subtitleStr) {
        HBox container = new HBox(14);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(0, 0, 8, 0));

        StackPane iconBadge = new StackPane();
        iconBadge.setPrefSize(40, 40);
        iconBadge.setMinSize(40, 40);
        iconBadge.setMaxSize(40, 40);
        iconBadge.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 10px; -fx-border-color: " + BORDER + "; -fx-border-radius: 10px;");

        Label iconLabel = new Label(iconStr);
        iconLabel.setStyle("-fx-font-size: 18px;");
        iconBadge.getChildren().add(iconLabel);

        VBox textBox = new VBox(2);
        Text title = new Text(titleStr);
        title.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + PRIMARY_TEXT + ";");
        Text subtitle = new Text(subtitleStr);
        subtitle.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-fill: " + SEC_TEXT + ";");
        textBox.getChildren().addAll(title, subtitle);

        container.getChildren().addAll(iconBadge, textBox);
        return container;
    }

    private void toggleEditMode(boolean isEditing) {
        for (EditableInfoRow row : fieldMap.values()) {
            if (row != null) row.setEditing(isEditing);
        }
    }

    private HBox createInfoRow(String icon, String title, String initialValue) {
        HBox row = new HBox(16);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 16, 12, 16));
        row.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");

        Label rowIcon = new Label(icon);
        rowIcon.setStyle("-fx-font-size: 15px;");

        Text titleText = new Text(title);
        titleText.setStyle(FONT_FAMILY + "-fx-font-size: 13.5px; -fx-font-weight: bold; -fx-fill: " + SEC_TEXT + ";");

        HBox leftCol = new HBox(10, rowIcon, titleText);
        leftCol.setAlignment(Pos.CENTER_LEFT);
        leftCol.setPrefWidth(210);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Text valueText = new Text(initialValue);
        valueText.setStyle(FONT_FAMILY + "-fx-font-size: 14px; -fx-font-weight: 600; -fx-fill: " + PRIMARY_TEXT + ";");

        TextField valueField = new TextField(initialValue);
        valueField.setStyle(FONT_FAMILY + "-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER
                + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: " + PRIMARY_TEXT
                + "; -fx-font-size: 13.5px; -fx-padding: 8px 14px;");
        valueField.setPrefWidth(420);
        valueField.setVisible(false);
        valueField.setManaged(false);

        valueField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                valueField.setStyle(FONT_FAMILY + "-fx-background-color: " + SURFACE + "; -fx-border-color: " + ACCENT_TERRACOTTA
                        + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: " + PRIMARY_TEXT
                        + "; -fx-font-size: 13.5px; -fx-padding: 8px 14px; -fx-effect: dropshadow(gaussian, rgba(216, 90, 48, 0.20), 8, 0.1, 0, 0);");
            } else {
                valueField.setStyle(FONT_FAMILY + "-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER
                        + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-text-fill: " + PRIMARY_TEXT
                        + "; -fx-font-size: 13.5px; -fx-padding: 8px 14px;");
            }
        });

        // Tactile hover micro-animation when not in edit mode
        row.setOnMouseEntered(e -> {
            if (!valueField.isVisible()) {
                row.setStyle("-fx-background-color: #F5EAE1; -fx-background-radius: 12px; -fx-border-color: " + BROWN_DARK + "; -fx-border-radius: 12px;");
                TranslateTransition tt = new TranslateTransition(Duration.millis(120), row);
                tt.setToX(4);
                tt.play();
            }
        });
        row.setOnMouseExited(e -> {
            if (!valueField.isVisible()) {
                row.setStyle("-fx-background-color: " + VERY_LIGHT_BEIGE + "; -fx-background-radius: 12px; -fx-border-color: " + BORDER + "; -fx-border-radius: 12px;");
                TranslateTransition tt = new TranslateTransition(Duration.millis(120), row);
                tt.setToX(0);
                tt.play();
            }
        });

        StackPane displayStack = new StackPane(valueText, valueField);
        displayStack.setAlignment(Pos.CENTER_RIGHT);

        row.getChildren().addAll(leftCol, spacer, displayStack);

        EditableInfoRow infoRow = new EditableInfoRow(valueText, valueField);
        fieldMap.put(title, infoRow);

        return row;
    }

    private void addSpringButtonAnimation(Button btn, String baseStyle, String hoverStyle, DropShadow shadow) {
        btn.setEffect(shadow);
        btn.setStyle(baseStyle);
        btn.setOnMouseEntered(e -> {
            btn.setStyle(hoverStyle);
            ScaleTransition st = new ScaleTransition(Duration.millis(140), btn);
            st.setToX(1.03);
            st.setToY(1.03);
            st.play();
        });
        btn.setOnMouseExited(e -> {
            btn.setStyle(baseStyle);
            ScaleTransition st = new ScaleTransition(Duration.millis(140), btn);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
        btn.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(80), btn);
            st.setToX(0.96);
            st.setToY(0.96);
            st.play();
        });
        btn.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(120), btn);
            st.setToX(1.03);
            st.setToY(1.03);
            st.play();
        });
    }

    private void playEntranceAnimation(VBox profilePage) {
        int delay = 0;
        for (Node child : profilePage.getChildren()) {
            child.setOpacity(0);
            child.setTranslateY(20);

            FadeTransition fade = new FadeTransition(Duration.millis(400), child);
            fade.setToValue(1.0);

            TranslateTransition translate = new TranslateTransition(Duration.millis(400), child);
            translate.setToY(0);

            ParallelTransition animation = new ParallelTransition(fade, translate);
            animation.setDelay(Duration.millis(delay));
            animation.play();

            delay += 90;
        }
    }

    private static class EditableInfoRow {
        private final Text textNode;
        private final TextField fieldNode;

        public EditableInfoRow(Text textNode, TextField fieldNode) {
            this.textNode = textNode;
            this.fieldNode = fieldNode;
        }

        public String getValue() {
            return fieldNode.getText().trim();
        }

        public void setValue(String val) {
            textNode.setText(val);
            fieldNode.setText(val);
        }

        public void setEditing(boolean editing) {
            if (editing) {
                fieldNode.setText(textNode.getText());
                textNode.setVisible(false);
                textNode.setManaged(false);
                fieldNode.setVisible(true);
                fieldNode.setManaged(true);
            } else {
                textNode.setText(fieldNode.getText());
                fieldNode.setVisible(false);
                fieldNode.setManaged(false);
                textNode.setVisible(true);
                textNode.setManaged(true);
            }
        }
    }
}