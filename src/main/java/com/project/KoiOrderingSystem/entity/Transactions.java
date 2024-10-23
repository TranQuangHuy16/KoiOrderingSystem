package com.project.KoiOrderingSystem.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    Account from;

    @ManyToOne
    @JoinColumn(name = "toId")
    @JsonIgnore
    Account to;

    @OneToOne
    @JoinColumn(name = "paymentId")
    @JsonIgnore
    Payment payment;

    @Enumerated(EnumType.STRING)
    StatusTransactions status;

    String description;

}
