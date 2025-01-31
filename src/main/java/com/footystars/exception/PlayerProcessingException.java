package com.footystars.exception;

/**
 * Exception thrown when an error occurs while processing player data.
 */
public class PlayerProcessingException extends RuntimeException {

    /**
     * Constructs a new PlayerProcessingException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public PlayerProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
