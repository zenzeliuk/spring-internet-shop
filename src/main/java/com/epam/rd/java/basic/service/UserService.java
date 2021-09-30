package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean save(User user);
    UserDetails loadUserByUsername(String login);
    void changeStatus(Long id);
    void changeRole(Long id);
    void update(User user, String login, String password, String firstName, String lastName, String email);
    List<UserDTO> getUsersWithFilter(Integer page, Integer size, String sortField, String sortDir);
}
