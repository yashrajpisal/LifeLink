package com.kurukshetra.dao;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.FamilyUserModel;

import com.google.cloud.firestore.Firestore;

public class FamilyUserDao {

    private Firestore db = FirebaseConfig.getFirestore();
    
    public void saveFamilyUser(FamilyUserModel familyUser){
        try{

            db.collection("family").document(familyUser.getEmail()).set(familyUser);
          
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
