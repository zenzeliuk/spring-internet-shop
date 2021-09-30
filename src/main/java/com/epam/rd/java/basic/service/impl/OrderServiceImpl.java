package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.Cart;
import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.StatusOrder;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.OrderDTO;
import com.epam.rd.java.basic.model.mapper.OrderMapper;
import com.epam.rd.java.basic.repository.CartRepository;
import com.epam.rd.java.basic.repository.OrderRepository;
import com.epam.rd.java.basic.service.OrderService;
import com.epam.rd.java.basic.service.util.MyPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;

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
    public boolean changeStatus(Long id, String status) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty() || status == null || StatusOrder.getStatusOrder(status) == null) {
            return false;
        }
        StatusOrder statusOrder = StatusOrder.getStatusOrder(status);
        order.get().setStatus(statusOrder);
        orderRepository.save(order.get());
        return true;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<OrderDTO> getOrdersWithFilter(BigDecimal priceFrom, BigDecimal priceTo, String statusOrder, User user, Integer page, Integer size, String sortField, String sortDir) {
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
        Page<Order> result;
        if (user == null) {
            result = orderRepository.findAllWithFilter(pageable, priceFrom, priceTo, status);
        } else {
            result = orderRepository.findAllWithFilterAndUser(pageable, user, priceFrom, priceTo, status);
        }
        return OrderMapper.orderDTOList(result);
    }


}
