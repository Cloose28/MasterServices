package com.nenazvan.services;

public class ConsoleIODataForModel {
    private static final String GET_PHONE_NUMBER = "Enter the phone number (8980...)";
    private static final String GET_COST = "Enter the cost of product (positive number)";
    private static final String YES_1_OR_0_NO = "1(Yes) or 0(No)";

    private IView view;

    public ConsoleIODataForModel(IView view) {
        this.view = view;
    }

    /**
     * The method adds the value in the result array
     */
    public int addAnswerToResult(int count, String[] result, String answer) {
        result[++count] = answer;
        return count;
    }

    /**
     * The method receives a valid phone number
     */
    public int getPhoneNumber(int count, String[] result) {
        String answer;
        do {
            answer = view.getChoice(GET_PHONE_NUMBER);
        } while (!Order.isAValidPhoneNumber(answer));
        count = addAnswerToResult(count, result, answer);
        return count;
    }

    /**
     * The method receives the response in the form of boolean
     */
    public String getAnswerOnBoolean(IView view) {
        String answer;
        do {
            answer = view.getChoice("Do you organization? " + YES_1_OR_0_NO);
        } while (!Order.isAValidBool(answer));
        return answer;
    }

    /**
     * The method receives the correct Boolean value
     */
    public int getCorrectAnswerBool(int count, String[] result, String question) {
        String answer;
        do {
            answer = view.getChoice(question);
        } while (!Order.isAValidBool(answer));
        count = addAnswerToResult(count, result, answer);
        return count;
    }

    /**
     * A method returns the correct word
     */
    public String getCorrectString(String question) {
        String answer;
        do {
            answer = view.getChoice(question);
        } while (!Order.isAValidString(answer));
        return answer;
    }

    /**
     * A method returns the correct date
     */
    public String getCorrectDate(String question) {
        String answer;
        do {
            answer = view.getChoice(question);
        } while (!Order.isAValidDate(answer));
        return answer;
    }

    /**
     * The method receives the correct value of the price
     */
    public int getCost(int count, String[] result) {
        String answer;
        do {
            answer = view.getChoice(GET_COST);
        } while (!Order.isAValidCost(answer));
        count = addAnswerToResult(count, result, answer);
        return count;
    }
}
