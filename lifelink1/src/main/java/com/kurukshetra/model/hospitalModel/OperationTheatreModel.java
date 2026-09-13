package com.kurukshetra.model.hospitalModel;

public class OperationTheatreModel {

    private String otId;
    private String patient;
    private String procedure;
    private String doctors;
    private String operationTime;
    private String status;

    public OperationTheatreModel() {
    }

    public OperationTheatreModel(
            String otId,
            String patient,
            String procedure,
            String doctors,
            String operationTime,
            String status) {

        this.otId = otId;
        this.patient = patient;
        this.procedure = procedure;
        this.doctors = doctors;
        this.operationTime = operationTime;
        this.status = status;
    }

    public String getOtId() {
        return otId;
    }

    public void setOtId(String otId) {
        this.otId = otId;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getDoctors() {
        return doctors;
    }

    public void setDoctors(String doctors) {
        this.doctors = doctors;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}