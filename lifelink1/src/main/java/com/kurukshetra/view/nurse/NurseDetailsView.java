package com.kurukshetra.view.nurse;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Nurse Professional Information — Step 1 of 1
 * Pure JavaFX onboarding UI. No FXML, no external CSS, no backend.
 */
public class NurseDetailsView extends Application {

    // ── Brand Palette ──────────────────────────────────────────────
    private static final String PRIMARY_BLUE       = "#1A56DB";
    private static final String PRIMARY_BLUE_HOVER = "#1E40AF";
    private static final String PRIMARY_BLUE_LIGHT = "#EBF0FF";
    private static final String DARK_NAVY          = "#111827";
    private static final String CHARCOAL           = "#1F2937";
    private static final String GRAY_500           = "#6B7280";
    private static final String GRAY_400           = "#9CA3AF";
    private static final String GRAY_300           = "#D1D5DB";
    private static final String GRAY_200           = "#E5E7EB";
    private static final String PAGE_BG            = "#FFFFFF";
    private static final String CARD_BG            = "#F8FAFC";
    private static final String WHITE              = "#FFFFFF";
    private static final String RED_500            = "#EF4444";
    private static final String INFO_BLUE_BG       = "#EFF6FF";
    private static final String INFO_BLUE_BORDER   = "#BFDBFE";
    private static final String INFO_BLUE_TEXT      = "#1E40AF";
    private static final String SHIELD_GREEN_BG    = "#F0FDF4";
    private static final String SHIELD_GREEN_BORDER = "#BBF7D0";
    private static final String SHIELD_GREEN_TEXT   = "#166534";

    private static final String FONT_FAMILY = "Segoe UI, Roboto, Arial, sans-serif";

    // ────────────────────────────────────────────────────────────────
    @Override
    public void start(Stage stage) {

        // ── Root ScrollPane wrapping everything ──
        VBox pageRoot = new VBox();
        pageRoot.setAlignment(Pos.TOP_CENTER);
        pageRoot.setStyle("-fx-background-color: " + PAGE_BG + ";");
        pageRoot.setPadding(new Insets(0, 0, 60, 0));

        ScrollPane scrollPane = new ScrollPane(pageRoot);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: " + PAGE_BG + "; -fx-background: " + PAGE_BG + ";");

        // ── Main content container (responsive width) ──
        VBox contentContainer = new VBox(0);
        contentContainer.setAlignment(Pos.TOP_CENTER);
        contentContainer.setMaxWidth(980);
        contentContainer.setPadding(new Insets(32, 32, 0, 32));

        // ── Page header ──
        VBox pageHeader = buildPageHeader();

        // ── Section 1: Personal & Contact Information ──
        VBox section1 = buildPersonalContactSection();

        // ── Section 2: Professional Information ──
        VBox section2 = buildProfessionalSection();

        // ── Action area ──
        VBox actionArea = buildActionArea();

        contentContainer.getChildren().addAll(
                pageHeader,
                section1,
                section2,
                actionArea
        );
        VBox.setMargin(pageHeader, new Insets(0, 0, 24, 0));
        VBox.setMargin(section1, new Insets(0, 0, 20, 0));
        VBox.setMargin(section2, new Insets(0, 0, 20, 0));

        pageRoot.getChildren().addAll(contentContainer);

        // ── Scene ──
        Scene scene = new Scene(scrollPane, 1100, 820);
        scene.setFill(Color.web(PAGE_BG));

        stage.setTitle("LifeLink — Nurse Registration");
        stage.setMinWidth(520);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }



    // ================================================================
    //  PAGE HEADER
    // ================================================================
    private VBox buildPageHeader() {
        VBox box = new VBox(6);
        box.setPadding(new Insets(20, 0, 0, 0));

        HBox titleRow = new HBox(12);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        StackPane idIcon = createRoundedIcon("\uD83E\uDEAA", PRIMARY_BLUE, PRIMARY_BLUE_LIGHT, 40);

        Label title = new Label("Professional Information");
        title.setStyle(
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 24px;" +
                "-fx-font-weight: 800;" +
                "-fx-text-fill: " + DARK_NAVY + ";"
        );

        titleRow.getChildren().addAll(idIcon, title);

        Label subtitle = new Label("Enter your professional and contact details");
        subtitle.setStyle(
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 14px;" +
                "-fx-text-fill: " + GRAY_500 + ";"
        );
        subtitle.setPadding(new Insets(0, 0, 0, 52));

        box.getChildren().addAll(titleRow, subtitle);
        return box;
    }

    // ================================================================
    //  SECTION 1 — PERSONAL & CONTACT INFORMATION
    // ================================================================
    private VBox buildPersonalContactSection() {
        VBox card = createCard();

        HBox headingRow = new HBox(10);
        headingRow.setAlignment(Pos.CENTER_LEFT);
        Label icon = new Label("\uD83D\uDC64");
        icon.setStyle("-fx-font-size: 16px;");
        Label heading = createSectionHeading("Personal & Contact Information");
        headingRow.getChildren().addAll(icon, heading);

        Region sep = createSeparator();

        Label helper = new Label("Provide your basic contact information.");
        helper.setStyle(
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 13px;" +
                "-fx-text-fill: " + GRAY_500 + ";"
        );
        helper.setPadding(new Insets(0, 0, 12, 0));

        // ── Full Name (full width) ──
        VBox fullNameField = createLabeledField("Full Name", true);
        TextField fullNameInput = createStyledTextField("Jane Doe, RN");
        HBox fullNameRow = wrapWithIcon("\uD83D\uDC64", fullNameInput);
        fullNameField.getChildren().add(fullNameRow);

        // ── Phone Number + Emergency Contact Number ──
        GridPane row2 = new GridPane();
        row2.setHgap(20);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(48);
        ColumnConstraints colGap = new ColumnConstraints();
        colGap.setPercentWidth(4);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(48);
        row2.getColumnConstraints().addAll(col1, colGap, col2);

        VBox phoneField = createLabeledField("Phone Number", true);
        TextField phoneInput = createStyledTextField("(555) 123-4567");
        HBox phoneRow = wrapWithIcon("\uD83D\uDCDE", phoneInput);
        phoneField.getChildren().add(phoneRow);

        VBox emergencyField = createLabeledField("Emergency Contact Number", false);
        TextField emergencyInput = createStyledTextField("(555) 987-6543");
        HBox emergencyRow = wrapWithIcon("\u2731", emergencyInput, RED_500);
        emergencyField.getChildren().add(emergencyRow);

        Label emergHelper = new Label("Used for emergency communication");
        emergHelper.setStyle(
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 11px;" +
                "-fx-text-fill: " + GRAY_400 + ";"
        );
        emergHelper.setPadding(new Insets(4, 0, 0, 0));
        emergencyField.getChildren().add(emergHelper);

        row2.add(phoneField, 0, 0);
        row2.add(emergencyField, 2, 0);

        card.getChildren().addAll(headingRow, sep, helper, fullNameField, row2);
        VBox.setMargin(fullNameField, new Insets(0, 0, 16, 0));
        return card;
    }

    // ================================================================
    //  SECTION 2 — PROFESSIONAL INFORMATION
    // ================================================================
    private VBox buildProfessionalSection() {
        VBox card = createCard();

        HBox headingRow = new HBox(10);
        headingRow.setAlignment(Pos.CENTER_LEFT);
        Label heading = createSectionHeading("Professional Information");
        headingRow.getChildren().addAll(heading);

        Region sep = createSeparator();

        // ── Nursing License / Registration Number ──
        VBox licenseField = createLabeledField("Nursing License / Registration Number", true);
        TextField licenseInput = createStyledTextField("RN-12345678");
        HBox licenseRow = wrapWithIcon("\uD83E\uDEAA", licenseInput);
        licenseField.getChildren().add(licenseRow);
        Label licenseHelper = new Label("Enter your valid nursing registration number.");
        licenseHelper.setStyle(
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 11px;" +
                "-fx-text-fill: " + GRAY_400 + ";"
        );
        licenseHelper.setPadding(new Insets(4, 0, 0, 0));
        licenseField.getChildren().add(licenseHelper);

        // ── Hospital Name ──
        VBox hospitalField = createLabeledField("Hospital Name", true);
        TextField hospitalInput = createStyledTextField("e.g. Metro General Hospital");
        HBox hospitalRow = wrapWithIcon("\uD83C\uDFE5", hospitalInput);
        hospitalField.getChildren().add(hospitalRow);

        // ── Department + Shift ──
        GridPane row3 = new GridPane();
        row3.setHgap(20);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(48);
        ColumnConstraints cGap = new ColumnConstraints();
        cGap.setPercentWidth(4);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(48);
        row3.getColumnConstraints().addAll(c1, cGap, c2);

        VBox departmentField = createLabeledField("Department", true);
        ComboBox<String> deptCombo = createStyledComboBox(
                "Select Department",
                "Emergency", "ICU", "Cardiology", "Pediatrics", "General Medicine", "Surgery"
        );
        HBox deptRow = wrapComboWithIcon("\uD83D\uDCCB", deptCombo);
        departmentField.getChildren().add(deptRow);

        VBox shiftField = createLabeledField("Shift", true);
        ComboBox<String> shiftCombo = createStyledComboBox(
                "Select Shift",
                "Morning", "Evening", "Night"
        );
        HBox shiftRow = wrapComboWithIcon("\uD83D\uDD50", shiftCombo);
        shiftField.getChildren().add(shiftRow);

        row3.add(departmentField, 0, 0);
        row3.add(shiftField, 2, 0);

        card.getChildren().addAll(
                headingRow, sep,
                licenseField, hospitalField, row3
        );
        VBox.setMargin(licenseField, new Insets(0, 0, 16, 0));
        VBox.setMargin(hospitalField, new Insets(0, 0, 16, 0));
        VBox.setMargin(row3, new Insets(0, 0, 20, 0));

        return card;
    }

    // ================================================================
    //  INFO BOX
    // ================================================================
    private VBox buildInfoBox(String icon, String title, String message,
                              String bgColor, String borderColor, String textColor) {
        VBox box = new VBox(4);
        box.setPadding(new Insets(14, 16, 14, 16));
        box.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                "-fx-border-color: " + borderColor + ";" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;"
        );

        HBox titleRow = new HBox(8);
        titleRow.setAlignment(Pos.CENTER_LEFT);
        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 15px;");
        Label titleLabel = new Label(title);
        titleLabel.setStyle(
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: 700;" +
                "-fx-text-fill: " + textColor + ";"
        );
        titleRow.getChildren().addAll(iconLabel, titleLabel);

        Label msg = new Label(message);
        msg.setWrapText(true);
        msg.setStyle(
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: " + textColor + ";"
        );
        msg.setPadding(new Insets(0, 0, 0, 26));

        box.getChildren().addAll(titleRow, msg);
        return box;
    }

    // ================================================================
    //  ACTION AREA
    // ================================================================
    private VBox buildActionArea() {
        VBox box = new VBox(14);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10, 0, 0, 0));

        Button createBtn = new Button("Create Account  \u2192");
        createBtn.setPrefWidth(180);
        createBtn.setPrefHeight(46);
        createBtn.setCursor(Cursor.HAND);
        String btnDefault =
                "-fx-background-color: " + PRIMARY_BLUE + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: 700;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-cursor: hand;";
        String btnHover =
                "-fx-background-color: " + PRIMARY_BLUE_HOVER + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: 700;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(26,86,219,0.25), 8, 0, 0, 2);";
        createBtn.setStyle(btnDefault);
        createBtn.setOnMouseEntered(e -> createBtn.setStyle(btnHover));
        createBtn.setOnMouseExited(e -> createBtn.setStyle(btnDefault));

        box.getChildren().addAll(createBtn);
        return box;
    }

    // ================================================================
    //  UTILITY — Card container
    // ================================================================
    private VBox createCard() {
        VBox card = new VBox(8);
        card.setPadding(new Insets(28, 32, 28, 32));
        card.setStyle(
                "-fx-background-color: " + CARD_BG + ";" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: " + GRAY_200 + ";" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 10, 0, 0, 2);"
        );
        return card;
    }

    // ================================================================
    //  UTILITY — Section heading
    // ================================================================
    private Label createSectionHeading(String text) {
        Label label = new Label(text);
        label.setStyle(
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 17px;" +
                "-fx-font-weight: 800;" +
                "-fx-text-fill: " + CHARCOAL + ";"
        );
        return label;
    }

    // ================================================================
    //  UTILITY — Separator
    // ================================================================
    private Region createSeparator() {
        Region sep = new Region();
        sep.setPrefHeight(1);
        sep.setMaxHeight(1);
        sep.setStyle("-fx-background-color: " + GRAY_200 + ";");
        VBox.setMargin(sep, new Insets(8, 0, 10, 0));
        return sep;
    }

    // ================================================================
    //  UTILITY — Labeled field
    // ================================================================
    private VBox createLabeledField(String labelText, boolean required) {
        VBox field = new VBox(6);

        HBox labelRow = new HBox(3);
        labelRow.setAlignment(Pos.CENTER_LEFT);

        Label lbl = new Label(labelText);
        lbl.setStyle(
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: 700;" +
                "-fx-text-fill: " + CHARCOAL + ";"
        );
        labelRow.getChildren().add(lbl);

        if (required) {
            Label ast = new Label(" *");
            ast.setStyle(
                    "-fx-font-size: 14px;" +
                    "-fx-font-weight: 700;" +
                    "-fx-text-fill: " + RED_500 + ";"
            );
            labelRow.getChildren().add(ast);
        }

        field.getChildren().add(labelRow);
        return field;
    }

    // ================================================================
    //  UTILITY — Styled TextField
    // ================================================================
    private TextField createStyledTextField(String placeholder) {
        TextField tf = new TextField();
        tf.setPromptText(placeholder);
        tf.setPrefHeight(46);
        tf.setMinHeight(46);
        String defaultStyle =
                "-fx-background-color: " + WHITE + ";" +
                "-fx-border-color: " + GRAY_300 + ";" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 0 14 0 38;" +
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 14px;" +
                "-fx-text-fill: " + DARK_NAVY + ";" +
                "-fx-prompt-text-fill: " + GRAY_400 + ";";
        String focusStyle =
                "-fx-background-color: " + WHITE + ";" +
                "-fx-border-color: " + PRIMARY_BLUE + ";" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 0 14 0 38;" +
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 14px;" +
                "-fx-text-fill: " + DARK_NAVY + ";" +
                "-fx-prompt-text-fill: " + GRAY_400 + ";" +
                "-fx-effect: dropshadow(gaussian, rgba(26,86,219,0.12), 6, 0, 0, 0);";
        String hoverStyle =
                "-fx-background-color: " + WHITE + ";" +
                "-fx-border-color: " + GRAY_400 + ";" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 0 14 0 38;" +
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 14px;" +
                "-fx-text-fill: " + DARK_NAVY + ";" +
                "-fx-prompt-text-fill: " + GRAY_400 + ";";

        tf.setStyle(defaultStyle);
        tf.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused) {
                tf.setStyle(focusStyle);
            } else {
                tf.setStyle(defaultStyle);
            }
        });
        tf.setOnMouseEntered(e -> {
            if (!tf.isFocused()) tf.setStyle(hoverStyle);
        });
        tf.setOnMouseExited(e -> {
            if (!tf.isFocused()) tf.setStyle(defaultStyle);
        });
        HBox.setHgrow(tf, Priority.ALWAYS);
        return tf;
    }

    // ================================================================
    //  UTILITY — Styled ComboBox
    // ================================================================
    private ComboBox<String> createStyledComboBox(String prompt, String... items) {
        ComboBox<String> cb = new ComboBox<>();
        cb.getItems().addAll(items);
        cb.setPromptText(prompt);
        cb.setPrefHeight(46);
        cb.setMinHeight(46);
        cb.setMaxWidth(Double.MAX_VALUE);
        String cbStyle =
                "-fx-background-color: " + WHITE + ";" +
                "-fx-border-color: " + GRAY_300 + ";" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 0 8 0 32;" +
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 14px;";
        String cbFocusStyle =
                "-fx-background-color: " + WHITE + ";" +
                "-fx-border-color: " + PRIMARY_BLUE + ";" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 0 8 0 32;" +
                "-fx-font-family: '" + FONT_FAMILY + "';" +
                "-fx-font-size: 14px;" +
                "-fx-effect: dropshadow(gaussian, rgba(26,86,219,0.12), 6, 0, 0, 0);";
        cb.setStyle(cbStyle);
        cb.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            cb.setStyle(isFocused ? cbFocusStyle : cbStyle);
        });
        HBox.setHgrow(cb, Priority.ALWAYS);
        return cb;
    }

    // ================================================================
    //  UTILITY — Wrap TextField with leading icon
    // ================================================================
    private HBox wrapWithIcon(String iconText, TextField tf) {
        return wrapWithIcon(iconText, tf, GRAY_400);
    }

    private HBox wrapWithIcon(String iconText, TextField tf, String iconColor) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label(iconText);
        icon.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-text-fill: " + iconColor + ";" +
                "-fx-padding: 0 0 0 12;"
        );
        icon.setMouseTransparent(true);

        StackPane wrapper = new StackPane();
        wrapper.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(wrapper, Priority.ALWAYS);
        wrapper.getChildren().addAll(tf, icon);

        row.getChildren().add(wrapper);
        return row;
    }

    // ================================================================
    //  UTILITY — Wrap ComboBox with leading icon
    // ================================================================
    private HBox wrapComboWithIcon(String iconText, ComboBox<String> cb) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label(iconText);
        icon.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-text-fill: " + GRAY_400 + ";" +
                "-fx-padding: 0 0 0 10;"
        );
        icon.setMouseTransparent(true);

        StackPane wrapper = new StackPane();
        wrapper.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(wrapper, Priority.ALWAYS);
        wrapper.getChildren().addAll(cb, icon);

        row.getChildren().add(wrapper);
        return row;
    }

    // ================================================================
    //  UTILITY — Rounded icon container
    // ================================================================
    private StackPane createRoundedIcon(String text, String fgColor, String bgColor, double size) {
        StackPane pane = new StackPane();
        pane.setPrefSize(size, size);
        pane.setMinSize(size, size);
        pane.setMaxSize(size, size);
        pane.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                "-fx-background-radius: 8;"
        );
        Label lbl = new Label(text);
        lbl.setStyle(
                "-fx-font-size: " + (size * 0.45) + "px;" +
                "-fx-text-fill: " + fgColor + ";" +
                "-fx-font-weight: bold;"
        );
        pane.getChildren().add(lbl);
        return pane;
    }

    // ================================================================
    //  MAIN
    // ================================================================
    public static void main(String[] args) {
        launch(args);
    }
}
