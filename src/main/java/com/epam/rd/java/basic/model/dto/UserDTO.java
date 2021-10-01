package com.epam.rd.java.basic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {

    private String id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private String role;
    private Integer currentPage;
    private Integer totalPage;

}
