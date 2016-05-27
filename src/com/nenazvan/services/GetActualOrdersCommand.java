package com.nenazvan.services;

public class GetActualOrdersCommand implements ICommand {
  private final ConsoleView view;
  private final Model model;

  public GetActualOrdersCommand(ConsoleView view, Model model) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void perform() {
    model.getOrderList().stream().filter(Order::isAction).forEach(view::printOrder);
  }
}
