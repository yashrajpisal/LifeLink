package com.kurukshetra.model.admin;

public class AdminUserModel {
    
    private static AdminUserModel instance;
    private String name;
    private String email;

    public AdminUserModel() {

    }

    public static AdminUserModel getInstance(){
        if(instance == null){
            instance = new AdminUserModel();
        }
        return instance;
    }

    public void setAdminUserData(String name, String email){
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
