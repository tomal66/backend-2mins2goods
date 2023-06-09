package com.mins2goods.backend.service;

import com.mins2goods.backend.model.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductImageService {
    Long uploadImage(MultipartFile file) throws IOException;
    byte[] downloadImage(Long imageId) throws IOException;
    void deleteImage(Long imageId) throws IOException;
}
