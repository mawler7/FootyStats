package com.footystars.exception;

/**
 * Exception thrown when a server error occurs.
 */
public class ServerErrorException extends RuntimeException {

    /**
     * Constructs a new ServerErrorException with the specified message.
     *
     * @param message the detail message
     */
    public ServerErrorException(String message) {
        super(message);
    }
}
