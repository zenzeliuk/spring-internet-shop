package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.controller.util.Helper;
import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.repository.BrandRepository;
import com.epam.rd.java.basic.repository.CategoryRepository;
import com.epam.rd.java.basic.repository.ColorRepository;
import com.epam.rd.java.basic.repository.ItemRepository;
import com.epam.rd.java.basic.service.ItemService;
import com.epam.rd.java.basic.service.Param;
import com.epam.rd.java.basic.service.util.MyPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository,
                           BrandRepository brandRepository, ColorRepository colorRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.colorRepository = colorRepository;
    }

    @Autowired
    private EntityManager entityManager;


    @Override
    public Optional<Item> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return itemRepository.findById(id);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Page<Item> getPage(String nameLike, BigDecimal priceFrom, BigDecimal priceTo,
                              Integer page, Integer size, String sortField, String sortDir, HttpServletRequest request) {
        Pageable pageable = MyPageable.getPageable(page, size, sortField, sortDir);
        List<Long> listCategoryId = getListCategoryId(request, Param.CATEGORY.getName());
        List<Long> listBrandId = getListBrandId(request, Param.BRAND.getName());
        List<Long> listColorId = getListColorId(request, Param.COLOR.getName());

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
