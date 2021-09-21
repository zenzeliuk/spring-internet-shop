package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.Brand;
import com.epam.rd.java.basic.repository.BrandRepository;
import com.epam.rd.java.basic.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }
}
