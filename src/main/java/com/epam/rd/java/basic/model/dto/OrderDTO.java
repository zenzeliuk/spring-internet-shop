package com.epam.rd.java.basic.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {

    private String id;
    private String login;
    private String createTime;
    private String updateTime;
    private String totalPrice;
    private String status;

}
