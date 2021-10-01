package com.epam.rd.java.basic.model.mapper;

import com.epam.rd.java.basic.model.Cart;
import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.StatusOrder;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.OrderDTO;
import com.epam.rd.java.basic.service.util.MyPageable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderMapperTest {

    private static Order order;
    private static User user;
    private static String create;
    private static String update;
    private static BigDecimal totalPrice;

    @BeforeAll
    static void setUp() {
        user = User.builder()
                .id(1L)
                .login("login")
                .build();
        create = "2020-12-31 23:59";
        update = "2020-12-31 23:59";
        Timestamp createTime = Timestamp.valueOf(create + ":59");
        Timestamp updateTime = Timestamp.valueOf(update + ":59");
        totalPrice = BigDecimal.valueOf(2 * 123L);
        Cart cart = Cart.builder()
                .count(2)
                .price(BigDecimal.valueOf(123L))
                .build();
        order = Order.builder()
                .id(1L)
                .user(user)
                .createTime(createTime)
                .updateTime(updateTime)
                .status(StatusOrder.REGISTERED)
                .carts(Collections.singleton(cart))
                .build();
    }


    @Test
    void toOrderDTO() {
        OrderDTO orderDTO = OrderMapper.toOrderDTO(order);
        assertEquals(String.valueOf(order.getId()), orderDTO.getId());
        assertEquals(String.valueOf(order.getUser().getLogin()), orderDTO.getLogin());
        assertEquals(create, orderDTO.getCreateTime());
        assertEquals(update, orderDTO.getUpdateTime());
        assertEquals(String.valueOf(totalPrice), orderDTO.getTotalPrice());
        assertEquals(order.getStatus().name(), orderDTO.getStatus());
    }

    @Test
    void orderDTOList() {
        List<Order> orderList = List.of(order);
        Integer currentPage = 1;
        Integer size = 5;
        Pageable pageable = MyPageable.getPageable(currentPage, size, null, null);
        Page<Order> orderPage = new PageImpl<>(orderList, pageable, 1);
        List<OrderDTO> orderDTOList = OrderMapper.orderDTOList(orderPage);
        assertEquals(String.valueOf(order.getId()), orderDTOList.get(0).getId());
        assertEquals(order.getUser().getLogin(), orderDTOList.get(0).getLogin());
        assertEquals(create, orderDTOList.get(0).getCreateTime());
        assertEquals(update, orderDTOList.get(0).getUpdateTime());
        assertEquals(String.valueOf(totalPrice), orderDTOList.get(0).getTotalPrice());
        assertEquals(order.getStatus().name(), orderDTOList.get(0).getStatus());
        assertEquals(0, orderDTOList.get(0).getCurrentPage());
        assertEquals(orderList.size(), orderDTOList.get(0).getTotalPage());

    }
}