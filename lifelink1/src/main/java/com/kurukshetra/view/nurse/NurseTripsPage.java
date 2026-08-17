// package com.kurukshetra.view.nurse;

// import javafx.animation.FadeTransition;
// import javafx.animation.ParallelTransition;
// import javafx.animation.ScaleTransition;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.ComboBox;
// import javafx.scene.control.DatePicker;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;

// import java.util.ArrayList;
// import java.util.List;

// public class NurseTripsPage {

//     public BorderPane tripsRoot;
//     public StackPane pageStack;
//     public VBox tableBody;
//     public VBox emptyState;

//     public List<HBox> rowNodes = new ArrayList<>();
//     public List<String[]> rowData = new ArrayList<>();

//     // Column widths, shared between the header row and every data row so
//     // everything lines up like a real table without needing a TableView.
//     public static final double COL_SR = 50;
//     public static final double COL_TRIP = 90;
//     public static final double COL_LOCATION = 250;
//     public static final double COL_PATIENT = 90;
//     public static final double COL_DATE = 100;
//     public static final double COL_TIME = 90;
//     public static final double COL_STATUS = 110;
//     public static final double COL_ACTION = 100;

//     public BorderPane getNurseTripsPage(Runnable callBackDashboard) {

//         tripsRoot = new BorderPane();
//         tripsRoot.setStyle("-fx-background-color : #F7F9FC; -fx-font-family : 'Segoe UI';");

//         // ================= TOP BAR =================

//         HBox topBar = new HBox(14);
//         topBar.setAlignment(Pos.CENTER_LEFT);
//         topBar.setPadding(new Insets(18, 30, 6, 30));

//         Button backButton = new Button("‹");
//         backButton.setPrefWidth(40);
//         backButton.setPrefHeight(40);
//         backButton.setStyle("-fx-background-color : #EAF7FD; -fx-text-fill : #08A1E5; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;");

//         backButton.setOnAction(e -> {
//             System.out.println("Back to Dashboard");
//             callBackDashboard.run();
//         });

//         backButton.setOnMouseEntered(e ->
//                 backButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;")
//         );

//         backButton.setOnMouseExited(e ->
//                 backButton.setStyle("-fx-background-color : #EAF7FD; -fx-text-fill : #08A1E5; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;")
//         );

//         VBox heading = new VBox(3);

//         Text pageTitle = new Text("Nurse Trips");
//         pageTitle.setStyle("-fx-font-size : 24px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Text pageSubtitle = new Text("View your patient pickup and transportation trips.");
//         pageSubtitle.setStyle("-fx-font-size : 11px; -fx-fill : #728096;");

//         heading.getChildren().addAll(pageTitle, pageSubtitle);

//         Region topSpace = new Region();
//         HBox.setHgrow(topSpace, Priority.ALWAYS);

//         Button newTripButton = new Button("+  New Trip");
//         newTripButton.setPrefHeight(40);
//         newTripButton.setPrefWidth(130);
//         newTripButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 10; -fx-cursor : hand;");

//         newTripButton.setOnMouseEntered(e ->
//                 newTripButton.setStyle("-fx-background-color : #007FAE; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 10; -fx-cursor : hand;")
//         );

//         newTripButton.setOnMouseExited(e ->
//                 newTripButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 10; -fx-cursor : hand;")
//         );

//         topBar.getChildren().addAll(backButton, heading, topSpace, newTripButton);

//         // ================= MAIN CONTENT =================

//         VBox mainContent = new VBox(18);
//         mainContent.setPadding(new Insets(10, 30, 20, 30));

//         // ================= SUMMARY CARDS =================

//         HBox summaryRow = new HBox(14);

//         VBox totalCard = summaryCard("TOTAL TRIPS", "24", "#08A1E5");
//         VBox todayCard = summaryCard("TODAY'S TRIPS", "5", "#08A1E5");
//         VBox completedCard = summaryCard("COMPLETED TRIPS", "18", "#20B86A");

//         summaryRow.getChildren().addAll(totalCard, todayCard, completedCard);

//         // ================= FILTER ROW =================

//         HBox filterRow = new HBox(12);
//         filterRow.setAlignment(Pos.CENTER_LEFT);
//         filterRow.setPadding(new Insets(14, 16, 14, 16));
//         filterRow.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.06), 8, 0, 0, 2);");

//         TextField searchField = new TextField();
//         searchField.setPromptText("🔍  Search by Trip ID or Patient ID...");
//         searchField.setPrefHeight(36);
//         searchField.setPrefWidth(320);
//         searchField.setStyle("-fx-background-color : #F0F4F9; -fx-background-radius : 8; -fx-border-color : transparent; -fx-font-size : 11px;");

//         searchField.focusedProperty().addListener((obs, wasFocused, isFocused) -> {

//             if (isFocused) {
//                 searchField.setStyle("-fx-background-color : #EAF7FD; -fx-background-radius : 8; -fx-border-color : #08A1E5; -fx-border-width : 2; -fx-border-radius : 8; -fx-font-size : 11px;");
//             } else {
//                 searchField.setStyle("-fx-background-color : #F0F4F9; -fx-background-radius : 8; -fx-border-color : transparent; -fx-font-size : 11px;");
//             }
//         });

//         DatePicker dateFilter = new DatePicker();
//         dateFilter.setPromptText("Select Date");
//         dateFilter.setPrefHeight(36);
//         dateFilter.setPrefWidth(160);
//         dateFilter.setStyle("-fx-background-color : #F0F4F9; -fx-background-radius : 8; -fx-font-size : 11px;");

//         ComboBox<String> statusFilter = new ComboBox<>();
//         statusFilter.getItems().addAll("All Status", "Scheduled", "In Progress", "Completed", "Cancelled");
//         statusFilter.setValue("All Status");
//         statusFilter.setPrefHeight(36);
//         statusFilter.setStyle("-fx-background-color : #F0F4F9; -fx-background-radius : 8; -fx-font-size : 11px;");

//         Region filterSpace = new Region();
//         HBox.setHgrow(filterSpace, Priority.ALWAYS);

//         filterRow.getChildren().addAll(searchField, filterSpace, dateFilter, statusFilter);

//         // ================= TRIP HISTORY TABLE =================

//         Text sectionTitle = new Text("Trip History");
//         sectionTitle.setStyle("-fx-font-size : 15px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         VBox tableContainer = new VBox();
//         tableContainer.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.08), 10, 0, 0, 3);");

//         HBox headerRow = tableHeaderRow();

//         tableBody = new VBox();

//         tableContainer.getChildren().addAll(headerRow, tableBody);

//         pageStack = new StackPane();

//         // ---- Sample trips (Sr.No, TripId, Location, PatientId, Date, Time, Status) ----

//         addTripRow("01", "NT-1024", "City Hospital  →  Shivaji Nagar", "P2048", "12 Aug 2026", "10:30 AM", "Completed");
//         addTripRow("02", "NT-1025", "City Hospital  →  Kothrud", "P2051", "12 Aug 2026", "11:15 AM", "In Progress");
//         addTripRow("03", "NT-1026", "City Hospital  →  Hadapsar", "P2055", "12 Aug 2026", "1:00 PM", "Scheduled");
//         addTripRow("04", "NT-1027", "City Hospital  →  Baner", "P2042", "11 Aug 2026", "4:30 PM", "Completed");
//         addTripRow("05", "NT-1028", "City Hospital  →  Aundh", "P2038", "11 Aug 2026", "6:00 PM", "Completed");

//         emptyState = buildEmptyState();
//         emptyState.setVisible(false);
//         emptyState.setManaged(false);

//         // ================= PAGINATION =================

//         HBox paginationRow = buildPaginationRow();

//         VBox tripsSection = new VBox(14, sectionTitle, tableContainer, emptyState, paginationRow);

//         VBox contentColumn = new VBox(18, summaryRow, filterRow, tripsSection);

//         pageStack.getChildren().add(contentColumn);

//         // ---- Wire filters to show/hide matching rows ----

//         Runnable applyFilters = () -> filterTrips(searchField.getText(), statusFilter.getValue());

//         searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters.run());
//         statusFilter.setOnAction(e -> applyFilters.run());

//         newTripButton.setOnAction(e -> showNewTripModal());

//         mainContent.getChildren().add(pageStack);

//         tripsRoot.setTop(topBar);
//         tripsRoot.setCenter(mainContent);

//         // ================= PAGE ANIMATION =================

//         FadeTransition fade = new FadeTransition(NurseAppSettings.dur(450), tripsRoot);
//         fade.setFromValue(0.3);
//         fade.setToValue(1);
//         fade.play();

//         return tripsRoot;
//     }

//     // =========================================================
//     // SUMMARY CARD
//     // =========================================================

//     public VBox summaryCard(String label, String value, String accentColor) {

//         VBox card = new VBox(4);
//         card.setPadding(new Insets(14, 20, 14, 20));
//         card.setPrefWidth(230);
//         card.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.07), 8, 0, 0, 2);");

//         Text labelText = new Text(label);
//         labelText.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : #A6B2C2;");

//         Text valueText = new Text(value);
//         valueText.setStyle("-fx-font-size : 22px; -fx-font-weight : bold; -fx-fill : " + accentColor + ";");

//         card.getChildren().addAll(labelText, valueText);

//         return card;
//     }

//     // =========================================================
//     // TABLE HEADER ROW
//     // =========================================================

//     public HBox tableHeaderRow() {

//         HBox header = new HBox();
//         header.setPadding(new Insets(12, 16, 12, 16));
//         header.setStyle("-fx-background-color : #F5F7FC; -fx-background-radius : 14 14 0 0;");

//         header.getChildren().addAll(
//                 wrapCell(headerCell("SR. NO.", COL_SR), COL_SR),
//                 wrapCell(headerCell("TRIP ID", COL_TRIP), COL_TRIP),
//                 locationHeaderCell(),
//                 wrapCell(headerCell("PATIENT ID", COL_PATIENT), COL_PATIENT),
//                 wrapCell(headerCell("DATE", COL_DATE), COL_DATE),
//                 wrapCell(headerCell("TIME", COL_TIME), COL_TIME),
//                 wrapCell(headerCell("STATUS", COL_STATUS), COL_STATUS),
//                 wrapCell(headerCell("ACTION", COL_ACTION), COL_ACTION));

//         return header;
//     }

//     public HBox locationHeaderCell() {

//         HBox box = new HBox(headerCell("LOCATION (FROM → TO)", COL_LOCATION));
//         box.setPrefWidth(COL_LOCATION);
//         box.setAlignment(Pos.CENTER_LEFT);
//         return box;
//     }

//     public Text headerCell(String label, double width) {

//         Text text = new Text(label);
//         text.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : #728096;");

//         return text;
//     }

//     // =========================================================
//     // TRIP ROW
//     // =========================================================

//     public void addTripRow(String srNo, String tripId, String location, String patientId, String date, String time, String status) {

//         HBox row = new HBox();
//         row.setPadding(new Insets(13, 16, 13, 16));
//         row.setAlignment(Pos.CENTER_LEFT);
//         row.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #EEF1F6 transparent transparent transparent; -fx-border-width : 1 0 0 0; -fx-cursor : hand;");

//         Text srText = rowCell(srNo, "#728096", false);
//         Text tripText = rowCell(tripId, "#08A1E5", true);

//         HBox locationBox = new HBox(6);
//         locationBox.setPrefWidth(COL_LOCATION);
//         locationBox.setAlignment(Pos.CENTER_LEFT);
//         Text locationText = new Text(location);
//         locationText.setStyle("-fx-font-size : 11px; -fx-fill : #172B4D;");
//         locationBox.getChildren().add(locationText);

//         Text patientText = rowCell(patientId, "#172B4D", false);
//         Text dateText = rowCell(date, "#536277", false);
//         Text timeText = rowCell(time, "#728096", false);

//         Text statusBadge = new Text(status);
//         statusBadge.setStyle(badgeStyle(status));
//         StackPane statusCell = new StackPane(statusBadge);
//         statusCell.setPrefWidth(COL_STATUS);
//         statusCell.setAlignment(Pos.CENTER_LEFT);

//         Text actionText = new Text("View Details");
//         actionText.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : #08A1E5;");
//         StackPane actionCell = new StackPane(actionText);
//         actionCell.setPrefWidth(COL_ACTION);
//         actionCell.setAlignment(Pos.CENTER_LEFT);

//         StackPane srCell = wrapCell(srText, COL_SR);
//         StackPane tripCell = wrapCell(tripText, COL_TRIP);
//         StackPane patientCell = wrapCell(patientText, COL_PATIENT);
//         StackPane dateCell = wrapCell(dateText, COL_DATE);
//         StackPane timeCell = wrapCell(timeText, COL_TIME);

//         row.getChildren().addAll(srCell, tripCell, locationBox, patientCell, dateCell, timeCell, statusCell, actionCell);

//         row.setOnMouseEntered(e ->
//                 row.setStyle("-fx-background-color : #EAF7FD; -fx-border-color : #EEF1F6 transparent transparent transparent; -fx-border-width : 1 0 0 0; -fx-cursor : hand;")
//         );

//         row.setOnMouseExited(e ->
//                 row.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #EEF1F6 transparent transparent transparent; -fx-border-width : 1 0 0 0; -fx-cursor : hand;")
//         );

//         row.setOnMouseClicked(e -> showTripDetailsModal(tripId, location, patientId, date, time, status));

//         tableBody.getChildren().add(row);
//         rowNodes.add(row);
//         rowData.add(new String[] { tripId, patientId, status });
//     }

//     public Text rowCell(String value, String color, boolean bold) {

//         Text text = new Text(value);
//         text.setStyle("-fx-font-size : 11px; " + (bold ? "-fx-font-weight : bold; " : "") + "-fx-fill : " + color + ";");
//         return text;
//     }

//     public StackPane wrapCell(Text text, double width) {

//         StackPane cell = new StackPane(text);
//         cell.setPrefWidth(width);
//         cell.setAlignment(Pos.CENTER_LEFT);
//         return cell;
//     }

//     public String badgeStyle(String status) {

//         String bg = "#EAF0F6";
//         String fg = "#536277";

//         if (status.equals("Completed")) {
//             bg = "#E7FBF1";
//             fg = "#20B86A";
//         } else if (status.equals("In Progress")) {
//             bg = "#EAF7FD";
//             fg = "#08A1E5";
//         } else if (status.equals("Scheduled")) {
//             bg = "#FFF6E5";
//             fg = "#C98A1B";
//         } else if (status.equals("Cancelled")) {
//             bg = "#FDEBEC";
//             fg = "#D71920";
//         }

//         return "-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + fg + "; -fx-background-color : " + bg + "; -fx-padding : 3 8 3 8; -fx-background-radius : 8;";
//     }

//     // =========================================================
//     // SEARCH + STATUS FILTER
//     // =========================================================

//     public void filterTrips(String searchText, String status) {

//         String query = searchText == null ? "" : searchText.toLowerCase().trim();

//         boolean anyVisible = false;

//         for (int i = 0; i < rowNodes.size(); i++) {

//             HBox row = rowNodes.get(i);
//             String[] data = rowData.get(i);

//             boolean matchesSearch = query.isEmpty()
//                     || data[0].toLowerCase().contains(query)
//                     || data[1].toLowerCase().contains(query);

//             boolean matchesStatus = status == null || status.equals("All Status") || data[2].equals(status);

//             boolean visible = matchesSearch && matchesStatus;

//             row.setVisible(visible);
//             row.setManaged(visible);

//             if (visible) {
//                 anyVisible = true;
//             }
//         }

//         emptyState.setVisible(!anyVisible);
//         emptyState.setManaged(!anyVisible);
//     }

//     // =========================================================
//     // PAGINATION
//     // =========================================================

//     public HBox buildPaginationRow() {

//         HBox pagination = new HBox(14);
//         pagination.setAlignment(Pos.CENTER_LEFT);
//         pagination.setPadding(new Insets(4, 6, 0, 6));

//         Text summary = new Text("Showing 1–5 of 24 trips");
//         summary.setStyle("-fx-font-size : 10px; -fx-fill : #728096;");

//         Region space = new Region();
//         HBox.setHgrow(space, Priority.ALWAYS);

//         Text prev = pageLink("Previous", false);
//         Text pageOne = pageLink("1", true);
//         Text pageTwo = pageLink("2", false);
//         Text pageThree = pageLink("3", false);
//         Text next = pageLink("Next", false);

//         HBox pageLinks = new HBox(10, prev, pageOne, pageTwo, pageThree, next);
//         pageLinks.setAlignment(Pos.CENTER_LEFT);

//         pagination.getChildren().addAll(summary, space, pageLinks);

//         return pagination;
//     }

//     public Text pageLink(String label, boolean active) {

//         Text text = new Text(label);
//         text.setStyle(active
//                 ? "-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : #08A1E5; -fx-cursor : hand;"
//                 : "-fx-font-size : 11px; -fx-fill : #728096; -fx-cursor : hand;");

//         text.setOnMouseClicked(e -> System.out.println("Pagination clicked: " + label));

//         return text;
//     }

//     // =========================================================
//     // EMPTY STATE
//     // =========================================================

//     public VBox buildEmptyState() {

//         VBox box = new VBox(10);
//         box.setAlignment(Pos.CENTER);
//         box.setPadding(new Insets(50));
//         box.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14;");

//         Text icon = new Text("⇄");
//         icon.setStyle("-fx-font-size : 30px; -fx-fill : #C7D2DE;");

//         Text title = new Text("No trips found");
//         title.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Text subtitle = new Text("Your patient trips will appear here.");
//         subtitle.setStyle("-fx-font-size : 11px; -fx-fill : #728096;");

//         Button createButton = new Button("+  New Trip");
//         createButton.setPrefHeight(38);
//         createButton.setPrefWidth(140);
//         createButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;");

//         createButton.setOnAction(e -> showNewTripModal());

//         box.getChildren().addAll(icon, title, subtitle, createButton);

//         return box;
//     }

//     // =========================================================
//     // TRIP DETAILS MODAL (no nurse info — single logged-in nurse)
//     // =========================================================

//     public void showTripDetailsModal(String tripId, String location, String patientId, String date, String time, String status) {

//         StackPane dimmer = new StackPane();
//         dimmer.setStyle("-fx-background-color : rgba(23,43,77,0.45);");

//         VBox modal = new VBox(14);
//         modal.setMaxWidth(360);
//         modal.setPadding(new Insets(24));
//         modal.setStyle("-fx-background-color : white; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(20,30,50,0.30), 20, 0, 0, 8);");
//         modal.setOpacity(0);
//         modal.setScaleX(0.92);
//         modal.setScaleY(0.92);

//         Text modalTitle = new Text("Trip Details — " + tripId);
//         modalTitle.setStyle("-fx-font-size : 15px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Text badge = new Text(status);
//         badge.setStyle(badgeStyle(status));
//         StackPane badgeWrap = new StackPane(badge);
//         badgeWrap.setAlignment(Pos.CENTER_LEFT);

//         VBox infoSection = new VBox(6,
//                 modalRow("Trip ID", tripId),
//                 modalRow("Patient ID", patientId),
//                 modalRow("Date", date),
//                 modalRow("Time", time));

//         Text locationLabel = new Text("LOCATION");
//         locationLabel.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : #A6B2C2;");

//         Text locationText = new Text(location);
//         locationText.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         VBox locationBox = new VBox(4, locationLabel, locationText);

//         HBox actionsRow = new HBox(10);
//         actionsRow.setAlignment(Pos.CENTER_RIGHT);

//         Button closeButton = new Button("Close");
//         closeButton.setPrefWidth(100);
//         closeButton.setPrefHeight(36);
//         closeButton.setStyle("-fx-background-color : #F0F4F9; -fx-text-fill : #536277; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");
//         closeButton.setOnAction(e -> hideModal(dimmer));

//         actionsRow.getChildren().add(closeButton);

//         if (status.equals("In Progress")) {

//             Button trackButton = new Button("View Current Trip");
//             trackButton.setPrefWidth(150);
//             trackButton.setPrefHeight(36);
//             trackButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");
//             trackButton.setOnAction(e -> {
//                 System.out.println("View Current Trip clicked: " + tripId);
//                 NurseToast.show(NurseDashboardPage.appOverlay, "Tracking Trip " + tripId, "info");
//             });

//             actionsRow.getChildren().add(0, trackButton);
//         }

//         modal.getChildren().addAll(modalTitle, badgeWrap, locationBox, infoSection, actionsRow);

//         dimmer.getChildren().add(modal);
//         dimmer.setOnMouseClicked(e -> {
//             if (e.getTarget() == dimmer) {
//                 hideModal(dimmer);
//             }
//         });

//         pageStack.getChildren().add(dimmer);

//         FadeTransition dimIn = new FadeTransition(NurseAppSettings.dur(180), dimmer);
//         dimIn.setFromValue(0);
//         dimIn.setToValue(1);

//         FadeTransition modalFadeIn = new FadeTransition(NurseAppSettings.dur(200), modal);
//         modalFadeIn.setToValue(1);

//         ScaleTransition modalScaleIn = new ScaleTransition(NurseAppSettings.dur(200), modal);
//         modalScaleIn.setToX(1);
//         modalScaleIn.setToY(1);

//         new ParallelTransition(dimIn, modalFadeIn, modalScaleIn).play();
//     }

//     public HBox modalRow(String label, String value) {

//         HBox row = new HBox();
//         row.setAlignment(Pos.CENTER_LEFT);

//         Text labelText = new Text(label);
//         labelText.setStyle("-fx-font-size : 10px; -fx-fill : #728096;");

//         Region space = new Region();
//         HBox.setHgrow(space, Priority.ALWAYS);

//         Text valueText = new Text(value);
//         valueText.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         row.getChildren().addAll(labelText, space, valueText);

//         return row;
//     }

//     // =========================================================
//     // NEW TRIP MODAL (no Nurse Name field — logged-in nurse is implicit)
//     // =========================================================

//     public void showNewTripModal() {

//         StackPane dimmer = new StackPane();
//         dimmer.setStyle("-fx-background-color : rgba(23,43,77,0.45);");

//         VBox modal = new VBox(12);
//         modal.setMaxWidth(360);
//         modal.setPadding(new Insets(24));
//         modal.setStyle("-fx-background-color : white; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(20,30,50,0.30), 20, 0, 0, 8);");
//         modal.setOpacity(0);
//         modal.setScaleX(0.92);
//         modal.setScaleY(0.92);

//         Text modalTitle = new Text("New Trip");
//         modalTitle.setStyle("-fx-font-size : 16px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         TextField patientField = formField("Patient ID");
//         TextField fromField = formField("From Location");
//         TextField toField = formField("To Location");

//         DatePicker tripDate = new DatePicker();
//         tripDate.setPromptText("Date");
//         tripDate.setPrefHeight(34);
//         tripDate.setMaxWidth(Double.MAX_VALUE);
//         tripDate.setStyle("-fx-background-color : #F0F4F9; -fx-background-radius : 8; -fx-font-size : 11px;");

//         TextField timeField = formField("Time (e.g. 10:30 AM)");

//         Button createButton = new Button("Create Trip");
//         createButton.setPrefHeight(38);
//         createButton.setMaxWidth(Double.MAX_VALUE);
//         createButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;");

//         createButton.setOnAction(e -> {

//             System.out.println("Create Trip button clicked");

//             hideModal(dimmer);

//             NurseToast.show(NurseDashboardPage.appOverlay, "Trip created successfully.", "success");
//         });

//         modal.getChildren().addAll(modalTitle, patientField, fromField, toField, tripDate, timeField, createButton);

//         dimmer.getChildren().add(modal);
//         dimmer.setOnMouseClicked(e -> {
//             if (e.getTarget() == dimmer) {
//                 hideModal(dimmer);
//             }
//         });

//         pageStack.getChildren().add(dimmer);

//         FadeTransition dimIn = new FadeTransition(NurseAppSettings.dur(180), dimmer);
//         dimIn.setFromValue(0);
//         dimIn.setToValue(1);

//         FadeTransition modalFadeIn = new FadeTransition(NurseAppSettings.dur(200), modal);
//         modalFadeIn.setToValue(1);

//         ScaleTransition modalScaleIn = new ScaleTransition(NurseAppSettings.dur(200), modal);
//         modalScaleIn.setToX(1);
//         modalScaleIn.setToY(1);

//         new ParallelTransition(dimIn, modalFadeIn, modalScaleIn).play();
//     }

//     public TextField formField(String hint) {

//         TextField field = new TextField();
//         field.setPromptText(hint);
//         field.setPrefHeight(34);
//         field.setStyle("-fx-background-color : #F0F4F9; -fx-background-radius : 8; -fx-border-color : transparent; -fx-font-size : 11px;");

//         field.focusedProperty().addListener((obs, wasFocused, isFocused) -> {

//             if (isFocused) {
//                 field.setStyle("-fx-background-color : #EAF7FD; -fx-background-radius : 8; -fx-border-color : #08A1E5; -fx-border-width : 2; -fx-border-radius : 8; -fx-font-size : 11px;");
//             } else {
//                 field.setStyle("-fx-background-color : #F0F4F9; -fx-background-radius : 8; -fx-border-color : transparent; -fx-font-size : 11px;");
//             }
//         });

//         return field;
//     }

//     public void hideModal(StackPane dimmer) {

//         FadeTransition dimOut = new FadeTransition(NurseAppSettings.dur(160), dimmer);
//         dimOut.setToValue(0);
//         dimOut.setOnFinished(e -> pageStack.getChildren().remove(dimmer));
//         dimOut.play();
//     }
// }

package com.kurukshetra.view.nurse;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class NurseTripsPage {

    // ================= COLOR PALETTE =================
    private static final String PRIMARY_PINK = "#E67593";
    private static final String PRIMARY_HOVER = "#D95F80";
    private static final String PINK_DARK = "#FF1493";
    private static final String VERY_LIGHT_PINK = "#FDEDF2";
    private static final String LIGHT_PINK = "#F9E0E8";
    private static final String SOFT_PINK = "#F5D1DC";
    private static final String VERY_PALE_PINK = "#FFF9FA";
    private static final String PAGE_BG = "#FCF9FA";
    private static final String SURFACE = "#FFFFFF";
    private static final String PRIMARY_TEXT = "#2B2226";
    private static final String SECONDARY_TEXT = "#665960";
    private static final String MUTED_TEXT = "#94878D";
    private static final String BORDER_COLOR = "#EEDDE3";
    private static final String DIVIDER_COLOR = "#F3E8EC";

    public BorderPane tripsRoot;
    public StackPane pageStack;
    public VBox tableBody;
    public VBox emptyState;

    public List<HBox> rowNodes = new ArrayList<>();
    public List<String[]> rowData = new ArrayList<>();

    public static final double COL_SR = 50;
    public static final double COL_TRIP = 90;
    public static final double COL_LOCATION = 250;
    public static final double COL_PATIENT = 90;
    public static final double COL_DATE = 100;
    public static final double COL_TIME = 90;
    public static final double COL_STATUS = 110;
    public static final double COL_ACTION = 100;

    public BorderPane getNurseTripsPage(Runnable callBackDashboard) {

        tripsRoot = new BorderPane();
        tripsRoot.setStyle("-fx-background-color : " + PAGE_BG + "; -fx-font-family : 'Segoe UI';");

        // ================= TOP BAR =================

        HBox topBar = new HBox(14);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(18, 30, 6, 30));

        Button backButton = new Button("‹");
        backButton.setPrefWidth(40);
        backButton.setPrefHeight(40);
        backButton.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-text-fill : " + PRIMARY_PINK + "; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;");

        backButton.setOnAction(e -> {
            System.out.println("Back to Dashboard");
            callBackDashboard.run();
        });

        backButton.setOnMouseEntered(e ->
                backButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;")
        );

        backButton.setOnMouseExited(e ->
                backButton.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-text-fill : " + PRIMARY_PINK + "; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;")
        );

        VBox heading = new VBox(3);

        Text pageTitle = new Text("Nurse Trips");
        pageTitle.setStyle("-fx-font-size : 24px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text pageSubtitle = new Text("View your patient pickup and transportation trips.");
        pageSubtitle.setStyle("-fx-font-size : 11px; -fx-fill : " + SECONDARY_TEXT + ";");

        heading.getChildren().addAll(pageTitle, pageSubtitle);

        Region topSpace = new Region();
        HBox.setHgrow(topSpace, Priority.ALWAYS);

        Button newTripButton = new Button("+  New Trip");
        newTripButton.setPrefHeight(40);
        newTripButton.setPrefWidth(130);
        newTripButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 10; -fx-cursor : hand;");

        newTripButton.setOnMouseEntered(e ->
                newTripButton.setStyle("-fx-background-color : " + PRIMARY_HOVER + "; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 10; -fx-cursor : hand;")
        );

        newTripButton.setOnMouseExited(e ->
                newTripButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 10; -fx-cursor : hand;")
        );

        topBar.getChildren().addAll(backButton, heading, topSpace, newTripButton);

        // ================= MAIN CONTENT =================

        VBox mainContent = new VBox(18);
        mainContent.setPadding(new Insets(10, 30, 20, 30));

        // ================= SUMMARY CARDS =================

        HBox summaryRow = new HBox(14);
        VBox totalCard = summaryCard("TOTAL TRIPS", "24", PRIMARY_PINK);
        VBox todayCard = summaryCard("TODAY'S TRIPS", "5", PRIMARY_PINK);
        VBox completedCard = summaryCard("COMPLETED TRIPS", "18", "#258052");

        summaryRow.getChildren().addAll(totalCard, todayCard, completedCard);

        // ================= FILTER ROW =================

        HBox filterRow = new HBox(12);
        filterRow.setAlignment(Pos.CENTER_LEFT);
        filterRow.setPadding(new Insets(14, 16, 14, 16));
        filterRow.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.06), 8, 0, 0, 2);");

        TextField searchField = new TextField();
        searchField.setPromptText("🔍  Search by Trip ID or Patient ID...");
        searchField.setPrefHeight(36);
        searchField.setPrefWidth(320);
        searchField.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 8; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 8; -fx-font-size : 11px; -fx-text-fill : " + PRIMARY_TEXT + ";");

        searchField.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused) {
                searchField.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 8; -fx-border-color : " + PRIMARY_PINK + "; -fx-border-width : 2; -fx-border-radius : 8; -fx-font-size : 11px; -fx-text-fill : " + PRIMARY_TEXT + ";");
            } else {
                searchField.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 8; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 8; -fx-font-size : 11px; -fx-text-fill : " + PRIMARY_TEXT + ";");
            }
        });

        DatePicker dateFilter = new DatePicker();
        dateFilter.setPromptText("Select Date");
        dateFilter.setPrefHeight(36);
        dateFilter.setPrefWidth(160);
        dateFilter.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 8; -fx-background-radius : 8; -fx-font-size : 11px;");

        ComboBox<String> statusFilter = new ComboBox<>();
        statusFilter.getItems().addAll("All Status", "Scheduled", "In Progress", "Completed", "Cancelled");
        statusFilter.setValue("All Status");
        statusFilter.setPrefHeight(36);
        statusFilter.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 8; -fx-background-radius : 8; -fx-font-size : 11px;");

        Region filterSpace = new Region();
        HBox.setHgrow(filterSpace, Priority.ALWAYS);

        filterRow.getChildren().addAll(searchField, filterSpace, dateFilter, statusFilter);

        // ================= TRIP HISTORY TABLE =================

        Text sectionTitle = new Text("Trip History");
        sectionTitle.setStyle("-fx-font-size : 15px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        VBox tableContainer = new VBox();
        tableContainer.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 10, 0, 0, 3);");

        HBox headerRow = tableHeaderRow();
        tableBody = new VBox();
        tableContainer.getChildren().addAll(headerRow, tableBody);

        pageStack = new StackPane();

        addTripRow("01", "NT-1024", "City Hospital  →  Shivaji Nagar", "P2048", "12 Aug 2026", "10:30 AM", "Completed");
        addTripRow("02", "NT-1025", "City Hospital  →  Kothrud", "P2051", "12 Aug 2026", "11:15 AM", "In Progress");
        addTripRow("03", "NT-1026", "City Hospital  →  Hadapsar", "P2055", "12 Aug 2026", "1:00 PM", "Scheduled");
        addTripRow("04", "NT-1027", "City Hospital  →  Baner", "P2042", "11 Aug 2026", "4:30 PM", "Completed");
        addTripRow("05", "NT-1028", "City Hospital  →  Aundh", "P2038", "11 Aug 2026", "6:00 PM", "Completed");

        emptyState = buildEmptyState();
        emptyState.setVisible(false);
        emptyState.setManaged(false);

        HBox paginationRow = buildPaginationRow();

        VBox tripsSection = new VBox(14, sectionTitle, tableContainer, emptyState, paginationRow);
        VBox contentColumn = new VBox(18, summaryRow, filterRow, tripsSection);

        pageStack.getChildren().add(contentColumn);

        Runnable applyFilters = () -> filterTrips(searchField.getText(), statusFilter.getValue());
        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters.run());
        statusFilter.setOnAction(e -> applyFilters.run());

        newTripButton.setOnAction(e -> showNewTripModal());

        mainContent.getChildren().add(pageStack);
        tripsRoot.setTop(topBar);
        tripsRoot.setCenter(mainContent);

        FadeTransition fade = new FadeTransition(NurseAppSettings.dur(450), tripsRoot);
        fade.setFromValue(0.3);
        fade.setToValue(1);
        fade.play();

        return tripsRoot;
    }

    public VBox summaryCard(String label, String value, String accentColor) {

        VBox card = new VBox(4);
        card.setPadding(new Insets(14, 20, 14, 20));
        card.setPrefWidth(230);
        card.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.07), 8, 0, 0, 2);");

        Text labelText = new Text(label);
        labelText.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + MUTED_TEXT + ";");

        Text valueText = new Text(value);
        valueText.setStyle("-fx-font-size : 22px; -fx-font-weight : bold; -fx-fill : " + accentColor + ";");

        card.getChildren().addAll(labelText, valueText);
        return card;
    }

    public HBox tableHeaderRow() {

        HBox header = new HBox();
        header.setPadding(new Insets(12, 16, 12, 16));
        header.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-background-radius : 14 14 0 0; -fx-border-color : " + DIVIDER_COLOR + " transparent transparent transparent;");

        header.getChildren().addAll(
                wrapCell(headerCell("SR. NO.", COL_SR), COL_SR),
                wrapCell(headerCell("TRIP ID", COL_TRIP), COL_TRIP),
                locationHeaderCell(),
                wrapCell(headerCell("PATIENT ID", COL_PATIENT), COL_PATIENT),
                wrapCell(headerCell("DATE", COL_DATE), COL_DATE),
                wrapCell(headerCell("TIME", COL_TIME), COL_TIME),
                wrapCell(headerCell("STATUS", COL_STATUS), COL_STATUS),
                wrapCell(headerCell("ACTION", COL_ACTION), COL_ACTION));

        return header;
    }

    public HBox locationHeaderCell() {
        HBox box = new HBox(headerCell("LOCATION (FROM → TO)", COL_LOCATION));
        box.setPrefWidth(COL_LOCATION);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    public Text headerCell(String label, double width) {
        Text text = new Text(label);
        text.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : #8F3F5B;");
        return text;
    }

    public void addTripRow(String srNo, String tripId, String location, String patientId, String date, String time, String status) {

        HBox row = new HBox();
        row.setPadding(new Insets(13, 16, 13, 16));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + DIVIDER_COLOR + " transparent transparent transparent; -fx-border-width : 1 0 0 0; -fx-cursor : hand;");

        Text srText = rowCell(srNo, SECONDARY_TEXT, false);
        Text tripText = rowCell(tripId, PRIMARY_PINK, true);

        HBox locationBox = new HBox(6);
        locationBox.setPrefWidth(COL_LOCATION);
        locationBox.setAlignment(Pos.CENTER_LEFT);
        Text locationText = new Text(location);
        locationText.setStyle("-fx-font-size : 11px; -fx-fill : " + PRIMARY_TEXT + ";");
        locationBox.getChildren().add(locationText);

        Text patientText = rowCell(patientId, PRIMARY_TEXT, false);
        Text dateText = rowCell(date, SECONDARY_TEXT, false);
        Text timeText = rowCell(time, MUTED_TEXT, false);

        Text statusBadge = new Text(status);
        statusBadge.setStyle(badgeStyle(status));
        StackPane statusCell = new StackPane(statusBadge);
        statusCell.setPrefWidth(COL_STATUS);
        statusCell.setAlignment(Pos.CENTER_LEFT);

        Text actionText = new Text("View Details");
        actionText.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + ";");
        StackPane actionCell = new StackPane(actionText);
        actionCell.setPrefWidth(COL_ACTION);
        actionCell.setAlignment(Pos.CENTER_LEFT);

        StackPane srCell = wrapCell(srText, COL_SR);
        StackPane tripCell = wrapCell(tripText, COL_TRIP);
        StackPane patientCell = wrapCell(patientText, COL_PATIENT);
        StackPane dateCell = wrapCell(dateText, COL_DATE);
        StackPane timeCell = wrapCell(timeText, COL_TIME);

        row.getChildren().addAll(srCell, tripCell, locationBox, patientCell, dateCell, timeCell, statusCell, actionCell);

        row.setOnMouseEntered(e ->
                row.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-border-color : " + DIVIDER_COLOR + " transparent transparent transparent; -fx-border-width : 1 0 0 0; -fx-cursor : hand;")
        );

        row.setOnMouseExited(e ->
                row.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + DIVIDER_COLOR + " transparent transparent transparent; -fx-border-width : 1 0 0 0; -fx-cursor : hand;")
        );

        row.setOnMouseClicked(e -> showTripDetailsModal(tripId, location, patientId, date, time, status));

        tableBody.getChildren().add(row);
        rowNodes.add(row);
        rowData.add(new String[] { tripId, patientId, status });
    }

    public Text rowCell(String value, String color, boolean bold) {
        Text text = new Text(value);
        text.setStyle("-fx-font-size : 11px; " + (bold ? "-fx-font-weight : bold; " : "") + "-fx-fill : " + color + ";");
        return text;
    }

    public StackPane wrapCell(Text text, double width) {
        StackPane cell = new StackPane(text);
        cell.setPrefWidth(width);
        cell.setAlignment(Pos.CENTER_LEFT);
        return cell;
    }

    public String badgeStyle(String status) {

        String bg = "#F5F1F3";
        String fg = "#665960";

        if (status.equals("Completed")) {
            bg = "#E7F5ED";
            fg = "#258052";
        } else if (status.equals("In Progress")) {
            bg = VERY_LIGHT_PINK;
            fg = PRIMARY_PINK;
        } else if (status.equals("Scheduled")) {
            bg = "#FFF3D8";
            fg = "#B77900";
        } else if (status.equals("Cancelled")) {
            bg = "#FCE8E7";
            fg = "#C94F4F";
        }

        return "-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + fg + "; -fx-background-color : " + bg + "; -fx-padding : 3 8 3 8; -fx-background-radius : 8;";
    }

    public void filterTrips(String searchText, String status) {

        String query = searchText == null ? "" : searchText.toLowerCase().trim();
        boolean anyVisible = false;

        for (int i = 0; i < rowNodes.size(); i++) {
            HBox row = rowNodes.get(i);
            String[] data = rowData.get(i);

            boolean matchesSearch = query.isEmpty()
                    || data[0].toLowerCase().contains(query)
                    || data[1].toLowerCase().contains(query);

            boolean matchesStatus = status == null || status.equals("All Status") || data[2].equals(status);
            boolean visible = matchesSearch && matchesStatus;

            row.setVisible(visible);
            row.setManaged(visible);

            if (visible) {
                anyVisible = true;
            }
        }

        emptyState.setVisible(!anyVisible);
        emptyState.setManaged(!anyVisible);
    }

    public HBox buildPaginationRow() {

        HBox pagination = new HBox(14);
        pagination.setAlignment(Pos.CENTER_LEFT);
        pagination.setPadding(new Insets(4, 6, 0, 6));

        Text summary = new Text("Showing 1–5 of 24 trips");
        summary.setStyle("-fx-font-size : 10px; -fx-fill : " + MUTED_TEXT + ";");

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        Text prev = pageLink("Previous", false);
        Text pageOne = pageLink("1", true);
        Text pageTwo = pageLink("2", false);
        Text pageThree = pageLink("3", false);
        Text next = pageLink("Next", false);

        HBox pageLinks = new HBox(10, prev, pageOne, pageTwo, pageThree, next);
        pageLinks.setAlignment(Pos.CENTER_LEFT);

        pagination.getChildren().addAll(summary, space, pageLinks);
        return pagination;
    }

    public Text pageLink(String label, boolean active) {

        Text text = new Text(label);
        text.setStyle(active
                ? "-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_PINK + "; -fx-cursor : hand;"
                : "-fx-font-size : 11px; -fx-fill : " + MUTED_TEXT + "; -fx-cursor : hand;");

        text.setOnMouseClicked(e -> System.out.println("Pagination clicked: " + label));
        return text;
    }

    public VBox buildEmptyState() {

        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(50));
        box.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 14; -fx-background-radius : 14;");

        Text icon = new Text("⇄");
        icon.setStyle("-fx-font-size : 30px; -fx-fill : " + SOFT_PINK + ";");

        Text title = new Text("No trips found");
        title.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text subtitle = new Text("Your patient trips will appear here.");
        subtitle.setStyle("-fx-font-size : 11px; -fx-fill : " + SECONDARY_TEXT + ";");

        Button createButton = new Button("+  New Trip");
        createButton.setPrefHeight(38);
        createButton.setPrefWidth(140);
        createButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;");

        createButton.setOnAction(e -> showNewTripModal());
        box.getChildren().addAll(icon, title, subtitle, createButton);

        return box;
    }

    public void showTripDetailsModal(String tripId, String location, String patientId, String date, String time, String status) {

        StackPane dimmer = new StackPane();
        dimmer.setStyle("-fx-background-color : rgba(43,34,38,0.45);");

        VBox modal = new VBox(14);
        modal.setMaxWidth(360);
        modal.setPadding(new Insets(24));
        modal.setStyle("-fx-background-color : white; -fx-background-radius : 16; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(43,34,38,0.25), 20, 0, 0, 8);");
        modal.setOpacity(0);
        modal.setScaleX(0.92);
        modal.setScaleY(0.92);

        Text modalTitle = new Text("Trip Details — " + tripId);
        modalTitle.setStyle("-fx-font-size : 15px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text badge = new Text(status);
        badge.setStyle(badgeStyle(status));
        StackPane badgeWrap = new StackPane(badge);
        badgeWrap.setAlignment(Pos.CENTER_LEFT);

        VBox infoSection = new VBox(6,
                modalRow("Trip ID", tripId),
                modalRow("Patient ID", patientId),
                modalRow("Date", date),
                modalRow("Time", time));

        Text locationLabel = new Text("LOCATION");
        locationLabel.setStyle("-fx-font-size : 9px; -fx-font-weight : bold; -fx-fill : " + MUTED_TEXT + ";");

        Text locationText = new Text(location);
        locationText.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        VBox locationBox = new VBox(4, locationLabel, locationText);

        HBox actionsRow = new HBox(10);
        actionsRow.setAlignment(Pos.CENTER_RIGHT);

        Button closeButton = new Button("Close");
        closeButton.setPrefWidth(100);
        closeButton.setPrefHeight(36);
        closeButton.setStyle("-fx-background-color : " + SURFACE + "; -fx-border-color : " + BORDER_COLOR + "; -fx-text-fill : " + SECONDARY_TEXT + "; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-border-radius : 8; -fx-cursor : hand;");
        closeButton.setOnAction(e -> hideModal(dimmer));

        actionsRow.getChildren().add(closeButton);

        if (status.equals("In Progress")) {
            Button trackButton = new Button("View Current Trip");
            trackButton.setPrefWidth(150);
            trackButton.setPrefHeight(36);
            trackButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");
            trackButton.setOnAction(e -> {
                System.out.println("View Current Trip clicked: " + tripId);
                NurseToast.show(NurseDashboardPage.appOverlay, "Tracking Trip " + tripId, "info");
            });

            actionsRow.getChildren().add(0, trackButton);
        }

        modal.getChildren().addAll(modalTitle, badgeWrap, locationBox, infoSection, actionsRow);

        dimmer.getChildren().add(modal);
        dimmer.setOnMouseClicked(e -> {
            if (e.getTarget() == dimmer) {
                hideModal(dimmer);
            }
        });

        pageStack.getChildren().add(dimmer);

        FadeTransition dimIn = new FadeTransition(NurseAppSettings.dur(180), dimmer);
        dimIn.setFromValue(0);
        dimIn.setToValue(1);

        FadeTransition modalFadeIn = new FadeTransition(NurseAppSettings.dur(200), modal);
        modalFadeIn.setToValue(1);

        ScaleTransition modalScaleIn = new ScaleTransition(NurseAppSettings.dur(200), modal);
        modalScaleIn.setToX(1);
        modalScaleIn.setToY(1);

        new ParallelTransition(dimIn, modalFadeIn, modalScaleIn).play();
    }

    public HBox modalRow(String label, String value) {

        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        Text labelText = new Text(label);
        labelText.setStyle("-fx-font-size : 10px; -fx-fill : " + MUTED_TEXT + ";");

        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        Text valueText = new Text(value);
        valueText.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        row.getChildren().addAll(labelText, space, valueText);
        return row;
    }

    public void showNewTripModal() {

        StackPane dimmer = new StackPane();
        dimmer.setStyle("-fx-background-color : rgba(43,34,38,0.45);");

        VBox modal = new VBox(12);
        modal.setMaxWidth(360);
        modal.setPadding(new Insets(24));
        modal.setStyle("-fx-background-color : white; -fx-background-radius : 16; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(43,34,38,0.25), 20, 0, 0, 8);");
        modal.setOpacity(0);
        modal.setScaleX(0.92);
        modal.setScaleY(0.92);

        Text modalTitle = new Text("New Trip");
        modalTitle.setStyle("-fx-font-size : 16px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        TextField patientField = formField("Patient ID");
        TextField fromField = formField("From Location");
        TextField toField = formField("To Location");

        DatePicker tripDate = new DatePicker();
        tripDate.setPromptText("Date");
        tripDate.setPrefHeight(34);
        tripDate.setMaxWidth(Double.MAX_VALUE);
        tripDate.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 8; -fx-background-radius : 8; -fx-font-size : 11px;");

        TextField timeField = formField("Time (e.g. 10:30 AM)");

        Button createButton = new Button("Create Trip");
        createButton.setPrefHeight(38);
        createButton.setMaxWidth(Double.MAX_VALUE);
        createButton.setStyle("-fx-background-color : " + PRIMARY_PINK + "; -fx-text-fill : white; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 9; -fx-cursor : hand;");

        createButton.setOnAction(e -> {
            System.out.println("Create Trip button clicked");
            hideModal(dimmer);
            NurseToast.show(NurseDashboardPage.appOverlay, "Trip created successfully.", "success");
        });

        modal.getChildren().addAll(modalTitle, patientField, fromField, toField, tripDate, timeField, createButton);

        dimmer.getChildren().add(modal);
        dimmer.setOnMouseClicked(e -> {
            if (e.getTarget() == dimmer) {
                hideModal(dimmer);
            }
        });

        pageStack.getChildren().add(dimmer);

        FadeTransition dimIn = new FadeTransition(NurseAppSettings.dur(180), dimmer);
        dimIn.setFromValue(0);
        dimIn.setToValue(1);

        FadeTransition modalFadeIn = new FadeTransition(NurseAppSettings.dur(200), modal);
        modalFadeIn.setToValue(1);

        ScaleTransition modalScaleIn = new ScaleTransition(NurseAppSettings.dur(200), modal);
        modalScaleIn.setToX(1);
        modalScaleIn.setToY(1);

        new ParallelTransition(dimIn, modalFadeIn, modalScaleIn).play();
    }

    public TextField formField(String hint) {

        TextField field = new TextField();
        field.setPromptText(hint);
        field.setPrefHeight(34);
        field.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 8; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 8; -fx-font-size : 11px; -fx-text-fill : " + PRIMARY_TEXT + ";");

        field.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused) {
                field.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 8; -fx-border-color : " + PRIMARY_PINK + "; -fx-border-width : 2; -fx-border-radius : 8; -fx-font-size : 11px; -fx-text-fill : " + PRIMARY_TEXT + ";");
            } else {
                field.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 8; -fx-border-color : " + BORDER_COLOR + "; -fx-border-radius : 8; -fx-font-size : 11px; -fx-text-fill : " + PRIMARY_TEXT + ";");
            }
        });

        return field;
    }

    public void hideModal(StackPane dimmer) {
        FadeTransition dimOut = new FadeTransition(NurseAppSettings.dur(160), dimmer);
        dimOut.setToValue(0);
        dimOut.setOnFinished(e -> pageStack.getChildren().remove(dimmer));
        dimOut.play();
    }
}