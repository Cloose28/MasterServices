package com.nenazvan.services.guiApplication;

import com.nenazvan.services.IView;
import com.nenazvan.services.Order;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class GUIView implements IView {
  /**
   * Variable for output logs of program
   */
  private final TextArea textArea;

  public GUIView(TextArea textArea) {
    this.textArea = textArea;
  }

  @Override
  public void printMessage(String message) {
    textArea.appendText("\n" + message);
  }

  @Override
  public void printErrorMessage(String errorMessage) {
    textArea.appendText("\n" + errorMessage);
  }

  /**
   * Receiving data from the user for the model
   */
  @Override
  public String getChoice(String message) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Input User Data");
    dialog.setContentText(message);
    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()) {
      return result.get();
    }
    return "";
  }

  @Override
  public void printOrder(Order order) {
    textArea.appendText("\n" + order + "\r");
  }
}
