package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.OrderDTO;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<Order> findById(Long id);
    void mergeCartsBetweenSessionAndUserWithStatusOpen(Order orderFromSession, User user);
    boolean changeStatus(Long id, String status);
    boolean confirmOpenOrder(User user);
    Order getOpenOrderOrReturnNull(User user, HttpSession session);
    boolean addItemToCart(User user, Long itemId, HttpSession session);
    List<OrderDTO> getOrdersWithFilter(BigDecimal priceFrom, BigDecimal priceTo, String statusOrder, User user,
                                       Integer page, Integer size, String sortField, String sortDir);
    boolean deleteItem(Long cartId, User user, HttpSession session);
}
