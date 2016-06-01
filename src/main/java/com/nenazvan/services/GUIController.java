package com.nenazvan.services;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class GUIController {
  /**Will print logs of program*/
  public TextArea textArea;
  /** Model store list of orders*/
  private Model model;
  /** Path to file with orders*/
  private static final String ORDERS_TXT = "src/main/resources/orders.txt";
  /** For print message to user*/
  private GUIView view;
  /** For gets data to model from user*/
  ConsoleIODataForModel consoleIODataForModel;

  @FXML
  private void initialize() {
    model = new Model();
    view = new GUIView(textArea);
    consoleIODataForModel = new ConsoleIODataForModel(view);
    new InitialDataToModel(model, view).getDataFromFile(ORDERS_TXT);
  }

  public void addOrder() {
    new AddOrderCommand(view, model, consoleIODataForModel).perform();
  }

  public void deleteOrder() {
    new DeleteOrderCommand(view, model).perform();
  }

  public void getOrderOfMaster() {
    new GetOrdersOfMasterCommand(view, model, consoleIODataForModel).perform();
  }

  public void getCurrentOrders() {
    new GetActualOrdersCommand(view, model).perform();
  }

  public void getExpiredOrders() {
    new GetExpiredOrdersCommand(view, model, consoleIODataForModel).perform();
  }

  public void saveAndExitProgram() {
    new SaveModelDataCommand(view, model).perform();
  }
}
