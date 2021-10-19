package com.example.Springbatchmultithreading;

import com.example.Springbatchmultithreading.config.BatchConfiguration;
import com.example.Springbatchmultithreading.config.BatchJDBCJobConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class SpringBatchMultithreadingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchMultithreadingApplication.class, args);
	}

}
