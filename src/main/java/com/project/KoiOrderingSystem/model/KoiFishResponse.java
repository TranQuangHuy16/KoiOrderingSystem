package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.Farm;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class KoiFishResponse {
    long id;

    String koiName;

    float price;

    String type;

    String description;

    String image;

    String farmName;
}
