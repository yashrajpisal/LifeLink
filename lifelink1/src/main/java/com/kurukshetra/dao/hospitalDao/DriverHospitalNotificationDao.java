package com.kurukshetra.dao.hospitalDao;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.ListenerRegistration;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.kurukshetra.model.hospitalModel.DriverHospitalNotificationModel;
import com.kurukshetra.config.FirebaseConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DriverHospitalNotificationDao {

    private final Firestore db;

    public DriverHospitalNotificationDao() {
        db = FirebaseConfig.getFirestore();
    }

    public ListenerRegistration listenToNotifications(
            String hospitalAddress,
            Consumer<List<DriverHospitalNotificationModel>> callback) {

        CollectionReference collection =
                db.collection("driverToHospitalNotify");

        Query query = collection
                .whereEqualTo("hospitalAddress", hospitalAddress);

        return query.addSnapshotListener((snapshots, error) -> {

            if (error != null) {
                error.printStackTrace();
                return;
            }

            List<DriverHospitalNotificationModel> notifications =
                    new ArrayList<>();

            if (snapshots != null) {

                for (var document : snapshots.getDocuments()) {

                    DriverHospitalNotificationModel notification =
                            document.toObject(
                                    DriverHospitalNotificationModel.class);

                    if (notification != null) {
                        notifications.add(notification);
                    }
                }
            }

            callback.accept(notifications);
        });
    }
}