package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Column(unique = true)
    String username;

    @Size(min = 2, max = 32, message = "First name must be between 2 and 32 characters")
    String firstName;

    @Size(min = 2, max = 32, message = "Last name must be between 2 and 32 characters")
    String lastName;

    @Email(message = "Invalid email")
    String email;

    @Size(min = 6, message = "Password must at lease 6 characters")
    String password;

    Role role;
}
