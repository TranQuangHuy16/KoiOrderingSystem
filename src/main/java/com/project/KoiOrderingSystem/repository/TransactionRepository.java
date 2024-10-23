package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
}
