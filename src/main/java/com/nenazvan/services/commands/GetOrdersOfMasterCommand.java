package com.nenazvan.services.commands;

import com.nenazvan.services.consoleApplication.ConsoleIODataForModel;
import com.nenazvan.services.IView;
import com.nenazvan.services.Model;
import com.nenazvan.services.Order;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Searches for all orders specified by the master for a certain period
 */
public class GetOrdersOfMasterCommand implements ICommand {
  private final IView view;
  private final Model model;
  private final ConsoleIODataForModel consoleIODataForModel;


  private static final String WITH_A_CAPITAL_LETTER = " with a capital letter";
  private static final String YYYY_MM_DD_HH_MM = "(yyyy-MM-dd HH:mm)";
  private static final String GET_PATRONYMIC = "Enter the patronymic";
  private static final String GET_FIRST_NAME = "Enter the first name";
  private static final String GET_SURNAME = "Enter the surname";
  private static final String OF_MASTER = " of master";

  public GetOrdersOfMasterCommand(IView view, Model model, ConsoleIODataForModel consoleIODataForModel) {
    this.view = view;
    this.model = model;
    this.consoleIODataForModel = consoleIODataForModel;
  }

  @Override
  public void perform() {
    String masterName = getMasterName();
    LocalDateTime begin = Order.getDateFromText(consoleIODataForModel.getCorrectDate("Enter begin date, format " + YYYY_MM_DD_HH_MM));
    LocalDateTime end = Order.getDateFromText(consoleIODataForModel.getCorrectDate("Enter end date, format " + YYYY_MM_DD_HH_MM));
    try {
      model.getOrderList().stream()
              .filter((order -> order.getMasterName().equals(masterName)))
              .filter(order -> order.getOrderDate().isAfter(begin))
              .filter(order -> order.getEstimatedDate().isBefore(end))
              .forEach(view::printOrder);
    } catch (SQLException e) {
      view.printErrorMessage("The database connection is not successfully, check properties of connection");
    }
    view.printMessage("It is all matching masters");
  }

  /**
   * The method receives the name of the master
   */
  private String getMasterName() {
    String answer = consoleIODataForModel.getCorrectString(GET_FIRST_NAME + OF_MASTER + WITH_A_CAPITAL_LETTER);
    answer += " " + consoleIODataForModel.getCorrectString(GET_SURNAME + OF_MASTER + WITH_A_CAPITAL_LETTER);
    answer += " " + consoleIODataForModel.getCorrectString(GET_PATRONYMIC + OF_MASTER + WITH_A_CAPITAL_LETTER);
    return answer;
  }
}
