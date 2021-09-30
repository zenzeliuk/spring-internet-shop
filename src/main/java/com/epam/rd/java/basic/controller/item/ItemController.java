package com.epam.rd.java.basic.controller.item;

import com.epam.rd.java.basic.model.Brand;
import com.epam.rd.java.basic.model.Category;
import com.epam.rd.java.basic.model.Color;
import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.service.BrandService;
import com.epam.rd.java.basic.service.CategoryService;
import com.epam.rd.java.basic.service.ColorService;
import com.epam.rd.java.basic.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/items")
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ColorService colorService;

    @Autowired
    public ItemController(ItemService itemService, CategoryService categoryService, BrandService brandService, ColorService colorService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.colorService = colorService;
    }

    @GetMapping()
    public String items(Model model,
                        HttpServletRequest request,
                        @RequestParam(value = "nameLike", required = false, defaultValue = "") String nameLike,
                        @RequestParam(value = "priceFrom", required = false, defaultValue = "0") BigDecimal priceFrom,
                        @RequestParam(value = "priceTo", required = false, defaultValue = "1000123") BigDecimal priceTo,
                        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                        @RequestParam(value = "size", required = false, defaultValue = "6") Integer size,
                        @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
                        @RequestParam(value = "sortDir", required = false, defaultValue = "desc") String sortDir

    ) {
        Page<Item> itemPage = itemService.getPage(nameLike, priceFrom, priceTo, page, size, sortField, sortDir, request);
        List<Category> categoryList = categoryService.findAll();
        List<Brand> brandList = brandService.findAll();
        List<Color> colorList = colorService.findAll();
        model.addAttribute("categories", categoryList);
        model.addAttribute("brandes", brandList);
        model.addAttribute("colors", colorList);
        model.addAttribute("itemsPage", itemPage);
        model.addAttribute("numbers", IntStream.range(1, itemPage.getTotalPages() + 1).toArray());
        return "item/items";
    }

    @GetMapping(value = "/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String newItem(Model model) {
        List<Category> categoryList = categoryService.findAll();
        List<Brand> brandList = brandService.findAll();
        List<Color> colorList = colorService.findAll();

        model.addAttribute("item", new Item());
        model.addAttribute("categories", categoryList);
        model.addAttribute("brandes", brandList);
        model.addAttribute("colors", colorList);

        return "item/new";
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(@ModelAttribute("item") Item item,
                         @RequestParam String categoryId,
                         @RequestParam String brandId,
                         @RequestParam String colorId) {
        itemService.save(item, categoryId, brandId, colorId);
        return "redirect:/";
    }

}
