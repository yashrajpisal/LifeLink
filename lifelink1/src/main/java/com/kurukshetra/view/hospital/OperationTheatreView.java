package com.kurukshetra.view.hospital;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class OperationTheatreView extends VBox{
    // ─── Palette ──────────────────────────────────────────────────────────────
    private static final String PAGE_BG        = "#F0F4F8";
    private static final String CARD_BG        = "#FFFFFF";
    private static final String HEADER_BG      = "#FFFFFF";
    private static final String BORDER         = "#E2E8F0";
    private static final String TEXT_PRIMARY   = "#1A202C";
    private static final String TEXT_SECONDARY = "#64748B";
    private static final String TEXT_MUTED     = "#94A3B8";
    private static final String BLUE           = "#1565C0";
    private static final String BLUE_LIGHT     = "#EFF6FF";
    private static final String GREEN          = "#16A34A";
    private static final String GREEN_LIGHT    = "#F0FDF4";
    private static final String ORANGE         = "#D97706";
    private static final String ORANGE_LIGHT   = "#FFF7ED";
    private static final String RED            = "#DC2626";
    private static final String RED_LIGHT      = "#FEF2F2";

    // CONSTRUCTOR
    private VBox buildMainContent(){
        VBox wrapper = new VBox(0);
        wrapper.setStyle("-fx-background-color: " + PAGE_BG + ";");
        VBox.setVgrow(wrapper, Priority.ALWAYS);

        HBox topBar = buildTopBar();
        topBar.setStyle(
            "-fx-background-color: " + HEADER_BG + ";" +
            "-fx-border-color: transparent transparent " + BORDER + " transparent;" +
            "-fx-border-width: 0 0 1 0;"
        );

        ScrollPane scroll = new ScrollPane(buildBody());
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background: " + PAGE_BG + "; -fx-background-color: " + PAGE_BG + ";");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        wrapper.getChildren().addAll(topBar, scroll);
        return wrapper;
    }

    // TOP BAR
    private Node buildBody() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildBody'");
    }

    private HBox buildTopBar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildTopBar'");
    }
}
