package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.Role;
import com.epam.rd.java.basic.model.StatusUser;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.repository.UserRepository;
import com.epam.rd.java.basic.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void changeStatusToBlock() {
        User user = User.builder()
                .id(1L)
                .statusUser(StatusUser.ACTIVE)
                .build();
        Mockito.doReturn(user).when(userRepository).getOne(user.getId());
        userService.changeStatus(user.getId());
        Assertions.assertTrue(CoreMatchers.is(user.getStatusUser()).matches(StatusUser.BLOCKED));
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(userRepository, Mockito.times(1)).getOne(user.getId());
    }

    @Test
    void changeStatusToActive() {
        User user = User.builder()
                .id(1L)
                .statusUser(StatusUser.BLOCKED)
                .build();
        Mockito.doReturn(user).when(userRepository).getOne(user.getId());
        userService.changeStatus(user.getId());
        Assertions.assertTrue(CoreMatchers.is(user.getStatusUser()).matches(StatusUser.ACTIVE));
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(userRepository, Mockito.times(1)).getOne(user.getId());
    }

    @Test
    void changeRoleToAdmin() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.USER);
        User user = User.builder()
                .id(1L)
                .roles(roleSet)
                .build();
        Mockito.doReturn(user).when(userRepository).getOne(user.getId());
        userService.changeRole(user.getId());
        Assertions.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.ADMIN)));
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(userRepository, Mockito.times(1)).getOne(user.getId());
    }

    @Test
    void changeRoleToUser() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.ADMIN);
        User user = User.builder()
                .id(1L)
                .roles(roleSet)
                .build();
        Mockito.doReturn(user).when(userRepository).getOne(user.getId());
        userService.changeRole(user.getId());
        Assertions.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(userRepository, Mockito.times(1)).getOne(user.getId());
    }
}