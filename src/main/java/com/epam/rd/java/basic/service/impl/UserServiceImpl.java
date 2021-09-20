package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.exception.ServiceException;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.repository.UserRepository;
import com.epam.rd.java.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.ServerCloneException;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(User user) throws ServiceException {
        if (isNull(user.getId()) &&
                nonNull(user.getLogin()) &&
                nonNull(user.getPassword()) &&
                nonNull(user.getUserDetails())
        ) {
            return userRepository.save(user);
        }
        throw new ServiceException("Can not create user. " + user);
    }
}
