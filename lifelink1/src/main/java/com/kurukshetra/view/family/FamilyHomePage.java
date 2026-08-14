
package com.kurukshetra.view.family;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FamilyHomePage {

    // ---------- DUMMY / STATIC DATA (replace with backend later) ----------
    private String[][] familyMembers = {
            // name, relation, status, status-css-class
            {"Arjun Sharma", "Brother", "Healthy", "status-healthy"},
            {"Maya Miller", "Mother", "Monitoring", "status-monitoring"}
    };

    private String[][] nearbyCare = {
            // name, distance, wait time
            {"CityCare General", "2.4 mi", "5 min wait"},
            {"Apollo Medical Center", "4.3 mi", "12 min wait"}
    };
    // ------------------------------------------------------------------

    public BorderPane setBorderPane(Stage stage) {

        BorderPane bp = new BorderPane();
        bp.getStyleClass().add("root-pane");

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.DASHBOARD);
        bp.setLeft(sidebar);

        VBox mainContent = buildMainContent(stage);
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("main-scroll");
        bp.setCenter(scrollPane);

        return bp;
    }

    // ---------------- MAIN CONTENT ----------------
    private VBox buildMainContent(Stage stage) {

        HBox topBar = buildTopBar();

        Text greeting = new Text("Good morning, Sarah");
        greeting.getStyleClass().add("greeting-text");

        Label subGreeting = new Label("How can LifeLink help you today?");
        subGreeting.getStyleClass().add("sub-greeting-label");

        VBox emergencyCard = buildEmergencyCard(stage);
        VBox familyHubCard = buildFamilyHubCard();

        HBox row1 = new HBox(20, emergencyCard, familyHubCard);
        HBox.setHgrow(emergencyCard, Priority.ALWAYS);

        VBox quickActionsGrid = buildQuickActionsGrid(stage);
        VBox nearbyCareCard = buildNearbyCareCard();

        HBox row2 = new HBox(20, quickActionsGrid, nearbyCareCard);
        HBox.setHgrow(quickActionsGrid, Priority.ALWAYS);

        // VBox chatCard = buildChatAssistantCard();

        VBox mainContent = new VBox(20, topBar, greeting, subGreeting, row1, row2);
        mainContent.getStyleClass().add("main-content");
        mainContent.setPadding(new Insets(24));

        return mainContent;
    }

    private HBox buildTopBar() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label bell = new Label("🔔");
        Label help = new Label("❓");
        bell.getStyleClass().add("icon-label");
        help.getStyleClass().add("icon-label");

        Label name = new Label("Sarah Miller");
        Label role = new Label("Family Care Lead");
        name.getStyleClass().add("profile-name");
        role.getStyleClass().add("profile-role");
        VBox profileText = new VBox(name, role);

        Label avatar = new Label("👩");
        avatar.getStyleClass().add("avatar-label");

        HBox profileBox = new HBox(10, profileText, avatar);
        profileBox.setAlignment(Pos.CENTER_RIGHT);

        HBox topBar = new HBox(20, spacer, bell, help, profileBox);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        return topBar;
    }

    private VBox buildEmergencyCard(Stage stage) {
        Label tag = new Label("⚠ IMMEDIATE ASSISTANCE");
        tag.getStyleClass().add("emergency-tag");

        Label title = new Label("Need urgent medical help?");
        title.getStyleClass().add("card-title-large");

        Label desc = new Label(
                "If you or a family member are experiencing a medical\n" +
                "emergency, request help immediately or find the nearest\n" +
                "critical care facility.");
        desc.getStyleClass().add("card-desc");

        Button emergencyHelpBtn = new Button("📞  Emergency Help");
        Button findHospitalsBtn = new Button("📍  Find Nearby Hospitals");
        emergencyHelpBtn.getStyleClass().add("primary-red-btn");
        findHospitalsBtn.getStyleClass().add("primary-dark-btn");

        // Connect these two buttons straight into the Emergency Services
        // and Find Care pages.
        emergencyHelpBtn.setOnAction(event -> {
            EmergencyServices emergencyServices = new EmergencyServices();
            stage.getScene().setRoot(emergencyServices.setBorderPane(stage));
        });

        findHospitalsBtn.setOnAction(event -> {
            FamilyFindCare findCare = new FamilyFindCare();
            stage.getScene().setRoot(findCare.setBorderPane(stage));
        });

        HBox btnRow = new HBox(12, emergencyHelpBtn, findHospitalsBtn);

        VBox card = new VBox(10, tag, title, desc, btnRow);
        card.getStyleClass().addAll("card", "emergency-card");
        card.setPadding(new Insets(20));           
        return card;
    }

    private VBox buildFamilyHubCard() {
        Label title = new Label("Family Hub");
        title.getStyleClass().add("card-title");

        Label addBtn = new Label("+");
        addBtn.getStyleClass().add("add-icon");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox header = new HBox(title, spacer, addBtn);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox membersBox = new VBox(12);
        for (String[] member : familyMembers) {
            membersBox.getChildren().add(buildFamilyMemberRow(member[0], member[1], member[2], member[3]));
        }

        VBox card = new VBox(16, header, membersBox);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(20));
        card.setPrefWidth(280);
        return card;
    }

    private HBox buildFamilyMemberRow(String name, String relation, String status, String statusStyleClass) {
        Label avatar = new Label("🧑");
        avatar.getStyleClass().add("avatar-label");

        Label nameLbl = new Label(name);
        Label relationLbl = new Label(relation);
        nameLbl.getStyleClass().add("member-name");
        relationLbl.getStyleClass().add("member-relation");
        VBox textBox = new VBox(nameLbl, relationLbl);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label statusLbl = new Label(status);
        statusLbl.getStyleClass().addAll("status-pill", statusStyleClass);

        HBox row = new HBox(10, avatar, textBox, spacer, statusLbl);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private VBox buildQuickActionsGrid(Stage stage) {
        Button firstAid = makeQuickActionCard("🩹", "First Aid");
        Button myHealth = makeQuickActionCard("💗", "My Health");
        Button savedPlaces = makeQuickActionCard("🔖", "Saved Places");

        // Wire the tiles that map onto existing pages.
        firstAid.setOnAction(e -> stage.getScene().setRoot(new FirstAidAssistant().setBorderPane(stage)));
        savedPlaces.setOnAction(e -> stage.getScene().setRoot(new SavedHospitals().setBorderPane(stage)));
        myHealth.setOnAction(event -> { System.out.println("enter myhealth button");});
        VBox chatCard = buildChatAssistantCard();

        HBox rowOne = new HBox(16, firstAid,myHealth, savedPlaces);
        HBox rowTwo = new HBox(chatCard);
        

        firstAid.setMaxWidth(Double.MAX_VALUE);
        myHealth.setMaxWidth(Double.MAX_VALUE);
        savedPlaces.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(firstAid, Priority.ALWAYS);
        HBox.setHgrow(myHealth, Priority.ALWAYS);
        HBox.setHgrow(savedPlaces, Priority.ALWAYS);
    

        VBox grid = new VBox(16, rowOne,rowTwo);       
        return grid;
    }

    private Button makeQuickActionCard(String icon, String text) {
        Label iconLbl = new Label(icon);
        iconLbl.getStyleClass().add("quick-action-icon");

        Label textLbl = new Label(text);
        textLbl.getStyleClass().add("quick-action-text");

        VBox box = new VBox(8, iconLbl, textLbl);
        box.setAlignment(Pos.CENTER);

        Button card = new Button();
        card.setGraphic(box);
        card.getStyleClass().add("quick-action-card");
        return card;
    }

    private VBox buildNearbyCareCard() {
        Label title = new Label("Nearby Care");
        title.getStyleClass().add("card-title");

        Label viewMap = new Label("View Map");
        viewMap.getStyleClass().add("link-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox header = new HBox(title, spacer, viewMap);
        header.setAlignment(Pos.CENTER_LEFT);

        Label mapPreview = new Label("🗺");
        mapPreview.getStyleClass().add("mini-map-preview");
        mapPreview.setMaxWidth(Double.MAX_VALUE);
        mapPreview.setAlignment(Pos.CENTER);

        VBox listBox = new VBox(10);
        for (String[] place : nearbyCare) {
            listBox.getChildren().add(buildNearbyCareRow(place[0], place[1], place[2]));
        }

        VBox card = new VBox(14, header, mapPreview, listBox);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(20));
        card.setPrefWidth(280);
        return card;
    }

    private HBox buildNearbyCareRow(String name, String distance, String wait) {
        Label icon = new Label("✳");
        icon.getStyleClass().add("small-icon");

        Label nameLbl = new Label(name);
        nameLbl.getStyleClass().add("member-name");

        Label infoLbl = new Label(distance + "  •  " + wait);
        infoLbl.getStyleClass().add("member-relation");

        VBox textBox = new VBox(nameLbl, infoLbl);
        HBox row = new HBox(10, icon, textBox);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private VBox buildChatAssistantCard() {
        Label icon = new Label("💬");
        icon.getStyleClass().add("chat-icon");

        Label title = new Label("LifeLink Care Assistant");
        title.getStyleClass().add("card-title");

        Label subtitle = new Label("AI-powered health guidance");
        subtitle.getStyleClass().add("card-desc");
        VBox titleBox = new VBox(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label openChat = new Label("Open Chat");
        openChat.getStyleClass().add("link-label");

        HBox header = new HBox(10, icon, titleBox, spacer, openChat);
        header.setAlignment(Pos.CENTER_LEFT);

        Label chatBubble = new Label(
                "Hello Sarah, I'm here to help you navigate your family's health\nneeds today. What's on your mind?");
        chatBubble.getStyleClass().add("chat-bubble");
        chatBubble.setWrapText(true);

        Button firstAidInfo = new Button("🩹 First Aid Info");
        Button checkSymptoms = new Button("💗 Check Symptoms");
        Button isEmergency = new Button("⚠ Is This an Emergency?");
        firstAidInfo.getStyleClass().add("chip-btn");
        checkSymptoms.getStyleClass().add("chip-btn");
        isEmergency.getStyleClass().add("chip-btn");

        HBox chipRow = new HBox(10, firstAidInfo, checkSymptoms, isEmergency);

        VBox card = new VBox(14, header, chatBubble, chipRow);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(20));
        card.setPrefWidth(1000);
        card.setPrefHeight(220);
        return card;
    }
}
