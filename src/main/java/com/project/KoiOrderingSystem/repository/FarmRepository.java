package com.project.KoiOrderingSystem.repository;


import com.project.KoiOrderingSystem.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FarmRepository extends JpaRepository<Farm, Long> {

    List<Farm> findFarmsByIsDeletedFalse();


    Farm findFarmById(long id);

    Farm findFarmByIdAndIsDeletedFalse(long id);

    @Query("SELECT f.farmName, COUNT(b) as bookingCount " +
            "FROM Booking b " +
            "JOIN b.trip.tripDetails.farm f " +
            "WHERE b.status = 'COMPLETED' " +
            "GROUP BY f.farmName " +
            "ORDER BY COUNT(b) DESC")
    List<Object[]> findTopFarm();
}
