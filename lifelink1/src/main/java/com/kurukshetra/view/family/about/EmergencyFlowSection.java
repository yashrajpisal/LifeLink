package com.kurukshetra.view.family.about;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Dark-theme emergency response flow with coordinated, exclusive hover details.
 * Exactly one step's side information can be visible at any time.
 */
public class EmergencyFlowSection {

    private static final String BG = "#0A1C36";
    private static final String SURFACE = "#102A48";
    private static final String SURFACE_HOVER = "#13345A";
    private static final String BORDER_DEFAULT = "rgba(41, 198, 216, 0.18)";
    private static final String BORDER_ACTIVE = "#29C6D8";
    private static final String PRIMARY = "#F5F8FC";
    private static final String SECONDARY = "#A9BCD0";
    private static final String ORANGE = "#E17B32";
    private static final String CYAN = "#29C6D8";
    private static final String FONT =
            "-fx-font-family: 'Sora', 'Segoe UI', -apple-system, BlinkMacSystemFont, sans-serif;";

    private FlowStep activeStep = null;
    private final List<FlowStep> allSteps = new ArrayList<>();

    public VBox build() {
        VBox section = new VBox(24);
        section.setAlignment(Pos.CENTER);
        section.setPadding(new Insets(90, 36, 110, 36));
        section.setMaxWidth(Double.MAX_VALUE);
        section.setStyle(FONT + "-fx-background-color:" + BG + ";");

        // Section Eyebrow Badge
        HBox eyebrowBadge = new HBox(8);
        eyebrowBadge.setAlignment(Pos.CENTER);
        eyebrowBadge.setMaxWidth(380);
        eyebrowBadge.setPadding(new Insets(6, 14, 6, 12));
        eyebrowBadge.setStyle(
                FONT +
                "-fx-background-color: rgba(225, 123, 50, 0.12);" +
                "-fx-border-color: rgba(225, 123, 50, 0.35);" +
                "-fx-border-radius: 16px;" +
                "-fx-background-radius: 16px;"
        );
        Circle dot = new Circle(4, Color.web(ORANGE));
        Label eyebrow = label("HOW LIFELINK CONNECTS EVERYONE", ORANGE, 11.5, 800);
        eyebrow.setStyle(eyebrow.getStyle() + "-fx-letter-spacing:1.6px;");
        eyebrowBadge.getChildren().addAll(dot, eyebrow);

        // Section Titles
        Label title = label("Emergency information travels through the system", PRIMARY, 36, 800);
        title.setWrapText(true);
        title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Label subtitle = label("and reaches the right people at the right time.", SECONDARY, 16, 500);

        VBox flow = new VBox(32);
        flow.setAlignment(Pos.CENTER);
        flow.setMaxWidth(1180);

        addStep(flow, "01", "PATIENT", "Emergency arises — LifeLink is activated",
                "Emergency Detection", "Critical situations trigger the LifeLink response workflow instantly.",
                "Instant Activation", "Emergency payload immediately enters the response network chain.");

        addStep(flow, "02", "AMBULANCE", "Nearest unit dispatched with live ETA",
                "Past Medical Context", "Relevant emergency and family history helps responders prepare beforehand.",
                "Ambulance Coordination", "Nearest available ambulance unit receives the dispatch with live navigation.");

        addStep(flow, "03", "POLICE / TRAFFIC SUPPORT", "Cleared routes for faster movement",
                "Route Prioritization", "Traffic support clears priority green corridors for rapid transport.",
                "Real-time Telemetry", "All responders stay synchronized with live telemetry while moving.");

        addStep(flow, "04", "HOSPITAL", "Ward, doctor and equipment made ready",
                "Smart Facility Match", "LifeLink evaluates hospital capability, bed availability, and travel time.",
                "Pre-arrival Triage", "Hospital trauma teams receive vital signs and prepare before arrival.");

        addStep(flow, "05", "DOCTOR / TREATMENT", "Care begins the moment the patient arrives",
                "Zero Delay Handoff", "Specialists are stationed at trauma bay the instant the ambulance arrives.",
                "Immediate Treatment", "Comprehensive medical action commences with no administrative delay.");

        section.getChildren().addAll(eyebrowBadge, title, subtitle, flow);
        return section;
    }

    private void addStep(VBox parent, String stepNum, String title, String description,
                         String leftTitle, String leftDesc,
                         String rightTitle, String rightDesc) {

        FlowStep step = new FlowStep(stepNum, title, description, leftTitle, leftDesc, rightTitle, rightDesc);
        allSteps.add(step);
        parent.getChildren().add(step.container);
    }

    private class FlowStep {
        final StackPane container = new StackPane();
        final VBox card = new VBox(8);
        final VBox leftDetail;
        final VBox rightDetail;
        ParallelTransition currentTransition = null;

        FlowStep(String stepNum, String title, String description,
                 String leftTitle, String leftDesc,
                 String rightTitle, String rightDesc) {

            container.setPrefWidth(1180);
            container.setMaxWidth(1180);
            container.setPrefHeight(115);

            // Illuminated vertical connector line
            Line connector = new Line(0, 0, 0, 52);
            connector.setStroke(Color.web("#173F63", 0.85));
            connector.setStrokeWidth(1.8);
            StackPane.setAlignment(connector, Pos.BOTTOM_CENTER);
            StackPane.setMargin(connector, new Insets(0, 0, -28, 0));

            HBox row = new HBox(22);
            row.setAlignment(Pos.CENTER);
            row.setPrefWidth(1180);
            row.setMaxWidth(1180);

            VBox leftSlot = new VBox();
            leftSlot.setAlignment(Pos.CENTER_RIGHT);
            leftSlot.setPrefWidth(270);
            leftSlot.setMinWidth(270);
            leftSlot.setMaxWidth(270);

            VBox rightSlot = new VBox();
            rightSlot.setAlignment(Pos.CENTER_LEFT);
            rightSlot.setPrefWidth(270);
            rightSlot.setMinWidth(270);
            rightSlot.setMaxWidth(270);

            leftDetail = buildDetailBox(leftTitle, leftDesc, true);
            rightDetail = buildDetailBox(rightTitle, rightDesc, false);

            leftDetail.setOpacity(0);
            leftDetail.setTranslateX(20);
            leftDetail.setMouseTransparent(true);

            rightDetail.setOpacity(0);
            rightDetail.setTranslateX(-20);
            rightDetail.setMouseTransparent(true);

            leftSlot.getChildren().add(leftDetail);
            rightSlot.getChildren().add(rightDetail);

            // Main Flow Card
            card.setAlignment(Pos.CENTER_LEFT);
            card.setPadding(new Insets(18, 28, 18, 28));
            card.setPrefWidth(590);
            card.setMinWidth(590);
            card.setMaxWidth(590);
            card.setStyle(cardStyle(false));

            HBox titleRow = new HBox(12);
            titleRow.setAlignment(Pos.CENTER_LEFT);

            Label numBadge = new Label(stepNum);
            numBadge.setStyle(FONT +
                    "-fx-text-fill:" + CYAN + ";" +
                    "-fx-font-size:11px;" +
                    "-fx-font-weight:900;" +
                    "-fx-background-color:rgba(41,198,216,0.12);" +
                    "-fx-padding:3px 8px;" +
                    "-fx-background-radius:8px;" +
                    "-fx-border-color:rgba(41,198,216,0.25);" +
                    "-fx-border-radius:8px;"
            );

            Label titleLabel = label(title, PRIMARY, 17.5, 800);
            titleRow.getChildren().addAll(numBadge, titleLabel);

            Label descLabel = label(description, SECONDARY, 14, 500);
            descLabel.setWrapText(true);

            card.getChildren().addAll(titleRow, descLabel);
            row.getChildren().addAll(leftSlot, card, rightSlot);
            container.getChildren().addAll(connector, row);

            // Hover interactions: Single active step guarantee
            container.setOnMouseEntered(e -> {
                if (activeStep != null && activeStep != this) {
                    activeStep.hideDetails(true);
                }
                activeStep = this;
                card.setStyle(cardStyle(true));
                showDetails();
            });

            container.setOnMouseExited(e -> {
                card.setStyle(cardStyle(false));
                hideDetails(false);
                if (activeStep == this) {
                    activeStep = null;
                }
            });
        }

        private VBox buildDetailBox(String title, String desc, boolean isLeft) {
            VBox box = new VBox(6);
            box.setAlignment(Pos.CENTER_LEFT);
            box.setMaxWidth(255);
            box.setPadding(new Insets(12, 18, 12, 18));
            box.setStyle(
                    FONT +
                    "-fx-background-color: #0D2440;" +
                    "-fx-border-color: rgba(41, 198, 216, 0.40);" +
                    "-fx-border-radius: 16px;" +
                    "-fx-background-radius: 16px;" +
                    "-fx-effect: dropshadow(three-pass-box, rgba(41, 198, 216, 0.12), 14, 0, 0, 3);"
            );

            HBox header = new HBox(7);
            header.setAlignment(Pos.CENTER_LEFT);
            Circle dot = new Circle(3.5, Color.web(isLeft ? ORANGE : CYAN));
            Label t = label(title, PRIMARY, 13.5, 800);
            header.getChildren().addAll(dot, t);

            Label d = label(desc, SECONDARY, 12, 500);
            d.setWrapText(true);
            d.setStyle(d.getStyle() + "-fx-line-spacing:2px;");

            box.getChildren().addAll(header, d);
            return box;
        }

        void showDetails() {
            if (currentTransition != null) currentTransition.stop();

            leftDetail.setMouseTransparent(false);
            rightDetail.setMouseTransparent(false);

            FadeTransition lf = new FadeTransition(Duration.millis(200), leftDetail);
            lf.setToValue(1);
            TranslateTransition lt = new TranslateTransition(Duration.millis(220), leftDetail);
            lt.setToX(0);

            FadeTransition rf = new FadeTransition(Duration.millis(200), rightDetail);
            rf.setToValue(1);
            TranslateTransition rt = new TranslateTransition(Duration.millis(220), rightDetail);
            rt.setToX(0);

            currentTransition = new ParallelTransition(lf, lt, rf, rt);
            currentTransition.play();
        }

        void hideDetails(boolean immediate) {
            if (currentTransition != null) currentTransition.stop();

            leftDetail.setMouseTransparent(true);
            rightDetail.setMouseTransparent(true);

            int dur = immediate ? 90 : 160;

            FadeTransition lf = new FadeTransition(Duration.millis(dur), leftDetail);
            lf.setToValue(0);
            TranslateTransition lt = new TranslateTransition(Duration.millis(dur), leftDetail);
            lt.setToX(20);

            FadeTransition rf = new FadeTransition(Duration.millis(dur), rightDetail);
            rf.setToValue(0);
            TranslateTransition rt = new TranslateTransition(Duration.millis(dur), rightDetail);
            rt.setToX(-20);

            currentTransition = new ParallelTransition(lf, lt, rf, rt);
            currentTransition.play();
        }

        private String cardStyle(boolean hovered) {
            if (hovered) {
                return FONT +
                        "-fx-background-color:" + SURFACE_HOVER + ";" +
                        "-fx-border-color:" + BORDER_ACTIVE + ";" +
                        "-fx-border-radius:18px;" +
                        "-fx-background-radius:18px;" +
                        "-fx-effect:dropshadow(three-pass-box, rgba(41,198,216,0.22),22,0,0,5);" +
                        "-fx-cursor:hand;";
            } else {
                return FONT +
                        "-fx-background-color:" + SURFACE + ";" +
                        "-fx-border-color:" + BORDER_DEFAULT + ";" +
                        "-fx-border-radius:18px;" +
                        "-fx-background-radius:18px;" +
                        "-fx-effect:dropshadow(three-pass-box, rgba(0,0,0,0.24),18,0,0,4);" +
                        "-fx-cursor:hand;";
            }
        }
    }

    private Label label(String text, String color, double size, int weight) {
        Label label = new Label(text);
        label.setStyle(FONT +
                "-fx-text-fill:" + color + ";" +
                "-fx-font-size:" + size + "px;" +
                "-fx-font-weight:" + weight + ";");
        return label;
    }
}
