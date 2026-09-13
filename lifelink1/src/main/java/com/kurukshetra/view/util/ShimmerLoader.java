package com.kurukshetra.view.util;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class ShimmerLoader {

    private static final String SKELETON_BG = "#E2E8F0";
    private static final String SKELETON_DARK = "#CBD5E1";
    private static final String CARD_BG = "#FFFFFF";

    public static class ShimmerPane extends StackPane {
        private final TranslateTransition shimmerTransition;
        private final Rectangle highlight;
        private final Node skeletonContent;

        public ShimmerPane(Node skeletonContent, double width, double height) {
            this.skeletonContent = skeletonContent;
            setPrefSize(width, height);
            setMaxSize(width, height);
            setMinSize(width, height);
            setAlignment(Pos.CENTER);

            // Shimmer Highlight Rectangle with soft gradient wave
            double hWidth = Math.max(width * 0.75, 250);
            double hHeight = Math.max(height * 1.5, 300);
            highlight = new Rectangle(hWidth, hHeight);
            LinearGradient waveGradient = new LinearGradient(
                    0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                    new Stop(0.0, Color.color(1, 1, 1, 0.0)),
                    new Stop(0.4, Color.color(1, 1, 1, 0.35)),
                    new Stop(0.5, Color.color(1, 1, 1, 0.75)),
                    new Stop(0.6, Color.color(1, 1, 1, 0.35)),
                    new Stop(1.0, Color.color(1, 1, 1, 0.0))
            );
            highlight.setFill(waveGradient);
            highlight.setRotate(20);
            highlight.setMouseTransparent(true);

            // Container for skeleton and highlight
            StackPane wrapper = new StackPane(skeletonContent, highlight);
            wrapper.setAlignment(Pos.CENTER);

            // Clip bounds so highlight does not overflow
            Rectangle clip = new Rectangle();
            clip.setArcWidth(16);
            clip.setArcHeight(16);
            clip.widthProperty().bind(wrapper.widthProperty());
            clip.heightProperty().bind(wrapper.heightProperty());
            wrapper.setClip(clip);

            getChildren().add(wrapper);

            // Translate transition across the width
            double travelWidth = Math.max(width * 1.2, 400);
            shimmerTransition = new TranslateTransition(Duration.millis(1250), highlight);
            shimmerTransition.setFromX(-travelWidth);
            shimmerTransition.setToX(travelWidth);
            shimmerTransition.setCycleCount(TranslateTransition.INDEFINITE);
            shimmerTransition.setInterpolator(Interpolator.EASE_BOTH);
            shimmerTransition.play();
        }

        public void stop() {
            if (shimmerTransition != null) {
                shimmerTransition.stop();
            }
        }

        public void play() {
            if (shimmerTransition != null) {
                shimmerTransition.play();
            }
        }

        public Node getSkeletonContent() {
            return skeletonContent;
        }
    }

    public static Region createBar(double width, double height, double arc) {
        Region bar = new Region();
        bar.setPrefSize(width, height);
        bar.setMinSize(width, height);
        bar.setMaxSize(width, height);
        bar.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: %.1fpx;", SKELETON_BG, arc));
        return bar;
    }

    public static Circle createCircle(double radius) {
        Circle circle = new Circle(radius);
        circle.setFill(Color.web(SKELETON_BG));
        return circle;
    }

  
    public static ShimmerPane createCardSkeleton(double width, double height) {
        VBox cardBox = new VBox(12);
        cardBox.setPadding(new Insets(18));
        cardBox.setPrefSize(width, height);
        cardBox.setMaxSize(width, height);
        cardBox.setStyle(String.format(
                "-fx-background-color: %s; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-radius: 16px;",
                CARD_BG
        ));

        // Top Row: icon box + badge
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);
        Region iconPlaceholder = createBar(36, 36, 10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Region badgePlaceholder = createBar(55, 18, 9);
        topRow.getChildren().addAll(iconPlaceholder, spacer, badgePlaceholder);

        // Value Bar
        Region valueBar = createBar(width * 0.45, 28, 8);

        // Label Bar
        Region labelBar = createBar(width * 0.7, 14, 6);

        cardBox.getChildren().addAll(topRow, valueBar, labelBar);
        return new ShimmerPane(cardBox, width, height);
    }

    public static ShimmerPane createListSkeleton(int rowCount, double width, double rowHeight) {
        VBox listContainer = new VBox(10);
        listContainer.setPadding(new Insets(12));
        double totalHeight = (rowHeight + 10) * rowCount + 24;
        listContainer.setPrefSize(width, totalHeight);
        listContainer.setMaxSize(width, totalHeight);
        listContainer.setStyle(String.format(
                "-fx-background-color: %s; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-radius: 16px;",
                CARD_BG
        ));

        for (int i = 0; i < rowCount; i++) {
            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPrefHeight(rowHeight);
            row.setPadding(new Insets(8, 12, 8, 12));
            row.setStyle("-fx-background-color: #F8FAFC; -fx-background-radius: 10px;");

            Circle avatar = createCircle(rowHeight * 0.3);

            VBox textGroup = new VBox(6);
            Region line1 = createBar(width * 0.4, 13, 5);
            Region line2 = createBar(width * 0.25, 10, 4);
            textGroup.getChildren().addAll(line1, line2);

            Region rowSpacer = new Region();
            HBox.setHgrow(rowSpacer, Priority.ALWAYS);

            Region actionPill = createBar(60, 22, 11);

            row.getChildren().addAll(avatar, textGroup, rowSpacer, actionPill);
            listContainer.getChildren().add(row);
        }

        return new ShimmerPane(listContainer, width, totalHeight);
    }

    public static ShimmerPane createTableSkeleton(int rows, int cols, double width, double height) {
        VBox tableBox = new VBox(6);
        tableBox.setPadding(new Insets(14));
        tableBox.setPrefSize(width, height);
        tableBox.setMaxSize(width, height);
        tableBox.setStyle(String.format(
                "-fx-background-color: %s; -fx-background-radius: 16px; -fx-border-color: #E2E8F0; -fx-border-radius: 16px;",
                CARD_BG
        ));

        // Header Row
        HBox headerRow = new HBox(12);
        headerRow.setPadding(new Insets(8, 12, 8, 12));
        headerRow.setStyle("-fx-background-color: #F1F5F9; -fx-background-radius: 8px;");
        double colWidth = (width - 60) / cols;
        for (int c = 0; c < cols; c++) {
            headerRow.getChildren().add(createBar(colWidth, 14, 5));
        }
        tableBox.getChildren().add(headerRow);

        // Body Rows
        for (int r = 0; r < rows; r++) {
            HBox bodyRow = new HBox(12);
            bodyRow.setPadding(new Insets(8, 12, 8, 12));
            bodyRow.setStyle("-fx-background-color: #FAFCFE; -fx-background-radius: 8px;");
            for (int c = 0; c < cols; c++) {
                bodyRow.getChildren().add(createBar(colWidth * (0.6 + (c % 3) * 0.15), 12, 4));
            }
            tableBox.getChildren().add(bodyRow);
        }

        return new ShimmerPane(tableBox, width, height);
    }

    public static ShimmerPane createMemberPillsSkeleton(double width, double height) {
        HBox pillsBox = new HBox(10);
        pillsBox.setPrefSize(width, height);
        pillsBox.setMaxSize(width, height);
        pillsBox.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < 4; i++) {
            HBox pill = new HBox(8);
            pill.setAlignment(Pos.CENTER_LEFT);
            pill.setPadding(new Insets(6, 14, 6, 10));
            pill.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 20px; -fx-border-color: #E2E8F0; -fx-border-radius: 20px;");
            Circle dot = createCircle(6);
            Region text = createBar(70 + (i * 12), 12, 6);
            pill.getChildren().addAll(dot, text);
            pillsBox.getChildren().add(pill);
        }

        return new ShimmerPane(pillsBox, width, height);
    }

    public static ShimmerPane createPassCardSkeleton(double width, double height) {
        VBox card = new VBox(16);
        card.setPadding(new Insets(20));
        card.setPrefSize(width, height);
        card.setMaxSize(width, height);
        card.setStyle(String.format(
                "-fx-background-color: %s; -fx-background-radius: 18px; -fx-border-color: #E2E8F0; -fx-border-radius: 18px;",
                CARD_BG
        ));

        // Top user row
        HBox userRow = new HBox(14);
        userRow.setAlignment(Pos.CENTER_LEFT);
        Circle avatar = createCircle(26);
        VBox names = new VBox(6);
        names.getChildren().addAll(createBar(130, 16, 6), createBar(85, 12, 5));
        userRow.getChildren().addAll(avatar, names);

        // QR Placeholder Box
        HBox qrRow = new HBox();
        qrRow.setAlignment(Pos.CENTER);
        Region qrSquare = createBar(120, 120, 12);
        qrRow.getChildren().add(qrSquare);

        // Vitals / Details Rows
        VBox details = new VBox(8);
        details.getChildren().addAll(
                createBar(width * 0.75, 12, 5),
                createBar(width * 0.60, 12, 5),
                createBar(width * 0.85, 12, 5)
        );

        card.getChildren().addAll(userRow, qrRow, details);
        return new ShimmerPane(card, width, height);
    }

    public static void transition(Pane container, ShimmerPane shimmerPane, Node realContent) {
        if (shimmerPane != null) shimmerPane.stop();
        realContent.setOpacity(0);
        container.getChildren().clear();
        container.getChildren().add(realContent);

        FadeTransition ft = new FadeTransition(Duration.millis(300), realContent);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    public static ShimmerPane createPoliceAlertListSkeleton(double width, int count) {
        VBox container = new VBox(12);
        container.setPrefWidth(width);
        container.setMaxWidth(width);

        double totalHeight = 0;
        for (int i = 0; i < count; i++) {
            VBox card = new VBox(10);
            card.setPadding(new Insets(16));
            card.setStyle(String.format(
                    "-fx-background-color: %s; -fx-background-radius: 14px; -fx-border-color: #E5DBD2; -fx-border-radius: 14px;",
                    CARD_BG
            ));

            // Top Row
            HBox topRow = new HBox(8);
            topRow.setAlignment(Pos.CENTER_LEFT);
            Region badge = createBar(70, 22, 6);
            Region pat = createBar(60, 14, 4);
            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);
            Region time = createBar(55, 14, 4);
            topRow.getChildren().addAll(badge, pat, sp, time);

            // Middle Route Box
            VBox routeBox = new VBox(6);
            routeBox.setPadding(new Insets(8));
            routeBox.setStyle("-fx-background-color: #F8F4F0; -fx-background-radius: 8px;");
            Region from = createBar(width * 0.65, 12, 4);
            Region to = createBar(width * 0.50, 13, 4);
            routeBox.getChildren().addAll(from, to);

            // Bottom action row
            HBox btnRow = new HBox(8);
            Region btn1 = createBar(120, 28, 8);
            Region btn2 = createBar(80, 28, 8);
            btnRow.getChildren().addAll(btn1, btn2);

            card.getChildren().addAll(topRow, routeBox, btnRow);
            container.getChildren().add(card);
            totalHeight += 140;
        }

        return new ShimmerPane(container, width, totalHeight);
    }

    public static ShimmerPane createPoliceHistorySkeleton(double width, int groupCount) {
        VBox container = new VBox(20);
        container.setPrefWidth(width);
        container.setMaxWidth(width);

        double totalHeight = 0;
        for (int g = 0; g < groupCount; g++) {
            VBox groupCard = new VBox(14);
            groupCard.setPadding(new Insets(22));
            groupCard.setStyle(String.format(
                    "-fx-background-color: %s; -fx-background-radius: 18px; -fx-border-color: #E5DBD2; -fx-border-radius: 18px;",
                    CARD_BG
            ));

            HBox header = new HBox();
            header.setAlignment(Pos.CENTER_LEFT);
            Region dateBar = createBar(160, 22, 6);
            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);
            Region countBar = createBar(90, 16, 6);
            header.getChildren().addAll(dateBar, sp, countBar);
            groupCard.getChildren().add(header);

            for (int r = 0; r < 2; r++) {
                HBox row = new HBox(20);
                row.setAlignment(Pos.CENTER_LEFT);
                row.setPadding(new Insets(16));
                row.setStyle("-fx-background-color: #F8F4F0; -fx-background-radius: 14px; -fx-border-color: #E5DBD2; -fx-border-radius: 14px;");

                VBox col1 = new VBox(6);
                col1.getChildren().addAll(createBar(130, 15, 4), createBar(90, 12, 4));

                VBox col2 = new VBox(6);
                col2.getChildren().addAll(createBar(80, 15, 4), createBar(100, 12, 4));

                VBox col3 = new VBox(6);
                HBox.setHgrow(col3, Priority.ALWAYS);
                col3.getChildren().addAll(createBar(140, 13, 4), createBar(160, 13, 4));

                Region pill = createBar(85, 26, 13);

                row.getChildren().addAll(col1, col2, col3, pill);
                groupCard.getChildren().add(row);
            }

            container.getChildren().add(groupCard);
            totalHeight += 240;
        }

        return new ShimmerPane(container, width, totalHeight);
    }

    public static ShimmerPane createPoliceProfileSkeleton(double width) {
        VBox container = new VBox(25);
        container.setPrefWidth(width);
        container.setMaxWidth(width);

        double totalHeight = 0;
        for (int c = 0; c < 2; c++) {
            VBox card = new VBox(18);
            card.setPadding(new Insets(25));
            card.setStyle(String.format(
                    "-fx-background-color: %s; -fx-background-radius: 18px; -fx-border-color: #E5DBD2; -fx-border-radius: 18px;",
                    CARD_BG
            ));

            Region title = createBar(220, 24, 6);
            card.getChildren().add(title);

            for (int r = 0; r < 4; r++) {
                HBox row = new HBox(15);
                row.setAlignment(Pos.CENTER_LEFT);
                row.setPadding(new Insets(12));
                row.setStyle("-fx-background-color: #F8F4F0; -fx-background-radius: 12px;");

                Region label = createBar(140, 14, 4);
                Region val = createBar(width * 0.45, 14, 4);
                row.getChildren().addAll(label, val);
                card.getChildren().add(row);
            }

            container.getChildren().add(card);
            totalHeight += 260;
        }

        return new ShimmerPane(container, width, totalHeight);
    }

    public static ShimmerPane createMapSkeleton(double width, double height) {
        StackPane mapPane = new StackPane();
        mapPane.setPrefSize(width, height);
        mapPane.setStyle(
                "-fx-background-color: #F1F5F9; -fx-background-radius: 18px; -fx-border-color: #CBD5E1; -fx-border-radius: 18px;"
        );

        VBox centerBox = new VBox(14);
        centerBox.setAlignment(Pos.CENTER);
        Circle radarPulse = createCircle(32);
        Region line1 = createBar(200, 16, 6);
        Region line2 = createBar(140, 12, 4);
        centerBox.getChildren().addAll(radarPulse, line1, line2);

        mapPane.getChildren().add(centerBox);
        return new ShimmerPane(mapPane, width, height);
    }

    public static ShimmerPane createHospitalInboundListSkeleton(double width, int count) {
        VBox container = new VBox(12);
        container.setPrefWidth(width);
        container.setMaxWidth(width);

        double totalHeight = 0;
        for (int i = 0; i < count; i++) {
            VBox card = new VBox(10);
            card.setPadding(new Insets(14));
            card.setStyle(String.format(
                    "-fx-background-color: %s; -fx-background-radius: 12px; -fx-border-color: #E2E8F0; -fx-border-radius: 12px;",
                    CARD_BG
            ));

            // Top Header: Amb ID badge + ETA pill
            HBox topRow = new HBox(8);
            topRow.setAlignment(Pos.CENTER_LEFT);
            Region badge = createBar(85, 20, 6);
            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);
            Region etaPill = createBar(65, 18, 9);
            topRow.getChildren().addAll(badge, sp, etaPill);

            // Middle: Driver & triage row
            HBox midRow = new HBox(10);
            midRow.setAlignment(Pos.CENTER_LEFT);
            Circle avatar = createCircle(16);
            VBox info = new VBox(5);
            Region name = createBar(width * 0.45, 13, 4);
            Region sub = createBar(width * 0.30, 10, 3);
            info.getChildren().addAll(name, sub);
            midRow.getChildren().addAll(avatar, info);

            // Bottom: Action button & route pill
            HBox botRow = new HBox(8);
            botRow.setAlignment(Pos.CENTER_LEFT);
            Region route = createBar(width * 0.40, 12, 4);
            Region botSp = new Region();
            HBox.setHgrow(botSp, Priority.ALWAYS);
            Region btn = createBar(110, 26, 6);
            botRow.getChildren().addAll(route, botSp, btn);

            card.getChildren().addAll(topRow, midRow, botRow);
            container.getChildren().add(card);
            totalHeight += 120;
        }

        return new ShimmerPane(container, width, Math.max(totalHeight, 150));
    }

    public static ShimmerPane createDoctorGridSkeleton(double width, int count) {
        FlowPane flow = new FlowPane();
        flow.setHgap(16);
        flow.setVgap(16);
        flow.setPrefWidth(width);
        flow.setMaxWidth(width);

        double cardWidth = 320;
        double cardHeight = 175;

        for (int i = 0; i < count; i++) {
            VBox card = new VBox(12);
            card.setPadding(new Insets(16));
            card.setPrefSize(cardWidth, cardHeight);
            card.setMaxSize(cardWidth, cardHeight);
            card.setStyle(String.format(
                    "-fx-background-color: %s; -fx-background-radius: 14px; -fx-border-color: #E2E8F0; -fx-border-radius: 14px;",
                    CARD_BG
            ));

            // Top: Avatar + Doctor Info
            HBox topRow = new HBox(12);
            topRow.setAlignment(Pos.CENTER_LEFT);
            Circle avatar = createCircle(22);
            VBox docInfo = new VBox(5);
            Region docName = createBar(140, 15, 4);
            Region docSpec = createBar(100, 12, 3);
            docInfo.getChildren().addAll(docName, docSpec);
            topRow.getChildren().addAll(avatar, docInfo);

            // Middle: Status badge + Department
            HBox badgeRow = new HBox(8);
            badgeRow.setAlignment(Pos.CENTER_LEFT);
            Region statusPill = createBar(70, 18, 6);
            Region expPill = createBar(80, 18, 6);
            badgeRow.getChildren().addAll(statusPill, expPill);

            // Bottom: Buttons
            HBox btnRow = new HBox(8);
            Region btn1 = createBar(120, 28, 6);
            Region btn2 = createBar(80, 28, 6);
            btnRow.getChildren().addAll(btn1, btn2);

            card.getChildren().addAll(topRow, badgeRow, btnRow);
            flow.getChildren().add(card);
        }

        int rows = (int) Math.ceil((double) count / Math.max(1, (int)(width / (cardWidth + 16))));
        double totalHeight = rows * (cardHeight + 16);

        return new ShimmerPane(flow, width, Math.max(totalHeight, 380));
    }
}
