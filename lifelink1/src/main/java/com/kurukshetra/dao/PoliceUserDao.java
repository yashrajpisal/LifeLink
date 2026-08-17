package com.kurukshetra.dao;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.PoliceUserModel;
import com.google.cloud.firestore.Firestore;

public class PoliceUserDao {

    private Firestore db = FirebaseConfig.getFirestore();
    
    public void savePoliceUser(PoliceUserModel policeUser){
        try{

            db.collection("police").document(policeUser.getEmail()).set(policeUser);
          
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
