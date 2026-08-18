package com.kurukshetra.dao.hospital;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.HospitalUserModel;

import com.google.cloud.firestore.Firestore;

public class HospitalUserDao {

    private Firestore db = FirebaseConfig.getFirestore();
    
    public void saveHospitalUser(HospitalUserModel hospitalUser){
        try{
          
            System.out.println("Hospital Data Entry Successful");
            db.collection("hospital").document(hospitalUser.getEmail()).set(hospitalUser);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
