package com.web.farm.FarmShop.controller.site;

import com.web.farm.FarmShop.domain.CartItem;
import com.web.farm.FarmShop.domain.Customer;
import com.web.farm.FarmShop.service.ProductService;
import com.web.farm.FarmShop.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("site/cart")
public class CartController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping("")
    public String cart(Model model, HttpSession session){

        System.out.println(" Cart is running");
        Customer customer = (Customer) session.getAttribute("customer");
        System.out.println("Customer: " + customer.getName());

        List<CartItem> cartItems = shoppingCartService.findAll(customer);

        if(cartItems == null) {
            model.addAttribute("check");
        } else {
            double totalPrice = cartItems.stream()
                    .mapToDouble(cartItem -> cartItem.getProduct().getUnitPrice() * cartItem.getQuantity())
                    .sum();
            model.addAttribute("grandTotal", totalPrice);
            session.setAttribute("cartCount", cartItems.size());
            model.addAttribute("cart", cartItems);
        }

        return "/site/fragments/cart";
    }

    @GetMapping("add-to-cart/{id}")
    public String addItemToCart(
            @PathVariable("id") Long productId,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            HttpSession session,
            Model model){

        Customer customer = (Customer) session.getAttribute(("customer"));

        if(customer == null) {
            return "redirect:/slogin";
        }

        CartItem cartItem = shoppingCartService.addToCart(productId, quantity, customer);

        model.addAttribute("cart", cartItem);
        return "redirect:/site/cart";
    }

    @GetMapping("delete/{id}")
    public String addItemToCart(
            @PathVariable("id") Long productId,
            HttpSession session,
            Model model){

        Customer customer = (Customer) session.getAttribute(("customer"));

        if(customer == null) {
            return "redirect:/slogin";
        }

        shoppingCartService.removeProduct(customer, productId);

        model.addAttribute("m", "Delete was successed");
        return "forward:/site/cart";
    }
}
