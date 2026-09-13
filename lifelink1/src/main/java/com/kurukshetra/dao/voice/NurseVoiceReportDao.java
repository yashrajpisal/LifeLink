package com.kurukshetra.dao.voice;


// import com.example.config.FirebaseConfig;
// import com.example.modell.voice.NurseVoiceReportModel;
// import com.google.cloud.firestore.Firestore;

// public class NurseVoiceReportDao {
//     private final Firestore db = FirebaseConfig.getFirestore();
//     private static final String COLLECTION = "nurseVoiceReports";

//     public void saveVoiceReport(NurseVoiceReportModel report) {
//         try {
//             if (db != null && report != null) {
//                 db.collection(COLLECTION).document(report.getReportId()).set(report);
//                 System.out.println("[NurseVoiceReportDao] Saved voice report: " + report.getReportId());
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }

// package com.example.dao.voice;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.voice.NurseVoiceReportModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NurseVoiceReportDao {

    private static final String COLLECTION_NAME = "nurseVoiceReports";

    /**
     * Saves or updates a voice clinical report document in Firestore.
     */
    public boolean saveVoiceReport(NurseVoiceReportModel model) {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME)
                    .document(model.getReportId())
                    .set(model);
            
            future.get(); // Blocks until the write is acknowledged
            System.out.println("[NurseVoiceReportDao] Saved voice report: " + model.getReportId());
            return true;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("[NurseVoiceReportDao] Error saving voice report: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all voice clinical reports for a specific patient ID.
     */
    public List<NurseVoiceReportModel> getReportsByPatientId(String patientId) throws Exception {
        Firestore db = FirebaseConfig.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
                .whereEqualTo("patientId", patientId)
                .get();

        QuerySnapshot snapshot = future.get();
        List<NurseVoiceReportModel> list = new ArrayList<>();

        for (DocumentSnapshot doc : snapshot.getDocuments()) {
            NurseVoiceReportModel model = doc.toObject(NurseVoiceReportModel.class);
            if (model != null) {
                list.add(model);
            }
        }
        return list;
    }

    /**
     * Retrieves all voice clinical reports created by a specific nurse.
     */
    public List<NurseVoiceReportModel> getReportsByNurseEmail(String nurseEmail) throws Exception {
        Firestore db = FirebaseConfig.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
                .whereEqualTo("nurseEmail", nurseEmail)
                .get();

        QuerySnapshot snapshot = future.get();
        List<NurseVoiceReportModel> list = new ArrayList<>();

        for (DocumentSnapshot doc : snapshot.getDocuments()) {
            NurseVoiceReportModel model = doc.toObject(NurseVoiceReportModel.class);
            if (model != null) {
                list.add(model);
            }
        }
        return list;
    }

    /**
     * Retrieves a single voice clinical report by its unique report ID.
     */
    public NurseVoiceReportModel getReportById(String reportId) throws Exception {
        Firestore db = FirebaseConfig.getFirestore();
        DocumentSnapshot doc = db.collection(COLLECTION_NAME)
                .document(reportId)
                .get()
                .get();

        if (doc.exists()) {
            return doc.toObject(NurseVoiceReportModel.class);
        }
        return null;
    }

    /**
     * Deletes a voice clinical report document from Firestore.
     */
    public boolean deleteVoiceReport(String reportId) {
        try {
            Firestore db = FirebaseConfig.getFirestore();
            db.collection(COLLECTION_NAME).document(reportId).delete().get();
            System.out.println("[NurseVoiceReportDao] Deleted report: " + reportId);
            return true;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("[NurseVoiceReportDao] Error deleting report: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}