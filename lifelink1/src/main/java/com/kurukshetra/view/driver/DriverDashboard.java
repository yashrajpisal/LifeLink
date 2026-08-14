package com.kurukshetra.view.driver;

// public class DriverDashboard {

// }

// package com.kurukshetra.view.hospital;

import com.kurukshetra.view.Welcome;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DriverDashboard extends Application {

    public static Stage hospitalStage;
    private Scene hospitalScene;

    public static BorderPane root;

    private static final String BODY_BACKGROUND = "#F7F9FC";
    private static final String WHITE = "#FFFFFF";

    private static final String BLUE = "#0754D9";
    private static final String DARK_BLUE = "#0645B8";
    private static final String LIGHT_BLUE = "#EAF2FF";
    private static final String ACTIVE_BLUE = "#DCE9FF";

    private static final String BORDER = "#D7DFEB";
    private static final String DIVIDER = "#E3E7EF";

    private static final String TEXT = "#101828";
    private static final String SECONDARY_TEXT = "#475467";
    private static final String MUTED_TEXT = "#667085";

    private static final String GREEN = "#087A35";
    private static final String GREEN_BACKGROUND = "#DDF8E8";

    private static final String RED = "#C91C1C";
    private static final String RED_BACKGROUND = "#FFE3E0";

    private static final String HEADER_BACKGROUND = "#FBFCFE";



    private final ObservableList<TripRecord> tripRecords =
            FXCollections.observableArrayList(

                    new TripRecord(
                            "TRP-8492-A",
                            "Oct 24,\n14:32",
                            "Sarah Jenkins",
                            "Mercy General\nHosp.",
                            "4.2 mi",
                            "11 min",
                            "Critical\nHandover",
                            true
                    ),

                    new TripRecord(
                            "TRP-8491-C",
                            "Oct 24,\n12:15",
                            "Michael Chang",
                            "St. Jude Medical",
                            "7.8 mi",
                            "24 min",
                            "Completed",
                            false
                    ),

                    new TripRecord(
                            "TRP-8490-B",
                            "Oct 24,\n09:45",
                            "Unknown Male\n(UID-44)",
                            "City Central\nTrauma",
                            "2.1 mi",
                            "6 min",
                            "Critical\nHandover",
                            true
                    ),

                    new TripRecord(
                            "TRP-8489-C",
                            "Oct 24,\n08:10",
                            "Elena Rodriguez",
                            "Mercy General\nHosp.",
                            "5.5 mi",
                            "18 min",
                            "Completed",
                            false
                    ),

                    new TripRecord(
                            "TRP-8488-C",
                            "Oct 24,\n07:30",
                            "David Smith",
                            "Westside Clinic",
                            "3.0 mi",
                            "12 min",
                            "Completed",
                            false
                    )
            );



    @Override
    public void start(Stage stage) {

        hospitalStage = stage;

        root = new BorderPane();

        root.setStyle(
                "-fx-background-color: " +
                        BODY_BACKGROUND + ";"
        );

        VBox sidebar =
                createSidebar(root);

        root.setLeft(sidebar);


        // HBox topHeader =
        //         createTopHeader();

        // root.setTop(topHeader);

        // root.setCenter(
        //         createScrollPane(
        //                 createTripHistoryBody()
        //         )
        // );


      
        root.setCenter(  createScrollPane(
                            createSimplePage(
                                    "Dashboard",
                                    "Emergency ambulance overview and current mission status."
                            )
                    ));

        hospitalScene =
                new Scene(
                        root,
                        1553,
                        820
                );

        hospitalStage.setScene(
                hospitalScene
        );

        hospitalStage.setTitle(
                "LifeLink - Trip History Audit Log"
        );

        hospitalStage.setMaximized(
                true
        );

        hospitalStage.show();
    }


    // =========================================================
    // SIDEBAR
    // =========================================================

    private VBox createSidebar(
            BorderPane root
    ) {

        VBox sidebar =
                new VBox();

        sidebar.setPrefWidth(300);
        sidebar.setMinWidth(300);
        sidebar.setMaxWidth(300);

        sidebar.setPadding(
                new Insets(
                        28,
                        18,
                        20,
                        18
                )
        );

        sidebar.setStyle(
                "-fx-background-color: " +
                        WHITE + ";" +
                        "-fx-border-color: " +
                        BORDER + ";" +
                        "-fx-border-width: 0 1px 0 0;"
        );


        // =====================================================
        // BRAND
        // =====================================================

        VBox brand =
                new VBox(4);

        brand.setPadding(
                new Insets(
                        0,
                        16,
                        38,
                        16
                )
        );


        Label lifeLink =
                new Label(
                        "LifeLink"
                );

        lifeLink.setStyle(
                "-fx-font-size: 34px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " +
                        BLUE + ";"
        );


        Label ambulanceName =
                new Label(
                        "Ambulance Alpha-1"
                );

        ambulanceName.setStyle(
                "-fx-font-size: 15px;" +
                        "-fx-text-fill: " +
                        SECONDARY_TEXT + ";"
        );


        brand.getChildren().addAll(
                lifeLink,
                ambulanceName
        );


        // =====================================================
        // NAVIGATION
        // =====================================================

        VBox navigation =
                new VBox(7);


        Button dashboardButton =
                createNavigationButton(
                        "▦",
                        "Dashboard",
                        false
                );


        Button emergencyButton =
                createNavigationButton(
                        "✱",
                        "Emergency Requests",
                        false
                );


        // Button recommendationButton =
        //         createNavigationButton(
        //                 "✦",
        //                 "AI Recommendation",
        //                 false
        //         );


        // Button navigationButton =
        //         createNavigationButton(
        //                 "◉",
        //                 "Navigation",
        //                 false
        //         );


        // Button patientDetailsButton =
        //         createNavigationButton(
        //                 "♙",
        //                 "Patient Details",
        //                 false
        //         );


        // Button policeButton =
        //         createNavigationButton(
        //                 "♢",
        //                 "Police Clearance",
        //                 false
        //         );


        // Button nurseButton =
        //         createNavigationButton(
        //                 "⊞",
        //                 "Nurse Updates",
        //                 false
        //         );


        Button notificationButton =
                createNavigationButton(
                        "♧",
                        "Notifications",
                        false
                );


        Button tripHistoryButton =
                createNavigationButton(
                        "♧",
                        "Trip History",
                        true
                );


        Button statisticsButton =
                createNavigationButton(
                        "▥",
                        "Statistics",
                        false
                );


        Button settingsButton =
                createNavigationButton(
                        "⚙",
                        "Settings",
                        false
                );


        navigation.getChildren().addAll(
                dashboardButton,
                emergencyButton,
                // recommendationButton,
                // navigationButton,
                // patientDetailsButton,
                // policeButton,
                // nurseButton,
                notificationButton,
                tripHistoryButton,
                statisticsButton,
                settingsButton
        );


        // =====================================================
        // NAVIGATION ACTIONS
        // =====================================================

        dashboardButton.setOnAction(event -> {

            setActiveNavigation(
                    navigation,
                    dashboardButton
            );

            root.setCenter(
                    createScrollPane(
                            createSimplePage(
                                    "Dashboard",
                                    "Emergency ambulance overview and current mission status."
                            )
                    )
            );
        });


        emergencyButton.setOnAction(event -> {

            setActiveNavigation(
                    navigation,
                    emergencyButton
            );

            root.setCenter(
                    createScrollPane(
                            createSimplePage(
                                    "Emergency Requests",
                                    "View and manage incoming emergency transport requests."
                            )
                    )
            );
        });


        notificationButton.setOnAction(event -> {

            setActiveNavigation(
                    navigation,
                    notificationButton
            );

            root.setCenter(
                    createScrollPane(
                            createSimplePage(
                                    "Notifications",
                                    "Emergency notifications and system alerts."
                            )
                    )
            );
        });


        tripHistoryButton.setOnAction(event -> {

            setActiveNavigation(
                    navigation,
                    tripHistoryButton
            );

            DriverTripHistory driverTripHistory = new DriverTripHistory();
            root.setCenter(
                driverTripHistory.getDriverTripsPage()
                    // createScrollPane(
                            // createTripHistoryBody()
                    // )
            );
        });


        statisticsButton.setOnAction(event -> {

            setActiveNavigation(
                    navigation,
                    statisticsButton
            );

            root.setCenter(
                    createScrollPane(
                            createSimplePage(
                                    "Statistics",
                                    "Ambulance performance and emergency transport analytics."
                            )
                    )
            );
        });


        settingsButton.setOnAction(event -> {

            setActiveNavigation(
                    navigation,
                    settingsButton
            );

            DriverSetting driversettings = new DriverSetting();
            root.setCenter(
                //     createScrollPane(
                //             createSimplePage(
                //                     "Settings",
                //                     "Manage ambulance and application settings."
                //             )
                        driversettings.getAppSettingsPage()
                //     )
            );
        });


        // =====================================================
        // SPACER
        // =====================================================

        Region spacer =
                new Region();

        VBox.setVgrow(
                spacer,
                Priority.ALWAYS
        );


        // =====================================================
        // LOGOUT
        // =====================================================

        Region divider =
                createSidebarDivider();


        Button logoutButton =
                createLogoutButton();


        logoutButton.setOnAction(event -> {

            try {

                Welcome welcome =
                        new Welcome();

                welcome.start(
                        hospitalStage
                );

            } catch (Exception e) {

                e.printStackTrace();
            }
        });


        sidebar.getChildren().addAll(
                brand,
                navigation,
                spacer,
                divider,
                logoutButton
        );


        return sidebar;
    }


    // =========================================================
    // NAVIGATION BUTTON
    // =========================================================

    private Button createNavigationButton(
            String icon,
            String text,
            boolean active
    ) {

        Button button =
                new Button();


        HBox content =
                new HBox(13);

        content.setAlignment(
                Pos.CENTER_LEFT
        );


        Label iconLabel =
                new Label(icon);

        iconLabel.setMinWidth(22);

        iconLabel.setAlignment(
                Pos.CENTER
        );


        Label textLabel =
                new Label(text);


        content.getChildren().addAll(
                iconLabel,
                textLabel
        );


        button.setGraphic(
                content
        );

        button.setText("");

        button.setMaxWidth(
                Double.MAX_VALUE
        );

        button.setMinHeight(
                50
        );

        button.setPrefHeight(
                50
        );

        button.setAlignment(
                Pos.CENTER_LEFT
        );

        button.setPadding(
                new Insets(
                        0,
                        14,
                        0,
                        14
                )
        );


        if (active) {

            applyActiveStyle(
                    button,
                    iconLabel,
                    textLabel
            );

        } else {

            applyInactiveStyle(
                    button,
                    iconLabel,
                    textLabel
            );
        }


        button.setOnMouseEntered(event -> {

            if (!button
                    .getStyleClass()
                    .contains("active-nav")) {

                applyHoverStyle(
                        button,
                        iconLabel,
                        textLabel
                );
            }
        });


        button.setOnMouseExited(event -> {

            if (!button
                    .getStyleClass()
                    .contains("active-nav")) {

                applyInactiveStyle(
                        button,
                        iconLabel,
                        textLabel
                );
            }
        });


        return button;
    }


    // =========================================================
    // ACTIVE STYLE
    // =========================================================

    private void applyActiveStyle(
            Button button,
            Label icon,
            Label text
    ) {

        if (!button
                .getStyleClass()
                .contains("active-nav")) {

            button.getStyleClass()
                    .add("active-nav");
        }


        button.setStyle(
                "-fx-background-color: " +
                        ACTIVE_BLUE + ";" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: " +
                        BLUE + ";" +
                        "-fx-border-width: 0 0 0 4px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-cursor: hand;"
        );


        icon.setStyle(
                "-fx-font-size: 19px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " +
                        BLUE + ";"
        );


        text.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " +
                        SECONDARY_TEXT + ";"
        );
    }


    // =========================================================
    // INACTIVE STYLE
    // =========================================================

    private void applyInactiveStyle(
            Button button,
            Label icon,
            Label text
    ) {

        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: transparent;" +
                        "-fx-cursor: hand;"
        );


        icon.setStyle(
                "-fx-font-size: 19px;" +
                        "-fx-text-fill: " +
                        SECONDARY_TEXT + ";"
        );


        text.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: normal;" +
                        "-fx-text-fill: " +
                        SECONDARY_TEXT + ";"
        );
    }


    // =========================================================
    // HOVER STYLE
    // =========================================================

    private void applyHoverStyle(
            Button button,
            Label icon,
            Label text
    ) {

        button.setStyle(
                "-fx-background-color: #F1F5FA;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-cursor: hand;"
        );


        icon.setStyle(
                "-fx-font-size: 19px;" +
                        "-fx-text-fill: " +
                        BLUE + ";"
        );


        text.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " +
                        TEXT + ";"
        );
    }


    // =========================================================
    // SET ACTIVE NAVIGATION
    // =========================================================

    private void setActiveNavigation(
            VBox navigation,
            Button selectedButton
    ) {

        for (Node node :
                navigation.getChildren()) {

            if (node instanceof Button button) {

                if (button.getGraphic()
                        instanceof HBox content) {

                    Label icon =
                            (Label) content
                                    .getChildren()
                                    .get(0);

                    Label text =
                            (Label) content
                                    .getChildren()
                                    .get(1);


                    button.getStyleClass()
                            .remove("active-nav");


                    if (button ==
                            selectedButton) {

                        applyActiveStyle(
                                button,
                                icon,
                                text
                        );

                    } else {

                        applyInactiveStyle(
                                button,
                                icon,
                                text
                        );
                    }
                }
            }
        }
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    private Button createLogoutButton() {

        Button logout =
                new Button();


        HBox content =
                new HBox(13);

        content.setAlignment(
                Pos.CENTER_LEFT
        );


        Label icon =
                new Label("↪");

        icon.setMinWidth(22);

        icon.setAlignment(
                Pos.CENTER
        );


        Label text =
                new Label("Logout");


        icon.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-text-fill: " +
                        SECONDARY_TEXT + ";"
        );


        text.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: " +
                        SECONDARY_TEXT + ";"
        );


        content.getChildren().addAll(
                icon,
                text
        );


        logout.setGraphic(
                content
        );

        logout.setText("");

        logout.setMaxWidth(
                Double.MAX_VALUE
        );

        logout.setMinHeight(
                48
        );

        logout.setPrefHeight(
                48
        );

        logout.setAlignment(
                Pos.CENTER_LEFT
        );

        logout.setPadding(
                new Insets(
                        0,
                        14,
                        0,
                        14
                )
        );


        logout.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-cursor: hand;"
        );


        logout.setOnMouseEntered(event -> {

            logout.setStyle(
                    "-fx-background-color: #F5F7FA;" +
                            "-fx-background-radius: 10px;" +
                            "-fx-cursor: hand;"
            );
        });


        logout.setOnMouseExited(event -> {

            logout.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-background-radius: 10px;" +
                            "-fx-cursor: hand;"
            );
        });


        return logout;
    }


    // =========================================================
    // SIDEBAR DIVIDER
    // =========================================================

    private Region createSidebarDivider() {

        Region divider =
                new Region();

        divider.setPrefHeight(1);

        divider.setMaxWidth(
                Double.MAX_VALUE
        );

        divider.setStyle(
                "-fx-background-color: " +
                        BORDER + ";"
        );

        // divider.setMargin(
        //         null
        // );

        return divider;
    }


    // =========================================================
    // TOP HEADER
    // =========================================================

    // private HBox createTopHeader() {

        // HBox header =
        //         new HBox(20);

        // header.setPrefHeight(
        //         68
        // );

        // header.setMinHeight(
        //         68
        // );

        // header.setAlignment(
        //         Pos.CENTER_RIGHT
        // );

        // header.setPadding(
        //         new Insets(
        //                 0,
        //                 28,
        //                 0,
        //                 28
        //         )
        // );

        // header.setStyle(
        //         "-fx-background-color: " +
        //                 HEADER_BACKGROUND + ";" +
        //                 "-fx-border-color: " +
        //                 BORDER + ";" +
        //                 "-fx-border-width: 0 0 1px 0;"
        // );


        // // =====================================================
        // // GPS ACTIVE
        // // =====================================================

        // Label gpsIcon =
        //         new Label("◎");


        // Label gpsText =
        //         new Label(
        //                 "GPS ACTIVE"
        //         );


        // gpsIcon.setStyle(
        //         "-fx-font-size: 20px;" +
        //                 "-fx-font-weight: bold;" +
        //                 "-fx-text-fill: " +
        //                 BLUE + ";"
        // );


        // gpsText.setStyle(
        //         "-fx-font-size: 13px;" +
        //                 "-fx-font-weight: bold;" +
        //                 "-fx-text-fill: " +
        //                 BLUE + ";"
        // );


        // HBox gpsBox =
        //         new HBox(
        //                 7,
        //                 gpsIcon,
        //                 gpsText
        //         );

        // gpsBox.setAlignment(
        //         Pos.CENTER
        // );


        // // =====================================================
        // // NETWORK
        // // =====================================================

        // Label networkIcon =
        //         new Label("◢");


        // Label networkText =
        //         new Label(
        //                 "NETWORK"
        //         );


        // networkIcon.setStyle(
        //         "-fx-font-size: 18px;" +
        //                 "-fx-text-fill: #667085;"
        // );


        // networkText.setStyle(
        //         "-fx-font-size: 13px;" +
        //                 "-fx-font-weight: bold;" +
        //                 "-fx-text-fill: #667085;"
        // );


        // HBox networkBox =
        //         new HBox(
        //                 7,
        //                 networkIcon,
        //                 networkText
        //         );

        // networkBox.setAlignment(
        //         Pos.CENTER
        // );


        // // =====================================================
        // // ACTIVE EMERGENCY
        // // =====================================================

        // Label emergencyStatus =
        //         new Label(
        //                 "ACTIVE EMERGENCY"
        //         );


        // emergencyStatus.setPadding(
        //         new Insets(
        //                 6,
        //                 14,
        //                 6,
        //                 14
        //         )
        // );


        // emergencyStatus.setStyle(
        //         "-fx-background-color: " +
        //                 RED_BACKGROUND + ";" +
        //                 "-fx-text-fill: " +
        //                 RED + ";" +
        //                 "-fx-font-size: 12px;" +
        //                 "-fx-font-weight: bold;" +
        //                 "-fx-background-radius: 18px;" +
        //                 "-fx-border-color: #F3B5B1;" +
        //                 "-fx-border-radius: 18px;"
        // );


        // // =====================================================
        // // ALPHA STATUS
        // // =====================================================

        // Label alphaStatus =
        //         new Label(
        //                 "Alpha-1 Status"
        //         );


        // alphaStatus.setPadding(
        //         new Insets(
        //                 6,
        //                 14,
        //                 6,
        //                 14
        //         )
        // );


        // alphaStatus.setStyle(
        //         "-fx-background-color: #EFF1F8;" +
        //                 "-fx-text-fill: " +
        //                 TEXT + ";" +
        //                 "-fx-font-size: 12px;" +
        //                 "-fx-font-weight: bold;" +
        //                 "-fx-background-radius: 18px;" +
        //                 "-fx-border-color: #C8CEDD;" +
        //                 "-fx-border-radius: 18px;"
        // );


        // // =====================================================
        // // HEADER DIVIDER
        // // =====================================================

        // Region divider =
        //         new Region();

        // divider.setPrefWidth(1);
        // divider.setPrefHeight(42);

        // divider.setStyle(
        //         "-fx-background-color: " +
        //                 BORDER + ";"
        // );


        // // =====================================================
        // // DRIVER PROFILE
        // // =====================================================

        // VBox driverInfo =
        //         new VBox(2);

        // driverInfo.setAlignment(
        //         Pos.CENTER_RIGHT
        // );


        // Label driverName =
        //         new Label(
        //                 "Marcus Thorne"
        //         );


        // driverName.setStyle(
        //         "-fx-font-size: 14px;" +
        //                 "-fx-font-weight: bold;" +
        //                 "-fx-text-fill: " +
        //                 TEXT + ";"
        // );


        // Label driverId =
        //         new Label(
        //                 "Driver ID: DT-894"
        //         );


        // driverId.setStyle(
        //         "-fx-font-size: 12px;" +
        //                 "-fx-text-fill: " +
        //                 SECONDARY_TEXT + ";"
        // );


        // driverInfo.getChildren().addAll(
        //         driverName,
        //         driverId
        // );


        // =====================================================
        // AVATAR
        // =====================================================

    //     StackPane avatar =
    //             new StackPane();

    //     avatar.setPrefSize(
    //             42,
    //             42
    //     );

    //     avatar.setMinSize(
    //             42,
    //             42
    //     );

    //     avatar.setMaxSize(
    //             42,
    //             42
    //     );


    //     avatar.setStyle(
    //             "-fx-background-color: #DDE7F2;" +
    //                     "-fx-background-radius: 50%;" +
    //                     "-fx-border-color: #B9C8D9;" +
    //                     "-fx-border-radius: 50%;"
    //     );


    //     Label avatarText =
    //             new Label(
    //                     "MT"
    //             );


    //     avatarText.setStyle(
    //             "-fx-font-size: 11px;" +
    //                     "-fx-font-weight: bold;" +
    //                     "-fx-text-fill: " +
    //                     BLUE + ";"
    //     );


    //     avatar.getChildren().add(
    //             avatarText
    //     );


    //     header.getChildren().addAll(
    //             gpsBox,
    //             networkBox,
    //             emergencyStatus,
    //             alphaStatus,
    //             divider,
    //             driverInfo,
    //             avatar
    //     );


    //     return header;
    // }


    // =========================================================
    // TRIP HISTORY BODY
    // =========================================================

    private VBox createTripHistoryBody() {

        VBox body =
                new VBox(22);

        body.setPadding(
                new Insets(
                        34,
                        38,
                        40,
                        38
                )
        );

        body.setStyle(
                "-fx-background-color: " +
                        BODY_BACKGROUND + ";"
        );


        // =====================================================
        // PAGE HEADER
        // =====================================================

        VBox pageHeader =
                createPageHeader();


        // =====================================================
        // STATISTICS
        // =====================================================

        HBox statistics =
                createStatisticsSection();


        // =====================================================
        // FILTER / EXPORT
        // =====================================================

        VBox filterPanel =
                createFilterPanel();


        // =====================================================
        // TRIP TABLE
        // =====================================================

        VBox tablePanel =
                createTripTablePanel();


        body.getChildren().addAll(
                pageHeader,
                statistics,
                filterPanel,
                tablePanel
        );


        return body;
    }


    // =========================================================
    // PAGE HEADER
    // =========================================================

    private VBox createPageHeader() {

        VBox header =
                new VBox(5);


        Label title =
                new Label(
                        "Trip History Audit Log"
                );


        title.setStyle(
                "-fx-font-size: 34px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " +
                        TEXT + ";"
        );


        Label subtitle =
                new Label(
                        "Review and export previous emergency transport records."
                );


        subtitle.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-text-fill: " +
                        SECONDARY_TEXT + ";"
        );


        header.getChildren().addAll(
                title,
                subtitle
        );


        return header;
    }


    // =========================================================
    // STATISTICS SECTION
    // =========================================================

    private HBox createStatisticsSection() {

        HBox statistics =
                new HBox(18);


        VBox totalTrips =
                createStatisticCard(
                        "TOTAL TRIPS (TODAY)",
                        "14",
                        "▱",
                        BLUE,
                        "#DDE7FF"
                );


        VBox responseTime =
                createStatisticCard(
                        "AVG. RESPONSE TIME",
                        "8.2 min",
                        "◴",
                        TEXT,
                        "#E4E6EF"
                );


        VBox criticalTrips =
                createStatisticCard(
                        "CRITICAL TRANSPORTS",
                        "3",
                        "⚠",
                        RED,
                        "#FFDCD8"
                );


        HBox.setHgrow(
                totalTrips,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                responseTime,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                criticalTrips,
                Priority.ALWAYS
        );


        statistics.getChildren().addAll(
                totalTrips,
                responseTime,
                criticalTrips
        );


        return statistics;
    }


    // =========================================================
    // STATISTIC CARD
    // =========================================================

    private VBox createStatisticCard(
            String title,
            String value,
            String icon,
            String valueColor,
            String iconBackground
    ) {

        VBox card =
                new VBox();


        card.setPrefHeight(
                135
        );

        card.setMinHeight(
                135
        );

        card.setPadding(
                new Insets(
                        22,
                        24,
                        20,
                        24
                )
        );


        card.setStyle(
                "-fx-background-color: " +
                        WHITE + ";" +
                        "-fx-border-color: " +
                        BORDER + ";" +
                        "-fx-border-radius: 13px;" +
                        "-fx-background-radius: 13px;"
        );


        Label titleLabel =
                new Label(
                        title
                );


        titleLabel.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " +
                        SECONDARY_TEXT + ";" +
                        "-fx-letter-spacing: 1px;"
        );


        Label valueLabel =
                new Label(
                        value
                );


        valueLabel.setStyle(
                "-fx-font-size: 52px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " +
                        valueColor + ";"
        );


        StackPane iconCircle =
                new StackPane();


        iconCircle.setPrefSize(
                52,
                52
        );

        iconCircle.setMinSize(
                52,
                52
        );

        iconCircle.setMaxSize(
                52,
                52
        );


        iconCircle.setStyle(
                "-fx-background-color: " +
                        iconBackground + ";" +
                        "-fx-background-radius: 50%;"
        );


        Label iconLabel =
                new Label(
                        icon
                );


        iconLabel.setStyle(
                "-fx-font-size: 23px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " +
                        valueColor + ";"
        );


        iconCircle.getChildren().add(
                iconLabel
        );


        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );


        HBox valueRow =
                new HBox(
                        valueLabel,
                        spacer,
                        iconCircle
                );


        valueRow.setAlignment(
                Pos.CENTER_LEFT
        );


        VBox.setVgrow(
                valueRow,
                Priority.ALWAYS
        );


        card.getChildren().addAll(
                titleLabel,
                valueRow
        );


        return card;
    }


    // =========================================================
    // FILTER PANEL
    // =========================================================

    private VBox createFilterPanel() {

        VBox panel =
                new VBox(14);


        panel.setPadding(
                new Insets(
                        18,
                        18,
                        18,
                        18
                )
        );


        panel.setStyle(
                "-fx-background-color: " +
                        WHITE + ";" +
                        "-fx-border-color: " +
                        BORDER + ";" +
                        "-fx-border-radius: 13px;" +
                        "-fx-background-radius: 13px;"
        );


        // =====================================================
        // SEARCH FIELD
        // =====================================================

        TextField searchField =
                new TextField();


        searchField.setPromptText(
                "Search by ID, Patient, or Hospital..."
        );


        searchField.setPrefWidth(
                480
        );


        searchField.setMaxWidth(
                480
        );


        searchField.setPrefHeight(
                42
        );


        searchField.setStyle(
                "-fx-background-color: #FBFBFF;" +
                        "-fx-border-color: #BBC5D8;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 0 14px 0 14px;"
        );


        // =====================================================
        // SEARCH ICON
        // =====================================================

        Label searchIcon =
                new Label(
                        "⌕"
                );


        searchIcon.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-text-fill: " +
                        SECONDARY_TEXT + ";"
        );


        StackPane searchContainer =
                new StackPane(
                        searchField
                );


        searchContainer.setAlignment(
                Pos.CENTER_LEFT
        );


        // =====================================================
        // PERIOD BUTTONS
        // =====================================================

        Button todayButton =
                createPeriodButton(
                        "Today",
                        true
                );


        Button weekButton =
                createPeriodButton(
                        "This Week",
                        false
                );


        Button monthButton =
                createPeriodButton(
                        "This Month",
                        false
                );


        HBox periodBox =
                new HBox(
                        0,
                        todayButton,
                        weekButton,
                        monthButton
                );


        periodBox.setPadding(
                new Insets(3)
        );


        periodBox.setStyle(
                "-fx-background-color: #E9ECF6;" +
                        "-fx-background-radius: 9px;" +
                        "-fx-border-color: #C6CCDD;" +
                        "-fx-border-radius: 9px;"
        );


        // =====================================================
        // VERTICAL DIVIDER
        // =====================================================

        Region verticalDivider =
                new Region();


        verticalDivider.setPrefWidth(
                1
        );

        verticalDivider.setPrefHeight(
                34
        );


        verticalDivider.setStyle(
                "-fx-background-color: #C9CFDC;"
        );


        // =====================================================
        // EXPORT PDF
        // =====================================================

        Button exportPdf =
                createExportButton(
                        "▣  Export PDF"
                );


        exportPdf.setOnAction(event -> {

            showInformation(
                    "Export PDF",
                    "PDF export is ready to be connected to your report generator."
            );
        });


        // =====================================================
        // EXPORT EXCEL
        // =====================================================

        Button exportExcel =
                createExportButton(
                        "▤  Export Excel"
                );


        exportExcel.setOnAction(event -> {

            showInformation(
                    "Export Excel",
                    "Excel export is ready to be connected to your report generator."
            );
        });


        // =====================================================
        // SECOND ROW
        // =====================================================

        HBox controlRow =
                new HBox(
                        14,
                        periodBox,
                        verticalDivider,
                        exportPdf,
                        exportExcel
                );


        controlRow.setAlignment(
                Pos.CENTER_LEFT
        );


        panel.getChildren().addAll(
                searchContainer,
                controlRow
        );


        return panel;
    }


    // =========================================================
    // PERIOD BUTTON
    // =========================================================

    private Button createPeriodButton(
            String text,
            boolean active
    ) {

        Button button =
                new Button(text);


        button.setPrefHeight(
                34
        );


        button.setMinWidth(
                text.equals("Today")
                        ? 75
                        : 100
        );


        button.setStyle(
                active
                        ? "-fx-background-color: #FFFFFF;" +
                          "-fx-background-radius: 7px;" +
                          "-fx-text-fill: " + TEXT + ";" +
                          "-fx-font-size: 12px;" +
                          "-fx-font-weight: bold;" +
                          "-fx-cursor: hand;"
                        : "-fx-background-color: transparent;" +
                          "-fx-background-radius: 7px;" +
                          "-fx-text-fill: " + TEXT + ";" +
                          "-fx-font-size: 12px;" +
                          "-fx-font-weight: bold;" +
                          "-fx-cursor: hand;"
        );


        button.setOnMouseEntered(event -> {

            if (!active) {

                button.setStyle(
                        "-fx-background-color: #F7F8FC;" +
                                "-fx-background-radius: 7px;" +
                                "-fx-text-fill: " +
                                BLUE + ";" +
                                "-fx-font-size: 12px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-cursor: hand;"
                );
            }
        });


        button.setOnMouseExited(event -> {

            if (!active) {

                button.setStyle(
                        "-fx-background-color: transparent;" +
                                "-fx-background-radius: 7px;" +
                                "-fx-text-fill: " +
                                TEXT + ";" +
                                "-fx-font-size: 12px;" +
                                "-fx-font-weight: bold;" +
                                "-fx-cursor: hand;"
                );
            }
        });


        return button;
    }


    // =========================================================
    // EXPORT BUTTON
    // =========================================================

    private Button createExportButton(
            String text
    ) {

        Button button =
                new Button(text);


        button.setPrefHeight(
                38
        );


        button.setPadding(
                new Insets(
                        0,
                        17,
                        0,
                        17
                )
        );


        button.setStyle(
                "-fx-background-color: #FFFFFF;" +
                        "-fx-border-color: #BEC7D8;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-text-fill: " +
                        TEXT + ";" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"
        );


        button.setOnMouseEntered(event -> {

            button.setStyle(
                    "-fx-background-color: #F4F7FC;" +
                            "-fx-border-color: " +
                            BLUE + ";" +
                            "-fx-border-radius: 8px;" +
                            "-fx-background-radius: 8px;" +
                            "-fx-text-fill: " +
                            BLUE + ";" +
                            "-fx-font-size: 12px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-cursor: hand;"
            );
        });


        button.setOnMouseExited(event -> {

            button.setStyle(
                    "-fx-background-color: #FFFFFF;" +
                            "-fx-border-color: #BEC7D8;" +
                            "-fx-border-radius: 8px;" +
                            "-fx-background-radius: 8px;" +
                            "-fx-text-fill: " +
                            TEXT + ";" +
                            "-fx-font-size: 12px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-cursor: hand;"
            );
        });


        return button;
    }


    // =========================================================
    // TRIP TABLE PANEL
    // =========================================================

    private VBox createTripTablePanel() {

        VBox panel =
                new VBox();


        panel.setStyle(
                "-fx-background-color: " +
                        WHITE + ";" +
                        "-fx-border-color: " +
                        BORDER + ";" +
                        "-fx-border-radius: 13px;" +
                        "-fx-background-radius: 13px;"
        );


        TableView<TripRecord> table =
                createTripTable();


        VBox.setVgrow(
                table,
                Priority.ALWAYS
        );


        // =====================================================
        // PAGINATION FOOTER
        // =====================================================

        HBox footer =
                createPaginationFooter();


        panel.getChildren().addAll(
                table,
                footer
        );


        return panel;
    }


    // =========================================================
    // CREATE TRIP TABLE
    // =========================================================

    private TableView<TripRecord> createTripTable() {

        TableView<TripRecord> table =
                new TableView<>();


        table.setItems(
                tripRecords
        );


        table.setPrefHeight(
                430
        );


        table.setMinHeight(
                390
        );


        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );


        table.setPlaceholder(
                new Label("No trip records found.")
        );


        table.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: transparent;" +
                        "-fx-font-size: 13px;"
        );


        // =====================================================
        // TRIP ID
        // =====================================================

        TableColumn<TripRecord, String> tripIdColumn =
                new TableColumn<>(
                        "TRIP ID"
                );


        tripIdColumn.setCellValueFactory(
                data ->
                        data.getValue()
                                .tripIdProperty()
        );


        tripIdColumn.setPrefWidth(
                110
        );


        tripIdColumn.setCellFactory(
                column ->
                        new TableCell<>() {

                            @Override
                            protected void updateItem(
                                    String item,
                                    boolean empty
                            ) {

                                super.updateItem(
                                        item,
                                        empty
                                );


                                if (empty ||
                                        item == null) {

                                    setText(null);

                                } else {

                                    setText(
                                            item
                                                    .replace(
                                                            "-",
                                                            "-\n"
                                                    )
                                    );

                                    setStyle(
                                            "-fx-font-weight: bold;" +
                                                    "-fx-text-fill: " +
                                                    TEXT + ";" +
                                                    "-fx-alignment: CENTER_LEFT;"
                                    );
                                }
                            }
                        }
        );


        // =====================================================
        // DATE / TIME
        // =====================================================

        TableColumn<TripRecord, String> dateColumn =
                new TableColumn<>(
                        "DATE &\nTIME"
                );


        dateColumn.setCellValueFactory(
                data ->
                        data.getValue()
                                .dateTimeProperty()
        );


        dateColumn.setPrefWidth(
                120
        );


        dateColumn.setCellFactory(
                column ->
                        new TableCell<>() {

                            @Override
                            protected void updateItem(
                                    String item,
                                    boolean empty
                            ) {

                                super.updateItem(
                                        item,
                                        empty
                                );


                                if (empty ||
                                        item == null) {

                                    setText(null);

                                } else {

                                    setText(item);

                                    setStyle(
                                            "-fx-text-fill: " +
                                                    TEXT + ";" +
                                                    "-fx-alignment: CENTER_LEFT;"
                                    );
                                }
                            }
                        }
        );


        // =====================================================
        // PATIENT
        // =====================================================

        TableColumn<TripRecord, String> patientColumn =
                new TableColumn<>(
                        "PATIENT"
                );


        patientColumn.setCellValueFactory(
                data ->
                        data.getValue()
                                .patientProperty()
        );


        patientColumn.setPrefWidth(
                175
        );


        patientColumn.setCellFactory(
                column ->
                        createWrappedCell()
        );


        // =====================================================
        // HOSPITAL DESTINATION
        // =====================================================

        TableColumn<TripRecord, String> hospitalColumn =
                new TableColumn<>(
                        "HOSPITAL\nDESTINATION"
                );


        hospitalColumn.setCellValueFactory(
                data ->
                        data.getValue()
                                .hospitalDestinationProperty()
        );


        hospitalColumn.setPrefWidth(
                190
        );


        hospitalColumn.setCellFactory(
                column ->
                        createHospitalCell()
        );


        // =====================================================
        // DISTANCE
        // =====================================================

        TableColumn<TripRecord, String> distanceColumn =
                new TableColumn<>(
                        "DISTANCE"
                );


        distanceColumn.setCellValueFactory(
                data ->
                        data.getValue()
                                .distanceProperty()
        );


        distanceColumn.setPrefWidth(
                105
        );


        // =====================================================
        // TOTAL TIME
        // =====================================================

        TableColumn<TripRecord, String> totalTimeColumn =
                new TableColumn<>(
                        "TOTAL\nTIME"
                );


        totalTimeColumn.setCellValueFactory(
                data ->
                        data.getValue()
                                .totalTimeProperty()
        );


        totalTimeColumn.setPrefWidth(
                110
        );


        // =====================================================
        // STATUS
        // =====================================================

        TableColumn<TripRecord, String> statusColumn =
                new TableColumn<>(
                        "STATUS"
                );


        statusColumn.setCellValueFactory(
                data ->
                        data.getValue()
                                .statusProperty()
        );


        statusColumn.setPrefWidth(
                155
        );


        statusColumn.setCellFactory(
                column ->
                        new TableCell<>() {

                            @Override
                            protected void updateItem(
                                    String item,
                                    boolean empty
                            ) {

                                super.updateItem(
                                        item,
                                        empty
                                );


                                if (empty ||
                                        item == null) {

                                    setGraphic(null);

                                } else {

                                    TripRecord record =
                                            getTableView()
                                                    .getItems()
                                                    .get(getIndex());


                                    Label status =
                                            new Label(
                                                    item
                                            );


                                    status.setPadding(
                                            new Insets(
                                                    6,
                                                    13,
                                                    6,
                                                    13
                                            )
                                    );


                                    if (record.isCritical()) {

                                        status.setStyle(
                                                "-fx-background-color: #FFF4F3;" +
                                                        "-fx-border-color: #F2B8B5;" +
                                                        "-fx-border-radius: 18px;" +
                                                        "-fx-background-radius: 18px;" +
                                                        "-fx-text-fill: " +
                                                        RED + ";" +
                                                        "-fx-font-size: 11px;" +
                                                        "-fx-font-weight: bold;"
                                        );

                                    } else {

                                        status.setStyle(
                                                "-fx-background-color: " +
                                                        GREEN_BACKGROUND + ";" +
                                                        "-fx-border-color: #A8E8BF;" +
                                                        "-fx-border-radius: 18px;" +
                                                        "-fx-background-radius: 18px;" +
                                                        "-fx-text-fill: " +
                                                        GREEN + ";" +
                                                        "-fx-font-size: 11px;" +
                                                        "-fx-font-weight: bold;"
                                        );
                                    }


                                    setGraphic(status);

                                    setAlignment(
                                            Pos.CENTER_LEFT
                                    );
                                }
                            }
                        }
        );


        // =====================================================
        // ACTIONS
        // =====================================================

        TableColumn<TripRecord, String> actionsColumn =
                new TableColumn<>(
                        "ACTIONS"
                );


        actionsColumn.setPrefWidth(
                85
        );


        actionsColumn.setCellFactory(
                column ->
                        new TableCell<>() {

                            private final Button viewButton =
                                    new Button("◉");


                            {

                                viewButton.setPrefSize(
                                        38,
                                        38
                                );


                                viewButton.setStyle(
                                        "-fx-background-color: transparent;" +
                                                "-fx-text-fill: #344054;" +
                                                "-fx-font-size: 17px;" +
                                                "-fx-cursor: hand;"
                                );


                                viewButton.setOnAction(
                                        event -> {

                                            TripRecord record =
                                                    getTableView()
                                                            .getItems()
                                                            .get(getIndex());


                                            showInformation(
                                                    "Trip Details",
                                                    "Trip ID: " +
                                                            record.getTripId() +
                                                            "\n\nPatient: " +
                                                            record.getPatient() +
                                                            "\n\nHospital: " +
                                                            record.getHospitalDestination() +
                                                            "\n\nDistance: " +
                                                            record.getDistance() +
                                                            "\n\nTotal Time: " +
                                                            record.getTotalTime() +
                                                            "\n\nStatus: " +
                                                            record.getStatus()
                                            );
                                        }
                                );
                            }


                            @Override
                            protected void updateItem(
                                    String item,
                                    boolean empty
                            ) {

                                super.updateItem(
                                        item,
                                        empty
                                );


                                if (empty) {

                                    setGraphic(null);

                                } else {

                                    setGraphic(
                                            viewButton
                                    );

                                    setAlignment(
                                            Pos.CENTER
                                    );
                                }
                            }
                        }
        );


        table.getColumns().addAll(
                tripIdColumn,
                dateColumn,
                patientColumn,
                hospitalColumn,
                distanceColumn,
                totalTimeColumn,
                statusColumn,
                actionsColumn
        );


        // =====================================================
        // HEADER STYLE
        // =====================================================

        table.skinProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (newValue != null) {

                        Node header =
                                table.lookup(
                                        ".column-header-background"
                                );


                        if (header != null) {

                            header.setStyle(
                                    "-fx-background-color: #F1F4F9;"
                            );
                        }
                    }
                }
        );


        return table;
    }


    // =========================================================
    // WRAPPED TABLE CELL
    // =========================================================

    private TableCell<TripRecord, String>
    createWrappedCell() {

        return new TableCell<>() {

            @Override
            protected void updateItem(
                    String item,
                    boolean empty
            ) {

                super.updateItem(
                        item,
                        empty
                );


                if (empty ||
                        item == null) {

                    setText(null);

                } else {

                    setText(item);

                    setStyle(
                            "-fx-text-fill: " +
                                    TEXT + ";" +
                                    "-fx-alignment: CENTER_LEFT;"
                    );
                }
            }
        };
    }


    // =========================================================
    // HOSPITAL CELL
    // =========================================================

    private TableCell<TripRecord, String>
    createHospitalCell() {

        return new TableCell<>() {

            @Override
            protected void updateItem(
                    String item,
                    boolean empty
            ) {

                super.updateItem(
                        item,
                        empty
                );


                if (empty ||
                        item == null) {

                    setText(null);

                } else {

                    setText(item);

                    setStyle(
                            "-fx-text-fill: " +
                                    TEXT + ";" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-alignment: CENTER_LEFT;"
                    );
                }
            }
        };
    }


    // =========================================================
    // PAGINATION FOOTER
    // =========================================================

    private HBox createPaginationFooter() {

        HBox footer =
                new HBox();


        footer.setPrefHeight(
                68
        );


        footer.setPadding(
                new Insets(
                        0,
                        18,
                        0,
                        18
                )
        );


        footer.setAlignment(
                Pos.CENTER_LEFT
        );


        footer.setStyle(
                "-fx-background-color: #FBF9FF;" +
                        "-fx-border-color: " +
                        BORDER + ";" +
                        "-fx-border-width: 1px 0 0 0;" +
                        "-fx-background-radius: 0 0 13px 13px;"
        );


        Label showing =
                new Label(
                        "Showing 1 to 5 of 14 trips"
                );


        showing.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: " +
                        SECONDARY_TEXT + ";"
        );


        Region spacer =
                new Region();


        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );


        Button previous =
                createPageButton(
                        "‹",
                        false
                );


        Button pageOne =
                createPageButton(
                        "1",
                        true
                );


        Button pageTwo =
                createPageButton(
                        "2",
                        false
                );


        Button pageThree =
                createPageButton(
                        "3",
                        false
                );


        Button next =
                createPageButton(
                        "›",
                        false
                );


        HBox pages =
                new HBox(
                        8,
                        previous,
                        pageOne,
                        pageTwo,
                        pageThree,
                        next
                );


        pages.setAlignment(
                Pos.CENTER
        );


        footer.getChildren().addAll(
                showing,
                spacer,
                pages
        );


        return footer;
    }


    // =========================================================
    // PAGE BUTTON
    // =========================================================

    private Button createPageButton(
            String text,
            boolean active
    ) {

        Button button =
                new Button(text);


        button.setPrefSize(
                38,
                38
        );


        if (active) {

            button.setStyle(
                    "-fx-background-color: " +
                            BLUE + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 13px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 7px;" +
                            "-fx-cursor: hand;"
            );

        } else {

            button.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-text-fill: " +
                            TEXT + ";" +
                            "-fx-font-size: 13px;" +
                            "-fx-background-radius: 7px;" +
                            "-fx-cursor: hand;"
            );


            button.setOnMouseEntered(
                    event -> {

                        button.setStyle(
                                "-fx-background-color: #EDF3FF;" +
                                        "-fx-text-fill: " +
                                        BLUE + ";" +
                                        "-fx-font-size: 13px;" +
                                        "-fx-background-radius: 7px;" +
                                        "-fx-cursor: hand;"
                        );
                    }
            );


            button.setOnMouseExited(
                    event -> {

                        button.setStyle(
                                "-fx-background-color: transparent;" +
                                        "-fx-text-fill: " +
                                        TEXT + ";" +
                                        "-fx-font-size: 13px;" +
                                        "-fx-background-radius: 7px;" +
                                        "-fx-cursor: hand;"
                        );
                    }
            );
        }


        return button;
    }


    // =========================================================
    // SIMPLE PLACEHOLDER PAGE
    // =========================================================

    private VBox createSimplePage(
            String title,
            String description
    ) {

        VBox page =
                new VBox(10);


        page.setPadding(
                new Insets(
                        40
                )
        );


        page.setStyle(
                "-fx-background-color: " +
                        BODY_BACKGROUND + ";"
        );


        Label titleLabel =
                new Label(title);


        titleLabel.setStyle(
                "-fx-font-size: 34px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " +
                        TEXT + ";"
        );


        Label descriptionLabel =
                new Label(description);


        descriptionLabel.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-text-fill: " +
                        SECONDARY_TEXT + ";"
        );


        page.getChildren().addAll(
                titleLabel,
                descriptionLabel
        );


        return page;
    }


    // =========================================================
    // SCROLL PANE
    // =========================================================

    private ScrollPane createScrollPane(
            VBox content
    ) {

        ScrollPane scrollPane =
                new ScrollPane(
                        content
                );


        scrollPane.setFitToWidth(
                true
        );


        scrollPane.setFitToHeight(
                false
        );


        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );


        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );


        scrollPane.setPannable(
                true
        );


        scrollPane.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background: " +
                        BODY_BACKGROUND + ";"
        );


        return scrollPane;
    }


    // =========================================================
    // INFORMATION ALERT
    // =========================================================

    private void showInformation(
            String title,
            String message
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );


        alert.setTitle(
                title
        );


        alert.setHeaderText(
                null
        );


        alert.setContentText(
                message
        );


        alert.showAndWait();
    }


    // =========================================================
    // TRIP RECORD MODEL
    // =========================================================

    public static class TripRecord {

        private final javafx.beans.property.SimpleStringProperty tripId;
        private final javafx.beans.property.SimpleStringProperty dateTime;
        private final javafx.beans.property.SimpleStringProperty patient;
        private final javafx.beans.property.SimpleStringProperty hospitalDestination;
        private final javafx.beans.property.SimpleStringProperty distance;
        private final javafx.beans.property.SimpleStringProperty totalTime;
        private final javafx.beans.property.SimpleStringProperty status;

        private final boolean critical;


        public TripRecord(
                String tripId,
                String dateTime,
                String patient,
                String hospitalDestination,
                String distance,
                String totalTime,
                String status,
                boolean critical
        ) {

            this.tripId =
                    new javafx.beans.property.SimpleStringProperty(
                            tripId
                    );

            this.dateTime =
                    new javafx.beans.property.SimpleStringProperty(
                            dateTime
                    );

            this.patient =
                    new javafx.beans.property.SimpleStringProperty(
                            patient
                    );

            this.hospitalDestination =
                    new javafx.beans.property.SimpleStringProperty(
                            hospitalDestination
                    );

            this.distance =
                    new javafx.beans.property.SimpleStringProperty(
                            distance
                    );

            this.totalTime =
                    new javafx.beans.property.SimpleStringProperty(
                            totalTime
                    );

            this.status =
                    new javafx.beans.property.SimpleStringProperty(
                            status
                    );

            this.critical = critical;
        }


        public javafx.beans.property.StringProperty
        tripIdProperty() {

            return tripId;
        }


        public javafx.beans.property.StringProperty
        dateTimeProperty() {

            return dateTime;
        }


        public javafx.beans.property.StringProperty
        patientProperty() {

            return patient;
        }


        public javafx.beans.property.StringProperty
        hospitalDestinationProperty() {

            return hospitalDestination;
        }


        public javafx.beans.property.StringProperty
        distanceProperty() {

            return distance;
        }


        public javafx.beans.property.StringProperty
        totalTimeProperty() {

            return totalTime;
        }


        public javafx.beans.property.StringProperty
        statusProperty() {

            return status;
        }


        public String getTripId() {

            return tripId.get();
        }


        public String getDateTime() {

            return dateTime.get();
        }


        public String getPatient() {

            return patient.get();
        }


        public String getHospitalDestination() {

            return hospitalDestination.get();
        }


        public String getDistance() {

            return distance.get();
        }


        public String getTotalTime() {

            return totalTime.get();
        }


        public String getStatus() {

            return status.get();
        }


        public boolean isCritical() {

            return critical;
        }
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        launch(args);
    }
}