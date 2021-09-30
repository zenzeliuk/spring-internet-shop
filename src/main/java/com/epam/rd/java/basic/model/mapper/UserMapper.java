package com.epam.rd.java.basic.model.mapper;

import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.UserDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(String.valueOf(user.getId()))
                .login(user.getLogin())
                .firstName(user.getFirsName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .status(user.getStatusUser().name())
                .role(user.getRoles().toString())
                .build();
    }

    public static List<UserDTO> toUserDTOList(Page<User> userPage) {
        List<UserDTO> userDTOList = userPage.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
        if (!userDTOList.isEmpty()) {
            userDTOList.get(0).setCurrentPage(userPage.getNumber());
            userDTOList.get(0).setTotalPage(userPage.getTotalPages());
        }
        return userDTOList;
    }
}
