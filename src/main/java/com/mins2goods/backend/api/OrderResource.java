package com.mins2goods.backend.api;

import com.mins2goods.backend.dto.OrderDto;
import com.mins2goods.backend.model.Order;
import com.mins2goods.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderResource {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        Order order = orderService.convertToEntity(orderDto);
        orderService.createOrder(order);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return ResponseEntity.ok(orderService.convertToDto(order));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<OrderDto>> getOrdersByBuyerUsername(@PathVariable String username) {
        List<Order> orders = orderService.getOrdersByBuyerUsername(username);
        return ResponseEntity.ok(orders.stream().map(orderService::convertToDto).collect(Collectors.toList()));
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto) {
        Order order = orderService.updateOrder(orderService.convertToEntity(orderDto));
        return ResponseEntity.ok(orderService.convertToDto(order));
    }
}