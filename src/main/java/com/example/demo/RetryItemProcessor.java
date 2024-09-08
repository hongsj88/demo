package com.example.demo;

import org.springframework.batch.item.ItemProcessor;

public class RetryItemProcessor implements ItemProcessor<String, String> {

    private int cnt = 0;

    @Override
    public String process(String item) throws Exception {
        cnt++;
//        System.out.println("processor cnt ####################:: " + cnt);
        throw new RetryableException();
//        return item;
    }
}
