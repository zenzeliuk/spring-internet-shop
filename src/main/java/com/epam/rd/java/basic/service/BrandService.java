package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> findAll();
    Brand save(Brand brand);
}
