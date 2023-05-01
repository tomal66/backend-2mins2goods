package com.mins2goods.backend.service;

import com.mins2goods.backend.model.Product;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.repository.ProductRepository;
import com.mins2goods.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Override
    public Product getProductById(Long productId) {
        return productRepository.findByProductId(productId);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> updateProduct(Product updatedProduct) {
        Long productId = updatedProduct.getProductId();
        return productRepository.findById(productId)
                .map(product -> {
                    product.setTitle(updatedProduct.getTitle());
                    product.setDescription(updatedProduct.getDescription());
                    product.setPrice(updatedProduct.getPrice());
                    product.setQuantity(updatedProduct.getQuantity());
                    product.setLatitude(updatedProduct.getLatitude());
                    product.setLongitude(updatedProduct.getLongitude());
                    return productRepository.save(product);
                });
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.delete(productRepository.findByProductId(productId));
    }

    @Override
    public List<Product> findBySeller(String username) {
        User seller = userRepository.findByUsername(username);
        return productRepository.findBySeller(seller);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getNearbyProducts(Double latitude, Double longitude) {
        return productRepository.findNearbyProducts(latitude, longitude);
    }
}
