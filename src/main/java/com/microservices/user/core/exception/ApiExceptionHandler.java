package com.microservices.user.core.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler{

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder().message(exception.getMessage())
                        .details(exception.getCause().getMessage())
                        .build());
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message((exception.getMessage())).build());
    }
}
