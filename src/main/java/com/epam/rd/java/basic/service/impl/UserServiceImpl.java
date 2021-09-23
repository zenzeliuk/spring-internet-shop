package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.UserDTO;
import com.epam.rd.java.basic.model.mapper.UserMapper;
import com.epam.rd.java.basic.repository.UserRepository;
import com.epam.rd.java.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<UserDTO> findAll() {
        return UserMapper.toUserDTOList(userRepository.findAll());
    }

    @Override
    public User findById(String id) {
        return userRepository.getOne(Long.valueOf(id));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
