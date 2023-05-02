package com.mins2goods.backend.api;

import com.mins2goods.backend.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageResource {
    private final ProductImageService productImageService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadedImage = productImageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadedImage);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadImage(@RequestParam Long imageId) throws IOException {
        byte[] imageData = productImageService.downloadImage(imageId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
