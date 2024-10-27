package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Farm;
import com.project.KoiOrderingSystem.entity.KoiFish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KoiRepository extends JpaRepository<KoiFish, Long> {
    List<KoiFish> findKoiFishesByIsDeletedFalse();


    KoiFish findKoiFishById(long id);


    @Query("SELECT k.koiName, SUM(od.quantity) as totalSold FROM OrderDetail od JOIN od.koi k " +
            "GROUP BY k.id " +
            "ORDER BY totalSold DESC")
    List<Object[]> findTopKoiSold();
}
