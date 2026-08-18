// package com.kurukshetra.view.family;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.VBox;
// import javafx.scene.shape.Circle;
// import javafx.stage.Stage;

// public class Sidebar {

//     public enum Page {
//         DASHBOARD,
//         FIND_HOSPITALS,
//         EMERGENCY,
//         FIRST_AID,
//         // APPOINTMENTS,
//         MEDICAL_HISTORY,
//         SAVED_HOSPITALS,
//         SETTINGS
//     }

//     public static VBox build(Stage stage, Page activePage) {

//         Label logo = new Label("💙 LifeLink");
//         logo.getStyleClass().add("logo-label");

//         HBox profileBox = buildProfileBox();

//         Button emergencyHelpBtn = new Button("🆘  Emergency Help");
//         emergencyHelpBtn.getStyleClass().add("emergency-btn");
//         emergencyHelpBtn.setMaxWidth(Double.MAX_VALUE);
//         emergencyHelpBtn.setOnAction(e ->
//                 navigateTo(stage, new EmergencyServices().setBorderPane(stage)));

//         Button dashboardBtn = new Button("🏠  Dashboard");
//         Button findHospitalsBtn = new Button("➕  Find Hospitals");
//         Button emergencyBtn = new Button("✳  Emergency Services");
//         Button firstAidBtn = new Button("🩹  First-Aid Assistant");
//         // Button appointmentsBtn = new Button("📅  Appointments");
//         Button medicalHistoryBtn = new Button("🕘  Medical Details");
//         Button savedHospitalsBtn = new Button("🔖  Saved Hospitals");
//         Button settingsBtn = new Button("⚙  Settings");

//         Button[] navButtons = {
//                 dashboardBtn, findHospitalsBtn, emergencyBtn, firstAidBtn,
//                 /*appointmentsBtn,*/ medicalHistoryBtn, savedHospitalsBtn, settingsBtn
//         };

//         for (Button b : navButtons) {
//             b.getStyleClass().add("nav-button");
//             b.setMaxWidth(Double.MAX_VALUE);
//             b.setAlignment(Pos.CENTER_LEFT);
//         }

//         highlightIfActive(dashboardBtn, activePage == Page.DASHBOARD);
//         highlightIfActive(findHospitalsBtn, activePage == Page.FIND_HOSPITALS);
//         highlightIfActive(emergencyBtn, activePage == Page.EMERGENCY);
//         highlightIfActive(firstAidBtn, activePage == Page.FIRST_AID);
//         // highlightIfActive(appointmentsBtn, activePage == Page.APPOINTMENTS);
//         highlightIfActive(medicalHistoryBtn, activePage == Page.MEDICAL_HISTORY);
//         highlightIfActive(savedHospitalsBtn, activePage == Page.SAVED_HOSPITALS);
//         highlightIfActive(settingsBtn, activePage == Page.SETTINGS);

//         // ---------------- NAVIGATION WIRING ----------------
        
//         dashboardBtn.setOnAction(e ->
//                 navigateTo(stage, new FamilyHomePage().setBorderPane(stage)));

//         findHospitalsBtn.setOnAction(e ->
//                 navigateTo(stage, new FamilyFindCare().setBorderPane(stage)));

//         emergencyBtn.setOnAction(e ->
//                 navigateTo(stage, new EmergencyServices().setBorderPane(stage)));

//         firstAidBtn.setOnAction(e ->
//                 navigateTo(stage, new FirstAidAssistant().setBorderPane(stage)));

//         savedHospitalsBtn.setOnAction(e ->
//                 navigateTo(stage, new SavedHospitals().setBorderPane(stage)));

//         settingsBtn.setOnAction(e ->
//                 navigateTo(stage, new SettingsPage().setBorderPane(stage)));

//         medicalHistoryBtn.setOnAction(e -> 
//             navigateTo(stage, new MedicalReports().setBorderPane(stage))
            
//         );

//         VBox navBox = new VBox(4, navButtons);

//         // Spacer pushes Logout to the bottom of the sidebar
//         Region spacer = new Region();
//         VBox.setVgrow(spacer, Priority.ALWAYS);

//         Button logoutBtn = new Button("↩  Logout");
//         logoutBtn.getStyleClass().add("nav-button");
//         logoutBtn.setMaxWidth(Double.MAX_VALUE);
//         logoutBtn.setOnAction(e -> System.out.println("Logout clicked"));

//         VBox sidebar = new VBox(16, logo, profileBox,  navBox, spacer, emergencyHelpBtn,logoutBtn);
//         sidebar.getStyleClass().add("sidebar");
//         sidebar.setPrefWidth(230);
//         sidebar.setPadding(new Insets(20, 14, 20, 14));

//         return sidebar;
//     }

//     private static HBox buildProfileBox() {

//         Image img = new Image("https://ui-avatars.com/api/?name=Sarah+Miller&background=6C63FF&color=fff");
//         ImageView imageView = new ImageView(img);
//         imageView.setFitWidth(38);
//         imageView.setFitHeight(38);

//         Circle clip = new Circle(19, 19, 19);
//         imageView.setClip(clip);

//         Label name = new Label("Sarah Miller");
//         name.getStyleClass().add("profile-name");

//         Label role = new Label("Family Care Lead");
//         role.getStyleClass().add("profile-role");

//         VBox textBox = new VBox(2, name, role);

//         HBox box = new HBox(10, imageView, textBox);
//         box.setAlignment(Pos.CENTER_LEFT);

//         return box;
//     }

//     private static void highlightIfActive(Button button, boolean active) {
//         if (active) {
//             button.getStyleClass().add("nav-button-active");
//         }
//     }

//     private static void navigateTo(Stage stage, BorderPane newRoot) {
//         stage.getScene().setRoot(newRoot);
//     }
// }


package com.kurukshetra.view.family;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Sidebar {

    private static final String PRIMARY = "#CA6721";
    private static final String PRIMARY_DARK = "#782F16";
    private static final String SURFACE = "#FFFFFF";
    private static final String VERY_LIGHT_TERRACOTTA = "#FDF0E8";
    private static final String LIGHT_TERRACOTTA = "#F9E4D6";
    private static final String TEXT_PRIMARY = "#2B211D";
    private static final String TEXT_SECONDARY = "#675B55";
    private static final String BORDER_COLOR = "#EDE0D8";
    private static final String DANGER_BG = "#FCE8E7";
    private static final String DANGER_TEXT = "#C94F4F";

    public enum Page {
        DASHBOARD,
        FIND_HOSPITALS,
        EMERGENCY,
        FIRST_AID,
        MEDICAL_HISTORY,
        SAVED_HOSPITALS,
        SETTINGS
    }

    public static VBox build(Stage stage, Page activePage) {
        Label logo = new Label("💙 LifeLink");
        logo.setFont(Font.font("System", FontWeight.BOLD, 20));
        logo.setStyle("-fx-text-fill: " + PRIMARY_DARK + ";");

        HBox profileBox = buildProfileBox();

        Button emergencyHelpBtn = new Button("🆘  Emergency Help");
        emergencyHelpBtn.setStyle("-fx-background-color: " + DANGER_BG + "; -fx-text-fill: " + DANGER_TEXT + "; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 12px; -fx-background-radius: 8px; -fx-cursor: hand;");
        emergencyHelpBtn.setOnMouseEntered(e -> emergencyHelpBtn.setStyle("-fx-background-color: #F8D3D1; -fx-text-fill: " + DANGER_TEXT + "; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 12px; -fx-background-radius: 8px; -fx-cursor: hand;"));
        emergencyHelpBtn.setOnMouseExited(e -> emergencyHelpBtn.setStyle("-fx-background-color: " + DANGER_BG + "; -fx-text-fill: " + DANGER_TEXT + "; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 12px; -fx-background-radius: 8px; -fx-cursor: hand;"));
        emergencyHelpBtn.setMaxWidth(Double.MAX_VALUE);
        emergencyHelpBtn.setOnAction(e -> navigateTo(stage, new EmergencyServices().setBorderPane(stage)));

        Button dashboardBtn = createNavButton("🏠  Dashboard", activePage == Page.DASHBOARD);
        Button findHospitalsBtn = createNavButton("➕  Find Hospitals", activePage == Page.FIND_HOSPITALS);
        Button emergencyBtn = createNavButton("✳  Emergency Services", activePage == Page.EMERGENCY);
        Button firstAidBtn = createNavButton("🩹  First-Aid Assistant", activePage == Page.FIRST_AID);
        Button medicalHistoryBtn = createNavButton("🕘  Medical Details", activePage == Page.MEDICAL_HISTORY);
        Button savedHospitalsBtn = createNavButton("🔖  Saved Hospitals", activePage == Page.SAVED_HOSPITALS);
        Button settingsBtn = createNavButton("⚙  Settings", activePage == Page.SETTINGS);

        dashboardBtn.setOnAction(e -> navigateTo(stage, new FamilyHomePage().setBorderPane(stage)));
        findHospitalsBtn.setOnAction(e -> navigateTo(stage, new FamilyFindCare().setBorderPane(stage)));
        emergencyBtn.setOnAction(e -> navigateTo(stage, new EmergencyServices().setBorderPane(stage)));
        firstAidBtn.setOnAction(e -> navigateTo(stage, new FirstAidAssistant().setBorderPane(stage)));
        savedHospitalsBtn.setOnAction(e -> navigateTo(stage, new SavedHospitals().setBorderPane(stage)));
        settingsBtn.setOnAction(e -> navigateTo(stage, new SettingsPage().setBorderPane(stage)));
        medicalHistoryBtn.setOnAction(e -> navigateTo(stage, new MedicalReports().setBorderPane(stage)));

        VBox navBox = new VBox(4, dashboardBtn, findHospitalsBtn, emergencyBtn, firstAidBtn, medicalHistoryBtn, savedHospitalsBtn, settingsBtn);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button logoutBtn = createNavButton("↩  Logout", false);
        logoutBtn.setOnAction(e -> System.out.println("Logout clicked"));

        VBox sidebar = new VBox(16, logo, profileBox, navBox, spacer, emergencyHelpBtn, logoutBtn);
        sidebar.setStyle(
                "-fx-background-color: " + SURFACE + ";" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-width: 0 1 0 0;"
        );
        sidebar.setPrefWidth(230);
        sidebar.setPadding(new Insets(20, 14, 20, 14));
        return sidebar;
    }

    private static Button createNavButton(String text, boolean active) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);

        if (active) {
            btn.setStyle(
                    "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                    "-fx-text-fill: " + PRIMARY_DARK + ";" +
                    "-fx-font-size: 13px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-padding: 10px 12px;" +
                    "-fx-background-radius: 8px;" +
                    "-fx-cursor: hand;"
            );
        } else {
            btn.setStyle(
                    "-fx-background-color: transparent;" +
                    "-fx-text-fill: " + TEXT_SECONDARY + ";" +
                    "-fx-font-size: 13px;" +
                    "-fx-padding: 10px 12px;" +
                    "-fx-background-radius: 8px;" +
                    "-fx-cursor: hand;"
            );
            btn.setOnMouseEntered(e -> btn.setStyle(
                    "-fx-background-color: #FFF5EF;" +
                    "-fx-text-fill: " + PRIMARY_DARK + ";" +
                    "-fx-font-size: 13px;" +
                    "-fx-padding: 10px 12px;" +
                    "-fx-background-radius: 8px;" +
                    "-fx-cursor: hand;"
            ));
            btn.setOnMouseExited(e -> btn.setStyle(
                    "-fx-background-color: transparent;" +
                    "-fx-text-fill: " + TEXT_SECONDARY + ";" +
                    "-fx-font-size: 13px;" +
                    "-fx-padding: 10px 12px;" +
                    "-fx-background-radius: 8px;" +
                    "-fx-cursor: hand;"
            ));
        }
        return btn;
    }

    private static HBox buildProfileBox() {
        Image img = new Image("https://ui-avatars.com/api/?name=Sarah+Miller&background=CA6721&color=fff");
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(38);
        imageView.setFitHeight(38);

        Circle clip = new Circle(19, 19, 19);
        imageView.setClip(clip);

        Label name = new Label("Sarah Miller");
        name.setFont(Font.font("System", FontWeight.BOLD, 13));
        name.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label role = new Label("Family Care Lead");
        role.setStyle("-fx-font-size: 11px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        VBox textBox = new VBox(2, name, role);
        HBox box = new HBox(10, imageView, textBox);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private static void navigateTo(Stage stage, BorderPane newRoot) {
        stage.getScene().setRoot(newRoot);
    }
}