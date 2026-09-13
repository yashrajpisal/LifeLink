// package com.kurukshetra.view.hospital;

// import com.kurukshetra.controller.hospitalController.DoctorController;
// import com.kurukshetra.model.hospitalModel.DoctorModel;
// import com.kurukshetra.view.util.ShimmerLoader;

// import javafx.application.Platform;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.ComboBox;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TextField;
// import javafx.scene.effect.DropShadow;
// import javafx.scene.layout.GridPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Circle;
// import javafx.scene.text.Text;
// import javafx.stage.Modality;
// import javafx.stage.Stage;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;
// import java.util.stream.Collectors;

// public class HospitalDoctorManagement {

//     // =========================================================================
//     // DESIGN SYSTEM CONSTANTS (MATCHING HospitalDashboard.java)
//     // =========================================================================
//     private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";
//     private static final String PRIMARY_TEAL = "#006591";
//     private static final String TEAL_HOVER = "#004F72";
//     private static final String PAGE_BG = "#a5bdaaff ";
//     private static final String SURFACE = "#FFFFFF";
//     private static final String BORDER_COLOR = "#E2E8F0";
//     private static final String TEXT_PRIMARY = "#0F172A";
//     private static final String TEXT_SECONDARY = "#475569";
//     private static final String TEXT_MUTED = "#64748B";

//     private static final String SUCCESS_BG = "#ECFDF5";
//     private static final String SUCCESS_TEXT = "#059669";
//     private static final String SUCCESS_BORDER = "#A7F3D0";

//     private static final String DANGER_BG = "#FEF2F2";
//     private static final String DANGER_TEXT = "#DC2626";
//     private static final String DANGER_BORDER = "#FECDD3";

//     private static final String WARNING_BG = "#FFFBEB";
//     private static final String WARNING_TEXT = "#D97706";
//     private static final String WARNING_BORDER = "#FDE68A";

//     private static final String INFO_BG = "#E0F2FE";
//     private static final String INFO_TEXT = "#0369A1";
//     private static final String INFO_BORDER = "#BAE6FD";

//     private static final String INDIGO_BG = "#EEF2FF";
//     private static final String INDIGO_TEXT = "#4F46E5";
//     private static final String INDIGO_BORDER = "#C7D2FE";

//     private static final String CARD_STYLE =
//             "-fx-background-color: " + SURFACE + "; " +
//             "-fx-border-color: " + BORDER_COLOR + "; " +
//             "-fx-border-radius: 14px; " +
//             "-fx-background-radius: 14px; " +
//             "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 14, 0, 0, 3);";

//     private final String hospitalId;
//     private final DoctorController doctorController = new DoctorController();
//     private final List<String> selectedDoctors = new ArrayList<>();
//     private final List<DoctorModel> cachedDoctorsList = new ArrayList<>();

//     // UI references
//     private VBox doctorGridContainer;
//     private ShimmerLoader.ShimmerPane doctorGridShimmer;
//     private Text availableValue;
//     private Text busyValue;
//     private Text leaveValue;
//     private Text selectedValue;

//     private Label doctorCountLabel;
//     private Button selectedButton;
//     private Label selectedCount;
//     private HBox selectedNamesBox;

//     private TextField searchField;
//     private ComboBox<String> statusFilterBox;

//     public HospitalDoctorManagement(String hospitalId) {
//         this.hospitalId = hospitalId;
//     }

//     public VBox getDoctorManagement() {

//         VBox mainContent = new VBox(22);
//         mainContent.setPadding(new Insets(26, 32, 36, 32));
//         mainContent.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

//         // =====================================================================
//         // 1. TOP HEADER (TITLE & ACTION BUTTONS)
//         // =====================================================================
//         HBox header = new HBox(16);
//         header.setAlignment(Pos.CENTER_LEFT);

//         VBox headingBox = new VBox(4);
//         Text heading = new Text("Doctors & Surgical Specialists");
//         heading.setStyle(FONT_FAMILY + "-fx-font-size: 26px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");
//         headingBox.getChildren().addAll(heading);

//         Region headerSpacer = new Region();
//         HBox.setHgrow(headerSpacer, Priority.ALWAYS);

//         // Selected Counter Button
//         selectedButton = new Button("Selected Roster (0)");
//         selectedButton.setPrefHeight(42);
//         selectedButton.setStyle(
//                 FONT_FAMILY +
//                 "-fx-background-color: " + INFO_BG + "; " +
//                 "-fx-text-fill: " + INFO_TEXT + "; " +
//                 "-fx-font-size: 12.5px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-background-radius: 10px; " +
//                 "-fx-border-color: " + INFO_BORDER + "; " +
//                 "-fx-border-radius: 10px; " +
//                 "-fx-padding: 0 16; " +
//                 "-fx-cursor: hand;"
//         );

//         // Add Doctor Button
//         Button addDoctorButton = new Button("+ Add Specialist");
//         addDoctorButton.setPrefHeight(42);
//         addDoctorButton.setStyle(
//                 FONT_FAMILY +
//                 "-fx-background-color: " + PRIMARY_TEAL + "; " +
//                 "-fx-text-fill: #FFFFFF; " +
//                 "-fx-font-size: 12.5px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-background-radius: 10px; " +
//                 "-fx-padding: 0 18; " +
//                 "-fx-cursor: hand;"
//         );
//         addDoctorButton.setEffect(new DropShadow(10, 0, 2, Color.rgb(0, 101, 145, 0.25)));

//         addDoctorButton.setOnMouseEntered(e -> {
//             addDoctorButton.setStyle(
//                     FONT_FAMILY +
//                     "-fx-background-color: " + TEAL_HOVER + "; " +
//                     "-fx-text-fill: #FFFFFF; " +
//                     "-fx-font-size: 12.5px; " +
//                     "-fx-font-weight: bold; " +
//                     "-fx-background-radius: 10px; " +
//                     "-fx-padding: 0 18; " +
//                     "-fx-cursor: hand;"
//             );
//             addDoctorButton.setTranslateY(-2);
//         });
//         addDoctorButton.setOnMouseExited(e -> {
//             addDoctorButton.setStyle(
//                     FONT_FAMILY +
//                     "-fx-background-color: " + PRIMARY_TEAL + "; " +
//                     "-fx-text-fill: #FFFFFF; " +
//                     "-fx-font-size: 12.5px; " +
//                     "-fx-font-weight: bold; " +
//                     "-fx-background-radius: 10px; " +
//                     "-fx-padding: 0 18; " +
//                     "-fx-cursor: hand;"
//             );
//             addDoctorButton.setTranslateY(0);
//         });

//         addDoctorButton.setOnAction(e -> showAddDoctorDialog());

//         header.getChildren().addAll(headingBox, headerSpacer, selectedButton, addDoctorButton);

//         // =====================================================================
//         // 2. SELECTED DOCTORS BANNER
//         // =====================================================================
//         VBox selectedDoctorsBox = new VBox(10);
//         selectedDoctorsBox.setPadding(new Insets(16, 20, 16, 20));
//         selectedDoctorsBox.setStyle(
//                 "-fx-background-color: #FFFFFF; " +
//                 "-fx-background-radius: 14px; " +
//                 "-fx-border-color: " + INFO_BORDER + "; " +
//                 "-fx-border-radius: 14px; " +
//                 "-fx-effect: dropshadow(three-pass-box, rgba(3,105,161,0.06), 14, 0, 0, 3);"
//         );

//         HBox selectedHeader = new HBox(10);
//         selectedHeader.setAlignment(Pos.CENTER_LEFT);

//         StackPane selIconHolder = new StackPane();
//         selIconHolder.setPrefSize(28, 28);
//         selIconHolder.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 7px;");
//         Label selIcon = new Label("📋");
//         selIcon.setStyle("-fx-font-size: 13px;");
//         selIconHolder.getChildren().add(selIcon);

//         Text selectedTitle = new Text("Active Surgical & Duty Roster Selection");
//         selectedTitle.setStyle(FONT_FAMILY + "-fx-font-size: 14.5px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

//         Region selectedSpacer = new Region();
//         HBox.setHgrow(selectedSpacer, Priority.ALWAYS);

//         selectedCount = new Label("0 Selected");
//         selectedCount.setStyle(
//                 FONT_FAMILY +
//                 "-fx-background-color: " + INFO_BG + "; " +
//                 "-fx-text-fill: " + INFO_TEXT + "; " +
//                 "-fx-font-size: 11px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-padding: 4px 10px; " +
//                 "-fx-background-radius: 6px;"
//         );

//         selectedHeader.getChildren().addAll(selIconHolder, selectedTitle, selectedSpacer, selectedCount);

//         selectedNamesBox = new HBox(8);
//         selectedNamesBox.setAlignment(Pos.CENTER_LEFT);

//         Label emptySelection = new Label("No specialists selected for current procedure yet. Click '+ Select' on available doctors below.");
//         emptySelection.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED + ";");
//         selectedNamesBox.getChildren().add(emptySelection);

//         selectedDoctorsBox.getChildren().addAll(selectedHeader, selectedNamesBox);

//         // =====================================================================
//         // 3. 4-CARD SUMMARY METRIC STRIP (MATCHING HospitalDashboard.java)
//         // =====================================================================
//         HBox summaryRow = new HBox(16);

//         availableValue = new Text("0");
//         VBox availableBox = createMetricCard("✓", SUCCESS_TEXT, SUCCESS_BG, "AVAILABLE DOCTORS", availableValue, "READY FOR DUTY", SUCCESS_BG, SUCCESS_TEXT);

//         busyValue = new Text("0");
//         VBox busyBox = createMetricCard("⚡", DANGER_TEXT, DANGER_BG, "BUSY / IN SURGERY", busyValue, "OCCUPIED", DANGER_BG, DANGER_TEXT);

//         leaveValue = new Text("0");
//         VBox leaveBox = createMetricCard("🏖", WARNING_TEXT, WARNING_BG, "ON LEAVE", leaveValue, "OFF DUTY", WARNING_BG, WARNING_TEXT);

//         selectedValue = new Text("0");
//         VBox selectedSummaryBox = createMetricCard("📋", INFO_TEXT, INFO_BG, "SELECTED FOR DUTY", selectedValue, "ASSIGNED", INFO_BG, INFO_TEXT);

//         summaryRow.getChildren().addAll(availableBox, busyBox, leaveBox, selectedSummaryBox);

//         // =====================================================================
//         // 4. SECTION DIRECTORY HEADER WITH SEARCH & STATUS FILTER
//         // =====================================================================
//         VBox directorySection = new VBox(14);

//         HBox directoryHeader = new HBox(14);
//         directoryHeader.setAlignment(Pos.CENTER_LEFT);

//         Text doctorsTitle = new Text("Medical Specialists Directory");
//         doctorsTitle.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

//         doctorCountLabel = new Label("0 Specialists");
//         doctorCountLabel.setStyle(
//                 FONT_FAMILY +
//                 "-fx-background-color: #F1F5F9; " +
//                 "-fx-text-fill: " + TEXT_MUTED + "; " +
//                 "-fx-font-size: 11px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-padding: 4px 10px; " +
//                 "-fx-background-radius: 8px; " +
//                 "-fx-border-color: " + BORDER_COLOR + "; " +
//                 "-fx-border-radius: 8px;"
//         );

//         Region doctorSpacer = new Region();
//         HBox.setHgrow(doctorSpacer, Priority.ALWAYS);

//         // Search Field
//         HBox searchContainer = new HBox(10);
//         searchContainer.setAlignment(Pos.CENTER_LEFT);
//         searchContainer.setPadding(new Insets(0, 16, 0, 16));
//         searchContainer.setPrefHeight(42);
//         searchContainer.setPrefWidth(320);
//         searchContainer.setStyle(
//                 "-fx-background-color: #F8FAFC; " +
//                 "-fx-border-color: #CBD5E1; " +
//                 "-fx-border-radius: 20px; " +
//                 "-fx-background-radius: 20px; " +
//                 "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 4, 0, 0, 2);"
//         );

//         Label searchIcon = new Label("🔍");
//         searchIcon.setStyle("-fx-font-size: 14px; -fx-text-fill: " + TEXT_MUTED + ";");

//         searchField = new TextField();
//         searchField.setPromptText("Search name, specialization, ID...");
//         searchField.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-font-size: 13.5px; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-prompt-text-fill: " + TEXT_MUTED + "; -fx-padding: 0;");
//         HBox.setHgrow(searchField, Priority.ALWAYS);

//         searchField.focusedProperty().addListener((obs, oldV, isFocused) -> {
//             if (isFocused) {
//                 searchContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + PRIMARY_TEAL + "; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-border-width: 1.5px; -fx-effect: dropshadow(three-pass-box, rgba(0,101,145,0.1), 8, 0, 0, 2);");
//             } else {
//                 searchContainer.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 4, 0, 0, 2);");
//             }
//         });

//         searchContainer.getChildren().addAll(searchIcon, searchField);

//         // searchField.focusedProperty().addListener((obs, oldV, isFocused) -> {
//         //     if (isFocused) {
//         //         searchContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + PRIMARY_TEAL + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");
//         //     } else {
//         //         searchContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");
//         //     }
//         // });

//         searchField.textProperty().addListener((obs, oldV, newV) -> filterAndRenderGrid());

//         // Status Filter Dropdown
//         statusFilterBox = new ComboBox<>();
//         statusFilterBox.getItems().addAll("All Statuses", "Available", "Busy", "On Leave");
//         statusFilterBox.setValue("All Statuses");
//         statusFilterBox.setPrefHeight(38);
//         statusFilterBox.setStyle(
//                 FONT_FAMILY +
//                 "-fx-background-color: #FFFFFF; " +
//                 "-fx-border-color: " + BORDER_COLOR + "; " +
//                 "-fx-border-radius: 8px; " +
//                 "-fx-background-radius: 8px; " +
//                 "-fx-font-size: 12px;"
//         );
//         statusFilterBox.valueProperty().addListener((obs, oldV, newV) -> filterAndRenderGrid());

//         directoryHeader.getChildren().addAll(doctorsTitle, doctorCountLabel, doctorSpacer, searchContainer, statusFilterBox);

//         // 5. DOCTOR GRID CONTAINER
//         doctorGridContainer = new VBox(16);
//         doctorGridShimmer = ShimmerLoader.createDoctorGridSkeleton(1100, 6);
//         doctorGridContainer.getChildren().add(doctorGridShimmer);

//         directorySection.getChildren().addAll(directoryHeader, doctorGridContainer);

//         mainContent.getChildren().addAll(
//                 header,
//                 selectedDoctorsBox,
//                 summaryRow,
//                 directorySection
//         );

//         ScrollPane scrollPane = new ScrollPane(mainContent);
//         scrollPane.setFitToWidth(true);
//         scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//         scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
//         scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");

//         VBox finalContent = new VBox(scrollPane);
//         finalContent.setStyle("-fx-background-color: " + PAGE_BG + ";");
//         VBox.setVgrow(scrollPane, Priority.ALWAYS);

//         loadDoctors();

//         return finalContent;
//     }

//     private VBox createMetricCard(String iconEmoji, String iconTextColor, String iconBgColor,
//                                   String title, Text valueNode, String badgeText,
//                                   String badgeBg, String badgeTextColor) {
//         VBox card = new VBox(10);
//         card.setPadding(new Insets(16, 20, 16, 20));
//         card.setPrefHeight(125);
//         card.setStyle(CARD_STYLE);
//         HBox.setHgrow(card, Priority.ALWAYS);

//         card.setOnMouseEntered(e -> {
//             card.setTranslateY(-3);
//             card.setStyle(
//                 "-fx-background-color: #FFFFFF; " +
//                 "-fx-border-color: #CBD5E1; " +
//                 "-fx-border-radius: 14px; " +
//                 "-fx-background-radius: 14px; " +
//                 "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.09), 18, 0, 0, 6);"
//             );
//         });
//         card.setOnMouseExited(e -> {
//             card.setTranslateY(0);
//             card.setStyle(CARD_STYLE);
//         });

//         HBox topRow = new HBox(8);
//         topRow.setAlignment(Pos.CENTER_LEFT);

//         StackPane iconPane = new StackPane();
//         iconPane.setPrefSize(34, 34);
//         iconPane.setMinSize(34, 34);
//         iconPane.setStyle("-fx-background-color: " + iconBgColor + "; -fx-background-radius: 8px;");
//         Text iconText = new Text(iconEmoji);
//         iconText.setStyle("-fx-font-size: 15px; -fx-fill: " + iconTextColor + "; -fx-font-weight: bold;");
//         iconPane.getChildren().add(iconText);

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         Label badge = new Label(badgeText);
//         badge.setStyle(
//             FONT_FAMILY +
//             "-fx-background-color: " + badgeBg + "; " +
//             "-fx-text-fill: " + badgeTextColor + "; " +
//             "-fx-font-size: 9.5px; " +
//             "-fx-font-weight: bold; " +
//             "-fx-padding: 3px 8px; " +
//             "-fx-background-radius: 6px;"
//         );
//         topRow.getChildren().addAll(iconPane, spacer, badge);

//         valueNode.setStyle(FONT_FAMILY + "-fx-font-size: 26px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");

//         Text labelText = new Text(title);
//         labelText.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: 600; -fx-fill: " + TEXT_MUTED + "; -fx-letter-spacing: 0.4px;");

//         card.getChildren().addAll(topRow, valueNode, labelText);
//         return card;
//     }

//     private void loadDoctors() {
//         doctorController.listenToDoctors(hospitalId, doctors -> {
//             Platform.runLater(() -> {
//                 cachedDoctorsList.clear();
//                 if (doctors != null) {
//                     cachedDoctorsList.addAll(doctors);
//                 }

//                 int available = 0;
//                 int busy = 0;
//                 int leave = 0;

//                 for (DoctorModel doc : cachedDoctorsList) {
//                     String st = doc.getStatus();
//                     if ("Available".equalsIgnoreCase(st)) {
//                         available++;
//                     } else if ("Busy".equalsIgnoreCase(st) || "In Surgery".equalsIgnoreCase(st) || "Assigned".equalsIgnoreCase(st)) {
//                         busy++;
//                     } else if ("On Leave".equalsIgnoreCase(st)) {
//                         leave++;
//                     }
//                 }

//                 doctorCountLabel.setText(cachedDoctorsList.size() + " Specialists");
//                 availableValue.setText(String.valueOf(available));
//                 busyValue.setText(String.valueOf(busy));
//                 leaveValue.setText(String.valueOf(leave));

//                 filterAndRenderGrid();
//             });
//         });
//     }

//     private void filterAndRenderGrid() {
//         if (doctorGridContainer == null) return;
//         if (doctorGridShimmer != null) {
//             doctorGridShimmer.stop();
//             doctorGridShimmer = null;
//         }
//         doctorGridContainer.getChildren().clear();

//         String query = (searchField != null && searchField.getText() != null)
//                 ? searchField.getText().trim().toLowerCase() : "";
//         String statusFilter = (statusFilterBox != null && statusFilterBox.getValue() != null)
//                 ? statusFilterBox.getValue() : "All Statuses";

//         List<DoctorModel> filtered = cachedDoctorsList.stream().filter(doc -> {
//             boolean matchesQuery = query.isEmpty()
//                     || (doc.getDoctorName() != null && doc.getDoctorName().toLowerCase().contains(query))
//                     || (doc.getDoctorId() != null && doc.getDoctorId().toLowerCase().contains(query))
//                     || (doc.getSpecialization() != null && doc.getSpecialization().toLowerCase().contains(query));

//             boolean matchesStatus = "All Statuses".equalsIgnoreCase(statusFilter)
//                     || (doc.getStatus() != null && doc.getStatus().equalsIgnoreCase(statusFilter))
//                     || ("Busy".equalsIgnoreCase(statusFilter) && ("In Surgery".equalsIgnoreCase(doc.getStatus()) || "Assigned".equalsIgnoreCase(doc.getStatus())));

//             return matchesQuery && matchesStatus;
//         }).collect(Collectors.toList());

//         if (filtered.isEmpty()) {
//             VBox emptyBox = new VBox(10);
//             emptyBox.setAlignment(Pos.CENTER);
//             emptyBox.setPadding(new Insets(40));
//             emptyBox.setStyle(CARD_STYLE);

//             Label emptyLbl = new Label("No doctors match the selected search and filter criteria.");
//             emptyLbl.setStyle(FONT_FAMILY + "-fx-font-size: 13.5px; -fx-text-fill: " + TEXT_MUTED + ";");
//             emptyBox.getChildren().add(emptyLbl);
//             doctorGridContainer.getChildren().add(emptyBox);
//             return;
//         }

//         GridPane grid = new GridPane();
//         grid.setHgap(18);
//         grid.setVgap(18);

//         for (int i = 0; i < filtered.size(); i++) {
//             DoctorModel doctor = filtered.get(i);
//             VBox card = createDoctorCard(doctor);
//             grid.add(card, i % 3, i / 3);
//         }

//         doctorGridContainer.getChildren().add(grid);
//     }

//     // =========================================================================
//     // ELEVATED DOCTOR PROFILE CARD (3-COLUMN GRID ITEM)
//     // =========================================================================
//     private VBox createDoctorCard(DoctorModel doctor) {
//         boolean available = "Available".equalsIgnoreCase(doctor.getStatus());
//         boolean isSelected = selectedDoctors.contains(doctor.getDoctorId());

//         VBox card = new VBox(14);
//         card.setPadding(new Insets(20));
//         card.setPrefWidth(340);
//         card.setPrefHeight(275);
//         card.setMinHeight(275);
//         card.setMaxHeight(275);
//         card.setStyle(CARD_STYLE);

//         card.setOnMouseEntered(e -> {
//             card.setTranslateY(-3);
//             card.setStyle(
//                 "-fx-background-color: #FFFFFF; " +
//                 "-fx-border-color: #CBD5E1; " +
//                 "-fx-border-radius: 14px; " +
//                 "-fx-background-radius: 14px; " +
//                 "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.09), 18, 0, 0, 6);"
//             );
//         });
//         card.setOnMouseExited(e -> {
//             card.setTranslateY(0);
//             card.setStyle(CARD_STYLE);
//         });

//         // Top Row: Avatar + Name + Status Badge
//         HBox top = new HBox(12);
//         top.setAlignment(Pos.CENTER_LEFT);

//         String initials = getInitials(doctor.getDoctorName());
//         StackPane avatarPane = new StackPane();
//         avatarPane.setPrefSize(44, 44);
//         avatarPane.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 12px; -fx-border-color: " + INFO_BORDER + "; -fx-border-radius: 12px;");

//         Text initialsText = new Text(initials);
//         initialsText.setStyle(FONT_FAMILY + "-fx-font-size: 13.5px; -fx-font-weight: 800; -fx-fill: " + PRIMARY_TEAL + ";");
//         avatarPane.getChildren().add(initialsText);

//         VBox nameBox = new VBox(2);
//         Text nameText = new Text(doctor.getDoctorName() != null ? doctor.getDoctorName() : "Doctor");
//         nameText.setStyle(FONT_FAMILY + "-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

//         Label specBadge = new Label(doctor.getSpecialization() != null ? doctor.getSpecialization() : "General Medicine");
//         specBadge.setStyle(
//                 FONT_FAMILY +
//                 "-fx-background-color: " + INDIGO_BG + "; " +
//                 "-fx-text-fill: " + INDIGO_TEXT + "; " +
//                 "-fx-font-size: 10.5px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-padding: 2px 8px; " +
//                 "-fx-background-radius: 6px;"
//         );
//         nameBox.getChildren().addAll(nameText, specBadge);

//         Region topSpacer = new Region();
//         HBox.setHgrow(topSpacer, Priority.ALWAYS);

//         Label statusLabel = new Label("● " + (doctor.getStatus() != null ? doctor.getStatus() : "Available"));
//         applyStatusStyle(statusLabel, doctor.getStatus());

//         top.getChildren().addAll(avatarPane, nameBox, topSpacer, statusLabel);

//         // Middle Section: ID & Shift details
//         HBox detailsGrid = new HBox(14);
//         detailsGrid.setPadding(new Insets(10, 12, 10, 12));
//         detailsGrid.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 10px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 10px;");

//         VBox idBox = new VBox(2);
//         Text idTitle = new Text("DOCTOR ID");
//         idTitle.setStyle(FONT_FAMILY + "-fx-font-size: 9.5px; -fx-font-weight: bold; -fx-fill: " + TEXT_MUTED + "; -fx-letter-spacing: 0.4px;");
//         Text idText = new Text(doctor.getDoctorId());
//         idText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: 600; -fx-fill: " + TEXT_PRIMARY + ";");
//         idBox.getChildren().addAll(idTitle, idText);

//         Region splitSpacer = new Region();
//         HBox.setHgrow(splitSpacer, Priority.ALWAYS);

//         VBox shiftBox = new VBox(2);
//         shiftBox.setAlignment(Pos.TOP_RIGHT);
//         Text shiftTitle = new Text("SHIFT SCHEDULE");
//         shiftTitle.setStyle(FONT_FAMILY + "-fx-font-size: 9.5px; -fx-font-weight: bold; -fx-fill: " + TEXT_MUTED + "; -fx-letter-spacing: 0.4px;");
//         Text shiftText = new Text("🕒 " + (doctor.getShift() != null ? doctor.getShift() : "Standard Shift"));
//         shiftText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: 600; -fx-fill: " + TEXT_PRIMARY + ";");
//         shiftBox.getChildren().addAll(shiftTitle, shiftText);

//         detailsGrid.getChildren().addAll(idBox, splitSpacer, shiftBox);

//         Region cardSpacer = new Region();
//         VBox.setVgrow(cardSpacer, Priority.ALWAYS);

//         // Action Buttons Row
//         HBox buttons = new HBox(10);
//         buttons.setAlignment(Pos.CENTER_LEFT);

//         Button selectButton = new Button(isSelected ? "Selected" : "+ Select");
//         selectButton.setPrefHeight(38);
//         HBox.setHgrow(selectButton, Priority.ALWAYS);

//         if (isSelected) {
//             selectButton.setStyle(
//                     FONT_FAMILY +
//                     "-fx-background-color: " + PRIMARY_TEAL + "; " +
//                     "-fx-text-fill: #FFFFFF; " +
//                     "-fx-font-size: 11.5px; " +
//                     "-fx-font-weight: bold; " +
//                     "-fx-background-radius: 8px; " +
//                     "-fx-cursor: hand;"
//             );
//         } else if (available) {
//             selectButton.setStyle(
//                     FONT_FAMILY +
//                     "-fx-background-color: " + INFO_BG + "; " +
//                     "-fx-text-fill: " + INFO_TEXT + "; " +
//                     "-fx-font-size: 11.5px; " +
//                     "-fx-font-weight: bold; " +
//                     "-fx-background-radius: 8px; " +
//                     "-fx-border-color: " + INFO_BORDER + "; " +
//                     "-fx-border-radius: 8px; " +
//                     "-fx-cursor: hand;"
//             );
//         } else {
//             selectButton.setDisable(true);
//             selectButton.setOpacity(0.65);
//             selectButton.setStyle(
//                     FONT_FAMILY +
//                     "-fx-background-color: #F1F5F9; " +
//                     "-fx-text-fill: " + TEXT_MUTED + "; " +
//                     "-fx-font-size: 11.5px; " +
//                     "-fx-font-weight: bold; " +
//                     "-fx-background-radius: 8px;"
//             );
//         }

//         selectButton.setOnAction(e -> {
//             if (selectedDoctors.contains(doctor.getDoctorId())) {
//                 selectedDoctors.remove(doctor.getDoctorId());
//             } else {
//                 selectedDoctors.add(doctor.getDoctorId());
//             }
//             updateSelectedDoctors();
//             filterAndRenderGrid();
//         });

//         Button statusButton = new Button("Change Status");
//         statusButton.setPrefHeight(38);
//         HBox.setHgrow(statusButton, Priority.ALWAYS);
//         statusButton.setStyle(
//                 FONT_FAMILY +
//                 "-fx-background-color: #FFFFFF; " +
//                 "-fx-text-fill: " + TEXT_PRIMARY + "; " +
//                 "-fx-font-size: 11.5px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-background-radius: 8px; " +
//                 "-fx-border-color: " + BORDER_COLOR + "; " +
//                 "-fx-border-radius: 8px; " +
//                 "-fx-cursor: hand;"
//         );

//         statusButton.setOnMouseEntered(e -> statusButton.setStyle(
//                 FONT_FAMILY +
//                 "-fx-background-color: #F1F5F9; " +
//                 "-fx-text-fill: " + PRIMARY_TEAL + "; " +
//                 "-fx-font-size: 11.5px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-background-radius: 8px; " +
//                 "-fx-border-color: #CBD5E1; " +
//                 "-fx-border-radius: 8px; " +
//                 "-fx-cursor: hand;"
//         ));
//         statusButton.setOnMouseExited(e -> statusButton.setStyle(
//                 FONT_FAMILY +
//                 "-fx-background-color: #FFFFFF; " +
//                 "-fx-text-fill: " + TEXT_PRIMARY + "; " +
//                 "-fx-font-size: 11.5px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-background-radius: 8px; " +
//                 "-fx-border-color: " + BORDER_COLOR + "; " +
//                 "-fx-border-radius: 8px; " +
//                 "-fx-cursor: hand;"
//         ));

//         statusButton.setOnAction(e -> showStatusOptions(doctor));

//         buttons.getChildren().addAll(selectButton, statusButton);

//         card.getChildren().addAll(top, detailsGrid, cardSpacer, buttons);
//         return card;
//     }

//     // =========================================================================
//     // MODAL: CHANGE DOCTOR STATUS
//     // =========================================================================
//     private void showStatusOptions(DoctorModel doctor) {
//         Stage stage = new Stage();
//         stage.initModality(Modality.APPLICATION_MODAL);
//         stage.setTitle("Update Status - " + doctor.getDoctorName());

//         VBox box = new VBox(16);
//         box.setPadding(new Insets(24));
//         box.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

//         HBox header = new HBox(12);
//         header.setAlignment(Pos.CENTER_LEFT);

//         StackPane iconHolder = new StackPane();
//         iconHolder.setPrefSize(34, 34);
//         iconHolder.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 8px;");
//         Label iLbl = new Label("🔄");
//         iLbl.setStyle("-fx-font-size: 16px;");
//         iconHolder.getChildren().add(iLbl);

//         VBox titleBox = new VBox(2);
//         Text title = new Text("Update Doctor Status");
//         title.setStyle(FONT_FAMILY + "-fx-font-size: 16.5px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
//         Label doctorLabel = new Label(doctor.getDoctorName() + " (" + doctor.getDoctorId() + ")");
//         doctorLabel.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + PRIMARY_TEAL + "; -fx-font-weight: bold;");
//         titleBox.getChildren().addAll(title, doctorLabel);

//         header.getChildren().addAll(iconHolder, titleBox);

//         VBox formCard = new VBox(12);
//         formCard.setPadding(new Insets(16));
//         formCard.setStyle(CARD_STYLE);

//         Label comboLbl = new Label("CURRENT AVAILABILITY STATUS");
//         comboLbl.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + "; -fx-letter-spacing: 0.5px;");

//         ComboBox<String> statusCombo = new ComboBox<>();
//         statusCombo.getItems().addAll("Available", "Busy", "On Leave");
//         statusCombo.setValue(doctor.getStatus());
//         statusCombo.setPrefHeight(40);
//         statusCombo.setMaxWidth(Double.MAX_VALUE);
//         statusCombo.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 13px;");

//         formCard.getChildren().addAll(comboLbl, statusCombo);

//         Button updateButton = new Button("Apply Status Change");
//         updateButton.setPrefHeight(42);
//         updateButton.setMaxWidth(Double.MAX_VALUE);
//         updateButton.setStyle(
//                 FONT_FAMILY +
//                 "-fx-background-color: " + PRIMARY_TEAL + "; " +
//                 "-fx-text-fill: #FFFFFF; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-font-size: 13px; " +
//                 "-fx-background-radius: 8px; " +
//                 "-fx-cursor: hand;"
//         );

//         updateButton.setOnAction(e -> {
//             doctorController.updateDoctorStatus(hospitalId, doctor.getDoctorId(), statusCombo.getValue());
//             stage.close();
//         });

//         box.getChildren().addAll(header, formCard, updateButton);

//         Scene scene = new Scene(box, 360, 260);
//         stage.setScene(scene);
//         stage.show();
//     }

//     // =========================================================================
//     // MODAL: ADD NEW DOCTOR
//     // =========================================================================
//     private void showAddDoctorDialog() {
//         Stage stage = new Stage();
//         stage.initModality(Modality.APPLICATION_MODAL);
//         stage.setTitle("Add Specialist - LifeLink Roster");

//         VBox box = new VBox(16);
//         box.setPadding(new Insets(24));
//         box.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

//         HBox header = new HBox(12);
//         header.setAlignment(Pos.CENTER_LEFT);

//         StackPane iconHolder = new StackPane();
//         iconHolder.setPrefSize(36, 36);
//         iconHolder.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 10px;");
//         Label dIcon = new Label("🩺");
//         dIcon.setStyle("-fx-font-size: 18px;");
//         iconHolder.getChildren().add(dIcon);

//         VBox titleBox = new VBox(2);
//         Text title = new Text("Register Medical Specialist");
//         title.setStyle(FONT_FAMILY + "-fx-font-size: 17px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
//         Text subTitle = new Text("Enlist doctor into active hospital emergency directory.");
//         subTitle.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-fill: " + TEXT_MUTED + ";");
//         titleBox.getChildren().addAll(title, subTitle);

//         header.getChildren().addAll(iconHolder, titleBox);

//         VBox formCard = new VBox(12);
//         formCard.setPadding(new Insets(18));
//         formCard.setStyle(CARD_STYLE);

//         TextField nameField = new TextField();
//         nameField.setPromptText("Doctor Full Name (e.g. Dr. Rajesh Kulkarni)");
//         nameField.setPrefHeight(40);
//         nameField.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");

//         TextField specializationField = new TextField();
//         specializationField.setPromptText("Specialization (e.g. Trauma Surgery, Cardiology)");
//         specializationField.setPrefHeight(40);
//         specializationField.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");

//         ComboBox<String> shiftCombo = new ComboBox<>();
//         shiftCombo.getItems().addAll(
//                 "09:00 AM - 05:00 PM",
//                 "08:00 AM - 04:00 PM",
//                 "10:00 AM - 06:00 PM",
//                 "06:00 AM - 02:00 PM",
//                 "02:00 PM - 10:00 PM",
//                 "10:00 PM - 06:00 AM"
//         );
//         shiftCombo.setPromptText("Select Duty Shift Schedule");
//         shiftCombo.setPrefHeight(40);
//         shiftCombo.setMaxWidth(Double.MAX_VALUE);
//         shiftCombo.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");

//         ComboBox<String> statusCombo = new ComboBox<>();
//         statusCombo.getItems().addAll("Available", "Busy", "On Leave");
//         statusCombo.setValue("Available");
//         statusCombo.setPrefHeight(40);
//         statusCombo.setMaxWidth(Double.MAX_VALUE);
//         statusCombo.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");

//         Label message = new Label();
//         message.setStyle(FONT_FAMILY + "-fx-text-fill: " + DANGER_TEXT + "; -fx-font-size: 11.5px;");

//         formCard.getChildren().addAll(nameField, specializationField, shiftCombo, statusCombo, message);

//         Button addButton = new Button("Enlist Specialist to Roster");
//         addButton.setPrefHeight(42);
//         addButton.setMaxWidth(Double.MAX_VALUE);
//         addButton.setStyle(
//                 FONT_FAMILY +
//                 "-fx-background-color: " + PRIMARY_TEAL + "; " +
//                 "-fx-text-fill: #FFFFFF; " +
//                 "-fx-font-size: 13px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-background-radius: 8px; " +
//                 "-fx-cursor: hand;"
//         );

//         addButton.setOnAction(e -> {
//             String name = nameField.getText().trim();
//             String specialization = specializationField.getText().trim();
//             String shift = shiftCombo.getValue();

//             if (name.isEmpty() || specialization.isEmpty()) {
//                 message.setText("Please enter both doctor name and medical specialization.");
//                 return;
//             }

//             if (shift == null || shift.isEmpty()) {
//                 message.setText("Please select a valid duty shift.");
//                 return;
//             }

//             String doctorId = "DOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
//             DoctorModel doctor = new DoctorModel(doctorId, name, specialization, shift, statusCombo.getValue());

//             doctorController.saveDoctor(hospitalId, doctor);
//             stage.close();
//         });

//         box.getChildren().addAll(header, formCard, addButton);

//         Scene scene = new Scene(box, 380, 420);
//         stage.setScene(scene);
//         stage.show();
//     }

//     private void updateSelectedDoctors() {
//         int count = selectedDoctors.size();
//         selectedButton.setText("Selected Roster (" + count + ")");
//         selectedCount.setText(count + " Selected");
//         selectedValue.setText(String.valueOf(count));

//         selectedNamesBox.getChildren().clear();

//         if (selectedDoctors.isEmpty()) {
//             Label emptyLabel = new Label("No specialists selected for current procedure yet. Click '+ Select' on available doctors below.");
//             emptyLabel.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED + ";");
//             selectedNamesBox.getChildren().add(emptyLabel);
//             return;
//         }

//         for (String doctorId : selectedDoctors) {
//             Label doctorLabel = new Label("✓  " + doctorId);
//             doctorLabel.setStyle(
//                     FONT_FAMILY +
//                     "-fx-background-color: " + INFO_BG + "; " +
//                     "-fx-text-fill: " + PRIMARY_TEAL + "; " +
//                     "-fx-font-size: 11px; " +
//                     "-fx-font-weight: bold; " +
//                     "-fx-padding: 5px 10px; " +
//                     "-fx-background-radius: 6px; " +
//                     "-fx-border-color: " + INFO_BORDER + "; " +
//                     "-fx-border-radius: 6px;"
//             );
//             selectedNamesBox.getChildren().add(doctorLabel);
//         }

//         Button clearAllBtn = new Button("Clear Selection");
//         clearAllBtn.setStyle(
//                 FONT_FAMILY +
//                 "-fx-background-color: transparent; " +
//                 "-fx-text-fill: " + DANGER_TEXT + "; " +
//                 "-fx-font-size: 11px; " +
//                 "-fx-font-weight: bold; " +
//                 "-fx-cursor: hand;"
//         );
//         clearAllBtn.setOnAction(e -> {
//             selectedDoctors.clear();
//             updateSelectedDoctors();
//             filterAndRenderGrid();
//         });
//         selectedNamesBox.getChildren().add(clearAllBtn);
//     }

//     private String getInitials(String name) {
//         if (name == null || name.trim().isEmpty()) {
//             return "DR";
//         }
//         String clean = name.replace("Dr.", "").replace("Dr", "").trim();
//         String[] parts = clean.split("\\s+");
//         if (parts.length == 0 || parts[0].isEmpty()) return "DR";
//         if (parts.length == 1) {
//             return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
//         }
//         return (parts[0].charAt(0) + "" + parts[parts.length - 1].charAt(0)).toUpperCase();
//     }

//     private void applyStatusStyle(Label label, String status) {
//         if ("Available".equalsIgnoreCase(status)) {
//             label.setStyle(
//                     FONT_FAMILY +
//                     "-fx-background-color: " + SUCCESS_BG + "; " +
//                     "-fx-text-fill: " + SUCCESS_TEXT + "; " +
//                     "-fx-font-size: 10.5px; " +
//                     "-fx-font-weight: bold; " +
//                     "-fx-padding: 4px 9px; " +
//                     "-fx-background-radius: 8px; " +
//                     "-fx-border-color: " + SUCCESS_BORDER + "; " +
//                     "-fx-border-radius: 8px;"
//             );
//         } else if ("Busy".equalsIgnoreCase(status) || "In Surgery".equalsIgnoreCase(status) || "Assigned".equalsIgnoreCase(status)) {
//             label.setStyle(
//                     FONT_FAMILY +
//                     "-fx-background-color: " + DANGER_BG + "; " +
//                     "-fx-text-fill: " + DANGER_TEXT + "; " +
//                     "-fx-font-size: 10.5px; " +
//                     "-fx-font-weight: bold; " +
//                     "-fx-padding: 4px 9px; " +
//                     "-fx-background-radius: 8px; " +
//                     "-fx-border-color: " + DANGER_BORDER + "; " +
//                     "-fx-border-radius: 8px;"
//             );
//         } else {
//             label.setStyle(
//                     FONT_FAMILY +
//                     "-fx-background-color: " + WARNING_BG + "; " +
//                     "-fx-text-fill: " + WARNING_TEXT + "; " +
//                     "-fx-font-size: 10.5px; " +
//                     "-fx-font-weight: bold; " +
//                     "-fx-padding: 4px 9px; " +
//                     "-fx-background-radius: 8px; " +
//                     "-fx-border-color: " + WARNING_BORDER + "; " +
//                     "-fx-border-radius: 8px;"
//             );
//         }
//     }
// }


package com.kurukshetra.view.hospital;

import com.kurukshetra.controller.hospitalController.DoctorController;
import com.kurukshetra.model.hospitalModel.DoctorModel;
import com.kurukshetra.view.util.ShimmerLoader;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HospitalDoctorManagement {

    // =========================================================================
    // DESIGN SYSTEM CONSTANTS (MATCHING HospitalDashboard.java)
    // =========================================================================
    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";
    private static final String PRIMARY_TEAL = "#006591";
    private static final String TEAL_HOVER = "#004F72";
    private static final String PAGE_BG = "#a5bdaaff ";
    private static final String SURFACE = "#FFFFFF";
    private static final String BORDER_COLOR = "#E2E8F0";
    private static final String TEXT_PRIMARY = "#0F172A";
    private static final String TEXT_SECONDARY = "#475569";
    private static final String TEXT_MUTED = "#64748B";

    private static final String SUCCESS_BG = "#ECFDF5";
    private static final String SUCCESS_TEXT = "#059669";
    private static final String SUCCESS_BORDER = "#A7F3D0";

    private static final String DANGER_BG = "#FEF2F2";
    private static final String DANGER_TEXT = "#DC2626";
    private static final String DANGER_BORDER = "#FECDD3";

    private static final String WARNING_BG = "#FFFBEB";
    private static final String WARNING_TEXT = "#D97706";
    private static final String WARNING_BORDER = "#FDE68A";

    private static final String INFO_BG = "#E0F2FE";
    private static final String INFO_TEXT = "#0369A1";
    private static final String INFO_BORDER = "#BAE6FD";

    private static final String INDIGO_BG = "#EEF2FF";
    private static final String INDIGO_TEXT = "#4F46E5";
    private static final String INDIGO_BORDER = "#C7D2FE";

    private static final String CARD_STYLE =
            "-fx-background-color: " + SURFACE + "; " +
            "-fx-border-color: " + BORDER_COLOR + "; " +
            "-fx-border-radius: 14px; " +
            "-fx-background-radius: 14px; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 14, 0, 0, 3);";

    private final String hospitalId;
    private final DoctorController doctorController = new DoctorController();
    private final List<String> selectedDoctors = new ArrayList<>();
    private final List<DoctorModel> cachedDoctorsList = new ArrayList<>();

    // UI references
    private VBox doctorGridContainer;
    private ShimmerLoader.ShimmerPane doctorGridShimmer;
    private Text availableValue;
    private Text busyValue;
    private Text leaveValue;
    private Text selectedValue;

    private Label doctorCountLabel;
    private Button selectedButton;
    private Label selectedCount;
    private HBox selectedNamesBox;

    private TextField searchField;
    private ComboBox<String> statusFilterBox;

    public HospitalDoctorManagement(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public VBox getDoctorManagement() {

        VBox mainContent = new VBox(22);
        mainContent.setPadding(new Insets(26, 32, 36, 32));
        mainContent.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        // =====================================================================
        // 1. TOP HEADER (TITLE & ACTION BUTTONS)
        // =====================================================================
        HBox header = new HBox(16);
        header.setAlignment(Pos.CENTER_LEFT);

        VBox headingBox = new VBox(4);
        Text heading = new Text("Doctors & Surgical Specialists");
        heading.setStyle(FONT_FAMILY + "-fx-font-size: 26px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");
        headingBox.getChildren().addAll(heading);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        // Selected Counter Button
        selectedButton = new Button("Selected Roster (0)");
        selectedButton.setPrefHeight(42);
        selectedButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + INFO_BG + "; " +
                "-fx-text-fill: " + INFO_TEXT + "; " +
                "-fx-font-size: 12.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 10px; " +
                "-fx-border-color: " + INFO_BORDER + "; " +
                "-fx-border-radius: 10px; " +
                "-fx-padding: 0 16; " +
                "-fx-cursor: hand;"
        );

        // Add Doctor Button
        Button addDoctorButton = new Button("+ Add Specialist");
        addDoctorButton.setPrefHeight(42);
        addDoctorButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_TEAL + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 12.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 10px; " +
                "-fx-padding: 0 18; " +
                "-fx-cursor: hand;"
        );
        addDoctorButton.setEffect(new DropShadow(10, 0, 2, Color.rgb(0, 101, 145, 0.25)));

        addDoctorButton.setOnMouseEntered(e -> {
            addDoctorButton.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + TEAL_HOVER + "; " +
                    "-fx-text-fill: #FFFFFF; " +
                    "-fx-font-size: 12.5px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-padding: 0 18; " +
                    "-fx-cursor: hand;"
            );
            addDoctorButton.setTranslateY(-2);
        });
        addDoctorButton.setOnMouseExited(e -> {
            addDoctorButton.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + PRIMARY_TEAL + "; " +
                    "-fx-text-fill: #FFFFFF; " +
                    "-fx-font-size: 12.5px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-padding: 0 18; " +
                    "-fx-cursor: hand;"
            );
            addDoctorButton.setTranslateY(0);
        });

        addDoctorButton.setOnAction(e -> showAddDoctorDialog());

        header.getChildren().addAll(headingBox, headerSpacer, selectedButton, addDoctorButton);

        // =====================================================================
        // 2. SELECTED DOCTORS BANNER
        // =====================================================================
        VBox selectedDoctorsBox = new VBox(10);
        selectedDoctorsBox.setPadding(new Insets(16, 20, 16, 20));
        selectedDoctorsBox.setStyle(
                "-fx-background-color: #FFFFFF; " +
                "-fx-background-radius: 14px; " +
                "-fx-border-color: " + INFO_BORDER + "; " +
                "-fx-border-radius: 14px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(3,105,161,0.06), 14, 0, 0, 3);"
        );

        HBox selectedHeader = new HBox(10);
        selectedHeader.setAlignment(Pos.CENTER_LEFT);

        StackPane selIconHolder = new StackPane();
        selIconHolder.setPrefSize(28, 28);
        selIconHolder.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 7px;");
        Label selIcon = new Label("📋");
        selIcon.setStyle("-fx-font-size: 13px;");
        selIconHolder.getChildren().add(selIcon);

        Text selectedTitle = new Text("Active Surgical & Duty Roster Selection");
        selectedTitle.setStyle(FONT_FAMILY + "-fx-font-size: 14.5px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Region selectedSpacer = new Region();
        HBox.setHgrow(selectedSpacer, Priority.ALWAYS);

        selectedCount = new Label("0 Selected");
        selectedCount.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + INFO_BG + "; " +
                "-fx-text-fill: " + INFO_TEXT + "; " +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 4px 10px; " +
                "-fx-background-radius: 6px;"
        );

        selectedHeader.getChildren().addAll(selIconHolder, selectedTitle, selectedSpacer, selectedCount);

        selectedNamesBox = new HBox(8);
        selectedNamesBox.setAlignment(Pos.CENTER_LEFT);

        Label emptySelection = new Label("No specialists selected for current procedure yet. Click '+ Select' on available doctors below.");
        emptySelection.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED + ";");
        selectedNamesBox.getChildren().add(emptySelection);

        selectedDoctorsBox.getChildren().addAll(selectedHeader, selectedNamesBox);

        // =====================================================================
        // 3. 4-CARD SUMMARY METRIC STRIP (MATCHING HospitalDashboard.java)
        // =====================================================================
        HBox summaryRow = new HBox(16);

        availableValue = new Text("0");
        VBox availableBox = createMetricCard("✓", SUCCESS_TEXT, SUCCESS_BG, "AVAILABLE DOCTORS", availableValue, "READY FOR DUTY", SUCCESS_BG, SUCCESS_TEXT);

        busyValue = new Text("0");
        VBox busyBox = createMetricCard("⚡", DANGER_TEXT, DANGER_BG, "BUSY / IN SURGERY", busyValue, "OCCUPIED", DANGER_BG, DANGER_TEXT);

        leaveValue = new Text("0");
        VBox leaveBox = createMetricCard("🏖", WARNING_TEXT, WARNING_BG, "ON LEAVE", leaveValue, "OFF DUTY", WARNING_BG, WARNING_TEXT);

        selectedValue = new Text("0");
        VBox selectedSummaryBox = createMetricCard("📋", INFO_TEXT, INFO_BG, "SELECTED FOR DUTY", selectedValue, "ASSIGNED", INFO_BG, INFO_TEXT);

        summaryRow.getChildren().addAll(availableBox, busyBox, leaveBox, selectedSummaryBox);

        // =====================================================================
        // 4. SECTION DIRECTORY HEADER WITH SEARCH & STATUS FILTER
        // =====================================================================
        VBox directorySection = new VBox(14);

        HBox directoryHeader = new HBox(14);
        directoryHeader.setAlignment(Pos.CENTER_LEFT);

        Text doctorsTitle = new Text("Medical Specialists Directory");
        doctorsTitle.setStyle(FONT_FAMILY + "-fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        doctorCountLabel = new Label("0 Specialists");
        doctorCountLabel.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #F1F5F9; " +
                "-fx-text-fill: " + TEXT_MUTED + "; " +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 4px 10px; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 8px;"
        );

        Region doctorSpacer = new Region();
        HBox.setHgrow(doctorSpacer, Priority.ALWAYS);

        // Search Field
        HBox searchContainer = new HBox(10);
        searchContainer.setAlignment(Pos.CENTER_LEFT);
        searchContainer.setPadding(new Insets(0, 16, 0, 16));
        searchContainer.setPrefHeight(42);
        searchContainer.setPrefWidth(320);
        searchContainer.setStyle(
                "-fx-background-color: #F8FAFC; " +
                "-fx-border-color: #CBD5E1; " +
                "-fx-border-radius: 20px; " +
                "-fx-background-radius: 20px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 4, 0, 0, 2);"
        );

        Label searchIcon = new Label("🔍");
        searchIcon.setStyle("-fx-font-size: 14px; -fx-text-fill: " + TEXT_MUTED + ";");

        searchField = new TextField();
        searchField.setPromptText("Search name, specialization, ID...");
        searchField.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-font-size: 13.5px; -fx-text-fill: " + TEXT_PRIMARY + "; -fx-prompt-text-fill: " + TEXT_MUTED + "; -fx-padding: 0;");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        searchField.focusedProperty().addListener((obs, oldV, isFocused) -> {
            if (isFocused) {
                searchContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + PRIMARY_TEAL + "; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-border-width: 1.5px; -fx-effect: dropshadow(three-pass-box, rgba(0,101,145,0.1), 8, 0, 0, 2);");
            } else {
                searchContainer.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E1; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 4, 0, 0, 2);");
            }
        });

        searchContainer.getChildren().addAll(searchIcon, searchField);

        // searchField.focusedProperty().addListener((obs, oldV, isFocused) -> {
        //     if (isFocused) {
        //         searchContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + PRIMARY_TEAL + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        //     } else {
        //         searchContainer.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        //     }
        // });

        searchField.textProperty().addListener((obs, oldV, newV) -> filterAndRenderGrid());

        // Status Filter Dropdown
        statusFilterBox = new ComboBox<>();
        statusFilterBox.getItems().addAll("All Statuses", "Available", "Busy", "On Leave");
        statusFilterBox.setValue("All Statuses");
        statusFilterBox.setPrefHeight(38);
        statusFilterBox.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #FFFFFF; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 8px; " +
                "-fx-background-radius: 8px; " +
                "-fx-font-size: 12px;"
        );
        statusFilterBox.valueProperty().addListener((obs, oldV, newV) -> filterAndRenderGrid());

        directoryHeader.getChildren().addAll(doctorsTitle, doctorCountLabel, doctorSpacer, searchContainer, statusFilterBox);

        // 5. DOCTOR GRID CONTAINER
        doctorGridContainer = new VBox(16);
        doctorGridShimmer = ShimmerLoader.createDoctorGridSkeleton(1100, 6);
        doctorGridContainer.getChildren().add(doctorGridShimmer);

        directorySection.getChildren().addAll(directoryHeader, doctorGridContainer);

        mainContent.getChildren().addAll(
                header,
                selectedDoctorsBox,
                summaryRow,
                directorySection
        );

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: " + PAGE_BG + "; -fx-border-color: transparent;");

        VBox finalContent = new VBox(scrollPane);
        finalContent.setStyle("-fx-background-color: " + PAGE_BG + ";");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        loadDoctors();

        return finalContent;
    }

    private VBox createMetricCard(String iconEmoji, String iconTextColor, String iconBgColor,
                                  String title, Text valueNode, String badgeText,
                                  String badgeBg, String badgeTextColor) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setPrefHeight(125);
        card.setStyle(CARD_STYLE);
        HBox.setHgrow(card, Priority.ALWAYS);

        card.setOnMouseEntered(e -> {
            card.setTranslateY(-3);
            card.setStyle(
                "-fx-background-color: #FFFFFF; " +
                "-fx-border-color: #CBD5E1; " +
                "-fx-border-radius: 14px; " +
                "-fx-background-radius: 14px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.09), 18, 0, 0, 6);"
            );
        });
        card.setOnMouseExited(e -> {
            card.setTranslateY(0);
            card.setStyle(CARD_STYLE);
        });

        HBox topRow = new HBox(8);
        topRow.setAlignment(Pos.CENTER_LEFT);

        StackPane iconPane = new StackPane();
        iconPane.setPrefSize(34, 34);
        iconPane.setMinSize(34, 34);
        iconPane.setStyle("-fx-background-color: " + iconBgColor + "; -fx-background-radius: 8px;");
        Text iconText = new Text(iconEmoji);
        iconText.setStyle("-fx-font-size: 15px; -fx-fill: " + iconTextColor + "; -fx-font-weight: bold;");
        iconPane.getChildren().add(iconText);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label badge = new Label(badgeText);
        badge.setStyle(
            FONT_FAMILY +
            "-fx-background-color: " + badgeBg + "; " +
            "-fx-text-fill: " + badgeTextColor + "; " +
            "-fx-font-size: 9.5px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 3px 8px; " +
            "-fx-background-radius: 6px;"
        );
        topRow.getChildren().addAll(iconPane, spacer, badge);

        valueNode.setStyle(FONT_FAMILY + "-fx-font-size: 26px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");

        Text labelText = new Text(title);
        labelText.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: 600; -fx-fill: " + TEXT_MUTED + "; -fx-letter-spacing: 0.4px;");

        card.getChildren().addAll(topRow, valueNode, labelText);
        return card;
    }

    private void loadDoctors() {
        doctorController.listenToDoctors(hospitalId, doctors -> {
            Platform.runLater(() -> {
                cachedDoctorsList.clear();
                if (doctors != null) {
                    cachedDoctorsList.addAll(doctors);
                }

                int available = 0;
                int busy = 0;
                int leave = 0;

                for (DoctorModel doc : cachedDoctorsList) {
                    String st = doc.getStatus();
                    if ("Available".equalsIgnoreCase(st)) {
                        available++;
                    } else if ("Busy".equalsIgnoreCase(st) || "In Surgery".equalsIgnoreCase(st) || "Assigned".equalsIgnoreCase(st)) {
                        busy++;
                    } else if ("On Leave".equalsIgnoreCase(st)) {
                        leave++;
                    }
                }

                doctorCountLabel.setText(cachedDoctorsList.size() + " Specialists");
                availableValue.setText(String.valueOf(available));
                busyValue.setText(String.valueOf(busy));
                leaveValue.setText(String.valueOf(leave));

                filterAndRenderGrid();
            });
        });
    }

    private void filterAndRenderGrid() {
        if (doctorGridContainer == null) return;
        if (doctorGridShimmer != null) {
            doctorGridShimmer.stop();
            doctorGridShimmer = null;
        }
        doctorGridContainer.getChildren().clear();

        String query = (searchField != null && searchField.getText() != null)
                ? searchField.getText().trim().toLowerCase() : "";
        String statusFilter = (statusFilterBox != null && statusFilterBox.getValue() != null)
                ? statusFilterBox.getValue() : "All Statuses";

        List<DoctorModel> filtered = cachedDoctorsList.stream().filter(doc -> {
            boolean matchesQuery = query.isEmpty()
                    || (doc.getDoctorName() != null && doc.getDoctorName().toLowerCase().contains(query))
                    || (doc.getDoctorId() != null && doc.getDoctorId().toLowerCase().contains(query))
                    || (doc.getSpecialization() != null && doc.getSpecialization().toLowerCase().contains(query));

            boolean matchesStatus = "All Statuses".equalsIgnoreCase(statusFilter)
                    || (doc.getStatus() != null && doc.getStatus().equalsIgnoreCase(statusFilter))
                    || ("Busy".equalsIgnoreCase(statusFilter) && ("In Surgery".equalsIgnoreCase(doc.getStatus()) || "Assigned".equalsIgnoreCase(doc.getStatus())));

            return matchesQuery && matchesStatus;
        }).collect(Collectors.toList());

        if (filtered.isEmpty()) {
            VBox emptyBox = new VBox(10);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(40));
            emptyBox.setStyle(CARD_STYLE);

            Label emptyLbl = new Label("No doctors match the selected search and filter criteria.");
            emptyLbl.setStyle(FONT_FAMILY + "-fx-font-size: 13.5px; -fx-text-fill: " + TEXT_MUTED + ";");
            emptyBox.getChildren().add(emptyLbl);
            doctorGridContainer.getChildren().add(emptyBox);
            return;
        }

        GridPane grid = new GridPane();
        grid.setHgap(18);
        grid.setVgap(18);

        for (int i = 0; i < filtered.size(); i++) {
            DoctorModel doctor = filtered.get(i);
            VBox card = createDoctorCard(doctor);
            grid.add(card, i % 3, i / 3);
        }

        doctorGridContainer.getChildren().add(grid);
    }

    // =========================================================================
    // ELEVATED DOCTOR PROFILE CARD (3-COLUMN GRID ITEM)
    // =========================================================================
    private VBox createDoctorCard(DoctorModel doctor) {
        boolean available = "Available".equalsIgnoreCase(doctor.getStatus());
        boolean isSelected = selectedDoctors.contains(doctor.getDoctorId());

        VBox card = new VBox(14);
        card.setPadding(new Insets(20));
        card.setPrefWidth(340);
        card.setPrefHeight(275);
        card.setMinHeight(275);
        card.setMaxHeight(275);
        card.setStyle(CARD_STYLE);

        card.setOnMouseEntered(e -> {
            card.setTranslateY(-3);
            card.setStyle(
                "-fx-background-color: #FFFFFF; " +
                "-fx-border-color: #CBD5E1; " +
                "-fx-border-radius: 14px; " +
                "-fx-background-radius: 14px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.09), 18, 0, 0, 6);"
            );
        });
        card.setOnMouseExited(e -> {
            card.setTranslateY(0);
            card.setStyle(CARD_STYLE);
        });

        // Top Row: Avatar + Name + Status Badge
        HBox top = new HBox(12);
        top.setAlignment(Pos.CENTER_LEFT);

        String initials = getInitials(doctor.getDoctorName());
        StackPane avatarPane = new StackPane();
        avatarPane.setPrefSize(44, 44);
        avatarPane.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 12px; -fx-border-color: " + INFO_BORDER + "; -fx-border-radius: 12px;");

        Text initialsText = new Text(initials);
        initialsText.setStyle(FONT_FAMILY + "-fx-font-size: 13.5px; -fx-font-weight: 800; -fx-fill: " + PRIMARY_TEAL + ";");
        avatarPane.getChildren().add(initialsText);

        VBox nameBox = new VBox(2);
        Text nameText = new Text(doctor.getDoctorName() != null ? doctor.getDoctorName() : "Doctor");
        nameText.setStyle(FONT_FAMILY + "-fx-font-size: 15px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");

        Label specBadge = new Label(doctor.getSpecialization() != null ? doctor.getSpecialization() : "General Medicine");
        specBadge.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + INDIGO_BG + "; " +
                "-fx-text-fill: " + INDIGO_TEXT + "; " +
                "-fx-font-size: 10.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 2px 8px; " +
                "-fx-background-radius: 6px;"
        );
        nameBox.getChildren().addAll(nameText, specBadge);

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        Label statusLabel = new Label("● " + (doctor.getStatus() != null ? doctor.getStatus() : "Available"));
        applyStatusStyle(statusLabel, doctor.getStatus());

        top.getChildren().addAll(avatarPane, nameBox, topSpacer, statusLabel);

        // Middle Section: ID & Shift details
        HBox detailsGrid = new HBox(14);
        detailsGrid.setPadding(new Insets(10, 12, 10, 12));
        detailsGrid.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 10px; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 10px;");

        VBox idBox = new VBox(2);
        Text idTitle = new Text("DOCTOR ID");
        idTitle.setStyle(FONT_FAMILY + "-fx-font-size: 9.5px; -fx-font-weight: bold; -fx-fill: " + TEXT_MUTED + "; -fx-letter-spacing: 0.4px;");
        Text idText = new Text(doctor.getDoctorId());
        idText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: 600; -fx-fill: " + TEXT_PRIMARY + ";");
        idBox.getChildren().addAll(idTitle, idText);

        Region splitSpacer = new Region();
        HBox.setHgrow(splitSpacer, Priority.ALWAYS);

        VBox shiftBox = new VBox(2);
        shiftBox.setAlignment(Pos.TOP_RIGHT);
        Text shiftTitle = new Text("SHIFT SCHEDULE");
        shiftTitle.setStyle(FONT_FAMILY + "-fx-font-size: 9.5px; -fx-font-weight: bold; -fx-fill: " + TEXT_MUTED + "; -fx-letter-spacing: 0.4px;");
        Text shiftText = new Text("🕒 " + (doctor.getShift() != null ? doctor.getShift() : "Standard Shift"));
        shiftText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: 600; -fx-fill: " + TEXT_PRIMARY + ";");
        shiftBox.getChildren().addAll(shiftTitle, shiftText);

        detailsGrid.getChildren().addAll(idBox, splitSpacer, shiftBox);

        Region cardSpacer = new Region();
        VBox.setVgrow(cardSpacer, Priority.ALWAYS);

        // Action Buttons Row
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_LEFT);

        Button selectButton = new Button(isSelected ? "Selected" : "+ Select");
        selectButton.setPrefHeight(38);
        HBox.setHgrow(selectButton, Priority.ALWAYS);

        if (isSelected) {
            selectButton.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + PRIMARY_TEAL + "; " +
                    "-fx-text-fill: #FFFFFF; " +
                    "-fx-font-size: 11.5px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8px; " +
                    "-fx-cursor: hand;"
            );
        } else if (available) {
            selectButton.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + INFO_BG + "; " +
                    "-fx-text-fill: " + INFO_TEXT + "; " +
                    "-fx-font-size: 11.5px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8px; " +
                    "-fx-border-color: " + INFO_BORDER + "; " +
                    "-fx-border-radius: 8px; " +
                    "-fx-cursor: hand;"
            );
        } else {
            selectButton.setDisable(true);
            selectButton.setOpacity(0.65);
            selectButton.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: #F1F5F9; " +
                    "-fx-text-fill: " + TEXT_MUTED + "; " +
                    "-fx-font-size: 11.5px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8px;"
            );
        }

        selectButton.setOnAction(e -> {
            if (selectedDoctors.contains(doctor.getDoctorId())) {
                selectedDoctors.remove(doctor.getDoctorId());
            } else {
                selectedDoctors.add(doctor.getDoctorId());
            }
            updateSelectedDoctors();
            filterAndRenderGrid();
        });

        Button statusButton = new Button("Change Status");
        statusButton.setPrefHeight(38);
        HBox.setHgrow(statusButton, Priority.ALWAYS);
        statusButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #FFFFFF; " +
                "-fx-text-fill: " + TEXT_PRIMARY + "; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 8px; " +
                "-fx-cursor: hand;"
        );

        statusButton.setOnMouseEntered(e -> statusButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #F1F5F9; " +
                "-fx-text-fill: " + PRIMARY_TEAL + "; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-color: #CBD5E1; " +
                "-fx-border-radius: 8px; " +
                "-fx-cursor: hand;"
        ));
        statusButton.setOnMouseExited(e -> statusButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: #FFFFFF; " +
                "-fx-text-fill: " + TEXT_PRIMARY + "; " +
                "-fx-font-size: 11.5px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 8px; " +
                "-fx-cursor: hand;"
        ));

        statusButton.setOnAction(e -> showStatusOptions(doctor));

        buttons.getChildren().addAll(selectButton, statusButton);

        card.getChildren().addAll(top, detailsGrid, cardSpacer, buttons);
        return card;
    }

    // =========================================================================
    // MODAL: CHANGE DOCTOR STATUS
    // =========================================================================
    private void showStatusOptions(DoctorModel doctor) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Update Status - " + doctor.getDoctorName());

        VBox box = new VBox(16);
        box.setPadding(new Insets(24));
        box.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        StackPane iconHolder = new StackPane();
        iconHolder.setPrefSize(34, 34);
        iconHolder.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 8px;");
        Label iLbl = new Label("🔄");
        iLbl.setStyle("-fx-font-size: 16px;");
        iconHolder.getChildren().add(iLbl);

        VBox titleBox = new VBox(2);
        Text title = new Text("Update Doctor Status");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 16.5px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Label doctorLabel = new Label(doctor.getDoctorName() + " (" + doctor.getDoctorId() + ")");
        doctorLabel.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + PRIMARY_TEAL + "; -fx-font-weight: bold;");
        titleBox.getChildren().addAll(title, doctorLabel);

        header.getChildren().addAll(iconHolder, titleBox);

        VBox formCard = new VBox(12);
        formCard.setPadding(new Insets(16));
        formCard.setStyle(CARD_STYLE);

        Label comboLbl = new Label("CURRENT AVAILABILITY STATUS");
        comboLbl.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + "; -fx-letter-spacing: 0.5px;");

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Available", "Busy", "On Leave");
        statusCombo.setValue(doctor.getStatus());
        statusCombo.setPrefHeight(40);
        statusCombo.setMaxWidth(Double.MAX_VALUE);
        statusCombo.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 13px;");

        formCard.getChildren().addAll(comboLbl, statusCombo);

        Button updateButton = new Button("Apply Status Change");
        updateButton.setPrefHeight(42);
        updateButton.setMaxWidth(Double.MAX_VALUE);
        updateButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_TEAL + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 13px; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
        );

        updateButton.setOnAction(e -> {
            try {
                if ("UNAVAILABLE".equalsIgnoreCase(doctor.getStatus())) {
                    throw new com.kurukshetra.exception.DoctorUnavailableException(
                        "Oops! This doctor is currently UNAVAILABLE.\n" +
                        "You can't change their status right now. 😄"
                    );
                }
                if ("BUSY".equalsIgnoreCase(doctor.getStatus()) && !statusCombo.getValue().equalsIgnoreCase(doctor.getStatus())) {
                    throw new com.kurukshetra.exception.DoctorBusyException(
                        "Doctor is busy in the Operation Theatre! Let them finish the surgery first. 😄"
                    );
                }
                doctorController.updateDoctorStatus(hospitalId, doctor.getDoctorId(), statusCombo.getValue());
                stage.close();
            } catch (com.kurukshetra.exception.DoctorUnavailableException | com.kurukshetra.exception.DoctorBusyException ex) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
                alert.setTitle(ex instanceof com.kurukshetra.exception.DoctorBusyException ? "⚠ Doctor In Surgery" : "⚠ Doctor Unavailable");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        box.getChildren().addAll(header, formCard, updateButton);

        Scene scene = new Scene(box, 360, 260);
        stage.setScene(scene);
        stage.show();
    }

    // =========================================================================
    // MODAL: ADD NEW DOCTOR
    // =========================================================================
    private void showAddDoctorDialog() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Specialist - LifeLink Roster");

        VBox box = new VBox(16);
        box.setPadding(new Insets(24));
        box.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        StackPane iconHolder = new StackPane();
        iconHolder.setPrefSize(36, 36);
        iconHolder.setStyle("-fx-background-color: " + INFO_BG + "; -fx-background-radius: 10px;");
        Label dIcon = new Label("🩺");
        dIcon.setStyle("-fx-font-size: 18px;");
        iconHolder.getChildren().add(dIcon);

        VBox titleBox = new VBox(2);
        Text title = new Text("Register Medical Specialist");
        title.setStyle(FONT_FAMILY + "-fx-font-size: 17px; -fx-font-weight: bold; -fx-fill: " + TEXT_PRIMARY + ";");
        Text subTitle = new Text("Enlist doctor into active hospital emergency directory.");
        subTitle.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-fill: " + TEXT_MUTED + ";");
        titleBox.getChildren().addAll(title, subTitle);

        header.getChildren().addAll(iconHolder, titleBox);

        VBox formCard = new VBox(12);
        formCard.setPadding(new Insets(18));
        formCard.setStyle(CARD_STYLE);

        TextField nameField = new TextField();
        nameField.setPromptText("Doctor Full Name (e.g. Dr. Rajesh Kulkarni)");
        nameField.setPrefHeight(40);
        nameField.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");

        TextField specializationField = new TextField();
        specializationField.setPromptText("Specialization (e.g. Trauma Surgery, Cardiology)");
        specializationField.setPrefHeight(40);
        specializationField.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");

        ComboBox<String> shiftCombo = new ComboBox<>();
        shiftCombo.getItems().addAll(
                "09:00 AM - 05:00 PM",
                "08:00 AM - 04:00 PM",
                "10:00 AM - 06:00 PM",
                "06:00 AM - 02:00 PM",
                "02:00 PM - 10:00 PM",
                "10:00 PM - 06:00 AM"
        );
        shiftCombo.setPromptText("Select Duty Shift Schedule");
        shiftCombo.setPrefHeight(40);
        shiftCombo.setMaxWidth(Double.MAX_VALUE);
        shiftCombo.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");

        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Available", "Busy", "On Leave");
        statusCombo.setValue("Available");
        statusCombo.setPrefHeight(40);
        statusCombo.setMaxWidth(Double.MAX_VALUE);
        statusCombo.setStyle(FONT_FAMILY + "-fx-background-color: #FFFFFF; -fx-border-color: " + BORDER_COLOR + "; -fx-border-radius: 8px; -fx-background-radius: 8px; -fx-font-size: 12.5px;");

        Label message = new Label();
        message.setStyle(FONT_FAMILY + "-fx-text-fill: " + DANGER_TEXT + "; -fx-font-size: 11.5px;");

        formCard.getChildren().addAll(nameField, specializationField, shiftCombo, statusCombo, message);

        Button addButton = new Button("Enlist Specialist to Roster");
        addButton.setPrefHeight(42);
        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setStyle(
                FONT_FAMILY +
                "-fx-background-color: " + PRIMARY_TEAL + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;"
        );

        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String specialization = specializationField.getText().trim();
            String shift = shiftCombo.getValue();

            if (name.isEmpty() || specialization.isEmpty()) {
                message.setText("Please enter both doctor name and medical specialization.");
                return;
            }

            if (shift == null || shift.isEmpty()) {
                message.setText("Please select a valid duty shift.");
                return;
            }

            String doctorId = "DOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            DoctorModel doctor = new DoctorModel(doctorId, name, specialization, shift, statusCombo.getValue());

            doctorController.saveDoctor(hospitalId, doctor);
            stage.close();
        });

        box.getChildren().addAll(header, formCard, addButton);

        Scene scene = new Scene(box, 380, 420);
        stage.setScene(scene);
        stage.show();
    }

    private void updateSelectedDoctors() {
        int count = selectedDoctors.size();
        selectedButton.setText("Selected Roster (" + count + ")");
        selectedCount.setText(count + " Selected");
        selectedValue.setText(String.valueOf(count));

        selectedNamesBox.getChildren().clear();

        if (selectedDoctors.isEmpty()) {
            Label emptyLabel = new Label("No specialists selected for current procedure yet. Click '+ Select' on available doctors below.");
            emptyLabel.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED + ";");
            selectedNamesBox.getChildren().add(emptyLabel);
            return;
        }

        for (String doctorId : selectedDoctors) {
            Label doctorLabel = new Label("✓  " + doctorId);
            doctorLabel.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + INFO_BG + "; " +
                    "-fx-text-fill: " + PRIMARY_TEAL + "; " +
                    "-fx-font-size: 11px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 5px 10px; " +
                    "-fx-background-radius: 6px; " +
                    "-fx-border-color: " + INFO_BORDER + "; " +
                    "-fx-border-radius: 6px;"
            );
            selectedNamesBox.getChildren().add(doctorLabel);
        }

        Button clearAllBtn = new Button("Clear Selection");
        clearAllBtn.setStyle(
                FONT_FAMILY +
                "-fx-background-color: transparent; " +
                "-fx-text-fill: " + DANGER_TEXT + "; " +
                "-fx-font-size: 11px; " +
                "-fx-font-weight: bold; " +
                "-fx-cursor: hand;"
        );
        clearAllBtn.setOnAction(e -> {
            selectedDoctors.clear();
            updateSelectedDoctors();
            filterAndRenderGrid();
        });
        selectedNamesBox.getChildren().add(clearAllBtn);
    }

    private String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "DR";
        }
        String clean = name.replace("Dr.", "").replace("Dr", "").trim();
        String[] parts = clean.split("\\s+");
        if (parts.length == 0 || parts[0].isEmpty()) return "DR";
        if (parts.length == 1) {
            return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
        }
        return (parts[0].charAt(0) + "" + parts[parts.length - 1].charAt(0)).toUpperCase();
    }

    private void applyStatusStyle(Label label, String status) {
        if ("Available".equalsIgnoreCase(status)) {
            label.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + SUCCESS_BG + "; " +
                    "-fx-text-fill: " + SUCCESS_TEXT + "; " +
                    "-fx-font-size: 10.5px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 4px 9px; " +
                    "-fx-background-radius: 8px; " +
                    "-fx-border-color: " + SUCCESS_BORDER + "; " +
                    "-fx-border-radius: 8px;"
            );
        } else if ("Busy".equalsIgnoreCase(status) || "In Surgery".equalsIgnoreCase(status) || "Assigned".equalsIgnoreCase(status)) {
            label.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + DANGER_BG + "; " +
                    "-fx-text-fill: " + DANGER_TEXT + "; " +
                    "-fx-font-size: 10.5px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 4px 9px; " +
                    "-fx-background-radius: 8px; " +
                    "-fx-border-color: " + DANGER_BORDER + "; " +
                    "-fx-border-radius: 8px;"
            );
        } else {
            label.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + WARNING_BG + "; " +
                    "-fx-text-fill: " + WARNING_TEXT + "; " +
                    "-fx-font-size: 10.5px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 4px 9px; " +
                    "-fx-background-radius: 8px; " +
                    "-fx-border-color: " + WARNING_BORDER + "; " +
                    "-fx-border-radius: 8px;"
            );
        }
    }
}