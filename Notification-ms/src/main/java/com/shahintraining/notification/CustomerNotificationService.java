package com.shahintraining.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerNotificationService {
    private final CustomerNotificationMapper customerNotificationMapper;
    private final CustomerNotificationRepository customerNotificationRepository;


    private void saveCustomerNotification(CustomerNotificationDto customerNotificationDto){
        CustomerNotification customerNotification = customerNotificationMapper.toEntity(customerNotificationDto);
        customerNotificationRepository.save(customerNotification);
    }

}
