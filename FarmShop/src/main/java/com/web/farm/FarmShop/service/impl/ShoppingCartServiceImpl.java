package com.web.farm.FarmShop.service.impl;

import com.web.farm.FarmShop.domain.CartItem;
import com.web.farm.FarmShop.domain.Customer;
import com.web.farm.FarmShop.domain.Product;
import com.web.farm.FarmShop.respository.CartItemRepository;
import com.web.farm.FarmShop.respository.ProductRepository;
import com.web.farm.FarmShop.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    CartItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<CartItem> findAll(Customer customer) {
        return itemRepository.findByCustomer(customer);
    }

    @Override
    public CartItem addToCart(Long productId, Integer quantity, Customer customer) {
        Integer addedQuantity = quantity;

        Product product = productRepository.findById(productId).get();

        CartItem  cartItem =  itemRepository.findByCustomerAndProduct(customer, product);

        if(cartItem != null) {
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
            cartItem.setTotalPrice(addedQuantity * product.getUnitPrice());
        } else {
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
            cartItem.setTotalPrice(product.getUnitPrice() * quantity);
        }

        return itemRepository.save(cartItem);
    }
    @Override
    @Transactional
    public void removeProduct(Customer customer, Long productId) {
        itemRepository.deleteByCustomerAndProduct(customer.getId(), productId);
    }
}