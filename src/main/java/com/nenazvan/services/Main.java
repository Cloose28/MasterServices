package com.nenazvan.services;

import com.nenazvan.services.consoleApplication.ConsoleController;
import com.nenazvan.services.guiApplication.GUIApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
  /**
   * The entry point to the program
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("No arguments, you need to specify the path to property file.");
      System.exit(0);
    }
    String typeApplication = getTypeApplication(args[0]);
    launchApplication(typeApplication);
  }

  /**
   * If property not found program was stopped and message sent to console
   */
  private static String getTypeApplication(String arg) {
    Properties property = new Properties();
    String typeApplication = "";
    try {
      FileInputStream fis = new FileInputStream(arg);
      property.load(fis);

      typeApplication = property.getProperty("typeApplication");
    } catch (IOException e) {
      System.err.println("Fail: File with properties not found!");
    }
    return typeApplication;
  }

  /**
   *  The method start new application according @param.
   * @param typeApplication can be console or gui.
   */
  private static void launchApplication(String typeApplication) {
    switch (typeApplication) {
      case "console":
        new ConsoleController().launchApplication();
      break;
      case "gui":
        try {
          new GUIApplication().launchApplication();
        } catch (Exception e) {
          e.printStackTrace();
          System.out.println("Can't launchApplication GUIApplication");
        }
        break;
    }
  }
}

