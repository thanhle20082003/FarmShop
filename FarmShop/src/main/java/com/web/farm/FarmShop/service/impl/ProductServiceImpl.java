package com.web.farm.FarmShop.service.impl;

import com.web.farm.FarmShop.domain.Category;
import com.web.farm.FarmShop.domain.Product;
import com.web.farm.FarmShop.model.ProductDTO;
import com.web.farm.FarmShop.respository.ProductRepository;
import com.web.farm.FarmShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public <S extends Product> S save(S entity) {
        return productRepository.save(entity);
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public Page<Product> findAllByNameLike(String name, Pageable pageable) {
        return productRepository.findAllByNameLike(name, pageable);
    }

    @Override
    public List<Product> findProductByStatus(Pageable pageable) {
        short status = 3;
        return productRepository.findProductByStatus(status , pageable);
    }

    @Override
    public Page<Product> findProductByCategoryId(Long id, Pageable pageable) {
        return productRepository.findProductByCategoryId(id, pageable);
    }

    @Override
    public <S extends Product> Optional<S> findOne(Example<S> example) {
        return productRepository.findOne(example);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> findAll(Sort sort) {
        return productRepository.findAll(sort);
    }

    @Override
    public List<Product> findAllById(Iterable<Long> ids) {
        return productRepository.findAllById(ids);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public <S extends Product> List<S> saveAll(Iterable<S> entities) {
        return productRepository.saveAll(entities);
    }

    @Override
    public void flush() {
        productRepository.flush();
    }

    @Override
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public <S extends Product> S saveAndFlush(S entity) {
        return productRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Product> List<S> saveAllAndFlush(Iterable<S> entities) {
        return productRepository.saveAllAndFlush(entities);
    }

    @Override
    public <S extends Product> Page<S> findAll(Example<S> example, Pageable pageable) {
        return productRepository.findAll(example, pageable);
    }

    @Override
    public void deleteInBatch(Iterable<Product> entities) {
        productRepository.deleteInBatch(entities);
    }

    @Override
    public <S extends Product> long count(Example<S> example) {
        return productRepository.count(example);
    }

    @Override
    public void deleteAllInBatch(Iterable<Product> entities) {
        productRepository.deleteAllInBatch(entities);
    }

    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public <S extends Product> boolean exists(Example<S> example) {
        return productRepository.exists(example);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        productRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void delete(Product entity) {
        productRepository.delete(entity);
    }

    @Override
    public <S extends Product, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return productRepository.findBy(example, queryFunction);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        productRepository.deleteAllById(ids);
    }

    @Override
    public void deleteAllInBatch() {
        productRepository.deleteAllInBatch();
    }

    @Override
    public Product getOne(Long id) {
        return productRepository.getOne(id);
    }

    @Override
    public void deleteAll(Iterable<? extends Product> entities) {
        productRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    @Override
    public Product getReferenceById(Long id) {
        return productRepository.getReferenceById(id);
    }

    @Override
    public <S extends Product> List<S> findAll(Example<S> example) {
        return productRepository.findAll(example);
    }

    @Override
    public <S extends Product> List<S> findAll(Example<S> example, Sort sort) {
        return productRepository.findAll(example, sort);
    }

    @Override
    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }//search theo id

}
