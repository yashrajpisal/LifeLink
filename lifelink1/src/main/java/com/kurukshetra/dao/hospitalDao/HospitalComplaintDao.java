package com.kurukshetra.dao.hospitalDao;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.hospitalModel.HospitalComplaintModel;

import java.util.HashMap;
import java.util.Map;

public class HospitalComplaintDao {

    private final Firestore db;

    public HospitalComplaintDao() {
        this.db = FirebaseConfig.getFirestore();
    }

    public String saveComplaint(HospitalComplaintModel complaint) throws Exception {

        if (db == null) {
            throw new Exception("Firebase database not connected.");
        }

        String complaintId = generateNextComplaintId();

        complaint.setComplaintId(complaintId);

        Map<String, Object> complaintData = new HashMap<>();

        complaintData.put("complaintId", complaint.getComplaintId());
        complaintData.put("hospitalEmail", complaint.getHospitalEmail());
        complaintData.put("hospitalName", complaint.getHospitalName());
        complaintData.put("reportedByRole", complaint.getReportedByRole());
        complaintData.put("ambulanceId", complaint.getAmbulanceId());
        complaintData.put("driverEmail", complaint.getDriverEmail());
        complaintData.put("nurseEmail", complaint.getNurseEmail());
        complaintData.put("complaintType", complaint.getComplaintType());
        complaintData.put("description", complaint.getDescription());
        complaintData.put("status", complaint.getStatus());
        complaintData.put("priority", complaint.getPriority());
        complaintData.put("timestamp", complaint.getTimestamp());

        db.collection("complainsByHospital")
                .document(complaintId)
                .set(complaintData)
                .get();

        return complaintId;
    }

    private synchronized String generateNextComplaintId() throws Exception {

        int maxSeqNumber = 1000;

        QuerySnapshot snap = db.collection("complainsByHospital")
                .get()
                .get();

        for (DocumentSnapshot doc : snap.getDocuments()) {

            String existingId = doc.getString("complaintId");

            if (existingId == null || existingId.isEmpty()) {
                existingId = doc.getId();
            }

            if (existingId != null && existingId.startsWith("COMP-H-")) {

                try {

                    String numPart = existingId
                            .substring("COMP-H-".length())
                            .trim();

                    int parsed = Integer.parseInt(numPart);

                    if (parsed > maxSeqNumber) {
                        maxSeqNumber = parsed;
                    }

                } catch (NumberFormatException ignored) {
                }
            }
        }

        return "COMP-H-" + (maxSeqNumber + 1);
    }

    public String getHospitalName(String hospitalEmail) throws Exception {

        if (db == null) {
            return null;
        }

        if (hospitalEmail == null || hospitalEmail.trim().isEmpty()) {
            return null;
        }

        DocumentSnapshot doc = db.collection("hospital")
                .document(hospitalEmail.trim())
                .get()
                .get();

        if (doc.exists() && doc.getString("hospitalName") != null) {
            return doc.getString("hospitalName");
        }

        return null;
    }

    public java.util.List<HospitalComplaintModel> getAllHospitalComplaints() throws Exception {

        java.util.List<HospitalComplaintModel> complaints = new java.util.ArrayList<>();

        if (db == null) {
            return complaints;
        }

        QuerySnapshot snap = db.collection("complainsByHospital")
                .get()
                .get();

        for (DocumentSnapshot doc : snap.getDocuments()) {

            HospitalComplaintModel complaint = new HospitalComplaintModel();

            complaint.setComplaintId(doc.getString("complaintId"));
            complaint.setHospitalEmail(doc.getString("hospitalEmail"));
            complaint.setHospitalName(doc.getString("hospitalName"));
            complaint.setReportedByRole(doc.getString("reportedByRole"));
            complaint.setAmbulanceId(doc.getString("ambulanceId"));
            complaint.setDriverEmail(doc.getString("driverEmail"));
            complaint.setNurseEmail(doc.getString("nurseEmail"));
            complaint.setComplaintType(doc.getString("complaintType"));
            complaint.setDescription(doc.getString("description"));
            complaint.setStatus(doc.getString("status"));
            complaint.setPriority(doc.getString("priority"));
            complaint.setTimestamp(doc.getTimestamp("timestamp"));

            complaints.add(complaint);
        }

        return complaints;
    }

    public void updateComplaintStatus(String complaintId, String status) throws Exception {

        if (db == null) {
            throw new Exception("Firebase database not connected.");
        }

        db.collection("complainsByHospital")
                .document(complaintId)
                .update("status", status)
                .get();
    }
}