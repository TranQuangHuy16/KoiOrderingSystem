package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findTransactionsesByFromId(long fromId);
}
