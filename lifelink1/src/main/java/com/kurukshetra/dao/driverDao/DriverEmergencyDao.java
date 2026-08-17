package com.kurukshetra.dao.driverDao;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.driverModel.DriverEmergencyModel;

import java.util.HashMap;
import java.util.Map;

public class DriverEmergencyDao {
    private final Firestore db;

    public DriverEmergencyDao() {
        this.db = FirebaseConfig.getFirestore();
    }

    public boolean saveDriverEmergency(DriverEmergencyModel emergency) {
        try {
            // Auto-generate unique document ID
            DocumentReference docRef = db.collection("driversideEmergency").document();
            emergency.setEmergencyId(docRef.getId());

            Map<String, Object> data = new HashMap<>();
            data.put("emergencyId", emergency.getEmergencyId());
            data.put("assignedDriverEmail", emergency.getAssignedDriverEmail());
            data.put("patientName", emergency.getPatientName());
            data.put("emergencyType", emergency.getEmergencyType());
            data.put("pickupLocation", emergency.getPickupLocation());
            data.put("googleMapUrl", emergency.getGoogleMapUrl());
            // data.put("priority", emergency.getPriority());
            // data.put("status", emergency.getStatus());
            data.put("timestamp", emergency.getTimestamp());

            docRef.set(data).get();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}