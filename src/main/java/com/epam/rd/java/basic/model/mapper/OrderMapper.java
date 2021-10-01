package com.epam.rd.java.basic.model.mapper;

import com.epam.rd.java.basic.controller.util.Helper;
import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.dto.OrderDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toOrderDTO(Order order) {
        String login = "";
        if (order.getUser() != null) {
            login = order.getUser().getLogin();
        }
        return OrderDTO.builder()
                .id(String.valueOf(order.getId()))
                .login(login)
                .createTime(convert(order.getCreateTime()))
                .updateTime(convert(order.getUpdateTime()))
                .totalPrice(String.valueOf(getPrice(order)))
                .status(order.getStatus().name())
                .build();
    }

    private static BigDecimal getPrice(Order order) {
        if (order.getTotalPrice() == null || Objects.equals(order.getTotalPrice(), BigDecimal.ZERO)) {
            return Helper.getTotalPrice(order.getCarts());
        } else {
            return order.getTotalPrice();
        }
    }

    public static List<OrderDTO> orderDTOList(Page<Order> orderPage) {
        List<OrderDTO> orderDTOList = orderPage.stream()
                .map(OrderMapper::toOrderDTO)
                .collect(Collectors.toList());
        if (!orderDTOList.isEmpty()){
            orderDTOList.get(0).setCurrentPage(orderPage.getNumber());
            orderDTOList.get(0).setTotalPage(orderPage.getTotalPages());
        }
        return orderDTOList;
    }

    private static String convert(Timestamp timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(timestamp);
    }
}
