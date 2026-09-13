package com.kurukshetra.model.hospitalModel;

import com.google.cloud.Timestamp;

public class HospitalComplaintModel {

    private String complaintId;
    private String hospitalEmail;
    private String hospitalName;
    private String reportedByRole;
    private String ambulanceId;
    private String driverEmail;
    private String nurseEmail;
    private String complaintType;
    private String description;
    private String status;
    private String priority;
    private Timestamp timestamp;

    public HospitalComplaintModel() {
    }

    public HospitalComplaintModel(
            String complaintId,
            String hospitalEmail,
            String hospitalName,
            String reportedByRole,
            String ambulanceId,
            String driverEmail,
            String nurseEmail,
            String complaintType,
            String description,
            String status,
            String priority,
            Timestamp timestamp) {

        this.complaintId = complaintId;
        this.hospitalEmail = hospitalEmail;
        this.hospitalName = hospitalName;
        this.reportedByRole = reportedByRole;
        this.ambulanceId = ambulanceId;
        this.driverEmail = driverEmail;
        this.nurseEmail = nurseEmail;
        this.complaintType = complaintType;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.timestamp = timestamp;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getHospitalEmail() {
        return hospitalEmail;
    }

    public void setHospitalEmail(String hospitalEmail) {
        this.hospitalEmail = hospitalEmail;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getReportedByRole() {
        return reportedByRole;
    }

    public void setReportedByRole(String reportedByRole) {
        this.reportedByRole = reportedByRole;
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

    public String getNurseEmail() {
        return nurseEmail;
    }

    public void setNurseEmail(String nurseEmail) {
        this.nurseEmail = nurseEmail;
    }
    
    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}