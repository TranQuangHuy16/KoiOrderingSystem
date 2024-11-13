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

    @ManyToOne
    @JoinColumn(name = "bookingId", nullable = true)
    Booking booking;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = true)
    Orders order;

    @OneToOne(mappedBy = "payment")
    @JsonIgnore
    Transactions transactions;
}
