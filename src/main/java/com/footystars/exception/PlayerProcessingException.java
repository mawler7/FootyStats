package com.footystars.exception;

public class PlayerProcessingException extends RuntimeException {
    public PlayerProcessingException(String message) {
        super(message);
    }

    public PlayerProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}