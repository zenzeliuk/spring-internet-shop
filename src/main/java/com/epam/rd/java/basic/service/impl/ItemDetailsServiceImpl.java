package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.model.ItemDetails;
import com.epam.rd.java.basic.repository.BrandRepository;
import com.epam.rd.java.basic.repository.CategoryRepository;
import com.epam.rd.java.basic.repository.ColorRepository;
import com.epam.rd.java.basic.repository.ItemDetailsRepository;
import com.epam.rd.java.basic.service.ItemDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDetailsServiceImpl implements ItemDetailsService {

    @Autowired
    private ItemDetailsRepository itemDetailsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ColorRepository colorRepository;

    @Override
    public ItemDetails save(Item item, String category_id, String brand_id, String color_id) {
        ItemDetails itemDetails = ItemDetails.builder()
                .item(item)
                .category(categoryRepository.getOne(Long.valueOf(category_id)))
                .brand(brandRepository.getOne(Long.valueOf(brand_id)))
                .color(colorRepository.getOne(Long.valueOf(color_id)))
                .build();
        return itemDetailsRepository.save(itemDetails);
    }

}
