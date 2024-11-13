package com.project.KoiOrderingSystem.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TripDetailRequest {
    long farmId;

    LocalDate travelDate;
}
