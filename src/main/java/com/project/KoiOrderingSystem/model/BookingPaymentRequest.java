package com.project.KoiOrderingSystem.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class BookingPaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Min(value = 0, message = "Total Price must be more than 0")
    float totalPrice;
}
