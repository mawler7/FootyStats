package com.footystars.exception;

/**
 * Exception thrown when an unexpected HTTP status code is received from an API response.
 */
public class UnexpectedStatusCodeException extends RuntimeException {

    /**
     * Constructs a new UnexpectedStatusCodeException with the specified message.
     *
     * @param message the detail message explaining the unexpected status code
     */
    public UnexpectedStatusCodeException(String message) {
        super(message);
    }
}
