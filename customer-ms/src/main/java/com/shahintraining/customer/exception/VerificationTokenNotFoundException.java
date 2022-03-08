package com.shahintraining.customer.exception;

/**
 * author - sh.khalajestani
 * created on - 3/8/2022
 */

public class VerificationTokenNotFoundException extends RuntimeException{
    public VerificationTokenNotFoundException(String message) {
        super(message);
    }
}
