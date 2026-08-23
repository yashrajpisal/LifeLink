package com.kurukshetra.config;

import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseConfig {

    static {
        getFirebaseConfig();
    }

    private static void getFirebaseConfig(){
        try{
            java.io.InputStream serviceAccount = FirebaseConfig.class.getResourceAsStream("/lifelinkFirebase.json");
            
            if (serviceAccount == null) {
                System.err.println("[FirebaseConfig] ERROR: Could not find /lifelinkFirebase.json in classpath!");
                return;
            }

            System.out.println("[FirebaseConfig] Loading Firebase config from classpath...");

            FirebaseOptions options =  FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

            FirebaseApp.initializeApp(options);
            System.out.println("[FirebaseConfig] FirebaseApp successfully initialized!");

        } catch(Exception e){
            System.err.println("[FirebaseConfig] FAILED to initialize FirebaseApp:");
            e.printStackTrace();
        }
    }

    public static Firestore getFirestore(){
        return FirestoreClient.getFirestore();
    }
}
                                                                            