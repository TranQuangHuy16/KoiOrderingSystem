package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    List<Transactions> findTransactionsesByFromId(long fromId);

    @Query("SELECT YEAR(t.created_at) as year, MONTH(t.created_at) as month, SUM(t.price) as totalOfMonth " +
            "FROM Transactions t " +
            "WHERE t.status = 'SUCCESS' AND t.payment.order IS NOT NULL " +
            "GROUP BY YEAR(t.created_at), MONTH(t.created_at) " +
            "ORDER BY YEAR(t.created_at), MONTH(t.created_at)")
    List<Object[]> getRevenueOrderByMonth();

    @Query("SELECT YEAR(t.created_at) as year, MONTH(t.created_at) as month, SUM(t.price) as totalOfMonth " +
            "FROM Transactions t " +
            "WHERE t.status = 'SUCCESS' AND t.payment.booking IS NOT NULL " +
            "GROUP BY YEAR(t.created_at), MONTH(t.created_at) " +
            "ORDER BY YEAR(t.created_at), MONTH(t.created_at)")
    List<Object[]> getRevenueBookingByMonth();

    @Query("SELECT YEAR(t.created_at) as year, MONTH(t.created_at) as month, SUM(t.price) as totalOfMonth " +
            "FROM Transactions t " +
            "WHERE t.status = 'SUCCESS' " +
            "GROUP BY YEAR(t.created_at), MONTH(t.created_at) " +
            "ORDER BY YEAR(t.created_at), MONTH(t.created_at)")
    List<Object[]> getRevenueAllByMonth();
}
