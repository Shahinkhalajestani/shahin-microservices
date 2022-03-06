package com.shahintraining.customer.repo;

import com.shahintraining.customer.domain.Customer;
import org.apache.catalina.util.CustomObjectInputStream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findCustomerByEmail(String email);
}
