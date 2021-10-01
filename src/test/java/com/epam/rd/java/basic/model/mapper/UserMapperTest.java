package com.epam.rd.java.basic.model.mapper;

import com.epam.rd.java.basic.model.Role;
import com.epam.rd.java.basic.model.StatusUser;
import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.UserDTO;
import com.epam.rd.java.basic.service.util.MyPageable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserMapperTest {

    private static User user;

    @BeforeAll
    static void setUp() {
        user = User.builder()
                .id(1L)
                .login("login")
                .firsName("firstName")
                .lastName("lastName")
                .email("email")
                .statusUser(StatusUser.ACTIVE)
                .roles(Collections.singleton(Role.USER))
                .build();
    }

    @Test
    void toUserDTO() {
        UserDTO userDTO = UserMapper.toUserDTO(user);
        assertEquals(String.valueOf(user.getId()), userDTO.getId());
        assertEquals(user.getLogin(), userDTO.getLogin());
        assertEquals(user.getFirsName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getStatusUser().name(), userDTO.getStatus());
        assertEquals(user.getRoles().toString(), userDTO.getRole());
    }

    @Test
    void toUserDTOList() {
        List<User> userList = List.of(user);
        Integer currentPage = 1;
        Integer size = 5;
        Pageable pageable = MyPageable.getPageable(currentPage, size, null, null);
        Page<User> userPage = new PageImpl<>(userList, pageable, 1);
        List<UserDTO> userDTOList = UserMapper.toUserDTOList(userPage);
        assertEquals(String.valueOf(user.getId()), userDTOList.get(0).getId());
        assertEquals(user.getLogin(), userDTOList.get(0).getLogin());
        assertEquals(user.getFirsName(), userDTOList.get(0).getFirstName());
        assertEquals(user.getLastName(), userDTOList.get(0).getLastName());
        assertEquals(user.getEmail(), userDTOList.get(0).getEmail());
        assertEquals(user.getStatusUser().name(), userDTOList.get(0).getStatus());
        assertEquals(user.getRoles().toString(), userDTOList.get(0).getRole());
        assertEquals(0, userDTOList.get(0).getCurrentPage());
        assertEquals(userList.size(), userDTOList.get(0).getTotalPage());
    }
}