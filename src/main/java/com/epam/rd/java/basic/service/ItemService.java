package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAllByCategoryId(Long categoryId);
}
