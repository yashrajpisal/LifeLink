package com.kurukshetra.view.family;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class FirstAidAssistant {

    public BorderPane setBorderPane(){

        BorderPane bp = new BorderPane();
        bp.getStyleClass().add("root-pane");

        VBox sidebar = createSidebar();
        VBox mainContent = createMainContent();

        bp.setLeft(sidebar);
        bp.setCenter(mainContent);

        return bp;
    }

    // ---------------- SIDEBAR ----------------

    private VBox createSidebar(){

        VBox sidebar = new VBox(5);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(250);
        sidebar.setPadding(new Insets(20,15,20,15));

        // profile section
        Image img1 = new Image("https://ui-avatars.com/api/?name=Sarah+Miller&background=6C63FF&color=fff");
        ImageView imView = new ImageView(img1);
        imView.setFitWidth(45);
        imView.setFitHeight(45);

        Circle clip = new Circle(22.5,22.5,22.5);
        imView.setClip(clip);

        Label nameLbl = new Label("Sarah Miller");
        nameLbl.getStyleClass().add("profile-name");

        Label roleLbl = new Label("Family Care Lead");
        roleLbl.getStyleClass().add("profile-role");

        VBox nameBox = new VBox(2,nameLbl,roleLbl);

        HBox profileBox = new HBox(10,imView,nameBox);
        profileBox.setAlignment(Pos.CENTER_LEFT);
        profileBox.setPadding(new Insets(0,0,20,0));

        // nav buttons
        Button dashboardBtn = createNavButton("Dashboard", false);
        Button findHospitalsBtn = createNavButton("Find Hospitals", false);
        Button emergencyBtn = createNavButton("Emergency Services", false);
        Button firstAidBtn = createNavButton("First-Aid Assistant", true);
        Button appointmentsBtn = createNavButton("Appointments", false);
        Button medicalHistoryBtn = createNavButton("Medical History", false);
        Button savedHospitalsBtn = createNavButton("Saved Hospitals", false);
        Button settingsBtn = createNavButton("Settings", false);

        VBox navBox = new VBox(3,dashboardBtn,findHospitalsBtn,emergencyBtn,firstAidBtn,
                appointmentsBtn,medicalHistoryBtn,savedHospitalsBtn,settingsBtn);

        // spacer pushes SOS button + logout to bottom
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button sosBtn = new Button("SOS Emergency Help");
        sosBtn.getStyleClass().add("sos-button");
        sosBtn.setMaxWidth(Double.MAX_VALUE);
        sosBtn.setOnAction(event -> {
            System.out.println("SOS Emergency Help clicked!");
        });

        Button logoutBtn = new Button("Logout");
        logoutBtn.getStyleClass().add("logout-button");
        logoutBtn.setMaxWidth(Double.MAX_VALUE);
        logoutBtn.setOnAction(event -> {
            System.out.println("Logout clicked!");
        });

        VBox bottomBox = new VBox(10,sosBtn,logoutBtn);
        bottomBox.setPadding(new Insets(15,0,0,0));

        sidebar.getChildren().addAll(profileBox, navBox, spacer, bottomBox);

        return sidebar;
    }

    private Button createNavButton(String text, boolean selected){

        Button btn = new Button(text);
        btn.getStyleClass().add("nav-button");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);

        if(selected){
            btn.getStyleClass().add("nav-button-selected");
        }

        btn.setOnAction(event -> {
            System.out.println(text + " clicked!");
        });

        return btn;
    }

    // ---------------- MAIN CONTENT ----------------

    private VBox createMainContent(){

        VBox mainBox = new VBox(20);
        mainBox.getStyleClass().add("main-content");
        mainBox.setPadding(new Insets(25,30,25,30));

        // top bar
        Label heading = new Label("AI Care Assistant");
        heading.getStyleClass().add("page-title");

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        Label bellIcon = new Label("\uD83D\uDD14");
        bellIcon.getStyleClass().add("top-icon");

        Label helpIcon = new Label("\u2753");
        helpIcon.getStyleClass().add("top-icon");

        HBox topBar = new HBox(15,heading,topSpacer,bellIcon,helpIcon);
        topBar.setAlignment(Pos.CENTER_LEFT);

        // assistant card
        VBox assistantCard = createAssistantCard();
        VBox.setVgrow(assistantCard, Priority.ALWAYS);

        mainBox.getChildren().addAll(topBar, assistantCard);

        return mainBox;
    }

    private VBox createAssistantCard(){

        VBox card = new VBox();
        card.getStyleClass().add("assistant-card");

        // card header
        Label avatarLbl = new Label("\uD83D\uDC64");
        avatarLbl.getStyleClass().add("assistant-avatar");

        Label titleLbl = new Label("LifeLink Care Assistant");
        titleLbl.getStyleClass().add("assistant-title");

        Label statusLbl = new Label("Status: Available");
        statusLbl.getStyleClass().add("assistant-status");

        VBox titleBox = new VBox(2,titleLbl,statusLbl);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Label menuLbl = new Label("\u22EE");
        menuLbl.getStyleClass().add("menu-icon");

        HBox headerBox = new HBox(10,avatarLbl,titleBox,headerSpacer,menuLbl);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.getStyleClass().add("assistant-header");
        headerBox.setPadding(new Insets(15,20,15,20));

        // scrollable chat area
        VBox chatArea = createChatArea();

        ScrollPane scrollPane = new ScrollPane(chatArea);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("chat-scroll");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // suggestion chips
        HBox chipsBox = createSuggestionChips();

        // input area
        HBox inputBox = createInputArea();

        card.getChildren().addAll(headerBox, scrollPane, chipsBox, inputBox);

        return card;
    }

    private VBox createChatArea(){

        VBox chatBox = new VBox(15);
        chatBox.setPadding(new Insets(15,20,15,20));

        // disclaimer
        Label disclaimerLbl = new Label("Disclaimer: This assistant provides general first aid information and " +
                "is not a substitute for professional medical advice. For severe symptoms, excessive bleeding, " +
                "or suspected fractures, please seek immediate emergency care.");
        disclaimerLbl.getStyleClass().add("disclaimer-label");
        disclaimerLbl.setWrapText(true);

        HBox disclaimerBox = new HBox(disclaimerLbl);
        disclaimerBox.getStyleClass().add("disclaimer-box");
        disclaimerBox.setPadding(new Insets(10));

        // timestamp
        Label timeLbl = new Label("Today, 10:42 AM");
        timeLbl.getStyleClass().add("time-label");
        HBox timeBox = new HBox(timeLbl);
        timeBox.setAlignment(Pos.CENTER);

        // user message
        HBox userMsgBox = createUserMessage();

        // assistant reply
        VBox assistantMsgBox = createAssistantMessage();

        // first aid info card
        VBox firstAidCard = createFirstAidCard();

        chatBox.getChildren().addAll(disclaimerBox, timeBox, userMsgBox, assistantMsgBox, firstAidCard);

        return chatBox;
    }

    private HBox createUserMessage(){

        Text msgText = new Text("My son just touched a hot pan and burned his hand. " +
                "It's red and hurts, but there are no blisters yet. What should I do?");
        msgText.getStyleClass().add("user-message-text");
        msgText.setWrappingWidth(420);

        VBox bubble = new VBox(msgText);
        bubble.getStyleClass().add("user-bubble");
        bubble.setPadding(new Insets(12,15,12,15));

        HBox box = new HBox(bubble);
        box.setAlignment(Pos.CENTER_RIGHT);

        return box;
    }

    private VBox createAssistantMessage(){

        Label iconLbl = new Label("\uD83E\uDD16");
        iconLbl.getStyleClass().add("bot-icon");

        Text line1 = new Text("I understand this is stressful. Based on your description, this sounds like " +
                "a first-degree minor burn. The priority right now is to cool the burn and reduce pain.");
        line1.getStyleClass().add("assistant-message-text");
        line1.setWrappingWidth(480);

        Text line2 = new Text("Please follow these immediate steps. If the burn forms blisters larger than " +
                "3 inches, covers a major joint, or the pain becomes unmanageable, you should seek medical attention.");
        line2.getStyleClass().add("assistant-message-text");
        line2.setWrappingWidth(480);

        VBox textBox = new VBox(10,line1,line2);
        textBox.getStyleClass().add("assistant-bubble");
        textBox.setPadding(new Insets(12,15,12,15));

        HBox rowBox = new HBox(10,iconLbl,textBox);
        rowBox.setAlignment(Pos.TOP_LEFT);

        VBox wrapper = new VBox(rowBox);

        return wrapper;
    }

    private VBox createFirstAidCard(){

        VBox card = new VBox(10);
        card.getStyleClass().add("first-aid-card");
        card.setPadding(new Insets(15));

        Label iconLbl = new Label("\uD83E\uDE79");
        iconLbl.getStyleClass().add("first-aid-icon");

        Label titleLbl = new Label("Minor Burn First-Aid");
        titleLbl.getStyleClass().add("first-aid-title");

        Label subLbl = new Label("Immediate Action Plan");
        subLbl.getStyleClass().add("first-aid-subtitle");

        VBox titleBox = new VBox(2,titleLbl,subLbl);

        HBox headerBox = new HBox(10,iconLbl,titleBox);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        VBox stepsBox = new VBox(10);
        stepsBox.getChildren().addAll(
                createStepRow(1,"Cool the burn immediately.", "Run cool (not cold) water over the burn " +
                        "for 10-15 minutes or apply a cool, wet compress. Do not use ice."),
                createStepRow(2,"Remove tight items.", "Take off rings, watches, or tight clothing near the " +
                        "burn before swelling starts."),
                createStepRow(3,"Cover loosely.", "Cover the burn with a clean, non-stick bandage or cloth. " +
                        "Do not apply butter or ointments."),
                createStepRow(4,"Watch for warning signs.", "Seek medical attention if blisters get larger, " +
                        "signs of infection appear, or pain worsens.")
        );

        card.getChildren().addAll(headerBox, stepsBox);

        return card;
    }

    private HBox createStepRow(int number, String boldPart, String restPart){

        Label numberLbl = new Label(String.valueOf(number));
        numberLbl.getStyleClass().add("step-number");

        Text boldText = new Text(boldPart + " ");
        boldText.getStyleClass().add("step-bold-text");

        Text restText = new Text(restPart);
        restText.getStyleClass().add("step-text");

        javafx.scene.text.TextFlow flow = new javafx.scene.text.TextFlow(boldText, restText);
        flow.setPrefWidth(430);

        HBox row = new HBox(10,numberLbl,flow);
        row.setAlignment(Pos.TOP_LEFT);

        return row;
    }

    private HBox createSuggestionChips(){

        Button burnsChip = createChip("Burns");
        Button bleedingChip = createChip("Bleeding");
        Button injuriesChip = createChip("Injuries");
        Button feverChip = createChip("Fever");
        Button nauseaChip = createChip("Nausea");

        HBox chipsBox = new HBox(10,burnsChip,bleedingChip,injuriesChip,feverChip,nauseaChip);
        chipsBox.setAlignment(Pos.CENTER_LEFT);
        chipsBox.setPadding(new Insets(0,20,10,20));

        return chipsBox;
    }

    private Button createChip(String text){

        Button chip = new Button(text);
        chip.getStyleClass().add("suggestion-chip");
        chip.setOnAction(event -> {
            System.out.println(text + " chip clicked!");
        });

        return chip;
    }

    private HBox createInputArea(){

        Label attachIcon = new Label("\uD83D\uDCCE");
        attachIcon.getStyleClass().add("input-icon");

        TextField inputField = new TextField();
        inputField.setPromptText("Describe the symptoms or ask a medical question...");
        inputField.getStyleClass().add("chat-input-field");
        HBox.setHgrow(inputField, Priority.ALWAYS);

        Label micIcon = new Label("\uD83C\uDFA4");
        micIcon.getStyleClass().add("input-icon");

        Button sendBtn = new Button("\u27A4");
        sendBtn.getStyleClass().add("send-button");
        sendBtn.setOnAction(event -> {
            System.out.println("Send message: " + inputField.getText());
        });

        StackPane sendWrapper = new StackPane(sendBtn);

        HBox inputBox = new HBox(12,attachIcon,inputField,micIcon,sendWrapper);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.getStyleClass().add("input-bar");
        inputBox.setPadding(new Insets(12,20,15,20));

        return inputBox;
    }
}