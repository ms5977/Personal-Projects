package com.main.repository;

import com.main.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends MongoRepository<Payment,String> {
    Optional<Payment> findByRazorPayOrderId(String razorPayOrderId);
}
