// package com.kurukshetra.view.family;

// import javafx.geometry.Pos;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;

// public class Profile {
    
//     public VBox getProfileVBox(){

//         Text titel = new Text("Profile");
//         titel.setStyle("-fx-font-size: 18px; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-background-color: #e1f0f9; -fx-padding:10px 10px");

//         VBox vmain = new VBox(titel);
//         vmain.setAlignment(Pos.CENTER);
//         return vmain;
//     }
// }

package com.kurukshetra.view.family;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Profile {

    public ScrollPane getProfileVBox(){

        // Top Header
        Text headerTitle = new Text("My Profile");
        headerTitle.setStyle("-fx-font-size:22px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        Text headerSub = new Text("Manage your personal information and health preferences");
        headerSub.setStyle("-fx-fill:#64748b; -fx-font-size:13px;");

        VBox titleBox = new VBox(3, headerTitle, headerSub);

        Button editProfileBtn = new Button("✏ Edit Profile");
        editProfileBtn.setStyle("-fx-background-color:#1d4ed8; -fx-text-fill:white; -fx-font-weight:bold; -fx-background-radius:8px; -fx-padding:10px 18px; -fx-cursor:hand;");
        addHoverAnimation(editProfileBtn);

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        HBox topBar = new HBox(10, titleBox, topSpacer, editProfileBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-padding:0 0 15px 0;");

        // Row 1: Main Avatar Card + Personal Information
        VBox profileCard = createProfileAvatarCard();
        VBox personalInfoCard = createPersonalInfoCard();
        HBox.setHgrow(personalInfoCard, Priority.ALWAYS);

        HBox row1 = new HBox(20, profileCard, personalInfoCard);

        // Row 2: Medical Profile + Emergency Contacts
        VBox medicalCard = createMedicalProfileCard();
        VBox contactsCard = createEmergencyContactsCard();
        HBox.setHgrow(medicalCard, Priority.ALWAYS);
        HBox.setHgrow(contactsCard, Priority.ALWAYS);

        HBox row2 = new HBox(20, medicalCard, contactsCard);

        // Row 3 (Extra Added Features): Insurance & Primary Care + Emergency Directives
        VBox insuranceCard = createInsuranceCard();
        VBox directivesCard = createDirectivesCard();
        HBox.setHgrow(insuranceCard, Priority.ALWAYS);
        HBox.setHgrow(directivesCard, Priority.ALWAYS);

        HBox row3 = new HBox(20, insuranceCard, directivesCard);

        VBox mainContent = new VBox(20, topBar, row1, row2, row3);
        mainContent.setStyle("-fx-padding:25px; -fx-background-color:#f8fafc;");

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color:transparent; -fx-background:#f8fafc;");
        return scrollPane;
    }

    private VBox createProfileAvatarCard(){
        Circle avatarBg = new Circle(45);
        avatarBg.setStyle("-fx-fill:#3b82f6;");
        Text initial = new Text("AT");
        initial.setStyle("-fx-fill:white; -fx-font-size:28px; -fx-font-weight:bold;");
        StackPane avatarPane = new StackPane(avatarBg, initial);

        Text name = new Text("Alexander Thorne");
        name.setStyle("-fx-font-size:18px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        Text subText = createSubText("Member since October 2023");

        Button changePhotoBtn = new Button("Change Profile Photo");
        changePhotoBtn.setStyle("-fx-background-color:transparent; -fx-text-fill:#2563eb; -fx-font-weight:bold; -fx-cursor:hand;");
        addHoverAnimation(changePhotoBtn);

        VBox card = createCard(avatarPane, name, subText, changePhotoBtn);
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(280);
        return card;
    }

    private VBox createPersonalInfoCard(){
        Text title = new Text("Personal Information");
        title.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        Text infoIcon = new Text("ⓘ");
        infoIcon.setStyle("-fx-fill:#94a3b8; -fx-font-size:16px;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        HBox cardHeader = new HBox(title, sp, infoIcon);

        VBox col1 = new VBox(12, createFieldGroup("FULL NAME", "Alexander J. Thorne"), createFieldGroup("PHONE NUMBER", "+1 (555) 902-4421"));
        VBox col2 = new VBox(12, createFieldGroup("EMAIL ADDRESS", "a.thorne@example.com"), createFieldGroup("ADDRESS", "742 Evergreen Terrace, Springfield, IL"));
        HBox.setHgrow(col1, Priority.ALWAYS);
        HBox.setHgrow(col2, Priority.ALWAYS);

        HBox grid = new HBox(30, col1, col2);

        return createCard(cardHeader, grid);
    }

    private VBox createMedicalProfileCard(){
        Text title = new Text("Medical Profile");
        title.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        Text badge = new Text("VERIFIED");
        badge.setStyle("-fx-fill:#16a34a; -fx-font-size:10px; -fx-font-weight:bold; -fx-background-color:#dcfce7; -fx-padding:3px 8px; -fx-background-radius:10px;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        HBox cardHeader = new HBox(title, sp, badge);

        HBox bloodRow = createKeyValueRow("Blood Type", "O Positive (O+)", "#0f172a");
        HBox allergyRow = createKeyValueRow("Allergies", "Penicillin, Peanuts", "#dc2626");
        HBox chronicRow = createKeyValueRow("Chronic Conditions", "None", "#0f172a");

        Button historyBtn = new Button("View Medical History →");
        historyBtn.setStyle("-fx-background-color:transparent; -fx-text-fill:#2563eb; -fx-font-weight:bold; -fx-cursor:hand; -fx-padding:5px 0 0 0;");
        addHoverAnimation(historyBtn);

        return createCard(cardHeader, bloodRow, allergyRow, chronicRow, historyBtn);
    }

    private VBox createEmergencyContactsCard(){
        Text title = new Text("Emergency Contacts");
        title.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        VBox contact1 = createContactItem("ST", "Sarah Thorne", "Spouse • +1 (555) 123-4567", "#16a34a", "#dcfce7");
        VBox contact2 = createContactItem("MT", "Michael Thorne", "Brother • +1 (555) 987-6543", "#2563eb", "#dbeafe");

        Button addContactBtn = new Button("+ Add Another Contact");
        addContactBtn.setMaxWidth(Double.MAX_VALUE);
        addContactBtn.setStyle("-fx-background-color:transparent; -fx-border-color:#cbd5e1; -fx-border-style:dashed; -fx-border-radius:8px; -fx-text-fill:#475569; -fx-padding:8px; -fx-cursor:hand;");
        addHoverAnimation(addContactBtn);

        return createCard(title, contact1, contact2, addContactBtn);
    }

    private VBox createInsuranceCard(){
        Text title = new Text("Insurance & Primary Care");
        title.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        HBox provider = createKeyValueRow("Insurance Provider", "Blue Cross Shield", "#0f172a");
        HBox policy = createKeyValueRow("Policy Number", "POL-99238411", "#0f172a");
        HBox physician = createKeyValueRow("Primary Physician", "Dr. Emily Roberts", "#0f172a");

        return createCard(title, provider, policy, physician);
    }

    private VBox createDirectivesCard(){
        Text title = new Text("Emergency Directives");
        title.setStyle("-fx-font-size:16px; -fx-font-weight:bold; -fx-fill:#0f172a;");

        HBox donor = createKeyValueRow("Organ Donor Status", "Registered Donor", "#16a34a");
        HBox dnr = createKeyValueRow("DNR Order", "No DNR On File", "#0f172a");
        HBox access = createKeyValueRow("Emergency Access Note", "Keycard with Front Desk", "#64748b");

        return createCard(title, donor, dnr, access);
    }

    private VBox createContactItem(String initials, String name, String details, String color, String bgColor){
        Circle iconBg = new Circle(16);
        iconBg.setStyle("-fx-fill:" + bgColor + ";");
        Text iconText = new Text(initials);
        iconText.setStyle("-fx-fill:" + color + "; -fx-font-weight:bold; -fx-font-size:11px;");
        StackPane iconPane = new StackPane(iconBg, iconText);

        Text nameText = new Text(name);
        nameText.setStyle("-fx-font-weight:bold; -fx-fill:#0f172a;");

        Text sub = createSubText(details);
        VBox textBox = new VBox(2, nameText, sub);

        Button callBtn = new Button("📞");
        callBtn.setStyle("-fx-background-color:transparent; -fx-cursor:hand;");

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);

        HBox row = new HBox(10, iconPane, textBox, sp, callBtn);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color:#f1f5f9; -fx-padding:10px; -fx-background-radius:8px;");

        VBox box = new VBox(row);
        addHoverAnimation(box);
        return box;
    }

    private VBox createFieldGroup(String label, String value){
        Text l = new Text(label);
        l.setStyle("-fx-fill:#94a3b8; -fx-font-size:11px; -fx-font-weight:bold;");
        Text v = new Text(value);
        v.setStyle("-fx-fill:#0f172a; -fx-font-size:14px; -fx-font-weight:bold;");
        return new VBox(4, l, v);
    }

    private HBox createKeyValueRow(String key, String val, String valColor){
        Text k = new Text(key);
        k.setStyle("-fx-fill:#64748b; -fx-font-size:13px;");
        Text v = new Text(val);
        v.setStyle("-fx-fill:" + valColor + "; -fx-font-weight:bold; -fx-font-size:13px;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        HBox row = new HBox(k, sp, v);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
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