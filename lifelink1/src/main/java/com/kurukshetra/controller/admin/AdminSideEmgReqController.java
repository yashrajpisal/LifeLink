// package com.example.controller;

// import com.example.dao.AdminSideEmgReqDao;
// import com.example.model.AdminSideEmgReqModel;

// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.List;
// import java.util.UUID;
// import java.util.function.Consumer;

// public class AdminSideEmgReqController {

//     private final AdminSideEmgReqDao emgReqDao = new AdminSideEmgReqDao();

//     // =========================================================================
//     // 1. CREATE / DISPATCH EMERGENCY REQUEST
//     // =========================================================================
//     /**
//      * Validates and submits a new emergency dispatch request to Firestore.
//      */
//     public boolean dispatchEmergencyRequest(String tripId, String patientId, String source, 
//                                            String destination, String nurseId, String driverId, 
//                                            String status) {
        
//         // Basic input validation
//         if (patientId == null || patientId.trim().isEmpty() ||
//             source == null || source.trim().isEmpty() ||
//             destination == null || destination.trim().isEmpty()) {
//             System.err.println("[AdminSideEmgReqController] Error: Required fields missing.");
//             return false;
//         }

//         // Fallback auto-ID generation if trip ID is not provided
//         String finalTripId = (tripId != null && !tripId.trim().isEmpty()) 
//                 ? tripId.trim() 
//                 : "TRIP-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

//         String finalStatus = (status != null && !status.trim().isEmpty()) ? status.trim() : "PENDING";
//         String currentTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

//         AdminSideEmgReqModel requestModel = new AdminSideEmgReqModel(
//             destination.trim(),
//             source.trim(),
//             patientId.trim(),
//             finalStatus,
//             nurseId != null ? nurseId.trim() : "Unassigned",
//             driverId != null ? driverId.trim() : "Unassigned",
//             currentTimestamp,
//             finalTripId
//         );

//         emgReqDao.createEmergencyRequest(requestModel);
//         return true;
//     }

//     // =========================================================================
//     // 2. REAL-TIME STREAM SUBSCRIPTION
//     // =========================================================================
//     /**
//      * Subscribes to real-time updates for all emergency requests.
//      */
//     public void subscribeToLiveRequests(Consumer<List<AdminSideEmgReqModel>> callback) {
//         emgReqDao.listenToEmergencyRequests(callback);
//     }

//     // =========================================================================
//     // 3. ONE-TIME FETCH METHODS
//     // =========================================================================
//     /**
//      * Fetches a single emergency request by its unique Trip ID.
//      */
//     public AdminSideEmgReqModel getRequestById(String tripId) {
//         if (tripId == null || tripId.trim().isEmpty()) return null;
//         return emgReqDao.fetchRequestById(tripId.trim());
//     }

//     /**
//      * Fetches all registered emergency requests at once.
//      */
//     public List<AdminSideEmgReqModel> getAllRequests() {
//         return emgReqDao.fetchAllEmergencyRequests();
//     }
// }package com.example.controller;

package com.kurukshetra.controller.admin;

import com.kurukshetra.dao.admin.AdminSideEmgReqDao;
import com.kurukshetra.model.admin.AdminSideEmgReqModel;
import com.google.cloud.Timestamp;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class AdminSideEmgReqController {

    private final AdminSideEmgReqDao emgReqDao = new AdminSideEmgReqDao();

    // =========================================================================
    // 1. CREATE / DISPATCH EMERGENCY REQUEST
    // =========================================================================
    /**
     * Validates input, attaches native Firestore Timestamp, and persists via DAO.
     */
    public boolean dispatchEmergencyRequest(String tripId, String patientId, String source, 
                                           String destination, String nurseId, String driverId, 
                                           String status) {
        
        if (patientId == null || patientId.trim().isEmpty() ||
            source == null || source.trim().isEmpty() ||
            destination == null || destination.trim().isEmpty()) {
            System.err.println("[AdminSideEmgReqController] Error: Required fields missing.");
            return false;
        }

        String finalTripId = (tripId != null && !tripId.trim().isEmpty()) 
                ? tripId.trim() 
                : "TRIP-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        String finalStatus = (status != null && !status.trim().isEmpty()) ? status.trim() : "PENDING";
        Timestamp currentTimestamp = Timestamp.now();

        AdminSideEmgReqModel requestModel = new AdminSideEmgReqModel(
            destination.trim(),
            source.trim(),
            patientId.trim(),
            finalStatus,
            nurseId != null ? nurseId.trim() : "Unassigned",
            driverId != null ? driverId.trim() : "Unassigned",
            currentTimestamp,
            finalTripId
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

    // =========================================================================
    // 2. REAL-TIME SNAPSHOT SUBSCRIPTION
    // =========================================================================
    /**
     * Subscribes to live snapshot updates from the DAO.
     */
    public void subscribeToLiveRequests(Consumer<List<AdminSideEmgReqModel>> callback) {
        emgReqDao.listenToEmergencyRequests(callback);
    }

    // =========================================================================
    // 3. ONE-TIME FETCH ALL
    // =========================================================================
    /**
     * Fetches all registered emergency requests at once.
     */
    public List<AdminSideEmgReqModel> getAllRequests() {
        return emgReqDao.fetchAllEmergencyRequests();
    }
}