package com.example.Springbatchmultithreading.proccessors;

import com.example.Springbatchmultithreading.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ItemProcessorCustomer implements ItemProcessor<Customer,Customer> {

    private static final Map<String, String> DEPT_NAME = new HashMap<>();

    public ItemProcessorCustomer(){
        DEPT_NAME.put("001","Technology");
        DEPT_NAME.put("002","Operations");
        DEPT_NAME.put("003","Accounts");
    }

    @Override
    public Customer process (Customer customer) throws Exception{
        String deptCode = customer.getDepartments();
        String dept = DEPT_NAME.get(deptCode);
        customer.setDepartments(dept);
        customer.setTime(new Date());
        Customer customers = new Customer();
        BeanUtils.copyProperties(customers, customers);
        log.info(Thread.currentThread().getName() + " ---> " + customers.getName());
        return customer;
    }
}
