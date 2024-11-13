package com.project.KoiOrderingSystem.model;

import lombok.Data;

@Data
public class RefundAccountRequest {
    String bankName;
    String accountNumber;
    String accountName;
}
