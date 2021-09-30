package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.OrderDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<Order> findById(Long id);
    void mergeCartsBetweenSessionAndUserWithStatusOpen(Order orderFromSession, User user);
    boolean changeStatus(Long id, String status);
    List<OrderDTO> getOrdersWithFilter(BigDecimal priceFrom, BigDecimal priceTo, String statusOrder, User user,
                                       Integer page, Integer size, String sortField, String sortDir);
}
