package com.shahintraining.fraud;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("check/fraud/customer")
public record FraudCheckingController(FraudCheckService fraudCheckService) {

    @GetMapping("{customerId}")
    public FraudCheckResponse isFraudster(
            @PathVariable Long customerId
    ) {
        return fraudCheckService.isFraudulentCustomer(customerId)? new FraudCheckResponse(true):
        new FraudCheckResponse(false);
    }

}
