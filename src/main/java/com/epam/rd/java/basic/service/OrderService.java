package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.User;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderService {

    Order addItemToOrderFromSession(Item item, Order orderFromSession);
    void addItemToOpenOrderForUser(Item item, User user);
    Optional<Order> findById(Long id);
    void update(Order order);
    Page<Order> getPage(String login, BigDecimal priceFrom, BigDecimal priceTo, String statusOrder,
                        Integer page, Integer size, String sortField, String sortDir, User user);
    void mergeCartsBetweenSessionAndUserWithStatusOpen(Order orderFromSession, User user);
}
