package com.web.farm.FarmShop.controller.site;

import com.web.farm.FarmShop.domain.CartItem;
import com.web.farm.FarmShop.service.ShoppingCartService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("site/cart")
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("")
    public String cart(Model model, Authentication authentication){

        HttpSession session = request.getSession();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("userDetails" +  userDetails);

        List<CartItem> cartItems = shoppingCartService.findAll(userDetails);

        if(cartItems == null) {
            model.addAttribute("check");
        } else {
            double totalPrice = shoppingCartService.calculateTotalPrice(cartItems);
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
            Model model, Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("userDetails" +  userDetails);

        CartItem cartItem = shoppingCartService.addToCart(productId, quantity, userDetails);

        model.addAttribute("cart", cartItem);
        return "redirect:/site/cart";
    }

    @GetMapping("delete/{id}")
    public String addItemToCart(
            @PathVariable("id") Long productId,
            HttpSession session,
            Model model,
            Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();


        shoppingCartService.removeProduct(userDetails, productId);

        model.addAttribute("m", "Delete was successed");
        return "forward:/site/cart";
    }
}
