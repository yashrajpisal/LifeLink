package com.kurukshetra;

import com.kurukshetra.view.Welcome;
import com.kurukshetra.view.FlashScreen;
import javafx.application.Application;
import javafx.application.Platform;


public class Main {
    public static void main(String[] args) {
        System.out.println("Shree Ganeshay Namhaa!!");
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "C:\\Users\\Asus\\Desktop\\JavaFx_Practical\\LifeLink\\lifelink1\\src\\main\\resources\\lifelinkFirebase.json");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("LifeLink application process terminated.");
        }));

        try {
            Application.launch(Welcome.class, args);
        } finally {
            Platform.exit();
            System.exit(0);
        }
    }
}