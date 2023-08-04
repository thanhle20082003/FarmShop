package com.web.farm.FarmShop.controller.site;

import com.web.farm.FarmShop.domain.CartItem;
import com.web.farm.FarmShop.domain.Customer;
import com.web.farm.FarmShop.domain.Order;
import com.web.farm.FarmShop.domain.OrderDetail;
import com.web.farm.FarmShop.service.OrderService;
import com.web.farm.FarmShop.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("site/order")
public class OrderController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("")
    public String orderList(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/slogin";
        }

        List<Order> order = orderService.findOrdersByCustomer(customer);
        model.addAttribute("order", order);
        return "/site/fragments/order-list";
    }
    @GetMapping("checkout")
    public String add(Model model) {
        Order order = new Order();
        model.addAttribute("order", order);
        return "/site/fragments/order";
    }

    @PostMapping("purchase")
    public String order(@RequestParam("address") String address,
                        @RequestParam("phone") String phone,
                        HttpSession session,
                        Model model) {

        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            return "redirect:/slogin";
        }

        List<CartItem> cartItems = shoppingCartService.findAll(customer);
        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("message", "Giỏ hàng trống. Vui lòng thêm sản phẩm vào giỏ hàng trước khi đặt hàng.");
            return "/site/fragments/cart";
        }

        // Tính tổng số tiền
        double totalPrice = shoppingCartService.calculateTotalPrice(cartItems);

        // Tạo đơn hàng và lưu vào cơ sở dữ liệu (hoặc thực hiện các thao tác khác liên quan đến đặt hàng)
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setAmount(totalPrice);
        order.setStatus("Pending"); // Trạng thái mặc định của đơn hàng
        order.setAddress(address);
        order.setPhone(phone);
        order.setCustomer(customer);

        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setUnitPrice(cartItem.getProduct().getUnitPrice());
            orderService.addOrderDetail(orderDetail);
        }

        // Lưu đơn hàng vào cơ sở dữ liệu (hoặc thực hiện các thao tác khác liên quan đến đặt hàng)
        orderService.saveOrder(order);

        // Xóa giỏ hàng sau khi đã đặt hàng thành công
        shoppingCartService.clearCart(customer);

        model.addAttribute("message", "Đặt hàng thành công! Cảm ơn bạn đã mua hàng.");
        return "forward:/site/order";
    }
}
