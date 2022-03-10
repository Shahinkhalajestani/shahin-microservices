package com.shahintraining.customer.controller;

import com.shahintraining.customer.domain.Customer;
import com.shahintraining.customer.domain.CustomerRegistrationRequest;
import com.shahintraining.customer.domain.VerificationToken;
import com.shahintraining.customer.events.CustomerRegistrationEvent;
import com.shahintraining.customer.exception.VerificationTokenExpiredException;
import com.shahintraining.customer.exception.VerificationTokenNotFoundException;
import com.shahintraining.customer.service.CustomerService;
import com.shahintraining.customer.service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("api/v1/customer")
public record CustomerController(CustomerService customerService, VerificationTokenService verificationTokenService) {

    @PostMapping("/register")
    public ResponseEntity<Void> registerCustomer(@RequestBody CustomerRegistrationRequest
                                                          customerRegistrationRequest, HttpServletRequest request){
        new CustomerRegistrationEvent(request.getLocale(),request.getContextPath()
                ,customerService.register(customerRegistrationRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/verify-customer")
    public ResponseEntity<Void> verifyCustomer(@RequestParam String token){
        VerificationToken tokenValue = verificationTokenService.findByTokenValue(token);
        if (tokenValue.expired()){
            throw new VerificationTokenExpiredException("the token is expired");
        }
        Customer customer = tokenValue.customer();
        customerService.checkCustomerFraudster(customer.id());
        customer.verified(true);
        customerService.save(customer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resend-token")
    public ResponseEntity<Void> resendToken(@RequestParam String email,HttpServletRequest request){
        new CustomerRegistrationEvent(request.getLocale(),request.getContextPath(),customerService.getCustomer(email));
        return ResponseEntity.ok().build();
    }

}
