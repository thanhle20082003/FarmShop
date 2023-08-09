package com.web.farm.FarmShop.service;

import com.web.farm.FarmShop.domain.Account;
import com.web.farm.FarmShop.domain.Order;
import com.web.farm.FarmShop.domain.OrderDetail;
import javax.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface OrderService {
    @Transactional
    void saveOrder(Order order);

    void addOrderDetail(OrderDetail orderDetail);

    List<Order> findOrdersByAccount(UserDetails userDetails);
}
