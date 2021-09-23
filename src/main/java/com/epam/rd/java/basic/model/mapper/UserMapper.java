package com.epam.rd.java.basic.model.mapper;

import com.epam.rd.java.basic.model.User;
import com.epam.rd.java.basic.model.dto.UserDTO;

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

    public static List<UserDTO> toUserDTOList(List<User> userList){
        return userList.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }
}
