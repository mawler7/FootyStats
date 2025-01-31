package com.footystars.exception;

/**
 * Exception thrown when a client error occurs during API communication.
 */
public class ClientErrorException extends RuntimeException {

    /**
     * Constructs a new ClientErrorException with the specified message.
     *
     * @param message the detail message
     */
    public ClientErrorException(String message) {
        super(message);
    }
}
