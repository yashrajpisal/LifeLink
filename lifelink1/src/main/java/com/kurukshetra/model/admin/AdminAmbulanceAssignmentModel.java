package com.kurukshetra.model.admin;

public class AdminAmbulanceAssignmentModel {

    private String ambulanceId;
    private String driverEmail;
    private String driverName;
    private String nurseEmail;
    private String nurseName;
    private String status;
    private long assignedAt;

    public AdminAmbulanceAssignmentModel() {

    }

    public AdminAmbulanceAssignmentModel(
            String ambulanceId,
            String driverEmail,
            String driverName,
            String nurseEmail,
            String nurseName,
            String status,
            long assignedAt) {

        this.ambulanceId = ambulanceId;
        this.driverEmail = driverEmail;
        this.driverName = driverName;
        this.nurseEmail = nurseEmail;
        this.nurseName = nurseName;
        this.status = status;
        this.assignedAt = assignedAt;
    }

    public String getAmbulanceId() {
        return ambulanceId;
    }

    public void setAmbulanceId(String ambulanceId) {
        this.ambulanceId = ambulanceId;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getNurseEmail() {
        return nurseEmail;
    }

    public void setNurseEmail(String nurseEmail) {
        this.nurseEmail = nurseEmail;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(long assignedAt) {
        this.assignedAt = assignedAt;
    }
}