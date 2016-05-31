package com.nenazvan.services;

import java.io.*;
import java.util.Scanner;

/** Class connecting view and model*/
public class ConsoleController implements Launcher {
  private ConsoleView view = new ConsoleView();
  /** Path to file with orders*/
  private static final String ORDERS_TXT = "orders.txt";
  /** Model store list of orders*/
  private  final Model model = new Model();

  public ConsoleController() {
    getDataFromFile(ORDERS_TXT);
  }

  @Override
  public void launchApplication() {
    new ConsoleView(this::handleMenuItemSelection, model);
  }

  /** For perform command from user*/
  private void handleMenuItemSelection(ICommand command) {
    command.perform();
  }

    /** Reading the start data*/
    private void getDataFromFile(String fileName) {
        view.printMessage("Reading the initial data from the file ... please wait ...");
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                model.addOrder(createObjectFromString(scanner.nextLine()));
            }
            view.printMessage("The initial data is successfully received!");
            scanner.close();
        } catch (FileNotFoundException e) {
            view.printErrorMessage("Can't found the file!");
        }
    }

    /** Method create new object from string*/
    private Order createObjectFromString(String line) {
        Order newOrder = null;
        try {
            newOrder = Order.getOrderFromParameters(checkCorrectParameters(line));
        } catch (IllegalArgumentException e) {
            view.printErrorMessage(e.getMessage());
            System.exit(0);
        }
        return newOrder;
    }

    /** The method checks the parameters to create order*/
    private String[] checkCorrectParameters(String line) {
        String[] split = line.split("\\s+");
        if (!checkCountParameters(split.length)) {
            throw new IllegalArgumentException("Incorrect number of parameters in the string");
        }
        if (!checkAllParameters(split)) {
            throw new IllegalArgumentException("Incorrect data format in the string");
        }
        return split;
    }

    /** The method validates parameters*/
    private boolean checkAllParameters(String[] split) {
        Integer count = -1;
        boolean result = Order.isAValidBool(split[++count]);
        if (Order.getBooleanFromString(split[0])) {
            result &= Order.isAValidString(split[++count]);
        } else {
            result &= Order.isAValidString(split[++count]);
            result &= Order.isAValidString(split[++count]);
            result &= Order.isAValidString(split[++count]);
        }
        result &= Order.isAValidDate(split[++count] + " " + split[++count]);
        result &= Order.isAValidDate(split[++count] + " " + split[++count]);
        result &= Order.isAValidString(split[++count]);
        result &= Order.isAValidCost(split[++count]);
        result &= Order.isAValidPhoneNumber(split[++count]);
        result &= Order.isAValidString(split[++count]);
        result &= Order.isAValidString(split[++count]);
        result &= Order.isAValidString(split[++count]);
        result &= Order.isAValidBool(split[++count]);
        result &= Order.isAValidBool(split[++count]);
        result &= Order.isAValidBool(split[++count]);
        result &= Order.isAValidBool(split[++count]);
        return result;
    }

    /** The method checks the number of parameters*/
    private boolean checkCountParameters(int length) {
        return length == Order.COUNT_ARGUMENTS_WITH_ORGANIZATION || length == Order.COUNT_ARGUMENTS_WITHOUT_ORGANIZATION;
    }
}
