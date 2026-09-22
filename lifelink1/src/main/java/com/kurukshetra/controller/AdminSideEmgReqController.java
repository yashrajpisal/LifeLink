package com.kurukshetra.controller;

import com.kurukshetra.dao.AdminSideEmgReqDao;
import com.kurukshetra.model.AdminSideEmgReqModel;
import com.google.cloud.Timestamp;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class AdminSideEmgReqController {

    private final AdminSideEmgReqDao emgReqDao = new AdminSideEmgReqDao();

 
    public boolean dispatchEmergencyRequest(String tripId, String patientId, String source, 
                                           String destination, String nurseId, String driverId, 
                                           String status) {
        return dispatchEmergencyRequest(tripId, patientId, source, destination, nurseId, driverId, status, null);
    }

    public boolean dispatchEmergencyRequest(String tripId, String patientId, String source, 
                                           String destination, String nurseId, String driverId, 
                                           String status, String ambulanceId) {
        return dispatchEmergencyRequest(tripId, patientId, source, destination, nurseId, driverId, status, ambulanceId, null, null);
    }

    public boolean dispatchEmergencyRequest(String tripId, String patientId, String source, 
                                           String destination, String nurseId, String driverId, 
                                           String status, String ambulanceId, Double latitude, Double longitude) {
        
        if (patientId == null || patientId.trim().isEmpty() ||
            destination == null || destination.trim().isEmpty()) {
            System.err.println("[AdminSideEmgReqController] Error: Required fields missing.");
            return false;
        }

        String finalTripId = (tripId != null && !tripId.trim().isEmpty()) 
                ? tripId.trim() 
                : "TRIP-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        String finalStatus = (status != null && !status.trim().isEmpty()) ? status.trim() : "PENDING";
        String finalSource = (source != null && !source.trim().isEmpty() && !source.equalsIgnoreCase("Emergency Location")) ? source.trim() : "Swargate";
        Timestamp currentTimestamp = Timestamp.now();

        AdminSideEmgReqModel requestModel = new AdminSideEmgReqModel(
            destination.trim(),
            finalSource,
            patientId.trim(),
            finalStatus,
            nurseId != null ? nurseId.trim() : "Unassigned",
            driverId != null ? driverId.trim() : "Unassigned",
            currentTimestamp,
            finalTripId,
            ambulanceId != null ? ambulanceId.trim() : "Unassigned",
            latitude,
            longitude
        );

        emgReqDao.createEmergencyRequest(requestModel);
        return true;
    }

    /**
     * Direct save method for pre-constructed models.
     */
    public void createEmergencyRequest(AdminSideEmgReqModel model) {
        if (model != null) {
            emgReqDao.createEmergencyRequest(model);
        }
    }


     // Subscribes to live snapshot updates from the DAO.
     
    public void subscribeToLiveRequests(Consumer<List<AdminSideEmgReqModel>> callback) {
        emgReqDao.listenToEmergencyRequests(callback);
    }

  
    //Fetches all registered emergency requests at once.
     
    public List<AdminSideEmgReqModel> getAllRequests() {
        return emgReqDao.fetchAllEmergencyRequests();
    }
}