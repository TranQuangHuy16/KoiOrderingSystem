package com.project.KoiOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    LocalDate startDate;

    @Future(message = "End Date must be future")
    LocalDate endDate;

    @Column(nullable = false)
    String startLocation;

    @Column(nullable = false)
    String endLocation;

    @Min(value = 0, message = "Trip price must be more than 0")
    float price;

    @JsonIgnore
    boolean isDeleted = false;

    @OneToMany(mappedBy = "trip")
    Set<TripDetail> tripDetails;

    @OneToMany(mappedBy = "trip")
    @JsonIgnore
    Set<Booking> bookings;
}
