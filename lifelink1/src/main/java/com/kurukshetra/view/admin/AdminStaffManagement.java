package com.kurukshetra.view.admin;

import com.kurukshetra.view.util.ShimmerLoader;
import com.kurukshetra.view.util.ShimmerLoader.ShimmerPane;
import com.kurukshetra.controller.admin.AdminAmbulanceAssignmentController;
import com.kurukshetra.model.admin.AdminAmbulanceAssignmentModel;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.controller.driverController.DriverStaffController;
import com.kurukshetra.controller.nurseController.NurseStaffController;
import com.kurukshetra.dao.driverDao.DriverStaffDao;
import com.kurukshetra.dao.nurseDao.NurseStaffDao;
import com.kurukshetra.model.driverModel.DriverStaffModel;
import com.kurukshetra.model.nurseModel.NurseStaffModel;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import java.util.concurrent.ExecutionException;

public class AdminStaffManagement {

    // Design Tokens - LifeLink Pastel Purple Theme
    private static final String BG_PAGE = "#FAF7FB";
    private static final String BG_SURFACE = "#FFFFFF";
    private static final String BORDER_COLOR = "#E9E2EF";
    private static final String BORDER_DIVIDER = "#F0E7F5";

    private static final String TEXT_PRIMARY = "#0F172A";
    private static final String TEXT_SECONDARY = "#5F5A70";
    private static final String TEXT_MUTED = "#8B8798";

    private static final String PURPLE_PRIMARY = "#9C7DF0";
    private static final String PURPLE_DARK = "#8B68E5";
    private static final String PURPLE_BUTTON = "#C084FC";
    private static final String PURPLE_LIGHT = "#F3E8FF";
    private static final String PURPLE_VARIANT = "#E9D5FF";

    private static final String SUCCESS_TEXT = "#15803D";
    private static final String SUCCESS_BG = "#DCFCE7";

    private static final String WARNING_TEXT = "#A16207";
    private static final String WARNING_BG = "#FEF3C7";

    private static final String DANGER_TEXT = "#E66A7A";
    private static final String DANGER_BG = "#FDE7EB";
    private static final String DANGER_BORDER = "#FCCED5";

    private static final String CARD_SHADOW = "-fx-effect: dropshadow(gaussian, rgba(156, 125, 240, 0.08), 16, 0.1, 0, 4);";
    private static final String FONT_STACK = "-fx-font-family: 'Segoe UI', 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;";

    private static final String BASE_CARD_STYLE = FONT_STACK +
            "-fx-background-color: " + BG_SURFACE + ";" +
            "-fx-background-radius: 16px;" +
            "-fx-border-color: " + BORDER_COLOR + ";" +
            "-fx-border-radius: 16px;" +
            "-fx-border-width: 1px;" +
            CARD_SHADOW;

    private static final String PRIMARY_BUTTON_STYLE = FONT_STACK +
            "-fx-background-color: " + PURPLE_BUTTON + ";" +
            "-fx-text-fill: #FFFFFF;" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10px;" +
            "-fx-effect: dropshadow(gaussian, rgba(192, 132, 252, 0.35), 10, 0.2, 0, 3);" +
            "-fx-cursor: hand;";

    private static final String SECONDARY_BUTTON_STYLE = FONT_STACK +
            "-fx-background-color: " + BG_SURFACE + ";" +
            "-fx-text-fill: " + PURPLE_DARK + ";" +
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-color: " + BORDER_COLOR + ";" +
            "-fx-border-radius: 10px;" +
            "-fx-background-radius: 10px;" +
            "-fx-cursor: hand;";


    private final DriverStaffDao driverStaffDao =
            new DriverStaffDao(
                    FirebaseConfig.getFirestore()
            );

    private final NurseStaffDao nurseStaffDao =
            new NurseStaffDao(
                    FirebaseConfig.getFirestore()
            );

    private final DriverStaffController driverStaffController =
            new DriverStaffController(
                    driverStaffDao
            );

    private final NurseStaffController nurseStaffController =
            new NurseStaffController(
                    nurseStaffDao
            );

    private final List<String> selectedDrivers =
            new ArrayList<>();

    private final List<String> selectedNurses =
            new ArrayList<>();



        private ComboBox<String> ambulanceIdCombo;
        private ComboBox<DriverStaffModel> assignmentDriverCombo;
        private ComboBox<NurseStaffModel> assignmentNurseCombo;

        private Label assignedDriverLabel;
        private Label assignedNurseLabel;

        private AdminAmbulanceAssignmentController assignmentController = new AdminAmbulanceAssignmentController();


    private VBox driverGridContainer;
    private VBox nurseGridContainer;

    private Text driverAvailableValue;
    private Text driverBusyValue;
    private Text driverLeaveValue;
    private Text driverSelectedValue;

    private Text nurseAvailableValue;
    private Text nurseBusyValue;
    private Text nurseLeaveValue;
    private Text nurseSelectedValue;

    private Label driverCountLabel;
    private Label nurseCountLabel;

    private Button selectedDriverButton;
    private Button selectedNurseButton;

    private Label selectedDriverCount;
    private Label selectedNurseCount;

    private HBox selectedDriverNamesBox;
    private HBox selectedNurseNamesBox;

    public AdminStaffManagement() {
    }

    private void showAddAmbulanceDialog() {

        Stage dialog = new Stage();

        dialog.setTitle("Add Ambulance ID");

        VBox box = new VBox(15);
        box.setPadding(new Insets(20));

        Label title = new Label("Add Ambulance ID");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField ambulanceIdField = new TextField();
        ambulanceIdField.setPromptText("Example: MH16-4411");
        ambulanceIdField.setPrefWidth(250);

        Button addButton = new Button("Add Ambulance");

        addButton.setOnAction(e -> {

                String ambulanceId = ambulanceIdField.getText().trim();

                if (ambulanceId.isEmpty()) {
                return;
                }

                if (!ambulanceIdCombo.getItems().contains(ambulanceId)) {

                ambulanceIdCombo.getItems().add(ambulanceId);
                ambulanceIdCombo.setValue(ambulanceId);
                }

                dialog.close();
        });

        box.getChildren().addAll(
                title,
                ambulanceIdField,
                addButton
        );

        Scene scene = new Scene(box, 320, 180);

        dialog.setScene(scene);
        dialog.show();
}


        private void loadAmbulanceAssignment() {

                String ambulanceId =
                        ambulanceIdCombo.getValue();

                if (ambulanceId == null ||
                        ambulanceId.isEmpty()) {

                        assignedDriverLabel.setText(
                                "Driver: Not Assigned"
                        );

                        assignedNurseLabel.setText(
                                "Nurse: Not Assigned"
                        );

                        return;
                }

                new Thread(() -> {

                        try {

                        AdminAmbulanceAssignmentModel assignment =
                                assignmentController.getAssignment(
                                        ambulanceId
                                );

                        Platform.runLater(() -> {

                                if (assignment == null) {

                                assignedDriverLabel.setText(
                                        "Driver: Not Assigned"
                                );

                                assignedNurseLabel.setText(
                                        "Nurse: Not Assigned"
                                );

                                assignmentDriverCombo.setValue(
                                        null
                                );

                                assignmentNurseCombo.setValue(
                                        null
                                );

                                return;
                                }

                                assignedDriverLabel.setText(
                                        "Driver: " +
                                        assignment.getDriverName()
                                );

                                assignedNurseLabel.setText(
                                        "Nurse: " +
                                        assignment.getNurseName()
                                );

                                for (
                                        DriverStaffModel driver :
                                        assignmentDriverCombo.getItems()) {

                                if (driver.getEmail().equals(
                                        assignment.getDriverEmail())) {

                                        assignmentDriverCombo.setValue(
                                                driver
                                        );

                                        break;
                                }
                                }

                                for (
                                        NurseStaffModel nurse :
                                        assignmentNurseCombo.getItems()) {

                                if (nurse.getEmail().equals(
                                        assignment.getNurseEmail())) {

                                        assignmentNurseCombo.setValue(
                                                nurse
                                        );

                                        break;
                                }
                                }
                        });

                        } catch (
                                ExecutionException |
                                InterruptedException ex) {

                        ex.printStackTrace();
                        }

                }).start();
        }





    public VBox getStaffManagement() {

        VBox mainContent = new VBox(20);

        mainContent.setPadding(
                new Insets(25)
        );

        mainContent.setStyle(
                "-fx-background-color: " + BG_PAGE + ";"
        );

        Text heading = new Text(
                "Drivers & Nurses Management"
        );

        heading.setStyle(
                "-fx-font-size: 28px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

        Text subHeading = new Text(
                "Manage ambulance drivers and nursing staff."
        );

        subHeading.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-fill: " + TEXT_SECONDARY + ";"
        );

        VBox headingBox = new VBox(5);

        headingBox.getChildren().addAll(
                heading,
                subHeading
        );

        Button addDriverButton = new Button(
                "+  Add Driver"
        );

        addDriverButton.setPrefHeight(40);

        addDriverButton.setStyle(
                PRIMARY_BUTTON_STYLE
        );

        addDriverButton.setOnAction(e ->
                showAddDriverDialog()
        );

        Button addNurseButton = new Button(
                "+  Add Nurse"
        );

        addNurseButton.setPrefHeight(40);

        addNurseButton.setStyle(
                PRIMARY_BUTTON_STYLE
        );

        addNurseButton.setOnAction(e ->
                showAddNurseDialog()
        );

        Region headerSpacer = new Region();

        HBox.setHgrow(
                headerSpacer,
                Priority.ALWAYS
        );

        HBox header = new HBox(10);

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        header.getChildren().addAll(
                headingBox,
                headerSpacer,
                addDriverButton,
                addNurseButton
        );






        VBox ambulanceAssignmentBox = new VBox(15);
        ambulanceAssignmentBox.setPadding(new Insets(20));
        ambulanceAssignmentBox.setStyle(
                BASE_CARD_STYLE
        );

        Text assignmentTitle = new Text(
                "Assign Staff to Ambulance"
        );

        assignmentTitle.setStyle(
                "-fx-font-size: 20px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

        Text assignmentSubtitle = new Text(
                "Select an ambulance and assign a driver and nurse."
        );

        assignmentSubtitle.setStyle(
                "-fx-font-size: 12px; " +
                "-fx-fill: " + TEXT_SECONDARY + ";"
        );

        assignedDriverLabel = new Label(
        "Driver: Not Assigned"
        );

        assignedDriverLabel.setStyle(
                "-fx-font-size: 12px; " +
                "-fx-text-fill: " + TEXT_PRIMARY + "; " +
                "-fx-font-weight: bold;"
        );

        assignedNurseLabel = new Label(
                "Nurse: Not Assigned"
        );

        assignedNurseLabel.setStyle(
                "-fx-font-size: 12px; " +
                "-fx-text-fill: " + TEXT_PRIMARY + "; " +
                "-fx-font-weight: bold;"
        );

        ambulanceIdCombo = new ComboBox<>();

        ambulanceIdCombo.getItems().addAll(
                "MH16-4411",
                "MH14-2233",
                "MH12-7788"
        );

        ambulanceIdCombo.setPromptText(
                "Select Ambulance ID"
        );

        ambulanceIdCombo.setPrefWidth(220);
        ambulanceIdCombo.setPrefHeight(40);

        ambulanceIdCombo.setOnAction(e -> {

                loadAmbulanceAssignment();
        
        });

        Button addAmbulanceButton = new Button(
                "+ Add Ambulance ID"
        );

        addAmbulanceButton.setPrefHeight(40);

        addAmbulanceButton.setStyle(
                SECONDARY_BUTTON_STYLE
        );

        addAmbulanceButton.setOnAction(e ->
                showAddAmbulanceDialog()
        );







        assignmentDriverCombo = new ComboBox<>();

        assignmentDriverCombo.setPromptText(
                "Select Driver"
        );

        assignmentDriverCombo.setPrefWidth(280);
        assignmentDriverCombo.setPrefHeight(40);

        assignmentDriverCombo.setCellFactory(listView -> new ListCell<DriverStaffModel>() {

        @Override
        protected void updateItem(DriverStaffModel driver, boolean empty) {

                super.updateItem(driver, empty);

                if (empty || driver == null) {

                setText(null);
                setOpacity(1.0);

                } else {

                setText(
                        driver.getName() +
                        "   [" +
                        driver.getStatus() +
                        "]"
                );

                if ("Available".equalsIgnoreCase(driver.getStatus())) {

                        setOpacity(1.0);

                } else {

                        setOpacity(0.45);
                }
                }
        }
        });

        assignmentDriverCombo.setButtonCell(
                new ListCell<DriverStaffModel>() {

                @Override
                protected void updateItem(
                        DriverStaffModel driver,
                        boolean empty) {

                        super.updateItem(driver, empty);

                        if (empty || driver == null) {

                        setText(null);

                        } else {

                        setText(
                                driver.getName() +
                                "   [" +
                                driver.getStatus() +
                                "]"
                        );
                        }
                }
                }
        );

        final DriverStaffModel[] previousDriver = {
                null
        };

        assignmentDriverCombo.setOnShowing(e -> {

        previousDriver[0] =
                assignmentDriverCombo.getValue();
        });

        assignmentDriverCombo.setOnAction(e -> {

        DriverStaffModel selectedDriver =
                assignmentDriverCombo.getValue();

        if (selectedDriver == null) {
                return;
        }

        if (!"Available".equalsIgnoreCase(
                selectedDriver.getStatus())) {

                Alert alert = new Alert(
                        Alert.AlertType.WARNING
                );

                alert.setTitle("Driver Already Selected");

                alert.setHeaderText(
                        "Driver Cannot Be Selected"
                );

                alert.setContentText(
                        "You cannot select this driver because " +
                        selectedDriver.getName() +
                        " is already " +
                        selectedDriver.getStatus().toLowerCase() +
                        ".\n\nPlease select another available driver."
                );

                alert.showAndWait();

                assignmentDriverCombo.setValue(
                        previousDriver[0]
                );

                return;
        }

        previousDriver[0] = selectedDriver;
        });


        assignmentNurseCombo = new ComboBox<>();

        assignmentNurseCombo.setPromptText(
                "Select Nurse"
        );

        assignmentNurseCombo.setPrefWidth(280);
        assignmentNurseCombo.setPrefHeight(40);

        assignmentNurseCombo.setCellFactory(listView -> new ListCell<NurseStaffModel>() {

        @Override
        protected void updateItem(NurseStaffModel nurse, boolean empty) {

                super.updateItem(nurse, empty);

                if (empty || nurse == null) {

                setText(null);
                setOpacity(1.0);

                } else {

                setText(
                        nurse.getName() +
                        "   [" +
                        nurse.getStatus() +
                        "]"
                );

                if ("Available".equalsIgnoreCase(nurse.getStatus())) {

                        setOpacity(1.0);

                } else {

                        setOpacity(0.45);
                }
                }
        }
        });

        assignmentNurseCombo.setButtonCell(
                new ListCell<NurseStaffModel>() {

                @Override
                protected void updateItem(
                        NurseStaffModel nurse,
                        boolean empty) {

                        super.updateItem(nurse, empty);

                        if (empty || nurse == null) {

                        setText(null);

                        } else {

                        setText(
                                nurse.getName() +
                                "   [" +
                                nurse.getStatus() +
                                "]"
                        );
                        }
                }
                }
        );

        final NurseStaffModel[] previousNurse = {
                null
        };

        assignmentNurseCombo.setOnShowing(e -> {

        previousNurse[0] =
                assignmentNurseCombo.getValue();
        });

        assignmentNurseCombo.setOnAction(e -> {

                NurseStaffModel selectedNurse =
                        assignmentNurseCombo.getValue();

                if (selectedNurse == null) {
                        return;
                }

                if (!"Available".equalsIgnoreCase(
                        selectedNurse.getStatus())) {

                        Alert alert = new Alert(
                                Alert.AlertType.WARNING
                        );

                        alert.setTitle("Nurse Already Selected");

                        alert.setHeaderText(
                                "Nurse Cannot Be Selected"
                        );

                        alert.setContentText(
                                "You cannot select this nurse because " +
                                selectedNurse.getName() +
                                " is already " +
                                selectedNurse.getStatus().toLowerCase() +
                                ".\n\nPlease select another available nurse."
                        );

                        alert.showAndWait();

                        assignmentNurseCombo.setValue(
                                previousNurse[0]
                        );

                        return;
                }

                previousNurse[0] = selectedNurse;
        });




        Button assignStaffButton = new Button(
                "Assign Staff"
        );

        assignStaffButton.setPrefWidth(150);
        assignStaffButton.setPrefHeight(40);

        assignStaffButton.setStyle(
                PRIMARY_BUTTON_STYLE
        );

        
        
        assignStaffButton.setOnAction(e -> {

                if (ambulanceIdCombo.getValue() == null) {

                        Alert alert =
                                new Alert(
                                        Alert.AlertType.WARNING
                                );

                        alert.setTitle(
                                "Ambulance Required"
                        );

                        alert.setHeaderText(
                                "Select Ambulance"
                        );

                        alert.setContentText(
                                "Please select an ambulance ID first."
                        );

                        alert.showAndWait();

                        return;
                }

                if (assignmentDriverCombo.getValue() == null) {

                        Alert alert =
                                new Alert(
                                        Alert.AlertType.WARNING
                                );

                        alert.setTitle(
                                "Driver Required"
                        );

                        alert.setHeaderText(
                                "Select Driver"
                        );

                        alert.setContentText(
                                "Please select an available driver."
                        );

                        alert.showAndWait();

                        return;
                }

                if (assignmentNurseCombo.getValue() == null) {

                        Alert alert =
                                new Alert(
                                        Alert.AlertType.WARNING
                                );

                        alert.setTitle(
                                "Nurse Required"
                        );

                        alert.setHeaderText(
                                "Select Nurse"
                        );

                        alert.setContentText(
                                "Please select an available nurse."
                        );

                        alert.showAndWait();

                        return;
                }

                DriverStaffModel selectedDriver =
                        assignmentDriverCombo.getValue();

                NurseStaffModel selectedNurse =
                        assignmentNurseCombo.getValue();

                if (!"Available".equalsIgnoreCase(
                        selectedDriver.getStatus())) {

                        Alert alert =
                                new Alert(
                                        Alert.AlertType.WARNING
                                );

                        alert.setTitle(
                                "Driver Already Selected"
                        );

                        alert.setHeaderText(
                                "Driver Cannot Be Assigned"
                        );

                        alert.setContentText(
                                selectedDriver.getName() +
                                " is already " +
                                selectedDriver.getStatus().toLowerCase() +
                                ".\n\nPlease select another available driver."
                        );

                        alert.showAndWait();

                        return;
                }

                if (!"Available".equalsIgnoreCase(
                        selectedNurse.getStatus())) {

                        Alert alert =
                                new Alert(
                                        Alert.AlertType.WARNING
                                );

                        alert.setTitle(
                                "Nurse Already Selected"
                        );

                        alert.setHeaderText(
                                "Nurse Cannot Be Assigned"
                        );

                        alert.setContentText(
                                selectedNurse.getName() +
                                " is already " +
                                selectedNurse.getStatus().toLowerCase() +
                                ".\n\nPlease select another available nurse."
                        );

                        alert.showAndWait();

                        return;
                }

                AdminAmbulanceAssignmentModel assignment =
                        new AdminAmbulanceAssignmentModel(
                                ambulanceIdCombo.getValue(),
                                selectedDriver.getEmail(),
                                selectedDriver.getName(),
                                selectedNurse.getEmail(),
                                selectedNurse.getName(),
                                "ACTIVE",
                                System.currentTimeMillis()
                        );

                try {

                        assignmentController.assignStaff(
                                assignment
                        );

                        driverStaffController.updateDriverStatus(
                                selectedDriver.getEmail(),
                                "Busy"
                        );

                        nurseStaffController.updateNurseStatus(
                                selectedNurse.getEmail(),
                                "Busy"
                        );


                        loadDrivers();
                        loadNurses();

                        Alert alert =
                                new Alert(
                                        Alert.AlertType.INFORMATION
                                );

                        alert.setTitle(
                                "Staff Assigned"
                        );

                        alert.setHeaderText(
                                "Staff Assignment Successful"
                        );

                        alert.setContentText(
                                "Ambulance: " +
                                assignment.getAmbulanceId() +
                                "\n\nDriver: " +
                                assignment.getDriverName() +
                                "\nNurse: " +
                                assignment.getNurseName()
                        );

                        alert.showAndWait();

                } catch (
                        ExecutionException |
                        InterruptedException ex) {

                        ex.printStackTrace();

                        Alert alert =
                                new Alert(
                                        Alert.AlertType.ERROR
                                );

                        alert.setTitle(
                                "Assignment Failed"
                        );

                        alert.setHeaderText(
                                "Unable to Assign Staff"
                        );

                        alert.setContentText(
                                "Something went wrong while saving the ambulance assignment."
                        );

                        alert.showAndWait();
                }
        });



        HBox assignmentRow = new HBox(12);

        assignmentRow.setAlignment(
                Pos.CENTER_LEFT
        );

        assignmentRow.getChildren().addAll(
                ambulanceIdCombo,
                addAmbulanceButton,
                assignmentDriverCombo,
                assignmentNurseCombo,
                assignStaffButton
        );

        ambulanceAssignmentBox.getChildren().addAll(
                assignmentTitle,
                assignmentSubtitle,
                assignmentRow,
                assignedDriverLabel,
                assignedNurseLabel
        );




                // SELECTED DRIVERS BOX

                VBox selectedDriversBox = new VBox(10);

                selectedDriversBox.setPadding(
                        new Insets(16)
                );

                selectedDriversBox.setStyle(
                        BASE_CARD_STYLE
                );

                HBox selectedDriverHeader = new HBox(10);

                selectedDriverHeader.setAlignment(
                        Pos.CENTER_LEFT
                );

                Text selectedDriverTitle = new Text(
                        "Selected Drivers"
                );

                selectedDriverTitle.setStyle(
                        "-fx-font-size: 15px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-fill: " + TEXT_PRIMARY + ";"
                );

                Region selectedDriverSpacer = new Region();

                HBox.setHgrow(
                        selectedDriverSpacer,
                        Priority.ALWAYS
                );

                selectedDriverCount = new Label(
                        "0 Selected"
                );

                selectedDriverCount.setStyle(
                        FONT_STACK + "-fx-background-color: " + PURPLE_LIGHT + "; " +
                        "-fx-text-fill: " + PURPLE_DARK + "; " +
                        "-fx-font-size: 11px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 6px 10px; " +
                        "-fx-background-radius: 8px;"
                );

                selectedDriverHeader.getChildren().addAll(
                        selectedDriverTitle,
                        selectedDriverSpacer,
                        selectedDriverCount
                );

                selectedDriverNamesBox = new HBox(8);

                selectedDriverNamesBox.setAlignment(
                        Pos.CENTER_LEFT
                );

                Label emptyDriverSelection = new Label(
                        "No drivers selected yet"
                );

                emptyDriverSelection.setStyle(
                        "-fx-font-size: 12px; " +
                        "-fx-text-fill: " + TEXT_MUTED + ";"
                );

                selectedDriverNamesBox.getChildren().add(
                        emptyDriverSelection
                );

                selectedDriversBox.getChildren().addAll(
                        selectedDriverHeader,
                        selectedDriverNamesBox
                );

                // SELECTED NURSES BOX

                VBox selectedNursesBox = new VBox(10);

                selectedNursesBox.setPadding(
                        new Insets(16)
                );

                selectedNursesBox.setStyle(
                        BASE_CARD_STYLE
                );

                HBox selectedNurseHeader = new HBox(10);

                selectedNurseHeader.setAlignment(
                        Pos.CENTER_LEFT
                );

                Text selectedNurseTitle = new Text(
                        "Selected Nurses"
                );

                selectedNurseTitle.setStyle(
                        "-fx-font-size: 15px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-fill: " + TEXT_PRIMARY + ";"
                );

                Region selectedNurseSpacer = new Region();

                HBox.setHgrow(
                        selectedNurseSpacer,
                        Priority.ALWAYS
                );

                selectedNurseCount = new Label(
                        "0 Selected"
                );

                selectedNurseCount.setStyle(
                        FONT_STACK + "-fx-background-color: " + PURPLE_LIGHT + "; " +
                        "-fx-text-fill: " + PURPLE_DARK + "; " +
                        "-fx-font-size: 11px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 6px 10px; " +
                        "-fx-background-radius: 8px;"
                );

                selectedNurseHeader.getChildren().addAll(
                        selectedNurseTitle,
                        selectedNurseSpacer,
                        selectedNurseCount
                );

                selectedNurseNamesBox = new HBox(8);

                selectedNurseNamesBox.setAlignment(
                        Pos.CENTER_LEFT
                );

                Label emptyNurseSelection = new Label(
                        "No nurses selected yet"
                );

                emptyNurseSelection.setStyle(
                        "-fx-font-size: 12px; " +
                        "-fx-text-fill: " + TEXT_MUTED + ";"
                );

                selectedNurseNamesBox.getChildren().add(
                        emptyNurseSelection
                );

                selectedNursesBox.getChildren().addAll(
                        selectedNurseHeader,
                        selectedNurseNamesBox
                );

                // DRIVER SECTION

                HBox driverSectionHeader = new HBox(10);

                driverSectionHeader.setAlignment(
                        Pos.CENTER_LEFT
                );

                Text driverTitle = new Text(
                        "Ambulance Drivers"
                );

                driverTitle.setStyle(
                        "-fx-font-size: 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-fill: " + TEXT_PRIMARY + ";"
                );

                Region driverSpacer = new Region();

                HBox.setHgrow(
                        driverSpacer,
                        Priority.ALWAYS
                );

                driverCountLabel = new Label(
                        "0 Drivers"
                );

                driverCountLabel.setStyle(
                        "-fx-background-color: #eef2f6; " +
                        "-fx-text-fill: " + TEXT_SECONDARY + "; " +
                        "-fx-font-size: 11px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 7px 12px; " +
                        "-fx-background-radius: 8px;"
                );

                driverSectionHeader.getChildren().addAll(
                        driverTitle,
                        driverSpacer,
                        driverCountLabel
                );

                // DRIVER SUMMARY

                HBox driverSummaryRow = new HBox(15);

                VBox driverAvailableBox = createSummaryCard(
                        "AVAILABLE DRIVERS",
                        "0",
                        "Ready for assignment",
                        "#dcfce7",
                        "#15803d",
                        "✓"
                );

                VBox driverBusyBox = createSummaryCard(
                        "BUSY / ASSIGNED",
                        "0",
                        "Currently occupied",
                        "#fee2e2",
                        "#b91c1c",
                        "●"
                );

                VBox driverLeaveBox = createSummaryCard(
                        "ON LEAVE",
                        "0",
                        "Currently unavailable",
                        "#fef3c7",
                        "#b45309",
                        "!"
                );

                VBox driverSelectedBox = createSummaryCard(
                        "SELECTED",
                        "0",
                        "For current assignment",
                        "#dbeafe",
                        "#1d4ed8",
                        "＋"
                );

                driverAvailableValue =
                        getSummaryValue(
                                driverAvailableBox
                        );

                driverBusyValue =
                        getSummaryValue(
                                driverBusyBox
                        );

                driverLeaveValue =
                        getSummaryValue(
                                driverLeaveBox
                        );

                driverSelectedValue =
                        getSummaryValue(
                                driverSelectedBox
                        );

                driverSummaryRow.getChildren().addAll(
                        driverAvailableBox,
                        driverBusyBox,
                        driverLeaveBox,
                        driverSelectedBox
                );

                HBox.setHgrow(
                        driverAvailableBox,
                        Priority.ALWAYS
                );

                HBox.setHgrow(
                        driverBusyBox,
                        Priority.ALWAYS
                );

                HBox.setHgrow(
                        driverLeaveBox,
                        Priority.ALWAYS
                );

                HBox.setHgrow(
                        driverSelectedBox,
                        Priority.ALWAYS
                );

                driverGridContainer = new VBox(16);

                // NURSE SECTION

                HBox nurseSectionHeader = new HBox(10);

                nurseSectionHeader.setAlignment(
                        Pos.CENTER_LEFT
                );

                Text nurseTitle = new Text(
                        "Nursing Staff"
                );

                nurseTitle.setStyle(
                        "-fx-font-size: 20px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-fill: " + TEXT_PRIMARY + ";"
                );

                Region nurseSpacer = new Region();

                HBox.setHgrow(
                        nurseSpacer,
                        Priority.ALWAYS
                );

                nurseCountLabel = new Label(
                        "0 Nurses"
                );

                nurseCountLabel.setStyle(
                        "-fx-background-color: #eef2f6; " +
                        "-fx-text-fill: " + TEXT_SECONDARY + "; " +
                        "-fx-font-size: 11px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 7px 12px; " +
                        "-fx-background-radius: 8px;"
                );

                nurseSectionHeader.getChildren().addAll(
                        nurseTitle,
                        nurseSpacer,
                        nurseCountLabel
                );

                // NURSE SUMMARY

                HBox nurseSummaryRow = new HBox(15);

                VBox nurseAvailableBox = createSummaryCard(
                        "AVAILABLE NURSES",
                        "0",
                        "Ready for assignment",
                        "#dcfce7",
                        "#15803d",
                        "✓"
                );

                VBox nurseBusyBox = createSummaryCard(
                        "BUSY / ASSIGNED",
                        "0",
                        "Currently occupied",
                        "#fee2e2",
                        "#b91c1c",
                        "●"
                );

                VBox nurseLeaveBox = createSummaryCard(
                        "ON LEAVE",
                        "0",
                        "Currently unavailable",
                        "#fef3c7",
                        "#b45309",
                        "!"
                );

                VBox nurseSelectedBox = createSummaryCard(
                        "SELECTED",
                        "0",
                        "For current assignment",
                        "#ede9fe",
                        "#7c3aed",
                        "＋"
                );

                nurseAvailableValue =
                        getSummaryValue(
                                nurseAvailableBox
                        );

                nurseBusyValue =
                        getSummaryValue(
                                nurseBusyBox
                        );

                nurseLeaveValue =
                        getSummaryValue(
                                nurseLeaveBox
                        );

                nurseSelectedValue =
                        getSummaryValue(
                                nurseSelectedBox
                        );

                nurseSummaryRow.getChildren().addAll(
                        nurseAvailableBox,
                        nurseBusyBox,
                        nurseLeaveBox,
                        nurseSelectedBox
                );

                HBox.setHgrow(
                        nurseAvailableBox,
                        Priority.ALWAYS
                );

                HBox.setHgrow(
                        nurseBusyBox,
                        Priority.ALWAYS
                );

                HBox.setHgrow(
                        nurseLeaveBox,
                        Priority.ALWAYS
                );

                HBox.setHgrow(
                        nurseSelectedBox,
                        Priority.ALWAYS
                );

                nurseGridContainer = new VBox(16);

                // ADD ALL

                mainContent.getChildren().addAll(
                        header,
                        ambulanceAssignmentBox,
                        selectedDriversBox,
                        driverSectionHeader,
                        driverSummaryRow,
                        driverGridContainer,
                        selectedNursesBox,
                        nurseSectionHeader,
                        nurseSummaryRow,
                        nurseGridContainer
                );

                ScrollPane scrollPane =
                        new ScrollPane(
                                mainContent
                        );

                scrollPane.setFitToWidth(true);

                scrollPane.setHbarPolicy(
                        ScrollPane.ScrollBarPolicy.NEVER
                );

                scrollPane.setVbarPolicy(
                        ScrollPane.ScrollBarPolicy.NEVER
                );

                scrollPane.setStyle(
                        "-fx-background-color: transparent; " +
                        "-fx-background: transparent;"
                );

                VBox finalContent =
                        new VBox(
                                scrollPane
                        );

                finalContent.setStyle(
                        "-fx-background-color: " + BG_PAGE + ";"
                );

                VBox.setVgrow(
                        scrollPane,
                        Priority.ALWAYS
                );

                loadDrivers();

                loadNurses();

                return finalContent;
        }




        
        // LOAD DRIVERS

        

                private void loadDrivers() {
        driverGridContainer.getChildren().clear();
        ShimmerPane driverShimmer = ShimmerLoader.createDoctorGridSkeleton(driverGridContainer.getWidth() > 0 ? driverGridContainer.getWidth() : 800, 3);
        driverGridContainer.getChildren().add(driverShimmer);


                Thread thread = new Thread(() -> {

                        try {

                        QuerySnapshot driverSnapshot =
                                FirebaseConfig.getFirestore()
                                        .collection("driver")
                                        .get()
                                        .get();

                        List<DriverStaffModel> drivers =
                                new ArrayList<>();


                        for (DocumentSnapshot driverDocument :
                                driverSnapshot.getDocuments()) {

                                String name =
                                        driverDocument.getString("name");

                                String email =
                                        driverDocument.getString("email");

                                if (email == null ||
                                        email.trim().isEmpty()) {

                                email =
                                        driverDocument.getId();
                                }

                                String status =
                                        "Available";

                                String shift =
                                        "Not Assigned";

                                DocumentSnapshot staffDocument =
                                        FirebaseConfig.getFirestore()
                                                .collection("driverStaff")
                                                .document(email)
                                                .get()
                                                .get();

                                if (staffDocument.exists()) {

                                String staffStatus =
                                        staffDocument.getString("status");

                                String staffShift =
                                        staffDocument.getString("shift");

                                if (staffStatus != null &&
                                        !staffStatus.trim().isEmpty()) {

                                        status = staffStatus;
                                }

                                if (staffShift != null &&
                                        !staffShift.trim().isEmpty()) {

                                        shift = staffShift;
                                }
                                }

                                DriverStaffModel driver =
                                        new DriverStaffModel(
                                                name,
                                                email,
                                                status,
                                                shift
                                        );

                                drivers.add(driver);
                        }

                        Platform.runLater(() -> {

                                assignmentDriverCombo.getItems().clear();

                                assignmentDriverCombo.getItems().addAll(
                                        drivers
                                );

                        });

                        
                                driverShimmer.stop();
                                Platform.runLater(() -> {

                                driverGridContainer
                                        .getChildren()
                                        .clear();

                                int available = 0;
                                int busy = 0;
                                int leave = 0;

                                GridPane grid =
                                        new GridPane();

                                grid.setHgap(16);
                                grid.setVgap(16);

                                for (int i = 0;
                                i < drivers.size();
                                i++) {

                                DriverStaffModel driver =
                                        drivers.get(i);

                                String status =
                                        driver.getStatus();

                                if ("Available".equalsIgnoreCase(
                                        status)) {

                                        available++;

                                } else if (
                                        "Busy".equalsIgnoreCase(status) ||
                                        "Assigned".equalsIgnoreCase(status) ||
                                        "On Duty".equalsIgnoreCase(status)
                                ) {

                                        busy++;

                                } else if (
                                        "On Leave".equalsIgnoreCase(status)
                                ) {

                                        leave++;
                                }

                                VBox card =
                                        createDriverCard(
                                                driver
                                        );

                                grid.add(
                                        card,
                                        i % 3,
                                        i / 3
                                );
                                }

                                driverGridContainer
                                        .getChildren()
                                        .add(grid);

                                driverCountLabel.setText(
                                        drivers.size() +
                                        " Drivers"
                                );

                                driverAvailableValue.setText(
                                        String.valueOf(
                                                available
                                        )
                                );

                                driverBusyValue.setText(
                                        String.valueOf(
                                                busy
                                        )
                                );

                                driverLeaveValue.setText(
                                        String.valueOf(
                                                leave
                                        )
                                );

                        });

                        } catch (
                                ExecutionException |
                                InterruptedException e) {

                        e.printStackTrace();
                        }

                });

                thread.setDaemon(true);
                thread.start();
                }







        // DRIVER CARD

        private VBox createDriverCard(
                DriverStaffModel driver) {

                boolean available =
                        "Available".equalsIgnoreCase(
                                driver.getStatus()
                        );

                VBox card =
                        new VBox(12);

                card.setPadding(
                        new Insets(18)
                );

                card.setPrefHeight(250);
                card.setMinHeight(250);
                card.setMaxHeight(250);

                card.setStyle(
                        "-fx-background-color: white; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-border-color: #e4e7ec; " +
                        "-fx-border-radius: 16px;"
                );

                String initials =
                        getInitials(
                                driver.getName()
                        );

                Circle circle =
                        new Circle(25);

                circle.setFill(
                        Color.web("#dbeafe")
                );

                Text initialsText =
                        new Text(initials);

                initialsText.setStyle(
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-fill: #1d4ed8;"
                );

                StackPane iconPane =
                        new StackPane();

                iconPane.setPrefSize(
                        50,
                        50
                );

                iconPane.getChildren().addAll(
                        circle,
                        initialsText
                );

                VBox nameBox =
                        new VBox(3);

                Text nameText =
                        new Text(
                                safeText(
                                        driver.getName()
                                )
                        );

                nameText.setStyle(
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-fill: " + TEXT_PRIMARY + ";"
                );

                Text emailText =
                        new Text(
                                safeText(
                                        driver.getEmail()
                                )
                        );

                emailText.setStyle(
                        "-fx-font-size: 11px; " +
                        "-fx-fill: " + TEXT_SECONDARY + ";"
                );

                nameBox.getChildren().addAll(
                        nameText,
                        emailText
                );

                Region topSpacer =
                        new Region();

                HBox.setHgrow(
                        topSpacer,
                        Priority.ALWAYS
                );

                Label statusLabel =
                        new Label(
                                "● " +
                                safeText(
                                        driver.getStatus()
                                )
                        );

                applyStatusStyle(
                        statusLabel,
                        driver.getStatus()
                );

                HBox top =
                        new HBox(12);

                top.setAlignment(
                        Pos.CENTER_LEFT
                );

                top.getChildren().addAll(
                        iconPane,
                        nameBox,
                        topSpacer,
                        statusLabel
                );

                Text emailTitle =
                        new Text(
                                "EMAIL"
                        );

                emailTitle.setStyle(
                        "-fx-font-size: 9px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-fill: #98a2b3;"
                );

                Text emailValue =
                        new Text(
                                safeText(
                                        driver.getEmail()
                                )
                        );

                emailValue.setStyle(
                        "-fx-font-size: 11px; " +
                        "-fx-fill: #344054;"
                );

                VBox emailBox =
                        new VBox(3);

                emailBox.getChildren().addAll(
                        emailTitle,
                        emailValue
                );

                Text shiftTitle =
                        new Text(
                                "SHIFT"
                        );

                shiftTitle.setStyle(
                        "-fx-font-size: 9px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-fill: #98a2b3;"
                );

                Text shiftText =
                        new Text(
                                safeText(
                                        driver.getShift()
                                )
                        );

                shiftText.setStyle(
                        "-fx-font-size: 11px; " +
                        "-fx-fill: #344054;"
                );

                VBox shiftBox =
                        new VBox(3);

                shiftBox.getChildren().addAll(
                        shiftTitle,
                        shiftText
                );

                Region separator =
                        new Region();

                separator.setPrefHeight(1);

                separator.setStyle(
                        "-fx-background-color: #eef0f3;"
                );

                HBox buttons =
                        new HBox(8);

                Button selectButton =
                        new Button(
                                selectedDrivers.contains(
                                        driver.getEmail()
                                )
                                        ? "✓  Selected"
                                        : "+  Select"
                        );

                selectButton.setPrefHeight(36);

                HBox.setHgrow(
                        selectButton,
                        Priority.ALWAYS
                );

                Button statusButton =
                        new Button(
                                "Change Status"
                        );

                statusButton.setPrefHeight(36);

                HBox.setHgrow(
                        statusButton,
                        Priority.ALWAYS
                );

                if (available) {

                selectButton.setStyle(
                        "-fx-background-color: #eff6ff; " +
                        "-fx-text-fill: #1d4ed8; " +
                        "-fx-font-size: 11px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-color: #bfdbfe; " +
                        "-fx-border-radius: 8px;"
                );

                } else {

                selectButton.setDisable(true);

                selectButton.setOpacity(0.7);

                selectButton.setStyle(
                        "-fx-background-color: #f2f4f7; " +
                        "-fx-text-fill: #98a2b3; " +
                        "-fx-font-size: 11px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 8px;"
                );
                }

                selectButton.setOnAction(e -> {

                if (selectedDrivers.contains(
                        driver.getEmail()
                )) {

                        selectedDrivers.remove(
                                driver.getEmail()
                        );

                } else {

                        selectedDrivers.add(
                                driver.getEmail()
                        );
                }

                updateSelectedDrivers();
                loadDrivers();
                });

                statusButton.setStyle(
                        "-fx-background-color: #ffffff; " +
                        "-fx-text-fill: " + TEXT_PRIMARY + "; " +
                        "-fx-font-size: 11px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-color: #d0d5dd; " +
                        "-fx-border-radius: 8px;"
                );

                statusButton.setOnAction(e ->
                        showDriverStatusOptions(
                                driver
                        )
                );

                buttons.getChildren().addAll(
                        selectButton,
                        statusButton
                );

                card.getChildren().addAll(
                        top,
                        emailBox,
                        shiftBox,
                        separator,
                        buttons
                );

                return card;
        }











        // LOAD NURSES

                private void loadNurses() {
        nurseGridContainer.getChildren().clear();
        ShimmerPane nurseShimmer = ShimmerLoader.createDoctorGridSkeleton(nurseGridContainer.getWidth() > 0 ? nurseGridContainer.getWidth() : 800, 3);
        nurseGridContainer.getChildren().add(nurseShimmer);


                Thread thread = new Thread(() -> {

                        try {

                        QuerySnapshot nurseSnapshot =
                                FirebaseConfig.getFirestore()
                                        .collection("nurse")
                                        .get()
                                        .get();

                        List<NurseStaffModel> nurses =
                                new ArrayList<>();


                        for (DocumentSnapshot nurseDocument :
                                nurseSnapshot.getDocuments()) {

                                String name =
                                        nurseDocument.getString("name");

                                String email =
                                        nurseDocument.getString("email");
                                        if (email == null ||
                                        email.trim().isEmpty()) {

                                email =
                                        nurseDocument.getId();
                                }

                                String status =
                                        "Available";

                                String shift =
                                        "Not Assigned";

                                DocumentSnapshot staffDocument =
                                        FirebaseConfig.getFirestore()
                                                .collection("nurseStaff")
                                                .document(email)
                                                .get()
                                                .get();

                                if (staffDocument.exists()) {

                                String staffStatus =
                                        staffDocument.getString("status");

                                String staffShift =
                                        staffDocument.getString("shift");

                                if (staffStatus != null &&
                                        !staffStatus.trim().isEmpty()) {

                                        status = staffStatus;
                                }

                                if (staffShift != null &&
                                        !staffShift.trim().isEmpty()) {

                                        shift = staffShift;
                                }
                                }

                                NurseStaffModel nurse =
                                        new NurseStaffModel(
                                                name,
                                                email,
                                                status,
                                                shift
                                        );

                                nurses.add(nurse);
                        }


                       Platform.runLater(() -> {

                                assignmentNurseCombo.getItems().clear();

                                assignmentNurseCombo.getItems().addAll(
                                        nurses
                                );

                        });

                        
                                nurseShimmer.stop();
                                Platform.runLater(() -> {

                                nurseGridContainer
                                        .getChildren()
                                        .clear();

                                int available = 0;
                                int busy = 0;
                                int leave = 0;

                                GridPane grid =
                                        new GridPane();

                                grid.setHgap(16);
                                grid.setVgap(16);

                                for (int i = 0;
                                i < nurses.size();
                                i++) {

                                NurseStaffModel nurse =
                                        nurses.get(i);

                                String status =
                                        nurse.getStatus();

                                if ("Available".equalsIgnoreCase(
                                        status)) {

                                        available++;

                                } else if (
                                        "Busy".equalsIgnoreCase(status) ||
                                        "Assigned".equalsIgnoreCase(status) ||
                                        "On Duty".equalsIgnoreCase(status)
                                ) {

                                        busy++;

                                } else if (
                                        "On Leave".equalsIgnoreCase(status)
                                ) {

                                        leave++;
                                }

                                VBox card =
                                        createNurseCard(
                                                nurse
                                        );

                                grid.add(
                                        card,
                                        i % 3,
                                        i / 3
                                );
                                }

                                nurseGridContainer
                                        .getChildren()
                                        .add(grid);

                                nurseCountLabel.setText(
                                        nurses.size() +
                                        " Nurses"
                                );

                                nurseAvailableValue.setText(
                                        String.valueOf(
                                                available
                                        )
                                );

                                nurseBusyValue.setText(
                                        String.valueOf(
                                                busy
                                        )
                                );

                                nurseLeaveValue.setText(
                                        String.valueOf(
                                                leave
                                        )
                                );

                        });

                        } catch (
                                ExecutionException |
                                InterruptedException e) {

                        e.printStackTrace();
                        }

                });

                thread.setDaemon(true);
                thread.start();
        }










    // NURSE CARD

    private VBox createNurseCard(
            NurseStaffModel nurse) {

        boolean available =
                "Available".equalsIgnoreCase(
                        nurse.getStatus()
                );

        VBox card =
                new VBox(12);

        card.setPadding(
                new Insets(18)
        );

        card.setPrefHeight(250);
        card.setMinHeight(250);
        card.setMaxHeight(250);

        card.setStyle(
                "-fx-background-color: white; " +
                "-fx-background-radius: 16px; " +
                "-fx-border-color: #e4e7ec; " +
                "-fx-border-radius: 16px;"
        );

        String initials =
                getInitials(
                        nurse.getName()
                );

        Circle circle =
                new Circle(25);

        circle.setFill(
                Color.web("#ede9fe")
        );

        Text initialsText =
                new Text(initials);

        initialsText.setStyle(
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: #7c3aed;"
        );

        StackPane iconPane =
                new StackPane();

        iconPane.setPrefSize(
                50,
                50
        );

        iconPane.getChildren().addAll(
                circle,
                initialsText
        );

        VBox nameBox =
                new VBox(3);

        Text nameText =
                new Text(
                        safeText(
                                nurse.getName()
                        )
                );

        nameText.setStyle(
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

        Text emailText =
                new Text(
                        safeText(
                                nurse.getEmail()
                        )
                );

        emailText.setStyle(
                "-fx-font-size: 11px; " +
                "-fx-fill: " + TEXT_SECONDARY + ";"
        );

        nameBox.getChildren().addAll(
                nameText,
                emailText
        );

        Region topSpacer =
                new Region();

        HBox.setHgrow(
                topSpacer,
                Priority.ALWAYS
        );

        Label statusLabel =
                new Label(
                        "● " +
                        safeText(
                                nurse.getStatus()
                        )
                );

        applyStatusStyle(
                statusLabel,
                nurse.getStatus()
        );

        HBox top =
                new HBox(12);

        top.setAlignment(
                Pos.CENTER_LEFT
        );

        top.getChildren().addAll(
                iconPane,
                nameBox,
                topSpacer,
                statusLabel
        );

        Text emailTitle =
                new Text(
                        "EMAIL"
                );

        emailTitle.setStyle(
                "-fx-font-size: 9px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: #98a2b3;"
        );

        Text emailValue =
                new Text(
                        safeText(
                                nurse.getEmail()
                        )
                );

        emailValue.setStyle(
                "-fx-font-size: 11px; " +
                "-fx-fill: #344054;"
        );

        VBox emailBox =
                new VBox(3);

        emailBox.getChildren().addAll(
                emailTitle,
                emailValue
        );

        Text shiftTitle =
                new Text(
                        "SHIFT"
                );

        shiftTitle.setStyle(
                "-fx-font-size: 9px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: #98a2b3;"
        );

        Text shiftText =
                new Text(
                        safeText(
                                nurse.getShift()
                        )
                );

        shiftText.setStyle(
                "-fx-font-size: 11px; " +
                "-fx-fill: #344054;"
        );

        VBox shiftBox =
                new VBox(3);

        shiftBox.getChildren().addAll(
                shiftTitle,
                shiftText
        );

        Region separator =
                new Region();

        separator.setPrefHeight(1);

        separator.setStyle(
                "-fx-background-color: #eef0f3;"
        );

        HBox buttons =
                new HBox(8);

        Button selectButton =
                new Button(
                        selectedNurses.contains(
                                nurse.getEmail()
                        )
                                ? "✓  Selected"
                                : "+  Select"
                );

        selectButton.setPrefHeight(36);

        HBox.setHgrow(
                selectButton,
                Priority.ALWAYS
        );

        Button statusButton =
                new Button(
                        "Change Status"
                );

        statusButton.setPrefHeight(36);

        HBox.setHgrow(
                statusButton,
                Priority.ALWAYS
        );

        if (available) {

            selectButton.setStyle(
                    "-fx-background-color: #f5f3ff; " +
                    "-fx-text-fill: #7c3aed; " +
                    "-fx-font-size: 11px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8px; " +
                    "-fx-border-color: #ddd6fe; " +
                    "-fx-border-radius: 8px;"
            );

        } else {

            selectButton.setDisable(true);

            selectButton.setOpacity(0.7);

            selectButton.setStyle(
                    "-fx-background-color: #f2f4f7; " +
                    "-fx-text-fill: #98a2b3; " +
                    "-fx-font-size: 11px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8px;"
            );
        }

        selectButton.setOnAction(e -> {

            if (selectedNurses.contains(
                    nurse.getEmail()
            )) {

                selectedNurses.remove(
                        nurse.getEmail()
                );

            } else {

                selectedNurses.add(
                        nurse.getEmail()
                );
            }

            updateSelectedNurses();
            loadNurses();
        });

        statusButton.setStyle(
                "-fx-background-color: #ffffff; " +
                "-fx-text-fill: " + TEXT_PRIMARY + "; " +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-color: #d0d5dd; " +
                "-fx-border-radius: 8px;"
        );

        statusButton.setOnAction(e ->
                showNurseStatusOptions(
                        nurse
                )
        );

        buttons.getChildren().addAll(
                selectButton,
                statusButton
        );

        card.getChildren().addAll(
                top,
                emailBox,
                shiftBox,
                separator,
                buttons
        );

        return card;
    }

    // DRIVER STATUS

    private void showDriverStatusOptions(
            DriverStaffModel driver) {

        VBox box =
                new VBox(12);

        box.setPadding(
                new Insets(20)
        );

        Text title =
                new Text(
                        "Change Driver Status"
                );

        title.setStyle(
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

        Label driverLabel =
                new Label(
                        safeText(
                                driver.getName()
                        )
                );

        driverLabel.setStyle(
                "-fx-font-size: 12px; " +
                "-fx-text-fill: #667085;"
        );

        ComboBox<String> statusCombo =
                new ComboBox<>();

        statusCombo.getItems().addAll(
                "Available",
                "Busy",
                "On Leave"
        );

        statusCombo.setValue(
                driver.getStatus()
        );

        statusCombo.setMaxWidth(
                Double.MAX_VALUE
        );

        Button updateButton =
                new Button(
                        "Update Status"
                );

        updateButton.setPrefHeight(38);

        updateButton.setMaxWidth(
                Double.MAX_VALUE
        );

        updateButton.setStyle(
                "-fx-background-color: #1d4ed8; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px;"
        );

        javafx.stage.Stage stage =
                new javafx.stage.Stage();

        stage.setTitle(
                "Driver Status"
        );

        updateButton.setOnAction(e -> {

            Thread thread = new Thread(() -> {

                try {

                    driverStaffController
                            .updateDriverStatus(
                                    driver.getEmail(),
                                    statusCombo.getValue()
                            );

                    Platform.runLater(() -> {

                        stage.close();

                        loadDrivers();
                    });

                } catch (
                        ExecutionException |
                        InterruptedException ex) {

                    ex.printStackTrace();
                }

            });

            thread.setDaemon(true);
            thread.start();
        });

        box.getChildren().addAll(
                title,
                driverLabel,
                statusCombo,
                updateButton
        );

        javafx.scene.Scene scene =
                new javafx.scene.Scene(
                        box,
                        320,
                        240
                );

        stage.setScene(scene);

        stage.show();
    }

    // NURSE STATUS

    private void showNurseStatusOptions(
            NurseStaffModel nurse) {

        VBox box =
                new VBox(12);

        box.setPadding(
                new Insets(20)
        );

        Text title =
                new Text(
                        "Change Nurse Status"
                );

        title.setStyle(
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

        Label nurseLabel =
                new Label(
                        safeText(
                                nurse.getName()
                        )
                );

        nurseLabel.setStyle(
                "-fx-font-size: 12px; " +
                "-fx-text-fill: #667085;"
        );

        ComboBox<String> statusCombo =
                new ComboBox<>();

        statusCombo.getItems().addAll(
                "Available",
                "Busy",
                "On Leave"
        );

        statusCombo.setValue(
                nurse.getStatus()
        );

        statusCombo.setMaxWidth(
                Double.MAX_VALUE
        );

        Button updateButton =
                new Button(
                        "Update Status"
                );

        updateButton.setPrefHeight(38);

        updateButton.setMaxWidth(
                Double.MAX_VALUE
        );

        updateButton.setStyle(
                "-fx-background-color: #7c3aed; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px;"
        );

        javafx.stage.Stage stage =
                new javafx.stage.Stage();

        stage.setTitle(
                "Nurse Status"
        );

        updateButton.setOnAction(e -> {

            Thread thread = new Thread(() -> {

                try {

                    nurseStaffController
                            .updateNurseStatus(
                                    nurse.getEmail(),
                                    statusCombo.getValue()
                            );

                    Platform.runLater(() -> {

                        stage.close();

                        loadNurses();
                    });

                } catch (
                        ExecutionException |
                        InterruptedException ex) {

                    ex.printStackTrace();
                }

            });

            thread.setDaemon(true);
            thread.start();
        });

        box.getChildren().addAll(
                title,
                nurseLabel,
                statusCombo,
                updateButton
        );

        javafx.scene.Scene scene =
                new javafx.scene.Scene(
                        box,
                        320,
                        240
                );

        stage.setScene(scene);

        stage.show();
    }

    // ADD DRIVER

    private void showAddDriverDialog() {

        javafx.stage.Stage stage =
                new javafx.stage.Stage();

        stage.setTitle(
                "Add New Driver"
        );

        VBox box =
                new VBox(12);

        box.setPadding(
                new Insets(20)
        );

        Text title =
                new Text(
                        "Add New Driver"
                );

        title.setStyle(
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

        TextField nameField =
                new TextField();

        nameField.setPromptText(
                "Driver Name"
        );

        TextField emailField =
                new TextField();

        emailField.setPromptText(
                "Driver Email"
        );

        ComboBox<String> shiftCombo =
                new ComboBox<>();

        shiftCombo.getItems().addAll(
                "09:00 AM - 05:00 PM",
                "08:00 AM - 04:00 PM",
                "10:00 AM - 06:00 PM",
                "06:00 AM - 02:00 PM",
                "02:00 PM - 10:00 PM",
                "10:00 PM - 06:00 AM"
        );

        shiftCombo.setPromptText(
                "Select Shift"
        );

        shiftCombo.setMaxWidth(
                Double.MAX_VALUE
        );

        ComboBox<String> statusCombo =
                new ComboBox<>();

        statusCombo.getItems().addAll(
                "Available",
                "Busy",
                "On Leave"
        );

        statusCombo.setValue(
                "Available"
        );

        statusCombo.setMaxWidth(
                Double.MAX_VALUE
        );

        Button addButton =
                new Button(
                        "Add Driver"
                );

        addButton.setPrefHeight(40);

        addButton.setMaxWidth(
                Double.MAX_VALUE
        );

        addButton.setStyle(
                "-fx-background-color: #15803d; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px;"
        );

        Label message =
                new Label();

        message.setStyle(
                "-fx-text-fill: #b91c1c; " +
                "-fx-font-size: 11px;"
        );

        addButton.setOnAction(e -> {

            String name =
                    nameField.getText().trim();

            String email =
                    emailField.getText().trim();

            String shift =
                    shiftCombo.getValue();

            String status =
                    statusCombo.getValue();

            if (name.isEmpty() ||
                    email.isEmpty()) {

                message.setText(
                        "Please enter driver name and email."
                );

                return;
            }

            if (shift == null ||
                    shift.isEmpty()) {

                message.setText(
                        "Please select a shift."
                );

                return;
            }

            DriverStaffModel driver =
                    new DriverStaffModel(
                            name,
                            email,
                            status,
                            shift
                    );

            Thread thread = new Thread(() -> {

                try {

                    driverStaffController
                            .addDriverStaff(
                                    driver
                            );

                    Platform.runLater(() -> {

                        stage.close();

                        loadDrivers();
                    });

                } catch (
                        ExecutionException |
                        InterruptedException ex) {

                    ex.printStackTrace();

                    Platform.runLater(() ->
                            message.setText(
                                    "Unable to add driver."
                            )
                    );
                }

            });

            thread.setDaemon(true);
            thread.start();
        });

        box.getChildren().addAll(
                title,
                nameField,
                emailField,
                shiftCombo,
                statusCombo,
                message,
                addButton
        );

        javafx.scene.Scene scene =
                new javafx.scene.Scene(
                        box,
                        360,
                        360
                );

        stage.setScene(scene);

        stage.show();
    }

    // ADD NURSE

    private void showAddNurseDialog() {

        javafx.stage.Stage stage =
                new javafx.stage.Stage();

        stage.setTitle(
                "Add New Nurse"
        );

        VBox box =
                new VBox(12);

        box.setPadding(
                new Insets(20)
        );

        Text title =
                new Text(
                        "Add New Nurse"
                );

        title.setStyle(
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

        TextField nameField =
                new TextField();

        nameField.setPromptText(
                "Nurse Name"
        );

        TextField emailField =
                new TextField();

        emailField.setPromptText(
                "Nurse Email"
        );

        ComboBox<String> shiftCombo =
                new ComboBox<>();

        shiftCombo.getItems().addAll(
                "09:00 AM - 05:00 PM",
                "08:00 AM - 04:00 PM",
                "10:00 AM - 06:00 PM",
                "06:00 AM - 02:00 PM",
                "02:00 PM - 10:00 PM",
                "10:00 PM - 06:00 AM"
        );

        shiftCombo.setPromptText(
                "Select Shift"
        );

        shiftCombo.setMaxWidth(
                Double.MAX_VALUE
        );

        ComboBox<String> statusCombo =
                new ComboBox<>();

        statusCombo.getItems().addAll(
                "Available",
                "Busy",
                "On Leave"
        );

        statusCombo.setValue(
                "Available"
        );

        statusCombo.setMaxWidth(
                Double.MAX_VALUE
        );

        Button addButton =
                new Button(
                        "Add Nurse"
                );

        addButton.setPrefHeight(40);

        addButton.setMaxWidth(
                Double.MAX_VALUE
        );

        addButton.setStyle(
                "-fx-background-color: #7c3aed; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px;"
        );

        Label message =
                new Label();

        message.setStyle(
                "-fx-text-fill: #b91c1c; " +
                "-fx-font-size: 11px;"
        );

        addButton.setOnAction(e -> {

            String name =
                    nameField.getText().trim();

            String email =
                    emailField.getText().trim();

            String shift =
                    shiftCombo.getValue();

            String status =
                    statusCombo.getValue();

            if (name.isEmpty() ||
                    email.isEmpty()) {

                message.setText(
                        "Please enter nurse name and email."
                );

                return;
            }

            if (shift == null ||
                    shift.isEmpty()) {

                message.setText(
                        "Please select a shift."
                );

                return;
            }

            NurseStaffModel nurse =
                    new NurseStaffModel(
                            name,
                            email,
                            status,
                            shift
                    );

            Thread thread = new Thread(() -> {

                try {

                    nurseStaffController
                            .addNurseStaff(
                                    nurse
                            );

                    Platform.runLater(() -> {

                        stage.close();

                        loadNurses();
                    });

                } catch (
                        ExecutionException |
                        InterruptedException ex) {

                    ex.printStackTrace();

                    Platform.runLater(() ->
                            message.setText(
                                    "Unable to add nurse."
                            )
                    );
                }

            });

            thread.setDaemon(true);
            thread.start();
        });

        box.getChildren().addAll(
                title,
                nameField,
                emailField,
                shiftCombo,
                statusCombo,
                message,
                addButton
        );

        javafx.scene.Scene scene =
                new javafx.scene.Scene(
                        box,
                        360,
                        360
                );

        stage.setScene(scene);

        stage.show();
    }

    // UPDATE SELECTED DRIVERS

    private void updateSelectedDrivers() {

        int count =
                selectedDrivers.size();

        if (selectedDriverButton != null) {

            selectedDriverButton.setText(
                    "Selected Drivers  " +
                    count
            );
        }

        selectedDriverCount.setText(
                count +
                " Selected"
        );

        driverSelectedValue.setText(
                String.valueOf(
                        count
                )
        );

        selectedDriverNamesBox
                .getChildren()
                .clear();

        if (selectedDrivers.isEmpty()) {

            Label emptyLabel =
                    new Label(
                            "No drivers selected yet"
                    );

            emptyLabel.setStyle(
                    "-fx-font-size: 12px; " +
                    "-fx-text-fill: " + TEXT_MUTED + ";"
            );

            selectedDriverNamesBox
                    .getChildren()
                    .add(
                            emptyLabel
                    );

            return;
        }

        for (String driverEmail :
                selectedDrivers) {

            Label driverLabel =
                    new Label(
                            "✓  " +
                            driverEmail
                    );

            driverLabel.setStyle(
                    "-fx-background-color: #eff6ff; " +
                    "-fx-text-fill: #1d4ed8; " +
                    "-fx-font-size: 11px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 8px 12px; " +
                    "-fx-background-radius: 8px;"
            );

            selectedDriverNamesBox
                    .getChildren()
                    .add(
                            driverLabel
                    );
        }
    }

    // UPDATE SELECTED NURSES

    private void updateSelectedNurses() {

        int count =
                selectedNurses.size();

        if (selectedNurseButton != null) {

            selectedNurseButton.setText(
                    "Selected Nurses  " +
                    count
            );
        }

        selectedNurseCount.setText(
                count +
                " Selected"
        );

        nurseSelectedValue.setText(
                String.valueOf(
                        count
                )
        );

        selectedNurseNamesBox
                .getChildren()
                .clear();

        if (selectedNurses.isEmpty()) {

            Label emptyLabel =
                    new Label(
                            "No nurses selected yet"
                    );

            emptyLabel.setStyle(
                    "-fx-font-size: 12px; " +
                    "-fx-text-fill: " + TEXT_MUTED + ";"
            );

            selectedNurseNamesBox
                    .getChildren()
                    .add(
                            emptyLabel
                    );

            return;
        }

        for (String nurseEmail :
                selectedNurses) {

            Label nurseLabel =
                    new Label(
                            "✓  " +
                            nurseEmail
                    );

            nurseLabel.setStyle(
                    "-fx-background-color: #f5f3ff; " +
                    "-fx-text-fill: #7c3aed; " +
                    "-fx-font-size: 11px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 8px 12px; " +
                    "-fx-background-radius: 8px;"
            );

            selectedNurseNamesBox
                    .getChildren()
                    .add(
                            nurseLabel
                    );
        }
    }

    private String getInitials(
            String name) {

        if (name == null ||
                name.trim().isEmpty()) {

            return "ST";
        }

        String[] parts =
                name.trim().split(
                        "\\s+"
                );

        if (parts.length == 1) {

            return parts[0]
                    .substring(
                            0,
                            Math.min(
                                    2,
                                    parts[0].length()
                            )
                    )
                    .toUpperCase();
        }

        return (
                parts[0].charAt(0) +
                "" +
                parts[parts.length - 1]
                        .charAt(0)
        ).toUpperCase();
    }

    private String safeText(
            String value) {

        if (value == null ||
                value.trim().isEmpty()) {

            return "-";
        }

        return value;
    }

    private void applyStatusStyle(
            Label label,
            String status) {

        if ("Available".equalsIgnoreCase(
                status)) {

            label.setStyle(
                    "-fx-background-color: #dcfce7; " +
                    "-fx-text-fill: #15803d; " +
                    "-fx-font-size: 10px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 6px 9px; " +
                    "-fx-background-radius: 8px;"
            );

        } else if (
                "Busy".equalsIgnoreCase(status) ||
                "Assigned".equalsIgnoreCase(status) ||
                "On Duty".equalsIgnoreCase(status)
        ) {

            label.setStyle(
                    "-fx-background-color: #fee2e2; " +
                    "-fx-text-fill: #b91c1c; " +
                    "-fx-font-size: 10px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 6px 9px; " +
                    "-fx-background-radius: 8px;"
            );

        } else {

            label.setStyle(
                    "-fx-background-color: #fef3c7; " +
                    "-fx-text-fill: #b45309; " +
                    "-fx-font-size: 10px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 6px 9px; " +
                    "-fx-background-radius: 8px;"
            );
        }
    }

    private VBox createSummaryCard(
            String title,
            String value,
            String info,
            String iconBackground,
            String iconColor,
            String icon) {

        VBox card =
                new VBox(8);

        card.setPadding(
                new Insets(16)
        );

        card.setPrefHeight(105);

        card.setStyle(
                BASE_CARD_STYLE
        );

        Circle circle =
                new Circle(22);

        circle.setFill(
                Color.web(
                        iconBackground
                )
        );

        Text iconText =
                new Text(icon);

        iconText.setStyle(
                "-fx-font-size: 17px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " +
                iconColor + ";"
        );

        StackPane iconPane =
                new StackPane();

        iconPane.setPrefSize(
                44,
                44
        );

        iconPane.getChildren().addAll(
                circle,
                iconText
        );

        Text titleText =
                new Text(title);

        titleText.setStyle(
                "-fx-font-size: 10px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + TEXT_SECONDARY + ";"
        );

        Text valueText =
                new Text(value);

        valueText.setStyle(
                "-fx-font-size: 24px; " +
                "-fx-font-weight: bold; " +
                "-fx-fill: " + TEXT_PRIMARY + ";"
        );

        Text infoText =
                new Text(info);

        infoText.setStyle(
                "-fx-font-size: 10px; " +
                "-fx-fill: " + TEXT_SECONDARY + ";"
        );

        VBox textBox =
                new VBox(1);

        textBox.getChildren().addAll(
                titleText,
                valueText,
                infoText
        );

        HBox content =
                new HBox(13);

        content.setAlignment(
                Pos.CENTER_LEFT
        );

        content.getChildren().addAll(
                iconPane,
                textBox
        );

        card.getChildren().add(
                content
        );

        return card;
    }

    private Text getSummaryValue(
            VBox card) {

        HBox content =
                (HBox) card
                        .getChildren()
                        .get(0);

        VBox textBox =
                (VBox) content
                        .getChildren()
                        .get(1);

        return (Text) textBox
                .getChildren()
                .get(1);
    }
}