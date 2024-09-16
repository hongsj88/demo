package com.example.demo;

import org.springframework.batch.core.ItemProcessListener;

public class CUstomItemProcessListener implements ItemProcessListener<Customer,Customer> {
    @Override
    public void beforeProcess(Customer customer) {

    }

    @Override
    public void afterProcess(Customer item, Customer customer2) {
        System.out.println("Thread : "+Thread.currentThread().getName()+", process item :" + item.getId());
    }

    @Override
    public void onProcessError(Customer customer, Exception e) {

    }
}
