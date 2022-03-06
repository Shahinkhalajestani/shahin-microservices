package com.shahintraining.customer.service;

import com.shahintraining.customer.domain.Customer;
import com.shahintraining.customer.domain.CustomerRegistrationRequest;
import com.shahintraining.customer.domain.VerificationToken;
import com.shahintraining.customer.exception.CustomerAlreadyExistsException;
import com.shahintraining.customer.proxy.FraudCheckProxy;
import com.shahintraining.customer.repo.CustomerRepository;
import com.shahintraining.customer.repo.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository, FraudCheckProxy fraudCheckProxy,
                              VerificationTokenRepository verificationTokenRepository) {

    public void register(CustomerRegistrationRequest customerRegistrationRequest) {
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
        sendRegisterationEmail(customer);
    }

    public void sendRegisterationEmail(Customer customer) {

    }

    public VerificationToken createVerificationToken(Customer customer, String token) {
        VerificationToken verificationToken = new VerificationToken().token(token).customer(customer);
        verificationToken.calculateExpiryDate(VerificationToken.EXPIRE_IN_MINUTES);
        return verificationTokenRepository.save(verificationToken);
    }
}
