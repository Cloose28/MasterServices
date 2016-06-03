package com.nenazvan.services;

import java.sql.SQLException;

/** Class returns the actual orders*/
public class GetActualOrdersCommand implements ICommand {
  private final IView view;
  private final Model model;

  public GetActualOrdersCommand(IView view, Model model) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void perform() {
    try {
      model.getOrderList().stream().filter(Order::isAction).forEach(view::printOrder);
    } catch (SQLException e) {
      view.printErrorMessage("The database connection is not successfully, check properties of connection");
    }
  }
}
