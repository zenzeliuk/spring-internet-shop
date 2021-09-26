package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.*;
import com.epam.rd.java.basic.repository.CartRepository;
import com.epam.rd.java.basic.repository.OrderRepository;
import com.epam.rd.java.basic.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Order getOrderUser(User user) {
        Optional<Order> optionalOrder = orderRepository.findByStatusAndUser(StatusOrder.OPEN, user);
        if (optionalOrder.isEmpty()) {
            Order newOrder = Order.builder()
                    .user(user)
                    .status(StatusOrder.OPEN)
                    .build();
            return orderRepository.save(newOrder);
        } else {
            return optionalOrder.get();
        }
    }

    @Override
    public Order addItemToOrderFromSession(Item item, Order orderFromSession) {
        if (orderFromSession == null) {
            orderFromSession = new Order();
            orderFromSession.setStatus(StatusOrder.OPEN);
            orderFromSession = orderRepository.save(orderFromSession);
        }
        Cart cart = Cart.builder()
                .count(1)
                .order(orderFromSession)
                .price(item.getPrice())
                .item(item)
                .build();
        cartRepository.save(cart);
        return orderFromSession;
    }

    @Override
    public void mergeOrderFromSessionAndOrderUsers(Order orderFromSession, Order orderUsers) {
        List<Cart> cartListFromSession = cartRepository.findAllByOrder(orderFromSession);
        cartListFromSession.forEach(c -> c.setOrder(orderUsers));
        cartRepository.saveAll(cartListFromSession);
        orderRepository.delete(orderFromSession);
    }

    @Override
    public void addItemForOrderUser(Item item, Order orderUser) {
        Cart cart = Cart.builder()
                .count(1)
                .order(orderUser)
                .price(item.getPrice())
                .item(item)
                .build();
        cartRepository.save(cart);
    }

}