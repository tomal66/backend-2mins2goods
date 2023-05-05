package com.mins2goods.backend.service;

import com.mins2goods.backend.model.CartItem;
import com.mins2goods.backend.repository.CartItemRepository;
import com.mins2goods.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService{
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Optional<CartItem> getCartItemById(Long itemId) {
        return cartItemRepository.findById(itemId);
    }

    @Override
    public List<CartItem> getCartItemsByBuyerUsername(String username) {
        return cartItemRepository.findByBuyerId(userRepository.findByUsername(username).getUserId());
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }
}
