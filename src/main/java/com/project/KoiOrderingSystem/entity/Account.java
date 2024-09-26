package com.project.KoiOrderingSystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    long id;

    @Enumerated(EnumType.STRING) // Persist as a string in the DB
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'CUSTOMER'")
    Role role;

    @Column(unique = true)
    String username;

    @Size(min = 6, message = "Password must at lease 6 characters")
    String password;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List< SimpleGrantedAuthority > authorities = new ArrayList<>();
        if (this.role != null) authorities.add(new SimpleGrantedAuthority(this.role.toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
