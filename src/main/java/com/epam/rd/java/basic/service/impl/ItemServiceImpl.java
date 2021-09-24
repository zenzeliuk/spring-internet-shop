package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.repository.ItemRepository;
import com.epam.rd.java.basic.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public Page<Item> findAllByCategoryIds(List<Long> listCategoryIds, Pageable pageable) {
//        Pageable p = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
//        Page<Item> itemList = itemRepository.findAllByCategoryIds(List.of(2l,3l), p);
//        System.out.println(itemList.getContent().size());
//        System.out.println(itemList.getContent().get(0).getName());
        return itemRepository.findAllByCategoryIds(listCategoryIds, pageable);
    }

    @Override
    public Page<Item> findAllBy(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

}
