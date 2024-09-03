package com.example.demo;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor1 implements ItemProcessor<ProcessorInfo,ProcessorInfo> {
    @Override
    public ProcessorInfo process(ProcessorInfo item) throws Exception {
        System.out.println("CustomItemProcessor1");
        return item;
    }
}
