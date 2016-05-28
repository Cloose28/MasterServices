package com.nenazvan.services;

/**
 * Class creates a new order through the user
 */
public class AddOrderCommand implements ICommand {
  private static final String WITH_A_CAPITAL_LETTER = " with a capital letter";
  private static final String GET_FIRST_NAME = "Enter the first name";
  private static final String GET_SURNAME = "Enter the surname";
  private static final String YYYY_MM_DD_HH_MM = "(yyyy-MM-dd HH:mm)";
  private static final String GET_PATRONYMIC = "Enter the patronymic";
  private static final String OF_MASTER = " of master";
  private static final String GET_DATE = "Enter the order date " + YYYY_MM_DD_HH_MM;
  private static final String OF_YOU = " of you";
  private static final String GET_PRODUCT_NAME = "Enter the product name";
  public static final String GET_NAME_ORGANIZATIONS = "Enter the name of the organization";
  private static final String YES_1_OR_0_NO = "1(Yes) or 0(No)";


  private final ConsoleView view;
  private final Model model;
  private final ConsoleIODataForModel consoleIODataForModel;

  public AddOrderCommand(ConsoleView view, Model model, ConsoleIODataForModel consoleIODataForModel) {
    this.view = view;
    this.model = model;
    this.consoleIODataForModel = consoleIODataForModel;
  }

  @Override
  public void perform() {
    int count = -1;
    String[] result;
    String answer = consoleIODataForModel.getAnswerOnBoolean(view);
    if (answer.equals("1")) {
      result = new String[Order.COUNT_ARGUMENTS_WITH_ORGANIZATION];
      count = consoleIODataForModel.addAnswerToResult(count, result, answer);
      count = getOrganizationName(count, result, GET_NAME_ORGANIZATIONS + WITH_A_CAPITAL_LETTER);
    } else {
      result = new String[Order.COUNT_ARGUMENTS_WITHOUT_ORGANIZATION];
      count = consoleIODataForModel.addAnswerToResult(count, result, answer);
      count = getName(count, result, GET_FIRST_NAME + OF_YOU + WITH_A_CAPITAL_LETTER,
              GET_SURNAME + OF_YOU + WITH_A_CAPITAL_LETTER, GET_PATRONYMIC +
                      OF_YOU + WITH_A_CAPITAL_LETTER);
    }
    count = getInfoAboutData(count, result, GET_DATE + " of order", GET_DATE + " of estimate");
    count = getProductName(count, result);
    count = consoleIODataForModel.getCost(count, result);
    count = consoleIODataForModel.getPhoneNumber(count, result);
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

  /**
   * The method receives the correct value date
   */
  private int getCorrectData(int count, String[] result, String question) {
    String[] split = consoleIODataForModel.getCorrectDate(question).split("\\s+");
    count = consoleIODataForModel.addAnswerToResult(count, result, split[0]);
    count = consoleIODataForModel.addAnswerToResult(count, result, split[1]);
    return count;
  }

  /**
   * Method gets the date
   */
  private int getInfoAboutData(int count, String[] result, String question, String question2) {
    count = getCorrectData(count, result, question);
    count = getCorrectData(count, result, question2);
    return count;
  }

  /**
   * Getting the name of the organization
   */
  private int getOrganizationName(int count, String[] result, String question) {
    count = getCorrectAnswerString(count, result, question);
    return count;
  }

  /**
   * The method receives the name from the user
   */
  private int getName(int count, String[] result, String question, String question2, String question3) {
    count = getCorrectAnswerString(count, result, question);
    count = getCorrectAnswerString(count, result, question2);
    count = getCorrectAnswerString(count, result, question3);
    return count;
  }

  /**
   * The method receives the correct product name
   */
  private int getProductName(int count, String[] result) {
    count = getCorrectAnswerString(count, result, GET_PRODUCT_NAME + WITH_A_CAPITAL_LETTER);
    return count;
  }

  /**
   * The method receives values of type bool from the user
   */
  private void getInfoAboutProduct(int count, String[] result, String question, String question2, String question3, String question4) {
    count = consoleIODataForModel.getCorrectAnswerBool(count, result, question);
    count = consoleIODataForModel.getCorrectAnswerBool(count, result, question2);
    count = consoleIODataForModel.getCorrectAnswerBool(count, result, question3);
    consoleIODataForModel.getCorrectAnswerBool(count, result, question4);
  }

  /**
   * The method adds the order, if such does not yet have
   */
  private boolean addOrder(Order newOrder) {
    boolean result = model.addOrder(newOrder);
    view.printMessage("The order was successfully added!");
    return result;
  }

  /**
   * The method receives the correct word
   */
  private int getCorrectAnswerString(int count, String[] result, String question) {
    count = consoleIODataForModel.addAnswerToResult(count, result, consoleIODataForModel.getCorrectString(question));
    return count;
  }
}
