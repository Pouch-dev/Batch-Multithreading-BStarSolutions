package com.example.Springbatchmultithreading.writers;

import com.example.Springbatchmultithreading.dto.demoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
@Slf4j
public class ItemWriterDB implements ItemWriter<demoDTO> {
    @Override
    public void write (List<? extends demoDTO> list) throws Exception{


    }
}
