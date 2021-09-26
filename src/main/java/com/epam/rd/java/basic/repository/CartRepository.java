package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.Cart;
import com.epam.rd.java.basic.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByOrder(Order order);
}
