package com.mins2goods.backend.api;

import com.mins2goods.backend.dto.ProductDto;
import com.mins2goods.backend.model.Product;
import com.mins2goods.backend.model.User;
import com.mins2goods.backend.service.ProductService;
import com.mins2goods.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductResource {
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/nearby")
    public ResponseEntity<List<Product>> getNearbyProducts(@RequestParam("latitude") double latitude,
                                                           @RequestParam("longitude") double longitude)
    {
        List<Product> nearbyProducts = productService.getNearbyProducts(latitude, longitude);
        return ResponseEntity.ok().body(nearbyProducts);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product> allProducts = productService.getAllProducts();
        return ResponseEntity.ok().body(allProducts);
    }

    @GetMapping("/seller")
    public ResponseEntity<List<Product>> getBySeller(@RequestParam("seller") String username)
    {
        List<Product> products = productService.findBySeller(username);

        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto productDto) {
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

        productService.createProduct(product);

        return new ResponseEntity<>("Product added", HttpStatus.OK);
    }

    @GetMapping("/{productid}")
    public ResponseEntity<Product> getById(@PathVariable Long productid)
    {
        return ResponseEntity.ok().body(productService.getProductById(productid));
    }


}
