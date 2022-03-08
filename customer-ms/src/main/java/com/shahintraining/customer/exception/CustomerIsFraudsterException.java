package com.shahintraining.customer.exception;

/**
 * author - sh.khalajestani
 * created on - 3/8/2022
 */

public class CustomerIsFraudsterException extends RuntimeException{
    public CustomerIsFraudsterException(String message) {
        super(message);
    }
}
