package com.project.KoiOrderingSystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class CheckInRequest {
    @Column(nullable = true, columnDefinition = "TEXT")
    @Lob
    String image;
}
