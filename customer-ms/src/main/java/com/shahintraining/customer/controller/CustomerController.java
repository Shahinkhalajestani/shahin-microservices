package com.shahintraining.customer.controller;

import com.shahintraining.customer.domain.Customer;
import com.shahintraining.customer.domain.CustomerRegistrationRequest;
import com.shahintraining.customer.domain.VerificationToken;
import com.shahintraining.customer.events.CustomerRegistrationEvent;
import com.shahintraining.customer.exception.VerificationTokenExpiredException;
import com.shahintraining.customer.service.CustomerService;
import com.shahintraining.customer.service.JwtUtilityService;
import com.shahintraining.customer.service.VerificationTokenService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("api/v1/customer")
public record CustomerController(CustomerService customerService, VerificationTokenService verificationTokenService,
                                 ApplicationEventPublisher eventPublisher, JwtUtilityService jwtUtilityService) {

    @PostMapping("/register")
    public ResponseEntity<Void> registerCustomer(@RequestBody CustomerRegistrationRequest
                                                          customerRegistrationRequest, HttpServletRequest request){
        eventPublisher.publishEvent(new CustomerRegistrationEvent(request.getLocale(),request.getContextPath()
                ,customerService.register(customerRegistrationRequest)));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/verify-customer")
    public ResponseEntity<Void> verifyCustomer(@RequestParam String token){
        VerificationToken tokenValue = verificationTokenService.findByTokenValue(token);
        if (tokenValue.expired()){
            log.error("the token is expired");
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
        eventPublisher.publishEvent(new CustomerRegistrationEvent(request.getLocale(),request.getContextPath(),customerService.getCustomer(email)));
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @GetMapping("/refresh-token")
    public ResponseEntity<Void> refreshTOken(HttpServletRequest request, HttpServletResponse response){
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtUtilityService.checkAuthorizationHeader(authorizationHeader)){
            String username = jwtUtilityService.extractUsernameFromToken(authorizationHeader);
            UserDetails userDetails = customerService.loadUserByUsername(username);
            jwtUtilityService.generateTokens(userDetails,request,response);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


}
