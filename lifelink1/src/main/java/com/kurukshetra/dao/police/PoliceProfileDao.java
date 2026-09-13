package com.kurukshetra.dao.police;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.controller.police.PoliceProfileController;
import com.kurukshetra.model.police.PoliceProfileModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PoliceProfileDao {

    private static final String COLLECTION_NAME = "police";

    public CompletableFuture<Boolean> savePoliceProfile(PoliceProfileModel profile) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                String targetEmail = (profile != null && profile.getEmail() != null && !profile.getEmail().trim().isEmpty())
                        ? profile.getEmail().trim()
                        : PoliceProfileController.getEffectiveEmail();

                if (db == null || targetEmail == null || targetEmail.trim().isEmpty()) {
                    System.err.println("[PoliceProfileDao] Error: Firestore db is null or targetEmail is empty: " + targetEmail);
                    future.complete(false);
                    return;
                }

                Map<String, Object> data = new HashMap<>();
                data.put("email", targetEmail);
                data.put("controlRoom", (profile != null && profile.getControlRoom() != null) ? profile.getControlRoom() : "Pune City Emergency Control Room");
                data.put("location", (profile != null && profile.getLocation() != null) ? profile.getLocation() : "Pune Police Commissionerate, Shivajinagar, Pune");
                data.put("policeDistrict", (profile != null && profile.getPoliceDistrict() != null) ? profile.getPoliceDistrict() : "Pune City");
                data.put("responseZone", (profile != null && profile.getResponseZone() != null) ? profile.getResponseZone() : "Pune Central");
                data.put("officer", (profile != null && profile.getOfficer() != null) ? profile.getOfficer() : "Control Room In-charge");
                data.put("role", (profile != null && profile.getRole() != null) ? profile.getRole() : "Emergency Coordination Officer");
                data.put("contact", (profile != null && profile.getContact() != null) ? profile.getContact() : "Police Control Room Emergency Line");
                data.put("shift", (profile != null && profile.getShift() != null) ? profile.getShift() : "08:00 AM – 04:00 PM");
                data.put("lastUpdated", Timestamp.now());

                DocumentReference docRef = db.collection(COLLECTION_NAME).document(targetEmail);
                docRef.set(data, SetOptions.merge()).get();

                System.out.println("[PoliceProfileDao] Profile saved successfully to Firestore under 'police/" + targetEmail + "'");
                future.complete(true);
            } catch (Exception e) {
                System.err.println("[PoliceProfileDao] Exception saving profile: " + e.getMessage());
                e.printStackTrace();
                future.complete(false);
            }
        }).start();

        return future;
    }

    public CompletableFuture<PoliceProfileModel> getPoliceProfile(String email) {
        CompletableFuture<PoliceProfileModel> future = new CompletableFuture<>();

        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                String targetEmail = (email != null && !email.trim().isEmpty())
                        ? email.trim()
                        : PoliceProfileController.getEffectiveEmail();

                if (db == null || targetEmail == null || targetEmail.trim().isEmpty()) {
                    System.err.println("[PoliceProfileDao] Error: Firestore db is null or targetEmail is empty: " + targetEmail);
                    future.complete(null);
                    return;
                }

                DocumentSnapshot snapshot = db.collection(COLLECTION_NAME).document(targetEmail).get().get();
                if (snapshot.exists()) {
                    PoliceProfileModel model = new PoliceProfileModel();
                    model.setEmail(targetEmail);
                    model.setControlRoom(snapshot.getString("controlRoom"));
                    model.setLocation(snapshot.getString("location"));
                    model.setPoliceDistrict(snapshot.getString("policeDistrict"));
                    model.setResponseZone(snapshot.getString("responseZone"));
                    model.setOfficer(snapshot.getString("officer"));
                    model.setRole(snapshot.getString("role"));
                    model.setContact(snapshot.getString("contact"));
                    model.setShift(snapshot.getString("shift"));
                    model.setLastUpdated(snapshot.getTimestamp("lastUpdated"));

                    System.out.println("[PoliceProfileDao] Loaded existing profile from Firestore: 'police/" + targetEmail + "'");
                    future.complete(model);
                } else {
                    System.out.println("[PoliceProfileDao] No existing document at 'police/" + targetEmail + "'");
                    future.complete(null);
                }
            } catch (Exception e) {
                System.err.println("[PoliceProfileDao] Exception loading profile: " + e.getMessage());
                e.printStackTrace();
                future.complete(null);
            }
        }).start();

        return future;
    }
}