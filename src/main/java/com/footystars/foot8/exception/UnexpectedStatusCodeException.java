package com.footystars.foot8.exception;

public class UnexpectedStatusCodeException extends RuntimeException {

    public UnexpectedStatusCodeException(String message) {
        super(message);
    }

    public UnexpectedStatusCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}