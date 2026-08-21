// package com.kurukshetra.view.loginSignup;

// import com.kurukshetra.controller.UserAuthController;
// import com.kurukshetra.controller.UserController;
// import com.kurukshetra.view.Welcome;
// import com.kurukshetra.view.family.FamilyHomePage;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.PasswordField;
// import javafx.scene.control.TextField;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.AnchorPane;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.ClosePath;
// import javafx.scene.shape.CubicCurveTo;
// import javafx.scene.shape.LineTo;
// import javafx.scene.shape.MoveTo;
// import javafx.scene.shape.Path;
// import javafx.scene.shape.Rectangle;
// import javafx.scene.text.Text;

// public class FamilyLoginPage {

//     // =========================================================
//     // COLORS
//     // =========================================================

//     private static final String TEAL = "#ca6721";
//     private static final String TEAL_DARK = "#782F16";
//     private static final String TEAL_LIGHT = "#63D7DC";

//     private static final String WHITE = "#FFFFFF";
//     private static final String BLACK = "#111111";
//     private static final String GRAY_BG = "#777775";
//     private static final String BORDER = "#D7DBDF";
//     private static final String PLACEHOLDER = "#A7A9AC";

//     // =========================================================
//     // IMAGES
//     // =========================================================

//     private static final String DOCTOR_IMAGE_URL =
//             "assets\\Images\\familyLogin.jpg";

//     private static final String LIFELINK_LOGO_IMAGE =
//             "assets\\Images\\LifeLinkLogo.png";

//     // =========================================================
//     // PAGE SIZE
//     // =========================================================

//     private static final double PAGE_WIDTH =
//             Welcome.WelcomeStage.getWidth();

//     private static final double PAGE_HEIGHT =
//             Welcome.WelcomeStage.getHeight();

//     // =========================================================
//     // MAIN PAGE
//     // =========================================================

//     public BorderPane getFamilyLoginPage() {

//         BorderPane root = new BorderPane();

//         root.setPrefSize(PAGE_WIDTH, PAGE_HEIGHT);
//         root.setMinSize(PAGE_WIDTH, PAGE_HEIGHT);
//         root.setMaxSize(PAGE_WIDTH, PAGE_HEIGHT);

//         root.setStyle(
//                 "-fx-background-color: " + GRAY_BG + ";" +
//                 "-fx-font-family: 'Segoe UI';"
//         );

//         AnchorPane page = new AnchorPane();

//         page.setPrefSize(PAGE_WIDTH, PAGE_HEIGHT);
//         page.setMinSize(PAGE_WIDTH, PAGE_HEIGHT);
//         page.setMaxSize(PAGE_WIDTH, PAGE_HEIGHT);

//         page.setStyle(
//                 "-fx-background-color: " + GRAY_BG + ";"
//         );

//         // =====================================================
//         // FAMILY IMAGE
//         // =====================================================

//         ImageView familyImage = createDoctorImage();

//         updateImage(
//                 familyImage,
//                 PAGE_WIDTH,
//                 PAGE_HEIGHT
//         );

//         AnchorPane.setLeftAnchor(
//                 familyImage,
//                 PAGE_WIDTH * 0.44
//         );

//         AnchorPane.setTopAnchor(
//                 familyImage,
//                 0.0
//         );

//         page.getChildren().add(familyImage);

//         // =====================================================
//         // IMAGE OVERLAY
//         // =====================================================

//         Rectangle imageOverlay = new Rectangle();

//         imageOverlay.setWidth(
//                 PAGE_WIDTH * 0.56
//         );

//         imageOverlay.setHeight(
//                 PAGE_HEIGHT
//         );

//         imageOverlay.setFill(
//                 Color.rgb(0, 0, 0, 0.50)
//         );

//         AnchorPane.setLeftAnchor(
//                 imageOverlay,
//                 PAGE_WIDTH * 0.44
//         );

//         AnchorPane.setTopAnchor(
//                 imageOverlay,
//                 0.0
//         );

//         page.getChildren().add(imageOverlay);

//         // =====================================================
//         // BLUE / ORANGE S-SHAPED BACKGROUND
//         // =====================================================

//         Path blueShape = createBlueBackground();

//         updateBlueShape(
//                 blueShape,
//                 PAGE_WIDTH,
//                 PAGE_HEIGHT
//         );

//         AnchorPane.setLeftAnchor(
//                 blueShape,
//                 0.0
//         );

//         AnchorPane.setTopAnchor(
//                 blueShape,
//                 0.0
//         );

//         page.getChildren().add(blueShape);

//         // =====================================================
//         // LOGIN CARD
//         // =====================================================

//         VBox loginCard = createLoginCard();

//         AnchorPane.setLeftAnchor(
//                 loginCard,
//                 150.0
//         );

//         AnchorPane.setTopAnchor(
//                 loginCard,
//                 120.0
//         );

//         page.getChildren().add(loginCard);

//         // =====================================================
//         // LIFELINK LOGO
//         // =====================================================

//         HBox lifeLinkLogo = createLifeLinkLogo();

//         AnchorPane.setRightAnchor(
//                 lifeLinkLogo,
//                 30.0
//         );

//         AnchorPane.setTopAnchor(
//                 lifeLinkLogo,
//                 28.0
//         );

//         page.getChildren().add(lifeLinkLogo);

//         // =====================================================
//         // BACK BUTTON
//         // =====================================================

//         Button backButton = createBackButton();

//         AnchorPane.setLeftAnchor(
//                 backButton,
//                 35.0
//         );

//         AnchorPane.setTopAnchor(
//                 backButton,
//                 30.0
//         );

//         page.getChildren().add(backButton);

//         // =====================================================
//         // BACK BUTTON ACTION
//         // =====================================================

//         backButton.setOnAction(e -> {

//             try {

//                 Welcome welcome = new Welcome();

//                 welcome.start(
//                         Welcome.WelcomeStage
//                 );

//             } catch (Exception ex) {

//                 ex.printStackTrace();
//             }
//         });

//         root.setCenter(page);

//         return root;
//     }

//     // =========================================================
//     // LOGIN CARD
//     // =========================================================

//     private VBox createLoginCard() {

//         VBox card = new VBox();

//         card.setPrefWidth(400);
//         card.setMinWidth(400);
//         card.setMaxWidth(400);

//         setFixedHeight(
//                 card,
//                 560
//         );

//         card.setPadding(
//                 new Insets(
//                         45,
//                         42,
//                         40,
//                         42
//                 )
//         );

//         card.setStyle(
//                 "-fx-background-color: " + WHITE + ";" +
//                 "-fx-background-radius: 38;" +
//                 "-fx-border-radius: 38;" +
//                 "-fx-effect: dropshadow(" +
//                 "gaussian," +
//                 "rgba(0,0,0,0.13)," +
//                 "22," +
//                 "0.12," +
//                 "0," +
//                 "5" +
//                 ");"
//         );

//         showLoginForm(card);

//         return card;
//     }

//     // =========================================================
//     // LOGIN FORM
//     // =========================================================

//     private void showLoginForm(VBox card) {

//         card.getChildren().clear();

//         setFixedHeight(
//                 card,
//                 560
//         );

//         // =====================================================
//         // SIGN IN TITLE
//         // =====================================================

//         Text signIn = new Text("SIGN IN");

//         signIn.setStyle(
//                 "-fx-fill: " + BLACK + ";" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 30px;" +
//                 "-fx-font-weight: bold;"
//         );

//         HBox titleBox = new HBox();

//         titleBox.setAlignment(
//                 Pos.CENTER
//         );

//         titleBox.setPrefHeight(48);

//         titleBox.getChildren().add(
//                 signIn
//         );

//         // =====================================================
//         // TITLE SPACE
//         // =====================================================

//         Region titleSpace =
//                 createSpacer(25);

//         // =====================================================
//         // FAMILY DATA
//         // =====================================================

//         Text familyData =
//                 new Text("family");

//         familyData.setStyle(
//                 "-fx-fill: " + BLACK + ";" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 24px;" +
//                 "-fx-font-style: italic;" +
//                 "-fx-font-weight: bold;"
//         );

//         HBox familyBox = new HBox();

//         familyBox.setAlignment(
//                 Pos.CENTER
//         );

//         setFixedHeight(
//                 familyBox,
//                 50
//         );

//         familyBox.setPadding(
//                 new Insets(
//                         0,
//                         19,
//                         0,
//                         19
//                 )
//         );

//         familyBox.setStyle(
//                 "-fx-background-color: " + WHITE + ";" +
//                 "-fx-border-color: " + BORDER + ";" +
//                 "-fx-border-width: 2;" +
//                 "-fx-border-radius: 10;" +
//                 "-fx-background-radius: 10;"
//         );

//         familyBox.getChildren().add(
//                 familyData
//         );

//         // =====================================================
//         // USERNAME
//         // =====================================================

//         Region space1 =
//                 createSpacer(25);

//         TextField username =
//                 createTextField("Useremail");

//         // =====================================================
//         // PASSWORD
//         // =====================================================

//         Region space2 =
//                 createSpacer(25);

//         PasswordField password =
//                 createPasswordField("Password");

//         // =====================================================
//         // PASSWORD VISIBILITY
//         // =====================================================

//         StackPane passwordBox =
//                 createPasswordVisibilityBox(password);

//         // =====================================================
//         // ACTION SPACE
//         // =====================================================

//         Region actionSpace =
//                 createSpacer(40);

//         // =====================================================
//         // ACTION ROW
//         // =====================================================

//         HBox actionRow = new HBox();

//         actionRow.setAlignment(
//                 Pos.CENTER_LEFT
//         );

//         // =====================================================
//         // LOGIN BUTTON
//         // =====================================================

//         Button loginButton =
//                 new Button("Login");

//         setFixedSize(
//                 loginButton,
//                 113,
//                 50
//         );

//         setLoginButtonStyle(
//                 loginButton,
//                 TEAL
//         );

//         // =====================================================
//         // BUTTON SPACE
//         // =====================================================

//         Region buttonSpace =
//                 new Region();

//         HBox.setHgrow(
//                 buttonSpace,
//                 Priority.ALWAYS
//         );

//         // =====================================================
//         // FORGOT PASSWORD
//         // =====================================================

//         Button forgot =
//                 new Button("Forgot Password?");

//         setForgotButtonStyle(
//                 forgot,
//                 BLACK
//         );

//         actionRow.getChildren().addAll(
//                 loginButton,
//                 buttonSpace,
//                 forgot
//         );

//         // =====================================================
//         // SIGN UP
//         // =====================================================

//         Region signUpSpace =
//                 createSpacer(35);

//         HBox signUpRow =
//                 new HBox();

//         signUpRow.setAlignment(
//                 Pos.CENTER
//         );

//         Text accountText =
//                 new Text(
//                         "Don't have an account? "
//                 );

//         accountText.setStyle(
//                 "-fx-fill: " + BLACK + ";" +
//                 "-fx-font-size: 14px;" +
//                 "-fx-font-family: 'Segoe UI';"
//         );

//         Button signUp =
//                 new Button("Sign Up");

//         setSignUpButtonStyle(
//                 signUp,
//                 TEAL
//         );

//         signUpRow.getChildren().addAll(
//                 accountText,
//                 signUp
//         );

//         // =====================================================
//         // LOGIN ACTION
//         // =====================================================

//         loginButton.setOnAction(e -> {

//             String emailValue =
//                     username.getText().trim();

//             String passwordValue =
//                     password.getText();

//             if (emailValue.isEmpty()
//                     || passwordValue.isEmpty()) {

//                 showMessage(
//                         card,
//                         "Please enter username and password.",
//                         false
//                 );

//                 return;
//             }

//             try {

//                 UserAuthController
//                         userAuthController =
//                         new UserAuthController();

//                 boolean isSuccess =
//                         userAuthController.signIn(
//                                 emailValue,
//                                 passwordValue
//                         );

//                 if (isSuccess) {

//                     System.out.println(
//                             "Family login successful."
//                     );

//                     showSuccessMessage(
//                             card,
//                             emailValue
//                     );

//                 } else {

//                     showMessage(
//                             card,
//                             "Invalid email or password.",
//                             false
//                     );
//                 }

//             } catch (Exception ex) {

//                 ex.printStackTrace();

//                 showMessage(
//                         card,
//                         "Login failed. Please try again.",
//                         false
//                 );
//             }
//         });

//         // =====================================================
//         // FORGOT PASSWORD
//         // =====================================================

//         forgot.setOnAction(e ->
//                 System.out.println(
//                         "Forgot Password clicked."
//                 )
//         );

//         // =====================================================
//         // SIGN UP ACTION
//         // =====================================================

//         signUp.setOnAction(
//                 e -> showSignUpForm(card)
//         );

//         // =====================================================
//         // ADD LOGIN CONTENT
//         // =====================================================

//         card.getChildren().addAll(
//                 titleBox,
//                 titleSpace,
//                 familyBox,
//                 space1,
//                 username,
//                 space2,
//                 passwordBox,
//                 actionSpace,
//                 actionRow,
//                 signUpSpace,
//                 signUpRow
//         );
//     }

//     // =========================================================
//     // PASSWORD VISIBILITY BOX
//     // =========================================================

//     private StackPane createPasswordVisibilityBox(
//             PasswordField password) {

//         StackPane container =
//                 new StackPane();

//         container.setPrefHeight(50);
//         container.setMinHeight(50);
//         container.setMaxHeight(50);

//         TextField visiblePassword =
//                 new TextField();

//         visiblePassword.setPromptText(
//                 "Password"
//         );

//         visiblePassword.setVisible(false);
//         visiblePassword.setManaged(false);

//         visiblePassword.setStyle(
//                 normalFieldStyle()
//         );

//         addFocusStyle(
//                 visiblePassword
//         );

//         Button eyeButton =
//                 new Button("👁");

//         eyeButton.setFocusTraversable(false);

//         eyeButton.setStyle(
//                 "-fx-background-color: transparent;" +
//                 "-fx-text-fill: #777777;" +
//                 "-fx-font-size: 18px;" +
//                 "-fx-cursor: hand;" +
//                 "-fx-padding: 0 12 0 12;"
//         );

//         StackPane.setAlignment(
//                 eyeButton,
//                 Pos.CENTER_RIGHT
//         );

//         eyeButton.setOnMouseEntered(
//                 e -> eyeButton.setStyle(
//                         "-fx-background-color: transparent;" +
//                         "-fx-text-fill: " + TEAL + ";" +
//                         "-fx-font-size: 18px;" +
//                         "-fx-cursor: hand;" +
//                         "-fx-padding: 0 12 0 12;"
//                 )
//         );

//         eyeButton.setOnMouseExited(
//                 e -> eyeButton.setStyle(
//                         "-fx-background-color: transparent;" +
//                         "-fx-text-fill: #777777;" +
//                         "-fx-font-size: 18px;" +
//                         "-fx-cursor: hand;" +
//                         "-fx-padding: 0 12 0 12;"
//                 )
//         );

//         // =====================================================
//         // SHOW / HIDE PASSWORD
//         // =====================================================

//         eyeButton.setOnAction(e -> {

//             if (password.isVisible()) {

//                 visiblePassword.setText(
//                         password.getText()
//                 );

//                 password.setVisible(false);
//                 password.setManaged(false);

//                 visiblePassword.setVisible(true);
//                 visiblePassword.setManaged(true);

//                 eyeButton.setText("🙈");

//             } else {

//                 password.setText(
//                         visiblePassword.getText()
//                 );

//                 visiblePassword.setVisible(false);
//                 visiblePassword.setManaged(false);

//                 password.setVisible(true);
//                 password.setManaged(true);

//                 eyeButton.setText("👁");
//             }
//         });

//         container.getChildren().addAll(
//                 password,
//                 visiblePassword,
//                 eyeButton
//         );

//         return container;
//     }

//     // =========================================================
//     // SIGN UP FORM
//     // =========================================================

//     private void showSignUpForm(VBox card) {

//         card.getChildren().clear();

//         setFixedHeight(
//                 card,
//                 550
//         );

//         // =====================================================
//         // TITLE
//         // =====================================================

//         Text signUpTitle =
//                 new Text("SIGN UP");

//         signUpTitle.setStyle(
//                 "-fx-fill: " + BLACK + ";" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 30px;" +
//                 "-fx-font-weight: bold;"
//         );

//         HBox titleBox =
//                 new HBox();

//         titleBox.setAlignment(
//                 Pos.CENTER
//         );

//         titleBox.setPrefHeight(48);

//         titleBox.getChildren().add(
//                 signUpTitle
//         );

//         // =====================================================
//         // TITLE SPACE
//         // =====================================================

//         Region titleSpace =
//                 createSpacer(25);

//         // =====================================================
//         // NAME
//         // =====================================================

//         TextField name =
//                 createTextField("Name");

//         // =====================================================
//         // EMAIL
//         // =====================================================

//         Region space1 =
//                 createSpacer(18);

//         TextField email =
//                 createTextField("Email");

//         // =====================================================
//         // PASSWORD
//         // =====================================================

//         Region space2 =
//                 createSpacer(18);

//         PasswordField password =
//                 createPasswordField("Password");

//         StackPane passwordBox =
//                 createPasswordVisibilityBox(password);

//         // =====================================================
//         // ACTION SPACE
//         // =====================================================

//         Region actionSpace =
//                 createSpacer(30);

//         // =====================================================
//         // SIGN UP BUTTON
//         // =====================================================

//         Button signUpButton =
//                 new Button("Sign Up");

//         setFixedSize(
//                 signUpButton,
//                 130,
//                 50
//         );

//         setLoginButtonStyle(
//                 signUpButton,
//                 TEAL
//         );

//         HBox buttonBox =
//                 new HBox();

//         buttonBox.setAlignment(
//                 Pos.CENTER
//         );

//         buttonBox.getChildren().add(
//                 signUpButton
//         );

//         // =====================================================
//         // LOGIN SPACE
//         // =====================================================

//         Region loginSpace =
//                 createSpacer(25);

//         // =====================================================
//         // BACK TO LOGIN
//         // =====================================================

//         Button backToLogin =
//                 new Button(
//                         "Already have an account? Login"
//                 );

//         setForgotButtonStyle(
//                 backToLogin,
//                 BLACK
//         );

//         HBox loginBox =
//                 new HBox();

//         loginBox.setAlignment(
//                 Pos.CENTER
//         );

//         loginBox.getChildren().add(
//                 backToLogin
//         );

//         // =====================================================
//         // SIGN UP ACTION
//         // =====================================================

//         signUpButton.setOnAction(e -> {

//             String nameValue =
//                     name.getText().trim();

//             String emailValue =
//                     email.getText().trim();

//             String passwordValue =
//                     password.getText();

//             if (nameValue.isEmpty()
//                     || emailValue.isEmpty()
//                     || passwordValue.isEmpty()) {

//                 showMessage(
//                         card,
//                         "Please enter name, email and password.",
//                         false
//                 );

//                 return;
//             }

//             try {

//                 UserAuthController
//                         userAuthController =
//                         new UserAuthController();

//                 boolean isSuccess =
//                         userAuthController.signUp(
//                                 nameValue,
//                                 emailValue,
//                                 passwordValue
//                         );

//                 if (isSuccess) {

//                     System.out.println(
//                             "API Hit Successfully (SignUp)"
//                     );

//                     UserController
//                             userController =
//                             new UserController();

//                     userController.passToFamilyModel(
//                             nameValue,
//                             emailValue
//                     );

//                     System.out.println(
//                             "========== SIGN UP =========="
//                     );

//                     System.out.println(
//                             "Name: " + nameValue
//                     );

//                     System.out.println(
//                             "Email: " + emailValue
//                     );

//                     System.out.println(
//                             "Sign Up successful."
//                     );

//                     System.out.println(
//                             "============================="
//                     );

//                     showSuccessMessage(
//                             card,
//                             "Account created successfully!"
//                     );

//                 } else {

//                     showMessage(
//                             card,
//                             "Sign up failed. Please try again.",
//                             false
//                     );
//                 }

//             } catch (Exception ex) {

//                 ex.printStackTrace();

//                 showMessage(
//                         card,
//                         "Unable to create account.",
//                         false
//                 );
//             }
//         });

//         // =====================================================
//         // BACK TO LOGIN
//         // =====================================================

//         backToLogin.setOnAction(
//                 e -> showLoginForm(card)
//         );

//         // =====================================================
//         // ADD SIGN UP CONTENT
//         // =====================================================

//         card.getChildren().addAll(
//                 titleBox,
//                 titleSpace,
//                 name,
//                 space1,
//                 email,
//                 space2,
//                 passwordBox,
//                 actionSpace,
//                 buttonBox,
//                 loginSpace,
//                 loginBox
//         );
//     }

//     // =========================================================
//     // SUCCESS MESSAGE
//     // =========================================================

//     private void showSuccessMessage(
//             VBox card,
//             String message) {

//         StackPane overlay =
//                 new StackPane();

//         overlay.setStyle(
//                 "-fx-background-color: rgba(255,255,255,0.97);" +
//                 "-fx-background-radius: 38;"
//         );

//         VBox successBox =
//                 new VBox(18);

//         successBox.setAlignment(
//                 Pos.CENTER
//         );

//         successBox.setPadding(
//                 new Insets(30)
//         );

//         Text successIcon =
//                 new Text("✓");

//         successIcon.setStyle(
//                 "-fx-fill: " + TEAL + ";" +
//                 "-fx-font-size: 55px;" +
//                 "-fx-font-weight: bold;"
//         );

//         Text title =
//                 new Text("Success!");

//         title.setStyle(
//                 "-fx-fill: " + BLACK + ";" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 26px;" +
//                 "-fx-font-weight: bold;"
//         );

//         Label messageLabel =
//                 new Label(message);

//         messageLabel.setWrapText(true);

//         messageLabel.setAlignment(
//                 Pos.CENTER
//         );

//         messageLabel.setStyle(
//                 "-fx-text-fill: #555555;" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 15px;"
//         );

//         Button continueButton =
//                 new Button("Continue");

//         setFixedSize(
//                 continueButton,
//                 130,
//                 45
//         );

//         setLoginButtonStyle(
//                 continueButton,
//                 TEAL
//         );

//         successBox.getChildren().addAll(
//                 successIcon,
//                 title,
//                 messageLabel,
//                 continueButton
//         );

//         overlay.getChildren().add(
//                 successBox
//         );

//         card.getChildren().clear();

//         card.getChildren().add(
//                 overlay
//         );

//         continueButton.setOnAction(e -> {

//             FamilyHomePage familyHomePage =
//                     new FamilyHomePage();

//             familyHomePage.start(
//                     Welcome.WelcomeStage
//             );
//         });
//     }

//     // =========================================================
//     // NORMAL MESSAGE
//     // =========================================================

//     private void showMessage(
//             VBox card,
//             String message,
//             boolean success) {

//         Text messageText =
//                 new Text(message);

//         messageText.setStyle(
//                 "-fx-fill: " +
//                 (success ? TEAL : "#D32F2F") +
//                 ";" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 14px;" +
//                 "-fx-font-weight: bold;"
//         );

//         HBox messageBox =
//                 new HBox();

//         messageBox.setAlignment(
//                 Pos.CENTER
//         );

//         messageBox.getChildren().add(
//                 messageText
//         );

//         // Remove previous message if necessary
//         if (card.getChildren().size() > 0) {
//             card.getChildren().add(
//                     messageBox
//             );
//         }
//     }

//     // =========================================================
//     // TEXT FIELD
//     // =========================================================

//     private TextField createTextField(
//             String prompt) {

//         TextField field =
//                 new TextField();

//         field.setPromptText(
//                 prompt
//         );

//         setFixedHeight(
//                 field,
//                 50
//         );

//         field.setStyle(
//                 normalFieldStyle()
//         );

//         addFocusStyle(
//                 field
//         );

//         return field;
//     }

//     // =========================================================
//     // PASSWORD FIELD
//     // =========================================================

//     private PasswordField createPasswordField(
//             String prompt) {

//         PasswordField field =
//                 new PasswordField();

//         field.setPromptText(
//                 prompt
//         );

//         setFixedHeight(
//                 field,
//                 50
//         );

//         field.setStyle(
//                 normalFieldStyle()
//         );

//         addFocusStyle(
//                 field
//         );

//         return field;
//     }

//     // =========================================================
//     // NORMAL FIELD STYLE
//     // =========================================================

//     private String normalFieldStyle() {

//         return
//                 "-fx-background-color: " + WHITE + ";" +
//                 "-fx-border-color: " + BORDER + ";" +
//                 "-fx-border-width: 2;" +
//                 "-fx-border-radius: 10;" +
//                 "-fx-background-radius: 10;" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 17px;" +
//                 "-fx-text-fill: " + BLACK + ";" +
//                 "-fx-prompt-text-fill: " + PLACEHOLDER + ";" +
//                 "-fx-padding: 0 19;";
//     }

//     // =========================================================
//     // FIELD FOCUS STYLE
//     // =========================================================

//     private void addFocusStyle(
//             TextField field) {

//         field.focusedProperty().addListener(
//                 (obs, oldValue, focused) -> {

//                     if (focused) {

//                         field.setStyle(
//                                 focusedFieldStyle()
//                         );

//                     } else {

//                         field.setStyle(
//                                 normalFieldStyle()
//                         );
//                     }
//                 }
//         );
//     }

//     private String focusedFieldStyle() {

//         return
//                 "-fx-background-color: " + WHITE + ";" +
//                 "-fx-border-color: " + TEAL + ";" +
//                 "-fx-border-width: 2;" +
//                 "-fx-border-radius: 10;" +
//                 "-fx-background-radius: 10;" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 17px;" +
//                 "-fx-text-fill: " + BLACK + ";" +
//                 "-fx-prompt-text-fill: " + PLACEHOLDER + ";" +
//                 "-fx-padding: 0 19;" +
//                 "-fx-effect: dropshadow(" +
//                 "gaussian," +
//                 "rgba(8,127,140,0.18)," +
//                 "8," +
//                 "0," +
//                 "0," +
//                 "0" +
//                 ");";
//     }

//     // =========================================================
//     // LOGIN BUTTON STYLE
//     // =========================================================

//     private void setLoginButtonStyle(
//             Button button,
//             String color) {

//         button.setStyle(
//                 loginButtonStyle(color)
//         );

//         button.setOnMouseEntered(
//                 e -> button.setStyle(
//                         loginButtonStyle(
//                                 TEAL_DARK
//                         )
//                 )
//         );

//         button.setOnMouseExited(
//                 e -> button.setStyle(
//                         loginButtonStyle(color)
//                 )
//         );
//     }

//     private String loginButtonStyle(
//             String color) {

//         return
//                 "-fx-background-color: " + color + ";" +
//                 "-fx-text-fill: white;" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 15px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 10;" +
//                 "-fx-border-radius: 10;" +
//                 "-fx-cursor: hand;";
//     }

//     // =========================================================
//     // SIGN UP BUTTON STYLE
//     // =========================================================

//     private void setSignUpButtonStyle(
//             Button button,
//             String color) {

//         button.setStyle(
//                 linkButtonStyle(
//                         color,
//                         14
//                 )
//         );

//         button.setOnMouseEntered(
//                 e -> button.setStyle(
//                         linkButtonStyle(
//                                 TEAL_DARK,
//                                 14
//                         )
//                 )
//         );

//         button.setOnMouseExited(
//                 e -> button.setStyle(
//                         linkButtonStyle(
//                                 color,
//                                 14
//                         )
//                 )
//         );
//     }

//     // =========================================================
//     // FORGOT / BACK BUTTON STYLE
//     // =========================================================

//     private void setForgotButtonStyle(
//             Button button,
//             String color) {

//         button.setStyle(
//                 linkButtonStyle(
//                         color,
//                         15
//                 )
//         );

//         button.setOnMouseEntered(
//                 e -> button.setStyle(
//                         linkButtonStyle(
//                                 TEAL_DARK,
//                                 15
//                         )
//                 )
//         );

//         button.setOnMouseExited(
//                 e -> button.setStyle(
//                         linkButtonStyle(
//                                 color,
//                                 15
//                         )
//                 )
//         );
//     }

//     private String linkButtonStyle(
//             String color,
//             int fontSize) {

//         return
//                 "-fx-background-color: transparent;" +
//                 "-fx-text-fill: " + color + ";" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: " + fontSize + "px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-cursor: hand;" +
//                 "-fx-padding: 5 0 5 5;";
//     }

//     // =========================================================
//     // BACK BUTTON
//     // =========================================================

//     private Button createBackButton() {

//         Button backButton =
//                 new Button("←  Back");

//         setFixedSize(
//                 backButton,
//                 100,
//                 42
//         );

//         backButton.setStyle(
//                 backButtonStyle()
//         );

//         backButton.setOnMouseEntered(
//                 e -> backButton.setStyle(
//                         backButtonHoverStyle()
//                 )
//         );

//         backButton.setOnMouseExited(
//                 e -> backButton.setStyle(
//                         backButtonStyle()
//                 )
//         );

//         return backButton;
//     }

//     private String backButtonStyle() {

//         return
//                 "-fx-background-color: rgba(255,255,255,0.15);" +
//                 "-fx-text-fill: white;" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 15px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 10;" +
//                 "-fx-border-color: rgba(255,255,255,0.55);" +
//                 "-fx-border-width: 1;" +
//                 "-fx-border-radius: 10;" +
//                 "-fx-cursor: hand;";
//     }

//     private String backButtonHoverStyle() {

//         return
//                 "-fx-background-color: white;" +
//                 "-fx-text-fill: " + TEAL_DARK + ";" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 15px;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 10;" +
//                 "-fx-border-color: white;" +
//                 "-fx-border-width: 1;" +
//                 "-fx-border-radius: 10;" +
//                 "-fx-cursor: hand;";
//     }

//     // =========================================================
//     // SPACER
//     // =========================================================

//     private Region createSpacer(
//             double height) {

//         Region spacer =
//                 new Region();

//         spacer.setPrefHeight(
//                 height
//         );

//         return spacer;
//     }

//     // =========================================================
//     // FIXED SIZE
//     // =========================================================

//     private void setFixedSize(
//             Region node,
//             double width,
//             double height) {

//         node.setPrefWidth(width);
//         node.setMinWidth(width);
//         node.setMaxWidth(width);

//         node.setPrefHeight(height);
//         node.setMinHeight(height);
//         node.setMaxHeight(height);
//     }

//     // =========================================================
//     // FIXED HEIGHT
//     // =========================================================

//     private void setFixedHeight(
//             Region node,
//             double height) {

//         node.setPrefHeight(height);
//         node.setMinHeight(height);
//         node.setMaxHeight(height);
//     }

//     // =========================================================
//     // BLUE / ORANGE BACKGROUND
//     // =========================================================

//     private Path createBlueBackground() {

//         Path blueShape =
//                 new Path();

//         blueShape.setFill(
//                 Color.web(TEAL)
//         );

//         blueShape.setStroke(
//                 Color.TRANSPARENT
//         );

//         return blueShape;
//     }

//     // =========================================================
//     // UPDATE BLUE BACKGROUND
//     // =========================================================

//     private void updateBlueShape(
//             Path shape,
//             double width,
//             double height) {

//         shape.getElements().clear();

//         double blueWidth =
//                 width * 0.53;

//         double curveAmount =
//                 130;

//         shape.getElements().add(
//                 new MoveTo(
//                         0,
//                         0
//                 )
//         );

//         shape.getElements().add(
//                 new LineTo(
//                         blueWidth,
//                         0
//                 )
//         );

//         shape.getElements().add(
//                 new CubicCurveTo(
//                         blueWidth + curveAmount,
//                         height * 0.14,

//                         blueWidth + curveAmount,
//                         height * 0.32,

//                         blueWidth,
//                         height * 0.50
//                 )
//         );

//         shape.getElements().add(
//                 new CubicCurveTo(
//                         blueWidth - curveAmount,
//                         height * 0.68,

//                         blueWidth - curveAmount,
//                         height * 0.86,

//                         blueWidth,
//                         height
//                 )
//         );

//         shape.getElements().add(
//                 new LineTo(
//                         0,
//                         height
//                 )
//         );

//         shape.getElements().add(
//                 new ClosePath()
//         );
//     }

//     // =========================================================
//     // FAMILY IMAGE
//     // =========================================================

//     private ImageView createDoctorImage() {

//         ImageView imageView =
//                 new ImageView();

//         try {

//             Image image =
//                     new Image(
//                             DOCTOR_IMAGE_URL,
//                             false
//                     );

//             imageView.setImage(
//                     image
//             );

//         } catch (Exception e) {

//             System.out.println(
//                     "Could not load family image: "
//                             + e.getMessage()
//             );
//         }

//         imageView.setPreserveRatio(
//                 false
//         );

//         imageView.setSmooth(
//                 true
//         );

//         imageView.setCache(
//                 true
//         );

//         return imageView;
//     }

//     // =========================================================
//     // IMAGE SIZE
//     // =========================================================

//     private void updateImage(
//             ImageView imageView,
//             double width,
//             double height) {

//         double imageWidth =
//                 width * 0.56;

//         imageView.setFitWidth(
//                 imageWidth
//         );

//         imageView.setFitHeight(
//                 height
//         );

//         imageView.setPreserveRatio(
//                 false
//         );

//         imageView.setSmooth(
//                 true
//         );
//     }

//     // =========================================================
//     // LIFELINK LOGO
//     // =========================================================

//     private HBox createLifeLinkLogo() {

//         HBox logo =
//                 new HBox(9);

//         logo.setAlignment(
//                 Pos.CENTER_RIGHT
//         );

//         // =====================================================
//         // LOGO IMAGE
//         // =====================================================

//         ImageView icon =
//                 new ImageView();

//         try {

//             Image logoImage =
//                     new Image(
//                             LIFELINK_LOGO_IMAGE,
//                             false
//                     );

//             icon.setImage(
//                     logoImage
//             );

//         } catch (Exception e) {

//             System.out.println(
//                     "Could not load LifeLink logo: "
//                             + e.getMessage()
//             );
//         }

//         icon.setFitWidth(55);
//         icon.setFitHeight(55);

//         icon.setPreserveRatio(
//                 true
//         );

//         icon.setSmooth(
//                 true
//         );

//         // =====================================================
//         // LIFE
//         // =====================================================

//         Text life =
//                 new Text("Life");

//         life.setStyle(
//                 "-fx-fill: white;" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 33px;" +
//                 "-fx-font-weight: bold;"
//         );

//         // =====================================================
//         // LINK
//         // =====================================================

//         Text link =
//                 new Text("Link");

//         link.setStyle(
//                 "-fx-fill: " + TEAL_LIGHT + ";" +
//                 "-fx-font-family: 'Segoe UI';" +
//                 "-fx-font-size: 33px;" +
//                 "-fx-font-weight: bold;"
//         );

//         // =====================================================
//         // TEXT
//         // =====================================================

//         HBox text =
//                 new HBox(0);

//         text.getChildren().addAll(
//                 life,
//                 link
//         );

//         // =====================================================
//         // LOGO
//         // =====================================================

//         logo.getChildren().addAll(
//                 icon,
//                 text
//         );

//         return logo;
//     }
// }

package com.kurukshetra.view.loginSignup;

import com.kurukshetra.controller.UserAuthController;
import com.kurukshetra.controller.UserController;
import com.kurukshetra.view.Welcome;
import com.kurukshetra.view.family.FamilyHomePage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class FamilyLoginPage {

    // =========================================================
    // COLORS
    // =========================================================

    private static final String TEAL = "#ca6721";
    private static final String TEAL_DARK = "#782F16";
    private static final String TEAL_LIGHT = "#63D7DC";

    private static final String WHITE = "#FFFFFF";
    private static final String BLACK = "#111111";
    private static final String GRAY_BG = "#777775";
    private static final String BORDER = "#D7DBDF";
    private static final String PLACEHOLDER = "#A7A9AC";

    // =========================================================
    // IMAGES
    // =========================================================

    private static final String DOCTOR_IMAGE_URL =
            "assets\\Images\\familyLogin.jpg";

    private static final String LIFELINK_LOGO_IMAGE =
            "assets\\Images\\LifeLinkLogo.png";

    // =========================================================
    // PAGE SIZE
    // =========================================================

    private static final double PAGE_WIDTH =
            Welcome.WelcomeStage.getWidth();

    private static final double PAGE_HEIGHT =
            Welcome.WelcomeStage.getHeight();

    // =========================================================
    // MAIN PAGE
    // =========================================================

    public BorderPane getFamilyLoginPage() {

        BorderPane root =
                new BorderPane();

        root.setPrefSize(
                PAGE_WIDTH,
                PAGE_HEIGHT
        );

        root.setMinSize(
                PAGE_WIDTH,
                PAGE_HEIGHT
        );

        root.setMaxSize(
                PAGE_WIDTH,
                PAGE_HEIGHT
        );

        root.setStyle(
                "-fx-background-color: " + GRAY_BG + ";" +
                "-fx-font-family: 'Segoe UI';"
        );

        AnchorPane page =
                new AnchorPane();

        page.setPrefSize(
                PAGE_WIDTH,
                PAGE_HEIGHT
        );

        page.setMinSize(
                PAGE_WIDTH,
                PAGE_HEIGHT
        );

        page.setMaxSize(
                PAGE_WIDTH,
                PAGE_HEIGHT
        );

        page.setStyle(
                "-fx-background-color: " + GRAY_BG + ";"
        );

        // =====================================================
        // FAMILY IMAGE
        // =====================================================

        ImageView familyImage =
                createDoctorImage();

        updateImage(
                familyImage,
                PAGE_WIDTH,
                PAGE_HEIGHT
        );

        AnchorPane.setLeftAnchor(
                familyImage,
                PAGE_WIDTH * 0.44
        );

        AnchorPane.setTopAnchor(
                familyImage,
                0.0
        );

        page.getChildren().add(
                familyImage
        );

        // =====================================================
        // IMAGE OVERLAY
        // =====================================================

        Rectangle imageOverlay =
                new Rectangle();

        imageOverlay.setWidth(
                PAGE_WIDTH * 0.56
        );

        imageOverlay.setHeight(
                PAGE_HEIGHT
        );

        imageOverlay.setFill(
                Color.rgb(
                        0,
                        0,
                        0,
                        0.50
                )
        );

        AnchorPane.setLeftAnchor(
                imageOverlay,
                PAGE_WIDTH * 0.44
        );

        AnchorPane.setTopAnchor(
                imageOverlay,
                0.0
        );

        page.getChildren().add(
                imageOverlay
        );

        // =====================================================
        // ORANGE S-SHAPED BACKGROUND
        // =====================================================

        Path blueShape =
                createBlueBackground();

        updateBlueShape(
                blueShape,
                PAGE_WIDTH,
                PAGE_HEIGHT
        );

        AnchorPane.setLeftAnchor(
                blueShape,
                0.0
        );

        AnchorPane.setTopAnchor(
                blueShape,
                0.0
        );

        page.getChildren().add(
                blueShape
        );

        // =====================================================
        // LOGIN CARD
        // =====================================================

        VBox loginCard =
                createLoginCard();

        AnchorPane.setLeftAnchor(
                loginCard,
                150.0
        );

        AnchorPane.setTopAnchor(
                loginCard,
                120.0
        );

        page.getChildren().add(
                loginCard
        );

        // =====================================================
        // LIFELINK LOGO
        // =====================================================

        HBox lifeLinkLogo =
                createLifeLinkLogo();

        AnchorPane.setRightAnchor(
                lifeLinkLogo,
                30.0
        );

        AnchorPane.setTopAnchor(
                lifeLinkLogo,
                28.0
        );

        page.getChildren().add(
                lifeLinkLogo
        );

        // =====================================================
        // BACK BUTTON
        // =====================================================

        Button backButton =
                createBackButton();

        AnchorPane.setLeftAnchor(
                backButton,
                35.0
        );

        AnchorPane.setTopAnchor(
                backButton,
                30.0
        );

        page.getChildren().add(
                backButton
        );

        // =====================================================
        // BACK BUTTON ACTION
        // =====================================================

        backButton.setOnAction(e -> {

            try {

                Welcome welcome =
                        new Welcome();

                welcome.start(
                        Welcome.WelcomeStage
                );

            } catch (Exception ex) {

                ex.printStackTrace();
            }
        });

        root.setCenter(page);

        return root;
    }

    // =========================================================
    // LOGIN CARD
    // =========================================================

    private VBox createLoginCard() {

        VBox card =
                new VBox();

        card.setPrefWidth(400);
        card.setMinWidth(400);
        card.setMaxWidth(400);

        setFixedHeight(
                card,
                560
        );

        card.setPadding(
                new Insets(
                        45,
                        42,
                        40,
                        42
                )
        );

        card.setStyle(
                "-fx-background-color: " + WHITE + ";" +
                "-fx-background-radius: 38;" +
                "-fx-border-radius: 38;" +
                "-fx-effect: dropshadow(" +
                "gaussian," +
                "rgba(0,0,0,0.13)," +
                "22," +
                "0.12," +
                "0," +
                "5" +
                ");"
        );

        showLoginForm(card);

        return card;
    }

    // =========================================================
    // LOGIN FORM
    // =========================================================

    private void showLoginForm(VBox card) {

        card.getChildren().clear();

        setFixedHeight(
                card,
                560
        );

        // =====================================================
        // SIGN IN TITLE
        // =====================================================

        Text signIn =
                new Text("SIGN IN");

        signIn.setStyle(
                "-fx-fill: " + BLACK + ";" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 30px;" +
                "-fx-font-weight: bold;"
        );

        HBox titleBox =
                new HBox();

        titleBox.setAlignment(
                Pos.CENTER
        );

        titleBox.setPrefHeight(
                48
        );

        titleBox.getChildren().add(
                signIn
        );

        // =====================================================
        // TITLE SPACE
        // =====================================================

        Region titleSpace =
                createSpacer(25);

        // =====================================================
        // FAMILY DATA
        // =====================================================

        Text familyData =
                new Text("family");

        familyData.setStyle(
                "-fx-fill: " + BLACK + ";" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 24px;" +
                "-fx-font-style: italic;" +
                "-fx-font-weight: bold;"
        );

        HBox familyBox =
                new HBox();

        familyBox.setAlignment(
                Pos.CENTER
        );

        setFixedHeight(
                familyBox,
                50
        );

        familyBox.setPadding(
                new Insets(
                        0,
                        19,
                        0,
                        19
                )
        );

        familyBox.setStyle(
                "-fx-background-color: " + WHITE + ";" +
                // "-fx-border-color: " + BORDER + ";" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;"
        );

        familyBox.getChildren().add(
                familyData
        );

        // =====================================================
        // USERNAME
        // =====================================================

        Region space1 =
                createSpacer(25);

        TextField username =
                createTextField(
                        "Useremail"
                );

        // =====================================================
        // PASSWORD
        // =====================================================

        Region space2 =
                createSpacer(25);

        PasswordField password =
                createPasswordField(
                        "Password"
                );

        // =====================================================
        // PASSWORD VISIBILITY
        // =====================================================

        StackPane passwordBox =
                createPasswordVisibilityBox(
                        password
                );

        // =====================================================
        // ACTION SPACE
        // =====================================================

        Region actionSpace =
                createSpacer(40);

        // =====================================================
        // ACTION ROW
        // =====================================================

        HBox actionRow =
                new HBox();

        actionRow.setAlignment(
                Pos.CENTER_LEFT
        );

        // =====================================================
        // LOGIN BUTTON
        // =====================================================

        Button loginButton =
                new Button("Login");

        setFixedSize(
                loginButton,
                113,
                50
        );

        setLoginButtonStyle(
                loginButton,
                TEAL
        );

        // =====================================================
        // BUTTON SPACE
        // =====================================================

        Region buttonSpace =
                new Region();

        HBox.setHgrow(
                buttonSpace,
                Priority.ALWAYS
        );

        // =====================================================
        // FORGOT PASSWORD
        // =====================================================

        Button forgot =
                new Button(
                        "Forgot Password?"
                );

        setForgotButtonStyle(
                forgot,
                BLACK
        );

        actionRow.getChildren().addAll(
                loginButton,
                buttonSpace,
                forgot
        );

        // =====================================================
        // SIGN UP
        // =====================================================

        Region signUpSpace =
                createSpacer(35);

        HBox signUpRow =
                new HBox();

        signUpRow.setAlignment(
                Pos.CENTER
        );

        Text accountText =
                new Text(
                        "Don't have an account? "
                );

        accountText.setStyle(
                "-fx-fill: " + BLACK + ";" +
                "-fx-font-size: 14px;" +
                "-fx-font-family: 'Segoe UI';"
        );

        Button signUp =
                new Button(
                        "Sign Up"
                );

        setSignUpButtonStyle(
                signUp,
                TEAL
        );

        signUpRow.getChildren().addAll(
                accountText,
                signUp
        );

        // =====================================================
        // LOGIN ACTION
        // =====================================================

        loginButton.setOnAction(e -> {

            String emailValue =
                    username.getText().trim();

            String passwordValue =
                    password.getText();

            if (emailValue.isEmpty()
                    || passwordValue.isEmpty()) {

                showMessage(
                        card,
                        "Please enter username and password.",
                        false
                );

                return;
            }

            try {

                UserAuthController
                        userAuthController =
                        new UserAuthController();

                boolean isSuccess =
                        userAuthController.signIn(
                                emailValue,
                                passwordValue
                        );

                if (isSuccess) {

                    System.out.println(
                            "Family login successful."
                    );

                    showSuccessMessage(
                            card,
                            emailValue
                    );

                } else {

                    showMessage(
                            card,
                            "Invalid email or password.",
                            false
                    );
                }

            } catch (Exception ex) {

                ex.printStackTrace();

                showMessage(
                        card,
                        "Login failed. Please try again.",
                        false
                );
            }
        });

        // =====================================================
        // FORGOT PASSWORD
        // =====================================================

        forgot.setOnAction(e ->
                System.out.println(
                        "Forgot Password clicked."
                )
        );

        // =====================================================
        // SIGN UP ACTION
        // =====================================================

        signUp.setOnAction(
                e -> showSignUpForm(card)
        );

        // =====================================================
        // ADD LOGIN CONTENT
        // =====================================================

        card.getChildren().addAll(
                titleBox,
                titleSpace,
                familyBox,
                space1,
                username,
                space2,
                passwordBox,
                actionSpace,
                actionRow,
                signUpSpace,
                signUpRow
        );
    }

    // =========================================================
    // PASSWORD VISIBILITY BOX
    // Same implementation/style as HospitalLoginPage
    // =========================================================

    private StackPane createPasswordVisibilityBox(
            PasswordField password) {

        StackPane container =
                new StackPane();

        setFixedHeight(
                container,
                50
        );

        // =====================================================
        // VISIBLE PASSWORD FIELD
        // =====================================================

        TextField visiblePassword =
                new TextField();

        visiblePassword.setPromptText(
                "Password"
        );

        setFixedHeight(
                visiblePassword,
                50
        );

        visiblePassword.setStyle(
                normalFieldStyle()
        );

        addFocusStyle(
                visiblePassword
        );

        visiblePassword.setVisible(false);
        visiblePassword.setManaged(false);

        // =====================================================
        // EYE BUTTON
        // =====================================================

        Button eyeButton =
                new Button("👁");

        eyeButton.setFocusTraversable(
                false
        );

        eyeButton.setStyle(
                eyeButtonStyle(
                        PLACEHOLDER
                )
        );

        StackPane.setAlignment(
                eyeButton,
                Pos.CENTER_RIGHT
        );

        StackPane.setMargin(
                eyeButton,
                new Insets(
                        0,
                        8,
                        0,
                        0
                )
        );

        // =====================================================
        // EYE HOVER
        // =====================================================

        eyeButton.setOnMouseEntered(
                e -> eyeButton.setStyle(
                        eyeButtonStyle(
                                TEAL
                        )
                )
        );

        eyeButton.setOnMouseExited(
                e -> {

                    String color =
                            visiblePassword.isVisible()
                                    ? TEAL
                                    : PLACEHOLDER;

                    eyeButton.setStyle(
                            eyeButtonStyle(
                                    color
                            )
                    );
                }
        );

        // =====================================================
        // SHOW / HIDE
        // =====================================================

        eyeButton.setOnAction(e -> {

            if (password.isVisible()) {

                // ---------------------------------------------
                // PASSWORD -> VISIBLE TEXT
                // ---------------------------------------------

                visiblePassword.setText(
                        password.getText()
                );

                password.setVisible(
                        false
                );

                password.setManaged(
                        false
                );

                visiblePassword.setVisible(
                        true
                );

                visiblePassword.setManaged(
                        true
                );

                // ---------------------------------------------
                // CHANGE ICON
                // ---------------------------------------------

                eyeButton.setText(
                        "🙈"
                );

                eyeButton.setStyle(
                        eyeButtonStyle(
                                TEAL
                        )
                );

                // ---------------------------------------------
                // KEEP FOCUS
                // ---------------------------------------------

                visiblePassword.requestFocus();

                visiblePassword.positionCaret(
                        visiblePassword.getText().length()
                );

            } else {

                // ---------------------------------------------
                // VISIBLE TEXT -> PASSWORD
                // ---------------------------------------------

                password.setText(
                        visiblePassword.getText()
                );

                visiblePassword.setVisible(
                        false
                );

                visiblePassword.setManaged(
                        false
                );

                password.setVisible(
                        true
                );

                password.setManaged(
                        true
                );

                // ---------------------------------------------
                // CHANGE ICON
                // ---------------------------------------------

                eyeButton.setText(
                        "👁"
                );

                eyeButton.setStyle(
                        eyeButtonStyle(
                                PLACEHOLDER
                        )
                );

                // ---------------------------------------------
                // KEEP FOCUS
                // ---------------------------------------------

                password.requestFocus();

                password.positionCaret(
                        password.getText().length()
                );
            }
        });

        // =====================================================
        // ADD CHILDREN
        // =====================================================

        container.getChildren().addAll(
                password,
                visiblePassword,
                eyeButton
        );

        return container;
    }

    // =========================================================
    // EYE BUTTON STYLE
    // =========================================================

    private String eyeButtonStyle(
            String color) {

        return
                "-fx-background-color: transparent;" +
                "-fx-text-fill: " + color + ";" +
                "-fx-font-size: 18px;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 5 8 5 8;";
    }

    // =========================================================
    // SIGN UP FORM
    // =========================================================

    private void showSignUpForm(VBox card) {

        card.getChildren().clear();

        setFixedHeight(
                card,
                550
        );

        // =====================================================
        // TITLE
        // =====================================================

        Text signUpTitle =
                new Text("SIGN UP");

        signUpTitle.setStyle(
                "-fx-fill: " + BLACK + ";" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 30px;" +
                "-fx-font-weight: bold;"
        );

        HBox titleBox =
                new HBox();

        titleBox.setAlignment(
                Pos.CENTER
        );

        titleBox.setPrefHeight(
                48
        );

        titleBox.getChildren().add(
                signUpTitle
        );

        // =====================================================
        // TITLE SPACE
        // =====================================================

        Region titleSpace =
                createSpacer(25);

        // =====================================================
        // NAME
        // =====================================================

        TextField name =
                createTextField(
                        "Name"
                );

        // =====================================================
        // EMAIL
        // =====================================================

        Region space1 =
                createSpacer(18);

        TextField email =
                createTextField(
                        "Email"
                );

        // =====================================================
        // PASSWORD
        // =====================================================

        Region space2 =
                createSpacer(18);

        PasswordField password =
                createPasswordField(
                        "Password"
                );

        // =====================================================
        // PASSWORD VISIBILITY
        // =====================================================

        StackPane passwordBox =
                createPasswordVisibilityBox(
                        password
                );

        // =====================================================
        // ACTION SPACE
        // =====================================================

        Region actionSpace =
                createSpacer(30);

        // =====================================================
        // SIGN UP BUTTON
        // =====================================================

        Button signUpButton =
                new Button(
                        "Sign Up"
                );

        setFixedSize(
                signUpButton,
                130,
                50
        );

        setLoginButtonStyle(
                signUpButton,
                TEAL
        );

        HBox buttonBox =
                new HBox();

        buttonBox.setAlignment(
                Pos.CENTER
        );

        buttonBox.getChildren().add(
                signUpButton
        );

        // =====================================================
        // LOGIN SPACE
        // =====================================================

        Region loginSpace =
                createSpacer(25);

        // =====================================================
        // BACK TO LOGIN
        // =====================================================

        Button backToLogin =
                new Button(
                        "Already have an account? Login"
                );

        setForgotButtonStyle(
                backToLogin,
                BLACK
        );

        HBox loginBox =
                new HBox();

        loginBox.setAlignment(
                Pos.CENTER
        );

        loginBox.getChildren().add(
                backToLogin
        );

        // =====================================================
        // SIGN UP ACTION
        // =====================================================

        signUpButton.setOnAction(e -> {

            String nameValue =
                    name.getText().trim();

            String emailValue =
                    email.getText().trim();

            String passwordValue =
                    password.getText();

            if (nameValue.isEmpty()
                    || emailValue.isEmpty()
                    || passwordValue.isEmpty()) {

                showMessage(
                        card,
                        "Please enter name, email and password.",
                        false
                );

                return;
            }

            try {

                UserAuthController
                        userAuthController =
                        new UserAuthController();

                boolean isSuccess =
                        userAuthController.signUp(
                                nameValue,
                                emailValue,
                                passwordValue
                        );

                if (isSuccess) {

                    System.out.println(
                            "API Hit Successfully (SignUp)"
                    );

                    UserController
                            userController =
                            new UserController();

                    userController.passToFamilyModel(
                            nameValue,
                            emailValue
                    );

                    System.out.println(
                            "========== SIGN UP =========="
                    );

                    System.out.println(
                            "Name: " + nameValue
                    );

                    System.out.println(
                            "Email: " + emailValue
                    );

                    System.out.println(
                            "Sign Up successful."
                    );

                    System.out.println(
                            "============================="
                    );

                    showSuccessMessage(
                            card,
                            "Account created successfully!"
                    );

                } else {

                    showMessage(
                            card,
                            "Sign up failed. Please try again.",
                            false
                    );
                }

            } catch (Exception ex) {

                ex.printStackTrace();

                showMessage(
                        card,
                        "Unable to create account.",
                        false
                );
            }
        });

        // =====================================================
        // BACK TO LOGIN
        // =====================================================

        backToLogin.setOnAction(
                e -> showLoginForm(card)
        );

        // =====================================================
        // ADD SIGN UP CONTENT
        // =====================================================

        card.getChildren().addAll(
                titleBox,
                titleSpace,
                name,
                space1,
                email,
                space2,
                passwordBox,
                actionSpace,
                buttonBox,
                loginSpace,
                loginBox
        );
    }

    // =========================================================
    // SUCCESS MESSAGE
    // =========================================================

    private void showSuccessMessage(
            VBox card,
            String message) {

        StackPane overlay =
                new StackPane();

        overlay.setStyle(
                "-fx-background-color: rgba(255,255,255,0.97);" +
                "-fx-background-radius: 38;"
        );

        VBox successBox =
                new VBox(18);

        successBox.setAlignment(
                Pos.CENTER
        );

        successBox.setPadding(
                new Insets(30)
        );

        Text successIcon =
                new Text("✓");

        successIcon.setStyle(
                "-fx-fill: " + TEAL + ";" +
                "-fx-font-size: 55px;" +
                "-fx-font-weight: bold;"
        );

        Text title =
                new Text("Success!");

        title.setStyle(
                "-fx-fill: " + BLACK + ";" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;"
        );

        Label messageLabel =
                new Label(message);

        messageLabel.setWrapText(
                true
        );

        messageLabel.setAlignment(
                Pos.CENTER
        );

        messageLabel.setStyle(
                "-fx-text-fill: #555555;" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 15px;"
        );

        Button continueButton =
                new Button(
                        "Continue"
                );

        setFixedSize(
                continueButton,
                130,
                45
        );

        setLoginButtonStyle(
                continueButton,
                TEAL
        );

        successBox.getChildren().addAll(
                successIcon,
                title,
                messageLabel,
                continueButton
        );

        overlay.getChildren().add(
                successBox
        );

        card.getChildren().clear();

        card.getChildren().add(
                overlay
        );

        continueButton.setOnAction(e -> {

            FamilyHomePage familyHomePage =
                    new FamilyHomePage();

            familyHomePage.start(
                    Welcome.WelcomeStage
            );
        });
    }

    // =========================================================
    // NORMAL MESSAGE
    // =========================================================

    private void showMessage(
            VBox card,
            String message,
            boolean success) {

        Text messageText =
                new Text(message);

        messageText.setStyle(
                "-fx-fill: " +
                (success
                        ? TEAL
                        : "#D32F2F") +
                ";" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;"
        );

        HBox messageBox =
                new HBox();

        messageBox.setAlignment(
                Pos.CENTER
        );

        messageBox.getChildren().add(
                messageText
        );

        if (card.getChildren().size() > 0) {

            card.getChildren().add(
                    messageBox
            );
        }
    }

    // =========================================================
    // TEXT FIELD
    // =========================================================

    private TextField createTextField(
            String prompt) {

        TextField field =
                new TextField();

        field.setPromptText(
                prompt
        );

        setFixedHeight(
                field,
                50
        );

        field.setStyle(
                normalFieldStyle()
        );

        addFocusStyle(
                field
        );

        return field;
    }

    // =========================================================
    // PASSWORD FIELD
    // =========================================================

    private PasswordField createPasswordField(
            String prompt) {

        PasswordField field =
                new PasswordField();

        field.setPromptText(
                prompt
        );

        setFixedHeight(
                field,
                50
        );

        field.setStyle(
                normalFieldStyle()
        );

        addFocusStyle(
                field
        );

        return field;
    }

    // =========================================================
    // NORMAL FIELD STYLE
    // =========================================================

    private String normalFieldStyle() {

        return
                "-fx-background-color: " + WHITE + ";" +
                "-fx-border-color: " + BORDER + ";" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 17px;" +
                "-fx-text-fill: " + BLACK + ";" +
                "-fx-prompt-text-fill: " + PLACEHOLDER + ";" +
                "-fx-padding: 0 40 0 19;";
    }

    // =========================================================
    // FIELD FOCUS STYLE
    // =========================================================

    private void addFocusStyle(
            TextField field) {

        field.focusedProperty().addListener(
                (obs, oldValue, focused) -> {

                    if (focused) {

                        field.setStyle(
                                focusedFieldStyle()
                        );

                    } else {

                        field.setStyle(
                                normalFieldStyle()
                        );
                    }
                }
        );
    }

    private String focusedFieldStyle() {

        return
                "-fx-background-color: " + WHITE + ";" +
                "-fx-border-color: " + TEAL + ";" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 17px;" +
                "-fx-text-fill: " + BLACK + ";" +
                "-fx-prompt-text-fill: " + PLACEHOLDER + ";" +
                "-fx-padding: 0 40 0 19;" +
                "-fx-effect: dropshadow(" +
                "gaussian," +
                "rgba(202,103,33,0.18)," +
                "8," +
                "0," +
                "0," +
                "0" +
                ");";
    }

    // =========================================================
    // LOGIN BUTTON STYLE
    // =========================================================

    private void setLoginButtonStyle(
            Button button,
            String color) {

        button.setStyle(
                loginButtonStyle(color)
        );

        button.setOnMouseEntered(
                e -> button.setStyle(
                        loginButtonStyle(
                                TEAL_DARK
                        )
                )
        );

        button.setOnMouseExited(
                e -> button.setStyle(
                        loginButtonStyle(
                                color
                        )
                )
        );
    }

    private String loginButtonStyle(
            String color) {

        return
                "-fx-background-color: " + color + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-cursor: hand;";
    }

    // =========================================================
    // SIGN UP BUTTON STYLE
    // =========================================================

    private void setSignUpButtonStyle(
            Button button,
            String color) {

        button.setStyle(
                linkButtonStyle(
                        color,
                        14
                )
        );

        button.setOnMouseEntered(
                e -> button.setStyle(
                        linkButtonStyle(
                                TEAL_DARK,
                                14
                        )
                )
        );

        button.setOnMouseExited(
                e -> button.setStyle(
                        linkButtonStyle(
                                color,
                                14
                        )
                )
        );
    }

    // =========================================================
    // FORGOT / BACK BUTTON STYLE
    // =========================================================

    private void setForgotButtonStyle(
            Button button,
            String color) {

        button.setStyle(
                linkButtonStyle(
                        color,
                        15
                )
        );

        button.setOnMouseEntered(
                e -> button.setStyle(
                        linkButtonStyle(
                                TEAL_DARK,
                                15
                        )
                )
        );

        button.setOnMouseExited(
                e -> button.setStyle(
                        linkButtonStyle(
                                color,
                                15
                        )
                )
        );
    }

    private String linkButtonStyle(
            String color,
            int fontSize) {

        return
                "-fx-background-color: transparent;" +
                "-fx-text-fill: " + color + ";" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: " + fontSize + "px;" +
                "-fx-font-weight: bold;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 5 0 5 5;";
    }

    // =========================================================
    // BACK BUTTON
    // =========================================================

    private Button createBackButton() {

        Button backButton =
                new Button(
                        "←  Back"
                );

        setFixedSize(
                backButton,
                100,
                42
        );

        backButton.setStyle(
                backButtonStyle()
        );

        backButton.setOnMouseEntered(
                e -> backButton.setStyle(
                        backButtonHoverStyle()
                )
        );

        backButton.setOnMouseExited(
                e -> backButton.setStyle(
                        backButtonStyle()
                )
        );

        return backButton;
    }

    private String backButtonStyle() {

        return
                "-fx-background-color: rgba(255,255,255,0.15);" +
                "-fx-text-fill: white;" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: rgba(255,255,255,0.55);" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10;" +
                "-fx-cursor: hand;";
    }

    private String backButtonHoverStyle() {

        return
                "-fx-background-color: white;" +
                "-fx-text-fill: " + TEAL_DARK + ";" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 10;" +
                "-fx-cursor: hand;";
    }

    // =========================================================
    // SPACER
    // =========================================================

    private Region createSpacer(
            double height) {

        Region spacer =
                new Region();

        spacer.setPrefHeight(
                height
        );

        return spacer;
    }

    // =========================================================
    // FIXED SIZE
    // =========================================================

    private void setFixedSize(
            Region node,
            double width,
            double height) {

        node.setPrefWidth(width);
        node.setMinWidth(width);
        node.setMaxWidth(width);

        node.setPrefHeight(height);
        node.setMinHeight(height);
        node.setMaxHeight(height);
    }

    // =========================================================
    // FIXED HEIGHT
    // =========================================================

    private void setFixedHeight(
            Region node,
            double height) {

        node.setPrefHeight(height);
        node.setMinHeight(height);
        node.setMaxHeight(height);
    }

    // =========================================================
    // ORANGE BACKGROUND
    // =========================================================

    private Path createBlueBackground() {

        Path blueShape =
                new Path();

        blueShape.setFill(
                Color.web(TEAL)
        );

        blueShape.setStroke(
                Color.TRANSPARENT
        );

        return blueShape;
    }

    // =========================================================
    // UPDATE ORANGE BACKGROUND
    // =========================================================

    private void updateBlueShape(
            Path shape,
            double width,
            double height) {

        shape.getElements().clear();

        double blueWidth =
                width * 0.53;

        double curveAmount =
                130;

        shape.getElements().add(
                new MoveTo(
                        0,
                        0
                )
        );

        shape.getElements().add(
                new LineTo(
                        blueWidth,
                        0
                )
        );

        shape.getElements().add(
                new CubicCurveTo(
                        blueWidth + curveAmount,
                        height * 0.14,

                        blueWidth + curveAmount,
                        height * 0.32,

                        blueWidth,
                        height * 0.50
                )
        );

        shape.getElements().add(
                new CubicCurveTo(
                        blueWidth - curveAmount,
                        height * 0.68,

                        blueWidth - curveAmount,
                        height * 0.86,

                        blueWidth,
                        height
                )
        );

        shape.getElements().add(
                new LineTo(
                        0,
                        height
                )
        );

        shape.getElements().add(
                new ClosePath()
        );
    }

    // =========================================================
    // FAMILY IMAGE
    // =========================================================

    private ImageView createDoctorImage() {

        ImageView imageView =
                new ImageView();

        try {

            Image image =
                    new Image(
                            DOCTOR_IMAGE_URL,
                            false
                    );

            imageView.setImage(
                    image
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not load family image: "
                            + e.getMessage()
            );
        }

        imageView.setPreserveRatio(
                false
        );

        imageView.setSmooth(
                true
        );

        imageView.setCache(
                true
        );

        return imageView;
    }

    // =========================================================
    // IMAGE SIZE
    // =========================================================

    private void updateImage(
            ImageView imageView,
            double width,
            double height) {

        double imageWidth =
                width * 0.56;

        imageView.setFitWidth(
                imageWidth
        );

        imageView.setFitHeight(
                height
        );

        imageView.setPreserveRatio(
                false
        );

        imageView.setSmooth(
                true
        );
    }

    // =========================================================
    // LIFELINK LOGO
    // =========================================================

    private HBox createLifeLinkLogo() {

        HBox logo =
                new HBox(9);

        logo.setAlignment(
                Pos.CENTER_RIGHT
        );

        // =====================================================
        // LOGO IMAGE
        // =====================================================

        ImageView icon =
                new ImageView();

        try {

            Image logoImage =
                    new Image(
                            LIFELINK_LOGO_IMAGE,
                            false
                    );

            icon.setImage(
                    logoImage
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not load LifeLink logo: "
                            + e.getMessage()
            );
        }

        icon.setFitWidth(
                55
        );

        icon.setFitHeight(
                55
        );

        icon.setPreserveRatio(
                true
        );

        icon.setSmooth(
                true
        );

        // =====================================================
        // LIFE
        // =====================================================

        Text life =
                new Text("Life");

        life.setStyle(
                "-fx-fill: white;" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 33px;" +
                "-fx-font-weight: bold;"
        );

        // =====================================================
        // LINK
        // =====================================================

        Text link =
                new Text("Link");

        link.setStyle(
                "-fx-fill: " + TEAL_LIGHT + ";" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 33px;" +
                "-fx-font-weight: bold;"
        );

        // =====================================================
        // TEXT
        // =====================================================

        HBox text =
                new HBox(0);

        text.getChildren().addAll(
                life,
                link
        );

        // =====================================================
        // LOGO
        // =====================================================

        logo.getChildren().addAll(
                icon,
                text
        );

        return logo;
    }
}