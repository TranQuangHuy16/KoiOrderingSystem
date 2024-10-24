package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.StatusOrder;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class OrderRequest {


    LocalDate expectedDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
    StatusOrder status;

    long bookingId;

    List<Long> koiIds;


}
