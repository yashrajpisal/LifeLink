package com.kurukshetra.controller.admin;



import com.kurukshetra.dao.admin.AdminAmbulanceAssignmentDao;
import com.kurukshetra.model.admin.AdminAmbulanceAssignmentModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AdminAmbulanceAssignmentController {

    private final AdminAmbulanceAssignmentDao assignmentDao;

    public AdminAmbulanceAssignmentController() {

        assignmentDao =
                new AdminAmbulanceAssignmentDao();
    }

    public void assignStaff(
            AdminAmbulanceAssignmentModel assignment)
            throws ExecutionException, InterruptedException {

        assignmentDao.assignStaff(assignment);
    }

    public AdminAmbulanceAssignmentModel getAssignment(
            String ambulanceId)
            throws ExecutionException, InterruptedException {

        return assignmentDao.getAssignment(
                ambulanceId
        );
    }

    public void removeAssignment(
            String ambulanceId)
            throws ExecutionException, InterruptedException {

        assignmentDao.removeAssignment(
                ambulanceId
        );
    }

    public List<AdminAmbulanceAssignmentModel> getAllAssignments()
        throws ExecutionException, InterruptedException {

        return assignmentDao.getAllAssignments();
    }
}