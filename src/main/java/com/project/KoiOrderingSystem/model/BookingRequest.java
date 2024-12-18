package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.entity.StatusBooking;
import com.project.KoiOrderingSystem.entity.Trip;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.Date;

@Data
public class BookingRequest {

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'PENDING_CONFIRMATION'")
    StatusBooking status;

    @Min(value = 1, message = "Quantity must be more than 1")
    int quantity;

    @Column(nullable = true)
    String note;

    long tripId;
}
