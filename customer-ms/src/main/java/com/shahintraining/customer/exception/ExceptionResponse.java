package com.shahintraining.customer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
public class ExceptionResponse {
    private final String message;
    private final String detail;
    private final Date timeStamp;
}
