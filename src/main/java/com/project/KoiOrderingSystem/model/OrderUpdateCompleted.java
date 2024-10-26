package com.project.KoiOrderingSystem.model;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;


@Data
public class OrderUpdateCompleted {

    @Column(nullable = true)
    String image;
}
