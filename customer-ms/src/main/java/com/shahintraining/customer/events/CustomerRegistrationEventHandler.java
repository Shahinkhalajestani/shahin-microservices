package com.shahintraining.customer.events;

import com.shahintraining.customer.domain.Customer;
import com.shahintraining.customer.domain.VerificationToken;
import com.shahintraining.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CustomerRegistrationEventHandler implements ApplicationListener<CustomerRegistrationEvent> {

    private final MessageSource messageSource;
    private final CustomerService customerService;

    @Override
    public void onApplicationEvent(CustomerRegistrationEvent event) {
        Customer customer = event.getCustomer();
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = customerService.createVerificationToken(customer, token);
        //todo send verification token to email.
    }
}
