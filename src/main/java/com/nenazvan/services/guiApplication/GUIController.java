package com.nenazvan.services.guiApplication;

import com.j256.ormlite.dao.DaoManager;
import com.nenazvan.services.Model;
import com.nenazvan.services.Order;
import com.nenazvan.services.commands.*;
import com.nenazvan.services.consoleApplication.ConsoleIODataForModel;
import com.nenazvan.services.db.dbHelper;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.sql.SQLException;

/**
 * Class operate with GUI application
 */
public class GUIController {
  /**Will print logs of program*/
  public TextArea textArea;
  /** Model store list of orders*/
  private Model model;
  /** For print message to user*/
  private GUIView view;
  /** For gets data to model from user*/
  ConsoleIODataForModel consoleIODataForModel;

  @FXML
  private void initialize() {
    view = new GUIView(textArea);
    model = getModel();
    consoleIODataForModel = new ConsoleIODataForModel(view);
  }

  /**
   * Trying to create a model
   * @return message of exception if connection was not found, the program will be stopped
   */
  private Model getModel() {
    try {
      return new Model(DaoManager.createDao(new dbHelper().getConnectionSource(), Order.class));
    } catch (SQLException exception) {
      view.printErrorMessage("The database connection is not successfully, check properties of connection");
      System.exit(0);
    }
    return null;
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
}
