package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Item;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public interface ItemService {

    Item save(Item item);
    Page<Item> getPage(String nameLike, BigDecimal priceFrom, BigDecimal priceTo, Integer page, Integer size, String sortField, String sortDir, HttpServletRequest request);
}
