// package com.example.model;

// import java.security.Timestamp;
// import java.sql.Time;

// public class AdminSideEmgReqModel {

//     String destination;
//     String source;
//     String patID;
//     String status;
//     String nurseID;
//     String driverID;
//     Timestamp timestamp;
//     String tripID;


//     public AdminSideEmgReqModel() {}

//     public AdminSideEmgReqModel(String destination, String source, String patID, String status, String nurseID, String driverID, Timestamp timestamp, String tripID){

//         this.destination = destination;
//         this.source = source;
//         this.patID = patID;
//         this.status = status;
//         this.nurseID = nurseID;
//         this.driverID = driverID;
//         this.timestamp = timestamp;
//         this.tripID = tripID;
//     }


//     public String getDestination() {
//         return destination;
//     }


//     public void setDestination(String destination) {
//         this.destination = destination;
//     }


//     public String getSource() {
//         return source;
//     }


//     public void setSource(String source) {
//         this.source = source;
//     }


//     public String getPatID() {
//         return patID;
//     }


//     public void setPatID(String patID) {
//         this.patID = patID;
//     }


//     public String getStatus() {
//         return status;
//     }


//     public void setStatus(String status) {
//         this.status = status;
//     }


//     public String getNurseID() {
//         return nurseID;
//     }


//     public void setNurseID(String nurseID) {
//         this.nurseID = nurseID;
//     }


//     public String getDriverID() {
//         return driverID;
//     }


//     public void setDriverID(String driverID) {
//         this.driverID = driverID;
//     }


//     public Timestamp getTimestamp() {
//         return timestamp;
//     }


//     public void setTimestamp(String timestamp) {
//         this.timestamp = timestamp;
//     }


//     public String getTripID() {
//         return tripID;
//     }


//     public void setTripID(String tripID) {
//         this.tripID = tripID;
//     }
    
    
// }

package com.kurukshetra.model.admin;

import com.google.cloud.Timestamp;

public class AdminSideEmgReqModel {

    private String destination;
    private String source;
    private String patID;
    private String status;
    private String nurseID;
    private String driverID;
    private Timestamp timestamp;
    private String tripID;

    // Required by Firestore for automatic object deserialization
    public AdminSideEmgReqModel() {}

    public AdminSideEmgReqModel(String destination, String source, String patID, String status, 
                                String nurseID, String driverID, Timestamp timestamp, String tripID) {
        this.destination = destination;
        this.source = source;
        this.patID = patID;
        this.status = status;
        this.nurseID = nurseID;
        this.driverID = driverID;
        this.timestamp = timestamp;
        this.tripID = tripID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPatID() {
        return patID;
    }

    public void setPatID(String patID) {
        this.patID = patID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNurseID() {
        return nurseID;
    }

    public void setNurseID(String nurseID) {
        this.nurseID = nurseID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }
}