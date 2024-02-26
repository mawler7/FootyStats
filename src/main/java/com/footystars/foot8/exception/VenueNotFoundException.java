package com.footystars.foot8.exception;

public class VenueNotFoundException extends RuntimeException {
    public VenueNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}