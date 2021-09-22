package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.Color;

import java.util.List;

public interface ColorService {
    List<Color> findAll();
    Color save(Color color);
}
