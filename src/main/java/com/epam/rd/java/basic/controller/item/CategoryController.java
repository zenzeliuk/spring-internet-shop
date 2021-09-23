package com.epam.rd.java.basic.controller.item;

import com.epam.rd.java.basic.model.Brand;
import com.epam.rd.java.basic.model.Category;
import com.epam.rd.java.basic.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/new")
    public String newCategory(Model model) {
        model.addAttribute("category", new Category());
        return "category/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/items/new";
    }
}
