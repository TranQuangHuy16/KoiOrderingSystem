package com.project.KoiOrderingSystem.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.UUID;

@Data
public class BookingPaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

}
