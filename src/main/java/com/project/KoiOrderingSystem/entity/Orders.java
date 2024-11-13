package com.project.KoiOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Orders {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;


    @Column(nullable = true)
    String image;


    LocalDate expectedDate;


    @Column(nullable = true)
    Date deliveredDate;

    @Min(value = 0, message = "Total must be greater than 0")
    float price;

    @Column(nullable = true)
    String address;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
    StatusOrder status;


    @OneToOne
    @JoinColumn(name = "bookingId")
    Booking booking;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    @JsonIgnore
    List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    List<Payment> payments;
}
