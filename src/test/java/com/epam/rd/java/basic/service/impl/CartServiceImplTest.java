package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.*;
import com.epam.rd.java.basic.repository.CartRepository;
import com.epam.rd.java.basic.repository.ItemRepository;
import com.epam.rd.java.basic.repository.OrderRepository;
import com.epam.rd.java.basic.service.CartService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CartServiceImplTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private ItemRepository itemRepository;

    private static Item item;
    private static User user;
    private static Order order;
    private static BigDecimal totalPrice;
    private static Cart cartFirst;

    @BeforeAll
    static void setUp() {
        item = Item.builder()
                .id(1L)
                .name("asd")
                .price(BigDecimal.valueOf(123L))
                .build();
        user = User.builder()
                .id(1L)
                .build();

        int countFirst = 2;
        int countSecond = 3;
        long priceFirst = 123L;
        long priceSecond = 234L;
        totalPrice = BigDecimal.valueOf(priceFirst * countFirst + priceSecond * countSecond);

        cartFirst = Cart.builder()
                .id(1L)
                .price(BigDecimal.valueOf(priceFirst))
                .count(countFirst)
                .build();
        Cart cartSecond = Cart.builder()
                .id(2L)
                .price(BigDecimal.valueOf(priceSecond))
                .count(countSecond)
                .build();
        Set<Cart> cartSet = new HashSet<>();
        cartSet.add(cartFirst);
        cartSet.add(cartSecond);
        order = Order.builder()
                .id(1L)
                .status(StatusOrder.OPEN)
                .carts(cartSet)
                .build();
    }

    @Test
    void confirmOpenOrderFailed() {
        Optional<Order> optionalOrder = Optional.empty();
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(optionalOrder);
        boolean res = cartService.confirmOpenOrder(user);
        assertFalse(res);
    }

    @Test
    void confirmOpenOrder() {
        Optional<Order> optionalOrder = Optional.ofNullable(order);
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(optionalOrder);
        boolean res = cartService.confirmOpenOrder(user);
        assertTrue(res);
        assertEquals(StatusOrder.REGISTERED, optionalOrder.get().getStatus());
        assertEquals(totalPrice, optionalOrder.get().getTotalPrice());
        verify(orderRepository, times(1)).save(optionalOrder.get());
    }

    @Test
    void getOpenOrderOrReturnNullWhenUserNotNull() {
        HttpSession session = new MockHttpSession();
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(Optional.of(order));
        Order orderFromDB = cartService.getOpenOrderOrReturnNull(user, session);
        assertEquals(order, orderFromDB);
    }

    @Test
    void getOpenOrderOrReturnNullWhenUserNotNullAndOrderIsNotExist() {
        HttpSession session = new MockHttpSession();
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(Optional.empty());
        Order orderFromDB = cartService.getOpenOrderOrReturnNull(user, session);
        assertNull(orderFromDB);
    }

    @Test
    void getOpenOrderOrReturnNullWhenUserNullAndOrderIsExist() {
        HttpSession session = new MockHttpSession();
        session.setAttribute("orderSession", order);
        when(orderRepository.getOne(order.getId())).thenReturn(order);
        Order orderFromSession = cartService.getOpenOrderOrReturnNull(null, session);
        assertEquals(order, orderFromSession);
    }

    @Test
    void getOpenOrderOrReturnNullWhenUserNullAndOrderIsNotExist() {
        HttpSession session = new MockHttpSession();
        session.setAttribute("orderSession", null);
        Order orderFromSession = cartService.getOpenOrderOrReturnNull(null, session);
        assertNull(orderFromSession);
    }

    @Test
    void addItemToCartWhenItemIsNoExist() {
        Long itemId = 1L;
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());
        boolean res = cartService.addItemToCart(null, itemId, null);
        assertFalse(res);
    }

    @Test
    void addItemToCartWhenUserIsNullAndOrderFromSessionIsExist() {
        Cart newCart = Cart.builder()
                .count(1)
                .order(order)
                .item(item)
                .price(item.getPrice())
                .build();
        BigDecimal totalPriceAfter = totalPrice;
        totalPriceAfter.add(new BigDecimal(String.valueOf(item.getPrice())));

        HttpSession session = new MockHttpSession();
        session.setAttribute("orderSession", order);

        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(orderRepository.getOne(order.getId())).thenReturn(order);

        boolean res = cartService.addItemToCart(null, item.getId(), session);
        assertTrue(res);

        order.getCarts().add(newCart);
        order.setTotalPrice(totalPriceAfter);
        verify(cartRepository, times(1)).save(newCart);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void addItemToCartWhenUserIsNullAndOrderFromSessionIsNotExist() {
        Set<Cart> cartSet = new HashSet<>();
        Order newOrder = Order.builder()
                .status(StatusOrder.OPEN)
                .carts(cartSet)
                .build();
        HttpSession session = new MockHttpSession();

        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(orderRepository.save(newOrder)).thenReturn(newOrder);

        boolean res = cartService.addItemToCart(null, item.getId(), session);
        assertTrue(res);
        verify(orderRepository, times(1)).save(newOrder);
    }

    @Test
    void addItemToCartWhenUserHasOpenOrder() {
        Cart newCart = Cart.builder()
                .count(1)
                .order(order)
                .item(item)
                .price(item.getPrice())
                .build();
        BigDecimal totalPriceAfter = totalPrice;
        totalPriceAfter.add(new BigDecimal(String.valueOf(item.getPrice())));

        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(Optional.of(order));

        boolean res = cartService.addItemToCart(user, item.getId(), null);
        assertTrue(res);

        order.getCarts().add(newCart);
        order.setTotalPrice(totalPriceAfter);
        verify(cartRepository, times(1)).save(newCart);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void addItemToCartWhenUserHasNotOpenOrder() {
        Set<Cart> cartSet = new HashSet<>();
        Order newOrder = Order.builder()
                .status(StatusOrder.OPEN)
                .carts(cartSet)
                .user(user)
                .build();

        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(orderRepository.save(newOrder)).thenReturn(newOrder);

        boolean res = cartService.addItemToCart(user, item.getId(), null);
        assertTrue(res);

        verify(orderRepository, times(1)).save(newOrder);
    }


    @Test
    void changeCountFailed() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());
        boolean res = cartService.changeCount(1L, 2, null, null);
        assertFalse(res);

    }

    @Test
    void changeCountForUserFailed() {
        when(cartRepository.findById(cartFirst.getId())).thenReturn(Optional.of(cartFirst));
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(Optional.empty());
        boolean res = cartService.changeCount(cartFirst.getId(), 2, user, null);
        assertFalse(res);
    }

    @Test
    void changeCountForUserWhenCartIsNotExist() {
        Cart cart = Cart.builder()
                .id(6L)
                .build();
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(Optional.of(order));
        boolean res = cartService.changeCount(cartFirst.getId(), 2, user, null);
        assertFalse(res);
    }

    @Test
    void changeCountForUserWhenOrderHasNotCart() {
        Cart cart = Cart.builder()
                .id(6L)
                .build();
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(Optional.of(order));
        boolean res = cartService.changeCount(cart.getId(), 2, user, null);
        assertFalse(res);
    }

    @Test
    void changeCountForUser() {
        Order myOrder = Order.builder()
                .id(1L)
                .user(user)
                .build();
        Cart cart = Cart.builder()
                .id(1L)
                .count(1)
                .order(myOrder)
                .build();
        myOrder.setCarts(Collections.singleton(cart));
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(Optional.of(myOrder));
        when(orderRepository.getOne(cart.getOrder().getId())).thenReturn((myOrder));
        boolean res = cartService.changeCount(cart.getId(), 2, user, null);
        assertTrue(res);
    }


    @Test
    void changeCountFromSessionFailed() {
        Order myOrder = Order.builder()
                .id(1L)
                .build();
        Cart cart = Cart.builder()
                .id(1L)
                .count(1)
                .order(myOrder)
                .build();
        myOrder.setCarts(Collections.singleton(cart));
        HttpSession session = new MockHttpSession();
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(orderRepository.findById(myOrder.getId())).thenReturn(Optional.of(myOrder));
        boolean res = cartService.changeCount(cart.getId(), 2, null, session);
        assertFalse(res);
    }

    @Test
    void changeCountFromSession() {
        Order myOrder = Order.builder()
                .id(1L)
                .build();
        Cart cart = Cart.builder()
                .id(1L)
                .count(1)
                .order(myOrder)
                .build();
        myOrder.setCarts(Collections.singleton(cart));
        HttpSession session = new MockHttpSession();
        session.setAttribute("orderSession", myOrder);
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(orderRepository.findById(myOrder.getId())).thenReturn(Optional.of(myOrder));
        when(orderRepository.getOne(cart.getOrder().getId())).thenReturn((myOrder));
        boolean res = cartService.changeCount(cart.getId(), 2, null, session);
        assertTrue(res);
    }


    @Test
    void deleteItemForUserFailedCartIsNotExist() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());
        boolean res = cartService.deleteItem(1L, user, null);
        assertFalse(res);
    }

    @Test
    void deleteItemForUserFailedOrderIsNotExist() {
        Cart cart = Cart.builder()
                .id(1L)
                .build();
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(Optional.empty());
        boolean res = cartService.deleteItem(cart.getId(), user, null);
        assertFalse(res);
    }

    @Test
    void deleteItemForUserFailedOrderHasNotCart() {
        Cart cart = Cart.builder()
                .id(5L)
                .build();
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(Optional.ofNullable(order));
        boolean res = cartService.deleteItem(cart.getId(), user, null);
        assertFalse(res);
    }

    @Test
    void deleteItemForUserWhenCartSizeTwo() {
        when(cartRepository.findById(cartFirst.getId())).thenReturn(Optional.of(cartFirst));
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(Optional.ofNullable(order));

        boolean res = cartService.deleteItem(cartFirst.getId(), user, null);
        assertTrue(res);
        verify(cartRepository, times(1)).delete(cartFirst);
    }

    @Test
    void deleteItemForUserWhenCartSizeOne() {
        Order orderNew = Order.builder()
                .user(user)
                .carts(Collections.singleton(cartFirst))
                .build();
        HttpSession session = new MockHttpSession();

        when(cartRepository.findById(cartFirst.getId())).thenReturn(Optional.of(cartFirst));
        when(orderRepository.findOrderByStatusAndUser(StatusOrder.OPEN, user)).thenReturn(Optional.ofNullable(orderNew));

        boolean res = cartService.deleteItem(cartFirst.getId(), user, session);
        assertTrue(res);
        verify(orderRepository, times(1)).delete(orderNew);
    }

    @Test
    void deleteItemFromSessionWhereOrderIsNull() {
        HttpSession session = new MockHttpSession();
        session.setAttribute("orderSession", null);
        when(cartRepository.findById(cartFirst.getId())).thenReturn(Optional.of(cartFirst));

        boolean res = cartService.deleteItem(cartFirst.getId(), null, session);
        assertFalse(res);
    }

    @Test
    void deleteItemFromSession() {
        Order order = Order.builder()
                .id(1L)
                .carts(Collections.singleton(cartFirst))
                .status(StatusOrder.OPEN)
                .build();
        HttpSession session = new MockHttpSession();
        session.setAttribute("orderSession", order);
        when(cartRepository.findById(cartFirst.getId())).thenReturn(Optional.of(cartFirst));
        when((orderRepository.findById(order.getId()))).thenReturn(Optional.of(order));

        boolean res = cartService.deleteItem(cartFirst.getId(), null, session);
        assertTrue(res);
    }



}