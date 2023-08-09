package com.web.farm.FarmShop.model;

import com.web.farm.FarmShop.domain.Account;
import com.web.farm.FarmShop.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;

    private Account customer;

    private Product product;

    private int quantity;

    private double totalPrice;
}
