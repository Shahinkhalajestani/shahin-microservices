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
                                                                                  WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(ex.getMessage(), request.getDescription(true), new Date()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ExceptionResponse> customerExistsExceptionHandler(Exception ex,
                                                                                  WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(ex.getMessage(), request.getDescription(false), new Date()));
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ExceptionResponse> customerNotFoundExceptionHandler(CustomerNotFoundException ex,
                                                                                    WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage(), request.getDescription(false), new Date()));
    }

    @ExceptionHandler(VerificationTokenNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ExceptionResponse> tokenNotFoundExceptionHandler(
            VerificationTokenNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage(), request.getDescription(false), new Date()));
    }

    @ExceptionHandler(CustomerIsFraudsterException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ExceptionResponse> customerIsFraudsterExceptionHandler(
            CustomerIsFraudsterException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ExceptionResponse(ex.getMessage(), request.getDescription(false), new Date()));
    }

    @ExceptionHandler(VerificationTokenExpiredException.class)
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public final ResponseEntity<ExceptionResponse> verificationTokenExpiredExceptionHandler(
            VerificationTokenExpiredException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
                .body(new ExceptionResponse(ex.getMessage(), request.getDescription(false), new Date()));
    }

    @ExceptionHandler(InvalidTokenPrefixException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ExceptionResponse> invalidTokenPrefixExceptionHandler(
            InvalidTokenPrefixException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(ex.getMessage(), request.getDescription(false), new Date()));
    }

}
