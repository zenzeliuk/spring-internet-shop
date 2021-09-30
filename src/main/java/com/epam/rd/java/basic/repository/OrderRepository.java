package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.StatusOrder;
import com.epam.rd.java.basic.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderByStatusAndUser(StatusOrder statusOrder, User user);


    @Query("select o from Order o " +
            "where o.user = :user " +
            "and o.totalPrice between :priceFrom and :priceTo " +
            "and o.status = :statusOrder " +
            "group by o")
    Page<Order> findAllWithFilterAndUser(
            Pageable pageable,
            @Param("user") User user,
            @Param("priceFrom") BigDecimal priceFrom,
            @Param("priceTo") BigDecimal priceTo,
            @Param("statusOrder") StatusOrder statusOrder
    );

    @Query("select o from Order o " +
            "where o.totalPrice between :priceFrom and :priceTo " +
            "and o.status = :statusOrder " +
            "group by o")
    Page<Order> findAllWithFilter(
            Pageable pageable,
            @Param("priceFrom") BigDecimal priceFrom,
            @Param("priceTo") BigDecimal priceTo,
            @Param("statusOrder") StatusOrder statusOrder
    );

}
