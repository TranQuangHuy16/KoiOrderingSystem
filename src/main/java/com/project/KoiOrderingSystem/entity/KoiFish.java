package com.project.KoiOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class KoiFish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;

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

    @ManyToOne
    @JoinColumn(name = "farmId")
    Farm farm;

    @JsonIgnore
    boolean isDeleted = false;

    @OneToMany(mappedBy = "koi")
    @JsonIgnore
    Set<OrderDetail> orderDetails;
}
