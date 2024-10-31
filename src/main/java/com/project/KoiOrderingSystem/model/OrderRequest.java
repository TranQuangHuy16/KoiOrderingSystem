package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.StatusOrder;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderRequest {


    LocalDate expectedDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
    StatusOrder status;

    @Column(nullable = true)
    String address;

    UUID bookingId;

    @Min(value = 0, message = "Total must be greater than 0")
    float price;

    List<OrderDetailRequest> orderDetails;


}
