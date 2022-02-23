package com.shahintraining.fraud;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("check/fraud/customer")
public record FraudCheckingController(FraudCheckService fraudCheckService) {

    @GetMapping("{customerId}")
    public ResponseEntity<FraudCheckResponse> isFraudster(
            @PathVariable Long customerId
    ) {
        return fraudCheckService.isFraudulentCustomer(customerId) ?
                ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
                        .body(new FraudCheckResponse(true)) :
                ResponseEntity.ok(new FraudCheckResponse(false));
    }

}
