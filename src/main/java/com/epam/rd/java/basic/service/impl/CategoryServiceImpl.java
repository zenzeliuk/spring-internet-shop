package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.Category;
import com.epam.rd.java.basic.repository.CategoryRepository;
import com.epam.rd.java.basic.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
