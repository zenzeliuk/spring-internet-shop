package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.model.Role;
import com.epam.rd.java.basic.model.StatusUser;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/user-list";
    }

    @PostMapping("/change-status")
    public String changeStatus(@RequestParam String id) {
        User user = userService.findById(id);
        if (user.getStatusUser().equals(StatusUser.BLOCKED)) {
            user.setStatusUser(StatusUser.ACTIVE);
        } else {
            user.setStatusUser(StatusUser.BLOCKED);
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/change-role")
    public String changeRole(@RequestParam String id) {
        User user = userService.findById(id);

        if (user.getRoles().contains(Role.USER)) {
            user.getRoles().clear();
            user.getRoles().add(Role.ADMIN);
        } else {
            user.getRoles().clear();
            user.getRoles().add(Role.USER);
        }
        userService.save(user);
        return "redirect:/admin";
    }
}
