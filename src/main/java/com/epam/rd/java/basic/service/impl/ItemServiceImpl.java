package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.model.ItemDetails;
import com.epam.rd.java.basic.repository.ItemRepository;
import com.epam.rd.java.basic.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> findAllByCategoryId(Long categoryId) {
        return itemRepository.findAllByItemDetails_Category_Id(categoryId);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

}
