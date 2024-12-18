package com.project.KoiOrderingSystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Min(value = 0, message = "Price must be more than 0")
    @Column(nullable = true)
    float totalPrice;

    @Min(value = 0, message = "Ticket price must be more than 0")
    @Column(nullable = true)
    float ticketPrice;

    @Column(nullable = true, columnDefinition = "TEXT")
    @Lob
    String image;

    @Column(nullable = true)
    String refundImage;

    Date bookingDate;

    @Column(nullable = true)
    Date cancelDate;

    @Min(value = 1, message = "Quantity must be more than 1")
    int quantity;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'PENDING_CONFIRMATION'")
    StatusBooking status;

    @Column(nullable = true)
    String note;

    @ManyToOne
    @JoinColumn(name = "accountId")
    Account account;

    @ManyToOne
    @JoinColumn(name = "tripId")
    Trip trip;

    @OneToOne(mappedBy = "booking")
    @JsonIgnore
    Orders order;

    @OneToOne(mappedBy = "booking")
    @JsonIgnore
    Feedback feedback;

    @OneToMany(mappedBy = "booking")
    @JsonIgnore
    List<Payment> payments;
}
