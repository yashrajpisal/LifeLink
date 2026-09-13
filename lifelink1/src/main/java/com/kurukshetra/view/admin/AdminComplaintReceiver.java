package com.kurukshetra.view.admin;

import com.kurukshetra.view.util.ShimmerLoader;
import com.kurukshetra.view.util.ShimmerLoader.ShimmerPane;
import com.kurukshetra.controller.hospitalController.HospitalComplaintController;
import com.kurukshetra.model.hospitalModel.HospitalComplaintModel;

import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdminComplaintReceiver {

    private final String BG_PAGE = "#FAF7FB";
    private final String BG_SURFACE = "#FFFFFF";
    private final String BORDER_COLOR = "#E9E2EF";
    private final String TEXT_PRIMARY = "#0F172A";
    private final String TEXT_SECONDARY = "#5F5A70";
    private final String TEXT_MUTED = "#8B8798";
    private final String PURPLE_PRIMARY = "#9C7DF0";
    private final String PURPLE_DARK = "#8B68E5";
    private final String PURPLE_LIGHT = "#F3E8FF";
    private final String SUCCESS_TEXT = "#22C55E";
    private final String SUCCESS_BG = "#DCFCE7";
    private final String WARNING_TEXT = "#F59E0B";
    private final String WARNING_BG = "#FEF3C7";
    private final String DANGER_TEXT = "#E66A7A";
    private final String DANGER_BG = "#FDE7EB";
    private final String CARD_SHADOW = "-fx-effect: dropshadow(gaussian, rgba(156, 125, 240, 0.08), 16, 0.1, 0, 4);";
    private final String FONT_STACK = "-fx-font-family: 'Segoe UI', 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;";

    private BorderPane mainPane;

    private HBox hospitalComplaintContainer;
    private HBox driverComplaintContainer;
    private HBox nurseComplaintContainer;

    private TextField searchField;

    private final HospitalComplaintController complaintController = new HospitalComplaintController();

    public ScrollPane getComplaintReceiver() {

        mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color: " + BG_PAGE + ";" + FONT_STACK);

        VBox content = new VBox(24);
        content.setPadding(new Insets(25));
        content.setFillWidth(true);
        content.setStyle("-fx-background-color: " + BG_PAGE + ";");

        VBox header = new VBox(5);

        Label title = new Label("Complaint Receiver");
        title.setStyle(FONT_STACK + "-fx-font-size: 28px;-fx-font-weight: 700;-fx-text-fill: " + TEXT_PRIMARY + ";");

        Label subtitle = new Label("Review and manage complaints received from hospitals, drivers and nurses.");
        subtitle.setStyle(FONT_STACK + "-fx-font-size: 14px;-fx-text-fill: " + TEXT_SECONDARY + ";");

        header.getChildren().addAll(title, subtitle);

        HBox topBar = new HBox(12);
        topBar.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("Search complaints...");
        searchField.setPrefHeight(42);
        searchField.setPrefWidth(430);
        searchField.setStyle(FONT_STACK + "-fx-background-color: " + BG_SURFACE + ";-fx-background-radius: 10px;-fx-border-color: " + BORDER_COLOR + ";-fx-border-radius: 10px;-fx-padding: 0 14px;-fx-font-size: 14px;");

        Button refreshButton = new Button("Refresh");
        refreshButton.setPrefHeight(42);
        refreshButton.setPrefWidth(110);
        refreshButton.setStyle(FONT_STACK + "-fx-background-color: " + PURPLE_PRIMARY + ";-fx-text-fill: white;-fx-background-radius: 10px;-fx-font-size: 14px;-fx-font-weight: 600;-fx-cursor: hand;");

        refreshButton.setOnAction(e -> {
            searchField.clear();
            loadHospitalComplaints();
        });

        topBar.getChildren().addAll(searchField, refreshButton);

        VBox hospitalSection = createSection(
                "🏥  Complaints from Hospital",
                "Complaints submitted by hospital management."
        );

        hospitalComplaintContainer = new HBox(18);
        hospitalComplaintContainer.setAlignment(Pos.TOP_LEFT);
        hospitalComplaintContainer.setPadding(new Insets(5));


        ScrollPane hospitalScroll = createHorizontalScroll(hospitalComplaintContainer);
        hospitalSection.getChildren().add(hospitalScroll);

        VBox driverSection = createSection(
                "🚑  Complaints from Driver",
                "Complaints submitted by ambulance drivers."
        );

        driverComplaintContainer = new HBox(18);
        driverComplaintContainer.setAlignment(Pos.TOP_LEFT);
        driverComplaintContainer.setPadding(new Insets(5));

        VBox driverComplaintCard = createStaticComplaintCard(
            "Patient Pickup Delay",
            "MEDIUM",
            "City Hospital",
            "AMB-104",
            "Rajesh Patil",
            "N/A",
            "Driver reported a delay while reaching the patient pickup location.",
            "PENDING"
        );

        driverComplaintContainer.getChildren().add(driverComplaintCard);

        ScrollPane driverScroll = createHorizontalScroll(driverComplaintContainer);
        driverSection.getChildren().add(driverScroll);

        VBox nurseSection = createSection(
                "👩‍⚕️  Complaints from Nurse",
                "Complaints submitted by nurses."
        );

        nurseComplaintContainer = new HBox(18);
        nurseComplaintContainer.setAlignment(Pos.TOP_LEFT);
        nurseComplaintContainer.setPadding(new Insets(5));

        VBox nurseComplaintCard = createStaticComplaintCard(
            "Nurse Coordination Issue",
            "HIGH",
            "KEM Hospital Pune",
            "AMB-102",
            "Amit Sharma",
            "Priya Deshmukh",
            "Nurse reported a coordination issue during patient handover.",
            "PENDING"
        );

        nurseComplaintContainer.getChildren().add(nurseComplaintCard);

        ScrollPane nurseScroll = createHorizontalScroll(nurseComplaintContainer);
        nurseSection.getChildren().add(nurseScroll);

        content.getChildren().addAll(
                header,
                topBar,
                hospitalSection,
                driverSection,
                nurseSection
        );

        ScrollPane mainScroll = new ScrollPane(content);
        mainScroll.setFitToWidth(true);
        mainScroll.setFitToHeight(false);
        mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScroll.setPannable(true);
        mainScroll.setStyle("-fx-background-color: transparent;-fx-background: transparent;");

        loadHospitalComplaints();

        return mainScroll;
    }

    private VBox createSection(String titleText, String subtitleText) {

        VBox section = new VBox(12);
        section.setPadding(new Insets(20));
        section.setFillWidth(true);
        section.setStyle(
                "-fx-background-color: " + BG_SURFACE + ";" +
                "-fx-background-radius: 14px;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 14px;" +
                CARD_SHADOW
        );

        VBox heading = new VBox(4);

        Label title = new Label(titleText);
        title.setStyle(
                FONT_STACK +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: 700;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );

        Label subtitle = new Label(subtitleText);
        subtitle.setStyle(
                FONT_STACK +
                "-fx-font-size: 13px;" +
                "-fx-text-fill: " + TEXT_SECONDARY + ";"
        );

        heading.getChildren().addAll(title, subtitle);

        section.getChildren().add(heading);

        return section;
    }

    private ScrollPane createHorizontalScroll(HBox container) {

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setContent(container);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(false);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
        scrollPane.setPrefHeight(310);
        scrollPane.setMinHeight(310);
        scrollPane.setStyle("-fx-background-color: transparent;-fx-background: transparent;");

        return scrollPane;
    }

    private void loadHospitalComplaints() {

        if (hospitalComplaintContainer == null) {
            return;
        }

        hospitalComplaintContainer.getChildren().clear();

        VBox loadingCard = createComingSoonCard(
                "Loading hospital complaints..."
        );

        hospitalComplaintContainer.getChildren().add(loadingCard);

        new Thread(() -> {

            try {

                List<HospitalComplaintModel> complaints =
                        complaintController.getAllHospitalComplaints();

                Platform.runLater(() -> {

                    hospitalComplaintContainer.getChildren().clear();

                    if (complaints.isEmpty()) {

                        VBox emptyCard = createEmptyCard(
                                "📋",
                                "No Hospital Complaints",
                                "There are currently no complaints submitted by hospitals."
                        );

                        hospitalComplaintContainer.getChildren().add(emptyCard);

                    } else {

                        for (HospitalComplaintModel complaint : complaints) {

                            VBox card = createComplaintCard(complaint);

                            hospitalComplaintContainer.getChildren().add(card);
                        }
                    }
                });

            } catch (Exception ex) {

                ex.printStackTrace();

                Platform.runLater(() -> {

                    hospitalComplaintContainer.getChildren().clear();

                    VBox errorCard = createEmptyCard(
                            "⚠",
                            "Unable to Load Complaints",
                            "There was a problem while fetching hospital complaints."
                    );

                    hospitalComplaintContainer.getChildren().add(errorCard);
                });
            }

        }).start();
    }

    private VBox createComplaintCard(HospitalComplaintModel complaint) {

        String priority = complaint.getPriority() != null
                ? complaint.getPriority()
                : "N/A";

        String complaintType = complaint.getComplaintType() != null
                ? complaint.getComplaintType()
                : "Complaint";

        String hospitalName = complaint.getHospitalName() != null
                ? complaint.getHospitalName()
                : "N/A";

        String ambulanceId = complaint.getAmbulanceId() != null
                ? complaint.getAmbulanceId()
                : "N/A";

        String driverEmail = complaint.getDriverEmail() != null
                ? complaint.getDriverEmail()
                : "N/A";

        String nurseEmail = complaint.getNurseEmail() != null
                ? complaint.getNurseEmail()
                : "N/A";

        String status = complaint.getStatus() != null
                ? complaint.getStatus()
                : "N/A";

        String description = complaint.getDescription() != null
                ? complaint.getDescription()
                : "No description available.";

        String timestamp = complaint.getTimestamp() != null
                ? complaint.getTimestamp().toString()
                : "N/A";

        VBox card = new VBox(12);

        card.setPrefWidth(340);
        card.setMinWidth(340);
        card.setMaxWidth(340);

        card.setPrefHeight(275);
        card.setMinHeight(275);
        card.setMaxHeight(275);

        card.setPadding(new Insets(18));

        card.setStyle(
                "-fx-background-color: " + BG_SURFACE + ";" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 12px;" +
                CARD_SHADOW
        );

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label complaintTitle = new Label(complaintType);
        complaintTitle.setWrapText(true);
        complaintTitle.setMaxWidth(220);

        complaintTitle.setStyle(
                FONT_STACK +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: 700;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );

        Label priorityLabel = new Label(priority);

        if ("HIGH".equalsIgnoreCase(priority)) {

            priorityLabel.setStyle(
                    FONT_STACK +
                    "-fx-font-size: 10px;" +
                    "-fx-font-weight: 700;" +
                    "-fx-text-fill: " + DANGER_TEXT + ";" +
                    "-fx-background-color: " + DANGER_BG + ";" +
                    "-fx-background-radius: 20px;" +
                    "-fx-padding: 5px 9px;"
            );

        } else {

            priorityLabel.setStyle(
                    FONT_STACK +
                    "-fx-font-size: 10px;" +
                    "-fx-font-weight: 700;" +
                    "-fx-text-fill: " + WARNING_TEXT + ";" +
                    "-fx-background-color: " + WARNING_BG + ";" +
                    "-fx-background-radius: 20px;" +
                    "-fx-padding: 5px 9px;"
            );
        }

        topRow.getChildren().addAll(
                complaintTitle,
                priorityLabel
        );

        Label hospitalLabel = createInfoLabel(
                "Hospital: " + hospitalName
        );

        Label ambulanceLabel = createInfoLabel(
                "Ambulance: " + ambulanceId
        );

        Label driverLabel = createInfoLabel(
                "Driver: " + driverEmail
        );

        Label nurseLabel = createInfoLabel(
                "Nurse: " + nurseEmail
        );

        Label descriptionLabel = new Label(
                "Description: " + description
        );

        descriptionLabel.setWrapText(true);
        descriptionLabel.setPrefHeight(55);
        descriptionLabel.setMaxHeight(55);

        descriptionLabel.setStyle(
                FONT_STACK +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                "-fx-background-color: " + BG_PAGE + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-padding: 10px;"
        );

        HBox bottomRow = new HBox(10);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        Label statusLabel = new Label(status);

        if ("PENDING".equalsIgnoreCase(status)) {

            statusLabel.setStyle(
                    FONT_STACK +
                    "-fx-font-size: 10px;" +
                    "-fx-font-weight: 700;" +
                    "-fx-text-fill: " + WARNING_TEXT + ";" +
                    "-fx-background-color: " + WARNING_BG + ";" +
                    "-fx-background-radius: 20px;" +
                    "-fx-padding: 5px 9px;"
            );

        } else {

            statusLabel.setStyle(
                    FONT_STACK +
                    "-fx-font-size: 10px;" +
                    "-fx-font-weight: 700;" +
                    "-fx-text-fill: " + SUCCESS_TEXT + ";" +
                    "-fx-background-color: " + SUCCESS_BG + ";" +
                    "-fx-background-radius: 20px;" +
                    "-fx-padding: 5px 9px;"
            );
        }

        Label timeLabel = new Label(timestamp);
        timeLabel.setMaxWidth(120);

        timeLabel.setStyle(
                FONT_STACK +
                "-fx-font-size: 10px;" +
                "-fx-text-fill: " + TEXT_MUTED + ";"
        );



        Button acknowledgeButton = new Button("Acknowledge");

        acknowledgeButton.setPrefHeight(32);

        acknowledgeButton.setStyle(
                FONT_STACK +
                "-fx-background-color: " + SUCCESS_BG + ";" +
                "-fx-text-fill: " + SUCCESS_TEXT + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: 600;" +
                "-fx-cursor: hand;"
        );

        if ("PENDING".equalsIgnoreCase(status)) {

            acknowledgeButton.setText("Acknowledge");

            acknowledgeButton.setOnAction(e -> {

                try {

                    complaintController.updateComplaintStatus(
                            complaint.getComplaintId(),
                            "ACKNOWLEDGED"
                    );

                    loadHospitalComplaints();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

        } else {

            acknowledgeButton.setText("Acknowledged");
            acknowledgeButton.setDisable(true);
        }

        Button viewButton = new Button("View Details");

        viewButton.setPrefHeight(32);

        viewButton.setStyle(
                FONT_STACK +
                "-fx-background-color: " + PURPLE_LIGHT + ";" +
                "-fx-text-fill: " + PURPLE_DARK + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: 600;" +
                "-fx-cursor: hand;"
        );

        viewButton.setOnAction(e -> showComplaintDetails(complaint));

        bottomRow.getChildren().addAll(
                statusLabel,
                acknowledgeButton,
                viewButton
        );

        

        card.getChildren().addAll(
                topRow,
                hospitalLabel,
                ambulanceLabel,
                driverLabel,
                nurseLabel,
                descriptionLabel,
                bottomRow
        );

        return card;
    }




    private VBox createStaticComplaintCard(
            String complaintType,
            String priority,
            String hospitalName,
            String ambulanceId,
            String driverName,
            String nurseName,
            String description,
            String status) {

        VBox card = new VBox(12);

        card.setPrefWidth(340);
        card.setMinWidth(340);
        card.setMaxWidth(340);

        card.setPrefHeight(275);
        card.setMinHeight(275);
        card.setMaxHeight(275);

        card.setPadding(new Insets(18));

        card.setStyle(
                "-fx-background-color: " + BG_SURFACE + ";" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 12px;" +
                CARD_SHADOW
        );

        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label complaintTitle = new Label(complaintType);
        complaintTitle.setWrapText(true);
        complaintTitle.setMaxWidth(220);

        complaintTitle.setStyle(
                FONT_STACK +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: 700;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );

        Label priorityLabel = new Label(priority);

        if ("HIGH".equalsIgnoreCase(priority)) {

            priorityLabel.setStyle(
                    FONT_STACK +
                    "-fx-font-size: 10px;" +
                    "-fx-font-weight: 700;" +
                    "-fx-text-fill: " + DANGER_TEXT + ";" +
                    "-fx-background-color: " + DANGER_BG + ";" +
                    "-fx-background-radius: 20px;" +
                    "-fx-padding: 5px 9px;"
            );

        } else {

            priorityLabel.setStyle(
                    FONT_STACK +
                    "-fx-font-size: 10px;" +
                    "-fx-font-weight: 700;" +
                    "-fx-text-fill: " + WARNING_TEXT + ";" +
                    "-fx-background-color: " + WARNING_BG + ";" +
                    "-fx-background-radius: 20px;" +
                    "-fx-padding: 5px 9px;"
            );
        }

        topRow.getChildren().addAll(
                complaintTitle,
                priorityLabel
        );

        Label hospitalLabel = createInfoLabel(
                "Hospital: " + hospitalName
        );

        Label ambulanceLabel = createInfoLabel(
                "Ambulance: " + ambulanceId
        );

        Label driverLabel = createInfoLabel(
                "Driver: " + driverName
        );

        Label nurseLabel = createInfoLabel(
                "Nurse: " + nurseName
        );

        Label descriptionLabel = new Label(
                "Description: " + description
        );

        descriptionLabel.setWrapText(true);
        descriptionLabel.setPrefHeight(55);
        descriptionLabel.setMaxHeight(55);

        descriptionLabel.setStyle(
                FONT_STACK +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";" +
                "-fx-background-color: " + BG_PAGE + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-padding: 10px;"
        );

        HBox bottomRow = new HBox(10);
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        Label statusLabel = new Label(status);

        statusLabel.setStyle(
                FONT_STACK +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: 700;" +
                "-fx-text-fill: " + WARNING_TEXT + ";" +
                "-fx-background-color: " + WARNING_BG + ";" +
                "-fx-background-radius: 20px;" +
                "-fx-padding: 5px 9px;"
        );

        Button acknowledgeButton = new Button("Acknowledge");

        acknowledgeButton.setPrefHeight(32);

        acknowledgeButton.setStyle(
                FONT_STACK +
                "-fx-background-color: " + SUCCESS_BG + ";" +
                "-fx-text-fill: " + SUCCESS_TEXT + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: 600;" +
                "-fx-cursor: hand;"
        );

        acknowledgeButton.setOnAction(e -> {

            statusLabel.setText("ACKNOWLEDGED");
            acknowledgeButton.setText("Acknowledged");
            acknowledgeButton.setDisable(true);

            statusLabel.setStyle(
                    FONT_STACK +
                    "-fx-font-size: 10px;" +
                    "-fx-font-weight: 700;" +
                    "-fx-text-fill: " + SUCCESS_TEXT + ";" +
                    "-fx-background-color: " + SUCCESS_BG + ";" +
                    "-fx-background-radius: 20px;" +
                    "-fx-padding: 5px 9px;"
            );
        });

        Button viewButton = new Button("View Details");

        viewButton.setPrefHeight(32);

        viewButton.setStyle(
                FONT_STACK +
                "-fx-background-color: " + PURPLE_LIGHT + ";" +
                "-fx-text-fill: " + PURPLE_DARK + ";" +
                "-fx-background-radius: 8px;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: 600;" +
                "-fx-cursor: hand;"
        );

        bottomRow.getChildren().addAll(
                statusLabel,
                acknowledgeButton,
                viewButton
        );

        card.getChildren().addAll(
                topRow,
                hospitalLabel,
                ambulanceLabel,
                driverLabel,
                nurseLabel,
                descriptionLabel,
                bottomRow
        );

        return card;
    }










    private Label createInfoLabel(String text) {

        Label label = new Label(text);

        label.setMaxWidth(300);

        label.setStyle(
                FONT_STACK +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: " + TEXT_SECONDARY + ";"
        );

        return label;
    }

    private VBox createEmptyCard(
            String iconText,
            String titleText,
            String messageText) {

        VBox card = new VBox(8);

        card.setAlignment(Pos.CENTER);

        card.setPrefWidth(340);
        card.setMinWidth(340);
        card.setMaxWidth(340);

        card.setPrefHeight(275);
        card.setMinHeight(275);
        card.setMaxHeight(275);

        card.setPadding(new Insets(25));

        card.setStyle(
                "-fx-background-color: " + BG_PAGE + ";" +
                "-fx-background-radius: 12px;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 12px;"
        );

        Label icon = new Label(iconText);
        icon.setStyle("-fx-font-size: 30px;");

        Label title = new Label(titleText);

        title.setStyle(
                FONT_STACK +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: 600;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );

        Label message = new Label(messageText);

        message.setWrapText(true);
        message.setAlignment(Pos.CENTER);
        message.setMaxWidth(260);

        message.setStyle(
                FONT_STACK +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: " + TEXT_MUTED + ";"
        );

        card.getChildren().addAll(
                icon,
                title,
                message
        );

        return card;
    }

    private VBox createComingSoonCard(String messageText) {

        return createEmptyCard(
                "📋",
                "Coming Later",
                messageText
        );
    }

    private void showComplaintDetails(
            HospitalComplaintModel complaint) {

        VBox details = new VBox(10);

        details.setPadding(new Insets(20));

        Label title = new Label("Complaint Details");

        title.setStyle(
                FONT_STACK +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: 700;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );

        Label complaintId = createInfoLabel(
                "Complaint ID: " +
                (complaint.getComplaintId() != null
                        ? complaint.getComplaintId()
                        : "N/A")
        );

        Label hospital = createInfoLabel(
                "Hospital: " +
                (complaint.getHospitalName() != null
                        ? complaint.getHospitalName()
                        : "N/A")
        );

        Label type = createInfoLabel(
                "Complaint Type: " +
                (complaint.getComplaintType() != null
                        ? complaint.getComplaintType()
                        : "N/A")
        );

        Label ambulance = createInfoLabel(
                "Ambulance: " +
                (complaint.getAmbulanceId() != null
                        ? complaint.getAmbulanceId()
                        : "N/A")
        );

        Label driver = createInfoLabel(
                "Driver: " +
                (complaint.getDriverEmail() != null
                        ? complaint.getDriverEmail()
                        : "N/A")
        );

        Label nurse = createInfoLabel(
                "Nurse: " +
                (complaint.getNurseEmail() != null
                        ? complaint.getNurseEmail()
                        : "N/A")
        );

        Label description = createInfoLabel(
                "Description: " +
                (complaint.getDescription() != null
                        ? complaint.getDescription()
                        : "N/A")
        );

        description.setWrapText(true);

        Label status = createInfoLabel(
                "Status: " +
                (complaint.getStatus() != null
                        ? complaint.getStatus()
                        : "N/A")
        );

        Label priority = createInfoLabel(
                "Priority: " +
                (complaint.getPriority() != null
                        ? complaint.getPriority()
                        : "N/A")
        );

        details.getChildren().addAll(
                title,
                complaintId,
                hospital,
                type,
                ambulance,
                driver,
                nurse,
                description,
                status,
                priority
        );

        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle("Complaint Details");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(details);
        alert.showAndWait();
    }
}