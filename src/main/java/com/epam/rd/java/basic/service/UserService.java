package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean save(User user);
    UserDetails loadUserByUsername(String login);
    Page<User> findAll(Pageable pageable);
    User findById(Long id);
    void changeStatus(Long id);
    void changeRole(Long id);
    void update(User user, String login, String password, String firstName, String lastName, String email);
}
