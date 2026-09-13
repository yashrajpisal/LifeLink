package com.kurukshetra.view.admin;

import com.kurukshetra.view.util.ShimmerLoader;
import com.kurukshetra.view.util.ShimmerLoader.ShimmerPane;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.kurukshetra.config.FirebaseConfig;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdminHospitalManagement {

    private static final String BG_PAGE = "#FAF7FB";
    private static final String BG_SURFACE = "#FFFFFF";
    private static final String BORDER_COLOR = "#E9E2EF";
    private static final String TEXT_PRIMARY = "#0F172A";
    private static final String TEXT_SECONDARY = "#5F5A70";
    private static final String TEXT_MUTED = "#8B8798";

    private static final String PURPLE_PRIMARY = "#9C7DF0";
    private static final String PURPLE_DARK = "#8B68E5";
    private static final String PURPLE_BUTTON = "#C084FC";
    private static final String PURPLE_LIGHT = "#F3E8FF";

    private static final String SUCCESS_TEXT = "#15803D";
    private static final String SUCCESS_BG = "#DCFCE7";

    private static final String DANGER_TEXT = "#E66A7A";
    private static final String DANGER_BG = "#FDE7EB";

    private static final String FONT_STACK = "Inter, Segoe UI, Arial";

    private static final String BASE_CARD_STYLE =
            "-fx-background-color: #FFFFFF;" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #E9E2EF;" +
            "-fx-border-radius: 18;" +
            "-fx-border-width: 1;";

    private static final String PRIMARY_BUTTON_STYLE =
            "-fx-background-color: #C084FC;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 10 18;" +
            "-fx-cursor: hand;";

    private static final String SECONDARY_BUTTON_STYLE =
            "-fx-background-color: #F3E8FF;" +
            "-fx-text-fill: #8B68E5;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 10 18;" +
            "-fx-cursor: hand;";

    private final List<DocumentSnapshot> hospitalList = new ArrayList<>();

    private VBox hospitalCardsBox;

    private StackPane mainRoot;

    private StackPane hospitalDetailsOverlay;


    public VBox getHospitalManagement() {

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(25));
        mainContent.setBackground(new Background(
                new BackgroundFill(
                        Color.web(BG_PAGE),
                        CornerRadii.EMPTY,
                        Insets.EMPTY
                )
        ));

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox titleBox = new VBox(4);

        Label title = new Label("Hospital Management");
        title.setStyle(
                "-fx-font-family: '" + FONT_STACK + "';" +
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );

        Label subtitle = new Label("Manage and monitor registered hospitals");
        subtitle.setStyle(
                "-fx-font-family: '" + FONT_STACK + "';" +
                "-fx-font-size: 13px;" +
                "-fx-text-fill: " + TEXT_SECONDARY + ";"
        );

        titleBox.getChildren().addAll(title, subtitle);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Button verifyHospitalButton = new Button("Verify Hospital");
        verifyHospitalButton.setStyle(PRIMARY_BUTTON_STYLE);

        Button addHospitalButton = new Button("+ Add Hospital");
        addHospitalButton.setStyle(PRIMARY_BUTTON_STYLE);

        header.getChildren().addAll(
                titleBox,
                headerSpacer,
                verifyHospitalButton,
                addHospitalButton
        );


        HBox filterBox = new HBox(12);
        filterBox.setAlignment(Pos.CENTER_LEFT);
        filterBox.setPadding(new Insets(15));

        filterBox.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 14;"
        );

        TextField searchField = new TextField();
        searchField.setPromptText("Search hospitals...");
        searchField.setPrefWidth(300);

        searchField.setStyle(
                "-fx-background-color: #FAF7FB;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: #E9E2EF;" +
                "-fx-border-radius: 10;" +
                "-fx-padding: 10 14;" +
                "-fx-font-size: 13px;"
        );

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.getItems().addAll(
                "All Status",
                "Emergency Available",
                "Available",
                "Busy",
                "Unavailable"
        );
        statusFilter.setValue("All Status");
        statusFilter.setPrefWidth(180);

        filterBox.getChildren().addAll(
                searchField,
                statusFilter
        );


        HBox hospitalTitleBox = new HBox();
        hospitalTitleBox.setAlignment(Pos.CENTER_LEFT);

        VBox hospitalTitleText = new VBox(3);

        Label hospitalTitle = new Label("Registered Hospitals");
        hospitalTitle.setStyle(
                "-fx-font-family: '" + FONT_STACK + "';" +
                "-fx-font-size: 19px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );

        Label hospitalSubtitle = new Label("Live data from Firebase");
        hospitalSubtitle.setStyle(
                "-fx-font-family: '" + FONT_STACK + "';" +
                "-fx-font-size: 12px;" +
                "-fx-text-fill: " + TEXT_MUTED + ";"
        );

        hospitalTitleText.getChildren().addAll(
                hospitalTitle,
                hospitalSubtitle
        );

        hospitalTitleBox.getChildren().add(hospitalTitleText);


        hospitalCardsBox = new VBox(15);
        hospitalCardsBox.setAlignment(Pos.TOP_CENTER);
        hospitalCardsBox.setPadding(new Insets(5));
        hospitalCardsBox.setFillWidth(true);


        ScrollPane hospitalScrollPane = new ScrollPane(hospitalCardsBox);
        hospitalScrollPane.setFitToWidth(true);
        hospitalScrollPane.setFitToHeight(false);
        hospitalScrollPane.setPrefHeight(600);
        hospitalScrollPane.setMinHeight(500);
        hospitalScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        hospitalScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        hospitalScrollPane.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background: transparent;"
        );

        VBox hospitalSection = new VBox(12);
        hospitalSection.getChildren().addAll(
                hospitalTitleBox,
                hospitalScrollPane
        );

        VBox.setVgrow(hospitalSection, Priority.ALWAYS);


        mainContent.getChildren().addAll(
                header,
                filterBox,
                hospitalSection
        );


        ScrollPane mainScrollPane = new ScrollPane(mainContent);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        mainScrollPane.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background: transparent;"
        );


        mainRoot = new StackPane(mainScrollPane);

        VBox root = new VBox(mainRoot);
        VBox.setVgrow(mainRoot, Priority.ALWAYS);


        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
            filterHospitals(searchField.getText(), statusFilter.getValue());
        });

        statusFilter.valueProperty().addListener((obs, oldValue, newValue) -> {
            filterHospitals(searchField.getText(), statusFilter.getValue());
        });


        loadHospitals();

        return root;
    }


    private void loadHospitals() {
        hospitalCardsBox.getChildren().clear();
        ShimmerPane hospShimmer = ShimmerLoader.createListSkeleton(3, 800, 150);
        hospitalCardsBox.getChildren().add(hospShimmer);

        new Thread(() -> {

            try {

                Firestore firestore = FirebaseConfig.getFirestore();

                ApiFuture<QuerySnapshot> future =
                        firestore.collection("hospital").get();

                QuerySnapshot snapshot = future.get();

                hospitalList.clear();
                hospitalList.addAll(snapshot.getDocuments());

                System.out.println(
                        "Hospitals loaded: " + hospitalList.size()
                );

                for (DocumentSnapshot hospital : hospitalList) {

                    System.out.println(
                            "Hospital ID: " + hospital.getId()
                    );

                    System.out.println(
                            "Hospital Name: " +
                            hospital.getString("hospitalName")
                    );

                    System.out.println(
                            "Distance: " +
                            hospital.get("distance")
                    );

                    System.out.println(
                            "ETA: " +
                            hospital.get("eta")
                    );
                }

                Platform.runLater(() -> {
                    ShimmerLoader.transition(hospitalCardsBox, hospShimmer, null);
                    displayHospitals(hospitalList);
                });

            } catch (Exception e) {

                e.printStackTrace();

                Platform.runLater(() -> {

                    hospitalCardsBox.getChildren().clear();

                    Label error = new Label(
                            "Unable to load hospitals from Firebase."
                    );

                    error.setStyle(
                            "-fx-text-fill: #E66A7A;" +
                            "-fx-font-size: 14px;" +
                            "-fx-font-weight: bold;"
                    );

                    hospitalCardsBox.getChildren().add(error);
                });
            }

        }).start();
    }


    private void displayHospitals(
            List<DocumentSnapshot> hospitals) {

        hospitalCardsBox.getChildren().clear();

        if (hospitals.isEmpty()) {

            Label emptyLabel =
                    new Label("No hospitals found.");

            emptyLabel.setStyle(
                    "-fx-font-size: 14px;" +
                    "-fx-text-fill: " + TEXT_MUTED + ";"
            );

            hospitalCardsBox.getChildren().add(emptyLabel);

            return;
        }


        for (DocumentSnapshot hospital : hospitals) {

            VBox card = createHospitalCard(hospital);

            hospitalCardsBox.getChildren().add(card);
        }
    }


    private VBox createHospitalCard(
            DocumentSnapshot hospital) {

        VBox card = new VBox(12);

        card.setMaxWidth(Double.MAX_VALUE);
        card.setPadding(new Insets(18));

        card.setStyle(BASE_CARD_STYLE);

        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetY(3);
        shadow.setColor(Color.rgb(60, 40, 80, 0.08));

        card.setEffect(shadow);


        String hospitalName =
                getString(hospital, "hospitalName", "Unknown Hospital");

        String address =
                getString(hospital, "address", "Address not available");

        String email =
                getString(hospital, "email", "Email not available");

        String contact =
                getString(hospital, "name", "Contact not available");

        String status =
                getString(hospital, "status", "Unknown");

        int doctors =
                getInt(hospital, "doctors", 0);


        HBox topRow = new HBox(15);
        topRow.setAlignment(Pos.CENTER_LEFT);


        StackPane hospitalIcon = new StackPane();

        Circle iconCircle =
                new Circle(24, Color.web(PURPLE_LIGHT));

        Text icon =
                new Text("H");

        icon.setStyle(
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: " + PURPLE_DARK + ";"
        );

        hospitalIcon.getChildren().addAll(
                iconCircle,
                icon
        );


        VBox nameBox = new VBox(4);

        Label nameLabel =
                new Label(hospitalName);

        nameLabel.setStyle(
                "-fx-font-family: '" + FONT_STACK + "';" +
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );

        Label addressLabel =
                new Label(address);

        addressLabel.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: " + TEXT_SECONDARY + ";"
        );

        addressLabel.setWrapText(true);

        nameBox.getChildren().addAll(
                nameLabel,
                addressLabel
        );


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        Label statusBadge =
                createStatusBadge(status);


        topRow.getChildren().addAll(
                hospitalIcon,
                nameBox,
                spacer,
                statusBadge
        );


        HBox infoRow = new HBox(35);
        infoRow.setAlignment(Pos.CENTER_LEFT);


        VBox emailBox =
                createInfoBox("Email", email);

        VBox contactBox =
                createInfoBox("Contact", contact);

        VBox bedsBox =
                createInfoBox("Gen Beds", "Loading...");

        VBox doctorsBox =
                createInfoBox("Doctors", String.valueOf(doctors));


        infoRow.getChildren().addAll(
                emailBox,
                contactBox,
                bedsBox,
                doctorsBox
        );


        new Thread(() -> {
            try {
                Firestore firestore = FirebaseConfig.getFirestore();
                DocumentSnapshot currentResource = firestore
                        .collection("hospital")
                        .document(hospital.getId())
                        .collection("resources")
                        .document("current")
                        .get()
                        .get();

                int availableGenBeds = getInt(currentResource, "availableGeneralBeds", 0);
                int totalGenBeds = getInt(currentResource, "totalGeneralBeds", 0);

                Platform.runLater(() -> {
                    Label valLbl = (Label) bedsBox.getChildren().get(1);
                    valLbl.setText(availableGenBeds + " / " + totalGenBeds);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    Label valLbl = (Label) bedsBox.getChildren().get(1);
                    valLbl.setText("N/A");
                });
            }
        }).start();


        HBox bottomRow = new HBox();
        bottomRow.setAlignment(Pos.CENTER_RIGHT);


        Button viewDetails =
                new Button("View Details");

        viewDetails.setStyle(
                SECONDARY_BUTTON_STYLE
        );

        viewDetails.setOnAction(e -> {
            showHospitalDetails(hospital);
        });


        bottomRow.getChildren().add(
                viewDetails
        );


        card.getChildren().addAll(
                topRow,
                infoRow,
                bottomRow
        );


        return card;
    }


    private void showHospitalDetails(
            DocumentSnapshot hospital) {

        String hospitalName =
                getString(
                        hospital,
                        "hospitalName",
                        "Unknown Hospital"
                );

        String address =
                getString(
                        hospital,
                        "address",
                        "Address not available"
                );

        String email =
                getString(
                        hospital,
                        "email",
                        "Email not available"
                );

        String contact =
                getString(
                        hospital,
                        "name",
                        "Contact not available"
                );

        String status =
                getString(
                        hospital,
                        "status",
                        "Unknown"
                );


        double distanceValue =
                getDouble(
                        hospital,
                        "distance",
                        0.0
                );

        int etaValue =
                getInt(
                        hospital,
                        "eta",
                        0
                );


        String distance =
                String.format(
                        Locale.US,
                        "%.1f km",
                        distanceValue
                );

        String eta =
                etaValue + " min";


        int doctors =
                getInt(
                        hospital,
                        "doctors",
                        0
                );


        createHospitalDetailsPopup(
                hospital,
                hospitalName,
                address,
                email,
                contact,
                status,
                distance,
                eta,
                doctors
        );
    }


    private void createHospitalDetailsPopup(
            DocumentSnapshot hospital,
            String hospitalName,
            String address,
            String email,
            String contact,
            String status,
            String distance,
            String eta,
            int doctors) {


        hospitalDetailsOverlay =
                new StackPane();

        hospitalDetailsOverlay.setStyle(
                "-fx-background-color: rgba(15, 23, 42, 0.35);"
        );


        VBox popup =
                new VBox();

        popup.setPrefWidth(650);
        popup.setMaxWidth(650);
        popup.setPrefHeight(700);
        popup.setMaxHeight(700);

        popup.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 24;"
        );


        DropShadow popupShadow =
                new DropShadow();

        popupShadow.setRadius(25);
        popupShadow.setOffsetY(10);
        popupShadow.setColor(
                Color.rgb(0, 0, 0, 0.25)
        );

        popup.setEffect(popupShadow);


        StackPane header =
                new StackPane();

        header.setPrefHeight(145);

        LinearGradient gradient =
                new LinearGradient(
                        0,
                        0,
                        1,
                        1,
                        true,
                        CycleMethod.NO_CYCLE,
                        new Stop(
                                0,
                                Color.web(PURPLE_PRIMARY)
                        ),
                        new Stop(
                                1,
                                Color.web(PURPLE_DARK)
                        )
                );

        header.setBackground(
                new Background(
                        new BackgroundFill(
                                gradient,
                                new CornerRadii(
                                        24,
                                        24,
                                        0,
                                        0,
                                        false
                                ),
                                Insets.EMPTY
                        )
                )
        );


        VBox headerContent =
                new VBox(8);

        headerContent.setAlignment(
                Pos.CENTER_LEFT
        );

        headerContent.setPadding(
                new Insets(22)
        );


        HBox hospitalHeader =
                new HBox(12);

        hospitalHeader.setAlignment(
                Pos.CENTER_LEFT
        );


        Circle headerCircle =
                new Circle(
                        25,
                        Color.rgb(255, 255, 255, 0.25)
                );

        StackPane headerIcon =
                new StackPane();

        Text h =
                new Text("H");

        h.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-fill: white;"
        );

        headerIcon.getChildren().addAll(
                headerCircle,
                h
        );


        VBox headerText =
                new VBox(3);

        Label headerTitle =
                new Label(hospitalName);

        headerTitle.setStyle(
                "-fx-font-size: 21px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;"
        );

        Label headerSubtitle =
                new Label("Hospital Details");

        headerSubtitle.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: rgba(255,255,255,0.8);"
        );

        headerText.getChildren().addAll(
                headerTitle,
                headerSubtitle
        );


        hospitalHeader.getChildren().addAll(
                headerIcon,
                headerText
        );


        headerContent.getChildren().add(
                hospitalHeader
        );


        StackPane.setAlignment(
                headerContent,
                Pos.CENTER_LEFT
        );


        Button closeButton =
                new Button("×");

        closeButton.setStyle(
                "-fx-background-color: rgba(255,255,255,0.20);" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 22px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 20;" +
                "-fx-min-width: 38;" +
                "-fx-min-height: 38;" +
                "-fx-cursor: hand;"
        );

        closeButton.setOnAction(
                e -> closeHospitalDetails()
        );


        StackPane.setAlignment(
                closeButton,
                Pos.TOP_RIGHT
        );

        StackPane.setMargin(
                closeButton,
                new Insets(15)
        );


        header.getChildren().addAll(
                headerContent,
                closeButton
        );


        VBox content =
                new VBox(16);

        content.setPadding(
                new Insets(20)
        );


        Label hospitalInfoTitle =
                new Label("Hospital Information");

        hospitalInfoTitle.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );


        VBox statusRow =
                new VBox(5);

        Label statusLabel =
                new Label("Current Status");

        statusLabel.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-text-fill: " + TEXT_MUTED + ";"
        );

        statusRow.getChildren().addAll(
                statusLabel,
                createStatusBadge(status)
        );


        VBox addressBox =
                createResourceBox(
                        "📍",
                        "Address",
                        address
                );


        VBox basicInfo =
                new VBox(10);

        HBox emailRow =
                new HBox(10);

        emailRow.setAlignment(
                Pos.CENTER_LEFT
        );

        Label emailIcon =
                new Label("✉");

        emailIcon.setStyle(
                "-fx-font-size: 17px;"
        );

        VBox emailText =
                new VBox(2);

        Label emailTitle =
                new Label("Email");

        emailTitle.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-text-fill: " + TEXT_MUTED + ";"
        );

        Label emailValue =
                new Label(email);

        emailValue.setStyle(
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );

        emailText.getChildren().addAll(
                emailTitle,
                emailValue
        );

        emailRow.getChildren().addAll(
                emailIcon,
                emailText
        );


        HBox contactRow =
                new HBox(10);

        contactRow.setAlignment(
                Pos.CENTER_LEFT
        );

        Label contactIcon =
                new Label("☎");

        contactIcon.setStyle(
                "-fx-font-size: 17px;"
        );

        VBox contactText =
                new VBox(2);

        Label contactTitle =
                new Label("Contact");

        contactTitle.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-text-fill: " + TEXT_MUTED + ";"
        );

        Label contactValue =
                new Label(contact);

        contactValue.setStyle(
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );

        contactText.getChildren().addAll(
                contactTitle,
                contactValue
        );

        contactRow.getChildren().addAll(
                contactIcon,
                contactText
        );


        basicInfo.getChildren().addAll(
                emailRow,
                contactRow
        );


        Label travelTitle =
                new Label("Travel Information");

        travelTitle.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );


        HBox travelBoxes =
                new HBox(12);

        VBox distanceBox =
                createResourceBox(
                        "↔",
                        "Distance",
                        distance
                );

        VBox etaBox =
                createResourceBox(
                        "⏱",
                        "Estimated Time",
                        eta
                );

        HBox.setHgrow(
                distanceBox,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                etaBox,
                Priority.ALWAYS
        );

        travelBoxes.getChildren().addAll(
                distanceBox,
                etaBox
        );


        Label resourceTitle =
                new Label("Live Resources");

        resourceTitle.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );


        Label liveLabel =
                new Label("● LIVE");

        liveLabel.setStyle(
                "-fx-text-fill: " + SUCCESS_TEXT + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        );


        HBox resourceHeading =
                new HBox();

        resourceHeading.setAlignment(
                Pos.CENTER_LEFT
        );

        Region resourceSpacer =
                new Region();

        HBox.setHgrow(
                resourceSpacer,
                Priority.ALWAYS
        );

        resourceHeading.getChildren().addAll(
                resourceTitle,
                resourceSpacer,
                liveLabel
        );


        VBox resourceContainer =
                new VBox(10);

        resourceContainer.setPadding(
                new Insets(12)
        );

        resourceContainer.setStyle(
                "-fx-background-color: #FAF7FB;" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 14;"
        );


        Label loadingLabel =
                new Label("Loading resources...");

        loadingLabel.setStyle(
                "-fx-text-fill: " + TEXT_MUTED + ";" +
                "-fx-font-size: 12px;"
        );

        resourceContainer.getChildren().add(
                loadingLabel
        );


        content.getChildren().addAll(
                hospitalInfoTitle,
                statusRow,
                addressBox,
                basicInfo,
                travelTitle,
                travelBoxes,
                resourceHeading,
                resourceContainer
        );


        ScrollPane contentScroll =
                new ScrollPane(content);

        contentScroll.setFitToWidth(true);
        contentScroll.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );
        contentScroll.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        contentScroll.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background: transparent;"
        );


        Button backButton =
                new Button("Back");

        backButton.setStyle(
                SECONDARY_BUTTON_STYLE
        );

        backButton.setOnAction(
                e -> closeHospitalDetails()
        );


        HBox bottom =
                new HBox();

        bottom.setAlignment(
                Pos.CENTER_RIGHT
        );

        bottom.setPadding(
                new Insets(10, 20, 18, 20)
        );

        bottom.getChildren().add(
                backButton
        );


        popup.getChildren().addAll(
                header,
                contentScroll,
                bottom
        );

        VBox.setVgrow(
                contentScroll,
                Priority.ALWAYS
        );


        hospitalDetailsOverlay.getChildren().add(
                popup
        );

        StackPane.setAlignment(
                popup,
                Pos.CENTER
        );


        mainRoot.getChildren().add(
                hospitalDetailsOverlay
        );


        loadHospitalResources(
                hospital,
                resourceContainer
        );
    }


    private void loadHospitalResources(
            DocumentSnapshot hospital,
            VBox resourceContainer) {

        new Thread(() -> {

            try {

                Firestore firestore =
                        FirebaseConfig.getFirestore();


                String hospitalId =
                        hospital.getId();


                DocumentSnapshot currentResource =
                        firestore
                                .collection("hospital")
                                .document(hospitalId)
                                .collection("resources")
                                .document("current")
                                .get()
                                .get();


                int doctors =
                        getInt(
                                hospital,
                                "doctors",
                                0
                        );


                QuerySnapshot otSnapshot =
                        firestore
                                .collection("hospital")
                                .document(hospitalId)
                                .collection("operationTheatres")
                                .get()
                                .get();


                int operationTheatres =
                        otSnapshot
                                .getDocuments()
                                .size();


                int availableEmergencyBeds =
                        getInt(
                                currentResource,
                                "availableEmergencyBeds",
                                0
                        );

                int totalEmergencyBeds =
                        getInt(
                                currentResource,
                                "totalEmergencyBeds",
                                0
                        );


                int availableGeneralBeds =
                        getInt(
                                currentResource,
                                "availableGeneralBeds",
                                0
                        );

                int totalGeneralBeds =
                        getInt(
                                currentResource,
                                "totalGeneralBeds",
                                0
                        );


                int availableICUBeds =
                        getInt(
                                currentResource,
                                "availableICUBeds",
                                0
                        );

                int totalICUBeds =
                        getInt(
                                currentResource,
                                "totalICUBeds",
                                0
                        );


                int availableVentilators =
                        getInt(
                                currentResource,
                                "availableVentilators",
                                0
                        );

                int totalVentilators =
                        getInt(
                                currentResource,
                                "totalVentilators",
                                0
                        );


                int oxygenReserve =
                        getInt(
                                currentResource,
                                "oxygenReserve",
                                0
                        );


                Platform.runLater(() -> {

                    displayResourceData(
                            resourceContainer,
                            availableEmergencyBeds,
                            totalEmergencyBeds,
                            availableGeneralBeds,
                            totalGeneralBeds,
                            availableICUBeds,
                            totalICUBeds,
                            doctors,
                            operationTheatres,
                            availableVentilators,
                            totalVentilators,
                            oxygenReserve
                    );
                });


            } catch (Exception e) {

                e.printStackTrace();

                Platform.runLater(() -> {

                    resourceContainer
                            .getChildren()
                            .clear();

                    Label error =
                            new Label(
                                    "Unable to load live resources."
                            );

                    error.setStyle(
                            "-fx-text-fill: " + DANGER_TEXT + ";" +
                            "-fx-font-size: 12px;"
                    );

                    resourceContainer
                            .getChildren()
                            .add(error);
                });
            }

        }).start();
    }


    private void displayResourceData(
            VBox container,
            int availableEmergencyBeds,
            int totalEmergencyBeds,
            int availableGeneralBeds,
            int totalGeneralBeds,
            int availableICUBeds,
            int totalICUBeds,
            int doctors,
            int operationTheatres,
            int availableVentilators,
            int totalVentilators,
            int oxygenReserve) {


        container.getChildren().clear();


        HBox row1 =
                new HBox(10);

        VBox emergencyBeds =
                createResourceBox(
                        "E",
                        "Emergency Beds",
                        availableEmergencyBeds +
                        " / " +
                        totalEmergencyBeds
                );

        VBox generalBeds =
                createResourceBox(
                        "G",
                        "General Beds",
                        availableGeneralBeds +
                        " / " +
                        totalGeneralBeds
                );

        HBox.setHgrow(
                emergencyBeds,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                generalBeds,
                Priority.ALWAYS
        );

        row1.getChildren().addAll(
                emergencyBeds,
                generalBeds
        );


        HBox row2 =
                new HBox(10);

        VBox icuBeds =
                createResourceBox(
                        "I",
                        "ICU Beds",
                        availableICUBeds +
                        " / " +
                        totalICUBeds
                );

        VBox doctorBox =
                createResourceBox(
                        "D",
                        "Doctors",
                        String.valueOf(doctors)
                );

        HBox.setHgrow(
                icuBeds,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                doctorBox,
                Priority.ALWAYS
        );

        row2.getChildren().addAll(
                icuBeds,
                doctorBox
        );


        HBox row3 =
                new HBox(10);

        VBox otBox =
                createResourceBox(
                        "O",
                        "Operation Theatres",
                        String.valueOf(operationTheatres)
                );

        VBox ventilatorBox =
                createResourceBox(
                        "V",
                        "Ventilators",
                        availableVentilators +
                        " / " +
                        totalVentilators
                );

        HBox.setHgrow(
                otBox,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                ventilatorBox,
                Priority.ALWAYS
        );

        row3.getChildren().addAll(
                otBox,
                ventilatorBox
        );


        HBox row4 =
                new HBox(10);

        VBox oxygenBox =
                createResourceBox(
                        "O",
                        "Oxygen Reserve",
                        String.valueOf(oxygenReserve)
                );

        HBox.setHgrow(
                oxygenBox,
                Priority.ALWAYS
        );

        row4.getChildren().add(
                oxygenBox
        );


        container.getChildren().addAll(
                row1,
                row2,
                row3,
                row4
        );
    }


    private VBox createResourceBox(
            String icon,
            String title,
            String value) {

        VBox box =
                new VBox(5);

        box.setPadding(
                new Insets(12)
        );

        box.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: " + BORDER_COLOR + ";" +
                "-fx-border-radius: 12;"
        );


        HBox top =
                new HBox(8);

        top.setAlignment(
                Pos.CENTER_LEFT
        );


        Label iconLabel =
                new Label(icon);

        iconLabel.setStyle(
                "-fx-background-color: " + PURPLE_LIGHT + ";" +
                "-fx-text-fill: " + PURPLE_DARK + ";" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 5 8;"
        );


        Label titleLabel =
                new Label(title);

        titleLabel.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-text-fill: " + TEXT_MUTED + ";"
        );


        top.getChildren().addAll(
                iconLabel,
                titleLabel
        );


        Label valueLabel =
                new Label(value);

        valueLabel.setStyle(
                "-fx-font-size: 17px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );


        box.getChildren().addAll(
                top,
                valueLabel
        );


        return box;
    }


    private VBox createInfoBox(
            String title,
            String value) {

        VBox box =
                new VBox(3);

        Label titleLabel =
                new Label(title);

        titleLabel.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-text-fill: " + TEXT_MUTED + ";"
        );

        Label valueLabel =
                new Label(value);

        valueLabel.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );

        valueLabel.setWrapText(true);

        box.getChildren().addAll(
                titleLabel,
                valueLabel
        );

        HBox.setHgrow(
                box,
                Priority.ALWAYS
        );

        return box;
    }


    private Label createStatusBadge(
            String status) {

        Label badge =
                new Label(status);

        String lowerStatus =
                status.toLowerCase();


        if (lowerStatus.contains("emergency") ||
                lowerStatus.contains("available")) {

            badge.setStyle(
                    "-fx-background-color: " +
                    SUCCESS_BG + ";" +
                    "-fx-text-fill: " +
                    SUCCESS_TEXT + ";" +
                    "-fx-font-size: 11px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 12;" +
                    "-fx-padding: 5 10;"
            );

        } else {

            badge.setStyle(
                    "-fx-background-color: " +
                    DANGER_BG + ";" +
                    "-fx-text-fill: " +
                    DANGER_TEXT + ";" +
                    "-fx-font-size: 11px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 12;" +
                    "-fx-padding: 5 10;"
            );
        }


        return badge;
    }


    private int getInt(
            DocumentSnapshot document,
            String field,
            int defaultValue) {

        try {

            Object value =
                    document.get(field);

            if (value == null) {
                return defaultValue;
            }

            if (value instanceof Number) {

                return ((Number) value).intValue();
            }

            return Integer.parseInt(
                    value.toString()
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not read integer field: " +
                    field
            );

            return defaultValue;
        }
    }


    private double getDouble(
            DocumentSnapshot document,
            String field,
            double defaultValue) {

        try {

            Object value =
                    document.get(field);

            if (value == null) {
                return defaultValue;
            }

            if (value instanceof Number) {

                return ((Number) value).doubleValue();
            }

            return Double.parseDouble(
                    value.toString()
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not read double field: " +
                    field
            );

            return defaultValue;
        }
    }


    private String getString(
            DocumentSnapshot document,
            String field,
            String defaultValue) {

        try {

            Object value =
                    document.get(field);

            if (value == null) {
                return defaultValue;
            }

            String text =
                    value.toString().trim();

            if (text.isEmpty()) {
                return defaultValue;
            }

            return text;

        } catch (Exception e) {

            return defaultValue;
        }
    }


    private void closeHospitalDetails() {

        if (hospitalDetailsOverlay != null) {

            mainRoot
                    .getChildren()
                    .remove(
                            hospitalDetailsOverlay
                    );

            hospitalDetailsOverlay = null;
        }
    }


    private void filterHospitals(
            String searchText,
            String selectedStatus) {

        String search =
                searchText == null
                        ? ""
                        : searchText
                                .trim()
                                .toLowerCase();


        List<DocumentSnapshot> filtered =
                new ArrayList<>();


        for (DocumentSnapshot hospital :
                hospitalList) {

            String name =
                    getString(
                            hospital,
                            "hospitalName",
                            ""
                    ).toLowerCase();

            String address =
                    getString(
                            hospital,
                            "address",
                            ""
                    ).toLowerCase();

            String email =
                    getString(
                            hospital,
                            "email",
                            ""
                    ).toLowerCase();

            String status =
                    getString(
                            hospital,
                            "status",
                            ""
                    );


            boolean matchesSearch =
                    search.isEmpty() ||
                    name.contains(search) ||
                    address.contains(search) ||
                    email.contains(search);


            boolean matchesStatus =
                    selectedStatus == null ||
                    selectedStatus.equals("All Status") ||
                    status.equalsIgnoreCase(
                            selectedStatus
                    );


            if (matchesSearch &&
                    matchesStatus) {

                filtered.add(hospital);
            }
        }


        displayHospitals(filtered);
    }
}