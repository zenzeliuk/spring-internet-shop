package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.controller.util.Helper;
import com.epam.rd.java.basic.model.Category;
import com.epam.rd.java.basic.model.Order;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.service.CategoryService;
import com.epam.rd.java.basic.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String main(HttpSession session, Model model, HttpServletRequest request, @AuthenticationPrincipal User user) {

        Order orderFromSession = (Order) session.getAttribute("orderSession");
        if (user != null && orderFromSession != null) {
            orderService.mergeCartsBetweenSessionAndUserWithStatusOpen(orderFromSession, user);
            session.setAttribute("orderSession", null);
        }

        List<Category> categoryList = categoryService.findAll();
        String paramName = "category";
        List<String> paramCategory = Helper.getValuesString(request, paramName);
        model.addAttribute("paramCategory", paramCategory);
        model.addAttribute("categories", categoryList);
        return "home";
    }


}
