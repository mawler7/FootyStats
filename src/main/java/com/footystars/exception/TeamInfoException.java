package com.footystars.exception;

/**
 * Exception thrown when an error occurs while retrieving team information.
 */
public class TeamInfoException extends RuntimeException {

    /**
     * Constructs a new TeamInfoException with a default message and cause.
     *
     * @param cause the cause of the exception
     */
    public TeamInfoException(Exception cause) {
        super("Failed to fetch team information", cause);
    }
}
