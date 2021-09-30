package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.controller.util.Helper;
import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.model.ItemDetails;
import com.epam.rd.java.basic.repository.*;
import com.epam.rd.java.basic.service.ItemService;
import com.epam.rd.java.basic.service.util.ParamItem;
import com.epam.rd.java.basic.service.util.MyPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemDetailsRepository itemDetailsRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ItemDetailsRepository itemDetailsRepository, CategoryRepository categoryRepository,
                           BrandRepository brandRepository, ColorRepository colorRepository) {
        this.itemRepository = itemRepository;
        this.itemDetailsRepository = itemDetailsRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.colorRepository = colorRepository;
    }

    @Override
    public void save(Item item, String categoryId, String brandId, String colorId) {
        item.setCount(1);
        itemRepository.save(item);
        ItemDetails itemDetails = ItemDetails.builder()
                .item(item)
                .category(categoryRepository.getOne(Long.valueOf(categoryId)))
                .brand(brandRepository.getOne(Long.valueOf(brandId)))
                .color(colorRepository.getOne(Long.valueOf(colorId)))
                .build();
        itemDetailsRepository.save(itemDetails);
    }

    @Override
    public Page<Item> getPage(String nameLike, BigDecimal priceFrom, BigDecimal priceTo,
                              Integer page, Integer size, String sortField, String sortDir, HttpServletRequest request) {
        Pageable pageable = MyPageable.getPageable(page, size, sortField, sortDir);
        List<Long> listCategoryId = getListCategoryId(request, ParamItem.CATEGORY.getName());
        List<Long> listBrandId = getListBrandId(request, ParamItem.BRAND.getName());
        List<Long> listColorId = getListColorId(request, ParamItem.COLOR.getName());

        return itemRepository.findAllWithFilter(pageable, nameLike, priceFrom, priceTo, listCategoryId, listBrandId, listColorId);
    }

    private List<Long> getListCategoryId(HttpServletRequest request, String param) {
        if (request.getParameterValues(param) == null) {
            return categoryRepository.findAllId();
        } else {
            return Helper.getValuesLong(request, param);
        }
    }

    private List<Long> getListBrandId(HttpServletRequest request, String param) {
        if (request.getParameterValues(param) == null) {
            return brandRepository.findAllId();
        } else {
            return Helper.getValuesLong(request, param);
        }
    }

    private List<Long> getListColorId(HttpServletRequest request, String param) {
        if (request.getParameterValues(param) == null) {
            return colorRepository.findAllId();
        } else {
            return Helper.getValuesLong(request, param);
        }
    }


}
