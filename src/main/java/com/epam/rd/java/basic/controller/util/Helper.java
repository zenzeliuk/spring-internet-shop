package com.epam.rd.java.basic.controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Helper {

    public static List<String> getValuesString(HttpServletRequest request, String paramName) {
        String[] paramValues = request.getParameterValues(paramName);
        if (paramValues == null) {
            paramValues = new String[0];
        }
        return Arrays.stream(paramValues).collect(Collectors.toList());
    }

    public static List<Long> getValuesLong(HttpServletRequest request, String paramName) {
        String[] paramValues = request.getParameterValues(paramName);
        if (paramValues == null) {
            paramValues = new String[0];
        }
        List<Long> result = new ArrayList<>();
        for (String s : paramValues) {
            if (s.contains(",")) {
                String[] q = s.split(",");
                result = Arrays.stream(q).map(Long::parseLong).collect(Collectors.toList());
            } else if (s.contains("%2C")) {
                String[] q = s.split("%2C");
                result = Arrays.stream(q).map(Long::parseLong).collect(Collectors.toList());
            } else {
                result.add(Long.valueOf(s));
            }
        }
//        System.out.println(result);
        return result;
//        return Arrays.stream(paramValues).map(Long::parseLong).collect(Collectors.toList());
    }


}
