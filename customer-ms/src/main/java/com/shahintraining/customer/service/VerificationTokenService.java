package com.shahintraining.customer.service;

import com.netflix.discovery.provider.Serializer;
import com.shahintraining.customer.domain.VerificationToken;
import com.shahintraining.customer.exception.VerificationTokenNotFoundException;
import com.shahintraining.customer.repo.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * author - sh.khalajestani
 * created on - 3/8/2022
 */

@Service
@Slf4j
public record VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {

    public VerificationToken findByTokenValue(String token){
       return verificationTokenRepository.findByToken(token).orElseThrow(() -> {
            log.error("token {} not found in repository",token);
            throw new VerificationTokenNotFoundException("verification token not found");
        });
    }

}
