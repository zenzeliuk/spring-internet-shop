package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.User;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

public interface OrderService {

    Optional<Order> findById(Long id);
    Page<Order> getPage(BigDecimal priceFrom, BigDecimal priceTo, String statusOrder,
                        Integer page, Integer size, String sortField, String sortDir, User user);
    void mergeCartsBetweenSessionAndUserWithStatusOpen(Order orderFromSession, User user);
    boolean changeStatus(Long id, String status);
    boolean confirmOpenOrder(User user);
    Order getOpenOrderOrNull(User user, HttpSession session);
    boolean addItemToCart(User user, Long itemId, HttpSession session);
}
