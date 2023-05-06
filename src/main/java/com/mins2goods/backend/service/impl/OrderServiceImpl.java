package com.mins2goods.backend.service.impl;

import com.mins2goods.backend.dto.OrderDto;
import com.mins2goods.backend.dto.OrderItemDto;
import com.mins2goods.backend.model.Order;
import com.mins2goods.backend.model.OrderItem;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.repository.OrderRepository;
import com.mins2goods.backend.repository.UserRepository;
import com.mins2goods.backend.service.OrderItemService;
import com.mins2goods.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemService orderItemService;

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> getOrdersByBuyerUsername(String username) {
        User buyer = userRepository.findByUsername(username);
        return orderRepository.findByBuyerId(buyer.getUserId());
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order convertToEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderId(orderDto.getOrderId());
        order.setTotal(orderDto.getTotal());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setStatus(orderDto.getStatus());
        order.setCreatedAt(orderDto.getCreatedAt());
        order.setUpdatedAt(orderDto.getUpdatedAt());
        order.setBuyer(userRepository.findByUsername(orderDto.getBuyerUsername()));
        List<OrderItem> orderItems = orderDto.getOrderItems().stream()
                .map(orderItemDto -> {
                    OrderItem orderItem = orderItemService.convertToEntity(orderItemDto);
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        return order;
    }

    @Override
    public OrderDto convertToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setTotal(order.getTotal());
        orderDto.setPaymentMethod(order.getPaymentMethod());
        orderDto.setStatus(order.getStatus());
        orderDto.setCreatedAt(order.getCreatedAt());
        orderDto.setUpdatedAt(order.getUpdatedAt());
        orderDto.setBuyerUsername(order.getBuyer().getUsername());

        List<OrderItemDto> orderItemDtos = order.getOrderItems().stream()
                .map(orderItem -> orderItemService.convertToDto(orderItem))
                .collect(Collectors.toList());

        orderDto.setOrderItems(orderItemDtos);

        return orderDto;
    }

}
