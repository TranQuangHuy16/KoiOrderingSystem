package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.Payment;
import com.project.KoiOrderingSystem.entity.StatusTransactions;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionRespose {
    String from;

    String to;

    LocalDateTime created_at;

    StatusTransactions status;

    String description;

    PaymentResponse paymentResponse;
}
