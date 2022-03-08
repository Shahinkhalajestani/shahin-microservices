package com.shahintraining.customer.events;

import com.shahintraining.customer.domain.Customer;
import com.shahintraining.customer.domain.VerificationToken;
import com.shahintraining.customer.repo.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public record CustomerRegistrationEventHandler(
        VerificationTokenRepository verificationTokenRepository, JavaMailSender mailSender,
        MessageSource messageSource,@Value("${server.email.address}") String serverMail) implements ApplicationListener<CustomerRegistrationEvent> {


    @Override
    public void onApplicationEvent(CustomerRegistrationEvent event) {
        Customer customer = event.getCustomer();
        String token = UUID.randomUUID().toString();
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


    private VerificationToken createVerificationToken(Customer customer, String token) {
        VerificationToken verificationToken = new VerificationToken().token(token).customer(customer);
        verificationToken.calculateExpiryDate(VerificationToken.EXPIRE_IN_MINUTES);
        return verificationTokenRepository.save(verificationToken);
    }
}
