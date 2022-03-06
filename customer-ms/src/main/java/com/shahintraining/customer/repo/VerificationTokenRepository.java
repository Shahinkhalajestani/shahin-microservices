package com.shahintraining.customer.repo;

import com.shahintraining.customer.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
}