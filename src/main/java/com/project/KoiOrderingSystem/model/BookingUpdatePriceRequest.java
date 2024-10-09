package com.project.KoiOrderingSystem.model;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class BookingUpdatePriceRequest {
    @Min(value = 0, message = "Total Price must be more than 0")
    float totalPrice;
}
