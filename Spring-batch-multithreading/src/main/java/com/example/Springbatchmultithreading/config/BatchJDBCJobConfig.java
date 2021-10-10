package com.example.Springbatchmultithreading.config;

import com.example.Springbatchmultithreading.model.Customer;
import com.example.Springbatchmultithreading.proccessors.ItemProcessorCustomer;
import com.example.Springbatchmultithreading.writers.ItemWriterCustomer;
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
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.Future;

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

    @Bean
    protected Step importData(
            @Qualifier("csvReader") FlatFileItemReader<Customer> csvReader,
            @Qualifier("processCustomerDataAsync") ItemProcessor<Customer, Future<Customer>> processCustomerDataAsync,
            @Qualifier("writeCustomerDataAsync") ItemWriter<Future<Customer>> writeCustomerDataAsync) {
        return this.steps.get("importData")
                .<Customer, Future<Customer>>chunk(Integer.parseInt(chunk))
                .reader(csvReader)
                .processor(processCustomerDataAsync)
                .writer(writeCustomerDataAsync)
                .build();
    }


    @Bean
    public ItemWriter<Future<Customer>> writeCustomerDataAsync(
            @Qualifier("writeCustomerData") ItemWriter<Customer> writeCustomerData) {
        AsyncItemWriter<Customer> wr = new AsyncItemWriter<>();
        wr.setDelegate(writeCustomerData);
        return wr;
    }

    @Bean
    public ItemWriter<Customer> writeCustomerData() {
        return new ItemWriterCustomer();
    }

    @Bean
    public ItemProcessor<Customer, Future<Customer>> processCustomerDataAsync(
            @Qualifier("processCustomerData") ItemProcessor<Customer, Customer> processCustomerData,
            @Qualifier("asyncExecutor") TaskExecutor getAsyncExecutor) {
        AsyncItemProcessor<Customer, Customer> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(processCustomerData);
        asyncItemProcessor.setTaskExecutor(getAsyncExecutor);
        return asyncItemProcessor;
    }

    @Bean
    public ItemProcessor<Customer, Customer> processCustomerData() {
        return new ItemProcessorCustomer();
    }

    @Bean
    public FlatFileItemReader<Customer> csvReader(){

        FlatFileItemReader<Customer> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource(inputFile));
        flatFileItemReader.setName("csvReader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id","name","departments","salary"});

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
