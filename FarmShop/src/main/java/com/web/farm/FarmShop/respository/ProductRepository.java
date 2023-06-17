package com.web.farm.FarmShop.respository;

import com.web.farm.FarmShop.domain.Product;
import com.web.farm.FarmShop.model.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);

    Page<Product> findAllByNameLike(String name, Pageable pageable);

    List<Product> findProductByStatus(short status, Pageable pageable);

    Page<Product> findProductByCategoryId(Long id, Pageable pageable);


}
