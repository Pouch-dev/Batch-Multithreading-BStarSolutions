package com.example.Springbatchmultithreading.repository.jdbc;

import com.example.Springbatchmultithreading.model.Customer;

import java.util.List;
import java.util.Optional;

public interface JDBCCustomerRepository {

    Customer save(Customer user);

    Customer update(Customer customer);

    Integer deleteById(Integer id);

    List<Customer> findAll();

    Optional<Customer> findById(Integer id);

}
