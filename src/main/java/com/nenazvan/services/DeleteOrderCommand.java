package com.nenazvan.services;

/** For the removal of the orders entered at number*/
public class DeleteOrderCommand implements ICommand {
  private final Model model;
  private final ConsoleView view;

  public DeleteOrderCommand(ConsoleView view, Model model) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void perform() {
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

}
