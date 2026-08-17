package com.kurukshetra.dao;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.HospitalUserModel;

import com.google.cloud.firestore.Firestore;

public class HospitalUserDao {

    private Firestore db = FirebaseConfig.getFirestore();
    
    public void saveHospitalUser(HospitalUserModel hospitalUser){
        try{

            db.collection("hospital").document(hospitalUser.getEmail()).set(hospitalUser);
          
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
