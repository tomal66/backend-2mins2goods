package com.mins2goods.backend.service;

import com.mins2goods.backend.dto.OrderDto;
import com.mins2goods.backend.dto.OrderItemDto;
import com.mins2goods.backend.model.Order;
import com.mins2goods.backend.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    OrderItem createOrderItem(OrderItem orderItem);

    Optional<OrderItem> getOrderItemById(Long itemId);

    List<OrderItem> getOrderItemsByOrderId(Long orderId);

    OrderItem updateOrderItem(OrderItem orderItem);

    void deleteOrderItem(Long itemId);
    OrderItem convertToEntity(OrderItemDto orderItemDto);
    OrderItemDto convertToDto(OrderItem orderItem);
}
