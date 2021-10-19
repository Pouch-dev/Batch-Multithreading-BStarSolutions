package com.example.Springbatchmultithreading;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(classes = SpringBatchMultithreadingApplication.class)
@ExtendWith(SpringExtension.class)
public class SpringBatchMultithreadingApplicationTests {

	@Autowired
	public MockMvc mockMvc;

}
