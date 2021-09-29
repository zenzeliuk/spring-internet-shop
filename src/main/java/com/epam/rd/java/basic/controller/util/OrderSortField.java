package com.epam.rd.java.basic.controller.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderSortField {
    createTime("За датою створення"),
    updateTime("За датою оновлення"),
    totalPrice("За ціною");
    private final String field;
}
