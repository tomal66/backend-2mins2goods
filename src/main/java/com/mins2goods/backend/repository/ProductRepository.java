package com.mins2goods.backend.repository;

import com.mins2goods.backend.model.Product;
import com.mins2goods.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductId(Long productId);

    @Query("SELECT p FROM Product p WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) " +
            "* cos(radians(p.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(p.latitude)))) * 1000 <= 500")
    List<Product> findNearbyProducts(@Param("latitude") double latitude, @Param("longitude") double longitude);

    List<Product> findBySeller(User seller);

}
