package com.kurukshetra.model.driverModel;

import com.google.cloud.Timestamp;

public class DriverEmergencyModel {
    private String emergencyId;
    private String assignedDriverEmail;
    private String patientName;
    private String emergencyType;
    private String pickupLocation;
    private String googleMapUrl;
    private String priority;
    private String status;
    private Timestamp timestamp;

    public DriverEmergencyModel() {
    }

    public DriverEmergencyModel(String patientName, String emergencyType,
            String pickupLocation,String assignedDriverEmail, String googleMapUrl) {
       
        this.patientName = patientName;
        this.emergencyType = emergencyType;
        this.pickupLocation = pickupLocation;
        this.googleMapUrl = googleMapUrl;
        // this.status = "PENDING";
        this.timestamp = Timestamp.now();
    }

    // Getters and Setters
    public String getEmergencyId() {
        return emergencyId;
    }

    public void setEmergencyId(String emergencyId) {
        this.emergencyId = emergencyId;
    }

    public String getAssignedDriverEmail() {
        return assignedDriverEmail;
    }

    public void setAssignedDriverEmail(String assignedDriverEmail) {
        this.assignedDriverEmail = assignedDriverEmail;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getEmergencyType() {
        return emergencyType;
    }

    public void setEmergencyType(String emergencyType) {
        this.emergencyType = emergencyType;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getGoogleMapUrl() {
        return googleMapUrl;
    }

    public void setGoogleMapUrl(String googleMapUrl) {
        this.googleMapUrl = googleMapUrl;
    }

    // public String getPriority() {
    //     return priority;
    // }

    // public void setPriority(String priority) {
    //     this.priority = priority;
    // }

    // public String getStatus() {
    //     return status;
    // }

    // public void setStatus(String status) {
    //     this.status = status;
    // }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}