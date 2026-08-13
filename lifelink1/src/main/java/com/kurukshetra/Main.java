package com.kurukshetra;

import com.kurukshetra.view.family.HomePage;
import com.kurukshetra.view.nurse.NurseDashboardPage;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        System.out.println("Shree Ganeshay Namhaa!!");
        Application.launch(NurseDashboardPage.class,args);
    }
}