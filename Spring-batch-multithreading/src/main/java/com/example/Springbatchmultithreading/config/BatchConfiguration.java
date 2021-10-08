package com.example.Springbatchmultithreading.config;

import com.example.Springbatchmultithreading.dto.demoDTO;
import com.example.Springbatchmultithreading.proccessors.ItemProcessorDB;
import com.example.Springbatchmultithreading.readers.ItemReaderDB;
import com.example.Springbatchmultithreading.writers.ItemWriterDB;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.Future;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends BatchJobConfiguration{


    @Autowired private JobLauncher launcher;

    @Bean public ItemReader<String> reader(){ return new ItemReaderDB();}

    @Bean public ItemProcessor<String, demoDTO> processor(){return new ItemProcessorDB();}

    @Bean public ItemWriter<demoDTO> writer(){ return new ItemWriterDB();}


    @Bean
    public ItemProcessor<String, Future<demoDTO>> asyncItemProcessor(
            @Qualifier("processor") ItemProcessor<String, demoDTO> processor,
            @Qualifier("asyncExecutor")TaskExecutor getAsyncExecutor){
        AsyncItemProcessor<String, demoDTO> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(processor);
        asyncItemProcessor.setTaskExecutor(getAsyncExecutor);
        return asyncItemProcessor;
    }

    @Bean
    public  ItemWriter<Future<demoDTO>> asyncItemWriter(@Qualifier("writer") ItemWriter<demoDTO> writer){
        AsyncItemWriter<demoDTO> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(writer);
        return asyncItemWriter;
    }


    @Bean
    protected Step stepTest(@Qualifier("asyncItemProcessor") ItemProcessor<String, Future<demoDTO>> asyncItemProcessor,
                            @Qualifier("asyncItemWriter") ItemWriter<Future<demoDTO>>asyncItemWriter){
        return this.steps.get("stepTest")
                .<String, Future<demoDTO>>chunk(Integer.parseInt(core))
                .reader(reader())
                .processor(asyncItemProcessor)
                .writer(asyncItemWriter)
                .build();
    }

    @Bean
    protected Job jobTest(@Qualifier("stepTest") Step stepTest){
        return this.jobs.get("jobTest")
                .preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(stepTest)
                .build();
    }
}
