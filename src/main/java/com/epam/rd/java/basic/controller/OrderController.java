package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.controller.util.Helper;
import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.OrderDTO;
import com.epam.rd.java.basic.service.OrderService;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
@Log4j2
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public String getMyOrders(Model model, @AuthenticationPrincipal User user,
                              @RequestParam(name = "priceFrom", required = false) BigDecimal priceFrom,
                              @RequestParam(name = "priceTo", required = false) BigDecimal priceTo,
                              @RequestParam(name = "statusOrder", required = false) String statusOrder,
                              @RequestParam(name = "page", required = false) Integer page,
                              @RequestParam(name = "size", required = false) Integer size,
                              @RequestParam(name = "sortField", required = false) String sortField,
                              @RequestParam(name = "sortDir", required = false) String sortDir
    ) {
        List<OrderDTO> orderDTOList = orderService.getOrdersWithFilter(priceFrom, priceTo, statusOrder, user, page, size, sortField, sortDir);
        model.addAttribute("orders", orderDTOList);
        model.addAttribute("action", "user");
        return "/order";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAllOrders(Model model,
                               @RequestParam(name = "priceFrom", required = false) BigDecimal priceFrom,
                               @RequestParam(name = "priceTo", required = false) BigDecimal priceTo,
                               @RequestParam(name = "statusOrder", required = false) String statusOrder,
                               @RequestParam(name = "page", required = false) Integer page,
                               @RequestParam(name = "size", required = false) Integer size,
                               @RequestParam(name = "sortField", required = false) String sortField,
                               @RequestParam(name = "sortDir", required = false) String sortDir
    ) {
        List<OrderDTO> orderDTOList = orderService.getOrdersWithFilter(priceFrom, priceTo, statusOrder, null, page, size, sortField, sortDir);
        int[] numbers = new int[0];
        if (!orderDTOList.isEmpty()) {
            numbers = Helper.getNumbers(orderDTOList.get(0).getTotalPage());
        }
        model.addAttribute("numbers", numbers);
        model.addAttribute("orders", orderDTOList);
        model.addAttribute("action", "admin");
        return "/order";
    }

    @GetMapping("/detail")
    public String orderDetails(@RequestParam(name = "id") Long id, Model model) {
        Optional<Order> order = orderService.findById(id);
        if (order.isEmpty()) {
            return "redirect:/error";
        }
        model.addAttribute("orderDetails", order.get());
        return "order-details";
    }

    @PostMapping("/change-status-order")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatusOrder(@RequestParam(name = "id") Long id,
                                    @RequestParam(name = "status") String status,
                                    RedirectAttributes redirectAttributes,
                                    @RequestHeader(required = false) String referer
    ) {
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams().forEach(redirectAttributes::addAttribute);
        String redirectPath = "redirect:" + components.getPath();
        if (orderService.changeStatus(id, status)) {
            return redirectPath;
        } else {
            return "redirect:/error";
        }
    }

}
