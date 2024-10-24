package com.project.KoiOrderingSystem.model;

import lombok.Data;

@Data
public class OrderDetailRequest {
    long koiId;
    int quantity;
}
