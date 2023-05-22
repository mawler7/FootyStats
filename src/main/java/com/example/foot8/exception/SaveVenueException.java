package com.example.foot8.exception;

public class SaveVenueException extends RuntimeException {
    public SaveVenueException(Throwable cause) {
        super("Cannot save venue!", cause);
    }
}