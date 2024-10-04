package com.project.KoiOrderingSystem.repository;

import com.project.KoiOrderingSystem.entity.Farm;
import com.project.KoiOrderingSystem.entity.KoiFish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KoiRepository extends JpaRepository<KoiFish, Long> {
    List<KoiFish> findKoiFishesByIsDeletedFalse();


    KoiFish findKoiFishById(long id);
}
