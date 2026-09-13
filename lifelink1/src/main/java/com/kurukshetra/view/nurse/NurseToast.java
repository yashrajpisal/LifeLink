package com.kurukshetra.view.nurse;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class NurseToast {

    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, sans-serif;";

    // type : "success" | "error" | "info" | "warning"
    public static void show(StackPane overlay, String message, String type) {
        Platform.runLater(() -> {
            StackPane targetOverlay = (overlay != null) ? overlay : NurseDashboardPage.appOverlay;
            if (targetOverlay == null) {
                System.out.println("[NurseToast] (" + type + "): " + message);
                return;
            }

            // Ensure only ONE single popup exists on screen at any time (remove any previous toasts)
            targetOverlay.getChildren().removeIf(node -> Boolean.TRUE.equals(node.getProperties().get("isNurseToast")));

            String bg;
            String borderColor;
            String titleColor;
            String icon;
            String defaultTitle;

            String lowerType = (type != null) ? type.toLowerCase() : "info";
            switch (lowerType) {
                case "success":
                    bg = "#FFFFFF";
                    borderColor = "#10B981";
                    titleColor = "#059669";
                    icon = "✓";
                    defaultTitle = "SUCCESS";
                    break;
                case "error":
                case "danger":
                    bg = "#FFFFFF";
                    borderColor = "#EF4444";
                    titleColor = "#DC2626";
                    icon = "🚨";
                    defaultTitle = "EMERGENCY ALERT";
                    break;
                case "warning":
                    bg = "#FFFFFF";
                    borderColor = "#F59E0B";
                    titleColor = "#D97706";
                    icon = "⚠";
                    defaultTitle = "TRIAGE WARNING";
                    break;
                case "info":
                default:
                    bg = "#FFFFFF";
                    borderColor = "#E67593";
                    titleColor = "#D85375";
                    icon = "ℹ";
                    defaultTitle = "NURSE NOTIFICATION";
                    break;
            }

            // Compact popup card strictly constrained (never full screen)
            HBox toastCard = new HBox(12);
            toastCard.setAlignment(Pos.CENTER_LEFT);
            toastCard.setPadding(new Insets(12, 14, 12, 14));
            toastCard.setMinWidth(300);
            toastCard.setPrefWidth(370);
            toastCard.setMaxWidth(380);
            toastCard.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            toastCard.setPickOnBounds(true);
            toastCard.getProperties().put("isNurseToast", true);

            toastCard.setStyle(
                    FONT_FAMILY +
                    "-fx-background-color: " + bg + ";" +
                    "-fx-background-radius: 14px;" +
                    "-fx-border-color: " + borderColor + ";" +
                    "-fx-border-width: 1.5px;" +
                    "-fx-border-radius: 14px;" +
                    "-fx-effect: dropshadow(gaussian, rgba(20, 15, 18, 0.18), 18, 0, 0, 6);"
            );

            // Icon badge circle
            StackPane iconCircle = new StackPane();
            Circle circle = new Circle(15);
            circle.setFill(Color.web(borderColor));
            Text iconText = new Text(icon);
            iconText.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-fill: white;");
            iconCircle.getChildren().addAll(circle, iconText);

            // Content text area
            VBox textStack = new VBox(2);
            HBox.setHgrow(textStack, Priority.ALWAYS);

            Text titleText = new Text(defaultTitle);
            titleText.setStyle(FONT_FAMILY + "-fx-font-size: 11.5px; -fx-font-weight: bold; -fx-fill: " + titleColor + "; -fx-letter-spacing: 0.4px;");

            Label msgLbl = new Label(message);
            msgLbl.setWrapText(true);
            msgLbl.setMaxWidth(260);
            msgLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight: 500; -fx-text-fill: #1E293B;");
            textStack.getChildren().addAll(titleText, msgLbl);

            // Close button (✕)
            Button closeBtn = new Button("✕");
            closeBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: #94A3B8; -fx-font-size: 13px; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 0 4 0 4;");
            closeBtn.setOnMouseEntered(e -> closeBtn.setStyle(FONT_FAMILY + "-fx-background-color: #F1F5F9; -fx-text-fill: #334155; -fx-font-size: 13px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 6; -fx-padding: 0 4 0 4;"));
            closeBtn.setOnMouseExited(e -> closeBtn.setStyle(FONT_FAMILY + "-fx-background-color: transparent; -fx-text-fill: #94A3B8; -fx-font-size: 13px; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 0 4 0 4;"));

            toastCard.getChildren().addAll(iconCircle, textStack, closeBtn);

            // Position at TOP RIGHT corner (just like PoliceDashboard)
            StackPane.setAlignment(toastCard, Pos.TOP_RIGHT);
            StackPane.setMargin(toastCard, new Insets(20, 24, 0, 0));

            // Initial animation coordinates
            toastCard.setOpacity(0.0);
            toastCard.setTranslateX(40.0);

            targetOverlay.getChildren().add(toastCard);

            // Slide in from right and fade in
            FadeTransition fadeIn = new FadeTransition(Duration.millis(240), toastCard);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(240), toastCard);
            slideIn.setFromX(40.0);
            slideIn.setToX(0.0);

            ParallelTransition in = new ParallelTransition(fadeIn, slideIn);

            // Dismiss animation (slide out to right and fade out)
            Runnable dismissAction = () -> {
                FadeTransition fadeOut = new FadeTransition(Duration.millis(220), toastCard);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);

                TranslateTransition slideOut = new TranslateTransition(Duration.millis(220), toastCard);
                slideOut.setToX(40.0);

                ParallelTransition exit = new ParallelTransition(fadeOut, slideOut);
                exit.setOnFinished(e -> targetOverlay.getChildren().remove(toastCard));
                exit.play();
            };

            closeBtn.setOnAction(e -> dismissAction.run());

            // Auto-dismiss after 3.8 seconds
            PauseTransition hold = new PauseTransition(Duration.millis(3800));
            hold.setOnFinished(e -> {
                if (targetOverlay.getChildren().contains(toastCard)) {
                    dismissAction.run();
                }
            });

            in.play();
            hold.play();
        });
    }
}