package com.project.KoiOrderingSystem.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class ProfileRequest {
    @Size(min = 2, max = 32, message = "First name must be between 2 and 32 characters")
    String firstName;

    @Size(min = 2, max = 32, message = "Last name must be between 2 and 32 characters")
    String lastName;

    @Column(nullable = true)
    String address;

    @Column(nullable = true)
    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Invalid phone number")
    String phone;

    @Email(message = "Invalid email")
    String email;

    @Column(nullable = true)
    String profile;

}
