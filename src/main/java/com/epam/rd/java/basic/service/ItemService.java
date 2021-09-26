package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Item;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public interface ItemService {

    Optional<Item> findById(Long id);
    Item save(Item item);
    Page<Item> getPage(String nameLike, BigDecimal priceFrom, BigDecimal priceTo, Integer page, Integer size, String sortField, String sortDir, HttpServletRequest request);
}
