package com.kurukshetra.dao.driverDao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.kurukshetra.model.driverModel.DriverStaffModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DriverStaffDao {

    private final Firestore firestore;

    public DriverStaffDao(Firestore firestore) {
        this.firestore = firestore;
    }

    public void addDriverStaff(DriverStaffModel driver) throws ExecutionException, InterruptedException {

        firestore.collection("driverStaff")
                .document(driver.getEmail())
                .set(driver)
                .get();
    }

    public DriverStaffModel getDriverStaff(String email) throws ExecutionException, InterruptedException {

        DocumentSnapshot document = firestore.collection("driverStaff")
                .document(email)
                .get()
                .get();

        if (document.exists()) {
            return document.toObject(DriverStaffModel.class);
        }

        return null;
    }

    public List<DriverStaffModel> getAllDriverStaff() throws ExecutionException, InterruptedException {

        List<DriverStaffModel> drivers = new ArrayList<>();

        ApiFuture<QuerySnapshot> future = firestore.collection("driverStaff").get();

        for (DocumentSnapshot document : future.get().getDocuments()) {

            DriverStaffModel driver = document.toObject(DriverStaffModel.class);

            if (driver != null) {
                drivers.add(driver);
            }
        }

        return drivers;
    }

    public void updateDriverStatus(String email, String status)
            throws ExecutionException, InterruptedException {

        DocumentReference document =
                firestore.collection("driverStaff")
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

            DocumentSnapshot driverDocument =
                    firestore.collection("driver")
                            .document(email)
                            .get()
                            .get();

            String name = email;

            if (driverDocument.exists()) {

                String driverName =
                        driverDocument.getString("name");

                if (driverName != null &&
                        !driverName.trim().isEmpty()) {

                    name = driverName;
                }
            }

            DriverStaffModel driver =
                    new DriverStaffModel(
                            name,
                            email,
                            status,
                            "Not Assigned"
                    );

            document.set(driver)
                    .get();
        }
    }


    public void updateDriverShift(String email, String shift)
            throws ExecutionException, InterruptedException {

        DocumentReference document =
                firestore.collection("driverStaff")
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

            DocumentSnapshot driverDocument =
                    firestore.collection("driver")
                            .document(email)
                            .get()
                            .get();

            String name = email;

            if (driverDocument.exists()) {

                String driverName =
                        driverDocument.getString("name");

                if (driverName != null &&
                        !driverName.trim().isEmpty()) {

                    name = driverName;
                }
            }

            DriverStaffModel driver =
                    new DriverStaffModel(
                            name,
                            email,
                            "Available",
                            shift
                    );

            document.set(driver)
                    .get();
        }
    }



    public void deleteDriverStaff(String email)
            throws ExecutionException, InterruptedException {

        firestore.collection("driverStaff")
                .document(email)
                .delete()
                .get();
    }
}