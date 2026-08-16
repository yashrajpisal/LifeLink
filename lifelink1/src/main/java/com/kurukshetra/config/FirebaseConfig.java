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

            FileInputStream serviceAccount = new FileInputStream("C:\\Users\\Asus\\Desktop\\JavaFx_Practical\\LifeLink\\lifelink1\\src\\main\\resources\\LifeLink_Firebase.json");

            FirebaseOptions options =  FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

            FirebaseApp.initializeApp(options);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Firestore getFirestore(){
        return FirestoreClient.getFirestore();
    }
}
