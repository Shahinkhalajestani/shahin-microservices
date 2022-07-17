package com.shahintraining.notification;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerNotificationMapper extends BaseMapper<CustomerNotification, CustomerNotificationDto> {

}
