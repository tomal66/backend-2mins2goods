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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    private final String STORAGE_PATH="C:\\Users\\User\\Desktop\\Storage\\";
    @Override
    public Long uploadImage(MultipartFile file) throws IOException {
        String url = STORAGE_PATH+file.getOriginalFilename();
        ProductImage productImage = new ProductImage();
        productImage.setSortOrder(1);
        productImage.setAltText("Image");
        productImage.setType(file.getContentType());
        productImage.setImageUrl(url);
        productImageRepository.save(productImage);
        file.transferTo(new File(url));
        return productImage.getImageId();
    }

    @Override
    public byte[] downloadImage(Long imageId) throws IOException {
        Optional<ProductImage> productImage = productImageRepository.findById(imageId);
        String url = productImage.get().getImageUrl();

        return Files.readAllBytes(new File(url).toPath());
    }

    @Override
    public void deleteImage(Long imageId) throws IOException {
        Optional<ProductImage> productImageOpt = productImageRepository.findById(imageId);
        if (productImageOpt.isPresent()) {
            ProductImage productImage = productImageOpt.get();
            String url = productImage.getImageUrl();

            // Delete image file from storage
            File file = new File(url);
            if (file.delete()) {
                // Delete record from database
                productImageRepository.delete(productImage);
            } else {
                throw new IOException("Could not delete file: " + url);
            }
        } else {
            throw new FileNotFoundException("Image not found");
        }
    }

}
