package com.kurukshetra.dao;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.DriverUserModel;

import com.google.cloud.firestore.Firestore;

public class DriverUserDao {

    private Firestore db = FirebaseConfig.getFirestore();
    
    public void saveDriverUser(DriverUserModel familyUser){
        try{

            db.collection("driver").document(familyUser.getEmail()).set(familyUser);
          
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
