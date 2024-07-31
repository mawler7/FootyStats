package com.footystars.foot8.exception;

import java.io.IOException;

public class DataFetcherException extends RuntimeException {
    public DataFetcherException(String message) {
        super(message);
    }

    public DataFetcherException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataFetcherException(IOException cause) {
        super(cause);
    }

}
