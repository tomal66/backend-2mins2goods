package com.mins2goods.backend.service.impl;

import com.mins2goods.backend.model.ProductImage;
import com.mins2goods.backend.repository.ProductImageRepository;
import com.mins2goods.backend.repository.ProductRepository;
import com.mins2goods.backend.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    private final String STORAGE_PATH="C:\\Users\\User\\Desktop\\Storage\\";
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String url = STORAGE_PATH+file.getOriginalFilename();
        ProductImage productImage = new ProductImage();
        productImage.setSortOrder(1);
        productImage.setAltText("Image");
        productImage.setType(file.getContentType());
        productImage.setImageUrl(url);

        productImageRepository.save(productImage);
        file.transferTo(new File(url));
        return productImage.getImageId().toString();
    }

    @Override
    public byte[] downloadImage(Long imageId) throws IOException {
        Optional<ProductImage> productImage = productImageRepository.findById(imageId);
        String url = productImage.get().getImageUrl();

        return Files.readAllBytes(new File(url).toPath());
    }
}
