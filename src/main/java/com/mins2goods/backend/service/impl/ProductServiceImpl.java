package com.mins2goods.backend.service.impl;

import com.mins2goods.backend.model.Product;
import com.mins2goods.backend.model.ProductImage;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.repository.ProductRepository;
import com.mins2goods.backend.repository.UserRepository;
import com.mins2goods.backend.service.ProductImageService;
import com.mins2goods.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductImageService productImageService;
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
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            List<ProductImage> images = product.getProductImages();
            for (ProductImage image : images) {
                try {
                    productImageService.deleteImage(image.getImageId());
                } catch (IOException e) {
                    // log the error and continue with the next image
                }
            }
            productRepository.delete(product);
        }  // handle product not found situation

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
