package com.nenazvan.services;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class GUIController {
  /**Will print logs of program*/
  public TextArea textArea;
  /** Model store list of orders*/
  private Model model;
  /** Path to file with orders*/
  private static final String ORDERS_TXT = "src/main/resources/orders.txt";

  @FXML
  private void initialize() {
    model = new Model();
    new InitialDataToModel(model, new GUIView(textArea)).getDataFromFile(ORDERS_TXT);
  }

  private void showInformationDialog(String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(headerText);
    alert.setContentText(message);

    alert.showAndWait();
  }

  public void addOrder(ActionEvent actionEvent) {
    System.out.println("addOrder");
  }

  public void deleteOrder(ActionEvent actionEvent) {

  }

  public void getOrderOfMaster(ActionEvent actionEvent) {

  }

  public void getCurrentOrders(ActionEvent actionEvent) {

  }

  public void getExpiredOrders(ActionEvent actionEvent) {

  }

  public void saveAndExitProgram(ActionEvent actionEvent) {
    new SaveAndCloseProgramCommand(new ConsoleView(), model).perform();
    System.exit(0);
  }
}
