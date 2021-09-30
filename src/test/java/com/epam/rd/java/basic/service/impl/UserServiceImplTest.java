package com.epam.rd.java.basic.service.impl;

import com.epam.rd.java.basic.model.Role;
import com.epam.rd.java.basic.model.StatusUser;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.repository.UserRepository;
import com.epam.rd.java.basic.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;


    @Test
    void changeStatusToBlock() {
        User user = User.builder()
                .id(1L)
                .statusUser(StatusUser.ACTIVE)
                .build();
        doReturn(user).when(userRepository).getOne(user.getId());
        userService.changeStatus(user.getId());
        assertTrue(CoreMatchers.is(user.getStatusUser()).matches(StatusUser.BLOCKED));
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).getOne(user.getId());
    }

    @Test
    void changeStatusToActive() {
        User user = User.builder()
                .id(1L)
                .statusUser(StatusUser.BLOCKED)
                .build();
        doReturn(user).when(userRepository).getOne(user.getId());
        userService.changeStatus(user.getId());
        assertTrue(CoreMatchers.is(user.getStatusUser()).matches(StatusUser.ACTIVE));
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).getOne(user.getId());
    }

    @Test
    void changeRoleToAdmin() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.USER);
        User user = User.builder()
                .id(1L)
                .roles(roleSet)
                .build();
        doReturn(user).when(userRepository).getOne(user.getId());
        userService.changeRole(user.getId());
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.ADMIN)));
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).getOne(user.getId());
    }

    @Test
    void changeRoleToUser() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.ADMIN);
        User user = User.builder()
                .id(1L)
                .roles(roleSet)
                .build();
        doReturn(user).when(userRepository).getOne(user.getId());
        userService.changeRole(user.getId());
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).getOne(user.getId());
    }

    @Test
    void update() {
        User user = User.builder()
                .id(1L)
                .login("log")
                .password("pass")
                .firsName("123")
                .lastName("234")
                .email("345")
                .build();
        String login = "new log";
        String password = "new pas";
        String firstName = "new firs";
        String lastName = "new last";
        String email = "new email";
        userService.update(user, login, password, firstName, lastName, email);
        assertEquals(login, user.getLogin());
        assertTrue(passwordEncoder.matches(password, user.getPassword()));
        assertEquals(firstName, user.getFirsName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void saveFail() {
        User user = User.builder()
                .id(1L)
                .login("test")
                .build();
        when(userRepository.existsByLogin(user.getLogin())).thenReturn(true);
        assertFalse(userService.save(user));
        verify(userRepository, times(0)).save(user);
        verify(userRepository, times(1)).existsByLogin(user.getLogin());
    }


    @Test
    void save() {
        String password = "test";
        User user = User.builder()
                .id(1L)
                .login(password)
                .password("test")
                .build();
        when(userRepository.existsByLogin(user.getLogin())).thenReturn(false);
        assertTrue(userService.save(user));
        assertTrue(passwordEncoder.matches(password, user.getPassword()));
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        assertTrue(CoreMatchers.is(user.getStatusUser()).matches(StatusUser.ACTIVE));
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).existsByLogin(user.getLogin());
    }
}