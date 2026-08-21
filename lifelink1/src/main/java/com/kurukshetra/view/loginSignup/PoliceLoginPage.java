package com.kurukshetra.view.loginSignup;

import com.kurukshetra.controller.UserAuthController;
import com.kurukshetra.controller.UserController;
import com.kurukshetra.view.Welcome;
import com.kurukshetra.view.police.PoliceDashboard;

import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

public class PoliceLoginPage {

    // =========================================================
    // COLORS
    // =========================================================

    private static final String TEAL = "#e67593";
    private static final String TEAL_DARK = "#FF1493";
    private static final String TEAL_LIGHT = "#63D7DC";
    private static final String WHITE = "#FFFFFF";
    private static final String BLACK = "#111111";
    private static final String GRAY_BG = "#777775";
    private static final String BORDER = "#D7DBDF";
    private static final String PLACEHOLDER = "#A7A9AC";

    // =========================================================
    // IMAGES
    // =========================================================

    private static final String POLICE_IMAGE_URL =
            "assets\\Images\\policeLogin.jpg";

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
    // AUTH CONTROLLER
    // =========================================================

    UserAuthController userAuthController =
            new UserAuthController();

    // =========================================================
    // MAIN PAGE
    // =========================================================

    public BorderPane getPoliceLoginPage() {

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

        // =====================================================
        // MAIN PAGE
        // =====================================================

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
        // POLICE IMAGE
        // =====================================================

        ImageView policeImage =
                createPoliceImage();

        updateImage(
                policeImage,
                PAGE_WIDTH,
                PAGE_HEIGHT
        );

        AnchorPane.setLeftAnchor(
                policeImage,
                PAGE_WIDTH * 0.44
        );

        AnchorPane.setTopAnchor(
                policeImage,
                0.0
        );

        page.getChildren().add(
                policeImage
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
        // TEAL S-SHAPED BACKGROUND
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
        // BACK BUTTON
        // OUTSIDE LOGIN CARD
        // =====================================================

        Button backButton =
                new Button("←  Back");

        setFixedSize(
                backButton,
                100,
                42
        );

        backButton.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: white;" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 10;" +
                "-fx-cursor: hand;"
        );

        backButton.setOnMouseEntered(
                e -> backButton.setStyle(
                        "-fx-background-color: white;" +
                        "-fx-text-fill: " + TEAL_DARK + ";" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 10;" +
                        "-fx-cursor: hand;"
                )
        );

        backButton.setOnMouseExited(
                e -> backButton.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 10;" +
                        "-fx-cursor: hand;"
                )
        );

        backButton.setOnAction(
                e -> {

                    Welcome welcome =
                            new Welcome();

                    try {

                        welcome.start(
                                Welcome.WelcomeStage
                        );

                    } catch (Exception ex) {

                        ex.printStackTrace();
                    }
                }
        );

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
        // LOGIN CARD
        // =====================================================

        VBox loginCard =
                createLoginCard(page);

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
        // ROOT
        // =====================================================

        root.setCenter(
                page
        );

        return root;
    }

    // =========================================================
    // LOGIN CARD
    // =========================================================

    private VBox createLoginCard(
            AnchorPane parentPane) {

        VBox card =
                new VBox();

        card.setPrefWidth(
                400
        );

        card.setMinWidth(
                400
        );

        card.setMaxWidth(
                400
        );

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

        showLoginForm(
                card,
                parentPane
        );

        return card;
    }

    // =========================================================
    // LOGIN FORM
    // =========================================================

    private void showLoginForm(
            VBox card,
            AnchorPane parentPane) {

        card.getChildren().clear();

        setFixedHeight(
                card,
                560
        );

        // =====================================================
        // SIGN IN TITLE
        // BACK BUTTON IS OUTSIDE CARD
        // =====================================================

        Text signIn =
                new Text("SIGN IN");

        signIn.setStyle(
                "-fx-fill: " + BLACK + ";" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 30px;" +
                "-fx-font-weight: bold;"
        );

        // =====================================================
        // TITLE BOX
        // =====================================================

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
                createSpacer(
                        25
                );

        // =====================================================
        // POLICE DATA
        // =====================================================

        Text policeData =
                new Text("police");

        policeData.setStyle(
                "-fx-fill: " + BLACK + ";" +
                "-fx-font-family: 'Segoe UI';" +
                "-fx-font-size: 24px;" +
                "-fx-font-style: italic;" +
                "-fx-font-weight: bold;"
        );

        HBox policeBox =
                new HBox();

        policeBox.setAlignment(
                Pos.CENTER
        );

        setFixedHeight(
                policeBox,
                50
        );

        policeBox.setPadding(
                new Insets(
                        0,
                        19,
                        0,
                        19
                )
        );

        policeBox.setStyle(
                "-fx-background-color: " + WHITE + ";" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;"
        );

        policeBox.getChildren().add(
                policeData
        );

        // =====================================================
        // USERNAME
        // =====================================================

        Region space1 =
                createSpacer(
                        25
                );

        TextField username =
                createTextField(
                        "Username"
                );

        // =====================================================
        // PASSWORD
        // =====================================================

        Region space2 =
                createSpacer(
                        25
                );

        PasswordField password =
                createPasswordField(
                        "Password"
                );

        // =====================================================
        // PASSWORD VISIBILITY
        // =====================================================

        StackPane passwordContainer =
                createPasswordVisibilityBox(
                        password
                );

        // =====================================================
        // ACTION SPACE
        // =====================================================

        Region actionSpace =
                createSpacer(
                        40
                );

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
                new Button(
                        "Login"
                );

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
                createSpacer(
                        35
                );

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

        loginButton.setOnAction(
                e -> {

                    String usernameValue =
                            username.getText().trim();

                    String passwordValue =
                            password.getText();

                    if (usernameValue.isEmpty()
                            || passwordValue.isEmpty()) {

                        System.out.println(
                                "Please enter username and password."
                        );

                        return;
                    }

                    boolean isSuccess =
                            userAuthController.signIn(
                                    usernameValue,
                                    passwordValue
                            );

                    if (isSuccess) {

                        System.out.println(
                                "Police login successful."
                        );

                        System.out.println(
                                "Username: "
                                        + usernameValue
                        );

                        // =================================================
                        // SUCCESS STACKPANE
                        // =================================================

                        showSuccessPopup(
                                parentPane,
                                () -> {

                                    PoliceDashboard
                                            policeDashboard =
                                            new PoliceDashboard();

                                    try {

                                        policeDashboard.start(
                                                Welcome.WelcomeStage
                                        );

                                    } catch (Exception ex) {

                                        ex.printStackTrace();
                                    }
                                }
                        );
                    }
                }
        );

        // =====================================================
        // FORGOT PASSWORD
        // =====================================================

        forgot.setOnAction(
                e -> System.out.println(
                        "Forgot Password clicked."
                )
        );

        // =====================================================
        // SIGN UP
        // =====================================================

        signUp.setOnAction(
                e -> showSignUpForm(
                        card,
                        parentPane
                )
        );

        // =====================================================
        // ADD CONTENT
        // =====================================================

        card.getChildren().addAll(
                titleBox,
                titleSpace,
                policeBox,
                space1,
                username,
                space2,
                passwordContainer,
                actionSpace,
                actionRow,
                signUpSpace,
                signUpRow
        );
    }

    // =========================================================
    // PASSWORD VISIBILITY BOX
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
                        eyeButtonStyle(TEAL)
                )
        );

        eyeButton.setOnMouseExited(
                e -> {

                    String color =
                            password.isVisible()
                                    ? TEAL
                                    : PLACEHOLDER;

                    eyeButton.setStyle(
                            eyeButtonStyle(color)
                    );
                }
        );

        // =====================================================
        // SHOW / HIDE
        // =====================================================

        eyeButton.setOnAction(e -> {

            if (password.isVisible()) {

                visiblePassword.setText(
                        password.getText()
                );

                password.setVisible(false);
                password.setManaged(false);

                visiblePassword.setVisible(true);
                visiblePassword.setManaged(true);

                eyeButton.setText("🙈");

                eyeButton.setStyle(
                        eyeButtonStyle(TEAL)
                );

                visiblePassword.requestFocus();

            } else {

                password.setText(
                        visiblePassword.getText()
                );

                visiblePassword.setVisible(false);
                visiblePassword.setManaged(false);

                password.setVisible(true);
                password.setManaged(true);

                eyeButton.setText("👁");

                eyeButton.setStyle(
                        eyeButtonStyle(PLACEHOLDER)
                );

                password.requestFocus();
            }
        });

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

    private void showSignUpForm(
            VBox card,
            AnchorPane parentPane) {

        card.getChildren().clear();

        setFixedHeight(
                card,
                550
        );

        // =====================================================
        // TITLE
        // =====================================================

        Text signUpTitle =
                new Text(
                        "SIGN UP"
                );

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
                createSpacer(
                        25
                );

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
                createSpacer(
                        18
                );

        TextField email =
                createTextField(
                        "Email"
                );

        // =====================================================
        // PASSWORD
        // =====================================================

        Region space2 =
                createSpacer(
                        18
                );

        PasswordField password =
                createPasswordField(
                        "Password"
                );

        // =====================================================
        // SIGN UP PASSWORD VISIBILITY
        // =====================================================

        StackPane passwordContainer =
                createPasswordVisibilityBox(
                        password
                );

        // =====================================================
        // ACTION SPACE
        // =====================================================

        Region actionSpace =
                createSpacer(
                        30
                );

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
                createSpacer(
                        25
                );

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

        signUpButton.setOnAction(
                e -> {

                    String nameValue =
                            name.getText().trim();

                    String emailValue =
                            email.getText().trim();

                    String passwordValue =
                            password.getText();

                    if (nameValue.isEmpty()
                            || emailValue.isEmpty()
                            || passwordValue.isEmpty()) {

                        System.out.println(
                                "Please enter name, email and password."
                        );

                        return;
                    }

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

                        UserController userController =
                                new UserController();

                        userController.passToPoliceModel(
                                nameValue,
                                emailValue
                        );

                        System.out.println(
                                "========== SIGN UP =========="
                        );

                        System.out.println(
                                "Name: "
                                        + nameValue
                        );

                        System.out.println(
                                "Email: "
                                        + emailValue
                        );

                        System.out.println(
                                "Password: "
                                        + passwordValue
                        );

                        System.out.println(
                                "Sign Up successful."
                        );

                        System.out.println(
                                "============================="
                        );
                    }
                }
        );

        // =====================================================
        // BACK TO LOGIN ACTION
        // =====================================================

        backToLogin.setOnAction(
                e -> showLoginForm(
                        card,
                        parentPane
                )
        );

        // =====================================================
        // ADD SIGNUP CONTENT
        // =====================================================

        card.getChildren().addAll(
                titleBox,
                titleSpace,
                name,
                space1,
                email,
                space2,
                passwordContainer,
                actionSpace,
                buttonBox,
                loginSpace,
                loginBox
        );
    }

    // =========================================================
    // SUCCESS POPUP STACKPANE
    // =========================================================

    private void showSuccessPopup(
            AnchorPane parentPane,
            Runnable onComplete) {

        StackPane overlay =
                new StackPane();

        overlay.setStyle(
                "-fx-background-color: rgba(0,0,0,0.40);"
        );

        // =====================================================
        // SUCCESS MESSAGE BOX
        // =====================================================

        VBox messageBox =
                new VBox(
                        15
                );

        messageBox.setAlignment(
                Pos.CENTER
        );

        messageBox.setMaxSize(
                360,
                160
        );

        messageBox.setPrefSize(
                360,
                160
        );

        messageBox.setPadding(
                new Insets(
                        20
                )
        );

        messageBox.setStyle(
                "-fx-background-color: " + WHITE + ";" +
                "-fx-background-radius: 18;" +
                "-fx-border-radius: 18;" +
                "-fx-effect: dropshadow(" +
                "gaussian," +
                "rgba(0,0,0,0.25)," +
                "18," +
                "0," +
                "0," +
                "4" +
                ");"
        );

        // =====================================================
        // SUCCESS TEXT
        // =====================================================

        Label successLabel =
                new Label(
                        "✓ Login Successful!"
                );

        successLabel.setStyle(
                "-fx-text-fill: #2ecc71;" +
                "-fx-font-size: 22px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: 'Segoe UI';"
        );

        // =====================================================
        // REDIRECT TEXT
        // =====================================================

        Label redirectLabel =
                new Label(
                        "Redirecting to Dashboard..."
                );

        redirectLabel.setStyle(
                "-fx-text-fill: " + BLACK + ";" +
                "-fx-font-size: 14px;" +
                "-fx-font-family: 'Segoe UI';"
        );

        messageBox.getChildren().addAll(
                successLabel,
                redirectLabel
        );

        // =====================================================
        // ADD MESSAGE BOX
        // =====================================================

        overlay.getChildren().add(
                messageBox
        );

        StackPane.setAlignment(
                messageBox,
                Pos.CENTER
        );

        // =====================================================
        // ANCHOR OVERLAY
        // =====================================================

        AnchorPane.setTopAnchor(
                overlay,
                0.0
        );

        AnchorPane.setBottomAnchor(
                overlay,
                0.0
        );

        AnchorPane.setLeftAnchor(
                overlay,
                0.0
        );

        AnchorPane.setRightAnchor(
                overlay,
                0.0
        );

        // =====================================================
        // ADD OVERLAY
        // =====================================================

        parentPane.getChildren().add(
                overlay
        );

        // =====================================================
        // REDIRECT DELAY
        // =====================================================

        PauseTransition delay =
                new PauseTransition(
                        Duration.seconds(
                                1.2
                        )
                );

        delay.setOnFinished(
                event -> {

                    parentPane.getChildren().remove(
                            overlay
                    );

                    if (onComplete != null) {

                        onComplete.run();
                    }
                }
        );

        delay.play();
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
                "rgba(8,127,140,0.18)," +
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
                loginButtonStyle(
                        color
                )
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
    // FORGOT PASSWORD STYLE
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

        node.setPrefWidth(
                width
        );

        node.setMinWidth(
                width
        );

        node.setMaxWidth(
                width
        );

        node.setPrefHeight(
                height
        );

        node.setMinHeight(
                height
        );

        node.setMaxHeight(
                height
        );
    }

    // =========================================================
    // FIXED HEIGHT
    // =========================================================

    private void setFixedHeight(
            Region node,
            double height) {

        node.setPrefHeight(
                height
        );

        node.setMinHeight(
                height
        );

        node.setMaxHeight(
                height
        );
    }

    // =========================================================
    // TEAL BACKGROUND
    // =========================================================

    private Path createBlueBackground() {

        Path blueShape =
                new Path();

        blueShape.setFill(
                Color.web(
                        TEAL
                )
        );

        blueShape.setStroke(
                Color.TRANSPARENT
        );

        return blueShape;
    }

    // =========================================================
    // UPDATE TEAL SHAPE
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
    // POLICE IMAGE
    // =========================================================

    private ImageView createPoliceImage() {

        ImageView imageView =
                new ImageView();

        try {

            Image image =
                    new Image(
                            POLICE_IMAGE_URL,
                            false
                    );

            imageView.setImage(
                    image
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not load police image: "
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
                new HBox(
                        9
                );

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
                new Text(
                        "Life"
                );

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
                new Text(
                        "Link"
                );

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
                new HBox(
                        0
                );

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