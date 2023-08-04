package com.web.farm.FarmShop.service;

import com.web.farm.FarmShop.domain.CartItem;
import com.web.farm.FarmShop.domain.Customer;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ShoppingCartService {
    List<CartItem> findAll(Customer customer);

    CartItem addToCart(Long productId, Integer quantity, Customer customer);

    void removeProduct(Customer customer, Long productId);

    double calculateTotalPrice(List<CartItem> cartItems);

    @Transactional
    void clearCart(Customer customer);
}
