package com.project.KoiOrderingSystem.model;

import com.project.KoiOrderingSystem.entity.Role;
import lombok.Data;

@Data
public class AccountResponse {
    long id;
    Role role;
    String username;
    String firstName;
    String lastName;
    String address;
    String phone;
    String email;
    String profile;
    String token;
}
