package com.kurukshetra.view.driver;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DriverDetailsView extends Application {

    private static final String PRIMARY_COLOR = "#0056b3";
    private static final String BACKGROUND_COLOR = "#f4f7f6";
    private static final String CARD_BG = "#ffffff";
    private static final String TEXT_MAIN = "#333333";
    private static final String TEXT_MUTED = "#666666";
    private static final String BORDER_COLOR = "#e0e0e0";

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        // Main Content Area
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30, 40, 30, 40));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setMaxWidth(900); // Max width for content

        // Header Section
        VBox header = createHeaderSection();
        mainContent.getChildren().add(header);

        // Personal Information Card
        VBox personalInfoCard = createCard("Personal Information", createPersonIcon());
        GridPane personalGrid = createFormGrid();
        
        TextField fullNameField = createStyledTextField("e.g. John Doe");
        TextField phoneField = createStyledTextField("+1 (555) 000-0000");
        
        personalGrid.add(createLabelWithAsterisk("Full Name"), 0, 0);
        personalGrid.add(fullNameField, 0, 1);
        personalGrid.add(createLabelWithAsterisk("Phone Number"), 1, 0);
        personalGrid.add(phoneField, 1, 1);
        
        personalInfoCard.getChildren().add(personalGrid);
        mainContent.getChildren().add(personalInfoCard);

        // Driving Information Card
        VBox drivingInfoCard = createCard("Driving Information", createCarIcon());
        
        // License Number
        VBox licenseNumBox = new VBox(5);
        licenseNumBox.getChildren().addAll(createLabelWithAsterisk("Driving License Number"), createStyledTextField("Enter license number"));
        drivingInfoCard.getChildren().add(licenseNumBox);
        
        // Upload Component
        VBox uploadSection = new VBox(10);
        uploadSection.getChildren().add(createLabelWithAsterisk("License Upload"));
        uploadSection.getChildren().add(createUploadArea());
        drivingInfoCard.getChildren().add(uploadSection);
        
        // Ambulance Assignment
        GridPane ambulanceGrid = createFormGrid();
        
        TextField ambulanceIdField = createStyledTextField("e.g. AMB-104");
        ComboBox<String> hospitalCombo = createStyledComboBox("Select assigned hospital", 
                "LifeLink General Hospital", "St. Jude's Medical Center", 
                "Northern District Hospital", "City Emergency Hospital");
        
        ambulanceGrid.add(createLabel("Ambulance ID (Optional)"), 0, 0);
        ambulanceGrid.add(ambulanceIdField, 0, 1);
        ambulanceGrid.add(createLabel("Assigned Hospital"), 1, 0);
        ambulanceGrid.add(hospitalCombo, 1, 1);
        
        drivingInfoCard.getChildren().add(ambulanceGrid);
        mainContent.getChildren().add(drivingInfoCard);

        // Emergency Contact Card
        VBox emergencyContactCard = createCard("Emergency Contact", createEmergencyIcon());
        VBox emergencyPhoneBox = new VBox(5);
        emergencyPhoneBox.getChildren().addAll(createLabelWithAsterisk("Emergency Contact Number"), createStyledTextField("(555) 000-0000"));
        emergencyContactCard.getChildren().add(emergencyPhoneBox);
        mainContent.getChildren().add(emergencyContactCard);

        // Bottom Action Area
        HBox bottomActionArea = createBottomActionArea();
        mainContent.getChildren().add(bottomActionArea);

        // Wrap main content in a StackPane to center it horizontally
        StackPane contentWrapper = new StackPane(mainContent);
        contentWrapper.setAlignment(Pos.TOP_CENTER);
        
        ScrollPane scrollPane = new ScrollPane(contentWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.getStyleClass().add("edge-to-edge"); // To remove border
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1024, 768);
        
        // Simple inline CSS for basic fixes
        scene.getStylesheets().add("data:text/css," +
                ".scroll-pane { -fx-background-insets: 0; -fx-padding: 0; }" +
                ".scroll-pane > .viewport { -fx-background-color: transparent; }" +
                ".combo-box { -fx-background-color: white; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 4; -fx-background-radius: 4; }" +
                ".combo-box .list-cell { -fx-background-color: white; -fx-text-fill: " + TEXT_MAIN + "; }" +
                ".combo-box:hover { -fx-border-color: " + PRIMARY_COLOR + "; }" +
                ".combo-box-popup .list-view { -fx-background-color: white; -fx-border-color: " + BORDER_COLOR + "; }" +
                ".combo-box-popup .list-view .list-cell:hover { -fx-background-color: #f0f8ff; }"
        );

        primaryStage.setTitle("LifeLink - Driver Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createTopBranding() {
        VBox branding = new VBox(5);
        branding.setAlignment(Pos.CENTER);
        branding.setPadding(new Insets(15, 0, 15, 0));
        branding.setStyle("-fx-background-color: " + CARD_BG + "; -fx-border-width: 0 0 1 0; -fx-border-color: " + BORDER_COLOR + ";");
        
        HBox logoBox = new HBox(8);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.getChildren().addAll(createMedicalIcon(), createText("LifeLink", PRIMARY_COLOR, 20, FontWeight.BOLD));
        
        Text subText = createText("HOSPITAL MANAGEMENT", TEXT_MUTED, 10, FontWeight.SEMI_BOLD);

        
        branding.getChildren().addAll(logoBox, subText);
        return branding;
    }

    private VBox createHeaderSection() {
        VBox header = new VBox(25);
        header.setAlignment(Pos.TOP_CENTER);
        
        // Branding Block
        HBox branding = new HBox(12);
        branding.setAlignment(Pos.CENTER);
        
        StackPane iconPane = new StackPane();
        iconPane.setPrefSize(42, 42);
        iconPane.setMinSize(42, 42);
        iconPane.setStyle("-fx-background-color: " + PRIMARY_COLOR + "; -fx-background-radius: 8;");
        SVGPath cross = createIcon("M8 2h4v6h6v4h-6v6H8v-6H2V8h6V2z", "#ffffff", 1.0);
        iconPane.getChildren().add(cross);
        
        VBox textBox = new VBox(2);
        textBox.setAlignment(Pos.CENTER_LEFT);
        textBox.getChildren().addAll(
            createText("LifeLink", PRIMARY_COLOR, 24, FontWeight.EXTRA_BOLD),
            createText("HOSPITAL MANAGEMENT", TEXT_MUTED, 11, FontWeight.BOLD)
        );
        
        branding.getChildren().addAll(iconPane, textBox);
        
        // Titles Block
        VBox titles = new VBox(5);
        titles.setAlignment(Pos.CENTER_LEFT);
        HBox titleWithIcon = new HBox(10);
        titleWithIcon.setAlignment(Pos.CENTER_LEFT);
        titleWithIcon.getChildren().addAll(createDriverIconLarge(), createText("Driver Information", TEXT_MAIN, 24, FontWeight.BOLD));
        
        Text subtitle = createText("Enter your driving and emergency contact details", TEXT_MUTED, 14);
        titles.getChildren().addAll(titleWithIcon, subtitle);
        
        header.getChildren().addAll(branding, titles);
        return header;
    }

    private VBox createCard(String title, SVGPath icon) {
        VBox card = new VBox(20);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: " + CARD_BG + "; " +
                      "-fx-border-color: " + BORDER_COLOR + "; " +
                      "-fx-border-radius: 8; " +
                      "-fx-background-radius: 8; " +
                      "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 10, 0, 0, 2);");
        
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getChildren().addAll(icon, createText(title, TEXT_MAIN, 16, FontWeight.BOLD));
        
        Line separator = new Line(0, 0, 500, 0);
        separator.setStroke(Color.web(BORDER_COLOR));
        separator.getStrokeDashArray().addAll(2d, 2d);
        
        Pane linePane = new Pane(separator);
        linePane.setMinHeight(1);
        separator.endXProperty().bind(linePane.widthProperty());
        
        card.getChildren().addAll(header, linePane);
        return card;
    }
    
    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(10);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);
        return grid;
    }

    private VBox createUploadArea() {
        VBox uploadBox = new VBox(10);
        uploadBox.setAlignment(Pos.CENTER);
        uploadBox.setPadding(new Insets(30));
        uploadBox.setStyle("-fx-background-color: #fafbfc; " +
                           "-fx-border-color: #c0cddb; " +
                           "-fx-border-style: dashed; " +
                           "-fx-border-width: 1.5; " +
                           "-fx-border-radius: 8; " +
                           "-fx-background-radius: 8;");
        
        StackPane iconBg = new StackPane();
        iconBg.setPrefSize(50, 50);
        iconBg.setMaxSize(50, 50);
        iconBg.setStyle("-fx-background-color: #eef2f7; -fx-background-radius: 25;");
        iconBg.getChildren().add(createUploadIcon());
        
        Text mainText = createText("Upload a clear copy of your driving license", TEXT_MAIN, 15, FontWeight.BOLD);
        Text subText = createText("Drag & drop your document here, or browse files", TEXT_MUTED, 13);
        Text formats = createText("Supported formats: PDF, JPG, PNG", TEXT_MUTED, 12);
        
        Button selectBtn = new Button("SELECT FILE");
        selectBtn.setStyle("-fx-background-color: transparent; " +
                           "-fx-text-fill: " + PRIMARY_COLOR + "; " +
                           "-fx-border-color: " + PRIMARY_COLOR + "; " +
                           "-fx-border-radius: 4; " +
                           "-fx-padding: 8 20 8 20; " +
                           "-fx-font-weight: bold; " +
                           "-fx-cursor: hand;");
                           
        selectBtn.setOnAction(e -> {
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Select License Document");
            fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("Supported Files", "*.pdf", "*.jpg", "*.png")
            );
            java.io.File selectedFile = fileChooser.showOpenDialog(selectBtn.getScene().getWindow());
            if (selectedFile != null) {
                subText.setText("Selected file: " + selectedFile.getName());
                subText.setFill(Color.web(PRIMARY_COLOR));
                iconBg.setStyle("-fx-background-color: #e6f9e6; -fx-background-radius: 25;");
                formats.setText("File size: " + (selectedFile.length() / 1024) + " KB");
            }
        });
        
        uploadBox.getChildren().addAll(iconBg, mainText, subText, formats, selectBtn);
        return uploadBox;
    }

    private HBox createDriverVerificationBanner() {
        HBox banner = new HBox(12);
        banner.setAlignment(Pos.CENTER_LEFT);
        banner.setPadding(new Insets(12, 15, 12, 15));
        banner.setStyle("-fx-background-color: " + CARD_BG + "; " +
                        "-fx-border-color: #e6e6e6; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6;");
        
        banner.getChildren().addAll(
            createShieldIcon(),
            new VBox(2, 
                createText("Driver Verification", TEXT_MAIN, 13, FontWeight.BOLD),
                createText("Your license and ambulance information help maintain a verified emergency transport team.", TEXT_MUTED, 12)
            )
        );
        return banner;
    }

    private HBox createEmergencyResponseBanner() {
        HBox banner = new HBox(12);
        banner.setAlignment(Pos.CENTER_LEFT);
        banner.setPadding(new Insets(12, 15, 12, 15));
        banner.setStyle("-fx-background-color: #f0f7ff; " +
                        "-fx-border-color: #cce0ff; " +
                        "-fx-border-radius: 6; " +
                        "-fx-background-radius: 6;");
        
        banner.getChildren().addAll(
            createAmbulanceCalloutIcon(),
            new VBox(2, 
                createText("Emergency transport matters", PRIMARY_COLOR, 13, FontWeight.BOLD),
                createText("Accurate driver and ambulance information helps hospitals coordinate emergency response faster.", "#4a6c92", 12)
            )
        );
        return banner;
    }

    private HBox createBottomActionArea() {
        HBox actionArea = new HBox();
        actionArea.setAlignment(Pos.CENTER);
        actionArea.setPadding(new Insets(20, 0, 20, 0));
        actionArea.setStyle("-fx-background-color: transparent;");
        
        Button createBtn = new Button("Create Account →");
        createBtn.setStyle("-fx-background-color: " + PRIMARY_COLOR + "; " +
                           "-fx-text-fill: white; " +
                           "-fx-font-size: 14px; " +
                           "-fx-font-weight: bold; " +
                           "-fx-padding: 12 25 12 25; " +
                           "-fx-background-radius: 4; " +
                           "-fx-cursor: hand;");
        createBtn.setPrefHeight(45);
        
        actionArea.getChildren().addAll(createBtn);
        return actionArea;
    }

    private Label createLabelWithAsterisk(String text) {
        Label lbl = new Label(text + " *");
        lbl.setTextFill(Color.web(TEXT_MAIN));
        lbl.setFont(Font.font("System", FontWeight.SEMI_BOLD, 13));
        return lbl;
    }
    
    private Label createLabel(String text) {
        Label lbl = new Label(text);
        lbl.setTextFill(Color.web(TEXT_MAIN));
        lbl.setFont(Font.font("System", FontWeight.SEMI_BOLD, 13));
        return lbl;
    }

    private TextField createStyledTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefHeight(45);
        tf.setStyle("-fx-background-color: white; " +
                    "-fx-border-color: " + BORDER_COLOR + "; " +
                    "-fx-border-radius: 4; " +
                    "-fx-background-radius: 4; " +
                    "-fx-padding: 5 10 5 10; " +
                    "-fx-font-size: 14px;");
        
        tf.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                tf.setStyle("-fx-background-color: white; " +
                            "-fx-border-color: " + PRIMARY_COLOR + "; " +
                            "-fx-border-radius: 4; " +
                            "-fx-background-radius: 4; " +
                            "-fx-padding: 5 10 5 10; " +
                            "-fx-font-size: 14px;");
            } else {
                tf.setStyle("-fx-background-color: white; " +
                            "-fx-border-color: " + BORDER_COLOR + "; " +
                            "-fx-border-radius: 4; " +
                            "-fx-background-radius: 4; " +
                            "-fx-padding: 5 10 5 10; " +
                            "-fx-font-size: 14px;");
            }
        });
        return tf;
    }

    private ComboBox<String> createStyledComboBox(String prompt, String... items) {
        ComboBox<String> cb = new ComboBox<>();
        cb.setPromptText(prompt);
        cb.getItems().addAll(items);
        cb.setPrefHeight(45);
        cb.setMaxWidth(Double.MAX_VALUE);
        return cb;
    }

    private Text createText(String content, String colorHex, double size) {
        return createText(content, colorHex, size, FontWeight.NORMAL);
    }

    private Text createText(String content, String colorHex, double size, FontWeight weight) {
        Text t = new Text(content);
        t.setFill(Color.web(colorHex));
        t.setFont(Font.font("System", weight, size));
        return t;
    }
    
    private SVGPath createIcon(String pathContent, String fill, double scale) {
        SVGPath path = new SVGPath();
        path.setContent(pathContent);
        path.setFill(Color.web(fill));
        path.setScaleX(scale);
        path.setScaleY(scale);
        return path;
    }

    private SVGPath createMedicalIcon() {
        return createIcon("M8 2h4v6h6v4h-6v6H8v-6H2V8h6V2z", PRIMARY_COLOR, 0.7);
    }
    
    private SVGPath createDriverIconLarge() {
        return createIcon("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z", PRIMARY_COLOR, 1.2);
    }

    private SVGPath createPersonIcon() {
        return createIcon("M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z", TEXT_MAIN, 0.9);
    }

    private SVGPath createCarIcon() {
        return createIcon("M18.92 6.01C18.72 5.42 18.16 5 17.5 5h-11c-.66 0-1.21.42-1.42 1.01L3 12v8c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h12v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-8l-2.08-5.99zM6.5 16c-.83 0-1.5-.67-1.5-1.5S5.67 13 6.5 13s1.5.67 1.5 1.5S7.33 16 6.5 16zm11 0c-.83 0-1.5-.67-1.5-1.5s.67-1.5 1.5-1.5 1.5.67 1.5 1.5-.67 1.5-1.5 1.5zM5 11l1.5-4.5h11L19 11H5z", TEXT_MAIN, 0.9);
    }

    private SVGPath createEmergencyIcon() {
        return createIcon("M20.01 15.38c-1.23 0-2.42-.2-3.53-.56-.35-.12-.74-.03-1.01.24l-1.57 1.97c-2.83-1.35-5.48-3.9-6.89-6.83l1.95-1.66c.27-.28.35-.67.24-1.02-.37-1.11-.56-2.3-.56-3.53 0-.54-.45-.99-.99-.99H4.19C3.65 3 3 3.24 3 3.99 3 13.28 10.73 21 20.01 21c.71 0 .99-.63.99-1.18v-3.45c0-.54-.45-.99-.99-.99z", TEXT_MAIN, 0.9);
    }

    private SVGPath createUploadIcon() {
        return createIcon("M9 16h6v-6h4l-7-7-7 7h4zm-4 2h14v2H5z", PRIMARY_COLOR, 1.2);
    }

    private SVGPath createShieldIcon() {
        return createIcon("M12 1L3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4zm-2 16l-4-4 1.41-1.41L10 14.17l6.59-6.59L18 9l-8 8z", "#4caf50", 0.8);
    }
    
    private SVGPath createAmbulanceCalloutIcon() {
        return createIcon("M19 11.5s-2 0-2-3V6c0-1.1-.9-2-2-2h-3v-1c0-.55-.45-1-1-1H9c-.55 0-1 .45-1 1v1H5c-1.1 0-2 .9-2 2v6H2v3h2v3c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2v-3h2v-3h-1.08z", PRIMARY_COLOR, 0.8);
    }

    private SVGPath createLockIcon() {
        return createIcon("M18 8h-1V6c0-2.76-2.24-5-5-5S7 3.24 7 6v2H6c-1.1 0-2 .9-2 2v10c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V10c0-1.1-.9-2-2-2zm-6 9c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2zm3.1-9H8.9V6c0-1.71 1.39-3.1 3.1-3.1 1.71 0 3.1 1.39 3.1 3.1v2z", TEXT_MUTED, 0.7);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
