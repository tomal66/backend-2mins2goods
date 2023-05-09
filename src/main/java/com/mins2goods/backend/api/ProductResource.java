package com.mins2goods.backend.api;

import com.mins2goods.backend.dto.ProductDto;
import com.mins2goods.backend.model.Product;
import com.mins2goods.backend.model.ProductImage;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.repository.ProductImageRepository;
import com.mins2goods.backend.service.ProductImageService;
import com.mins2goods.backend.service.ProductService;
import com.mins2goods.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductResource {
    private final ProductService productService;
    private final UserService userService;
    private final ProductImageService productImageService;
    private final ProductImageRepository productImageRepository;

    @GetMapping("/nearby")
    public ResponseEntity<List<ProductDto>> getNearbyProducts(@RequestParam("latitude") double latitude,
                                                              @RequestParam("longitude") double longitude) {
        List<Product> nearbyProducts = productService.getNearbyProducts(latitude, longitude);
        List<ProductDto> nearbyProductDtos = nearbyProducts.stream()
                .map(this::convertProductToProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(nearbyProductDtos);
    }


    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts()
    {
        List<Product> allProducts = productService.getAllProducts();
        List<ProductDto> allProductDtos = allProducts.stream()
                .map(this::convertProductToProductDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(allProductDtos);
    }

    @GetMapping("/seller")
    public ResponseEntity<List<ProductDto>> getBySeller(@RequestParam("seller") String username)
    {
        List<Product> products = productService.findBySeller(username);
        List<ProductDto> productDtos = products.stream()
                .map(this::convertProductToProductDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(productDtos);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestPart("product") ProductDto productDto, @RequestPart("images") List<MultipartFile> images) throws IOException {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLatitude(productDto.getLatitude());
        product.setLongitude(productDto.getLongitude());
        product.setCategory(productDto.getCategory());
        User seller = userService.getUser(productDto.getSellerUsername());
        product.setSeller(seller);

        productService.createProduct(product); // Save the product first to generate the productId

        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile image : images) {
            Long imageId = productImageService.uploadImage(image);
            ProductImage productImage = productImageRepository.findById(imageId).orElse(null);
            productImage.setProduct(product); // Associate the product with the image
            productImages.add(productImage);
        }
        product.setProductImages(productImages);

        productService.updateProduct(product); // Update the product with the associated images

        return new ResponseEntity<>("Product added", HttpStatus.OK);
    }


    @GetMapping("/{productid}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long productid)
    {
        return ResponseEntity.ok().body(convertProductToProductDto(productService.getProductById(productid)));
    }

    public ProductDto convertProductToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        productDto.setLatitude(product.getLatitude());
        productDto.setLongitude(product.getLongitude());
        productDto.setSellerUsername(product.getSeller().getUsername());
        productDto.setCategory(product.getCategory());

        List<Integer> imageIds = product.getProductImages().stream()
                .map(image -> image.getImageId().intValue())
                .collect(Collectors.toList());
        productDto.setImages(imageIds);

        return productDto;
    }



}
