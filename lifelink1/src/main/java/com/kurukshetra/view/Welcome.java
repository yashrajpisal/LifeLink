package com.kurukshetra.view;

import com.kurukshetra.view.hospital.DoctorManagementView;
import com.kurukshetra.view.hospital.EmergencyRequestsView;
import com.kurukshetra.view.hospital.OperationTheatreView;
import com.kurukshetra.view.hospital.ResourceManagementView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Welcome extends Application{

    public Stage welcomStage;

    @Override
    public void start(Stage stage) {

        this.welcomStage = stage;
        BorderPane root = new BorderPane();

        Sidebar sidebar = new Sidebar(Sidebar.NavItem.EMERGENCY_REQUESTS);
        root.setLeft(sidebar);

        sidebar.setOnNavigate(item -> showView(root, sidebar, item));
        showView(root, sidebar, Sidebar.NavItem.EMERGENCY_REQUESTS);

        Scene scene = new Scene(root, 1200, 700);
        scene.setFill(null);
        stage.setTitle("LifeLink - Hospital Management");
        stage.setScene(scene);
        stage.setMinWidth(920);
        stage.setMinHeight(620);
        stage.show();
    }

    private void showView(BorderPane root, Sidebar sidebar, Sidebar.NavItem item) {
        // Keep sidebar highlight in sync with the currently displayed page
        sidebar.setActiveItem(item);

        switch (item) {
            case DASHBOARD:
                //root.setCenter(new DashboardView());
                break;
            case EMERGENCY_REQUESTS:
                root.setCenter(new EmergencyRequestsView());
                break;
            case RESOURCE_MANAGEMENT:
                root.setCenter(new ResourceManagementView());
                break;
            case DOCTOR_MANAGEMENT:
                root.setCenter(new DoctorManagementView());
                break;
            case OPERATION_THEATRE:
                root.setCenter(new OperationTheatreView());
                break;
            case PATIENT_RECORDS:
                // root.setCenter(new PatientRecordsView());
                break;
            case AMBULANCE_TRACKING:
                // root.setCenter(new AmbulanceTrackingView());
                break;
            case NOTIFICATIONS:
                // root.setCenter(new NotificationsView());
                break;
            case ANALYTICS:
                // root.setCenter(new AnalyticsView());
                break;
            case SETTINGS:
                // root.setCenter(new SettingsView());
                break;
        }
    }
    
}
