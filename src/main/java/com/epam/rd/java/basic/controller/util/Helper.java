package com.epam.rd.java.basic.controller.util;

import com.epam.rd.java.basic.model.Cart;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class  Helper {

    public static BigDecimal getTotalPrice(Set<Cart> carts) {
        BigDecimal totalPrice;
        try {
            totalPrice = carts.stream()
                    .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getCount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (NullPointerException e) {
            totalPrice = BigDecimal.ZERO;
        }
        return totalPrice;
    }

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
        return result;
    }


}
