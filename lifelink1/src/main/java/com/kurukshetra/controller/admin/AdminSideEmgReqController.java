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

    // =========================================================================
    // 1b. GREEN CORRIDOR EMERGENCY DISPATCH
    // =========================================================================
    /**
     * Creates a Green Corridor emergency request with severity and ambulance
     * tracking.  Source (accident / pickup location) and destination (hospital)
     * are supplied dynamically by the UI — nothing is hardcoded.
     *
     * <p>A unique Trip ID is auto-generated, and status is set to
     * {@code PENDING_CLEARANCE} so Police dashboards can pick it up for
     * route clearance.
     *
     * @param patientId       the patient identifier
     * @param source          accident / pickup location name (from UI)
     * @param destination     hospital name (from UI)
     * @param nurseId         assigned nurse (nullable → defaults to "Unassigned")
     * @param driverId        assigned driver (nullable → defaults to "Unassigned")
     * @param severity        e.g. "CRITICAL", "HIGH", "MEDIUM", "LOW"
     * @param ambulanceStatus e.g. "DISPATCHED", "EN_ROUTE", "WAITING"
     * @return true when the request is successfully queued for persistence
     */
    public boolean dispatchGreenCorridorRequest(String patientId, String source,
                                                String destination, String nurseId,
                                                String driverId, String severity,
                                                String ambulanceStatus) {

        // --- input validation (source, destination, patientId are mandatory) ---
        if (patientId == null || patientId.trim().isEmpty() ||
            source == null || source.trim().isEmpty() ||
            destination == null || destination.trim().isEmpty()) {
            System.err.println("[AdminSideEmgReqController] Green Corridor Error: Required fields missing.");
            return false;
        }

        // Auto-generate a unique Trip ID with a GC (Green Corridor) prefix
        String tripId = "GC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Green Corridor requests always start as PENDING_CLEARANCE
        String status = "PENDING_CLEARANCE";

        Timestamp now = Timestamp.now();

        String finalSeverity = (severity != null && !severity.trim().isEmpty())
                ? severity.trim() : "HIGH";
        String finalAmbulanceStatus = (ambulanceStatus != null && !ambulanceStatus.trim().isEmpty())
                ? ambulanceStatus.trim() : "DISPATCHED";

        AdminSideEmgReqModel model = new AdminSideEmgReqModel(
            destination.trim(),
            source.trim(),
            patientId.trim(),
            status,
            nurseId != null ? nurseId.trim() : "Unassigned",
            driverId != null ? driverId.trim() : "Unassigned",
            now,
            tripId,
            finalSeverity,
            finalAmbulanceStatus
        );

        // Persist via the existing DAO save method
        emgReqDao.createEmergencyRequest(model);
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