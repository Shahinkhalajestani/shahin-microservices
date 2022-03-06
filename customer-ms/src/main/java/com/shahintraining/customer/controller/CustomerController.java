package com.shahintraining.customer.controller;

import com.shahintraining.customer.domain.CustomerRegistrationRequest;
import com.shahintraining.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/customer")
public record CustomerController(CustomerService customerService) {

    @PostMapping("/register")
    public ResponseEntity<Void> registerCustomer(@RequestBody CustomerRegistrationRequest
                                                          customerRegistrationRequest){
        customerService.register(customerRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
