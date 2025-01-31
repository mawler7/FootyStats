package com.footystars.exception;

/**
 * Exception thrown when an error occurs while fetching league data.
 */
public class FetchLeaguesException extends RuntimeException {

    /**
     * Constructs a new FetchLeaguesException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public FetchLeaguesException(String message, Exception cause) {
        super(message, cause);
    }
}
