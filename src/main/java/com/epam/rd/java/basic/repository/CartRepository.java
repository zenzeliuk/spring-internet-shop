package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.Cart;
import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByOrder(Order order);
}
