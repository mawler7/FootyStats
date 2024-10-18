package com.footystars.exception;

public class PredictionsException extends RuntimeException {

    public PredictionsException(String message) {
        super(message);
    }

    public PredictionsException(String message, Throwable cause) {
        super(message, cause);
    }

}
