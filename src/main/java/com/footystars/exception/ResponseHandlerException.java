package com.footystars.exception;

/**
 * Exception thrown when an error occurs while handling an API response.
 */
public class ResponseHandlerException extends RuntimeException {

    /**
     * Constructs a new ResponseHandlerException with the specified message.
     *
     * @param message the detail message
     */
    public ResponseHandlerException(String message) {
        super(message);
    }
}
