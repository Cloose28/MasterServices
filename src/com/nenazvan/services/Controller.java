package com.nenazvan.services;

import com.nenazvan.enums.MainMenu;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Class connecting view and model*/
public class Controller {
    private static final int COUNT_ARGUMENTS_WITHOUT_ORGANIZATION = 18;
    private static final int COUNT_ARGUMENTS_WITH_ORGANIZATION = 16;
    private static final String GET_NAME_ORGANIZATIONS = "Enter the name of the organization";
    private static final String WITH_A_CAPITAL_LETTER = " with a capital letter";
    private static final String GET_FIRST_NAME = "Enter the first name";
    private static final String GET_SURNAME = "Enter the surname";
    private static final String GET_PATRONYMIC = "Enter the patronymic";
    private static final String YYYY_MM_DD_HH_MM = "(yyyy-MM-dd HH:mm)";
    private static final String GET_DATE = "Enter the order date " + YYYY_MM_DD_HH_MM;
    private static final String GET_PRODUCT_NAME = "Enter the product name";
    private static final String OF_YOU = " of you";
    private static final String GET_COST = "Enter the cost of product (positive number)";
    private static final String GET_PHONE_NUMBER = "Enter the phone number (8980...)";
    private static final String OF_MASTER = " of master";
    private static final String YES_1_OR_0_NO = "1(Yes) or 0(No)";

    /** Variable management console*/
    private View view;
    /** Path to file with orders*/
    private static final String ORDERS_TXT = "orders.txt";
    /** List of orders*/
    private List<Order> orderList = new ArrayList<>();

    public Controller() {
        view = new View();
        orderList = getListFromFile(ORDERS_TXT);

    }

    /** Reading the start data*/
    private List<Order> getListFromFile(String fileName) {
        view.printMessage("Reading the initial data from the file ... please wait ...");
        List<Order> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                list.add(createObjectFromString(scanner.nextLine()));
            }
            view.printMessage("The initial data is successfully received!");
            scanner.close();
        } catch (FileNotFoundException e) {
            view.printErrorMessage("Can't found the file!");
        }
        return list;
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
        return length == COUNT_ARGUMENTS_WITH_ORGANIZATION || length == COUNT_ARGUMENTS_WITHOUT_ORGANIZATION;
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
                    orderList.stream().filter(Order::isAction).forEach(order -> view.printOrder(order));
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
        LocalDateTime begin = Order.getDateFromText(getCorrectDate("Enter begin date, format " + YYYY_MM_DD_HH_MM));
        orderList.stream()
                .filter(order -> order.getEstimatedDate().isBefore(begin))
                .filter(Order::isAction)
                .forEach(order -> view.printOrder(order));
        view.printMessage("It is all matching orders");
    }

    /** Method searches for all orders specified by the master for a certain period*/
    private void getOrdersOfMaster() {
        String masterName = getMasterName();
        LocalDateTime begin = Order.getDateFromText(getCorrectDate("Enter begin date, format " + YYYY_MM_DD_HH_MM));
        LocalDateTime end = Order.getDateFromText(getCorrectDate("Enter end date, format " + YYYY_MM_DD_HH_MM));
        orderList.stream()
                .filter((order -> order.getMasterName().equals(masterName)))
                .filter(order -> order.getOrderDate().isAfter(begin))
                .filter(order -> order.getEstimatedDate().isBefore(end))
                .forEach(order -> view.printOrder(order));
        view.printMessage("It is all matching masters");
    }

    /** The method receives the name of the master*/
    private String getMasterName() {
        String answer = getCorrectString(GET_FIRST_NAME + OF_MASTER + WITH_A_CAPITAL_LETTER);
        answer += " " + getCorrectString(GET_SURNAME + OF_MASTER + WITH_A_CAPITAL_LETTER);
        answer += " " + getCorrectString(GET_PATRONYMIC + OF_MASTER + WITH_A_CAPITAL_LETTER);
        return answer;
    }

    /** A method returns the correct word*/
    private String getCorrectString(String question) {
        String answer;
        do {
            answer = view.getChoice(question);
        } while (!Order.isAValidString(answer));
        return answer;
    }

    /** A method returns the correct date*/
    private String getCorrectDate(String question) {
        String answer;
        do {
            answer = view.getChoice(question);
        } while (!Order.isAValidDate(answer));
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
        for (Order order : orderList) {
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
            if (-1 <= number && number < orderList.size()) {
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
        orderList.remove(number);
    }

    /** Display all orders with numbers*/
    private void showListOfOrders() {
        for (Integer i = 0; i < orderList.size(); i++) {
            view.printMessage(i.toString() + ") " + orderList.get(i).toString());
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
        int count = -1;
        String[] result;
        String answer = getAnswerOnBoolean();
        if (answer.equals("1")) {
            result = new String[COUNT_ARGUMENTS_WITH_ORGANIZATION];
            count = addAnswerToResult(count, result, answer);
            count = getOrganizationName(count, result, GET_NAME_ORGANIZATIONS + WITH_A_CAPITAL_LETTER);
        } else {
            result = new String[COUNT_ARGUMENTS_WITHOUT_ORGANIZATION];
            count = addAnswerToResult(count, result, answer);
            count = getName(count, result, GET_FIRST_NAME + OF_YOU + WITH_A_CAPITAL_LETTER,
                    GET_SURNAME + OF_YOU + WITH_A_CAPITAL_LETTER, GET_PATRONYMIC +
                            OF_YOU + WITH_A_CAPITAL_LETTER);
        }
        count = getInfoAboutData(count, result, GET_DATE + " of order", GET_DATE + " of estimate");
        count = getProductName(count, result);
        count = getCost(count, result);
        count = getPhoneNumber(count, result);
        count = getName(count, result, GET_FIRST_NAME + OF_MASTER + WITH_A_CAPITAL_LETTER,
                GET_SURNAME + OF_MASTER + WITH_A_CAPITAL_LETTER,
                GET_PATRONYMIC + OF_MASTER + WITH_A_CAPITAL_LETTER);
        getInfoAboutProduct(count, result, "It is a new product? " + YES_1_OR_0_NO,
                "It is a repair product? " + YES_1_OR_0_NO,
                "It is a duplicate product? " + YES_1_OR_0_NO,
                "It is a search for defects product? " + YES_1_OR_0_NO);
        Order newOrder = Order.getOrderFromParameters(result);
        if (addOrder(newOrder)) return;
        view.printErrorMessage("This order already exists!");
    }

    /** The method adds the order, if such does not yet have*/
    private boolean addOrder(Order newOrder) {
        if (!orderList.contains(newOrder)) {
            orderList.add(newOrder);
            view.printMessage("The order was successfully added!");
            return true;
        }
        return false;
    }

    /** The method receives the response in the form of boolean*/
    private String getAnswerOnBoolean() {
        String answer;
        do {
            answer = view.getChoice("Do you organization? " + YES_1_OR_0_NO);
        } while (!Order.isAValidBool(answer));
        return answer;
    }

    /** The method receives the correct value of the price*/
    private int getCost(int count, String[] result) {
        String answer;
        do {
            answer = view.getChoice(GET_COST);
        } while (!Order.isAValidCost(answer));
        count = addAnswerToResult(count, result, answer);
        return count;
    }

    /** The method receives a valid phone number*/
    private int getPhoneNumber(int count, String[] result) {
        String answer;

        do {
            answer = view.getChoice(GET_PHONE_NUMBER);
        } while (!Order.isAValidPhoneNumber(answer));
        count = addAnswerToResult(count, result, answer);
        return count;
    }

    /** Method gets the date*/
    private int getInfoAboutData(int count, String[] result, String question, String question2) {
        count = getCorrectData(count, result, question);
        count = getCorrectData(count, result, question2);
        return count;
    }

    /** The method receives the correct value date*/
    private int getCorrectData(int count, String[] result, String question) {
        String[] split = getCorrectDate(question).split("\\s+");
        count = addAnswerToResult(count, result, split[0]);
        count = addAnswerToResult(count, result, split[1]);
        return count;
    }

    /** The method adds the value in the result array*/
    private int addAnswerToResult(int count, String[] result, String answer) {
        result[++count] = answer;
        return count;
    }

    /** Getting the name of the organization*/
    private int getOrganizationName(int count, String[] result, String question) {
        count = getCorrectAnswerString(count, result, question);
        return count;
    }

    /** The method receives the name from the user*/
    private int getName(int count, String[] result, String question, String question2, String question3) {
        count = getCorrectAnswerString(count, result, question);
        count = getCorrectAnswerString(count, result, question2);
        count = getCorrectAnswerString(count, result, question3);
        return count;
    }

    /** The method receives the correct word*/
    private int getCorrectAnswerString(int count, String[] result, String question) {
        count = addAnswerToResult(count, result, getCorrectString(question));
        return count;
    }

    /** The method receives the correct product name*/
    private int getProductName(int count, String[] result) {
        count = getCorrectAnswerString(count, result, GET_PRODUCT_NAME + WITH_A_CAPITAL_LETTER);
        return count;
    }

    /** The method receives values of type bool from the user*/
    private void getInfoAboutProduct(int count, String[] result, String question, String question2, String question3, String question4) {
        count = getCorrectAnswerBool(count, result, question);
        count = getCorrectAnswerBool(count, result, question2);
        count = getCorrectAnswerBool(count, result, question3);
        getCorrectAnswerBool(count, result, question4);
    }

    /** The method receives the correct Boolean value*/
    private int getCorrectAnswerBool(int count, String[] result, String question) {
        String answer;
        do {
            answer = view.getChoice(question);
        } while (!Order.isAValidBool(answer));
        count = addAnswerToResult(count, result, answer);
        return count;
    }
}
