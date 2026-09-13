package com.kurukshetra.controller.nurseController;

import com.kurukshetra.dao.nurseDao.NurseStaffDao;
import com.kurukshetra.model.nurseModel.NurseStaffModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NurseStaffController {

    private final NurseStaffDao nurseStaffDao;

    public NurseStaffController(NurseStaffDao nurseStaffDao) {
        this.nurseStaffDao = nurseStaffDao;
    }

    public void addNurseStaff(NurseStaffModel nurse)
            throws ExecutionException, InterruptedException {

        nurseStaffDao.addNurseStaff(nurse);
    }

    public NurseStaffModel getNurseStaff(String email)
            throws ExecutionException, InterruptedException {

        return nurseStaffDao.getNurseStaff(email);
    }

    public List<NurseStaffModel> getAllNurseStaff()
            throws ExecutionException, InterruptedException {

        return nurseStaffDao.getAllNurseStaff();
    }

    public void updateNurseStatus(String email, String status)
            throws ExecutionException, InterruptedException {

        nurseStaffDao.updateNurseStatus(email, status);
    }

    public void updateNurseShift(String email, String shift)
            throws ExecutionException, InterruptedException {

        nurseStaffDao.updateNurseShift(email, shift);
    }

    public void deleteNurseStaff(String email)
            throws ExecutionException, InterruptedException {

        nurseStaffDao.deleteNurseStaff(email);
    }
}