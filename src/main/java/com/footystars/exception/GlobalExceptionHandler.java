package com.footystars.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler for handling application-wide exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions related to data fetching.
     *
     * @param ex      the exception
     * @param request the web request
     * @return a response entity with a BAD_REQUEST status
     */
    @ExceptionHandler(DataFetcherException.class)
    public ResponseEntity<String> handleDataFetcherException(@NotNull DataFetcherException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles request execution failures.
     *
     * @param ex      the exception
     * @param request the web request
     * @return a response entity with a SERVICE_UNAVAILABLE status
     */
    @ExceptionHandler(RequestExecutorException.class)
    public ResponseEntity<String> handleRequestExecutorException(@NotNull RequestExecutorException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }

    /**
     * Handles invalid input parameters.
     *
     * @param ex      the exception
     * @param request the web request
     * @return a response entity with a BAD_REQUEST status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(@NotNull IllegalArgumentException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + ex.getMessage());
    }

    /**
     * Handles unexpected system errors.
     *
     * @param ex      the exception
     * @param request the web request
     * @return a response entity with an INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(@NotNull Exception ex, @NotNull WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred for request: " + request.getContextPath() + " with message: " + ex.getMessage());
    }
}
