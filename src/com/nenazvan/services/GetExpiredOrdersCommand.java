package com.nenazvan.services;

import java.time.LocalDateTime;

/** Class for get all orders that have been expired for the specified time period*/
public class GetExpiredOrdersCommand implements ICommand {
  private static final String YYYY_MM_DD_HH_MM = "(yyyy-MM-dd HH:mm)";
  private final ConsoleView view;
  private final Model model;
  private final ConsoleIO consoleIO;

  public GetExpiredOrdersCommand(ConsoleView view, Model model, ConsoleIO consoleIO) {
    this.view = view;
    this.model = model;
    this.consoleIO = consoleIO;
  }
  @Override
  public void perform() {
    LocalDateTime begin = Order.getDateFromText(consoleIO.getCorrectDate("Enter begin date, format " + YYYY_MM_DD_HH_MM));
    model.getOrderList().stream()
            .filter(order -> order.getEstimatedDate().isBefore(begin))
            .filter(Order::isAction)
            .forEach(view::printOrder);
    view.printMessage("It is all matching orders");
  }
}
