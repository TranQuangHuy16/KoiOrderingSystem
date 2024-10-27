package com.project.KoiOrderingSystem.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "fromId")
    Account from;

    @ManyToOne
    @JoinColumn(name = "toId")
    Account to;

    @OneToOne
    @JoinColumn(name = "paymentId")
    Payment payment;

    @Enumerated(EnumType.STRING)
    StatusTransactions status;

    String description;
}
