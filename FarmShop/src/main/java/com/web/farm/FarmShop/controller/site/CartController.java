package com.web.farm.FarmShop.controller.site;

import com.web.farm.FarmShop.domain.Product;
import com.web.farm.FarmShop.global.GlobalData;
import com.web.farm.FarmShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
    @Autowired
    private ProductService productService;

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") Long id) {
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/home";
    }

    @GetMapping("/cart")
    public String getCartItems(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getUnitPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "/site/fragments/cart";
    }
}
