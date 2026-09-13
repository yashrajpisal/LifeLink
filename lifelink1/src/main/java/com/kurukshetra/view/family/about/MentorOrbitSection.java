

package com.kurukshetra.view.family.about;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * LifeLink Mentor / Super Mentor floating ecosystem.
 *
 * Visual & Behavioral Design:
 * - Core2web logo stays visually in front of all floating circles.
 * - 10 mentor nodes are distributed evenly around the center ellipse.
 * - Concentric water-ripple waves continuously radiate outward from each circle's border.
 * - Nodes move organically with elegant velocity without overlapping or colliding.
 * - Nodes never cross into the Core2web central keep-out zone.
 * - Hovering any node freezes all motion immediately and brings it to the absolute front.
 * - Expands in place with photo/avatar, name, role badge, and description.
 * - Movement resumes smoothly on mouse leave.
 */
public class MentorOrbitSection {

    private static final String BG = "#07162E";
    private static final String FIELD_BG = "#081C36";
    private static final String WHITE = "#F5F8FC";
    private static final String SECONDARY = "#A9BCD0";
    private static final String CYAN = "#29C6D8";
    private static final String ORANGE = "#E17B32";
    private static final String PROFILE_BG = "#0B2341";

    private static final String FONT =
            "-fx-font-family: 'Sora', 'Segoe UI', -apple-system, BlinkMacSystemFont, sans-serif;";

    private static final double FIELD_WIDTH = 1060;
    private static final double FIELD_HEIGHT = 630;

    private static final double NODE_SIZE = 112;
    private static final double NODE_RADIUS = 56;
    private static final double CENTER_KEEP_OUT_RADIUS = 196;
    private static final double FIELD_PADDING = 24;

    private static final double MIN_SPEED = 0.28;
    private static final double MAX_SPEED = 0.52;

    private final Random random = new Random(42);
    private final List<NodeState> nodes = new ArrayList<>();
    private AnimationTimer timer;
    private boolean interactionActive = false;

    public VBox build() {
        VBox section = new VBox(24);
        section.setAlignment(Pos.CENTER);
        section.setPadding(new Insets(92, 30, 105, 30));
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
        Label eyebrow = new Label("CORE2WEB ECOSYSTEM");
        eyebrow.setStyle(FONT + "-fx-text-fill:" + ORANGE + ";-fx-font-size:11.5px;-fx-font-weight:800;-fx-letter-spacing:1.6px;");
        eyebrowBadge.getChildren().addAll(dot, eyebrow);

        Label title = new Label("Guidance that keeps the ecosystem moving");
        title.setStyle(FONT + "-fx-text-fill:" + WHITE + ";-fx-font-size:36px;-fx-font-weight:800;");

        Label subtitle = new Label("Super Mentors and Mentors surrounding the Core2web foundation.");
        subtitle.setStyle(FONT + "-fx-text-fill:" + SECONDARY + ";-fx-font-size:15.5px;");

        // Outer Stage Field
        StackPane stage = new StackPane();
        stage.setPrefSize(FIELD_WIDTH + 36, FIELD_HEIGHT + 36);
        stage.setMinSize(FIELD_WIDTH + 36, FIELD_HEIGHT + 36);
        stage.setMaxWidth(FIELD_WIDTH + 36);
        stage.setStyle(
                FONT +
                "-fx-background-color:" + FIELD_BG + ";" +
                "-fx-background-radius:28px;" +
                "-fx-border-color:rgba(41,198,216,0.20);" +
                "-fx-border-width:1.2px;" +
                "-fx-border-radius:28px;" +
                "-fx-effect:dropshadow(three-pass-box, rgba(0,0,0,0.32), 24, 0, 0, 6);"
        );

        Pane field = new Pane();
        field.setPrefSize(FIELD_WIDTH, FIELD_HEIGHT);
        field.setMinSize(FIELD_WIDTH, FIELD_HEIGHT);
        field.setMaxSize(FIELD_WIDTH, FIELD_HEIGHT);

        // Center Core2web Logo
        StackPane logoPane = buildCenterLogo();
        logoPane.setLayoutX((FIELD_WIDTH - 250) / 2.0);
        logoPane.setLayoutY((FIELD_HEIGHT - 250) / 2.0);

        // Mentor Data
        String[][] data = {
                {
                        "Shiv Dada",
                        "Super Mentor",
                        "/assets/Images/shiv.jpeg",
                        "Supports learners through practical guidance, deep experience, and systematic problem solving."
                },
                {
                        "Subodh Dada",
                        "Super Mentor",
                        "/assets/Images/subhodh.jpeg",
                        "Helps learners stay focused, consistent, and solution-oriented during complex challenges."
                },
                {
                        "Vishal Dada",
                        "Super Mentor",
                        "/assets/Images/vishal.jpeg",
                        "Brings practical perspective and guidance that connects structured learning with real product execution."
                },
                {
                        "Rahul Dada",
                        "Super Mentor",
                        "/assets/Images/rahul.jpeg",
                        "Encourages disciplined learning, thoughtful architecture decisions, and confidence in solving problems."
                },
                {
                        "Prajwal Dada",
                        "Super Mentor",
                        "/assets/Images/pr.jpeg",
                        "Supports teamwork, collaboration, and continuous practical progress across the wider ecosystem."
                },
                {
                        "Govind Dada",
                        "Super Mentor",
                        "/assets/Images/govind.jpeg",
                        "Guides learners through real-world thinking, personal accountability, and continuous improvement."
                },
                {
                        "Sumit Dada",
                        "Mentor",
                        "/assets/Images/sumit.jpeg",
                        "Provides focused mentoring and encouragement while learners construct and refine their skills."
                },
                {
                        "Mansi Didi",
                        "Mentor",
                        "/assets/Images/manashi.jpeg",
                        "Helps learners navigate from questions to practical clarity with patient and consistent guidance."
                },
                {
                        "Dhanashri Didi",
                        "Mentor",
                        "/assets/Images/dhanashree.jpeg",
                        "Supports learners with approachable guidance, collaboration, and constructive practical feedback."
                },
                {
                        "Sayali Didi",
                        "Mentor",
                        "/assets/Images/sayali.jpeg",
                        "Encourages steady personal growth, confidence, and methodical problem-solving habits."
                }
        };

        // Add moving nodes FIRST
        for (int i = 0; i < data.length; i++) {
            MentorNode mentorNode = new MentorNode(data[i][0], data[i][1], data[i][2], data[i][3]);
            NodeState state = new NodeState(mentorNode.node);
            state.profileNode = mentorNode.profile;
            nodes.add(state);
            field.getChildren().add(mentorNode.node);
        }

        // Add Center Logo LAST -> Logo strictly stays in front of every moving circle
        field.getChildren().add(logoPane);
        stage.getChildren().add(field);

        section.getChildren().addAll(eyebrowBadge, title, subtitle, stage);

        initializePositions();
        startAnimation();

        return section;
    }

    private StackPane buildCenterLogo() {
        StackPane logoPane = new StackPane();
        logoPane.setPrefSize(250, 250);
        logoPane.setMinSize(250, 250);
        logoPane.setMaxSize(250, 250);

        // Soft ambient aura
        Circle aura = new Circle(120, Color.web(CYAN, 0.08));
        aura.setStroke(Color.web(CYAN, 0.32));
        aura.setStrokeWidth(1.2);

        // Inner glowing ring
        Circle outerRing = new Circle(98);
        outerRing.setFill(Color.web("#0A2139", 0.96));
        outerRing.setStroke(Color.web(CYAN, 0.60));
        outerRing.setStrokeWidth(1.6);

        logoPane.getChildren().addAll(aura, outerRing);

        ImageView logo = loadLogo();
        if (logo != null) {
            logo.setFitWidth(130);
            logo.setFitHeight(130);
            logo.setPreserveRatio(true);
            logo.setSmooth(true);
            logoPane.getChildren().add(logo);
        } else {
            Label core = new Label("CORE2WEB");
            core.setStyle(FONT + "-fx-text-fill:" + WHITE + ";-fx-font-size:24px;-fx-font-weight:900;-fx-letter-spacing:2px;");
            logoPane.getChildren().add(core);
        }

        return logoPane;
    }

    private void initializePositions() {
        final double centerX = FIELD_WIDTH / 2.0;
        final double centerY = FIELD_HEIGHT / 2.0;
        final int count = nodes.size();
        final double radiusX = 390;
        final double radiusY = 225;

        for (int i = 0; i < count; i++) {
            NodeState state = nodes.get(i);
            double angle = -Math.PI / 2.0 + (Math.PI * 2.0 * i / count);

            double variationX = (random.nextDouble() - 0.5) * 32;
            double variationY = (random.nextDouble() - 0.5) * 22;

            double x = centerX + Math.cos(angle) * radiusX + variationX - NODE_RADIUS;
            double y = centerY + Math.sin(angle) * radiusY + variationY - NODE_RADIUS;

            state.homeX = clamp(x, FIELD_PADDING, FIELD_WIDTH - NODE_SIZE - FIELD_PADDING);
            state.homeY = clamp(y, FIELD_PADDING, FIELD_HEIGHT - NODE_SIZE - FIELD_PADDING);

            state.x = state.homeX;
            state.y = state.homeY;

            state.vx = randomVelocity();
            state.vy = randomVelocity();

            state.node.relocate(state.x, state.y);
        }
    }

    private void startAnimation() {
        timer = new AnimationTimer() {
            private long last = -1;

            @Override
            public void handle(long now) {
                if (last < 0) {
                    last = now;
                    return;
                }
                last = now;

                if (interactionActive) return;

                moveNodes();
                avoidCoreOverlap();
                preventNodeCollisions();
                keepInsideField();
                updateNodeLocations();
            }
        };
        timer.start();
    }

    private void moveNodes() {
        for (NodeState state : nodes) {
            // Spring tether to home position keeps distribution balanced on all sides
            double homeForceX = (state.homeX - state.x) * 0.0018;
            double homeForceY = (state.homeY - state.y) * 0.0018;

            state.vx += homeForceX;
            state.vy += homeForceY;

            // Small organic wander
            if (random.nextDouble() < 0.02) {
                state.vx += (random.nextDouble() - 0.5) * 0.035;
                state.vy += (random.nextDouble() - 0.5) * 0.035;
            }

            state.x += state.vx;
            state.y += state.vy;

            state.vx = clamp(state.vx, -MAX_SPEED, MAX_SPEED);
            state.vy = clamp(state.vy, -MAX_SPEED, MAX_SPEED);
        }
    }

    private void avoidCoreOverlap() {
        final double centerX = FIELD_WIDTH / 2.0;
        final double centerY = FIELD_HEIGHT / 2.0;
        final double minDistance = CENTER_KEEP_OUT_RADIUS;

        for (NodeState state : nodes) {
            double nodeCenterX = state.x + NODE_RADIUS;
            double nodeCenterY = state.y + NODE_RADIUS;

            double dx = nodeCenterX - centerX;
            double dy = nodeCenterY - centerY;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance == 0) {
                dx = 1;
                dy = 0;
                distance = 1;
            }

            if (distance < minDistance) {
                double push = minDistance - distance;
                double nx = dx / distance;
                double ny = dy / distance;

                state.x += nx * push;
                state.y += ny * push;

                state.vx += nx * 0.04;
                state.vy += ny * 0.04;
            }
        }
    }

    private void preventNodeCollisions() {
        final double minDistance = 120;

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                NodeState a = nodes.get(i);
                NodeState b = nodes.get(j);

                double ax = a.x + NODE_RADIUS;
                double ay = a.y + NODE_RADIUS;
                double bx = b.x + NODE_RADIUS;
                double by = b.y + NODE_RADIUS;

                double dx = bx - ax;
                double dy = by - ay;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance == 0) distance = 0.01;

                if (distance < minDistance) {
                    double push = (minDistance - distance) / 2.0;
                    double nx = dx / distance;
                    double ny = dy / distance;

                    a.x -= nx * push;
                    a.y -= ny * push;
                    b.x += nx * push;
                    b.y += ny * push;

                    a.vx -= nx * 0.015;
                    a.vy -= ny * 0.015;
                    b.vx += nx * 0.015;
                    b.vy += ny * 0.015;
                }
            }
        }
    }

    private void keepInsideField() {
        final double minX = FIELD_PADDING;
        final double minY = FIELD_PADDING;
        final double maxX = FIELD_WIDTH - NODE_SIZE - FIELD_PADDING;
        final double maxY = FIELD_HEIGHT - NODE_SIZE - FIELD_PADDING;

        for (NodeState state : nodes) {
            if (state.x < minX) {
                state.x = minX;
                state.vx = Math.abs(state.vx);
            }
            if (state.x > maxX) {
                state.x = maxX;
                state.vx = -Math.abs(state.vx);
            }
            if (state.y < minY) {
                state.y = minY;
                state.vy = Math.abs(state.vy);
            }
            if (state.y > maxY) {
                state.y = maxY;
                state.vy = -Math.abs(state.vy);
            }

            state.x = clamp(state.x, minX, maxX);
            state.y = clamp(state.y, minY, maxY);
        }
    }

    private void updateNodeLocations() {
        for (NodeState state : nodes) {
            state.node.relocate(state.x, state.y);
        }
    }

    private final class MentorNode {
        private final StackPane node = new StackPane();
        private final StackPane normal = new StackPane();
        private final VBox profile = new VBox(8);
        private final String position;

        MentorNode(String name, String position, String imagePath, String description) {
            this.position = position;

            node.setPrefSize(NODE_SIZE, NODE_SIZE);
            node.setMinSize(NODE_SIZE, NODE_SIZE);
            node.setMaxSize(NODE_SIZE, NODE_SIZE);
            node.setPickOnBounds(true);

            // Normal State Circle
            Circle circle = new Circle(53);
            boolean isSuper = position.equals("Super Mentor");

            circle.setFill(Color.web(isSuper ? "#102D49" : "#0E2742", 0.98));
            circle.setStroke(Color.web(isSuper ? ORANGE : CYAN, 0.90));
            circle.setStrokeWidth(isSuper ? 2.2 : 1.7);

            normal.setPrefSize(NODE_SIZE, NODE_SIZE);

            // Concentric water-ripple waves that emanate outward from the circular border
            List<ParallelTransition> waveTransitions = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Circle wave = new Circle(53);
                wave.setFill(Color.TRANSPARENT);
                wave.setStroke(Color.web(isSuper ? ORANGE : CYAN, 0.70));
                wave.setStrokeWidth(1.3);
                wave.setMouseTransparent(true);
                wave.setScaleX(1.0);
                wave.setScaleY(1.0);
                wave.setOpacity(0.0);

                ScaleTransition st = new ScaleTransition(Duration.millis(2600), wave);
                st.setFromX(1.0);
                st.setFromY(1.0);
                st.setToX(1.56);
                st.setToY(1.56);
                st.setInterpolator(Interpolator.EASE_OUT);

                FadeTransition ft = new FadeTransition(Duration.millis(2600), wave);
                ft.setFromValue(0.65);
                ft.setToValue(0.0);
                ft.setInterpolator(Interpolator.EASE_OUT);

                ParallelTransition pt = new ParallelTransition(st, ft);
                pt.setCycleCount(Animation.INDEFINITE);

                int delay = i * 860;
                if (delay > 0) {
                    PauseTransition pause = new PauseTransition(Duration.millis(delay));
                    pause.setOnFinished(e -> pt.play());
                    pause.play();
                } else {
                    pt.play();
                }

                waveTransitions.add(pt);
                normal.getChildren().add(wave);
            }

            // Main solid circle & label (rendered in front of expanding ripple waves)
            normal.getChildren().addAll(circle);

            // Name centered and clearly readable inside the circle
            Label nameLabel = new Label(name);
            nameLabel.setWrapText(true);
            nameLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            nameLabel.setAlignment(Pos.CENTER);
            nameLabel.setMaxWidth(96);
            nameLabel.setStyle(FONT +
                    "-fx-text-fill:" + WHITE + ";" +
                    "-fx-font-size:12px;" +
                    "-fx-font-weight:800;" +
                    "-fx-alignment:center;"
            );
            normal.getChildren().add(nameLabel);

            // Expanded In-Place Profile Panel
            profile.setPrefWidth(240);
            profile.setMinWidth(240);
            profile.setMaxWidth(240);
            profile.setPadding(new Insets(14, 14, 14, 14));
            profile.setAlignment(Pos.CENTER);
            String glowRgba = isSuper ? "rgba(225, 123, 50, 0.42)" : "rgba(41, 198, 216, 0.42)";
            profile.setStyle(
                    FONT +
                    "-fx-background-color:" + PROFILE_BG + ";" +
                    "-fx-border-color:" + (isSuper ? ORANGE : CYAN) + ";" +
                    "-fx-border-width:1.5px;" +
                    "-fx-border-radius:24px;" +
                    "-fx-background-radius:24px;" +
                    "-fx-effect:dropshadow(three-pass-box, " + glowRgba + ", 26, 0.12, 0, 7);" +
                    "-fx-cursor:hand;"
            );

            ImageView expandedImage = loadImage(imagePath);
            if (expandedImage != null) {
                expandedImage.setFitWidth(78);
                expandedImage.setFitHeight(78);
                expandedImage.setPreserveRatio(true);
                expandedImage.setSmooth(true);
                javafx.scene.shape.Rectangle imgClip = new javafx.scene.shape.Rectangle(78, 78);
                imgClip.setArcWidth(18);
                imgClip.setArcHeight(18);
                expandedImage.setClip(imgClip);
                profile.getChildren().add(expandedImage);
            } else {
                // Elegant Monogram Avatar fallback
                StackPane avatar = new StackPane();
                avatar.setPrefSize(68, 68);
                Circle avCircle = new Circle(34, Color.web(isSuper ? "#1D3A5C" : "#133758"));
                avCircle.setStroke(Color.web(isSuper ? ORANGE : CYAN, 0.7));
                avCircle.setStrokeWidth(1.5);
                String initials = getInitials(name);
                Label avLabel = new Label(initials);
                avLabel.setStyle(FONT + "-fx-text-fill:" + (isSuper ? ORANGE : CYAN) + ";-fx-font-size:17px;-fx-font-weight:bold;");
                avatar.getChildren().addAll(avCircle, avLabel);
                profile.getChildren().add(avatar);
            }

            Label expandedName = new Label(name);
            expandedName.setStyle(FONT + "-fx-text-fill:" + WHITE + ";-fx-font-size:15px;-fx-font-weight:800;");
            expandedName.setWrapText(true);
            expandedName.setAlignment(Pos.CENTER);

            // Premium Position Pill matching showcase cards
            StackPane positionBox = new StackPane();
            positionBox.setPadding(new Insets(3, 11, 3, 11));
            positionBox.setStyle(
                    FONT +
                    "-fx-background-color: " + (isSuper ? "rgba(225, 123, 50, 0.12)" : "rgba(41, 198, 216, 0.12)") + ";" +
                    "-fx-border-color: " + (isSuper ? "rgba(225, 123, 50, 0.35)" : "rgba(41, 198, 216, 0.35)") + ";" +
                    "-fx-border-radius: 10px;" +
                    "-fx-background-radius: 10px;"
            );
            Label expandedPosition = new Label(position.toUpperCase());
            expandedPosition.setStyle(FONT +
                    "-fx-text-fill:" + (isSuper ? ORANGE : CYAN) + ";" +
                    "-fx-font-size:10px;" +
                    "-fx-font-weight:900;" +
                    "-fx-letter-spacing:1.2px;"
            );
            positionBox.getChildren().add(expandedPosition);

            Label descriptionLabel = new Label(description);
            descriptionLabel.setWrapText(true);
            descriptionLabel.setMaxWidth(205);
            descriptionLabel.setAlignment(Pos.CENTER);
            descriptionLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            descriptionLabel.setStyle(FONT + "-fx-text-fill:" + SECONDARY + ";-fx-font-size:11px;-fx-line-spacing:2.5px;");

            profile.getChildren().addAll(expandedName, positionBox, descriptionLabel);

            profile.setVisible(false);
            profile.setManaged(false);
            profile.setOpacity(0);

            node.getChildren().addAll(normal, profile);

            // Hover interactions
            node.setOnMouseEntered(event -> {
                interactionActive = true;
                node.toFront();

                for (ParallelTransition pt : waveTransitions) {
                    pt.pause();
                }

                profile.setManaged(true);
                profile.setVisible(true);

                FadeTransition pf = new FadeTransition(Duration.millis(180), profile);
                pf.setFromValue(0);
                pf.setToValue(1);

                ScaleTransition ps = new ScaleTransition(Duration.millis(200), profile);
                ps.setFromX(0.85);
                ps.setFromY(0.85);
                ps.setToX(1.0);
                ps.setToY(1.0);

                FadeTransition nf = new FadeTransition(Duration.millis(150), normal);
                nf.setToValue(0);

                new ParallelTransition(pf, ps, nf).play();
            });

            node.setOnMouseExited(event -> {
                FadeTransition pf = new FadeTransition(Duration.millis(160), profile);
                pf.setToValue(0);

                FadeTransition nf = new FadeTransition(Duration.millis(160), normal);
                nf.setToValue(1);

                pf.setOnFinished(finishEvent -> {
                    profile.setVisible(false);
                    profile.setManaged(false);
                    interactionActive = false;
                    for (ParallelTransition pt : waveTransitions) {
                        pt.play();
                    }
                });

                new ParallelTransition(pf, nf).play();
            });
        }

        private String getInitials(String fullName) {
            String[] parts = fullName.trim().split("\\s+");
            if (parts.length >= 2) {
                return ("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
            } else if (parts.length == 1 && !parts[0].isEmpty()) {
                return ("" + parts[0].charAt(0)).toUpperCase();
            }
            return "LM";
        }
    }

    private static final class NodeState {
        final StackPane node;
        Node profileNode;
        double x;
        double y;
        double homeX;
        double homeY;
        double vx;
        double vy;

        NodeState(StackPane node) {
            this.node = node;
        }
    }

    private ImageView loadLogo() {
        String[] paths = {
                "/assets/Images/about/core2web.png",
                "/assets/Images/core2web.png",
                // "C:\\Users\\Acer\\Desktop\\Java_SuperX_jp\\LifeLink\\LifeLink\\lifelink1\\src\\main\\resources\\assets\\Images\\about\\core2web.png",
                // "/assets/Images/Core2web.png"
        };

        for (String path : paths) {
            try {
                var url = MentorOrbitSection.class.getResource(path);
                if (url != null) {
                    return new ImageView(new Image(url.toExternalForm(), true));
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private ImageView loadImage(String path) {
        try {
            var url = MentorOrbitSection.class.getResource(path);
            if (url != null) {
                return new ImageView(new Image(url.toExternalForm(), true));
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private double randomVelocity() {
        double magnitude = MIN_SPEED + random.nextDouble() * (MAX_SPEED - MIN_SPEED);
        return random.nextBoolean() ? magnitude : -magnitude;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
