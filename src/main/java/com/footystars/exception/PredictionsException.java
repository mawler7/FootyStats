package com.footystars.exception;

/**
 * Exception thrown when an error occurs while processing predictions.
 */
public class PredictionsException extends RuntimeException {

    /**
     * Constructs a new PredictionsException with the specified message.
     *
     * @param message the detail message
     */
    public PredictionsException(String message) {
        super(message);
    }

}
