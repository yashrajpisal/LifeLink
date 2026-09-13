package com.kurukshetra.dao.familyDao;

// package com.example.dao;

import com.kurukshetra.config.FirebaseConfig;
import com.kurukshetra.model.familyModel.MemberModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.EventListener;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class FamilyDao {

    private Firestore getDb() {
        return FirebaseConfig.getFirestore();
    }

    /**
     * Initializes the parent family document if not present.
     */
    public void ensureFamilyDocumentExists(String familyEmail) {
        Firestore db = getDb();
        if (db == null) return;
        Map<String, Object> meta = new HashMap<>();
        meta.put("email", familyEmail);
        meta.put("lastUpdated", FieldValue.serverTimestamp());
        db.collection("family").document(familyEmail).set(meta, SetOptions.merge());
    }

    /**
     * Saves or updates a member under family/{familyEmail}/members/{memberId}.
     */
    public void saveOrUpdateMember(String familyEmail, MemberModel member) throws ExecutionException, InterruptedException {
        Firestore db = getDb();
        if (db == null) return;

        ensureFamilyDocumentExists(familyEmail);

        String docId = (member.getId() == null || member.getId().trim().isEmpty())
                ? "mem_" + UUID.randomUUID().toString().substring(0, 8)
                : member.getId();
        member.setId(docId);

        db.collection("family")
          .document(familyEmail)
          .collection("members")
          .document(docId)
          .set(member, SetOptions.merge())
          .get();
    }

    /**
     * Deletes a specific member document from the subcollection.
     */
    public void deleteMember(String familyEmail, String memberId) throws ExecutionException, InterruptedException {
        Firestore db = getDb();
        if (db == null || memberId == null || memberId.trim().isEmpty()) return;

        db.collection("family")
          .document(familyEmail)
          .collection("members")
          .document(memberId)
          .delete()
          .get();
    }

    /**
     * Synchronizes whole list in batch (saves active members and removes deleted ones).
     */
    public void syncAllMembers(String familyEmail, List<MemberModel> activeMembers) throws ExecutionException, InterruptedException {
        Firestore db = getDb();
        if (db == null) return;

        ensureFamilyDocumentExists(familyEmail);

        CollectionReference membersRef = db.collection("family").document(familyEmail).collection("members");
        
        // Fetch existing documents from Firestore
        QuerySnapshot existingSnapshots = membersRef.get().get();
        Set<String> activeIds = new HashSet<>();

        WriteBatch batch = db.batch();

        for (MemberModel m : activeMembers) {
            if (m.getId() == null || m.getId().trim().isEmpty()) {
                m.setId("mem_" + UUID.randomUUID().toString().substring(0, 8));
            }
            activeIds.add(m.getId());
            DocumentReference docRef = membersRef.document(m.getId());
            batch.set(docRef, m, SetOptions.merge());
        }

        // Delete documents that are no longer present in the active UI list
        for (DocumentSnapshot doc : existingSnapshots.getDocuments()) {
            if (!activeIds.contains(doc.getId())) {
                batch.delete(doc.getReference());
            }
        }

        batch.commit().get();
    }

    /**
     * Real-time listener for members under family/{familyEmail}/members.
     */
    public ListenerRegistration listenToMembers(String familyEmail, EventListener<QuerySnapshot> listener) {
        Firestore db = getDb();
        if (db == null) return null;

        return db.collection("family")
                 .document(familyEmail)
                 .collection("members")
                 .addSnapshotListener(listener);
    }
}