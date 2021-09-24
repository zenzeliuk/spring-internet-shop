package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.model.Role;
import com.epam.rd.java.basic.model.StatusUser;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.UserDTO;
import com.epam.rd.java.basic.model.mapper.UserMapper;
import com.epam.rd.java.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String users(Model model) {
        return userListPage(model, 1, "login", "asc");
    }

    @GetMapping("/page/{currentPage}")
    public String userListPage(
            Model model,
            @PathVariable int currentPage,
            @Param("sortField") String sortField,
            @Param("sortDir") String sortDir
    ) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(currentPage - 1, 2, sort);
        Page<User> page = userService.findAll(pageable);

        String reversSortDir = sortDir.equals("asc") ? "desc" : "asc";

        Long totalUsers = page.getTotalElements();
        Integer totalPages = page.getTotalPages();
        List<UserDTO> userDTOList = UserMapper.toUserDTOList(page.getContent());

        model.addAttribute("users", userDTOList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reversSortDir", reversSortDir);
        return "admin/user-list";
    }

    @PostMapping("/change-status")
    public String changeStatus(
            @RequestParam String id,
            @RequestParam String currentPage,
            @RequestParam String sortField,
            @RequestParam String sortDir
    ) {
        User user = userService.findById(id);
        if (user.getStatusUser().equals(StatusUser.BLOCKED)) {
            user.setStatusUser(StatusUser.ACTIVE);
        } else {
            user.setStatusUser(StatusUser.BLOCKED);
        }
        userService.save(user);

        return redirectToAdminUsers(currentPage, sortField, sortDir);
    }

    private String redirectToAdminUsers(String currentPage, String sortField, String sortDir) {
        return "redirect:/admin/users/page/" + currentPage + "?" + "sortField=" + sortField + "&sortDir=" + sortDir;
    }

    @PostMapping("/change-role")
    public String changeRole(
            @RequestParam String id,
            @RequestParam String currentPage,
            @RequestParam String sortField,
            @RequestParam String sortDir
    ) {
        User user = userService.findById(id);

        if (user.getRoles().contains(Role.USER)) {
            user.getRoles().clear();
            user.getRoles().add(Role.ADMIN);
        } else {
            user.getRoles().clear();
            user.getRoles().add(Role.USER);
        }
        userService.save(user);

        return redirectToAdminUsers(currentPage, sortField, sortDir);
    }
}
