package com.project.KoiOrderingSystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
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

    Date bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'PENDING_CONFIRMATION'")
    StatusBooking status;

    @Column(nullable = true)
    String note;

    @ManyToOne
    @JoinColumn(name = "accountId")
    @JsonIgnore
    Account account;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "tripId")
    Trip trip;

    @OneToOne(mappedBy = "booking")
    @JsonIgnore
    Orders order;

    @OneToOne(mappedBy = "booking")
    @JsonIgnore
    Payment payment;
}
