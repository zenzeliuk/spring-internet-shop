package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.Cart;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.repository.CartRepository;
import com.epam.rd.java.basic.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

}
