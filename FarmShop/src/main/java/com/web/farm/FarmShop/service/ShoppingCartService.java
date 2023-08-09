package com.web.farm.FarmShop.service;

import com.web.farm.FarmShop.domain.CartItem;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.List;

public interface ShoppingCartService {

    List<CartItem> findAll(UserDetails userDetails);

    CartItem addToCart(Long productId, Integer quantity, UserDetails userDetails);

    @Transactional
    void removeProduct(UserDetails userDetails, Long productId);

    double calculateTotalPrice(List<CartItem> cartItems);

    @Transactional
    void clearCart(UserDetails userDetails);
}
