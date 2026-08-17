package com.kurukshetra.model;

public class HospitalUserModel {
    
    private static HospitalUserModel instance;
    private String name;
    private String email;

    public HospitalUserModel() {

    }

    public static HospitalUserModel getInstance(){
        if(instance == null){
            instance = new HospitalUserModel();
        }
        return instance;
    }

    public void setHospitalUserData(String name, String email){
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
