package com.kurukshetra.view.hospital;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class OperationTheatre {

    public VBox getOperationTheatre() {

        // Main page
        VBox mainPage = new VBox(20);
        mainPage.setPadding(new Insets(24));
        mainPage.setStyle("-fx-background-color:#faf8ff;");

        // Page heading
        Label title = new Label("Theatre Operations");
        title.setStyle("-fx-font-size:32px;-fx-font-weight:700;-fx-text-fill:#191b23;");

        Label subtitle = new Label("Real-time surgical unit monitoring and resource allocation.");
        subtitle.setStyle("-fx-font-size:14px;-fx-text-fill:#434655;");

        VBox headingBox = new VBox(5, title, subtitle);

        Button filterButton = new Button("☰  Filter Status");
        filterButton.setStyle("-fx-background-color:#e7e7f3;-fx-text-fill:#191b23;-fx-font-size:13px;-fx-font-weight:600;-fx-background-radius:8px;-fx-padding:10px 16px;");

        Button newScheduleButton = new Button("+  New Schedule");
        newScheduleButton.setStyle("-fx-background-color:#004ac6;-fx-text-fill:white;-fx-font-size:13px;-fx-font-weight:600;-fx-background-radius:8px;-fx-padding:10px 16px;");

        HBox headingButtons = new HBox(8, filterButton, newScheduleButton);
        headingButtons.setAlignment(Pos.CENTER_RIGHT);

        HBox heading = new HBox();
        heading.getChildren().addAll(
                headingBox,
                headingButtons
        );
        HBox.setHgrow(headingBox, Priority.ALWAYS);

        // OT-1 card
        Label ot1Id = new Label("THEATRE ID");
        ot1Id.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label ot1 = new Label("OT-1");
        ot1.setStyle("-fx-font-size:24px;-fx-font-weight:700;-fx-text-fill:#191b23;");

        VBox ot1Title = new VBox(2, ot1Id, ot1);

        Label occupied = new Label("●  OCCUPIED");
        occupied.setStyle("-fx-background-color:#fee2e2;-fx-text-fill:#b91c1c;-fx-font-size:10px;-fx-font-weight:700;-fx-padding:6px 9px;-fx-background-radius:8px;");

        HBox ot1Header = new HBox();
        ot1Header.getChildren().addAll(
                ot1Title,
                occupied
        );
        ot1Header.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(ot1Title, Priority.ALWAYS);

        Label procedureTitle = new Label("CURRENT PROCEDURE");
        procedureTitle.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label procedure = new Label("Cardiovascular Bypass Graft");
        procedure.setStyle("-fx-font-size:18px;-fx-font-weight:600;-fx-text-fill:#191b23;");

        VBox procedureBox = new VBox(5, procedureTitle, procedure);

        Label surgeonTitle = new Label("SURGEON");
        surgeonTitle.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label surgeon = new Label("●  Dr. Robert Chen");
        surgeon.setStyle("-fx-font-size:13px;-fx-font-weight:600;-fx-text-fill:#191b23;");

        VBox surgeonBox = new VBox(5, surgeonTitle, surgeon);

        Label timeTitle = new Label("TIME REMAINING");
        timeTitle.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label time = new Label("42:13");
        time.setStyle("-fx-font-size:15px;-fx-font-weight:600;-fx-text-fill:#ba1a1a;");

        VBox timeBox = new VBox(5, timeTitle, time);

        HBox surgeonTime = new HBox(40);
        surgeonTime.getChildren().addAll(
                surgeonBox,
                timeBox
        );

        Label equipmentTitle1 = new Label("EQUIPMENT STATUS");
        equipmentTitle1.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label equipment1 = new Label("✓ Ventilator");
        equipment1.setStyle("-fx-font-size:13px;-fx-text-fill:#15803d;");

        Label equipment2 = new Label("✓ Bypass Machine");
        equipment2.setStyle("-fx-font-size:13px;-fx-text-fill:#15803d;");

        HBox equipmentRow1 = new HBox(30);
        equipmentRow1.getChildren().addAll(
                equipment1,
                equipment2
        );

        VBox equipmentBox1 = new VBox(8, equipmentTitle1, equipmentRow1);
        equipmentBox1.setPadding(new Insets(12));
        equipmentBox1.setStyle("-fx-background-color:#f3f3fe;-fx-border-color:#c3c6d7;-fx-border-style:dashed;-fx-border-radius:8px;-fx-background-radius:8px;");

        Button reserve1 = new Button("RESERVE OT");
        reserve1.setMaxWidth(Double.MAX_VALUE);
        reserve1.setStyle("-fx-background-color:#c3c6d7;-fx-text-fill:#434655;-fx-font-size:11px;-fx-font-weight:700;-fx-background-radius:8px;-fx-padding:10px;");

        Button release1 = new Button("RELEASE OT");
        release1.setMaxWidth(Double.MAX_VALUE);
        release1.setStyle("-fx-background-color:#004ac6;-fx-text-fill:white;-fx-font-size:11px;-fx-font-weight:700;-fx-background-radius:8px;-fx-padding:10px;");

        HBox ot1Buttons = new HBox(8);
        ot1Buttons.getChildren().addAll(
                reserve1,
                release1
        );
        HBox.setHgrow(reserve1, Priority.ALWAYS);
        HBox.setHgrow(release1, Priority.ALWAYS);

        VBox ot1Card = new VBox(16);
        ot1Card.setPadding(new Insets(20));
        ot1Card.setStyle("-fx-background-color:#ffffff;-fx-border-color:#c3c6d7;-fx-border-radius:12px;-fx-background-radius:12px;");
        ot1Card.getChildren().addAll(
                ot1Header,
                procedureBox,
                surgeonTime,
                equipmentBox1,
                ot1Buttons
        );

        // OT-2 card
        Label ot2Id = new Label("THEATRE ID");
        ot2Id.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label ot2 = new Label("OT-2");
        ot2.setStyle("-fx-font-size:24px;-fx-font-weight:700;-fx-text-fill:#191b23;");

        VBox ot2Title = new VBox(2, ot2Id, ot2);

        Label available = new Label("●  AVAILABLE");
        available.setStyle("-fx-background-color:#dcfce7;-fx-text-fill:#15803d;-fx-font-size:10px;-fx-font-weight:700;-fx-padding:6px 9px;-fx-background-radius:8px;");

        HBox ot2Header = new HBox();
        ot2Header.getChildren().addAll(
                ot2Title,
                available
        );
        ot2Header.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(ot2Title, Priority.ALWAYS);

        Label ready = new Label("Ready for immediate surgical entry.");
        ready.setStyle("-fx-font-size:14px;-fx-font-style:italic;-fx-text-fill:#434655;");

        VBox readyBox = new VBox();
        readyBox.setPrefHeight(44);
        readyBox.setAlignment(Pos.CENTER_LEFT);
        readyBox.getChildren().addAll(
                ready
        );

        Label nextSlotTitle = new Label("NEXT SLOT");
        nextSlotTitle.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label nextSlot = new Label("▣  Today, 14:30 PM");
        nextSlot.setStyle("-fx-font-size:13px;-fx-font-weight:600;-fx-text-fill:#191b23;");

        VBox nextSlotBox = new VBox(5, nextSlotTitle, nextSlot);

        Label prepTitle = new Label("PREP STATUS");
        prepTitle.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label prep = new Label("Sterilized");
        prep.setStyle("-fx-font-size:13px;-fx-font-weight:600;-fx-text-fill:#004ac6;");

        VBox prepBox = new VBox(5, prepTitle, prep);

        HBox nextPrep = new HBox(40);
        nextPrep.getChildren().addAll(
                nextSlotBox,
                prepBox
        );

        Label equipmentTitle2 = new Label("EQUIPMENT STATUS");
        equipmentTitle2.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label equipment3 = new Label("✓ Standby Lights");
        equipment3.setStyle("-fx-font-size:13px;-fx-text-fill:#15803d;");

        Label equipment4 = new Label("✓ Anesthesia Unit");
        equipment4.setStyle("-fx-font-size:13px;-fx-text-fill:#15803d;");

        HBox equipmentRow2 = new HBox(30);
        equipmentRow2.getChildren().addAll(
                equipment3,
                equipment4
        );

        VBox equipmentBox2 = new VBox(8, equipmentTitle2, equipmentRow2);
        equipmentBox2.setPadding(new Insets(12));
        equipmentBox2.setStyle("-fx-background-color:#f3f3fe;-fx-border-color:#c3c6d7;-fx-border-style:dashed;-fx-border-radius:8px;-fx-background-radius:8px;");

        Button reserve2 = new Button("RESERVE OT");
        reserve2.setMaxWidth(Double.MAX_VALUE);
        reserve2.setStyle("-fx-background-color:#004ac6;-fx-text-fill:white;-fx-font-size:11px;-fx-font-weight:700;-fx-background-radius:8px;-fx-padding:10px;");

        Button release2 = new Button("RELEASE OT");
        release2.setMaxWidth(Double.MAX_VALUE);
        release2.setDisable(true);
        release2.setStyle("-fx-background-color:#c3c6d7;-fx-text-fill:#434655;-fx-font-size:11px;-fx-font-weight:700;-fx-background-radius:8px;-fx-padding:10px;");

        HBox ot2Buttons = new HBox(8);
        ot2Buttons.getChildren().addAll(
                reserve2,
                release2
        );
        HBox.setHgrow(reserve2, Priority.ALWAYS);
        HBox.setHgrow(release2, Priority.ALWAYS);

        VBox ot2Card = new VBox(16);
        ot2Card.setPadding(new Insets(20));
        ot2Card.setStyle("-fx-background-color:#ffffff;-fx-border-color:#c3c6d7;-fx-border-radius:12px;-fx-background-radius:12px;");
        ot2Card.getChildren().addAll(
                ot2Header,
                readyBox,
                nextPrep,
                equipmentBox2,
                ot2Buttons
        );

        // OT-3 card
        Label ot3Id = new Label("THEATRE ID");
        ot3Id.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label ot3 = new Label("OT-3");
        ot3.setStyle("-fx-font-size:24px;-fx-font-weight:700;-fx-text-fill:#191b23;");

        VBox ot3Title = new VBox(2, ot3Id, ot3);

        Label maintenance = new Label("⚙  MAINTENANCE");
        maintenance.setStyle("-fx-background-color:#ffedd5;-fx-text-fill:#c2410c;-fx-font-size:10px;-fx-font-weight:700;-fx-padding:6px 9px;-fx-background-radius:8px;");

        HBox ot3Header = new HBox();
        ot3Header.getChildren().addAll(
                ot3Title,
                maintenance
        );
        ot3Header.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(ot3Title, Priority.ALWAYS);

        Label maintenanceText = new Label("Equipment calibration and HEPA filter replacement.");
        maintenanceText.setStyle("-fx-font-size:14px;-fx-text-fill:#434655;");
        maintenanceText.setWrapText(true);

        VBox maintenanceBox = new VBox();
        maintenanceBox.setPrefHeight(44);
        maintenanceBox.setAlignment(Pos.CENTER_LEFT);
        maintenanceBox.getChildren().addAll(
                maintenanceText
        );

        Label completionTitle = new Label("EST. COMPLETION");
        completionTitle.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label completion = new Label("◷  Tomorrow, 08:00 AM");
        completion.setStyle("-fx-font-size:13px;-fx-font-weight:600;-fx-text-fill:#191b23;");

        VBox completionBox = new VBox(5, completionTitle, completion);

        Label priorityTitle = new Label("PRIORITY");
        priorityTitle.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label priority = new Label("Routine");
        priority.setStyle("-fx-font-size:13px;-fx-font-weight:600;-fx-text-fill:#943700;");

        VBox priorityBox = new VBox(5, priorityTitle, priority);

        HBox completionPriority = new HBox(40);
        completionPriority.getChildren().addAll(
                completionBox,
                priorityBox
        );

        Label serviceTitle = new Label("SERVICE LOG");
        serviceTitle.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        Label service = new Label("⚙  Sys Update");
        service.setStyle("-fx-font-size:13px;-fx-text-fill:#434655;");

        Label progress = new Label("40% Complete");
        progress.setStyle("-fx-font-size:13px;-fx-font-weight:700;-fx-text-fill:#ea580c;");

        HBox serviceRow = new HBox();
        serviceRow.getChildren().addAll(
                service,
                progress
        );
        serviceRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(service, Priority.ALWAYS);

        VBox serviceBox = new VBox(8, serviceTitle, serviceRow);
        serviceBox.setPadding(new Insets(12));
        serviceBox.setStyle("-fx-background-color:#f3f3fe;-fx-border-color:#c3c6d7;-fx-border-style:dashed;-fx-border-radius:8px;-fx-background-radius:8px;");

        Button reserve3 = new Button("RESERVE OT");
        reserve3.setMaxWidth(Double.MAX_VALUE);
        reserve3.setDisable(true);
        reserve3.setStyle("-fx-background-color:#c3c6d7;-fx-text-fill:#434655;-fx-font-size:11px;-fx-font-weight:700;-fx-background-radius:8px;-fx-padding:10px;");

        Button logs = new Button("VIEW LOGS");
        logs.setMaxWidth(Double.MAX_VALUE);
        logs.setStyle("-fx-background-color:#ffffff;-fx-border-color:#c3c6d7;-fx-text-fill:#434655;-fx-font-size:11px;-fx-font-weight:700;-fx-background-radius:8px;-fx-padding:10px;");

        HBox ot3Buttons = new HBox(8);
        ot3Buttons.getChildren().addAll(
                reserve3,
                logs
        );
        HBox.setHgrow(reserve3, Priority.ALWAYS);
        HBox.setHgrow(logs, Priority.ALWAYS);

        VBox ot3Card = new VBox(16);
        ot3Card.setPadding(new Insets(20));
        ot3Card.setStyle("-fx-background-color:#ffffff;-fx-border-color:#c3c6d7;-fx-border-radius:12px;-fx-background-radius:12px;");
        ot3Card.getChildren().addAll(
                ot3Header,
                maintenanceBox,
                completionPriority,
                serviceBox,
                ot3Buttons
        );

        // Main OT cards
        HBox otCards = new HBox(16);
        otCards.getChildren().addAll(
                ot1Card,
                ot2Card,
                ot3Card
        );
        HBox.setHgrow(ot1Card, Priority.ALWAYS);
        HBox.setHgrow(ot2Card, Priority.ALWAYS);
        HBox.setHgrow(ot3Card, Priority.ALWAYS);

        // Auxiliary units heading
        Label auxiliaryTitle = new Label("Auxiliary Units & Day Care Surgery");
        auxiliaryTitle.setStyle("-fx-font-size:18px;-fx-font-weight:600;-fx-text-fill:#191b23;");

        Label free = new Label("●  6 FREE");
        free.setStyle("-fx-text-fill:#15803d;-fx-font-size:10px;-fx-font-weight:700;");

        Label busy = new Label("●  2 BUSY");
        busy.setStyle("-fx-text-fill:#b91c1c;-fx-font-size:10px;-fx-font-weight:700;");

        HBox auxiliaryStatus = new HBox(16);
        auxiliaryStatus.getChildren().addAll(
                free,
                busy
        );

        HBox auxiliaryHeader = new HBox();
        auxiliaryHeader.getChildren().addAll(
                auxiliaryTitle,
                auxiliaryStatus
        );
        auxiliaryHeader.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(auxiliaryTitle, Priority.ALWAYS);

        // Table header
        Label unitHeader = new Label("UNIT ID");
        Label specialtyHeader = new Label("SPECIALTY");
        Label statusHeader = new Label("STATUS");
        Label currentHeader = new Label("CURRENT / NEXT");
        Label technicianHeader = new Label("LEAD TECHNICIAN");
        Label actionHeader = new Label("ACTIONS");

        unitHeader.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");
        specialtyHeader.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");
        statusHeader.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");
        currentHeader.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");
        technicianHeader.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");
        actionHeader.setStyle("-fx-font-size:11px;-fx-font-weight:700;-fx-text-fill:#434655;");

        HBox tableHeader = new HBox();
        tableHeader.getChildren().addAll(
                unitHeader,
                specialtyHeader,
                statusHeader,
                currentHeader,
                technicianHeader,
                actionHeader
        );
        tableHeader.setPadding(new Insets(12, 20, 12, 20));
        tableHeader.setStyle("-fx-background-color:#f3f3fe;-fx-border-color:#c3c6d7;-fx-border-width:0 0 1 0;");

        // Auxiliary row 1
        Label unit4 = new Label("OT-4");
        unit4.setStyle("-fx-font-weight:700;-fx-text-fill:#004ac6;");

        Label spec4 = new Label("Orthopedic");

        Label status4 = new Label("AVAILABLE");
        status4.setStyle("-fx-background-color:#dcfce7;-fx-text-fill:#15803d;-fx-font-size:11px;-fx-font-weight:700;-fx-padding:5px 8px;-fx-background-radius:5px;");

        Label current4 = new Label("Laminectomy (Next: 15:00)");
        Label tech4 = new Label("Jane Doe");

        Button details4 = new Button("Details");
        details4.setStyle("-fx-background-color:transparent;-fx-text-fill:#004ac6;-fx-font-size:13px;");

        HBox row4 = new HBox();
        row4.getChildren().addAll(
                unit4,
                spec4,
                status4,
                current4,
                tech4,
                details4
        );
        row4.setAlignment(Pos.CENTER_LEFT);
        row4.setPadding(new Insets(14, 20, 14, 20));
        row4.setStyle("-fx-border-color:#c3c6d7;-fx-border-width:0 0 1 0;");

        // Auxiliary row 2
        Label unit5 = new Label("OT-5");
        unit5.setStyle("-fx-font-weight:700;-fx-text-fill:#004ac6;");

        Label spec5 = new Label("Pediatric");

        Label status5 = new Label("OCCUPIED");
        status5.setStyle("-fx-background-color:#fee2e2;-fx-text-fill:#b91c1c;-fx-font-size:11px;-fx-font-weight:700;-fx-padding:5px 8px;-fx-background-radius:5px;");

        Label current5 = new Label("Hernia Repair");
        Label tech5 = new Label("Mark Smith");

        Button details5 = new Button("Details");
        details5.setStyle("-fx-background-color:transparent;-fx-text-fill:#004ac6;-fx-font-size:13px;");

        HBox row5 = new HBox();
        row5.getChildren().addAll(
                unit5,
                spec5,
                status5,
                current5,
                tech5,
                details5
        );
        row5.setAlignment(Pos.CENTER_LEFT);
        row5.setPadding(new Insets(14, 20, 14, 20));
        row5.setStyle("-fx-border-color:#c3c6d7;-fx-border-width:0 0 1 0;");

        // Auxiliary row 3
        Label unit6 = new Label("OT-6");
        unit6.setStyle("-fx-font-weight:700;-fx-text-fill:#004ac6;");

        Label spec6 = new Label("Ophthalmology");

        Label status6 = new Label("AVAILABLE");
        status6.setStyle("-fx-background-color:#dcfce7;-fx-text-fill:#15803d;-fx-font-size:11px;-fx-font-weight:700;-fx-padding:5px 8px;-fx-background-radius:5px;");

        Label current6 = new Label("Cataract Surgery (Next: 16:15)");
        Label tech6 = new Label("Sarah Lee");

        Button details6 = new Button("Details");
        details6.setStyle("-fx-background-color:transparent;-fx-text-fill:#004ac6;-fx-font-size:13px;");

        HBox row6 = new HBox();
        row6.getChildren().addAll(
                unit6,
                spec6,
                status6,
                current6,
                tech6,
                details6
        );
        row6.setAlignment(Pos.CENTER_LEFT);
        row6.setPadding(new Insets(14, 20, 14, 20));

        // Auxiliary table
        VBox auxiliaryTable = new VBox();
        auxiliaryTable.setStyle("-fx-background-color:#ffffff;-fx-border-color:#c3c6d7;-fx-border-radius:12px;-fx-background-radius:12px;");
        auxiliaryTable.getChildren().addAll(
                auxiliaryHeader,
                tableHeader,
                row4,
                row5,
                row6
        );

        // Final page
        mainPage.getChildren().addAll(
                heading,
                otCards,
                auxiliaryTable
        );

        ScrollPane scrollPane = new ScrollPane(mainPage);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background:#faf8ff;-fx-background-color:#faf8ff;");

        VBox page = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return page;
    }
}

