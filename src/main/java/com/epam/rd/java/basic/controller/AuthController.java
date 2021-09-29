package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.net.http.HttpRequest;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    public String userHome() {
        return "user";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/sign-up")
    public String signUpPage(@ModelAttribute("user") User user) {
        return "sign-up";
    }

    @PostMapping(value = "/sign-up")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Map<String, Object> model) {

        if (bindingResult.hasErrors()) {
            return "sign-up";
        }

        if (!userService.save(user)) {
            model.put("errorExist", "User exists!");
            return "sign-up";
        }
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("login", user.getLogin());
        model.addAttribute("firstName", user.getFirsName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String login,
                                @RequestParam String password,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email
    ) {
        userService.update(user, login, password, firstName, lastName, email);
        return "redirect:/";
    }
}
