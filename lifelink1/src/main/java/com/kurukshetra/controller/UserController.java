package com.kurukshetra.controller;

import com.kurukshetra.dao.*;
import com.kurukshetra.dao.adminDao.AdminUserDao;
import com.kurukshetra.dao.driverDao.DriverUserDao;
import com.kurukshetra.dao.family.FamilyUserDao;
import com.kurukshetra.dao.hospital.HospitalUserDao;
import com.kurukshetra.dao.nurse.NurseUserDao;
import com.kurukshetra.dao.police.PoliceUserDao;
import com.kurukshetra.model.*;
import com.kurukshetra.model.driverModel.DriverUserModel;

public class UserController {
    

    public void passToHospitalModel(String name, String email) {
        HospitalUserModel user = HospitalUserModel.getInstance();
        user.setHospitalUserData(name, email);

        HospitalUserDao userDao = new HospitalUserDao();
        userDao.saveHospitalUser(user);
        System.out.println("Passing hospital data to HospitalDao");
    }

    public void passToNurseModel(String name, String email) {
        NurseUserModel user = NurseUserModel.getInstance();
        user.setNurseUserData(name, email);

        NurseUserDao userDao = new NurseUserDao();
        userDao.saveNurseUser(user);
    }

    public void passToDriverModel(String name, String email) {
        DriverUserModel user = DriverUserModel.getInstance();
        user.setDriverUserData(name, email);

        DriverUserDao userDao = new DriverUserDao();
        userDao.saveDriverUser(user);
    }

    public void passToFamilyModel(String name, String email) {
        FamilyUserModel user = FamilyUserModel.getInstance();
        user.setFamilyUserData(name, email);

        FamilyUserDao userDao = new FamilyUserDao();
        userDao.saveFamilyUser(user);
    }

     public void passToAdminModel(String name, String email) {
        AdminUserModel user = AdminUserModel.getInstance();
        user.setAdminUserData(name, email);

        AdminUserDao userDao = new AdminUserDao();
        userDao.saveAdminUser(user);
    }

     public void passToPoliceModel(String name, String email) {
        PoliceUserModel user = PoliceUserModel.getInstance();
        user.setPoliceUserData(name, email);

        PoliceUserDao userDao = new PoliceUserDao();
        userDao.savePoliceUser(user);
    }
}
