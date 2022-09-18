package com.shahintraining.customer.events;

import com.shahintraining.customer.domain.Customer;
import com.shahintraining.customer.domain.VerificationToken;
import com.shahintraining.customer.service.VerificationTokenService;
import com.shahintraining.notification.CustomerNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerRegistrationEventHandler implements ApplicationListener<CustomerRegistrationEvent> {

    private final Environment environment;
    private final MessageSource messageSource;
    private final VerificationTokenService verificationTokenService;


    @Override
    public void onApplicationEvent(@NonNull CustomerRegistrationEvent event) {
          sendNotificationToCustomer(event);
//        sendVerificationEmail(event);
    }

    private void sendVerificationEmail(CustomerRegistrationEvent event) {
        String serverMail = environment.getProperty("server.email.address");
        Customer customer = event.getCustomer();
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (verificationTokenService.checkTokenExists(token));
        createVerificationToken(customer, token);
        log.info("the token is : {}",token);
        String confirmationUrl = event.getAppUrl() + "/verify-customer?token=" + token;
        String message = messageSource.getMessage("reg.message" + confirmationUrl, null, event.getLocale());
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        assert serverMail != null;
        simpleMailMessage.setFrom(serverMail);
        simpleMailMessage.setText(message);
        simpleMailMessage.setTo(customer.email());
        simpleMailMessage.setSubject("Customer Verification");
//        mailSender.send(simpleMailMessage);
        System.out.println(simpleMailMessage);
    }

    private void sendNotificationToCustomer(CustomerRegistrationEvent customerRegistrationEvent){
        Customer customer = customerRegistrationEvent.getCustomer();
        CustomerNotificationDto customerNotificationDto = new CustomerNotificationDto()
                .setCustomerId(customer.id().toString())
                .setContent(environment.getProperty("customer.registration.message"))
                .setCustomerPhoneNumber(customer.phoneNumber());

    }



    private void createVerificationToken(Customer customer, String token) {
        VerificationToken verificationToken = new VerificationToken().token(token).customer(customer);
        verificationToken.calculateExpiryDate(VerificationToken.EXPIRE_IN_MINUTES);
        verificationTokenService.save(verificationToken);
    }
}
