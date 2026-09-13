package com.kurukshetra.dao.hospitalDao;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.hospitalModel.HospitalResourceModel;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

public class HospitalResourceDao {

    private Firestore db = FirebaseConfig.getFirestore();

    public void saveHospitalResource(
            String hospitalId,
            HospitalResourceModel resource) {

        try {

            db.collection("hospital")
                    .document(hospitalId)
                    .collection("resources")
                    .document("current")
                    .set(resource);

            System.out.println(
                    "Hospital Resource Data Updated Successfully");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public HospitalResourceModel getHospitalResource(
            String hospitalId) {

        try {

            DocumentSnapshot document = db.collection("hospital")
                    .document(hospitalId)
                    .collection("resources")
                    .document("current")
                    .get()
                    .get();

            if (document.exists()) {

                return document.toObject(
                        HospitalResourceModel.class);
            }

            System.out.println(
                    "Hospital Resource Data Not Found");

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    public void listenToHospitalResource(
            String hospitalId,
            java.util.function.Consumer<HospitalResourceModel> listener) {

        db.collection("hospital")
                .document(hospitalId)
                .collection("resources")
                .document("current")
                .addSnapshotListener((snapshot, error) -> {

                    if (error != null) {
                        error.printStackTrace();
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {

                        HospitalResourceModel resource = snapshot.toObject(
                                HospitalResourceModel.class);

                        listener.accept(resource);
                    }
                });
    }

}