package com.shahintraining.customer.service;

import com.shahintraining.customer.domain.Customer;
import com.shahintraining.customer.domain.CustomerRegistrationRequest;
import com.shahintraining.customer.proxy.FraudCheckProxy;
import com.shahintraining.customer.repo.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository, FraudCheckProxy fraudCheckProxy) {

    public void register(CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("registering customer : {}", customerRegistrationRequest.firstName());
        Customer customer = new Customer().firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email());
        customer = customerRepository.save(customer);
        fraudCheckProxy.checkFraudCustomer(customer.id());
    }
}
