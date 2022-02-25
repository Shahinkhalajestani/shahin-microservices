package com.shahintraining.fraud;

import org.springframework.stereotype.Service;

@Service
public record FraudCheckService(FraudCheckHistoryRepository fraudRepository) {

    public boolean isFraudulentCustomer(Long customerId) {
        fraudRepository.save(new FraudCheckHistory().isFraudster(false).customerId(customerId));
        return false;
    }
}
