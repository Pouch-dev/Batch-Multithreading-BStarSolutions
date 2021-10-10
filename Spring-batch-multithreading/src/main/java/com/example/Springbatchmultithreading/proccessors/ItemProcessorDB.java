package com.example.Springbatchmultithreading.proccessors;

import com.example.Springbatchmultithreading.dto.demoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class ItemProcessorDB implements ItemProcessor<String, demoDTO> {


    @Override
    public demoDTO process (String item) throws Exception{
        log.info("------- PROCESSING O ITEM : " + item);
        Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 21000));
        log.info("------- ITEM : " + item + "HAS BEEN PROCESSED!");
        return new demoDTO("CPF: " + item + " PROCESSED SUCCESSFULLY!");
    }


}
