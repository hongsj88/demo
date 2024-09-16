package com.example.demo;

import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

public class CustomItemWriteListener implements ItemWriteListener<Customer> {

    @Override
    public void beforeWrite(List<? extends Customer> list) {

    }

    @Override
    public void afterWrite(List<? extends Customer> items) {
        System.out.println("Thread : "+Thread.currentThread().getName()+", writer item :" + items.size());
    }

    @Override
    public void onWriteError(Exception e, List<? extends Customer> list) {

    }
}
