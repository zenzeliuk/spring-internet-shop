package com.epam.rd.java.basic.service;

import com.epam.rd.java.basic.exception.ServiceException;
import com.epam.rd.java.basic.model.User;

public interface UserService {

    User create(User user) throws ServiceException;
}
