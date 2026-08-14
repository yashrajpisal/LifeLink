package com.kurukshetra.view.family;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class MedicalReports {

    private BorderPane root;
    private Stage stage;
    private static final String BACKGROUND = "#F7F9FC";
    private static final String WHITE = "#FFFFFF";
    private static final String BLUE = "#0756D6";
    private static final String BLUE_DARK = "#0344AE";
    private static final String BLUE_SELECTED = "#DCE9FF";
    private static final String TEXT = "#172B4D";
    private static final String SECONDARY = "#728096";
    private static final String BORDER = "#DCE5EC";
    private static final String DIVIDER = "#E7ECF3";
    private static final double MEMBER_PANEL_WIDTH = 300;
    private static final double MEMBER_CARD_WIDTH = 250;
    private static final double DETAILS_GAP = 20;
    private static final double REPORT_HEIGHT = 550;

    public MedicalReports() {}

    public BorderPane setBorderPane(Stage stage) {

        this.stage = stage;

        root = new BorderPane();        
        root.getStyleClass().add("root-pane");
        VBox sidebar = Sidebar.build(stage,Sidebar.Page.MEDICAL_HISTORY);

        root.setLeft(sidebar);
        VBox body = createMedicalReportsBody();

        ScrollPane scrollPane =createMainScrollPane(body);
        root.setCenter(scrollPane);
        // playPageAnimation(scrollPane);

        return root;
    }
    // MAIN MEDICAL REPORTS BODY
    
    private VBox createMedicalReportsBody() {

        VBox body = new VBox();
        body.setPadding(new Insets(24));
        body.setStyle("-fx-background-color: " +BACKGROUND + ";");
        body.setFillWidth(true);
        // MAIN WHITE CONTAINER

        HBox reportsContainer = new HBox(DETAILS_GAP);

        reportsContainer.setMinHeight(REPORT_HEIGHT);
        reportsContainer.setPrefHeight(REPORT_HEIGHT);

        reportsContainer.setMaxWidth(
                Double.MAX_VALUE
        );

        reportsContainer.setStyle(
                "-fx-background-color: " +
                        WHITE + ";" +
                "-fx-border-color: " +
                        BORDER + ";" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;"
        );

        // =====================================================
        // MEMBER PANEL
        // =====================================================

        VBox memberPanel = createMemberPanel();

        /*
         * MEMBER PANEL SIZE IS NOT CHANGED.
         */
        memberPanel.setPrefWidth(
                MEMBER_PANEL_WIDTH
        );

        memberPanel.setMinWidth(
                MEMBER_PANEL_WIDTH
        );

        memberPanel.setMaxWidth(
                MEMBER_PANEL_WIDTH
        );

        // =====================================================
        // DETAILS PANEL
        // =====================================================

        VBox detailsPanel =
                createDetailsPanel();

        /*
         * Details panel uses remaining space.
         */
        HBox.setHgrow(
                detailsPanel,
                Priority.ALWAYS
        );

        reportsContainer.getChildren().addAll(
                memberPanel,
                detailsPanel
        );

        body.getChildren().add(
                reportsContainer
        );

        return body;
    }

    // =========================================================
    // MEMBER PANEL
    // =========================================================

    private VBox createMemberPanel() {

        VBox panel = new VBox();

        panel.setPadding(
                new Insets(
                        18,
                        14,
                        18,
                        14
                )
        );

        panel.setSpacing(12);

        panel.setFillWidth(true);

        panel.setStyle(
                "-fx-background-color: #FAFBFF;" +
                "-fx-border-color: " +
                        BORDER + ";" +
                "-fx-border-width: 0 1 0 0;"
        );

        // =====================================================
        // HEADER
        // =====================================================

        HBox heading = new HBox();

        heading.setAlignment(
                Pos.CENTER_LEFT
        );

        Label title =
                new Label(
                        "Medical\nReports"
                );

        title.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " +
                        TEXT + ";"
        );

        Region space = new Region();

        HBox.setHgrow(
                space,
                Priority.ALWAYS
        );

        Button newButton =
                new Button("+ New");

        newButton.setPrefWidth(55);

        newButton.setPrefHeight(32);

        newButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: " +
                        BLUE + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
        );

        newButton.setOnAction(
                e -> showMessage(
                        "New member button clicked."
                )
        );

        heading.getChildren().addAll(
                title,
                space,
                newButton
        );

        panel.getChildren().add(
                heading
        );

        // =====================================================
        // MEMBERS
        // =====================================================

        panel.getChildren().addAll(

                createMember(
                        "Member 1",
                        true
                ),

                createMember(
                        "Member 2",
                        false
                ),

                createMember(
                        "Member 3",
                        false
                )
        );

        return panel;
    }

    // =========================================================
    // MEMBER CARD
    // =========================================================

    private HBox createMember(
            String memberName,
            boolean selected
    ) {

        HBox card =
                new HBox(8);

        card.setAlignment(
                Pos.CENTER_LEFT
        );

        /*
         * Member card width is fixed.
         *
         * This prevents overlapping.
         */
        card.setPrefWidth(
                MEMBER_CARD_WIDTH
        );

        card.setMinWidth(
                MEMBER_CARD_WIDTH
        );

        card.setMaxWidth(
                MEMBER_CARD_WIDTH
        );

        card.setPrefHeight(62);

        card.setMinHeight(62);

        card.setMaxHeight(62);

        card.setPadding(
                new Insets(
                        9,
                        10,
                        9,
                        10
                )
        );

        // =====================================================
        // AVATAR
        // =====================================================

        StackPane avatar =
                new StackPane();

        avatar.setPrefSize(
                38,
                38
        );

        avatar.setMinSize(
                38,
                38
        );

        avatar.setMaxSize(
                38,
                38
        );

        avatar.setStyle(
                "-fx-background-color: " +
                        (selected
                                ? "#D8E5FF"
                                : "#EEF1F6") +
                        ";" +
                "-fx-background-radius: 50%;"
        );

        Label dot =
                new Label("•");

        dot.setStyle(
                "-fx-font-size: 13px;" +
                "-fx-text-fill: " +
                        (selected
                                ? BLUE
                                : SECONDARY) +
                        ";"
        );

        avatar.getChildren().add(
                dot
        );

        // =====================================================
        // NAME
        // =====================================================

        Label name =
                new Label(memberName);

        /*
         * Enough width to show:
         *
         * Member 1
         * Member 2
         * Member 3
         */
        name.setPrefWidth(90);

        name.setMinWidth(90);

        name.setMaxWidth(90);

        name.setWrapText(false);

        name.setStyle(
                "-fx-font-size: 13px;" +
                "-fx-font-weight: " +
                        (selected
                                ? "bold"
                                : "normal") +
                        ";" +
                "-fx-text-fill: " +
                        TEXT + ";"
        );

        // =====================================================
        // SPACE
        // =====================================================

        Region nameSpace =
                new Region();

        HBox.setHgrow(
                nameSpace,
                Priority.ALWAYS
        );

        // =====================================================
        // ARROW
        // =====================================================

        Label arrow =
                new Label(
                        selected
                                ? "›"
                                : ""
                );

        arrow.setPrefWidth(12);

        arrow.setMinWidth(12);

        arrow.setMaxWidth(12);

        arrow.setAlignment(
                Pos.CENTER
        );

        arrow.setStyle(
                "-fx-font-size: 22px;" +
                "-fx-text-fill: " +
                        BLUE + ";"
        );

        // =====================================================
        // ADD CHILDREN
        // =====================================================

        card.getChildren().addAll(
                avatar,
                name,
                nameSpace,
                arrow
        );

        // =====================================================
        // STYLE
        // =====================================================

        if (selected) {

            card.setStyle(
                    "-fx-background-color: " +
                            BLUE_SELECTED + ";" +
                    "-fx-border-color: " +
                            BLUE + ";" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 9;" +
                    "-fx-background-radius: 9;" +
                    "-fx-cursor: hand;"
            );

        } else {

            card.setStyle(
                    "-fx-background-color: " +
                            WHITE + ";" +
                    "-fx-border-color: " +
                            BORDER + ";" +
                    "-fx-border-width: 1;" +
                    "-fx-border-radius: 9;" +
                    "-fx-background-radius: 9;" +
                    "-fx-cursor: hand;"
            );
        }

        // =====================================================
        // HOVER
        // =====================================================

        card.setOnMouseEntered(
                e -> {

                    if (!selected) {

                        card.setStyle(
                                "-fx-background-color: #F2F6FC;" +
                                "-fx-border-color: " +
                                        BLUE + ";" +
                                "-fx-border-width: 1;" +
                                "-fx-border-radius: 9;" +
                                "-fx-background-radius: 9;" +
                                "-fx-cursor: hand;"
                        );
                    }
                }
        );

        card.setOnMouseExited(
                e -> {

                    if (!selected) {

                        card.setStyle(
                                "-fx-background-color: " +
                                        WHITE + ";" +
                                "-fx-border-color: " +
                                        BORDER + ";" +
                                "-fx-border-width: 1;" +
                                "-fx-border-radius: 9;" +
                                "-fx-background-radius: 9;" +
                                "-fx-cursor: hand;"
                        );
                    }
                }
        );

        card.setOnMouseClicked(
                e -> animateNode(card)
        );

        return card;
    }

    // =========================================================
    // DETAILS PANEL
    // =========================================================

    private VBox createDetailsPanel() {

        VBox details =
                new VBox();

        details.setPadding(
                new Insets(
                        22,
                        24,
                        22,
                        24
                )
        );

        details.setSpacing(14);

        details.setStyle(
                "-fx-background-color: " +
                        WHITE + ";"
        );

        // =====================================================
        // TITLE
        // =====================================================

        Label title =
                new Label("Details");

        title.setStyle(
                "-fx-font-size: 19px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " +
                        TEXT + ";"
        );

        details.getChildren().add(
                title
        );

        details.getChildren().add(
                createDivider()
        );

        // =====================================================
        // NAME + BLOOD GROUP
        // =====================================================

        HBox nameRow =
                new HBox(14);

        VBox nameBox =
                createFormBox(
                        "Name",
                        "Yashraj Pisal"
                );

        VBox bloodBox =
                createFormBox(
                        "BG",
                        "O+"
                );

        HBox.setHgrow(
                nameBox,
                Priority.ALWAYS
        );

        bloodBox.setPrefWidth(90);

        bloodBox.setMinWidth(90);

        bloodBox.setMaxWidth(90);

        nameRow.getChildren().addAll(
                nameBox,
                bloodBox
        );

        // =====================================================
        // ALLERGIES + WEIGHT
        // =====================================================

        HBox allergyRow =
                new HBox(14);

        VBox allergyBox =
                createFormBox(
                        "Allergies",
                        "Penicillin"
                );

        VBox weightBox =
                createFormBox(
                        "Wt",
                        "65 kg"
                );

        HBox.setHgrow(
                allergyBox,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                weightBox,
                Priority.ALWAYS
        );

        allergyRow.getChildren().addAll(
                allergyBox,
                weightBox
        );

        // =====================================================
        // PHONE + REPORT BUTTON
        // =====================================================

        HBox phoneRow =
                new HBox(14);

        VBox phoneBox =
                createFormBox(
                        "Phn No.",
                        "555-0198"
                );

        HBox.setHgrow(
                phoneBox,
                Priority.ALWAYS
        );

        Button reportButton =
                new Button(
                        "▤  View Report"
                );

        reportButton.setPrefWidth(145);

        reportButton.setPrefHeight(42);

        reportButton.setStyle(
                "-fx-background-color: #E8EBF3;" +
                "-fx-text-fill: " +
                        TEXT + ";" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 7;" +
                "-fx-cursor: hand;"
        );

        reportButton.setOnAction(
                e -> showMessage(
                        "Medical report opened."
                )
        );

        phoneRow.getChildren().addAll(
                phoneBox,
                reportButton
        );

        // =====================================================
        // SAVE BUTTON
        // =====================================================

        HBox saveRow =
                new HBox();

        saveRow.setAlignment(
                Pos.CENTER_RIGHT
        );

        Button saveButton =
                createPrimaryButton(
                        "Save"
                );

        saveButton.setPrefWidth(84);

        saveButton.setOnAction(
                e -> showMessage(
                        "Medical details saved."
                )
        );

        saveRow.getChildren().add(
                saveButton
        );

        // =====================================================
        // ADD FORM
        // =====================================================

        details.getChildren().addAll(
                nameRow,
                allergyRow,
                phoneRow,
                saveRow
        );

        // =====================================================
        // SPACE
        // =====================================================

        Region verticalSpace =
                new Region();

        VBox.setVgrow(
                verticalSpace,
                Priority.ALWAYS
        );

        details.getChildren().add(
                verticalSpace
        );

        // =====================================================
        // ALL REPORT
        // =====================================================

        details.getChildren().add(
                createAllReportSection()
        );

        return details;
    }

    // =========================================================
    // FORM BOX
    // =========================================================

    private VBox createFormBox(
            String labelText,
            String value
    ) {

        VBox box =
                new VBox(5);

        HBox.setHgrow(
                box,
                Priority.ALWAYS
        );

        Label label =
                new Label(labelText);

        label.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " +
                        SECONDARY + ";"
        );

        TextField field =
                new TextField(value);

        field.setPrefHeight(44);

        field.setMinHeight(44);

        field.setMaxWidth(
                Double.MAX_VALUE
        );

        field.setStyle(
                "-fx-background-color: #FBFCFF;" +
                "-fx-border-color: " +
                        BORDER + ";" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 7;" +
                "-fx-background-radius: 7;" +
                "-fx-font-size: 13px;" +
                "-fx-text-fill: " +
                        TEXT + ";" +
                "-fx-padding: 0 12px;"
        );

        box.getChildren().addAll(
                label,
                field
        );

        return box;
    }

    // =========================================================
    // ALL REPORT SECTION
    // =========================================================

    private VBox createAllReportSection() {

        VBox section =
                new VBox(10);

        section.setPadding(
                new Insets(
                        16,
                        0,
                        0,
                        0
                )
        );

        section.setStyle(
                "-fx-border-color: " +
                        DIVIDER + ";" +
                "-fx-border-width: 1 0 0 0;"
        );

        Label title =
                new Label(
                        "All Report"
                );

        title.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " +
                        TEXT + ";"
        );

        HBox uploadRow =
                new HBox(12);

        uploadRow.setAlignment(
                Pos.CENTER_LEFT
        );

        // =====================================================
        // REPORT TYPE
        // =====================================================

        HBox reportType =
                new HBox();

        reportType.setAlignment(
                Pos.CENTER
        );

        reportType.setPrefHeight(48);

        HBox.setHgrow(
                reportType,
                Priority.ALWAYS
        );

        reportType.setStyle(
                "-fx-background-color: #FBFCFF;" +
                "-fx-border-color: " +
                        BORDER + ";" +
                "-fx-border-radius: 7;" +
                "-fx-background-radius: 7;"
        );

        Label selectText =
                new Label(
                        "Select Report Type"
                );

        selectText.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: " +
                        TEXT + ";"
        );

        reportType.getChildren().add(
                selectText
        );

        // =====================================================
        // UPLOAD BUTTON
        // =====================================================

        Button uploadButton =
                new Button(
                        "☁  Upload File"
                );

        uploadButton.setPrefWidth(130);

        uploadButton.setPrefHeight(48);

        uploadButton.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #9FB4DD;" +
                "-fx-border-style: dashed;" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 7;" +
                "-fx-background-radius: 7;" +
                "-fx-text-fill: " +
                        BLUE + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;"
        );

        uploadButton.setOnAction(
                e -> chooseReportFile()
        );

        uploadRow.getChildren().addAll(
                reportType,
                uploadButton
        );

        section.getChildren().addAll(
                title,
                uploadRow
        );

        return section;
    }

    // =========================================================
    // FILE CHOOSER
    // =========================================================

    private void chooseReportFile() {

        FileChooser chooser =
                new FileChooser();

        chooser.setTitle(
                "Select Medical Report"
        );

        chooser.getExtensionFilters()
                .add(
                        new FileChooser.ExtensionFilter(
                                "Medical Documents",
                                "*.pdf",
                                "*.png",
                                "*.jpg",
                                "*.jpeg",
                                "*.doc",
                                "*.docx"
                        )
                );

        File file =
                chooser.showOpenDialog(stage);

        if (file != null) {

            showMessage(
                    "Report selected:\n" +
                            file.getName()
            );
        }
    }

    // =========================================================
    // PRIMARY BUTTON
    // =========================================================

    private Button createPrimaryButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.setPrefHeight(42);

        button.setStyle(
                "-fx-background-color: " +
                        BLUE + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 7;" +
                "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(
                e -> button.setStyle(
                        "-fx-background-color: " +
                                BLUE_DARK + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 7;" +
                        "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(
                e -> button.setStyle(
                        "-fx-background-color: " +
                                BLUE + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 7;" +
                        "-fx-cursor: hand;"
                )
        );

        return button;
    }

    // =========================================================
    // DIVIDER
    // =========================================================

    private Region createDivider() {

        Region divider =
                new Region();

        divider.setPrefHeight(1);

        divider.setMaxWidth(
                Double.MAX_VALUE
        );

        divider.setStyle(
                "-fx-background-color: " +
                        DIVIDER + ";"
        );

        return divider;
    }

    // =========================================================
    // SCROLL PANE
    // =========================================================

    private ScrollPane createMainScrollPane(
            VBox content
    ) {

        ScrollPane scrollPane =
                new ScrollPane(content);

        scrollPane.setFitToWidth(true);

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scrollPane.setPannable(true);

        scrollPane.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background: " +
                        BACKGROUND + ";"
        );

        return scrollPane;
    }

    // =========================================================
    // FILE / MESSAGE
    // =========================================================

    private void showMessage(
            String message
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle(
                "LifeLink"
        );

        alert.setHeaderText(null);

        alert.setContentText(
                message
        );

        alert.showAndWait();
    }




    
    // =========================================================
    // ANIMATION
    // =========================================================

    // private void playPageAnimation(
    //         Node node
    // ) {

    //     node.setOpacity(0);

    //     FadeTransition fade =
    //             new FadeTransition(
    //                     Duration.millis(300),
    //                     node
    //             );

    //     fade.setFromValue(0.3);

    //     fade.setToValue(1);

    //     ScaleTransition scale =
    //             new ScaleTransition(
    //                     Duration.millis(250),
    //                     node
    //             );

    //     scale.setFromX(0.985);
    //     scale.setFromY(0.985);

    //     scale.setToX(1);
    //     scale.setToY(1);

    //     ParallelTransition animation =
    //             new ParallelTransition(
    //                     fade,
    //                     scale
    //             );

    //     animation.play();
    // }






    // =========================================================
    // CARD ANIMATION
    // =========================================================

    private void animateNode(
            Node node
    ) {

        ScaleTransition scale =
                new ScaleTransition(
                        Duration.millis(130),
                        node
                );

        scale.setFromX(1);
        scale.setFromY(1);

        scale.setToX(1.02);
        scale.setToY(1.02);

        scale.setAutoReverse(true);

        scale.setCycleCount(2);

        scale.play();
    }
}