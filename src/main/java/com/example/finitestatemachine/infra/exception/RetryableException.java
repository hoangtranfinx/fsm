package com.example.finitestatemachine.infra.exception;

public class RetryableException extends RuntimeException {

    public RetryableException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
