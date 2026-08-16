// package com.kurukshetra.view;

// import javafx.animation.FadeTransition;
// import javafx.animation.ParallelTransition;
// import javafx.animation.PauseTransition;
// import javafx.animation.SequentialTransition;
// import javafx.animation.TranslateTransition;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.StackPane;
// import javafx.scene.text.Text;

// public class Toast {

//     // type : "success" | "error" | "info"
//     public static void show(StackPane overlay, String message, String type) {

//         String bg = "#172B4D";
//         String icon = "ℹ";

//         if (type.equals("success")) {
//             bg = "#20B86A";
//             icon = "✓";
//         } else if (type.equals("error")) {
//             bg = "#D71920";
//             icon = "⚠";
//         }

//         Text iconText = new Text(icon);
//         iconText.setStyle("-fx-font-size : 13px; -fx-font-weight : bold; -fx-fill : white;");

//         Text messageText = new Text(message);
//         messageText.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : white;");

//         HBox toast = new HBox(10, iconText, messageText);
//         toast.setAlignment(Pos.CENTER_LEFT);
//         toast.setPadding(new Insets(12, 18, 12, 18));
//         toast.setStyle("-fx-background-color : " + bg + "; -fx-background-radius : 12; -fx-effect : dropshadow(gaussian, rgba(20,30,50,0.30), 14, 0, 0, 5);");
//         toast.setOpacity(0);
//         toast.setTranslateY(16);

//         StackPane.setAlignment(toast, Pos.BOTTOM_RIGHT);
//         StackPane.setMargin(toast, new Insets(0, 26, 26, 0));

//         overlay.getChildren().add(toast);

//         FadeTransition fadeIn = new FadeTransition(AppSettings.dur(220), toast);
//         fadeIn.setFromValue(0);
//         fadeIn.setToValue(1);

//         TranslateTransition slideIn = new TranslateTransition(AppSettings.dur(220), toast);
//         slideIn.setFromY(16);
//         slideIn.setToY(0);

//         ParallelTransition in = new ParallelTransition(fadeIn, slideIn);

//         PauseTransition hold = new PauseTransition(AppSettings.dur(2200));

//         FadeTransition fadeOut = new FadeTransition(AppSettings.dur(300), toast);
//         fadeOut.setFromValue(1);
//         fadeOut.setToValue(0);

//         SequentialTransition sequence = new SequentialTransition(in, hold, fadeOut);

//         sequence.setOnFinished(e -> overlay.getChildren().remove(toast));

//         sequence.play();
//     }
// }



package com.kurukshetra.view.nurse;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class NurseToast {

    // type : "success" | "error" | "info"
    public static void show(StackPane overlay, String message, String type) {

        String bg = "#172B4D";
        String icon = "ℹ";

        if (type.equals("success")) {
            bg = "#20B86A";
            icon = "✓";
        } else if (type.equals("error")) {
            bg = "#D71920";
            icon = "⚠";
        }

        Text iconText = new Text(icon);
        iconText.setStyle("-fx-font-size : 13px; -fx-font-weight : bold; -fx-fill : white;");

        Text messageText = new Text(message);
        messageText.setStyle("-fx-font-size : 12px; -fx-font-weight : bold; -fx-fill : white;");

        HBox toast = new HBox(10, iconText, messageText);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setPadding(new Insets(12, 18, 12, 18));
        toast.setStyle("-fx-background-color : " + bg + "; -fx-background-radius : 12; -fx-effect : dropshadow(gaussian, rgba(20,30,50,0.30), 14, 0, 0, 5);");
        toast.setOpacity(0);
        toast.setTranslateY(16);

        StackPane.setAlignment(toast, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(toast, new Insets(0, 26, 26, 0));

        overlay.getChildren().add(toast);

        FadeTransition fadeIn = new FadeTransition(NurseAppSettings.dur(220), toast);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        TranslateTransition slideIn = new TranslateTransition(NurseAppSettings.dur(220), toast);
        slideIn.setFromY(16);
        slideIn.setToY(0);

        ParallelTransition in = new ParallelTransition(fadeIn, slideIn);

        PauseTransition hold = new PauseTransition(NurseAppSettings.dur(2200));

        FadeTransition fadeOut = new FadeTransition(NurseAppSettings.dur(300), toast);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        SequentialTransition sequence = new SequentialTransition(in, hold, fadeOut);

        sequence.setOnFinished(e -> overlay.getChildren().remove(toast));

        sequence.play();
    }
}