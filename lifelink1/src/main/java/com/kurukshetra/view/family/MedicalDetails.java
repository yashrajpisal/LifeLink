// package com.kurukshetra.view.family;

// import javafx.geometry.Pos;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;

// public class MedicalDetails {

//     public VBox getMedicalVBox(){
        
//         Text title = new Text("MEdical Details");
//         title.setStyle("-fx-font-size: 18px; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-background-color: #e1f0f9; -fx-padding:10px 10px");

//         VBox vmainBox = new VBox(title);
//         vmainBox.setAlignment(Pos.CENTER);
//         return vmainBox;
//     }

// }
package com.kurukshetra.view.family;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class MedicalDetails {

    private final List<MemberCardHolder> memberCardHolders = new ArrayList<>();
    private TextField nameField;

    private static class MemberCardHolder {
        VBox card;
        Circle iconBg;
        Text arrow;
        String memberName;

        MemberCardHolder(VBox card, Circle iconBg, Text arrow, String memberName){
            this.card = card;
            this.iconBg = iconBg;
            this.arrow = arrow;
            this.memberName = memberName;
        }

        void setSelected(boolean isSelected){
            iconBg.setStyle(isSelected ? "-fx-fill:#dbeafe;" : "-fx-fill:#e2e8f0;");
            arrow.setText(isSelected ? ">" : "");
            card.setStyle(isSelected ? "-fx-background-color:#ffffff; -fx-border-color:#2563eb; -fx-border-width:2px; -fx-border-radius:10px; -fx-background-radius:10px; -fx-padding:15px; -fx-cursor:hand;" : "-fx-background-color:#ffffff; -fx-border-color:#e2e8f0; -fx-border-width:1px; -fx-border-radius:10px; -fx-background-radius:10px; -fx-padding:15px; -fx-cursor:hand;");
        }
    }

    public ScrollPane getMedicalVBox(){

        memberCardHolders.clear();

        // Top Header
        Text headerTitle = new Text("Medical Reports");
        headerTitle.setStyle("-fx-font-size:22px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        Text headerSub = new Text("Manage family medical profiles and documents.");
        headerSub.setStyle("-fx-fill:#64748b; -fx-font-size:13px;");

        VBox titleBox = new VBox(3, headerTitle, headerSub);

        Button addNewBtn = new Button("+ Add New");
        addNewBtn.setStyle("-fx-background-color:#1d4ed8; -fx-text-fill:white; -fx-font-weight:bold; -fx-background-radius:8px; -fx-padding:10px 18px; -fx-cursor:hand;");
        addHoverAnimation(addNewBtn);

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        HBox topBar = new HBox(10, titleBox, topSpacer, addNewBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-padding:0 0 15px 0;");

        // Left Panel: Member List
        VBox member1 = createMemberCard("👤", "Sarah Connor", "Primary Member", true);
        VBox member2 = createMemberCard("😊", "John Connor", "Dependent", false);
        VBox member3 = createMemberCard("🚶", "Kyle Reese", "Dependent", false);

        VBox memberList = new VBox(12, member1, member2, member3);
        memberList.setPrefWidth(240);

        // Right Panel: Edit Profile & Upload Section
        VBox editProfileCard = createEditProfileCard();
        VBox uploadCard = createUploadCard();

        VBox rightPane = new VBox(20, editProfileCard, uploadCard);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        HBox mainLayout = new HBox(20, memberList, rightPane);

        VBox mainContent = new VBox(20, topBar, mainLayout);
        mainContent.setStyle("-fx-padding:25px; -fx-background-color:#f8fafc;");

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color:transparent; -fx-background:#f8fafc;");
        return scrollPane;
    }

    private VBox createMemberCard(String icon, String name, String role, boolean isSelected){
        Circle iconBg = new Circle(18);
        iconBg.setStyle(isSelected ? "-fx-fill:#dbeafe;" : "-fx-fill:#e2e8f0;");
        Text iconText = new Text(icon);
        iconText.setStyle("-fx-font-size:14px;");
        StackPane iconPane = new StackPane(iconBg, iconText);

        Text nameText = new Text(name);
        nameText.setStyle("-fx-font-weight:bold; -fx-font-size:14px; -fx-fill:#0f172a;");

        Text roleText = createSubText(role);
        VBox textBox = new VBox(2, nameText, roleText);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Text arrow = new Text(isSelected ? ">" : "");
        arrow.setStyle("-fx-fill:#2563eb; -fx-font-weight:bold;");

        HBox row = new HBox(12, iconPane, textBox, sp, arrow);
        row.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(row);
        card.setStyle(isSelected ? "-fx-background-color:#ffffff; -fx-border-color:#2563eb; -fx-border-width:2px; -fx-border-radius:10px; -fx-background-radius:10px; -fx-padding:15px; -fx-cursor:hand;" : "-fx-background-color:#ffffff; -fx-border-color:#e2e8f0; -fx-border-width:1px; -fx-border-radius:10px; -fx-background-radius:10px; -fx-padding:15px; -fx-cursor:hand;");
        addHoverAnimation(card);

        MemberCardHolder holder = new MemberCardHolder(card, iconBg, arrow, name);
        memberCardHolders.add(holder);

        card.setOnMouseClicked(e -> {
            // for(MemberCardHolder m : memberCardHolders){
            //     m.setSelected(m == holder);
            // }
            // if(nameField != null){
            //     nameField.setText(name);
            // }
            MedicalDetailsReport md = new MedicalDetailsReport();
            Dashboard dash = new Dashboard();
            Dashboard.root.setCenter(md.getFamilyReportsVBox());
        });

        return card;
    }

    private VBox createEditProfileCard(){
        Text title = new Text("Edit Profile");
        title.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        // Row 1: Name & Blood Group
        nameField = new TextField("Sarah Connor");
        nameField.setStyle("-fx-background-color:#ffffff; -fx-border-color:#cbd5e1; -fx-border-radius:6px; -fx-padding:8px;");

        ComboBox<String> bloodCombo = new ComboBox<>();
        bloodCombo.getItems().addAll("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");
        bloodCombo.getSelectionModel().select("A+");
        bloodCombo.setMaxWidth(Double.MAX_VALUE);
        bloodCombo.setStyle("-fx-background-color:#ffffff; -fx-border-color:#cbd5e1; -fx-border-radius:6px; -fx-padding:4px;");

        VBox nameGroup = createFormField("Full Name", nameField);
        VBox bloodGroup = createFormField("Blood Group", bloodCombo);
        HBox.setHgrow(nameGroup, Priority.ALWAYS);
        HBox.setHgrow(bloodGroup, Priority.ALWAYS);
        HBox row1 = new HBox(15, nameGroup, bloodGroup);

        // Row 2: Weight & Phone Number
        TextField weightField = new TextField("65");
        weightField.setStyle("-fx-background-color:#ffffff; -fx-border-color:#cbd5e1; -fx-border-radius:6px; -fx-padding:8px;");

        TextField phoneField = new TextField("+1 (555) 123-4567");
        phoneField.setStyle("-fx-background-color:#ffffff; -fx-border-color:#cbd5e1; -fx-border-radius:6px; -fx-padding:8px;");

        VBox weightGroup = createFormField("Weight (kg)", weightField);
        VBox phoneGroup = createFormField("Phone Number", phoneField);
        HBox.setHgrow(weightGroup, Priority.ALWAYS);
        HBox.setHgrow(phoneGroup, Priority.ALWAYS);
        HBox row2 = new HBox(15, weightGroup, phoneGroup);

        // Row 3: Allergies Multi-select
        ListView<String> allergyList = new ListView<>();
        allergyList.getItems().addAll("Penicillin", "Peanuts", "Dust Mites", "Latex");
        allergyList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        allergyList.getSelectionModel().select(0);
        allergyList.setPrefHeight(90);
        allergyList.setStyle("-fx-background-color:#ffffff; -fx-border-color:#cbd5e1; -fx-border-radius:6px;");

        Text hintText = createSubText("Hold Ctrl/Cmd to select multiple.");
        VBox allergyGroup = createFormField("Allergies", new VBox(5, allergyList, hintText));

        // Save Button
        Button saveBtn = new Button("💾 Save Profile");
        saveBtn.setStyle("-fx-background-color:#1d4ed8; -fx-text-fill:white; -fx-font-weight:bold; -fx-background-radius:6px; -fx-padding:10px 20px; -fx-cursor:hand;");
        addHoverAnimation(saveBtn);

        HBox btnBox = new HBox(saveBtn);
        btnBox.setAlignment(Pos.CENTER_RIGHT);

        return createCard(title, row1, row2, allergyGroup, btnBox);
    }

    private VBox createUploadCard(){
        Text title = new Text("Upload Medical Report");
        title.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Select Report Type", "Blood Test", "X-Ray / MRI", "Prescription", "Vaccination");
        typeCombo.getSelectionModel().selectFirst();
        typeCombo.setMaxWidth(Double.MAX_VALUE);
        typeCombo.setStyle("-fx-background-color:#ffffff; -fx-border-color:#cbd5e1; -fx-border-radius:6px; -fx-padding:6px;");

        VBox typeGroup = createFormField("Report Type", typeCombo);
        HBox.setHgrow(typeGroup, Priority.ALWAYS);

        // Drag & Drop Area
        Text uploadIcon = new Text("📤");
        uploadIcon.setStyle("-fx-font-size:24px;");

        Text dropText = new Text("Click to browse or drag file here");
        dropText.setStyle("-fx-font-weight:bold; -fx-fill:#334155;");

        Text subDrop = createSubText("PDF, JPG, PNG up to 10MB");

        VBox dropZone = new VBox(6, uploadIcon, dropText, subDrop);
        dropZone.setAlignment(Pos.CENTER);
        dropZone.setStyle("-fx-border-color:#cbd5e1; -fx-border-style:dashed; -fx-border-width:2px; -fx-border-radius:8px; -fx-padding:20px; -fx-background-color:#f8fafc; -fx-cursor:hand;");
        addHoverAnimation(dropZone);
        HBox.setHgrow(dropZone, Priority.ALWAYS);

        HBox uploadSection = new HBox(15, typeGroup, dropZone);
        uploadSection.setAlignment(Pos.CENTER_LEFT);

        // Recent Uploads
        Text recentTitle = new Text("Recent Uploads");
        recentTitle.setStyle("-fx-font-weight:bold; -fx-fill:#0f172a; -fx-font-size:13px;");

        Text pdfIcon = new Text("📄");
        pdfIcon.setStyle("-fx-font-size:18px;");

        Text fileTitle = new Text("blood_test_results_2023.pdf");
        fileTitle.setStyle("-fx-font-weight:bold; -fx-fill:#0f172a;");

        Text fileSub = createSubText("Blood Test • Oct 24, 2023");
        VBox fileDetails = new VBox(2, fileTitle, fileSub);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Button deleteBtn = new Button("🗑");
        deleteBtn.setStyle("-fx-background-color:transparent; -fx-text-fill:#ef4444; -fx-cursor:hand;");

        HBox fileRow = new HBox(10, pdfIcon, fileDetails, sp, deleteBtn);
        fileRow.setAlignment(Pos.CENTER_LEFT);
        fileRow.setStyle("-fx-background-color:#ffffff; -fx-border-color:#e2e8f0; -fx-border-radius:8px; -fx-padding:10px 14px;");

        return createCard(title, uploadSection, recentTitle, fileRow);
    }

    private VBox createFormField(String labelText, javafx.scene.Node inputNode){
        Text label = new Text(labelText);
        label.setStyle("-fx-fill:#475569; -fx-font-size:12px; -fx-font-weight:bold;");
        return new VBox(5, label, inputNode);
    }

    private Text createSubText(String text){
        Text t = new Text(text);
        t.setStyle("-fx-fill:#64748b; -fx-font-size:12px;");
        return t;
    }

    private void addHoverAnimation(javafx.scene.Node node){
        ScaleTransition stIn = new ScaleTransition(Duration.millis(150), node);
        stIn.setToX(1.02);
        stIn.setToY(1.02);

        ScaleTransition stOut = new ScaleTransition(Duration.millis(150), node);
        stOut.setToX(1.0);
        stOut.setToY(1.0);

        node.setOnMouseEntered(e -> stIn.playFromStart());
        node.setOnMouseExited(e -> stOut.playFromStart());
    }

    private VBox createCard(javafx.scene.Node... nodes){
        VBox card = new VBox(14, nodes);
        card.setStyle("-fx-background-color:#ffffff; -fx-border-color:#e2e8f0; -fx-border-radius:10px; -fx-background-radius:10px; -fx-padding:20px;");
        return card;
    }
}