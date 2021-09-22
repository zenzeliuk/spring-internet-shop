package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.model.*;
import com.epam.rd.java.basic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping(value = "/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private ItemDetailsService itemDetailsService;

    @GetMapping()
    public String items(@RequestParam String category_id, Model model) {
        Long categoryId = Long.valueOf(category_id);
        List<Item> itemList = itemService.findAllByCategoryId(categoryId);
        model.addAttribute("items", itemList);
        return "items";
    }

    @GetMapping(value = "/new")
    public String newItem(Model model) {
        List<Category> categoryList = categoryService.findAll();
        List<Brand> brandList = brandService.findAll();
        List<Color> colorList = colorService.findAll();

        model.addAttribute("item", new Item());
        model.addAttribute("categories", categoryList);
        model.addAttribute("brandes", brandList);
        model.addAttribute("colors", colorList);

        return "item/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("item") Item item,
                         @RequestParam String category_id,
                         @RequestParam String brand_id,
                         @RequestParam String color_id) {
        item.setCount(1);
        Item savedItem = itemService.save(item);
        itemDetailsService.save(savedItem, category_id, brand_id, color_id);
        return "redirect:/";
    }


}
