// package com.kurukshetra.view.nurse;

// import javafx.animation.AnimationTimer;
// import javafx.animation.KeyFrame;
// import javafx.animation.Timeline;
// import javafx.application.Platform;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.canvas.Canvas;
// import javafx.scene.canvas.GraphicsContext;
// import javafx.scene.control.Button;
// import javafx.scene.control.ComboBox;
// import javafx.scene.control.Label;
// import javafx.scene.effect.DropShadow;
// import javafx.scene.effect.Glow;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import javafx.scene.shape.Circle;
// import javafx.scene.shape.Rectangle;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.scene.text.Text;
// import javafx.util.Duration;

// import java.awt.Toolkit;
// import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;
// import java.util.Random;
// import java.util.function.Consumer;

// /**
//  * Authentic Hospital ICU Bedside Patient Monitor Component.
//  * Themed to match the LifeLink Nurse Dashboard (Primary Pink #E67593 accents).
//  * Fully responsive width (no horizontal scrollbar) with ResizableCanvas.
//  */
// public class PatientIcuMonitorView extends VBox {

//     // LifeLink Nurse Theme Color Accents
//     public static final String NURSE_PINK = "#E67593";
//     public static final String NURSE_PINK_DARK = "#D85375";
//     public static final String NURSE_LIGHT_PINK = "#FDF0F4";
//     public static final String NURSE_BORDER = "#EEDEE3";

//     // Clinical Monitor Hardware Palette
//     private static final String CHASSIS_BG = "#0B111E";
//     private static final String SCREEN_BG = "#070B14";
//     private static final String BEZEL_TOP_BG = "#0E1526";
//     private static final String BEZEL_BORDER = "#E67593";

//     // Channel Clinical Colors (Harmonized with Nurse Palette)
//     public static final String COLOR_ECG = "#00FF66";     // Vibrant Neon Green (Heart Rate)
//     public static final String COLOR_ABP = "#E67593";     // LifeLink Medical Pink (Blood Pressure)
//     public static final String COLOR_PLETH = "#00E5FF";   // Medical Aqua Cyan (Pulse Rate / SpO2)
//     public static final String COLOR_TEMP = "#FFD600";    // Warm Golden Amber (Temperature)

//     // Patient and Clinical State
//     public static class VitalsSnapshot {
//         public int heartRate;
//         public int systolic;
//         public int diastolic;
//         public int pulseRate;
//         public int spo2;
//         public double temperature;
//         public String rhythm;

//         public VitalsSnapshot(int hr, int sys, int dia, int pr, int spo2, double temp, String rhythm) {
//             this.heartRate = hr;
//             this.systolic = sys;
//             this.diastolic = dia;
//             this.pulseRate = pr;
//             this.spo2 = spo2;
//             this.temperature = temp;
//             this.rhythm = rhythm;
//         }
//     }

//     private final VitalsSnapshot currentVitals = new VitalsSnapshot(78, 120, 80, 78, 98, 37.0, "NSR");
//     private Consumer<VitalsSnapshot> onVitalsUpdateListener;

//     // Resizable Canvas for 100% Horizontal Fit without scroll
//     private static class ResizableCanvas extends Canvas {
//         @Override
//         public boolean isResizable() {
//             return true;
//         }

//         @Override
//         public double minWidth(double height) {
//             return 80;
//         }

//         @Override
//         public double maxWidth(double height) {
//             return Double.MAX_VALUE;
//         }

//         @Override
//         public double prefWidth(double height) {
//             return getWidth();
//         }

//         @Override
//         public double minHeight(double width) {
//             return 120;
//         }

//         @Override
//         public double maxHeight(double width) {
//             return Double.MAX_VALUE;
//         }

//         @Override
//         public double prefHeight(double width) {
//             return getHeight();
//         }
//     }

//     private ResizableCanvas waveformCanvas;
//     private GraphicsContext gc;
//     private AnimationTimer sweepTimer;
//     private Timeline telemetryTicker;
//     private Timeline clockTicker;

//     private double canvasWidth = 600;
//     private double canvasHeight = 270;
//     private double sweepX = 0;
//     private double sweepSpeed = 2.4;
//     private static final double ERASE_BAR_WIDTH = 20.0;

//     // Waveform tracking
//     private double lastEcgY = -1;
//     private double lastAbpY = -1;
//     private double lastPlethY = -1;
//     private double lastTempY = -1;
//     private double phaseCounter = 0;

//     // Simulation & State controls
//     private boolean isFrozen = false;
//     private boolean isAudioMuted = true;
//     private boolean isAlarmSilenced = false;
//     private int alarmSilenceSeconds = 0;
//     private final Random random = new Random();

//     // UI Elements on Monitor
//     private Label clockLabel;
//     private Label alarmBanner;
//     private Circle alarmLed;
//     private Text ecgBeatHeart;
//     private Label ecgValLabel;
//     private Label abpValLabel;
//     private Label abpMapLabel;
//     private Label plethValLabel;
//     private Label spo2ValLabel;
//     private Label tempValLabel;
//     private Label tempFahrLabel;
//     private HBox pulseBarContainer;
//     private Rectangle[] pulseBars;
//     private Button freezeBtn;
//     private Button soundBtn;
//     private Button silenceBtn;
//     private ComboBox<String> rhythmSelector;

//     public PatientIcuMonitorView(String patientId, String hospitalDestination, Consumer<VitalsSnapshot> listener) {
//         this.onVitalsUpdateListener = listener;

//         setSpacing(0);
//         setAlignment(Pos.TOP_CENTER);
//         setMinWidth(0);
//         setMaxWidth(Double.MAX_VALUE);
//         setStyle("-fx-background-color: " + CHASSIS_BG + "; -fx-background-radius: 16; -fx-border-color: " + BEZEL_BORDER + "; -fx-border-width: 1.5; -fx-border-radius: 16;");
//         setEffect(new DropShadow(14, 0, 3, Color.rgb(230, 117, 147, 0.14)));
//         VBox.setVgrow(this, Priority.NEVER);

//         // 1. Monitor Top Bezel Bar
//         HBox topBezel = buildTopBezel(patientId, hospitalDestination);

//         // 2. Main Screen Area (Resizable Canvas + Telemetry HUD)
//         HBox screenViewport = buildScreenViewport();

//         // 3. Monitor Bottom Membrane Soft-Keys Control Bar
//         HBox bottomSoftKeys = buildBottomSoftKeys();

//         getChildren().addAll(topBezel, screenViewport, bottomSoftKeys);

//         // Start animations and timers
//         initRenderingLoop();
//         initTelemetryFluctuations();
//         initClockTicker();

//         // Initial listener notification
//         notifyVitalsChanged();
//     }

//     // =========================================================================
//     // 1. TOP BEZEL STRIP
//     // =========================================================================
//     private HBox buildTopBezel(String patientId, String hospitalDestination) {
//         HBox bezel = new HBox(10);
//         bezel.setAlignment(Pos.CENTER_LEFT);
//         bezel.setPadding(new Insets(6, 14, 6, 14));
//         bezel.setMinWidth(0);
//         bezel.setStyle("-fx-background-color: " + BEZEL_TOP_BG + "; -fx-background-radius: 14 14 0 0; -fx-border-color: transparent transparent #1E293B transparent; -fx-border-width: 1;");

//         // Left: Telemetry Badge + Bed Info
//         HBox leftSec = new HBox(6);
//         leftSec.setAlignment(Pos.CENTER_LEFT);

//         Label brandBadge = new Label("💓 Telemetry");
//         brandBadge.setStyle("-fx-background-color: " + NURSE_LIGHT_PINK + "; -fx-text-fill: " + NURSE_PINK + "; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 2 6; -fx-background-radius: 6; -fx-border-color: " + NURSE_BORDER + "; -fx-border-radius: 6;");

//         alarmLed = new Circle(3.5, Color.web("#10B981"));
//         alarmLed.setEffect(new Glow(0.6));

//         Label bedInfo = new Label("Bed-04 • " + patientId);
//         bedInfo.setStyle("-fx-font-size: 10px; -fx-text-fill: #E2E8F0; -fx-font-weight: bold;");

//         leftSec.getChildren().addAll(brandBadge, alarmLed, bedInfo);

//         Region sp1 = new Region();
//         HBox.setHgrow(sp1, Priority.ALWAYS);

//         // Center: Master Alarm Status
//         alarmBanner = new Label("● NORMAL SINUS RHYTHM");
//         alarmBanner.setStyle("-fx-background-color: rgba(16, 185, 129, 0.18); -fx-text-fill: #34D399; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 10; -fx-background-radius: 10; -fx-border-color: rgba(16, 185, 129, 0.4); -fx-border-radius: 10;");

//         Region sp2 = new Region();
//         HBox.setHgrow(sp2, Priority.ALWAYS);

//         // Right: Mode + Clock
//         HBox rightSec = new HBox(6);
//         rightSec.setAlignment(Pos.CENTER_RIGHT);

//         Label filterLabel = new Label("LEAD II • 25mm/s");
//         filterLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #94A3B8; -fx-font-family: monospace;");

//         clockLabel = new Label("00:00:00 IST");
//         clockLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #F8FAFC; -fx-font-family: monospace; -fx-background-color: #172033; -fx-padding: 2 6; -fx-background-radius: 4; -fx-border-color: " + NURSE_PINK + "; -fx-border-radius: 4;");

//         rightSec.getChildren().addAll(filterLabel, clockLabel);

//         bezel.getChildren().addAll(leftSec, sp1, alarmBanner, sp2, rightSec);
//         return bezel;
//     }

//     // =========================================================================
//     // 2. MAIN SCREEN VIEWPORT (OSCILLOSCOPE CANVAS + TELEMETRY HUD)
//     // =========================================================================
//     private HBox buildScreenViewport() {
//         HBox viewport = new HBox(0);
//         viewport.setStyle("-fx-background-color: " + SCREEN_BG + ";");
//         viewport.setMinWidth(0);
//         HBox.setHgrow(viewport, Priority.ALWAYS);

//         // Left Container: Canvas with Medical ECG Grid
//         StackPane canvasContainer = new StackPane();
//         canvasContainer.setStyle("-fx-background-color: " + SCREEN_BG + ";");
//         canvasContainer.setMinWidth(0);
//         HBox.setHgrow(canvasContainer, Priority.ALWAYS);

//         waveformCanvas = new ResizableCanvas();
//         waveformCanvas.setHeight(canvasHeight);
//         gc = waveformCanvas.getGraphicsContext2D();

//         waveformCanvas.widthProperty().bind(canvasContainer.widthProperty());
//         waveformCanvas.widthProperty().addListener((obs, oldW, newW) -> {
//             if (newW.doubleValue() > 50) {
//                 canvasWidth = newW.doubleValue();
//                 redrawFullMedicalGrid();
//             }
//         });

//         canvasContainer.getChildren().add(waveformCanvas);

//         // Right Container: Dedicated ICU Digital Telemetry HUD (4 Channels)
//         VBox rightTelemetryHud = buildRightTelemetryHud();
//         rightTelemetryHud.setPrefWidth(190);
//         rightTelemetryHud.setMinWidth(180);
//         rightTelemetryHud.setMaxWidth(200);

//         viewport.getChildren().addAll(canvasContainer, rightTelemetryHud);
//         return viewport;
//     }

//     // =========================================================================
//     // 3. RIGHT TELEMETRY HUD STRIP
//     // =========================================================================
//     private VBox buildRightTelemetryHud() {
//         VBox hud = new VBox(0);
//         hud.setStyle("-fx-background-color: #070B14; -fx-border-color: transparent transparent transparent #1E293B; -fx-border-width: 1;");
//         hud.setAlignment(Pos.TOP_CENTER);
//         VBox.setVgrow(hud, Priority.ALWAYS);

//         // Channel 1: ECG HUD
//         VBox ecgBox = buildChannelHudBlock("ECG II", COLOR_ECG, () -> {
//             HBox row = new HBox(4);
//             row.setAlignment(Pos.BASELINE_LEFT);

//             ecgBeatHeart = new Text("♥");
//             ecgBeatHeart.setStyle("-fx-font-size: 14px; -fx-fill: " + COLOR_ECG + ";");
//             ecgBeatHeart.setEffect(new Glow(0.8));

//             ecgValLabel = new Label(String.valueOf(currentVitals.heartRate));
//             ecgValLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_ECG + "; -fx-font-family: 'Consolas', monospace;");

//             VBox unitBox = new VBox(0);
//             unitBox.setAlignment(Pos.BOTTOM_LEFT);
//             Label bpmLbl = new Label("BPM");
//             bpmLbl.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_ECG + ";");
//             Label limitsLbl = new Label("[50-120]");
//             limitsLbl.setStyle("-fx-font-size: 8px; -fx-text-fill: #64748B;");
//             unitBox.getChildren().addAll(bpmLbl, limitsLbl);

//             row.getChildren().addAll(ecgBeatHeart, ecgValLabel, unitBox);
//             return row;
//         }, "Normal Sinus • Lead II");

//         // Channel 2: Arterial Blood Pressure HUD (Nurse Pink Themed)
//         VBox abpBox = buildChannelHudBlock("ART / NIBP", COLOR_ABP, () -> {
//             HBox row = new HBox(4);
//             row.setAlignment(Pos.BASELINE_LEFT);

//             abpValLabel = new Label(currentVitals.systolic + "/" + currentVitals.diastolic);
//             abpValLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_ABP + "; -fx-font-family: 'Consolas', monospace;");

//             VBox unitBox = new VBox(0);
//             unitBox.setAlignment(Pos.BOTTOM_LEFT);
//             Label mmLbl = new Label("mmHg");
//             mmLbl.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_ABP + ";");
//             int mean = (int) Math.round((2.0 * currentVitals.diastolic + currentVitals.systolic) / 3.0);
//             abpMapLabel = new Label("(" + mean + ")");
//             abpMapLabel.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #FDA4AF;");
//             unitBox.getChildren().addAll(mmLbl, abpMapLabel);

//             row.getChildren().addAll(abpValLabel, unitBox);
//             return row;
//         }, "ABP Limits [90/60-140/90]");

//         // Channel 3: PLETH / SpO2 HUD
//         VBox plethBox = buildChannelHudBlock("PLETH / SpO₂", COLOR_PLETH, () -> {
//             HBox row = new HBox(6);
//             row.setAlignment(Pos.CENTER_LEFT);

//             VBox prCol = new VBox(0);
//             plethValLabel = new Label(String.valueOf(currentVitals.pulseRate));
//             plethValLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_PLETH + "; -fx-font-family: 'Consolas', monospace;");
//             Label prUnit = new Label("PR bpm");
//             prUnit.setStyle("-fx-font-size: 8px; -fx-text-fill: #64748B;");
//             prCol.getChildren().addAll(plethValLabel, prUnit);

//             VBox spo2Col = new VBox(0);
//             spo2ValLabel = new Label(currentVitals.spo2 + "%");
//             spo2ValLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_PLETH + "; -fx-font-family: 'Consolas', monospace;");
//             Label spo2Unit = new Label("SpO₂");
//             spo2Unit.setStyle("-fx-font-size: 8px; -fx-text-fill: #64748B;");
//             spo2Col.getChildren().addAll(spo2ValLabel, spo2Unit);

//             pulseBarContainer = buildPulseBarMeter();

//             row.getChildren().addAll(prCol, spo2Col, pulseBarContainer);
//             return row;
//         }, "PI 4.2% • Signal Strong");

//         // Channel 4: Temperature HUD
//         VBox tempBox = buildChannelHudBlock("TEMP 1", COLOR_TEMP, () -> {
//             HBox row = new HBox(5);
//             row.setAlignment(Pos.BASELINE_LEFT);

//             tempValLabel = new Label(String.format("%.1f", currentVitals.temperature));
//             tempValLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_TEMP + "; -fx-font-family: 'Consolas', monospace;");

//             VBox unitBox = new VBox(0);
//             unitBox.setAlignment(Pos.BOTTOM_LEFT);
//             Label cLbl = new Label("°C");
//             cLbl.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_TEMP + ";");
//             double fahr = (currentVitals.temperature * 9.0 / 5.0) + 32.0;
//             tempFahrLabel = new Label(String.format("%.1f°F", fahr));
//             tempFahrLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #FDE68A;");
//             unitBox.getChildren().addAll(cLbl, tempFahrLabel);

//             row.getChildren().addAll(tempValLabel, unitBox);
//             return row;
//         }, "Core • Normothermic");

//         VBox.setVgrow(ecgBox, Priority.ALWAYS);
//         VBox.setVgrow(abpBox, Priority.ALWAYS);
//         VBox.setVgrow(plethBox, Priority.ALWAYS);
//         VBox.setVgrow(tempBox, Priority.ALWAYS);

//         hud.getChildren().addAll(ecgBox, abpBox, plethBox, tempBox);
//         return hud;
//     }

//     private VBox buildChannelHudBlock(String title, String color, java.util.function.Supplier<HBox> rowSupplier, String subtext) {
//         VBox box = new VBox(2);
//         box.setPadding(new Insets(5, 10, 5, 10));
//         box.setStyle("-fx-border-color: transparent transparent #161F30 transparent; -fx-border-width: 1;");
//         box.setAlignment(Pos.CENTER_LEFT);

//         HBox topRow = new HBox(5);
//         topRow.setAlignment(Pos.CENTER_LEFT);

//         Rectangle colorBar = new Rectangle(3, 8, Color.web(color));
//         Label titleLbl = new Label(title);
//         titleLbl.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

//         topRow.getChildren().addAll(colorBar, titleLbl);

//         HBox valRow = rowSupplier.get();

//         Label subLbl = new Label(subtext);
//         subLbl.setStyle("-fx-font-size: 8px; -fx-text-fill: #64748B;");

//         box.getChildren().addAll(topRow, valRow, subLbl);
//         return box;
//     }

//     private HBox buildPulseBarMeter() {
//         HBox barBox = new HBox(2);
//         barBox.setAlignment(Pos.BOTTOM_CENTER);
//         barBox.setPadding(new Insets(2, 0, 2, 4));

//         pulseBars = new Rectangle[5];
//         for (int i = 0; i < 5; i++) {
//             pulseBars[i] = new Rectangle(2.5, 4 + (i * 2.5), Color.web(COLOR_PLETH, 0.25));
//             pulseBars[i].setArcWidth(2);
//             pulseBars[i].setArcHeight(2);
//             barBox.getChildren().add(pulseBars[i]);
//         }
//         return barBox;
//     }

//     private void updatePulseBarMeter(double intensity) {
//         if (pulseBars == null) return;
//         int activeCount = (int) Math.round(intensity * 5);
//         for (int i = 0; i < 5; i++) {
//             if (i < activeCount) {
//                 pulseBars[i].setFill(Color.web(COLOR_PLETH, 0.95));
//             } else {
//                 pulseBars[i].setFill(Color.web(COLOR_PLETH, 0.20));
//             }
//         }
//     }

//     // =========================================================================
//     // 4. BOTTOM MEMBRANE SOFT-KEYS CONTROL STRIP (COMPACT, NO OVERFLOW)
//     // =========================================================================
//     private HBox buildBottomSoftKeys() {
//         HBox bar = new HBox(8);
//         bar.setAlignment(Pos.CENTER_LEFT);
//         bar.setPadding(new Insets(6, 12, 6, 12));
//         bar.setMinWidth(0);
//         bar.setStyle("-fx-background-color: " + BEZEL_TOP_BG + "; -fx-background-radius: 0 0 14 14; -fx-border-color: #1E293B transparent transparent transparent; -fx-border-width: 1;");

//         // Compact Rhythm Selector
//         rhythmSelector = new ComboBox<>();
//         rhythmSelector.getItems().addAll(
//                 "Normal (78 bpm)",
//                 "Tachycardia (115 bpm)",
//                 "Bradycardia (52 bpm)",
//                 "Hypertension (92 bpm)"
//         );
//         rhythmSelector.setValue("Normal (78 bpm)");
//         rhythmSelector.setPrefWidth(140);
//         rhythmSelector.setStyle("-fx-background-color: #172033; -fx-mark-color: " + NURSE_PINK + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: white; -fx-border-color: " + NURSE_PINK + "; -fx-border-radius: 6; -fx-background-radius: 6; -fx-cursor: hand;");
//         rhythmSelector.setOnAction(e -> applyRhythmPreset(rhythmSelector.getValue()));

//         // Freeze Key
//         freezeBtn = createMembraneButton("⏸ Freeze", "#1E293B", () -> {
//             isFrozen = !isFrozen;
//             if (isFrozen) {
//                 freezeBtn.setText("▶ Resume");
//                 freezeBtn.setStyle("-fx-background-color: #D97706; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand;");
//                 alarmBanner.setText("⏸ TRACE FROZEN");
//                 alarmBanner.setStyle("-fx-background-color: rgba(217, 119, 6, 0.2); -fx-text-fill: #F59E0B; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 10; -fx-border-color: #F59E0B; -fx-border-radius: 10;");
//             } else {
//                 freezeBtn.setText("⏸ Freeze");
//                 freezeBtn.setStyle("-fx-background-color: #1E293B; -fx-text-fill: #F8FAFC; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #334155; -fx-border-radius: 6;");
//                 alarmBanner.setText("● NORMAL SINUS RHYTHM");
//                 alarmBanner.setStyle("-fx-background-color: rgba(16, 185, 129, 0.18); -fx-text-fill: #34D399; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 10; -fx-border-color: rgba(16, 185, 129, 0.4); -fx-border-radius: 10;");
//             }
//         });

//         // Silence Alarm Key
//         silenceBtn = createMembraneButton("🔇 Silence", "#1E293B", () -> {
//             isAlarmSilenced = !isAlarmSilenced;
//             if (isAlarmSilenced) {
//                 alarmSilenceSeconds = 120;
//                 silenceBtn.setText("🔇 Mute (120s)");
//                 silenceBtn.setStyle("-fx-background-color: #7C2D12; -fx-text-fill: #FDBA74; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand;");
//             } else {
//                 silenceBtn.setText("🔇 Silence");
//                 silenceBtn.setStyle("-fx-background-color: #1E293B; -fx-text-fill: #F8FAFC; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #334155; -fx-border-radius: 6;");
//             }
//         });

//         // Audio Beep Toggle
//         soundBtn = createMembraneButton("🔇 Beep", "#1E293B", () -> {
//             isAudioMuted = !isAudioMuted;
//             if (isAudioMuted) {
//                 soundBtn.setText("🔇 Beep");
//                 soundBtn.setStyle("-fx-background-color: #1E293B; -fx-text-fill: #94A3B8; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #334155; -fx-border-radius: 6;");
//             } else {
//                 soundBtn.setText("🔊 Tone");
//                 soundBtn.setStyle("-fx-background-color: #065F46; -fx-text-fill: #6EE7B7; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #10B981; -fx-border-radius: 6;");
//             }
//         });

//         // Sweep Speed Toggle
//         Button speedBtn = createMembraneButton("⏱ 25mm/s", "#1E293B", null);
//         speedBtn.setOnAction(e -> {
//             if (sweepSpeed < 3.5) {
//                 sweepSpeed = 4.8;
//                 speedBtn.setText("⏱ 50mm/s");
//             } else {
//                 sweepSpeed = 2.4;
//                 speedBtn.setText("⏱ 25mm/s");
//             }
//         });

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         Label sensorPill = new Label("📡 Sensors Online");
//         sensorPill.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + NURSE_PINK + "; -fx-padding: 2 6; -fx-background-color: " + NURSE_LIGHT_PINK + "; -fx-background-radius: 6; -fx-border-color: " + NURSE_BORDER + "; -fx-border-radius: 6;");

//         bar.getChildren().addAll(rhythmSelector, freezeBtn, silenceBtn, soundBtn, speedBtn, spacer, sensorPill);
//         return bar;
//     }

//     private Button createMembraneButton(String text, String bg, Runnable action) {
//         Button btn = new Button(text);
//         btn.setStyle("-fx-background-color: " + bg + "; -fx-text-fill: #F8FAFC; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #334155; -fx-border-radius: 6;");
//         if (action != null) {
//             btn.setOnAction(e -> action.run());
//         }
//         btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #2D3748; -fx-text-fill: #F8FAFC; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: " + NURSE_PINK + "; -fx-border-radius: 6;"));
//         btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + bg + "; -fx-text-fill: #F8FAFC; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #334155; -fx-border-radius: 6;"));
//         return btn;
//     }

//     private void applyRhythmPreset(String preset) {
//         if (preset.startsWith("Normal")) {
//             currentVitals.heartRate = 78;
//             currentVitals.systolic = 120;
//             currentVitals.diastolic = 80;
//             currentVitals.pulseRate = 78;
//             currentVitals.spo2 = 98;
//             currentVitals.temperature = 37.0;
//             currentVitals.rhythm = "NSR";
//             alarmBanner.setText("● NORMAL SINUS RHYTHM");
//             alarmBanner.setStyle("-fx-background-color: rgba(16, 185, 129, 0.18); -fx-text-fill: #34D399; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 10; -fx-border-color: rgba(16, 185, 129, 0.4); -fx-border-radius: 10;");
//             alarmLed.setFill(Color.web("#10B981"));
//         } else if (preset.startsWith("Tachycardia")) {
//             currentVitals.heartRate = 115;
//             currentVitals.systolic = 135;
//             currentVitals.diastolic = 88;
//             currentVitals.pulseRate = 115;
//             currentVitals.spo2 = 96;
//             currentVitals.temperature = 37.8;
//             currentVitals.rhythm = "SINUS TACHYCARDIA";
//             alarmBanner.setText("⚠ SINUS TACHYCARDIA");
//             alarmBanner.setStyle("-fx-background-color: rgba(239, 68, 68, 0.2); -fx-text-fill: #EF4444; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 10; -fx-border-color: #EF4444; -fx-border-radius: 10;");
//             alarmLed.setFill(Color.web("#EF4444"));
//         } else if (preset.startsWith("Bradycardia")) {
//             currentVitals.heartRate = 52;
//             currentVitals.systolic = 105;
//             currentVitals.diastolic = 65;
//             currentVitals.pulseRate = 52;
//             currentVitals.spo2 = 97;
//             currentVitals.temperature = 36.6;
//             currentVitals.rhythm = "SINUS BRADYCARDIA";
//             alarmBanner.setText("⚠ SINUS BRADYCARDIA");
//             alarmBanner.setStyle("-fx-background-color: rgba(245, 158, 11, 0.2); -fx-text-fill: #F59E0B; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 10; -fx-border-color: #F59E0B; -fx-border-radius: 10;");
//             alarmLed.setFill(Color.web("#F59E0B"));
//         } else if (preset.startsWith("Hypertension")) {
//             currentVitals.heartRate = 92;
//             currentVitals.systolic = 155;
//             currentVitals.diastolic = 98;
//             currentVitals.pulseRate = 92;
//             currentVitals.spo2 = 98;
//             currentVitals.temperature = 37.1;
//             currentVitals.rhythm = "ELEVATED ARTERIAL PRESSURE";
//             alarmBanner.setText("⚠ ELEVATED NIBP");
//             alarmBanner.setStyle("-fx-background-color: rgba(239, 68, 68, 0.2); -fx-text-fill: #EF4444; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 10; -fx-border-color: #EF4444; -fx-border-radius: 10;");
//             alarmLed.setFill(Color.web("#EF4444"));
//         }

//         updateDigitalReadouts();
//         notifyVitalsChanged();
//     }

//     // =========================================================================
//     // 5. RENDERING LOOP & DYNAMIC OSCILLOSCOPE SWEEP
//     // =========================================================================
//     private void initRenderingLoop() {
//         redrawFullMedicalGrid();

//         sweepTimer = new AnimationTimer() {
//             @Override
//             public void handle(long now) {
//                 if (isFrozen) return;

//                 double prevX = sweepX;
//                 sweepX += sweepSpeed;

//                 // Erase bar ahead of cursor and restore medical grid
//                 eraseAndRestoreGrid(sweepX, ERASE_BAR_WIDTH);

//                 // Render 4 continuous channels
//                 renderWaveforms(prevX, sweepX);

//                 // Wrap-around at right edge
//                 if (sweepX >= canvasWidth) {
//                     sweepX = 0;
//                     lastEcgY = -1;
//                     lastAbpY = -1;
//                     lastPlethY = -1;
//                     lastTempY = -1;
//                 }
//             }
//         };
//         sweepTimer.start();
//     }

//     private void renderWaveforms(double x0, double x1) {
//         if (gc == null) return;

//         double chHeight = canvasHeight / 4.0;
//         double ecgBaseY = chHeight * 0.52;
//         double abpBaseY = chHeight * 1.54;
//         double plethBaseY = chHeight * 2.54;
//         double tempBaseY = chHeight * 3.52;

//         // Rate & Cycle period based on heart rate
//         double cycleLength = (60.0 / Math.max(35, currentVitals.heartRate)) * 140.0;
//         double phase = (x1 % cycleLength) / cycleLength; // 0.0 to 1.0

//         // 1. ECG Signal Calculation (Lead II)
//         double ecgOffset = calculateEcgSample(phase);
//         double curEcgY = ecgBaseY - (ecgOffset * 25.0);

//         // Flash Heart on R-Peak
//         if (phase >= 0.35 && phase <= 0.38) {
//             triggerHeartbeatVisual();
//         }

//         // Draw Channel 1: ECG (Neon Green)
//         gc.setLineWidth(2.0);
//         gc.setStroke(Color.web(COLOR_ECG));
//         if (lastEcgY != -1 && x0 < x1) {
//             gc.strokeLine(x0, lastEcgY, x1, curEcgY);
//         }
//         lastEcgY = curEcgY;

//         // 2. Arterial Blood Pressure (ABP) Signal (LifeLink Pink / Coral)
//         double abpPhase = (phase + 0.88) % 1.0;
//         double abpOffset = calculateAbpSample(abpPhase);
//         double abpAmplitude = (currentVitals.systolic - currentVitals.diastolic) * 0.30;
//         double curAbpY = abpBaseY - (abpOffset * abpAmplitude);

//         gc.setLineWidth(1.8);
//         gc.setStroke(Color.web(COLOR_ABP));
//         if (lastAbpY != -1 && x0 < x1) {
//             gc.strokeLine(x0, lastAbpY, x1, curAbpY);
//         }
//         lastAbpY = curAbpY;

//         // 3. PLETH Signal (Pulse / SpO2 Aqua Cyan)
//         double plethPhase = (phase + 0.84) % 1.0;
//         double plethOffset = calculatePlethSample(plethPhase);
//         double curPlethY = plethBaseY - (plethOffset * 18.0);

//         gc.setLineWidth(1.8);
//         gc.setStroke(Color.web(COLOR_PLETH));
//         if (lastPlethY != -1 && x0 < x1) {
//             gc.strokeLine(x0, lastPlethY, x1, curPlethY);
//         }
//         lastPlethY = curPlethY;
//         updatePulseBarMeter(Math.max(0.1, plethOffset));

//         // 4. TEMP Signal (Continuous Thermal Telemetry Line, Amber Gold)
//         phaseCounter += 0.02;
//         double tempDrift = Math.sin(phaseCounter * 0.4) * 2.5 + Math.cos(phaseCounter * 0.15) * 1.5;
//         double curTempY = tempBaseY + tempDrift;

//         gc.setLineWidth(1.5);
//         gc.setStroke(Color.web(COLOR_TEMP));
//         if (lastTempY != -1 && x0 < x1) {
//             gc.strokeLine(x0, lastTempY, x1, curTempY);
//         }
//         lastTempY = curTempY;
//     }

//     private double calculateEcgSample(double phase) {
//         if (phase < 0.12) {
//             return 0.0;
//         } else if (phase < 0.22) {
//             double pPhase = (phase - 0.12) / 0.10;
//             return Math.sin(pPhase * Math.PI) * 0.18;
//         } else if (phase < 0.32) {
//             return 0.0;
//         } else if (phase < 0.35) {
//             double qPhase = (phase - 0.32) / 0.03;
//             return -Math.sin(qPhase * Math.PI) * 0.15;
//         } else if (phase < 0.40) {
//             double rPhase = (phase - 0.35) / 0.05;
//             return Math.sin(rPhase * Math.PI) * 1.0;
//         } else if (phase < 0.44) {
//             double sPhase = (phase - 0.40) / 0.04;
//             return -Math.sin(sPhase * Math.PI) * 0.32;
//         } else if (phase < 0.52) {
//             return 0.0;
//         } else if (phase < 0.68) {
//             double tPhase = (phase - 0.52) / 0.16;
//             return Math.sin(tPhase * Math.PI) * 0.32;
//         } else {
//             return (random.nextDouble() - 0.5) * 0.015;
//         }
//     }

//     private double calculateAbpSample(double phase) {
//         if (phase < 0.15) {
//             double p = phase / 0.15;
//             return Math.sin(p * Math.PI * 0.5);
//         } else if (phase < 0.32) {
//             double p = (phase - 0.15) / 0.17;
//             return 1.0 - (p * 0.45);
//         } else if (phase < 0.42) {
//             double p = (phase - 0.32) / 0.10;
//             return 0.55 + (Math.sin(p * Math.PI) * 0.15);
//         } else {
//             double p = (phase - 0.42) / 0.58;
//             return 0.55 * Math.exp(-p * 2.2);
//         }
//     }

//     private double calculatePlethSample(double phase) {
//         if (phase < 0.20) {
//             double p = phase / 0.20;
//             return Math.sin(p * Math.PI * 0.5);
//         } else if (phase < 0.40) {
//             double p = (phase - 0.20) / 0.20;
//             return 1.0 - (p * 0.35);
//         } else if (phase < 0.55) {
//             double p = (phase - 0.40) / 0.15;
//             return 0.65 + (Math.sin(p * Math.PI) * 0.12);
//         } else {
//             double p = (phase - 0.55) / 0.45;
//             return 0.65 * (1.0 - p);
//         }
//     }

//     private void triggerHeartbeatVisual() {
//         if (ecgBeatHeart != null) {
//             ecgBeatHeart.setScaleX(1.3);
//             ecgBeatHeart.setScaleY(1.3);
//             ecgBeatHeart.setFill(Color.WHITE);

//             if (!isAudioMuted) {
//                 new Thread(() -> {
//                     try {
//                         Toolkit.getDefaultToolkit().beep();
//                     } catch (Exception ignored) {}
//                 }).start();
//             }

//             Platform.runLater(() -> {
//                 ecgBeatHeart.setScaleX(1.0);
//                 ecgBeatHeart.setScaleY(1.0);
//                 ecgBeatHeart.setFill(Color.web(COLOR_ECG));
//             });
//         }
//     }

//     // =========================================================================
//     // 6. MEDICAL GRID BACKGROUND MANAGEMENT (CLEAN, NO BLUE BARCODE)
//     // =========================================================================
//     private void redrawFullMedicalGrid() {
//         if (gc == null) return;

//         // Clean dark medical background
//         gc.setFill(Color.web(SCREEN_BG));
//         gc.fillRect(0, 0, canvasWidth, canvasHeight);

//         // Major subtle grid lines (40px horizontal pitch, 35px vertical pitch)
//         gc.setLineWidth(0.8);
//         gc.setStroke(Color.rgb(30, 41, 59, 0.45)); // Clean dark slate grid lines
//         for (double x = 0; x < canvasWidth; x += 40) {
//             gc.strokeLine(x, 0, x, canvasHeight);
//         }
//         for (double y = 0; y < canvasHeight; y += 35) {
//             gc.strokeLine(0, y, canvasWidth, y);
//         }

//         // Distinct channel divider lines
//         double chHeight = canvasHeight / 4.0;
//         gc.setLineWidth(1.0);
//         gc.setStroke(Color.rgb(51, 65, 85, 0.7));
//         for (int i = 1; i < 4; i++) {
//             gc.strokeLine(0, chHeight * i, canvasWidth, chHeight * i);
//         }

//         // Channel Labels on Canvas
//         drawChannelCanvasHeader(gc, "ECG II", COLOR_ECG, 8, 15);
//         drawChannelCanvasHeader(gc, "ART mmHg", COLOR_ABP, 8, (int)(chHeight + 15));
//         drawChannelCanvasHeader(gc, "PLETH", COLOR_PLETH, 8, (int)(chHeight * 2 + 15));
//         drawChannelCanvasHeader(gc, "TEMP °C", COLOR_TEMP, 8, (int)(chHeight * 3 + 15));
//     }

//     private void drawChannelCanvasHeader(GraphicsContext g, String text, String color, int x, int y) {
//         g.setFont(Font.font("Consolas", FontWeight.BOLD, 9));
//         g.setFill(Color.web(color, 0.85));
//         g.fillText(text, x, y);
//     }

//     private void eraseAndRestoreGrid(double x, double width) {
//         if (gc == null) return;

//         double drawX = x;
//         double drawW = Math.min(width, canvasWidth - drawX);
//         if (drawW <= 0) return;

//         // Clear erased strip
//         gc.setFill(Color.web(SCREEN_BG));
//         gc.fillRect(drawX, 0, drawW, canvasHeight);

//         // Restore horizontal grid lines
//         gc.setLineWidth(0.8);
//         gc.setStroke(Color.rgb(30, 41, 59, 0.45));
//         for (double gy = 0; gy < canvasHeight; gy += 35) {
//             gc.strokeLine(drawX, gy, drawX + drawW, gy);
//         }

//         // Restore vertical grid lines that fall into the strip
//         double startMajor = Math.floor(drawX / 40.0) * 40.0;
//         for (double gx = startMajor; gx <= drawX + drawW; gx += 40) {
//             if (gx >= drawX && gx <= drawX + drawW) {
//                 gc.strokeLine(gx, 0, gx, canvasHeight);
//             }
//         }

//         // Restore channel dividers
//         double chHeight = canvasHeight / 4.0;
//         gc.setLineWidth(1.0);
//         gc.setStroke(Color.rgb(51, 65, 85, 0.7));
//         for (int i = 1; i < 4; i++) {
//             gc.strokeLine(drawX, chHeight * i, drawX + drawW, chHeight * i);
//         }

//         // Draw dynamic sweep bar cursor (LifeLink Pink glowing lead edge)
//         gc.setLineWidth(2.2);
//         gc.setStroke(Color.web(NURSE_PINK, 0.90));
//         gc.strokeLine(drawX, 0, drawX, canvasHeight);
//     }

//     // =========================================================================
//     // 7. REAL-TIME PHYSIOLOGICAL DATA FLUCTUATIONS & TIMERS
//     // =========================================================================
//     private void initTelemetryFluctuations() {
//         telemetryTicker = new Timeline(new KeyFrame(Duration.seconds(2.5), e -> {
//             if (isFrozen) return;

//             int hrDelta = random.nextInt(3) - 1;
//             currentVitals.heartRate = Math.max(45, Math.min(160, currentVitals.heartRate + hrDelta));
//             currentVitals.pulseRate = currentVitals.heartRate;

//             if (random.nextDouble() < 0.3) {
//                 int sysDelta = random.nextInt(3) - 1;
//                 int diaDelta = random.nextInt(3) - 1;
//                 currentVitals.systolic = Math.max(90, Math.min(190, currentVitals.systolic + sysDelta));
//                 currentVitals.diastolic = Math.max(50, Math.min(115, currentVitals.diastolic + diaDelta));
//             }

//             if (random.nextDouble() < 0.2) {
//                 double tempDelta = (random.nextDouble() - 0.5) * 0.1;
//                 currentVitals.temperature = Math.round((currentVitals.temperature + tempDelta) * 10.0) / 10.0;
//             }

//             updateDigitalReadouts();
//             notifyVitalsChanged();
//         }));
//         telemetryTicker.setCycleCount(Timeline.INDEFINITE);
//         telemetryTicker.play();
//     }

//     private void initClockTicker() {
//         clockTicker = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
//             if (clockLabel != null) {
//                 clockLabel.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " IST");
//             }

//             if (isAlarmSilenced) {
//                 alarmSilenceSeconds--;
//                 if (alarmSilenceSeconds <= 0) {
//                     isAlarmSilenced = false;
//                     silenceBtn.setText("🔇 Silence");
//                     silenceBtn.setStyle("-fx-background-color: #1E293B; -fx-text-fill: #F8FAFC; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #334155; -fx-border-radius: 6;");
//                 } else {
//                     silenceBtn.setText("🔇 (" + alarmSilenceSeconds + "s)");
//                 }
//             }
//         }));
//         clockTicker.setCycleCount(Timeline.INDEFINITE);
//         clockTicker.play();
//     }

//     private void updateDigitalReadouts() {
//         if (ecgValLabel != null) ecgValLabel.setText(String.valueOf(currentVitals.heartRate));
//         if (abpValLabel != null) abpValLabel.setText(currentVitals.systolic + "/" + currentVitals.diastolic);
//         if (abpMapLabel != null) {
//             int mean = (int) Math.round((2.0 * currentVitals.diastolic + currentVitals.systolic) / 3.0);
//             abpMapLabel.setText("(" + mean + ")");
//         }
//         if (plethValLabel != null) plethValLabel.setText(String.valueOf(currentVitals.pulseRate));
//         if (spo2ValLabel != null) spo2ValLabel.setText(currentVitals.spo2 + "%");
//         if (tempValLabel != null) tempValLabel.setText(String.format("%.1f", currentVitals.temperature));
//         if (tempFahrLabel != null) {
//             double fahr = (currentVitals.temperature * 9.0 / 5.0) + 32.0;
//             tempFahrLabel.setText(String.format("%.1f°F", fahr));
//         }
//     }

//     private void notifyVitalsChanged() {
//         if (onVitalsUpdateListener != null) {
//             onVitalsUpdateListener.accept(currentVitals);
//         }
//     }

//     public VitalsSnapshot getCurrentVitals() {
//         return currentVitals;
//     }

//     // =========================================================================
//     // 8. CLEAN LIFECYCLE MANAGEMENT
//     // =========================================================================
//     public void stopAnimation() {
//         if (sweepTimer != null) {
//             sweepTimer.stop();
//         }
//         if (telemetryTicker != null) {
//             telemetryTicker.stop();
//         }
//         if (clockTicker != null) {
//             clockTicker.stop();
//         }
//     }

//     public void startAnimation() {
//         if (sweepTimer != null) {
//             sweepTimer.start();
//         }
//         if (telemetryTicker != null) {
//             telemetryTicker.play();
//         }
//         if (clockTicker != null) {
//             clockTicker.play();
//         }
//     }
// }



package com.kurukshetra.view.nurse;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Authentic Hospital ICU Bedside Patient Monitor Component.
 * Themed to match the LifeLink Nurse Dashboard (Primary Pink #E67593 accents).
 * Fully responsive width (no horizontal scrollbar) with ResizableCanvas.
 */
public class PatientIcuMonitorView extends VBox {

    // LifeLink Nurse Theme Color Accents
    public static final String NURSE_PINK = "#E67593";
    public static final String NURSE_PINK_DARK = "#D85375";
    public static final String NURSE_LIGHT_PINK = "#FDF0F4";
    public static final String NURSE_BORDER = "#EEDEE3";

    // Clinical Monitor Hardware Palette
    private static final String CHASSIS_BG = "#0B111E";
    private static final String SCREEN_BG = "#070B14";
    private static final String BEZEL_TOP_BG = "#0E1526";
    private static final String BEZEL_BORDER = "#E67593";

    // Channel Clinical Colors (Harmonized with Nurse Palette)
    public static final String COLOR_ECG = "#00FF66";     // Vibrant Neon Green (Heart Rate)
    public static final String COLOR_ABP = "#E67593";     // LifeLink Medical Pink (Blood Pressure)
    public static final String COLOR_PLETH = "#00E5FF";   // Medical Aqua Cyan (Pulse Rate / SpO2)
    public static final String COLOR_TEMP = "#FFD600";    // Warm Golden Amber (Temperature)

    // Patient and Clinical State
    public static class VitalsSnapshot {
        public int heartRate;
        public int systolic;
        public int diastolic;
        public int pulseRate;
        public int spo2;
        public double temperature;
        public String rhythm;

        public VitalsSnapshot(int hr, int sys, int dia, int pr, int spo2, double temp, String rhythm) {
            this.heartRate = hr;
            this.systolic = sys;
            this.diastolic = dia;
            this.pulseRate = pr;
            this.spo2 = spo2;
            this.temperature = temp;
            this.rhythm = rhythm;
        }
    }

    private final VitalsSnapshot currentVitals = new VitalsSnapshot(78, 120, 80, 78, 98, 37.0, "NSR");
    private Consumer<VitalsSnapshot> onVitalsUpdateListener;

    // Resizable Canvas for 100% Horizontal Fit without scroll
    private static class ResizableCanvas extends Canvas {
        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double minWidth(double height) {
            return 80;
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
            return 120;
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

    private ResizableCanvas waveformCanvas;
    private GraphicsContext gc;
    private AnimationTimer sweepTimer;
    private Timeline telemetryTicker;
    private Timeline clockTicker;

    private double canvasWidth = 600;
    private double canvasHeight = 270;
    private double sweepX = 0;
    private double sweepSpeed = 2.4;
    private static final double ERASE_BAR_WIDTH = 20.0;

    // Waveform tracking
    private double lastEcgY = -1;
    private double lastAbpY = -1;
    private double lastPlethY = -1;
    private double lastTempY = -1;
    private double phaseCounter = 0;

    // Simulation & State controls
    private boolean isFrozen = false;
    private boolean isAudioMuted = true;
    private boolean isAlarmSilenced = false;
    private int alarmSilenceSeconds = 0;
    private final Random random = new Random();

    // UI Elements on Monitor
    private Label clockLabel;
    private Label alarmBanner;
    private Circle alarmLed;
    private Text ecgBeatHeart;
    private Label ecgValLabel;
    private Label abpValLabel;
    private Label abpMapLabel;
    private Label plethValLabel;
    private Label spo2ValLabel;
    private Label tempValLabel;
    private Label tempFahrLabel;
    private HBox pulseBarContainer;
    private Rectangle[] pulseBars;
    private Button freezeBtn;
    private Button soundBtn;
    private Button silenceBtn;
    private ComboBox<String> rhythmSelector;

    public PatientIcuMonitorView(String patientId, String hospitalDestination, Consumer<VitalsSnapshot> listener) {
        this.onVitalsUpdateListener = listener;

        setSpacing(0);
        setAlignment(Pos.TOP_CENTER);
        setMinWidth(0);
        setMaxWidth(Double.MAX_VALUE);
        setStyle("-fx-background-color: " + CHASSIS_BG + "; -fx-background-radius: 16; -fx-border-color: " + BEZEL_BORDER + "; -fx-border-width: 1.5; -fx-border-radius: 16;");
        setEffect(new DropShadow(14, 0, 3, Color.rgb(230, 117, 147, 0.14)));
        VBox.setVgrow(this, Priority.NEVER);

        // 1. Monitor Top Bezel Bar
        HBox topBezel = buildTopBezel(patientId, hospitalDestination);

        // 2. Main Screen Area (Resizable Canvas + Telemetry HUD)
        HBox screenViewport = buildScreenViewport();

        // 3. Monitor Bottom Membrane Soft-Keys Control Bar
        HBox bottomSoftKeys = buildBottomSoftKeys();

        getChildren().addAll(topBezel, screenViewport, bottomSoftKeys);

        // Start animations and timers
        initRenderingLoop();
        initTelemetryFluctuations();
        initClockTicker();

        // Initial listener notification
        notifyVitalsChanged();
    }

    // =========================================================================
    // 1. TOP BEZEL STRIP
    // =========================================================================
    private HBox buildTopBezel(String patientId, String hospitalDestination) {
        HBox bezel = new HBox(10);
        bezel.setAlignment(Pos.CENTER_LEFT);
        bezel.setPadding(new Insets(6, 14, 6, 14));
        bezel.setMinWidth(0);
        bezel.setStyle("-fx-background-color: " + BEZEL_TOP_BG + "; -fx-background-radius: 14 14 0 0; -fx-border-color: transparent transparent #1E293B transparent; -fx-border-width: 1;");

        // Left: Telemetry Badge + Bed Info
        HBox leftSec = new HBox(6);
        leftSec.setAlignment(Pos.CENTER_LEFT);

        Label brandBadge = new Label("💓 Telemetry");
        brandBadge.setStyle("-fx-background-color: " + NURSE_LIGHT_PINK + "; -fx-text-fill: " + NURSE_PINK + "; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 2 6; -fx-background-radius: 6; -fx-border-color: " + NURSE_BORDER + "; -fx-border-radius: 6;");

        alarmLed = new Circle(3.5, Color.web("#10B981"));
        alarmLed.setEffect(new Glow(0.6));

        Label bedInfo = new Label("Bed-04 • " + patientId);
        bedInfo.setStyle("-fx-font-size: 10px; -fx-text-fill: #E2E8F0; -fx-font-weight: bold;");

        leftSec.getChildren().addAll(brandBadge, alarmLed, bedInfo);

        Region sp1 = new Region();
        HBox.setHgrow(sp1, Priority.ALWAYS);

        // Center: Master Alarm Status
        alarmBanner = new Label("● NORMAL SINUS RHYTHM");
        alarmBanner.setStyle("-fx-background-color: rgba(16, 185, 129, 0.18); -fx-text-fill: #34D399; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 10; -fx-background-radius: 10; -fx-border-color: rgba(16, 185, 129, 0.4); -fx-border-radius: 10;");

        Region sp2 = new Region();
        HBox.setHgrow(sp2, Priority.ALWAYS);

        // Right: Mode + Clock
        HBox rightSec = new HBox(6);
        rightSec.setAlignment(Pos.CENTER_RIGHT);

        Label filterLabel = new Label("LEAD II • 25mm/s");
        filterLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #94A3B8; -fx-font-family: monospace;");

        clockLabel = new Label("00:00:00 IST");
        clockLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #F8FAFC; -fx-font-family: monospace; -fx-background-color: #172033; -fx-padding: 2 6; -fx-background-radius: 4; -fx-border-color: " + NURSE_PINK + "; -fx-border-radius: 4;");

        rightSec.getChildren().addAll(filterLabel, clockLabel);

        bezel.getChildren().addAll(leftSec, sp1, alarmBanner, sp2, rightSec);
        return bezel;
    }

    // =========================================================================
    // 2. MAIN SCREEN VIEWPORT (OSCILLOSCOPE CANVAS + TELEMETRY HUD)
    // =========================================================================
    private HBox buildScreenViewport() {
        HBox viewport = new HBox(0);
        viewport.setStyle("-fx-background-color: " + SCREEN_BG + ";");
        viewport.setMinWidth(0);
        HBox.setHgrow(viewport, Priority.ALWAYS);

        // Left Container: Canvas with Medical ECG Grid
        StackPane canvasContainer = new StackPane();
        canvasContainer.setStyle("-fx-background-color: " + SCREEN_BG + ";");
        canvasContainer.setMinWidth(0);
        HBox.setHgrow(canvasContainer, Priority.ALWAYS);

        waveformCanvas = new ResizableCanvas();
        waveformCanvas.setHeight(canvasHeight);
        gc = waveformCanvas.getGraphicsContext2D();

        waveformCanvas.widthProperty().bind(canvasContainer.widthProperty());
        waveformCanvas.widthProperty().addListener((obs, oldW, newW) -> {
            if (newW.doubleValue() > 50) {
                canvasWidth = newW.doubleValue();
                redrawFullMedicalGrid();
            }
        });

        canvasContainer.getChildren().add(waveformCanvas);

        // Right Container: Dedicated ICU Digital Telemetry HUD (4 Channels)
        VBox rightTelemetryHud = buildRightTelemetryHud();
        rightTelemetryHud.setPrefWidth(190);
        rightTelemetryHud.setMinWidth(180);
        rightTelemetryHud.setMaxWidth(200);

        viewport.getChildren().addAll(canvasContainer, rightTelemetryHud);
        return viewport;
    }

    // =========================================================================
    // 3. RIGHT TELEMETRY HUD STRIP
    // =========================================================================
    private VBox buildRightTelemetryHud() {
        VBox hud = new VBox(0);
        hud.setStyle("-fx-background-color: #070B14; -fx-border-color: transparent transparent transparent #1E293B; -fx-border-width: 1;");
        hud.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(hud, Priority.ALWAYS);

        // Channel 1: ECG HUD
        VBox ecgBox = buildChannelHudBlock("ECG II", COLOR_ECG, () -> {
            HBox row = new HBox(4);
            row.setAlignment(Pos.BASELINE_LEFT);

            ecgBeatHeart = new Text("♥");
            ecgBeatHeart.setStyle("-fx-font-size: 14px; -fx-fill: " + COLOR_ECG + ";");
            ecgBeatHeart.setEffect(new Glow(0.8));

            ecgValLabel = new Label(String.valueOf(currentVitals.heartRate));
            ecgValLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_ECG + "; -fx-font-family: 'Consolas', monospace;");

            VBox unitBox = new VBox(0);
            unitBox.setAlignment(Pos.BOTTOM_LEFT);
            Label bpmLbl = new Label("BPM");
            bpmLbl.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_ECG + ";");
            Label limitsLbl = new Label("[50-120]");
            limitsLbl.setStyle("-fx-font-size: 8px; -fx-text-fill: #64748B;");
            unitBox.getChildren().addAll(bpmLbl, limitsLbl);

            row.getChildren().addAll(ecgBeatHeart, ecgValLabel, unitBox);
            return row;
        }, "Normal Sinus • Lead II");

        // Channel 2: Arterial Blood Pressure HUD (Nurse Pink Themed)
        VBox abpBox = buildChannelHudBlock("ART / NIBP", COLOR_ABP, () -> {
            HBox row = new HBox(4);
            row.setAlignment(Pos.BASELINE_LEFT);

            abpValLabel = new Label(currentVitals.systolic + "/" + currentVitals.diastolic);
            abpValLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_ABP + "; -fx-font-family: 'Consolas', monospace;");

            VBox unitBox = new VBox(0);
            unitBox.setAlignment(Pos.BOTTOM_LEFT);
            Label mmLbl = new Label("mmHg");
            mmLbl.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_ABP + ";");
            int mean = (int) Math.round((2.0 * currentVitals.diastolic + currentVitals.systolic) / 3.0);
            abpMapLabel = new Label("(" + mean + ")");
            abpMapLabel.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: #FDA4AF;");
            unitBox.getChildren().addAll(mmLbl, abpMapLabel);

            row.getChildren().addAll(abpValLabel, unitBox);
            return row;
        }, "ABP Limits [90/60-140/90]");

        // Channel 3: PLETH / SpO2 HUD
        VBox plethBox = buildChannelHudBlock("PLETH / SpO₂", COLOR_PLETH, () -> {
            HBox row = new HBox(6);
            row.setAlignment(Pos.CENTER_LEFT);

            VBox prCol = new VBox(0);
            plethValLabel = new Label(String.valueOf(currentVitals.pulseRate));
            plethValLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_PLETH + "; -fx-font-family: 'Consolas', monospace;");
            Label prUnit = new Label("PR bpm");
            prUnit.setStyle("-fx-font-size: 8px; -fx-text-fill: #64748B;");
            prCol.getChildren().addAll(plethValLabel, prUnit);

            VBox spo2Col = new VBox(0);
            spo2ValLabel = new Label(currentVitals.spo2 + "%");
            spo2ValLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_PLETH + "; -fx-font-family: 'Consolas', monospace;");
            Label spo2Unit = new Label("SpO₂");
            spo2Unit.setStyle("-fx-font-size: 8px; -fx-text-fill: #64748B;");
            spo2Col.getChildren().addAll(spo2ValLabel, spo2Unit);

            pulseBarContainer = buildPulseBarMeter();

            row.getChildren().addAll(prCol, spo2Col, pulseBarContainer);
            return row;
        }, "PI 4.2% • Signal Strong");

        // Channel 4: Temperature HUD
        VBox tempBox = buildChannelHudBlock("TEMP 1", COLOR_TEMP, () -> {
            HBox row = new HBox(5);
            row.setAlignment(Pos.BASELINE_LEFT);

            tempValLabel = new Label(String.format("%.1f", currentVitals.temperature));
            tempValLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_TEMP + "; -fx-font-family: 'Consolas', monospace;");

            VBox unitBox = new VBox(0);
            unitBox.setAlignment(Pos.BOTTOM_LEFT);
            Label cLbl = new Label("°C");
            cLbl.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_TEMP + ";");
            double fahr = (currentVitals.temperature * 9.0 / 5.0) + 32.0;
            tempFahrLabel = new Label(String.format("%.1f°F", fahr));
            tempFahrLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #FDE68A;");
            unitBox.getChildren().addAll(cLbl, tempFahrLabel);

            row.getChildren().addAll(tempValLabel, unitBox);
            return row;
        }, "Core • Normothermic");

        VBox.setVgrow(ecgBox, Priority.ALWAYS);
        VBox.setVgrow(abpBox, Priority.ALWAYS);
        VBox.setVgrow(plethBox, Priority.ALWAYS);
        VBox.setVgrow(tempBox, Priority.ALWAYS);

        hud.getChildren().addAll(ecgBox, abpBox, plethBox, tempBox);
        return hud;
    }

    private VBox buildChannelHudBlock(String title, String color, java.util.function.Supplier<HBox> rowSupplier, String subtext) {
        VBox box = new VBox(2);
        box.setPadding(new Insets(5, 10, 5, 10));
        box.setStyle("-fx-border-color: transparent transparent #161F30 transparent; -fx-border-width: 1;");
        box.setAlignment(Pos.CENTER_LEFT);

        HBox topRow = new HBox(5);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Rectangle colorBar = new Rectangle(3, 8, Color.web(color));
        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");

        topRow.getChildren().addAll(colorBar, titleLbl);

        HBox valRow = rowSupplier.get();

        Label subLbl = new Label(subtext);
        subLbl.setStyle("-fx-font-size: 8px; -fx-text-fill: #64748B;");

        box.getChildren().addAll(topRow, valRow, subLbl);
        return box;
    }

    private HBox buildPulseBarMeter() {
        HBox barBox = new HBox(2);
        barBox.setAlignment(Pos.BOTTOM_CENTER);
        barBox.setPadding(new Insets(2, 0, 2, 4));

        pulseBars = new Rectangle[5];
        for (int i = 0; i < 5; i++) {
            pulseBars[i] = new Rectangle(2.5, 4 + (i * 2.5), Color.web(COLOR_PLETH, 0.25));
            pulseBars[i].setArcWidth(2);
            pulseBars[i].setArcHeight(2);
            barBox.getChildren().add(pulseBars[i]);
        }
        return barBox;
    }

    private void updatePulseBarMeter(double intensity) {
        if (pulseBars == null) return;
        int activeCount = (int) Math.round(intensity * 5);
        for (int i = 0; i < 5; i++) {
            if (i < activeCount) {
                pulseBars[i].setFill(Color.web(COLOR_PLETH, 0.95));
            } else {
                pulseBars[i].setFill(Color.web(COLOR_PLETH, 0.20));
            }
        }
    }

    // =========================================================================
    // 4. BOTTOM MEMBRANE SOFT-KEYS CONTROL STRIP (COMPACT, NO OVERFLOW)
    // =========================================================================
    private HBox buildBottomSoftKeys() {
        HBox bar = new HBox(8);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(6, 12, 6, 12));
        bar.setMinWidth(0);
        bar.setStyle("-fx-background-color: " + BEZEL_TOP_BG + "; -fx-background-radius: 0 0 14 14; -fx-border-color: #1E293B transparent transparent transparent; -fx-border-width: 1;");

        // Compact Rhythm Selector
        rhythmSelector = new ComboBox<>();
        rhythmSelector.getItems().addAll(
                "Normal (78 bpm)",
                "Tachycardia (115 bpm)",
                "Bradycardia (52 bpm)",
                "Hypertension (92 bpm)"
        );
        rhythmSelector.setValue("Normal (78 bpm)");
        rhythmSelector.setPrefWidth(140);
        rhythmSelector.setStyle("-fx-background-color: #e7e8eaff; -fx-mark-color: " + NURSE_PINK + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-border-color: " + NURSE_PINK + "; -fx-border-radius: 6; -fx-background-radius: 6; -fx-cursor: hand;");
        rhythmSelector.setOnAction(e -> applyRhythmPreset(rhythmSelector.getValue()));

        // Freeze Key
        freezeBtn = createMembraneButton("⏸ Freeze", "#1E293B", () -> {
            isFrozen = !isFrozen;
            if (isFrozen) {
                freezeBtn.setText("▶ Resume");
                freezeBtn.setStyle("-fx-background-color: #D97706; -fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand;");
                alarmBanner.setText("⏸ TRACE FROZEN");
                alarmBanner.setStyle("-fx-background-color: rgba(217, 119, 6, 0.2); -fx-text-fill: #F59E0B; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 10; -fx-border-color: #F59E0B; -fx-border-radius: 10;");
            } else {
                freezeBtn.setText("⏸ Freeze");
                freezeBtn.setStyle("-fx-background-color: #1E293B; -fx-text-fill: #F8FAFC; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #334155; -fx-border-radius: 6;");
                alarmBanner.setText("● NORMAL SINUS RHYTHM");
                alarmBanner.setStyle("-fx-background-color: rgba(16, 185, 129, 0.18); -fx-text-fill: #34D399; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 10; -fx-border-color: rgba(16, 185, 129, 0.4); -fx-border-radius: 10;");
            }
        });

        // Silence Alarm Key
        silenceBtn = createMembraneButton("🔇 Silence", "#1E293B", () -> {
            isAlarmSilenced = !isAlarmSilenced;
            if (isAlarmSilenced) {
                alarmSilenceSeconds = 120;
                silenceBtn.setText("🔇 Mute (120s)");
                silenceBtn.setStyle("-fx-background-color: #7C2D12; -fx-text-fill: #FDBA74; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand;");
            } else {
                silenceBtn.setText("🔇 Silence");
                silenceBtn.setStyle("-fx-background-color: #1E293B; -fx-text-fill: #F8FAFC; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #334155; -fx-border-radius: 6;");
            }
        });

        // Audio Beep Toggle
        soundBtn = createMembraneButton("🔇 Beep", "#1E293B", () -> {
            isAudioMuted = !isAudioMuted;
            if (isAudioMuted) {
                soundBtn.setText("🔇 Beep");
                soundBtn.setStyle("-fx-background-color: #1E293B; -fx-text-fill: #94A3B8; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #334155; -fx-border-radius: 6;");
            } else {
                soundBtn.setText("🔊 Tone");
                soundBtn.setStyle("-fx-background-color: #065F46; -fx-text-fill: #6EE7B7; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #10B981; -fx-border-radius: 6;");
            }
        });

        // Sweep Speed Toggle
        Button speedBtn = createMembraneButton("⏱ 25mm/s", "#1E293B", null);
        speedBtn.setOnAction(e -> {
            if (sweepSpeed < 3.5) {
                sweepSpeed = 4.8;
                speedBtn.setText("⏱ 50mm/s");
            } else {
                sweepSpeed = 2.4;
                speedBtn.setText("⏱ 25mm/s");
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label sensorPill = new Label("📡 Sensors Online");
        sensorPill.setStyle("-fx-font-size: 9px; -fx-font-weight: bold; -fx-text-fill: " + NURSE_PINK + "; -fx-padding: 2 6; -fx-background-color: " + NURSE_LIGHT_PINK + "; -fx-background-radius: 6; -fx-border-color: " + NURSE_BORDER + "; -fx-border-radius: 6;");

        bar.getChildren().addAll(rhythmSelector, freezeBtn, silenceBtn, soundBtn, speedBtn, spacer, sensorPill);
        return bar;
    }

    private Button createMembraneButton(String text, String bg, Runnable action) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: " + bg + "; -fx-text-fill: #F8FAFC; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #334155; -fx-border-radius: 6;");
        if (action != null) {
            btn.setOnAction(e -> action.run());
        }
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #2D3748; -fx-text-fill: #F8FAFC; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: " + NURSE_PINK + "; -fx-border-radius: 6;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + bg + "; -fx-text-fill: #F8FAFC; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #334155; -fx-border-radius: 6;"));
        return btn;
    }

    private void applyRhythmPreset(String preset) {
        if (preset.startsWith("Normal")) {
            currentVitals.heartRate = 78;
            currentVitals.systolic = 120;
            currentVitals.diastolic = 80;
            currentVitals.pulseRate = 78;
            currentVitals.spo2 = 98;
            currentVitals.temperature = 37.0;
            currentVitals.rhythm = "NSR";
            alarmBanner.setText("● NORMAL SINUS RHYTHM");
            alarmBanner.setStyle("-fx-background-color: rgba(16, 185, 129, 0.18); -fx-text-fill: #34D399; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 10; -fx-border-color: rgba(16, 185, 129, 0.4); -fx-border-radius: 10;");
            alarmLed.setFill(Color.web("#10B981"));
        } else if (preset.startsWith("Tachycardia")) {
            currentVitals.heartRate = 115;
            currentVitals.systolic = 135;
            currentVitals.diastolic = 88;
            currentVitals.pulseRate = 115;
            currentVitals.spo2 = 96;
            currentVitals.temperature = 37.8;
            currentVitals.rhythm = "SINUS TACHYCARDIA";
            alarmBanner.setText("⚠ SINUS TACHYCARDIA");
            alarmBanner.setStyle("-fx-background-color: rgba(239, 68, 68, 0.2); -fx-text-fill: #EF4444; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 10; -fx-border-color: #EF4444; -fx-border-radius: 10;");
            alarmLed.setFill(Color.web("#EF4444"));
        } else if (preset.startsWith("Bradycardia")) {
            currentVitals.heartRate = 52;
            currentVitals.systolic = 105;
            currentVitals.diastolic = 65;
            currentVitals.pulseRate = 52;
            currentVitals.spo2 = 97;
            currentVitals.temperature = 36.6;
            currentVitals.rhythm = "SINUS BRADYCARDIA";
            alarmBanner.setText("⚠ SINUS BRADYCARDIA");
            alarmBanner.setStyle("-fx-background-color: rgba(245, 158, 11, 0.2); -fx-text-fill: #F59E0B; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 10; -fx-border-color: #F59E0B; -fx-border-radius: 10;");
            alarmLed.setFill(Color.web("#F59E0B"));
        } else if (preset.startsWith("Hypertension")) {
            currentVitals.heartRate = 92;
            currentVitals.systolic = 155;
            currentVitals.diastolic = 98;
            currentVitals.pulseRate = 92;
            currentVitals.spo2 = 98;
            currentVitals.temperature = 37.1;
            currentVitals.rhythm = "ELEVATED ARTERIAL PRESSURE";
            alarmBanner.setText("⚠ ELEVATED NIBP");
            alarmBanner.setStyle("-fx-background-color: rgba(239, 68, 68, 0.2); -fx-text-fill: #EF4444; -fx-font-size: 9px; -fx-font-weight: bold; -fx-padding: 2 8; -fx-background-radius: 10; -fx-border-color: #EF4444; -fx-border-radius: 10;");
            alarmLed.setFill(Color.web("#EF4444"));
        }

        updateDigitalReadouts();
        notifyVitalsChanged();
    }

    // =========================================================================
    // 5. RENDERING LOOP & DYNAMIC OSCILLOSCOPE SWEEP
    // =========================================================================
    private void initRenderingLoop() {
        redrawFullMedicalGrid();

        sweepTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isFrozen) return;

                double prevX = sweepX;
                sweepX += sweepSpeed;

                // Erase bar ahead of cursor and restore medical grid
                eraseAndRestoreGrid(sweepX, ERASE_BAR_WIDTH);

                // Render 4 continuous channels
                renderWaveforms(prevX, sweepX);

                // Wrap-around at right edge
                if (sweepX >= canvasWidth) {
                    sweepX = 0;
                    lastEcgY = -1;
                    lastAbpY = -1;
                    lastPlethY = -1;
                    lastTempY = -1;
                }
            }
        };
        sweepTimer.start();
    }

    private void renderWaveforms(double x0, double x1) {
        if (gc == null) return;

        double chHeight = canvasHeight / 4.0;
        double ecgBaseY = chHeight * 0.52;
        double abpBaseY = chHeight * 1.54;
        double plethBaseY = chHeight * 2.54;
        double tempBaseY = chHeight * 3.52;

        // Rate & Cycle period based on heart rate
        double cycleLength = (60.0 / Math.max(35, currentVitals.heartRate)) * 140.0;
        double phase = (x1 % cycleLength) / cycleLength; // 0.0 to 1.0

        // 1. ECG Signal Calculation (Lead II)
        double ecgOffset = calculateEcgSample(phase);
        double curEcgY = ecgBaseY - (ecgOffset * 25.0);

        // Flash Heart on R-Peak
        if (phase >= 0.35 && phase <= 0.38) {
            triggerHeartbeatVisual();
        }

        // Draw Channel 1: ECG (Neon Green)
        gc.setLineWidth(2.0);
        gc.setStroke(Color.web(COLOR_ECG));
        if (lastEcgY != -1 && x0 < x1) {
            gc.strokeLine(x0, lastEcgY, x1, curEcgY);
        }
        lastEcgY = curEcgY;

        // 2. Arterial Blood Pressure (ABP) Signal (LifeLink Pink / Coral)
        double abpPhase = (phase + 0.88) % 1.0;
        double abpOffset = calculateAbpSample(abpPhase);
        double abpAmplitude = (currentVitals.systolic - currentVitals.diastolic) * 0.30;
        double curAbpY = abpBaseY - (abpOffset * abpAmplitude);

        gc.setLineWidth(1.8);
        gc.setStroke(Color.web(COLOR_ABP));
        if (lastAbpY != -1 && x0 < x1) {
            gc.strokeLine(x0, lastAbpY, x1, curAbpY);
        }
        lastAbpY = curAbpY;

        // 3. PLETH Signal (Pulse / SpO2 Aqua Cyan)
        double plethPhase = (phase + 0.84) % 1.0;
        double plethOffset = calculatePlethSample(plethPhase);
        double curPlethY = plethBaseY - (plethOffset * 18.0);

        gc.setLineWidth(1.8);
        gc.setStroke(Color.web(COLOR_PLETH));
        if (lastPlethY != -1 && x0 < x1) {
            gc.strokeLine(x0, lastPlethY, x1, curPlethY);
        }
        lastPlethY = curPlethY;
        updatePulseBarMeter(Math.max(0.1, plethOffset));

        // 4. TEMP Signal (Continuous Thermal Telemetry Line, Amber Gold)
        phaseCounter += 0.02;
        double tempDrift = Math.sin(phaseCounter * 0.4) * 2.5 + Math.cos(phaseCounter * 0.15) * 1.5;
        double curTempY = tempBaseY + tempDrift;

        gc.setLineWidth(1.5);
        gc.setStroke(Color.web(COLOR_TEMP));
        if (lastTempY != -1 && x0 < x1) {
            gc.strokeLine(x0, lastTempY, x1, curTempY);
        }
        lastTempY = curTempY;
    }

    private double calculateEcgSample(double phase) {
        if (phase < 0.12) {
            return 0.0;
        } else if (phase < 0.22) {
            double pPhase = (phase - 0.12) / 0.10;
            return Math.sin(pPhase * Math.PI) * 0.18;
        } else if (phase < 0.32) {
            return 0.0;
        } else if (phase < 0.35) {
            double qPhase = (phase - 0.32) / 0.03;
            return -Math.sin(qPhase * Math.PI) * 0.15;
        } else if (phase < 0.40) {
            double rPhase = (phase - 0.35) / 0.05;
            return Math.sin(rPhase * Math.PI) * 1.0;
        } else if (phase < 0.44) {
            double sPhase = (phase - 0.40) / 0.04;
            return -Math.sin(sPhase * Math.PI) * 0.32;
        } else if (phase < 0.52) {
            return 0.0;
        } else if (phase < 0.68) {
            double tPhase = (phase - 0.52) / 0.16;
            return Math.sin(tPhase * Math.PI) * 0.32;
        } else {
            return (random.nextDouble() - 0.5) * 0.015;
        }
    }

    private double calculateAbpSample(double phase) {
        if (phase < 0.15) {
            double p = phase / 0.15;
            return Math.sin(p * Math.PI * 0.5);
        } else if (phase < 0.32) {
            double p = (phase - 0.15) / 0.17;
            return 1.0 - (p * 0.45);
        } else if (phase < 0.42) {
            double p = (phase - 0.32) / 0.10;
            return 0.55 + (Math.sin(p * Math.PI) * 0.15);
        } else {
            double p = (phase - 0.42) / 0.58;
            return 0.55 * Math.exp(-p * 2.2);
        }
    }

    private double calculatePlethSample(double phase) {
        if (phase < 0.20) {
            double p = phase / 0.20;
            return Math.sin(p * Math.PI * 0.5);
        } else if (phase < 0.40) {
            double p = (phase - 0.20) / 0.20;
            return 1.0 - (p * 0.35);
        } else if (phase < 0.55) {
            double p = (phase - 0.40) / 0.15;
            return 0.65 + (Math.sin(p * Math.PI) * 0.12);
        } else {
            double p = (phase - 0.55) / 0.45;
            return 0.65 * (1.0 - p);
        }
    }

    private void triggerHeartbeatVisual() {
        if (ecgBeatHeart != null) {
            ecgBeatHeart.setScaleX(1.3);
            ecgBeatHeart.setScaleY(1.3);
            ecgBeatHeart.setFill(Color.WHITE);

            if (!isAudioMuted) {
                new Thread(() -> {
                    try {
                        Toolkit.getDefaultToolkit().beep();
                    } catch (Exception ignored) {}
                }).start();
            }

            Platform.runLater(() -> {
                ecgBeatHeart.setScaleX(1.0);
                ecgBeatHeart.setScaleY(1.0);
                ecgBeatHeart.setFill(Color.web(COLOR_ECG));
            });
        }
    }

    // =========================================================================
    // 6. MEDICAL GRID BACKGROUND MANAGEMENT (CLEAN, NO BLUE BARCODE)
    // =========================================================================
    private void redrawFullMedicalGrid() {
        if (gc == null) return;

        // Clean dark medical background
        gc.setFill(Color.web(SCREEN_BG));
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        // Major subtle grid lines (40px horizontal pitch, 35px vertical pitch)
        gc.setLineWidth(0.8);
        gc.setStroke(Color.rgb(30, 41, 59, 0.45)); // Clean dark slate grid lines
        for (double x = 0; x < canvasWidth; x += 40) {
            gc.strokeLine(x, 0, x, canvasHeight);
        }
        for (double y = 0; y < canvasHeight; y += 35) {
            gc.strokeLine(0, y, canvasWidth, y);
        }

        // Distinct channel divider lines
        double chHeight = canvasHeight / 4.0;
        gc.setLineWidth(1.0);
        gc.setStroke(Color.rgb(51, 65, 85, 0.7));
        for (int i = 1; i < 4; i++) {
            gc.strokeLine(0, chHeight * i, canvasWidth, chHeight * i);
        }

        // Channel Labels on Canvas
        drawChannelCanvasHeader(gc, "ECG II", COLOR_ECG, 8, 15);
        drawChannelCanvasHeader(gc, "ART mmHg", COLOR_ABP, 8, (int)(chHeight + 15));
        drawChannelCanvasHeader(gc, "PLETH", COLOR_PLETH, 8, (int)(chHeight * 2 + 15));
        drawChannelCanvasHeader(gc, "TEMP °C", COLOR_TEMP, 8, (int)(chHeight * 3 + 15));
    }

    private void drawChannelCanvasHeader(GraphicsContext g, String text, String color, int x, int y) {
        g.setFont(Font.font("Consolas", FontWeight.BOLD, 9));
        g.setFill(Color.web(color, 0.85));
        g.fillText(text, x, y);
    }

    private void eraseAndRestoreGrid(double x, double width) {
        if (gc == null) return;

        double drawX = x;
        double drawW = Math.min(width, canvasWidth - drawX);
        if (drawW <= 0) return;

        // Clear erased strip
        gc.setFill(Color.web(SCREEN_BG));
        gc.fillRect(drawX, 0, drawW, canvasHeight);

        // Restore horizontal grid lines
        gc.setLineWidth(0.8);
        gc.setStroke(Color.rgb(30, 41, 59, 0.45));
        for (double gy = 0; gy < canvasHeight; gy += 35) {
            gc.strokeLine(drawX, gy, drawX + drawW, gy);
        }

        // Restore vertical grid lines that fall into the strip
        double startMajor = Math.floor(drawX / 40.0) * 40.0;
        for (double gx = startMajor; gx <= drawX + drawW; gx += 40) {
            if (gx >= drawX && gx <= drawX + drawW) {
                gc.strokeLine(gx, 0, gx, canvasHeight);
            }
        }

        // Restore channel dividers
        double chHeight = canvasHeight / 4.0;
        gc.setLineWidth(1.0);
        gc.setStroke(Color.rgb(51, 65, 85, 0.7));
        for (int i = 1; i < 4; i++) {
            gc.strokeLine(drawX, chHeight * i, drawX + drawW, chHeight * i);
        }

        // Draw dynamic sweep bar cursor (LifeLink Pink glowing lead edge)
        gc.setLineWidth(2.2);
        gc.setStroke(Color.web(NURSE_PINK, 0.90));
        gc.strokeLine(drawX, 0, drawX, canvasHeight);
    }

    // =========================================================================
    // 7. REAL-TIME PHYSIOLOGICAL DATA FLUCTUATIONS & TIMERS
    // =========================================================================
    // ThingSpeak Integration Configuration
    private static final boolean USE_THINGSPEAK = true; // Set to true to fetch data from ThingSpeak
    private static final String CHANNEL_ID = "3491368";
    private static final String THINGSPEAK_URL = "https://api.thingspeak.com/channels/3491368/feeds.json?api_key=PPESCI22CYQX179E&results=2";
    private static final int UPDATE_INTERVAL = 5000;

    private void initTelemetryFluctuations() {
        telemetryTicker = new Timeline(new KeyFrame(Duration.millis(UPDATE_INTERVAL), e -> {
            if (isFrozen) return;

            if (USE_THINGSPEAK) {
                new Thread(() -> {
                    try {
                        URL url = new URL(THINGSPEAK_URL);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(3000);
                        conn.setReadTimeout(3000);
                        
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }
                        in.close();
                        
                        JSONObject json = new JSONObject(response.toString());
                        JSONArray feeds = json.getJSONArray("feeds");
                        if (feeds.length() > 0) {
                            JSONObject latestFeed = feeds.getJSONObject(0);
                            
                            // Based on ThingSpeak Channel Metadata:
                            // field1: ECG (Heart Rate)
                            // field2: MAX30102 IR
                            // field3: MAX30102 RED
                            // field4: Temperature °C
                            // field5: Lead-off status
                            
                            String f1 = latestFeed.optString("field1");
                            String f4 = latestFeed.optString("field4");
                            
                            System.out.println("[ThingSpeak] Fetched Data - ECG: " + f1 + ", Temp: " + f4);
                            
                            int hr = f1.isEmpty() ? currentVitals.heartRate : (int)Double.parseDouble(f1);
                            double temp = f4.isEmpty() ? currentVitals.temperature : Double.parseDouble(f4);
                            
                            // We don't have BP or SpO2 in this channel, so keep them stable
                            int sys = currentVitals.systolic;
                            int dia = currentVitals.diastolic;
                            int spo2 = currentVitals.spo2;
                            
                            Platform.runLater(() -> {
                                currentVitals.heartRate = hr;
                                currentVitals.pulseRate = hr;
                                currentVitals.systolic = sys;
                                currentVitals.diastolic = dia;
                                currentVitals.spo2 = spo2;
                                currentVitals.temperature = temp;
                                
                                updateDigitalReadouts();
                                notifyVitalsChanged();
                            });
                        }
                    } catch (Exception ex) {
                        // Silently handle exception (e.g., rate limit, network issue, or API keys not set)
                        System.err.println("ThingSpeak fetch failed: " + ex.getMessage());
                    }
                }).start();
            } else {
                int hrDelta = random.nextInt(3) - 1;
                currentVitals.heartRate = Math.max(45, Math.min(160, currentVitals.heartRate + hrDelta));
                currentVitals.pulseRate = currentVitals.heartRate;

                if (random.nextDouble() < 0.3) {
                    int sysDelta = random.nextInt(3) - 1;
                    int diaDelta = random.nextInt(3) - 1;
                    currentVitals.systolic = Math.max(90, Math.min(190, currentVitals.systolic + sysDelta));
                    currentVitals.diastolic = Math.max(50, Math.min(115, currentVitals.diastolic + diaDelta));
                }

                if (random.nextDouble() < 0.2) {
                    double tempDelta = (random.nextDouble() - 0.5) * 0.1;
                    currentVitals.temperature = Math.round((currentVitals.temperature + tempDelta) * 10.0) / 10.0;
                }

                updateDigitalReadouts();
                notifyVitalsChanged();
            }
        }));
        telemetryTicker.setCycleCount(Timeline.INDEFINITE);
        telemetryTicker.play();
    }

    private void initClockTicker() {
        clockTicker = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (clockLabel != null) {
                clockLabel.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " IST");
            }

            if (isAlarmSilenced) {
                alarmSilenceSeconds--;
                if (alarmSilenceSeconds <= 0) {
                    isAlarmSilenced = false;
                    silenceBtn.setText("🔇 Silence");
                    silenceBtn.setStyle("-fx-background-color: #1E293B; -fx-text-fill: #F8FAFC; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 9; -fx-background-radius: 6; -fx-cursor: hand; -fx-border-color: #334155; -fx-border-radius: 6;");
                } else {
                    silenceBtn.setText("🔇 (" + alarmSilenceSeconds + "s)");
                }
            }
        }));
        clockTicker.setCycleCount(Timeline.INDEFINITE);
        clockTicker.play();
    }

    private void updateDigitalReadouts() {
        if (ecgValLabel != null) ecgValLabel.setText(String.valueOf(currentVitals.heartRate));
        if (abpValLabel != null) abpValLabel.setText(currentVitals.systolic + "/" + currentVitals.diastolic);
        if (abpMapLabel != null) {
            int mean = (int) Math.round((2.0 * currentVitals.diastolic + currentVitals.systolic) / 3.0);
            abpMapLabel.setText("(" + mean + ")");
        }
        if (plethValLabel != null) plethValLabel.setText(String.valueOf(currentVitals.pulseRate));
        if (spo2ValLabel != null) spo2ValLabel.setText(currentVitals.spo2 + "%");
        if (tempValLabel != null) tempValLabel.setText(String.format("%.1f", currentVitals.temperature));
        if (tempFahrLabel != null) {
            double fahr = (currentVitals.temperature * 9.0 / 5.0) + 32.0;
            tempFahrLabel.setText(String.format("%.1f°F", fahr));
        }
    }

    private void notifyVitalsChanged() {
        if (onVitalsUpdateListener != null) {
            onVitalsUpdateListener.accept(currentVitals);
        }
    }

    public VitalsSnapshot getCurrentVitals() {
        return currentVitals;
    }

    // =========================================================================
    // 8. CLEAN LIFECYCLE MANAGEMENT
    // =========================================================================
    public void stopAnimation() {
        if (sweepTimer != null) {
            sweepTimer.stop();
        }
        if (telemetryTicker != null) {
            telemetryTicker.stop();
        }
        if (clockTicker != null) {
            clockTicker.stop();
        }
    }

    public void startAnimation() {
        if (sweepTimer != null) {
            sweepTimer.start();
        }
        if (telemetryTicker != null) {
            telemetryTicker.play();
        }
        if (clockTicker != null) {
            clockTicker.play();
        }
    }
}