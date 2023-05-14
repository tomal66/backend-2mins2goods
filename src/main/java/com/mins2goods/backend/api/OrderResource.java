package com.mins2goods.backend.api;

import com.mins2goods.backend.dto.OrderDto;
import com.mins2goods.backend.dto.OrderItemDto;
import com.mins2goods.backend.model.OrderItem;
import com.mins2goods.backend.model.Orders;
import com.mins2goods.backend.service.OrderItemService;
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
@CrossOrigin(origins = "http://localhost:3000")
public class OrderResource {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        Orders order = orderService.convertToEntity(orderDto);
        orderService.createOrder(order);
        return new ResponseEntity<>(orderService.convertToDto(order), HttpStatus.CREATED);
    }

    @PutMapping("/item")
    public ResponseEntity<OrderItemDto> updateOrderItem( @RequestBody OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemService.convertToEntity(orderItemDto);
        OrderItem updatedOrderItem = orderItemService.updateOrderItem(orderItem);
        return ResponseEntity.ok(orderItemService.convertToDto(updatedOrderItem));
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        Orders order = orderService.getOrderById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return ResponseEntity.ok(orderService.convertToDto(order));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderItemDto>> getAllOrders() {
        return ResponseEntity.ok(orderItemService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<OrderItemDto>> getOrdersByBuyerUsername(@PathVariable String username) {
        List<OrderItemDto> userOrderItems = orderItemService.getOrdersByBuyer(username);
        return ResponseEntity.ok(userOrderItems);
    }

    @GetMapping("/seller/{username}")
    public ResponseEntity<List<OrderItemDto>> getOrdersBySellerUsername(@PathVariable String username) {
        List<OrderItemDto> sellerOrderItems = orderItemService.getOrdersBySeller(username);
        return ResponseEntity.ok(sellerOrderItems);
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto) {
        Orders order = orderService.updateOrder(orderService.convertToEntity(orderDto));
        return ResponseEntity.ok(orderService.convertToDto(order));
    }

    @PutMapping("/{itemId}/cancel")
    public ResponseEntity<OrderItemDto> cancelOrderItem(@PathVariable Long itemId) {
        try {
            OrderItemDto cancelledOrderItem = orderItemService.cancelOrderItem(itemId);
            return new ResponseEntity<>(cancelledOrderItem, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
