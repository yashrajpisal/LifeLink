
package com.kurukshetra.view.admin;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PoliceManagement {

    public VBox getPoliceManagement() {

        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(25));
        mainBox.setStyle("-fx-background-color: #f8f8ff;");

        // ---------------- HEADER ----------------

        Text heading = new Text("Police Management");
        heading.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text subHeading = new Text("Manage police units and emergency traffic coordination");
        subHeading.setStyle("-fx-font-size: 14px;" + "-fx-fill: #6b7280;");

        VBox headingBox = new VBox(5);
        headingBox.getChildren().addAll(
                heading,
                subHeading
        );

        Button addPoliceButton = new Button("+   Add Police Unit");
        addPoliceButton.setPrefWidth(150);
        addPoliceButton.setPrefHeight(40);
        addPoliceButton.setStyle("-fx-background-color: #004ac6;" + "-fx-text-fill: white;" + "-fx-font-size: 13px;" + "-fx-font-weight: bold;" + "-fx-background-radius: 8px;");

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        HBox header = new HBox(
                headingBox,
                headerSpacer,
                addPoliceButton
        );

        header.setAlignment(Pos.CENTER_LEFT);

        // ---------------- SUMMARY CARDS ----------------

        HBox cardsRow = new HBox(15);

        VBox totalPoliceCard = new VBox(7);
        totalPoliceCard.setPadding(new Insets(18));
        totalPoliceCard.setPrefHeight(120);
        totalPoliceCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text totalPoliceTitle = new Text("Total Police Units");
        totalPoliceTitle.setStyle("-fx-font-size: 13px;" + "-fx-fill: #6b7280;" + "-fx-font-weight: bold;");

        Text totalPoliceValue = new Text("42");
        totalPoliceValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text totalPoliceInfo = new Text("Registered units");
        totalPoliceInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        totalPoliceCard.getChildren().addAll(
                totalPoliceTitle,
                totalPoliceValue,
                totalPoliceInfo
        );

        HBox.setHgrow(totalPoliceCard, Priority.ALWAYS);

        VBox activePoliceCard = new VBox(7);
        activePoliceCard.setPadding(new Insets(18));
        activePoliceCard.setPrefHeight(120);
        activePoliceCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text activePoliceTitle = new Text("Active Units");
        activePoliceTitle.setStyle("-fx-font-size: 13px;" + "-fx-fill: #6b7280;" + "-fx-font-weight: bold;");

        Text activePoliceValue = new Text("28");
        activePoliceValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text activePoliceInfo = new Text("Currently deployed");
        activePoliceInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #16a34a;");

        activePoliceCard.getChildren().addAll(
                activePoliceTitle,
                activePoliceValue,
                activePoliceInfo
        );

        HBox.setHgrow(activePoliceCard, Priority.ALWAYS);

        VBox availablePoliceCard = new VBox(7);
        availablePoliceCard.setPadding(new Insets(18));
        availablePoliceCard.setPrefHeight(120);
        availablePoliceCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text availablePoliceTitle = new Text("Available Units");
        availablePoliceTitle.setStyle("-fx-font-size: 13px;" + "-fx-fill: #6b7280;" + "-fx-font-weight: bold;");

        Text availablePoliceValue = new Text("14");
        availablePoliceValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text availablePoliceInfo = new Text("Ready for deployment");
        availablePoliceInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #2563eb;");

        availablePoliceCard.getChildren().addAll(
                availablePoliceTitle,
                availablePoliceValue,
                availablePoliceInfo
        );

        HBox.setHgrow(availablePoliceCard, Priority.ALWAYS);

        VBox trafficCard = new VBox(7);
        trafficCard.setPadding(new Insets(18));
        trafficCard.setPrefHeight(120);
        trafficCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 12px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 12px;");

        Text trafficTitle = new Text("Traffic Support");
        trafficTitle.setStyle("-fx-font-size: 13px;" + "-fx-fill: #6b7280;" + "-fx-font-weight: bold;");

        Text trafficValue = new Text("9");
        trafficValue.setStyle("-fx-font-size: 28px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text trafficInfo = new Text("Units managing routes");
        trafficInfo.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        trafficCard.getChildren().addAll(
                trafficTitle,
                trafficValue,
                trafficInfo
        );

        HBox.setHgrow(trafficCard, Priority.ALWAYS);

        cardsRow.getChildren().addAll(
                totalPoliceCard,
                activePoliceCard,
                availablePoliceCard,
                trafficCard
        );

        // ---------------- POLICE UNIT LIST ----------------

        VBox policeListCard = new VBox(15);
        policeListCard.setPadding(new Insets(20));
        policeListCard.setStyle("-fx-background-color: #ffffff;" + "-fx-background-radius: 15px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 15px;");

        Text listTitle = new Text("Police Units");
        listTitle.setStyle("-fx-font-size: 19px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        // Unit 1

        HBox police1 = new HBox(15);
        police1.setPadding(new Insets(15));
        police1.setAlignment(Pos.CENTER_LEFT);
        police1.setStyle("-fx-background-color: #f9fafb;" + "-fx-background-radius: 10px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 10px;");

        VBox police1Info = new VBox(5);

        Text police1Name = new Text("Police Unit P-101");
        police1Name.setStyle("-fx-font-size: 14px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text police1Location = new Text("Shivajinagar Zone");
        police1Location.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        police1Info.getChildren().addAll(
                police1Name,
                police1Location
        );

        Region police1Spacer = new Region();
        HBox.setHgrow(police1Spacer, Priority.ALWAYS);

        Text police1Status = new Text("ACTIVE");
        police1Status.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #16a34a;");

        Button police1Button = new Button("View");
        police1Button.setPrefWidth(70);
        police1Button.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 11px;" + "-fx-background-radius: 7px;");

        police1.getChildren().addAll(
                police1Info,
                police1Spacer,
                police1Status,
                police1Button
        );

        // Unit 2

        HBox police2 = new HBox(15);
        police2.setPadding(new Insets(15));
        police2.setAlignment(Pos.CENTER_LEFT);
        police2.setStyle("-fx-background-color: #f9fafb;" + "-fx-background-radius: 10px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 10px;");

        VBox police2Info = new VBox(5);

        Text police2Name = new Text("Police Unit P-102");
        police2Name.setStyle("-fx-font-size: 14px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text police2Location = new Text("Kothrud Zone");
        police2Location.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        police2Info.getChildren().addAll(
                police2Name,
                police2Location
        );

        Region police2Spacer = new Region();
        HBox.setHgrow(police2Spacer, Priority.ALWAYS);

        Text police2Status = new Text("AVAILABLE");
        police2Status.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #2563eb;");

        Button police2Button = new Button("View");
        police2Button.setPrefWidth(70);
        police2Button.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 11px;" + "-fx-background-radius: 7px;");

        police2.getChildren().addAll(
                police2Info,
                police2Spacer,
                police2Status,
                police2Button
        );

        // Unit 3

        HBox police3 = new HBox(15);
        police3.setPadding(new Insets(15));
        police3.setAlignment(Pos.CENTER_LEFT);
        police3.setStyle("-fx-background-color: #f9fafb;" + "-fx-background-radius: 10px;" + "-fx-border-color: #e5e7eb;" + "-fx-border-radius: 10px;");

        VBox police3Info = new VBox(5);

        Text police3Name = new Text("Police Unit P-103");
        police3Name.setStyle("-fx-font-size: 14px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        Text police3Location = new Text("Hadapsar Zone");
        police3Location.setStyle("-fx-font-size: 11px;" + "-fx-fill: #6b7280;");

        police3Info.getChildren().addAll(
                police3Name,
                police3Location
        );

        Region police3Spacer = new Region();
        HBox.setHgrow(police3Spacer, Priority.ALWAYS);

        Text police3Status = new Text("ON DUTY");
        police3Status.setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;" + "-fx-fill: #16a34a;");

        Button police3Button = new Button("View");
        police3Button.setPrefWidth(70);
        police3Button.setStyle("-fx-background-color: #e9edff;" + "-fx-text-fill: #3949ab;" + "-fx-font-size: 11px;" + "-fx-background-radius: 7px;");

        police3.getChildren().addAll(
                police3Info,
                police3Spacer,
                police3Status,
                police3Button
        );

        policeListCard.getChildren().addAll(
                listTitle,
                police1,
                police2,
                police3
        );

        // ---------------- QUICK ACTIONS ----------------

        VBox quickActions = new VBox(12);

        Text quickTitle = new Text("Quick Actions");
        quickTitle.setStyle("-fx-font-size: 19px;" + "-fx-font-weight: bold;" + "-fx-fill: #111827;");

        HBox actionButtons = new HBox(12);

        Button dispatchButton = new Button("Dispatch Police Unit");
        dispatchButton.setPrefWidth(180);
        dispatchButton.setPrefHeight(50);
        dispatchButton.setStyle("-fx-background-color: #ffffff;" + "-fx-text-fill: #374151;" + "-fx-border-color: #d1d5db;" + "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 13px;");

        Button trafficButton = new Button("Manage Traffic");
        trafficButton.setPrefWidth(170);
        trafficButton.setPrefHeight(50);
        trafficButton.setStyle("-fx-background-color: #ffffff;" + "-fx-text-fill: #374151;" + "-fx-border-color: #d1d5db;" + "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 13px;");

        Button emergencyRouteButton = new Button("Clear Emergency Route");
        emergencyRouteButton.setPrefWidth(190);
        emergencyRouteButton.setPrefHeight(50);
        emergencyRouteButton.setStyle("-fx-background-color: #ffffff;" + "-fx-text-fill: #374151;" + "-fx-border-color: #d1d5db;" + "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 13px;");

        actionButtons.getChildren().addAll(
                dispatchButton,
                trafficButton,
                emergencyRouteButton
        );

        quickActions.getChildren().addAll(
                quickTitle,
                actionButtons
        );

        // ---------------- MAIN BOX ----------------

        mainBox.getChildren().addAll(
                header,
                cardsRow,
                policeListCard,
                quickActions
        );

        ScrollPane scrollPane = new ScrollPane(mainBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;" + "-fx-background: transparent;");

        VBox result = new VBox(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return result;
    }
}