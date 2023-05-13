package com.mins2goods.backend.service.impl;

import com.mins2goods.backend.dto.OrderDto;
import com.mins2goods.backend.dto.OrderItemDto;

import com.mins2goods.backend.model.OrderItem;
import com.mins2goods.backend.model.Orders;
import com.mins2goods.backend.model.Product;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.repository.OrderRepository;
import com.mins2goods.backend.repository.ProductRepository;
import com.mins2goods.backend.repository.UserRepository;
import com.mins2goods.backend.service.CartItemService;
import com.mins2goods.backend.service.OrderItemService;
import com.mins2goods.backend.service.OrderService;
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
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemService orderItemService;
    private final CartItemService cartItemService;

    @Override
    public Orders createOrder(Orders order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            long newStock = product.getQuantity() - orderItem.getQuantity();
            if (newStock < 0) {
                throw new RuntimeException("Not enough stock for product with id: " + product.getProductId());
            }
            product.setQuantity(Math.toIntExact(newStock));
            productRepository.save(product);
        }
        cartItemService.clearCart(order.getBuyer().getUsername());
        return orderRepository.save(order);
    }

    @Override
    public Optional<Orders> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Orders> getOrdersByBuyerUsername(String username) {
        User buyer = userRepository.findByUsername(username);
        return orderRepository.findByBuyer(buyer);
    }

    @Override
    public Orders updateOrder(Orders order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Orders convertToEntity(OrderDto orderDto) {
        Orders order = new Orders();
        if(orderDto.getOrderId()!=null){
            order.setOrderId(orderDto.getOrderId());
        }
        order.setTotal(orderDto.getTotal());
        order.setDeliveryMethod(orderDto.getDeliveryMethod());
//        order.setCreatedAt(orderDto.getCreatedAt());
//        order.setUpdatedAt(orderDto.getUpdatedAt());
        order.setBuyer(userRepository.findByUsername(orderDto.getBuyerUsername()));
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
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
    public OrderDto convertToDto(Orders order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setTotal(order.getTotal());
        orderDto.setDeliveryMethod(order.getDeliveryMethod());
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
