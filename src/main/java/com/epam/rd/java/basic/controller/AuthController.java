package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.model.Role;
import com.epam.rd.java.basic.model.StatusUser;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/sign-up")
    public String signUpPage(@ModelAttribute("user") User user) {
        return "sign-up";
    }

    @PostMapping(value = "/sign-up")
    public String create(@ModelAttribute("user") User user, Map<String, Object> model) {
        User userFromDB = userRepository.findByLogin(user.getLogin());
        if (userFromDB != null) {
            model.put("message", "User exists!");
            return "sign-up";
        }

        user.setStatusUser(StatusUser.ACTIVE);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/";
    }

}
