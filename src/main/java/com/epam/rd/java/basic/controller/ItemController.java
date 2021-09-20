//package com.epam.rd.java.basic.controller;
//
//import com.epam.rd.java.basic.model.Item;
//import com.epam.rd.java.basic.service.ItemService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//public class ItemController {
//
//    @Autowired
//    private ItemService itemService;
//
//    @GetMapping(value = "/item")
//    public String items(@RequestAttribute String category_id, Model model){
//        Long categoryId = Long.valueOf(category_id);
//        List<Item> itemList = itemService.findAllByCategoryId(categoryId);
//        model.addAttribute("items", itemList);
//        return "items";
//    }
//
//}
