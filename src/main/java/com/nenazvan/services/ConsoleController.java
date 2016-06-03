package com.nenazvan.services;

import com.j256.ormlite.dao.DaoManager;
import com.nenazvan.services.db.dbHelper;

import java.sql.SQLException;

/** Class connecting view and model*/
public class ConsoleController implements Launcher {
  /** Model store list of orders*/
  private  final Model model = getModel();

  public ConsoleController() {
  }

  @Override
  public void launchApplication() {
    new ConsoleView(this::handleMenuItemSelection, model);
  }

  /** For perform command from user*/
  private void handleMenuItemSelection(ICommand command) {
    try {
      command.perform();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Trying to create a model
   * @return message of exception if connection was not found, the program will be stopped
   */
  private Model getModel() {
    try {
      return new Model(DaoManager.createDao(new dbHelper().getConnectionSource(), Order.class));
    } catch (SQLException exception) {
      new ConsoleView().printErrorMessage("The database connection is not successfully, check properties of connection");
      System.exit(0);
    }
    return null;
  }

}
