package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.StatusOrder;
import com.epam.rd.java.basic.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAllByStatusAndUser(StatusOrder statusOrder, User user);
    Order getOrderUser(User user);
    Order addItemToOrderFromSession(Item item, Order orderFromSession);
    void mergeOrderFromSessionAndOrderUsers(Order orderFromSession, Order orderUsers);
    void addItemForOrderUser(Item item, Order orderUser);
    Optional<Order> findById(Long id);
}
