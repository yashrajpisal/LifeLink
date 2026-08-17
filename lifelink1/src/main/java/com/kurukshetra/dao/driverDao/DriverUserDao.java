package com.kurukshetra.dao.driverDao;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.driverModel.DriverUserModel;
import com.google.cloud.firestore.Firestore;

public class DriverUserDao {

    private Firestore db = FirebaseConfig.getFirestore();
    
    public void saveDriverUser(DriverUserModel driverUser){
        try{

            System.out.println("Driver Data Entry Successful");
            db.collection("driver").document(driverUser.getEmail()).set(driverUser);
          
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
