package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
