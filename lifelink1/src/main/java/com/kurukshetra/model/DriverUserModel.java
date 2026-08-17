package com.kurukshetra.model;

public class DriverUserModel {
    
    private static DriverUserModel instance;
    private String name;
    private String email;

    public DriverUserModel() {

    }

    public static DriverUserModel getInstance(){
        if(instance == null){
            instance = new DriverUserModel();
        }
        return instance;
    }

    public void setDriverUserData(String name, String email){
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
