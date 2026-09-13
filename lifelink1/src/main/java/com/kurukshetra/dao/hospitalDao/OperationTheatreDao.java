package com.kurukshetra.dao.hospitalDao;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.hospitalModel.OperationTheatreModel;

public class OperationTheatreDao {

    private Firestore db = FirebaseConfig.getFirestore();


    public void reserveOperationTheatre(
            String hospitalId,
            String otId,
            OperationTheatreModel operationTheatre) {

        try {

            db.collection("hospital")
                    .document(hospitalId)
                    .collection("operationTheatres")
                    .document(otId)
                    .set(operationTheatre)
                    .get();

            System.out.println(
                    otId + " Reserved Successfully"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public OperationTheatreModel getOperationTheatre(
            String hospitalId,
            String otId) {

        try {

            DocumentSnapshot document =
                    db.collection("hospital")
                            .document(hospitalId)
                            .collection("operationTheatres")
                            .document(otId)
                            .get()
                            .get();

            if (document.exists()) {

                return document.toObject(
                        OperationTheatreModel.class
                );
            }

            System.out.println(
                    otId + " Data Not Found"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }


    public void completeOperationTheatre(
            String hospitalId,
            String otId) {

        try {

            db.collection("hospital")
                    .document(hospitalId)
                    .collection("operationTheatres")
                    .document(otId)
                    .update(
                            "status",
                            "AVAILABLE",
                            "patient",
                            "",
                            "procedure",
                            "",
                            "doctors",
                            "",
                            "operationTime",
                            ""
                    )
                    .get();

            System.out.println(
                    otId + " Operation Completed"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


            public void listenToOperationTheatre(
                    String hospitalId,
                    String otId,
                    java.util.function.Consumer<OperationTheatreModel> listener) {

                db.collection("hospital")
                        .document(hospitalId)
                        .collection("operationTheatres")
                        .document(otId)
                        .addSnapshotListener((snapshot, error) -> {

                            if (error != null) {

                                error.printStackTrace();

                                return;
                            }

                            if (snapshot != null &&
                                    snapshot.exists()) {

                                OperationTheatreModel operationTheatre =
                                        snapshot.toObject(
                                                OperationTheatreModel.class
                                        );

                                listener.accept(
                                        operationTheatre
                                );
                            }
                        });
            }


             
}