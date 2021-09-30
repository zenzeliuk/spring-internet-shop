package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.User;

import javax.servlet.http.HttpSession;

public interface CartService {

    boolean addItemToCart(User user, Long itemId, HttpSession session);
    boolean deleteItem(Long cartId, User user, HttpSession session);
    boolean changeCount(Long cartId, Integer count, User user, HttpSession session);
    boolean confirmOpenOrder(User user);
    Order getOpenOrderOrReturnNull(User user, HttpSession session);
}
