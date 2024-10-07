package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
