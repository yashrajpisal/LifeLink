package com.kurukshetra.dao.family;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.FamilyUserModel;

import com.google.cloud.firestore.Firestore;

public class FamilyUserDao {

    private Firestore db = FirebaseConfig.getFirestore();
    
    public void saveFamilyUser(FamilyUserModel familyUser){
        try{

            System.out.println("Family Data Entry Successful");
            db.collection("family").document(familyUser.getEmail()).set(familyUser);
          
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
