package com.shahintraining.fraud;

public record FraudCheckService(FraudCheckHistoryRepository fraudRepository) {

    public boolean isFraudulentCustomer(Long customerId){
        fraudRepository.save(new FraudCheckHistory().isFraudster(false).customerId(customerId));
        return false;
    }
}
