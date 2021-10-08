package com.example.Springbatchmultithreading.readers;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

public class ItemReaderDB implements ItemReader<String>, StepExecutionListener {

    private List<String> cpfs;

    /**
     * next CPF to be returned;
     */
    private int next;

    /**
     * Initializing the list
     */
    private void init(){
        cpfs = Arrays.asList("1","2","3","4","5","6","7","8","9","10");
        next = 0;

    }


    @Override
    public void beforeStep (StepExecution stepExecution){
        init();
    }

    @Override
    public ExitStatus afterStep (StepExecution stepExecution){
        return null;
    }

    @Override
    public String read ( ) throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException{

        if (CollectionUtils.isEmpty(cpfs) || next >= cpfs.size()){
            return null;
        }

        return cpfs.get(next++);
    }
}
