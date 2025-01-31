package com.footystars.exception;

/**
 * Exception thrown when an error occurs while fetching coach data.
 */
public class CoachFetchingException extends RuntimeException {

    /**
     * Constructs a new CoachFetchingException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public CoachFetchingException(String message, Throwable cause) {
        super(message, cause);
    }
}
