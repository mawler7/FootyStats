package com.footystars.exception;

public class RequestExecutorException extends RuntimeException {
    public RequestExecutorException(Exception e, String message) {
        super(message, e);
    }

    public RequestExecutorException(String message) {
        super(message);
    }

}
