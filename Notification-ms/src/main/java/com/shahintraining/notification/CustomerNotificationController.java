package com.shahintraining.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CustomerNotificationController {

    private final CustomerNotificationService customerNotificationService;

    @PostMapping("/send-notification-to-customer")
    public ResponseEntity<Void> sendNotificationToCustomer(@RequestBody CustomerNotificationDto customerNotificationDto){
        customerNotificationService.saveCustomerNotification(customerNotificationDto);
        return ResponseEntity.ok().build();
    }

}
