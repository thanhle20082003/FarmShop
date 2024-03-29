package com.web.farm.FarmShop.respository;

import com.web.farm.FarmShop.domain.Account;
import com.web.farm.FarmShop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByAccount(Account account);
}
