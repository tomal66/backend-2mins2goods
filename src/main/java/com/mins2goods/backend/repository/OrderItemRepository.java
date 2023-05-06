package com.mins2goods.backend.repository;

import com.mins2goods.backend.model.Orders;
import com.mins2goods.backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Optional<Orders> order);
}
