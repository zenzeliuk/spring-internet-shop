package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.service.CartService;
import com.epam.rd.java.basic.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/carts")
public class CartController {

    private final ItemService itemService;
    private final CartService cartService;


    @Autowired
    public CartController(ItemService itemService, CartService cartService) {
        this.itemService = itemService;
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public String addToCart(HttpServletRequest request){

        return "redirect:/";
    }

}
