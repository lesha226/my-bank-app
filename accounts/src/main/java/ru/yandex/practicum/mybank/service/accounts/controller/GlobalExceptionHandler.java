package ru.yandex.practicum.mybank.service.accounts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        System.out.println("GlobalExceptionHandler.handleResponseStatusException: e=" + e);

        ErrorResponse response = new ErrorResponse(e.getMessage(),LocalDateTime.now());

        return ResponseEntity.status(e.getStatusCode()).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        System.out.println("GlobalExceptionHandler.handleRuntimeException: e=" + e);

        ErrorResponse response = new ErrorResponse(e.getMessage(),LocalDateTime.now());

        return ResponseEntity.badRequest().body(response);
    }
    */
}
