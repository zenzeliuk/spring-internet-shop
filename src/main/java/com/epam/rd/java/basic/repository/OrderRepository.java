package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.StatusOrder;
import com.epam.rd.java.basic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByStatusAndUser(StatusOrder statusOrder, User user);
}
