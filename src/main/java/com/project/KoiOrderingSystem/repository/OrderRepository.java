package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Orders, UUID> {

    @Query("SELECT o FROM Orders o WHERE o.booking.account = :account")
    List<Orders> findOrdersByAccount(@Param("account") Account account);

    Orders findOrderById(UUID orderId);

    @Query("SELECT YEAR(o.orderDate) as year, MONTH(o.orderDate) as month, COUNT(o) as QuantityOfMonth " +
            "FROM Orders o " +
            "WHERE o.status = 'COMPLETED' " +
            "GROUP BY YEAR(o.orderDate), MONTH(o.orderDate) " +
            "ORDER BY YEAR(o.orderDate), MONTH(o.orderDate)")
    List<Object[]> getOrderByMonth();
}
