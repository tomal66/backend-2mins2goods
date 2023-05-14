package com.mins2goods.backend.service;

import com.mins2goods.backend.dto.OrderItemDto;
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
    List<OrderItemDto> getOrdersBySeller(String username);
    List<OrderItemDto> getOrdersByBuyer(String username);
    List<OrderItemDto> getAll();
    OrderItemDto cancelOrderItem(Long itemId);
}
