package com.kurukshetra.model;

public class NurseUserModel {
    
    private static NurseUserModel instance;
    private String name;
    private String email;

    public NurseUserModel() {

    }

    public static NurseUserModel getInstance(){
        if(instance == null){
            instance = new NurseUserModel();
        }
        return instance;
    }

    public void setNurseUserData(String name, String email){
        this.name = name;
        this.email = email;
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

    
}
