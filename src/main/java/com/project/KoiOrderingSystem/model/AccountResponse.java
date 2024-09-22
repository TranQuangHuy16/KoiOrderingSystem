package com.project.KoiOrderingSystem.model;

import lombok.Data;

@Data
public class AccountResponse {
    int id;
    int roleId;
    String username;
    String firstName;
    String lastName;
    String address;
    String phone;
    String email;
    String profile;
}
