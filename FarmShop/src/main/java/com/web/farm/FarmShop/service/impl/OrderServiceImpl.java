package com.web.farm.FarmShop.service.impl;

import com.web.farm.FarmShop.domain.Account;
import com.web.farm.FarmShop.domain.Order;
import com.web.farm.FarmShop.domain.OrderDetail;
import com.web.farm.FarmShop.respository.OrderDetailRepository;
import com.web.farm.FarmShop.respository.OrderRepository;
import com.web.farm.FarmShop.service.AccountService;
import com.web.farm.FarmShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private AccountService accountService;

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
    public List<Order> findOrdersByAccount(UserDetails userDetails) {
        String username = userDetails.getUsername();

        // Sử dụng tên người dùng để lấy thông tin Account
        Optional<Account> account = accountService.findById(username);

        return orderRepository.findByAccount(account.get());
    }
}
