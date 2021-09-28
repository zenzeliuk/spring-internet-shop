package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.controller.util.Helper;
import com.epam.rd.java.basic.model.*;
import com.epam.rd.java.basic.repository.OrderRepository;
import com.epam.rd.java.basic.service.ItemService;
import com.epam.rd.java.basic.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping(value = "/carts")
@Log4j2
public class CartController {

    private final ItemService itemService;
    private final OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    public CartController(ItemService itemService, OrderService orderService) {
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @PostMapping
    public String confirmCart(Model model) {
        Order order = (Order) model.getAttribute("order");
        if (order == null) {
            return "redirect:/";
        }
        order.setStatus(StatusOrder.REGISTERED);
        order.setTotalPrice(Helper.getTotalPrice(order.getCarts()));
        orderService.update(order);
        model.addAttribute("order", null);
        return "redirect:/";
    }

    @GetMapping
    public String cart(Model model, @AuthenticationPrincipal User user, HttpSession session) {
        Order order = null;
        if (user == null) {
            Order orderFromSession = (Order) session.getAttribute("orderSession");
            if (orderFromSession != null) {
                order = orderFromSession;
            }
        } else {
            Optional<Order> orderFromDb = orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user);
            if (orderFromDb.isPresent()) {
                order = orderFromDb.get();
            }
        }
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

        Optional<Item> item = itemService.findById(itemId);
        if (item.isEmpty()) {
            return redirectPath;
        }
        if (user == null) {
            Order orderFromSession = (Order) session.getAttribute("orderSession");
            orderFromSession = orderService.addItemToOrderFromSession(item.get(), orderFromSession);
            session.setAttribute("orderSession", orderFromSession);
        } else {
            orderService.addItemToOpenOrderForUser(item.get(), user);
        }
        return redirectPath;
    }


}
