package com.nenazvan.services;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class Model {
  /**
   * For connect and manage BD
   */
  private Dao<Order, Integer> orderDao = null;

  public Model(Dao<Order, Integer> orderDao) {
    this.orderDao = orderDao;
  }

  public List<Order> getOrderList() throws SQLException {
    return Collections.unmodifiableList(orderDao.queryForAll());
  }

  public int getNumberOfOrders() throws SQLException {
    return (int) orderDao.countOf();
  }

  public boolean addOrder(Order order) throws SQLException {
    if (!isDistinctOrder(order)) {
      orderDao.create(order);
      return true;
    }
    return false;
  }

  private boolean isDistinctOrder(Order order) throws SQLException {
    List<Order> orders = orderDao.queryForAll();
    return !orders.contains(order);
  }

  public void removeOrder(int id) throws SQLException {
    orderDao.deleteById(id);
  }
}
