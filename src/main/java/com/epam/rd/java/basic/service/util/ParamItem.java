package com.epam.rd.java.basic.service.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ParamItem {
    CATEGORY("category"),
    BRAND("brand"),
    COLOR("color");

    private final String name;

}
