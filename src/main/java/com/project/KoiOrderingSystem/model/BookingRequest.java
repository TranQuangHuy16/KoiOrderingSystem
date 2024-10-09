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
    @Min(value = 0, message = "Total Price must be more than 0")
    float totalPrice;

    @Column(nullable = true)
    String image;
    
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'DANG_CHO_XAC_NHAN'")
    StatusBooking status;

    @Column(nullable = true)
    String note;

    long tripId;
}
