package com.kurukshetra.model.voice;

import com.google.cloud.Timestamp;

public class NurseVoiceReportModel {
    private String reportId;
    private String nurseEmail;
    private String patientId;
    private String transcribedText;
    private Timestamp timestamp;

    public NurseVoiceReportModel() {}

    public NurseVoiceReportModel(String reportId, String nurseEmail, String patientId, String transcribedText, Timestamp timestamp) {
        this.reportId = reportId;
        this.nurseEmail = nurseEmail;
        this.patientId = patientId;
        this.transcribedText = transcribedText;
        this.timestamp = timestamp;
    }

    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }

    public String getNurseEmail() { return nurseEmail; }
    public void setNurseEmail(String nurseEmail) { this.nurseEmail = nurseEmail; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getTranscribedText() { return transcribedText; }
    public void setTranscribedText(String transcribedText) { this.transcribedText = transcribedText; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}
