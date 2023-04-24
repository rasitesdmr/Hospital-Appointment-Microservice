package com.rasitesdmr.apigateway.exception;

public class AlreadyAvailableException extends RuntimeException {

    public AlreadyAvailableException(String message) {
        super(message);
    }
}
