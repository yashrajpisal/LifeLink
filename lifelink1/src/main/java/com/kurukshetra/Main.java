package com.kurukshetra;
import java.security.AlgorithmConstraints;

import com.kurukshetra.view.Welcome;
import com.kurukshetra.view.admin.AdminDashboard;
import com.kurukshetra.view.driver.DriverDashboard;
import com.kurukshetra.view.hospital.HospitalDashboard;
import com.kurukshetra.view.login_signup.AalLoginStartPoint;
import com.kurukshetra.view.police.PoliceDashboard;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        System.out.println("Shree Ganeshay Namhaa!!");
        Application.launch(AalLoginStartPoint.class, args);
    }
}