package com.shahintraining.customer.domain;

public record CustomerRegistrationRequest(String firstName,
                                          String lastName ,
                                          String email,
                                          String password,
                                          String matchingPassword) {
}
