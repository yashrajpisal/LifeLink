package com.kurukshetra.model.police;

import com.google.cloud.Timestamp;

public class PoliceProfileModel {

    private String email;
    private String controlRoom;
    private String location;
    private String policeDistrict;
    private String responseZone;
    private String officer;
    private String role;
    private String contact;
    private String shift;
    private Timestamp lastUpdated;

    public PoliceProfileModel() {}

    public PoliceProfileModel(String email, String controlRoom, String location, String policeDistrict,
                              String responseZone, String officer, String role, String contact, String shift) {
        this.email = email;
        this.controlRoom = controlRoom;
        this.location = location;
        this.policeDistrict = policeDistrict;
        this.responseZone = responseZone;
        this.officer = officer;
        this.role = role;
        this.contact = contact;
        this.shift = shift;
        this.lastUpdated = Timestamp.now();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getControlRoom() { return controlRoom; }
    public void setControlRoom(String controlRoom) { this.controlRoom = controlRoom; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getPoliceDistrict() { return policeDistrict; }
    public void setPoliceDistrict(String policeDistrict) { this.policeDistrict = policeDistrict; }

    public String getResponseZone() { return responseZone; }
    public void setResponseZone(String responseZone) { this.responseZone = responseZone; }

    public String getOfficer() { return officer; }
    public void setOfficer(String officer) { this.officer = officer; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public Timestamp getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Timestamp lastUpdated) { this.lastUpdated = lastUpdated; }
}