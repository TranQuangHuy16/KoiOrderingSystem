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

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "orderId")
    Orders orders;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "bookingId")
    Booking booking;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Transactions> transactions;
}
