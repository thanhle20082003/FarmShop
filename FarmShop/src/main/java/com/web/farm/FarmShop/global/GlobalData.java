package com.web.farm.FarmShop.global;

import com.web.farm.FarmShop.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Product> cart;

    static {
        cart = new ArrayList<Product>();
    }
}
