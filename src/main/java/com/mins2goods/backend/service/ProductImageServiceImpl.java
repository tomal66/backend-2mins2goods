package com.mins2goods.backend.service;

import com.mins2goods.backend.model.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageServiceImpl implements ProductImageService{

    private final String STORAGE_PATH="C:\\Users\\User\\Desktop\\Storage\\";
    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String url = STORAGE_PATH+file.getOriginalFilename();
        //productImage.setImageUrl(url);

        file.transferTo(new File(url));
        return url;
    }

    @Override
    public byte[] downloadImage(String url) throws IOException {
        return Files.readAllBytes(new File(url).toPath());
    }
}
