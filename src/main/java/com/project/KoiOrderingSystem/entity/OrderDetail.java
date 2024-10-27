package com.project.KoiOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    Orders order;


    @ManyToOne
    @JoinColumn(name = "koiId")
    @JsonIgnore
    KoiFish koi;

}
