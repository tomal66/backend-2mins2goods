package com.mins2goods.backend.service.impl;

import com.mins2goods.backend.dto.OrderItemDto;
import com.mins2goods.backend.model.OrderItem;
import com.mins2goods.backend.model.Orders;
import com.mins2goods.backend.model.Product;
import com.mins2goods.backend.repository.OrderItemRepository;
import com.mins2goods.backend.repository.OrderRepository;
import com.mins2goods.backend.repository.ProductRepository;
import com.mins2goods.backend.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public Optional<OrderItem> getOrderItemById(Long itemId) {
        return orderItemRepository.findById(itemId);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        Optional<Orders> order = orderRepository.findById(orderId);
        return orderItemRepository.findByOrder(order);
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        Orders order = orderRepository.findById(orderItem.getOrder().getOrderId()).orElseThrow();
        order.setUpdatedAt(new Date());
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long itemId) {
        orderItemRepository.deleteById(itemId);
    }

    @Override
    public OrderItem convertToEntity(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        if(orderItemDto.getItemId()!=null){
            orderItem.setItemId(orderItemDto.getItemId());
        }
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setStatus(orderItemDto.getStatus());
        Product product = productRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + orderItemDto.getProductId()));
        orderItem.setProduct(product);
        orderItem.setDeliveryMethod(orderItemDto.getDeliveryMethod());

        // Fetch the Order entity from the database and set it on the OrderItem
        if(orderItemDto.getOrderId()!=null){
            Orders order = orderRepository.findById(orderItemDto.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderItemDto.getOrderId()));
            orderItem.setOrder(order);
        }


        return orderItem;
    }


    @Override
    public OrderItemDto convertToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setItemId(orderItem.getItemId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setOrderId(orderItem.getOrder().getOrderId());
        orderItemDto.setProductId(orderItem.getProduct().getProductId());
        orderItemDto.setStatus(orderItem.getStatus());
        orderItemDto.setDeliveryMethod(orderItem.getDeliveryMethod());

        return orderItemDto;
    }

    @Override
    public List<OrderItemDto> getOrdersBySeller(String username) {
        List<OrderItem> orderItems = orderItemRepository.findBySellerUsername(username);
        return orderItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


}
