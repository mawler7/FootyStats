package com.footystars.exception;

/**
 * Exception thrown when an error occurs while fetching player sidelined data.
 */
public class PlayerSidelinedException extends RuntimeException {

    /**
     * Constructs a new PlayerSidelinedException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public PlayerSidelinedException(String message, Exception cause) {
        super(message, cause);
    }
}
