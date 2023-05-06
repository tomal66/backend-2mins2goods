package com.mins2goods.backend.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private Long orderId;
    private Long total;
    private String paymentMethod;
    private String status;
    private String buyerUsername;
    private Date createdAt;
    private Date updatedAt;
    private List<OrderItemDto> orderItems;
}
