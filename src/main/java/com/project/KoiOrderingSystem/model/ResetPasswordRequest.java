package com.project.KoiOrderingSystem.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @Size(min = 6, message = "Password must at lease 6 characters")
    String password;
}
