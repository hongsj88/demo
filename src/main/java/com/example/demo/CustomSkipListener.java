package com.example.demo;

import org.springframework.batch.core.SkipListener;

public class CustomSkipListener implements SkipListener<Integer,String> {

    @Override
    public void onSkipInRead(Throwable throwable) {
        System.out.println(">> onSkipInRead" + throwable.getMessage());

    }

    @Override
    public void onSkipInWrite(String s, Throwable throwable) {
        System.out.println(">> onSkipInWrite" + s);
        System.out.println(">> onSkipInWrite" + throwable.getMessage());

    }

    @Override
    public void onSkipInProcess(Integer integer, Throwable throwable) {

        System.out.println(">> onSkipInProcess" + integer);
        System.out.println(">> onSkipInProcess" + throwable.getMessage());

    }
}
