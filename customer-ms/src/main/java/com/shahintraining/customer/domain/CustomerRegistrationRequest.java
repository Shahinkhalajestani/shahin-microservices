package com.shahintraining.customer.domain;

public record CustomerRegistrationRequest(String firstName,
                                          String lastName ,
                                          String email) {
}
