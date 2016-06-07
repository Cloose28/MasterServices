package com.nenazvan.services;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

/**
 * A class stores and processes all order data
 */
@DatabaseTable(tableName = "order")
public class Order {
  public static final int COUNT_ARGUMENTS_WITHOUT_ORGANIZATION = 18;
  public static final int COUNT_ARGUMENTS_WITH_ORGANIZATION = 16;
  @DatabaseField(generatedId = true, canBeNull = false)
  private int id = new Random().nextInt();
  /**
   * Date and time of order.
   */
  @DatabaseField(dataType = DataType.SERIALIZABLE)
  private LocalDateTime orderDate;
  /**
   * Date and time calculation.
   */
  @DatabaseField (dataType = DataType.SERIALIZABLE)
  private LocalDateTime estimatedDate;

  /**
   * True if the organization
   */
  @DatabaseField
  private boolean isOrganization;
  /**
   * The full name of the customer
   */
  @DatabaseField
  private String customerName;

  /**
   * The name of the product
   */
  @DatabaseField
  private String productName;
  /**
   * The cost of order in roubles
   */
  @DatabaseField
  private int cost;
  /**
   * Phone customer (example 8980..)
   */
  @DatabaseField
  private String phoneNumber;
  /**
   * The full name of the master
   */
  @DatabaseField
  private String masterName;

  /**
   * True if in action
   */
  @DatabaseField
  private boolean isAction;
  /**
   * True if is ready
   */
  @DatabaseField
  private boolean isReady;
  /**
   * True if is exerted
   */
  @DatabaseField
  private boolean isExerted;
  /**
   * True if the make
   */
  @DatabaseField
  private boolean isMake;
  /**
   * True if the repair
   */
  @DatabaseField
  private boolean isRepair;
  /**
   * True if the duplicate
   */
  @DatabaseField
  private boolean isDuplicate;
  /**
   * True if the search for defects
   */
  @DatabaseField
  private boolean isSearchForDefects;

  public Order() {

  }

  public Order(LocalDateTime orderDate, LocalDateTime estimatedDate, boolean isOrganization,
               String customerName, String productName, int cost, String phoneNumber, String masterName,
               boolean isAction, boolean isReady, boolean isExerted, boolean isMake, boolean isRepair,
               boolean isDuplicate, boolean isSearchForDefects) {
    this.orderDate = orderDate;
    this.estimatedDate = estimatedDate;
    this.isOrganization = isOrganization;
    this.customerName = customerName;
    this.productName = productName;
    this.cost = cost;
    this.phoneNumber = phoneNumber;
    this.masterName = masterName;
    this.isAction = isAction;
    this.isReady = isReady;
    this.isExerted = isExerted;
    this.isMake = isMake;
    this.isRepair = isRepair;
    this.isDuplicate = isDuplicate;
    this.isSearchForDefects = isSearchForDefects;
  }

  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + estimatedDate.hashCode() + orderDate.hashCode() + customerName.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    if (obj == this) return true;
    Order order = (Order) obj;
    return this.orderDate.equals(order.getOrderDate()) && this.estimatedDate.equals(order.getEstimatedDate())
            && this.isOrganization == order.isOrganization() && this.customerName.equals(order.getCustomerName())
            && this.productName.equals(order.getProductName()) && this.cost == order.getCost()
            && this.phoneNumber.equals(order.getPhoneNumber()) && this.masterName.equals(order.getMasterName())
            && this.isAction == order.isAction() && this.isReady == order.isReady() && this.isExerted == order.isExerted
            && this.isMake == order.isMake() && this.isRepair == order.isRepair() && this.isDuplicate == order.isDuplicate()
            && this.isSearchForDefects == this.isSearchForDefects();
  }

  /**
   * Truth is, if the order is executed
   */
  public boolean isAction() {
    return isAction;
  }

  /**
   * Method created new Object Order from string
   */
  public static Order getOrderFromParameters(String[] parameters) {
    int count = -1;

    boolean isOrganization = getBooleanFromString(parameters[++count]);
    String customerName;
    if (isOrganization) {
      customerName = parameters[++count];
    } else {
      customerName = parameters[++count];
      customerName += " " + parameters[++count];
      customerName += " " + parameters[++count];
    }

    return new Order(getDateFromText(parameters[++count] + " " + parameters[++count]),
            getDateFromText(parameters[++count] + " " + parameters[++count]), isOrganization, customerName,
            parameters[++count], Integer.parseInt(parameters[++count]),
            parameters[++count], (parameters[++count]) + " " +
            (parameters[++count]) + " " + (parameters[++count]), true,
            false, false, getBooleanFromString(parameters[++count]), getBooleanFromString(parameters[++count]),
            getBooleanFromString(parameters[++count]), getBooleanFromString(parameters[++count]));
  }

  /**
   * Method checks for the correctness of the phone number
   */
  public static boolean isAValidPhoneNumber(String text) {
    return text.matches("^(8|\\+7)([\\d]){10}");
  }

  /**
   * Method checked text on correct format for cost
   */
  public static boolean isAValidCost(String text) {
    return text.matches("0|[1-9][\\d]*");
  }

  /**
   * Method parse text to the type LocalDateTime
   */
  public static LocalDateTime getDateFromText(String date) {
    try {
      return getLocalDateTime(date);
    } catch (DateTimeException exception) {
      System.out.println("Error parse date!!!");
      throw new IllegalArgumentException(exception.getMessage());
    }
  }

  /**
   * Method parse string to date
   */
  private static LocalDateTime getLocalDateTime(String date) throws DateTimeException {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return LocalDateTime.parse(date, formatter);
  }

  /**
   * Method will check the validity date
   */
  public static boolean isAValidDate(String date) {
    try {
      getLocalDateTime(date);
    } catch (DateTimeException e) {
      return false;
    }
    return true;
  }

  /**
   * The method will check for the correctness of the word
   */
  public static boolean isAValidString(String text) {
    return text.matches("[A-Z][a-z]+");
  }

  /**
   * The method returns true, if line "1"
   */
  public static boolean getBooleanFromString(String text) {
    return Objects.equals(text, "1");
  }

  /**
   * Method will check the correctness of the boolean type
   */
  public static boolean isAValidBool(String bool) {
    return Objects.equals(bool, "0") || Objects.equals(bool, "1");
  }

  @Override
  public String toString() {
    return "Order{" +
            "orderDate=" + orderDate +
            ", estimatedDate=" + estimatedDate +
            ", isOrganization=" + isOrganization +
            ", customerName='" + customerName + '\'' + "\n" +
            ", productName='" + productName + '\'' +
            ", cost=" + cost +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", masterName='" + masterName + '\'' + "\n" +
            ", isAction=" + isAction +
            ", isReady=" + isReady +
            ", isExerted=" + isExerted +
            ", isMake=" + isMake + "\n" +
            ", isRepair=" + isRepair +
            ", isDuplicate=" + isDuplicate +
            ", isSearchForDefects=" + isSearchForDefects +
            '}';
  }

  /**
   * Getter parse date from string to LocalDateTime
   */
  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  /**
   * Getter parse date from string to LocalDateTime
   */
  public LocalDateTime getEstimatedDate() {
    return estimatedDate;
  }

  public boolean isOrganization() {
    return isOrganization;
  }

  public String getCustomerName() {
    return customerName;
  }

  public String getProductName() {
    return productName;
  }

  public int getCost() {
    return cost;
  }

  public Integer getId() {
    return id;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getMasterName() {
    return masterName;
  }

  public boolean isMake() {
    return isMake;
  }

  public boolean isRepair() {
    return isRepair;
  }

  public boolean isDuplicate() {
    return isDuplicate;
  }

  public boolean isSearchForDefects() {
    return isSearchForDefects;
  }

  public boolean isReady() {
    return isReady;
  }
}
