package com.footystars.exception;

/**
 * Exception thrown when an error occurs while executing a request.
 */
public class RequestExecutorException extends RuntimeException {

    /**
     * Constructs a new RequestExecutorException with the specified cause and message.
     *
     * @param cause   the cause of the exception
     * @param message the detail message
     */
    public RequestExecutorException(Exception cause, String message) {
        super(message, cause);
    }

    /**
     * Constructs a new RequestExecutorException with the specified message.
     *
     * @param message the detail message
     */
    public RequestExecutorException(String message) {
        super(message);
    }
}
