package com.kurukshetra.model.familyModel;

import com.google.cloud.firestore.annotation.DocumentId;

public class MemberModel {

    @DocumentId
    private String id;
    private String memberTag;
    private String fullName;
    private String bloodGroup;
    private String age;
    private String gender;
    private String allergy;
    private String weight;
    private String phone;
    private String emergencyContact;
    private String chronicCondition;
    private String lastReportType;
    private String uploadedFileName;
    private String relation;

    public MemberModel() {}

    public MemberModel(String id, String memberTag, String fullName, String bloodGroup, String age, String gender,
                       String allergy, String weight, String phone, String emergencyContact,
                       String chronicCondition, String lastReportType, String uploadedFileName,
                       String relation) {
        this.id = id;
        this.memberTag = memberTag;
        this.fullName = fullName;
        this.bloodGroup = bloodGroup;
        this.age = age;
        this.gender = gender;
        this.allergy = allergy;
        this.weight = weight;
        this.phone = phone;
        this.emergencyContact = emergencyContact;
        this.chronicCondition = chronicCondition;
        this.lastReportType = lastReportType;
        this.uploadedFileName = uploadedFileName;
        this.relation = relation;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMemberTag() { return memberTag; }
    public void setMemberTag(String memberTag) { this.memberTag = memberTag; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAllergy() { return allergy; }
    public void setAllergy(String allergy) { this.allergy = allergy; }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public String getChronicCondition() { return chronicCondition; }
    public void setChronicCondition(String chronicCondition) { this.chronicCondition = chronicCondition; }

    public String getLastReportType() { return lastReportType; }
    public void setLastReportType(String lastReportType) { this.lastReportType = lastReportType; }

    public String getUploadedFileName() { return uploadedFileName; }
    public void setUploadedFileName(String uploadedFileName) { this.uploadedFileName = uploadedFileName; }

    public String getRelation() { return relation; }
    public void setRelation(String relation) { this.relation = relation; }
}
