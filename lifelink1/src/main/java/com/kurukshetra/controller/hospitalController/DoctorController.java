package com.kurukshetra.controller.hospitalController;

import com.kurukshetra.dao.hospitalDao.DoctorDao;
import com.kurukshetra.model.hospitalModel.DoctorModel;

import java.util.List;
import java.util.function.Consumer;

public class DoctorController {

    private final DoctorDao doctorDao = new DoctorDao();

    public void saveDoctor(
            String hospitalId,
            DoctorModel doctor) {

        doctorDao.saveDoctor(
                hospitalId,
                doctor
        );
    }

    public List<DoctorModel> getDoctors(
            String hospitalId) {

        return doctorDao.getDoctors(
                hospitalId
        );
    }

    public DoctorModel getDoctor(
            String hospitalId,
            String doctorId) {

        return doctorDao.getDoctor(
                hospitalId,
                doctorId
        );
    }

    public void updateDoctorStatus(
            String hospitalId,
            String doctorId,
            String status) {

        DoctorModel doctor =
                doctorDao.getDoctor(
                        hospitalId,
                        doctorId
                );

        if (doctor == null) {
            return;
        }

        doctorDao.updateDoctorStatus(
                hospitalId,
                doctorId,
                status
        );
    }

    public void listenToDoctors(
            String hospitalId,
            Consumer<List<DoctorModel>> listener) {

        doctorDao.listenToDoctors(
                hospitalId,
                listener
        );
    }
}
