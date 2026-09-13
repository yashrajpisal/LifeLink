package com.kurukshetra.controller.hospitalController;

import com.google.cloud.firestore.ListenerRegistration;
import com.kurukshetra.dao.hospitalDao.DriverHospitalNotificationDao;
import com.kurukshetra.model.hospitalModel.DriverHospitalNotificationModel;

import java.util.List;
import java.util.function.Consumer;

public class DriverHospitalNotificationController {

    private final DriverHospitalNotificationDao notificationDao;

    public DriverHospitalNotificationController() {
        notificationDao = new DriverHospitalNotificationDao();
    }

    public ListenerRegistration listenToNotifications(
            String hospitalAddress,
            Consumer<List<DriverHospitalNotificationModel>> callback) {

        return notificationDao.listenToNotifications(
                hospitalAddress,
                callback
        );
    }
}