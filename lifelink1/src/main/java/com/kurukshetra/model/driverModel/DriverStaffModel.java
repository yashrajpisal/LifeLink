package com.kurukshetra.model.driverModel;

public class DriverStaffModel {

    private String name;
    private String email;
    private String status;
    private String shift;

    public DriverStaffModel() {
    }

    public DriverStaffModel(String name, String email, String status, String shift) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.shift = shift;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}