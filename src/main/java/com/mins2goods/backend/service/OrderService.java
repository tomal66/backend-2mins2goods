package com.mins2goods.backend.service;

import com.mins2goods.backend.dto.OrderDto;
import com.mins2goods.backend.model.Orders;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Orders createOrder(Orders order);
    Optional<Orders> getOrderById(Long orderId);
    List<Orders> getOrdersByBuyerUsername(String username);
    Orders updateOrder(Orders order);
    void deleteOrder(Long orderId);
    Orders convertToEntity(OrderDto orderDto);
    OrderDto convertToDto(Orders order);
}
