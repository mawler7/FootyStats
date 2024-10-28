package com.footystars.exception;

public class UnexpectedStatusCodeException extends RuntimeException {

    public UnexpectedStatusCodeException(String message) {
        super(message);
    }

}