package com.epam.rd.java.basic.controller.item;

import com.epam.rd.java.basic.model.Brand;
import com.epam.rd.java.basic.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/brands")
@PreAuthorize("hasAuthority('ADMIN')")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping(value = "/new")
    public String newBrand(Model model) {
        model.addAttribute("brand", new Brand());
        return "brand/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("brand") Brand brand) {
        brandService.save(brand);
        return "redirect:/items/new";
    }

}
