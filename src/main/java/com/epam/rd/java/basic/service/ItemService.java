package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.model.ItemDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    Page<Item> findAllByCategoryIds(List<Long> listCategoryIds, Pageable pageable);
    Page<Item> findAllBy(Pageable pageable);
    Item save(Item item);
}
