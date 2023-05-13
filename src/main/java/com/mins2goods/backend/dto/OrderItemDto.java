package com.mins2goods.backend.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long itemId;
    private Long quantity;
    private Long orderId;
    private Long productId;
    private String status;
    private String deliveryMethod;
}
