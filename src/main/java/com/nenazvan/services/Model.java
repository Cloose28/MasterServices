package com.nenazvan.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {
    /** List of orders*/
    private List<Order> orderList = new ArrayList<>();

    public List<Order> getOrderList() {
        return Collections.unmodifiableList(orderList);
    }

    public boolean addOrder(Order order) {
        if (!orderList.contains(order)) {
            orderList.add(order);
            return true;
        }
        return false;
    }

    public void removeOrder(int number) {
        orderList.remove(number);
    }
}
