package com.footystars.exception;

/**
 * Exception thrown when an error occurs while processing fixture-related operations.
 */
public class FixtureException extends RuntimeException {

    /**
     * Constructs a new FixtureException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public FixtureException(String message, Exception cause) {
        super(message, cause);
    }
}
