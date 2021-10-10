package com.example.Springbatchmultithreading.writers;

import com.example.Springbatchmultithreading.model.Customer;
import com.example.Springbatchmultithreading.repository.CustomerRepository;
import com.example.Springbatchmultithreading.repository.jdbc.JDBCCustomerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ItemWriterCustomer implements ItemWriter<Customer> {

    @Autowired
    JDBCCustomerRepository customerRepository;

    @Override
    public void write (List<? extends Customer> list) throws Exception{
        for (Customer record : list) {
            customerRepository.save(record);
            System.out.println("Data Saved for user: " + record.toString());
        }
    }
}
