package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.Role;
import com.epam.rd.java.basic.model.StatusUser;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.repository.UserRepository;
import com.epam.rd.java.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void changeStatus(Long id) {
        User user = userRepository.getOne(id);
        if (user.getStatusUser().equals(StatusUser.BLOCKED)) {
            user.setStatusUser(StatusUser.ACTIVE);
        } else {
            user.setStatusUser(StatusUser.BLOCKED);
        }
        userRepository.save(user);
    }

    @Override
    public void changeRole(Long id) {
        User user = userRepository.getOne(id);
        if (user.getRoles().contains(Role.USER)) {
            user.getRoles().clear();
            user.getRoles().add(Role.ADMIN);
        } else {
            user.getRoles().clear();
            user.getRoles().add(Role.USER);
        }
        userRepository.save(user);
    }

    @Override
    public void update(User user, String login, String password, String firstName, String lastName, String email) {
        user.setLogin(login);
        user.setPassword(password);
        user.setFirsName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepository.save(user);
    }

    @Override
    public boolean save(User user) {
        if (userRepository.existsByLogin(user.getLogin())){
            return false;
        }
        user.setStatusUser(StatusUser.ACTIVE);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}
