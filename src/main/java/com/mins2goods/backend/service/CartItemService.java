package com.mins2goods.backend.service;

import com.mins2goods.backend.model.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    CartItem addCartItem(CartItem cartItem);

    Optional<CartItem> getCartItemById(Long itemId);

    List<CartItem> getCartItemsByBuyerUsername(String username);

    CartItem updateCartItem(CartItem cartItem);

    void deleteCartItem(Long itemId);
    void clearCart(String username);

}
