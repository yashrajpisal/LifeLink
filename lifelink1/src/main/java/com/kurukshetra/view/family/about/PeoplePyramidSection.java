package com.kurukshetra.view.family.about;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Teacher and Mentor pyramid presentation with naturally integrated transparent portraits,
 * smooth scroll-triggered upward entrance, live typewriter hover effects,
 * and interactive Core2web & Kurukshetra digital dust particle logos that follow the cursor,
 * assemble into crisp official logos, and can be frozen / unfrozen at any position via mouse clicks.
 */
public class PeoplePyramidSection {

    private static final String BG = "#0B203B";
    private static final String PRIMARY = "#F5F8FC";
    private static final String SECONDARY = "#A9BCD0";
    private static final String MUTED = "#6F88A6";
    private static final String CYAN = "#29C6D8";
    private static final String ORANGE = "#E17B32";
    private static final String FONT =
            "-fx-font-family: 'Sora', 'Segoe UI', -apple-system, BlinkMacSystemFont, sans-serif;";

    private static final String[] LOGO_ASSET_PATHS = {
            "/assets/Images/about/core2web_official.jpg",
            "/assets/Images/about/core2web-logo.png",
            "/assets/Images/about/core2web-logo.png.png"
    };
    private static final String[] KURUKSHETRA_ASSET_PATHS = {
            "/assets/Images/about/kurukshetra_logo.png"
    };
    private static final double CORE2WEB_TARGET_SIZE = 215.0;
    private static final double KURUKSHETRA_TARGET_SIZE = 260.0;

    public StackPane build() {
        StackPane root = new StackPane();
        root.setMaxWidth(Double.MAX_VALUE);
        root.setStyle(FONT + "-fx-background-color:" + BG + ";");

        // Strictly clip the particle system to this section's boundaries
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(root.widthProperty());
        clip.heightProperty().bind(root.heightProperty());
        root.setClip(clip);

        // Particle Canvas Layer (behind content, mouse-transparent)
        ResizableCanvas canvas = new ResizableCanvas();
        canvas.setMouseTransparent(true);
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());

        // Content Layer (VBox)
        VBox section = new VBox(32);
        section.setAlignment(Pos.CENTER);
        section.setPadding(new Insets(95, 40, 115, 40));
        section.setMaxWidth(Double.MAX_VALUE);
        section.setStyle("-fx-background-color: transparent;");

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
        Label eyebrow = new Label("PEOPLE BEHIND LIFELINK");
        eyebrow.setStyle(FONT + "-fx-text-fill:" + ORANGE + ";-fx-font-size:11.5px;-fx-font-weight:800;-fx-letter-spacing:1.6px;");
        eyebrowBadge.getChildren().addAll(dot, eyebrow);

        Label title = new Label("The people guiding the journey");
        title.setStyle(FONT + "-fx-text-fill:" + PRIMARY + ";-fx-font-size:36px;-fx-font-weight:800;");

        // Top Guide (Shashi Sir)
        ProfileCard shashi = new ProfileCard(
                "Shashi Sir", "Teacher", "/assets/Images/about/team-leads/shashi_sir.png",
                "Guiding the vision behind LifeLink with a focus on practical learning, problem solving and building technology that creates real-world impact.",
                260, 260, 27, 340
        );

        // Second Row Instructors
        ProfileCard sachin = new ProfileCard(
                "Sachin Sir", "Instructor", "/assets/Images/shachin.jpeg",
                "Helping learners turn concepts into practical solutions through consistent guidance, discipline and hands-on development.",
                225, 225, 23, 290
        );

        ProfileCard pramod = new ProfileCard(
                "Pramod Sir", "Instructor", "/assets/Images/pramodsir.png",
                "Encouraging deeper technical understanding and practical thinking while supporting learners through real project challenges.",
                225, 225, 23, 290
        );

        ProfileCard akshay = new ProfileCard(
                "Akshay Sir", "Instructor", "/assets/Images/about/team-leads/akshay_sir.png",
                "Supporting technical growth through practical learning, problem solving and a strong focus on building useful products.",
                225, 225, 23, 290
        );

        HBox secondRow = new HBox(48, sachin.node, pramod.node, akshay.node);
        secondRow.setAlignment(Pos.TOP_CENTER);
        secondRow.setMaxWidth(1160);

        section.getChildren().addAll(
                eyebrowBadge,
                title,
                shashi.node,
                secondRow
        );

        // Initialize Core2web & Kurukshetra digital dust particle simulations
        setupParticleSystem(root, canvas, shashi);

        // Layer order: Background -> Particle Canvas -> Profile Content
        root.getChildren().addAll(canvas, section);

        // Smooth upward entrance when entering viewport
        armScrollReveal(shashi, 0);
        armScrollReveal(sachin, 120);
        armScrollReveal(pramod, 240);
        armScrollReveal(akshay, 360);

        return root;
    }

    // =========================================================================
    // DUST PARTICLE SYSTEM (CORE2WEB & KURUKSHETRA)
    // =========================================================================

    private static class LogoPoint {
        final double dx;
        final double dy;
        final int r, g, b;

        LogoPoint(double dx, double dy, int r, int g, int b) {
            this.dx = dx;
            this.dy = dy;
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    private static class Particle {
        double x, y;
        double vx, vy;
        final double targetDx, targetDy;
        final double trailOffsetX, trailOffsetY;
        final double spring;
        final double damping;
        final double radius;
        final double baseAlpha;
        final double phase;
        final double sparkSeed;
        final int r, g, b;

        Particle(LogoPoint pt, Random rng) {
            this.targetDx = pt.dx;
            this.targetDy = pt.dy;
            this.r = pt.r;
            this.g = pt.g;
            this.b = pt.b;

            // Random cloud offset for trailing dust behind mouse
            double tAngle = rng.nextDouble() * Math.PI * 2.0;
            double tDist = rng.nextDouble() * 32.0;
            this.trailOffsetX = Math.cos(tAngle) * tDist;
            this.trailOffsetY = Math.sin(tAngle) * tDist;

            // Varied physics for natural, non-uniform trailing lag
            this.spring = 0.052 + rng.nextDouble() * 0.040;
            this.damping = 0.80 + rng.nextDouble() * 0.055;
            this.radius = 1.05 + rng.nextDouble() * 0.35;
            this.baseAlpha = 0.85 + rng.nextDouble() * 0.15;
            this.phase = rng.nextDouble() * Math.PI * 2.0;
            this.sparkSeed = rng.nextDouble();
        }
    }

    private static class DustLogo {
        final String name;
        final List<Particle> particles;
        final double hitRadius;

        boolean isEnabled = false;
        boolean isFrozen = false;

        double frozenX = 0;
        double frozenY = 0;

        double smoothX = -1000;
        double smoothY = -1000;
        double anchorX = -1000;
        double anchorY = -1000;
        double prevMouseX = -1000;
        double prevMouseY = -1000;

        double smoothedSpeed = 0.0;
        double stillTimer = 0.0;
        double formation = 0.0;
        double fadeAlpha = 0.0;

        DustLogo(String name, List<LogoPoint> points, Random rng, double hitRadius) {
            this.name = name;
            this.hitRadius = hitRadius;
            this.particles = new ArrayList<>(points.size());
            for (LogoPoint pt : points) {
                this.particles.add(new Particle(pt, rng));
            }
        }

        void blossom(double startX, double startY, Random rng) {
            isEnabled = true;
            isFrozen = false;
            smoothX = startX;
            smoothY = startY;
            anchorX = startX;
            anchorY = startY;
            prevMouseX = startX;
            prevMouseY = startY;
            fadeAlpha = 1.0;
            formation = 0.0;
            stillTimer = 0.0;
            smoothedSpeed = 0.0;

            for (Particle p : particles) {
                p.x = startX;
                p.y = startY;
                double angle = rng.nextDouble() * Math.PI * 2.0;
                double speed = 2.0 + rng.nextDouble() * 5.5;
                p.vx = Math.cos(angle) * speed;
                p.vy = Math.sin(angle) * speed;
            }
        }

        void freeze(double x, double y) {
            isFrozen = true;
            frozenX = x;
            frozenY = y;
            anchorX = x;
            anchorY = y;
            smoothX = x;
            smoothY = y;
            prevMouseX = x;
            prevMouseY = y;
            formation = 1.0;
            fadeAlpha = 1.0;
            for (Particle p : particles) {
                p.vx *= 0.20;
                p.vy *= 0.20;
            }
        }

        void unfreeze(double currentMouseX, double currentMouseY, Random rng) {
            isFrozen = false;
            smoothX = currentMouseX;
            smoothY = currentMouseY;
            anchorX = currentMouseX;
            anchorY = currentMouseY;
            prevMouseX = currentMouseX;
            prevMouseY = currentMouseY;
            stillTimer = 0.0;
            smoothedSpeed = 0.0;
            fadeAlpha = 1.0;
            formation = 1.0;

            // Subtle tactile ripple pulse when unfreezing
            for (Particle p : particles) {
                p.vx += (rng.nextDouble() - 0.5) * 2.4;
                p.vy += (rng.nextDouble() - 0.5) * 2.4;
            }
        }

        boolean containsPoint(double x, double y) {
            if (!isEnabled) return false;
            if (isFrozen) {
                return Math.hypot(x - frozenX, y - frozenY) <= hitRadius;
            } else {
                return Math.hypot(x - anchorX, y - anchorY) <= hitRadius;
            }
        }

        void update(double mouseX, double mouseY, double dt, boolean isMouseInside, double timeSec, Random rng) {
            if (!isEnabled) return;

            if (isFrozen) {
                // Firmly hold at frozen coordinates
                anchorX = frozenX;
                anchorY = frozenY;
                smoothX = frozenX;
                smoothY = frozenY;
                fadeAlpha = 1.0;
                formation = 1.0;
            } else {
                if (isMouseInside) {
                    fadeAlpha = Math.min(1.0, fadeAlpha + dt * 3.5);

                    double dist = Math.hypot(mouseX - prevMouseX, mouseY - prevMouseY);
                    smoothedSpeed = smoothedSpeed * 0.76 + dist * 0.24;
                    prevMouseX = mouseX;
                    prevMouseY = mouseY;

                    smoothX += (mouseX - smoothX) * Math.min(1.0, dt * 10.0);
                    smoothY += (mouseY - smoothY) * Math.min(1.0, dt * 10.0);

                    if (smoothedSpeed < 2.4) {
                        stillTimer += dt;
                    } else {
                        stillTimer = Math.max(0, stillTimer - dt * 2.8);
                    }

                    if (stillTimer > 0.20) {
                        formation = Math.min(1.0, formation + (1.0 - formation) * (dt * 3.6));
                        anchorX += (mouseX - anchorX) * Math.min(1.0, dt * 8.0);
                        anchorY += (mouseY - anchorY) * Math.min(1.0, dt * 8.0);
                    } else {
                        formation = Math.max(0.0, formation - formation * (dt * 4.8));
                        anchorX += (mouseX - anchorX) * Math.min(1.0, dt * 4.0);
                        anchorY += (mouseY - anchorY) * Math.min(1.0, dt * 4.0);
                    }
                } else {
                    fadeAlpha = Math.max(0.0, fadeAlpha - dt * 2.4);
                    formation = Math.max(0.0, formation - dt * 3.5);
                }
            }

            if (fadeAlpha <= 0.002) return;

            double f = formation;

            for (Particle p : particles) {
                double trailX = smoothX + p.trailOffsetX;
                double trailY = smoothY + p.trailOffsetY;

                double logoX = anchorX + p.targetDx;
                double logoY = anchorY + p.targetDy;

                double targetX = trailX * (1.0 - f) + logoX * f;
                double targetY = trailY * (1.0 - f) + logoY * f;

                // Micro-motion only when moving freely in trail mode. When formed or frozen, ZERO jitter for razor-sharp clarity!
                if (!isFrozen && f < 0.90) {
                    double microAmp = 0.5 * (1.0 - f);
                    targetX += Math.cos(timeSec * 2.2 + p.phase) * microAmp;
                    targetY += Math.sin(timeSec * 2.2 + p.phase) * microAmp;
                }

                double fx = (targetX - p.x) * p.spring;
                double fy = (targetY - p.y) * p.spring;
                p.vx = (p.vx + fx) * p.damping;
                p.vy = (p.vy + fy) * p.damping;

                if (isFrozen) {
                    // Firmly lock to target position to prevent any vibration blur
                    p.x += (targetX - p.x) * 0.35;
                    p.y += (targetY - p.y) * 0.35;
                    p.vx *= 0.2;
                    p.vy *= 0.2;
                } else {
                    p.x += p.vx;
                    p.y += p.vy;
                }
            }
        }

        void render(GraphicsContext gc, double timeSec) {
            if (!isEnabled || fadeAlpha <= 0.002) return;

            double f = formation;
            double globalFade = fadeAlpha;

            double cycleTime = timeSec % 3.6;
            double beamU = (cycleTime < 1.4) ? (-180.0 + (cycleTime / 1.4) * 360.0) : 9999.0;

            for (Particle p : particles) {
                double alpha = p.baseAlpha * globalFade * (0.65 + 0.35 * f);
                if (alpha <= 0.01) continue;

                double beam = 0.0;
                if (f > 0.35 && beamU < 5000.0) {
                    double u = (p.targetDx * 0.82 + p.targetDy * 0.57);
                    double distToBeam = Math.abs(u - beamU);
                    if (distToBeam < 36.0) {
                        double cosFactor = Math.cos((distToBeam / 36.0) * (Math.PI / 2.0));
                        beam = cosFactor * cosFactor * f;
                    }
                }

                double shimmer = 0.85 + 0.15 * Math.sin(timeSec * 3.6 + p.phase);

                // 1. Sharp Chromatic Body
                int bodyR = Math.min(255, (int) (p.r + beam * 55));
                int bodyG = Math.min(255, (int) (p.g + beam * 55));
                int bodyB = Math.min(255, (int) (p.b + beam * 55));
                gc.setFill(Color.rgb(bodyR, bodyG, bodyB, alpha * 0.98));
                gc.fillOval(p.x - p.radius, p.y - p.radius, p.radius * 2, p.radius * 2);

                // 2. Razor-Sharp 3D Specular Highlight
                double specR = Math.max(0.30, p.radius * 0.28);
                double specX = p.x - p.radius * 0.26;
                double specY = p.y - p.radius * 0.26;
                double specAlpha = alpha * (0.60 + 0.40 * beam) * shimmer;
                gc.setFill(Color.rgb(255, 255, 255, specAlpha));
                gc.fillOval(specX - specR, specY - specR, specR * 2, specR * 2);

                // 3. Crisp Diamond Sparkle Flare
                if (p.sparkSeed < 0.025 && f > 0.45 && Math.sin(timeSec * 4.0 + p.phase * 3.0) > 0.80) {
                    double sparkFrac = (Math.sin(timeSec * 4.0 + p.phase * 3.0) - 0.80) / 0.20;
                    double sparkAlpha = alpha * sparkFrac * 0.92;
                    gc.setStroke(Color.rgb(255, 255, 255, sparkAlpha));
                    gc.setLineWidth(1.0);
                    double len = 4.0;
                    gc.strokeLine(p.x - len, p.y, p.x + len, p.y);
                    gc.strokeLine(p.x, p.y - len, p.x, p.y + len);
                }
            }
        }
    }

    private List<LogoPoint> sampleCore2webPoints() {
        List<LogoPoint> points = new ArrayList<>();
        for (String path : LOGO_ASSET_PATHS) {
            try (InputStream is = getClass().getResourceAsStream(path)) {
                if (is != null) {
                    Image img = new Image(is);
                    PixelReader reader = img.getPixelReader();
                    if (reader != null) {
                        double w = img.getWidth();
                        double h = img.getHeight();
                        if (w > 20 && h > 20) {
                            double centerX = w / 2.0;
                            double centerY = h / 2.0;
                            double halfSize = Math.max(w, h) / 2.0;

                            int step = 6;

                            for (int y = 0; y < (int) h; y += step) {
                                for (int x = 0; x < (int) w; x += step) {
                                    int argb = reader.getArgb(x, y);
                                    int a = (argb >> 24) & 0xFF;
                                    int r = (argb >> 16) & 0xFF;
                                    int g = (argb >> 8) & 0xFF;
                                    int b = argb & 0xFF;
                                    int maxBr = Math.max(r, Math.max(g, b));

                                    // Filter out transparent and black background pixels
                                    if (a > 45 && maxBr > 36) {
                                        double dx = ((x - centerX) / halfSize) * (CORE2WEB_TARGET_SIZE / 2.0);
                                        double dy = ((y - centerY) / halfSize) * (CORE2WEB_TARGET_SIZE / 2.0);

                                        int glowR = Math.min(255, (int) (r * 1.15 + 10));
                                        int glowG = Math.min(255, (int) (g * 1.15 + 10));
                                        int glowB = Math.min(255, (int) (b * 1.15 + 10));

                                        points.add(new LogoPoint(dx, dy, glowR, glowG, glowB));
                                    }
                                }
                            }

                            if (points.size() >= 100) {
                                break;
                            }
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }

        if (points.isEmpty()) {
            for (int i = 0; i < 450; i++) {
                double angle = i * 0.08;
                double rad = 22 + (i % 45);
                double dx = Math.cos(angle) * rad;
                double dy = Math.sin(angle) * rad;
                points.add(new LogoPoint(dx, dy, 41, 198, 216));
            }
        }
        return points;
    }

    private List<LogoPoint> sampleKurukshetraPoints() {
        List<LogoPoint> points = new ArrayList<>();
        for (String path : KURUKSHETRA_ASSET_PATHS) {
            try (InputStream is = getClass().getResourceAsStream(path)) {
                if (is != null) {
                    Image img = new Image(is);
                    PixelReader reader = img.getPixelReader();
                    if (reader != null) {
                        double w = img.getWidth();
                        double h = img.getHeight();
                        if (w > 20 && h > 20) {
                            double centerX = w / 2.0;
                            double centerY = h / 2.0;
                            double halfSize = Math.max(w, h) / 2.0;

                            // Step 5 gives dense, crisp ~4400 points matching Core2web
                            int step = 5;

                            for (int y = 0; y < (int) h; y += step) {
                                for (int x = 0; x < (int) w; x += step) {
                                    int argb = reader.getArgb(x, y);
                                    int a = (argb >> 24) & 0xFF;
                                    int r = (argb >> 16) & 0xFF;
                                    int g = (argb >> 8) & 0xFF;
                                    int b = argb & 0xFF;

                                    // Filter out semi-transparent edge antialiasing for razor-sharp clarity
                                    if (a > 115) {
                                        double dx = ((x - centerX) / halfSize) * (KURUKSHETRA_TARGET_SIZE / 2.0);
                                        double dy = ((y - centerY) / halfSize) * (KURUKSHETRA_TARGET_SIZE / 2.0);

                                        // High-contrast radiant gold & vibrant peacock feather grading
                                        int glowR, glowG, glowB;
                                        if (b > r + 15) {
                                            // Peacock royal cyan / blue feather
                                            glowR = Math.min(255, (int) (r * 1.15 + 15));
                                            glowG = Math.min(255, (int) (g * 1.35 + 30));
                                            glowB = Math.min(255, (int) (b * 1.45 + 45));
                                        } else {
                                            // Radiant high-contrast gold/amber (pops crisply on navy #0B203B)
                                            glowR = Math.min(255, (int) (r * 1.35 + 35));
                                            glowG = Math.min(255, (int) (g * 1.30 + 22));
                                            glowB = Math.min(255, (int) (b * 1.15 + 10));
                                        }

                                        points.add(new LogoPoint(dx, dy, glowR, glowG, glowB));
                                    }
                                }
                            }

                            if (points.size() >= 100) {
                                break;
                            }
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }

        if (points.isEmpty()) {
            for (int i = 0; i < 450; i++) {
                double angle = i * 0.08;
                double rad = 25 + (i % 50);
                double dx = Math.cos(angle) * rad;
                double dy = Math.sin(angle) * rad;
                points.add(new LogoPoint(dx, dy, 255, 200, 70));
            }
        }
        return points;
    }

    private static boolean isNodeOrDescendant(Object target, Node node) {
        if (target == null || node == null) return false;
        if (target == node) return true;
        if (target instanceof Node targetNode) {
            Node parent = targetNode;
            while (parent != null) {
                if (parent == node) return true;
                parent = parent.getParent();
            }
        }
        return false;
    }

    private void setupParticleSystem(StackPane root, ResizableCanvas canvas, ProfileCard shashiCard) {
        Random rng = new Random();
        DustLogo core2web = new DustLogo("Core2web", sampleCore2webPoints(), rng, 130.0);
        DustLogo kurukshetra = new DustLogo("Kurukshetra", sampleKurukshetraPoints(), rng, 145.0);

        final DustLogo[] activeLogo = new DustLogo[]{null};
        final double[] mousePos = new double[]{-1000, -1000};
        final boolean[] isMouseInside = new boolean[]{false};

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 1. Trigger Core2web dust logo when clicking Shashi Sir name
        shashiCard.nameLabel.setCursor(Cursor.HAND);
        shashiCard.nameLabel.setOnMouseClicked(e -> {
            ScaleTransition bounce = new ScaleTransition(Duration.millis(140), shashiCard.nameLabel);
            bounce.setToX(1.08);
            bounce.setToY(1.08);
            bounce.setAutoReverse(true);
            bounce.setCycleCount(2);
            bounce.play();

            double startX = root.getWidth() / 2.0;
            double startY = 220.0;
            try {
                var bounds = shashiCard.nameLabel.localToScene(shashiCard.nameLabel.getBoundsInLocal());
                if (bounds != null && root.getScene() != null) {
                    var pt = root.sceneToLocal(bounds.getCenterX(), bounds.getCenterY());
                    startX = pt.getX();
                    startY = pt.getY();
                }
            } catch (Exception ignored) {
            }

            // If Kurukshetra is currently tracking mouse, auto-freeze it where it is
            if (kurukshetra.isEnabled && !kurukshetra.isFrozen) {
                kurukshetra.freeze(kurukshetra.anchorX, kurukshetra.anchorY);
            }

            mousePos[0] = startX;
            mousePos[1] = startY;
            isMouseInside[0] = true;
            core2web.blossom(startX, startY, rng);
            activeLogo[0] = core2web;
        });

        // 2. Trigger Kurukshetra dust logo when clicking Teacher badge
        shashiCard.positionBox.setOnMouseClicked(e -> {
            ScaleTransition bounce = new ScaleTransition(Duration.millis(140), shashiCard.positionBox);
            bounce.setToX(1.08);
            bounce.setToY(1.08);
            bounce.setAutoReverse(true);
            bounce.setCycleCount(2);
            bounce.play();

            double startX = root.getWidth() / 2.0;
            double startY = 260.0;
            try {
                var bounds = shashiCard.positionBox.localToScene(shashiCard.positionBox.getBoundsInLocal());
                if (bounds != null && root.getScene() != null) {
                    var pt = root.sceneToLocal(bounds.getCenterX(), bounds.getCenterY());
                    startX = pt.getX();
                    startY = pt.getY();
                }
            } catch (Exception ignored) {
            }

            // If Core2web is currently tracking mouse, auto-freeze it where it is
            if (core2web.isEnabled && !core2web.isFrozen) {
                core2web.freeze(core2web.anchorX, core2web.anchorY);
            }

            mousePos[0] = startX;
            mousePos[1] = startY;
            isMouseInside[0] = true;
            kurukshetra.blossom(startX, startY, rng);
            activeLogo[0] = kurukshetra;
        });

        // 3. Mouse Tracking & Hover Indicator Handling
        root.setOnMouseMoved(e -> {
            mousePos[0] = e.getX();
            mousePos[1] = e.getY();
            isMouseInside[0] = true;

            // Hover indicator: show HAND cursor when hovering over any frozen logo
            boolean hoveringFrozen = false;
            if (core2web.isEnabled && core2web.isFrozen && core2web.containsPoint(e.getX(), e.getY())) {
                hoveringFrozen = true;
            } else if (kurukshetra.isEnabled && kurukshetra.isFrozen && kurukshetra.containsPoint(e.getX(), e.getY())) {
                hoveringFrozen = true;
            }

            if (hoveringFrozen) {
                root.setCursor(Cursor.HAND);
            } else if (activeLogo[0] != null && activeLogo[0].isEnabled && !activeLogo[0].isFrozen) {
                root.setCursor(Cursor.HAND);
            } else {
                root.setCursor(Cursor.DEFAULT);
            }
        });

        root.setOnMouseDragged(e -> {
            mousePos[0] = e.getX();
            mousePos[1] = e.getY();
            isMouseInside[0] = true;
        });

        root.setOnMouseEntered(e -> {
            mousePos[0] = e.getX();
            mousePos[1] = e.getY();
            isMouseInside[0] = true;
        });

        root.setOnMouseExited(e -> {
            isMouseInside[0] = false;
            root.setCursor(Cursor.DEFAULT);
        });

        // 4. Global Click Filter: Freeze moving logo & Unfreeze frozen logo
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            // Let dedicated click handlers on triggers run unimpeded
            if (isNodeOrDescendant(e.getTarget(), shashiCard.nameLabel) ||
                isNodeOrDescendant(e.getTarget(), shashiCard.positionBox)) {
                return;
            }

            double clickX = e.getX();
            double clickY = e.getY();

            // Priority A: Clicked on a FROZEN logo to UNFREEZE it
            DustLogo clickedFrozen = null;
            if (kurukshetra.isEnabled && kurukshetra.isFrozen && kurukshetra.containsPoint(clickX, clickY)) {
                clickedFrozen = kurukshetra;
            } else if (core2web.isEnabled && core2web.isFrozen && core2web.containsPoint(clickX, clickY)) {
                clickedFrozen = core2web;
            }

            if (clickedFrozen != null) {
                // If another logo was tracking the mouse, auto-freeze it where it is
                if (activeLogo[0] != null && activeLogo[0] != clickedFrozen && activeLogo[0].isEnabled && !activeLogo[0].isFrozen) {
                    activeLogo[0].freeze(activeLogo[0].anchorX, activeLogo[0].anchorY);
                }
                clickedFrozen.unfreeze(clickX, clickY, rng);
                activeLogo[0] = clickedFrozen;
                e.consume();
                return;
            }

            // Priority B: Clicked while an active logo is moving -> FREEZE it at this position
            if (activeLogo[0] != null && activeLogo[0].isEnabled && !activeLogo[0].isFrozen) {
                double fx = (activeLogo[0].formation > 0.5) ? activeLogo[0].anchorX : clickX;
                double fy = (activeLogo[0].formation > 0.5) ? activeLogo[0].anchorY : clickY;
                activeLogo[0].freeze(fx, fy);
                activeLogo[0] = null;
                e.consume();
                return;
            }
        });

        // 5. Unified Animation Timer
        AnimationTimer timer = new AnimationTimer() {
            private long lastNano = -1;

            @Override
            public void handle(long now) {
                if (!core2web.isEnabled && !kurukshetra.isEnabled) {
                    return;
                }
                if (lastNano < 0) {
                    lastNano = now;
                    return;
                }
                double dt = Math.min(0.04, (now - lastNano) * 1e-9);
                lastNano = now;

                double w = canvas.getWidth();
                double h = canvas.getHeight();
                if (w <= 10 || h <= 10) return;

                double timeSec = now * 1e-9;

                core2web.update(mousePos[0], mousePos[1], dt, isMouseInside[0], timeSec, rng);
                kurukshetra.update(mousePos[0], mousePos[1], dt, isMouseInside[0], timeSec, rng);

                gc.clearRect(0, 0, w, h);
                core2web.render(gc, timeSec);
                kurukshetra.render(gc, timeSec);
            }
        };
        timer.start();
    }

    private static class ResizableCanvas extends Canvas {
        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double minWidth(double height) {
            return 100;
        }

        @Override
        public double maxWidth(double height) {
            return Double.MAX_VALUE;
        }

        @Override
        public double prefWidth(double height) {
            return getWidth();
        }

        @Override
        public double minHeight(double width) {
            return 100;
        }

        @Override
        public double maxHeight(double width) {
            return Double.MAX_VALUE;
        }

        @Override
        public double prefHeight(double width) {
            return getHeight();
        }
    }

    private void armScrollReveal(ProfileCard card, int delay) {
        Node node = card.node;
        node.setOpacity(0);
        node.setTranslateY(70);
        node.setScaleX(0.96);
        node.setScaleY(0.96);

        AnimationTimer watcher = new AnimationTimer() {
            private boolean played = false;

            @Override
            public void handle(long now) {
                if (played || node.getScene() == null) return;

                var bounds = node.localToScene(node.getBoundsInLocal());
                double sceneHeight = node.getScene().getHeight();
                boolean visible = bounds.getMaxY() > 50 && bounds.getMinY() < sceneHeight - 40;

                if (visible) {
                    played = true;
                    stop();
                    PauseTransition pause = new PauseTransition(Duration.millis(delay));
                    pause.setOnFinished(e -> {
                        playEntrance(node);
                        // Trigger typewriter description reveal when scrolled into view
                        PauseTransition typeDelay = new PauseTransition(Duration.millis(250));
                        typeDelay.setOnFinished(ev -> card.revealDescription());
                        typeDelay.play();
                    });
                    pause.play();
                }
            }
        };
        watcher.start();
    }

    private void playEntrance(Node node) {
        FadeTransition fade = new FadeTransition(Duration.millis(750), node);
        fade.setToValue(1);

        TranslateTransition move = new TranslateTransition(Duration.millis(850), node);
        move.setToY(0);

        ScaleTransition scale = new ScaleTransition(Duration.millis(850), node);
        scale.setToX(1.0);
        scale.setToY(1.0);

        new ParallelTransition(fade, move, scale).play();
    }

    private static class ProfileCard {
        private final VBox node = new VBox(10);
        private final Label descriptionLabel = new Label();
        final Label nameLabel;
        final StackPane positionBox;
        final Label positionLabel;
        private final String description;
        private Timeline typingTimeline;
        private int typingIndex;

        ProfileCard(String name, String position, String imagePath, String description,
                    double imgW, double imgH, double nameSize, double maxDescW) {

            this.description = description;
            this.nameLabel = new Label(name);
            this.positionBox = new StackPane();
            this.positionLabel = new Label(position.toUpperCase());

            node.setPrefWidth(maxDescW + 30);
            node.setMaxWidth(maxDescW + 30);
            node.setAlignment(Pos.TOP_CENTER);
            node.setPadding(new Insets(6, 12, 12, 12));

            // Transparent image container with subtle natural grounded glow (no ugly card, no circular halo)
            StackPane imagePane = new StackPane();
            imagePane.setPrefSize(imgW + 20, imgH + 20);
            imagePane.setAlignment(Pos.CENTER);

            // Subtle floor reflection glow so the transparent portrait feels grounded
            Ellipse groundGlow = new Ellipse(imgW * 0.42, 12);
            groundGlow.setFill(Color.web(CYAN, 0.08));
            groundGlow.setStroke(Color.web(CYAN, 0.15));
            groundGlow.setStrokeWidth(1.0);
            StackPane.setAlignment(groundGlow, Pos.BOTTOM_CENTER);
            StackPane.setMargin(groundGlow, new Insets(0, 0, 4, 0));

            ImageView imageView = loadImage(imagePath);
            if (imageView != null) {
                imageView.setFitWidth(imgW);
                imageView.setFitHeight(imgH);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                imagePane.getChildren().addAll(groundGlow, imageView);
            } else {
                Label placeholder = new Label(name.toUpperCase());
                placeholder.setStyle(FONT + "-fx-text-fill:" + MUTED + ";-fx-font-size:12px;-fx-font-weight:bold;");
                imagePane.getChildren().addAll(groundGlow, placeholder);
            }

            // Name Label (Shashi Sir has interactive cursor and hover glow)
            if (name.equals("Shashi Sir")) {
                nameLabel.setStyle(FONT +
                        "-fx-text-fill:" + PRIMARY + ";" +
                        "-fx-font-size:" + nameSize + "px;" +
                        "-fx-font-weight:800;" +
                        "-fx-cursor: hand;"
                );
                nameLabel.setOnMouseEntered(ev -> {
                    nameLabel.setStyle(FONT +
                            "-fx-text-fill:" + CYAN + ";" +
                            "-fx-font-size:" + nameSize + "px;" +
                            "-fx-font-weight:800;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(41, 198, 216, 0.55), 12, 0, 0, 0);"
                    );
                });
                nameLabel.setOnMouseExited(ev -> {
                    nameLabel.setStyle(FONT +
                            "-fx-text-fill:" + PRIMARY + ";" +
                            "-fx-font-size:" + nameSize + "px;" +
                            "-fx-font-weight:800;" +
                            "-fx-cursor: hand;"
                    );
                });
            } else {
                nameLabel.setStyle(FONT +
                        "-fx-text-fill:" + PRIMARY + ";" +
                        "-fx-font-size:" + nameSize + "px;" +
                        "-fx-font-weight:800;"
                );
            }

            // Position Box with styling
            positionBox.setAlignment(Pos.CENTER);
            positionBox.setPadding(new Insets(5, 16, 6, 16));

            if (position.equalsIgnoreCase("Teacher")) {
                // Interactive Kurukshetra golden trigger badge
                positionBox.setCursor(Cursor.HAND);
                positionBox.setStyle(
                        FONT +
                        "-fx-background-color: linear-gradient(to bottom, rgba(225, 123, 50, 0.10), rgba(225, 123, 50, 0.22));" +
                        "-fx-border-color: rgba(225, 123, 50, 0.50);" +
                        "-fx-border-width: 0 0 2 0;" +
                        "-fx-border-radius: 6px;" +
                        "-fx-background-radius: 6px;"
                );
                positionLabel.setStyle(FONT +
                        "-fx-text-fill: #E17B32;" +
                        "-fx-font-size:11px;" +
                        "-fx-font-weight:800;" +
                        "-fx-letter-spacing:1.3px;" +
                        "-fx-cursor: hand;"
                );

                positionBox.setOnMouseEntered(ev -> {
                    positionBox.setStyle(
                            FONT +
                            "-fx-background-color: linear-gradient(to bottom, rgba(225, 123, 50, 0.22), rgba(225, 123, 50, 0.38));" +
                            "-fx-border-color: #E17B32;" +
                            "-fx-border-width: 0 0 2 0;" +
                            "-fx-border-radius: 6px;" +
                            "-fx-background-radius: 6px;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(225, 123, 50, 0.65), 14, 0, 0, 0);"
                    );
                    positionLabel.setStyle(FONT +
                            "-fx-text-fill: #FFAA55;" +
                            "-fx-font-size:11px;" +
                            "-fx-font-weight:800;" +
                            "-fx-letter-spacing:1.3px;" +
                            "-fx-cursor: hand;"
                    );
                });
                positionBox.setOnMouseExited(ev -> {
                    positionBox.setStyle(
                            FONT +
                            "-fx-background-color: linear-gradient(to bottom, rgba(225, 123, 50, 0.10), rgba(225, 123, 50, 0.22));" +
                            "-fx-border-color: rgba(225, 123, 50, 0.50);" +
                            "-fx-border-width: 0 0 2 0;" +
                            "-fx-border-radius: 6px;" +
                            "-fx-background-radius: 6px;"
                    );
                    positionLabel.setStyle(FONT +
                            "-fx-text-fill: #E17B32;" +
                            "-fx-font-size:11px;" +
                            "-fx-font-weight:800;" +
                            "-fx-letter-spacing:1.3px;" +
                            "-fx-cursor: hand;"
                    );
                });
            } else {
                positionBox.setStyle(
                        FONT +
                        "-fx-background-color: linear-gradient(to bottom, rgba(41, 198, 216, 0.06), rgba(41, 198, 216, 0.14));" +
                        "-fx-border-color: rgba(41, 198, 216, 0.36);" +
                        "-fx-border-width: 0 0 2 0;" +
                        "-fx-border-radius: 6px;" +
                        "-fx-background-radius: 6px;"
                );
                positionLabel.setStyle(FONT +
                        "-fx-text-fill:" + CYAN + ";" +
                        "-fx-font-size:11px;" +
                        "-fx-font-weight:800;" +
                        "-fx-letter-spacing:1.3px;"
                );
            }
            positionBox.getChildren().add(positionLabel);

            // Description Label (Hidden initially, appears via typewriter effect on hover)
            descriptionLabel.setWrapText(true);
            descriptionLabel.setMaxWidth(maxDescW);
            descriptionLabel.setMinHeight(64);
            descriptionLabel.setAlignment(Pos.TOP_CENTER);
            descriptionLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            descriptionLabel.setStyle(FONT +
                    "-fx-text-fill:" + SECONDARY + ";" +
                    "-fx-font-size:13.5px;" +
                    "-fx-line-spacing:3px;"
            );
            descriptionLabel.setText(""); // Initially empty

            node.getChildren().addAll(
                    imagePane,
                    nameLabel,
                    positionBox,
                    descriptionLabel
            );

            // Hover interactions: Interactive card lift (description does NOT disappear)
            node.setOnMouseEntered(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
                st.setToX(1.025);
                st.setToY(1.025);
                st.play();
                if (!revealed) {
                    revealDescription();
                }
            });

            node.setOnMouseExited(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
                st.setToX(1.0);
                st.setToY(1.0);
                st.play();
                // Description remains permanently visible after scrolling
            });
        }

        private boolean revealed = false;

        void revealDescription() {
            if (revealed) return;
            revealed = true;
            stopTyping();
            typingIndex = 0;
            descriptionLabel.setText("");

            typingTimeline = new Timeline(
                    new KeyFrame(Duration.millis(14), e -> {
                        if (typingIndex < description.length()) {
                            typingIndex++;
                            descriptionLabel.setText(description.substring(0, typingIndex));
                        } else {
                            typingTimeline.stop();
                        }
                    })
            );
            typingTimeline.setCycleCount(Timeline.INDEFINITE);
            typingTimeline.play();
        }

        private void stopTyping() {
            if (typingTimeline != null) {
                typingTimeline.stop();
            }
        }

        private ImageView loadImage(String path) {
            try {
                var url = PeoplePyramidSection.class.getResource(path);
                if (url != null) {
                    return new ImageView(new Image(url.toExternalForm(), true));
                }
            } catch (Exception ignored) {
            }
            return null;
        }
    }
}