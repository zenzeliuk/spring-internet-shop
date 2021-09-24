package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String login);
    Page<User> findAll(Pageable pageable);
    User findById(String id);
    User save(User user);
}
