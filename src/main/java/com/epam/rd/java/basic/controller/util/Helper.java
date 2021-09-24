package com.epam.rd.java.basic.controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Helper {

    public static List<String> getValues(HttpServletRequest request, String paramName) {
        String[] paramValues = request.getParameterValues(paramName);
        if (paramValues == null) {
            paramValues = new String[0];
        }
        return Arrays.stream(paramValues).collect(Collectors.toList());
    }
}
