package com.project.KoiOrderingSystem.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "fromId")
    Account from;

    @ManyToOne
    @JoinColumn(name = "toId")
    Account to;

    @ManyToOne
    @JoinColumn(name = "paymentId")
    Payment payment;

    String description;

}
