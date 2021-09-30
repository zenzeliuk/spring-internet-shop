package com.epam.rd.java.basic.controller.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserSortField {
    id("ID"),
    login("За логіном"),
    firsName("За призвіщем"),
    lastName("За ім'ям"),
    email("За поштою"),
    roles("За ролю"),
    statusUser("За статусом");
    private final String field;
}
