package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.TripDetail;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;

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

    float price;

    Set<TripDetailRequest> tripDetailRequests;

}
