package com.shahintraining.customer.exception;

/**
 * author - sh.khalajestani
 * created on - 3/9/2022
 */

public class VerificationTokenExpiredException extends RuntimeException{
    public VerificationTokenExpiredException(String message) {
        super(message);
    }
}
