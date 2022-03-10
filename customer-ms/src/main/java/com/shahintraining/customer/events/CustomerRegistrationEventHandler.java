package com.shahintraining.customer.events;

import com.shahintraining.customer.domain.Customer;
import com.shahintraining.customer.domain.VerificationToken;
import com.shahintraining.customer.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public record CustomerRegistrationEventHandler(
        VerificationTokenService verificationTokenService, JavaMailSender mailSender,
        MessageSource messageSource,
        @Value("${server.email.address}") String serverMail) implements ApplicationListener<CustomerRegistrationEvent> {


    @Override
    public void onApplicationEvent(@NonNull CustomerRegistrationEvent event) {
        sendVerificationEmail(event);
    }

    private void sendVerificationEmail(CustomerRegistrationEvent event) {
        Customer customer = event.getCustomer();
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (verificationTokenService().checkTokenExists(token));
        createVerificationToken(customer, token);
        String confirmationUrl = event.getAppUrl() + "/verify-customer?token=" + token;
        String message = messageSource.getMessage("reg.message" + confirmationUrl, null, event.getLocale());
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(serverMail);
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo(customer.email());
        simpleMailMessage.setSubject("Customer Verification");
        mailSender.send(simpleMailMessage);
    }


    private void createVerificationToken(Customer customer, String token) {
        VerificationToken verificationToken = new VerificationToken().token(token).customer(customer);
        verificationToken.calculateExpiryDate(VerificationToken.EXPIRE_IN_MINUTES);
        verificationTokenService.save(verificationToken);
    }
}
