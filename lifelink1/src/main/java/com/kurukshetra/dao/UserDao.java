package com.kurukshetra.dao;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.UserModel;

import com.google.cloud.firestore.Firestore;

public class UserDao {

    private Firestore db = FirebaseConfig.getFirestore();
    
    public void saveHospitalUser(UserModel hospitalUser){
        try{

            db.collection("hospital").document(hospitalUser.getEmail()).set(hospitalUser);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
