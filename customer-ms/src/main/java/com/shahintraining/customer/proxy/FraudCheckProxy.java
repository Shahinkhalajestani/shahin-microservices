package com.shahintraining.customer.proxy;

import com.shahintraining.customer.domain.FraudCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "fraud",url = "http://localhost:25489")
public interface FraudCheckProxy {

    @GetMapping("/check/fraud/customer/{customerId}")
    FraudCheckResponse checkFraudCustomer(@PathVariable(value = "customerId") Long customerId);

}
