package com.project.KoiOrderingSystem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentResponse {

    long id;

    Date created_at;

    float price;

    long bookingId;

    long orderId;
}
