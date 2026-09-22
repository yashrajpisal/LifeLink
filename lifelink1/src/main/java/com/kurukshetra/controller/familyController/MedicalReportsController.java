package com.kurukshetra.controller.familyController;


import com.kurukshetra.dao.familyDao.FamilyDao;
import com.kurukshetra.model.familyModel.MemberModel;
import com.google.cloud.firestore.ListenerRegistration;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MedicalReportsController {

    private final FamilyDao familyDAO;
    private ListenerRegistration activeListener;

    public MedicalReportsController() {
        this.familyDAO = new FamilyDao();
    }

    
     // Subscribes to real-time updates for a given family email.
     
    public void subscribeToFamilyMembers(String familyEmail, Consumer<List<MemberModel>> onDataLoaded, Consumer<String> onError) {
        if (activeListener != null) {
            activeListener.remove();
        }

        activeListener = familyDAO.listenToMembers(familyEmail, (snapshots, error) -> {
            if (error != null) {
                Platform.runLater(() -> onError.accept("Failed to load records: " + error.getMessage()));
                return;
            }

            List<MemberModel> members = new ArrayList<>();
            if (snapshots != null && !snapshots.isEmpty()) {
                for (QueryDocumentSnapshot doc : snapshots.getDocuments()) {
                    MemberModel model = doc.toObject(MemberModel.class);
                    model.setId(doc.getId());
                    members.add(model);
                }
            }

            Platform.runLater(() -> onDataLoaded.accept(members));
        });
    }

    // Save or update a member with error handling and success callback.
 
    public void saveMember(String familyEmail, MemberModel member, Runnable onSuccess, Consumer<String> onError) {
        new Thread(() -> {
            try {
                familyDAO.saveOrUpdateMember(familyEmail, member);
                Platform.runLater(onSuccess);
            } catch (Exception e) {
                Platform.runLater(() -> onError.accept("Save failed: " + e.getMessage()));
            }
        }).start();
    }

    // Delete a member by ID with error handling and success callback.

    public void deleteMember(String familyEmail, String memberId, Runnable onSuccess, Consumer<String> onError) {
        new Thread(() -> {
            try {
                familyDAO.deleteMember(familyEmail, memberId);
                Platform.runLater(onSuccess);
            } catch (Exception e) {
                Platform.runLater(() -> onError.accept("Deletion failed: " + e.getMessage()));
            }
        }).start();
    }


    // Synchronizes entire list with batch execution.
     
    public void syncAllMembers(String familyEmail, List<MemberModel> members, Runnable onSuccess, Consumer<String> onError) {
        new Thread(() -> {
            try {
                familyDAO.syncAllMembers(familyEmail, members);
                Platform.runLater(onSuccess);
            } catch (Exception e) {
                Platform.runLater(() -> onError.accept("Sync failed: " + e.getMessage()));
            }
        }).start();
    }

    public void cleanUpListener() {
        if (activeListener != null) {
            activeListener.remove();
        }
    }
}