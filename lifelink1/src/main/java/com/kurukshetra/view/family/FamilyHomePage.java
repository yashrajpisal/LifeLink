
// package com.kurukshetra.view.family;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;
// import javafx.stage.Stage;

// public class FamilyHomePage {

//     // ---------- DUMMY / STATIC DATA (replace with backend later) ----------
//     private String[][] familyMembers = {
//             // name, relation, status, status-css-class
//             {"Arjun Sharma", "Brother", "Healthy", "status-healthy"},
//             {"Maya Miller", "Mother", "Monitoring", "status-monitoring"}
//     };

//     private String[][] nearbyCare = {
//             // name, distance, wait time
//             {"CityCare General", "2.4 mi", "5 min wait"},
//             {"Apollo Medical Center", "4.3 mi", "12 min wait"}
//     };
//     // ------------------------------------------------------------------

//     public BorderPane setBorderPane(Stage stage) {

//         BorderPane bp = new BorderPane();
//         bp.getStyleClass().add("root-pane");

//         VBox sidebar = Sidebar.build(stage, Sidebar.Page.DASHBOARD);
//         bp.setLeft(sidebar);

//         VBox mainContent = buildMainContent(stage);
//         ScrollPane scrollPane = new ScrollPane(mainContent);
//         scrollPane.setFitToWidth(true);
//         scrollPane.getStyleClass().add("main-scroll");
//         bp.setCenter(scrollPane);

//         MedicalReports.playPageAnimation(scrollPane);

//         return bp;
//     }

//     // ---------------- MAIN CONTENT ----------------
//     private VBox buildMainContent(Stage stage) {

//         HBox topBar = buildTopBar();

//         Text greeting = new Text("Good morning, Sarah");
//         greeting.getStyleClass().add("greeting-text");

//         Label subGreeting = new Label("How can LifeLink help you today?");
//         subGreeting.getStyleClass().add("sub-greeting-label");

//         VBox emergencyCard = buildEmergencyCard(stage);
//         VBox familyHubCard = buildFamilyHubCard();

//         HBox row1 = new HBox(20, emergencyCard, familyHubCard);
//         HBox.setHgrow(emergencyCard, Priority.ALWAYS);

//         VBox quickActionsGrid = buildQuickActionsGrid(stage);
//         VBox nearbyCareCard = buildNearbyCareCard();

//         HBox row2 = new HBox(20, quickActionsGrid, nearbyCareCard);
//         HBox.setHgrow(quickActionsGrid, Priority.ALWAYS);

//         // VBox chatCard = buildChatAssistantCard();

//         VBox mainContent = new VBox(20, topBar, greeting, subGreeting, row1, row2);
//         mainContent.getStyleClass().add("main-content");
//         mainContent.setPadding(new Insets(24));

//         return mainContent;
//     }

//     private HBox buildTopBar() {
//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         Label bell = new Label("🔔");
//         Label help = new Label("❓");
//         bell.getStyleClass().add("icon-label");
//         help.getStyleClass().add("icon-label");

//         Label name = new Label("Sarah Miller");
//         Label role = new Label("Family Care Lead");
//         name.getStyleClass().add("profile-name");
//         role.getStyleClass().add("profile-role");
//         VBox profileText = new VBox(name, role);

//         Label avatar = new Label("👩");
//         avatar.getStyleClass().add("avatar-label");

//         HBox profileBox = new HBox(10, profileText, avatar);
//         profileBox.setAlignment(Pos.CENTER_RIGHT);

//         HBox topBar = new HBox(20, spacer, bell, help, profileBox);
//         topBar.setAlignment(Pos.CENTER_RIGHT);
//         return topBar;
//     }

//     private VBox buildEmergencyCard(Stage stage) {
//         Label tag = new Label("⚠ IMMEDIATE ASSISTANCE");
//         tag.getStyleClass().add("emergency-tag");

//         Label title = new Label("Need urgent medical help?");
//         title.getStyleClass().add("card-title-large");

//         Label desc = new Label(
//                 "If you or a family member are experiencing a medical\n" +
//                 "emergency, request help immediately or find the nearest\n" +
//                 "critical care facility.");
//         desc.getStyleClass().add("card-desc");

//         Button emergencyHelpBtn = new Button("📞  Emergency Help");
//         Button findHospitalsBtn = new Button("📍  Find Nearby Hospitals");
//         emergencyHelpBtn.getStyleClass().add("primary-red-btn");
//         findHospitalsBtn.getStyleClass().add("primary-dark-btn");

//         // Connect these two buttons straight into the Emergency Services
//         // and Find Care pages.
//         emergencyHelpBtn.setOnAction(event -> {
//             EmergencyServices emergencyServices = new EmergencyServices();
//             stage.getScene().setRoot(emergencyServices.setBorderPane(stage));
//         });

//         findHospitalsBtn.setOnAction(event -> {
//             FamilyFindCare findCare = new FamilyFindCare();
//             stage.getScene().setRoot(findCare.setBorderPane(stage));
//         });

//         HBox btnRow = new HBox(12, emergencyHelpBtn, findHospitalsBtn);

//         VBox card = new VBox(10, tag, title, desc, btnRow);
//         card.getStyleClass().addAll("card", "emergency-card");
//         card.setPadding(new Insets(20));           
//         return card;
//     }

//     private VBox buildFamilyHubCard() {
//         Label title = new Label("Family Hub");
//         title.getStyleClass().add("card-title");

//         Label addBtn = new Label("+");
//         addBtn.getStyleClass().add("add-icon");

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);
//         HBox header = new HBox(title, spacer, addBtn);
//         header.setAlignment(Pos.CENTER_LEFT);

//         VBox membersBox = new VBox(12);
//         for (String[] member : familyMembers) {
//             membersBox.getChildren().add(buildFamilyMemberRow(member[0], member[1], member[2], member[3]));
//         }

//         VBox card = new VBox(16, header, membersBox);
//         card.getStyleClass().add("card");
//         card.setPadding(new Insets(20));
//         card.setPrefWidth(280);
//         return card;
//     }

//     private HBox buildFamilyMemberRow(String name, String relation, String status, String statusStyleClass) {
//         Label avatar = new Label("🧑");
//         avatar.getStyleClass().add("avatar-label");

//         Label nameLbl = new Label(name);
//         Label relationLbl = new Label(relation);
//         nameLbl.getStyleClass().add("member-name");
//         relationLbl.getStyleClass().add("member-relation");
//         VBox textBox = new VBox(nameLbl, relationLbl);

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         Label statusLbl = new Label(status);
//         statusLbl.getStyleClass().addAll("status-pill", statusStyleClass);

//         HBox row = new HBox(10, avatar, textBox, spacer, statusLbl);
//         row.setAlignment(Pos.CENTER_LEFT);
//         return row;
//     }

//     private VBox buildQuickActionsGrid(Stage stage) {
//         Button firstAid = makeQuickActionCard("🩹", "First Aid");
//         Button myHealth = makeQuickActionCard("💗", "My Health");
//         Button savedPlaces = makeQuickActionCard("🔖", "Saved Places");

//         // Wire the tiles that map onto existing pages.
//         firstAid.setOnAction(e -> stage.getScene().setRoot(new FirstAidAssistant().setBorderPane(stage)));
//         savedPlaces.setOnAction(e -> stage.getScene().setRoot(new SavedHospitals().setBorderPane(stage)));
//         myHealth.setOnAction(event -> { System.out.println("enter myhealth button");});
//         VBox chatCard = buildChatAssistantCard();

//         HBox rowOne = new HBox(16, firstAid,myHealth, savedPlaces);
//         HBox rowTwo = new HBox(chatCard);
        

//         firstAid.setMaxWidth(Double.MAX_VALUE);
//         myHealth.setMaxWidth(Double.MAX_VALUE);
//         savedPlaces.setMaxWidth(Double.MAX_VALUE);

//         HBox.setHgrow(firstAid, Priority.ALWAYS);
//         HBox.setHgrow(myHealth, Priority.ALWAYS);
//         HBox.setHgrow(savedPlaces, Priority.ALWAYS);
    

//         VBox grid = new VBox(16, rowOne,rowTwo);       
//         return grid;
//     }

//     private Button makeQuickActionCard(String icon, String text) {
//         Label iconLbl = new Label(icon);
//         iconLbl.getStyleClass().add("quick-action-icon");

//         Label textLbl = new Label(text);
//         textLbl.getStyleClass().add("quick-action-text");

//         VBox box = new VBox(8, iconLbl, textLbl);
//         box.setAlignment(Pos.CENTER);

//         Button card = new Button();
//         card.setGraphic(box);
//         card.getStyleClass().add("quick-action-card");
//         return card;
//     }

//     private VBox buildNearbyCareCard() {
//         Label title = new Label("Nearby Care");
//         title.getStyleClass().add("card-title");

//         Label viewMap = new Label("View Map");
//         viewMap.getStyleClass().add("link-label");

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);
//         HBox header = new HBox(title, spacer, viewMap);
//         header.setAlignment(Pos.CENTER_LEFT);

//         Label mapPreview = new Label("🗺");
//         mapPreview.getStyleClass().add("mini-map-preview");
//         mapPreview.setMaxWidth(Double.MAX_VALUE);
//         mapPreview.setAlignment(Pos.CENTER);

//         VBox listBox = new VBox(10);
//         for (String[] place : nearbyCare) {
//             listBox.getChildren().add(buildNearbyCareRow(place[0], place[1], place[2]));
//         }

//         VBox card = new VBox(14, header, mapPreview, listBox);
//         card.getStyleClass().add("card");
//         card.setPadding(new Insets(20));
//         card.setPrefWidth(280);
//         return card;
//     }

//     private HBox buildNearbyCareRow(String name, String distance, String wait) {
//         Label icon = new Label("✳");
//         icon.getStyleClass().add("small-icon");

//         Label nameLbl = new Label(name);
//         nameLbl.getStyleClass().add("member-name");

//         Label infoLbl = new Label(distance + "  •  " + wait);
//         infoLbl.getStyleClass().add("member-relation");

//         VBox textBox = new VBox(nameLbl, infoLbl);
//         HBox row = new HBox(10, icon, textBox);
//         row.setAlignment(Pos.CENTER_LEFT);
//         return row;
//     }

//     private VBox buildChatAssistantCard() {
//         Label icon = new Label("💬");
//         icon.getStyleClass().add("chat-icon");

//         Label title = new Label("LifeLink Care Assistant");
//         title.getStyleClass().add("card-title");

//         Label subtitle = new Label("AI-powered health guidance");
//         subtitle.getStyleClass().add("card-desc");
//         VBox titleBox = new VBox(title, subtitle);

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         Label openChat = new Label("Open Chat");
//         openChat.getStyleClass().add("link-label");

//         HBox header = new HBox(10, icon, titleBox, spacer, openChat);
//         header.setAlignment(Pos.CENTER_LEFT);

//         Label chatBubble = new Label(
//                 "Hello Sarah, I'm here to help you navigate your family's health\nneeds today. What's on your mind?");
//         chatBubble.getStyleClass().add("chat-bubble");
//         chatBubble.setWrapText(true);

//         Button firstAidInfo = new Button("🩹 First Aid Info");
//         Button checkSymptoms = new Button("💗 Check Symptoms");
//         Button isEmergency = new Button("⚠ Is This an Emergency?");
//         firstAidInfo.getStyleClass().add("chip-btn");
//         checkSymptoms.getStyleClass().add("chip-btn");
//         isEmergency.getStyleClass().add("chip-btn");

//         HBox chipRow = new HBox(10, firstAidInfo, checkSymptoms, isEmergency);

//         VBox card = new VBox(14, header, chatBubble, chipRow);
//         card.getStyleClass().add("card");
//         card.setPadding(new Insets(20));
//         card.setPrefWidth(1000);
//         card.setPrefHeight(220);
//         return card;
//     }
// }



package com.kurukshetra.view.family;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import javafx.stage.Stage;

public class FamilyHomePage extends Application {

    // ---------- COLOR CONSTANTS ----------
    private static final String PRIMARY = "#CA6721";
    private static final String PRIMARY_DARK = "#782F16";
    private static final String VERY_LIGHT_TERRACOTTA = "#FDF0E8";
    private static final String LIGHT_TERRACOTTA = "#F9E4D6";
    private static final String PALE_PEACH = "#FEF7F2";
    private static final String PAGE_BG = "#FCF9F7";
    private static final String SURFACE = "#FFFFFF";
    private static final String TEXT_PRIMARY = "#2B211D";
    private static final String TEXT_SECONDARY = "#675B55";
    private static final String TEXT_MUTED = "#938780";
    private static final String BORDER_COLOR = "#EDE0D8";
    private static final String HOVER_BG = "#FFF5EF";
    private static final String DANGER_BG = "#FCE8E7";
    private static final String DANGER_TEXT = "#C94F4F";

    public static Stage mainStage;

    // ---------- DUMMY / STATIC DATA ----------
    private String[][] familyMembers = {
            {"Arjun Sharma", "Brother", "Healthy", "status-healthy"},
            {"Maya Miller", "Mother", "Monitoring", "status-monitoring"}
    };

    private String[][] nearbyCare = {
            {"CityCare General", "2.4 mi", "5 min wait"},
            {"Apollo Medical Center", "4.3 mi", "12 min wait"}
    };

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        BorderPane bp = setBorderPane(stage);
        Scene scene = new Scene(bp, mainStage.getWidth(), mainStage.getHeight());
        stage.setTitle("LifeLink - Family Care");
        mainStage.setMaximized(true);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public BorderPane setBorderPane(Stage stage) {
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: " + PAGE_BG + ";");

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.DASHBOARD);
        bp.setLeft(sidebar);

        VBox mainContent = buildMainContent(stage);
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle(
                "-fx-background: " + PAGE_BG + ";" +
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );
        bp.setCenter(scrollPane);

        MedicalReports.playPageAnimation(scrollPane);
        return bp;
    }

    // ---------------- MAIN CONTENT ----------------
    private VBox buildMainContent(Stage stage) {

        Text greeting = new Text("Good morning, Sarah");
        greeting.setFont(Font.font("System", FontWeight.BOLD, 26));
        greeting.setFill(Color.web(TEXT_PRIMARY));

        Label subGreeting = new Label("How can LifeLink help you today?");
        subGreeting.setFont(Font.font("System", FontWeight.NORMAL, 14));
        subGreeting.setStyle("-fx-text-fill: " + TEXT_SECONDARY + ";");

        VBox emergencyCard = buildEmergencyCard(stage);
        VBox familyHubCard = buildFamilyHubCard();

        HBox row1 = new HBox(20, emergencyCard, familyHubCard);
        HBox.setHgrow(emergencyCard, Priority.ALWAYS);

        VBox quickActionsGrid = buildQuickActionsGrid(stage);
        VBox nearbyCareCard = buildNearbyCareCard();

        HBox row2 = new HBox(20, quickActionsGrid, nearbyCareCard);
        HBox.setHgrow(quickActionsGrid, Priority.ALWAYS);

        VBox mainContent = new VBox(20, greeting, subGreeting, row1, row2);
        mainContent.setStyle("-fx-background-color: " + PAGE_BG + ";");
        mainContent.setPadding(new Insets(24));

        return mainContent;
    }

    private VBox buildEmergencyCard(Stage stage) {
        Label tag = new Label("⚠ IMMEDIATE ASSISTANCE");
        tag.setFont(Font.font("System", FontWeight.BOLD, 11));
        tag.setStyle(
                "-fx-background-color: " + DANGER_BG + ";" +
                "-fx-text-fill: " + DANGER_TEXT + ";" +
                "-fx-padding: 4px 10px;" +
                "-fx-background-radius: 12px;"
        );

        Label title = new Label("Need urgent medical help?");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label desc = new Label(
                "If you or a family member are experiencing a medical\n" +
                "emergency, request help immediately or find the nearest\n" +
                "critical care facility.");
        desc.setFont(Font.font("System", FontWeight.NORMAL, 13));
        desc.setStyle("-fx-text-fill: " + TEXT_SECONDARY + "; -fx-line-spacing: 2px;");

        Button emergencyHelpBtn = new Button("📞  Emergency Help");
        styleDangerButton(emergencyHelpBtn);

        Button findHospitalsBtn = new Button("📍  Find Nearby Hospitals");
        styleSecondaryActionButton(findHospitalsBtn);

        emergencyHelpBtn.setOnAction(event -> {
            EmergencyServices emergencyServices = new EmergencyServices();
            stage.getScene().setRoot(emergencyServices.setBorderPane(stage));
        });

        findHospitalsBtn.setOnAction(event -> {
            FamilyFindCare findCare = new FamilyFindCare();
            stage.getScene().setRoot(findCare.setBorderPane(stage));
        });

        HBox btnRow = new HBox(12, emergencyHelpBtn, findHospitalsBtn);

        VBox card = new VBox(12, tag, title, desc, btnRow);
        applyCardStyle(card, VERY_LIGHT_TERRACOTTA, BORDER_COLOR);
        card.setPadding(new Insets(22));
        return card;
    }

    private VBox buildFamilyHubCard() {
        Label title = new Label("Family Hub");
        title.setFont(Font.font("System", FontWeight.BOLD, 18));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label addBtn = new Label("+");
        addBtn.setFont(Font.font("System", FontWeight.BOLD, 16));
        addBtn.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-padding: 2px 10px;" +
                "-fx-background-radius: 8px;" +
                "-fx-cursor: hand;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox header = new HBox(title, spacer, addBtn);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox membersBox = new VBox(12);
        for (String[] member : familyMembers) {
            membersBox.getChildren().add(buildFamilyMemberRow(member[0], member[1], member[2], member[3]));
        }

        VBox card = new VBox(16, header, membersBox);
        applyCardStyle(card, SURFACE, BORDER_COLOR);
        card.setPadding(new Insets(20));
        card.setPrefWidth(280);
        return card;
    }

    private HBox buildFamilyMemberRow(String name, String relation, String status, String statusStyleClass) {
        Label avatar = new Label("🧑");
        avatar.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-padding: 6px;" +
                "-fx-background-radius: 16px;" +
                "-fx-font-size: 14px;"
        );

        Label nameLbl = new Label(name);
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        nameLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label relationLbl = new Label(relation);
        relationLbl.setFont(Font.font("System", FontWeight.NORMAL, 12));
        relationLbl.setStyle("-fx-text-fill: " + TEXT_MUTED + ";");

        VBox textBox = new VBox(2, nameLbl, relationLbl);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label statusLbl = new Label(status);
        statusLbl.setFont(Font.font("System", FontWeight.BOLD, 11));
        applyStatusBadgeStyle(statusLbl, status);

        HBox row = new HBox(10, avatar, textBox, spacer, statusLbl);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle(
                "-fx-background-color: " + PALE_PEACH + ";" +
                "-fx-padding: 8px 12px;" +
                "-fx-background-radius: 10px;"
        );
        return row;
    }

    private VBox buildQuickActionsGrid(Stage stage) {
        Button firstAid = makeQuickActionCard("🩹", "First Aid");
        Button myHealth = makeQuickActionCard("💗", "My Health");
        Button savedPlaces = makeQuickActionCard("🔖", "Saved Places");

        firstAid.setOnAction(e -> stage.getScene().setRoot(new FirstAidAssistant().setBorderPane(stage)));
        savedPlaces.setOnAction(e -> stage.getScene().setRoot(new SavedHospitals().setBorderPane(stage)));
        myHealth.setOnAction(event -> System.out.println("enter myhealth button"));
        VBox chatCard = buildChatAssistantCard();

        HBox rowOne = new HBox(16, firstAid, myHealth, savedPlaces);
        HBox rowTwo = new HBox(chatCard);

        firstAid.setMaxWidth(Double.MAX_VALUE);
        myHealth.setMaxWidth(Double.MAX_VALUE);
        savedPlaces.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(firstAid, Priority.ALWAYS);
        HBox.setHgrow(myHealth, Priority.ALWAYS);
        HBox.setHgrow(savedPlaces, Priority.ALWAYS);

        return new VBox(16, rowOne, rowTwo);
    }

    private Button makeQuickActionCard(String icon, String text) {
        Label iconLbl = new Label(icon);
        iconLbl.setStyle("-fx-font-size: 20px;");

        Label textLbl = new Label(text);
        textLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        textLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        VBox box = new VBox(8, iconLbl, textLbl);
        box.setAlignment(Pos.CENTER);

        Button card = new Button();
        card.setGraphic(box);
        card.setPadding(new Insets(16));
        card.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;" +
                "-fx-cursor: hand;"
        );
        card.setEffect(new DropShadow(10, 0, 3, Color.rgb(120, 47, 22, 0.05)));

        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-background-color: " + HOVER_BG + ";" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;" +
                "-fx-cursor: hand;"
        ));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;" +
                "-fx-cursor: hand;"
        ));

        return card;
    }

    private VBox buildNearbyCareCard() {
        Label title = new Label("Nearby Care");
        title.setFont(Font.font("System", FontWeight.BOLD, 18));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label viewMap = new Label("View Map");
        viewMap.setFont(Font.font("System", FontWeight.BOLD, 12));
        viewMap.setStyle("-fx-text-fill: " + PRIMARY + "; -fx-cursor: hand;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox header = new HBox(title, spacer, viewMap);
        header.setAlignment(Pos.CENTER_LEFT);

        Label mapPreview = new Label("🗺");
        mapPreview.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;" +
                "-fx-font-size: 24px;" +
                "-fx-padding: 16px;"
        );
        mapPreview.setMaxWidth(Double.MAX_VALUE);
        mapPreview.setAlignment(Pos.CENTER);

        VBox listBox = new VBox(10);
        for (String[] place : nearbyCare) {
            listBox.getChildren().add(buildNearbyCareRow(place[0], place[1], place[2]));
        }

        VBox card = new VBox(14, header, mapPreview, listBox);
        applyCardStyle(card, SURFACE, BORDER_COLOR);
        card.setPadding(new Insets(20));
        card.setPrefWidth(280);
        return card;
    }

    private HBox buildNearbyCareRow(String name, String distance, String wait) {
        Label icon = new Label("✳");
        icon.setStyle("-fx-text-fill: " + PRIMARY + "; -fx-font-size: 13px;");

        Label nameLbl = new Label(name);
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        nameLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label infoLbl = new Label(distance + "  •  " + wait);
        infoLbl.setFont(Font.font("System", FontWeight.NORMAL, 11));
        infoLbl.setStyle("-fx-text-fill: " + TEXT_MUTED + ";");

        VBox textBox = new VBox(2, nameLbl, infoLbl);
        HBox row = new HBox(10, icon, textBox);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private VBox buildChatAssistantCard() {
        Label icon = new Label("💬");
        icon.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-padding: 8px;" +
                "-fx-background-radius: 10px;" +
                "-fx-font-size: 16px;"
        );

        Label title = new Label("LifeLink Care Assistant");
        title.setFont(Font.font("System", FontWeight.BOLD, 16));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label subtitle = new Label("AI-powered health guidance");
        subtitle.setFont(Font.font("System", FontWeight.NORMAL, 12));
        subtitle.setStyle("-fx-text-fill: " + TEXT_MUTED + ";");
        VBox titleBox = new VBox(2, title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label openChat = new Label("Open Chat");
        openChat.setFont(Font.font("System", FontWeight.BOLD, 12));
        openChat.setStyle("-fx-text-fill: " + PRIMARY + "; -fx-cursor: hand;");

        HBox header = new HBox(10, icon, titleBox, spacer, openChat);
        header.setAlignment(Pos.CENTER_LEFT);

        Label chatBubble = new Label(
                "Hello Sarah, I'm here to help you navigate your family's health\nneeds today. What's on your mind?");
        chatBubble.setFont(Font.font("System", FontWeight.NORMAL, 13));
        chatBubble.setStyle(
                "-fx-background-color: " + PALE_PEACH + ";" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                "-fx-padding: 12px 14px;" +
                "-fx-background-radius: 10px;" +
                "-fx-line-spacing: 2px;"
        );
        chatBubble.setWrapText(true);

        Button firstAidInfo = new Button("🩹 First Aid Info");
        Button checkSymptoms = new Button("💗 Check Symptoms");
        Button isEmergency = new Button("⚠ Is This an Emergency?");
        styleChipButton(firstAidInfo);
        styleChipButton(checkSymptoms);
        styleChipButton(isEmergency);

        HBox chipRow = new HBox(10, firstAidInfo, checkSymptoms, isEmergency);

        VBox card = new VBox(14, header, chatBubble, chipRow);
        applyCardStyle(card, SURFACE, BORDER_COLOR);
        card.setPadding(new Insets(20));
        card.setPrefWidth(1000);
        card.setPrefHeight(220);
        return card;
    }

    // ---------- STYLE HELPER METHODS ----------
    private void applyCardStyle(VBox card, String bgColor, String borderColor) {
        card.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                "-fx-border-color: " + borderColor + ";" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 14px;" +
                "-fx-background-radius: 14px;"
        );
        card.setEffect(new DropShadow(16, 0, 5, Color.rgb(120, 47, 22, 0.06)));
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

    private void styleDangerButton(Button btn) {
        btn.setFont(Font.font("System", FontWeight.BOLD, 13));
        String baseStyle =
                "-fx-background-color: " + DANGER_BG + ";" +
                "-fx-text-fill: " + DANGER_TEXT + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-padding: 8px 16px;" +
                "-fx-cursor: hand;";
        btn.setStyle(baseStyle);
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #F8D3D1;" +
                "-fx-text-fill: " + DANGER_TEXT + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-padding: 8px 16px;" +
                "-fx-cursor: hand;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(baseStyle));
    }

    private void styleSecondaryActionButton(Button btn) {
        btn.setFont(Font.font("System", FontWeight.BOLD, 13));
        String baseStyle =
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-border-radius: 8px;" +
                "-fx-padding: 8px 16px;" +
                "-fx-cursor: hand;";
        btn.setStyle(baseStyle);
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: " + HOVER_BG + ";" +
                "-fx-border-color: " + PRIMARY + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-border-radius: 8px;" +
                "-fx-padding: 8px 16px;" +
                "-fx-cursor: hand;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(baseStyle));
    }

    private void styleChipButton(Button btn) {
        btn.setFont(Font.font("System", FontWeight.NORMAL, 12));
        String baseStyle =
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-background-radius: 16px;" +
                "-fx-border-radius: 16px;" +
                "-fx-padding: 6px 14px;" +
                "-fx-cursor: hand;";
        btn.setStyle(baseStyle);
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-color: " + PRIMARY + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-background-radius: 16px;" +
                "-fx-border-radius: 16px;" +
                "-fx-padding: 6px 14px;" +
                "-fx-cursor: hand;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(baseStyle));
    }

    private void applyStatusBadgeStyle(Label statusLbl, String status) {
        if ("Healthy".equalsIgnoreCase(status) || status.toLowerCase().contains("healthy")) {
            statusLbl.setStyle(
                    "-fx-background-color: #E8F5EC;" +
                    "-fx-text-fill: #23804F;" +
                    "-fx-padding: 4px 10px;" +
                    "-fx-background-radius: 12px;"
            );
        } else if ("Monitoring".equalsIgnoreCase(status) || status.toLowerCase().contains("monitor") || status.toLowerCase().contains("pending")) {
            statusLbl.setStyle(
                    "-fx-background-color: #FFF3D8;" +
                    "-fx-text-fill: #B77900;" +
                    "-fx-padding: 4px 10px;" +
                    "-fx-background-radius: 12px;"
            );
        } else if ("Critical".equalsIgnoreCase(status)) {
            statusLbl.setStyle(
                    "-fx-background-color: " + DANGER_BG + ";" +
                    "-fx-text-fill: " + DANGER_TEXT + ";" +
                    "-fx-padding: 4px 10px;" +
                    "-fx-background-radius: 12px;"
            );
        } else {
            statusLbl.setStyle(
                    "-fx-background-color: #F0F1F2;" +
                    "-fx-text-fill: #667177;" +
                    "-fx-padding: 4px 10px;" +
                    "-fx-background-radius: 12px;"
            );
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}