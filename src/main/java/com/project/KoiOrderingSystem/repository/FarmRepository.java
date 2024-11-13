package com.project.KoiOrderingSystem.repository;


import com.project.KoiOrderingSystem.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmRepository extends JpaRepository<Farm, Long> {

    List<Farm> findFarmsByIsDeletedFalse();


    Farm findFarmById(long id);

    Farm findFarmByIdAndIsDeletedFalse(long id);
}
