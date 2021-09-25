package com.epam.rd.java.basic.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Param {
    CATEGORY("category"),
    BRAND("brand"),
    COLOR("color");

    private final String name;

}
