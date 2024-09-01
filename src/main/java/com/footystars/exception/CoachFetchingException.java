package com.footystars.exception;

public class CoachFetchingException extends RuntimeException {
    public CoachFetchingException(String message) {
        super(message);
    }

    public CoachFetchingException(String message, Throwable cause) {
        super(message, cause);
    }
}