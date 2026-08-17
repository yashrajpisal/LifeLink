package com.kurukshetra.model;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserModel {

    private static UserModel instance;

    private String uid;
    private String name;
    private String email;
    private String profilePicUrl;
    private String status;
    private long createdAt;

    // Private constructor for Singleton pattern
    public UserModel() {}

    public UserModel(String uid, String name, String email, String profilePicUrl, String status, long createdAt) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.profilePicUrl = profilePicUrl;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Thread-safe Singleton access
    public static synchronized UserModel getInstance() {
        if (instance == null) {
            instance = new UserModel();
        }
        return instance;
    }

    // Set logged-in session user
    public static synchronized void setInstance(UserModel userModel) {
        instance = userModel;
    }

    // Clear session on logout
    public static synchronized void clearSession() {
        instance = null;
    }

    // Getters and Setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email != null ? email : "";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}