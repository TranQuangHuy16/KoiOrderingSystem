package com.project.KoiOrderingSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.KoiOrderingSystem.entity.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class BookingResponseManager {

    UUID id;

    float totalPrice;

    String image;

    Date bookingDate;

    StatusBooking status;

    String note;

    Account account;

    Trip trip;

    FeedbackResponse FeedbackResponse;
}
