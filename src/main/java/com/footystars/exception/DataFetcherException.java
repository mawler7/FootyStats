package com.footystars.exception;

/**
 * Exception thrown when an error occurs while fetching data from an external API.
 */
public class DataFetcherException extends RuntimeException {

    /**
     * Constructs a new DataFetcherException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public DataFetcherException(String message, Throwable cause) {
        super(message, cause);
    }
}
