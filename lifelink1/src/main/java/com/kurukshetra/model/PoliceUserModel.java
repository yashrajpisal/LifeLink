package com.kurukshetra.model;

public class PoliceUserModel {
    
    private static PoliceUserModel instance;
    private String name;
    private String email;

    public PoliceUserModel() {

    }

    public static PoliceUserModel getInstance(){
        if(instance == null){
            instance = new PoliceUserModel();
        }
        return instance;
    }

    public void setPoliceUserData(String name, String email){
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
