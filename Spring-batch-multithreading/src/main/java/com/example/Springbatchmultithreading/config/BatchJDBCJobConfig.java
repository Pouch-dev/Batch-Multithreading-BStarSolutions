package com.example.Springbatchmultithreading.config;

import com.example.Springbatchmultithreading.model.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchJDBCJobConfig extends BatchJobConfiguration{

    @Autowired
    private JobLauncher jobLauncher;

    public void schedule(@Qualifier("importDBFromCSV") Job importDBFromCSV) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException{
        jobLauncher.run(importDBFromCSV, new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
    }

    @Bean
    protected Job importDBFromCSV(@Qualifier("importData")Step importData){
        return this.jobs.get("importDBFromCSV")
                .preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(importData)
                .build();
    }

//    @Bean
//    protected Step importData(
//            @Qualifier("csvReader")FlatFileItemReader<Customer> csvReader,
//
//            )
}
