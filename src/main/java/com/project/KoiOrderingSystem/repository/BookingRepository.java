package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingsByAccount(Account account);

    Booking findBookingById(UUID bookingId);

}
