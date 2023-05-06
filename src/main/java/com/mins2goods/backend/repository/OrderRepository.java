package com.mins2goods.backend.repository;

import com.mins2goods.backend.model.Orders;
import com.mins2goods.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByBuyer(User buyer);
}
