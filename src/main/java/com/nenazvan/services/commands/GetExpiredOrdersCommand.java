package com.nenazvan.services.commands;

import com.nenazvan.services.consoleApplication.ConsoleIODataForModel;
import com.nenazvan.services.IView;
import com.nenazvan.services.Model;
import com.nenazvan.services.Order;

import java.sql.SQLException;
import java.time.LocalDateTime;

/** Class for get all orders that have been expired for the specified time period*/
public class GetExpiredOrdersCommand implements ICommand {
  private static final String YYYY_MM_DD_HH_MM = "(yyyy-MM-dd HH:mm)";
  private final IView view;
  private final Model model;
  private final ConsoleIODataForModel consoleIODataForModel;

  public GetExpiredOrdersCommand(IView view, Model model, ConsoleIODataForModel consoleIODataForModel) {
    this.view = view;
    this.model = model;
    this.consoleIODataForModel = consoleIODataForModel;
  }
  @Override
  public void perform() {
    LocalDateTime begin = Order.getDateFromText(consoleIODataForModel.getCorrectDate("Enter begin date, format " + YYYY_MM_DD_HH_MM));
    view.printMessage("");
    try {
      model.getOrderList().stream()
              .filter(order -> order.getEstimatedDate().isBefore(begin))
              .filter(Order::isAction)
              .forEach(view::printOrder);
    } catch (SQLException e) {
      view.printErrorMessage("The database connection is not successfully, check properties of connection");
    }
    view.printMessage("It is all matching orders");
  }
}
