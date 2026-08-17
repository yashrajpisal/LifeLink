package com.kurukshetra.config;

import java.io.InputStream;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class Firebase {

    public static Firestore db;

    private static synchronized void initFirebase() {
        try {
            InputStream serviceAccount = Firebase.class.getResourceAsStream("/firebase_chat.json");
            if (serviceAccount == null) {
                try {
                    serviceAccount = new java.io.FileInputStream("src/main/resources/firebase_chat.json");
                } catch (Exception ex) {
                    try {
                        serviceAccount = new java.io.FileInputStream("demo/src/main/resources/firebase_chat.json");
                    } catch (Exception ex2) {
                        serviceAccount = new java.io.FileInputStream("demo\\src\\main\\resources\\firebase_chat.json");
                    }
                }
            }
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
            }
            if (db == null) {
                db = FirestoreClient.getFirestore();
            }
        } catch (Exception e) {
            System.out.println("Error in loading .json : " + e.getMessage());
        }
    }

    public static Firestore getDB(){
        if(db == null){
            initFirebase();
        }
        return db;
    }
}