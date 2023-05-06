package com.mins2goods.backend.service;

import com.mins2goods.backend.dto.OrderDto;
import com.mins2goods.backend.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Order order);
    Optional<Order> getOrderById(Long orderId);
    List<Order> getOrdersByBuyerUsername(String username);
    Order updateOrder(Order order);
    void deleteOrder(Long orderId);
    Order convertToEntity(OrderDto orderDto);
    OrderDto convertToDto(Order order);
}
