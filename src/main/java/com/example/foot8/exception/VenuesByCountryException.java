package com.example.foot8.exception;

public class VenuesByCountryException extends RuntimeException {
    public VenuesByCountryException(String message, Throwable cause) {
        super(message, cause);
    }
}