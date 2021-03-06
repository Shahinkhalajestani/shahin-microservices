package com.shahintraining.customer.proxy;

import com.shahintraining.notification.CustomerNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification")
public interface NotificationProxy {

    @PostMapping("/")
    void sendNotificationToCustomer(@RequestBody CustomerNotificationDto customerNotificationDto);

}
