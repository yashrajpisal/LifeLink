package com.kurukshetra.dao.nurseDao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.kurukshetra.model.nurseModel.NurseStaffModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NurseStaffDao {

    private final Firestore firestore;

    public NurseStaffDao(Firestore firestore) {
        this.firestore = firestore;
    }

    public void addNurseStaff(NurseStaffModel nurse)
            throws ExecutionException, InterruptedException {

        firestore.collection("nurse")
                .document(nurse.getEmail())
                .set(nurse)
                .get();
    }

    public NurseStaffModel getNurseStaff(String email)
            throws ExecutionException, InterruptedException {

        DocumentSnapshot document =
                firestore.collection("nurseStaff")
                        .document(email)
                        .get()
                        .get();

        if (document.exists()) {
            return document.toObject(NurseStaffModel.class);
        }

        return null;
    }

    public List<NurseStaffModel> getAllNurseStaff()
            throws ExecutionException, InterruptedException {

        List<NurseStaffModel> nurses = new ArrayList<>();

        ApiFuture<QuerySnapshot> future =
                firestore.collection("nurseStaff").get();

        for (DocumentSnapshot document :
                future.get().getDocuments()) {

            NurseStaffModel nurse =
                    document.toObject(NurseStaffModel.class);

            if (nurse != null) {
                nurses.add(nurse);
            }
        }

        return nurses;
    }

    public void updateNurseStatus(String email, String status)
            throws ExecutionException, InterruptedException {

        DocumentReference document =
                firestore.collection("nurseStaff")
                        .document(email);

        DocumentSnapshot snapshot =
                document.get()
                        .get();

        if (snapshot.exists()) {

            document.update(
                    "status",
                    status
            ).get();

        } else {

            DocumentSnapshot nurseDocument =
                    firestore.collection("nurse")
                            .document(email)
                            .get()
                            .get();

            String name = email;

            if (nurseDocument.exists()) {

                String nurseName =
                        nurseDocument.getString("name");

                if (nurseName != null &&
                        !nurseName.trim().isEmpty()) {

                    name = nurseName;
                }
            }

            NurseStaffModel nurse =
                    new NurseStaffModel(
                            name,
                            email,
                            status,
                            "Not Assigned"
                    );

            document.set(nurse)
                    .get();
        }
    }

    public void updateNurseShift(String email, String shift)
            throws ExecutionException, InterruptedException {

        DocumentReference document =
                firestore.collection("nurseStaff")
                        .document(email);

        DocumentSnapshot snapshot =
                document.get()
                        .get();

        if (snapshot.exists()) {

            document.update(
                    "shift",
                    shift
            ).get();

        } else {

            DocumentSnapshot nurseDocument =
                    firestore.collection("nurse")
                            .document(email)
                            .get()
                            .get();

            String name = email;

            if (nurseDocument.exists()) {

                String nurseName =
                        nurseDocument.getString("name");

                if (nurseName != null &&
                        !nurseName.trim().isEmpty()) {

                    name = nurseName;
                }
            }

            NurseStaffModel nurse =
                    new NurseStaffModel(
                            name,
                            email,
                            "Available",
                            shift
                    );

            document.set(nurse)
                    .get();
        }
    }

    public void deleteNurseStaff(String email)
            throws ExecutionException, InterruptedException {

        firestore.collection("nurseStaff")
                .document(email)
                .delete()
                .get();
    }
}