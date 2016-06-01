package com.nenazvan.services;

/** Class connecting view and model*/
public class ConsoleController implements Launcher {
  /** Path to file with orders*/
  private static final String ORDERS_TXT = "src/main/resources/orders.txt";
  /** Model store list of orders*/
  private  final Model model = new Model();

  public ConsoleController() {
    new InitialDataToModel(model, new ConsoleView()).getDataFromFile(ORDERS_TXT);
  }

  @Override
  public void launchApplication() {
    new ConsoleView(this::handleMenuItemSelection, model);
  }

  /** For perform command from user*/
  private void handleMenuItemSelection(ICommand command) {
    try {
      command.perform();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
