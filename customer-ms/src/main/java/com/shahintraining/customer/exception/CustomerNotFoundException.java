package com.shahintraining.customer.exception;

/**
 * author - sh.khalajestani
 * created on - 3/8/2022
 */

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
