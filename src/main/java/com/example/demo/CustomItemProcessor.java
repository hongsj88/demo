package com.example.demo;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Integer,String> {
    int count = 0;
    @Override
    public String process(Integer integer) throws Exception {

        if (integer < 2) {
            if (count % 2 == 0) {
                count++;
            } else if (count % 2 == 1) {
                count++;

                throw  new CumtomRetryException("failed");
            }
        }
        return String.valueOf(integer);
    }
}
