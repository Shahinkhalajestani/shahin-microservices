package com.shahintraining.notification;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerNotificationRepository extends MongoRepository<CustomerNotification,String> {
}
