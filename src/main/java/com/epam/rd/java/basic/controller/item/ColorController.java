package com.epam.rd.java.basic.controller.item;

import com.epam.rd.java.basic.model.Brand;
import com.epam.rd.java.basic.model.Color;
import com.epam.rd.java.basic.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/colors")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping(value = "/new")
    public String newBrand(Model model) {
        model.addAttribute("color", new Color());
        return "color/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("color") Color color) {
        colorService.save(color);
        return "redirect:/items/new";
    }
}
