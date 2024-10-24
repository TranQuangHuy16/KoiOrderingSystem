package com.project.KoiOrderingSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    int quantity;

    @ManyToOne
    @JoinColumn(name = "orderId")
    Orders order;

    @ManyToOne
    @JoinColumn(name = "koiId")
    KoiFish koi;

}
