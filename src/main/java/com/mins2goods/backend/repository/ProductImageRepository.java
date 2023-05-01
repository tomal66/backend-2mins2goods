package com.mins2goods.backend.repository;

import com.mins2goods.backend.model.Product;
import com.mins2goods.backend.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProduct(Product product);
}
