// // package com.kurukshetra.view;

// // import javafx.animation.FadeTransition;
// // import javafx.animation.ParallelTransition;
// // import javafx.animation.ScaleTransition;
// // import javafx.geometry.Insets;
// // import javafx.geometry.Pos;
// // import javafx.scene.control.Button;
// // import javafx.scene.layout.BorderPane;
// // import javafx.scene.layout.HBox;
// // import javafx.scene.layout.Priority;
// // import javafx.scene.layout.Region;
// // import javafx.scene.layout.StackPane;
// // import javafx.scene.layout.VBox;
// // import javafx.scene.paint.Color;
// // import javafx.scene.shape.Circle;
// // import javafx.scene.text.Text;

// // public class ProfilePage {

// //     public BorderPane profileRoot;

// //     public BorderPane getProfilePage(Runnable callBackDashboard) {

// //         profileRoot = new BorderPane();
// //         profileRoot.setStyle("-fx-background-color : #F7F9FC; -fx-font-family : 'Segoe UI';");

// //         // ================= TOP BAR =================

// //         HBox topBar = new HBox();
// //         topBar.setAlignment(Pos.CENTER_LEFT);
// //         topBar.setPadding(new Insets(18, 30, 10, 30));

// //         Button backButton = new Button("‹");
// //         backButton.setPrefWidth(40);
// //         backButton.setPrefHeight(40);
// //         backButton.setStyle("-fx-background-color : #EAF7FD; -fx-text-fill : #08A1E5; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;");

// //         backButton.setOnAction(e -> {
// //             System.out.println("Back to Dashboard");
// //             callBackDashboard.run();
// //         });

// //         backButton.setOnMouseEntered(e ->
// //                 backButton.setStyle("-fx-background-color : #08A1E5; -fx-text-fill : white; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;")
// //         );

// //         backButton.setOnMouseExited(e ->
// //                 backButton.setStyle("-fx-background-color : #EAF7FD; -fx-text-fill : #08A1E5; -fx-font-size : 22px; -fx-font-weight : bold; -fx-background-radius : 20; -fx-cursor : hand;")
// //         );

// //         Region leftSpace = new Region();
// //         HBox.setHgrow(leftSpace, Priority.ALWAYS);

// //         Text pageTitle = new Text("Nurse Profile");
// //         pageTitle.setStyle("-fx-font-size : 19px; -fx-font-weight : bold; -fx-fill : #172B4D;");

// //         Region rightSpace = new Region();
// //         HBox.setHgrow(rightSpace, Priority.ALWAYS);

// //         Text settingsIcon = new Text("⚙");
// //         settingsIcon.setStyle("-fx-font-size : 16px; -fx-fill : #08A1E5; -fx-cursor : hand;");

// //         settingsIcon.setOnMouseClicked(e ->
// //                 System.out.println("Profile settings clicked")
// //         );

// //         topBar.getChildren().addAll(backButton, leftSpace, pageTitle, rightSpace, settingsIcon);

// //         // ================= MAIN CONTENT =================

// //         VBox mainContent = new VBox(18);
// //         mainContent.setPadding(new Insets(5, 50, 25, 50));

// //         // ================= PROFILE HEADER =================

// //         VBox profileCard = new VBox(6);
// //         profileCard.setAlignment(Pos.CENTER);
// //         profileCard.setPrefHeight(175);
// //         profileCard.setPadding(new Insets(20));
// //         profileCard.setStyle("-fx-background-color : linear-gradient(to bottom, #DDF5FC, #FFFFFF); -fx-background-radius : 16; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.10), 12, 0, 0, 4);");

// //         StackPane profileImage = new StackPane();

// //         Circle imageCircle = new Circle(36);
// //         imageCircle.setFill(Color.web("#08A1E5"));
// //         imageCircle.setStroke(Color.WHITE);
// //         imageCircle.setStrokeWidth(4);

// //         Text imageText = new Text("S");
// //         imageText.setStyle("-fx-font-size : 26px; -fx-font-weight : bold; -fx-fill : white;");

// //         profileImage.getChildren().addAll(imageCircle, imageText);

// //         Circle statusCircle = new Circle(8);
// //         statusCircle.setFill(Color.web("#20B86A"));
// //         statusCircle.setStroke(Color.WHITE);
// //         statusCircle.setStrokeWidth(2);

// //         StackPane.setAlignment(statusCircle, Pos.BOTTOM_RIGHT);
// //         StackPane.setMargin(statusCircle, new Insets(0, 2, 3, 0));

// //         profileImage.getChildren().add(statusCircle);

// //         Text name = new Text("Dr. Sarah Smith");
// //         name.setStyle("-fx-font-size : 17px; -fx-font-weight : bold; -fx-fill : #172B4D;");

// //         Text role = new Text("Head of Emergency / Senior Nurse");
// //         role.setStyle("-fx-font-size : 11px; -fx-fill : #728096;");

// //         Text online = new Text("●  On Duty");
// //         online.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : #20B86A;");

// //         profileCard.getChildren().addAll(profileImage, name, role, online);

// //         // ================= STATISTICS =================

// //         HBox statistics = new HBox(14);

// //         VBox shiftsCard = statCard("◷", "Shift Hours", "128h", "#08A1E5");
// //         VBox emergencyCard = statCard("✱", "Emergencies", "42", "#D71920");
// //         VBox ratingCard = statCard("★", "Rating", "4.9/5", "#08A1E5");

// //         statistics.getChildren().addAll(shiftsCard, emergencyCard, ratingCard);

// //         // ================= PROFESSIONAL INFO =================

// //         VBox professionalCard = new VBox(11);
// //         professionalCard.setPrefHeight(195);
// //         professionalCard.setPadding(new Insets(18, 20, 16, 20));
// //         professionalCard.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 16; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.08), 10, 0, 0, 3);");

// //         Text professionalTitle = new Text("Professional Info");
// //         professionalTitle.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : #172B4D;");

// //         professionalCard.getChildren().add(professionalTitle);

// //         professionalCard.getChildren().add(informationRow("▣", "Employee ID", "LL-8902"));
// //         professionalCard.getChildren().add(informationRow("▤", "Department", "Emergency Ward 3"));
// //         professionalCard.getChildren().add(informationRow("◎", "Certifications", "ACLS    BLS    PALS"));

// //         // ================= ACCOUNT SETTINGS =================

// //         VBox accountCard = new VBox(10);
// //         accountCard.setPrefHeight(95);
// //         accountCard.setPadding(new Insets(16, 20, 14, 20));
// //         accountCard.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 16; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.08), 10, 0, 0, 3);");

// //         Text accountTitle = new Text("Account Settings");
// //         accountTitle.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : #172B4D;");

// //         Button logoutButton = new Button("⇥  Logout");
// //         logoutButton.setPrefWidth(110);
// //         logoutButton.setAlignment(Pos.CENTER_LEFT);
// //         logoutButton.setStyle("-fx-background-color : transparent; -fx-text-fill : #D71920; -fx-font-size : 12px; -fx-font-weight : bold; -fx-cursor : hand;");

// //         StackPane pageStack = new StackPane();

// //         logoutButton.setOnAction(e -> showLogoutConfirm(pageStack));

// //         logoutButton.focusedProperty().addListener((obs, wasFocused, isFocused) -> {

// //             if (isFocused) {

// //                 logoutButton.setStyle("-fx-background-color : transparent; -fx-text-fill : #D71920; -fx-font-size : 12px; -fx-font-weight : bold; -fx-cursor : hand; -fx-border-color : #D71920; -fx-border-width : 1.5; -fx-border-radius : 6;");

// //             } else {

// //                 logoutButton.setStyle("-fx-background-color : transparent; -fx-text-fill : #D71920; -fx-font-size : 12px; -fx-font-weight : bold; -fx-cursor : hand;");
// //             }
// //         });

// //         logoutButton.setOnMouseEntered(e ->
// //                 logoutButton.setStyle("-fx-background-color : #FFF0F0; -fx-text-fill : #D71920; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;")
// //         );

// //         logoutButton.setOnMouseExited(e ->
// //                 logoutButton.setStyle("-fx-background-color : transparent; -fx-text-fill : #D71920; -fx-font-size : 12px; -fx-font-weight : bold; -fx-cursor : hand;")
// //         );

// //         accountCard.getChildren().addAll(accountTitle, logoutButton);

// //         mainContent.getChildren().addAll(profileCard, statistics, professionalCard, accountCard);

// //         pageStack.getChildren().add(mainContent);

// //         profileRoot.setTop(topBar);
// //         profileRoot.setCenter(pageStack);

// //         // ================= PAGE ANIMATION =================

// //         FadeTransition fade = new FadeTransition(AppSettings.dur(450), profileRoot);

// //         fade.setFromValue(0.3);
// //         fade.setToValue(1);

// //         fade.play();

// //         return profileRoot;
// //     }

// //     // =========================================================
// //     // LOGOUT CONFIRMATION MODAL
// //     // A lightweight in-window overlay (fade + scale in) instead of a native
// //     // Dialog, so it stays visually consistent with the rest of the app.
// //     // =========================================================

// //     public void showLogoutConfirm(StackPane pageStack) {

// //         StackPane dimmer = new StackPane();
// //         dimmer.setStyle("-fx-background-color : rgba(23,43,77,0.45);");

// //         VBox modal = new VBox(14);
// //         modal.setAlignment(Pos.CENTER);
// //         modal.setMaxWidth(300);
// //         modal.setPadding(new Insets(26));
// //         modal.setStyle("-fx-background-color : white; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(20,30,50,0.30), 20, 0, 0, 8);");
// //         modal.setOpacity(0);
// //         modal.setScaleX(0.9);
// //         modal.setScaleY(0.9);

// //         Text modalIcon = new Text("⇥");
// //         modalIcon.setStyle("-fx-font-size : 26px; -fx-fill : #D71920;");

// //         Text modalTitle = new Text("Log out of LifeLink?");
// //         modalTitle.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : #172B4D;");

// //         Text modalBody = new Text("You will need to sign back in to access the dashboard.");
// //         modalBody.setStyle("-fx-font-size : 10px; -fx-fill : #728096; -fx-text-alignment : center;");
// //         modalBody.setWrappingWidth(230);

// //         HBox modalButtons = new HBox(10);
// //         modalButtons.setAlignment(Pos.CENTER);

// //         Button cancelButton = new Button("Cancel");
// //         cancelButton.setPrefWidth(110);
// //         cancelButton.setPrefHeight(36);
// //         cancelButton.setStyle("-fx-background-color : #F0F4F9; -fx-text-fill : #536277; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");

// //         Button confirmButton = new Button("Logout");
// //         confirmButton.setPrefWidth(110);
// //         confirmButton.setPrefHeight(36);
// //         confirmButton.setStyle("-fx-background-color : #D71920; -fx-text-fill : white; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");

// //         cancelButton.setOnAction(e -> hideModal(pageStack, dimmer));

// //         confirmButton.setOnAction(e -> {

// //             System.out.println("Logout button clicked");

// //             hideModal(pageStack, dimmer);

// //             Toast.show(DashboardPage.appOverlay, "You have been logged out", "success");
// //         });

// //         modalButtons.getChildren().addAll(cancelButton, confirmButton);

// //         modal.getChildren().addAll(modalIcon, modalTitle, modalBody, modalButtons);

// //         dimmer.getChildren().add(modal);
// //         dimmer.setOnMouseClicked(e -> {
// //             if (e.getTarget() == dimmer) {
// //                 hideModal(pageStack, dimmer);
// //             }
// //         });

// //         pageStack.getChildren().add(dimmer);

// //         FadeTransition dimIn = new FadeTransition(AppSettings.dur(180), dimmer);
// //         dimIn.setFromValue(0);
// //         dimIn.setToValue(1);

// //         FadeTransition modalFadeIn = new FadeTransition(AppSettings.dur(200), modal);
// //         modalFadeIn.setToValue(1);

// //         ScaleTransition modalScaleIn = new ScaleTransition(AppSettings.dur(200), modal);
// //         modalScaleIn.setToX(1);
// //         modalScaleIn.setToY(1);

// //         new ParallelTransition(dimIn, modalFadeIn, modalScaleIn).play();
// //     }

// //     public void hideModal(StackPane pageStack, StackPane dimmer) {

// //         FadeTransition dimOut = new FadeTransition(AppSettings.dur(160), dimmer);
// //         dimOut.setToValue(0);
// //         dimOut.setOnFinished(e -> pageStack.getChildren().remove(dimmer));
// //         dimOut.play();
// //     }

// //     // =========================================================
// //     // STAT CARD
// //     // =========================================================

// //     public VBox statCard(String icon, String title, String value, String iconColor) {

// //         VBox card = new VBox(5);

// //         card.setAlignment(Pos.CENTER);
// //         card.setPrefHeight(85);

// //         HBox.setHgrow(card, Priority.ALWAYS);

// //         card.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 14; -fx-border-color : #E0E5EC; -fx-border-width : 1; -fx-border-radius : 14; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.07), 8, 0, 0, 2); -fx-cursor : hand;");

// //         Text iconText = new Text(icon);
// //         iconText.setStyle("-fx-font-size : 16px; -fx-font-weight : bold; -fx-fill : " + iconColor + ";");

// //         Text titleText = new Text(title);
// //         titleText.setStyle("-fx-font-size : 9px; -fx-fill : #728096;");

// //         Text valueText = new Text(value);
// //         valueText.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : #172B4D;");

// //         card.getChildren().addAll(iconText, titleText, valueText);

// //         ScaleTransition scale = new ScaleTransition(AppSettings.dur(150), card);

// //         card.setOnMouseEntered(e -> {

// //             scale.setToX(1.05);
// //             scale.setToY(1.05);
// //             scale.playFromStart();

// //             card.setStyle("-fx-background-color : #EAF7FD; -fx-background-radius : 14; -fx-border-color : #08A1E5; -fx-border-width : 1.5; -fx-border-radius : 14; -fx-cursor : hand;");
// //         });

// //         card.setOnMouseExited(e -> {

// //             scale.setToX(1);
// //             scale.setToY(1);
// //             scale.playFromStart();

// //             card.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 14; -fx-border-color : #E0E5EC; -fx-border-width : 1; -fx-border-radius : 14; -fx-cursor : hand;");
// //         });

// //         return card;
// //     }

// //     // =========================================================
// //     // INFORMATION ROW
// //     // =========================================================

// //     public HBox informationRow(String icon, String title, String value) {

// //         HBox row = new HBox(12);

// //         row.setAlignment(Pos.CENTER_LEFT);
// //         row.setPadding(new Insets(7, 8, 7, 8));
// //         row.setStyle("-fx-background-color : #F7F9FC; -fx-background-radius : 8;");

// //         Text iconText = new Text(icon);
// //         iconText.setStyle("-fx-font-size : 14px; -fx-fill : #08A1E5;");

// //         VBox textBox = new VBox(2);

// //         Text titleText = new Text(title);
// //         titleText.setStyle("-fx-font-size : 9px; -fx-fill : #8993A2;");

// //         Text valueText = new Text(value);
// //         valueText.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : #172B4D;");

// //         textBox.getChildren().addAll(titleText, valueText);

// //         row.getChildren().addAll(iconText, textBox);

// //         return row;
// //     }
// // }









// package com.kurukshetra.view.nurse;

// import javafx.animation.FadeTransition;
// import javafx.animation.ParallelTransition;
// import javafx.animation.ScaleTransition;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Circle;
// import javafx.scene.text.Text;

// public class NurseProfilePage {

//     public BorderPane profileRoot;

//     public BorderPane getProfilePage(Runnable callBackDashboard) {

//         profileRoot = new BorderPane();
//         profileRoot.setStyle("-fx-background-color : #F7F9FC; -fx-font-family : 'Segoe UI';");

//         // ================= TOP BAR =================

//         HBox topBar = new HBox();
//         topBar.setAlignment(Pos.CENTER_LEFT);
//         topBar.setPadding(new Insets(18, 30, 10, 30));

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

//         Region leftSpace = new Region();
//         HBox.setHgrow(leftSpace, Priority.ALWAYS);

//         Text pageTitle = new Text("Nurse Profile");
//         pageTitle.setStyle("-fx-font-size : 19px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Region rightSpace = new Region();
//         HBox.setHgrow(rightSpace, Priority.ALWAYS);

//         Text settingsIcon = new Text("⚙");
//         settingsIcon.setStyle("-fx-font-size : 16px; -fx-fill : #08A1E5; -fx-cursor : hand;");

//         settingsIcon.setOnMouseClicked(e ->
//                 System.out.println("Profile settings clicked")
//         );

//         topBar.getChildren().addAll(backButton, leftSpace, pageTitle, rightSpace, settingsIcon);

//         // ================= MAIN CONTENT =================

//         VBox mainContent = new VBox(18);
//         mainContent.setPadding(new Insets(5, 50, 25, 50));

//         // ================= PROFILE HEADER =================

//         VBox profileCard = new VBox(6);
//         profileCard.setAlignment(Pos.CENTER);
//         profileCard.setPrefHeight(175);
//         profileCard.setPadding(new Insets(20));
//         profileCard.setStyle("-fx-background-color : linear-gradient(to bottom, #DDF5FC, #FFFFFF); -fx-background-radius : 16; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.10), 12, 0, 0, 4);");

//         StackPane profileImage = new StackPane();

//         Circle imageCircle = new Circle(36);
//         imageCircle.setFill(Color.web("#08A1E5"));
//         imageCircle.setStroke(Color.WHITE);
//         imageCircle.setStrokeWidth(4);

//         Text imageText = new Text("S");
//         imageText.setStyle("-fx-font-size : 26px; -fx-font-weight : bold; -fx-fill : white;");

//         profileImage.getChildren().addAll(imageCircle, imageText);

//         Circle statusCircle = new Circle(8);
//         statusCircle.setFill(Color.web("#20B86A"));
//         statusCircle.setStroke(Color.WHITE);
//         statusCircle.setStrokeWidth(2);

//         StackPane.setAlignment(statusCircle, Pos.BOTTOM_RIGHT);
//         StackPane.setMargin(statusCircle, new Insets(0, 2, 3, 0));

//         profileImage.getChildren().add(statusCircle);

//         Text name = new Text("Dr. Sarah Smith");
//         name.setStyle("-fx-font-size : 17px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Text role = new Text("Head of Emergency / Senior Nurse");
//         role.setStyle("-fx-font-size : 11px; -fx-fill : #728096;");

//         Text online = new Text("●  On Duty");
//         online.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : #20B86A;");

//         profileCard.getChildren().addAll(profileImage, name, role, online);

//         // ================= STATISTICS =================

//         HBox statistics = new HBox(14);

//         VBox shiftsCard = statCard("◷", "Shift Hours", "128h", "#08A1E5");
//         VBox emergencyCard = statCard("✱", "Emergencies", "42", "#D71920");
//         VBox ratingCard = statCard("★", "Rating", "4.9/5", "#08A1E5");

//         statistics.getChildren().addAll(shiftsCard, emergencyCard, ratingCard);

//         // ================= PROFESSIONAL INFO =================

//         VBox professionalCard = new VBox(11);
//         professionalCard.setPrefHeight(195);
//         professionalCard.setPadding(new Insets(18, 20, 16, 20));
//         professionalCard.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 16; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.08), 10, 0, 0, 3);");

//         Text professionalTitle = new Text("Professional Info");
//         professionalTitle.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         professionalCard.getChildren().add(professionalTitle);

//         professionalCard.getChildren().add(informationRow("▣", "Employee ID", "LL-8902"));
//         professionalCard.getChildren().add(informationRow("▤", "Department", "Emergency Ward 3"));
//         professionalCard.getChildren().add(informationRow("◎", "Certifications", "ACLS    BLS    PALS"));

//         // ================= ACCOUNT SETTINGS =================

//         VBox accountCard = new VBox(10);
//         accountCard.setPrefHeight(95);
//         accountCard.setPadding(new Insets(16, 20, 14, 20));
//         accountCard.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 16; -fx-border-color : #DCE5EC; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.08), 10, 0, 0, 3);");

//         Text accountTitle = new Text("Account Settings");
//         accountTitle.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Button logoutButton = new Button("⇥  Logout");
//         logoutButton.setPrefWidth(110);
//         logoutButton.setAlignment(Pos.CENTER_LEFT);
//         logoutButton.setStyle("-fx-background-color : transparent; -fx-text-fill : #D71920; -fx-font-size : 12px; -fx-font-weight : bold; -fx-cursor : hand;");

//         StackPane pageStack = new StackPane();

//         logoutButton.setOnAction(e -> showLogoutConfirm(pageStack));

//         logoutButton.focusedProperty().addListener((obs, wasFocused, isFocused) -> {

//             if (isFocused) {

//                 logoutButton.setStyle("-fx-background-color : transparent; -fx-text-fill : #D71920; -fx-font-size : 12px; -fx-font-weight : bold; -fx-cursor : hand; -fx-border-color : #D71920; -fx-border-width : 1.5; -fx-border-radius : 6;");

//             } else {

//                 logoutButton.setStyle("-fx-background-color : transparent; -fx-text-fill : #D71920; -fx-font-size : 12px; -fx-font-weight : bold; -fx-cursor : hand;");
//             }
//         });

//         logoutButton.setOnMouseEntered(e ->
//                 logoutButton.setStyle("-fx-background-color : #FFF0F0; -fx-text-fill : #D71920; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;")
//         );

//         logoutButton.setOnMouseExited(e ->
//                 logoutButton.setStyle("-fx-background-color : transparent; -fx-text-fill : #D71920; -fx-font-size : 12px; -fx-font-weight : bold; -fx-cursor : hand;")
//         );

//         accountCard.getChildren().addAll(accountTitle, logoutButton);

//         mainContent.getChildren().addAll(profileCard, statistics, professionalCard, accountCard);

//         pageStack.getChildren().add(mainContent);

//         profileRoot.setTop(topBar);
//         profileRoot.setCenter(pageStack);

//         // ================= PAGE ANIMATION =================

//         FadeTransition fade = new FadeTransition(NurseAppSettings.dur(450), profileRoot);

//         fade.setFromValue(0.3);
//         fade.setToValue(1);

//         fade.play();

//         return profileRoot;
//     }

//     // =========================================================
//     // LOGOUT CONFIRMATION MODAL
//     // A lightweight in-window overlay (fade + scale in) instead of a native
//     // Dialog, so it stays visually consistent with the rest of the app.
//     // =========================================================

//     public void showLogoutConfirm(StackPane pageStack) {

//         StackPane dimmer = new StackPane();
//         dimmer.setStyle("-fx-background-color : rgba(23,43,77,0.45);");

//         VBox modal = new VBox(14);
//         modal.setAlignment(Pos.CENTER);
//         modal.setMaxWidth(300);
//         modal.setPadding(new Insets(26));
//         modal.setStyle("-fx-background-color : white; -fx-background-radius : 16; -fx-effect : dropshadow(gaussian, rgba(20,30,50,0.30), 20, 0, 0, 8);");
//         modal.setOpacity(0);
//         modal.setScaleX(0.9);
//         modal.setScaleY(0.9);

//         Text modalIcon = new Text("⇥");
//         modalIcon.setStyle("-fx-font-size : 26px; -fx-fill : #D71920;");

//         Text modalTitle = new Text("Log out of LifeLink?");
//         modalTitle.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         Text modalBody = new Text("You will need to sign back in to access the dashboard.");
//         modalBody.setStyle("-fx-font-size : 10px; -fx-fill : #728096; -fx-text-alignment : center;");
//         modalBody.setWrappingWidth(230);

//         HBox modalButtons = new HBox(10);
//         modalButtons.setAlignment(Pos.CENTER);

//         Button cancelButton = new Button("Cancel");
//         cancelButton.setPrefWidth(110);
//         cancelButton.setPrefHeight(36);
//         cancelButton.setStyle("-fx-background-color : #F0F4F9; -fx-text-fill : #536277; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");

//         Button confirmButton = new Button("Logout");
//         confirmButton.setPrefWidth(110);
//         confirmButton.setPrefHeight(36);
//         confirmButton.setStyle("-fx-background-color : #D71920; -fx-text-fill : white; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");

//         cancelButton.setOnAction(e -> hideModal(pageStack, dimmer));

//         confirmButton.setOnAction(e -> {

//             System.out.println("Logout button clicked");

//             hideModal(pageStack, dimmer);

//             NurseToast.show(NurseDashboardPage.appOverlay, "You have been logged out", "success");
//         });

//         modalButtons.getChildren().addAll(cancelButton, confirmButton);

//         modal.getChildren().addAll(modalIcon, modalTitle, modalBody, modalButtons);

//         dimmer.getChildren().add(modal);
//         dimmer.setOnMouseClicked(e -> {
//             if (e.getTarget() == dimmer) {
//                 hideModal(pageStack, dimmer);
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

//     public void hideModal(StackPane pageStack, StackPane dimmer) {

//         FadeTransition dimOut = new FadeTransition(NurseAppSettings.dur(160), dimmer);
//         dimOut.setToValue(0);
//         dimOut.setOnFinished(e -> pageStack.getChildren().remove(dimmer));
//         dimOut.play();
//     }

//     // =========================================================
//     // STAT CARD
//     // =========================================================

//     public VBox statCard(String icon, String title, String value, String iconColor) {

//         VBox card = new VBox(5);

//         card.setAlignment(Pos.CENTER);
//         card.setPrefHeight(85);

//         HBox.setHgrow(card, Priority.ALWAYS);

//         card.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 14; -fx-border-color : #E0E5EC; -fx-border-width : 1; -fx-border-radius : 14; -fx-effect : dropshadow(gaussian, rgba(30,60,90,0.07), 8, 0, 0, 2); -fx-cursor : hand;");

//         Text iconText = new Text(icon);
//         iconText.setStyle("-fx-font-size : 16px; -fx-font-weight : bold; -fx-fill : " + iconColor + ";");

//         Text titleText = new Text(title);
//         titleText.setStyle("-fx-font-size : 9px; -fx-fill : #728096;");

//         Text valueText = new Text(value);
//         valueText.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         card.getChildren().addAll(iconText, titleText, valueText);

//         ScaleTransition scale = new ScaleTransition(NurseAppSettings.dur(150), card);

//         card.setOnMouseEntered(e -> {

//             scale.setToX(1.05);
//             scale.setToY(1.05);
//             scale.playFromStart();

//             card.setStyle("-fx-background-color : #EAF7FD; -fx-background-radius : 14; -fx-border-color : #08A1E5; -fx-border-width : 1.5; -fx-border-radius : 14; -fx-cursor : hand;");
//         });

//         card.setOnMouseExited(e -> {

//             scale.setToX(1);
//             scale.setToY(1);
//             scale.playFromStart();

//             card.setStyle("-fx-background-color : #FFFFFF; -fx-background-radius : 14; -fx-border-color : #E0E5EC; -fx-border-width : 1; -fx-border-radius : 14; -fx-cursor : hand;");
//         });

//         return card;
//     }

//     // =========================================================
//     // INFORMATION ROW
//     // =========================================================

//     public HBox informationRow(String icon, String title, String value) {

//         HBox row = new HBox(12);

//         row.setAlignment(Pos.CENTER_LEFT);
//         row.setPadding(new Insets(7, 8, 7, 8));
//         row.setStyle("-fx-background-color : #F7F9FC; -fx-background-radius : 8;");

//         Text iconText = new Text(icon);
//         iconText.setStyle("-fx-font-size : 14px; -fx-fill : #08A1E5;");

//         VBox textBox = new VBox(2);

//         Text titleText = new Text(title);
//         titleText.setStyle("-fx-font-size : 9px; -fx-fill : #8993A2;");

//         Text valueText = new Text(value);
//         valueText.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : #172B4D;");

//         textBox.getChildren().addAll(titleText, valueText);

//         row.getChildren().addAll(iconText, textBox);

//         return row;
//     }
// }


package com.kurukshetra.view.nurse;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class NurseProfilePage {

    // ================= COLOR PALETTE =================
    private static final String PRIMARY_PINK = "#E67593";
    private static final String PINK_DARK = "#FF1493";
    private static final String VERY_LIGHT_PINK = "#FDEDF2";
    private static final String LIGHT_PINK = "#F9E0E8";
    private static final String VERY_PALE_PINK = "#FFF9FA";
    private static final String PAGE_BG = "#FCF9FA";
    private static final String SURFACE = "#FFFFFF";
    private static final String PRIMARY_TEXT = "#2B2226";
    private static final String SECONDARY_TEXT = "#665960";
    private static final String MUTED_TEXT = "#94878D";
    private static final String BORDER_COLOR = "#EEDDE3";
    private static final String DIVIDER_COLOR = "#F3E8EC";

    public BorderPane profileRoot;

    public BorderPane getProfilePage(Runnable callBackDashboard) {

        profileRoot = new BorderPane();
        profileRoot.setStyle("-fx-background-color : " + PAGE_BG + "; -fx-font-family : 'Segoe UI';");

        // ================= TOP BAR =================

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(18, 30, 10, 30));

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

        Region leftSpace = new Region();
        HBox.setHgrow(leftSpace, Priority.ALWAYS);

        Text pageTitle = new Text("Nurse Profile");
        pageTitle.setStyle("-fx-font-size : 19px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Region rightSpace = new Region();
        HBox.setHgrow(rightSpace, Priority.ALWAYS);

        Text settingsIcon = new Text("⚙");
        settingsIcon.setStyle("-fx-font-size : 16px; -fx-fill : " + PRIMARY_PINK + "; -fx-cursor : hand;");
        settingsIcon.setOnMouseClicked(e -> System.out.println("Profile settings clicked"));

        topBar.getChildren().addAll(backButton, leftSpace, pageTitle, rightSpace, settingsIcon);

        // ================= MAIN CONTENT =================

        VBox mainContent = new VBox(18);
        mainContent.setPadding(new Insets(5, 50, 25, 50));

        // ================= PROFILE HEADER =================

        VBox profileCard = new VBox(6);
        profileCard.setAlignment(Pos.CENTER);
        profileCard.setPrefHeight(175);
        profileCard.setPadding(new Insets(20));
        profileCard.setStyle("-fx-background-color : linear-gradient(to bottom, " + VERY_LIGHT_PINK + ", #FFFFFF); -fx-background-radius : 16; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 12, 0, 0, 4);");

        StackPane profileImage = new StackPane();

        Circle imageCircle = new Circle(36);
        imageCircle.setFill(Color.web(PRIMARY_PINK));
        imageCircle.setStroke(Color.WHITE);
        imageCircle.setStrokeWidth(4);

        Text imageText = new Text("S");
        imageText.setStyle("-fx-font-size : 26px; -fx-font-weight : bold; -fx-fill : white;");

        profileImage.getChildren().addAll(imageCircle, imageText);

        Circle statusCircle = new Circle(8);
        statusCircle.setFill(Color.web("#258052"));
        statusCircle.setStroke(Color.WHITE);
        statusCircle.setStrokeWidth(2);

        StackPane.setAlignment(statusCircle, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(statusCircle, new Insets(0, 2, 3, 0));

        profileImage.getChildren().add(statusCircle);

        Text name = new Text("Dr. Sarah Smith");
        name.setStyle("-fx-font-size : 17px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text role = new Text("Head of Emergency / Senior Nurse");
        role.setStyle("-fx-font-size : 11px; -fx-fill : " + SECONDARY_TEXT + ";");

        Text online = new Text("●  On Duty");
        online.setStyle("-fx-font-size : 10px; -fx-font-weight : bold; -fx-fill : #258052;");

        profileCard.getChildren().addAll(profileImage, name, role, online);

        // ================= STATISTICS =================

        HBox statistics = new HBox(14);
        VBox shiftsCard = statCard("◷", "Shift Hours", "128h", PRIMARY_PINK);
        VBox emergencyCard = statCard("✱", "Emergencies", "42", "#C94F4F");
        VBox ratingCard = statCard("★", "Rating", "4.9/5", PRIMARY_PINK);

        statistics.getChildren().addAll(shiftsCard, emergencyCard, ratingCard);

        // ================= PROFESSIONAL INFO =================

        VBox professionalCard = new VBox(11);
        professionalCard.setPrefHeight(195);
        professionalCard.setPadding(new Insets(18, 20, 16, 20));
        professionalCard.setStyle("-fx-background-color : " + SURFACE + "; -fx-background-radius : 16; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 10, 0, 0, 3);");

        Text professionalTitle = new Text("Professional Info");
        professionalTitle.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        professionalCard.getChildren().add(professionalTitle);
        professionalCard.getChildren().add(informationRow("▣", "Employee ID", "LL-8902"));
        professionalCard.getChildren().add(informationRow("▤", "Department", "Emergency Ward 3"));
        professionalCard.getChildren().add(informationRow("◎", "Certifications", "ACLS    BLS    PALS"));

        // ================= ACCOUNT SETTINGS =================

        VBox accountCard = new VBox(10);
        accountCard.setPrefHeight(95);
        accountCard.setPadding(new Insets(16, 20, 14, 20));
        accountCard.setStyle("-fx-background-color : " + SURFACE + "; -fx-background-radius : 16; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.08), 10, 0, 0, 3);");

        Text accountTitle = new Text("Account Settings");
        accountTitle.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Button logoutButton = new Button("⇥  Logout");
        logoutButton.setPrefWidth(110);
        logoutButton.setAlignment(Pos.CENTER_LEFT);
        logoutButton.setStyle("-fx-background-color : transparent; -fx-text-fill : #C94F4F; -fx-font-size : 12px; -fx-font-weight : bold; -fx-cursor : hand;");

        StackPane pageStack = new StackPane();
        logoutButton.setOnAction(e -> showLogoutConfirm(pageStack));

        logoutButton.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused) {
                logoutButton.setStyle("-fx-background-color : transparent; -fx-text-fill : #C94F4F; -fx-font-size : 12px; -fx-font-weight : bold; -fx-cursor : hand; -fx-border-color : #C94F4F; -fx-border-width : 1.5; -fx-border-radius : 6;");
            } else {
                logoutButton.setStyle("-fx-background-color : transparent; -fx-text-fill : #C94F4F; -fx-font-size : 12px; -fx-font-weight : bold; -fx-cursor : hand;");
            }
        });

        logoutButton.setOnMouseEntered(e ->
                logoutButton.setStyle("-fx-background-color : #FCE8E7; -fx-text-fill : #C94F4F; -fx-font-size : 12px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;")
        );

        logoutButton.setOnMouseExited(e ->
                logoutButton.setStyle("-fx-background-color : transparent; -fx-text-fill : #C94F4F; -fx-font-size : 12px; -fx-font-weight : bold; -fx-cursor : hand;")
        );

        accountCard.getChildren().addAll(accountTitle, logoutButton);
        mainContent.getChildren().addAll(profileCard, statistics, professionalCard, accountCard);
        pageStack.getChildren().add(mainContent);

        profileRoot.setTop(topBar);
        profileRoot.setCenter(pageStack);

        FadeTransition fade = new FadeTransition(NurseAppSettings.dur(450), profileRoot);
        fade.setFromValue(0.3);
        fade.setToValue(1);
        fade.play();

        return profileRoot;
    }

    public void showLogoutConfirm(StackPane pageStack) {

        StackPane dimmer = new StackPane();
        dimmer.setStyle("-fx-background-color : rgba(43,34,38,0.45);");

        VBox modal = new VBox(14);
        modal.setAlignment(Pos.CENTER);
        modal.setMaxWidth(300);
        modal.setPadding(new Insets(26));
        modal.setStyle("-fx-background-color : white; -fx-background-radius : 16; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 16; -fx-effect : dropshadow(gaussian, rgba(43,34,38,0.25), 20, 0, 0, 8);");
        modal.setOpacity(0);
        modal.setScaleX(0.9);
        modal.setScaleY(0.9);

        Text modalIcon = new Text("⇥");
        modalIcon.setStyle("-fx-font-size : 26px; -fx-fill : #C94F4F;");

        Text modalTitle = new Text("Log out of LifeLink?");
        modalTitle.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        Text modalBody = new Text("You will need to sign back in to access the dashboard.");
        modalBody.setStyle("-fx-font-size : 10px; -fx-fill : " + SECONDARY_TEXT + "; -fx-text-alignment : center;");
        modalBody.setWrappingWidth(230);

        HBox modalButtons = new HBox(10);
        modalButtons.setAlignment(Pos.CENTER);

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(110);
        cancelButton.setPrefHeight(36);
        cancelButton.setStyle("-fx-background-color : #FFFFFF; -fx-border-color : " + BORDER_COLOR + "; -fx-text-fill : " + SECONDARY_TEXT + "; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-border-radius : 8; -fx-cursor : hand;");

        Button confirmButton = new Button("Logout");
        confirmButton.setPrefWidth(110);
        confirmButton.setPrefHeight(36);
        confirmButton.setStyle("-fx-background-color : #C94F4F; -fx-text-fill : white; -fx-font-size : 11px; -fx-font-weight : bold; -fx-background-radius : 8; -fx-cursor : hand;");

        cancelButton.setOnAction(e -> hideModal(pageStack, dimmer));

        confirmButton.setOnAction(e -> {
            System.out.println("Logout button clicked");
            hideModal(pageStack, dimmer);
            NurseToast.show(NurseDashboardPage.appOverlay, "You have been logged out", "success");
        });

        modalButtons.getChildren().addAll(cancelButton, confirmButton);
        modal.getChildren().addAll(modalIcon, modalTitle, modalBody, modalButtons);

        dimmer.getChildren().add(modal);
        dimmer.setOnMouseClicked(e -> {
            if (e.getTarget() == dimmer) {
                hideModal(pageStack, dimmer);
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

    public void hideModal(StackPane pageStack, StackPane dimmer) {
        FadeTransition dimOut = new FadeTransition(NurseAppSettings.dur(160), dimmer);
        dimOut.setToValue(0);
        dimOut.setOnFinished(e -> pageStack.getChildren().remove(dimmer));
        dimOut.play();
    }

    public VBox statCard(String icon, String title, String value, String iconColor) {

        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setPrefHeight(85);
        HBox.setHgrow(card, Priority.ALWAYS);

        card.setStyle("-fx-background-color : " + SURFACE + "; -fx-background-radius : 14; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 14; -fx-effect : dropshadow(gaussian, rgba(230,117,147,0.07), 8, 0, 0, 2); -fx-cursor : hand;");

        Text iconText = new Text(icon);
        iconText.setStyle("-fx-font-size : 16px; -fx-font-weight : bold; -fx-fill : " + iconColor + ";");

        Text titleText = new Text(title);
        titleText.setStyle("-fx-font-size : 9px; -fx-fill : " + SECONDARY_TEXT + ";");

        Text valueText = new Text(value);
        valueText.setStyle("-fx-font-size : 14px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        card.getChildren().addAll(iconText, titleText, valueText);

        ScaleTransition scale = new ScaleTransition(NurseAppSettings.dur(150), card);

        card.setOnMouseEntered(e -> {
            scale.setToX(1.05);
            scale.setToY(1.05);
            scale.playFromStart();
            card.setStyle("-fx-background-color : " + VERY_LIGHT_PINK + "; -fx-background-radius : 14; -fx-border-color : " + PRIMARY_PINK + "; -fx-border-width : 1.5; -fx-border-radius : 14; -fx-cursor : hand;");
        });

        card.setOnMouseExited(e -> {
            scale.setToX(1);
            scale.setToY(1);
            scale.playFromStart();
            card.setStyle("-fx-background-color : " + SURFACE + "; -fx-background-radius : 14; -fx-border-color : " + BORDER_COLOR + "; -fx-border-width : 1; -fx-border-radius : 14;");
        });

        return card;
    }

    public HBox informationRow(String icon, String title, String value) {

        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(7, 8, 7, 8));
        row.setStyle("-fx-background-color : " + VERY_PALE_PINK + "; -fx-background-radius : 8; -fx-border-color : " + DIVIDER_COLOR + "; -fx-border-radius : 8;");

        Text iconText = new Text(icon);
        iconText.setStyle("-fx-font-size : 14px; -fx-fill : " + PRIMARY_PINK + ";");

        VBox textBox = new VBox(2);

        Text titleText = new Text(title);
        titleText.setStyle("-fx-font-size : 9px; -fx-fill : " + MUTED_TEXT + ";");

        Text valueText = new Text(value);
        valueText.setStyle("-fx-font-size : 11px; -fx-font-weight : bold; -fx-fill : " + PRIMARY_TEXT + ";");

        textBox.getChildren().addAll(titleText, valueText);
        row.getChildren().addAll(iconText, textBox);

        return row;
    }
}