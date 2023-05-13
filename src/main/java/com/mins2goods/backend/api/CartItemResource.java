package com.mins2goods.backend.api;

import com.mins2goods.backend.dto.CartItemDto;
import com.mins2goods.backend.model.CartItem;
import com.mins2goods.backend.service.CartItemService;
import com.mins2goods.backend.service.ProductService;
import com.mins2goods.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cartitem")
@CrossOrigin(origins = "http://localhost:3000")
public class CartItemResource {
    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CartItemDto> addCartItem(@RequestBody CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem(
                null,
                cartItemDto.getQuantity(),
                productService.getProductById(cartItemDto.getProductId()),
                userService.getUser(cartItemDto.getBuyerUsername())
        );
        CartItem savedCartItem = cartItemService.addCartItem(cartItem);
        CartItemDto savedCartItemDto = new CartItemDto(
                savedCartItem.getItemId(),
                savedCartItem.getQuantity(),
                savedCartItem.getProduct().getProductId(),
                savedCartItem.getBuyer().getUsername()
        );
        return new ResponseEntity<>(savedCartItemDto, HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<CartItemDto> getCartItemById(@PathVariable Long itemId) {
        Optional<CartItem> cartItemOptional = cartItemService.getCartItemById(itemId);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            CartItemDto cartItemDto = new CartItemDto(
                    cartItem.getItemId(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getProductId(),
                    cartItem.getBuyer().getUsername()
            );
            return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buyer/{buyerUsername}")
    public ResponseEntity<List<CartItemDto>> getCartItemsByBuyerUsername(@PathVariable String buyerUsername) {
        List<CartItem> cartItems = cartItemService.getCartItemsByBuyerUsername(buyerUsername);
        List<CartItemDto> cartItemDtos = cartItems.stream().map(cartItem -> new CartItemDto(
                cartItem.getItemId(),
                cartItem.getQuantity(),
                cartItem.getProduct().getProductId(),
                cartItem.getBuyer().getUsername()
        )).collect(Collectors.toList());
        return new ResponseEntity<>(cartItemDtos, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CartItemDto> updateCartItem(@RequestBody CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem(
                cartItemDto.getItemId(),
                cartItemDto.getQuantity(),
                productService.getProductById(cartItemDto.getProductId()),
                userService.getUser(cartItemDto.getBuyerUsername())
        );
        CartItem updatedCartItem = cartItemService.updateCartItem(cartItem);
        CartItemDto updatedCartItemDto = new CartItemDto(
                updatedCartItem.getItemId(),
                updatedCartItem.getQuantity(),
                updatedCartItem.getProduct().getProductId(),
                updatedCartItem.getBuyer().getUsername()
        );
        return new ResponseEntity<>(updatedCartItemDto, HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long itemId) {
        cartItemService.deleteCartItem(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/buyer/{buyerUsername}")
    public ResponseEntity<Void> clearCart(@PathVariable String buyerUsername){
        cartItemService.clearCart(buyerUsername);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
