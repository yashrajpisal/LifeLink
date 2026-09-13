package com.kurukshetra.dao.admin;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.admin.AdminSideEmgReqModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class AdminSideEmgReqDao {

    private final Firestore db = FirebaseConfig.getFirestore();
    public static final String COLLECTION_NAME = "adminEmergencyRequests";

    // =========================================================================
    // 1. CREATE / SAVE METHOD
    // =========================================================================
    public void createEmergencyRequest(AdminSideEmgReqModel req) {
        if (db == null) {
            System.err.println("[AdminSideEmgReqDao] Firestore instance is null!");
            return;
        }
        try {
            System.out.println("[AdminSideEmgReqDao] Saving Trip to collection '" + COLLECTION_NAME + "': " + req.getTripID());
            Map<String, Object> data = new HashMap<>();
            data.put("tripID", req.getTripID());
            data.put("patID", req.getPatID());
            data.put("patId", req.getPatID());
            data.put("patientID", req.getPatID());
            data.put("patientId", req.getPatID());
            String src = (req.getSource() != null && !req.getSource().trim().isEmpty() && !req.getSource().equalsIgnoreCase("Emergency Location")) 
                    ? req.getSource().trim() 
                    : "Swargate";
            data.put("source", src);
            data.put("pickupLocation", src);
            data.put("destination", req.getDestination());
            data.put("status", req.getStatus() != null ? req.getStatus() : "PENDING");
            data.put("nurseID", req.getNurseID());
            data.put("driverID", req.getDriverID());
            data.put("ambulanceId", req.getAmbulanceId());
            data.put("latitude", req.getLatitude());
            data.put("longitude", req.getLongitude());
            data.put("destLat", req.getLatitude());
            data.put("destLng", req.getLongitude());
            data.put("timestamp", req.getTimestamp() != null ? req.getTimestamp() : Timestamp.now());

            db.collection(COLLECTION_NAME).document(req.getTripID()).set(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================================
    // 2. REAL-TIME SNAPSHOT LISTENER (Fail-Safe Manual Extraction)
    // =========================================================================
    public void listenToEmergencyRequests(Consumer<List<AdminSideEmgReqModel>> onUpdate) {
        if (db == null) {
            System.err.println("[AdminSideEmgReqDao] Cannot attach listener: Firestore DB is null.");
            return;
        }

        System.out.println("[AdminSideEmgReqDao] Listening to Firestore collection: " + COLLECTION_NAME);

        db.collection(COLLECTION_NAME).addSnapshotListener((snapshots, error) -> {
            if (error != null) {
                System.err.println("[AdminSideEmgReqDao] Firestore listen error: " + error.getMessage());
                return;
            }

            if (snapshots != null) {
                System.out.println("[AdminSideEmgReqDao] Live Snapshot received! Document count = " + snapshots.size());
                List<AdminSideEmgReqModel> currentList = new ArrayList<>();

                for (DocumentSnapshot doc : snapshots.getDocuments()) {
                    AdminSideEmgReqModel model = parseDocumentSafely(doc);
                    if (model != null) {
                        currentList.add(model);
                    }
                }

                onUpdate.accept(currentList);
            }
        });
    }

    // =========================================================================
    // 3. ONE-TIME FETCH ALL
    // =========================================================================
    public List<AdminSideEmgReqModel> fetchAllEmergencyRequests() {
        List<AdminSideEmgReqModel> list = new ArrayList<>();
        if (db == null) return list;

        try {
            ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                AdminSideEmgReqModel model = parseDocumentSafely(doc);
                if (model != null) {
                    list.add(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Safe parser: Handles both String & Timestamp data types without crashing
    private AdminSideEmgReqModel parseDocumentSafely(DocumentSnapshot doc) {
        try {
            AdminSideEmgReqModel model = new AdminSideEmgReqModel();
            model.setTripID(doc.getString("tripID") != null ? doc.getString("tripID") : doc.getId());
            String pat = doc.getString("patID");
            if (pat == null) pat = doc.getString("patientID");
            if (pat == null) pat = doc.getString("patId");
            if (pat == null) pat = doc.getString("patientId");
            model.setPatID(pat != null ? pat : "N/A");
            model.setSource(doc.getString("source") != null ? doc.getString("source") : "Hospital");
            model.setDestination(doc.getString("destination") != null ? doc.getString("destination") : "Destination");
            model.setStatus(doc.getString("status") != null ? doc.getString("status") : "PENDING");
            model.setNurseID(doc.getString("nurseID") != null ? doc.getString("nurseID") : "Unassigned");
            model.setDriverID(doc.getString("driverID") != null ? doc.getString("driverID") : "Unassigned");

            String ambId = doc.getString("ambulanceId");
            if (ambId == null) ambId = doc.getString("ambulanceID");
            model.setAmbulanceId(ambId != null ? ambId : "Unassigned");

            Double lat = doc.getDouble("latitude");
            if (lat == null) lat = doc.getDouble("destLat");
            if (lat == null && doc.get("latitude") instanceof String) {
                try { lat = Double.parseDouble(doc.getString("latitude")); } catch (Exception ignored) {}
            }
            model.setLatitude(lat);

            Double lng = doc.getDouble("longitude");
            if (lng == null) lng = doc.getDouble("destLng");
            if (lng == null && doc.get("longitude") instanceof String) {
                try { lng = Double.parseDouble(doc.getString("longitude")); } catch (Exception ignored) {}
            }
            model.setLongitude(lng);

            // Handle Timestamp safely whether stored as Timestamp, Date, or String
            Object tsObj = doc.get("timestamp");
            if (tsObj instanceof Timestamp) {
                model.setTimestamp((Timestamp) tsObj);
            } else if (tsObj instanceof java.util.Date) {
                model.setTimestamp(Timestamp.of((java.util.Date) tsObj));
            } else {
                model.setTimestamp(Timestamp.now());
            }

            return model;
        } catch (Exception e) {
            System.err.println("[AdminSideEmgReqDao] Error parsing doc ID: " + doc.getId() + " - " + e.getMessage());
            return null;
        }
    }
}