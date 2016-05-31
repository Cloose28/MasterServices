package com.nenazvan.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/** For saving data and closing the form*/
public class SaveAndCloseProgramCommand implements ICommand {
  /** Path to file with orders*/
  private static final String ORDERS_TXT = "orders.txt";
  private final Model model;
  private final ConsoleView view;

  public SaveAndCloseProgramCommand(ConsoleView view, Model model) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void perform() {
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter(ORDERS_TXT));
      for (Order order : model.getOrderList()) {
        saveOrderInFile(out, order);
      }
      out.close();
    } catch (IOException e) {
      view.printErrorMessage("Error saving data to a file");
    }
    view.printMessage("Bye, Bye!");
    System.exit(0);
  }
  /** Method to save the order in the file*/
  private void saveOrderInFile(BufferedWriter out, Order order) throws IOException {
    out.write(boolToString(order.isOrganization()) + " ");
    out.write(order.getCustomerName() + " ");
    out.write(order.getOrderDate().toString().replace("T", " ") + " ");
    out.write(order.getEstimatedDate().toString().replace("T", " ") + " ");
    out.write(order.getProductName() + " ");
    out.write(order.getCost() + " ");
    out.write(order.getPhoneNumber() + " ");
    out.write(order.getMasterName() + " ");
    out.write(boolToString(order.isMake()) + " ");
    out.write(boolToString(order.isRepair()) + " ");
    out.write(boolToString(order.isDuplicate()) + " ");
    out.write(boolToString(order.isSearchForDefects()) + "\n");
  }


  /** Method that translates bool to a string*/
  private String boolToString(boolean bool) {
    return bool ? "1" : "0";
  }
}
