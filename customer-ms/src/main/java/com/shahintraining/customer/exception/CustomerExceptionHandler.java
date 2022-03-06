package com.shahintraining.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ExceptionResponse> customerExistsExceptionHandler(CustomerAlreadyExistsException ex,
                                                                                 WebRequest request){
        ExceptionResponse response =
                new ExceptionResponse(ex.getMessage(), request.getDescription(true), new Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ExceptionResponse> customerExistsExceptionHandler(Exception ex,
                                                                                  WebRequest request){
        ExceptionResponse response =
                new ExceptionResponse(ex.getMessage(), request.getDescription(false), new Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
