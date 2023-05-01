package com.mins2goods.backend.service;

import com.mins2goods.backend.model.Product;
import com.mins2goods.backend.model.ProductImage;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product getProductById(Long productId);
    Product createProduct(Product product);
    Optional<Product> updateProduct(Product updatedProduct);
    void deleteProduct(Long productId);
    List<Product> findBySeller(String username);
    List<Product> getAllProducts();
    List<Product> getNearbyProducts(Double latitude, Double longitude);

}
