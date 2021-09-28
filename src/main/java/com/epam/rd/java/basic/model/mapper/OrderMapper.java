package com.epam.rd.java.basic.model.mapper;

import com.epam.rd.java.basic.controller.util.Helper;
import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.dto.OrderDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toOrderDTO(Order order) {
        String login = "";
        if (order.getUser() != null){
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
        if (order.getTotalPrice() != BigDecimal.ZERO) {
            return order.getTotalPrice();
        } else {
            return Helper.getTotalPrice(order.getCarts());
        }
    }

    public static List<OrderDTO> orderDTOList(List<Order> orderList) {
        return orderList.stream()
                .map(OrderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }


    private static String convert(Timestamp timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(timestamp);
    }
}
