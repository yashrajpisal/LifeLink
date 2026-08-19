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
import javafx.stage.Stage;

public class Sidebar {

    public enum Page {
        DASHBOARD,
        FIND_HOSPITALS,
        EMERGENCY,
        FIRST_AID,
        // APPOINTMENTS,
        MEDICAL_HISTORY,
        SAVED_HOSPITALS,
        SETTINGS
    }

    public static VBox build(Stage stage, Page activePage) {

        Label logo = new Label("💙 LifeLink");
        logo.getStyleClass().add("logo-label");

        HBox profileBox = buildProfileBox();

        Button emergencyHelpBtn = new Button("🆘  Emergency Help");
        emergencyHelpBtn.getStyleClass().add("emergency-btn");
        emergencyHelpBtn.setMaxWidth(Double.MAX_VALUE);
        emergencyHelpBtn.setOnAction(e ->
                navigateTo(stage, new EmergencyServices().setBorderPane(stage)));

        Button dashboardBtn = new Button("🏠  Dashboard");
        Button findHospitalsBtn = new Button("➕  Find Hospitals");
        Button emergencyBtn = new Button("✳  Emergency Services");
        Button firstAidBtn = new Button("🩹  First-Aid Assistant");
        // Button appointmentsBtn = new Button("📅  Appointments");
        Button medicalHistoryBtn = new Button("🕘  Medical Details");
        Button savedHospitalsBtn = new Button("🔖  Saved Hospitals");
        Button settingsBtn = new Button("⚙  Settings");

        Button[] navButtons = {
                dashboardBtn, findHospitalsBtn, emergencyBtn, firstAidBtn,
                /*appointmentsBtn,*/ medicalHistoryBtn, savedHospitalsBtn, settingsBtn
        };

        for (Button b : navButtons) {
            b.getStyleClass().add("nav-button");
            b.setMaxWidth(Double.MAX_VALUE);
            b.setAlignment(Pos.CENTER_LEFT);
        }

        highlightIfActive(dashboardBtn, activePage == Page.DASHBOARD);
        highlightIfActive(findHospitalsBtn, activePage == Page.FIND_HOSPITALS);
        highlightIfActive(emergencyBtn, activePage == Page.EMERGENCY);
        highlightIfActive(firstAidBtn, activePage == Page.FIRST_AID);
        // highlightIfActive(appointmentsBtn, activePage == Page.APPOINTMENTS);
        highlightIfActive(medicalHistoryBtn, activePage == Page.MEDICAL_HISTORY);
        highlightIfActive(savedHospitalsBtn, activePage == Page.SAVED_HOSPITALS);
        highlightIfActive(settingsBtn, activePage == Page.SETTINGS);

        // ---------------- NAVIGATION WIRING ----------------
        
        dashboardBtn.setOnAction(e ->
                navigateTo(stage, new FamilyHomePage().setBorderPane(stage)));

        findHospitalsBtn.setOnAction(e ->
                navigateTo(stage, new FamilyFindCare().setBorderPane(stage)));

        emergencyBtn.setOnAction(e ->
                navigateTo(stage, new EmergencyServices().setBorderPane(stage)));

        firstAidBtn.setOnAction(e ->
                navigateTo(stage, new FirstAidAssistant().setBorderPane(stage)));

        savedHospitalsBtn.setOnAction(e ->
                navigateTo(stage, new SavedHospitals().setBorderPane(stage)));

        settingsBtn.setOnAction(e ->
                navigateTo(stage, new SettingsPage().setBorderPane(stage)));

        medicalHistoryBtn.setOnAction(e -> 
            navigateTo(stage, new MedicalReports().setBorderPane(stage))
            
        );

        VBox navBox = new VBox(4, navButtons);

        // Spacer pushes Logout to the bottom of the sidebar
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button logoutBtn = new Button("↩  Logout");
        logoutBtn.getStyleClass().add("nav-button");
        logoutBtn.setMaxWidth(Double.MAX_VALUE);
        logoutBtn.setOnAction(e -> System.out.println("Logout clicked"));

        VBox sidebar = new VBox(16, logo, profileBox,  navBox, spacer, emergencyHelpBtn,logoutBtn);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(230);
        sidebar.setPadding(new Insets(20, 14, 20, 14));

        return sidebar;
    }

    private static HBox buildProfileBox() {

        Image img = new Image("https://ui-avatars.com/api/?name=Sarah+Miller&background=6C63FF&color=fff");
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(38);
        imageView.setFitHeight(38);

        Circle clip = new Circle(19, 19, 19);
        imageView.setClip(clip);

        Label name = new Label("Sarah Miller");
        name.getStyleClass().add("profile-name");

        Label role = new Label("Family Care Lead");
        role.getStyleClass().add("profile-role");

        VBox textBox = new VBox(2, name, role);

        HBox box = new HBox(10, imageView, textBox);
        box.setAlignment(Pos.CENTER_LEFT);

        return box;
    }

    private static void highlightIfActive(Button button, boolean active) {
        if (active) {
            button.getStyleClass().add("nav-button-active");
        }
    }

    private static void navigateTo(Stage stage, BorderPane newRoot) {
        stage.getScene().setRoot(newRoot);
    }
}
