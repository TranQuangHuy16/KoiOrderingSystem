package com.project.KoiOrderingSystem.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Min(value = 0, message = "Total Price must be more than 0")
    float totalPrice;

    @Column(nullable = true)
    String image;

    @FutureOrPresent(message = "Date must be present or future")
    Date bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'DANG_CHO_XAC_NHAN'")
    StatusBooking status;

    @Column(nullable = true)
    String note;

}
