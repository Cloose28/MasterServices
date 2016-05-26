package com.nenazvan.services;

import com.nenazvan.enums.MainMenu;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

/** Class connecting view and model*/
public class Controller {
    private static final String WITH_A_CAPITAL_LETTER = " with a capital letter";
    private static final String YYYY_MM_DD_HH_MM = "(yyyy-MM-dd HH:mm)";
    private static final String GET_PATRONYMIC = "Enter the patronymic";
    private static final String GET_FIRST_NAME = "Enter the first name";
    private static final String GET_SURNAME = "Enter the surname";
    private static final String OF_MASTER = " of master";

  private ConsoleView view = new ConsoleView();
    /** Path to file with orders*/
    private static final String ORDERS_TXT = "orders.txt";
    /** Model store list of orders*/
    Model model = new Model();
    ConsoleIO consoleIO;

    public Controller() {
        getDataFromFile(ORDERS_TXT);
      consoleIO = new ConsoleIO(new ConsoleView());
      new ConsoleView(this::handleMenuItemSelection, model);
    }

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

    /** The method of displaying the menu and command processing*/
    public void startMenu() {
        boolean flag = true;
        while (flag) {
            view.showMainMenu();
            switch (getInstance()) {
                case ADD_ORDER:
                    addNewOrder();
                    break;
                case DELETE_ORDER:
                    deleteOrder();
                    break;
                case ORDERS_OF_MASTER:
                    getOrdersOfMaster();
                    break;
                case ACTUAL_ORDERS:
                    model.getOrderList().stream().filter(Order::isAction).forEach(order -> view.printOrder(order));
                    break;
                case EXPIRED_ORDERS:
                    getExpiredOrders();
                    break;
                case EXIT:
                    flag = closeAndSaveProgram();
                    break;
                default:
                    view.printErrorMessage("You have entered an invalid number! Please re-enter your choice");
            }
        }
    }

    /** Method get all orders that have been expired for the specified time period*/
    private void getExpiredOrders() {
        LocalDateTime begin = Order.getDateFromText(consoleIO.getCorrectDate("Enter begin date, format " + YYYY_MM_DD_HH_MM));
        model.getOrderList().stream()
                .filter(order -> order.getEstimatedDate().isBefore(begin))
                .filter(Order::isAction)
                .forEach(order -> view.printOrder(order));
        view.printMessage("It is all matching orders");
    }

    /** Method searches for all orders specified by the master for a certain period*/
    private void getOrdersOfMaster() {
        String masterName = getMasterName();
        LocalDateTime begin = Order.getDateFromText(consoleIO.getCorrectDate("Enter begin date, format " + YYYY_MM_DD_HH_MM));
        LocalDateTime end = Order.getDateFromText(consoleIO.getCorrectDate("Enter end date, format " + YYYY_MM_DD_HH_MM));
        model.getOrderList().stream()
                .filter((order -> order.getMasterName().equals(masterName)))
                .filter(order -> order.getOrderDate().isAfter(begin))
                .filter(order -> order.getEstimatedDate().isBefore(end))
                .forEach(order -> view.printOrder(order));
        view.printMessage("It is all matching masters");
    }

    /** The method receives the name of the master*/
    private String getMasterName() {
        String answer = consoleIO.getCorrectString(GET_FIRST_NAME + OF_MASTER + WITH_A_CAPITAL_LETTER);
        answer += " " + consoleIO.getCorrectString(GET_SURNAME + OF_MASTER + WITH_A_CAPITAL_LETTER);
        answer += " " + consoleIO.getCorrectString(GET_PATRONYMIC + OF_MASTER + WITH_A_CAPITAL_LETTER);
        return answer;
    }

    /** The method for saving data and closing the form*/
    private boolean closeAndSaveProgram() {
        try {
            saveData();
        } catch (IOException e) {
            view.printErrorMessage("Error saving data to a file");
        }
        view.closeConsole();
        return false;
    }

    /** The method saves a list of orders*/
    private void saveData() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(ORDERS_TXT));
        for (Order order : model.getOrderList()) {
            saveOrderInFile(out, order);
        }
        out.close();
    }

    /** Method to save the order in the file*/
    private void saveOrderInFile(BufferedWriter out, Order order) throws IOException {
        out.write(boolToString(order.isOrganization()) + " ");
        out.write(order.getCustomerName() + " ");
        out.write(order.getOrderDate().toString().replace("T", " ") + " ");
        out.write(order.getEstimatedDate().toString().replace("T", " ") + " ");
        out.write(order.getProductName() + " ");
        out.write(order.getCost() + " ");
        out.write(order.getPhoneNumber() + " ");
        out.write(order.getMasterName() + " ");
        out.write(boolToString(order.isMake()) + " ");
        out.write(boolToString(order.isRepair()) + " ");
        out.write(boolToString(order.isDuplicate()) + " ");
        out.write(boolToString(order.isSearchForDefects()) + "\n");
    }

    /** Method that translates bool to a string*/
    private String boolToString(boolean bool) {
        return bool ? "1" : "0";
    }

    /** Method for the removal of the orders entered at number*/
    private void deleteOrder() {
        showListOfOrders();
        String result;
        do {
            result = view.getChoice("Please, select the order number that you want to delete, or \"-1\" to exit");
        } while (checkAndExecute(result));
    }

    /** Method validates and performs the action*/
    private boolean checkAndExecute(String result) {
        try {
            int number = Integer.parseInt(result);
            if (-1 <= number && number < model.getOrderList().size()) {
                executeAction(number);
            } else {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    /** The method removes the order, if the number is not -1*/
    private void executeAction(int number) {
        if (number == -1) return;
        model.removeOrder(number);
    }

    /** Display all orders with numbers*/
    private void showListOfOrders() {
        for (Integer i = 0; i < model.getOrderList().size(); i++) {
            view.printMessage(i.toString() + ") " + model.getOrderList().get(i).toString());
        }
    }

    /** The method converts the number to enum*/
    private MainMenu getInstance() {
        int choiceInt;
        try {
            choiceInt = Integer.parseInt(view.getChoice("Please, choice any options: ")) - 1;
        } catch (Exception e) {
            choiceInt = -1;
        }
        return MainMenu.getInstance(choiceInt);
    }

    /** Method creates a new order through the user*/
    private void addNewOrder() {
    }


}
