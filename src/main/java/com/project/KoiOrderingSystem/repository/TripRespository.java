package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRespository extends JpaRepository<Trip, Long> {
    Trip findTripByIdAndIsDeletedFalse(long id);

    List<Trip> findTripsByIsDeletedFalse();
}
