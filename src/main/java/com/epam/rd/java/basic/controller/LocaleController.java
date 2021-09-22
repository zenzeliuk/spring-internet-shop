package com.epam.rd.java.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LocaleController {

    @GetMapping("/international")
    public String getInternationalPage(@RequestParam(name = "lang") String lang, HttpSession session, HttpServletRequest request) {
        session.setAttribute("local", lang);
        String params = request.getHeader("Referer");
        return "redirect:" + params;
    }

}
