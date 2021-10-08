package com.example.Springbatchmultithreading.repository.jdbc;

import com.example.Springbatchmultithreading.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JDBCTemplateCustomerRepository implements JDBCCustomerRepository {

    @Autowired private JdbcTemplate jdbcTemplate;

    /**
     *
     * @return
     */
    @Override
    public Customer save(Customer user) {
        Assert.notNull(jdbcTemplate.update("insert into customer (name, departments, salary,time) values(?,?,?,?)",
                user.getName(),user.getDepartments(), user.getSalary(), user.getTime()), "Entity must not be null.");
        return user;
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public Customer update(Customer user) {
        Assert.notNull(jdbcTemplate.update("update customer set name = ?, departments = ?,  salary = ?, time=?  where id = ?",
                user.getName(),user.getDepartments(), user.getSalary(),user.getTime(),user.getId()), "Entity must not be null.");
        return user;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteById(Integer id) {
        Assert.notNull(jdbcTemplate.update("delete customer where id = ?", id), "Entity must not be null.");
        return id;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(
                "select * from customer",
                (result, row) -> new Customer(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("departments"),
                        result.getString("salary"),
                        result.getDate("time")
                ));
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Customer> findById(Integer id) {
        return jdbcTemplate.queryForObject(
                "select * from customer where id = ?",
                new Object[]{id},
                (result, row) -> Optional.of(new Customer(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("departments"),
                        result.getString("salary"),
                        result.getDate("time")
                ))
        );
    }
}
