package com.nenazvan.services;

public interface IView {
  void printMessage(String message);
  void printErrorMessage(String errorMessage);
  void printOrder(Order order);

  String getChoice(String message);
}
