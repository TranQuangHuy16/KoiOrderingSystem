package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findBookingsByAccount(Account account);

    Booking findBookingById(UUID bookingId);

    @Query("SELECT YEAR(b.bookingDate) as year, MONTH(b.bookingDate) as month, COUNT(b) as QuantityOfMonth " +
            "FROM Booking b " +
            "WHERE b.status = 'COMPLETED' " +
            "GROUP BY YEAR(b.bookingDate), MONTH(b.bookingDate) " +
            "ORDER BY YEAR(b.bookingDate), MONTH(b.bookingDate)")
    List<Object[]> getBookingByMonth();
}
