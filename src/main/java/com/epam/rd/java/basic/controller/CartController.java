package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.model.Cart;
import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.repository.CartRepository;
import com.epam.rd.java.basic.service.ItemService;
import com.epam.rd.java.basic.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(value = "/carts")
public class CartController {

    private final ItemService itemService;
    private final OrderService orderService;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    public CartController(ItemService itemService, OrderService orderService) {
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @GetMapping
    public String cart(Model model, @AuthenticationPrincipal User user, HttpSession session) {
        Set<Cart> carts = new HashSet<>();
        Order orderFromSession = (Order) session.getAttribute("orderSession");
        if (user == null) {
            if (orderFromSession != null) {
                Optional<Order> order = orderService.findById(orderFromSession.getId());
                if (order.isPresent()) {
                    carts = order.get().getCarts();
                }
            }
        } else {
            Order orderUser = orderService.getOrderUser(user);
            if (orderFromSession != null) {
                orderService.mergeOrderFromSessionAndOrderUsers(orderFromSession, orderUser);
                session.setAttribute("orderSession", null);
            }
            carts = cartRepository.findAllByOrder_User(user);
        }

        model.addAttribute("carts", carts);
        return "cart";
    }

    @GetMapping("/add")
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

        Order orderFromSession = (Order) session.getAttribute("orderSession");
        if (user == null) {
            orderFromSession = orderService.addItemToOrderFromSession(item.get(), orderFromSession);
            session.setAttribute("orderSession", orderFromSession);
        } else {
            Order orderUser = orderService.getOrderUser(user);
            if (orderFromSession != null) {
                orderService.mergeOrderFromSessionAndOrderUsers(orderFromSession, orderUser);
                session.setAttribute("orderSession", null);
            }
            orderService.addItemForOrderUser(item.get(), orderUser);
        }

        return redirectPath;
    }


}
