package com.kurukshetra.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

// import com.kurukshetra.model.HospitalUserModel;

public class UserAuthController {

    private String API_KEY = "AIzaSyD3U81TU3fOOQRhIvixahFfB0A_t2h3vLI";
    UserController userController = new UserController();
    
    public Boolean signUp(String name, String email, String password) {

        JSONObject reqbody = new JSONObject()
                .put("email", email)
                .put("password", password)
                .put("returnSecureToken", true);

        try {
            HttpClient client = HttpClient.newHttpClient();

            URI uri = URI.create("https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + API_KEY);
                
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(reqbody.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response: " + response);

            if (response.statusCode() == 200) {
                return true;

            }else{

                System.out.println("Error:"+ response.body());
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     
    public Boolean signIn(String email, String pass){

        JSONObject reqbody = new JSONObject()
            .put("email",email)
            .put("password", pass);

        try{

            HttpClient client = HttpClient.newHttpClient();

            URI uri = URI.create("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key="+ API_KEY);

            HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(reqbody.toString()))
            .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response: "+ response);

            if (response.statusCode() == 200) {
                System.out.println("API Hit Successfully (SignIn)");
                return true;
            }else{
                System.out.println(response.body());
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
