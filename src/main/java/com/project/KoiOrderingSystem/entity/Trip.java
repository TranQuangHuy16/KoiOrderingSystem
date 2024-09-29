package com.project.KoiOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;

    @FutureOrPresent(message = "Start Date must be present or future")
    Date startDate;

    @Future(message = "End Date must be future")
    Date endDate;

    @Column(nullable = false)
    String startLocation;

    @Column(nullable = false)
    String endLocation;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'SAP_KHOI_HANH'")
    StatusTrip status;

    @Column(nullable = true)
    String image;

}
