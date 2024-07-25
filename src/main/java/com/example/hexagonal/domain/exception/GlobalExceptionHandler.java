package com.example.hexagonal.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String BODY_MESSAGE_KEY = "message";
    private static final String BODY_TIMESTAMP_KEY = "timestamp";
    private static final String BODY_STATUS_KEY = "status";
    private static final String BODY_DETAILS_KEY = "details";

    // Handling error when no price is found
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<Object> handlePriceNotFoundException(PriceNotFoundException ex) {

        LOG.error("Price not found exception: ", ex);

        Map<String, Object> body = new HashMap<>();
        body.put(BODY_MESSAGE_KEY, ex.getMessage());
        body.put(BODY_TIMESTAMP_KEY, System.currentTimeMillis());
        body.put(BODY_STATUS_KEY, HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Handling error when api param is not correct
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        LOG.error("Method argument type mismatch: ", ex);

        Map<String, Object> body = new HashMap<>();
        body.put(BODY_MESSAGE_KEY, "Invalid parameter type: " + ex.getName());
        body.put(BODY_TIMESTAMP_KEY, System.currentTimeMillis());
        body.put(BODY_STATUS_KEY, HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Other exception handlers...
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {

        LOG.error("An unexpected error occurred: ", ex);

        Map<String, Object> body = new HashMap<>();
        body.put(BODY_MESSAGE_KEY, "An unexpected error occurred");
        body.put(BODY_DETAILS_KEY, ex.getMessage());
        body.put(BODY_TIMESTAMP_KEY, System.currentTimeMillis());
        body.put(BODY_STATUS_KEY, HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
