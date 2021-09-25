package com.epam.rd.java.basic.controller.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortField {
    price("За ціною"),
    createTime("Датою"),
    name("Назвою");

    private final String field;
}
