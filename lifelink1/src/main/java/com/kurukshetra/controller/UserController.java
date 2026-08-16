package com.kurukshetra.controller;

import com.kurukshetra.dao.UserDao;
import com.kurukshetra.model.UserModel;

public class UserController {
    
    UserModel userModel = new UserModel();

    public void passToModel(String name, String email){
        UserModel user = UserModel.getInstance();
        user.setHospitalUserData(name, email);

        UserDao userDao = new UserDao();
        userDao.saveHospitalUser(user);
    }
}
