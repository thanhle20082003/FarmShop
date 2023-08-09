package com.web.farm.FarmShop.service.impl;



import com.web.farm.FarmShop.domain.Account;
import com.web.farm.FarmShop.domain.CartItem;
import com.web.farm.FarmShop.domain.Product;
import com.web.farm.FarmShop.respository.CartItemRepository;
import com.web.farm.FarmShop.respository.ProductRepository;
import com.web.farm.FarmShop.service.AccountService;
import com.web.farm.FarmShop.service.ShoppingCartService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private CartItemRepository itemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountService accountService;

    @Override
    public List<CartItem> findAll(UserDetails userDetails) {
        // Lấy tên người dùng từ UserDetails
        String username = userDetails.getUsername();

        // Sử dụng tên người dùng để lấy thông tin Account
        Optional<Account> account = accountService.findById(username);

        // Sử dụng thông tin Account để truy vấn danh sách CartItem
        return itemRepository.findByAccount(account.get());
    }


    @Override
    public CartItem addToCart(Long productId, Integer quantity, UserDetails userDetails) {
        // Lấy tên người dùng từ UserDetails
        String username = userDetails.getUsername();

        // Sử dụng tên người dùng để lấy thông tin Account
        Optional<Account> account = accountService.findById(username);
        System.out.println("Account" +  account);


        Integer addedQuantity = quantity;

        Product product = productRepository.findById(productId).get();

        CartItem  cartItem =  itemRepository.findByAccountAndProduct(account.get(), product);

        if(cartItem != null) {
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
            cartItem.setTotalPrice(addedQuantity * product.getUnitPrice());
        } else {
            cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setAccount(account.get());
            cartItem.setProduct(product);
            cartItem.setTotalPrice(product.getUnitPrice() * quantity);
        }

        return itemRepository.save(cartItem);
    }
    @Override
    @Transactional
    public void removeProduct(UserDetails userDetails, Long productId) {
        String username = userDetails.getUsername();
        itemRepository.deleteByAccountAndProduct(username, productId);
    }
    @Override
    public double calculateTotalPrice(List<CartItem> cartItems) {
        return cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getUnitPrice() * cartItem.getQuantity())
                .sum();
    }
    @Override
    @Transactional
    public void clearCart(UserDetails userDetails) {

        String username = userDetails.getUsername();

        // Sử dụng tên người dùng để lấy thông tin Account
        Optional<Account> account = accountService.findById(username);

        List<CartItem> cartItems = itemRepository.findByAccount(account.get());
        itemRepository.deleteAll(cartItems);
    }
}