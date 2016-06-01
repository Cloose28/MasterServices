package com.nenazvan.services;

import javafx.scene.control.TextArea;

public class GUIView implements IView {
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
}
