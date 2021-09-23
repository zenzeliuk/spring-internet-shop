package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String login);
    List<UserDTO> findAll();
    User findById(String id);
    User save(User user);
}
