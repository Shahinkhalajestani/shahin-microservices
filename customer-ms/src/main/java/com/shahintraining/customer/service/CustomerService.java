package com.shahintraining.customer.service;

import com.shahintraining.customer.domain.Customer;
import com.shahintraining.customer.domain.CustomerRegistrationRequest;
import com.shahintraining.customer.events.CustomerRegistrationEvent;
import com.shahintraining.customer.exception.CustomerAlreadyExistsException;
import com.shahintraining.customer.exception.CustomerIsFraudsterException;
import com.shahintraining.customer.exception.CustomerNotFoundException;
import com.shahintraining.customer.proxy.FraudCheckProxy;
import com.shahintraining.customer.repo.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository, FraudCheckProxy fraudCheckProxy) {


    public Customer register(CustomerRegistrationRequest customerRegistrationRequest) {
        customerRepository.findCustomerByEmail(customerRegistrationRequest.email()).ifPresent(
                customer -> {
                    throw new CustomerAlreadyExistsException("Customer Already Exists");
                }
        );
        log.info("registering customer : {}", customerRegistrationRequest.firstName());
        Customer customer = new Customer().firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email());
       return customerRepository.save(customer);
    }

    public Customer getCustomer(String email){
        Optional<Customer> customerByEmail = customerRepository.findCustomerByEmail(email);
        return customerByEmail.orElseThrow(() -> {
            throw new CustomerNotFoundException("customer not found");
        });
    }

    public void checkCustomerFraudster(Long customerId){
        if (fraudCheckProxy.checkFraudCustomer(customerId).isFraudster()){
            throw new CustomerIsFraudsterException("customer is a fraud");
        }
    }

    public void save(Customer customer){
        customerRepository.save(customer);
    }



}
