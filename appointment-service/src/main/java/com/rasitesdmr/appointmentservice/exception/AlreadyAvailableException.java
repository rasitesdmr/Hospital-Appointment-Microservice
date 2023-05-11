package com.rasitesdmr.appointmentservice.exception;

public class AlreadyAvailableException extends RuntimeException {

    public AlreadyAvailableException(String message) {
        super(message);
    }
}
