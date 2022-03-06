package com.shahintraining.customer.events;

import com.shahintraining.customer.domain.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class CustomerRegistrationEvent extends ApplicationEvent {
    private Locale locale;
    private String appUrl;
    private Customer customer;

    public CustomerRegistrationEvent(Locale locale, String appUrl, Customer customer) {
        super(customer);
        this.locale = locale;
        this.appUrl = appUrl;
        this.customer = customer;
    }
}
