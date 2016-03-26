package com.nenazvan.services;

import java.util.Scanner;

/** Class interacts with the user*/
public class View {
    /** To work with the console*/
    private Scanner console = new Scanner(System.in);

    /** Display main options*/
    public void showMainMenu() {
        System.out.println("The list of features:" +
                "\n 1) Add order" +
                "\n 2) Delete order" +
                "\n 3) Display orders by the master for the period" +
                "\n 4) Display current orders" +
                "\n 5) Display orders expired during the period" +
                "\n 6) Save and exit" );
    }

    /** Closing at the end of the work*/
    public void closeConsole() {
        console.close();
    }

    /** Error message to console*/
    public void printErrorMessage(String message) {
        System.out.println((char) 27 + "[31m" + message + (char) 27 + "[0m");
    }

    /** Message to console*/
    public void printMessage(String message) {
        System.out.println((char) 27 + "[32m" + message + (char) 27 + "[0m");
    }

    /** The method displays the order*/
    public void printOrder(Order order) {
        System.out.println(order);
    }

    /** Method asks and receives a response from the user*/
    public String getChoice(String question) {
        System.out.println((char) 27 + "[32m" + question + (char)27 + "[0m");
        return console.nextLine();
    }
}
