package com.nenazvan.services;

import java.util.Scanner;

/**
 * Class interacts with the user
 */
public class ConsoleView implements IView {
  private Model model;

  /**
   * To work with the console
   */
  private final Scanner console = new Scanner(System.in);

  public ConsoleView() {
  }

  public ConsoleView(MenuCallBack menuCallBack, Model model) {
    this.model = model;
    Thread consoleViewThread = new Thread() {
      @Override
      public void run() {
        showMainMenu();
        while (true) {
          ICommand userSelection = getUserSelection();
          if (userSelection == null) continue;
          menuCallBack.menuSelected(userSelection);
          if (userSelection instanceof SaveModelDataCommand) break;
        }
      }
    };
    consoleViewThread.start();
  }

  /** The method gets user command from console*/
  private ICommand getUserSelection() {
    String choice = getChoice("Please, choice any options: ");
    switch (choice) {
      case "1":
        return new AddOrderCommand(this, model, new ConsoleIODataForModel(this));
      case "2":
        return new DeleteOrderCommand(this, model);
      case "3":
        return new GetOrdersOfMasterCommand(this, model, new ConsoleIODataForModel(this));
      case "4":
        return new GetActualOrdersCommand(this, model);
      case "5":
        return new GetExpiredOrdersCommand(this, model, new ConsoleIODataForModel(this));
      case "6":
        return new SaveModelDataCommand(this, model);
      default:
        printErrorMessage("You have entered an invalid number! Please re-enter your choice");
    }
    return  null;
  }

  /**
   * Display main options
   */
  public void showMainMenu() {
    System.out.println("The list of features:" +
            "\n 1) Add order" +
            "\n 2) Delete order" +
            "\n 3) Display orders by the master for the period" +
            "\n 4) Display current orders" +
            "\n 5) Display orders expired during the period" +
            "\n 6) Save orders and exit");
  }

  /**
   * Closing at the end of the work
   */
  public void closeConsole() {
    console.close();
  }

  /**
   * Error message to console
   */
  @Override
  public void printErrorMessage(String message) {
    System.out.println((char) 27 + "[31m" + message + (char) 27 + "[0m");
  }

  /**
   * Message to console
   */
  @Override
  public void printMessage(String message) {
    System.out.println((char) 27 + "[32m" + message + (char) 27 + "[0m");
  }

  /**
   * The method displays the order
   */
  public void printOrder(Order order) {
    System.out.println(order);
  }

  /**
   * Method asks and receives a response from the user
   */
  @Override
  public String getChoice(String question) {
    System.out.println((char) 27 + "[32m" + question + (char) 27 + "[0m");
    return console.nextLine();
  }

  public interface MenuCallBack {
    void menuSelected(ICommand command);
  }
}
