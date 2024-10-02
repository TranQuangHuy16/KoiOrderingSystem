package com.project.KoiOrderingSystem.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgotPasswordRequest {

    @Email(message = "Invalid email")
    @Column(unique = true)
    String email;
}
