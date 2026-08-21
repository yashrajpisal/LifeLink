

// package com.kurukshetra.view.family;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;
// import javafx.scene.text.TextFlow;
// import javafx.stage.Stage;

// public class FirstAidAssistant {

//     public BorderPane setBorderPane(Stage stage) {

//         BorderPane bp = new BorderPane();
//         bp.getStyleClass().add("root-pane");

//         VBox sidebar = Sidebar.build(stage, Sidebar.Page.FIRST_AID);
//         VBox mainContent = createMainContent();

//         bp.setLeft(sidebar);
//         bp.setCenter(mainContent);

//         return bp;
//     }

//     // ---------------- MAIN CONTENT ----------------

//     private VBox createMainContent() {

//         VBox mainBox = new VBox(20);
//         mainBox.getStyleClass().add("main-content");
//         mainBox.setPadding(new Insets(25, 30, 25, 30));

//         // top bar
//         Label heading = new Label("AI Care Assistant");
//         heading.getStyleClass().add("page-title");

//         Region topSpacer = new Region();
//         HBox.setHgrow(topSpacer, Priority.ALWAYS);

//         Label bellIcon = new Label("\uD83D\uDD14");
//         bellIcon.getStyleClass().add("top-icon");

//         Label helpIcon = new Label("\u2753");
//         helpIcon.getStyleClass().add("top-icon");

//         HBox topBar = new HBox(15, heading, topSpacer, bellIcon, helpIcon);
//         topBar.setAlignment(Pos.CENTER_LEFT);

//         // assistant card
//         VBox assistantCard = createAssistantCard();
//         VBox.setVgrow(assistantCard, Priority.ALWAYS);

//         mainBox.getChildren().addAll(topBar, assistantCard);

//         return mainBox;
//     }

//     private VBox createAssistantCard() {

//         VBox card = new VBox();
//         card.getStyleClass().add("assistant-card");

//         // card header
//         Label avatarLbl = new Label("\uD83D\uDC64");
//         avatarLbl.getStyleClass().add("assistant-avatar");

//         Label titleLbl = new Label("LifeLink Care Assistant");
//         titleLbl.getStyleClass().add("assistant-title");

//         Label statusLbl = new Label("Status: Available");
//         statusLbl.getStyleClass().add("assistant-status");

//         VBox titleBox = new VBox(2, titleLbl, statusLbl);

//         Region headerSpacer = new Region();
//         HBox.setHgrow(headerSpacer, Priority.ALWAYS);

//         Label menuLbl = new Label("\u22EE");
//         menuLbl.getStyleClass().add("menu-icon");

//         HBox headerBox = new HBox(10, avatarLbl, titleBox, headerSpacer, menuLbl);
//         headerBox.setAlignment(Pos.CENTER_LEFT);
//         headerBox.getStyleClass().add("assistant-header");
//         headerBox.setPadding(new Insets(15, 20, 15, 20));

//         // scrollable chat area
//         VBox chatArea = createChatArea();

//         ScrollPane scrollPane = new ScrollPane(chatArea);
//         scrollPane.setFitToWidth(true);
//         scrollPane.getStyleClass().add("chat-scroll");
//         VBox.setVgrow(scrollPane, Priority.ALWAYS);

//         MedicalReports.playPageAnimation(scrollPane);

//         // suggestion chips
//         HBox chipsBox = createSuggestionChips();

//         // input area
//         HBox inputBox = createInputArea();

//         card.getChildren().addAll(headerBox, scrollPane, chipsBox, inputBox);

//         return card;
//     }

//     private VBox createChatArea() {

//         VBox chatBox = new VBox(15);
//         chatBox.setPadding(new Insets(15, 20, 15, 20));

//         // disclaimer
//         Label disclaimerLbl = new Label("Disclaimer: This assistant provides general first aid information and " +
//                 "is not a substitute for professional medical advice. For severe symptoms, excessive bleeding, " +
//                 "or suspected fractures, please seek immediate emergency care.");
//         disclaimerLbl.getStyleClass().add("disclaimer-label");
//         disclaimerLbl.setWrapText(true);

//         HBox disclaimerBox = new HBox(disclaimerLbl);
//         disclaimerBox.getStyleClass().add("disclaimer-box");
//         disclaimerBox.setPadding(new Insets(10));

//         // timestamp
//         Label timeLbl = new Label("Today, 10:42 AM");
//         timeLbl.getStyleClass().add("time-label");
//         HBox timeBox = new HBox(timeLbl);
//         timeBox.setAlignment(Pos.CENTER);

//         // user message
//         HBox userMsgBox = createUserMessage();

//         // assistant reply
//         VBox assistantMsgBox = createAssistantMessage();

//         // first aid info card
//         VBox firstAidCard = createFirstAidCard();

//         chatBox.getChildren().addAll(disclaimerBox, timeBox, userMsgBox, assistantMsgBox, firstAidCard);

//         return chatBox;
//     }

//     private HBox createUserMessage() {

//         Text msgText = new Text("My son just touched a hot pan and burned his hand. " +
//                 "It's red and hurts, but there are no blisters yet. What should I do?");
//         msgText.getStyleClass().add("user-message-text");
//         msgText.setWrappingWidth(420);

//         VBox bubble = new VBox(msgText);
//         bubble.getStyleClass().add("user-bubble");
//         bubble.setPadding(new Insets(12, 15, 12, 15));

//         HBox box = new HBox(bubble);
//         box.setAlignment(Pos.CENTER_RIGHT);

//         return box;
//     }

//     private VBox createAssistantMessage() {

//         Label iconLbl = new Label("\uD83E\uDD16");
//         iconLbl.getStyleClass().add("bot-icon");

//         Text line1 = new Text("I understand this is stressful. Based on your description, this sounds like " +
//                 "a first-degree minor burn. The priority right now is to cool the burn and reduce pain.");
//         line1.getStyleClass().add("assistant-message-text");
//         line1.setWrappingWidth(480);

//         Text line2 = new Text("Please follow these immediate steps. If the burn forms blisters larger than " +
//                 "3 inches, covers a major joint, or the pain becomes unmanageable, you should seek medical attention.");
//         line2.getStyleClass().add("assistant-message-text");
//         line2.setWrappingWidth(480);

//         VBox textBox = new VBox(10, line1, line2);
//         textBox.getStyleClass().add("assistant-bubble");
//         textBox.setPadding(new Insets(12, 15, 12, 15));

//         HBox rowBox = new HBox(10, iconLbl, textBox);
//         rowBox.setAlignment(Pos.TOP_LEFT);

//         return new VBox(rowBox);
//     }

//     private VBox createFirstAidCard() {

//         VBox card = new VBox(10);
//         card.getStyleClass().add("first-aid-card");
//         card.setPadding(new Insets(15));

//         Label iconLbl = new Label("\uD83E\uDE79");
//         iconLbl.getStyleClass().add("first-aid-icon");

//         Label titleLbl = new Label("Minor Burn First-Aid");
//         titleLbl.getStyleClass().add("first-aid-title");

//         Label subLbl = new Label("Immediate Action Plan");
//         subLbl.getStyleClass().add("first-aid-subtitle");

//         VBox titleBox = new VBox(2, titleLbl, subLbl);

//         HBox headerBox = new HBox(10, iconLbl, titleBox);
//         headerBox.setAlignment(Pos.CENTER_LEFT);

//         VBox stepsBox = new VBox(10);
//         stepsBox.getChildren().addAll(
//                 createStepRow(1, "Cool the burn immediately.", "Run cool (not cold) water over the burn " +
//                         "for 10-15 minutes or apply a cool, wet compress. Do not use ice."),
//                 createStepRow(2, "Remove tight items.", "Take off rings, watches, or tight clothing near the " +
//                         "burn before swelling starts."),
//                 createStepRow(3, "Cover loosely.", "Cover the burn with a clean, non-stick bandage or cloth. " +
//                         "Do not apply butter or ointments."),
//                 createStepRow(4, "Watch for warning signs.", "Seek medical attention if blisters get larger, " +
//                         "signs of infection appear, or pain worsens.")
//         );

//         card.getChildren().addAll(headerBox, stepsBox);

//         return card;
//     }

//     private HBox createStepRow(int number, String boldPart, String restPart) {

//         Label numberLbl = new Label(String.valueOf(number));
//         numberLbl.getStyleClass().add("step-number");

//         Text boldText = new Text(boldPart + " ");
//         boldText.getStyleClass().add("step-bold-text");

//         Text restText = new Text(restPart);
//         restText.getStyleClass().add("step-text");

//         TextFlow flow = new TextFlow(boldText, restText);
//         flow.setPrefWidth(430);

//         HBox row = new HBox(10, numberLbl, flow);
//         row.setAlignment(Pos.TOP_LEFT);

//         return row;
//     }

//     private HBox createSuggestionChips() {

//         Button burnsChip = createChip("Burns");
//         Button bleedingChip = createChip("Bleeding");
//         Button injuriesChip = createChip("Injuries");
//         Button feverChip = createChip("Fever");
//         Button nauseaChip = createChip("Nausea");

//         HBox chipsBox = new HBox(10, burnsChip, bleedingChip, injuriesChip, feverChip, nauseaChip);
//         chipsBox.setAlignment(Pos.CENTER_LEFT);
//         chipsBox.setPadding(new Insets(0, 20, 10, 20));

//         return chipsBox;
//     }

//     private Button createChip(String text) {

//         Button chip = new Button(text);
//         chip.getStyleClass().add("suggestion-chip");
//         chip.setOnAction(event -> System.out.println(text + " chip clicked!"));

//         return chip;
//     }

//     private HBox createInputArea() {

//         Label attachIcon = new Label("\uD83D\uDCCE");
//         attachIcon.getStyleClass().add("input-icon");

//         TextField inputField = new TextField();
//         inputField.setPromptText("Describe the symptoms or ask a medical question...");
//         inputField.getStyleClass().add("chat-input-field");
//         HBox.setHgrow(inputField, Priority.ALWAYS);

//         Label micIcon = new Label("\uD83C\uDFA4");
//         micIcon.getStyleClass().add("input-icon");

//         Button sendBtn = new Button("\u27A4");
//         sendBtn.getStyleClass().add("send-button");
//         sendBtn.setOnAction(event -> System.out.println("Send message: " + inputField.getText()));

//         StackPane sendWrapper = new StackPane(sendBtn);

//         HBox inputBox = new HBox(12, attachIcon, inputField, micIcon, sendWrapper);
//         inputBox.setAlignment(Pos.CENTER);
//         inputBox.getStyleClass().add("input-bar");
//         inputBox.setPadding(new Insets(12, 20, 15, 20));

//         return inputBox;
//     }
// }


package com.kurukshetra.view.family;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class FirstAidAssistant {

    private static final String PRIMARY = "#CA6721";
    private static final String PRIMARY_DARK = "#782F16";
    private static final String PAGE_BG = "#FCF9F7";
    private static final String SURFACE = "#FFFFFF";
    private static final String VERY_LIGHT_TERRACOTTA = "#FDF0E8";
    private static final String LIGHT_TERRACOTTA = "#F9E4D6";
    private static final String PALE_PEACH = "#FEF7F2";
    private static final String TEXT_PRIMARY = "#2B211D";
    private static final String TEXT_SECONDARY = "#675B55";
    private static final String TEXT_MUTED = "#938780";
    private static final String BORDER_COLOR = "#EDE0D8";
    private static final String HOVER_BG = "#FFF5EF";

    public BorderPane setBorderPane(Stage stage) {
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: " + PAGE_BG + ";");

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.FIRST_AID);
        VBox mainContent = createMainContent();

        bp.setLeft(sidebar);
        bp.setCenter(mainContent);
        return bp;
    }

    private VBox createMainContent() {
        VBox mainBox = new VBox(20);
        mainBox.setStyle("-fx-background-color: " + PAGE_BG + ";");
        mainBox.setPadding(new Insets(25, 30, 25, 30));

        Label heading = new Label("AI Care Assistant");
        heading.setFont(Font.font("System", FontWeight.BOLD, 22));
        heading.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        HBox topBar = new HBox(15, heading, topSpacer);
        topBar.setAlignment(Pos.CENTER_LEFT);

        VBox assistantCard = createAssistantCard();
        VBox.setVgrow(assistantCard, Priority.ALWAYS);

        mainBox.getChildren().addAll(topBar, assistantCard);
        return mainBox;
    }

    private VBox createAssistantCard() {
        VBox card = new VBox();
        card.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 14px;" +
                "-fx-background-radius: 14px;"
        );
        card.setEffect(new DropShadow(12, 0, 4, Color.rgb(120, 47, 22, 0.05)));

        Label avatarLbl = new Label("\uD83D\uDC64");
        avatarLbl.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-background-radius: 20px;" +
                "-fx-padding: 6px 10px;" +
                "-fx-font-size: 18px;"
        );

        Label titleLbl = new Label("LifeLink Care Assistant");
        titleLbl.setFont(Font.font("System", FontWeight.BOLD, 14));
        titleLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label statusLbl = new Label("Status: Available");
        statusLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #23804F;");

        VBox titleBox = new VBox(2, titleLbl, statusLbl);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Label menuLbl = new Label("\u22EE");
        menuLbl.setStyle("-fx-font-size: 16px; -fx-text-fill: " + TEXT_MUTED + "; -fx-cursor: hand;");

        HBox headerBox = new HBox(10, avatarLbl, titleBox, headerSpacer, menuLbl);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(15, 20, 15, 20));
        headerBox.setStyle("-fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;");

        VBox chatArea = createChatArea();
        ScrollPane scrollPane = new ScrollPane(chatArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        MedicalReports.playPageAnimation(scrollPane);

        HBox chipsBox = createSuggestionChips();
        HBox inputBox = createInputArea();

        card.getChildren().addAll(headerBox, scrollPane, chipsBox, inputBox);
        return card;
    }

    private VBox createChatArea() {
        VBox chatBox = new VBox(15);
        chatBox.setPadding(new Insets(15, 20, 15, 20));

        Label disclaimerLbl = new Label("Disclaimer: This assistant provides general first aid information and " +
                "is not a substitute for professional medical advice. For severe symptoms, excessive bleeding, " +
                "or suspected fractures, please seek immediate emergency care.");
        disclaimerLbl.setWrapText(true);
        disclaimerLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: " + PRIMARY_DARK + "; -fx-line-spacing: 2px;");

        HBox disclaimerBox = new HBox(disclaimerLbl);
        disclaimerBox.setPadding(new Insets(10));
        disclaimerBox.setStyle("-fx-background-color: " + VERY_LIGHT_TERRACOTTA + "; -fx-background-radius: 10px;");

        Label timeLbl = new Label("Today, 10:42 AM");
        timeLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");
        HBox timeBox = new HBox(timeLbl);
        timeBox.setAlignment(Pos.CENTER);

        HBox userMsgBox = createUserMessage();
        VBox assistantMsgBox = createAssistantMessage();
        VBox firstAidCard = createFirstAidCard();

        chatBox.getChildren().addAll(disclaimerBox, timeBox, userMsgBox, assistantMsgBox, firstAidCard);
        return chatBox;
    }

    private HBox createUserMessage() {
        Text msgText = new Text("My son just touched a hot pan and burned his hand. " +
                "It's red and hurts, but there are no blisters yet. What should I do?");
        msgText.setFill(Color.WHITE);
        msgText.setFont(Font.font("System", 13));
        msgText.setWrappingWidth(420);

        VBox bubble = new VBox(msgText);
        bubble.setStyle("-fx-background-color: " + PRIMARY + "; -fx-background-radius: 14 14 2 14; -fx-padding: 12 15;");

        HBox box = new HBox(bubble);
        box.setAlignment(Pos.CENTER_RIGHT);
        return box;
    }

    private VBox createAssistantMessage() {
        Label iconLbl = new Label("\uD83E\uDD16");
        iconLbl.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-background-radius: 15px;" +
                "-fx-padding: 6px;" +
                "-fx-font-size: 16px;"
        );

        Text line1 = new Text("I understand this is stressful. Based on your description, this sounds like " +
                "a first-degree minor burn. The priority right now is to cool the burn and reduce pain.");
        line1.setFill(Color.web(TEXT_PRIMARY));
        line1.setFont(Font.font("System", 13));
        line1.setWrappingWidth(480);

        Text line2 = new Text("Please follow these immediate steps. If the burn forms blisters larger than " +
                "3 inches, covers a major joint, or the pain becomes unmanageable, you should seek medical attention.");
        line2.setFill(Color.web(TEXT_PRIMARY));
        line2.setFont(Font.font("System", 13));
        line2.setWrappingWidth(480);

        VBox textBox = new VBox(10, line1, line2);
        textBox.setStyle("-fx-background-color: " + PALE_PEACH + "; -fx-background-radius: 2 14 14 14; -fx-padding: 12 15;");

        HBox rowBox = new HBox(10, iconLbl, textBox);
        rowBox.setAlignment(Pos.TOP_LEFT);
        return new VBox(rowBox);
    }

    private VBox createFirstAidCard() {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;"
        );

        Label iconLbl = new Label("\uD83E\uDE79");
        iconLbl.setStyle(
                "-fx-background-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-background-radius: 15px;" +
                "-fx-padding: 6px;" +
                "-fx-font-size: 18px;"
        );

        Label titleLbl = new Label("Minor Burn First-Aid");
        titleLbl.setFont(Font.font("System", FontWeight.BOLD, 14));
        titleLbl.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label subLbl = new Label("Immediate Action Plan");
        subLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");

        VBox titleBox = new VBox(2, titleLbl, subLbl);
        HBox headerBox = new HBox(10, iconLbl, titleBox);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        VBox stepsBox = new VBox(10);
        stepsBox.getChildren().addAll(
                createStepRow(1, "Cool the burn immediately.", "Run cool (not cold) water over the burn for 10-15 minutes or apply a cool, wet compress. Do not use ice."),
                createStepRow(2, "Remove tight items.", "Take off rings, watches, or tight clothing near the burn before swelling starts."),
                createStepRow(3, "Cover loosely.", "Cover the burn with a clean, non-stick bandage or cloth. Do not apply butter or ointments."),
                createStepRow(4, "Watch for warning signs.", "Seek medical attention if blisters get larger, signs of infection appear, or pain worsens.")
        );

        card.getChildren().addAll(headerBox, stepsBox);
        return card;
    }

    private HBox createStepRow(int number, String boldPart, String restPart) {
        Label numberLbl = new Label(String.valueOf(number));
        numberLbl.setStyle(
                "-fx-background-color: " + PRIMARY + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10px;" +
                "-fx-min-width: 20px;" +
                "-fx-min-height: 20px;" +
                "-fx-alignment: center;"
        );

        Text boldText = new Text(boldPart + " ");
        boldText.setFont(Font.font("System", FontWeight.BOLD, 12));
        boldText.setFill(Color.web(TEXT_PRIMARY));

        Text restText = new Text(restPart);
        restText.setFont(Font.font("System", 12));
        restText.setFill(Color.web(TEXT_SECONDARY));

        TextFlow flow = new TextFlow(boldText, restText);
        flow.setPrefWidth(430);

        HBox row = new HBox(10, numberLbl, flow);
        row.setAlignment(Pos.TOP_LEFT);
        return row;
    }

    private HBox createSuggestionChips() {
        Button burnsChip = createChip("Burns");
        Button bleedingChip = createChip("Bleeding");
        Button injuriesChip = createChip("Injuries");
        Button feverChip = createChip("Fever");
        Button nauseaChip = createChip("Nausea");

        HBox chipsBox = new HBox(10, burnsChip, bleedingChip, injuriesChip, feverChip, nauseaChip);
        chipsBox.setAlignment(Pos.CENTER_LEFT);
        chipsBox.setPadding(new Insets(0, 20, 10, 20));
        return chipsBox;
    }

    private Button createChip(String text) {
        Button chip = new Button(text);
        chip.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 12px;" +
                "-fx-background-radius: 20px;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 20px;" +
                "-fx-padding: 6px 14px;" +
                "-fx-cursor: hand;"
        );
        chip.setOnMouseEntered(e -> chip.setStyle(
                "-fx-background-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 12px;" +
                "-fx-background-radius: 20px;" +
                "-fx-border-color: " + PRIMARY + ";" +
                "-fx-border-radius: 20px;" +
                "-fx-padding: 6px 14px;" +
                "-fx-cursor: hand;"
        ));
        chip.setOnMouseExited(e -> chip.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                "-fx-text-fill: " + PRIMARY_DARK + ";" +
                "-fx-font-size: 12px;" +
                "-fx-background-radius: 20px;" +
                "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                "-fx-border-radius: 20px;" +
                "-fx-padding: 6px 14px;" +
                "-fx-cursor: hand;"
        ));
        chip.setOnAction(event -> System.out.println(text + " chip clicked!"));
        return chip;
    }

    private HBox createInputArea() {
        Label attachIcon = new Label("\uD83D\uDCCE");
        attachIcon.setStyle("-fx-font-size: 15px; -fx-text-fill: " + TEXT_MUTED + "; -fx-cursor: hand;");

        TextField inputField = new TextField();
        inputField.setPromptText("Describe the symptoms or ask a medical question...");
        inputField.setStyle(
                "-fx-background-color: " + PALE_PEACH + ";" +
                "-fx-background-radius: 20px;" +
                "-fx-padding: 8px 14px;" +
                "-fx-font-size: 13px;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 20px;"
        );
        HBox.setHgrow(inputField, Priority.ALWAYS);

        Label micIcon = new Label("\uD83C\uDFA4");
        micIcon.setStyle("-fx-font-size: 15px; -fx-text-fill: " + TEXT_MUTED + "; -fx-cursor: hand;");

        Button sendBtn = new Button("\u27A4");
        sendBtn.setStyle(
                "-fx-background-color: " + PRIMARY + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 20px;" +
                "-fx-min-width: 36px;" +
                "-fx-min-height: 36px;" +
                "-fx-cursor: hand;"
        );
        sendBtn.setOnMouseEntered(e -> sendBtn.setStyle(
                "-fx-background-color: " + PRIMARY_DARK + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 20px;" +
                "-fx-min-width: 36px;" +
                "-fx-min-height: 36px;" +
                "-fx-cursor: hand;"
        ));
        sendBtn.setOnMouseExited(e -> sendBtn.setStyle(
                "-fx-background-color: " + PRIMARY + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 20px;" +
                "-fx-min-width: 36px;" +
                "-fx-min-height: 36px;" +
                "-fx-cursor: hand;"
        ));
        sendBtn.setOnAction(event -> System.out.println("Send message: " + inputField.getText()));

        StackPane sendWrapper = new StackPane(sendBtn);

        HBox inputBox = new HBox(12, attachIcon, inputField, micIcon, sendWrapper);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setStyle("-fx-background-color: " + SURFACE + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 1 0 0 0;");
        inputBox.setPadding(new Insets(12, 20, 15, 20));
        return inputBox;
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