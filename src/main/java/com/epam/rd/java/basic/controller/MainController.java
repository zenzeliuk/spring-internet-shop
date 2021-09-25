package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.controller.util.Helper;
import com.epam.rd.java.basic.model.Category;
import com.epam.rd.java.basic.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String main(Model model, HttpServletRequest request) {
        List<Category> categoryList = categoryService.findAll();
        String paramName = "category";
        List<String> paramCategory = Helper.getValuesString(request, paramName);
        model.addAttribute("paramCategory", paramCategory);
        model.addAttribute("categories", categoryList);
        return "home";
    }


}
