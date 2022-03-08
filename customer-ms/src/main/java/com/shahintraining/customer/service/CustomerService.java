package com.shahintraining.customer.service;

import com.shahintraining.customer.domain.Customer;
import com.shahintraining.customer.domain.CustomerRegistrationRequest;
import com.shahintraining.customer.events.CustomerRegistrationEvent;
import com.shahintraining.customer.exception.CustomerAlreadyExistsException;
import com.shahintraining.customer.proxy.FraudCheckProxy;
import com.shahintraining.customer.repo.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository, FraudCheckProxy fraudCheckProxy) {


    public void register(CustomerRegistrationRequest customerRegistrationRequest, HttpServletRequest request) {
        customerRepository.findCustomerByEmail(customerRegistrationRequest.email()).ifPresent(
                customer -> {
                    throw new CustomerAlreadyExistsException("Customer Already Exists");
                }
        );
        log.info("registering customer : {}", customerRegistrationRequest.firstName());
        Customer customer = new Customer().firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email());
        customer = customerRepository.save(customer);
        new CustomerRegistrationEvent(request.getLocale(),request.getContextPath(),customer);
    }

}
