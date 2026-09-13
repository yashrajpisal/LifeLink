package com.kurukshetra.controller.driverController;

import com.kurukshetra.dao.driverDao.DriverStaffDao;
import com.kurukshetra.model.driverModel.DriverStaffModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DriverStaffController {

    private final DriverStaffDao driverStaffDao;

    public DriverStaffController(DriverStaffDao driverStaffDao) {
        this.driverStaffDao = driverStaffDao;
    }

    public void addDriverStaff(DriverStaffModel driver)
            throws ExecutionException, InterruptedException {

        driverStaffDao.addDriverStaff(driver);
    }

    public DriverStaffModel getDriverStaff(String email)
            throws ExecutionException, InterruptedException {

        return driverStaffDao.getDriverStaff(email);
    }

    public List<DriverStaffModel> getAllDriverStaff()
            throws ExecutionException, InterruptedException {

        return driverStaffDao.getAllDriverStaff();
    }

    public void updateDriverStatus(String email, String status)
            throws ExecutionException, InterruptedException {

        driverStaffDao.updateDriverStatus(email, status);
    }

    public void updateDriverShift(String email, String shift)
            throws ExecutionException, InterruptedException {

        driverStaffDao.updateDriverShift(email, shift);
    }

    public void deleteDriverStaff(String email)
            throws ExecutionException, InterruptedException {

        driverStaffDao.deleteDriverStaff(email);
    }
}