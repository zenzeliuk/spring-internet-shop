package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.controller.util.Helper;
import com.epam.rd.java.basic.model.*;
import com.epam.rd.java.basic.repository.CartRepository;
import com.epam.rd.java.basic.repository.OrderRepository;
import com.epam.rd.java.basic.service.OrderService;
import com.epam.rd.java.basic.service.util.MyPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;


    @Override
    public Order addItemToOrderFromSession(Item item, Order orderFromSession) {
        Order order;
        if (orderFromSession == null) {
            orderFromSession = new Order();
            orderFromSession.setStatus(StatusOrder.OPEN);
            order = orderRepository.save(orderFromSession);
        }else {
            order = orderRepository.getOne(orderFromSession.getId());
        }
        Cart cart = Cart.builder()
                .count(1)
                .order(order)
                .price(item.getPrice())
                .item(item)
                .build();
        cart = cartRepository.save(cart);
        order.getCarts().add(cart);
        order.setTotalPrice(Helper.getTotalPrice(order.getCarts()));
        return orderRepository.save(order);
    }

    @Override
    public void addItemToOpenOrderForUser(Item item, User user) {
        Optional<Order> optionalOrder = orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user);
        Order order;
        if (optionalOrder.isEmpty()) {
            order = Order.builder()
                    .user(user)
                    .carts(new HashSet<>())
                    .status(StatusOrder.OPEN)
                    .build();
            order = orderRepository.save(order);
        } else {
            order = optionalOrder.get();
        }
        Cart cart = Cart.builder()
                .count(1)
                .order(order)
                .price(item.getPrice())
                .item(item)
                .build();
        cart = cartRepository.save(cart);
        order.getCarts().add(cart);
        order.setTotalPrice(Helper.getTotalPrice(order.getCarts()));
        orderRepository.save(order);


//        Cart cart = Cart.builder()
//                .count(1)
//                .order(order)
//                .price(item.getPrice())
//                .item(item)
//                .build();
//        cartRepository.save(cart);
//        order = orderRepository.getOne(order.getId());
//        order.setTotalPrice(Helper.getTotalPrice(order.getCarts()));
//        orderRepository.save(order);
    }

    @Override
    public void mergeCartsBetweenSessionAndUserWithStatusOpen(Order orderFromSession, User user) {
        Optional<Order> orderUserWithStatusOpen = orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user);
        if (orderUserWithStatusOpen.isEmpty()) {
            orderFromSession.setUser(user);
            orderRepository.save(orderFromSession);
        } else {
            Order order = orderUserWithStatusOpen.get();
            for (Cart cartSession : orderFromSession.getCarts()) {
                for (Cart cartUser : order.getCarts()) {
                    if (cartSession.getItem().equals(cartUser.getItem())) {
                        cartRepository.delete(cartSession);
                    } else {
                        cartSession.setOrder(order);
                        cartRepository.save(cartSession);
                    }
                }
            }
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void update(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Page<Order> getPage(BigDecimal priceFrom, BigDecimal priceTo, String statusOrder,
                               Integer page, Integer size, String sortField, String sortDir, User user) {
        Pageable pageable = MyPageable.getPageable(page, size, sortField, sortDir);
        StatusOrder status = StatusOrder.getStatusOrder(statusOrder);

        if (status == null) {
            status = StatusOrder.REGISTERED;
        }
        if (priceFrom == null) {
            priceFrom = BigDecimal.ZERO;
        }
        if (priceTo == null) {
            priceTo = new BigDecimal(999999999);
        }

        if (user == null) {
            return orderRepository.findAllWithFilter(pageable,priceFrom, priceTo, status);
        } else {
            return orderRepository.findAllWithFilterAndUser(pageable, user, priceFrom, priceTo, status);
        }
    }


}
