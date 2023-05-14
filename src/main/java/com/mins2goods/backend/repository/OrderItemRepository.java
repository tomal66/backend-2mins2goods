package com.mins2goods.backend.repository;

import com.mins2goods.backend.model.Orders;
import com.mins2goods.backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Optional<Orders> order);
    @Query("SELECT oi FROM OrderItem oi JOIN oi.product p JOIN p.seller u WHERE u.username = :username")
    List<OrderItem> findBySellerUsername(@Param("username") String username);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.buyer.username = :username")
    List<OrderItem> findsByBuyerUsername(@Param("username") String username);
}
