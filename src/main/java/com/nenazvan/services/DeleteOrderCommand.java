package com.nenazvan.services;

/** For the removal of the orders entered at number*/
public class DeleteOrderCommand implements ICommand {
  private final Model model;
  private final IView view;

  public DeleteOrderCommand(IView view, Model model) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void perform() {
    String result;
    do {
      result = view.getChoice(getListOfOrders() + "Please, select the order number that you want to delete, or \"-1\" to exit");
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

  /**
   * Display all orders with numbers
   */
  private String getListOfOrders() {
    StringBuilder builder = new StringBuilder();
    for (Integer i = 0; i < model.getOrderList().size(); i++) {
      builder.append(i.toString()).append(") ").append(model.getOrderList().get(i).toString()).append("\n");
    }
    return builder.toString();
  }

}
