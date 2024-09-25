package com.example.demo;

public class CustomSkipException extends Exception {
    public CustomSkipException() {
    }

    public CustomSkipException(String message) {
        super(message);
    }
}
