package com.footystars.exception;

/**
 * Exception thrown when an error occurs while creating a request.
 */
public class RequestCreatorException extends RuntimeException {

    /**
     * Constructs a new RequestCreatorException with the specified message.
     *
     * @param message the detail message
     */
    public RequestCreatorException(String message) {
        super(message);
    }
}
