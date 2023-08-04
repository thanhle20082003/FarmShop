package com.web.farm.FarmShop.service;

import com.web.farm.FarmShop.domain.Customer;
import com.web.farm.FarmShop.domain.Order;
import com.web.farm.FarmShop.domain.OrderDetail;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OrderService {
    @Transactional
    void saveOrder(Order order);

    void addOrderDetail(OrderDetail orderDetail);

    List<Order> findOrdersByCustomer(Customer customer);
}
