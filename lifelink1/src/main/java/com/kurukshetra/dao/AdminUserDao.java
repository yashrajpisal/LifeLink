package com.kurukshetra.dao;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.AdminUserModel;

import com.google.cloud.firestore.Firestore;

public class AdminUserDao {

    private Firestore db = FirebaseConfig.getFirestore();
    
    public void saveAdminUser(AdminUserModel adminUser){
        try{

            db.collection("admin").document(adminUser.getEmail()).set(adminUser);
          
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
