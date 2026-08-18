package com.kurukshetra;

import java.sql.Driver;

import com.kurukshetra.view.Welcome;
import com.kurukshetra.view.driver.DriverDetailsView;
import com.kurukshetra.view.nurse.NurseDetailsView;
import javafx.application.Application;

public class Main{
    public static void main(String[] args) {
        //Application.launch(NurseDetailsView.class, args);
        Application.launch(Welcome.class, args);
        //Application.launch(DriverDetailsView.class, args);
    }
}