package com.shahintraining.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CustomerNotificationDto {
    private String id;
    private String content;
    private String customerId;
    private String customerPhoneNumber;

}
