package com.web.farm.FarmShop.service;

import com.web.farm.FarmShop.domain.Customer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface CustomerService {
    //login
    Customer login(String email, String password);

    Customer findByName(String name);

    <S extends Customer> S save(S entity);

    Optional<Customer> findByEmail(String email);

    <S extends Customer> Optional<S> findOne(Example<S> example);

    List<Customer> findAll();

    Page<Customer> findAll(Pageable pageable);

    List<Customer> findAll(Sort sort);

    List<Customer> findAllById(Iterable<Long> ids);

    Optional<Customer> findById(Long id);

    <S extends Customer> List<S> saveAll(Iterable<S> entities);

    void flush();

    boolean existsById(Long id);

    <S extends Customer> S saveAndFlush(S entity);

    <S extends Customer> List<S> saveAllAndFlush(Iterable<S> entities);

    <S extends Customer> Page<S> findAll(Example<S> example, Pageable pageable);

    void deleteInBatch(Iterable<Customer> entities);

    <S extends Customer> long count(Example<S> example);

    void deleteAllInBatch(Iterable<Customer> entities);

    long count();

    <S extends Customer> boolean exists(Example<S> example);

    void deleteById(Long id);

    void deleteAllByIdInBatch(Iterable<Long> ids);

    void delete(Customer entity);

    <S extends Customer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    void deleteAllById(Iterable<? extends Long> ids);

    void deleteAllInBatch();

    Customer getOne(Long id);

    void deleteAll(Iterable<? extends Customer> entities);

    void deleteAll();

    Customer getById(Long id);

    Customer getReferenceById(Long id);

    <S extends Customer> List<S> findAll(Example<S> example);

    <S extends Customer> List<S> findAll(Example<S> example, Sort sort);
}
