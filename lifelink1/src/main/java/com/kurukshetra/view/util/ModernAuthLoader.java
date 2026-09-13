package com.kurukshetra.view.util;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModernAuthLoader {

    private static final String TEAL_PRIMARY = "#006591";
    private static final String SUCCESS_GREEN = "#10B981";
    private static final String DANGER_RED = "#EF4444";

    public static class LoaderSession {
        private final Button loginButton;
        private final Pane formCard;
        private final String originalButtonText;
        private final String originalButtonStyle;
        private final Node[] inputControls;
        private StackPane overlayPane;
        private ProgressIndicator buttonSpinner;

        public LoaderSession(Button loginButton, Pane formCard, Node... inputControls) {
            this.loginButton = loginButton;
            this.formCard = formCard;
            this.originalButtonText = loginButton.getText();
            this.originalButtonStyle = loginButton.getStyle();
            this.inputControls = inputControls;
        }

        public void start(String loadingMessage) {
            // Disable inputs to prevent double clicks
            if (inputControls != null) {
                for (Node node : inputControls) {
                    if (node != null) node.setDisable(true);
                }
            }
            loginButton.setDisable(true);

            // Update button with spinner
            buttonSpinner = new ProgressIndicator();
            buttonSpinner.setMaxSize(20, 20);
            buttonSpinner.setStyle("-fx-progress-color: #FFFFFF;");

            HBox buttonContent = new HBox(8, buttonSpinner, new Text(loadingMessage));
            ((Text) buttonContent.getChildren().get(1)).setStyle("-fx-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 13px;");
            buttonContent.setAlignment(Pos.CENTER);
            loginButton.setGraphic(buttonContent);
            loginButton.setText("");

            // Create glassmorphic overlay over formCard if it's a StackPane or contains one
            showFormOverlay(loadingMessage);
        }

        private void showFormOverlay(String loadingMessage) {
            if (formCard == null) return;

            overlayPane = new StackPane();
            overlayPane.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.88);" +
                    "-fx-background-radius: 28px;" +
                    "-fx-border-radius: 28px;"
            );
            overlayPane.setOpacity(0);

            VBox overlayContent = new VBox(14);
            overlayContent.setAlignment(Pos.CENTER);
            overlayContent.setPadding(new Insets(24));

            // Animated pulsing circle with medical icon
            StackPane pulseContainer = new StackPane();
            Circle outerGlow = new Circle(32);
            outerGlow.setFill(Color.web(TEAL_PRIMARY, 0.15));

            Circle innerCore = new Circle(22);
            innerCore.setFill(Color.web(TEAL_PRIMARY));

            Text lockIcon = new Text("🔒");
            lockIcon.setStyle("-fx-font-size: 16px;");

            pulseContainer.getChildren().addAll(outerGlow, innerCore, lockIcon);

            // Scale pulse animation
            ScaleTransition st = new ScaleTransition(Duration.millis(800), outerGlow);
            st.setFromX(1.0);
            st.setFromY(1.0);
            st.setToX(1.35);
            st.setToY(1.35);
            st.setCycleCount(Animation.INDEFINITE);
            st.setAutoReverse(true);
            st.play();

            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setMaxSize(36, 36);
            progressIndicator.setStyle("-fx-progress-color: " + TEAL_PRIMARY + ";");

            Label statusLabel = new Label("Securing connection...");
            statusLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
            statusLabel.setTextFill(Color.web("#0F172A"));

            Label subText = new Label("Verifying with LifeLink Cloud");
            subText.setFont(Font.font("Segoe UI", 12));
            subText.setTextFill(Color.web("#64748B"));

            overlayContent.getChildren().addAll(pulseContainer, progressIndicator, statusLabel, subText);
            overlayPane.getChildren().add(overlayContent);

            // Add overlay to formCard
            if (formCard instanceof StackPane) {
                ((StackPane) formCard).getChildren().add(overlayPane);
            } else if (formCard.getParent() instanceof StackPane) {
                ((StackPane) formCard.getParent()).getChildren().add(overlayPane);
            } else {
                formCard.getChildren().add(overlayPane);
            }

            FadeTransition ft = new FadeTransition(Duration.millis(200), overlayPane);
            ft.setToValue(1.0);
            ft.play();
        }

        public void finishSuccess(String successMessage, Runnable onComplete) {
            loginButton.setGraphic(null);
            loginButton.setText("✓ " + successMessage);
            loginButton.setStyle(originalButtonStyle + "; -fx-background-color: " + SUCCESS_GREEN + ";");

            if (overlayPane != null) {
                VBox box = (VBox) overlayPane.getChildren().get(0);
                box.getChildren().clear();

                Text check = new Text("✓");
                check.setStyle("-fx-fill: " + SUCCESS_GREEN + "; -fx-font-size: 48px; -fx-font-weight: bold;");

                Label doneLabel = new Label("Access Granted");
                doneLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
                doneLabel.setTextFill(Color.web("#0F172A"));

                Label redirectLabel = new Label("Redirecting to dashboard...");
                redirectLabel.setFont(Font.font("Segoe UI", 13));
                redirectLabel.setTextFill(Color.web("#64748B"));

                box.getChildren().addAll(check, doneLabel, redirectLabel);

                ScaleTransition scale = new ScaleTransition(Duration.millis(300), check);
                scale.setFromX(0.5);
                scale.setFromY(0.5);
                scale.setToX(1.1);
                scale.setToY(1.1);
                scale.play();
            }

            PauseTransition delay = new PauseTransition(Duration.millis(600));
            delay.setOnFinished(e -> {
                if (onComplete != null) onComplete.run();
            });
            delay.play();
        }

        public void finishFailure(String errorMessage) {
            // Re-enable button and inputs
            loginButton.setDisable(false);
            loginButton.setGraphic(null);
            loginButton.setText(originalButtonText);
            loginButton.setStyle(originalButtonStyle);

            if (inputControls != null) {
                for (Node node : inputControls) {
                    if (node != null) node.setDisable(false);
                }
            }

            // Remove overlay
            if (overlayPane != null) {
                FadeTransition fadeOut = new FadeTransition(Duration.millis(180), overlayPane);
                fadeOut.setToValue(0);
                fadeOut.setOnFinished(e -> {
                    if (overlayPane.getParent() instanceof Pane) {
                        ((Pane) overlayPane.getParent()).getChildren().remove(overlayPane);
                    }
                });
                fadeOut.play();
            }

            // Display floating error toast with shake physics
            showShakeError(errorMessage);
        }

        private void showShakeError(String errorMessage) {
            HBox errorBox = new HBox(10);
            errorBox.setAlignment(Pos.CENTER_LEFT);
            errorBox.setPadding(new Insets(10, 14, 10, 14));
            errorBox.setStyle(
                    "-fx-background-color: #FEF2F2;" +
                    "-fx-border-color: #FCA5A5;" +
                    "-fx-border-radius: 10px;" +
                    "-fx-background-radius: 10px;"
            );

            Text alertIcon = new Text("⚠️");
            alertIcon.setStyle("-fx-font-size: 14px;");

            Label errorLabel = new Label(errorMessage);
            errorLabel.setStyle("-fx-text-fill: #B91C1C; -fx-font-weight: bold; -fx-font-size: 12px;");
            errorLabel.setWrapText(true);

            errorBox.getChildren().addAll(alertIcon, errorLabel);

            // Insert above or at top of form
            int insertIndex = Math.min(2, formCard.getChildren().size());
            formCard.getChildren().add(insertIndex, errorBox);

            // Shake animation
            TranslateTransition shake = new TranslateTransition(Duration.millis(60), errorBox);
            shake.setFromX(0);
            shake.setByX(8);
            shake.setCycleCount(6);
            shake.setAutoReverse(true);
            shake.setOnFinished(e -> errorBox.setTranslateX(0));
            shake.play();

            // Auto-dismiss after 4 seconds
            PauseTransition dismiss = new PauseTransition(Duration.millis(4000));
            dismiss.setOnFinished(e -> {
                FadeTransition ft = new FadeTransition(Duration.millis(250), errorBox);
                ft.setToValue(0);
                ft.setOnFinished(ev -> formCard.getChildren().remove(errorBox));
                ft.play();
            });
            dismiss.play();
        }
    }

    /**
     * Executes an authentication action asynchronously with modern loading state,
     * maintaining 60fps animations and avoiding JavaFX thread freeze.
     */
    public static void runAsyncAuth(Button loginButton, Pane formCard, String loadingText,
                                    Supplier<Boolean> authSupplier,
                                    Consumer<Boolean> onResultHandler,
                                    Node... inputs) {
        LoaderSession session = new LoaderSession(loginButton, formCard, inputs);
        session.start(loadingText);

        new Thread(() -> {
            boolean success = false;
            try {
                // Short minimum display duration to ensure smooth visual feedback
                long start = System.currentTimeMillis();
                success = authSupplier.get();
                long elapsed = System.currentTimeMillis() - start;
                if (elapsed < 600) {
                    Thread.sleep(600 - elapsed);
                }
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }

            final boolean finalSuccess = success;
            Platform.runLater(() -> {
                if (finalSuccess) {
                    session.finishSuccess("Verified!", () -> onResultHandler.accept(true));
                } else {
                    session.finishFailure("Invalid email or password. Please try again.");
                    onResultHandler.accept(false);
                }
            });
        }).start();
    }
}
