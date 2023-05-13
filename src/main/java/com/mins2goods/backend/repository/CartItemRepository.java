package com.mins2goods.backend.repository;

import com.mins2goods.backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.buyer.userId = :buyerId")
    List<CartItem> findByBuyerId(Long buyerId);
    @Query("DELETE  FROM CartItem ci WHERE ci.buyer.userId = :buyerId")
    void deleteAllByBuyerId(Long buyerId);
}

