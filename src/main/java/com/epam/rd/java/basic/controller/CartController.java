package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/carts")
@Log4j2
public class CartController {


    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String confirmCart(@AuthenticationPrincipal User user) {
        if (cartService.confirmOpenOrder(user)) {
            return "redirect:/orders";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping
    public String cart(Model model, @AuthenticationPrincipal User user, HttpSession session) {
        Order order = cartService.getOpenOrderOrReturnNull(user, session);
        model.addAttribute("order", order);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(RedirectAttributes redirectAttributes,
                            @RequestHeader(required = false) String referer,
                            @AuthenticationPrincipal User user,
                            @RequestParam(required = false) Long itemId,
                            HttpSession session) {

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams().forEach(redirectAttributes::addAttribute);
        String redirectPath = "redirect:" + components.getPath();
        if (cartService.addItemToCart(user, itemId, session)) {
            return redirectPath;
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(name = "cartId") Long cartId, @AuthenticationPrincipal User user, HttpSession session) {
        if (cartService.deleteItem(cartId, user, session)) {
            return "redirect:/carts";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/change-count")
    public String count(@RequestParam(name = "cartId") Long cartId,
                        @RequestParam(name = "count") Integer count,
                        @AuthenticationPrincipal User user,
                        HttpSession session) {
        if (cartService.changeCount(cartId, count, user, session)) {
            return "redirect:/carts";
        } else {
            return "redirect:/error";
        }
    }

}
