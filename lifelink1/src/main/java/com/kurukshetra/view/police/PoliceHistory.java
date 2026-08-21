// package com.kurukshetra.view.police;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.DatePicker;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;

// public class PoliceHistory {

//     private static final String BG_SURFACE = "#faf8ff";
//     private static final String PRIMARY_COLOR = "#006591";
//     private static final String ON_SURFACE = "#131b2e";
//     private static final String ON_SURFACE_VARIANT = "#3e4850";
//     private static final String OUTLINE_VARIANT = "#bec8d2";
//     private static final String CARD_BG = "#ffffff";

//     public VBox getHistoryVBox() {

//         VBox historyPage = new VBox(24);
//         historyPage.setPadding(new Insets(40, 50, 40, 50));
//         historyPage.setStyle("-fx-background-color:" + BG_SURFACE + ";");

//         Text heading = new Text("Ambulance History");
//         heading.setStyle("-fx-font-size:30px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");

//         Text subHeading = new Text("View ambulances that used police facilities");
//         subHeading.setStyle("-fx-font-size:15px; -fx-fill:" + ON_SURFACE_VARIANT + ";");

//         VBox headingBox = new VBox(7, heading, subHeading);

//         HBox filterBox = new HBox(15);
//         filterBox.setAlignment(Pos.CENTER_LEFT);
//         filterBox.setPadding(new Insets(18));
//         filterBox.setStyle("-fx-background-color:" + CARD_BG + "; -fx-background-radius:16px; -fx-border-color:" + OUTLINE_VARIANT + "; -fx-border-radius:16px;");

//         Text dateText = new Text("Select Date");
//         dateText.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");

//         DatePicker datePicker = new DatePicker();
//         datePicker.setPrefWidth(190);
//         datePicker.setStyle("-fx-background-radius:10px;");

//         Button todayButton = new Button("Today");
//         todayButton.setPrefWidth(90);
//         todayButton.setPrefHeight(36);
//         todayButton.setStyle("-fx-background-color:" + PRIMARY_COLOR + "; -fx-text-fill:white; -fx-font-size:13px; -fx-font-weight:bold; -fx-background-radius:10px;");

//         todayButton.setOnAction(event -> datePicker.setValue(java.time.LocalDate.now()));

//         filterBox.getChildren().addAll(dateText, datePicker, todayButton);

//         VBox date1Box = new VBox(14);
//         date1Box.setPadding(new Insets(22));
//         date1Box.setStyle("-fx-background-color:" + CARD_BG + "; -fx-background-radius:18px; -fx-border-color:" + OUTLINE_VARIANT + "; -fx-border-radius:18px;");

//         HBox date1Header = new HBox();

//         Text date1Text = new Text("12 August 2026");
//         date1Text.setStyle("-fx-font-size:20px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");

//         Text count1 = new Text("3 Ambulances");
//         count1.setStyle("-fx-font-size:13px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_COLOR + ";");

//         Region date1Spacer = new Region();
//         HBox.setHgrow(date1Spacer, Priority.ALWAYS);

//         date1Header.getChildren().addAll(date1Text, date1Spacer, count1);

//         HBox ambulance1 = new HBox(20);
//         ambulance1.setAlignment(Pos.CENTER_LEFT);
//         ambulance1.setPadding(new Insets(16));
//         ambulance1.setStyle("-fx-background-color:" + CARD_BG + "; -fx-background-radius:14px; -fx-border-color:" + OUTLINE_VARIANT + "; -fx-border-radius:14px; -fx-border-width:1px;");

//         VBox unit1Box = new VBox(5);
//         Text unit1 = new Text("UNIT A-102");
//         unit1.setStyle("-fx-font-size:15px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");
//         Text driver1 = new Text("Driver: Rahul Patil");
//         driver1.setStyle("-fx-font-size:12px; -fx-fill:" + ON_SURFACE_VARIANT + ";");
//         unit1Box.getChildren().addAll(unit1, driver1);

//         VBox time1Box = new VBox(5);
//         Text arrival1 = new Text("09:42 AM");
//         arrival1.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");
//         Text clearance1 = new Text("Cleared: 09:45 AM");
//         clearance1.setStyle("-fx-font-size:12px; -fx-fill:" + ON_SURFACE_VARIANT + ";");
//         time1Box.getChildren().addAll(arrival1, clearance1);

//         VBox route1Box = new VBox(5);
//         Text route1 = new Text("Shivajinagar → Kothrud");
//         route1.setStyle("-fx-font-size:13px; -fx-fill:" + ON_SURFACE + ";");
//         Text facility1 = new Text("Traffic Clearance");
//         facility1.setStyle("-fx-font-size:12px; -fx-fill:" + ON_SURFACE_VARIANT + ";");
//         route1Box.getChildren().addAll(route1, facility1);

//         VBox hospital1Box = new VBox(5);
//         Text hospital1 = new Text("Ruby Hall Clinic");
//         hospital1.setStyle("-fx-font-size:13px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");
//         Text status1 = new Text("COMPLETED");
//         status1.setStyle("-fx-font-size:11px; -fx-font-weight:bold; -fx-fill:#16803c;");
//         hospital1Box.getChildren().addAll(hospital1, status1);

//         HBox.setHgrow(unit1Box, Priority.ALWAYS);
//         HBox.setHgrow(time1Box, Priority.ALWAYS);
//         HBox.setHgrow(route1Box, Priority.ALWAYS);
//         HBox.setHgrow(hospital1Box, Priority.ALWAYS);

//         ambulance1.getChildren().addAll(unit1Box, time1Box, route1Box, hospital1Box);

//         HBox ambulance2 = new HBox(20);
//         ambulance2.setAlignment(Pos.CENTER_LEFT);
//         ambulance2.setPadding(new Insets(16));
//         ambulance2.setStyle("-fx-background-color:" + CARD_BG + "; -fx-background-radius:14px; -fx-border-color:" + OUTLINE_VARIANT + "; -fx-border-radius:14px; -fx-border-width:1px;");

//         VBox unit2Box = new VBox(5);
//         Text unit2 = new Text("UNIT C-088");
//         unit2.setStyle("-fx-font-size:15px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");
//         Text driver2 = new Text("Driver: Amit Shinde");
//         driver2.setStyle("-fx-font-size:12px; -fx-fill:" + ON_SURFACE_VARIANT + ";");
//         unit2Box.getChildren().addAll(unit2, driver2);

//         VBox time2Box = new VBox(5);
//         Text arrival2 = new Text("10:15 AM");
//         arrival2.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");
//         Text clearance2 = new Text("Cleared: 10:18 AM");
//         clearance2.setStyle("-fx-font-size:12px; -fx-fill:" + ON_SURFACE_VARIANT + ";");
//         time2Box.getChildren().addAll(arrival2, clearance2);

//         VBox route2Box = new VBox(5);
//         Text route2 = new Text("Camp → Shivajinagar");
//         route2.setStyle("-fx-font-size:13px; -fx-fill:" + ON_SURFACE + ";");
//         Text facility2 = new Text("Signal Clearance");
//         facility2.setStyle("-fx-font-size:12px; -fx-fill:" + ON_SURFACE_VARIANT + ";");
//         route2Box.getChildren().addAll(route2, facility2);

//         VBox hospital2Box = new VBox(5);
//         Text hospital2 = new Text("KEM Hospital");
//         hospital2.setStyle("-fx-font-size:13px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");
//         Text status2 = new Text("COMPLETED");
//         status2.setStyle("-fx-font-size:11px; -fx-font-weight:bold; -fx-fill:#16803c;");
//         hospital2Box.getChildren().addAll(hospital2, status2);

//         HBox.setHgrow(unit2Box, Priority.ALWAYS);
//         HBox.setHgrow(time2Box, Priority.ALWAYS);
//         HBox.setHgrow(route2Box, Priority.ALWAYS);
//         HBox.setHgrow(hospital2Box, Priority.ALWAYS);

//         ambulance2.getChildren().addAll(unit2Box, time2Box, route2Box, hospital2Box);

//         HBox ambulance3 = new HBox(20);
//         ambulance3.setAlignment(Pos.CENTER_LEFT);
//         ambulance3.setPadding(new Insets(16));
//         ambulance3.setStyle("-fx-background-color:" + CARD_BG + "; -fx-background-radius:14px; -fx-border-color:" + OUTLINE_VARIANT + "; -fx-border-radius:14px; -fx-border-width:1px;");

//         VBox unit3Box = new VBox(5);
//         Text unit3 = new Text("UNIT B-205");
//         unit3.setStyle("-fx-font-size:15px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");
//         Text driver3 = new Text("Driver: Sagar More");
//         driver3.setStyle("-fx-font-size:12px; -fx-fill:" + ON_SURFACE_VARIANT + ";");
//         unit3Box.getChildren().addAll(unit3, driver3);

//         VBox time3Box = new VBox(5);
//         Text arrival3 = new Text("11:30 AM");
//         arrival3.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");
//         Text clearance3 = new Text("Cleared: 11:33 AM");
//         clearance3.setStyle("-fx-font-size:12px; -fx-fill:" + ON_SURFACE_VARIANT + ";");
//         time3Box.getChildren().addAll(arrival3, clearance3);

//         VBox route3Box = new VBox(5);
//         Text route3 = new Text("Hadapsar → Swargate");
//         route3.setStyle("-fx-font-size:13px; -fx-fill:" + ON_SURFACE + ";");
//         Text facility3 = new Text("Route Clearance");
//         facility3.setStyle("-fx-font-size:12px; -fx-fill:" + ON_SURFACE_VARIANT + ";");
//         route3Box.getChildren().addAll(route3, facility3);

//         VBox hospital3Box = new VBox(5);
//         Text hospital3 = new Text("Sassoon Hospital");
//         hospital3.setStyle("-fx-font-size:13px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");
//         Text status3 = new Text("COMPLETED");
//         status3.setStyle("-fx-font-size:11px; -fx-font-weight:bold; -fx-fill:#16803c;");
//         hospital3Box.getChildren().addAll(hospital3, status3);

//         HBox.setHgrow(unit3Box, Priority.ALWAYS);
//         HBox.setHgrow(time3Box, Priority.ALWAYS);
//         HBox.setHgrow(route3Box, Priority.ALWAYS);
//         HBox.setHgrow(hospital3Box, Priority.ALWAYS);

//         ambulance3.getChildren().addAll(unit3Box, time3Box, route3Box, hospital3Box);

//         date1Box.getChildren().addAll(date1Header, ambulance1, ambulance2, ambulance3);

//         VBox date2Box = new VBox(14);
//         date2Box.setPadding(new Insets(22));
//         date2Box.setStyle("-fx-background-color:" + CARD_BG + "; -fx-background-radius:18px; -fx-border-color:" + OUTLINE_VARIANT + "; -fx-border-radius:18px;");

//         HBox date2Header = new HBox();

//         Text date2Text = new Text("11 August 2026");
//         date2Text.setStyle("-fx-font-size:20px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");

//         Text count2 = new Text("2 Ambulances");
//         count2.setStyle("-fx-font-size:13px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_COLOR + ";");

//         Region date2Spacer = new Region();
//         HBox.setHgrow(date2Spacer, Priority.ALWAYS);

//         date2Header.getChildren().addAll(date2Text, date2Spacer, count2);

//         HBox history1 = new HBox(20);
//         history1.setPadding(new Insets(16));
//         history1.setAlignment(Pos.CENTER_LEFT);
//         history1.setStyle("-fx-background-color:" + CARD_BG + "; -fx-background-radius:14px; -fx-border-color:" + OUTLINE_VARIANT + "; -fx-border-radius:14px;");

//         Text historyUnit1 = new Text("UNIT A-087");
//         historyUnit1.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");

//         Text historyTime1 = new Text("08:20 AM");
//         historyTime1.setStyle("-fx-font-size:13px; -fx-fill:" + ON_SURFACE_VARIANT + ";");

//         Text historyFacility1 = new Text("Traffic Clearance");
//         historyFacility1.setStyle("-fx-font-size:13px; -fx-fill:" + ON_SURFACE_VARIANT + ";");

//         Text historyHospital1 = new Text("Deenanath Hospital");
//         historyHospital1.setStyle("-fx-font-size:13px; -fx-fill:" + ON_SURFACE + ";");

//         Region historySpacer1 = new Region();
//         HBox.setHgrow(historySpacer1, Priority.ALWAYS);

//         Text historyStatus1 = new Text("COMPLETED");
//         historyStatus1.setStyle("-fx-font-size:11px; -fx-font-weight:bold; -fx-fill:#16803c;");

//         history1.getChildren().addAll(historyUnit1, historyTime1, historyFacility1, historyHospital1, historySpacer1, historyStatus1);

//         HBox history2 = new HBox(20);
//         history2.setPadding(new Insets(16));
//         history2.setAlignment(Pos.CENTER_LEFT);
//         history2.setStyle("-fx-background-color:" + CARD_BG + "; -fx-background-radius:14px; -fx-border-color:" + OUTLINE_VARIANT + "; -fx-border-radius:14px;");

//         Text historyUnit2 = new Text("UNIT C-041");
//         historyUnit2.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + ON_SURFACE + ";");

//         Text historyTime2 = new Text("02:10 PM");
//         historyTime2.setStyle("-fx-font-size:13px; -fx-fill:" + ON_SURFACE_VARIANT + ";");

//         Text historyFacility2 = new Text("Signal Clearance");
//         historyFacility2.setStyle("-fx-font-size:13px; -fx-fill:" + ON_SURFACE_VARIANT + ";");

//         Text historyHospital2 = new Text("KEM Hospital");
//         historyHospital2.setStyle("-fx-font-size:13px; -fx-fill:" + ON_SURFACE + ";");

//         Region historySpacer2 = new Region();
//         HBox.setHgrow(historySpacer2, Priority.ALWAYS);

//         Text historyStatus2 = new Text("COMPLETED");
//         historyStatus2.setStyle("-fx-font-size:11px; -fx-font-weight:bold; -fx-fill:#16803c;");

//         history2.getChildren().addAll(historyUnit2, historyTime2, historyFacility2, historyHospital2, historySpacer2, historyStatus2);

//         date2Box.getChildren().addAll(date2Header, history1, history2);

//         historyPage.getChildren().addAll(headingBox, filterBox, date1Box, date2Box);

//         return historyPage;
//     }
// }



package com.kurukshetra.view.police;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PoliceHistory {

    // --- FAINT DARK-BROWN / WARM BRONZE COLOR PALETTE ---
    private static final String PAGE_BG = "#F7F3EF";
    private static final String SURFACE = "#FFFFFF";
    private static final String BROWN_DARK = "#694d3b";
    private static final String WARM_BRONZE = "#8d6338";
    private static final String PRIMARY_TEXT = "#29231F";
    private static final String SEC_TEXT = "#635951";
    private static final String BORDER = "#E5DBD2";
    private static final String SUCCESS_TEXT = "#34704A";

    public ScrollPane getHistoryView() {

        VBox historyPage = new VBox(24);
        historyPage.setPadding(new Insets(40, 50, 40, 50));
        historyPage.setStyle("-fx-background-color:" + PAGE_BG + ";");

        Text heading = new Text("Ambulance History");
        heading.setStyle("-fx-font-size:30px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");

        Text subHeading = new Text("View ambulances that used police facilities");
        subHeading.setStyle("-fx-font-size:15px; -fx-fill:" + SEC_TEXT + ";");

        VBox headingBox = new VBox(7, heading, subHeading);

        HBox filterBox = new HBox(15);
        filterBox.setAlignment(Pos.CENTER_LEFT);
        filterBox.setPadding(new Insets(18));
        filterBox.setStyle("-fx-background-color:" + SURFACE + "; -fx-background-radius:16px; -fx-border-color:" + BORDER + "; -fx-border-radius:16px;");

        Text dateText = new Text("Select Date");
        dateText.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");

        DatePicker datePicker = new DatePicker();
        datePicker.setPrefWidth(190);
        datePicker.setStyle("-fx-background-radius:10px;");

        Button todayButton = new Button("Today");
        todayButton.setPrefWidth(90);
        todayButton.setPrefHeight(36);
        todayButton.setStyle("-fx-background-color:" + WARM_BRONZE + "; -fx-text-fill:white; -fx-font-size:13px; -fx-font-weight:bold; -fx-background-radius:10px; -fx-cursor: hand;");
        addHoverEffect(todayButton);

        todayButton.setOnAction(event -> datePicker.setValue(java.time.LocalDate.now()));

        Region searchSpacer = new Region();
        HBox.setHgrow(searchSpacer, Priority.ALWAYS);

        TextField searchField = new TextField();
        searchField.setPromptText("Search Unit or Hospital...");
        searchField.setPrefWidth(250);
        searchField.setPrefHeight(36);
        searchField.setStyle("-fx-background-radius: 10px; -fx-border-color: " + BORDER + "; -fx-border-radius: 10px; -fx-background-color: " + SURFACE + ";");

        filterBox.getChildren().addAll(dateText, datePicker, todayButton, searchSpacer, searchField);

        VBox date1Box = new VBox(14);
        date1Box.setPadding(new Insets(22));
        date1Box.setStyle("-fx-background-color:" + SURFACE + "; -fx-background-radius:18px; -fx-border-color:" + BORDER + "; -fx-border-radius:18px;");

        HBox date1Header = new HBox();

        Text date1Text = new Text("12 August 2026");
        date1Text.setStyle("-fx-font-size:20px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");

        Text count1 = new Text("3 Ambulances");
        count1.setStyle("-fx-font-size:13px; -fx-font-weight:bold; -fx-fill:" + WARM_BRONZE + ";");

        Region date1Spacer = new Region();
        HBox.setHgrow(date1Spacer, Priority.ALWAYS);

        date1Header.getChildren().addAll(date1Text, date1Spacer, count1);

        HBox ambulance1 = new HBox(20);
        ambulance1.setAlignment(Pos.CENTER_LEFT);
        ambulance1.setPadding(new Insets(16));
        ambulance1.setStyle("-fx-background-color:" + SURFACE + "; -fx-background-radius:14px; -fx-border-color:" + BORDER + "; -fx-border-radius:14px; -fx-border-width:1px;");

        VBox unit1Box = new VBox(5);
        Text unit1 = new Text("UNIT A-102");
        unit1.setStyle("-fx-font-size:15px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");
        Text driver1 = new Text("Driver: Rahul Patil");
        driver1.setStyle("-fx-font-size:12px; -fx-fill:" + SEC_TEXT + ";");
        unit1Box.getChildren().addAll(unit1, driver1);

        VBox time1Box = new VBox(5);
        Text arrival1 = new Text("09:42 AM");
        arrival1.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");
        Text clearance1 = new Text("Cleared: 09:45 AM");
        clearance1.setStyle("-fx-font-size:12px; -fx-fill:" + SEC_TEXT + ";");
        time1Box.getChildren().addAll(arrival1, clearance1);

        VBox route1Box = new VBox(5);
        Text route1 = new Text("Shivajinagar → Kothrud");
        route1.setStyle("-fx-font-size:13px; -fx-fill:" + PRIMARY_TEXT + ";");
        Text facility1 = new Text("Traffic Clearance");
        facility1.setStyle("-fx-font-size:12px; -fx-fill:" + SEC_TEXT + ";");
        route1Box.getChildren().addAll(route1, facility1);

        VBox hospital1Box = new VBox(5);
        Text hospital1 = new Text("Ruby Hall Clinic");
        hospital1.setStyle("-fx-font-size:13px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");
        Text status1 = new Text("COMPLETED");
        status1.setStyle("-fx-font-size:11px; -fx-font-weight:bold; -fx-fill:" + SUCCESS_TEXT + ";");
        hospital1Box.getChildren().addAll(hospital1, status1);

        HBox.setHgrow(unit1Box, Priority.ALWAYS);
        HBox.setHgrow(time1Box, Priority.ALWAYS);
        HBox.setHgrow(route1Box, Priority.ALWAYS);
        HBox.setHgrow(hospital1Box, Priority.ALWAYS);

        ambulance1.getChildren().addAll(unit1Box, time1Box, route1Box, hospital1Box);

        HBox ambulance2 = new HBox(20);
        ambulance2.setAlignment(Pos.CENTER_LEFT);
        ambulance2.setPadding(new Insets(16));
        ambulance2.setStyle("-fx-background-color:" + SURFACE + "; -fx-background-radius:14px; -fx-border-color:" + BORDER + "; -fx-border-radius:14px; -fx-border-width:1px;");

        VBox unit2Box = new VBox(5);
        Text unit2 = new Text("UNIT C-088");
        unit2.setStyle("-fx-font-size:15px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");
        Text driver2 = new Text("Driver: Amit Shinde");
        driver2.setStyle("-fx-font-size:12px; -fx-fill:" + SEC_TEXT + ";");
        unit2Box.getChildren().addAll(unit2, driver2);

        VBox time2Box = new VBox(5);
        Text arrival2 = new Text("10:15 AM");
        arrival2.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");
        Text clearance2 = new Text("Cleared: 10:18 AM");
        clearance2.setStyle("-fx-font-size:12px; -fx-fill:" + SEC_TEXT + ";");
        time2Box.getChildren().addAll(arrival2, clearance2);

        VBox route2Box = new VBox(5);
        Text route2 = new Text("Camp → Shivajinagar");
        route2.setStyle("-fx-font-size:13px; -fx-fill:" + PRIMARY_TEXT + ";");
        Text facility2 = new Text("Signal Clearance");
        facility2.setStyle("-fx-font-size:12px; -fx-fill:" + SEC_TEXT + ";");
        route2Box.getChildren().addAll(route2, facility2);

        VBox hospital2Box = new VBox(5);
        Text hospital2 = new Text("KEM Hospital");
        hospital2.setStyle("-fx-font-size:13px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");
        Text status2 = new Text("COMPLETED");
        status2.setStyle("-fx-font-size:11px; -fx-font-weight:bold; -fx-fill:" + SUCCESS_TEXT + ";");
        hospital2Box.getChildren().addAll(hospital2, status2);

        HBox.setHgrow(unit2Box, Priority.ALWAYS);
        HBox.setHgrow(time2Box, Priority.ALWAYS);
        HBox.setHgrow(route2Box, Priority.ALWAYS);
        HBox.setHgrow(hospital2Box, Priority.ALWAYS);

        ambulance2.getChildren().addAll(unit2Box, time2Box, route2Box, hospital2Box);

        HBox ambulance3 = new HBox(20);
        ambulance3.setAlignment(Pos.CENTER_LEFT);
        ambulance3.setPadding(new Insets(16));
        ambulance3.setStyle("-fx-background-color:" + SURFACE + "; -fx-background-radius:14px; -fx-border-color:" + BORDER + "; -fx-border-radius:14px; -fx-border-width:1px;");

        VBox unit3Box = new VBox(5);
        Text unit3 = new Text("UNIT B-205");
        unit3.setStyle("-fx-font-size:15px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");
        Text driver3 = new Text("Driver: Sagar More");
        driver3.setStyle("-fx-font-size:12px; -fx-fill:" + SEC_TEXT + ";");
        unit3Box.getChildren().addAll(unit3, driver3);

        VBox time3Box = new VBox(5);
        Text arrival3 = new Text("11:30 AM");
        arrival3.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");
        Text clearance3 = new Text("Cleared: 11:33 AM");
        clearance3.setStyle("-fx-font-size:12px; -fx-fill:" + SEC_TEXT + ";");
        time3Box.getChildren().addAll(arrival3, clearance3);

        VBox route3Box = new VBox(5);
        Text route3 = new Text("Hadapsar → Swargate");
        route3.setStyle("-fx-font-size:13px; -fx-fill:" + PRIMARY_TEXT + ";");
        Text facility3 = new Text("Route Clearance");
        facility3.setStyle("-fx-font-size:12px; -fx-fill:" + SEC_TEXT + ";");
        route3Box.getChildren().addAll(route3, facility3);

        VBox hospital3Box = new VBox(5);
        Text hospital3 = new Text("Sassoon Hospital");
        hospital3.setStyle("-fx-font-size:13px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");
        Text status3 = new Text("COMPLETED");
        status3.setStyle("-fx-font-size:11px; -fx-font-weight:bold; -fx-fill:" + SUCCESS_TEXT + ";");
        hospital3Box.getChildren().addAll(hospital3, status3);

        HBox.setHgrow(unit3Box, Priority.ALWAYS);
        HBox.setHgrow(time3Box, Priority.ALWAYS);
        HBox.setHgrow(route3Box, Priority.ALWAYS);
        HBox.setHgrow(hospital3Box, Priority.ALWAYS);

        ambulance3.getChildren().addAll(unit3Box, time3Box, route3Box, hospital3Box);

        date1Box.getChildren().addAll(date1Header, ambulance1, ambulance2, ambulance3);

        VBox date2Box = new VBox(14);
        date2Box.setPadding(new Insets(22));
        date2Box.setStyle("-fx-background-color:" + SURFACE + "; -fx-background-radius:18px; -fx-border-color:" + BORDER + "; -fx-border-radius:18px;");

        HBox date2Header = new HBox();

        Text date2Text = new Text("11 August 2026");
        date2Text.setStyle("-fx-font-size:20px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");

        Text count2 = new Text("2 Ambulances");
        count2.setStyle("-fx-font-size:13px; -fx-font-weight:bold; -fx-fill:" + WARM_BRONZE + ";");

        Region date2Spacer = new Region();
        HBox.setHgrow(date2Spacer, Priority.ALWAYS);

        date2Header.getChildren().addAll(date2Text, date2Spacer, count2);

        HBox history1 = new HBox(20);
        history1.setPadding(new Insets(16));
        history1.setAlignment(Pos.CENTER_LEFT);
        history1.setStyle("-fx-background-color:" + SURFACE + "; -fx-background-radius:14px; -fx-border-color:" + BORDER + "; -fx-border-radius:14px;");

        Text historyUnit1 = new Text("UNIT A-087");
        historyUnit1.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");

        Text historyTime1 = new Text("08:20 AM");
        historyTime1.setStyle("-fx-font-size:13px; -fx-fill:" + SEC_TEXT + ";");

        Text historyFacility1 = new Text("Traffic Clearance");
        historyFacility1.setStyle("-fx-font-size:13px; -fx-fill:" + SEC_TEXT + ";");

        Text historyHospital1 = new Text("Deenanath Hospital");
        historyHospital1.setStyle("-fx-font-size:13px; -fx-fill:" + PRIMARY_TEXT + ";");

        Region historySpacer1 = new Region();
        HBox.setHgrow(historySpacer1, Priority.ALWAYS);

        Text historyStatus1 = new Text("COMPLETED");
        historyStatus1.setStyle("-fx-font-size:11px; -fx-font-weight:bold; -fx-fill:" + SUCCESS_TEXT + ";");

        history1.getChildren().addAll(historyUnit1, historyTime1, historyFacility1, historyHospital1, historySpacer1, historyStatus1);

        HBox history2 = new HBox(20);
        history2.setPadding(new Insets(16));
        history2.setAlignment(Pos.CENTER_LEFT);
        history2.setStyle("-fx-background-color:" + SURFACE + "; -fx-background-radius:14px; -fx-border-color:" + BORDER + "; -fx-border-radius:14px;");

        Text historyUnit2 = new Text("UNIT C-041");
        historyUnit2.setStyle("-fx-font-size:14px; -fx-font-weight:bold; -fx-fill:" + PRIMARY_TEXT + ";");

        Text historyTime2 = new Text("02:10 PM");
        historyTime2.setStyle("-fx-font-size:13px; -fx-fill:" + SEC_TEXT + ";");

        Text historyFacility2 = new Text("Signal Clearance");
        historyFacility2.setStyle("-fx-font-size:13px; -fx-fill:" + SEC_TEXT + ";");

        Text historyHospital2 = new Text("KEM Hospital");
        historyHospital2.setStyle("-fx-font-size:13px; -fx-fill:" + PRIMARY_TEXT + ";");

        Region historySpacer2 = new Region();
        HBox.setHgrow(historySpacer2, Priority.ALWAYS);

        Text historyStatus2 = new Text("COMPLETED");
        historyStatus2.setStyle("-fx-font-size:11px; -fx-font-weight:bold; -fx-fill:" + SUCCESS_TEXT + ";");

        history2.getChildren().addAll(historyUnit2, historyTime2, historyFacility2, historyHospital2, historySpacer2, historyStatus2);

        date2Box.getChildren().addAll(date2Header, history1, history2);

        historyPage.getChildren().addAll(headingBox, filterBox, date1Box, date2Box);

        ScrollPane scrollPane = new ScrollPane(historyPage);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");

        return scrollPane;
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + BROWN_DARK + "; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + WARM_BRONZE + "; -fx-text-fill: white; -fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand;"));
    }
}