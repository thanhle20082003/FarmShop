package com.web.farm.FarmShop.service.impl;

import com.web.farm.FarmShop.domain.Customer;
import com.web.farm.FarmShop.domain.Order;
import com.web.farm.FarmShop.domain.OrderDetail;
import com.web.farm.FarmShop.respository.CartItemRepository;
import com.web.farm.FarmShop.respository.CustomerRepository;
import com.web.farm.FarmShop.respository.OrderDetailRepository;
import com.web.farm.FarmShop.respository.OrderRepository;
import com.web.farm.FarmShop.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Override
    @Transactional
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<Order> findOrdersByCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }
}
