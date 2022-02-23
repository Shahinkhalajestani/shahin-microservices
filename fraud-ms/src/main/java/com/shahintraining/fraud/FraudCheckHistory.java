package com.shahintraining.fraud;

/*
    author - sh.khalajestani
    created on - 2/23/2022
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true,fluent = true)
public class FraudCheckHistory {
    @Id
    private String id;
    private Long customerId;
    private Boolean isFraudster;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private Date creationDate;
    @LastModifiedDate
    private Date modifiedDate;
    @LastModifiedBy
    private String modifiedBy;
}
