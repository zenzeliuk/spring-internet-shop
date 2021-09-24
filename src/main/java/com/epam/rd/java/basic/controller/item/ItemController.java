package com.epam.rd.java.basic.controller.item;

import com.epam.rd.java.basic.controller.util.Helper;
import com.epam.rd.java.basic.model.Brand;
import com.epam.rd.java.basic.model.Category;
import com.epam.rd.java.basic.model.Color;
import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/items")
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ColorService colorService;
    private final ItemDetailsService itemDetailsService;

    @Autowired
    public ItemController(ItemService itemService, CategoryService categoryService, BrandService brandService, ColorService colorService, ItemDetailsService itemDetailsService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.colorService = colorService;
        this.itemDetailsService = itemDetailsService;
    }

    @GetMapping()
    public String items(
            @RequestParam(value = "categories", required = false) Integer categories,
            Model model,
            HttpServletRequest request,
            @RequestParam(value = "size", required = false, defaultValue = "3") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort
    ) {
        String paramName = "category";
        List<String> paramCategory = Helper.getValues(request, paramName);
        model.addAttribute("paramCategory", paramCategory);
//        Page<Item> ii - itemService.findAllByCategoryIds()
        Page<Item> itemsPage = itemService.findAllBy(
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort)));
        model.addAttribute("itemsPage", itemsPage);
        model.addAttribute("numbers", IntStream.range(0, itemsPage.getTotalPages()).toArray());
        return "item/items";
    }


    @GetMapping(value = "/new")
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
    public String create(@ModelAttribute("item") Item item,
                         @RequestParam String category_id,
                         @RequestParam String brand_id,
                         @RequestParam String color_id) {
        item.setCount(1);
        Item savedItem = itemService.save(item);
        itemDetailsService.save(savedItem, category_id, brand_id, color_id);
        return "redirect:/";
    }


}
