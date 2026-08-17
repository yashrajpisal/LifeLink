package com.kurukshetra.dao;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.NurseUserModel;

import com.google.cloud.firestore.Firestore;

public class NurseUserDao {

    private Firestore db = FirebaseConfig.getFirestore();
    
    public void saveNurseUser(NurseUserModel nurseUser){
        try{

            System.out.println("Nurse Data Entry Successful");
            db.collection("nurse").document(nurseUser.getEmail()).set(nurseUser);
          
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
