package com.epam.rd.java.basic.controller.item;

import com.epam.rd.java.basic.model.Brand;
import com.epam.rd.java.basic.model.Category;
import com.epam.rd.java.basic.model.Color;
import com.epam.rd.java.basic.model.Item;
import com.epam.rd.java.basic.repository.BrandRepository;
import com.epam.rd.java.basic.repository.CategoryRepository;
import com.epam.rd.java.basic.service.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private BrandService brandService;
    @MockBean
    private ColorService colorService;
    @MockBean
    private UserService userService;
    @MockBean
    private ItemService itemService;
    @MockBean
    private ItemDetailsService itemDetailsService;

    @Test
    void newItem() throws Exception {
        Item item = new Item();
        Category category = Category.builder()
                .id(1L)
                .name("category-name")
                .build() ;
        Brand brand = Brand.builder()
                .id(1L)
                .name("brand-name")
                .build();
        Color color = Color.builder()
                .id(1L)
                .name("color-name")
                .build();

        List<Category> categoryList = List.of(category);
        List<Brand> brandList = List.of(brand);
        List<Color> colorList = List.of(color);

        Mockito.doReturn(categoryList).when(categoryService).findAll();
        Mockito.doReturn(brandList).when(brandService).findAll();
        Mockito.doReturn(colorList).when(colorService).findAll();

        mockMvc.perform(get("/items/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("item/new"))
                .andExpect(forwardedUrl("/templates/item/new.html"))
                .andExpect(model().attribute("categories", hasSize(1)))
                ;

//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("todo/list"))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/todo/list.jsp"))
//                .andExpect(model().attribute("todos", hasSize(2)))
//                .andExpect(model().attribute("todos", hasItem(
//                        allOf(
//                                hasProperty("id", is(1L)),
//                                hasProperty("description", is("Lorem ipsum")),
//                                hasProperty("title", is("Foo"))
//                        )
//                )))
//                .andExpect(model().attribute("todos", hasItem(
//                        allOf(
//                                hasProperty("id", is(2L)),
//                                hasProperty("description", is("Lorem ipsum")),
//                                hasProperty("title", is("Bar"))
//                        )
//                )));
//
//        verify(todoServiceMock, times(1)).findAll();
//        verifyNoMoreInteractions(todoServiceMock);




    }
}