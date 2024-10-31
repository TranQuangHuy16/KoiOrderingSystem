package com.project.KoiOrderingSystem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PaymentResponse {

    long id;

    Date created_at;

    float price;

    UUID bookingId;

    UUID orderId;
}
