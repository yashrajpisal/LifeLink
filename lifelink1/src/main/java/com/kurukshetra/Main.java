package com.kurukshetra;

import com.kurukshetra.view.Welcome;
import com.kurukshetra.view.driver.DriverDashboard;
import com.kurukshetra.view.family.Start;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        System.out.println("Shree Ganeshay Namhaa!!");
        Application.launch(DriverDashboard.class,args);
    }
}

