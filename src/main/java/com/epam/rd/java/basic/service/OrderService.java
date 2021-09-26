package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.User;

public interface OrderService {

    Order getOrderUser(User user);
    Order addItemToOrderFromSession(Item item, Order orderFromSession);
    void mergeOrderFromSessionAndOrderUsers(Order orderFromSession, Order orderUsers);
    void addItemForOrderUser(Item item, Order orderUser);
}
