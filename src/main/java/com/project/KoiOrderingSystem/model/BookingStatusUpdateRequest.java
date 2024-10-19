package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.StatusBooking;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class BookingStatusUpdateRequest {
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'PENDING_CONFIRMATION'")
    StatusBooking status;
}
