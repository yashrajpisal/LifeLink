package com.kurukshetra.controller.hospitalController;

import com.google.cloud.Timestamp;
import com.kurukshetra.dao.hospitalDao.HospitalComplaintDao;
import com.kurukshetra.model.hospitalModel.HospitalComplaintModel;

public class HospitalComplaintController {

    private final HospitalComplaintDao complaintDao;

    public HospitalComplaintController() {

        this.complaintDao = new HospitalComplaintDao();

    }

    public String submitComplaint(

            String hospitalEmail,
            String hospitalName,
            String ambulanceId,
            String driverEmail,
            String nurseEmail,
            String complaintType,
            String description) throws Exception {

        String targetEmail = (hospitalEmail != null && !hospitalEmail.trim().isEmpty())
                ? hospitalEmail.trim()
                : "hospital1@lifelink.com";

        HospitalComplaintModel complaint = new HospitalComplaintModel();
        complaint.setHospitalEmail(targetEmail);
        complaint.setHospitalName(hospitalName);
        complaint.setReportedByRole("Hospital Management");
        complaint.setAmbulanceId(ambulanceId);
        complaint.setDriverEmail(driverEmail);
        complaint.setNurseEmail(nurseEmail);
        complaint.setComplaintType(complaintType);
        complaint.setDescription(description);
        complaint.setStatus("PENDING");
        complaint.setPriority("HIGH");
        complaint.setTimestamp(Timestamp.now());

        return complaintDao.saveComplaint(complaint);

    }

    public String getHospitalName(String hospitalEmail) throws Exception {

        return complaintDao.getHospitalName(hospitalEmail);

    }

    public java.util.List<HospitalComplaintModel> getAllHospitalComplaints() throws Exception {
        
        return complaintDao.getAllHospitalComplaints();
    
    }

    public void updateComplaintStatus(String complaintId, String status) throws Exception {
        
        complaintDao.updateComplaintStatus(complaintId, status);
    
    }

}