package com.web.farm.FarmShop.respository;

import com.web.farm.FarmShop.domain.CartItem;
import com.web.farm.FarmShop.domain.Customer;
import com.web.farm.FarmShop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCustomer(Customer customer);

    CartItem findByCustomerAndProduct(Customer customer, Product product);
}
