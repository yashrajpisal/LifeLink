package com.kurukshetra.dao.admin;

import com.google.api.core.ApiFuture;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.admin.AdminAmbulanceAssignmentModel;

import java.util.concurrent.ExecutionException;

public class AdminAmbulanceAssignmentDao {

    private final Firestore firestore;

    public AdminAmbulanceAssignmentDao() {

        firestore = FirebaseConfig.getFirestore();
    }

    public void assignStaff(
            AdminAmbulanceAssignmentModel assignment)
            throws ExecutionException, InterruptedException {

        firestore.collection(
                "ambulanceAssignments"
        ).document(
                assignment.getAmbulanceId()
        ).set(assignment).get();
    }

    public AdminAmbulanceAssignmentModel getAssignment(
            String ambulanceId)
            throws ExecutionException, InterruptedException {

        DocumentSnapshot document =
                firestore.collection(
                        "ambulanceAssignments"
                ).document(
                        ambulanceId
                ).get().get();

        if (!document.exists()) {

            return null;
        }

        return document.toObject(
                AdminAmbulanceAssignmentModel.class
        );
    }

    public void removeAssignment(
            String ambulanceId)
            throws ExecutionException, InterruptedException {

        firestore.collection(
                "ambulanceAssignments"
        ).document(
                ambulanceId
        ).delete().get();
    }


    public List<AdminAmbulanceAssignmentModel> getAllAssignments()
        throws ExecutionException, InterruptedException {

        List<AdminAmbulanceAssignmentModel> assignments =
                new ArrayList<>();

        ApiFuture<QuerySnapshot> future =
                firestore.collection(
                        "ambulanceAssignments"
                ).get();

        for (DocumentSnapshot document :
                future.get().getDocuments()) {

            AdminAmbulanceAssignmentModel assignment =
                    document.toObject(
                            AdminAmbulanceAssignmentModel.class
                    );

            if (assignment != null) {
                assignments.add(assignment);
            }
        }

        return assignments;
    }

}