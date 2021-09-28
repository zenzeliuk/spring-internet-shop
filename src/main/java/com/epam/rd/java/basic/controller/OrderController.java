package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.StatusOrder;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.OrderDTO;
import com.epam.rd.java.basic.model.mapper.OrderMapper;
import com.epam.rd.java.basic.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    EntityManager entityManager;

    @GetMapping()
    public String getMyOrders(Model model, @AuthenticationPrincipal User user,
                              @RequestParam(name = "priceFrom", required = false, defaultValue = "0") BigDecimal priceFrom,
                              @RequestParam(name = "priceTo", required = false, defaultValue = "999999") BigDecimal priceTo,
                              @RequestParam(name = "statusOrder", required = false, defaultValue = "") String statusOrder,
                              @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                              @RequestParam(name = "sortField", required = false, defaultValue = "updateTime") String sortField,
                              @RequestParam(name = "sortDir", required = false, defaultValue = "desc") String sortDir
    ) {
        Page<Order> orderList = orderService.getPage(priceFrom, priceTo, statusOrder,
                page, size, sortField, sortDir, user);
        List<OrderDTO> orderDTOList = OrderMapper.orderDTOList(orderList.getContent());
        model.addAttribute("orders", orderDTOList);
        model.addAttribute("action", "user");
        return "/order";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAllOrders(Model model,
                               @RequestParam(name = "priceFrom", required = false) BigDecimal priceFrom,
                               @RequestParam(name = "priceTo", required = false) BigDecimal priceTo,
                               @RequestParam(name = "statusOrder", required = false, defaultValue = "") String statusOrder,
                               @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                               @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                               @RequestParam(name = "sortField", required = false, defaultValue = "updateTime") String sortField,
                               @RequestParam(name = "sortDir", required = false, defaultValue = "desc") String sortDir
    ) {
        Page<Order> orderPage = orderService.getPage(priceFrom, priceTo, statusOrder,
                page, size, sortField, sortDir, null);
        List<OrderDTO> orderDTOList = OrderMapper.orderDTOList(orderPage.getContent());
        model.addAttribute("orders", orderDTOList);
        model.addAttribute("action", "admin");
        return "/order";
    }

    @GetMapping("/detail")
    public String orderDetails(@RequestParam(name = "id") Long id, Model model) {
        Optional<Order> order = orderService.findById(id);
        if (order.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("orderDetails", order.get());
        return "order-details";
    }

    @PostMapping("/change-status-order")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatusOrder(@RequestParam(name = "id") Long id,
                                    @RequestParam(name = "status") String status) {
        Optional<Order> order = orderService.findById(id);
        StatusOrder statusOrder = StatusOrder.getStatusOrder(status);

        if (order.isEmpty() || statusOrder == null) {
            return "redirect:/orders";
        }
        order.get().setStatus(statusOrder);
        orderService.update(order.get());
        return "redirect:/orders";
    }

}
