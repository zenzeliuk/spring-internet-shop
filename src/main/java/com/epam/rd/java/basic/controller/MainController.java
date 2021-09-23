package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.model.Category;
import com.epam.rd.java.basic.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String main(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categories", categoryList);
        return "home";
    }




}
