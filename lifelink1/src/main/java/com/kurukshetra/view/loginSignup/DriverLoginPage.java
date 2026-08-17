package com.kurukshetra.view.loginSignup;

import com.kurukshetra.controller.UserAuthController;
import com.kurukshetra.controller.UserController;
import com.kurukshetra.view.driver.DriverDashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class DriverLoginPage {

        // =========================================================
        // COLORS
        // =========================================================

        private static final String TEAL = "#DE4343";
        private static final String TEAL_DARK = "#D2042D";
        private static final String TEAL_LIGHT = "#63D7DC";
        private static final String WHITE = "#FFFFFF";
        private static final String BLACK = "#111111";
        private static final String GRAY_BG = "#777775";
        private static final String BORDER = "#D7DBDF";
        private static final String PLACEHOLDER = "#A7A9AC";

        // =========================================================
        // IMAGES
        // =========================================================

        private static final String DOCTOR_IMAGE_URL = "assets\\Images\\hospitalLogin.jpg";

        private static final String LIFELINK_LOGO_IMAGE = "assets\\Images\\LifeLinkLogo.png";

        // =========================================================
        // PAGE SIZE
        // =========================================================

        private static final double PAGE_WIDTH = 1536;
        private static final double PAGE_HEIGHT = 809;

        // =========================================================
        // MAIN PAGE
        // =========================================================
        UserAuthController userAuthController = new UserAuthController();

        public BorderPane getDriverLoginPage() {

                BorderPane root = new BorderPane();

                root.setPrefSize(PAGE_WIDTH, PAGE_HEIGHT);
                root.setMinSize(PAGE_WIDTH, PAGE_HEIGHT);
                root.setMaxSize(PAGE_WIDTH, PAGE_HEIGHT);

                root.setStyle(
                                "-fx-background-color: " + GRAY_BG + ";" +
                                                "-fx-font-family: 'Segoe UI';");

                AnchorPane page = new AnchorPane();

                page.setPrefSize(PAGE_WIDTH, PAGE_HEIGHT);
                page.setMinSize(PAGE_WIDTH, PAGE_HEIGHT);
                page.setMaxSize(PAGE_WIDTH, PAGE_HEIGHT);

                page.setStyle(
                                "-fx-background-color: " + GRAY_BG + ";");

                // =====================================================
                // DOCTOR IMAGE
                // =====================================================

                ImageView doctorImage = createDoctorImage();

                updateImage(
                                doctorImage,
                                PAGE_WIDTH,
                                PAGE_HEIGHT);

                AnchorPane.setLeftAnchor(
                                doctorImage,
                                PAGE_WIDTH * 0.44);

                AnchorPane.setTopAnchor(
                                doctorImage,
                                0.0);

                page.getChildren().add(doctorImage);

                // =====================================================
                // IMAGE OVERLAY
                // =====================================================

                Rectangle imageOverlay = new Rectangle();

                imageOverlay.setWidth(PAGE_WIDTH * 0.56);
                imageOverlay.setHeight(PAGE_HEIGHT);

                imageOverlay.setFill(
                                Color.rgb(0, 0, 0, 0.50));

                AnchorPane.setLeftAnchor(
                                imageOverlay,
                                PAGE_WIDTH * 0.44);

                AnchorPane.setTopAnchor(
                                imageOverlay,
                                0.0);

                page.getChildren().add(imageOverlay);

                // =====================================================
                // BLUE S-SHAPED BACKGROUND
                // =====================================================

                Path blueShape = createBlueBackground();

                updateBlueShape(
                                blueShape,
                                PAGE_WIDTH,
                                PAGE_HEIGHT);

                AnchorPane.setLeftAnchor(
                                blueShape,
                                0.0);

                AnchorPane.setTopAnchor(
                                blueShape,
                                0.0);

                page.getChildren().add(blueShape);

                // =====================================================
                // LOGIN CARD
                // =====================================================

                VBox loginCard = createLoginCard();

                AnchorPane.setLeftAnchor(
                                loginCard,
                                (double) 150);

                AnchorPane.setTopAnchor(
                                loginCard,
                                (double) 120);

                page.getChildren().add(loginCard);

                // =====================================================
                // LIFELINK LOGO
                // =====================================================

                HBox lifeLinkLogo = createLifeLinkLogo();

                AnchorPane.setRightAnchor(
                                lifeLinkLogo,
                                30.0);

                AnchorPane.setTopAnchor(
                                lifeLinkLogo,
                                28.0);

                page.getChildren().add(lifeLinkLogo);

                // =====================================================
                // ROOT
                // =====================================================

                root.setCenter(page);

                return root;
        }

        // =========================================================
        // LOGIN CARD
        // =========================================================

        private VBox createLoginCard() {

                VBox card = new VBox();

                card.setPrefWidth(400);
                card.setMinWidth(400);
                card.setMaxWidth(400);

                setFixedHeight(card, 560);

                card.setPadding(
                                new Insets(
                                                45,
                                                42,
                                                40,
                                                42));

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
                                                ");");

                // Only one Login UI implementation.
                showLoginForm(card);

                return card;
        }

        // =========================================================
        // LOGIN FORM
        // =========================================================

        private void showLoginForm(VBox card) {

                card.getChildren().clear();

                setFixedHeight(card, 560);

                // =====================================================
                // SIGN IN TITLE
                // =====================================================

                Text signIn = new Text("SIGN IN");

                signIn.setStyle(
                                "-fx-fill: " + BLACK + ";" +
                                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 30px;" +
                                                "-fx-font-weight: bold;");

                HBox titleBox = new HBox();

                titleBox.setAlignment(Pos.CENTER);
                titleBox.setPrefHeight(48);

                titleBox.getChildren().add(signIn);

                // =====================================================
                // TITLE SPACE
                // =====================================================

                Region titleSpace = createSpacer(25);

                // =====================================================
                // DRIVER DATA
                // =====================================================

                Text driverData = new Text("Ambulance Driver");

                driverData.setStyle(
                                "-fx-fill: " + BLACK + ";" +
                                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 24px;" +
                                                "-fx-font-style: italic;" +
                                                "-fx-font-weight: bold;");

                HBox driverBox = new HBox();

                driverBox.setAlignment(Pos.CENTER);
                setFixedHeight(driverBox, 50);

                driverBox.setPadding(
                                new Insets(
                                                0,
                                                19,
                                                0,
                                                19));

                driverBox.setStyle(
                                "-fx-background-color: " + WHITE + ";" +
                                                "-fx-border-width: 2;" +
                                                "-fx-border-radius: 10;" +
                                                "-fx-background-radius: 10;");

                driverBox.getChildren().add(driverData);

                // =====================================================
                // USERNAME
                // =====================================================

                Region space1 = createSpacer(25);

                TextField username = createTextField("Useremail");

                // =====================================================
                // PASSWORD
                // =====================================================

                Region space2 = createSpacer(25);

                PasswordField password = createPasswordField("Password");

                // =====================================================
                // ACTION SPACE
                // =====================================================

                Region actionSpace = createSpacer(40);

                // =====================================================
                // ACTION ROW
                // =====================================================

                HBox actionRow = new HBox();

                actionRow.setAlignment(Pos.CENTER_LEFT);

                // =====================================================
                // LOGIN BUTTON
                // =====================================================

                Button loginButton = new Button("Login");

                setFixedSize(
                                loginButton,
                                113,
                                50);

                setLoginButtonStyle(
                                loginButton,
                                TEAL);

                // =====================================================
                // BUTTON SPACE
                // =====================================================

                Region buttonSpace = new Region();

                HBox.setHgrow(
                                buttonSpace,
                                Priority.ALWAYS);

                // =====================================================
                // FORGOT PASSWORD
                // =====================================================

                Button forgot = new Button("Forgot Password?");

                setForgotButtonStyle(
                                forgot,
                                BLACK);

                actionRow.getChildren().addAll(
                                loginButton,
                                buttonSpace,
                                forgot);

                // =====================================================
                // SIGN UP
                // =====================================================

                Region signUpSpace = createSpacer(35);

                HBox signUpRow = new HBox();

                signUpRow.setAlignment(Pos.CENTER);

                Text accountText = new Text(
                                "Don't have an account? ");

                accountText.setStyle(
                                "-fx-fill: " + BLACK + ";" +
                                                "-fx-font-size: 14px;" +
                                                "-fx-font-family: 'Segoe UI';");

                Button signUp = new Button("Sign Up");

                setSignUpButtonStyle(
                                signUp,
                                TEAL);

                signUpRow.getChildren().addAll(
                                accountText,
                                signUp);

                // =====================================================
                // LOGIN ACTION
                // =====================================================

                loginButton.setOnAction(e -> {

                        String emailValue = username.getText().trim();

                        String passwordValue = password.getText();

                        if (emailValue.isEmpty()
                                        || passwordValue.isEmpty()) {

                                System.out.println(
                                                "Please enter username and password.");

                                return;
                        }

                        
                        boolean isSuccess = userAuthController.signIn(emailValue, passwordValue);

                        if (isSuccess) {
                                System.out.println(
                                                "Driver: Driver Data");

                                System.out.println(
                                                "Username: " + emailValue);

                                System.out.println(
                                                "Login successful.");

                                DriverDashboard driverDashboard = new DriverDashboard();
                                try{
                                
                                        driverDashboard.start(AalLoginStartPoint.startPageStage);

                                }catch(Exception e1){
                                        e1.printStackTrace();
                                }
                        }

                });

                // =====================================================
                // FORGOT PASSWORD ACTION
                // =====================================================

                forgot.setOnAction(e -> System.out.println(
                                "Forgot Password clicked."));

                // =====================================================
                // SIGN UP ACTION
                // =====================================================

                signUp.setOnAction(e -> showSignUpForm(card));

                // =====================================================
                // ADD LOGIN CONTENT
                // =====================================================

                card.getChildren().addAll(
                                titleBox,
                                titleSpace,
                                driverBox,
                                space1,
                                username,
                                space2,
                                password,
                                actionSpace,
                                actionRow,
                                signUpSpace,
                                signUpRow);
        }

        // =========================================================
        // SIGN UP FORM
        // =========================================================

        private void showSignUpForm(VBox card) {

                card.getChildren().clear();

                setFixedHeight(card, 550);

                // =====================================================
                // TITLE
                // =====================================================

                Text signUpTitle = new Text("SIGN UP");

                signUpTitle.setStyle(
                                "-fx-fill: " + BLACK + ";" +
                                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 30px;" +
                                                "-fx-font-weight: bold;");

                HBox titleBox = new HBox();

                titleBox.setAlignment(Pos.CENTER);
                titleBox.setPrefHeight(48);

                titleBox.getChildren().add(signUpTitle);

                // =====================================================
                // TITLE SPACE
                // =====================================================

                Region titleSpace = createSpacer(25);

                // =====================================================
                // NAME
                // =====================================================

                TextField name = createTextField("Name");

                // =====================================================
                // EMAIL
                // =====================================================

                Region space1 = createSpacer(18);

                TextField email = createTextField("Email");

                // =====================================================
                // PASSWORD
                // =====================================================

                Region space2 = createSpacer(18);

                PasswordField password = createPasswordField("Password");

                // =====================================================
                // ACTION SPACE
                // =====================================================

                Region actionSpace = createSpacer(30);

                // =====================================================
                // SIGN UP BUTTON
                // =====================================================

                Button signUpButton = new Button("Sign Up");

                setFixedSize(
                                signUpButton,
                                130,
                                50);

                setLoginButtonStyle(
                                signUpButton,
                                TEAL);

                HBox buttonBox = new HBox();

                buttonBox.setAlignment(Pos.CENTER);
                buttonBox.getChildren().add(signUpButton);

                // =====================================================
                // LOGIN SPACE
                // =====================================================

                Region loginSpace = createSpacer(25);

                // =====================================================
                // BACK TO LOGIN
                // =====================================================

                Button backToLogin = new Button(
                                "Already have an account? Login");

                setForgotButtonStyle(
                                backToLogin,
                                BLACK);

                HBox loginBox = new HBox();

                loginBox.setAlignment(Pos.CENTER);
                loginBox.getChildren().add(backToLogin);

                // =====================================================
                // SIGN UP ACTION
                // =====================================================

                signUpButton.setOnAction(e -> {

                        String nameValue = name.getText().trim();

                        String emailValue = email.getText().trim();

                        String passwordValue = password.getText();

                        if (nameValue.isEmpty()
                                        || emailValue.isEmpty()
                                        || passwordValue.isEmpty()) {

                                System.out.println(
                                                "Please enter name, email and password.");

                                return;
                        }
                         boolean isSuccess = userAuthController.signUp(nameValue, emailValue, passwordValue);

                         if(isSuccess){

                                 System.out.println("API Hit Successfully (SignUp)");
                                UserController userController = new UserController();
                                userController.passToDriverModel(nameValue, emailValue);

                                System.out.println(
                                        "========== SIGN UP ==========");

                        System.out.println(
                                        "Name: " + nameValue);

                        System.out.println(
                                        "Email: " + emailValue);

                        System.out.println(
                                        "Password: " + passwordValue);

                        System.out.println(
                                        "Sign Up successful.");

                        System.out.println(
                                        "=============================");
               

                         }

                         });

                // =====================================================
                // BACK TO LOGIN ACTION
                // =====================================================

                backToLogin.setOnAction(e -> showLoginForm(card));

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
                                password,
                                actionSpace,
                                buttonBox,
                                loginSpace,
                                loginBox);
        }

        // =========================================================
        // TEXT FIELD
        // =========================================================

        private TextField createTextField(String prompt) {

                TextField field = new TextField();

                field.setPromptText(prompt);

                setFixedHeight(field, 50);

                field.setStyle(
                                normalFieldStyle());

                addFocusStyle(field);

                return field;
        }

        // =========================================================
        // PASSWORD FIELD
        // =========================================================

        private PasswordField createPasswordField(
                        String prompt) {

                PasswordField field = new PasswordField();

                field.setPromptText(prompt);

                setFixedHeight(field, 50);

                field.setStyle(
                                normalFieldStyle());

                addFocusStyle(field);

                return field;
        }

        // =========================================================
        // NORMAL FIELD STYLE
        // =========================================================

        private String normalFieldStyle() {

                return "-fx-background-color: " + WHITE + ";" +
                                "-fx-border-color: " + BORDER + ";" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-radius: 10;" +
                                "-fx-font-family: 'Segoe UI';" +
                                "-fx-font-size: 17px;" +
                                "-fx-text-fill: " + BLACK + ";" +
                                "-fx-prompt-text-fill: " + PLACEHOLDER + ";" +
                                "-fx-padding: 0 19;";
        }

        // =========================================================
        // FIELD FOCUS STYLE
        // =========================================================

        private void addFocusStyle(TextField field) {

                field.focusedProperty().addListener(
                                (obs, oldValue, focused) -> {

                                        if (focused) {

                                                field.setStyle(
                                                                focusedFieldStyle());

                                        } else {

                                                field.setStyle(
                                                                normalFieldStyle());
                                        }
                                });
        }

        private String focusedFieldStyle() {

                return "-fx-background-color: " + WHITE + ";" +
                                "-fx-border-color: " + TEAL + ";" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-radius: 10;" +
                                "-fx-font-family: 'Segoe UI';" +
                                "-fx-font-size: 17px;" +
                                "-fx-text-fill: " + BLACK + ";" +
                                "-fx-prompt-text-fill: " + PLACEHOLDER + ";" +
                                "-fx-padding: 0 19;" +
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
                                loginButtonStyle(color));

                button.setOnMouseEntered(
                                e -> button.setStyle(
                                                loginButtonStyle(TEAL_DARK)));

                button.setOnMouseExited(
                                e -> button.setStyle(
                                                loginButtonStyle(color)));
        }

        private String loginButtonStyle(String color) {

                return "-fx-background-color: " + color + ";" +
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
                                                14));

                button.setOnMouseEntered(
                                e -> button.setStyle(
                                                linkButtonStyle(
                                                                TEAL_DARK,
                                                                14)));

                button.setOnMouseExited(
                                e -> button.setStyle(
                                                linkButtonStyle(
                                                                color,
                                                                14)));
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
                                                15));

                button.setOnMouseEntered(
                                e -> button.setStyle(
                                                linkButtonStyle(
                                                                TEAL_DARK,
                                                                15)));

                button.setOnMouseExited(
                                e -> button.setStyle(
                                                linkButtonStyle(
                                                                color,
                                                                15)));
        }

        private String linkButtonStyle(
                        String color,
                        int fontSize) {

                return "-fx-background-color: transparent;" +
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

        private Region createSpacer(double height) {

                Region spacer = new Region();

                spacer.setPrefHeight(height);

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
        // BLUE BACKGROUND
        // =========================================================

        private Path createBlueBackground() {

                Path blueShape = new Path();

                blueShape.setFill(
                                Color.web(TEAL));

                blueShape.setStroke(
                                Color.TRANSPARENT);

                return blueShape;
        }

        // =========================================================
        // UPDATE BLUE BACKGROUND
        // =========================================================

        private void updateBlueShape(
                        Path shape,
                        double width,
                        double height) {

                shape.getElements().clear();

                double blueWidth = width * 0.53;
                double curveAmount = 130;

                shape.getElements().add(
                                new MoveTo(
                                                0,
                                                0));

                shape.getElements().add(
                                new LineTo(
                                                blueWidth,
                                                0));

                shape.getElements().add(
                                new CubicCurveTo(
                                                blueWidth + curveAmount,
                                                height * 0.14,

                                                blueWidth + curveAmount,
                                                height * 0.32,

                                                blueWidth,
                                                height * 0.50));

                shape.getElements().add(
                                new CubicCurveTo(
                                                blueWidth - curveAmount,
                                                height * 0.68,

                                                blueWidth - curveAmount,
                                                height * 0.86,

                                                blueWidth,
                                                height));

                shape.getElements().add(
                                new LineTo(
                                                0,
                                                height));

                shape.getElements().add(
                                new ClosePath());
        }

        // =========================================================
        // DOCTOR IMAGE
        // =========================================================

        private ImageView createDoctorImage() {

                ImageView imageView = new ImageView();

                try {

                        Image image = new Image(
                                        DOCTOR_IMAGE_URL,
                                        false);

                        imageView.setImage(image);

                } catch (Exception e) {

                        System.out.println(
                                        "Could not load doctor image: "
                                                        + e.getMessage());
                }

                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);
                imageView.setCache(true);

                return imageView;
        }

        // =========================================================
        // IMAGE SIZE
        // =========================================================

        private void updateImage(
                        ImageView imageView,
                        double width,
                        double height) {

                double imageWidth = width * 0.56;

                imageView.setFitWidth(
                                imageWidth);

                imageView.setFitHeight(
                                height);

                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);
        }

        // =========================================================
        // LIFELINK LOGO
        // =========================================================

        private HBox createLifeLinkLogo() {

                HBox logo = new HBox(9);

                logo.setAlignment(
                                Pos.CENTER_RIGHT);

                // =====================================================
                // LOGO IMAGE
                // =====================================================

                ImageView icon = new ImageView();

                try {

                        Image logoImage = new Image(
                                        LIFELINK_LOGO_IMAGE,
                                        false);

                        icon.setImage(logoImage);

                } catch (Exception e) {

                        System.out.println(
                                        "Could not load LifeLink logo: "
                                                        + e.getMessage());
                }

                icon.setFitWidth(55);
                icon.setFitHeight(55);

                icon.setPreserveRatio(true);
                icon.setSmooth(true);

                // =====================================================
                // LIFE
                // =====================================================

                Text life = new Text("Life");

                life.setStyle(
                                "-fx-fill: white;" +
                                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 33px;" +
                                                "-fx-font-weight: bold;");

                // =====================================================
                // LINK
                // =====================================================

                Text link = new Text("Link");

                link.setStyle(
                                "-fx-fill: " + TEAL_LIGHT + ";" +
                                                "-fx-font-family: 'Segoe UI';" +
                                                "-fx-font-size: 33px;" +
                                                "-fx-font-weight: bold;");

                // =====================================================
                // TEXT
                // =====================================================

                HBox text = new HBox(0);

                text.getChildren().addAll(
                                life,
                                link);

                // =====================================================
                // LOGO
                // =====================================================

                logo.getChildren().addAll(
                                icon,
                                text);

                return logo;
        }
}
