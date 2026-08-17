package com.kurukshetra.controller.driverController;

import com.kurukshetra.dao.driverDao.DriverEmergencyDao;
import com.kurukshetra.model.driverModel.DriverEmergencyModel;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DriverEmergencyController {
    private final DriverEmergencyDao emergencyDao = new DriverEmergencyDao();

    public boolean dispatchEmergency(String driverEmail, String patientName, String emergencyType, 
                                     String pickupLocation, String priority) {
        // 1. Generate Google Maps Navigation URL
        String encodedLocation = URLEncoder.encode(pickupLocation, StandardCharsets.UTF_8);
        String googleMapUrl = "https://www.google.com/maps/dir/?api=1&destination=" + encodedLocation;

        // 2. Create and populate Model
        DriverEmergencyModel model = new DriverEmergencyModel(
            patientName,
            emergencyType,
            pickupLocation,
            driverEmail,
            googleMapUrl
        );

        // 3. Persist via DAO
        return emergencyDao.saveDriverEmergency(model);
    }
}