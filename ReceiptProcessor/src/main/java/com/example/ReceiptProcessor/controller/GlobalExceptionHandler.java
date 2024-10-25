package com.example.ReceiptProcessor.controller;


import com.example.ReceiptProcessor.exceptions.InvalidPriceFormatException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    // Handle  IllegalStateException
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    // Handle custom InvalidPriceFormatException
    @ExceptionHandler(InvalidPriceFormatException.class)
    public ResponseEntity<String> handleInvalidPriceFormatException(InvalidPriceFormatException ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid price format: " + ex.getMessage());
    }

    // Handle invalid JSON format
    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<String> handleJsonParseException(JsonParseException ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid JSON format: " + ex.getMessage());
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid receipt data: " + errorMessage);
    }

    // Handle invalid data format
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleInvalidFormatException(InvalidFormatException ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid data format: " + ex.getMessage());
    }

    // Handle no element found errors
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not Found: " + ex.getMessage());
    }

    // Handle any other exceptions (generic)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + ex.getMessage());
    }

    // Helper method to create a structured error response
    private ResponseEntity<String> createErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body("{\"error\":\"" + message + "\", \"status\":" + status.value() + ", \"timestamp\":" + System.currentTimeMillis() + "}");
    }
}
