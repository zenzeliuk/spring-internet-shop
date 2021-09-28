package com.epam.rd.java.basic.controller.item;

import com.epam.rd.java.basic.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.RequestEntity.post;

@WebMvcTest(BrandController.class)
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    @Test
    void newBrand() {


    }

    @Test
    void save() {

//        mockMvc.perform(post("/brads")
//                .contentType(MediaType.ALL)
//                .
//        );

    }
}