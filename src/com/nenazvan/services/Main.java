package com.nenazvan.services;

public class Main {
  /**
   * The entry point to the program
   */
  public static void main(String[] args) {
    final boolean choice = false;
    if (choice) {
      new Controller().statrtConsoleApplication();
    } else {
      try {
        new GUIApplication().startGUIApplication();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}

