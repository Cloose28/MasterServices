package com.nenazvan.services;

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
    model.getOrderList().stream().filter(Order::isAction).forEach(view::printOrder);
  }
}
