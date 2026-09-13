package com.kurukshetra.model.hospitalModel;

public class DoctorModel {

    private String doctorId;

    private String doctorName;

    private String specialization;

    private String shift;

    private String status;

    public DoctorModel() {

    }

    public DoctorModel(
            String doctorId,
            String doctorName,
            String specialization,
            String shift,
            String status) {

        this.doctorId = doctorId;

        this.doctorName = doctorName;

        this.specialization = specialization;

        this.shift = shift;

        this.status = status;
    }

    public String getDoctorId() {

        return doctorId;
    }

    public void setDoctorId(String doctorId) {

        this.doctorId = doctorId;
    }

    public String getDoctorName() {

        return doctorName;
    }

    public void setDoctorName(String doctorName) {

        this.doctorName = doctorName;
    }

    public String getSpecialization() {

        return specialization;
    }

    public void setSpecialization(String specialization) {

        this.specialization = specialization;
    }

    public String getShift() {

        return shift;
    }

    public void setShift(String shift) {

        this.shift = shift;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }
}
