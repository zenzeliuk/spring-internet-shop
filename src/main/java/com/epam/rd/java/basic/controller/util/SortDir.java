package com.epam.rd.java.basic.controller.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortDir {
    asc("За зростанням"),
    desc("За спаданням");

    private final String dir;

}
