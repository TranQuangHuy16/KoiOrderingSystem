package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.Farm;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class KoiRequest {

    @Size(min = 2, max = 32, message = "Koi name must be between 2 and 32 characters")
    String koiName;

    @Size(min = 2, max = 32, message = "Type must be between 2 and 32 characters")
    String type;

    @Min(value = 0, message = "Price must be at least 0")
    float price;

    @Size(min = 2, max = 255, message = "Description must be between 2 and 255 characters")
    String description;

    @Column(nullable = true)
    String image;

    long farmId;
}
