package com.kurukshetra;

import com.kurukshetra.view.Welcome;
// import com.kurukshetra.view.driver.DriverDashboard;
// import com.kurukshetra.view.family.Start;
import com.kurukshetra.view.admin.AdminDashboard;
import com.kurukshetra.view.driver.DriverDashboard;
import com.kurukshetra.view.family.FamilyHomePage;
import com.kurukshetra.view.hospital.HospitalDashboard;
import com.kurukshetra.view.nurse.NurseDashboardPage;
import com.kurukshetra.view.police.PoliceDashboard;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        System.out.println("Shree Ganeshay Namhaa!!");
        Application.launch(DriverDashboard.class,args);
    }
}

