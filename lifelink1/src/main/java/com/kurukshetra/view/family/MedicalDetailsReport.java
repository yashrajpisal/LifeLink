package com.kurukshetra.view.family;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class MedicalDetailsReport {

    public ScrollPane getFamilyReportsVBox(){

        // Left Section: Family Members List
        Text fmTitle = new Text("Family\nMembers");
        fmTitle.setStyle("-fx-font-size:18px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        Button addNewBtn = new Button("+\tAdd\n\tNew");
        addNewBtn.setStyle("-fx-background-color:#1d4ed8; -fx-text-fill:white; -fx-font-weight:bold; -fx-font-size:11px; -fx-background-radius:8px; -fx-padding:8px 12px; -fx-cursor:hand;");
        addHoverAnimation(addNewBtn);

        Region sp1 = new Region();
        HBox.setHgrow(sp1, Priority.ALWAYS);

        HBox leftHeader = new HBox(10, fmTitle, sp1, addNewBtn);
        leftHeader.setAlignment(Pos.CENTER_LEFT);

        VBox member1 = createMemberCard("Robert Smith", "Father • 68 yrs", true);
        VBox member2 = createMemberCard("Sarah Smith", "Mother • 65 yrs", false);
        VBox member3 = createMemberCard("Emily Smith", "Daughter • 24 yrs", false);

        VBox leftPane = new VBox(15, leftHeader, member1, member2, member3);
        leftPane.setPrefWidth(220);

        // Right Top Section: Member Profile Card
        VBox memberProfileCard = createMemberProfileCard();

        // Right Bottom Section: Medical Reports Card
        VBox medicalReportsCard = createMedicalReportsCard();

        VBox rightPane = new VBox(20, memberProfileCard, medicalReportsCard);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        HBox mainLayout = new HBox(20, leftPane, rightPane);

        VBox mainContent = new VBox(mainLayout);
        mainContent.setStyle("-fx-padding:25px; -fx-background-color:#f8fafc;");

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color:transparent; -fx-background:#f8fafc;");
        return scrollPane;
    }

    private VBox createMemberCard(String name, String role, boolean isSelected){
        Circle avatarBg = new Circle(16);
        avatarBg.setStyle(isSelected ? "-fx-fill:#ffffff;" : "-fx-fill:#e2e8f0;");
        Text avatarText = new Text("👤");
        avatarText.setStyle("-fx-font-size:12px;");
        StackPane avatarPane = new StackPane(avatarBg, avatarText);

        Text nameText = new Text(name);
        nameText.setStyle(isSelected ? "-fx-font-weight:bold; -fx-font-size:13px; -fx-fill:#ffffff;" : "-fx-font-weight:bold; -fx-font-size:13px; -fx-fill:#0f172a;");

        Text roleText = new Text(role);
        roleText.setStyle(isSelected ? "-fx-fill:#dbeafe; -fx-font-size:11px;" : "-fx-fill:#64748b; -fx-font-size:11px;");

        VBox textBox = new VBox(2, nameText, roleText);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        Text arrow = new Text(isSelected ? ">" : "");
        arrow.setStyle("-fx-fill:#ffffff; -fx-font-weight:bold;");

        HBox row = new HBox(10, avatarPane, textBox, sp, arrow);
        row.setAlignment(Pos.CENTER_LEFT);

        VBox card = new VBox(row);
        card.setStyle(isSelected ? "-fx-background-color:#2563eb; -fx-border-radius:10px; -fx-background-radius:10px; -fx-padding:12px; -fx-cursor:hand;" : "-fx-background-color:#ffffff; -fx-border-color:#e2e8f0; -fx-border-width:1px; -fx-border-radius:10px; -fx-background-radius:10px; -fx-padding:12px; -fx-cursor:hand;");
        addHoverAnimation(card);
        return card;
    }

    private VBox createMemberProfileCard(){
        Circle avatarBg = new Circle(22);
        avatarBg.setStyle("-fx-fill:#cbd5e1;");
        Text avatarText = new Text("👴");
        avatarText.setStyle("-fx-font-size:18px;");
        StackPane avatarPane = new StackPane(avatarBg, avatarText);

        Text nameText = new Text("Robert Smith");
        nameText.setStyle("-fx-font-size:18px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        Text statusBadge = new Text("Stable");
        statusBadge.setStyle("-fx-fill:#16a34a; -fx-font-size:10px; -fx-font-weight:bold; -fx-background-color:#dcfce7; -fx-padding:2px 8px; -fx-background-radius:10px;");

        HBox nameRow = new HBox(8, nameText, statusBadge);
        nameRow.setAlignment(Pos.CENTER_LEFT);

        Text subText = createSubText("Last updated: Today, 09:41 AM");
        VBox infoBox = new VBox(3, nameRow, subText);

        Button editBtn = new Button("Edit Profile");
        editBtn.setStyle("-fx-background-color:transparent; -fx-border-color:#2563eb; -fx-border-radius:6px; -fx-text-fill:#2563eb; -fx-font-weight:bold; -fx-padding:6px 14px; -fx-cursor:hand;");
        addHoverAnimation(editBtn);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        HBox headerRow = new HBox(12, avatarPane, infoBox, sp, editBtn);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        // Stats grid
        VBox b1 = createStatBox("Blood Group", "🩸 O+");
        VBox b2 = createStatBox("Weight", "78 kg");
        VBox b3 = createStatBox("Height", "175 cm");
        VBox b4 = createStatBox("Phone Number", "+1 555-0198");

        HBox.setHgrow(b1, Priority.ALWAYS);
        HBox.setHgrow(b2, Priority.ALWAYS);
        HBox.setHgrow(b3, Priority.ALWAYS);
        HBox.setHgrow(b4, Priority.ALWAYS);

        HBox statsGrid = new HBox(12, b1, b2, b3, b4);

        // Allergy Warning Box
        Text warningIcon = new Text("⚠️");
        warningIcon.setStyle("-fx-fill:#dc2626; -fx-font-size:14px;");

        Text warningTitle = new Text("Known Allergies");
        warningTitle.setStyle("-fx-font-weight:bold; -fx-fill:#991b1b; -fx-font-size:12px;");

        Text warningValue = new Text("Penicillin, Peanuts (Severe)");
        warningValue.setStyle("-fx-fill:#7f1d1d; -fx-font-size:12px;");

        VBox warningText = new VBox(2, warningTitle, warningValue);
        HBox allergyBox = new HBox(10, warningIcon, warningText);
        allergyBox.setAlignment(Pos.CENTER_LEFT);
        allergyBox.setStyle("-fx-background-color:#fef2f2; -fx-border-color:#fca5a5; -fx-border-radius:8px; -fx-background-radius:8px; -fx-padding:10px 14px;");

        return createCard(headerRow, statsGrid, allergyBox);
    }

    private VBox createMedicalReportsCard(){
        Text title = new Text("Medical Reports");
        title.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        Button uploadBtn = new Button("📄 Upload Report");
        uploadBtn.setStyle("-fx-background-color:#1d4ed8; -fx-text-fill:white; -fx-font-weight:bold; -fx-background-radius:6px; -fx-padding:8px 14px; -fx-cursor:hand;");
        addHoverAnimation(uploadBtn);

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        HBox cardHeader = new HBox(title, sp, uploadBtn);
        cardHeader.setAlignment(Pos.CENTER_LEFT);

        // Filter search bar & dropdown
        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Search reports...");
        searchField.setStyle("-fx-background-color:#f1f5f9; -fx-border-color:#cbd5e1; -fx-border-radius:6px; -fx-padding:6px 12px;");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        ComboBox<String> filterCombo = new ComboBox<>();
        filterCombo.getItems().addAll("All Types", "Lab Tests", "X-Ray / Scan", "Consultation");
        filterCombo.getSelectionModel().selectFirst();
        filterCombo.setStyle("-fx-background-color:#f1f5f9; -fx-border-color:#cbd5e1; -fx-border-radius:6px; -fx-padding:4px;");

        HBox filterRow = new HBox(12, searchField, filterCombo);

        // Report Rows
        HBox r1 = createReportRow("📄", "Comprehensive Blood Panel", "Oct 24, 2023 • Quest Diagnostics • PDF (2.4 MB)");
        HBox r2 = createReportRow("🩺", "Chest X-Ray Results", "Sep 12, 2023 • City General Hospital • JPG (5.1 MB)");
        HBox r3 = createReportRow("📋", "Cardiology Consultation Notes", "Aug 05, 2023 • Dr. H. McCoy • PDF (1.1 MB)");

        VBox reportsList = new VBox(10, r1, r2, r3);

        return createCard(cardHeader, filterRow, reportsList);
    }

    private VBox createStatBox(String labelText, String valueText){
        Text label = createSubText(labelText);
        Text value = new Text(valueText);
        value.setStyle("-fx-font-weight:bold; -fx-font-size:14px; -fx-fill:#0f172a;");

        VBox box = new VBox(4, label, value);
        box.setStyle("-fx-background-color:#f8fafc; -fx-border-color:#e2e8f0; -fx-border-radius:8px; -fx-background-radius:8px; -fx-padding:10px;");
        return box;
    }

    private HBox createReportRow(String icon, String titleText, String subDetails){
        Circle iconBg = new Circle(16);
        iconBg.setStyle("-fx-fill:#dbeafe;");
        Text iconText = new Text(icon);
        iconText.setStyle("-fx-font-size:13px;");
        StackPane iconPane = new StackPane(iconBg, iconText);

        Text title = new Text(titleText);
        title.setStyle("-fx-font-weight:bold; -fx-fill:#0f172a; -fx-font-size:13px;");

        Text sub = createSubText(subDetails);
        VBox textBox = new VBox(2, title, sub);

        HBox row = new HBox(12, iconPane, textBox);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color:#f8fafc; -fx-padding:10px 12px; -fx-background-radius:8px; -fx-cursor:hand;");
        addHoverAnimation(row);
        return row;
    }

    private Text createSubText(String text){
        Text t = new Text(text);
        t.setStyle("-fx-fill:#64748b; -fx-font-size:11px;");
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