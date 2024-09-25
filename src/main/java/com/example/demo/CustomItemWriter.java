package com.example.demo;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class CustomItemWriter implements ItemWriter<String> {

    int count = 0;

    @Override
    public void write(List<? extends String> list) throws Exception {
        for (String item : list) {
            if (count < 2) {
                if (count % 2 == 0) {
                    count++;
                } else if (count % 2 == 1) {
                    count++;

                    throw new CumtomRetryException("failed");
                }
            }
            System.out.println("write : " + item);
        }
    }
}