package com.project.KoiOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;

    @Size(min = 2, max = 32, message = "Farm name must be between 2 and 32 characters")
    String farmName;

    @Size(min = 2, max = 255, message = "Location must be between 2 and 255 characters")
    String location;

    @Size(min = 2, max = 255, message = "Description must be between 2 and 255 characters")
    String description;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Invalid phone number")
    String phone;

    @Email(message = "Invalid email")
    String email;

    @Column(nullable = true)
    String image;

    @OneToMany(mappedBy = "farm")
    @JsonIgnore
    List<KoiFish> koiFish;

    @JsonIgnore
    boolean isDeleted = false;

    @ManyToMany(mappedBy = "farms")
    @JsonIgnore
    Set<Trip> trips;
}
