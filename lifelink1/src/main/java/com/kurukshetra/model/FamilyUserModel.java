package com.kurukshetra.model;

public class FamilyUserModel {
    
    private static FamilyUserModel instance;
    private String name;
    private String email;

    public FamilyUserModel() {

    }

    public static FamilyUserModel getInstance(){
        if(instance == null){
            instance = new FamilyUserModel();
        }
        return instance;
    }

    public void setFamilyUserData(String name, String email){
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
