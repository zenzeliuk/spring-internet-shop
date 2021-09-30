package com.epam.rd.java.basic.controller;

import com.epam.rd.java.basic.controller.util.Helper;
import com.epam.rd.java.basic.model.dto.UserDTO;
import com.epam.rd.java.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String getUsers(Model model,
                          @RequestParam(name = "page", required = false) Integer page,
                          @RequestParam(name = "size", required = false) Integer size,
                          @RequestParam(name = "sortField", required = false) String sortField,
                          @RequestParam(name = "sortDir", required = false) String sortDir) {
        List<UserDTO> userDTOList = userService.getUsersWithFilter(page, size, sortField, sortDir);
        int[] numbers = new int[0];
        if (!userDTOList.isEmpty()) {
            numbers = Helper.getNumbers(userDTOList.get(0).getTotalPage());
        }
        model.addAttribute("numbers", numbers);
        model.addAttribute("users", userDTOList);
        return "admin/user-list";
    }

    @PostMapping("/change-status")
    public String changeStatus(@RequestParam Long id,
                               RedirectAttributes redirectAttributes,
                               @RequestHeader(required = false) String referer) {
        userService.changeStatus(id);
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams().forEach(redirectAttributes::addAttribute);
        return "redirect:" + components.getPath();

    }

    @PostMapping("/change-role")
    public String changeRole(@RequestParam Long id,
                             RedirectAttributes redirectAttributes,
                             @RequestHeader(required = false) String referer) {
        userService.changeRole(id);
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams().forEach(redirectAttributes::addAttribute);
        return "redirect:" + components.getPath();

    }
}
