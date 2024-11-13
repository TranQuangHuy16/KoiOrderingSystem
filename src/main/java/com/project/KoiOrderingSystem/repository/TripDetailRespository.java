package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Trip;
import com.project.KoiOrderingSystem.entity.TripDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TripDetailRespository extends JpaRepository<TripDetail, Long> {

    @Transactional // This annotation is used to specify the scope of a single database transaction.
    void deleteTripDetailsByTripId(long tripId);
}
