package com.project.KoiOrderingSystem.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FarmRequest {

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
}
