package com.kurukshetra.controller.hospitalController;

import com.kurukshetra.dao.hospitalDao.OperationTheatreDao;
import com.kurukshetra.dao.hospitalDao.DoctorDao;
import com.kurukshetra.model.hospitalModel.OperationTheatreModel;
import com.kurukshetra.model.hospitalModel.DoctorModel;

import java.util.List;

public class OperationTheatreController {

    private OperationTheatreDao operationTheatreDao;
    private DoctorDao doctorDao;

    public OperationTheatreController() {

        operationTheatreDao =
                new OperationTheatreDao();

        doctorDao =
                new DoctorDao();
    }

    public void reserveOperationTheatre(

            String hospitalId,
            String otId,
            String patient,
            String procedure,
            String doctors,
            String operationTime) {

        OperationTheatreModel operationTheatre =
                new OperationTheatreModel(
                        otId,
                        patient,
                        procedure,
                        doctors,
                        operationTime,
                        "RESERVED"
                );

        operationTheatreDao.reserveOperationTheatre(
                hospitalId,
                otId,
                operationTheatre
        );

        /*
         * Selected doctors become BUSY.
         */
        if (doctors != null && !doctors.isEmpty()) {

            List<DoctorModel> doctorList =
                    doctorDao.getDoctors(hospitalId);

            String[] selectedDoctors =
                    doctors.split(", ");

            for (String selectedDoctor : selectedDoctors) {

                for (DoctorModel doctor : doctorList) {

                    String doctorDisplay =
                            doctor.getDoctorName()
                                    + " - "
                                    + doctor.getSpecialization();

                    if (doctorDisplay.equals(selectedDoctor)) {

                        doctorDao.updateDoctorStatus(
                                hospitalId,
                                doctor.getDoctorId(),
                                "BUSY"
                        );

                        break;
                    }
                }
            }
        }
    }

    public OperationTheatreModel getOperationTheatre(

            String hospitalId,
            String otId) {

        return operationTheatreDao.getOperationTheatre(
                hospitalId,
                otId
        );
    }

    public void completeOperationTheatre(

            String hospitalId,
            String otId) {

        /*
         * First get the OT so we know
         * which doctors were assigned.
         */
        OperationTheatreModel operationTheatre =
                operationTheatreDao.getOperationTheatre(
                        hospitalId,
                        otId
                );

        /*
         * Complete the OT.
         */
        operationTheatreDao.completeOperationTheatre(
                hospitalId,
                otId
        );

        /*
         * Assigned doctors become AVAILABLE.
         */
        if (operationTheatre != null &&
                operationTheatre.getDoctors() != null &&
                !operationTheatre.getDoctors().isEmpty()) {

            List<DoctorModel> doctorList =
                    doctorDao.getDoctors(hospitalId);

            String[] assignedDoctors =
                    operationTheatre.getDoctors().split(", ");

            for (String assignedDoctor : assignedDoctors) {

                for (DoctorModel doctor : doctorList) {

                    String doctorDisplay =
                            doctor.getDoctorName()
                                    + " - "
                                    + doctor.getSpecialization();

                    String altDisplay =
                            doctor.getDoctorName()
                                    + " ("
                                    + doctor.getSpecialization()
                                    + ")";

                    if (doctorDisplay.equalsIgnoreCase(assignedDoctor.trim()) ||
                            altDisplay.equalsIgnoreCase(assignedDoctor.trim()) ||
                            doctor.getDoctorName().equalsIgnoreCase(assignedDoctor.trim()) ||
                            doctor.getDoctorId().equalsIgnoreCase(assignedDoctor.trim()) ||
                            assignedDoctor.contains(doctor.getDoctorName())) {

                        doctorDao.updateDoctorStatus(
                                hospitalId,
                                doctor.getDoctorId(),
                                "AVAILABLE"
                        );

                        break;
                    }
                }
            }
        }
    }

    public void listenToOperationTheatre(

            String hospitalId,
            String otId,

            java.util.function.Consumer<OperationTheatreModel> listener) {

        operationTheatreDao.listenToOperationTheatre(
                hospitalId,
                otId,
                listener
        );
    }
}

