package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.Color;
import com.epam.rd.java.basic.repository.ColorRepository;
import com.epam.rd.java.basic.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<Color> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public Color save(Color color) {
        return colorRepository.save(color);
    }
}
