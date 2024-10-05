package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.StatusTrip;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Data
public class TripRequest {
    @FutureOrPresent(message = "Start Date must be present or future")
    LocalDate startDate;

    @Future(message = "End Date must be future")
    LocalDate endDate;

    @Column(nullable = false)
    String startLocation;

    @Column(nullable = false)
    String endLocation;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'COMING_SOON'")
    StatusTrip status;

    Set<Long> farmIds;

}
