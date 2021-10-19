package com.example.Springbatchmultithreading.controller;

import com.example.Springbatchmultithreading.SpringBatchMultithreadingApplicationTests;
import com.example.Springbatchmultithreading.common.Utils;
import com.example.Springbatchmultithreading.model.Customer;
import com.example.Springbatchmultithreading.proccessors.ItemProcessorCustomer;
import com.example.Springbatchmultithreading.writers.ItemWriterCustomer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import javax.xml.crypto.Data;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class JobControllerTest extends SpringBatchMultithreadingApplicationTests {

    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    ItemProcessorCustomer processorCustomer;
    @Autowired
    @Qualifier("importDBFromCSV")
    Job importDBFromCSV;

    @Test
    void load ( ){
    }

//    @Test
//    public void testLoad() throws Exception{
//
//        Customer customers = new Customer(1,"User","001","1200000",new Date());
//        Mockito.when(processorCustomer.process(any(Customer.class))).thenReturn(customers);
//
//        String URL = "/job/load";
//        String requestJson = Utils.getJsonStringFromFile("testapidata/users.json");
//        MvcResult mvcResult = mockMvc.perform(get(URL)
//                .content(requestJson)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//    }
}