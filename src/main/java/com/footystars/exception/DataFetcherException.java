package com.footystars.exception;

import java.io.IOException;

public class DataFetcherException extends RuntimeException {

    public DataFetcherException(String message, Throwable cause) {
        super(message, cause);
    }

}
