package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.*;
import com.epam.rd.java.basic.repository.*;
import com.epam.rd.java.basic.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ItemServiceImplTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private ItemDetailsRepository itemDetailsRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private BrandRepository brandRepository;
    @MockBean
    private ColorRepository colorRepository;

    @Test
    void save() {
        String categoryId = "1";
        String brandId = "1";
        String colorId = "1";
        Category category = Category.builder()
                .id(1L)
                .name("123")
                .image("234")
                .build();
        Brand brand = Brand.builder()
                .id(1L)
                .name("345")
                .build();
        Color color = Color.builder()
                .id(1L)
                .name("qwe")
                .build();
        Item item = Item.builder()
                .name("name")
                .price(BigDecimal.valueOf(200L))
                .image("image")
                .build();
        when(categoryRepository.getOne(Long.valueOf(categoryId))).thenReturn(category);
        when(brandRepository.getOne(Long.valueOf(brandId))).thenReturn(brand);
        when(colorRepository.getOne(Long.valueOf(colorId))).thenReturn(color);
        itemService.save(item, categoryId, brandId, colorId);
        ItemDetails itemDetails = ItemDetails.builder()
                .item(item)
                .category(category)
                .brand(brand)
                .color(color)
                .build();
        assertEquals(1, item.getCount());
        verify(itemRepository, times(1)).save(item);
        verify(itemDetailsRepository, times(1)).save(itemDetails);

    }
}