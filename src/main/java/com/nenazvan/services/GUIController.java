package com.nenazvan.services;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class GUIController {
  private final Model model = new Model();

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
