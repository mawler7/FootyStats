package com.footystars.foot8.exception;

public class SaveVenueException extends RuntimeException {
    public SaveVenueException(String s, Throwable cause) {
        super("Cannot save venue!", cause);
    }
}