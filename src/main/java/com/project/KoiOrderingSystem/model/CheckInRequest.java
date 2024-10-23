package com.project.KoiOrderingSystem.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CheckInRequest {
    @Column(nullable = true)
    String image;
}
