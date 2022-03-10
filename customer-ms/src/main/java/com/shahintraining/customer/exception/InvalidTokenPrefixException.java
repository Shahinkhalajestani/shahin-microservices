package com.shahintraining.customer.exception;

/**
 * author - sh.khalajestani
 * created on - 3/9/2022
 */

public class InvalidTokenPrefixException extends RuntimeException {
    public InvalidTokenPrefixException(String message) {
        super(message);
    }
}
