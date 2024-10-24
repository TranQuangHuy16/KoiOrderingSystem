package com.project.KoiOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class Orders {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;


    @Column(nullable = true)
    String image;


    LocalDate expectedDate;


    @Column(nullable = true)
    Date deliveredDate;


    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
    StatusOrder status;


    @OneToOne
    @JoinColumn(name = "bookingId")
    Booking booking;

    @OneToMany(mappedBy = "order")
    Set<OrderDetail> orderDetails;
}
