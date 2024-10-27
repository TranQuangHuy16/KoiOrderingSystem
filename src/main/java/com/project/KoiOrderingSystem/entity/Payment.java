package com.project.KoiOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    Date created_at;

    float price;

    @OneToOne
    @JoinColumn(name = "bookingId", nullable = true)
    @JsonIgnore
    Booking booking;

    @OneToOne
    @JoinColumn(name = "orderId", nullable = true)
    @JsonIgnore
    Orders order;

    @OneToOne(mappedBy = "payment")
    @JsonIgnore
    Transactions transactions;
}
