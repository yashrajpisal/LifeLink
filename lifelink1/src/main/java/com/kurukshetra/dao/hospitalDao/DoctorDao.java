package com.kurukshetra.dao.hospitalDao;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.hospitalModel.DoctorModel;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DoctorDao {

    private Firestore db = FirebaseConfig.getFirestore();

    public void saveDoctor(
            String hospitalId,
            DoctorModel doctor) {

        try {

            db.collection("hospital")
                    .document(hospitalId)
                    .collection("doctors")
                    .document(doctor.getDoctorId())
                    .set(doctor);

            System.out.println(
                    "Doctor Data Saved Successfully");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public List<DoctorModel> getDoctors(
            String hospitalId) {

        List<DoctorModel> doctors = new ArrayList<>();

        try {

           List<QueryDocumentSnapshot> documents = db.collection("hospital")
                .document(hospitalId)
                .collection("doctors")
                .get()
                .get()
                .getDocuments();

            for (QueryDocumentSnapshot document : documents) {

            if (document.exists()) {

                DoctorModel doctor = document.toObject(DoctorModel.class);

                doctors.add(doctor);

            }

}

            System.out.println(
                    "Doctors Loaded: " + doctors.size());

        } catch (Exception e) {

            e.printStackTrace();

        }

        return doctors;

    }

    public DoctorModel getDoctor(
            String hospitalId,
            String doctorId) {

        try {

            DocumentSnapshot document = db.collection("hospital")
                    .document(hospitalId)
                    .collection("doctors")
                    .document(doctorId)
                    .get()
                    .get();

            if (document.exists()) {

                return document.toObject(
                        DoctorModel.class);

            }

            System.out.println(
                    "Doctor Data Not Found");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    public void updateDoctorStatus(
            String hospitalId,
            String doctorId,
            String status) {

        try {

            db.collection("hospital")
                    .document(hospitalId)
                    .collection("doctors")
                    .document(doctorId)
                    .update("status", status)
                    .get();

            System.out.println(
                    "Doctor Status Updated Successfully: " + doctorId + " -> " + status);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void listenToDoctors(
            String hospitalId,
            Consumer<List<DoctorModel>> listener) {

        db.collection("hospital")
                .document(hospitalId)
                .collection("doctors")
                .addSnapshotListener((snapshot, error) -> {

                    if (error != null) {

                        error.printStackTrace();
                        return;

                    }

                    if (snapshot != null) {

                        List<DoctorModel> doctors =
                                new ArrayList<>();

                        for (DocumentSnapshot document :
                                snapshot.getDocuments()) {

                            if (document.exists()) {

                                DoctorModel doctor =
                                        document.toObject(
                                                DoctorModel.class);

                                doctors.add(doctor);

                            }

                        }

                        listener.accept(doctors);

                    }

                });

    }

}