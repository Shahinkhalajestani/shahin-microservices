package com.shahintraining.fraud;

import org.springframework.stereotype.Service;

@Service
public record FraudCheckService(FraudCheckHistoryRepository fraudRepository) {

    public boolean isFraudulentCustomer(Long customerId) {
        //for example, we can check weather the customer is registered as a con or not this is just a demo.
        fraudRepository.save(new FraudCheckHistory().isFraudster(false).customerId(customerId));
        return false;
    }
}
