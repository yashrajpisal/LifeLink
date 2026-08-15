package com.kurukshetra.view.driver;

// public class DriverProfile {
    
// }
// package com.kurukshetra.view.driver;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DriverProfile {

    private BorderPane reportsRoot;
    private StackPane pageStack;

    // =========================================================
    // COLORS
    // =========================================================

    private static final String BACKGROUND = "#F8F7FD";
    private static final String WHITE = "#FFFFFF";

    private static final String BLUE = "#0756D9";
    private static final String BLUE_HOVER = "#0648B8";
    private static final String LIGHT_BLUE = "#EAF2FF";

    private static final String BORDER = "#D9DCE8";
    private static final String DIVIDER = "#E4E3ED";

    private static final String TEXT = "#172033";
    private static final String SECONDARY = "#697386";
    private static final String MUTED = "#8B93A5";

    private static final String RED = "#D91C24";
    private static final String LIGHT_RED = "#FFF0F0";

    private static final String GREEN = "#149447";
    private static final String LIGHT_GREEN = "#EAF8EF";

    // =========================================================
    // DIMENSIONS
    // =========================================================

    /*
     * The screenshot body is relatively narrow.
     * These values keep the page compact beside the existing
     * Driver sidebar.
     */
    private static final double BODY_MIN_WIDTH = 430;
    private static final double BODY_PREF_WIDTH = 540;
    private static final double BODY_MAX_WIDTH = 720;

    // =========================================================
    // MAIN PAGE
    // =========================================================

    public BorderPane getMedicalReportsPage(Runnable callbackDashboard) {

        reportsRoot = new BorderPane();

        reportsRoot.setMinWidth(BODY_MIN_WIDTH);
        reportsRoot.setPrefWidth(BODY_PREF_WIDTH);
        reportsRoot.setMaxWidth(BODY_MAX_WIDTH);

        reportsRoot.setStyle(
                "-fx-background-color: " + BACKGROUND + ";" +
                "-fx-font-family: 'Segoe UI';"
        );

        pageStack = new StackPane();

        VBox content = createMedicalReportsBody(callbackDashboard);

        pageStack.getChildren().add(content);

        ScrollPane scrollPane = createScrollPane(pageStack);

        reportsRoot.setCenter(scrollPane);

        // =====================================================
        // PAGE ANIMATION
        // =====================================================

        reportsRoot.setOpacity(0);

        FadeTransition fade = new FadeTransition(
                Duration.millis(350),
                reportsRoot
        );

        fade.setFromValue(0);
        fade.setToValue(1);

        fade.play();

        return reportsRoot;
    }

    // =========================================================
    // MAIN MEDICAL REPORT BODY
    // =========================================================

    private VBox createMedicalReportsBody(Runnable callbackDashboard) {

        VBox body = new VBox(0);

        body.setFillWidth(true);
        body.setPadding(new Insets(18, 18, 18, 18));

        body.setStyle(
                "-fx-background-color: " + BACKGROUND + ";"
        );

        // =====================================================
        // MAIN MEDICAL REPORT CARD
        // =====================================================

        HBox reportsCard = new HBox();

        reportsCard.setPrefWidth(BODY_PREF_WIDTH - 36);
        reportsCard.setMaxWidth(Double.MAX_VALUE);
        reportsCard.setMinHeight(490);

        reportsCard.setStyle(
                "-fx-background-color: " + WHITE + ";" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-width: 1;" +
                "-fx-background-radius: 8;" +
                "-fx-border-radius: 8;"
        );

        // =====================================================
        // LEFT MEMBER PANEL
        // =====================================================

        VBox memberPanel = createMemberPanel();

        memberPanel.setPrefWidth(150);
        memberPanel.setMinWidth(145);
        memberPanel.setMaxWidth(165);

        // =====================================================
        // RIGHT DETAILS PANEL
        // =====================================================

        VBox detailsPanel = createDetailsPanel();

        HBox.setHgrow(detailsPanel, Priority.ALWAYS);

        reportsCard.getChildren().addAll(
                memberPanel,
                detailsPanel
        );

        body.getChildren().add(reportsCard);

        return body;
    }

    // =========================================================
    // MEMBER PANEL
    // =========================================================

    private VBox createMemberPanel() {

        VBox panel = new VBox(12);

        panel.setPadding(new Insets(12, 8, 12, 8));

        panel.setStyle(
                "-fx-background-color: #FBFAFF;" +
                "-fx-border-color: " + DIVIDER + ";" +
                "-fx-border-width: 0 1 0 0;"
        );

        // =====================================================
        // HEADER
        // =====================================================

        HBox header = new HBox();

        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(1);

        Text title = new Text("Medical");
        title.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT + ";"
        );

        Text title2 = new Text("Reports");
        title2.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT + ";"
        );

        titleBox.getChildren().addAll(
                title,
                title2
        );

        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addButton = new Button("+\nAdd\nNew");

        addButton.setPrefWidth(38);
        addButton.setPrefHeight(42);

        addButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: " + BLUE + ";" +
                "-fx-font-size: 7px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 0;" +
                "-fx-cursor: hand;"
        );

        addButton.setOnMouseEntered(e ->
                addButton.setStyle(
                        "-fx-background-color: " + LIGHT_BLUE + ";" +
                        "-fx-text-fill: " + BLUE + ";" +
                        "-fx-font-size: 7px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 0;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
                )
        );

        addButton.setOnMouseExited(e ->
                addButton.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-text-fill: " + BLUE + ";" +
                        "-fx-font-size: 7px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 0;" +
                        "-fx-cursor: hand;"
                )
        );

        addButton.setOnAction(e ->
                showAddMemberModal()
        );

        header.getChildren().addAll(
                titleBox,
                spacer,
                addButton
        );

        // =====================================================
        // MEMBER CARDS
        // =====================================================

        VBox members = new VBox(6);

        Button member1 = createMemberButton(
                "member\n1",
                true
        );

        Button member2 = createMemberButton(
                "member 2",
                false
        );

        Button member3 = createMemberButton(
                "member 3",
                false
        );

        members.getChildren().addAll(
                member1,
                member2,
                member3
        );

        // =====================================================
        // MEMBER SELECTION
        // =====================================================

        member1.setOnAction(e -> {

            setActiveMember(
                    members,
                    member1
            );

            refreshMemberDetails(
                    "Jane Doe",
                    "O+",
                    "Penicillin",
                    "65 kg",
                    "555-0198"
            );
        });

        member2.setOnAction(e -> {

            setActiveMember(
                    members,
                    member2
            );

            refreshMemberDetails(
                    "Michael Smith",
                    "A+",
                    "None",
                    "72 kg",
                    "555-0214"
            );
        });

        member3.setOnAction(e -> {

            setActiveMember(
                    members,
                    member3
            );

            refreshMemberDetails(
                    "Sarah Wilson",
                    "B+",
                    "Aspirin",
                    "58 kg",
                    "555-0288"
            );
        });

        panel.getChildren().addAll(
                header,
                members
        );

        return panel;
    }

    // =========================================================
    // MEMBER BUTTON
    // =========================================================

    private Button createMemberButton(
            String memberName,
            boolean active
    ) {

        Button button = new Button();

        HBox content = new HBox(7);

        content.setAlignment(Pos.CENTER_LEFT);

        // Avatar
        StackPane avatar = new StackPane();

        avatar.setPrefSize(25, 25);
        avatar.setMinSize(25, 25);
        avatar.setMaxSize(25, 25);

        Circle circle = new Circle(12);

        circle.setFill(
                active
                        ? Color.web("#C9D8FF")
                        : Color.web("#E3E5ED")
        );

        Text icon = new Text("●");

        icon.setStyle(
                "-fx-font-size: 8px;" +
                "-fx-fill: " + (active ? BLUE : SECONDARY) + ";"
        );

        avatar.getChildren().addAll(
                circle,
                icon
        );

        Text name = new Text(memberName);

        name.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-font-weight: " +
                (active ? "bold" : "normal") + ";" +
                "-fx-fill: " + TEXT + ";"
        );

        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);

        Text arrow = new Text("›");

        arrow.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-fill: " + BLUE + ";"
        );

        content.getChildren().addAll(
                avatar,
                name,
                spacer,
                arrow
        );

        button.setGraphic(content);
        button.setText("");

        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(48);

        button.setPadding(
                new Insets(5, 7, 5, 7)
        );

        if (active) {

            button.setStyle(
                    "-fx-background-color: " + LIGHT_BLUE + ";" +
                    "-fx-border-color: " + BLUE + ";" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-cursor: hand;"
            );

        } else {

            button.setStyle(
                    "-fx-background-color: transparent;" +
                    "-fx-border-color: " + BORDER + ";" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-cursor: hand;"
            );
        }

        button.setOnMouseEntered(e -> {

            if (!button.getStyle().contains(BLUE)) {

                button.setStyle(
                        "-fx-background-color: #F2F5FC;" +
                        "-fx-border-color: #B8C5DE;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
                );
            }
        });

        button.setOnMouseExited(e -> {

            if (!button.getStyle().contains(
                    "-fx-background-color: " + LIGHT_BLUE
            )) {

                button.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: " + BORDER + ";" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
                );
            }
        });

        return button;
    }

    // =========================================================
    // ACTIVE MEMBER
    // =========================================================

    private void setActiveMember(
            VBox members,
            Button selected
    ) {

        for (Node node : members.getChildren()) {

            if (!(node instanceof Button button)) {
                continue;
            }

            HBox content = (HBox) button.getGraphic();

            Text name = null;

            for (Node child : content.getChildren()) {

                if (child instanceof Text text) {

                    if (!text.getText().equals("›")) {
                        name = text;
                        break;
                    }
                }
            }

            if (button == selected) {

                button.setStyle(
                        "-fx-background-color: " + LIGHT_BLUE + ";" +
                        "-fx-border-color: " + BLUE + ";" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
                );

            } else {

                button.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: " + BORDER + ";" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
                );
            }
        }
    }

    // =========================================================
    // DETAILS PANEL
    // =========================================================

    private VBox detailsPanel;

    private TextField nameField;
    private TextField bloodGroupField;
    private TextField allergiesField;
    private TextField weightField;
    private TextField phoneField;

    private VBox createDetailsPanel() {

        detailsPanel = new VBox();

        detailsPanel.setPadding(
                new Insets(12, 12, 0, 12)
        );

        detailsPanel.setStyle(
                "-fx-background-color: " + WHITE + ";"
        );

        // =====================================================
        // DETAILS HEADER
        // =====================================================

        VBox detailsHeader = new VBox(5);

        HBox titleRow = new HBox();

        titleRow.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("Details");

        title.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT + ";"
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        titleRow.getChildren().addAll(
                title,
                spacer
        );

        Region divider = new Region();

        divider.setPrefHeight(1);

        divider.setMaxWidth(Double.MAX_VALUE);

        divider.setStyle(
                "-fx-background-color: " + DIVIDER + ";"
        );

        detailsHeader.getChildren().addAll(
                titleRow,
                divider
        );

        // =====================================================
        // FORM
        // =====================================================

        VBox form = new VBox(7);

        // Name + Blood Group
        HBox row1 = new HBox(8);

        VBox nameBox = createField(
                "Name",
                "Jane Doe",
                0
        );

        VBox bloodBox = createField(
                "BG",
                "O+",
                1
        );

        HBox.setHgrow(
                nameBox,
                Priority.ALWAYS
        );

        bloodBox.setPrefWidth(60);
        bloodBox.setMinWidth(60);
        bloodBox.setMaxWidth(80);

        row1.getChildren().addAll(
                nameBox,
                bloodBox
        );

        // Allergies + Weight
        HBox row2 = new HBox(8);

        VBox allergiesBox = createField(
                "Allergies",
                "Penicillin",
                2
        );

        VBox weightBox = createField(
                "Wt",
                "65 kg",
                3
        );

        HBox.setHgrow(
                allergiesBox,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                weightBox,
                Priority.ALWAYS
        );

        row2.getChildren().addAll(
                allergiesBox,
                weightBox
        );

        // Phone + View Report
        HBox row3 = new HBox(8);

        VBox phoneBox = createField(
                "Phn NO.",
                "555-0198",
                4
        );

        HBox.setHgrow(
                phoneBox,
                Priority.ALWAYS
        );

        Button viewReportButton = createViewReportButton();

        row3.getChildren().addAll(
                phoneBox,
                viewReportButton
        );

        // Save
        HBox saveRow = new HBox();

        saveRow.setAlignment(
                Pos.CENTER_RIGHT
        );

        Button saveButton = createSaveButton();

        saveRow.getChildren().add(
                saveButton
        );

        form.getChildren().addAll(
                row1,
                row2,
                row3,
                saveRow
        );

        // =====================================================
        // TOP DETAILS AREA
        // =====================================================

        VBox topArea = new VBox(10);

        topArea.setPadding(
                new Insets(0, 0, 12, 0)
        );

        topArea.getChildren().addAll(
                detailsHeader,
                form
        );

        // =====================================================
        // SPACER
        // =====================================================

        Region verticalSpacer = new Region();

        VBox.setVgrow(
                verticalSpacer,
                Priority.ALWAYS
        );

        // =====================================================
        // ALL REPORT SECTION
        // =====================================================

        VBox allReportSection = createAllReportSection();

        detailsPanel.getChildren().addAll(
                topArea,
                verticalSpacer,
                allReportSection
        );

        return detailsPanel;
    }

    // =========================================================
    // FORM FIELD
    // =========================================================

    private VBox createField(
            String label,
            String value,
            int fieldIndex
    ) {

        VBox box = new VBox(3);

        Text labelText = new Text(label);

        labelText.setStyle(
                "-fx-font-size: 7px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + SECONDARY + ";"
        );

        TextField field = new TextField(value);

        field.setPrefHeight(25);

        field.setMinHeight(25);

        field.setPadding(
                new Insets(4, 7, 4, 7)
        );

        field.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-border-color: #D9DCE8;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;" +
                "-fx-font-size: 8px;" +
                "-fx-text-fill: " + TEXT + ";"
        );

        field.focusedProperty().addListener(
                (obs, oldValue, focused) -> {

                    if (focused) {

                        field.setStyle(
                                "-fx-background-color: #FFFFFF;" +
                                "-fx-border-color: " + BLUE + ";" +
                                "-fx-border-width: 1;" +
                                "-fx-border-radius: 5;" +
                                "-fx-background-radius: 5;" +
                                "-fx-font-size: 8px;" +
                                "-fx-text-fill: " + TEXT + ";"
                        );

                    } else {

                        field.setStyle(
                                "-fx-background-color: #FFFFFF;" +
                                "-fx-border-color: #D9DCE8;" +
                                "-fx-border-width: 1;" +
                                "-fx-border-radius: 5;" +
                                "-fx-background-radius: 5;" +
                                "-fx-font-size: 8px;" +
                                "-fx-text-fill: " + TEXT + ";"
                        );
                    }
                }
        );

        switch (fieldIndex) {

            case 0 -> nameField = field;

            case 1 -> bloodGroupField = field;

            case 2 -> allergiesField = field;

            case 3 -> weightField = field;

            case 4 -> phoneField = field;

            default -> {
            }
        }

        box.getChildren().addAll(
                labelText,
                field
        );

        return box;
    }

    // =========================================================
    // VIEW REPORT BUTTON
    // =========================================================

    private Button createViewReportButton() {

        Button button = new Button(
                "▧  View Report"
        );

        button.setPrefWidth(92);
        button.setPrefHeight(25);

        button.setStyle(
                "-fx-background-color: #E8EAF2;" +
                "-fx-text-fill: " + TEXT + ";" +
                "-fx-font-size: 7px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #DDE2EF;" +
                        "-fx-text-fill: " + BLUE + ";" +
                        "-fx-font-size: 7px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-border-radius: 5;" +
                        "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: #E8EAF2;" +
                        "-fx-text-fill: " + TEXT + ";" +
                        "-fx-font-size: 7px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-border-radius: 5;" +
                        "-fx-cursor: hand;"
                )
        );

        button.setOnAction(e ->
                showReportPreviewModal()
        );

        return button;
    }

    // =========================================================
    // SAVE BUTTON
    // =========================================================

    private Button createSaveButton() {

        Button button = new Button(
                "Save"
        );

        button.setPrefWidth(45);
        button.setPrefHeight(25);

        button.setStyle(
                "-fx-background-color: " + BLUE + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 7px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 5;" +
                "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: " + BLUE_HOVER + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 7px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: " + BLUE + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 7px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
                )
        );

        button.setOnAction(e -> {

            System.out.println(
                    "Medical information saved."
            );

            showSavedAnimation();
        });

        return button;
    }

    // =========================================================
    // ALL REPORT SECTION
    // =========================================================

    private VBox createAllReportSection() {

        VBox section = new VBox(8);

        section.setPadding(
                new Insets(12, 0, 12, 0)
        );

        section.setStyle(
                "-fx-background-color: #F5F4FC;" +
                "-fx-border-color: " + DIVIDER + ";" +
                "-fx-border-width: 1 0 0 0;"
        );

        Text title = new Text(
                "All Report"
        );

        title.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT + ";"
        );

        Region divider = new Region();

        divider.setPrefHeight(1);

        divider.setStyle(
                "-fx-background-color: " + DIVIDER + ";"
        );

        HBox uploadRow = new HBox(7);

        // Report type
        Button reportTypeButton =
                new Button("Select Report Type");

        reportTypeButton.setPrefWidth(88);
        reportTypeButton.setPrefHeight(34);

        reportTypeButton.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;" +
                "-fx-text-fill: " + TEXT + ";" +
                "-fx-font-size: 7px;" +
                "-fx-cursor: hand;"
        );

        // Upload
        Button uploadButton =
                new Button("♧\nUpload File");

        uploadButton.setPrefWidth(100);
        uploadButton.setPrefHeight(34);

        uploadButton.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #C8CEDC;" +
                "-fx-border-style: dashed;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;" +
                "-fx-text-fill: " + BLUE + ";" +
                "-fx-font-size: 7px;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
        );

        uploadButton.setOnMouseEntered(e ->
                uploadButton.setStyle(
                        "-fx-background-color: " + LIGHT_BLUE + ";" +
                        "-fx-border-color: " + BLUE + ";" +
                        "-fx-border-style: dashed;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-text-fill: " + BLUE + ";" +
                        "-fx-font-size: 7px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"
                )
        );

        uploadButton.setOnMouseExited(e ->
                uploadButton.setStyle(
                        "-fx-background-color: white;" +
                        "-fx-border-color: #C8CEDC;" +
                        "-fx-border-style: dashed;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-text-fill: " + BLUE + ";" +
                        "-fx-font-size: 7px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"
                )
        );

        uploadButton.setOnAction(
                e -> showUploadAnimation()
        );

        uploadRow.getChildren().addAll(
                reportTypeButton,
                uploadButton
        );

        section.getChildren().addAll(
                title,
                divider,
                uploadRow
        );

        return section;
    }

    // =========================================================
    // REPORT PREVIEW MODAL
    // =========================================================

    private void showReportPreviewModal() {

        StackPane dimmer = new StackPane();

        dimmer.setStyle(
                "-fx-background-color: rgba(23,32,51,0.42);"
        );

        VBox modal = new VBox(12);

        modal.setMaxWidth(330);
        modal.setMaxHeight(250);

        modal.setPadding(
                new Insets(20)
        );

        modal.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(20,30,50,0.30), 20, 0, 0, 8);"
        );

        Text title = new Text(
                "Medical Report"
        );

        title.setStyle(
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT + ";"
        );

        Text reportTitle = new Text(
                "Patient Medical Summary"
        );

        reportTitle.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + BLUE + ";"
        );

        VBox information = new VBox(7);

        information.getChildren().addAll(
                modalInfoRow(
                        "Patient",
                        nameField != null
                                ? nameField.getText()
                                : "Jane Doe"
                ),
                modalInfoRow(
                        "Blood Group",
                        bloodGroupField != null
                                ? bloodGroupField.getText()
                                : "O+"
                ),
                modalInfoRow(
                        "Allergies",
                        allergiesField != null
                                ? allergiesField.getText()
                                : "Penicillin"
                ),
                modalInfoRow(
                        "Weight",
                        weightField != null
                                ? weightField.getText()
                                : "65 kg"
                )
        );

        Button close = new Button(
                "Close"
        );

        close.setPrefWidth(90);
        close.setPrefHeight(34);

        close.setStyle(
                "-fx-background-color: " + LIGHT_BLUE + ";" +
                "-fx-text-fill: " + BLUE + ";" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 7;" +
                "-fx-cursor: hand;"
        );

        close.setOnAction(
                e -> hideModal(dimmer)
        );

        HBox closeRow = new HBox(close);

        closeRow.setAlignment(
                Pos.CENTER_RIGHT
        );

        modal.getChildren().addAll(
                title,
                reportTitle,
                information,
                closeRow
        );

        modal.setOpacity(0);
        modal.setScaleX(0.90);
        modal.setScaleY(0.90);

        dimmer.getChildren().add(modal);

        dimmer.setOnMouseClicked(e -> {

            if (e.getTarget() == dimmer) {
                hideModal(dimmer);
            }
        });

        pageStack.getChildren().add(dimmer);

        animateModalIn(
                dimmer,
                modal
        );
    }

    // =========================================================
    // MODAL INFORMATION ROW
    // =========================================================

    private HBox modalInfoRow(
            String label,
            String value
    ) {

        HBox row = new HBox();

        Text labelText = new Text(label);

        labelText.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-fill: " + SECONDARY + ";"
        );

        Region spacer = new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Text valueText = new Text(value);

        valueText.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT + ";"
        );

        row.getChildren().addAll(
                labelText,
                spacer,
                valueText
        );

        return row;
    }

    // =========================================================
    // ADD MEMBER MODAL
    // =========================================================

    private void showAddMemberModal() {

        StackPane dimmer = new StackPane();

        dimmer.setStyle(
                "-fx-background-color: rgba(23,32,51,0.42);"
        );

        VBox modal = new VBox(12);

        modal.setMaxWidth(320);

        modal.setPadding(
                new Insets(20)
        );

        modal.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(20,30,50,0.30), 20, 0, 0, 8);"
        );

        Text title = new Text(
                "Add Member"
        );

        title.setStyle(
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + TEXT + ";"
        );

        TextField memberName =
                createModalField(
                        "Member Name"
                );

        TextField memberId =
                createModalField(
                        "Member ID"
                );

        Button addButton =
                new Button("Add Member");

        addButton.setPrefHeight(36);
        addButton.setMaxWidth(Double.MAX_VALUE);

        addButton.setStyle(
                "-fx-background-color: " + BLUE + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 7;" +
                "-fx-cursor: hand;"
        );

        addButton.setOnAction(e -> {

            System.out.println(
                    "Member added: " +
                    memberName.getText()
            );

            hideModal(dimmer);
        });

        Button cancelButton =
                new Button("Cancel");

        cancelButton.setPrefWidth(80);
        cancelButton.setPrefHeight(34);

        cancelButton.setStyle(
                "-fx-background-color: #EEF0F5;" +
                "-fx-text-fill: " + TEXT + ";" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 7;" +
                "-fx-cursor: hand;"
        );

        cancelButton.setOnAction(
                e -> hideModal(dimmer)
        );

        HBox buttons =
                new HBox(
                        8,
                        cancelButton,
                        addButton
                );

        HBox.setHgrow(
                addButton,
                Priority.ALWAYS
        );

        buttons.setAlignment(
                Pos.CENTER_RIGHT
        );

        modal.getChildren().addAll(
                title,
                memberName,
                memberId,
                buttons
        );

        modal.setOpacity(0);
        modal.setScaleX(0.90);
        modal.setScaleY(0.90);

        dimmer.getChildren().add(modal);

        dimmer.setOnMouseClicked(e -> {

            if (e.getTarget() == dimmer) {
                hideModal(dimmer);
            }
        });

        pageStack.getChildren().add(dimmer);

        animateModalIn(
                dimmer,
                modal
        );
    }

    // =========================================================
    // MODAL FIELD
    // =========================================================

    private TextField createModalField(
            String prompt
    ) {

        TextField field = new TextField();

        field.setPromptText(prompt);

        field.setPrefHeight(35);

        field.setStyle(
                "-fx-background-color: #F6F7FB;" +
                "-fx-border-color: #D9DCE8;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 7;" +
                "-fx-background-radius: 7;" +
                "-fx-font-size: 10px;"
        );

        return field;
    }

    // =========================================================
    // MODAL ANIMATION
    // =========================================================

    private void animateModalIn(
            StackPane dimmer,
            VBox modal
    ) {

        FadeTransition dimFade =
                new FadeTransition(
                        Duration.millis(180),
                        dimmer
                );

        dimFade.setFromValue(0);
        dimFade.setToValue(1);

        FadeTransition modalFade =
                new FadeTransition(
                        Duration.millis(220),
                        modal
                );

        modalFade.setFromValue(0);
        modalFade.setToValue(1);

        ScaleTransition scale =
                new ScaleTransition(
                        Duration.millis(220),
                        modal
                );

        scale.setFromX(0.90);
        scale.setFromY(0.90);

        scale.setToX(1);
        scale.setToY(1);

        new ParallelTransition(
                dimFade,
                modalFade,
                scale
        ).play();
    }

    // =========================================================
    // HIDE MODAL
    // =========================================================

    private void hideModal(
            StackPane dimmer
    ) {

        FadeTransition fade =
                new FadeTransition(
                        Duration.millis(160),
                        dimmer
                );

        fade.setFromValue(1);
        fade.setToValue(0);

        fade.setOnFinished(
                e -> pageStack.getChildren().remove(dimmer)
        );

        fade.play();
    }

    // =========================================================
    // SAVE ANIMATION
    // =========================================================

    private void showSavedAnimation() {

        if (detailsPanel == null) {
            return;
        }

        Text savedText =
                new Text("✓ Saved successfully");

        savedText.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + GREEN + ";"
        );

        StackPane notification =
                new StackPane(savedText);

        notification.setPadding(
                new Insets(8, 12, 8, 12)
        );

        notification.setStyle(
                "-fx-background-color: " + LIGHT_GREEN + ";" +
                "-fx-background-radius: 7;" +
                "-fx-border-color: #BDE8CB;" +
                "-fx-border-radius: 7;"
        );

        pageStack.getChildren().add(
                notification
        );

        StackPane.setAlignment(
                notification,
                Pos.TOP_RIGHT
        );

        StackPane.setMargin(
                notification,
                new Insets(15)
        );

        FadeTransition fade =
                new FadeTransition(
                        Duration.millis(1800),
                        notification
                );

        fade.setFromValue(1);
        fade.setToValue(0);

        fade.setDelay(
                Duration.millis(700)
        );

        fade.setOnFinished(
                e -> pageStack.getChildren().remove(notification)
        );

        fade.play();
    }

    // =========================================================
    // UPLOAD ANIMATION
    // =========================================================

    private void showUploadAnimation() {

        StackPane notification =
                new StackPane(
                        new Text("✓ File selected")
                );

        Text text =
                (Text) notification.getChildren().get(0);

        text.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + BLUE + ";"
        );

        notification.setPadding(
                new Insets(8, 12, 8, 12)
        );

        notification.setStyle(
                "-fx-background-color: " + LIGHT_BLUE + ";" +
                "-fx-background-radius: 7;" +
                "-fx-border-color: #C7D8FF;" +
                "-fx-border-radius: 7;"
        );

        pageStack.getChildren().add(
                notification
        );

        StackPane.setAlignment(
                notification,
                Pos.TOP_RIGHT
        );

        StackPane.setMargin(
                notification,
                new Insets(15)
        );

        FadeTransition fade =
                new FadeTransition(
                        Duration.millis(1600),
                        notification
                );

        fade.setFromValue(1);
        fade.setToValue(0);

        fade.setDelay(
                Duration.millis(600)
        );

        fade.setOnFinished(
                e -> pageStack.getChildren().remove(notification)
        );

        fade.play();
    }

    // =========================================================
    // REFRESH DETAILS
    // =========================================================

    private void refreshMemberDetails(
            String name,
            String bloodGroup,
            String allergies,
            String weight,
            String phone
    ) {

        if (nameField != null) {
            nameField.setText(name);
        }

        if (bloodGroupField != null) {
            bloodGroupField.setText(bloodGroup);
        }

        if (allergiesField != null) {
            allergiesField.setText(allergies);
        }

        if (weightField != null) {
            weightField.setText(weight);
        }

        if (phoneField != null) {
            phoneField.setText(phone);
        }

        if (detailsPanel != null) {

            FadeTransition fade =
                    new FadeTransition(
                            Duration.millis(220),
                            detailsPanel
                    );

            fade.setFromValue(0.65);
            fade.setToValue(1);

            fade.play();
        }
    }

    // =========================================================
    // SCROLL PANE
    // =========================================================

    private ScrollPane createScrollPane(
            StackPane content
    ) {

        ScrollPane scrollPane =
                new ScrollPane(content);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scrollPane.setPannable(true);

        scrollPane.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background: " + BACKGROUND + ";" +
                "-fx-border-color: transparent;"
        );

        return scrollPane;
    }

    // =========================================================
    // PUBLIC WIDTH CONTROL
    // =========================================================

    public void setBodyWidth(double width) {

        if (reportsRoot == null) {
            return;
        }

        double adjustedWidth =
                Math.max(
                        BODY_MIN_WIDTH,
                        Math.min(
                                BODY_MAX_WIDTH,
                                width
                        )
                );

        reportsRoot.setPrefWidth(
                adjustedWidth
        );
    }

    // =========================================================
    // GET ROOT
    // =========================================================

    public BorderPane getRoot() {
        return reportsRoot;
    }
}